package net.got.worldgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.got.GotMod;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Biome source for the GoT mod — synced with terrain's bicubic B-spline pipeline.
 *
 * <p>Uses the same coordinate conversion, domain warp, and 4×4 grid sampling
 * as {@link GotChunkGenerator#computeRawSurfaceY} so biomes stay perfectly
 * aligned with terrain height transitions.
 *
 * <h3>Water containment + creek fringe</h3>
 * <p>Two post-vote checks run after the bicubic vote resolves a winner:
 * <ol>
 *   <li><b>Containment</b> — if a water biome won but terrain is dry
 *       ({@code surfaceY >= SEA_LEVEL}), the best-weighted land candidate
 *       is promoted instead.  Stops river/lake biomes bleeding onto dry shore.
 *   <li><b>Creek fringe</b> — if a land biome won AND the bicubic
 *       neighbourhood carries at least {@link #CREEK_FRINGE_THRESHOLD} of
 *       cumulative weight from river-type pixels, the cell is promoted to
 *       {@code got:creek}.  Creates organic, irregularly-shaped muddy
 *       creek-bank strips along both sides of every river without affecting
 *       ocean or lake shores.  Adjust {@link #CREEK_FRINGE_THRESHOLD} to
 *       change the strip width.
 * </ol>
 *
 * <h3>Sub-biome system</h3>
 * <p>After all map-based and creek/containment logic resolves the final
 * winner, {@link SubbiomeResolver#resolve(String, int, int)} is called.
 * If any registered subbiome's noise field exceeds its threshold at this
 * position, the subbiome ID replaces the winner.  Subbiomes are defined in
 * {@code data/got/worldgen/subbiomes/subbiomes.json} — see
 * {@link SubbiomeResolver} for the full format and tuning guide.
 */
public final class GotBiomeSource extends BiomeSource {

    /**
     * All explicitly-painted water biome IDs — must match biome_colors.json
     * entries whose base_height is below SEA_LEVEL.
     * {@code got:creek} is included so containment strips it from dry land.
     */
    private static final Set<String> WATER_BIOME_IDS = Set.of(
            "got:ocean",
            "got:deep_ocean",
            "got:river",
            "got:neck_river",
            "got:frozen_river",
            "got:lake",
            "got:frozen_lake",
            "got:creek"
    );

    /**
     * River-type biome IDs used for the creek-fringe check.
     * Lakes and oceans are intentionally excluded — creek fringes only form
     * along flowing rivers and the Neck river, not on ocean or lake shores.
     */
    private static final Set<String> RIVER_BIOME_IDS = Set.of(
            "got:river",
            "got:neck_river",
            "got:frozen_river"
    );

    /**
     * Minimum total bicubic vote weight from river pixels required for a dry
     * land cell to be promoted to {@code got:creek}.
     *
     * <p>Tuning guide (MAP_SCALE = 46 blocks per biomemap pixel):
     * <ul>
     *   <li>0.04 → ~2–3 block fringe (very thin strip, almost invisible)</li>
     *   <li>0.08 → ~8–15 block fringe (narrow creek bank, subtle)</li>
     *   <li>0.14 → ~20–35 block fringe (comfortable visible creek strip)</li>
     *   <li>0.20 → ~40–55 block fringe (wide, merges across narrow land gaps)</li>
     * </ul>
     */
    private static final float CREEK_FRINGE_THRESHOLD = 0.10f;

    /**
     * Cold/northern biome IDs where a warm green creek strip would look wrong.
     * Creek fringe is skipped when the best adjacent land candidate is one of these;
     * the cold biome is used directly instead so snowy banks stay snowy.
     */
    private static final Set<String> COLD_BIOME_IDS = Set.of(
            "got:always_winter",
            "got:frostfangs",
            "got:north",
            "got:north_hills",
            "got:north_mountains",
            "got:barrowlands",
            "got:haunted_forest",
            "got:the_wall"
    );

    /** Biome assigned on dry land adjacent to rivers (creek fringe). */
    private static final String CREEK_BIOME_ID = "got:creek";

    private static volatile int reloadGeneration = 0;
    public static void onMapReloaded() { reloadGeneration++; }

    public static final MapCodec<GotBiomeSource> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    RegistryCodecs.homogeneousList(Registries.BIOME)
                            .fieldOf("biomes")
                            .forGetter(s -> HolderSet.direct(s.biomes))
            ).apply(instance, instance.stable(
                    holderSet -> new GotBiomeSource(holderSet.stream().collect(Collectors.toList()))
            ))
    );

    private final List<Holder<Biome>>                  biomes;
    private final Map<ResourceLocation, Holder<Biome>> locationToHolder;
    private final Holder<Biome>                        fallback;

    public GotBiomeSource(List<Holder<Biome>> biomes) {
        this.biomes = List.copyOf(biomes);
        this.locationToHolder = new HashMap<>(biomes.size() * 2);
        for (Holder<Biome> h : biomes)
            h.unwrapKey().ifPresent(key -> locationToHolder.put(key.location(), h));

        Holder<Biome> fb = locationToHolder.get(GotMod.id("north"));
        if (fb == null) fb = locationToHolder.get(GotMod.id("ocean"));
        if (fb == null && !biomes.isEmpty()) fb = biomes.get(0);
        this.fallback = Objects.requireNonNull(fb, "GotBiomeSource: biome list is empty!");
    }

    @Override protected @NotNull MapCodec<? extends BiomeSource> codec() { return CODEC; }
    @Override protected @NotNull Stream<Holder<Biome>> collectPossibleBiomes() { return biomes.stream(); }

    @Override
    public @NotNull Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.@NotNull Sampler sampler) {
        if (!BiomemapLoader.isLoaded()) return fallback;

        // Biome-grid (quarter-resolution) → world block coordinates
        int worldX = x << 2;
        int worldZ = z << 2;

        // World block → biomemap pixel (same coordinate conversion as terrain)
        float cx = worldX / (float) BiomemapLoader.MAP_SCALE + BiomemapLoader.getWidth()  * 0.5f;
        float cz = worldZ / (float) BiomemapLoader.MAP_SCALE + BiomemapLoader.getHeight() * 0.5f;

        // Same domain warp as terrain
        float warpX = (float) SimplexNoise.noise(worldX / 320.0, worldZ / 320.0);
        float warpZ = (float) SimplexNoise.noise(worldX / 320.0 + 3.7, worldZ / 320.0 + 8.1);
        cx += warpX * 0.9f;
        cz += warpZ * 0.9f;

        int   ipx = (int) Math.floor(cx);
        int   ipz = (int) Math.floor(cz);
        float fx  = cx - ipx;
        float fz  = cz - ipz;

        // Sample 4×4 grid
        String[][]  biomeIds = new String[4][4];
        boolean[][] isWater  = new boolean[4][4];
        for (int i = -1; i <= 2; i++) {
            for (int j = -1; j <= 2; j++) {
                int px = ipx + i;
                int pz = ipz + j;
                if (px < 0 || pz < 0 || px >= BiomemapLoader.getWidth() || pz >= BiomemapLoader.getHeight()) {
                    biomeIds[i + 1][j + 1] = null;
                    isWater[i + 1][j + 1]  = false;
                } else {
                    var params = GotBiomeTerrainParams.forColor(BiomemapLoader.getRawPixel(px, pz));
                    biomeIds[i + 1][j + 1] = params.biomeId();
                    isWater[i + 1][j + 1]  = params.isWater();
                }
            }
        }

        // Bicubic B-spline voting
        Map<String, Float> biomeVotes = new HashMap<>();
        float riverInfluence = 0f; // accumulates raw (pre-boost) river pixel weights
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                String id = biomeIds[i][j];
                if (id == null || id.isEmpty()) continue;
                float wx     = cubicBsplineWeight(i - 1, fx);
                float wz     = cubicBsplineWeight(j - 1, fz);
                float weight = wx * wz;
                if (isWater[i][j]) weight *= 1.15f; // thin rivers survive
                biomeVotes.merge(id, weight, Float::sum);
                // Track how much of the vote came from river-type pixels
                // (measured before the 1.15× boost so the threshold stays stable)
                if (RIVER_BIOME_IDS.contains(id)) riverInfluence += wx * wz;
            }
        }

        // Overall winner
        String winner    = null;
        float  maxWeight = -1f;
        for (Map.Entry<String, Float> entry : biomeVotes.entrySet()) {
            if (entry.getValue() > maxWeight) {
                maxWeight = entry.getValue();
                winner    = entry.getKey();
            }
        }
        if (winner == null) return fallback;

        // Only run land-side logic when terrain is genuinely above sea level.
        float surfaceY = GotChunkGenerator.computeRawSurfaceY(worldX, worldZ);
        if (surfaceY >= GotChunkGenerator.SEA_LEVEL) {

            // Find best non-water land candidate from the vote (used below).
            String landCandidate    = null;
            float  landCandidateW   = -1f;
            for (Map.Entry<String, Float> entry : biomeVotes.entrySet()) {
                if (!WATER_BIOME_IDS.contains(entry.getKey())
                        && entry.getValue() > landCandidateW) {
                    landCandidateW = entry.getValue();
                    landCandidate  = entry.getKey();
                }
            }

            // CREEK FRINGE — dry terrain with meaningful river influence.
            // Skip creek and use the actual land biome if it is cold/northern:
            // a warm green creek strip next to snow looks completely wrong.
            if (riverInfluence >= CREEK_FRINGE_THRESHOLD
                    || (riverInfluence > 0f && RIVER_BIOME_IDS.contains(winner))) {

                if (landCandidate != null && COLD_BIOME_IDS.contains(landCandidate)) {
                    // Cold bank — keep the snowy biome right up to the river.
                    winner = landCandidate;
                } else {
                    winner = CREEK_BIOME_ID;
                }

            } else if (WATER_BIOME_IDS.contains(winner)) {
                // CONTAINMENT — non-river water (ocean/lake) bled onto dry terrain
                // with zero river influence.  Promote to best land candidate.
                if (landCandidate != null) winner = landCandidate;
                // Fully surrounded by water pixels — keep the water winner.
            }
        }

        // ── PROCEDURAL CREEK CHECK ────────────────────────────────────────────
        // After the biomemap fringe logic, run the noise-based creek channel system.
        // We pass the riverInfluence already computed above — it's the bicubic-weighted
        // sum of river pixel votes, so it's smooth, domain-warped, and perfectly
        // consistent with how biomes are placed. No redundant biomemap lookup needed.
        if (!WATER_BIOME_IDS.contains(winner) && !CREEK_BIOME_ID.equals(winner)) {
            if (CreekResolver.isCreek(winner, riverInfluence, worldX, worldZ)) {
                winner = CREEK_BIOME_ID;
            }
        }
        // ─────────────────────────────────────────────────────────────────────

        // ── SUB-BIOME CHECK ───────────────────────────────────────────────────
        // After all map-based and creek/containment logic has resolved the winner,
        // ask the SubbiomeResolver whether a smaller procedural biome should be
        // placed here instead.  Water biomes are excluded so creek / river / ocean
        // cells are never accidentally overridden (unless the user explicitly adds
        // them as parent biomes in subbiomes.json, which is a deliberate choice).
        if (!WATER_BIOME_IDS.contains(winner)) {
            String subbiome = SubbiomeResolver.resolve(winner, worldX, worldZ);
            if (subbiome != null) {
                winner = subbiome;
            }
        }
        // ─────────────────────────────────────────────────────────────────────

        ResourceLocation loc = ResourceLocation.tryParse(winner);
        if (loc == null) return fallback;
        Holder<Biome> h = locationToHolder.get(loc);
        return h != null ? h : fallback;
    }

    private static float cubicBsplineWeight(int i, float t) {
        float t2 = t * t;
        float t3 = t2 * t;
        switch (i) {
            case -1: return (1f - 3f*t + 3f*t2 - t3) / 6f;
            case  0: return (4f - 6f*t2 + 3f*t3) / 6f;
            case  1: return (1f + 3f*t + 3f*t2 - 3f*t3) / 6f;
            case  2: return t3 / 6f;
            default: return 0f;
        }
    }
}