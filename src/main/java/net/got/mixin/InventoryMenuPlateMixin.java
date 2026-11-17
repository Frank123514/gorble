package net.got.mixin;

import net.got.plate.PlateItem;
import net.got.plate.PlatePiece;
import net.got.plate.PlateSlot;
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
 * Adds the five plate slots (gorget, spaulders, vambraces, breastplate, cuisses) to the player's inventory
 * menu. They are appended after the vanilla slots, so every vanilla slot index stays the same. Positions come
 * from {@link PlatePiece}.
 */
@Mixin(value = InventoryMenu.class, priority = 1600)
public abstract class InventoryMenuPlateMixin extends AbstractContainerMenu {

    protected InventoryMenuPlateMixin(MenuType<?> type, int containerId) {
        super(type, containerId);
    }

    @Inject(method = "<init>", at = @At("RETURN"), remap = false)
    private void got$addPlateSlots(Inventory inventory, boolean active, Player owner, CallbackInfo ci) {
        for (PlatePiece piece : PlatePiece.values()) {
            this.addSlot(new PlateSlot(owner, piece));
        }
    }

    /** Shift-click: plate slot -> inventory; a plate item in the inventory -> its own slot. Everything else is untouched. */
    @Inject(method = "quickMoveStack", at = @At("HEAD"), cancellable = true, remap = false)
    private void got$plateQuickMove(Player player, int index, CallbackInfoReturnable<ItemStack> cir) {
        if (index < 0 || index >= this.slots.size()) return;
        Slot slot = this.slots.get(index);
        if (!slot.hasItem()) return;
        ItemStack stack = slot.getItem();
        boolean fromPlateSlot = slot instanceof PlateSlot;
        if (!fromPlateSlot && !(stack.getItem() instanceof PlateItem)) return;

        ItemStack original = stack.copy();
        boolean moved = false;
        if (fromPlateSlot) {
            moved = this.moveItemStackTo(stack, 9, 45, false);
        } else {
            PlatePiece wanted = ((PlateItem) stack.getItem()).getPiece();
            for (int i = 0; i < this.slots.size(); i++) {
                if (this.slots.get(i) instanceof PlateSlot target && target.getPiece() == wanted && !target.hasItem()) {
                    moved = this.moveItemStackTo(stack, i, i + 1, false);
                    break;
                }
            }
        }
        if (!moved) { cir.setReturnValue(ItemStack.EMPTY); return; }
        if (stack.isEmpty()) slot.setByPlayer(ItemStack.EMPTY); else slot.setChanged();
        slot.onTake(player, stack);
        cir.setReturnValue(original);
    }
}
