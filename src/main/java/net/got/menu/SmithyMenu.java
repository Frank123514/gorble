package net.got.menu;

import net.got.block.ForgeBlockEntity;
import net.got.init.ModMenus;
import net.got.init.ModRecipeTypes;
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

public class SmithyMenu extends AbstractContainerMenu {

    public static final int INPUT_X  = 20;
    public static final int INPUT_Y  = 19;
    public static final int FUEL_X   = 20;
    public static final int FUEL_Y   = 53;
    public static final int OUTPUT_X = 143;
    public static final int OUTPUT_Y = 33;

    private final Container   container;
    private final ContainerData data;
    private final Level         level;

    private static final int PLAYER_INV_START  = 0;
    private static final int PLAYER_INV_END    = 36;
    private static final int TE_INPUT_IDX      = 36;
    private static final int TE_FUEL_IDX       = 37;
    private static final int TE_OUTPUT_IDX     = 38;

    public SmithyMenu(int windowId, Inventory playerInv) {
        this(windowId, playerInv,
                new SimpleContainer(ForgeBlockEntity.NUM_SLOTS),
                new SimpleContainerData(ForgeBlockEntity.NUM_DATA));
    }

    public SmithyMenu(int windowId, Inventory playerInv,
                      Container container, ContainerData data) {
        super(ModMenus.SMITHY.get(), windowId);
        this.container = container;
        this.data      = data;
        this.level     = playerInv.player.level();

        checkContainerSize(container, ForgeBlockEntity.NUM_SLOTS);
        checkContainerDataCount(data, ForgeBlockEntity.NUM_DATA);
        container.startOpen(playerInv.player);

        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 9; col++)
                this.addSlot(new Slot(playerInv, col + row * 9 + 9,
                        8 + col * 18, 84 + row * 18));

        for (int col = 0; col < 9; col++)
            this.addSlot(new Slot(playerInv, col, 8 + col * 18, 142));

        this.addSlot(new Slot(container, ForgeBlockEntity.SLOT_HEAT_A, INPUT_X, INPUT_Y));

        this.addSlot(new FuelSlot(container, ForgeBlockEntity.SLOT_FUEL,
                FUEL_X, FUEL_Y, level));

        this.addSlot(new ResultSlot(playerInv.player, container,
                ForgeBlockEntity.SLOT_OUTPUT, OUTPUT_X, OUTPUT_Y));

        this.addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(ForgeBlockEntity.DATA_COOKING_PROGRESS) > 0;
    }

    public boolean isFlaming() {
        return data.get(ForgeBlockEntity.DATA_LIT_TIME) > 0;
    }

    public int getArrowProgress() {
        int progress = data.get(ForgeBlockEntity.DATA_COOKING_PROGRESS);
        int total    = data.get(ForgeBlockEntity.DATA_COOKING_TOTAL);
        return (total != 0 && progress != 0) ? progress * 24 / total : 0;
    }

    public int getFlameProgress() {
        int duration = data.get(ForgeBlockEntity.DATA_LIT_DURATION);
        if (duration == 0) duration = 200;
        return data.get(ForgeBlockEntity.DATA_LIT_TIME) * 13 / duration;
    }

    public int getSelectedRecipeIndex() {
        return data.get(ForgeBlockEntity.DATA_SELECTED_RECIPE);
    }

    public ItemStack getInputItem() {
        return container.getItem(ForgeBlockEntity.SLOT_HEAT_A);
    }

    public Container getContainer() { return container; }

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

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = this.slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack stack = slot.getItem();
        ItemStack copy  = stack.copy();

        if (index >= PLAYER_INV_START && index < PLAYER_INV_END) {
            
            if (!this.moveItemStackTo(stack, TE_FUEL_IDX, TE_FUEL_IDX + 1, false)) {
                if (!this.moveItemStackTo(stack, TE_INPUT_IDX, TE_INPUT_IDX + 1, false))
                    return ItemStack.EMPTY;
            }
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

    private static class FuelSlot extends Slot {
        private final Level level;
        FuelSlot(Container c, int slot, int x, int y, Level level) {
            super(c, slot, x, y);
            this.level = level;
        }
        @Override
        public boolean mayPlace(ItemStack stack) {
            return stack.getBurnTime(ModRecipeTypes.SMITHY.get(),
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