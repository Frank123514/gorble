package net.got.mixin;

import net.got.client.animation.player.GotPlayerAnimator;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Runs after vanilla's own {@code PlayerModel.setupAnim}, then hands the
 * (already-populated) model parts to {@link GotPlayerAnimator} to
 * overwrite the rotations for the poses this mod owns (walk, run, sneak,
 * jump, climb, punch, weapon swings, blocking). See
 * {@link GotPlayerAnimator}'s class doc for exactly what's left untouched
 * and why.
 *
 * <p>Injecting at {@code RETURN} rather than cancelling {@code HEAD} is
 * deliberate: vanilla's pass still needs to run first for every case this
 * mod doesn't cover (bow draw, spyglass, elytra, sleeping, riding, etc.),
 * since reimplementing all of those from scratch is out of scope — we only
 * overwrite the specific rotations for the poses we do own.
 *
 * <p><b>Verification note:</b> field names below (head/hat/body/rightArm/
 * leftArm/rightLeg/leftLeg from {@code HumanoidModel}, plus jacket/sleeves/
 * pants from {@code PlayerModel} itself) are the long-stable vanilla names
 * for these cubes; the one thing genuinely new in 1.21.x is the
 * {@code setupAnim(PlayerRenderState)} single-parameter signature (replacing
 * the old entity+multiple-float-args signature), which is what's targeted
 * below. If Mixin fails to locate the method, confirm this signature against
 * a decompile of {@code PlayerModel}.
 */
@Mixin(value = PlayerModel.class, remap = false)
public abstract class PlayerModelMixin {

    @Shadow(remap = false) public ModelPart head;
    @Shadow(remap = false) public ModelPart hat;
    @Shadow(remap = false) public ModelPart body;
    @Shadow(remap = false) public ModelPart rightArm;
    @Shadow(remap = false) public ModelPart leftArm;
    @Shadow(remap = false) public ModelPart rightLeg;
    @Shadow(remap = false) public ModelPart leftLeg;
    @Shadow(remap = false) public ModelPart jacket;
    @Shadow(remap = false) public ModelPart leftSleeve;
    @Shadow(remap = false) public ModelPart rightSleeve;
    @Shadow(remap = false) public ModelPart leftPants;
    @Shadow(remap = false) public ModelPart rightPants;

    @Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;)V",
            at = @At("RETURN"), remap = false)
    private void got_overridePose(PlayerRenderState state, CallbackInfo ci) {
        GotPlayerAnimator.apply(
                state,
                head, hat, body,
                rightArm, leftArm,
                rightLeg, leftLeg,
                jacket, leftSleeve, rightSleeve,
                leftPants, rightPants);
    }
}