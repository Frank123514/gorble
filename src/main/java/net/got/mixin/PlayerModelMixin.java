package net.got.mixin;

import net.got.client.animation.player.AnimatedPlayerState;
import net.got.client.animation.player.HeadBobState;
import net.got.client.animation.player.PlayerAnimator;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PlayerModel.class, remap = false)
public abstract class PlayerModelMixin extends HumanoidModel<AvatarRenderState> {

    public PlayerModelMixin(ModelPart root) {
        super(root);
    }

    @Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;)V",
            at = @At("RETURN"), remap = false)
    private void got_overridePose(AvatarRenderState state, CallbackInfo ci) {
        
        PlayerAnimator.apply(
                this,
                state,
                body, head,
                rightArm, leftArm,
                rightLeg, leftLeg);

        boolean hideHead = ((AnimatedPlayerState) state).got$isLocalFirstPerson();
        head.visible = !hideHead;
        hat.visible = !hideHead;
        if (hideHead) {
            
            HeadBobState.setHeadBob(head.x, head.y);
        }
        
    }
}