package net.got.client.gui.overlay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class GotMapText {

    public static void renderCoords(
            GuiGraphics gfx,
            int x,
            int y,
            int mapX,
            int mapZ
    ) {
        gfx.drawString(
                Minecraft.getInstance().font,
                Component.literal("X: " + mapX + " Z: " + mapZ),
                x,
                y,
                0xFFFFFF
        );
    }
}
