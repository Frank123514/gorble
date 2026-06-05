package net.got.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.client.animation.GotPlayerAnimator;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin {

    @Shadow
    public abstract PlayerModel getModel();

    private static final Vector3f ANIM_VEC = new Vector3f();

    /**
     * Inject at the END of setupAnim so vanilla has already positioned all bones,
     * and we layer our keyframe animation on top.
     *
     * setupAnim(RenderState) is the method that actually writes bone rotations
     * from the render state — it exists on PlayerRenderer in 1.21.4.
     */
    @Inject(method = "setupAnim", at = @At("TAIL"))
    private void got_applyAnimation(PlayerRenderState state, CallbackInfo ci) {
        var anim = GotPlayerAnimator.INSTANCE.getCurrentAnimation();
        if (anim == null) return;

        float ticks = GotPlayerAnimator.INSTANCE.getCurrentAnimationTicks();
        KeyframeAnimations.animate(
                getModel(),
                anim,
                (long)(ticks * 50F),
                1.0F,
                ANIM_VEC
        );
    }
}