package net.got.mixin;

import net.got.worldgen.ModDimensions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldPresets.class)
public class WorldPresetsMixin {

    private static final ResourceKey<WorldPreset> KNOWNWORLD_PRESET = ModDimensions.KNOWNWORLD_WORLD_PRESET;

    @Redirect(
            method = {"createNormalWorldDimensions", "getNormalOverworld"},
            require = 2,
            at = @At(
                    value = "FIELD",
                    opcode = Opcodes.GETSTATIC,
                    target = "Lnet/minecraft/world/level/levelgen/presets/WorldPresets;NORMAL:Lnet/minecraft/resources/ResourceKey;"
            )
    )
    private static ResourceKey<WorldPreset> replaceDefault() {
        return KNOWNWORLD_PRESET;
    }
}
