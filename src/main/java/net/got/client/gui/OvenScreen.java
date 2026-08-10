package net.got.client.gui;
import net.got.menu.OvenMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
/**
 * OvenScreen — ported from OFAW (1.16.5) to NeoForge 1.21.4.
 *
 * Renders the 3×3 oven GUI using the OFAW oven texture (copied to
 * assets/got/textures/gui/oven.png). Arrow and flame sprite coordinates
 * match the OFAW original layout.
 */
public class OvenScreen extends AbstractContainerScreen<OvenMenu> {
    private static final Identifier CRAFTING_TABLE_LOCATION = Identifier.withDefaultNamespace("textures/gui/container/crafting_table.png");
    private static final Identifier LIT_PROGRESS_SPRITE = Identifier.withDefaultNamespace("container/furnace/lit_progress");
    private static final Identifier BURN_PROGRESS_SPRITE = Identifier.withDefaultNamespace("container/furnace/burn_progress");
    public OvenScreen(OvenMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        this.imageWidth  = 176;
        this.imageHeight = 166;
        this.inventoryLabelY = this.imageHeight - 94;
    }
    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int x = this.leftPos;
        int y = this.topPos;
        // Draw the crafting table background (this gives us the 3x3 grid, inventory, and arrow)
        graphics.blit(RenderPipelines.GUI_TEXTURED, CRAFTING_TABLE_LOCATION,
                x, y, 0, 0, this.imageWidth, this.imageHeight, 256, 256);
        // Draw a single slot background for our fuel slot at (8, 53)
        // We just grab the texture of one of the crafting slots
        graphics.blit(RenderPipelines.GUI_TEXTURED, CRAFTING_TABLE_LOCATION,
                x + 8 - 1, y + 53 - 1, 29, 16, 18, 18, 256, 256);
        // Draw Flame indicator (fuel remaining) above the fuel slot
        if (menu.isFlaming()) {
            int k = menu.getFlameScaledProgress(); // 0-13
            if (k > 0) {
                graphics.blitSprite(RenderPipelines.GUI_TEXTURED, LIT_PROGRESS_SPRITE,
                        14, 14, 0, 14 - k,
                        x + 10, y + 36 + 14 - k,
                        14, k);
            }
        }
        // Draw Progress arrow (cooking) over the crafting table's static arrow
        if (menu.isCrafting()) {
            int l = menu.getArrowScaledProgress(); // 0-24
            if (l > 0) {
                graphics.blitSprite(RenderPipelines.GUI_TEXTURED, BURN_PROGRESS_SPRITE,
                        24, 16, 0, 0,
                        x + 90, y + 35,
                        l, 16);
            }
        }
    }
    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics, mouseX, mouseY, partialTick);
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }
}
