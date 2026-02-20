package net.got.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.world.level.biome.Biome;

import java.util.Set;

public interface GotBiome {
    void collectPossibleBiomes(
            HolderGetter<Biome> lookup,
            Set<Holder<Biome>> set
    );
}
