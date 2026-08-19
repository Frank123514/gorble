package net.francis.got.mixin;

import net.francis.got.client.animation.player.FirstPersonRenderState;
import net.minecraft.client.Camera;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Pose;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LevelRenderer.class, remap = false)
public abstract class LevelRendererMixin {

    @Unique
    private static final float GOT_STANDING_RENDER_OFFSET = 0.35F;

    @Unique
    private static final float GOT_SNEAK_RENDER_OFFSET = 0.37F;

    @Unique
    private double got$offsetX, got$offsetY, got$offsetZ;

    @Unique
    private boolean got$offsetApplied;

    @Redirect(
            method = "extractVisibleEntities(Lnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/culling/Frustum;Lnet/minecraft/client/DeltaTracker;Lnet/minecraft/client/renderer/state/LevelRenderState;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;isDetached()Z")
    )
    private boolean got_forceRenderLocalPlayerBody(Camera camera) {
        if (Minecraft.getInstance().options.getCameraType() == CameraType.FIRST_PERSON) {
            return true;
        }
        return camera.isDetached();
    }

    @Inject(method = "extractEntity(Lnet/minecraft/world/entity/Entity;F)Lnet/minecraft/client/renderer/entity/state/EntityRenderState;",
            at = @At("HEAD"))
    private void got_applyRenderOffset(Entity entity, float partialTick, CallbackInfoReturnable<EntityRenderState> cir) {
        got$offsetApplied = false;

        Minecraft mc = Minecraft.getInstance();
        boolean isLocalFirstPersonBody = entity == mc.player
                && mc.options.getCameraType() == CameraType.FIRST_PERSON;
        
        FirstPersonRenderState.setRenderingLocalBody(isLocalFirstPersonBody);

        if (!(entity instanceof LivingEntity living) || !isLocalFirstPersonBody || mc.player.isSleeping()) {
            return;
        }

        float realYaw = Mth.rotLerp(partialTick, living.yBodyRotO, living.yBodyRot);
        float renderOffset = (living.isCrouching() || living.getPose() == Pose.CROUCHING)
                ? GOT_SNEAK_RENDER_OFFSET
                : GOT_STANDING_RENDER_OFFSET;
        double x = renderOffset * Math.sin(Math.toRadians(realYaw));
        double z = -renderOffset * Math.cos(Math.toRadians(realYaw));

        got$offsetX = x;
        got$offsetY = 0.0D;
        got$offsetZ = z;
        got$offsetApplied = true;

        EntityPositionAccessor access = (EntityPositionAccessor) entity;
        access.got$setRawPosition(access.got$getRawPosition().add(got$offsetX, got$offsetY, got$offsetZ));
        entity.xo += got$offsetX;
        entity.yo += got$offsetY;
        entity.zo += got$offsetZ;
        entity.xOld += got$offsetX;
        entity.yOld += got$offsetY;
        entity.zOld += got$offsetZ;
    }

    @Inject(method = "extractEntity(Lnet/minecraft/world/entity/Entity;F)Lnet/minecraft/client/renderer/entity/state/EntityRenderState;",
            at = @At("RETURN"))
    private void got_restoreRenderOffset(Entity entity, float partialTick, CallbackInfoReturnable<EntityRenderState> cir) {
        FirstPersonRenderState.setRenderingLocalBody(false);

        if (!got$offsetApplied) {
            return;
        }
        got$offsetApplied = false;

        EntityPositionAccessor access = (EntityPositionAccessor) entity;
        access.got$setRawPosition(access.got$getRawPosition().subtract(got$offsetX, got$offsetY, got$offsetZ));
        entity.xo -= got$offsetX;
        entity.yo -= got$offsetY;
        entity.zo -= got$offsetZ;
        entity.xOld -= got$offsetX;
        entity.yOld -= got$offsetY;
        entity.zOld -= got$offsetZ;
    }
}