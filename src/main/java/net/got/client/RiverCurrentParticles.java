package net.got.client;

import net.got.worldgen.RiverFlowMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;

public final class RiverCurrentParticles {

    private static final double LANE_SPACING = 2.5;
    
    private static final int RADIUS = 20;
    
    private static final double CYCLE_LENGTH = 6.0;
    
    private static final double CYCLE_TICKS = 90.0;
    
    private static final double DRIFT_SPEED = CYCLE_LENGTH / CYCLE_TICKS;
    
    private static final int BUCKETS = 4;

    private RiverCurrentParticles() {}

    public static void init() {
        NeoForge.EVENT_BUS.addListener(RiverCurrentParticles::onClientTick);
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel level = mc.level;
        if (level == null || mc.player == null) return;
        if (mc.isPaused()) return;

        long gameTime = level.getGameTime();
        int activeBucket = (int) (gameTime % BUCKETS);

        BlockPos playerPos = mc.player.blockPosition();
        double minX = playerPos.getX() - RADIUS;
        double maxX = playerPos.getX() + RADIUS;
        double minZ = playerPos.getZ() - RADIUS;
        double maxZ = playerPos.getZ() + RADIUS;

        double startX = Math.floor(minX / LANE_SPACING) * LANE_SPACING;
        double startZ = Math.floor(minZ / LANE_SPACING) * LANE_SPACING;

        for (double lx = startX; lx <= maxX; lx += LANE_SPACING) {
            for (double lz = startZ; lz <= maxZ; lz += LANE_SPACING) {
                int laneHash = laneHash(lx, lz);
                if (Math.floorMod(laneHash, BUCKETS) != activeBucket) continue;

                double ddx = lx - playerPos.getX();
                double ddz = lz - playerPos.getZ();
                if (ddx * ddx + ddz * ddz > RADIUS * RADIUS) continue;

                RiverFlowMap.FlowVector flow =
                        RiverFlowMap.flowAt((int) Math.floor(lx), (int) Math.floor(lz));
                if (flow == null) continue;

                double phase = (laneHash & 0xFFFF) / (double) 0xFFFF;
                double t = ((gameTime + phase * CYCLE_TICKS) % CYCLE_TICKS) / CYCLE_TICKS;
                double travel = t * CYCLE_LENGTH - CYCLE_LENGTH * 0.5;

                double jitterX = ((laneHash >> 4) & 0xF) / 15.0 - 0.5;
                double jitterZ = ((laneHash >> 8) & 0xF) / 15.0 - 0.5;

                double px = lx + 0.5 + jitterX + flow.dx() * travel;
                double pz = lz + 0.5 + jitterZ + flow.dz() * travel;
                int wx = (int) Math.floor(px);
                int wz = (int) Math.floor(pz);

                int surfaceY  = level.getHeight(Heightmap.Types.MOTION_BLOCKING, wx, wz);
                int topWaterY = surfaceY - 1;
                BlockPos waterPos = new BlockPos(wx, topWaterY, wz);
                if (!level.getFluidState(waterPos).is(FluidTags.WATER)) continue;

                double py = topWaterY + 0.75 + ((laneHash >> 12) & 0x7) / 35.0;

                level.addParticle(ParticleTypes.BUBBLE,
                        px, py, pz,
                        flow.dx() * DRIFT_SPEED,
                        0.0,
                        flow.dz() * DRIFT_SPEED);
            }
        }
    }

    private static int laneHash(double lx, double lz) {
        int ix = (int) Math.round(lx * 4);
        int iz = (int) Math.round(lz * 4);
        int h = ix * 374761393 + iz * 668265263;
        h = (h ^ (h >>> 13)) * 1274126177;
        return h ^ (h >>> 16);
    }
}
