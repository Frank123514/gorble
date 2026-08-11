package net.got.client.gui.widget;

import net.got.client.gui.GotPlaceholderScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

/**
 * Simple "Coming Soon" panel used for the Skills / Magic / Culture tabs -
 * see {@link GotPlaceholderScreen}.
 *
 * <p>Deliberately structured as a real {@link AbstractWidget} (same base
 * class as {@link GotMapWidget}) rather than being painted directly in the
 * screen's render() method, so every tab goes through the same rendering
 * path.
 *
 * <p>Purely visual: {@link #mouseClicked} always returns {@code false} so
 * this widget never swallows clicks. Its bounds cover the whole tab canvas,
 * so without this override it would sit in front of (in the screen's widget
 * list, though not necessarily on screen) any interactive widget added over
 * the same area - such as Culture's "Reset Affiliation" button - and eat
 * every click meant for it.
 */
public class GotPlaceholderWidget extends AbstractWidget {

    private final String title;
    private final String body;

    public GotPlaceholderWidget(int x, int y, int width, int height,
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