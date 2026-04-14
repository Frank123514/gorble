package net.got.worldgen.biome.placers;

import net.got.GotMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class GotTreePlacers {

    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER_TYPES =
            DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, GotMod.MODID);

    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<GotPineTrunkPlacer>> GOT_PINE_TRUNK_PLACER =
            TRUNK_PLACER_TYPES.register("got_pine_trunk_placer", () -> new TrunkPlacerType<>(GotPineTrunkPlacer.CODEC));

    public static void register(IEventBus bus) {
        TRUNK_PLACER_TYPES.register(bus);
    }

    private GotTreePlacers() {}
}
