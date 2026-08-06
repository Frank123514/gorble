package net.got.client.animation.player;

/**
 * Extra per-player animation data that vanilla's {@code PlayerRenderState}
 * doesn't carry, mixed onto it by {@code PlayerRenderStateMixin} and
 * populated each frame by {@code PlayerRendererMixin}.
 *
 * <p>Vanilla already tracks everything we need for walk/run/sneak/attack
 * (walkAnimationPos/Speed, isCrouching, attackTime, arm poses) so those
 * aren't duplicated here — this only covers the handful of things vanilla
 * genuinely has no field for: whether the player is on a climbable, whether
 * they're airborne, and which swing style the held item maps to.
 *
 * <p>Cast the render state to this interface to read/write it, e.g.
 * {@code ((GotAnimatedPlayerState) state).got$getClimbProgress()}.
 */
public interface GotAnimatedPlayerState {

    float got$getClimbProgress();

    void got$setClimbProgress(float value);

    float got$getAirborneProgress();

    void got$setAirborneProgress(float value);

    /**
     * Smoothed 0..1 toward {@code player.isSprinting()}, captured directly
     * off the live entity by {@code PlayerRendererMixin} the same way
     * {@code climbProgress}/{@code airborneProgress} are. {@code
     * GotPlayerAnimator} uses this as the walk/run crossfade weight instead
     * of deriving it from {@code walkAnimationSpeed} — that continuous
     * limb-swing value doesn't reliably separate "walking" from "sprinting"
     * (it's driven by how far the legs actually swung last tick, not by
     * whether the player is sprinting), which is why WALKING and RUNNING
     * kept reading as visually identical no matter how different their
     * keyframe data was: the blend weight just never moved cleanly between
     * the two. The actual sprint flag has no such ambiguity.
     */
    float got$getSprintProgress();

    void got$setSprintProgress(float value);

    GotSwingStyle got$getSwingStyle();

    void got$setSwingStyle(GotSwingStyle style);

    /**
     * Which sword attack of the 2-swing combo (0 or 1) should play next —
     * toggled by {@code PlayerRendererMixin} each time a fresh swing starts,
     * so consecutive sword hits alternate {@code sword_attack} /
     * {@code sword_attack_2} instead of replaying the same one every time.
     */
    int got$getComboIndex();

    void got$setComboIndex(int value);

    /** Last frame's clamped swing progress (0..1), kept only to detect the rising edge that marks a new swing starting. */
    float got$getPrevSwing();

    void got$setPrevSwing(float value);

    /**
     * {@code state.ageInTicks} captured at the rising edge of the current
     * swing (see {@code got$getPrevSwing}), used by {@code GotPlayerAnimator}
     * to drive the swing pose on its own fixed, actually-visible duration
     * ({@code GotAnimMath.SWING_VISUAL_DURATION_TICKS}) instead of vanilla's
     * raw {@code attackTime} — which, especially on a fast weapon, can cover
     * the whole swing in as little as 5-6 ticks, too short to read as
     * anything but a blur no matter how the pose curve is shaped. Defaults
     * far in the past so no swing plays before the first real one.
     */
    float got$getSwingStartAge();

    void got$setSwingStartAge(float value);

    /**
     * Whether this player is (as far as the local client can tell)
     * currently mid-swing-mining a block, i.e. holding down attack against
     * a block rather than swinging at nothing/an entity. Only ever true
     * for {@code Minecraft.getInstance().player} — {@code PlayerRendererMixin}
     * has no way to know this for remote players, so it always reports
     * {@code false} for them and those players fall back to the normal
     * single-swing-per-attackTime-cycle behavior.
     *
     * <p>{@code GotPlayerAnimator} uses this to decide whether an AXE-style
     * swing should loop continuously off {@code ageInTicks} (breaking
     * blocks) instead of playing once per rising edge of {@code attackTime}
     * (actually attacking something) — see the AXE branch in
     * {@code GotPlayerAnimator#apply}.
     */
    boolean got$isMiningWithAxe();

    void got$setMiningWithAxe(boolean value);

    /**
     * Whether the player is currently seated on an {@code AbstractHorse}
     * (horse/donkey/mule/skeleton horse/zombie horse — the whole family
     * shares vanilla's horse riding pose), captured directly off {@code
     * player.getVehicle()} by {@code PlayerRendererMixin}. {@code
     * GotPlayerAnimator} uses this to play {@code PlayerAnimations.HORSE_IDLE}/
     * {@code HORSE_RUNNING} instead of the usual skip-if-passenger
     * behavior — other vehicles (boats, minecarts, pigs, etc.) still fall
     * through to that unchanged, since there's no authored riding clip for
     * them.
     */
    boolean got$isRidingHorse();

    void got$setRidingHorse(boolean value);

    /**
     * Smoothed 0..1 crossfade weight between {@code HORSE_IDLE} (0) and
     * {@code HORSE_RUNNING} (1), the same {@code GotAnimMath.approach}
     * easing pattern as {@code climbProgress}/{@code sprintProgress},
     * driven off the ridden horse's own ground speed rather than the
     * player's (the player has no walk speed of their own while seated).
     */
    float got$getHorseRunBlend();

    void got$setHorseRunBlend(float value);
}