package net.got.client.animation.player;

public final class FirstPersonRenderState {

    private FirstPersonRenderState() {}

    private static boolean renderingLocalBody = false;

    public static boolean isRenderingLocalBody() {
        return renderingLocalBody;
    }

    public static void setRenderingLocalBody(boolean value) {
        renderingLocalBody = value;
    }
}
