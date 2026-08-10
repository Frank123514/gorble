package net.got.worldgen;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public final class GotChunkGenerator extends ChunkGenerator {

    // ── Constants ──────────────────────────────────────────────────────────

    public static final int SEA_LEVEL = 63;

    // Fractal terrain noise — multiple octaves, each double the frequency
    // and roughly half the amplitude of the last. Two isolated layers
    // (280/240 "base" + 80 "detail") left a whole band of scale with no
    // contribution at all — nothing between "broad rolling shape" and
    // "80-block ridges" — which is exactly what read as smooth up close:
    // there was no noise actually operating at the 10-60 block range a
    // player walks around and looks at. Stacking real octaves fills that
    // gap at every scale simultaneously instead of just two discrete ones.
    private static final double  BASE_NOISE_SCALE_X = 280.0; // scale of the lowest (broadest) octave
    private static final double  BASE_NOISE_SCALE_Z = 240.0;
    private static final int     NOISE_OCTAVES      = 5;     // 280 → 140 → 70 → 35 → 17.5 block scales
    private static final double  NOISE_LACUNARITY    = 2.0;  // frequency multiplier per octave
    private static final double  NOISE_PERSISTENCE   = 0.5;  // amplitude multiplier per octave

    // One permutation table per octave so adjacent octaves (which land on
    // exact power-of-two multiples of each other's frequency) don't share
    // a gradient pattern — that correlation shows up as faint repeating
    // alignment between octaves otherwise.
    private static volatile short[][] noiseOctavePerms = buildOctavePerms(0L);

    // ── Subbiome height blend ────────────────────────────────────────────
    // Exponent applied to the subbiome's own [0,1] noise value to get a
    // continuous (never a hard edge) height-blend weight — see
    // computeRawSurfaceY. Higher = boost concentrates into fewer, more
    // pronounced peaks with a wider gentle falloff around them; lower =
    // more of the biome gets a mild lift. 4-6 reads as "occasional broad
    // hills," not "the whole biome is a bit bumpy."
    private static final double HEIGHT_BLEND_CURVE = 5.0;

    // How much of the ramp climb's height gain a ridge "valley" (the saddle
    // between two branches of a mountain's skeleton, where ridgeWeight
    // bottoms out at 0) keeps at minimum. 1.0 would mean ridgeWeight does
    // nothing; 0.0 would let valleys collapse all the way to FOOT_HEIGHT
    // (near sea level), turning every saddle into a lake once the mountain's
    // own large heightVariation is layered on top. Low enough that valleys
    // and passes read as real, prevalent, snaking low ground cutting through
    // the range rather than a barely-dipped plateau.
    private static final float RIDGE_VALLEY_FLOOR = 0.3f;

    // The skeleton/ridgeline itself only climbs to this fraction of the
    // full configured height — NOT all the way to 1.0. Reserving that
    // remaining headroom for peakWeight (see computeRawSurfaceY) is what
    // turns the ridgeline from one continuous wall at uniform crest height
    // into a lower connecting ridge with distinct summits poking above it.
    private static final float RIDGE_SHOULDER_FRACTION = 0.75f;

    // Height-variation amplitude applied at a mountain pixel's own edge
    // (rampWeight ≈ 0), before cellH has had any distance to lerp toward
    // FOOT_HEIGHT. A biome only qualifies as "mountain" in the first place
    // once its heightVariation >= MountainSlopemapResolver.MOUNTAIN_VARIATION_THRESHOLD
    // (30+) — and until now that full amplitude applied unchanged right at
    // the mountain's own foot, on top of a cellH that had already been
    // lerped almost all the way down to FOOT_HEIGHT (66, barely above
    // SEA_LEVEL 63). One column of noise landing near -1 there subtracts
    // most/all of that 30+ variation from a base height with almost no
    // headroom left to absorb it, carving the terrain below sea level and
    // filling the dip with a lake — exactly the pools visible at the base
    // of the mountains in-game. Ramping heightVariation itself up
    // alongside cellH — mild right at the edge, the mountain's full
    // configured amplitude only once the ramp is complete — keeps the
    // smooth foot actually smooth instead of secretly still rolling with
    // full mountain-scale noise on top of foot-scale height.
    private static final float FOOT_HEIGHT_VARIATION = 8f;

    // ── Codec ──────────────────────────────────────────────────────────────

    public static final MapCodec<GotChunkGenerator> CODEC =
            RecordCodecBuilder.mapCodec(i -> i.group(
                    BiomeSource.CODEC
                            .fieldOf("biome_source")
                            .forGetter(ChunkGenerator::getBiomeSource),
                    NoiseGeneratorSettings.CODEC
                            .fieldOf("settings")
                            .forGetter(g -> g.settings),
                    com.mojang.serialization.Codec.INT
                            .optionalFieldOf("spawn_pixel_x", -1)
                            .forGetter(g -> g.spawnPixelX),
                    com.mojang.serialization.Codec.INT
                            .optionalFieldOf("spawn_pixel_z", -1)
                            .forGetter(g -> g.spawnPixelZ)
            ).apply(i, GotChunkGenerator::new));

    // ── Fields ─────────────────────────────────────────────────────────────

    private final Holder<NoiseGeneratorSettings> settings;
    private final NoiseBasedChunkGenerator       vanilla;
    private final int spawnPixelX;
    private final int spawnPixelZ;

    private static volatile int configuredSpawnPixelX = -1;
    private static volatile int configuredSpawnPixelZ = -1;

    // ── Inline simplex noise ───────────────────────────────────────────────
    private static final double F2 = 0.5 * (Math.sqrt(3.0) - 1.0);
    private static final double G2 = (3.0 - Math.sqrt(3.0)) / 6.0;
    private static final double[][] GRAD2 = {
            { 1, 1}, {-1, 1}, { 1,-1}, {-1,-1},
            { 1, 0}, {-1, 0}, { 0, 1}, { 0,-1}
    };
    private static volatile double  noiseOffX = 0, noiseOffZ = 0;

    // ── Constructor ────────────────────────────────────────────────────────

    public GotChunkGenerator(BiomeSource biomeSource,
                             Holder<NoiseGeneratorSettings> settings,
                             int spawnPixelX, int spawnPixelZ) {
        super(biomeSource);
        this.settings     = settings;
        this.spawnPixelX  = spawnPixelX;
        this.spawnPixelZ  = spawnPixelZ;
        this.vanilla      = new NoiseBasedChunkGenerator(biomeSource, settings);
        configuredSpawnPixelX = spawnPixelX;
        configuredSpawnPixelZ = spawnPixelZ;
    }

    public static int getConfiguredSpawnPixelX() { return configuredSpawnPixelX; }
    public static int getConfiguredSpawnPixelZ() { return configuredSpawnPixelZ; }

    public static void initNoise(long worldSeed) {
        noiseOctavePerms = buildOctavePerms(worldSeed);
        noiseOffX = ((worldSeed       & 0xFFFFL) / 65536.0) * 1000.0;
        noiseOffZ = ((worldSeed >> 16 & 0xFFFFL) / 65536.0) * 1000.0;
        SubbiomeResolver.initSeed(worldSeed);
        SlopeSurfaceResolver.initSeed(worldSeed);
    }

    @Override
    protected @NotNull MapCodec<? extends ChunkGenerator> codec() { return CODEC; }

    // ── fillFromNoise ──────────────────────────────────────────────────────

    @Override
    public @NotNull CompletableFuture<ChunkAccess> fillFromNoise(
            @NotNull Blender blender, @NotNull RandomState random,
            @NotNull StructureManager structures, @NotNull ChunkAccess chunk) {

        NoiseSettings ns = settings.value().noiseSettings();
        int minY  = ns.minY();
        int maxY  = minY + ns.height();
        int sea   = getSeaLevel();
        ChunkPos cp = chunk.getPos();

        List<StructureStart> nearbyStructures = structures.startsForStructure(
                SectionPos.bottomOf(chunk).chunk(), structure -> true);

        for (int lx = 0; lx < 16; lx++) {
            for (int lz = 0; lz < 16; lz++) {
                int wx = cp.getBlockX(lx);
                int wz = cp.getBlockZ(lz);
                int surfaceY = Mth.floor(computeBlendedSurfaceY(wx, wz, nearbyStructures));

                for (int y = minY; y < maxY; y++) {
                    BlockState state;
                    if (y > surfaceY) {
                        state = y <= sea
                                ? settings.value().defaultFluid()
                                : Blocks.AIR.defaultBlockState();
                    } else {
                        state = Blocks.STONE.defaultBlockState();
                    }
                    chunk.setBlockState(new BlockPos(lx, y, lz), state, 3);
                }
            }
        }
        return CompletableFuture.completedFuture(chunk);
    }

    // ── buildSurface ────────────────────────────────────────────────────────

    private static final Logger LOGGER = LogUtils.getLogger();
    private static volatile boolean loggedBuildSurface = false;

    @Override
    public void buildSurface(WorldGenRegion region, StructureManager structures,
                             RandomState random, ChunkAccess chunk) {
        if (!loggedBuildSurface) {
            loggedBuildSurface = true;
            ChunkPos cp0 = chunk.getPos();
            LOGGER.info("[GoT][DEBUG] GotChunkGenerator.buildSurface: FIRST CALL — dimension={}, chunk=({},{})",
                    region.getLevel().dimension().location(), cp0.x, cp0.z);
        }

        vanilla.buildSurface(region, structures, random, chunk);
        RoadWorldGen.buildRoadsInChunk(chunk);
        RoadWorldGen.clearVegetationFromRoads(chunk);
        WallWorldGen.buildWallInChunk(chunk);
        SlopeSurfaceResolver.applySlopeBlocks(chunk, region);
    }

    // ── Structure placement — water avoidance ───────────────────────────────
    // vanilla/NeoForge don't expose a generic "keep structures away from
    // water" knob in structure_set/structure JSON — the only built-in filter
    // is a biome check at a single anchor point, which doesn't help here
    // since a puddle/pond sits inside the same biome as the dry land around
    // it. So: let vanilla place structures as normal, then veto any start
    // whose footprint (expanded by a buffer) overlaps a flooded column.

    /** Buffer (blocks) added around a structure's bounding box when checking for nearby water. */
    private static final int STRUCTURE_WATER_AVOID_RADIUS = 12;
    /** How many columns to sample per axis across the (expanded) bounding box. */
    private static final int STRUCTURE_WATER_SAMPLE_STEPS = 12;

    @Override
    public void createStructures(@NotNull RegistryAccess registryAccess, @NotNull ChunkGeneratorStructureState structureState,
                                 @NotNull StructureManager structures, @NotNull ChunkAccess chunk,
                                 @NotNull StructureTemplateManager templateManager, @NotNull ResourceKey<Level> dimension) {
        super.createStructures(registryAccess, structureState, structures, chunk, templateManager, dimension);

        for (Map.Entry<Structure, StructureStart> entry : chunk.getAllStarts().entrySet()) {
            StructureStart start = entry.getValue();
            if (start == null || !start.isValid()) continue;

            if (isNearWater(start.getBoundingBox())) {
                LOGGER.info("[GoT] Cancelled structure {} at {} — too close to water",
                        entry.getKey(), start.getBoundingBox());
                chunk.setStartForStructure(entry.getKey(), StructureStart.INVALID_START);
            }
        }
    }

    /** True if any sampled column within {@code box} (expanded by the avoid radius) is flooded. */
    private boolean isNearWater(BoundingBox box) {
        int minX = box.minX() - STRUCTURE_WATER_AVOID_RADIUS;
        int maxX = box.maxX() + STRUCTURE_WATER_AVOID_RADIUS;
        int minZ = box.minZ() - STRUCTURE_WATER_AVOID_RADIUS;
        int maxZ = box.maxZ() + STRUCTURE_WATER_AVOID_RADIUS;
        int sea  = getSeaLevel();

        int stepX = Math.max(1, (maxX - minX) / STRUCTURE_WATER_SAMPLE_STEPS);
        int stepZ = Math.max(1, (maxZ - minZ) / STRUCTURE_WATER_SAMPLE_STEPS);

        for (int x = minX; x <= maxX; x += stepX) {
            for (int z = minZ; z <= maxZ; z += stepZ) {
                if (computeSurfaceY(x, z) <= sea) return true;
            }
        }
        return false;
    }

    // ── Surface height ─────────────────────────────────────────────────────

    /**
     * Per-thread memo cache for {@link #computeRawSurfaceY}. The same
     * (worldX, worldZ) column gets recomputed from scratch by several
     * independent call sites during normal generation — getBaseHeight,
     * getBaseColumn, SlopeSurfaceResolver's gradient sampling, the biome
     * source's containment/shore checks, road/wall scanning — each doing
     * the full 16-cell bicubic blend + subbiome/slopemap work again. This
     * cache lets repeat calls for a coordinate that's already been computed
     * (by this thread) return instantly instead of redoing all of that.
     *
     * <p>Bounded + access-ordered (LRU) rather than a single last-value slot,
     * since call sites interleave several distinct nearby coordinates (e.g.
     * gradient sampling at x±3, z±3) rather than repeating the exact same
     * one back-to-back. ThreadLocal because chunk generation runs many
     * worker threads concurrently and this must not be shared/synchronized
     * across them.
     */
    private static final int RAW_SURFACE_CACHE_CAPACITY = 256;

    private static final ThreadLocal<LinkedHashMap<Long, Float>> RAW_SURFACE_CACHE =
            ThreadLocal.withInitial(() -> new LinkedHashMap<>(RAW_SURFACE_CACHE_CAPACITY * 4 / 3, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<Long, Float> eldest) {
                    return size() > RAW_SURFACE_CACHE_CAPACITY;
                }
            });

    private static long packColumnKey(int worldX, int worldZ) {
        return ((long) worldX << 32) ^ (worldZ & 0xFFFFFFFFL);
    }

    public static int computeSurfaceY(int worldX, int worldZ) {
        return Mth.floor(computeRawSurfaceY(worldX, worldZ));
    }

    public static float computeRawSurfaceY(int worldX, int worldZ) {
        if (!BiomemapLoader.isLoaded()) return SEA_LEVEL;

        Map<Long, Float> cache = RAW_SURFACE_CACHE.get();
        long key = packColumnKey(worldX, worldZ);
        Float cached = cache.get(key);
        if (cached != null) return cached;

        float result = computeRawSurfaceYUncached(worldX, worldZ);
        cache.put(key, result);
        return result;
    }

    private static float computeRawSurfaceYUncached(int worldX, int worldZ) {
        float cx = worldX / (float) BiomemapLoader.MAP_SCALE
                + BiomemapLoader.getWidth()  * 0.5f;
        float cz = worldZ / (float) BiomemapLoader.MAP_SCALE
                + BiomemapLoader.getHeight() * 0.5f;

        int   ipx = (int) Math.floor(cx);
        int   ipz = (int) Math.floor(cz);
        float fx  = cx - ipx;
        float fz  = cz - ipz;

        float[] h = new float[16];
        float[] v = new float[16];

        // Subbiome height is blended in PER CELL, before the bicubic pass,
        // not applied once afterward from a single "nearest" pixel.
        //
        // Each of the 16 surrounding biomemap pixels can belong to a
        // different parent biome, each with its own subbiome list (e.g.
        // got:north_hills has different noise_scale/base_height under
        // got:north vs. got:wolfswood). If we only sampled the single
        // nearest pixel's parent, that parent — and therefore the whole
        // noise_scale/base_height it blends toward — would flip the
        // instant "nearest" crosses a biomemap pixel boundary, producing
        // a hard seam right at that boundary even though the base
        // terrain around it is smoothly bicubic-interpolated. Resolving
        // the override separately for each of the 16 cells (using that
        // cell's own parent, but the noise sampled at the true world
        // position so patch shape stays pixel-accurate) means the
        // bicubic pass smooths across parent borders exactly the same
        // way it already smooths the plain terrain.
        //
        // The override itself is still NOT a threshold cutoff — a hard
        // "in the patch or not" test always looks like a stamped-down
        // plateau no matter how wide the edge blend is, since everything
        // past that edge is still 100% one value or the other. Instead
        // the extra height is blended in proportionally to the raw noise
        // value itself, continuously, same as the base terrain shape.
        // HEIGHT_BLEND_CURVE pulls that curve toward the noise field's
        // peaks (via Math.pow) so most of the biome stays close to normal
        // and the boost concentrates into broad, gently-rounded rises —
        // real hills, not the whole biome getting uniformly bumpy.
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                int px = ipx + col - 1;
                int pz = ipz + row - 1;
                GotBiomeTerrainParams.Params p = paramsAt(px, pz);

                float cellH = p.baseHeight();
                float cellV = p.heightVariation();

                if (!p.isWater()) {
                    float[] noiseOut = { -1f };
                    SubbiomeDef sub = SubbiomeResolver.resolveTerrain(
                            p.biomeId(), worldX, worldZ, noiseOut);

                    if (sub != null && noiseOut[0] >= 0f) {
                        float weight = (float) Math.pow(Mth.clamp(noiseOut[0], 0f, 1f), HEIGHT_BLEND_CURVE);

                        float subH = sub.baseHeight()      >= 0 ? sub.baseHeight()      : cellH;
                        float subV = sub.heightVariation() >= 0 ? sub.heightVariation() : cellV;

                        cellH = Mth.lerp(weight, cellH, subH);
                        cellV = Mth.lerp(weight, cellV, subV);
                    }
                }

                // ── Mountain slopemap ────────────────────────────────────
                // Ramp THIS cell's own contribution to the height field
                // between a modest foot height (right at the mountain's
                // edge) and its own full configured peak (deep inside the
                // blob), based on how far this specific biomemap pixel
                // sits from the nearest non-mountain edge. Doing this here,
                // per cell, before the bicubic pass below, means the long
                // slopemap climb and the ordinary short-range biome-border
                // blend are the same continuous field — not two separate
                // systems that can each independently pull toward the peak
                // value and produce a cliff. Non-mountain pixels get a ramp
                // weight of 0 and are completely unaffected.
                if (MountainSlopemapResolver.isLoaded()) {
                    float rampWeight = MountainSlopemapResolver.rampWeight(px, pz);
                    if (rampWeight > 0f) {
                        cellH = Mth.lerp(rampWeight, MountainSlopemapResolver.FOOT_HEIGHT, cellH);
                        cellV = Mth.lerp(rampWeight, FOOT_HEIGHT_VARIATION, cellV);

                        // ridgeWeight alone would fall back to 0 in the saddle
                        // between two ridge branches of a wide blob — but
                        // lerping the FULL height there straight to FOOT_HEIGHT
                        // (barely above sea level) turns every saddle into a
                        // giant lake, since the mountain's own large
                        // heightVariation is still active on top of it. A real
                        // saddle is still elevated, just lower than the crest —
                        // so only taper the portion of height the ramp climb
                        // already earned above the foot, and keep a floor
                        // (RIDGE_VALLEY_FLOOR) so the least-ridgey point in the
                        // interior still keeps most of that climb instead of
                        // collapsing toward the foot. The ridgeline itself is
                        // also capped at RIDGE_SHOULDER_FRACTION rather than
                        // the full 1.0 — the remaining headroom belongs to
                        // peakWeight below, so the crest reads as a lower
                        // connecting ridge, not a wall at uniform height.
                        float ridgeWeight  = MountainSlopemapResolver.ridgeWeight(px, pz);
                        float ridgeFactor  = RIDGE_VALLEY_FLOOR
                                + (RIDGE_SHOULDER_FRACTION - RIDGE_VALLEY_FLOOR) * ridgeWeight;

                        // Mountain passes: occasional low gaps cut into the
                        // connecting ridge crest itself — the kind of saddle
                        // a road actually threads through — rather than into
                        // the open flanks. Gating by ridgeWeight keeps the
                        // cut localized to the crest; pulling toward
                        // RIDGE_VALLEY_FLOOR (not below it) keeps a pass a
                        // walkable gap rather than a notch to sea level.
                        float passWeight = MountainSlopemapResolver.passWeight(px, pz) * ridgeWeight;
                        ridgeFactor = ridgeFactor + (RIDGE_VALLEY_FLOOR - ridgeFactor) * passWeight;

                        // Spend the headroom the ridgeline left unclaimed
                        // (1 - ridgeFactor) on peakWeight, which is 1 only at
                        // a handful of selected summit points and falls off
                        // LINEARLY (an actual pointed pyramid, not a
                        // smoothstepped dome) — so only real summits reach
                        // full configured height; everywhere else settles
                        // onto the lower connecting ridge/valley beneath it.
                        float peakWeight   = MountainSlopemapResolver.peakWeight(px, pz);
                        float totalFactor  = ridgeFactor + peakWeight * (1f - ridgeFactor);

                        // Parallel sub-ridges: a wide mountain blob folds
                        // into several roughly-parallel ridgelines instead
                        // of one flat interior plateau, tracing the blob's
                        // own edge contour (see foldWeight) — narrow spurs
                        // barely complete one fold period and stay smooth,
                        // wide ranges fit several and read as a proper
                        // range. Scaled by rampWeight so it fades in from
                        // the border instead of starting sharp right at the
                        // mountain's edge.
                        float foldWeight = MountainSlopemapResolver.foldWeight(px, pz);
                        float foldFactor = 1f - MountainSlopemapResolver.FOLD_STRENGTH * rampWeight * (1f - foldWeight);
                        totalFactor *= foldFactor;

                        cellH = MountainSlopemapResolver.FOOT_HEIGHT
                                + (cellH - MountainSlopemapResolver.FOOT_HEIGHT) * totalFactor;
                    }
                }

                h[row * 4 + col] = cellH;
                v[row * 4 + col] = cellV;
            }
        }

        float rawHeight       = bicubicBspline(h, fx, fz);
        float heightVariation = bicubicBspline(v, fx, fz);

        double ox = noiseOffX + worldX;
        double oz = noiseOffZ + worldZ;

        double noiseVal = computeTerrainNoise(ox, oz);

        float finalHeight = rawHeight + (float) noiseVal * heightVariation;

        return finalHeight;
    }

    // ── Structure terrain blend ───────────────────────────────────────────
    // Vanilla (and LOTR, which still runs vanilla 1.16's own structure-beard
    // code almost verbatim) blends structures into terrain by modifying the
    // 3D noise DENSITY field near a piece, using a ~12-block-radius
    // gaussian-like falloff kernel. We don't have a density field — this
    // generator picks one surface height per column and fills solid below
    // it — so we approximate the same visual result by blending the target
    // HEIGHT radially instead, but we now match vanilla's actual radius
    // (12, not 6) and use a smooth gaussian-style falloff instead of a
    // linear smoothstep, since a narrow linear blend reads as an abrupt
    // slope/cliff right at the edge rather than a gradual rise into the
    // structure.
    //
    // The blend target used to just be `box.minY() - 1`, which assumes the
    // piece sits ON TOP of untouched natural terrain (true for e.g. the
    // watchtower/windmill, which don't capture any ground blocks of their
    // own). That assumption breaks for a structure like the hamlet, which
    // has its own dirt/grass foundation baked directly into row 0 of its
    // NBT (box.minY() itself IS the walkable grass surface, not the natural
    // terrain a fixed 1 block below it). Blending toward box.minY()-1 in
    // that case digs a moat exactly 1 block deep around the whole structure
    // instead of meeting its actual surface.
    //
    // `ground_level_delta` (set per template_pool element, default 1) is
    // the existing vanilla-standard way of describing how many blocks of a
    // piece are "buried" below its own natural ground line. We reuse it
    // here instead of inventing new per-structure config: delta=1 (the
    // default, used by watchtower/windmill) reproduces the old box.minY()-1
    // behaviour exactly; a structure with its own captured ground layer at
    // row 0 sets delta=2 in its start_pool.json so the blend meets that
    // layer directly instead of tunnelling under it.
    //
    // IMPORTANT: this only affects chunks generated AFTER this change.
    // Already-generated chunks are baked permanently — testing needs to
    // happen in a fresh, never-loaded area (or a new world) to see it.

    private static final float STRUCTURE_PAD_RADIUS = 12f; // matches vanilla's actual beard radius

    private static float computeBlendedSurfaceY(int wx, int wz, List<StructureStart> starts) {
        float naturalY = computeRawSurfaceY(wx, wz);
        if (starts.isEmpty()) return naturalY;

        float blended = naturalY;
        for (StructureStart start : starts) {
            for (StructurePiece piece : start.getPieces()) {
                BoundingBox box = piece.getBoundingBox();
                float dist = distanceToBox(wx, wz, box);
                if (dist >= STRUCTURE_PAD_RADIUS) continue;

                // Gaussian-style falloff instead of linear smoothstep — stays
                // close to 1.0 (fully the structure's floor) for a while near
                // the piece, then eases out gradually, rather than a straight
                // ramp across the whole radius. Same shape family vanilla's
                // own beard kernel uses (exp of squared distance).
                float t      = dist / STRUCTURE_PAD_RADIUS; // 0..1
                float weight = (float) Math.exp(-(t * t) * 4.0);

                int groundLevelDelta = 1; // vanilla default — matches old hardcoded behaviour
                if (piece instanceof PoolElementStructurePiece pep) {
                    groundLevelDelta = pep.getElement().getGroundLevelDelta();
                }
                float floorY = box.minY() + groundLevelDelta - 2;

                blended = Mth.lerp(weight, blended, floorY);
            }
        }
        return blended;
    }

    private static float distanceToBox(int x, int z, BoundingBox box) {
        float dx = Math.max(Math.max(box.minX() - x, 0), x - box.maxX());
        float dz = Math.max(Math.max(box.minZ() - z, 0), z - box.maxZ());
        return Mth.sqrt(dx * dx + dz * dz);
    }

    // ── Bicubic B-spline ───────────────────────────────────────────────────

    static float bicubicBspline(float[] grid, float fx, float fz) {
        float r0 = cubicBspline1D(grid[0],  grid[1],  grid[2],  grid[3],  fx);
        float r1 = cubicBspline1D(grid[4],  grid[5],  grid[6],  grid[7],  fx);
        float r2 = cubicBspline1D(grid[8],  grid[9],  grid[10], grid[11], fx);
        float r3 = cubicBspline1D(grid[12], grid[13], grid[14], grid[15], fx);
        return cubicBspline1D(r0, r1, r2, r3, fz);
    }

    private static float cubicBspline1D(float p0, float p1, float p2, float p3, float t) {
        float t2 = t * t;
        float t3 = t2 * t;
        return (1f / 6f) * (
                (-p0 + 3f * p1 - 3f * p2 + p3) * t3
                        + ( 3f * p0 - 6f * p1 + 3f * p2)  * t2
                        + (-3f * p0              + 3f * p2)  * t
                        + (       p0 + 4f * p1  +       p2)
        );
    }

    // ── Helpers ────────────────────────────────────────────────────────────

    private static GotBiomeTerrainParams.Params paramsAt(int px, int pz) {
        if (px < 0 || pz < 0
                || px >= BiomemapLoader.getWidth()
                || pz >= BiomemapLoader.getHeight()) {
            return GotBiomeTerrainParams.FALLBACK;
        }
        return GotBiomeTerrainParams.forColor(BiomemapLoader.getRawPixel(px, pz));
    }

    // ── ChunkGenerator boilerplate ─────────────────────────────────────────

    @Override
    public int getBaseHeight(int x, int z, Heightmap.@NotNull Types type,
                             @NotNull LevelHeightAccessor level,
                             @NotNull RandomState random) {
        int surface = computeSurfaceY(x, z);
        // WORLD_SURFACE(_WG) means "top of whatever occupies this column,
        // fluids included" — vanilla semantics treat water as non-air, so a
        // flooded column's surface is the water level, not the lakebed.
        // Returning the raw stone floor here was the root cause of
        // structures (which project onto this heightmap) landing at pond-
        // bottom elevation instead of on dry ground.
        if (type == Heightmap.Types.WORLD_SURFACE || type == Heightmap.Types.WORLD_SURFACE_WG) {
            int sea = getSeaLevel();
            if (surface < sea) return sea;
        }
        return surface;
    }

    @Override
    public @NotNull NoiseColumn getBaseColumn(int x, int z,
                                              @NotNull LevelHeightAccessor level,
                                              @NotNull RandomState random) {
        int minY    = level.getMinY();
        int surface = computeSurfaceY(x, z);
        int sea     = getSeaLevel();

        BlockState[] states = new BlockState[level.getHeight()];
        for (int i = 0; i < states.length; i++) {
            int y = minY + i;
            states[i] = y <= surface ? Blocks.STONE.defaultBlockState()
                    : y <= sea       ? settings.value().defaultFluid()
                    : Blocks.AIR.defaultBlockState();
        }
        return new NoiseColumn(minY, states);
    }

    @Override
    public void applyCarvers(@NotNull WorldGenRegion region, long seed,
                             @NotNull RandomState random,
                             @NotNull BiomeManager biomeManager,
                             @NotNull StructureManager structures,
                             @NotNull ChunkAccess chunk) {
        vanilla.applyCarvers(region, seed, random, biomeManager, structures, chunk);
    }

    @Override
    public void spawnOriginalMobs(@NotNull WorldGenRegion region) {
        ChunkPos pos = region.getCenter();
        Holder<Biome> b = region.getBiome(
                pos.getWorldPosition().atY(region.getMaxY() - 1));
        WorldgenRandom rand = new WorldgenRandom(RandomSource.create());
        rand.setDecorationSeed(region.getSeed(),
                pos.getMinBlockX(), pos.getMinBlockZ());
        NaturalSpawner.spawnMobsForChunkGeneration(region, b, pos, rand);
    }

    @Override public int getSeaLevel() { return SEA_LEVEL; }
    @Override public int getMinY()     { return settings.value().noiseSettings().minY(); }
    @Override public int getGenDepth() { return settings.value().noiseSettings().height(); }

    @Override
    public void addDebugScreenInfo(java.util.List<String> info,
                                   RandomState random, BlockPos pos) {
        if (!BiomemapLoader.isLoaded()) return;

        float cx = pos.getX() / (float) BiomemapLoader.MAP_SCALE
                + BiomemapLoader.getWidth()  * 0.5f;
        float cz = pos.getZ() / (float) BiomemapLoader.MAP_SCALE
                + BiomemapLoader.getHeight() * 0.5f;
        int px = Math.round(cx);
        int pz = Math.round(cz);

        GotBiomeTerrainParams.Params p =
                GotBiomeTerrainParams.forColor(BiomemapLoader.getRawPixel(px, pz));
        float rawY  = computeRawSurfaceY(pos.getX(), pos.getZ());
        int   surfY = Mth.floor(rawY);

        info.add(String.format(
                "[GoT] Y=%d  raw=%.1f  nearestPixelBase=%.0f  px=(%d,%d)",
                surfY, rawY, p.baseHeight(), px, pz));
        info.add("[GoT] " + SlopeSurfaceResolver.debugInfo(p.biomeId(), pos.getX(), pos.getZ()));
    }

    // ── Inline 2-D simplex helpers ─────────────────────────────────────────

    /**
     * The same multi-octave fBm noise used for terrain shape (see
     * {@link #computeSurfaceY}), exposed so other resolvers can sample the
     * exact same field instead of maintaining their own separate noise
     * instances. {@code x}/{@code z} should already include the world-seed
     * offset ({@code noiseOffX}/{@code noiseOffZ} + world coords) — pass
     * raw world coordinates plus that offset, same as computeSurfaceY does
     * internally.
     */
    public static double computeTerrainNoise(double x, double z) {
        short[][] perms = noiseOctavePerms;
        double frequency = 1.0;
        double amplitude = 1.0;
        double sum        = 0.0;
        double maxAmplitude = 0.0;

        for (int o = 0; o < NOISE_OCTAVES; o++) {
            double nx = x / BASE_NOISE_SCALE_X * frequency;
            double nz = z / BASE_NOISE_SCALE_Z * frequency;
            sum += simplexEval(perms[o], nx, nz) * amplitude;
            maxAmplitude += amplitude;
            amplitude *= NOISE_PERSISTENCE;
            frequency *= NOISE_LACUNARITY;
        }

        return sum / maxAmplitude; // normalized back to roughly [-1, 1]
    }

    /** Same as {@link #computeTerrainNoise(double, double)} but takes raw
     *  world coordinates directly (applies the world-seed offset itself) —
     *  the convenient entry point for external callers. */
    public static double computeTerrainNoiseAtWorldPos(double worldX, double worldZ) {
        return computeTerrainNoise(noiseOffX + worldX, noiseOffZ + worldZ);
    }

    private static short[][] buildOctavePerms(long worldSeed) {
        short[][] perms = new short[NOISE_OCTAVES][];
        for (int o = 0; o < NOISE_OCTAVES; o++) {
            perms[o] = buildPerm(worldSeed ^ (0x9E3779B97F4A7C15L * (o + 1)));
        }
        return perms;
    }

    private static short[] buildPerm(long seed) {
        short[] p = new short[256];
        for (short i = 0; i < 256; i++) p[i] = i;
        long rng = seed ^ 0x6C62272E07BB0142L;
        for (int i = 255; i > 0; i--) {
            rng = rng * 6364136223846793005L + 1442695040888963407L;
            int j = (int) ((rng >>> 33) % (i + 1));
            short tmp = p[i]; p[i] = p[j]; p[j] = tmp;
        }
        short[] perm = new short[512];
        for (int i = 0; i < 512; i++) perm[i] = p[i & 255];
        return perm;
    }

    private static double simplexEval(short[] perm, double xin, double yin) {
        double s  = (xin + yin) * F2;
        int i     = (int) Math.floor(xin + s);
        int j     = (int) Math.floor(yin + s);
        double t  = (i + j) * G2;
        double x0 = xin - (i - t), y0 = yin - (j - t);
        int i1 = x0 > y0 ? 1 : 0, j1 = x0 > y0 ? 0 : 1;
        double x1 = x0 - i1 + G2,        y1 = y0 - j1 + G2;
        double x2 = x0 - 1.0 + 2.0 * G2, y2 = y0 - 1.0 + 2.0 * G2;
        int gi0 = perm[(i      + perm[ j      & 255]) & 255] & 7;
        int gi1 = perm[(i + i1 + perm[(j + j1) & 255]) & 255] & 7;
        int gi2 = perm[(i + 1  + perm[(j + 1)  & 255]) & 255] & 7;
        double n0 = 0, n1 = 0, n2 = 0;
        double t0 = 0.5 - x0*x0 - y0*y0; if (t0 > 0) { t0*=t0; n0 = t0*t0*(GRAD2[gi0][0]*x0 + GRAD2[gi0][1]*y0); }
        double t1 = 0.5 - x1*x1 - y1*y1; if (t1 > 0) { t1*=t1; n1 = t1*t1*(GRAD2[gi1][0]*x1 + GRAD2[gi1][1]*y1); }
        double t2 = 0.5 - x2*x2 - y2*y2; if (t2 > 0) { t2*=t2; n2 = t2*t2*(GRAD2[gi2][0]*x2 + GRAD2[gi2][1]*y2); }
        return 70.0 * (n0 + n1 + n2);
    }

}