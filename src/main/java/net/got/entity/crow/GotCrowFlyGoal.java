package net.got.entity.crow;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

/**
 * Makes the crow occasionally take flight, glide to a new spot nearby,
 * then land.
 *
 * <p>Mirrors {@link net.got.entity.heron.GotHeronFlyGoal} but operates on
 * {@link GotCrowEntity} directly, avoiding the unsafe cross-type cast that
 * was previously used in {@link GotCrowEntity#registerGoals}.
 */
public class GotCrowFlyGoal extends Goal {

    private static final int COOLDOWN_MIN  = 400;   // 20 s
    private static final int COOLDOWN_MAX  = 1200;  // 60 s
    private static final int SEARCH_RANGE = 12;
    private static final float FLIGHT_SPEED = 0.30F;

    private final GotCrowEntity crow;
    private Vec3  target;
    private int   cooldown;
    private Phase phase = Phase.IDLE;

    private enum Phase { IDLE, LIFTOFF, CRUISE, DESCEND }

    public GotCrowFlyGoal(GotCrowEntity crow) {
        this.crow = crow;
        this.cooldown = crow.getRandom().nextIntBetweenInclusive(COOLDOWN_MIN, COOLDOWN_MAX);
        setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
    }

    // ── Goal lifecycle ────────────────────────────────────────────────────────

    @Override
    public boolean canUse() {
        if (cooldown-- > 0) return false;
        if (crow.isInWater() || crow.isBaby()) return false;
        target = findLandingSpot();
        return target != null;
    }

    @Override
    public boolean canContinueToUse() {
        return phase != Phase.IDLE && !crow.isInWater();
    }

    @Override
    public void start() {
        phase = Phase.LIFTOFF;
        crow.setNoGravity(true);
        crow.setDeltaMovement(crow.getDeltaMovement().add(0, 0.5, 0));
    }

    @Override
    public void stop() {
        crow.setNoGravity(false);
        phase = Phase.IDLE;
        cooldown = crow.getRandom().nextIntBetweenInclusive(COOLDOWN_MIN, COOLDOWN_MAX);
    }

    @Override
    public void tick() {
        if (target == null) { stop(); return; }

        double dx = target.x - crow.getX();
        double dz = target.z - crow.getZ();
        double horizDist = Math.sqrt(dx * dx + dz * dz);

        switch (phase) {
            case LIFTOFF -> {
                if (crow.getY() >= target.y + 4) phase = Phase.CRUISE;
                crow.setDeltaMovement(crow.getDeltaMovement().add(0, 0.04, 0));
            }
            case CRUISE -> {
                if (horizDist < 2.0) {
                    phase = Phase.DESCEND;
                } else {
                    double speed = FLIGHT_SPEED;
                    crow.setDeltaMovement(dx / horizDist * speed, 0, dz / horizDist * speed);
                }
            }
            case DESCEND -> {
                crow.setNoGravity(false);
                if (crow.onGround()) stop();
            }
            default -> stop();
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private Vec3 findLandingSpot() {
        var rng = crow.getRandom();
        double ox = (rng.nextDouble() * 2 - 1) * SEARCH_RANGE;
        double oz = (rng.nextDouble() * 2 - 1) * SEARCH_RANGE;
        var level = crow.level();
        var pos = crow.blockPosition().offset((int) ox, 0, (int) oz);
        // Walk down to find solid ground
        for (int dy = 4; dy >= -4; dy--) {
            var check = pos.above(dy);
            if (level.getBlockState(check.below()).isSolid()
                    && level.isEmptyBlock(check)
                    && level.isEmptyBlock(check.above())) {
                return Vec3.atBottomCenterOf(check);
            }
        }
        return null;
    }
}
