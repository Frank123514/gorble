package net.got.client.gui;

import net.got.client.gui.widget.GotMapWidget;
import net.got.faction.GotFactionData;
import net.got.faction.GotFactions;
import net.got.faction.WaypointData;
import net.got.faction.WaypointRegistry;
import net.got.network.SelectFactionPayload;
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
 *  │  │  zoomable map   │   │ "Winter is Coming"               │   │
 *  │  │  widget         │   │ Seat:     Winterfell             │   │
 *  │  └─────────────────┘   │ Fealty:   The Iron Throne        │   │
 *  │  [<] Winterfell [>]    │ Faith:    The Old Gods           │   │
 *  │                        │ Economy:  Martial                │   │
 *  │                        │ Military: Heavy Infantry         │   │
 *  │                        │ <lore>                           │   │
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

    // ── Layout (fixed pixel sizes, Middle-earth style) ──────────────────────────
    // No per-GUI-scale preset table and no runtime clamping/rescaling math.
    // Just hand-picked constant sizes in GUI-scaled pixels. Minecraft's GUI
    // scale option already scales the coordinate space uniformly (this
    // screen's `width`/`height` fields are the *scaled* window dimensions),
    // so a fixed-size panel centered against width/height renders correctly
    // at every GUI scale on its own - same approach used by the Middle-earth
    // mod's FactionSelectionScreen.
    private static final int PANEL_W = 314;
    private static final int PANEL_H = 246;

    // ── Nav rows (stacked above the map, aligned to the map's column) ──────────
    private static final int NAV_ARROW_W  = 14;
    private static final int NAV_ARROW_H  = 12;
    private static final int NAV_LABEL_W  = 94;
    private static final int NAV_CONT_Y   = 22;
    private static final int NAV_REGION_Y = NAV_CONT_Y + NAV_ARROW_H + 5;
    // Space reserved below the region label for the "x / y" page indicator line.
    private static final int PAGE_ROW_H   = 9;

    // ── Map canvas (left column, square-ish) ───────────────────────────────────
    // Sits below the continent/region nav instead of overlapping it, so the
    // map's own overlay text (e.g. "Zoom: 8.0x") never collides with the labels.
    private static final int MAP_X_OFF = 8;
    private static final int MAP_Y_OFF = NAV_REGION_Y + NAV_ARROW_H + PAGE_ROW_H + 6;
    private static final int MAP_W     = 122;
    private static final int MAP_H     = 88;

    // ── Location nav (below map canvas) ───────────────────────────────────────
    private static final int LOC_NAV_H     = 12;
    private static final int LOC_NAV_Y_OFF = MAP_Y_OFF + MAP_H + 6;

    // ── Info panel (right column) ───────────────────────────────────────────────
    // Starts right under the title (not pinned to the map's Y offset), so it
    // stays tall even though the nav+map column above it is taller now.
    private static final int INFO_X_OFF = MAP_X_OFF + MAP_W + 8;
    private static final int INFO_Y_OFF = NAV_CONT_Y;
    private static final int INFO_W     = PANEL_W - INFO_X_OFF - 8;
    private static final int INFO_H     = PANEL_H - INFO_Y_OFF - 38;

    // ── Confirm button ────────────────────────────────────────────────────────
    private static final int CONFIRM_W = 130;
    private static final int CONFIRM_H = 18;
    private static final int CONFIRM_B = 8;

    // ── Default colours (overridden per-faction with primaryColour) ───────────
    private static final int COL_TITLE   = 0xFFE8C060;
    private static final int COL_WORDS   = 0xFFDDCC88; // house words / motto
    private static final int COL_LABEL   = 0xFFCCCCAA;
    private static final int COL_LORE    = 0xFFAAAAAA;
    private static final int COL_BORDER  = 0xFF887733;

    // ── State ─────────────────────────────────────────────────────────────────
    private final List<String> continentKeys;
    private int continentIndex = 0;
    private int regionIndex    = 0;
    private int locationIndex  = 0;

    /** The live scrollable/zoomable map widget. Persisted across rebuildWidgets. */
    private GotMapWidget mapWidget;

    /** Current scroll offset (in lines) for the lore text in the info panel.
     *  Reset to 0 whenever the selected faction changes. */
    private int loreScrollOffset = 0;
    /** Cached max scroll for the currently-rendered lore, updated each frame
     *  so mouseScrolled can clamp against it without recomputing wrapping. */
    private int loreMaxScroll = 0;

    // Width/height the cached layout was last computed for, so a window resize
    // (which can change the effective GUI scale) triggers a relayout.
    private int lastLayoutWidth = -1;
    private int lastLayoutHeight = -1;

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

    private String currentLocationName() {
        WaypointData wp = currentWaypoint();
        return wp != null ? wp.name() : "";
    }

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
        int mapMid = px + MAP_X_OFF + MAP_W / 2;
        int leftX  = mapMid - NAV_LABEL_W / 2 - 4 - NAV_ARROW_W;
        int rightX = mapMid + NAV_LABEL_W / 2 + 4;
        int ry     = py + NAV_CONT_Y;
        addRenderableWidget(Button.builder(Component.literal("<"), b -> {
            continentIndex = (continentIndex - 1 + continentKeys.size()) % continentKeys.size();
            regionIndex = 0;
            locationIndex = 0;
            loreScrollOffset = 0;
            mapWidget = null;
            rebuildWidgets();
        }).bounds(leftX, ry, NAV_ARROW_W, NAV_ARROW_H).build());
        addRenderableWidget(Button.builder(Component.literal(">"), b -> {
            continentIndex = (continentIndex + 1) % continentKeys.size();
            regionIndex = 0;
            locationIndex = 0;
            loreScrollOffset = 0;
            mapWidget = null;
            rebuildWidgets();
        }).bounds(rightX, ry, NAV_ARROW_W, NAV_ARROW_H).build());
    }

    private void buildRegionNav(int px, int py) {
        int mapMid = px + MAP_X_OFF + MAP_W / 2;
        int leftX  = mapMid - NAV_LABEL_W / 2 - 4 - NAV_ARROW_W;
        int rightX = mapMid + NAV_LABEL_W / 2 + 4;
        int ry     = py + NAV_REGION_Y;
        addRenderableWidget(Button.builder(Component.literal("<"), b -> {
            int sz = currentFactions().size();
            regionIndex = (regionIndex - 1 + sz) % sz;
            locationIndex = 0;
            loreScrollOffset = 0;
            rebuildWidgets();
        }).bounds(leftX, ry, NAV_ARROW_W, NAV_ARROW_H).build());
        addRenderableWidget(Button.builder(Component.literal(">"), b -> {
            regionIndex = (regionIndex + 1) % currentFactions().size();
            locationIndex = 0;
            loreScrollOffset = 0;
            rebuildWidgets();
        }).bounds(rightX, ry, NAV_ARROW_W, NAV_ARROW_H).build());
    }

    private void buildLocationNav(int px, int py) {
        int areaX = px + MAP_X_OFF;
        int areaW = MAP_W;
        int ly    = py + LOC_NAV_Y_OFF;

        int leftX  = areaX;
        int rightX = areaX + areaW - NAV_ARROW_W;

        addRenderableWidget(Button.builder(Component.literal("<"), b -> {
            int sz = currentWaypoints().size();
            if (sz > 0) {
                locationIndex = (locationIndex - 1 + sz) % sz;
                syncWaypointsToMap();
                rebuildWidgets();
            }
        }).bounds(leftX, ly, NAV_ARROW_W, LOC_NAV_H).build());

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
        if (width != lastLayoutWidth || height != lastLayoutHeight) {
            mapWidget = null;
            rebuildWidgets();
            lastLayoutWidth = width;
            lastLayoutHeight = height;
        }

        int px = (width  - PANEL_W) / 2;
        int py = (height - PANEL_H) / 2;

        renderBackground(gfx, mouseX, mouseY, partialTick);
        renderPanelBorder(gfx, px, py);

        super.render(gfx, mouseX, mouseY, partialTick);

        renderInfoPanel(gfx, px, py);

        // Title
        String title = "— Choose Your Allegiance —";
        gfx.drawString(font, title,
                (width - font.width(title)) / 2, py + 8, COL_TITLE, false);

        // Continent label (stacked above the map, in its column)
        String cont = GotFactions.CONTINENTS.getOrDefault(currentContinent(), "");
        int mapMid = px + MAP_X_OFF + MAP_W / 2;
        gfx.drawString(font, cont,
                mapMid - font.width(cont) / 2, py + NAV_CONT_Y + 3, COL_TITLE, false);

        // Region label + page indicator (also stacked above the map)
        GotFactionData f = currentFaction();
        String region = f != null ? f.displayName() : "";
        gfx.drawString(font, region,
                mapMid - font.width(region) / 2, py + NAV_REGION_Y + 3, 0xFFEEEEEE, false);
        List<GotFactionData> facs = currentFactions();
        if (!facs.isEmpty()) {
            String page = (regionIndex + 1) + " / " + facs.size();
            gfx.drawString(font, page,
                    mapMid - font.width(page) / 2,
                    py + NAV_REGION_Y + 3 + font.lineHeight + 1, 0xFF777766, false);
        }

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

        gfx.drawString(font, loc,
                lx + (labelW - font.width(loc)) / 2,
                ly + (LOC_NAV_H - font.lineHeight) / 2 + 1,
                0xFFEEEEEE, false);
    }

    // ── Panel border ──────────────────────────────────────────────────────────

    private void renderPanelBorder(GuiGraphics gfx, int px, int py) {
        gfx.hLine(px,               px + PANEL_W - 1, py,               COL_BORDER);
        gfx.hLine(px,               px + PANEL_W - 1, py + PANEL_H - 1, COL_BORDER);
        gfx.vLine(px,               py,               py + PANEL_H - 1, COL_BORDER);
        gfx.vLine(px + PANEL_W - 1, py,               py + PANEL_H - 1, COL_BORDER);
        gfx.hLine(px + 1,           px + PANEL_W - 2, py + 1,           0xFFCCAA44);
        gfx.hLine(px + 1,           px + PANEL_W - 2, py + PANEL_H - 2, 0xFFCCAA44);
        gfx.vLine(px + 1,           py + 1,           py + PANEL_H - 2, 0xFFCCAA44);
        gfx.vLine(px + PANEL_W - 2, py + 1,           py + PANEL_H - 2, 0xFFCCAA44);
    }

    // ── Info panel ────────────────────────────────────────────────────────────

    private void renderInfoPanel(GuiGraphics gfx, int px, int py) {
        GotFactionData f = currentFaction();

        int infoX = px + INFO_X_OFF;
        int infoY = py + INFO_Y_OFF;

        // Subtle tint + border (use faction's primary colour if available)
        int borderCol = (f != null) ? f.primaryColour() : COL_BORDER;
        gfx.fill(infoX, infoY, infoX + INFO_W, infoY + INFO_H, 0x44000000);
        gfx.hLine(infoX, infoX + INFO_W - 1, infoY,              borderCol);
        gfx.hLine(infoX, infoX + INFO_W - 1, infoY + INFO_H - 1, borderCol);
        gfx.vLine(infoX, infoY, infoY + INFO_H - 1,               borderCol);
        gfx.vLine(infoX + INFO_W - 1, infoY, infoY + INFO_H - 1,  borderCol);

        if (f == null) return;

        int tx = infoX + 5;
        int lh = font.lineHeight + 2;
        int cy = infoY + 5;

        gfx.enableScissor(infoX, infoY, infoX + INFO_W, infoY + INFO_H);

        // House name (tinted with faction's primary colour)
        gfx.drawString(font, f.greatHouse(), tx, cy, f.primaryColour(), false); cy += lh + 1;

        // House words in italics-esque colour
        gfx.drawString(font, "\"" + f.words() + "\"", tx, cy, COL_WORDS, false); cy += lh + 3;

        // Separator
        gfx.hLine(tx, infoX + INFO_W - 6, cy, 0xFF554422); cy += 4;

        // Key facts (word-wrapped so long values, e.g. faith/fealty names, are
        // never sliced off by the info panel's edge - they wrap onto a second
        // line instead, same spirit as the Middle-earth screen's lore block).
        cy = drawWrappedFact(gfx, "Seat:     " + f.seat(), tx, cy, INFO_W - 10, lh);
        cy = drawWrappedFact(gfx, "Fealty:   " + f.fealtyTo(), tx, cy, INFO_W - 10, lh);
        cy = drawWrappedFact(gfx, "Faith:    " + f.religion().displayName, tx, cy, INFO_W - 10, lh);
        cy += 4;

        // Lore text (word-wrapped, scrollable). Wrap the full lore first so we
        // know the total line count, then only draw the slice that fits in the
        // remaining vertical space, offset by loreScrollOffset lines.
        List<net.minecraft.util.FormattedCharSequence> loreLines =
                font.split(Component.literal(f.lore()), INFO_W - 10);

        int loreTop = cy;
        int loreBottom = infoY + INFO_H - 4;
        int visibleLines = Math.max(1, (loreBottom - loreTop) / lh);
        loreMaxScroll = Math.max(0, loreLines.size() - visibleLines);
        loreScrollOffset = Math.min(loreScrollOffset, loreMaxScroll);

        for (int i = loreScrollOffset; i < loreLines.size() && cy + lh <= loreBottom + 1; i++) {
            gfx.drawString(font, loreLines.get(i), tx, cy, COL_LORE, false);
            cy += lh;
        }

        // Scrollbar: a thin track + thumb along the panel's right inner edge,
        // only drawn when there's actually more lore than fits.
        if (loreMaxScroll > 0) {
            int barX = infoX + INFO_W - 4;
            int barTop = loreTop;
            int barBottom = loreBottom;
            int barH = barBottom - barTop;
            gfx.fill(barX, barTop, barX + 2, barBottom, 0x55FFFFFF);

            int thumbH = Math.max(6, barH * visibleLines / loreLines.size());
            int thumbY = barTop + (barH - thumbH) * loreScrollOffset / loreMaxScroll;
            gfx.fill(barX, thumbY, barX + 2, thumbY + thumbH, 0xFFCCAA44);

            // Small hint arrows so it's obvious the text scrolls, even before
            // the player has touched the scroll wheel.
            if (loreScrollOffset < loreMaxScroll) {
                gfx.drawString(font, "v", infoX + INFO_W - 10, loreBottom - 6, 0xFFCCAA44, false);
            }
        }

        gfx.disableScissor();
    }

    /** Handles mouse-wheel scrolling of the lore text when the cursor is over
     *  the info panel. One notch scrolls a single line, so even short lore
     *  blocks with only a couple of overflow lines get smooth, granular
     *  scrolling instead of jumping straight to the bottom. */
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        int px = (width  - PANEL_W) / 2;
        int py = (height - PANEL_H) / 2;
        int infoX = px + INFO_X_OFF;
        int infoY = py + INFO_Y_OFF;

        if (mouseX >= infoX && mouseX < infoX + INFO_W
                && mouseY >= infoY && mouseY < infoY + INFO_H) {
            int delta = (int) Math.signum(scrollY);
            loreScrollOffset = Math.max(0, Math.min(loreMaxScroll, loreScrollOffset - delta));
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    /** Draws a "Label: value" fact line, wrapping onto extra lines if it's too
     *  wide for the info panel instead of letting the scissor box slice it off.
     *  Returns the cursor Y position to continue drawing from. */
    private int drawWrappedFact(GuiGraphics gfx, String text, int tx, int cy, int maxWidth, int lh) {
        if (font.width(text) <= maxWidth) {
            gfx.drawString(font, text, tx, cy, COL_LABEL, false);
            return cy + lh + 1;
        }
        for (net.minecraft.util.FormattedCharSequence line :
                font.split(Component.literal(text), maxWidth)) {
            gfx.drawString(font, line, tx, cy, COL_LABEL, false);
            cy += lh;
        }
        return cy + 1;
    }

    // ── Screen lifecycle ──────────────────────────────────────────────────────

    @Override
    protected void init() {
        super.init();
        buildWidgets();
        lastLayoutWidth = width;
        lastLayoutHeight = height;
    }

    @Override public boolean shouldCloseOnEsc() { return false; }
    @Override public boolean isPauseScreen()    { return true;  }
}