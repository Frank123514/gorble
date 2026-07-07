package net.got.climate;

import net.got.GotMod;
import net.got.worldgen.biome.GotConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.ChunkEvent;

import java.util.List;

/**
 * Places {@code got:noisy_patch_snow} (the speckled surface-snow feature
 * defined in {@link GotConfiguredFeatures#NOISY_PATCH_SNOW}) on land north of
 * the frozen latitude line, growing more frequent the further north you go.
 *
 * <p>This mirrors {@link LatitudeIcebergHandler} exactly — same per-chunk
 * gradient approach, same {@link LatitudeClimate#latitudeStrength} curve —
 * just placing a land feature instead of a water one. It's intentionally
 * separate from the normal biome-driven placement pipeline
 * ({@code GotPlacedFeatures#NOISY_PATCH_SNOW}, which uses a flat
 * {@code RarityFilter} and applies per-biome rather than by latitude): this
 * handler is gated purely on distance north of the line, so density here
 * tracks the same gradient the ice and icebergs already use rather than a
 * separate hand-tuned rate.
 *
 * <p>Runs once per chunk on {@link ChunkEvent.Load}, same timing as
 * {@link LatitudeIceHandler} and {@link LatitudeIcebergHandler} — fully
 * resolved before the chunk is ever shown to a client.
 */
@EventBusSubscriber(modid = GotMod.MODID)
public final class LatitudeSnowPatchHandler {

    // Separate seed from the ice/iceberg noise fields so snow-patch placement
    // doesn't just mirror either of those 1:1 — it should read as its own
    // pattern of patches, not literally overlap with where ice/icebergs land.
    private static final PerlinSimplexNoise SNOW_PATCH_NOISE =
            new PerlinSimplexNoise(RandomSource.create(4820193756102938475L), List.of(0));

    // One placement attempt per chunk, same granularity as icebergs. At
    // strength = 0 (right at the line) chance is 0 — nothing extra right at
    // the boundary — ramping up to MAX_SNOW_PATCH_CHANCE at full latitude
    // strength, so patches get denser the further north you go, same
    // "gradual, not a hard switch" shape as everything else in this system.
    private static final float MAX_SNOW_PATCH_CHANCE = 0.9f;

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        LevelAccessor levelAccessor = event.getLevel();
        if (levelAccessor.isClientSide()) return;
        if (!(levelAccessor instanceof ServerLevel level)) return;
        if (!level.dimension().equals(Level.OVERWORLD)) return;

        ChunkAccess chunk = event.getChunk();
        ChunkPos chunkPos = chunk.getPos();

        // Sample the gradient at the chunk centre — this is a per-chunk
        // feature, not a per-block one, so there's no need to loop all 256
        // columns like the plain ice freeze does.
        int centerX = chunkPos.getMinBlockX() + 8;
        int centerZ = chunkPos.getMinBlockZ() + 8;

        float strength = LatitudeClimate.latitudeStrength(centerX, centerZ);
        if (strength <= 0f) return;

        double noise1 = SNOW_PATCH_NOISE.getValue(centerX * 0.05, centerZ * 0.05, false);
        double noise2 = SNOW_PATCH_NOISE.getValue(centerX * 0.015, centerZ * 0.015, false);
        double noiseNorm = ((noise1 + noise2) / 2.0 + 1.0) / 2.0; // -1..1 -> 0..1

        float chance = strength * MAX_SNOW_PATCH_CHANCE;
        if (noiseNorm >= chance) return;

        // Deterministic-but-varied spot within the chunk, same trick as
        // LatitudeIcebergHandler — reload the chunk, get the same answer.
        int offsetX = (int) (((noise1 + 1.0) / 2.0) * 16.0);
        int offsetZ = (int) (((noise2 + 1.0) / 2.0) * 16.0);
        int worldX = chunkPos.getMinBlockX() + Math.min(15, Math.max(0, offsetX));
        int worldZ = chunkPos.getMinBlockZ() + Math.min(15, Math.max(0, offsetZ));

        int surfaceY = chunk.getHeight(Heightmap.Types.MOTION_BLOCKING, worldX, worldZ);
        BlockPos surfacePos = new BlockPos(worldX, surfaceY, worldZ);

        // Land feature only — skip if the heightmap surface here is open water.
        BlockState atSurface = chunk.getBlockState(surfacePos);
        if (atSurface.getBlock() == Blocks.WATER) return;

        Holder<ConfiguredFeature<?, ?>> feature = level.registryAccess()
                .lookupOrThrow(Registries.CONFIGURED_FEATURE)
                .get(GotConfiguredFeatures.NOISY_PATCH_SNOW)
                .orElse(null);
        if (feature == null) {
            GotMod.LOGGER.warn("[LatitudeSnowPatchHandler] configured feature not found: {}",
                    GotConfiguredFeatures.NOISY_PATCH_SNOW.location());
            return;
        }

        RandomSource featureRandom = RandomSource.create(
                chunk.getPos().toLong() ^ 4820193756102938475L);

        feature.value().place(level, level.getChunkSource().getGenerator(), featureRandom, surfacePos);
    }

    private LatitudeSnowPatchHandler() {}
}