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
 * blocking. During normal walk/run it also offsets each leg/arm's
 * {@code x/y/z} position (added on top of rotation, not instead of it) so
 * the stride actually lifts the foot and reaches it forward through space
 * instead of just pivoting the limb around a fixed hip/shoulder point —
 * see {@link GotAnimMath}'s "Fourth pass" doc.
 *
 * <p><b>What this intentionally leaves alone:</b> bow/crossbow draw, eating
 * &amp; drinking, spyglass, brush, horn, spear throw, elytra flight, sleeping,
 * and riding poses. Vanilla already has bespoke poses for those, and this
 * class skips overriding an arm whenever its {@link HumanoidModel.ArmPose}
 * is one of those special poses (checked via {@code shouldOverrideArm}), and
 * skips the whole player when airborne-with-elytra or seated as a passenger.
 * That's a deliberate scope decision, not an oversight — extend the
 * {@code shouldOverrideArm} switch if more vanilla poses should be replaced
 * later. Crawling and swimming are similarly out of scope: vanilla's own
 * poses for those aren't currently overridden, and Got doesn't track those
 * render states yet.
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
            ModelPart body, ModelPart head,
            ModelPart rightArm, ModelPart leftArm,
            ModelPart rightLeg, ModelPart leftLeg) {

        // Don't fight vanilla's elytra-flight or seated-passenger poses —
        // those aren't in scope and have their own careful hand-tuned rig.
        if (state.isFallFlying || state.isPassenger) {
            return;
        }

        GotAnimatedPlayerState anim = (GotAnimatedPlayerState) state;
        float climb = anim.got$getClimbProgress();
        float airborne = anim.got$getAirborneProgress();
        float age = state.ageInTicks;

        float walkPos = state.walkAnimationPos;
        float walkSpeed = Mth.clamp(state.walkAnimationSpeed, 0.0F, 1.0F);
        // "run" is treated as a blend factor rather than a hard boolean: the
        // faster the limb-swing speed, the more we lean into the wider run
        // gait, so there's no jarring pop at the walk/sprint threshold.
        float runBlend = Mth.clamp((walkSpeed - 0.35F) / 0.35F, 0.0F, 1.0F);
        float moveIntensity = walkSpeed;

        // ── Base locomotion pose (walk/run/idle), unless overridden by climb below ──
        float bodyPitch = 0.0F, bodyYaw = 0.0F, bodyRoll = 0.0F;
        float legR, legL, armR, armL, legSwayR, legSwayL;
        float armSideR = 0.0F, armSideL = 0.0F; // arm zRot ("roll")
        float armYawR = 0.0F, armYawL = 0.0F;   // arm yRot

        // Actual limb translation (ModelPart.x/y/z), not just rotation —
        // without this the legs/arms just pivot around a fixed hip/shoulder
        // point like a rigid pendulum; this is what makes a step actually
        // lift the foot and reach it forward through space. See
        // GotAnimMath's "Fourth pass" class doc. Zeroed out (not sampled)
        // during climb/sneak since those replace the gait entirely and
        // this data is only meaningful for the normal walk/run stride.
        float legPosYR = 0.0F, legPosZR = 0.0F, legPosYL = 0.0F, legPosZL = 0.0F;
        float armPosXR = 0.0F, armPosYR = 0.0F, armPosZR = 0.0F;
        float armPosXL = 0.0F, armPosYL = 0.0F, armPosZL = 0.0F;

        if (climb > 0.01F) {
            // Climbing replaces normal locomotion entirely while active, and
            // blends back out smoothly via climbProgress as the player lets go.
            armR = Mth.lerp(climb, 0.0F, GotAnimMath.climbArmReach(age, true));
            armL = Mth.lerp(climb, 0.0F, GotAnimMath.climbArmReach(age, false));
            legR = Mth.lerp(climb, GotAnimMath.legSwing(walkPos, moveIntensity, runBlend, true), GotAnimMath.climbLegPush(age, true));
            legL = Mth.lerp(climb, GotAnimMath.legSwing(walkPos, moveIntensity, runBlend, false), GotAnimMath.climbLegPush(age, false));
            legSwayR = 0.0F;
            legSwayL = 0.0F;
            bodyPitch = GotAnimMath.climbBodyPitch() * climb;
        } else {
            // legSwing/armSwing/armYaw/armRoll are direct per-side keyframe
            // samples (see GotAnimMath's locomotion section doc) rather
            // than a symmetric sine curve, so each side is sampled
            // independently instead of negating one from the other.
            legR = GotAnimMath.legSwing(walkPos, moveIntensity, runBlend, true);
            legL = GotAnimMath.legSwing(walkPos, moveIntensity, runBlend, false);
            armR = GotAnimMath.armSwing(walkPos, moveIntensity, runBlend, true);
            armL = GotAnimMath.armSwing(walkPos, moveIntensity, runBlend, false);
            legSwayR = 0.0F;
            legSwayL = 0.0F;
            bodyPitch = GotAnimMath.torsoPitch(moveIntensity, runBlend) + GotAnimMath.jumpBodyLean(airborne);
            bodyYaw = GotAnimMath.torsoTwist(walkPos, moveIntensity, runBlend);
            bodyRoll = GotAnimMath.torsoRoll(walkPos, moveIntensity, runBlend);

            armSideR = GotAnimMath.armRoll(walkPos, moveIntensity, runBlend, true);
            armSideL = GotAnimMath.armRoll(walkPos, moveIntensity, runBlend, false);
            armYawR = GotAnimMath.armYaw(walkPos, moveIntensity, runBlend, true);
            armYawL = GotAnimMath.armYaw(walkPos, moveIntensity, runBlend, false);

            legPosYR = GotAnimMath.legPosY(walkPos, moveIntensity, runBlend, true);
            legPosZR = GotAnimMath.legPosZ(walkPos, moveIntensity, runBlend, true);
            legPosYL = GotAnimMath.legPosY(walkPos, moveIntensity, runBlend, false);
            legPosZL = GotAnimMath.legPosZ(walkPos, moveIntensity, runBlend, false);

            armPosXR = GotAnimMath.armPosX(walkPos, moveIntensity, runBlend, true);
            armPosYR = GotAnimMath.armPosY(walkPos, moveIntensity, runBlend, true);
            armPosZR = GotAnimMath.armPosZ(walkPos, moveIntensity, runBlend, true);
            armPosXL = GotAnimMath.armPosX(walkPos, moveIntensity, runBlend, false);
            armPosYL = GotAnimMath.armPosY(walkPos, moveIntensity, runBlend, false);
            armPosZL = GotAnimMath.armPosZ(walkPos, moveIntensity, runBlend, false);

            // Idle breathing sway fades in as movement stops and fades back
            // out the moment the player starts walking or leaves the ground,
            // so there's no seam between "standing still" and "just started
            // walking".
            float idleBlend = (1.0F - moveIntensity) * (1.0F - airborne);
            armSideR += GotAnimMath.idleArmSway(age) * idleBlend;
            armSideL += -GotAnimMath.idleArmSway(age) * idleBlend;
            legSwayR += GotAnimMath.idleLegSplay(true) * idleBlend;
            legSwayL += GotAnimMath.idleLegSplay(false) * idleBlend;
            bodyYaw += GotAnimMath.idleBodySway(age) * idleBlend;
        }

        // ── Sneak ────────────────────────────────────────────────────────────
        if (state.isCrouching && climb <= 0.01F) {
            bodyPitch += GotAnimMath.sneakBodyPitch();
            bodyYaw += GotAnimMath.sneakTorsoTwist(walkPos, moveIntensity);
            bodyRoll += GotAnimMath.sneakTorsoRoll(walkPos, moveIntensity);
            armR += GotAnimMath.sneakArmForward();
            armL += GotAnimMath.sneakArmForward();

            // Sneaking replaces the normal walk gait with a tighter,
            // forward-biased crouch-step, and holds the arms out to the
            // sides for balance instead of the loose walk/idle roll.
            float sneakLeg = GotAnimMath.sneakLegSwing(walkPos, moveIntensity);
            legR = sneakLeg;
            legL = -sneakLeg;
            legSwayR = 0.0F;
            legSwayL = 0.0F;
            armSideR = GotAnimMath.sneakArmSideways(true);
            armSideL = GotAnimMath.sneakArmSideways(false);
        }

        // ── Jump / airborne ──────────────────────────────────────────────────
        if (climb <= 0.01F) {
            legR += GotAnimMath.jumpLegTuck(airborne);
            legL += GotAnimMath.jumpLegTuck(airborne);
            armR -= GotAnimMath.jumpArmFlare(airborne) + GotAnimMath.jumpArmReach(airborne);
            armL += GotAnimMath.jumpArmFlare(airborne) + GotAnimMath.jumpArmReach(airborne);
        }

        body.xRot = bodyPitch;
        body.yRot = bodyYaw;
        body.zRot = bodyRoll;
        rightLeg.xRot = legR;
        leftLeg.xRot = legL;
        rightLeg.zRot = legSwayR;
        leftLeg.zRot = legSwayL;
        rightArm.xRot = armR;
        leftArm.xRot = armL;
        rightArm.zRot = armSideR;
        leftArm.zRot = armSideL;
        rightArm.yRot = armYawR;
        leftArm.yRot = armYawL;

        // Translation on top of rotation — the actual "real motion" step:
        // additive (+=) rather than assignment (=) since ModelPart.x/y/z
        // already holds the bone's rest pivot from the geometry; overwriting
        // it outright would relocate the pivot instead of offsetting it.
        rightLeg.y += legPosYR;
        rightLeg.z += legPosZR;
        leftLeg.y += legPosYL;
        leftLeg.z += legPosZL;
        rightArm.x += armPosXR;
        rightArm.y += armPosYR;
        rightArm.z += armPosZR;
        leftArm.x += armPosXL;
        leftArm.y += armPosYL;
        leftArm.z += armPosZL;

        // ── Attack swings (punch / weapon), applied per-arm ─────────────────
        float swing = Mth.clamp(state.attackTime, 0.0F, 1.0F);
        if (swing > 0.001F) {
            boolean swingingRight = state.attackArm != HumanoidArm.LEFT;
            HumanoidModel.ArmPose swingingPose = swingingRight ? state.rightArmPose : state.leftArmPose;
            if (shouldOverrideArm(swingingPose)) {
                applySwing(rightArm, leftArm, rightLeg, leftLeg, body, head, swing, swingingRight, anim.got$getSwingStyle(), anim.got$getComboIndex());
            }
        }

        // ── Blocking (shield raised) ─────────────────────────────────────────
        applyBlockIfNeeded(state.rightArmPose, rightArm, body, true);
        applyBlockIfNeeded(state.leftArmPose, leftArm, body, false);

        // Vanilla-untouched arm poses (bow, crossbow, spyglass, brush, horn,
        // spear throw) are left exactly as HumanoidModel.setupAnim already
        // set them, since shouldOverrideArm()/applySwing() skip those cases.
        //
        // hat/jacket/leftSleeve/rightSleeve/leftPants/rightPants are NOT
        // synced here on purpose: they're children of head/body/arms/legs
        // in the model's part hierarchy, so they already inherit whatever
        // rotation we just set on their parent automatically at render
        // time. Manually copying the parent's rotation onto them here
        // (as an earlier version of this method did) double-applies it —
        // that's what was causing the overlay layer to visibly swing away
        // from the body instead of tracking it.
    }

    /** Only take over an arm's rotation when it's in a "plain" pose — leave vanilla's specialized poses (bow, spyglass, etc.) untouched. */
    private static boolean shouldOverrideArm(HumanoidModel.ArmPose pose) {
        return pose == HumanoidModel.ArmPose.EMPTY || pose == HumanoidModel.ArmPose.ITEM;
    }

    /**
     * Applies the full swing pose: pitch/yaw/roll on the striking arm, a
     * counter-balance pull on the off arm, a torso twist + forward pitch
     * snap on impact, and a small same-side weight shift through the legs.
     * The off-arm/torso/leg secondary motion is new — earlier versions of
     * this method only touched the single swinging arm — added after
     * comparing against Kelvin's Better Player Animations, whose punch and
     * sword swings move the whole body, not just the striking arm.
     */
    private static void applySwing(
            ModelPart rightArm, ModelPart leftArm,
            ModelPart rightLeg, ModelPart leftLeg,
            ModelPart body, ModelPart head,
            float t, boolean rightSide, GotSwingStyle style, int comboIndex) {

        ModelPart swingArm = rightSide ? rightArm : leftArm;
        ModelPart offArm = rightSide ? leftArm : rightArm;
        ModelPart swingLeg = rightSide ? rightLeg : leftLeg;
        ModelPart offLeg = rightSide ? leftLeg : rightLeg;

        if (style == GotSwingStyle.SWORD || style == GotSwingStyle.GREATSWORD) {
            applyKeyframeSwing(rightArm, leftArm, swingLeg, offLeg, body, head, t, rightSide, style, comboIndex);
            return;
        }

        float pitch;
        float yaw = 0.0F;
        float roll = 0.0F;
        switch (style) {
            // SWORD/GREATSWORD are handled above via applyKeyframeSwing and
            // never reach this switch.
            case AXE, TRIDENT -> pitch = GotAnimMath.axeChopPitch(t);
            case TOOL -> pitch = GotAnimMath.toolStrikePitch(t);
            case PUNCH -> {
                pitch = GotAnimMath.punchPitch(t);
                yaw = GotAnimMath.punchYaw(t, rightSide);
            }
            default -> pitch = GotAnimMath.genericSwingPitch(t);
        }
        swingArm.xRot += pitch;
        swingArm.yRot += yaw;
        swingArm.zRot += roll;

        offArm.xRot -= GotAnimMath.offArmCounterPitch(t);
        offArm.zRot += GotAnimMath.offArmCounterRoll(t, !rightSide);

        body.yRot += GotAnimMath.swingBodyFollow(t, rightSide);
        body.xRot += GotAnimMath.swingBodyPitchSnap(t);

        swingLeg.xRot += GotAnimMath.swingLegWeightShift(t, rightSide);
        offLeg.xRot -= GotAnimMath.swingLegWeightShift(t, rightSide);
    }

    /**
     * SWORD/GREATSWORD swing path: unlike the sine-approximated styles in
     * {@link #applySwing}, these play back a literal keyframe-ported
     * animation from {@link GotAnimMath} (sword_attack / sword_attack_2 /
     * greatsword_attack), so arm/body rotation is <b>assigned</b> here
     * rather than added on top of the walk/idle pose already written onto
     * these {@code ModelPart}s earlier in {@link #apply} — the source clip
     * already carries the whole pose, not a delta, the same way the base
     * walk/run pose itself is assigned rather than added. Position offsets
     * stay additive ({@code +=}) since {@code ModelPart.x/y/z} holds the
     * bone's rest pivot from the geometry (see the "Fourth pass" doc on
     * {@link GotAnimMath}). The source clips are authored for a
     * right-handed swing (rightArm = weapon arm); when the player is
     * actually swinging with their left arm, both tracks are played
     * mirrored via {@link GotAnimMath#mirrorPose} instead of swapped, so
     * the off-hand still gets the (mirrored) counter-motion track rather
     * than the weapon-motion track.
     */
    private static void applyKeyframeSwing(
            ModelPart rightArm, ModelPart leftArm,
            ModelPart swingLeg, ModelPart offLeg,
            ModelPart body, ModelPart head,
            float t, boolean rightSide, GotSwingStyle style, int comboIndex) {

        GotAnimMath.LimbPose rArmSrc, lArmSrc, bodySrc, headSrc;
        if (style == GotSwingStyle.GREATSWORD) {
            rArmSrc = GotAnimMath.greatswordRightArm(t);
            lArmSrc = GotAnimMath.greatswordLeftArm(t);
            bodySrc = GotAnimMath.greatswordBody(t);
            headSrc = GotAnimMath.greatswordHead(t);
        } else if (comboIndex == 0) {
            rArmSrc = GotAnimMath.sword1RightArm(t);
            lArmSrc = GotAnimMath.sword1LeftArm(t);
            bodySrc = GotAnimMath.sword1Body(t);
            headSrc = GotAnimMath.sword1Head(t);
        } else {
            rArmSrc = GotAnimMath.sword2RightArm(t);
            lArmSrc = GotAnimMath.sword2LeftArm(t);
            bodySrc = GotAnimMath.sword2Body(t);
            headSrc = GotAnimMath.sword2Head(t);
        }

        // Right-handed swing (the common case): tracks map straight onto
        // their same-named ModelPart. Left-handed swing: both arm tracks
        // are mirrored and swapped, so the actually-swinging arm still
        // plays the weapon-motion track and the actually-idle arm still
        // plays the counter-motion track; body/head are mirrored in place
        // (not swapped — there's only one of each) since a torso twist
        // authored to bring the right arm across needs to flip direction
        // to correctly bring the left arm across instead.
        GotAnimMath.LimbPose rightPose = rightSide ? rArmSrc : GotAnimMath.mirrorPose(lArmSrc);
        GotAnimMath.LimbPose leftPose = rightSide ? lArmSrc : GotAnimMath.mirrorPose(rArmSrc);
        if (!rightSide) {
            bodySrc = GotAnimMath.mirrorPose(bodySrc);
            headSrc = GotAnimMath.mirrorPose(headSrc);
        }

        rightArm.xRot = rightPose.xRot();
        rightArm.yRot = rightPose.yRot();
        rightArm.zRot = rightPose.zRot();
        rightArm.x += rightPose.x();
        rightArm.y += rightPose.y();
        rightArm.z += rightPose.z();

        leftArm.xRot = leftPose.xRot();
        leftArm.yRot = leftPose.yRot();
        leftArm.zRot = leftPose.zRot();
        leftArm.x += leftPose.x();
        leftArm.y += leftPose.y();
        leftArm.z += leftPose.z();

        body.xRot += bodySrc.xRot();
        body.yRot += bodySrc.yRot();
        body.zRot += bodySrc.zRot();
        body.x += bodySrc.x();
        body.y += bodySrc.y();
        body.z += bodySrc.z();

        head.x += headSrc.x();
        head.y += headSrc.y();
        head.z += headSrc.z();

        swingLeg.xRot += GotAnimMath.swingLegWeightShift(t, rightSide);
        offLeg.xRot -= GotAnimMath.swingLegWeightShift(t, rightSide);
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
}