package net.got.climate;

import net.got.GotMod;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

/**
 * Melts snow and ice on loaded chunks during non-winter seasons.
 *
 * Mirrors Serene Seasons' RandomUpdateHandler.meltInChunk approach:
 * each tick, a random surface column in each loaded chunk has a chance
 * to melt snow/ice if the seasonal biome temperature is warm enough (≥ 0.15).
 *
 * Melt rates by season (probability per chunk per tick):
 *   Spring — 0.10  (gradual melt)
 *   Summer — 0.25  (fast melt)
 *   Autumn — 0.03  (very slow, first frosts arriving)
 *   Winter — 0.0   (no melt)
 */
@EventBusSubscriber(modid = GotMod.MODID)
public final class SnowMeltHandler {

    private static final float WINTER_TEMP_ADJUSTMENT = -0.8f;

    /** Probability per chunk per tick that one random surface column is checked. */
    private static float meltChance(GotSeason season) {
        return switch (season) {
            case SPRING -> 0.10f;
            case SUMMER -> 0.25f;
            case AUTUMN -> 0.03f;
            case WINTER -> 0.00f;
        };
    }

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        if (event.getLevel().isClientSide()) return;
        if (!(event.getLevel() instanceof ServerLevel level)) return;
        if (!level.dimension().equals(Level.OVERWORLD)) return;

        GotSeason season = SeasonCache.get();
        float chance = meltChance(season);
        if (chance <= 0f) return;

        // Iterate every ticking chunk and maybe melt one random surface column
        level.getChunkSource().chunkMap.getChunks().forEach(chunkHolder -> {
            LevelChunk chunk = chunkHolder.getTickingChunk();
            if (chunk == null) return;
            ChunkPos chunkPos = chunk.getPos();
            if (!level.shouldTickBlocksAt(chunkPos.toLong())) return;

            if (level.random.nextFloat() >= chance) return;

            int minX = chunkPos.getMinBlockX();
            int minZ = chunkPos.getMinBlockZ();

            // Pick a random surface column, same approach as SS
            BlockPos surfaceAir = level.getHeightmapPos(
                    Heightmap.Types.MOTION_BLOCKING,
                    level.getBlockRandomPos(minX, 0, minZ, 15));
            BlockPos surfaceGround = surfaceAir.below();

            BlockState airState    = level.getBlockState(surfaceAir);
            BlockState groundState = level.getBlockState(surfaceGround);

            // Melt snow if the biome is warm enough at this position.
            // Snow melt is NOT affected by the frozen latitude line — only
            // normal season/biome temperature decides this.
            if (airState.getBlock() == Blocks.SNOW) {
                float temp = getSeasonalBiomeTemp(level, season,
                        level.getBiome(surfaceAir).value(), surfaceGround);
                if (temp >= 0.15f) {
                    level.setBlockAndUpdate(surfaceAir, Blocks.AIR.defaultBlockState());
                }
            }

            // Melt ice if the biome is warm enough at this position.
            // North of the frozen latitude line, water stays frozen regardless
            // of season/biome — that's the one effect the line still has.
            if (groundState.getBlock() == Blocks.ICE) {
                float temp = getSeasonalBiomeTempWithLatitude(level, season,
                        level.getBiome(surfaceGround).value(), surfaceGround);
                if (temp >= 0.15f) {
                    ((IceBlock) Blocks.ICE).melt(groundState, level, surfaceGround);
                }
            }
        });
    }

    /**
     * Returns the biome temperature adjusted for the current season only.
     * Used for snow melt, which the frozen latitude line does not affect.
     */
    private static float getSeasonalBiomeTemp(ServerLevel level, GotSeason season,
                                               Biome biome, BlockPos pos) {
        float base = biome.getTemperature(pos, level.getSeaLevel());

        if (biome.getBaseTemperature() > 0.8f) {
            // Hot biomes are unaffected by season.
            return Mth.clamp(base, -0.5f, 2.0f);
        }

        float adjustment = switch (season) {
            case SUMMER -> +0.15f;
            case SPRING -> +0.05f;
            case AUTUMN -> -0.20f;
            case WINTER -> WINTER_TEMP_ADJUSTMENT;
        };
        return Mth.clamp(base + adjustment, -0.5f, 2.0f);
    }

    /**
     * Same as {@link #getSeasonalBiomeTemp}, but also layers on the frozen
     * latitude adjustment. Used for ice melt only, so that water stays
     * frozen north of the line regardless of season.
     */
    private static float getSeasonalBiomeTempWithLatitude(ServerLevel level, GotSeason season,
                                                            Biome biome, BlockPos pos) {
        float base = biome.getTemperature(pos, level.getSeaLevel());
        float latitudeAdj = LatitudeClimate.temperatureAdjustment(pos.getX(), pos.getZ());

        if (biome.getBaseTemperature() > 0.8f) {
            return Mth.clamp(base + latitudeAdj, -0.5f, 2.0f);
        }

        float adjustment = switch (season) {
            case SUMMER -> +0.15f;
            case SPRING -> +0.05f;
            case AUTUMN -> -0.20f;
            case WINTER -> WINTER_TEMP_ADJUSTMENT;
        };
        return Mth.clamp(base + adjustment + latitudeAdj, -0.5f, 2.0f);
    }

    private SnowMeltHandler() {}
}
