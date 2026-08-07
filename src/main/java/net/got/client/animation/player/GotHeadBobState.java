package net.got.client.animation.player;

/**
 * Holds the local player's own {@code head} {@code ModelPart}'s
 * just-computed POSITION offset (model-local units — 1/16 of a block,
 * vanilla's usual cube-coordinate scale, NOT blocks) from the most recent
 * frame it was rendered in first person, so {@code CameraMixin} can nudge
 * the actual first-person camera to bob along with it.
 *
 * <p><b>Why a static holder instead of reading {@code PlayerRenderState}
 * directly (same pattern as {@link GotFirstPersonRenderState}):</b>
 * {@code Camera.setup} runs once per frame near the start of
 * {@code GameRenderer.render}, before {@code LevelRenderer.renderLevel}
 * does this frame's entity rendering (where {@code PlayerModelMixin}
 * actually computes the head's animated pose via {@code
 * GotPlayerAnimator.apply}). So whatever {@code CameraMixin} reads here for
 * "this frame" is actually last frame's value — a lag of a few
 * milliseconds at most at any normal framerate, invisible in practice for
 * a smooth continuous bob curve like this one, but worth stating plainly
 * rather than leaving implicit.
 *
 * <p><b>Only position, never rotation:</b> {@code PlayerModelMixin} only
 * ever writes the head's {@code x}/{@code y} here (z is intentionally
 * dropped too — see {@code CameraMixin}'s class doc for why), never
 * {@code xRot}/{@code yRot}/{@code zRot}. Some of Got's own clips (RUNNING
 * in particular) rotate the head cube by a lot — a constant 18-degree pitch
 * while sprinting, for the model — and mirroring that onto the actual
 * camera look direction would tilt the player's real view toward the
 * ground while running, which is a gameplay problem, not a polish one. The
 * model can lean into its pose however it wants; the camera only ever
 * borrows its translation.
 *
 * <p>Only ever meaningfully non-zero while {@code
 * GotAnimatedPlayerState#got$isLocalFirstPerson()} was true on the frame
 * that wrote it (see {@code PlayerModelMixin}) — {@code CameraMixin}
 * separately re-checks camera type/entity itself before using this, so
 * stale leftover values from the last time the player was in first person
 * are harmless even if read once right after switching to third person.
 */
public final class GotHeadBobState {

    private GotHeadBobState() {}

    private static float headBobX = 0.0F;
    private static float headBobY = 0.0F;

    public static void setHeadBob(float x, float y) {
        headBobX = x;
        headBobY = y;
    }

    public static float getHeadBobX() {
        return headBobX;
    }

    public static float getHeadBobY() {
        return headBobY;
    }
}