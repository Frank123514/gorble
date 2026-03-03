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
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

import net.got.GotMod;
import net.got.init.GotTreeGrowers;
import net.got.init.GotWoodTypes;

import java.util.Properties;
import java.util.function.Function;

public class GotModBlocks {
    public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(GotMod.MODID);
    public static final DeferredBlock<Block> WEIRWOOD_LOG = woodBlock("weirwood_log", WeirwoodLogBlock::new);
    public static final DeferredBlock<Block> WEIRWOOD_WOOD = woodBlock("weirwood_wood", WeirwoodWoodBlock::new);
    public static final DeferredBlock<Block> WEIRWOOD_PLANKS = woodBlock("weirwood_planks", WeirwoodPlanksBlock::new);
    public static final DeferredBlock<Block> WEIRWOOD_LEAVES = woodBlock("weirwood_leaves", WeirwoodLeavesBlock::new);
    public static final DeferredBlock<Block> WEIRWOOD_STAIRS = woodBlock("weirwood_stairs", WeirwoodStairsBlock::new);
    public static final DeferredBlock<Block> WEIRWOOD_SLAB = woodBlock("weirwood_slab", WeirwoodSlabBlock::new);
    public static final DeferredBlock<Block> WEIRWOOD_FENCE = woodBlock("weirwood_fence", WeirwoodFenceBlock::new);
    public static final DeferredBlock<Block> WEIRWOOD_FENCE_GATE = woodBlock("weirwood_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.WEIRWOOD, p));
    public static final DeferredBlock<Block> WEIRWOOD_PRESSURE_PLATE = woodBlock("weirwood_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.WEIRWOOD_SET, p));
    public static final DeferredBlock<Block> WEIRWOOD_BUTTON = woodBlock("weirwood_button", p -> new GotButtonBlock(GotWoodTypes.WEIRWOOD_SET, p));
    public static final DeferredBlock<Block> ASPEN_LOG = woodBlock("aspen_log", AspenLogBlock::new);
    public static final DeferredBlock<Block> ASPEN_WOOD = woodBlock("aspen_wood", AspenWoodBlock::new);
    public static final DeferredBlock<Block> ASPEN_PLANKS = woodBlock("aspen_planks", AspenPlanksBlock::new);
    public static final DeferredBlock<Block> ASPEN_LEAVES = woodBlock("aspen_leaves", AspenLeavesBlock::new);
    public static final DeferredBlock<Block> ASPEN_STAIRS = woodBlock("aspen_stairs", AspenStairsBlock::new);
    public static final DeferredBlock<Block> ASPEN_SLAB = woodBlock("aspen_slab", AspenSlabBlock::new);
    public static final DeferredBlock<Block> ASPEN_FENCE = woodBlock("aspen_fence", AspenFenceBlock::new);
    public static final DeferredBlock<Block> ASPEN_FENCE_GATE = woodBlock("aspen_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.ASPEN, p));
    public static final DeferredBlock<Block> ASPEN_PRESSURE_PLATE = woodBlock("aspen_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.ASPEN_SET, p));
    public static final DeferredBlock<Block> ASPEN_BUTTON = woodBlock("aspen_button", p -> new GotButtonBlock(GotWoodTypes.ASPEN_SET, p));
    public static final DeferredBlock<Block> ALDER_LOG = woodBlock("alder_log", AlderLogBlock::new);
    public static final DeferredBlock<Block> ALDER_WOOD = woodBlock("alder_wood", AlderWoodBlock::new);
    public static final DeferredBlock<Block> ALDER_PLANKS = woodBlock("alder_planks", AlderPlanksBlock::new);
    public static final DeferredBlock<Block> ALDER_LEAVES = woodBlock("alder_leaves", AlderLeavesBlock::new);
    public static final DeferredBlock<Block> ALDER_STAIRS = woodBlock("alder_stairs", AlderStairsBlock::new);
    public static final DeferredBlock<Block> ALDER_SLAB = woodBlock("alder_slab", AlderSlabBlock::new);
    public static final DeferredBlock<Block> ALDER_FENCE = woodBlock("alder_fence", AlderFenceBlock::new);
    public static final DeferredBlock<Block> ALDER_FENCE_GATE = woodBlock("alder_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.ALDER, p));
    public static final DeferredBlock<Block> ALDER_PRESSURE_PLATE = woodBlock("alder_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.ALDER_SET, p));
    public static final DeferredBlock<Block> ALDER_BUTTON = woodBlock("alder_button", p -> new GotButtonBlock(GotWoodTypes.ALDER_SET, p));
    public static final DeferredBlock<Block> PINE_LOG = woodBlock("pine_log", PineLogBlock::new);
    public static final DeferredBlock<Block> PINE_WOOD = woodBlock("pine_wood", PineWoodBlock::new);
    public static final DeferredBlock<Block> PINE_PLANKS = woodBlock("pine_planks", PinePlanksBlock::new);
    public static final DeferredBlock<Block> PINE_LEAVES = woodBlock("pine_leaves", PineLeavesBlock::new);
    public static final DeferredBlock<Block> PINE_STAIRS = woodBlock("pine_stairs", PineStairsBlock::new);
    public static final DeferredBlock<Block> PINE_SLAB = woodBlock("pine_slab", PineSlabBlock::new);
    public static final DeferredBlock<Block> PINE_FENCE = woodBlock("pine_fence", PineFenceBlock::new);
    public static final DeferredBlock<Block> PINE_FENCE_GATE = woodBlock("pine_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.PINE, p));
    public static final DeferredBlock<Block> PINE_PRESSURE_PLATE = woodBlock("pine_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.PINE_SET, p));
    public static final DeferredBlock<Block> PINE_BUTTON = woodBlock("pine_button", p -> new GotButtonBlock(GotWoodTypes.PINE_SET, p));
    public static final DeferredBlock<Block> FIR_LOG = woodBlock("fir_log", FirLogBlock::new);
    public static final DeferredBlock<Block> FIR_WOOD = woodBlock("fir_wood", FirWoodBlock::new);
    public static final DeferredBlock<Block> FIR_PLANKS = woodBlock("fir_planks", FirPlanksBlock::new);
    public static final DeferredBlock<Block> FIR_LEAVES = woodBlock("fir_leaves", FirLeavesBlock::new);
    public static final DeferredBlock<Block> FIR_STAIRS = woodBlock("fir_stairs", FirStairsBlock::new);
    public static final DeferredBlock<Block> FIR_SLAB = woodBlock("fir_slab", FirSlabBlock::new);
    public static final DeferredBlock<Block> FIR_FENCE = woodBlock("fir_fence", FirFenceBlock::new);
    public static final DeferredBlock<Block> FIR_FENCE_GATE = woodBlock("fir_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.FIR, p));
    public static final DeferredBlock<Block> FIR_PRESSURE_PLATE = woodBlock("fir_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.FIR_SET, p));
    public static final DeferredBlock<Block> FIR_BUTTON = woodBlock("fir_button", p -> new GotButtonBlock(GotWoodTypes.FIR_SET, p));
    public static final DeferredBlock<Block> SENTINAL_LOG = woodBlock("sentinal_log", SentinalLogBlock::new);
    public static final DeferredBlock<Block> SENTINAL_WOOD = woodBlock("sentinal_wood", SentinalWoodBlock::new);
    public static final DeferredBlock<Block> SENTINAL_PLANKS = woodBlock("sentinal_planks", SentinalPlanksBlock::new);
    public static final DeferredBlock<Block> SENTINAL_LEAVES = woodBlock("sentinal_leaves", SentinalLeavesBlock::new);
    public static final DeferredBlock<Block> SENTINAL_STAIRS = woodBlock("sentinal_stairs", SentinalStairsBlock::new);
    public static final DeferredBlock<Block> SENTINAL_SLAB = woodBlock("sentinal_slab", SentinalSlabBlock::new);
    public static final DeferredBlock<Block> SENTINAL_FENCE = woodBlock("sentinal_fence", SentinalFenceBlock::new);
    public static final DeferredBlock<Block> SENTINAL_FENCE_GATE = woodBlock("sentinal_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.SENTINAL, p));
    public static final DeferredBlock<Block> SENTINAL_PRESSURE_PLATE = woodBlock("sentinal_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.SENTINAL_SET, p));
    public static final DeferredBlock<Block> SENTINAL_BUTTON = woodBlock("sentinal_button", p -> new GotButtonBlock(GotWoodTypes.SENTINAL_SET, p));
    public static final DeferredBlock<Block> IRONWOOD_LOG = woodBlock("ironwood_log", IronwoodLogBlock::new);
    public static final DeferredBlock<Block> IRONWOOD_WOOD = woodBlock("ironwood_wood", IronwoodWoodBlock::new);
    public static final DeferredBlock<Block> IRONWOOD_PLANKS = woodBlock("ironwood_planks", IronwoodPlanksBlock::new);
    public static final DeferredBlock<Block> IRONWOOD_LEAVES = woodBlock("ironwood_leaves", IronwoodLeavesBlock::new);
    public static final DeferredBlock<Block> IRONWOOD_STAIRS = woodBlock("ironwood_stairs", IronwoodStairsBlock::new);
    public static final DeferredBlock<Block> IRONWOOD_SLAB = woodBlock("ironwood_slab", IronwoodSlabBlock::new);
    public static final DeferredBlock<Block> IRONWOOD_FENCE = woodBlock("ironwood_fence", IronwoodFenceBlock::new);
    public static final DeferredBlock<Block> IRONWOOD_FENCE_GATE = woodBlock("ironwood_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.IRONWOOD, p));
    public static final DeferredBlock<Block> IRONWOOD_PRESSURE_PLATE = woodBlock("ironwood_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.IRONWOOD_SET, p));
    public static final DeferredBlock<Block> IRONWOOD_BUTTON = woodBlock("ironwood_button", p -> new GotButtonBlock(GotWoodTypes.IRONWOOD_SET, p));
    public static final DeferredBlock<Block> BEECH_LOG = woodBlock("beech_log", BeechLogBlock::new);
    public static final DeferredBlock<Block> BEECH_WOOD = woodBlock("beech_wood", BeechWoodBlock::new);
    public static final DeferredBlock<Block> BEECH_PLANKS = woodBlock("beech_planks", BeechPlanksBlock::new);
    public static final DeferredBlock<Block> BEECH_LEAVES = woodBlock("beech_leaves", BeechLeavesBlock::new);
    public static final DeferredBlock<Block> BEECH_STAIRS = woodBlock("beech_stairs", BeechStairsBlock::new);
    public static final DeferredBlock<Block> BEECH_SLAB = woodBlock("beech_slab", BeechSlabBlock::new);
    public static final DeferredBlock<Block> BEECH_FENCE = woodBlock("beech_fence", BeechFenceBlock::new);
    public static final DeferredBlock<Block> BEECH_FENCE_GATE = woodBlock("beech_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.BEECH, p));
    public static final DeferredBlock<Block> BEECH_PRESSURE_PLATE = woodBlock("beech_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.BEECH_SET, p));
    public static final DeferredBlock<Block> BEECH_BUTTON = woodBlock("beech_button", p -> new GotButtonBlock(GotWoodTypes.BEECH_SET, p));
    public static final DeferredBlock<Block> SOLDIER_PINE_LOG = woodBlock("soldier_pine_log", SoldierPineLogBlock::new);
    public static final DeferredBlock<Block> SOLDIER_PINE_WOOD = woodBlock("soldier_pine_wood", SoldierPineWoodBlock::new);
    public static final DeferredBlock<Block> SOLDIER_PINE_PLANKS = woodBlock("soldier_pine_planks", SoldierPinePlanksBlock::new);
    public static final DeferredBlock<Block> SOLDIER_PINE_LEAVES = woodBlock("soldier_pine_leaves", SoldierPineLeavesBlock::new);
    public static final DeferredBlock<Block> SOLDIER_PINE_STAIRS = woodBlock("soldier_pine_stairs", SoldierPineStairsBlock::new);
    public static final DeferredBlock<Block> SOLDIER_PINE_SLAB = woodBlock("soldier_pine_slab", SoldierPineSlabBlock::new);
    public static final DeferredBlock<Block> SOLDIER_PINE_FENCE = woodBlock("soldier_pine_fence", SoldierPineFenceBlock::new);
    public static final DeferredBlock<Block> SOLDIER_PINE_FENCE_GATE = woodBlock("soldier_pine_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.PINE, p));
    public static final DeferredBlock<Block> SOLDIER_PINE_PRESSURE_PLATE = woodBlock("soldier_pine_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.PINE_SET, p));
    public static final DeferredBlock<Block> SOLDIER_PINE_BUTTON = woodBlock("soldier_pine_button", p -> new GotButtonBlock(GotWoodTypes.PINE_SET, p));
    public static final DeferredBlock<Block> ASH_LOG = woodBlock("ash_log", AshLogBlock::new);
    public static final DeferredBlock<Block> ASH_WOOD = woodBlock("ash_wood", AshWoodBlock::new);
    public static final DeferredBlock<Block> ASH_PLANKS = woodBlock("ash_planks", AshPlanksBlock::new);
    public static final DeferredBlock<Block> ASH_LEAVES = woodBlock("ash_leaves", AshLeavesBlock::new);
    public static final DeferredBlock<Block> ASH_STAIRS = woodBlock("ash_stairs", AshStairsBlock::new);
    public static final DeferredBlock<Block> ASH_SLAB = woodBlock("ash_slab", AshSlabBlock::new);
    public static final DeferredBlock<Block> ASH_FENCE = woodBlock("ash_fence", AshFenceBlock::new);
    public static final DeferredBlock<Block> ASH_FENCE_GATE = woodBlock("ash_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.ASH, p));
    public static final DeferredBlock<Block> ASH_PRESSURE_PLATE = woodBlock("ash_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.ASH_SET, p));
    public static final DeferredBlock<Block> ASH_BUTTON = woodBlock("ash_button", p -> new GotButtonBlock(GotWoodTypes.ASH_SET, p));
    public static final DeferredBlock<Block> HAWTHORN_LOG = woodBlock("hawthorn_log", HawthornLogBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_WOOD = woodBlock("hawthorn_wood", HawthornWoodBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_PLANKS = woodBlock("hawthorn_planks", HawthornPlanksBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_LEAVES = woodBlock("hawthorn_leaves", HawthornLeavesBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_STAIRS = woodBlock("hawthorn_stairs", HawthornStairsBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_SLAB = woodBlock("hawthorn_slab", HawthornSlabBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_FENCE = woodBlock("hawthorn_fence", HawthornFenceBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_FENCE_GATE = woodBlock("hawthorn_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.HAWTHORN, p));
    public static final DeferredBlock<Block> HAWTHORN_PRESSURE_PLATE = woodBlock("hawthorn_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.HAWTHORN_SET, p));
    public static final DeferredBlock<Block> HAWTHORN_BUTTON = woodBlock("hawthorn_button", p -> new GotButtonBlock(GotWoodTypes.HAWTHORN_SET, p));

    // ── Crownlands ──────────────────────────────────────────────────────
    public static final DeferredBlock<Block> CROWNLANDS_ROCK = register("crownlands_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> CROWNLANDS_BRICK = register("crownlands_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> CRACKED_CROWNLANDS_BRICK = register("cracked_crownlands_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_CROWNLANDS_BRICK = register("mossy_crownlands_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> CROWNLANDS_COBBLESTONE = register("crownlands_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_CROWNLANDS_COBBLESTONE = register("mossy_crownlands_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> POLISHED_CROWNLANDS_ROCK = register("polished_crownlands_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> CROWNLANDS_PILLAR = register("crownlands_pillar", RegionalRockPillarBlock::new);

    // ── Dorne ──────────────────────────────────────────────────────
    public static final DeferredBlock<Block> DORNE_ROCK = register("dorne_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> DORNE_BRICK = register("dorne_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> CRACKED_DORNE_BRICK = register("cracked_dorne_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_DORNE_BRICK = register("mossy_dorne_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> DORNE_COBBLESTONE = register("dorne_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_DORNE_COBBLESTONE = register("mossy_dorne_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> POLISHED_DORNE_ROCK = register("polished_dorne_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> DORNE_PILLAR = register("dorne_pillar", RegionalRockPillarBlock::new);

    // ── Iron Islands ──────────────────────────────────────────────────────
    public static final DeferredBlock<Block> IRON_ISLANDS_ROCK = register("iron_islands_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> IRON_ISLANDS_BRICK = register("iron_islands_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> CRACKED_IRON_ISLANDS_BRICK = register("cracked_iron_islands_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_IRON_ISLANDS_BRICK = register("mossy_iron_islands_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> IRON_ISLANDS_COBBLESTONE = register("iron_islands_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_IRON_ISLANDS_COBBLESTONE = register("mossy_iron_islands_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> POLISHED_IRON_ISLANDS_ROCK = register("polished_iron_islands_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> IRON_ISLANDS_PILLAR = register("iron_islands_pillar", RegionalRockPillarBlock::new);

    // ── North ──────────────────────────────────────────────────────
    public static final DeferredBlock<Block> NORTH_ROCK = register("north_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> NORTH_BRICK = register("north_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> CRACKED_NORTH_BRICK = register("cracked_north_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_NORTH_BRICK = register("mossy_north_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> NORTH_COBBLESTONE = register("north_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_NORTH_COBBLESTONE = register("mossy_north_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> POLISHED_NORTH_ROCK = register("polished_north_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> NORTH_PILLAR = register("north_pillar", RegionalRockPillarBlock::new);

    // ── Reach ──────────────────────────────────────────────────────
    public static final DeferredBlock<Block> REACH_ROCK = register("reach_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> REACH_BRICK = register("reach_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> CRACKED_REACH_BRICK = register("cracked_reach_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_REACH_BRICK = register("mossy_reach_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> REACH_COBBLESTONE = register("reach_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_REACH_COBBLESTONE = register("mossy_reach_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> POLISHED_REACH_ROCK = register("polished_reach_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> REACH_PILLAR = register("reach_pillar", RegionalRockPillarBlock::new);

    // ── Riverlands ──────────────────────────────────────────────────────
    public static final DeferredBlock<Block> RIVERLANDS_ROCK = register("riverlands_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> RIVERLANDS_BRICK = register("riverlands_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> CRACKED_RIVERLANDS_BRICK = register("cracked_riverlands_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_RIVERLANDS_BRICK = register("mossy_riverlands_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> RIVERLANDS_COBBLESTONE = register("riverlands_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_RIVERLANDS_COBBLESTONE = register("mossy_riverlands_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> POLISHED_RIVERLANDS_ROCK = register("polished_riverlands_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> RIVERLANDS_PILLAR = register("riverlands_pillar", RegionalRockPillarBlock::new);

    // ── Stormlands ──────────────────────────────────────────────────────
    public static final DeferredBlock<Block> STORMLANDS_ROCK = register("stormlands_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> STORMLANDS_BRICK = register("stormlands_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> CRACKED_STORMLANDS_BRICK = register("cracked_stormlands_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_STORMLANDS_BRICK = register("mossy_stormlands_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> STORMLANDS_COBBLESTONE = register("stormlands_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_STORMLANDS_COBBLESTONE = register("mossy_stormlands_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> POLISHED_STORMLANDS_ROCK = register("polished_stormlands_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> STORMLANDS_PILLAR = register("stormlands_pillar", RegionalRockPillarBlock::new);

    // ── Vale ──────────────────────────────────────────────────────
    public static final DeferredBlock<Block> VALE_ROCK = register("vale_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> VALE_BRICK = register("vale_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> CRACKED_VALE_BRICK = register("cracked_vale_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_VALE_BRICK = register("mossy_vale_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> VALE_COBBLESTONE = register("vale_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_VALE_COBBLESTONE = register("mossy_vale_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> POLISHED_VALE_ROCK = register("polished_vale_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> VALE_PILLAR = register("vale_pillar", RegionalRockPillarBlock::new);

    // ── Westerlands ──────────────────────────────────────────────────────
    public static final DeferredBlock<Block> WESTERLANDS_ROCK = register("westerlands_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> WESTERLANDS_BRICK = register("westerlands_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> CRACKED_WESTERLANDS_BRICK = register("cracked_westerlands_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_WESTERLANDS_BRICK = register("mossy_westerlands_brick", RegionalRockBlock::new);
    public static final DeferredBlock<Block> WESTERLANDS_COBBLESTONE = register("westerlands_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> MOSSY_WESTERLANDS_COBBLESTONE = register("mossy_westerlands_cobblestone", RegionalRockBlock::new);
    public static final DeferredBlock<Block> POLISHED_WESTERLANDS_ROCK = register("polished_westerlands_rock", RegionalRockBlock::new);
    public static final DeferredBlock<Block> WESTERLANDS_PILLAR = register("westerlands_pillar", RegionalRockPillarBlock::new);

    // ── Crownlands ─────────────────────────────────────────
    public static final DeferredBlock<Block> CROWNLANDS_ROCK_SLAB = register("crownlands_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CROWNLANDS_ROCK_STAIRS = register("crownlands_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CROWNLANDS_ROCK_WALL = register("crownlands_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> CROWNLANDS_ROCK_BUTTON = register("crownlands_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> CROWNLANDS_ROCK_PRESSURE_PLATE = register("crownlands_rock_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> CROWNLANDS_BRICK_SLAB = register("crownlands_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CROWNLANDS_BRICK_STAIRS = register("crownlands_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CROWNLANDS_BRICK_WALL = register("crownlands_brick_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> CRACKED_CROWNLANDS_BRICK_SLAB = register("cracked_crownlands_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_CROWNLANDS_BRICK_STAIRS = register("cracked_crownlands_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_CROWNLANDS_BRICK_WALL = register("cracked_crownlands_brick_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> MOSSY_CROWNLANDS_BRICK_SLAB = register("mossy_crownlands_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_CROWNLANDS_BRICK_STAIRS = register("mossy_crownlands_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_CROWNLANDS_BRICK_WALL = register("mossy_crownlands_brick_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> CROWNLANDS_COBBLESTONE_SLAB = register("crownlands_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CROWNLANDS_COBBLESTONE_STAIRS = register("crownlands_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CROWNLANDS_COBBLESTONE_WALL = register("crownlands_cobblestone_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> MOSSY_CROWNLANDS_COBBLESTONE_SLAB = register("mossy_crownlands_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_CROWNLANDS_COBBLESTONE_STAIRS = register("mossy_crownlands_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_CROWNLANDS_COBBLESTONE_WALL = register("mossy_crownlands_cobblestone_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> POLISHED_CROWNLANDS_ROCK_SLAB = register("polished_crownlands_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> POLISHED_CROWNLANDS_ROCK_STAIRS = register("polished_crownlands_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> POLISHED_CROWNLANDS_ROCK_WALL = register("polished_crownlands_rock_wall", RegionalRockWallBlock::new);

    // ── Dorne ─────────────────────────────────────────
    public static final DeferredBlock<Block> DORNE_ROCK_SLAB = register("dorne_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> DORNE_ROCK_STAIRS = register("dorne_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> DORNE_ROCK_WALL = register("dorne_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> DORNE_ROCK_BUTTON = register("dorne_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> DORNE_ROCK_PRESSURE_PLATE = register("dorne_rock_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> DORNE_BRICK_SLAB = register("dorne_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> DORNE_BRICK_STAIRS = register("dorne_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> DORNE_BRICK_WALL = register("dorne_brick_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> CRACKED_DORNE_BRICK_SLAB = register("cracked_dorne_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_DORNE_BRICK_STAIRS = register("cracked_dorne_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_DORNE_BRICK_WALL = register("cracked_dorne_brick_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> MOSSY_DORNE_BRICK_SLAB = register("mossy_dorne_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_DORNE_BRICK_STAIRS = register("mossy_dorne_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_DORNE_BRICK_WALL = register("mossy_dorne_brick_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> DORNE_COBBLESTONE_SLAB = register("dorne_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> DORNE_COBBLESTONE_STAIRS = register("dorne_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> DORNE_COBBLESTONE_WALL = register("dorne_cobblestone_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> MOSSY_DORNE_COBBLESTONE_SLAB = register("mossy_dorne_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_DORNE_COBBLESTONE_STAIRS = register("mossy_dorne_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_DORNE_COBBLESTONE_WALL = register("mossy_dorne_cobblestone_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> POLISHED_DORNE_ROCK_SLAB = register("polished_dorne_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> POLISHED_DORNE_ROCK_STAIRS = register("polished_dorne_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> POLISHED_DORNE_ROCK_WALL = register("polished_dorne_rock_wall", RegionalRockWallBlock::new);

    // ── Iron Islands ─────────────────────────────────────────
    public static final DeferredBlock<Block> IRON_ISLANDS_ROCK_SLAB = register("iron_islands_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> IRON_ISLANDS_ROCK_STAIRS = register("iron_islands_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> IRON_ISLANDS_ROCK_WALL = register("iron_islands_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> IRON_ISLANDS_ROCK_BUTTON = register("iron_islands_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> IRON_ISLANDS_ROCK_PRESSURE_PLATE = register("iron_islands_rock_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> IRON_ISLANDS_BRICK_SLAB = register("iron_islands_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> IRON_ISLANDS_BRICK_STAIRS = register("iron_islands_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> IRON_ISLANDS_BRICK_WALL = register("iron_islands_brick_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> CRACKED_IRON_ISLANDS_BRICK_SLAB = register("cracked_iron_islands_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_IRON_ISLANDS_BRICK_STAIRS = register("cracked_iron_islands_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_IRON_ISLANDS_BRICK_WALL = register("cracked_iron_islands_brick_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> MOSSY_IRON_ISLANDS_BRICK_SLAB = register("mossy_iron_islands_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_IRON_ISLANDS_BRICK_STAIRS = register("mossy_iron_islands_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_IRON_ISLANDS_BRICK_WALL = register("mossy_iron_islands_brick_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> IRON_ISLANDS_COBBLESTONE_SLAB = register("iron_islands_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> IRON_ISLANDS_COBBLESTONE_STAIRS = register("iron_islands_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> IRON_ISLANDS_COBBLESTONE_WALL = register("iron_islands_cobblestone_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> MOSSY_IRON_ISLANDS_COBBLESTONE_SLAB = register("mossy_iron_islands_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_IRON_ISLANDS_COBBLESTONE_STAIRS = register("mossy_iron_islands_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_IRON_ISLANDS_COBBLESTONE_WALL = register("mossy_iron_islands_cobblestone_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> POLISHED_IRON_ISLANDS_ROCK_SLAB = register("polished_iron_islands_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> POLISHED_IRON_ISLANDS_ROCK_STAIRS = register("polished_iron_islands_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> POLISHED_IRON_ISLANDS_ROCK_WALL = register("polished_iron_islands_rock_wall", RegionalRockWallBlock::new);

    // ── North ─────────────────────────────────────────
    public static final DeferredBlock<Block> NORTH_ROCK_SLAB = register("north_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> NORTH_ROCK_STAIRS = register("north_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> NORTH_ROCK_WALL = register("north_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> NORTH_ROCK_BUTTON = register("north_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> NORTH_ROCK_PRESSURE_PLATE = register("north_rock_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> NORTH_BRICK_SLAB = register("north_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> NORTH_BRICK_STAIRS = register("north_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> NORTH_BRICK_WALL = register("north_brick_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> CRACKED_NORTH_BRICK_SLAB = register("cracked_north_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_NORTH_BRICK_STAIRS = register("cracked_north_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_NORTH_BRICK_WALL = register("cracked_north_brick_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> MOSSY_NORTH_BRICK_SLAB = register("mossy_north_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_NORTH_BRICK_STAIRS = register("mossy_north_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_NORTH_BRICK_WALL = register("mossy_north_brick_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> NORTH_COBBLESTONE_SLAB = register("north_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> NORTH_COBBLESTONE_STAIRS = register("north_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> NORTH_COBBLESTONE_WALL = register("north_cobblestone_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> MOSSY_NORTH_COBBLESTONE_SLAB = register("mossy_north_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_NORTH_COBBLESTONE_STAIRS = register("mossy_north_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_NORTH_COBBLESTONE_WALL = register("mossy_north_cobblestone_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> POLISHED_NORTH_ROCK_SLAB = register("polished_north_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> POLISHED_NORTH_ROCK_STAIRS = register("polished_north_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> POLISHED_NORTH_ROCK_WALL = register("polished_north_rock_wall", RegionalRockWallBlock::new);

    // ── Reach ─────────────────────────────────────────
    public static final DeferredBlock<Block> REACH_ROCK_SLAB = register("reach_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> REACH_ROCK_STAIRS = register("reach_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> REACH_ROCK_WALL = register("reach_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> REACH_ROCK_BUTTON = register("reach_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> REACH_ROCK_PRESSURE_PLATE = register("reach_rock_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> REACH_BRICK_SLAB = register("reach_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> REACH_BRICK_STAIRS = register("reach_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> REACH_BRICK_WALL = register("reach_brick_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> CRACKED_REACH_BRICK_SLAB = register("cracked_reach_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_REACH_BRICK_STAIRS = register("cracked_reach_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_REACH_BRICK_WALL = register("cracked_reach_brick_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> MOSSY_REACH_BRICK_SLAB = register("mossy_reach_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_REACH_BRICK_STAIRS = register("mossy_reach_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_REACH_BRICK_WALL = register("mossy_reach_brick_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> REACH_COBBLESTONE_SLAB = register("reach_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> REACH_COBBLESTONE_STAIRS = register("reach_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> REACH_COBBLESTONE_WALL = register("reach_cobblestone_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> MOSSY_REACH_COBBLESTONE_SLAB = register("mossy_reach_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_REACH_COBBLESTONE_STAIRS = register("mossy_reach_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_REACH_COBBLESTONE_WALL = register("mossy_reach_cobblestone_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> POLISHED_REACH_ROCK_SLAB = register("polished_reach_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> POLISHED_REACH_ROCK_STAIRS = register("polished_reach_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> POLISHED_REACH_ROCK_WALL = register("polished_reach_rock_wall", RegionalRockWallBlock::new);

    // ── Riverlands ─────────────────────────────────────────
    public static final DeferredBlock<Block> RIVERLANDS_ROCK_SLAB = register("riverlands_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> RIVERLANDS_ROCK_STAIRS = register("riverlands_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> RIVERLANDS_ROCK_WALL = register("riverlands_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> RIVERLANDS_ROCK_BUTTON = register("riverlands_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> RIVERLANDS_ROCK_PRESSURE_PLATE = register("riverlands_rock_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> RIVERLANDS_BRICK_SLAB = register("riverlands_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> RIVERLANDS_BRICK_STAIRS = register("riverlands_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> RIVERLANDS_BRICK_WALL = register("riverlands_brick_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> CRACKED_RIVERLANDS_BRICK_SLAB = register("cracked_riverlands_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_RIVERLANDS_BRICK_STAIRS = register("cracked_riverlands_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_RIVERLANDS_BRICK_WALL = register("cracked_riverlands_brick_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> MOSSY_RIVERLANDS_BRICK_SLAB = register("mossy_riverlands_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_RIVERLANDS_BRICK_STAIRS = register("mossy_riverlands_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_RIVERLANDS_BRICK_WALL = register("mossy_riverlands_brick_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> RIVERLANDS_COBBLESTONE_SLAB = register("riverlands_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> RIVERLANDS_COBBLESTONE_STAIRS = register("riverlands_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> RIVERLANDS_COBBLESTONE_WALL = register("riverlands_cobblestone_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> MOSSY_RIVERLANDS_COBBLESTONE_SLAB = register("mossy_riverlands_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_RIVERLANDS_COBBLESTONE_STAIRS = register("mossy_riverlands_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_RIVERLANDS_COBBLESTONE_WALL = register("mossy_riverlands_cobblestone_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> POLISHED_RIVERLANDS_ROCK_SLAB = register("polished_riverlands_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> POLISHED_RIVERLANDS_ROCK_STAIRS = register("polished_riverlands_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> POLISHED_RIVERLANDS_ROCK_WALL = register("polished_riverlands_rock_wall", RegionalRockWallBlock::new);

    // ── Stormlands ─────────────────────────────────────────
    public static final DeferredBlock<Block> STORMLANDS_ROCK_SLAB = register("stormlands_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> STORMLANDS_ROCK_STAIRS = register("stormlands_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> STORMLANDS_ROCK_WALL = register("stormlands_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> STORMLANDS_ROCK_BUTTON = register("stormlands_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> STORMLANDS_ROCK_PRESSURE_PLATE = register("stormlands_rock_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> STORMLANDS_BRICK_SLAB = register("stormlands_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> STORMLANDS_BRICK_STAIRS = register("stormlands_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> STORMLANDS_BRICK_WALL = register("stormlands_brick_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> CRACKED_STORMLANDS_BRICK_SLAB = register("cracked_stormlands_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_STORMLANDS_BRICK_STAIRS = register("cracked_stormlands_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_STORMLANDS_BRICK_WALL = register("cracked_stormlands_brick_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> MOSSY_STORMLANDS_BRICK_SLAB = register("mossy_stormlands_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_STORMLANDS_BRICK_STAIRS = register("mossy_stormlands_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_STORMLANDS_BRICK_WALL = register("mossy_stormlands_brick_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> STORMLANDS_COBBLESTONE_SLAB = register("stormlands_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> STORMLANDS_COBBLESTONE_STAIRS = register("stormlands_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> STORMLANDS_COBBLESTONE_WALL = register("stormlands_cobblestone_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> MOSSY_STORMLANDS_COBBLESTONE_SLAB = register("mossy_stormlands_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_STORMLANDS_COBBLESTONE_STAIRS = register("mossy_stormlands_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_STORMLANDS_COBBLESTONE_WALL = register("mossy_stormlands_cobblestone_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> POLISHED_STORMLANDS_ROCK_SLAB = register("polished_stormlands_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> POLISHED_STORMLANDS_ROCK_STAIRS = register("polished_stormlands_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> POLISHED_STORMLANDS_ROCK_WALL = register("polished_stormlands_rock_wall", RegionalRockWallBlock::new);

    // ── Vale ─────────────────────────────────────────
    public static final DeferredBlock<Block> VALE_ROCK_SLAB = register("vale_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> VALE_ROCK_STAIRS = register("vale_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> VALE_ROCK_WALL = register("vale_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> VALE_ROCK_BUTTON = register("vale_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> VALE_ROCK_PRESSURE_PLATE = register("vale_rock_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> VALE_BRICK_SLAB = register("vale_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> VALE_BRICK_STAIRS = register("vale_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> VALE_BRICK_WALL = register("vale_brick_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> CRACKED_VALE_BRICK_SLAB = register("cracked_vale_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_VALE_BRICK_STAIRS = register("cracked_vale_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_VALE_BRICK_WALL = register("cracked_vale_brick_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> MOSSY_VALE_BRICK_SLAB = register("mossy_vale_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_VALE_BRICK_STAIRS = register("mossy_vale_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_VALE_BRICK_WALL = register("mossy_vale_brick_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> VALE_COBBLESTONE_SLAB = register("vale_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> VALE_COBBLESTONE_STAIRS = register("vale_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> VALE_COBBLESTONE_WALL = register("vale_cobblestone_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> MOSSY_VALE_COBBLESTONE_SLAB = register("mossy_vale_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_VALE_COBBLESTONE_STAIRS = register("mossy_vale_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_VALE_COBBLESTONE_WALL = register("mossy_vale_cobblestone_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> POLISHED_VALE_ROCK_SLAB = register("polished_vale_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> POLISHED_VALE_ROCK_STAIRS = register("polished_vale_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> POLISHED_VALE_ROCK_WALL = register("polished_vale_rock_wall", RegionalRockWallBlock::new);

    // ── Westerlands ─────────────────────────────────────────
    public static final DeferredBlock<Block> WESTERLANDS_ROCK_SLAB = register("westerlands_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> WESTERLANDS_ROCK_STAIRS = register("westerlands_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> WESTERLANDS_ROCK_WALL = register("westerlands_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> WESTERLANDS_ROCK_BUTTON = register("westerlands_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> WESTERLANDS_ROCK_PRESSURE_PLATE = register("westerlands_rock_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> WESTERLANDS_BRICK_SLAB = register("westerlands_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> WESTERLANDS_BRICK_STAIRS = register("westerlands_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> WESTERLANDS_BRICK_WALL = register("westerlands_brick_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> CRACKED_WESTERLANDS_BRICK_SLAB = register("cracked_westerlands_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_WESTERLANDS_BRICK_STAIRS = register("cracked_westerlands_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_WESTERLANDS_BRICK_WALL = register("cracked_westerlands_brick_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> MOSSY_WESTERLANDS_BRICK_SLAB = register("mossy_westerlands_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_WESTERLANDS_BRICK_STAIRS = register("mossy_westerlands_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_WESTERLANDS_BRICK_WALL = register("mossy_westerlands_brick_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> WESTERLANDS_COBBLESTONE_SLAB = register("westerlands_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> WESTERLANDS_COBBLESTONE_STAIRS = register("westerlands_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> WESTERLANDS_COBBLESTONE_WALL = register("westerlands_cobblestone_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> MOSSY_WESTERLANDS_COBBLESTONE_SLAB = register("mossy_westerlands_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_WESTERLANDS_COBBLESTONE_STAIRS = register("mossy_westerlands_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_WESTERLANDS_COBBLESTONE_WALL = register("mossy_westerlands_cobblestone_wall", RegionalRockWallBlock::new);

    public static final DeferredBlock<Block> POLISHED_WESTERLANDS_ROCK_SLAB = register("polished_westerlands_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> POLISHED_WESTERLANDS_ROCK_STAIRS = register("polished_westerlands_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> POLISHED_WESTERLANDS_ROCK_WALL = register("polished_westerlands_rock_wall", RegionalRockWallBlock::new);
    // ── Blackbark Tree ────────────────────────────────────────────────────
    public static final DeferredBlock<Block> BLACKBARK_LOG            = woodBlock("blackbark_log",            BlackbarkLogBlock::new);
    public static final DeferredBlock<Block> BLACKBARK_WOOD           = woodBlock("blackbark_wood",           BlackbarkWoodBlock::new);
    public static final DeferredBlock<Block> BLACKBARK_PLANKS         = woodBlock("blackbark_planks",         BlackbarkPlanksBlock::new);
    public static final DeferredBlock<Block> BLACKBARK_LEAVES         = woodBlock("blackbark_leaves",         BlackbarkLeavesBlock::new);
    public static final DeferredBlock<Block> BLACKBARK_STAIRS         = woodBlock("blackbark_stairs",         BlackbarkStairsBlock::new);
    public static final DeferredBlock<Block> BLACKBARK_SLAB           = woodBlock("blackbark_slab",           BlackbarkSlabBlock::new);
    public static final DeferredBlock<Block> BLACKBARK_FENCE          = woodBlock("blackbark_fence",          BlackbarkFenceBlock::new);
    public static final DeferredBlock<Block> BLACKBARK_FENCE_GATE     = woodBlock("blackbark_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.BLACKBARK, p));
    public static final DeferredBlock<Block> BLACKBARK_PRESSURE_PLATE = woodBlock("blackbark_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.BLACKBARK_SET, p));
    public static final DeferredBlock<Block> BLACKBARK_BUTTON         = woodBlock("blackbark_button",         p -> new GotButtonBlock(GotWoodTypes.BLACKBARK_SET, p));

    // ── Bloodwood Tree ────────────────────────────────────────────────────
    public static final DeferredBlock<Block> BLOODWOOD_LOG            = woodBlock("bloodwood_log",            BloodwoodLogBlock::new);
    public static final DeferredBlock<Block> BLOODWOOD_WOOD           = woodBlock("bloodwood_wood",           BloodwoodWoodBlock::new);
    public static final DeferredBlock<Block> BLOODWOOD_PLANKS         = woodBlock("bloodwood_planks",         BloodwoodPlanksBlock::new);
    public static final DeferredBlock<Block> BLOODWOOD_LEAVES         = woodBlock("bloodwood_leaves",         BloodwoodLeavesBlock::new);
    public static final DeferredBlock<Block> BLOODWOOD_STAIRS         = woodBlock("bloodwood_stairs",         BloodwoodStairsBlock::new);
    public static final DeferredBlock<Block> BLOODWOOD_SLAB           = woodBlock("bloodwood_slab",           BloodwoodSlabBlock::new);
    public static final DeferredBlock<Block> BLOODWOOD_FENCE          = woodBlock("bloodwood_fence",          BloodwoodFenceBlock::new);
    public static final DeferredBlock<Block> BLOODWOOD_FENCE_GATE     = woodBlock("bloodwood_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.BLOODWOOD, p));
    public static final DeferredBlock<Block> BLOODWOOD_PRESSURE_PLATE = woodBlock("bloodwood_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.BLOODWOOD_SET, p));
    public static final DeferredBlock<Block> BLOODWOOD_BUTTON         = woodBlock("bloodwood_button",         p -> new GotButtonBlock(GotWoodTypes.BLOODWOOD_SET, p));

    // ── Blue Mahoe Tree ───────────────────────────────────────────────────
    public static final DeferredBlock<Block> BLUE_MAHOE_LOG            = woodBlock("blue_mahoe_log",            BlueMahoeLogBlock::new);
    public static final DeferredBlock<Block> BLUE_MAHOE_WOOD           = woodBlock("blue_mahoe_wood",           BlueMahoeWoodBlock::new);
    public static final DeferredBlock<Block> BLUE_MAHOE_PLANKS         = woodBlock("blue_mahoe_planks",         BlueMahoePlanksBlock::new);
    public static final DeferredBlock<Block> BLUE_MAHOE_LEAVES         = woodBlock("blue_mahoe_leaves",         BlueMahoeLeavesBlock::new);
    public static final DeferredBlock<Block> BLUE_MAHOE_STAIRS         = woodBlock("blue_mahoe_stairs",         BlueMahoeStairsBlock::new);
    public static final DeferredBlock<Block> BLUE_MAHOE_SLAB           = woodBlock("blue_mahoe_slab",           BlueMahoeSlabBlock::new);
    public static final DeferredBlock<Block> BLUE_MAHOE_FENCE          = woodBlock("blue_mahoe_fence",          BlueMahoeFenceBlock::new);
    public static final DeferredBlock<Block> BLUE_MAHOE_FENCE_GATE     = woodBlock("blue_mahoe_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.BLUE_MAHOE, p));
    public static final DeferredBlock<Block> BLUE_MAHOE_PRESSURE_PLATE = woodBlock("blue_mahoe_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.BLUE_MAHOE_SET, p));
    public static final DeferredBlock<Block> BLUE_MAHOE_BUTTON         = woodBlock("blue_mahoe_button",         p -> new GotButtonBlock(GotWoodTypes.BLUE_MAHOE_SET, p));

    // ── Cottonwood Tree ───────────────────────────────────────────────────
    public static final DeferredBlock<Block> COTTONWOOD_LOG            = woodBlock("cottonwood_log",            CottonwoodLogBlock::new);
    public static final DeferredBlock<Block> COTTONWOOD_WOOD           = woodBlock("cottonwood_wood",           CottonwoodWoodBlock::new);
    public static final DeferredBlock<Block> COTTONWOOD_PLANKS         = woodBlock("cottonwood_planks",         CottonwoodPlanksBlock::new);
    public static final DeferredBlock<Block> COTTONWOOD_LEAVES         = woodBlock("cottonwood_leaves",         CottonwoodLeavesBlock::new);
    public static final DeferredBlock<Block> COTTONWOOD_STAIRS         = woodBlock("cottonwood_stairs",         CottonwoodStairsBlock::new);
    public static final DeferredBlock<Block> COTTONWOOD_SLAB           = woodBlock("cottonwood_slab",           CottonwoodSlabBlock::new);
    public static final DeferredBlock<Block> COTTONWOOD_FENCE          = woodBlock("cottonwood_fence",          CottonwoodFenceBlock::new);
    public static final DeferredBlock<Block> COTTONWOOD_FENCE_GATE     = woodBlock("cottonwood_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.COTTONWOOD, p));
    public static final DeferredBlock<Block> COTTONWOOD_PRESSURE_PLATE = woodBlock("cottonwood_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.COTTONWOOD_SET, p));
    public static final DeferredBlock<Block> COTTONWOOD_BUTTON         = woodBlock("cottonwood_button",         p -> new GotButtonBlock(GotWoodTypes.COTTONWOOD_SET, p));


    // ── BlackCottonwood Tree ──────────────────────────────────────────
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_LOG            = woodBlock("black_cottonwood_log",            BlackCottonwoodLogBlock::new);
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_WOOD           = woodBlock("black_cottonwood_wood",           BlackCottonwoodWoodBlock::new);
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_PLANKS         = woodBlock("black_cottonwood_planks",         BlackCottonwoodPlanksBlock::new);
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_LEAVES         = woodBlock("black_cottonwood_leaves",         BlackCottonwoodLeavesBlock::new);
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_STAIRS         = woodBlock("black_cottonwood_stairs",         BlackCottonwoodStairsBlock::new);
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_SLAB           = woodBlock("black_cottonwood_slab",           BlackCottonwoodSlabBlock::new);
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_FENCE          = woodBlock("black_cottonwood_fence",          BlackCottonwoodFenceBlock::new);
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_FENCE_GATE     = woodBlock("black_cottonwood_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.COTTONWOOD, p));
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_PRESSURE_PLATE = woodBlock("black_cottonwood_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.COTTONWOOD_SET, p));
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_BUTTON         = woodBlock("black_cottonwood_button",         p -> new GotButtonBlock(GotWoodTypes.COTTONWOOD_SET, p));

    // ── Cinnamon Tree ──────────────────────────────────────────
    public static final DeferredBlock<Block> CINNAMON_LOG            = woodBlock("cinnamon_log",            CinnamonLogBlock::new);
    public static final DeferredBlock<Block> CINNAMON_WOOD           = woodBlock("cinnamon_wood",           CinnamonWoodBlock::new);
    public static final DeferredBlock<Block> CINNAMON_PLANKS         = woodBlock("cinnamon_planks",         CinnamonPlanksBlock::new);
    public static final DeferredBlock<Block> CINNAMON_LEAVES         = woodBlock("cinnamon_leaves",         CinnamonLeavesBlock::new);
    public static final DeferredBlock<Block> CINNAMON_STAIRS         = woodBlock("cinnamon_stairs",         CinnamonStairsBlock::new);
    public static final DeferredBlock<Block> CINNAMON_SLAB           = woodBlock("cinnamon_slab",           CinnamonSlabBlock::new);
    public static final DeferredBlock<Block> CINNAMON_FENCE          = woodBlock("cinnamon_fence",          CinnamonFenceBlock::new);
    public static final DeferredBlock<Block> CINNAMON_FENCE_GATE     = woodBlock("cinnamon_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.CINNAMON, p));
    public static final DeferredBlock<Block> CINNAMON_PRESSURE_PLATE = woodBlock("cinnamon_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.CINNAMON_SET, p));
    public static final DeferredBlock<Block> CINNAMON_BUTTON         = woodBlock("cinnamon_button",         p -> new GotButtonBlock(GotWoodTypes.CINNAMON_SET, p));

    // ── Clove Tree ──────────────────────────────────────────
    public static final DeferredBlock<Block> CLOVE_LOG            = woodBlock("clove_log",            CloveLogBlock::new);
    public static final DeferredBlock<Block> CLOVE_WOOD           = woodBlock("clove_wood",           CloveWoodBlock::new);
    public static final DeferredBlock<Block> CLOVE_PLANKS         = woodBlock("clove_planks",         ClovePlanksBlock::new);
    public static final DeferredBlock<Block> CLOVE_LEAVES         = woodBlock("clove_leaves",         CloveLeavesBlock::new);
    public static final DeferredBlock<Block> CLOVE_STAIRS         = woodBlock("clove_stairs",         CloveStairsBlock::new);
    public static final DeferredBlock<Block> CLOVE_SLAB           = woodBlock("clove_slab",           CloveSlabBlock::new);
    public static final DeferredBlock<Block> CLOVE_FENCE          = woodBlock("clove_fence",          CloveFenceBlock::new);
    public static final DeferredBlock<Block> CLOVE_FENCE_GATE     = woodBlock("clove_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.CLOVE, p));
    public static final DeferredBlock<Block> CLOVE_PRESSURE_PLATE = woodBlock("clove_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.CLOVE_SET, p));
    public static final DeferredBlock<Block> CLOVE_BUTTON         = woodBlock("clove_button",         p -> new GotButtonBlock(GotWoodTypes.CLOVE_SET, p));

    // ── Ebony Tree ──────────────────────────────────────────
    public static final DeferredBlock<Block> EBONY_LOG            = woodBlock("ebony_log",            EbonyLogBlock::new);
    public static final DeferredBlock<Block> EBONY_WOOD           = woodBlock("ebony_wood",           EbonyWoodBlock::new);
    public static final DeferredBlock<Block> EBONY_PLANKS         = woodBlock("ebony_planks",         EbonyPlanksBlock::new);
    public static final DeferredBlock<Block> EBONY_LEAVES         = woodBlock("ebony_leaves",         EbonyLeavesBlock::new);
    public static final DeferredBlock<Block> EBONY_STAIRS         = woodBlock("ebony_stairs",         EbonyStairsBlock::new);
    public static final DeferredBlock<Block> EBONY_SLAB           = woodBlock("ebony_slab",           EbonySlabBlock::new);
    public static final DeferredBlock<Block> EBONY_FENCE          = woodBlock("ebony_fence",          EbonyFenceBlock::new);
    public static final DeferredBlock<Block> EBONY_FENCE_GATE     = woodBlock("ebony_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.EBONY, p));
    public static final DeferredBlock<Block> EBONY_PRESSURE_PLATE = woodBlock("ebony_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.EBONY_SET, p));
    public static final DeferredBlock<Block> EBONY_BUTTON         = woodBlock("ebony_button",         p -> new GotButtonBlock(GotWoodTypes.EBONY_SET, p));

    // ── Elm Tree ──────────────────────────────────────────
    public static final DeferredBlock<Block> ELM_LOG            = woodBlock("elm_log",            ElmLogBlock::new);
    public static final DeferredBlock<Block> ELM_WOOD           = woodBlock("elm_wood",           ElmWoodBlock::new);
    public static final DeferredBlock<Block> ELM_PLANKS         = woodBlock("elm_planks",         ElmPlanksBlock::new);
    public static final DeferredBlock<Block> ELM_LEAVES         = woodBlock("elm_leaves",         ElmLeavesBlock::new);
    public static final DeferredBlock<Block> ELM_STAIRS         = woodBlock("elm_stairs",         ElmStairsBlock::new);
    public static final DeferredBlock<Block> ELM_SLAB           = woodBlock("elm_slab",           ElmSlabBlock::new);
    public static final DeferredBlock<Block> ELM_FENCE          = woodBlock("elm_fence",          ElmFenceBlock::new);
    public static final DeferredBlock<Block> ELM_FENCE_GATE     = woodBlock("elm_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.ELM, p));
    public static final DeferredBlock<Block> ELM_PRESSURE_PLATE = woodBlock("elm_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.ELM_SET, p));
    public static final DeferredBlock<Block> ELM_BUTTON         = woodBlock("elm_button",         p -> new GotButtonBlock(GotWoodTypes.ELM_SET, p));

    // ── Cedar Tree ──────────────────────────────────────────
    public static final DeferredBlock<Block> CEDAR_LOG            = woodBlock("cedar_log",            CedarLogBlock::new);
    public static final DeferredBlock<Block> CEDAR_WOOD           = woodBlock("cedar_wood",           CedarWoodBlock::new);
    public static final DeferredBlock<Block> CEDAR_PLANKS         = woodBlock("cedar_planks",         CedarPlanksBlock::new);
    public static final DeferredBlock<Block> CEDAR_LEAVES         = woodBlock("cedar_leaves",         CedarLeavesBlock::new);
    public static final DeferredBlock<Block> CEDAR_STAIRS         = woodBlock("cedar_stairs",         CedarStairsBlock::new);
    public static final DeferredBlock<Block> CEDAR_SLAB           = woodBlock("cedar_slab",           CedarSlabBlock::new);
    public static final DeferredBlock<Block> CEDAR_FENCE          = woodBlock("cedar_fence",          CedarFenceBlock::new);
    public static final DeferredBlock<Block> CEDAR_FENCE_GATE     = woodBlock("cedar_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.CEDAR, p));
    public static final DeferredBlock<Block> CEDAR_PRESSURE_PLATE = woodBlock("cedar_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.CEDAR_SET, p));
    public static final DeferredBlock<Block> CEDAR_BUTTON         = woodBlock("cedar_button",         p -> new GotButtonBlock(GotWoodTypes.CEDAR_SET, p));



    // ── Stripped Logs ────────────────────────────────────────────────────
    public static final DeferredBlock<Block> STRIPPED_WEIRWOOD_LOG = woodBlock("stripped_weirwood_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_ASPEN_LOG = woodBlock("stripped_aspen_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_ALDER_LOG = woodBlock("stripped_alder_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_PINE_LOG = woodBlock("stripped_pine_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_FIR_LOG = woodBlock("stripped_fir_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_SENTINAL_LOG = woodBlock("stripped_sentinal_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_IRONWOOD_LOG = woodBlock("stripped_ironwood_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_BEECH_LOG = woodBlock("stripped_beech_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_SOLDIER_PINE_LOG = woodBlock("stripped_soldier_pine_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_ASH_LOG = woodBlock("stripped_ash_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_HAWTHORN_LOG = woodBlock("stripped_hawthorn_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_BLACKBARK_LOG = woodBlock("stripped_blackbark_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_BLOODWOOD_LOG = woodBlock("stripped_bloodwood_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_BLUE_MAHOE_LOG = woodBlock("stripped_blue_mahoe_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_COTTONWOOD_LOG = woodBlock("stripped_cottonwood_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_BLACK_COTTONWOOD_LOG = woodBlock("stripped_black_cottonwood_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_CINNAMON_LOG = woodBlock("stripped_cinnamon_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_CLOVE_LOG = woodBlock("stripped_clove_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_EBONY_LOG = woodBlock("stripped_ebony_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_ELM_LOG = woodBlock("stripped_elm_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_CEDAR_LOG = woodBlock("stripped_cedar_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_APPLE_LOG = woodBlock("stripped_apple_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_GOLDENHEART_LOG = woodBlock("stripped_goldenheart_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_LINDEN_LOG = woodBlock("stripped_linden_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_MAHOGANY_LOG = woodBlock("stripped_mahogany_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_MAPLE_LOG = woodBlock("stripped_maple_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_MYRRH_LOG = woodBlock("stripped_myrrh_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_REDWOOD_LOG = woodBlock("stripped_redwood_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_CHESTNUT_LOG = woodBlock("stripped_chestnut_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_WILLOW_LOG = woodBlock("stripped_willow_log", GotStrippedLogBlock::new);
    public static final DeferredBlock<Block> STRIPPED_WORMTREE_LOG = woodBlock("stripped_wormtree_log", GotStrippedLogBlock::new);

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
    public static final DeferredBlock<Block> APPLE_LOG            = woodBlock("apple_log",            AppleLogBlock::new);
    public static final DeferredBlock<Block> APPLE_WOOD           = woodBlock("apple_wood",           AppleWoodBlock::new);
    public static final DeferredBlock<Block> APPLE_PLANKS         = woodBlock("apple_planks",         ApplePlanksBlock::new);
    public static final DeferredBlock<Block> APPLE_LEAVES         = woodBlock("apple_leaves",         AppleLeavesBlock::new);
    public static final DeferredBlock<Block> APPLE_STAIRS         = woodBlock("apple_stairs",         AppleStairsBlock::new);
    public static final DeferredBlock<Block> APPLE_SLAB           = woodBlock("apple_slab",           AppleSlabBlock::new);
    public static final DeferredBlock<Block> APPLE_FENCE          = woodBlock("apple_fence",          AppleFenceBlock::new);
    public static final DeferredBlock<Block> APPLE_FENCE_GATE     = woodBlock("apple_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.APPLE, p));
    public static final DeferredBlock<Block> APPLE_PRESSURE_PLATE = woodBlock("apple_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.APPLE_SET, p));
    public static final DeferredBlock<Block> APPLE_BUTTON         = woodBlock("apple_button",         p -> new GotButtonBlock(GotWoodTypes.APPLE_SET, p));

    // ── Goldenheart Tree
    public static final DeferredBlock<Block> GOLDENHEART_LOG            = woodBlock("goldenheart_log",            GoldenheartLogBlock::new);
    public static final DeferredBlock<Block> GOLDENHEART_WOOD           = woodBlock("goldenheart_wood",           GoldenheartWoodBlock::new);
    public static final DeferredBlock<Block> GOLDENHEART_PLANKS         = woodBlock("goldenheart_planks",         GoldenheartPlanksBlock::new);
    public static final DeferredBlock<Block> GOLDENHEART_LEAVES         = woodBlock("goldenheart_leaves",         GoldenheartLeavesBlock::new);
    public static final DeferredBlock<Block> GOLDENHEART_STAIRS         = woodBlock("goldenheart_stairs",         GoldenheartStairsBlock::new);
    public static final DeferredBlock<Block> GOLDENHEART_SLAB           = woodBlock("goldenheart_slab",           GoldenheartSlabBlock::new);
    public static final DeferredBlock<Block> GOLDENHEART_FENCE          = woodBlock("goldenheart_fence",          GoldenheartFenceBlock::new);
    public static final DeferredBlock<Block> GOLDENHEART_FENCE_GATE     = woodBlock("goldenheart_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.GOLDENHEART, p));
    public static final DeferredBlock<Block> GOLDENHEART_PRESSURE_PLATE = woodBlock("goldenheart_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.GOLDENHEART_SET, p));
    public static final DeferredBlock<Block> GOLDENHEART_BUTTON         = woodBlock("goldenheart_button",         p -> new GotButtonBlock(GotWoodTypes.GOLDENHEART_SET, p));

    // ── Linden Tree
    public static final DeferredBlock<Block> LINDEN_LOG            = woodBlock("linden_log",            LindenLogBlock::new);
    public static final DeferredBlock<Block> LINDEN_WOOD           = woodBlock("linden_wood",           LindenWoodBlock::new);
    public static final DeferredBlock<Block> LINDEN_PLANKS         = woodBlock("linden_planks",         LindenPlanksBlock::new);
    public static final DeferredBlock<Block> LINDEN_LEAVES         = woodBlock("linden_leaves",         LindenLeavesBlock::new);
    public static final DeferredBlock<Block> LINDEN_STAIRS         = woodBlock("linden_stairs",         LindenStairsBlock::new);
    public static final DeferredBlock<Block> LINDEN_SLAB           = woodBlock("linden_slab",           LindenSlabBlock::new);
    public static final DeferredBlock<Block> LINDEN_FENCE          = woodBlock("linden_fence",          LindenFenceBlock::new);
    public static final DeferredBlock<Block> LINDEN_FENCE_GATE     = woodBlock("linden_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.LINDEN, p));
    public static final DeferredBlock<Block> LINDEN_PRESSURE_PLATE = woodBlock("linden_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.LINDEN_SET, p));
    public static final DeferredBlock<Block> LINDEN_BUTTON         = woodBlock("linden_button",         p -> new GotButtonBlock(GotWoodTypes.LINDEN_SET, p));

    // ── Mahogany Tree
    public static final DeferredBlock<Block> MAHOGANY_LOG            = woodBlock("mahogany_log",            MahoganyLogBlock::new);
    public static final DeferredBlock<Block> MAHOGANY_WOOD           = woodBlock("mahogany_wood",           MahoganyWoodBlock::new);
    public static final DeferredBlock<Block> MAHOGANY_PLANKS         = woodBlock("mahogany_planks",         MahoganyPlanksBlock::new);
    public static final DeferredBlock<Block> MAHOGANY_LEAVES         = woodBlock("mahogany_leaves",         MahoganyLeavesBlock::new);
    public static final DeferredBlock<Block> MAHOGANY_STAIRS         = woodBlock("mahogany_stairs",         MahoganyStairsBlock::new);
    public static final DeferredBlock<Block> MAHOGANY_SLAB           = woodBlock("mahogany_slab",           MahoganySlabBlock::new);
    public static final DeferredBlock<Block> MAHOGANY_FENCE          = woodBlock("mahogany_fence",          MahoganyFenceBlock::new);
    public static final DeferredBlock<Block> MAHOGANY_FENCE_GATE     = woodBlock("mahogany_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.MAHOGANY, p));
    public static final DeferredBlock<Block> MAHOGANY_PRESSURE_PLATE = woodBlock("mahogany_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.MAHOGANY_SET, p));
    public static final DeferredBlock<Block> MAHOGANY_BUTTON         = woodBlock("mahogany_button",         p -> new GotButtonBlock(GotWoodTypes.MAHOGANY_SET, p));

    // ── Maple Tree
    public static final DeferredBlock<Block> MAPLE_LOG            = woodBlock("maple_log",            MapleLogBlock::new);
    public static final DeferredBlock<Block> MAPLE_WOOD           = woodBlock("maple_wood",           MapleWoodBlock::new);
    public static final DeferredBlock<Block> MAPLE_PLANKS         = woodBlock("maple_planks",         MaplePlanksBlock::new);
    public static final DeferredBlock<Block> MAPLE_LEAVES         = woodBlock("maple_leaves",         MapleLeavesBlock::new);
    public static final DeferredBlock<Block> MAPLE_STAIRS         = woodBlock("maple_stairs",         MapleStairsBlock::new);
    public static final DeferredBlock<Block> MAPLE_SLAB           = woodBlock("maple_slab",           MapleSlabBlock::new);
    public static final DeferredBlock<Block> MAPLE_FENCE          = woodBlock("maple_fence",          MapleFenceBlock::new);
    public static final DeferredBlock<Block> MAPLE_FENCE_GATE     = woodBlock("maple_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.MAPLE, p));
    public static final DeferredBlock<Block> MAPLE_PRESSURE_PLATE = woodBlock("maple_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.MAPLE_SET, p));
    public static final DeferredBlock<Block> MAPLE_BUTTON         = woodBlock("maple_button",         p -> new GotButtonBlock(GotWoodTypes.MAPLE_SET, p));

    // ── Myrrh Tree
    public static final DeferredBlock<Block> MYRRH_LOG            = woodBlock("myrrh_log",            MyrrhLogBlock::new);
    public static final DeferredBlock<Block> MYRRH_WOOD           = woodBlock("myrrh_wood",           MyrrhWoodBlock::new);
    public static final DeferredBlock<Block> MYRRH_PLANKS         = woodBlock("myrrh_planks",         MyrrhPlanksBlock::new);
    public static final DeferredBlock<Block> MYRRH_LEAVES         = woodBlock("myrrh_leaves",         MyrrhLeavesBlock::new);
    public static final DeferredBlock<Block> MYRRH_STAIRS         = woodBlock("myrrh_stairs",         MyrrhStairsBlock::new);
    public static final DeferredBlock<Block> MYRRH_SLAB           = woodBlock("myrrh_slab",           MyrrhSlabBlock::new);
    public static final DeferredBlock<Block> MYRRH_FENCE          = woodBlock("myrrh_fence",          MyrrhFenceBlock::new);
    public static final DeferredBlock<Block> MYRRH_FENCE_GATE     = woodBlock("myrrh_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.MYRRH, p));
    public static final DeferredBlock<Block> MYRRH_PRESSURE_PLATE = woodBlock("myrrh_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.MYRRH_SET, p));
    public static final DeferredBlock<Block> MYRRH_BUTTON         = woodBlock("myrrh_button",         p -> new GotButtonBlock(GotWoodTypes.MYRRH_SET, p));
    // ── Redwood Tree
    public static final DeferredBlock<Block> REDWOOD_LOG            = woodBlock("redwood_log",            RedwoodLogBlock::new);
    public static final DeferredBlock<Block> REDWOOD_WOOD           = woodBlock("redwood_wood",           RedwoodWoodBlock::new);
    public static final DeferredBlock<Block> REDWOOD_PLANKS         = woodBlock("redwood_planks",         RedwoodPlanksBlock::new);
    public static final DeferredBlock<Block> REDWOOD_LEAVES         = woodBlock("redwood_leaves",         RedwoodLeavesBlock::new);
    public static final DeferredBlock<Block> REDWOOD_STAIRS         = woodBlock("redwood_stairs",         RedwoodStairsBlock::new);
    public static final DeferredBlock<Block> REDWOOD_SLAB           = woodBlock("redwood_slab",           RedwoodSlabBlock::new);
    public static final DeferredBlock<Block> REDWOOD_FENCE          = woodBlock("redwood_fence",          RedwoodFenceBlock::new);
    public static final DeferredBlock<Block> REDWOOD_FENCE_GATE     = woodBlock("redwood_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.REDWOOD, p));
    public static final DeferredBlock<Block> REDWOOD_PRESSURE_PLATE = woodBlock("redwood_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.REDWOOD_SET, p));
    public static final DeferredBlock<Block> REDWOOD_BUTTON         = woodBlock("redwood_button",         p -> new GotButtonBlock(GotWoodTypes.REDWOOD_SET, p));

    // ── Chestnut Tree
    public static final DeferredBlock<Block> CHESTNUT_LOG            = woodBlock("chestnut_log",            ChestnutLogBlock::new);
    public static final DeferredBlock<Block> CHESTNUT_WOOD           = woodBlock("chestnut_wood",           ChestnutWoodBlock::new);
    public static final DeferredBlock<Block> CHESTNUT_PLANKS         = woodBlock("chestnut_planks",         ChestnutPlanksBlock::new);
    public static final DeferredBlock<Block> CHESTNUT_LEAVES         = woodBlock("chestnut_leaves",         ChestnutLeavesBlock::new);
    public static final DeferredBlock<Block> CHESTNUT_STAIRS         = woodBlock("chestnut_stairs",         ChestnutStairsBlock::new);
    public static final DeferredBlock<Block> CHESTNUT_SLAB           = woodBlock("chestnut_slab",           ChestnutSlabBlock::new);
    public static final DeferredBlock<Block> CHESTNUT_FENCE          = woodBlock("chestnut_fence",          ChestnutFenceBlock::new);
    public static final DeferredBlock<Block> CHESTNUT_FENCE_GATE     = woodBlock("chestnut_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.CHESTNUT, p));
    public static final DeferredBlock<Block> CHESTNUT_PRESSURE_PLATE = woodBlock("chestnut_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.CHESTNUT_SET, p));
    public static final DeferredBlock<Block> CHESTNUT_BUTTON         = woodBlock("chestnut_button",         p -> new GotButtonBlock(GotWoodTypes.CHESTNUT_SET, p));

    // ── Willow Tree
    public static final DeferredBlock<Block> WILLOW_LOG            = woodBlock("willow_log",            WillowLogBlock::new);
    public static final DeferredBlock<Block> WILLOW_WOOD           = woodBlock("willow_wood",           WillowWoodBlock::new);
    public static final DeferredBlock<Block> WILLOW_PLANKS         = woodBlock("willow_planks",         WillowPlanksBlock::new);
    public static final DeferredBlock<Block> WILLOW_LEAVES         = woodBlock("willow_leaves",         WillowLeavesBlock::new);
    public static final DeferredBlock<Block> WILLOW_STAIRS         = woodBlock("willow_stairs",         WillowStairsBlock::new);
    public static final DeferredBlock<Block> WILLOW_SLAB           = woodBlock("willow_slab",           WillowSlabBlock::new);
    public static final DeferredBlock<Block> WILLOW_FENCE          = woodBlock("willow_fence",          WillowFenceBlock::new);
    public static final DeferredBlock<Block> WILLOW_FENCE_GATE     = woodBlock("willow_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.WILLOW, p));
    public static final DeferredBlock<Block> WILLOW_PRESSURE_PLATE = woodBlock("willow_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.WILLOW_SET, p));
    public static final DeferredBlock<Block> WILLOW_BUTTON         = woodBlock("willow_button",         p -> new GotButtonBlock(GotWoodTypes.WILLOW_SET, p));

    // ── Wormtree Tree
    public static final DeferredBlock<Block> WORMTREE_LOG            = woodBlock("wormtree_log",            WormtreeLogBlock::new);
    public static final DeferredBlock<Block> WORMTREE_WOOD           = woodBlock("wormtree_wood",           WormtreeWoodBlock::new);
    public static final DeferredBlock<Block> WORMTREE_PLANKS         = woodBlock("wormtree_planks",         WormtreePlanksBlock::new);
    public static final DeferredBlock<Block> WORMTREE_LEAVES         = woodBlock("wormtree_leaves",         WormtreeLeavesBlock::new);
    public static final DeferredBlock<Block> WORMTREE_STAIRS         = woodBlock("wormtree_stairs",         WormtreeStairsBlock::new);
    public static final DeferredBlock<Block> WORMTREE_SLAB           = woodBlock("wormtree_slab",           WormtreeSlabBlock::new);
    public static final DeferredBlock<Block> WORMTREE_FENCE          = woodBlock("wormtree_fence",          WormtreeFenceBlock::new);
    public static final DeferredBlock<Block> WORMTREE_FENCE_GATE     = woodBlock("wormtree_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.WORMTREE, p));
    public static final DeferredBlock<Block> WORMTREE_PRESSURE_PLATE = woodBlock("wormtree_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.WORMTREE_SET, p));
    public static final DeferredBlock<Block> WORMTREE_BUTTON         = woodBlock("wormtree_button",         p -> new GotButtonBlock(GotWoodTypes.WORMTREE_SET, p));

    // ── Helpers ──────────────────────────────────────────────────────────

    /** Wood block: copies from OAK_PLANKS so no tool requirement, proper wood map-colour etc. */
    private static <B extends Block> DeferredBlock<B> woodBlock(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
        return REGISTRY.registerBlock(name, supplier, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
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