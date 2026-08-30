package net.got.client.animation.player;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.WeakHashMap;

public final class PlayerAnimator {

    private PlayerAnimator() {}

    private static final Vector3f ANIM_VEC = new Vector3f();

    private static final Map<AnimationDefinition, WeakHashMap<ModelPart, KeyframeAnimation>> BAKED_ANIMATIONS =
            new IdentityHashMap<>();

    private static KeyframeAnimation baked(AnimationDefinition definition, ModelPart root) {
        return BAKED_ANIMATIONS
                .computeIfAbsent(definition, d -> new WeakHashMap<>())
                .computeIfAbsent(root, definition::bake);
    }

    private static final float MINING_LOOP_SPEED = 3.0F;

    private static final float FIRST_PERSON_BODY_SWAY_DAMPEN = 0.4F;

    public static void apply(
            Model model,
            AvatarRenderState state,
            ModelPart body, ModelPart head,
            ModelPart rightArm, ModelPart leftArm,
            ModelPart rightLeg, ModelPart leftLeg) {

        if (state.isFallFlying) {
            return;
        }

        AnimatedPlayerState anim = (AnimatedPlayerState) state;

        boolean firstPerson = anim.got$isLocalFirstPerson();

        if (state.isPassenger) {

            if (anim.got$isRidingHorse()) {
                body.resetPose();
                head.resetPose();
                rightArm.resetPose();
                leftArm.resetPose();
                rightLeg.resetPose();
                leftLeg.resetPose();

                float horseRun = anim.got$getHorseRunBlend();
                long ms = (long) (state.ageInTicks * 50F);
                baked(PlayerAnimations.HORSE_IDLE, model.root()).apply(ms, 1.0F - horseRun);
                baked(PlayerAnimations.HORSE_RUNNING, model.root()).apply(ms, horseRun);
            }
            return;
        }

        float climb = anim.got$getClimbProgress();
        float airborne = anim.got$getAirborneProgress();
        float age = state.ageInTicks;

        float walkPos = state.walkAnimationPos;
        float walkSpeed = Mth.clamp(state.walkAnimationSpeed, 0.0F, 1.0F);

        float runBlend = anim.got$getSprintProgress();
        float moveIntensity = walkSpeed;

        boolean sneaking = climb <= 0.01F && state.isCrouching;
        boolean blocking = state.rightArmPose == HumanoidModel.ArmPose.BLOCK
                || state.leftArmPose == HumanoidModel.ArmPose.BLOCK;

        if (!sneaking && !blocking) {

            body.resetPose();
            head.resetPose();
            rightArm.resetPose();
            leftArm.resetPose();
            rightLeg.resetPose();
            leftLeg.resetPose();

            if (climb > 0.01F) {

                float armR = Mth.lerp(climb, 0.0F, AnimMath.climbArmReach(age, true));
                float armL = Mth.lerp(climb, 0.0F, AnimMath.climbArmReach(age, false));
                float legR = Mth.lerp(climb, 0.0F, AnimMath.climbLegPush(age, true));
                float legL = Mth.lerp(climb, 0.0F, AnimMath.climbLegPush(age, false));
                body.xRot = AnimMath.climbBodyPitch() * climb;
                rightArm.xRot = armR;
                leftArm.xRot = armL;
                rightLeg.xRot = legR;
                leftLeg.xRot = legL;
            } else {

                long walkMs = (long) (walkPos * 50F);
                baked(PlayerAnimations.WALKING, model.root()).apply(walkMs, (1.0F - runBlend) * moveIntensity);
                baked(PlayerAnimations.RUNNING, model.root()).apply(walkMs, runBlend * moveIntensity);

                float idleBlend = (1.0F - moveIntensity) * (1.0F - airborne);
                rightArm.zRot += AnimMath.idleArmSway(age) * idleBlend;
                leftArm.zRot += -AnimMath.idleArmSway(age) * idleBlend;
                rightLeg.zRot += AnimMath.idleLegSplay(true) * idleBlend;
                leftLeg.zRot += AnimMath.idleLegSplay(false) * idleBlend;
                body.yRot += AnimMath.idleBodySway(age) * idleBlend;
            }

            if (airborne > 0.01F) {
                baked(PlayerAnimations.JUMP, model.root()).apply((long) (age * 50F), airborne);
            }

            if (firstPerson) {
                body.xRot *= FIRST_PERSON_BODY_SWAY_DAMPEN;
                body.yRot *= FIRST_PERSON_BODY_SWAY_DAMPEN;
                body.zRot *= FIRST_PERSON_BODY_SWAY_DAMPEN;
                body.x *= FIRST_PERSON_BODY_SWAY_DAMPEN;
                body.y *= FIRST_PERSON_BODY_SWAY_DAMPEN;
                body.z *= FIRST_PERSON_BODY_SWAY_DAMPEN;
            }

            if (airborne > 0.01F) {

                attachRotationToBody(rightArm, body);
                attachRotationToBody(leftArm, body);
                attachRotationToBody(head, body);
            }
        }

        SwingStyle style = anim.got$getSwingStyle();
        int comboIndex = anim.got$getComboIndex();
        float swingDuration = swingDurationTicks(style, comboIndex);
        float swing;
        boolean swingActive;
        if (style == SwingStyle.AXE && anim.got$isMiningWithAxe()) {

            float miningDuration = swingDuration / MINING_LOOP_SPEED;
            swing = (age % miningDuration) / miningDuration;
            swingActive = true;
        } else {
            float swingElapsed = age - anim.got$getSwingStartAge();
            swing = Mth.clamp(swingElapsed / swingDuration, 0.0F, 1.0F);
            swingActive = swingElapsed >= 0.0F && swing < 1.0F;
        }
        if (swingActive) {
            boolean swingingRight = state.attackArm != HumanoidArm.LEFT;
            HumanoidModel.ArmPose swingingPose = swingingRight ? state.rightArmPose : state.leftArmPose;
            if (shouldOverrideArm(swingingPose)) {
                applySwing(model, rightArm, leftArm, rightLeg, leftLeg, body, head, swing, swingingRight, style, comboIndex, firstPerson);
            }
        }

        boolean drawingBow = state.rightArmPose == HumanoidModel.ArmPose.BOW_AND_ARROW
                || state.leftArmPose == HumanoidModel.ArmPose.BOW_AND_ARROW;
        if (drawingBow) {
            applyBowArm(rightArm, true);
            applyBowArm(leftArm, false);
        }

    }

    private static boolean shouldOverrideArm(HumanoidModel.ArmPose pose) {
        return pose == HumanoidModel.ArmPose.EMPTY || pose == HumanoidModel.ArmPose.ITEM;
    }

    private static float swingDurationTicks(SwingStyle style, int comboIndex) {
        return switch (style) {
            case SWORD -> (comboIndex == 0 ? PlayerAnimations.SWORD_ATTACK : PlayerAnimations.SWORD_ATTACK_2).lengthInSeconds() * 20.0F;
            case GREATSWORD -> PlayerAnimations.GREATSWORD_ATTACK.lengthInSeconds() * 20.0F;
            case AXE -> PlayerAnimations.AXE_ATTACK.lengthInSeconds() * 20.0F;
            default -> AnimMath.swingVisualDuration(style);
        };
    }

    private static void applySwing(
            Model model,
            ModelPart rightArm, ModelPart leftArm,
            ModelPart rightLeg, ModelPart leftLeg,
            ModelPart body, ModelPart head,
            float t, boolean rightSide, SwingStyle style, int comboIndex,
            boolean firstPerson) {

        if (style == SwingStyle.SWORD || style == SwingStyle.GREATSWORD || style == SwingStyle.AXE) {
            applyKeyframeSwing(model, body, head, rightArm, leftArm, rightLeg, leftLeg, t, rightSide, style, comboIndex, firstPerson);
            return;
        }

        ModelPart swingArm = rightSide ? rightArm : leftArm;
        ModelPart offArm = rightSide ? leftArm : rightArm;
        ModelPart swingLeg = rightSide ? rightLeg : leftLeg;
        ModelPart offLeg = rightSide ? leftLeg : rightLeg;

        float pitch;
        float yaw = 0.0F;
        switch (style) {
            case TRIDENT -> pitch = AnimMath.tridentThrustPitch(t);
            case TOOL -> pitch = AnimMath.toolStrikePitch(t);
            case PUNCH -> {
                pitch = AnimMath.punchPitch(t);
                yaw = AnimMath.punchYaw(t, rightSide);
            }

            default -> pitch = AnimMath.genericSwingPitch(t);
        }
        swingArm.xRot += pitch;
        swingArm.yRot += yaw;

        offArm.xRot -= AnimMath.offArmCounterPitch(t);
        offArm.zRot += AnimMath.offArmCounterRoll(t, !rightSide);

        float torsoScale = firstPerson ? FIRST_PERSON_BODY_SWAY_DAMPEN : 1.0F;
        body.yRot += AnimMath.swingBodyFollow(t, rightSide) * torsoScale;
        body.xRot += AnimMath.swingBodyPitchSnap(t) * torsoScale;

        swingLeg.xRot += AnimMath.swingLegWeightShift(t, rightSide);
        offLeg.xRot -= AnimMath.swingLegWeightShift(t, rightSide);
    }

    private static void applyKeyframeSwing(
            Model model,
            ModelPart body, ModelPart head,
            ModelPart rightArm, ModelPart leftArm,
            ModelPart rightLeg, ModelPart leftLeg,
            float t, boolean rightSide, SwingStyle style, int comboIndex,
            boolean firstPerson) {

        AnimationDefinition clip = switch (style) {
            case GREATSWORD -> PlayerAnimations.GREATSWORD_ATTACK;
            case AXE -> PlayerAnimations.AXE_ATTACK;
            default -> comboIndex == 0 ? PlayerAnimations.SWORD_ATTACK : PlayerAnimations.SWORD_ATTACK_2;
        };

        body.resetPose();
        head.resetPose();
        rightArm.resetPose();
        leftArm.resetPose();
        rightLeg.resetPose();
        leftLeg.resetPose();

        long ms = (long) (t * clip.lengthInSeconds() * 1000.0F);
        baked(clip, model.root()).apply(ms, 1.0F);

        if (firstPerson) {
            body.xRot *= FIRST_PERSON_BODY_SWAY_DAMPEN;
            body.yRot *= FIRST_PERSON_BODY_SWAY_DAMPEN;
            body.zRot *= FIRST_PERSON_BODY_SWAY_DAMPEN;
            body.x *= FIRST_PERSON_BODY_SWAY_DAMPEN;
            body.y *= FIRST_PERSON_BODY_SWAY_DAMPEN;
            body.z *= FIRST_PERSON_BODY_SWAY_DAMPEN;
        }

        attachToBody(rightArm, body);
        attachToBody(leftArm, body);
        attachToBody(head, body);

        if (!rightSide) {
            swapMirrored(rightArm, leftArm);
            swapMirrored(rightLeg, leftLeg);
            mirrorInPlace(body);
            mirrorInPlace(head);
        }
    }

    private static void attachRotationToBody(ModelPart limb, ModelPart body) {
        Quaternionf bodyRot = new Quaternionf().rotationZYX(body.zRot, body.yRot, body.xRot);
        Quaternionf localRot = new Quaternionf().rotationZYX(limb.zRot, limb.yRot, limb.xRot);
        Quaternionf combined = new Quaternionf();
        bodyRot.mul(localRot, combined);

        Vector3f euler = new Vector3f();
        combined.getEulerAnglesZYX(euler);
        limb.xRot = euler.x;
        limb.yRot = euler.y;
        limb.zRot = euler.z;
    }

    private static void attachToBody(ModelPart limb, ModelPart body) {
        Quaternionf bodyRot = new Quaternionf().rotationZYX(body.zRot, body.yRot, body.xRot);
        Quaternionf localRot = new Quaternionf().rotationZYX(limb.zRot, limb.yRot, limb.xRot);
        Quaternionf combined = new Quaternionf();
        bodyRot.mul(localRot, combined);

        Vector3f rotatedPos = new Vector3f();
        bodyRot.transform(new Vector3f(limb.x, limb.y, limb.z), rotatedPos);
        limb.x = body.x + rotatedPos.x;
        limb.y = body.y + rotatedPos.y;
        limb.z = body.z + rotatedPos.z;

        Vector3f euler = new Vector3f();
        combined.getEulerAnglesZYX(euler);
        limb.xRot = euler.x;
        limb.yRot = euler.y;
        limb.zRot = euler.z;
    }

    private static void mirrorInPlace(ModelPart part) {
        part.yRot = -part.yRot;
        part.zRot = -part.zRot;
        part.x = -part.x;
    }

    private static void swapMirrored(ModelPart a, ModelPart b) {
        float axr = a.xRot, ayr = a.yRot, azr = a.zRot, ax = a.x, ay = a.y, az = a.z;
        float bxr = b.xRot, byr = b.yRot, bzr = b.zRot, bx = b.x, by = b.y, bz = b.z;

        a.xRot = bxr; a.yRot = -byr; a.zRot = -bzr; a.x = -bx; a.y = by; a.z = bz;
        b.xRot = axr; b.yRot = -ayr; b.zRot = -azr; b.x = -ax; b.y = ay; b.z = az;
    }

    private static void applyBowArm(ModelPart arm, boolean rightSide) {
        arm.xRot += AnimMath.bowArmPitch();
        arm.yRot += AnimMath.bowArmYaw(rightSide);
        arm.zRot += AnimMath.bowArmSpread(rightSide);
    }
}