package net.got.client.gui;

import net.got.block.SmithingAnvilBlockEntity;
import net.got.menu.SmithingAnvilMenu;
import net.got.network.SelectSmithingAnvilRecipePayload;
import net.got.recipe.SmithyRecipe;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

/**
 * SmithingAnvilScreen — the GUI for the Smithing Anvil block.
 *
 * Displays the stonecutter-style recipe selection panel, and a timing
 * bar below the output slot that the player must hit at the right moment
 * with the smithing hammer.
 */
public class SmithingAnvilScreen extends AbstractContainerScreen<SmithingAnvilMenu> {

    private static final ResourceLocation STONECUTTER_TEXTURE =
            ResourceLocation.withDefaultNamespace("textures/gui/container/stonecutter.png");

    private static final ResourceLocation RECIPE_SELECTED =
            ResourceLocation.withDefaultNamespace("container/stonecutter/recipe_selected");
    private static final ResourceLocation RECIPE_HIGHLIGHTED =
            ResourceLocation.withDefaultNamespace("container/stonecutter/recipe_highlighted");
    private static final ResourceLocation SCROLLER =
            ResourceLocation.withDefaultNamespace("container/stonecutter/scroller");
    private static final ResourceLocation SCROLLER_DISABLED =
            ResourceLocation.withDefaultNamespace("container/stonecutter/scroller_disabled");

    private static final int GRID_X    = 52;
    private static final int GRID_Y    = 14;
    private static final int CELL_W    = 16;
    private static final int CELL_H    = 18;
    private static final int GRID_COLS = 4;
    private static final int GRID_ROWS = 3;
    private static final int GRID_W    = GRID_COLS * CELL_W;
    private static final int GRID_H    = GRID_ROWS * CELL_H;

    private static final int SCROLLER_W = 12;
    private static final int SCROLLER_H = 15;
    private static final int SCROLL_X   = 119;

    // Timing bar layout — positioned below the output slot
    // Output slot is at OUTPUT_X=143, OUTPUT_Y=33 (18x18)
    // Bar sits below the output area, centered on it
    private static final int BAR_W  = 52;   // total bar width
    private static final int BAR_H  = 6;    // bar height
    private static final int BAR_X  = 131;  // leftPos-relative
    private static final int BAR_Y  = 58;   // leftPos-relative (below output slot)

    private static final int MARKER_W = 3;
    private static final int MARKER_H = 10;

    // Hit counter text position (above bar)
    private static final int HITS_TEXT_X = 131;
    private static final int HITS_TEXT_Y = 49;

    // Colors
    private static final int C_SLOT_BG     = 0xFF_8B8B8B;
    private static final int C_LT          = 0xFF_FFFFFF;
    private static final int C_DK          = 0xFF_555555;
    private static final int C_TEXT        = 0xFF_404040;
    private static final int C_BAR_BG      = 0xFF_3A3A3A;
    private static final int C_ZONE_GOOD   = 0xFF_1FA01F;   // green zone
    private static final int C_ZONE_PERFECT = 0xFF_59D459;  // brighter inner zone
    private static final int C_MARKER      = 0xFF_FFFFFF;
    private static final int C_MISS        = 0xFF_DD2222;
    private static final int C_GOOD        = 0xFF_22BB22;
    private static final int C_PERFECT     = 0xFF_FFFF44;

    // Feedback flash duration in ticks
    private static final int FLASH_TICKS = 12;

    // ── State ─────────────────────────────────────────────────────────────────
    private int     scrollOffset       = 0;
    private boolean isDraggingScroller = false;
    private List<RecipeHolder<SmithyRecipe>> recipes = List.of();
    private ItemStack lastInput = ItemStack.EMPTY;

    // Client-side flash feedback
    private int lastQualitySeen   = SmithingAnvilBlockEntity.HIT_QUALITY_NONE;
    private int flashTimer        = 0;

    public SmithingAnvilScreen(SmithingAnvilMenu menu, Inventory playerInv, Component title) {
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
        refreshRecipeList();
        tickFlash();
        renderBackground(g, mouseX, mouseY, partialTick);
        super.render(g, mouseX, mouseY, partialTick);
        renderRecipeGrid(g, mouseX, mouseY);
        renderTooltip(g, mouseX, mouseY);
        renderRecipeTooltip(g, mouseX, mouseY);
    }

    private void tickFlash() {
        int quality = menu.getLastHitQuality();
        if (quality != SmithingAnvilBlockEntity.HIT_QUALITY_NONE && quality != lastQualitySeen) {
            lastQualitySeen = quality;
            flashTimer = FLASH_TICKS;
        }
        if (flashTimer > 0) flashTimer--;
    }

    @Override
    protected void renderBg(GuiGraphics g, float partialTick, int mouseX, int mouseY) {
        int x = this.leftPos;
        int y = this.topPos;

        g.blit(RenderType::guiTextured, STONECUTTER_TEXTURE,
                x, y, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        // Clear the stonecutter's built-in input/fuel slot area
        g.fill(x + 19, y + 32, x + 37, y + 51, 0xFFC6C6C6);

        // Draw our single input slot
        vanillaSlot(g, x + SmithingAnvilMenu.INPUT_X - 1, y + SmithingAnvilMenu.INPUT_Y - 1);

        // Timing bar
        if (menu.getSelectedRecipeIndex() >= 0) {
            renderTimingBar(g, x, y);
        }
    }

    private void renderTimingBar(GuiGraphics g, int x, int y) {
        int bx = x + BAR_X;
        int by = y + BAR_Y;

        // ── Hits Left label ───────────────────────────────────────────────
        int hitsLeft = menu.getHitsRequired() - menu.getHitCount();
        String hitsText = "Hits: " + hitsLeft;
        g.drawString(font, hitsText, x + HITS_TEXT_X, y + HITS_TEXT_Y, C_TEXT, false);

        // ── Bar background ────────────────────────────────────────────────
        g.fill(bx, by, bx + BAR_W, by + BAR_H, C_BAR_BG);

        // ── Zone highlight ────────────────────────────────────────────────
        int zoneCenter = menu.getZoneCenter(); // 50
        int zoneHalf   = menu.getZoneHalf();   // 12

        int goodLeft  = bx + (zoneCenter - zoneHalf) * BAR_W / 100;
        int goodRight = bx + (zoneCenter + zoneHalf) * BAR_W / 100;
        g.fill(goodLeft, by, goodRight, by + BAR_H, C_ZONE_GOOD);

        // Perfect inner zone (half of zone_half)
        int perfLeft  = bx + (zoneCenter - zoneHalf / 2) * BAR_W / 100;
        int perfRight = bx + (zoneCenter + zoneHalf / 2) * BAR_W / 100;
        g.fill(perfLeft, by, perfRight, by + BAR_H, C_ZONE_PERFECT);

        // ── Marker ────────────────────────────────────────────────────────
        int markerPos = menu.getMarkerPos(); // 0–100
        int markerX   = bx + markerPos * BAR_W / 100 - MARKER_W / 2;
        int markerTop = by - (MARKER_H - BAR_H) / 2;
        g.fill(markerX, markerTop, markerX + MARKER_W, markerTop + MARKER_H, C_MARKER);

        // ── Flash feedback ────────────────────────────────────────────────
        if (flashTimer > 0) {
            int flashColor = switch (lastQualitySeen) {
                case SmithingAnvilBlockEntity.HIT_QUALITY_MISS    -> C_MISS;
                case SmithingAnvilBlockEntity.HIT_QUALITY_GOOD    -> C_GOOD;
                case SmithingAnvilBlockEntity.HIT_QUALITY_PERFECT -> C_PERFECT;
                default -> 0;
            };
            if (flashColor != 0) {
                // Small flash square above the bar
                int alpha = (flashTimer * 0xFF / FLASH_TICKS) << 24;
                int col   = (flashColor & 0x00FFFFFF) | alpha;
                g.fill(bx + BAR_W / 2 - 4, by - 10, bx + BAR_W / 2 + 4, by - 2, col);
            }
        }

        // ── Border ────────────────────────────────────────────────────────
        g.fill(bx - 1, by - 1, bx + BAR_W + 1, by,          C_DK);
        g.fill(bx - 1, by + BAR_H, bx + BAR_W + 1, by + BAR_H + 1, C_LT);
        g.fill(bx - 1, by - 1, bx,          by + BAR_H + 1, C_DK);
        g.fill(bx + BAR_W, by - 1, bx + BAR_W + 1, by + BAR_H + 1, C_LT);
    }

    // ── Recipe grid ───────────────────────────────────────────────────────────

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
                    g.blitSprite(RenderType::guiTextured, RECIPE_SELECTED,
                            bx, by, CELL_W, CELL_H);
                } else if (hovered) {
                    g.blitSprite(RenderType::guiTextured, RECIPE_HIGHLIGHTED,
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

        g.blitSprite(RenderType::guiTextured,
                canScroll ? SCROLLER : SCROLLER_DISABLED,
                leftPos + SCROLL_X, thumbY, SCROLLER_W, SCROLLER_H);
    }

    private void renderRecipeTooltip(GuiGraphics g, int mouseX, int mouseY) {
        int gx = leftPos + GRID_X, gy = topPos + GRID_Y;
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                int ri = (row + scrollOffset) * GRID_COLS + col;
                if (ri >= recipes.size()) return;
                int bx = gx + col * CELL_W, by = gy + row * CELL_H;
                if (mouseX >= bx && mouseX < bx + CELL_W && mouseY >= by && mouseY < by + CELL_H) {
                    g.renderTooltip(font, recipes.get(ri).value().getResult(), mouseX, mouseY);
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

            int gx = leftPos + GRID_X, gy = topPos + GRID_Y;
            if (mx >= gx && mx < gx + GRID_W && my >= gy && my < gy + GRID_H) {
                int col = (mx - gx) / CELL_W;
                int row = (my - gy) / CELL_H;
                int ri  = (row + scrollOffset) * GRID_COLS + col;
                if (ri >= 0 && ri < recipes.size()) {
                    int toSend = (ri == menu.getSelectedRecipeIndex()) ? -1 : ri;
                    PacketDistributor.sendToServer(new SelectSmithingAnvilRecipePayload(toSend));
                    return true;
                }
            }

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

    private void vanillaSlot(GuiGraphics g, int x, int y) {
        g.fill(x,      y,      x + 18, y + 1,  C_DK);
        g.fill(x,      y,      x + 1,  y + 18, C_DK);
        g.fill(x,      y + 17, x + 18, y + 18, C_LT);
        g.fill(x + 17, y,      x + 18, y + 18, C_LT);
        g.fill(x + 1,  y + 1,  x + 17, y + 17, C_SLOT_BG);
    }
}
