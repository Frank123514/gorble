package net.got.climate;

import net.got.GotMod;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ChunkHolder;
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

@EventBusSubscriber(modid = GotMod.MODID)
public final class SnowMeltHandler {

    private static final float WINTER_TEMP_ADJUSTMENT = -0.8f;

    private static float meltChance(Season season) {
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

        Season season = SeasonCache.get();
        float chance = meltChance(season);
        if (chance <= 0f) return;

        // roll a random surface block per loaded chunk each tick, melt snow/ice if warm enough
        for (ChunkHolder holder : level.getChunkSource().chunkMap.visibleChunkMap.values()) {
            LevelChunk chunk = holder.getTickingChunk();
            if (chunk == null) continue;

            ChunkPos chunkPos = chunk.getPos();
            if (!level.shouldTickBlocksAt(chunkPos.toLong())) continue;

            if (level.random.nextFloat() >= chance) continue;

            int minX = chunkPos.getMinBlockX();
            int minZ = chunkPos.getMinBlockZ();

            BlockPos surfaceAir = level.getHeightmapPos(
                    Heightmap.Types.MOTION_BLOCKING,
                    level.getBlockRandomPos(minX, 0, minZ, 15));
            BlockPos surfaceGround = surfaceAir.below();

            BlockState airState    = level.getBlockState(surfaceAir);
            BlockState groundState = level.getBlockState(surfaceGround);

            if (airState.getBlock() == Blocks.SNOW) {
                float temp = getSeasonalBiomeTemp(level, season, level.getBiome(surfaceAir).value(), surfaceGround);
                if (temp >= 0.15f) {
                    level.setBlockAndUpdate(surfaceAir, Blocks.AIR.defaultBlockState());
                }
            }

            if (groundState.getBlock() == Blocks.ICE) {
                float temp = getSeasonalBiomeTempWithLatitude(level, season,
                        level.getBiome(surfaceGround).value(), surfaceGround);
                if (temp >= 0.15f) {
                    ((IceBlock) Blocks.ICE).melt(groundState, level, surfaceGround);
                }
            }
        }
    }

    private static float getSeasonalBiomeTemp(ServerLevel level, Season season, Biome biome, BlockPos pos) {
        float base = biome.getTemperature(pos, level.getSeaLevel());

        if (biome.getBaseTemperature() > 0.8f) {
            return Mth.clamp(base, -0.5f, 2.0f);
        }

        float adjustment = switch (season) {
            case SUMMER -> +0.15f;
            case SPRING -> +0.05f;
            case AUTUMN -> -0.20f;
            case WINTER -> WINTER_TEMP_ADJUSTMENT;
        };

        // colder biomes warm up less in summer/spring than warmer ones
        if (adjustment > 0f) {
            float coldness = 1f - Mth.clamp(base / 0.5f, 0f, 1f);
            float warmingScale = Mth.lerp(coldness, 1f, 0.15f);
            adjustment *= warmingScale;
        }

        return Mth.clamp(base + adjustment, -0.5f, 2.0f);
    }

    private static float getSeasonalBiomeTempWithLatitude(ServerLevel level, Season season,
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

        if (adjustment > 0f) {
            float coldness = 1f - Mth.clamp(base / 0.5f, 0f, 1f);
            float warmingScale = Mth.lerp(coldness, 1f, 0.15f);
            adjustment *= warmingScale;
        }

        return Mth.clamp(base + adjustment + latitudeAdj, -0.5f, 2.0f);
    }

    private SnowMeltHandler() {}
}