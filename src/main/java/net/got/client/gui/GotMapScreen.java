package net.got.client.gui;

import net.got.client.gui.widget.GotMapWidget;
import net.got.faction.WaypointRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * The Map tab's screen, opened from {@link GotMainMenuScreen}. Always fills
 * the entire window - no parchment panel, no windowed/fullscreen toggle.
 * Just a solid backdrop with the map canvas covering the whole screen and a
 * "Menu" button in the corner to go back to the tab launcher.
 */
public class GotMapScreen extends Screen {

    private static final ResourceLocation MAP_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/gui/map/known_world.png");

    private static final ResourceLocation WIDGETS_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/gui/map/widgets.png");

    private static final int BUTTON_W = 120;
    private static final int BUTTON_H = 20;

    private static final int BUTTON_V_NORMAL  = 0;
    private static final int BUTTON_V_HOVERED = 22;

    private static final int MAP_PIXEL_WIDTH  = 4207;
    private static final int MAP_PIXEL_HEIGHT = 3277;

    /** Solid backdrop colour, filling the space behind the map canvas. */
    private static final int BACKDROP_COLOUR = 0xFF111111;

    private GotMapWidget mapWidget;

    private int btnX, btnY;
    private boolean btnHovered = false;

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

        // Fill the entire screen with the map canvas - always.
        int canvasX = 0;
        int canvasY = 0;
        int canvasW = width;
        int canvasH = height;

        // "Menu" button: top-left corner
        btnX = 6;
        btnY = 6;

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

        // Solid backdrop - no parchment art behind the map anymore.
        gfx.fill(0, 0, width, height, BACKDROP_COLOUR);

        // ── Map canvas widget ──
        super.render(gfx, mouseX, mouseY, partialTick);

        // ── Back / Menu button ──
        btnHovered = mouseX >= btnX && mouseX < btnX + BUTTON_W
                && mouseY >= btnY && mouseY < btnY + BUTTON_H;
        int btnV = btnHovered ? BUTTON_V_HOVERED : BUTTON_V_NORMAL;

        gfx.blit(RenderPipelines.GUI_TEXTURED, WIDGETS_TEXTURE,
                btnX, btnY,
                0, btnV,
                BUTTON_W, BUTTON_H,
                256, 256);

        String btnLabel = "Menu";
        int lblX = btnX + (BUTTON_W - font.width(btnLabel)) / 2;
        int lblY = btnY + (BUTTON_H - font.lineHeight)       / 2;
        gfx.drawString(font, btnLabel, lblX, lblY,
                btnHovered ? 0xFFFFFFFF : 0xFFE8D8A0, btnHovered);

        // ── Coordinates near the bottom of the screen ──
        if (mapWidget != null) {
            BlockPos pos = mapWidget.getHoveredWorldPos(mouseX, mouseY);
            if (pos != null) {
                String text = "x: " + pos.getX() + "  z: " + pos.getZ();
                int    tx   = (width - font.width(text)) / 2;
                int    ty   = height - font.lineHeight - 6;
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
