package net.got.client.gui;

import net.got.client.gui.widget.MapWidget;
import net.got.faction.FactionData;
import net.got.faction.Factions;
import net.got.faction.WaypointData;
import net.got.faction.WaypointRegistry;
import net.got.network.SelectFactionPayload;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class FactionSelectionScreen extends Screen {

    private static final Identifier MAP_TEXTURE =
            Identifier.fromNamespaceAndPath("got", "textures/gui/map/known_world.png");
    private static final int MAP_PIXEL_W = 4207;
    private static final int MAP_PIXEL_H = 3277;

    private static final int PANEL_W = 314;
    private static final int PANEL_H = 246;

    private static final int NAV_ARROW_W  = 14;
    private static final int NAV_ARROW_H  = 12;
    private static final int NAV_LABEL_W  = 94;
    private static final int NAV_CONT_Y   = 22;
    private static final int NAV_REGION_Y = NAV_CONT_Y + NAV_ARROW_H + 5;
    
    private static final int PAGE_ROW_H   = 9;

    private static final int MAP_X_OFF = 8;
    private static final int MAP_Y_OFF = NAV_REGION_Y + NAV_ARROW_H + PAGE_ROW_H + 6;
    private static final int MAP_W     = 122;
    private static final int MAP_H     = 88;

    private static final int LOC_NAV_H     = 12;
    private static final int LOC_NAV_Y_OFF = MAP_Y_OFF + MAP_H + 6;

    private static final int INFO_X_OFF = MAP_X_OFF + MAP_W + 8;
    private static final int INFO_Y_OFF = NAV_CONT_Y;
    private static final int INFO_W     = PANEL_W - INFO_X_OFF - 8;
    private static final int INFO_H     = PANEL_H - INFO_Y_OFF - 38;

    private static final int CONFIRM_W = 130;
    private static final int CONFIRM_H = 18;
    private static final int CONFIRM_B = 8;

    private static final int COL_TITLE   = 0xFFE8C060;
    private static final int COL_WORDS   = 0xFFDDCC88;
    private static final int COL_LABEL   = 0xFFCCCCAA;
    private static final int COL_LORE    = 0xFFAAAAAA;

    private final List<String> continentKeys;
    private int continentIndex = 0;
    private int regionIndex    = 0;
    private int locationIndex  = 0;

    private MapWidget mapWidget;

    private int loreScrollOffset = 0;
    
    private int loreMaxScroll = 0;

    private int lastLayoutWidth = -1;
    private int lastLayoutHeight = -1;

    public FactionSelectionScreen() {
        super(Component.literal("Choose Your Allegiance"));
        continentKeys = new ArrayList<>(Factions.CONTINENTS.keySet());
    }

    private String currentContinent() {
        return continentKeys.isEmpty() ? "" : continentKeys.get(continentIndex);
    }

    private List<FactionData> currentFactions() {
        return Factions.forContinent(currentContinent());
    }

    private FactionData currentFaction() {
        List<FactionData> f = currentFactions();
        if (f.isEmpty() || regionIndex >= f.size()) return null;
        return f.get(regionIndex);
    }

    private List<WaypointData> currentWaypoints() {
        FactionData f = currentFaction();
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

    private void buildWidgets() {
        int px = (width  - PANEL_W) / 2;
        int py = (height - PANEL_H) / 2;

        int mx = px + MAP_X_OFF;
        int my = py + MAP_Y_OFF;
        if (mapWidget == null) {
            mapWidget = new MapWidget(mx, my, MAP_W, MAP_H,
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
        FactionData faction = currentFaction();
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
        FactionData f = currentFaction();
        if (f == null || f.id().contains("coming_soon")) return;
        ClientPacketDistributor.sendToServer(new SelectFactionPayload(f.id(), currentLocationName()));
        net.minecraft.client.Minecraft.getInstance()
                .setScreen(new IntroScreen(IntroScreen.Mode.FINAL_ONLY));
    }

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

        gfx.fill(0, 0, width, height, 0xFF000000);

        super.render(gfx, mouseX, mouseY, partialTick);

        renderInfoPanel(gfx, px, py);

        String title = "— Choose Your Allegiance —";
        gfx.drawString(font, title,
                (width - font.width(title)) / 2, py + 8, COL_TITLE, false);

        String cont = Factions.CONTINENTS.getOrDefault(currentContinent(), "");
        int mapMid = px + MAP_X_OFF + MAP_W / 2;
        gfx.drawString(font, cont,
                mapMid - font.width(cont) / 2, py + NAV_CONT_Y + 3, COL_TITLE, false);

        FactionData f = currentFaction();
        String region = f != null ? f.displayName() : "";
        gfx.drawString(font, region,
                mapMid - font.width(region) / 2, py + NAV_REGION_Y + 3, 0xFFEEEEEE, false);
        List<FactionData> facs = currentFactions();
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

    private void renderInfoPanel(GuiGraphics gfx, int px, int py) {
        FactionData f = currentFaction();

        int infoX = px + INFO_X_OFF;
        int infoY = py + INFO_Y_OFF;

        gfx.fill(infoX, infoY, infoX + INFO_W, infoY + INFO_H, 0x44000000);

        if (f == null) return;

        int tx = infoX + 5;
        int lh = font.lineHeight + 2;
        int cy = infoY + 5;

        gfx.enableScissor(infoX, infoY, infoX + INFO_W, infoY + INFO_H);

        gfx.drawString(font, f.greatHouse(), tx, cy, f.primaryColour(), false); cy += lh + 1;

        gfx.drawString(font, "\"" + f.words() + "\"", tx, cy, COL_WORDS, false); cy += lh + 3;

        gfx.hLine(tx, infoX + INFO_W - 6, cy, 0xFF554422); cy += 4;

        cy = drawWrappedFact(gfx, "Seat:     " + f.seat(), tx, cy, INFO_W - 10, lh);
        cy = drawWrappedFact(gfx, "Fealty:   " + f.fealtyTo(), tx, cy, INFO_W - 10, lh);
        cy = drawWrappedFact(gfx, "Faith:    " + f.religion().displayName, tx, cy, INFO_W - 10, lh);
        cy += 4;

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

        if (loreMaxScroll > 0) {
            int barX = infoX + INFO_W - 4;
            int barTop = loreTop;
            int barBottom = loreBottom;
            int barH = barBottom - barTop;
            gfx.fill(barX, barTop, barX + 2, barBottom, 0x55FFFFFF);

            int thumbH = Math.max(6, barH * visibleLines / loreLines.size());
            int thumbY = barTop + (barH - thumbH) * loreScrollOffset / loreMaxScroll;
            gfx.fill(barX, thumbY, barX + 2, thumbY + thumbH, 0xFFCCAA44);

            if (loreScrollOffset < loreMaxScroll) {
                gfx.drawString(font, "v", infoX + INFO_W - 10, loreBottom - 6, 0xFFCCAA44, false);
            }
        }

        gfx.disableScissor();
    }

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

    @Override
    protected void init() {
        super.init();
        buildWidgets();
        lastLayoutWidth = width;
        lastLayoutHeight = height;
    }

    @Override public boolean shouldCloseOnEsc() { return false; }
    @Override public boolean isPauseScreen()    { return true;  }

    @Override
    public void renderBackground(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {}
}