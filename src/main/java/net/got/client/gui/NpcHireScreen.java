package net.got.client.gui;

import net.got.event.entity.npc.data.GotNpcOccupation;
import net.got.network.HireNpcPayload;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * A simple job-picker screen opened by sneak + right-clicking an unemployed NPC.
 *
 * <p>Shows all hireable occupations as a scrollable grid of buttons. Clicking
 * one sends a {@link HireNpcPayload} to the server and closes the screen.
 * No inventory slots — just a chooser.
 */
public class NpcHireScreen extends Screen {

    /**
     * Called from {@link net.got.network.GotNetwork} when
     * {@link net.got.network.OpenHireScreenPayload} is received.
     * Keeps the {@code Minecraft} reference inside the client-only class.
     */
    public static void open(int entityId) {
        net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
        if (mc != null) mc.setScreen(new NpcHireScreen(entityId));
    }

    private static final int GUI_W   = 200;
    private static final int GUI_H   = 180;
    private static final int COLS    = 3;
    private static final int BTN_W   = 58;
    private static final int BTN_H   = 18;
    private static final int PAD_X   = 6;
    private static final int PAD_Y   = 6;
    private static final int GAP     = 4;

    private static final int COL_BG      = 0xFF_C6C6C6;
    private static final int COL_PANEL   = 0xFF_8B8B8B;
    private static final int COL_BTN     = 0xFF_5A7A9A;
    private static final int COL_BTN_HOV = 0xFF_7AAACA;
    private static final int COL_TEXT    = 0xFF_FFFFFF;
    private static final int COL_TITLE   = 0xFF_222222;

    private final int entityId;
    private int scrollOffset = 0;

    private static final GotNpcOccupation[] JOBS = GotNpcOccupation.HIREABLE;

    /** Visible rows of buttons that fit in the panel. */
    private static final int ROWS_VISIBLE;
    static {
        int panelH = GUI_H - 28; // below title/padding
        ROWS_VISIBLE = panelH / (BTN_H + GAP);
    }

    public NpcHireScreen(int entityId) {
        super(Component.literal("Hire NPC"));
        this.entityId = entityId;
    }

    @Override
    public boolean isPauseScreen() { return false; }

    @Override
    public void render(GuiGraphics g, int mx, int my, float delta) {
        // Dim background
        renderBackground(g, mx, my, delta);

        int x = (width  - GUI_W) / 2;
        int y = (height - GUI_H) / 2;

        // Window chrome
        g.fill(x, y, x + GUI_W, y + GUI_H, COL_BG);
        g.fill(x + 1, y + 1, x + GUI_W - 1, y + GUI_H - 1, COL_PANEL);

        // Title
        g.drawString(font, "Choose a Job",
                x + (GUI_W - font.width("Choose a Job")) / 2, y + 6,
                COL_TITLE, false);

        // Job buttons
        int startRow = scrollOffset;
        int totalRows = (int) Math.ceil((double) JOBS.length / COLS);

        for (int row = 0; row < ROWS_VISIBLE; row++) {
            int jobRow = row + startRow;
            if (jobRow >= totalRows) break;
            for (int col = 0; col < COLS; col++) {
                int idx = jobRow * COLS + col;
                if (idx >= JOBS.length) break;

                GotNpcOccupation occ = JOBS[idx];
                int bx = x + PAD_X + col * (BTN_W + GAP);
                int by = y + 22    + row * (BTN_H + GAP);

                boolean hov = mx >= bx && mx < bx + BTN_W
                        && my >= by && my < by + BTN_H;
                g.fill(bx, by, bx + BTN_W, by + BTN_H,
                        hov ? COL_BTN_HOV : COL_BTN);
                g.drawString(font, occ.label,
                        bx + (BTN_W - font.width(occ.label)) / 2,
                        by + (BTN_H - 8) / 2,
                        COL_TEXT, true);
            }
        }

        // Scroll hints
        if (scrollOffset > 0) {
            g.drawString(font, "▲", x + GUI_W - 16, y + 22, COL_TITLE, false);
        }
        if (scrollOffset + ROWS_VISIBLE < totalRows) {
            g.drawString(font, "▼", x + GUI_W - 16, y + GUI_H - 18, COL_TITLE, false);
        }

        super.render(g, mx, my, delta);
    }

    @Override
    public boolean mouseClicked(double mx, double my, int btn) {
        if (btn != 0) return super.mouseClicked(mx, my, btn);

        int x = (width  - GUI_W) / 2;
        int y = (height - GUI_H) / 2;
        int totalRows = (int) Math.ceil((double) JOBS.length / COLS);

        for (int row = 0; row < ROWS_VISIBLE; row++) {
            int jobRow = row + scrollOffset;
            if (jobRow >= totalRows) break;
            for (int col = 0; col < COLS; col++) {
                int idx = jobRow * COLS + col;
                if (idx >= JOBS.length) break;

                int bx = x + PAD_X + col * (BTN_W + GAP);
                int by = y + 22    + row * (BTN_H + GAP);

                if (mx >= bx && mx < bx + BTN_W && my >= by && my < by + BTN_H) {
                    PacketDistributor.sendToServer(
                            new HireNpcPayload(entityId, JOBS[idx].id));
                    onClose();
                    return true;
                }
            }
        }
        return super.mouseClicked(mx, my, btn);
    }

    @Override
    public boolean mouseScrolled(double mx, double my, double dx, double dy) {
        int totalRows = (int) Math.ceil((double) JOBS.length / COLS);
        int max = Math.max(0, totalRows - ROWS_VISIBLE);
        if (dy < 0) scrollOffset = Math.min(scrollOffset + 1, max);
        else         scrollOffset = Math.max(scrollOffset - 1, 0);
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // Escape closes
        if (keyCode == 256) { onClose(); return true; }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
