package net.got.init;

import net.got.block.*;
import net.got.block.RegionalRockBlock;
import net.got.block.RegionalRockPillarBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

import net.got.GotMod;

import java.util.Properties;
import java.util.function.Function;

public class GotModBlocks {
    public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(GotMod.MODID);
    public static final DeferredBlock<Block> WEIRWOOD_LOG = register("weirwood_log", WeirwoodLogBlock::new);
    public static final DeferredBlock<Block> WEIRWOOD_WOOD = register("weirwood_wood", WeirwoodWoodBlock::new);
    public static final DeferredBlock<Block> WEIRWOOD_PLANKS = register("weirwood_planks", WeirwoodPlanksBlock::new);
    public static final DeferredBlock<Block> WEIRWOOD_LEAVES = register("weirwood_leaves", WeirwoodLeavesBlock::new);
    public static final DeferredBlock<Block> WEIRWOOD_STAIRS = register("weirwood_stairs", WeirwoodStairsBlock::new);
    public static final DeferredBlock<Block> WEIRWOOD_SLAB = register("weirwood_slab", WeirwoodSlabBlock::new);
    public static final DeferredBlock<Block> WEIRWOOD_FENCE = register("weirwood_fence", WeirwoodFenceBlock::new);
    public static final DeferredBlock<Block> WEIRWOOD_FENCE_GATE = register("weirwood_fence_gate", WeirwoodFenceGateBlock::new);
    public static final DeferredBlock<Block> WEIRWOOD_PRESSURE_PLATE = register("weirwood_pressure_plate", WeirwoodPressurePlateBlock::new);
    public static final DeferredBlock<Block> WEIRWOOD_BUTTON = register("weirwood_button", WeirwoodButtonBlock::new);
    public static final DeferredBlock<Block> ASPEN_LOG = register("aspen_log", AspenLogBlock::new);
    public static final DeferredBlock<Block> ASPEN_WOOD = register("aspen_wood", AspenWoodBlock::new);
    public static final DeferredBlock<Block> ASPEN_PLANKS = register("aspen_planks", AspenPlanksBlock::new);
    public static final DeferredBlock<Block> ASPEN_LEAVES = register("aspen_leaves", AspenLeavesBlock::new);
    public static final DeferredBlock<Block> ASPEN_STAIRS = register("aspen_stairs", AspenStairsBlock::new);
    public static final DeferredBlock<Block> ASPEN_SLAB = register("aspen_slab", AspenSlabBlock::new);
    public static final DeferredBlock<Block> ASPEN_FENCE = register("aspen_fence", AspenFenceBlock::new);
    public static final DeferredBlock<Block> ASPEN_FENCE_GATE = register("aspen_fence_gate", AspenFenceGateBlock::new);
    public static final DeferredBlock<Block> ASPEN_PRESSURE_PLATE = register("aspen_pressure_plate", AspenPressurePlateBlock::new);
    public static final DeferredBlock<Block> ASPEN_BUTTON = register("aspen_button", AspenButtonBlock::new);
    public static final DeferredBlock<Block> ALDER_LOG = register("alder_log", AlderLogBlock::new);
    public static final DeferredBlock<Block> ALDER_WOOD = register("alder_wood", AlderWoodBlock::new);
    public static final DeferredBlock<Block> ALDER_PLANKS = register("alder_planks", AlderPlanksBlock::new);
    public static final DeferredBlock<Block> ALDER_LEAVES = register("alder_leaves", AlderLeavesBlock::new);
    public static final DeferredBlock<Block> ALDER_STAIRS = register("alder_stairs", AlderStairsBlock::new);
    public static final DeferredBlock<Block> ALDER_SLAB = register("alder_slab", AlderSlabBlock::new);
    public static final DeferredBlock<Block> ALDER_FENCE = register("alder_fence", AlderFenceBlock::new);
    public static final DeferredBlock<Block> ALDER_FENCE_GATE = register("alder_fence_gate", AlderFenceGateBlock::new);
    public static final DeferredBlock<Block> ALDER_PRESSURE_PLATE = register("alder_pressure_plate", AlderPressurePlateBlock::new);
    public static final DeferredBlock<Block> ALDER_BUTTON = register("alder_button", AlderButtonBlock::new);
    public static final DeferredBlock<Block> PINE_LOG = register("pine_log", PineLogBlock::new);
	public static final DeferredBlock<Block> PINE_WOOD = register("pine_wood", PineWoodBlock::new);
	public static final DeferredBlock<Block> PINE_PLANKS = register("pine_planks", PinePlanksBlock::new);
	public static final DeferredBlock<Block> PINE_LEAVES = register("pine_leaves", PineLeavesBlock::new);
	public static final DeferredBlock<Block> PINE_STAIRS = register("pine_stairs", PineStairsBlock::new);
	public static final DeferredBlock<Block> PINE_SLAB = register("pine_slab", PineSlabBlock::new);
	public static final DeferredBlock<Block> PINE_FENCE = register("pine_fence", PineFenceBlock::new);
	public static final DeferredBlock<Block> PINE_FENCE_GATE = register("pine_fence_gate", PineFenceGateBlock::new);
	public static final DeferredBlock<Block> PINE_PRESSURE_PLATE = register("pine_pressure_plate", PinePressurePlateBlock::new);
	public static final DeferredBlock<Block> PINE_BUTTON = register("pine_button", PineButtonBlock::new);
	public static final DeferredBlock<Block> FIR_LOG = register("fir_log", FirLogBlock::new);
	public static final DeferredBlock<Block> FIR_WOOD = register("fir_wood", FirWoodBlock::new);
	public static final DeferredBlock<Block> FIR_PLANKS = register("fir_planks", FirPlanksBlock::new);
	public static final DeferredBlock<Block> FIR_LEAVES = register("fir_leaves", FirLeavesBlock::new);
	public static final DeferredBlock<Block> FIR_STAIRS = register("fir_stairs", FirStairsBlock::new);
	public static final DeferredBlock<Block> FIR_SLAB = register("fir_slab", FirSlabBlock::new);
	public static final DeferredBlock<Block> FIR_FENCE = register("fir_fence", FirFenceBlock::new);
	public static final DeferredBlock<Block> FIR_FENCE_GATE = register("fir_fence_gate", FirFenceGateBlock::new);
	public static final DeferredBlock<Block> FIR_PRESSURE_PLATE = register("fir_pressure_plate", FirPressurePlateBlock::new);
	public static final DeferredBlock<Block> FIR_BUTTON = register("fir_button", FirButtonBlock::new);
	public static final DeferredBlock<Block> SENTINAL_LOG = register("sentinal_log", SentinalLogBlock::new);
	public static final DeferredBlock<Block> SENTINAL_WOOD = register("sentinal_wood", SentinalWoodBlock::new);
	public static final DeferredBlock<Block> SENTINAL_PLANKS = register("sentinal_planks", SentinalPlanksBlock::new);
	public static final DeferredBlock<Block> SENTINAL_LEAVES = register("sentinal_leaves", SentinalLeavesBlock::new);
	public static final DeferredBlock<Block> SENTINAL_STAIRS = register("sentinal_stairs", SentinalStairsBlock::new);
	public static final DeferredBlock<Block> SENTINAL_SLAB = register("sentinal_slab", SentinalSlabBlock::new);
	public static final DeferredBlock<Block> SENTINAL_FENCE = register("sentinal_fence", SentinalFenceBlock::new);
	public static final DeferredBlock<Block> SENTINAL_FENCE_GATE = register("sentinal_fence_gate", SentinalFenceGateBlock::new);
	public static final DeferredBlock<Block> SENTINAL_PRESSURE_PLATE = register("sentinal_pressure_plate", SentinalPressurePlateBlock::new);
	public static final DeferredBlock<Block> SENTINAL_BUTTON = register("sentinal_button", SentinalButtonBlock::new);
	public static final DeferredBlock<Block> IRONWOOD_LOG = register("ironwood_log", IronwoodLogBlock::new);
	public static final DeferredBlock<Block> IRONWOOD_WOOD = register("ironwood_wood", IronwoodWoodBlock::new);
	public static final DeferredBlock<Block> IRONWOOD_PLANKS = register("ironwood_planks", IronwoodPlanksBlock::new);
	public static final DeferredBlock<Block> IRONWOOD_LEAVES = register("ironwood_leaves", IronwoodLeavesBlock::new);
	public static final DeferredBlock<Block> IRONWOOD_STAIRS = register("ironwood_stairs", IronwoodStairsBlock::new);
	public static final DeferredBlock<Block> IRONWOOD_SLAB = register("ironwood_slab", IronwoodSlabBlock::new);
	public static final DeferredBlock<Block> IRONWOOD_FENCE = register("ironwood_fence", IronwoodFenceBlock::new);
	public static final DeferredBlock<Block> IRONWOOD_FENCE_GATE = register("ironwood_fence_gate", IronwoodFenceGateBlock::new);
	public static final DeferredBlock<Block> IRONWOOD_PRESSURE_PLATE = register("ironwood_pressure_plate", IronwoodPressurePlateBlock::new);
	public static final DeferredBlock<Block> IRONWOOD_BUTTON = register("ironwood_button", IronwoodButtonBlock::new);
	public static final DeferredBlock<Block> BEECH_LOG = register("beech_log", BeechLogBlock::new);
	public static final DeferredBlock<Block> BEECH_WOOD = register("beech_wood", BeechWoodBlock::new);
	public static final DeferredBlock<Block> BEECH_PLANKS = register("beech_planks", BeechPlanksBlock::new);
	public static final DeferredBlock<Block> BEECH_LEAVES = register("beech_leaves", BeechLeavesBlock::new);
	public static final DeferredBlock<Block> BEECH_STAIRS = register("beech_stairs", BeechStairsBlock::new);
	public static final DeferredBlock<Block> BEECH_SLAB = register("beech_slab", BeechSlabBlock::new);
	public static final DeferredBlock<Block> BEECH_FENCE = register("beech_fence", BeechFenceBlock::new);
	public static final DeferredBlock<Block> BEECH_FENCE_GATE = register("beech_fence_gate", BeechFenceGateBlock::new);
	public static final DeferredBlock<Block> BEECH_PRESSURE_PLATE = register("beech_pressure_plate", BeechPressurePlateBlock::new);
	public static final DeferredBlock<Block> BEECH_BUTTON = register("beech_button", BeechButtonBlock::new);
	public static final DeferredBlock<Block> SOLDIER_PINE_LOG = register("soldier_pine_log", SoldierPineLogBlock::new);
	public static final DeferredBlock<Block> SOLDIER_PINE_WOOD = register("soldier_pine_wood", SoldierPineWoodBlock::new);
	public static final DeferredBlock<Block> SOLDIER_PINE_PLANKS = register("soldier_pine_planks", SoldierPinePlanksBlock::new);
	public static final DeferredBlock<Block> SOLDIER_PINE_LEAVES = register("soldier_pine_leaves", SoldierPineLeavesBlock::new);
	public static final DeferredBlock<Block> SOLDIER_PINE_STAIRS = register("soldier_pine_stairs", SoldierPineStairsBlock::new);
	public static final DeferredBlock<Block> SOLDIER_PINE_SLAB = register("soldier_pine_slab", SoldierPineSlabBlock::new);
	public static final DeferredBlock<Block> SOLDIER_PINE_FENCE = register("soldier_pine_fence", SoldierPineFenceBlock::new);
	public static final DeferredBlock<Block> SOLDIER_PINE_FENCE_GATE = register("soldier_pine_fence_gate", SoldierPineFenceGateBlock::new);
	public static final DeferredBlock<Block> SOLDIER_PINE_PRESSURE_PLATE = register("soldier_pine_pressure_plate", SoldierPinePressurePlateBlock::new);
	public static final DeferredBlock<Block> SOLDIER_PINE_BUTTON = register("soldier_pine_button", SoldierPineButtonBlock::new);
    public static final DeferredBlock<Block> ASH_LOG = register("ash_log", AshLogBlock::new);
    public static final DeferredBlock<Block> ASH_WOOD = register("ash_wood", AshWoodBlock::new);
    public static final DeferredBlock<Block> ASH_PLANKS = register("ash_planks", AshPlanksBlock::new);
    public static final DeferredBlock<Block> ASH_LEAVES = register("ash_leaves", AshLeavesBlock::new);
    public static final DeferredBlock<Block> ASH_STAIRS = register("ash_stairs", AshStairsBlock::new);
    public static final DeferredBlock<Block> ASH_SLAB = register("ash_slab", AshSlabBlock::new);
    public static final DeferredBlock<Block> ASH_FENCE = register("ash_fence", AshFenceBlock::new);
    public static final DeferredBlock<Block> ASH_FENCE_GATE = register("ash_fence_gate", AshFenceGateBlock::new);
    public static final DeferredBlock<Block> ASH_PRESSURE_PLATE = register("ash_pressure_plate", AshPressurePlateBlock::new);
    public static final DeferredBlock<Block> ASH_BUTTON = register("ash_button", AshButtonBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_LOG = register("hawthorn_log", HawthornLogBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_WOOD = register("hawthorn_wood", HawthornWoodBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_PLANKS = register("hawthorn_planks", HawthornPlanksBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_LEAVES = register("hawthorn_leaves", HawthornLeavesBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_STAIRS = register("hawthorn_stairs", HawthornStairsBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_SLAB = register("hawthorn_slab", HawthornSlabBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_FENCE = register("hawthorn_fence", HawthornFenceBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_FENCE_GATE = register("hawthorn_fence_gate", HawthornFenceGateBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_PRESSURE_PLATE = register("hawthorn_pressure_plate", HawthornPressurePlateBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_BUTTON = register("hawthorn_button", HawthornButtonBlock::new);

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
    public static final DeferredBlock<Block> CROWNLANDS_BRICK_BUTTON = register("crownlands_brick_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> CROWNLANDS_BRICK_PRESSURE_PLATE = register("crownlands_brick_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> CRACKED_CROWNLANDS_BRICK_SLAB = register("cracked_crownlands_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_CROWNLANDS_BRICK_STAIRS = register("cracked_crownlands_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_CROWNLANDS_BRICK_WALL = register("cracked_crownlands_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> CRACKED_CROWNLANDS_BRICK_BUTTON = register("cracked_crownlands_brick_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> CRACKED_CROWNLANDS_BRICK_PRESSURE_PLATE = register("cracked_crownlands_brick_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> MOSSY_CROWNLANDS_BRICK_SLAB = register("mossy_crownlands_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_CROWNLANDS_BRICK_STAIRS = register("mossy_crownlands_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_CROWNLANDS_BRICK_WALL = register("mossy_crownlands_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_CROWNLANDS_BRICK_BUTTON = register("mossy_crownlands_brick_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> MOSSY_CROWNLANDS_BRICK_PRESSURE_PLATE = register("mossy_crownlands_brick_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> CROWNLANDS_COBBLESTONE_SLAB = register("crownlands_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CROWNLANDS_COBBLESTONE_STAIRS = register("crownlands_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CROWNLANDS_COBBLESTONE_WALL = register("crownlands_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> CROWNLANDS_COBBLESTONE_BUTTON = register("crownlands_cobblestone_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> CROWNLANDS_COBBLESTONE_PRESSURE_PLATE = register("crownlands_cobblestone_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> MOSSY_CROWNLANDS_COBBLESTONE_SLAB = register("mossy_crownlands_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_CROWNLANDS_COBBLESTONE_STAIRS = register("mossy_crownlands_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_CROWNLANDS_COBBLESTONE_WALL = register("mossy_crownlands_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_CROWNLANDS_COBBLESTONE_BUTTON = register("mossy_crownlands_cobblestone_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> MOSSY_CROWNLANDS_COBBLESTONE_PRESSURE_PLATE = register("mossy_crownlands_cobblestone_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> POLISHED_CROWNLANDS_ROCK_SLAB = register("polished_crownlands_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> POLISHED_CROWNLANDS_ROCK_STAIRS = register("polished_crownlands_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> POLISHED_CROWNLANDS_ROCK_WALL = register("polished_crownlands_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> POLISHED_CROWNLANDS_ROCK_BUTTON = register("polished_crownlands_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> POLISHED_CROWNLANDS_ROCK_PRESSURE_PLATE = register("polished_crownlands_rock_pressure_plate", RegionalRockPressurePlateBlock::new);

    // ── Dorne ─────────────────────────────────────────
    public static final DeferredBlock<Block> DORNE_ROCK_SLAB = register("dorne_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> DORNE_ROCK_STAIRS = register("dorne_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> DORNE_ROCK_WALL = register("dorne_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> DORNE_ROCK_BUTTON = register("dorne_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> DORNE_ROCK_PRESSURE_PLATE = register("dorne_rock_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> DORNE_BRICK_SLAB = register("dorne_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> DORNE_BRICK_STAIRS = register("dorne_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> DORNE_BRICK_WALL = register("dorne_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> DORNE_BRICK_BUTTON = register("dorne_brick_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> DORNE_BRICK_PRESSURE_PLATE = register("dorne_brick_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> CRACKED_DORNE_BRICK_SLAB = register("cracked_dorne_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_DORNE_BRICK_STAIRS = register("cracked_dorne_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_DORNE_BRICK_WALL = register("cracked_dorne_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> CRACKED_DORNE_BRICK_BUTTON = register("cracked_dorne_brick_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> CRACKED_DORNE_BRICK_PRESSURE_PLATE = register("cracked_dorne_brick_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> MOSSY_DORNE_BRICK_SLAB = register("mossy_dorne_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_DORNE_BRICK_STAIRS = register("mossy_dorne_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_DORNE_BRICK_WALL = register("mossy_dorne_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_DORNE_BRICK_BUTTON = register("mossy_dorne_brick_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> MOSSY_DORNE_BRICK_PRESSURE_PLATE = register("mossy_dorne_brick_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> DORNE_COBBLESTONE_SLAB = register("dorne_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> DORNE_COBBLESTONE_STAIRS = register("dorne_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> DORNE_COBBLESTONE_WALL = register("dorne_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> DORNE_COBBLESTONE_BUTTON = register("dorne_cobblestone_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> DORNE_COBBLESTONE_PRESSURE_PLATE = register("dorne_cobblestone_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> MOSSY_DORNE_COBBLESTONE_SLAB = register("mossy_dorne_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_DORNE_COBBLESTONE_STAIRS = register("mossy_dorne_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_DORNE_COBBLESTONE_WALL = register("mossy_dorne_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_DORNE_COBBLESTONE_BUTTON = register("mossy_dorne_cobblestone_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> MOSSY_DORNE_COBBLESTONE_PRESSURE_PLATE = register("mossy_dorne_cobblestone_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> POLISHED_DORNE_ROCK_SLAB = register("polished_dorne_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> POLISHED_DORNE_ROCK_STAIRS = register("polished_dorne_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> POLISHED_DORNE_ROCK_WALL = register("polished_dorne_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> POLISHED_DORNE_ROCK_BUTTON = register("polished_dorne_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> POLISHED_DORNE_ROCK_PRESSURE_PLATE = register("polished_dorne_rock_pressure_plate", RegionalRockPressurePlateBlock::new);

    // ── Iron Islands ─────────────────────────────────────────
    public static final DeferredBlock<Block> IRON_ISLANDS_ROCK_SLAB = register("iron_islands_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> IRON_ISLANDS_ROCK_STAIRS = register("iron_islands_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> IRON_ISLANDS_ROCK_WALL = register("iron_islands_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> IRON_ISLANDS_ROCK_BUTTON = register("iron_islands_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> IRON_ISLANDS_ROCK_PRESSURE_PLATE = register("iron_islands_rock_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> IRON_ISLANDS_BRICK_SLAB = register("iron_islands_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> IRON_ISLANDS_BRICK_STAIRS = register("iron_islands_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> IRON_ISLANDS_BRICK_WALL = register("iron_islands_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> IRON_ISLANDS_BRICK_BUTTON = register("iron_islands_brick_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> IRON_ISLANDS_BRICK_PRESSURE_PLATE = register("iron_islands_brick_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> CRACKED_IRON_ISLANDS_BRICK_SLAB = register("cracked_iron_islands_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_IRON_ISLANDS_BRICK_STAIRS = register("cracked_iron_islands_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_IRON_ISLANDS_BRICK_WALL = register("cracked_iron_islands_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> CRACKED_IRON_ISLANDS_BRICK_BUTTON = register("cracked_iron_islands_brick_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> CRACKED_IRON_ISLANDS_BRICK_PRESSURE_PLATE = register("cracked_iron_islands_brick_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> MOSSY_IRON_ISLANDS_BRICK_SLAB = register("mossy_iron_islands_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_IRON_ISLANDS_BRICK_STAIRS = register("mossy_iron_islands_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_IRON_ISLANDS_BRICK_WALL = register("mossy_iron_islands_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_IRON_ISLANDS_BRICK_BUTTON = register("mossy_iron_islands_brick_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> MOSSY_IRON_ISLANDS_BRICK_PRESSURE_PLATE = register("mossy_iron_islands_brick_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> IRON_ISLANDS_COBBLESTONE_SLAB = register("iron_islands_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> IRON_ISLANDS_COBBLESTONE_STAIRS = register("iron_islands_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> IRON_ISLANDS_COBBLESTONE_WALL = register("iron_islands_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> IRON_ISLANDS_COBBLESTONE_BUTTON = register("iron_islands_cobblestone_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> IRON_ISLANDS_COBBLESTONE_PRESSURE_PLATE = register("iron_islands_cobblestone_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> MOSSY_IRON_ISLANDS_COBBLESTONE_SLAB = register("mossy_iron_islands_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_IRON_ISLANDS_COBBLESTONE_STAIRS = register("mossy_iron_islands_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_IRON_ISLANDS_COBBLESTONE_WALL = register("mossy_iron_islands_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_IRON_ISLANDS_COBBLESTONE_BUTTON = register("mossy_iron_islands_cobblestone_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> MOSSY_IRON_ISLANDS_COBBLESTONE_PRESSURE_PLATE = register("mossy_iron_islands_cobblestone_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> POLISHED_IRON_ISLANDS_ROCK_SLAB = register("polished_iron_islands_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> POLISHED_IRON_ISLANDS_ROCK_STAIRS = register("polished_iron_islands_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> POLISHED_IRON_ISLANDS_ROCK_WALL = register("polished_iron_islands_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> POLISHED_IRON_ISLANDS_ROCK_BUTTON = register("polished_iron_islands_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> POLISHED_IRON_ISLANDS_ROCK_PRESSURE_PLATE = register("polished_iron_islands_rock_pressure_plate", RegionalRockPressurePlateBlock::new);

    // ── North ─────────────────────────────────────────
    public static final DeferredBlock<Block> NORTH_ROCK_SLAB = register("north_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> NORTH_ROCK_STAIRS = register("north_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> NORTH_ROCK_WALL = register("north_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> NORTH_ROCK_BUTTON = register("north_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> NORTH_ROCK_PRESSURE_PLATE = register("north_rock_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> NORTH_BRICK_SLAB = register("north_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> NORTH_BRICK_STAIRS = register("north_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> NORTH_BRICK_WALL = register("north_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> NORTH_BRICK_BUTTON = register("north_brick_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> NORTH_BRICK_PRESSURE_PLATE = register("north_brick_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> CRACKED_NORTH_BRICK_SLAB = register("cracked_north_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_NORTH_BRICK_STAIRS = register("cracked_north_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_NORTH_BRICK_WALL = register("cracked_north_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> CRACKED_NORTH_BRICK_BUTTON = register("cracked_north_brick_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> CRACKED_NORTH_BRICK_PRESSURE_PLATE = register("cracked_north_brick_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> MOSSY_NORTH_BRICK_SLAB = register("mossy_north_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_NORTH_BRICK_STAIRS = register("mossy_north_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_NORTH_BRICK_WALL = register("mossy_north_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_NORTH_BRICK_BUTTON = register("mossy_north_brick_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> MOSSY_NORTH_BRICK_PRESSURE_PLATE = register("mossy_north_brick_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> NORTH_COBBLESTONE_SLAB = register("north_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> NORTH_COBBLESTONE_STAIRS = register("north_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> NORTH_COBBLESTONE_WALL = register("north_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> NORTH_COBBLESTONE_BUTTON = register("north_cobblestone_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> NORTH_COBBLESTONE_PRESSURE_PLATE = register("north_cobblestone_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> MOSSY_NORTH_COBBLESTONE_SLAB = register("mossy_north_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_NORTH_COBBLESTONE_STAIRS = register("mossy_north_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_NORTH_COBBLESTONE_WALL = register("mossy_north_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_NORTH_COBBLESTONE_BUTTON = register("mossy_north_cobblestone_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> MOSSY_NORTH_COBBLESTONE_PRESSURE_PLATE = register("mossy_north_cobblestone_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> POLISHED_NORTH_ROCK_SLAB = register("polished_north_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> POLISHED_NORTH_ROCK_STAIRS = register("polished_north_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> POLISHED_NORTH_ROCK_WALL = register("polished_north_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> POLISHED_NORTH_ROCK_BUTTON = register("polished_north_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> POLISHED_NORTH_ROCK_PRESSURE_PLATE = register("polished_north_rock_pressure_plate", RegionalRockPressurePlateBlock::new);

    // ── Reach ─────────────────────────────────────────
    public static final DeferredBlock<Block> REACH_ROCK_SLAB = register("reach_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> REACH_ROCK_STAIRS = register("reach_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> REACH_ROCK_WALL = register("reach_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> REACH_ROCK_BUTTON = register("reach_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> REACH_ROCK_PRESSURE_PLATE = register("reach_rock_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> REACH_BRICK_SLAB = register("reach_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> REACH_BRICK_STAIRS = register("reach_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> REACH_BRICK_WALL = register("reach_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> REACH_BRICK_BUTTON = register("reach_brick_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> REACH_BRICK_PRESSURE_PLATE = register("reach_brick_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> CRACKED_REACH_BRICK_SLAB = register("cracked_reach_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_REACH_BRICK_STAIRS = register("cracked_reach_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_REACH_BRICK_WALL = register("cracked_reach_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> CRACKED_REACH_BRICK_BUTTON = register("cracked_reach_brick_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> CRACKED_REACH_BRICK_PRESSURE_PLATE = register("cracked_reach_brick_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> MOSSY_REACH_BRICK_SLAB = register("mossy_reach_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_REACH_BRICK_STAIRS = register("mossy_reach_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_REACH_BRICK_WALL = register("mossy_reach_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_REACH_BRICK_BUTTON = register("mossy_reach_brick_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> MOSSY_REACH_BRICK_PRESSURE_PLATE = register("mossy_reach_brick_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> REACH_COBBLESTONE_SLAB = register("reach_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> REACH_COBBLESTONE_STAIRS = register("reach_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> REACH_COBBLESTONE_WALL = register("reach_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> REACH_COBBLESTONE_BUTTON = register("reach_cobblestone_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> REACH_COBBLESTONE_PRESSURE_PLATE = register("reach_cobblestone_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> MOSSY_REACH_COBBLESTONE_SLAB = register("mossy_reach_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_REACH_COBBLESTONE_STAIRS = register("mossy_reach_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_REACH_COBBLESTONE_WALL = register("mossy_reach_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_REACH_COBBLESTONE_BUTTON = register("mossy_reach_cobblestone_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> MOSSY_REACH_COBBLESTONE_PRESSURE_PLATE = register("mossy_reach_cobblestone_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> POLISHED_REACH_ROCK_SLAB = register("polished_reach_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> POLISHED_REACH_ROCK_STAIRS = register("polished_reach_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> POLISHED_REACH_ROCK_WALL = register("polished_reach_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> POLISHED_REACH_ROCK_BUTTON = register("polished_reach_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> POLISHED_REACH_ROCK_PRESSURE_PLATE = register("polished_reach_rock_pressure_plate", RegionalRockPressurePlateBlock::new);

    // ── Riverlands ─────────────────────────────────────────
    public static final DeferredBlock<Block> RIVERLANDS_ROCK_SLAB = register("riverlands_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> RIVERLANDS_ROCK_STAIRS = register("riverlands_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> RIVERLANDS_ROCK_WALL = register("riverlands_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> RIVERLANDS_ROCK_BUTTON = register("riverlands_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> RIVERLANDS_ROCK_PRESSURE_PLATE = register("riverlands_rock_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> RIVERLANDS_BRICK_SLAB = register("riverlands_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> RIVERLANDS_BRICK_STAIRS = register("riverlands_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> RIVERLANDS_BRICK_WALL = register("riverlands_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> RIVERLANDS_BRICK_BUTTON = register("riverlands_brick_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> RIVERLANDS_BRICK_PRESSURE_PLATE = register("riverlands_brick_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> CRACKED_RIVERLANDS_BRICK_SLAB = register("cracked_riverlands_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_RIVERLANDS_BRICK_STAIRS = register("cracked_riverlands_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_RIVERLANDS_BRICK_WALL = register("cracked_riverlands_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> CRACKED_RIVERLANDS_BRICK_BUTTON = register("cracked_riverlands_brick_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> CRACKED_RIVERLANDS_BRICK_PRESSURE_PLATE = register("cracked_riverlands_brick_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> MOSSY_RIVERLANDS_BRICK_SLAB = register("mossy_riverlands_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_RIVERLANDS_BRICK_STAIRS = register("mossy_riverlands_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_RIVERLANDS_BRICK_WALL = register("mossy_riverlands_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_RIVERLANDS_BRICK_BUTTON = register("mossy_riverlands_brick_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> MOSSY_RIVERLANDS_BRICK_PRESSURE_PLATE = register("mossy_riverlands_brick_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> RIVERLANDS_COBBLESTONE_SLAB = register("riverlands_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> RIVERLANDS_COBBLESTONE_STAIRS = register("riverlands_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> RIVERLANDS_COBBLESTONE_WALL = register("riverlands_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> RIVERLANDS_COBBLESTONE_BUTTON = register("riverlands_cobblestone_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> RIVERLANDS_COBBLESTONE_PRESSURE_PLATE = register("riverlands_cobblestone_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> MOSSY_RIVERLANDS_COBBLESTONE_SLAB = register("mossy_riverlands_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_RIVERLANDS_COBBLESTONE_STAIRS = register("mossy_riverlands_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_RIVERLANDS_COBBLESTONE_WALL = register("mossy_riverlands_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_RIVERLANDS_COBBLESTONE_BUTTON = register("mossy_riverlands_cobblestone_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> MOSSY_RIVERLANDS_COBBLESTONE_PRESSURE_PLATE = register("mossy_riverlands_cobblestone_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> POLISHED_RIVERLANDS_ROCK_SLAB = register("polished_riverlands_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> POLISHED_RIVERLANDS_ROCK_STAIRS = register("polished_riverlands_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> POLISHED_RIVERLANDS_ROCK_WALL = register("polished_riverlands_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> POLISHED_RIVERLANDS_ROCK_BUTTON = register("polished_riverlands_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> POLISHED_RIVERLANDS_ROCK_PRESSURE_PLATE = register("polished_riverlands_rock_pressure_plate", RegionalRockPressurePlateBlock::new);

    // ── Stormlands ─────────────────────────────────────────
    public static final DeferredBlock<Block> STORMLANDS_ROCK_SLAB = register("stormlands_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> STORMLANDS_ROCK_STAIRS = register("stormlands_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> STORMLANDS_ROCK_WALL = register("stormlands_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> STORMLANDS_ROCK_BUTTON = register("stormlands_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> STORMLANDS_ROCK_PRESSURE_PLATE = register("stormlands_rock_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> STORMLANDS_BRICK_SLAB = register("stormlands_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> STORMLANDS_BRICK_STAIRS = register("stormlands_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> STORMLANDS_BRICK_WALL = register("stormlands_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> STORMLANDS_BRICK_BUTTON = register("stormlands_brick_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> STORMLANDS_BRICK_PRESSURE_PLATE = register("stormlands_brick_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> CRACKED_STORMLANDS_BRICK_SLAB = register("cracked_stormlands_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_STORMLANDS_BRICK_STAIRS = register("cracked_stormlands_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_STORMLANDS_BRICK_WALL = register("cracked_stormlands_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> CRACKED_STORMLANDS_BRICK_BUTTON = register("cracked_stormlands_brick_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> CRACKED_STORMLANDS_BRICK_PRESSURE_PLATE = register("cracked_stormlands_brick_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> MOSSY_STORMLANDS_BRICK_SLAB = register("mossy_stormlands_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_STORMLANDS_BRICK_STAIRS = register("mossy_stormlands_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_STORMLANDS_BRICK_WALL = register("mossy_stormlands_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_STORMLANDS_BRICK_BUTTON = register("mossy_stormlands_brick_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> MOSSY_STORMLANDS_BRICK_PRESSURE_PLATE = register("mossy_stormlands_brick_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> STORMLANDS_COBBLESTONE_SLAB = register("stormlands_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> STORMLANDS_COBBLESTONE_STAIRS = register("stormlands_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> STORMLANDS_COBBLESTONE_WALL = register("stormlands_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> STORMLANDS_COBBLESTONE_BUTTON = register("stormlands_cobblestone_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> STORMLANDS_COBBLESTONE_PRESSURE_PLATE = register("stormlands_cobblestone_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> MOSSY_STORMLANDS_COBBLESTONE_SLAB = register("mossy_stormlands_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_STORMLANDS_COBBLESTONE_STAIRS = register("mossy_stormlands_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_STORMLANDS_COBBLESTONE_WALL = register("mossy_stormlands_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_STORMLANDS_COBBLESTONE_BUTTON = register("mossy_stormlands_cobblestone_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> MOSSY_STORMLANDS_COBBLESTONE_PRESSURE_PLATE = register("mossy_stormlands_cobblestone_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> POLISHED_STORMLANDS_ROCK_SLAB = register("polished_stormlands_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> POLISHED_STORMLANDS_ROCK_STAIRS = register("polished_stormlands_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> POLISHED_STORMLANDS_ROCK_WALL = register("polished_stormlands_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> POLISHED_STORMLANDS_ROCK_BUTTON = register("polished_stormlands_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> POLISHED_STORMLANDS_ROCK_PRESSURE_PLATE = register("polished_stormlands_rock_pressure_plate", RegionalRockPressurePlateBlock::new);

    // ── Vale ─────────────────────────────────────────
    public static final DeferredBlock<Block> VALE_ROCK_SLAB = register("vale_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> VALE_ROCK_STAIRS = register("vale_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> VALE_ROCK_WALL = register("vale_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> VALE_ROCK_BUTTON = register("vale_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> VALE_ROCK_PRESSURE_PLATE = register("vale_rock_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> VALE_BRICK_SLAB = register("vale_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> VALE_BRICK_STAIRS = register("vale_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> VALE_BRICK_WALL = register("vale_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> VALE_BRICK_BUTTON = register("vale_brick_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> VALE_BRICK_PRESSURE_PLATE = register("vale_brick_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> CRACKED_VALE_BRICK_SLAB = register("cracked_vale_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_VALE_BRICK_STAIRS = register("cracked_vale_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_VALE_BRICK_WALL = register("cracked_vale_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> CRACKED_VALE_BRICK_BUTTON = register("cracked_vale_brick_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> CRACKED_VALE_BRICK_PRESSURE_PLATE = register("cracked_vale_brick_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> MOSSY_VALE_BRICK_SLAB = register("mossy_vale_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_VALE_BRICK_STAIRS = register("mossy_vale_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_VALE_BRICK_WALL = register("mossy_vale_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_VALE_BRICK_BUTTON = register("mossy_vale_brick_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> MOSSY_VALE_BRICK_PRESSURE_PLATE = register("mossy_vale_brick_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> VALE_COBBLESTONE_SLAB = register("vale_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> VALE_COBBLESTONE_STAIRS = register("vale_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> VALE_COBBLESTONE_WALL = register("vale_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> VALE_COBBLESTONE_BUTTON = register("vale_cobblestone_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> VALE_COBBLESTONE_PRESSURE_PLATE = register("vale_cobblestone_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> MOSSY_VALE_COBBLESTONE_SLAB = register("mossy_vale_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_VALE_COBBLESTONE_STAIRS = register("mossy_vale_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_VALE_COBBLESTONE_WALL = register("mossy_vale_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_VALE_COBBLESTONE_BUTTON = register("mossy_vale_cobblestone_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> MOSSY_VALE_COBBLESTONE_PRESSURE_PLATE = register("mossy_vale_cobblestone_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> POLISHED_VALE_ROCK_SLAB = register("polished_vale_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> POLISHED_VALE_ROCK_STAIRS = register("polished_vale_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> POLISHED_VALE_ROCK_WALL = register("polished_vale_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> POLISHED_VALE_ROCK_BUTTON = register("polished_vale_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> POLISHED_VALE_ROCK_PRESSURE_PLATE = register("polished_vale_rock_pressure_plate", RegionalRockPressurePlateBlock::new);

    // ── Westerlands ─────────────────────────────────────────
    public static final DeferredBlock<Block> WESTERLANDS_ROCK_SLAB = register("westerlands_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> WESTERLANDS_ROCK_STAIRS = register("westerlands_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> WESTERLANDS_ROCK_WALL = register("westerlands_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> WESTERLANDS_ROCK_BUTTON = register("westerlands_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> WESTERLANDS_ROCK_PRESSURE_PLATE = register("westerlands_rock_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> WESTERLANDS_BRICK_SLAB = register("westerlands_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> WESTERLANDS_BRICK_STAIRS = register("westerlands_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> WESTERLANDS_BRICK_WALL = register("westerlands_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> WESTERLANDS_BRICK_BUTTON = register("westerlands_brick_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> WESTERLANDS_BRICK_PRESSURE_PLATE = register("westerlands_brick_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> CRACKED_WESTERLANDS_BRICK_SLAB = register("cracked_westerlands_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> CRACKED_WESTERLANDS_BRICK_STAIRS = register("cracked_westerlands_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> CRACKED_WESTERLANDS_BRICK_WALL = register("cracked_westerlands_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> CRACKED_WESTERLANDS_BRICK_BUTTON = register("cracked_westerlands_brick_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> CRACKED_WESTERLANDS_BRICK_PRESSURE_PLATE = register("cracked_westerlands_brick_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> MOSSY_WESTERLANDS_BRICK_SLAB = register("mossy_westerlands_brick_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_WESTERLANDS_BRICK_STAIRS = register("mossy_westerlands_brick_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_WESTERLANDS_BRICK_WALL = register("mossy_westerlands_brick_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_WESTERLANDS_BRICK_BUTTON = register("mossy_westerlands_brick_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> MOSSY_WESTERLANDS_BRICK_PRESSURE_PLATE = register("mossy_westerlands_brick_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> WESTERLANDS_COBBLESTONE_SLAB = register("westerlands_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> WESTERLANDS_COBBLESTONE_STAIRS = register("westerlands_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> WESTERLANDS_COBBLESTONE_WALL = register("westerlands_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> WESTERLANDS_COBBLESTONE_BUTTON = register("westerlands_cobblestone_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> WESTERLANDS_COBBLESTONE_PRESSURE_PLATE = register("westerlands_cobblestone_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> MOSSY_WESTERLANDS_COBBLESTONE_SLAB = register("mossy_westerlands_cobblestone_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> MOSSY_WESTERLANDS_COBBLESTONE_STAIRS = register("mossy_westerlands_cobblestone_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> MOSSY_WESTERLANDS_COBBLESTONE_WALL = register("mossy_westerlands_cobblestone_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> MOSSY_WESTERLANDS_COBBLESTONE_BUTTON = register("mossy_westerlands_cobblestone_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> MOSSY_WESTERLANDS_COBBLESTONE_PRESSURE_PLATE = register("mossy_westerlands_cobblestone_pressure_plate", RegionalRockPressurePlateBlock::new);

    public static final DeferredBlock<Block> POLISHED_WESTERLANDS_ROCK_SLAB = register("polished_westerlands_rock_slab", RegionalRockSlabBlock::new);
    public static final DeferredBlock<Block> POLISHED_WESTERLANDS_ROCK_STAIRS = register("polished_westerlands_rock_stairs", RegionalRockStairsBlock::new);
    public static final DeferredBlock<Block> POLISHED_WESTERLANDS_ROCK_WALL = register("polished_westerlands_rock_wall", RegionalRockWallBlock::new);
    public static final DeferredBlock<Block> POLISHED_WESTERLANDS_ROCK_BUTTON = register("polished_westerlands_rock_button", RegionalRockButtonBlock::new);
    public static final DeferredBlock<Block> POLISHED_WESTERLANDS_ROCK_PRESSURE_PLATE = register("polished_westerlands_rock_pressure_plate", RegionalRockPressurePlateBlock::new);
    private static <B extends Block> DeferredBlock<B> register(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
        return REGISTRY.registerBlock(name, supplier, BlockBehaviour.Properties.of());
    }
}