package net.got.client.gui;

import net.got.client.gui.widget.GotMapWidget;
import net.got.faction.GotFactionData;
import net.got.faction.GotFactions;
import net.got.faction.WaypointData;
import net.got.faction.WaypointRegistry;
import net.got.network.SelectFactionPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Faction selection screen.
 *
 * <p>Layout:
 * <pre>
 *  ┌────────────────────────────────────────────────────────────────┐
 *  │               — CHOOSE YOUR ALLEGIANCE —                       │
 *  │           [<]       Westeros       [>]   ← continent           │
 *  │           [<]      The North       [>]   ← region              │
 *  │                                                                │
 *  │  ┌─────────────────┐   ┌──────────────────────────────────┐   │
 *  │  │  Full scrollable│   │ House Stark                      │   │
 *  │  │  zoomable map   │   │ Lord Paramount of The North      │   │
 *  │  │  widget         │   │ Seat:   Winterfell               │   │
 *  │  └─────────────────┘   │ Fealty: The Iron Throne          │   │
 *  │  [<] Winterfell [>]    │ <lore>                           │   │
 *  │                        └──────────────────────────────────┘   │
 *  │                    [  Confirm Selection  ]                      │
 *  └────────────────────────────────────────────────────────────────┘
 * </pre>
 */
public final class FactionSelectionScreen extends Screen {

    // ── Map texture ───────────────────────────────────────────────────────────
    private static final ResourceLocation MAP_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/gui/map/known_world.png");
    private static final int MAP_PIXEL_W = 4207;
    private static final int MAP_PIXEL_H = 3277;

    // ── Panel ─────────────────────────────────────────────────────────────────
    private static final int PANEL_W = 420;
    private static final int PANEL_H = 310;

    // ── Nav rows ──────────────────────────────────────────────────────────────
    private static final int NAV_ARROW_W  = 18;
    private static final int NAV_ARROW_H  = 16;
    private static final int NAV_LABEL_W  = 140;
    private static final int NAV_CONT_Y   = 24;
    private static final int NAV_REGION_Y = NAV_CONT_Y + NAV_ARROW_H + 4;

    // ── Map canvas (left column, square-ish) ──────────────────────────────────
    private static final int MAP_X_OFF = 8;
    private static final int MAP_Y_OFF = NAV_REGION_Y + NAV_ARROW_H + 8;
    private static final int MAP_W     = 150;
    private static final int MAP_H     = 150;

    // ── Location nav (below map canvas) ───────────────────────────────────────
    private static final int LOC_NAV_H     = 14;
    private static final int LOC_NAV_Y_OFF = MAP_Y_OFF + MAP_H + 6;

    // ── Info panel (right column) ─────────────────────────────────────────────
    private static final int INFO_X_OFF = MAP_X_OFF + MAP_W + 8;
    private static final int INFO_Y_OFF = MAP_Y_OFF;
    private static final int INFO_W     = PANEL_W - INFO_X_OFF - 8;
    private static final int INFO_H     = PANEL_H - INFO_Y_OFF - 36;

    // ── Confirm button ────────────────────────────────────────────────────────
    private static final int CONFIRM_W = 160;
    private static final int CONFIRM_H = 20;
    private static final int CONFIRM_B = 8;

    // ── Colours ───────────────────────────────────────────────────────────────
    private static final int COL_TITLE      = 0xFFE8C060;
    private static final int COL_HOUSE_NAME = 0xFFFFD700;
    private static final int COL_LABEL      = 0xFFCCCCAA;
    private static final int COL_LORE       = 0xFFAAAAAA;

    // ── GUI scale override ────────────────────────────────────────────────────
    private static final int MIN_SCALE = 3;
    private int savedGuiScale = -1;

    // ── State ─────────────────────────────────────────────────────────────────
    private final List<String> continentKeys;
    private int continentIndex = 0;
    private int regionIndex    = 0;
    private int locationIndex  = 0; // which location is "active"

    /** The live scrollable/zoomable map widget. Persisted across rebuildWidgets. */
    private GotMapWidget mapWidget;

    public FactionSelectionScreen() {
        super(Component.literal("Choose Your Allegiance"));
        continentKeys = new ArrayList<>(GotFactions.CONTINENTS.keySet());
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private String currentContinent() {
        return continentKeys.isEmpty() ? "" : continentKeys.get(continentIndex);
    }

    private List<GotFactionData> currentFactions() {
        return GotFactions.forContinent(currentContinent());
    }

    private GotFactionData currentFaction() {
        List<GotFactionData> f = currentFactions();
        if (f.isEmpty() || regionIndex >= f.size()) return null;
        return f.get(regionIndex);
    }

    private List<WaypointData> currentWaypoints() {
        GotFactionData f = currentFaction();
        if (f == null) return List.of();
        return WaypointRegistry.BY_FACTION.getOrDefault(f.id(), List.of());
    }

    private WaypointData currentWaypoint() {
        List<WaypointData> wps = currentWaypoints();
        if (wps.isEmpty()) return null;
        if (locationIndex >= wps.size()) locationIndex = 0;
        return wps.get(locationIndex);
    }

    /** Returns just the display name of the active waypoint (or empty string). */
    private String currentLocationName() {
        WaypointData wp = currentWaypoint();
        return wp != null ? wp.name() : "";
    }

    /** Pushes the current faction's waypoints and active index into the map widget. */
    private void syncWaypointsToMap() {
        if (mapWidget == null) return;
        List<WaypointData> wps = currentWaypoints();
        if (wps.isEmpty()) {
            mapWidget.setWaypoints(List.of(), -1);
        } else {
            mapWidget.setWaypoints(wps, locationIndex);
        }
    }

    // ── Widget construction ───────────────────────────────────────────────────

    private void buildWidgets() {
        int px = (width  - PANEL_W) / 2;
        int py = (height - PANEL_H) / 2;

        // Map widget — created once, repositioned on rebuild
        int mx = px + MAP_X_OFF;
        int my = py + MAP_Y_OFF;
        if (mapWidget == null) {
            mapWidget = new GotMapWidget(mx, my, MAP_W, MAP_H,
                    MAP_TEXTURE, MAP_PIXEL_W, MAP_PIXEL_H);
        } else {
            mapWidget.setX(mx);
            mapWidget.setY(my);
            mapWidget.setWidth(MAP_W);
            mapWidget.setHeight(MAP_H);
        }
        addRenderableWidget(mapWidget);

        syncWaypointsToMap();

        buildContinentNav(px, py);
        buildRegionNav(px, py);
        buildLocationNav(px, py);
        buildConfirmButton(px, py);
    }

    private void buildContinentNav(int px, int py) {
        int mid    = px + PANEL_W / 2;
        int leftX  = mid - NAV_LABEL_W / 2 - 4 - NAV_ARROW_W;
        int rightX = mid + NAV_LABEL_W / 2 + 4;
        int ry     = py + NAV_CONT_Y;
        addRenderableWidget(Button.builder(Component.literal("<"), b -> {
            continentIndex = (continentIndex - 1 + continentKeys.size()) % continentKeys.size();
            regionIndex = 0;
            locationIndex = 0;
            mapWidget = null; // let map reset
            rebuildWidgets();
        }).bounds(leftX, ry, NAV_ARROW_W, NAV_ARROW_H).build());
        addRenderableWidget(Button.builder(Component.literal(">"), b -> {
            continentIndex = (continentIndex + 1) % continentKeys.size();
            regionIndex = 0;
            locationIndex = 0;
            mapWidget = null;
            rebuildWidgets();
        }).bounds(rightX, ry, NAV_ARROW_W, NAV_ARROW_H).build());
    }

    private void buildRegionNav(int px, int py) {
        int mid    = px + PANEL_W / 2;
        int leftX  = mid - NAV_LABEL_W / 2 - 4 - NAV_ARROW_W;
        int rightX = mid + NAV_LABEL_W / 2 + 4;
        int ry     = py + NAV_REGION_Y;
        addRenderableWidget(Button.builder(Component.literal("<"), b -> {
            int sz = currentFactions().size();
            regionIndex = (regionIndex - 1 + sz) % sz;
            locationIndex = 0;
            rebuildWidgets();
        }).bounds(leftX, ry, NAV_ARROW_W, NAV_ARROW_H).build());
        addRenderableWidget(Button.builder(Component.literal(">"), b -> {
            regionIndex = (regionIndex + 1) % currentFactions().size();
            locationIndex = 0;
            rebuildWidgets();
        }).bounds(rightX, ry, NAV_ARROW_W, NAV_ARROW_H).build());
    }

    /**
     * Single location tab with left/right arrows, centered below the map.
     * Cycling through locations smoothly pans+zooms the mini-map to each waypoint.
     */
    private void buildLocationNav(int px, int py) {
        List<WaypointData> wps = currentWaypoints();
        int areaX = px + MAP_X_OFF;
        int areaW = MAP_W;
        int ly    = py + LOC_NAV_Y_OFF;

        int leftX  = areaX;
        int rightX = areaX + areaW - NAV_ARROW_W;

        // Left arrow
        addRenderableWidget(Button.builder(Component.literal("<"), b -> {
            int sz = currentWaypoints().size();
            if (sz > 0) {
                locationIndex = (locationIndex - 1 + sz) % sz;
                syncWaypointsToMap(); // smooth pan — no full rebuild needed
                rebuildWidgets();
            }
        }).bounds(leftX, ly, NAV_ARROW_W, LOC_NAV_H).build());

        // Right arrow
        addRenderableWidget(Button.builder(Component.literal(">"), b -> {
            int sz = currentWaypoints().size();
            if (sz > 0) {
                locationIndex = (locationIndex + 1) % sz;
                syncWaypointsToMap();
                rebuildWidgets();
            }
        }).bounds(rightX, ly, NAV_ARROW_W, LOC_NAV_H).build());
    }

    private void buildConfirmButton(int px, int py) {
        GotFactionData faction = currentFaction();
        boolean ok = faction != null && !faction.id().contains("coming_soon");
        Button btn = Button.builder(
                Component.literal(ok ? "Confirm Selection" : "Select a Faction"),
                b -> confirmSelection()
        ).bounds(px + (PANEL_W - CONFIRM_W) / 2,
                py + PANEL_H - CONFIRM_H - CONFIRM_B,
                CONFIRM_W, CONFIRM_H).build();
        btn.active = ok;
        addRenderableWidget(btn);
    }

    private void confirmSelection() {
        GotFactionData f = currentFaction();
        if (f == null || f.id().contains("coming_soon")) return;
        PacketDistributor.sendToServer(new SelectFactionPayload(f.id()));
        onClose();
    }

    // ── Rendering ─────────────────────────────────────────────────────────────

    @Override
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        int px = (width  - PANEL_W) / 2;
        int py = (height - PANEL_H) / 2;

        renderBackground(gfx, mouseX, mouseY, partialTick);
        renderPanelBorder(gfx, px, py);

        // Widgets (map, buttons) rendered by super
        super.render(gfx, mouseX, mouseY, partialTick);

        // Text always drawn last for crispness
        renderInfoPanel(gfx, px, py);

        // Title
        String title = "— Choose Your Allegiance —";
        gfx.drawString(font, title,
                (width - font.width(title)) / 2, py + 8, COL_TITLE, false);

        // Continent label
        String cont = GotFactions.CONTINENTS.getOrDefault(currentContinent(), "");
        int mid = px + PANEL_W / 2;
        gfx.drawString(font, cont,
                mid - font.width(cont) / 2, py + NAV_CONT_Y + 3, COL_TITLE, false);

        // Region label + page indicator
        GotFactionData f = currentFaction();
        String region = f != null ? f.displayName() : "";
        gfx.drawString(font, region,
                mid - font.width(region) / 2, py + NAV_REGION_Y + 3, 0xFFEEEEEE, false);
        List<GotFactionData> facs = currentFactions();
        if (!facs.isEmpty()) {
            String page = (regionIndex + 1) + " / " + facs.size();
            gfx.drawString(font, page,
                    mid - font.width(page) / 2,
                    py + NAV_REGION_Y + 3 + font.lineHeight + 1, 0xFF777766, false);
        }

        // Location label (drawn over the location nav area, between arrows)
        renderLocationLabel(gfx, px, py);
    }

    private void renderLocationLabel(GuiGraphics gfx, int px, int py) {
        String loc = currentLocationName();
        if (loc.isEmpty()) return;

        int areaX = px + MAP_X_OFF;
        int areaW = MAP_W;
        int ly    = py + LOC_NAV_Y_OFF;
        int labelW = areaW - NAV_ARROW_W * 2 - 4;
        int lx    = areaX + NAV_ARROW_W + 2;

        // Center the text in the gap between arrows
        gfx.drawString(font, loc,
                lx + (labelW - font.width(loc)) / 2,
                ly + (LOC_NAV_H - font.lineHeight) / 2 + 1,
                0xFFEEEEEE, false);
    }

    // ── Panel border ──────────────────────────────────────────────────────────

    private void renderPanelBorder(GuiGraphics gfx, int px, int py) {
        gfx.hLine(px,              px + PANEL_W - 1, py,               0xFF887733);
        gfx.hLine(px,              px + PANEL_W - 1, py + PANEL_H - 1, 0xFF887733);
        gfx.vLine(px,              py,               py + PANEL_H - 1, 0xFF887733);
        gfx.vLine(px + PANEL_W - 1, py,              py + PANEL_H - 1, 0xFF887733);
        gfx.hLine(px + 1,          px + PANEL_W - 2, py + 1,           0xFFCCAA44);
        gfx.hLine(px + 1,          px + PANEL_W - 2, py + PANEL_H - 2, 0xFFCCAA44);
        gfx.vLine(px + 1,          py + 1,           py + PANEL_H - 2, 0xFFCCAA44);
        gfx.vLine(px + PANEL_W - 2, py + 1,          py + PANEL_H - 2, 0xFFCCAA44);
    }

    // ── Info panel ────────────────────────────────────────────────────────────

    private void renderInfoPanel(GuiGraphics gfx, int px, int py) {
        GotFactionData f = currentFaction();

        int infoX = px + INFO_X_OFF;
        int infoY = py + INFO_Y_OFF;

        // Subtle tint + gold border
        gfx.fill(infoX, infoY, infoX + INFO_W, infoY + INFO_H, 0x44000000);
        gfx.hLine(infoX, infoX + INFO_W - 1, infoY,              0xFF887733);
        gfx.hLine(infoX, infoX + INFO_W - 1, infoY + INFO_H - 1, 0xFF887733);
        gfx.vLine(infoX, infoY, infoY + INFO_H - 1, 0xFF887733);
        gfx.vLine(infoX + INFO_W - 1, infoY, infoY + INFO_H - 1, 0xFF887733);

        if (f == null) return;

        int tx = infoX + 5;
        int lh = font.lineHeight + 2;
        int cy = infoY + 5;

        gfx.drawString(font, f.lordParamount(), tx, cy, COL_HOUSE_NAME, false); cy += lh + 1;
        gfx.drawString(font, "Lord Paramount of " + f.displayName(), tx, cy, COL_LABEL, false); cy += lh + 3;
        gfx.hLine(tx, infoX + INFO_W - 6, cy, 0xFF554422); cy += 4;
        gfx.drawString(font, "Seat:     " + f.seat(),     tx, cy, COL_LABEL, false); cy += lh + 1;
        gfx.drawString(font, "Fealty:   " + f.fealtyTo(), tx, cy, COL_LABEL, false); cy += lh + 5;

        for (net.minecraft.util.FormattedCharSequence line :
                font.split(Component.literal(f.lore()), INFO_W - 10)) {
            if (cy + lh > infoY + INFO_H - 4) break;
            gfx.drawString(font, line, tx, cy, COL_LORE, false);
            cy += lh;
        }
    }

    // ── Screen lifecycle ──────────────────────────────────────────────────────

    @Override
    protected void init() {
        Minecraft mc = Minecraft.getInstance();
        if (savedGuiScale < 0) {
            savedGuiScale = mc.options.guiScale().get();
            if (savedGuiScale < MIN_SCALE) {
                mc.options.guiScale().set(MIN_SCALE);
                mc.resizeDisplay();
                return;
            }
        }
        super.init();
        buildWidgets();
    }

    @Override
    public void removed() {
        super.removed();
        if (savedGuiScale >= 0) {
            Minecraft mc = Minecraft.getInstance();
            mc.options.guiScale().set(savedGuiScale);
            mc.resizeDisplay();
            savedGuiScale = -1;
        }
    }

    @Override public boolean shouldCloseOnEsc() { return false; }
    @Override public boolean isPauseScreen()    { return true;  }
}