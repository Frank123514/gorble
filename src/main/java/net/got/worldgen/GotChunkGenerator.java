package net.got.worldgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
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
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * Chunk generator for the GoT mod.
 *
 * <h3>Algorithm</h3>
 * <ol>
 *   <li>Convert the world column to <em>float</em> pixel-space coordinates
 *       (no domain warp).</li>
 *   <li>Compute a <b>bicubic (Catmull-Rom) water fraction</b> by interpolating
 *       the binary water/land mask over the 4×4 pixel neighbourhood.  This
 *       produces a smooth, C¹-continuous value in [0,1] whose isolines are
 *       gently curved — eliminating the staircase edges of raw pixel checks.
 *       <ul>
 *         <li>fraction ≥ 0.99 → fully inside river/ocean → flat {@code baseY},
 *             no noise.</li>
 *         <li>fraction 0.01..0.99 → riverbank transition → Gaussian land height
 *             blended down toward the water-bed Y.</li>
 *         <li>fraction ≤ 0.01 → fully on land → normal Gaussian blend.</li>
 *       </ul></li>
 *   <li>The water-bed Y (river bed / ocean floor / deep ocean floor) is itself
 *       bicubically interpolated across the 4×4 pixel grid rather than averaged,
 *       so river→ocean and ocean→deep-ocean boundaries slope smoothly instead
 *       of snapping between discrete depth values.</li>
 *   <li>For the land path, sample a (2×{@value SAMPLE_RADIUS}+1)² pixel grid.
 *       Weight each non-water neighbour by a <b>Gaussian</b>
 *       {@code exp(−dist² / (2σ²))} where dist is the <em>continuous float</em>
 *       distance from the sample point to the pixel centre.
 *       Gaussian weighting gives the smoothest possible transition —
 *       no terracing, no snap artefacts, C∞ continuity.</li>
 *   <li>Evaluate three-octave smooth fBm noise and add the scaled result
 *       to the blended baseY.</li>
 * </ol>
 *
 * <h3>Noise frequencies</h3>
 * <pre>
 *   Smooth coarse  1/185  — broad landscape undulation
 *   Smooth mid     1/90   — hill-scale bumps
 *   Smooth fine    1/38   — surface roughness
 * </pre>
 */
public final class GotChunkGenerator extends ChunkGenerator {

    public static final int SEA_LEVEL = 61;

    // ── Neighbour sampling ────────────────────────────────────────────────

    /**
     * Pixel radius of the Gaussian sample window.
     * 1 pixel = {@value BiomemapLoader#MAP_SCALE} world blocks.
     * Radius 5 → ±280 blocks; with σ=1.2 px this still covers the full
     * relevant falloff while keeping the sharp σ meaningful.
     */
    private static final int   SAMPLE_RADIUS = 5;

    /**
     * Gaussian standard deviation for land↔land blending, in pixels.
     * σ=1.6 → weight falls off over ~2-3 pixels (≈180-270 blocks at MAP_SCALE=56).
     * Wider than before — lets mountain biomes blend into adjacent hills/plains,
     * producing the sloping flanks and natural valley floors of real alpine terrain
     * rather than hard biome walls.
     */
    private static final float GAUSSIAN_SIGMA   = 1.6f;
    private static final float GAUSSIAN_INV_2S2 = 1f / (2f * GAUSSIAN_SIGMA * GAUSSIAN_SIGMA);

    /**
     * Height-stretch exponent applied to each Gaussian land-blend weight.
     * 1.5 lets neighbouring biomes contribute meaningfully to the blended height —
     * this is what allows valleys to form between two mountain masses.
     * At 4.0 the dominant biome always won, producing flat-topped plateaus.
     * At 1.5 the blending is genuinely weighted, so terrain naturally sags
     * between peaks and rises toward their centres — passes and cols emerge
     * from the blend itself without any special-casing.
     */
    private static final float HEIGHT_STRETCH = 1.5f;

    /**
     * Bicubic water-fraction threshold that determines where the terrain switches
     * from land to water bed.  The same constant is used in {@link GotBiomeSource}
     * so biome boundaries and terrain boundaries are always in sync.
     *
     * <p>0.5 = water biome fills exactly the painted pixel area.
     * Lowering this value expands water biomes outward into the surrounding land
     * pixels — useful to widen rivers so the river biome fills the full carved bed
     * rather than stopping at the raw pixel edge.
     *
     * <ul>
     *   <li>0.50 — biome matches painted pixel exactly</li>
     *   <li>0.38 — river biome widens by ~½ pixel (≈64 blocks at MAP_SCALE=128)</li>
     *   <li>0.25 — river biome widens by ~1 full pixel (≈128 blocks)</li>
     * </ul>
     */
    public static final float WATER_THRESHOLD = 0.38f;

    // ── Terrain noise parameters ──────────────────────────────────────────
    // Five octaves of fBm give a natural rough-to-fine hierarchy.
    // The coarse octave uses ridged noise (1 - |n|) to produce sharper
    // ridgelines and valley floors rather than soft rounded bumps.
    // Quintic interpolation (6t⁵-15t⁴+10t³) zero-clamps both the first AND
    // second derivatives at lattice edges, breaking up the subtle grid
    // regularity that smooth (3t²-2t³) interpolation leaves behind.

    private static final float NZ_COARSE_FREQ  = 1f / 280f;  // broad landscape — longer wavelength for wider mountain bases
    private static final float NZ_MID_FREQ     = 1f / 110f;  // hill-scale — slightly longer for smoother faces
    private static final float NZ_DETAIL_FREQ  = 1f / 52f;   // face detail — pulled back for less jaggedness
    private static final float NZ_FINE_FREQ    = 1f / 24f;   // rock-scale bumps — softer
    private static final float NZ_MICRO_FREQ   = 1f / 12f;   // surface roughness — coarser than before

    // Octave weights — sum to 1.0. Shifted toward coarse/mid, fine/micro pulled way back
    private static final float W_COARSE  = 0.45f;
    private static final float W_MID     = 0.32f;
    private static final float W_DETAIL  = 0.14f;
    private static final float W_FINE    = 0.06f;
    private static final float W_MICRO   = 0.03f;

    // ── Valley carving noise ──────────────────────────────────────────────
    // A separate low-frequency noise is used to carve valleys and passes into
    // mountain terrain.  Where this noise is negative (roughly half the area)
    // the terrain is pushed downward, creating the snaking valleys and saddle
    // passes seen in real alpine ranges.  The carving is scaled by how high
    // the blended terrain already is — flat lowlands are barely touched,
    // while mountain peaks are carved most aggressively.
    //
    // VALLEY_FREQ controls how wide the valleys are.  1/320 → valleys
    // roughly 160-400 blocks across, consistent with a plausible mountain pass.
    // VALLEY_DEPTH is the maximum downward carve in blocks when noise = -1.
    // VALLEY_THRESHOLD is how low the noise must go before carving begins —
    // 0.0 means exactly half the terrain is carved (valley floors and ridges
    // appear in roughly equal measure, as in image 2/3).

    private static final float VALLEY_FREQ      = 1f / 320f;
    private static final float VALLEY_DEPTH     = 115f;  // max carve depth in blocks (was 85)
    private static final float VALLEY_THRESHOLD = 0.05f; // noise above this = ridge; below = valley

    // ── Domain warp parameters ────────────────────────────────────────────
    // Two-octave warp displaces the pixel-space coordinate before any biomemap
    // lookup — terrain height AND biome assignment both call warpPixelCoord()
    // so they always use the identical displaced position and stay in sync.
    //
    // Amplitudes are in *pixel* units (1 px = MAP_SCALE blocks).
    // Coarse warp: broad landscape distortion (~1 800 blocks / cycle).
    // Fine warp:   medium-scale edge roughness (~600 blocks / cycle).
    // Total max displacement ≈ 1.1 + 0.4 = 1.5 px ≈ 192 blocks — enough to
    // visibly curve biome edges and break grid alignment without
    // grossly relocating features.

    private static final float WARP_COARSE_AMP  = 0.85f;  // reduced — wider σ needs less warp to avoid over-scrambling biome edges
    private static final float WARP_COARSE_FREQ = 1f / 14f;   // in pixel space
    private static final float WARP_FINE_AMP    = 0.30f;
    private static final float WARP_FINE_FREQ   = 1f / 4.5f;  // in pixel space

    // ── Water-bed noise parameters ────────────────────────────────────────
    // Low-amplitude noise added to the bicubic waterY so river beds and ocean
    // floors have gentle undulation instead of being dead flat.
    // Amplitude is intentionally small — rivers ±3 blocks, oceans ±5 blocks.

    private static final float WB_FREQ = 1f / 55f;   // world-space frequency
    private static final float WB_AMP  = 4.5f;       // blocks of vertical variation

    // ── Multi-octave fBm ──────────────────────────────────────────────────

    /**
     * Five-octave fBm terrain noise.
     *
     * <p>Coarse octave is <em>ridged</em> ({@code 1 - |n|}) to produce sharp
     * ridgelines and defined valley floors — natural-looking rather than the
     * soft sinusoidal hills that standard value noise tends to produce.
     * Remaining octaves add progressive detail down to single-block roughness.
     *
     * @return noise in approximately [−1, 1]
     */
    private static float terrainNoise(int x, int z) {
        // Ridged coarse — maps [-1,1] → [0,1] with a sharp peak at 0
        float coarse = quinticNoise(x * NZ_COARSE_FREQ,            z * NZ_COARSE_FREQ,            0x3F9A1B);
        float ridged = 1f - Math.abs(coarse);          // sharp ridgelines
        ridged = ridged * 2f - 1f;                     // re-centre to [-1,1]

        float mid    = quinticNoise(x * NZ_MID_FREQ    + 31.7f,    z * NZ_MID_FREQ    + 17.3f,    0x7C4D2E);
        float detail = quinticNoise(x * NZ_DETAIL_FREQ + 93.1f,    z * NZ_DETAIL_FREQ + 57.9f,    0xA18C55);
        float fine   = quinticNoise(x * NZ_FINE_FREQ   + 214.3f,   z * NZ_FINE_FREQ   + 138.7f,   0xD3621F);
        float micro  = quinticNoise(x * NZ_MICRO_FREQ  + 407.9f,   z * NZ_MICRO_FREQ  + 293.5f,   0x4E8B3C);

        return ridged * W_COARSE
                + mid    * W_MID
                + detail * W_DETAIL
                + fine   * W_FINE
                + micro  * W_MICRO;
    }

    // ── Domain warp ───────────────────────────────────────────────────────

    /**
     * Displaces a float pixel-space coordinate {@code (cx, cz)} using
     * two-octave domain warp before any biomemap lookup.
     *
     * <p>Both {@link #computeSurfaceY} (terrain) and {@link GotBiomeSource}
     * (biome assignment) must call this with the same pixel coordinate so
     * that biome boundaries and terrain features are always co-located.
     *
     * @return float[2] { warpedCx, warpedCz }
     */
    public static float[] warpPixelCoord(float cx, float cz) {
        float wx = WARP_COARSE_AMP * quinticNoise(cx * WARP_COARSE_FREQ,          cz * WARP_COARSE_FREQ,          0x2B8F4A)
                + WARP_FINE_AMP   * quinticNoise(cx * WARP_FINE_FREQ   + 17.3f,  cz * WARP_FINE_FREQ   + 53.1f,  0xC74E1D);
        float wz = WARP_COARSE_AMP * quinticNoise(cx * WARP_COARSE_FREQ +  5.7f,  cz * WARP_COARSE_FREQ + 89.2f,  0x6A3FB8)
                + WARP_FINE_AMP   * quinticNoise(cx * WARP_FINE_FREQ   + 71.5f,  cz * WARP_FINE_FREQ   + 28.9f,  0xF0A235);
        return new float[]{ cx + wx, cz + wz };
    }

    // ── Water-bed noise ───────────────────────────────────────────────────

    /**
     * Small-amplitude noise added to the bicubic waterY so river beds and
     * ocean floors have gentle undulation instead of being perfectly flat.
     * Uses a single mid-frequency quintic octave — coarser and quieter than
     * the land noise so water still reads as water.
     *
     * @return offset in blocks, roughly in [−{@value WB_AMP}, +{@value WB_AMP}]
     */
    private static float waterBedNoise(int worldX, int worldZ) {
        float n = quinticNoise(worldX * WB_FREQ + 155.3f, worldZ * WB_FREQ + 211.7f, 0x8D5C29);
        // Second octave for a little more character
        n += 0.4f * quinticNoise(worldX * WB_FREQ * 2.3f + 73.1f, worldZ * WB_FREQ * 2.3f + 44.6f, 0x3E7F51);
        n /= 1.4f; // normalise back to [-1, 1]
        return n * WB_AMP;
    }



    /**
     * Valley carving noise in [−1, 1].
     *
     * <p>Two-octave fBm at low frequency.  The second octave is offset and
     * rotated (swapped x/z) to break symmetry — otherwise valleys would run
     * in axis-aligned corridors.  The result has natural-feeling meandering
     * valley lines that cross each other at oblique angles, producing the
     * snaking pass network seen in real mountain ranges.
     *
     * <p>Where the result is negative the caller subtracts a height contribution
     * proportional to how far below zero the value is, scaled by terrain height.
     * Positive values are left alone — ridges are shaped entirely by the main
     * fBm, only valleys are actively carved.
     */
    private static float valleyNoise(int x, int z) {
        float n1 = quinticNoise(x * VALLEY_FREQ          + 503.7f, z * VALLEY_FREQ          + 119.3f, 0x2C5F8A);
        // Second octave: swap x/z so valley lines run at oblique angles
        float n2 = quinticNoise(z * VALLEY_FREQ * 1.37f  + 201.1f, x * VALLEY_FREQ * 1.37f  + 378.5f, 0xB14E0D);
        return (n1 * 0.65f + n2 * 0.35f);
    }

    /**
     * 2D value noise in [−1, 1] using <em>quintic</em> interpolation
     * ({@code 6t⁵ − 15t⁴ + 10t³}).
     *
     * <p>Quintic curves zero both the first and second derivatives at lattice
     * boundaries, eliminating the faint grid-aligned ridges and flat spots that
     * smoothstep (3t²−2t³) leaves in multi-octave terrain.
     */
    static float quinticNoise(float x, float z, int seed) {
        int   ix = (int) Math.floor(x);
        int   iz = (int) Math.floor(z);
        float fx = x - ix;
        float fz = z - iz;
        // Quintic fade — C² continuity, no second-derivative kink at grid edges
        float ux = fx * fx * fx * (fx * (fx * 6f - 15f) + 10f);
        float uz = fz * fz * fz * (fz * (fz * 6f - 15f) + 10f);
        float v00 = noiseHash(ix,     iz,     seed);
        float v10 = noiseHash(ix + 1, iz,     seed);
        float v01 = noiseHash(ix,     iz + 1, seed);
        float v11 = noiseHash(ix + 1, iz + 1, seed);
        return lerp(uz, lerp(ux, v00, v10), lerp(ux, v01, v11));
    }

    /** Keep the old smoothstep version; used by nothing now but available for reference. */
    static float valueNoise(float x, float z, int seed) {
        int   ix = (int) Math.floor(x);
        int   iz = (int) Math.floor(z);
        float fx = x - ix;
        float fz = z - iz;
        float ux = fx * fx * (3f - 2f * fx);
        float uz = fz * fz * (3f - 2f * fz);
        float v00 = noiseHash(ix,     iz,     seed);
        float v10 = noiseHash(ix + 1, iz,     seed);
        float v01 = noiseHash(ix,     iz + 1, seed);
        float v11 = noiseHash(ix + 1, iz + 1, seed);
        return lerp(uz, lerp(ux, v00, v10), lerp(ux, v01, v11));
    }

    // ── Codec / vanilla delegate ──────────────────────────────────────────

    private final Holder<NoiseGeneratorSettings> settings;
    private final NoiseBasedChunkGenerator vanilla;

    /**
     * Configured spawn pixel X from the world-preset JSON.
     * -1 means "use map centre" (world X = 0).
     * Updated whenever the chunk generator is deserialised from the preset.
     */
    private final int spawnPixelX;

    /**
     * Configured spawn pixel Z from the world-preset JSON.
     * -1 means "use map centre" (world Z = 0).
     * Updated whenever the chunk generator is deserialised from the preset.
     */
    private final int spawnPixelZ;

    // Static copies so the LevelEvent handler in GotMod can read them without
    // needing a reference to the chunk generator instance.
    private static int configuredSpawnPixelX = -1;
    private static int configuredSpawnPixelZ = -1;

    public static final MapCodec<GotChunkGenerator> CODEC =
            RecordCodecBuilder.mapCodec(i -> i.group(
                    BiomeSource.CODEC.fieldOf("biome_source")
                            .forGetter(ChunkGenerator::getBiomeSource),
                    NoiseGeneratorSettings.CODEC.fieldOf("settings")
                            .forGetter(g -> g.settings),
                    com.mojang.serialization.Codec.INT
                            .optionalFieldOf("spawn_pixel_x", -1)
                            .forGetter(g -> g.spawnPixelX),
                    com.mojang.serialization.Codec.INT
                            .optionalFieldOf("spawn_pixel_z", -1)
                            .forGetter(g -> g.spawnPixelZ)
            ).apply(i, GotChunkGenerator::new));

    public GotChunkGenerator(BiomeSource biomeSource,
                             Holder<NoiseGeneratorSettings> settings,
                             int spawnPixelX,
                             int spawnPixelZ) {
        super(biomeSource);
        this.settings     = settings;
        this.spawnPixelX  = spawnPixelX;
        this.spawnPixelZ  = spawnPixelZ;
        this.vanilla      = new NoiseBasedChunkGenerator(biomeSource, settings);
        // Publish to static fields so the event handler can read them.
        configuredSpawnPixelX = spawnPixelX;
        configuredSpawnPixelZ = spawnPixelZ;
    }

    /** Returns the pixel-X configured in the world-preset JSON (-1 = map centre). */
    public static int getConfiguredSpawnPixelX() { return configuredSpawnPixelX; }

    /** Returns the pixel-Z configured in the world-preset JSON (-1 = map centre). */
    public static int getConfiguredSpawnPixelZ() { return configuredSpawnPixelZ; }

    @Override
    protected @NotNull MapCodec<? extends ChunkGenerator> codec() { return CODEC; }

    // ── Block fill ────────────────────────────────────────────────────────

    @Override
    public @NotNull CompletableFuture<ChunkAccess> fillFromNoise(
            @NotNull Blender blender, @NotNull RandomState random,
            @NotNull StructureManager structures, @NotNull ChunkAccess chunk) {

        NoiseSettings noise = settings.value().noiseSettings();
        int minY = noise.minY();
        int maxY = minY + noise.height();
        int sea  = getSeaLevel();
        ChunkPos pos = chunk.getPos();

        for (int lx = 0; lx < 16; lx++) {
            for (int lz = 0; lz < 16; lz++) {
                int wx = pos.getBlockX(lx);
                int wz = pos.getBlockZ(lz);
                int surfaceY = computeSurfaceY(wx, wz);

                for (int y = minY; y < maxY; y++) {
                    BlockState state;
                    if      (y <= surfaceY) state = Blocks.STONE.defaultBlockState();
                    else if (y <= sea)      state = settings.value().defaultFluid();
                    else                    state = Blocks.AIR.defaultBlockState();
                    chunk.setBlockState(new BlockPos(lx, y, lz), state, false);
                }
            }
        }
        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public void buildSurface(@NotNull WorldGenRegion region, @NotNull StructureManager structures,
                             @NotNull RandomState random, @NotNull ChunkAccess chunk) {
        vanilla.buildSurface(region, structures, random, chunk);
    }


    // ── Surface Y computation ─────────────────────────────────────────────

    /**
     * Computes the terrain surface Y at (worldX, worldZ).
     *
     * <p><b>Water path</b>: Uses Catmull-Rom bicubic interpolation over the 4×4
     * pixel neighbourhood to decide if a position is inside a river/ocean.
     * The 0.5 threshold on the interpolated value gives smooth, curved edges
     * instead of blocky pixel-aligned rectangles.  Returns flat water-bed Y.
     *
     * <p><b>Land path</b>: Gaussian-weighted blend over a (2×{@value SAMPLE_RADIUS}+1)²
     * pixel window, land neighbours only, with {@link #HEIGHT_STRETCH} applied.
     */
    public static int computeSurfaceY(int worldX, int worldZ) {
        if (!BiomemapLoader.isLoaded()) return SEA_LEVEL;

        // Raw pixel coord, then domain-warped so terrain and biome boundaries
        // are organically curved and always in sync (GotBiomeSource uses the
        // identical warpPixelCoord call).
        float rawCx = worldX / (float) BiomemapLoader.MAP_SCALE
                + BiomemapLoader.getWidth()  * 0.5f;
        float rawCz = worldZ / (float) BiomemapLoader.MAP_SCALE
                + BiomemapLoader.getHeight() * 0.5f;
        float[] warped = warpPixelCoord(rawCx, rawCz);
        float cx = warped[0];
        float cz = warped[1];
        int icx = (int) Math.floor(cx);
        int icz = (int) Math.floor(cz);

        // Bicubic water check: interpolate water/land mask over a 4x4 pixel grid
        // using Catmull-Rom splines so river boundaries are smooth curves,
        // not pixel staircases.  Threshold at 0.5 = same area, smooth edges.
        float[] waterResult = bicubicWaterFraction(cx, cz);
        float waterFrac = waterResult[0];
        float waterY    = waterResult[1];
        if (waterFrac >= WATER_THRESHOLD) {
            // Add gentle undulation to river beds and ocean floors.
            float bedNoise = waterBedNoise(worldX, worldZ);
            return Mth.floor(Mth.clamp(waterY + bedNoise, 10f, SEA_LEVEL - 1f));
        }

        // Land path: steep Gaussian blend
        float totalBaseY  = 0f;
        float totalScale  = 0f;
        float totalWeight = 0f;

        for (int dx = -SAMPLE_RADIUS; dx <= SAMPLE_RADIUS; dx++) {
            for (int dz = -SAMPLE_RADIUS; dz <= SAMPLE_RADIUS; dz++) {
                int color = BiomemapLoader.getRawPixel(icx + dx, icz + dz);
                GotBiomeTerrainParams.Params p = GotBiomeTerrainParams.forColor(color);
                if (p.isWater) continue;

                float ddx   = (icx + dx) - cx;
                float ddz   = (icz + dz) - cz;
                float dist2 = ddx * ddx + ddz * ddz;
                float w = (float) Math.exp(-dist2 * GAUSSIAN_INV_2S2);
                w = (float) Math.pow(w, HEIGHT_STRETCH);

                totalBaseY  += p.baseY * w;
                totalScale  += p.scale * w;
                totalWeight += w;
            }
        }

        if (totalWeight <= 0f) return SEA_LEVEL;

        float blendedBaseY = totalBaseY / totalWeight;
        float blendedScale = totalScale / totalWeight;
        float noise = terrainNoise(worldX, worldZ);
        float landY = blendedBaseY + noise * blendedScale * GotBiomeTerrainParams.AMP_SMOOTH;

        // Valley carving — only meaningful in elevated terrain.
        // valleyNoise returns [-1, 1].  Where it dips below VALLEY_THRESHOLD,
        // terrain is pushed downward proportional to (a) how negative the noise is
        // and (b) how high above sea level the current terrain is.
        // This means lowlands and plains are barely affected (they're already near
        // sea level so there's little height to remove), while mountain terrain is
        // actively carved into ridges, faces, and saddle passes.
        // The Gaussian blend (σ=1.6) ensures the carved valleys naturally continue
        // through biome boundaries — a valley in north_mountains transitions
        // smoothly into a valley in the adjacent north_hills or frostfangs without
        // any seam.
        float vn = valleyNoise(worldX, worldZ);
        if (vn < VALLEY_THRESHOLD) {
            // How deep into "valley territory" are we?  0 at threshold, 1 at noise=-1.
            float valleyT = (VALLEY_THRESHOLD - vn) / (1f + VALLEY_THRESHOLD);
            // Smoothstep for a gentler valley floor / less knife-edge transition
            valleyT = valleyT * valleyT * (3f - 2f * valleyT);
            // Height above sea — carving scales with elevation, so flatlands unchanged
            float heightAboveSea = Math.max(0f, landY - SEA_LEVEL);
            // Carve fraction: max out at 1.0 for very high terrain, taper to 0 near sea
            float carveFrac = Math.min(1f, heightAboveSea / 80f);
            landY -= valleyT * VALLEY_DEPTH * carveFrac;
        }

        // Height-proportional steepness — the higher the terrain the more aggressively
        // the noise amplitude is amplified.  Below sea level nothing changes; above it
        // a smooth ramp multiplies the noise contribution so high peaks are sharp and
        // craggy while lowlands stay gently rolling.
        // elevFrac: 0 at sea level, 1 at baseY+100 (into mountain territory).
        // extraAmp at elevFrac=1 → noise scaled up by an additional 85 blocks,
        // giving high ridges a noticeably steeper, more angular profile (was 55).
        float elevFrac = Math.min(1f, Math.max(0f, (landY - SEA_LEVEL) / 100f));
        elevFrac = elevFrac * elevFrac; // quadratic — effect kicks in harder at real altitude
        landY += noise * blendedScale * elevFrac * 85f;

        // Bank slope: blend land height down toward waterY as we approach the water
        // boundary.  waterFrac runs 0 (pure land) to WATER_THRESHOLD (water edge).
        // We start pulling the terrain down at BANK_START and reach waterY exactly
        // at WATER_THRESHOLD.  This produces a natural sloping bank.
        //
        // BANK_START is set well below WATER_THRESHOLD so the slope is gradual enough
        // that Mth.floor() doesn't produce visible stair-steps in the transition zone.
        final float BANK_START = WATER_THRESHOLD - 0.23f; // begin slope ~0.23 frac units before water edge
        if (waterFrac > BANK_START) {
            // t goes 0->1 as waterFrac goes BANK_START->WATER_THRESHOLD
            float t = (waterFrac - BANK_START) / (WATER_THRESHOLD - BANK_START);
            // Smoothstep so the transition is gentle at both ends
            t = t * t * (3f - 2f * t);
            landY = landY + t * (waterY - landY);
        }

        return Mth.floor(landY);
    }

    // ── Bicubic water-fraction helpers ────────────────────────────────────

    /**
     * Computes a smooth, continuous water fraction and interpolated water-bed Y
     * for position {@code (cx, cz)} using <em>Catmull-Rom bicubic interpolation</em>
     * over the 4×4 pixel neighbourhood of the biomemap.
     *
     * <p><b>Water fraction</b>: Each pixel is treated as 0 (land) or 1 (water).
     * Catmull-Rom interpolation produces smooth, curved river/ocean boundaries.
     *
     * <p><b>Water-bed Y</b>: Rather than averaging all nearby water pixels
     * (which causes hard cliffs at river→ocean and ocean→deep-ocean transitions),
     * we bicubically interpolate the actual {@code baseY} of water pixels
     * exactly like the water-fraction mask. This means the sea floor slopes
     * smoothly between river bed (Y≈54), ocean (Y≈36), and deep ocean (Y≈22),
     * eliminating the terracing and abrupt depth changes visible in-game.
     *
     * @return float[2]: [waterFraction ∈ [0,1], bicubic-interpolated waterBedY]
     */
    private static float[] bicubicWaterFraction(float cx, float cz) {
        int ix = (int) Math.floor(cx);
        int iz = (int) Math.floor(cz);
        // Sub-pixel offset within the current pixel (0..1).
        float fx = cx - ix;
        float fz = cz - iz;

        // Sample 4×4 grid: columns ix-1..ix+2, rows iz-1..iz+2
        // For each cell store both the water mask (0 or 1) and the water-bed Y.
        // Land pixels contribute 0 to the water mask and SEA_LEVEL to the Y grid
        // (their Y is irrelevant where waterFrac < 0.5, but a neutral value avoids
        // pulling the interpolated Y down at the very edge of a bank).
        float[][] waterGrid = new float[4][4];
        float[][] yGrid     = new float[4][4];
        for (int dz = 0; dz < 4; dz++) {
            for (int dx = 0; dx < 4; dx++) {
                int color = BiomemapLoader.getRawPixel(ix - 1 + dx, iz - 1 + dz);
                GotBiomeTerrainParams.Params p = GotBiomeTerrainParams.forColor(color);
                waterGrid[dz][dx] = p.isWater ? 1.0f : 0.0f;
                // For water pixels use actual bed Y; for land use SEA_LEVEL so the
                // bicubic Y interpolation stays at a sensible neutral near shore.
                yGrid[dz][dx] = p.isWater ? p.baseY : (float) SEA_LEVEL;
            }
        }

        // Bicubic interpolation of the water-mask fraction.
        float[] rowFrac = new float[4];
        for (int dz = 0; dz < 4; dz++) {
            rowFrac[dz] = catmullRom(fx,
                    waterGrid[dz][0], waterGrid[dz][1],
                    waterGrid[dz][2], waterGrid[dz][3]);
        }
        float frac = Mth.clamp(catmullRom(fz,
                rowFrac[0], rowFrac[1], rowFrac[2], rowFrac[3]), 0f, 1f);

        // Bicubic interpolation of the water-bed Y.
        // This smoothly transitions the sea floor between river, ocean, and deep ocean
        // pixels rather than snapping between their discrete baseY values.
        float[] rowY = new float[4];
        for (int dz = 0; dz < 4; dz++) {
            rowY[dz] = catmullRom(fx,
                    yGrid[dz][0], yGrid[dz][1],
                    yGrid[dz][2], yGrid[dz][3]);
        }
        float waterY = catmullRom(fz, rowY[0], rowY[1], rowY[2], rowY[3]);
        // Clamp to valid underground range (deep ocean floor to sea level).
        waterY = Mth.clamp(waterY, 10f, (float) SEA_LEVEL);

        return new float[]{ frac, waterY };
    }

    /**
     * Catmull-Rom spline evaluation at parameter {@code t} ∈ [0, 1].
     *
     * <p>p1 and p2 are the two surrounding knot values; p0 and p3 are the
     * outer neighbours used to compute tangents.  This gives C¹ continuity
     * at knot boundaries, producing smooth curves without requiring explicit
     * tangent specification.
     */
    private static float catmullRom(float t, float p0, float p1, float p2, float p3) {
        float t2 = t * t;
        float t3 = t2 * t;
        return 0.5f * (
                (2f * p1)
                        + (-p0 + p2)           * t
                        + (2f*p0 - 5f*p1 + 4f*p2 - p3) * t2
                        + (-p0 + 3f*p1 - 3f*p2 + p3)   * t3
        );
    }

    private static float noiseHash(int x, int z, int seed) {
        int n = x * 1619 + z * 31337 + seed * 6971;
        n = (n << 13) ^ n;
        n = n * (n * n * 15731 + 789221) + 1376312589;
        return 1f - (n & 0x7FFFFFFF) / 1073741824f;
    }

    private static float lerp(float t, float a, float b) { return a + t * (b - a); }

    // ── ChunkGenerator boilerplate ────────────────────────────────────────

    @Override
    public int getBaseHeight(int x, int z, Heightmap.@NotNull Types type,
                             @NotNull LevelHeightAccessor level,
                             @NotNull RandomState random) {
        return computeSurfaceY(x, z);
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
            if      (y <= surface) states[i] = Blocks.STONE.defaultBlockState();
            else if (y <= sea)     states[i] = settings.value().defaultFluid();
            else                   states[i] = Blocks.AIR.defaultBlockState();
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
        Holder<Biome> b = region.getBiome(pos.getWorldPosition().atY(region.getMaxY() - 1));
        WorldgenRandom rand = new WorldgenRandom(RandomSource.create());
        rand.setDecorationSeed(region.getSeed(), pos.getMinBlockX(), pos.getMinBlockZ());
        NaturalSpawner.spawnMobsForChunkGeneration(region, b, pos, rand);
    }

    @Override public int getSeaLevel()  { return SEA_LEVEL; }
    @Override public int getMinY()      { return settings.value().noiseSettings().minY(); }
    @Override public int getGenDepth()  { return settings.value().noiseSettings().height(); }

    @Override
    public void addDebugScreenInfo(java.util.List<String> info,
                                   RandomState random, BlockPos pos) {
        if (!BiomemapLoader.isLoaded()) return;

        int cx = Math.round(pos.getX() / (float) BiomemapLoader.MAP_SCALE
                + BiomemapLoader.getWidth()  * 0.5f);
        int cz = Math.round(pos.getZ() / (float) BiomemapLoader.MAP_SCALE
                + BiomemapLoader.getHeight() * 0.5f);
        int color = BiomemapLoader.getRawPixel(cx, cz);
        GotBiomeTerrainParams.Params p = GotBiomeTerrainParams.forColor(color);
        int surfY = computeSurfaceY(pos.getX(), pos.getZ());

        info.add(String.format(
                "[GoT] Y=%d  base=%.0f  scale=%.2f  %s  sea=%d  px=(%d,%d)  σ=%.1f  stretch=%.1f",
                surfY, p.baseY, p.scale, p.isWater ? "WATER" : "land",
                SEA_LEVEL, cx, cz, GAUSSIAN_SIGMA, HEIGHT_STRETCH));
    }
}