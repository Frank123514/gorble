package net.got.client.gui.widget;

import net.got.client.gui.PlaceholderScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class PlaceholderWidget extends AbstractWidget {

    private final String title;
    private final String body;

    public PlaceholderWidget(int x, int y, int width, int height,
                                String title, String body) {
        super(x, y, width, height, Component.empty());
        this.title = title;
        this.body  = body;
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        var font = net.minecraft.client.Minecraft.getInstance().font;

        int cx = getX() + width  / 2;
        int cy = getY() + height / 2;

        gfx.drawCenteredString(font, title, cx, cy - 20, 0xFFE8C060);
        gfx.drawCenteredString(font, body,  cx, cy,      0xFFAA9050);
        gfx.drawCenteredString(font, "-- Coming Soon --", cx, cy + 20, 0xFF7A6A48);
    }

    @Override
    public boolean mouseClicked(net.minecraft.client.input.MouseButtonEvent __event, boolean __doubleClick){
        return false;
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {}
}