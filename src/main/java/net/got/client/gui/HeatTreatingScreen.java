package net.got.client.gui;

import net.got.block.ForgeBlockEntity;
import net.got.menu.HeatTreatingMenu;
import net.got.network.SelectForgeModePayload;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

/**
 * HeatTreatingScreen — GUI for the Forge block's heat-treating ("Smith") mode.
 * <p>
 * Blits assets/got/textures/gui/heating.png as the full background (256×256
 * sheet, 176×166 used region). The texture bakes in four ingot slots in a
 * row, the fuel slot, and the static steam-wisp icon above it — there is no
 * separate output slot, since each ingot slot doubles as its own result
 * slot. We overlay the vanilla animated lit_progress flame sprite on top of
 * the baked steam-wisp icon, and draw a fully custom vertical temperature
 * gauge in the open panel space to the left of the ingot slots, replacing
 * the standard horizontal progress arrow.
 * <p>
 * Pixel positions are measured directly from heating.png:
 *   Ingots A-D : (53,17) (71,17) (89,17) (107,17)  — 18×18 each
 *   Fuel       : (81,53)                            — 18×18
 *   Steam icon : baked at (83,37), ~13×13 px
 * <p>
 * The temperature gauge sits at (12,20), 10px wide × 58px tall, and fills
 * from the bottom up in a blue → orange → red gradient that tracks whichever
 * ingot slot is closest to finishing.
 */
public class HeatTreatingScreen extends AbstractContainerScreen<HeatTreatingMenu> {

    private static final ResourceLocation HEATING_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/gui/heating.png");

    private static final ResourceLocation LIT_SPRITE =
            ResourceLocation.withDefaultNamespace("container/furnace/lit_progress");

    private static final int FLAME_W = 14;
    private static final int FLAME_H = 14;

    // Flame: covers the baked steam-wisp pixels above the fuel slot.
    private static final int FLAME_X = 82;
    private static final int FLAME_Y = 36;

    // ── Temperature gauge geometry (panel-relative) ───────────────────────────

    private static final int GAUGE_X      = 12;
    private static final int GAUGE_Y      = 20;
    private static final int GAUGE_W      = 10;
    private static final int GAUGE_H      = 58;
    private static final int GAUGE_BORDER = 0xFF_2B2B2B;
    private static final int GAUGE_BG     = 0xFF_1A1A1A;

    // ── Mode tab ───────────────────────────────────────────────────────────────

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
        renderTemperatureGaugeTooltip(g, mouseX, mouseY);
        renderTooltip(g, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics g, float partialTick, int mouseX, int mouseY) {
        int x = this.leftPos;
        int y = this.topPos;

        // 1. Blit the custom heating texture — panel, all slots, and the
        //    baked steam-wisp icon are all baked in.
        g.blit(RenderType::guiTextured, HEATING_TEXTURE,
                x, y, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        // 2. Animated flame — overlaid on the baked steam-wisp pixels.
        //    Only shown while fuel is actually burning.
        if (menu.isFlaming()) {
            int h = menu.getFlameProgress(); // 0–13
            if (h > 0) {
                g.blitSprite(RenderType::guiTextured, LIT_SPRITE,
                        FLAME_W, FLAME_H,
                        0, FLAME_H - h,
                        x + FLAME_X, y + FLAME_Y + FLAME_H - h,
                        FLAME_W, h);
            }
        }

        // 3. Custom vertical temperature gauge — replaces the standard
        //    horizontal progress arrow. Tracks whichever ingot slot is
        //    closest to finishing.
        renderTemperatureGauge(g, x, y, menu.getMaxHeatFraction());
    }

    /** Draws a bottom-up filling vertical gauge with a blue → orange → red gradient. */
    private void renderTemperatureGauge(GuiGraphics g, int panelX, int panelY, float fraction) {
        int gx = panelX + GAUGE_X;
        int gy = panelY + GAUGE_Y;

        // Border + empty background.
        g.fill(gx - 1, gy - 1, gx + GAUGE_W + 1, gy + GAUGE_H + 1, GAUGE_BORDER);
        g.fill(gx, gy, gx + GAUGE_W, gy + GAUGE_H, GAUGE_BG);

        int filled = Math.round(GAUGE_H * Math.max(0f, Math.min(1f, fraction)));
        if (filled <= 0) return;

        int top = gy + (GAUGE_H - filled);
        // Draw the fill one row at a time so it can shade from cool blue at
        // the bottom to white-hot at the top, regardless of how full it is.
        for (int row = 0; row < filled; row++) {
            int py = gy + GAUGE_H - 1 - row;
            float heightFrac = (row + 1) / (float) GAUGE_H; // 0 at bottom, 1 at top of full bar
            g.fill(gx, py, gx + GAUGE_W, py + 1, temperatureColor(heightFrac));
        }
        // A brighter cap row right at the surface of the fill for a "glow" edge.
        g.fill(gx, top, gx + GAUGE_W, top + 1, 0xFF_FFE9B0);
    }

    /** Blue (cool) → orange → red → white-hot (max), based on position within the gauge. */
    private int temperatureColor(float t) {
        t = Math.max(0f, Math.min(1f, t));
        int r, gg, b;
        if (t < 0.35f) {
            float f = t / 0.35f;
            r  = lerp(0x2A, 0xC8, f);
            gg = lerp(0x5C, 0x5A, f);
            b  = lerp(0xC8, 0x2A, f);
        } else if (t < 0.7f) {
            float f = (t - 0.35f) / 0.35f;
            r  = lerp(0xC8, 0xFF, f);
            gg = lerp(0x5A, 0x8A, f);
            b  = lerp(0x2A, 0x10, f);
        } else {
            float f = (t - 0.7f) / 0.3f;
            r  = lerp(0xFF, 0xFF, f);
            gg = lerp(0x8A, 0xF2, f);
            b  = lerp(0x10, 0xC0, f);
        }
        return 0xFF_000000 | (r << 16) | (gg << 8) | b;
    }

    private static int lerp(int a, int b, float f) {
        return a + Math.round((b - a) * f);
    }

    /** Shows a small tooltip with the exact heat percentage when hovering the gauge. */
    private void renderTemperatureGaugeTooltip(GuiGraphics g, int mouseX, int mouseY) {
        int gx = leftPos + GAUGE_X;
        int gy = topPos + GAUGE_Y;
        if (mouseX >= gx - 1 && mouseX < gx + GAUGE_W + 1
                && mouseY >= gy - 1 && mouseY < gy + GAUGE_H + 1) {
            int pct = Math.round(menu.getMaxHeatFraction() * 100f);
            g.renderTooltip(font,
                    List.of(Component.translatable("got.forge.heat_percent", pct).getVisualOrderText()),
                    mouseX, mouseY);
        }
    }

    /** Tab in the top-right corner that switches the Forge into Alloying mode. */
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
                PacketDistributor.sendToServer(
                        new SelectForgeModePayload(ForgeBlockEntity.MODE_ALLOYING));
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
