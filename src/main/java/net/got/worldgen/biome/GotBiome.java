package net.got.worldgen.biome;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;

public interface GotBiome {
    Holder<Biome> getNoiseBiome(
            int x,
            int y,
            int z,
            net.minecraft.world.level.levelgen.RandomState random
    );
}
