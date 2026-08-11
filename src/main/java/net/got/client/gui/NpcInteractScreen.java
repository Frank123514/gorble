package net.got.client.gui;

import net.got.network.CloseInteractScreenPayload;
import net.got.network.RequestTradeMenuPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * Floating interaction menu shown when the player right-clicks an NPC.
 *
 * <p><b>Civilians</b> (smallfolk with jobs) show:
 * <pre>
 *   "Jory, Mason"        ← header
 *   [Trade]  [Mason]
 *   [Exchange Coins]
 * </pre>
 *
 * <p><b>Military NPCs</b> (levies, soldiers, knights) show:
 * <pre>
 *   "Arnolf, Soldier"    ← header
 *   [Exchange Coins]
 * </pre>
 * No Trade button, no job button — fighters don't have civilian occupations.
 */
public class NpcInteractScreen extends Screen {

    private static final int BTN_W = 70;
    private static final int BTN_H = 18;
    private static final int GAP   = 4;
    private static final int PAD   = 8;

    private final int     entityId;
    private final String  occupationId;
    private final String  npcName;
    private final String  militaryTitle;  // empty = civilian

    private NpcInteractScreen(int entityId, String occupationId,
                               String npcName, String militaryTitle) {
        super(Component.empty());
        this.entityId      = entityId;
        this.occupationId  = occupationId;
        this.npcName       = npcName;
        this.militaryTitle = militaryTitle == null ? "" : militaryTitle;
    }

    /** Called from GotNetwork when OpenInteractScreenPayload arrives. */
    public static void open(int entityId, String occupationId,
                            String npcName, String militaryTitle) {
        Minecraft mc = Minecraft.getInstance();
        if (mc != null)
            mc.setScreen(new NpcInteractScreen(entityId, occupationId, npcName, militaryTitle));
    }

    private boolean isCivilian() { return militaryTitle.isEmpty(); }

    @Override public boolean isPauseScreen() { return false; }

    @Override
    protected void init() {
        super.init();

        int panelW = BTN_W * 2 + GAP + PAD * 2;
        int panelH = BTN_H * 2 + GAP + PAD * 2;
        int px     = (width  - panelW) / 2;
        int py     = (height - panelH) / 2 + 60;
        int exBtnW = BTN_W * 2 + GAP;
        int exBtnX = px + PAD;

        if (isCivilian()) {
            // ── Civilian layout ─────────────────────────────────────────────
            // Row 1: [Trade]  [<Job>]
            int row1y = py + PAD;
            int col1  = px + PAD;
            int col2  = col1 + BTN_W + GAP;

            addRenderableWidget(Button.builder(
                    Component.literal("Trade"),
                    btn -> { ClientPacketDistributor.sendToServer(new RequestTradeMenuPayload(entityId)); onClose(); }
            ).bounds(col1, row1y, BTN_W, BTN_H).build());

            String jobLabel = capitalize(occupationId.equals("none") ? "Civilian" : occupationId);
            addRenderableWidget(Button.builder(
                    Component.literal(jobLabel),
                    btn -> { ClientPacketDistributor.sendToServer(new RequestTradeMenuPayload(entityId)); onClose(); }
            ).bounds(col2, row1y, BTN_W, BTN_H).build());

            // Row 2: [Exchange Coins]
            int row2y = row1y + BTN_H + GAP;
            addRenderableWidget(Button.builder(
                    Component.literal("Exchange Coins"),
                    btn -> Minecraft.getInstance().setScreen(new NpcCoinExchangeScreen(entityId))
            ).bounds(exBtnX, row2y, exBtnW, BTN_H).build());

        } else {
            // ── Military layout ─────────────────────────────────────────────
            // Single row: [Exchange Coins] centred
            int row1y = py + PAD + (BTN_H + GAP) / 2; // vertically centred in the same panel area
            addRenderableWidget(Button.builder(
                    Component.literal("Exchange Coins"),
                    btn -> Minecraft.getInstance().setScreen(new NpcCoinExchangeScreen(entityId))
            ).bounds(exBtnX, row1y, exBtnW, BTN_H).build());
        }
    }

    @Override
    public void render(GuiGraphics g, int mx, int my, float delta) {
        int panelW = BTN_W * 2 + GAP + PAD * 2;
        int panelH = BTN_H * 2 + GAP + PAD * 2;
        int px = (width  - panelW) / 2;
        int py = (height - panelH) / 2 + 60;

        // Header: "Jory, Mason" for civilians — "Arnolf, Soldier" for military
        String label;
        if (isCivilian()) {
            String occ = occupationId.equals("none") ? "Unemployed" : capitalize(occupationId);
            label = npcName.isEmpty() ? occ : npcName + ", " + occ;
        } else {
            label = npcName.isEmpty() ? militaryTitle : npcName + ", " + militaryTitle;
        }
        g.drawCenteredString(font, label, width / 2, py - 12, 0xFF_FFFF55);

        super.render(g, mx, my, delta);
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
