package net.got.client.animation.player;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Writes {@link PlayerAnimations} keyframe clips (walk/run/jump/sword/
 * sword2/greatsword/axe/block/horse-idle/horse-running) and
 * {@link GotAnimMath}'s remaining procedural curves (idle sway, climb,
 * punch/trident/tool/generic swings) onto the real player
 * {@link ModelPart}s, fully replacing vanilla's rotations for the poses
 * Got owns.
 *
 * <p><b>Sneaking is explicitly NOT one of the poses Got owns:</b> the base
 * locomotion block in {@link #apply} is skipped entirely whenever the
 * player is crouching (and not climbing), so vanilla's own default sneak
 * pose from {@code HumanoidModel.setupAnim} — already computed before this
 * class runs, see {@code PlayerModelMixin} — is left completely untouched.
 * Attack swings, blocking, and bow-draw still layer on top of that vanilla
 * pose exactly the way they would on top of a Got walk/run/climb pose.
 *
 * <p><b>Eighth pass — locomotion and sword/greatsword/axe go from math back
 * to literal keyframe playback:</b> this class used to compute every pose
 * as plain {@code float}s from {@link GotAnimMath} and assign them onto
 * the {@code ModelPart}s in one block at the end. Walking, running,
 * jumping, and the sword/sword2/greatsword/axe attack swings don't work
 * that way any more — they're played directly off {@link PlayerAnimations}
 * via {@code KeyframeAnimations.animate}, the same vanilla playback path
 * {@code GotStagModel}/{@code GotBrownBearModel}/{@code BellowsModel}
 * already use for their own clips, rather than being resampled through a
 * hand-tuned pendulum/spring model. Idle sway, climbing, and the
 * punch/trident/tool/generic swing styles have no source clip to play
 * back, so those stay procedural exactly as before.
 *
 * <p><b>How the two systems combine:</b> {@code KeyframeAnimations.animate}
 * <i>adds</i> a clip's sampled rotation/position onto whatever's already on
 * a {@code ModelPart} (scaled by its {@code weight} argument), the same way
 * the old code's {@code +=} assignments worked. So each frame starts by
 * resetting the six owned parts to their bind pose (mirroring how
 * {@code GotStagModel#applyAnimation} resets before playing its own clips),
 * then layers WALKING/RUNNING (crossfaded by {@code runBlend}, and by
 * movement intensity so the clip's contribution fades to zero at a
 * standstill rather than freezing mid-stride) or the climb procedural
 * pose, then layers JUMP on top at {@code weight = airborneProgress}. A
 * sword/greatsword/axe swing is different: those clips carry a full-body
 * performance (their own body/head/leg channels, not just an arm), so an
 * active swing in one of those styles resets and re-plays the clip on all
 * six parts, replacing this frame's locomotion pose outright rather than
 * adding to it — see {@link #applyKeyframeSwing}.
 *
 * <p><b>What this intentionally leaves alone:</b> sneaking (see above),
 * crossbow draw, eating
 * &amp; drinking, spyglass, brush, horn, spear throw, elytra flight, sleeping,
 * and non-horse riding poses (boats, minecarts, pigs, etc. — vanilla
 * already has bespoke poses for those and there's no authored Got clip for
 * them). Bow draw (vanilla bow and our own longbow, which both report
 * {@code ArmPose.BOW_AND_ARROW} since they share {@code BowItem}'s use
 * animation) is the one exception with a Got-owned pose — see
 * {@code applyBowArmIfNeeded} near the bottom of {@link #apply}. This class
 * skips overriding an arm whenever its {@link HumanoidModel.ArmPose} is one
 * of the remaining special poses (checked via {@code shouldOverrideArm}),
 * and skips the whole player when
 * airborne-with-elytra or seated on a non-horse vehicle. Horse riding
 * (including donkeys/mules/skeleton/zombie horses — the whole
 * {@code AbstractHorse} family) IS in scope: see the {@code isPassenger}
 * branch at the top of {@link #apply}, which plays {@link
 * PlayerAnimations#HORSE_IDLE}/{@link PlayerAnimations#HORSE_RUNNING}
 * instead of skipping. That's a deliberate scope decision, not an
 * oversight — extend the {@code shouldOverrideArm} switch or the
 * {@code isPassenger} branch if more vanilla poses should be replaced
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
 * {@code isFallFlying}, {@code isPassenger}. {@code KeyframeAnimations.animate}'s
 * signature/behavior (a {@code Model}, a millisecond time computed as
 * {@code (long) (ageInTicks * 50F)}, a blend weight, and a reusable scratch
 * {@code Vector3f}) is taken from this project's own already-working
 * {@code BellowsModel}/{@code GotStagModel} usages rather than re-derived.
 */
public final class GotPlayerAnimator {

    private GotPlayerAnimator() {}

    /** Reusable scratch vector for {@code KeyframeAnimation#apply} — see e.g. {@code BellowsModel}/{@code GotStagModel} for the same pattern. Never read, only written into by vanilla's sampler. */
    private static final Vector3f ANIM_VEC = new Vector3f();

    /**
     * 1.21.6 removed the old static {@code KeyframeAnimations.animate(Model,
     * AnimationDefinition, long, float, Vector3f)} helper entirely — an
     * {@link AnimationDefinition} must now be baked once against a specific
     * {@link ModelPart} root via {@link AnimationDefinition#bake}, producing
     * a reusable {@link KeyframeAnimation} whose {@code apply(long, float,
     * Vector3f)} does what the old static method used to do. Baking is not
     * free (it walks the whole part tree resolving names), so this caches
     * the baked result per (definition, root) pair — keyed weakly on the
     * root so it doesn't outlive the model it was baked against — instead
     * of re-baking every single frame for every player on screen.
     */
    private static final Map<AnimationDefinition, WeakHashMap<ModelPart, KeyframeAnimation>> BAKED_ANIMATIONS =
            new IdentityHashMap<>();

    private static KeyframeAnimation baked(AnimationDefinition definition, ModelPart root) {
        return BAKED_ANIMATIONS
                .computeIfAbsent(definition, d -> new WeakHashMap<>())
                .computeIfAbsent(root, definition::bake);
    }

    /**
     * How much faster the axe's mining loop plays than its real combat
     * swing duration — {@code 3.0F} means each mining chop takes
     * {@code AXE_ATTACK}'s length divided by 3, so the loop reads as a
     * quick repeated hack rather than the slower full-length combat swing
     * played back to back. Tune this directly to taste.
     */
    private static final float MINING_LOOP_SPEED = 3.0F;

    /**
     * How much of the walk/run torso sway (both the yaw-twist rotation and
     * the up/down/forward bob position, WALKING/RUNNING's "body" channels)
     * survives when this is the local player's own body in first person —
     * see {@link GotAnimatedPlayerState#got$isLocalFirstPerson()}. The full
     * authored amplitude (~11 degrees of twist, up to 2 units of bob) reads
     * fine on other players and in third person, but right up against the
     * camera it's a lot of visible torso motion, right in the area
     * (collar/shoulder) that was already poking into frame — dampening it
     * quiets that down without touching the arm swing itself, which stays
     * at full amplitude. 0.4 was picked by eye, not measured; raise it
     * toward 1.0 if the torso ends up reading as too stiff/lifeless in
     * first person, lower it if it's still too busy.
     */
    private static final float FIRST_PERSON_BODY_SWAY_DAMPEN = 0.4F;

    public static void apply(
            Model model,
            PlayerRenderState state,
            ModelPart body, ModelPart head,
            ModelPart rightArm, ModelPart leftArm,
            ModelPart rightLeg, ModelPart leftLeg) {

        // Don't fight vanilla's elytra-flight pose — not in scope, has its
        // own careful hand-tuned rig.
        if (state.isFallFlying) {
            return;
        }

        GotAnimatedPlayerState anim = (GotAnimatedPlayerState) state;

        if (state.isPassenger) {
            // Horses get their own authored riding pose, crossfaded by the
            // horse's own ground speed; every other vehicle (boats,
            // minecarts, pigs, etc.) has no clip for it and is left to
            // vanilla's own seated pose exactly as before.
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
        // Was derived from walkAnimationSpeed via a speed threshold, but
        // that value tracks how far the legs swung last tick, not whether
        // the player is sprinting — it didn't reliably separate the two
        // states, which is why WALKING and RUNNING kept looking identical
        // regardless of their (correctly different) keyframe data. Now
        // sourced straight from the player's actual isSprinting() flag
        // (captured by PlayerRendererMixin into got$sprintProgress, smoothed
        // the same way climb/airborne are so there's no pop at the toggle).
        float runBlend = anim.got$getSprintProgress();
        float moveIntensity = walkSpeed;

        // Sneaking is intentionally left alone: this whole locomotion+jump
        // block (including the resetPose() calls that would otherwise wipe
        // it) is skipped while crouching, so vanilla's own
        // HumanoidModel.setupAnim sneak pose (already computed before this
        // mixin runs — see PlayerModelMixin) survives untouched instead of
        // being replaced with a Got-authored crouch pose. Attack swings,
        // blocking, and bow-draw below still run and layer on top exactly
        // as before, whether the base pose underneath is vanilla's sneak or
        // Got's own walk/run/climb.
        boolean sneaking = climb <= 0.01F && state.isCrouching;

        if (!sneaking) {
            // Fresh baseline every frame: KeyframeAnimations.animate() ADDS
            // onto whatever's already on a part, so start from bind pose the
            // same way GotStagModel#applyAnimation etc. do, rather than
            // layering on top of vanilla's own HumanoidModel.setupAnim pose.
            body.resetPose();
            head.resetPose();
            rightArm.resetPose();
            leftArm.resetPose();
            rightLeg.resetPose();
            leftLeg.resetPose();

            // ── Base locomotion pose (climb / walk+run keyframe) ─────────────
            if (climb > 0.01F) {
                // Climbing replaces normal locomotion entirely while active, and
                // blends back out smoothly via climbProgress as the player lets go.
                // Blended from rest (0) rather than from the walk pose at
                // climb's low end, since there's no cheap way to read a scalar
                // "current leg angle" back out of a keyframe clip mid-blend —
                // climbProgress itself already ramps in smoothly (see
                // PlayerRendererMixin), so the transition still reads as smooth.
                float armR = Mth.lerp(climb, 0.0F, GotAnimMath.climbArmReach(age, true));
                float armL = Mth.lerp(climb, 0.0F, GotAnimMath.climbArmReach(age, false));
                float legR = Mth.lerp(climb, 0.0F, GotAnimMath.climbLegPush(age, true));
                float legL = Mth.lerp(climb, 0.0F, GotAnimMath.climbLegPush(age, false));
                body.xRot = GotAnimMath.climbBodyPitch() * climb;
                rightArm.xRot = armR;
                leftArm.xRot = armL;
                rightLeg.xRot = legR;
                leftLeg.xRot = legL;
            } else {
                // WALKING/RUNNING are crossfaded by runBlend and additionally
                // weighted by moveIntensity, so the clip's contribution fades
                // out smoothly to a standstill instead of freezing on whatever
                // frame walkAnimationPos happened to stop on.
                long walkMs = (long) (walkPos * 50F);
                baked(PlayerAnimations.WALKING, model.root()).apply(walkMs, (1.0F - runBlend) * moveIntensity);
                baked(PlayerAnimations.RUNNING, model.root()).apply(walkMs, runBlend * moveIntensity);

                // Idle breathing sway fades in as movement stops and fades back
                // out the moment the player starts walking or leaves the ground,
                // so there's no seam between "standing still" and "just started
                // walking".
                float idleBlend = (1.0F - moveIntensity) * (1.0F - airborne);
                rightArm.zRot += GotAnimMath.idleArmSway(age) * idleBlend;
                leftArm.zRot += -GotAnimMath.idleArmSway(age) * idleBlend;
                rightLeg.zRot += GotAnimMath.idleLegSplay(true) * idleBlend;
                leftLeg.zRot += GotAnimMath.idleLegSplay(false) * idleBlend;
                body.yRot += GotAnimMath.idleBodySway(age) * idleBlend;
            }

            // Only the torso's own walk/run sway gets quieted down here —
            // rightArm/leftArm/rightLeg/leftLeg are untouched, so the arm
            // swing you actually watch your hands/weapon do every frame in
            // first person keeps its full, correctly-timed amplitude. See
            // FIRST_PERSON_BODY_SWAY_DAMPEN's doc for why body specifically.
            if (anim.got$isLocalFirstPerson()) {
                body.xRot *= FIRST_PERSON_BODY_SWAY_DAMPEN;
                body.yRot *= FIRST_PERSON_BODY_SWAY_DAMPEN;
                body.zRot *= FIRST_PERSON_BODY_SWAY_DAMPEN;
                body.x *= FIRST_PERSON_BODY_SWAY_DAMPEN;
                body.y *= FIRST_PERSON_BODY_SWAY_DAMPEN;
                body.z *= FIRST_PERSON_BODY_SWAY_DAMPEN;
            }

            // ── Jump / airborne ──────────────────────────────────────────────
            // Layered on top of whichever base pose above via
            // KeyframeAnimations' additive weight semantics.
            if (airborne > 0.01F) {
                baked(PlayerAnimations.JUMP, model.root()).apply((long) (age * 50F), airborne);

                // Rotation-only torso attach (unlike the swing path's full
                // attachToBody): composing body's POSITION here as well
                // dragged the arms down by body's small anticipation-dip
                // translation and knocked the head sideways off-center,
                // since JUMP's arm/head channels are already authored at
                // their normal resting height/offset and don't expect to
                // also be carried by the body's own squash/dip. Only the
                // torso's tilt should be inherited here, not its translation.
                attachRotationToBody(rightArm, body);
                attachRotationToBody(leftArm, body);
                attachRotationToBody(head, body);
            }
        }

        // ── Attack swings (punch / weapon), applied per-arm ─────────────────
        // Timed off our own fixed visual window (see swingDurationTicks),
        // not vanilla's raw attackTime — see
        // GotAnimatedPlayerState#got$getSwingStartAge for why: a fast
        // weapon's real swing is often too short to actually see play out.
        // SWORD/GREATSWORD/AXE size that window off their real
        // PlayerAnimations clip length; the remaining styles use
        // GotAnimMath's hand-tuned constants. swingElapsed goes negative-
        // infinity-ish before the first real swing (got$swingStartAge
        // defaults far in the past), so swing clamps to 1 and swingActive
        // is correctly false until then.
        GotSwingStyle style = anim.got$getSwingStyle();
        int comboIndex = anim.got$getComboIndex();
        float swingDuration = swingDurationTicks(style, comboIndex);
        float swing;
        boolean swingActive;
        if (style == GotSwingStyle.AXE && anim.got$isMiningWithAxe()) {
            // Breaking a block with an axe: loop the chop clip back-to-back
            // off ageInTicks directly instead of gating it on attackTime's
            // rising edge, so it keeps repeating for as long as the block
            // is being mined rather than playing once and holding/stopping.
            // Actual attacking (mining flag false) falls through to the
            // unchanged single-swing-per-rising-edge path below.
            //
            // Sped up relative to the real combat swing (MINING_LOOP_SPEED
            // > 1) — chopping wants a quick, repeated hack, not the full
            // slower combat flourish stretched out over and over.
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
                applySwing(model, rightArm, leftArm, rightLeg, leftLeg, body, head, swing, swingingRight, style, comboIndex);
            }
        }

        // ── Blocking (shield raised) ─────────────────────────────────────────
        applyBlockIfNeeded(model, state.rightArmPose, body, head, rightArm, leftArm, rightLeg, leftLeg, true);
        applyBlockIfNeeded(model, state.leftArmPose, body, head, rightArm, leftArm, rightLeg, leftLeg, false);

        // ── Bow draw (vanilla bow & our longbow) ─────────────────────────────
        // Unlike block, this doesn't reset/replace the whole body — legs and
        // torso keep walking/idling normally while aiming (matching vanilla,
        // where you can still walk while drawing a bow), only the arms get
        // pulled up.
        //
        // NOT checked per-arm off ArmPose — in practice only the arm
        // actually holding/using the bow ever reports BOW_AND_ARROW; the
        // other arm's pose stays EMPTY/ITEM the whole time, so gating both
        // arms on their own individual pose (the first version of this)
        // only ever raised the one arm and left the other hanging exactly
        // like before. Instead: if EITHER arm reports BOW_AND_ARROW, raise
        // BOTH arms unconditionally — that's what actually produces the
        // "other arm comes up to help hold it" look.
        boolean drawingBow = state.rightArmPose == HumanoidModel.ArmPose.BOW_AND_ARROW
                || state.leftArmPose == HumanoidModel.ArmPose.BOW_AND_ARROW;
        if (drawingBow) {
            applyBowArm(rightArm, true);
            applyBowArm(leftArm, false);
        }

        // Vanilla-untouched arm poses (crossbow, spyglass, brush, horn,
        // spear throw) are left exactly as HumanoidModel.setupAnim already
        // set them, since shouldOverrideArm()/applySwing() skip those cases.
        //
        // hat/jacket/leftSleeve/rightSleeve/leftPants/rightPants are NOT
        // synced here on purpose: they're children of head/body/arms/legs
        // in the model's part hierarchy, so they already inherit whatever
        // rotation we just set on their parent automatically at render
        // time. Manually copying the parent's rotation onto them here
        // would double-apply it — that's what was causing the overlay
        // layer to visibly swing away from the body instead of tracking it
        // in an earlier version of this method.
    }

    /** Only take over an arm's rotation when it's in a "plain" pose — leave vanilla's specialized poses (bow, spyglass, etc.) untouched. */
    private static boolean shouldOverrideArm(HumanoidModel.ArmPose pose) {
        return pose == HumanoidModel.ArmPose.EMPTY || pose == HumanoidModel.ArmPose.ITEM;
    }

    /**
     * How long (in ticks) {@code style}'s swing pose gets to play, given the
     * current combo index for SWORD (sword_attack vs. sword_attack_2 have
     * different authored lengths). SWORD/GREATSWORD/AXE read this straight
     * off their real {@link PlayerAnimations} clip length rather than a
     * hand-tuned constant, since those styles now play the literal clip
     * rather than an eased approximation of one.
     */
    private static float swingDurationTicks(GotSwingStyle style, int comboIndex) {
        return switch (style) {
            case SWORD -> (comboIndex == 0 ? PlayerAnimations.SWORD_ATTACK : PlayerAnimations.SWORD_ATTACK_2).lengthInSeconds() * 20.0F;
            case GREATSWORD -> PlayerAnimations.GREATSWORD_ATTACK.lengthInSeconds() * 20.0F;
            case AXE -> PlayerAnimations.AXE_ATTACK.lengthInSeconds() * 20.0F;
            default -> GotAnimMath.swingVisualDuration(style);
        };
    }

    /**
     * Applies the full swing pose. SWORD/GREATSWORD/AXE hand off to
     * {@link #applyKeyframeSwing} entirely (see that method's doc); the
     * remaining styles keep the pitch/yaw/roll-on-the-striking-arm plus
     * counter-balance/torso-twist/leg-weight-shift approach from before.
     */
    private static void applySwing(
            Model model,
            ModelPart rightArm, ModelPart leftArm,
            ModelPart rightLeg, ModelPart leftLeg,
            ModelPart body, ModelPart head,
            float t, boolean rightSide, GotSwingStyle style, int comboIndex) {

        if (style == GotSwingStyle.SWORD || style == GotSwingStyle.GREATSWORD || style == GotSwingStyle.AXE) {
            applyKeyframeSwing(model, body, head, rightArm, leftArm, rightLeg, leftLeg, t, rightSide, style, comboIndex);
            return;
        }

        ModelPart swingArm = rightSide ? rightArm : leftArm;
        ModelPart offArm = rightSide ? leftArm : rightArm;
        ModelPart swingLeg = rightSide ? rightLeg : leftLeg;
        ModelPart offLeg = rightSide ? leftLeg : rightLeg;

        float pitch;
        float yaw = 0.0F;
        switch (style) {
            case TRIDENT -> pitch = GotAnimMath.tridentThrustPitch(t);
            case TOOL -> pitch = GotAnimMath.toolStrikePitch(t);
            case PUNCH -> {
                pitch = GotAnimMath.punchPitch(t);
                yaw = GotAnimMath.punchYaw(t, rightSide);
            }
            // SWORD/GREATSWORD/AXE never reach here (handled above).
            default -> pitch = GotAnimMath.genericSwingPitch(t);
        }
        swingArm.xRot += pitch;
        swingArm.yRot += yaw;

        offArm.xRot -= GotAnimMath.offArmCounterPitch(t);
        offArm.zRot += GotAnimMath.offArmCounterRoll(t, !rightSide);

        body.yRot += GotAnimMath.swingBodyFollow(t, rightSide);
        body.xRot += GotAnimMath.swingBodyPitchSnap(t);

        swingLeg.xRot += GotAnimMath.swingLegWeightShift(t, rightSide);
        offLeg.xRot -= GotAnimMath.swingLegWeightShift(t, rightSide);
    }

    /**
     * SWORD/GREATSWORD/AXE swing path: plays the literal
     * {@link PlayerAnimations#SWORD_ATTACK}/{@code SWORD_ATTACK_2}/
     * {@code GREATSWORD_ATTACK}/{@code AXE_ATTACK} clip via
     * {@code KeyframeAnimations.animate}, exactly the way
     * {@code GotBrownBearModel} plays its own {@code ATTACK} clip. Unlike
     * the procedural styles in {@link #applySwing}, these source clips
     * carry a full-body performance — their own body/head/rightLeg/leftLeg
     * channels, not just an arm — so this resets and re-plays the whole
     * six-part pose for the duration of the swing, replacing this frame's
     * walk/run/jump pose outright rather than adding a delta on top of it,
     * the same way the source clips were authored to be played back whole.
     *
     * <p>The source clips are authored for a right-handed swing (rightArm =
     * weapon arm, body/head twisting to match). When the player is actually
     * swinging with their left arm, the played-back pose is mirrored in
     * place afterward: right/left arm poses are swapped (each becoming the
     * left-right mirror of the other), same for right/left leg, and body/
     * head are mirrored in place, rather than re-authoring the clip itself.
     * Pitch (xRot) and vertical/depth position (y/z) are symmetric across
     * the body's left-right axis and carry over unchanged; yaw/roll
     * (yRot/zRot) and sideways position (x) flip sign — see
     * {@link #mirrorInPlace}/{@link #swapMirrored}.
     */
    private static void applyKeyframeSwing(
            Model model,
            ModelPart body, ModelPart head,
            ModelPart rightArm, ModelPart leftArm,
            ModelPart rightLeg, ModelPart leftLeg,
            float t, boolean rightSide, GotSwingStyle style, int comboIndex) {

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

        // t is 0..1 across swingDurationTicks, which for these three styles
        // *is* the clip's own real length in ticks (see
        // swingDurationTicks) — so t * clip.lengthInSeconds() lands on the clip's
        // own timeline exactly, rather than stretching/compressing it into
        // an artificial window.
        long ms = (long) (t * clip.lengthInSeconds() * 1000.0F);
        baked(clip, model.root()).apply(ms, 1.0F);

        // The source rig authors right_arm/left_arm/head as children of
        // the torso, so their keyframes are local to the body's own
        // swing — but vanilla's PlayerModel has no such parenting (all
        // six of these parts are flat siblings under root), so without
        // this each part plays its correct local motion but never
        // inherits the torso's lean/twist. For the arms that shows up as
        // the shoulder visibly separating from the body; for the head —
        // which has no local rotation keyframes of its own in any of
        // these clips, only a small position bob — the symptom is worse:
        // with zero local rotation to compose, the head doesn't turn at
        // all, appearing completely un-animated during every sword-style
        // swing. Composing it against body's rotation here makes it
        // rigidly track the torso lean, which is the correct behavior
        // for a head with no independent look-animation of its own.
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

    /**
     * Rotation-only version of {@link #attachToBody}: composes body's
     * rotation into {@code limb}, but leaves {@code limb.x/y/z} exactly
     * as sampled, instead of also translating it by body's position. Use
     * this when the limb's own authored position already represents its
     * normal resting offset and shouldn't additionally be carried by
     * body's own (typically small, stylized) translation — e.g. JUMP's
     * arm/head channels, where composing full position dragged them down
     * by body's anticipation dip. Prefer {@link #attachToBody} instead
     * when body's translation is itself large enough that the limb
     * genuinely needs to travel with it (e.g. the sword/greatsword/axe
     * swings, where body physically steps/lunges).
     */
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

    /**
     * Re-parents {@code limb}'s just-sampled LOCAL (torso-relative) clip
     * pose onto {@code body}'s just-sampled pose, simulating the
     * torso-&gt;arm hierarchy the source rig was actually authored with,
     * since vanilla's flat {@code PlayerModel} has no such parenting. Must
     * run after both parts have been posed by the same
     * {@code KeyframeAnimations.animate} call this frame (see call site).
     *
     * <p>{@code body}'s rest pivot sits at the model origin (vanilla
     * {@code HumanoidModel.createMesh}: {@code body} is offset {@code
     * (0,0,0)}), so composing world position collapses to a clean
     * {@code body.position + rotate(body.rotation, limb.position)} — no
     * separate rest-offset bookkeeping needed, since {@code limb.x/y/z}
     * already carries its own rest pivot (e.g. right_arm's {@code
     * (-5,2,0)}) plus this frame's local clip delta, courtesy of
     * {@code resetPose()} + the additive {@code animate} call above.
     * Rotation composes the same way any parent-child rotation does:
     * {@code combined = bodyRotation * limbLocalRotation}.
     */
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

    /** Left-right mirror of a single part's already-sampled pose, in place: pitch (xRot) and vertical/depth position (y/z) are symmetric and carry over unchanged; yaw/roll (yRot/zRot) and sideways position (x) flip sign. */
    private static void mirrorInPlace(ModelPart part) {
        part.yRot = -part.yRot;
        part.zRot = -part.zRot;
        part.x = -part.x;
    }

    /** Swaps two same-body-plane parts' poses (e.g. rightArm/leftArm), each becoming the left-right mirror of what the other had — used to retarget a right-handed source clip onto a left-handed swing. */
    private static void swapMirrored(ModelPart a, ModelPart b) {
        float axr = a.xRot, ayr = a.yRot, azr = a.zRot, ax = a.x, ay = a.y, az = a.z;
        float bxr = b.xRot, byr = b.yRot, bzr = b.zRot, bx = b.x, by = b.y, bz = b.z;

        a.xRot = bxr; a.yRot = -byr; a.zRot = -bzr; a.x = -bx; a.y = by; a.z = bz;
        b.xRot = axr; b.yRot = -ayr; b.zRot = -azr; b.x = -ax; b.y = ay; b.z = az;
    }

    /**
     * BLOCK pose (shield raised) plays the literal {@link PlayerAnimations#SWORD_BLOCK}
     * clip the same way {@link #applyKeyframeSwing} plays SWORD/GREATSWORD/AXE —
     * full reset + replay on all six parts, not blended with the swing/walk
     * pose above, since raising a shield should look the same whether the
     * player is standing still or mid-stride. Replaces the older procedural
     * {@code GotAnimMath.blockArmPitch}/{@code blockArmYawAcrossChest}/
     * {@code blockBodyLean} approximation now that a real authored clip
     * exists for it.
     *
     * <p>The source clip was authored for a shield held in the <b>left</b>
     * hand (the default off-hand slot when {@code mainArm} is right) — its
     * {@code left_arm} channel carries the big raised-across-chest rotation
     * while {@code right_arm} stays low. So unlike
     * {@link #applyKeyframeSwing} (authored right-handed, mirrored for
     * left), this one is mirrored the other way: played as-is when it's the
     * left arm blocking, mirrored when it's the right arm (i.e. {@code
     * mainArm} is left and the shield ends up in the off-hand right slot).
     */
    private static void applyBlockIfNeeded(
            Model model, HumanoidModel.ArmPose pose,
            ModelPart body, ModelPart head,
            ModelPart rightArm, ModelPart leftArm,
            ModelPart rightLeg, ModelPart leftLeg,
            boolean rightSide) {
        if (pose != HumanoidModel.ArmPose.BLOCK) {
            return;
        }

        body.resetPose();
        head.resetPose();
        rightArm.resetPose();
        leftArm.resetPose();
        rightLeg.resetPose();
        leftLeg.resetPose();

        // Single-keyframe clip (all channels hold just t=0), so any ms
        // value samples the same static held pose — still routed through
        // the real clip length rather than hardcoding 0 in case a future
        // re-export adds motion to it.
        long ms = (long) (PlayerAnimations.SWORD_BLOCK.lengthInSeconds() * 1000.0F);
        baked(PlayerAnimations.SWORD_BLOCK, model.root()).apply(ms, 1.0F);

        attachToBody(rightArm, body);
        attachToBody(leftArm, body);
        attachToBody(head, body);

        if (rightSide) {
            swapMirrored(rightArm, leftArm);
            swapMirrored(rightLeg, leftLeg);
            mirrorInPlace(body);
            mirrorInPlace(head);
        }
    }

    /**
     * Raises one arm into the bow-draw pose. Called for both arms
     * unconditionally whenever either one is actually drawing (see the
     * call site) rather than gated on that specific arm's own {@code
     * ArmPose} — only the drawing arm ever reports {@code BOW_AND_ARROW}
     * in practice, so checking each arm individually left the off-hand
     * exactly where locomotion put it. Applied additively ({@code +=}) on
     * top of whatever locomotion already wrote onto the arm this frame
     * (idle sway / walk / run), the same layering {@link #applySwing}
     * uses, so the raised pose still reads correctly whether the player is
     * standing still or walking while aiming.
     */
    private static void applyBowArm(ModelPart arm, boolean rightSide) {
        arm.xRot += GotAnimMath.bowArmPitch();
        arm.yRot += GotAnimMath.bowArmYaw(rightSide);
        arm.zRot += GotAnimMath.bowArmSpread(rightSide);
    }
}