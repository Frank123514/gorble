package net.got.client.gui;

import net.got.GotMod;
import net.got.menu.OvenMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/**
 * GUI for the Oven, ported from OFAW's OvenScreen.
 *
 * Renders the background texture, a cooking-progress arrow, and a fuel-level
 * flame — matching OFAW's visual layout exactly.
 *
 * Expected UV layout of oven.png (256×256):
 *   0,0  → 176×166 : main GUI background
 *   176,0  →  14×13 : flame sprite (full height)
 *   176,14 →  27×16 : arrow sprite (full width)
 */
public class OvenScreen extends AbstractContainerScreen<OvenMenu> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "textures/gui/oven.png");

    public OvenScreen(OvenMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth       = 176;
        this.imageHeight      = 166;
        this.inventoryLabelY  = this.imageHeight - 94;
        this.titleLabelX      = 8;
        this.titleLabelY      = 4;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int x = (this.width  - this.imageWidth)  / 2;
        int y = (this.height - this.imageHeight) / 2;

        // Draw the main background
        graphics.blit(RenderType::guiTextured, TEXTURE,
                x, y, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        // Draw the cooking-progress arrow (right-pointing, 26px wide)
        renderProgressArrow(graphics, x, y);

        // Draw the fuel-level flame (14px wide, 13px tall, fills bottom-to-top)
        renderProgressFlame(graphics, x, y);
    }

    /**
     * Renders the right-pointing cooking arrow, growing left→right as cooking progresses.
     * Source UV matches OFAW: (176, 14), width up to 27, height 16.
     */
    private void renderProgressArrow(GuiGraphics graphics, int x, int y) {
        if (menu.isCrafting()) {
            int progress = menu.getArrowScaledProgress(); // 0-26
            graphics.blit(RenderType::guiTextured, TEXTURE,
                    x + 89, y + 35,   // destination (screen position, matches OFAW)
                    176, 14,           // source UV
                    progress + 1, 16, // size
                    256, 256);
        }
    }

    /**
     * Renders the fuel flame, shrinking top→bottom as fuel depletes.
     * Source UV matches OFAW: (176, 12-k), height k+1.
     */
    private void renderProgressFlame(GuiGraphics graphics, int x, int y) {
        if (menu.isFlaming()) {
            int k = menu.getFlameScaledProgress(); // 0-13
            graphics.blit(RenderType::guiTextured, TEXTURE,
                    x + 9, y + 36 + 12 - k, // destination (grows upward)
                    176, 12 - k,              // source UV
                    14, k + 1,                // size
                    256, 256);
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics, mouseX, mouseY, partialTick);
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }
}
