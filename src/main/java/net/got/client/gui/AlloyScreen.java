package net.got.client.gui;

import net.got.block.ForgeBlockEntity;
import net.got.menu.AlloyMenu;
import net.got.network.SelectForgeModePayload;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

public class AlloyScreen extends AbstractContainerScreen<AlloyMenu> {

    private static final Identifier FORGE_TEXTURE =
            Identifier.fromNamespaceAndPath("got", "textures/gui/forge.png");

    private static final Identifier LIT_SPRITE =
            Identifier.withDefaultNamespace("container/furnace/lit_progress");
    private static final Identifier ARROW_SPRITE =
            Identifier.withDefaultNamespace("container/furnace/burn_progress");

    private static final int FLAME_W = 14;
    private static final int FLAME_H = 14;
    private static final int ARROW_W = 24;
    private static final int ARROW_H = 16;

    private static final int FLAME_X = 49;
    private static final int FLAME_Y = 36;

    private static final int ARROW_X = 96;
    private static final int ARROW_Y = 17;

    private static final int TAB_W = 20;
    private static final int TAB_H = 14;
    private static final int TAB_X = 176 - TAB_W - 2;
    private static final int TAB_Y = 2;

    private static final int C_TEXT = 0xFF_404040;

    public AlloyScreen(AlloyMenu menu, Inventory playerInv, Component title) {
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
        renderTooltip(g, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics g, float partialTick, int mouseX, int mouseY) {
        int x = this.leftPos;
        int y = this.topPos;

        g.blit(RenderPipelines.GUI_TEXTURED, FORGE_TEXTURE,
                x, y, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

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

        if (menu.isCrafting()) {
            int w = menu.getArrowProgress();
            if (w > 0) {
                g.blitSprite(RenderPipelines.GUI_TEXTURED, ARROW_SPRITE,
                        ARROW_W, ARROW_H, 0, 0,
                        x + ARROW_X, y + ARROW_Y,
                        w, ARROW_H);
            }
        }
    }

    private void renderModeTab(GuiGraphics g, int mouseX, int mouseY) {
        int bx = leftPos + TAB_X, by = topPos + TAB_Y;
        boolean hovered = mouseX >= bx && mouseX < bx + TAB_W
                && mouseY >= by && mouseY < by + TAB_H;
        g.fill(bx, by, bx + TAB_W, by + TAB_H, hovered ? 0xFF_B0B0B0 : 0xFF_8B8B8B);
        g.renderOutline(bx, by, TAB_W, TAB_H, 0xFF_373737);
        g.drawCenteredString(font, Component.translatable("got.forge.tab.smith"),
                bx + TAB_W / 2, by + 3, C_TEXT);
    }

    @Override
    public boolean mouseClicked(net.minecraft.client.input.MouseButtonEvent __event, boolean __doubleClick){
        if (__event.button() == 0) {
            int mx = (int) __event.x(), my = (int) __event.y();
            int tbx = leftPos + TAB_X, tby = topPos + TAB_Y;
            if (mx >= tbx && mx < tbx + TAB_W && my >= tby && my < tby + TAB_H) {
                ClientPacketDistributor.sendToServer(
                        new SelectForgeModePayload(ForgeBlockEntity.MODE_HEAT_TREATING));
                return true;
            }
        }
        return super.mouseClicked(__event, __doubleClick);
    }
}
