package net.got.mixin;

import net.got.client.animation.player.GotAnimMath;
import net.got.client.animation.player.GotAnimatedPlayerState;
import net.got.client.animation.player.GotSwingStyle;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Populates the {@link GotAnimatedPlayerState} fields mixed onto
 * {@code PlayerRenderState} (see {@link PlayerRenderStateMixin}) each time
 * the renderer pulls a fresh snapshot from the live {@code AbstractClientPlayer}.
 *
 * <p>This is the only place in the whole custom-animation system that
 * touches the actual entity — everything downstream ({@code PlayerModelMixin}
 * / {@code GotPlayerAnimator}) only ever reads the render state, which
 * keeps us aligned with how the rest of vanilla's render-state pipeline is
 * meant to be used.
 *
 * <p><b>Confirmed at runtime (1.21.4):</b> {@code extractRenderState} is
 * {@code void (AbstractClientPlayer, PlayerRenderState, float)} — it mutates
 * the reused state in place rather than returning it, hence the plain
 * {@link CallbackInfo} here (an earlier draft assumed a returned value and
 * used {@code CallbackInfoReturnable}, which Mixin rejected at launch with
 * an "Invalid descriptor" error).
 */
@Mixin(value = PlayerRenderer.class, remap = false)
public abstract class PlayerRendererMixin {

    @Inject(method = "extractRenderState", at = @At("RETURN"), remap = false)
    private void got_extractCustomAnimState(
            AbstractClientPlayer player, PlayerRenderState state, float partialTick,
            CallbackInfo ci) {

        GotAnimatedPlayerState anim = (GotAnimatedPlayerState) state;

        float climbTarget = player.onClimbable() ? 1.0F : 0.0F;
        anim.got$setClimbProgress(GotAnimMath.approach(anim.got$getClimbProgress(), climbTarget, 0.35F));

        float airborneTarget = player.onGround() ? 0.0F : 1.0F;
        anim.got$setAirborneProgress(GotAnimMath.approach(anim.got$getAirborneProgress(), airborneTarget, 0.25F));

        anim.got$setSwingStyle(GotSwingStyle.fromItem(player.getMainHandItem()));
    }
}