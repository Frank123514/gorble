package net.got.mixin;

import net.got.armorpanel.ArmorPanelLayout;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * The armor panel sits outside the normal GUI bounds, so a click on the panel's padding (between slots)
 * would count as "clicked outside" and drop the held item. Treat the panel as part of the GUI instead.
 */
@Mixin({InventoryScreen.class, CreativeModeInventoryScreen.class})
public abstract class ArmorPanelClickMixin {

    @Inject(method = "hasClickedOutside", at = @At("HEAD"), cancellable = true, remap = false, require = 0)
    private void got$clickInsideArmorPanel(double mouseX, double mouseY, int guiLeft, int guiTop,
                                           CallbackInfoReturnable<Boolean> cir) {
        if (!ArmorPanelLayout.shown) return;
        if (mouseX >= guiLeft + ArmorPanelLayout.PANEL_LEFT && mouseX < guiLeft + ArmorPanelLayout.PANEL_RIGHT
                && mouseY >= guiTop + ArmorPanelLayout.PANEL_TOP && mouseY < guiTop + ArmorPanelLayout.PANEL_BOTTOM) {
            cir.setReturnValue(false);
        }
    }
}
