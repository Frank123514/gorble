package net.got.worldgen.biome.placers;

import net.got.GotMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class TreePlacers {

    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER_TYPES =
            DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, GotMod.MODID);

    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<PineTrunkPlacer>>
            GOT_PINE_TRUNK_PLACER =
            TRUNK_PLACER_TYPES.register("got_pine_trunk_placer",
                    () -> new TrunkPlacerType<>(PineTrunkPlacer.CODEC));

    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<PalmTrunkPlacer>>
            GOT_PALM_TRUNK_PLACER =
            TRUNK_PLACER_TYPES.register("got_palm_trunk_placer",
                    () -> new TrunkPlacerType<>(PalmTrunkPlacer.CODEC));

    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<BroadleafTrunkPlacer>>
            GOT_BROADLEAF_TRUNK_PLACER =
            TRUNK_PLACER_TYPES.register("got_broadleaf_trunk_placer",
                    () -> new TrunkPlacerType<>(BroadleafTrunkPlacer.CODEC));

    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<RedwoodTrunkPlacer>>
            GOT_REDWOOD_TRUNK_PLACER =
            TRUNK_PLACER_TYPES.register("got_redwood_trunk_placer",
                    () -> new TrunkPlacerType<>(RedwoodTrunkPlacer.CODEC));

    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPES =
            DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, GotMod.MODID);

    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<PalmFoliagePlacer>>
            GOT_PALM_FOLIAGE_PLACER =
            FOLIAGE_PLACER_TYPES.register("got_palm_foliage_placer",
                    () -> new FoliagePlacerType<>(PalmFoliagePlacer.CODEC));

    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<BroadleafFoliagePlacer>>
            GOT_BROADLEAF_FOLIAGE_PLACER =
            FOLIAGE_PLACER_TYPES.register("got_broadleaf_foliage_placer",
                    () -> new FoliagePlacerType<>(BroadleafFoliagePlacer.CODEC));

    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<OrchardFoliagePlacer>>
            GOT_ORCHARD_FOLIAGE_PLACER =
            FOLIAGE_PLACER_TYPES.register("got_orchard_foliage_placer",
                    () -> new FoliagePlacerType<>(OrchardFoliagePlacer.CODEC));

    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<AspenFoliagePlacer>>
            GOT_ASPEN_FOLIAGE_PLACER =
            FOLIAGE_PLACER_TYPES.register("got_aspen_foliage_placer",
                    () -> new FoliagePlacerType<>(AspenFoliagePlacer.CODEC));

    public static void register(IEventBus bus) {
        TRUNK_PLACER_TYPES.register(bus);
        FOLIAGE_PLACER_TYPES.register(bus);
    }

    private TreePlacers() {}
}