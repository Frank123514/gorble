package net.got.init;

import net.got.block.*;
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
import net.got.init.GotWoodTypes;

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
    public static final DeferredBlock<Block> WEIRWOOD_FENCE_GATE = register("weirwood_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.WEIRWOOD, p));
    public static final DeferredBlock<Block> WEIRWOOD_PRESSURE_PLATE = register("weirwood_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.WEIRWOOD_SET, p));
    public static final DeferredBlock<Block> WEIRWOOD_BUTTON = register("weirwood_button", p -> new GotButtonBlock(GotWoodTypes.WEIRWOOD_SET, p));
    public static final DeferredBlock<Block> ASPEN_LOG = register("aspen_log", AspenLogBlock::new);
    public static final DeferredBlock<Block> ASPEN_WOOD = register("aspen_wood", AspenWoodBlock::new);
    public static final DeferredBlock<Block> ASPEN_PLANKS = register("aspen_planks", AspenPlanksBlock::new);
    public static final DeferredBlock<Block> ASPEN_LEAVES = register("aspen_leaves", AspenLeavesBlock::new);
    public static final DeferredBlock<Block> ASPEN_STAIRS = register("aspen_stairs", AspenStairsBlock::new);
    public static final DeferredBlock<Block> ASPEN_SLAB = register("aspen_slab", AspenSlabBlock::new);
    public static final DeferredBlock<Block> ASPEN_FENCE = register("aspen_fence", AspenFenceBlock::new);
    public static final DeferredBlock<Block> ASPEN_FENCE_GATE = register("aspen_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.ASPEN, p));
    public static final DeferredBlock<Block> ASPEN_PRESSURE_PLATE = register("aspen_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.ASPEN_SET, p));
    public static final DeferredBlock<Block> ASPEN_BUTTON = register("aspen_button", p -> new GotButtonBlock(GotWoodTypes.ASPEN_SET, p));
    public static final DeferredBlock<Block> ALDER_LOG = register("alder_log", AlderLogBlock::new);
    public static final DeferredBlock<Block> ALDER_WOOD = register("alder_wood", AlderWoodBlock::new);
    public static final DeferredBlock<Block> ALDER_PLANKS = register("alder_planks", AlderPlanksBlock::new);
    public static final DeferredBlock<Block> ALDER_LEAVES = register("alder_leaves", AlderLeavesBlock::new);
    public static final DeferredBlock<Block> ALDER_STAIRS = register("alder_stairs", AlderStairsBlock::new);
    public static final DeferredBlock<Block> ALDER_SLAB = register("alder_slab", AlderSlabBlock::new);
    public static final DeferredBlock<Block> ALDER_FENCE = register("alder_fence", AlderFenceBlock::new);
    public static final DeferredBlock<Block> ALDER_FENCE_GATE = register("alder_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.ALDER, p));
    public static final DeferredBlock<Block> ALDER_PRESSURE_PLATE = register("alder_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.ALDER_SET, p));
    public static final DeferredBlock<Block> ALDER_BUTTON = register("alder_button", p -> new GotButtonBlock(GotWoodTypes.ALDER_SET, p));
    public static final DeferredBlock<Block> PINE_LOG = register("pine_log", PineLogBlock::new);
    public static final DeferredBlock<Block> PINE_WOOD = register("pine_wood", PineWoodBlock::new);
    public static final DeferredBlock<Block> PINE_PLANKS = register("pine_planks", PinePlanksBlock::new);
    public static final DeferredBlock<Block> PINE_LEAVES = register("pine_leaves", PineLeavesBlock::new);
    public static final DeferredBlock<Block> PINE_STAIRS = register("pine_stairs", PineStairsBlock::new);
    public static final DeferredBlock<Block> PINE_SLAB = register("pine_slab", PineSlabBlock::new);
    public static final DeferredBlock<Block> PINE_FENCE = register("pine_fence", PineFenceBlock::new);
    public static final DeferredBlock<Block> PINE_FENCE_GATE = register("pine_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.PINE, p));
    public static final DeferredBlock<Block> PINE_PRESSURE_PLATE = register("pine_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.PINE_SET, p));
    public static final DeferredBlock<Block> PINE_BUTTON = register("pine_button", p -> new GotButtonBlock(GotWoodTypes.PINE_SET, p));
    public static final DeferredBlock<Block> FIR_LOG = register("fir_log", FirLogBlock::new);
    public static final DeferredBlock<Block> FIR_WOOD = register("fir_wood", FirWoodBlock::new);
    public static final DeferredBlock<Block> FIR_PLANKS = register("fir_planks", FirPlanksBlock::new);
    public static final DeferredBlock<Block> FIR_LEAVES = register("fir_leaves", FirLeavesBlock::new);
    public static final DeferredBlock<Block> FIR_STAIRS = register("fir_stairs", FirStairsBlock::new);
    public static final DeferredBlock<Block> FIR_SLAB = register("fir_slab", FirSlabBlock::new);
    public static final DeferredBlock<Block> FIR_FENCE = register("fir_fence", FirFenceBlock::new);
    public static final DeferredBlock<Block> FIR_FENCE_GATE = register("fir_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.FIR, p));
    public static final DeferredBlock<Block> FIR_PRESSURE_PLATE = register("fir_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.FIR_SET, p));
    public static final DeferredBlock<Block> FIR_BUTTON = register("fir_button", p -> new GotButtonBlock(GotWoodTypes.FIR_SET, p));
    public static final DeferredBlock<Block> SENTINAL_LOG = register("sentinal_log", SentinalLogBlock::new);
    public static final DeferredBlock<Block> SENTINAL_WOOD = register("sentinal_wood", SentinalWoodBlock::new);
    public static final DeferredBlock<Block> SENTINAL_PLANKS = register("sentinal_planks", SentinalPlanksBlock::new);
    public static final DeferredBlock<Block> SENTINAL_LEAVES = register("sentinal_leaves", SentinalLeavesBlock::new);
    public static final DeferredBlock<Block> SENTINAL_STAIRS = register("sentinal_stairs", SentinalStairsBlock::new);
    public static final DeferredBlock<Block> SENTINAL_SLAB = register("sentinal_slab", SentinalSlabBlock::new);
    public static final DeferredBlock<Block> SENTINAL_FENCE = register("sentinal_fence", SentinalFenceBlock::new);
    public static final DeferredBlock<Block> SENTINAL_FENCE_GATE = register("sentinal_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.SENTINAL, p));
    public static final DeferredBlock<Block> SENTINAL_PRESSURE_PLATE = register("sentinal_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.SENTINAL_SET, p));
    public static final DeferredBlock<Block> SENTINAL_BUTTON = register("sentinal_button", p -> new GotButtonBlock(GotWoodTypes.SENTINAL_SET, p));
    public static final DeferredBlock<Block> IRONWOOD_LOG = register("ironwood_log", IronwoodLogBlock::new);
    public static final DeferredBlock<Block> IRONWOOD_WOOD = register("ironwood_wood", IronwoodWoodBlock::new);
    public static final DeferredBlock<Block> IRONWOOD_PLANKS = register("ironwood_planks", IronwoodPlanksBlock::new);
    public static final DeferredBlock<Block> IRONWOOD_LEAVES = register("ironwood_leaves", IronwoodLeavesBlock::new);
    public static final DeferredBlock<Block> IRONWOOD_STAIRS = register("ironwood_stairs", IronwoodStairsBlock::new);
    public static final DeferredBlock<Block> IRONWOOD_SLAB = register("ironwood_slab", IronwoodSlabBlock::new);
    public static final DeferredBlock<Block> IRONWOOD_FENCE = register("ironwood_fence", IronwoodFenceBlock::new);
    public static final DeferredBlock<Block> IRONWOOD_FENCE_GATE = register("ironwood_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.IRONWOOD, p));
    public static final DeferredBlock<Block> IRONWOOD_PRESSURE_PLATE = register("ironwood_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.IRONWOOD_SET, p));
    public static final DeferredBlock<Block> IRONWOOD_BUTTON = register("ironwood_button", p -> new GotButtonBlock(GotWoodTypes.IRONWOOD_SET, p));
    public static final DeferredBlock<Block> BEECH_LOG = register("beech_log", BeechLogBlock::new);
    public static final DeferredBlock<Block> BEECH_WOOD = register("beech_wood", BeechWoodBlock::new);
    public static final DeferredBlock<Block> BEECH_PLANKS = register("beech_planks", BeechPlanksBlock::new);
    public static final DeferredBlock<Block> BEECH_LEAVES = register("beech_leaves", BeechLeavesBlock::new);
    public static final DeferredBlock<Block> BEECH_STAIRS = register("beech_stairs", BeechStairsBlock::new);
    public static final DeferredBlock<Block> BEECH_SLAB = register("beech_slab", BeechSlabBlock::new);
    public static final DeferredBlock<Block> BEECH_FENCE = register("beech_fence", BeechFenceBlock::new);
    public static final DeferredBlock<Block> BEECH_FENCE_GATE = register("beech_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.BEECH, p));
    public static final DeferredBlock<Block> BEECH_PRESSURE_PLATE = register("beech_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.BEECH_SET, p));
    public static final DeferredBlock<Block> BEECH_BUTTON = register("beech_button", p -> new GotButtonBlock(GotWoodTypes.BEECH_SET, p));
    public static final DeferredBlock<Block> SOLDIER_PINE_LOG = register("soldier_pine_log", SoldierPineLogBlock::new);
    public static final DeferredBlock<Block> SOLDIER_PINE_WOOD = register("soldier_pine_wood", SoldierPineWoodBlock::new);
    public static final DeferredBlock<Block> SOLDIER_PINE_PLANKS = register("soldier_pine_planks", SoldierPinePlanksBlock::new);
    public static final DeferredBlock<Block> SOLDIER_PINE_LEAVES = register("soldier_pine_leaves", SoldierPineLeavesBlock::new);
    public static final DeferredBlock<Block> SOLDIER_PINE_STAIRS = register("soldier_pine_stairs", SoldierPineStairsBlock::new);
    public static final DeferredBlock<Block> SOLDIER_PINE_SLAB = register("soldier_pine_slab", SoldierPineSlabBlock::new);
    public static final DeferredBlock<Block> SOLDIER_PINE_FENCE = register("soldier_pine_fence", SoldierPineFenceBlock::new);
    public static final DeferredBlock<Block> SOLDIER_PINE_FENCE_GATE = register("soldier_pine_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.PINE, p));
    public static final DeferredBlock<Block> SOLDIER_PINE_PRESSURE_PLATE = register("soldier_pine_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.PINE_SET, p));
    public static final DeferredBlock<Block> SOLDIER_PINE_BUTTON = register("soldier_pine_button", p -> new GotButtonBlock(GotWoodTypes.PINE_SET, p));
    public static final DeferredBlock<Block> ASH_LOG = register("ash_log", AshLogBlock::new);
    public static final DeferredBlock<Block> ASH_WOOD = register("ash_wood", AshWoodBlock::new);
    public static final DeferredBlock<Block> ASH_PLANKS = register("ash_planks", AshPlanksBlock::new);
    public static final DeferredBlock<Block> ASH_LEAVES = register("ash_leaves", AshLeavesBlock::new);
    public static final DeferredBlock<Block> ASH_STAIRS = register("ash_stairs", AshStairsBlock::new);
    public static final DeferredBlock<Block> ASH_SLAB = register("ash_slab", AshSlabBlock::new);
    public static final DeferredBlock<Block> ASH_FENCE = register("ash_fence", AshFenceBlock::new);
    public static final DeferredBlock<Block> ASH_FENCE_GATE = register("ash_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.ASH, p));
    public static final DeferredBlock<Block> ASH_PRESSURE_PLATE = register("ash_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.ASH_SET, p));
    public static final DeferredBlock<Block> ASH_BUTTON = register("ash_button", p -> new GotButtonBlock(GotWoodTypes.ASH_SET, p));
    public static final DeferredBlock<Block> HAWTHORN_LOG = register("hawthorn_log", HawthornLogBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_WOOD = register("hawthorn_wood", HawthornWoodBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_PLANKS = register("hawthorn_planks", HawthornPlanksBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_LEAVES = register("hawthorn_leaves", HawthornLeavesBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_STAIRS = register("hawthorn_stairs", HawthornStairsBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_SLAB = register("hawthorn_slab", HawthornSlabBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_FENCE = register("hawthorn_fence", HawthornFenceBlock::new);
    public static final DeferredBlock<Block> HAWTHORN_FENCE_GATE = register("hawthorn_fence_gate", p -> new GotFenceGateBlock(GotWoodTypes.HAWTHORN, p));
    public static final DeferredBlock<Block> HAWTHORN_PRESSURE_PLATE = register("hawthorn_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.HAWTHORN_SET, p));
    public static final DeferredBlock<Block> HAWTHORN_BUTTON = register("hawthorn_button", p -> new GotButtonBlock(GotWoodTypes.HAWTHORN_SET, p));

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
    public static final DeferredBlock<Block> BLACKBARK_LOG            = register("blackbark_log",            BlackbarkLogBlock::new);
    public static final DeferredBlock<Block> BLACKBARK_WOOD           = register("blackbark_wood",           BlackbarkWoodBlock::new);
    public static final DeferredBlock<Block> BLACKBARK_PLANKS         = register("blackbark_planks",         BlackbarkPlanksBlock::new);
    public static final DeferredBlock<Block> BLACKBARK_LEAVES         = register("blackbark_leaves",         BlackbarkLeavesBlock::new);
    public static final DeferredBlock<Block> BLACKBARK_STAIRS         = register("blackbark_stairs",         BlackbarkStairsBlock::new);
    public static final DeferredBlock<Block> BLACKBARK_SLAB           = register("blackbark_slab",           BlackbarkSlabBlock::new);
    public static final DeferredBlock<Block> BLACKBARK_FENCE          = register("blackbark_fence",          BlackbarkFenceBlock::new);
    public static final DeferredBlock<Block> BLACKBARK_FENCE_GATE     = register("blackbark_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.BLACKBARK, p));
    public static final DeferredBlock<Block> BLACKBARK_PRESSURE_PLATE = register("blackbark_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.BLACKBARK_SET, p));
    public static final DeferredBlock<Block> BLACKBARK_BUTTON         = register("blackbark_button",         p -> new GotButtonBlock(GotWoodTypes.BLACKBARK_SET, p));

    // ── Bloodwood Tree ────────────────────────────────────────────────────
    public static final DeferredBlock<Block> BLOODWOOD_LOG            = register("bloodwood_log",            BloodwoodLogBlock::new);
    public static final DeferredBlock<Block> BLOODWOOD_WOOD           = register("bloodwood_wood",           BloodwoodWoodBlock::new);
    public static final DeferredBlock<Block> BLOODWOOD_PLANKS         = register("bloodwood_planks",         BloodwoodPlanksBlock::new);
    public static final DeferredBlock<Block> BLOODWOOD_LEAVES         = register("bloodwood_leaves",         BloodwoodLeavesBlock::new);
    public static final DeferredBlock<Block> BLOODWOOD_STAIRS         = register("bloodwood_stairs",         BloodwoodStairsBlock::new);
    public static final DeferredBlock<Block> BLOODWOOD_SLAB           = register("bloodwood_slab",           BloodwoodSlabBlock::new);
    public static final DeferredBlock<Block> BLOODWOOD_FENCE          = register("bloodwood_fence",          BloodwoodFenceBlock::new);
    public static final DeferredBlock<Block> BLOODWOOD_FENCE_GATE     = register("bloodwood_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.BLOODWOOD, p));
    public static final DeferredBlock<Block> BLOODWOOD_PRESSURE_PLATE = register("bloodwood_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.BLOODWOOD_SET, p));
    public static final DeferredBlock<Block> BLOODWOOD_BUTTON         = register("bloodwood_button",         p -> new GotButtonBlock(GotWoodTypes.BLOODWOOD_SET, p));

    // ── Blue Mahoe Tree ───────────────────────────────────────────────────
    public static final DeferredBlock<Block> BLUE_MAHOE_LOG            = register("blue_mahoe_log",            BlueMahoeLogBlock::new);
    public static final DeferredBlock<Block> BLUE_MAHOE_WOOD           = register("blue_mahoe_wood",           BlueMahoeWoodBlock::new);
    public static final DeferredBlock<Block> BLUE_MAHOE_PLANKS         = register("blue_mahoe_planks",         BlueMahoePlanksBlock::new);
    public static final DeferredBlock<Block> BLUE_MAHOE_LEAVES         = register("blue_mahoe_leaves",         BlueMahoeLeavesBlock::new);
    public static final DeferredBlock<Block> BLUE_MAHOE_STAIRS         = register("blue_mahoe_stairs",         BlueMahoeStairsBlock::new);
    public static final DeferredBlock<Block> BLUE_MAHOE_SLAB           = register("blue_mahoe_slab",           BlueMahoeSlabBlock::new);
    public static final DeferredBlock<Block> BLUE_MAHOE_FENCE          = register("blue_mahoe_fence",          BlueMahoeFenceBlock::new);
    public static final DeferredBlock<Block> BLUE_MAHOE_FENCE_GATE     = register("blue_mahoe_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.BLUE_MAHOE, p));
    public static final DeferredBlock<Block> BLUE_MAHOE_PRESSURE_PLATE = register("blue_mahoe_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.BLUE_MAHOE_SET, p));
    public static final DeferredBlock<Block> BLUE_MAHOE_BUTTON         = register("blue_mahoe_button",         p -> new GotButtonBlock(GotWoodTypes.BLUE_MAHOE_SET, p));

    // ── Burl Tree ─────────────────────────────────────────────────────────
    public static final DeferredBlock<Block> BURL_LOG            = register("burl_log",            BurlLogBlock::new);
    public static final DeferredBlock<Block> BURL_WOOD           = register("burl_wood",           BurlWoodBlock::new);
    public static final DeferredBlock<Block> BURL_PLANKS         = register("burl_planks",         BurlPlanksBlock::new);
    public static final DeferredBlock<Block> BURL_LEAVES         = register("burl_leaves",         BurlLeavesBlock::new);
    public static final DeferredBlock<Block> BURL_STAIRS         = register("burl_stairs",         BurlStairsBlock::new);
    public static final DeferredBlock<Block> BURL_SLAB           = register("burl_slab",           BurlSlabBlock::new);
    public static final DeferredBlock<Block> BURL_FENCE          = register("burl_fence",          BurlFenceBlock::new);
    public static final DeferredBlock<Block> BURL_FENCE_GATE     = register("burl_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.BURL, p));
    public static final DeferredBlock<Block> BURL_PRESSURE_PLATE = register("burl_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.BURL_SET, p));
    public static final DeferredBlock<Block> BURL_BUTTON         = register("burl_button",         p -> new GotButtonBlock(GotWoodTypes.BURL_SET, p));

    // ── Cottonwood Tree ───────────────────────────────────────────────────
    public static final DeferredBlock<Block> COTTONWOOD_LOG            = register("cottonwood_log",            CottonwoodLogBlock::new);
    public static final DeferredBlock<Block> COTTONWOOD_WOOD           = register("cottonwood_wood",           CottonwoodWoodBlock::new);
    public static final DeferredBlock<Block> COTTONWOOD_PLANKS         = register("cottonwood_planks",         CottonwoodPlanksBlock::new);
    public static final DeferredBlock<Block> COTTONWOOD_LEAVES         = register("cottonwood_leaves",         CottonwoodLeavesBlock::new);
    public static final DeferredBlock<Block> COTTONWOOD_STAIRS         = register("cottonwood_stairs",         CottonwoodStairsBlock::new);
    public static final DeferredBlock<Block> COTTONWOOD_SLAB           = register("cottonwood_slab",           CottonwoodSlabBlock::new);
    public static final DeferredBlock<Block> COTTONWOOD_FENCE          = register("cottonwood_fence",          CottonwoodFenceBlock::new);
    public static final DeferredBlock<Block> COTTONWOOD_FENCE_GATE     = register("cottonwood_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.COTTONWOOD, p));
    public static final DeferredBlock<Block> COTTONWOOD_PRESSURE_PLATE = register("cottonwood_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.COTTONWOOD_SET, p));
    public static final DeferredBlock<Block> COTTONWOOD_BUTTON         = register("cottonwood_button",         p -> new GotButtonBlock(GotWoodTypes.COTTONWOOD_SET, p));


    // ── BlackCottonwood Tree ──────────────────────────────────────────
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_LOG            = register("black_cottonwood_log",            BlackCottonwoodLogBlock::new);
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_WOOD           = register("black_cottonwood_wood",           BlackCottonwoodWoodBlock::new);
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_PLANKS         = register("black_cottonwood_planks",         BlackCottonwoodPlanksBlock::new);
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_LEAVES         = register("black_cottonwood_leaves",         BlackCottonwoodLeavesBlock::new);
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_STAIRS         = register("black_cottonwood_stairs",         BlackCottonwoodStairsBlock::new);
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_SLAB           = register("black_cottonwood_slab",           BlackCottonwoodSlabBlock::new);
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_FENCE          = register("black_cottonwood_fence",          BlackCottonwoodFenceBlock::new);
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_FENCE_GATE     = register("black_cottonwood_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.COTTONWOOD, p));
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_PRESSURE_PLATE = register("black_cottonwood_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.COTTONWOOD_SET, p));
    public static final DeferredBlock<Block> BLACK_COTTONWOOD_BUTTON         = register("black_cottonwood_button",         p -> new GotButtonBlock(GotWoodTypes.COTTONWOOD_SET, p));

    // ── Cinnamon Tree ──────────────────────────────────────────
    public static final DeferredBlock<Block> CINNAMON_LOG            = register("cinnamon_log",            CinnamonLogBlock::new);
    public static final DeferredBlock<Block> CINNAMON_WOOD           = register("cinnamon_wood",           CinnamonWoodBlock::new);
    public static final DeferredBlock<Block> CINNAMON_PLANKS         = register("cinnamon_planks",         CinnamonPlanksBlock::new);
    public static final DeferredBlock<Block> CINNAMON_LEAVES         = register("cinnamon_leaves",         CinnamonLeavesBlock::new);
    public static final DeferredBlock<Block> CINNAMON_STAIRS         = register("cinnamon_stairs",         CinnamonStairsBlock::new);
    public static final DeferredBlock<Block> CINNAMON_SLAB           = register("cinnamon_slab",           CinnamonSlabBlock::new);
    public static final DeferredBlock<Block> CINNAMON_FENCE          = register("cinnamon_fence",          CinnamonFenceBlock::new);
    public static final DeferredBlock<Block> CINNAMON_FENCE_GATE     = register("cinnamon_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.CINNAMON, p));
    public static final DeferredBlock<Block> CINNAMON_PRESSURE_PLATE = register("cinnamon_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.CINNAMON_SET, p));
    public static final DeferredBlock<Block> CINNAMON_BUTTON         = register("cinnamon_button",         p -> new GotButtonBlock(GotWoodTypes.CINNAMON_SET, p));

    // ── Clove Tree ──────────────────────────────────────────
    public static final DeferredBlock<Block> CLOVE_LOG            = register("clove_log",            CloveLogBlock::new);
    public static final DeferredBlock<Block> CLOVE_WOOD           = register("clove_wood",           CloveWoodBlock::new);
    public static final DeferredBlock<Block> CLOVE_PLANKS         = register("clove_planks",         ClovePlanksBlock::new);
    public static final DeferredBlock<Block> CLOVE_LEAVES         = register("clove_leaves",         CloveLeavesBlock::new);
    public static final DeferredBlock<Block> CLOVE_STAIRS         = register("clove_stairs",         CloveStairsBlock::new);
    public static final DeferredBlock<Block> CLOVE_SLAB           = register("clove_slab",           CloveSlabBlock::new);
    public static final DeferredBlock<Block> CLOVE_FENCE          = register("clove_fence",          CloveFenceBlock::new);
    public static final DeferredBlock<Block> CLOVE_FENCE_GATE     = register("clove_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.CLOVE, p));
    public static final DeferredBlock<Block> CLOVE_PRESSURE_PLATE = register("clove_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.CLOVE_SET, p));
    public static final DeferredBlock<Block> CLOVE_BUTTON         = register("clove_button",         p -> new GotButtonBlock(GotWoodTypes.CLOVE_SET, p));

    // ── Ebony Tree ──────────────────────────────────────────
    public static final DeferredBlock<Block> EBONY_LOG            = register("ebony_log",            EbonyLogBlock::new);
    public static final DeferredBlock<Block> EBONY_WOOD           = register("ebony_wood",           EbonyWoodBlock::new);
    public static final DeferredBlock<Block> EBONY_PLANKS         = register("ebony_planks",         EbonyPlanksBlock::new);
    public static final DeferredBlock<Block> EBONY_LEAVES         = register("ebony_leaves",         EbonyLeavesBlock::new);
    public static final DeferredBlock<Block> EBONY_STAIRS         = register("ebony_stairs",         EbonyStairsBlock::new);
    public static final DeferredBlock<Block> EBONY_SLAB           = register("ebony_slab",           EbonySlabBlock::new);
    public static final DeferredBlock<Block> EBONY_FENCE          = register("ebony_fence",          EbonyFenceBlock::new);
    public static final DeferredBlock<Block> EBONY_FENCE_GATE     = register("ebony_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.EBONY, p));
    public static final DeferredBlock<Block> EBONY_PRESSURE_PLATE = register("ebony_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.EBONY_SET, p));
    public static final DeferredBlock<Block> EBONY_BUTTON         = register("ebony_button",         p -> new GotButtonBlock(GotWoodTypes.EBONY_SET, p));

    // ── Elm Tree ──────────────────────────────────────────
    public static final DeferredBlock<Block> ELM_LOG            = register("elm_log",            ElmLogBlock::new);
    public static final DeferredBlock<Block> ELM_WOOD           = register("elm_wood",           ElmWoodBlock::new);
    public static final DeferredBlock<Block> ELM_PLANKS         = register("elm_planks",         ElmPlanksBlock::new);
    public static final DeferredBlock<Block> ELM_LEAVES         = register("elm_leaves",         ElmLeavesBlock::new);
    public static final DeferredBlock<Block> ELM_STAIRS         = register("elm_stairs",         ElmStairsBlock::new);
    public static final DeferredBlock<Block> ELM_SLAB           = register("elm_slab",           ElmSlabBlock::new);
    public static final DeferredBlock<Block> ELM_FENCE          = register("elm_fence",          ElmFenceBlock::new);
    public static final DeferredBlock<Block> ELM_FENCE_GATE     = register("elm_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.ELM, p));
    public static final DeferredBlock<Block> ELM_PRESSURE_PLATE = register("elm_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.ELM_SET, p));
    public static final DeferredBlock<Block> ELM_BUTTON         = register("elm_button",         p -> new GotButtonBlock(GotWoodTypes.ELM_SET, p));

    // ── Cedar Tree ──────────────────────────────────────────
    public static final DeferredBlock<Block> CEDAR_LOG            = register("cedar_log",            CedarLogBlock::new);
    public static final DeferredBlock<Block> CEDAR_WOOD           = register("cedar_wood",           CedarWoodBlock::new);
    public static final DeferredBlock<Block> CEDAR_PLANKS         = register("cedar_planks",         CedarPlanksBlock::new);
    public static final DeferredBlock<Block> CEDAR_LEAVES         = register("cedar_leaves",         CedarLeavesBlock::new);
    public static final DeferredBlock<Block> CEDAR_STAIRS         = register("cedar_stairs",         CedarStairsBlock::new);
    public static final DeferredBlock<Block> CEDAR_SLAB           = register("cedar_slab",           CedarSlabBlock::new);
    public static final DeferredBlock<Block> CEDAR_FENCE          = register("cedar_fence",          CedarFenceBlock::new);
    public static final DeferredBlock<Block> CEDAR_FENCE_GATE     = register("cedar_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.CEDAR, p));
    public static final DeferredBlock<Block> CEDAR_PRESSURE_PLATE = register("cedar_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.CEDAR_SET, p));
    public static final DeferredBlock<Block> CEDAR_BUTTON         = register("cedar_button",         p -> new GotButtonBlock(GotWoodTypes.CEDAR_SET, p));

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
    public static final DeferredBlock<Block> APPLE_LOG            = register("apple_log",            AppleLogBlock::new);
    public static final DeferredBlock<Block> APPLE_WOOD           = register("apple_wood",           AppleWoodBlock::new);
    public static final DeferredBlock<Block> APPLE_PLANKS         = register("apple_planks",         ApplePlanksBlock::new);
    public static final DeferredBlock<Block> APPLE_LEAVES         = register("apple_leaves",         AppleLeavesBlock::new);
    public static final DeferredBlock<Block> APPLE_STAIRS         = register("apple_stairs",         AppleStairsBlock::new);
    public static final DeferredBlock<Block> APPLE_SLAB           = register("apple_slab",           AppleSlabBlock::new);
    public static final DeferredBlock<Block> APPLE_FENCE          = register("apple_fence",          AppleFenceBlock::new);
    public static final DeferredBlock<Block> APPLE_FENCE_GATE     = register("apple_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.APPLE, p));
    public static final DeferredBlock<Block> APPLE_PRESSURE_PLATE = register("apple_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.APPLE_SET, p));
    public static final DeferredBlock<Block> APPLE_BUTTON         = register("apple_button",         p -> new GotButtonBlock(GotWoodTypes.APPLE_SET, p));

    // ── Goldenheart Tree
    public static final DeferredBlock<Block> GOLDENHEART_LOG            = register("goldenheart_log",            GoldenheartLogBlock::new);
    public static final DeferredBlock<Block> GOLDENHEART_WOOD           = register("goldenheart_wood",           GoldenheartWoodBlock::new);
    public static final DeferredBlock<Block> GOLDENHEART_PLANKS         = register("goldenheart_planks",         GoldenheartPlanksBlock::new);
    public static final DeferredBlock<Block> GOLDENHEART_LEAVES         = register("goldenheart_leaves",         GoldenheartLeavesBlock::new);
    public static final DeferredBlock<Block> GOLDENHEART_STAIRS         = register("goldenheart_stairs",         GoldenheartStairsBlock::new);
    public static final DeferredBlock<Block> GOLDENHEART_SLAB           = register("goldenheart_slab",           GoldenheartSlabBlock::new);
    public static final DeferredBlock<Block> GOLDENHEART_FENCE          = register("goldenheart_fence",          GoldenheartFenceBlock::new);
    public static final DeferredBlock<Block> GOLDENHEART_FENCE_GATE     = register("goldenheart_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.GOLDENHEART, p));
    public static final DeferredBlock<Block> GOLDENHEART_PRESSURE_PLATE = register("goldenheart_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.GOLDENHEART_SET, p));
    public static final DeferredBlock<Block> GOLDENHEART_BUTTON         = register("goldenheart_button",         p -> new GotButtonBlock(GotWoodTypes.GOLDENHEART_SET, p));

    // ── Linden Tree
    public static final DeferredBlock<Block> LINDEN_LOG            = register("linden_log",            LindenLogBlock::new);
    public static final DeferredBlock<Block> LINDEN_WOOD           = register("linden_wood",           LindenWoodBlock::new);
    public static final DeferredBlock<Block> LINDEN_PLANKS         = register("linden_planks",         LindenPlanksBlock::new);
    public static final DeferredBlock<Block> LINDEN_LEAVES         = register("linden_leaves",         LindenLeavesBlock::new);
    public static final DeferredBlock<Block> LINDEN_STAIRS         = register("linden_stairs",         LindenStairsBlock::new);
    public static final DeferredBlock<Block> LINDEN_SLAB           = register("linden_slab",           LindenSlabBlock::new);
    public static final DeferredBlock<Block> LINDEN_FENCE          = register("linden_fence",          LindenFenceBlock::new);
    public static final DeferredBlock<Block> LINDEN_FENCE_GATE     = register("linden_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.LINDEN, p));
    public static final DeferredBlock<Block> LINDEN_PRESSURE_PLATE = register("linden_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.LINDEN_SET, p));
    public static final DeferredBlock<Block> LINDEN_BUTTON         = register("linden_button",         p -> new GotButtonBlock(GotWoodTypes.LINDEN_SET, p));

    // ── Mahogany Tree
    public static final DeferredBlock<Block> MAHOGANY_LOG            = register("mahogany_log",            MahoganyLogBlock::new);
    public static final DeferredBlock<Block> MAHOGANY_WOOD           = register("mahogany_wood",           MahoganyWoodBlock::new);
    public static final DeferredBlock<Block> MAHOGANY_PLANKS         = register("mahogany_planks",         MahoganyPlanksBlock::new);
    public static final DeferredBlock<Block> MAHOGANY_LEAVES         = register("mahogany_leaves",         MahoganyLeavesBlock::new);
    public static final DeferredBlock<Block> MAHOGANY_STAIRS         = register("mahogany_stairs",         MahoganyStairsBlock::new);
    public static final DeferredBlock<Block> MAHOGANY_SLAB           = register("mahogany_slab",           MahoganySlabBlock::new);
    public static final DeferredBlock<Block> MAHOGANY_FENCE          = register("mahogany_fence",          MahoganyFenceBlock::new);
    public static final DeferredBlock<Block> MAHOGANY_FENCE_GATE     = register("mahogany_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.MAHOGANY, p));
    public static final DeferredBlock<Block> MAHOGANY_PRESSURE_PLATE = register("mahogany_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.MAHOGANY_SET, p));
    public static final DeferredBlock<Block> MAHOGANY_BUTTON         = register("mahogany_button",         p -> new GotButtonBlock(GotWoodTypes.MAHOGANY_SET, p));

    // ── Maple Tree
    public static final DeferredBlock<Block> MAPLE_LOG            = register("maple_log",            MapleLogBlock::new);
    public static final DeferredBlock<Block> MAPLE_WOOD           = register("maple_wood",           MapleWoodBlock::new);
    public static final DeferredBlock<Block> MAPLE_PLANKS         = register("maple_planks",         MaplePlanksBlock::new);
    public static final DeferredBlock<Block> MAPLE_LEAVES         = register("maple_leaves",         MapleLeavesBlock::new);
    public static final DeferredBlock<Block> MAPLE_STAIRS         = register("maple_stairs",         MapleStairsBlock::new);
    public static final DeferredBlock<Block> MAPLE_SLAB           = register("maple_slab",           MapleSlabBlock::new);
    public static final DeferredBlock<Block> MAPLE_FENCE          = register("maple_fence",          MapleFenceBlock::new);
    public static final DeferredBlock<Block> MAPLE_FENCE_GATE     = register("maple_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.MAPLE, p));
    public static final DeferredBlock<Block> MAPLE_PRESSURE_PLATE = register("maple_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.MAPLE_SET, p));
    public static final DeferredBlock<Block> MAPLE_BUTTON         = register("maple_button",         p -> new GotButtonBlock(GotWoodTypes.MAPLE_SET, p));

    // ── Myrrh Tree
    public static final DeferredBlock<Block> MYRRH_LOG            = register("myrrh_log",            MyrrhLogBlock::new);
    public static final DeferredBlock<Block> MYRRH_WOOD           = register("myrrh_wood",           MyrrhWoodBlock::new);
    public static final DeferredBlock<Block> MYRRH_PLANKS         = register("myrrh_planks",         MyrrhPlanksBlock::new);
    public static final DeferredBlock<Block> MYRRH_LEAVES         = register("myrrh_leaves",         MyrrhLeavesBlock::new);
    public static final DeferredBlock<Block> MYRRH_STAIRS         = register("myrrh_stairs",         MyrrhStairsBlock::new);
    public static final DeferredBlock<Block> MYRRH_SLAB           = register("myrrh_slab",           MyrrhSlabBlock::new);
    public static final DeferredBlock<Block> MYRRH_FENCE          = register("myrrh_fence",          MyrrhFenceBlock::new);
    public static final DeferredBlock<Block> MYRRH_FENCE_GATE     = register("myrrh_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.MYRRH, p));
    public static final DeferredBlock<Block> MYRRH_PRESSURE_PLATE = register("myrrh_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.MYRRH_SET, p));
    public static final DeferredBlock<Block> MYRRH_BUTTON         = register("myrrh_button",         p -> new GotButtonBlock(GotWoodTypes.MYRRH_SET, p));
    // ── Redwood Tree
    public static final DeferredBlock<Block> REDWOOD_LOG            = register("redwood_log",            RedwoodLogBlock::new);
    public static final DeferredBlock<Block> REDWOOD_WOOD           = register("redwood_wood",           RedwoodWoodBlock::new);
    public static final DeferredBlock<Block> REDWOOD_PLANKS         = register("redwood_planks",         RedwoodPlanksBlock::new);
    public static final DeferredBlock<Block> REDWOOD_LEAVES         = register("redwood_leaves",         RedwoodLeavesBlock::new);
    public static final DeferredBlock<Block> REDWOOD_STAIRS         = register("redwood_stairs",         RedwoodStairsBlock::new);
    public static final DeferredBlock<Block> REDWOOD_SLAB           = register("redwood_slab",           RedwoodSlabBlock::new);
    public static final DeferredBlock<Block> REDWOOD_FENCE          = register("redwood_fence",          RedwoodFenceBlock::new);
    public static final DeferredBlock<Block> REDWOOD_FENCE_GATE     = register("redwood_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.REDWOOD, p));
    public static final DeferredBlock<Block> REDWOOD_PRESSURE_PLATE = register("redwood_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.REDWOOD_SET, p));
    public static final DeferredBlock<Block> REDWOOD_BUTTON         = register("redwood_button",         p -> new GotButtonBlock(GotWoodTypes.REDWOOD_SET, p));

    // ── Chestnut Tree
    public static final DeferredBlock<Block> CHESTNUT_LOG            = register("chestnut_log",            ChestnutLogBlock::new);
    public static final DeferredBlock<Block> CHESTNUT_WOOD           = register("chestnut_wood",           ChestnutWoodBlock::new);
    public static final DeferredBlock<Block> CHESTNUT_PLANKS         = register("chestnut_planks",         ChestnutPlanksBlock::new);
    public static final DeferredBlock<Block> CHESTNUT_LEAVES         = register("chestnut_leaves",         ChestnutLeavesBlock::new);
    public static final DeferredBlock<Block> CHESTNUT_STAIRS         = register("chestnut_stairs",         ChestnutStairsBlock::new);
    public static final DeferredBlock<Block> CHESTNUT_SLAB           = register("chestnut_slab",           ChestnutSlabBlock::new);
    public static final DeferredBlock<Block> CHESTNUT_FENCE          = register("chestnut_fence",          ChestnutFenceBlock::new);
    public static final DeferredBlock<Block> CHESTNUT_FENCE_GATE     = register("chestnut_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.CHESTNUT, p));
    public static final DeferredBlock<Block> CHESTNUT_PRESSURE_PLATE = register("chestnut_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.CHESTNUT_SET, p));
    public static final DeferredBlock<Block> CHESTNUT_BUTTON         = register("chestnut_button",         p -> new GotButtonBlock(GotWoodTypes.CHESTNUT_SET, p));

    // ── Willow Tree
    public static final DeferredBlock<Block> WILLOW_LOG            = register("willow_log",            WillowLogBlock::new);
    public static final DeferredBlock<Block> WILLOW_WOOD           = register("willow_wood",           WillowWoodBlock::new);
    public static final DeferredBlock<Block> WILLOW_PLANKS         = register("willow_planks",         WillowPlanksBlock::new);
    public static final DeferredBlock<Block> WILLOW_LEAVES         = register("willow_leaves",         WillowLeavesBlock::new);
    public static final DeferredBlock<Block> WILLOW_STAIRS         = register("willow_stairs",         WillowStairsBlock::new);
    public static final DeferredBlock<Block> WILLOW_SLAB           = register("willow_slab",           WillowSlabBlock::new);
    public static final DeferredBlock<Block> WILLOW_FENCE          = register("willow_fence",          WillowFenceBlock::new);
    public static final DeferredBlock<Block> WILLOW_FENCE_GATE     = register("willow_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.WILLOW, p));
    public static final DeferredBlock<Block> WILLOW_PRESSURE_PLATE = register("willow_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.WILLOW_SET, p));
    public static final DeferredBlock<Block> WILLOW_BUTTON         = register("willow_button",         p -> new GotButtonBlock(GotWoodTypes.WILLOW_SET, p));

    // ── Wormtree Tree
    public static final DeferredBlock<Block> WORMTREE_LOG            = register("wormtree_log",            WormtreeLogBlock::new);
    public static final DeferredBlock<Block> WORMTREE_WOOD           = register("wormtree_wood",           WormtreeWoodBlock::new);
    public static final DeferredBlock<Block> WORMTREE_PLANKS         = register("wormtree_planks",         WormtreePlanksBlock::new);
    public static final DeferredBlock<Block> WORMTREE_LEAVES         = register("wormtree_leaves",         WormtreeLeavesBlock::new);
    public static final DeferredBlock<Block> WORMTREE_STAIRS         = register("wormtree_stairs",         WormtreeStairsBlock::new);
    public static final DeferredBlock<Block> WORMTREE_SLAB           = register("wormtree_slab",           WormtreeSlabBlock::new);
    public static final DeferredBlock<Block> WORMTREE_FENCE          = register("wormtree_fence",          WormtreeFenceBlock::new);
    public static final DeferredBlock<Block> WORMTREE_FENCE_GATE     = register("wormtree_fence_gate",     p -> new GotFenceGateBlock(GotWoodTypes.WORMTREE, p));
    public static final DeferredBlock<Block> WORMTREE_PRESSURE_PLATE = register("wormtree_pressure_plate", p -> new GotPressurePlateBlock(GotWoodTypes.WORMTREE_SET, p));
    public static final DeferredBlock<Block> WORMTREE_BUTTON         = register("wormtree_button",         p -> new GotButtonBlock(GotWoodTypes.WORMTREE_SET, p));

    // ── Helpers ──────────────────────────────────────────────────────────

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