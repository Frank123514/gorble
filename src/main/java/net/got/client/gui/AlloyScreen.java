package net.got.client.gui;

import net.got.menu.AlloyMenu;
import net.got.network.SelectForgeModePayload;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * AlloyScreen — furnace-style GUI for the Forge block's alloying mode.
 *
 * Uses the generic inventory background (no baked-in slots) so we can place
 * everything ourselves cleanly.
 *
 * Layout (panel-relative):
 *   [A][B][C][D]  — 4 input slots in a row, y=17
 *       flame     — below slot B, y=36
 *       [fuel]    — below flame, y=53
 *       ----→     — progress arrow, x=97, y=35
 *       [output]  — result slot, x=127, y=35
 */
public class AlloyScreen extends AbstractContainerScreen<AlloyMenu> {



    private static final ResourceLocation LIT_SPRITE =
            ResourceLocation.withDefaultNamespace("container/furnace/lit_progress");
    private static final ResourceLocation ARROW_SPRITE =
            ResourceLocation.withDefaultNamespace("container/furnace/burn_progress");

    private static final int FLAME_SPRITE_W = 14;
    private static final int FLAME_SPRITE_H = 14;
    private static final int ARROW_SPRITE_W = 24;
    private static final int ARROW_SPRITE_H = 16;

    // All positions panel-relative
    private static final int FLAME_X      = 35;
    private static final int FLAME_Y      = 36;
    private static final int ARROW_X      = 97;
    private static final int ARROW_Y      = 35;

    private static final int C_SLOT_BG = 0xFF_8B8B8B;
    private static final int C_LT      = 0xFF_FFFFFF;
    private static final int C_DK      = 0xFF_555555;
    private static final int C_TEXT    = 0xFF_404040;
    private static final int C_PANEL   = 0xFF_C6C6C6;

    private static final int TAB_W = 20;
    private static final int TAB_H = 14;
    private static final int TAB_X = 176 - TAB_W - 2;
    private static final int TAB_Y = 2;

    public AlloyScreen(AlloyMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        this.imageWidth      = 176;
        this.imageHeight     = 166;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void renderLabels(GuiGraphics g, int mouseX, int mouseY) {
        g.drawString(font, this.title,                8, 6,                    C_TEXT, false);
        g.drawString(font, this.playerInventoryTitle, 8, this.imageHeight - 94, C_TEXT, false);
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        renderBackground(g, mouseX, mouseY, partialTick);
        super.render(g, mouseX, mouseY, partialTick);
        renderModeTab(g, mouseX, mouseY);
        renderTooltip(g, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics g, float partialTick, int mouseX, int mouseY) {
        int x = this.leftPos;
        int y = this.topPos;

        // Plain panel background — drawn entirely in code, no texture
        g.fill(x, y, x + this.imageWidth, y + this.imageHeight, 0xFF_C6C6C6);
        // Border
        g.fill(x, y,                          x + this.imageWidth, y + 1,                C_DK);
        g.fill(x, y,                          x + 1,               y + this.imageHeight, C_DK);
        g.fill(x, y + this.imageHeight - 1,   x + this.imageWidth, y + this.imageHeight, C_LT);
        g.fill(x + this.imageWidth - 1, y,    x + this.imageWidth, y + this.imageHeight, C_LT);

        // 4 input slots in a row
        vanillaSlot(g, x + AlloyMenu.INPUT_A_X - 1, y + AlloyMenu.INPUT_A_Y - 1);
        vanillaSlot(g, x + AlloyMenu.INPUT_B_X - 1, y + AlloyMenu.INPUT_B_Y - 1);
        vanillaSlot(g, x + AlloyMenu.INPUT_C_X - 1, y + AlloyMenu.INPUT_C_Y - 1);
        vanillaSlot(g, x + AlloyMenu.INPUT_D_X - 1, y + AlloyMenu.INPUT_D_Y - 1);

        // Fuel slot
        vanillaSlot(g, x + AlloyMenu.FUEL_X - 1, y + AlloyMenu.FUEL_Y - 1);

        // Output slot
        vanillaSlot(g, x + AlloyMenu.OUTPUT_X - 1, y + AlloyMenu.OUTPUT_Y - 1);

        // Flame — always draw the empty sprite so the icon is always visible
        g.blitSprite(RenderType::guiTextured, LIT_SPRITE,
                FLAME_SPRITE_W, FLAME_SPRITE_H, 0, 0,
                x + FLAME_X, y + FLAME_Y, FLAME_SPRITE_W, FLAME_SPRITE_H);
        if (menu.isFlaming()) {
            int h = menu.getFlameProgress();
            if (h > 0)
                g.blitSprite(RenderType::guiTextured, LIT_SPRITE,
                        FLAME_SPRITE_W, FLAME_SPRITE_H,
                        0, FLAME_SPRITE_H - h,
                        x + FLAME_X, y + FLAME_Y + FLAME_SPRITE_H - h,
                        FLAME_SPRITE_W, h);
        }

        // Arrow — always draw the empty sprite
        g.blitSprite(RenderType::guiTextured, ARROW_SPRITE,
                ARROW_SPRITE_W, ARROW_SPRITE_H, 0, 0,
                x + ARROW_X, y + ARROW_Y, ARROW_SPRITE_W, ARROW_SPRITE_H);
        if (menu.isCrafting()) {
            int w = menu.getArrowProgress();
            if (w > 0)
                g.blitSprite(RenderType::guiTextured, ARROW_SPRITE,
                        ARROW_SPRITE_W, ARROW_SPRITE_H, 0, 0,
                        x + ARROW_X, y + ARROW_Y, w, ARROW_SPRITE_H);
        }
    }

    private void renderModeTab(GuiGraphics g, int mouseX, int mouseY) {
        int bx = leftPos + TAB_X, by = topPos + TAB_Y;
        boolean hovered = mouseX >= bx && mouseX < bx + TAB_W && mouseY >= by && mouseY < by + TAB_H;
        g.fill(bx, by, bx + TAB_W, by + TAB_H, hovered ? 0xFFB0B0B0 : 0xFF8B8B8B);
        g.renderOutline(bx, by, TAB_W, TAB_H, 0xFF373737);
        g.drawCenteredString(font, Component.translatable("got.forge.tab.smith"),
                bx + TAB_W / 2, by + 3, C_TEXT);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            int mx = (int) mouseX, my = (int) mouseY;
            int tbx = leftPos + TAB_X, tby = topPos + TAB_Y;
            if (mx >= tbx && mx < tbx + TAB_W && my >= tby && my < tby + TAB_H) {
                PacketDistributor.sendToServer(
                        new SelectForgeModePayload(net.got.block.ForgeBlockEntity.MODE_HEAT_TREATING));
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void vanillaSlot(GuiGraphics g, int x, int y) {
        g.fill(x,      y,      x + 18, y + 1,  C_DK);
        g.fill(x,      y,      x + 1,  y + 18, C_DK);
        g.fill(x,      y + 17, x + 18, y + 18, C_LT);
        g.fill(x + 17, y,      x + 18, y + 18, C_LT);
        g.fill(x + 1,  y + 1,  x + 17, y + 17, C_SLOT_BG);
    }
}