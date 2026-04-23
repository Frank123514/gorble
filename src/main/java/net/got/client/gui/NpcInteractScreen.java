package net.got.client.gui;

import net.got.network.RequestTradeMenuPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * Floating interaction menu shown when the player shift-right-clicks an NPC.
 * The game world stays visible and running behind this screen.
 *
 * Layout (matches image style):
 *   - NPC name + occupation header above the button panel
 *   - Row 1: [Talk]  [Trade]  [<Occupation>]
 *   - Row 2: [Exchange Coins]
 *
 * Uses vanilla Button widgets throughout so hover/press states are automatic.
 */
public class NpcInteractScreen extends Screen {

    private static final int BTN_W  = 80;
    private static final int BTN_H  = 20;
    private static final int GAP    = 4;
    private static final int PAD    = 10;

    private final int    entityId;
    private final String occupationId;
    private final String npcName;

    private NpcInteractScreen(int entityId, String occupationId, String npcName) {
        super(Component.empty());
        this.entityId     = entityId;
        this.occupationId = occupationId;
        this.npcName      = npcName;
    }

    /** Called from GotNetwork when OpenInteractScreenPayload is received. */
    public static void open(int entityId, String occupationId, String npcName) {
        Minecraft mc = Minecraft.getInstance();
        if (mc != null) mc.setScreen(new NpcInteractScreen(entityId, occupationId, npcName));
    }

    @Override public boolean isPauseScreen() { return false; }

    @Override
    protected void init() {
        super.init();

        // Panel origin — centred, slightly below screen midpoint so NPC shows above
        int panelW = BTN_W * 3 + GAP * 2 + PAD * 2;
        int panelH = BTN_H * 2 + GAP + PAD * 2;
        int px = (width  - panelW) / 2;
        int py = (height - panelH) / 2 + 30;

        // Row 1 left-edge of first button
        int row1y = py + PAD;
        int col1  = px + PAD;
        int col2  = col1 + BTN_W + GAP;
        int col3  = col2 + BTN_W + GAP;

        // Row 2 centred
        int row2y  = row1y + BTN_H + GAP;
        int exBtnW = 120;
        int exBtnX = px + (panelW - exBtnW) / 2;

        // Talk — just closes the screen; regular right-click handles talking
        addRenderableWidget(Button.builder(
                Component.literal("Talk"),
                btn -> onClose()
        ).bounds(col1, row1y, BTN_W, BTN_H).build());

        // Trade — requests the server to open the NpcTradeMenu
        addRenderableWidget(Button.builder(
                Component.literal("Trade"),
                btn -> {
                    PacketDistributor.sendToServer(new RequestTradeMenuPayload(entityId));
                    onClose();
                }
        ).bounds(col2, row1y, BTN_W, BTN_H).build());

        // Occupation button — same action as Trade for now; shows job label
        String jobLabel = capitalize(occupationId.equals("none") ? "Civilian" : occupationId);
        addRenderableWidget(Button.builder(
                Component.literal(jobLabel),
                btn -> {
                    PacketDistributor.sendToServer(new RequestTradeMenuPayload(entityId));
                    onClose();
                }
        ).bounds(col3, row1y, BTN_W, BTN_H).build());

        // Exchange Coins — opens coin exchange screen
        addRenderableWidget(Button.builder(
                Component.literal("Exchange Coins"),
                btn -> Minecraft.getInstance().setScreen(new NpcCoinExchangeScreen())
        ).bounds(exBtnX, row2y, exBtnW, BTN_H).build());
    }

    @Override
    public void render(GuiGraphics g, int mx, int my, float delta) {
        // ── Semi-transparent panel behind buttons ─────────────────────────────
        int panelW = BTN_W * 3 + GAP * 2 + PAD * 2;
        int panelH = BTN_H * 2 + GAP + PAD * 2;
        int px = (width  - panelW) / 2;
        int py = (height - panelH) / 2 + 30;

        // Outer border
        g.fill(px - 1, py - 1, px + panelW + 1, py + panelH + 1, 0xFF_000000);
        // Panel background (vanilla inventory tone)
        g.fill(px, py, px + panelW, py + panelH, 0xFF_C6C6C6);
        // Top-left highlight
        g.fill(px, py, px + panelW, py + 1, 0xFF_FFFFFF);
        g.fill(px, py, px + 1, py + panelH, 0xFF_FFFFFF);
        // Bottom-right shadow
        g.fill(px, py + panelH - 1, px + panelW, py + panelH, 0xFF_555555);
        g.fill(px + panelW - 1, py, px + panelW, py + panelH, 0xFF_555555);

        // ── NPC name + occupation above panel ────────────────────────────────
        String occ  = occupationId.equals("none") ? "Unemployed" : capitalize(occupationId);
        String header = npcName.isEmpty() ? occ : npcName + ", " + occ;
        g.drawCenteredString(font, header, width / 2, py - 12, 0xFF_FFFF55);

        // ── Vanilla button widgets ────────────────────────────────────────────
        super.render(g, mx, my, delta);
    }

    @Override
    public boolean keyPressed(int key, int scan, int mods) {
        if (key == 256) { onClose(); return true; }
        return super.keyPressed(key, scan, mods);
    }

    // Do NOT call renderBackground — let the game world show through
    @Override
    public void renderBackground(GuiGraphics g, int mx, int my, float delta) {}

    private static String capitalize(String s) {
        return s == null || s.isEmpty() ? s
                : Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
