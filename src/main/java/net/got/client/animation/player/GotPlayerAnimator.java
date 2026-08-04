package net.got.client.animation.player;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

/**
 * Writes {@link GotAnimMath} results onto the real player {@link ModelPart}s,
 * fully replacing vanilla's rotations for the poses Got owns: idle sway,
 * walk, run, sneak, jump/airborne, climbing, punch, weapon swings and
 * blocking.
 *
 * <p><b>What this intentionally leaves alone:</b> bow/crossbow draw, eating
 * &amp; drinking, spyglass, brush, horn, spear throw, elytra flight, sleeping,
 * and riding poses. Vanilla already has bespoke poses for those, and this
 * class skips overriding an arm whenever its {@link HumanoidModel.ArmPose}
 * is one of those special poses (checked via {@code shouldOverrideArm}), and
 * skips the whole player when airborne-with-elytra or seated as a passenger.
 * That's a deliberate scope decision, not an oversight — extend the
 * {@code shouldOverrideArm} switch if more vanilla poses should be replaced
 * later.
 *
 * <p><b>Verification note:</b> this project mixes into named/Parchment
 * mappings directly rather than SRG (see other mixins' {@code remap=false}
 * comments), which lets these field names below be written as their real
 * 1.21.4 Mojang-mapped names. They were written from memory of the
 * render-state refactor and not compiled in this environment (no network
 * access to the NeoForge/Mojang maven repos here) — if the mod fails to
 * compile, {@code PlayerRenderState}/{@code HumanoidRenderState} field
 * names are the first thing to check against decompiled sources
 * (Mojang mappings via a local Gradle {@code genSources}/IDE decompile).
 * The fields used are: {@code walkAnimationPos}, {@code walkAnimationSpeed},
 * {@code ageInTicks}, {@code isCrouching}, {@code attackTime},
 * {@code attackArm}, {@code leftArmPose}, {@code rightArmPose},
 * {@code isFallFlying}, {@code isPassenger}.
 */
public final class GotPlayerAnimator {

    private GotPlayerAnimator() {}

    public static void apply(
            PlayerRenderState state,
            ModelPart head, ModelPart hat, ModelPart body,
            ModelPart rightArm, ModelPart leftArm,
            ModelPart rightLeg, ModelPart leftLeg,
            ModelPart jacket, ModelPart leftSleeve, ModelPart rightSleeve,
            ModelPart leftPants, ModelPart rightPants) {

        // Don't fight vanilla's elytra-flight or seated-passenger poses —
        // those aren't in scope and have their own careful hand-tuned rig.
        if (state.isFallFlying || state.isPassenger) {
            syncOverlayLayers(head, hat, body, jacket, rightArm, rightSleeve, leftArm, leftSleeve, rightLeg, rightPants, leftLeg, leftPants);
            return;
        }

        GotAnimatedPlayerState anim = (GotAnimatedPlayerState) state;
        float climb = anim.got$getClimbProgress();
        float airborne = anim.got$getAirborneProgress();

        float walkPos = state.walkAnimationPos;
        float walkSpeed = Mth.clamp(state.walkAnimationSpeed, 0.0F, 1.0F);
        // "run" is treated as a blend factor rather than a hard boolean: the
        // faster the limb-swing speed, the more we lean into the wider run
        // gait, so there's no jarring pop at the walk/sprint threshold.
        float runBlend = Mth.clamp((walkSpeed - 0.35F) / 0.35F, 0.0F, 1.0F);
        float moveIntensity = walkSpeed;

        // ── Base locomotion pose (walk/run), unless overridden by climb below ──
        float bodyPitch = 0.0F;
        float legR, legL, armR, armL, legSwayR, legSwayL;

        if (climb > 0.01F) {
            // Climbing replaces normal locomotion entirely while active, and
            // blends back out smoothly via climbProgress as the player lets go.
            float age = state.ageInTicks;
            armR = Mth.lerp(climb, 0.0F, GotAnimMath.climbArmReach(age, true));
            armL = Mth.lerp(climb, 0.0F, GotAnimMath.climbArmReach(age, false));
            legR = Mth.lerp(climb, GotAnimMath.legSwing(walkPos, moveIntensity, runBlend), GotAnimMath.climbLegPush(age, true));
            legL = Mth.lerp(climb, GotAnimMath.legSwing(walkPos, moveIntensity, runBlend), GotAnimMath.climbLegPush(age, false));
            legSwayR = 0.0F;
            legSwayL = 0.0F;
            bodyPitch = GotAnimMath.climbBodyPitch() * climb;
        } else {
            legR = GotAnimMath.legSwing(walkPos, moveIntensity, runBlend);
            legL = -legR;
            armR = -GotAnimMath.armSwing(walkPos, moveIntensity, runBlend);
            armL = GotAnimMath.armSwing(walkPos, moveIntensity, runBlend);
            legSwayR = GotAnimMath.legSway(walkPos, moveIntensity, runBlend);
            legSwayL = -legSwayR;
            bodyPitch = GotAnimMath.runTorsoLean(runBlend);
        }

        // ── Sneak ────────────────────────────────────────────────────────────
        if (state.isCrouching && climb <= 0.01F) {
            bodyPitch += GotAnimMath.sneakBodyPitch();
            armR += GotAnimMath.sneakArmForward();
            armL += GotAnimMath.sneakArmForward();
        }

        // ── Jump / airborne ──────────────────────────────────────────────────
        if (climb <= 0.01F) {
            legR += GotAnimMath.jumpLegTuck(airborne);
            legL += GotAnimMath.jumpLegTuck(airborne);
            armR -= GotAnimMath.jumpArmFlare(airborne);
            armL += GotAnimMath.jumpArmFlare(airborne);
        }

        body.xRot = bodyPitch;
        body.yRot = 0.0F;
        rightLeg.xRot = legR;
        leftLeg.xRot = legL;
        rightLeg.zRot = legSwayR;
        leftLeg.zRot = legSwayL;
        rightArm.xRot = armR;
        leftArm.xRot = armL;
        rightArm.zRot = 0.0F;
        leftArm.zRot = 0.0F;
        rightArm.yRot = 0.0F;
        leftArm.yRot = 0.0F;

        // ── Attack swings (punch / weapon), applied per-arm ─────────────────
        float swing = Mth.clamp(state.attackTime, 0.0F, 1.0F);
        if (swing > 0.001F) {
            boolean swingingRight = state.attackArm != HumanoidArm.LEFT;
            if (shouldOverrideArm(swingingRight ? state.rightArmPose : state.leftArmPose)) {
                applySwing(swingingRight ? rightArm : leftArm, body, swing, swingingRight, anim.got$getSwingStyle());
            }
        }

        // ── Blocking (shield raised) ─────────────────────────────────────────
        applyBlockIfNeeded(state.rightArmPose, rightArm, body, true);
        applyBlockIfNeeded(state.leftArmPose, leftArm, body, false);

        // Vanilla-untouched arm poses (bow, crossbow, spyglass, brush, horn,
        // spear throw) are left exactly as HumanoidModel.setupAnim already
        // set them, since shouldOverrideArm()/applySwing() skip those cases.

        syncOverlayLayers(head, hat, body, jacket, rightArm, rightSleeve, leftArm, leftSleeve, rightLeg, rightPants, leftLeg, leftPants);
    }

    /** Only take over an arm's rotation when it's in a "plain" pose — leave vanilla's specialized poses (bow, spyglass, etc.) untouched. */
    private static boolean shouldOverrideArm(HumanoidModel.ArmPose pose) {
        return pose == HumanoidModel.ArmPose.EMPTY || pose == HumanoidModel.ArmPose.ITEM;
    }

    private static void applySwing(ModelPart arm, ModelPart body, float t, boolean rightSide, GotSwingStyle style) {
        float pitch;
        float yaw = 0.0F;
        switch (style) {
            case SWORD -> {
                pitch = GotAnimMath.swordSlashPitch(t);
                yaw = GotAnimMath.swordSlashYaw(t, rightSide);
            }
            case AXE, TRIDENT -> pitch = GotAnimMath.axeChopPitch(t);
            case TOOL -> pitch = GotAnimMath.toolStrikePitch(t);
            case PUNCH -> pitch = GotAnimMath.punchPitch(t);
            default -> pitch = GotAnimMath.genericSwingPitch(t);
        }
        arm.xRot += pitch;
        arm.yRot += yaw;
        body.yRot += GotAnimMath.swingBodyFollow(t, rightSide);
    }

    private static void applyBlockIfNeeded(HumanoidModel.ArmPose pose, ModelPart arm, ModelPart body, boolean rightSide) {
        if (pose != HumanoidModel.ArmPose.BLOCK) {
            return;
        }
        // BLOCK pose is fully custom (not blended with the swing/walk pose
        // above) since raising a shield should look the same whether the
        // player is standing still or mid-stride.
        float progress = 1.0F;
        arm.xRot = GotAnimMath.blockArmPitch(progress);
        arm.yRot = GotAnimMath.blockArmYawAcrossChest(progress, rightSide);
        arm.zRot = 0.0F;
        body.yRot += GotAnimMath.blockBodyLean(progress) * (rightSide ? 1.0F : -1.0F);
    }

    /** Vanilla PlayerModel keeps the skin-overlay cubes (hat/jacket/sleeves/pants) mirroring the base cubes each frame — we must redo that copy since we changed the base cubes after vanilla already did its own copy. */
    private static void syncOverlayLayers(
            ModelPart head, ModelPart hat, ModelPart body, ModelPart jacket,
            ModelPart rightArm, ModelPart rightSleeve, ModelPart leftArm, ModelPart leftSleeve,
            ModelPart rightLeg, ModelPart rightPants, ModelPart leftLeg, ModelPart leftPants) {
        hat.copyFrom(head);
        jacket.copyFrom(body);
        rightSleeve.copyFrom(rightArm);
        leftSleeve.copyFrom(leftArm);
        rightPants.copyFrom(rightLeg);
        leftPants.copyFrom(leftLeg);
    }
}
