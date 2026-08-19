package net.francis.got.mixin;

import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ItemInHandRenderer.class, remap = false)
public abstract class ItemInHandRendererMixin {

    @Inject(method = "renderHandsWithItems", at = @At("HEAD"), cancellable = true)
    private void got_hideVanillaFirstPersonHands(CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.options.getCameraType() == CameraType.FIRST_PERSON) {
            ci.cancel();
        }
    }
}
