package net.got.mixin;

import net.got.client.animation.player.GotHeadBobState;
import net.minecraft.client.Camera;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Nudges the actual first-person camera left/right and up/down to track
 * {@link GotHeadBobState} — the local player's own animated {@code head}
 * ModelPart's walk/run bob, which {@code PlayerModelMixin} publishes there
 * every frame it's computed. Without this, the walk/run head-sway clips
 * only ever move the (now-invisible-in-first-person, see
 * {@code PlayerModelMixin}) head cube itself, which nobody watching
 * through their own eyes could ever actually see — the animation existed
 * but had no visible effect on the one render the local player spends all
 * their time looking at.
 *
 * <p><b>Position only, never rotation:</b> intentional, not an oversight —
 * see {@link GotHeadBobState}'s class doc. Applying the head channel's
 * rotation here too would physically tilt the player's real view (RUNNING
 * pitches the head model 18 degrees while sprinting), which would make
 * looking around/aiming while running actively worse to play, not just
 * cosmetically different. Only translation is borrowed.
 *
 * <p><b>Z (forward/back) is also dropped, not just rotation:</b> the
 * WALKING/RUNNING head channels barely move on Z at all (a constant -1/-3
 * offset, not an oscillation), so there's nothing there worth reproducing,
 * and it sidesteps needing to pin down this model's local +Z convention
 * (forward-facing vs. rear-facing) with certainty. X (left/right) and Y
 * (up/down) are the two axes that actually oscillate in these clips, and
 * both have an unambiguous mapping: {@code HumanoidModel}'s own arm pivots
 * (right_arm at x=-5, left_arm at x=+5) confirm local +X is the
 * character's own left side, and model Y is the long-standing
 * down-is-positive Minecraft cube convention, so world-up is {@code -y}.
 *
 * <p><b>One-frame lag:</b> see {@link GotHeadBobState}'s class doc —
 * {@code Camera.setup} runs before this frame's entity render pass
 * recomputes the head pose, so this is always reading last frame's value.
 * Not perceptible for a smooth continuous curve at normal framerates.
 *
 * <p><b>Confirmed against the official 1.21.11 Mojang mappings:</b> two of
 * the three shadowed accessors were renamed from their 1.21.4 names —
 * {@code getPosition()} is now {@code position()} and {@code getYRot()} is
 * now {@code yRot()}. {@code setPosition(double, double, double)} is
 * unchanged. {@code setup}'s method name is unchanged, but its first
 * parameter is {@code Level}, not {@code BlockGetter} — the actual runtime
 * type was always {@code Level} (an earlier draft loosened it to the
 * {@code BlockGetter} interface Level implements, which doesn't match the
 * real erased descriptor Mixin matches against).
 */
@Mixin(value = Camera.class, remap = false)
public abstract class CameraMixin {

    /** {@code head.x}/{@code head.y} are in 1/16-block model units; this converts to actual blocks. */
    private static final float GOT_MODEL_UNITS_PER_BLOCK = 16.0F;

    @Shadow
    public abstract Vec3 position();

    @Shadow
    protected abstract void setPosition(double x, double y, double z);

    @Shadow
    public abstract float yRot();

    @Inject(method = "setup(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/Entity;ZZF)V",
            at = @At("TAIL"), remap = false)
    private void got_applyHeadBobToCamera(Level level, Entity entity, boolean detached,
                                          boolean thirdPersonMirrored, float partialTick, CallbackInfo ci) {
        if (detached) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        if (entity != mc.player || mc.options.getCameraType() != CameraType.FIRST_PERSON) {
            return;
        }

        float bobX = GotHeadBobState.getHeadBobX();
        float bobY = GotHeadBobState.getHeadBobY();
        if (bobX == 0.0F && bobY == 0.0F) {
            return;
        }

        // Local +X (character's own left) rotated into world space by the
        // camera's current yaw, matching the same sin/cos-from-yaw pattern
        // LevelRendererMixin's own render-offset push already uses.
        double yawRadians = Math.toRadians(yRot());
        double leftBlocks = bobX / GOT_MODEL_UNITS_PER_BLOCK;
        double leftWorldX = -Math.cos(yawRadians) * leftBlocks;
        double leftWorldZ = -Math.sin(yawRadians) * leftBlocks;

        // Model Y is down-positive, so world-up is the negation.
        double upWorldY = -(bobY / GOT_MODEL_UNITS_PER_BLOCK);

        Vec3 pos = position();
        setPosition(pos.x + leftWorldX, pos.y + upWorldY, pos.z + leftWorldZ);
    }
}