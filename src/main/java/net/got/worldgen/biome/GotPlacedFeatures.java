package net.got.worldgen.biome;

import net.got.GotMod;
import net.got.worldgen.biome.GotConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

/**
 * Registers every GOT placed feature with its full placement-modifier stack,
 * following the same pattern as {@code ModplacedFeatures} in the example mod.
 *
 * <p>All parameters are derived directly from the generated JSON files under
 * {@code data/got/worldgen/placed_feature/}. The constants here are kept in
 * sync with {@link GotConfiguredFeatures}; each placed feature references
 * exactly one configured feature key.
 *
 * <p>Two density tiers exist for most trees:
 * <ul>
 *   <li>Dense  – e.g. {@link #ASH} – rarity 1/1 (every chunk), count 2</li>
 *   <li>Sparse – e.g. {@link #ASH_SPARSE} – rarity 1/35, count 1</li>
 * </ul>
 */
public final class GotPlacedFeatures {

    // ─────────────────────────────────────────────────────────────────────────
    // Resource keys
    // ─────────────────────────────────────────────────────────────────────────

    // Trees – temperate broadleaf
    public static final ResourceKey<PlacedFeature> ALDER              = key("alder");

    public static final ResourceKey<PlacedFeature> APPLE              = key("apple");
    public static final ResourceKey<PlacedFeature> APPLE_SPARSE       = key("apple_sparse");

    public static final ResourceKey<PlacedFeature> ASH                = key("ash");
    public static final ResourceKey<PlacedFeature> ASH_SPARSE         = key("ash_sparse");

    public static final ResourceKey<PlacedFeature> ASPEN              = key("aspen");
    public static final ResourceKey<PlacedFeature> ASPEN_SPARSE       = key("aspen_sparse");

    public static final ResourceKey<PlacedFeature> BEECH              = key("beech");
    public static final ResourceKey<PlacedFeature> BEECH_SPARSE       = key("beech_sparse");

    public static final ResourceKey<PlacedFeature> CHESTNUT           = key("chestnut");
    public static final ResourceKey<PlacedFeature> CHESTNUT_SPARSE    = key("chestnut_sparse");

    public static final ResourceKey<PlacedFeature> ELM                = key("elm");
    public static final ResourceKey<PlacedFeature> ELM_SPARSE         = key("elm_sparse");

    public static final ResourceKey<PlacedFeature> HAWTHORN           = key("hawthorn");
    public static final ResourceKey<PlacedFeature> HAWTHORN_SPARSE    = key("hawthorn_sparse");

    public static final ResourceKey<PlacedFeature> LINDEN             = key("linden");
    public static final ResourceKey<PlacedFeature> LINDEN_SPARSE      = key("linden_sparse");

    public static final ResourceKey<PlacedFeature> MAPLE              = key("maple");
    public static final ResourceKey<PlacedFeature> MAPLE_SPARSE       = key("maple_sparse");

    public static final ResourceKey<PlacedFeature> WILLOW             = key("willow");
    public static final ResourceKey<PlacedFeature> WILLOW_SPARSE      = key("willow_sparse");

    // Trees – conifers
    public static final ResourceKey<PlacedFeature> CEDAR              = key("cedar");
    public static final ResourceKey<PlacedFeature> CEDAR_SPARSE       = key("cedar_sparse");

    public static final ResourceKey<PlacedFeature> FIR                = key("fir");
    public static final ResourceKey<PlacedFeature> FIR_SPARSE         = key("fir_sparse");

    public static final ResourceKey<PlacedFeature> PINE               = key("pine");
    public static final ResourceKey<PlacedFeature> PINE_SPARSE        = key("pine_sparse");

    public static final ResourceKey<PlacedFeature> REDWOOD            = key("redwood");
    public static final ResourceKey<PlacedFeature> REDWOOD_SPARSE     = key("redwood_sparse");

    public static final ResourceKey<PlacedFeature> SENTINAL           = key("sentinal");

    public static final ResourceKey<PlacedFeature> SOLDIER_PINE       = key("soldier_pine");

    // Trees – exotic / tropical
    public static final ResourceKey<PlacedFeature> BLUE_MAHOE         = key("blue_mahoe");
    public static final ResourceKey<PlacedFeature> BLUE_MAHOE_SPARSE  = key("blue_mahoe_sparse");

    public static final ResourceKey<PlacedFeature> CINNAMON           = key("cinnamon");
    public static final ResourceKey<PlacedFeature> CINNAMON_SPARSE    = key("cinnamon_sparse");

    public static final ResourceKey<PlacedFeature> CLOVE              = key("clove");
    public static final ResourceKey<PlacedFeature> CLOVE_SPARSE       = key("clove_sparse");

    public static final ResourceKey<PlacedFeature> EBONY              = key("ebony");
    public static final ResourceKey<PlacedFeature> EBONY_SPARSE       = key("ebony_sparse");

    public static final ResourceKey<PlacedFeature> GOLDENHEART        = key("goldenheart");
    public static final ResourceKey<PlacedFeature> GOLDENHEART_SPARSE = key("goldenheart_sparse");

    public static final ResourceKey<PlacedFeature> MAHOGANY           = key("mahogany");
    public static final ResourceKey<PlacedFeature> MAHOGANY_SPARSE    = key("mahogany_sparse");

    public static final ResourceKey<PlacedFeature> MYRRH              = key("myrrh");
    public static final ResourceKey<PlacedFeature> MYRRH_SPARSE       = key("myrrh_sparse");

    // Trees – wetland / riverside
    public static final ResourceKey<PlacedFeature> BLACK_COTTONWOOD        = key("black_cottonwood");
    public static final ResourceKey<PlacedFeature> BLACK_COTTONWOOD_SPARSE = key("black_cottonwood_sparse");

    public static final ResourceKey<PlacedFeature> COTTONWOOD         = key("cottonwood");
    public static final ResourceKey<PlacedFeature> COTTONWOOD_SPARSE  = key("cottonwood_sparse");

    // Trees – dark / special
    public static final ResourceKey<PlacedFeature> BLACKBARK          = key("blackbark");
    public static final ResourceKey<PlacedFeature> BLACKBARK_SPARSE   = key("blackbark_sparse");

    public static final ResourceKey<PlacedFeature> BLOODWOOD          = key("bloodwood");
    public static final ResourceKey<PlacedFeature> BLOODWOOD_SPARSE   = key("bloodwood_sparse");

    public static final ResourceKey<PlacedFeature> IRONWOOD           = key("ironwood");
    public static final ResourceKey<PlacedFeature> IRONWOOD_SPARSE    = key("ironwood_sparse.json");

    public static final ResourceKey<PlacedFeature> SHRUB              = key("shrub");

    public static final ResourceKey<PlacedFeature> WEIRWOOD           = key("weirwood");

    public static final ResourceKey<PlacedFeature> WORMTREE           = key("wormtree");
    public static final ResourceKey<PlacedFeature> WORMTREE_SPARSE    = key("wormtree_sparse");

    // Boulders
    public static final ResourceKey<PlacedFeature> BOULDER              = key("boulder");
    public static final ResourceKey<PlacedFeature> GREY_GRANITE_BOULDER = key("grey_granite_boulder");
    public static final ResourceKey<PlacedFeature> LIMESTONE_BOULDER    = key("limestone_boulder");
    public static final ResourceKey<PlacedFeature> SLATE_BOULDER        = key("slate_boulder");

    // Disks
    public static final ResourceKey<PlacedFeature> DISK_CLAY  = key("disk_clay");
    public static final ResourceKey<PlacedFeature> DISK_SAND  = key("disk_sand");

    // Rock-pocket ores
    public static final ResourceKey<PlacedFeature> ORE_BASALT_ROCK        = key("ore_basalt_rock");
    public static final ResourceKey<PlacedFeature> ORE_FLINT_ROCK         = key("ore_flint_rock");
    public static final ResourceKey<PlacedFeature> ORE_GREY_GRANITE_ROCK  = key("ore_grey_granite_rock");
    public static final ResourceKey<PlacedFeature> ORE_LIMESTONE_ROCK     = key("ore_limestone_rock");
    public static final ResourceKey<PlacedFeature> ORE_MARBLE_ROCK        = key("ore_marble_rock");
    public static final ResourceKey<PlacedFeature> ORE_RED_SANDSTONE_ROCK = key("ore_red_sandstone_rock");
    public static final ResourceKey<PlacedFeature> ORE_SANDSTONE_ROCK     = key("ore_sandstone_rock");
    public static final ResourceKey<PlacedFeature> ORE_SLATE_ROCK         = key("ore_slate_rock");

    // Berry bushes
    public static final ResourceKey<PlacedFeature> BLACKBERRY_BUSH   = key("blackberry_bush");
    public static final ResourceKey<PlacedFeature> BLUEBERRY_BUSH    = key("blueberry_bush");
    public static final ResourceKey<PlacedFeature> RASPBERRY_BUSH    = key("raspberry_bush");
    public static final ResourceKey<PlacedFeature> STRAWBERRY_BUSH   = key("strawberry_bush");

    // Wild crops
    public static final ResourceKey<PlacedFeature> WILD_BARLEY       = key("wild_barley");
    public static final ResourceKey<PlacedFeature> WILD_BEETROOT     = key("wild_beetroot");
    public static final ResourceKey<PlacedFeature> WILD_CABBAGE      = key("wild_cabbage");
    public static final ResourceKey<PlacedFeature> WILD_CARROT       = key("wild_carrot");
    public static final ResourceKey<PlacedFeature> WILD_COTTON       = key("wild_cotton");
    public static final ResourceKey<PlacedFeature> WILD_GARLIC       = key("wild_garlic");
    public static final ResourceKey<PlacedFeature> WILD_HORSERADISH  = key("wild_horseradish");
    public static final ResourceKey<PlacedFeature> WILD_LEEK         = key("wild_leek");
    public static final ResourceKey<PlacedFeature> WILD_NEEP         = key("wild_neep");
    public static final ResourceKey<PlacedFeature> WILD_OAT          = key("wild_oat");
    public static final ResourceKey<PlacedFeature> WILD_ONION        = key("wild_onion");
    public static final ResourceKey<PlacedFeature> WILD_PARSNIP      = key("wild_parsnip");
    public static final ResourceKey<PlacedFeature> WILD_PEAS         = key("wild_peas");
    public static final ResourceKey<PlacedFeature> WILD_PEPPERCORN   = key("wild_peppercorn");
    public static final ResourceKey<PlacedFeature> WILD_RYE          = key("wild_rye");
    public static final ResourceKey<PlacedFeature> WILD_TURNIP       = key("wild_turnip");
    public static final ResourceKey<PlacedFeature> WILD_WHEAT        = key("wild_wheat");

    // ─────────────────────────────────────────────────────────────────────────
    // Bootstrap
    // ─────────────────────────────────────────────────────────────────────────

    public static void bootstrap(BootstrapContext<PlacedFeature> ctx) {
        HolderGetter<ConfiguredFeature<?, ?>> cf = ctx.lookup(Registries.CONFIGURED_FEATURE);

        // ══════════════════════════════════════════════════════════════════════
        // TREES
        // Each tree placed feature uses these modifiers (matching the JSONs):
        //   rarity_filter(chance)  – skips the feature N-1 out of N chunks
        //   count / count_uniform  – how many attempts per chunk
        //   in_square              – spreads attempts across the 16×16 chunk area
        //   heightmap(WORLD_SURFACE_WG) – finds the surface
        //   block_predicate_filter(solid below) – only place on solid ground
        //   biome                  – respects the current biome
        //
        // "Sparse" variants have higher rarity and lower count.
        // ══════════════════════════════════════════════════════════════════════

        // ── Temperate broadleaf ───────────────────────────────────────────────

        // alder: rarity=2, count=2-3 (no sparse variant in JSON)
        register(ctx, ALDER, cf.getOrThrow(GotConfiguredFeatures.ALDER),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(2, 3))));

        // apple: rarity=2 count=1-3 / sparse: rarity=4 count=1
        register(ctx, APPLE, cf.getOrThrow(GotConfiguredFeatures.APPLE),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, APPLE_SPARSE, cf.getOrThrow(GotConfiguredFeatures.APPLE),
                treeMods(4, CountPlacement.of(1)));

        // ash: rarity=1 count=2 / sparse: rarity=35 count=1
        register(ctx, ASH, cf.getOrThrow(GotConfiguredFeatures.ASH),
                treeMods(1, CountPlacement.of(2)));
        register(ctx, ASH_SPARSE, cf.getOrThrow(GotConfiguredFeatures.ASH),
                treeMods(35, CountPlacement.of(1)));

        // aspen: rarity=2 count=1-3 / sparse: rarity=35 count=1
        register(ctx, ASPEN, cf.getOrThrow(GotConfiguredFeatures.ASPEN),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, ASPEN_SPARSE, cf.getOrThrow(GotConfiguredFeatures.ASPEN),
                treeMods(35, CountPlacement.of(1)));

        // beech: rarity=1 count=2 / sparse: rarity=35 count=1
        register(ctx, BEECH, cf.getOrThrow(GotConfiguredFeatures.BEECH),
                treeMods(1, CountPlacement.of(2)));
        register(ctx, BEECH_SPARSE, cf.getOrThrow(GotConfiguredFeatures.BEECH),
                treeMods(35, CountPlacement.of(1)));

        // chestnut: rarity=2 count=1-3 / sparse: rarity=4 count=1-3
        register(ctx, CHESTNUT, cf.getOrThrow(GotConfiguredFeatures.CHESTNUT),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, CHESTNUT_SPARSE, cf.getOrThrow(GotConfiguredFeatures.CHESTNUT),
                treeMods(4, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));

        // elm: rarity=2 count=1-3 / sparse: rarity=4 count=1
        register(ctx, ELM, cf.getOrThrow(GotConfiguredFeatures.ELM),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, ELM_SPARSE, cf.getOrThrow(GotConfiguredFeatures.ELM),
                treeMods(4, CountPlacement.of(1)));

        // hawthorn: rarity=1 count=2 / sparse: rarity=35 count=1
        register(ctx, HAWTHORN, cf.getOrThrow(GotConfiguredFeatures.HAWTHORN),
                treeMods(1, CountPlacement.of(2)));
        register(ctx, HAWTHORN_SPARSE, cf.getOrThrow(GotConfiguredFeatures.HAWTHORN),
                treeMods(35, CountPlacement.of(1)));

        // linden: rarity=2 count=1-3 / sparse: rarity=4 count=1
        register(ctx, LINDEN, cf.getOrThrow(GotConfiguredFeatures.LINDEN),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, LINDEN_SPARSE, cf.getOrThrow(GotConfiguredFeatures.LINDEN),
                treeMods(4, CountPlacement.of(1)));

        // maple: rarity=2 count=1-3 / sparse: rarity=4 count=1
        register(ctx, MAPLE, cf.getOrThrow(GotConfiguredFeatures.MAPLE),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, MAPLE_SPARSE, cf.getOrThrow(GotConfiguredFeatures.MAPLE),
                treeMods(4, CountPlacement.of(1)));

        // willow: rarity=2 count=1-3 / sparse: rarity=4 count=1-3
        register(ctx, WILLOW, cf.getOrThrow(GotConfiguredFeatures.WILLOW),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, WILLOW_SPARSE, cf.getOrThrow(GotConfiguredFeatures.WILLOW),
                treeMods(4, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));

        // ── Conifers ──────────────────────────────────────────────────────────

        // cedar: rarity=2 count=1-3 / sparse: rarity=4 count=1
        register(ctx, CEDAR, cf.getOrThrow(GotConfiguredFeatures.CEDAR),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, CEDAR_SPARSE, cf.getOrThrow(GotConfiguredFeatures.CEDAR),
                treeMods(4, CountPlacement.of(1)));

        // fir: rarity=1 count=2 / sparse: rarity=35 count=1
        register(ctx, FIR, cf.getOrThrow(GotConfiguredFeatures.FIR),
                treeMods(1, CountPlacement.of(2)));
        register(ctx, FIR_SPARSE, cf.getOrThrow(GotConfiguredFeatures.FIR),
                treeMods(35, CountPlacement.of(1)));

        // pine: rarity=1 count=2 / sparse: rarity=35 count=1
        register(ctx, PINE, cf.getOrThrow(GotConfiguredFeatures.PINE),
                treeMods(1, CountPlacement.of(2)));
        register(ctx, PINE_SPARSE, cf.getOrThrow(GotConfiguredFeatures.PINE),
                treeMods(35, CountPlacement.of(1)));

        // redwood: rarity=2 count=1-3 / sparse: rarity=4 count=1-3
        register(ctx, REDWOOD, cf.getOrThrow(GotConfiguredFeatures.REDWOOD),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, REDWOOD_SPARSE, cf.getOrThrow(GotConfiguredFeatures.REDWOOD),
                treeMods(4, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));

        // sentinal: rarity=2 count=2 (no sparse)
        register(ctx, SENTINAL, cf.getOrThrow(GotConfiguredFeatures.SENTINAL),
                treeMods(2, CountPlacement.of(2)));

        // soldier_pine: rarity=1 count=2 (no sparse)
        register(ctx, SOLDIER_PINE, cf.getOrThrow(GotConfiguredFeatures.SOLDIER_PINE),
                treeMods(1, CountPlacement.of(2)));

        // ── Exotic / tropical ─────────────────────────────────────────────────

        // blue_mahoe: rarity=2 count=1-3 / sparse: rarity=4 count=1
        register(ctx, BLUE_MAHOE, cf.getOrThrow(GotConfiguredFeatures.BLUE_MAHOE),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, BLUE_MAHOE_SPARSE, cf.getOrThrow(GotConfiguredFeatures.BLUE_MAHOE),
                treeMods(4, CountPlacement.of(1)));

        // cinnamon: rarity=2 count=1-3 / sparse: rarity=4 count=1
        register(ctx, CINNAMON, cf.getOrThrow(GotConfiguredFeatures.CINNAMON),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, CINNAMON_SPARSE, cf.getOrThrow(GotConfiguredFeatures.CINNAMON),
                treeMods(4, CountPlacement.of(1)));

        // clove: rarity=2 count=1-3 / sparse: rarity=4 count=1
        register(ctx, CLOVE, cf.getOrThrow(GotConfiguredFeatures.CLOVE),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, CLOVE_SPARSE, cf.getOrThrow(GotConfiguredFeatures.CLOVE),
                treeMods(4, CountPlacement.of(1)));

        // ebony: rarity=2 count=1-3 / sparse: rarity=4 count=1
        register(ctx, EBONY, cf.getOrThrow(GotConfiguredFeatures.EBONY),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, EBONY_SPARSE, cf.getOrThrow(GotConfiguredFeatures.EBONY),
                treeMods(4, CountPlacement.of(1)));

        // goldenheart: rarity=2 count=1-3 / sparse: rarity=4 count=1
        register(ctx, GOLDENHEART, cf.getOrThrow(GotConfiguredFeatures.GOLDENHEART),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, GOLDENHEART_SPARSE, cf.getOrThrow(GotConfiguredFeatures.GOLDENHEART),
                treeMods(4, CountPlacement.of(1)));

        // mahogany: rarity=2 count=1-3 / sparse: rarity=4 count=1
        register(ctx, MAHOGANY, cf.getOrThrow(GotConfiguredFeatures.MAHOGANY),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, MAHOGANY_SPARSE, cf.getOrThrow(GotConfiguredFeatures.MAHOGANY),
                treeMods(4, CountPlacement.of(1)));

        // myrrh: rarity=2 count=1-3 / sparse: rarity=4 count=1
        register(ctx, MYRRH, cf.getOrThrow(GotConfiguredFeatures.MYRRH),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, MYRRH_SPARSE, cf.getOrThrow(GotConfiguredFeatures.MYRRH),
                treeMods(4, CountPlacement.of(1)));

        // ── Wetland / riverside ───────────────────────────────────────────────

        // black_cottonwood: rarity=2 count=1-3 / sparse: rarity=4 count=1
        register(ctx, BLACK_COTTONWOOD, cf.getOrThrow(GotConfiguredFeatures.BLACK_COTTONWOOD),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, BLACK_COTTONWOOD_SPARSE, cf.getOrThrow(GotConfiguredFeatures.BLACK_COTTONWOOD),
                treeMods(4, CountPlacement.of(1)));

        // cottonwood: rarity=2 count=1-3 / sparse: rarity=4 count=1
        register(ctx, COTTONWOOD, cf.getOrThrow(GotConfiguredFeatures.COTTONWOOD),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, COTTONWOOD_SPARSE, cf.getOrThrow(GotConfiguredFeatures.COTTONWOOD),
                treeMods(4, CountPlacement.of(1)));

        // ── Dark / special ────────────────────────────────────────────────────

        // blackbark: rarity=2 count=1-3 / sparse: rarity=4 count=1
        register(ctx, BLACKBARK, cf.getOrThrow(GotConfiguredFeatures.BLACKBARK),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, BLACKBARK_SPARSE, cf.getOrThrow(GotConfiguredFeatures.BLACKBARK),
                treeMods(4, CountPlacement.of(1)));

        // bloodwood: rarity=2 count=1-3 / sparse: rarity=4 count=1
        register(ctx, BLOODWOOD, cf.getOrThrow(GotConfiguredFeatures.BLOODWOOD),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, BLOODWOOD_SPARSE, cf.getOrThrow(GotConfiguredFeatures.BLOODWOOD),
                treeMods(4, CountPlacement.of(1)));

        // ironwood: rarity=3 count=3-5 / sparse: rarity=30 count=2
        register(ctx, IRONWOOD, cf.getOrThrow(GotConfiguredFeatures.IRONWOOD),
                treeMods(3, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(3, 5))));
        register(ctx, IRONWOOD_SPARSE, cf.getOrThrow(GotConfiguredFeatures.IRONWOOD),
                treeMods(30, CountPlacement.of(2)));

        // shrub: rarity=10 count=1
        register(ctx, SHRUB, cf.getOrThrow(GotConfiguredFeatures.SHRUB),
                treeMods(10, CountPlacement.of(1)));

        // weirwood: rarity=35, no count (single attempt), no block_pred omitted—JSON has it
        register(ctx, WEIRWOOD, cf.getOrThrow(GotConfiguredFeatures.WEIRWOOD),
                List.of(RarityFilter.onAverageOnceEvery(35),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BlockPredicateFilter.forPredicate(BlockPredicate.solid(
                                net.minecraft.core.BlockPos.ZERO.below())),
                        BiomeFilter.biome()));

        // wormtree: rarity=2 count=1-3 / sparse: rarity=4 count=1-3
        register(ctx, WORMTREE, cf.getOrThrow(GotConfiguredFeatures.WORMTREE),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, WORMTREE_SPARSE, cf.getOrThrow(GotConfiguredFeatures.WORMTREE),
                treeMods(4, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));

        // ══════════════════════════════════════════════════════════════════════
        // BOULDERS  (rarity=20, count=2, heightmap WORLD_SURFACE_WG, biome only)
        // ══════════════════════════════════════════════════════════════════════

        register(ctx, BOULDER, cf.getOrThrow(GotConfiguredFeatures.BOULDER),
                List.of(RarityFilter.onAverageOnceEvery(20), CountPlacement.of(2),
                        InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));

        register(ctx, GREY_GRANITE_BOULDER, cf.getOrThrow(GotConfiguredFeatures.GREY_GRANITE_BOULDER),
                List.of(RarityFilter.onAverageOnceEvery(20), CountPlacement.of(2),
                        InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));

        register(ctx, LIMESTONE_BOULDER, cf.getOrThrow(GotConfiguredFeatures.LIMESTONE_BOULDER),
                List.of(RarityFilter.onAverageOnceEvery(20), CountPlacement.of(2),
                        InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));

        register(ctx, SLATE_BOULDER, cf.getOrThrow(GotConfiguredFeatures.SLATE_BOULDER),
                List.of(RarityFilter.onAverageOnceEvery(20), CountPlacement.of(2),
                        InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));

        // ══════════════════════════════════════════════════════════════════════
        // DISKS  (near water: OCEAN_FLOOR_WG heightmap, matching_fluids predicate)
        // ══════════════════════════════════════════════════════════════════════

        // disk_clay: count=3, OCEAN_FLOOR_WG, matching_fluids(water), biome
        register(ctx, DISK_CLAY, cf.getOrThrow(GotConfiguredFeatures.DISK_CLAY),
                List.of(CountPlacement.of(3),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_TOP_SOLID,
                        BlockPredicateFilter.forPredicate(
                                BlockPredicate.matchesFluids(net.minecraft.core.BlockPos.ZERO,
                                        net.minecraft.world.level.material.Fluids.WATER)),
                        BiomeFilter.biome()));

        // disk_sand: no count, OCEAN_FLOOR_WG, matching_fluids(water), biome
        register(ctx, DISK_SAND, cf.getOrThrow(GotConfiguredFeatures.DISK_SAND),
                List.of(InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_TOP_SOLID,
                        BlockPredicateFilter.forPredicate(
                                BlockPredicate.matchesFluids(net.minecraft.core.BlockPos.ZERO,
                                        net.minecraft.world.level.material.Fluids.WATER)),
                        BiomeFilter.biome()));

        // ══════════════════════════════════════════════════════════════════════
        // ROCK-POCKET ORES  (count, in_square, uniform height range, biome)
        // ══════════════════════════════════════════════════════════════════════

        // ore_basalt_rock: count=3, y=16-80
        register(ctx, ORE_BASALT_ROCK, cf.getOrThrow(GotConfiguredFeatures.ORE_BASALT_ROCK),
                oreMods(3, 16, 80));

        // ore_flint_rock: rarity=2, count=3, y=32-96
        register(ctx, ORE_FLINT_ROCK, cf.getOrThrow(GotConfiguredFeatures.ORE_FLINT_ROCK),
                List.of(RarityFilter.onAverageOnceEvery(2), CountPlacement.of(3),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(32), VerticalAnchor.absolute(96)),
                        BiomeFilter.biome()));

        // ore_grey_granite_rock: count=6, y=0-128
        register(ctx, ORE_GREY_GRANITE_ROCK, cf.getOrThrow(GotConfiguredFeatures.ORE_GREY_GRANITE_ROCK),
                oreMods(6, 0, 128));

        // ore_limestone_rock: count=4, y=32-96
        register(ctx, ORE_LIMESTONE_ROCK, cf.getOrThrow(GotConfiguredFeatures.ORE_LIMESTONE_ROCK),
                oreMods(4, 32, 96));

        // ore_marble_rock: count=3, y=0-64
        register(ctx, ORE_MARBLE_ROCK, cf.getOrThrow(GotConfiguredFeatures.ORE_MARBLE_ROCK),
                oreMods(3, 0, 64));

        // ore_red_sandstone_rock: count=2, y=16-64
        register(ctx, ORE_RED_SANDSTONE_ROCK, cf.getOrThrow(GotConfiguredFeatures.ORE_RED_SANDSTONE_ROCK),
                oreMods(2, 16, 64));

        // ore_sandstone_rock: count=3, y=24-80
        register(ctx, ORE_SANDSTONE_ROCK, cf.getOrThrow(GotConfiguredFeatures.ORE_SANDSTONE_ROCK),
                oreMods(3, 24, 80));

        // ore_slate_rock: count=4, y=0-72
        register(ctx, ORE_SLATE_ROCK, cf.getOrThrow(GotConfiguredFeatures.ORE_SLATE_ROCK),
                oreMods(4, 0, 72));

        // ══════════════════════════════════════════════════════════════════════
        // BERRY BUSHES  (rarity, count=1, in_square, WORLD_SURFACE_WG, biome)
        //               No block_predicate_filter — the patch itself handles it
        // ══════════════════════════════════════════════════════════════════════

        register(ctx, BLACKBERRY_BUSH, cf.getOrThrow(GotConfiguredFeatures.BLACKBERRY_BUSH),
                patchMods(4, 1));
        register(ctx, BLUEBERRY_BUSH, cf.getOrThrow(GotConfiguredFeatures.BLUEBERRY_BUSH),
                patchMods(5, 1));
        register(ctx, RASPBERRY_BUSH, cf.getOrThrow(GotConfiguredFeatures.RASPBERRY_BUSH),
                patchMods(4, 1));
        register(ctx, STRAWBERRY_BUSH, cf.getOrThrow(GotConfiguredFeatures.STRAWBERRY_BUSH),
                patchMods(4, 1));

        // ══════════════════════════════════════════════════════════════════════
        // WILD CROPS  (rarity, optional count, in_square, WORLD_SURFACE_WG, biome)
        // Some crops omit the explicit count modifier (just rarity + in_square)
        // ══════════════════════════════════════════════════════════════════════

        // wild_barley:  rarity=2, no count
        register(ctx, WILD_BARLEY, cf.getOrThrow(GotConfiguredFeatures.WILD_BARLEY),
                cropMods(2, false));
        // wild_beetroot: rarity=4, count=1
        register(ctx, WILD_BEETROOT, cf.getOrThrow(GotConfiguredFeatures.WILD_BEETROOT),
                cropMods(4, true));
        // wild_cabbage: rarity=5, count=1
        register(ctx, WILD_CABBAGE, cf.getOrThrow(GotConfiguredFeatures.WILD_CABBAGE),
                cropMods(5, true));
        // wild_carrot: rarity=3, count=1
        register(ctx, WILD_CARROT, cf.getOrThrow(GotConfiguredFeatures.WILD_CARROT),
                cropMods(3, true));
        // wild_cotton: rarity=4, no count
        register(ctx, WILD_COTTON, cf.getOrThrow(GotConfiguredFeatures.WILD_COTTON),
                cropMods(4, false));
        // wild_garlic: rarity=5, count=1
        register(ctx, WILD_GARLIC, cf.getOrThrow(GotConfiguredFeatures.WILD_GARLIC),
                cropMods(5, true));
        // wild_horseradish: rarity=6, count=1
        register(ctx, WILD_HORSERADISH, cf.getOrThrow(GotConfiguredFeatures.WILD_HORSERADISH),
                cropMods(6, true));
        // wild_leek: rarity=6, count=1
        register(ctx, WILD_LEEK, cf.getOrThrow(GotConfiguredFeatures.WILD_LEEK),
                cropMods(6, true));
        // wild_neep: rarity=5, count=1
        register(ctx, WILD_NEEP, cf.getOrThrow(GotConfiguredFeatures.WILD_NEEP),
                cropMods(5, true));
        // wild_oat: rarity=2, no count
        register(ctx, WILD_OAT, cf.getOrThrow(GotConfiguredFeatures.WILD_OAT),
                cropMods(2, false));
        // wild_onion: rarity=4, count=1
        register(ctx, WILD_ONION, cf.getOrThrow(GotConfiguredFeatures.WILD_ONION),
                cropMods(4, true));
        // wild_parsnip: rarity=4, count=1
        register(ctx, WILD_PARSNIP, cf.getOrThrow(GotConfiguredFeatures.WILD_PARSNIP),
                cropMods(4, true));
        // wild_peas: rarity=5, count=1
        register(ctx, WILD_PEAS, cf.getOrThrow(GotConfiguredFeatures.WILD_PEAS),
                cropMods(5, true));
        // wild_peppercorn: rarity=6, no count
        register(ctx, WILD_PEPPERCORN, cf.getOrThrow(GotConfiguredFeatures.WILD_PEPPERCORN),
                cropMods(6, false));
        // wild_rye: rarity=3, no count
        register(ctx, WILD_RYE, cf.getOrThrow(GotConfiguredFeatures.WILD_RYE),
                cropMods(3, false));
        // wild_turnip: rarity=4, count=1
        register(ctx, WILD_TURNIP, cf.getOrThrow(GotConfiguredFeatures.WILD_TURNIP),
                cropMods(4, true));
        // wild_wheat: rarity=2, no count
        register(ctx, WILD_WHEAT, cf.getOrThrow(GotConfiguredFeatures.WILD_WHEAT),
                cropMods(2, false));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Placement modifier helpers
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Standard tree placement stack:
     * rarity(chance) → countModifier → in_square → heightmap(WORLD_SURFACE_WG)
     *   → block_predicate_filter(solid below) → biome
     */
    private static List<PlacementModifier> treeMods(int raritychance, PlacementModifier countModifier) {
        return List.of(
                RarityFilter.onAverageOnceEvery(raritychance),
                countModifier,
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BlockPredicateFilter.forPredicate(BlockPredicate.solid(
                        net.minecraft.core.BlockPos.ZERO.below())),
                BiomeFilter.biome());
    }

    /**
     * Berry/patch placement stack:
     * rarity(chance) → count(n) → in_square → heightmap(WORLD_SURFACE_WG) → biome
     */
    private static List<PlacementModifier> patchMods(int raritychance, int count) {
        return List.of(
                RarityFilter.onAverageOnceEvery(raritychance),
                CountPlacement.of(count),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome());
    }

    /**
     * Wild-crop placement stack — some crops don't have an explicit count modifier
     * in the JSON (they rely on the random-patch trying multiple positions internally).
     */
    private static List<PlacementModifier> cropMods(int raritychance, boolean withCount) {
        if (withCount) {
            return List.of(
                    RarityFilter.onAverageOnceEvery(raritychance),
                    CountPlacement.of(1),
                    InSquarePlacement.spread(),
                    PlacementUtils.HEIGHTMAP,
                    BiomeFilter.biome());
        }
        return List.of(
                RarityFilter.onAverageOnceEvery(raritychance),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome());
    }

    /**
     * Standard rock-pocket ore placement:
     * count(n) → in_square → height_range(uniform, yMin, yMax) → biome
     */
    private static List<PlacementModifier> oreMods(int count, int yMin, int yMax) {
        return List.of(
                CountPlacement.of(count),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(yMin), VerticalAnchor.absolute(yMax)),
                BiomeFilter.biome());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Registry helpers
    // ─────────────────────────────────────────────────────────────────────────

    public static ResourceKey<PlacedFeature> key(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE,
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> ctx,
                                 ResourceKey<PlacedFeature> key,
                                 Holder<ConfiguredFeature<?, ?>> configured,
                                 List<PlacementModifier> modifiers) {
        ctx.register(key, new PlacedFeature(configured, List.copyOf(modifiers)));
    }

    private GotPlacedFeatures() {}
}