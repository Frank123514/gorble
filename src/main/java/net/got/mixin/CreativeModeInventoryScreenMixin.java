package net.got.mixin;

import net.got.brigandine.BrigandineSlot;
import net.got.coif.CoifSlot;
import net.got.gambeson.GambesonSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * The creative "survival inventory" tab re-lays-out every slot of the player's InventoryMenu and puts
 * any slot it doesn't know on top of a hotbar slot. Move our gambeson slot to its own spot.
 */
@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeModeInventoryScreenMixin {

    @Inject(method = "selectTab", at = @At("TAIL"), remap = false)
    private void got$placeGambesonSlot(CreativeModeTab tab, CallbackInfo ci) {
        AbstractContainerScreen<?> self = (AbstractContainerScreen<?>) (Object) this;
        // Find the vanilla boots slot (Inventory container slot 36) so the brigandine slot can sit to its right (and the helmet slot for the coif slot).
        Slot boots = null;
        Slot helmet = null;
        for (Slot slot : self.getMenu().slots) {
            if (slot instanceof CreativeSlotWrapperAccessor w
                    && w.got$getTarget().container instanceof Inventory
                    && w.got$getTarget().getContainerSlot() == 36) {
                boots = slot;
            }
            if (slot instanceof CreativeSlotWrapperAccessor w
                    && w.got$getTarget().container instanceof Inventory
                    && w.got$getTarget().getContainerSlot() == 39) {
                helmet = slot;
            }
        }
        for (Slot slot : self.getMenu().slots) {
            if (boots != null && slot instanceof CreativeSlotWrapperAccessor wrapper
                    && wrapper.got$getTarget() instanceof BrigandineSlot) {
                ((SlotAccessor) slot).got$setX(boots.x + 18);
                ((SlotAccessor) slot).got$setY(boots.y);
            }
            if (helmet != null && slot instanceof CreativeSlotWrapperAccessor wrapper
                    && wrapper.got$getTarget() instanceof CoifSlot) {
                ((SlotAccessor) slot).got$setX(helmet.x + 18);
                ((SlotAccessor) slot).got$setY(helmet.y);
            }
            if (slot instanceof CreativeSlotWrapperAccessor wrapper
                    && wrapper.got$getTarget() instanceof GambesonSlot) {
                ((SlotAccessor) slot).got$setX(35);
                ((SlotAccessor) slot).got$setY(42);
            }
        }
    }
}
