package net.got.menu;

import net.got.init.GotModMenus;
import net.got.init.GotModRecipeTypes;
import net.got.recipe.BakingRecipe;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.core.NonNullList;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

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
        this(id, playerInventory, new SimpleContainer(TOTAL_SLOTS), new SimpleContainerData(3));
    }

    public OvenMenu(int id, Inventory playerInventory, Container container, ContainerData data) {
        super(GotModMenus.OVEN.get(), id);
        checkContainerSize(container, TOTAL_SLOTS);
        checkContainerDataCount(data, 3);
        this.container = container;
        this.data      = data;
        this.level     = playerInventory.player.level();

        // Fuel slot
        this.addSlot(new Slot(container, FUEL_SLOT, 14, 54) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                if (stack.isEmpty()) return false;
                return stack.getItemHolder().getData(NeoForgeDataMaps.FURNACE_FUELS) != null;
            }
        });

        // 3×3 input grid
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                this.addSlot(new Slot(container, GRID_START + row * 3 + col,
                        47 + col * 18, 17 + row * 18));
            }
        }

        // Output slot — read-only, consumes grid items on take
        this.addSlot(new Slot(container, OUTPUT_SLOT, 137, 36) {
            @Override public boolean mayPlace(ItemStack stack) { return false; }

            @Override
            public void onTake(Player player, ItemStack stack) {
                // Taking the output counts as consuming slot GRID_START only
                // (the actual shrink is handled by OvenBlockEntity; this covers
                //  edge cases where the player takes mid-cook from a synced menu)
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

    // ── Slots-changed callback ─────────────────────────────────────────────

    @Override
    public void slotsChanged(Container c) {
        super.slotsChanged(c);
        if (!level.isClientSide) updateResult();
    }

    /**
     * Previews the baking result in the output slot.
     *
     * FIX: the original queried RecipeType.CRAFTING with a 3×3 CraftingInput,
     * which never matches a got:baking recipe.  We now look up got:baking with
     * a SingleRecipeInput from slot GRID_START only.
     */
    private void updateResult() {
        ItemStack inputStack = container.getItem(GRID_START);
        boolean hasFuel = !container.getItem(FUEL_SLOT).isEmpty() || data.get(0) > 0;

        if (!inputStack.isEmpty() && hasFuel) {
            ServerLevel serverLevel = (ServerLevel) level;
            SingleRecipeInput recipeInput = new SingleRecipeInput(inputStack);
            Optional<RecipeHolder<BakingRecipe>> recipe =
                    serverLevel.getServer().getRecipeManager()
                            .getRecipeFor(GotModRecipeTypes.BAKING.get(), recipeInput, serverLevel);

            if (recipe.isPresent()) {
                container.setItem(OUTPUT_SLOT,
                        recipe.get().value().assemble(recipeInput, level.registryAccess()));
                return;
            }
        }
        container.setItem(OUTPUT_SLOT, ItemStack.EMPTY);
    }

    // ── Data accessors (forwarded to ContainerData) ────────────────────────

    public int  getBurnTime()      { return data.get(0); }
    public int  getBurnDuration()  { return data.get(1); }
    public int  getCookingProgress(){ return data.get(2); }
    public boolean isLit()         { return getBurnTime() > 0; }

    // ── AbstractContainerMenu ─────────────────────────────────────────────

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