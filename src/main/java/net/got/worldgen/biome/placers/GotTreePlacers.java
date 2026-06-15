package net.got.worldgen.biome.placers;

import net.got.GotMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class GotTreePlacers {

    // ── Trunk placer registry ─────────────────────────────────────────────────

    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER_TYPES =
            DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, GotMod.MODID);

    /** Pine trunk: straight shaft with horizontal dead-branch stubs. */
    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<GotPineTrunkPlacer>>
            GOT_PINE_TRUNK_PLACER =
            TRUNK_PLACER_TYPES.register("got_pine_trunk_placer",
                    () -> new TrunkPlacerType<>(GotPineTrunkPlacer.CODEC));

    /**
     * Palm trunk: gently-leaning segmented shaft with a bulging base ring,
     * used for all palm species (date palm, coconut palm, etc.).
     */
    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<GotPalmTrunkPlacer>>
            GOT_PALM_TRUNK_PLACER =
            TRUNK_PLACER_TYPES.register("got_palm_trunk_placer",
                    () -> new TrunkPlacerType<>(GotPalmTrunkPlacer.CODEC));

    // ── Foliage placer registry ───────────────────────────────────────────────

    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPES =
            DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, GotMod.MODID);

    /**
     * Palm foliage placer: radiating arched frond arms from the crown,
     * used for all palm species.
     */
    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<GotPalmFoliagePlacer>>
            GOT_PALM_FOLIAGE_PLACER =
            FOLIAGE_PLACER_TYPES.register("got_palm_foliage_placer",
                    () -> new FoliagePlacerType<>(GotPalmFoliagePlacer.CODEC));

    // ── Registration ──────────────────────────────────────────────────────────

    public static void register(IEventBus bus) {
        TRUNK_PLACER_TYPES.register(bus);
        FOLIAGE_PLACER_TYPES.register(bus);
    }

    private GotTreePlacers() {}
}