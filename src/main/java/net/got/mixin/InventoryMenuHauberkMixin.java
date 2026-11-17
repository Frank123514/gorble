package net.got.mixin;

import net.got.hauberk.HauberkItem;
import net.got.hauberk.HauberkSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Adds the hauberk slot to the player's inventory menu. It is appended after the vanilla
 * slots, so every vanilla slot index stays the same.
 */
@Mixin(InventoryMenu.class)
public abstract class InventoryMenuHauberkMixin extends AbstractContainerMenu {

    // Position in the survival inventory: directly above the gambeson slot, right of the player preview.
    private static final int HAUBERK_X = 77;
    private static final int HAUBERK_Y = 26;

    protected InventoryMenuHauberkMixin(MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }

    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    private void got$addHauberkSlot(Inventory inventory, boolean active, Player owner, CallbackInfo ci) {
        this.addSlot(new HauberkSlot(owner, HAUBERK_X, HAUBERK_Y));
    }

    /** Shift-click: hauberk <-> inventory, and inventory hauberk -> the hauberk slot. */
    @Inject(method = "quickMoveStack", at = @At("HEAD"), cancellable = true, remap = false)
    private void got$quickMoveHauberk(Player player, int index, CallbackInfoReturnable<ItemStack> cir) {
        if (index < 0 || index >= this.slots.size()) return;
        Slot slot = this.slots.get(index);
        if (slot == null || !slot.hasItem()) return;
        ItemStack stack = slot.getItem();

        if (slot instanceof HauberkSlot) {
            ItemStack original = stack.copy();
            if (!this.moveItemStackTo(stack, 9, 45, false)) {
                cir.setReturnValue(ItemStack.EMPTY);
                return;
            }
            if (stack.isEmpty()) slot.setByPlayer(ItemStack.EMPTY); else slot.setChanged();
            cir.setReturnValue(original);
            return;
        }

        if (index >= 9 && index < 45 && stack.getItem() instanceof HauberkItem) {
            for (Slot other : this.slots) {
                if (other instanceof HauberkSlot && !other.hasItem()) {
                    ItemStack original = stack.copy();
                    other.setByPlayer(stack.split(1));
                    if (stack.isEmpty()) slot.setByPlayer(ItemStack.EMPTY); else slot.setChanged();
                    cir.setReturnValue(original);
                    return;
                }
            }
        }
    }
}
