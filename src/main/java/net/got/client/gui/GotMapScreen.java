package net.got.client.gui;

import net.got.client.gui.widget.GotMapWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class GotMapScreen extends Screen {

    private static final ResourceLocation MAP_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/gui/map/known_world.png");

    private static final ResourceLocation WIDGETS_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/gui/map/widgets.png");

    private static final int BUTTON_W = 120;
    private static final int BUTTON_H = 20;

    private static final int BUTTON_V_NORMAL  = 0;
    private static final int BUTTON_V_HOVERED = 22;

    private static final int MAP_PIXEL_WIDTH  = 2000;
    private static final int MAP_PIXEL_HEIGHT = 2000;

    private static final int MAP_BORDER = 20;

    private GotMapWidget mapWidget;

    private int btnX, btnY;
    private boolean btnHovered = false;

    private int canvasX, canvasY, canvasW, canvasH;

    public GotMapScreen() {
        super(Component.literal("Map"));
    }

    /* ------------------------------------------------------------------ */
    /* Init                                                                 */
    /* ------------------------------------------------------------------ */

    @Override
    protected void init() {
        mapWidget = null;

        int lineH = font.lineHeight;
        int barH  = 2 + lineH + 2;

        canvasX = MAP_BORDER;
        canvasY = MAP_BORDER + barH;
        canvasW = width  - MAP_BORDER * 2;
        canvasH = height - MAP_BORDER - canvasY;

        mapWidget = new GotMapWidget(
                canvasX, canvasY,
                canvasW, canvasH,
                MAP_TEXTURE,
                MAP_PIXEL_WIDTH, MAP_PIXEL_HEIGHT
        );
        addRenderableWidget(mapWidget);

        btnX = MAP_BORDER;
        btnY = (canvasY - BUTTON_H) / 2;
    }

    /* ------------------------------------------------------------------ */
    /* Render                                                               */
    /* ------------------------------------------------------------------ */

    @Override
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {

        // Dark background behind the whole screen (no tiling)
        gfx.fill(0, 0, width, height, 0xFF111008);

        // Map canvas widget (also draws the iron border)
        super.render(gfx, mouseX, mouseY, partialTick);

        // Back button
        btnHovered = mouseX >= btnX && mouseX < btnX + BUTTON_W
                && mouseY >= btnY && mouseY < btnY + BUTTON_H;
        int btnV = btnHovered ? BUTTON_V_HOVERED : BUTTON_V_NORMAL;

        gfx.blit(RenderType::guiTextured, WIDGETS_TEXTURE,
                btnX, btnY,
                0, btnV,
                BUTTON_W, BUTTON_H,
                256, 256);

        String btnLabel = "Menu";
        int lblX = btnX + (BUTTON_W - font.width(btnLabel)) / 2;
        int lblY = btnY + (BUTTON_H - font.lineHeight)       / 2;
        gfx.drawString(font, btnLabel, lblX, lblY,
                btnHovered ? 0xFFFFFFFF : 0xFFE8D8A0, btnHovered);

        // Title
        String title  = "The Lands of Ice and Fire";
        int    titleX = (width - font.width(title)) / 2;
        int    titleY = MAP_BORDER + 2;
        gfx.drawString(font, title, titleX, titleY, 0xFFE8C060, false);

        // Coordinates
        if (mapWidget != null) {
            BlockPos pos = mapWidget.getHoveredWorldPos(mouseX, mouseY);
            if (pos != null) {
                String text = "x: " + pos.getX() + "  z: " + pos.getZ();
                int    tx   = (width - font.width(text)) / 2;
                int    ty   = canvasY + canvasH + 2;
                gfx.drawString(font, text, tx, ty, 0xFFFFFFFF, false);
            }
        }
    }

    /* ------------------------------------------------------------------ */
    /* Input                                                                */
    /* ------------------------------------------------------------------ */

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && mouseX >= btnX && mouseX < btnX + BUTTON_W
                && mouseY >= btnY && mouseY < btnY + BUTTON_H) {
            Minecraft.getInstance().setScreen(new GotMainMenuScreen());
            return true;
        }
        if (super.mouseClicked(mouseX, mouseY, button)) return true;
        return mapWidget != null && mapWidget.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dx, double dy) {
        if (super.mouseDragged(mouseX, mouseY, button, dx, dy)) return true;
        return mapWidget != null && mapWidget.mouseDragged(mouseX, mouseY, button, dx, dy);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY) {
        if (super.mouseScrolled(mouseX, mouseY, deltaX, deltaY)) return true;
        return mapWidget != null && mapWidget.mouseScrolled(mouseX, mouseY, deltaX, deltaY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (super.keyPressed(keyCode, scanCode, modifiers)) return true;
        return mapWidget != null && mapWidget.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean isPauseScreen() { return false; }
}