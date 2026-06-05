package net.got.client.input;

import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public final class GotKeybinds {

    public static final KeyMapping OPEN_MAP =
            new KeyMapping(
                    "key.got.open_map",
                    GLFW.GLFW_KEY_M,
                    "key.categories.got"
            );

    /**
     * Block / raise guard key.  Default: right mouse button (GLFW_MOUSE_BUTTON_2).
     * Hold this key while holding a sword to enter the BLOCK pose.
     * The vanilla "use item" action already fires on right-click, so we piggyback
     * on top of it; this keybind just gives players a visible "Block" label in
     * the controls screen.
     */
    public static final KeyMapping BLOCK =
            new KeyMapping(
                    "key.got.block",
                    GLFW.GLFW_KEY_R,   // default: Q — easy to reach while WASD-moving
                    "key.categories.got"
            );

    private GotKeybinds() {}
}
