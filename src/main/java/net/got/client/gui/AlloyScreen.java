package net.got.client.gui;

import net.got.block.ForgeBlockEntity;
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
 * AlloyScreen — GUI for the Forge block's alloying mode.
 *
 * Blits assets/got/textures/gui/forge.png as the full background (256×256
 * sheet, 176×166 used region). The texture has all slots, the player
 * inventory, the static arrow shape, and the static flame shape already
 * baked in. We overlay the vanilla animated lit_progress and burn_progress
 * sprites on top — only while the forge is actually burning / cooking.
 *
 * Pixel positions are measured directly from forge.png:
 *   Inputs A-D : (20,17) (38,17) (56,17) (74,17)   — 18×18 each
 *   Fuel       : (48,53)                             — 18×18
 *   Output     : (130,13)                            — 26×26 big slot
 *   Flame area : baked at (49,37), 13×13 px
 *   Arrow area : baked at (88,16), right-pointing shape
 *
 * The flame sprite (14×14) is placed to exactly cover the baked flame pixels.
 * The arrow sprite (24×16) is placed to cover the baked arrow shaft.
 */
public class AlloyScreen extends AbstractContainerScreen<AlloyMenu> {

    // ── Textures / sprites ────────────────────────────────────────────────────

    private static final ResourceLocation FORGE_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/gui/forge.png");

    private static final ResourceLocation LIT_SPRITE =
            ResourceLocation.withDefaultNamespace("container/furnace/lit_progress");
    private static final ResourceLocation ARROW_SPRITE =
            ResourceLocation.withDefaultNamespace("container/furnace/burn_progress");

    // ── Sprite dimensions ─────────────────────────────────────────────────────

    private static final int FLAME_W = 14;
    private static final int FLAME_H = 14;
    private static final int ARROW_W = 24;
    private static final int ARROW_H = 16;

    // ── Overlay positions (panel-relative, measured from forge.png) ───────────

    // Flame: covers the baked flame pixels at (49,37)–(61,49).
    // lit_progress sprite grows upward, so we anchor at the bottom of the area.
    private static final int FLAME_X = 49;
    private static final int FLAME_Y = 36;   // 1px above baked pixels so it fills cleanly

    // Arrow: the baked arrow shaft centre is y≈24; 16px tall sprite centred there.
    private static final int ARROW_X = 96;
    private static final int ARROW_Y = 17;

    // ── Tab ───────────────────────────────────────────────────────────────────

    private static final int TAB_W = 20;
    private static final int TAB_H = 14;
    private static final int TAB_X = 176 - TAB_W - 2;
    private static final int TAB_Y = 2;

    private static final int C_TEXT = 0xFF_404040;

    // ─────────────────────────────────────────────────────────────────────────

    public AlloyScreen(AlloyMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        this.imageWidth      = 176;
        this.imageHeight     = 166;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    // ── Labels ────────────────────────────────────────────────────────────────

    @Override
    protected void renderLabels(GuiGraphics g, int mouseX, int mouseY) {
        g.drawString(font, this.title,                8, 6,                     C_TEXT, false);
        g.drawString(font, this.playerInventoryTitle, 8, this.imageHeight - 94, C_TEXT, false);
    }

    // ── Full render ───────────────────────────────────────────────────────────

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        renderBackground(g, mouseX, mouseY, partialTick);
        super.render(g, mouseX, mouseY, partialTick);
        renderModeTab(g, mouseX, mouseY);
        renderTooltip(g, mouseX, mouseY);
    }

    // ── Background ────────────────────────────────────────────────────────────

    @Override
    protected void renderBg(GuiGraphics g, float partialTick, int mouseX, int mouseY) {
        int x = this.leftPos;
        int y = this.topPos;

        // 1. Blit the custom forge texture — panel, all slots, arrow, flame,
        //    and player inventory all baked in.
        g.blit(RenderType::guiTextured, FORGE_TEXTURE,
                x, y, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        // 2. Animated flame — overlaid on the baked flame pixels.
        //    lit_progress grows upward (src y offset from bottom).
        //    Only shown while fuel is burning.
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

        // 3. Animated arrow — overlaid on the baked arrow pixels.
        //    burn_progress fills left-to-right.
        //    Only shown while a recipe is cooking.
        if (menu.isCrafting()) {
            int w = menu.getArrowProgress(); // 0–24
            if (w > 0) {
                g.blitSprite(RenderType::guiTextured, ARROW_SPRITE,
                        ARROW_W, ARROW_H, 0, 0,
                        x + ARROW_X, y + ARROW_Y,
                        w, ARROW_H);
            }
        }
    }

    // ── Mode-switch tab ───────────────────────────────────────────────────────

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
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            int mx = (int) mouseX, my = (int) mouseY;
            int tbx = leftPos + TAB_X, tby = topPos + TAB_Y;
            if (mx >= tbx && mx < tbx + TAB_W && my >= tby && my < tby + TAB_H) {
                PacketDistributor.sendToServer(
                        new SelectForgeModePayload(ForgeBlockEntity.MODE_HEAT_TREATING));
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
