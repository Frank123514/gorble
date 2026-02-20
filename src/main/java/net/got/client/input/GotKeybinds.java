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

    private GotKeybinds() {}
}
