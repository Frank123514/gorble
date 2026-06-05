package net.got.mixin;

import net.got.client.animation.GotPlayerAnimator;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.client.renderer.MultiBufferSource;
import com.mojang.blaze3d.vertex.PoseStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Injects our combat animation AFTER vanilla's setupAnim() has set bone poses.
 *
 * The injection point is the HEAD of renderToBuffer, which executes after
 * setupAnim() but before vertices are emitted — so our KeyframeAnimations
 * call is the last thing to touch the bones.
 *
 * We avoid @Shadow entirely (getModel() is on the parent LivingEntityRenderer,
 * not declared on PlayerRenderer, so Mixin can't shadow it without a refMap).
 * Instead we cast `this` to PlayerRenderer and call getModel() directly —
 * it's a public method so no access tricks needed.
 *
 * remap=false: same pattern as the other mixins in this project.
 */
@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {

    @Inject(
        method = "renderToBuffer",
        at = @At("HEAD"),
        remap = false
    )
    @SuppressWarnings({"rawtypes", "unchecked"})
    private void gotAnim_applyAfterSetupAnim(
            PlayerRenderState renderState,
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight,
            CallbackInfo ci) {

        GotPlayerAnimator animator = GotPlayerAnimator.INSTANCE;
        if (!animator.hasActiveAnimation()) return;

        // Cast this to PlayerRenderer — safe since we're inside a mixin on it.
        // getModel() is public and inherited from LivingEntityRenderer.
        PlayerRenderer self = (PlayerRenderer) (Object) this;
        PlayerModel model = (PlayerModel) self.getModel();
        if (model == null) return;

        animator.applyToModel(model);
    }
}
