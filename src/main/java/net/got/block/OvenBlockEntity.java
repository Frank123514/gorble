package net.got.block;

import net.got.init.GotModBlockEntities;
import net.got.init.GotModRecipeTypes;
import net.got.menu.OvenMenu;
import net.got.recipe.BakingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.Optional;

public class OvenBlockEntity extends BaseContainerBlockEntity {

    private NonNullList<ItemStack> items = NonNullList.withSize(OvenMenu.TOTAL_SLOTS, ItemStack.EMPTY);

    private int burnTime;
    private int burnDuration;
    // FIX: added cookingProgress — was missing entirely, so baking never completed
    private int cookingProgress;

    protected final ContainerData dataAccess = new ContainerData() {
        @Override public int get(int i) {
            return switch (i) {
                case 0 -> burnTime;
                case 1 -> burnDuration;
                case 2 -> cookingProgress;
                default -> 0;
            };
        }
        @Override public void set(int i, int v) {
            switch (i) {
                case 0 -> burnTime      = v;
                case 1 -> burnDuration  = v;
                case 2 -> cookingProgress = v;
            }
        }
        @Override public int getCount() { return 3; }
    };

    public OvenBlockEntity(BlockPos pos, BlockState state) {
        super(GotModBlockEntities.OVEN.get(), pos, state);
    }

    // ── BaseContainerBlockEntity ───────────────────────────────────────────

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.got.oven");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return new OvenMenu(id, inventory, this, dataAccess);
    }

    @Override
    protected NonNullList<ItemStack> getItems() { return items; }

    @Override
    protected void setItems(NonNullList<ItemStack> items) { this.items = items; }

    @Override
    public int getContainerSize() { return OvenMenu.TOTAL_SLOTS; }

    // ── Fuel helper ────────────────────────────────────────────────────────

    /** Returns the burn time for an ItemStack using the NeoForge furnace fuel data map. */
    private static int getFuelTime(ItemStack stack) {
        if (stack.isEmpty()) return 0;
        var fuelData = stack.getItemHolder().getData(NeoForgeDataMaps.FURNACE_FUELS);
        return fuelData != null ? fuelData.burnTime() : 0;
    }

    // ── Server tick ────────────────────────────────────────────────────────

    /**
     * Full baking tick: manages fuel AND advances cooking progress.
     *
     * FIX: the original serverTick only burned fuel — it never checked for a
     * baking recipe, never advanced cookingProgress, and never produced output.
     * This version does all three.
     */
    public static void serverTick(Level level, BlockPos pos, BlockState state, OvenBlockEntity be) {

        boolean wasLit = be.burnTime > 0;

        // ── Fuel management ────────────────────────────────────────────────
        if (be.burnTime > 0) {
            be.burnTime--;
        }
        if (be.burnTime == 0) {
            ItemStack fuel = be.items.get(OvenMenu.FUEL_SLOT);
            int fuelTime = getFuelTime(fuel);
            if (fuelTime > 0) {
                be.burnDuration = fuelTime;
                be.burnTime     = fuelTime;
                fuel.shrink(1);
                if (fuel.isEmpty()) be.items.set(OvenMenu.FUEL_SLOT, ItemStack.EMPTY);
            } else {
                be.burnDuration = 0;
            }
        }

        // ── Baking logic ───────────────────────────────────────────────────
        // The oven only bakes from slot GRID_START (the first input slot).
        // Place dough there; the rest of the 3×3 grid is ignored for baking.
        if (be.burnTime > 0) {
            ItemStack input = be.items.get(OvenMenu.GRID_START);
            if (!input.isEmpty()) {
                ServerLevel serverLevel = (ServerLevel) level;
                SingleRecipeInput recipeInput = new SingleRecipeInput(input);
                Optional<RecipeHolder<BakingRecipe>> match =
                        serverLevel.getServer().getRecipeManager()
                                .getRecipeFor(GotModRecipeTypes.BAKING.get(), recipeInput, serverLevel);

                if (match.isPresent()) {
                    BakingRecipe recipe = match.get().value();
                    be.cookingProgress++;

                    if (be.cookingProgress >= recipe.getCookingTime()) {
                        be.cookingProgress = 0;
                        ItemStack result = recipe.assemble(recipeInput, level.registryAccess());
                        ItemStack output = be.items.get(OvenMenu.OUTPUT_SLOT);

                        if (output.isEmpty()) {
                            be.items.set(OvenMenu.OUTPUT_SLOT, result);
                        } else if (ItemStack.isSameItemSameComponents(output, result)
                                && output.getCount() + result.getCount() <= output.getMaxStackSize()) {
                            output.grow(result.getCount());
                        }
                        // Only consume one dough if output could accept the result
                        // (guard above means we don't consume if slot was already full)
                        if (output.isEmpty() || ItemStack.isSameItemSameComponents(output, result)) {
                            input.shrink(1);
                        }
                        be.setChanged();
                    }
                } else {
                    // No matching recipe — reset progress
                    be.cookingProgress = 0;
                }
            } else {
                be.cookingProgress = 0;
            }
        } else {
            // Not lit — pause progress (don't reset so fuel restocking resumes cooking)
        }

        // ── Block-state lit flag ───────────────────────────────────────────
        boolean isLit = be.burnTime > 0;
        if (wasLit != isLit) {
            state = state.setValue(AbstractFurnaceBlock.LIT, isLit);
            level.setBlock(pos, state, 3);
            be.setChanged();
        }
    }

    // ── NBT persistence ────────────────────────────────────────────────────

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, items, registries);
        tag.putInt("BurnTime",       burnTime);
        tag.putInt("BurnDuration",   burnDuration);
        // FIX: persist cookingProgress so baking survives chunk reloads
        tag.putInt("CookingProgress", cookingProgress);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, items, registries);
        burnTime       = tag.getInt("BurnTime");
        burnDuration   = tag.getInt("BurnDuration");
        cookingProgress = tag.getInt("CookingProgress");
    }
}