package net.got.client.gui;

import net.got.item.GotCoin;
import net.got.network.CoinExchangePayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * Coin exchange screen — break large coins into smaller ones or combine
 * smaller ones into larger ones.
 *
 * <p>Each row shows one denomination.  Two buttons appear per row:
 * <ul>
 *   <li><b>▼ Break</b> — convert 1 of this coin into {@code ratio} of the
 *       next-smaller denomination.  Greyed if you have none, or if this is
 *       the smallest coin.</li>
 *   <li><b>▲ Combine</b> — convert {@code ratio} of the next-smaller coin
 *       into 1 of this coin.  Greyed if you don't have enough smaller coins,
 *       or if this is the largest coin.</li>
 * </ul>
 *
 * <p>The screen also shows your current count of each denomination.
 */
public class NpcCoinExchangeScreen extends Screen {

    private static final int GUI_W   = 240;
    private static final int GUI_H   = 210;
    private static final int ROW_H   = 22;
    private static final int ROW_Y0  = 24;

    // Column x positions (relative to window x)
    private static final int COL_ICON  = 6;
    private static final int COL_NAME  = 26;
    private static final int COL_COUNT = 108;
    private static final int COL_BREAK = 138;
    private static final int COL_COMB  = 190;
    private static final int BTN_W     = 46;
    private static final int BTN_H     = 14;

    private static final int C_BG     = 0xFF_C6C6C6;
    private static final int C_PANEL  = 0xFF_8B8B8B;
    private static final int C_DARK   = 0xFF_373737;
    private static final int C_BTN    = 0xFF_5A5A8A;
    private static final int C_BTN_H  = 0xFF_8888CC;
    private static final int C_BTN_D  = 0xFF_444444;
    private static final int C_WHITE  = 0xFF_FFFFFF;
    private static final int C_GOLD   = 0xFF_FFD700;
    private static final int C_TITLE  = 0xFF_222222;

    private static final GotCoin[] COINS = GotCoin.values(); // smallest first

    public NpcCoinExchangeScreen() {
        super(Component.literal("Coin Exchange"));
    }

    @Override
    public boolean isPauseScreen() { return false; }

    @Override
    public void render(GuiGraphics g, int mx, int my, float delta) {
        renderBackground(g, mx, my, delta);
        int x = (width - GUI_W) / 2, y = (height - GUI_H) / 2;
        Inventory inv = Minecraft.getInstance().player == null
                ? null : Minecraft.getInstance().player.getInventory();

        // Chrome
        g.fill(x, y, x + GUI_W, y + GUI_H, C_BG);
        g.fill(x + 1, y + 1, x + GUI_W - 1, y + GUI_H - 1, C_PANEL);

        // Title
        String title = "Coin Exchange";
        g.drawString(font, title, x + (GUI_W - font.width(title)) / 2, y + 7, C_TITLE, false);

        // Column headers
        g.drawString(font, "Coin",    x + COL_NAME,  y + ROW_Y0 - 10, C_TITLE, false);
        g.drawString(font, "In Bag",  x + COL_COUNT, y + ROW_Y0 - 10, C_TITLE, false);
        g.drawString(font, "Break ▼", x + COL_BREAK, y + ROW_Y0 - 10, C_TITLE, false);
        g.drawString(font, "Combine ▲", x + COL_COMB - 4, y + ROW_Y0 - 10, C_TITLE, false);

        // Separator
        g.fill(x + 4, y + ROW_Y0 - 1, x + GUI_W - 4, y + ROW_Y0, C_DARK);

        for (int i = 0; i < COINS.length; i++) {
            GotCoin coin = COINS[i]; // smallest first
            // Display largest first — more intuitive
            GotCoin display = COINS[COINS.length - 1 - i];
            int ry = y + ROW_Y0 + i * ROW_H;

            int count = (inv == null) ? 0 : display.countIn(inv);
            int smallerCount = (inv == null || display.smaller == null)
                    ? 0 : display.smaller.countIn(inv);

            // Alternating row tint
            if (i % 2 == 0) g.fill(x + 4, ry, x + GUI_W - 4, ry + ROW_H - 1, 0x22_000000);

            // Coin icon
            g.renderItem(new ItemStack(display.item()), x + COL_ICON, ry + 3);

            // Coin name + ratio hint
            String ratioHint = display.smaller != null
                    ? " (" + display.ratio() + "× " + capitalize(display.smaller.id) + ")"
                    : "";
            g.drawString(font, capitalize(display.id) + ratioHint,
                    x + COL_NAME, ry + 7, C_TITLE, false);

            // Count in bag
            g.drawString(font, String.valueOf(count),
                    x + COL_COUNT + (20 - font.width(String.valueOf(count))) / 2,
                    ry + 7, count > 0 ? C_GOLD : 0xFF_888888, true);

            // Break button (need ≥1 of this coin, and has smaller)
            boolean canBreak = count >= 1 && display.smaller != null;
            boolean brkHov   = canBreak && hit(mx, my, x + COL_BREAK, ry + 4, BTN_W, BTN_H);
            drawBtn(g, x + COL_BREAK, ry + 4, BTN_W, BTN_H, "▼ Break", canBreak, brkHov);

            // Combine button (need ≥ratio of smaller, and has smaller)
            boolean canComb = display.smaller != null && smallerCount >= display.ratio();
            boolean cmbHov  = canComb && hit(mx, my, x + COL_COMB, ry + 4, BTN_W, BTN_H);
            drawBtn(g, x + COL_COMB, ry + 4, BTN_W, BTN_H, "▲ Combine", canComb, cmbHov);
        }

        // Back button
        boolean backHov = hit(mx, my, x + GUI_W / 2 - 30, y + GUI_H - 18, 60, 12);
        g.fill(x + GUI_W / 2 - 30, y + GUI_H - 18, x + GUI_W / 2 + 30, y + GUI_H - 6,
                backHov ? C_BTN_H : C_BTN);
        g.drawString(font, "Close", x + GUI_W / 2 - font.width("Close") / 2, y + GUI_H - 15,
                C_WHITE, true);

        super.render(g, mx, my, delta);
    }

    @Override
    public boolean mouseClicked(double mx, double my, int btn) {
        if (btn != 0) return super.mouseClicked(mx, my, btn);
        int x = (width - GUI_W) / 2, y = (height - GUI_H) / 2;
        Inventory inv = Minecraft.getInstance().player == null
                ? null : Minecraft.getInstance().player.getInventory();

        // Close button
        if (hit(mx, my, x + GUI_W / 2 - 30, y + GUI_H - 18, 60, 12)) {
            onClose();
            return true;
        }

        for (int i = 0; i < COINS.length; i++) {
            GotCoin display = COINS[COINS.length - 1 - i];
            int ry = y + ROW_Y0 + i * ROW_H;
            int count        = (inv == null) ? 0 : display.countIn(inv);
            int smallerCount = (inv == null || display.smaller == null)
                    ? 0 : display.smaller.countIn(inv);

            // Break button
            if (display.smaller != null && count >= 1
                    && hit(mx, my, x + COL_BREAK, ry + 4, BTN_W, BTN_H)) {
                PacketDistributor.sendToServer(
                        new CoinExchangePayload(display.id, true));
                return true;
            }

            // Combine button
            if (display.smaller != null && smallerCount >= display.ratio()
                    && hit(mx, my, x + COL_COMB, ry + 4, BTN_W, BTN_H)) {
                PacketDistributor.sendToServer(
                        new CoinExchangePayload(display.id, false));
                return true;
            }
        }

        return super.mouseClicked(mx, my, btn);
    }

    @Override
    public boolean keyPressed(int key, int scan, int mods) {
        if (key == 256) { onClose(); return true; }
        return super.keyPressed(key, scan, mods);
    }

    private void drawBtn(GuiGraphics g, int x, int y, int w, int h,
                         String label, boolean enabled, boolean hov) {
        int col = enabled ? (hov ? C_BTN_H : C_BTN) : C_BTN_D;
        g.fill(x, y, x + w, y + h, col);
        int lx = x + (w - font.width(label)) / 2;
        int ly = y + (h - 8) / 2;
        g.drawString(font, label, lx, ly, C_WHITE, true);
    }

    private static boolean hit(double mx, double my, int x, int y, int w, int h) {
        return mx >= x && mx < x + w && my >= y && my < y + h;
    }

    private static String capitalize(String s) {
        return s == null || s.isEmpty() ? s
                : Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
