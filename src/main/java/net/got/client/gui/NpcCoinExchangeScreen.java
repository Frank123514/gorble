package net.got.client.gui;

import net.got.network.CloseInteractScreenPayload;
import net.got.item.GotCoin;
import net.got.network.CoinExchangePayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * Coin exchange screen using vanilla Button widgets.
 *
 * Each row shows one denomination with:
 *  - Coin icon + name + ratio hint
 *  - Current count in player's bag
 *  - [▼ Break] — spend 1 of this coin for ratio smaller coins
 *  - [▲ Combine] — spend ratio smaller coins for 1 of this coin
 */
public class NpcCoinExchangeScreen extends Screen {

    private static final int GUI_W   = 260;
    private static final int ROW_H   = 22;
    private static final int ROW_Y0  = 28;   // first row top
    private static final int BTN_W   = 56;
    private static final int BTN_H   = 14;
    private static final int CLOSE_W = 60;
    private static final int CLOSE_H = 20;

    // Column x-offsets (relative to panel left)
    private static final int COL_ICON   = 6;
    private static final int COL_NAME   = 26;
    private static final int COL_COUNT  = 120;
    private static final int COL_BREAK  = 142;
    private static final int COL_COMB   = 202;

    private static final int C_BG     = 0xFF_C6C6C6;
    private static final int C_HILITE = 0xFF_FFFFFF;
    private static final int C_SHADOW = 0xFF_555555;
    private static final int C_BORDER = 0xFF_000000;
    private static final int C_TEXT   = 0xFF_404040;
    private static final int C_GOLD   = 0xFF_FFFF55;

    private static final GotCoin[] COINS = GotCoin.values(); // smallest first

    private final int entityId;

    // Computed panel height once we know the number of rows
    private int panelH;
    private int px, py; // panel top-left (computed in init)

    public NpcCoinExchangeScreen(int entityId) {
        super(Component.literal("Coin Exchange"));
        this.entityId = entityId;
    }

    @Override public boolean isPauseScreen() { return false; }

    @Override
    protected void init() {
        super.init();

        int rows    = COINS.length;          // 8 denominations
        panelH = ROW_Y0 + rows * ROW_H + ROW_H + 6; // header + rows + close btn + padding

        px = (width  - GUI_W) / 2;
        py = (height - panelH) / 2;

        // Add vanilla buttons for each denomination row
        for (int i = 0; i < rows; i++) {
            // Display largest first (index 0 = halfpenny, rows-1-i = dragon first)
            final GotCoin coin = COINS[rows - 1 - i];
            int rowY = py + ROW_Y0 + i * ROW_H;
            int midY = rowY + (ROW_H - BTN_H) / 2;

            // Break button
            if (coin.smaller != null) {
                addRenderableWidget(Button.builder(
                        Component.literal("▼ Break"),
                        btn -> PacketDistributor.sendToServer(
                                new CoinExchangePayload(coin.id, true))
                ).bounds(px + COL_BREAK, midY, BTN_W, BTN_H).build());

                // Combine button
                addRenderableWidget(Button.builder(
                        Component.literal("▲ Combine"),
                        btn -> PacketDistributor.sendToServer(
                                new CoinExchangePayload(coin.id, false))
                ).bounds(px + COL_COMB, midY, BTN_W, BTN_H).build());
            }
        }

        // Close button
        addRenderableWidget(Button.builder(
                Component.literal("Close"),
                btn -> onClose()
        ).bounds(px + (GUI_W - CLOSE_W) / 2,
                  py + panelH - ROW_H - 4,
                  CLOSE_W, CLOSE_H).build());
    }

    @Override
    public void render(GuiGraphics g, int mx, int my, float delta) {
        // ── Panel background ──────────────────────────────────────────────────
        g.fill(px - 1, py - 1, px + GUI_W + 1, py + panelH + 1, C_BORDER);
        g.fill(px, py, px + GUI_W, py + panelH, C_BG);
        g.fill(px, py, px + GUI_W, py + 1, C_HILITE);
        g.fill(px, py, px + 1, py + panelH, C_HILITE);
        g.fill(px, py + panelH - 1, px + GUI_W, py + panelH, C_SHADOW);
        g.fill(px + GUI_W - 1, py, px + GUI_W, py + panelH, C_SHADOW);

        // ── Title ─────────────────────────────────────────────────────────────
        g.drawCenteredString(font, "Coin Exchange", px + GUI_W / 2, py + 8, C_TEXT);

        // ── Column headers ────────────────────────────────────────────────────
        int hdrY = py + ROW_Y0 - 10;
        g.drawString(font, "Coin",    px + COL_NAME,  hdrY, C_TEXT, false);
        g.drawString(font, "In Bag",  px + COL_COUNT, hdrY, C_TEXT, false);
        // Divider below headers
        g.fill(px + 4, py + ROW_Y0 - 2, px + GUI_W - 4, py + ROW_Y0 - 1, C_SHADOW);

        // ── Denomination rows ─────────────────────────────────────────────────
        Inventory inv = Minecraft.getInstance().player == null
                ? null : Minecraft.getInstance().player.getInventory();

        int rows = COINS.length;
        for (int i = 0; i < rows; i++) {
            GotCoin coin = COINS[rows - 1 - i]; // largest first
            int rowY = py + ROW_Y0 + i * ROW_H;

            // Alternating row shade
            if (i % 2 == 0)
                g.fill(px + 4, rowY, px + GUI_W - 4, rowY + ROW_H - 1, 0x18_000000);

            // Coin icon
            g.renderItem(new ItemStack(coin.item()), px + COL_ICON, rowY + 3);

            // Coin name + ratio
            String ratioHint = coin.smaller != null
                    ? " = " + coin.ratio() + " " + capitalize(coin.smaller.id)
                    : "";
            g.drawString(font, capitalize(coin.id) + ratioHint,
                    px + COL_NAME, rowY + 7, C_TEXT, false);

            // Count in bag
            int count = (inv == null) ? 0 : coin.countIn(inv);
            String countStr = String.valueOf(count);
            g.drawString(font, countStr,
                    px + COL_COUNT + (28 - font.width(countStr)) / 2, rowY + 7,
                    count > 0 ? C_GOLD : 0xFF_888888, true);
        }

        // ── Vanilla button widgets ────────────────────────────────────────────
        // (handled by super.render — update enabled state first)
        updateButtonStates(inv);
        super.render(g, mx, my, delta);
    }

    private void updateButtonStates(Inventory inv) {
        if (inv == null) return;
        int rows   = COINS.length;
        int btnIdx = 0;

        // renderables contains all widgets added via addRenderableWidget in order.
        // We added: for each coin with smaller → [Break btn, Combine btn], then Close.
        for (var renderable : renderables) {
            if (!(renderable instanceof Button b)) continue;
            String lbl = b.getMessage().getString();
            if (lbl.equals("Close")) continue;

            int row = btnIdx / 2;
            if (row >= rows) break;
            GotCoin coin = COINS[rows - 1 - row];

            if (lbl.startsWith("▼")) {
                b.active = coin.smaller != null && coin.countIn(inv) >= 1;
            } else if (lbl.startsWith("▲")) {
                b.active = coin.smaller != null
                        && coin.smaller.countIn(inv) >= coin.ratio();
            }
            btnIdx++;
        }
    }

    @Override
    public void onClose() {
        PacketDistributor.sendToServer(new CloseInteractScreenPayload(entityId));
        super.onClose();
    }

    @Override
    public boolean keyPressed(int key, int scan, int mods) {
        if (key == 256) { onClose(); return true; }
        return super.keyPressed(key, scan, mods);
    }

    // Don't dim world behind this screen
    @Override
    public void renderBackground(GuiGraphics g, int mx, int my, float delta) {}

    private static String capitalize(String s) {
        return s == null || s.isEmpty() ? s
                : Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
