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
    private static final ResourceLocation MAP_BACKGROUND =
            ResourceLocation.fromNamespaceAndPath("got", "textures/gui/map/map_background.png");

    /**
     * widgets.png: 256×256 sheet.
     * Normal button:  u=0, v=0,  120×20 px
     * Hovered button: u=0, v=22, 120×20 px
     */
    private static final ResourceLocation WIDGETS_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/gui/map/widgets.png");

    private static final int BUTTON_TEX_W = 120;
    private static final int BUTTON_TEX_H = 20;
    private static final int BUTTON_V_NORMAL  = 0;
    private static final int BUTTON_V_HOVERED = 22;

    /** Rendered button size in GUI pixels — scales with GUI scale automatically. */
    private static final int BUTTON_W = 80;
    private static final int BUTTON_H = 14;

    private static final int MAP_PIXEL_WIDTH  = 2000;
    private static final int MAP_PIXEL_HEIGHT = 2000;

    /** Uniform margin from screen edge to canvas on all sides. */
    private static final int MAP_BORDER = 20;

    private GotMapWidget mapWidget;

    // Button state
    private int btnX, btnY;
    private boolean btnHovered = false;

    private int canvasX, canvasY, canvasW, canvasH;

    public GotMapScreen() {
        super(Component.literal("Map"));
    }

    /* ============================================================= */
    /* =========================== INIT ============================ */
    /* ============================================================= */

    @Override
    protected void init() {
        mapWidget = null;

        int lineH = font.lineHeight;
        int barH  = 2 + lineH + 2;   // HUD bar height above and below canvas

        // Canvas: MAP_BORDER on all sides, with equal top/bottom spacing
        canvasX = MAP_BORDER;
        canvasY = MAP_BORDER + barH;
        canvasW = width  - MAP_BORDER * 2;
        canvasH = height - MAP_BORDER - canvasY; // bottom margin = MAP_BORDER

        mapWidget = new GotMapWidget(
                canvasX, canvasY,
                canvasW, canvasH,
                MAP_TEXTURE,
                MAP_PIXEL_WIDTH, MAP_PIXEL_HEIGHT
        );
        addRenderableWidget(mapWidget);

        // Button top-left, in the bar above the canvas
        btnX = MAP_BORDER;
        btnY = MAP_BORDER + (barH - BUTTON_H) / 2;
    }

    /* ============================================================= */
    /* ========================== RENDER =========================== */
    /* ============================================================= */

    @Override
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {

        // === TILED BACKGROUND ===
        for (int x = 0; x < width; x += 256) {
            for (int y = 0; y < height; y += 256) {
                gfx.blit(RenderType::guiTextured, MAP_BACKGROUND,
                        x, y, 0, 0,
                        Math.min(256, width - x), Math.min(256, height - y),
                        256, 256);
            }
        }

        // === WIDGETS (map canvas) ===
        super.render(gfx, mouseX, mouseY, partialTick);

        // === BACK BUTTON — textured, in bar above canvas ===
        btnHovered = mouseX >= btnX && mouseX < btnX + BUTTON_W
                && mouseY >= btnY && mouseY < btnY + BUTTON_H;
        int btnV = btnHovered ? BUTTON_V_HOVERED : BUTTON_V_NORMAL;

        gfx.blit(RenderType::guiTextured, WIDGETS_TEXTURE,
                btnX, btnY,
                0, btnV,
                BUTTON_W, BUTTON_H,
                256, 256);

        // Button label centred on the button
        String btnLabel  = "Menu";
        int    lblX      = btnX + (BUTTON_W - font.width(btnLabel)) / 2;
        int    lblY      = btnY + (BUTTON_H - font.lineHeight) / 2;
        gfx.drawString(font, btnLabel, lblX, lblY, 0xFFFFFF, false);

        // === MAP TITLE — plain white, centred in bar above canvas ===
        String title  = "The Lands of Ice and Fire";
        int    titleX = (width - font.width(title)) / 2;
        int    titleY = MAP_BORDER + 2;
        gfx.drawString(font, title, titleX, titleY, 0xFFFFFF, false);

        // === COORDINATE DISPLAY — plain white, centred below canvas ===
        if (mapWidget != null) {
            BlockPos pos = mapWidget.getHoveredWorldPos(mouseX, mouseY);
            if (pos != null) {
                String text  = "x: " + pos.getX() + "z: " + pos.getZ();
                int    tx    = (width - font.width(text)) / 2;
                int    ty    = canvasY + canvasH + 2;
                gfx.drawString(font, text, tx, ty, 0xFFFFFF, false);
            }
        }
    }

    /* ============================================================= */
    /* ========================== INPUT ============================ */
    /* ============================================================= */

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        // Handle back button click
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
    public boolean isPauseScreen() {
        return false;
    }
}