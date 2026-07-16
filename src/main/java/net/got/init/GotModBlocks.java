package net.got.init;

import net.got.block.*;
import net.got.block.OvenBlock;
import net.got.block.GotSaplingBlock;
import net.got.block.GotStrippedLogBlock;
import net.got.block.RegionalRockBlock;
import net.got.block.RegionalRockPillarBlock;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.effect.MobEffects;
import net.got.block.GotSeedCropBlock;
import net.got.block.GotShortSeedCropBlock;
import net.got.block.GotProduceCropBlock;
import net.got.block.GotBerryBushBlock;
import net.minecraft.world.item.Item;
import net.got.block.GotStandingSignBlock;
import net.got.block.GotWallSignBlock;
import net.got.block.ReedsBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

import net.got.GotMod;
import net.got.init.GotTreeGrowers;
import net.got.init.GotModBlockEntities;
import net.got.init.GotWoodTypes;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

import static net.minecraft.world.item.Items.registerBlock;

public class GotModBlocks {
    public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(GotMod.MODID);

    /** Maps wood-type name (e.g. "weirwood") → its log DeferredBlock, for stripping lookup. */
    public static final Map<String, DeferredBlock<Block>> LOGS = new LinkedHashMap<>();
    /** Maps wood-type name (e.g. "weirwood") → its stripped log DeferredBlock, for stripping lookup. */
    public static final Map<String, DeferredBlock<Block>> STRIPPED_LOGS = new LinkedHashMap<>();
    /** Maps wood-type name (e.g. "weirwood") → its wood (bark-all-sides) DeferredBlock, for stripping lookup. */
    public static final Map<String, DeferredBlock<Block>> WOODS = new LinkedHashMap<>();
    /** Maps wood-type name (e.g. "weirwood") → its stripped wood DeferredBlock, for stripping lookup. */
    public static final Map<String, DeferredBlock<Block>> STRIPPED_WOODS = new LinkedHashMap<>();
    /** Maps wood-type name (e.g. "weirwood") → its branch DeferredBlock, for stripping lookup. */
    public static final Map<String, DeferredBlock<Block>> BRANCHES = new LinkedHashMap<>();
    /** Maps wood-type name (e.g. "weirwood") → its stripped branch DeferredBlock, for stripping lookup. */
    public static final Map<String, DeferredBlock<Block>> STRIPPED_BRANCHES = new LinkedHashMap<>();
    public static final DeferredBlock<Block> WEIRWOOD_LOG = logBlock("weirwood_log", WeirwoodLogBlock::new);
    public static final DeferredBlock<Block> WEIRWOOD_WOOD = logBlock("weirwood_wood", WeirwoodWoodBlock::new);
    public static final DeferredBlock<Block> WEIRWOOD_PLANKS = woodBlock("weirwood_planks", WeirwoodPlanksBlock::new);
    public static final DeferredBlock<Block> WEIRWOOD_LEAVES = woodBlock("weirwood_leaves", WeirwoodLeavesBlock::new);
    public static final DeferredBlock<Block> WEIRWOOD_STAIRS = woodBlock("weirwood_stairs", WeirwoodStairsBlock::new);
    public static final DeferredBlock<Block> WEIRWOOD_SLAB = woodBlock("weirwood_slab", WeirwoodSlabBlock::new);
    public static final DeferredBlock<Block> WEIRWOOD_FENCE = woodBlock("weirwood_fence", WeirwoodFenceBlock::new);
    public static final DeferredBlock<Block> WEIRWOOD_FENCE_GATE = woodBlock("weirwood_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.WEIRWOOD, p));
    public static final DeferredBlock<Block> WEIRWOOD_PRESSURE_PLATE = woodBlock("weirwood_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> WEIRWOOD_BUTTON = woodBlock("weirwood_button", p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> ASPEN_LOG = logBlock("aspen_log", AspenLogBlock::new);
    public static final DeferredBlock<Block> ASPEN_WOOD = logBlock("aspen_wood", AspenWoodBlock::new);
    public static final DeferredBlock<Block> ASPEN_PLANKS = woodBlock("aspen_planks", AspenPlanksBlock::new);
    public static final DeferredBlock<Block> ASPEN_LEAVES = woodBlock("aspen_leaves", AspenLeavesBlock::new);
    public static final DeferredBlock<Block> ASPEN_STAIRS = woodBlock("aspen_stairs", AspenStairsBlock::new);
    public static final DeferredBlock<Block> ASPEN_SLAB = woodBlock("aspen_slab", AspenSlabBlock::new);
    public static final DeferredBlock<Block> ASPEN_FENCE = woodBlock("aspen_fence", AspenFenceBlock::new);
    public static final DeferredBlock<Block> ASPEN_FENCE_GATE = woodBlock("aspen_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.ASPEN, p));
    public static final DeferredBlock<Block> ASPEN_PRESSURE_PLATE = woodBlock("aspen_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> ASPEN_BUTTON = woodBlock("aspen_button", p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> ALDER_LOG = logBlock("alder_log", AlderLogBlock::new);
    public static final DeferredBlock<Block> ALDER_WOOD = logBlock("alder_wood", AlderWoodBlock::new);
    public static final DeferredBlock<Block> ALDER_PLANKS = woodBlock("alder_planks", AlderPlanksBlock::new);
    public static final DeferredBlock<Block> ALDER_LEAVES = woodBlock("alder_leaves", AlderLeavesBlock::new);
    public static final DeferredBlock<Block> ALDER_STAIRS = woodBlock("alder_stairs", AlderStairsBlock::new);
    public static final DeferredBlock<Block> ALDER_SLAB = woodBlock("alder_slab", AlderSlabBlock::new);
    public static final DeferredBlock<Block> ALDER_FENCE = woodBlock("alder_fence", AlderFenceBlock::new);
    public static final DeferredBlock<Block> ALDER_FENCE_GATE = woodBlock("alder_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.ALDER, p));
    public static final DeferredBlock<Block> ALDER_PRESSURE_PLATE = woodBlock("alder_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> ALDER_BUTTON = woodBlock("alder_button", p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> PINE_LOG = logBlock("pine_log", PineLogBlock::new);
    public static final DeferredBlock<Block> PINE_WOOD = logBlock("pine_wood", PineWoodBlock::new);
    public static final DeferredBlock<Block> PINE_PLANKS = woodBlock("pine_planks", PinePlanksBlock::new);
    public static final DeferredBlock<Block> PINE_LEAVES = woodBlock("pine_leaves", PineLeavesBlock::new);
    public static final DeferredBlock<Block> PINE_STAIRS = woodBlock("pine_stairs", PineStairsBlock::new);
    public static final DeferredBlock<Block> PINE_SLAB = woodBlock("pine_slab", PineSlabBlock::new);
    public static final DeferredBlock<Block> PINE_FENCE = woodBlock("pine_fence", PineFenceBlock::new);
    public static final DeferredBlock<Block> PINE_FENCE_GATE = woodBlock("pine_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.PINE, p));
    public static final DeferredBlock<Block> PINE_PRESSURE_PLATE = woodBlock("pine_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> PINE_BUTTON = woodBlock("pine_button", p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> FIR_LOG = logBlock("fir_log", FirLogBlock::new);
    public static final DeferredBlock<Block> FIR_WOOD = logBlock("fir_wood", FirWoodBlock::new);
    public static final DeferredBlock<Block> FIR_PLANKS = woodBlock("fir_planks", FirPlanksBlock::new);
    public static final DeferredBlock<Block> FIR_LEAVES = woodBlock("fir_leaves", FirLeavesBlock::new);
    public static final DeferredBlock<Block> FIR_STAIRS = woodBlock("fir_stairs", FirStairsBlock::new);
    public static final DeferredBlock<Block> FIR_SLAB = woodBlock("fir_slab", FirSlabBlock::new);
    public static final DeferredBlock<Block> FIR_FENCE = woodBlock("fir_fence", FirFenceBlock::new);
    public static final DeferredBlock<Block> FIR_FENCE_GATE = woodBlock("fir_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.FIR, p));
    public static final DeferredBlock<Block> FIR_PRESSURE_PLATE = woodBlock("fir_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> FIR_BUTTON = woodBlock("fir_button", p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> SENTINAL_LOG = logBlock("sentinal_log", SentinalLogBlock::new);
    public static final DeferredBlock<Block> SENTINAL_WOOD = logBlock("sentinal_wood", SentinalWoodBlock::new);
    public static final DeferredBlock<Block> SENTINAL_PLANKS = woodBlock("sentinal_planks", SentinalPlanksBlock::new);
    public static final DeferredBlock<Block> SENTINAL_LEAVES = woodBlock("sentinal_leaves", SentinalLeavesBlock::new);
    public static final DeferredBlock<Block> SENTINAL_STAIRS = woodBlock("sentinal_stairs", SentinalStairsBlock::new);
    public static final DeferredBlock<Block> SENTINAL_SLAB = woodBlock("sentinal_slab", SentinalSlabBlock::new);
    public static final DeferredBlock<Block> SENTINAL_FENCE = woodBlock("sentinal_fence", SentinalFenceBlock::new);
    public static final DeferredBlock<Block> SENTINAL_FENCE_GATE = woodBlock("sentinal_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.SENTINAL, p));
    public static final DeferredBlock<Block> SENTINAL_PRESSURE_PLATE = woodBlock("sentinal_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> SENTINAL_BUTTON = woodBlock("sentinal_button", p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> IRONWOOD_LOG = logBlock("ironwood_log", IronwoodLogBlock::new);
    public static final DeferredBlock<Block> IRONWOOD_WOOD = logBlock("ironwood_wood", IronwoodWoodBlock::new);
    public static final DeferredBlock<Block> IRONWOOD_PLANKS = woodBlock("ironwood_planks", IronwoodPlanksBlock::new);
    public static final DeferredBlock<Block> IRONWOOD_LEAVES = woodBlock("ironwood_leaves", IronwoodLeavesBlock::new);
    public static final DeferredBlock<Block> IRONWOOD_STAIRS = woodBlock("ironwood_stairs", IronwoodStairsBlock::new);
    public static final DeferredBlock<Block> IRONWOOD_SLAB = woodBlock("ironwood_slab", IronwoodSlabBlock::new);
    public static final DeferredBlock<Block> IRONWOOD_FENCE = woodBlock("ironwood_fence", IronwoodFenceBlock::new);
    public static final DeferredBlock<Block> IRONWOOD_FENCE_GATE = woodBlock("ironwood_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.IRONWOOD, p));
    public static final DeferredBlock<Block> IRONWOOD_PRESSURE_PLATE = woodBlock("ironwood_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> IRONWOOD_BUTTON = woodBlock("ironwood_button", p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> BEECH_LOG = logBlock("beech_log", BeechLogBlock::new);
    public static final DeferredBlock<Block> BEECH_WOOD = logBlock("beech_wood", BeechWoodBlock::new);
    public static final DeferredBlock<Block> BEECH_PLANKS = woodBlock("beech_planks", BeechPlanksBlock::new);
    public static final DeferredBlock<Block> BEECH_LEAVES = woodBlock("beech_leaves", BeechLeavesBlock::new);
    public static final DeferredBlock<Block> BEECH_STAIRS = woodBlock("beech_stairs", BeechStairsBlock::new);
    public static final DeferredBlock<Block> BEECH_SLAB = woodBlock("beech_slab", BeechSlabBlock::new);
    public static final DeferredBlock<Block> BEECH_FENCE = woodBlock("beech_fence", BeechFenceBlock::new);
    public static final DeferredBlock<Block> BEECH_FENCE_GATE = woodBlock("beech_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.BEECH, p));
    public static final DeferredBlock<Block> BEECH_PRESSURE_PLATE = woodBlock("beech_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> BEECH_BUTTON = woodBlock("beech_button", p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> SOLDIER_PINE_LOG = logBlock("soldier_pine_log", SoldierPineLogBlock::new);
    public static final DeferredBlock<Block> SOLDIER_PINE_WOOD = logBlock("soldier_pine_wood", SoldierPineWoodBlock::new);
    public static final DeferredBlock<Block> SOLDIER_PINE_PLANKS = woodBlock("soldier_pine_planks", SoldierPinePlanksBlock::new);
    public static final DeferredBlock<Block> SOLDIER_PINE_LEAVES = woodBlock("soldier_pine_leaves", SoldierPineLeavesBlock::new);
    public static final DeferredBlock<Block> SOLDIER_PINE_STAIRS = woodBlock("soldier_pine_stairs", SoldierPineStairsBlock::new);
    public static final DeferredBlock<Block> SOLDIER_PINE_SLAB = woodBlock("soldier_pine_slab", SoldierPineSlabBlock::new);
    public static final DeferredBlock<Block> SOLDIER_PINE_FENCE = woodBlock("soldier_pine_fence", SoldierPineFenceBlock::new);
    public static final DeferredBlock<Block> SOLDIER_PINE_FENCE_GATE = woodBlock("soldier_pine_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.PINE, p));
    public static final DeferredBlock<Block> SOLDIER_PINE_PRESSURE_PLATE = woodBlock("soldier_pine_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> SOLDIER_PINE_BUTTON = woodBlock("soldier_pine_button", p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> ASH_LOG = logBlock("ash_log", AshLogBlock::new);
    public static final DeferredBlock<Block> ASH_WOOD = logBlock("ash_wood", AshWoodBlock::new);
    public static final DeferredBlock<Block> ASH_PLANKS = woodBlock("ash_planks", AshPlanksBlock::new);
    public static final DeferredBlock<Block> ASH_LEAVES = woodBlock("ash_leaves", AshLeavesBlock::new);
    public static final DeferredBlock<Block> ASH_STAIRS = woodBlock("ash_stairs", AshStairsBlock::new);
    public static final DeferredBlock<Block> ASH_SLAB = woodBlock("ash_slab", AshSlabBlock::new);
    public static final DeferredBlock<Block> ASH_FENCE = woodBlock("ash_fence", AshFenceBlock::new);
    public static final DeferredBlock<Block> ASH_FENCE_GATE = woodBlock("ash_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.ASH, p));
    public static final DeferredBlock<Block> ASH_PRESSURE_PLATE = woodBlock("ash_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> ASH_BUTTON = woodBlock("ash_button", p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> HAWTHORN_LOG = logBlock("hawthorn_log", HawthornLogBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_WOOD = logBlock("hawthorn_wood", HawthornWoodBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_PLANKS = woodBlock("hawthorn_planks", HawthornPlanksBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_LEAVES = woodBlock("hawthorn_leaves", HawthornLeavesBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_STAIRS = woodBlock("hawthorn_stairs", HawthornStairsBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_SLAB = woodBlock("hawthorn_slab", HawthornSlabBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_FENCE = woodBlock("hawthorn_fence", HawthornFenceBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_FENCE_GATE = woodBlock("hawthorn_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.HAWTHORN, p));
    public static final DeferredBlock<Block> HAWTHORN_PRESSURE_PLATE = woodBlock("hawthorn_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> HAWTHORN_BUTTON = woodBlock("hawthorn_button", p -> new ButtonBlock(BlockSetType.OAK, 10, p));

    // ── Blackbark Tree ────────────────────────────────────────────────────
    public static final DeferredBlock<Block> BLACKBARK_LOG            = logBlock("blackbark_log",            BlackbarkLogBlock::new);
    public static final DeferredBlock<Block> BLACKBARK_WOOD           = logBlock("blackbark_wood",           BlackbarkWoodBlock::new);
    public static final DeferredBlock<Block> BLACKBARK_PLANKS         = woodBlock("blackbark_planks",         BlackbarkPlanksBlock::new);
    public static final DeferredBlock<Block> BLACKBARK_LEAVES         = woodBlock("blackbark_leaves",         BlackbarkLeavesBlock::new);
    public static final DeferredBlock<Block> BLACKBARK_STAIRS         = woodBlock("blackbark_stairs",         BlackbarkStairsBlock::new);
    public static final DeferredBlock<Block> BLACKBARK_SLAB           = woodBlock("blackbark_slab",           BlackbarkSlabBlock::new);
    public static final DeferredBlock<Block> BLACKBARK_FENCE          = woodBlock("blackbark_fence",          BlackbarkFenceBlock::new);
    public static final DeferredBlock<Block> BLACKBARK_FENCE_GATE     = woodBlock("blackbark_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.BLACKBARK, p));
    public static final DeferredBlock<Block> BLACKBARK_PRESSURE_PLATE = woodBlock("blackbark_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> BLACKBARK_BUTTON         = woodBlock("blackbark_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));

    // ── Bloodwood Tree ────────────────────────────────────────────────────
    public static final DeferredBlock<Block> BLOODWOOD_LOG            = logBlock("bloodwood_log",            BloodwoodLogBlock::new);
    public static final DeferredBlock<Block> BLOODWOOD_WOOD           = logBlock("bloodwood_wood",           BloodwoodWoodBlock::new);
    public static final DeferredBlock<Block> BLOODWOOD_PLANKS         = woodBlock("bloodwood_planks",         BloodwoodPlanksBlock::new);
    public static final DeferredBlock<Block> BLOODWOOD_LEAVES         = woodBlock("bloodwood_leaves",         BloodwoodLeavesBlock::new);
    public static final DeferredBlock<Block> BLOODWOOD_STAIRS         = woodBlock("bloodwood_stairs",         BloodwoodStairsBlock::new);
    public static final DeferredBlock<Block> BLOODWOOD_SLAB           = woodBlock("bloodwood_slab",           BloodwoodSlabBlock::new);
    public static final DeferredBlock<Block> BLOODWOOD_FENCE          = woodBlock("bloodwood_fence",          BloodwoodFenceBlock::new);
    public static final DeferredBlock<Block> BLOODWOOD_FENCE_GATE     = woodBlock("bloodwood_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.BLOODWOOD, p));
    public static final DeferredBlock<Block> BLOODWOOD_PRESSURE_PLATE = woodBlock("bloodwood_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> BLOODWOOD_BUTTON         = woodBlock("bloodwood_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));

    // ── Blue Mahoe Tree ───────────────────────────────────────────────────
    public static final DeferredBlock<Block> BLUE_MAHOE_LOG            = logBlock("blue_mahoe_log",            BlueMahoeLogBlock::new);
    public static final DeferredBlock<Block> BLUE_MAHOE_WOOD           = logBlock("blue_mahoe_wood",           BlueMahoeWoodBlock::new);
    public static final DeferredBlock<Block> BLUE_MAHOE_PLANKS         = woodBlock("blue_mahoe_planks",         BlueMahoePlanksBlock::new);
    public static final DeferredBlock<Block> BLUE_MAHOE_LEAVES         = woodBlock("blue_mahoe_leaves",         BlueMahoeLeavesBlock::new);
    public static final DeferredBlock<Block> BLUE_MAHOE_STAIRS         = woodBlock("blue_mahoe_stairs",         BlueMahoeStairsBlock::new);
    public static final DeferredBlock<Block> BLUE_MAHOE_SLAB           = woodBlock("blue_mahoe_slab",           BlueMahoeSlabBlock::new);
    public static final DeferredBlock<Block> BLUE_MAHOE_FENCE          = woodBlock("blue_mahoe_fence",          BlueMahoeFenceBlock::new);
    public static final DeferredBlock<Block> BLUE_MAHOE_FENCE_GATE     = woodBlock("blue_mahoe_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.BLUE_MAHOE, p));
    public static final DeferredBlock<Block> BLUE_MAHOE_PRESSURE_PLATE = woodBlock("blue_mahoe_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> BLUE_MAHOE_BUTTON         = woodBlock("blue_mahoe_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));

    // ── Cottonwood Tree ───────────────────────────────────────────────────
    public static final DeferredBlock<Block> COTTONWOOD_LOG            = logBlock("cottonwood_log",            CottonwoodLogBlock::new);
    public static final DeferredBlock<Block> COTTONWOOD_WOOD           = logBlock("cottonwood_wood",           CottonwoodWoodBlock::new);
    public static final DeferredBlock<Block> COTTONWOOD_PLANKS         = woodBlock("cottonwood_planks",         CottonwoodPlanksBlock::new);
    public static final DeferredBlock<Block> COTTONWOOD_LEAVES         = woodBlock("cottonwood_leaves",         CottonwoodLeavesBlock::new);
    public static final DeferredBlock<Block> COTTONWOOD_STAIRS         = woodBlock("cottonwood_stairs",         CottonwoodStairsBlock::new);
    public static final DeferredBlock<Block> COTTONWOOD_SLAB           = woodBlock("cottonwood_slab",           CottonwoodSlabBlock::new);
    public static final DeferredBlock<Block> COTTONWOOD_FENCE          = woodBlock("cottonwood_fence",          CottonwoodFenceBlock::new);
    public static final DeferredBlock<Block> COTTONWOOD_FENCE_GATE     = woodBlock("cottonwood_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.COTTONWOOD, p));
    public static final DeferredBlock<Block> COTTONWOOD_PRESSURE_PLATE = woodBlock("cottonwood_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> COTTONWOOD_BUTTON         = woodBlock("cottonwood_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));


    // ── BlackCottonwood Tree ──────────────────────────────────────────
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_LOG            = logBlock("black_cottonwood_log",            BlackCottonwoodLogBlock::new);
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_WOOD           = logBlock("black_cottonwood_wood",           BlackCottonwoodWoodBlock::new);
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_PLANKS         = woodBlock("black_cottonwood_planks",         BlackCottonwoodPlanksBlock::new);
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_LEAVES         = woodBlock("black_cottonwood_leaves",         BlackCottonwoodLeavesBlock::new);
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_STAIRS         = woodBlock("black_cottonwood_stairs",         BlackCottonwoodStairsBlock::new);
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_SLAB           = woodBlock("black_cottonwood_slab",           BlackCottonwoodSlabBlock::new);
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_FENCE          = woodBlock("black_cottonwood_fence",          BlackCottonwoodFenceBlock::new);
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_FENCE_GATE     = woodBlock("black_cottonwood_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.COTTONWOOD, p));
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_PRESSURE_PLATE = woodBlock("black_cottonwood_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_BUTTON         = woodBlock("black_cottonwood_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));

    // ── Cinnamon Tree ──────────────────────────────────────────
    public static final DeferredBlock<Block> CINNAMON_LOG            = logBlock("cinnamon_log",            CinnamonLogBlock::new);
    public static final DeferredBlock<Block> CINNAMON_WOOD           = logBlock("cinnamon_wood",           CinnamonWoodBlock::new);
    public static final DeferredBlock<Block> CINNAMON_PLANKS         = woodBlock("cinnamon_planks",         CinnamonPlanksBlock::new);
    public static final DeferredBlock<Block> CINNAMON_LEAVES         = woodBlock("cinnamon_leaves",         CinnamonLeavesBlock::new);
    public static final DeferredBlock<Block> CINNAMON_STAIRS         = woodBlock("cinnamon_stairs",         CinnamonStairsBlock::new);
    public static final DeferredBlock<Block> CINNAMON_SLAB           = woodBlock("cinnamon_slab",           CinnamonSlabBlock::new);
    public static final DeferredBlock<Block> CINNAMON_FENCE          = woodBlock("cinnamon_fence",          CinnamonFenceBlock::new);
    public static final DeferredBlock<Block> CINNAMON_FENCE_GATE     = woodBlock("cinnamon_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.CINNAMON, p));
    public static final DeferredBlock<Block> CINNAMON_PRESSURE_PLATE = woodBlock("cinnamon_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> CINNAMON_BUTTON         = woodBlock("cinnamon_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));

    // ── Clove Tree ──────────────────────────────────────────
    public static final DeferredBlock<Block> CLOVE_LOG            = logBlock("clove_log",            CloveLogBlock::new);
    public static final DeferredBlock<Block> CLOVE_WOOD           = logBlock("clove_wood",           CloveWoodBlock::new);
    public static final DeferredBlock<Block> CLOVE_PLANKS         = woodBlock("clove_planks",         ClovePlanksBlock::new);
    public static final DeferredBlock<Block> CLOVE_LEAVES         = woodBlock("clove_leaves",         CloveLeavesBlock::new);
    public static final DeferredBlock<Block> CLOVE_STAIRS         = woodBlock("clove_stairs",         CloveStairsBlock::new);
    public static final DeferredBlock<Block> CLOVE_SLAB           = woodBlock("clove_slab",           CloveSlabBlock::new);
    public static final DeferredBlock<Block> CLOVE_FENCE          = woodBlock("clove_fence",          CloveFenceBlock::new);
    public static final DeferredBlock<Block> CLOVE_FENCE_GATE     = woodBlock("clove_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.CLOVE, p));
    public static final DeferredBlock<Block> CLOVE_PRESSURE_PLATE = woodBlock("clove_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> CLOVE_BUTTON         = woodBlock("clove_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));

    // ── Ebony Tree ──────────────────────────────────────────
    public static final DeferredBlock<Block> EBONY_LOG            = logBlock("ebony_log",            EbonyLogBlock::new);
    public static final DeferredBlock<Block> EBONY_WOOD           = logBlock("ebony_wood",           EbonyWoodBlock::new);
    public static final DeferredBlock<Block> EBONY_PLANKS         = woodBlock("ebony_planks",         EbonyPlanksBlock::new);
    public static final DeferredBlock<Block> EBONY_LEAVES         = woodBlock("ebony_leaves",         EbonyLeavesBlock::new);
    public static final DeferredBlock<Block> EBONY_STAIRS         = woodBlock("ebony_stairs",         EbonyStairsBlock::new);
    public static final DeferredBlock<Block> EBONY_SLAB           = woodBlock("ebony_slab",           EbonySlabBlock::new);
    public static final DeferredBlock<Block> EBONY_FENCE          = woodBlock("ebony_fence",          EbonyFenceBlock::new);
    public static final DeferredBlock<Block> EBONY_FENCE_GATE     = woodBlock("ebony_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.EBONY, p));
    public static final DeferredBlock<Block> EBONY_PRESSURE_PLATE = woodBlock("ebony_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> EBONY_BUTTON         = woodBlock("ebony_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));

    // ── Elm Tree ──────────────────────────────────────────
    public static final DeferredBlock<Block> ELM_LOG            = logBlock("elm_log",            ElmLogBlock::new);
    public static final DeferredBlock<Block> ELM_WOOD           = logBlock("elm_wood",           ElmWoodBlock::new);
    public static final DeferredBlock<Block> ELM_PLANKS         = woodBlock("elm_planks",         ElmPlanksBlock::new);
    public static final DeferredBlock<Block> ELM_LEAVES         = woodBlock("elm_leaves",         ElmLeavesBlock::new);
    public static final DeferredBlock<Block> ELM_STAIRS         = woodBlock("elm_stairs",         ElmStairsBlock::new);
    public static final DeferredBlock<Block> ELM_SLAB           = woodBlock("elm_slab",           ElmSlabBlock::new);
    public static final DeferredBlock<Block> ELM_FENCE          = woodBlock("elm_fence",          ElmFenceBlock::new);
    public static final DeferredBlock<Block> ELM_FENCE_GATE     = woodBlock("elm_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.ELM, p));
    public static final DeferredBlock<Block> ELM_PRESSURE_PLATE = woodBlock("elm_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> ELM_BUTTON         = woodBlock("elm_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));

    // ── Cedar Tree ──────────────────────────────────────────
    public static final DeferredBlock<Block> CEDAR_LOG            = logBlock("cedar_log",            CedarLogBlock::new);
    public static final DeferredBlock<Block> CEDAR_WOOD           = logBlock("cedar_wood",           CedarWoodBlock::new);
    public static final DeferredBlock<Block> CEDAR_PLANKS         = woodBlock("cedar_planks",         CedarPlanksBlock::new);
    public static final DeferredBlock<Block> CEDAR_LEAVES         = woodBlock("cedar_leaves",         CedarLeavesBlock::new);
    public static final DeferredBlock<Block> CEDAR_STAIRS         = woodBlock("cedar_stairs",         CedarStairsBlock::new);
    public static final DeferredBlock<Block> CEDAR_SLAB           = woodBlock("cedar_slab",           CedarSlabBlock::new);
    public static final DeferredBlock<Block> CEDAR_FENCE          = woodBlock("cedar_fence",          CedarFenceBlock::new);
    public static final DeferredBlock<Block> CEDAR_FENCE_GATE     = woodBlock("cedar_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.CEDAR, p));
    public static final DeferredBlock<Block> CEDAR_PRESSURE_PLATE = woodBlock("cedar_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> CEDAR_BUTTON         = woodBlock("cedar_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));



    // ── Branch Blocks (log-textured walls) ───────────────────────────────
    public static final DeferredBlock<Block> WEIRWOOD_BRANCH         = woodBlock("weirwood_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_WEIRWOOD_BRANCH = woodBlock("stripped_weirwood_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> ASPEN_BRANCH            = woodBlock("aspen_branch",            WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_ASPEN_BRANCH = woodBlock("stripped_aspen_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> ALDER_BRANCH            = woodBlock("alder_branch",            WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_ALDER_BRANCH = woodBlock("stripped_alder_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> PINE_BRANCH             = woodBlock("pine_branch",             WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_PINE_BRANCH = woodBlock("stripped_pine_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> FIR_BRANCH              = woodBlock("fir_branch",              WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_FIR_BRANCH = woodBlock("stripped_fir_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> SENTINAL_BRANCH         = woodBlock("sentinal_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_SENTINAL_BRANCH = woodBlock("stripped_sentinal_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> IRONWOOD_BRANCH         = woodBlock("ironwood_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_IRONWOOD_BRANCH = woodBlock("stripped_ironwood_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> BEECH_BRANCH            = woodBlock("beech_branch",            WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_BEECH_BRANCH = woodBlock("stripped_beech_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> SOLDIER_PINE_BRANCH     = woodBlock("soldier_pine_branch",     WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_SOLDIER_PINE_BRANCH = woodBlock("stripped_soldier_pine_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> ASH_BRANCH              = woodBlock("ash_branch",              WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_ASH_BRANCH = woodBlock("stripped_ash_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_BRANCH         = woodBlock("hawthorn_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_HAWTHORN_BRANCH = woodBlock("stripped_hawthorn_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> BLACKBARK_BRANCH        = woodBlock("blackbark_branch",        WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_BLACKBARK_BRANCH = woodBlock("stripped_blackbark_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> BLOODWOOD_BRANCH        = woodBlock("bloodwood_branch",        WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_BLOODWOOD_BRANCH = woodBlock("stripped_bloodwood_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> BLUE_MAHOE_BRANCH       = woodBlock("blue_mahoe_branch",       WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_BLUE_MAHOE_BRANCH = woodBlock("stripped_blue_mahoe_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> COTTONWOOD_BRANCH       = woodBlock("cottonwood_branch",       WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_COTTONWOOD_BRANCH = woodBlock("stripped_cottonwood_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_BRANCH = woodBlock("black_cottonwood_branch", WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_BLACK_COTTONWOOD_BRANCH = woodBlock("stripped_black_cottonwood_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> CINNAMON_BRANCH         = woodBlock("cinnamon_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_CINNAMON_BRANCH = woodBlock("stripped_cinnamon_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> CLOVE_BRANCH            = woodBlock("clove_branch",            WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_CLOVE_BRANCH = woodBlock("stripped_clove_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> EBONY_BRANCH            = woodBlock("ebony_branch",            WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_EBONY_BRANCH = woodBlock("stripped_ebony_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> ELM_BRANCH              = woodBlock("elm_branch",              WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_ELM_BRANCH = woodBlock("stripped_elm_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> CEDAR_BRANCH            = woodBlock("cedar_branch",            WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_CEDAR_BRANCH = woodBlock("stripped_cedar_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> APPLE_BRANCH            = woodBlock("apple_branch",            WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_APPLE_BRANCH = woodBlock("stripped_apple_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> GOLDENHEART_BRANCH      = woodBlock("goldenheart_branch",      WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_GOLDENHEART_BRANCH = woodBlock("stripped_goldenheart_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> LINDEN_BRANCH           = woodBlock("linden_branch",           WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_LINDEN_BRANCH = woodBlock("stripped_linden_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> MAHOGANY_BRANCH         = woodBlock("mahogany_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_MAHOGANY_BRANCH = woodBlock("stripped_mahogany_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> MAPLE_BRANCH            = woodBlock("maple_branch",            WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_MAPLE_BRANCH = woodBlock("stripped_maple_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> MYRRH_BRANCH            = woodBlock("myrrh_branch",            WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_MYRRH_BRANCH = woodBlock("stripped_myrrh_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> REDWOOD_BRANCH          = woodBlock("redwood_branch",          WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_REDWOOD_BRANCH = woodBlock("stripped_redwood_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> CHESTNUT_BRANCH         = woodBlock("chestnut_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_CHESTNUT_BRANCH = woodBlock("stripped_chestnut_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> WILLOW_BRANCH           = woodBlock("willow_branch",           WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_WILLOW_BRANCH = woodBlock("stripped_willow_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> WORMTREE_BRANCH         = woodBlock("wormtree_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_WORMTREE_BRANCH = woodBlock("stripped_wormtree_branch", WoodStrippedBranchBlock::new);

    // ── Branch Blocks — vanilla overworld woods (log-textured walls) ──────
    public static final DeferredBlock<Block> OAK_BRANCH              = woodBlock("oak_branch",              WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_OAK_BRANCH     = woodBlock("stripped_oak_branch",     WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> SPRUCE_BRANCH           = woodBlock("spruce_branch",           WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_SPRUCE_BRANCH  = woodBlock("stripped_spruce_branch",  WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> BIRCH_BRANCH            = woodBlock("birch_branch",            WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_BIRCH_BRANCH   = woodBlock("stripped_birch_branch",   WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> JUNGLE_BRANCH           = woodBlock("jungle_branch",           WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_JUNGLE_BRANCH  = woodBlock("stripped_jungle_branch",  WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> ACACIA_BRANCH           = woodBlock("acacia_branch",           WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_ACACIA_BRANCH  = woodBlock("stripped_acacia_branch",  WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> DARK_OAK_BRANCH         = woodBlock("dark_oak_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_DARK_OAK_BRANCH = woodBlock("stripped_dark_oak_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> MANGROVE_BRANCH         = woodBlock("mangrove_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_MANGROVE_BRANCH = woodBlock("stripped_mangrove_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> CHERRY_BRANCH           = woodBlock("cherry_branch",           WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_CHERRY_BRANCH  = woodBlock("stripped_cherry_branch",  WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> PALE_OAK_BRANCH         = woodBlock("pale_oak_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_PALE_OAK_BRANCH = woodBlock("stripped_pale_oak_branch", WoodStrippedBranchBlock::new);

    // ── Signs ──────────────────────────────────────────────────────────────
    public static final DeferredBlock<Block> WEIRWOOD_SIGN              = woodBlock("weirwood_sign",      p -> new GotStandingSignBlock(GotWoodTypes.WEIRWOOD, p, () -> GotModBlockEntities.WEIRWOOD_SIGN.get()));
    public static final DeferredBlock<Block> WEIRWOOD_WALL_SIGN         = woodBlock("weirwood_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.WEIRWOOD, p, () -> GotModBlockEntities.WEIRWOOD_SIGN.get()));
    public static final DeferredBlock<Block> ASPEN_SIGN              = woodBlock("aspen_sign",      p -> new GotStandingSignBlock(GotWoodTypes.ASPEN, p, () -> GotModBlockEntities.ASPEN_SIGN.get()));
    public static final DeferredBlock<Block> ASPEN_WALL_SIGN         = woodBlock("aspen_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.ASPEN, p, () -> GotModBlockEntities.ASPEN_SIGN.get()));
    public static final DeferredBlock<Block> ALDER_SIGN              = woodBlock("alder_sign",      p -> new GotStandingSignBlock(GotWoodTypes.ALDER, p, () -> GotModBlockEntities.ALDER_SIGN.get()));
    public static final DeferredBlock<Block> ALDER_WALL_SIGN         = woodBlock("alder_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.ALDER, p, () -> GotModBlockEntities.ALDER_SIGN.get()));
    public static final DeferredBlock<Block> PINE_SIGN              = woodBlock("pine_sign",      p -> new GotStandingSignBlock(GotWoodTypes.PINE, p, () -> GotModBlockEntities.PINE_SIGN.get()));
    public static final DeferredBlock<Block> PINE_WALL_SIGN         = woodBlock("pine_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.PINE, p, () -> GotModBlockEntities.PINE_SIGN.get()));
    public static final DeferredBlock<Block> FIR_SIGN              = woodBlock("fir_sign",      p -> new GotStandingSignBlock(GotWoodTypes.FIR, p, () -> GotModBlockEntities.FIR_SIGN.get()));
    public static final DeferredBlock<Block> FIR_WALL_SIGN         = woodBlock("fir_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.FIR, p, () -> GotModBlockEntities.FIR_SIGN.get()));
    public static final DeferredBlock<Block> SENTINAL_SIGN              = woodBlock("sentinal_sign",      p -> new GotStandingSignBlock(GotWoodTypes.SENTINAL, p, () -> GotModBlockEntities.SENTINAL_SIGN.get()));
    public static final DeferredBlock<Block> SENTINAL_WALL_SIGN         = woodBlock("sentinal_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.SENTINAL, p, () -> GotModBlockEntities.SENTINAL_SIGN.get()));
    public static final DeferredBlock<Block> IRONWOOD_SIGN              = woodBlock("ironwood_sign",      p -> new GotStandingSignBlock(GotWoodTypes.IRONWOOD, p, () -> GotModBlockEntities.IRONWOOD_SIGN.get()));
    public static final DeferredBlock<Block> IRONWOOD_WALL_SIGN         = woodBlock("ironwood_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.IRONWOOD, p, () -> GotModBlockEntities.IRONWOOD_SIGN.get()));
    public static final DeferredBlock<Block> BEECH_SIGN              = woodBlock("beech_sign",      p -> new GotStandingSignBlock(GotWoodTypes.BEECH, p, () -> GotModBlockEntities.BEECH_SIGN.get()));
    public static final DeferredBlock<Block> BEECH_WALL_SIGN         = woodBlock("beech_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.BEECH, p, () -> GotModBlockEntities.BEECH_SIGN.get()));
    public static final DeferredBlock<Block> SOLDIER_PINE_SIGN              = woodBlock("soldier_pine_sign",      p -> new GotStandingSignBlock(GotWoodTypes.SOLDIER_PINE, p, () -> GotModBlockEntities.SOLDIER_PINE_SIGN.get()));
    public static final DeferredBlock<Block> SOLDIER_PINE_WALL_SIGN         = woodBlock("soldier_pine_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.SOLDIER_PINE, p, () -> GotModBlockEntities.SOLDIER_PINE_SIGN.get()));
    public static final DeferredBlock<Block> ASH_SIGN              = woodBlock("ash_sign",      p -> new GotStandingSignBlock(GotWoodTypes.ASH, p, () -> GotModBlockEntities.ASH_SIGN.get()));
    public static final DeferredBlock<Block> ASH_WALL_SIGN         = woodBlock("ash_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.ASH, p, () -> GotModBlockEntities.ASH_SIGN.get()));
    public static final DeferredBlock<Block> HAWTHORN_SIGN              = woodBlock("hawthorn_sign",      p -> new GotStandingSignBlock(GotWoodTypes.HAWTHORN, p, () -> GotModBlockEntities.HAWTHORN_SIGN.get()));
    public static final DeferredBlock<Block> HAWTHORN_WALL_SIGN         = woodBlock("hawthorn_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.HAWTHORN, p, () -> GotModBlockEntities.HAWTHORN_SIGN.get()));
    public static final DeferredBlock<Block> BLACKBARK_SIGN              = woodBlock("blackbark_sign",      p -> new GotStandingSignBlock(GotWoodTypes.BLACKBARK, p, () -> GotModBlockEntities.BLACKBARK_SIGN.get()));
    public static final DeferredBlock<Block> BLACKBARK_WALL_SIGN         = woodBlock("blackbark_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.BLACKBARK, p, () -> GotModBlockEntities.BLACKBARK_SIGN.get()));
    public static final DeferredBlock<Block> BLOODWOOD_SIGN              = woodBlock("bloodwood_sign",      p -> new GotStandingSignBlock(GotWoodTypes.BLOODWOOD, p, () -> GotModBlockEntities.BLOODWOOD_SIGN.get()));
    public static final DeferredBlock<Block> BLOODWOOD_WALL_SIGN         = woodBlock("bloodwood_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.BLOODWOOD, p, () -> GotModBlockEntities.BLOODWOOD_SIGN.get()));
    public static final DeferredBlock<Block> BLUE_MAHOE_SIGN              = woodBlock("blue_mahoe_sign",      p -> new GotStandingSignBlock(GotWoodTypes.BLUE_MAHOE, p, () -> GotModBlockEntities.BLUE_MAHOE_SIGN.get()));
    public static final DeferredBlock<Block> BLUE_MAHOE_WALL_SIGN         = woodBlock("blue_mahoe_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.BLUE_MAHOE, p, () -> GotModBlockEntities.BLUE_MAHOE_SIGN.get()));
    public static final DeferredBlock<Block> COTTONWOOD_SIGN              = woodBlock("cottonwood_sign",      p -> new GotStandingSignBlock(GotWoodTypes.COTTONWOOD, p, () -> GotModBlockEntities.COTTONWOOD_SIGN.get()));
    public static final DeferredBlock<Block> COTTONWOOD_WALL_SIGN         = woodBlock("cottonwood_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.COTTONWOOD, p, () -> GotModBlockEntities.COTTONWOOD_SIGN.get()));
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_SIGN              = woodBlock("black_cottonwood_sign",      p -> new GotStandingSignBlock(GotWoodTypes.BLACK_COTTONWOOD, p, () -> GotModBlockEntities.BLACK_COTTONWOOD_SIGN.get()));
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_WALL_SIGN         = woodBlock("black_cottonwood_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.BLACK_COTTONWOOD, p, () -> GotModBlockEntities.BLACK_COTTONWOOD_SIGN.get()));
    public static final DeferredBlock<Block> CINNAMON_SIGN              = woodBlock("cinnamon_sign",      p -> new GotStandingSignBlock(GotWoodTypes.CINNAMON, p, () -> GotModBlockEntities.CINNAMON_SIGN.get()));
    public static final DeferredBlock<Block> CINNAMON_WALL_SIGN         = woodBlock("cinnamon_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.CINNAMON, p, () -> GotModBlockEntities.CINNAMON_SIGN.get()));
    public static final DeferredBlock<Block> CLOVE_SIGN              = woodBlock("clove_sign",      p -> new GotStandingSignBlock(GotWoodTypes.CLOVE, p, () -> GotModBlockEntities.CLOVE_SIGN.get()));
    public static final DeferredBlock<Block> CLOVE_WALL_SIGN         = woodBlock("clove_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.CLOVE, p, () -> GotModBlockEntities.CLOVE_SIGN.get()));
    public static final DeferredBlock<Block> EBONY_SIGN              = woodBlock("ebony_sign",      p -> new GotStandingSignBlock(GotWoodTypes.EBONY, p, () -> GotModBlockEntities.EBONY_SIGN.get()));
    public static final DeferredBlock<Block> EBONY_WALL_SIGN         = woodBlock("ebony_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.EBONY, p, () -> GotModBlockEntities.EBONY_SIGN.get()));
    public static final DeferredBlock<Block> ELM_SIGN              = woodBlock("elm_sign",      p -> new GotStandingSignBlock(GotWoodTypes.ELM, p, () -> GotModBlockEntities.ELM_SIGN.get()));
    public static final DeferredBlock<Block> ELM_WALL_SIGN         = woodBlock("elm_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.ELM, p, () -> GotModBlockEntities.ELM_SIGN.get()));
    public static final DeferredBlock<Block> CEDAR_SIGN              = woodBlock("cedar_sign",      p -> new GotStandingSignBlock(GotWoodTypes.CEDAR, p, () -> GotModBlockEntities.CEDAR_SIGN.get()));
    public static final DeferredBlock<Block> CEDAR_WALL_SIGN         = woodBlock("cedar_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.CEDAR, p, () -> GotModBlockEntities.CEDAR_SIGN.get()));
    public static final DeferredBlock<Block> APPLE_SIGN              = woodBlock("apple_sign",      p -> new GotStandingSignBlock(GotWoodTypes.APPLE, p, () -> GotModBlockEntities.APPLE_SIGN.get()));
    public static final DeferredBlock<Block> APPLE_WALL_SIGN         = woodBlock("apple_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.APPLE, p, () -> GotModBlockEntities.APPLE_SIGN.get()));
    public static final DeferredBlock<Block> GOLDENHEART_SIGN              = woodBlock("goldenheart_sign",      p -> new GotStandingSignBlock(GotWoodTypes.GOLDENHEART, p, () -> GotModBlockEntities.GOLDENHEART_SIGN.get()));
    public static final DeferredBlock<Block> GOLDENHEART_WALL_SIGN         = woodBlock("goldenheart_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.GOLDENHEART, p, () -> GotModBlockEntities.GOLDENHEART_SIGN.get()));
    public static final DeferredBlock<Block> LINDEN_SIGN              = woodBlock("linden_sign",      p -> new GotStandingSignBlock(GotWoodTypes.LINDEN, p, () -> GotModBlockEntities.LINDEN_SIGN.get()));
    public static final DeferredBlock<Block> LINDEN_WALL_SIGN         = woodBlock("linden_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.LINDEN, p, () -> GotModBlockEntities.LINDEN_SIGN.get()));
    public static final DeferredBlock<Block> MAHOGANY_SIGN              = woodBlock("mahogany_sign",      p -> new GotStandingSignBlock(GotWoodTypes.MAHOGANY, p, () -> GotModBlockEntities.MAHOGANY_SIGN.get()));
    public static final DeferredBlock<Block> MAHOGANY_WALL_SIGN         = woodBlock("mahogany_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.MAHOGANY, p, () -> GotModBlockEntities.MAHOGANY_SIGN.get()));
    public static final DeferredBlock<Block> MAPLE_SIGN              = woodBlock("maple_sign",      p -> new GotStandingSignBlock(GotWoodTypes.MAPLE, p, () -> GotModBlockEntities.MAPLE_SIGN.get()));
    public static final DeferredBlock<Block> MAPLE_WALL_SIGN         = woodBlock("maple_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.MAPLE, p, () -> GotModBlockEntities.MAPLE_SIGN.get()));
    public static final DeferredBlock<Block> MYRRH_SIGN              = woodBlock("myrrh_sign",      p -> new GotStandingSignBlock(GotWoodTypes.MYRRH, p, () -> GotModBlockEntities.MYRRH_SIGN.get()));
    public static final DeferredBlock<Block> MYRRH_WALL_SIGN         = woodBlock("myrrh_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.MYRRH, p, () -> GotModBlockEntities.MYRRH_SIGN.get()));
    public static final DeferredBlock<Block> REDWOOD_SIGN              = woodBlock("redwood_sign",      p -> new GotStandingSignBlock(GotWoodTypes.REDWOOD, p, () -> GotModBlockEntities.REDWOOD_SIGN.get()));
    public static final DeferredBlock<Block> REDWOOD_WALL_SIGN         = woodBlock("redwood_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.REDWOOD, p, () -> GotModBlockEntities.REDWOOD_SIGN.get()));
    public static final DeferredBlock<Block> CHESTNUT_SIGN              = woodBlock("chestnut_sign",      p -> new GotStandingSignBlock(GotWoodTypes.CHESTNUT, p, () -> GotModBlockEntities.CHESTNUT_SIGN.get()));
    public static final DeferredBlock<Block> CHESTNUT_WALL_SIGN         = woodBlock("chestnut_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.CHESTNUT, p, () -> GotModBlockEntities.CHESTNUT_SIGN.get()));
    public static final DeferredBlock<Block> WILLOW_SIGN              = woodBlock("willow_sign",      p -> new GotStandingSignBlock(GotWoodTypes.WILLOW, p, () -> GotModBlockEntities.WILLOW_SIGN.get()));
    public static final DeferredBlock<Block> WILLOW_WALL_SIGN         = woodBlock("willow_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.WILLOW, p, () -> GotModBlockEntities.WILLOW_SIGN.get()));
    public static final DeferredBlock<Block> WORMTREE_SIGN              = woodBlock("wormtree_sign",      p -> new GotStandingSignBlock(GotWoodTypes.WORMTREE, p, () -> GotModBlockEntities.WORMTREE_SIGN.get()));
    public static final DeferredBlock<Block> WORMTREE_WALL_SIGN         = woodBlock("wormtree_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.WORMTREE, p, () -> GotModBlockEntities.WORMTREE_SIGN.get()));
    // ── Hanging Signs ─────────────────────────────────────────────────────
    public static final DeferredBlock<Block> WEIRWOOD_HANGING_SIGN      = woodBlock("weirwood_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.WEIRWOOD, p));
    public static final DeferredBlock<Block> WEIRWOOD_WALL_HANGING_SIGN = woodBlock("weirwood_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.WEIRWOOD, p));
    public static final DeferredBlock<Block> ASPEN_HANGING_SIGN      = woodBlock("aspen_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.ASPEN, p));
    public static final DeferredBlock<Block> ASPEN_WALL_HANGING_SIGN = woodBlock("aspen_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.ASPEN, p));
    public static final DeferredBlock<Block> ALDER_HANGING_SIGN      = woodBlock("alder_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.ALDER, p));
    public static final DeferredBlock<Block> ALDER_WALL_HANGING_SIGN = woodBlock("alder_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.ALDER, p));
    public static final DeferredBlock<Block> PINE_HANGING_SIGN      = woodBlock("pine_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.PINE, p));
    public static final DeferredBlock<Block> PINE_WALL_HANGING_SIGN = woodBlock("pine_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.PINE, p));
    public static final DeferredBlock<Block> FIR_HANGING_SIGN      = woodBlock("fir_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.FIR, p));
    public static final DeferredBlock<Block> FIR_WALL_HANGING_SIGN = woodBlock("fir_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.FIR, p));
    public static final DeferredBlock<Block> SENTINAL_HANGING_SIGN      = woodBlock("sentinal_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.SENTINAL, p));
    public static final DeferredBlock<Block> SENTINAL_WALL_HANGING_SIGN = woodBlock("sentinal_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.SENTINAL, p));
    public static final DeferredBlock<Block> IRONWOOD_HANGING_SIGN      = woodBlock("ironwood_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.IRONWOOD, p));
    public static final DeferredBlock<Block> IRONWOOD_WALL_HANGING_SIGN = woodBlock("ironwood_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.IRONWOOD, p));
    public static final DeferredBlock<Block> BEECH_HANGING_SIGN      = woodBlock("beech_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.BEECH, p));
    public static final DeferredBlock<Block> BEECH_WALL_HANGING_SIGN = woodBlock("beech_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.BEECH, p));
    public static final DeferredBlock<Block> SOLDIER_PINE_HANGING_SIGN      = woodBlock("soldier_pine_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.SOLDIER_PINE, p));
    public static final DeferredBlock<Block> SOLDIER_PINE_WALL_HANGING_SIGN = woodBlock("soldier_pine_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.SOLDIER_PINE, p));
    public static final DeferredBlock<Block> ASH_HANGING_SIGN      = woodBlock("ash_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.ASH, p));
    public static final DeferredBlock<Block> ASH_WALL_HANGING_SIGN = woodBlock("ash_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.ASH, p));
    public static final DeferredBlock<Block> HAWTHORN_HANGING_SIGN      = woodBlock("hawthorn_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.HAWTHORN, p));
    public static final DeferredBlock<Block> HAWTHORN_WALL_HANGING_SIGN = woodBlock("hawthorn_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.HAWTHORN, p));
    public static final DeferredBlock<Block> BLACKBARK_HANGING_SIGN      = woodBlock("blackbark_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.BLACKBARK, p));
    public static final DeferredBlock<Block> BLACKBARK_WALL_HANGING_SIGN = woodBlock("blackbark_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.BLACKBARK, p));
    public static final DeferredBlock<Block> BLOODWOOD_HANGING_SIGN      = woodBlock("bloodwood_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.BLOODWOOD, p));
    public static final DeferredBlock<Block> BLOODWOOD_WALL_HANGING_SIGN = woodBlock("bloodwood_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.BLOODWOOD, p));
    public static final DeferredBlock<Block> BLUE_MAHOE_HANGING_SIGN      = woodBlock("blue_mahoe_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.BLUE_MAHOE, p));
    public static final DeferredBlock<Block> BLUE_MAHOE_WALL_HANGING_SIGN = woodBlock("blue_mahoe_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.BLUE_MAHOE, p));
    public static final DeferredBlock<Block> COTTONWOOD_HANGING_SIGN      = woodBlock("cottonwood_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.COTTONWOOD, p));
    public static final DeferredBlock<Block> COTTONWOOD_WALL_HANGING_SIGN = woodBlock("cottonwood_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.COTTONWOOD, p));
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_HANGING_SIGN      = woodBlock("black_cottonwood_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.BLACK_COTTONWOOD, p));
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_WALL_HANGING_SIGN = woodBlock("black_cottonwood_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.BLACK_COTTONWOOD, p));
    public static final DeferredBlock<Block> CINNAMON_HANGING_SIGN      = woodBlock("cinnamon_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.CINNAMON, p));
    public static final DeferredBlock<Block> CINNAMON_WALL_HANGING_SIGN = woodBlock("cinnamon_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.CINNAMON, p));
    public static final DeferredBlock<Block> CLOVE_HANGING_SIGN      = woodBlock("clove_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.CLOVE, p));
    public static final DeferredBlock<Block> CLOVE_WALL_HANGING_SIGN = woodBlock("clove_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.CLOVE, p));
    public static final DeferredBlock<Block> EBONY_HANGING_SIGN      = woodBlock("ebony_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.EBONY, p));
    public static final DeferredBlock<Block> EBONY_WALL_HANGING_SIGN = woodBlock("ebony_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.EBONY, p));
    public static final DeferredBlock<Block> ELM_HANGING_SIGN      = woodBlock("elm_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.ELM, p));
    public static final DeferredBlock<Block> ELM_WALL_HANGING_SIGN = woodBlock("elm_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.ELM, p));
    public static final DeferredBlock<Block> CEDAR_HANGING_SIGN      = woodBlock("cedar_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.CEDAR, p));
    public static final DeferredBlock<Block> CEDAR_WALL_HANGING_SIGN = woodBlock("cedar_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.CEDAR, p));
    public static final DeferredBlock<Block> APPLE_HANGING_SIGN      = woodBlock("apple_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.APPLE, p));
    public static final DeferredBlock<Block> APPLE_WALL_HANGING_SIGN = woodBlock("apple_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.APPLE, p));
    public static final DeferredBlock<Block> GOLDENHEART_HANGING_SIGN      = woodBlock("goldenheart_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.GOLDENHEART, p));
    public static final DeferredBlock<Block> GOLDENHEART_WALL_HANGING_SIGN = woodBlock("goldenheart_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.GOLDENHEART, p));
    public static final DeferredBlock<Block> LINDEN_HANGING_SIGN      = woodBlock("linden_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.LINDEN, p));
    public static final DeferredBlock<Block> LINDEN_WALL_HANGING_SIGN = woodBlock("linden_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.LINDEN, p));
    public static final DeferredBlock<Block> MAHOGANY_HANGING_SIGN      = woodBlock("mahogany_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.MAHOGANY, p));
    public static final DeferredBlock<Block> MAHOGANY_WALL_HANGING_SIGN = woodBlock("mahogany_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.MAHOGANY, p));
    public static final DeferredBlock<Block> MAPLE_HANGING_SIGN      = woodBlock("maple_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.MAPLE, p));
    public static final DeferredBlock<Block> MAPLE_WALL_HANGING_SIGN = woodBlock("maple_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.MAPLE, p));
    public static final DeferredBlock<Block> MYRRH_HANGING_SIGN      = woodBlock("myrrh_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.MYRRH, p));
    public static final DeferredBlock<Block> MYRRH_WALL_HANGING_SIGN = woodBlock("myrrh_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.MYRRH, p));
    public static final DeferredBlock<Block> REDWOOD_HANGING_SIGN      = woodBlock("redwood_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.REDWOOD, p));
    public static final DeferredBlock<Block> REDWOOD_WALL_HANGING_SIGN = woodBlock("redwood_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.REDWOOD, p));
    public static final DeferredBlock<Block> CHESTNUT_HANGING_SIGN      = woodBlock("chestnut_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.CHESTNUT, p));
    public static final DeferredBlock<Block> CHESTNUT_WALL_HANGING_SIGN = woodBlock("chestnut_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.CHESTNUT, p));
    public static final DeferredBlock<Block> WILLOW_HANGING_SIGN      = woodBlock("willow_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.WILLOW, p));
    public static final DeferredBlock<Block> WILLOW_WALL_HANGING_SIGN = woodBlock("willow_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.WILLOW, p));
    public static final DeferredBlock<Block> WORMTREE_HANGING_SIGN      = woodBlock("wormtree_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.WORMTREE, p));
    public static final DeferredBlock<Block> WORMTREE_WALL_HANGING_SIGN = woodBlock("wormtree_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.WORMTREE, p));


    // ── Doors ────────────────────────────────────────────────────────────
    public static final DeferredBlock<Block> WEIRWOOD_DOOR             = doorBlock("weirwood_door", p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> ASPEN_DOOR                = doorBlock("aspen_door", p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> ALDER_DOOR                = doorBlock("alder_door", p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> PINE_DOOR                 = doorBlock("pine_door", p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> FIR_DOOR                  = doorBlock("fir_door", p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> SENTINAL_DOOR             = doorBlock("sentinal_door", p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> IRONWOOD_DOOR             = doorBlock("ironwood_door", p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> BEECH_DOOR                = doorBlock("beech_door", p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> SOLDIER_PINE_DOOR         = doorBlock("soldier_pine_door", p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> ASH_DOOR                  = doorBlock("ash_door", p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> HAWTHORN_DOOR             = doorBlock("hawthorn_door", p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> BLACKBARK_DOOR            = doorBlock("blackbark_door", p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> BLOODWOOD_DOOR            = doorBlock("bloodwood_door", p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> BLUE_MAHOE_DOOR           = doorBlock("blue_mahoe_door", p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> COTTONWOOD_DOOR           = doorBlock("cottonwood_door", p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_DOOR     = doorBlock("black_cottonwood_door", p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> CINNAMON_DOOR             = doorBlock("cinnamon_door", p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> CLOVE_DOOR                = doorBlock("clove_door", p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> EBONY_DOOR                = doorBlock("ebony_door", p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> ELM_DOOR                  = doorBlock("elm_door", p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> CEDAR_DOOR                = doorBlock("cedar_door", p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> APPLE_DOOR                = doorBlock("apple_door", p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> GOLDENHEART_DOOR          = doorBlock("goldenheart_door", p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> LINDEN_DOOR               = doorBlock("linden_door", p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> MAHOGANY_DOOR             = doorBlock("mahogany_door", p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> MAPLE_DOOR                = doorBlock("maple_door", p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> MYRRH_DOOR                = doorBlock("myrrh_door", p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> REDWOOD_DOOR              = doorBlock("redwood_door", p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> CHESTNUT_DOOR             = doorBlock("chestnut_door", p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> WILLOW_DOOR               = doorBlock("willow_door", p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> WORMTREE_DOOR             = doorBlock("wormtree_door", p -> new DoorBlock(BlockSetType.OAK, p));

    // ── Trapdoors ────────────────────────────────────────────────────────
    public static final DeferredBlock<Block> WEIRWOOD_TRAPDOOR         = trapdoorBlock("weirwood_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> ASPEN_TRAPDOOR            = trapdoorBlock("aspen_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> ALDER_TRAPDOOR            = trapdoorBlock("alder_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> PINE_TRAPDOOR             = trapdoorBlock("pine_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> FIR_TRAPDOOR              = trapdoorBlock("fir_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> SENTINAL_TRAPDOOR         = trapdoorBlock("sentinal_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> IRONWOOD_TRAPDOOR         = trapdoorBlock("ironwood_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> BEECH_TRAPDOOR            = trapdoorBlock("beech_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> SOLDIER_PINE_TRAPDOOR     = trapdoorBlock("soldier_pine_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> ASH_TRAPDOOR              = trapdoorBlock("ash_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> HAWTHORN_TRAPDOOR         = trapdoorBlock("hawthorn_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> BLACKBARK_TRAPDOOR        = trapdoorBlock("blackbark_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> BLOODWOOD_TRAPDOOR        = trapdoorBlock("bloodwood_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> BLUE_MAHOE_TRAPDOOR       = trapdoorBlock("blue_mahoe_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> COTTONWOOD_TRAPDOOR       = trapdoorBlock("cottonwood_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_TRAPDOOR = trapdoorBlock("black_cottonwood_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> CINNAMON_TRAPDOOR         = trapdoorBlock("cinnamon_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> CLOVE_TRAPDOOR            = trapdoorBlock("clove_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> EBONY_TRAPDOOR            = trapdoorBlock("ebony_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> ELM_TRAPDOOR              = trapdoorBlock("elm_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> CEDAR_TRAPDOOR            = trapdoorBlock("cedar_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> APPLE_TRAPDOOR            = trapdoorBlock("apple_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> GOLDENHEART_TRAPDOOR      = trapdoorBlock("goldenheart_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> LINDEN_TRAPDOOR           = trapdoorBlock("linden_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> MAHOGANY_TRAPDOOR         = trapdoorBlock("mahogany_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> MAPLE_TRAPDOOR            = trapdoorBlock("maple_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> MYRRH_TRAPDOOR            = trapdoorBlock("myrrh_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> REDWOOD_TRAPDOOR          = trapdoorBlock("redwood_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> CHESTNUT_TRAPDOOR         = trapdoorBlock("chestnut_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> WILLOW_TRAPDOOR           = trapdoorBlock("willow_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> WORMTREE_TRAPDOOR         = trapdoorBlock("wormtree_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));

    // ── Stripped Logs ────────────────────────────────────────────────────
    public static final DeferredBlock<Block> STRIPPED_WEIRWOOD_LOG = logBlock("stripped_weirwood_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_ASPEN_LOG = logBlock("stripped_aspen_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_ALDER_LOG = logBlock("stripped_alder_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_PINE_LOG = logBlock("stripped_pine_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_FIR_LOG = logBlock("stripped_fir_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_SENTINAL_LOG = logBlock("stripped_sentinal_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_IRONWOOD_LOG = logBlock("stripped_ironwood_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_BEECH_LOG = logBlock("stripped_beech_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_SOLDIER_PINE_LOG = logBlock("stripped_soldier_pine_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_ASH_LOG = logBlock("stripped_ash_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_HAWTHORN_LOG = logBlock("stripped_hawthorn_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_BLACKBARK_LOG = logBlock("stripped_blackbark_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_BLOODWOOD_LOG = logBlock("stripped_bloodwood_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_BLUE_MAHOE_LOG = logBlock("stripped_blue_mahoe_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_COTTONWOOD_LOG = logBlock("stripped_cottonwood_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_BLACK_COTTONWOOD_LOG = logBlock("stripped_black_cottonwood_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_CINNAMON_LOG = logBlock("stripped_cinnamon_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_CLOVE_LOG = logBlock("stripped_clove_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_EBONY_LOG = logBlock("stripped_ebony_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_ELM_LOG = logBlock("stripped_elm_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_CEDAR_LOG = logBlock("stripped_cedar_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_APPLE_LOG = logBlock("stripped_apple_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_GOLDENHEART_LOG = logBlock("stripped_goldenheart_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_LINDEN_LOG = logBlock("stripped_linden_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_MAHOGANY_LOG = logBlock("stripped_mahogany_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_MAPLE_LOG = logBlock("stripped_maple_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_MYRRH_LOG = logBlock("stripped_myrrh_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_REDWOOD_LOG = logBlock("stripped_redwood_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_CHESTNUT_LOG = logBlock("stripped_chestnut_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_WILLOW_LOG = logBlock("stripped_willow_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_WORMTREE_LOG = logBlock("stripped_wormtree_log", GotStrippedLogBlock::new);

    // ── Stripped Woods ───────────────────────────────────────────────────
    public static final DeferredBlock<Block> STRIPPED_WEIRWOOD_WOOD         = logBlock("stripped_weirwood_wood",         GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_ASPEN_WOOD            = logBlock("stripped_aspen_wood",            GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_ALDER_WOOD            = logBlock("stripped_alder_wood",            GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_PINE_WOOD             = logBlock("stripped_pine_wood",             GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_FIR_WOOD              = logBlock("stripped_fir_wood",              GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_SENTINAL_WOOD         = logBlock("stripped_sentinal_wood",         GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_IRONWOOD_WOOD         = logBlock("stripped_ironwood_wood",         GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_BEECH_WOOD            = logBlock("stripped_beech_wood",            GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_SOLDIER_PINE_WOOD     = logBlock("stripped_soldier_pine_wood",     GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_ASH_WOOD              = logBlock("stripped_ash_wood",              GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_HAWTHORN_WOOD         = logBlock("stripped_hawthorn_wood",         GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_BLACKBARK_WOOD        = logBlock("stripped_blackbark_wood",        GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_BLOODWOOD_WOOD        = logBlock("stripped_bloodwood_wood",        GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_BLUE_MAHOE_WOOD       = logBlock("stripped_blue_mahoe_wood",       GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_COTTONWOOD_WOOD       = logBlock("stripped_cottonwood_wood",       GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_BLACK_COTTONWOOD_WOOD = logBlock("stripped_black_cottonwood_wood", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_CINNAMON_WOOD         = logBlock("stripped_cinnamon_wood",         GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_CLOVE_WOOD            = logBlock("stripped_clove_wood",            GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_EBONY_WOOD            = logBlock("stripped_ebony_wood",            GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_ELM_WOOD              = logBlock("stripped_elm_wood",              GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_CEDAR_WOOD            = logBlock("stripped_cedar_wood",            GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_APPLE_WOOD            = logBlock("stripped_apple_wood",            GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_GOLDENHEART_WOOD      = logBlock("stripped_goldenheart_wood",      GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_LINDEN_WOOD           = logBlock("stripped_linden_wood",           GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_MAHOGANY_WOOD         = logBlock("stripped_mahogany_wood",         GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_MAPLE_WOOD            = logBlock("stripped_maple_wood",            GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_MYRRH_WOOD            = logBlock("stripped_myrrh_wood",            GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_REDWOOD_WOOD          = logBlock("stripped_redwood_wood",          GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_CHESTNUT_WOOD         = logBlock("stripped_chestnut_wood",         GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_WILLOW_WOOD           = logBlock("stripped_willow_wood",           GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_WORMTREE_WOOD         = logBlock("stripped_wormtree_wood",         GotStrippedLogBlock::new);

    // ── Wood Slabs & Stairs — GOT trees (bark-all-sides, normal and stripped) ──
    public static final DeferredBlock<Block> WEIRWOOD_WOOD_SLAB            = woodBlock("weirwood_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> WEIRWOOD_WOOD_STAIRS          = woodBlock("weirwood_wood_stairs",          p -> new StairBlock(WEIRWOOD_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_WEIRWOOD_WOOD_SLAB   = woodBlock("stripped_weirwood_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_WEIRWOOD_WOOD_STAIRS = woodBlock("stripped_weirwood_wood_stairs", p -> new StairBlock(STRIPPED_WEIRWOOD_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> ASPEN_WOOD_SLAB            = woodBlock("aspen_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> ASPEN_WOOD_STAIRS          = woodBlock("aspen_wood_stairs",          p -> new StairBlock(ASPEN_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_ASPEN_WOOD_SLAB   = woodBlock("stripped_aspen_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_ASPEN_WOOD_STAIRS = woodBlock("stripped_aspen_wood_stairs", p -> new StairBlock(STRIPPED_ASPEN_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> ALDER_WOOD_SLAB            = woodBlock("alder_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> ALDER_WOOD_STAIRS          = woodBlock("alder_wood_stairs",          p -> new StairBlock(ALDER_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_ALDER_WOOD_SLAB   = woodBlock("stripped_alder_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_ALDER_WOOD_STAIRS = woodBlock("stripped_alder_wood_stairs", p -> new StairBlock(STRIPPED_ALDER_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> PINE_WOOD_SLAB            = woodBlock("pine_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> PINE_WOOD_STAIRS          = woodBlock("pine_wood_stairs",          p -> new StairBlock(PINE_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_PINE_WOOD_SLAB   = woodBlock("stripped_pine_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_PINE_WOOD_STAIRS = woodBlock("stripped_pine_wood_stairs", p -> new StairBlock(STRIPPED_PINE_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> FIR_WOOD_SLAB            = woodBlock("fir_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> FIR_WOOD_STAIRS          = woodBlock("fir_wood_stairs",          p -> new StairBlock(FIR_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_FIR_WOOD_SLAB   = woodBlock("stripped_fir_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_FIR_WOOD_STAIRS = woodBlock("stripped_fir_wood_stairs", p -> new StairBlock(STRIPPED_FIR_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> SENTINAL_WOOD_SLAB            = woodBlock("sentinal_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> SENTINAL_WOOD_STAIRS          = woodBlock("sentinal_wood_stairs",          p -> new StairBlock(SENTINAL_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_SENTINAL_WOOD_SLAB   = woodBlock("stripped_sentinal_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_SENTINAL_WOOD_STAIRS = woodBlock("stripped_sentinal_wood_stairs", p -> new StairBlock(STRIPPED_SENTINAL_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> IRONWOOD_WOOD_SLAB            = woodBlock("ironwood_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> IRONWOOD_WOOD_STAIRS          = woodBlock("ironwood_wood_stairs",          p -> new StairBlock(IRONWOOD_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_IRONWOOD_WOOD_SLAB   = woodBlock("stripped_ironwood_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_IRONWOOD_WOOD_STAIRS = woodBlock("stripped_ironwood_wood_stairs", p -> new StairBlock(STRIPPED_IRONWOOD_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> BEECH_WOOD_SLAB            = woodBlock("beech_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> BEECH_WOOD_STAIRS          = woodBlock("beech_wood_stairs",          p -> new StairBlock(BEECH_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_BEECH_WOOD_SLAB   = woodBlock("stripped_beech_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_BEECH_WOOD_STAIRS = woodBlock("stripped_beech_wood_stairs", p -> new StairBlock(STRIPPED_BEECH_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> SOLDIER_PINE_WOOD_SLAB            = woodBlock("soldier_pine_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> SOLDIER_PINE_WOOD_STAIRS          = woodBlock("soldier_pine_wood_stairs",          p -> new StairBlock(SOLDIER_PINE_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_SOLDIER_PINE_WOOD_SLAB   = woodBlock("stripped_soldier_pine_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_SOLDIER_PINE_WOOD_STAIRS = woodBlock("stripped_soldier_pine_wood_stairs", p -> new StairBlock(STRIPPED_SOLDIER_PINE_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> ASH_WOOD_SLAB            = woodBlock("ash_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> ASH_WOOD_STAIRS          = woodBlock("ash_wood_stairs",          p -> new StairBlock(ASH_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_ASH_WOOD_SLAB   = woodBlock("stripped_ash_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_ASH_WOOD_STAIRS = woodBlock("stripped_ash_wood_stairs", p -> new StairBlock(STRIPPED_ASH_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> HAWTHORN_WOOD_SLAB            = woodBlock("hawthorn_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> HAWTHORN_WOOD_STAIRS          = woodBlock("hawthorn_wood_stairs",          p -> new StairBlock(HAWTHORN_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_HAWTHORN_WOOD_SLAB   = woodBlock("stripped_hawthorn_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_HAWTHORN_WOOD_STAIRS = woodBlock("stripped_hawthorn_wood_stairs", p -> new StairBlock(STRIPPED_HAWTHORN_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> BLACKBARK_WOOD_SLAB            = woodBlock("blackbark_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> BLACKBARK_WOOD_STAIRS          = woodBlock("blackbark_wood_stairs",          p -> new StairBlock(BLACKBARK_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_BLACKBARK_WOOD_SLAB   = woodBlock("stripped_blackbark_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_BLACKBARK_WOOD_STAIRS = woodBlock("stripped_blackbark_wood_stairs", p -> new StairBlock(STRIPPED_BLACKBARK_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> BLOODWOOD_WOOD_SLAB            = woodBlock("bloodwood_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> BLOODWOOD_WOOD_STAIRS          = woodBlock("bloodwood_wood_stairs",          p -> new StairBlock(BLOODWOOD_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_BLOODWOOD_WOOD_SLAB   = woodBlock("stripped_bloodwood_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_BLOODWOOD_WOOD_STAIRS = woodBlock("stripped_bloodwood_wood_stairs", p -> new StairBlock(STRIPPED_BLOODWOOD_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> BLUE_MAHOE_WOOD_SLAB            = woodBlock("blue_mahoe_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> BLUE_MAHOE_WOOD_STAIRS          = woodBlock("blue_mahoe_wood_stairs",          p -> new StairBlock(BLUE_MAHOE_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_BLUE_MAHOE_WOOD_SLAB   = woodBlock("stripped_blue_mahoe_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_BLUE_MAHOE_WOOD_STAIRS = woodBlock("stripped_blue_mahoe_wood_stairs", p -> new StairBlock(STRIPPED_BLUE_MAHOE_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> COTTONWOOD_WOOD_SLAB            = woodBlock("cottonwood_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> COTTONWOOD_WOOD_STAIRS          = woodBlock("cottonwood_wood_stairs",          p -> new StairBlock(COTTONWOOD_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_COTTONWOOD_WOOD_SLAB   = woodBlock("stripped_cottonwood_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_COTTONWOOD_WOOD_STAIRS = woodBlock("stripped_cottonwood_wood_stairs", p -> new StairBlock(STRIPPED_COTTONWOOD_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_WOOD_SLAB            = woodBlock("black_cottonwood_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_WOOD_STAIRS          = woodBlock("black_cottonwood_wood_stairs",          p -> new StairBlock(BLACK_COTTONWOOD_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_BLACK_COTTONWOOD_WOOD_SLAB   = woodBlock("stripped_black_cottonwood_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_BLACK_COTTONWOOD_WOOD_STAIRS = woodBlock("stripped_black_cottonwood_wood_stairs", p -> new StairBlock(STRIPPED_BLACK_COTTONWOOD_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> CINNAMON_WOOD_SLAB            = woodBlock("cinnamon_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> CINNAMON_WOOD_STAIRS          = woodBlock("cinnamon_wood_stairs",          p -> new StairBlock(CINNAMON_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_CINNAMON_WOOD_SLAB   = woodBlock("stripped_cinnamon_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_CINNAMON_WOOD_STAIRS = woodBlock("stripped_cinnamon_wood_stairs", p -> new StairBlock(STRIPPED_CINNAMON_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> CLOVE_WOOD_SLAB            = woodBlock("clove_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> CLOVE_WOOD_STAIRS          = woodBlock("clove_wood_stairs",          p -> new StairBlock(CLOVE_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_CLOVE_WOOD_SLAB   = woodBlock("stripped_clove_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_CLOVE_WOOD_STAIRS = woodBlock("stripped_clove_wood_stairs", p -> new StairBlock(STRIPPED_CLOVE_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> EBONY_WOOD_SLAB            = woodBlock("ebony_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> EBONY_WOOD_STAIRS          = woodBlock("ebony_wood_stairs",          p -> new StairBlock(EBONY_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_EBONY_WOOD_SLAB   = woodBlock("stripped_ebony_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_EBONY_WOOD_STAIRS = woodBlock("stripped_ebony_wood_stairs", p -> new StairBlock(STRIPPED_EBONY_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> ELM_WOOD_SLAB            = woodBlock("elm_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> ELM_WOOD_STAIRS          = woodBlock("elm_wood_stairs",          p -> new StairBlock(ELM_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_ELM_WOOD_SLAB   = woodBlock("stripped_elm_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_ELM_WOOD_STAIRS = woodBlock("stripped_elm_wood_stairs", p -> new StairBlock(STRIPPED_ELM_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> CEDAR_WOOD_SLAB            = woodBlock("cedar_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> CEDAR_WOOD_STAIRS          = woodBlock("cedar_wood_stairs",          p -> new StairBlock(CEDAR_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_CEDAR_WOOD_SLAB   = woodBlock("stripped_cedar_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_CEDAR_WOOD_STAIRS = woodBlock("stripped_cedar_wood_stairs", p -> new StairBlock(STRIPPED_CEDAR_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> APPLE_WOOD_SLAB            = woodBlock("apple_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_APPLE_WOOD_SLAB   = woodBlock("stripped_apple_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_APPLE_WOOD_STAIRS = woodBlock("stripped_apple_wood_stairs", p -> new StairBlock(STRIPPED_APPLE_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> GOLDENHEART_WOOD_SLAB            = woodBlock("goldenheart_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_GOLDENHEART_WOOD_SLAB   = woodBlock("stripped_goldenheart_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_GOLDENHEART_WOOD_STAIRS = woodBlock("stripped_goldenheart_wood_stairs", p -> new StairBlock(STRIPPED_GOLDENHEART_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> LINDEN_WOOD_SLAB            = woodBlock("linden_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_LINDEN_WOOD_SLAB   = woodBlock("stripped_linden_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_LINDEN_WOOD_STAIRS = woodBlock("stripped_linden_wood_stairs", p -> new StairBlock(STRIPPED_LINDEN_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> MAHOGANY_WOOD_SLAB            = woodBlock("mahogany_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_MAHOGANY_WOOD_SLAB   = woodBlock("stripped_mahogany_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_MAHOGANY_WOOD_STAIRS = woodBlock("stripped_mahogany_wood_stairs", p -> new StairBlock(STRIPPED_MAHOGANY_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> MAPLE_WOOD_SLAB            = woodBlock("maple_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_MAPLE_WOOD_SLAB   = woodBlock("stripped_maple_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_MAPLE_WOOD_STAIRS = woodBlock("stripped_maple_wood_stairs", p -> new StairBlock(STRIPPED_MAPLE_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> MYRRH_WOOD_SLAB            = woodBlock("myrrh_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_MYRRH_WOOD_SLAB   = woodBlock("stripped_myrrh_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_MYRRH_WOOD_STAIRS = woodBlock("stripped_myrrh_wood_stairs", p -> new StairBlock(STRIPPED_MYRRH_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> REDWOOD_WOOD_SLAB            = woodBlock("redwood_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_REDWOOD_WOOD_SLAB   = woodBlock("stripped_redwood_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_REDWOOD_WOOD_STAIRS = woodBlock("stripped_redwood_wood_stairs", p -> new StairBlock(STRIPPED_REDWOOD_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> CHESTNUT_WOOD_SLAB            = woodBlock("chestnut_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_CHESTNUT_WOOD_SLAB   = woodBlock("stripped_chestnut_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_CHESTNUT_WOOD_STAIRS = woodBlock("stripped_chestnut_wood_stairs", p -> new StairBlock(STRIPPED_CHESTNUT_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> WILLOW_WOOD_SLAB            = woodBlock("willow_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_WILLOW_WOOD_SLAB   = woodBlock("stripped_willow_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_WILLOW_WOOD_STAIRS = woodBlock("stripped_willow_wood_stairs", p -> new StairBlock(STRIPPED_WILLOW_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> WORMTREE_WOOD_SLAB            = woodBlock("wormtree_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_WORMTREE_WOOD_SLAB   = woodBlock("stripped_wormtree_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_WORMTREE_WOOD_STAIRS = woodBlock("stripped_wormtree_wood_stairs", p -> new StairBlock(STRIPPED_WORMTREE_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> NIGHTWOOD_WOOD_SLAB            = woodBlock("nightwood_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_NIGHTWOOD_WOOD_SLAB   = woodBlock("stripped_nightwood_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> PURPLEHEART_WOOD_SLAB            = woodBlock("purpleheart_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_PURPLEHEART_WOOD_SLAB   = woodBlock("stripped_purpleheart_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> TIGERWOOD_WOOD_SLAB            = woodBlock("tigerwood_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_TIGERWOOD_WOOD_SLAB   = woodBlock("stripped_tigerwood_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> BURL_WOOD_SLAB            = woodBlock("burl_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_BURL_WOOD_SLAB   = woodBlock("stripped_burl_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> SANDALWOOD_WOOD_SLAB            = woodBlock("sandalwood_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_SANDALWOOD_WOOD_SLAB   = woodBlock("stripped_sandalwood_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> SANDBEGGAR_WOOD_SLAB            = woodBlock("sandbeggar_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_SANDBEGGAR_WOOD_SLAB   = woodBlock("stripped_sandbeggar_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> APRICOT_WOOD_SLAB            = woodBlock("apricot_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_APRICOT_WOOD_SLAB   = woodBlock("stripped_apricot_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> BLACKTHORN_WOOD_SLAB            = woodBlock("blackthorn_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_BLACKTHORN_WOOD_SLAB   = woodBlock("stripped_blackthorn_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> RED_CHERRY_WOOD_SLAB            = woodBlock("red_cherry_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_RED_CHERRY_WOOD_SLAB   = woodBlock("stripped_red_cherry_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> BLACK_CHERRY_WOOD_SLAB            = woodBlock("black_cherry_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_BLACK_CHERRY_WOOD_SLAB   = woodBlock("stripped_black_cherry_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> WHITE_CHERRY_WOOD_SLAB            = woodBlock("white_cherry_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_WHITE_CHERRY_WOOD_SLAB   = woodBlock("stripped_white_cherry_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> CRABAPPLE_WOOD_SLAB            = woodBlock("crabapple_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_CRABAPPLE_WOOD_SLAB   = woodBlock("stripped_crabapple_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> DATE_PALM_WOOD_SLAB            = woodBlock("date_palm_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_DATE_PALM_WOOD_SLAB   = woodBlock("stripped_date_palm_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> FIG_WOOD_SLAB            = woodBlock("fig_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_FIG_WOOD_SLAB   = woodBlock("stripped_fig_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> LEMON_WOOD_SLAB            = woodBlock("lemon_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_LEMON_WOOD_SLAB   = woodBlock("stripped_lemon_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> LIME_WOOD_SLAB            = woodBlock("lime_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_LIME_WOOD_SLAB   = woodBlock("stripped_lime_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> OLIVE_WOOD_SLAB            = woodBlock("olive_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_OLIVE_WOOD_SLAB   = woodBlock("stripped_olive_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> ORANGE_WOOD_SLAB            = woodBlock("orange_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_ORANGE_WOOD_SLAB   = woodBlock("stripped_orange_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> ALMOND_WOOD_SLAB            = woodBlock("almond_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_ALMOND_WOOD_SLAB   = woodBlock("stripped_almond_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> HEMLOCK_WOOD_SLAB            = woodBlock("hemlock_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_HEMLOCK_WOOD_SLAB   = woodBlock("stripped_hemlock_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> NUTMEG_WOOD_SLAB            = woodBlock("nutmeg_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_NUTMEG_WOOD_SLAB   = woodBlock("stripped_nutmeg_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> PEACH_WOOD_SLAB            = woodBlock("peach_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_PEACH_WOOD_SLAB   = woodBlock("stripped_peach_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> PEAR_WOOD_SLAB            = woodBlock("pear_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_PEAR_WOOD_SLAB   = woodBlock("stripped_pear_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> PERSIMMON_WOOD_SLAB            = woodBlock("persimmon_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_PERSIMMON_WOOD_SLAB   = woodBlock("stripped_persimmon_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> PINK_IVORY_WOOD_SLAB            = woodBlock("pink_ivory_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_PINK_IVORY_WOOD_SLAB   = woodBlock("stripped_pink_ivory_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> PLUM_WOOD_SLAB            = woodBlock("plum_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_PLUM_WOOD_SLAB   = woodBlock("stripped_plum_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> POMEGRANATE_WOOD_SLAB            = woodBlock("pomegranate_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_POMEGRANATE_WOOD_SLAB   = woodBlock("stripped_pomegranate_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> PRUNE_WOOD_SLAB            = woodBlock("prune_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_PRUNE_WOOD_SLAB   = woodBlock("stripped_prune_wood_slab",   p -> new SlabBlock(p));

    // ── Wood Slabs & Stairs — vanilla overworld trees (normal and stripped) ──────
    public static final DeferredBlock<Block> OAK_WOOD_SLAB            = woodBlock("oak_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> OAK_WOOD_STAIRS          = woodBlock("oak_wood_stairs",          p -> new StairBlock(Blocks.OAK_WOOD.defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_OAK_WOOD_SLAB   = woodBlock("stripped_oak_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_OAK_WOOD_STAIRS = woodBlock("stripped_oak_wood_stairs", p -> new StairBlock(Blocks.STRIPPED_OAK_WOOD.defaultBlockState(), p));
    public static final DeferredBlock<Block> SPRUCE_WOOD_SLAB            = woodBlock("spruce_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> SPRUCE_WOOD_STAIRS          = woodBlock("spruce_wood_stairs",          p -> new StairBlock(Blocks.SPRUCE_WOOD.defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_SPRUCE_WOOD_SLAB   = woodBlock("stripped_spruce_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_SPRUCE_WOOD_STAIRS = woodBlock("stripped_spruce_wood_stairs", p -> new StairBlock(Blocks.STRIPPED_SPRUCE_WOOD.defaultBlockState(), p));
    public static final DeferredBlock<Block> BIRCH_WOOD_SLAB            = woodBlock("birch_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> BIRCH_WOOD_STAIRS          = woodBlock("birch_wood_stairs",          p -> new StairBlock(Blocks.BIRCH_WOOD.defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_BIRCH_WOOD_SLAB   = woodBlock("stripped_birch_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_BIRCH_WOOD_STAIRS = woodBlock("stripped_birch_wood_stairs", p -> new StairBlock(Blocks.STRIPPED_BIRCH_WOOD.defaultBlockState(), p));
    public static final DeferredBlock<Block> JUNGLE_WOOD_SLAB            = woodBlock("jungle_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> JUNGLE_WOOD_STAIRS          = woodBlock("jungle_wood_stairs",          p -> new StairBlock(Blocks.JUNGLE_WOOD.defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_JUNGLE_WOOD_SLAB   = woodBlock("stripped_jungle_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_JUNGLE_WOOD_STAIRS = woodBlock("stripped_jungle_wood_stairs", p -> new StairBlock(Blocks.STRIPPED_JUNGLE_WOOD.defaultBlockState(), p));
    public static final DeferredBlock<Block> ACACIA_WOOD_SLAB            = woodBlock("acacia_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> ACACIA_WOOD_STAIRS          = woodBlock("acacia_wood_stairs",          p -> new StairBlock(Blocks.ACACIA_WOOD.defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_ACACIA_WOOD_SLAB   = woodBlock("stripped_acacia_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_ACACIA_WOOD_STAIRS = woodBlock("stripped_acacia_wood_stairs", p -> new StairBlock(Blocks.STRIPPED_ACACIA_WOOD.defaultBlockState(), p));
    public static final DeferredBlock<Block> DARK_OAK_WOOD_SLAB            = woodBlock("dark_oak_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> DARK_OAK_WOOD_STAIRS          = woodBlock("dark_oak_wood_stairs",          p -> new StairBlock(Blocks.DARK_OAK_WOOD.defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_DARK_OAK_WOOD_SLAB   = woodBlock("stripped_dark_oak_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_DARK_OAK_WOOD_STAIRS = woodBlock("stripped_dark_oak_wood_stairs", p -> new StairBlock(Blocks.STRIPPED_DARK_OAK_WOOD.defaultBlockState(), p));
    public static final DeferredBlock<Block> MANGROVE_WOOD_SLAB            = woodBlock("mangrove_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> MANGROVE_WOOD_STAIRS          = woodBlock("mangrove_wood_stairs",          p -> new StairBlock(Blocks.MANGROVE_WOOD.defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_MANGROVE_WOOD_SLAB   = woodBlock("stripped_mangrove_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_MANGROVE_WOOD_STAIRS = woodBlock("stripped_mangrove_wood_stairs", p -> new StairBlock(Blocks.STRIPPED_MANGROVE_WOOD.defaultBlockState(), p));
    public static final DeferredBlock<Block> CHERRY_WOOD_SLAB            = woodBlock("cherry_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> CHERRY_WOOD_STAIRS          = woodBlock("cherry_wood_stairs",          p -> new StairBlock(Blocks.CHERRY_WOOD.defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_CHERRY_WOOD_SLAB   = woodBlock("stripped_cherry_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_CHERRY_WOOD_STAIRS = woodBlock("stripped_cherry_wood_stairs", p -> new StairBlock(Blocks.STRIPPED_CHERRY_WOOD.defaultBlockState(), p));
    public static final DeferredBlock<Block> PALE_OAK_WOOD_SLAB            = woodBlock("pale_oak_wood_slab",            p -> new SlabBlock(p));
    public static final DeferredBlock<Block> PALE_OAK_WOOD_STAIRS          = woodBlock("pale_oak_wood_stairs",          p -> new StairBlock(Blocks.PALE_OAK_WOOD.defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_PALE_OAK_WOOD_SLAB   = woodBlock("stripped_pale_oak_wood_slab",   p -> new SlabBlock(p));
    public static final DeferredBlock<Block> STRIPPED_PALE_OAK_WOOD_STAIRS = woodBlock("stripped_pale_oak_wood_stairs", p -> new StairBlock(Blocks.STRIPPED_PALE_OAK_WOOD.defaultBlockState(), p));

    // ── Saplings ─────────────────────────────────────────────────────────
    public static final DeferredBlock<Block> WEIRWOOD_SAPLING = woodBlock("weirwood_sapling", p -> new GotSaplingBlock(GotTreeGrowers.WEIRWOOD, p));
    public static final DeferredBlock<Block> ASPEN_SAPLING = woodBlock("aspen_sapling", p -> new GotSaplingBlock(GotTreeGrowers.ASPEN, p));
    public static final DeferredBlock<Block> ALDER_SAPLING = woodBlock("alder_sapling", p -> new GotSaplingBlock(GotTreeGrowers.ALDER, p));
    public static final DeferredBlock<Block> PINE_SAPLING = woodBlock("pine_sapling", p -> new GotSaplingBlock(GotTreeGrowers.PINE, p));
    public static final DeferredBlock<Block> FIR_SAPLING = woodBlock("fir_sapling", p -> new GotSaplingBlock(GotTreeGrowers.FIR, p));
    public static final DeferredBlock<Block> SENTINAL_SAPLING = woodBlock("sentinal_sapling", p -> new GotSaplingBlock(GotTreeGrowers.SENTINAL, p));
    public static final DeferredBlock<Block> IRONWOOD_SAPLING = woodBlock("ironwood_sapling", p -> new GotSaplingBlock(GotTreeGrowers.IRONWOOD, p));
    public static final DeferredBlock<Block> BEECH_SAPLING = woodBlock("beech_sapling", p -> new GotSaplingBlock(GotTreeGrowers.BEECH, p));
    public static final DeferredBlock<Block> SOLDIER_PINE_SAPLING = woodBlock("soldier_pine_sapling", p -> new GotSaplingBlock(GotTreeGrowers.SOLDIER_PINE, p));
    public static final DeferredBlock<Block> ASH_SAPLING = woodBlock("ash_sapling", p -> new GotSaplingBlock(GotTreeGrowers.ASH, p));
    public static final DeferredBlock<Block> HAWTHORN_SAPLING = woodBlock("hawthorn_sapling", p -> new GotSaplingBlock(GotTreeGrowers.HAWTHORN, p));
    public static final DeferredBlock<Block> BLACKBARK_SAPLING = woodBlock("blackbark_sapling", p -> new GotSaplingBlock(GotTreeGrowers.BLACKBARK, p));
    public static final DeferredBlock<Block> BLOODWOOD_SAPLING = woodBlock("bloodwood_sapling", p -> new GotSaplingBlock(GotTreeGrowers.BLOODWOOD, p));
    public static final DeferredBlock<Block> BLUE_MAHOE_SAPLING = woodBlock("blue_mahoe_sapling", p -> new GotSaplingBlock(GotTreeGrowers.BLUE_MAHOE, p));
    public static final DeferredBlock<Block> COTTONWOOD_SAPLING = woodBlock("cottonwood_sapling", p -> new GotSaplingBlock(GotTreeGrowers.COTTONWOOD, p));
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_SAPLING = woodBlock("black_cottonwood_sapling", p -> new GotSaplingBlock(GotTreeGrowers.BLACK_COTTONWOOD, p));
    public static final DeferredBlock<Block> CINNAMON_SAPLING = woodBlock("cinnamon_sapling", p -> new GotSaplingBlock(GotTreeGrowers.CINNAMON, p));
    public static final DeferredBlock<Block> CLOVE_SAPLING = woodBlock("clove_sapling", p -> new GotSaplingBlock(GotTreeGrowers.CLOVE, p));
    public static final DeferredBlock<Block> EBONY_SAPLING = woodBlock("ebony_sapling", p -> new GotSaplingBlock(GotTreeGrowers.EBONY, p));
    public static final DeferredBlock<Block> ELM_SAPLING = woodBlock("elm_sapling", p -> new GotSaplingBlock(GotTreeGrowers.ELM, p));
    public static final DeferredBlock<Block> CEDAR_SAPLING = woodBlock("cedar_sapling", p -> new GotSaplingBlock(GotTreeGrowers.CEDAR, p));
    public static final DeferredBlock<Block> APPLE_SAPLING = woodBlock("apple_sapling", p -> new GotSaplingBlock(GotTreeGrowers.APPLE, p));
    public static final DeferredBlock<Block> GOLDENHEART_SAPLING = woodBlock("goldenheart_sapling", p -> new GotSaplingBlock(GotTreeGrowers.GOLDENHEART, p));
    public static final DeferredBlock<Block> LINDEN_SAPLING = woodBlock("linden_sapling", p -> new GotSaplingBlock(GotTreeGrowers.LINDEN, p));
    public static final DeferredBlock<Block> MAHOGANY_SAPLING = woodBlock("mahogany_sapling", p -> new GotSaplingBlock(GotTreeGrowers.MAHOGANY, p));
    public static final DeferredBlock<Block> MAPLE_SAPLING = woodBlock("maple_sapling", p -> new GotSaplingBlock(GotTreeGrowers.MAPLE, p));
    public static final DeferredBlock<Block> MYRRH_SAPLING = woodBlock("myrrh_sapling", p -> new GotSaplingBlock(GotTreeGrowers.MYRRH, p));
    public static final DeferredBlock<Block> REDWOOD_SAPLING = woodBlock("redwood_sapling", p -> new GotSaplingBlock(GotTreeGrowers.REDWOOD, p));
    public static final DeferredBlock<Block> CHESTNUT_SAPLING = woodBlock("chestnut_sapling", p -> new GotSaplingBlock(GotTreeGrowers.CHESTNUT, p));
    public static final DeferredBlock<Block> WILLOW_SAPLING = woodBlock("willow_sapling", p -> new GotSaplingBlock(GotTreeGrowers.WILLOW, p));
    public static final DeferredBlock<Block> WORMTREE_SAPLING = woodBlock("wormtree_sapling", p -> new GotSaplingBlock(GotTreeGrowers.WORMTREE, p));

    // ── Wood Shingles ─────────────────────────────────────────────────────────
    // Block, Slab, Stairs, Wall for every custom and vanilla wood type
    // Paste this block into GotModBlocks.java

    public static final DeferredBlock<Block> ALDER_ROOFING =
            REGISTRY.registerBlock("alder_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> ALDER_ROOFING_SLAB =
            REGISTRY.registerBlock("alder_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> ALDER_ROOFING_STAIRS =
            REGISTRY.registerBlock("alder_roofing_stairs",
                    p -> new StairBlock(ALDER_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> ALDER_ROOFING_WALL =
            REGISTRY.registerBlock("alder_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> APPLE_ROOFING =
            REGISTRY.registerBlock("apple_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> APPLE_ROOFING_SLAB =
            REGISTRY.registerBlock("apple_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> APPLE_ROOFING_STAIRS =
            REGISTRY.registerBlock("apple_roofing_stairs",
                    p -> new StairBlock(APPLE_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> APPLE_ROOFING_WALL =
            REGISTRY.registerBlock("apple_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> ASH_ROOFING =
            REGISTRY.registerBlock("ash_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> ASH_ROOFING_SLAB =
            REGISTRY.registerBlock("ash_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> ASH_ROOFING_STAIRS =
            REGISTRY.registerBlock("ash_roofing_stairs",
                    p -> new StairBlock(ASH_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> ASH_ROOFING_WALL =
            REGISTRY.registerBlock("ash_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> ASPEN_ROOFING =
            REGISTRY.registerBlock("aspen_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> ASPEN_ROOFING_SLAB =
            REGISTRY.registerBlock("aspen_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> ASPEN_ROOFING_STAIRS =
            REGISTRY.registerBlock("aspen_roofing_stairs",
                    p -> new StairBlock(ASPEN_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> ASPEN_ROOFING_WALL =
            REGISTRY.registerBlock("aspen_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> BEECH_ROOFING =
            REGISTRY.registerBlock("beech_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> BEECH_ROOFING_SLAB =
            REGISTRY.registerBlock("beech_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> BEECH_ROOFING_STAIRS =
            REGISTRY.registerBlock("beech_roofing_stairs",
                    p -> new StairBlock(BEECH_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> BEECH_ROOFING_WALL =
            REGISTRY.registerBlock("beech_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> BLACK_COTTONWOOD_ROOFING =
            REGISTRY.registerBlock("black_cottonwood_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_ROOFING_SLAB =
            REGISTRY.registerBlock("black_cottonwood_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_ROOFING_STAIRS =
            REGISTRY.registerBlock("black_cottonwood_roofing_stairs",
                    p -> new StairBlock(BLACK_COTTONWOOD_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_ROOFING_WALL =
            REGISTRY.registerBlock("black_cottonwood_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> BLACKBARK_ROOFING =
            REGISTRY.registerBlock("blackbark_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> BLACKBARK_ROOFING_SLAB =
            REGISTRY.registerBlock("blackbark_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> BLACKBARK_ROOFING_STAIRS =
            REGISTRY.registerBlock("blackbark_roofing_stairs",
                    p -> new StairBlock(BLACKBARK_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> BLACKBARK_ROOFING_WALL =
            REGISTRY.registerBlock("blackbark_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> BLOODWOOD_ROOFING =
            REGISTRY.registerBlock("bloodwood_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> BLOODWOOD_ROOFING_SLAB =
            REGISTRY.registerBlock("bloodwood_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> BLOODWOOD_ROOFING_STAIRS =
            REGISTRY.registerBlock("bloodwood_roofing_stairs",
                    p -> new StairBlock(BLOODWOOD_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> BLOODWOOD_ROOFING_WALL =
            REGISTRY.registerBlock("bloodwood_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> BLUE_MAHOE_ROOFING =
            REGISTRY.registerBlock("blue_mahoe_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> BLUE_MAHOE_ROOFING_SLAB =
            REGISTRY.registerBlock("blue_mahoe_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> BLUE_MAHOE_ROOFING_STAIRS =
            REGISTRY.registerBlock("blue_mahoe_roofing_stairs",
                    p -> new StairBlock(BLUE_MAHOE_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> BLUE_MAHOE_ROOFING_WALL =
            REGISTRY.registerBlock("blue_mahoe_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> CEDAR_ROOFING =
            REGISTRY.registerBlock("cedar_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> CEDAR_ROOFING_SLAB =
            REGISTRY.registerBlock("cedar_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> CEDAR_ROOFING_STAIRS =
            REGISTRY.registerBlock("cedar_roofing_stairs",
                    p -> new StairBlock(CEDAR_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> CEDAR_ROOFING_WALL =
            REGISTRY.registerBlock("cedar_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> CHESTNUT_ROOFING =
            REGISTRY.registerBlock("chestnut_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> CHESTNUT_ROOFING_SLAB =
            REGISTRY.registerBlock("chestnut_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> CHESTNUT_ROOFING_STAIRS =
            REGISTRY.registerBlock("chestnut_roofing_stairs",
                    p -> new StairBlock(CHESTNUT_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> CHESTNUT_ROOFING_WALL =
            REGISTRY.registerBlock("chestnut_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> CINNAMON_ROOFING =
            REGISTRY.registerBlock("cinnamon_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> CINNAMON_ROOFING_SLAB =
            REGISTRY.registerBlock("cinnamon_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> CINNAMON_ROOFING_STAIRS =
            REGISTRY.registerBlock("cinnamon_roofing_stairs",
                    p -> new StairBlock(CINNAMON_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> CINNAMON_ROOFING_WALL =
            REGISTRY.registerBlock("cinnamon_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> CLOVE_ROOFING =
            REGISTRY.registerBlock("clove_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> CLOVE_ROOFING_SLAB =
            REGISTRY.registerBlock("clove_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> CLOVE_ROOFING_STAIRS =
            REGISTRY.registerBlock("clove_roofing_stairs",
                    p -> new StairBlock(CLOVE_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> CLOVE_ROOFING_WALL =
            REGISTRY.registerBlock("clove_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> COTTONWOOD_ROOFING =
            REGISTRY.registerBlock("cottonwood_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> COTTONWOOD_ROOFING_SLAB =
            REGISTRY.registerBlock("cottonwood_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> COTTONWOOD_ROOFING_STAIRS =
            REGISTRY.registerBlock("cottonwood_roofing_stairs",
                    p -> new StairBlock(COTTONWOOD_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> COTTONWOOD_ROOFING_WALL =
            REGISTRY.registerBlock("cottonwood_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> EBONY_ROOFING =
            REGISTRY.registerBlock("ebony_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> EBONY_ROOFING_SLAB =
            REGISTRY.registerBlock("ebony_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> EBONY_ROOFING_STAIRS =
            REGISTRY.registerBlock("ebony_roofing_stairs",
                    p -> new StairBlock(EBONY_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> EBONY_ROOFING_WALL =
            REGISTRY.registerBlock("ebony_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> ELM_ROOFING =
            REGISTRY.registerBlock("elm_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> ELM_ROOFING_SLAB =
            REGISTRY.registerBlock("elm_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> ELM_ROOFING_STAIRS =
            REGISTRY.registerBlock("elm_roofing_stairs",
                    p -> new StairBlock(ELM_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> ELM_ROOFING_WALL =
            REGISTRY.registerBlock("elm_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> FIR_ROOFING =
            REGISTRY.registerBlock("fir_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> FIR_ROOFING_SLAB =
            REGISTRY.registerBlock("fir_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> FIR_ROOFING_STAIRS =
            REGISTRY.registerBlock("fir_roofing_stairs",
                    p -> new StairBlock(FIR_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> FIR_ROOFING_WALL =
            REGISTRY.registerBlock("fir_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> GOLDENHEART_ROOFING =
            REGISTRY.registerBlock("goldenheart_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> GOLDENHEART_ROOFING_SLAB =
            REGISTRY.registerBlock("goldenheart_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> GOLDENHEART_ROOFING_STAIRS =
            REGISTRY.registerBlock("goldenheart_roofing_stairs",
                    p -> new StairBlock(GOLDENHEART_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> GOLDENHEART_ROOFING_WALL =
            REGISTRY.registerBlock("goldenheart_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> HAWTHORN_ROOFING =
            REGISTRY.registerBlock("hawthorn_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> HAWTHORN_ROOFING_SLAB =
            REGISTRY.registerBlock("hawthorn_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> HAWTHORN_ROOFING_STAIRS =
            REGISTRY.registerBlock("hawthorn_roofing_stairs",
                    p -> new StairBlock(HAWTHORN_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> HAWTHORN_ROOFING_WALL =
            REGISTRY.registerBlock("hawthorn_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> IRONWOOD_ROOFING =
            REGISTRY.registerBlock("ironwood_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> IRONWOOD_ROOFING_SLAB =
            REGISTRY.registerBlock("ironwood_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> IRONWOOD_ROOFING_STAIRS =
            REGISTRY.registerBlock("ironwood_roofing_stairs",
                    p -> new StairBlock(IRONWOOD_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> IRONWOOD_ROOFING_WALL =
            REGISTRY.registerBlock("ironwood_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> LINDEN_ROOFING =
            REGISTRY.registerBlock("linden_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> LINDEN_ROOFING_SLAB =
            REGISTRY.registerBlock("linden_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> LINDEN_ROOFING_STAIRS =
            REGISTRY.registerBlock("linden_roofing_stairs",
                    p -> new StairBlock(LINDEN_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> LINDEN_ROOFING_WALL =
            REGISTRY.registerBlock("linden_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> MAHOGANY_ROOFING =
            REGISTRY.registerBlock("mahogany_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> MAHOGANY_ROOFING_SLAB =
            REGISTRY.registerBlock("mahogany_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> MAHOGANY_ROOFING_STAIRS =
            REGISTRY.registerBlock("mahogany_roofing_stairs",
                    p -> new StairBlock(MAHOGANY_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> MAHOGANY_ROOFING_WALL =
            REGISTRY.registerBlock("mahogany_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> MAPLE_ROOFING =
            REGISTRY.registerBlock("maple_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> MAPLE_ROOFING_SLAB =
            REGISTRY.registerBlock("maple_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> MAPLE_ROOFING_STAIRS =
            REGISTRY.registerBlock("maple_roofing_stairs",
                    p -> new StairBlock(MAPLE_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> MAPLE_ROOFING_WALL =
            REGISTRY.registerBlock("maple_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> MYRRH_ROOFING =
            REGISTRY.registerBlock("myrrh_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> MYRRH_ROOFING_SLAB =
            REGISTRY.registerBlock("myrrh_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> MYRRH_ROOFING_STAIRS =
            REGISTRY.registerBlock("myrrh_roofing_stairs",
                    p -> new StairBlock(MYRRH_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> MYRRH_ROOFING_WALL =
            REGISTRY.registerBlock("myrrh_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> PINE_ROOFING =
            REGISTRY.registerBlock("pine_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> PINE_ROOFING_SLAB =
            REGISTRY.registerBlock("pine_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> PINE_ROOFING_STAIRS =
            REGISTRY.registerBlock("pine_roofing_stairs",
                    p -> new StairBlock(PINE_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> PINE_ROOFING_WALL =
            REGISTRY.registerBlock("pine_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> REDWOOD_ROOFING =
            REGISTRY.registerBlock("redwood_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> REDWOOD_ROOFING_SLAB =
            REGISTRY.registerBlock("redwood_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> REDWOOD_ROOFING_STAIRS =
            REGISTRY.registerBlock("redwood_roofing_stairs",
                    p -> new StairBlock(REDWOOD_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> REDWOOD_ROOFING_WALL =
            REGISTRY.registerBlock("redwood_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> SENTINAL_ROOFING =
            REGISTRY.registerBlock("sentinal_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> SENTINAL_ROOFING_SLAB =
            REGISTRY.registerBlock("sentinal_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> SENTINAL_ROOFING_STAIRS =
            REGISTRY.registerBlock("sentinal_roofing_stairs",
                    p -> new StairBlock(SENTINAL_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> SENTINAL_ROOFING_WALL =
            REGISTRY.registerBlock("sentinal_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> SOLDIER_PINE_ROOFING =
            REGISTRY.registerBlock("soldier_pine_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> SOLDIER_PINE_ROOFING_SLAB =
            REGISTRY.registerBlock("soldier_pine_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> SOLDIER_PINE_ROOFING_STAIRS =
            REGISTRY.registerBlock("soldier_pine_roofing_stairs",
                    p -> new StairBlock(SOLDIER_PINE_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> SOLDIER_PINE_ROOFING_WALL =
            REGISTRY.registerBlock("soldier_pine_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> WEIRWOOD_ROOFING =
            REGISTRY.registerBlock("weirwood_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> WEIRWOOD_ROOFING_SLAB =
            REGISTRY.registerBlock("weirwood_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> WEIRWOOD_ROOFING_STAIRS =
            REGISTRY.registerBlock("weirwood_roofing_stairs",
                    p -> new StairBlock(WEIRWOOD_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> WEIRWOOD_ROOFING_WALL =
            REGISTRY.registerBlock("weirwood_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> WILLOW_ROOFING =
            REGISTRY.registerBlock("willow_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> WILLOW_ROOFING_SLAB =
            REGISTRY.registerBlock("willow_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> WILLOW_ROOFING_STAIRS =
            REGISTRY.registerBlock("willow_roofing_stairs",
                    p -> new StairBlock(WILLOW_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> WILLOW_ROOFING_WALL =
            REGISTRY.registerBlock("willow_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> WORMTREE_ROOFING =
            REGISTRY.registerBlock("wormtree_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> WORMTREE_ROOFING_SLAB =
            REGISTRY.registerBlock("wormtree_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> WORMTREE_ROOFING_STAIRS =
            REGISTRY.registerBlock("wormtree_roofing_stairs",
                    p -> new StairBlock(WORMTREE_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> WORMTREE_ROOFING_WALL =
            REGISTRY.registerBlock("wormtree_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> OAK_ROOFING =
            REGISTRY.registerBlock("oak_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> OAK_ROOFING_SLAB =
            REGISTRY.registerBlock("oak_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> OAK_ROOFING_STAIRS =
            REGISTRY.registerBlock("oak_roofing_stairs",
                    p -> new StairBlock(OAK_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> OAK_ROOFING_WALL =
            REGISTRY.registerBlock("oak_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> SPRUCE_ROOFING =
            REGISTRY.registerBlock("spruce_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> SPRUCE_ROOFING_SLAB =
            REGISTRY.registerBlock("spruce_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> SPRUCE_ROOFING_STAIRS =
            REGISTRY.registerBlock("spruce_roofing_stairs",
                    p -> new StairBlock(SPRUCE_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> SPRUCE_ROOFING_WALL =
            REGISTRY.registerBlock("spruce_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> BIRCH_ROOFING =
            REGISTRY.registerBlock("birch_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> BIRCH_ROOFING_SLAB =
            REGISTRY.registerBlock("birch_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> BIRCH_ROOFING_STAIRS =
            REGISTRY.registerBlock("birch_roofing_stairs",
                    p -> new StairBlock(BIRCH_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> BIRCH_ROOFING_WALL =
            REGISTRY.registerBlock("birch_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> JUNGLE_ROOFING =
            REGISTRY.registerBlock("jungle_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> JUNGLE_ROOFING_SLAB =
            REGISTRY.registerBlock("jungle_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> JUNGLE_ROOFING_STAIRS =
            REGISTRY.registerBlock("jungle_roofing_stairs",
                    p -> new StairBlock(JUNGLE_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> JUNGLE_ROOFING_WALL =
            REGISTRY.registerBlock("jungle_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> ACACIA_ROOFING =
            REGISTRY.registerBlock("acacia_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> ACACIA_ROOFING_SLAB =
            REGISTRY.registerBlock("acacia_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> ACACIA_ROOFING_STAIRS =
            REGISTRY.registerBlock("acacia_roofing_stairs",
                    p -> new StairBlock(ACACIA_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> ACACIA_ROOFING_WALL =
            REGISTRY.registerBlock("acacia_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> DARK_OAK_ROOFING =
            REGISTRY.registerBlock("dark_oak_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> DARK_OAK_ROOFING_SLAB =
            REGISTRY.registerBlock("dark_oak_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> DARK_OAK_ROOFING_STAIRS =
            REGISTRY.registerBlock("dark_oak_roofing_stairs",
                    p -> new StairBlock(DARK_OAK_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> DARK_OAK_ROOFING_WALL =
            REGISTRY.registerBlock("dark_oak_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> MANGROVE_ROOFING =
            REGISTRY.registerBlock("mangrove_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> MANGROVE_ROOFING_SLAB =
            REGISTRY.registerBlock("mangrove_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> MANGROVE_ROOFING_STAIRS =
            REGISTRY.registerBlock("mangrove_roofing_stairs",
                    p -> new StairBlock(MANGROVE_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> MANGROVE_ROOFING_WALL =
            REGISTRY.registerBlock("mangrove_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> CHERRY_ROOFING =
            REGISTRY.registerBlock("cherry_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> CHERRY_ROOFING_SLAB =
            REGISTRY.registerBlock("cherry_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> CHERRY_ROOFING_STAIRS =
            REGISTRY.registerBlock("cherry_roofing_stairs",
                    p -> new StairBlock(CHERRY_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> CHERRY_ROOFING_WALL =
            REGISTRY.registerBlock("cherry_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> RED_CHERRY_ROOFING =
            REGISTRY.registerBlock("red_cherry_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> RED_CHERRY_ROOFING_SLAB =
            REGISTRY.registerBlock("red_cherry_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> RED_CHERRY_ROOFING_STAIRS =
            REGISTRY.registerBlock("red_cherry_roofing_stairs",
                    p -> new StairBlock(RED_CHERRY_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> RED_CHERRY_ROOFING_WALL =
            REGISTRY.registerBlock("red_cherry_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> BLACK_CHERRY_ROOFING =
            REGISTRY.registerBlock("black_cherry_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> BLACK_CHERRY_ROOFING_SLAB =
            REGISTRY.registerBlock("black_cherry_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> BLACK_CHERRY_ROOFING_STAIRS =
            REGISTRY.registerBlock("black_cherry_roofing_stairs",
                    p -> new StairBlock(BLACK_CHERRY_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> BLACK_CHERRY_ROOFING_WALL =
            REGISTRY.registerBlock("black_cherry_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> WHITE_CHERRY_ROOFING =
            REGISTRY.registerBlock("white_cherry_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> WHITE_CHERRY_ROOFING_SLAB =
            REGISTRY.registerBlock("white_cherry_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> WHITE_CHERRY_ROOFING_STAIRS =
            REGISTRY.registerBlock("white_cherry_roofing_stairs",
                    p -> new StairBlock(WHITE_CHERRY_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> WHITE_CHERRY_ROOFING_WALL =
            REGISTRY.registerBlock("white_cherry_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<Block> BAMBOO_ROOFING =
            REGISTRY.registerBlock("bamboo_roofing", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> BAMBOO_ROOFING_SLAB =
            REGISTRY.registerBlock("bamboo_roofing_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<Block> BAMBOO_ROOFING_STAIRS =
            REGISTRY.registerBlock("bamboo_roofing_stairs",
                    p -> new StairBlock(BAMBOO_ROOFING.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<Block> BAMBOO_ROOFING_WALL =
            REGISTRY.registerBlock("bamboo_roofing_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    // ── Flowers ──────────────────────────────────────────────────────────
    public static final DeferredBlock<Block> BELLFLOWER         = flowerBlock("bellflower");
    public static final DeferredBlock<Block> BLACK_LOTUS        = flowerBlock("black_lotus");
    public static final DeferredBlock<Block> BLOOD_BLOOM        = flowerBlock("blood_bloom");
    public static final DeferredBlock<Block> COLDSNAPS          = flowerBlock("coldsnaps");
    public static final DeferredBlock<Block> DRAGONS_BREATH     = flowerBlock("dragons_breath");
    public static final DeferredBlock<Block> EVENING_STAR       = flowerBlock("evening_star");
    public static final DeferredBlock<Block> FORGET_ME_NOT      = flowerBlock("forget_me_not");
    public static final DeferredBlock<Block> FROSTFIRES         = flowerBlock("frostfires");
    public static final DeferredBlock<Block> GILLYFLOWER        = flowerBlock("gillyflower");
    public static final DeferredBlock<Block> GINGER             = flowerBlock("ginger");
    public static final DeferredBlock<Block> GOATHEAD           = flowerBlock("goathead");
    public static final DeferredBlock<Block> GOLDENCUP          = flowerBlock("goldencup");
    public static final DeferredBlock<Block> GOLDENROD          = flowerBlock("goldenrod");
    public static final DeferredBlock<Block> GORSE              = flowerBlock("gorse");
    public static final DeferredBlock<Block> LADYS_LACE         = flowerBlock("ladys_lace");
    public static final DeferredBlock<Block> LAVENDER           = flowerBlock("lavender");
    public static final DeferredBlock<Block> LIVERWORT          = flowerBlock("liverwort");
    public static final DeferredBlock<Block> LUNGWORT           = flowerBlock("lungwort");
    public static final DeferredBlock<Block> MOONBLOOM          = flowerBlock("moonbloom");
    public static final DeferredBlock<Block> NIGHTSHADE         = flowerBlock("nightshade");
    public static final DeferredBlock<Block> PENNYROYAL         = flowerBlock("pennyroyal");
    public static final DeferredBlock<Block> POISON_KISSES      = flowerBlock("poison_kisses");
    public static final DeferredBlock<Block> THORNBUSH          = flowerBlock("thornbush");
    public static final DeferredBlock<Block> OPIUM_POPPY        = flowerBlock("opium_poppy");
    public static final DeferredBlock<Block> GOLDEN_ROSE        = flowerBlock("golden_rose");
    public static final DeferredBlock<Block> RED_ROSE           = flowerBlock("red_rose");
    public static final DeferredBlock<Block> WHITE_ROSE         = flowerBlock("white_rose");
    public static final DeferredBlock<Block> WINTER_ROSE        = flowerBlock("winter_rose");
    public static final DeferredBlock<Block> SAFFRON_CROCUS     = flowerBlock("saffron_crocus");
    public static final DeferredBlock<Block> SEDGE              = flowerBlock("sedge");
    public static final DeferredBlock<Block> SPICEFLOWER        = flowerBlock("spiceflower");
    public static final DeferredBlock<Block> TANSY              = flowerBlock("tansy");
    public static final DeferredBlock<Block> THISTLE            = flowerBlock("thistle");
    public static final DeferredBlock<Block> WILD_RADISH        = flowerBlock("wild_radish");

    public static final DeferredBlock<Block> RED_ROSE_BUSH =
            REGISTRY.registerBlock("red_rose_bush",
                    RoseBushBlock::new,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.ROSE_BUSH));

    public static final DeferredBlock<Block> GOLDEN_ROSE_BUSH =
            REGISTRY.registerBlock("golden_rose_bush",
                    RoseBushBlock::new,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.ROSE_BUSH));

    public static final DeferredBlock<Block> WHITE_ROSE_BUSH =
            REGISTRY.registerBlock("white_rose_bush",
                    RoseBushBlock::new,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.ROSE_BUSH));

    public static final DeferredBlock<Block> WINTER_ROSE_BUSH =
            REGISTRY.registerBlock("winter_rose_bush",
                    RoseBushBlock::new,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.ROSE_BUSH));



    // ── Grasses ──────────────────────────────────────────────────────────
    public static final DeferredBlock<Block> DEVILGRASS = REGISTRY.registerBlock("devilgrass",
            GotPlantBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS));
    public static final DeferredBlock<Block> GHOST_GRASS = REGISTRY.registerBlock("ghost_grass",
            GhostGrassBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.TALL_GRASS).noOcclusion().randomTicks());
    public static final DeferredBlock<Block> HRANNA             = flowerBlock("hranna");
    public static final DeferredBlock<Block> PIPERS_GRASS = REGISTRY.registerBlock("pipers_grass",
            GotPlantBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS));
    public static final DeferredBlock<Block> WHEATGRASS = REGISTRY.registerBlock("wheatgrass",
            GotPlantBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS));


    // ── Wild Crops (naturally spawning, drop seeds/produce when broken) ─────
    public static final DeferredBlock<Block> WILD_WHEAT = flowerBlock("wild_wheat");
    public static final DeferredBlock<Block> WILD_OAT = flowerBlock("wild_oat");
    public static final DeferredBlock<Block> WILD_RYE = flowerBlock("wild_rye");
    public static final DeferredBlock<Block> WILD_BARLEY = flowerBlock("wild_barley");
    public static final DeferredBlock<Block> WILD_BEETROOT = flowerBlock("wild_beetroot");
    public static final DeferredBlock<Block> WILD_COTTON = flowerBlock("wild_cotton");
    public static final DeferredBlock<Block> WILD_PEPPERCORN = flowerBlock("wild_peppercorn");

    public static final DeferredBlock<Block> WILD_CARROT = flowerBlock("wild_carrot");
    public static final DeferredBlock<Block> WILD_PARSNIP = flowerBlock("wild_parsnip");
    public static final DeferredBlock<Block> WILD_ONION = flowerBlock("wild_onion");
    public static final DeferredBlock<Block> WILD_TURNIP = flowerBlock("wild_turnip");
    public static final DeferredBlock<Block> WILD_NEEP = flowerBlock("wild_neep");
    public static final DeferredBlock<Block> WILD_PEAS = flowerBlock("wild_peas");
    public static final DeferredBlock<Block> WILD_CABBAGE = flowerBlock("wild_cabbage");
    public static final DeferredBlock<Block> WILD_GARLIC = flowerBlock("wild_garlic");
    public static final DeferredBlock<Block> WILD_HORSERADISH = flowerBlock("wild_horseradish");
    public static final DeferredBlock<Block> WILD_LEEK = flowerBlock("wild_leek");

    // ── Miscellaneous ASOIAF Plants (not yet in mod) ─────────────────────
    public static final DeferredBlock<Block> WILD_BEAN         = flowerBlock("wild_bean");

    public static final DeferredBlock<Block> BRACKEN           = flowerBlock("bracken");
    public static final DeferredBlock<Block> BRIAR             = flowerBlock("briar");
    public static final DeferredBlock<Block> BROOM             = flowerBlock("broom");

    public static final DeferredBlock<Block> WILD_CARDAMOM     = flowerBlock("wild_cardamom");
    public static final DeferredBlock<Block> WILD_CHICKPEA     = flowerBlock("wild_chickpea");

    public static final DeferredBlock<Block> WILD_CUCUMBER     = flowerBlock("wild_cucumber");
    public static final DeferredBlock<Block> DAGGERLEAF        = flowerBlock("daggerleaf");
    public static final DeferredBlock<Block> FIREPOD           = flowerBlock("firepod");
    public static final DeferredBlock<Block> GHOSTSKIN         = REGISTRY.registerBlock("ghostskin",
            GhostskinBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.PALE_HANGING_MOSS));
    public static final DeferredBlock<Block> GRAPE_VINE        = flowerBlock("grape_vine");
    public static final DeferredBlock<Block> HARPYS_GOLD       = flowerBlock("harpys_gold");
    public static final DeferredBlock<Block> WILD_HEMP         = tallFlowerBlock("wild_hemp");
    public static final DeferredBlock<Block> HORNWORT          = flowerBlock("hornwort");
    public static final DeferredBlock<Block> IVY               =
            REGISTRY.registerBlock("ivy", VineBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.VINE));
    public static final DeferredBlock<Block> KINGSCOPPER       = flowerBlock("kingscopper");
    public static final DeferredBlock<Block> WILD_LICORICE     = flowerBlock("wild_licorice");
    public static final DeferredBlock<Block> MISTLETOE         = flowerBlock("mistletoe");
    public static final DeferredBlock<Block> WILD_MUSTARD_PLANT = flowerBlock("wild_mustard_plant");
    public static final DeferredBlock<Block> NETTLE            = flowerBlock("nettle");
    public static final DeferredBlock<Block> WILD_PEPPER_PLANT  = flowerBlock("wild_pepper_plant");
    public static final DeferredBlock<Block> PINCHFIRE         = flowerBlock("pinchfire");
    public static final DeferredBlock<Block> PRICKLY_BEN       = flowerBlock("prickly_ben");
    public static final DeferredBlock<Block> SANDWILLOW        = flowerBlock("sandwillow");
    public static final DeferredBlock<Block> SMOKEBERRY_BUSH   = berryBushBlock("smokeberry_bush",   () -> GotModItems.SMOKEBERRIES.get());
    public static final DeferredBlock<Block> SOURLEAF          = flowerBlock("sourleaf");
    public static final DeferredBlock<Block> STING_ME_NOT      = flowerBlock("sting_me_not");
    public static final DeferredBlock<Block> WASPWILLOW        = flowerBlock("waspwillow");

    // ── Potted variants ──────────────────────────────────────────────────
    public static final DeferredBlock<Block> POTTED_WILD_BEAN           = pottedBlock("potted_wild_bean",       () -> WILD_BEAN);

    public static final DeferredBlock<Block> POTTED_BRACKEN        = pottedBlock("potted_bracken",        () -> BRACKEN);
    public static final DeferredBlock<Block> POTTED_BRIAR          = pottedBlock("potted_briar",          () -> BRIAR);
    public static final DeferredBlock<Block> POTTED_BROOM          = pottedBlock("potted_broom",          () -> BROOM);

    public static final DeferredBlock<Block> POTTED_WILD_CARDAMOM       = pottedBlock("potted_wild_cardamom",  () -> WILD_CARDAMOM);
    public static final DeferredBlock<Block> POTTED_WILD_CHICKPEA       = pottedBlock("potted_wild_chickpea",  () -> WILD_CHICKPEA);

    public static final DeferredBlock<Block> POTTED_WILD_CUCUMBER       = pottedBlock("potted_wild_cucumber",  () -> WILD_CUCUMBER);
    public static final DeferredBlock<Block> POTTED_DAGGERLEAF     = pottedBlock("potted_daggerleaf",     () -> DAGGERLEAF);
    public static final DeferredBlock<Block> POTTED_FIREPOD        = pottedBlock("potted_firepod",        () -> FIREPOD);
    public static final DeferredBlock<Block> POTTED_GHOSTSKIN      = pottedBlock("potted_ghostskin",      () -> GHOSTSKIN);
    public static final DeferredBlock<Block> POTTED_GRAPE_VINE     = pottedBlock("potted_grape_vine",     () -> GRAPE_VINE);
    public static final DeferredBlock<Block> POTTED_HARPYS_GOLD    = pottedBlock("potted_harpys_gold",    () -> HARPYS_GOLD);
    public static final DeferredBlock<Block> POTTED_HORNWORT       = pottedBlock("potted_hornwort",       () -> HORNWORT);
    public static final DeferredBlock<Block> POTTED_IVY            = pottedBlock("potted_ivy",            () -> IVY);
    public static final DeferredBlock<Block> POTTED_KINGSCOPPER    = pottedBlock("potted_kingscopper",    () -> KINGSCOPPER);
    public static final DeferredBlock<Block> POTTED_WILD_LICORICE       = pottedBlock("potted_wild_licorice",  () -> WILD_LICORICE);
    public static final DeferredBlock<Block> POTTED_MISTLETOE      = pottedBlock("potted_mistletoe",      () -> MISTLETOE);
    public static final DeferredBlock<Block> POTTED_WILD_MUSTARD_PLANT  = pottedBlock("potted_wild_mustard_plant",  () -> WILD_MUSTARD_PLANT);
    public static final DeferredBlock<Block> POTTED_NETTLE         = pottedBlock("potted_nettle",         () -> NETTLE);
    public static final DeferredBlock<Block> POTTED_WILD_PEPPER_PLANT   = pottedBlock("potted_wild_pepper_plant",   () -> WILD_PEPPER_PLANT);
    public static final DeferredBlock<Block> POTTED_PINCHFIRE      = pottedBlock("potted_pinchfire",      () -> PINCHFIRE);
    public static final DeferredBlock<Block> POTTED_PRICKLY_BEN    = pottedBlock("potted_prickly_ben",    () -> PRICKLY_BEN);
    public static final DeferredBlock<Block> POTTED_SANDWILLOW     = pottedBlock("potted_sandwillow",     () -> SANDWILLOW);

    public static final DeferredBlock<Block> POTTED_SOURLEAF       = pottedBlock("potted_sourleaf",       () -> SOURLEAF);
    public static final DeferredBlock<Block> POTTED_STING_ME_NOT   = pottedBlock("potted_sting_me_not",   () -> STING_ME_NOT);
    public static final DeferredBlock<Block> POTTED_WASPWILLOW     = pottedBlock("potted_waspwillow",     () -> WASPWILLOW);

    // ── Potted versions ──────────────────────────────────────────────────────
    public static final DeferredBlock<Block> POTTED_BELLFLOWER         = pottedBlock("potted_bellflower",         () -> BELLFLOWER);
    public static final DeferredBlock<Block> POTTED_BLACK_LOTUS        = pottedBlock("potted_black_lotus",        () -> BLACK_LOTUS);
    public static final DeferredBlock<Block> POTTED_BLOOD_BLOOM        = pottedBlock("potted_blood_bloom",        () -> BLOOD_BLOOM);
    public static final DeferredBlock<Block> POTTED_COLDSNAPS          = pottedBlock("potted_coldsnaps",          () -> COLDSNAPS);
    public static final DeferredBlock<Block> POTTED_DRAGONS_BREATH     = pottedBlock("potted_dragons_breath",     () -> DRAGONS_BREATH);
    public static final DeferredBlock<Block> POTTED_EVENING_STAR       = pottedBlock("potted_evening_star",       () -> EVENING_STAR);
    public static final DeferredBlock<Block> POTTED_FORGET_ME_NOT      = pottedBlock("potted_forget_me_not",      () -> FORGET_ME_NOT);
    public static final DeferredBlock<Block> POTTED_FROSTFIRES         = pottedBlock("potted_frostfires",         () -> FROSTFIRES);
    public static final DeferredBlock<Block> POTTED_GILLYFLOWER        = pottedBlock("potted_gillyflower",        () -> GILLYFLOWER);
    public static final DeferredBlock<Block> POTTED_GINGER             = pottedBlock("potted_ginger",             () -> GINGER);
    public static final DeferredBlock<Block> POTTED_GOATHEAD           = pottedBlock("potted_goathead",           () -> GOATHEAD);
    public static final DeferredBlock<Block> POTTED_GOLDENCUP          = pottedBlock("potted_goldencup",          () -> GOLDENCUP);
    public static final DeferredBlock<Block> POTTED_GOLDENROD          = pottedBlock("potted_goldenrod",          () -> GOLDENROD);
    public static final DeferredBlock<Block> POTTED_GORSE              = pottedBlock("potted_gorse",              () -> GORSE);
    public static final DeferredBlock<Block> POTTED_LADYS_LACE         = pottedBlock("potted_ladys_lace",         () -> LADYS_LACE);
    public static final DeferredBlock<Block> POTTED_LAVENDER           = pottedBlock("potted_lavender",           () -> LAVENDER);
    public static final DeferredBlock<Block> POTTED_LIVERWORT          = pottedBlock("potted_liverwort",          () -> LIVERWORT);
    public static final DeferredBlock<Block> POTTED_LUNGWORT           = pottedBlock("potted_lungwort",           () -> LUNGWORT);
    public static final DeferredBlock<Block> POTTED_MOONBLOOM          = pottedBlock("potted_moonbloom",          () -> MOONBLOOM);
    public static final DeferredBlock<Block> POTTED_NIGHTSHADE         = pottedBlock("potted_nightshade",         () -> NIGHTSHADE);
    public static final DeferredBlock<Block> POTTED_PENNYROYAL         = pottedBlock("potted_pennyroyal",         () -> PENNYROYAL);
    public static final DeferredBlock<Block> POTTED_POISON_KISSES      = pottedBlock("potted_poison_kisses",      () -> POISON_KISSES);
    public static final DeferredBlock<Block> POTTED_THORNBUSH          = pottedBlock("potted_thornbush",          () -> THORNBUSH);
    public static final DeferredBlock<Block> POTTED_OPIUM_POPPY        = pottedBlock("potted_opium_poppy",        () -> OPIUM_POPPY);
    public static final DeferredBlock<Block> POTTED_GOLDEN_ROSE        = pottedBlock("potted_golden_rose",        () -> GOLDEN_ROSE);
    public static final DeferredBlock<Block> POTTED_RED_ROSE           = pottedBlock("potted_red_rose",           () -> RED_ROSE);
    public static final DeferredBlock<Block> POTTED_WHITE_ROSE         = pottedBlock("potted_white_rose",         () -> WHITE_ROSE);
    public static final DeferredBlock<Block> POTTED_WINTER_ROSE        = pottedBlock("potted_winter_rose",        () -> WINTER_ROSE);
    public static final DeferredBlock<Block> POTTED_SAFFRON_CROCUS     = pottedBlock("potted_saffron_crocus",     () -> SAFFRON_CROCUS);
    public static final DeferredBlock<Block> POTTED_SEDGE              = pottedBlock("potted_sedge",              () -> SEDGE);
    public static final DeferredBlock<Block> POTTED_SPICEFLOWER        = pottedBlock("potted_spiceflower",        () -> SPICEFLOWER);
    public static final DeferredBlock<Block> POTTED_TANSY              = pottedBlock("potted_tansy",              () -> TANSY);
    public static final DeferredBlock<Block> POTTED_THISTLE            = pottedBlock("potted_thistle",            () -> THISTLE);
    public static final DeferredBlock<Block> POTTED_WILD_RADISH        = pottedBlock("potted_wild_radish",        () -> WILD_RADISH);
    public static final DeferredBlock<Block> POTTED_HRANNA             = pottedBlock("potted_hranna",             () -> HRANNA);
    public static final DeferredBlock<Block> POTTED_WILD_WHEAT         = pottedBlock("potted_wild_wheat",         () -> WILD_WHEAT);
    public static final DeferredBlock<Block> POTTED_WILD_OAT           = pottedBlock("potted_wild_oat",           () -> WILD_OAT);
    public static final DeferredBlock<Block> POTTED_WILD_RYE           = pottedBlock("potted_wild_rye",           () -> WILD_RYE);
    public static final DeferredBlock<Block> POTTED_WILD_BARLEY        = pottedBlock("potted_wild_barley",        () -> WILD_BARLEY);
    public static final DeferredBlock<Block> POTTED_WILD_BEETROOT      = pottedBlock("potted_wild_beetroot",      () -> WILD_BEETROOT);
    public static final DeferredBlock<Block> POTTED_WILD_COTTON        = pottedBlock("potted_wild_cotton",        () -> WILD_COTTON);
    public static final DeferredBlock<Block> POTTED_WILD_PEPPERCORN    = pottedBlock("potted_wild_peppercorn",    () -> WILD_PEPPERCORN);
    public static final DeferredBlock<Block> POTTED_WILD_CARROT        = pottedBlock("potted_wild_carrot",        () -> WILD_CARROT);
    public static final DeferredBlock<Block> POTTED_WILD_PARSNIP       = pottedBlock("potted_wild_parsnip",       () -> WILD_PARSNIP);
    public static final DeferredBlock<Block> POTTED_WILD_ONION         = pottedBlock("potted_wild_onion",         () -> WILD_ONION);
    public static final DeferredBlock<Block> POTTED_WILD_TURNIP        = pottedBlock("potted_wild_turnip",        () -> WILD_TURNIP);
    public static final DeferredBlock<Block> POTTED_WILD_NEEP          = pottedBlock("potted_wild_neep",          () -> WILD_NEEP);
    public static final DeferredBlock<Block> POTTED_WILD_PEAS          = pottedBlock("potted_wild_peas",          () -> WILD_PEAS);
    public static final DeferredBlock<Block> POTTED_WILD_CABBAGE       = pottedBlock("potted_wild_cabbage",       () -> WILD_CABBAGE);
    public static final DeferredBlock<Block> POTTED_WILD_GARLIC        = pottedBlock("potted_wild_garlic",        () -> WILD_GARLIC);
    public static final DeferredBlock<Block> POTTED_WILD_HORSERADISH   = pottedBlock("potted_wild_horseradish",   () -> WILD_HORSERADISH);
    public static final DeferredBlock<Block> POTTED_WILD_LEEK          = pottedBlock("potted_wild_leek",          () -> WILD_LEEK);



    // ── Quagmire ──────────────────────────────────────────────────────────────
    /**
     * Quagmire block — a boggy mud-terrain block that causes entities to sink
     * slowly (like powder snow but at roughly half the rate) and applies
     * Slowness II.  No freeze damage.
     */
    public static final DeferredBlock<Block> QUAGMIRE = REGISTRY.registerBlock("quagmire",
            properties -> new QuagmireBlock(properties),
            BlockBehaviour.Properties.ofFullCopy(Blocks.SNOW_BLOCK)
                    .sound(SoundType.MUD)
                    .friction(0.8F)
                    .speedFactor(0.1F)
                    .strength(6f)
                    .noOcclusion()
                    .isValidSpawn((state, level, pos, type) -> false)
                    .pushReaction(PushReaction.BLOCK));


    // ── Reeds ──────────────────────────────────────────────────────────────
    public static final DeferredBlock<Block> REEDS = REGISTRY.registerBlock("reeds",
            TripleReedsBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.TALL_GRASS).noOcclusion());

    public static final DeferredBlock<Block> SHORT_REEDS = REGISTRY.registerBlock("short_reeds",
            ShortReedsBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.TALL_GRASS).noOcclusion());


    // ── Crops — Seed-type (planted with seed, harvests crop + seeds) ──────
    // Block registered first; seed item wired in via supplier after items load.
    public static final DeferredBlock<Block> OAT_CROP      = seedCropBlock("oat_crop",      () -> GotModItems.OAT_SEEDS.get());
    public static final DeferredBlock<Block> RYE_CROP      = seedCropBlock("rye_crop",      () -> GotModItems.RYE_SEEDS.get());
    public static final DeferredBlock<Block> BARLEY_CROP   = seedCropBlock("barley_crop",   () -> GotModItems.BARLEY_SEEDS.get());
    public static final DeferredBlock<Block> COTTON_CROP      = seedCropBlock("cotton_crop",      () -> GotModItems.COTTON_SEEDS.get());
    public static final DeferredBlock<Block> PEPPERCORN_CROP  = seedCropBlock("peppercorn_crop",  () -> GotModItems.PEPPERCORN_SEEDS.get());
    public static final DeferredBlock<Block> BEAN_CROP          = shortProduceCropBlock("bean_crop",     () -> GotModItems.BEAN.get());

    public static final DeferredBlock<Block> CARDAMOM_CROP      = seedCropBlock("cardamom_crop",      () -> GotModItems.CARDAMOM_SEEDS.get());
    public static final DeferredBlock<Block> CHICKPEA_CROP      = seedCropBlock("chickpea_crop",      () -> GotModItems.CHICKPEA_SEEDS.get());
    public static final DeferredBlock<Block> CORN_CROP          = shortSeedCropBlock("corn_crop",     () -> GotModItems.CORN_SEEDS.get());
    public static final DeferredBlock<Block> CUCUMBER_CROP      = shortSeedCropBlock("cucumber_crop",     () -> GotModItems.CUCUMBER_SEEDS.get());
    public static final DeferredBlock<Block> HEMP_CROP          = seedCropBlock("hemp_crop",          () -> GotModItems.HEMP_SEEDS.get());
    public static final DeferredBlock<Block> LICORICE_CROP      = seedCropBlock("licorice_crop",      () -> GotModItems.LICORICE_SEEDS.get());
    public static final DeferredBlock<Block> MUSTARD_PLANT_CROP = seedCropBlock("mustard_plant_crop", () -> GotModItems.MUSTARD_PLANT_SEEDS.get());
    public static final DeferredBlock<Block> PEPPER_PLANT_CROP  = seedCropBlock("pepper_plant_crop",  () -> GotModItems.PEPPER_PLANT_SEEDS.get());

    // ── Crops — Produce-type (planted with produce, harvests more produce) ─
    public static final DeferredBlock<Block> PARSNIP_CROP = produceCropBlock("parsnip_crop", () -> GotModItems.PARSNIP.get());
    public static final DeferredBlock<Block> ONION_CROP   = produceCropBlock("onion_crop",   () -> GotModItems.ONION.get());
    public static final DeferredBlock<Block> TURNIP_CROP  = produceCropBlock("turnip_crop",  () -> GotModItems.TURNIP.get());
    public static final DeferredBlock<Block> PEAS_CROP    = produceCropBlock("peas_crop",    () -> GotModItems.PEAS.get());
    public static final DeferredBlock<Block> CABBAGE_CROP = seedCropBlock("cabbage_crop", () -> GotModItems.CABBAGE_PLANT_SEEDS.get());
    public static final DeferredBlock<Block> GARLIC_CROP      = produceCropBlock("garlic_crop",      () -> GotModItems.GARLIC.get());
    public static final DeferredBlock<Block> NEEP_CROP        = produceCropBlock("neep_crop",        () -> GotModItems.NEEP.get());
    public static final DeferredBlock<Block> HORSERADISH_CROP = produceCropBlock("horseradish_crop", () -> GotModItems.HORSERADISH.get());
    public static final DeferredBlock<Block> LEEK_CROP        = produceCropBlock("leek_crop",        () -> GotModItems.LEEK.get());

    // ── Berry Bushes ──────────────────────────────────────────────────────────
    public static final DeferredBlock<Block> BLACKBERRY_BUSH  = berryBushBlock("blackberry_bush",  () -> GotModItems.BLACKBERRIES.get());
    public static final DeferredBlock<Block> BLUEBERRY_BUSH   = berryBushBlock("blueberry_bush",   () -> GotModItems.BLUEBERRIES.get());
    public static final DeferredBlock<Block> RASPBERRY_BUSH   = berryBushBlock("raspberry_bush",   () -> GotModItems.RASPBERRIES.get());
    public static final DeferredBlock<Block> STRAWBERRY_CROP   = REGISTRY.registerBlock("strawberry_crop",
            p -> new GotStrawberryCropBlock(p),
            BlockBehaviour.Properties.of()
                    .noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.CROP)
                    .pushReaction(PushReaction.DESTROY));
    public static final DeferredBlock<Block> WILD_STRAWBERRY   = flowerBlock("wild_strawberry");

    // ── Ores
    // Stone-tier ores (hardness 3, resistance 3) — copper, tin, amber, topaz
    public static final DeferredBlock<Block> TIN_ORE       = oreStone("tin_ore");
    public static final DeferredBlock<Block> TOPAZ_ORE     = oreStone("topaz_ore");
    // Iron-tier ores (hardness 3, resistance 3, like vanilla iron ore) — silver, amethyst, opal, ruby, sapphire, dragonglass
    public static final DeferredBlock<Block> SILVER_ORE      = oreIron("silver_ore");
    public static final DeferredBlock<Block> AMETHYST_ORE      = oreIron("amethyst_ore");
    public static final DeferredBlock<Block> OPAL_ORE        = oreIron("opal_ore");
    public static final DeferredBlock<Block> RUBY_ORE        = oreIron("ruby_ore");
    public static final DeferredBlock<Block> SAPPHIRE_ORE    = oreIron("sapphire_ore");
    public static final DeferredBlock<Block> DRAGONGLASS_ORE = oreIron("dragonglass");
    // Diamond-tier ore (hardness 3, resistance 3, like vanilla diamond ore)
    public static final DeferredBlock<Block> VALYRIAN_STEEL_ORE = oreDiamond("valyrian_ore");

    // ── New base metal ores ───────────────────────────────────────────────
    public static final DeferredBlock<Block> COBALT_ORE              = oreIron("cobalt_ore");
    public static final DeferredBlock<Block> DEEPSLATE_COBALT_ORE    = oreIronDeep("deepslate_cobalt_ore");
    public static final DeferredBlock<Block> LEAD_ORE                = oreStone("lead_ore");
    public static final DeferredBlock<Block> DEEPSLATE_LEAD_ORE      = oreStoneDeep("deepslate_lead_ore");
    public static final DeferredBlock<Block> PLATINUM_ORE            = oreDiamond("platinum_ore");
    public static final DeferredBlock<Block> DEEPSLATE_PLATINUM_ORE  = oreDiamondDeep("deepslate_platinum_ore");
    public static final DeferredBlock<Block> ZINC_ORE                = oreStone("zinc_ore");
    public static final DeferredBlock<Block> DEEPSLATE_ZINC_ORE      = oreStoneDeep("deepslate_zinc_ore");

    // ── Deepslate variants of existing metals ─────────────────────────────
    public static final DeferredBlock<Block> DEEPSLATE_SILVER_ORE    = oreIronDeep("deepslate_silver_ore");
    public static final DeferredBlock<Block> DEEPSLATE_AMETHYST_ORE  = oreIronDeep("deepslate_amethyst_ore");
    public static final DeferredBlock<Block> DEEPSLATE_TIN_ORE       = oreStoneDeep("deepslate_tin_ore");

    // ── Deepslate variants of existing gem ores ───────────────────────────
    public static final DeferredBlock<Block> DEEPSLATE_OPAL_ORE      = oreIronDeep("deepslate_opal_ore");
    public static final DeferredBlock<Block> DEEPSLATE_RUBY_ORE      = oreIronDeep("deepslate_ruby_ore");
    public static final DeferredBlock<Block> DEEPSLATE_SAPPHIRE_ORE  = oreIronDeep("deepslate_sapphire_ore");
    public static final DeferredBlock<Block> DEEPSLATE_TOPAZ_ORE     = oreIronDeep("deepslate_topaz_ore");

    // ── Gem storage blocks for existing gems ─────────────────────────────
    public static final DeferredBlock<Block> OPAL_BLOCK      = gemBlock("opal_block");
    public static final DeferredBlock<Block> RUBY_BLOCK      = gemBlock("ruby_block");
    public static final DeferredBlock<Block> SAPPHIRE_BLOCK  = gemBlock("sapphire_block");
    public static final DeferredBlock<Block> TOPAZ_BLOCK     = gemBlock("topaz_block");

    // ── New mineral gem ores + blocks ────────────────────────────────────
    public static final DeferredBlock<Block> BERYL_ORE               = oreIron("beryl_ore");
    public static final DeferredBlock<Block> DEEPSLATE_BERYL_ORE     = oreIronDeep("deepslate_beryl_ore");
    public static final DeferredBlock<Block> BERYL_BLOCK             = gemBlock("beryl_block");

    public static final DeferredBlock<Block> BLOODSTONE_ORE          = oreIron("bloodstone_ore");
    public static final DeferredBlock<Block> DEEPSLATE_BLOODSTONE_ORE= oreIronDeep("deepslate_bloodstone_ore");
    public static final DeferredBlock<Block> BLOODSTONE_BLOCK        = gemBlock("bloodstone_block");

    public static final DeferredBlock<Block> CARNELIAN_ORE           = oreIron("carnelian_ore");
    public static final DeferredBlock<Block> DEEPSLATE_CARNELIAN_ORE = oreIronDeep("deepslate_carnelian_ore");
    public static final DeferredBlock<Block> CARNELIAN_BLOCK         = gemBlock("carnelian_block");

    public static final DeferredBlock<Block> CHALCEDONY_ORE          = oreIron("chalcedony_ore");
    public static final DeferredBlock<Block> DEEPSLATE_CHALCEDONY_ORE= oreIronDeep("deepslate_chalcedony_ore");
    public static final DeferredBlock<Block> CHALCEDONY_BLOCK        = gemBlock("chalcedony_block");

    public static final DeferredBlock<Block> GARNET_ORE              = oreIron("garnet_ore");
    public static final DeferredBlock<Block> DEEPSLATE_GARNET_ORE    = oreIronDeep("deepslate_garnet_ore");
    public static final DeferredBlock<Block> GARNET_BLOCK            = gemBlock("garnet_block");

    public static final DeferredBlock<Block> JADE_ORE                = oreIron("jade_ore");
    public static final DeferredBlock<Block> DEEPSLATE_JADE_ORE      = oreIronDeep("deepslate_jade_ore");
    public static final DeferredBlock<Block> JADE_BLOCK              = gemBlock("jade_block");

    public static final DeferredBlock<Block> JASPER_ORE              = oreIron("jasper_ore");
    public static final DeferredBlock<Block> DEEPSLATE_JASPER_ORE    = oreIronDeep("deepslate_jasper_ore");
    public static final DeferredBlock<Block> JASPER_BLOCK            = gemBlock("jasper_block");

    public static final DeferredBlock<Block> MALACHITE_ORE           = oreIron("malachite_ore");
    public static final DeferredBlock<Block> DEEPSLATE_MALACHITE_ORE = oreIronDeep("deepslate_malachite_ore");
    public static final DeferredBlock<Block> MALACHITE_BLOCK         = gemBlock("malachite_block");

    public static final DeferredBlock<Block> MOONSTONE_ORE           = oreIron("moonstone_ore");
    public static final DeferredBlock<Block> DEEPSLATE_MOONSTONE_ORE = oreIronDeep("deepslate_moonstone_ore");
    public static final DeferredBlock<Block> MOONSTONE_BLOCK         = gemBlock("moonstone_block");

    public static final DeferredBlock<Block> ONYX_ORE                = oreIron("onyx_ore");
    public static final DeferredBlock<Block> DEEPSLATE_ONYX_ORE      = oreIronDeep("deepslate_onyx_ore");
    public static final DeferredBlock<Block> ONYX_BLOCK              = gemBlock("onyx_block");

    public static final DeferredBlock<Block> TIGERS_EYE_ORE          = oreIron("tigers_eye_ore");
    public static final DeferredBlock<Block> DEEPSLATE_TIGERS_EYE_ORE= oreIronDeep("deepslate_tigers_eye_ore");
    public static final DeferredBlock<Block> TIGERS_EYE_BLOCK        = gemBlock("tigers_eye_block");

    public static final DeferredBlock<Block> TOURMALINE_ORE          = oreIron("tourmaline_ore");
    public static final DeferredBlock<Block> DEEPSLATE_TOURMALINE_ORE= oreIronDeep("deepslate_tourmaline_ore");
    public static final DeferredBlock<Block> TOURMALINE_BLOCK        = gemBlock("tourmaline_block");

    // ── Raw metal storage blocks ──────────────────────────────────────────
    public static final DeferredBlock<Block> RAW_COBALT_BLOCK        = rawBlock("raw_cobalt_block");
    public static final DeferredBlock<Block> RAW_LEAD_BLOCK          = rawBlock("raw_lead_block");
    public static final DeferredBlock<Block> RAW_PLATINUM_BLOCK      = rawBlock("raw_platinum_block");
    public static final DeferredBlock<Block> RAW_ZINC_BLOCK          = rawBlock("raw_zinc_block");
    public static final DeferredBlock<Block> RAW_SILVER_BLOCK        = rawBlock("raw_silver_block");
    public static final DeferredBlock<Block> RAW_TIN_BLOCK           = rawBlock("raw_tin_block");

    // ── Path blocks ───────────────────────────────────────────────────────
    public static final DeferredBlock<Block> PATH_BLOCK              = registerPath("path_block");
    public static final DeferredBlock<Block> COBBLED_PATH_BLOCK      = registerCobbledPath("cobbled_path_block");

    // ── Vanilla dirt-family slabs/stairs ──────────────────────────────────────
    public static final DeferredBlock<Block> DIRT_SLAB          = registerDirt("dirt_slab", SlabBlock::new);
    public static final DeferredBlock<Block> DIRT_STAIRS        = registerDirt("dirt_stairs", p -> new StairBlock(Blocks.DIRT.defaultBlockState(), p));
    public static final DeferredBlock<Block> MUD_SLAB           = registerMud("mud_slab", SlabBlock::new);
    public static final DeferredBlock<Block> MUD_STAIRS         = registerMud("mud_stairs", p -> new StairBlock(Blocks.MUD.defaultBlockState(), p));
    public static final DeferredBlock<Block> DIRT_PATH_SLAB     = registerDirtPath("dirt_path_slab", DirtPathSlabBlock::new);
    public static final DeferredBlock<Block> DIRT_PATH_STAIRS   = registerDirtPath("dirt_path_stairs", p -> new DirtPathStairsBlock(Blocks.DIRT_PATH.defaultBlockState(), p));
    public static final DeferredBlock<Block> COARSE_DIRT_SLAB   = registerCoarseDirt("coarse_dirt_slab", SlabBlock::new);
    public static final DeferredBlock<Block> COARSE_DIRT_STAIRS = registerCoarseDirt("coarse_dirt_stairs", p -> new StairBlock(Blocks.COARSE_DIRT.defaultBlockState(), p));
    public static final DeferredBlock<Block> ROOTED_DIRT_SLAB   = registerRootedDirt("rooted_dirt_slab", SlabBlock::new);
    public static final DeferredBlock<Block> ROOTED_DIRT_STAIRS = registerRootedDirt("rooted_dirt_stairs", p -> new StairBlock(Blocks.ROOTED_DIRT.defaultBlockState(), p));
    public static final DeferredBlock<Block> PODZOL_SLAB        = registerPodzol("podzol_slab", SlabBlock::new);
    public static final DeferredBlock<Block> PODZOL_STAIRS      = registerPodzol("podzol_stairs", p -> new StairBlock(Blocks.PODZOL.defaultBlockState(), p));
    public static final DeferredBlock<Block> GRASS_BLOCK_SLAB   = registerGrassBlock("grass_block_slab", p -> new GrassBlockSlabBlock(p, GotModBlocks.DIRT_PATH_SLAB));
    public static final DeferredBlock<Block> GRASS_BLOCK_STAIRS = registerGrassBlock("grass_block_stairs", p -> new GrassBlockStairsBlock(p, GotModBlocks.DIRT_PATH_STAIRS));

    /** Iron-tier deepslate ore. */
    private static DeferredBlock<Block> oreIronDeep(String name) {
        return REGISTRY.registerSimpleBlock(name,
                BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE));
    }

    /** Stone-tier deepslate ore. */
    private static DeferredBlock<Block> oreStoneDeep(String name) {
        return REGISTRY.registerSimpleBlock(name,
                BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_COPPER_ORE));
    }

    /** Diamond-tier deepslate ore. */
    private static DeferredBlock<Block> oreDiamondDeep(String name) {
        return REGISTRY.registerSimpleBlock(name,
                BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_DIAMOND_ORE));
    }

    /** Raw metal storage block (hardness 5, resistance 6, like vanilla raw iron block). */
    private static DeferredBlock<Block> rawBlock(String name) {
        return REGISTRY.registerSimpleBlock(name,
                BlockBehaviour.Properties.ofFullCopy(Blocks.RAW_IRON_BLOCK));
    }

    /** Path block: hardness 0.6, resistance 0.6, GRAVEL sound — a light tan compacted path. */
    private static DeferredBlock<Block> registerPath(String name) {
        return REGISTRY.registerSimpleBlock(name,
                BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL));
    }

    /** Cobbled path block: hardness 2.0, resistance 6.0, STONE sound — path reinforced with cobblestones. */
    private static DeferredBlock<Block> registerCobbledPath(String name) {
        return REGISTRY.registerSimpleBlock(name,
                BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE));
    }

    // ── Vanilla dirt-family slab/stairs helpers ───────────────────────────────
    // Each helper copies the matching vanilla block's properties so hardness,
    // sound, and behaviour line up with the full block these are cut from.
    private static <B extends Block> DeferredBlock<B> registerDirt(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
        return REGISTRY.registerBlock(name, supplier, BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT));
    }

    private static <B extends Block> DeferredBlock<B> registerMud(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
        return REGISTRY.registerBlock(name, supplier, BlockBehaviour.Properties.ofFullCopy(Blocks.MUD));
    }

    private static <B extends Block> DeferredBlock<B> registerDirtPath(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
        return REGISTRY.registerBlock(name, supplier, BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT_PATH));
    }

    private static <B extends Block> DeferredBlock<B> registerCoarseDirt(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
        return REGISTRY.registerBlock(name, supplier, BlockBehaviour.Properties.ofFullCopy(Blocks.COARSE_DIRT));
    }

    private static <B extends Block> DeferredBlock<B> registerRootedDirt(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
        return REGISTRY.registerBlock(name, supplier, BlockBehaviour.Properties.ofFullCopy(Blocks.ROOTED_DIRT));
    }

    private static <B extends Block> DeferredBlock<B> registerPodzol(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
        return REGISTRY.registerBlock(name, supplier, BlockBehaviour.Properties.ofFullCopy(Blocks.PODZOL));
    }

    private static <B extends Block> DeferredBlock<B> registerGrassBlock(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
        return REGISTRY.registerBlock(name, supplier, BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK));
    }

    // ── Apple Tree
    public static final DeferredBlock<Block> APPLE_LOG            = logBlock("apple_log",            AppleLogBlock::new);
    public static final DeferredBlock<Block> APPLE_WOOD           = logBlock("apple_wood",           AppleWoodBlock::new);
    public static final DeferredBlock<Block> APPLE_PLANKS         = woodBlock("apple_planks",         ApplePlanksBlock::new);
    public static final DeferredBlock<Block> APPLE_LEAVES         = woodBlock("apple_leaves",         AppleLeavesBlock::new);
    public static final DeferredBlock<Block> APPLE_STAIRS         = woodBlock("apple_stairs",         AppleStairsBlock::new);
    public static final DeferredBlock<Block> APPLE_SLAB           = woodBlock("apple_slab",           AppleSlabBlock::new);
    public static final DeferredBlock<Block> APPLE_FENCE          = woodBlock("apple_fence",          AppleFenceBlock::new);
    public static final DeferredBlock<Block> APPLE_FENCE_GATE     = woodBlock("apple_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.APPLE, p));
    public static final DeferredBlock<Block> APPLE_PRESSURE_PLATE = woodBlock("apple_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> APPLE_BUTTON         = woodBlock("apple_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));

    // ── Goldenheart Tree
    public static final DeferredBlock<Block> GOLDENHEART_LOG            = logBlock("goldenheart_log",            GoldenheartLogBlock::new);
    public static final DeferredBlock<Block> GOLDENHEART_WOOD           = logBlock("goldenheart_wood",           GoldenheartWoodBlock::new);
    public static final DeferredBlock<Block> GOLDENHEART_PLANKS         = woodBlock("goldenheart_planks",         GoldenheartPlanksBlock::new);
    public static final DeferredBlock<Block> GOLDENHEART_LEAVES         = woodBlock("goldenheart_leaves",         GoldenheartLeavesBlock::new);
    public static final DeferredBlock<Block> GOLDENHEART_STAIRS         = woodBlock("goldenheart_stairs",         GoldenheartStairsBlock::new);
    public static final DeferredBlock<Block> GOLDENHEART_SLAB           = woodBlock("goldenheart_slab",           GoldenheartSlabBlock::new);
    public static final DeferredBlock<Block> GOLDENHEART_FENCE          = woodBlock("goldenheart_fence",          GoldenheartFenceBlock::new);
    public static final DeferredBlock<Block> GOLDENHEART_FENCE_GATE     = woodBlock("goldenheart_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.GOLDENHEART, p));
    public static final DeferredBlock<Block> GOLDENHEART_PRESSURE_PLATE = woodBlock("goldenheart_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> GOLDENHEART_BUTTON         = woodBlock("goldenheart_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));

    // ── Linden Tree
    public static final DeferredBlock<Block> LINDEN_LOG            = logBlock("linden_log",            LindenLogBlock::new);
    public static final DeferredBlock<Block> LINDEN_WOOD           = logBlock("linden_wood",           LindenWoodBlock::new);
    public static final DeferredBlock<Block> LINDEN_PLANKS         = woodBlock("linden_planks",         LindenPlanksBlock::new);
    public static final DeferredBlock<Block> LINDEN_LEAVES         = woodBlock("linden_leaves",         LindenLeavesBlock::new);
    public static final DeferredBlock<Block> LINDEN_STAIRS         = woodBlock("linden_stairs",         LindenStairsBlock::new);
    public static final DeferredBlock<Block> LINDEN_SLAB           = woodBlock("linden_slab",           LindenSlabBlock::new);
    public static final DeferredBlock<Block> LINDEN_FENCE          = woodBlock("linden_fence",          LindenFenceBlock::new);
    public static final DeferredBlock<Block> LINDEN_FENCE_GATE     = woodBlock("linden_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.LINDEN, p));
    public static final DeferredBlock<Block> LINDEN_PRESSURE_PLATE = woodBlock("linden_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> LINDEN_BUTTON         = woodBlock("linden_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));

    // ── Mahogany Tree
    public static final DeferredBlock<Block> MAHOGANY_LOG            = logBlock("mahogany_log",            MahoganyLogBlock::new);
    public static final DeferredBlock<Block> MAHOGANY_WOOD           = logBlock("mahogany_wood",           MahoganyWoodBlock::new);
    public static final DeferredBlock<Block> MAHOGANY_PLANKS         = woodBlock("mahogany_planks",         MahoganyPlanksBlock::new);
    public static final DeferredBlock<Block> MAHOGANY_LEAVES         = woodBlock("mahogany_leaves",         MahoganyLeavesBlock::new);
    public static final DeferredBlock<Block> MAHOGANY_STAIRS         = woodBlock("mahogany_stairs",         MahoganyStairsBlock::new);
    public static final DeferredBlock<Block> MAHOGANY_SLAB           = woodBlock("mahogany_slab",           MahoganySlabBlock::new);
    public static final DeferredBlock<Block> MAHOGANY_FENCE          = woodBlock("mahogany_fence",          MahoganyFenceBlock::new);
    public static final DeferredBlock<Block> MAHOGANY_FENCE_GATE     = woodBlock("mahogany_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.MAHOGANY, p));
    public static final DeferredBlock<Block> MAHOGANY_PRESSURE_PLATE = woodBlock("mahogany_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> MAHOGANY_BUTTON         = woodBlock("mahogany_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));

    // ── Maple Tree
    public static final DeferredBlock<Block> MAPLE_LOG            = logBlock("maple_log",            MapleLogBlock::new);
    public static final DeferredBlock<Block> MAPLE_WOOD           = logBlock("maple_wood",           MapleWoodBlock::new);
    public static final DeferredBlock<Block> MAPLE_PLANKS         = woodBlock("maple_planks",         MaplePlanksBlock::new);
    public static final DeferredBlock<Block> MAPLE_LEAVES         = woodBlock("maple_leaves",         MapleLeavesBlock::new);
    public static final DeferredBlock<Block> MAPLE_STAIRS         = woodBlock("maple_stairs",         MapleStairsBlock::new);
    public static final DeferredBlock<Block> MAPLE_SLAB           = woodBlock("maple_slab",           MapleSlabBlock::new);
    public static final DeferredBlock<Block> MAPLE_FENCE          = woodBlock("maple_fence",          MapleFenceBlock::new);
    public static final DeferredBlock<Block> MAPLE_FENCE_GATE     = woodBlock("maple_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.MAPLE, p));
    public static final DeferredBlock<Block> MAPLE_PRESSURE_PLATE = woodBlock("maple_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> MAPLE_BUTTON         = woodBlock("maple_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));

    // ── Myrrh Tree
    public static final DeferredBlock<Block> MYRRH_LOG            = logBlock("myrrh_log",            MyrrhLogBlock::new);
    public static final DeferredBlock<Block> MYRRH_WOOD           = logBlock("myrrh_wood",           MyrrhWoodBlock::new);
    public static final DeferredBlock<Block> MYRRH_PLANKS         = woodBlock("myrrh_planks",         MyrrhPlanksBlock::new);
    public static final DeferredBlock<Block> MYRRH_LEAVES         = woodBlock("myrrh_leaves",         MyrrhLeavesBlock::new);
    public static final DeferredBlock<Block> MYRRH_STAIRS         = woodBlock("myrrh_stairs",         MyrrhStairsBlock::new);
    public static final DeferredBlock<Block> MYRRH_SLAB           = woodBlock("myrrh_slab",           MyrrhSlabBlock::new);
    public static final DeferredBlock<Block> MYRRH_FENCE          = woodBlock("myrrh_fence",          MyrrhFenceBlock::new);
    public static final DeferredBlock<Block> MYRRH_FENCE_GATE     = woodBlock("myrrh_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.MYRRH, p));
    public static final DeferredBlock<Block> MYRRH_PRESSURE_PLATE = woodBlock("myrrh_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> MYRRH_BUTTON         = woodBlock("myrrh_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    // ── Redwood Tree
    public static final DeferredBlock<Block> REDWOOD_LOG            = logBlock("redwood_log",            RedwoodLogBlock::new);
    public static final DeferredBlock<Block> REDWOOD_WOOD           = logBlock("redwood_wood",           RedwoodWoodBlock::new);
    public static final DeferredBlock<Block> REDWOOD_PLANKS         = woodBlock("redwood_planks",         RedwoodPlanksBlock::new);
    public static final DeferredBlock<Block> REDWOOD_LEAVES         = woodBlock("redwood_leaves",         RedwoodLeavesBlock::new);
    public static final DeferredBlock<Block> REDWOOD_STAIRS         = woodBlock("redwood_stairs",         RedwoodStairsBlock::new);
    public static final DeferredBlock<Block> REDWOOD_SLAB           = woodBlock("redwood_slab",           RedwoodSlabBlock::new);
    public static final DeferredBlock<Block> REDWOOD_FENCE          = woodBlock("redwood_fence",          RedwoodFenceBlock::new);
    public static final DeferredBlock<Block> REDWOOD_FENCE_GATE     = woodBlock("redwood_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.REDWOOD, p));
    public static final DeferredBlock<Block> REDWOOD_PRESSURE_PLATE = woodBlock("redwood_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> REDWOOD_BUTTON         = woodBlock("redwood_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));

    // ── Chestnut Tree
    public static final DeferredBlock<Block> CHESTNUT_LOG            = logBlock("chestnut_log",            ChestnutLogBlock::new);
    public static final DeferredBlock<Block> CHESTNUT_WOOD           = logBlock("chestnut_wood",           ChestnutWoodBlock::new);
    public static final DeferredBlock<Block> CHESTNUT_PLANKS         = woodBlock("chestnut_planks",         ChestnutPlanksBlock::new);
    public static final DeferredBlock<Block> CHESTNUT_LEAVES         = woodBlock("chestnut_leaves",         ChestnutLeavesBlock::new);
    public static final DeferredBlock<Block> CHESTNUT_STAIRS         = woodBlock("chestnut_stairs",         ChestnutStairsBlock::new);
    public static final DeferredBlock<Block> CHESTNUT_SLAB           = woodBlock("chestnut_slab",           ChestnutSlabBlock::new);
    public static final DeferredBlock<Block> CHESTNUT_FENCE          = woodBlock("chestnut_fence",          ChestnutFenceBlock::new);
    public static final DeferredBlock<Block> CHESTNUT_FENCE_GATE     = woodBlock("chestnut_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.CHESTNUT, p));
    public static final DeferredBlock<Block> CHESTNUT_PRESSURE_PLATE = woodBlock("chestnut_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> CHESTNUT_BUTTON         = woodBlock("chestnut_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));

    // ── Willow Tree
    public static final DeferredBlock<Block> WILLOW_LOG            = logBlock("willow_log",            WillowLogBlock::new);
    public static final DeferredBlock<Block> WILLOW_WOOD           = logBlock("willow_wood",           WillowWoodBlock::new);
    public static final DeferredBlock<Block> WILLOW_PLANKS         = woodBlock("willow_planks",         WillowPlanksBlock::new);
    public static final DeferredBlock<Block> WILLOW_LEAVES         = woodBlock("willow_leaves",         WillowLeavesBlock::new);
    public static final DeferredBlock<Block> WILLOW_STAIRS         = woodBlock("willow_stairs",         WillowStairsBlock::new);
    public static final DeferredBlock<Block> WILLOW_SLAB           = woodBlock("willow_slab",           WillowSlabBlock::new);
    public static final DeferredBlock<Block> WILLOW_FENCE          = woodBlock("willow_fence",          WillowFenceBlock::new);
    public static final DeferredBlock<Block> WILLOW_FENCE_GATE     = woodBlock("willow_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.WILLOW, p));
    public static final DeferredBlock<Block> WILLOW_PRESSURE_PLATE = woodBlock("willow_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> WILLOW_BUTTON         = woodBlock("willow_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));

    // ── Wormtree Tree
    public static final DeferredBlock<Block> WORMTREE_LOG            = logBlock("wormtree_log",            WormtreeLogBlock::new);
    public static final DeferredBlock<Block> WORMTREE_WOOD           = logBlock("wormtree_wood",           WormtreeWoodBlock::new);
    public static final DeferredBlock<Block> WORMTREE_PLANKS         = woodBlock("wormtree_planks",         WormtreePlanksBlock::new);
    public static final DeferredBlock<Block> WORMTREE_LEAVES         = woodBlock("wormtree_leaves",         WormtreeLeavesBlock::new);
    public static final DeferredBlock<Block> WORMTREE_STAIRS         = woodBlock("wormtree_stairs",         WormtreeStairsBlock::new);
    public static final DeferredBlock<Block> WORMTREE_SLAB           = woodBlock("wormtree_slab",           WormtreeSlabBlock::new);
    public static final DeferredBlock<Block> WORMTREE_FENCE          = woodBlock("wormtree_fence",          WormtreeFenceBlock::new);
    public static final DeferredBlock<Block> WORMTREE_FENCE_GATE     = woodBlock("wormtree_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.WORMTREE, p));
    public static final DeferredBlock<Block> WORMTREE_PRESSURE_PLATE = woodBlock("wormtree_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> WORMTREE_BUTTON         = woodBlock("wormtree_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));

    // ── Utility blocks ───────────────────────────────────────────────────────
    public static final DeferredBlock<Block> OVEN = REGISTRY.registerBlock("oven",
            OvenBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE));

    public static final DeferredBlock<Block> FORGE = REGISTRY.registerBlock("forge",
            ForgeBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE));

    public static final DeferredBlock<Block> SMITHING_ANVIL = REGISTRY.registerBlock("smithing_anvil",
            SmithingAnvilBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL));

    public static final DeferredBlock<Block> BELLOWS = REGISTRY.registerBlock("bellows",
            BellowsBlock::new,
            BlockBehaviour.Properties.of()
                    .strength(1.5F)
                    .sound(SoundType.WOOD)
                    .noOcclusion());

    // ── Basalt ──────────────────────────────────────────────────────────
    public static final DeferredBlock<Block> BASALT_BRICK = registerBasalt("basalt_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> CRACKED_BASALT_BRICK = registerBasalt("cracked_basalt_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_BASALT_BRICK = registerBasalt("mossy_basalt_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> BASALT_COBBLESTONE = registerBasalt("basalt_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_BASALT_COBBLESTONE = registerBasalt("mossy_basalt_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> BASALT_PILLAR = registerBasalt("basalt_pillar", RegionalRockPillarBlock::new);
    public static final DeferredBlock<Block> BASALT_ROCK_SLAB = registerBasalt("basalt_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> BASALT_ROCK_STAIRS = registerBasalt("basalt_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> BASALT_ROCK_WALL = registerBasalt("basalt_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> BASALT_ROCK_BUTTON = registerBasalt("basalt_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> BASALT_ROCK_PRESSURE_PLATE = registerBasalt("basalt_rock_pressure_plate", RegionalRockPressurePlateBlock::new);
    public static final DeferredBlock<Block> BASALT_BRICK_SLAB = registerBasalt("basalt_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> BASALT_BRICK_STAIRS = registerBasalt("basalt_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> BASALT_BRICK_WALL = registerBasalt("basalt_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> CRACKED_BASALT_BRICK_SLAB = registerBasalt("cracked_basalt_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_BASALT_BRICK_STAIRS = registerBasalt("cracked_basalt_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_BASALT_BRICK_WALL = registerBasalt("cracked_basalt_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_BASALT_BRICK_SLAB = registerBasalt("mossy_basalt_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_BASALT_BRICK_STAIRS = registerBasalt("mossy_basalt_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_BASALT_BRICK_WALL = registerBasalt("mossy_basalt_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> BASALT_COBBLESTONE_SLAB = registerBasalt("basalt_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> BASALT_COBBLESTONE_STAIRS = registerBasalt("basalt_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> BASALT_COBBLESTONE_WALL = registerBasalt("basalt_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_BASALT_COBBLESTONE_SLAB = registerBasalt("mossy_basalt_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_BASALT_COBBLESTONE_STAIRS = registerBasalt("mossy_basalt_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_BASALT_COBBLESTONE_WALL = registerBasalt("mossy_basalt_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> SMOOTH_BASALT_ROCK_SLAB = registerBasalt("smooth_basalt_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> SMOOTH_BASALT_ROCK_STAIRS = registerBasalt("smooth_basalt_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> SMOOTH_BASALT_ROCK_WALL = registerBasalt("smooth_basalt_rock_wall", RegionalRockWallBlock::new);

    // ── Fieldstone ──────────────────────────────────────────────────────
    // Standalone building stone (no brick/cobblestone/pillar family — just the
    // base block plus its slab, stairs, and wall).
    public static final DeferredBlock<Block> FIELDSTONE = registerFieldstone("fieldstone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> FIELDSTONE_SLAB = registerFieldstone("fieldstone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> FIELDSTONE_STAIRS = registerFieldstone("fieldstone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> FIELDSTONE_WALL = registerFieldstone("fieldstone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> FIELDSTONE_BUTTON = registerFieldstone("fieldstone_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> FIELDSTONE_PRESSURE_PLATE = registerFieldstone("fieldstone_pressure_plate", RegionalRockPressurePlateBlock::new);

    // ── Grey Granite ──────────────────────────────────────────────────────────
    public static final DeferredBlock<Block> GREY_GRANITE_ROCK = registerGranite("grey_granite_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> GREY_GRANITE_BRICK = registerGranite("grey_granite_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> CRACKED_GREY_GRANITE_BRICK = registerGranite("cracked_grey_granite_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_GREY_GRANITE_BRICK = registerGranite("mossy_grey_granite_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> GREY_GRANITE_COBBLESTONE = registerGranite("grey_granite_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_GREY_GRANITE_COBBLESTONE = registerGranite("mossy_grey_granite_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> SMOOTH_GREY_GRANITE_ROCK = registerGranite("smooth_grey_granite_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> GREY_GRANITE_PILLAR = registerGranite("grey_granite_pillar", RegionalRockPillarBlock::new);
    public static final DeferredBlock<Block> GREY_GRANITE_ROCK_SLAB = registerGranite("grey_granite_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> GREY_GRANITE_ROCK_STAIRS = registerGranite("grey_granite_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> GREY_GRANITE_ROCK_WALL = registerGranite("grey_granite_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> GREY_GRANITE_ROCK_BUTTON = registerGranite("grey_granite_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> GREY_GRANITE_ROCK_PRESSURE_PLATE = registerGranite("grey_granite_rock_pressure_plate", RegionalRockPressurePlateBlock::new);
    public static final DeferredBlock<Block> GREY_GRANITE_BRICK_SLAB = registerGranite("grey_granite_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> GREY_GRANITE_BRICK_STAIRS = registerGranite("grey_granite_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> GREY_GRANITE_BRICK_WALL = registerGranite("grey_granite_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> CRACKED_GREY_GRANITE_BRICK_SLAB = registerGranite("cracked_grey_granite_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_GREY_GRANITE_BRICK_STAIRS = registerGranite("cracked_grey_granite_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_GREY_GRANITE_BRICK_WALL = registerGranite("cracked_grey_granite_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_GREY_GRANITE_BRICK_SLAB = registerGranite("mossy_grey_granite_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_GREY_GRANITE_BRICK_STAIRS = registerGranite("mossy_grey_granite_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_GREY_GRANITE_BRICK_WALL = registerGranite("mossy_grey_granite_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> GREY_GRANITE_COBBLESTONE_SLAB = registerGranite("grey_granite_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> GREY_GRANITE_COBBLESTONE_STAIRS = registerGranite("grey_granite_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> GREY_GRANITE_COBBLESTONE_WALL = registerGranite("grey_granite_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_GREY_GRANITE_COBBLESTONE_SLAB = registerGranite("mossy_grey_granite_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_GREY_GRANITE_COBBLESTONE_STAIRS = registerGranite("mossy_grey_granite_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_GREY_GRANITE_COBBLESTONE_WALL = registerGranite("mossy_grey_granite_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> SMOOTH_GREY_GRANITE_ROCK_SLAB = registerGranite("smooth_grey_granite_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> SMOOTH_GREY_GRANITE_ROCK_STAIRS = registerGranite("smooth_grey_granite_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> SMOOTH_GREY_GRANITE_ROCK_WALL = registerGranite("smooth_grey_granite_rock_wall", RegionalRockWallBlock::new);

    // ── Flint ──────────────────────────────────────────────────────────
    public static final DeferredBlock<Block> FLINT_ROCK = registerFlint("flint_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> FLINT_BRICK = registerFlint("flint_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> CRACKED_FLINT_BRICK = registerFlint("cracked_flint_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_FLINT_BRICK = registerFlint("mossy_flint_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> FLINT_ROCK_SLAB = registerFlint("flint_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> FLINT_ROCK_STAIRS = registerFlint("flint_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> FLINT_ROCK_WALL = registerFlint("flint_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> FLINT_ROCK_BUTTON = registerFlint("flint_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> FLINT_ROCK_PRESSURE_PLATE = registerFlint("flint_rock_pressure_plate", RegionalRockPressurePlateBlock::new);
    public static final DeferredBlock<Block> FLINT_BRICK_SLAB = registerFlint("flint_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> FLINT_BRICK_STAIRS = registerFlint("flint_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> FLINT_BRICK_WALL = registerFlint("flint_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> CRACKED_FLINT_BRICK_SLAB = registerFlint("cracked_flint_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_FLINT_BRICK_STAIRS = registerFlint("cracked_flint_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_FLINT_BRICK_WALL = registerFlint("cracked_flint_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_FLINT_BRICK_SLAB = registerFlint("mossy_flint_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_FLINT_BRICK_STAIRS = registerFlint("mossy_flint_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_FLINT_BRICK_WALL = registerFlint("mossy_flint_brick_wall", RegionalRockWallBlock::new);

    // ── Limestone ──────────────────────────────────────────────────────────
    public static final DeferredBlock<Block> LIMESTONE_ROCK = registerLimestone("limestone_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> LIMESTONE_BRICK = registerLimestone("limestone_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> CRACKED_LIMESTONE_BRICK = registerLimestone("cracked_limestone_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_LIMESTONE_BRICK = registerLimestone("mossy_limestone_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> LIMESTONE_COBBLESTONE = registerLimestone("limestone_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_LIMESTONE_COBBLESTONE = registerLimestone("mossy_limestone_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> SMOOTH_LIMESTONE_ROCK = registerLimestone("smooth_limestone_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> LIMESTONE_PILLAR = registerLimestone("limestone_pillar", RegionalRockPillarBlock::new);
    public static final DeferredBlock<Block> LIMESTONE_ROCK_SLAB = registerLimestone("limestone_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> LIMESTONE_ROCK_STAIRS = registerLimestone("limestone_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> LIMESTONE_ROCK_WALL = registerLimestone("limestone_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> LIMESTONE_ROCK_BUTTON = registerLimestone("limestone_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> LIMESTONE_ROCK_PRESSURE_PLATE = registerLimestone("limestone_rock_pressure_plate", RegionalRockPressurePlateBlock::new);
    public static final DeferredBlock<Block> LIMESTONE_BRICK_SLAB = registerLimestone("limestone_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> LIMESTONE_BRICK_STAIRS = registerLimestone("limestone_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> LIMESTONE_BRICK_WALL = registerLimestone("limestone_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> CRACKED_LIMESTONE_BRICK_SLAB = registerLimestone("cracked_limestone_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_LIMESTONE_BRICK_STAIRS = registerLimestone("cracked_limestone_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_LIMESTONE_BRICK_WALL = registerLimestone("cracked_limestone_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_LIMESTONE_BRICK_SLAB = registerLimestone("mossy_limestone_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_LIMESTONE_BRICK_STAIRS = registerLimestone("mossy_limestone_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_LIMESTONE_BRICK_WALL = registerLimestone("mossy_limestone_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> LIMESTONE_COBBLESTONE_SLAB = registerLimestone("limestone_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> LIMESTONE_COBBLESTONE_STAIRS = registerLimestone("limestone_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> LIMESTONE_COBBLESTONE_WALL = registerLimestone("limestone_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_LIMESTONE_COBBLESTONE_SLAB = registerLimestone("mossy_limestone_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_LIMESTONE_COBBLESTONE_STAIRS = registerLimestone("mossy_limestone_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_LIMESTONE_COBBLESTONE_WALL = registerLimestone("mossy_limestone_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> SMOOTH_LIMESTONE_ROCK_SLAB = registerLimestone("smooth_limestone_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> SMOOTH_LIMESTONE_ROCK_STAIRS = registerLimestone("smooth_limestone_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> SMOOTH_LIMESTONE_ROCK_WALL = registerLimestone("smooth_limestone_rock_wall", RegionalRockWallBlock::new);

    // ── Sandstone ──────────────────────────────────────────────────────────
    public static final DeferredBlock<Block> SANDSTONE_BRICK = registerSandstone("sandstone_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> CRACKED_SANDSTONE_BRICK = registerSandstone("cracked_sandstone_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_SANDSTONE_BRICK = registerSandstone("mossy_sandstone_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> SANDSTONE_COBBLESTONE = registerSandstone("sandstone_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_SANDSTONE_COBBLESTONE = registerSandstone("mossy_sandstone_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> SANDSTONE_PILLAR = registerSandstone("sandstone_pillar", RegionalRockPillarBlock::new);
    public static final DeferredBlock<Block> SANDSTONE_ROCK_BUTTON = registerSandstone("sandstone_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> SANDSTONE_ROCK_PRESSURE_PLATE = registerSandstone("sandstone_rock_pressure_plate", RegionalRockPressurePlateBlock::new);
    public static final DeferredBlock<Block> SANDSTONE_BRICK_SLAB = registerSandstone("sandstone_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> SANDSTONE_BRICK_STAIRS = registerSandstone("sandstone_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> SANDSTONE_BRICK_WALL = registerSandstone("sandstone_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> CRACKED_SANDSTONE_BRICK_SLAB = registerSandstone("cracked_sandstone_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_SANDSTONE_BRICK_STAIRS = registerSandstone("cracked_sandstone_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_SANDSTONE_BRICK_WALL = registerSandstone("cracked_sandstone_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_SANDSTONE_BRICK_SLAB = registerSandstone("mossy_sandstone_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_SANDSTONE_BRICK_STAIRS = registerSandstone("mossy_sandstone_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_SANDSTONE_BRICK_WALL = registerSandstone("mossy_sandstone_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> SANDSTONE_COBBLESTONE_SLAB = registerSandstone("sandstone_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> SANDSTONE_COBBLESTONE_STAIRS = registerSandstone("sandstone_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> SANDSTONE_COBBLESTONE_WALL = registerSandstone("sandstone_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_SANDSTONE_COBBLESTONE_SLAB = registerSandstone("mossy_sandstone_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_SANDSTONE_COBBLESTONE_STAIRS = registerSandstone("mossy_sandstone_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_SANDSTONE_COBBLESTONE_WALL = registerSandstone("mossy_sandstone_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> SMOOTH_SANDSTONE_ROCK_WALL = registerSandstone("smooth_sandstone_rock_wall", RegionalRockWallBlock::new);

    // ── Red Sandstone ──────────────────────────────────────────────────────────
    public static final DeferredBlock<Block> RED_SANDSTONE_BRICK = registerRedSandstone("red_sandstone_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> CRACKED_RED_SANDSTONE_BRICK = registerRedSandstone("cracked_red_sandstone_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_RED_SANDSTONE_BRICK = registerRedSandstone("mossy_red_sandstone_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> RED_SANDSTONE_COBBLESTONE = registerRedSandstone("red_sandstone_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_RED_SANDSTONE_COBBLESTONE = registerRedSandstone("mossy_red_sandstone_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> RED_SANDSTONE_PILLAR = registerRedSandstone("red_sandstone_pillar", RegionalRockPillarBlock::new);
    public static final DeferredBlock<Block> RED_SANDSTONE_ROCK_BUTTON = registerRedSandstone("red_sandstone_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> RED_SANDSTONE_ROCK_PRESSURE_PLATE = registerRedSandstone("red_sandstone_rock_pressure_plate", RegionalRockPressurePlateBlock::new);
    public static final DeferredBlock<Block> RED_SANDSTONE_BRICK_SLAB = registerRedSandstone("red_sandstone_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> RED_SANDSTONE_BRICK_STAIRS = registerRedSandstone("red_sandstone_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> RED_SANDSTONE_BRICK_WALL = registerRedSandstone("red_sandstone_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> CRACKED_RED_SANDSTONE_BRICK_SLAB = registerRedSandstone("cracked_red_sandstone_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_RED_SANDSTONE_BRICK_STAIRS = registerRedSandstone("cracked_red_sandstone_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_RED_SANDSTONE_BRICK_WALL = registerRedSandstone("cracked_red_sandstone_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_RED_SANDSTONE_BRICK_SLAB = registerRedSandstone("mossy_red_sandstone_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_RED_SANDSTONE_BRICK_STAIRS = registerRedSandstone("mossy_red_sandstone_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_RED_SANDSTONE_BRICK_WALL = registerRedSandstone("mossy_red_sandstone_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> RED_SANDSTONE_COBBLESTONE_SLAB = registerRedSandstone("red_sandstone_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> RED_SANDSTONE_COBBLESTONE_STAIRS = registerRedSandstone("red_sandstone_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> RED_SANDSTONE_COBBLESTONE_WALL = registerRedSandstone("red_sandstone_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_RED_SANDSTONE_COBBLESTONE_SLAB = registerRedSandstone("mossy_red_sandstone_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_RED_SANDSTONE_COBBLESTONE_STAIRS = registerRedSandstone("mossy_red_sandstone_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_RED_SANDSTONE_COBBLESTONE_WALL = registerRedSandstone("mossy_red_sandstone_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> SMOOTH_RED_SANDSTONE_ROCK_WALL = registerRedSandstone("smooth_red_sandstone_rock_wall", RegionalRockWallBlock::new);

    // ── Slate ──────────────────────────────────────────────────────────
    public static final DeferredBlock<Block> SLATE_ROCK = registerSlate("slate_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> SLATE_BRICK = registerSlate("slate_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> CRACKED_SLATE_BRICK = registerSlate("cracked_slate_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_SLATE_BRICK = registerSlate("mossy_slate_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> SLATE_COBBLESTONE = registerSlate("slate_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_SLATE_COBBLESTONE = registerSlate("mossy_slate_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> SMOOTH_SLATE_ROCK = registerSlate("smooth_slate_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> SLATE_PILLAR = registerSlate("slate_pillar", RegionalRockPillarBlock::new);
    public static final DeferredBlock<Block> SLATE_ROCK_SLAB = registerSlate("slate_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> SLATE_ROCK_STAIRS = registerSlate("slate_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> SLATE_ROCK_WALL = registerSlate("slate_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> SLATE_ROCK_BUTTON = registerSlate("slate_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> SLATE_ROCK_PRESSURE_PLATE = registerSlate("slate_rock_pressure_plate", RegionalRockPressurePlateBlock::new);
    public static final DeferredBlock<Block> SLATE_BRICK_SLAB = registerSlate("slate_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> SLATE_BRICK_STAIRS = registerSlate("slate_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> SLATE_BRICK_WALL = registerSlate("slate_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> CRACKED_SLATE_BRICK_SLAB = registerSlate("cracked_slate_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_SLATE_BRICK_STAIRS = registerSlate("cracked_slate_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_SLATE_BRICK_WALL = registerSlate("cracked_slate_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_SLATE_BRICK_SLAB = registerSlate("mossy_slate_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_SLATE_BRICK_STAIRS = registerSlate("mossy_slate_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_SLATE_BRICK_WALL = registerSlate("mossy_slate_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> SLATE_COBBLESTONE_SLAB = registerSlate("slate_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> SLATE_COBBLESTONE_STAIRS = registerSlate("slate_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> SLATE_COBBLESTONE_WALL = registerSlate("slate_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_SLATE_COBBLESTONE_SLAB = registerSlate("mossy_slate_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_SLATE_COBBLESTONE_STAIRS = registerSlate("mossy_slate_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_SLATE_COBBLESTONE_WALL = registerSlate("mossy_slate_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> SMOOTH_SLATE_ROCK_SLAB = registerSlate("smooth_slate_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> SMOOTH_SLATE_ROCK_STAIRS = registerSlate("smooth_slate_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> SMOOTH_SLATE_ROCK_WALL = registerSlate("smooth_slate_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> SLATE_SHINGLES = registerSlate("slate_shingles", RegionalRockBlock::new);
    public static final DeferredBlock<Block> SLATE_SHINGLES_SLAB = registerSlate("slate_shingles_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> SLATE_SHINGLES_STAIRS = registerSlate("slate_shingles_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> SLATE_SHINGLES_WALL = registerSlate("slate_shingles_wall", RegionalRockWallBlock::new);

    // ── Oily Black Stone blocks ───────────────────────────────────────────────
    public static final DeferredBlock<Block> OILY_BLACK_ROCK = registerOilyBlack("oily_black_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> OILY_BLACK_BRICK = registerOilyBlack("oily_black_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> CRACKED_OILY_BLACK_BRICK = registerOilyBlack("cracked_oily_black_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_OILY_BLACK_BRICK = registerOilyBlack("mossy_oily_black_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> OILY_BLACK_COBBLESTONE = registerOilyBlack("oily_black_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_OILY_BLACK_COBBLESTONE = registerOilyBlack("mossy_oily_black_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> SMOOTH_OILY_BLACK_ROCK = registerOilyBlack("smooth_oily_black_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> OILY_BLACK_PILLAR = registerOilyBlack("oily_black_pillar", RegionalRockPillarBlock::new);
    public static final DeferredBlock<Block> OILY_BLACK_ROCK_SLAB = registerOilyBlack("oily_black_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> OILY_BLACK_ROCK_STAIRS = registerOilyBlack("oily_black_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> OILY_BLACK_ROCK_WALL = registerOilyBlack("oily_black_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> OILY_BLACK_ROCK_BUTTON = registerOilyBlack("oily_black_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> OILY_BLACK_ROCK_PRESSURE_PLATE = registerOilyBlack("oily_black_rock_pressure_plate", RegionalRockPressurePlateBlock::new);
    public static final DeferredBlock<Block> OILY_BLACK_BRICK_SLAB = registerOilyBlack("oily_black_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> OILY_BLACK_BRICK_STAIRS = registerOilyBlack("oily_black_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> OILY_BLACK_BRICK_WALL = registerOilyBlack("oily_black_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> CRACKED_OILY_BLACK_BRICK_SLAB = registerOilyBlack("cracked_oily_black_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_OILY_BLACK_BRICK_STAIRS = registerOilyBlack("cracked_oily_black_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_OILY_BLACK_BRICK_WALL = registerOilyBlack("cracked_oily_black_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_OILY_BLACK_BRICK_SLAB = registerOilyBlack("mossy_oily_black_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_OILY_BLACK_BRICK_STAIRS = registerOilyBlack("mossy_oily_black_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_OILY_BLACK_BRICK_WALL = registerOilyBlack("mossy_oily_black_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> OILY_BLACK_COBBLESTONE_SLAB = registerOilyBlack("oily_black_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> OILY_BLACK_COBBLESTONE_STAIRS = registerOilyBlack("oily_black_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> OILY_BLACK_COBBLESTONE_WALL = registerOilyBlack("oily_black_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_OILY_BLACK_COBBLESTONE_SLAB = registerOilyBlack("mossy_oily_black_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_OILY_BLACK_COBBLESTONE_STAIRS = registerOilyBlack("mossy_oily_black_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_OILY_BLACK_COBBLESTONE_WALL = registerOilyBlack("mossy_oily_black_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> SMOOTH_OILY_BLACK_ROCK_SLAB = registerOilyBlack("smooth_oily_black_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> SMOOTH_OILY_BLACK_ROCK_STAIRS = registerOilyBlack("smooth_oily_black_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> SMOOTH_OILY_BLACK_ROCK_WALL = registerOilyBlack("smooth_oily_black_rock_wall", RegionalRockWallBlock::new);

    // ── Fused Black Stone blocks ──────────────────────────────────────────────
    public static final DeferredBlock<Block> FUSED_BLACK_ROCK = registerFusedBlack("fused_black_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> FUSED_BLACK_BRICK = registerFusedBlack("fused_black_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> CRACKED_FUSED_BLACK_BRICK = registerFusedBlack("cracked_fused_black_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_FUSED_BLACK_BRICK = registerFusedBlack("mossy_fused_black_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> FUSED_BLACK_COBBLESTONE = registerFusedBlack("fused_black_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_FUSED_BLACK_COBBLESTONE = registerFusedBlack("mossy_fused_black_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> SMOOTH_FUSED_BLACK_ROCK = registerFusedBlack("smooth_fused_black_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> FUSED_BLACK_PILLAR = registerFusedBlack("fused_black_pillar", RegionalRockPillarBlock::new);
    public static final DeferredBlock<Block> FUSED_BLACK_ROCK_SLAB = registerFusedBlack("fused_black_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> FUSED_BLACK_ROCK_STAIRS = registerFusedBlack("fused_black_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> FUSED_BLACK_ROCK_WALL = registerFusedBlack("fused_black_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> FUSED_BLACK_ROCK_BUTTON = registerFusedBlack("fused_black_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> FUSED_BLACK_ROCK_PRESSURE_PLATE = registerFusedBlack("fused_black_rock_pressure_plate", RegionalRockPressurePlateBlock::new);
    public static final DeferredBlock<Block> FUSED_BLACK_BRICK_SLAB = registerFusedBlack("fused_black_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> FUSED_BLACK_BRICK_STAIRS = registerFusedBlack("fused_black_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> FUSED_BLACK_BRICK_WALL = registerFusedBlack("fused_black_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> CRACKED_FUSED_BLACK_BRICK_SLAB = registerFusedBlack("cracked_fused_black_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_FUSED_BLACK_BRICK_STAIRS = registerFusedBlack("cracked_fused_black_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_FUSED_BLACK_BRICK_WALL = registerFusedBlack("cracked_fused_black_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_FUSED_BLACK_BRICK_SLAB = registerFusedBlack("mossy_fused_black_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_FUSED_BLACK_BRICK_STAIRS = registerFusedBlack("mossy_fused_black_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_FUSED_BLACK_BRICK_WALL = registerFusedBlack("mossy_fused_black_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> FUSED_BLACK_COBBLESTONE_SLAB = registerFusedBlack("fused_black_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> FUSED_BLACK_COBBLESTONE_STAIRS = registerFusedBlack("fused_black_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> FUSED_BLACK_COBBLESTONE_WALL = registerFusedBlack("fused_black_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_FUSED_BLACK_COBBLESTONE_SLAB = registerFusedBlack("mossy_fused_black_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_FUSED_BLACK_COBBLESTONE_STAIRS = registerFusedBlack("mossy_fused_black_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_FUSED_BLACK_COBBLESTONE_WALL = registerFusedBlack("mossy_fused_black_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> SMOOTH_FUSED_BLACK_ROCK_SLAB = registerFusedBlack("smooth_fused_black_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> SMOOTH_FUSED_BLACK_ROCK_STAIRS = registerFusedBlack("smooth_fused_black_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> SMOOTH_FUSED_BLACK_ROCK_WALL = registerFusedBlack("smooth_fused_black_rock_wall", RegionalRockWallBlock::new);

    // ── Marble blocks ─────────────────────────────────────────────────────────
    public static final DeferredBlock<Block> MARBLE_ROCK = registerMarble("marble_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MARBLE_BRICK = registerMarble("marble_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> CRACKED_MARBLE_BRICK = registerMarble("cracked_marble_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_MARBLE_BRICK = registerMarble("mossy_marble_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MARBLE_COBBLESTONE = registerMarble("marble_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_MARBLE_COBBLESTONE = registerMarble("mossy_marble_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> SMOOTH_MARBLE_ROCK = registerMarble("smooth_marble_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MARBLE_PILLAR = registerMarble("marble_pillar", RegionalRockPillarBlock::new);
    public static final DeferredBlock<Block> MARBLE_ROCK_SLAB = registerMarble("marble_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MARBLE_ROCK_STAIRS = registerMarble("marble_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MARBLE_ROCK_WALL = registerMarble("marble_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MARBLE_ROCK_BUTTON = registerMarble("marble_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> MARBLE_ROCK_PRESSURE_PLATE = registerMarble("marble_rock_pressure_plate", RegionalRockPressurePlateBlock::new);
    public static final DeferredBlock<Block> MARBLE_BRICK_SLAB = registerMarble("marble_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MARBLE_BRICK_STAIRS = registerMarble("marble_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MARBLE_BRICK_WALL = registerMarble("marble_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> CRACKED_MARBLE_BRICK_SLAB = registerMarble("cracked_marble_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_MARBLE_BRICK_STAIRS = registerMarble("cracked_marble_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_MARBLE_BRICK_WALL = registerMarble("cracked_marble_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_MARBLE_BRICK_SLAB = registerMarble("mossy_marble_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_MARBLE_BRICK_STAIRS = registerMarble("mossy_marble_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_MARBLE_BRICK_WALL = registerMarble("mossy_marble_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MARBLE_COBBLESTONE_SLAB = registerMarble("marble_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MARBLE_COBBLESTONE_STAIRS = registerMarble("marble_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MARBLE_COBBLESTONE_WALL = registerMarble("marble_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_MARBLE_COBBLESTONE_SLAB = registerMarble("mossy_marble_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_MARBLE_COBBLESTONE_STAIRS = registerMarble("mossy_marble_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_MARBLE_COBBLESTONE_WALL = registerMarble("mossy_marble_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> SMOOTH_MARBLE_ROCK_SLAB = registerMarble("smooth_marble_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> SMOOTH_MARBLE_ROCK_STAIRS = registerMarble("smooth_marble_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> SMOOTH_MARBLE_ROCK_WALL = registerMarble("smooth_marble_rock_wall", RegionalRockWallBlock::new);

    // ── Thatch ────────────────────────────────────────────────────────────
    public static final DeferredBlock<Block> LIGHT_THATCH =
            REGISTRY.registerBlock("light_thatch", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK).strength(0.5f, 0.5f));
    public static final DeferredBlock<Block> LIGHT_THATCH_SLAB =
            REGISTRY.registerBlock("light_thatch_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK).strength(0.5f, 0.5f));
    public static final DeferredBlock<Block> LIGHT_THATCH_STAIRS =
            REGISTRY.registerBlock("light_thatch_stairs",
                    p -> new StairBlock(LIGHT_THATCH.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK).strength(0.5f, 0.5f));
    public static final DeferredBlock<Block> LIGHT_THATCH_WALL =
            REGISTRY.registerBlock("light_thatch_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK).strength(0.5f, 0.5f));

    public static final DeferredBlock<Block> DARK_THATCH =
            REGISTRY.registerBlock("dark_thatch", p -> new Block(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK).strength(0.5f, 0.5f));
    public static final DeferredBlock<Block> DARK_THATCH_SLAB =
            REGISTRY.registerBlock("dark_thatch_slab", p -> new SlabBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK).strength(0.5f, 0.5f));
    public static final DeferredBlock<Block> DARK_THATCH_STAIRS =
            REGISTRY.registerBlock("dark_thatch_stairs",
                    p -> new StairBlock(DARK_THATCH.get().defaultBlockState(), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK).strength(0.5f, 0.5f));
    public static final DeferredBlock<Block> DARK_THATCH_WALL =
            REGISTRY.registerBlock("dark_thatch_wall", p -> new WallBlock(p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK).strength(0.5f, 0.5f));

    // ── Log / Stripped-log maps (used by GotFlammableRotatedPillarBlock for axe stripping) ──

    // ── Helpers ──────────────────────────────────────────────────────────

    /** Wood block: copies from OAK_PLANKS so no tool requirement, proper wood map-colour etc. */
    private static <B extends Block> DeferredBlock<B> woodBlock(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
        return REGISTRY.registerBlock(name, supplier, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    }

    /** Wool block: copies properties (hardness, map-colour, sound) from the matching vanilla wool block. */
    private static <B extends Block> DeferredBlock<B> woolBlock(String name, Block copyFrom, Function<BlockBehaviour.Properties, ? extends B> supplier) {
        return REGISTRY.registerBlock(name, supplier, BlockBehaviour.Properties.ofFullCopy(copyFrom));
    }

    /** Vertical slab copying properties from an already-registered GOT slab (same hardness/sound/etc as the horizontal version). */
    private static DeferredBlock<Block> verticalSlab(String name, DeferredBlock<Block> copyFrom) {
        return REGISTRY.registerBlock(name, VerticalSlabBlock::new, BlockBehaviour.Properties.ofFullCopy(copyFrom.get()));
    }

    /** Vertical slab copying properties from a vanilla slab block. */
    private static DeferredBlock<Block> verticalSlab(String name, Block copyFrom) {
        return REGISTRY.registerBlock(name, VerticalSlabBlock::new, BlockBehaviour.Properties.ofFullCopy(copyFrom));
    }

    /**
     * Flower block: non-solid, no collision, requires dirt/grass below.
     * Uses FlowerBlock with no potion effect (MobEffects.ABSORPTION, 0 seconds).
     */
    private static DeferredBlock<Block> flowerBlock(String name) {
        return REGISTRY.registerBlock(name,
                GotShortGrassBlock::new,
                BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION));
    }

    /**
     * 2-tall flower block: vanilla DoublePlantBlock (same base class as tall grass,
     * sunflower, lilac, rose bush, peony). Placing one automatically fills the block
     * above with the matching upper half; breaking either half breaks both. Like
     * vanilla's tall flowers, these are not pottable (a flower pot can't hold a
     * two-block plant), so there is no potted variant.
     */
    private static DeferredBlock<Block> tallFlowerBlock(String name) {
        return REGISTRY.registerBlock(name,
                DoublePlantBlock::new,
                BlockBehaviour.Properties.ofFullCopy(Blocks.TALL_GRASS));
    }

    /**
     * Potted plant block: a FlowerPotBlock that holds the given plant.
     * Register one for every plant you pass to addPlant() in GotMod.java.
     */
    private static DeferredBlock<Block> pottedBlock(String name, java.util.function.Supplier<DeferredBlock<Block>> plant) {
        return REGISTRY.registerBlock(name,
                p -> new FlowerPotBlock(plant.get().get(), p),
                BlockBehaviour.Properties.of()
                        .instabreak()
                        .noOcclusion()
                        .pushReaction(PushReaction.DESTROY));
    }


    /**
     * Berry bush (blackberry, blueberry, raspberry, strawberry).
     * Grows through 4 ages (0-3); ripe at 3. Right-click to harvest without breaking.
     * Copies properties from vanilla SWEET_BERRY_BUSH.
     */
    private static DeferredBlock<Block> berryBushBlock(String name, java.util.function.Supplier<Item> berry) {
        return REGISTRY.registerBlock(name,
                p -> new GotBerryBushBlock(berry, p),
                BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH));
    }

    /**
     * Seed-type crop (wheat, oat, rye, barley, beetroot, cotton).
     * Copies properties from vanilla WHEAT — farmland requirement, no collision,
     * cutout render, instabreak. The seed supplier is resolved lazily so that
     * the item registry is fully populated before it is first accessed.
     */
    private static DeferredBlock<Block> seedCropBlock(String name, java.util.function.Supplier<Item> seed) {
        return REGISTRY.registerBlock(name,
                p -> new GotSeedCropBlock(seed, p),
                BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT));
    }

    /**
     * Short seed-type crop (4 growth stages, age 0–3). Same farmland/no-collision
     * properties as seedCropBlock, but uses GotShortSeedCropBlock.
     */
    private static DeferredBlock<Block> shortSeedCropBlock(String name, java.util.function.Supplier<Item> seed) {
        // Cannot use ofFullCopy(Blocks.WHEAT) here — that copies WHEAT's 0-7 age
        // BlockState properties onto our block, which only declares age 0-3, causing
        // an IllegalArgumentException during StateDefinition construction.
        // Instead we manually copy the behaviour properties we need from WHEAT.
        return REGISTRY.registerBlock(name,
                p -> new GotShortSeedCropBlock(seed, p),
                BlockBehaviour.Properties.of()
                        .noCollission()
                        .randomTicks()
                        .instabreak()
                        .sound(SoundType.CROP)
                        .pushReaction(PushReaction.DESTROY));
    }

    /**
     * Produce-type crop (carrot, parsnip, onion, turnip, peas, cabbage, garlic).
     * Same farmland/no-collision properties as vanilla carrots/potatoes. The
     * produce item is used both as the seed and as the harvest drop.
     */
    private static DeferredBlock<Block> produceCropBlock(String name, java.util.function.Supplier<Item> produce) {
        return REGISTRY.registerBlock(name,
                p -> new GotProduceCropBlock(produce, p),
                BlockBehaviour.Properties.ofFullCopy(Blocks.CARROTS));
    }

    /**
     * Short produce-type crop (4 growth stages, age 0–3). Planted with the
     * produce item itself (no separate seed), like produceCropBlock, but uses
     * GotShortProduceCropBlock for the 0-3 age range.
     */
    private static DeferredBlock<Block> shortProduceCropBlock(String name, java.util.function.Supplier<Item> produce) {
        // Cannot use ofFullCopy(Blocks.CARROTS) here — that copies CARROTS' 0-7 age
        // BlockState properties onto our block, which only declares age 0-3, causing
        // an IllegalArgumentException during StateDefinition construction.
        // Instead we manually copy the behaviour properties we need from CARROTS.
        return REGISTRY.registerBlock(name,
                p -> new GotShortProduceCropBlock(produce, p),
                BlockBehaviour.Properties.of()
                        .noCollission()
                        .randomTicks()
                        .instabreak()
                        .sound(SoundType.CROP)
                        .pushReaction(PushReaction.DESTROY));
    }

    /**
     * Door block: copies from OAK_DOOR exactly — matching how the reference mod registers
     * doors (vanilla DoorBlock + ofFullCopy(Blocks.OAK_DOOR), no extra mutations).
     */
    private static <B extends Block> DeferredBlock<B> doorBlock(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
        return REGISTRY.registerBlock(name, supplier, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR));
    }

    /**
     * Trapdoor block: copies from OAK_TRAPDOOR exactly — matching how the reference mod
     * registers trapdoors (vanilla TrapDoorBlock + ofFullCopy(Blocks.OAK_TRAPDOOR), no extra mutations).
     */
    private static <B extends Block> DeferredBlock<B> trapdoorBlock(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
        return REGISTRY.registerBlock(name, supplier, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR));
    }

    /** Log/wood variant: copies OAK_LOG (hardness 2, resistance 2, ignitedByLava). */
    private static <B extends Block> DeferredBlock<B> logBlock(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
        return REGISTRY.registerBlock(name, supplier, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG));
    }

    private static <B extends Block> DeferredBlock<B> register(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
        return REGISTRY.registerBlock(name, supplier, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE));
    }

    // ── Per-stone-type helpers ────────────────────────────────────────────────
    // Each helper copies from the most appropriate vanilla base block so that
    // hardness, blast-resistance, map-colour, and sound are all correct for
    // the stone family, instead of every variant silently inheriting STONE.

    /** Basalt: hardness 1.25, resistance 4.2, BASALT sound, dark grey map colour. */
    private static <B extends Block> DeferredBlock<B> registerBasalt(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
        return REGISTRY.registerBlock(name, supplier, BlockBehaviour.Properties.ofFullCopy(Blocks.BASALT));
    }

    /** Fieldstone: hardness 2.0, resistance 6.0, STONE sound – rugged, cobblestone-like building stone. */
    private static <B extends Block> DeferredBlock<B> registerFieldstone(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
        return REGISTRY.registerBlock(name, supplier, BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE));
    }

    /** Grey Granite: hardness 1.5, resistance 6.0, STONE sound, pink-grey map colour. */
    private static <B extends Block> DeferredBlock<B> registerGranite(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
        return REGISTRY.registerBlock(name, supplier, BlockBehaviour.Properties.ofFullCopy(Blocks.GRANITE));
    }

    /** Limestone: hardness 1.5, resistance 3.0 (softer than granite), STONE sound. */
    private static <B extends Block> DeferredBlock<B> registerLimestone(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
        return REGISTRY.registerBlock(name, supplier, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(1.5f, 3.0f));
    }

    /** Sandstone: hardness 0.8, resistance 0.8, STONE sound – copies vanilla SANDSTONE. */
    private static <B extends Block> DeferredBlock<B> registerSandstone(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
        return REGISTRY.registerBlock(name, supplier, BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE));
    }

    /** Red Sandstone: hardness 0.8, resistance 0.8, STONE sound – copies vanilla RED_SANDSTONE. */
    private static <B extends Block> DeferredBlock<B> registerRedSandstone(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
        return REGISTRY.registerBlock(name, supplier, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_SANDSTONE));
    }

    /** Slate: hardness 2.0, resistance 4.0, STONE sound – slightly harder and denser than stone. */
    private static <B extends Block> DeferredBlock<B> registerSlate(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
        return REGISTRY.registerBlock(name, supplier, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2.0f, 4.0f));
    }

    /** Flint: hardness 2.0, resistance 6.0, STONE sound – hard and brittle. */
    private static <B extends Block> DeferredBlock<B> registerFlint(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
        return REGISTRY.registerBlock(name, supplier, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2.0f, 6.0f));
    }

    /** Oily Black Stone: hardness 1.5, resistance 5.0, STONE sound – dense and smooth. */
    private static <B extends Block> DeferredBlock<B> registerOilyBlack(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
        return REGISTRY.registerBlock(name, supplier, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(1.5f, 5.0f));
    }

    /** Fused Black Stone: hardness 2.5, resistance 6.0, DEEPSLATE sound – volcanic and extremely tough. */
    private static <B extends Block> DeferredBlock<B> registerFusedBlack(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
        return REGISTRY.registerBlock(name, supplier, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE).strength(2.5f, 6.0f));
    }

    /** Marble: hardness 0.75, resistance 0.75, CALCITE sound – elegant but brittle, copies vanilla CALCITE. */
    private static <B extends Block> DeferredBlock<B> registerMarble(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
        return REGISTRY.registerBlock(name, supplier, BlockBehaviour.Properties.ofFullCopy(Blocks.CALCITE));
    }

    /** Stone-tier ore: hardness 3.0, resistance 3.0, requires stone pickaxe. */
    private static DeferredBlock<Block> oreStone(String name) {
        return REGISTRY.registerSimpleBlock(name,
                BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_ORE));
    }

    /** Iron-tier ore: hardness 3.0, resistance 3.0, requires iron pickaxe. */
    private static DeferredBlock<Block> oreIron(String name) {
        return REGISTRY.registerSimpleBlock(name,
                BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE));
    }

    /** Gem storage block: hardness 5.0, resistance 6.0, iron-tier — mirrors vanilla amethyst block. */
    private static DeferredBlock<Block> gemBlock(String name) {
        return REGISTRY.registerSimpleBlock(name,
                BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK));
    }

    /** Diamond-tier ore: hardness 3.0, resistance 3.0, requires diamond pickaxe. */
    private static DeferredBlock<Block> oreDiamond(String name) {
        return REGISTRY.registerSimpleBlock(name,
                BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE));
    }

    // ── Nightwood ──
    public static final DeferredBlock<Block> NIGHTWOOD_LOG            = logBlock("nightwood_log",            NightwoodLogBlock::new);
    public static final DeferredBlock<Block> NIGHTWOOD_WOOD           = logBlock("nightwood_wood",           NightwoodWoodBlock::new);
    public static final DeferredBlock<Block> NIGHTWOOD_PLANKS         = woodBlock("nightwood_planks",         NightwoodPlanksBlock::new);
    public static final DeferredBlock<Block> NIGHTWOOD_LEAVES         = woodBlock("nightwood_leaves",         NightwoodLeavesBlock::new);
    public static final DeferredBlock<Block> NIGHTWOOD_STAIRS         = woodBlock("nightwood_stairs",         p -> new NightwoodStairsBlock(NIGHTWOOD_PLANKS.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> NIGHTWOOD_SLAB           = woodBlock("nightwood_slab",           NightwoodSlabBlock::new);
    public static final DeferredBlock<Block> NIGHTWOOD_FENCE          = woodBlock("nightwood_fence",          NightwoodFenceBlock::new);
    public static final DeferredBlock<Block> NIGHTWOOD_FENCE_GATE     = woodBlock("nightwood_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.NIGHTWOOD, p));
    public static final DeferredBlock<Block> NIGHTWOOD_PRESSURE_PLATE = woodBlock("nightwood_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> NIGHTWOOD_BUTTON         = woodBlock("nightwood_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> NIGHTWOOD_DOOR           = doorBlock("nightwood_door",     p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> NIGHTWOOD_TRAPDOOR       = trapdoorBlock("nightwood_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> NIGHTWOOD_SIGN           = woodBlock("nightwood_sign",      p -> new GotStandingSignBlock(GotWoodTypes.NIGHTWOOD, p, () -> GotModBlockEntities.NIGHTWOOD_SIGN.get()));
    public static final DeferredBlock<Block> NIGHTWOOD_WALL_SIGN      = woodBlock("nightwood_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.NIGHTWOOD, p, () -> GotModBlockEntities.NIGHTWOOD_SIGN.get()));
    public static final DeferredBlock<Block> NIGHTWOOD_HANGING_SIGN      = woodBlock("nightwood_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.NIGHTWOOD, p));
    public static final DeferredBlock<Block> NIGHTWOOD_WALL_HANGING_SIGN = woodBlock("nightwood_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.NIGHTWOOD, p));
    public static final DeferredBlock<Block> NIGHTWOOD_BRANCH         = woodBlock("nightwood_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_NIGHTWOOD_BRANCH = woodBlock("stripped_nightwood_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> NIGHTWOOD_SAPLING        = woodBlock("nightwood_sapling",        p -> new GotSaplingBlock(GotTreeGrowers.NIGHTWOOD, p));
    public static final DeferredBlock<Block> STRIPPED_NIGHTWOOD_LOG   = logBlock("stripped_nightwood_log",   GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_NIGHTWOOD_WOOD  = logBlock("stripped_nightwood_wood",  GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> NIGHTWOOD_ROOFING        = REGISTRY.registerBlock("nightwood_roofing",        p -> new Block(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> NIGHTWOOD_ROOFING_SLAB   = REGISTRY.registerBlock("nightwood_roofing_slab",   p -> new SlabBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> NIGHTWOOD_ROOFING_STAIRS = REGISTRY.registerBlock("nightwood_roofing_stairs", p -> new StairBlock(NIGHTWOOD_ROOFING.get().defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> NIGHTWOOD_ROOFING_WALL   = REGISTRY.registerBlock("nightwood_roofing_wall",   p -> new WallBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    // LOGS.put("nightwood", NIGHTWOOD_LOG); STRIPPED_LOGS.put("nightwood", STRIPPED_NIGHTWOOD_LOG);
    // WOODS.put("nightwood", NIGHTWOOD_WOOD); STRIPPED_WOODS.put("nightwood", STRIPPED_NIGHTWOOD_WOOD);

    // ── Purpleheart ──
    public static final DeferredBlock<Block> PURPLEHEART_LOG            = logBlock("purpleheart_log",            PurpleheartLogBlock::new);
    public static final DeferredBlock<Block> PURPLEHEART_WOOD           = logBlock("purpleheart_wood",           PurpleheartWoodBlock::new);
    public static final DeferredBlock<Block> PURPLEHEART_PLANKS         = woodBlock("purpleheart_planks",         PurpleheartPlanksBlock::new);
    public static final DeferredBlock<Block> PURPLEHEART_LEAVES         = woodBlock("purpleheart_leaves",         PurpleheartLeavesBlock::new);
    public static final DeferredBlock<Block> PURPLEHEART_STAIRS         = woodBlock("purpleheart_stairs",         p -> new PurpleheartStairsBlock(PURPLEHEART_PLANKS.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> PURPLEHEART_SLAB           = woodBlock("purpleheart_slab",           PurpleheartSlabBlock::new);
    public static final DeferredBlock<Block> PURPLEHEART_FENCE          = woodBlock("purpleheart_fence",          PurpleheartFenceBlock::new);
    public static final DeferredBlock<Block> PURPLEHEART_FENCE_GATE     = woodBlock("purpleheart_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.PURPLEHEART, p));
    public static final DeferredBlock<Block> PURPLEHEART_PRESSURE_PLATE = woodBlock("purpleheart_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> PURPLEHEART_BUTTON         = woodBlock("purpleheart_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> PURPLEHEART_DOOR           = doorBlock("purpleheart_door",     p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> PURPLEHEART_TRAPDOOR       = trapdoorBlock("purpleheart_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> PURPLEHEART_SIGN           = woodBlock("purpleheart_sign",      p -> new GotStandingSignBlock(GotWoodTypes.PURPLEHEART, p, () -> GotModBlockEntities.PURPLEHEART_SIGN.get()));
    public static final DeferredBlock<Block> PURPLEHEART_WALL_SIGN      = woodBlock("purpleheart_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.PURPLEHEART, p, () -> GotModBlockEntities.PURPLEHEART_SIGN.get()));
    public static final DeferredBlock<Block> PURPLEHEART_HANGING_SIGN      = woodBlock("purpleheart_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.PURPLEHEART, p));
    public static final DeferredBlock<Block> PURPLEHEART_WALL_HANGING_SIGN = woodBlock("purpleheart_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.PURPLEHEART, p));
    public static final DeferredBlock<Block> PURPLEHEART_BRANCH         = woodBlock("purpleheart_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_PURPLEHEART_BRANCH = woodBlock("stripped_purpleheart_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> PURPLEHEART_SAPLING        = woodBlock("purpleheart_sapling",        p -> new GotSaplingBlock(GotTreeGrowers.PURPLEHEART, p));
    public static final DeferredBlock<Block> STRIPPED_PURPLEHEART_LOG   = logBlock("stripped_purpleheart_log",   GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_PURPLEHEART_WOOD  = logBlock("stripped_purpleheart_wood",  GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> PURPLEHEART_ROOFING        = REGISTRY.registerBlock("purpleheart_roofing",        p -> new Block(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> PURPLEHEART_ROOFING_SLAB   = REGISTRY.registerBlock("purpleheart_roofing_slab",   p -> new SlabBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> PURPLEHEART_ROOFING_STAIRS = REGISTRY.registerBlock("purpleheart_roofing_stairs", p -> new StairBlock(PURPLEHEART_ROOFING.get().defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> PURPLEHEART_ROOFING_WALL   = REGISTRY.registerBlock("purpleheart_roofing_wall",   p -> new WallBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    // LOGS.put("purpleheart", PURPLEHEART_LOG); STRIPPED_LOGS.put("purpleheart", STRIPPED_PURPLEHEART_LOG);
    // WOODS.put("purpleheart", PURPLEHEART_WOOD); STRIPPED_WOODS.put("purpleheart", STRIPPED_PURPLEHEART_WOOD);

    // ── Tigerwood ──
    public static final DeferredBlock<Block> TIGERWOOD_LOG            = logBlock("tigerwood_log",            TigerwoodLogBlock::new);
    public static final DeferredBlock<Block> TIGERWOOD_WOOD           = logBlock("tigerwood_wood",           TigerwoodWoodBlock::new);
    public static final DeferredBlock<Block> TIGERWOOD_PLANKS         = woodBlock("tigerwood_planks",         TigerwoodPlanksBlock::new);
    public static final DeferredBlock<Block> TIGERWOOD_LEAVES         = woodBlock("tigerwood_leaves",         TigerwoodLeavesBlock::new);
    public static final DeferredBlock<Block> TIGERWOOD_STAIRS         = woodBlock("tigerwood_stairs",         p -> new TigerwoodStairsBlock(TIGERWOOD_PLANKS.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> TIGERWOOD_SLAB           = woodBlock("tigerwood_slab",           TigerwoodSlabBlock::new);
    public static final DeferredBlock<Block> TIGERWOOD_FENCE          = woodBlock("tigerwood_fence",          TigerwoodFenceBlock::new);
    public static final DeferredBlock<Block> TIGERWOOD_FENCE_GATE     = woodBlock("tigerwood_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.TIGERWOOD, p));
    public static final DeferredBlock<Block> TIGERWOOD_PRESSURE_PLATE = woodBlock("tigerwood_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> TIGERWOOD_BUTTON         = woodBlock("tigerwood_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> TIGERWOOD_DOOR           = doorBlock("tigerwood_door",     p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> TIGERWOOD_TRAPDOOR       = trapdoorBlock("tigerwood_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> TIGERWOOD_SIGN           = woodBlock("tigerwood_sign",      p -> new GotStandingSignBlock(GotWoodTypes.TIGERWOOD, p, () -> GotModBlockEntities.TIGERWOOD_SIGN.get()));
    public static final DeferredBlock<Block> TIGERWOOD_WALL_SIGN      = woodBlock("tigerwood_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.TIGERWOOD, p, () -> GotModBlockEntities.TIGERWOOD_SIGN.get()));
    public static final DeferredBlock<Block> TIGERWOOD_HANGING_SIGN      = woodBlock("tigerwood_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.TIGERWOOD, p));
    public static final DeferredBlock<Block> TIGERWOOD_WALL_HANGING_SIGN = woodBlock("tigerwood_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.TIGERWOOD, p));
    public static final DeferredBlock<Block> TIGERWOOD_BRANCH         = woodBlock("tigerwood_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_TIGERWOOD_BRANCH = woodBlock("stripped_tigerwood_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> TIGERWOOD_SAPLING        = woodBlock("tigerwood_sapling",        p -> new GotSaplingBlock(GotTreeGrowers.TIGERWOOD, p));
    public static final DeferredBlock<Block> STRIPPED_TIGERWOOD_LOG   = logBlock("stripped_tigerwood_log",   GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_TIGERWOOD_WOOD  = logBlock("stripped_tigerwood_wood",  GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> TIGERWOOD_ROOFING        = REGISTRY.registerBlock("tigerwood_roofing",        p -> new Block(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> TIGERWOOD_ROOFING_SLAB   = REGISTRY.registerBlock("tigerwood_roofing_slab",   p -> new SlabBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> TIGERWOOD_ROOFING_STAIRS = REGISTRY.registerBlock("tigerwood_roofing_stairs", p -> new StairBlock(TIGERWOOD_ROOFING.get().defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> TIGERWOOD_ROOFING_WALL   = REGISTRY.registerBlock("tigerwood_roofing_wall",   p -> new WallBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    // LOGS.put("tigerwood", TIGERWOOD_LOG); STRIPPED_LOGS.put("tigerwood", STRIPPED_TIGERWOOD_LOG);
    // WOODS.put("tigerwood", TIGERWOOD_WOOD); STRIPPED_WOODS.put("tigerwood", STRIPPED_TIGERWOOD_WOOD);

    // ── Burl ──
    public static final DeferredBlock<Block> BURL_LOG            = logBlock("burl_log",            BurlLogBlock::new);
    public static final DeferredBlock<Block> BURL_WOOD           = logBlock("burl_wood",           BurlWoodBlock::new);
    public static final DeferredBlock<Block> BURL_PLANKS         = woodBlock("burl_planks",         BurlPlanksBlock::new);
    public static final DeferredBlock<Block> BURL_LEAVES         = woodBlock("burl_leaves",         BurlLeavesBlock::new);
    public static final DeferredBlock<Block> BURL_STAIRS         = woodBlock("burl_stairs",         p -> new BurlStairsBlock(BURL_PLANKS.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> BURL_SLAB           = woodBlock("burl_slab",           BurlSlabBlock::new);
    public static final DeferredBlock<Block> BURL_FENCE          = woodBlock("burl_fence",          BurlFenceBlock::new);
    public static final DeferredBlock<Block> BURL_FENCE_GATE     = woodBlock("burl_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.BURL, p));
    public static final DeferredBlock<Block> BURL_PRESSURE_PLATE = woodBlock("burl_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> BURL_BUTTON         = woodBlock("burl_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> BURL_DOOR           = doorBlock("burl_door",     p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> BURL_TRAPDOOR       = trapdoorBlock("burl_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> BURL_SIGN           = woodBlock("burl_sign",      p -> new GotStandingSignBlock(GotWoodTypes.BURL, p, () -> GotModBlockEntities.BURL_SIGN.get()));
    public static final DeferredBlock<Block> BURL_WALL_SIGN      = woodBlock("burl_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.BURL, p, () -> GotModBlockEntities.BURL_SIGN.get()));
    public static final DeferredBlock<Block> BURL_HANGING_SIGN      = woodBlock("burl_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.BURL, p));
    public static final DeferredBlock<Block> BURL_WALL_HANGING_SIGN = woodBlock("burl_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.BURL, p));
    public static final DeferredBlock<Block> BURL_BRANCH         = woodBlock("burl_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_BURL_BRANCH = woodBlock("stripped_burl_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> BURL_SAPLING        = woodBlock("burl_sapling",        p -> new GotSaplingBlock(GotTreeGrowers.BURL, p));
    public static final DeferredBlock<Block> STRIPPED_BURL_LOG   = logBlock("stripped_burl_log",   GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_BURL_WOOD  = logBlock("stripped_burl_wood",  GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> BURL_ROOFING        = REGISTRY.registerBlock("burl_roofing",        p -> new Block(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> BURL_ROOFING_SLAB   = REGISTRY.registerBlock("burl_roofing_slab",   p -> new SlabBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> BURL_ROOFING_STAIRS = REGISTRY.registerBlock("burl_roofing_stairs", p -> new StairBlock(BURL_ROOFING.get().defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> BURL_ROOFING_WALL   = REGISTRY.registerBlock("burl_roofing_wall",   p -> new WallBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    // LOGS.put("burl", BURL_LOG); STRIPPED_LOGS.put("burl", STRIPPED_BURL_LOG);
    // WOODS.put("burl", BURL_WOOD); STRIPPED_WOODS.put("burl", STRIPPED_BURL_WOOD);

    // ── Sandalwood ──
    public static final DeferredBlock<Block> SANDALWOOD_LOG            = logBlock("sandalwood_log",            SandalwoodLogBlock::new);
    public static final DeferredBlock<Block> SANDALWOOD_WOOD           = logBlock("sandalwood_wood",           SandalwoodWoodBlock::new);
    public static final DeferredBlock<Block> SANDALWOOD_PLANKS         = woodBlock("sandalwood_planks",         SandalwoodPlanksBlock::new);
    public static final DeferredBlock<Block> SANDALWOOD_LEAVES         = woodBlock("sandalwood_leaves",         SandalwoodLeavesBlock::new);
    public static final DeferredBlock<Block> SANDALWOOD_STAIRS         = woodBlock("sandalwood_stairs",         p -> new SandalwoodStairsBlock(SANDALWOOD_PLANKS.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> SANDALWOOD_SLAB           = woodBlock("sandalwood_slab",           SandalwoodSlabBlock::new);
    public static final DeferredBlock<Block> SANDALWOOD_FENCE          = woodBlock("sandalwood_fence",          SandalwoodFenceBlock::new);
    public static final DeferredBlock<Block> SANDALWOOD_FENCE_GATE     = woodBlock("sandalwood_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.SANDALWOOD, p));
    public static final DeferredBlock<Block> SANDALWOOD_PRESSURE_PLATE = woodBlock("sandalwood_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> SANDALWOOD_BUTTON         = woodBlock("sandalwood_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> SANDALWOOD_DOOR           = doorBlock("sandalwood_door",     p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> SANDALWOOD_TRAPDOOR       = trapdoorBlock("sandalwood_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> SANDALWOOD_SIGN           = woodBlock("sandalwood_sign",      p -> new GotStandingSignBlock(GotWoodTypes.SANDALWOOD, p, () -> GotModBlockEntities.SANDALWOOD_SIGN.get()));
    public static final DeferredBlock<Block> SANDALWOOD_WALL_SIGN      = woodBlock("sandalwood_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.SANDALWOOD, p, () -> GotModBlockEntities.SANDALWOOD_SIGN.get()));
    public static final DeferredBlock<Block> SANDALWOOD_HANGING_SIGN      = woodBlock("sandalwood_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.SANDALWOOD, p));
    public static final DeferredBlock<Block> SANDALWOOD_WALL_HANGING_SIGN = woodBlock("sandalwood_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.SANDALWOOD, p));
    public static final DeferredBlock<Block> SANDALWOOD_BRANCH         = woodBlock("sandalwood_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_SANDALWOOD_BRANCH = woodBlock("stripped_sandalwood_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> SANDALWOOD_SAPLING        = woodBlock("sandalwood_sapling",        p -> new GotSaplingBlock(GotTreeGrowers.SANDALWOOD, p));
    public static final DeferredBlock<Block> STRIPPED_SANDALWOOD_LOG   = logBlock("stripped_sandalwood_log",   GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_SANDALWOOD_WOOD  = logBlock("stripped_sandalwood_wood",  GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> SANDALWOOD_ROOFING        = REGISTRY.registerBlock("sandalwood_roofing",        p -> new Block(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> SANDALWOOD_ROOFING_SLAB   = REGISTRY.registerBlock("sandalwood_roofing_slab",   p -> new SlabBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> SANDALWOOD_ROOFING_STAIRS = REGISTRY.registerBlock("sandalwood_roofing_stairs", p -> new StairBlock(SANDALWOOD_ROOFING.get().defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> SANDALWOOD_ROOFING_WALL   = REGISTRY.registerBlock("sandalwood_roofing_wall",   p -> new WallBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    // LOGS.put("sandalwood", SANDALWOOD_LOG); STRIPPED_LOGS.put("sandalwood", STRIPPED_SANDALWOOD_LOG);
    // WOODS.put("sandalwood", SANDALWOOD_WOOD); STRIPPED_WOODS.put("sandalwood", STRIPPED_SANDALWOOD_WOOD);

    // ── Sandbeggar ──
    public static final DeferredBlock<Block> SANDBEGGAR_LOG            = logBlock("sandbeggar_log",            SandbeggarLogBlock::new);
    public static final DeferredBlock<Block> SANDBEGGAR_WOOD           = logBlock("sandbeggar_wood",           SandbeggarWoodBlock::new);
    public static final DeferredBlock<Block> SANDBEGGAR_PLANKS         = woodBlock("sandbeggar_planks",         SandbeggarPlanksBlock::new);
    public static final DeferredBlock<Block> SANDBEGGAR_LEAVES         = woodBlock("sandbeggar_leaves",         SandbeggarLeavesBlock::new);
    public static final DeferredBlock<Block> SANDBEGGAR_STAIRS         = woodBlock("sandbeggar_stairs",         p -> new SandbeggarStairsBlock(SANDBEGGAR_PLANKS.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> SANDBEGGAR_SLAB           = woodBlock("sandbeggar_slab",           SandbeggarSlabBlock::new);
    public static final DeferredBlock<Block> SANDBEGGAR_FENCE          = woodBlock("sandbeggar_fence",          SandbeggarFenceBlock::new);
    public static final DeferredBlock<Block> SANDBEGGAR_FENCE_GATE     = woodBlock("sandbeggar_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.SANDBEGGAR, p));
    public static final DeferredBlock<Block> SANDBEGGAR_PRESSURE_PLATE = woodBlock("sandbeggar_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> SANDBEGGAR_BUTTON         = woodBlock("sandbeggar_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> SANDBEGGAR_DOOR           = doorBlock("sandbeggar_door",     p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> SANDBEGGAR_TRAPDOOR       = trapdoorBlock("sandbeggar_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> SANDBEGGAR_SIGN           = woodBlock("sandbeggar_sign",      p -> new GotStandingSignBlock(GotWoodTypes.SANDBEGGAR, p, () -> GotModBlockEntities.SANDBEGGAR_SIGN.get()));
    public static final DeferredBlock<Block> SANDBEGGAR_WALL_SIGN      = woodBlock("sandbeggar_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.SANDBEGGAR, p, () -> GotModBlockEntities.SANDBEGGAR_SIGN.get()));
    public static final DeferredBlock<Block> SANDBEGGAR_HANGING_SIGN      = woodBlock("sandbeggar_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.SANDBEGGAR, p));
    public static final DeferredBlock<Block> SANDBEGGAR_WALL_HANGING_SIGN = woodBlock("sandbeggar_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.SANDBEGGAR, p));
    public static final DeferredBlock<Block> SANDBEGGAR_BRANCH         = woodBlock("sandbeggar_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_SANDBEGGAR_BRANCH = woodBlock("stripped_sandbeggar_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> SANDBEGGAR_SAPLING        = woodBlock("sandbeggar_sapling",        p -> new GotSaplingBlock(GotTreeGrowers.SANDBEGGAR, p));
    public static final DeferredBlock<Block> STRIPPED_SANDBEGGAR_LOG   = logBlock("stripped_sandbeggar_log",   GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_SANDBEGGAR_WOOD  = logBlock("stripped_sandbeggar_wood",  GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> SANDBEGGAR_ROOFING        = REGISTRY.registerBlock("sandbeggar_roofing",        p -> new Block(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> SANDBEGGAR_ROOFING_SLAB   = REGISTRY.registerBlock("sandbeggar_roofing_slab",   p -> new SlabBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> SANDBEGGAR_ROOFING_STAIRS = REGISTRY.registerBlock("sandbeggar_roofing_stairs", p -> new StairBlock(SANDBEGGAR_ROOFING.get().defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> SANDBEGGAR_ROOFING_WALL   = REGISTRY.registerBlock("sandbeggar_roofing_wall",   p -> new WallBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    // LOGS.put("sandbeggar", SANDBEGGAR_LOG); STRIPPED_LOGS.put("sandbeggar", STRIPPED_SANDBEGGAR_LOG);
    // WOODS.put("sandbeggar", SANDBEGGAR_WOOD); STRIPPED_WOODS.put("sandbeggar", STRIPPED_SANDBEGGAR_WOOD);

    // ── Apricot ──
    public static final DeferredBlock<Block> APRICOT_LOG            = logBlock("apricot_log",            ApricotLogBlock::new);
    public static final DeferredBlock<Block> APRICOT_WOOD           = logBlock("apricot_wood",           ApricotWoodBlock::new);
    public static final DeferredBlock<Block> APRICOT_PLANKS         = woodBlock("apricot_planks",         ApricotPlanksBlock::new);
    public static final DeferredBlock<Block> APRICOT_LEAVES         = woodBlock("apricot_leaves",         ApricotLeavesBlock::new);
    public static final DeferredBlock<Block> APRICOT_STAIRS         = woodBlock("apricot_stairs",         p -> new ApricotStairsBlock(APRICOT_PLANKS.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> APRICOT_SLAB           = woodBlock("apricot_slab",           ApricotSlabBlock::new);
    public static final DeferredBlock<Block> APRICOT_FENCE          = woodBlock("apricot_fence",          ApricotFenceBlock::new);
    public static final DeferredBlock<Block> APRICOT_FENCE_GATE     = woodBlock("apricot_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.APRICOT, p));
    public static final DeferredBlock<Block> APRICOT_PRESSURE_PLATE = woodBlock("apricot_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> APRICOT_BUTTON         = woodBlock("apricot_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> APRICOT_DOOR           = doorBlock("apricot_door",     p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> APRICOT_TRAPDOOR       = trapdoorBlock("apricot_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> APRICOT_SIGN           = woodBlock("apricot_sign",      p -> new GotStandingSignBlock(GotWoodTypes.APRICOT, p, () -> GotModBlockEntities.APRICOT_SIGN.get()));
    public static final DeferredBlock<Block> APRICOT_WALL_SIGN      = woodBlock("apricot_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.APRICOT, p, () -> GotModBlockEntities.APRICOT_SIGN.get()));
    public static final DeferredBlock<Block> APRICOT_HANGING_SIGN      = woodBlock("apricot_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.APRICOT, p));
    public static final DeferredBlock<Block> APRICOT_WALL_HANGING_SIGN = woodBlock("apricot_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.APRICOT, p));
    public static final DeferredBlock<Block> APRICOT_BRANCH         = woodBlock("apricot_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_APRICOT_BRANCH = woodBlock("stripped_apricot_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> APRICOT_SAPLING        = woodBlock("apricot_sapling",        p -> new GotSaplingBlock(GotTreeGrowers.APRICOT, p));
    public static final DeferredBlock<Block> STRIPPED_APRICOT_LOG   = logBlock("stripped_apricot_log",   GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_APRICOT_WOOD  = logBlock("stripped_apricot_wood",  GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> APRICOT_ROOFING        = REGISTRY.registerBlock("apricot_roofing",        p -> new Block(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> APRICOT_ROOFING_SLAB   = REGISTRY.registerBlock("apricot_roofing_slab",   p -> new SlabBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> APRICOT_ROOFING_STAIRS = REGISTRY.registerBlock("apricot_roofing_stairs", p -> new StairBlock(APRICOT_ROOFING.get().defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> APRICOT_ROOFING_WALL   = REGISTRY.registerBlock("apricot_roofing_wall",   p -> new WallBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    // LOGS.put("apricot", APRICOT_LOG); STRIPPED_LOGS.put("apricot", STRIPPED_APRICOT_LOG);
    // WOODS.put("apricot", APRICOT_WOOD); STRIPPED_WOODS.put("apricot", STRIPPED_APRICOT_WOOD);

    // ── Blackthorn ──
    public static final DeferredBlock<Block> BLACKTHORN_LOG            = logBlock("blackthorn_log",            BlackthornLogBlock::new);
    public static final DeferredBlock<Block> BLACKTHORN_WOOD           = logBlock("blackthorn_wood",           BlackthornWoodBlock::new);
    public static final DeferredBlock<Block> BLACKTHORN_PLANKS         = woodBlock("blackthorn_planks",         BlackthornPlanksBlock::new);
    public static final DeferredBlock<Block> BLACKTHORN_LEAVES         = woodBlock("blackthorn_leaves",         BlackthornLeavesBlock::new);
    public static final DeferredBlock<Block> BLACKTHORN_STAIRS         = woodBlock("blackthorn_stairs",         p -> new BlackthornStairsBlock(BLACKTHORN_PLANKS.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> BLACKTHORN_SLAB           = woodBlock("blackthorn_slab",           BlackthornSlabBlock::new);
    public static final DeferredBlock<Block> BLACKTHORN_FENCE          = woodBlock("blackthorn_fence",          BlackthornFenceBlock::new);
    public static final DeferredBlock<Block> BLACKTHORN_FENCE_GATE     = woodBlock("blackthorn_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.BLACKTHORN, p));
    public static final DeferredBlock<Block> BLACKTHORN_PRESSURE_PLATE = woodBlock("blackthorn_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> BLACKTHORN_BUTTON         = woodBlock("blackthorn_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> BLACKTHORN_DOOR           = doorBlock("blackthorn_door",     p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> BLACKTHORN_TRAPDOOR       = trapdoorBlock("blackthorn_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> BLACKTHORN_SIGN           = woodBlock("blackthorn_sign",      p -> new GotStandingSignBlock(GotWoodTypes.BLACKTHORN, p, () -> GotModBlockEntities.BLACKTHORN_SIGN.get()));
    public static final DeferredBlock<Block> BLACKTHORN_WALL_SIGN      = woodBlock("blackthorn_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.BLACKTHORN, p, () -> GotModBlockEntities.BLACKTHORN_SIGN.get()));
    public static final DeferredBlock<Block> BLACKTHORN_HANGING_SIGN      = woodBlock("blackthorn_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.BLACKTHORN, p));
    public static final DeferredBlock<Block> BLACKTHORN_WALL_HANGING_SIGN = woodBlock("blackthorn_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.BLACKTHORN, p));
    public static final DeferredBlock<Block> BLACKTHORN_BRANCH         = woodBlock("blackthorn_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_BLACKTHORN_BRANCH = woodBlock("stripped_blackthorn_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> BLACKTHORN_SAPLING        = woodBlock("blackthorn_sapling",        p -> new GotSaplingBlock(GotTreeGrowers.BLACKTHORN, p));
    public static final DeferredBlock<Block> STRIPPED_BLACKTHORN_LOG   = logBlock("stripped_blackthorn_log",   GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_BLACKTHORN_WOOD  = logBlock("stripped_blackthorn_wood",  GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> BLACKTHORN_ROOFING        = REGISTRY.registerBlock("blackthorn_roofing",        p -> new Block(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> BLACKTHORN_ROOFING_SLAB   = REGISTRY.registerBlock("blackthorn_roofing_slab",   p -> new SlabBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> BLACKTHORN_ROOFING_STAIRS = REGISTRY.registerBlock("blackthorn_roofing_stairs", p -> new StairBlock(BLACKTHORN_ROOFING.get().defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> BLACKTHORN_ROOFING_WALL   = REGISTRY.registerBlock("blackthorn_roofing_wall",   p -> new WallBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    // LOGS.put("blackthorn", BLACKTHORN_LOG); STRIPPED_LOGS.put("blackthorn", STRIPPED_BLACKTHORN_LOG);
    // WOODS.put("blackthorn", BLACKTHORN_WOOD); STRIPPED_WOODS.put("blackthorn", STRIPPED_BLACKTHORN_WOOD);

    // ── Cherry ──
    public static final DeferredBlock<Block> RED_CHERRY_LOG            = logBlock("red_cherry_log",            CherryLogBlock::new);
    public static final DeferredBlock<Block> RED_CHERRY_WOOD           = logBlock("red_cherry_wood",           CherryWoodBlock::new);
    public static final DeferredBlock<Block> RED_CHERRY_PLANKS         = woodBlock("red_cherry_planks",         CherryPlanksBlock::new);
    public static final DeferredBlock<Block> RED_CHERRY_LEAVES         = woodBlock("red_cherry_leaves",         CherryLeavesBlock::new);
    public static final DeferredBlock<Block> RED_CHERRY_STAIRS         = woodBlock("red_cherry_stairs",         p -> new CherryStairsBlock(RED_CHERRY_PLANKS.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> RED_CHERRY_SLAB           = woodBlock("red_cherry_slab",           CherrySlabBlock::new);
    public static final DeferredBlock<Block> RED_CHERRY_FENCE          = woodBlock("red_cherry_fence",          CherryFenceBlock::new);
    public static final DeferredBlock<Block> RED_CHERRY_FENCE_GATE     = woodBlock("red_cherry_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.RED_CHERRY, p));
    public static final DeferredBlock<Block> RED_CHERRY_PRESSURE_PLATE = woodBlock("red_cherry_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> RED_CHERRY_BUTTON         = woodBlock("red_cherry_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> RED_CHERRY_DOOR           = doorBlock("red_cherry_door",     p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> RED_CHERRY_TRAPDOOR       = trapdoorBlock("red_cherry_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> RED_CHERRY_SIGN           = woodBlock("red_cherry_sign",      p -> new GotStandingSignBlock(GotWoodTypes.RED_CHERRY, p, () -> GotModBlockEntities.RED_CHERRY_SIGN.get()));
    public static final DeferredBlock<Block> RED_CHERRY_WALL_SIGN      = woodBlock("red_cherry_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.RED_CHERRY, p, () -> GotModBlockEntities.RED_CHERRY_SIGN.get()));
    public static final DeferredBlock<Block> RED_CHERRY_HANGING_SIGN      = woodBlock("red_cherry_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.RED_CHERRY, p));
    public static final DeferredBlock<Block> RED_CHERRY_WALL_HANGING_SIGN = woodBlock("red_cherry_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.RED_CHERRY, p));
    public static final DeferredBlock<Block> RED_CHERRY_BRANCH         = woodBlock("red_cherry_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_RED_CHERRY_BRANCH = woodBlock("stripped_red_cherry_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> RED_CHERRY_SAPLING        = woodBlock("red_cherry_sapling",        p -> new GotSaplingBlock(GotTreeGrowers.RED_CHERRY, p));
    public static final DeferredBlock<Block> STRIPPED_RED_CHERRY_LOG   = logBlock("stripped_red_cherry_log",   GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_RED_CHERRY_WOOD  = logBlock("stripped_red_cherry_wood",  GotStrippedLogBlock::new);

    // ── black_cherry ──────────────────────────────────────────────────────────
    public static final DeferredBlock<Block> BLACK_CHERRY_LOG            = logBlock("black_cherry_log",            CherryLogBlock::new);
    public static final DeferredBlock<Block> BLACK_CHERRY_WOOD           = logBlock("black_cherry_wood",           CherryWoodBlock::new);
    public static final DeferredBlock<Block> BLACK_CHERRY_PLANKS         = woodBlock("black_cherry_planks",         CherryPlanksBlock::new);
    public static final DeferredBlock<Block> BLACK_CHERRY_LEAVES         = woodBlock("black_cherry_leaves",         CherryLeavesBlock::new);
    public static final DeferredBlock<Block> BLACK_CHERRY_STAIRS         = woodBlock("black_cherry_stairs",         p -> new CherryStairsBlock(BLACK_CHERRY_PLANKS.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> BLACK_CHERRY_SLAB           = woodBlock("black_cherry_slab",           CherrySlabBlock::new);
    public static final DeferredBlock<Block> BLACK_CHERRY_FENCE          = woodBlock("black_cherry_fence",          CherryFenceBlock::new);
    public static final DeferredBlock<Block> BLACK_CHERRY_FENCE_GATE     = woodBlock("black_cherry_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.BLACK_CHERRY, p));
    public static final DeferredBlock<Block> BLACK_CHERRY_PRESSURE_PLATE = woodBlock("black_cherry_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> BLACK_CHERRY_BUTTON         = woodBlock("black_cherry_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> BLACK_CHERRY_DOOR           = doorBlock("black_cherry_door",     p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> BLACK_CHERRY_TRAPDOOR       = trapdoorBlock("black_cherry_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> BLACK_CHERRY_SIGN           = woodBlock("black_cherry_sign",      p -> new GotStandingSignBlock(GotWoodTypes.BLACK_CHERRY, p, () -> GotModBlockEntities.BLACK_CHERRY_SIGN.get()));
    public static final DeferredBlock<Block> BLACK_CHERRY_WALL_SIGN      = woodBlock("black_cherry_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.BLACK_CHERRY, p, () -> GotModBlockEntities.BLACK_CHERRY_SIGN.get()));
    public static final DeferredBlock<Block> BLACK_CHERRY_HANGING_SIGN      = woodBlock("black_cherry_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.BLACK_CHERRY, p));
    public static final DeferredBlock<Block> BLACK_CHERRY_WALL_HANGING_SIGN = woodBlock("black_cherry_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.BLACK_CHERRY, p));
    public static final DeferredBlock<Block> BLACK_CHERRY_BRANCH         = woodBlock("black_cherry_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_BLACK_CHERRY_BRANCH = woodBlock("stripped_black_cherry_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> BLACK_CHERRY_SAPLING        = woodBlock("black_cherry_sapling",        p -> new GotSaplingBlock(GotTreeGrowers.BLACK_CHERRY, p));
    public static final DeferredBlock<Block> STRIPPED_BLACK_CHERRY_LOG   = logBlock("stripped_black_cherry_log",   GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_BLACK_CHERRY_WOOD  = logBlock("stripped_black_cherry_wood",  GotStrippedLogBlock::new);

    // ── white_cherry ──────────────────────────────────────────────────────────
    public static final DeferredBlock<Block> WHITE_CHERRY_LOG            = logBlock("white_cherry_log",            CherryLogBlock::new);
    public static final DeferredBlock<Block> WHITE_CHERRY_WOOD           = logBlock("white_cherry_wood",           CherryWoodBlock::new);
    public static final DeferredBlock<Block> WHITE_CHERRY_PLANKS         = woodBlock("white_cherry_planks",         CherryPlanksBlock::new);
    public static final DeferredBlock<Block> WHITE_CHERRY_LEAVES         = woodBlock("white_cherry_leaves",         CherryLeavesBlock::new);
    public static final DeferredBlock<Block> WHITE_CHERRY_STAIRS         = woodBlock("white_cherry_stairs",         p -> new CherryStairsBlock(WHITE_CHERRY_PLANKS.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> WHITE_CHERRY_SLAB           = woodBlock("white_cherry_slab",           CherrySlabBlock::new);
    public static final DeferredBlock<Block> WHITE_CHERRY_FENCE          = woodBlock("white_cherry_fence",          CherryFenceBlock::new);
    public static final DeferredBlock<Block> WHITE_CHERRY_FENCE_GATE     = woodBlock("white_cherry_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.WHITE_CHERRY, p));
    public static final DeferredBlock<Block> WHITE_CHERRY_PRESSURE_PLATE = woodBlock("white_cherry_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> WHITE_CHERRY_BUTTON         = woodBlock("white_cherry_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> WHITE_CHERRY_DOOR           = doorBlock("white_cherry_door",     p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> WHITE_CHERRY_TRAPDOOR       = trapdoorBlock("white_cherry_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> WHITE_CHERRY_SIGN           = woodBlock("white_cherry_sign",      p -> new GotStandingSignBlock(GotWoodTypes.WHITE_CHERRY, p, () -> GotModBlockEntities.WHITE_CHERRY_SIGN.get()));
    public static final DeferredBlock<Block> WHITE_CHERRY_WALL_SIGN      = woodBlock("white_cherry_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.WHITE_CHERRY, p, () -> GotModBlockEntities.WHITE_CHERRY_SIGN.get()));
    public static final DeferredBlock<Block> WHITE_CHERRY_HANGING_SIGN      = woodBlock("white_cherry_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.WHITE_CHERRY, p));
    public static final DeferredBlock<Block> WHITE_CHERRY_WALL_HANGING_SIGN = woodBlock("white_cherry_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.WHITE_CHERRY, p));
    public static final DeferredBlock<Block> WHITE_CHERRY_BRANCH         = woodBlock("white_cherry_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_WHITE_CHERRY_BRANCH = woodBlock("stripped_white_cherry_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> WHITE_CHERRY_SAPLING        = woodBlock("white_cherry_sapling",        p -> new GotSaplingBlock(GotTreeGrowers.WHITE_CHERRY, p));
    public static final DeferredBlock<Block> STRIPPED_WHITE_CHERRY_LOG   = logBlock("stripped_white_cherry_log",   GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_WHITE_CHERRY_WOOD  = logBlock("stripped_white_cherry_wood",  GotStrippedLogBlock::new);

    // LOGS.put("red_cherry", RED_CHERRY_LOG); STRIPPED_LOGS.put("red_cherry", STRIPPED_RED_CHERRY_LOG);
    // WOODS.put("red_cherry", RED_CHERRY_WOOD); STRIPPED_WOODS.put("red_cherry", STRIPPED_RED_CHERRY_WOOD);

    // ── Crabapple ──
    public static final DeferredBlock<Block> CRABAPPLE_LOG            = logBlock("crabapple_log",            CrabappleLogBlock::new);
    public static final DeferredBlock<Block> CRABAPPLE_WOOD           = logBlock("crabapple_wood",           CrabappleWoodBlock::new);
    public static final DeferredBlock<Block> CRABAPPLE_PLANKS         = woodBlock("crabapple_planks",         CrabapplePlanksBlock::new);
    public static final DeferredBlock<Block> CRABAPPLE_LEAVES         = woodBlock("crabapple_leaves",         CrabappleLeavesBlock::new);
    public static final DeferredBlock<Block> CRABAPPLE_STAIRS         = woodBlock("crabapple_stairs",         p -> new CrabappleStairsBlock(CRABAPPLE_PLANKS.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> CRABAPPLE_SLAB           = woodBlock("crabapple_slab",           CrabappleSlabBlock::new);
    public static final DeferredBlock<Block> CRABAPPLE_FENCE          = woodBlock("crabapple_fence",          CrabappleFenceBlock::new);
    public static final DeferredBlock<Block> CRABAPPLE_FENCE_GATE     = woodBlock("crabapple_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.CRABAPPLE, p));
    public static final DeferredBlock<Block> CRABAPPLE_PRESSURE_PLATE = woodBlock("crabapple_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> CRABAPPLE_BUTTON         = woodBlock("crabapple_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> CRABAPPLE_DOOR           = doorBlock("crabapple_door",     p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> CRABAPPLE_TRAPDOOR       = trapdoorBlock("crabapple_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> CRABAPPLE_SIGN           = woodBlock("crabapple_sign",      p -> new GotStandingSignBlock(GotWoodTypes.CRABAPPLE, p, () -> GotModBlockEntities.CRABAPPLE_SIGN.get()));
    public static final DeferredBlock<Block> CRABAPPLE_WALL_SIGN      = woodBlock("crabapple_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.CRABAPPLE, p, () -> GotModBlockEntities.CRABAPPLE_SIGN.get()));
    public static final DeferredBlock<Block> CRABAPPLE_HANGING_SIGN      = woodBlock("crabapple_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.CRABAPPLE, p));
    public static final DeferredBlock<Block> CRABAPPLE_WALL_HANGING_SIGN = woodBlock("crabapple_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.CRABAPPLE, p));
    public static final DeferredBlock<Block> CRABAPPLE_BRANCH         = woodBlock("crabapple_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_CRABAPPLE_BRANCH = woodBlock("stripped_crabapple_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> CRABAPPLE_SAPLING        = woodBlock("crabapple_sapling",        p -> new GotSaplingBlock(GotTreeGrowers.CRABAPPLE, p));
    public static final DeferredBlock<Block> STRIPPED_CRABAPPLE_LOG   = logBlock("stripped_crabapple_log",   GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_CRABAPPLE_WOOD  = logBlock("stripped_crabapple_wood",  GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> CRABAPPLE_ROOFING        = REGISTRY.registerBlock("crabapple_roofing",        p -> new Block(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> CRABAPPLE_ROOFING_SLAB   = REGISTRY.registerBlock("crabapple_roofing_slab",   p -> new SlabBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> CRABAPPLE_ROOFING_STAIRS = REGISTRY.registerBlock("crabapple_roofing_stairs", p -> new StairBlock(CRABAPPLE_ROOFING.get().defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> CRABAPPLE_ROOFING_WALL   = REGISTRY.registerBlock("crabapple_roofing_wall",   p -> new WallBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    // LOGS.put("crabapple", CRABAPPLE_LOG); STRIPPED_LOGS.put("crabapple", STRIPPED_CRABAPPLE_LOG);
    // WOODS.put("crabapple", CRABAPPLE_WOOD); STRIPPED_WOODS.put("crabapple", STRIPPED_CRABAPPLE_WOOD);

    // ── DatePalm ──
    public static final DeferredBlock<Block> DATE_PALM_LOG            = logBlock("date_palm_log",            DatePalmLogBlock::new);
    public static final DeferredBlock<Block> DATE_PALM_WOOD           = logBlock("date_palm_wood",           DatePalmWoodBlock::new);
    public static final DeferredBlock<Block> DATE_PALM_PLANKS         = woodBlock("date_palm_planks",         DatePalmPlanksBlock::new);
    public static final DeferredBlock<Block> DATE_PALM_LEAVES         = woodBlock("date_palm_leaves",         DatePalmLeavesBlock::new);
    public static final DeferredBlock<Block> DATE_PALM_STAIRS         = woodBlock("date_palm_stairs",         p -> new DatePalmStairsBlock(DATE_PALM_PLANKS.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> DATE_PALM_SLAB           = woodBlock("date_palm_slab",           DatePalmSlabBlock::new);
    public static final DeferredBlock<Block> DATE_PALM_FENCE          = woodBlock("date_palm_fence",          DatePalmFenceBlock::new);
    public static final DeferredBlock<Block> DATE_PALM_FENCE_GATE     = woodBlock("date_palm_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.DATE_PALM, p));
    public static final DeferredBlock<Block> DATE_PALM_PRESSURE_PLATE = woodBlock("date_palm_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> DATE_PALM_BUTTON         = woodBlock("date_palm_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> DATE_PALM_DOOR           = doorBlock("date_palm_door",     p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> DATE_PALM_TRAPDOOR       = trapdoorBlock("date_palm_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> DATE_PALM_SIGN           = woodBlock("date_palm_sign",      p -> new GotStandingSignBlock(GotWoodTypes.DATE_PALM, p, () -> GotModBlockEntities.DATE_PALM_SIGN.get()));
    public static final DeferredBlock<Block> DATE_PALM_WALL_SIGN      = woodBlock("date_palm_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.DATE_PALM, p, () -> GotModBlockEntities.DATE_PALM_SIGN.get()));
    public static final DeferredBlock<Block> DATE_PALM_HANGING_SIGN      = woodBlock("date_palm_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.DATE_PALM, p));
    public static final DeferredBlock<Block> DATE_PALM_WALL_HANGING_SIGN = woodBlock("date_palm_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.DATE_PALM, p));
    public static final DeferredBlock<Block> DATE_PALM_BRANCH         = woodBlock("date_palm_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_DATE_PALM_BRANCH = woodBlock("stripped_date_palm_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> DATE_PALM_SAPLING        = woodBlock("date_palm_sapling",        p -> new GotSaplingBlock(GotTreeGrowers.DATE_PALM, p));
    public static final DeferredBlock<Block> STRIPPED_DATE_PALM_LOG   = logBlock("stripped_date_palm_log",   GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_DATE_PALM_WOOD  = logBlock("stripped_date_palm_wood",  GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> DATE_PALM_ROOFING        = REGISTRY.registerBlock("date_palm_roofing",        p -> new Block(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> DATE_PALM_ROOFING_SLAB   = REGISTRY.registerBlock("date_palm_roofing_slab",   p -> new SlabBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> DATE_PALM_ROOFING_STAIRS = REGISTRY.registerBlock("date_palm_roofing_stairs", p -> new StairBlock(DATE_PALM_ROOFING.get().defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> DATE_PALM_ROOFING_WALL   = REGISTRY.registerBlock("date_palm_roofing_wall",   p -> new WallBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    // LOGS.put("date_palm", DATE_PALM_LOG); STRIPPED_LOGS.put("date_palm", STRIPPED_DATE_PALM_LOG);
    // WOODS.put("date_palm", DATE_PALM_WOOD); STRIPPED_WOODS.put("date_palm", STRIPPED_DATE_PALM_WOOD);

    // ── Fig ──
    public static final DeferredBlock<Block> FIG_LOG            = logBlock("fig_log",            FigLogBlock::new);
    public static final DeferredBlock<Block> FIG_WOOD           = logBlock("fig_wood",           FigWoodBlock::new);
    public static final DeferredBlock<Block> FIG_PLANKS         = woodBlock("fig_planks",         FigPlanksBlock::new);
    public static final DeferredBlock<Block> FIG_LEAVES         = woodBlock("fig_leaves",         FigLeavesBlock::new);
    public static final DeferredBlock<Block> FIG_STAIRS         = woodBlock("fig_stairs",         p -> new FigStairsBlock(FIG_PLANKS.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> FIG_SLAB           = woodBlock("fig_slab",           FigSlabBlock::new);
    public static final DeferredBlock<Block> FIG_FENCE          = woodBlock("fig_fence",          FigFenceBlock::new);
    public static final DeferredBlock<Block> FIG_FENCE_GATE     = woodBlock("fig_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.FIG, p));
    public static final DeferredBlock<Block> FIG_PRESSURE_PLATE = woodBlock("fig_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> FIG_BUTTON         = woodBlock("fig_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> FIG_DOOR           = doorBlock("fig_door",     p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> FIG_TRAPDOOR       = trapdoorBlock("fig_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> FIG_SIGN           = woodBlock("fig_sign",      p -> new GotStandingSignBlock(GotWoodTypes.FIG, p, () -> GotModBlockEntities.FIG_SIGN.get()));
    public static final DeferredBlock<Block> FIG_WALL_SIGN      = woodBlock("fig_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.FIG, p, () -> GotModBlockEntities.FIG_SIGN.get()));
    public static final DeferredBlock<Block> FIG_HANGING_SIGN      = woodBlock("fig_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.FIG, p));
    public static final DeferredBlock<Block> FIG_WALL_HANGING_SIGN = woodBlock("fig_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.FIG, p));
    public static final DeferredBlock<Block> FIG_BRANCH         = woodBlock("fig_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_FIG_BRANCH = woodBlock("stripped_fig_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> FIG_SAPLING        = woodBlock("fig_sapling",        p -> new GotSaplingBlock(GotTreeGrowers.FIG, p));
    public static final DeferredBlock<Block> STRIPPED_FIG_LOG   = logBlock("stripped_fig_log",   GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_FIG_WOOD  = logBlock("stripped_fig_wood",  GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> FIG_ROOFING        = REGISTRY.registerBlock("fig_roofing",        p -> new Block(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> FIG_ROOFING_SLAB   = REGISTRY.registerBlock("fig_roofing_slab",   p -> new SlabBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> FIG_ROOFING_STAIRS = REGISTRY.registerBlock("fig_roofing_stairs", p -> new StairBlock(FIG_ROOFING.get().defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> FIG_ROOFING_WALL   = REGISTRY.registerBlock("fig_roofing_wall",   p -> new WallBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    // LOGS.put("fig", FIG_LOG); STRIPPED_LOGS.put("fig", STRIPPED_FIG_LOG);
    // WOODS.put("fig", FIG_WOOD); STRIPPED_WOODS.put("fig", STRIPPED_FIG_WOOD);

    // ── Lemon ──
    public static final DeferredBlock<Block> LEMON_LOG            = logBlock("lemon_log",            LemonLogBlock::new);
    public static final DeferredBlock<Block> LEMON_WOOD           = logBlock("lemon_wood",           LemonWoodBlock::new);
    public static final DeferredBlock<Block> LEMON_PLANKS         = woodBlock("lemon_planks",         LemonPlanksBlock::new);
    public static final DeferredBlock<Block> LEMON_LEAVES         = woodBlock("lemon_leaves",         LemonLeavesBlock::new);
    public static final DeferredBlock<Block> LEMON_STAIRS         = woodBlock("lemon_stairs",         p -> new LemonStairsBlock(LEMON_PLANKS.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> LEMON_SLAB           = woodBlock("lemon_slab",           LemonSlabBlock::new);
    public static final DeferredBlock<Block> LEMON_FENCE          = woodBlock("lemon_fence",          LemonFenceBlock::new);
    public static final DeferredBlock<Block> LEMON_FENCE_GATE     = woodBlock("lemon_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.LEMON, p));
    public static final DeferredBlock<Block> LEMON_PRESSURE_PLATE = woodBlock("lemon_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> LEMON_BUTTON         = woodBlock("lemon_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> LEMON_DOOR           = doorBlock("lemon_door",     p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> LEMON_TRAPDOOR       = trapdoorBlock("lemon_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> LEMON_SIGN           = woodBlock("lemon_sign",      p -> new GotStandingSignBlock(GotWoodTypes.LEMON, p, () -> GotModBlockEntities.LEMON_SIGN.get()));
    public static final DeferredBlock<Block> LEMON_WALL_SIGN      = woodBlock("lemon_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.LEMON, p, () -> GotModBlockEntities.LEMON_SIGN.get()));
    public static final DeferredBlock<Block> LEMON_HANGING_SIGN      = woodBlock("lemon_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.LEMON, p));
    public static final DeferredBlock<Block> LEMON_WALL_HANGING_SIGN = woodBlock("lemon_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.LEMON, p));
    public static final DeferredBlock<Block> LEMON_BRANCH         = woodBlock("lemon_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_LEMON_BRANCH = woodBlock("stripped_lemon_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> LEMON_SAPLING        = woodBlock("lemon_sapling",        p -> new GotSaplingBlock(GotTreeGrowers.LEMON, p));
    public static final DeferredBlock<Block> STRIPPED_LEMON_LOG   = logBlock("stripped_lemon_log",   GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_LEMON_WOOD  = logBlock("stripped_lemon_wood",  GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> LEMON_ROOFING        = REGISTRY.registerBlock("lemon_roofing",        p -> new Block(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> LEMON_ROOFING_SLAB   = REGISTRY.registerBlock("lemon_roofing_slab",   p -> new SlabBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> LEMON_ROOFING_STAIRS = REGISTRY.registerBlock("lemon_roofing_stairs", p -> new StairBlock(LEMON_ROOFING.get().defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> LEMON_ROOFING_WALL   = REGISTRY.registerBlock("lemon_roofing_wall",   p -> new WallBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    // LOGS.put("lemon", LEMON_LOG); STRIPPED_LOGS.put("lemon", STRIPPED_LEMON_LOG);
    // WOODS.put("lemon", LEMON_WOOD); STRIPPED_WOODS.put("lemon", STRIPPED_LEMON_WOOD);

    // ── Lime ──
    public static final DeferredBlock<Block> LIME_LOG            = logBlock("lime_log",            LimeLogBlock::new);
    public static final DeferredBlock<Block> LIME_WOOD           = logBlock("lime_wood",           LimeWoodBlock::new);
    public static final DeferredBlock<Block> LIME_PLANKS         = woodBlock("lime_planks",         LimePlanksBlock::new);
    public static final DeferredBlock<Block> LIME_LEAVES         = woodBlock("lime_leaves",         LimeLeavesBlock::new);
    public static final DeferredBlock<Block> LIME_STAIRS         = woodBlock("lime_stairs",         p -> new LimeStairsBlock(LIME_PLANKS.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> LIME_SLAB           = woodBlock("lime_slab",           LimeSlabBlock::new);
    public static final DeferredBlock<Block> LIME_FENCE          = woodBlock("lime_fence",          LimeFenceBlock::new);
    public static final DeferredBlock<Block> LIME_FENCE_GATE     = woodBlock("lime_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.LIME, p));
    public static final DeferredBlock<Block> LIME_PRESSURE_PLATE = woodBlock("lime_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> LIME_BUTTON         = woodBlock("lime_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> LIME_DOOR           = doorBlock("lime_door",     p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> LIME_TRAPDOOR       = trapdoorBlock("lime_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> LIME_SIGN           = woodBlock("lime_sign",      p -> new GotStandingSignBlock(GotWoodTypes.LIME, p, () -> GotModBlockEntities.LIME_SIGN.get()));
    public static final DeferredBlock<Block> LIME_WALL_SIGN      = woodBlock("lime_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.LIME, p, () -> GotModBlockEntities.LIME_SIGN.get()));
    public static final DeferredBlock<Block> LIME_HANGING_SIGN      = woodBlock("lime_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.LIME, p));
    public static final DeferredBlock<Block> LIME_WALL_HANGING_SIGN = woodBlock("lime_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.LIME, p));
    public static final DeferredBlock<Block> LIME_BRANCH         = woodBlock("lime_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_LIME_BRANCH = woodBlock("stripped_lime_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> LIME_SAPLING        = woodBlock("lime_sapling",        p -> new GotSaplingBlock(GotTreeGrowers.LIME, p));
    public static final DeferredBlock<Block> STRIPPED_LIME_LOG   = logBlock("stripped_lime_log",   GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_LIME_WOOD  = logBlock("stripped_lime_wood",  GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> LIME_ROOFING        = REGISTRY.registerBlock("lime_roofing",        p -> new Block(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> LIME_ROOFING_SLAB   = REGISTRY.registerBlock("lime_roofing_slab",   p -> new SlabBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> LIME_ROOFING_STAIRS = REGISTRY.registerBlock("lime_roofing_stairs", p -> new StairBlock(LIME_ROOFING.get().defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> LIME_ROOFING_WALL   = REGISTRY.registerBlock("lime_roofing_wall",   p -> new WallBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    // LOGS.put("lime", LIME_LOG); STRIPPED_LOGS.put("lime", STRIPPED_LIME_LOG);
    // WOODS.put("lime", LIME_WOOD); STRIPPED_WOODS.put("lime", STRIPPED_LIME_WOOD);

    // ── Olive ──
    public static final DeferredBlock<Block> OLIVE_LOG            = logBlock("olive_log",            OliveLogBlock::new);
    public static final DeferredBlock<Block> OLIVE_WOOD           = logBlock("olive_wood",           OliveWoodBlock::new);
    public static final DeferredBlock<Block> OLIVE_PLANKS         = woodBlock("olive_planks",         OlivePlanksBlock::new);
    public static final DeferredBlock<Block> OLIVE_LEAVES         = woodBlock("olive_leaves",         OliveLeavesBlock::new);
    public static final DeferredBlock<Block> OLIVE_STAIRS         = woodBlock("olive_stairs",         p -> new OliveStairsBlock(OLIVE_PLANKS.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> OLIVE_SLAB           = woodBlock("olive_slab",           OliveSlabBlock::new);
    public static final DeferredBlock<Block> OLIVE_FENCE          = woodBlock("olive_fence",          OliveFenceBlock::new);
    public static final DeferredBlock<Block> OLIVE_FENCE_GATE     = woodBlock("olive_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.OLIVE, p));
    public static final DeferredBlock<Block> OLIVE_PRESSURE_PLATE = woodBlock("olive_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> OLIVE_BUTTON         = woodBlock("olive_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> OLIVE_DOOR           = doorBlock("olive_door",     p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> OLIVE_TRAPDOOR       = trapdoorBlock("olive_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> OLIVE_SIGN           = woodBlock("olive_sign",      p -> new GotStandingSignBlock(GotWoodTypes.OLIVE, p, () -> GotModBlockEntities.OLIVE_SIGN.get()));
    public static final DeferredBlock<Block> OLIVE_WALL_SIGN      = woodBlock("olive_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.OLIVE, p, () -> GotModBlockEntities.OLIVE_SIGN.get()));
    public static final DeferredBlock<Block> OLIVE_HANGING_SIGN      = woodBlock("olive_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.OLIVE, p));
    public static final DeferredBlock<Block> OLIVE_WALL_HANGING_SIGN = woodBlock("olive_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.OLIVE, p));
    public static final DeferredBlock<Block> OLIVE_BRANCH         = woodBlock("olive_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_OLIVE_BRANCH = woodBlock("stripped_olive_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> OLIVE_SAPLING        = woodBlock("olive_sapling",        p -> new GotSaplingBlock(GotTreeGrowers.OLIVE, p));
    public static final DeferredBlock<Block> STRIPPED_OLIVE_LOG   = logBlock("stripped_olive_log",   GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_OLIVE_WOOD  = logBlock("stripped_olive_wood",  GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> OLIVE_ROOFING        = REGISTRY.registerBlock("olive_roofing",        p -> new Block(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> OLIVE_ROOFING_SLAB   = REGISTRY.registerBlock("olive_roofing_slab",   p -> new SlabBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> OLIVE_ROOFING_STAIRS = REGISTRY.registerBlock("olive_roofing_stairs", p -> new StairBlock(OLIVE_ROOFING.get().defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> OLIVE_ROOFING_WALL   = REGISTRY.registerBlock("olive_roofing_wall",   p -> new WallBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    // LOGS.put("olive", OLIVE_LOG); STRIPPED_LOGS.put("olive", STRIPPED_OLIVE_LOG);
    // WOODS.put("olive", OLIVE_WOOD); STRIPPED_WOODS.put("olive", STRIPPED_OLIVE_WOOD);

    // ── Orange ──
    public static final DeferredBlock<Block> ORANGE_LOG            = logBlock("orange_log",            OrangeLogBlock::new);
    public static final DeferredBlock<Block> ORANGE_WOOD           = logBlock("orange_wood",           OrangeWoodBlock::new);
    public static final DeferredBlock<Block> ORANGE_PLANKS         = woodBlock("orange_planks",         OrangePlanksBlock::new);
    public static final DeferredBlock<Block> ORANGE_LEAVES         = woodBlock("orange_leaves",         OrangeLeavesBlock::new);
    public static final DeferredBlock<Block> ORANGE_STAIRS         = woodBlock("orange_stairs",         p -> new OrangeStairsBlock(ORANGE_PLANKS.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> ORANGE_SLAB           = woodBlock("orange_slab",           OrangeSlabBlock::new);
    public static final DeferredBlock<Block> ORANGE_FENCE          = woodBlock("orange_fence",          OrangeFenceBlock::new);
    public static final DeferredBlock<Block> ORANGE_FENCE_GATE     = woodBlock("orange_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.ORANGE, p));
    public static final DeferredBlock<Block> ORANGE_PRESSURE_PLATE = woodBlock("orange_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> ORANGE_BUTTON         = woodBlock("orange_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> ORANGE_DOOR           = doorBlock("orange_door",     p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> ORANGE_TRAPDOOR       = trapdoorBlock("orange_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> ORANGE_SIGN           = woodBlock("orange_sign",      p -> new GotStandingSignBlock(GotWoodTypes.ORANGE, p, () -> GotModBlockEntities.ORANGE_SIGN.get()));
    public static final DeferredBlock<Block> ORANGE_WALL_SIGN      = woodBlock("orange_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.ORANGE, p, () -> GotModBlockEntities.ORANGE_SIGN.get()));
    public static final DeferredBlock<Block> ORANGE_HANGING_SIGN      = woodBlock("orange_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.ORANGE, p));
    public static final DeferredBlock<Block> ORANGE_WALL_HANGING_SIGN = woodBlock("orange_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.ORANGE, p));
    public static final DeferredBlock<Block> ORANGE_BRANCH         = woodBlock("orange_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_ORANGE_BRANCH = woodBlock("stripped_orange_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> ORANGE_SAPLING        = woodBlock("orange_sapling",        p -> new GotSaplingBlock(GotTreeGrowers.ORANGE, p));
    public static final DeferredBlock<Block> STRIPPED_ORANGE_LOG   = logBlock("stripped_orange_log",   GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_ORANGE_WOOD  = logBlock("stripped_orange_wood",  GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> ORANGE_ROOFING        = REGISTRY.registerBlock("orange_roofing",        p -> new Block(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> ORANGE_ROOFING_SLAB   = REGISTRY.registerBlock("orange_roofing_slab",   p -> new SlabBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> ORANGE_ROOFING_STAIRS = REGISTRY.registerBlock("orange_roofing_stairs", p -> new StairBlock(ORANGE_ROOFING.get().defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> ORANGE_ROOFING_WALL   = REGISTRY.registerBlock("orange_roofing_wall",   p -> new WallBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    // LOGS.put("orange", ORANGE_LOG); STRIPPED_LOGS.put("orange", STRIPPED_ORANGE_LOG);
    // WOODS.put("orange", ORANGE_WOOD); STRIPPED_WOODS.put("orange", STRIPPED_ORANGE_WOOD);

    // ── Peach ──
    public static final DeferredBlock<Block> PEACH_LOG            = logBlock("peach_log",            PeachLogBlock::new);
    public static final DeferredBlock<Block> PEACH_WOOD           = logBlock("peach_wood",           PeachWoodBlock::new);
    public static final DeferredBlock<Block> PEACH_PLANKS         = woodBlock("peach_planks",         PeachPlanksBlock::new);
    public static final DeferredBlock<Block> PEACH_LEAVES         = woodBlock("peach_leaves",         PeachLeavesBlock::new);
    public static final DeferredBlock<Block> PEACH_STAIRS         = woodBlock("peach_stairs",         p -> new PeachStairsBlock(PEACH_PLANKS.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> PEACH_SLAB           = woodBlock("peach_slab",           PeachSlabBlock::new);
    public static final DeferredBlock<Block> PEACH_FENCE          = woodBlock("peach_fence",          PeachFenceBlock::new);
    public static final DeferredBlock<Block> PEACH_FENCE_GATE     = woodBlock("peach_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.PEACH, p));
    public static final DeferredBlock<Block> PEACH_PRESSURE_PLATE = woodBlock("peach_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> PEACH_BUTTON         = woodBlock("peach_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> PEACH_DOOR           = doorBlock("peach_door",     p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> PEACH_TRAPDOOR       = trapdoorBlock("peach_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> PEACH_SIGN           = woodBlock("peach_sign",      p -> new GotStandingSignBlock(GotWoodTypes.PEACH, p, () -> GotModBlockEntities.PEACH_SIGN.get()));
    public static final DeferredBlock<Block> PEACH_WALL_SIGN      = woodBlock("peach_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.PEACH, p, () -> GotModBlockEntities.PEACH_SIGN.get()));
    public static final DeferredBlock<Block> PEACH_HANGING_SIGN      = woodBlock("peach_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.PEACH, p));
    public static final DeferredBlock<Block> PEACH_WALL_HANGING_SIGN = woodBlock("peach_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.PEACH, p));
    public static final DeferredBlock<Block> PEACH_BRANCH         = woodBlock("peach_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_PEACH_BRANCH = woodBlock("stripped_peach_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> PEACH_SAPLING        = woodBlock("peach_sapling",        p -> new GotSaplingBlock(GotTreeGrowers.PEACH, p));
    public static final DeferredBlock<Block> STRIPPED_PEACH_LOG   = logBlock("stripped_peach_log",   GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_PEACH_WOOD  = logBlock("stripped_peach_wood",  GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> PEACH_ROOFING        = REGISTRY.registerBlock("peach_roofing",        p -> new Block(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> PEACH_ROOFING_SLAB   = REGISTRY.registerBlock("peach_roofing_slab",   p -> new SlabBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> PEACH_ROOFING_STAIRS = REGISTRY.registerBlock("peach_roofing_stairs", p -> new StairBlock(PEACH_ROOFING.get().defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> PEACH_ROOFING_WALL   = REGISTRY.registerBlock("peach_roofing_wall",   p -> new WallBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    // LOGS.put("peach", PEACH_LOG); STRIPPED_LOGS.put("peach", STRIPPED_PEACH_LOG);
    // WOODS.put("peach", PEACH_WOOD); STRIPPED_WOODS.put("peach", STRIPPED_PEACH_WOOD);

    // ── Pear ──
    public static final DeferredBlock<Block> PEAR_LOG            = logBlock("pear_log",            PearLogBlock::new);
    public static final DeferredBlock<Block> PEAR_WOOD           = logBlock("pear_wood",           PearWoodBlock::new);
    public static final DeferredBlock<Block> PEAR_PLANKS         = woodBlock("pear_planks",         PearPlanksBlock::new);
    public static final DeferredBlock<Block> PEAR_LEAVES         = woodBlock("pear_leaves",         PearLeavesBlock::new);
    public static final DeferredBlock<Block> PEAR_STAIRS         = woodBlock("pear_stairs",         p -> new PearStairsBlock(PEAR_PLANKS.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> PEAR_SLAB           = woodBlock("pear_slab",           PearSlabBlock::new);
    public static final DeferredBlock<Block> PEAR_FENCE          = woodBlock("pear_fence",          PearFenceBlock::new);
    public static final DeferredBlock<Block> PEAR_FENCE_GATE     = woodBlock("pear_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.PEAR, p));
    public static final DeferredBlock<Block> PEAR_PRESSURE_PLATE = woodBlock("pear_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> PEAR_BUTTON         = woodBlock("pear_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> PEAR_DOOR           = doorBlock("pear_door",     p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> PEAR_TRAPDOOR       = trapdoorBlock("pear_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> PEAR_SIGN           = woodBlock("pear_sign",      p -> new GotStandingSignBlock(GotWoodTypes.PEAR, p, () -> GotModBlockEntities.PEAR_SIGN.get()));
    public static final DeferredBlock<Block> PEAR_WALL_SIGN      = woodBlock("pear_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.PEAR, p, () -> GotModBlockEntities.PEAR_SIGN.get()));
    public static final DeferredBlock<Block> PEAR_HANGING_SIGN      = woodBlock("pear_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.PEAR, p));
    public static final DeferredBlock<Block> PEAR_WALL_HANGING_SIGN = woodBlock("pear_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.PEAR, p));
    public static final DeferredBlock<Block> PEAR_BRANCH         = woodBlock("pear_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_PEAR_BRANCH = woodBlock("stripped_pear_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> PEAR_SAPLING        = woodBlock("pear_sapling",        p -> new GotSaplingBlock(GotTreeGrowers.PEAR, p));
    public static final DeferredBlock<Block> STRIPPED_PEAR_LOG   = logBlock("stripped_pear_log",   GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_PEAR_WOOD  = logBlock("stripped_pear_wood",  GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> PEAR_ROOFING        = REGISTRY.registerBlock("pear_roofing",        p -> new Block(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> PEAR_ROOFING_SLAB   = REGISTRY.registerBlock("pear_roofing_slab",   p -> new SlabBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> PEAR_ROOFING_STAIRS = REGISTRY.registerBlock("pear_roofing_stairs", p -> new StairBlock(PEAR_ROOFING.get().defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> PEAR_ROOFING_WALL   = REGISTRY.registerBlock("pear_roofing_wall",   p -> new WallBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    // LOGS.put("pear", PEAR_LOG); STRIPPED_LOGS.put("pear", STRIPPED_PEAR_LOG);
    // WOODS.put("pear", PEAR_WOOD); STRIPPED_WOODS.put("pear", STRIPPED_PEAR_WOOD);

    // ── Persimmon ──
    public static final DeferredBlock<Block> PERSIMMON_LOG            = logBlock("persimmon_log",            PersimmonLogBlock::new);
    public static final DeferredBlock<Block> PERSIMMON_WOOD           = logBlock("persimmon_wood",           PersimmonWoodBlock::new);
    public static final DeferredBlock<Block> PERSIMMON_PLANKS         = woodBlock("persimmon_planks",         PersimmonPlanksBlock::new);
    public static final DeferredBlock<Block> PERSIMMON_LEAVES         = woodBlock("persimmon_leaves",         PersimmonLeavesBlock::new);
    public static final DeferredBlock<Block> PERSIMMON_STAIRS         = woodBlock("persimmon_stairs",         p -> new PersimmonStairsBlock(PERSIMMON_PLANKS.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> PERSIMMON_SLAB           = woodBlock("persimmon_slab",           PersimmonSlabBlock::new);
    public static final DeferredBlock<Block> PERSIMMON_FENCE          = woodBlock("persimmon_fence",          PersimmonFenceBlock::new);
    public static final DeferredBlock<Block> PERSIMMON_FENCE_GATE     = woodBlock("persimmon_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.PERSIMMON, p));
    public static final DeferredBlock<Block> PERSIMMON_PRESSURE_PLATE = woodBlock("persimmon_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> PERSIMMON_BUTTON         = woodBlock("persimmon_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> PERSIMMON_DOOR           = doorBlock("persimmon_door",     p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> PERSIMMON_TRAPDOOR       = trapdoorBlock("persimmon_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> PERSIMMON_SIGN           = woodBlock("persimmon_sign",      p -> new GotStandingSignBlock(GotWoodTypes.PERSIMMON, p, () -> GotModBlockEntities.PERSIMMON_SIGN.get()));
    public static final DeferredBlock<Block> PERSIMMON_WALL_SIGN      = woodBlock("persimmon_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.PERSIMMON, p, () -> GotModBlockEntities.PERSIMMON_SIGN.get()));
    public static final DeferredBlock<Block> PERSIMMON_HANGING_SIGN      = woodBlock("persimmon_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.PERSIMMON, p));
    public static final DeferredBlock<Block> PERSIMMON_WALL_HANGING_SIGN = woodBlock("persimmon_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.PERSIMMON, p));
    public static final DeferredBlock<Block> PERSIMMON_BRANCH         = woodBlock("persimmon_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_PERSIMMON_BRANCH = woodBlock("stripped_persimmon_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> PERSIMMON_SAPLING        = woodBlock("persimmon_sapling",        p -> new GotSaplingBlock(GotTreeGrowers.PERSIMMON, p));
    public static final DeferredBlock<Block> STRIPPED_PERSIMMON_LOG   = logBlock("stripped_persimmon_log",   GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_PERSIMMON_WOOD  = logBlock("stripped_persimmon_wood",  GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> PERSIMMON_ROOFING        = REGISTRY.registerBlock("persimmon_roofing",        p -> new Block(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> PERSIMMON_ROOFING_SLAB   = REGISTRY.registerBlock("persimmon_roofing_slab",   p -> new SlabBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> PERSIMMON_ROOFING_STAIRS = REGISTRY.registerBlock("persimmon_roofing_stairs", p -> new StairBlock(PERSIMMON_ROOFING.get().defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> PERSIMMON_ROOFING_WALL   = REGISTRY.registerBlock("persimmon_roofing_wall",   p -> new WallBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    // LOGS.put("persimmon", PERSIMMON_LOG); STRIPPED_LOGS.put("persimmon", STRIPPED_PERSIMMON_LOG);
    // WOODS.put("persimmon", PERSIMMON_WOOD); STRIPPED_WOODS.put("persimmon", STRIPPED_PERSIMMON_WOOD);

    // ── PinkIvory ──
    public static final DeferredBlock<Block> PINK_IVORY_LOG            = logBlock("pink_ivory_log",            PinkIvoryLogBlock::new);
    public static final DeferredBlock<Block> PINK_IVORY_WOOD           = logBlock("pink_ivory_wood",           PinkIvoryWoodBlock::new);
    public static final DeferredBlock<Block> PINK_IVORY_PLANKS         = woodBlock("pink_ivory_planks",         PinkIvoryPlanksBlock::new);
    public static final DeferredBlock<Block> PINK_IVORY_LEAVES         = woodBlock("pink_ivory_leaves",         PinkIvoryLeavesBlock::new);
    public static final DeferredBlock<Block> PINK_IVORY_STAIRS         = woodBlock("pink_ivory_stairs",         p -> new PinkIvoryStairsBlock(PINK_IVORY_PLANKS.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> PINK_IVORY_SLAB           = woodBlock("pink_ivory_slab",           PinkIvorySlabBlock::new);
    public static final DeferredBlock<Block> PINK_IVORY_FENCE          = woodBlock("pink_ivory_fence",          PinkIvoryFenceBlock::new);
    public static final DeferredBlock<Block> PINK_IVORY_FENCE_GATE     = woodBlock("pink_ivory_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.PINK_IVORY, p));
    public static final DeferredBlock<Block> PINK_IVORY_PRESSURE_PLATE = woodBlock("pink_ivory_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> PINK_IVORY_BUTTON         = woodBlock("pink_ivory_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> PINK_IVORY_DOOR           = doorBlock("pink_ivory_door",     p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> PINK_IVORY_TRAPDOOR       = trapdoorBlock("pink_ivory_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> PINK_IVORY_SIGN           = woodBlock("pink_ivory_sign",      p -> new GotStandingSignBlock(GotWoodTypes.PINK_IVORY, p, () -> GotModBlockEntities.PINK_IVORY_SIGN.get()));
    public static final DeferredBlock<Block> PINK_IVORY_WALL_SIGN      = woodBlock("pink_ivory_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.PINK_IVORY, p, () -> GotModBlockEntities.PINK_IVORY_SIGN.get()));
    public static final DeferredBlock<Block> PINK_IVORY_HANGING_SIGN      = woodBlock("pink_ivory_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.PINK_IVORY, p));
    public static final DeferredBlock<Block> PINK_IVORY_WALL_HANGING_SIGN = woodBlock("pink_ivory_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.PINK_IVORY, p));
    public static final DeferredBlock<Block> PINK_IVORY_BRANCH         = woodBlock("pink_ivory_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_PINK_IVORY_BRANCH = woodBlock("stripped_pink_ivory_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> PINK_IVORY_SAPLING        = woodBlock("pink_ivory_sapling",        p -> new GotSaplingBlock(GotTreeGrowers.PINK_IVORY, p));
    public static final DeferredBlock<Block> STRIPPED_PINK_IVORY_LOG   = logBlock("stripped_pink_ivory_log",   GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_PINK_IVORY_WOOD  = logBlock("stripped_pink_ivory_wood",  GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> PINK_IVORY_ROOFING        = REGISTRY.registerBlock("pink_ivory_roofing",        p -> new Block(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> PINK_IVORY_ROOFING_SLAB   = REGISTRY.registerBlock("pink_ivory_roofing_slab",   p -> new SlabBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> PINK_IVORY_ROOFING_STAIRS = REGISTRY.registerBlock("pink_ivory_roofing_stairs", p -> new StairBlock(PINK_IVORY_ROOFING.get().defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> PINK_IVORY_ROOFING_WALL   = REGISTRY.registerBlock("pink_ivory_roofing_wall",   p -> new WallBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    // LOGS.put("pink_ivory", PINK_IVORY_LOG); STRIPPED_LOGS.put("pink_ivory", STRIPPED_PINK_IVORY_LOG);
    // WOODS.put("pink_ivory", PINK_IVORY_WOOD); STRIPPED_WOODS.put("pink_ivory", STRIPPED_PINK_IVORY_WOOD);

    // ── Plum ──
    public static final DeferredBlock<Block> PLUM_LOG            = logBlock("plum_log",            PlumLogBlock::new);
    public static final DeferredBlock<Block> PLUM_WOOD           = logBlock("plum_wood",           PlumWoodBlock::new);
    public static final DeferredBlock<Block> PLUM_PLANKS         = woodBlock("plum_planks",         PlumPlanksBlock::new);
    public static final DeferredBlock<Block> PLUM_LEAVES         = woodBlock("plum_leaves",         PlumLeavesBlock::new);
    public static final DeferredBlock<Block> PLUM_STAIRS         = woodBlock("plum_stairs",         p -> new PlumStairsBlock(PLUM_PLANKS.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> PLUM_SLAB           = woodBlock("plum_slab",           PlumSlabBlock::new);
    public static final DeferredBlock<Block> PLUM_FENCE          = woodBlock("plum_fence",          PlumFenceBlock::new);
    public static final DeferredBlock<Block> PLUM_FENCE_GATE     = woodBlock("plum_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.PLUM, p));
    public static final DeferredBlock<Block> PLUM_PRESSURE_PLATE = woodBlock("plum_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> PLUM_BUTTON         = woodBlock("plum_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> PLUM_DOOR           = doorBlock("plum_door",     p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> PLUM_TRAPDOOR       = trapdoorBlock("plum_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> PLUM_SIGN           = woodBlock("plum_sign",      p -> new GotStandingSignBlock(GotWoodTypes.PLUM, p, () -> GotModBlockEntities.PLUM_SIGN.get()));
    public static final DeferredBlock<Block> PLUM_WALL_SIGN      = woodBlock("plum_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.PLUM, p, () -> GotModBlockEntities.PLUM_SIGN.get()));
    public static final DeferredBlock<Block> PLUM_HANGING_SIGN      = woodBlock("plum_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.PLUM, p));
    public static final DeferredBlock<Block> PLUM_WALL_HANGING_SIGN = woodBlock("plum_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.PLUM, p));
    public static final DeferredBlock<Block> PLUM_BRANCH         = woodBlock("plum_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_PLUM_BRANCH = woodBlock("stripped_plum_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> PLUM_SAPLING        = woodBlock("plum_sapling",        p -> new GotSaplingBlock(GotTreeGrowers.PLUM, p));
    public static final DeferredBlock<Block> STRIPPED_PLUM_LOG   = logBlock("stripped_plum_log",   GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_PLUM_WOOD  = logBlock("stripped_plum_wood",  GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> PLUM_ROOFING        = REGISTRY.registerBlock("plum_roofing",        p -> new Block(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> PLUM_ROOFING_SLAB   = REGISTRY.registerBlock("plum_roofing_slab",   p -> new SlabBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> PLUM_ROOFING_STAIRS = REGISTRY.registerBlock("plum_roofing_stairs", p -> new StairBlock(PLUM_ROOFING.get().defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> PLUM_ROOFING_WALL   = REGISTRY.registerBlock("plum_roofing_wall",   p -> new WallBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    // LOGS.put("plum", PLUM_LOG); STRIPPED_LOGS.put("plum", STRIPPED_PLUM_LOG);
    // WOODS.put("plum", PLUM_WOOD); STRIPPED_WOODS.put("plum", STRIPPED_PLUM_WOOD);

    // ── Pomegranate ──
    public static final DeferredBlock<Block> POMEGRANATE_LOG            = logBlock("pomegranate_log",            PomegranateLogBlock::new);
    public static final DeferredBlock<Block> POMEGRANATE_WOOD           = logBlock("pomegranate_wood",           PomegranateWoodBlock::new);
    public static final DeferredBlock<Block> POMEGRANATE_PLANKS         = woodBlock("pomegranate_planks",         PomegranatePlanksBlock::new);
    public static final DeferredBlock<Block> POMEGRANATE_LEAVES         = woodBlock("pomegranate_leaves",         PomegranateLeavesBlock::new);
    public static final DeferredBlock<Block> POMEGRANATE_STAIRS         = woodBlock("pomegranate_stairs",         p -> new PomegranateStairsBlock(POMEGRANATE_PLANKS.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> POMEGRANATE_SLAB           = woodBlock("pomegranate_slab",           PomegranateSlabBlock::new);
    public static final DeferredBlock<Block> POMEGRANATE_FENCE          = woodBlock("pomegranate_fence",          PomegranateFenceBlock::new);
    public static final DeferredBlock<Block> POMEGRANATE_FENCE_GATE     = woodBlock("pomegranate_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.POMEGRANATE, p));
    public static final DeferredBlock<Block> POMEGRANATE_PRESSURE_PLATE = woodBlock("pomegranate_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> POMEGRANATE_BUTTON         = woodBlock("pomegranate_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> POMEGRANATE_DOOR           = doorBlock("pomegranate_door",     p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> POMEGRANATE_TRAPDOOR       = trapdoorBlock("pomegranate_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> POMEGRANATE_SIGN           = woodBlock("pomegranate_sign",      p -> new GotStandingSignBlock(GotWoodTypes.POMEGRANATE, p, () -> GotModBlockEntities.POMEGRANATE_SIGN.get()));
    public static final DeferredBlock<Block> POMEGRANATE_WALL_SIGN      = woodBlock("pomegranate_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.POMEGRANATE, p, () -> GotModBlockEntities.POMEGRANATE_SIGN.get()));
    public static final DeferredBlock<Block> POMEGRANATE_HANGING_SIGN      = woodBlock("pomegranate_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.POMEGRANATE, p));
    public static final DeferredBlock<Block> POMEGRANATE_WALL_HANGING_SIGN = woodBlock("pomegranate_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.POMEGRANATE, p));
    public static final DeferredBlock<Block> POMEGRANATE_BRANCH         = woodBlock("pomegranate_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_POMEGRANATE_BRANCH = woodBlock("stripped_pomegranate_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> POMEGRANATE_SAPLING        = woodBlock("pomegranate_sapling",        p -> new GotSaplingBlock(GotTreeGrowers.POMEGRANATE, p));
    public static final DeferredBlock<Block> STRIPPED_POMEGRANATE_LOG   = logBlock("stripped_pomegranate_log",   GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_POMEGRANATE_WOOD  = logBlock("stripped_pomegranate_wood",  GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> POMEGRANATE_ROOFING        = REGISTRY.registerBlock("pomegranate_roofing",        p -> new Block(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> POMEGRANATE_ROOFING_SLAB   = REGISTRY.registerBlock("pomegranate_roofing_slab",   p -> new SlabBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> POMEGRANATE_ROOFING_STAIRS = REGISTRY.registerBlock("pomegranate_roofing_stairs", p -> new StairBlock(POMEGRANATE_ROOFING.get().defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> POMEGRANATE_ROOFING_WALL   = REGISTRY.registerBlock("pomegranate_roofing_wall",   p -> new WallBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    // LOGS.put("pomegranate", POMEGRANATE_LOG); STRIPPED_LOGS.put("pomegranate", STRIPPED_POMEGRANATE_LOG);
    // WOODS.put("pomegranate", POMEGRANATE_WOOD); STRIPPED_WOODS.put("pomegranate", STRIPPED_POMEGRANATE_WOOD);

    // ── Prune ──
    public static final DeferredBlock<Block> PRUNE_LOG            = logBlock("prune_log",            PruneLogBlock::new);
    public static final DeferredBlock<Block> PRUNE_WOOD           = logBlock("prune_wood",           PruneWoodBlock::new);
    public static final DeferredBlock<Block> PRUNE_PLANKS         = woodBlock("prune_planks",         PrunePlanksBlock::new);
    public static final DeferredBlock<Block> PRUNE_LEAVES         = woodBlock("prune_leaves",         PruneLeavesBlock::new);
    public static final DeferredBlock<Block> PRUNE_STAIRS         = woodBlock("prune_stairs",         p -> new PruneStairsBlock(PRUNE_PLANKS.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> PRUNE_SLAB           = woodBlock("prune_slab",           PruneSlabBlock::new);
    public static final DeferredBlock<Block> PRUNE_FENCE          = woodBlock("prune_fence",          PruneFenceBlock::new);
    public static final DeferredBlock<Block> PRUNE_FENCE_GATE     = woodBlock("prune_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.PRUNE, p));
    public static final DeferredBlock<Block> PRUNE_PRESSURE_PLATE = woodBlock("prune_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> PRUNE_BUTTON         = woodBlock("prune_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> PRUNE_DOOR           = doorBlock("prune_door",     p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> PRUNE_TRAPDOOR       = trapdoorBlock("prune_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> PRUNE_SIGN           = woodBlock("prune_sign",      p -> new GotStandingSignBlock(GotWoodTypes.PRUNE, p, () -> GotModBlockEntities.PRUNE_SIGN.get()));
    public static final DeferredBlock<Block> PRUNE_WALL_SIGN      = woodBlock("prune_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.PRUNE, p, () -> GotModBlockEntities.PRUNE_SIGN.get()));
    public static final DeferredBlock<Block> PRUNE_HANGING_SIGN      = woodBlock("prune_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.PRUNE, p));
    public static final DeferredBlock<Block> PRUNE_WALL_HANGING_SIGN = woodBlock("prune_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.PRUNE, p));
    public static final DeferredBlock<Block> PRUNE_BRANCH         = woodBlock("prune_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_PRUNE_BRANCH = woodBlock("stripped_prune_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> PRUNE_SAPLING        = woodBlock("prune_sapling",        p -> new GotSaplingBlock(GotTreeGrowers.PRUNE, p));
    public static final DeferredBlock<Block> STRIPPED_PRUNE_LOG   = logBlock("stripped_prune_log",   GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_PRUNE_WOOD  = logBlock("stripped_prune_wood",  GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> PRUNE_ROOFING        = REGISTRY.registerBlock("prune_roofing",        p -> new Block(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> PRUNE_ROOFING_SLAB   = REGISTRY.registerBlock("prune_roofing_slab",   p -> new SlabBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> PRUNE_ROOFING_STAIRS = REGISTRY.registerBlock("prune_roofing_stairs", p -> new StairBlock(PRUNE_ROOFING.get().defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> PRUNE_ROOFING_WALL   = REGISTRY.registerBlock("prune_roofing_wall",   p -> new WallBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    // LOGS.put("prune", PRUNE_LOG); STRIPPED_LOGS.put("prune", STRIPPED_PRUNE_LOG);
    // WOODS.put("prune", PRUNE_WOOD); STRIPPED_WOODS.put("prune", STRIPPED_PRUNE_WOOD);

    // ── Almond ──
    public static final DeferredBlock<Block> ALMOND_LOG            = logBlock("almond_log",            AlmondLogBlock::new);
    public static final DeferredBlock<Block> ALMOND_WOOD           = logBlock("almond_wood",           AlmondWoodBlock::new);
    public static final DeferredBlock<Block> ALMOND_PLANKS         = woodBlock("almond_planks",         AlmondPlanksBlock::new);
    public static final DeferredBlock<Block> ALMOND_LEAVES         = woodBlock("almond_leaves",         AlmondLeavesBlock::new);
    public static final DeferredBlock<Block> ALMOND_STAIRS         = woodBlock("almond_stairs",         p -> new AlmondStairsBlock(ALMOND_PLANKS.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> ALMOND_SLAB           = woodBlock("almond_slab",           AlmondSlabBlock::new);
    public static final DeferredBlock<Block> ALMOND_FENCE          = woodBlock("almond_fence",          AlmondFenceBlock::new);
    public static final DeferredBlock<Block> ALMOND_FENCE_GATE     = woodBlock("almond_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.ALMOND, p));
    public static final DeferredBlock<Block> ALMOND_PRESSURE_PLATE = woodBlock("almond_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> ALMOND_BUTTON         = woodBlock("almond_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> ALMOND_DOOR           = doorBlock("almond_door",     p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> ALMOND_TRAPDOOR       = trapdoorBlock("almond_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> ALMOND_SIGN           = woodBlock("almond_sign",      p -> new GotStandingSignBlock(GotWoodTypes.ALMOND, p, () -> GotModBlockEntities.ALMOND_SIGN.get()));
    public static final DeferredBlock<Block> ALMOND_WALL_SIGN      = woodBlock("almond_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.ALMOND, p, () -> GotModBlockEntities.ALMOND_SIGN.get()));
    public static final DeferredBlock<Block> ALMOND_HANGING_SIGN      = woodBlock("almond_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.ALMOND, p));
    public static final DeferredBlock<Block> ALMOND_WALL_HANGING_SIGN = woodBlock("almond_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.ALMOND, p));
    public static final DeferredBlock<Block> ALMOND_BRANCH         = woodBlock("almond_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_ALMOND_BRANCH = woodBlock("stripped_almond_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> ALMOND_SAPLING        = woodBlock("almond_sapling",        p -> new GotSaplingBlock(GotTreeGrowers.ALMOND, p));
    public static final DeferredBlock<Block> STRIPPED_ALMOND_LOG   = logBlock("stripped_almond_log",   GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_ALMOND_WOOD  = logBlock("stripped_almond_wood",  GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> ALMOND_ROOFING        = REGISTRY.registerBlock("almond_roofing",        p -> new Block(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> ALMOND_ROOFING_SLAB   = REGISTRY.registerBlock("almond_roofing_slab",   p -> new SlabBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> ALMOND_ROOFING_STAIRS = REGISTRY.registerBlock("almond_roofing_stairs", p -> new StairBlock(ALMOND_ROOFING.get().defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> ALMOND_ROOFING_WALL   = REGISTRY.registerBlock("almond_roofing_wall",   p -> new WallBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    // LOGS.put("almond", ALMOND_LOG); STRIPPED_LOGS.put("almond", STRIPPED_ALMOND_LOG);
    // WOODS.put("almond", ALMOND_WOOD); STRIPPED_WOODS.put("almond", STRIPPED_ALMOND_WOOD);

    // ── Nutmeg ──
    public static final DeferredBlock<Block> NUTMEG_LOG            = logBlock("nutmeg_log",            NutmegLogBlock::new);
    public static final DeferredBlock<Block> NUTMEG_WOOD           = logBlock("nutmeg_wood",           NutmegWoodBlock::new);
    public static final DeferredBlock<Block> NUTMEG_PLANKS         = woodBlock("nutmeg_planks",         NutmegPlanksBlock::new);
    public static final DeferredBlock<Block> NUTMEG_LEAVES         = woodBlock("nutmeg_leaves",         NutmegLeavesBlock::new);
    public static final DeferredBlock<Block> NUTMEG_STAIRS         = woodBlock("nutmeg_stairs",         p -> new NutmegStairsBlock(NUTMEG_PLANKS.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> NUTMEG_SLAB           = woodBlock("nutmeg_slab",           NutmegSlabBlock::new);
    public static final DeferredBlock<Block> NUTMEG_FENCE          = woodBlock("nutmeg_fence",          NutmegFenceBlock::new);
    public static final DeferredBlock<Block> NUTMEG_FENCE_GATE     = woodBlock("nutmeg_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.NUTMEG, p));
    public static final DeferredBlock<Block> NUTMEG_PRESSURE_PLATE = woodBlock("nutmeg_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> NUTMEG_BUTTON         = woodBlock("nutmeg_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> NUTMEG_DOOR           = doorBlock("nutmeg_door",     p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> NUTMEG_TRAPDOOR       = trapdoorBlock("nutmeg_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> NUTMEG_SIGN           = woodBlock("nutmeg_sign",      p -> new GotStandingSignBlock(GotWoodTypes.NUTMEG, p, () -> GotModBlockEntities.NUTMEG_SIGN.get()));
    public static final DeferredBlock<Block> NUTMEG_WALL_SIGN      = woodBlock("nutmeg_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.NUTMEG, p, () -> GotModBlockEntities.NUTMEG_SIGN.get()));
    public static final DeferredBlock<Block> NUTMEG_HANGING_SIGN      = woodBlock("nutmeg_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.NUTMEG, p));
    public static final DeferredBlock<Block> NUTMEG_WALL_HANGING_SIGN = woodBlock("nutmeg_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.NUTMEG, p));
    public static final DeferredBlock<Block> NUTMEG_BRANCH         = woodBlock("nutmeg_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_NUTMEG_BRANCH = woodBlock("stripped_nutmeg_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> NUTMEG_SAPLING        = woodBlock("nutmeg_sapling",        p -> new GotSaplingBlock(GotTreeGrowers.NUTMEG, p));
    public static final DeferredBlock<Block> STRIPPED_NUTMEG_LOG   = logBlock("stripped_nutmeg_log",   GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_NUTMEG_WOOD  = logBlock("stripped_nutmeg_wood",  GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> NUTMEG_ROOFING        = REGISTRY.registerBlock("nutmeg_roofing",        p -> new Block(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> NUTMEG_ROOFING_SLAB   = REGISTRY.registerBlock("nutmeg_roofing_slab",   p -> new SlabBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> NUTMEG_ROOFING_STAIRS = REGISTRY.registerBlock("nutmeg_roofing_stairs", p -> new StairBlock(NUTMEG_ROOFING.get().defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> NUTMEG_ROOFING_WALL   = REGISTRY.registerBlock("nutmeg_roofing_wall",   p -> new WallBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    // LOGS.put("nutmeg", NUTMEG_LOG); STRIPPED_LOGS.put("nutmeg", STRIPPED_NUTMEG_LOG);
    // WOODS.put("nutmeg", NUTMEG_WOOD); STRIPPED_WOODS.put("nutmeg", STRIPPED_NUTMEG_WOOD);

    // ── Hemlock ──
    public static final DeferredBlock<Block> HEMLOCK_LOG            = logBlock("hemlock_log",            HemlockLogBlock::new);
    public static final DeferredBlock<Block> HEMLOCK_WOOD           = logBlock("hemlock_wood",           HemlockWoodBlock::new);
    public static final DeferredBlock<Block> HEMLOCK_PLANKS         = woodBlock("hemlock_planks",         HemlockPlanksBlock::new);
    public static final DeferredBlock<Block> HEMLOCK_LEAVES         = woodBlock("hemlock_leaves",         HemlockLeavesBlock::new);
    public static final DeferredBlock<Block> HEMLOCK_STAIRS         = woodBlock("hemlock_stairs",         p -> new HemlockStairsBlock(HEMLOCK_PLANKS.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> HEMLOCK_SLAB           = woodBlock("hemlock_slab",           HemlockSlabBlock::new);
    public static final DeferredBlock<Block> HEMLOCK_FENCE          = woodBlock("hemlock_fence",          HemlockFenceBlock::new);
    public static final DeferredBlock<Block> HEMLOCK_FENCE_GATE     = woodBlock("hemlock_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.HEMLOCK, p));
    public static final DeferredBlock<Block> HEMLOCK_PRESSURE_PLATE = woodBlock("hemlock_pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> HEMLOCK_BUTTON         = woodBlock("hemlock_button",         p -> new ButtonBlock(BlockSetType.OAK, 10, p));
    public static final DeferredBlock<Block> HEMLOCK_DOOR           = doorBlock("hemlock_door",     p -> new DoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> HEMLOCK_TRAPDOOR       = trapdoorBlock("hemlock_trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p));
    public static final DeferredBlock<Block> HEMLOCK_SIGN           = woodBlock("hemlock_sign",      p -> new GotStandingSignBlock(GotWoodTypes.HEMLOCK, p, () -> GotModBlockEntities.HEMLOCK_SIGN.get()));
    public static final DeferredBlock<Block> HEMLOCK_WALL_SIGN      = woodBlock("hemlock_wall_sign", p -> new GotWallSignBlock(GotWoodTypes.HEMLOCK, p, () -> GotModBlockEntities.HEMLOCK_SIGN.get()));
    public static final DeferredBlock<Block> HEMLOCK_HANGING_SIGN      = woodBlock("hemlock_hanging_sign",      p -> new CeilingHangingSignBlock(GotWoodTypes.HEMLOCK, p));
    public static final DeferredBlock<Block> HEMLOCK_WALL_HANGING_SIGN = woodBlock("hemlock_wall_hanging_sign", p -> new WallHangingSignBlock(GotWoodTypes.HEMLOCK, p));
    public static final DeferredBlock<Block> HEMLOCK_BRANCH         = woodBlock("hemlock_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> STRIPPED_HEMLOCK_BRANCH = woodBlock("stripped_hemlock_branch", WoodStrippedBranchBlock::new);
    public static final DeferredBlock<Block> HEMLOCK_SAPLING        = woodBlock("hemlock_sapling",        p -> new GotSaplingBlock(GotTreeGrowers.HEMLOCK, p));
    public static final DeferredBlock<Block> STRIPPED_HEMLOCK_LOG   = logBlock("stripped_hemlock_log",   GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_HEMLOCK_WOOD  = logBlock("stripped_hemlock_wood",  GotStrippedLogBlock::new);

    // ── Wood stairs (moved here to avoid forward reference to WOOD blocks) ──
    public static final DeferredBlock<Block> APPLE_WOOD_STAIRS          = woodBlock("apple_wood_stairs",          p -> new StairBlock(APPLE_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> GOLDENHEART_WOOD_STAIRS          = woodBlock("goldenheart_wood_stairs",          p -> new StairBlock(GOLDENHEART_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> LINDEN_WOOD_STAIRS          = woodBlock("linden_wood_stairs",          p -> new StairBlock(LINDEN_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> MAHOGANY_WOOD_STAIRS          = woodBlock("mahogany_wood_stairs",          p -> new StairBlock(MAHOGANY_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> MAPLE_WOOD_STAIRS          = woodBlock("maple_wood_stairs",          p -> new StairBlock(MAPLE_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> MYRRH_WOOD_STAIRS          = woodBlock("myrrh_wood_stairs",          p -> new StairBlock(MYRRH_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> REDWOOD_WOOD_STAIRS          = woodBlock("redwood_wood_stairs",          p -> new StairBlock(REDWOOD_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> CHESTNUT_WOOD_STAIRS          = woodBlock("chestnut_wood_stairs",          p -> new StairBlock(CHESTNUT_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> WILLOW_WOOD_STAIRS          = woodBlock("willow_wood_stairs",          p -> new StairBlock(WILLOW_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> WORMTREE_WOOD_STAIRS          = woodBlock("wormtree_wood_stairs",          p -> new StairBlock(WORMTREE_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> NIGHTWOOD_WOOD_STAIRS          = woodBlock("nightwood_wood_stairs",          p -> new StairBlock(NIGHTWOOD_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_NIGHTWOOD_WOOD_STAIRS = woodBlock("stripped_nightwood_wood_stairs", p -> new StairBlock(STRIPPED_NIGHTWOOD_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> PURPLEHEART_WOOD_STAIRS          = woodBlock("purpleheart_wood_stairs",          p -> new StairBlock(PURPLEHEART_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_PURPLEHEART_WOOD_STAIRS = woodBlock("stripped_purpleheart_wood_stairs", p -> new StairBlock(STRIPPED_PURPLEHEART_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> TIGERWOOD_WOOD_STAIRS          = woodBlock("tigerwood_wood_stairs",          p -> new StairBlock(TIGERWOOD_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_TIGERWOOD_WOOD_STAIRS = woodBlock("stripped_tigerwood_wood_stairs", p -> new StairBlock(STRIPPED_TIGERWOOD_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> BURL_WOOD_STAIRS          = woodBlock("burl_wood_stairs",          p -> new StairBlock(BURL_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_BURL_WOOD_STAIRS = woodBlock("stripped_burl_wood_stairs", p -> new StairBlock(STRIPPED_BURL_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> SANDALWOOD_WOOD_STAIRS          = woodBlock("sandalwood_wood_stairs",          p -> new StairBlock(SANDALWOOD_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_SANDALWOOD_WOOD_STAIRS = woodBlock("stripped_sandalwood_wood_stairs", p -> new StairBlock(STRIPPED_SANDALWOOD_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> SANDBEGGAR_WOOD_STAIRS          = woodBlock("sandbeggar_wood_stairs",          p -> new StairBlock(SANDBEGGAR_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_SANDBEGGAR_WOOD_STAIRS = woodBlock("stripped_sandbeggar_wood_stairs", p -> new StairBlock(STRIPPED_SANDBEGGAR_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> APRICOT_WOOD_STAIRS          = woodBlock("apricot_wood_stairs",          p -> new StairBlock(APRICOT_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_APRICOT_WOOD_STAIRS = woodBlock("stripped_apricot_wood_stairs", p -> new StairBlock(STRIPPED_APRICOT_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> BLACKTHORN_WOOD_STAIRS          = woodBlock("blackthorn_wood_stairs",          p -> new StairBlock(BLACKTHORN_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_BLACKTHORN_WOOD_STAIRS = woodBlock("stripped_blackthorn_wood_stairs", p -> new StairBlock(STRIPPED_BLACKTHORN_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> RED_CHERRY_WOOD_STAIRS          = woodBlock("red_cherry_wood_stairs",          p -> new StairBlock(RED_CHERRY_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_RED_CHERRY_WOOD_STAIRS = woodBlock("stripped_red_cherry_wood_stairs", p -> new StairBlock(STRIPPED_RED_CHERRY_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> BLACK_CHERRY_WOOD_STAIRS          = woodBlock("black_cherry_wood_stairs",          p -> new StairBlock(BLACK_CHERRY_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_BLACK_CHERRY_WOOD_STAIRS = woodBlock("stripped_black_cherry_wood_stairs", p -> new StairBlock(STRIPPED_BLACK_CHERRY_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> WHITE_CHERRY_WOOD_STAIRS          = woodBlock("white_cherry_wood_stairs",          p -> new StairBlock(WHITE_CHERRY_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_WHITE_CHERRY_WOOD_STAIRS = woodBlock("stripped_white_cherry_wood_stairs", p -> new StairBlock(STRIPPED_WHITE_CHERRY_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> CRABAPPLE_WOOD_STAIRS          = woodBlock("crabapple_wood_stairs",          p -> new StairBlock(CRABAPPLE_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_CRABAPPLE_WOOD_STAIRS = woodBlock("stripped_crabapple_wood_stairs", p -> new StairBlock(STRIPPED_CRABAPPLE_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> DATE_PALM_WOOD_STAIRS          = woodBlock("date_palm_wood_stairs",          p -> new StairBlock(DATE_PALM_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_DATE_PALM_WOOD_STAIRS = woodBlock("stripped_date_palm_wood_stairs", p -> new StairBlock(STRIPPED_DATE_PALM_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> FIG_WOOD_STAIRS          = woodBlock("fig_wood_stairs",          p -> new StairBlock(FIG_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_FIG_WOOD_STAIRS = woodBlock("stripped_fig_wood_stairs", p -> new StairBlock(STRIPPED_FIG_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> LEMON_WOOD_STAIRS          = woodBlock("lemon_wood_stairs",          p -> new StairBlock(LEMON_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_LEMON_WOOD_STAIRS = woodBlock("stripped_lemon_wood_stairs", p -> new StairBlock(STRIPPED_LEMON_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> LIME_WOOD_STAIRS          = woodBlock("lime_wood_stairs",          p -> new StairBlock(LIME_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_LIME_WOOD_STAIRS = woodBlock("stripped_lime_wood_stairs", p -> new StairBlock(STRIPPED_LIME_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> OLIVE_WOOD_STAIRS          = woodBlock("olive_wood_stairs",          p -> new StairBlock(OLIVE_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_OLIVE_WOOD_STAIRS = woodBlock("stripped_olive_wood_stairs", p -> new StairBlock(STRIPPED_OLIVE_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> ORANGE_WOOD_STAIRS          = woodBlock("orange_wood_stairs",          p -> new StairBlock(ORANGE_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_ORANGE_WOOD_STAIRS = woodBlock("stripped_orange_wood_stairs", p -> new StairBlock(STRIPPED_ORANGE_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> ALMOND_WOOD_STAIRS          = woodBlock("almond_wood_stairs",          p -> new StairBlock(ALMOND_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_ALMOND_WOOD_STAIRS = woodBlock("stripped_almond_wood_stairs", p -> new StairBlock(STRIPPED_ALMOND_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> HEMLOCK_WOOD_STAIRS          = woodBlock("hemlock_wood_stairs",          p -> new StairBlock(HEMLOCK_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_HEMLOCK_WOOD_STAIRS = woodBlock("stripped_hemlock_wood_stairs", p -> new StairBlock(STRIPPED_HEMLOCK_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> NUTMEG_WOOD_STAIRS          = woodBlock("nutmeg_wood_stairs",          p -> new StairBlock(NUTMEG_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_NUTMEG_WOOD_STAIRS = woodBlock("stripped_nutmeg_wood_stairs", p -> new StairBlock(STRIPPED_NUTMEG_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> PEACH_WOOD_STAIRS          = woodBlock("peach_wood_stairs",          p -> new StairBlock(PEACH_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_PEACH_WOOD_STAIRS = woodBlock("stripped_peach_wood_stairs", p -> new StairBlock(STRIPPED_PEACH_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> PEAR_WOOD_STAIRS          = woodBlock("pear_wood_stairs",          p -> new StairBlock(PEAR_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_PEAR_WOOD_STAIRS = woodBlock("stripped_pear_wood_stairs", p -> new StairBlock(STRIPPED_PEAR_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> PERSIMMON_WOOD_STAIRS          = woodBlock("persimmon_wood_stairs",          p -> new StairBlock(PERSIMMON_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_PERSIMMON_WOOD_STAIRS = woodBlock("stripped_persimmon_wood_stairs", p -> new StairBlock(STRIPPED_PERSIMMON_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> PINK_IVORY_WOOD_STAIRS          = woodBlock("pink_ivory_wood_stairs",          p -> new StairBlock(PINK_IVORY_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_PINK_IVORY_WOOD_STAIRS = woodBlock("stripped_pink_ivory_wood_stairs", p -> new StairBlock(STRIPPED_PINK_IVORY_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> PLUM_WOOD_STAIRS          = woodBlock("plum_wood_stairs",          p -> new StairBlock(PLUM_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_PLUM_WOOD_STAIRS = woodBlock("stripped_plum_wood_stairs", p -> new StairBlock(STRIPPED_PLUM_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> POMEGRANATE_WOOD_STAIRS          = woodBlock("pomegranate_wood_stairs",          p -> new StairBlock(POMEGRANATE_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_POMEGRANATE_WOOD_STAIRS = woodBlock("stripped_pomegranate_wood_stairs", p -> new StairBlock(STRIPPED_POMEGRANATE_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> PRUNE_WOOD_STAIRS          = woodBlock("prune_wood_stairs",          p -> new StairBlock(PRUNE_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> STRIPPED_PRUNE_WOOD_STAIRS = woodBlock("stripped_prune_wood_stairs", p -> new StairBlock(STRIPPED_PRUNE_WOOD.get().defaultBlockState(), p));
    public static final DeferredBlock<Block> HEMLOCK_ROOFING        = REGISTRY.registerBlock("hemlock_roofing",        p -> new Block(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> HEMLOCK_ROOFING_SLAB   = REGISTRY.registerBlock("hemlock_roofing_slab",   p -> new SlabBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> HEMLOCK_ROOFING_STAIRS = REGISTRY.registerBlock("hemlock_roofing_stairs", p -> new StairBlock(HEMLOCK_ROOFING.get().defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> HEMLOCK_ROOFING_WALL   = REGISTRY.registerBlock("hemlock_roofing_wall",   p -> new WallBlock(p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    // LOGS.put("hemlock", HEMLOCK_LOG); STRIPPED_LOGS.put("hemlock", STRIPPED_HEMLOCK_LOG);
    // WOODS.put("hemlock", HEMLOCK_WOOD); STRIPPED_WOODS.put("hemlock", STRIPPED_HEMLOCK_WOOD);






    // Sapling potted versions
    public static final DeferredBlock<Block> POTTED_WEIRWOOD_SAPLING        = pottedBlock("potted_weirwood_sapling",        () -> WEIRWOOD_SAPLING);
    public static final DeferredBlock<Block> POTTED_ASPEN_SAPLING           = pottedBlock("potted_aspen_sapling",           () -> ASPEN_SAPLING);
    public static final DeferredBlock<Block> POTTED_ALDER_SAPLING           = pottedBlock("potted_alder_sapling",           () -> ALDER_SAPLING);
    public static final DeferredBlock<Block> POTTED_PINE_SAPLING            = pottedBlock("potted_pine_sapling",            () -> PINE_SAPLING);
    public static final DeferredBlock<Block> POTTED_FIR_SAPLING             = pottedBlock("potted_fir_sapling",             () -> FIR_SAPLING);
    public static final DeferredBlock<Block> POTTED_SENTINAL_SAPLING        = pottedBlock("potted_sentinal_sapling",        () -> SENTINAL_SAPLING);
    public static final DeferredBlock<Block> POTTED_IRONWOOD_SAPLING        = pottedBlock("potted_ironwood_sapling",        () -> IRONWOOD_SAPLING);
    public static final DeferredBlock<Block> POTTED_BEECH_SAPLING           = pottedBlock("potted_beech_sapling",           () -> BEECH_SAPLING);
    public static final DeferredBlock<Block> POTTED_SOLDIER_PINE_SAPLING    = pottedBlock("potted_soldier_pine_sapling",    () -> SOLDIER_PINE_SAPLING);
    public static final DeferredBlock<Block> POTTED_ASH_SAPLING             = pottedBlock("potted_ash_sapling",             () -> ASH_SAPLING);
    public static final DeferredBlock<Block> POTTED_HAWTHORN_SAPLING        = pottedBlock("potted_hawthorn_sapling",        () -> HAWTHORN_SAPLING);
    public static final DeferredBlock<Block> POTTED_BLACKBARK_SAPLING       = pottedBlock("potted_blackbark_sapling",       () -> BLACKBARK_SAPLING);
    public static final DeferredBlock<Block> POTTED_BLOODWOOD_SAPLING       = pottedBlock("potted_bloodwood_sapling",       () -> BLOODWOOD_SAPLING);
    public static final DeferredBlock<Block> POTTED_BLUE_MAHOE_SAPLING      = pottedBlock("potted_blue_mahoe_sapling",      () -> BLUE_MAHOE_SAPLING);
    public static final DeferredBlock<Block> POTTED_COTTONWOOD_SAPLING      = pottedBlock("potted_cottonwood_sapling",      () -> COTTONWOOD_SAPLING);
    public static final DeferredBlock<Block> POTTED_BLACK_COTTONWOOD_SAPLING = pottedBlock("potted_black_cottonwood_sapling", () -> BLACK_COTTONWOOD_SAPLING);
    public static final DeferredBlock<Block> POTTED_CINNAMON_SAPLING        = pottedBlock("potted_cinnamon_sapling",        () -> CINNAMON_SAPLING);
    public static final DeferredBlock<Block> POTTED_CLOVE_SAPLING           = pottedBlock("potted_clove_sapling",           () -> CLOVE_SAPLING);
    public static final DeferredBlock<Block> POTTED_EBONY_SAPLING           = pottedBlock("potted_ebony_sapling",           () -> EBONY_SAPLING);
    public static final DeferredBlock<Block> POTTED_ELM_SAPLING             = pottedBlock("potted_elm_sapling",             () -> ELM_SAPLING);
    public static final DeferredBlock<Block> POTTED_CEDAR_SAPLING           = pottedBlock("potted_cedar_sapling",           () -> CEDAR_SAPLING);
    public static final DeferredBlock<Block> POTTED_APPLE_SAPLING           = pottedBlock("potted_apple_sapling",           () -> APPLE_SAPLING);
    public static final DeferredBlock<Block> POTTED_GOLDENHEART_SAPLING     = pottedBlock("potted_goldenheart_sapling",     () -> GOLDENHEART_SAPLING);
    public static final DeferredBlock<Block> POTTED_LINDEN_SAPLING          = pottedBlock("potted_linden_sapling",          () -> LINDEN_SAPLING);
    public static final DeferredBlock<Block> POTTED_MAHOGANY_SAPLING        = pottedBlock("potted_mahogany_sapling",        () -> MAHOGANY_SAPLING);
    public static final DeferredBlock<Block> POTTED_MAPLE_SAPLING           = pottedBlock("potted_maple_sapling",           () -> MAPLE_SAPLING);
    public static final DeferredBlock<Block> POTTED_MYRRH_SAPLING           = pottedBlock("potted_myrrh_sapling",           () -> MYRRH_SAPLING);
    public static final DeferredBlock<Block> POTTED_REDWOOD_SAPLING         = pottedBlock("potted_redwood_sapling",         () -> REDWOOD_SAPLING);
    public static final DeferredBlock<Block> POTTED_CHESTNUT_SAPLING        = pottedBlock("potted_chestnut_sapling",        () -> CHESTNUT_SAPLING);
    public static final DeferredBlock<Block> POTTED_WILLOW_SAPLING          = pottedBlock("potted_willow_sapling",          () -> WILLOW_SAPLING);
    public static final DeferredBlock<Block> POTTED_WORMTREE_SAPLING        = pottedBlock("potted_wormtree_sapling",        () -> WORMTREE_SAPLING);
    public static final DeferredBlock<Block> POTTED_NIGHTWOOD_SAPLING       = pottedBlock("potted_nightwood_sapling",       () -> NIGHTWOOD_SAPLING);
    public static final DeferredBlock<Block> POTTED_PURPLEHEART_SAPLING     = pottedBlock("potted_purpleheart_sapling",     () -> PURPLEHEART_SAPLING);
    public static final DeferredBlock<Block> POTTED_TIGERWOOD_SAPLING       = pottedBlock("potted_tigerwood_sapling",       () -> TIGERWOOD_SAPLING);
    public static final DeferredBlock<Block> POTTED_BURL_SAPLING            = pottedBlock("potted_burl_sapling",            () -> BURL_SAPLING);
    public static final DeferredBlock<Block> POTTED_SANDALWOOD_SAPLING      = pottedBlock("potted_sandalwood_sapling",      () -> SANDALWOOD_SAPLING);
    public static final DeferredBlock<Block> POTTED_SANDBEGGAR_SAPLING      = pottedBlock("potted_sandbeggar_sapling",      () -> SANDBEGGAR_SAPLING);
    public static final DeferredBlock<Block> POTTED_APRICOT_SAPLING         = pottedBlock("potted_apricot_sapling",         () -> APRICOT_SAPLING);
    public static final DeferredBlock<Block> POTTED_BLACKTHORN_SAPLING      = pottedBlock("potted_blackthorn_sapling",      () -> BLACKTHORN_SAPLING);
    public static final DeferredBlock<Block> POTTED_RED_CHERRY_SAPLING      = pottedBlock("potted_red_cherry_sapling",      () -> RED_CHERRY_SAPLING);
    public static final DeferredBlock<Block> POTTED_BLACK_CHERRY_SAPLING    = pottedBlock("potted_black_cherry_sapling",    () -> BLACK_CHERRY_SAPLING);
    public static final DeferredBlock<Block> POTTED_WHITE_CHERRY_SAPLING    = pottedBlock("potted_white_cherry_sapling",    () -> WHITE_CHERRY_SAPLING);
    public static final DeferredBlock<Block> POTTED_CRABAPPLE_SAPLING       = pottedBlock("potted_crabapple_sapling",       () -> CRABAPPLE_SAPLING);
    public static final DeferredBlock<Block> POTTED_DATE_PALM_SAPLING       = pottedBlock("potted_date_palm_sapling",       () -> DATE_PALM_SAPLING);
    public static final DeferredBlock<Block> POTTED_FIG_SAPLING             = pottedBlock("potted_fig_sapling",             () -> FIG_SAPLING);
    public static final DeferredBlock<Block> POTTED_LEMON_SAPLING           = pottedBlock("potted_lemon_sapling",           () -> LEMON_SAPLING);
    public static final DeferredBlock<Block> POTTED_LIME_SAPLING            = pottedBlock("potted_lime_sapling",            () -> LIME_SAPLING);
    public static final DeferredBlock<Block> POTTED_OLIVE_SAPLING           = pottedBlock("potted_olive_sapling",           () -> OLIVE_SAPLING);
    public static final DeferredBlock<Block> POTTED_ORANGE_SAPLING          = pottedBlock("potted_orange_sapling",          () -> ORANGE_SAPLING);
    public static final DeferredBlock<Block> POTTED_PEACH_SAPLING           = pottedBlock("potted_peach_sapling",           () -> PEACH_SAPLING);
    public static final DeferredBlock<Block> POTTED_PEAR_SAPLING            = pottedBlock("potted_pear_sapling",            () -> PEAR_SAPLING);
    public static final DeferredBlock<Block> POTTED_PERSIMMON_SAPLING       = pottedBlock("potted_persimmon_sapling",       () -> PERSIMMON_SAPLING);
    public static final DeferredBlock<Block> POTTED_PINK_IVORY_SAPLING      = pottedBlock("potted_pink_ivory_sapling",      () -> PINK_IVORY_SAPLING);
    public static final DeferredBlock<Block> POTTED_PLUM_SAPLING            = pottedBlock("potted_plum_sapling",            () -> PLUM_SAPLING);
    public static final DeferredBlock<Block> POTTED_POMEGRANATE_SAPLING     = pottedBlock("potted_pomegranate_sapling",     () -> POMEGRANATE_SAPLING);
    public static final DeferredBlock<Block> POTTED_PRUNE_SAPLING           = pottedBlock("potted_prune_sapling",           () -> PRUNE_SAPLING);
    public static final DeferredBlock<Block> POTTED_ALMOND_SAPLING          = pottedBlock("potted_almond_sapling",          () -> ALMOND_SAPLING);
    public static final DeferredBlock<Block> POTTED_NUTMEG_SAPLING          = pottedBlock("potted_nutmeg_sapling",          () -> NUTMEG_SAPLING);
    public static final DeferredBlock<Block> POTTED_HEMLOCK_SAPLING         = pottedBlock("potted_hemlock_sapling",         () -> HEMLOCK_SAPLING);

    // ── Wool Slabs & Stairs — vanilla wool colours ─────────────────────────
    public static final DeferredBlock<Block> WHITE_WOOL_SLAB        = woolBlock("white_wool_slab",        Blocks.WHITE_WOOL,      p -> new SlabBlock(p));
    public static final DeferredBlock<Block> WHITE_WOOL_STAIRS      = woolBlock("white_wool_stairs",      Blocks.WHITE_WOOL,      p -> new StairBlock(Blocks.WHITE_WOOL.defaultBlockState(), p));
    public static final DeferredBlock<Block> ORANGE_WOOL_SLAB       = woolBlock("orange_wool_slab",       Blocks.ORANGE_WOOL,     p -> new SlabBlock(p));
    public static final DeferredBlock<Block> ORANGE_WOOL_STAIRS     = woolBlock("orange_wool_stairs",     Blocks.ORANGE_WOOL,     p -> new StairBlock(Blocks.ORANGE_WOOL.defaultBlockState(), p));
    public static final DeferredBlock<Block> MAGENTA_WOOL_SLAB      = woolBlock("magenta_wool_slab",      Blocks.MAGENTA_WOOL,    p -> new SlabBlock(p));
    public static final DeferredBlock<Block> MAGENTA_WOOL_STAIRS    = woolBlock("magenta_wool_stairs",    Blocks.MAGENTA_WOOL,    p -> new StairBlock(Blocks.MAGENTA_WOOL.defaultBlockState(), p));
    public static final DeferredBlock<Block> LIGHT_BLUE_WOOL_SLAB   = woolBlock("light_blue_wool_slab",   Blocks.LIGHT_BLUE_WOOL, p -> new SlabBlock(p));
    public static final DeferredBlock<Block> LIGHT_BLUE_WOOL_STAIRS = woolBlock("light_blue_wool_stairs", Blocks.LIGHT_BLUE_WOOL, p -> new StairBlock(Blocks.LIGHT_BLUE_WOOL.defaultBlockState(), p));
    public static final DeferredBlock<Block> YELLOW_WOOL_SLAB       = woolBlock("yellow_wool_slab",       Blocks.YELLOW_WOOL,     p -> new SlabBlock(p));
    public static final DeferredBlock<Block> YELLOW_WOOL_STAIRS     = woolBlock("yellow_wool_stairs",     Blocks.YELLOW_WOOL,     p -> new StairBlock(Blocks.YELLOW_WOOL.defaultBlockState(), p));
    public static final DeferredBlock<Block> LIME_WOOL_SLAB         = woolBlock("lime_wool_slab",         Blocks.LIME_WOOL,       p -> new SlabBlock(p));
    public static final DeferredBlock<Block> LIME_WOOL_STAIRS       = woolBlock("lime_wool_stairs",       Blocks.LIME_WOOL,       p -> new StairBlock(Blocks.LIME_WOOL.defaultBlockState(), p));
    public static final DeferredBlock<Block> PINK_WOOL_SLAB         = woolBlock("pink_wool_slab",         Blocks.PINK_WOOL,       p -> new SlabBlock(p));
    public static final DeferredBlock<Block> PINK_WOOL_STAIRS       = woolBlock("pink_wool_stairs",       Blocks.PINK_WOOL,       p -> new StairBlock(Blocks.PINK_WOOL.defaultBlockState(), p));
    public static final DeferredBlock<Block> GRAY_WOOL_SLAB         = woolBlock("gray_wool_slab",         Blocks.GRAY_WOOL,       p -> new SlabBlock(p));
    public static final DeferredBlock<Block> GRAY_WOOL_STAIRS       = woolBlock("gray_wool_stairs",       Blocks.GRAY_WOOL,       p -> new StairBlock(Blocks.GRAY_WOOL.defaultBlockState(), p));
    public static final DeferredBlock<Block> LIGHT_GRAY_WOOL_SLAB   = woolBlock("light_gray_wool_slab",   Blocks.LIGHT_GRAY_WOOL, p -> new SlabBlock(p));
    public static final DeferredBlock<Block> LIGHT_GRAY_WOOL_STAIRS = woolBlock("light_gray_wool_stairs", Blocks.LIGHT_GRAY_WOOL, p -> new StairBlock(Blocks.LIGHT_GRAY_WOOL.defaultBlockState(), p));
    public static final DeferredBlock<Block> CYAN_WOOL_SLAB         = woolBlock("cyan_wool_slab",         Blocks.CYAN_WOOL,       p -> new SlabBlock(p));
    public static final DeferredBlock<Block> CYAN_WOOL_STAIRS       = woolBlock("cyan_wool_stairs",       Blocks.CYAN_WOOL,       p -> new StairBlock(Blocks.CYAN_WOOL.defaultBlockState(), p));
    public static final DeferredBlock<Block> PURPLE_WOOL_SLAB       = woolBlock("purple_wool_slab",       Blocks.PURPLE_WOOL,     p -> new SlabBlock(p));
    public static final DeferredBlock<Block> PURPLE_WOOL_STAIRS     = woolBlock("purple_wool_stairs",     Blocks.PURPLE_WOOL,     p -> new StairBlock(Blocks.PURPLE_WOOL.defaultBlockState(), p));
    public static final DeferredBlock<Block> BLUE_WOOL_SLAB         = woolBlock("blue_wool_slab",         Blocks.BLUE_WOOL,       p -> new SlabBlock(p));
    public static final DeferredBlock<Block> BLUE_WOOL_STAIRS       = woolBlock("blue_wool_stairs",       Blocks.BLUE_WOOL,       p -> new StairBlock(Blocks.BLUE_WOOL.defaultBlockState(), p));
    public static final DeferredBlock<Block> BROWN_WOOL_SLAB        = woolBlock("brown_wool_slab",        Blocks.BROWN_WOOL,      p -> new SlabBlock(p));
    public static final DeferredBlock<Block> BROWN_WOOL_STAIRS      = woolBlock("brown_wool_stairs",      Blocks.BROWN_WOOL,      p -> new StairBlock(Blocks.BROWN_WOOL.defaultBlockState(), p));
    public static final DeferredBlock<Block> GREEN_WOOL_SLAB        = woolBlock("green_wool_slab",        Blocks.GREEN_WOOL,      p -> new SlabBlock(p));
    public static final DeferredBlock<Block> GREEN_WOOL_STAIRS      = woolBlock("green_wool_stairs",      Blocks.GREEN_WOOL,      p -> new StairBlock(Blocks.GREEN_WOOL.defaultBlockState(), p));
    public static final DeferredBlock<Block> RED_WOOL_SLAB          = woolBlock("red_wool_slab",          Blocks.RED_WOOL,        p -> new SlabBlock(p));
    public static final DeferredBlock<Block> RED_WOOL_STAIRS        = woolBlock("red_wool_stairs",        Blocks.RED_WOOL,        p -> new StairBlock(Blocks.RED_WOOL.defaultBlockState(), p));
    public static final DeferredBlock<Block> BLACK_WOOL_SLAB        = woolBlock("black_wool_slab",        Blocks.BLACK_WOOL,      p -> new SlabBlock(p));
    public static final DeferredBlock<Block> BLACK_WOOL_STAIRS      = woolBlock("black_wool_stairs",      Blocks.BLACK_WOOL,      p -> new StairBlock(Blocks.BLACK_WOOL.defaultBlockState(), p));

    // ── Vertical Slabs — every GOT slab + every vanilla slab ──────────────
    public static final DeferredBlock<Block> ACACIA_ROOFING_VERTICAL_SLAB = verticalSlab("acacia_roofing_vertical_slab", GotModBlocks.ACACIA_ROOFING_SLAB);
    public static final DeferredBlock<Block> ACACIA_WOOD_VERTICAL_SLAB = verticalSlab("acacia_wood_vertical_slab", GotModBlocks.ACACIA_WOOD_SLAB);
    public static final DeferredBlock<Block> ALDER_ROOFING_VERTICAL_SLAB = verticalSlab("alder_roofing_vertical_slab", GotModBlocks.ALDER_ROOFING_SLAB);
    public static final DeferredBlock<Block> ALDER_VERTICAL_SLAB = verticalSlab("alder_vertical_slab", GotModBlocks.ALDER_SLAB);
    public static final DeferredBlock<Block> ALDER_WOOD_VERTICAL_SLAB = verticalSlab("alder_wood_vertical_slab", GotModBlocks.ALDER_WOOD_SLAB);
    public static final DeferredBlock<Block> ALMOND_ROOFING_VERTICAL_SLAB = verticalSlab("almond_roofing_vertical_slab", GotModBlocks.ALMOND_ROOFING_SLAB);
    public static final DeferredBlock<Block> ALMOND_VERTICAL_SLAB = verticalSlab("almond_vertical_slab", GotModBlocks.ALMOND_SLAB);
    public static final DeferredBlock<Block> ALMOND_WOOD_VERTICAL_SLAB = verticalSlab("almond_wood_vertical_slab", GotModBlocks.ALMOND_WOOD_SLAB);
    public static final DeferredBlock<Block> APPLE_ROOFING_VERTICAL_SLAB = verticalSlab("apple_roofing_vertical_slab", GotModBlocks.APPLE_ROOFING_SLAB);
    public static final DeferredBlock<Block> APPLE_VERTICAL_SLAB = verticalSlab("apple_vertical_slab", GotModBlocks.APPLE_SLAB);
    public static final DeferredBlock<Block> APPLE_WOOD_VERTICAL_SLAB = verticalSlab("apple_wood_vertical_slab", GotModBlocks.APPLE_WOOD_SLAB);
    public static final DeferredBlock<Block> APRICOT_ROOFING_VERTICAL_SLAB = verticalSlab("apricot_roofing_vertical_slab", GotModBlocks.APRICOT_ROOFING_SLAB);
    public static final DeferredBlock<Block> APRICOT_VERTICAL_SLAB = verticalSlab("apricot_vertical_slab", GotModBlocks.APRICOT_SLAB);
    public static final DeferredBlock<Block> APRICOT_WOOD_VERTICAL_SLAB = verticalSlab("apricot_wood_vertical_slab", GotModBlocks.APRICOT_WOOD_SLAB);
    public static final DeferredBlock<Block> ASH_ROOFING_VERTICAL_SLAB = verticalSlab("ash_roofing_vertical_slab", GotModBlocks.ASH_ROOFING_SLAB);
    public static final DeferredBlock<Block> ASH_VERTICAL_SLAB = verticalSlab("ash_vertical_slab", GotModBlocks.ASH_SLAB);
    public static final DeferredBlock<Block> ASH_WOOD_VERTICAL_SLAB = verticalSlab("ash_wood_vertical_slab", GotModBlocks.ASH_WOOD_SLAB);
    public static final DeferredBlock<Block> ASPEN_ROOFING_VERTICAL_SLAB = verticalSlab("aspen_roofing_vertical_slab", GotModBlocks.ASPEN_ROOFING_SLAB);
    public static final DeferredBlock<Block> ASPEN_VERTICAL_SLAB = verticalSlab("aspen_vertical_slab", GotModBlocks.ASPEN_SLAB);
    public static final DeferredBlock<Block> ASPEN_WOOD_VERTICAL_SLAB = verticalSlab("aspen_wood_vertical_slab", GotModBlocks.ASPEN_WOOD_SLAB);
    public static final DeferredBlock<Block> BAMBOO_ROOFING_VERTICAL_SLAB = verticalSlab("bamboo_roofing_vertical_slab", GotModBlocks.BAMBOO_ROOFING_SLAB);
    public static final DeferredBlock<Block> BASALT_BRICK_VERTICAL_SLAB = verticalSlab("basalt_brick_vertical_slab", GotModBlocks.BASALT_BRICK_SLAB);
    public static final DeferredBlock<Block> BASALT_COBBLESTONE_VERTICAL_SLAB = verticalSlab("basalt_cobblestone_vertical_slab", GotModBlocks.BASALT_COBBLESTONE_SLAB);
    public static final DeferredBlock<Block> BASALT_ROCK_VERTICAL_SLAB = verticalSlab("basalt_rock_vertical_slab", GotModBlocks.BASALT_ROCK_SLAB);
    public static final DeferredBlock<Block> BEECH_ROOFING_VERTICAL_SLAB = verticalSlab("beech_roofing_vertical_slab", GotModBlocks.BEECH_ROOFING_SLAB);
    public static final DeferredBlock<Block> BEECH_VERTICAL_SLAB = verticalSlab("beech_vertical_slab", GotModBlocks.BEECH_SLAB);
    public static final DeferredBlock<Block> BEECH_WOOD_VERTICAL_SLAB = verticalSlab("beech_wood_vertical_slab", GotModBlocks.BEECH_WOOD_SLAB);
    public static final DeferredBlock<Block> BIRCH_ROOFING_VERTICAL_SLAB = verticalSlab("birch_roofing_vertical_slab", GotModBlocks.BIRCH_ROOFING_SLAB);
    public static final DeferredBlock<Block> BIRCH_WOOD_VERTICAL_SLAB = verticalSlab("birch_wood_vertical_slab", GotModBlocks.BIRCH_WOOD_SLAB);
    public static final DeferredBlock<Block> BLACK_CHERRY_ROOFING_VERTICAL_SLAB = verticalSlab("black_cherry_roofing_vertical_slab", GotModBlocks.BLACK_CHERRY_ROOFING_SLAB);
    public static final DeferredBlock<Block> BLACK_CHERRY_VERTICAL_SLAB = verticalSlab("black_cherry_vertical_slab", GotModBlocks.BLACK_CHERRY_SLAB);
    public static final DeferredBlock<Block> BLACK_CHERRY_WOOD_VERTICAL_SLAB = verticalSlab("black_cherry_wood_vertical_slab", GotModBlocks.BLACK_CHERRY_WOOD_SLAB);
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_ROOFING_VERTICAL_SLAB = verticalSlab("black_cottonwood_roofing_vertical_slab", GotModBlocks.BLACK_COTTONWOOD_ROOFING_SLAB);
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_VERTICAL_SLAB = verticalSlab("black_cottonwood_vertical_slab", GotModBlocks.BLACK_COTTONWOOD_SLAB);
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_WOOD_VERTICAL_SLAB = verticalSlab("black_cottonwood_wood_vertical_slab", GotModBlocks.BLACK_COTTONWOOD_WOOD_SLAB);
    public static final DeferredBlock<Block> BLACK_WOOL_VERTICAL_SLAB = verticalSlab("black_wool_vertical_slab", GotModBlocks.BLACK_WOOL_SLAB);
    public static final DeferredBlock<Block> BLACKBARK_ROOFING_VERTICAL_SLAB = verticalSlab("blackbark_roofing_vertical_slab", GotModBlocks.BLACKBARK_ROOFING_SLAB);
    public static final DeferredBlock<Block> BLACKBARK_VERTICAL_SLAB = verticalSlab("blackbark_vertical_slab", GotModBlocks.BLACKBARK_SLAB);
    public static final DeferredBlock<Block> BLACKBARK_WOOD_VERTICAL_SLAB = verticalSlab("blackbark_wood_vertical_slab", GotModBlocks.BLACKBARK_WOOD_SLAB);
    public static final DeferredBlock<Block> BLACKTHORN_ROOFING_VERTICAL_SLAB = verticalSlab("blackthorn_roofing_vertical_slab", GotModBlocks.BLACKTHORN_ROOFING_SLAB);
    public static final DeferredBlock<Block> BLACKTHORN_VERTICAL_SLAB = verticalSlab("blackthorn_vertical_slab", GotModBlocks.BLACKTHORN_SLAB);
    public static final DeferredBlock<Block> BLACKTHORN_WOOD_VERTICAL_SLAB = verticalSlab("blackthorn_wood_vertical_slab", GotModBlocks.BLACKTHORN_WOOD_SLAB);
    public static final DeferredBlock<Block> BLOODWOOD_ROOFING_VERTICAL_SLAB = verticalSlab("bloodwood_roofing_vertical_slab", GotModBlocks.BLOODWOOD_ROOFING_SLAB);
    public static final DeferredBlock<Block> BLOODWOOD_VERTICAL_SLAB = verticalSlab("bloodwood_vertical_slab", GotModBlocks.BLOODWOOD_SLAB);
    public static final DeferredBlock<Block> BLOODWOOD_WOOD_VERTICAL_SLAB = verticalSlab("bloodwood_wood_vertical_slab", GotModBlocks.BLOODWOOD_WOOD_SLAB);
    public static final DeferredBlock<Block> BLUE_MAHOE_ROOFING_VERTICAL_SLAB = verticalSlab("blue_mahoe_roofing_vertical_slab", GotModBlocks.BLUE_MAHOE_ROOFING_SLAB);
    public static final DeferredBlock<Block> BLUE_MAHOE_VERTICAL_SLAB = verticalSlab("blue_mahoe_vertical_slab", GotModBlocks.BLUE_MAHOE_SLAB);
    public static final DeferredBlock<Block> BLUE_MAHOE_WOOD_VERTICAL_SLAB = verticalSlab("blue_mahoe_wood_vertical_slab", GotModBlocks.BLUE_MAHOE_WOOD_SLAB);
    public static final DeferredBlock<Block> BLUE_WOOL_VERTICAL_SLAB = verticalSlab("blue_wool_vertical_slab", GotModBlocks.BLUE_WOOL_SLAB);
    public static final DeferredBlock<Block> BROWN_WOOL_VERTICAL_SLAB = verticalSlab("brown_wool_vertical_slab", GotModBlocks.BROWN_WOOL_SLAB);
    public static final DeferredBlock<Block> BURL_ROOFING_VERTICAL_SLAB = verticalSlab("burl_roofing_vertical_slab", GotModBlocks.BURL_ROOFING_SLAB);
    public static final DeferredBlock<Block> BURL_VERTICAL_SLAB = verticalSlab("burl_vertical_slab", GotModBlocks.BURL_SLAB);
    public static final DeferredBlock<Block> BURL_WOOD_VERTICAL_SLAB = verticalSlab("burl_wood_vertical_slab", GotModBlocks.BURL_WOOD_SLAB);
    public static final DeferredBlock<Block> CEDAR_ROOFING_VERTICAL_SLAB = verticalSlab("cedar_roofing_vertical_slab", GotModBlocks.CEDAR_ROOFING_SLAB);
    public static final DeferredBlock<Block> CEDAR_VERTICAL_SLAB = verticalSlab("cedar_vertical_slab", GotModBlocks.CEDAR_SLAB);
    public static final DeferredBlock<Block> CEDAR_WOOD_VERTICAL_SLAB = verticalSlab("cedar_wood_vertical_slab", GotModBlocks.CEDAR_WOOD_SLAB);
    public static final DeferredBlock<Block> CHERRY_ROOFING_VERTICAL_SLAB = verticalSlab("cherry_roofing_vertical_slab", GotModBlocks.CHERRY_ROOFING_SLAB);
    public static final DeferredBlock<Block> CHERRY_WOOD_VERTICAL_SLAB = verticalSlab("cherry_wood_vertical_slab", GotModBlocks.CHERRY_WOOD_SLAB);
    public static final DeferredBlock<Block> CHESTNUT_ROOFING_VERTICAL_SLAB = verticalSlab("chestnut_roofing_vertical_slab", GotModBlocks.CHESTNUT_ROOFING_SLAB);
    public static final DeferredBlock<Block> CHESTNUT_VERTICAL_SLAB = verticalSlab("chestnut_vertical_slab", GotModBlocks.CHESTNUT_SLAB);
    public static final DeferredBlock<Block> CHESTNUT_WOOD_VERTICAL_SLAB = verticalSlab("chestnut_wood_vertical_slab", GotModBlocks.CHESTNUT_WOOD_SLAB);
    public static final DeferredBlock<Block> CINNAMON_ROOFING_VERTICAL_SLAB = verticalSlab("cinnamon_roofing_vertical_slab", GotModBlocks.CINNAMON_ROOFING_SLAB);
    public static final DeferredBlock<Block> CINNAMON_VERTICAL_SLAB = verticalSlab("cinnamon_vertical_slab", GotModBlocks.CINNAMON_SLAB);
    public static final DeferredBlock<Block> CINNAMON_WOOD_VERTICAL_SLAB = verticalSlab("cinnamon_wood_vertical_slab", GotModBlocks.CINNAMON_WOOD_SLAB);
    public static final DeferredBlock<Block> CLOVE_ROOFING_VERTICAL_SLAB = verticalSlab("clove_roofing_vertical_slab", GotModBlocks.CLOVE_ROOFING_SLAB);
    public static final DeferredBlock<Block> CLOVE_VERTICAL_SLAB = verticalSlab("clove_vertical_slab", GotModBlocks.CLOVE_SLAB);
    public static final DeferredBlock<Block> CLOVE_WOOD_VERTICAL_SLAB = verticalSlab("clove_wood_vertical_slab", GotModBlocks.CLOVE_WOOD_SLAB);
    public static final DeferredBlock<Block> COARSE_DIRT_VERTICAL_SLAB = verticalSlab("coarse_dirt_vertical_slab", GotModBlocks.COARSE_DIRT_SLAB);
    public static final DeferredBlock<Block> COTTONWOOD_ROOFING_VERTICAL_SLAB = verticalSlab("cottonwood_roofing_vertical_slab", GotModBlocks.COTTONWOOD_ROOFING_SLAB);
    public static final DeferredBlock<Block> COTTONWOOD_VERTICAL_SLAB = verticalSlab("cottonwood_vertical_slab", GotModBlocks.COTTONWOOD_SLAB);
    public static final DeferredBlock<Block> COTTONWOOD_WOOD_VERTICAL_SLAB = verticalSlab("cottonwood_wood_vertical_slab", GotModBlocks.COTTONWOOD_WOOD_SLAB);
    public static final DeferredBlock<Block> CRABAPPLE_ROOFING_VERTICAL_SLAB = verticalSlab("crabapple_roofing_vertical_slab", GotModBlocks.CRABAPPLE_ROOFING_SLAB);
    public static final DeferredBlock<Block> CRABAPPLE_VERTICAL_SLAB = verticalSlab("crabapple_vertical_slab", GotModBlocks.CRABAPPLE_SLAB);
    public static final DeferredBlock<Block> CRABAPPLE_WOOD_VERTICAL_SLAB = verticalSlab("crabapple_wood_vertical_slab", GotModBlocks.CRABAPPLE_WOOD_SLAB);
    public static final DeferredBlock<Block> CRACKED_BASALT_BRICK_VERTICAL_SLAB = verticalSlab("cracked_basalt_brick_vertical_slab", GotModBlocks.CRACKED_BASALT_BRICK_SLAB);
    public static final DeferredBlock<Block> CRACKED_FLINT_BRICK_VERTICAL_SLAB = verticalSlab("cracked_flint_brick_vertical_slab", GotModBlocks.CRACKED_FLINT_BRICK_SLAB);
    public static final DeferredBlock<Block> CRACKED_FUSED_BLACK_BRICK_VERTICAL_SLAB = verticalSlab("cracked_fused_black_brick_vertical_slab", GotModBlocks.CRACKED_FUSED_BLACK_BRICK_SLAB);
    public static final DeferredBlock<Block> CRACKED_GREY_GRANITE_BRICK_VERTICAL_SLAB = verticalSlab("cracked_grey_granite_brick_vertical_slab", GotModBlocks.CRACKED_GREY_GRANITE_BRICK_SLAB);
    public static final DeferredBlock<Block> CRACKED_LIMESTONE_BRICK_VERTICAL_SLAB = verticalSlab("cracked_limestone_brick_vertical_slab", GotModBlocks.CRACKED_LIMESTONE_BRICK_SLAB);
    public static final DeferredBlock<Block> CRACKED_MARBLE_BRICK_VERTICAL_SLAB = verticalSlab("cracked_marble_brick_vertical_slab", GotModBlocks.CRACKED_MARBLE_BRICK_SLAB);
    public static final DeferredBlock<Block> CRACKED_OILY_BLACK_BRICK_VERTICAL_SLAB = verticalSlab("cracked_oily_black_brick_vertical_slab", GotModBlocks.CRACKED_OILY_BLACK_BRICK_SLAB);
    public static final DeferredBlock<Block> CRACKED_RED_SANDSTONE_BRICK_VERTICAL_SLAB = verticalSlab("cracked_red_sandstone_brick_vertical_slab", GotModBlocks.CRACKED_RED_SANDSTONE_BRICK_SLAB);
    public static final DeferredBlock<Block> CRACKED_SANDSTONE_BRICK_VERTICAL_SLAB = verticalSlab("cracked_sandstone_brick_vertical_slab", GotModBlocks.CRACKED_SANDSTONE_BRICK_SLAB);
    public static final DeferredBlock<Block> CRACKED_SLATE_BRICK_VERTICAL_SLAB = verticalSlab("cracked_slate_brick_vertical_slab", GotModBlocks.CRACKED_SLATE_BRICK_SLAB);
    public static final DeferredBlock<Block> CYAN_WOOL_VERTICAL_SLAB = verticalSlab("cyan_wool_vertical_slab", GotModBlocks.CYAN_WOOL_SLAB);
    public static final DeferredBlock<Block> DARK_OAK_ROOFING_VERTICAL_SLAB = verticalSlab("dark_oak_roofing_vertical_slab", GotModBlocks.DARK_OAK_ROOFING_SLAB);
    public static final DeferredBlock<Block> DARK_OAK_WOOD_VERTICAL_SLAB = verticalSlab("dark_oak_wood_vertical_slab", GotModBlocks.DARK_OAK_WOOD_SLAB);
    public static final DeferredBlock<Block> DARK_THATCH_VERTICAL_SLAB = verticalSlab("dark_thatch_vertical_slab", GotModBlocks.DARK_THATCH_SLAB);
    public static final DeferredBlock<Block> DATE_PALM_ROOFING_VERTICAL_SLAB = verticalSlab("date_palm_roofing_vertical_slab", GotModBlocks.DATE_PALM_ROOFING_SLAB);
    public static final DeferredBlock<Block> DATE_PALM_VERTICAL_SLAB = verticalSlab("date_palm_vertical_slab", GotModBlocks.DATE_PALM_SLAB);
    public static final DeferredBlock<Block> DATE_PALM_WOOD_VERTICAL_SLAB = verticalSlab("date_palm_wood_vertical_slab", GotModBlocks.DATE_PALM_WOOD_SLAB);
    public static final DeferredBlock<Block> DIRT_PATH_VERTICAL_SLAB = verticalSlab("dirt_path_vertical_slab", GotModBlocks.DIRT_PATH_SLAB);
    public static final DeferredBlock<Block> DIRT_VERTICAL_SLAB = verticalSlab("dirt_vertical_slab", GotModBlocks.DIRT_SLAB);
    public static final DeferredBlock<Block> EBONY_ROOFING_VERTICAL_SLAB = verticalSlab("ebony_roofing_vertical_slab", GotModBlocks.EBONY_ROOFING_SLAB);
    public static final DeferredBlock<Block> EBONY_VERTICAL_SLAB = verticalSlab("ebony_vertical_slab", GotModBlocks.EBONY_SLAB);
    public static final DeferredBlock<Block> EBONY_WOOD_VERTICAL_SLAB = verticalSlab("ebony_wood_vertical_slab", GotModBlocks.EBONY_WOOD_SLAB);
    public static final DeferredBlock<Block> ELM_ROOFING_VERTICAL_SLAB = verticalSlab("elm_roofing_vertical_slab", GotModBlocks.ELM_ROOFING_SLAB);
    public static final DeferredBlock<Block> ELM_VERTICAL_SLAB = verticalSlab("elm_vertical_slab", GotModBlocks.ELM_SLAB);
    public static final DeferredBlock<Block> ELM_WOOD_VERTICAL_SLAB = verticalSlab("elm_wood_vertical_slab", GotModBlocks.ELM_WOOD_SLAB);
    public static final DeferredBlock<Block> FIELDSTONE_VERTICAL_SLAB = verticalSlab("fieldstone_vertical_slab", GotModBlocks.FIELDSTONE_SLAB);
    public static final DeferredBlock<Block> FIG_ROOFING_VERTICAL_SLAB = verticalSlab("fig_roofing_vertical_slab", GotModBlocks.FIG_ROOFING_SLAB);
    public static final DeferredBlock<Block> FIG_VERTICAL_SLAB = verticalSlab("fig_vertical_slab", GotModBlocks.FIG_SLAB);
    public static final DeferredBlock<Block> FIG_WOOD_VERTICAL_SLAB = verticalSlab("fig_wood_vertical_slab", GotModBlocks.FIG_WOOD_SLAB);
    public static final DeferredBlock<Block> FIR_ROOFING_VERTICAL_SLAB = verticalSlab("fir_roofing_vertical_slab", GotModBlocks.FIR_ROOFING_SLAB);
    public static final DeferredBlock<Block> FIR_VERTICAL_SLAB = verticalSlab("fir_vertical_slab", GotModBlocks.FIR_SLAB);
    public static final DeferredBlock<Block> FIR_WOOD_VERTICAL_SLAB = verticalSlab("fir_wood_vertical_slab", GotModBlocks.FIR_WOOD_SLAB);
    public static final DeferredBlock<Block> FLINT_BRICK_VERTICAL_SLAB = verticalSlab("flint_brick_vertical_slab", GotModBlocks.FLINT_BRICK_SLAB);
    public static final DeferredBlock<Block> FLINT_ROCK_VERTICAL_SLAB = verticalSlab("flint_rock_vertical_slab", GotModBlocks.FLINT_ROCK_SLAB);
    public static final DeferredBlock<Block> FUSED_BLACK_BRICK_VERTICAL_SLAB = verticalSlab("fused_black_brick_vertical_slab", GotModBlocks.FUSED_BLACK_BRICK_SLAB);
    public static final DeferredBlock<Block> FUSED_BLACK_COBBLESTONE_VERTICAL_SLAB = verticalSlab("fused_black_cobblestone_vertical_slab", GotModBlocks.FUSED_BLACK_COBBLESTONE_SLAB);
    public static final DeferredBlock<Block> FUSED_BLACK_ROCK_VERTICAL_SLAB = verticalSlab("fused_black_rock_vertical_slab", GotModBlocks.FUSED_BLACK_ROCK_SLAB);
    public static final DeferredBlock<Block> GOLDENHEART_ROOFING_VERTICAL_SLAB = verticalSlab("goldenheart_roofing_vertical_slab", GotModBlocks.GOLDENHEART_ROOFING_SLAB);
    public static final DeferredBlock<Block> GOLDENHEART_VERTICAL_SLAB = verticalSlab("goldenheart_vertical_slab", GotModBlocks.GOLDENHEART_SLAB);
    public static final DeferredBlock<Block> GOLDENHEART_WOOD_VERTICAL_SLAB = verticalSlab("goldenheart_wood_vertical_slab", GotModBlocks.GOLDENHEART_WOOD_SLAB);
    public static final DeferredBlock<Block> GRASS_BLOCK_VERTICAL_SLAB = verticalSlab("grass_block_vertical_slab", GotModBlocks.GRASS_BLOCK_SLAB);
    public static final DeferredBlock<Block> GRAY_WOOL_VERTICAL_SLAB = verticalSlab("gray_wool_vertical_slab", GotModBlocks.GRAY_WOOL_SLAB);
    public static final DeferredBlock<Block> GREEN_WOOL_VERTICAL_SLAB = verticalSlab("green_wool_vertical_slab", GotModBlocks.GREEN_WOOL_SLAB);
    public static final DeferredBlock<Block> GREY_GRANITE_BRICK_VERTICAL_SLAB = verticalSlab("grey_granite_brick_vertical_slab", GotModBlocks.GREY_GRANITE_BRICK_SLAB);
    public static final DeferredBlock<Block> GREY_GRANITE_COBBLESTONE_VERTICAL_SLAB = verticalSlab("grey_granite_cobblestone_vertical_slab", GotModBlocks.GREY_GRANITE_COBBLESTONE_SLAB);
    public static final DeferredBlock<Block> GREY_GRANITE_ROCK_VERTICAL_SLAB = verticalSlab("grey_granite_rock_vertical_slab", GotModBlocks.GREY_GRANITE_ROCK_SLAB);
    public static final DeferredBlock<Block> HAWTHORN_ROOFING_VERTICAL_SLAB = verticalSlab("hawthorn_roofing_vertical_slab", GotModBlocks.HAWTHORN_ROOFING_SLAB);
    public static final DeferredBlock<Block> HAWTHORN_VERTICAL_SLAB = verticalSlab("hawthorn_vertical_slab", GotModBlocks.HAWTHORN_SLAB);
    public static final DeferredBlock<Block> HAWTHORN_WOOD_VERTICAL_SLAB = verticalSlab("hawthorn_wood_vertical_slab", GotModBlocks.HAWTHORN_WOOD_SLAB);
    public static final DeferredBlock<Block> HEMLOCK_ROOFING_VERTICAL_SLAB = verticalSlab("hemlock_roofing_vertical_slab", GotModBlocks.HEMLOCK_ROOFING_SLAB);
    public static final DeferredBlock<Block> HEMLOCK_VERTICAL_SLAB = verticalSlab("hemlock_vertical_slab", GotModBlocks.HEMLOCK_SLAB);
    public static final DeferredBlock<Block> HEMLOCK_WOOD_VERTICAL_SLAB = verticalSlab("hemlock_wood_vertical_slab", GotModBlocks.HEMLOCK_WOOD_SLAB);
    public static final DeferredBlock<Block> IRONWOOD_ROOFING_VERTICAL_SLAB = verticalSlab("ironwood_roofing_vertical_slab", GotModBlocks.IRONWOOD_ROOFING_SLAB);
    public static final DeferredBlock<Block> IRONWOOD_VERTICAL_SLAB = verticalSlab("ironwood_vertical_slab", GotModBlocks.IRONWOOD_SLAB);
    public static final DeferredBlock<Block> IRONWOOD_WOOD_VERTICAL_SLAB = verticalSlab("ironwood_wood_vertical_slab", GotModBlocks.IRONWOOD_WOOD_SLAB);
    public static final DeferredBlock<Block> JUNGLE_ROOFING_VERTICAL_SLAB = verticalSlab("jungle_roofing_vertical_slab", GotModBlocks.JUNGLE_ROOFING_SLAB);
    public static final DeferredBlock<Block> JUNGLE_WOOD_VERTICAL_SLAB = verticalSlab("jungle_wood_vertical_slab", GotModBlocks.JUNGLE_WOOD_SLAB);
    public static final DeferredBlock<Block> LEMON_ROOFING_VERTICAL_SLAB = verticalSlab("lemon_roofing_vertical_slab", GotModBlocks.LEMON_ROOFING_SLAB);
    public static final DeferredBlock<Block> LEMON_VERTICAL_SLAB = verticalSlab("lemon_vertical_slab", GotModBlocks.LEMON_SLAB);
    public static final DeferredBlock<Block> LEMON_WOOD_VERTICAL_SLAB = verticalSlab("lemon_wood_vertical_slab", GotModBlocks.LEMON_WOOD_SLAB);
    public static final DeferredBlock<Block> LIGHT_BLUE_WOOL_VERTICAL_SLAB = verticalSlab("light_blue_wool_vertical_slab", GotModBlocks.LIGHT_BLUE_WOOL_SLAB);
    public static final DeferredBlock<Block> LIGHT_GRAY_WOOL_VERTICAL_SLAB = verticalSlab("light_gray_wool_vertical_slab", GotModBlocks.LIGHT_GRAY_WOOL_SLAB);
    public static final DeferredBlock<Block> LIGHT_THATCH_VERTICAL_SLAB = verticalSlab("light_thatch_vertical_slab", GotModBlocks.LIGHT_THATCH_SLAB);
    public static final DeferredBlock<Block> LIME_ROOFING_VERTICAL_SLAB = verticalSlab("lime_roofing_vertical_slab", GotModBlocks.LIME_ROOFING_SLAB);
    public static final DeferredBlock<Block> LIME_VERTICAL_SLAB = verticalSlab("lime_vertical_slab", GotModBlocks.LIME_SLAB);
    public static final DeferredBlock<Block> LIME_WOOD_VERTICAL_SLAB = verticalSlab("lime_wood_vertical_slab", GotModBlocks.LIME_WOOD_SLAB);
    public static final DeferredBlock<Block> LIME_WOOL_VERTICAL_SLAB = verticalSlab("lime_wool_vertical_slab", GotModBlocks.LIME_WOOL_SLAB);
    public static final DeferredBlock<Block> LIMESTONE_BRICK_VERTICAL_SLAB = verticalSlab("limestone_brick_vertical_slab", GotModBlocks.LIMESTONE_BRICK_SLAB);
    public static final DeferredBlock<Block> LIMESTONE_COBBLESTONE_VERTICAL_SLAB = verticalSlab("limestone_cobblestone_vertical_slab", GotModBlocks.LIMESTONE_COBBLESTONE_SLAB);
    public static final DeferredBlock<Block> LIMESTONE_ROCK_VERTICAL_SLAB = verticalSlab("limestone_rock_vertical_slab", GotModBlocks.LIMESTONE_ROCK_SLAB);
    public static final DeferredBlock<Block> LINDEN_ROOFING_VERTICAL_SLAB = verticalSlab("linden_roofing_vertical_slab", GotModBlocks.LINDEN_ROOFING_SLAB);
    public static final DeferredBlock<Block> LINDEN_VERTICAL_SLAB = verticalSlab("linden_vertical_slab", GotModBlocks.LINDEN_SLAB);
    public static final DeferredBlock<Block> LINDEN_WOOD_VERTICAL_SLAB = verticalSlab("linden_wood_vertical_slab", GotModBlocks.LINDEN_WOOD_SLAB);
    public static final DeferredBlock<Block> MAGENTA_WOOL_VERTICAL_SLAB = verticalSlab("magenta_wool_vertical_slab", GotModBlocks.MAGENTA_WOOL_SLAB);
    public static final DeferredBlock<Block> MAHOGANY_ROOFING_VERTICAL_SLAB = verticalSlab("mahogany_roofing_vertical_slab", GotModBlocks.MAHOGANY_ROOFING_SLAB);
    public static final DeferredBlock<Block> MAHOGANY_VERTICAL_SLAB = verticalSlab("mahogany_vertical_slab", GotModBlocks.MAHOGANY_SLAB);
    public static final DeferredBlock<Block> MAHOGANY_WOOD_VERTICAL_SLAB = verticalSlab("mahogany_wood_vertical_slab", GotModBlocks.MAHOGANY_WOOD_SLAB);
    public static final DeferredBlock<Block> MANGROVE_ROOFING_VERTICAL_SLAB = verticalSlab("mangrove_roofing_vertical_slab", GotModBlocks.MANGROVE_ROOFING_SLAB);
    public static final DeferredBlock<Block> MANGROVE_WOOD_VERTICAL_SLAB = verticalSlab("mangrove_wood_vertical_slab", GotModBlocks.MANGROVE_WOOD_SLAB);
    public static final DeferredBlock<Block> MAPLE_ROOFING_VERTICAL_SLAB = verticalSlab("maple_roofing_vertical_slab", GotModBlocks.MAPLE_ROOFING_SLAB);
    public static final DeferredBlock<Block> MAPLE_VERTICAL_SLAB = verticalSlab("maple_vertical_slab", GotModBlocks.MAPLE_SLAB);
    public static final DeferredBlock<Block> MAPLE_WOOD_VERTICAL_SLAB = verticalSlab("maple_wood_vertical_slab", GotModBlocks.MAPLE_WOOD_SLAB);
    public static final DeferredBlock<Block> MARBLE_BRICK_VERTICAL_SLAB = verticalSlab("marble_brick_vertical_slab", GotModBlocks.MARBLE_BRICK_SLAB);
    public static final DeferredBlock<Block> MARBLE_COBBLESTONE_VERTICAL_SLAB = verticalSlab("marble_cobblestone_vertical_slab", GotModBlocks.MARBLE_COBBLESTONE_SLAB);
    public static final DeferredBlock<Block> MARBLE_ROCK_VERTICAL_SLAB = verticalSlab("marble_rock_vertical_slab", GotModBlocks.MARBLE_ROCK_SLAB);
    public static final DeferredBlock<Block> MOSSY_BASALT_BRICK_VERTICAL_SLAB = verticalSlab("mossy_basalt_brick_vertical_slab", GotModBlocks.MOSSY_BASALT_BRICK_SLAB);
    public static final DeferredBlock<Block> MOSSY_BASALT_COBBLESTONE_VERTICAL_SLAB = verticalSlab("mossy_basalt_cobblestone_vertical_slab", GotModBlocks.MOSSY_BASALT_COBBLESTONE_SLAB);
    public static final DeferredBlock<Block> MOSSY_FLINT_BRICK_VERTICAL_SLAB = verticalSlab("mossy_flint_brick_vertical_slab", GotModBlocks.MOSSY_FLINT_BRICK_SLAB);
    public static final DeferredBlock<Block> MOSSY_FUSED_BLACK_BRICK_VERTICAL_SLAB = verticalSlab("mossy_fused_black_brick_vertical_slab", GotModBlocks.MOSSY_FUSED_BLACK_BRICK_SLAB);
    public static final DeferredBlock<Block> MOSSY_FUSED_BLACK_COBBLESTONE_VERTICAL_SLAB = verticalSlab("mossy_fused_black_cobblestone_vertical_slab", GotModBlocks.MOSSY_FUSED_BLACK_COBBLESTONE_SLAB);
    public static final DeferredBlock<Block> MOSSY_GREY_GRANITE_BRICK_VERTICAL_SLAB = verticalSlab("mossy_grey_granite_brick_vertical_slab", GotModBlocks.MOSSY_GREY_GRANITE_BRICK_SLAB);
    public static final DeferredBlock<Block> MOSSY_GREY_GRANITE_COBBLESTONE_VERTICAL_SLAB = verticalSlab("mossy_grey_granite_cobblestone_vertical_slab", GotModBlocks.MOSSY_GREY_GRANITE_COBBLESTONE_SLAB);
    public static final DeferredBlock<Block> MOSSY_LIMESTONE_BRICK_VERTICAL_SLAB = verticalSlab("mossy_limestone_brick_vertical_slab", GotModBlocks.MOSSY_LIMESTONE_BRICK_SLAB);
    public static final DeferredBlock<Block> MOSSY_LIMESTONE_COBBLESTONE_VERTICAL_SLAB = verticalSlab("mossy_limestone_cobblestone_vertical_slab", GotModBlocks.MOSSY_LIMESTONE_COBBLESTONE_SLAB);
    public static final DeferredBlock<Block> MOSSY_MARBLE_BRICK_VERTICAL_SLAB = verticalSlab("mossy_marble_brick_vertical_slab", GotModBlocks.MOSSY_MARBLE_BRICK_SLAB);
    public static final DeferredBlock<Block> MOSSY_MARBLE_COBBLESTONE_VERTICAL_SLAB = verticalSlab("mossy_marble_cobblestone_vertical_slab", GotModBlocks.MOSSY_MARBLE_COBBLESTONE_SLAB);
    public static final DeferredBlock<Block> MOSSY_OILY_BLACK_BRICK_VERTICAL_SLAB = verticalSlab("mossy_oily_black_brick_vertical_slab", GotModBlocks.MOSSY_OILY_BLACK_BRICK_SLAB);
    public static final DeferredBlock<Block> MOSSY_OILY_BLACK_COBBLESTONE_VERTICAL_SLAB = verticalSlab("mossy_oily_black_cobblestone_vertical_slab", GotModBlocks.MOSSY_OILY_BLACK_COBBLESTONE_SLAB);
    public static final DeferredBlock<Block> MOSSY_RED_SANDSTONE_BRICK_VERTICAL_SLAB = verticalSlab("mossy_red_sandstone_brick_vertical_slab", GotModBlocks.MOSSY_RED_SANDSTONE_BRICK_SLAB);
    public static final DeferredBlock<Block> MOSSY_RED_SANDSTONE_COBBLESTONE_VERTICAL_SLAB = verticalSlab("mossy_red_sandstone_cobblestone_vertical_slab", GotModBlocks.MOSSY_RED_SANDSTONE_COBBLESTONE_SLAB);
    public static final DeferredBlock<Block> MOSSY_SANDSTONE_BRICK_VERTICAL_SLAB = verticalSlab("mossy_sandstone_brick_vertical_slab", GotModBlocks.MOSSY_SANDSTONE_BRICK_SLAB);
    public static final DeferredBlock<Block> MOSSY_SANDSTONE_COBBLESTONE_VERTICAL_SLAB = verticalSlab("mossy_sandstone_cobblestone_vertical_slab", GotModBlocks.MOSSY_SANDSTONE_COBBLESTONE_SLAB);
    public static final DeferredBlock<Block> MOSSY_SLATE_BRICK_VERTICAL_SLAB = verticalSlab("mossy_slate_brick_vertical_slab", GotModBlocks.MOSSY_SLATE_BRICK_SLAB);
    public static final DeferredBlock<Block> MOSSY_SLATE_COBBLESTONE_VERTICAL_SLAB = verticalSlab("mossy_slate_cobblestone_vertical_slab", GotModBlocks.MOSSY_SLATE_COBBLESTONE_SLAB);
    public static final DeferredBlock<Block> MUD_VERTICAL_SLAB = verticalSlab("mud_vertical_slab", GotModBlocks.MUD_SLAB);
    public static final DeferredBlock<Block> MYRRH_ROOFING_VERTICAL_SLAB = verticalSlab("myrrh_roofing_vertical_slab", GotModBlocks.MYRRH_ROOFING_SLAB);
    public static final DeferredBlock<Block> MYRRH_VERTICAL_SLAB = verticalSlab("myrrh_vertical_slab", GotModBlocks.MYRRH_SLAB);
    public static final DeferredBlock<Block> MYRRH_WOOD_VERTICAL_SLAB = verticalSlab("myrrh_wood_vertical_slab", GotModBlocks.MYRRH_WOOD_SLAB);
    public static final DeferredBlock<Block> NIGHTWOOD_ROOFING_VERTICAL_SLAB = verticalSlab("nightwood_roofing_vertical_slab", GotModBlocks.NIGHTWOOD_ROOFING_SLAB);
    public static final DeferredBlock<Block> NIGHTWOOD_VERTICAL_SLAB = verticalSlab("nightwood_vertical_slab", GotModBlocks.NIGHTWOOD_SLAB);
    public static final DeferredBlock<Block> NIGHTWOOD_WOOD_VERTICAL_SLAB = verticalSlab("nightwood_wood_vertical_slab", GotModBlocks.NIGHTWOOD_WOOD_SLAB);
    public static final DeferredBlock<Block> NUTMEG_ROOFING_VERTICAL_SLAB = verticalSlab("nutmeg_roofing_vertical_slab", GotModBlocks.NUTMEG_ROOFING_SLAB);
    public static final DeferredBlock<Block> NUTMEG_VERTICAL_SLAB = verticalSlab("nutmeg_vertical_slab", GotModBlocks.NUTMEG_SLAB);
    public static final DeferredBlock<Block> NUTMEG_WOOD_VERTICAL_SLAB = verticalSlab("nutmeg_wood_vertical_slab", GotModBlocks.NUTMEG_WOOD_SLAB);
    public static final DeferredBlock<Block> OAK_ROOFING_VERTICAL_SLAB = verticalSlab("oak_roofing_vertical_slab", GotModBlocks.OAK_ROOFING_SLAB);
    public static final DeferredBlock<Block> OAK_WOOD_VERTICAL_SLAB = verticalSlab("oak_wood_vertical_slab", GotModBlocks.OAK_WOOD_SLAB);
    public static final DeferredBlock<Block> OILY_BLACK_BRICK_VERTICAL_SLAB = verticalSlab("oily_black_brick_vertical_slab", GotModBlocks.OILY_BLACK_BRICK_SLAB);
    public static final DeferredBlock<Block> OILY_BLACK_COBBLESTONE_VERTICAL_SLAB = verticalSlab("oily_black_cobblestone_vertical_slab", GotModBlocks.OILY_BLACK_COBBLESTONE_SLAB);
    public static final DeferredBlock<Block> OILY_BLACK_ROCK_VERTICAL_SLAB = verticalSlab("oily_black_rock_vertical_slab", GotModBlocks.OILY_BLACK_ROCK_SLAB);
    public static final DeferredBlock<Block> OLIVE_ROOFING_VERTICAL_SLAB = verticalSlab("olive_roofing_vertical_slab", GotModBlocks.OLIVE_ROOFING_SLAB);
    public static final DeferredBlock<Block> OLIVE_VERTICAL_SLAB = verticalSlab("olive_vertical_slab", GotModBlocks.OLIVE_SLAB);
    public static final DeferredBlock<Block> OLIVE_WOOD_VERTICAL_SLAB = verticalSlab("olive_wood_vertical_slab", GotModBlocks.OLIVE_WOOD_SLAB);
    public static final DeferredBlock<Block> ORANGE_ROOFING_VERTICAL_SLAB = verticalSlab("orange_roofing_vertical_slab", GotModBlocks.ORANGE_ROOFING_SLAB);
    public static final DeferredBlock<Block> ORANGE_VERTICAL_SLAB = verticalSlab("orange_vertical_slab", GotModBlocks.ORANGE_SLAB);
    public static final DeferredBlock<Block> ORANGE_WOOD_VERTICAL_SLAB = verticalSlab("orange_wood_vertical_slab", GotModBlocks.ORANGE_WOOD_SLAB);
    public static final DeferredBlock<Block> ORANGE_WOOL_VERTICAL_SLAB = verticalSlab("orange_wool_vertical_slab", GotModBlocks.ORANGE_WOOL_SLAB);
    public static final DeferredBlock<Block> PALE_OAK_WOOD_VERTICAL_SLAB = verticalSlab("pale_oak_wood_vertical_slab", GotModBlocks.PALE_OAK_WOOD_SLAB);
    public static final DeferredBlock<Block> PEACH_ROOFING_VERTICAL_SLAB = verticalSlab("peach_roofing_vertical_slab", GotModBlocks.PEACH_ROOFING_SLAB);
    public static final DeferredBlock<Block> PEACH_VERTICAL_SLAB = verticalSlab("peach_vertical_slab", GotModBlocks.PEACH_SLAB);
    public static final DeferredBlock<Block> PEACH_WOOD_VERTICAL_SLAB = verticalSlab("peach_wood_vertical_slab", GotModBlocks.PEACH_WOOD_SLAB);
    public static final DeferredBlock<Block> PEAR_ROOFING_VERTICAL_SLAB = verticalSlab("pear_roofing_vertical_slab", GotModBlocks.PEAR_ROOFING_SLAB);
    public static final DeferredBlock<Block> PEAR_VERTICAL_SLAB = verticalSlab("pear_vertical_slab", GotModBlocks.PEAR_SLAB);
    public static final DeferredBlock<Block> PEAR_WOOD_VERTICAL_SLAB = verticalSlab("pear_wood_vertical_slab", GotModBlocks.PEAR_WOOD_SLAB);
    public static final DeferredBlock<Block> PERSIMMON_ROOFING_VERTICAL_SLAB = verticalSlab("persimmon_roofing_vertical_slab", GotModBlocks.PERSIMMON_ROOFING_SLAB);
    public static final DeferredBlock<Block> PERSIMMON_VERTICAL_SLAB = verticalSlab("persimmon_vertical_slab", GotModBlocks.PERSIMMON_SLAB);
    public static final DeferredBlock<Block> PERSIMMON_WOOD_VERTICAL_SLAB = verticalSlab("persimmon_wood_vertical_slab", GotModBlocks.PERSIMMON_WOOD_SLAB);
    public static final DeferredBlock<Block> PINE_ROOFING_VERTICAL_SLAB = verticalSlab("pine_roofing_vertical_slab", GotModBlocks.PINE_ROOFING_SLAB);
    public static final DeferredBlock<Block> PINE_VERTICAL_SLAB = verticalSlab("pine_vertical_slab", GotModBlocks.PINE_SLAB);
    public static final DeferredBlock<Block> PINE_WOOD_VERTICAL_SLAB = verticalSlab("pine_wood_vertical_slab", GotModBlocks.PINE_WOOD_SLAB);
    public static final DeferredBlock<Block> PINK_IVORY_ROOFING_VERTICAL_SLAB = verticalSlab("pink_ivory_roofing_vertical_slab", GotModBlocks.PINK_IVORY_ROOFING_SLAB);
    public static final DeferredBlock<Block> PINK_IVORY_VERTICAL_SLAB = verticalSlab("pink_ivory_vertical_slab", GotModBlocks.PINK_IVORY_SLAB);
    public static final DeferredBlock<Block> PINK_IVORY_WOOD_VERTICAL_SLAB = verticalSlab("pink_ivory_wood_vertical_slab", GotModBlocks.PINK_IVORY_WOOD_SLAB);
    public static final DeferredBlock<Block> PINK_WOOL_VERTICAL_SLAB = verticalSlab("pink_wool_vertical_slab", GotModBlocks.PINK_WOOL_SLAB);
    public static final DeferredBlock<Block> PLUM_ROOFING_VERTICAL_SLAB = verticalSlab("plum_roofing_vertical_slab", GotModBlocks.PLUM_ROOFING_SLAB);
    public static final DeferredBlock<Block> PLUM_VERTICAL_SLAB = verticalSlab("plum_vertical_slab", GotModBlocks.PLUM_SLAB);
    public static final DeferredBlock<Block> PLUM_WOOD_VERTICAL_SLAB = verticalSlab("plum_wood_vertical_slab", GotModBlocks.PLUM_WOOD_SLAB);
    public static final DeferredBlock<Block> PODZOL_VERTICAL_SLAB = verticalSlab("podzol_vertical_slab", GotModBlocks.PODZOL_SLAB);
    public static final DeferredBlock<Block> POMEGRANATE_ROOFING_VERTICAL_SLAB = verticalSlab("pomegranate_roofing_vertical_slab", GotModBlocks.POMEGRANATE_ROOFING_SLAB);
    public static final DeferredBlock<Block> POMEGRANATE_VERTICAL_SLAB = verticalSlab("pomegranate_vertical_slab", GotModBlocks.POMEGRANATE_SLAB);
    public static final DeferredBlock<Block> POMEGRANATE_WOOD_VERTICAL_SLAB = verticalSlab("pomegranate_wood_vertical_slab", GotModBlocks.POMEGRANATE_WOOD_SLAB);
    public static final DeferredBlock<Block> PRUNE_ROOFING_VERTICAL_SLAB = verticalSlab("prune_roofing_vertical_slab", GotModBlocks.PRUNE_ROOFING_SLAB);
    public static final DeferredBlock<Block> PRUNE_VERTICAL_SLAB = verticalSlab("prune_vertical_slab", GotModBlocks.PRUNE_SLAB);
    public static final DeferredBlock<Block> PRUNE_WOOD_VERTICAL_SLAB = verticalSlab("prune_wood_vertical_slab", GotModBlocks.PRUNE_WOOD_SLAB);
    public static final DeferredBlock<Block> PURPLE_WOOL_VERTICAL_SLAB = verticalSlab("purple_wool_vertical_slab", GotModBlocks.PURPLE_WOOL_SLAB);
    public static final DeferredBlock<Block> PURPLEHEART_ROOFING_VERTICAL_SLAB = verticalSlab("purpleheart_roofing_vertical_slab", GotModBlocks.PURPLEHEART_ROOFING_SLAB);
    public static final DeferredBlock<Block> PURPLEHEART_VERTICAL_SLAB = verticalSlab("purpleheart_vertical_slab", GotModBlocks.PURPLEHEART_SLAB);
    public static final DeferredBlock<Block> PURPLEHEART_WOOD_VERTICAL_SLAB = verticalSlab("purpleheart_wood_vertical_slab", GotModBlocks.PURPLEHEART_WOOD_SLAB);
    public static final DeferredBlock<Block> RED_CHERRY_ROOFING_VERTICAL_SLAB = verticalSlab("red_cherry_roofing_vertical_slab", GotModBlocks.RED_CHERRY_ROOFING_SLAB);
    public static final DeferredBlock<Block> RED_CHERRY_VERTICAL_SLAB = verticalSlab("red_cherry_vertical_slab", GotModBlocks.RED_CHERRY_SLAB);
    public static final DeferredBlock<Block> RED_CHERRY_WOOD_VERTICAL_SLAB = verticalSlab("red_cherry_wood_vertical_slab", GotModBlocks.RED_CHERRY_WOOD_SLAB);
    public static final DeferredBlock<Block> RED_SANDSTONE_BRICK_VERTICAL_SLAB = verticalSlab("red_sandstone_brick_vertical_slab", GotModBlocks.RED_SANDSTONE_BRICK_SLAB);
    public static final DeferredBlock<Block> RED_SANDSTONE_COBBLESTONE_VERTICAL_SLAB = verticalSlab("red_sandstone_cobblestone_vertical_slab", GotModBlocks.RED_SANDSTONE_COBBLESTONE_SLAB);
    public static final DeferredBlock<Block> RED_WOOL_VERTICAL_SLAB = verticalSlab("red_wool_vertical_slab", GotModBlocks.RED_WOOL_SLAB);
    public static final DeferredBlock<Block> REDWOOD_ROOFING_VERTICAL_SLAB = verticalSlab("redwood_roofing_vertical_slab", GotModBlocks.REDWOOD_ROOFING_SLAB);
    public static final DeferredBlock<Block> REDWOOD_VERTICAL_SLAB = verticalSlab("redwood_vertical_slab", GotModBlocks.REDWOOD_SLAB);
    public static final DeferredBlock<Block> REDWOOD_WOOD_VERTICAL_SLAB = verticalSlab("redwood_wood_vertical_slab", GotModBlocks.REDWOOD_WOOD_SLAB);
    public static final DeferredBlock<Block> ROOTED_DIRT_VERTICAL_SLAB = verticalSlab("rooted_dirt_vertical_slab", GotModBlocks.ROOTED_DIRT_SLAB);
    public static final DeferredBlock<Block> SANDALWOOD_ROOFING_VERTICAL_SLAB = verticalSlab("sandalwood_roofing_vertical_slab", GotModBlocks.SANDALWOOD_ROOFING_SLAB);
    public static final DeferredBlock<Block> SANDALWOOD_VERTICAL_SLAB = verticalSlab("sandalwood_vertical_slab", GotModBlocks.SANDALWOOD_SLAB);
    public static final DeferredBlock<Block> SANDALWOOD_WOOD_VERTICAL_SLAB = verticalSlab("sandalwood_wood_vertical_slab", GotModBlocks.SANDALWOOD_WOOD_SLAB);
    public static final DeferredBlock<Block> SANDBEGGAR_ROOFING_VERTICAL_SLAB = verticalSlab("sandbeggar_roofing_vertical_slab", GotModBlocks.SANDBEGGAR_ROOFING_SLAB);
    public static final DeferredBlock<Block> SANDBEGGAR_VERTICAL_SLAB = verticalSlab("sandbeggar_vertical_slab", GotModBlocks.SANDBEGGAR_SLAB);
    public static final DeferredBlock<Block> SANDBEGGAR_WOOD_VERTICAL_SLAB = verticalSlab("sandbeggar_wood_vertical_slab", GotModBlocks.SANDBEGGAR_WOOD_SLAB);
    public static final DeferredBlock<Block> SANDSTONE_BRICK_VERTICAL_SLAB = verticalSlab("sandstone_brick_vertical_slab", GotModBlocks.SANDSTONE_BRICK_SLAB);
    public static final DeferredBlock<Block> SANDSTONE_COBBLESTONE_VERTICAL_SLAB = verticalSlab("sandstone_cobblestone_vertical_slab", GotModBlocks.SANDSTONE_COBBLESTONE_SLAB);
    public static final DeferredBlock<Block> SENTINAL_ROOFING_VERTICAL_SLAB = verticalSlab("sentinal_roofing_vertical_slab", GotModBlocks.SENTINAL_ROOFING_SLAB);
    public static final DeferredBlock<Block> SENTINAL_VERTICAL_SLAB = verticalSlab("sentinal_vertical_slab", GotModBlocks.SENTINAL_SLAB);
    public static final DeferredBlock<Block> SENTINAL_WOOD_VERTICAL_SLAB = verticalSlab("sentinal_wood_vertical_slab", GotModBlocks.SENTINAL_WOOD_SLAB);
    public static final DeferredBlock<Block> SLATE_BRICK_VERTICAL_SLAB = verticalSlab("slate_brick_vertical_slab", GotModBlocks.SLATE_BRICK_SLAB);
    public static final DeferredBlock<Block> SLATE_COBBLESTONE_VERTICAL_SLAB = verticalSlab("slate_cobblestone_vertical_slab", GotModBlocks.SLATE_COBBLESTONE_SLAB);
    public static final DeferredBlock<Block> SLATE_ROCK_VERTICAL_SLAB = verticalSlab("slate_rock_vertical_slab", GotModBlocks.SLATE_ROCK_SLAB);
    public static final DeferredBlock<Block> SLATE_SHINGLES_VERTICAL_SLAB = verticalSlab("slate_shingles_vertical_slab", GotModBlocks.SLATE_SHINGLES_SLAB);
    public static final DeferredBlock<Block> SMOOTH_BASALT_ROCK_VERTICAL_SLAB = verticalSlab("smooth_basalt_rock_vertical_slab", GotModBlocks.SMOOTH_BASALT_ROCK_SLAB);
    public static final DeferredBlock<Block> SMOOTH_FUSED_BLACK_ROCK_VERTICAL_SLAB = verticalSlab("smooth_fused_black_rock_vertical_slab", GotModBlocks.SMOOTH_FUSED_BLACK_ROCK_SLAB);
    public static final DeferredBlock<Block> SMOOTH_GREY_GRANITE_ROCK_VERTICAL_SLAB = verticalSlab("smooth_grey_granite_rock_vertical_slab", GotModBlocks.SMOOTH_GREY_GRANITE_ROCK_SLAB);
    public static final DeferredBlock<Block> SMOOTH_LIMESTONE_ROCK_VERTICAL_SLAB = verticalSlab("smooth_limestone_rock_vertical_slab", GotModBlocks.SMOOTH_LIMESTONE_ROCK_SLAB);
    public static final DeferredBlock<Block> SMOOTH_MARBLE_ROCK_VERTICAL_SLAB = verticalSlab("smooth_marble_rock_vertical_slab", GotModBlocks.SMOOTH_MARBLE_ROCK_SLAB);
    public static final DeferredBlock<Block> SMOOTH_OILY_BLACK_ROCK_VERTICAL_SLAB = verticalSlab("smooth_oily_black_rock_vertical_slab", GotModBlocks.SMOOTH_OILY_BLACK_ROCK_SLAB);
    public static final DeferredBlock<Block> SMOOTH_SLATE_ROCK_VERTICAL_SLAB = verticalSlab("smooth_slate_rock_vertical_slab", GotModBlocks.SMOOTH_SLATE_ROCK_SLAB);
    public static final DeferredBlock<Block> SOLDIER_PINE_ROOFING_VERTICAL_SLAB = verticalSlab("soldier_pine_roofing_vertical_slab", GotModBlocks.SOLDIER_PINE_ROOFING_SLAB);
    public static final DeferredBlock<Block> SOLDIER_PINE_VERTICAL_SLAB = verticalSlab("soldier_pine_vertical_slab", GotModBlocks.SOLDIER_PINE_SLAB);
    public static final DeferredBlock<Block> SOLDIER_PINE_WOOD_VERTICAL_SLAB = verticalSlab("soldier_pine_wood_vertical_slab", GotModBlocks.SOLDIER_PINE_WOOD_SLAB);
    public static final DeferredBlock<Block> SPRUCE_ROOFING_VERTICAL_SLAB = verticalSlab("spruce_roofing_vertical_slab", GotModBlocks.SPRUCE_ROOFING_SLAB);
    public static final DeferredBlock<Block> SPRUCE_WOOD_VERTICAL_SLAB = verticalSlab("spruce_wood_vertical_slab", GotModBlocks.SPRUCE_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_ACACIA_WOOD_VERTICAL_SLAB = verticalSlab("stripped_acacia_wood_vertical_slab", GotModBlocks.STRIPPED_ACACIA_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_ALDER_WOOD_VERTICAL_SLAB = verticalSlab("stripped_alder_wood_vertical_slab", GotModBlocks.STRIPPED_ALDER_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_ALMOND_WOOD_VERTICAL_SLAB = verticalSlab("stripped_almond_wood_vertical_slab", GotModBlocks.STRIPPED_ALMOND_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_APPLE_WOOD_VERTICAL_SLAB = verticalSlab("stripped_apple_wood_vertical_slab", GotModBlocks.STRIPPED_APPLE_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_APRICOT_WOOD_VERTICAL_SLAB = verticalSlab("stripped_apricot_wood_vertical_slab", GotModBlocks.STRIPPED_APRICOT_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_ASH_WOOD_VERTICAL_SLAB = verticalSlab("stripped_ash_wood_vertical_slab", GotModBlocks.STRIPPED_ASH_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_ASPEN_WOOD_VERTICAL_SLAB = verticalSlab("stripped_aspen_wood_vertical_slab", GotModBlocks.STRIPPED_ASPEN_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_BEECH_WOOD_VERTICAL_SLAB = verticalSlab("stripped_beech_wood_vertical_slab", GotModBlocks.STRIPPED_BEECH_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_BIRCH_WOOD_VERTICAL_SLAB = verticalSlab("stripped_birch_wood_vertical_slab", GotModBlocks.STRIPPED_BIRCH_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_BLACK_CHERRY_WOOD_VERTICAL_SLAB = verticalSlab("stripped_black_cherry_wood_vertical_slab", GotModBlocks.STRIPPED_BLACK_CHERRY_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_BLACK_COTTONWOOD_WOOD_VERTICAL_SLAB = verticalSlab("stripped_black_cottonwood_wood_vertical_slab", GotModBlocks.STRIPPED_BLACK_COTTONWOOD_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_BLACKBARK_WOOD_VERTICAL_SLAB = verticalSlab("stripped_blackbark_wood_vertical_slab", GotModBlocks.STRIPPED_BLACKBARK_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_BLACKTHORN_WOOD_VERTICAL_SLAB = verticalSlab("stripped_blackthorn_wood_vertical_slab", GotModBlocks.STRIPPED_BLACKTHORN_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_BLOODWOOD_WOOD_VERTICAL_SLAB = verticalSlab("stripped_bloodwood_wood_vertical_slab", GotModBlocks.STRIPPED_BLOODWOOD_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_BLUE_MAHOE_WOOD_VERTICAL_SLAB = verticalSlab("stripped_blue_mahoe_wood_vertical_slab", GotModBlocks.STRIPPED_BLUE_MAHOE_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_BURL_WOOD_VERTICAL_SLAB = verticalSlab("stripped_burl_wood_vertical_slab", GotModBlocks.STRIPPED_BURL_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_CEDAR_WOOD_VERTICAL_SLAB = verticalSlab("stripped_cedar_wood_vertical_slab", GotModBlocks.STRIPPED_CEDAR_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_CHERRY_WOOD_VERTICAL_SLAB = verticalSlab("stripped_cherry_wood_vertical_slab", GotModBlocks.STRIPPED_CHERRY_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_CHESTNUT_WOOD_VERTICAL_SLAB = verticalSlab("stripped_chestnut_wood_vertical_slab", GotModBlocks.STRIPPED_CHESTNUT_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_CINNAMON_WOOD_VERTICAL_SLAB = verticalSlab("stripped_cinnamon_wood_vertical_slab", GotModBlocks.STRIPPED_CINNAMON_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_CLOVE_WOOD_VERTICAL_SLAB = verticalSlab("stripped_clove_wood_vertical_slab", GotModBlocks.STRIPPED_CLOVE_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_COTTONWOOD_WOOD_VERTICAL_SLAB = verticalSlab("stripped_cottonwood_wood_vertical_slab", GotModBlocks.STRIPPED_COTTONWOOD_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_CRABAPPLE_WOOD_VERTICAL_SLAB = verticalSlab("stripped_crabapple_wood_vertical_slab", GotModBlocks.STRIPPED_CRABAPPLE_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_DARK_OAK_WOOD_VERTICAL_SLAB = verticalSlab("stripped_dark_oak_wood_vertical_slab", GotModBlocks.STRIPPED_DARK_OAK_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_DATE_PALM_WOOD_VERTICAL_SLAB = verticalSlab("stripped_date_palm_wood_vertical_slab", GotModBlocks.STRIPPED_DATE_PALM_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_EBONY_WOOD_VERTICAL_SLAB = verticalSlab("stripped_ebony_wood_vertical_slab", GotModBlocks.STRIPPED_EBONY_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_ELM_WOOD_VERTICAL_SLAB = verticalSlab("stripped_elm_wood_vertical_slab", GotModBlocks.STRIPPED_ELM_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_FIG_WOOD_VERTICAL_SLAB = verticalSlab("stripped_fig_wood_vertical_slab", GotModBlocks.STRIPPED_FIG_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_FIR_WOOD_VERTICAL_SLAB = verticalSlab("stripped_fir_wood_vertical_slab", GotModBlocks.STRIPPED_FIR_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_GOLDENHEART_WOOD_VERTICAL_SLAB = verticalSlab("stripped_goldenheart_wood_vertical_slab", GotModBlocks.STRIPPED_GOLDENHEART_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_HAWTHORN_WOOD_VERTICAL_SLAB = verticalSlab("stripped_hawthorn_wood_vertical_slab", GotModBlocks.STRIPPED_HAWTHORN_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_HEMLOCK_WOOD_VERTICAL_SLAB = verticalSlab("stripped_hemlock_wood_vertical_slab", GotModBlocks.STRIPPED_HEMLOCK_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_IRONWOOD_WOOD_VERTICAL_SLAB = verticalSlab("stripped_ironwood_wood_vertical_slab", GotModBlocks.STRIPPED_IRONWOOD_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_JUNGLE_WOOD_VERTICAL_SLAB = verticalSlab("stripped_jungle_wood_vertical_slab", GotModBlocks.STRIPPED_JUNGLE_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_LEMON_WOOD_VERTICAL_SLAB = verticalSlab("stripped_lemon_wood_vertical_slab", GotModBlocks.STRIPPED_LEMON_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_LIME_WOOD_VERTICAL_SLAB = verticalSlab("stripped_lime_wood_vertical_slab", GotModBlocks.STRIPPED_LIME_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_LINDEN_WOOD_VERTICAL_SLAB = verticalSlab("stripped_linden_wood_vertical_slab", GotModBlocks.STRIPPED_LINDEN_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_MAHOGANY_WOOD_VERTICAL_SLAB = verticalSlab("stripped_mahogany_wood_vertical_slab", GotModBlocks.STRIPPED_MAHOGANY_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_MANGROVE_WOOD_VERTICAL_SLAB = verticalSlab("stripped_mangrove_wood_vertical_slab", GotModBlocks.STRIPPED_MANGROVE_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_MAPLE_WOOD_VERTICAL_SLAB = verticalSlab("stripped_maple_wood_vertical_slab", GotModBlocks.STRIPPED_MAPLE_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_MYRRH_WOOD_VERTICAL_SLAB = verticalSlab("stripped_myrrh_wood_vertical_slab", GotModBlocks.STRIPPED_MYRRH_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_NIGHTWOOD_WOOD_VERTICAL_SLAB = verticalSlab("stripped_nightwood_wood_vertical_slab", GotModBlocks.STRIPPED_NIGHTWOOD_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_NUTMEG_WOOD_VERTICAL_SLAB = verticalSlab("stripped_nutmeg_wood_vertical_slab", GotModBlocks.STRIPPED_NUTMEG_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_OAK_WOOD_VERTICAL_SLAB = verticalSlab("stripped_oak_wood_vertical_slab", GotModBlocks.STRIPPED_OAK_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_OLIVE_WOOD_VERTICAL_SLAB = verticalSlab("stripped_olive_wood_vertical_slab", GotModBlocks.STRIPPED_OLIVE_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_ORANGE_WOOD_VERTICAL_SLAB = verticalSlab("stripped_orange_wood_vertical_slab", GotModBlocks.STRIPPED_ORANGE_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_PALE_OAK_WOOD_VERTICAL_SLAB = verticalSlab("stripped_pale_oak_wood_vertical_slab", GotModBlocks.STRIPPED_PALE_OAK_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_PEACH_WOOD_VERTICAL_SLAB = verticalSlab("stripped_peach_wood_vertical_slab", GotModBlocks.STRIPPED_PEACH_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_PEAR_WOOD_VERTICAL_SLAB = verticalSlab("stripped_pear_wood_vertical_slab", GotModBlocks.STRIPPED_PEAR_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_PERSIMMON_WOOD_VERTICAL_SLAB = verticalSlab("stripped_persimmon_wood_vertical_slab", GotModBlocks.STRIPPED_PERSIMMON_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_PINE_WOOD_VERTICAL_SLAB = verticalSlab("stripped_pine_wood_vertical_slab", GotModBlocks.STRIPPED_PINE_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_PINK_IVORY_WOOD_VERTICAL_SLAB = verticalSlab("stripped_pink_ivory_wood_vertical_slab", GotModBlocks.STRIPPED_PINK_IVORY_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_PLUM_WOOD_VERTICAL_SLAB = verticalSlab("stripped_plum_wood_vertical_slab", GotModBlocks.STRIPPED_PLUM_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_POMEGRANATE_WOOD_VERTICAL_SLAB = verticalSlab("stripped_pomegranate_wood_vertical_slab", GotModBlocks.STRIPPED_POMEGRANATE_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_PRUNE_WOOD_VERTICAL_SLAB = verticalSlab("stripped_prune_wood_vertical_slab", GotModBlocks.STRIPPED_PRUNE_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_PURPLEHEART_WOOD_VERTICAL_SLAB = verticalSlab("stripped_purpleheart_wood_vertical_slab", GotModBlocks.STRIPPED_PURPLEHEART_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_RED_CHERRY_WOOD_VERTICAL_SLAB = verticalSlab("stripped_red_cherry_wood_vertical_slab", GotModBlocks.STRIPPED_RED_CHERRY_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_REDWOOD_WOOD_VERTICAL_SLAB = verticalSlab("stripped_redwood_wood_vertical_slab", GotModBlocks.STRIPPED_REDWOOD_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_SANDALWOOD_WOOD_VERTICAL_SLAB = verticalSlab("stripped_sandalwood_wood_vertical_slab", GotModBlocks.STRIPPED_SANDALWOOD_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_SANDBEGGAR_WOOD_VERTICAL_SLAB = verticalSlab("stripped_sandbeggar_wood_vertical_slab", GotModBlocks.STRIPPED_SANDBEGGAR_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_SENTINAL_WOOD_VERTICAL_SLAB = verticalSlab("stripped_sentinal_wood_vertical_slab", GotModBlocks.STRIPPED_SENTINAL_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_SOLDIER_PINE_WOOD_VERTICAL_SLAB = verticalSlab("stripped_soldier_pine_wood_vertical_slab", GotModBlocks.STRIPPED_SOLDIER_PINE_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_SPRUCE_WOOD_VERTICAL_SLAB = verticalSlab("stripped_spruce_wood_vertical_slab", GotModBlocks.STRIPPED_SPRUCE_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_TIGERWOOD_WOOD_VERTICAL_SLAB = verticalSlab("stripped_tigerwood_wood_vertical_slab", GotModBlocks.STRIPPED_TIGERWOOD_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_WEIRWOOD_WOOD_VERTICAL_SLAB = verticalSlab("stripped_weirwood_wood_vertical_slab", GotModBlocks.STRIPPED_WEIRWOOD_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_WHITE_CHERRY_WOOD_VERTICAL_SLAB = verticalSlab("stripped_white_cherry_wood_vertical_slab", GotModBlocks.STRIPPED_WHITE_CHERRY_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_WILLOW_WOOD_VERTICAL_SLAB = verticalSlab("stripped_willow_wood_vertical_slab", GotModBlocks.STRIPPED_WILLOW_WOOD_SLAB);
    public static final DeferredBlock<Block> STRIPPED_WORMTREE_WOOD_VERTICAL_SLAB = verticalSlab("stripped_wormtree_wood_vertical_slab", GotModBlocks.STRIPPED_WORMTREE_WOOD_SLAB);
    public static final DeferredBlock<Block> TIGERWOOD_ROOFING_VERTICAL_SLAB = verticalSlab("tigerwood_roofing_vertical_slab", GotModBlocks.TIGERWOOD_ROOFING_SLAB);
    public static final DeferredBlock<Block> TIGERWOOD_VERTICAL_SLAB = verticalSlab("tigerwood_vertical_slab", GotModBlocks.TIGERWOOD_SLAB);
    public static final DeferredBlock<Block> TIGERWOOD_WOOD_VERTICAL_SLAB = verticalSlab("tigerwood_wood_vertical_slab", GotModBlocks.TIGERWOOD_WOOD_SLAB);
    public static final DeferredBlock<Block> WEIRWOOD_ROOFING_VERTICAL_SLAB = verticalSlab("weirwood_roofing_vertical_slab", GotModBlocks.WEIRWOOD_ROOFING_SLAB);
    public static final DeferredBlock<Block> WEIRWOOD_VERTICAL_SLAB = verticalSlab("weirwood_vertical_slab", GotModBlocks.WEIRWOOD_SLAB);
    public static final DeferredBlock<Block> WEIRWOOD_WOOD_VERTICAL_SLAB = verticalSlab("weirwood_wood_vertical_slab", GotModBlocks.WEIRWOOD_WOOD_SLAB);
    public static final DeferredBlock<Block> WHITE_CHERRY_ROOFING_VERTICAL_SLAB = verticalSlab("white_cherry_roofing_vertical_slab", GotModBlocks.WHITE_CHERRY_ROOFING_SLAB);
    public static final DeferredBlock<Block> WHITE_CHERRY_VERTICAL_SLAB = verticalSlab("white_cherry_vertical_slab", GotModBlocks.WHITE_CHERRY_SLAB);
    public static final DeferredBlock<Block> WHITE_CHERRY_WOOD_VERTICAL_SLAB = verticalSlab("white_cherry_wood_vertical_slab", GotModBlocks.WHITE_CHERRY_WOOD_SLAB);
    public static final DeferredBlock<Block> WHITE_WOOL_VERTICAL_SLAB = verticalSlab("white_wool_vertical_slab", GotModBlocks.WHITE_WOOL_SLAB);
    public static final DeferredBlock<Block> WILLOW_ROOFING_VERTICAL_SLAB = verticalSlab("willow_roofing_vertical_slab", GotModBlocks.WILLOW_ROOFING_SLAB);
    public static final DeferredBlock<Block> WILLOW_VERTICAL_SLAB = verticalSlab("willow_vertical_slab", GotModBlocks.WILLOW_SLAB);
    public static final DeferredBlock<Block> WILLOW_WOOD_VERTICAL_SLAB = verticalSlab("willow_wood_vertical_slab", GotModBlocks.WILLOW_WOOD_SLAB);
    public static final DeferredBlock<Block> WORMTREE_ROOFING_VERTICAL_SLAB = verticalSlab("wormtree_roofing_vertical_slab", GotModBlocks.WORMTREE_ROOFING_SLAB);
    public static final DeferredBlock<Block> WORMTREE_VERTICAL_SLAB = verticalSlab("wormtree_vertical_slab", GotModBlocks.WORMTREE_SLAB);
    public static final DeferredBlock<Block> WORMTREE_WOOD_VERTICAL_SLAB = verticalSlab("wormtree_wood_vertical_slab", GotModBlocks.WORMTREE_WOOD_SLAB);
    public static final DeferredBlock<Block> YELLOW_WOOL_VERTICAL_SLAB = verticalSlab("yellow_wool_vertical_slab", GotModBlocks.YELLOW_WOOL_SLAB);
    public static final DeferredBlock<Block> OAK_VERTICAL_SLAB = verticalSlab("oak_vertical_slab", Blocks.OAK_SLAB);
    public static final DeferredBlock<Block> SPRUCE_VERTICAL_SLAB = verticalSlab("spruce_vertical_slab", Blocks.SPRUCE_SLAB);
    public static final DeferredBlock<Block> BIRCH_VERTICAL_SLAB = verticalSlab("birch_vertical_slab", Blocks.BIRCH_SLAB);
    public static final DeferredBlock<Block> JUNGLE_VERTICAL_SLAB = verticalSlab("jungle_vertical_slab", Blocks.JUNGLE_SLAB);
    public static final DeferredBlock<Block> ACACIA_VERTICAL_SLAB = verticalSlab("acacia_vertical_slab", Blocks.ACACIA_SLAB);
    public static final DeferredBlock<Block> DARK_OAK_VERTICAL_SLAB = verticalSlab("dark_oak_vertical_slab", Blocks.DARK_OAK_SLAB);
    public static final DeferredBlock<Block> MANGROVE_VERTICAL_SLAB = verticalSlab("mangrove_vertical_slab", Blocks.MANGROVE_SLAB);
    public static final DeferredBlock<Block> CHERRY_VERTICAL_SLAB = verticalSlab("cherry_vertical_slab", Blocks.CHERRY_SLAB);
    public static final DeferredBlock<Block> BAMBOO_VERTICAL_SLAB = verticalSlab("bamboo_vertical_slab", Blocks.BAMBOO_SLAB);
    public static final DeferredBlock<Block> BAMBOO_MOSAIC_VERTICAL_SLAB = verticalSlab("bamboo_mosaic_vertical_slab", Blocks.BAMBOO_MOSAIC_SLAB);
    public static final DeferredBlock<Block> CRIMSON_VERTICAL_SLAB = verticalSlab("crimson_vertical_slab", Blocks.CRIMSON_SLAB);
    public static final DeferredBlock<Block> WARPED_VERTICAL_SLAB = verticalSlab("warped_vertical_slab", Blocks.WARPED_SLAB);
    public static final DeferredBlock<Block> PALE_OAK_VERTICAL_SLAB = verticalSlab("pale_oak_vertical_slab", Blocks.PALE_OAK_SLAB);
    public static final DeferredBlock<Block> PETRIFIED_OAK_VERTICAL_SLAB = verticalSlab("petrified_oak_vertical_slab", Blocks.PETRIFIED_OAK_SLAB);
    public static final DeferredBlock<Block> STONE_VERTICAL_SLAB = verticalSlab("stone_vertical_slab", Blocks.STONE_SLAB);
    public static final DeferredBlock<Block> SMOOTH_STONE_VERTICAL_SLAB = verticalSlab("smooth_stone_vertical_slab", Blocks.SMOOTH_STONE_SLAB);
    public static final DeferredBlock<Block> COBBLESTONE_VERTICAL_SLAB = verticalSlab("cobblestone_vertical_slab", Blocks.COBBLESTONE_SLAB);
    public static final DeferredBlock<Block> MOSSY_COBBLESTONE_VERTICAL_SLAB = verticalSlab("mossy_cobblestone_vertical_slab", Blocks.MOSSY_COBBLESTONE_SLAB);
    public static final DeferredBlock<Block> GRANITE_VERTICAL_SLAB = verticalSlab("granite_vertical_slab", Blocks.GRANITE_SLAB);
    public static final DeferredBlock<Block> POLISHED_GRANITE_VERTICAL_SLAB = verticalSlab("polished_granite_vertical_slab", Blocks.POLISHED_GRANITE_SLAB);
    public static final DeferredBlock<Block> DIORITE_VERTICAL_SLAB = verticalSlab("diorite_vertical_slab", Blocks.DIORITE_SLAB);
    public static final DeferredBlock<Block> POLISHED_DIORITE_VERTICAL_SLAB = verticalSlab("polished_diorite_vertical_slab", Blocks.POLISHED_DIORITE_SLAB);
    public static final DeferredBlock<Block> ANDESITE_VERTICAL_SLAB = verticalSlab("andesite_vertical_slab", Blocks.ANDESITE_SLAB);
    public static final DeferredBlock<Block> POLISHED_ANDESITE_VERTICAL_SLAB = verticalSlab("polished_andesite_vertical_slab", Blocks.POLISHED_ANDESITE_SLAB);
    public static final DeferredBlock<Block> BRICK_VERTICAL_SLAB = verticalSlab("brick_vertical_slab", Blocks.BRICK_SLAB);
    public static final DeferredBlock<Block> MUD_BRICK_VERTICAL_SLAB = verticalSlab("mud_brick_vertical_slab", Blocks.MUD_BRICK_SLAB);
    public static final DeferredBlock<Block> NETHER_BRICK_VERTICAL_SLAB = verticalSlab("nether_brick_vertical_slab", Blocks.NETHER_BRICK_SLAB);
    public static final DeferredBlock<Block> RED_NETHER_BRICK_VERTICAL_SLAB = verticalSlab("red_nether_brick_vertical_slab", Blocks.RED_NETHER_BRICK_SLAB);
    public static final DeferredBlock<Block> STONE_BRICK_VERTICAL_SLAB = verticalSlab("stone_brick_vertical_slab", Blocks.STONE_BRICK_SLAB);
    public static final DeferredBlock<Block> MOSSY_STONE_BRICK_VERTICAL_SLAB = verticalSlab("mossy_stone_brick_vertical_slab", Blocks.MOSSY_STONE_BRICK_SLAB);
    public static final DeferredBlock<Block> END_STONE_BRICK_VERTICAL_SLAB = verticalSlab("end_stone_brick_vertical_slab", Blocks.END_STONE_BRICK_SLAB);
    public static final DeferredBlock<Block> PRISMARINE_VERTICAL_SLAB = verticalSlab("prismarine_vertical_slab", Blocks.PRISMARINE_SLAB);
    public static final DeferredBlock<Block> PRISMARINE_BRICK_VERTICAL_SLAB = verticalSlab("prismarine_brick_vertical_slab", Blocks.PRISMARINE_BRICK_SLAB);
    public static final DeferredBlock<Block> DARK_PRISMARINE_VERTICAL_SLAB = verticalSlab("dark_prismarine_vertical_slab", Blocks.DARK_PRISMARINE_SLAB);
    public static final DeferredBlock<Block> PURPUR_VERTICAL_SLAB = verticalSlab("purpur_vertical_slab", Blocks.PURPUR_SLAB);
    public static final DeferredBlock<Block> BLACKSTONE_VERTICAL_SLAB = verticalSlab("blackstone_vertical_slab", Blocks.BLACKSTONE_SLAB);
    public static final DeferredBlock<Block> POLISHED_BLACKSTONE_VERTICAL_SLAB = verticalSlab("polished_blackstone_vertical_slab", Blocks.POLISHED_BLACKSTONE_SLAB);
    public static final DeferredBlock<Block> POLISHED_BLACKSTONE_BRICK_VERTICAL_SLAB = verticalSlab("polished_blackstone_brick_vertical_slab", Blocks.POLISHED_BLACKSTONE_BRICK_SLAB);
    public static final DeferredBlock<Block> COBBLED_DEEPSLATE_VERTICAL_SLAB = verticalSlab("cobbled_deepslate_vertical_slab", Blocks.COBBLED_DEEPSLATE_SLAB);
    public static final DeferredBlock<Block> POLISHED_DEEPSLATE_VERTICAL_SLAB = verticalSlab("polished_deepslate_vertical_slab", Blocks.POLISHED_DEEPSLATE_SLAB);
    public static final DeferredBlock<Block> DEEPSLATE_BRICK_VERTICAL_SLAB = verticalSlab("deepslate_brick_vertical_slab", Blocks.DEEPSLATE_BRICK_SLAB);
    public static final DeferredBlock<Block> DEEPSLATE_TILE_VERTICAL_SLAB = verticalSlab("deepslate_tile_vertical_slab", Blocks.DEEPSLATE_TILE_SLAB);
    public static final DeferredBlock<Block> TUFF_VERTICAL_SLAB = verticalSlab("tuff_vertical_slab", Blocks.TUFF_SLAB);
    public static final DeferredBlock<Block> POLISHED_TUFF_VERTICAL_SLAB = verticalSlab("polished_tuff_vertical_slab", Blocks.POLISHED_TUFF_SLAB);
    public static final DeferredBlock<Block> TUFF_BRICK_VERTICAL_SLAB = verticalSlab("tuff_brick_vertical_slab", Blocks.TUFF_BRICK_SLAB);
    public static final DeferredBlock<Block> RESIN_BRICK_VERTICAL_SLAB = verticalSlab("resin_brick_vertical_slab", Blocks.RESIN_BRICK_SLAB);
    public static final DeferredBlock<Block> SANDSTONE_VERTICAL_SLAB = verticalSlab("sandstone_vertical_slab", Blocks.SANDSTONE_SLAB);
    public static final DeferredBlock<Block> SMOOTH_SANDSTONE_VERTICAL_SLAB = verticalSlab("smooth_sandstone_vertical_slab", Blocks.SMOOTH_SANDSTONE_SLAB);
    public static final DeferredBlock<Block> CUT_SANDSTONE_VERTICAL_SLAB = verticalSlab("cut_sandstone_vertical_slab", Blocks.CUT_SANDSTONE_SLAB);
    public static final DeferredBlock<Block> RED_SANDSTONE_VERTICAL_SLAB = verticalSlab("red_sandstone_vertical_slab", Blocks.RED_SANDSTONE_SLAB);
    public static final DeferredBlock<Block> SMOOTH_RED_SANDSTONE_VERTICAL_SLAB = verticalSlab("smooth_red_sandstone_vertical_slab", Blocks.SMOOTH_RED_SANDSTONE_SLAB);
    public static final DeferredBlock<Block> CUT_RED_SANDSTONE_VERTICAL_SLAB = verticalSlab("cut_red_sandstone_vertical_slab", Blocks.CUT_RED_SANDSTONE_SLAB);
    public static final DeferredBlock<Block> QUARTZ_VERTICAL_SLAB = verticalSlab("quartz_vertical_slab", Blocks.QUARTZ_SLAB);
    public static final DeferredBlock<Block> SMOOTH_QUARTZ_VERTICAL_SLAB = verticalSlab("smooth_quartz_vertical_slab", Blocks.SMOOTH_QUARTZ_SLAB);

    static {
        LOGS.put("weirwood",         WEIRWOOD_LOG);
        LOGS.put("aspen",            ASPEN_LOG);
        LOGS.put("alder",            ALDER_LOG);
        LOGS.put("pine",             PINE_LOG);
        LOGS.put("fir",              FIR_LOG);
        LOGS.put("sentinal",         SENTINAL_LOG);
        LOGS.put("ironwood",         IRONWOOD_LOG);
        LOGS.put("beech",            BEECH_LOG);
        LOGS.put("soldier_pine",     SOLDIER_PINE_LOG);
        LOGS.put("ash",              ASH_LOG);
        LOGS.put("hawthorn",         HAWTHORN_LOG);
        LOGS.put("blackbark",        BLACKBARK_LOG);
        LOGS.put("bloodwood",        BLOODWOOD_LOG);
        LOGS.put("blue_mahoe",       BLUE_MAHOE_LOG);
        LOGS.put("cottonwood",       COTTONWOOD_LOG);
        LOGS.put("black_cottonwood", BLACK_COTTONWOOD_LOG);
        LOGS.put("cinnamon",         CINNAMON_LOG);
        LOGS.put("clove",            CLOVE_LOG);
        LOGS.put("ebony",            EBONY_LOG);
        LOGS.put("elm",              ELM_LOG);
        LOGS.put("cedar",            CEDAR_LOG);
        LOGS.put("apple",            APPLE_LOG);
        LOGS.put("goldenheart",      GOLDENHEART_LOG);
        LOGS.put("linden",           LINDEN_LOG);
        LOGS.put("mahogany",         MAHOGANY_LOG);
        LOGS.put("maple",            MAPLE_LOG);
        LOGS.put("myrrh",            MYRRH_LOG);
        LOGS.put("redwood",          REDWOOD_LOG);
        LOGS.put("chestnut",         CHESTNUT_LOG);
        LOGS.put("willow",           WILLOW_LOG);
        LOGS.put("wormtree",         WORMTREE_LOG);

        STRIPPED_LOGS.put("weirwood",         STRIPPED_WEIRWOOD_LOG);
        STRIPPED_LOGS.put("aspen",            STRIPPED_ASPEN_LOG);
        STRIPPED_LOGS.put("alder",            STRIPPED_ALDER_LOG);
        STRIPPED_LOGS.put("pine",             STRIPPED_PINE_LOG);
        STRIPPED_LOGS.put("fir",              STRIPPED_FIR_LOG);
        STRIPPED_LOGS.put("sentinal",         STRIPPED_SENTINAL_LOG);
        STRIPPED_LOGS.put("ironwood",         STRIPPED_IRONWOOD_LOG);
        STRIPPED_LOGS.put("beech",            STRIPPED_BEECH_LOG);
        STRIPPED_LOGS.put("soldier_pine",     STRIPPED_SOLDIER_PINE_LOG);
        STRIPPED_LOGS.put("ash",              STRIPPED_ASH_LOG);
        STRIPPED_LOGS.put("hawthorn",         STRIPPED_HAWTHORN_LOG);
        STRIPPED_LOGS.put("blackbark",        STRIPPED_BLACKBARK_LOG);
        STRIPPED_LOGS.put("bloodwood",        STRIPPED_BLOODWOOD_LOG);
        STRIPPED_LOGS.put("blue_mahoe",       STRIPPED_BLUE_MAHOE_LOG);
        STRIPPED_LOGS.put("cottonwood",       STRIPPED_COTTONWOOD_LOG);
        STRIPPED_LOGS.put("black_cottonwood", STRIPPED_BLACK_COTTONWOOD_LOG);
        STRIPPED_LOGS.put("cinnamon",         STRIPPED_CINNAMON_LOG);
        STRIPPED_LOGS.put("clove",            STRIPPED_CLOVE_LOG);
        STRIPPED_LOGS.put("ebony",            STRIPPED_EBONY_LOG);
        STRIPPED_LOGS.put("elm",              STRIPPED_ELM_LOG);
        STRIPPED_LOGS.put("cedar",            STRIPPED_CEDAR_LOG);
        STRIPPED_LOGS.put("apple",            STRIPPED_APPLE_LOG);
        STRIPPED_LOGS.put("goldenheart",      STRIPPED_GOLDENHEART_LOG);
        STRIPPED_LOGS.put("linden",           STRIPPED_LINDEN_LOG);
        STRIPPED_LOGS.put("mahogany",         STRIPPED_MAHOGANY_LOG);
        STRIPPED_LOGS.put("maple",            STRIPPED_MAPLE_LOG);
        STRIPPED_LOGS.put("myrrh",            STRIPPED_MYRRH_LOG);
        STRIPPED_LOGS.put("redwood",          STRIPPED_REDWOOD_LOG);
        STRIPPED_LOGS.put("chestnut",         STRIPPED_CHESTNUT_LOG);
        STRIPPED_LOGS.put("willow",           STRIPPED_WILLOW_LOG);
        STRIPPED_LOGS.put("wormtree",         STRIPPED_WORMTREE_LOG);

        WOODS.put("weirwood",         WEIRWOOD_WOOD);
        WOODS.put("aspen",            ASPEN_WOOD);
        WOODS.put("alder",            ALDER_WOOD);
        WOODS.put("pine",             PINE_WOOD);
        WOODS.put("fir",              FIR_WOOD);
        WOODS.put("sentinal",         SENTINAL_WOOD);
        WOODS.put("ironwood",         IRONWOOD_WOOD);
        WOODS.put("beech",            BEECH_WOOD);
        WOODS.put("soldier_pine",     SOLDIER_PINE_WOOD);
        WOODS.put("ash",              ASH_WOOD);
        WOODS.put("hawthorn",         HAWTHORN_WOOD);
        WOODS.put("blackbark",        BLACKBARK_WOOD);
        WOODS.put("bloodwood",        BLOODWOOD_WOOD);
        WOODS.put("blue_mahoe",       BLUE_MAHOE_WOOD);
        WOODS.put("cottonwood",       COTTONWOOD_WOOD);
        WOODS.put("black_cottonwood", BLACK_COTTONWOOD_WOOD);
        WOODS.put("cinnamon",         CINNAMON_WOOD);
        WOODS.put("clove",            CLOVE_WOOD);
        WOODS.put("ebony",            EBONY_WOOD);
        WOODS.put("elm",              ELM_WOOD);
        WOODS.put("cedar",            CEDAR_WOOD);
        WOODS.put("apple",            APPLE_WOOD);
        WOODS.put("goldenheart",      GOLDENHEART_WOOD);
        WOODS.put("linden",           LINDEN_WOOD);
        WOODS.put("mahogany",         MAHOGANY_WOOD);
        WOODS.put("maple",            MAPLE_WOOD);
        WOODS.put("myrrh",            MYRRH_WOOD);
        WOODS.put("redwood",          REDWOOD_WOOD);
        WOODS.put("chestnut",         CHESTNUT_WOOD);
        WOODS.put("willow",           WILLOW_WOOD);
        WOODS.put("wormtree",         WORMTREE_WOOD);

        STRIPPED_WOODS.put("weirwood",         STRIPPED_WEIRWOOD_WOOD);
        STRIPPED_WOODS.put("aspen",            STRIPPED_ASPEN_WOOD);
        STRIPPED_WOODS.put("alder",            STRIPPED_ALDER_WOOD);
        STRIPPED_WOODS.put("pine",             STRIPPED_PINE_WOOD);
        STRIPPED_WOODS.put("fir",              STRIPPED_FIR_WOOD);
        STRIPPED_WOODS.put("sentinal",         STRIPPED_SENTINAL_WOOD);
        STRIPPED_WOODS.put("ironwood",         STRIPPED_IRONWOOD_WOOD);
        STRIPPED_WOODS.put("beech",            STRIPPED_BEECH_WOOD);
        STRIPPED_WOODS.put("soldier_pine",     STRIPPED_SOLDIER_PINE_WOOD);
        STRIPPED_WOODS.put("ash",              STRIPPED_ASH_WOOD);
        STRIPPED_WOODS.put("hawthorn",         STRIPPED_HAWTHORN_WOOD);
        STRIPPED_WOODS.put("blackbark",        STRIPPED_BLACKBARK_WOOD);
        STRIPPED_WOODS.put("bloodwood",        STRIPPED_BLOODWOOD_WOOD);
        STRIPPED_WOODS.put("blue_mahoe",       STRIPPED_BLUE_MAHOE_WOOD);
        STRIPPED_WOODS.put("cottonwood",       STRIPPED_COTTONWOOD_WOOD);
        STRIPPED_WOODS.put("black_cottonwood", STRIPPED_BLACK_COTTONWOOD_WOOD);
        STRIPPED_WOODS.put("cinnamon",         STRIPPED_CINNAMON_WOOD);
        STRIPPED_WOODS.put("clove",            STRIPPED_CLOVE_WOOD);
        STRIPPED_WOODS.put("ebony",            STRIPPED_EBONY_WOOD);
        STRIPPED_WOODS.put("elm",              STRIPPED_ELM_WOOD);
        STRIPPED_WOODS.put("cedar",            STRIPPED_CEDAR_WOOD);
        STRIPPED_WOODS.put("apple",            STRIPPED_APPLE_WOOD);
        STRIPPED_WOODS.put("goldenheart",      STRIPPED_GOLDENHEART_WOOD);
        STRIPPED_WOODS.put("linden",           STRIPPED_LINDEN_WOOD);
        STRIPPED_WOODS.put("mahogany",         STRIPPED_MAHOGANY_WOOD);
        STRIPPED_WOODS.put("maple",            STRIPPED_MAPLE_WOOD);
        STRIPPED_WOODS.put("myrrh",            STRIPPED_MYRRH_WOOD);
        STRIPPED_WOODS.put("redwood",          STRIPPED_REDWOOD_WOOD);
        STRIPPED_WOODS.put("chestnut",         STRIPPED_CHESTNUT_WOOD);
        STRIPPED_WOODS.put("willow",           STRIPPED_WILLOW_WOOD);
        STRIPPED_WOODS.put("wormtree",         STRIPPED_WORMTREE_WOOD);

        // ── Extended wood types ───────────────────────────────────────────────
        LOGS.put("nightwood",    NIGHTWOOD_LOG);
        LOGS.put("purpleheart",  PURPLEHEART_LOG);
        LOGS.put("tigerwood",    TIGERWOOD_LOG);
        LOGS.put("burl",         BURL_LOG);
        LOGS.put("sandalwood",   SANDALWOOD_LOG);
        LOGS.put("sandbeggar",   SANDBEGGAR_LOG);
        LOGS.put("apricot",      APRICOT_LOG);
        LOGS.put("blackthorn",   BLACKTHORN_LOG);
        LOGS.put("red_cherry",   RED_CHERRY_LOG);
        LOGS.put("black_cherry", BLACK_CHERRY_LOG);
        LOGS.put("white_cherry", WHITE_CHERRY_LOG);
        LOGS.put("crabapple",    CRABAPPLE_LOG);
        LOGS.put("date_palm",    DATE_PALM_LOG);
        LOGS.put("fig",          FIG_LOG);
        LOGS.put("lemon",        LEMON_LOG);
        LOGS.put("lime",         LIME_LOG);
        LOGS.put("olive",        OLIVE_LOG);
        LOGS.put("orange",       ORANGE_LOG);
        LOGS.put("peach",        PEACH_LOG);
        LOGS.put("pear",         PEAR_LOG);
        LOGS.put("persimmon",    PERSIMMON_LOG);
        LOGS.put("pink_ivory",   PINK_IVORY_LOG);
        LOGS.put("plum",         PLUM_LOG);
        LOGS.put("pomegranate",  POMEGRANATE_LOG);
        LOGS.put("prune",        PRUNE_LOG);
        LOGS.put("almond",       ALMOND_LOG);
        LOGS.put("nutmeg",       NUTMEG_LOG);
        LOGS.put("hemlock",      HEMLOCK_LOG);

        STRIPPED_LOGS.put("nightwood",    STRIPPED_NIGHTWOOD_LOG);
        STRIPPED_LOGS.put("purpleheart",  STRIPPED_PURPLEHEART_LOG);
        STRIPPED_LOGS.put("tigerwood",    STRIPPED_TIGERWOOD_LOG);
        STRIPPED_LOGS.put("burl",         STRIPPED_BURL_LOG);
        STRIPPED_LOGS.put("sandalwood",   STRIPPED_SANDALWOOD_LOG);
        STRIPPED_LOGS.put("sandbeggar",   STRIPPED_SANDBEGGAR_LOG);
        STRIPPED_LOGS.put("apricot",      STRIPPED_APRICOT_LOG);
        STRIPPED_LOGS.put("blackthorn",   STRIPPED_BLACKTHORN_LOG);
        STRIPPED_LOGS.put("red_cherry",   STRIPPED_RED_CHERRY_LOG);
        STRIPPED_LOGS.put("black_cherry", STRIPPED_BLACK_CHERRY_LOG);
        STRIPPED_LOGS.put("white_cherry", STRIPPED_WHITE_CHERRY_LOG);
        STRIPPED_LOGS.put("crabapple",    STRIPPED_CRABAPPLE_LOG);
        STRIPPED_LOGS.put("date_palm",    STRIPPED_DATE_PALM_LOG);
        STRIPPED_LOGS.put("fig",          STRIPPED_FIG_LOG);
        STRIPPED_LOGS.put("lemon",        STRIPPED_LEMON_LOG);
        STRIPPED_LOGS.put("lime",         STRIPPED_LIME_LOG);
        STRIPPED_LOGS.put("olive",        STRIPPED_OLIVE_LOG);
        STRIPPED_LOGS.put("orange",       STRIPPED_ORANGE_LOG);
        STRIPPED_LOGS.put("peach",        STRIPPED_PEACH_LOG);
        STRIPPED_LOGS.put("pear",         STRIPPED_PEAR_LOG);
        STRIPPED_LOGS.put("persimmon",    STRIPPED_PERSIMMON_LOG);
        STRIPPED_LOGS.put("pink_ivory",   STRIPPED_PINK_IVORY_LOG);
        STRIPPED_LOGS.put("plum",         STRIPPED_PLUM_LOG);
        STRIPPED_LOGS.put("pomegranate",  STRIPPED_POMEGRANATE_LOG);
        STRIPPED_LOGS.put("prune",        STRIPPED_PRUNE_LOG);
        STRIPPED_LOGS.put("almond",       STRIPPED_ALMOND_LOG);
        STRIPPED_LOGS.put("nutmeg",       STRIPPED_NUTMEG_LOG);
        STRIPPED_LOGS.put("hemlock",      STRIPPED_HEMLOCK_LOG);

        WOODS.put("nightwood",    NIGHTWOOD_WOOD);
        WOODS.put("purpleheart",  PURPLEHEART_WOOD);
        WOODS.put("tigerwood",    TIGERWOOD_WOOD);
        WOODS.put("burl",         BURL_WOOD);
        WOODS.put("sandalwood",   SANDALWOOD_WOOD);
        WOODS.put("sandbeggar",   SANDBEGGAR_WOOD);
        WOODS.put("apricot",      APRICOT_WOOD);
        WOODS.put("blackthorn",   BLACKTHORN_WOOD);
        WOODS.put("red_cherry",   RED_CHERRY_WOOD);
        WOODS.put("black_cherry", BLACK_CHERRY_WOOD);
        WOODS.put("white_cherry", WHITE_CHERRY_WOOD);
        WOODS.put("crabapple",    CRABAPPLE_WOOD);
        WOODS.put("date_palm",    DATE_PALM_WOOD);
        WOODS.put("fig",          FIG_WOOD);
        WOODS.put("lemon",        LEMON_WOOD);
        WOODS.put("lime",         LIME_WOOD);
        WOODS.put("olive",        OLIVE_WOOD);
        WOODS.put("orange",       ORANGE_WOOD);
        WOODS.put("peach",        PEACH_WOOD);
        WOODS.put("pear",         PEAR_WOOD);
        WOODS.put("persimmon",    PERSIMMON_WOOD);
        WOODS.put("pink_ivory",   PINK_IVORY_WOOD);
        WOODS.put("plum",         PLUM_WOOD);
        WOODS.put("pomegranate",  POMEGRANATE_WOOD);
        WOODS.put("prune",        PRUNE_WOOD);
        WOODS.put("almond",       ALMOND_WOOD);
        WOODS.put("nutmeg",       NUTMEG_WOOD);
        WOODS.put("hemlock",      HEMLOCK_WOOD);

        STRIPPED_WOODS.put("nightwood",    STRIPPED_NIGHTWOOD_WOOD);
        STRIPPED_WOODS.put("purpleheart",  STRIPPED_PURPLEHEART_WOOD);
        STRIPPED_WOODS.put("tigerwood",    STRIPPED_TIGERWOOD_WOOD);
        STRIPPED_WOODS.put("burl",         STRIPPED_BURL_WOOD);
        STRIPPED_WOODS.put("sandalwood",   STRIPPED_SANDALWOOD_WOOD);
        STRIPPED_WOODS.put("sandbeggar",   STRIPPED_SANDBEGGAR_WOOD);
        STRIPPED_WOODS.put("apricot",      STRIPPED_APRICOT_WOOD);
        STRIPPED_WOODS.put("blackthorn",   STRIPPED_BLACKTHORN_WOOD);
        STRIPPED_WOODS.put("red_cherry",   STRIPPED_RED_CHERRY_WOOD);
        STRIPPED_WOODS.put("black_cherry", STRIPPED_BLACK_CHERRY_WOOD);
        STRIPPED_WOODS.put("white_cherry", STRIPPED_WHITE_CHERRY_WOOD);
        STRIPPED_WOODS.put("crabapple",    STRIPPED_CRABAPPLE_WOOD);
        STRIPPED_WOODS.put("date_palm",    STRIPPED_DATE_PALM_WOOD);
        STRIPPED_WOODS.put("fig",          STRIPPED_FIG_WOOD);
        STRIPPED_WOODS.put("lemon",        STRIPPED_LEMON_WOOD);
        STRIPPED_WOODS.put("lime",         STRIPPED_LIME_WOOD);
        STRIPPED_WOODS.put("olive",        STRIPPED_OLIVE_WOOD);
        STRIPPED_WOODS.put("orange",       STRIPPED_ORANGE_WOOD);
        STRIPPED_WOODS.put("peach",        STRIPPED_PEACH_WOOD);
        STRIPPED_WOODS.put("pear",         STRIPPED_PEAR_WOOD);
        STRIPPED_WOODS.put("persimmon",    STRIPPED_PERSIMMON_WOOD);
        STRIPPED_WOODS.put("pink_ivory",   STRIPPED_PINK_IVORY_WOOD);
        STRIPPED_WOODS.put("plum",         STRIPPED_PLUM_WOOD);
        STRIPPED_WOODS.put("pomegranate",  STRIPPED_POMEGRANATE_WOOD);
        STRIPPED_WOODS.put("prune",        STRIPPED_PRUNE_WOOD);
        STRIPPED_WOODS.put("almond",       STRIPPED_ALMOND_WOOD);
        STRIPPED_WOODS.put("nutmeg",       STRIPPED_NUTMEG_WOOD);
        STRIPPED_WOODS.put("hemlock",      STRIPPED_HEMLOCK_WOOD);

        // ── Branches (custom woods + vanilla overworld woods) ──────────────
        BRANCHES.put("weirwood", WEIRWOOD_BRANCH);
        BRANCHES.put("aspen", ASPEN_BRANCH);
        BRANCHES.put("alder", ALDER_BRANCH);
        BRANCHES.put("pine", PINE_BRANCH);
        BRANCHES.put("fir", FIR_BRANCH);
        BRANCHES.put("sentinal", SENTINAL_BRANCH);
        BRANCHES.put("ironwood", IRONWOOD_BRANCH);
        BRANCHES.put("beech", BEECH_BRANCH);
        BRANCHES.put("soldier_pine", SOLDIER_PINE_BRANCH);
        BRANCHES.put("ash", ASH_BRANCH);
        BRANCHES.put("hawthorn", HAWTHORN_BRANCH);
        BRANCHES.put("blackbark", BLACKBARK_BRANCH);
        BRANCHES.put("bloodwood", BLOODWOOD_BRANCH);
        BRANCHES.put("blue_mahoe", BLUE_MAHOE_BRANCH);
        BRANCHES.put("cottonwood", COTTONWOOD_BRANCH);
        BRANCHES.put("black_cottonwood", BLACK_COTTONWOOD_BRANCH);
        BRANCHES.put("cinnamon", CINNAMON_BRANCH);
        BRANCHES.put("clove", CLOVE_BRANCH);
        BRANCHES.put("ebony", EBONY_BRANCH);
        BRANCHES.put("elm", ELM_BRANCH);
        BRANCHES.put("cedar", CEDAR_BRANCH);
        BRANCHES.put("apple", APPLE_BRANCH);
        BRANCHES.put("goldenheart", GOLDENHEART_BRANCH);
        BRANCHES.put("linden", LINDEN_BRANCH);
        BRANCHES.put("mahogany", MAHOGANY_BRANCH);
        BRANCHES.put("maple", MAPLE_BRANCH);
        BRANCHES.put("myrrh", MYRRH_BRANCH);
        BRANCHES.put("redwood", REDWOOD_BRANCH);
        BRANCHES.put("chestnut", CHESTNUT_BRANCH);
        BRANCHES.put("willow", WILLOW_BRANCH);
        BRANCHES.put("wormtree", WORMTREE_BRANCH);
        BRANCHES.put("nightwood", NIGHTWOOD_BRANCH);
        BRANCHES.put("purpleheart", PURPLEHEART_BRANCH);
        BRANCHES.put("tigerwood", TIGERWOOD_BRANCH);
        BRANCHES.put("burl", BURL_BRANCH);
        BRANCHES.put("sandalwood", SANDALWOOD_BRANCH);
        BRANCHES.put("sandbeggar", SANDBEGGAR_BRANCH);
        BRANCHES.put("apricot", APRICOT_BRANCH);
        BRANCHES.put("blackthorn", BLACKTHORN_BRANCH);
        BRANCHES.put("red_cherry", RED_CHERRY_BRANCH);
        BRANCHES.put("black_cherry", BLACK_CHERRY_BRANCH);
        BRANCHES.put("white_cherry", WHITE_CHERRY_BRANCH);
        BRANCHES.put("crabapple", CRABAPPLE_BRANCH);
        BRANCHES.put("date_palm", DATE_PALM_BRANCH);
        BRANCHES.put("fig", FIG_BRANCH);
        BRANCHES.put("lemon", LEMON_BRANCH);
        BRANCHES.put("lime", LIME_BRANCH);
        BRANCHES.put("olive", OLIVE_BRANCH);
        BRANCHES.put("orange", ORANGE_BRANCH);
        BRANCHES.put("peach", PEACH_BRANCH);
        BRANCHES.put("pear", PEAR_BRANCH);
        BRANCHES.put("persimmon", PERSIMMON_BRANCH);
        BRANCHES.put("pink_ivory", PINK_IVORY_BRANCH);
        BRANCHES.put("plum", PLUM_BRANCH);
        BRANCHES.put("pomegranate", POMEGRANATE_BRANCH);
        BRANCHES.put("prune", PRUNE_BRANCH);
        BRANCHES.put("almond", ALMOND_BRANCH);
        BRANCHES.put("nutmeg", NUTMEG_BRANCH);
        BRANCHES.put("hemlock", HEMLOCK_BRANCH);
        BRANCHES.put("oak", OAK_BRANCH);
        BRANCHES.put("spruce", SPRUCE_BRANCH);
        BRANCHES.put("birch", BIRCH_BRANCH);
        BRANCHES.put("jungle", JUNGLE_BRANCH);
        BRANCHES.put("acacia", ACACIA_BRANCH);
        BRANCHES.put("dark_oak", DARK_OAK_BRANCH);
        BRANCHES.put("mangrove", MANGROVE_BRANCH);
        BRANCHES.put("cherry", CHERRY_BRANCH);
        BRANCHES.put("pale_oak", PALE_OAK_BRANCH);

        STRIPPED_BRANCHES.put("weirwood", STRIPPED_WEIRWOOD_BRANCH);
        STRIPPED_BRANCHES.put("aspen", STRIPPED_ASPEN_BRANCH);
        STRIPPED_BRANCHES.put("alder", STRIPPED_ALDER_BRANCH);
        STRIPPED_BRANCHES.put("pine", STRIPPED_PINE_BRANCH);
        STRIPPED_BRANCHES.put("fir", STRIPPED_FIR_BRANCH);
        STRIPPED_BRANCHES.put("sentinal", STRIPPED_SENTINAL_BRANCH);
        STRIPPED_BRANCHES.put("ironwood", STRIPPED_IRONWOOD_BRANCH);
        STRIPPED_BRANCHES.put("beech", STRIPPED_BEECH_BRANCH);
        STRIPPED_BRANCHES.put("soldier_pine", STRIPPED_SOLDIER_PINE_BRANCH);
        STRIPPED_BRANCHES.put("ash", STRIPPED_ASH_BRANCH);
        STRIPPED_BRANCHES.put("hawthorn", STRIPPED_HAWTHORN_BRANCH);
        STRIPPED_BRANCHES.put("blackbark", STRIPPED_BLACKBARK_BRANCH);
        STRIPPED_BRANCHES.put("bloodwood", STRIPPED_BLOODWOOD_BRANCH);
        STRIPPED_BRANCHES.put("blue_mahoe", STRIPPED_BLUE_MAHOE_BRANCH);
        STRIPPED_BRANCHES.put("cottonwood", STRIPPED_COTTONWOOD_BRANCH);
        STRIPPED_BRANCHES.put("black_cottonwood", STRIPPED_BLACK_COTTONWOOD_BRANCH);
        STRIPPED_BRANCHES.put("cinnamon", STRIPPED_CINNAMON_BRANCH);
        STRIPPED_BRANCHES.put("clove", STRIPPED_CLOVE_BRANCH);
        STRIPPED_BRANCHES.put("ebony", STRIPPED_EBONY_BRANCH);
        STRIPPED_BRANCHES.put("elm", STRIPPED_ELM_BRANCH);
        STRIPPED_BRANCHES.put("cedar", STRIPPED_CEDAR_BRANCH);
        STRIPPED_BRANCHES.put("apple", STRIPPED_APPLE_BRANCH);
        STRIPPED_BRANCHES.put("goldenheart", STRIPPED_GOLDENHEART_BRANCH);
        STRIPPED_BRANCHES.put("linden", STRIPPED_LINDEN_BRANCH);
        STRIPPED_BRANCHES.put("mahogany", STRIPPED_MAHOGANY_BRANCH);
        STRIPPED_BRANCHES.put("maple", STRIPPED_MAPLE_BRANCH);
        STRIPPED_BRANCHES.put("myrrh", STRIPPED_MYRRH_BRANCH);
        STRIPPED_BRANCHES.put("redwood", STRIPPED_REDWOOD_BRANCH);
        STRIPPED_BRANCHES.put("chestnut", STRIPPED_CHESTNUT_BRANCH);
        STRIPPED_BRANCHES.put("willow", STRIPPED_WILLOW_BRANCH);
        STRIPPED_BRANCHES.put("wormtree", STRIPPED_WORMTREE_BRANCH);
        STRIPPED_BRANCHES.put("nightwood", STRIPPED_NIGHTWOOD_BRANCH);
        STRIPPED_BRANCHES.put("purpleheart", STRIPPED_PURPLEHEART_BRANCH);
        STRIPPED_BRANCHES.put("tigerwood", STRIPPED_TIGERWOOD_BRANCH);
        STRIPPED_BRANCHES.put("burl", STRIPPED_BURL_BRANCH);
        STRIPPED_BRANCHES.put("sandalwood", STRIPPED_SANDALWOOD_BRANCH);
        STRIPPED_BRANCHES.put("sandbeggar", STRIPPED_SANDBEGGAR_BRANCH);
        STRIPPED_BRANCHES.put("apricot", STRIPPED_APRICOT_BRANCH);
        STRIPPED_BRANCHES.put("blackthorn", STRIPPED_BLACKTHORN_BRANCH);
        STRIPPED_BRANCHES.put("red_cherry", STRIPPED_RED_CHERRY_BRANCH);
        STRIPPED_BRANCHES.put("black_cherry", STRIPPED_BLACK_CHERRY_BRANCH);
        STRIPPED_BRANCHES.put("white_cherry", STRIPPED_WHITE_CHERRY_BRANCH);
        STRIPPED_BRANCHES.put("crabapple", STRIPPED_CRABAPPLE_BRANCH);
        STRIPPED_BRANCHES.put("date_palm", STRIPPED_DATE_PALM_BRANCH);
        STRIPPED_BRANCHES.put("fig", STRIPPED_FIG_BRANCH);
        STRIPPED_BRANCHES.put("lemon", STRIPPED_LEMON_BRANCH);
        STRIPPED_BRANCHES.put("lime", STRIPPED_LIME_BRANCH);
        STRIPPED_BRANCHES.put("olive", STRIPPED_OLIVE_BRANCH);
        STRIPPED_BRANCHES.put("orange", STRIPPED_ORANGE_BRANCH);
        STRIPPED_BRANCHES.put("peach", STRIPPED_PEACH_BRANCH);
        STRIPPED_BRANCHES.put("pear", STRIPPED_PEAR_BRANCH);
        STRIPPED_BRANCHES.put("persimmon", STRIPPED_PERSIMMON_BRANCH);
        STRIPPED_BRANCHES.put("pink_ivory", STRIPPED_PINK_IVORY_BRANCH);
        STRIPPED_BRANCHES.put("plum", STRIPPED_PLUM_BRANCH);
        STRIPPED_BRANCHES.put("pomegranate", STRIPPED_POMEGRANATE_BRANCH);
        STRIPPED_BRANCHES.put("prune", STRIPPED_PRUNE_BRANCH);
        STRIPPED_BRANCHES.put("almond", STRIPPED_ALMOND_BRANCH);
        STRIPPED_BRANCHES.put("nutmeg", STRIPPED_NUTMEG_BRANCH);
        STRIPPED_BRANCHES.put("hemlock", STRIPPED_HEMLOCK_BRANCH);
        STRIPPED_BRANCHES.put("oak", STRIPPED_OAK_BRANCH);
        STRIPPED_BRANCHES.put("spruce", STRIPPED_SPRUCE_BRANCH);
        STRIPPED_BRANCHES.put("birch", STRIPPED_BIRCH_BRANCH);
        STRIPPED_BRANCHES.put("jungle", STRIPPED_JUNGLE_BRANCH);
        STRIPPED_BRANCHES.put("acacia", STRIPPED_ACACIA_BRANCH);
        STRIPPED_BRANCHES.put("dark_oak", STRIPPED_DARK_OAK_BRANCH);
        STRIPPED_BRANCHES.put("mangrove", STRIPPED_MANGROVE_BRANCH);
        STRIPPED_BRANCHES.put("cherry", STRIPPED_CHERRY_BRANCH);
        STRIPPED_BRANCHES.put("pale_oak", STRIPPED_PALE_OAK_BRANCH);
    }

}