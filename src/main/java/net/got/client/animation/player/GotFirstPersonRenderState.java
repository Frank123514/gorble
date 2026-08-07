package net.got.client.animation.player;

/**
 * A single boolean flag, true only for the duration of {@code
 * LevelRendererMixin}'s forced local-player world render (the one
 * {@code renderEntity} call per frame that {@code LevelRendererMixin}'s
 * {@code got_forceRenderLocalPlayerBody} redirect makes happen at all).
 *
 * <p><b>Why this exists:</b> {@code PlayerRenderer.extractRenderState} runs
 * for {@code mc.player} in more places than just that one world render —
 * the inventory screen's player preview, the character/skin screen, and
 * anywhere else vanilla draws a live preview of the local player all call
 * it too, and all of them do so while {@code mc.options.getCameraType()}
 * still reports {@code FIRST_PERSON} (opening a menu doesn't change the
 * camera option). {@code PlayerRendererMixin} used to gate {@code
 * got$setLocalFirstPerson} on camera type alone, which meant the inventory
 * preview also got its head/hat hidden by {@code PlayerModelMixin} —
 * headless armor floating in the character screen. Gating on this flag
 * instead (set/cleared right around the one real world-render call in
 * {@code LevelRendererMixin}) scopes the head-hiding to exactly the render
 * it was meant for.
 *
 * <p>Deliberately a plain static holder, not a field on the mixin class
 * itself: {@code LevelRendererMixin}'s own fields get merged onto the real
 * {@code LevelRenderer} class at runtime, which makes cross-mixin-class
 * static field access needlessly fragile. A small standalone utility class
 * has no such ambiguity.
 */
public final class GotFirstPersonRenderState {

    private GotFirstPersonRenderState() {}

    private static boolean renderingLocalBody = false;

    public static boolean isRenderingLocalBody() {
        return renderingLocalBody;
    }

    public static void setRenderingLocalBody(boolean value) {
        renderingLocalBody = value;
    }
}
