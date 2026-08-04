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

/**
 * Purely cosmetic: renders vanilla water bubble particles that appear to
 * ride the river current computed by {@link RiverFlowMap}.
 *
 * <p>The current is treated as a field that already exists everywhere,
 * all the time — not something spawned fresh around the player. World
 * space is covered by a fixed lattice of "lanes" spaced {@link
 * #LANE_SPACING} blocks apart. Each lane has exactly one bubble whose
 * position along the local flow direction is a deterministic function of
 * the current game time and that lane's own phase offset:
 * {@code position = f(worldPos, gameTime)}, never {@code f(playerPos,
 * random)}. A lane the player has never been near is "flowing" in
 * exactly the same sense as one right under them; nearby lanes are
 * simply the ones close enough to sample and render on a given tick.
 * That's why this reads as a current sliding past the player instead of
 * a cloud of bubbles that spawns and piles up around them.
 *
 * <p>Client-side only — particles are spawned locally per-player (like
 * vanilla's own ambient water particles) rather than synced from the
 * server, so there's no network cost.
 *
 * <p>Gated on {@link Minecraft#isPaused()}: {@code ClientTickEvent.Post}
 * keeps firing every real-time frame even while the pause menu is open,
 * but {@code level.getGameTime()} freezes since the level stops ticking.
 * Without this guard, every frame the menu was open re-ran with the same
 * frozen game time, landed the same lane bucket, and spawned another
 * particle stacked on the exact same spot — hence bubbles piling up and
 * merging while the menu sat open.
 */
public final class RiverCurrentParticles {

    /** Spacing between lattice lanes, in blocks. */
    private static final double LANE_SPACING = 2.5;
    /** Horizontal search radius around the player, in blocks. */
    private static final int RADIUS = 20;
    /** Distance a lane's bubble travels along the current before looping back to its start. */
    private static final double CYCLE_LENGTH = 6.0;
    /** How long, in ticks, a full loop over CYCLE_LENGTH takes. */
    private static final double CYCLE_TICKS = 90.0;
    /** Bubble particle's own spawn velocity, so it keeps drifting between lane refreshes too. */
    private static final double DRIFT_SPEED = CYCLE_LENGTH / CYCLE_TICKS;
    /** Lanes are split into this many buckets; one bucket is refreshed per tick. */
    private static final int BUCKETS = 4;

    private RiverCurrentParticles() {}

    public static void init() {
        NeoForge.EVENT_BUS.addListener(RiverCurrentParticles::onClientTick);
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel level = mc.level;
        if (level == null || mc.player == null) return;
        if (mc.isPaused()) return; // see class doc — gameTime freezes but this event doesn't

        long gameTime = level.getGameTime();
        int activeBucket = (int) (gameTime % BUCKETS);

        BlockPos playerPos = mc.player.blockPosition();
        double minX = playerPos.getX() - RADIUS;
        double maxX = playerPos.getX() + RADIUS;
        double minZ = playerPos.getZ() - RADIUS;
        double maxZ = playerPos.getZ() + RADIUS;

        // Snap the search window onto the lattice so lanes are stable in
        // world space regardless of where the player happens to be standing.
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
                if (flow == null) continue; // not a connected river — no current here

                // Slide this lane's bubble back and forth along the flow
                // direction, looping seamlessly. laneHash phase-offsets each
                // lane so they don't all pulse in sync with each other.
                double phase = (laneHash & 0xFFFF) / (double) 0xFFFF;
                double t = ((gameTime + phase * CYCLE_TICKS) % CYCLE_TICKS) / CYCLE_TICKS;
                double travel = t * CYCLE_LENGTH - CYCLE_LENGTH * 0.5;

                // Small deterministic per-lane lateral jitter so the lattice
                // doesn't read as a visible grid.
                double jitterX = ((laneHash >> 4) & 0xF) / 15.0 - 0.5;
                double jitterZ = ((laneHash >> 8) & 0xF) / 15.0 - 0.5;

                double px = lx + 0.5 + jitterX + flow.dx() * travel;
                double pz = lz + 0.5 + jitterZ + flow.dz() * travel;
                int wx = (int) Math.floor(px);
                int wz = (int) Math.floor(pz);

                // MOTION_BLOCKING treats fluids as "blocking", so this gives
                // the first air position above the water column — i.e. the
                // water's top surface, not the riverbed.
                int surfaceY  = level.getHeight(Heightmap.Types.MOTION_BLOCKING, wx, wz);
                int topWaterY = surfaceY - 1;
                BlockPos waterPos = new BlockPos(wx, topWaterY, wz);
                if (!level.getFluidState(waterPos).is(FluidTags.WATER)) continue;

                double py = topWaterY + 0.75 + ((laneHash >> 12) & 0x7) / 35.0; // just under the surface

                level.addParticle(ParticleTypes.BUBBLE,
                        px, py, pz,
                        flow.dx() * DRIFT_SPEED,
                        0.0,
                        flow.dz() * DRIFT_SPEED);
            }
        }
    }

    /** Deterministic, position-only hash — same lane always hashes the same way. */
    private static int laneHash(double lx, double lz) {
        int ix = (int) Math.round(lx * 4);
        int iz = (int) Math.round(lz * 4);
        int h = ix * 374761393 + iz * 668265263;
        h = (h ^ (h >>> 13)) * 1274126177;
        return h ^ (h >>> 16);
    }
}
