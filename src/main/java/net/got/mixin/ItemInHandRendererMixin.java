package net.got.mixin;

import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Cancels vanilla's classic first-person hand/item view-model entirely
 * while the camera is first person, so the only thing drawing our hands is
 * the real {@code PlayerRenderer} body {@link LevelRendererMixin} makes
 * visible — {@code PlayerModel}'s own {@code ItemInHandLayer} already
 * renders the held item in that body's actual hand, so nothing is lost by
 * cutting this vanilla path, only the duplicate/mismatched floating
 * view-model on top of it.
 *
 * <p>Targets the single top-level entry point ({@code
 * renderHandsWithItems}) rather than each per-arm {@code renderArmWithItem}
 * call, since that one method is exactly what {@code GameRenderer} calls
 * once per frame to draw both hands (item or bare fist) — cancelling it
 * here skips main hand, off hand, and the empty-fist case in one place
 * instead of three.
 *
 * <p><b>Verification note:</b> like the rest of this project's client
 * mixins, this targets named/Parchment mappings directly ({@code
 * remap = false}); the fields/methods here were written from memory of
 * {@code ItemInHandRenderer} and not compiled in this environment — if
 * Mixin fails to locate {@code renderHandsWithItems}, confirm the method
 * name/signature against a decompile of {@code ItemInHandRenderer} (1.21.4
 * Mojang mappings: {@code void renderHandsWithItems(float, PoseStack,
 * MultiBufferSource, LocalPlayer, int)}).
 */
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
