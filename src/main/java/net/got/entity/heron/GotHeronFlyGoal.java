package net.got.entity.heron;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

/**
 * Makes the heron occasionally take flight, glide to a new spot nearby,
 * then land.
 *
 * <h3>Flight lifecycle</h3>
 * <ol>
 *   <li><b>Liftoff</b> — entity gains upward velocity and {@code noGravity} is
 *       set so it doesn't immediately fall back down.</li>
 *   <li><b>Cruise</b> — each tick the heron steers toward the chosen landing
 *       point at a fixed flight speed, levelling off when it is roughly above
 *       the target.</li>
 *   <li><b>Descent</b> — once horizontally close, gravity is restored and the
 *       entity glides down naturally until {@code onGround()} is true.</li>
 *   <li><b>Cooldown</b> — after landing the goal does not start again for a
 *       random interval (30–90 s).</li>
 * </ol>
 *
 * <p>Because the heron extends {@link net.minecraft.world.entity.animal.Animal}
 * (not a flying animal), we manage gravity manually rather than relying on
 * a {@code FlyingMoveControl}. This keeps the rest of the entity simple.
 */
public class GotHeronFlyGoal extends Goal {

    // ── Tunables ─────────────────────────────────────────────────────────────

    /** Minimum ticks between two flight attempts (30 s at 20 tps). */
    private static final int COOLDOWN_MIN = 600;
    /** Maximum ticks between two flight attempts (90 s at 20 tps). */
    private static final int COOLDOWN_MAX = 1800;
    /** How far (blocks) the heron looks for a landing spot. */
    private static final int SEARCH_RADIUS = 24;
    /** Cruise altitude above the ground (blocks). */
    private static final float CRUISE_HEIGHT = 6.0F;
    /** Horizontal speed while flying (blocks/tick). */
    private static final float FLY_SPEED = 0.28F;
    /** Liftoff vertical impulse (blocks/tick). */
    private static final float LIFTOFF_Y = 0.55F;
    /** Horizontal distance (sq) at which descent begins. */
    private static final double DESCENT_DIST_SQ = 9.0;   // ~3 blocks

    // ── State ────────────────────────────────────────────────────────────────

    private final GotHeronEntity heron;
    private int cooldown;

    /** World-space landing target chosen at liftoff. */
    private BlockPos landTarget;
    /** Whether the heron is currently descending toward the target. */
    private boolean descending;

    // ── Construction ─────────────────────────────────────────────────────────

    public GotHeronFlyGoal(GotHeronEntity heron) {
        this.heron = heron;
        // Start with a random offset so not every heron in a group flies at once.
        this.cooldown = heron.getRandom().nextIntBetweenInclusive(COOLDOWN_MIN / 2, COOLDOWN_MAX / 2);
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    // ── Goal lifecycle ────────────────────────────────────────────────────────

    @Override
    public boolean canUse() {
        if (heron.isBaby())        return false;
        if (!heron.onGround())     return false;
        if (heron.isInWater())     return false;
        if (heron.isLeashed())     return false;
        if (--cooldown > 0)        return false;

        // Pick a reachable landing spot; if none found, back off cooldown.
        landTarget = findLandingSpot();
        if (landTarget == null) {
            cooldown = COOLDOWN_MIN / 4;
            return false;
        }
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        // Stop if we've landed after descending.
        if (descending && heron.onGround()) return false;
        // Stop if we've been in the air a very long time (safety valve).
        return !heron.isInWater();
    }

    @Override
    public void start() {
        descending = false;

        // Liftoff impulse.
        heron.setNoGravity(true);
        Vec3 motion = heron.getDeltaMovement();
        heron.setDeltaMovement(motion.x, LIFTOFF_Y, motion.z);

        // Point the heron toward the target immediately.
        steerToward(false);
    }

    @Override
    public void tick() {
        Vec3 pos     = heron.position();
        Vec3 target  = Vec3.atBottomCenterOf(landTarget);
        double dx    = target.x - pos.x;
        double dz    = target.z - pos.z;
        double distSq = dx * dx + dz * dz;

        if (!descending && distSq < DESCENT_DIST_SQ) {
            // Switch to descent: restore gravity and let the entity fall.
            descending = true;
            heron.setNoGravity(false);
            // Kill horizontal speed so it drops nearly straight down.
            heron.setDeltaMovement(heron.getDeltaMovement().multiply(0.3, 1.0, 0.3));
            return;
        }

        if (!descending) {
            steerToward(true);
        }
    }

    @Override
    public void stop() {
        heron.setNoGravity(false);
        cooldown = heron.getRandom().nextIntBetweenInclusive(COOLDOWN_MIN, COOLDOWN_MAX);
        landTarget = null;
        descending = false;
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    /**
     * Steers the heron toward {@link #landTarget} at cruise altitude when
     * {@code level} is true, or just applies forward velocity if not.
     */
    private void steerToward(boolean maintainAltitude) {
        Vec3 pos    = heron.position();
        Vec3 target = Vec3.atBottomCenterOf(landTarget);

        double dx = target.x - pos.x;
        double dz = target.z - pos.z;
        double hLen = Math.sqrt(dx * dx + dz * dz);

        if (hLen < 0.01) return;

        // Normalised horizontal direction.
        double nx = dx / hLen;
        double nz = dz / hLen;

        // Vertical component: climb/descend toward cruise altitude.
        double dy = 0.0;
        if (maintainAltitude) {
            double desiredY = landTarget.getY() + CRUISE_HEIGHT;
            dy = Math.signum(desiredY - pos.y) * 0.15;
        }

        Vec3 vel = new Vec3(nx * FLY_SPEED, dy, nz * FLY_SPEED);
        heron.setDeltaMovement(vel);

        // Face direction of travel.
        heron.setYRot((float)(Math.toDegrees(Math.atan2(-nz, nx)) + 90.0));
        heron.yBodyRot = heron.getYRot();
        heron.setXRot((float) Math.toDegrees(-Math.atan2(dy, FLY_SPEED)));
    }

    /**
     * Searches for a solid-topped block within {@link #SEARCH_RADIUS} that is
     * suitable for the heron to land on (grass, sand, gravel, or water-adjacent
     * ground — same criteria as spawn rules).
     *
     * @return a valid landing {@link BlockPos}, or {@code null} if none found.
     */
    private BlockPos findLandingSpot() {
        Level level = heron.level();
        BlockPos origin = heron.blockPosition();
        var rng = heron.getRandom();

        for (int attempt = 0; attempt < 12; attempt++) {
            int ox = rng.nextIntBetweenInclusive(-SEARCH_RADIUS, SEARCH_RADIUS);
            int oz = rng.nextIntBetweenInclusive(-SEARCH_RADIUS, SEARCH_RADIUS);
            // Minimum distance so the heron actually travels somewhere.
            if (Math.abs(ox) < 6 && Math.abs(oz) < 6) continue;

            BlockPos candidate = origin.offset(ox, 0, oz);
            candidate = findSurface(level, candidate);
            if (candidate == null) continue;

            // Same surface checks as spawn rules.
            var block = level.getBlockState(candidate.below()).getBlock();
            if (block != net.minecraft.world.level.block.Blocks.GRASS_BLOCK
                    && block != net.minecraft.world.level.block.Blocks.SAND
                    && block != net.minecraft.world.level.block.Blocks.GRAVEL
                    && block != net.minecraft.world.level.block.Blocks.DIRT
                    && block != net.minecraft.world.level.block.Blocks.MUD) {
                continue;
            }

            // Must be able to stand there (not inside a block).
            BlockState atFeet = level.getBlockState(candidate);
            BlockState atHead = level.getBlockState(candidate.above());
            if (!atFeet.isAir() || !atHead.isAir()) continue;

            return candidate;
        }
        return null;
    }

    /**
     * Walks down from {@code pos} (or up if underground) to find the top
     * solid surface within a 16-block vertical range.
     */
    private static BlockPos findSurface(Level level, BlockPos pos) {
        // Clamp to world height range.
        int y = Math.min(pos.getY(), level.getMaxY() - 2);
        BlockPos.MutableBlockPos mut = new BlockPos.MutableBlockPos(pos.getX(), y, pos.getZ());

        // Walk down until we hit a solid block.
        for (int dy = 0; dy < 16; dy++) {
            mut.setY(y - dy);
            if (!level.getBlockState(mut).isAir()) {
                // Surface is one above the solid block.
                mut.setY(y - dy + 1);
                return mut.immutable();
            }
        }
        return null;
    }
}
