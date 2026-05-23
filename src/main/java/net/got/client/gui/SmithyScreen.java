package net.got.client.gui;

import net.got.block.SmithyBlockEntity;
import net.got.menu.SmithyMenu;
import net.got.network.SelectSmithyRecipePayload;
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
 * SmithyScreen — GUI for the Smithy block.
 *
 * imageWidth=256, imageHeight=166
 *
 * Section layout (all coordinates relative to leftPos / topPos):
 *
 *   LEFT COLUMN  (x 4–35):
 *     Header bar  y = 0–13
 *     Input slot  (11, 17)
 *     Fire anim   (12, 36–50)
 *     Fuel slot   (11, 55)
 *
 *   DIVIDER      x = 36–37
 *
 *   MIDDLE       (x 38–182):
 *     Header bar  y = 0–13
 *     Recipe list y = 14–68   ← 3 rows × 18 px  (ends at 68, safe above inventory)
 *
 *   DIVIDER      x = 183–184
 *
 *   RIGHT COLUMN (x 185–255):
 *     Header bar  y = 0–13
 *     Output slot (196, 17)
 *     Progress    y = 38–44
 *     Smelt btn   y = 48–61
 *
 *   PLAYER INV   y = 84 / 142  (vanilla positions – unchanged in SmithyMenu)
 */
public class SmithyScreen extends AbstractContainerScreen<SmithyMenu> {

    // ── Sprites ───────────────────────────────────────────────────────────────
    private static final ResourceLocation LIT_SPRITE =
            ResourceLocation.withDefaultNamespace("container/furnace/lit_progress");
    private static final ResourceLocation BURN_SPRITE =
            ResourceLocation.withDefaultNamespace("container/furnace/burn_progress");

    // ── Palette ───────────────────────────────────────────────────────────────
    private static final int C_BG          = 0xFF_1E1A14;
    private static final int C_PANEL       = 0xFF_27201A;
    private static final int C_PANEL_DARK  = 0xFF_15100D;
    private static final int C_HDR         = 0xFF_342A1E;
    private static final int C_BORDER_LT   = 0xFF_60503A;
    private static final int C_BORDER_DK   = 0xFF_0D0B08;
    private static final int C_SLOT_BG     = 0xFF_0F0D0A;
    private static final int C_SLOT_BDR    = 0xFF_6B5B3E;
    private static final int C_DIV         = 0xFF_3A2F1F;
    private static final int C_HDR_TXT     = 0xFF_C8A86A;
    private static final int C_ROW_NORMAL  = 0xFF_1E1912;
    private static final int C_ROW_HOVER   = 0xFF_38301F;
    private static final int C_ROW_SEL     = 0xFF_56441A;
    private static final int C_ROW_PENDING = 0xFF_3D3018;
    private static final int C_NAME_NORMAL = 0xFF_C8BC9A;
    private static final int C_NAME_SEL    = 0xFF_FFD700;
    private static final int C_NAME_PEND   = 0xFF_E8CF80;
    private static final int C_BTN_NORM    = 0xFF_4A3B20;
    private static final int C_BTN_HOVER   = 0xFF_6A5528;
    private static final int C_BTN_DIM     = 0xFF_252016;
    private static final int C_BTN_TXT     = 0xFF_E8D49A;
    private static final int C_BTN_TXT_DIM = 0xFF_4A4030;
    private static final int C_PROG_BG     = 0xFF_100E0A;
    private static final int C_PROG_FG     = 0xFF_C87820;
    private static final int C_PROG_CAP    = 0xFF_FFAE40;

    // ── Recipe list (relative) ────────────────────────────────────────────────
    private static final int LIST_X       = 38;
    private static final int LIST_Y       = 15;     // below the header bar
    private static final int LIST_W       = 145;
    private static final int ENTRY_H      = 18;
    private static final int VISIBLE_ROWS = 3;      // 3 rows → list ends at y=69, safely above inventory at y=84
    private static final int LIST_H       = VISIBLE_ROWS * ENTRY_H; // 54

    // ── Smelt button / progress (relative) ────────────────────────────────────
    private static final int PROG_X = 185;
    private static final int PROG_Y = 40;
    private static final int PROG_W = 60;
    private static final int PROG_H = 6;
    private static final int BTN_X  = 185;
    private static final int BTN_Y  = 50;
    private static final int BTN_W  = 60;
    private static final int BTN_H  = 14;

    // ── Header bar height ─────────────────────────────────────────────────────
    private static final int HDR_H = 13;

    // ── State ─────────────────────────────────────────────────────────────────
    private int scrollOffset = 0;
    private List<RecipeHolder<SmithyRecipe>> recipes   = List.of();
    private ItemStack lastInput = ItemStack.EMPTY;
    private int pendingIdx = -1;   // locally highlighted, sent on Smelt press

    // ──────────────────────────────────────────────────────────────────────────

    public SmithyScreen(SmithyMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        this.imageWidth      = 256;
        this.imageHeight     = 166;
        // Suppress default label positions – we override renderLabels() below.
        this.titleLabelX     = -9999;
        this.titleLabelY     = -9999;
        this.inventoryLabelX = -9999;
        this.inventoryLabelY = -9999;
    }

    // ── Override renderLabels so default "Smithy" / "Inventory" don't appear ──

    @Override
    protected void renderLabels(GuiGraphics g, int mouseX, int mouseY) {
        // Intentionally empty – we draw our own section headers in renderBg.
        // This prevents the container title from stomping our layout.
    }

    // ── Main render ───────────────────────────────────────────────────────────

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        refreshRecipeList();
        renderBackground(g, mouseX, mouseY, partialTick);
        super.render(g, mouseX, mouseY, partialTick);
        renderRecipePanel(g, mouseX, mouseY);
        renderOutputPanel(g, mouseX, mouseY);
        renderTooltip(g, mouseX, mouseY);
        renderRecipeTooltip(g, mouseX, mouseY);
    }

    // ── Background ────────────────────────────────────────────────────────────

    @Override
    protected void renderBg(GuiGraphics g, float pt, int mouseX, int mouseY) {
        int x = leftPos;
        int y = topPos;

        // ── Outer frame ───────────────────────────────────────────────────────
        outerPanel(g, x, y, imageWidth, imageHeight);

        // ── Header bar (full width) ───────────────────────────────────────────
        g.fill(x + 1,      y + 1, x + imageWidth - 1, y + HDR_H, C_HDR);
        // Thin gold underline
        g.fill(x + 1, y + HDR_H, x + imageWidth - 1, y + HDR_H + 1, C_BORDER_LT);

        // Section labels in header
        g.drawString(font, "INPUT",
                x + 4,           y + 3, C_HDR_TXT, false);
        g.drawString(font, "RECIPES",
                x + LIST_X + 2,  y + 3, C_HDR_TXT, false);
        g.drawString(font, "OUTPUT",
                x + 185,         y + 3, C_HDR_TXT, false);

        // Inventory label (drawn manually so it's in the right spot)
        g.drawString(font, "Inventory",
                x + 8, y + imageHeight - 94, 0xFF_888070, false);

        // ── Vertical dividers ─────────────────────────────────────────────────
        divV(g, x + 36,  y + HDR_H + 1, imageHeight - HDR_H - 26);
        divV(g, x + 183, y + HDR_H + 1, imageHeight - HDR_H - 26);

        // ── Left column: slots and fire ───────────────────────────────────────
        drawSlot(g, x + SmithyMenu.INPUT_X - 1, y + SmithyMenu.INPUT_Y - 1);

        // Fire animation (between input and fuel)
        if (menu.isFlaming()) {
            int k = menu.getFlameProgress(); // 0..13
            if (k > 0) {
                g.blitSprite(RenderType::guiTextured, LIT_SPRITE,
                        14, 14, 0, 14 - k,
                        x + 12, y + 36 + (14 - k), 14, k);
            }
        }

        drawSlot(g, x + SmithyMenu.FUEL_X - 1, y + SmithyMenu.FUEL_Y - 1);
    }

    // ── Recipe list panel ─────────────────────────────────────────────────────

    private void renderRecipePanel(GuiGraphics g, int mouseX, int mouseY) {
        int px = leftPos + LIST_X;
        int py = topPos  + LIST_Y;

        int selectedIdx = menu.getSelectedRecipeIndex();
        int maxScroll   = Math.max(0, recipes.size() - VISIBLE_ROWS);
        scrollOffset    = Math.min(scrollOffset, maxScroll);

        // Inset background
        inset(g, px - 2, py - 2, LIST_W + 4, LIST_H + 4);

        if (recipes.isEmpty()) {
            String msg = lastInput.isEmpty() ? "Put an ingot in the input slot" : "No smithy recipes";
            int tw = font.width(msg);
            g.drawString(font, msg,
                    px + (LIST_W - tw) / 2,
                    py + LIST_H / 2 - 4,
                    0xFF_554A30, false);
            return;
        }

        for (int i = 0; i < VISIBLE_ROWS; i++) {
            int ri = i + scrollOffset;
            if (ri >= recipes.size()) break;

            RecipeHolder<SmithyRecipe> holder = recipes.get(ri);
            ItemStack result = holder.value().getResult();
            int ry = py + i * ENTRY_H;

            boolean hovered  = mouseX >= px && mouseX < px + LIST_W
                    && mouseY >= ry && mouseY < ry + ENTRY_H;
            boolean isActive  = (ri == selectedIdx);
            boolean isPending = (ri == pendingIdx);

            int rowBg = isActive ? C_ROW_SEL
                    : isPending  ? C_ROW_PENDING
                    : hovered    ? C_ROW_HOVER
                    :              C_ROW_NORMAL;
            g.fill(px, ry, px + LIST_W, ry + ENTRY_H, rowBg);

            if (isActive || isPending) {
                g.fill(px, ry, px + 2, ry + ENTRY_H, isActive ? 0xFF_D4A830 : 0xFF_8A7040);
            }

            g.renderItem(result, px + 1, ry + 1);
            g.renderItemDecorations(font, result, px + 1, ry + 1);

            String name = result.getHoverName().getString();
            int maxW = LIST_W - 22;
            if (font.width(name) > maxW)
                name = font.plainSubstrByWidth(name, maxW - 6) + "…";

            int nameCol = isActive ? C_NAME_SEL : isPending ? C_NAME_PEND : C_NAME_NORMAL;
            g.drawString(font, name, px + 20, ry + 5, nameCol, false);
        }

        // Scrollbar
        if (recipes.size() > VISIBLE_ROWS) {
            int sbX    = px + LIST_W + 2;
            int thumbH = Math.max(6, LIST_H * VISIBLE_ROWS / recipes.size());
            int thumbY = py + (LIST_H - thumbH) * scrollOffset / Math.max(1, maxScroll);
            g.fill(sbX, py, sbX + 3, py + LIST_H, 0xFF_1A1510);
            g.fill(sbX, thumbY, sbX + 3, thumbY + thumbH, C_BORDER_LT);
        }
    }

    // ── Output panel ─────────────────────────────────────────────────────────

    private void renderOutputPanel(GuiGraphics g, int mouseX, int mouseY) {
        int x = leftPos;
        int y = topPos;

        drawSlot(g, x + SmithyMenu.OUTPUT_X - 1, y + SmithyMenu.OUTPUT_Y - 1);

        // Progress bar
        int px = x + PROG_X, py = y + PROG_Y;
        g.fill(px, py, px + PROG_W, py + PROG_H, C_PROG_BG);
        g.fill(px, py, px + 1, py + PROG_H, 0xFF_080705); // left shadow
        g.fill(px, py, px + PROG_W, py + 1, 0xFF_080705); // top shadow
        if (menu.isCrafting()) {
            int filled = menu.getArrowProgress() * PROG_W / 22;
            if (filled > 0) {
                g.fill(px + 1, py + 1, px + 1 + filled, py + PROG_H - 1, C_PROG_FG);
                g.fill(px + filled, py + 1, px + 1 + filled, py + PROG_H - 1, C_PROG_CAP);
            }
        }

        // Smelt button
        int bx = x + BTN_X, by = y + BTN_Y;
        boolean canSmelt = pendingIdx >= 0 || menu.getSelectedRecipeIndex() >= 0;
        boolean btnHov   = mouseX >= bx && mouseX < bx + BTN_W
                        && mouseY >= by && mouseY < by + BTN_H;

        int btnBg = !canSmelt ? C_BTN_DIM : btnHov ? C_BTN_HOVER : C_BTN_NORM;
        g.fill(bx,     by,     bx + BTN_W,     by + BTN_H,     C_BORDER_DK);
        g.fill(bx,     by,     bx + BTN_W - 1, by + BTN_H - 1, C_BORDER_LT);
        g.fill(bx + 1, by + 1, bx + BTN_W - 1, by + BTN_H - 1, btnBg);

        String lbl = "SMELT";
        int lw = font.width(lbl);
        g.drawString(font, lbl,
                bx + (BTN_W - lw) / 2, by + (BTN_H - 8) / 2 + 1,
                canSmelt ? C_BTN_TXT : C_BTN_TXT_DIM, false);
    }

    // ── Recipe tooltip ────────────────────────────────────────────────────────

    private void renderRecipeTooltip(GuiGraphics g, int mouseX, int mouseY) {
        int px = leftPos + LIST_X, py = topPos + LIST_Y;
        for (int i = 0; i < VISIBLE_ROWS; i++) {
            int ri = i + scrollOffset;
            if (ri >= recipes.size()) break;
            int ry = py + i * ENTRY_H;
            if (mouseX >= px && mouseX < px + LIST_W && mouseY >= ry && mouseY < ry + ENTRY_H) {
                g.renderTooltip(font, recipes.get(ri).value().getResult(), mouseX, mouseY);
                break;
            }
        }
    }

    // ── Input ─────────────────────────────────────────────────────────────────

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            int mx = (int) mouseX, my = (int) mouseY;

            int lx = leftPos + LIST_X, ly = topPos + LIST_Y;
            if (mx >= lx && mx < lx + LIST_W && my >= ly && my < ly + LIST_H) {
                int ri = (my - ly) / ENTRY_H + scrollOffset;
                if (ri >= 0 && ri < recipes.size()) {
                    pendingIdx = (ri == pendingIdx) ? -1 : ri;
                    return true;
                }
            }

            int bx = leftPos + BTN_X, by = topPos + BTN_Y;
            if (mx >= bx && mx < bx + BTN_W && my >= by && my < by + BTN_H) {
                int toSend = pendingIdx >= 0 ? pendingIdx : menu.getSelectedRecipeIndex();
                if (toSend >= 0)
                    PacketDistributor.sendToServer(new SelectSmithyRecipePayload(toSend));
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mx, double my, double sx, double sy) {
        int lx = leftPos + LIST_X - 2, ly = topPos + LIST_Y - 2;
        if (mx >= lx && mx < lx + LIST_W + 8 && my >= ly && my < ly + LIST_H + 4) {
            int maxS = Math.max(0, recipes.size() - VISIBLE_ROWS);
            scrollOffset = (int) Math.max(0, Math.min(maxS, scrollOffset - sy));
            return true;
        }
        return super.mouseScrolled(mx, my, sx, sy);
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private void refreshRecipeList() {
        ItemStack input = menu.getInputItem();
        if (!ItemStack.isSameItemSameComponents(input, lastInput)) {
            lastInput  = input.copy();
            recipes    = menu.getMatchingRecipes();
            scrollOffset = 0;
            pendingIdx   = -1;
        }
    }

    // ── Draw primitives ───────────────────────────────────────────────────────

    private void outerPanel(GuiGraphics g, int x, int y, int w, int h) {
        g.fill(x, y, x + w, y + h, C_PANEL);
        g.fill(x,         y,         x + w,     y + 1,     C_BORDER_LT);
        g.fill(x,         y,         x + 1,     y + h,     C_BORDER_LT);
        g.fill(x,         y + h - 1, x + w,     y + h,     C_BORDER_DK);
        g.fill(x + w - 1, y,         x + w,     y + h,     C_BORDER_DK);
    }

    private void inset(GuiGraphics g, int x, int y, int w, int h) {
        g.fill(x,         y,         x + w,     y + h,     C_BORDER_DK);
        g.fill(x + 1,     y + 1,     x + w - 1, y + h - 1, C_PANEL_DARK);
        g.fill(x + w - 1, y,         x + w,     y + h,     C_BORDER_LT);
        g.fill(x,         y + h - 1, x + w,     y + h,     C_BORDER_LT);
    }

    private void drawSlot(GuiGraphics g, int x, int y) {
        g.fill(x,     y,     x + 18, y + 18, C_SLOT_BDR);
        g.fill(x + 1, y + 1, x + 17, y + 17, C_SLOT_BG);
    }

    private void divV(GuiGraphics g, int x, int y, int h) {
        g.fill(x,     y, x + 1, y + h, C_BORDER_DK);
        g.fill(x + 1, y, x + 2, y + h, C_DIV);
    }
}
