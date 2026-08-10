package net.got.client.gui;

import net.minecraft.client.renderer.RenderPipelines;
import net.got.block.ForgeBlockEntity;
import net.got.menu.HeatTreatingMenu;
import net.got.network.SelectForgeModePayload;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import java.util.List;

public class HeatTreatingScreen extends AbstractContainerScreen<HeatTreatingMenu> {

    private static final Identifier HEATING_TEXTURE =
            Identifier.fromNamespaceAndPath("got", "textures/gui/heating.png");

    private static final Identifier LIT_SPRITE =
            Identifier.withDefaultNamespace("container/furnace/lit_progress");

    private static final int FLAME_W = 14;
    private static final int FLAME_H = 14;
    private static final int FLAME_X = 81;
    private static final int FLAME_Y = 36;

    // Exact pixel spans of the grey flame silhouette in heating.png, row by row.
    // Index 0 = y=14 (tip), last = y=68 (base). Each int[] is {left_x, right_x} inclusive.
    // Multiple spans per row where the shape has gaps.
    private static final int[][][] SILHOUETTE = {
            {{18,19}},  // y=14
            {{18,20}},  // y=15
            {{18,21}},  // y=16
            {{19,22}},  // y=17
            {{19,23}},  // y=18
            {{19,24}},  // y=19
            {{19,24}},  // y=20
            {{20,25}},  // y=21
            {{20,25}},  // y=22
            {{20,25}},  // y=23
            {{20,26}},  // y=24
            {{20,26}},  // y=25
            {{19,26}},  // y=26
            {{19,26}},  // y=27
            {{19,26}},  // y=28
            {{18,26}},  // y=29
            {{18,26}},  // y=30
            {{17,26}},  // y=31
            {{16,25}},  // y=32
            {{16,25}},  // y=33
            {{15,24}},  // y=34
            {{10,10}, {15,24}},  // y=35
            {{ 9,10}, {14,23}},  // y=36
            {{ 8, 9}, {14,23}, {32,33}},  // y=37
            {{ 8, 9}, {13,23}, {30,32}},  // y=38
            {{ 7,10}, {13,22}, {28,31}},  // y=39
            {{ 7,10}, {13,22}, {27,30}},  // y=40
            {{ 7, 9}, {13,22}, {26,29}},  // y=41
            {{ 6, 9}, {14,23}, {25,29}},  // y=42
            {{ 6,10}, {14,28}},  // y=43
            {{ 6,11}, {15,27}},  // y=44
            {{ 7,13}, {15,27}},  // y=45
            {{ 7,27}},  // y=46
            {{ 8,26}},  // y=47
            {{ 8,26}, {30,30}},  // y=48
            {{ 9,26}, {30,31}},  // y=49
            {{10,26}, {30,32}},  // y=50
            {{11,26}, {30,32}},  // y=51
            {{12,27}, {29,33}},  // y=52
            {{13,27}, {29,33}},  // y=53
            {{13,33}},  // y=54
            {{14,33}},  // y=55
            {{11,11}, {14,33}},  // y=56
            {{11,12}, {15,33}},  // y=57
            {{10,12}, {15,33}},  // y=58
            {{10,13}, {16,32}},  // y=59
            {{10,32}},  // y=60
            {{11,32}},  // y=61
            {{11,31}},  // y=62
            {{12,31}},  // y=63
            {{13,30}},  // y=64
            {{14,30}},  // y=65
            {{16,29}},  // y=66
            {{17,27}},  // y=67
            {{20,25}},  // y=68
    };

    private static final int SILHOUETTE_TOP_Y = 14;
    private static final int SILHOUETTE_H     = 55;

    private static final int TAB_W = 20;
    private static final int TAB_H = 14;
    private static final int TAB_X = 176 - TAB_W - 2;
    private static final int TAB_Y = 2;
    private static final int C_TEXT = 0xFF_404040;

    public HeatTreatingScreen(HeatTreatingMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        this.imageWidth      = 176;
        this.imageHeight     = 166;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void renderLabels(GuiGraphics g, int mouseX, int mouseY) {
        g.drawString(font, this.title,                8, 6,                     C_TEXT, false);
        g.drawString(font, this.playerInventoryTitle, 8, this.imageHeight - 94, C_TEXT, false);
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        renderBackground(g, mouseX, mouseY, partialTick);
        super.render(g, mouseX, mouseY, partialTick);
        renderModeTab(g, mouseX, mouseY);
        renderFlameTooltip(g, mouseX, mouseY);
        renderTooltip(g, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics g, float partialTick, int mouseX, int mouseY) {
        int x = this.leftPos;
        int y = this.topPos;

        g.blit(RenderPipelines.GUI_TEXTURED, HEATING_TEXTURE,
                x, y, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        renderFlameFill(g, x, y, menu.getMaxHeatFraction());

        if (menu.isFlaming()) {
            int h = menu.getFlameProgress();
            if (h > 0) {
                g.blitSprite(RenderPipelines.GUI_TEXTURED, LIT_SPRITE,
                        FLAME_W, FLAME_H,
                        0, FLAME_H - h,
                        x + FLAME_X, y + FLAME_Y + FLAME_H - h,
                        FLAME_W, h);
            }
        }
    }

    /**
     * Fills the baked grey flame silhouette bottom-up by painting exactly the
     * grey pixels row by row. Only the actual silhouette spans are coloured —
     * gaps in the shape stay as background.
     */
    private void renderFlameFill(GuiGraphics g, int panelX, int panelY, float fraction) {
        if (fraction <= 0f) return;
        int filled = Math.round(SILHOUETTE_H * Math.max(0f, Math.min(1f, fraction)));
        if (filled == 0) return;

        // filled rows from the bottom; SILHOUETTE[0]=tip, SILHOUETTE[last]=base
        int startRow = SILHOUETTE_H - filled;

        for (int i = startRow; i < SILHOUETTE_H; i++) {
            int screenY = panelY + SILHOUETTE_TOP_Y + i;
            // heightFrac: 0.0 at base, 1.0 at tip
            float heightFrac = (float)(SILHOUETTE_H - 1 - i) / (float)(filled - 1 + 1);
            int color = flameColor(heightFrac);

            for (int[] span : SILHOUETTE[i]) {
                g.fill(panelX + span[0], screenY, panelX + span[1] + 1, screenY + 1, color);
            }
        }
    }

    /** dark red (base) → orange → yellow → near-white (tip) */
    private int flameColor(float t) {
        t = Math.max(0f, Math.min(1f, t));
        int r, gr, b;
        if (t < 0.35f) {
            float f = t / 0.35f;
            r  = lerp(0x8B, 0xFF, f);
            gr = lerp(0x00, 0x60, f);
            b  = 0;
        } else if (t < 0.7f) {
            float f = (t - 0.35f) / 0.35f;
            r  = 0xFF;
            gr = lerp(0x60, 0xE0, f);
            b  = 0;
        } else {
            float f = (t - 0.7f) / 0.3f;
            r  = 0xFF;
            gr = lerp(0xE0, 0xFF, f);
            b  = lerp(0x00, 0xC0, f);
        }
        return 0xFF_000000 | (r << 16) | (gr << 8) | b;
    }

    private static int lerp(int a, int b, float f) {
        return a + Math.round((b - a) * f);
    }

    private void renderFlameTooltip(GuiGraphics g, int mouseX, int mouseY) {
        int bx = leftPos + 6;
        int by = topPos + SILHOUETTE_TOP_Y;
        if (mouseX >= bx && mouseX < bx + 34 && mouseY >= by && mouseY < by + SILHOUETTE_H) {
            int pct = Math.round(menu.getMaxHeatFraction() * 100f);
            g.setTooltipForNextFrame(font,
                    List.of(Component.translatable("got.forge.heat_percent", pct).getVisualOrderText()),
                    mouseX, mouseY);
        }
    }

    private void renderModeTab(GuiGraphics g, int mouseX, int mouseY) {
        int bx = leftPos + TAB_X, by = topPos + TAB_Y;
        boolean hovered = mouseX >= bx && mouseX < bx + TAB_W && mouseY >= by && mouseY < by + TAB_H;
        g.fill(bx, by, bx + TAB_W, by + TAB_H, hovered ? 0xFFB0B0B0 : 0xFF8B8B8B);
        g.renderOutline(bx, by, TAB_W, TAB_H, 0xFF373737);
        g.drawCenteredString(font, Component.translatable("got.forge.tab.alloy"),
                bx + TAB_W / 2, by + 3, C_TEXT);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            int mx = (int) mouseX, my = (int) mouseY;
            int tbx = leftPos + TAB_X, tby = topPos + TAB_Y;
            if (mx >= tbx && mx < tbx + TAB_W && my >= tby && my < tby + TAB_H) {
                ClientPacketDistributor.sendToServer(
                        new SelectForgeModePayload(ForgeBlockEntity.MODE_ALLOYING));
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}