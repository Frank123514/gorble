package net.got.client.input;

import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public final class GotKeybinds {

    // 1.21.9+: KeyMapping categories are no longer raw translation-key strings; they must be
    // registered as a KeyMapping.Category via KeyMapping.Category#register.
    public static final KeyMapping.Category CATEGORY =
            KeyMapping.Category.register(Identifier.fromNamespaceAndPath("got", "got"));

    public static final KeyMapping OPEN_MAP =
            new KeyMapping(
                    "key.got.open_map",
                    GLFW.GLFW_KEY_M,
                    CATEGORY
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
                    CATEGORY
            );

    private GotKeybinds() {}
}
