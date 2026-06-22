package net.got.client.gui;

import net.got.block.ForgeBlockEntity;
import net.got.menu.AlloyMenu;
import net.got.menu.HeatTreatingMenu;
import net.got.network.SelectForgeModePayload;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * HeatTreatingScreen — the GUI for the Forge in heat-treating mode.
 * <p>
 * A clean furnace-style interface: raw metal goes in the top slot, fuel in the
 * bottom-left slot, and heated (malleable) metal comes out the right slot.
 * The heated output can then be taken to a Smithing Anvil to be worked into
 * finished items.
 * <p>
 * A tab in the corner switches to the Alloying mode.
 */
public class HeatTreatingScreen extends AbstractContainerScreen<HeatTreatingMenu> {

    private static final ResourceLocation FURNACE_TEXTURE =
            ResourceLocation.withDefaultNamespace("textures/gui/container/furnace.png");

    private static final ResourceLocation LIT_SPRITE =
            ResourceLocation.withDefaultNamespace("container/furnace/lit_progress");
    private static final ResourceLocation ARROW_SPRITE =
            ResourceLocation.withDefaultNamespace("container/furnace/burn_progress");

    private static final int FLAME_SPRITE_W = 14;
    private static final int FLAME_SPRITE_H = 14;
    private static final int ARROW_SPRITE_W = 24;
    private static final int ARROW_SPRITE_H = 16;

    // Flame sits between input (y=17) and fuel (y=53); centered ~y=36
    private static final int FLAME_X = 56;
    private static final int FLAME_Y = 36;

    // Arrow sits between fuel area and output slot
    private static final int ARROW_X = 79;
    private static final int ARROW_Y = 34;

    // Mode tab — top-right corner of the panel, switches to Alloying
    private static final int TAB_W = 20;
    private static final int TAB_H = 14;
    private static final int TAB_X = 176 - TAB_W - 2;
    private static final int TAB_Y = 2;

    private static final int C_SLOT_BG = 0xFF_8B8B8B;
    private static final int C_LT      = 0xFF_FFFFFF;
    private static final int C_DK      = 0xFF_555555;
    private static final int C_TEXT    = 0xFF_404040;

    public HeatTreatingScreen(HeatTreatingMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        this.imageWidth      = 176;
        this.imageHeight     = 166;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void renderLabels(GuiGraphics g, int mouseX, int mouseY) {
        g.drawString(font, this.title,               8, 6,                    C_TEXT, false);
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

        // Use the vanilla furnace background — gives us the standard slot layout
        g.blit(RenderType::guiTextured, FURNACE_TEXTURE,
                x, y, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        // Flame indicator
        if (menu.isFlaming()) {
            int flameHeight = menu.getFlameProgress();
            if (flameHeight > 0) {
                g.blitSprite(RenderType::guiTextured, LIT_SPRITE,
                        FLAME_SPRITE_W, FLAME_SPRITE_H,
                        0, FLAME_SPRITE_H - flameHeight,
                        x + FLAME_X, y + FLAME_Y + FLAME_SPRITE_H - flameHeight,
                        FLAME_SPRITE_W, flameHeight);
            }
        }

        // Progress arrow
        if (menu.isCrafting()) {
            int arrowWidth = menu.getArrowProgress();
            if (arrowWidth > 0) {
                g.blitSprite(RenderType::guiTextured, ARROW_SPRITE,
                        ARROW_SPRITE_W, ARROW_SPRITE_H,
                        0, 0,
                        x + ARROW_X, y + ARROW_Y,
                        arrowWidth, ARROW_SPRITE_H);
            }
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