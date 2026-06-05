package net.got.mixin;

import net.got.client.animation.GotPlayerAnimator;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerModel.class)
public abstract class PlayerRendererMixin {

    private static final Vector3f GOT_ANIM_VEC = new Vector3f();

    @Inject(method = "setupAnim", at = @At("TAIL"), remap = false)
    private void got_applyAnimation(PlayerRenderState state, CallbackInfo ci) {
        var anim = GotPlayerAnimator.INSTANCE.getCurrentAnimation();
        if (anim == null) return;

        float ticks = GotPlayerAnimator.INSTANCE.getCurrentAnimationTicks();
        @SuppressWarnings("unchecked")
        PlayerModel model = (PlayerModel)(Object) this;
        KeyframeAnimations.animate(
                model,
                anim,
                (long)(ticks * 50F),
                1.0F,
                GOT_ANIM_VEC
        );
    }
}
