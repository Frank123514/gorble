package net.got.client.animation.player;

public final class HeadBobState {

    private HeadBobState() {}

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