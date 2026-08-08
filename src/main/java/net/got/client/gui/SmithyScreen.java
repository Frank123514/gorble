package net.got.client.gui;

import net.got.menu.SmithyMenu;
import net.got.network.SelectForgeModePayload;
import net.got.network.SelectSmithyRecipePayload;
import net.got.recipe.SmithyRecipe;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

/**
 * SmithyScreen — stonecutter GUI base + furnace fuel/progress overlaid on top.
 *
 * Exactly mirrors the OvenScreen pattern:
 *   • Blits the vanilla stonecutter.png as the full background
 *     (gives input slot at (20,33), recipe panel, scroller track,
 *      output slot at (143,33), arrow, and player inventory — all for free)
 *   • Borrows one slot patch from the stonecutter texture UV for the extra fuel slot
 *   • Overlays furnace lit_progress (flame) and burn_progress (arrow) sprites
 *
 * Vanilla stonecutter.png layout (176×166 GUI, 256×256 sheet):
 *   Input  slot : (20,  33)
 *   Output slot : (143, 33)
 *   Recipe panel: inset at (52,14), 4 cols × 3 rows of 16×18 cells
 *   Scroller    : x=119, y=14, 12×15 sprite
 *   Arrow       : drawn in texture at ~(92,33), 22×16
 *   Player inv  : (8+col*18, 84+row*18) and hotbar at (8+col*18, 142)
 *
 * We add (not in stonecutter texture):
 *   Fuel slot   : (20, 53) — directly below input (+20px), painted over the texture
 *   Flame sprite: 14×14, base at (22, 36), grows upward as fuel burns
 */
public class SmithyScreen extends AbstractContainerScreen<SmithyMenu> {

    // ── Vanilla stonecutter texture ───────────────────────────────────────────
    private static final ResourceLocation STONECUTTER_TEXTURE =
            ResourceLocation.withDefaultNamespace("textures/gui/container/stonecutter.png");

    // ── Furnace animated sprites ──────────────────────────────────────────────
    private static final ResourceLocation LIT_SPRITE =
            ResourceLocation.withDefaultNamespace("container/furnace/lit_progress");
    private static final ResourceLocation ARROW_SPRITE =
            ResourceLocation.withDefaultNamespace("container/furnace/burn_progress");

    // ── Sprite dimensions ─────────────────────────────────────────────────────
    private static final int FLAME_SPRITE_W = 14;
    private static final int FLAME_SPRITE_H = 14;
    private static final int ARROW_SPRITE_W = 24;
    private static final int ARROW_SPRITE_H = 16;

    // ── Stonecutter recipe-button sprites ─────────────────────────────────────
    private static final ResourceLocation RECIPE_SELECTED =
            ResourceLocation.withDefaultNamespace("container/stonecutter/recipe_selected");
    private static final ResourceLocation RECIPE_HIGHLIGHTED =
            ResourceLocation.withDefaultNamespace("container/stonecutter/recipe_highlighted");
    private static final ResourceLocation SCROLLER =
            ResourceLocation.withDefaultNamespace("container/stonecutter/scroller");
    private static final ResourceLocation SCROLLER_DISABLED =
            ResourceLocation.withDefaultNamespace("container/stonecutter/scroller_disabled");

    // ── Recipe grid — mirrors stonecutter.png's panel exactly ─────────────────
    // Panel inset starts at (52,14); 4 cols × 3 rows of 16×18 cells.
    private static final int GRID_X    = 52;
    private static final int GRID_Y    = 14;
    private static final int CELL_W    = 16;
    private static final int CELL_H    = 18;
    private static final int GRID_COLS = 4;
    private static final int GRID_ROWS = 3;
    private static final int GRID_W    = GRID_COLS * CELL_W;  // 64 px
    private static final int GRID_H    = GRID_ROWS * CELL_H;  // 54 px

    // Scroller matches vanilla stonecutter (x=119, track from y=14)
    private static final int SCROLLER_W = 12;
    private static final int SCROLLER_H = 15;
    private static final int SCROLL_X   = 119;

    // ── Furnace overlay positions (relative to GUI top-left) ─────────────────
    // Flame: 14×14 sprite, sits between input slot and fuel slot.
    // Input slot at y=14 (bottom at y=30), fuel slot at y=53.
    // Flame positioned at y=36 to fit in the gap.
    private static final int FLAME_X = 20;
    private static final int FLAME_Y = 36;

    // Arrow: positioned below the output/result slot.
    // Output slot at x=143, y=33 (width=16, height=16).
    // Arrow centered horizontally under the slot, starting below it.
    private static final int ARROW_X = 140;
    private static final int ARROW_Y = 55;

    // ── Slot bevel for the extra fuel slot (not in stonecutter texture) ───────
    private static final int C_SLOT_BG = 0xFF_8B8B8B;
    private static final int C_LT      = 0xFF_FFFFFF;
    private static final int C_DK      = 0xFF_555555;
    private static final int C_TEXT    = 0xFF_404040;

    // Mode tab (switches to Alloying) drawn at the top-right of the panel.
    private static final int TAB_W = 20;
    private static final int TAB_H = 14;
    private static final int TAB_X = 176 - TAB_W - 2;
    private static final int TAB_Y = 2;

    // ── State ─────────────────────────────────────────────────────────────────
    private int     scrollOffset       = 0;
    private boolean isDraggingScroller = false;
    private List<RecipeHolder<SmithyRecipe>> recipes = List.of();
    private ItemStack lastInput = ItemStack.EMPTY;

    // ─────────────────────────────────────────────────────────────────────────

    public SmithyScreen(SmithyMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        this.imageWidth      = 176;
        this.imageHeight     = 166;
        this.inventoryLabelY = this.imageHeight - 94;  // = 72, matches stonecutter
    }

    // ── Labels ────────────────────────────────────────────────────────────────

    @Override
    protected void renderLabels(GuiGraphics g, int mouseX, int mouseY) {
        g.drawString(font, this.title,               8, 6,                    C_TEXT, false);
        g.drawString(font, this.playerInventoryTitle, 8, this.imageHeight - 94, C_TEXT, false);
    }

    // ── Main render ───────────────────────────────────────────────────────────

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        refreshRecipeList();
        renderBackground(g, mouseX, mouseY, partialTick);
        super.render(g, mouseX, mouseY, partialTick);
        renderRecipeGrid(g, mouseX, mouseY);
        renderModeTab(g, mouseX, mouseY);
        renderTooltip(g, mouseX, mouseY);
        renderRecipeTooltip(g, mouseX, mouseY);
    }

    // ── Background ────────────────────────────────────────────────────────────

    @Override
    protected void renderBg(GuiGraphics g, float partialTick, int mouseX, int mouseY) {
        int x = this.leftPos;
        int y = this.topPos;

        // 1. Blit the full vanilla stonecutter background.
        //    This draws: outer panel, input slot, recipe panel inset, scroller track,
        //    static arrow, output slot, and all 36 player-inventory slot backgrounds.
        g.blit(RenderPipelines.GUI_TEXTURED, STONECUTTER_TEXTURE,
                x, y, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        // Paint over the stonecutter texture's built-in input slot at (20,33)
        // so the flame area is clean panel background, not a slot box.
        // The standard inventory panel gray is 0xFFC6C6C6.
        g.fill(x + 19, y + 32, x + 37, y + 50, 0xFFC6C6C6);

        // 2. Draw slot backgrounds for positions not covered by the stonecutter texture.
        //    The stonecutter PNG has its input slot drawn at y=33; we moved ours to y=14,
        //    so we paint both the input and fuel slots programmatically.
        vanillaSlot(g, x + SmithyMenu.INPUT_X - 1, y + SmithyMenu.INPUT_Y - 1);  // input at y=14
        vanillaSlot(g, x + SmithyMenu.FUEL_X  - 1, y + SmithyMenu.FUEL_Y  - 1);  // fuel  at y=53

        // 3. Flame indicator — uses vanilla furnace lit_progress sprite.
        //    The sprite grows upward from the bottom (height 0-13px).
        if (menu.isFlaming()) {
            int flameHeight = menu.getFlameProgress();  // 0-13
            if (flameHeight > 0) {
                g.blitSprite(RenderPipelines.GUI_TEXTURED, LIT_SPRITE,
                        FLAME_SPRITE_W, FLAME_SPRITE_H,
                        0, FLAME_SPRITE_H - flameHeight,
                        x + FLAME_X, y + FLAME_Y + FLAME_SPRITE_H - flameHeight,
                        FLAME_SPRITE_W, flameHeight);
            }
        }

        // 4. Progress arrow — uses vanilla furnace burn_progress sprite.
        //    The sprite fills from left to right (width 0-24px).
        if (menu.isCrafting()) {
            int arrowWidth = menu.getArrowProgress();  // 0-24
            if (arrowWidth > 0) {
                g.blitSprite(RenderPipelines.GUI_TEXTURED, ARROW_SPRITE,
                        ARROW_SPRITE_W, ARROW_SPRITE_H,
                        0, 0,
                        x + ARROW_X, y + ARROW_Y,
                        arrowWidth, ARROW_SPRITE_H);
            }
        }
    }

    // ── Stonecutter-style recipe grid ─────────────────────────────────────────

    private void renderRecipeGrid(GuiGraphics g, int mouseX, int mouseY) {
        int gx = leftPos + GRID_X;
        int gy = topPos  + GRID_Y;

        int total     = recipes.size();
        int maxRows   = total == 0 ? 0 : (int) Math.ceil((double) total / GRID_COLS);
        int maxScroll = Math.max(0, maxRows - GRID_ROWS);
        scrollOffset  = Math.min(scrollOffset, maxScroll);

        int selectedIdx = menu.getSelectedRecipeIndex();

        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                int ri = (row + scrollOffset) * GRID_COLS + col;
                if (ri >= total) break;

                int bx = gx + col * CELL_W;
                int by = gy + row * CELL_H;

                boolean hovered  = mouseX >= bx && mouseX < bx + CELL_W
                        && mouseY >= by && mouseY < by + CELL_H;
                boolean isActive = (ri == selectedIdx);

                if (isActive) {
                    g.blitSprite(RenderPipelines.GUI_TEXTURED, RECIPE_SELECTED,
                            bx, by, CELL_W, CELL_H);
                } else if (hovered) {
                    g.blitSprite(RenderPipelines.GUI_TEXTURED, RECIPE_HIGHLIGHTED,
                            bx, by, CELL_W, CELL_H);
                }

                g.renderItem(recipes.get(ri).value().getResult(), bx, by + 1);
            }
        }

        // Scroller thumb
        boolean canScroll = maxScroll > 0;
        int trackH = GRID_H - SCROLLER_H;
        int thumbY  = canScroll
                ? gy + (int) Math.round((double) scrollOffset / maxScroll * trackH)
                : gy;

        g.blitSprite(RenderPipelines.GUI_TEXTURED,
                canScroll ? SCROLLER : SCROLLER_DISABLED,
                leftPos + SCROLL_X, thumbY, SCROLLER_W, SCROLLER_H);
    }

    // ── Tooltips ──────────────────────────────────────────────────────────────

    /** Small text tab in the top-right corner that flips the Forge into Alloying mode. */
    private void renderModeTab(GuiGraphics g, int mouseX, int mouseY) {
        int bx = leftPos + TAB_X, by = topPos + TAB_Y;
        boolean hovered = mouseX >= bx && mouseX < bx + TAB_W && mouseY >= by && mouseY < by + TAB_H;
        g.fill(bx, by, bx + TAB_W, by + TAB_H, hovered ? 0xFFB0B0B0 : 0xFF8B8B8B);
        g.renderOutline(bx, by, TAB_W, TAB_H, 0xFF373737);
        g.drawCenteredString(font, Component.translatable("got.forge.tab.alloy"),
                bx + TAB_W / 2, by + 3, C_TEXT);
    }

    private void renderRecipeTooltip(GuiGraphics g, int mouseX, int mouseY) {
        int gx = leftPos + GRID_X, gy = topPos + GRID_Y;
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                int ri = (row + scrollOffset) * GRID_COLS + col;
                if (ri >= recipes.size()) return;
                int bx = gx + col * CELL_W, by = gy + row * CELL_H;
                if (mouseX >= bx && mouseX < bx + CELL_W && mouseY >= by && mouseY < by + CELL_H) {
                    g.setTooltipForNextFrame(font, recipes.get(ri).value().getResult(), mouseX, mouseY);
                    return;
                }
            }
        }
    }

    // ── Mouse input ───────────────────────────────────────────────────────────

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        isDraggingScroller = false;

        if (button == 0) {
            int mx = (int) mouseX, my = (int) mouseY;

            int tbx = leftPos + TAB_X, tby = topPos + TAB_Y;
            if (mx >= tbx && mx < tbx + TAB_W && my >= tby && my < tby + TAB_H) {
                ClientPacketDistributor.sendToServer(
                        new SelectForgeModePayload(net.got.block.ForgeBlockEntity.MODE_ALLOYING));
                return true;
            }

            // Recipe grid click
            int gx = leftPos + GRID_X, gy = topPos + GRID_Y;
            if (mx >= gx && mx < gx + GRID_W && my >= gy && my < gy + GRID_H) {
                int col = (mx - gx) / CELL_W;
                int row = (my - gy) / CELL_H;
                int ri  = (row + scrollOffset) * GRID_COLS + col;
                if (ri >= 0 && ri < recipes.size()) {
                    int toSend = (ri == menu.getSelectedRecipeIndex()) ? -1 : ri;
                    ClientPacketDistributor.sendToServer(new SelectSmithyRecipePayload(toSend));
                    return true;
                }
            }

            // Scroller drag start
            int sx = leftPos + SCROLL_X, sy = topPos + GRID_Y;
            if (mx >= sx && mx < sx + SCROLLER_W && my >= sy && my < sy + GRID_H) {
                isDraggingScroller = true;
                updateScrollFromMouse(my);
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (isDraggingScroller && button == 0) {
            updateScrollFromMouse((int) mouseY);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (isDraggingScroller) { isDraggingScroller = false; return true; }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mx, double my, double sx, double sy) {
        int gx = leftPos + GRID_X - 1, gy = topPos + GRID_Y - 1;
        if (mx >= gx && mx < gx + GRID_W + SCROLLER_W + 6 && my >= gy && my < gy + GRID_H + 2) {
            int maxRows   = (int) Math.ceil((double) recipes.size() / GRID_COLS);
            int maxScroll = Math.max(0, maxRows - GRID_ROWS);
            scrollOffset  = (int) Math.max(0, Math.min(maxScroll, scrollOffset - sy));
            return true;
        }
        return super.mouseScrolled(mx, my, sx, sy);
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private void updateScrollFromMouse(int mouseY) {
        int maxRows   = (int) Math.ceil((double) recipes.size() / GRID_COLS);
        int maxScroll = Math.max(0, maxRows - GRID_ROWS);
        if (maxScroll == 0) return;
        int gy     = topPos + GRID_Y;
        int trackH = GRID_H - SCROLLER_H;
        scrollOffset = (int) Math.round((double)(mouseY - gy - SCROLLER_H / 2) / trackH * maxScroll);
        scrollOffset = Math.max(0, Math.min(maxScroll, scrollOffset));
    }

    private void refreshRecipeList() {
        ItemStack input = menu.getInputItem();
        if (!ItemStack.isSameItemSameComponents(input, lastInput)) {
            lastInput    = input.copy();
            recipes      = menu.getMatchingRecipes();
            scrollOffset = 0;
        }
    }

    /** Standard 18×18 inventory slot bevel — dark top/left, light bottom/right. */
    private void vanillaSlot(GuiGraphics g, int x, int y) {
        g.fill(x,      y,      x + 18, y + 1,  C_DK);
        g.fill(x,      y,      x + 1,  y + 18, C_DK);
        g.fill(x,      y + 17, x + 18, y + 18, C_LT);
        g.fill(x + 17, y,      x + 18, y + 18, C_LT);
        g.fill(x + 1,  y + 1,  x + 17, y + 17, C_SLOT_BG);
    }
}