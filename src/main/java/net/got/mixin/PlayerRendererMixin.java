package net.got.mixin;

import net.got.client.animation.player.AnimMath;
import net.got.client.animation.player.AnimatedPlayerState;
import net.got.client.animation.player.FirstPersonRenderState;
import net.got.client.animation.player.SwingStyle;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = AvatarRenderer.class, remap = false)
public abstract class PlayerRendererMixin {

    @Inject(method = "extractRenderState", at = @At("RETURN"), remap = false)
    private void got_extractCustomAnimState(
            Avatar entity, AvatarRenderState state, float partialTick,
            CallbackInfo ci) {

        AbstractClientPlayer player = (AbstractClientPlayer) entity;
        AnimatedPlayerState anim = (AnimatedPlayerState) state;

        float climbTarget = player.onClimbable() ? 1.0F : 0.0F;
        anim.got$setClimbProgress(AnimMath.approach(anim.got$getClimbProgress(), climbTarget, 0.35F));

        float airborneTarget = player.onGround() ? 0.0F : 1.0F;
        anim.got$setAirborneProgress(AnimMath.approach(anim.got$getAirborneProgress(), airborneTarget, 0.25F));

        float sprintTarget = player.isSprinting() ? 1.0F : 0.0F;
        anim.got$setSprintProgress(AnimMath.approach(anim.got$getSprintProgress(), sprintTarget, 0.35F));

        anim.got$setSwingStyle(SwingStyle.fromItem(player.getMainHandItem()));

        Minecraft mc = Minecraft.getInstance();
        MultiPlayerGameMode gameMode = mc.gameMode;
        boolean miningWithAxe = mc.player == player
                && gameMode != null
                && gameMode.isDestroying()
                && SwingStyle.fromItem(player.getMainHandItem()) == SwingStyle.AXE;
        anim.got$setMiningWithAxe(miningWithAxe);

        anim.got$setLocalFirstPerson(
                mc.player == player
                        && mc.options.getCameraType() == CameraType.FIRST_PERSON
                        && FirstPersonRenderState.isRenderingLocalBody());

        Entity vehicle = player.getVehicle();
        boolean ridingHorse = vehicle instanceof AbstractHorse;
        anim.got$setRidingHorse(ridingHorse);
        float horseRunTarget = ridingHorse && vehicle.getDeltaMovement().horizontalDistanceSqr() > 0.0025
                ? 1.0F
                : 0.0F;
        anim.got$setHorseRunBlend(AnimMath.approach(anim.got$getHorseRunBlend(), horseRunTarget, 0.2F));

        float swingNow = Mth.clamp(state.attackTime, 0.0F, 1.0F);
        if (!miningWithAxe && anim.got$getPrevSwing() < 0.02F && swingNow >= 0.02F) {
            anim.got$setComboIndex((anim.got$getComboIndex() + 1) % 2);
            
            anim.got$setSwingStartAge(state.ageInTicks);
        }
        anim.got$setPrevSwing(swingNow);

        if (miningWithAxe) {
            
            anim.got$setSwingStartAge(state.ageInTicks - 1.0E6F);
        }
    }
}