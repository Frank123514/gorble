package net.got.worldgen.biome;

import net.got.GotMod;
import net.got.init.GotModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AlterGroundDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;
import java.util.OptionalInt;

/**
 * Registers every GOT configured feature with its full in-code configuration,
 * following the same pattern as {@code ModConfiguredFeatures} in the example mod.
 *
 * <p>Wire this class into a {@link net.neoforged.neoforge.registries.datapacks.DataPackRegistriesHooks}
 * or {@code WorldgenDatapackRegistries} entry so that
 * {@link #bootstrap(BootstrapContext)} is called during data-pack bootstrap.
 * All parameters are derived directly from the generated JSON files under
 * {@code data/got/worldgen/configured_feature/}.
 */
public final class GotConfiguredFeatures {

    // ─────────────────────────────────────────────────────────────────────────
    // Resource keys
    // ─────────────────────────────────────────────────────────────────────────

    // Trees – temperate broadleaf
    public static final ResourceKey<ConfiguredFeature<?, ?>> ALDER            = key("alder");
    public static final ResourceKey<ConfiguredFeature<?, ?>> APPLE            = key("apple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ASH              = key("ash");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ASPEN            = key("aspen");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BEECH            = key("beech");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CHESTNUT         = key("chestnut");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ELM              = key("elm");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HAWTHORN         = key("hawthorn");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LINDEN           = key("linden");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE            = key("maple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILLOW           = key("willow");

    // Trees – conifers
    public static final ResourceKey<ConfiguredFeature<?, ?>> CEDAR            = key("cedar");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FIR              = key("fir");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PINE             = key("pine");
    public static final ResourceKey<ConfiguredFeature<?, ?>> REDWOOD          = key("redwood");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SENTINAL         = key("sentinal");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SOLDIER_PINE     = key("soldier_pine");

    // Trees – exotic / tropical
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_MAHOE       = key("blue_mahoe");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CINNAMON         = key("cinnamon");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CLOVE            = key("clove");
    public static final ResourceKey<ConfiguredFeature<?, ?>> EBONY            = key("ebony");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDENHEART      = key("goldenheart");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MAHOGANY         = key("mahogany");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MYRRH            = key("myrrh");

    // Trees – wetland / riverside
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLACK_COTTONWOOD = key("black_cottonwood");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COTTONWOOD       = key("cottonwood");

    // Trees – dark / special
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLACKBARK        = key("blackbark");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLOODWOOD        = key("bloodwood");
    public static final ResourceKey<ConfiguredFeature<?, ?>> IRONWOOD         = key("ironwood");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHRUB            = key("shrub");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WEIRWOOD         = key("weirwood");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WORMTREE         = key("wormtree");

    // Boulders
    public static final ResourceKey<ConfiguredFeature<?, ?>> BOULDER              = key("boulder");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GREY_GRANITE_BOULDER  = key("grey_granite_boulder");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LIMESTONE_BOULDER     = key("limestone_boulder");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SLATE_BOULDER         = key("slate_boulder");

    // Disks
    public static final ResourceKey<ConfiguredFeature<?, ?>> DISK_CLAY        = key("disk_clay");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DISK_SAND        = key("disk_sand");

    // Rock-pocket ores
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_BASALT_ROCK        = key("ore_basalt_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_FLINT_ROCK         = key("ore_flint_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GREY_GRANITE_ROCK  = key("ore_grey_granite_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_LIMESTONE_ROCK     = key("ore_limestone_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_MARBLE_ROCK        = key("ore_marble_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_RED_SANDSTONE_ROCK = key("ore_red_sandstone_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SANDSTONE_ROCK     = key("ore_sandstone_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SLATE_ROCK         = key("ore_slate_rock");

    // Berry bushes
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLACKBERRY_BUSH  = key("blackberry_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLUEBERRY_BUSH   = key("blueberry_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RASPBERRY_BUSH   = key("raspberry_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> STRAWBERRY_BUSH  = key("strawberry_bush");

    // Wild crops
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

    // ─────────────────────────────────────────────────────────────────────────
    // Bootstrap – registers every feature's configuration at world-gen time
    // ─────────────────────────────────────────────────────────────────────────

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> ctx) {

        // Rule-test shared by all rock-pocket ore features:
        // replaces any base_stone_overworld block (stone, deepslate, andesite…)
        var baseStone = new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD);

        // ══════════════════════════════════════════════════════════════════════
        // TREES
        // ══════════════════════════════════════════════════════════════════════

        // ── Temperate broadleaf ───────────────────────────────────────────────

        // alder: straight trunk(7,2,3), fancy foliage(r=2,o=4,h=4), dirt=alder_wood
        register(ctx, ALDER, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(GotModBlocks.ALDER_LOG.get()),
                new StraightTrunkPlacer(7, 2, 3),
                BlockStateProvider.simple(GotModBlocks.ALDER_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                new TwoLayersFeatureSize(1, 0, 1))
                .dirt(BlockStateProvider.simple(GotModBlocks.ALDER_WOOD.get()))
                .ignoreVines()
                .build());

        // apple: straight trunk(4,2,0), blob foliage(r=2,o=0,h=3)
        register(ctx, APPLE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(GotModBlocks.APPLE_LOG.get()),
                new StraightTrunkPlacer(4, 2, 0),
                BlockStateProvider.simple(GotModBlocks.APPLE_LEAVES.get().defaultBlockState()),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1))
                .ignoreVines()
                .build());

        // ash: fancy trunk(7,2,3), cherry foliage(r=4,o=3,h=5)
        register(ctx, ASH, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(GotModBlocks.ASH_LOG.get()),
                new FancyTrunkPlacer(7, 2, 3),
                BlockStateProvider.simple(GotModBlocks.ASH_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new CherryFoliagePlacer(ConstantInt.of(4), ConstantInt.of(3), ConstantInt.of(5),
                        0.25f, 0.25f, 0.5f, 0.8f),
                new TwoLayersFeatureSize(1, 0, 2))
                .ignoreVines()
                .build());

        // aspen: straight trunk(7,2,2), spruce foliage(r=2,o=2,trunk_height=2)
        register(ctx, ASPEN, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(GotModBlocks.ASPEN_LOG.get()),
                new StraightTrunkPlacer(7, 2, 2),
                BlockStateProvider.simple(GotModBlocks.ASPEN_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new SpruceFoliagePlacer(ConstantInt.of(2), ConstantInt.of(2), ConstantInt.of(2)),
                new TwoLayersFeatureSize(1, 1, 1))
                .build());

        // beech: fancy trunk(7,2,3), cherry foliage(r=4,o=3,h=5)
        register(ctx, BEECH, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(GotModBlocks.BEECH_LOG.get()),
                new FancyTrunkPlacer(7, 2, 3),
                BlockStateProvider.simple(GotModBlocks.BEECH_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new CherryFoliagePlacer(ConstantInt.of(4), ConstantInt.of(3), ConstantInt.of(5),
                        0.25f, 0.25f, 0.5f, 0.8f),
                new TwoLayersFeatureSize(1, 0, 2))
                .ignoreVines()
                .build());

        // chestnut: fancy trunk(8,2,3), fancy foliage(r=2,o=4,h=4), dirt=chestnut_wood, minClipped=4
        register(ctx, CHESTNUT, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(GotModBlocks.CHESTNUT_LOG.get()),
                new FancyTrunkPlacer(8, 2, 3),
                BlockStateProvider.simple(GotModBlocks.CHESTNUT_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))
                .dirt(BlockStateProvider.simple(GotModBlocks.CHESTNUT_WOOD.get()))
                .ignoreVines()
                .build());

        // elm: fancy trunk(7,2,3), fancy foliage(r=2,o=4,h=4)
        register(ctx, ELM, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(GotModBlocks.ELM_LOG.get()),
                new FancyTrunkPlacer(7, 2, 3),
                BlockStateProvider.simple(GotModBlocks.ELM_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                new TwoLayersFeatureSize(1, 0, 2))
                .ignoreVines()
                .build());

        // hawthorn: fancy trunk(7,2,3), cherry foliage(r=4,o=3,h=5)
        register(ctx, HAWTHORN, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(GotModBlocks.HAWTHORN_LOG.get()),
                new FancyTrunkPlacer(7, 2, 3),
                BlockStateProvider.simple(GotModBlocks.HAWTHORN_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new CherryFoliagePlacer(ConstantInt.of(4), ConstantInt.of(3), ConstantInt.of(5),
                        0.25f, 0.25f, 0.5f, 0.8f),
                new TwoLayersFeatureSize(1, 0, 2))
                .ignoreVines()
                .build());

        // linden: straight trunk(8,2,2), blob foliage(r=3,o=0,h=3)
        register(ctx, LINDEN, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(GotModBlocks.LINDEN_LOG.get()),
                new StraightTrunkPlacer(8, 2, 2),
                BlockStateProvider.simple(GotModBlocks.LINDEN_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 1, 1))
                .ignoreVines()
                .build());

        // maple: straight trunk(4,2,0), blob foliage(r=2,o=0,h=3)
        register(ctx, MAPLE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(GotModBlocks.MAPLE_LOG.get()),
                new StraightTrunkPlacer(4, 2, 0),
                BlockStateProvider.simple(GotModBlocks.MAPLE_LEAVES.get().defaultBlockState()),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1))
                .ignoreVines()
                .build());

        // willow: fancy trunk(7,2,3), cherry foliage(r=4,o=3,h=5) + leave_vine(15%)
        register(ctx, WILLOW, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(GotModBlocks.WILLOW_LOG.get()),
                new FancyTrunkPlacer(7, 2, 3),
                BlockStateProvider.simple(GotModBlocks.WILLOW_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new CherryFoliagePlacer(ConstantInt.of(4), ConstantInt.of(3), ConstantInt.of(5),
                        0.25f, 0.25f, 0.5f, 0.8f),
                new TwoLayersFeatureSize(1, 0, 2))
                .ignoreVines()
                .decorators(List.of(new LeaveVineDecorator(0.15f)))
                .build());

        // ── Conifers ──────────────────────────────────────────────────────────

        // cedar: straight trunk(10,3,2), spruce foliage(r=2-3,o=0-2,trunk_height=4-5)
        register(ctx, CEDAR, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(GotModBlocks.CEDAR_LOG.get()),
                new StraightTrunkPlacer(10, 3, 2),
                BlockStateProvider.simple(GotModBlocks.CEDAR_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new SpruceFoliagePlacer(UniformInt.of(2, 3), UniformInt.of(0, 2), UniformInt.of(4, 5)),
                new TwoLayersFeatureSize(2, 0, 2))
                .ignoreVines()
                .build());

        // fir: straight trunk(7,2,1), spruce foliage(r=2-3,o=0-2,trunk_height=4-5), dirt=fir_wood
        register(ctx, FIR, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(GotModBlocks.FIR_LOG.get()),
                new StraightTrunkPlacer(7, 2, 1),
                BlockStateProvider.simple(GotModBlocks.FIR_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new SpruceFoliagePlacer(UniformInt.of(2, 3), UniformInt.of(0, 2), UniformInt.of(4, 5)),
                new TwoLayersFeatureSize(2, 0, 2))
                .dirt(BlockStateProvider.simple(GotModBlocks.FIR_WOOD.get()))
                .ignoreVines()
                .build());

        // pine: straight trunk(6,4,0), pine foliage(r=1,o=1,h=3-4)
        register(ctx, PINE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(GotModBlocks.PINE_LOG.get()),
                new StraightTrunkPlacer(6, 4, 0),
                BlockStateProvider.simple(GotModBlocks.PINE_LEAVES.get().defaultBlockState()),
                new PineFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1), UniformInt.of(3, 4)),
                new TwoLayersFeatureSize(2, 0, 2))
                .ignoreVines()
                .build());

        // redwood: giant trunk(13,2,14), mega_pine foliage(r=0,o=0,crown=3-7) + alter_ground podzol
        register(ctx, REDWOOD, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(GotModBlocks.REDWOOD_LOG.get()),
                new GiantTrunkPlacer(13, 2, 14),
                BlockStateProvider.simple(GotModBlocks.REDWOOD_LEAVES.get().defaultBlockState()),
                new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(3, 7)),
                new TwoLayersFeatureSize(1, 1, 2))
                .ignoreVines()
                .decorators(List.of(new AlterGroundDecorator(
                        BlockStateProvider.simple(Blocks.PODZOL.defaultBlockState()))))
                .build());

        // sentinal: straight trunk(12,3,3), spruce foliage(r=3,o=2,trunk_height=3)
        register(ctx, SENTINAL, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(GotModBlocks.SENTINAL_LOG.get()),
                new StraightTrunkPlacer(12, 3, 3),
                BlockStateProvider.simple(GotModBlocks.SENTINAL_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new SpruceFoliagePlacer(ConstantInt.of(3), ConstantInt.of(2), ConstantInt.of(3)),
                new TwoLayersFeatureSize(1, 0, 1))
                .ignoreVines()
                .build());

        // soldier_pine: straight trunk(8,2,2), spruce foliage(r=2-3,o=0-2,trunk_height=4-5), dirt=soldier_pine_wood
        register(ctx, SOLDIER_PINE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(GotModBlocks.SOLDIER_PINE_LOG.get()),
                new StraightTrunkPlacer(8, 2, 2),
                BlockStateProvider.simple(GotModBlocks.SOLDIER_PINE_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new SpruceFoliagePlacer(UniformInt.of(2, 3), UniformInt.of(0, 2), UniformInt.of(4, 5)),
                new TwoLayersFeatureSize(2, 0, 2))
                .dirt(BlockStateProvider.simple(GotModBlocks.SOLDIER_PINE_WOOD.get()))
                .ignoreVines()
                .build());

        // ── Exotic / tropical ─────────────────────────────────────────────────

        // blue_mahoe: straight trunk(7,2,2), spruce foliage(r=2,o=2,trunk_height=2)
        register(ctx, BLUE_MAHOE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(GotModBlocks.BLUE_MAHOE_LOG.get()),
                new StraightTrunkPlacer(7, 2, 2),
                BlockStateProvider.simple(GotModBlocks.BLUE_MAHOE_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new SpruceFoliagePlacer(ConstantInt.of(2), ConstantInt.of(2), ConstantInt.of(2)),
                new TwoLayersFeatureSize(1, 1, 1))
                .ignoreVines()
                .build());

        // cinnamon: straight trunk(5,2,6), blob foliage(r=2,o=0,h=3) + beehive(100%)
        register(ctx, CINNAMON, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(GotModBlocks.CINNAMON_LOG.get()),
                new StraightTrunkPlacer(5, 2, 6),
                BlockStateProvider.simple(GotModBlocks.CINNAMON_LEAVES.get().defaultBlockState()),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1))
                .ignoreVines()
                .decorators(List.of(new BeehiveDecorator(1.0f)))
                .build());

        // clove: straight trunk(7,2,2), spruce foliage(r=2,o=2,trunk_height=2)
        register(ctx, CLOVE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(GotModBlocks.CLOVE_LOG.get()),
                new StraightTrunkPlacer(7, 2, 2),
                BlockStateProvider.simple(GotModBlocks.CLOVE_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new SpruceFoliagePlacer(ConstantInt.of(2), ConstantInt.of(2), ConstantInt.of(2)),
                new TwoLayersFeatureSize(1, 1, 1))
                .ignoreVines()
                .build());

        // ebony: fancy trunk(8,2,3), fancy foliage(r=2,o=4,h=4), dirt=ebony_wood, minClipped=4
        register(ctx, EBONY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(GotModBlocks.EBONY_LOG.get()),
                new FancyTrunkPlacer(8, 2, 3),
                BlockStateProvider.simple(GotModBlocks.EBONY_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))
                .dirt(BlockStateProvider.simple(GotModBlocks.EBONY_WOOD.get()))
                .ignoreVines()
                .build());

        // goldenheart: fancy trunk(7,2,3), fancy foliage(r=2,o=4,h=4)
        register(ctx, GOLDENHEART, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(GotModBlocks.GOLDENHEART_LOG.get()),
                new FancyTrunkPlacer(7, 2, 3),
                BlockStateProvider.simple(GotModBlocks.GOLDENHEART_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                new TwoLayersFeatureSize(1, 0, 2))
                .ignoreVines()
                .build());

        // mahogany: straight trunk(10,2,2), jungle foliage(r=2,o=0,h=3)
        register(ctx, MAHOGANY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(GotModBlocks.MAHOGANY_LOG.get()),
                new StraightTrunkPlacer(10, 2, 2),
                BlockStateProvider.simple(GotModBlocks.MAHOGANY_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 1, 2))
                .ignoreVines()
                .build());

        // myrrh: forking trunk(5,2,2), acacia foliage(r=2,o=0)
        register(ctx, MYRRH, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(GotModBlocks.MYRRH_LOG.get()),
                new ForkingTrunkPlacer(5, 2, 2),
                BlockStateProvider.simple(GotModBlocks.MYRRH_LEAVES.get().defaultBlockState()),
                new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                new TwoLayersFeatureSize(1, 0, 2))
                .ignoreVines()
                .build());

        // ── Wetland / riverside ───────────────────────────────────────────────

        // black_cottonwood: fancy trunk(7,2,3), cherry foliage(r=4,o=3,h=5)
        register(ctx, BLACK_COTTONWOOD, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(GotModBlocks.BLACK_COTTONWOOD_LOG.get()),
                new FancyTrunkPlacer(7, 2, 3),
                BlockStateProvider.simple(GotModBlocks.BLACK_COTTONWOOD_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new CherryFoliagePlacer(ConstantInt.of(4), ConstantInt.of(3), ConstantInt.of(5),
                        0.25f, 0.25f, 0.5f, 0.8f),
                new TwoLayersFeatureSize(1, 0, 2))
                .ignoreVines()
                .build());

        // cottonwood: fancy trunk(7,2,3), cherry foliage(r=4,o=3,h=5)
        register(ctx, COTTONWOOD, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(GotModBlocks.COTTONWOOD_LOG.get()),
                new FancyTrunkPlacer(7, 2, 3),
                BlockStateProvider.simple(GotModBlocks.COTTONWOOD_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new CherryFoliagePlacer(ConstantInt.of(4), ConstantInt.of(3), ConstantInt.of(5),
                        0.25f, 0.25f, 0.5f, 0.8f),
                new TwoLayersFeatureSize(1, 0, 2))
                .ignoreVines()
                .build());

        // ── Dark / special ────────────────────────────────────────────────────

        // blackbark: straight trunk(7,2,2), spruce foliage(r=2,o=2,trunk_height=2)
        register(ctx, BLACKBARK, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(GotModBlocks.BLACKBARK_LOG.get()),
                new StraightTrunkPlacer(7, 2, 2),
                BlockStateProvider.simple(GotModBlocks.BLACKBARK_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new SpruceFoliagePlacer(ConstantInt.of(2), ConstantInt.of(2), ConstantInt.of(2)),
                new TwoLayersFeatureSize(1, 1, 1))
                .ignoreVines()
                .build());

        // bloodwood: forking trunk(5,2,2), acacia foliage(r=2,o=0)
        register(ctx, BLOODWOOD, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(GotModBlocks.BLOODWOOD_LOG.get()),
                new ForkingTrunkPlacer(5, 2, 2),
                BlockStateProvider.simple(GotModBlocks.BLOODWOOD_LEAVES.get().defaultBlockState()),
                new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                new TwoLayersFeatureSize(1, 0, 2))
                .ignoreVines()
                .build());

        // ironwood: fancy trunk(8,2,3), fancy foliage(r=2,o=4,h=4), dirt=ironwood_wood, minClipped=4
        register(ctx, IRONWOOD, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(GotModBlocks.IRONWOOD_LOG.get()),
                new FancyTrunkPlacer(8, 2, 3),
                BlockStateProvider.simple(GotModBlocks.IRONWOOD_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))
                .dirt(BlockStateProvider.simple(GotModBlocks.IRONWOOD_WOOD.get()))
                .ignoreVines()
                .build());

        // shrub: straight trunk(1,0,0), bush foliage(r=2,o=1,h=2), vanilla oak blocks
        register(ctx, SHRUB, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.OAK_LOG),
                new StraightTrunkPlacer(1, 0, 0),
                BlockStateProvider.simple(Blocks.OAK_LEAVES.defaultBlockState()),
                new BushFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), 2),
                new TwoLayersFeatureSize(0, 0, 0))
                .dirt(BlockStateProvider.simple(Blocks.OAK_WOOD))
                .build());

        // weirwood: fancy trunk(8,2,3), fancy foliage(r=2,o=4,h=4), dirt=weirwood_wood
        //           TwoLayers(limit=0, lower=2, middle=1, upper=0) — the JSON uses
        //           lower_size=2 which maps to the "lower" param in the 3-arg constructor
        register(ctx, WEIRWOOD, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(GotModBlocks.WEIRWOOD_LOG.get()),
                new FancyTrunkPlacer(8, 2, 3),
                BlockStateProvider.simple(GotModBlocks.WEIRWOOD_LEAVES.get().defaultBlockState()),
                new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                new TwoLayersFeatureSize(0, 2, 0))
                .dirt(BlockStateProvider.simple(GotModBlocks.WEIRWOOD_WOOD.get()))
                .ignoreVines()
                .build());

        // wormtree: straight trunk(8,2,2), blob foliage(r=2,o=0,h=2)
        register(ctx, WORMTREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(GotModBlocks.WORMTREE_LOG.get()),
                new StraightTrunkPlacer(8, 2, 2),
                BlockStateProvider.simple(GotModBlocks.WORMTREE_LEAVES.get().defaultBlockState()
                        .setValue(LeavesBlock.PERSISTENT, true)),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 2),
                new TwoLayersFeatureSize(1, 1, 1))
                .ignoreVines()
                .build());

        // ══════════════════════════════════════════════════════════════════════
        // BOULDERS  (forest_rock = one block placed on the surface)
        // ══════════════════════════════════════════════════════════════════════

        register(ctx, BOULDER, Feature.FOREST_ROCK,
                new BlockStateConfiguration(Blocks.STONE.defaultBlockState()));

        register(ctx, GREY_GRANITE_BOULDER, Feature.FOREST_ROCK,
                new BlockStateConfiguration(GotModBlocks.GREY_GRANITE_ROCK.get().defaultBlockState()));

        register(ctx, LIMESTONE_BOULDER, Feature.FOREST_ROCK,
                new BlockStateConfiguration(GotModBlocks.LIMESTONE_ROCK.get().defaultBlockState()));

        register(ctx, SLATE_BOULDER, Feature.FOREST_ROCK,
                new BlockStateConfiguration(GotModBlocks.SLATE_ROCK.get().defaultBlockState()));

        // ══════════════════════════════════════════════════════════════════════
        // DISKS  (surface sediment patches near water)
        // ══════════════════════════════════════════════════════════════════════

        // disk_clay: clay, radius 2-6, half_height 2, replaces dirt/grass_block
        register(ctx, DISK_CLAY, Feature.DISK, new DiskConfiguration(
                RuleBasedBlockStateProvider.simple(Blocks.CLAY),
                BlockPredicate.matchesBlocks(Blocks.DIRT, Blocks.GRASS_BLOCK),
                UniformInt.of(2, 6),
                2));

        // disk_sand: sand, radius 2-3, half_height 1, replaces dirt/sand
        register(ctx, DISK_SAND, Feature.DISK, new DiskConfiguration(
                RuleBasedBlockStateProvider.simple(Blocks.SAND),
                BlockPredicate.matchesBlocks(Blocks.DIRT, Blocks.SAND),
                UniformInt.of(2, 3),
                1));

        // ══════════════════════════════════════════════════════════════════════
        // ROCK-POCKET ORES  (vein size 64, replaces base_stone_overworld)
        // ══════════════════════════════════════════════════════════════════════

        register(ctx, ORE_BASALT_ROCK, Feature.ORE, new OreConfiguration(
                List.of(OreConfiguration.target(baseStone,
                        GotModBlocks.BASALT_ROCK.get().defaultBlockState())), 64));

        register(ctx, ORE_FLINT_ROCK, Feature.ORE, new OreConfiguration(
                List.of(OreConfiguration.target(baseStone,
                        GotModBlocks.FLINT_ROCK.get().defaultBlockState())), 64));

        register(ctx, ORE_GREY_GRANITE_ROCK, Feature.ORE, new OreConfiguration(
                List.of(OreConfiguration.target(baseStone,
                        GotModBlocks.GREY_GRANITE_ROCK.get().defaultBlockState())), 64));

        register(ctx, ORE_LIMESTONE_ROCK, Feature.ORE, new OreConfiguration(
                List.of(OreConfiguration.target(baseStone,
                        GotModBlocks.LIMESTONE_ROCK.get().defaultBlockState())), 64));

        register(ctx, ORE_MARBLE_ROCK, Feature.ORE, new OreConfiguration(
                List.of(OreConfiguration.target(baseStone,
                        GotModBlocks.MARBLE_ROCK.get().defaultBlockState())), 64));

        register(ctx, ORE_RED_SANDSTONE_ROCK, Feature.ORE, new OreConfiguration(
                List.of(OreConfiguration.target(baseStone,
                        GotModBlocks.RED_SANDSTONE_ROCK.get().defaultBlockState())), 64));

        register(ctx, ORE_SANDSTONE_ROCK, Feature.ORE, new OreConfiguration(
                List.of(OreConfiguration.target(baseStone,
                        GotModBlocks.SANDSTONE_ROCK.get().defaultBlockState())), 64));

        register(ctx, ORE_SLATE_ROCK, Feature.ORE, new OreConfiguration(
                List.of(OreConfiguration.target(baseStone,
                        GotModBlocks.SLATE_ROCK.get().defaultBlockState())), 64));

        // ══════════════════════════════════════════════════════════════════════
        // BERRY BUSHES  (random_patch, age=3 so they spawn harvestable)
        // ══════════════════════════════════════════════════════════════════════

        register(ctx, BLACKBERRY_BUSH, Feature.RANDOM_PATCH, berryPatch(GotModBlocks.BLACKBERRY_BUSH, 10, 4, 3));
        register(ctx, BLUEBERRY_BUSH,  Feature.RANDOM_PATCH, berryPatch(GotModBlocks.BLUEBERRY_BUSH,  10, 4, 3));
        register(ctx, RASPBERRY_BUSH,  Feature.RANDOM_PATCH, berryPatch(GotModBlocks.RASPBERRY_BUSH,  10, 4, 3));
        register(ctx, STRAWBERRY_BUSH, Feature.RANDOM_PATCH, berryPatch(GotModBlocks.STRAWBERRY_BUSH, 12, 5, 3));

        // ══════════════════════════════════════════════════════════════════════
        // WILD CROPS  (random_patch, simple block, no age)
        // ══════════════════════════════════════════════════════════════════════

        register(ctx, WILD_BARLEY,     Feature.RANDOM_PATCH, cropPatch(GotModBlocks.WILD_BARLEY,     16, 5, 3));
        register(ctx, WILD_BEETROOT,   Feature.RANDOM_PATCH, cropPatch(GotModBlocks.WILD_BEETROOT,   12, 4, 3));
        register(ctx, WILD_CABBAGE,    Feature.RANDOM_PATCH, cropPatch(GotModBlocks.WILD_CABBAGE,     8, 4, 3));
        register(ctx, WILD_CARROT,     Feature.RANDOM_PATCH, cropPatch(GotModBlocks.WILD_CARROT,     14, 5, 3));
        register(ctx, WILD_COTTON,     Feature.RANDOM_PATCH, cropPatch(GotModBlocks.WILD_COTTON,     12, 4, 3));
        register(ctx, WILD_GARLIC,     Feature.RANDOM_PATCH, cropPatch(GotModBlocks.WILD_GARLIC,     10, 4, 3));
        register(ctx, WILD_HORSERADISH,Feature.RANDOM_PATCH, cropPatch(GotModBlocks.WILD_HORSERADISH, 8, 3, 3));
        register(ctx, WILD_LEEK,       Feature.RANDOM_PATCH, cropPatch(GotModBlocks.WILD_LEEK,        8, 3, 3));
        register(ctx, WILD_NEEP,       Feature.RANDOM_PATCH, cropPatch(GotModBlocks.WILD_NEEP,       10, 4, 3));
        register(ctx, WILD_OAT,        Feature.RANDOM_PATCH, cropPatch(GotModBlocks.WILD_OAT,        14, 5, 3));
        register(ctx, WILD_ONION,      Feature.RANDOM_PATCH, cropPatch(GotModBlocks.WILD_ONION,      12, 4, 3));
        register(ctx, WILD_PARSNIP,    Feature.RANDOM_PATCH, cropPatch(GotModBlocks.WILD_PARSNIP,    12, 4, 3));
        register(ctx, WILD_PEAS,       Feature.RANDOM_PATCH, cropPatch(GotModBlocks.WILD_PEAS,       10, 4, 3));
        register(ctx, WILD_PEPPERCORN, Feature.RANDOM_PATCH, cropPatch(GotModBlocks.WILD_PEPPERCORN,  8, 3, 3));
        register(ctx, WILD_RYE,        Feature.RANDOM_PATCH, cropPatch(GotModBlocks.WILD_RYE,        14, 5, 3));
        register(ctx, WILD_TURNIP,     Feature.RANDOM_PATCH, cropPatch(GotModBlocks.WILD_TURNIP,     12, 4, 3));
        register(ctx, WILD_WHEAT,      Feature.RANDOM_PATCH, cropPatch(GotModBlocks.WILD_WHEAT,      16, 5, 3));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Helpers
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Berry bush patch — spawns the bush at {@code age=3} (fully ripe) and only
     * places into air, matching the JSON's block_predicate_filter.
     */
    private static RandomPatchConfiguration berryPatch(
            net.neoforged.neoforge.registries.DeferredBlock<?> block,
            int tries, int xzSpread, int ySpread) {
        var state = block.get().defaultBlockState();
        // Set age=3 if available
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

    /** Wild crop patch — plain simple-block placement, no age property. */
    private static RandomPatchConfiguration cropPatch(
            net.neoforged.neoforge.registries.DeferredBlock<?> block,
            int tries, int xzSpread, int ySpread) {
        return new RandomPatchConfiguration(tries, xzSpread, ySpread,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(
                                BlockStateProvider.simple(block.get().defaultBlockState()))));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> key(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE,
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(
            BootstrapContext<ConfiguredFeature<?, ?>> ctx,
            ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC config) {
        ctx.register(key, new ConfiguredFeature<>(feature, config));
    }

    private GotConfiguredFeatures() {}
}