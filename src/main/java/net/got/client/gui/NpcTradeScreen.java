package net.got.client.gui;

import net.got.event.entity.npc.data.GotNpcOccupation;
import net.got.event.entity.npc.data.GotNpcTrades;
import net.got.network.CloseInteractScreenPayload;
import net.got.network.ExecuteSellPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

/**
 * Redesigned NPC trade screen.
 *
 * - No text labels ("You can buy" etc. removed)
 * - Buy tab: grid of item icons — left-click to buy
 * - Sell tab: grid of item icons — left-click to sell (items taken from inventory)
 * - Hovering an icon shows a tooltip with name + price
 * - Tinted red when you can't afford / don't have enough items
 * - Larger panel with generous spacing
 * - Extends Screen (not AbstractContainerScreen) — no container slot overhead
 */
public class NpcTradeScreen extends Screen {

    // ── Layout ────────────────────────────────────────────────────────────────
    private static final int GUI_W       = 252;
    private static final int GUI_H       = 260;
    private static final int COLS        = 5;
    private static final int CELL        = 40;   // cell stride (px)
    private static final int ICON_PAD    = 12;   // padding inside cell before icon (centres 16px icon)
    private static final int GRID_X      = 14;   // left margin of grid
    private static final int GRID_Y      = 36;   // top of grid (below tab bar)
    private static final int TAB_Y       = 8;
    private static final int TAB_W       = 60;
    private static final int TAB_H       = 18;
    private static final int MAX_ROWS    = 4;    // visible rows before scroll

    // ── Colours ───────────────────────────────────────────────────────────────
    private static final int C_BG        = 0xFF_C6C6C6;
    private static final int C_HILITE    = 0xFF_FFFFFF;
    private static final int C_SHADOW    = 0xFF_555555;
    private static final int C_BORDER    = 0xFF_000000;
    private static final int C_SLOT      = 0xFF_8B8B8B;
    private static final int C_SLOT_BDR  = 0xFF_373737;
    private static final int C_TAB_ON    = 0xFF_C6C6C6;
    private static final int C_TAB_OFF   = 0xFF_9E9E9E;
    private static final int C_TEXT      = 0xFF_404040;
    private static final int C_GOLD      = 0xFF_FFFF55;
    private static final int C_WHITE     = 0xFF_FFFFFF;
    private static final int TINT_BAD    = 0x88_FF2020;
    private static final int TINT_HOVER  = 0x55_FFFFFF;
    private static final int TINT_GOOD   = 0x33_40FF40;

    // ── State ─────────────────────────────────────────────────────────────────
    private final int            entityId;
    private final GotNpcOccupation occupation;
    private final String         npcName;
    private int  tab     = 0;   // 0 = buy, 1 = sell
    private int  scrollY = 0;   // current scroll row offset

    private List<GotNpcTrades.BuyOffer>  buyOffers;
    private List<GotNpcTrades.SellOffer> sellOffers;

    private NpcTradeScreen(int entityId, String occupationId, String npcName) {
        super(Component.empty());
        this.entityId   = entityId;
        this.occupation = GotNpcOccupation.fromString(occupationId);
        this.npcName    = npcName;
        this.buyOffers  = GotNpcTrades.getBuyOffers(this.occupation);
        this.sellOffers = GotNpcTrades.getSellOffers(this.occupation);
    }

    public static void open(int entityId, String occupationId, String npcName) {
        Minecraft mc = Minecraft.getInstance();
        if (mc != null) mc.setScreen(new NpcTradeScreen(entityId, occupationId, npcName));
    }

    @Override public boolean isPauseScreen() { return false; }

    // ── Rendering ─────────────────────────────────────────────────────────────

    @Override
    public void render(GuiGraphics g, int mx, int my, float delta) {
        int px = (width  - GUI_W) / 2;
        int py = (height - GUI_H) / 2;

        drawPanel(g, px, py);
        drawTabs(g, px, py, mx, my);
        drawGrid(g, px, py, mx, my);
        super.render(g, mx, my, delta);
        drawHoveredTooltip(g, px, py, mx, my);
    }

    private void drawPanel(GuiGraphics g, int px, int py) {
        // Outer border
        g.fill(px - 1, py - 1, px + GUI_W + 1, py + GUI_H + 1, C_BORDER);
        // Background
        g.fill(px, py, px + GUI_W, py + GUI_H, C_BG);
        // Highlight (top + left edge)
        g.fill(px, py, px + GUI_W, py + 1, C_HILITE);
        g.fill(px, py, px + 1, py + GUI_H, C_HILITE);
        // Shadow (bottom + right edge)
        g.fill(px, py + GUI_H - 1, px + GUI_W, py + GUI_H, C_SHADOW);
        g.fill(px + GUI_W - 1, py, px + GUI_W, py + GUI_H, C_SHADOW);

        // NPC name centred at top
        g.drawCenteredString(font, npcName.isEmpty() ? capitalize(occupation.id) : npcName,
                px + GUI_W / 2, py + 2, C_TEXT);
    }

    private void drawTabs(GuiGraphics g, int px, int py, int mx, int my) {
        drawTab(g, px + 4,            py + TAB_Y, TAB_W, TAB_H, "Buy",  tab == 0, mx, my);
        drawTab(g, px + 4 + TAB_W + 4, py + TAB_Y, TAB_W, TAB_H, "Sell", tab == 1, mx, my);
    }

    private void drawTab(GuiGraphics g, int x, int y, int w, int h,
                         String label, boolean active, int mx, int my) {
        g.fill(x, y, x + w, y + h, active ? C_TAB_ON : C_TAB_OFF);
        g.fill(x, y, x + w, y + 1, C_HILITE);
        g.fill(x, y, x + 1, y + h, C_HILITE);
        if (!active) {
            g.fill(x, y + h - 1, x + w, y + h, C_SHADOW);
            g.fill(x + w - 1, y, x + w, y + h, C_SHADOW);
            if (inRect(mx, my, x, y, w, h))
                g.fill(x + 1, y + 1, x + w - 1, y + h - 1, 0x22_FFFFFF);
        } else {
            g.fill(x + w - 1, y, x + w, y + h, C_SHADOW);
        }
        g.drawCenteredString(font, label, x + w / 2, y + (h - 8) / 2, C_TEXT);
    }

    private void drawGrid(GuiGraphics g, int px, int py, int mx, int my) {
        List<?> offers = (tab == 0) ? buyOffers : sellOffers;
        Inventory inv = (minecraft != null && minecraft.player != null)
                ? minecraft.player.getInventory() : null;

        int rows = (int) Math.ceil((double) offers.size() / COLS);
        int visRows = Math.min(rows, MAX_ROWS);

        for (int row = scrollY; row < scrollY + visRows && row < rows; row++) {
            for (int col = 0; col < COLS; col++) {
                int idx = row * COLS + col;
                if (idx >= offers.size()) break;

                int cx = px + GRID_X + col * CELL;
                int cy = py + GRID_Y + (row - scrollY) * CELL;
                boolean hov = inRect(mx, my, cx, cy, CELL - 2, CELL - 2);

                boolean canDo = (inv != null) && canExecute(idx, inv);

                // Slot background
                g.fill(cx - 1, cy - 1, cx + CELL - 3, cy + CELL - 3, C_SLOT_BDR);
                g.fill(cx, cy, cx + CELL - 4, cy + CELL - 4, C_SLOT);

                // Affordability tint
                if (!canDo)
                    g.fill(cx, cy, cx + CELL - 4, cy + CELL - 4, TINT_BAD);
                else if (hov)
                    g.fill(cx, cy, cx + CELL - 4, cy + CELL - 4, TINT_HOVER);

                // Item icon — centred in cell
                ItemStack stack = getDisplayStack(idx);
                g.renderItem(stack, cx + ICON_PAD, cy + ICON_PAD);

                // Count badge (bottom-right of icon)
                int count = stack.getCount();
                if (count > 1) {
                    String cnt = String.valueOf(count);
                    g.drawString(font, cnt,
                            cx + ICON_PAD + 16 - font.width(cnt),
                            cy + ICON_PAD + 8, C_WHITE, true);
                }
            }
        }

        // Scroll arrows
        int arrowX = px + GUI_W - 14;
        if (scrollY > 0)
            g.drawString(font, "▲", arrowX, py + GRID_Y, C_WHITE, true);
        if (scrollY + visRows < rows)
            g.drawString(font, "▼", arrowX, py + GRID_Y + visRows * CELL - 10, C_WHITE, true);
    }

    private void drawHoveredTooltip(GuiGraphics g, int px, int py, int mx, int my) {
        List<?> offers = (tab == 0) ? buyOffers : sellOffers;
        Inventory inv = (minecraft != null && minecraft.player != null)
                ? minecraft.player.getInventory() : null;

        int rows = (int) Math.ceil((double) offers.size() / COLS);
        int visRows = Math.min(rows, MAX_ROWS);

        for (int row = scrollY; row < scrollY + visRows && row < rows; row++) {
            for (int col = 0; col < COLS; col++) {
                int idx = row * COLS + col;
                if (idx >= offers.size()) break;

                int cx = px + GRID_X + col * CELL;
                int cy = py + GRID_Y + (row - scrollY) * CELL;

                if (!inRect(mx, my, cx, cy, CELL - 2, CELL - 2)) continue;

                List<Component> tooltip = new ArrayList<>();
                ItemStack stack = getDisplayStack(idx);
                tooltip.add(stack.getHoverName());

                if (tab == 0) {
                    GotNpcTrades.BuyOffer offer = buyOffers.get(idx);
                    String coinName = capitalize(offer.coinType().id);
                    tooltip.add(Component.literal("§7Cost: §e" + offer.coinCost() + " " + coinName));
                    boolean can = inv != null && canExecute(idx, inv);
                    tooltip.add(Component.literal(can ? "§aLeft-click to buy" : "§cNot enough coins"));
                } else {
                    GotNpcTrades.SellOffer offer = sellOffers.get(idx);
                    String coinName = capitalize(offer.coinType().id);
                    tooltip.add(Component.literal("§7Need: §f" + offer.costCount() + "x "
                            + new ItemStack(offer.costItem()).getHoverName().getString()));
                    tooltip.add(Component.literal("§7Sell for: §e" + offer.coinPay() + " " + coinName));
                    boolean can = inv != null && canExecute(idx, inv);
                    tooltip.add(Component.literal(can ? "§aLeft-click to sell" : "§cNot enough items"));
                }

                g.renderComponentTooltip(font, tooltip, mx, my);
                return;
            }
        }
    }

    // ── Input ─────────────────────────────────────────────────────────────────

    @Override
    public boolean mouseClicked(double mx, double my, int btn) {
        if (btn != 0) return super.mouseClicked(mx, my, btn);
        int px = (width - GUI_W) / 2, py = (height - GUI_H) / 2;

        // Tabs
        if (inRect(mx, my, px + 4, py + TAB_Y, TAB_W, TAB_H)) {
            tab = 0; scrollY = 0; return true;
        }
        if (inRect(mx, my, px + 4 + TAB_W + 4, py + TAB_Y, TAB_W, TAB_H)) {
            tab = 1; scrollY = 0; return true;
        }

        // Grid cells
        List<?> offers = (tab == 0) ? buyOffers : sellOffers;
        int rows = (int) Math.ceil((double) offers.size() / COLS);
        int visRows = Math.min(rows, MAX_ROWS);

        for (int row = scrollY; row < scrollY + visRows && row < rows; row++) {
            for (int col = 0; col < COLS; col++) {
                int idx = row * COLS + col;
                if (idx >= offers.size()) break;
                int cx = px + GRID_X + col * CELL;
                int cy = py + GRID_Y + (row - scrollY) * CELL;
                if (inRect(mx, my, cx, cy, CELL - 2, CELL - 2)) {
                    if (tab == 0) executeBuy(idx);
                    else          executeSell(idx);
                    return true;
                }
            }
        }

        return super.mouseClicked(mx, my, btn);
    }

    @Override
    public boolean mouseScrolled(double mx, double my, double dx, double dy) {
        List<?> offers = (tab == 0) ? buyOffers : sellOffers;
        int rows = (int) Math.ceil((double) offers.size() / COLS);
        int maxScroll = Math.max(0, rows - MAX_ROWS);
        if (dy < 0) scrollY = Math.min(scrollY + 1, maxScroll);
        else        scrollY = Math.max(scrollY - 1, 0);
        return true;
    }

    @Override
    public boolean keyPressed(int key, int scan, int mods) {
        if (key == 256) { onClose(); return true; }
        return super.keyPressed(key, scan, mods);
    }

    @Override
    public void onClose() {
        PacketDistributor.sendToServer(new CloseInteractScreenPayload(entityId));
        super.onClose();
    }

    // World shows through — no background dim
    @Override
    public void renderBackground(GuiGraphics g, int mx, int my, float delta) {}

    // ── Trade execution ───────────────────────────────────────────────────────

    private boolean canExecute(int idx, Inventory inv) {
        if (tab == 0) {
            if (idx >= buyOffers.size()) return false;
            GotNpcTrades.BuyOffer o = buyOffers.get(idx);
            return o.coinType().countIn(inv) >= o.coinCost();
        } else {
            if (idx >= sellOffers.size()) return false;
            GotNpcTrades.SellOffer o = sellOffers.get(idx);
            int have = 0;
            for (int i = 0; i < inv.getContainerSize(); i++) {
                ItemStack s = inv.getItem(i);
                if (s.is(o.costItem())) have += s.getCount();
            }
            return have >= o.costCount();
        }
    }

    private ItemStack getDisplayStack(int idx) {
        if (tab == 0) {
            GotNpcTrades.BuyOffer o = buyOffers.get(idx);
            return o.payStack();
        } else {
            GotNpcTrades.SellOffer o = sellOffers.get(idx);
            return o.costStack();
        }
    }

    /** Buy: remove coins from inventory, grant items. (client-side) */
    private void executeBuy(int idx) {
        if (idx >= buyOffers.size() || minecraft == null || minecraft.player == null) return;
        GotNpcTrades.BuyOffer offer = buyOffers.get(idx);
        Inventory inv = minecraft.player.getInventory();
        if (!canExecute(idx, inv)) return;
        offer.coinType().removeFrom(inv, offer.coinCost());
        inv.add(offer.payStack());
        inv.setChanged();
    }

    /** Sell: server-authoritative, sends payload. */
    private void executeSell(int idx) {
        if (idx >= sellOffers.size() || minecraft == null || minecraft.player == null) return;
        if (!canExecute(idx, minecraft.player.getInventory())) return;
        PacketDistributor.sendToServer(new ExecuteSellPayload(entityId, idx));

        // Optimistic client-side update so the player sees inventory change instantly
        GotNpcTrades.SellOffer offer = sellOffers.get(idx);
        Inventory inv = minecraft.player.getInventory();
        int toRemove = offer.costCount();
        for (int i = 0; i < inv.getContainerSize() && toRemove > 0; i++) {
            ItemStack s = inv.getItem(i);
            if (s.is(offer.costItem())) {
                int take = Math.min(toRemove, s.getCount());
                s.shrink(take);
                toRemove -= take;
                if (s.isEmpty()) inv.setItem(i, ItemStack.EMPTY);
            }
        }
        inv.add(offer.coinStack());
        inv.setChanged();
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private static boolean inRect(double mx, double my, int x, int y, int w, int h) {
        return mx >= x && mx < x + w && my >= y && my < y + h;
    }

    private static String capitalize(String s) {
        return s == null || s.isEmpty() ? s
                : Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
