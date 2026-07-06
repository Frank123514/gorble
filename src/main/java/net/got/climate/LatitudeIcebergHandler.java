package net.got.climate;

import net.got.GotMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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
 * Spawns vanilla's {@code minecraft:iceberg_packed} / {@code minecraft:iceberg_blue}
 * configured features north of the frozen latitude line, at the same place
 * and with the same deterministic gradient logic as {@link LatitudeIceHandler}'s
 * surface freezing.
 *
 * <p>Right at the line, icebergs are rare and only the strongest noise peaks
 * spawn one; deep in the fade zone they're common. This reuses the exact
 * same {@link LatitudeClimate#latitudeStrength} gradient and static-noise
 * gate as the ice freezing itself (just a different noise seed / threshold
 * curve), so iceberg density visually tracks how "frozen" the water already
 * looks — no separate tuning knob to keep in sync by hand.
 *
 * <p>Runs once per chunk on {@link ChunkEvent.Load}, same timing rationale
 * as {@link LatitudeIceHandler}: fully resolved before the chunk is ever
 * shown to a client, so nothing pops in while a player is looking at it.
 */
@EventBusSubscriber(modid = GotMod.MODID)
public final class LatitudeIcebergHandler {

    // Separate seed from LatitudeIceHandler's ICE_NOISE so iceberg placement
    // doesn't just mirror the ice-freeze pattern 1:1 (that would put an
    // iceberg on literally every frozen tile near the threshold instead of
    // reading as its own sparser feature).
    private static final PerlinSimplexNoise ICEBERG_NOISE =
            new PerlinSimplexNoise(RandomSource.create(9081726354102938475L), List.of(0));

    // Icebergs are a much sparser feature than plain ice: even at full
    // saturation (strength = 1) we don't want one on every chunk, so the
    // gradient is scaled down before being used as a spawn chance.
    private static final float MAX_ICEBERG_CHANCE = 0.4f;

    // One placement attempt per chunk (at a random-but-deterministic column
    // within it), same granularity vanilla's iceberg placed-feature uses
    // (a handful of tries per chunk, not a per-block roll).
    private static final ResourceKey<ConfiguredFeature<?, ?>> ICEBERG_PACKED =
            ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.withDefaultNamespace("iceberg_packed"));
    private static final ResourceKey<ConfiguredFeature<?, ?>> ICEBERG_BLUE =
            ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.withDefaultNamespace("iceberg_blue"));

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        LevelAccessor levelAccessor = event.getLevel();
        if (levelAccessor.isClientSide()) return;
        if (!(levelAccessor instanceof ServerLevel level)) return;
        if (!level.dimension().equals(Level.OVERWORLD)) return;

        ChunkAccess chunk = event.getChunk();
        ChunkPos chunkPos = chunk.getPos();

        // Sample the gradient at the chunk centre to decide whether/what to
        // roll for the whole chunk — icebergs are a per-chunk feature, not
        // a per-block one, so there's no need to loop all 256 columns.
        int centerX = chunkPos.getMinBlockX() + 8;
        int centerZ = chunkPos.getMinBlockZ() + 8;

        float strength = LatitudeClimate.latitudeStrength(centerX, centerZ);
        if (strength <= 0f) return;

        double noise1 = ICEBERG_NOISE.getValue(centerX * 0.05, centerZ * 0.05, false);
        double noise2 = ICEBERG_NOISE.getValue(centerX * 0.015, centerZ * 0.015, false);
        double noiseNorm = ((noise1 + noise2) / 2.0 + 1.0) / 2.0; // -1..1 -> 0..1

        float chance = strength * MAX_ICEBERG_CHANCE;
        if (noiseNorm >= chance) return;

        // Pick a deterministic-but-varied spot within the chunk using the
        // same noise field, rather than an RNG roll, so re-loading the
        // chunk always makes the same decision.
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

        // Blue ice deeper in the fade zone (colder), packed ice near the edge.
        ResourceKey<ConfiguredFeature<?, ?>> featureKey =
                (strength >= 0.6f) ? ICEBERG_BLUE : ICEBERG_PACKED;

        Holder<ConfiguredFeature<?, ?>> feature = level.registryAccess()
                .lookupOrThrow(Registries.CONFIGURED_FEATURE)
                .get(featureKey)
                .orElse(null);
        if (feature == null) {
            GotMod.LOGGER.warn("[LatitudeIcebergHandler] configured feature not found: {}", featureKey.location());
            return;
        }

        RandomSource featureRandom = RandomSource.create(
                chunk.getPos().toLong() ^ 9081726354102938475L);

        boolean placed = feature.value().place(level, level.getChunkSource().getGenerator(), featureRandom, surfaceWater);
        GotMod.LOGGER.info("[LatitudeIcebergHandler] chunk {} strength={} feature={} placed={}",
                chunkPos, strength, featureKey.location(), placed);
    }

    private LatitudeIcebergHandler() {}
}