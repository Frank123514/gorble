package net.got.client.gui;

import net.got.network.RequestTradeMenuPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * Simple two-button screen opened by shift + right-clicking any NPC.
 *
 * <ul>
 *   <li><b>Trade</b> — sends {@link RequestTradeMenuPayload} to the server,
 *       which opens the full {@link NpcTradeScreen}.</li>
 *   <li><b>Coin Exchange</b> — opens {@link NpcCoinExchangeScreen} directly
 *       on the client (no server round-trip needed).</li>
 * </ul>
 */
public class NpcInteractScreen extends Screen {

    private static final int GUI_W = 160;
    private static final int GUI_H = 100;

    private static final int COL_BG     = 0xFF_C6C6C6;
    private static final int COL_BORDER = 0xFF_373737;
    private static final int COL_BTN    = 0xFF_5A7A9A;
    private static final int COL_BTN_H  = 0xFF_7AAACA;
    private static final int COL_BTN_D  = 0xFF_444444; // disabled
    private static final int COL_TEXT   = 0xFF_FFFFFF;
    private static final int COL_TITLE  = 0xFF_222222;

    private final int    entityId;
    private final String occupationId;
    private final String npcName;
    private final boolean employed;

    private NpcInteractScreen(int entityId, String occupationId, String npcName) {
        super(Component.empty());
        this.entityId     = entityId;
        this.occupationId = occupationId;
        this.npcName      = npcName;
        this.employed     = !occupationId.equals("none");
    }

    /** Called from {@link net.got.network.GotNetwork} on packet receipt. */
    public static void open(int entityId, String occupationId, String npcName) {
        Minecraft mc = Minecraft.getInstance();
        if (mc != null) mc.setScreen(new NpcInteractScreen(entityId, occupationId, npcName));
    }

    @Override
    public boolean isPauseScreen() { return false; }

    @Override
    public void render(GuiGraphics g, int mx, int my, float delta) {
        renderBackground(g, mx, my, delta);
        int x = (width - GUI_W) / 2, y = (height - GUI_H) / 2;

        // Chrome
        g.fill(x, y, x + GUI_W, y + GUI_H, COL_BG);
        g.fill(x, y, x + GUI_W, y + 1, COL_BORDER);
        g.fill(x, y + GUI_H - 1, x + GUI_W, y + GUI_H, COL_BORDER);
        g.fill(x, y, x + 1, y + GUI_H, COL_BORDER);
        g.fill(x + GUI_W - 1, y, x + GUI_W, y + GUI_H, COL_BORDER);

        // NPC header
        String occupation = capitalize(occupationId.equals("none") ? "Unemployed" : occupationId);
        String header = npcName.isEmpty() ? occupation : npcName + "  —  " + occupation;
        g.drawString(font, header, x + (GUI_W - font.width(header)) / 2, y + 8, COL_TITLE, false);

        // Trade button (greyed if unemployed)
        drawBtn(g, x + 20, y + 30, 120, 20, "Trade", employed, mx, my);

        // Coin Exchange button (always active)
        drawBtn(g, x + 20, y + 58, 120, 20, "Coin Exchange", true, mx, my);

        super.render(g, mx, my, delta);
    }

    @Override
    public boolean mouseClicked(double mx, double my, int btn) {
        if (btn != 0) return super.mouseClicked(mx, my, btn);
        int x = (width - GUI_W) / 2, y = (height - GUI_H) / 2;

        // Trade button
        if (employed && hit(mx, my, x + 20, y + 30, 120, 20)) {
            PacketDistributor.sendToServer(new RequestTradeMenuPayload(entityId));
            onClose();
            return true;
        }

        // Coin Exchange button
        if (hit(mx, my, x + 20, y + 58, 120, 20)) {
            Minecraft.getInstance().setScreen(new NpcCoinExchangeScreen());
            return true;
        }

        return super.mouseClicked(mx, my, btn);
    }

    @Override
    public boolean keyPressed(int key, int scan, int mods) {
        if (key == 256) { onClose(); return true; }
        return super.keyPressed(key, scan, mods);
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private void drawBtn(GuiGraphics g, int x, int y, int w, int h,
                         String label, boolean enabled, int mx, int my) {
        boolean hov = enabled && hit(mx, my, x, y, w, h);
        int col = enabled ? (hov ? COL_BTN_H : COL_BTN) : COL_BTN_D;
        g.fill(x, y, x + w, y + h, col);
        g.fill(x, y, x + w, y + 1, 0x44_FFFFFF);
        g.drawString(font, label, x + (w - font.width(label)) / 2,
                y + (h - 8) / 2, COL_TEXT, true);
    }

    private static boolean hit(double mx, double my, int x, int y, int w, int h) {
        return mx >= x && mx < x + w && my >= y && my < y + h;
    }

    private static String capitalize(String s) {
        return s == null || s.isEmpty() ? s
                : Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
