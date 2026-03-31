package net.got.menu;

import net.got.init.GotModMenus;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

/**
 * Container menu for the Oven.
 *
 * Slot layout (mirrored from OFAW's OvenMenu):
 *
 *   Container slots:
 *     0 - 8  : 3×3 input grid   (GUI positions: 30 + col*18, 17 + row*18)
 *     9      : output            (GUI position:  124, 35)
 *     10     : fuel              (GUI position:  8,   53)
 *
 *   this.slots indices:
 *     0  - 26 : player inventory (playerInventory slots 9-35)
 *     27 - 35 : player hotbar    (playerInventory slots 0-8)
 *     36      : output (container slot 9)
 *     37      : fuel   (container slot 10)
 *     38 - 46 : grid   (container slots 0-8)
 *
 * ContainerData (4 values):
 *     data[0] : cookingProgress
 *     data[1] : cookingTime  (max)
 *     data[2] : burnTime     (remaining)
 *     data[3] : burnDuration (total for current fuel)
 */
public class OvenMenu extends AbstractContainerMenu {

    // Container slot indices
    public static final int GRID_START  = 0;
    public static final int GRID_SIZE   = 9;
    public static final int OUTPUT_SLOT = 9;
    public static final int FUEL_SLOT   = 10;
    public static final int TOTAL_SLOTS = 11;

    // this.slots index boundaries
    private static final int PLAYER_INV_ROWS    = 3;
    private static final int PLAYER_INV_COLS    = 9;
    private static final int PLAYER_SLOT_COUNT  = PLAYER_INV_ROWS * PLAYER_INV_COLS + PLAYER_INV_COLS; // 36
    private static final int TE_FIRST_SLOT      = PLAYER_SLOT_COUNT; // 36
    private static final int TE_SLOT_COUNT      = TOTAL_SLOTS;       // 11

    private final ContainerData data;
    private final Container container;
    private final Level level;

    /** Client-side constructor (called by MenuType). */
    public OvenMenu(int id, Inventory playerInventory) {
        this(id, playerInventory, new SimpleContainer(TOTAL_SLOTS), new SimpleContainerData(4));
    }

    /** Server-side constructor (called by OvenBlockEntity.createMenu). */
    public OvenMenu(int id, Inventory playerInventory, Container container, ContainerData data) {
        super(GotModMenus.OVEN.get(), id);
        checkContainerSize(container, TOTAL_SLOTS);
        checkContainerDataCount(data, 4);
        this.container = container;
        this.data      = data;
        this.level     = playerInventory.player.level();

        // ── Player inventory ───────────────────────────────────────────────
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory,
                        col + row * 9 + 9,
                        8 + col * 18, 84 + row * 18));
            }
        }
        // ── Player hotbar ──────────────────────────────────────────────────
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }

        // ── Output slot (read-only) ────────────────────────────────────────
        this.addSlot(new Slot(container, OUTPUT_SLOT, 124, 35) {
            @Override public boolean mayPlace(ItemStack stack) { return false; }
        });

        // ── Fuel slot (only accepts valid furnace fuels) ───────────────────
        this.addSlot(new Slot(container, FUEL_SLOT, 8, 53) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                if (stack.isEmpty()) return false;
                return stack.getItemHolder().getData(NeoForgeDataMaps.FURNACE_FUELS) != null;
            }
        });

        // ── 3×3 input grid ─────────────────────────────────────────────────
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                this.addSlot(new Slot(container,
                        GRID_START + col + row * 3,
                        30 + col * 18, 17 + row * 18));
            }
        }

        this.addDataSlots(data);
    }

    // ── Data accessors (for OvenScreen) ───────────────────────────────────

    public int  getCookingProgress() { return data.get(0); }
    public int  getCookingTime()     { return data.get(1); }
    public int  getBurnTime()        { return data.get(2); }
    public int  getBurnDuration()    { return data.get(3); }
    public boolean isCrafting()      { return getCookingProgress() > 0; }
    public boolean isFlaming()       { return getBurnTime() > 0; }

    /** Returns 0-26 arrow width in pixels (matches OFAW). */
    public int getArrowScaledProgress() {
        int progress    = getCookingProgress();
        int maxProgress = getCookingTime();
        int arrowSize   = 26;
        return (maxProgress != 0 && progress != 0) ? progress * arrowSize / maxProgress : 0;
    }

    /** Returns 0-13 flame height in pixels (matches OFAW). */
    public int getFlameScaledProgress() {
        int duration = getBurnDuration();
        if (duration == 0) duration = 200;
        return getBurnTime() * 13 / duration;
    }

    // ── AbstractContainerMenu ─────────────────────────────────────────────

    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = this.slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack stack    = slot.getItem();
        ItemStack original = stack.copy();

        if (index < TE_FIRST_SLOT) {
            // Player slot → move into TE (skip output slot, try fuel then grid)
            if (!this.moveItemStackTo(stack, TE_FIRST_SLOT, TE_FIRST_SLOT + TE_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            // TE slot → move into player inventory
            if (!this.moveItemStackTo(stack, 0, TE_FIRST_SLOT, false)) {
                return ItemStack.EMPTY;
            }
        }

        if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
        else slot.setChanged();

        return original;
    }
}
