package net.got.client.gui;

import net.got.client.ClientFactionCache;
import net.got.client.gui.widget.GotIdlePreviewWidget;
import net.got.client.gui.widget.GotMapWidget;
import net.got.client.gui.widget.GotPlaceholderWidget;
import net.got.faction.GotFactionData;
import net.got.faction.WaypointRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * Main GoT menu screen.
 *
 * <p>Layout: a parchment window with four tabs above it - Map, Skills, Magic
 * and Culture. Tabs are toggleable: clicking the active tab again deselects
 * it. Every tab's canvas content is rendered through a real widget added via
 * {@code addRenderableWidget} - {@link GotMapWidget} for Map,
 * {@link GotPlaceholderWidget} for Skills / Magic / Culture, and
 * {@link GotIdlePreviewWidget} for the idle drag-to-rotate player preview
 * shown when no tab is selected - so every tab goes through the identical
 * rendering path instead of some tabs being painted directly in this
 * screen's render() and others going through a widget.
 *
 * <p>Widget sheet layout (all buttons 120x20 px), reused for every tab plaque
 * and the small close button (cropped to 20x20):
 *   v=  0  Back  - normal
 *   v= 22  Back  - hovered
 *   v= 44  Map   - normal
 *   v= 66  Map   - hovered
 *   v= 88  Close - normal
 *   v=110  Close - hovered
 */
public final class GotMainMenuScreen extends Screen {

    private static final ResourceLocation WIDGETS_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/gui/map/widgets.png");

    private static final ResourceLocation MAP_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/gui/map/known_world.png");

    private static final ResourceLocation MAP_BG_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/gui/map/map_background.png");
    private static final int MAP_BG_W = 660;
    private static final int MAP_BG_H = 390;

    private static final int MAP_PIXEL_WIDTH  = 4207;
    private static final int MAP_PIXEL_HEIGHT = 3277;

    /** How many pixels of torn-edge border to leave on each side of the parchment canvas. */
    private static final int PARCHMENT_BORDER = 18;

    /** Extra inset (on top of the torn-edge border) so the canvas content doesn't
     *  crowd right up to the parchment's inner frame - purely cosmetic breathing room. */
    private static final int CANVAS_INSET = 14;

    private static final int TAB_W   = 108;
    private static final int TAB_H   = 20;
    private static final int TAB_GAP = 6;

    private static final int CLOSE_W = 20;
    private static final int CLOSE_H = 20;

    private static final int V_NORM = 0;
    private static final int V_HOV  = 22;

    /** The four tabs, in display order. */
    private enum Tab {
        MAP("Map"), SKILLS("Skills"), MAGIC("Magic"), CULTURE("Culture");

        final String label;
        Tab(String label) { this.label = label; }
    }

    /** Currently selected tab, or {@code null} when no tab is selected (idle preview). */
    private Tab selectedTab = null;

    // Window bounds (the parchment panel, excluding the tab row above it)
    private int winX, winY, winW, winH;
    private int scaledBorder;

    // Canvas bounds (inside the parchment's torn-edge border)
    private int canvasX, canvasY, canvasW, canvasH;

    // Tab row bounds
    private int tabRowY;
    private final int[] tabX = new int[Tab.values().length];

    // Close button bounds
    private int closeBtnX, closeBtnY;

    public GotMainMenuScreen() {
        super(Component.literal("Game of Thrones"));
    }

    /* ------------------------------------------------------------------ */
    /* Init                                                                 */
    /* ------------------------------------------------------------------ */

    @Override
    protected void init() {
        rebuildLayout();
    }

    private void rebuildLayout() {
        clearWidgets();

        float targetW = width  * 0.82f;
        float targetH = height * 0.82f;
        float scaleX  = targetW / MAP_BG_W;
        float scaleY  = targetH / MAP_BG_H;
        float scale   = Math.min(scaleX, scaleY);
        float maxScale = (float) (height - TAB_H - 16) / MAP_BG_H;
        scale = Math.min(scale, maxScale);

        winW = Math.round(MAP_BG_W * scale);
        winH = Math.round(MAP_BG_H * scale);
        winX = (width  - winW) / 2;
        winY = (height - winH) / 2 + (TAB_H / 2);

        scaledBorder = Math.round(PARCHMENT_BORDER * ((float) winW / MAP_BG_W));
        int scaledInset = Math.round(CANVAS_INSET * ((float) winW / MAP_BG_W));
        canvasX = winX + scaledBorder + scaledInset;
        canvasY = winY + scaledBorder + scaledInset;
        canvasW = winW - (scaledBorder + scaledInset) * 2;
        canvasH = winH - (scaledBorder + scaledInset) * 2;

        // Tab row sits above the parchment, like tabs on a folder
        tabRowY = winY - TAB_H - 4;
        int totalTabsW = Tab.values().length * TAB_W + (Tab.values().length - 1) * TAB_GAP;
        int startX = winX + (winW - totalTabsW) / 2;
        for (int i = 0; i < Tab.values().length; i++) {
            tabX[i] = startX + i * (TAB_W + TAB_GAP);
        }

        closeBtnX = winX + winW - CLOSE_W;
        closeBtnY = tabRowY;

        // Every tab's canvas content is a real widget, added the same way.
        if (selectedTab == Tab.MAP) {
            GotMapWidget mapWidget = new GotMapWidget(
                    canvasX, canvasY,
                    canvasW, canvasH,
                    MAP_TEXTURE,
                    MAP_PIXEL_WIDTH, MAP_PIXEL_HEIGHT
            );
            mapWidget.setWaypoints(WaypointRegistry.ALL, -1);
            addRenderableWidget(mapWidget);
        } else if (selectedTab == null) {
            addRenderableWidget(new GotIdlePreviewWidget(canvasX, canvasY, canvasW, canvasH));
        } else {
            String title;
            String body;
            switch (selectedTab) {
                case SKILLS -> {
                    title = "Skills";
                    body  = "Your skills and abilities will appear here.";
                }
                case MAGIC -> {
                    title = "Magic";
                    body  = "The magics of this world will appear here.";
                }
                case CULTURE -> {
                    title = "Culture";
                    body  = cultureBodyText();
                }
                default -> {
                    title = "";
                    body  = "";
                }
            }
            addRenderableWidget(new GotPlaceholderWidget(canvasX, canvasY, canvasW, canvasH, title, body));

            if (selectedTab == Tab.CULTURE) {
                int btnW = 160, btnH = 20;
                int btnX = canvasX + (canvasW - btnW) / 2;
                int btnY = canvasY + canvasH - btnH - 16;
                addRenderableWidget(Button.builder(
                        // TEMP marker "[v2]" - if this text isn't visible in-game after your
                        // rebuild, the running game isn't using this file. Remove once confirmed.
                        Component.literal("Reset Affiliation [v2]"),
                        b -> onResetAffiliation()
                ).bounds(btnX, btnY, btnW, btnH).build());
            }
        }
    }

    /** Builds the summary text shown on the Culture tab above the reset button. */
    private String cultureBodyText() {
        String factionId = ClientFactionCache.getFactionId();
        if (factionId.isEmpty()) {
            return "You have not sworn allegiance to any culture yet.";
        }
        GotFactionData faction = ClientFactionCache.getFaction();
        String houseName = faction != null ? faction.greatHouse() : factionId;
        String currentTitle = ClientFactionCache.getTitle();
        return currentTitle.isEmpty()
                ? "Sworn to " + houseName + "."
                : currentTitle + " of " + houseName + ".";
    }

    /** Closes the main menu and opens the faction selection screen so the player can reset their affiliation. */
    private void onResetAffiliation() {
        net.got.GotMod.LOGGER.info("[GoT] onResetAffiliation() fired");
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            mc.player.displayClientMessage(
                    Component.literal("§e[GoT] Reset Affiliation clicked"), false);
        }
        mc.setScreen(new FactionSelectionScreen());
    }

    /* ------------------------------------------------------------------ */
    /* Render                                                               */
    /* ------------------------------------------------------------------ */

    @Override
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {

        // No full-screen fill here - matches GotMapScreen, which draws the
        // parchment directly over the world with nothing behind it.

        // Parchment window background
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

        // Widgets (map / placeholder / idle preview - always exactly one, added in rebuildLayout)
        super.render(gfx, mouseX, mouseY, partialTick);

        // Header text in the parchment's top border gap
        String header;
        if (selectedTab == null) {
            header = "The Lands of Ice and Fire";
        } else {
            switch (selectedTab) {
                case MAP -> header = "The Lands of Ice and Fire";
                case SKILLS -> header = "Skills";
                case MAGIC -> header = "Magic";
                case CULTURE -> header = "Culture";
                default -> header = "";
            }
        }
        int headerX = winX + (winW - font.width(header)) / 2;
        int headerY = winY + (canvasY - winY - font.lineHeight) / 2;
        gfx.drawString(font, header, headerX, headerY, 0xFFE8C060, false);

        // Tab bar
        for (int i = 0; i < Tab.values().length; i++) {
            Tab tab = Tab.values()[i];
            boolean hovered  = isOver(mouseX, mouseY, tabX[i], tabRowY, TAB_W, TAB_H);
            boolean selected = tab == selectedTab;
            drawPlaque(gfx, tabX[i], tabRowY, TAB_W, TAB_H, hovered || selected, tab.label);
        }

        // Close button
        boolean closeHov = isOver(mouseX, mouseY, closeBtnX, closeBtnY, CLOSE_W, CLOSE_H);
        int closeV = closeHov ? V_HOV : V_NORM;
        gfx.blit(RenderType::guiTextured, WIDGETS_TEXTURE,
                closeBtnX, closeBtnY,
                0, closeV,
                CLOSE_W, CLOSE_H,
                256, 256);
        String x = "X";
        gfx.drawString(font, x,
                closeBtnX + (CLOSE_W - font.width(x)) / 2,
                closeBtnY + (CLOSE_H - font.lineHeight) / 2,
                closeHov ? 0xFFFFFFFF : 0xFFE8D8A0, closeHov);
    }

    private void drawPlaque(GuiGraphics gfx, int bx, int by, int w, int h,
                            boolean hovered, String label) {
        int v = hovered ? V_HOV : V_NORM;
        gfx.blit(RenderType::guiTextured, WIDGETS_TEXTURE,
                bx, by,
                0, v,
                w, h,
                256, 256);
        int colour = hovered ? 0xFFFFFFFF : 0xFFE8D8A0;
        int lx = bx + (w - font.width(label)) / 2;
        int ly = by + (h - font.lineHeight) / 2;
        gfx.drawString(font, label, lx, ly, colour, hovered);
    }

    /* ------------------------------------------------------------------ */
    /* Input                                                                */
    /* ------------------------------------------------------------------ */

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        net.got.GotMod.LOGGER.info("[GoT] mouseClicked at ({}, {}) button={} selectedTab={}",
                mouseX, mouseY, button, selectedTab);

        if (button == 0) {
            for (int i = 0; i < Tab.values().length; i++) {
                if (isOver(mouseX, mouseY, tabX[i], tabRowY, TAB_W, TAB_H)) {
                    Tab clicked = Tab.values()[i];
                    selectedTab = (selectedTab == clicked) ? null : clicked;
                    rebuildLayout();
                    return true;
                }
            }
            if (isOver(mouseX, mouseY, closeBtnX, closeBtnY, CLOSE_W, CLOSE_H)) {
                Minecraft.getInstance().setScreen(null);
                return true;
            }
        }

        boolean handledByWidget = super.mouseClicked(mouseX, mouseY, button);
        net.got.GotMod.LOGGER.info("[GoT] super.mouseClicked (widget dispatch) returned {}", handledByWidget);
        return handledByWidget;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dx, double dy) {
        return super.mouseDragged(mouseX, mouseY, button, dx, dy);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY) {
        return super.mouseScrolled(mouseX, mouseY, deltaX, deltaY);
    }

    private boolean isOver(double mx, double my, int bx, int by, int w, int h) {
        return mx >= bx && mx < bx + w && my >= by && my < by + h;
    }

    @Override
    public boolean isPauseScreen() { return false; }
}