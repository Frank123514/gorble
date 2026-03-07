package net.got.init;

import net.got.block.*;
import net.got.block.GotSaplingBlock;
import net.got.block.GotStrippedLogBlock;
import net.got.block.RegionalRockBlock;
import net.got.block.RegionalRockPillarBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

import net.got.GotMod;
import net.got.init.GotTreeGrowers;
import net.got.init.GotWoodTypes;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

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
    public static final DeferredBlock<Block> WEIRWOOD_LOG = logBlock("weirwood_log", WeirwoodLogBlock::new);
    public static final DeferredBlock<Block> WEIRWOOD_WOOD = logBlock("weirwood_wood", WeirwoodWoodBlock::new);
    public static final DeferredBlock<Block> WEIRWOOD_PLANKS = woodBlock("weirwood_planks", WeirwoodPlanksBlock::new);
    public static final DeferredBlock<Block> WEIRWOOD_LEAVES = woodBlock("weirwood_leaves", WeirwoodLeavesBlock::new);
    public static final DeferredBlock<Block> WEIRWOOD_STAIRS = woodBlock("weirwood_stairs", WeirwoodStairsBlock::new);
    public static final DeferredBlock<Block> WEIRWOOD_SLAB = woodBlock("weirwood_slab", WeirwoodSlabBlock::new);
    public static final DeferredBlock<Block> WEIRWOOD_FENCE = woodBlock("weirwood_fence", WeirwoodFenceBlock::new);
    public static final DeferredBlock<Block> WEIRWOOD_FENCE_GATE = woodBlock("weirwood_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.WEIRWOOD, p));
    public static final DeferredBlock<Block> WEIRWOOD_PRESSURE_PLATE = woodBlock("weirwood_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.WEIRWOOD_SET, p));
    public static final DeferredBlock<Block> WEIRWOOD_BUTTON = woodBlock("weirwood_button", p -> new GotButtonBlock(GotWoodTypes.WEIRWOOD_SET, p));
    public static final DeferredBlock<Block> ASPEN_LOG = logBlock("aspen_log", AspenLogBlock::new);
    public static final DeferredBlock<Block> ASPEN_WOOD = logBlock("aspen_wood", AspenWoodBlock::new);
    public static final DeferredBlock<Block> ASPEN_PLANKS = woodBlock("aspen_planks", AspenPlanksBlock::new);
    public static final DeferredBlock<Block> ASPEN_LEAVES = woodBlock("aspen_leaves", AspenLeavesBlock::new);
    public static final DeferredBlock<Block> ASPEN_STAIRS = woodBlock("aspen_stairs", AspenStairsBlock::new);
    public static final DeferredBlock<Block> ASPEN_SLAB = woodBlock("aspen_slab", AspenSlabBlock::new);
    public static final DeferredBlock<Block> ASPEN_FENCE = woodBlock("aspen_fence", AspenFenceBlock::new);
    public static final DeferredBlock<Block> ASPEN_FENCE_GATE = woodBlock("aspen_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.ASPEN, p));
    public static final DeferredBlock<Block> ASPEN_PRESSURE_PLATE = woodBlock("aspen_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.ASPEN_SET, p));
    public static final DeferredBlock<Block> ASPEN_BUTTON = woodBlock("aspen_button", p -> new GotButtonBlock(GotWoodTypes.ASPEN_SET, p));
    public static final DeferredBlock<Block> ALDER_LOG = logBlock("alder_log", AlderLogBlock::new);
    public static final DeferredBlock<Block> ALDER_WOOD = logBlock("alder_wood", AlderWoodBlock::new);
    public static final DeferredBlock<Block> ALDER_PLANKS = woodBlock("alder_planks", AlderPlanksBlock::new);
    public static final DeferredBlock<Block> ALDER_LEAVES = woodBlock("alder_leaves", AlderLeavesBlock::new);
    public static final DeferredBlock<Block> ALDER_STAIRS = woodBlock("alder_stairs", AlderStairsBlock::new);
    public static final DeferredBlock<Block> ALDER_SLAB = woodBlock("alder_slab", AlderSlabBlock::new);
    public static final DeferredBlock<Block> ALDER_FENCE = woodBlock("alder_fence", AlderFenceBlock::new);
    public static final DeferredBlock<Block> ALDER_FENCE_GATE = woodBlock("alder_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.ALDER, p));
    public static final DeferredBlock<Block> ALDER_PRESSURE_PLATE = woodBlock("alder_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.ALDER_SET, p));
    public static final DeferredBlock<Block> ALDER_BUTTON = woodBlock("alder_button", p -> new GotButtonBlock(GotWoodTypes.ALDER_SET, p));
    public static final DeferredBlock<Block> PINE_LOG = logBlock("pine_log", PineLogBlock::new);
    public static final DeferredBlock<Block> PINE_WOOD = logBlock("pine_wood", PineWoodBlock::new);
    public static final DeferredBlock<Block> PINE_PLANKS = woodBlock("pine_planks", PinePlanksBlock::new);
    public static final DeferredBlock<Block> PINE_LEAVES = woodBlock("pine_leaves", PineLeavesBlock::new);
    public static final DeferredBlock<Block> PINE_STAIRS = woodBlock("pine_stairs", PineStairsBlock::new);
    public static final DeferredBlock<Block> PINE_SLAB = woodBlock("pine_slab", PineSlabBlock::new);
    public static final DeferredBlock<Block> PINE_FENCE = woodBlock("pine_fence", PineFenceBlock::new);
    public static final DeferredBlock<Block> PINE_FENCE_GATE = woodBlock("pine_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.PINE, p));
    public static final DeferredBlock<Block> PINE_PRESSURE_PLATE = woodBlock("pine_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.PINE_SET, p));
    public static final DeferredBlock<Block> PINE_BUTTON = woodBlock("pine_button", p -> new GotButtonBlock(GotWoodTypes.PINE_SET, p));
    public static final DeferredBlock<Block> FIR_LOG = logBlock("fir_log", FirLogBlock::new);
    public static final DeferredBlock<Block> FIR_WOOD = logBlock("fir_wood", FirWoodBlock::new);
    public static final DeferredBlock<Block> FIR_PLANKS = woodBlock("fir_planks", FirPlanksBlock::new);
    public static final DeferredBlock<Block> FIR_LEAVES = woodBlock("fir_leaves", FirLeavesBlock::new);
    public static final DeferredBlock<Block> FIR_STAIRS = woodBlock("fir_stairs", FirStairsBlock::new);
    public static final DeferredBlock<Block> FIR_SLAB = woodBlock("fir_slab", FirSlabBlock::new);
    public static final DeferredBlock<Block> FIR_FENCE = woodBlock("fir_fence", FirFenceBlock::new);
    public static final DeferredBlock<Block> FIR_FENCE_GATE = woodBlock("fir_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.FIR, p));
    public static final DeferredBlock<Block> FIR_PRESSURE_PLATE = woodBlock("fir_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.FIR_SET, p));
    public static final DeferredBlock<Block> FIR_BUTTON = woodBlock("fir_button", p -> new GotButtonBlock(GotWoodTypes.FIR_SET, p));
    public static final DeferredBlock<Block> SENTINAL_LOG = logBlock("sentinal_log", SentinalLogBlock::new);
    public static final DeferredBlock<Block> SENTINAL_WOOD = logBlock("sentinal_wood", SentinalWoodBlock::new);
    public static final DeferredBlock<Block> SENTINAL_PLANKS = woodBlock("sentinal_planks", SentinalPlanksBlock::new);
    public static final DeferredBlock<Block> SENTINAL_LEAVES = woodBlock("sentinal_leaves", SentinalLeavesBlock::new);
    public static final DeferredBlock<Block> SENTINAL_STAIRS = woodBlock("sentinal_stairs", SentinalStairsBlock::new);
    public static final DeferredBlock<Block> SENTINAL_SLAB = woodBlock("sentinal_slab", SentinalSlabBlock::new);
    public static final DeferredBlock<Block> SENTINAL_FENCE = woodBlock("sentinal_fence", SentinalFenceBlock::new);
    public static final DeferredBlock<Block> SENTINAL_FENCE_GATE = woodBlock("sentinal_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.SENTINAL, p));
    public static final DeferredBlock<Block> SENTINAL_PRESSURE_PLATE = woodBlock("sentinal_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.SENTINAL_SET, p));
    public static final DeferredBlock<Block> SENTINAL_BUTTON = woodBlock("sentinal_button", p -> new GotButtonBlock(GotWoodTypes.SENTINAL_SET, p));
    public static final DeferredBlock<Block> IRONWOOD_LOG = logBlock("ironwood_log", IronwoodLogBlock::new);
    public static final DeferredBlock<Block> IRONWOOD_WOOD = logBlock("ironwood_wood", IronwoodWoodBlock::new);
    public static final DeferredBlock<Block> IRONWOOD_PLANKS = woodBlock("ironwood_planks", IronwoodPlanksBlock::new);
    public static final DeferredBlock<Block> IRONWOOD_LEAVES = woodBlock("ironwood_leaves", IronwoodLeavesBlock::new);
    public static final DeferredBlock<Block> IRONWOOD_STAIRS = woodBlock("ironwood_stairs", IronwoodStairsBlock::new);
    public static final DeferredBlock<Block> IRONWOOD_SLAB = woodBlock("ironwood_slab", IronwoodSlabBlock::new);
    public static final DeferredBlock<Block> IRONWOOD_FENCE = woodBlock("ironwood_fence", IronwoodFenceBlock::new);
    public static final DeferredBlock<Block> IRONWOOD_FENCE_GATE = woodBlock("ironwood_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.IRONWOOD, p));
    public static final DeferredBlock<Block> IRONWOOD_PRESSURE_PLATE = woodBlock("ironwood_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.IRONWOOD_SET, p));
    public static final DeferredBlock<Block> IRONWOOD_BUTTON = woodBlock("ironwood_button", p -> new GotButtonBlock(GotWoodTypes.IRONWOOD_SET, p));
    public static final DeferredBlock<Block> BEECH_LOG = logBlock("beech_log", BeechLogBlock::new);
    public static final DeferredBlock<Block> BEECH_WOOD = logBlock("beech_wood", BeechWoodBlock::new);
    public static final DeferredBlock<Block> BEECH_PLANKS = woodBlock("beech_planks", BeechPlanksBlock::new);
    public static final DeferredBlock<Block> BEECH_LEAVES = woodBlock("beech_leaves", BeechLeavesBlock::new);
    public static final DeferredBlock<Block> BEECH_STAIRS = woodBlock("beech_stairs", BeechStairsBlock::new);
    public static final DeferredBlock<Block> BEECH_SLAB = woodBlock("beech_slab", BeechSlabBlock::new);
    public static final DeferredBlock<Block> BEECH_FENCE = woodBlock("beech_fence", BeechFenceBlock::new);
    public static final DeferredBlock<Block> BEECH_FENCE_GATE = woodBlock("beech_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.BEECH, p));
    public static final DeferredBlock<Block> BEECH_PRESSURE_PLATE = woodBlock("beech_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.BEECH_SET, p));
    public static final DeferredBlock<Block> BEECH_BUTTON = woodBlock("beech_button", p -> new GotButtonBlock(GotWoodTypes.BEECH_SET, p));
    public static final DeferredBlock<Block> SOLDIER_PINE_LOG = logBlock("soldier_pine_log", SoldierPineLogBlock::new);
    public static final DeferredBlock<Block> SOLDIER_PINE_WOOD = logBlock("soldier_pine_wood", SoldierPineWoodBlock::new);
    public static final DeferredBlock<Block> SOLDIER_PINE_PLANKS = woodBlock("soldier_pine_planks", SoldierPinePlanksBlock::new);
    public static final DeferredBlock<Block> SOLDIER_PINE_LEAVES = woodBlock("soldier_pine_leaves", SoldierPineLeavesBlock::new);
    public static final DeferredBlock<Block> SOLDIER_PINE_STAIRS = woodBlock("soldier_pine_stairs", SoldierPineStairsBlock::new);
    public static final DeferredBlock<Block> SOLDIER_PINE_SLAB = woodBlock("soldier_pine_slab", SoldierPineSlabBlock::new);
    public static final DeferredBlock<Block> SOLDIER_PINE_FENCE = woodBlock("soldier_pine_fence", SoldierPineFenceBlock::new);
    public static final DeferredBlock<Block> SOLDIER_PINE_FENCE_GATE = woodBlock("soldier_pine_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.PINE, p));
    public static final DeferredBlock<Block> SOLDIER_PINE_PRESSURE_PLATE = woodBlock("soldier_pine_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.PINE_SET, p));
    public static final DeferredBlock<Block> SOLDIER_PINE_BUTTON = woodBlock("soldier_pine_button", p -> new GotButtonBlock(GotWoodTypes.PINE_SET, p));
    public static final DeferredBlock<Block> ASH_LOG = logBlock("ash_log", AshLogBlock::new);
    public static final DeferredBlock<Block> ASH_WOOD = logBlock("ash_wood", AshWoodBlock::new);
    public static final DeferredBlock<Block> ASH_PLANKS = woodBlock("ash_planks", AshPlanksBlock::new);
    public static final DeferredBlock<Block> ASH_LEAVES = woodBlock("ash_leaves", AshLeavesBlock::new);
    public static final DeferredBlock<Block> ASH_STAIRS = woodBlock("ash_stairs", AshStairsBlock::new);
    public static final DeferredBlock<Block> ASH_SLAB = woodBlock("ash_slab", AshSlabBlock::new);
    public static final DeferredBlock<Block> ASH_FENCE = woodBlock("ash_fence", AshFenceBlock::new);
    public static final DeferredBlock<Block> ASH_FENCE_GATE = woodBlock("ash_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.ASH, p));
    public static final DeferredBlock<Block> ASH_PRESSURE_PLATE = woodBlock("ash_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.ASH_SET, p));
    public static final DeferredBlock<Block> ASH_BUTTON = woodBlock("ash_button", p -> new GotButtonBlock(GotWoodTypes.ASH_SET, p));
    public static final DeferredBlock<Block> HAWTHORN_LOG = logBlock("hawthorn_log", HawthornLogBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_WOOD = logBlock("hawthorn_wood", HawthornWoodBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_PLANKS = woodBlock("hawthorn_planks", HawthornPlanksBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_LEAVES = woodBlock("hawthorn_leaves", HawthornLeavesBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_STAIRS = woodBlock("hawthorn_stairs", HawthornStairsBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_SLAB = woodBlock("hawthorn_slab", HawthornSlabBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_FENCE = woodBlock("hawthorn_fence", HawthornFenceBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_FENCE_GATE = woodBlock("hawthorn_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.HAWTHORN, p));
    public static final DeferredBlock<Block> HAWTHORN_PRESSURE_PLATE = woodBlock("hawthorn_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.HAWTHORN_SET, p));
    public static final DeferredBlock<Block> HAWTHORN_BUTTON = woodBlock("hawthorn_button", p -> new GotButtonBlock(GotWoodTypes.HAWTHORN_SET, p));

    // ── Crownlands ──────────────────────────────────────────────────────

    // ── Dorne ──────────────────────────────────────────────────────

    // ── Iron Islands ──────────────────────────────────────────────────────

    // ── North ──────────────────────────────────────────────────────

    // ── Reach ──────────────────────────────────────────────────────

    // ── Riverlands ──────────────────────────────────────────────────────

    // ── Stormlands ──────────────────────────────────────────────────────

    // ── Vale ──────────────────────────────────────────────────────

    // ── Westerlands ──────────────────────────────────────────────────────

    // ── Crownlands ─────────────────────────────────────────







    // ── Dorne ─────────────────────────────────────────







    // ── Iron Islands ─────────────────────────────────────────







    // ── North ─────────────────────────────────────────







    // ── Reach ─────────────────────────────────────────







    // ── Riverlands ─────────────────────────────────────────







    // ── Stormlands ─────────────────────────────────────────







    // ── Vale ─────────────────────────────────────────







    // ── Westerlands ─────────────────────────────────────────






    // ── Blackbark Tree ────────────────────────────────────────────────────
    public static final DeferredBlock<Block> BLACKBARK_LOG            = logBlock("blackbark_log",            BlackbarkLogBlock::new);
    public static final DeferredBlock<Block> BLACKBARK_WOOD           = logBlock("blackbark_wood",           BlackbarkWoodBlock::new);
    public static final DeferredBlock<Block> BLACKBARK_PLANKS         = woodBlock("blackbark_planks",         BlackbarkPlanksBlock::new);
    public static final DeferredBlock<Block> BLACKBARK_LEAVES         = woodBlock("blackbark_leaves",         BlackbarkLeavesBlock::new);
    public static final DeferredBlock<Block> BLACKBARK_STAIRS         = woodBlock("blackbark_stairs",         BlackbarkStairsBlock::new);
    public static final DeferredBlock<Block> BLACKBARK_SLAB           = woodBlock("blackbark_slab",           BlackbarkSlabBlock::new);
    public static final DeferredBlock<Block> BLACKBARK_FENCE          = woodBlock("blackbark_fence",          BlackbarkFenceBlock::new);
    public static final DeferredBlock<Block> BLACKBARK_FENCE_GATE     = woodBlock("blackbark_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.BLACKBARK, p));
    public static final DeferredBlock<Block> BLACKBARK_PRESSURE_PLATE = woodBlock("blackbark_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.BLACKBARK_SET, p));
    public static final DeferredBlock<Block> BLACKBARK_BUTTON         = woodBlock("blackbark_button",         p -> new GotButtonBlock(GotWoodTypes.BLACKBARK_SET, p));

    // ── Bloodwood Tree ────────────────────────────────────────────────────
    public static final DeferredBlock<Block> BLOODWOOD_LOG            = logBlock("bloodwood_log",            BloodwoodLogBlock::new);
    public static final DeferredBlock<Block> BLOODWOOD_WOOD           = logBlock("bloodwood_wood",           BloodwoodWoodBlock::new);
    public static final DeferredBlock<Block> BLOODWOOD_PLANKS         = woodBlock("bloodwood_planks",         BloodwoodPlanksBlock::new);
    public static final DeferredBlock<Block> BLOODWOOD_LEAVES         = woodBlock("bloodwood_leaves",         BloodwoodLeavesBlock::new);
    public static final DeferredBlock<Block> BLOODWOOD_STAIRS         = woodBlock("bloodwood_stairs",         BloodwoodStairsBlock::new);
    public static final DeferredBlock<Block> BLOODWOOD_SLAB           = woodBlock("bloodwood_slab",           BloodwoodSlabBlock::new);
    public static final DeferredBlock<Block> BLOODWOOD_FENCE          = woodBlock("bloodwood_fence",          BloodwoodFenceBlock::new);
    public static final DeferredBlock<Block> BLOODWOOD_FENCE_GATE     = woodBlock("bloodwood_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.BLOODWOOD, p));
    public static final DeferredBlock<Block> BLOODWOOD_PRESSURE_PLATE = woodBlock("bloodwood_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.BLOODWOOD_SET, p));
    public static final DeferredBlock<Block> BLOODWOOD_BUTTON         = woodBlock("bloodwood_button",         p -> new GotButtonBlock(GotWoodTypes.BLOODWOOD_SET, p));

    // ── Blue Mahoe Tree ───────────────────────────────────────────────────
    public static final DeferredBlock<Block> BLUE_MAHOE_LOG            = logBlock("blue_mahoe_log",            BlueMahoeLogBlock::new);
    public static final DeferredBlock<Block> BLUE_MAHOE_WOOD           = logBlock("blue_mahoe_wood",           BlueMahoeWoodBlock::new);
    public static final DeferredBlock<Block> BLUE_MAHOE_PLANKS         = woodBlock("blue_mahoe_planks",         BlueMahoePlanksBlock::new);
    public static final DeferredBlock<Block> BLUE_MAHOE_LEAVES         = woodBlock("blue_mahoe_leaves",         BlueMahoeLeavesBlock::new);
    public static final DeferredBlock<Block> BLUE_MAHOE_STAIRS         = woodBlock("blue_mahoe_stairs",         BlueMahoeStairsBlock::new);
    public static final DeferredBlock<Block> BLUE_MAHOE_SLAB           = woodBlock("blue_mahoe_slab",           BlueMahoeSlabBlock::new);
    public static final DeferredBlock<Block> BLUE_MAHOE_FENCE          = woodBlock("blue_mahoe_fence",          BlueMahoeFenceBlock::new);
    public static final DeferredBlock<Block> BLUE_MAHOE_FENCE_GATE     = woodBlock("blue_mahoe_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.BLUE_MAHOE, p));
    public static final DeferredBlock<Block> BLUE_MAHOE_PRESSURE_PLATE = woodBlock("blue_mahoe_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.BLUE_MAHOE_SET, p));
    public static final DeferredBlock<Block> BLUE_MAHOE_BUTTON         = woodBlock("blue_mahoe_button",         p -> new GotButtonBlock(GotWoodTypes.BLUE_MAHOE_SET, p));

    // ── Cottonwood Tree ───────────────────────────────────────────────────
    public static final DeferredBlock<Block> COTTONWOOD_LOG            = logBlock("cottonwood_log",            CottonwoodLogBlock::new);
    public static final DeferredBlock<Block> COTTONWOOD_WOOD           = logBlock("cottonwood_wood",           CottonwoodWoodBlock::new);
    public static final DeferredBlock<Block> COTTONWOOD_PLANKS         = woodBlock("cottonwood_planks",         CottonwoodPlanksBlock::new);
    public static final DeferredBlock<Block> COTTONWOOD_LEAVES         = woodBlock("cottonwood_leaves",         CottonwoodLeavesBlock::new);
    public static final DeferredBlock<Block> COTTONWOOD_STAIRS         = woodBlock("cottonwood_stairs",         CottonwoodStairsBlock::new);
    public static final DeferredBlock<Block> COTTONWOOD_SLAB           = woodBlock("cottonwood_slab",           CottonwoodSlabBlock::new);
    public static final DeferredBlock<Block> COTTONWOOD_FENCE          = woodBlock("cottonwood_fence",          CottonwoodFenceBlock::new);
    public static final DeferredBlock<Block> COTTONWOOD_FENCE_GATE     = woodBlock("cottonwood_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.COTTONWOOD, p));
    public static final DeferredBlock<Block> COTTONWOOD_PRESSURE_PLATE = woodBlock("cottonwood_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.COTTONWOOD_SET, p));
    public static final DeferredBlock<Block> COTTONWOOD_BUTTON         = woodBlock("cottonwood_button",         p -> new GotButtonBlock(GotWoodTypes.COTTONWOOD_SET, p));


    // ── BlackCottonwood Tree ──────────────────────────────────────────
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_LOG            = logBlock("black_cottonwood_log",            BlackCottonwoodLogBlock::new);
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_WOOD           = logBlock("black_cottonwood_wood",           BlackCottonwoodWoodBlock::new);
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_PLANKS         = woodBlock("black_cottonwood_planks",         BlackCottonwoodPlanksBlock::new);
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_LEAVES         = woodBlock("black_cottonwood_leaves",         BlackCottonwoodLeavesBlock::new);
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_STAIRS         = woodBlock("black_cottonwood_stairs",         BlackCottonwoodStairsBlock::new);
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_SLAB           = woodBlock("black_cottonwood_slab",           BlackCottonwoodSlabBlock::new);
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_FENCE          = woodBlock("black_cottonwood_fence",          BlackCottonwoodFenceBlock::new);
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_FENCE_GATE     = woodBlock("black_cottonwood_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.COTTONWOOD, p));
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_PRESSURE_PLATE = woodBlock("black_cottonwood_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.COTTONWOOD_SET, p));
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_BUTTON         = woodBlock("black_cottonwood_button",         p -> new GotButtonBlock(GotWoodTypes.COTTONWOOD_SET, p));

    // ── Cinnamon Tree ──────────────────────────────────────────
    public static final DeferredBlock<Block> CINNAMON_LOG            = logBlock("cinnamon_log",            CinnamonLogBlock::new);
    public static final DeferredBlock<Block> CINNAMON_WOOD           = logBlock("cinnamon_wood",           CinnamonWoodBlock::new);
    public static final DeferredBlock<Block> CINNAMON_PLANKS         = woodBlock("cinnamon_planks",         CinnamonPlanksBlock::new);
    public static final DeferredBlock<Block> CINNAMON_LEAVES         = woodBlock("cinnamon_leaves",         CinnamonLeavesBlock::new);
    public static final DeferredBlock<Block> CINNAMON_STAIRS         = woodBlock("cinnamon_stairs",         CinnamonStairsBlock::new);
    public static final DeferredBlock<Block> CINNAMON_SLAB           = woodBlock("cinnamon_slab",           CinnamonSlabBlock::new);
    public static final DeferredBlock<Block> CINNAMON_FENCE          = woodBlock("cinnamon_fence",          CinnamonFenceBlock::new);
    public static final DeferredBlock<Block> CINNAMON_FENCE_GATE     = woodBlock("cinnamon_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.CINNAMON, p));
    public static final DeferredBlock<Block> CINNAMON_PRESSURE_PLATE = woodBlock("cinnamon_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.CINNAMON_SET, p));
    public static final DeferredBlock<Block> CINNAMON_BUTTON         = woodBlock("cinnamon_button",         p -> new GotButtonBlock(GotWoodTypes.CINNAMON_SET, p));

    // ── Clove Tree ──────────────────────────────────────────
    public static final DeferredBlock<Block> CLOVE_LOG            = logBlock("clove_log",            CloveLogBlock::new);
    public static final DeferredBlock<Block> CLOVE_WOOD           = logBlock("clove_wood",           CloveWoodBlock::new);
    public static final DeferredBlock<Block> CLOVE_PLANKS         = woodBlock("clove_planks",         ClovePlanksBlock::new);
    public static final DeferredBlock<Block> CLOVE_LEAVES         = woodBlock("clove_leaves",         CloveLeavesBlock::new);
    public static final DeferredBlock<Block> CLOVE_STAIRS         = woodBlock("clove_stairs",         CloveStairsBlock::new);
    public static final DeferredBlock<Block> CLOVE_SLAB           = woodBlock("clove_slab",           CloveSlabBlock::new);
    public static final DeferredBlock<Block> CLOVE_FENCE          = woodBlock("clove_fence",          CloveFenceBlock::new);
    public static final DeferredBlock<Block> CLOVE_FENCE_GATE     = woodBlock("clove_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.CLOVE, p));
    public static final DeferredBlock<Block> CLOVE_PRESSURE_PLATE = woodBlock("clove_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.CLOVE_SET, p));
    public static final DeferredBlock<Block> CLOVE_BUTTON         = woodBlock("clove_button",         p -> new GotButtonBlock(GotWoodTypes.CLOVE_SET, p));

    // ── Ebony Tree ──────────────────────────────────────────
    public static final DeferredBlock<Block> EBONY_LOG            = logBlock("ebony_log",            EbonyLogBlock::new);
    public static final DeferredBlock<Block> EBONY_WOOD           = logBlock("ebony_wood",           EbonyWoodBlock::new);
    public static final DeferredBlock<Block> EBONY_PLANKS         = woodBlock("ebony_planks",         EbonyPlanksBlock::new);
    public static final DeferredBlock<Block> EBONY_LEAVES         = woodBlock("ebony_leaves",         EbonyLeavesBlock::new);
    public static final DeferredBlock<Block> EBONY_STAIRS         = woodBlock("ebony_stairs",         EbonyStairsBlock::new);
    public static final DeferredBlock<Block> EBONY_SLAB           = woodBlock("ebony_slab",           EbonySlabBlock::new);
    public static final DeferredBlock<Block> EBONY_FENCE          = woodBlock("ebony_fence",          EbonyFenceBlock::new);
    public static final DeferredBlock<Block> EBONY_FENCE_GATE     = woodBlock("ebony_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.EBONY, p));
    public static final DeferredBlock<Block> EBONY_PRESSURE_PLATE = woodBlock("ebony_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.EBONY_SET, p));
    public static final DeferredBlock<Block> EBONY_BUTTON         = woodBlock("ebony_button",         p -> new GotButtonBlock(GotWoodTypes.EBONY_SET, p));

    // ── Elm Tree ──────────────────────────────────────────
    public static final DeferredBlock<Block> ELM_LOG            = logBlock("elm_log",            ElmLogBlock::new);
    public static final DeferredBlock<Block> ELM_WOOD           = logBlock("elm_wood",           ElmWoodBlock::new);
    public static final DeferredBlock<Block> ELM_PLANKS         = woodBlock("elm_planks",         ElmPlanksBlock::new);
    public static final DeferredBlock<Block> ELM_LEAVES         = woodBlock("elm_leaves",         ElmLeavesBlock::new);
    public static final DeferredBlock<Block> ELM_STAIRS         = woodBlock("elm_stairs",         ElmStairsBlock::new);
    public static final DeferredBlock<Block> ELM_SLAB           = woodBlock("elm_slab",           ElmSlabBlock::new);
    public static final DeferredBlock<Block> ELM_FENCE          = woodBlock("elm_fence",          ElmFenceBlock::new);
    public static final DeferredBlock<Block> ELM_FENCE_GATE     = woodBlock("elm_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.ELM, p));
    public static final DeferredBlock<Block> ELM_PRESSURE_PLATE = woodBlock("elm_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.ELM_SET, p));
    public static final DeferredBlock<Block> ELM_BUTTON         = woodBlock("elm_button",         p -> new GotButtonBlock(GotWoodTypes.ELM_SET, p));

    // ── Cedar Tree ──────────────────────────────────────────
    public static final DeferredBlock<Block> CEDAR_LOG            = logBlock("cedar_log",            CedarLogBlock::new);
    public static final DeferredBlock<Block> CEDAR_WOOD           = logBlock("cedar_wood",           CedarWoodBlock::new);
    public static final DeferredBlock<Block> CEDAR_PLANKS         = woodBlock("cedar_planks",         CedarPlanksBlock::new);
    public static final DeferredBlock<Block> CEDAR_LEAVES         = woodBlock("cedar_leaves",         CedarLeavesBlock::new);
    public static final DeferredBlock<Block> CEDAR_STAIRS         = woodBlock("cedar_stairs",         CedarStairsBlock::new);
    public static final DeferredBlock<Block> CEDAR_SLAB           = woodBlock("cedar_slab",           CedarSlabBlock::new);
    public static final DeferredBlock<Block> CEDAR_FENCE          = woodBlock("cedar_fence",          CedarFenceBlock::new);
    public static final DeferredBlock<Block> CEDAR_FENCE_GATE     = woodBlock("cedar_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.CEDAR, p));
    public static final DeferredBlock<Block> CEDAR_PRESSURE_PLATE = woodBlock("cedar_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.CEDAR_SET, p));
    public static final DeferredBlock<Block> CEDAR_BUTTON         = woodBlock("cedar_button",         p -> new GotButtonBlock(GotWoodTypes.CEDAR_SET, p));



    // ── Branch Blocks (log-textured walls) ───────────────────────────────
    public static final DeferredBlock<Block> WEIRWOOD_BRANCH         = woodBlock("weirwood_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> ASPEN_BRANCH            = woodBlock("aspen_branch",            WoodBranchBlock::new);
    public static final DeferredBlock<Block> ALDER_BRANCH            = woodBlock("alder_branch",            WoodBranchBlock::new);
    public static final DeferredBlock<Block> PINE_BRANCH             = woodBlock("pine_branch",             WoodBranchBlock::new);
    public static final DeferredBlock<Block> FIR_BRANCH              = woodBlock("fir_branch",              WoodBranchBlock::new);
    public static final DeferredBlock<Block> SENTINAL_BRANCH         = woodBlock("sentinal_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> IRONWOOD_BRANCH         = woodBlock("ironwood_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> BEECH_BRANCH            = woodBlock("beech_branch",            WoodBranchBlock::new);
    public static final DeferredBlock<Block> SOLDIER_PINE_BRANCH     = woodBlock("soldier_pine_branch",     WoodBranchBlock::new);
    public static final DeferredBlock<Block> ASH_BRANCH              = woodBlock("ash_branch",              WoodBranchBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_BRANCH         = woodBlock("hawthorn_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> BLACKBARK_BRANCH        = woodBlock("blackbark_branch",        WoodBranchBlock::new);
    public static final DeferredBlock<Block> BLOODWOOD_BRANCH        = woodBlock("bloodwood_branch",        WoodBranchBlock::new);
    public static final DeferredBlock<Block> BLUE_MAHOE_BRANCH       = woodBlock("blue_mahoe_branch",       WoodBranchBlock::new);
    public static final DeferredBlock<Block> COTTONWOOD_BRANCH       = woodBlock("cottonwood_branch",       WoodBranchBlock::new);
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_BRANCH = woodBlock("black_cottonwood_branch", WoodBranchBlock::new);
    public static final DeferredBlock<Block> CINNAMON_BRANCH         = woodBlock("cinnamon_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> CLOVE_BRANCH            = woodBlock("clove_branch",            WoodBranchBlock::new);
    public static final DeferredBlock<Block> EBONY_BRANCH            = woodBlock("ebony_branch",            WoodBranchBlock::new);
    public static final DeferredBlock<Block> ELM_BRANCH              = woodBlock("elm_branch",              WoodBranchBlock::new);
    public static final DeferredBlock<Block> CEDAR_BRANCH            = woodBlock("cedar_branch",            WoodBranchBlock::new);
    public static final DeferredBlock<Block> APPLE_BRANCH            = woodBlock("apple_branch",            WoodBranchBlock::new);
    public static final DeferredBlock<Block> GOLDENHEART_BRANCH      = woodBlock("goldenheart_branch",      WoodBranchBlock::new);
    public static final DeferredBlock<Block> LINDEN_BRANCH           = woodBlock("linden_branch",           WoodBranchBlock::new);
    public static final DeferredBlock<Block> MAHOGANY_BRANCH         = woodBlock("mahogany_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> MAPLE_BRANCH            = woodBlock("maple_branch",            WoodBranchBlock::new);
    public static final DeferredBlock<Block> MYRRH_BRANCH            = woodBlock("myrrh_branch",            WoodBranchBlock::new);
    public static final DeferredBlock<Block> REDWOOD_BRANCH          = woodBlock("redwood_branch",          WoodBranchBlock::new);
    public static final DeferredBlock<Block> CHESTNUT_BRANCH         = woodBlock("chestnut_branch",         WoodBranchBlock::new);
    public static final DeferredBlock<Block> WILLOW_BRANCH           = woodBlock("willow_branch",           WoodBranchBlock::new);
    public static final DeferredBlock<Block> WORMTREE_BRANCH         = woodBlock("wormtree_branch",         WoodBranchBlock::new);

    // ── Signs ──────────────────────────────────────────────────────────────
    public static final DeferredBlock<Block> WEIRWOOD_SIGN              = woodBlock("weirwood_sign",              p -> new StandingSignBlock(GotWoodTypes.WEIRWOOD, p));
    public static final DeferredBlock<Block> WEIRWOOD_WALL_SIGN         = woodBlock("weirwood_wall_sign",         p -> new WallSignBlock(GotWoodTypes.WEIRWOOD, p));
    public static final DeferredBlock<Block> ASPEN_SIGN              = woodBlock("aspen_sign",              p -> new StandingSignBlock(GotWoodTypes.ASPEN, p));
    public static final DeferredBlock<Block> ASPEN_WALL_SIGN         = woodBlock("aspen_wall_sign",         p -> new WallSignBlock(GotWoodTypes.ASPEN, p));
    public static final DeferredBlock<Block> ALDER_SIGN              = woodBlock("alder_sign",              p -> new StandingSignBlock(GotWoodTypes.ALDER, p));
    public static final DeferredBlock<Block> ALDER_WALL_SIGN         = woodBlock("alder_wall_sign",         p -> new WallSignBlock(GotWoodTypes.ALDER, p));
    public static final DeferredBlock<Block> PINE_SIGN              = woodBlock("pine_sign",              p -> new StandingSignBlock(GotWoodTypes.PINE, p));
    public static final DeferredBlock<Block> PINE_WALL_SIGN         = woodBlock("pine_wall_sign",         p -> new WallSignBlock(GotWoodTypes.PINE, p));
    public static final DeferredBlock<Block> FIR_SIGN              = woodBlock("fir_sign",              p -> new StandingSignBlock(GotWoodTypes.FIR, p));
    public static final DeferredBlock<Block> FIR_WALL_SIGN         = woodBlock("fir_wall_sign",         p -> new WallSignBlock(GotWoodTypes.FIR, p));
    public static final DeferredBlock<Block> SENTINAL_SIGN              = woodBlock("sentinal_sign",              p -> new StandingSignBlock(GotWoodTypes.SENTINAL, p));
    public static final DeferredBlock<Block> SENTINAL_WALL_SIGN         = woodBlock("sentinal_wall_sign",         p -> new WallSignBlock(GotWoodTypes.SENTINAL, p));
    public static final DeferredBlock<Block> IRONWOOD_SIGN              = woodBlock("ironwood_sign",              p -> new StandingSignBlock(GotWoodTypes.IRONWOOD, p));
    public static final DeferredBlock<Block> IRONWOOD_WALL_SIGN         = woodBlock("ironwood_wall_sign",         p -> new WallSignBlock(GotWoodTypes.IRONWOOD, p));
    public static final DeferredBlock<Block> BEECH_SIGN              = woodBlock("beech_sign",              p -> new StandingSignBlock(GotWoodTypes.BEECH, p));
    public static final DeferredBlock<Block> BEECH_WALL_SIGN         = woodBlock("beech_wall_sign",         p -> new WallSignBlock(GotWoodTypes.BEECH, p));
    public static final DeferredBlock<Block> SOLDIER_PINE_SIGN              = woodBlock("soldier_pine_sign",              p -> new StandingSignBlock(GotWoodTypes.SOLDIER_PINE, p));
    public static final DeferredBlock<Block> SOLDIER_PINE_WALL_SIGN         = woodBlock("soldier_pine_wall_sign",         p -> new WallSignBlock(GotWoodTypes.SOLDIER_PINE, p));
    public static final DeferredBlock<Block> ASH_SIGN              = woodBlock("ash_sign",              p -> new StandingSignBlock(GotWoodTypes.ASH, p));
    public static final DeferredBlock<Block> ASH_WALL_SIGN         = woodBlock("ash_wall_sign",         p -> new WallSignBlock(GotWoodTypes.ASH, p));
    public static final DeferredBlock<Block> HAWTHORN_SIGN              = woodBlock("hawthorn_sign",              p -> new StandingSignBlock(GotWoodTypes.HAWTHORN, p));
    public static final DeferredBlock<Block> HAWTHORN_WALL_SIGN         = woodBlock("hawthorn_wall_sign",         p -> new WallSignBlock(GotWoodTypes.HAWTHORN, p));
    public static final DeferredBlock<Block> BLACKBARK_SIGN              = woodBlock("blackbark_sign",              p -> new StandingSignBlock(GotWoodTypes.BLACKBARK, p));
    public static final DeferredBlock<Block> BLACKBARK_WALL_SIGN         = woodBlock("blackbark_wall_sign",         p -> new WallSignBlock(GotWoodTypes.BLACKBARK, p));
    public static final DeferredBlock<Block> BLOODWOOD_SIGN              = woodBlock("bloodwood_sign",              p -> new StandingSignBlock(GotWoodTypes.BLOODWOOD, p));
    public static final DeferredBlock<Block> BLOODWOOD_WALL_SIGN         = woodBlock("bloodwood_wall_sign",         p -> new WallSignBlock(GotWoodTypes.BLOODWOOD, p));
    public static final DeferredBlock<Block> BLUE_MAHOE_SIGN              = woodBlock("blue_mahoe_sign",              p -> new StandingSignBlock(GotWoodTypes.BLUE_MAHOE, p));
    public static final DeferredBlock<Block> BLUE_MAHOE_WALL_SIGN         = woodBlock("blue_mahoe_wall_sign",         p -> new WallSignBlock(GotWoodTypes.BLUE_MAHOE, p));
    public static final DeferredBlock<Block> COTTONWOOD_SIGN              = woodBlock("cottonwood_sign",              p -> new StandingSignBlock(GotWoodTypes.COTTONWOOD, p));
    public static final DeferredBlock<Block> COTTONWOOD_WALL_SIGN         = woodBlock("cottonwood_wall_sign",         p -> new WallSignBlock(GotWoodTypes.COTTONWOOD, p));
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_SIGN              = woodBlock("black_cottonwood_sign",              p -> new StandingSignBlock(GotWoodTypes.BLACK_COTTONWOOD, p));
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_WALL_SIGN         = woodBlock("black_cottonwood_wall_sign",         p -> new WallSignBlock(GotWoodTypes.BLACK_COTTONWOOD, p));
    public static final DeferredBlock<Block> CINNAMON_SIGN              = woodBlock("cinnamon_sign",              p -> new StandingSignBlock(GotWoodTypes.CINNAMON, p));
    public static final DeferredBlock<Block> CINNAMON_WALL_SIGN         = woodBlock("cinnamon_wall_sign",         p -> new WallSignBlock(GotWoodTypes.CINNAMON, p));
    public static final DeferredBlock<Block> CLOVE_SIGN              = woodBlock("clove_sign",              p -> new StandingSignBlock(GotWoodTypes.CLOVE, p));
    public static final DeferredBlock<Block> CLOVE_WALL_SIGN         = woodBlock("clove_wall_sign",         p -> new WallSignBlock(GotWoodTypes.CLOVE, p));
    public static final DeferredBlock<Block> EBONY_SIGN              = woodBlock("ebony_sign",              p -> new StandingSignBlock(GotWoodTypes.EBONY, p));
    public static final DeferredBlock<Block> EBONY_WALL_SIGN         = woodBlock("ebony_wall_sign",         p -> new WallSignBlock(GotWoodTypes.EBONY, p));
    public static final DeferredBlock<Block> ELM_SIGN              = woodBlock("elm_sign",              p -> new StandingSignBlock(GotWoodTypes.ELM, p));
    public static final DeferredBlock<Block> ELM_WALL_SIGN         = woodBlock("elm_wall_sign",         p -> new WallSignBlock(GotWoodTypes.ELM, p));
    public static final DeferredBlock<Block> CEDAR_SIGN              = woodBlock("cedar_sign",              p -> new StandingSignBlock(GotWoodTypes.CEDAR, p));
    public static final DeferredBlock<Block> CEDAR_WALL_SIGN         = woodBlock("cedar_wall_sign",         p -> new WallSignBlock(GotWoodTypes.CEDAR, p));
    public static final DeferredBlock<Block> APPLE_SIGN              = woodBlock("apple_sign",              p -> new StandingSignBlock(GotWoodTypes.APPLE, p));
    public static final DeferredBlock<Block> APPLE_WALL_SIGN         = woodBlock("apple_wall_sign",         p -> new WallSignBlock(GotWoodTypes.APPLE, p));
    public static final DeferredBlock<Block> GOLDENHEART_SIGN              = woodBlock("goldenheart_sign",              p -> new StandingSignBlock(GotWoodTypes.GOLDENHEART, p));
    public static final DeferredBlock<Block> GOLDENHEART_WALL_SIGN         = woodBlock("goldenheart_wall_sign",         p -> new WallSignBlock(GotWoodTypes.GOLDENHEART, p));
    public static final DeferredBlock<Block> LINDEN_SIGN              = woodBlock("linden_sign",              p -> new StandingSignBlock(GotWoodTypes.LINDEN, p));
    public static final DeferredBlock<Block> LINDEN_WALL_SIGN         = woodBlock("linden_wall_sign",         p -> new WallSignBlock(GotWoodTypes.LINDEN, p));
    public static final DeferredBlock<Block> MAHOGANY_SIGN              = woodBlock("mahogany_sign",              p -> new StandingSignBlock(GotWoodTypes.MAHOGANY, p));
    public static final DeferredBlock<Block> MAHOGANY_WALL_SIGN         = woodBlock("mahogany_wall_sign",         p -> new WallSignBlock(GotWoodTypes.MAHOGANY, p));
    public static final DeferredBlock<Block> MAPLE_SIGN              = woodBlock("maple_sign",              p -> new StandingSignBlock(GotWoodTypes.MAPLE, p));
    public static final DeferredBlock<Block> MAPLE_WALL_SIGN         = woodBlock("maple_wall_sign",         p -> new WallSignBlock(GotWoodTypes.MAPLE, p));
    public static final DeferredBlock<Block> MYRRH_SIGN              = woodBlock("myrrh_sign",              p -> new StandingSignBlock(GotWoodTypes.MYRRH, p));
    public static final DeferredBlock<Block> MYRRH_WALL_SIGN         = woodBlock("myrrh_wall_sign",         p -> new WallSignBlock(GotWoodTypes.MYRRH, p));
    public static final DeferredBlock<Block> REDWOOD_SIGN              = woodBlock("redwood_sign",              p -> new StandingSignBlock(GotWoodTypes.REDWOOD, p));
    public static final DeferredBlock<Block> REDWOOD_WALL_SIGN         = woodBlock("redwood_wall_sign",         p -> new WallSignBlock(GotWoodTypes.REDWOOD, p));
    public static final DeferredBlock<Block> CHESTNUT_SIGN              = woodBlock("chestnut_sign",              p -> new StandingSignBlock(GotWoodTypes.CHESTNUT, p));
    public static final DeferredBlock<Block> CHESTNUT_WALL_SIGN         = woodBlock("chestnut_wall_sign",         p -> new WallSignBlock(GotWoodTypes.CHESTNUT, p));
    public static final DeferredBlock<Block> WILLOW_SIGN              = woodBlock("willow_sign",              p -> new StandingSignBlock(GotWoodTypes.WILLOW, p));
    public static final DeferredBlock<Block> WILLOW_WALL_SIGN         = woodBlock("willow_wall_sign",         p -> new WallSignBlock(GotWoodTypes.WILLOW, p));
    public static final DeferredBlock<Block> WORMTREE_SIGN              = woodBlock("wormtree_sign",              p -> new StandingSignBlock(GotWoodTypes.WORMTREE, p));
    public static final DeferredBlock<Block> WORMTREE_WALL_SIGN         = woodBlock("wormtree_wall_sign",         p -> new WallSignBlock(GotWoodTypes.WORMTREE, p));
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
    public static final DeferredBlock<Block> WEIRWOOD_DOOR             = woodBlock("weirwood_door",             p -> new GotDoorBlock(GotWoodTypes.WEIRWOOD_SET, p));
    public static final DeferredBlock<Block> ASPEN_DOOR                = woodBlock("aspen_door",                p -> new GotDoorBlock(GotWoodTypes.ASPEN_SET, p));
    public static final DeferredBlock<Block> ALDER_DOOR                = woodBlock("alder_door",                p -> new GotDoorBlock(GotWoodTypes.ALDER_SET, p));
    public static final DeferredBlock<Block> PINE_DOOR                 = woodBlock("pine_door",                 p -> new GotDoorBlock(GotWoodTypes.PINE_SET, p));
    public static final DeferredBlock<Block> FIR_DOOR                  = woodBlock("fir_door",                  p -> new GotDoorBlock(GotWoodTypes.FIR_SET, p));
    public static final DeferredBlock<Block> SENTINAL_DOOR             = woodBlock("sentinal_door",             p -> new GotDoorBlock(GotWoodTypes.SENTINAL_SET, p));
    public static final DeferredBlock<Block> IRONWOOD_DOOR             = woodBlock("ironwood_door",             p -> new GotDoorBlock(GotWoodTypes.IRONWOOD_SET, p));
    public static final DeferredBlock<Block> BEECH_DOOR                = woodBlock("beech_door",                p -> new GotDoorBlock(GotWoodTypes.BEECH_SET, p));
    public static final DeferredBlock<Block> SOLDIER_PINE_DOOR         = woodBlock("soldier_pine_door",         p -> new GotDoorBlock(GotWoodTypes.SOLDIER_PINE_SET, p));
    public static final DeferredBlock<Block> ASH_DOOR                  = woodBlock("ash_door",                  p -> new GotDoorBlock(GotWoodTypes.ASH_SET, p));
    public static final DeferredBlock<Block> HAWTHORN_DOOR             = woodBlock("hawthorn_door",             p -> new GotDoorBlock(GotWoodTypes.HAWTHORN_SET, p));
    public static final DeferredBlock<Block> BLACKBARK_DOOR            = woodBlock("blackbark_door",            p -> new GotDoorBlock(GotWoodTypes.BLACKBARK_SET, p));
    public static final DeferredBlock<Block> BLOODWOOD_DOOR            = woodBlock("bloodwood_door",            p -> new GotDoorBlock(GotWoodTypes.BLOODWOOD_SET, p));
    public static final DeferredBlock<Block> BLUE_MAHOE_DOOR           = woodBlock("blue_mahoe_door",           p -> new GotDoorBlock(GotWoodTypes.BLUE_MAHOE_SET, p));
    public static final DeferredBlock<Block> COTTONWOOD_DOOR           = woodBlock("cottonwood_door",           p -> new GotDoorBlock(GotWoodTypes.COTTONWOOD_SET, p));
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_DOOR     = woodBlock("black_cottonwood_door",     p -> new GotDoorBlock(GotWoodTypes.BLACK_COTTONWOOD_SET, p));
    public static final DeferredBlock<Block> CINNAMON_DOOR             = woodBlock("cinnamon_door",             p -> new GotDoorBlock(GotWoodTypes.CINNAMON_SET, p));
    public static final DeferredBlock<Block> CLOVE_DOOR                = woodBlock("clove_door",                p -> new GotDoorBlock(GotWoodTypes.CLOVE_SET, p));
    public static final DeferredBlock<Block> EBONY_DOOR                = woodBlock("ebony_door",                p -> new GotDoorBlock(GotWoodTypes.EBONY_SET, p));
    public static final DeferredBlock<Block> ELM_DOOR                  = woodBlock("elm_door",                  p -> new GotDoorBlock(GotWoodTypes.ELM_SET, p));
    public static final DeferredBlock<Block> CEDAR_DOOR                = woodBlock("cedar_door",                p -> new GotDoorBlock(GotWoodTypes.CEDAR_SET, p));
    public static final DeferredBlock<Block> APPLE_DOOR                = woodBlock("apple_door",                p -> new GotDoorBlock(GotWoodTypes.APPLE_SET, p));
    public static final DeferredBlock<Block> GOLDENHEART_DOOR          = woodBlock("goldenheart_door",          p -> new GotDoorBlock(GotWoodTypes.GOLDENHEART_SET, p));
    public static final DeferredBlock<Block> LINDEN_DOOR               = woodBlock("linden_door",               p -> new GotDoorBlock(GotWoodTypes.LINDEN_SET, p));
    public static final DeferredBlock<Block> MAHOGANY_DOOR             = woodBlock("mahogany_door",             p -> new GotDoorBlock(GotWoodTypes.MAHOGANY_SET, p));
    public static final DeferredBlock<Block> MAPLE_DOOR                = woodBlock("maple_door",                p -> new GotDoorBlock(GotWoodTypes.MAPLE_SET, p));
    public static final DeferredBlock<Block> MYRRH_DOOR                = woodBlock("myrrh_door",                p -> new GotDoorBlock(GotWoodTypes.MYRRH_SET, p));
    public static final DeferredBlock<Block> REDWOOD_DOOR              = woodBlock("redwood_door",              p -> new GotDoorBlock(GotWoodTypes.REDWOOD_SET, p));
    public static final DeferredBlock<Block> CHESTNUT_DOOR             = woodBlock("chestnut_door",             p -> new GotDoorBlock(GotWoodTypes.CHESTNUT_SET, p));
    public static final DeferredBlock<Block> WILLOW_DOOR               = woodBlock("willow_door",               p -> new GotDoorBlock(GotWoodTypes.WILLOW_SET, p));
    public static final DeferredBlock<Block> WORMTREE_DOOR             = woodBlock("wormtree_door",             p -> new GotDoorBlock(GotWoodTypes.WORMTREE_SET, p));

    // ── Trapdoors ────────────────────────────────────────────────────────
    public static final DeferredBlock<Block> WEIRWOOD_TRAPDOOR         = woodBlock("weirwood_trapdoor",         p -> new GotTrapDoorBlock(GotWoodTypes.WEIRWOOD_SET, p));
    public static final DeferredBlock<Block> ASPEN_TRAPDOOR            = woodBlock("aspen_trapdoor",            p -> new GotTrapDoorBlock(GotWoodTypes.ASPEN_SET, p));
    public static final DeferredBlock<Block> ALDER_TRAPDOOR            = woodBlock("alder_trapdoor",            p -> new GotTrapDoorBlock(GotWoodTypes.ALDER_SET, p));
    public static final DeferredBlock<Block> PINE_TRAPDOOR             = woodBlock("pine_trapdoor",             p -> new GotTrapDoorBlock(GotWoodTypes.PINE_SET, p));
    public static final DeferredBlock<Block> FIR_TRAPDOOR              = woodBlock("fir_trapdoor",              p -> new GotTrapDoorBlock(GotWoodTypes.FIR_SET, p));
    public static final DeferredBlock<Block> SENTINAL_TRAPDOOR         = woodBlock("sentinal_trapdoor",         p -> new GotTrapDoorBlock(GotWoodTypes.SENTINAL_SET, p));
    public static final DeferredBlock<Block> IRONWOOD_TRAPDOOR         = woodBlock("ironwood_trapdoor",         p -> new GotTrapDoorBlock(GotWoodTypes.IRONWOOD_SET, p));
    public static final DeferredBlock<Block> BEECH_TRAPDOOR            = woodBlock("beech_trapdoor",            p -> new GotTrapDoorBlock(GotWoodTypes.BEECH_SET, p));
    public static final DeferredBlock<Block> SOLDIER_PINE_TRAPDOOR     = woodBlock("soldier_pine_trapdoor",     p -> new GotTrapDoorBlock(GotWoodTypes.SOLDIER_PINE_SET, p));
    public static final DeferredBlock<Block> ASH_TRAPDOOR              = woodBlock("ash_trapdoor",              p -> new GotTrapDoorBlock(GotWoodTypes.ASH_SET, p));
    public static final DeferredBlock<Block> HAWTHORN_TRAPDOOR         = woodBlock("hawthorn_trapdoor",         p -> new GotTrapDoorBlock(GotWoodTypes.HAWTHORN_SET, p));
    public static final DeferredBlock<Block> BLACKBARK_TRAPDOOR        = woodBlock("blackbark_trapdoor",        p -> new GotTrapDoorBlock(GotWoodTypes.BLACKBARK_SET, p));
    public static final DeferredBlock<Block> BLOODWOOD_TRAPDOOR        = woodBlock("bloodwood_trapdoor",        p -> new GotTrapDoorBlock(GotWoodTypes.BLOODWOOD_SET, p));
    public static final DeferredBlock<Block> BLUE_MAHOE_TRAPDOOR       = woodBlock("blue_mahoe_trapdoor",       p -> new GotTrapDoorBlock(GotWoodTypes.BLUE_MAHOE_SET, p));
    public static final DeferredBlock<Block> COTTONWOOD_TRAPDOOR       = woodBlock("cottonwood_trapdoor",       p -> new GotTrapDoorBlock(GotWoodTypes.COTTONWOOD_SET, p));
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_TRAPDOOR = woodBlock("black_cottonwood_trapdoor", p -> new GotTrapDoorBlock(GotWoodTypes.BLACK_COTTONWOOD_SET, p));
    public static final DeferredBlock<Block> CINNAMON_TRAPDOOR         = woodBlock("cinnamon_trapdoor",         p -> new GotTrapDoorBlock(GotWoodTypes.CINNAMON_SET, p));
    public static final DeferredBlock<Block> CLOVE_TRAPDOOR            = woodBlock("clove_trapdoor",            p -> new GotTrapDoorBlock(GotWoodTypes.CLOVE_SET, p));
    public static final DeferredBlock<Block> EBONY_TRAPDOOR            = woodBlock("ebony_trapdoor",            p -> new GotTrapDoorBlock(GotWoodTypes.EBONY_SET, p));
    public static final DeferredBlock<Block> ELM_TRAPDOOR              = woodBlock("elm_trapdoor",              p -> new GotTrapDoorBlock(GotWoodTypes.ELM_SET, p));
    public static final DeferredBlock<Block> CEDAR_TRAPDOOR            = woodBlock("cedar_trapdoor",            p -> new GotTrapDoorBlock(GotWoodTypes.CEDAR_SET, p));
    public static final DeferredBlock<Block> APPLE_TRAPDOOR            = woodBlock("apple_trapdoor",            p -> new GotTrapDoorBlock(GotWoodTypes.APPLE_SET, p));
    public static final DeferredBlock<Block> GOLDENHEART_TRAPDOOR      = woodBlock("goldenheart_trapdoor",      p -> new GotTrapDoorBlock(GotWoodTypes.GOLDENHEART_SET, p));
    public static final DeferredBlock<Block> LINDEN_TRAPDOOR           = woodBlock("linden_trapdoor",           p -> new GotTrapDoorBlock(GotWoodTypes.LINDEN_SET, p));
    public static final DeferredBlock<Block> MAHOGANY_TRAPDOOR         = woodBlock("mahogany_trapdoor",         p -> new GotTrapDoorBlock(GotWoodTypes.MAHOGANY_SET, p));
    public static final DeferredBlock<Block> MAPLE_TRAPDOOR            = woodBlock("maple_trapdoor",            p -> new GotTrapDoorBlock(GotWoodTypes.MAPLE_SET, p));
    public static final DeferredBlock<Block> MYRRH_TRAPDOOR            = woodBlock("myrrh_trapdoor",            p -> new GotTrapDoorBlock(GotWoodTypes.MYRRH_SET, p));
    public static final DeferredBlock<Block> REDWOOD_TRAPDOOR          = woodBlock("redwood_trapdoor",          p -> new GotTrapDoorBlock(GotWoodTypes.REDWOOD_SET, p));
    public static final DeferredBlock<Block> CHESTNUT_TRAPDOOR         = woodBlock("chestnut_trapdoor",         p -> new GotTrapDoorBlock(GotWoodTypes.CHESTNUT_SET, p));
    public static final DeferredBlock<Block> WILLOW_TRAPDOOR           = woodBlock("willow_trapdoor",           p -> new GotTrapDoorBlock(GotWoodTypes.WILLOW_SET, p));
    public static final DeferredBlock<Block> WORMTREE_TRAPDOOR         = woodBlock("wormtree_trapdoor",         p -> new GotTrapDoorBlock(GotWoodTypes.WORMTREE_SET, p));

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

    // ── Ores
    // Stone-tier ores (hardness 3, resistance 3) — copper, tin, amber, topaz
    public static final DeferredBlock<Block> COPPER_ORE    = oreStone("copper_ore");
    public static final DeferredBlock<Block> TIN_ORE       = oreStone("tin_ore");
    public static final DeferredBlock<Block> AMBER_ORE     = oreStone("amber_ore");
    public static final DeferredBlock<Block> TOPAZ_ORE     = oreStone("topaz_ore");
    // Iron-tier ores (hardness 3, resistance 3, like vanilla iron ore) — silver, amethyst, opal, ruby, sapphire, dragonglass
    public static final DeferredBlock<Block> SILVER_ORE      = oreIron("silver_ore");
    public static final DeferredBlock<Block> AMETHYST_ORE    = oreIron("amethyst_ore");
    public static final DeferredBlock<Block> OPAL_ORE        = oreIron("opal_ore");
    public static final DeferredBlock<Block> RUBY_ORE        = oreIron("ruby_ore");
    public static final DeferredBlock<Block> SAPPHIRE_ORE    = oreIron("sapphire_ore");
    public static final DeferredBlock<Block> DRAGONGLASS_ORE = oreIron("dragonglass");
    // Diamond-tier ore (hardness 3, resistance 3, like vanilla diamond ore)
    public static final DeferredBlock<Block> VALYRIAN_STEEL_ORE = oreDiamond("valyrian_ore");

    // ── Apple Tree
    public static final DeferredBlock<Block> APPLE_LOG            = logBlock("apple_log",            AppleLogBlock::new);
    public static final DeferredBlock<Block> APPLE_WOOD           = logBlock("apple_wood",           AppleWoodBlock::new);
    public static final DeferredBlock<Block> APPLE_PLANKS         = woodBlock("apple_planks",         ApplePlanksBlock::new);
    public static final DeferredBlock<Block> APPLE_LEAVES         = woodBlock("apple_leaves",         AppleLeavesBlock::new);
    public static final DeferredBlock<Block> APPLE_STAIRS         = woodBlock("apple_stairs",         AppleStairsBlock::new);
    public static final DeferredBlock<Block> APPLE_SLAB           = woodBlock("apple_slab",           AppleSlabBlock::new);
    public static final DeferredBlock<Block> APPLE_FENCE          = woodBlock("apple_fence",          AppleFenceBlock::new);
    public static final DeferredBlock<Block> APPLE_FENCE_GATE     = woodBlock("apple_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.APPLE, p));
    public static final DeferredBlock<Block> APPLE_PRESSURE_PLATE = woodBlock("apple_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.APPLE_SET, p));
    public static final DeferredBlock<Block> APPLE_BUTTON         = woodBlock("apple_button",         p -> new GotButtonBlock(GotWoodTypes.APPLE_SET, p));

    // ── Goldenheart Tree
    public static final DeferredBlock<Block> GOLDENHEART_LOG            = logBlock("goldenheart_log",            GoldenheartLogBlock::new);
    public static final DeferredBlock<Block> GOLDENHEART_WOOD           = logBlock("goldenheart_wood",           GoldenheartWoodBlock::new);
    public static final DeferredBlock<Block> GOLDENHEART_PLANKS         = woodBlock("goldenheart_planks",         GoldenheartPlanksBlock::new);
    public static final DeferredBlock<Block> GOLDENHEART_LEAVES         = woodBlock("goldenheart_leaves",         GoldenheartLeavesBlock::new);
    public static final DeferredBlock<Block> GOLDENHEART_STAIRS         = woodBlock("goldenheart_stairs",         GoldenheartStairsBlock::new);
    public static final DeferredBlock<Block> GOLDENHEART_SLAB           = woodBlock("goldenheart_slab",           GoldenheartSlabBlock::new);
    public static final DeferredBlock<Block> GOLDENHEART_FENCE          = woodBlock("goldenheart_fence",          GoldenheartFenceBlock::new);
    public static final DeferredBlock<Block> GOLDENHEART_FENCE_GATE     = woodBlock("goldenheart_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.GOLDENHEART, p));
    public static final DeferredBlock<Block> GOLDENHEART_PRESSURE_PLATE = woodBlock("goldenheart_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.GOLDENHEART_SET, p));
    public static final DeferredBlock<Block> GOLDENHEART_BUTTON         = woodBlock("goldenheart_button",         p -> new GotButtonBlock(GotWoodTypes.GOLDENHEART_SET, p));

    // ── Linden Tree
    public static final DeferredBlock<Block> LINDEN_LOG            = logBlock("linden_log",            LindenLogBlock::new);
    public static final DeferredBlock<Block> LINDEN_WOOD           = logBlock("linden_wood",           LindenWoodBlock::new);
    public static final DeferredBlock<Block> LINDEN_PLANKS         = woodBlock("linden_planks",         LindenPlanksBlock::new);
    public static final DeferredBlock<Block> LINDEN_LEAVES         = woodBlock("linden_leaves",         LindenLeavesBlock::new);
    public static final DeferredBlock<Block> LINDEN_STAIRS         = woodBlock("linden_stairs",         LindenStairsBlock::new);
    public static final DeferredBlock<Block> LINDEN_SLAB           = woodBlock("linden_slab",           LindenSlabBlock::new);
    public static final DeferredBlock<Block> LINDEN_FENCE          = woodBlock("linden_fence",          LindenFenceBlock::new);
    public static final DeferredBlock<Block> LINDEN_FENCE_GATE     = woodBlock("linden_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.LINDEN, p));
    public static final DeferredBlock<Block> LINDEN_PRESSURE_PLATE = woodBlock("linden_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.LINDEN_SET, p));
    public static final DeferredBlock<Block> LINDEN_BUTTON         = woodBlock("linden_button",         p -> new GotButtonBlock(GotWoodTypes.LINDEN_SET, p));

    // ── Mahogany Tree
    public static final DeferredBlock<Block> MAHOGANY_LOG            = logBlock("mahogany_log",            MahoganyLogBlock::new);
    public static final DeferredBlock<Block> MAHOGANY_WOOD           = logBlock("mahogany_wood",           MahoganyWoodBlock::new);
    public static final DeferredBlock<Block> MAHOGANY_PLANKS         = woodBlock("mahogany_planks",         MahoganyPlanksBlock::new);
    public static final DeferredBlock<Block> MAHOGANY_LEAVES         = woodBlock("mahogany_leaves",         MahoganyLeavesBlock::new);
    public static final DeferredBlock<Block> MAHOGANY_STAIRS         = woodBlock("mahogany_stairs",         MahoganyStairsBlock::new);
    public static final DeferredBlock<Block> MAHOGANY_SLAB           = woodBlock("mahogany_slab",           MahoganySlabBlock::new);
    public static final DeferredBlock<Block> MAHOGANY_FENCE          = woodBlock("mahogany_fence",          MahoganyFenceBlock::new);
    public static final DeferredBlock<Block> MAHOGANY_FENCE_GATE     = woodBlock("mahogany_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.MAHOGANY, p));
    public static final DeferredBlock<Block> MAHOGANY_PRESSURE_PLATE = woodBlock("mahogany_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.MAHOGANY_SET, p));
    public static final DeferredBlock<Block> MAHOGANY_BUTTON         = woodBlock("mahogany_button",         p -> new GotButtonBlock(GotWoodTypes.MAHOGANY_SET, p));

    // ── Maple Tree
    public static final DeferredBlock<Block> MAPLE_LOG            = logBlock("maple_log",            MapleLogBlock::new);
    public static final DeferredBlock<Block> MAPLE_WOOD           = logBlock("maple_wood",           MapleWoodBlock::new);
    public static final DeferredBlock<Block> MAPLE_PLANKS         = woodBlock("maple_planks",         MaplePlanksBlock::new);
    public static final DeferredBlock<Block> MAPLE_LEAVES         = woodBlock("maple_leaves",         MapleLeavesBlock::new);
    public static final DeferredBlock<Block> MAPLE_STAIRS         = woodBlock("maple_stairs",         MapleStairsBlock::new);
    public static final DeferredBlock<Block> MAPLE_SLAB           = woodBlock("maple_slab",           MapleSlabBlock::new);
    public static final DeferredBlock<Block> MAPLE_FENCE          = woodBlock("maple_fence",          MapleFenceBlock::new);
    public static final DeferredBlock<Block> MAPLE_FENCE_GATE     = woodBlock("maple_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.MAPLE, p));
    public static final DeferredBlock<Block> MAPLE_PRESSURE_PLATE = woodBlock("maple_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.MAPLE_SET, p));
    public static final DeferredBlock<Block> MAPLE_BUTTON         = woodBlock("maple_button",         p -> new GotButtonBlock(GotWoodTypes.MAPLE_SET, p));

    // ── Myrrh Tree
    public static final DeferredBlock<Block> MYRRH_LOG            = logBlock("myrrh_log",            MyrrhLogBlock::new);
    public static final DeferredBlock<Block> MYRRH_WOOD           = logBlock("myrrh_wood",           MyrrhWoodBlock::new);
    public static final DeferredBlock<Block> MYRRH_PLANKS         = woodBlock("myrrh_planks",         MyrrhPlanksBlock::new);
    public static final DeferredBlock<Block> MYRRH_LEAVES         = woodBlock("myrrh_leaves",         MyrrhLeavesBlock::new);
    public static final DeferredBlock<Block> MYRRH_STAIRS         = woodBlock("myrrh_stairs",         MyrrhStairsBlock::new);
    public static final DeferredBlock<Block> MYRRH_SLAB           = woodBlock("myrrh_slab",           MyrrhSlabBlock::new);
    public static final DeferredBlock<Block> MYRRH_FENCE          = woodBlock("myrrh_fence",          MyrrhFenceBlock::new);
    public static final DeferredBlock<Block> MYRRH_FENCE_GATE     = woodBlock("myrrh_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.MYRRH, p));
    public static final DeferredBlock<Block> MYRRH_PRESSURE_PLATE = woodBlock("myrrh_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.MYRRH_SET, p));
    public static final DeferredBlock<Block> MYRRH_BUTTON         = woodBlock("myrrh_button",         p -> new GotButtonBlock(GotWoodTypes.MYRRH_SET, p));
    // ── Redwood Tree
    public static final DeferredBlock<Block> REDWOOD_LOG            = logBlock("redwood_log",            RedwoodLogBlock::new);
    public static final DeferredBlock<Block> REDWOOD_WOOD           = logBlock("redwood_wood",           RedwoodWoodBlock::new);
    public static final DeferredBlock<Block> REDWOOD_PLANKS         = woodBlock("redwood_planks",         RedwoodPlanksBlock::new);
    public static final DeferredBlock<Block> REDWOOD_LEAVES         = woodBlock("redwood_leaves",         RedwoodLeavesBlock::new);
    public static final DeferredBlock<Block> REDWOOD_STAIRS         = woodBlock("redwood_stairs",         RedwoodStairsBlock::new);
    public static final DeferredBlock<Block> REDWOOD_SLAB           = woodBlock("redwood_slab",           RedwoodSlabBlock::new);
    public static final DeferredBlock<Block> REDWOOD_FENCE          = woodBlock("redwood_fence",          RedwoodFenceBlock::new);
    public static final DeferredBlock<Block> REDWOOD_FENCE_GATE     = woodBlock("redwood_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.REDWOOD, p));
    public static final DeferredBlock<Block> REDWOOD_PRESSURE_PLATE = woodBlock("redwood_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.REDWOOD_SET, p));
    public static final DeferredBlock<Block> REDWOOD_BUTTON         = woodBlock("redwood_button",         p -> new GotButtonBlock(GotWoodTypes.REDWOOD_SET, p));

    // ── Chestnut Tree
    public static final DeferredBlock<Block> CHESTNUT_LOG            = logBlock("chestnut_log",            ChestnutLogBlock::new);
    public static final DeferredBlock<Block> CHESTNUT_WOOD           = logBlock("chestnut_wood",           ChestnutWoodBlock::new);
    public static final DeferredBlock<Block> CHESTNUT_PLANKS         = woodBlock("chestnut_planks",         ChestnutPlanksBlock::new);
    public static final DeferredBlock<Block> CHESTNUT_LEAVES         = woodBlock("chestnut_leaves",         ChestnutLeavesBlock::new);
    public static final DeferredBlock<Block> CHESTNUT_STAIRS         = woodBlock("chestnut_stairs",         ChestnutStairsBlock::new);
    public static final DeferredBlock<Block> CHESTNUT_SLAB           = woodBlock("chestnut_slab",           ChestnutSlabBlock::new);
    public static final DeferredBlock<Block> CHESTNUT_FENCE          = woodBlock("chestnut_fence",          ChestnutFenceBlock::new);
    public static final DeferredBlock<Block> CHESTNUT_FENCE_GATE     = woodBlock("chestnut_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.CHESTNUT, p));
    public static final DeferredBlock<Block> CHESTNUT_PRESSURE_PLATE = woodBlock("chestnut_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.CHESTNUT_SET, p));
    public static final DeferredBlock<Block> CHESTNUT_BUTTON         = woodBlock("chestnut_button",         p -> new GotButtonBlock(GotWoodTypes.CHESTNUT_SET, p));

    // ── Willow Tree
    public static final DeferredBlock<Block> WILLOW_LOG            = logBlock("willow_log",            WillowLogBlock::new);
    public static final DeferredBlock<Block> WILLOW_WOOD           = logBlock("willow_wood",           WillowWoodBlock::new);
    public static final DeferredBlock<Block> WILLOW_PLANKS         = woodBlock("willow_planks",         WillowPlanksBlock::new);
    public static final DeferredBlock<Block> WILLOW_LEAVES         = woodBlock("willow_leaves",         WillowLeavesBlock::new);
    public static final DeferredBlock<Block> WILLOW_STAIRS         = woodBlock("willow_stairs",         WillowStairsBlock::new);
    public static final DeferredBlock<Block> WILLOW_SLAB           = woodBlock("willow_slab",           WillowSlabBlock::new);
    public static final DeferredBlock<Block> WILLOW_FENCE          = woodBlock("willow_fence",          WillowFenceBlock::new);
    public static final DeferredBlock<Block> WILLOW_FENCE_GATE     = woodBlock("willow_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.WILLOW, p));
    public static final DeferredBlock<Block> WILLOW_PRESSURE_PLATE = woodBlock("willow_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.WILLOW_SET, p));
    public static final DeferredBlock<Block> WILLOW_BUTTON         = woodBlock("willow_button",         p -> new GotButtonBlock(GotWoodTypes.WILLOW_SET, p));

    // ── Wormtree Tree
    public static final DeferredBlock<Block> WORMTREE_LOG            = logBlock("wormtree_log",            WormtreeLogBlock::new);
    public static final DeferredBlock<Block> WORMTREE_WOOD           = logBlock("wormtree_wood",           WormtreeWoodBlock::new);
    public static final DeferredBlock<Block> WORMTREE_PLANKS         = woodBlock("wormtree_planks",         WormtreePlanksBlock::new);
    public static final DeferredBlock<Block> WORMTREE_LEAVES         = woodBlock("wormtree_leaves",         WormtreeLeavesBlock::new);
    public static final DeferredBlock<Block> WORMTREE_STAIRS         = woodBlock("wormtree_stairs",         WormtreeStairsBlock::new);
    public static final DeferredBlock<Block> WORMTREE_SLAB           = woodBlock("wormtree_slab",           WormtreeSlabBlock::new);
    public static final DeferredBlock<Block> WORMTREE_FENCE          = woodBlock("wormtree_fence",          WormtreeFenceBlock::new);
    public static final DeferredBlock<Block> WORMTREE_FENCE_GATE     = woodBlock("wormtree_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.WORMTREE, p));
    public static final DeferredBlock<Block> WORMTREE_PRESSURE_PLATE = woodBlock("wormtree_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.WORMTREE_SET, p));
    public static final DeferredBlock<Block> WORMTREE_BUTTON         = woodBlock("wormtree_button",         p -> new GotButtonBlock(GotWoodTypes.WORMTREE_SET, p));

    // ── Basalt ──────────────────────────────────────────────────────────
    public static final DeferredBlock<Block> BASALT_ROCK = register("basalt_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> BASALT_BRICK = register("basalt_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> CRACKED_BASALT_BRICK = register("cracked_basalt_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_BASALT_BRICK = register("mossy_basalt_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> BASALT_COBBLESTONE = register("basalt_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_BASALT_COBBLESTONE = register("mossy_basalt_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> SMOOTH_BASALT_ROCK = register("smooth_basalt_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> BASALT_PILLAR = register("basalt_pillar", RegionalRockPillarBlock::new);
    public static final DeferredBlock<Block> BASALT_ROCK_SLAB = register("basalt_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> BASALT_ROCK_STAIRS = register("basalt_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> BASALT_ROCK_WALL = register("basalt_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> BASALT_ROCK_BUTTON = register("basalt_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> BASALT_ROCK_PRESSURE_PLATE = register("basalt_rock_pressure_plate", RegionalRockPressurePlateBlock::new);
    public static final DeferredBlock<Block> BASALT_BRICK_SLAB = register("basalt_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> BASALT_BRICK_STAIRS = register("basalt_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> BASALT_BRICK_WALL = register("basalt_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> CRACKED_BASALT_BRICK_SLAB = register("cracked_basalt_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_BASALT_BRICK_STAIRS = register("cracked_basalt_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_BASALT_BRICK_WALL = register("cracked_basalt_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_BASALT_BRICK_SLAB = register("mossy_basalt_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_BASALT_BRICK_STAIRS = register("mossy_basalt_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_BASALT_BRICK_WALL = register("mossy_basalt_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> BASALT_COBBLESTONE_SLAB = register("basalt_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> BASALT_COBBLESTONE_STAIRS = register("basalt_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> BASALT_COBBLESTONE_WALL = register("basalt_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_BASALT_COBBLESTONE_SLAB = register("mossy_basalt_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_BASALT_COBBLESTONE_STAIRS = register("mossy_basalt_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_BASALT_COBBLESTONE_WALL = register("mossy_basalt_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> SMOOTH_BASALT_ROCK_SLAB = register("smooth_basalt_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> SMOOTH_BASALT_ROCK_STAIRS = register("smooth_basalt_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> SMOOTH_BASALT_ROCK_WALL = register("smooth_basalt_rock_wall", RegionalRockWallBlock::new);

    // ── Granite ──────────────────────────────────────────────────────────
    public static final DeferredBlock<Block> GRANITE_ROCK = register("granite_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> GRANITE_BRICK = register("granite_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> CRACKED_GRANITE_BRICK = register("cracked_granite_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_GRANITE_BRICK = register("mossy_granite_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> GRANITE_COBBLESTONE = register("granite_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_GRANITE_COBBLESTONE = register("mossy_granite_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> SMOOTH_GRANITE_ROCK = register("smooth_granite_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> GRANITE_PILLAR = register("granite_pillar", RegionalRockPillarBlock::new);
    public static final DeferredBlock<Block> GRANITE_ROCK_SLAB = register("granite_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> GRANITE_ROCK_STAIRS = register("granite_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> GRANITE_ROCK_WALL = register("granite_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> GRANITE_ROCK_BUTTON = register("granite_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> GRANITE_ROCK_PRESSURE_PLATE = register("granite_rock_pressure_plate", RegionalRockPressurePlateBlock::new);
    public static final DeferredBlock<Block> GRANITE_BRICK_SLAB = register("granite_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> GRANITE_BRICK_STAIRS = register("granite_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> GRANITE_BRICK_WALL = register("granite_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> CRACKED_GRANITE_BRICK_SLAB = register("cracked_granite_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_GRANITE_BRICK_STAIRS = register("cracked_granite_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_GRANITE_BRICK_WALL = register("cracked_granite_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_GRANITE_BRICK_SLAB = register("mossy_granite_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_GRANITE_BRICK_STAIRS = register("mossy_granite_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_GRANITE_BRICK_WALL = register("mossy_granite_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> GRANITE_COBBLESTONE_SLAB = register("granite_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> GRANITE_COBBLESTONE_STAIRS = register("granite_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> GRANITE_COBBLESTONE_WALL = register("granite_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_GRANITE_COBBLESTONE_SLAB = register("mossy_granite_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_GRANITE_COBBLESTONE_STAIRS = register("mossy_granite_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_GRANITE_COBBLESTONE_WALL = register("mossy_granite_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> SMOOTH_GRANITE_ROCK_SLAB = register("smooth_granite_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> SMOOTH_GRANITE_ROCK_STAIRS = register("smooth_granite_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> SMOOTH_GRANITE_ROCK_WALL = register("smooth_granite_rock_wall", RegionalRockWallBlock::new);

    // ── Limestone ──────────────────────────────────────────────────────────
    public static final DeferredBlock<Block> LIMESTONE_ROCK = register("limestone_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> LIMESTONE_BRICK = register("limestone_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> CRACKED_LIMESTONE_BRICK = register("cracked_limestone_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_LIMESTONE_BRICK = register("mossy_limestone_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> LIMESTONE_COBBLESTONE = register("limestone_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_LIMESTONE_COBBLESTONE = register("mossy_limestone_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> SMOOTH_LIMESTONE_ROCK = register("smooth_limestone_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> LIMESTONE_PILLAR = register("limestone_pillar", RegionalRockPillarBlock::new);
    public static final DeferredBlock<Block> LIMESTONE_ROCK_SLAB = register("limestone_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> LIMESTONE_ROCK_STAIRS = register("limestone_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> LIMESTONE_ROCK_WALL = register("limestone_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> LIMESTONE_ROCK_BUTTON = register("limestone_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> LIMESTONE_ROCK_PRESSURE_PLATE = register("limestone_rock_pressure_plate", RegionalRockPressurePlateBlock::new);
    public static final DeferredBlock<Block> LIMESTONE_BRICK_SLAB = register("limestone_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> LIMESTONE_BRICK_STAIRS = register("limestone_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> LIMESTONE_BRICK_WALL = register("limestone_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> CRACKED_LIMESTONE_BRICK_SLAB = register("cracked_limestone_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_LIMESTONE_BRICK_STAIRS = register("cracked_limestone_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_LIMESTONE_BRICK_WALL = register("cracked_limestone_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_LIMESTONE_BRICK_SLAB = register("mossy_limestone_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_LIMESTONE_BRICK_STAIRS = register("mossy_limestone_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_LIMESTONE_BRICK_WALL = register("mossy_limestone_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> LIMESTONE_COBBLESTONE_SLAB = register("limestone_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> LIMESTONE_COBBLESTONE_STAIRS = register("limestone_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> LIMESTONE_COBBLESTONE_WALL = register("limestone_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_LIMESTONE_COBBLESTONE_SLAB = register("mossy_limestone_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_LIMESTONE_COBBLESTONE_STAIRS = register("mossy_limestone_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_LIMESTONE_COBBLESTONE_WALL = register("mossy_limestone_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> SMOOTH_LIMESTONE_ROCK_SLAB = register("smooth_limestone_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> SMOOTH_LIMESTONE_ROCK_STAIRS = register("smooth_limestone_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> SMOOTH_LIMESTONE_ROCK_WALL = register("smooth_limestone_rock_wall", RegionalRockWallBlock::new);

    // ── Sandstone ──────────────────────────────────────────────────────────
    public static final DeferredBlock<Block> SANDSTONE_ROCK = register("sandstone_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> SANDSTONE_BRICK = register("sandstone_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> CRACKED_SANDSTONE_BRICK = register("cracked_sandstone_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_SANDSTONE_BRICK = register("mossy_sandstone_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> SANDSTONE_COBBLESTONE = register("sandstone_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_SANDSTONE_COBBLESTONE = register("mossy_sandstone_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> SMOOTH_SANDSTONE_ROCK = register("smooth_sandstone_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> SANDSTONE_PILLAR = register("sandstone_pillar", RegionalRockPillarBlock::new);
    public static final DeferredBlock<Block> SANDSTONE_ROCK_SLAB = register("sandstone_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> SANDSTONE_ROCK_STAIRS = register("sandstone_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> SANDSTONE_ROCK_WALL = register("sandstone_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> SANDSTONE_ROCK_BUTTON = register("sandstone_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> SANDSTONE_ROCK_PRESSURE_PLATE = register("sandstone_rock_pressure_plate", RegionalRockPressurePlateBlock::new);
    public static final DeferredBlock<Block> SANDSTONE_BRICK_SLAB = register("sandstone_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> SANDSTONE_BRICK_STAIRS = register("sandstone_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> SANDSTONE_BRICK_WALL = register("sandstone_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> CRACKED_SANDSTONE_BRICK_SLAB = register("cracked_sandstone_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_SANDSTONE_BRICK_STAIRS = register("cracked_sandstone_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_SANDSTONE_BRICK_WALL = register("cracked_sandstone_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_SANDSTONE_BRICK_SLAB = register("mossy_sandstone_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_SANDSTONE_BRICK_STAIRS = register("mossy_sandstone_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_SANDSTONE_BRICK_WALL = register("mossy_sandstone_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> SANDSTONE_COBBLESTONE_SLAB = register("sandstone_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> SANDSTONE_COBBLESTONE_STAIRS = register("sandstone_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> SANDSTONE_COBBLESTONE_WALL = register("sandstone_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_SANDSTONE_COBBLESTONE_SLAB = register("mossy_sandstone_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_SANDSTONE_COBBLESTONE_STAIRS = register("mossy_sandstone_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_SANDSTONE_COBBLESTONE_WALL = register("mossy_sandstone_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> SMOOTH_SANDSTONE_ROCK_SLAB = register("smooth_sandstone_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> SMOOTH_SANDSTONE_ROCK_STAIRS = register("smooth_sandstone_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> SMOOTH_SANDSTONE_ROCK_WALL = register("smooth_sandstone_rock_wall", RegionalRockWallBlock::new);

    // ── Red Sandstone ──────────────────────────────────────────────────────────
    public static final DeferredBlock<Block> RED_SANDSTONE_ROCK = register("red_sandstone_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> RED_SANDSTONE_BRICK = register("red_sandstone_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> CRACKED_RED_SANDSTONE_BRICK = register("cracked_red_sandstone_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_RED_SANDSTONE_BRICK = register("mossy_red_sandstone_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> RED_SANDSTONE_COBBLESTONE = register("red_sandstone_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_RED_SANDSTONE_COBBLESTONE = register("mossy_red_sandstone_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> SMOOTH_RED_SANDSTONE_ROCK = register("smooth_red_sandstone_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> RED_SANDSTONE_PILLAR = register("red_sandstone_pillar", RegionalRockPillarBlock::new);
    public static final DeferredBlock<Block> RED_SANDSTONE_ROCK_SLAB = register("red_sandstone_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> RED_SANDSTONE_ROCK_STAIRS = register("red_sandstone_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> RED_SANDSTONE_ROCK_WALL = register("red_sandstone_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> RED_SANDSTONE_ROCK_BUTTON = register("red_sandstone_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> RED_SANDSTONE_ROCK_PRESSURE_PLATE = register("red_sandstone_rock_pressure_plate", RegionalRockPressurePlateBlock::new);
    public static final DeferredBlock<Block> RED_SANDSTONE_BRICK_SLAB = register("red_sandstone_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> RED_SANDSTONE_BRICK_STAIRS = register("red_sandstone_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> RED_SANDSTONE_BRICK_WALL = register("red_sandstone_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> CRACKED_RED_SANDSTONE_BRICK_SLAB = register("cracked_red_sandstone_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_RED_SANDSTONE_BRICK_STAIRS = register("cracked_red_sandstone_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_RED_SANDSTONE_BRICK_WALL = register("cracked_red_sandstone_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_RED_SANDSTONE_BRICK_SLAB = register("mossy_red_sandstone_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_RED_SANDSTONE_BRICK_STAIRS = register("mossy_red_sandstone_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_RED_SANDSTONE_BRICK_WALL = register("mossy_red_sandstone_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> RED_SANDSTONE_COBBLESTONE_SLAB = register("red_sandstone_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> RED_SANDSTONE_COBBLESTONE_STAIRS = register("red_sandstone_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> RED_SANDSTONE_COBBLESTONE_WALL = register("red_sandstone_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_RED_SANDSTONE_COBBLESTONE_SLAB = register("mossy_red_sandstone_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_RED_SANDSTONE_COBBLESTONE_STAIRS = register("mossy_red_sandstone_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_RED_SANDSTONE_COBBLESTONE_WALL = register("mossy_red_sandstone_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> SMOOTH_RED_SANDSTONE_ROCK_SLAB = register("smooth_red_sandstone_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> SMOOTH_RED_SANDSTONE_ROCK_STAIRS = register("smooth_red_sandstone_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> SMOOTH_RED_SANDSTONE_ROCK_WALL = register("smooth_red_sandstone_rock_wall", RegionalRockWallBlock::new);

    // ── Slate ──────────────────────────────────────────────────────────
    public static final DeferredBlock<Block> SLATE_ROCK = register("slate_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> SLATE_BRICK = register("slate_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> CRACKED_SLATE_BRICK = register("cracked_slate_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_SLATE_BRICK = register("mossy_slate_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> SLATE_COBBLESTONE = register("slate_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_SLATE_COBBLESTONE = register("mossy_slate_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> SMOOTH_SLATE_ROCK = register("smooth_slate_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> SLATE_PILLAR = register("slate_pillar", RegionalRockPillarBlock::new);
    public static final DeferredBlock<Block> SLATE_ROCK_SLAB = register("slate_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> SLATE_ROCK_STAIRS = register("slate_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> SLATE_ROCK_WALL = register("slate_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> SLATE_ROCK_BUTTON = register("slate_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> SLATE_ROCK_PRESSURE_PLATE = register("slate_rock_pressure_plate", RegionalRockPressurePlateBlock::new);
    public static final DeferredBlock<Block> SLATE_BRICK_SLAB = register("slate_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> SLATE_BRICK_STAIRS = register("slate_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> SLATE_BRICK_WALL = register("slate_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> CRACKED_SLATE_BRICK_SLAB = register("cracked_slate_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_SLATE_BRICK_STAIRS = register("cracked_slate_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_SLATE_BRICK_WALL = register("cracked_slate_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_SLATE_BRICK_SLAB = register("mossy_slate_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_SLATE_BRICK_STAIRS = register("mossy_slate_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_SLATE_BRICK_WALL = register("mossy_slate_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> SLATE_COBBLESTONE_SLAB = register("slate_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> SLATE_COBBLESTONE_STAIRS = register("slate_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> SLATE_COBBLESTONE_WALL = register("slate_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_SLATE_COBBLESTONE_SLAB = register("mossy_slate_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_SLATE_COBBLESTONE_STAIRS = register("mossy_slate_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_SLATE_COBBLESTONE_WALL = register("mossy_slate_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> SMOOTH_SLATE_ROCK_SLAB = register("smooth_slate_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> SMOOTH_SLATE_ROCK_STAIRS = register("smooth_slate_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> SMOOTH_SLATE_ROCK_WALL = register("smooth_slate_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> SLATE_SHINGLES = register("slate_shingles", RegionalRockBlock::new);

    // ── Oily Black Stone blocks ───────────────────────────────────────────────
    public static final DeferredBlock<Block> OILY_BLACK_ROCK = register("oily_black_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> OILY_BLACK_BRICK = register("oily_black_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> CRACKED_OILY_BLACK_BRICK = register("cracked_oily_black_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_OILY_BLACK_BRICK = register("mossy_oily_black_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> OILY_BLACK_COBBLESTONE = register("oily_black_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_OILY_BLACK_COBBLESTONE = register("mossy_oily_black_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> SMOOTH_OILY_BLACK_ROCK = register("smooth_oily_black_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> OILY_BLACK_PILLAR = register("oily_black_pillar", RegionalRockPillarBlock::new);
    public static final DeferredBlock<Block> OILY_BLACK_ROCK_SLAB = register("oily_black_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> OILY_BLACK_ROCK_STAIRS = register("oily_black_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> OILY_BLACK_ROCK_WALL = register("oily_black_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> OILY_BLACK_ROCK_BUTTON = register("oily_black_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> OILY_BLACK_ROCK_PRESSURE_PLATE = register("oily_black_rock_pressure_plate", RegionalRockPressurePlateBlock::new);
    public static final DeferredBlock<Block> OILY_BLACK_BRICK_SLAB = register("oily_black_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> OILY_BLACK_BRICK_STAIRS = register("oily_black_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> OILY_BLACK_BRICK_WALL = register("oily_black_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> CRACKED_OILY_BLACK_BRICK_SLAB = register("cracked_oily_black_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_OILY_BLACK_BRICK_STAIRS = register("cracked_oily_black_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_OILY_BLACK_BRICK_WALL = register("cracked_oily_black_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_OILY_BLACK_BRICK_SLAB = register("mossy_oily_black_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_OILY_BLACK_BRICK_STAIRS = register("mossy_oily_black_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_OILY_BLACK_BRICK_WALL = register("mossy_oily_black_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> OILY_BLACK_COBBLESTONE_SLAB = register("oily_black_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> OILY_BLACK_COBBLESTONE_STAIRS = register("oily_black_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> OILY_BLACK_COBBLESTONE_WALL = register("oily_black_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_OILY_BLACK_COBBLESTONE_SLAB = register("mossy_oily_black_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_OILY_BLACK_COBBLESTONE_STAIRS = register("mossy_oily_black_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_OILY_BLACK_COBBLESTONE_WALL = register("mossy_oily_black_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> SMOOTH_OILY_BLACK_ROCK_SLAB = register("smooth_oily_black_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> SMOOTH_OILY_BLACK_ROCK_STAIRS = register("smooth_oily_black_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> SMOOTH_OILY_BLACK_ROCK_WALL = register("smooth_oily_black_rock_wall", RegionalRockWallBlock::new);

    // ── Fused Black Stone blocks ──────────────────────────────────────────────
    public static final DeferredBlock<Block> FUSED_BLACK_ROCK = register("fused_black_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> FUSED_BLACK_BRICK = register("fused_black_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> CRACKED_FUSED_BLACK_BRICK = register("cracked_fused_black_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_FUSED_BLACK_BRICK = register("mossy_fused_black_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> FUSED_BLACK_COBBLESTONE = register("fused_black_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_FUSED_BLACK_COBBLESTONE = register("mossy_fused_black_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> SMOOTH_FUSED_BLACK_ROCK = register("smooth_fused_black_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> FUSED_BLACK_PILLAR = register("fused_black_pillar", RegionalRockPillarBlock::new);
    public static final DeferredBlock<Block> FUSED_BLACK_ROCK_SLAB = register("fused_black_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> FUSED_BLACK_ROCK_STAIRS = register("fused_black_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> FUSED_BLACK_ROCK_WALL = register("fused_black_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> FUSED_BLACK_ROCK_BUTTON = register("fused_black_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> FUSED_BLACK_ROCK_PRESSURE_PLATE = register("fused_black_rock_pressure_plate", RegionalRockPressurePlateBlock::new);
    public static final DeferredBlock<Block> FUSED_BLACK_BRICK_SLAB = register("fused_black_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> FUSED_BLACK_BRICK_STAIRS = register("fused_black_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> FUSED_BLACK_BRICK_WALL = register("fused_black_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> CRACKED_FUSED_BLACK_BRICK_SLAB = register("cracked_fused_black_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_FUSED_BLACK_BRICK_STAIRS = register("cracked_fused_black_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_FUSED_BLACK_BRICK_WALL = register("cracked_fused_black_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_FUSED_BLACK_BRICK_SLAB = register("mossy_fused_black_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_FUSED_BLACK_BRICK_STAIRS = register("mossy_fused_black_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_FUSED_BLACK_BRICK_WALL = register("mossy_fused_black_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> FUSED_BLACK_COBBLESTONE_SLAB = register("fused_black_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> FUSED_BLACK_COBBLESTONE_STAIRS = register("fused_black_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> FUSED_BLACK_COBBLESTONE_WALL = register("fused_black_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_FUSED_BLACK_COBBLESTONE_SLAB = register("mossy_fused_black_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_FUSED_BLACK_COBBLESTONE_STAIRS = register("mossy_fused_black_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_FUSED_BLACK_COBBLESTONE_WALL = register("mossy_fused_black_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> SMOOTH_FUSED_BLACK_ROCK_SLAB = register("smooth_fused_black_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> SMOOTH_FUSED_BLACK_ROCK_STAIRS = register("smooth_fused_black_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> SMOOTH_FUSED_BLACK_ROCK_WALL = register("smooth_fused_black_rock_wall", RegionalRockWallBlock::new);

    // ── Marble blocks ─────────────────────────────────────────────────────────
    public static final DeferredBlock<Block> MARBLE_ROCK = register("marble_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MARBLE_BRICK = register("marble_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> CRACKED_MARBLE_BRICK = register("cracked_marble_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_MARBLE_BRICK = register("mossy_marble_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MARBLE_COBBLESTONE = register("marble_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_MARBLE_COBBLESTONE = register("mossy_marble_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> SMOOTH_MARBLE_ROCK = register("smooth_marble_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MARBLE_PILLAR = register("marble_pillar", RegionalRockPillarBlock::new);
    public static final DeferredBlock<Block> MARBLE_ROCK_SLAB = register("marble_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MARBLE_ROCK_STAIRS = register("marble_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MARBLE_ROCK_WALL = register("marble_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MARBLE_ROCK_BUTTON = register("marble_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> MARBLE_ROCK_PRESSURE_PLATE = register("marble_rock_pressure_plate", RegionalRockPressurePlateBlock::new);
    public static final DeferredBlock<Block> MARBLE_BRICK_SLAB = register("marble_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MARBLE_BRICK_STAIRS = register("marble_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MARBLE_BRICK_WALL = register("marble_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> CRACKED_MARBLE_BRICK_SLAB = register("cracked_marble_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_MARBLE_BRICK_STAIRS = register("cracked_marble_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_MARBLE_BRICK_WALL = register("cracked_marble_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_MARBLE_BRICK_SLAB = register("mossy_marble_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_MARBLE_BRICK_STAIRS = register("mossy_marble_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_MARBLE_BRICK_WALL = register("mossy_marble_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MARBLE_COBBLESTONE_SLAB = register("marble_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MARBLE_COBBLESTONE_STAIRS = register("marble_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MARBLE_COBBLESTONE_WALL = register("marble_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_MARBLE_COBBLESTONE_SLAB = register("mossy_marble_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_MARBLE_COBBLESTONE_STAIRS = register("mossy_marble_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_MARBLE_COBBLESTONE_WALL = register("mossy_marble_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> SMOOTH_MARBLE_ROCK_SLAB = register("smooth_marble_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> SMOOTH_MARBLE_ROCK_STAIRS = register("smooth_marble_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> SMOOTH_MARBLE_ROCK_WALL = register("smooth_marble_rock_wall", RegionalRockWallBlock::new);

    // ── Log / Stripped-log maps (used by GotFlammableRotatedPillarBlock for axe stripping) ──
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
    }

    // ── Helpers ──────────────────────────────────────────────────────────

    /** Wood block: copies from OAK_PLANKS so no tool requirement, proper wood map-colour etc. */
    private static <B extends Block> DeferredBlock<B> woodBlock(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
        return REGISTRY.registerBlock(name, supplier, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    }

    /** Log/wood variant: copies OAK_LOG (hardness 2, resistance 2, ignitedByLava). */
    private static <B extends Block> DeferredBlock<B> logBlock(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
        return REGISTRY.registerBlock(name, supplier, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG));
    }

    private static <B extends Block> DeferredBlock<B> register(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
        return REGISTRY.registerBlock(name, supplier, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE));
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

    /** Diamond-tier ore: hardness 3.0, resistance 3.0, requires diamond pickaxe. */
    private static DeferredBlock<Block> oreDiamond(String name) {
        return REGISTRY.registerSimpleBlock(name,
                BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE));
    }

}
// This file is appended by a generation script - do not edit manually past this point
