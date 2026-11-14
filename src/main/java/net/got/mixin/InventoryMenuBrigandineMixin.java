package net.got.mixin;

import net.got.brigandine.BrigandineItem;
import net.got.brigandine.BrigandineSlot;
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
 * Adds the brigandine slot to the player's inventory menu. High priority so it runs after the
 * gambeson slot mixin, letting us place our slot next to the gambeson slot without overlapping anything.
 */
@Mixin(value = InventoryMenu.class, priority = 1500)
public abstract class InventoryMenuBrigandineMixin extends AbstractContainerMenu {

    protected InventoryMenuBrigandineMixin(MenuType<?> type, int containerId) {
        super(type, containerId);
    }

    @Inject(method = "<init>", at = @At("RETURN"), remap = false)
    private void got$addBrigandineSlot(Inventory inventory, boolean active, Player owner, CallbackInfo ci) {
        int[] pos = got$findFreeSpot();
        this.addSlot(new BrigandineSlot(owner, pos[0], pos[1]));
    }

    /**
     * Puts the slot right next to the vanilla armor slots (armor column is x=8, y=8..62), taking the
     * topmost spot that doesn't overlap any other slot (the gambeson slot may already be there).
     */
    private int[] got$findFreeSpot() {
        int[][] candidates = { {26, 8}, {26, 26}, {26, 44}, {26, 62}, {77, 44}, {77, 26}, {77, 8} };
        for (int[] c : candidates) {
            boolean free = true;
            for (Slot s : this.slots) {
                if (Math.abs(s.x - c[0]) < 18 && Math.abs(s.y - c[1]) < 18) { free = false; break; }
            }
            if (free) return c;
        }
        return candidates[candidates.length - 1];
    }

    /** Shift-click: brigandine -> brigandine slot; brigandine slot -> inventory. Everything else is untouched. */
    @Inject(method = "quickMoveStack", at = @At("HEAD"), cancellable = true, remap = false)
    private void got$brigandineQuickMove(Player player, int index, CallbackInfoReturnable<ItemStack> cir) {
        if (index < 0 || index >= this.slots.size()) return;
        Slot slot = this.slots.get(index);
        if (!slot.hasItem()) return;
        ItemStack stack = slot.getItem();
        boolean fromBrigandineSlot = slot instanceof BrigandineSlot;
        if (!fromBrigandineSlot && !(stack.getItem() instanceof BrigandineItem)) return;

        ItemStack original = stack.copy();
        boolean moved = false;
        if (fromBrigandineSlot) {
            moved = this.moveItemStackTo(stack, 9, 45, false);
        } else {
            for (int i = 0; i < this.slots.size(); i++) {
                if (this.slots.get(i) instanceof BrigandineSlot target && !target.hasItem()) {
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
