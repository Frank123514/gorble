package net.got.worldgen.biome;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class GotBiomes {

    public static final ResourceKey<Biome> IRONWOOD =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath("got", "ironwood"));

    public static final ResourceKey<Biome> WOLFSWOOD =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath("got", "wolfswood"));

    public static final ResourceKey<Biome> NORTH =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath("got", "north"));
}
