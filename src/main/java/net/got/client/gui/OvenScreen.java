package net.got.client.gui;

import net.got.menu.OvenMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/**
 * OvenScreen — renders the oven GUI using the vanilla furnace texture layout.
 *
 * You can replace the TEXTURE path with a custom texture once you have art.
 * The layout mirrors the vanilla furnace (176×166 GUI) so the vanilla furnace
 * texture works as a placeholder out of the box.
 */
public class OvenScreen extends AbstractContainerScreen<OvenMenu> {

    // Use the vanilla furnace texture as a placeholder.
    // Replace with ResourceLocation.fromNamespaceAndPath("got", "textures/gui/oven.png")
    // once you have a custom texture.
    private static final ResourceLocation TEXTURE =
            ResourceLocation.withDefaultNamespace("textures/gui/container/furnace.png");

    public OvenScreen(OvenMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        // Standard furnace GUI dimensions
        this.imageWidth  = 176;
        this.imageHeight = 166;
        // Move the inventory label down to match furnace layout
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int x = this.leftPos;
        int y = this.topPos;

        // Draw the GUI background.
        // MC 1.21.4: blit now requires a RenderType function as the first argument.
        graphics.blit(RenderType::guiTextured, TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        // ── Flame indicator (fuel remaining) ─────────────────────────────────
        if (menu.isLit()) {
            int flameHeight = Math.round(menu.getFuelProgress() * 13f); // max 13 px tall
            // Vanilla furnace flame: u=176, v=300 → 14×14 px sprite, drawn bottom-up
            graphics.blit(RenderType::guiTextured, TEXTURE,
                    x + 56, y + 36 + (13 - flameHeight),
                    176, 300 - flameHeight,
                    14, flameHeight + 1, 256, 256);
        }

        // ── Progress arrow (cooking) ──────────────────────────────────────────
        int arrowWidth = Math.round(menu.getCookProgress() * 24f); // max 24 px wide
        // Vanilla furnace arrow: u=176, v=14 → 24×16 px sprite
        graphics.blit(RenderType::guiTextured, TEXTURE,
                x + 79, y + 34,
                176, 14,
                arrowWidth, 16, 256, 256);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics, mouseX, mouseY, partialTick);
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }
}
