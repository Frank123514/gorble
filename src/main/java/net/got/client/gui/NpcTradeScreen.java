package net.got.client.gui;

import net.got.entity.npc.data.GotNpcTrades;
import net.got.item.GotCoin;
import net.got.menu.NpcTradeMenu;
import net.got.network.ExecuteSellPayload;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

/**
 * Trade screen shown when the server opens {@link NpcTradeMenu}.
 *
 * <p><b>Buy tab</b> — a grid of items the NPC sells. Each cell shows the
 * item icon, its name, and its coin cost. Click a cell to purchase;
 * cells are greyed out if the player cannot afford them.
 *
 * <p><b>Sell tab</b> — a list of items the NPC will buy. Each row shows
 * the item and what the NPC pays. The player places the item to sell in
 * the sell slot at the bottom, selects the matching offer row, and clicks
 * the Sell button.
 */
public class NpcTradeScreen extends AbstractContainerScreen<NpcTradeMenu> {

    // ── Layout ────────────────────────────────────────────────────────────────
    private static final int GUI_W = 230;
    private static final int GUI_H = 240;

    // Tab bar
    private static final int TAB_Y  = 14;
    private static final int TAB_H  = 14;
    private static final int TAB_W  = 55;

    // Offer grid (buy)
    private static final int GRID_X    = 8;
    private static final int GRID_Y    = 34;
    private static final int CELL_W    = 105;
    private static final int CELL_H    = 22;
    private static final int COLS      = 2;
    private static final int VIS_ROWS  = 4;

    // Sell list
    private static final int LIST_X   = 8;
    private static final int LIST_Y   = 34;
    private static final int LIST_W   = 214;
    private static final int S_ROW_H  = 22;
    private static final int S_VIS    = 4;

    // Sell slot position (matches NpcTradeMenu slot 36 coordinates)
    private static final int SELL_SLOT_X = 80;
    private static final int SELL_SLOT_Y = 130;

    // Sell button
    private static final int SELL_BTN_X = 104;
    private static final int SELL_BTN_Y = 128;
    private static final int SELL_BTN_W = 60;
    private static final int SELL_BTN_H = 14;

    // Player inventory
    private static final int PINV_Y = 152;
    private static final int HBAR_Y = 210;

    // ── Colours ───────────────────────────────────────────────────────────────
    private static final int C_BG      = 0xFF_C6C6C6;
    private static final int C_PANEL   = 0xFF_8B8B8B;
    private static final int C_DARK    = 0xFF_373737;
    private static final int C_HOVER   = 0xFF_555555;
    private static final int C_AFFORD  = 0xFF_1A3A1A; // dark green bg for affordable
    private static final int C_CANT    = 0xFF_3A1A1A; // dark red bg for unaffordable
    private static final int C_SEL     = 0xFF_2A4A6A; // selected sell row
    private static final int C_TAB_ON  = 0xFF_A0C8FF;
    private static final int C_TAB_OFF = 0xFF_707070;
    private static final int C_TEXT    = 0xFF_222222;
    private static final int C_WHITE   = 0xFF_FFFFFF;
    private static final int C_GOLD    = 0xFF_FFD700;
    private static final int C_BTN_G   = 0xFF_3A7A3A;
    private static final int C_BTN_GH  = 0xFF_50AA50;
    private static final int C_BTN_D   = 0xFF_555555;

    // ── State ─────────────────────────────────────────────────────────────────
    private int  tab          = 0; // 0 = buy, 1 = sell
    private int  scrollBuy    = 0;
    private int  scrollSell   = 0;
    private int  selectedSell = -1;
    private int  hoveredBuy   = -1;
    private int  hoveredSell  = -1;

    public NpcTradeScreen(NpcTradeMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        this.imageWidth  = GUI_W;
        this.imageHeight = GUI_H;
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (imageWidth - font.width(title)) / 2;
        this.inventoryLabelY = GUI_H - 94;
    }

    // ── Render ────────────────────────────────────────────────────────────────

    @Override
    public void render(GuiGraphics g, int mx, int my, float delta) {
        super.render(g, mx, my, delta);
        renderTooltip(g, mx, my);
    }

    @Override
    protected void renderBg(GuiGraphics g, float delta, int mx, int my) {
        int x = leftPos, y = topPos;

        g.fill(x, y, x + GUI_W, y + GUI_H, C_BG);
        g.fill(x + 1, y + 1, x + GUI_W - 1, y + GUI_H - 1, C_PANEL);

        // ── Tabs ──────────────────────────────────────────────────────────────
        drawTab(g, x + 8,  y + TAB_Y, TAB_W, TAB_H, "Buy",  tab == 0, mx, my);
        drawTab(g, x + 67, y + TAB_Y, TAB_W, TAB_H, "Sell", tab == 1, mx, my);

        if (tab == 0) renderBuyTab(g, x, y, mx, my);
        else          renderSellTab(g, x, y, mx, my);

        // ── Player inventory ──────────────────────────────────────────────────
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 9; col++)
                slotBg(g, x + 8 + col * 18, y + PINV_Y + row * 18);
        for (int col = 0; col < 9; col++)
            slotBg(g, x + 8 + col * 18, y + HBAR_Y);
    }

    private void renderBuyTab(GuiGraphics g, int x, int y, int mx, int my) {
        List<GotNpcTrades.BuyOffer> offers = menu.getBuyOffers();
        int rows   = (int) Math.ceil((double) offers.size() / COLS);
        int visEnd = Math.min(scrollBuy + VIS_ROWS, rows);
        hoveredBuy = -1;

        for (int row = scrollBuy; row < visEnd; row++) {
            for (int col = 0; col < COLS; col++) {
                int idx = row * COLS + col;
                if (idx >= offers.size()) break;

                GotNpcTrades.BuyOffer offer = offers.get(idx);
                int cx = x + GRID_X + col * CELL_W;
                int cy = y + GRID_Y + (row - scrollBuy) * CELL_H;

                boolean canAfford = canAffordBuy(offer);
                boolean hov = hit(mx, my, cx, cy, CELL_W - 2, CELL_H - 2);
                if (hov) hoveredBuy = idx;

                int bg = hov ? C_HOVER : (canAfford ? C_AFFORD : C_CANT);
                g.fill(cx, cy, cx + CELL_W - 2, cy + CELL_H - 2, bg);

                // Item icon
                g.renderItem(offer.payStack(), cx + 2, cy + 3);

                // Item name (truncated)
                String name = offer.payItem().getName(offer.payStack()).getString();
                if (font.width(name) > 52) name = font.plainSubstrByWidth(name, 49) + "…";
                g.drawString(font, name, cx + 22, cy + 4,
                        canAfford ? C_WHITE : 0xFF_AA7777, true);

                // Coin cost
                String cost = offer.coinCost() + " " + capitalize(offer.coinType().id);
                g.renderItem(offer.coinStack(), cx + 22, cy + 12);
                // small coin icon already rendered; draw text after it
                g.drawString(font, cost, cx + 22, cy + 13,
                        canAfford ? C_GOLD : 0xFF_886644, true);
            }
        }

        scrollHint(g, x + GRID_X + COLS * CELL_W + 2, y + GRID_Y, rows, VIS_ROWS, scrollBuy);
    }

    private void renderSellTab(GuiGraphics g, int x, int y, int mx, int my) {
        List<GotNpcTrades.SellOffer> offers = menu.getSellOffers();
        int visEnd = Math.min(scrollSell + S_VIS, offers.size());
        hoveredSell = -1;

        // Offer list
        g.fill(x + LIST_X, y + LIST_Y,
               x + LIST_X + LIST_W, y + LIST_Y + S_VIS * S_ROW_H, C_DARK);

        for (int i = scrollSell; i < visEnd; i++) {
            GotNpcTrades.SellOffer offer = offers.get(i);
            int ry = y + LIST_Y + (i - scrollSell) * S_ROW_H;
            boolean hov  = hit(mx, my, x + LIST_X, ry, LIST_W, S_ROW_H);
            boolean sel  = selectedSell == i;
            if (hov) hoveredSell = i;

            int bg = sel ? C_SEL : (hov ? C_HOVER : C_DARK);
            g.fill(x + LIST_X, ry, x + LIST_X + LIST_W, ry + S_ROW_H, bg);

            // "Give X of [item] → receive Y [coin]"
            g.renderItem(offer.costStack(), x + LIST_X + 2, ry + 3);
            g.drawString(font, "×" + offer.costCount(),
                    x + LIST_X + 20, ry + 7, C_WHITE, true);
            g.drawString(font, "→",
                    x + LIST_X + 60, ry + 7, 0xFF_AAAAAA, false);
            g.renderItem(offer.coinStack(), x + LIST_X + 78, ry + 3);
            g.drawString(font, "×" + offer.coinPay() + " " + capitalize(offer.coinType().id),
                    x + LIST_X + 96, ry + 7,
                    sel ? C_GOLD : C_WHITE, true);
        }

        scrollHint(g, x + LIST_X + LIST_W + 2, y + LIST_Y,
                offers.size(), S_VIS, scrollSell);

        // Sell slot label + background
        g.drawString(font, "Place item here:", x + 8, y + SELL_SLOT_Y - 10, C_TEXT, false);
        slotBg(g, x + SELL_SLOT_X, y + SELL_SLOT_Y);

        // Sell button
        boolean hasItem  = !menu.getSellInputSlot().getItem(0).isEmpty();
        boolean hasOffer = selectedSell >= 0 && selectedSell < offers.size();
        boolean canSell  = hasItem && hasOffer && canExecuteSell();
        boolean btnHov   = hit(mx, my, x + SELL_BTN_X, y + SELL_BTN_Y, SELL_BTN_W, SELL_BTN_H);
        int btnCol = canSell ? (btnHov ? C_BTN_GH : C_BTN_G) : C_BTN_D;
        g.fill(x + SELL_BTN_X, y + SELL_BTN_Y,
               x + SELL_BTN_X + SELL_BTN_W, y + SELL_BTN_Y + SELL_BTN_H, btnCol);
        g.drawString(font, "Sell",
                x + SELL_BTN_X + (SELL_BTN_W - font.width("Sell")) / 2,
                y + SELL_BTN_Y + (SELL_BTN_H - 8) / 2, C_WHITE, true);

        // Hint if nothing selected
        if (!hasOffer) {
            g.drawString(font, "← Select an offer",
                    x + SELL_BTN_X + SELL_BTN_W + 4, y + SELL_BTN_Y + 3,
                    0xFF_AAAAAA, false);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics g, int mx, int my) {
        String occ  = capitalize(menu.getOccupation().id);
        String hdr  = menu.getNpcName().isEmpty() ? occ
                : menu.getNpcName() + "  —  " + occ;
        g.drawString(font, hdr, 8, 4, C_TEXT, false);
        g.drawString(font, playerInventoryTitle, 8, imageHeight - 86, C_TEXT, false);
    }

    // ── Input ─────────────────────────────────────────────────────────────────

    @Override
    public boolean mouseClicked(double mx, double my, int btn) {
        if (btn != 0) return super.mouseClicked(mx, my, btn);
        int x = leftPos, y = topPos;

        // Tab clicks
        if (hit(mx, my, x + 8,  y + TAB_Y, TAB_W, TAB_H)) { tab = 0; scrollBuy  = 0; return true; }
        if (hit(mx, my, x + 67, y + TAB_Y, TAB_W, TAB_H)) { tab = 1; scrollSell = 0; return true; }

        if (tab == 0) {
            // Buy grid click
            List<GotNpcTrades.BuyOffer> offers = menu.getBuyOffers();
            int rows = (int) Math.ceil((double) offers.size() / COLS);
            for (int row = scrollBuy; row < Math.min(scrollBuy + VIS_ROWS, rows); row++) {
                for (int col = 0; col < COLS; col++) {
                    int idx = row * COLS + col;
                    if (idx >= offers.size()) break;
                    int cx = x + GRID_X + col * CELL_W;
                    int cy = y + GRID_Y + (row - scrollBuy) * CELL_H;
                    if (hit(mx, my, cx, cy, CELL_W - 2, CELL_H - 2)) {
                        executeBuy(idx);
                        return true;
                    }
                }
            }
        } else {
            // Sell list — select row
            List<GotNpcTrades.SellOffer> offers = menu.getSellOffers();
            for (int i = scrollSell; i < Math.min(scrollSell + S_VIS, offers.size()); i++) {
                int ry = y + LIST_Y + (i - scrollSell) * S_ROW_H;
                if (hit(mx, my, x + LIST_X, ry, LIST_W, S_ROW_H)) {
                    selectedSell = (selectedSell == i) ? -1 : i;
                    return true;
                }
            }

            // Sell button
            if (hit(mx, my, x + SELL_BTN_X, y + SELL_BTN_Y, SELL_BTN_W, SELL_BTN_H)) {
                if (selectedSell >= 0 && canExecuteSell()) {
                    PacketDistributor.sendToServer(new ExecuteSellPayload(selectedSell));
                    selectedSell = -1;
                }
                return true;
            }
        }

        return super.mouseClicked(mx, my, btn);
    }

    @Override
    public boolean mouseScrolled(double mx, double my, double dx, double dy) {
        if (tab == 0) {
            int rows = (int) Math.ceil((double) menu.getBuyOffers().size() / COLS);
            int max  = Math.max(0, rows - VIS_ROWS);
            scrollBuy = (dy < 0) ? Math.min(scrollBuy + 1, max) : Math.max(scrollBuy - 1, 0);
        } else {
            int max = Math.max(0, menu.getSellOffers().size() - S_VIS);
            scrollSell = (dy < 0) ? Math.min(scrollSell + 1, max) : Math.max(scrollSell - 1, 0);
        }
        return true;
    }

    // ── Trade logic ───────────────────────────────────────────────────────────

    private boolean canAffordBuy(GotNpcTrades.BuyOffer offer) {
        assert minecraft != null;
        if (minecraft.player == null) return false;
        return offer.coinType().countIn(minecraft.player.getInventory()) >= offer.coinCost();
    }

    private boolean canExecuteSell() {
        assert minecraft != null;
        if (minecraft.player == null || selectedSell < 0) return false;
        List<GotNpcTrades.SellOffer> offers = menu.getSellOffers();
        if (selectedSell >= offers.size()) return false;
        GotNpcTrades.SellOffer offer = offers.get(selectedSell);
        ItemStack slot = menu.getSellInputSlot().getItem(0);
        return !slot.isEmpty() && slot.is(offer.costItem())
                && slot.getCount() >= offer.costCount();
    }

    /**
     * Client-side buy execution. Deducts coin cost and grants pay item.
     * Works correctly in singleplayer / as server host.
     */
    private void executeBuy(int idx) {
        List<GotNpcTrades.BuyOffer> offers = menu.getBuyOffers();
        if (idx < 0 || idx >= offers.size()) return;
        assert minecraft != null;
        if (minecraft.player == null) return;

        GotNpcTrades.BuyOffer offer = offers.get(idx);
        if (!canAffordBuy(offer)) return;

        offer.coinType().removeFrom(minecraft.player.getInventory(), offer.coinCost());
        minecraft.player.getInventory().add(offer.payStack());
        minecraft.player.getInventory().setChanged();
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private void drawTab(GuiGraphics g, int x, int y, int w, int h,
                         String label, boolean active, int mx, int my) {
        boolean hov = !active && hit(mx, my, x, y, w, h);
        g.fill(x, y, x + w, y + h, active ? C_TAB_ON : (hov ? C_HOVER : C_TAB_OFF));
        g.drawString(font, label,
                x + (w - font.width(label)) / 2, y + (h - 8) / 2,
                active ? C_TEXT : C_WHITE, false);
    }

    private void slotBg(GuiGraphics g, int x, int y) {
        g.fill(x - 1, y - 1, x + 17, y + 17, C_DARK);
        g.fill(x, y, x + 16, y + 16, 0xFF_8B8B8B);
    }

    private void scrollHint(GuiGraphics g, int x, int y, int total, int vis, int off) {
        if (total > vis) {
            if (off > 0)         g.drawString(font, "▲", x, y + 2,     C_WHITE, false);
            if (off < total-vis) g.drawString(font, "▼", x, y + vis*S_ROW_H - 10, C_WHITE, false);
        }
    }

    private static boolean hit(double mx, double my, int x, int y, int w, int h) {
        return mx >= x && mx < x + w && my >= y && my < y + h;
    }

    private static String capitalize(String s) {
        return s == null || s.isEmpty() ? s
                : Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
