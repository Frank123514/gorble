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

    /**
     * Broadleaf trunk: vanilla's fancy trunk shape (random branch scatter,
     * pruned canopy) with a flared, root-thickened base. Used for our large
     * hardwood species (ash, beech, chestnut, elm, hawthorn, willow, ebony,
     * goldenheart, black cottonwood, cottonwood, ironwood, weirwood).
     */
    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<GotBroadleafTrunkPlacer>>
            GOT_BROADLEAF_TRUNK_PLACER =
            TRUNK_PLACER_TYPES.register("got_broadleaf_trunk_placer",
                    () -> new TrunkPlacerType<>(GotBroadleafTrunkPlacer.CODEC));

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

    /**
     * Broadleaf foliage placer: wide irregular layered dome for large hardwood
     * trees (ash, beech, elm, chestnut, willow, cottonwood, ironwood, weirwood,
     * ebony, nightwood, purpleheart, tigerwood, burl, pink ivory, etc.).
     */
    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<GotBroadleafFoliagePlacer>>
            GOT_BROADLEAF_FOLIAGE_PLACER =
            FOLIAGE_PLACER_TYPES.register("got_broadleaf_foliage_placer",
                    () -> new FoliagePlacerType<>(GotBroadleafFoliagePlacer.CODEC));

    /**
     * Orchard foliage placer: compact Euclidean sphere crown with a drooping
     * lower skirt, for small fruit and nut trees (apple, pear, cherry, plum,
     * peach, fig, olive, pomegranate, almond, citrus, crabapple, etc.).
     */
    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<GotOrchardFoliagePlacer>>
            GOT_ORCHARD_FOLIAGE_PLACER =
            FOLIAGE_PLACER_TYPES.register("got_orchard_foliage_placer",
                    () -> new FoliagePlacerType<>(GotOrchardFoliagePlacer.CODEC));

    /**
     * Conifer foliage placer: distinct tiered whorls with air gaps between
     * each tier, narrowing to a pointed tip.  Used for cedar, fir, sentinal,
     * soldier pine, blackbark, hemlock, blue mahoe, clove, aspen.
     */
    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<GotConiferFoliagePlacer>>
            GOT_CONIFER_FOLIAGE_PLACER =
            FOLIAGE_PLACER_TYPES.register("got_conifer_foliage_placer",
                    () -> new FoliagePlacerType<>(GotConiferFoliagePlacer.CODEC));

    // ── Registration ──────────────────────────────────────────────────────────

    public static void register(IEventBus bus) {
        TRUNK_PLACER_TYPES.register(bus);
        FOLIAGE_PLACER_TYPES.register(bus);
    }

    private GotTreePlacers() {}
}