package net.francis.got.worldgen.biome;

import net.francis.got.GotMod;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public final class PlacedFeatures {

    public static final ResourceKey<PlacedFeature> ALDER              = key("alder");

    public static final ResourceKey<PlacedFeature> APPLE              = key("apple");
    public static final ResourceKey<PlacedFeature> APPLE_SPARSE       = key("apple_sparse");

    public static final ResourceKey<PlacedFeature> ASH                = key("ash");
    public static final ResourceKey<PlacedFeature> ASH_SPARSE         = key("ash_sparse");

    public static final ResourceKey<PlacedFeature> ASPEN              = key("aspen");
    public static final ResourceKey<PlacedFeature> ASPEN_SPARSE       = key("aspen_sparse");

    public static final ResourceKey<PlacedFeature> BEECH              = key("beech");
    public static final ResourceKey<PlacedFeature> BEECH_SPARSE       = key("beech_sparse");

    public static final ResourceKey<PlacedFeature> BIRCH              = key("birch");
    public static final ResourceKey<PlacedFeature> BIRCH_SPARSE       = key("birch_sparse");

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

    public static final ResourceKey<PlacedFeature> BLACK_COTTONWOOD        = key("black_cottonwood");
    public static final ResourceKey<PlacedFeature> BLACK_COTTONWOOD_SPARSE = key("black_cottonwood_sparse");

    public static final ResourceKey<PlacedFeature> COTTONWOOD         = key("cottonwood");
    public static final ResourceKey<PlacedFeature> COTTONWOOD_SPARSE  = key("cottonwood_sparse");

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

    public static final ResourceKey<PlacedFeature> BOULDER              = key("boulder");
    public static final ResourceKey<PlacedFeature> GREY_GRANITE_BOULDER = key("grey_granite_boulder");
    public static final ResourceKey<PlacedFeature> LIMESTONE_BOULDER    = key("limestone_boulder");
    public static final ResourceKey<PlacedFeature> SLATE_BOULDER        = key("slate_boulder");

    public static final ResourceKey<PlacedFeature> DISK_CLAY  = key("disk_clay");
    public static final ResourceKey<PlacedFeature> DISK_SAND  = key("disk_sand");

    public static final ResourceKey<PlacedFeature> DISK_QUAGMIRE     = key("disk_quagmire");
    public static final ResourceKey<PlacedFeature> REEDS_PATCH       = key("reeds_patch");

    public static final ResourceKey<PlacedFeature> NOISY_PATCH_MUD    = key("noisy_patch_mud");
    public static final ResourceKey<PlacedFeature> NOISY_PATCH_GRAVEL = key("noisy_patch_gravel");
    public static final ResourceKey<PlacedFeature> NOISY_PATCH_SAND   = key("noisy_patch_sand");
    public static final ResourceKey<PlacedFeature> NOISY_PATCH_DIRT   = key("noisy_patch_dirt");

    public static final ResourceKey<PlacedFeature> ORE_BASALT_ROCK        = key("ore_basalt_rock");
    public static final ResourceKey<PlacedFeature> ORE_FLINT_ROCK         = key("ore_flint_rock");
    public static final ResourceKey<PlacedFeature> ORE_GREY_GRANITE_ROCK  = key("ore_grey_granite_rock");
    public static final ResourceKey<PlacedFeature> ORE_LIMESTONE_ROCK     = key("ore_limestone_rock");
    public static final ResourceKey<PlacedFeature> ORE_MARBLE_ROCK        = key("ore_marble_rock");
    public static final ResourceKey<PlacedFeature> ORE_RED_SANDSTONE_ROCK = key("ore_red_sandstone_rock");
    public static final ResourceKey<PlacedFeature> ORE_SANDSTONE_ROCK     = key("ore_sandstone_rock");
    public static final ResourceKey<PlacedFeature> ORE_SLATE_ROCK         = key("ore_slate_rock");

    public static final ResourceKey<PlacedFeature> BLACKBERRY_BUSH   = key("blackberry_bush");
    public static final ResourceKey<PlacedFeature> BLUEBERRY_BUSH    = key("blueberry_bush");
    public static final ResourceKey<PlacedFeature> RASPBERRY_BUSH    = key("raspberry_bush");
    public static final ResourceKey<PlacedFeature> STRAWBERRY_BUSH   = key("strawberry_bush");

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

    public static void bootstrap(BootstrapContext<PlacedFeature> ctx) {
        HolderGetter<ConfiguredFeature<?, ?>> cf = ctx.lookup(Registries.CONFIGURED_FEATURE);

        register(ctx, ALDER, cf.getOrThrow(ConfiguredFeatures.ALDER),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(2, 3))));

        register(ctx, APPLE, cf.getOrThrow(ConfiguredFeatures.APPLE),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, APPLE_SPARSE, cf.getOrThrow(ConfiguredFeatures.APPLE),
                treeMods(4, CountPlacement.of(1)));

        register(ctx, ASH, cf.getOrThrow(ConfiguredFeatures.ASH),
                treeMods(1, CountPlacement.of(2)));
        register(ctx, ASH_SPARSE, cf.getOrThrow(ConfiguredFeatures.ASH),
                treeMods(35, CountPlacement.of(1)));

        register(ctx, ASPEN, cf.getOrThrow(ConfiguredFeatures.ASPEN),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, ASPEN_SPARSE, cf.getOrThrow(ConfiguredFeatures.ASPEN),
                treeMods(35, CountPlacement.of(1)));

        register(ctx, BIRCH, cf.getOrThrow(ConfiguredFeatures.BIRCH),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, BIRCH_SPARSE, cf.getOrThrow(ConfiguredFeatures.BIRCH),
                treeMods(35, CountPlacement.of(1)));

        register(ctx, BEECH, cf.getOrThrow(ConfiguredFeatures.BEECH),
                treeMods(1, CountPlacement.of(2)));
        register(ctx, BEECH_SPARSE, cf.getOrThrow(ConfiguredFeatures.BEECH),
                treeMods(35, CountPlacement.of(1)));

        register(ctx, CHESTNUT, cf.getOrThrow(ConfiguredFeatures.CHESTNUT),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, CHESTNUT_SPARSE, cf.getOrThrow(ConfiguredFeatures.CHESTNUT),
                treeMods(4, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));

        register(ctx, ELM, cf.getOrThrow(ConfiguredFeatures.ELM),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, ELM_SPARSE, cf.getOrThrow(ConfiguredFeatures.ELM),
                treeMods(4, CountPlacement.of(1)));

        register(ctx, HAWTHORN, cf.getOrThrow(ConfiguredFeatures.HAWTHORN),
                treeMods(1, CountPlacement.of(2)));
        register(ctx, HAWTHORN_SPARSE, cf.getOrThrow(ConfiguredFeatures.HAWTHORN),
                treeMods(35, CountPlacement.of(1)));

        register(ctx, LINDEN, cf.getOrThrow(ConfiguredFeatures.LINDEN),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, LINDEN_SPARSE, cf.getOrThrow(ConfiguredFeatures.LINDEN),
                treeMods(4, CountPlacement.of(1)));

        register(ctx, MAPLE, cf.getOrThrow(ConfiguredFeatures.MAPLE),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, MAPLE_SPARSE, cf.getOrThrow(ConfiguredFeatures.MAPLE),
                treeMods(4, CountPlacement.of(1)));

        register(ctx, WILLOW, cf.getOrThrow(ConfiguredFeatures.WILLOW),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, WILLOW_SPARSE, cf.getOrThrow(ConfiguredFeatures.WILLOW),
                treeMods(4, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));

        register(ctx, CEDAR, cf.getOrThrow(ConfiguredFeatures.CEDAR),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, CEDAR_SPARSE, cf.getOrThrow(ConfiguredFeatures.CEDAR),
                treeMods(4, CountPlacement.of(1)));

        register(ctx, FIR, cf.getOrThrow(ConfiguredFeatures.FIR),
                treeMods(1, CountPlacement.of(2)));
        register(ctx, FIR_SPARSE, cf.getOrThrow(ConfiguredFeatures.FIR),
                treeMods(35, CountPlacement.of(1)));

        register(ctx, PINE, cf.getOrThrow(ConfiguredFeatures.PINE),
                treeMods(1, CountPlacement.of(2)));
        register(ctx, PINE_SPARSE, cf.getOrThrow(ConfiguredFeatures.PINE),
                treeMods(35, CountPlacement.of(1)));

        register(ctx, REDWOOD, cf.getOrThrow(ConfiguredFeatures.REDWOOD),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, REDWOOD_SPARSE, cf.getOrThrow(ConfiguredFeatures.REDWOOD),
                treeMods(4, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));

        register(ctx, SENTINAL, cf.getOrThrow(ConfiguredFeatures.SENTINAL),
                treeMods(2, CountPlacement.of(2)));

        register(ctx, SOLDIER_PINE, cf.getOrThrow(ConfiguredFeatures.SOLDIER_PINE),
                treeMods(1, CountPlacement.of(2)));

        register(ctx, BLUE_MAHOE, cf.getOrThrow(ConfiguredFeatures.BLUE_MAHOE),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, BLUE_MAHOE_SPARSE, cf.getOrThrow(ConfiguredFeatures.BLUE_MAHOE),
                treeMods(4, CountPlacement.of(1)));

        register(ctx, CINNAMON, cf.getOrThrow(ConfiguredFeatures.CINNAMON),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, CINNAMON_SPARSE, cf.getOrThrow(ConfiguredFeatures.CINNAMON),
                treeMods(4, CountPlacement.of(1)));

        register(ctx, CLOVE, cf.getOrThrow(ConfiguredFeatures.CLOVE),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, CLOVE_SPARSE, cf.getOrThrow(ConfiguredFeatures.CLOVE),
                treeMods(4, CountPlacement.of(1)));

        register(ctx, EBONY, cf.getOrThrow(ConfiguredFeatures.EBONY),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, EBONY_SPARSE, cf.getOrThrow(ConfiguredFeatures.EBONY),
                treeMods(4, CountPlacement.of(1)));

        register(ctx, GOLDENHEART, cf.getOrThrow(ConfiguredFeatures.GOLDENHEART),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, GOLDENHEART_SPARSE, cf.getOrThrow(ConfiguredFeatures.GOLDENHEART),
                treeMods(4, CountPlacement.of(1)));

        register(ctx, MAHOGANY, cf.getOrThrow(ConfiguredFeatures.MAHOGANY),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, MAHOGANY_SPARSE, cf.getOrThrow(ConfiguredFeatures.MAHOGANY),
                treeMods(4, CountPlacement.of(1)));

        register(ctx, MYRRH, cf.getOrThrow(ConfiguredFeatures.MYRRH),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, MYRRH_SPARSE, cf.getOrThrow(ConfiguredFeatures.MYRRH),
                treeMods(4, CountPlacement.of(1)));

        register(ctx, BLACK_COTTONWOOD, cf.getOrThrow(ConfiguredFeatures.BLACK_COTTONWOOD),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, BLACK_COTTONWOOD_SPARSE, cf.getOrThrow(ConfiguredFeatures.BLACK_COTTONWOOD),
                treeMods(4, CountPlacement.of(1)));

        register(ctx, COTTONWOOD, cf.getOrThrow(ConfiguredFeatures.COTTONWOOD),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, COTTONWOOD_SPARSE, cf.getOrThrow(ConfiguredFeatures.COTTONWOOD),
                treeMods(4, CountPlacement.of(1)));

        register(ctx, BLACKBARK, cf.getOrThrow(ConfiguredFeatures.BLACKBARK),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, BLACKBARK_SPARSE, cf.getOrThrow(ConfiguredFeatures.BLACKBARK),
                treeMods(4, CountPlacement.of(1)));

        register(ctx, BLOODWOOD, cf.getOrThrow(ConfiguredFeatures.BLOODWOOD),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, BLOODWOOD_SPARSE, cf.getOrThrow(ConfiguredFeatures.BLOODWOOD),
                treeMods(4, CountPlacement.of(1)));

        register(ctx, IRONWOOD, cf.getOrThrow(ConfiguredFeatures.IRONWOOD),
                treeMods(3, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(3, 5))));
        register(ctx, IRONWOOD_SPARSE, cf.getOrThrow(ConfiguredFeatures.IRONWOOD),
                treeMods(30, CountPlacement.of(2)));

        register(ctx, SHRUB, cf.getOrThrow(ConfiguredFeatures.SHRUB),
                treeMods(10, CountPlacement.of(1)));

        register(ctx, WEIRWOOD, cf.getOrThrow(ConfiguredFeatures.WEIRWOOD),
                List.of(RarityFilter.onAverageOnceEvery(35),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BlockPredicateFilter.forPredicate(BlockPredicate.solid(
                                net.minecraft.core.BlockPos.ZERO.below())),
                        BiomeFilter.biome()));

        register(ctx, WORMTREE, cf.getOrThrow(ConfiguredFeatures.WORMTREE),
                treeMods(2, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));
        register(ctx, WORMTREE_SPARSE, cf.getOrThrow(ConfiguredFeatures.WORMTREE),
                treeMods(4, CountPlacement.of(net.minecraft.util.valueproviders.UniformInt.of(1, 3))));

        register(ctx, BOULDER, cf.getOrThrow(ConfiguredFeatures.BOULDER),
                List.of(RarityFilter.onAverageOnceEvery(20), CountPlacement.of(2),
                        InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));

        register(ctx, GREY_GRANITE_BOULDER, cf.getOrThrow(ConfiguredFeatures.GREY_GRANITE_BOULDER),
                List.of(RarityFilter.onAverageOnceEvery(20), CountPlacement.of(2),
                        InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));

        register(ctx, LIMESTONE_BOULDER, cf.getOrThrow(ConfiguredFeatures.LIMESTONE_BOULDER),
                List.of(RarityFilter.onAverageOnceEvery(20), CountPlacement.of(2),
                        InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));

        register(ctx, SLATE_BOULDER, cf.getOrThrow(ConfiguredFeatures.SLATE_BOULDER),
                List.of(RarityFilter.onAverageOnceEvery(20), CountPlacement.of(2),
                        InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));

        register(ctx, DISK_CLAY, cf.getOrThrow(ConfiguredFeatures.DISK_CLAY),
                List.of(CountPlacement.of(3),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_TOP_SOLID,
                        BlockPredicateFilter.forPredicate(
                                BlockPredicate.matchesFluids(net.minecraft.core.BlockPos.ZERO,
                                        net.minecraft.world.level.material.Fluids.WATER)),
                        BiomeFilter.biome()));

        register(ctx, DISK_SAND, cf.getOrThrow(ConfiguredFeatures.DISK_SAND),
                List.of(InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_TOP_SOLID,
                        BlockPredicateFilter.forPredicate(
                                BlockPredicate.matchesFluids(net.minecraft.core.BlockPos.ZERO,
                                        net.minecraft.world.level.material.Fluids.WATER)),
                        BiomeFilter.biome()));

        register(ctx, DISK_QUAGMIRE, cf.getOrThrow(ConfiguredFeatures.DISK_QUAGMIRE),
                List.of(CountPlacement.of(4),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()));

        register(ctx, REEDS_PATCH, cf.getOrThrow(ConfiguredFeatures.REEDS_PATCH),
                List.of(CountPlacement.of(1),
                        InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(net.minecraft.world.level.levelgen.Heightmap.Types.OCEAN_FLOOR_WG),
                        BiomeFilter.biome()));

        register(ctx, NOISY_PATCH_MUD, cf.getOrThrow(ConfiguredFeatures.NOISY_PATCH_MUD),
                List.of(RarityFilter.onAverageOnceEvery(4),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()));

        register(ctx, NOISY_PATCH_GRAVEL, cf.getOrThrow(ConfiguredFeatures.NOISY_PATCH_GRAVEL),
                List.of(RarityFilter.onAverageOnceEvery(4),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()));

        register(ctx, NOISY_PATCH_SAND, cf.getOrThrow(ConfiguredFeatures.NOISY_PATCH_SAND),
                List.of(RarityFilter.onAverageOnceEvery(4),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()));

        register(ctx, NOISY_PATCH_DIRT, cf.getOrThrow(ConfiguredFeatures.NOISY_PATCH_DIRT),
                List.of(RarityFilter.onAverageOnceEvery(4),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()));

        register(ctx, ORE_BASALT_ROCK, cf.getOrThrow(ConfiguredFeatures.ORE_BASALT_ROCK),
                oreMods(3, 16, 80));

        register(ctx, ORE_FLINT_ROCK, cf.getOrThrow(ConfiguredFeatures.ORE_FLINT_ROCK),
                List.of(RarityFilter.onAverageOnceEvery(2), CountPlacement.of(3),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(32), VerticalAnchor.absolute(96)),
                        BiomeFilter.biome()));

        register(ctx, ORE_GREY_GRANITE_ROCK, cf.getOrThrow(ConfiguredFeatures.ORE_GREY_GRANITE_ROCK),
                oreMods(6, 0, 128));

        register(ctx, ORE_LIMESTONE_ROCK, cf.getOrThrow(ConfiguredFeatures.ORE_LIMESTONE_ROCK),
                oreMods(4, 32, 96));

        register(ctx, ORE_MARBLE_ROCK, cf.getOrThrow(ConfiguredFeatures.ORE_MARBLE_ROCK),
                oreMods(3, 0, 64));

        register(ctx, ORE_RED_SANDSTONE_ROCK, cf.getOrThrow(ConfiguredFeatures.ORE_RED_SANDSTONE_ROCK),
                oreMods(2, 16, 64));

        register(ctx, ORE_SANDSTONE_ROCK, cf.getOrThrow(ConfiguredFeatures.ORE_SANDSTONE_ROCK),
                oreMods(3, 24, 80));

        register(ctx, ORE_SLATE_ROCK, cf.getOrThrow(ConfiguredFeatures.ORE_SLATE_ROCK),
                oreMods(4, 0, 72));

        register(ctx, BLACKBERRY_BUSH, cf.getOrThrow(ConfiguredFeatures.BLACKBERRY_BUSH),
                patchMods(4, 1));
        register(ctx, BLUEBERRY_BUSH, cf.getOrThrow(ConfiguredFeatures.BLUEBERRY_BUSH),
                patchMods(5, 1));
        register(ctx, RASPBERRY_BUSH, cf.getOrThrow(ConfiguredFeatures.RASPBERRY_BUSH),
                patchMods(4, 1));
        register(ctx, STRAWBERRY_BUSH, cf.getOrThrow(ConfiguredFeatures.STRAWBERRY_BUSH),
                patchMods(4, 1));

        register(ctx, WILD_BARLEY, cf.getOrThrow(ConfiguredFeatures.WILD_BARLEY),
                cropMods(2, false));
        
        register(ctx, WILD_BEETROOT, cf.getOrThrow(ConfiguredFeatures.WILD_BEETROOT),
                cropMods(4, true));
        
        register(ctx, WILD_CABBAGE, cf.getOrThrow(ConfiguredFeatures.WILD_CABBAGE),
                cropMods(5, true));
        
        register(ctx, WILD_CARROT, cf.getOrThrow(ConfiguredFeatures.WILD_CARROT),
                cropMods(3, true));
        
        register(ctx, WILD_COTTON, cf.getOrThrow(ConfiguredFeatures.WILD_COTTON),
                cropMods(4, false));
        
        register(ctx, WILD_GARLIC, cf.getOrThrow(ConfiguredFeatures.WILD_GARLIC),
                cropMods(5, true));
        
        register(ctx, WILD_HORSERADISH, cf.getOrThrow(ConfiguredFeatures.WILD_HORSERADISH),
                cropMods(6, true));
        
        register(ctx, WILD_LEEK, cf.getOrThrow(ConfiguredFeatures.WILD_LEEK),
                cropMods(6, true));
        
        register(ctx, WILD_NEEP, cf.getOrThrow(ConfiguredFeatures.WILD_NEEP),
                cropMods(5, true));
        
        register(ctx, WILD_OAT, cf.getOrThrow(ConfiguredFeatures.WILD_OAT),
                cropMods(2, false));
        
        register(ctx, WILD_ONION, cf.getOrThrow(ConfiguredFeatures.WILD_ONION),
                cropMods(4, true));
        
        register(ctx, WILD_PARSNIP, cf.getOrThrow(ConfiguredFeatures.WILD_PARSNIP),
                cropMods(4, true));
        
        register(ctx, WILD_PEAS, cf.getOrThrow(ConfiguredFeatures.WILD_PEAS),
                cropMods(5, true));
        
        register(ctx, WILD_PEPPERCORN, cf.getOrThrow(ConfiguredFeatures.WILD_PEPPERCORN),
                cropMods(6, false));
        
        register(ctx, WILD_RYE, cf.getOrThrow(ConfiguredFeatures.WILD_RYE),
                cropMods(3, false));
        
        register(ctx, WILD_TURNIP, cf.getOrThrow(ConfiguredFeatures.WILD_TURNIP),
                cropMods(4, true));
        
        register(ctx, WILD_WHEAT, cf.getOrThrow(ConfiguredFeatures.WILD_WHEAT),
                cropMods(2, false));
    }

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

    private static List<PlacementModifier> patchMods(int raritychance, int count) {
        return List.of(
                RarityFilter.onAverageOnceEvery(raritychance),
                CountPlacement.of(count),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome());
    }

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

    private static List<PlacementModifier> oreMods(int count, int yMin, int yMax) {
        return List.of(
                CountPlacement.of(count),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(yMin), VerticalAnchor.absolute(yMax)),
                BiomeFilter.biome());
    }

    public static ResourceKey<PlacedFeature> key(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE,
                Identifier.fromNamespaceAndPath(GotMod.MODID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> ctx,
                                 ResourceKey<PlacedFeature> key,
                                 Holder<ConfiguredFeature<?, ?>> configured,
                                 List<PlacementModifier> modifiers) {
        ctx.register(key, new PlacedFeature(configured, List.copyOf(modifiers)));
    }

    private PlacedFeatures() {}
}