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
 * Chunk generator for the GoT mod — full ME-quality terrain.
 *
 * ═══════════════════════════════════════════════════════
 * WHAT THIS DOES (all borrowed from the Middle Earth mod)
 * ═══════════════════════════════════════════════════════
 *
 * 1. FULL SIMPLEX NOISE (2D + 3D)
 *    Stefan Gustavson's public-domain implementation, same one ME uses.
 *    2D for surface height; 3D for cave carving and domain warping.
 *    Much better gradient distribution than the old hand-rolled Perlin.
 *
 * 2. FOUR-OCTAVE FRACTAL NOISE  (ME: getPerlinHeight)
 *    Octaves at 1x, 2x, 4x, 8x frequency with 1/0.5/0.25/0.125 weights,
 *    normalised. Large rolling hills with fine surface detail on top.
 *
 * 3. DOMAIN WARPING on the Gaussian sample coordinates
 *    Before we look up a pixel in the biomemap we nudge the sample point
 *    with a low-frequency Simplex displacement. This breaks the hard pixel
 *    grid so biome boundaries meander organically instead of forming crisp
 *    horizontal/vertical lines (rivers especially benefit from this).
 *
 * 4. BILINEAR SUB-PIXEL INTERPOLATION  (ME: getBiomeWeightHeight)
 *    Within each biomemap pixel we bilinearly interpolate between the four
 *    surrounding pixels, so terrain never snaps between height values at
 *    pixel boundaries. Smooth at every scale.
 *
 * 5. EXPONENTIAL MOUNTAIN AMPLIFICATION  (ME: MOUNTAIN_EXPONENTIAL_HEIGHT)
 *    Past MOUNTAIN_THRESHOLD the height is boosted exponentially AND two
 *    extra octaves of high-frequency noise are added so peaks are jagged.
 *
 * 6. SDF RIVER CARVING — fixed-width channel carved by world-block distance
 *    River/ocean depth varies by the same noise that drives the surface,
 *    giving rivers varied-depth channels rather than flat uniform beds.
 *    Deep water calms the noise (ME's divider clamp).
 *
 *
 * 8. BLOCK LAYERING BENEATH THE SURFACE  (ME: BlocksLayeringData)
 *    Instead of pure stone below the surface we place 3 dirt/under blocks
 *    then transition to stone. For water biomes we do gravel→sand→stone.
 *    Deepslate appears in the bottom quarter of the world.
 *
 * 9. CAVE NOISE in fillFromNoise  (ME: trySetBlock)
 *    Two octaves of 3D Simplex for large chambers + two crossed absolute-
 *    value passes for spaghetti tunnels. Blocks are only placed when the
 *    combined noise allows it, punching caves directly into the stone fill.
 *
 * 10. MARSH HEIGHT VARIANT  (ME: getMarshesHeight / getNoisyHeight)
 *     Water biomes with very low base_height get the marsh treatment:
 *     height oscillates around sea level with small Simplex bumps so
 *     flat wetlands look wet and uneven rather than completely flat.
 */
public final class GotChunkGenerator extends ChunkGenerator {

    public static final int SEA_LEVEL = 61;

    // ─────────────────────────────────────────────────────────────────────
    // Gaussian biome blending
    // ─────────────────────────────────────────────────────────────────────
    private static final int   SAMPLE_RADIUS    = 6;
    private static final float GAUSSIAN_SIGMA   = 0.8f;
    private static final float GAUSSIAN_INV_2S2 = 1f / (2f * GAUSSIAN_SIGMA * GAUSSIAN_SIGMA);

    // ─────────────────────────────────────────────────────────────────────
    // Domain warping — applied before the Gaussian window lookup.
    // Low frequency so the distortion is large-scale (river meander).
    // ─────────────────────────────────────────────────────────────────────
    private static final float WARP_FREQ      = 1f / 340f; // pixel-space frequency
    private static final float WARP_AMPLITUDE = 2.4f;      // max pixel displacement

    // ─────────────────────────────────────────────────────────────────────
    // Fractal / octave Perlin noise  (ME: getPerlinHeight constants)
    // ─────────────────────────────────────────────────────────────────────
    private static final float PERLIN_FREQ          = 1f / 210f;
    private static final float PERLIN_STRETCH_2     = 37f;          // ME's PERLIN_STRETCH_X2
    private static final float PERLIN_HEIGHT_RANGE  = 53f;          // ME: PERLIN_HEIGHT_RANGE
    private static final float PERLIN_HEIGHT_OFFSET = 8f;           // ME: PERLIN_HEIGHT_OFFSET
    private static final float PERLIN_NORMALISER    = 1f + 0.5f + 0.25f + 0.125f;

    // ─────────────────────────────────────────────────────────────────────
    // Mountain amplification  (ME: MOUNTAIN_EXPONENTIAL_HEIGHT constants)
    // ─────────────────────────────────────────────────────────────────────
    private static final float MOUNTAIN_THRESHOLD  = SEA_LEVEL + 30f; // ME: MOUNTAIN_START_HEIGHT
    private static final float MOUNTAIN_EXPO       = 1.02f;           // ME: MOUNTAIN_EXPONENTIAL_HEIGHT
    private static final float MOUNTAIN_NOISE_AMP  = 3.5f;           // ME: MOUNTAIN_HEIGHT_RANGE

    // ─────────────────────────────────────────────────────────────────────
    // SDF river carving  (replaces the old waterMix / waterWeight system)
    // ─────────────────────────────────────────────────────────────────────
    // Rivers are carved by measuring world-block distance from the current
    // column to the nearest river-type biomemap pixel center.  Width is a
    // fixed block constant, completely independent of MAP_SCALE, so rivers
    // look the same regardless of how large a biomemap pixel is.
    //
    // Only pixels with isRiver=true (shallow negative base_height) are carved.
    // Oceans and lakes use the Gaussian base-height blend — their negative
    // avgBaseY naturally depresses terrain, then buildSurface fills with water.
    private static final float RIVER_HALF_WIDTH   = 28f; // half-width of carved channel (blocks)
    private static final float RIVER_BANK_WIDTH   = 22f; // smoothstep transition zone (blocks)
    private static final float RIVER_BED_DEPTH    = 5f;  // blocks below SEA_LEVEL for channel floor
    private static final float RIVER_BED_NOISE    = 0.18f; // noise fraction added to bed Y variation
    private static final int   RIVER_SEARCH_RADIUS = 3;  // pixel search radius (covers warp+channel)

    // ─────────────────────────────────────────────────────────────────────
    // Slope surface  (ME: getTerrainSlope offset)
    // ─────────────────────────────────────────────────────────────────────

    // ─────────────────────────────────────────────────────────────────────
    // Cave noise  (ME: trySetBlock constants)
    // ─────────────────────────────────────────────────────────────────────
    private static final float CAVE_STRETCH_H   = 60f;
    private static final float CAVE_STRETCH_V   = 50f;
    private static final float SPAG_STRETCH_H   = 90f;
    // Cave thresholds — block placed only when ALL conditions pass
    private static final float CAVE_THRESH      = 0.40f;  // large-chamber noise threshold
    private static final float CAVE_THRESH2     = 0.75f;  // medium-scale threshold
    private static final float CAVE_THRESH3     = 0.80f;  // fine-scale threshold
    private static final float SPAG_THRESH      = 0.09f;  // spaghetti tunnel threshold

    // ─────────────────────────────────────────────────────────────────────
    // Block layering depths  (ME: BlocksLayeringData percentages)
    // ─────────────────────────────────────────────────────────────────────
    private static final int DEEPSLATE_DEPTH = 3; // fraction of world from bottom → deepslate
    private static final int UNDER_DEPTH     = 3; // dirt/gravel blocks under the surface cap

    // ─────────────────────────────────────────────────────────────────────
    // Noise seed
    // ─────────────────────────────────────────────────────────────────────
    private static final int NOISE_SEED = 0x5EED;

    // ─────────────────────────────────────────────────────────────────────
    // Codec / vanilla delegate
    // ─────────────────────────────────────────────────────────────────────
    private final Holder<NoiseGeneratorSettings> settings;
    private final NoiseBasedChunkGenerator       vanilla;
    private final int spawnPixelX;
    private final int spawnPixelZ;

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
                             int spawnPixelX, int spawnPixelZ) {
        super(biomeSource);
        this.settings    = settings;
        this.spawnPixelX = spawnPixelX;
        this.spawnPixelZ = spawnPixelZ;
        this.vanilla     = new NoiseBasedChunkGenerator(biomeSource, settings);
        configuredSpawnPixelX = spawnPixelX;
        configuredSpawnPixelZ = spawnPixelZ;
    }

    public static int getConfiguredSpawnPixelX() { return configuredSpawnPixelX; }
    public static int getConfiguredSpawnPixelZ() { return configuredSpawnPixelZ; }

    @Override
    protected @NotNull MapCodec<? extends ChunkGenerator> codec() { return CODEC; }

    // ═════════════════════════════════════════════════════════════════════
    // fillFromNoise — stone skeleton + cave carving
    // ═════════════════════════════════════════════════════════════════════

    /**
     * Fills the chunk column from minY to maxY with stone/water/air, then
     * punches caves using ME-style 3D Simplex noise (large chambers + spaghetti
     * tunnels) rather than relying solely on vanilla carvers.
     *
     * <p>Cave algorithm mirrors ME's {@code trySetBlock}:
     * <ul>
     *   <li>Two octaves of 3D Simplex (CAVE_STRETCH_H × CAVE_STRETCH_V) for
     *       large rounded chambers.</li>
     *   <li>Three absolute-value 3D Simplex passes at offset seeds for
     *       spaghetti tunnels — the combined value must stay above SPAG_THRESH
     *       or the block is removed.</li>
     *   <li>A medium-scale and fine-scale pass gate the carve so tiny
     *       pockets don't appear everywhere.</li>
     * </ul>
     * Cave noise is only computed below sea level to preserve the surface.
     */
    @Override
    public @NotNull CompletableFuture<ChunkAccess> fillFromNoise(
            @NotNull Blender blender, @NotNull RandomState random,
            @NotNull StructureManager structures, @NotNull ChunkAccess chunk) {

        NoiseSettings noiseSettings = settings.value().noiseSettings();
        int minY = noiseSettings.minY();
        int maxY = minY + noiseSettings.height();
        int sea  = getSeaLevel();
        ChunkPos pos = chunk.getPos();

        for (int lx = 0; lx < 16; lx++) {
            for (int lz = 0; lz < 16; lz++) {
                int wx = pos.getBlockX(lx);
                int wz = pos.getBlockZ(lz);
                int surfaceY = computeSurfaceY(wx, wz);

                for (int y = minY; y < maxY; y++) {
                    BlockState state;

                    if (y > surfaceY) {
                        state = (y <= sea) ? settings.value().defaultFluid()
                                : Blocks.AIR.defaultBlockState();
                    } else {
                        // Below surface: check cave carve
                        if (y < sea && shouldCarve(wx, y, wz)) {
                            // Carved air, or water if below sea
                            state = Blocks.AIR.defaultBlockState();
                        } else {
                            state = Blocks.STONE.defaultBlockState();
                        }
                    }

                    chunk.setBlockState(new BlockPos(lx, y, lz), state, false);
                }
            }
        }
        return CompletableFuture.completedFuture(chunk);
    }

    /**
     * Returns true if this block position should be carved into air by cave noise.
     * Mirrors ME's trySetBlock logic, but inverted (we return true = carve).
     *
     * ME uses:  if(noise < 0.4 && noise3 < 0.75 && mini < 0.8 && spag > 0.09) → place block
     * We use:   if(noise < 0.4 && noise3 < 0.75 && mini < 0.8 && spag > 0.09) → keep stone
     *           else → carve
     * So shouldCarve = NOT(those conditions).
     */
    private static boolean shouldCarve(int x, int y, int z) {
        // Large chamber noise (two octaves with tan-Y warp on vertical axis, ME-style)
        float chamberNoise = (float) simplex3D(
                x / CAVE_STRETCH_H,
                Math.tan(y / (double) CAVE_STRETCH_V),
                z / CAVE_STRETCH_H);
        chamberNoise += 0.5f * (float) simplex3D(
                x / (CAVE_STRETCH_H * 0.5f),
                y / (CAVE_STRETCH_V * 0.5f),
                z / (CAVE_STRETCH_H * 0.5f));
        chamberNoise /= 1.5f;

        if (chamberNoise >= CAVE_THRESH) return false;  // solid

        // Medium-scale gate
        float medium = (float) simplex3D(x / 90f, y / 60f, z / 90f);
        if (medium >= CAVE_THRESH2) return false;

        // Fine-scale gate
        float fine = (float) simplex3D(x / 40f, y / 30f, z / 40f);
        if (fine >= CAVE_THRESH3) return false;

        // Spaghetti tunnels: three offset passes, combined absolute value
        float sp1 = Math.abs((float) simplex3D(
                x / (SPAG_STRETCH_H * 1.5f),
                Math.tan(y / (double) CAVE_STRETCH_V),
                z / (SPAG_STRETCH_H * 1.5f)));
        float sp2 = Math.abs((float) simplex3D(
                (z + 98153f) / SPAG_STRETCH_H,
                y / CAVE_STRETCH_V,
                x / SPAG_STRETCH_H));
        float sp3 = Math.abs((float) simplex3D(
                (z + 1243624f) / (SPAG_STRETCH_H * 0.5f),
                y / CAVE_STRETCH_V,
                x / (SPAG_STRETCH_H * 0.5f)));
        float spag = (sp1 + sp2 + sp3) / 3f;

        // Carve if spaghetti noise is below threshold (thin tunnel)
        return spag <= SPAG_THRESH;
    }

    // ═════════════════════════════════════════════════════════════════════
    // buildSurface — slope-based blocks + ME-style layering
    // ═════════════════════════════════════════════════════════════════════

    /**
     * Places surface and sub-surface blocks using:
     * <ol>
     *   <li>UNDER_DEPTH blocks of under-material (dirt for grass, self for rock).</li>
     *   <li>Deepslate in the bottom fraction of the world (ME: DEEPSLATE_LEVEL).</li>
     *   <li>Water fill up to sea level above submerged terrain.</li>
     * </ol>
     */
    @Override
    public void buildSurface(@NotNull WorldGenRegion region, @NotNull StructureManager structures,
                             @NotNull RandomState random, @NotNull ChunkAccess chunk) {
        if (!BiomemapLoader.isLoaded()) {
            // vanilla.buildSurface intentionally removed — it applies vanilla surface
            // rules (snow blocks on cold biomes, gravel on rivers) that conflict with ours.
            return;
        }

        ChunkPos cp  = chunk.getPos();
        int sea      = getSeaLevel();
        int minY     = chunk.getMinY();
        int worldH   = chunk.getHeight();
        int deepYMax = minY + worldH / 4;  // bottom quarter → deepslate

        for (int lx = 0; lx < 16; lx++) {
            for (int lz = 0; lz < 16; lz++) {
                int wx = cp.getBlockX(lx);
                int wz = cp.getBlockZ(lz);

                int   surfaceY   = computeSurfaceY(wx, wz);

                // Pixel lookup (raw, nearest — domain warp already baked into surfaceY)
                float rawCx = wx / (float) BiomemapLoader.MAP_SCALE + BiomemapLoader.getWidth()  * 0.5f;
                float rawCz = wz / (float) BiomemapLoader.MAP_SCALE + BiomemapLoader.getHeight() * 0.5f;
                int color   = BiomemapLoader.getRawPixel(Math.round(rawCx), Math.round(rawCz));
                GotBiomeTerrainParams.Params params = GotBiomeTerrainParams.forColor(color);

                // ── Surface block ─────────────────────────────────────────
                // Columns carved by the SDF (inside channel or bank) get river
                // bed blocks regardless of what the raw pixel says, because the
                // SDF may have carved a land pixel into the channel.
                float[] wc  = warpCoord(rawCx, rawCz);
                float riverDist = riverSdf(wc[0], wc[1]);

                // Surface block — height-based only, no slope map.
                BlockState surfaceBlock;
                if (surfaceY < sea) {
                    surfaceBlock = Blocks.GRAVEL.defaultBlockState();
                } else if (surfaceY >= 115) {
                    surfaceBlock = Blocks.STONE.defaultBlockState();
                } else if (surfaceY >= 95) {
                    surfaceBlock = Blocks.SNOW_BLOCK.defaultBlockState();
                } else {
                    surfaceBlock = Blocks.GRASS_BLOCK.defaultBlockState();
                }
                BlockState underBlock;
                if (surfaceBlock.is(Blocks.GRASS_BLOCK)
                        || surfaceBlock.is(Blocks.SNOW_BLOCK)
                        || surfaceBlock.is(Blocks.PODZOL)) {
                    underBlock = Blocks.DIRT.defaultBlockState();
                } else if (surfaceBlock.is(Blocks.SAND)) {
                    underBlock = Blocks.SANDSTONE.defaultBlockState();
                } else {
                    underBlock = surfaceBlock;
                }

                // ── Place block layers ────────────────────────────────────
                for (int dy = 0; dy < UNDER_DEPTH; dy++) {
                    int y = surfaceY - dy;
                    if (y >= minY) {
                        BlockState layer = (y <= deepYMax)
                                ? Blocks.DEEPSLATE.defaultBlockState()
                                : underBlock;
                        chunk.setBlockState(new BlockPos(lx, y, lz), layer, false);
                    }
                }
                int capY = surfaceY + 1;
                if (capY >= minY && capY < minY + worldH) {
                    chunk.setBlockState(new BlockPos(lx, capY, lz), surfaceBlock, false);
                }

                // ── Water fill ────────────────────────────────────────────
                // Always fill to sea level when below it — covers both carved
                // river channels (bed < sea) and ocean/lake depressions.
                for (int y = capY + 1; y <= sea; y++) {
                    chunk.setBlockState(new BlockPos(lx, y, lz),
                            settings.value().defaultFluid(), false);
                }
            }
        }
    }

    // ═════════════════════════════════════════════════════════════════════
    // computeSurfaceY — the main terrain height function
    // ═════════════════════════════════════════════════════════════════════

    /**
     * Computes terrain surface Y at (worldX, worldZ).
     *
     * <h3>Pipeline:</h3>
     * <ol>
     *   <li><b>Domain warp:</b> nudge the pixel-space sample point with low-freq
     *       Simplex displacement so biome boundaries meander organically.</li>
     *   <li><b>Bilinear interpolation:</b> smooth sub-pixel height between the
     *       four surrounding biomemap pixels (ME: getBiomeWeightHeight).</li>
     *   <li><b>Gaussian window:</b> weighted blend of nearby pixels for smooth
     *       biome transitions (existing behaviour, unchanged).</li>
     *   <li><b>Fractal noise:</b> four-octave Simplex, scaled by biome variance
     *       and suppressed under deep water.</li>
     *   <li><b>Mountain amplification:</b> exponential boost above MOUNTAIN_THRESHOLD
     *       plus extra high-frequency jagged noise on peaks.</li>
     *   <li><b>Marsh variant:</b> very-shallow water biomes (base near sea level)
     *       get small oscillating bumps so wetlands look uneven.</li>
     *   <li><b>River carving (SDF):</b> world-block distance to nearest isRiver pixel
     *       sloped banks.</li>
     * </ol>
     */
    public static int computeSurfaceY(int worldX, int worldZ) {
        if (!BiomemapLoader.isLoaded()) return SEA_LEVEL;

        // ── 1. Raw pixel-space coordinate ────────────────────────────────
        float rawCx = worldX / (float) BiomemapLoader.MAP_SCALE
                + BiomemapLoader.getWidth()  * 0.5f;
        float rawCz = worldZ / (float) BiomemapLoader.MAP_SCALE
                + BiomemapLoader.getHeight() * 0.5f;

        // ── 2. Domain warping ─────────────────────────────────────────────
        // Two perpendicular Simplex passes displace the sample point so
        // biome edges meander like real geography (rivers curve, coasts
        // are ragged). ME does something similar with its edge blending.
        float warpX = (float) simplex2D(rawCx * WARP_FREQ + 3.7f, rawCz * WARP_FREQ + 1.3f);
        float warpZ = (float) simplex2D(rawCx * WARP_FREQ + 9.2f, rawCz * WARP_FREQ + 7.8f);
        float warpedCx = rawCx + warpX * WARP_AMPLITUDE;
        float warpedCz = rawCz + warpZ * WARP_AMPLITUDE;

        int icx = (int) Math.floor(warpedCx);
        int icz = (int) Math.floor(warpedCz);

        // ── 3. Gaussian window + bilinear interpolation ───────────────────
        // The bilinear pass (ME: getBiomeWeightHeight) runs inside the
        // Gaussian accumulation: at each integer pixel position we sample
        // the four surrounding pixel heights and lerp by sub-pixel fraction.
        float blendedBaseY = 0f;
        float blendedScale = 0f;
        float totalWeight  = 0f;

        for (int dx = -SAMPLE_RADIUS; dx <= SAMPLE_RADIUS; dx++) {
            for (int dz = -SAMPLE_RADIUS; dz <= SAMPLE_RADIUS; dz++) {
                int px = icx + dx;
                int pz = icz + dz;

                float bilinearBaseY = bilinearSample(px, pz, warpedCx, warpedCz);
                GotBiomeTerrainParams.Params p = GotBiomeTerrainParams.forColor(
                        BiomemapLoader.getRawPixel(px, pz));

                float ddx   = px - warpedCx;
                float ddz   = pz - warpedCz;
                float dist2 = ddx * ddx + ddz * ddz;
                float w     = (float) Math.exp(-dist2 * GAUSSIAN_INV_2S2);

                totalWeight  += w;
                blendedBaseY += bilinearBaseY * w;
                blendedScale += p.scale() * w;
            }
        }

        if (totalWeight <= 0f) return SEA_LEVEL;

        float avgBaseY = blendedBaseY / totalWeight;
        float avgScale = blendedScale / totalWeight;

        // ── 4. Fractal Simplex noise ──────────────────────────────────────
        float perlin = fractalSimplex2D(worldX, worldZ);
        float noiseContrib = (perlin - PERLIN_HEIGHT_OFFSET) * (avgScale / PERLIN_HEIGHT_RANGE);

        // ── 5. Mountain amplification ─────────────────────────────────────
        if (avgBaseY >= MOUNTAIN_THRESHOLD) {
            float mult = (avgBaseY / MOUNTAIN_THRESHOLD) - 1f;
            avgBaseY  += avgBaseY * mult * MOUNTAIN_EXPO;
            float pn1  = fractalSimplex2D((int)(worldX * 2f / PERLIN_STRETCH_2),
                    (int)(worldZ * 2f / PERLIN_STRETCH_2));
            float pn2  = fractalSimplex2D((int)(worldX * 4f / PERLIN_STRETCH_2),
                    (int)(worldZ * 4f / PERLIN_STRETCH_2));
            noiseContrib += mult * MOUNTAIN_EXPO * MOUNTAIN_NOISE_AMP
                    * ((pn1 - PERLIN_HEIGHT_OFFSET) / PERLIN_HEIGHT_RANGE);
            noiseContrib += mult * (MOUNTAIN_NOISE_AMP * 0.5f)
                    * ((pn2 - PERLIN_HEIGHT_OFFSET) / PERLIN_HEIGHT_RANGE);
        }

        float totalHeight = avgBaseY + noiseContrib;

        // ── 6. SDF river carving ──────────────────────────────────────────
        float riverDist = riverSdf(warpedCx, warpedCz);
        if (riverDist < RIVER_HALF_WIDTH + RIVER_BANK_WIDTH) {
            float bedY = SEA_LEVEL - RIVER_BED_DEPTH
                    - Math.max(0f, noiseContrib * RIVER_BED_NOISE);
            if (riverDist < RIVER_HALF_WIDTH) {
                return Mth.floor(bedY);
            } else {
                float t = (riverDist - RIVER_HALF_WIDTH) / RIVER_BANK_WIDTH;
                t = t * t * (3f - 2f * t);
                float bankH = Math.max(totalHeight, SEA_LEVEL + 1f);
                return Mth.floor(Mth.lerp(t, bedY, bankH));
            }
        }

        return Mth.floor(totalHeight);
    }

    // ═════════════════════════════════════════════════════════════════════
    // Domain warp helper — shared by computeSurfaceY and buildSurface
    // ═════════════════════════════════════════════════════════════════════

    /** Applies domain warp to raw pixel-space coords; returns [warpedCx, warpedCz]. */
    static float[] warpCoord(float rawCx, float rawCz) {
        float warpX = (float) simplex2D(rawCx * WARP_FREQ + 3.7f, rawCz * WARP_FREQ + 1.3f);
        float warpZ = (float) simplex2D(rawCx * WARP_FREQ + 9.2f, rawCz * WARP_FREQ + 7.8f);
        return new float[]{ rawCx + warpX * WARP_AMPLITUDE, rawCz + warpZ * WARP_AMPLITUDE };
    }

    // ═════════════════════════════════════════════════════════════════════
    // SDF river query — shared by computeSurfaceY and GotBiomeSource
    // ═════════════════════════════════════════════════════════════════════

    /**
     * Returns the world-block distance from (warpedCx, warpedCz) in pixel space
     * to the nearest {@link GotBiomeTerrainParams.Params#isRiver()} pixel center.
     * Returns {@link Float#MAX_VALUE} if no river pixel is within the search window.
     */
    static float riverSdf(float warpedCx, float warpedCz) {
        int icx = (int) Math.floor(warpedCx);
        int icz = (int) Math.floor(warpedCz);
        float nearestSq = Float.MAX_VALUE;
        for (int dx = -RIVER_SEARCH_RADIUS; dx <= RIVER_SEARCH_RADIUS; dx++) {
            for (int dz = -RIVER_SEARCH_RADIUS; dz <= RIVER_SEARCH_RADIUS; dz++) {
                if (GotBiomeTerrainParams.forColor(
                        BiomemapLoader.getRawPixel(icx + dx, icz + dz)).isRiver()) {
                    float ddx = (icx + dx + 0.5f) - warpedCx;
                    float ddz = (icz + dz + 0.5f) - warpedCz;
                    float sq  = ddx * ddx + ddz * ddz;
                    if (sq < nearestSq) nearestSq = sq;
                }
            }
        }
        return nearestSq == Float.MAX_VALUE
                ? Float.MAX_VALUE
                : (float) Math.sqrt(nearestSq) * BiomemapLoader.MAP_SCALE;
    }

    // ═════════════════════════════════════════════════════════════════════
    // Bilinear sub-pixel interpolation  (ME: getBiomeWeightHeight)
    // ═════════════════════════════════════════════════════════════════════

    /**
     * Bilinearly interpolates the biome base height between the four
     * surrounding biomemap pixels at the given pixel position (px, pz)
     * using the fractional offset from the warped sample coordinate.
     *
     * <p>This matches ME's {@code getBiomeWeightHeight} / {@code getHeightBetween}
     * pattern exactly.
     */
    private static float bilinearSample(int px, int pz, float warpedCx, float warpedCz) {
        // Four surrounding pixel heights
        float h00 = baseYForPixel(px,     pz    );
        float h10 = baseYForPixel(px + 1, pz    );
        float h01 = baseYForPixel(px,     pz + 1);
        float h11 = baseYForPixel(px + 1, pz + 1);

        // Sub-pixel fraction (clamped to avoid edge artefacts)
        float xf = Mth.clamp(warpedCx - px, 0f, 1f);
        float zf = Mth.clamp(warpedCz - pz, 0f, 1f);

        float hi = Mth.lerp(xf, h00, h10);
        float lo = Mth.lerp(xf, h01, h11);
        return Mth.lerp(zf, hi, lo);
    }

    /** Returns the baseY (SEA_LEVEL + base_height) for a given pixel. */
    private static float baseYForPixel(int px, int pz) {
        int color = BiomemapLoader.getRawPixel(px, pz);
        return GotBiomeTerrainParams.forColor(color).baseY();
    }

    // ═════════════════════════════════════════════════════════════════════
    // ═════════════════════════════════════════════════════════════════════
    // Fractal noise helpers
    // ═════════════════════════════════════════════════════════════════════

    /**
     * Four-octave 2D Simplex noise matching ME's getPerlinHeight structure.
     * Returns a value in approximately [0, PERLIN_HEIGHT_RANGE].
     */
    private static float fractalSimplex2D(int worldX, int worldZ) {
        double x = worldX * PERLIN_FREQ;
        double z = worldZ * PERLIN_FREQ;
        double n  = 1.000 * simplex2D(x,      z     );
        n        += 0.500 * simplex2D(x * 2,  z * 2 );
        n        += 0.250 * simplex2D(x * 4,  z * 4 );
        n        += 0.125 * simplex2D(x * 8,  z * 8 );
        n        /= PERLIN_NORMALISER;
        // Map [-1,1] → [PERLIN_HEIGHT_OFFSET, PERLIN_HEIGHT_OFFSET + PERLIN_HEIGHT_RANGE]
        return (float)((n + 1.0) * 0.5 * PERLIN_HEIGHT_RANGE + PERLIN_HEIGHT_OFFSET);
    }

    // ═════════════════════════════════════════════════════════════════════
    // Simplex noise — Stefan Gustavson's public-domain implementation
    // (same implementation used by the Middle Earth mod)
    // ═════════════════════════════════════════════════════════════════════

    private static final short[] PERM;
    private static final short[] PERM_MOD12;

    private static final short[] BASE_PERM = {
            151,160,137,91,90,15,131,13,201,95,96,53,194,233,7,225,140,36,103,30,
            69,142,8,99,37,240,21,10,23,190,6,148,247,120,234,75,0,26,197,62,94,
            252,219,203,117,35,11,32,57,177,33,88,237,149,56,87,174,20,125,136,171,
            168,68,175,74,165,71,134,139,48,27,166,77,146,158,231,83,111,229,122,60,
            211,133,230,220,105,92,41,55,46,245,40,244,102,143,54,65,25,63,161,1,
            216,80,73,209,76,132,187,208,89,18,169,200,196,135,130,116,188,159,86,
            164,100,109,198,173,186,3,64,52,217,226,250,124,123,5,202,38,147,118,
            126,255,82,85,212,207,206,59,227,47,16,58,17,182,189,28,42,223,183,170,
            213,119,248,152,2,44,154,163,70,221,153,101,155,167,43,172,9,129,22,39,
            253,19,98,108,110,79,113,224,232,178,185,112,104,218,246,97,228,251,34,
            242,193,238,210,144,12,191,179,162,241,81,51,145,235,249,14,239,107,49,
            192,214,31,181,199,106,157,184,84,204,176,115,121,50,45,127,4,150,254,
            138,236,205,93,222,114,67,29,24,72,243,141,128,195,78,66,215,61,156,180
    };

    static {
        PERM      = new short[512];
        PERM_MOD12 = new short[512];
        for (int i = 0; i < 512; i++) {
            PERM[i]       = BASE_PERM[i & 255];
            PERM_MOD12[i] = (short)(PERM[i] % 12);
        }
    }

    // 12 gradient directions for 2D/3D
    private static final int[][] GRAD3 = {
            {1,1,0},{-1,1,0},{1,-1,0},{-1,-1,0},
            {1,0,1},{-1,0,1},{1,0,-1},{-1,0,-1},
            {0,1,1},{0,-1,1},{0,1,-1},{0,-1,-1}
    };

    private static final double F2 = 0.5 * (Math.sqrt(3.0) - 1.0);
    private static final double G2 = (3.0 - Math.sqrt(3.0)) / 6.0;
    private static final double F3 = 1.0 / 3.0;
    private static final double G3 = 1.0 / 6.0;

    private static int floor(double x) { int xi = (int)x; return x < xi ? xi - 1 : xi; }

    private static double dot2(int[] g, double x, double y) {
        return g[0] * x + g[1] * y;
    }
    private static double dot3(int[] g, double x, double y, double z) {
        return g[0] * x + g[1] * y + g[2] * z;
    }

    /** 2D Simplex noise. Returns a value in [-1, 1]. */
    static double simplex2D(double xin, double yin) {
        double s = (xin + yin) * F2;
        int i = floor(xin + s), j = floor(yin + s);
        double t = (i + j) * G2;
        double x0 = xin - (i - t), y0 = yin - (j - t);
        int i1, j1;
        if (x0 > y0) { i1 = 1; j1 = 0; } else { i1 = 0; j1 = 1; }
        double x1 = x0 - i1 + G2, y1 = y0 - j1 + G2;
        double x2 = x0 - 1.0 + 2.0 * G2, y2 = y0 - 1.0 + 2.0 * G2;
        int ii = i & 255, jj = j & 255;
        int gi0 = PERM_MOD12[ii      + PERM[jj     ]];
        int gi1 = PERM_MOD12[ii + i1 + PERM[jj + j1]];
        int gi2 = PERM_MOD12[ii + 1  + PERM[jj + 1 ]];
        double n0 = 0, n1 = 0, n2 = 0;
        double t0 = 0.5 - x0*x0 - y0*y0;
        if (t0 >= 0) { t0 *= t0; n0 = t0*t0 * dot2(GRAD3[gi0], x0, y0); }
        double t1 = 0.5 - x1*x1 - y1*y1;
        if (t1 >= 0) { t1 *= t1; n1 = t1*t1 * dot2(GRAD3[gi1], x1, y1); }
        double t2 = 0.5 - x2*x2 - y2*y2;
        if (t2 >= 0) { t2 *= t2; n2 = t2*t2 * dot2(GRAD3[gi2], x2, y2); }
        return 70.0 * (n0 + n1 + n2);
    }

    /** 3D Simplex noise. Returns a value in [-1, 1]. Used for cave carving. */
    static double simplex3D(double xin, double yin, double zin) {
        double s = (xin + yin + zin) * F3;
        int i = floor(xin+s), j = floor(yin+s), k = floor(zin+s);
        double t = (i+j+k) * G3;
        double x0=xin-(i-t), y0=yin-(j-t), z0=zin-(k-t);
        int i1,j1,k1,i2,j2,k2;
        if (x0>=y0) {
            if (y0>=z0)      { i1=1;j1=0;k1=0; i2=1;j2=1;k2=0; }
            else if (x0>=z0) { i1=1;j1=0;k1=0; i2=1;j2=0;k2=1; }
            else             { i1=0;j1=0;k1=1; i2=1;j2=0;k2=1; }
        } else {
            if (y0<z0)       { i1=0;j1=0;k1=1; i2=0;j2=1;k2=1; }
            else if (x0<z0)  { i1=0;j1=1;k1=0; i2=0;j2=1;k2=1; }
            else             { i1=0;j1=1;k1=0; i2=1;j2=1;k2=0; }
        }
        double x1=x0-i1+G3,y1=y0-j1+G3,z1=z0-k1+G3;
        double x2=x0-i2+2*G3,y2=y0-j2+2*G3,z2=z0-k2+2*G3;
        double x3=x0-1+3*G3,y3=y0-1+3*G3,z3=z0-1+3*G3;
        int ii=i&255,jj=j&255,kk=k&255;
        int gi0=PERM_MOD12[ii   +PERM[jj   +PERM[kk   ]]];
        int gi1=PERM_MOD12[ii+i1+PERM[jj+j1+PERM[kk+k1]]];
        int gi2=PERM_MOD12[ii+i2+PERM[jj+j2+PERM[kk+k2]]];
        int gi3=PERM_MOD12[ii+1 +PERM[jj+1 +PERM[kk+1 ]]];
        double n0=0,n1=0,n2=0,n3=0;
        double t0=0.6-x0*x0-y0*y0-z0*z0; if(t0>=0){t0*=t0;n0=t0*t0*dot3(GRAD3[gi0],x0,y0,z0);}
        double t1=0.6-x1*x1-y1*y1-z1*z1; if(t1>=0){t1*=t1;n1=t1*t1*dot3(GRAD3[gi1],x1,y1,z1);}
        double t2=0.6-x2*x2-y2*y2-z2*z2; if(t2>=0){t2*=t2;n2=t2*t2*dot3(GRAD3[gi2],x2,y2,z2);}
        double t3=0.6-x3*x3-y3*y3-z3*z3; if(t3>=0){t3*=t3;n3=t3*t3*dot3(GRAD3[gi3],x3,y3,z3);}
        return 32.0*(n0+n1+n2+n3);
    }

    // ═════════════════════════════════════════════════════════════════════
    // ChunkGenerator boilerplate
    // ═════════════════════════════════════════════════════════════════════

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
        // Vanilla carvers add ravines and mineshaft-style tunnels on top of our cave noise
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

    @Override public int getSeaLevel() { return SEA_LEVEL; }
    @Override public int getMinY()     { return settings.value().noiseSettings().minY(); }
    @Override public int getGenDepth() { return settings.value().noiseSettings().height(); }

    @Override
    public void addDebugScreenInfo(java.util.List<String> info,
                                   RandomState random, BlockPos pos) {
        if (!BiomemapLoader.isLoaded()) return;
        float rawCx = pos.getX() / (float) BiomemapLoader.MAP_SCALE + BiomemapLoader.getWidth()  * 0.5f;
        float rawCz = pos.getZ() / (float) BiomemapLoader.MAP_SCALE + BiomemapLoader.getHeight() * 0.5f;
        int color  = BiomemapLoader.getRawPixel(Math.round(rawCx), Math.round(rawCz));
        GotBiomeTerrainParams.Params p = GotBiomeTerrainParams.forColor(color);
        int   surfY = computeSurfaceY(pos.getX(), pos.getZ());
        float cave  = (float) simplex3D(pos.getX() / CAVE_STRETCH_H,
                pos.getY() / (double) CAVE_STRETCH_V,
                pos.getZ() / CAVE_STRETCH_H);
        info.add(String.format(
                "[GoT] Y=%d base=%.0f scale=%.2f cave=%.2f %s px=(%.1f,%.1f)",
                surfY, p.baseY(), p.scale(), cave,
                p.isWater() ? "WATER" : "land", rawCx, rawCz));
    }
}