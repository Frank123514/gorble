package net.got.client;

import net.got.worldgen.RiverFlowMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;

/**
 * Purely cosmetic: spawns vanilla water bubble particles that drift along
 * the local river current, so the flow direction computed by
 * {@link RiverFlowMap} is actually visible in-world.
 *
 * <p>Client-side only — particles are spawned locally per-player (like
 * vanilla's own ambient water particles) rather than synced from the
 * server, so there's no network cost.
 */
public final class RiverCurrentParticles {

    /** How many candidate spawn points to test per client tick. */
    private static final int ATTEMPTS_PER_TICK = 3;
    /** Horizontal search radius around the player, in blocks. */
    private static final int RADIUS = 24;
    /** How fast the bubble particle drifts along the current. */
    private static final double DRIFT_SPEED = 0.06;

    private RiverCurrentParticles() {}

    public static void init() {
        NeoForge.EVENT_BUS.addListener(RiverCurrentParticles::onClientTick);
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel level = mc.level;
        if (level == null || mc.player == null) return;

        RandomSource random = level.random;
        BlockPos playerPos = mc.player.blockPosition();

        for (int i = 0; i < ATTEMPTS_PER_TICK; i++) {
            int x = playerPos.getX() + random.nextInt(RADIUS * 2 + 1) - RADIUS;
            int z = playerPos.getZ() + random.nextInt(RADIUS * 2 + 1) - RADIUS;

            RiverFlowMap.FlowVector flow = RiverFlowMap.flowAt(x, z);
            if (flow == null) continue; // not in a connected river — no current here

            // MOTION_BLOCKING treats fluids as "blocking", so this gives the
            // first air position above the water column — i.e. the water's
            // top surface, not the riverbed. (OCEAN_FLOOR_WG, used here
            // before, ignores fluids entirely and pointed at the bottom of
            // the river instead — that was the bug.)
            int surfaceY  = level.getHeight(Heightmap.Types.MOTION_BLOCKING, x, z);
            int topWaterY = surfaceY - 1;
            BlockPos waterPos = new BlockPos(x, topWaterY, z);
            if (!level.getFluidState(waterPos).is(FluidTags.WATER)) continue;

            double px = x + random.nextDouble();
            double pz = z + random.nextDouble();
            double py = topWaterY + 0.75 + random.nextDouble() * 0.2; // just under the surface

            level.addParticle(ParticleTypes.BUBBLE,
                    px, py, pz,
                    flow.dx() * DRIFT_SPEED,
                    0.01,
                    flow.dz() * DRIFT_SPEED);
        }
    }
}
