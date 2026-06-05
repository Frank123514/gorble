package net.got.client.gui;

import net.got.client.gui.widget.GotMapWidget;
import net.got.faction.WaypointRegistry;
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

    /** Parchment background texture — 660×390 px. */
    private static final ResourceLocation MAP_BG_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/gui/map/map_background.png");
    private static final int MAP_BG_W = 660;
    private static final int MAP_BG_H = 390;

    /** How many pixels of torn-edge border to leave on each side of the canvas. */
    private static final int PARCHMENT_BORDER = 18;

    private static final int BUTTON_W = 120;
    private static final int BUTTON_H = 20;

    /** Smaller square button for the fullscreen toggle. */
    private static final int FS_BUTTON_W = 20;
    private static final int FS_BUTTON_H = 20;

    private static final int BUTTON_V_NORMAL  = 0;
    private static final int BUTTON_V_HOVERED = 22;

    private static final int MAP_PIXEL_WIDTH  = 4207;
    private static final int MAP_PIXEL_HEIGHT = 3277;



    private GotMapWidget mapWidget;

    private int btnX, btnY;
    private boolean btnHovered = false;

    /** Fullscreen toggle button position and hover state. */
    private int fsBtnX, fsBtnY;
    private boolean fsBtnHovered = false;

    /** Whether the map is currently filling the whole screen. */
    private boolean fullscreen = false;

    // Window bounds (the whole shrunken panel including the header bar)
    private int winX, winY, winW, winH;
    private int scaledBorder;

    // Map canvas bounds (inside the window, below the header bar)
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
        rebuildLayout();
    }

    /** (Re-)computes all positional fields and recreates the map widget. */
    private void rebuildLayout() {
        clearWidgets();

        if (fullscreen) {
            // Fill the entire screen with the map canvas
            canvasX = 0;
            canvasY = 0;
            canvasW = width;
            canvasH = height;

            // Window bounds aren't used for drawing in fullscreen, but keep them
            // consistent so the exit button can be positioned relative to them.
            winX = 0; winY = 0; winW = width; winH = height;
            scaledBorder = 0;

            // Fullscreen toggle button: bottom-right corner, above coordinates line
            fsBtnX = width  - FS_BUTTON_W - 6;
            fsBtnY = height - FS_BUTTON_H - 6;

            // "Menu" button in fullscreen: top-left
            btnX = 6;
            btnY = 6;
        } else {
            // Size the window as a fraction of the screen so it naturally scales
            // with GUI scale (larger GUI scale = smaller screen dims = smaller map).
            // Clamp so it always fits with room for the button above.
            float targetW = width  * 0.82f;
            float targetH = height * 0.82f;
            float scaleX  = targetW / MAP_BG_W;
            float scaleY  = targetH / MAP_BG_H;
            float scale   = Math.min(scaleX, scaleY);
            // Hard clamp: ensure button + map + margins never exceed screen height
            float maxScale = (float)(height - BUTTON_H - 16) / MAP_BG_H;
            scale = Math.min(scale, maxScale);

            winW = Math.round(MAP_BG_W * scale);
            winH = Math.round(MAP_BG_H * scale);
            winX = (width  - winW) / 2;
            winY = (height - winH) / 2 + (BUTTON_H / 2); // shift down slightly for button

            // Canvas sits inside the torn-edge border of the parchment.
            scaledBorder = Math.round(PARCHMENT_BORDER * ((float) winW / MAP_BG_W));
            canvasX = winX + scaledBorder;
            canvasY = winY + scaledBorder;
            canvasW = winW - scaledBorder * 2;
            canvasH = winH - scaledBorder * 2;

            // Menu button sits above the parchment, like a tab
            btnX = winX + scaledBorder;
            btnY = winY - BUTTON_H - 4;

            // Fullscreen toggle button: sits to the right of the menu button, same row
            fsBtnX = btnX + BUTTON_W + 4;
            fsBtnY = btnY;
        }

        mapWidget = new GotMapWidget(
                canvasX, canvasY,
                canvasW, canvasH,
                MAP_TEXTURE,
                MAP_PIXEL_WIDTH, MAP_PIXEL_HEIGHT
        );
        // Show all known-world waypoints on the full map (no active highlight)
        mapWidget.setWaypoints(WaypointRegistry.ALL, -1);
        addRenderableWidget(mapWidget);
    }

    /* ------------------------------------------------------------------ */
    /* Render                                                               */
    /* ------------------------------------------------------------------ */

    @Override
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {

        if (!fullscreen) {
            // ── 1. Parchment background — scaled to fit the window ──
            float bgScaleX = (float) winW / MAP_BG_W;
            float bgScaleY = (float) winH / MAP_BG_H;
            gfx.pose().pushPose();
            gfx.pose().translate(winX, winY, 0);
            gfx.pose().scale(bgScaleX, bgScaleY, 1f);
            gfx.blit(RenderType::guiTextured, MAP_BG_TEXTURE,
                    0, 0, 0f, 0f,
                    MAP_BG_W, MAP_BG_H,
                    MAP_BG_W, MAP_BG_H);
            gfx.pose().popPose();
        } else {
            // ── 1b. Solid dark background in fullscreen mode ──
            gfx.fill(0, 0, width, height, 0xFF111111);
        }

        // ── 2. Map canvas widget ──
        super.render(gfx, mouseX, mouseY, partialTick);

        // ── 3. Back / Menu button ──
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

        // ── 4. Fullscreen toggle button ──
        fsBtnHovered = mouseX >= fsBtnX && mouseX < fsBtnX + FS_BUTTON_W
                    && mouseY >= fsBtnY && mouseY < fsBtnY + FS_BUTTON_H;
        int fsBtnV = fsBtnHovered ? BUTTON_V_HOVERED : BUTTON_V_NORMAL;

        // Reuse the same widget texture strip — blit just the 20×20 portion
        gfx.blit(RenderType::guiTextured, WIDGETS_TEXTURE,
                fsBtnX, fsBtnY,
                0, fsBtnV,
                FS_BUTTON_W, FS_BUTTON_H,
                256, 256);

        // Draw a simple ⛶ / ✕ symbol: "[ ]" when windowed, "X" when fullscreen
        String fsLabel = fullscreen ? "X" : "[ ]";
        int fsLblX = fsBtnX + (FS_BUTTON_W - font.width(fsLabel)) / 2;
        int fsLblY = fsBtnY + (FS_BUTTON_H - font.lineHeight)     / 2;
        gfx.drawString(font, fsLabel, fsLblX, fsLblY,
                fsBtnHovered ? 0xFFFFFFFF : 0xFFE8D8A0, fsBtnHovered);

        if (!fullscreen) {
            // ── 5. Title (centred in window, not full screen) ──
            String title  = "The Lands of Ice and Fire";
            int    titleX = winX + (winW - font.width(title)) / 2;
            int    titleY = winY + (canvasY - winY - font.lineHeight) / 2;
            gfx.drawString(font, title, titleX, titleY, 0xFFE8C060, false);
        }

        // ── 6. Coordinates below the canvas ──
        if (mapWidget != null) {
            BlockPos pos = mapWidget.getHoveredWorldPos(mouseX, mouseY);
            if (pos != null) {
                String text = "x: " + pos.getX() + "  z: " + pos.getZ();
                int    tx   = winX + (winW - font.width(text)) / 2;
                int    ty   = canvasY + canvasH + 4;
                gfx.drawString(font, text, tx, ty, 0xFFE8D8A0, false);
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
        // Fullscreen toggle button
        if (button == 0 && mouseX >= fsBtnX && mouseX < fsBtnX + FS_BUTTON_W
                && mouseY >= fsBtnY && mouseY < fsBtnY + FS_BUTTON_H) {
            fullscreen = !fullscreen;
            rebuildLayout();
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