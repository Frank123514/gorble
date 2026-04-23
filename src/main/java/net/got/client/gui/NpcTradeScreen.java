package net.got.client.gui;

import net.got.entity.npc.data.GotNpcTrades;
import net.got.item.GotCoin;
import net.got.menu.NpcTradeMenu;
import net.got.network.ExecuteSellPayload;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

/**
 * Trade screen for employed NPCs.
 *
 * Buy tab  (matches image 2):
 *   "You can buy" label
 *   ┌──┬──┬──┬──┬──┬──┬──┬──┐   8 item cells per row
 *   │  │  │  │  │  │  │  │  │   16×16 item icon + count badge
 *   └──┴──┴──┴──┴──┴──┴──┴──┘
 *    15  19  84  …                coin cost below each cell
 *
 * Sell tab (matches image 3):
 *   "You can sell" label
 *   row of what the NPC accepts + payout
 *   "You want to sell"
 *   [ sell slot ]   [ Sell ]
 *   player inventory
 */
public class NpcTradeScreen extends AbstractContainerScreen<NpcTradeMenu> {

    // ── Screen dimensions ─────────────────────────────────────────────────────
    private static final int W  = 176;
    private static final int H  = 220;

    // ── Offer grid (buy + sell displays) ─────────────────────────────────────
    // 8 cells × 18 px = 144 px; left margin 8 px → right edge 152 px
    private static final int CELLS_PER_ROW = 8;
    private static final int CELL          = 18; // slot stride (icon 16×16 + 2 gap)
    private static final int OFFERS_X      = 8;
    private static final int OFFERS_Y      = 30; // top of offer row (icon top)
    private static final int PRICE_DY      = 18; // price text is 18 px below icon top

    // ── Sell section ──────────────────────────────────────────────────────────
    private static final int SELL_LBL_Y    = 58;  // "You want to sell" label
    private static final int SELL_BTN_X    = 30;  // relative to leftPos
    private static final int SELL_BTN_Y    = 86;  // relative to topPos (just above sell slot)
    private static final int SELL_BTN_W    = 60;
    private static final int SELL_BTN_H    = 16;

    // ── Tab bar ───────────────────────────────────────────────────────────────
    private static final int TAB_Y  = 6;
    private static final int TAB_W  = 55;
    private static final int TAB_H  = 14;

    // ── Colours ───────────────────────────────────────────────────────────────
    private static final int C_BG       = 0xFF_C6C6C6;
    private static final int C_SHADOW   = 0xFF_555555;
    private static final int C_HILITE   = 0xFF_FFFFFF;
    private static final int C_BORDER   = 0xFF_000000;
    private static final int C_SLOT     = 0xFF_8B8B8B;
    private static final int C_SLOT_BDR = 0xFF_373737;
    private static final int C_SELECTED = 0xFF_6699CC;
    private static final int C_HOVER    = 0xFF_A0B8D0;
    private static final int C_AFFORD   = 0xFF_2A5A2A;   // green tint: can afford
    private static final int C_CANT     = 0xFF_5A2A2A;   // red tint: can't afford
    private static final int C_TEXT     = 0xFF_404040;
    private static final int C_WHITE    = 0xFF_FFFFFF;
    private static final int C_GOLD     = 0xFF_FFFF55;
    private static final int C_LABEL    = 0xFF_404040;
    private static final int C_TAB_ON   = 0xFF_C6C6C6;   // matches panel bg — "selected"
    private static final int C_TAB_OFF  = 0xFF_8B8B8B;

    // ── State ─────────────────────────────────────────────────────────────────
    private int  tab          = 0;   // 0 = buy, 1 = sell
    private int  scrollBuy    = 0;
    private int  scrollSell   = 0;
    private int  selectedSell = -1;  // which sell offer row is selected

    private Button sellButton;

    public NpcTradeScreen(NpcTradeMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        imageWidth  = W;
        imageHeight = H;
    }

    @Override
    protected void init() {
        super.init();
        titleLabelX    = (W - font.width(buildTitle())) / 2;
        titleLabelY    = -10; // negative = hidden (we draw our own header)
        inventoryLabelX = 8;
        inventoryLabelY = H - 86;  // 220 - 86 = 134 → matches slot y 138

        // Sell button — only active in sell tab
        sellButton = Button.builder(Component.literal("Sell"), btn -> doSell())
                .bounds(leftPos + SELL_BTN_X, topPos + SELL_BTN_Y, SELL_BTN_W, SELL_BTN_H)
                .build();
        addRenderableWidget(sellButton);
    }

    // ── Rendering ─────────────────────────────────────────────────────────────

    @Override
    public void render(GuiGraphics g, int mx, int my, float delta) {
        // Update sell button visibility & enabled state
        sellButton.visible = (tab == 1);
        sellButton.active  = (tab == 1) && canExecuteSell();
        // Reposition in case window was resized
        sellButton.setX(leftPos + SELL_BTN_X);
        sellButton.setY(topPos  + SELL_BTN_Y);

        super.render(g, mx, my, delta);
        renderTooltip(g, mx, my);
    }

    @Override
    protected void renderBg(GuiGraphics g, float delta, int mx, int my) {
        int x = leftPos, y = topPos;

        // ── Vanilla-style panel background ────────────────────────────────────
        g.fill(x - 1, y - 1, x + W + 1, y + H + 1, C_BORDER);
        g.fill(x, y, x + W, y + H, C_BG);
        // Top-left highlight
        g.fill(x, y, x + W, y + 1, C_HILITE);
        g.fill(x, y, x + 1, y + H, C_HILITE);
        // Bottom-right shadow
        g.fill(x, y + H - 1, x + W, y + H, C_SHADOW);
        g.fill(x + W - 1, y, x + W, y + H, C_SHADOW);

        // ── Header: NPC name + occupation ─────────────────────────────────────
        String hdr = buildTitle();
        g.drawCenteredString(font, hdr, x + W / 2, y + 5, C_TEXT);

        // ── Tab selectors ─────────────────────────────────────────────────────
        drawTabSelector(g, x + 8,       y + TAB_Y, TAB_W, TAB_H, "Buy",  tab == 0, mx, my);
        drawTabSelector(g, x + 8 + TAB_W + 4, y + TAB_Y, TAB_W, TAB_H, "Sell", tab == 1, mx, my);

        // ── Tab content ───────────────────────────────────────────────────────
        if (tab == 0) renderBuyTab(g, x, y, mx, my);
        else          renderSellTab(g, x, y, mx, my);

        // ── Player inventory area (identical for both tabs) ───────────────────
        // Divider line above inventory label
        g.fill(x + 7, y + H - 90, x + W - 7, y + H - 89, C_SHADOW);
    }

    @Override
    protected void renderLabels(GuiGraphics g, int mx, int my) {
        // Draw "Inventory" label only (title is drawn in renderBg)
        g.drawString(font, playerInventoryTitle, inventoryLabelX, inventoryLabelY, C_LABEL, false);
    }

    // ── Buy tab ───────────────────────────────────────────────────────────────

    private void renderBuyTab(GuiGraphics g, int x, int y, int mx, int my) {
        List<GotNpcTrades.BuyOffer> offers = menu.getBuyOffers();

        g.drawString(font, "You can buy", x + OFFERS_X, y + OFFERS_Y - 10, C_LABEL, false);

        int rows    = (int) Math.ceil((double) offers.size() / CELLS_PER_ROW);
        int visRows = Math.min(2, rows); // show up to 2 rows

        for (int row = scrollBuy; row < scrollBuy + visRows && row < rows; row++) {
            for (int col = 0; col < CELLS_PER_ROW; col++) {
                int idx = row * CELLS_PER_ROW + col;
                if (idx >= offers.size()) break;

                GotNpcTrades.BuyOffer offer = offers.get(idx);
                int cx = x + OFFERS_X + col * CELL;
                int cy = y + OFFERS_Y + (row - scrollBuy) * (CELL + PRICE_DY);

                boolean canAfford = canAffordBuy(offer);
                boolean hov = inRect(mx, my, cx, cy, 16, 16);

                // Slot background
                drawSlotBg(g, cx, cy);
                // Tint the slot
                if (hov)
                    g.fill(cx, cy, cx + 16, cy + 16, 0x44_FFFFFF);
                else if (!canAfford)
                    g.fill(cx, cy, cx + 16, cy + 16, 0x66_FF0000);

                // Item icon
                g.renderItem(offer.payStack(), cx, cy);

                // Count badge (top-right of icon)
                if (offer.payCount() > 1) {
                    String cnt = String.valueOf(offer.payCount());
                    g.drawString(font, cnt, cx + 17 - font.width(cnt), cy + 9,
                            C_WHITE, true);
                }

                // Coin cost number below the slot
                String cost = String.valueOf(offer.coinCost());
                g.drawString(font, cost,
                        cx + (16 - font.width(cost)) / 2,
                        cy + PRICE_DY, canAfford ? C_GOLD : 0xFF_AA5555, true);
            }
        }

        // Scroll hints
        if (scrollBuy > 0)
            g.drawString(font, "▲", x + W - 14, y + OFFERS_Y, C_WHITE, false);
        if (scrollBuy + visRows < rows)
            g.drawString(font, "▼", x + W - 14, y + OFFERS_Y + visRows * (CELL + PRICE_DY) - 8,
                    C_WHITE, false);
    }

    // ── Sell tab ──────────────────────────────────────────────────────────────

    private void renderSellTab(GuiGraphics g, int x, int y, int mx, int my) {
        List<GotNpcTrades.SellOffer> offers = menu.getSellOffers();

        // "You can sell" row (visual display of what NPC accepts)
        g.drawString(font, "You can sell", x + OFFERS_X, y + OFFERS_Y - 10, C_LABEL, false);

        int visOffers = Math.min(offers.size(), CELLS_PER_ROW);
        for (int i = 0; i < visOffers; i++) {
            int idx = i + scrollSell;
            if (idx >= offers.size()) break;
            GotNpcTrades.SellOffer offer = offers.get(idx);
            int cx = x + OFFERS_X + i * CELL;
            int cy = y + OFFERS_Y;

            boolean sel = (selectedSell == idx);
            boolean hov = inRect(mx, my, cx, cy, 16, 16);

            drawSlotBg(g, cx, cy);
            if (sel)       g.fill(cx, cy, cx + 16, cy + 16, 0x88_0055FF);
            else if (hov)  g.fill(cx, cy, cx + 16, cy + 16, 0x44_FFFFFF);

            g.renderItem(offer.costStack(), cx, cy);

            // Required count below
            String cnt  = String.valueOf(offer.costCount());
            g.drawString(font, cnt,
                    cx + (16 - font.width(cnt)) / 2,
                    cy + PRICE_DY, sel ? C_GOLD : 0xFF_DDDDDD, true);
        }

        // "You want to sell" section
        g.drawString(font, "You want to sell", x + OFFERS_X, y + SELL_LBL_Y, C_LABEL, false);

        // Sell input slot background  (slot 36 is drawn by AbstractContainerScreen
        // at leftPos + SELL_SLOT_X, topPos + SELL_SLOT_Y — we just add the surround)
        int sx = x + NpcTradeMenu.SELL_SLOT_X;
        int sy = y + NpcTradeMenu.SELL_SLOT_Y;
        drawSlotBg(g, sx, sy);

        // Show payout for selected offer next to sell slot
        if (selectedSell >= 0 && selectedSell < offers.size()) {
            GotNpcTrades.SellOffer offer = offers.get(selectedSell);
            g.drawString(font, "→ " + offer.coinPay() + " " + capitalize(offer.coinType().id),
                    sx + 20, sy + 4, C_GOLD, true);
        }
    }

    // ── Slot hide on buy tab ──────────────────────────────────────────────────

    /** Don't render the sell slot when on the buy tab. */
    @Override
    protected void renderSlot(GuiGraphics g, Slot slot) {
        if (tab == 0 && slot.index == NpcTradeMenu.SELL_SLOT_INDEX) {
            return;
        }
        super.renderSlot(g, slot);
    }

    /** Prevent interaction with the sell slot on the buy tab. */
    @Override
    protected void slotClicked(Slot slot, int slotIndex, int mouseButton, ClickType type) {
        if (tab == 0 && slotIndex == NpcTradeMenu.SELL_SLOT_INDEX) {
            return;
        }
        super.slotClicked(slot, slotIndex, mouseButton, type);
    }

    // ── Input ─────────────────────────────────────────────────────────────────

    @Override
    public boolean mouseClicked(double mx, double my, int btn) {
        if (btn != 0) return super.mouseClicked(mx, my, btn);
        int x = leftPos, y = topPos;

        // Tab selectors
        if (inRect(mx, my, x + 8, y + TAB_Y, TAB_W, TAB_H)) {
            tab = 0; scrollBuy = 0; return true;
        }
        if (inRect(mx, my, x + 8 + TAB_W + 4, y + TAB_Y, TAB_W, TAB_H)) {
            tab = 1; scrollSell = 0; selectedSell = -1; return true;
        }

        if (tab == 0) {
            // Buy: click offer cell
            List<GotNpcTrades.BuyOffer> offers = menu.getBuyOffers();
            int rows    = (int) Math.ceil((double) offers.size() / CELLS_PER_ROW);
            int visRows = Math.min(2, rows);
            for (int row = scrollBuy; row < scrollBuy + visRows && row < rows; row++) {
                for (int col = 0; col < CELLS_PER_ROW; col++) {
                    int idx = row * CELLS_PER_ROW + col;
                    if (idx >= offers.size()) break;
                    int cx = x + OFFERS_X + col * CELL;
                    int cy = y + OFFERS_Y + (row - scrollBuy) * (CELL + PRICE_DY);
                    if (inRect(mx, my, cx, cy, 16, 16)) {
                        executeBuy(idx);
                        return true;
                    }
                }
            }
        } else {
            // Sell: select offer row
            List<GotNpcTrades.SellOffer> offers = menu.getSellOffers();
            int visOffers = Math.min(offers.size(), CELLS_PER_ROW);
            for (int i = 0; i < visOffers; i++) {
                int idx = i + scrollSell;
                if (idx >= offers.size()) break;
                int cx = x + OFFERS_X + i * CELL;
                int cy = y + OFFERS_Y;
                if (inRect(mx, my, cx, cy, 16, 16)) {
                    selectedSell = (selectedSell == idx) ? -1 : idx;
                    return true;
                }
            }
        }

        return super.mouseClicked(mx, my, btn);
    }

    @Override
    public boolean mouseScrolled(double mx, double my, double dx, double dy) {
        if (tab == 0) {
            int rows = (int) Math.ceil((double) menu.getBuyOffers().size() / CELLS_PER_ROW);
            int max  = Math.max(0, rows - 2);
            scrollBuy = dy < 0 ? Math.min(scrollBuy + 1, max) : Math.max(scrollBuy - 1, 0);
        } else {
            int max = Math.max(0, menu.getSellOffers().size() - CELLS_PER_ROW);
            scrollSell = dy < 0 ? Math.min(scrollSell + 1, max) : Math.max(scrollSell - 1, 0);
        }
        return true;
    }

    // ── Trade execution ───────────────────────────────────────────────────────

    private boolean canAffordBuy(GotNpcTrades.BuyOffer offer) {
        if (minecraft == null || minecraft.player == null) return false;
        return offer.coinType().countIn(minecraft.player.getInventory()) >= offer.coinCost();
    }

    private boolean canExecuteSell() {
        if (minecraft == null || minecraft.player == null || selectedSell < 0) return false;
        List<GotNpcTrades.SellOffer> offers = menu.getSellOffers();
        if (selectedSell >= offers.size()) return false;
        GotNpcTrades.SellOffer offer = offers.get(selectedSell);
        ItemStack slot = menu.getSellInputSlot().getItem(0);
        return !slot.isEmpty() && slot.is(offer.costItem()) && slot.getCount() >= offer.costCount();
    }

    private void executeBuy(int idx) {
        List<GotNpcTrades.BuyOffer> offers = menu.getBuyOffers();
        if (idx < 0 || idx >= offers.size()) return;
        if (minecraft == null || minecraft.player == null) return;
        GotNpcTrades.BuyOffer offer = offers.get(idx);
        if (!canAffordBuy(offer)) return;
        offer.coinType().removeFrom(minecraft.player.getInventory(), offer.coinCost());
        minecraft.player.getInventory().add(offer.payStack());
        minecraft.player.getInventory().setChanged();
    }

    private void doSell() {
        if (selectedSell >= 0 && canExecuteSell()) {
            PacketDistributor.sendToServer(new ExecuteSellPayload(selectedSell));
            selectedSell = -1;
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private String buildTitle() {
        String occ = capitalize(menu.getOccupation().id);
        return menu.getNpcName().isEmpty() ? occ : menu.getNpcName() + "  —  " + occ;
    }

    private void drawTabSelector(GuiGraphics g, int x, int y, int w, int h,
                                 String label, boolean active, int mx, int my) {
        boolean hov = !active && inRect(mx, my, x, y, w, h);
        // Tab background
        g.fill(x, y, x + w, y + h, active ? C_TAB_ON : C_TAB_OFF);
        // Active tab: flush border with panel (no bottom border)
        if (active) {
            g.fill(x, y, x + w, y + 1, C_HILITE);
            g.fill(x, y, x + 1, y + h, C_HILITE);
            g.fill(x + w - 1, y, x + w, y + h, C_SHADOW);
        } else {
            g.fill(x, y, x + w, y + 1, C_HILITE);
            g.fill(x, y, x + 1, y + h, C_HILITE);
            g.fill(x, y + h - 1, x + w, y + h, C_SHADOW);
            g.fill(x + w - 1, y, x + w, y + h, C_SHADOW);
            if (hov) g.fill(x + 1, y + 1, x + w - 1, y + h - 1, 0x22_FFFFFF);
        }
        // Label
        g.drawCenteredString(font, label, x + w / 2, y + (h - 8) / 2, C_TEXT);
    }

    private void drawSlotBg(GuiGraphics g, int x, int y) {
        // 1px dark border then medium-gray interior — classic MC slot look
        g.fill(x - 1, y - 1, x + 17, y + 17, C_SLOT_BDR);
        g.fill(x, y, x + 16, y + 16, C_SLOT);
    }

    private static boolean inRect(double mx, double my, int x, int y, int w, int h) {
        return mx >= x && mx < x + w && my >= y && my < y + h;
    }

    private static String capitalize(String s) {
        return s == null || s.isEmpty() ? s
                : Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}