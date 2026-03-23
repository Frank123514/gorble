package net.got.menu;

import net.got.init.GotModMenus;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.core.NonNullList;

import java.util.Optional;

public class OvenMenu extends AbstractContainerMenu {

    public static final int FUEL_SLOT   = 0;
    public static final int GRID_START  = 1;
    public static final int GRID_SIZE   = 9;
    public static final int OUTPUT_SLOT = 10;
    public static final int TOTAL_SLOTS = 11;

    private final ContainerData data;
    private final Container container;
    private final Level level;

    public OvenMenu(int id, Inventory playerInventory) {
        this(id, playerInventory, new SimpleContainer(TOTAL_SLOTS), new SimpleContainerData(2));
    }

    public OvenMenu(int id, Inventory playerInventory, Container container, ContainerData data) {
        super(GotModMenus.OVEN.get(), id);
        checkContainerSize(container, TOTAL_SLOTS);
        checkContainerDataCount(data, 2);
        this.container = container;
        this.data = data;
        this.level = playerInventory.player.level();

        // Fuel slot — plain slot, accepts any fuel item
        this.addSlot(new Slot(container, FUEL_SLOT, 14, 54) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return net.neoforged.neoforge.common.CommonHooks.getBurnTime(stack, null) > 0;
            }
        });

        // 3x3 crafting grid
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                this.addSlot(new Slot(container, GRID_START + row * 3 + col,
                        47 + col * 18, 17 + row * 18));
            }
        }

        // Output slot
        this.addSlot(new Slot(container, OUTPUT_SLOT, 137, 36) {
            @Override public boolean mayPlace(ItemStack stack) { return false; }
            @Override
            public void onTake(Player player, ItemStack stack) {
                for (int i = GRID_START; i < GRID_START + GRID_SIZE; i++) {
                    container.removeItem(i, 1);
                }
                slotsChanged(container);
                super.onTake(player, stack);
            }
        });

        // Player inventory
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory,
                        col + row * 9 + 9, 3 + col * 18, 84 + row * 18));
            }
        }
        // Hotbar
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 3 + col * 18, 142));
        }

        this.addDataSlots(data);
    }

    private CraftingInput getCraftingInput() {
        NonNullList<ItemStack> items = NonNullList.withSize(9, ItemStack.EMPTY);
        for (int i = 0; i < 9; i++) {
            items.set(i, container.getItem(GRID_START + i));
        }
        return CraftingInput.of(3, 3, items);
    }

    @Override
    public void slotsChanged(Container c) {
        super.slotsChanged(c);
        if (!level.isClientSide) updateResult();
    }

    private void updateResult() {
        CraftingInput input = getCraftingInput();
        boolean hasFuel = !container.getItem(FUEL_SLOT).isEmpty() || data.get(0) > 0;
        Optional<RecipeHolder<CraftingRecipe>> recipe =
                level.recipeAccess().getRecipeFor(
                        net.minecraft.world.item.crafting.RecipeType.CRAFTING, input, level);
        if (recipe.isPresent() && hasFuel) {
            container.setItem(OUTPUT_SLOT,
                    recipe.get().value().assemble(input, level.registryAccess()));
        } else {
            container.setItem(OUTPUT_SLOT, ItemStack.EMPTY);
        }
    }

    public int getBurnTime()     { return data.get(0); }
    public int getBurnDuration() { return data.get(1); }
    public boolean isLit()       { return getBurnTime() > 0; }

    @Override
    public boolean stillValid(Player player) { return container.stillValid(player); }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            result = stack.copy();
            if (index < TOTAL_SLOTS) {
                if (!this.moveItemStackTo(stack, TOTAL_SLOTS, this.slots.size(), true))
                    return ItemStack.EMPTY;
            } else {
                if (!this.moveItemStackTo(stack, 0, TOTAL_SLOTS - 1, false))
                    return ItemStack.EMPTY;
            }
            if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
            else slot.setChanged();
        }
        return result;
    }
}
