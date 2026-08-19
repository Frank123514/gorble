package net.got.mixin.client;

import net.got.worldgen.ModDimensions;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@OnlyIn(Dist.CLIENT)
@Mixin(CreateWorldScreen.class)
public class CreateWorldScreenMixin {

    private static final ResourceKey<WorldPreset> KNOWNWORLD_PRESET = ModDimensions.KNOWNWORLD_WORLD_PRESET;

    // openFresh() calls the private openCreateWorldScreen(..., WorldPresets.NORMAL, ...);
    // intercept the "preset" parameter itself rather than the static field read, since it
    // avoids needing the exact descriptor of the package-private CreateWorldCallback type.
    @ModifyVariable(method = "openCreateWorldScreen", at = @At("HEAD"), argsOnly = true)
    private static ResourceKey<WorldPreset> got$redirectDefaultPreset(ResourceKey<WorldPreset> preset) {
        return preset == WorldPresets.NORMAL ? KNOWNWORLD_PRESET : preset;
    }
}
