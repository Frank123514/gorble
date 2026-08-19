package net.francis.got.registry;

import com.mojang.serialization.MapCodec;
import net.francis.got.GotMod;
import net.francis.got.worldgen.GotChunkGenerator;
import net.francis.got.worldgen.GotBiomeSource;
import net.francis.got.worldgen.biome.placers.NoisyBlockPatchFeature;
import net.francis.got.worldgen.biome.placers.BoulderFeature;
import net.francis.got.worldgen.biome.placers.TripleReedsPatchFeature;
import net.francis.got.worldgen.biome.placers.NearFluidFilter;
import net.francis.got.worldgen.biome.placers.MountainBaseFilter;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class WorldgenRegistries {

    public static final DeferredRegister<MapCodec<? extends ChunkGenerator>> CHUNK_GENERATORS =
            DeferredRegister.create(Registries.CHUNK_GENERATOR, GotMod.MODID);

    public static final DeferredRegister<MapCodec<? extends BiomeSource>> BIOME_SOURCES =
            DeferredRegister.create(Registries.BIOME_SOURCE, GotMod.MODID);

    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(Registries.FEATURE, GotMod.MODID);

    public static final net.neoforged.neoforge.registries.DeferredHolder<Feature<?>, TripleReedsPatchFeature> TRIPLE_REEDS_PATCH = FEATURES.register(
            "triple_reeds_patch",
            () -> new TripleReedsPatchFeature(NoneFeatureConfiguration.CODEC));

    public static final net.neoforged.neoforge.registries.DeferredHolder<Feature<?>, NoisyBlockPatchFeature> NOISY_BLOCK_PATCH = FEATURES.register(
            "noisy_block_patch",
            () -> new NoisyBlockPatchFeature(NoisyBlockPatchFeature.CONFIG_CODEC));

    public static final net.neoforged.neoforge.registries.DeferredHolder<Feature<?>, BoulderFeature> BOULDER = FEATURES.register(
            "boulder",
            () -> new BoulderFeature(BoulderFeature.CONFIG_CODEC));

    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIER_TYPES =
            DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, GotMod.MODID);

    public static final net.neoforged.neoforge.registries.DeferredHolder<PlacementModifierType<?>, PlacementModifierType<NearFluidFilter>> NEAR_FLUID =
            PLACEMENT_MODIFIER_TYPES.register("near_fluid", () -> () -> NearFluidFilter.CODEC);

    public static final net.neoforged.neoforge.registries.DeferredHolder<PlacementModifierType<?>, PlacementModifierType<MountainBaseFilter>> MOUNTAIN_BASE_FILTER =
            PLACEMENT_MODIFIER_TYPES.register("mountain_base_filter", () -> () -> MountainBaseFilter.CODEC);

    static {
        CHUNK_GENERATORS.register("chunk_generator", () -> GotChunkGenerator.CODEC);
        BIOME_SOURCES   .register("biome_source",    () -> GotBiomeSource.CODEC);

        System.out.println("[GoT] Worldgen registries initialised");
    }

    public static void register(IEventBus bus) {
        CHUNK_GENERATORS.register(bus);
        BIOME_SOURCES   .register(bus);
        FEATURES        .register(bus);
        PLACEMENT_MODIFIER_TYPES.register(bus);
        System.out.println("[GoT] Worldgen registries registered to event bus");
    }

    private WorldgenRegistries() {}
}