package net.got.client.armor;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

/**
 * Snapshot of how a cloak wearer is moving, taken when the render state is extracted and read by
 * {@link CloakBodyModel} to swing the back panel.
 *
 * @param forward -1..1: how much of the travel direction is along the way the body faces (1 = walking forwards)
 * @param sprint  0..1: how far above walking pace the wearer is
 * @param fall    0..1: how fast the wearer is dropping (the cloak billows up while falling)
 */
public record CloakMotion(float forward, float sprint, float fall) {

    /** Used when no data was captured (treated as "if it moves, it moves forwards"). */
    public static final CloakMotion STILL = new CloakMotion(1.0F, 0.0F, 0.0F);

    /** Works for remote players too: uses the interpolated position delta, not the (unsynced) velocity. */
    public static CloakMotion of(LivingEntity entity) {
        double dx = entity.getX() - entity.xo;
        double dy = entity.getY() - entity.yo;
        double dz = entity.getZ() - entity.zo;
        double horizontal = Math.sqrt(dx * dx + dz * dz);

        float forward = 1.0F;
        if (horizontal > 1.0E-3D) {
            float yaw = (float) Math.toRadians(entity.yBodyRot);
            double facingX = -Mth.sin(yaw);
            double facingZ = Mth.cos(yaw);
            forward = (float) Mth.clamp((dx * facingX + dz * facingZ) / horizontal, -1.0D, 1.0D);
        }
        float sprint = Mth.clamp((float) ((horizontal - 0.2D) / 0.1D), 0.0F, 1.0F);
        float fall = Mth.clamp((float) (-dy - 0.15D) * 1.5F, 0.0F, 1.0F);
        return new CloakMotion(forward, sprint, fall);
    }
}
