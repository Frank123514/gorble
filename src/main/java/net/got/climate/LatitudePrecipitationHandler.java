package net.got.climate;

import net.got.GotMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

@EventBusSubscriber(modid = GotMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public final class LatitudePrecipitationHandler {

    @SubscribeEvent
    public static void onLevelTickPost(LevelTickEvent.Post event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;
        if (!level.isRaining()) return;

        // Mirror vanilla's tickChunk precipitation loop exactly:
        // for every chunk that vanilla is ticking, sample one random column
        for (var player : level.players()) {
            ChunkPos center = player.chunkPosition();
            // 11x11 = matches vanilla's RAIN_RADIUS=10 particle area
            for (int dx = -11; dx <= 11; dx++) {
                for (int dz = -11; dz <= 11; dz++) {
                    int cx = center.x + dx;
                    int cz = center.z + dz;
                    LevelChunk chunk = level.getChunkSource().getChunkNow(cx, cz);
                    if (chunk == null) continue;

                    int x = (cx << 4) + level.random.nextInt(16);
                    int z = (cz << 4) + level.random.nextInt(16);
                    BlockPos surfacePos = level.getHeightmapPos(
                            Heightmap.Types.MOTION_BLOCKING, new BlockPos(x, 0, z));

                    // Use BlockPos version so noise is applied to the boundary
                    float latTemp = ClimateSystem.getLatitudeTemperature(surfacePos);

                    if (latTemp < 0.15f) {
                        tryPlaceSnow(level, surfacePos);
                        tryFreezeWater(level, surfacePos);
                    } else {
                        tryMeltSnow(level, surfacePos, latTemp);
                    }
                }
            }
        }
    }

    private static void tryPlaceSnow(ServerLevel level, BlockPos pos) {
        if (level.getBrightness(LightLayer.BLOCK, pos) >= 10) return;

        BlockState existing = level.getBlockState(pos);
        if (existing.isAir()) {
            BlockPos below = pos.below();
            if (level.getBlockState(below).isFaceSturdy(level, below, Direction.UP)) {
                level.setBlockAndUpdate(pos, Blocks.SNOW.defaultBlockState());
            }
        } else if (existing.is(Blocks.SNOW)) {
            int layers = existing.getValue(SnowLayerBlock.LAYERS);
            if (layers < 2 && level.random.nextInt(3) == 0) {
                level.setBlockAndUpdate(pos, existing.setValue(SnowLayerBlock.LAYERS, layers + 1));
            }
        }
    }

    private static void tryFreezeWater(ServerLevel level, BlockPos pos) {
        BlockPos waterPos = pos.below();
        if (level.getBlockState(waterPos).is(Blocks.WATER)) {
            if (level.getBrightness(LightLayer.BLOCK, waterPos) < 10) {
                level.setBlockAndUpdate(waterPos, Blocks.ICE.defaultBlockState());
            }
        }
    }

    private static void tryMeltSnow(ServerLevel level, BlockPos pos, float latTemp) {
        BlockState state = level.getBlockState(pos);
        if (state.is(Blocks.SNOW) || state.is(Blocks.SNOW_BLOCK)) {
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        }
        if (latTemp > 0.45f) {
            BlockState below = level.getBlockState(pos.below());
            if (below.is(Blocks.ICE)) {
                level.setBlockAndUpdate(pos.below(), Blocks.WATER.defaultBlockState());
            }
        }
    }
}