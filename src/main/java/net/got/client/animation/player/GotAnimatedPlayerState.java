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
}
