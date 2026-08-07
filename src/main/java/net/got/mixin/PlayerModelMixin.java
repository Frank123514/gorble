package net.got.mixin;

import net.got.client.animation.player.GotAnimatedPlayerState;
import net.got.client.animation.player.GotPlayerAnimator;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Runs after vanilla's own {@code PlayerModel.setupAnim}, then hands the
 * (already-populated) model parts to {@link GotPlayerAnimator} to
 * overwrite the rotations for the poses this mod owns (walk, run,
 * jump, climb, punch, weapon swings, blocking — sneaking is deliberately
 * left as vanilla's default pose, see {@link GotPlayerAnimator}). See
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
 *
 * <p><b>Why this extends {@code HumanoidModel<PlayerRenderState>}:</b> as of
 * the 1.21.2 entity-render-state rewrite, {@code PlayerModel} is no longer
 * generic and extends {@code HumanoidModel<PlayerRenderState>} directly, and
 * head/hat/body/rightArm/leftArm/rightLeg/leftLeg are still declared on
 * {@code HumanoidModel}, not on {@code PlayerModel} itself. Mixin only
 * resolves {@code @Shadow} fields against classes that appear in *this*
 * class's own extends chain — it won't walk the real target's superclasses
 * on its own — so without extending {@code HumanoidModel} here, those
 * seven inherited fields fail to resolve ("was not located in the target
 * class") even though they genuinely exist at runtime. jacket/leftSleeve/
 * rightSleeve/leftPants/rightPants are declared directly on
 * {@code PlayerModel} and don't need this.
 */
@Mixin(value = PlayerModel.class, remap = false)
public abstract class PlayerModelMixin extends HumanoidModel<PlayerRenderState> {

    public PlayerModelMixin(ModelPart root) {
        super(root);
    }

    // head/hat/body/rightArm/leftArm/rightLeg/leftLeg are inherited
    // directly from HumanoidModel via the extends clause above, so
    // `this.body` etc. below already resolve without being shadowed.
    //
    // jacket/leftSleeve/rightSleeve/leftPants/rightPants aren't shadowed
    // here at all: they're children of body/arms/legs in the model's part
    // hierarchy and inherit rotation from their parent automatically at
    // render time, so this mixin never needs to touch them directly.

    @Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;)V",
            at = @At("RETURN"), remap = false)
    private void got_overridePose(PlayerRenderState state, CallbackInfo ci) {
        // `this` is passed as the Model argument KeyframeAnimations.animate
        // needs to resolve PlayerAnimations' bone names ("body", "rightArm",
        // etc.) against the real player model tree — HumanoidModel extends
        // EntityModel<PlayerRenderState> extends Model, the same way
        // GotStagModel/BellowsModel already feed themselves into that call.
        GotPlayerAnimator.apply(
                this,
                state,
                body, head,
                rightArm, leftArm,
                rightLeg, leftLeg);

        // Hide the head/hat cubes only for the one frame this is the
        // local player's own body rendering in first person (see
        // GotAnimatedPlayerState#got$isLocalFirstPerson /
        // LevelRendererMixin). This single PlayerModel instance is reused
        // to render every player each frame, so the flag must be
        // explicitly cleared here too, not just set when true — otherwise
        // the first local-first-person frame would leave heads hidden on
        // every other player rendered afterward.
        boolean hideHead = ((GotAnimatedPlayerState) state).got$isLocalFirstPerson();
        head.visible = !hideHead;
        hat.visible = !hideHead;
        // Arms ARE shown here (unlike the head/hat). ItemInHandRendererMixin
        // cancels vanilla's separate first-person hand model entirely for
        // the local player and renders this same PlayerRenderer body
        // instead, so the arms drawn here are what the player sees as
        // their own hands — full body, real armor/skin, real swing
        // animation, no separate hardcoded hand model to fight with.
    }
}