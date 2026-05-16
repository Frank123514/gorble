package net.got.client.gui;

import net.got.faction.GotFactionData;
import net.got.faction.GotFactions;
import net.got.network.SelectFactionPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Full-screen faction selection UI shown once when a player first enters the Known World.
 *
 * <p>Layout:
 * <pre>
 *   ┌─────────────────────────────────────────────────────┐
 *   │            CHOOSE YOUR ALLEGIANCE                   │
 *   │  [Westeros]  [Essos]  [Sothoryos]   ← continent    │
 *   │                                                     │
 *   │  ┌──────────────┐  ┌──────────────────────────────┐ │
 *   │  │ > The North  │  │ House Stark                  │ │
 *   │  │   The Vale   │  │ Lord Paramount of the North  │ │
 *   │  │   ...        │  │ Seat: Winterfell             │ │
 *   │  │              │  │ Fealty: The Iron Throne      │ │
 *   │  │              │  │                              │ │
 *   │  │              │  │ <lore text wrapped>          │ │
 *   │  └──────────────┘  └──────────────────────────────┘ │
 *   │              [  Confirm Selection  ]                 │
 *   └─────────────────────────────────────────────────────┘
 * </pre>
 *
 * Uses only vanilla MC button widgets and {@link GuiGraphics} drawing calls.
 */
public final class FactionSelectionScreen extends Screen {

    // ── Panel dimensions ──────────────────────────────────────────────────────
    private static final int PANEL_W     = 390;
    private static final int PANEL_H     = 270;

    // ── Continent tab strip (inside panel, top) ───────────────────────────────
    private static final int CONT_TAB_H  = 20;
    private static final int CONT_TAB_W  = 90;
    private static final int CONT_GAP    = 4;
    private static final int CONT_ROW_Y  = 28;   // offset from panel top

    // ── Region list (left column) ─────────────────────────────────────────────
    private static final int LIST_X_OFF  = 8;     // offset from panel left
    private static final int LIST_Y_OFF  = CONT_ROW_Y + CONT_TAB_H + 10;
    private static final int LIST_BTN_W  = 120;
    private static final int LIST_BTN_H  = 18;
    private static final int LIST_GAP    = 3;

    // ── Info panel (right side) ───────────────────────────────────────────────
    private static final int INFO_X_OFF  = LIST_X_OFF + LIST_BTN_W + 8;
    private static final int INFO_Y_OFF  = LIST_Y_OFF;

    // ── Confirm button ────────────────────────────────────────────────────────
    private static final int CONFIRM_W   = 160;
    private static final int CONFIRM_H   = 20;
    private static final int CONFIRM_B   = 12;   // bottom margin inside panel

    // ── Colours ───────────────────────────────────────────────────────────────
    private static final int COL_TITLE       = 0xFFE8C060;
    private static final int COL_PANEL_BG    = 0xCC000000;
    private static final int COL_INFO_BG     = 0xAA111111;
    private static final int COL_INFO_BORDER = 0xFF555533;
    private static final int COL_HOUSE_NAME  = 0xFFFFD700;
    private static final int COL_LABEL       = 0xFFCCCCAA;
    private static final int COL_VALUE       = 0xFFEEEEEE;
    private static final int COL_LORE        = 0xFFAAAAAA;

    // ── State ─────────────────────────────────────────────────────────────────
    private String selectedContinent;
    private String selectedFactionId;

    /** Tracks which continent keys are available, in order. */
    private final List<String> continentKeys;

    public FactionSelectionScreen() {
        super(Component.literal("Choose Your Allegiance"));

        continentKeys = new ArrayList<>(GotFactions.CONTINENTS.keySet());

        // Default: first continent, first faction
        selectedContinent = continentKeys.isEmpty() ? "" : continentKeys.get(0);
        List<GotFactionData> first = GotFactions.forContinent(selectedContinent);
        selectedFactionId = first.isEmpty() ? "" : first.get(0).id();
    }

    // ── Widget construction ───────────────────────────────────────────────────

    @Override
    protected void init() {
        super.init();

        int panelX = (width  - PANEL_W) / 2;
        int panelY = (height - PANEL_H) / 2;

        buildContinentTabs(panelX, panelY);
        buildRegionList(panelX, panelY);
        buildConfirmButton(panelX, panelY);
    }

    private void buildContinentTabs(int px, int py) {
        int totalTabsW = continentKeys.size() * CONT_TAB_W
                + (continentKeys.size() - 1) * CONT_GAP;
        int startX = px + (PANEL_W - totalTabsW) / 2;
        int tabY   = py + CONT_ROW_Y;

        for (int i = 0; i < continentKeys.size(); i++) {
            final String key   = continentKeys.get(i);
            String label       = GotFactions.CONTINENTS.get(key);

            // Selected tab: gold + bold; unselected: plain label
            Component msg = key.equals(selectedContinent)
                    ? Component.literal("§e§l" + label)
                    : Component.literal(label);

            int tabX = startX + i * (CONT_TAB_W + CONT_GAP);
            addRenderableWidget(Button.builder(msg, btn -> selectContinent(key))
                    .bounds(tabX, tabY, CONT_TAB_W, CONT_TAB_H)
                    .build());
        }
    }

    private void buildRegionList(int px, int py) {
        List<GotFactionData> factions = GotFactions.forContinent(selectedContinent);
        int btnX = px + LIST_X_OFF;
        int btnY = py + LIST_Y_OFF;

        for (GotFactionData faction : factions) {
            final String fid = faction.id();
            boolean selected = fid.equals(selectedFactionId);

            // Selected region: gold arrow prefix; unselected: two-space indent
            String prefix = selected ? "§e> " : "  ";
            Component msg  = Component.literal(prefix + faction.displayName());

            addRenderableWidget(Button.builder(msg, btn -> selectFaction(fid))
                    .bounds(btnX, btnY, LIST_BTN_W, LIST_BTN_H)
                    .build());

            btnY += LIST_BTN_H + LIST_GAP;
        }
    }

    private void buildConfirmButton(int px, int py) {
        boolean canConfirm = !selectedFactionId.isEmpty();
        GotFactionData faction = GotFactions.get(selectedFactionId);
        String label = (faction != null && !faction.id().contains("coming_soon"))
                ? "Confirm Selection"
                : "Select a Faction";

        int confirmX = px + (PANEL_W - CONFIRM_W) / 2;
        int confirmY = py + PANEL_H - CONFIRM_H - CONFIRM_B;

        Button btn = Button.builder(Component.literal(label), b -> confirmSelection())
                .bounds(confirmX, confirmY, CONFIRM_W, CONFIRM_H)
                .build();

        btn.active = canConfirm
                && faction != null
                && !faction.id().contains("coming_soon");

        addRenderableWidget(btn);
    }

    // ── State transitions (rebuild widgets on change) ─────────────────────────

    private void selectContinent(String continentKey) {
        if (continentKey.equals(selectedContinent)) return;
        selectedContinent = continentKey;

        // Reset to first faction in this continent
        List<GotFactionData> factions = GotFactions.forContinent(continentKey);
        selectedFactionId = factions.isEmpty() ? "" : factions.get(0).id();

        rebuildWidgets();
    }

    private void selectFaction(String factionId) {
        if (factionId.equals(selectedFactionId)) return;
        selectedFactionId = factionId;
        rebuildWidgets();
    }

    private void confirmSelection() {
        GotFactionData faction = GotFactions.get(selectedFactionId);
        if (faction == null || faction.id().contains("coming_soon")) return;

        PacketDistributor.sendToServer(new SelectFactionPayload(faction.id()));
        onClose();
    }

    // ── Rendering ─────────────────────────────────────────────────────────────

    @Override
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        int panelX = (width  - PANEL_W) / 2;
        int panelY = (height - PANEL_H) / 2;

        renderBackground(gfx, mouseX, mouseY, partialTick);
        renderPanel(gfx, panelX, panelY);
        renderInfoPanel(gfx, panelX, panelY);

        // Draw title above continent tabs
        String title = "— Choose Your Allegiance —";
        int titleX = (width - font.width(title)) / 2;
        gfx.drawString(font, title, titleX, panelY + 10, COL_TITLE, true);

        // Render all widgets (buttons) on top
        super.render(gfx, mouseX, mouseY, partialTick);
    }

    /** Dark panel background with a thin border. */
    private void renderPanel(GuiGraphics gfx, int px, int py) {
        // Shadow
        gfx.fill(px + 4, py + 4, px + PANEL_W + 4, py + PANEL_H + 4, 0x55000000);
        // Background
        gfx.fill(px, py, px + PANEL_W, py + PANEL_H, COL_PANEL_BG);
        // Border (1px gold outline)
        gfx.hLine(px,              px + PANEL_W - 1, py,              0xFF887733);
        gfx.hLine(px,              px + PANEL_W - 1, py + PANEL_H - 1, 0xFF887733);
        gfx.vLine(px,              py,              py + PANEL_H - 1, 0xFF887733);
        gfx.vLine(px + PANEL_W - 1, py,              py + PANEL_H - 1, 0xFF887733);
    }

    /** Info panel to the right of the region list. */
    private void renderInfoPanel(GuiGraphics gfx, int px, int py) {
        GotFactionData faction = GotFactions.get(selectedFactionId);

        int infoX = px + INFO_X_OFF;
        int infoY = py + INFO_Y_OFF;
        int infoW = PANEL_W - INFO_X_OFF - 8;
        int infoH = PANEL_H - INFO_Y_OFF - CONFIRM_H - CONFIRM_B - 10;

        // Info background + border
        gfx.fill(infoX, infoY, infoX + infoW, infoY + infoH, COL_INFO_BG);
        gfx.hLine(infoX,          infoX + infoW - 1, infoY,           COL_INFO_BORDER);
        gfx.hLine(infoX,          infoX + infoW - 1, infoY + infoH - 1, COL_INFO_BORDER);
        gfx.vLine(infoX,          infoY,           infoY + infoH - 1, COL_INFO_BORDER);
        gfx.vLine(infoX + infoW - 1, infoY,         infoY + infoH - 1, COL_INFO_BORDER);

        if (faction == null) return;

        int textX = infoX + 6;
        int lineH = font.lineHeight + 2;
        int curY  = infoY + 6;

        // House name (gold, large feel via shadow)
        gfx.drawString(font, "§l" + faction.lordParamount(), textX, curY, COL_HOUSE_NAME, true);
        curY += lineH + 2;

        // Lord Paramount of <Region>
        gfx.drawString(font,
                "§oLord Paramount of " + faction.displayName(),
                textX, curY, COL_LABEL, false);
        curY += lineH + 4;

        // Divider
        gfx.hLine(textX, infoX + infoW - 8, curY, 0xFF554422);
        curY += 5;

        // Seat
        gfx.drawString(font, "Seat:     " + "§f" + faction.seat(),
                textX, curY, COL_LABEL, false);
        curY += lineH + 1;

        // Fealty
        gfx.drawString(font, "Fealty:   " + "§f" + faction.fealtyTo(),
                textX, curY, COL_LABEL, false);
        curY += lineH + 6;

        // Lore text — word-wrap to fit the info panel
        int wrapWidth = infoW - 12;
        List<net.minecraft.util.FormattedCharSequence> lines =
                font.split(Component.literal(faction.lore()), wrapWidth);

        for (net.minecraft.util.FormattedCharSequence line : lines) {
            if (curY + lineH > infoY + infoH - 6) break; // don't overflow
            gfx.drawString(font, line, textX, curY, COL_LORE, false);
            curY += lineH;
        }
    }

    // ── Screen properties ─────────────────────────────────────────────────────

    /** Prevent the player from simply pressing Escape to dismiss this screen. */
    @Override
    public boolean shouldCloseOnEsc() { return false; }

    @Override
    public boolean isPauseScreen() { return true; }
}