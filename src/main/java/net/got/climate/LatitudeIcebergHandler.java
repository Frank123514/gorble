package net.got.climate;

import net.got.GotMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
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

@EventBusSubscriber(modid = GotMod.MODID)
public final class LatitudeIcebergHandler {

    private static final PerlinSimplexNoise ICEBERG_NOISE =
            new PerlinSimplexNoise(RandomSource.create(9081726354102938475L), List.of(0));

    private static final float MAX_ICEBERG_CHANCE = 0.4f;

    private static final ResourceKey<ConfiguredFeature<?, ?>> ICEBERG_PACKED =
            ResourceKey.create(Registries.CONFIGURED_FEATURE, Identifier.withDefaultNamespace("iceberg_packed"));
    private static final ResourceKey<ConfiguredFeature<?, ?>> ICEBERG_BLUE =
            ResourceKey.create(Registries.CONFIGURED_FEATURE, Identifier.withDefaultNamespace("iceberg_blue"));

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        LevelAccessor levelAccessor = event.getLevel();
        if (levelAccessor.isClientSide()) return;
        if (!(levelAccessor instanceof ServerLevel level)) return;
        if (!level.dimension().equals(Level.OVERWORLD)) return;

        ChunkAccess chunk = event.getChunk();
        ChunkPos chunkPos = chunk.getPos();

        int centerX = chunkPos.getMinBlockX() + 8;
        int centerZ = chunkPos.getMinBlockZ() + 8;

        float strength = LatitudeClimate.latitudeStrength(centerX, centerZ);
        if (strength <= 0f) return;

        double noise1 = ICEBERG_NOISE.getValue(centerX * 0.05, centerZ * 0.05, false);
        double noise2 = ICEBERG_NOISE.getValue(centerX * 0.015, centerZ * 0.015, false);
        double noiseNorm = ((noise1 + noise2) / 2.0 + 1.0) / 2.0;

        float chance = strength * MAX_ICEBERG_CHANCE;
        if (noiseNorm >= chance) return;

        int offsetX = (int) (((noise1 + 1.0) / 2.0) * 16.0);
        int offsetZ = (int) (((noise2 + 1.0) / 2.0) * 16.0);
        int worldX = chunkPos.getMinBlockX() + Math.min(15, Math.max(0, offsetX));
        int worldZ = chunkPos.getMinBlockZ() + Math.min(15, Math.max(0, offsetZ));

        int surfaceY = chunk.getHeight(Heightmap.Types.MOTION_BLOCKING, worldX, worldZ);
        BlockPos heightmapPos = new BlockPos(worldX, surfaceY, worldZ);
        BlockState atHeightmap = chunk.getBlockState(heightmapPos);
        BlockPos surfaceWater = (atHeightmap.getBlock() == Blocks.WATER)
                ? heightmapPos
                : heightmapPos.below();

        BlockState waterState = chunk.getBlockState(surfaceWater);
        if (waterState.getBlock() != Blocks.WATER) return;
        if (!waterState.getFluidState().isSource()) return;

        ResourceKey<ConfiguredFeature<?, ?>> featureKey =
                (strength >= 0.6f) ? ICEBERG_BLUE : ICEBERG_PACKED;

        Holder<ConfiguredFeature<?, ?>> feature = level.registryAccess()
                .lookupOrThrow(Registries.CONFIGURED_FEATURE)
                .get(featureKey)
                .orElse(null);
        if (feature == null) {
            GotMod.LOGGER.warn("[LatitudeIcebergHandler] configured feature not found: {}", featureKey.identifier());
            return;
        }

        RandomSource featureRandom = RandomSource.create(
                chunk.getPos().toLong() ^ 9081726354102938475L);

        boolean placed = feature.value().place(level, level.getChunkSource().getGenerator(), featureRandom, surfaceWater);
        GotMod.LOGGER.info("[LatitudeIcebergHandler] chunk {} strength={} feature={} placed={}",
                chunkPos, strength, featureKey.identifier(), placed);
    }

    private LatitudeIcebergHandler() {}
}