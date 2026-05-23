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
 * OvenScreen — ported from OFAW (1.16.5) to NeoForge 1.21.4.
 *
 * Renders the 3×3 oven GUI using the OFAW oven texture (copied to
 * assets/got/textures/gui/oven.png). Arrow and flame sprite coordinates
 * match the OFAW original layout.
 */
public class OvenScreen extends AbstractContainerScreen<OvenMenu> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "textures/gui/oven.png");

    public OvenScreen(OvenMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        // Standard 176×166 GUI (same as OFAW default)
        this.imageWidth  = 176;
        this.imageHeight = 166;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int x = this.leftPos;
        int y = this.topPos;

        // Draw the full GUI background
        graphics.blit(RenderType::guiTextured, TEXTURE,
                x, y, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        // ── Flame indicator (fuel remaining) ──────────────────────────────────
        // OFAW position: x+9, y+36+12-k, with sprite at (176, 12-k), size 14×(k+1)
        if (menu.isFlaming()) {
            int k = menu.getFlameScaledProgress(); // 0–13
            graphics.blit(RenderType::guiTextured, TEXTURE,
                    x + 9, y + 36 + 12 - k,
                    176, 12 - k,
                    14, k + 1, 256, 256);
        }

        // ── Progress arrow (cooking) ──────────────────────────────────────────
        // OFAW position: x+89, y+35, sprite at (176,14), width l+1, height 16
        if (menu.isCrafting()) {
            int l = menu.getArrowScaledProgress(); // 0–26
            graphics.blit(RenderType::guiTextured, TEXTURE,
                    x + 89, y + 35,
                    176, 14,
                    l + 1, 16, 256, 256);
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics, mouseX, mouseY, partialTick);
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }
}
