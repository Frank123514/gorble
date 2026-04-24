package net.got.client.gui;

import net.got.network.CloseInteractScreenPayload;
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

    private static final int BTN_W  = 70;
    private static final int BTN_H  = 18;
    private static final int GAP    = 4;
    private static final int PAD    = 8;

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

        // Panel origin — centred, pushed further down so the NPC body is clearly visible
        int panelW = BTN_W * 2 + GAP + PAD * 2;
        int panelH = BTN_H * 2 + GAP + PAD * 2;
        int px = (width  - panelW) / 2;
        int py = (height - panelH) / 2 + 60;

        // Row 1: [Trade]  [<Occupation>] — centred in the panel
        int row1y = py + PAD;
        int col1  = px + PAD;
        int col2  = col1 + BTN_W + GAP;

        // Row 2 centred
        int row2y  = row1y + BTN_H + GAP;
        int exBtnW = BTN_W * 2 + GAP;
        int exBtnX = px + PAD;

        // Trade — requests the server to open the NpcTradeMenu
        addRenderableWidget(Button.builder(
                Component.literal("Trade"),
                btn -> {
                    PacketDistributor.sendToServer(new RequestTradeMenuPayload(entityId));
                    onClose();
                }
        ).bounds(col1, row1y, BTN_W, BTN_H).build());

        // Occupation button — shows job label
        String jobLabel = capitalize(occupationId.equals("none") ? "Civilian" : occupationId);
        addRenderableWidget(Button.builder(
                Component.literal(jobLabel),
                btn -> {
                    PacketDistributor.sendToServer(new RequestTradeMenuPayload(entityId));
                    onClose();
                }
        ).bounds(col2, row1y, BTN_W, BTN_H).build());

        // Exchange Coins — opens coin exchange screen (NPC stays frozen via same entityId)
        addRenderableWidget(Button.builder(
                Component.literal("Exchange Coins"),
                btn -> Minecraft.getInstance().setScreen(new NpcCoinExchangeScreen(entityId))
        ).bounds(exBtnX, row2y, exBtnW, BTN_H).build());
    }

    @Override
    public void render(GuiGraphics g, int mx, int my, float delta) {
        // ── No panel background — let the game world show through fully ─────
        int panelW = BTN_W * 2 + GAP + PAD * 2;
        int panelH = BTN_H * 2 + GAP + PAD * 2;
        int px = (width  - panelW) / 2;
        int py = (height - panelH) / 2 + 60;

        // ── NPC name + occupation above panel ────────────────────────────────
        String occ  = occupationId.equals("none") ? "Unemployed" : capitalize(occupationId);
        String header = npcName.isEmpty() ? occ : npcName + ", " + occ;
        g.drawCenteredString(font, header, width / 2, py - 12, 0xFF_FFFF55);

        // ── Vanilla button widgets ────────────────────────────────────────────
        super.render(g, mx, my, delta);
    }

    @Override
    public void onClose() {
        // Tell the server to stop the NPC's talking animation
        PacketDistributor.sendToServer(new CloseInteractScreenPayload(entityId));
        super.onClose();
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
