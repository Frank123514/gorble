package net.got.event.entity.heron;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class HeronFlyGoal extends Goal {

    private static final int COOLDOWN_MIN = 600;
    
    private static final int COOLDOWN_MAX = 1800;
    
    private static final int SEARCH_RADIUS = 24;
    
    private static final float CRUISE_HEIGHT = 6.0F;
    
    private static final float FLY_SPEED = 0.28F;
    
    private static final float LIFTOFF_Y = 0.55F;
    
    private static final double DESCENT_DIST_SQ = 9.0;

    private final HeronEntity heron;
    private int cooldown;

    private BlockPos landTarget;
    
    private boolean descending;

    public HeronFlyGoal(HeronEntity heron) {
        this.heron = heron;
        
        this.cooldown = heron.getRandom().nextIntBetweenInclusive(COOLDOWN_MIN / 2, COOLDOWN_MAX / 2);
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (heron.isBaby())        return false;
        if (!heron.onGround())     return false;
        if (heron.isInWater())     return false;
        if (heron.isLeashed())     return false;
        if (--cooldown > 0)        return false;

        landTarget = findLandingSpot();
        if (landTarget == null) {
            cooldown = COOLDOWN_MIN / 4;
            return false;
        }
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        
        if (descending && heron.onGround()) return false;
        
        return !heron.isInWater();
    }

    @Override
    public void start() {
        descending = false;

        heron.setNoGravity(true);
        Vec3 motion = heron.getDeltaMovement();
        heron.setDeltaMovement(motion.x, LIFTOFF_Y, motion.z);

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
            
            descending = true;
            heron.setNoGravity(false);
            
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

    private void steerToward(boolean maintainAltitude) {
        Vec3 pos    = heron.position();
        Vec3 target = Vec3.atBottomCenterOf(landTarget);

        double dx = target.x - pos.x;
        double dz = target.z - pos.z;
        double hLen = Math.sqrt(dx * dx + dz * dz);

        if (hLen < 0.01) return;

        double nx = dx / hLen;
        double nz = dz / hLen;

        double dy = 0.0;
        if (maintainAltitude) {
            double desiredY = landTarget.getY() + CRUISE_HEIGHT;
            dy = Math.signum(desiredY - pos.y) * 0.15;
        }

        Vec3 vel = new Vec3(nx * FLY_SPEED, dy, nz * FLY_SPEED);
        heron.setDeltaMovement(vel);

        heron.setYRot((float)(Math.toDegrees(Math.atan2(-nz, nx)) + 90.0));
        heron.yBodyRot = heron.getYRot();
        heron.setXRot((float) Math.toDegrees(-Math.atan2(dy, FLY_SPEED)));
    }

    private BlockPos findLandingSpot() {
        Level level = heron.level();
        BlockPos origin = heron.blockPosition();
        var rng = heron.getRandom();

        for (int attempt = 0; attempt < 12; attempt++) {
            int ox = rng.nextIntBetweenInclusive(-SEARCH_RADIUS, SEARCH_RADIUS);
            int oz = rng.nextIntBetweenInclusive(-SEARCH_RADIUS, SEARCH_RADIUS);
            
            if (Math.abs(ox) < 6 && Math.abs(oz) < 6) continue;

            BlockPos candidate = origin.offset(ox, 0, oz);
            candidate = findSurface(level, candidate);
            if (candidate == null) continue;

            var block = level.getBlockState(candidate.below()).getBlock();
            if (block != net.minecraft.world.level.block.Blocks.GRASS_BLOCK
                    && block != net.minecraft.world.level.block.Blocks.SAND
                    && block != net.minecraft.world.level.block.Blocks.GRAVEL
                    && block != net.minecraft.world.level.block.Blocks.DIRT
                    && block != net.minecraft.world.level.block.Blocks.MUD) {
                continue;
            }

            BlockState atFeet = level.getBlockState(candidate);
            BlockState atHead = level.getBlockState(candidate.above());
            if (!atFeet.isAir() || !atHead.isAir()) continue;

            return candidate;
        }
        return null;
    }

    private static BlockPos findSurface(Level level, BlockPos pos) {
        
        int y = Math.min(pos.getY(), level.getMaxY() - 2);
        BlockPos.MutableBlockPos mut = new BlockPos.MutableBlockPos(pos.getX(), y, pos.getZ());

        for (int dy = 0; dy < 16; dy++) {
            mut.setY(y - dy);
            if (!level.getBlockState(mut).isAir()) {
                
                mut.setY(y - dy + 1);
                return mut.immutable();
            }
        }
        return null;
    }
}
