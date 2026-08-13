package net.got.client.input;

import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public final class Keybinds {

    public static final KeyMapping.Category CATEGORY =
            KeyMapping.Category.register(Identifier.fromNamespaceAndPath("got", "got"));

    public static final KeyMapping OPEN_MAP =
            new KeyMapping(
                    "key.got.open_map",
                    GLFW.GLFW_KEY_M,
                    CATEGORY
            );

    public static final KeyMapping BLOCK =
            new KeyMapping(
                    "key.got.block",
                    GLFW.GLFW_KEY_R,
                    CATEGORY
            );

    private Keybinds() {}
}
