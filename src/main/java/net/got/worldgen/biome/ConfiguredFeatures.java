package net.got.worldgen.biome;

import net.got.GotMod;
import net.got.init.ModBlocks;
import net.got.worldgen.biome.placers.AspenFoliagePlacer;
import net.got.worldgen.biome.placers.BroadleafFoliagePlacer;
import net.got.worldgen.biome.placers.BroadleafTrunkPlacer;
import net.got.worldgen.biome.placers.OrchardFoliagePlacer;
import net.got.worldgen.biome.placers.PalmFoliagePlacer;
import net.got.worldgen.biome.placers.PalmTrunkPlacer;
import net.got.worldgen.biome.placers.PineTrunkPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AlterGroundDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;
import java.util.OptionalInt;

public final class ConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> ALDER            = key("alder");
    public static final ResourceKey<ConfiguredFeature<?, ?>> APPLE            = key("apple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ASH              = key("ash");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ASPEN            = key("aspen");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BEECH            = key("beech");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BIRCH            = key("birch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CHESTNUT         = key("chestnut");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ELM              = key("elm");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HAWTHORN         = key("hawthorn");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LINDEN           = key("linden");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE            = key("maple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILLOW           = key("willow");

    public static final ResourceKey<ConfiguredFeature<?, ?>> CEDAR            = key("cedar");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FIR              = key("fir");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PINE             = key("pine");
    public static final ResourceKey<ConfiguredFeature<?, ?>> REDWOOD          = key("redwood");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SENTINAL         = key("sentinal");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SOLDIER_PINE     = key("soldier_pine");

    public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_MAHOE       = key("blue_mahoe");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CINNAMON         = key("cinnamon");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CLOVE            = key("clove");
    public static final ResourceKey<ConfiguredFeature<?, ?>> EBONY            = key("ebony");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDENHEART      = key("goldenheart");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MAHOGANY         = key("mahogany");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MYRRH            = key("myrrh");

    public static final ResourceKey<ConfiguredFeature<?, ?>> BLACK_COTTONWOOD = key("black_cottonwood");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COTTONWOOD       = key("cottonwood");

    public static final ResourceKey<ConfiguredFeature<?, ?>> BLACKBARK        = key("blackbark");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLOODWOOD        = key("bloodwood");
    public static final ResourceKey<ConfiguredFeature<?, ?>> IRONWOOD         = key("ironwood");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHRUB            = key("shrub");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WEIRWOOD         = key("weirwood");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WORMTREE         = key("wormtree");

    public static final ResourceKey<ConfiguredFeature<?, ?>> BOULDER              = key("boulder");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GREY_GRANITE_BOULDER  = key("grey_granite_boulder");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LIMESTONE_BOULDER     = key("limestone_boulder");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SLATE_BOULDER         = key("slate_boulder");

    public static final ResourceKey<ConfiguredFeature<?, ?>> DISK_CLAY        = key("disk_clay");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DISK_SAND        = key("disk_sand");

    public static final ResourceKey<ConfiguredFeature<?, ?>> DISK_QUAGMIRE    = key("disk_quagmire");
    public static final ResourceKey<ConfiguredFeature<?, ?>> REEDS_PATCH       = key("reeds_patch");

    public static final ResourceKey<ConfiguredFeature<?, ?>> NOISY_PATCH_MUD     = key("noisy_patch_mud");
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> NOISY_PATCH_GRAVEL  = key("noisy_patch_gravel");
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> NOISY_PATCH_SAND    = key("noisy_patch_sand");
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> NOISY_PATCH_DIRT    = key("noisy_patch_dirt");

    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_BASALT_ROCK        = key("ore_basalt_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_FLINT_ROCK         = key("ore_flint_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GREY_GRANITE_ROCK  = key("ore_grey_granite_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_LIMESTONE_ROCK     = key("ore_limestone_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_MARBLE_ROCK        = key("ore_marble_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_RED_SANDSTONE_ROCK = key("ore_red_sandstone_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SANDSTONE_ROCK     = key("ore_sandstone_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SLATE_ROCK         = key("ore_slate_rock");

    public static final ResourceKey<ConfiguredFeature<?, ?>> BLACKBERRY_BUSH  = key("blackberry_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLUEBERRY_BUSH   = key("blueberry_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RASPBERRY_BUSH   = key("raspberry_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> STRAWBERRY_BUSH  = key("strawberry_bush");

    public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_BARLEY      = key("wild_barley");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_BEETROOT    = key("wild_beetroot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_CABBAGE     = key("wild_cabbage");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_CARROT      = key("wild_carrot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_COTTON      = key("wild_cotton");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_GARLIC      = key("wild_garlic");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_HORSERADISH = key("wild_horseradish");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_LEEK        = key("wild_leek");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_NEEP        = key("wild_neep");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_OAT         = key("wild_oat");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_ONION       = key("wild_onion");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_PARSNIP     = key("wild_parsnip");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_PEAS        = key("wild_peas");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_PEPPERCORN  = key("wild_peppercorn");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_RYE         = key("wild_rye");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_TURNIP      = key("wild_turnip");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_WHEAT       = key("wild_wheat");

    public static final ResourceKey<ConfiguredFeature<?, ?>> NIGHTWOOD    = key("nightwood");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLEHEART  = key("purpleheart");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TIGERWOOD    = key("tigerwood");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SANDALWOOD   = key("sandalwood");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SANDBEGGAR   = key("sandbeggar");
    public static final ResourceKey<ConfiguredFeature<?, ?>> APRICOT      = key("apricot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLACKTHORN   = key("blackthorn");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_CHERRY       = key("red_cherry");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WHITE_CHERRY       = key("white_cherry");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLACK_CHERRY       = key("black_cherry");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CRABAPPLE    = key("crabapple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DATE_PALM    = key("date_palm");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FIG          = key("fig");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LEMON        = key("lemon");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LIME         = key("lime");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OLIVE        = key("olive");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE       = key("orange");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PEACH        = key("peach");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PEAR         = key("pear");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PERSIMMON    = key("persimmon");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PINK_IVORY   = key("pink_ivory");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PLUM         = key("plum");
    public static final ResourceKey<ConfiguredFeature<?, ?>> POMEGRANATE  = key("pomegranate");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PRUNE        = key("prune");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ALMOND       = key("almond");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NUTMEG       = key("nutmeg");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HEMLOCK      = key("hemlock");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> ctx) {

        var baseStone = new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD);

        register(ctx, ALDER, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.ALDER_LOG.get()),
                new StraightTrunkPlacer(7, 2, 3),
                BlockStateProvider.simple(ModBlocks.ALDER_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new BroadleafFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), 6),
                new TwoLayersFeatureSize(1, 0, 1))
                .dirt(BlockStateProvider.simple(ModBlocks.ALDER_WOOD.get()))
                .ignoreVines()
                .build());

        register(ctx, APPLE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.APPLE_LOG.get()),
                new StraightTrunkPlacer(4, 2, 0),
                BlockStateProvider.simple(ModBlocks.APPLE_LEAVES.get().defaultBlockState()),
                new OrchardFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                new TwoLayersFeatureSize(1, 0, 1))
                .ignoreVines()
                .build());

        register(ctx, ASH, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.ASH_LOG.get()),
                new BroadleafTrunkPlacer(7, 2, 3),
                BlockStateProvider.simple(ModBlocks.ASH_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new BroadleafFoliagePlacer(ConstantInt.of(4), ConstantInt.of(0), 6),
                new TwoLayersFeatureSize(1, 0, 2))
                .ignoreVines()
                .build());

        register(ctx, ASPEN, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.ASPEN_LOG.get()),
                new StraightTrunkPlacer(7, 2, 2),
                BlockStateProvider.simple(ModBlocks.ASPEN_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new AspenFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 4),
                new TwoLayersFeatureSize(1, 1, 1))
                .build());

        register(ctx, BIRCH, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.BIRCH_LOG),
                new StraightTrunkPlacer(7, 2, 2),
                BlockStateProvider.simple(Blocks.BIRCH_LEAVES.defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new AspenFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 4),
                new TwoLayersFeatureSize(1, 1, 1))
                .build());

        register(ctx, BEECH, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.BEECH_LOG.get()),
                new BroadleafTrunkPlacer(7, 2, 3),
                BlockStateProvider.simple(ModBlocks.BEECH_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new BroadleafFoliagePlacer(ConstantInt.of(4), ConstantInt.of(0), 6),
                new TwoLayersFeatureSize(1, 0, 2))
                .ignoreVines()
                .build());

        register(ctx, CHESTNUT, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.CHESTNUT_LOG.get()),
                new BroadleafTrunkPlacer(8, 2, 3),
                BlockStateProvider.simple(ModBlocks.CHESTNUT_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new BroadleafFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), 6),
                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))
                .dirt(BlockStateProvider.simple(ModBlocks.CHESTNUT_WOOD.get()))
                .ignoreVines()
                .build());

        register(ctx, ELM, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.ELM_LOG.get()),
                new BroadleafTrunkPlacer(7, 2, 3),
                BlockStateProvider.simple(ModBlocks.ELM_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new BroadleafFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), 6),
                new TwoLayersFeatureSize(1, 0, 2))
                .ignoreVines()
                .build());

        register(ctx, HAWTHORN, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.HAWTHORN_LOG.get()),
                new BroadleafTrunkPlacer(7, 2, 3),
                BlockStateProvider.simple(ModBlocks.HAWTHORN_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new BroadleafFoliagePlacer(ConstantInt.of(4), ConstantInt.of(0), 6),
                new TwoLayersFeatureSize(1, 0, 2))
                .ignoreVines()
                .build());

        register(ctx, LINDEN, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.LINDEN_LOG.get()),
                new StraightTrunkPlacer(8, 2, 2),
                BlockStateProvider.simple(ModBlocks.LINDEN_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new OrchardFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                new TwoLayersFeatureSize(1, 1, 1))
                .ignoreVines()
                .build());

        register(ctx, MAPLE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.MAPLE_LOG.get()),
                new StraightTrunkPlacer(4, 2, 0),
                BlockStateProvider.simple(ModBlocks.MAPLE_LEAVES.get().defaultBlockState()),
                new OrchardFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                new TwoLayersFeatureSize(1, 0, 1))
                .ignoreVines()
                .build());

        register(ctx, WILLOW, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.WILLOW_LOG.get()),
                new BroadleafTrunkPlacer(7, 2, 3),
                BlockStateProvider.simple(ModBlocks.WILLOW_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new BroadleafFoliagePlacer(ConstantInt.of(4), ConstantInt.of(0), 6),
                new TwoLayersFeatureSize(1, 0, 2))
                .ignoreVines()
                .decorators(List.of(new LeaveVineDecorator(0.15f)))
                .build());

        register(ctx, CEDAR, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.CEDAR_LOG.get()),
                new StraightTrunkPlacer(10, 3, 2),
                BlockStateProvider.simple(ModBlocks.CEDAR_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new SpruceFoliagePlacer(UniformInt.of(2, 3), UniformInt.of(0, 2), UniformInt.of(4, 5)),
                new TwoLayersFeatureSize(2, 0, 2))
                .ignoreVines()
                .build());

        register(ctx, FIR, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.FIR_LOG.get()),
                new StraightTrunkPlacer(7, 2, 1),
                BlockStateProvider.simple(ModBlocks.FIR_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new SpruceFoliagePlacer(UniformInt.of(2, 3), UniformInt.of(0, 2), UniformInt.of(4, 5)),
                new TwoLayersFeatureSize(2, 0, 2))
                .dirt(BlockStateProvider.simple(ModBlocks.FIR_WOOD.get()))
                .ignoreVines()
                .build());

        register(ctx, PINE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.PINE_LOG.get()),
                new PineTrunkPlacer(14, 3, 0),
                BlockStateProvider.simple(ModBlocks.PINE_LEAVES.get().defaultBlockState()),
                new PineFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1), UniformInt.of(3, 4)),
                new TwoLayersFeatureSize(1, 0, 2))
                .dirt(BlockStateProvider.simple(Blocks.GRASS_BLOCK))
                .ignoreVines()
                .build());

        register(ctx, REDWOOD, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.REDWOOD_LOG.get()),
                new GiantTrunkPlacer(13, 2, 14),
                BlockStateProvider.simple(ModBlocks.REDWOOD_LEAVES.get().defaultBlockState()),
                new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(3, 7)),
                new TwoLayersFeatureSize(1, 1, 2))
                .ignoreVines()
                .decorators(List.of(new AlterGroundDecorator(
                        BlockStateProvider.simple(Blocks.PODZOL.defaultBlockState()))))
                .build());

        register(ctx, SENTINAL, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.SENTINAL_LOG.get()),
                new StraightTrunkPlacer(12, 3, 3),
                BlockStateProvider.simple(ModBlocks.SENTINAL_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new SpruceFoliagePlacer(ConstantInt.of(3), ConstantInt.of(2), ConstantInt.of(3)),
                new TwoLayersFeatureSize(1, 0, 1))
                .ignoreVines()
                .build());

        register(ctx, SOLDIER_PINE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.SOLDIER_PINE_LOG.get()),
                new StraightTrunkPlacer(8, 2, 2),
                BlockStateProvider.simple(ModBlocks.SOLDIER_PINE_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new SpruceFoliagePlacer(UniformInt.of(2, 3), UniformInt.of(0, 2), UniformInt.of(4, 5)),
                new TwoLayersFeatureSize(2, 0, 2))
                .dirt(BlockStateProvider.simple(ModBlocks.SOLDIER_PINE_WOOD.get()))
                .ignoreVines()
                .build());

        register(ctx, BLUE_MAHOE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.BLUE_MAHOE_LOG.get()),
                new StraightTrunkPlacer(7, 2, 2),
                BlockStateProvider.simple(ModBlocks.BLUE_MAHOE_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new SpruceFoliagePlacer(ConstantInt.of(2), ConstantInt.of(2), ConstantInt.of(2)),
                new TwoLayersFeatureSize(1, 1, 1))
                .ignoreVines()
                .build());

        register(ctx, CINNAMON, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.CINNAMON_LOG.get()),
                new StraightTrunkPlacer(5, 2, 6),
                BlockStateProvider.simple(ModBlocks.CINNAMON_LEAVES.get().defaultBlockState()),
                new OrchardFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                new TwoLayersFeatureSize(1, 0, 1))
                .ignoreVines()
                .decorators(List.of(new BeehiveDecorator(1.0f)))
                .build());

        register(ctx, CLOVE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.CLOVE_LOG.get()),
                new StraightTrunkPlacer(7, 2, 2),
                BlockStateProvider.simple(ModBlocks.CLOVE_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new SpruceFoliagePlacer(ConstantInt.of(2), ConstantInt.of(2), ConstantInt.of(2)),
                new TwoLayersFeatureSize(1, 1, 1))
                .ignoreVines()
                .build());

        register(ctx, DATE_PALM, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.DATE_PALM_LOG.get()),
                new PalmTrunkPlacer(9, 3, 2),
                BlockStateProvider.simple(ModBlocks.DATE_PALM_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, false)),
                new PalmFoliagePlacer(
                        ConstantInt.of(6),
                        ConstantInt.of(0),
                        6,
                        8
                ),
                new TwoLayersFeatureSize(1, 0, 1))
                .ignoreVines()
                .build());

        register(ctx, EBONY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.EBONY_LOG.get()),
                new BroadleafTrunkPlacer(8, 2, 3),
                BlockStateProvider.simple(ModBlocks.EBONY_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new BroadleafFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), 6),
                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))
                .dirt(BlockStateProvider.simple(ModBlocks.EBONY_WOOD.get()))
                .ignoreVines()
                .build());

        register(ctx, GOLDENHEART, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.GOLDENHEART_LOG.get()),
                new BroadleafTrunkPlacer(7, 2, 3),
                BlockStateProvider.simple(ModBlocks.GOLDENHEART_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new BroadleafFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), 6),
                new TwoLayersFeatureSize(1, 0, 2))
                .ignoreVines()
                .build());

        register(ctx, MAHOGANY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.MAHOGANY_LOG.get()),
                new StraightTrunkPlacer(10, 2, 2),
                BlockStateProvider.simple(ModBlocks.MAHOGANY_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new OrchardFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                new TwoLayersFeatureSize(1, 1, 2))
                .ignoreVines()
                .build());

        register(ctx, MYRRH, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.MYRRH_LOG.get()),
                new ForkingTrunkPlacer(5, 2, 2),
                BlockStateProvider.simple(ModBlocks.MYRRH_LEAVES.get().defaultBlockState()),
                new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                new TwoLayersFeatureSize(1, 0, 2))
                .ignoreVines()
                .build());

        register(ctx, BLACK_COTTONWOOD, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.BLACK_COTTONWOOD_LOG.get()),
                new BroadleafTrunkPlacer(7, 2, 3),
                BlockStateProvider.simple(ModBlocks.BLACK_COTTONWOOD_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new BroadleafFoliagePlacer(ConstantInt.of(4), ConstantInt.of(0), 6),
                new TwoLayersFeatureSize(1, 0, 2))
                .ignoreVines()
                .build());

        register(ctx, COTTONWOOD, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.COTTONWOOD_LOG.get()),
                new BroadleafTrunkPlacer(7, 2, 3),
                BlockStateProvider.simple(ModBlocks.COTTONWOOD_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new BroadleafFoliagePlacer(ConstantInt.of(4), ConstantInt.of(0), 6),
                new TwoLayersFeatureSize(1, 0, 2))
                .ignoreVines()
                .build());

        register(ctx, BLACKBARK, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.BLACKBARK_LOG.get()),
                new StraightTrunkPlacer(7, 2, 2),
                BlockStateProvider.simple(ModBlocks.BLACKBARK_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new SpruceFoliagePlacer(ConstantInt.of(2), ConstantInt.of(2), ConstantInt.of(2)),
                new TwoLayersFeatureSize(1, 1, 1))
                .ignoreVines()
                .build());

        register(ctx, BLOODWOOD, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.BLOODWOOD_LOG.get()),
                new ForkingTrunkPlacer(5, 2, 2),
                BlockStateProvider.simple(ModBlocks.BLOODWOOD_LEAVES.get().defaultBlockState()),
                new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                new TwoLayersFeatureSize(1, 0, 2))
                .ignoreVines()
                .build());

        register(ctx, IRONWOOD, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.IRONWOOD_LOG.get()),
                new BroadleafTrunkPlacer(8, 2, 3),
                BlockStateProvider.simple(ModBlocks.IRONWOOD_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new BroadleafFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), 6),
                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))
                .dirt(BlockStateProvider.simple(ModBlocks.IRONWOOD_WOOD.get()))
                .ignoreVines()
                .build());

        register(ctx, SHRUB, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.OAK_LOG),
                new StraightTrunkPlacer(1, 0, 0),
                BlockStateProvider.simple(Blocks.OAK_LEAVES.defaultBlockState()),
                new BushFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), 2),
                new TwoLayersFeatureSize(0, 0, 0))
                .dirt(BlockStateProvider.simple(Blocks.OAK_WOOD))
                .build());

        register(ctx, WEIRWOOD, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.WEIRWOOD_LOG.get()),
                new BroadleafTrunkPlacer(8, 2, 3),
                BlockStateProvider.simple(ModBlocks.WEIRWOOD_LEAVES.get().defaultBlockState()),
                new BroadleafFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), 6),
                new TwoLayersFeatureSize(0, 2, 0))
                .dirt(BlockStateProvider.simple(ModBlocks.WEIRWOOD_WOOD.get()))
                .ignoreVines()
                .build());

        register(ctx, WORMTREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.WORMTREE_LOG.get()),
                new StraightTrunkPlacer(8, 2, 2),
                BlockStateProvider.simple(ModBlocks.WORMTREE_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new OrchardFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                new TwoLayersFeatureSize(1, 1, 1))
                .ignoreVines()
                .build());

        List<BlockState> boulderTargets = List.of(
                Blocks.GRASS_BLOCK.defaultBlockState(),
                Blocks.DIRT.defaultBlockState(),
                Blocks.DIRT_PATH.defaultBlockState(),
                Blocks.COARSE_DIRT.defaultBlockState(),
                Blocks.PODZOL.defaultBlockState(),
                Blocks.STONE.defaultBlockState(),
                Blocks.SAND.defaultBlockState(),
                Blocks.GRAVEL.defaultBlockState());

        register(ctx, BOULDER, net.got.registry.WorldgenRegistries.BOULDER.get(),
                new net.got.worldgen.biome.placers.BoulderFeature.Config(
                        mossyBlend(Blocks.STONE.defaultBlockState()),
                        boulderTargets,
                        3,
                        0.8,
                        0.35));

        register(ctx, GREY_GRANITE_BOULDER, net.got.registry.WorldgenRegistries.BOULDER.get(),
                new net.got.worldgen.biome.placers.BoulderFeature.Config(
                        mossyBlend(ModBlocks.GREY_GRANITE_ROCK.get().defaultBlockState()),
                        boulderTargets,
                        3, 0.8, 0.35));

        register(ctx, LIMESTONE_BOULDER, net.got.registry.WorldgenRegistries.BOULDER.get(),
                new net.got.worldgen.biome.placers.BoulderFeature.Config(
                        mossyBlend(ModBlocks.LIMESTONE_ROCK.get().defaultBlockState()),
                        boulderTargets,
                        3, 0.8, 0.35));

        register(ctx, SLATE_BOULDER, net.got.registry.WorldgenRegistries.BOULDER.get(),
                new net.got.worldgen.biome.placers.BoulderFeature.Config(
                        mossyBlend(ModBlocks.SLATE_ROCK.get().defaultBlockState()),
                        boulderTargets,
                        3, 0.8, 0.35));

        register(ctx, DISK_CLAY, Feature.DISK, new DiskConfiguration(
                RuleBasedBlockStateProvider.simple(Blocks.CLAY),
                BlockPredicate.matchesBlocks(Blocks.DIRT, Blocks.GRASS_BLOCK),
                UniformInt.of(2, 6),
                2));

        register(ctx, DISK_SAND, Feature.DISK, new DiskConfiguration(
                RuleBasedBlockStateProvider.simple(Blocks.SAND),
                BlockPredicate.matchesBlocks(Blocks.DIRT, Blocks.SAND),
                UniformInt.of(2, 3),
                1));

        register(ctx, DISK_QUAGMIRE, Feature.DISK, new DiskConfiguration(
                RuleBasedBlockStateProvider.simple(ModBlocks.QUAGMIRE.get().defaultBlockState().getBlock()),
                BlockPredicate.matchesBlocks(Blocks.DIRT, Blocks.GRASS_BLOCK, Blocks.MUD, Blocks.CLAY),
                UniformInt.of(1, 3),
                1));

        register(ctx, REEDS_PATCH,
                net.got.registry.WorldgenRegistries.TRIPLE_REEDS_PATCH.get(),
                NoneFeatureConfiguration.INSTANCE);

        register(ctx, NOISY_PATCH_MUD,
                net.got.registry.WorldgenRegistries.NOISY_BLOCK_PATCH.get(),
                new net.got.worldgen.biome.placers.NoisyBlockPatchFeature.Config(
                        BlockStateProvider.simple(Blocks.MUD),
                        List.of(Blocks.DIRT.defaultBlockState(),
                                Blocks.GRASS_BLOCK.defaultBlockState(),
                                Blocks.CLAY.defaultBlockState()),
                        8,
                        net.got.worldgen.biome.placers.NoisyBlockPatchFeature.Mode.BLOB,
                        1.8,
                        1.0,
                        0.10,
                        0.55,
                        0.18,
                        0.42,
                        0.25,
                        false));
        register(ctx, NOISY_PATCH_GRAVEL,
                net.got.registry.WorldgenRegistries.NOISY_BLOCK_PATCH.get(),
                new net.got.worldgen.biome.placers.NoisyBlockPatchFeature.Config(
                        BlockStateProvider.simple(Blocks.GRAVEL),
                        List.of(Blocks.DIRT.defaultBlockState(),
                                Blocks.GRASS_BLOCK.defaultBlockState(),
                                Blocks.SAND.defaultBlockState()),
                        7,
                        net.got.worldgen.biome.placers.NoisyBlockPatchFeature.Mode.BLOB,
                        2.0,
                        1.0,
                        0.20,
                        0.70,
                        0.22,
                        0.55,
                        0.30,
                        false));
        register(ctx, NOISY_PATCH_SAND,
                net.got.registry.WorldgenRegistries.NOISY_BLOCK_PATCH.get(),
                new net.got.worldgen.biome.placers.NoisyBlockPatchFeature.Config(
                        BlockStateProvider.simple(Blocks.SAND),
                        List.of(Blocks.DIRT.defaultBlockState(),
                                Blocks.GRASS_BLOCK.defaultBlockState(),
                                Blocks.GRAVEL.defaultBlockState()),
                        9,
                        net.got.worldgen.biome.placers.NoisyBlockPatchFeature.Mode.BLOB,
                        1.6,
                        1.0,
                        0.05,
                        0.40,
                        0.14,
                        0.35,
                        0.20,
                        false));
        register(ctx, NOISY_PATCH_DIRT,
                net.got.registry.WorldgenRegistries.NOISY_BLOCK_PATCH.get(),
                new net.got.worldgen.biome.placers.NoisyBlockPatchFeature.Config(
                        BlockStateProvider.simple(Blocks.DIRT),
                        List.of(Blocks.GRASS_BLOCK.defaultBlockState(),
                                Blocks.DIRT_PATH.defaultBlockState()),
                        7,
                        net.got.worldgen.biome.placers.NoisyBlockPatchFeature.Mode.BLOB,
                        1.7,
                        1.0,
                        0.12,
                        0.60,
                        0.20,
                        0.45,
                        0.28,
                        false));
        
        register(ctx, ORE_BASALT_ROCK, Feature.ORE, new OreConfiguration(
                List.of(OreConfiguration.target(baseStone,
                        Blocks.BASALT.defaultBlockState())), 64));

        register(ctx, ORE_FLINT_ROCK, Feature.ORE, new OreConfiguration(
                List.of(OreConfiguration.target(baseStone,
                        ModBlocks.FLINT_ROCK.get().defaultBlockState())), 64));

        register(ctx, ORE_GREY_GRANITE_ROCK, Feature.ORE, new OreConfiguration(
                List.of(OreConfiguration.target(baseStone,
                        ModBlocks.GREY_GRANITE_ROCK.get().defaultBlockState())), 64));

        register(ctx, ORE_LIMESTONE_ROCK, Feature.ORE, new OreConfiguration(
                List.of(OreConfiguration.target(baseStone,
                        ModBlocks.LIMESTONE_ROCK.get().defaultBlockState())), 64));

        register(ctx, ORE_MARBLE_ROCK, Feature.ORE, new OreConfiguration(
                List.of(OreConfiguration.target(baseStone,
                        ModBlocks.MARBLE_ROCK.get().defaultBlockState())), 64));

        register(ctx, ORE_RED_SANDSTONE_ROCK, Feature.ORE, new OreConfiguration(
                List.of(OreConfiguration.target(baseStone,
                        Blocks.RED_SANDSTONE.defaultBlockState())), 64));

        register(ctx, ORE_SANDSTONE_ROCK, Feature.ORE, new OreConfiguration(
                List.of(OreConfiguration.target(baseStone,
                        Blocks.SANDSTONE.defaultBlockState())), 64));

        register(ctx, ORE_SLATE_ROCK, Feature.ORE, new OreConfiguration(
                List.of(OreConfiguration.target(baseStone,
                        ModBlocks.SLATE_ROCK.get().defaultBlockState())), 64));

        register(ctx, BLACKBERRY_BUSH, Feature.RANDOM_PATCH, berryPatch(ModBlocks.BLACKBERRY_BUSH, 10, 4, 3));
        register(ctx, BLUEBERRY_BUSH,  Feature.RANDOM_PATCH, berryPatch(ModBlocks.BLUEBERRY_BUSH,  10, 4, 3));
        register(ctx, RASPBERRY_BUSH,  Feature.RANDOM_PATCH, berryPatch(ModBlocks.RASPBERRY_BUSH,  10, 4, 3));

        register(ctx, WILD_BARLEY,     Feature.RANDOM_PATCH, cropPatch(ModBlocks.WILD_BARLEY,     16, 5, 3));
        register(ctx, WILD_BEETROOT,   Feature.RANDOM_PATCH, cropPatch(ModBlocks.WILD_BEETROOT,   12, 4, 3));
        register(ctx, WILD_CABBAGE,    Feature.RANDOM_PATCH, cropPatch(ModBlocks.WILD_CABBAGE,     8, 4, 3));
        register(ctx, WILD_CARROT,     Feature.RANDOM_PATCH, cropPatch(ModBlocks.WILD_CARROT,     14, 5, 3));
        register(ctx, WILD_COTTON,     Feature.RANDOM_PATCH, cropPatch(ModBlocks.WILD_COTTON,     12, 4, 3));
        register(ctx, WILD_GARLIC,     Feature.RANDOM_PATCH, cropPatch(ModBlocks.WILD_GARLIC,     10, 4, 3));
        register(ctx, WILD_HORSERADISH,Feature.RANDOM_PATCH, cropPatch(ModBlocks.WILD_HORSERADISH, 8, 3, 3));
        register(ctx, WILD_LEEK,       Feature.RANDOM_PATCH, cropPatch(ModBlocks.WILD_LEEK,        8, 3, 3));
        register(ctx, WILD_NEEP,       Feature.RANDOM_PATCH, cropPatch(ModBlocks.WILD_NEEP,       10, 4, 3));
        register(ctx, WILD_OAT,        Feature.RANDOM_PATCH, cropPatch(ModBlocks.WILD_OAT,        14, 5, 3));
        register(ctx, WILD_ONION,      Feature.RANDOM_PATCH, cropPatch(ModBlocks.WILD_ONION,      12, 4, 3));
        register(ctx, WILD_PARSNIP,    Feature.RANDOM_PATCH, cropPatch(ModBlocks.WILD_PARSNIP,    12, 4, 3));
        register(ctx, WILD_PEAS,       Feature.RANDOM_PATCH, cropPatch(ModBlocks.WILD_PEAS,       10, 4, 3));
        register(ctx, WILD_PEPPERCORN, Feature.RANDOM_PATCH, cropPatch(ModBlocks.WILD_PEPPERCORN,  8, 3, 3));
        register(ctx, WILD_RYE,        Feature.RANDOM_PATCH, cropPatch(ModBlocks.WILD_RYE,        14, 5, 3));
        register(ctx, WILD_TURNIP,     Feature.RANDOM_PATCH, cropPatch(ModBlocks.WILD_TURNIP,     12, 4, 3));
        register(ctx, WILD_WHEAT,      Feature.RANDOM_PATCH, cropPatch(ModBlocks.WILD_WHEAT,      16, 5, 3));
    }

    private static RandomPatchConfiguration berryPatch(
            net.neoforged.neoforge.registries.DeferredBlock<?> block,
            int tries, int xzSpread, int ySpread) {
        var state = block.get().defaultBlockState();
        
        for (var prop : state.getProperties()) {
            if (prop instanceof IntegerProperty ip && ip.getName().equals("age")
                    && ip.getPossibleValues().contains(3)) {
                state = state.setValue(ip, 3);
                break;
            }
        }
        return new RandomPatchConfiguration(tries, xzSpread, ySpread,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(state))));
    }

    private static RandomPatchConfiguration cropPatch(
            net.neoforged.neoforge.registries.DeferredBlock<?> block,
            int tries, int xzSpread, int ySpread) {
        return new RandomPatchConfiguration(tries, xzSpread, ySpread,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(
                                BlockStateProvider.simple(block.get().defaultBlockState()))));
    }

    private static BlockStateProvider mossyBlend(BlockState base) {
        
        return new WeightedStateProvider(
                WeightedList.<BlockState>builder()
                        .add(base, 5)
                        .add(Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 3)
                        .add(Blocks.COBBLESTONE.defaultBlockState(), 2)
                        .add(Blocks.MOSS_BLOCK.defaultBlockState(), 1));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> key(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE,
                Identifier.fromNamespaceAndPath(GotMod.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(
            BootstrapContext<ConfiguredFeature<?, ?>> ctx,
            ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC config) {
        ctx.register(key, new ConfiguredFeature<>(feature, config));
    }

    private ConfiguredFeatures() {}
}