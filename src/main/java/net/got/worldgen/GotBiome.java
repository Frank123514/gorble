package net.got.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.RandomState;
import org.jetbrains.annotations.NotNull;

public interface GotBiome {
    @NotNull Holder<Biome> getNoiseBiome(int x, int y, int z, @NotNull RandomState random);
}
