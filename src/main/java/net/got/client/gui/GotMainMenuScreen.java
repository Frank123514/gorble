package net.got.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * Main GoT menu screen.
 *
 * Widget sheet layout (all buttons 120×20 px):
 *   v=  0  Back  – normal
 *   v= 22  Back  – hovered
 *   v= 44  Map   – normal
 *   v= 66  Map   – hovered
 *   v= 88  Close – normal
 *   v=110  Close – hovered
 */
public final class GotMainMenuScreen extends Screen {

    private static final ResourceLocation WIDGETS_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/gui/map/widgets.png");

    /** Button size — matches the source texture exactly so no pixels are cropped or stretched. */
    private static final int BTN_W = 120;
    private static final int BTN_H = 20;
    private static final int BTN_GAP = 8;

    private static final int V_MAP_NORM   = 44;
    private static final int V_MAP_HOV    = 66;
    private static final int V_CLOSE_NORM = 88;
    private static final int V_CLOSE_HOV  = 110;

    private int mapBtnX, mapBtnY;
    private int closeBtnX, closeBtnY;
    private boolean mapHov   = false;
    private boolean closeHov = false;

    public GotMainMenuScreen() {
        super(Component.literal("Game of Thrones"));
    }

    @Override
    protected void init() {
        int cx = width  / 2;
        int cy = height / 2;

        mapBtnX   = cx - BTN_W / 2;
        mapBtnY   = cy - BTN_H - BTN_GAP / 2;
        closeBtnX = cx - BTN_W / 2;
        closeBtnY = cy + BTN_GAP / 2;
    }

    @Override
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY,
                       float partialTick) {

        renderBackground(gfx, mouseX, mouseY, partialTick);

        mapHov   = isOver(mouseX, mouseY, mapBtnX,   mapBtnY);
        closeHov = isOver(mouseX, mouseY, closeBtnX, closeBtnY);

        String title = "Game of Thrones";
        gfx.drawString(font, title,
                (width - font.width(title)) / 2,
                mapBtnY - 28,
                0xFFE8C060, true);

        String sub = "The Lands of Ice and Fire";
        gfx.drawString(font, sub,
                (width - font.width(sub)) / 2,
                mapBtnY - 14,
                0xFFAA9050, false);

        drawButton(gfx, mapBtnX,   mapBtnY,   V_MAP_NORM,   V_MAP_HOV,   mapHov,   "Map");
        drawButton(gfx, closeBtnX, closeBtnY, V_CLOSE_NORM, V_CLOSE_HOV, closeHov, "Close");
    }

    private void drawButton(GuiGraphics gfx, int bx, int by,
                            int vNorm, int vHov, boolean hovered, String label) {
        int v = hovered ? vHov : vNorm;

        // Blit the full 120×20 source region at 120×20 screen pixels — 1:1, no stretching
        gfx.blit(RenderType::guiTextured, WIDGETS_TEXTURE,
                bx, by,
                0, v,
                BTN_W, BTN_H,
                256, 256);

        int colour = hovered ? 0xFFFFFFFF : 0xFFE8D8A0;
        int lx = bx + (BTN_W - font.width(label)) / 2;
        int ly = by + (BTN_H - font.lineHeight)    / 2;
        gfx.drawString(font, label, lx, ly, colour, hovered);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0) return super.mouseClicked(mouseX, mouseY, button);

        if (isOver(mouseX, mouseY, mapBtnX, mapBtnY)) {
            Minecraft.getInstance().setScreen(new GotMapScreen());
            return true;
        }
        if (isOver(mouseX, mouseY, closeBtnX, closeBtnY)) {
            Minecraft.getInstance().setScreen(null);
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private boolean isOver(double mx, double my, int bx, int by) {
        return mx >= bx && mx < bx + BTN_W && my >= by && my < by + BTN_H;
    }

    @Override
    public boolean isPauseScreen() { return false; }
}