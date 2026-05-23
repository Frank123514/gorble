package net.got.client.gui;

import net.got.GotMod;
import net.got.block.SmithyBlockEntity;
import net.got.menu.SmithyMenu;
import net.got.network.SelectSmithyRecipePayload;
import net.got.recipe.SmithyRecipe;
import net.minecraft.client.Minecraft;
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
 * SmithyScreen — GUI for the Smithy block.
 *
 * Layout (imageWidth=256, imageHeight=166):
 *
 *   Left panel  [x=0..99]:
 *     Input slot  at (27, 17)  ← ingot goes here
 *     Fire anim   at (28, 37)  ← fuel remaining
 *     Fuel slot   at (27, 55)  ← fuel goes here
 *     Arrow anim  at (50, 35)  ← cooking progress
 *     Output slot at (77, 35)  ← result appears here
 *
 *   Right panel [x=100..255]:
 *     Title "Select Recipe:" at (102, 6)
 *     Scrollable recipe list at (100, 16)..(254, 79)
 *       Each row: 18px icon + name, 18px tall
 *       Up to 4 visible rows; mouse-wheel to scroll
 *
 *   Player inventory at standard y=84/142.
 */
public class SmithyScreen extends AbstractContainerScreen<SmithyMenu> {

    // ── Textures & sprites ────────────────────────────────────────────────────
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "textures/gui/smithy.png");

    private static final ResourceLocation LIT_SPRITE =
            ResourceLocation.withDefaultNamespace("container/furnace/lit_progress");
    private static final ResourceLocation BURN_SPRITE =
            ResourceLocation.withDefaultNamespace("container/furnace/burn_progress");

    // ── Recipe list panel geometry ────────────────────────────────────────────
    private static final int LIST_X          = 100; // left edge of recipe panel (relative)
    private static final int LIST_Y          = 16;  // top edge
    private static final int LIST_W          = 154; // width
    private static final int ENTRY_H         = 18;  // height of each row
    private static final int VISIBLE_ROWS    = 4;   // rows visible without scrolling
    private static final int LIST_H          = VISIBLE_ROWS * ENTRY_H; // 72px

    // ── Scroll state ──────────────────────────────────────────────────────────
    private int scrollOffset = 0;  // how many rows have been scrolled past

    // Cached recipe list (rebuilt when input changes)
    private List<RecipeHolder<SmithyRecipe>> recipes = List.of();
    private ItemStack lastInputItem = net.minecraft.world.item.ItemStack.EMPTY;

    public SmithyScreen(SmithyMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        this.imageWidth  = 256;
        this.imageHeight = 166;
        this.inventoryLabelY = this.imageHeight - 94; // standard
    }

    // ── Render ────────────────────────────────────────────────────────────────

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        refreshRecipeList();
        this.renderBackground(g, mouseX, mouseY, partialTick);
        super.render(g, mouseX, mouseY, partialTick);
        renderRecipePanel(g, mouseX, mouseY);
        this.renderTooltip(g, mouseX, mouseY);
        renderRecipeTooltip(g, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics g, float partialTick, int mouseX, int mouseY) {
        int x = this.leftPos;
        int y = this.topPos;

        // Draw smithy background texture
        g.blit(RenderType::guiTextured, TEXTURE,
                x, y, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        // ── Fire (fuel remaining) ─────────────────────────────────────────────
        if (menu.isFlaming()) {
            int k = menu.getFlameProgress(); // 0..13
            if (k > 0) {
                g.blitSprite(RenderType::guiTextured, LIT_SPRITE,
                        14, 14, 0, 14 - k,
                        x + 28, y + 36 + (14 - k),
                        14, k);
            }
        }

        // ── Arrow (cooking progress) ──────────────────────────────────────────
        if (menu.isCrafting()) {
            int l = menu.getArrowProgress(); // 0..22
            if (l > 0) {
                g.blitSprite(RenderType::guiTextured, BURN_SPRITE,
                        24, 16, 0, 0,
                        x + 50, y + 35,
                        l, 16);
            }
        }
    }

    // ── Recipe panel ──────────────────────────────────────────────────────────

    private void renderRecipePanel(GuiGraphics g, int mouseX, int mouseY) {
        int px = leftPos + LIST_X;
        int py = topPos  + LIST_Y;

        int selectedIdx = menu.getSelectedRecipeIndex();
        int maxScroll   = Math.max(0, recipes.size() - VISIBLE_ROWS);
        scrollOffset    = Math.min(scrollOffset, maxScroll);

        // Panel background
        g.fill(px - 2, py - 2,
                px + LIST_W + 2, py + LIST_H + 2,
                0xFF_2A2A2A);
        g.fill(px - 1, py - 1,
                px + LIST_W + 1, py + LIST_H + 1,
                0xFF_3C3C3C);
        g.fill(px, py,
                px + LIST_W, py + LIST_H,
                0xFF_1A1A1A);

        if (recipes.isEmpty()) {
            g.drawString(this.font,
                    Component.literal("No recipes for this item"),
                    px + 4, py + (LIST_H / 2) - 4,
                    0xFF_888888, false);
            return;
        }

        // Draw visible recipe rows
        for (int i = 0; i < VISIBLE_ROWS; i++) {
            int recipeIdx = i + scrollOffset;
            if (recipeIdx >= recipes.size()) break;

            RecipeHolder<SmithyRecipe> holder = recipes.get(recipeIdx);
            net.minecraft.world.item.ItemStack result = holder.value().getResult();

            int ry = py + i * ENTRY_H;

            // Highlight hovered row
            boolean hovered = mouseX >= px && mouseX < px + LIST_W
                    && mouseY >= ry && mouseY < ry + ENTRY_H;

            // Highlight selected row
            boolean selected = (recipeIdx == selectedIdx);

            int rowColor = selected ? 0xFF_5A4A1A
                    : hovered  ? 0xFF_3A3A3A
                      :            0xFF_222222;
            g.fill(px, ry, px + LIST_W, ry + ENTRY_H, rowColor);

            // Item icon
            g.renderItem(result, px + 1, ry + 1);
            g.renderItemDecorations(this.font, result, px + 1, ry + 1);

            // Item name (truncated to fit)
            String name = result.getHoverName().getString();
            int maxW = LIST_W - 22;
            if (this.font.width(name) > maxW) {
                name = this.font.plainSubstrByWidth(name, maxW - 6) + "…";
            }
            g.drawString(this.font, name,
                    px + 20, ry + 5,
                    selected ? 0xFF_FFD700 : 0xFF_DDDDDD,
                    false);
        }

        // ── Scroll indicator (dots) if needed ────────────────────────────────
        if (recipes.size() > VISIBLE_ROWS) {
            int totalDots  = recipes.size();
            int activeDot  = scrollOffset;
            // Draw a tiny scrollbar on the far right
            int sbX = px + LIST_W + 3;
            int sbH = LIST_H;
            g.fill(sbX, py, sbX + 3, py + sbH, 0xFF_444444);
            int thumbH = Math.max(4, sbH * VISIBLE_ROWS / totalDots);
            int thumbY = py + (sbH - thumbH) * scrollOffset / Math.max(1, maxScroll);
            g.fill(sbX, thumbY, sbX + 3, thumbY + thumbH, 0xFF_AAAAAA);
        }
    }

    private void renderRecipeTooltip(GuiGraphics g, int mouseX, int mouseY) {
        int px = leftPos + LIST_X;
        int py = topPos  + LIST_Y;

        for (int i = 0; i < VISIBLE_ROWS; i++) {
            int recipeIdx = i + scrollOffset;
            if (recipeIdx >= recipes.size()) break;

            int ry = py + i * ENTRY_H;
            if (mouseX >= px && mouseX < px + LIST_W
                    && mouseY >= ry && mouseY < ry + ENTRY_H) {
                net.minecraft.world.item.ItemStack result =
                        recipes.get(recipeIdx).value().getResult();
                g.renderTooltip(this.font, result, mouseX, mouseY);
                break;
            }
        }
    }

    // ── Input handling ────────────────────────────────────────────────────────

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            int px = leftPos + LIST_X;
            int py = topPos  + LIST_Y;

            if (mouseX >= px && mouseX < px + LIST_W
                    && mouseY >= py && mouseY < py + LIST_H) {

                int row = (int) ((mouseY - py) / ENTRY_H);
                int recipeIdx = row + scrollOffset;
                if (recipeIdx >= 0 && recipeIdx < recipes.size()) {
                    int current = menu.getSelectedRecipeIndex();
                    int next    = (recipeIdx == current) ? -1 : recipeIdx;
                    PacketDistributor.sendToServer(new SelectSmithyRecipePayload(next));
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        int px = leftPos + LIST_X;
        int py = topPos  + LIST_Y;

        if (mouseX >= px - 2 && mouseX < px + LIST_W + 6
                && mouseY >= py - 2 && mouseY < py + LIST_H + 2) {
            int maxScroll = Math.max(0, recipes.size() - VISIBLE_ROWS);
            scrollOffset  = (int) Math.max(0, Math.min(maxScroll, scrollOffset - scrollY));
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    /** Rebuild the recipe list only when the input item changes. */
    private void refreshRecipeList() {
        net.minecraft.world.item.ItemStack input = menu.getInputItem();
        if (!net.minecraft.world.item.ItemStack.isSameItemSameComponents(input, lastInputItem)) {
            lastInputItem = input.copy();
            recipes       = menu.getMatchingRecipes();
            scrollOffset  = 0;
        }
    }
}