package net.got.mixin;

import net.got.coif.CoifItem;
import net.got.coif.CoifSlot;
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
 * Adds the mail coif slot to the player's inventory menu, directly to the right of the vanilla helmet slot.
 */
@Mixin(value = InventoryMenu.class, priority = 1600)
public abstract class InventoryMenuCoifMixin extends AbstractContainerMenu {

    protected InventoryMenuCoifMixin(MenuType<?> type, int containerId) {
        super(type, containerId);
    }

    @Inject(method = "<init>", at = @At("RETURN"), remap = false)
    private void got$addCoifSlot(Inventory inventory, boolean active, Player owner, CallbackInfo ci) {
        // Vanilla armor column is x=8, y=8..62 (helmet at y=8): put the slot right of the helmet slot.
        this.addSlot(new CoifSlot(owner, net.got.armorpanel.ArmorPanelLayout.COIF_X, net.got.armorpanel.ArmorPanelLayout.COIF_Y));
    }

    /** Shift-click: coif -> coif slot; coif slot -> inventory. Everything else is untouched. */
    @Inject(method = "quickMoveStack", at = @At("HEAD"), cancellable = true, remap = false)
    private void got$coifQuickMove(Player player, int index, CallbackInfoReturnable<ItemStack> cir) {
        if (index < 0 || index >= this.slots.size()) return;
        Slot slot = this.slots.get(index);
        if (!slot.hasItem()) return;
        ItemStack stack = slot.getItem();
        boolean fromCoifSlot = slot instanceof CoifSlot;
        if (!fromCoifSlot && !(stack.getItem() instanceof CoifItem)) return;

        ItemStack original = stack.copy();
        boolean moved = false;
        if (fromCoifSlot) {
            moved = this.moveItemStackTo(stack, 9, 45, false);
        } else {
            for (int i = 0; i < this.slots.size(); i++) {
                if (this.slots.get(i) instanceof CoifSlot target && !target.hasItem()) {
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
