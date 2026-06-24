package net.got.menu;

import net.got.block.SmithingAnvilBlockEntity;
import net.got.init.GotModDataComponents;
import net.got.init.GotModMenus;
import net.got.recipe.SmithyRecipe;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SmithingAnvilMenu — container menu for the Smithing Anvil block.
 *
 * Slot layout in the menu (indices used by AbstractContainerMenu.slots):
 *   0–26 — player main inventory
 *   27–35 — player hotbar
 *   36    — anvil input  (container slot 0)
 *   37    — anvil output (container slot 1)
 */
public class SmithingAnvilMenu extends AbstractContainerMenu {

    public static final int INPUT_X  = 20;
    public static final int INPUT_Y  = 33;
    public static final int OUTPUT_X = 143;
    public static final int OUTPUT_Y = 33;

    private final Container   container;
    private final ContainerData data;
    private final Level         level;

    private static final int PLAYER_INV_START = 0;
    private static final int PLAYER_INV_END   = 36;
    private static final int TE_INPUT_IDX     = 36;
    private static final int TE_OUTPUT_IDX    = 37;

    /** Client-side constructor (called by MenuType factory). */
    public SmithingAnvilMenu(int windowId, Inventory playerInv) {
        this(windowId, playerInv,
                new SimpleContainer(SmithingAnvilBlockEntity.NUM_SLOTS),
                new SimpleContainerData(SmithingAnvilBlockEntity.NUM_DATA));
    }

    /** Server-side constructor (called by SmithingAnvilBlockEntity). */
    public SmithingAnvilMenu(int windowId, Inventory playerInv,
                             Container container, ContainerData data) {
        super(GotModMenus.SMITHING_ANVIL.get(), windowId);
        this.container = container;
        this.data      = data;
        this.level     = playerInv.player.level();

        checkContainerSize(container, SmithingAnvilBlockEntity.NUM_SLOTS);
        checkContainerDataCount(data, SmithingAnvilBlockEntity.NUM_DATA);
        container.startOpen(playerInv.player);

        // Player main inventory (rows 0–2)
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 9; col++)
                this.addSlot(new Slot(playerInv, col + row * 9 + 9,
                        8 + col * 18, 84 + row * 18));

        // Hotbar
        for (int col = 0; col < 9; col++)
            this.addSlot(new Slot(playerInv, col, 8 + col * 18, 142));

        // Anvil slots
        this.addSlot(new HotIngotSlot(container, SmithingAnvilBlockEntity.SLOT_INPUT, INPUT_X, INPUT_Y));
        this.addSlot(new ResultSlot(playerInv.player, container,
                SmithingAnvilBlockEntity.SLOT_OUTPUT, OUTPUT_X, OUTPUT_Y));

        this.addDataSlots(data);
    }

    // ── Progress helpers (used by SmithingAnvilScreen) ────────────────────────

    public int getHitCount() {
        return data.get(SmithingAnvilBlockEntity.DATA_HIT_COUNT);
    }

    public int getHitsRequired() {
        return SmithingAnvilBlockEntity.HITS_REQUIRED;
    }

    public int getSelectedRecipeIndex() {
        return data.get(SmithingAnvilBlockEntity.DATA_SELECTED_RECIPE);
    }

    public int getMarkerPos() {
        return data.get(SmithingAnvilBlockEntity.DATA_MARKER_POS);
    }

    public int getLastHitQuality() {
        return data.get(SmithingAnvilBlockEntity.DATA_LAST_HIT_QUALITY);
    }

    public int getZoneCenter() { return SmithingAnvilBlockEntity.ZONE_CENTER; }
    public int getZoneHalf()   { return SmithingAnvilBlockEntity.ZONE_HALF; }

    public ItemStack getInputItem() {
        return container.getItem(SmithingAnvilBlockEntity.SLOT_INPUT);
    }

    public Container getContainer() { return container; }

    // ── Client-side recipe list ───────────────────────────────────────────────

    public List<RecipeHolder<SmithyRecipe>> getMatchingRecipes() {
        ItemStack input = getInputItem();
        if (input.isEmpty()) return List.of();
        SingleRecipeInput ri = new SingleRecipeInput(input);
        return net.got.client.SmithyClientRecipes.get()
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
            if (!this.moveItemStackTo(stack, TE_INPUT_IDX, TE_INPUT_IDX + 1, false))
                return ItemStack.EMPTY;
        } else {
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

    /** Input slot — only accepts hot ingots, one at a time. */
    private static class HotIngotSlot extends Slot {
        HotIngotSlot(Container c, int slot, int x, int y) { super(c, slot, x, y); }
        @Override public boolean mayPlace(ItemStack stack) {
            return stack.has(GotModDataComponents.HOT.get());
        }
        @Override public int getMaxStackSize() { return 1; }
        @Override public int getMaxStackSize(ItemStack stack) { return 1; }
    }

    private static class ResultSlot extends Slot {
        ResultSlot(Player player, Container c, int slot, int x, int y) {
            super(c, slot, x, y);
        }
        @Override public boolean mayPlace(ItemStack stack) { return false; }
    }
}