package net.francis.got.mixin;

import net.francis.got.client.animation.player.HeadBobState;
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

@Mixin(value = Camera.class, remap = false)
public abstract class CameraMixin {

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

        float bobX = HeadBobState.getHeadBobX();
        float bobY = HeadBobState.getHeadBobY();
        if (bobX == 0.0F && bobY == 0.0F) {
            return;
        }

        double yawRadians = Math.toRadians(yRot());
        double leftBlocks = bobX / GOT_MODEL_UNITS_PER_BLOCK;
        double leftWorldX = -Math.cos(yawRadians) * leftBlocks;
        double leftWorldZ = -Math.sin(yawRadians) * leftBlocks;

        double upWorldY = -(bobY / GOT_MODEL_UNITS_PER_BLOCK);

        Vec3 pos = position();
        setPosition(pos.x + leftWorldX, pos.y + upWorldY, pos.z + leftWorldZ);
    }
}