package net.got.menu;

import net.got.block.SmithyBlockEntity;
import net.got.init.GotModMenus;
import net.got.init.GotModRecipeTypes;
import net.got.recipe.SmithyRecipe;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SmithyMenu — container menu for the Smithy block.
 *
 * Slot layout in the menu (indices used by AbstractContainerMenu.slots):
 *   0–26 — player main inventory
 *   27–35 — player hotbar
 *   36    — smithy input  (container slot 0)
 *   37    — smithy fuel   (container slot 1)
 *   38    — smithy output (container slot 2)
 *
 * GUI positions (relative to window top-left):
 *   Input  : (27, 17)
 *   Fuel   : (27, 55)
 *   Output : (77, 35)
 */
public class SmithyMenu extends AbstractContainerMenu {

    // GUI pixel positions of the three smithy slots
    // Left column: input (top) + fuel (bottom)
    // Right column: output (with smelt button beside it)
    public static final int INPUT_X  = 11;
    public static final int INPUT_Y  = 17;
    public static final int FUEL_X   = 11;
    public static final int FUEL_Y   = 55;
    public static final int OUTPUT_X = 196;
    public static final int OUTPUT_Y = 20;

    private final Container   container;
    private final ContainerData data;
    private final Level         level;

    // ── Slot index ranges in this.slots ──────────────────────────────────────
    private static final int PLAYER_INV_START  = 0;
    private static final int PLAYER_INV_END    = 36;   // exclusive
    private static final int TE_INPUT_IDX      = 36;
    private static final int TE_FUEL_IDX       = 37;
    private static final int TE_OUTPUT_IDX     = 38;

    /** Client-side constructor (called by MenuType factory). */
    public SmithyMenu(int windowId, Inventory playerInv) {
        this(windowId, playerInv,
                new SimpleContainer(SmithyBlockEntity.NUM_SLOTS),
                new SimpleContainerData(SmithyBlockEntity.NUM_DATA));
    }

    /** Server-side constructor (called by SmithyBlockEntity). */
    public SmithyMenu(int windowId, Inventory playerInv,
                      Container container, ContainerData data) {
        super(GotModMenus.SMITHY.get(), windowId);
        this.container = container;
        this.data      = data;
        this.level     = playerInv.player.level();

        checkContainerSize(container, SmithyBlockEntity.NUM_SLOTS);
        checkContainerDataCount(data, SmithyBlockEntity.NUM_DATA);
        container.startOpen(playerInv.player);

        // ── Player main inventory (rows 0–2) ──────────────────────────────────
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 9; col++)
                this.addSlot(new Slot(playerInv, col + row * 9 + 9,
                        8 + col * 18, 84 + row * 18));

        // ── Hotbar ────────────────────────────────────────────────────────────
        for (int col = 0; col < 9; col++)
            this.addSlot(new Slot(playerInv, col, 8 + col * 18, 142));

        // ── Smithy slots ──────────────────────────────────────────────────────
        // Input (slot 0)
        this.addSlot(new Slot(container, SmithyBlockEntity.SLOT_INPUT, INPUT_X, INPUT_Y));

        // Fuel (slot 1)
        this.addSlot(new FuelSlot(container, SmithyBlockEntity.SLOT_FUEL,
                FUEL_X, FUEL_Y, level));

        // Output (slot 2) — take-only
        this.addSlot(new ResultSlot(playerInv.player, container,
                SmithyBlockEntity.SLOT_OUTPUT, OUTPUT_X, OUTPUT_Y));

        this.addDataSlots(data);
    }

    // ── Progress helpers (used by SmithyScreen) ───────────────────────────────

    public boolean isCrafting() {
        return data.get(SmithyBlockEntity.DATA_COOKING_PROGRESS) > 0;
    }

    public boolean isFlaming() {
        return data.get(SmithyBlockEntity.DATA_LIT_TIME) > 0;
    }

    /** Arrow progress scaled to [0, 22]. */
    public int getArrowProgress() {
        int progress = data.get(SmithyBlockEntity.DATA_COOKING_PROGRESS);
        int total    = data.get(SmithyBlockEntity.DATA_COOKING_TOTAL);
        return (total != 0 && progress != 0) ? progress * 22 / total : 0;
    }

    /** Flame height scaled to [0, 13]. */
    public int getFlameProgress() {
        int duration = data.get(SmithyBlockEntity.DATA_LIT_DURATION);
        if (duration == 0) duration = 200;
        return data.get(SmithyBlockEntity.DATA_LIT_TIME) * 13 / duration;
    }

    /** Index of the selected recipe (-1 = none). */
    public int getSelectedRecipeIndex() {
        return data.get(SmithyBlockEntity.DATA_SELECTED_RECIPE);
    }

    public ItemStack getInputItem() {
        return container.getItem(SmithyBlockEntity.SLOT_INPUT);
    }

    /** Exposes the backing container so the network handler can reach the block entity. */
    public Container getContainer() { return container; }

    // ── Client-side recipe list ───────────────────────────────────────────────

    /**
     * Returns all SmithyRecipes that match the current input item,
     * sorted by their ResourceLocation so ordering is stable on both sides.
     *
     * Works on both the server (ServerLevel) and the client (ClientLevel),
     * because recipeAccess() is available on the base Level class and recipes
     * are synced to the client by vanilla.
     */
    public List<RecipeHolder<SmithyRecipe>> getMatchingRecipes() {
        ItemStack input = getInputItem();
        if (input.isEmpty()) return List.of();
        if (!(level.recipeAccess() instanceof RecipeManager rm)) return List.of();
        SingleRecipeInput ri = new SingleRecipeInput(input);
        return rm.getAllRecipesFor(GotModRecipeTypes.SMITHY.get())
                .stream()
                .filter(h -> h.value().matches(ri, level))
                .sorted(Comparator.comparing(h -> h.id().toString()))
                .collect(Collectors.toList());
    }

    // ── Shift-click ───────────────────────────────────────────────────────────

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = this.slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack stack = slot.getItem();
        ItemStack copy  = stack.copy();

        if (index >= PLAYER_INV_START && index < PLAYER_INV_END) {
            // Player → smithy: try fuel, then input
            if (!this.moveItemStackTo(stack, TE_FUEL_IDX, TE_FUEL_IDX + 1, false)) {
                if (!this.moveItemStackTo(stack, TE_INPUT_IDX, TE_INPUT_IDX + 1, false))
                    return ItemStack.EMPTY;
            }
        } else {
            // Smithy → player inventory
            if (!this.moveItemStackTo(stack, PLAYER_INV_START, PLAYER_INV_END, false))
                return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
        else slot.setChanged();
        if (stack.getCount() == copy.getCount()) return ItemStack.EMPTY;
        slot.onTake(player, stack);
        return copy;
    }

    @Override
    public boolean stillValid(Player player) { return container.stillValid(player); }

    @Override
    public void removed(Player player) {
        super.removed(player);
        container.stopOpen(player);
    }

    // ── Inner slot types ──────────────────────────────────────────────────────

    private static class FuelSlot extends Slot {
        private final Level level;
        FuelSlot(Container c, int slot, int x, int y, Level level) {
            super(c, slot, x, y);
            this.level = level;
        }
        @Override
        public boolean mayPlace(ItemStack stack) {
            return stack.getBurnTime(GotModRecipeTypes.SMITHY.get(),
                    level.fuelValues()) > 0;
        }
    }

    private static class ResultSlot extends Slot {
        ResultSlot(Player player, Container c, int slot, int x, int y) {
            super(c, slot, x, y);
        }
        @Override public boolean mayPlace(ItemStack stack) { return false; }
    }
}