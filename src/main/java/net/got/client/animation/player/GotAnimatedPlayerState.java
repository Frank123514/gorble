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
}
