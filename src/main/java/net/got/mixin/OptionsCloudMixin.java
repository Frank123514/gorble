package net.got.mixin;

import net.got.client.renderer.GotCloudRenderer;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Vanilla's LevelRenderer only builds/draws its (voxelized) cloud geometry when
 * {@code Options.getCloudsType() != CloudStatus.OFF}. Rather than fighting with the
 * internals of the vanilla cloud renderer directly, we just make that check always
 * see "off" while {@link GotCloudRenderer#ENABLED} is true — GotCloudRenderer then
 * draws its own flat, texture-sampled cloud plane in the same spot vanilla clouds
 * would have appeared.
 *
 * Note this means the "Clouds" entry in vanilla's video settings has no effect
 * while our renderer is enabled; toggle GotCloudRenderer.ENABLED instead if you
 * want an in-game way to turn clouds fully off.
 *
 * remap=false — see WeatherEffectRendererMixin for explanation.
 */
@Mixin(Options.class)
public class OptionsCloudMixin {

    @Inject(method = "getCloudsType", at = @At("HEAD"), cancellable = true, remap = false)
    private void gotCloud_forceVanillaOff(CallbackInfoReturnable<CloudStatus> cir) {
        if (GotCloudRenderer.ENABLED) {
            cir.setReturnValue(CloudStatus.OFF);
        }
    }
}
