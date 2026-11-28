package net.got.mixin;

import net.got.armorpanel.ArmorPanelLayout;
import net.got.brigandine.BrigandineSlot;
import net.got.cloak.CloakSlot;
import net.got.coif.CoifSlot;
import net.got.gambeson.GambesonSlot;
import net.got.hauberk.HauberkSlot;
import net.got.plate.PlateSlot;
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
 * any slot it doesn't know on top of a hotbar slot. Move all our custom armor slots into the armor panel
 * (see {@link ArmorPanelLayout}) on the left of the GUI.
 */
@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeModeInventoryScreenMixin {

    @Inject(method = "selectTab", at = @At("TAIL"), remap = false)
    private void got$placeArmorPanelSlots(CreativeModeTab tab, CallbackInfo ci) {
        AbstractContainerScreen<?> self = (AbstractContainerScreen<?>) (Object) this;
        for (Slot slot : self.getMenu().slots) {
            if (!(slot instanceof CreativeSlotWrapperAccessor wrapper)) continue;
            Slot target = wrapper.got$getTarget();
            int x;
            int y;
            if (target instanceof CoifSlot) {
                x = ArmorPanelLayout.COIF_X;       y = ArmorPanelLayout.COIF_Y;
            } else if (target instanceof GambesonSlot) {
                x = ArmorPanelLayout.GAMBESON_X;   y = ArmorPanelLayout.GAMBESON_Y;
            } else if (target instanceof HauberkSlot) {
                x = ArmorPanelLayout.HAUBERK_X;    y = ArmorPanelLayout.HAUBERK_Y;
            } else if (target instanceof BrigandineSlot) {
                x = ArmorPanelLayout.BRIGANDINE_X; y = ArmorPanelLayout.BRIGANDINE_Y;
            } else if (target instanceof CloakSlot) {
                x = ArmorPanelLayout.CLOAK_X;      y = ArmorPanelLayout.CLOAK_Y;
            } else if (target instanceof PlateSlot plate) {
                x = ArmorPanelLayout.plateX(plate.getPiece());
                y = ArmorPanelLayout.plateY(plate.getPiece());
            } else {
                continue;
            }
            ((SlotAccessor) slot).got$setX(x);
            ((SlotAccessor) slot).got$setY(y);
        }
    }
}
