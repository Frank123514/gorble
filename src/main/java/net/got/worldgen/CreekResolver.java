package net.got.worldgen;

import com.mojang.logging.LogUtils;
import net.got.worldgen.SimplexNoise;
import org.slf4j.Logger;

import java.util.Set;

/**
 * Procedurally generates creek channels that branch outward from rivers into
 * adjacent land biomes.
 *
 * <h3>Design</h3>
 * <p>Rather than re-sampling the biomemap (which would duplicate and potentially
 * disagree with the bicubic vote already done in {@link GotBiomeSource}),
 * {@code CreekResolver} receives the {@code riverInfluence} float that
 * {@code GotBiomeSource} already computes — the total bicubic weight contributed
 * by river-type pixels at this position.  This value is naturally smooth,
 * domain-warped, and consistent with how biomes are placed everywhere else.
 *
 * <p>Creek channels are carved by domain-warped <em>ridged</em> simplex noise.
 * The ridge transform ({@code 1 - |noise|}) produces thin peaked lines that
 * branch and fork like real tributaries.  The threshold is lowered by river
 * influence so channels are widest and most common near the river and thin out
 * naturally at their tips.
 *
 * <pre>
 *   effectiveThreshold = BASE_THRESHOLD - riverInfluence * INFLUENCE_SCALE
 *   ridge = 1 - |domainWarpedNoise(x, z)|
 *   isCreek = ridge {@literal >=} effectiveThreshold
 * </pre>
 *
 * <h3>Tuning knobs in this file</h3>
 * <ul>
 *   <li>{@link #BASE_THRESHOLD} — ridge value needed with zero river influence.
 *       Raise toward 1.0 for narrower tip channels; lower for wider.</li>
 *   <li>{@link #INFLUENCE_SCALE} — how much each unit of river influence lowers
 *       the threshold.  Higher = longer, more aggressive creek branches.</li>
 *   <li>{@link #MIN_RIVER_INFLUENCE} — minimum influence before creeks even
 *       start; keeps them strictly adjacent to rivers.</li>
 *   <li>{@link #CHANNEL_SCALE} — noise scale in blocks; larger = longer meanders.</li>
 *   <li>{@link #WARP_STRENGTH} — domain warp intensity; higher = more sinuous.</li>
 * </ul>
 */
public final class CreekResolver {

    private static final Logger LOGGER = LogUtils.getLogger();

    // ── Tuning ─────────────────────────────────────────────────────────────

    /**
     * Ridge noise threshold with zero river influence.
     * Ridge values are in [0,1]; the closer to 1, the narrower the channels.
     * Lowered from 0.68 → 0.62 so creek fingers reach a bit further into land.
     * Note: this only affects land biome cells — river biomes are handled
     * separately by GotBiomeSource and are unaffected by this value.
     */
    private static final double BASE_THRESHOLD = 0.62;

    /**
     * How much each unit of riverInfluence lowers the effective threshold.
     * riverInfluence from GotBiomeSource typically peaks around 0.15-0.25
     * right next to a river, so INFLUENCE_SCALE=2.5 gives a threshold drop
     * of ~0.38-0.63 at the river edge — essentially always creek right next
     * to the river, thinning to narrow channels far out.
     */
    private static final double INFLUENCE_SCALE = 2.8;

    /**
     * Minimum river influence before creek channels can appear at all.
     * Prevents stray creek pixels far from any river.
     * The creek-fringe check in GotBiomeSource uses 0.10 as its threshold,
     * so setting this slightly below that blends the two systems together.
     */
    private static final double MIN_RIVER_INFLUENCE = 0.04;

    /** World-space noise scale for channel shapes. Larger = longer meanders. */
    private static final double CHANNEL_SCALE = 160.0;

    /** World-space scale for domain warp. Should be larger than CHANNEL_SCALE. */
    private static final double WARP_SCALE = 300.0;

    /** Domain warp displacement in blocks. Higher = more sinuous bends. */
    private static final double WARP_STRENGTH = 60.0;

    /** Cold biomes where a warm muddy creek would look wrong. */
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

    // ── Live state ─────────────────────────────────────────────────────────

    private static volatile SimplexNoise noise = SimplexNoise.seeded(0L);

    private CreekResolver() {}

    // ── Seed ───────────────────────────────────────────────────────────────

    /**
     * Seeds the creek noise from the world seed.  Called by
     * {@link GotChunkGenerator#initNoise(long)}.
     */
    public static void initSeed(long worldSeed) {
        noise = SimplexNoise.seeded(worldSeed ^ 0xC4EEA75F_1D3E9B2AL);
        LOGGER.debug("[GoT] CreekResolver seeded with world seed {}", worldSeed);
    }

    // ── Query ──────────────────────────────────────────────────────────────

    /**
     * Returns {@code true} if this position should become {@code got:creek}.
     *
     * @param currentBiomeId  the land biome that would otherwise be placed here
     * @param riverInfluence  bicubic vote weight from river-type pixels at this
     *                        position, as computed by {@link GotBiomeSource}.
     *                        Range is roughly [0, 0.3]; 0 means no river nearby.
     * @param worldX          world X block coordinate
     * @param worldZ          world Z block coordinate
     */
    public static boolean isCreek(String currentBiomeId,
                                  float riverInfluence,
                                  int worldX, int worldZ) {
        // No river influence at all — can't be a creek.
        if (riverInfluence < MIN_RIVER_INFLUENCE) return false;

        // Cold biomes keep their snowy banks.
        if (COLD_BIOME_IDS.contains(currentBiomeId)) return false;

        // Effective threshold: lower near rivers (high influence), higher far away.
        double effectiveThreshold = BASE_THRESHOLD - riverInfluence * INFLUENCE_SCALE;
        // Clamp so even right next to a river there's still some channel structure.
        effectiveThreshold = Math.max(0.10, effectiveThreshold);

        // Domain-warped ridged noise — warp makes channels meander and branch.
        SimplexNoise n = noise;
        double wx = n.eval(worldX / WARP_SCALE,        worldZ / WARP_SCALE)        * WARP_STRENGTH;
        double wz = n.eval(worldX / WARP_SCALE + 17.3, worldZ / WARP_SCALE + 43.7) * WARP_STRENGTH;

        double sx = (worldX + wx) / CHANNEL_SCALE;
        double sz = (worldZ + wz) / CHANNEL_SCALE;

        // Primary ridge — thin channel lines.
        double ridge1 = 1.0 - Math.abs(n.eval(sx, sz));

        // Second octave for branching detail.
        double ridge2 = 1.0 - Math.abs(n.eval(sx * 2.1 + 5.3, sz * 2.1 + 11.7));

        double combined = ridge1 * 0.72 + ridge2 * 0.28;

        return combined >= effectiveThreshold;
    }
}