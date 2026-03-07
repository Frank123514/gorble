package net.got.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.BoatItem;

import net.got.GotMod;
import net.got.init.GotModBoatTypes;

import java.util.function.Function;

public class GotModItems {
    public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(GotMod.MODID);
    public static final DeferredItem<Item> WEIRWOOD_LOG = block(GotModBlocks.WEIRWOOD_LOG);
    public static final DeferredItem<Item> WEIRWOOD_WOOD = block(GotModBlocks.WEIRWOOD_WOOD);
    public static final DeferredItem<Item> WEIRWOOD_PLANKS = block(GotModBlocks.WEIRWOOD_PLANKS);
    public static final DeferredItem<Item> WEIRWOOD_LEAVES = block(GotModBlocks.WEIRWOOD_LEAVES);
    public static final DeferredItem<Item> WEIRWOOD_STAIRS = block(GotModBlocks.WEIRWOOD_STAIRS);
    public static final DeferredItem<Item> WEIRWOOD_SLAB = block(GotModBlocks.WEIRWOOD_SLAB);
    public static final DeferredItem<Item> WEIRWOOD_FENCE = block(GotModBlocks.WEIRWOOD_FENCE);
    public static final DeferredItem<Item> WEIRWOOD_FENCE_GATE = block(GotModBlocks.WEIRWOOD_FENCE_GATE);
    public static final DeferredItem<Item> WEIRWOOD_PRESSURE_PLATE = block(GotModBlocks.WEIRWOOD_PRESSURE_PLATE);
    public static final DeferredItem<Item> WEIRWOOD_BUTTON = block(GotModBlocks.WEIRWOOD_BUTTON);
    public static final DeferredItem<Item> ASPEN_LOG = block(GotModBlocks.ASPEN_LOG);
    public static final DeferredItem<Item> ASPEN_WOOD = block(GotModBlocks.ASPEN_WOOD);
    public static final DeferredItem<Item> ASPEN_PLANKS = block(GotModBlocks.ASPEN_PLANKS);
    public static final DeferredItem<Item> ASPEN_LEAVES = block(GotModBlocks.ASPEN_LEAVES);
    public static final DeferredItem<Item> ASPEN_STAIRS = block(GotModBlocks.ASPEN_STAIRS);
    public static final DeferredItem<Item> ASPEN_SLAB = block(GotModBlocks.ASPEN_SLAB);
    public static final DeferredItem<Item> ASPEN_FENCE = block(GotModBlocks.ASPEN_FENCE);
    public static final DeferredItem<Item> ASPEN_FENCE_GATE = block(GotModBlocks.ASPEN_FENCE_GATE);
    public static final DeferredItem<Item> ASPEN_PRESSURE_PLATE = block(GotModBlocks.ASPEN_PRESSURE_PLATE);
    public static final DeferredItem<Item> ASPEN_BUTTON = block(GotModBlocks.ASPEN_BUTTON);
    public static final DeferredItem<Item> ALDER_LOG = block(GotModBlocks.ALDER_LOG);
    public static final DeferredItem<Item> ALDER_WOOD = block(GotModBlocks.ALDER_WOOD);
    public static final DeferredItem<Item> ALDER_PLANKS = block(GotModBlocks.ALDER_PLANKS);
    public static final DeferredItem<Item> ALDER_LEAVES = block(GotModBlocks.ALDER_LEAVES);
    public static final DeferredItem<Item> ALDER_STAIRS = block(GotModBlocks.ALDER_STAIRS);
    public static final DeferredItem<Item> ALDER_SLAB = block(GotModBlocks.ALDER_SLAB);
    public static final DeferredItem<Item> ALDER_FENCE = block(GotModBlocks.ALDER_FENCE);
    public static final DeferredItem<Item> ALDER_FENCE_GATE = block(GotModBlocks.ALDER_FENCE_GATE);
    public static final DeferredItem<Item> ALDER_PRESSURE_PLATE = block(GotModBlocks.ALDER_PRESSURE_PLATE);
    public static final DeferredItem<Item> ALDER_BUTTON = block(GotModBlocks.ALDER_BUTTON);
    public static final DeferredItem<Item> PINE_LOG = block(GotModBlocks.PINE_LOG);
    public static final DeferredItem<Item> PINE_WOOD = block(GotModBlocks.PINE_WOOD);
    public static final DeferredItem<Item> PINE_PLANKS = block(GotModBlocks.PINE_PLANKS);
    public static final DeferredItem<Item> PINE_LEAVES = block(GotModBlocks.PINE_LEAVES);
    public static final DeferredItem<Item> PINE_STAIRS = block(GotModBlocks.PINE_STAIRS);
    public static final DeferredItem<Item> PINE_SLAB = block(GotModBlocks.PINE_SLAB);
    public static final DeferredItem<Item> PINE_FENCE = block(GotModBlocks.PINE_FENCE);
    public static final DeferredItem<Item> PINE_FENCE_GATE = block(GotModBlocks.PINE_FENCE_GATE);
    public static final DeferredItem<Item> PINE_PRESSURE_PLATE = block(GotModBlocks.PINE_PRESSURE_PLATE);
    public static final DeferredItem<Item> PINE_BUTTON = block(GotModBlocks.PINE_BUTTON);
    public static final DeferredItem<Item> FIR_LOG = block(GotModBlocks.FIR_LOG);
    public static final DeferredItem<Item> FIR_WOOD = block(GotModBlocks.FIR_WOOD);
    public static final DeferredItem<Item> FIR_PLANKS = block(GotModBlocks.FIR_PLANKS);
    public static final DeferredItem<Item> FIR_LEAVES = block(GotModBlocks.FIR_LEAVES);
    public static final DeferredItem<Item> FIR_STAIRS = block(GotModBlocks.FIR_STAIRS);
    public static final DeferredItem<Item> FIR_SLAB = block(GotModBlocks.FIR_SLAB);
    public static final DeferredItem<Item> FIR_FENCE = block(GotModBlocks.FIR_FENCE);
    public static final DeferredItem<Item> FIR_FENCE_GATE = block(GotModBlocks.FIR_FENCE_GATE);
    public static final DeferredItem<Item> FIR_PRESSURE_PLATE = block(GotModBlocks.FIR_PRESSURE_PLATE);
    public static final DeferredItem<Item> FIR_BUTTON = block(GotModBlocks.FIR_BUTTON);
    public static final DeferredItem<Item> SENTINAL_LOG = block(GotModBlocks.SENTINAL_LOG);
    public static final DeferredItem<Item> SENTINAL_WOOD = block(GotModBlocks.SENTINAL_WOOD);
    public static final DeferredItem<Item> SENTINAL_PLANKS = block(GotModBlocks.SENTINAL_PLANKS);
    public static final DeferredItem<Item> SENTINAL_LEAVES = block(GotModBlocks.SENTINAL_LEAVES);
    public static final DeferredItem<Item> SENTINAL_STAIRS = block(GotModBlocks.SENTINAL_STAIRS);
    public static final DeferredItem<Item> SENTINAL_SLAB = block(GotModBlocks.SENTINAL_SLAB);
    public static final DeferredItem<Item> SENTINAL_FENCE = block(GotModBlocks.SENTINAL_FENCE);
    public static final DeferredItem<Item> SENTINAL_FENCE_GATE = block(GotModBlocks.SENTINAL_FENCE_GATE);
    public static final DeferredItem<Item> SENTINAL_PRESSURE_PLATE = block(GotModBlocks.SENTINAL_PRESSURE_PLATE);
    public static final DeferredItem<Item> SENTINAL_BUTTON = block(GotModBlocks.SENTINAL_BUTTON);
    public static final DeferredItem<Item> IRONWOOD_LOG = block(GotModBlocks.IRONWOOD_LOG);
    public static final DeferredItem<Item> IRONWOOD_WOOD = block(GotModBlocks.IRONWOOD_WOOD);
    public static final DeferredItem<Item> IRONWOOD_PLANKS = block(GotModBlocks.IRONWOOD_PLANKS);
    public static final DeferredItem<Item> IRONWOOD_LEAVES = block(GotModBlocks.IRONWOOD_LEAVES);
    public static final DeferredItem<Item> IRONWOOD_STAIRS = block(GotModBlocks.IRONWOOD_STAIRS);
    public static final DeferredItem<Item> IRONWOOD_SLAB = block(GotModBlocks.IRONWOOD_SLAB);
    public static final DeferredItem<Item> IRONWOOD_FENCE = block(GotModBlocks.IRONWOOD_FENCE);
    public static final DeferredItem<Item> IRONWOOD_FENCE_GATE = block(GotModBlocks.IRONWOOD_FENCE_GATE);
    public static final DeferredItem<Item> IRONWOOD_PRESSURE_PLATE = block(GotModBlocks.IRONWOOD_PRESSURE_PLATE);
    public static final DeferredItem<Item> IRONWOOD_BUTTON = block(GotModBlocks.IRONWOOD_BUTTON);
    public static final DeferredItem<Item> BEECH_LOG = block(GotModBlocks.BEECH_LOG);
    public static final DeferredItem<Item> BEECH_WOOD = block(GotModBlocks.BEECH_WOOD);
    public static final DeferredItem<Item> BEECH_PLANKS = block(GotModBlocks.BEECH_PLANKS);
    public static final DeferredItem<Item> BEECH_LEAVES = block(GotModBlocks.BEECH_LEAVES);
    public static final DeferredItem<Item> BEECH_STAIRS = block(GotModBlocks.BEECH_STAIRS);
    public static final DeferredItem<Item> BEECH_SLAB = block(GotModBlocks.BEECH_SLAB);
    public static final DeferredItem<Item> BEECH_FENCE = block(GotModBlocks.BEECH_FENCE);
    public static final DeferredItem<Item> BEECH_FENCE_GATE = block(GotModBlocks.BEECH_FENCE_GATE);
    public static final DeferredItem<Item> BEECH_PRESSURE_PLATE = block(GotModBlocks.BEECH_PRESSURE_PLATE);
    public static final DeferredItem<Item> BEECH_BUTTON = block(GotModBlocks.BEECH_BUTTON);
    public static final DeferredItem<Item> SOLDIER_PINE_LOG = block(GotModBlocks.SOLDIER_PINE_LOG);
    public static final DeferredItem<Item> SOLDIER_PINE_WOOD = block(GotModBlocks.SOLDIER_PINE_WOOD);
    public static final DeferredItem<Item> SOLDIER_PINE_PLANKS = block(GotModBlocks.SOLDIER_PINE_PLANKS);
    public static final DeferredItem<Item> SOLDIER_PINE_LEAVES = block(GotModBlocks.SOLDIER_PINE_LEAVES);
    public static final DeferredItem<Item> SOLDIER_PINE_STAIRS = block(GotModBlocks.SOLDIER_PINE_STAIRS);
    public static final DeferredItem<Item> SOLDIER_PINE_SLAB = block(GotModBlocks.SOLDIER_PINE_SLAB);
    public static final DeferredItem<Item> SOLDIER_PINE_FENCE = block(GotModBlocks.SOLDIER_PINE_FENCE);
    public static final DeferredItem<Item> SOLDIER_PINE_FENCE_GATE = block(GotModBlocks.SOLDIER_PINE_FENCE_GATE);
    public static final DeferredItem<Item> SOLDIER_PINE_PRESSURE_PLATE = block(GotModBlocks.SOLDIER_PINE_PRESSURE_PLATE);
    public static final DeferredItem<Item> SOLDIER_PINE_BUTTON = block(GotModBlocks.SOLDIER_PINE_BUTTON);
    public static final DeferredItem<Item> ASH_LOG = block(GotModBlocks.ASH_LOG);
    public static final DeferredItem<Item> ASH_WOOD = block(GotModBlocks.ASH_WOOD);
    public static final DeferredItem<Item> ASH_PLANKS = block(GotModBlocks.ASH_PLANKS);
    public static final DeferredItem<Item> ASH_LEAVES = block(GotModBlocks.ASH_LEAVES);
    public static final DeferredItem<Item> ASH_STAIRS = block(GotModBlocks.ASH_STAIRS);
    public static final DeferredItem<Item> ASH_SLAB = block(GotModBlocks.ASH_SLAB);
    public static final DeferredItem<Item> ASH_FENCE = block(GotModBlocks.ASH_FENCE);
    public static final DeferredItem<Item> ASH_FENCE_GATE = block(GotModBlocks.ASH_FENCE_GATE);
    public static final DeferredItem<Item> ASH_PRESSURE_PLATE = block(GotModBlocks.ASH_PRESSURE_PLATE);
    public static final DeferredItem<Item> ASH_BUTTON = block(GotModBlocks.ASH_BUTTON);
    public static final DeferredItem<Item> HAWTHORN_LOG = block(GotModBlocks.HAWTHORN_LOG);
    public static final DeferredItem<Item> HAWTHORN_WOOD = block(GotModBlocks.HAWTHORN_WOOD);
    public static final DeferredItem<Item> HAWTHORN_PLANKS = block(GotModBlocks.HAWTHORN_PLANKS);
    public static final DeferredItem<Item> HAWTHORN_LEAVES = block(GotModBlocks.HAWTHORN_LEAVES);
    public static final DeferredItem<Item> HAWTHORN_STAIRS = block(GotModBlocks.HAWTHORN_STAIRS);
    public static final DeferredItem<Item> HAWTHORN_SLAB = block(GotModBlocks.HAWTHORN_SLAB);
    public static final DeferredItem<Item> HAWTHORN_FENCE = block(GotModBlocks.HAWTHORN_FENCE);
    public static final DeferredItem<Item> HAWTHORN_FENCE_GATE = block(GotModBlocks.HAWTHORN_FENCE_GATE);
    public static final DeferredItem<Item> HAWTHORN_PRESSURE_PLATE = block(GotModBlocks.HAWTHORN_PRESSURE_PLATE);
    public static final DeferredItem<Item> HAWTHORN_BUTTON = block(GotModBlocks.HAWTHORN_BUTTON);

    // ── Blackbark Tree ──────────────────────────────────────────
    public static final DeferredItem<Item> BLACKBARK_LOG            = block(GotModBlocks.BLACKBARK_LOG);
    public static final DeferredItem<Item> BLACKBARK_WOOD           = block(GotModBlocks.BLACKBARK_WOOD);
    public static final DeferredItem<Item> BLACKBARK_PLANKS         = block(GotModBlocks.BLACKBARK_PLANKS);
    public static final DeferredItem<Item> BLACKBARK_LEAVES         = block(GotModBlocks.BLACKBARK_LEAVES);
    public static final DeferredItem<Item> BLACKBARK_STAIRS         = block(GotModBlocks.BLACKBARK_STAIRS);
    public static final DeferredItem<Item> BLACKBARK_SLAB           = block(GotModBlocks.BLACKBARK_SLAB);
    public static final DeferredItem<Item> BLACKBARK_FENCE          = block(GotModBlocks.BLACKBARK_FENCE);
    public static final DeferredItem<Item> BLACKBARK_FENCE_GATE     = block(GotModBlocks.BLACKBARK_FENCE_GATE);
    public static final DeferredItem<Item> BLACKBARK_PRESSURE_PLATE = block(GotModBlocks.BLACKBARK_PRESSURE_PLATE);
    public static final DeferredItem<Item> BLACKBARK_BUTTON         = block(GotModBlocks.BLACKBARK_BUTTON);

    // ── Bloodwood Tree ───────────────────────────────────────────
    public static final DeferredItem<Item> BLOODWOOD_LOG            = block(GotModBlocks.BLOODWOOD_LOG);
    public static final DeferredItem<Item> BLOODWOOD_WOOD           = block(GotModBlocks.BLOODWOOD_WOOD);
    public static final DeferredItem<Item> BLOODWOOD_PLANKS         = block(GotModBlocks.BLOODWOOD_PLANKS);
    public static final DeferredItem<Item> BLOODWOOD_LEAVES         = block(GotModBlocks.BLOODWOOD_LEAVES);
    public static final DeferredItem<Item> BLOODWOOD_STAIRS         = block(GotModBlocks.BLOODWOOD_STAIRS);
    public static final DeferredItem<Item> BLOODWOOD_SLAB           = block(GotModBlocks.BLOODWOOD_SLAB);
    public static final DeferredItem<Item> BLOODWOOD_FENCE          = block(GotModBlocks.BLOODWOOD_FENCE);
    public static final DeferredItem<Item> BLOODWOOD_FENCE_GATE     = block(GotModBlocks.BLOODWOOD_FENCE_GATE);
    public static final DeferredItem<Item> BLOODWOOD_PRESSURE_PLATE = block(GotModBlocks.BLOODWOOD_PRESSURE_PLATE);
    public static final DeferredItem<Item> BLOODWOOD_BUTTON         = block(GotModBlocks.BLOODWOOD_BUTTON);

    // ── Blue Mahoe Tree ──────────────────────────────────────────
    public static final DeferredItem<Item> BLUE_MAHOE_LOG            = block(GotModBlocks.BLUE_MAHOE_LOG);
    public static final DeferredItem<Item> BLUE_MAHOE_WOOD           = block(GotModBlocks.BLUE_MAHOE_WOOD);
    public static final DeferredItem<Item> BLUE_MAHOE_PLANKS         = block(GotModBlocks.BLUE_MAHOE_PLANKS);
    public static final DeferredItem<Item> BLUE_MAHOE_LEAVES         = block(GotModBlocks.BLUE_MAHOE_LEAVES);
    public static final DeferredItem<Item> BLUE_MAHOE_STAIRS         = block(GotModBlocks.BLUE_MAHOE_STAIRS);
    public static final DeferredItem<Item> BLUE_MAHOE_SLAB           = block(GotModBlocks.BLUE_MAHOE_SLAB);
    public static final DeferredItem<Item> BLUE_MAHOE_FENCE          = block(GotModBlocks.BLUE_MAHOE_FENCE);
    public static final DeferredItem<Item> BLUE_MAHOE_FENCE_GATE     = block(GotModBlocks.BLUE_MAHOE_FENCE_GATE);
    public static final DeferredItem<Item> BLUE_MAHOE_PRESSURE_PLATE = block(GotModBlocks.BLUE_MAHOE_PRESSURE_PLATE);
    public static final DeferredItem<Item> BLUE_MAHOE_BUTTON         = block(GotModBlocks.BLUE_MAHOE_BUTTON);

    // ── Cottonwood Tree ───────────────────────────────────────────
    public static final DeferredItem<Item> COTTONWOOD_LOG            = block(GotModBlocks.COTTONWOOD_LOG);
    public static final DeferredItem<Item> COTTONWOOD_WOOD           = block(GotModBlocks.COTTONWOOD_WOOD);
    public static final DeferredItem<Item> COTTONWOOD_PLANKS         = block(GotModBlocks.COTTONWOOD_PLANKS);
    public static final DeferredItem<Item> COTTONWOOD_LEAVES         = block(GotModBlocks.COTTONWOOD_LEAVES);
    public static final DeferredItem<Item> COTTONWOOD_STAIRS         = block(GotModBlocks.COTTONWOOD_STAIRS);
    public static final DeferredItem<Item> COTTONWOOD_SLAB           = block(GotModBlocks.COTTONWOOD_SLAB);
    public static final DeferredItem<Item> COTTONWOOD_FENCE          = block(GotModBlocks.COTTONWOOD_FENCE);
    public static final DeferredItem<Item> COTTONWOOD_FENCE_GATE     = block(GotModBlocks.COTTONWOOD_FENCE_GATE);
    public static final DeferredItem<Item> COTTONWOOD_PRESSURE_PLATE = block(GotModBlocks.COTTONWOOD_PRESSURE_PLATE);
    public static final DeferredItem<Item> COTTONWOOD_BUTTON         = block(GotModBlocks.COTTONWOOD_BUTTON);


    // ── BlackCottonwood Tree ──────────────────────────────────────────
    public static final DeferredItem<Item> BLACK_COTTONWOOD_LOG            = block(GotModBlocks.BLACK_COTTONWOOD_LOG);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_WOOD           = block(GotModBlocks.BLACK_COTTONWOOD_WOOD);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_PLANKS         = block(GotModBlocks.BLACK_COTTONWOOD_PLANKS);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_LEAVES         = block(GotModBlocks.BLACK_COTTONWOOD_LEAVES);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_STAIRS         = block(GotModBlocks.BLACK_COTTONWOOD_STAIRS);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_SLAB           = block(GotModBlocks.BLACK_COTTONWOOD_SLAB);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_FENCE          = block(GotModBlocks.BLACK_COTTONWOOD_FENCE);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_FENCE_GATE     = block(GotModBlocks.BLACK_COTTONWOOD_FENCE_GATE);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_PRESSURE_PLATE = block(GotModBlocks.BLACK_COTTONWOOD_PRESSURE_PLATE);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_BUTTON         = block(GotModBlocks.BLACK_COTTONWOOD_BUTTON);

    // ── Cinnamon Tree ──────────────────────────────────────────
    public static final DeferredItem<Item> CINNAMON_LOG            = block(GotModBlocks.CINNAMON_LOG);
    public static final DeferredItem<Item> CINNAMON_WOOD           = block(GotModBlocks.CINNAMON_WOOD);
    public static final DeferredItem<Item> CINNAMON_PLANKS         = block(GotModBlocks.CINNAMON_PLANKS);
    public static final DeferredItem<Item> CINNAMON_LEAVES         = block(GotModBlocks.CINNAMON_LEAVES);
    public static final DeferredItem<Item> CINNAMON_STAIRS         = block(GotModBlocks.CINNAMON_STAIRS);
    public static final DeferredItem<Item> CINNAMON_SLAB           = block(GotModBlocks.CINNAMON_SLAB);
    public static final DeferredItem<Item> CINNAMON_FENCE          = block(GotModBlocks.CINNAMON_FENCE);
    public static final DeferredItem<Item> CINNAMON_FENCE_GATE     = block(GotModBlocks.CINNAMON_FENCE_GATE);
    public static final DeferredItem<Item> CINNAMON_PRESSURE_PLATE = block(GotModBlocks.CINNAMON_PRESSURE_PLATE);
    public static final DeferredItem<Item> CINNAMON_BUTTON         = block(GotModBlocks.CINNAMON_BUTTON);

    // ── Clove Tree ──────────────────────────────────────────
    public static final DeferredItem<Item> CLOVE_LOG            = block(GotModBlocks.CLOVE_LOG);
    public static final DeferredItem<Item> CLOVE_WOOD           = block(GotModBlocks.CLOVE_WOOD);
    public static final DeferredItem<Item> CLOVE_PLANKS         = block(GotModBlocks.CLOVE_PLANKS);
    public static final DeferredItem<Item> CLOVE_LEAVES         = block(GotModBlocks.CLOVE_LEAVES);
    public static final DeferredItem<Item> CLOVE_STAIRS         = block(GotModBlocks.CLOVE_STAIRS);
    public static final DeferredItem<Item> CLOVE_SLAB           = block(GotModBlocks.CLOVE_SLAB);
    public static final DeferredItem<Item> CLOVE_FENCE          = block(GotModBlocks.CLOVE_FENCE);
    public static final DeferredItem<Item> CLOVE_FENCE_GATE     = block(GotModBlocks.CLOVE_FENCE_GATE);
    public static final DeferredItem<Item> CLOVE_PRESSURE_PLATE = block(GotModBlocks.CLOVE_PRESSURE_PLATE);
    public static final DeferredItem<Item> CLOVE_BUTTON         = block(GotModBlocks.CLOVE_BUTTON);

    // ── Ebony Tree ──────────────────────────────────────────
    public static final DeferredItem<Item> EBONY_LOG            = block(GotModBlocks.EBONY_LOG);
    public static final DeferredItem<Item> EBONY_WOOD           = block(GotModBlocks.EBONY_WOOD);
    public static final DeferredItem<Item> EBONY_PLANKS         = block(GotModBlocks.EBONY_PLANKS);
    public static final DeferredItem<Item> EBONY_LEAVES         = block(GotModBlocks.EBONY_LEAVES);
    public static final DeferredItem<Item> EBONY_STAIRS         = block(GotModBlocks.EBONY_STAIRS);
    public static final DeferredItem<Item> EBONY_SLAB           = block(GotModBlocks.EBONY_SLAB);
    public static final DeferredItem<Item> EBONY_FENCE          = block(GotModBlocks.EBONY_FENCE);
    public static final DeferredItem<Item> EBONY_FENCE_GATE     = block(GotModBlocks.EBONY_FENCE_GATE);
    public static final DeferredItem<Item> EBONY_PRESSURE_PLATE = block(GotModBlocks.EBONY_PRESSURE_PLATE);
    public static final DeferredItem<Item> EBONY_BUTTON         = block(GotModBlocks.EBONY_BUTTON);

    // ── Elm Tree ──────────────────────────────────────────
    public static final DeferredItem<Item> ELM_LOG            = block(GotModBlocks.ELM_LOG);
    public static final DeferredItem<Item> ELM_WOOD           = block(GotModBlocks.ELM_WOOD);
    public static final DeferredItem<Item> ELM_PLANKS         = block(GotModBlocks.ELM_PLANKS);
    public static final DeferredItem<Item> ELM_LEAVES         = block(GotModBlocks.ELM_LEAVES);
    public static final DeferredItem<Item> ELM_STAIRS         = block(GotModBlocks.ELM_STAIRS);
    public static final DeferredItem<Item> ELM_SLAB           = block(GotModBlocks.ELM_SLAB);
    public static final DeferredItem<Item> ELM_FENCE          = block(GotModBlocks.ELM_FENCE);
    public static final DeferredItem<Item> ELM_FENCE_GATE     = block(GotModBlocks.ELM_FENCE_GATE);
    public static final DeferredItem<Item> ELM_PRESSURE_PLATE = block(GotModBlocks.ELM_PRESSURE_PLATE);
    public static final DeferredItem<Item> ELM_BUTTON         = block(GotModBlocks.ELM_BUTTON);

    // ── Cedar Tree ──────────────────────────────────────────
    public static final DeferredItem<Item> CEDAR_LOG            = block(GotModBlocks.CEDAR_LOG);
    public static final DeferredItem<Item> CEDAR_WOOD           = block(GotModBlocks.CEDAR_WOOD);
    public static final DeferredItem<Item> CEDAR_PLANKS         = block(GotModBlocks.CEDAR_PLANKS);
    public static final DeferredItem<Item> CEDAR_LEAVES         = block(GotModBlocks.CEDAR_LEAVES);
    public static final DeferredItem<Item> CEDAR_STAIRS         = block(GotModBlocks.CEDAR_STAIRS);
    public static final DeferredItem<Item> CEDAR_SLAB           = block(GotModBlocks.CEDAR_SLAB);
    public static final DeferredItem<Item> CEDAR_FENCE          = block(GotModBlocks.CEDAR_FENCE);
    public static final DeferredItem<Item> CEDAR_FENCE_GATE     = block(GotModBlocks.CEDAR_FENCE_GATE);
    public static final DeferredItem<Item> CEDAR_PRESSURE_PLATE = block(GotModBlocks.CEDAR_PRESSURE_PLATE);
    public static final DeferredItem<Item> CEDAR_BUTTON         = block(GotModBlocks.CEDAR_BUTTON);

    // ── Apple Tree
    public static final DeferredItem<Item> APPLE_LOG            = block(GotModBlocks.APPLE_LOG);
    public static final DeferredItem<Item> APPLE_WOOD           = block(GotModBlocks.APPLE_WOOD);
    public static final DeferredItem<Item> APPLE_PLANKS         = block(GotModBlocks.APPLE_PLANKS);
    public static final DeferredItem<Item> APPLE_LEAVES         = block(GotModBlocks.APPLE_LEAVES);
    public static final DeferredItem<Item> APPLE_STAIRS         = block(GotModBlocks.APPLE_STAIRS);
    public static final DeferredItem<Item> APPLE_SLAB           = block(GotModBlocks.APPLE_SLAB);
    public static final DeferredItem<Item> APPLE_FENCE          = block(GotModBlocks.APPLE_FENCE);
    public static final DeferredItem<Item> APPLE_FENCE_GATE     = block(GotModBlocks.APPLE_FENCE_GATE);
    public static final DeferredItem<Item> APPLE_PRESSURE_PLATE = block(GotModBlocks.APPLE_PRESSURE_PLATE);
    public static final DeferredItem<Item> APPLE_BUTTON         = block(GotModBlocks.APPLE_BUTTON);

    // ── Goldenheart Tree
    public static final DeferredItem<Item> GOLDENHEART_LOG            = block(GotModBlocks.GOLDENHEART_LOG);
    public static final DeferredItem<Item> GOLDENHEART_WOOD           = block(GotModBlocks.GOLDENHEART_WOOD);
    public static final DeferredItem<Item> GOLDENHEART_PLANKS         = block(GotModBlocks.GOLDENHEART_PLANKS);
    public static final DeferredItem<Item> GOLDENHEART_LEAVES         = block(GotModBlocks.GOLDENHEART_LEAVES);
    public static final DeferredItem<Item> GOLDENHEART_STAIRS         = block(GotModBlocks.GOLDENHEART_STAIRS);
    public static final DeferredItem<Item> GOLDENHEART_SLAB           = block(GotModBlocks.GOLDENHEART_SLAB);
    public static final DeferredItem<Item> GOLDENHEART_FENCE          = block(GotModBlocks.GOLDENHEART_FENCE);
    public static final DeferredItem<Item> GOLDENHEART_FENCE_GATE     = block(GotModBlocks.GOLDENHEART_FENCE_GATE);
    public static final DeferredItem<Item> GOLDENHEART_PRESSURE_PLATE = block(GotModBlocks.GOLDENHEART_PRESSURE_PLATE);
    public static final DeferredItem<Item> GOLDENHEART_BUTTON         = block(GotModBlocks.GOLDENHEART_BUTTON);

    // ── Linden Tree
    public static final DeferredItem<Item> LINDEN_LOG            = block(GotModBlocks.LINDEN_LOG);
    public static final DeferredItem<Item> LINDEN_WOOD           = block(GotModBlocks.LINDEN_WOOD);
    public static final DeferredItem<Item> LINDEN_PLANKS         = block(GotModBlocks.LINDEN_PLANKS);
    public static final DeferredItem<Item> LINDEN_LEAVES         = block(GotModBlocks.LINDEN_LEAVES);
    public static final DeferredItem<Item> LINDEN_STAIRS         = block(GotModBlocks.LINDEN_STAIRS);
    public static final DeferredItem<Item> LINDEN_SLAB           = block(GotModBlocks.LINDEN_SLAB);
    public static final DeferredItem<Item> LINDEN_FENCE          = block(GotModBlocks.LINDEN_FENCE);
    public static final DeferredItem<Item> LINDEN_FENCE_GATE     = block(GotModBlocks.LINDEN_FENCE_GATE);
    public static final DeferredItem<Item> LINDEN_PRESSURE_PLATE = block(GotModBlocks.LINDEN_PRESSURE_PLATE);
    public static final DeferredItem<Item> LINDEN_BUTTON         = block(GotModBlocks.LINDEN_BUTTON);

    // ── Mahogany Tree
    public static final DeferredItem<Item> MAHOGANY_LOG            = block(GotModBlocks.MAHOGANY_LOG);
    public static final DeferredItem<Item> MAHOGANY_WOOD           = block(GotModBlocks.MAHOGANY_WOOD);
    public static final DeferredItem<Item> MAHOGANY_PLANKS         = block(GotModBlocks.MAHOGANY_PLANKS);
    public static final DeferredItem<Item> MAHOGANY_LEAVES         = block(GotModBlocks.MAHOGANY_LEAVES);
    public static final DeferredItem<Item> MAHOGANY_STAIRS         = block(GotModBlocks.MAHOGANY_STAIRS);
    public static final DeferredItem<Item> MAHOGANY_SLAB           = block(GotModBlocks.MAHOGANY_SLAB);
    public static final DeferredItem<Item> MAHOGANY_FENCE          = block(GotModBlocks.MAHOGANY_FENCE);
    public static final DeferredItem<Item> MAHOGANY_FENCE_GATE     = block(GotModBlocks.MAHOGANY_FENCE_GATE);
    public static final DeferredItem<Item> MAHOGANY_PRESSURE_PLATE = block(GotModBlocks.MAHOGANY_PRESSURE_PLATE);
    public static final DeferredItem<Item> MAHOGANY_BUTTON         = block(GotModBlocks.MAHOGANY_BUTTON);

    // ── Maple Tree
    public static final DeferredItem<Item> MAPLE_LOG            = block(GotModBlocks.MAPLE_LOG);
    public static final DeferredItem<Item> MAPLE_WOOD           = block(GotModBlocks.MAPLE_WOOD);
    public static final DeferredItem<Item> MAPLE_PLANKS         = block(GotModBlocks.MAPLE_PLANKS);
    public static final DeferredItem<Item> MAPLE_LEAVES         = block(GotModBlocks.MAPLE_LEAVES);
    public static final DeferredItem<Item> MAPLE_STAIRS         = block(GotModBlocks.MAPLE_STAIRS);
    public static final DeferredItem<Item> MAPLE_SLAB           = block(GotModBlocks.MAPLE_SLAB);
    public static final DeferredItem<Item> MAPLE_FENCE          = block(GotModBlocks.MAPLE_FENCE);
    public static final DeferredItem<Item> MAPLE_FENCE_GATE     = block(GotModBlocks.MAPLE_FENCE_GATE);
    public static final DeferredItem<Item> MAPLE_PRESSURE_PLATE = block(GotModBlocks.MAPLE_PRESSURE_PLATE);
    public static final DeferredItem<Item> MAPLE_BUTTON         = block(GotModBlocks.MAPLE_BUTTON);

    // ── Myrrh Tree
    public static final DeferredItem<Item> MYRRH_LOG            = block(GotModBlocks.MYRRH_LOG);
    public static final DeferredItem<Item> MYRRH_WOOD           = block(GotModBlocks.MYRRH_WOOD);
    public static final DeferredItem<Item> MYRRH_PLANKS         = block(GotModBlocks.MYRRH_PLANKS);
    public static final DeferredItem<Item> MYRRH_LEAVES         = block(GotModBlocks.MYRRH_LEAVES);
    public static final DeferredItem<Item> MYRRH_STAIRS         = block(GotModBlocks.MYRRH_STAIRS);
    public static final DeferredItem<Item> MYRRH_SLAB           = block(GotModBlocks.MYRRH_SLAB);
    public static final DeferredItem<Item> MYRRH_FENCE          = block(GotModBlocks.MYRRH_FENCE);
    public static final DeferredItem<Item> MYRRH_FENCE_GATE     = block(GotModBlocks.MYRRH_FENCE_GATE);
    public static final DeferredItem<Item> MYRRH_PRESSURE_PLATE = block(GotModBlocks.MYRRH_PRESSURE_PLATE);
    public static final DeferredItem<Item> MYRRH_BUTTON         = block(GotModBlocks.MYRRH_BUTTON);
    // ── Redwood Tree
    public static final DeferredItem<Item> REDWOOD_LOG            = block(GotModBlocks.REDWOOD_LOG);
    public static final DeferredItem<Item> REDWOOD_WOOD           = block(GotModBlocks.REDWOOD_WOOD);
    public static final DeferredItem<Item> REDWOOD_PLANKS         = block(GotModBlocks.REDWOOD_PLANKS);
    public static final DeferredItem<Item> REDWOOD_LEAVES         = block(GotModBlocks.REDWOOD_LEAVES);
    public static final DeferredItem<Item> REDWOOD_STAIRS         = block(GotModBlocks.REDWOOD_STAIRS);
    public static final DeferredItem<Item> REDWOOD_SLAB           = block(GotModBlocks.REDWOOD_SLAB);
    public static final DeferredItem<Item> REDWOOD_FENCE          = block(GotModBlocks.REDWOOD_FENCE);
    public static final DeferredItem<Item> REDWOOD_FENCE_GATE     = block(GotModBlocks.REDWOOD_FENCE_GATE);
    public static final DeferredItem<Item> REDWOOD_PRESSURE_PLATE = block(GotModBlocks.REDWOOD_PRESSURE_PLATE);
    public static final DeferredItem<Item> REDWOOD_BUTTON         = block(GotModBlocks.REDWOOD_BUTTON);

    // ── Chestnut Tree
    public static final DeferredItem<Item> CHESTNUT_LOG            = block(GotModBlocks.CHESTNUT_LOG);
    public static final DeferredItem<Item> CHESTNUT_WOOD           = block(GotModBlocks.CHESTNUT_WOOD);
    public static final DeferredItem<Item> CHESTNUT_PLANKS         = block(GotModBlocks.CHESTNUT_PLANKS);
    public static final DeferredItem<Item> CHESTNUT_LEAVES         = block(GotModBlocks.CHESTNUT_LEAVES);
    public static final DeferredItem<Item> CHESTNUT_STAIRS         = block(GotModBlocks.CHESTNUT_STAIRS);
    public static final DeferredItem<Item> CHESTNUT_SLAB           = block(GotModBlocks.CHESTNUT_SLAB);
    public static final DeferredItem<Item> CHESTNUT_FENCE          = block(GotModBlocks.CHESTNUT_FENCE);
    public static final DeferredItem<Item> CHESTNUT_FENCE_GATE     = block(GotModBlocks.CHESTNUT_FENCE_GATE);
    public static final DeferredItem<Item> CHESTNUT_PRESSURE_PLATE = block(GotModBlocks.CHESTNUT_PRESSURE_PLATE);
    public static final DeferredItem<Item> CHESTNUT_BUTTON         = block(GotModBlocks.CHESTNUT_BUTTON);

    // ── Willow Tree
    public static final DeferredItem<Item> WILLOW_LOG            = block(GotModBlocks.WILLOW_LOG);
    public static final DeferredItem<Item> WILLOW_WOOD           = block(GotModBlocks.WILLOW_WOOD);
    public static final DeferredItem<Item> WILLOW_PLANKS         = block(GotModBlocks.WILLOW_PLANKS);
    public static final DeferredItem<Item> WILLOW_LEAVES         = block(GotModBlocks.WILLOW_LEAVES);
    public static final DeferredItem<Item> WILLOW_STAIRS         = block(GotModBlocks.WILLOW_STAIRS);
    public static final DeferredItem<Item> WILLOW_SLAB           = block(GotModBlocks.WILLOW_SLAB);
    public static final DeferredItem<Item> WILLOW_FENCE          = block(GotModBlocks.WILLOW_FENCE);
    public static final DeferredItem<Item> WILLOW_FENCE_GATE     = block(GotModBlocks.WILLOW_FENCE_GATE);
    public static final DeferredItem<Item> WILLOW_PRESSURE_PLATE = block(GotModBlocks.WILLOW_PRESSURE_PLATE);
    public static final DeferredItem<Item> WILLOW_BUTTON         = block(GotModBlocks.WILLOW_BUTTON);

    // ── Wormtree Tree
    public static final DeferredItem<Item> WORMTREE_LOG            = block(GotModBlocks.WORMTREE_LOG);
    public static final DeferredItem<Item> WORMTREE_WOOD           = block(GotModBlocks.WORMTREE_WOOD);
    public static final DeferredItem<Item> WORMTREE_PLANKS         = block(GotModBlocks.WORMTREE_PLANKS);
    public static final DeferredItem<Item> WORMTREE_LEAVES         = block(GotModBlocks.WORMTREE_LEAVES);
    public static final DeferredItem<Item> WORMTREE_STAIRS         = block(GotModBlocks.WORMTREE_STAIRS);
    public static final DeferredItem<Item> WORMTREE_SLAB           = block(GotModBlocks.WORMTREE_SLAB);
    public static final DeferredItem<Item> WORMTREE_FENCE          = block(GotModBlocks.WORMTREE_FENCE);
    public static final DeferredItem<Item> WORMTREE_FENCE_GATE     = block(GotModBlocks.WORMTREE_FENCE_GATE);
    public static final DeferredItem<Item> WORMTREE_PRESSURE_PLATE = block(GotModBlocks.WORMTREE_PRESSURE_PLATE);
    public static final DeferredItem<Item> WORMTREE_BUTTON         = block(GotModBlocks.WORMTREE_BUTTON);

    // ── Basalt items ──────────────────────────────────────────────────────
    public static final DeferredItem<Item> BASALT_ROCK = block(GotModBlocks.BASALT_ROCK);
    public static final DeferredItem<Item> BASALT_BRICK = block(GotModBlocks.BASALT_BRICK);
    public static final DeferredItem<Item> CRACKED_BASALT_BRICK = block(GotModBlocks.CRACKED_BASALT_BRICK);
    public static final DeferredItem<Item> MOSSY_BASALT_BRICK = block(GotModBlocks.MOSSY_BASALT_BRICK);
    public static final DeferredItem<Item> BASALT_COBBLESTONE = block(GotModBlocks.BASALT_COBBLESTONE);
    public static final DeferredItem<Item> MOSSY_BASALT_COBBLESTONE = block(GotModBlocks.MOSSY_BASALT_COBBLESTONE);
    public static final DeferredItem<Item> SMOOTH_BASALT_ROCK = block(GotModBlocks.SMOOTH_BASALT_ROCK);
    public static final DeferredItem<Item> BASALT_PILLAR = block(GotModBlocks.BASALT_PILLAR);
    public static final DeferredItem<Item> BASALT_ROCK_SLAB = block(GotModBlocks.BASALT_ROCK_SLAB);
    public static final DeferredItem<Item> BASALT_ROCK_STAIRS = block(GotModBlocks.BASALT_ROCK_STAIRS);
    public static final DeferredItem<Item> BASALT_ROCK_WALL = block(GotModBlocks.BASALT_ROCK_WALL);
    public static final DeferredItem<Item> BASALT_ROCK_BUTTON = block(GotModBlocks.BASALT_ROCK_BUTTON);
    public static final DeferredItem<Item> BASALT_ROCK_PRESSURE_PLATE = block(GotModBlocks.BASALT_ROCK_PRESSURE_PLATE);
    public static final DeferredItem<Item> BASALT_BRICK_SLAB = block(GotModBlocks.BASALT_BRICK_SLAB);
    public static final DeferredItem<Item> BASALT_BRICK_STAIRS = block(GotModBlocks.BASALT_BRICK_STAIRS);
    public static final DeferredItem<Item> BASALT_BRICK_WALL = block(GotModBlocks.BASALT_BRICK_WALL);
    public static final DeferredItem<Item> CRACKED_BASALT_BRICK_SLAB = block(GotModBlocks.CRACKED_BASALT_BRICK_SLAB);
    public static final DeferredItem<Item> CRACKED_BASALT_BRICK_STAIRS = block(GotModBlocks.CRACKED_BASALT_BRICK_STAIRS);
    public static final DeferredItem<Item> CRACKED_BASALT_BRICK_WALL = block(GotModBlocks.CRACKED_BASALT_BRICK_WALL);
    public static final DeferredItem<Item> MOSSY_BASALT_BRICK_SLAB = block(GotModBlocks.MOSSY_BASALT_BRICK_SLAB);
    public static final DeferredItem<Item> MOSSY_BASALT_BRICK_STAIRS = block(GotModBlocks.MOSSY_BASALT_BRICK_STAIRS);
    public static final DeferredItem<Item> MOSSY_BASALT_BRICK_WALL = block(GotModBlocks.MOSSY_BASALT_BRICK_WALL);
    public static final DeferredItem<Item> BASALT_COBBLESTONE_SLAB = block(GotModBlocks.BASALT_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> BASALT_COBBLESTONE_STAIRS = block(GotModBlocks.BASALT_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> BASALT_COBBLESTONE_WALL = block(GotModBlocks.BASALT_COBBLESTONE_WALL);
    public static final DeferredItem<Item> MOSSY_BASALT_COBBLESTONE_SLAB = block(GotModBlocks.MOSSY_BASALT_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> MOSSY_BASALT_COBBLESTONE_STAIRS = block(GotModBlocks.MOSSY_BASALT_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> MOSSY_BASALT_COBBLESTONE_WALL = block(GotModBlocks.MOSSY_BASALT_COBBLESTONE_WALL);
    public static final DeferredItem<Item> SMOOTH_BASALT_ROCK_SLAB = block(GotModBlocks.SMOOTH_BASALT_ROCK_SLAB);
    public static final DeferredItem<Item> SMOOTH_BASALT_ROCK_STAIRS = block(GotModBlocks.SMOOTH_BASALT_ROCK_STAIRS);
    public static final DeferredItem<Item> SMOOTH_BASALT_ROCK_WALL = block(GotModBlocks.SMOOTH_BASALT_ROCK_WALL);

    // ── Granite items ──────────────────────────────────────────────────────
    public static final DeferredItem<Item> GRANITE_ROCK = block(GotModBlocks.GRANITE_ROCK);
    public static final DeferredItem<Item> GRANITE_BRICK = block(GotModBlocks.GRANITE_BRICK);
    public static final DeferredItem<Item> CRACKED_GRANITE_BRICK = block(GotModBlocks.CRACKED_GRANITE_BRICK);
    public static final DeferredItem<Item> MOSSY_GRANITE_BRICK = block(GotModBlocks.MOSSY_GRANITE_BRICK);
    public static final DeferredItem<Item> GRANITE_COBBLESTONE = block(GotModBlocks.GRANITE_COBBLESTONE);
    public static final DeferredItem<Item> MOSSY_GRANITE_COBBLESTONE = block(GotModBlocks.MOSSY_GRANITE_COBBLESTONE);
    public static final DeferredItem<Item> SMOOTH_GRANITE_ROCK = block(GotModBlocks.SMOOTH_GRANITE_ROCK);
    public static final DeferredItem<Item> GRANITE_PILLAR = block(GotModBlocks.GRANITE_PILLAR);
    public static final DeferredItem<Item> GRANITE_ROCK_SLAB = block(GotModBlocks.GRANITE_ROCK_SLAB);
    public static final DeferredItem<Item> GRANITE_ROCK_STAIRS = block(GotModBlocks.GRANITE_ROCK_STAIRS);
    public static final DeferredItem<Item> GRANITE_ROCK_WALL = block(GotModBlocks.GRANITE_ROCK_WALL);
    public static final DeferredItem<Item> GRANITE_ROCK_BUTTON = block(GotModBlocks.GRANITE_ROCK_BUTTON);
    public static final DeferredItem<Item> GRANITE_ROCK_PRESSURE_PLATE = block(GotModBlocks.GRANITE_ROCK_PRESSURE_PLATE);
    public static final DeferredItem<Item> GRANITE_BRICK_SLAB = block(GotModBlocks.GRANITE_BRICK_SLAB);
    public static final DeferredItem<Item> GRANITE_BRICK_STAIRS = block(GotModBlocks.GRANITE_BRICK_STAIRS);
    public static final DeferredItem<Item> GRANITE_BRICK_WALL = block(GotModBlocks.GRANITE_BRICK_WALL);
    public static final DeferredItem<Item> CRACKED_GRANITE_BRICK_SLAB = block(GotModBlocks.CRACKED_GRANITE_BRICK_SLAB);
    public static final DeferredItem<Item> CRACKED_GRANITE_BRICK_STAIRS = block(GotModBlocks.CRACKED_GRANITE_BRICK_STAIRS);
    public static final DeferredItem<Item> CRACKED_GRANITE_BRICK_WALL = block(GotModBlocks.CRACKED_GRANITE_BRICK_WALL);
    public static final DeferredItem<Item> MOSSY_GRANITE_BRICK_SLAB = block(GotModBlocks.MOSSY_GRANITE_BRICK_SLAB);
    public static final DeferredItem<Item> MOSSY_GRANITE_BRICK_STAIRS = block(GotModBlocks.MOSSY_GRANITE_BRICK_STAIRS);
    public static final DeferredItem<Item> MOSSY_GRANITE_BRICK_WALL = block(GotModBlocks.MOSSY_GRANITE_BRICK_WALL);
    public static final DeferredItem<Item> GRANITE_COBBLESTONE_SLAB = block(GotModBlocks.GRANITE_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> GRANITE_COBBLESTONE_STAIRS = block(GotModBlocks.GRANITE_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> GRANITE_COBBLESTONE_WALL = block(GotModBlocks.GRANITE_COBBLESTONE_WALL);
    public static final DeferredItem<Item> MOSSY_GRANITE_COBBLESTONE_SLAB = block(GotModBlocks.MOSSY_GRANITE_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> MOSSY_GRANITE_COBBLESTONE_STAIRS = block(GotModBlocks.MOSSY_GRANITE_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> MOSSY_GRANITE_COBBLESTONE_WALL = block(GotModBlocks.MOSSY_GRANITE_COBBLESTONE_WALL);
    public static final DeferredItem<Item> SMOOTH_GRANITE_ROCK_SLAB = block(GotModBlocks.SMOOTH_GRANITE_ROCK_SLAB);
    public static final DeferredItem<Item> SMOOTH_GRANITE_ROCK_STAIRS = block(GotModBlocks.SMOOTH_GRANITE_ROCK_STAIRS);
    public static final DeferredItem<Item> SMOOTH_GRANITE_ROCK_WALL = block(GotModBlocks.SMOOTH_GRANITE_ROCK_WALL);

    // ── Limestone items ──────────────────────────────────────────────────────
    public static final DeferredItem<Item> LIMESTONE_ROCK = block(GotModBlocks.LIMESTONE_ROCK);
    public static final DeferredItem<Item> LIMESTONE_BRICK = block(GotModBlocks.LIMESTONE_BRICK);
    public static final DeferredItem<Item> CRACKED_LIMESTONE_BRICK = block(GotModBlocks.CRACKED_LIMESTONE_BRICK);
    public static final DeferredItem<Item> MOSSY_LIMESTONE_BRICK = block(GotModBlocks.MOSSY_LIMESTONE_BRICK);
    public static final DeferredItem<Item> LIMESTONE_COBBLESTONE = block(GotModBlocks.LIMESTONE_COBBLESTONE);
    public static final DeferredItem<Item> MOSSY_LIMESTONE_COBBLESTONE = block(GotModBlocks.MOSSY_LIMESTONE_COBBLESTONE);
    public static final DeferredItem<Item> SMOOTH_LIMESTONE_ROCK = block(GotModBlocks.SMOOTH_LIMESTONE_ROCK);
    public static final DeferredItem<Item> LIMESTONE_PILLAR = block(GotModBlocks.LIMESTONE_PILLAR);
    public static final DeferredItem<Item> LIMESTONE_ROCK_SLAB = block(GotModBlocks.LIMESTONE_ROCK_SLAB);
    public static final DeferredItem<Item> LIMESTONE_ROCK_STAIRS = block(GotModBlocks.LIMESTONE_ROCK_STAIRS);
    public static final DeferredItem<Item> LIMESTONE_ROCK_WALL = block(GotModBlocks.LIMESTONE_ROCK_WALL);
    public static final DeferredItem<Item> LIMESTONE_ROCK_BUTTON = block(GotModBlocks.LIMESTONE_ROCK_BUTTON);
    public static final DeferredItem<Item> LIMESTONE_ROCK_PRESSURE_PLATE = block(GotModBlocks.LIMESTONE_ROCK_PRESSURE_PLATE);
    public static final DeferredItem<Item> LIMESTONE_BRICK_SLAB = block(GotModBlocks.LIMESTONE_BRICK_SLAB);
    public static final DeferredItem<Item> LIMESTONE_BRICK_STAIRS = block(GotModBlocks.LIMESTONE_BRICK_STAIRS);
    public static final DeferredItem<Item> LIMESTONE_BRICK_WALL = block(GotModBlocks.LIMESTONE_BRICK_WALL);
    public static final DeferredItem<Item> CRACKED_LIMESTONE_BRICK_SLAB = block(GotModBlocks.CRACKED_LIMESTONE_BRICK_SLAB);
    public static final DeferredItem<Item> CRACKED_LIMESTONE_BRICK_STAIRS = block(GotModBlocks.CRACKED_LIMESTONE_BRICK_STAIRS);
    public static final DeferredItem<Item> CRACKED_LIMESTONE_BRICK_WALL = block(GotModBlocks.CRACKED_LIMESTONE_BRICK_WALL);
    public static final DeferredItem<Item> MOSSY_LIMESTONE_BRICK_SLAB = block(GotModBlocks.MOSSY_LIMESTONE_BRICK_SLAB);
    public static final DeferredItem<Item> MOSSY_LIMESTONE_BRICK_STAIRS = block(GotModBlocks.MOSSY_LIMESTONE_BRICK_STAIRS);
    public static final DeferredItem<Item> MOSSY_LIMESTONE_BRICK_WALL = block(GotModBlocks.MOSSY_LIMESTONE_BRICK_WALL);
    public static final DeferredItem<Item> LIMESTONE_COBBLESTONE_SLAB = block(GotModBlocks.LIMESTONE_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> LIMESTONE_COBBLESTONE_STAIRS = block(GotModBlocks.LIMESTONE_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> LIMESTONE_COBBLESTONE_WALL = block(GotModBlocks.LIMESTONE_COBBLESTONE_WALL);
    public static final DeferredItem<Item> MOSSY_LIMESTONE_COBBLESTONE_SLAB = block(GotModBlocks.MOSSY_LIMESTONE_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> MOSSY_LIMESTONE_COBBLESTONE_STAIRS = block(GotModBlocks.MOSSY_LIMESTONE_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> MOSSY_LIMESTONE_COBBLESTONE_WALL = block(GotModBlocks.MOSSY_LIMESTONE_COBBLESTONE_WALL);
    public static final DeferredItem<Item> SMOOTH_LIMESTONE_ROCK_SLAB = block(GotModBlocks.SMOOTH_LIMESTONE_ROCK_SLAB);
    public static final DeferredItem<Item> SMOOTH_LIMESTONE_ROCK_STAIRS = block(GotModBlocks.SMOOTH_LIMESTONE_ROCK_STAIRS);
    public static final DeferredItem<Item> SMOOTH_LIMESTONE_ROCK_WALL = block(GotModBlocks.SMOOTH_LIMESTONE_ROCK_WALL);

    // ── Sandstone items ──────────────────────────────────────────────────────
    public static final DeferredItem<Item> SANDSTONE_ROCK = block(GotModBlocks.SANDSTONE_ROCK);
    public static final DeferredItem<Item> SANDSTONE_BRICK = block(GotModBlocks.SANDSTONE_BRICK);
    public static final DeferredItem<Item> CRACKED_SANDSTONE_BRICK = block(GotModBlocks.CRACKED_SANDSTONE_BRICK);
    public static final DeferredItem<Item> MOSSY_SANDSTONE_BRICK = block(GotModBlocks.MOSSY_SANDSTONE_BRICK);
    public static final DeferredItem<Item> SANDSTONE_COBBLESTONE = block(GotModBlocks.SANDSTONE_COBBLESTONE);
    public static final DeferredItem<Item> MOSSY_SANDSTONE_COBBLESTONE = block(GotModBlocks.MOSSY_SANDSTONE_COBBLESTONE);
    public static final DeferredItem<Item> SMOOTH_SANDSTONE_ROCK = block(GotModBlocks.SMOOTH_SANDSTONE_ROCK);
    public static final DeferredItem<Item> SANDSTONE_PILLAR = block(GotModBlocks.SANDSTONE_PILLAR);
    public static final DeferredItem<Item> SANDSTONE_ROCK_SLAB = block(GotModBlocks.SANDSTONE_ROCK_SLAB);
    public static final DeferredItem<Item> SANDSTONE_ROCK_STAIRS = block(GotModBlocks.SANDSTONE_ROCK_STAIRS);
    public static final DeferredItem<Item> SANDSTONE_ROCK_WALL = block(GotModBlocks.SANDSTONE_ROCK_WALL);
    public static final DeferredItem<Item> SANDSTONE_ROCK_BUTTON = block(GotModBlocks.SANDSTONE_ROCK_BUTTON);
    public static final DeferredItem<Item> SANDSTONE_ROCK_PRESSURE_PLATE = block(GotModBlocks.SANDSTONE_ROCK_PRESSURE_PLATE);
    public static final DeferredItem<Item> SANDSTONE_BRICK_SLAB = block(GotModBlocks.SANDSTONE_BRICK_SLAB);
    public static final DeferredItem<Item> SANDSTONE_BRICK_STAIRS = block(GotModBlocks.SANDSTONE_BRICK_STAIRS);
    public static final DeferredItem<Item> SANDSTONE_BRICK_WALL = block(GotModBlocks.SANDSTONE_BRICK_WALL);
    public static final DeferredItem<Item> CRACKED_SANDSTONE_BRICK_SLAB = block(GotModBlocks.CRACKED_SANDSTONE_BRICK_SLAB);
    public static final DeferredItem<Item> CRACKED_SANDSTONE_BRICK_STAIRS = block(GotModBlocks.CRACKED_SANDSTONE_BRICK_STAIRS);
    public static final DeferredItem<Item> CRACKED_SANDSTONE_BRICK_WALL = block(GotModBlocks.CRACKED_SANDSTONE_BRICK_WALL);
    public static final DeferredItem<Item> MOSSY_SANDSTONE_BRICK_SLAB = block(GotModBlocks.MOSSY_SANDSTONE_BRICK_SLAB);
    public static final DeferredItem<Item> MOSSY_SANDSTONE_BRICK_STAIRS = block(GotModBlocks.MOSSY_SANDSTONE_BRICK_STAIRS);
    public static final DeferredItem<Item> MOSSY_SANDSTONE_BRICK_WALL = block(GotModBlocks.MOSSY_SANDSTONE_BRICK_WALL);
    public static final DeferredItem<Item> SANDSTONE_COBBLESTONE_SLAB = block(GotModBlocks.SANDSTONE_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> SANDSTONE_COBBLESTONE_STAIRS = block(GotModBlocks.SANDSTONE_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> SANDSTONE_COBBLESTONE_WALL = block(GotModBlocks.SANDSTONE_COBBLESTONE_WALL);
    public static final DeferredItem<Item> MOSSY_SANDSTONE_COBBLESTONE_SLAB = block(GotModBlocks.MOSSY_SANDSTONE_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> MOSSY_SANDSTONE_COBBLESTONE_STAIRS = block(GotModBlocks.MOSSY_SANDSTONE_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> MOSSY_SANDSTONE_COBBLESTONE_WALL = block(GotModBlocks.MOSSY_SANDSTONE_COBBLESTONE_WALL);
    public static final DeferredItem<Item> SMOOTH_SANDSTONE_ROCK_SLAB = block(GotModBlocks.SMOOTH_SANDSTONE_ROCK_SLAB);
    public static final DeferredItem<Item> SMOOTH_SANDSTONE_ROCK_STAIRS = block(GotModBlocks.SMOOTH_SANDSTONE_ROCK_STAIRS);
    public static final DeferredItem<Item> SMOOTH_SANDSTONE_ROCK_WALL = block(GotModBlocks.SMOOTH_SANDSTONE_ROCK_WALL);

    // ── Red Sandstone items ──────────────────────────────────────────────────────
    public static final DeferredItem<Item> RED_SANDSTONE_ROCK = block(GotModBlocks.RED_SANDSTONE_ROCK);
    public static final DeferredItem<Item> RED_SANDSTONE_BRICK = block(GotModBlocks.RED_SANDSTONE_BRICK);
    public static final DeferredItem<Item> CRACKED_RED_SANDSTONE_BRICK = block(GotModBlocks.CRACKED_RED_SANDSTONE_BRICK);
    public static final DeferredItem<Item> MOSSY_RED_SANDSTONE_BRICK = block(GotModBlocks.MOSSY_RED_SANDSTONE_BRICK);
    public static final DeferredItem<Item> RED_SANDSTONE_COBBLESTONE = block(GotModBlocks.RED_SANDSTONE_COBBLESTONE);
    public static final DeferredItem<Item> MOSSY_RED_SANDSTONE_COBBLESTONE = block(GotModBlocks.MOSSY_RED_SANDSTONE_COBBLESTONE);
    public static final DeferredItem<Item> SMOOTH_RED_SANDSTONE_ROCK = block(GotModBlocks.SMOOTH_RED_SANDSTONE_ROCK);
    public static final DeferredItem<Item> RED_SANDSTONE_PILLAR = block(GotModBlocks.RED_SANDSTONE_PILLAR);
    public static final DeferredItem<Item> RED_SANDSTONE_ROCK_SLAB = block(GotModBlocks.RED_SANDSTONE_ROCK_SLAB);
    public static final DeferredItem<Item> RED_SANDSTONE_ROCK_STAIRS = block(GotModBlocks.RED_SANDSTONE_ROCK_STAIRS);
    public static final DeferredItem<Item> RED_SANDSTONE_ROCK_WALL = block(GotModBlocks.RED_SANDSTONE_ROCK_WALL);
    public static final DeferredItem<Item> RED_SANDSTONE_ROCK_BUTTON = block(GotModBlocks.RED_SANDSTONE_ROCK_BUTTON);
    public static final DeferredItem<Item> RED_SANDSTONE_ROCK_PRESSURE_PLATE = block(GotModBlocks.RED_SANDSTONE_ROCK_PRESSURE_PLATE);
    public static final DeferredItem<Item> RED_SANDSTONE_BRICK_SLAB = block(GotModBlocks.RED_SANDSTONE_BRICK_SLAB);
    public static final DeferredItem<Item> RED_SANDSTONE_BRICK_STAIRS = block(GotModBlocks.RED_SANDSTONE_BRICK_STAIRS);
    public static final DeferredItem<Item> RED_SANDSTONE_BRICK_WALL = block(GotModBlocks.RED_SANDSTONE_BRICK_WALL);
    public static final DeferredItem<Item> CRACKED_RED_SANDSTONE_BRICK_SLAB = block(GotModBlocks.CRACKED_RED_SANDSTONE_BRICK_SLAB);
    public static final DeferredItem<Item> CRACKED_RED_SANDSTONE_BRICK_STAIRS = block(GotModBlocks.CRACKED_RED_SANDSTONE_BRICK_STAIRS);
    public static final DeferredItem<Item> CRACKED_RED_SANDSTONE_BRICK_WALL = block(GotModBlocks.CRACKED_RED_SANDSTONE_BRICK_WALL);
    public static final DeferredItem<Item> MOSSY_RED_SANDSTONE_BRICK_SLAB = block(GotModBlocks.MOSSY_RED_SANDSTONE_BRICK_SLAB);
    public static final DeferredItem<Item> MOSSY_RED_SANDSTONE_BRICK_STAIRS = block(GotModBlocks.MOSSY_RED_SANDSTONE_BRICK_STAIRS);
    public static final DeferredItem<Item> MOSSY_RED_SANDSTONE_BRICK_WALL = block(GotModBlocks.MOSSY_RED_SANDSTONE_BRICK_WALL);
    public static final DeferredItem<Item> RED_SANDSTONE_COBBLESTONE_SLAB = block(GotModBlocks.RED_SANDSTONE_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> RED_SANDSTONE_COBBLESTONE_STAIRS = block(GotModBlocks.RED_SANDSTONE_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> RED_SANDSTONE_COBBLESTONE_WALL = block(GotModBlocks.RED_SANDSTONE_COBBLESTONE_WALL);
    public static final DeferredItem<Item> MOSSY_RED_SANDSTONE_COBBLESTONE_SLAB = block(GotModBlocks.MOSSY_RED_SANDSTONE_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> MOSSY_RED_SANDSTONE_COBBLESTONE_STAIRS = block(GotModBlocks.MOSSY_RED_SANDSTONE_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> MOSSY_RED_SANDSTONE_COBBLESTONE_WALL = block(GotModBlocks.MOSSY_RED_SANDSTONE_COBBLESTONE_WALL);
    public static final DeferredItem<Item> SMOOTH_RED_SANDSTONE_ROCK_SLAB = block(GotModBlocks.SMOOTH_RED_SANDSTONE_ROCK_SLAB);
    public static final DeferredItem<Item> SMOOTH_RED_SANDSTONE_ROCK_STAIRS = block(GotModBlocks.SMOOTH_RED_SANDSTONE_ROCK_STAIRS);
    public static final DeferredItem<Item> SMOOTH_RED_SANDSTONE_ROCK_WALL = block(GotModBlocks.SMOOTH_RED_SANDSTONE_ROCK_WALL);

    // ── Slate items ──────────────────────────────────────────────────────
    public static final DeferredItem<Item> SLATE_ROCK = block(GotModBlocks.SLATE_ROCK);
    public static final DeferredItem<Item> SLATE_BRICK = block(GotModBlocks.SLATE_BRICK);
    public static final DeferredItem<Item> CRACKED_SLATE_BRICK = block(GotModBlocks.CRACKED_SLATE_BRICK);
    public static final DeferredItem<Item> MOSSY_SLATE_BRICK = block(GotModBlocks.MOSSY_SLATE_BRICK);
    public static final DeferredItem<Item> SLATE_COBBLESTONE = block(GotModBlocks.SLATE_COBBLESTONE);
    public static final DeferredItem<Item> MOSSY_SLATE_COBBLESTONE = block(GotModBlocks.MOSSY_SLATE_COBBLESTONE);
    public static final DeferredItem<Item> SMOOTH_SLATE_ROCK = block(GotModBlocks.SMOOTH_SLATE_ROCK);
    public static final DeferredItem<Item> SLATE_PILLAR = block(GotModBlocks.SLATE_PILLAR);
    public static final DeferredItem<Item> SLATE_ROCK_SLAB = block(GotModBlocks.SLATE_ROCK_SLAB);
    public static final DeferredItem<Item> SLATE_ROCK_STAIRS = block(GotModBlocks.SLATE_ROCK_STAIRS);
    public static final DeferredItem<Item> SLATE_ROCK_WALL = block(GotModBlocks.SLATE_ROCK_WALL);
    public static final DeferredItem<Item> SLATE_ROCK_BUTTON = block(GotModBlocks.SLATE_ROCK_BUTTON);
    public static final DeferredItem<Item> SLATE_ROCK_PRESSURE_PLATE = block(GotModBlocks.SLATE_ROCK_PRESSURE_PLATE);
    public static final DeferredItem<Item> SLATE_BRICK_SLAB = block(GotModBlocks.SLATE_BRICK_SLAB);
    public static final DeferredItem<Item> SLATE_BRICK_STAIRS = block(GotModBlocks.SLATE_BRICK_STAIRS);
    public static final DeferredItem<Item> SLATE_BRICK_WALL = block(GotModBlocks.SLATE_BRICK_WALL);
    public static final DeferredItem<Item> CRACKED_SLATE_BRICK_SLAB = block(GotModBlocks.CRACKED_SLATE_BRICK_SLAB);
    public static final DeferredItem<Item> CRACKED_SLATE_BRICK_STAIRS = block(GotModBlocks.CRACKED_SLATE_BRICK_STAIRS);
    public static final DeferredItem<Item> CRACKED_SLATE_BRICK_WALL = block(GotModBlocks.CRACKED_SLATE_BRICK_WALL);
    public static final DeferredItem<Item> MOSSY_SLATE_BRICK_SLAB = block(GotModBlocks.MOSSY_SLATE_BRICK_SLAB);
    public static final DeferredItem<Item> MOSSY_SLATE_BRICK_STAIRS = block(GotModBlocks.MOSSY_SLATE_BRICK_STAIRS);
    public static final DeferredItem<Item> MOSSY_SLATE_BRICK_WALL = block(GotModBlocks.MOSSY_SLATE_BRICK_WALL);
    public static final DeferredItem<Item> SLATE_COBBLESTONE_SLAB = block(GotModBlocks.SLATE_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> SLATE_COBBLESTONE_STAIRS = block(GotModBlocks.SLATE_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> SLATE_COBBLESTONE_WALL = block(GotModBlocks.SLATE_COBBLESTONE_WALL);
    public static final DeferredItem<Item> MOSSY_SLATE_COBBLESTONE_SLAB = block(GotModBlocks.MOSSY_SLATE_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> MOSSY_SLATE_COBBLESTONE_STAIRS = block(GotModBlocks.MOSSY_SLATE_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> MOSSY_SLATE_COBBLESTONE_WALL = block(GotModBlocks.MOSSY_SLATE_COBBLESTONE_WALL);
    public static final DeferredItem<Item> SMOOTH_SLATE_ROCK_SLAB = block(GotModBlocks.SMOOTH_SLATE_ROCK_SLAB);
    public static final DeferredItem<Item> SMOOTH_SLATE_ROCK_STAIRS = block(GotModBlocks.SMOOTH_SLATE_ROCK_STAIRS);
    public static final DeferredItem<Item> SMOOTH_SLATE_ROCK_WALL = block(GotModBlocks.SMOOTH_SLATE_ROCK_WALL);
    public static final DeferredItem<Item> SLATE_SHINGLES = block(GotModBlocks.SLATE_SHINGLES);

    // ── Oily Black Stone items ────────────────────────────────────────────
    public static final DeferredItem<Item> OILY_BLACK_ROCK = block(GotModBlocks.OILY_BLACK_ROCK);
    public static final DeferredItem<Item> OILY_BLACK_BRICK = block(GotModBlocks.OILY_BLACK_BRICK);
    public static final DeferredItem<Item> CRACKED_OILY_BLACK_BRICK = block(GotModBlocks.CRACKED_OILY_BLACK_BRICK);
    public static final DeferredItem<Item> MOSSY_OILY_BLACK_BRICK = block(GotModBlocks.MOSSY_OILY_BLACK_BRICK);
    public static final DeferredItem<Item> OILY_BLACK_COBBLESTONE = block(GotModBlocks.OILY_BLACK_COBBLESTONE);
    public static final DeferredItem<Item> MOSSY_OILY_BLACK_COBBLESTONE = block(GotModBlocks.MOSSY_OILY_BLACK_COBBLESTONE);
    public static final DeferredItem<Item> SMOOTH_OILY_BLACK_ROCK = block(GotModBlocks.SMOOTH_OILY_BLACK_ROCK);
    public static final DeferredItem<Item> OILY_BLACK_PILLAR = block(GotModBlocks.OILY_BLACK_PILLAR);
    public static final DeferredItem<Item> OILY_BLACK_ROCK_SLAB = block(GotModBlocks.OILY_BLACK_ROCK_SLAB);
    public static final DeferredItem<Item> OILY_BLACK_ROCK_STAIRS = block(GotModBlocks.OILY_BLACK_ROCK_STAIRS);
    public static final DeferredItem<Item> OILY_BLACK_ROCK_WALL = block(GotModBlocks.OILY_BLACK_ROCK_WALL);
    public static final DeferredItem<Item> OILY_BLACK_ROCK_BUTTON = block(GotModBlocks.OILY_BLACK_ROCK_BUTTON);
    public static final DeferredItem<Item> OILY_BLACK_ROCK_PRESSURE_PLATE = block(GotModBlocks.OILY_BLACK_ROCK_PRESSURE_PLATE);
    public static final DeferredItem<Item> OILY_BLACK_BRICK_SLAB = block(GotModBlocks.OILY_BLACK_BRICK_SLAB);
    public static final DeferredItem<Item> OILY_BLACK_BRICK_STAIRS = block(GotModBlocks.OILY_BLACK_BRICK_STAIRS);
    public static final DeferredItem<Item> OILY_BLACK_BRICK_WALL = block(GotModBlocks.OILY_BLACK_BRICK_WALL);
    public static final DeferredItem<Item> CRACKED_OILY_BLACK_BRICK_SLAB = block(GotModBlocks.CRACKED_OILY_BLACK_BRICK_SLAB);
    public static final DeferredItem<Item> CRACKED_OILY_BLACK_BRICK_STAIRS = block(GotModBlocks.CRACKED_OILY_BLACK_BRICK_STAIRS);
    public static final DeferredItem<Item> CRACKED_OILY_BLACK_BRICK_WALL = block(GotModBlocks.CRACKED_OILY_BLACK_BRICK_WALL);
    public static final DeferredItem<Item> MOSSY_OILY_BLACK_BRICK_SLAB = block(GotModBlocks.MOSSY_OILY_BLACK_BRICK_SLAB);
    public static final DeferredItem<Item> MOSSY_OILY_BLACK_BRICK_STAIRS = block(GotModBlocks.MOSSY_OILY_BLACK_BRICK_STAIRS);
    public static final DeferredItem<Item> MOSSY_OILY_BLACK_BRICK_WALL = block(GotModBlocks.MOSSY_OILY_BLACK_BRICK_WALL);
    public static final DeferredItem<Item> OILY_BLACK_COBBLESTONE_SLAB = block(GotModBlocks.OILY_BLACK_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> OILY_BLACK_COBBLESTONE_STAIRS = block(GotModBlocks.OILY_BLACK_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> OILY_BLACK_COBBLESTONE_WALL = block(GotModBlocks.OILY_BLACK_COBBLESTONE_WALL);
    public static final DeferredItem<Item> MOSSY_OILY_BLACK_COBBLESTONE_SLAB = block(GotModBlocks.MOSSY_OILY_BLACK_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> MOSSY_OILY_BLACK_COBBLESTONE_STAIRS = block(GotModBlocks.MOSSY_OILY_BLACK_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> MOSSY_OILY_BLACK_COBBLESTONE_WALL = block(GotModBlocks.MOSSY_OILY_BLACK_COBBLESTONE_WALL);
    public static final DeferredItem<Item> SMOOTH_OILY_BLACK_ROCK_SLAB = block(GotModBlocks.SMOOTH_OILY_BLACK_ROCK_SLAB);
    public static final DeferredItem<Item> SMOOTH_OILY_BLACK_ROCK_STAIRS = block(GotModBlocks.SMOOTH_OILY_BLACK_ROCK_STAIRS);
    public static final DeferredItem<Item> SMOOTH_OILY_BLACK_ROCK_WALL = block(GotModBlocks.SMOOTH_OILY_BLACK_ROCK_WALL);

    // ── Fused Black Stone items ───────────────────────────────────────────
    public static final DeferredItem<Item> FUSED_BLACK_ROCK = block(GotModBlocks.FUSED_BLACK_ROCK);
    public static final DeferredItem<Item> FUSED_BLACK_BRICK = block(GotModBlocks.FUSED_BLACK_BRICK);
    public static final DeferredItem<Item> CRACKED_FUSED_BLACK_BRICK = block(GotModBlocks.CRACKED_FUSED_BLACK_BRICK);
    public static final DeferredItem<Item> MOSSY_FUSED_BLACK_BRICK = block(GotModBlocks.MOSSY_FUSED_BLACK_BRICK);
    public static final DeferredItem<Item> FUSED_BLACK_COBBLESTONE = block(GotModBlocks.FUSED_BLACK_COBBLESTONE);
    public static final DeferredItem<Item> MOSSY_FUSED_BLACK_COBBLESTONE = block(GotModBlocks.MOSSY_FUSED_BLACK_COBBLESTONE);
    public static final DeferredItem<Item> SMOOTH_FUSED_BLACK_ROCK = block(GotModBlocks.SMOOTH_FUSED_BLACK_ROCK);
    public static final DeferredItem<Item> FUSED_BLACK_PILLAR = block(GotModBlocks.FUSED_BLACK_PILLAR);
    public static final DeferredItem<Item> FUSED_BLACK_ROCK_SLAB = block(GotModBlocks.FUSED_BLACK_ROCK_SLAB);
    public static final DeferredItem<Item> FUSED_BLACK_ROCK_STAIRS = block(GotModBlocks.FUSED_BLACK_ROCK_STAIRS);
    public static final DeferredItem<Item> FUSED_BLACK_ROCK_WALL = block(GotModBlocks.FUSED_BLACK_ROCK_WALL);
    public static final DeferredItem<Item> FUSED_BLACK_ROCK_BUTTON = block(GotModBlocks.FUSED_BLACK_ROCK_BUTTON);
    public static final DeferredItem<Item> FUSED_BLACK_ROCK_PRESSURE_PLATE = block(GotModBlocks.FUSED_BLACK_ROCK_PRESSURE_PLATE);
    public static final DeferredItem<Item> FUSED_BLACK_BRICK_SLAB = block(GotModBlocks.FUSED_BLACK_BRICK_SLAB);
    public static final DeferredItem<Item> FUSED_BLACK_BRICK_STAIRS = block(GotModBlocks.FUSED_BLACK_BRICK_STAIRS);
    public static final DeferredItem<Item> FUSED_BLACK_BRICK_WALL = block(GotModBlocks.FUSED_BLACK_BRICK_WALL);
    public static final DeferredItem<Item> CRACKED_FUSED_BLACK_BRICK_SLAB = block(GotModBlocks.CRACKED_FUSED_BLACK_BRICK_SLAB);
    public static final DeferredItem<Item> CRACKED_FUSED_BLACK_BRICK_STAIRS = block(GotModBlocks.CRACKED_FUSED_BLACK_BRICK_STAIRS);
    public static final DeferredItem<Item> CRACKED_FUSED_BLACK_BRICK_WALL = block(GotModBlocks.CRACKED_FUSED_BLACK_BRICK_WALL);
    public static final DeferredItem<Item> MOSSY_FUSED_BLACK_BRICK_SLAB = block(GotModBlocks.MOSSY_FUSED_BLACK_BRICK_SLAB);
    public static final DeferredItem<Item> MOSSY_FUSED_BLACK_BRICK_STAIRS = block(GotModBlocks.MOSSY_FUSED_BLACK_BRICK_STAIRS);
    public static final DeferredItem<Item> MOSSY_FUSED_BLACK_BRICK_WALL = block(GotModBlocks.MOSSY_FUSED_BLACK_BRICK_WALL);
    public static final DeferredItem<Item> FUSED_BLACK_COBBLESTONE_SLAB = block(GotModBlocks.FUSED_BLACK_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> FUSED_BLACK_COBBLESTONE_STAIRS = block(GotModBlocks.FUSED_BLACK_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> FUSED_BLACK_COBBLESTONE_WALL = block(GotModBlocks.FUSED_BLACK_COBBLESTONE_WALL);
    public static final DeferredItem<Item> MOSSY_FUSED_BLACK_COBBLESTONE_SLAB = block(GotModBlocks.MOSSY_FUSED_BLACK_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> MOSSY_FUSED_BLACK_COBBLESTONE_STAIRS = block(GotModBlocks.MOSSY_FUSED_BLACK_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> MOSSY_FUSED_BLACK_COBBLESTONE_WALL = block(GotModBlocks.MOSSY_FUSED_BLACK_COBBLESTONE_WALL);
    public static final DeferredItem<Item> SMOOTH_FUSED_BLACK_ROCK_SLAB = block(GotModBlocks.SMOOTH_FUSED_BLACK_ROCK_SLAB);
    public static final DeferredItem<Item> SMOOTH_FUSED_BLACK_ROCK_STAIRS = block(GotModBlocks.SMOOTH_FUSED_BLACK_ROCK_STAIRS);
    public static final DeferredItem<Item> SMOOTH_FUSED_BLACK_ROCK_WALL = block(GotModBlocks.SMOOTH_FUSED_BLACK_ROCK_WALL);

    // ── Marble items ──────────────────────────────────────────────────────
    public static final DeferredItem<Item> MARBLE_ROCK = block(GotModBlocks.MARBLE_ROCK);
    public static final DeferredItem<Item> MARBLE_BRICK = block(GotModBlocks.MARBLE_BRICK);
    public static final DeferredItem<Item> CRACKED_MARBLE_BRICK = block(GotModBlocks.CRACKED_MARBLE_BRICK);
    public static final DeferredItem<Item> MOSSY_MARBLE_BRICK = block(GotModBlocks.MOSSY_MARBLE_BRICK);
    public static final DeferredItem<Item> MARBLE_COBBLESTONE = block(GotModBlocks.MARBLE_COBBLESTONE);
    public static final DeferredItem<Item> MOSSY_MARBLE_COBBLESTONE = block(GotModBlocks.MOSSY_MARBLE_COBBLESTONE);
    public static final DeferredItem<Item> SMOOTH_MARBLE_ROCK = block(GotModBlocks.SMOOTH_MARBLE_ROCK);
    public static final DeferredItem<Item> MARBLE_PILLAR = block(GotModBlocks.MARBLE_PILLAR);
    public static final DeferredItem<Item> MARBLE_ROCK_SLAB = block(GotModBlocks.MARBLE_ROCK_SLAB);
    public static final DeferredItem<Item> MARBLE_ROCK_STAIRS = block(GotModBlocks.MARBLE_ROCK_STAIRS);
    public static final DeferredItem<Item> MARBLE_ROCK_WALL = block(GotModBlocks.MARBLE_ROCK_WALL);
    public static final DeferredItem<Item> MARBLE_ROCK_BUTTON = block(GotModBlocks.MARBLE_ROCK_BUTTON);
    public static final DeferredItem<Item> MARBLE_ROCK_PRESSURE_PLATE = block(GotModBlocks.MARBLE_ROCK_PRESSURE_PLATE);
    public static final DeferredItem<Item> MARBLE_BRICK_SLAB = block(GotModBlocks.MARBLE_BRICK_SLAB);
    public static final DeferredItem<Item> MARBLE_BRICK_STAIRS = block(GotModBlocks.MARBLE_BRICK_STAIRS);
    public static final DeferredItem<Item> MARBLE_BRICK_WALL = block(GotModBlocks.MARBLE_BRICK_WALL);
    public static final DeferredItem<Item> CRACKED_MARBLE_BRICK_SLAB = block(GotModBlocks.CRACKED_MARBLE_BRICK_SLAB);
    public static final DeferredItem<Item> CRACKED_MARBLE_BRICK_STAIRS = block(GotModBlocks.CRACKED_MARBLE_BRICK_STAIRS);
    public static final DeferredItem<Item> CRACKED_MARBLE_BRICK_WALL = block(GotModBlocks.CRACKED_MARBLE_BRICK_WALL);
    public static final DeferredItem<Item> MOSSY_MARBLE_BRICK_SLAB = block(GotModBlocks.MOSSY_MARBLE_BRICK_SLAB);
    public static final DeferredItem<Item> MOSSY_MARBLE_BRICK_STAIRS = block(GotModBlocks.MOSSY_MARBLE_BRICK_STAIRS);
    public static final DeferredItem<Item> MOSSY_MARBLE_BRICK_WALL = block(GotModBlocks.MOSSY_MARBLE_BRICK_WALL);
    public static final DeferredItem<Item> MARBLE_COBBLESTONE_SLAB = block(GotModBlocks.MARBLE_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> MARBLE_COBBLESTONE_STAIRS = block(GotModBlocks.MARBLE_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> MARBLE_COBBLESTONE_WALL = block(GotModBlocks.MARBLE_COBBLESTONE_WALL);
    public static final DeferredItem<Item> MOSSY_MARBLE_COBBLESTONE_SLAB = block(GotModBlocks.MOSSY_MARBLE_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> MOSSY_MARBLE_COBBLESTONE_STAIRS = block(GotModBlocks.MOSSY_MARBLE_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> MOSSY_MARBLE_COBBLESTONE_WALL = block(GotModBlocks.MOSSY_MARBLE_COBBLESTONE_WALL);
    public static final DeferredItem<Item> SMOOTH_MARBLE_ROCK_SLAB = block(GotModBlocks.SMOOTH_MARBLE_ROCK_SLAB);
    public static final DeferredItem<Item> SMOOTH_MARBLE_ROCK_STAIRS = block(GotModBlocks.SMOOTH_MARBLE_ROCK_STAIRS);
    public static final DeferredItem<Item> SMOOTH_MARBLE_ROCK_WALL = block(GotModBlocks.SMOOTH_MARBLE_ROCK_WALL);

    // ── Ore block items ───────────────────────────────────────────────────
    public static final DeferredItem<Item> AMBER_ORE          = block(GotModBlocks.AMBER_ORE);
    public static final DeferredItem<Item> AMETHYST_ORE       = block(GotModBlocks.AMETHYST_ORE);
    public static final DeferredItem<Item> COPPER_ORE         = block(GotModBlocks.COPPER_ORE);
    public static final DeferredItem<Item> DRAGONGLASS_ORE    = block(GotModBlocks.DRAGONGLASS_ORE);
    public static final DeferredItem<Item> OPAL_ORE           = block(GotModBlocks.OPAL_ORE);
    public static final DeferredItem<Item> RUBY_ORE           = block(GotModBlocks.RUBY_ORE);
    public static final DeferredItem<Item> SAPPHIRE_ORE       = block(GotModBlocks.SAPPHIRE_ORE);
    public static final DeferredItem<Item> SILVER_ORE         = block(GotModBlocks.SILVER_ORE);
    public static final DeferredItem<Item> TIN_ORE            = block(GotModBlocks.TIN_ORE);
    public static final DeferredItem<Item> TOPAZ_ORE          = block(GotModBlocks.TOPAZ_ORE);
    public static final DeferredItem<Item> VALYRIAN_STEEL_ORE = block(GotModBlocks.VALYRIAN_STEEL_ORE) /* valyrian_ore */;

    // ── Gemstones ─────────────────────────────────────────────────────────
    public static final DeferredItem<Item> AMBER           = simple("amber");
    public static final DeferredItem<Item> AMETHYST        = simple("amethyst");
    public static final DeferredItem<Item> DRAGONGLASS_SHARD = simple("dragonglass_shard");
    public static final DeferredItem<Item> OPAL            = simple("opal");
    public static final DeferredItem<Item> RUBY            = simple("ruby");
    public static final DeferredItem<Item> SAPPHIRE        = simple("sapphire");
    public static final DeferredItem<Item> TOPAZ           = simple("topaz");

    // ── Raw ores ──────────────────────────────────────────────────────────
    public static final DeferredItem<Item> RAW_COPPER          = simple("raw_copper");
    public static final DeferredItem<Item> RAW_SILVER          = simple("raw_silver");
    public static final DeferredItem<Item> RAW_TIN             = simple("raw_tin");
    public static final DeferredItem<Item> RAW_VALYRIAN_STEEL  = simple("raw_valyrian_steel");

    // ── Ingots ────────────────────────────────────────────────────────────
    public static final DeferredItem<Item> COPPER_INGOT         = simple("copper_ingot");
    public static final DeferredItem<Item> SILVER_INGOT         = simple("silver_ingot");
    public static final DeferredItem<Item> TIN_INGOT            = simple("tin_ingot");
    public static final DeferredItem<Item> BRONZE_INGOT         = simple("bronze_ingot");
    public static final DeferredItem<Item> VALYRIAN_STEEL_INGOT = simple("valyrian_steel_ingot");



    // ── Stripped Logs ────────────────────────────────────────────────────
    public static final DeferredItem<Item> STRIPPED_WEIRWOOD_LOG = block(GotModBlocks.STRIPPED_WEIRWOOD_LOG);
    public static final DeferredItem<Item> STRIPPED_ASPEN_LOG = block(GotModBlocks.STRIPPED_ASPEN_LOG);
    public static final DeferredItem<Item> STRIPPED_ALDER_LOG = block(GotModBlocks.STRIPPED_ALDER_LOG);
    public static final DeferredItem<Item> STRIPPED_PINE_LOG = block(GotModBlocks.STRIPPED_PINE_LOG);
    public static final DeferredItem<Item> STRIPPED_FIR_LOG = block(GotModBlocks.STRIPPED_FIR_LOG);
    public static final DeferredItem<Item> STRIPPED_SENTINAL_LOG = block(GotModBlocks.STRIPPED_SENTINAL_LOG);
    public static final DeferredItem<Item> STRIPPED_IRONWOOD_LOG = block(GotModBlocks.STRIPPED_IRONWOOD_LOG);
    public static final DeferredItem<Item> STRIPPED_BEECH_LOG = block(GotModBlocks.STRIPPED_BEECH_LOG);
    public static final DeferredItem<Item> STRIPPED_SOLDIER_PINE_LOG = block(GotModBlocks.STRIPPED_SOLDIER_PINE_LOG);
    public static final DeferredItem<Item> STRIPPED_ASH_LOG = block(GotModBlocks.STRIPPED_ASH_LOG);
    public static final DeferredItem<Item> STRIPPED_HAWTHORN_LOG = block(GotModBlocks.STRIPPED_HAWTHORN_LOG);
    public static final DeferredItem<Item> STRIPPED_BLACKBARK_LOG = block(GotModBlocks.STRIPPED_BLACKBARK_LOG);
    public static final DeferredItem<Item> STRIPPED_BLOODWOOD_LOG = block(GotModBlocks.STRIPPED_BLOODWOOD_LOG);
    public static final DeferredItem<Item> STRIPPED_BLUE_MAHOE_LOG = block(GotModBlocks.STRIPPED_BLUE_MAHOE_LOG);
    public static final DeferredItem<Item> STRIPPED_COTTONWOOD_LOG = block(GotModBlocks.STRIPPED_COTTONWOOD_LOG);
    public static final DeferredItem<Item> STRIPPED_BLACK_COTTONWOOD_LOG = block(GotModBlocks.STRIPPED_BLACK_COTTONWOOD_LOG);
    public static final DeferredItem<Item> STRIPPED_CINNAMON_LOG = block(GotModBlocks.STRIPPED_CINNAMON_LOG);
    public static final DeferredItem<Item> STRIPPED_CLOVE_LOG = block(GotModBlocks.STRIPPED_CLOVE_LOG);
    public static final DeferredItem<Item> STRIPPED_EBONY_LOG = block(GotModBlocks.STRIPPED_EBONY_LOG);
    public static final DeferredItem<Item> STRIPPED_ELM_LOG = block(GotModBlocks.STRIPPED_ELM_LOG);
    public static final DeferredItem<Item> STRIPPED_CEDAR_LOG = block(GotModBlocks.STRIPPED_CEDAR_LOG);
    public static final DeferredItem<Item> STRIPPED_APPLE_LOG = block(GotModBlocks.STRIPPED_APPLE_LOG);
    public static final DeferredItem<Item> STRIPPED_GOLDENHEART_LOG = block(GotModBlocks.STRIPPED_GOLDENHEART_LOG);
    public static final DeferredItem<Item> STRIPPED_LINDEN_LOG = block(GotModBlocks.STRIPPED_LINDEN_LOG);
    public static final DeferredItem<Item> STRIPPED_MAHOGANY_LOG = block(GotModBlocks.STRIPPED_MAHOGANY_LOG);
    public static final DeferredItem<Item> STRIPPED_MAPLE_LOG = block(GotModBlocks.STRIPPED_MAPLE_LOG);
    public static final DeferredItem<Item> STRIPPED_MYRRH_LOG = block(GotModBlocks.STRIPPED_MYRRH_LOG);
    public static final DeferredItem<Item> STRIPPED_REDWOOD_LOG = block(GotModBlocks.STRIPPED_REDWOOD_LOG);
    public static final DeferredItem<Item> STRIPPED_CHESTNUT_LOG = block(GotModBlocks.STRIPPED_CHESTNUT_LOG);
    public static final DeferredItem<Item> STRIPPED_WILLOW_LOG = block(GotModBlocks.STRIPPED_WILLOW_LOG);
    public static final DeferredItem<Item> STRIPPED_WORMTREE_LOG = block(GotModBlocks.STRIPPED_WORMTREE_LOG);

    // ── Stripped Woods ────────────────────────────────────────────────────
    public static final DeferredItem<Item> STRIPPED_WEIRWOOD_WOOD         = block(GotModBlocks.STRIPPED_WEIRWOOD_WOOD);
    public static final DeferredItem<Item> STRIPPED_ASPEN_WOOD            = block(GotModBlocks.STRIPPED_ASPEN_WOOD);
    public static final DeferredItem<Item> STRIPPED_ALDER_WOOD            = block(GotModBlocks.STRIPPED_ALDER_WOOD);
    public static final DeferredItem<Item> STRIPPED_PINE_WOOD             = block(GotModBlocks.STRIPPED_PINE_WOOD);
    public static final DeferredItem<Item> STRIPPED_FIR_WOOD              = block(GotModBlocks.STRIPPED_FIR_WOOD);
    public static final DeferredItem<Item> STRIPPED_SENTINAL_WOOD         = block(GotModBlocks.STRIPPED_SENTINAL_WOOD);
    public static final DeferredItem<Item> STRIPPED_IRONWOOD_WOOD         = block(GotModBlocks.STRIPPED_IRONWOOD_WOOD);
    public static final DeferredItem<Item> STRIPPED_BEECH_WOOD            = block(GotModBlocks.STRIPPED_BEECH_WOOD);
    public static final DeferredItem<Item> STRIPPED_SOLDIER_PINE_WOOD     = block(GotModBlocks.STRIPPED_SOLDIER_PINE_WOOD);
    public static final DeferredItem<Item> STRIPPED_ASH_WOOD              = block(GotModBlocks.STRIPPED_ASH_WOOD);
    public static final DeferredItem<Item> STRIPPED_HAWTHORN_WOOD         = block(GotModBlocks.STRIPPED_HAWTHORN_WOOD);
    public static final DeferredItem<Item> STRIPPED_BLACKBARK_WOOD        = block(GotModBlocks.STRIPPED_BLACKBARK_WOOD);
    public static final DeferredItem<Item> STRIPPED_BLOODWOOD_WOOD        = block(GotModBlocks.STRIPPED_BLOODWOOD_WOOD);
    public static final DeferredItem<Item> STRIPPED_BLUE_MAHOE_WOOD       = block(GotModBlocks.STRIPPED_BLUE_MAHOE_WOOD);
    public static final DeferredItem<Item> STRIPPED_COTTONWOOD_WOOD       = block(GotModBlocks.STRIPPED_COTTONWOOD_WOOD);
    public static final DeferredItem<Item> STRIPPED_BLACK_COTTONWOOD_WOOD = block(GotModBlocks.STRIPPED_BLACK_COTTONWOOD_WOOD);
    public static final DeferredItem<Item> STRIPPED_CINNAMON_WOOD         = block(GotModBlocks.STRIPPED_CINNAMON_WOOD);
    public static final DeferredItem<Item> STRIPPED_CLOVE_WOOD            = block(GotModBlocks.STRIPPED_CLOVE_WOOD);
    public static final DeferredItem<Item> STRIPPED_EBONY_WOOD            = block(GotModBlocks.STRIPPED_EBONY_WOOD);
    public static final DeferredItem<Item> STRIPPED_ELM_WOOD              = block(GotModBlocks.STRIPPED_ELM_WOOD);
    public static final DeferredItem<Item> STRIPPED_CEDAR_WOOD            = block(GotModBlocks.STRIPPED_CEDAR_WOOD);
    public static final DeferredItem<Item> STRIPPED_APPLE_WOOD            = block(GotModBlocks.STRIPPED_APPLE_WOOD);
    public static final DeferredItem<Item> STRIPPED_GOLDENHEART_WOOD      = block(GotModBlocks.STRIPPED_GOLDENHEART_WOOD);
    public static final DeferredItem<Item> STRIPPED_LINDEN_WOOD           = block(GotModBlocks.STRIPPED_LINDEN_WOOD);
    public static final DeferredItem<Item> STRIPPED_MAHOGANY_WOOD         = block(GotModBlocks.STRIPPED_MAHOGANY_WOOD);
    public static final DeferredItem<Item> STRIPPED_MAPLE_WOOD            = block(GotModBlocks.STRIPPED_MAPLE_WOOD);
    public static final DeferredItem<Item> STRIPPED_MYRRH_WOOD            = block(GotModBlocks.STRIPPED_MYRRH_WOOD);
    public static final DeferredItem<Item> STRIPPED_REDWOOD_WOOD          = block(GotModBlocks.STRIPPED_REDWOOD_WOOD);
    public static final DeferredItem<Item> STRIPPED_CHESTNUT_WOOD         = block(GotModBlocks.STRIPPED_CHESTNUT_WOOD);
    public static final DeferredItem<Item> STRIPPED_WILLOW_WOOD           = block(GotModBlocks.STRIPPED_WILLOW_WOOD);
    public static final DeferredItem<Item> STRIPPED_WORMTREE_WOOD         = block(GotModBlocks.STRIPPED_WORMTREE_WOOD);

    // ── Doors (DoubleHighBlockItem so they place correctly) ───────────────
    public static final DeferredItem<Item> WEIRWOOD_DOOR             = door(GotModBlocks.WEIRWOOD_DOOR);
    public static final DeferredItem<Item> ASPEN_DOOR                = door(GotModBlocks.ASPEN_DOOR);
    public static final DeferredItem<Item> ALDER_DOOR                = door(GotModBlocks.ALDER_DOOR);
    public static final DeferredItem<Item> PINE_DOOR                 = door(GotModBlocks.PINE_DOOR);
    public static final DeferredItem<Item> FIR_DOOR                  = door(GotModBlocks.FIR_DOOR);
    public static final DeferredItem<Item> SENTINAL_DOOR             = door(GotModBlocks.SENTINAL_DOOR);
    public static final DeferredItem<Item> IRONWOOD_DOOR             = door(GotModBlocks.IRONWOOD_DOOR);
    public static final DeferredItem<Item> BEECH_DOOR                = door(GotModBlocks.BEECH_DOOR);
    public static final DeferredItem<Item> SOLDIER_PINE_DOOR         = door(GotModBlocks.SOLDIER_PINE_DOOR);
    public static final DeferredItem<Item> ASH_DOOR                  = door(GotModBlocks.ASH_DOOR);
    public static final DeferredItem<Item> HAWTHORN_DOOR             = door(GotModBlocks.HAWTHORN_DOOR);
    public static final DeferredItem<Item> BLACKBARK_DOOR            = door(GotModBlocks.BLACKBARK_DOOR);
    public static final DeferredItem<Item> BLOODWOOD_DOOR            = door(GotModBlocks.BLOODWOOD_DOOR);
    public static final DeferredItem<Item> BLUE_MAHOE_DOOR           = door(GotModBlocks.BLUE_MAHOE_DOOR);
    public static final DeferredItem<Item> COTTONWOOD_DOOR           = door(GotModBlocks.COTTONWOOD_DOOR);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_DOOR     = door(GotModBlocks.BLACK_COTTONWOOD_DOOR);
    public static final DeferredItem<Item> CINNAMON_DOOR             = door(GotModBlocks.CINNAMON_DOOR);
    public static final DeferredItem<Item> CLOVE_DOOR                = door(GotModBlocks.CLOVE_DOOR);
    public static final DeferredItem<Item> EBONY_DOOR                = door(GotModBlocks.EBONY_DOOR);
    public static final DeferredItem<Item> ELM_DOOR                  = door(GotModBlocks.ELM_DOOR);
    public static final DeferredItem<Item> CEDAR_DOOR                = door(GotModBlocks.CEDAR_DOOR);
    public static final DeferredItem<Item> APPLE_DOOR                = door(GotModBlocks.APPLE_DOOR);
    public static final DeferredItem<Item> GOLDENHEART_DOOR          = door(GotModBlocks.GOLDENHEART_DOOR);
    public static final DeferredItem<Item> LINDEN_DOOR               = door(GotModBlocks.LINDEN_DOOR);
    public static final DeferredItem<Item> MAHOGANY_DOOR             = door(GotModBlocks.MAHOGANY_DOOR);
    public static final DeferredItem<Item> MAPLE_DOOR                = door(GotModBlocks.MAPLE_DOOR);
    public static final DeferredItem<Item> MYRRH_DOOR                = door(GotModBlocks.MYRRH_DOOR);
    public static final DeferredItem<Item> REDWOOD_DOOR              = door(GotModBlocks.REDWOOD_DOOR);
    public static final DeferredItem<Item> CHESTNUT_DOOR             = door(GotModBlocks.CHESTNUT_DOOR);
    public static final DeferredItem<Item> WILLOW_DOOR               = door(GotModBlocks.WILLOW_DOOR);
    public static final DeferredItem<Item> WORMTREE_DOOR             = door(GotModBlocks.WORMTREE_DOOR);

    // ── Trapdoors ─────────────────────────────────────────────────────────
    public static final DeferredItem<Item> WEIRWOOD_TRAPDOOR         = block(GotModBlocks.WEIRWOOD_TRAPDOOR);
    public static final DeferredItem<Item> ASPEN_TRAPDOOR            = block(GotModBlocks.ASPEN_TRAPDOOR);
    public static final DeferredItem<Item> ALDER_TRAPDOOR            = block(GotModBlocks.ALDER_TRAPDOOR);
    public static final DeferredItem<Item> PINE_TRAPDOOR             = block(GotModBlocks.PINE_TRAPDOOR);
    public static final DeferredItem<Item> FIR_TRAPDOOR              = block(GotModBlocks.FIR_TRAPDOOR);
    public static final DeferredItem<Item> SENTINAL_TRAPDOOR         = block(GotModBlocks.SENTINAL_TRAPDOOR);
    public static final DeferredItem<Item> IRONWOOD_TRAPDOOR         = block(GotModBlocks.IRONWOOD_TRAPDOOR);
    public static final DeferredItem<Item> BEECH_TRAPDOOR            = block(GotModBlocks.BEECH_TRAPDOOR);
    public static final DeferredItem<Item> SOLDIER_PINE_TRAPDOOR     = block(GotModBlocks.SOLDIER_PINE_TRAPDOOR);
    public static final DeferredItem<Item> ASH_TRAPDOOR              = block(GotModBlocks.ASH_TRAPDOOR);
    public static final DeferredItem<Item> HAWTHORN_TRAPDOOR         = block(GotModBlocks.HAWTHORN_TRAPDOOR);
    public static final DeferredItem<Item> BLACKBARK_TRAPDOOR        = block(GotModBlocks.BLACKBARK_TRAPDOOR);
    public static final DeferredItem<Item> BLOODWOOD_TRAPDOOR        = block(GotModBlocks.BLOODWOOD_TRAPDOOR);
    public static final DeferredItem<Item> BLUE_MAHOE_TRAPDOOR       = block(GotModBlocks.BLUE_MAHOE_TRAPDOOR);
    public static final DeferredItem<Item> COTTONWOOD_TRAPDOOR       = block(GotModBlocks.COTTONWOOD_TRAPDOOR);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_TRAPDOOR = block(GotModBlocks.BLACK_COTTONWOOD_TRAPDOOR);
    public static final DeferredItem<Item> CINNAMON_TRAPDOOR         = block(GotModBlocks.CINNAMON_TRAPDOOR);
    public static final DeferredItem<Item> CLOVE_TRAPDOOR            = block(GotModBlocks.CLOVE_TRAPDOOR);
    public static final DeferredItem<Item> EBONY_TRAPDOOR            = block(GotModBlocks.EBONY_TRAPDOOR);
    public static final DeferredItem<Item> ELM_TRAPDOOR              = block(GotModBlocks.ELM_TRAPDOOR);
    public static final DeferredItem<Item> CEDAR_TRAPDOOR            = block(GotModBlocks.CEDAR_TRAPDOOR);
    public static final DeferredItem<Item> APPLE_TRAPDOOR            = block(GotModBlocks.APPLE_TRAPDOOR);
    public static final DeferredItem<Item> GOLDENHEART_TRAPDOOR      = block(GotModBlocks.GOLDENHEART_TRAPDOOR);
    public static final DeferredItem<Item> LINDEN_TRAPDOOR           = block(GotModBlocks.LINDEN_TRAPDOOR);
    public static final DeferredItem<Item> MAHOGANY_TRAPDOOR         = block(GotModBlocks.MAHOGANY_TRAPDOOR);
    public static final DeferredItem<Item> MAPLE_TRAPDOOR            = block(GotModBlocks.MAPLE_TRAPDOOR);
    public static final DeferredItem<Item> MYRRH_TRAPDOOR            = block(GotModBlocks.MYRRH_TRAPDOOR);
    public static final DeferredItem<Item> REDWOOD_TRAPDOOR          = block(GotModBlocks.REDWOOD_TRAPDOOR);
    public static final DeferredItem<Item> CHESTNUT_TRAPDOOR         = block(GotModBlocks.CHESTNUT_TRAPDOOR);
    public static final DeferredItem<Item> WILLOW_TRAPDOOR           = block(GotModBlocks.WILLOW_TRAPDOOR);
    public static final DeferredItem<Item> WORMTREE_TRAPDOOR         = block(GotModBlocks.WORMTREE_TRAPDOOR);

    // ── Branches (log-textured wall blocks) ──────────────────────────────
    public static final DeferredItem<Item> WEIRWOOD_BRANCH         = block(GotModBlocks.WEIRWOOD_BRANCH);
    public static final DeferredItem<Item> ASPEN_BRANCH            = block(GotModBlocks.ASPEN_BRANCH);
    public static final DeferredItem<Item> ALDER_BRANCH            = block(GotModBlocks.ALDER_BRANCH);
    public static final DeferredItem<Item> PINE_BRANCH             = block(GotModBlocks.PINE_BRANCH);
    public static final DeferredItem<Item> FIR_BRANCH              = block(GotModBlocks.FIR_BRANCH);
    public static final DeferredItem<Item> SENTINAL_BRANCH         = block(GotModBlocks.SENTINAL_BRANCH);
    public static final DeferredItem<Item> IRONWOOD_BRANCH         = block(GotModBlocks.IRONWOOD_BRANCH);
    public static final DeferredItem<Item> BEECH_BRANCH            = block(GotModBlocks.BEECH_BRANCH);
    public static final DeferredItem<Item> SOLDIER_PINE_BRANCH     = block(GotModBlocks.SOLDIER_PINE_BRANCH);
    public static final DeferredItem<Item> ASH_BRANCH              = block(GotModBlocks.ASH_BRANCH);
    public static final DeferredItem<Item> HAWTHORN_BRANCH         = block(GotModBlocks.HAWTHORN_BRANCH);
    public static final DeferredItem<Item> BLACKBARK_BRANCH        = block(GotModBlocks.BLACKBARK_BRANCH);
    public static final DeferredItem<Item> BLOODWOOD_BRANCH        = block(GotModBlocks.BLOODWOOD_BRANCH);
    public static final DeferredItem<Item> BLUE_MAHOE_BRANCH       = block(GotModBlocks.BLUE_MAHOE_BRANCH);
    public static final DeferredItem<Item> COTTONWOOD_BRANCH       = block(GotModBlocks.COTTONWOOD_BRANCH);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_BRANCH = block(GotModBlocks.BLACK_COTTONWOOD_BRANCH);
    public static final DeferredItem<Item> CINNAMON_BRANCH         = block(GotModBlocks.CINNAMON_BRANCH);
    public static final DeferredItem<Item> CLOVE_BRANCH            = block(GotModBlocks.CLOVE_BRANCH);
    public static final DeferredItem<Item> EBONY_BRANCH            = block(GotModBlocks.EBONY_BRANCH);
    public static final DeferredItem<Item> ELM_BRANCH              = block(GotModBlocks.ELM_BRANCH);
    public static final DeferredItem<Item> CEDAR_BRANCH            = block(GotModBlocks.CEDAR_BRANCH);
    public static final DeferredItem<Item> APPLE_BRANCH            = block(GotModBlocks.APPLE_BRANCH);
    public static final DeferredItem<Item> GOLDENHEART_BRANCH      = block(GotModBlocks.GOLDENHEART_BRANCH);
    public static final DeferredItem<Item> LINDEN_BRANCH           = block(GotModBlocks.LINDEN_BRANCH);
    public static final DeferredItem<Item> MAHOGANY_BRANCH         = block(GotModBlocks.MAHOGANY_BRANCH);
    public static final DeferredItem<Item> MAPLE_BRANCH            = block(GotModBlocks.MAPLE_BRANCH);
    public static final DeferredItem<Item> MYRRH_BRANCH            = block(GotModBlocks.MYRRH_BRANCH);
    public static final DeferredItem<Item> REDWOOD_BRANCH          = block(GotModBlocks.REDWOOD_BRANCH);
    public static final DeferredItem<Item> CHESTNUT_BRANCH         = block(GotModBlocks.CHESTNUT_BRANCH);
    public static final DeferredItem<Item> WILLOW_BRANCH           = block(GotModBlocks.WILLOW_BRANCH);
    public static final DeferredItem<Item> WORMTREE_BRANCH         = block(GotModBlocks.WORMTREE_BRANCH);

    // ── Signs ─────────────────────────────────────────────────────────────
    public static final DeferredItem<Item> WEIRWOOD_SIGN         = REGISTRY.registerItem("weirwood_sign",         p -> new SignItem(p, GotModBlocks.WEIRWOOD_SIGN.get(), GotModBlocks.WEIRWOOD_WALL_SIGN.get()));
    public static final DeferredItem<Item> WEIRWOOD_HANGING_SIGN = REGISTRY.registerItem("weirwood_hanging_sign", p -> new HangingSignItem(p, GotModBlocks.WEIRWOOD_HANGING_SIGN.get(), GotModBlocks.WEIRWOOD_WALL_HANGING_SIGN.get()));
    public static final DeferredItem<Item> ASPEN_SIGN         = REGISTRY.registerItem("aspen_sign",         p -> new SignItem(p, GotModBlocks.ASPEN_SIGN.get(), GotModBlocks.ASPEN_WALL_SIGN.get()));
    public static final DeferredItem<Item> ASPEN_HANGING_SIGN = REGISTRY.registerItem("aspen_hanging_sign", p -> new HangingSignItem(p, GotModBlocks.ASPEN_HANGING_SIGN.get(), GotModBlocks.ASPEN_WALL_HANGING_SIGN.get()));
    public static final DeferredItem<Item> ALDER_SIGN         = REGISTRY.registerItem("alder_sign",         p -> new SignItem(p, GotModBlocks.ALDER_SIGN.get(), GotModBlocks.ALDER_WALL_SIGN.get()));
    public static final DeferredItem<Item> ALDER_HANGING_SIGN = REGISTRY.registerItem("alder_hanging_sign", p -> new HangingSignItem(p, GotModBlocks.ALDER_HANGING_SIGN.get(), GotModBlocks.ALDER_WALL_HANGING_SIGN.get()));
    public static final DeferredItem<Item> PINE_SIGN         = REGISTRY.registerItem("pine_sign",         p -> new SignItem(p, GotModBlocks.PINE_SIGN.get(), GotModBlocks.PINE_WALL_SIGN.get()));
    public static final DeferredItem<Item> PINE_HANGING_SIGN = REGISTRY.registerItem("pine_hanging_sign", p -> new HangingSignItem(p, GotModBlocks.PINE_HANGING_SIGN.get(), GotModBlocks.PINE_WALL_HANGING_SIGN.get()));
    public static final DeferredItem<Item> FIR_SIGN         = REGISTRY.registerItem("fir_sign",         p -> new SignItem(p, GotModBlocks.FIR_SIGN.get(), GotModBlocks.FIR_WALL_SIGN.get()));
    public static final DeferredItem<Item> FIR_HANGING_SIGN = REGISTRY.registerItem("fir_hanging_sign", p -> new HangingSignItem(p, GotModBlocks.FIR_HANGING_SIGN.get(), GotModBlocks.FIR_WALL_HANGING_SIGN.get()));
    public static final DeferredItem<Item> SENTINAL_SIGN         = REGISTRY.registerItem("sentinal_sign",         p -> new SignItem(p, GotModBlocks.SENTINAL_SIGN.get(), GotModBlocks.SENTINAL_WALL_SIGN.get()));
    public static final DeferredItem<Item> SENTINAL_HANGING_SIGN = REGISTRY.registerItem("sentinal_hanging_sign", p -> new HangingSignItem(p, GotModBlocks.SENTINAL_HANGING_SIGN.get(), GotModBlocks.SENTINAL_WALL_HANGING_SIGN.get()));
    public static final DeferredItem<Item> IRONWOOD_SIGN         = REGISTRY.registerItem("ironwood_sign",         p -> new SignItem(p, GotModBlocks.IRONWOOD_SIGN.get(), GotModBlocks.IRONWOOD_WALL_SIGN.get()));
    public static final DeferredItem<Item> IRONWOOD_HANGING_SIGN = REGISTRY.registerItem("ironwood_hanging_sign", p -> new HangingSignItem(p, GotModBlocks.IRONWOOD_HANGING_SIGN.get(), GotModBlocks.IRONWOOD_WALL_HANGING_SIGN.get()));
    public static final DeferredItem<Item> BEECH_SIGN         = REGISTRY.registerItem("beech_sign",         p -> new SignItem(p, GotModBlocks.BEECH_SIGN.get(), GotModBlocks.BEECH_WALL_SIGN.get()));
    public static final DeferredItem<Item> BEECH_HANGING_SIGN = REGISTRY.registerItem("beech_hanging_sign", p -> new HangingSignItem(p, GotModBlocks.BEECH_HANGING_SIGN.get(), GotModBlocks.BEECH_WALL_HANGING_SIGN.get()));
    public static final DeferredItem<Item> SOLDIER_PINE_SIGN         = REGISTRY.registerItem("soldier_pine_sign",         p -> new SignItem(p, GotModBlocks.SOLDIER_PINE_SIGN.get(), GotModBlocks.SOLDIER_PINE_WALL_SIGN.get()));
    public static final DeferredItem<Item> SOLDIER_PINE_HANGING_SIGN = REGISTRY.registerItem("soldier_pine_hanging_sign", p -> new HangingSignItem(p, GotModBlocks.SOLDIER_PINE_HANGING_SIGN.get(), GotModBlocks.SOLDIER_PINE_WALL_HANGING_SIGN.get()));
    public static final DeferredItem<Item> ASH_SIGN         = REGISTRY.registerItem("ash_sign",         p -> new SignItem(p, GotModBlocks.ASH_SIGN.get(), GotModBlocks.ASH_WALL_SIGN.get()));
    public static final DeferredItem<Item> ASH_HANGING_SIGN = REGISTRY.registerItem("ash_hanging_sign", p -> new HangingSignItem(p, GotModBlocks.ASH_HANGING_SIGN.get(), GotModBlocks.ASH_WALL_HANGING_SIGN.get()));
    public static final DeferredItem<Item> HAWTHORN_SIGN         = REGISTRY.registerItem("hawthorn_sign",         p -> new SignItem(p, GotModBlocks.HAWTHORN_SIGN.get(), GotModBlocks.HAWTHORN_WALL_SIGN.get()));
    public static final DeferredItem<Item> HAWTHORN_HANGING_SIGN = REGISTRY.registerItem("hawthorn_hanging_sign", p -> new HangingSignItem(p, GotModBlocks.HAWTHORN_HANGING_SIGN.get(), GotModBlocks.HAWTHORN_WALL_HANGING_SIGN.get()));
    public static final DeferredItem<Item> BLACKBARK_SIGN         = REGISTRY.registerItem("blackbark_sign",         p -> new SignItem(p, GotModBlocks.BLACKBARK_SIGN.get(), GotModBlocks.BLACKBARK_WALL_SIGN.get()));
    public static final DeferredItem<Item> BLACKBARK_HANGING_SIGN = REGISTRY.registerItem("blackbark_hanging_sign", p -> new HangingSignItem(p, GotModBlocks.BLACKBARK_HANGING_SIGN.get(), GotModBlocks.BLACKBARK_WALL_HANGING_SIGN.get()));
    public static final DeferredItem<Item> BLOODWOOD_SIGN         = REGISTRY.registerItem("bloodwood_sign",         p -> new SignItem(p, GotModBlocks.BLOODWOOD_SIGN.get(), GotModBlocks.BLOODWOOD_WALL_SIGN.get()));
    public static final DeferredItem<Item> BLOODWOOD_HANGING_SIGN = REGISTRY.registerItem("bloodwood_hanging_sign", p -> new HangingSignItem(p, GotModBlocks.BLOODWOOD_HANGING_SIGN.get(), GotModBlocks.BLOODWOOD_WALL_HANGING_SIGN.get()));
    public static final DeferredItem<Item> BLUE_MAHOE_SIGN         = REGISTRY.registerItem("blue_mahoe_sign",         p -> new SignItem(p, GotModBlocks.BLUE_MAHOE_SIGN.get(), GotModBlocks.BLUE_MAHOE_WALL_SIGN.get()));
    public static final DeferredItem<Item> BLUE_MAHOE_HANGING_SIGN = REGISTRY.registerItem("blue_mahoe_hanging_sign", p -> new HangingSignItem(p, GotModBlocks.BLUE_MAHOE_HANGING_SIGN.get(), GotModBlocks.BLUE_MAHOE_WALL_HANGING_SIGN.get()));
    public static final DeferredItem<Item> COTTONWOOD_SIGN         = REGISTRY.registerItem("cottonwood_sign",         p -> new SignItem(p, GotModBlocks.COTTONWOOD_SIGN.get(), GotModBlocks.COTTONWOOD_WALL_SIGN.get()));
    public static final DeferredItem<Item> COTTONWOOD_HANGING_SIGN = REGISTRY.registerItem("cottonwood_hanging_sign", p -> new HangingSignItem(p, GotModBlocks.COTTONWOOD_HANGING_SIGN.get(), GotModBlocks.COTTONWOOD_WALL_HANGING_SIGN.get()));
    public static final DeferredItem<Item> BLACK_COTTONWOOD_SIGN         = REGISTRY.registerItem("black_cottonwood_sign",         p -> new SignItem(p, GotModBlocks.BLACK_COTTONWOOD_SIGN.get(), GotModBlocks.BLACK_COTTONWOOD_WALL_SIGN.get()));
    public static final DeferredItem<Item> BLACK_COTTONWOOD_HANGING_SIGN = REGISTRY.registerItem("black_cottonwood_hanging_sign", p -> new HangingSignItem(p, GotModBlocks.BLACK_COTTONWOOD_HANGING_SIGN.get(), GotModBlocks.BLACK_COTTONWOOD_WALL_HANGING_SIGN.get()));
    public static final DeferredItem<Item> CINNAMON_SIGN         = REGISTRY.registerItem("cinnamon_sign",         p -> new SignItem(p, GotModBlocks.CINNAMON_SIGN.get(), GotModBlocks.CINNAMON_WALL_SIGN.get()));
    public static final DeferredItem<Item> CINNAMON_HANGING_SIGN = REGISTRY.registerItem("cinnamon_hanging_sign", p -> new HangingSignItem(p, GotModBlocks.CINNAMON_HANGING_SIGN.get(), GotModBlocks.CINNAMON_WALL_HANGING_SIGN.get()));
    public static final DeferredItem<Item> CLOVE_SIGN         = REGISTRY.registerItem("clove_sign",         p -> new SignItem(p, GotModBlocks.CLOVE_SIGN.get(), GotModBlocks.CLOVE_WALL_SIGN.get()));
    public static final DeferredItem<Item> CLOVE_HANGING_SIGN = REGISTRY.registerItem("clove_hanging_sign", p -> new HangingSignItem(p, GotModBlocks.CLOVE_HANGING_SIGN.get(), GotModBlocks.CLOVE_WALL_HANGING_SIGN.get()));
    public static final DeferredItem<Item> EBONY_SIGN         = REGISTRY.registerItem("ebony_sign",         p -> new SignItem(p, GotModBlocks.EBONY_SIGN.get(), GotModBlocks.EBONY_WALL_SIGN.get()));
    public static final DeferredItem<Item> EBONY_HANGING_SIGN = REGISTRY.registerItem("ebony_hanging_sign", p -> new HangingSignItem(p, GotModBlocks.EBONY_HANGING_SIGN.get(), GotModBlocks.EBONY_WALL_HANGING_SIGN.get()));
    public static final DeferredItem<Item> ELM_SIGN         = REGISTRY.registerItem("elm_sign",         p -> new SignItem(p, GotModBlocks.ELM_SIGN.get(), GotModBlocks.ELM_WALL_SIGN.get()));
    public static final DeferredItem<Item> ELM_HANGING_SIGN = REGISTRY.registerItem("elm_hanging_sign", p -> new HangingSignItem(p, GotModBlocks.ELM_HANGING_SIGN.get(), GotModBlocks.ELM_WALL_HANGING_SIGN.get()));
    public static final DeferredItem<Item> CEDAR_SIGN         = REGISTRY.registerItem("cedar_sign",         p -> new SignItem(p, GotModBlocks.CEDAR_SIGN.get(), GotModBlocks.CEDAR_WALL_SIGN.get()));
    public static final DeferredItem<Item> CEDAR_HANGING_SIGN = REGISTRY.registerItem("cedar_hanging_sign", p -> new HangingSignItem(p, GotModBlocks.CEDAR_HANGING_SIGN.get(), GotModBlocks.CEDAR_WALL_HANGING_SIGN.get()));
    public static final DeferredItem<Item> APPLE_SIGN         = REGISTRY.registerItem("apple_sign",         p -> new SignItem(p, GotModBlocks.APPLE_SIGN.get(), GotModBlocks.APPLE_WALL_SIGN.get()));
    public static final DeferredItem<Item> APPLE_HANGING_SIGN = REGISTRY.registerItem("apple_hanging_sign", p -> new HangingSignItem(p, GotModBlocks.APPLE_HANGING_SIGN.get(), GotModBlocks.APPLE_WALL_HANGING_SIGN.get()));
    public static final DeferredItem<Item> GOLDENHEART_SIGN         = REGISTRY.registerItem("goldenheart_sign",         p -> new SignItem(p, GotModBlocks.GOLDENHEART_SIGN.get(), GotModBlocks.GOLDENHEART_WALL_SIGN.get()));
    public static final DeferredItem<Item> GOLDENHEART_HANGING_SIGN = REGISTRY.registerItem("goldenheart_hanging_sign", p -> new HangingSignItem(p, GotModBlocks.GOLDENHEART_HANGING_SIGN.get(), GotModBlocks.GOLDENHEART_WALL_HANGING_SIGN.get()));
    public static final DeferredItem<Item> LINDEN_SIGN         = REGISTRY.registerItem("linden_sign",         p -> new SignItem(p, GotModBlocks.LINDEN_SIGN.get(), GotModBlocks.LINDEN_WALL_SIGN.get()));
    public static final DeferredItem<Item> LINDEN_HANGING_SIGN = REGISTRY.registerItem("linden_hanging_sign", p -> new HangingSignItem(p, GotModBlocks.LINDEN_HANGING_SIGN.get(), GotModBlocks.LINDEN_WALL_HANGING_SIGN.get()));
    public static final DeferredItem<Item> MAHOGANY_SIGN         = REGISTRY.registerItem("mahogany_sign",         p -> new SignItem(p, GotModBlocks.MAHOGANY_SIGN.get(), GotModBlocks.MAHOGANY_WALL_SIGN.get()));
    public static final DeferredItem<Item> MAHOGANY_HANGING_SIGN = REGISTRY.registerItem("mahogany_hanging_sign", p -> new HangingSignItem(p, GotModBlocks.MAHOGANY_HANGING_SIGN.get(), GotModBlocks.MAHOGANY_WALL_HANGING_SIGN.get()));
    public static final DeferredItem<Item> MAPLE_SIGN         = REGISTRY.registerItem("maple_sign",         p -> new SignItem(p, GotModBlocks.MAPLE_SIGN.get(), GotModBlocks.MAPLE_WALL_SIGN.get()));
    public static final DeferredItem<Item> MAPLE_HANGING_SIGN = REGISTRY.registerItem("maple_hanging_sign", p -> new HangingSignItem(p, GotModBlocks.MAPLE_HANGING_SIGN.get(), GotModBlocks.MAPLE_WALL_HANGING_SIGN.get()));
    public static final DeferredItem<Item> MYRRH_SIGN         = REGISTRY.registerItem("myrrh_sign",         p -> new SignItem(p, GotModBlocks.MYRRH_SIGN.get(), GotModBlocks.MYRRH_WALL_SIGN.get()));
    public static final DeferredItem<Item> MYRRH_HANGING_SIGN = REGISTRY.registerItem("myrrh_hanging_sign", p -> new HangingSignItem(p, GotModBlocks.MYRRH_HANGING_SIGN.get(), GotModBlocks.MYRRH_WALL_HANGING_SIGN.get()));
    public static final DeferredItem<Item> REDWOOD_SIGN         = REGISTRY.registerItem("redwood_sign",         p -> new SignItem(p, GotModBlocks.REDWOOD_SIGN.get(), GotModBlocks.REDWOOD_WALL_SIGN.get()));
    public static final DeferredItem<Item> REDWOOD_HANGING_SIGN = REGISTRY.registerItem("redwood_hanging_sign", p -> new HangingSignItem(p, GotModBlocks.REDWOOD_HANGING_SIGN.get(), GotModBlocks.REDWOOD_WALL_HANGING_SIGN.get()));
    public static final DeferredItem<Item> CHESTNUT_SIGN         = REGISTRY.registerItem("chestnut_sign",         p -> new SignItem(p, GotModBlocks.CHESTNUT_SIGN.get(), GotModBlocks.CHESTNUT_WALL_SIGN.get()));
    public static final DeferredItem<Item> CHESTNUT_HANGING_SIGN = REGISTRY.registerItem("chestnut_hanging_sign", p -> new HangingSignItem(p, GotModBlocks.CHESTNUT_HANGING_SIGN.get(), GotModBlocks.CHESTNUT_WALL_HANGING_SIGN.get()));
    public static final DeferredItem<Item> WILLOW_SIGN         = REGISTRY.registerItem("willow_sign",         p -> new SignItem(p, GotModBlocks.WILLOW_SIGN.get(), GotModBlocks.WILLOW_WALL_SIGN.get()));
    public static final DeferredItem<Item> WILLOW_HANGING_SIGN = REGISTRY.registerItem("willow_hanging_sign", p -> new HangingSignItem(p, GotModBlocks.WILLOW_HANGING_SIGN.get(), GotModBlocks.WILLOW_WALL_HANGING_SIGN.get()));
    public static final DeferredItem<Item> WORMTREE_SIGN         = REGISTRY.registerItem("wormtree_sign",         p -> new SignItem(p, GotModBlocks.WORMTREE_SIGN.get(), GotModBlocks.WORMTREE_WALL_SIGN.get()));
    public static final DeferredItem<Item> WORMTREE_HANGING_SIGN = REGISTRY.registerItem("wormtree_hanging_sign", p -> new HangingSignItem(p, GotModBlocks.WORMTREE_HANGING_SIGN.get(), GotModBlocks.WORMTREE_WALL_HANGING_SIGN.get()));
    // ── Boats ─────────────────────────────────────────────────────────────
    public static final DeferredItem<Item> WEIRWOOD_BOAT       = REGISTRY.registerItem("weirwood_boat",       p -> new BoatItem(false, GotModBoatTypes.WEIRWOOD.get(), p));
    public static final DeferredItem<Item> WEIRWOOD_CHEST_BOAT = REGISTRY.registerItem("weirwood_chest_boat", p -> new BoatItem(true,  GotModBoatTypes.WEIRWOOD.get(), p));
    public static final DeferredItem<Item> ASPEN_BOAT       = REGISTRY.registerItem("aspen_boat",       p -> new BoatItem(false, GotModBoatTypes.ASPEN.get(), p));
    public static final DeferredItem<Item> ASPEN_CHEST_BOAT = REGISTRY.registerItem("aspen_chest_boat", p -> new BoatItem(true,  GotModBoatTypes.ASPEN.get(), p));
    public static final DeferredItem<Item> ALDER_BOAT       = REGISTRY.registerItem("alder_boat",       p -> new BoatItem(false, GotModBoatTypes.ALDER.get(), p));
    public static final DeferredItem<Item> ALDER_CHEST_BOAT = REGISTRY.registerItem("alder_chest_boat", p -> new BoatItem(true,  GotModBoatTypes.ALDER.get(), p));
    public static final DeferredItem<Item> PINE_BOAT       = REGISTRY.registerItem("pine_boat",       p -> new BoatItem(false, GotModBoatTypes.PINE.get(), p));
    public static final DeferredItem<Item> PINE_CHEST_BOAT = REGISTRY.registerItem("pine_chest_boat", p -> new BoatItem(true,  GotModBoatTypes.PINE.get(), p));
    public static final DeferredItem<Item> FIR_BOAT       = REGISTRY.registerItem("fir_boat",       p -> new BoatItem(false, GotModBoatTypes.FIR.get(), p));
    public static final DeferredItem<Item> FIR_CHEST_BOAT = REGISTRY.registerItem("fir_chest_boat", p -> new BoatItem(true,  GotModBoatTypes.FIR.get(), p));
    public static final DeferredItem<Item> SENTINAL_BOAT       = REGISTRY.registerItem("sentinal_boat",       p -> new BoatItem(false, GotModBoatTypes.SENTINAL.get(), p));
    public static final DeferredItem<Item> SENTINAL_CHEST_BOAT = REGISTRY.registerItem("sentinal_chest_boat", p -> new BoatItem(true,  GotModBoatTypes.SENTINAL.get(), p));
    public static final DeferredItem<Item> IRONWOOD_BOAT       = REGISTRY.registerItem("ironwood_boat",       p -> new BoatItem(false, GotModBoatTypes.IRONWOOD.get(), p));
    public static final DeferredItem<Item> IRONWOOD_CHEST_BOAT = REGISTRY.registerItem("ironwood_chest_boat", p -> new BoatItem(true,  GotModBoatTypes.IRONWOOD.get(), p));
    public static final DeferredItem<Item> BEECH_BOAT       = REGISTRY.registerItem("beech_boat",       p -> new BoatItem(false, GotModBoatTypes.BEECH.get(), p));
    public static final DeferredItem<Item> BEECH_CHEST_BOAT = REGISTRY.registerItem("beech_chest_boat", p -> new BoatItem(true,  GotModBoatTypes.BEECH.get(), p));
    public static final DeferredItem<Item> SOLDIER_PINE_BOAT       = REGISTRY.registerItem("soldier_pine_boat",       p -> new BoatItem(false, GotModBoatTypes.SOLDIER_PINE.get(), p));
    public static final DeferredItem<Item> SOLDIER_PINE_CHEST_BOAT = REGISTRY.registerItem("soldier_pine_chest_boat", p -> new BoatItem(true,  GotModBoatTypes.SOLDIER_PINE.get(), p));
    public static final DeferredItem<Item> ASH_BOAT       = REGISTRY.registerItem("ash_boat",       p -> new BoatItem(false, GotModBoatTypes.ASH.get(), p));
    public static final DeferredItem<Item> ASH_CHEST_BOAT = REGISTRY.registerItem("ash_chest_boat", p -> new BoatItem(true,  GotModBoatTypes.ASH.get(), p));
    public static final DeferredItem<Item> HAWTHORN_BOAT       = REGISTRY.registerItem("hawthorn_boat",       p -> new BoatItem(false, GotModBoatTypes.HAWTHORN.get(), p));
    public static final DeferredItem<Item> HAWTHORN_CHEST_BOAT = REGISTRY.registerItem("hawthorn_chest_boat", p -> new BoatItem(true,  GotModBoatTypes.HAWTHORN.get(), p));
    public static final DeferredItem<Item> BLACKBARK_BOAT       = REGISTRY.registerItem("blackbark_boat",       p -> new BoatItem(false, GotModBoatTypes.BLACKBARK.get(), p));
    public static final DeferredItem<Item> BLACKBARK_CHEST_BOAT = REGISTRY.registerItem("blackbark_chest_boat", p -> new BoatItem(true,  GotModBoatTypes.BLACKBARK.get(), p));
    public static final DeferredItem<Item> BLOODWOOD_BOAT       = REGISTRY.registerItem("bloodwood_boat",       p -> new BoatItem(false, GotModBoatTypes.BLOODWOOD.get(), p));
    public static final DeferredItem<Item> BLOODWOOD_CHEST_BOAT = REGISTRY.registerItem("bloodwood_chest_boat", p -> new BoatItem(true,  GotModBoatTypes.BLOODWOOD.get(), p));
    public static final DeferredItem<Item> BLUE_MAHOE_BOAT       = REGISTRY.registerItem("blue_mahoe_boat",       p -> new BoatItem(false, GotModBoatTypes.BLUE_MAHOE.get(), p));
    public static final DeferredItem<Item> BLUE_MAHOE_CHEST_BOAT = REGISTRY.registerItem("blue_mahoe_chest_boat", p -> new BoatItem(true,  GotModBoatTypes.BLUE_MAHOE.get(), p));
    public static final DeferredItem<Item> COTTONWOOD_BOAT       = REGISTRY.registerItem("cottonwood_boat",       p -> new BoatItem(false, GotModBoatTypes.COTTONWOOD.get(), p));
    public static final DeferredItem<Item> COTTONWOOD_CHEST_BOAT = REGISTRY.registerItem("cottonwood_chest_boat", p -> new BoatItem(true,  GotModBoatTypes.COTTONWOOD.get(), p));
    public static final DeferredItem<Item> BLACK_COTTONWOOD_BOAT       = REGISTRY.registerItem("black_cottonwood_boat",       p -> new BoatItem(false, GotModBoatTypes.BLACK_COTTONWOOD.get(), p));
    public static final DeferredItem<Item> BLACK_COTTONWOOD_CHEST_BOAT = REGISTRY.registerItem("black_cottonwood_chest_boat", p -> new BoatItem(true,  GotModBoatTypes.BLACK_COTTONWOOD.get(), p));
    public static final DeferredItem<Item> CINNAMON_BOAT       = REGISTRY.registerItem("cinnamon_boat",       p -> new BoatItem(false, GotModBoatTypes.CINNAMON.get(), p));
    public static final DeferredItem<Item> CINNAMON_CHEST_BOAT = REGISTRY.registerItem("cinnamon_chest_boat", p -> new BoatItem(true,  GotModBoatTypes.CINNAMON.get(), p));
    public static final DeferredItem<Item> CLOVE_BOAT       = REGISTRY.registerItem("clove_boat",       p -> new BoatItem(false, GotModBoatTypes.CLOVE.get(), p));
    public static final DeferredItem<Item> CLOVE_CHEST_BOAT = REGISTRY.registerItem("clove_chest_boat", p -> new BoatItem(true,  GotModBoatTypes.CLOVE.get(), p));
    public static final DeferredItem<Item> EBONY_BOAT       = REGISTRY.registerItem("ebony_boat",       p -> new BoatItem(false, GotModBoatTypes.EBONY.get(), p));
    public static final DeferredItem<Item> EBONY_CHEST_BOAT = REGISTRY.registerItem("ebony_chest_boat", p -> new BoatItem(true,  GotModBoatTypes.EBONY.get(), p));
    public static final DeferredItem<Item> ELM_BOAT       = REGISTRY.registerItem("elm_boat",       p -> new BoatItem(false, GotModBoatTypes.ELM.get(), p));
    public static final DeferredItem<Item> ELM_CHEST_BOAT = REGISTRY.registerItem("elm_chest_boat", p -> new BoatItem(true,  GotModBoatTypes.ELM.get(), p));
    public static final DeferredItem<Item> CEDAR_BOAT       = REGISTRY.registerItem("cedar_boat",       p -> new BoatItem(false, GotModBoatTypes.CEDAR.get(), p));
    public static final DeferredItem<Item> CEDAR_CHEST_BOAT = REGISTRY.registerItem("cedar_chest_boat", p -> new BoatItem(true,  GotModBoatTypes.CEDAR.get(), p));
    public static final DeferredItem<Item> APPLE_BOAT       = REGISTRY.registerItem("apple_boat",       p -> new BoatItem(false, GotModBoatTypes.APPLE.get(), p));
    public static final DeferredItem<Item> APPLE_CHEST_BOAT = REGISTRY.registerItem("apple_chest_boat", p -> new BoatItem(true,  GotModBoatTypes.APPLE.get(), p));
    public static final DeferredItem<Item> GOLDENHEART_BOAT       = REGISTRY.registerItem("goldenheart_boat",       p -> new BoatItem(false, GotModBoatTypes.GOLDENHEART.get(), p));
    public static final DeferredItem<Item> GOLDENHEART_CHEST_BOAT = REGISTRY.registerItem("goldenheart_chest_boat", p -> new BoatItem(true,  GotModBoatTypes.GOLDENHEART.get(), p));
    public static final DeferredItem<Item> LINDEN_BOAT       = REGISTRY.registerItem("linden_boat",       p -> new BoatItem(false, GotModBoatTypes.LINDEN.get(), p));
    public static final DeferredItem<Item> LINDEN_CHEST_BOAT = REGISTRY.registerItem("linden_chest_boat", p -> new BoatItem(true,  GotModBoatTypes.LINDEN.get(), p));
    public static final DeferredItem<Item> MAHOGANY_BOAT       = REGISTRY.registerItem("mahogany_boat",       p -> new BoatItem(false, GotModBoatTypes.MAHOGANY.get(), p));
    public static final DeferredItem<Item> MAHOGANY_CHEST_BOAT = REGISTRY.registerItem("mahogany_chest_boat", p -> new BoatItem(true,  GotModBoatTypes.MAHOGANY.get(), p));
    public static final DeferredItem<Item> MAPLE_BOAT       = REGISTRY.registerItem("maple_boat",       p -> new BoatItem(false, GotModBoatTypes.MAPLE.get(), p));
    public static final DeferredItem<Item> MAPLE_CHEST_BOAT = REGISTRY.registerItem("maple_chest_boat", p -> new BoatItem(true,  GotModBoatTypes.MAPLE.get(), p));
    public static final DeferredItem<Item> MYRRH_BOAT       = REGISTRY.registerItem("myrrh_boat",       p -> new BoatItem(false, GotModBoatTypes.MYRRH.get(), p));
    public static final DeferredItem<Item> MYRRH_CHEST_BOAT = REGISTRY.registerItem("myrrh_chest_boat", p -> new BoatItem(true,  GotModBoatTypes.MYRRH.get(), p));
    public static final DeferredItem<Item> REDWOOD_BOAT       = REGISTRY.registerItem("redwood_boat",       p -> new BoatItem(false, GotModBoatTypes.REDWOOD.get(), p));
    public static final DeferredItem<Item> REDWOOD_CHEST_BOAT = REGISTRY.registerItem("redwood_chest_boat", p -> new BoatItem(true,  GotModBoatTypes.REDWOOD.get(), p));
    public static final DeferredItem<Item> CHESTNUT_BOAT       = REGISTRY.registerItem("chestnut_boat",       p -> new BoatItem(false, GotModBoatTypes.CHESTNUT.get(), p));
    public static final DeferredItem<Item> CHESTNUT_CHEST_BOAT = REGISTRY.registerItem("chestnut_chest_boat", p -> new BoatItem(true,  GotModBoatTypes.CHESTNUT.get(), p));
    public static final DeferredItem<Item> WILLOW_BOAT       = REGISTRY.registerItem("willow_boat",       p -> new BoatItem(false, GotModBoatTypes.WILLOW.get(), p));
    public static final DeferredItem<Item> WILLOW_CHEST_BOAT = REGISTRY.registerItem("willow_chest_boat", p -> new BoatItem(true,  GotModBoatTypes.WILLOW.get(), p));
    public static final DeferredItem<Item> WORMTREE_BOAT       = REGISTRY.registerItem("wormtree_boat",       p -> new BoatItem(false, GotModBoatTypes.WORMTREE.get(), p));
    public static final DeferredItem<Item> WORMTREE_CHEST_BOAT = REGISTRY.registerItem("wormtree_chest_boat", p -> new BoatItem(true,  GotModBoatTypes.WORMTREE.get(), p));


    // ── Saplings ─────────────────────────────────────────────────────────
    public static final DeferredItem<Item> WEIRWOOD_SAPLING = block(GotModBlocks.WEIRWOOD_SAPLING);
    public static final DeferredItem<Item> ASPEN_SAPLING = block(GotModBlocks.ASPEN_SAPLING);
    public static final DeferredItem<Item> ALDER_SAPLING = block(GotModBlocks.ALDER_SAPLING);
    public static final DeferredItem<Item> PINE_SAPLING = block(GotModBlocks.PINE_SAPLING);
    public static final DeferredItem<Item> FIR_SAPLING = block(GotModBlocks.FIR_SAPLING);
    public static final DeferredItem<Item> SENTINAL_SAPLING = block(GotModBlocks.SENTINAL_SAPLING);
    public static final DeferredItem<Item> IRONWOOD_SAPLING = block(GotModBlocks.IRONWOOD_SAPLING);
    public static final DeferredItem<Item> BEECH_SAPLING = block(GotModBlocks.BEECH_SAPLING);
    public static final DeferredItem<Item> SOLDIER_PINE_SAPLING = block(GotModBlocks.SOLDIER_PINE_SAPLING);
    public static final DeferredItem<Item> ASH_SAPLING = block(GotModBlocks.ASH_SAPLING);
    public static final DeferredItem<Item> HAWTHORN_SAPLING = block(GotModBlocks.HAWTHORN_SAPLING);
    public static final DeferredItem<Item> BLACKBARK_SAPLING = block(GotModBlocks.BLACKBARK_SAPLING);
    public static final DeferredItem<Item> BLOODWOOD_SAPLING = block(GotModBlocks.BLOODWOOD_SAPLING);
    public static final DeferredItem<Item> BLUE_MAHOE_SAPLING = block(GotModBlocks.BLUE_MAHOE_SAPLING);
    public static final DeferredItem<Item> COTTONWOOD_SAPLING = block(GotModBlocks.COTTONWOOD_SAPLING);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_SAPLING = block(GotModBlocks.BLACK_COTTONWOOD_SAPLING);
    public static final DeferredItem<Item> CINNAMON_SAPLING = block(GotModBlocks.CINNAMON_SAPLING);
    public static final DeferredItem<Item> CLOVE_SAPLING = block(GotModBlocks.CLOVE_SAPLING);
    public static final DeferredItem<Item> EBONY_SAPLING = block(GotModBlocks.EBONY_SAPLING);
    public static final DeferredItem<Item> ELM_SAPLING = block(GotModBlocks.ELM_SAPLING);
    public static final DeferredItem<Item> CEDAR_SAPLING = block(GotModBlocks.CEDAR_SAPLING);
    public static final DeferredItem<Item> APPLE_SAPLING = block(GotModBlocks.APPLE_SAPLING);
    public static final DeferredItem<Item> GOLDENHEART_SAPLING = block(GotModBlocks.GOLDENHEART_SAPLING);
    public static final DeferredItem<Item> LINDEN_SAPLING = block(GotModBlocks.LINDEN_SAPLING);
    public static final DeferredItem<Item> MAHOGANY_SAPLING = block(GotModBlocks.MAHOGANY_SAPLING);
    public static final DeferredItem<Item> MAPLE_SAPLING = block(GotModBlocks.MAPLE_SAPLING);
    public static final DeferredItem<Item> MYRRH_SAPLING = block(GotModBlocks.MYRRH_SAPLING);
    public static final DeferredItem<Item> REDWOOD_SAPLING = block(GotModBlocks.REDWOOD_SAPLING);
    public static final DeferredItem<Item> CHESTNUT_SAPLING = block(GotModBlocks.CHESTNUT_SAPLING);
    public static final DeferredItem<Item> WILLOW_SAPLING = block(GotModBlocks.WILLOW_SAPLING);
    public static final DeferredItem<Item> WORMTREE_SAPLING = block(GotModBlocks.WORMTREE_SAPLING);

    // ── Copper tools ──────────────────────────────────────────────────────
    // Constructors: ToolMaterial, float attackDamageBonus, float attackSpeed, Properties
    public static final DeferredItem<SwordItem>  COPPER_SWORD    = REGISTRY.registerItem("copper_sword",
            p -> new SwordItem(GotModTiers.COPPER, 3.0f, -2.4f, p));
    public static final DeferredItem<PickaxeItem> COPPER_PICKAXE = REGISTRY.registerItem("copper_pickaxe",
            p -> new PickaxeItem(GotModTiers.COPPER, 1.0f, -2.8f, p));
    public static final DeferredItem<AxeItem>    COPPER_AXE      = REGISTRY.registerItem("copper_axe",
            p -> new AxeItem(GotModTiers.COPPER, 6.0f, -3.1f, p));
    public static final DeferredItem<ShovelItem> COPPER_SHOVEL   = REGISTRY.registerItem("copper_shovel",
            p -> new ShovelItem(GotModTiers.COPPER, 1.5f, -3.0f, p));
    public static final DeferredItem<HoeItem>    COPPER_HOE      = REGISTRY.registerItem("copper_hoe",
            p -> new HoeItem(GotModTiers.COPPER, 0.0f, -3.0f, p));

    // ── Bronze tools ──────────────────────────────────────────────────────
    public static final DeferredItem<SwordItem>  BRONZE_SWORD    = REGISTRY.registerItem("bronze_sword",
            p -> new SwordItem(GotModTiers.BRONZE, 4.0f, -2.4f, p));
    public static final DeferredItem<PickaxeItem> BRONZE_PICKAXE = REGISTRY.registerItem("bronze_pickaxe",
            p -> new PickaxeItem(GotModTiers.BRONZE, 1.0f, -2.8f, p));
    public static final DeferredItem<AxeItem>    BRONZE_AXE      = REGISTRY.registerItem("bronze_axe",
            p -> new AxeItem(GotModTiers.BRONZE, 7.0f, -3.1f, p));
    public static final DeferredItem<ShovelItem> BRONZE_SHOVEL   = REGISTRY.registerItem("bronze_shovel",
            p -> new ShovelItem(GotModTiers.BRONZE, 1.5f, -3.0f, p));
    public static final DeferredItem<HoeItem>    BRONZE_HOE      = REGISTRY.registerItem("bronze_hoe",
            p -> new HoeItem(GotModTiers.BRONZE, 0.0f, -3.0f, p));

    // ── Copper armor ──────────────────────────────────────────────────────
    public static final DeferredItem<ArmorItem> COPPER_HELMET     = REGISTRY.registerItem("copper_helmet",
            p -> new ArmorItem(GotModArmorMaterials.COPPER.value(), ArmorType.HELMET, p));
    public static final DeferredItem<ArmorItem> COPPER_CHESTPLATE = REGISTRY.registerItem("copper_chestplate",
            p -> new ArmorItem(GotModArmorMaterials.COPPER.value(), ArmorType.CHESTPLATE, p));
    public static final DeferredItem<ArmorItem> COPPER_LEGGINGS   = REGISTRY.registerItem("copper_leggings",
            p -> new ArmorItem(GotModArmorMaterials.COPPER.value(), ArmorType.LEGGINGS, p));
    public static final DeferredItem<ArmorItem> COPPER_BOOTS      = REGISTRY.registerItem("copper_boots",
            p -> new ArmorItem(GotModArmorMaterials.COPPER.value(), ArmorType.BOOTS, p));

    // ── Bronze armor ──────────────────────────────────────────────────────
    public static final DeferredItem<ArmorItem> BRONZE_HELMET     = REGISTRY.registerItem("bronze_helmet",
            p -> new ArmorItem(GotModArmorMaterials.BRONZE.value(), ArmorType.HELMET, p));
    public static final DeferredItem<ArmorItem> BRONZE_CHESTPLATE = REGISTRY.registerItem("bronze_chestplate",
            p -> new ArmorItem(GotModArmorMaterials.BRONZE.value(), ArmorType.CHESTPLATE, p));
    public static final DeferredItem<ArmorItem> BRONZE_LEGGINGS   = REGISTRY.registerItem("bronze_leggings",
            p -> new ArmorItem(GotModArmorMaterials.BRONZE.value(), ArmorType.LEGGINGS, p));
    public static final DeferredItem<ArmorItem> BRONZE_BOOTS      = REGISTRY.registerItem("bronze_boots",
            p -> new ArmorItem(GotModArmorMaterials.BRONZE.value(), ArmorType.BOOTS, p));

    // ── Helpers ───────────────────────────────────────────────────────────

    private static DeferredItem<Item> simple(String name) {
        return REGISTRY.registerSimpleItem(name);
    }

    private static <I extends Item> DeferredItem<I> register(String name, Function<Item.Properties, ? extends I> supplier) {
        return REGISTRY.registerItem(name, supplier, new Item.Properties());
    }

    private static DeferredItem<Item> block(DeferredHolder<Block, Block> block) {
        return block(block, new Item.Properties());
    }

    private static DeferredItem<Item> block(DeferredHolder<Block, Block> block, Item.Properties properties) {
        return REGISTRY.registerItem(block.getId().getPath(), prop -> new BlockItem(block.get(), prop), properties);
    }

    /** Doors are two blocks tall — must use DoubleHighBlockItem. */
    private static DeferredItem<Item> door(DeferredHolder<Block, Block> block) {
        return REGISTRY.registerItem(block.getId().getPath(),
                prop -> new DoubleHighBlockItem(block.get(), prop), new Item.Properties());
    }
}