package net.got.client.gui;

import net.got.network.CloseInteractScreenPayload;
import net.got.item.Coin;
import net.got.network.CoinExchangePayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.network.PacketDistributor;

public class NpcCoinExchangeScreen extends Screen {

    private static final int GUI_W   = 260;
    private static final int ROW_H   = 22;
    private static final int ROW_Y0  = 28;
    private static final int BTN_W   = 56;
    private static final int BTN_H   = 14;
    private static final int CLOSE_W = 60;
    private static final int CLOSE_H = 20;

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

    private static final Coin[] COINS = Coin.values();

    private final int entityId;

    private int panelH;
    private int px, py;

    public NpcCoinExchangeScreen(int entityId) {
        super(Component.literal("Coin Exchange"));
        this.entityId = entityId;
    }

    @Override public boolean isPauseScreen() { return false; }

    @Override
    protected void init() {
        super.init();

        int rows    = COINS.length;
        panelH = ROW_Y0 + rows * ROW_H + ROW_H + 6;

        px = (width  - GUI_W) / 2;
        py = (height - panelH) / 2;

        for (int i = 0; i < rows; i++) {
            
            final Coin coin = COINS[rows - 1 - i];
            int rowY = py + ROW_Y0 + i * ROW_H;
            int midY = rowY + (ROW_H - BTN_H) / 2;

            if (coin.smaller != null) {
                addRenderableWidget(Button.builder(
                        Component.literal("▼ Break"),
                        btn -> ClientPacketDistributor.sendToServer(
                                new CoinExchangePayload(coin.id, true))
                ).bounds(px + COL_BREAK, midY, BTN_W, BTN_H).build());

                addRenderableWidget(Button.builder(
                        Component.literal("▲ Combine"),
                        btn -> ClientPacketDistributor.sendToServer(
                                new CoinExchangePayload(coin.id, false))
                ).bounds(px + COL_COMB, midY, BTN_W, BTN_H).build());
            }
        }

        addRenderableWidget(Button.builder(
                Component.literal("Close"),
                btn -> onClose()
        ).bounds(px + (GUI_W - CLOSE_W) / 2,
                  py + panelH - ROW_H - 4,
                  CLOSE_W, CLOSE_H).build());
    }

    @Override
    public void render(GuiGraphics g, int mx, int my, float delta) {
        
        g.fill(px - 1, py - 1, px + GUI_W + 1, py + panelH + 1, C_BORDER);
        g.fill(px, py, px + GUI_W, py + panelH, C_BG);
        g.fill(px, py, px + GUI_W, py + 1, C_HILITE);
        g.fill(px, py, px + 1, py + panelH, C_HILITE);
        g.fill(px, py + panelH - 1, px + GUI_W, py + panelH, C_SHADOW);
        g.fill(px + GUI_W - 1, py, px + GUI_W, py + panelH, C_SHADOW);

        g.drawCenteredString(font, "Coin Exchange", px + GUI_W / 2, py + 8, C_TEXT);

        int hdrY = py + ROW_Y0 - 10;
        g.drawString(font, "Coin",    px + COL_NAME,  hdrY, C_TEXT, false);
        g.drawString(font, "In Bag",  px + COL_COUNT, hdrY, C_TEXT, false);
        
        g.fill(px + 4, py + ROW_Y0 - 2, px + GUI_W - 4, py + ROW_Y0 - 1, C_SHADOW);

        Inventory inv = Minecraft.getInstance().player == null
                ? null : Minecraft.getInstance().player.getInventory();

        int rows = COINS.length;
        for (int i = 0; i < rows; i++) {
            Coin coin = COINS[rows - 1 - i];
            int rowY = py + ROW_Y0 + i * ROW_H;

            if (i % 2 == 0)
                g.fill(px + 4, rowY, px + GUI_W - 4, rowY + ROW_H - 1, 0x18_000000);

            g.renderItem(new ItemStack(coin.item()), px + COL_ICON, rowY + 3);

            String ratioHint = coin.smaller != null
                    ? " = " + coin.ratio() + " " + capitalize(coin.smaller.id)
                    : "";
            g.drawString(font, capitalize(coin.id) + ratioHint,
                    px + COL_NAME, rowY + 7, C_TEXT, false);

            int count = (inv == null) ? 0 : coin.countIn(inv);
            String countStr = String.valueOf(count);
            g.drawString(font, countStr,
                    px + COL_COUNT + (28 - font.width(countStr)) / 2, rowY + 7,
                    count > 0 ? C_GOLD : 0xFF_888888, true);
        }

        updateButtonStates(inv);
        super.render(g, mx, my, delta);
    }

    private void updateButtonStates(Inventory inv) {
        if (inv == null) return;
        int rows   = COINS.length;
        int btnIdx = 0;

        for (var renderable : renderables) {
            if (!(renderable instanceof Button b)) continue;
            String lbl = b.getMessage().getString();
            if (lbl.equals("Close")) continue;

            int row = btnIdx / 2;
            if (row >= rows) break;
            Coin coin = COINS[rows - 1 - row];

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
        ClientPacketDistributor.sendToServer(new CloseInteractScreenPayload(entityId));
        super.onClose();
    }

    @Override
    public boolean keyPressed(net.minecraft.client.input.KeyEvent __event){
        if (__event.key() == 256) { onClose(); return true; }
        return super.keyPressed(__event);
    }

    @Override
    public void renderBackground(GuiGraphics g, int mx, int my, float delta) {}

    private static String capitalize(String s) {
        return s == null || s.isEmpty() ? s
                : Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
