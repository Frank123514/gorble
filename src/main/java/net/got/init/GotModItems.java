package net.got.init;

import net.got.GotMod;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.equipment.ArmorType;
import net.got.item.GotBoatItem;

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

    // ── Wood Shingles Items — paste into GotModItems.java ─────────────────────
    public static final DeferredItem<BlockItem> ALDER_ROOFING =
            REGISTRY.registerSimpleBlockItem("alder_roofing", GotModBlocks.ALDER_ROOFING);
    public static final DeferredItem<BlockItem> ALDER_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("alder_roofing_slab", GotModBlocks.ALDER_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> ALDER_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("alder_roofing_stairs", GotModBlocks.ALDER_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> ALDER_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("alder_roofing_wall", GotModBlocks.ALDER_ROOFING_WALL);
    public static final DeferredItem<BlockItem> APPLE_ROOFING =
            REGISTRY.registerSimpleBlockItem("apple_roofing", GotModBlocks.APPLE_ROOFING);
    public static final DeferredItem<BlockItem> APPLE_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("apple_roofing_slab", GotModBlocks.APPLE_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> APPLE_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("apple_roofing_stairs", GotModBlocks.APPLE_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> APPLE_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("apple_roofing_wall", GotModBlocks.APPLE_ROOFING_WALL);
    public static final DeferredItem<BlockItem> ASH_ROOFING =
            REGISTRY.registerSimpleBlockItem("ash_roofing", GotModBlocks.ASH_ROOFING);
    public static final DeferredItem<BlockItem> ASH_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("ash_roofing_slab", GotModBlocks.ASH_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> ASH_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("ash_roofing_stairs", GotModBlocks.ASH_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> ASH_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("ash_roofing_wall", GotModBlocks.ASH_ROOFING_WALL);
    public static final DeferredItem<BlockItem> ASPEN_ROOFING =
            REGISTRY.registerSimpleBlockItem("aspen_roofing", GotModBlocks.ASPEN_ROOFING);
    public static final DeferredItem<BlockItem> ASPEN_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("aspen_roofing_slab", GotModBlocks.ASPEN_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> ASPEN_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("aspen_roofing_stairs", GotModBlocks.ASPEN_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> ASPEN_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("aspen_roofing_wall", GotModBlocks.ASPEN_ROOFING_WALL);
    public static final DeferredItem<BlockItem> BEECH_ROOFING =
            REGISTRY.registerSimpleBlockItem("beech_roofing", GotModBlocks.BEECH_ROOFING);
    public static final DeferredItem<BlockItem> BEECH_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("beech_roofing_slab", GotModBlocks.BEECH_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> BEECH_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("beech_roofing_stairs", GotModBlocks.BEECH_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> BEECH_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("beech_roofing_wall", GotModBlocks.BEECH_ROOFING_WALL);
    public static final DeferredItem<BlockItem> BLACK_COTTONWOOD_ROOFING =
            REGISTRY.registerSimpleBlockItem("black_cottonwood_roofing", GotModBlocks.BLACK_COTTONWOOD_ROOFING);
    public static final DeferredItem<BlockItem> BLACK_COTTONWOOD_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("black_cottonwood_roofing_slab", GotModBlocks.BLACK_COTTONWOOD_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> BLACK_COTTONWOOD_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("black_cottonwood_roofing_stairs", GotModBlocks.BLACK_COTTONWOOD_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> BLACK_COTTONWOOD_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("black_cottonwood_roofing_wall", GotModBlocks.BLACK_COTTONWOOD_ROOFING_WALL);
    public static final DeferredItem<BlockItem> BLACKBARK_ROOFING =
            REGISTRY.registerSimpleBlockItem("blackbark_roofing", GotModBlocks.BLACKBARK_ROOFING);
    public static final DeferredItem<BlockItem> BLACKBARK_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("blackbark_roofing_slab", GotModBlocks.BLACKBARK_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> BLACKBARK_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("blackbark_roofing_stairs", GotModBlocks.BLACKBARK_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> BLACKBARK_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("blackbark_roofing_wall", GotModBlocks.BLACKBARK_ROOFING_WALL);
    public static final DeferredItem<BlockItem> BLOODWOOD_ROOFING =
            REGISTRY.registerSimpleBlockItem("bloodwood_roofing", GotModBlocks.BLOODWOOD_ROOFING);
    public static final DeferredItem<BlockItem> BLOODWOOD_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("bloodwood_roofing_slab", GotModBlocks.BLOODWOOD_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> BLOODWOOD_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("bloodwood_roofing_stairs", GotModBlocks.BLOODWOOD_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> BLOODWOOD_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("bloodwood_roofing_wall", GotModBlocks.BLOODWOOD_ROOFING_WALL);
    public static final DeferredItem<BlockItem> BLUE_MAHOE_ROOFING =
            REGISTRY.registerSimpleBlockItem("blue_mahoe_roofing", GotModBlocks.BLUE_MAHOE_ROOFING);
    public static final DeferredItem<BlockItem> BLUE_MAHOE_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("blue_mahoe_roofing_slab", GotModBlocks.BLUE_MAHOE_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> BLUE_MAHOE_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("blue_mahoe_roofing_stairs", GotModBlocks.BLUE_MAHOE_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> BLUE_MAHOE_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("blue_mahoe_roofing_wall", GotModBlocks.BLUE_MAHOE_ROOFING_WALL);
    public static final DeferredItem<BlockItem> CEDAR_ROOFING =
            REGISTRY.registerSimpleBlockItem("cedar_roofing", GotModBlocks.CEDAR_ROOFING);
    public static final DeferredItem<BlockItem> CEDAR_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("cedar_roofing_slab", GotModBlocks.CEDAR_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> CEDAR_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("cedar_roofing_stairs", GotModBlocks.CEDAR_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> CEDAR_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("cedar_roofing_wall", GotModBlocks.CEDAR_ROOFING_WALL);
    public static final DeferredItem<BlockItem> CHESTNUT_ROOFING =
            REGISTRY.registerSimpleBlockItem("chestnut_roofing", GotModBlocks.CHESTNUT_ROOFING);
    public static final DeferredItem<BlockItem> CHESTNUT_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("chestnut_roofing_slab", GotModBlocks.CHESTNUT_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> CHESTNUT_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("chestnut_roofing_stairs", GotModBlocks.CHESTNUT_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> CHESTNUT_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("chestnut_roofing_wall", GotModBlocks.CHESTNUT_ROOFING_WALL);
    public static final DeferredItem<BlockItem> CINNAMON_ROOFING =
            REGISTRY.registerSimpleBlockItem("cinnamon_roofing", GotModBlocks.CINNAMON_ROOFING);
    public static final DeferredItem<BlockItem> CINNAMON_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("cinnamon_roofing_slab", GotModBlocks.CINNAMON_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> CINNAMON_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("cinnamon_roofing_stairs", GotModBlocks.CINNAMON_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> CINNAMON_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("cinnamon_roofing_wall", GotModBlocks.CINNAMON_ROOFING_WALL);
    public static final DeferredItem<BlockItem> CLOVE_ROOFING =
            REGISTRY.registerSimpleBlockItem("clove_roofing", GotModBlocks.CLOVE_ROOFING);
    public static final DeferredItem<BlockItem> CLOVE_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("clove_roofing_slab", GotModBlocks.CLOVE_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> CLOVE_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("clove_roofing_stairs", GotModBlocks.CLOVE_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> CLOVE_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("clove_roofing_wall", GotModBlocks.CLOVE_ROOFING_WALL);
    public static final DeferredItem<BlockItem> COTTONWOOD_ROOFING =
            REGISTRY.registerSimpleBlockItem("cottonwood_roofing", GotModBlocks.COTTONWOOD_ROOFING);
    public static final DeferredItem<BlockItem> COTTONWOOD_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("cottonwood_roofing_slab", GotModBlocks.COTTONWOOD_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> COTTONWOOD_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("cottonwood_roofing_stairs", GotModBlocks.COTTONWOOD_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> COTTONWOOD_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("cottonwood_roofing_wall", GotModBlocks.COTTONWOOD_ROOFING_WALL);
    public static final DeferredItem<BlockItem> EBONY_ROOFING =
            REGISTRY.registerSimpleBlockItem("ebony_roofing", GotModBlocks.EBONY_ROOFING);
    public static final DeferredItem<BlockItem> EBONY_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("ebony_roofing_slab", GotModBlocks.EBONY_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> EBONY_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("ebony_roofing_stairs", GotModBlocks.EBONY_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> EBONY_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("ebony_roofing_wall", GotModBlocks.EBONY_ROOFING_WALL);
    public static final DeferredItem<BlockItem> ELM_ROOFING =
            REGISTRY.registerSimpleBlockItem("elm_roofing", GotModBlocks.ELM_ROOFING);
    public static final DeferredItem<BlockItem> ELM_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("elm_roofing_slab", GotModBlocks.ELM_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> ELM_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("elm_roofing_stairs", GotModBlocks.ELM_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> ELM_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("elm_roofing_wall", GotModBlocks.ELM_ROOFING_WALL);
    public static final DeferredItem<BlockItem> FIR_ROOFING =
            REGISTRY.registerSimpleBlockItem("fir_roofing", GotModBlocks.FIR_ROOFING);
    public static final DeferredItem<BlockItem> FIR_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("fir_roofing_slab", GotModBlocks.FIR_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> FIR_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("fir_roofing_stairs", GotModBlocks.FIR_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> FIR_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("fir_roofing_wall", GotModBlocks.FIR_ROOFING_WALL);
    public static final DeferredItem<BlockItem> GOLDENHEART_ROOFING =
            REGISTRY.registerSimpleBlockItem("goldenheart_roofing", GotModBlocks.GOLDENHEART_ROOFING);
    public static final DeferredItem<BlockItem> GOLDENHEART_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("goldenheart_roofing_slab", GotModBlocks.GOLDENHEART_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> GOLDENHEART_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("goldenheart_roofing_stairs", GotModBlocks.GOLDENHEART_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> GOLDENHEART_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("goldenheart_roofing_wall", GotModBlocks.GOLDENHEART_ROOFING_WALL);
    public static final DeferredItem<BlockItem> HAWTHORN_ROOFING =
            REGISTRY.registerSimpleBlockItem("hawthorn_roofing", GotModBlocks.HAWTHORN_ROOFING);
    public static final DeferredItem<BlockItem> HAWTHORN_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("hawthorn_roofing_slab", GotModBlocks.HAWTHORN_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> HAWTHORN_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("hawthorn_roofing_stairs", GotModBlocks.HAWTHORN_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> HAWTHORN_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("hawthorn_roofing_wall", GotModBlocks.HAWTHORN_ROOFING_WALL);
    public static final DeferredItem<BlockItem> IRONWOOD_ROOFING =
            REGISTRY.registerSimpleBlockItem("ironwood_roofing", GotModBlocks.IRONWOOD_ROOFING);
    public static final DeferredItem<BlockItem> IRONWOOD_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("ironwood_roofing_slab", GotModBlocks.IRONWOOD_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> IRONWOOD_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("ironwood_roofing_stairs", GotModBlocks.IRONWOOD_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> IRONWOOD_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("ironwood_roofing_wall", GotModBlocks.IRONWOOD_ROOFING_WALL);
    public static final DeferredItem<BlockItem> LINDEN_ROOFING =
            REGISTRY.registerSimpleBlockItem("linden_roofing", GotModBlocks.LINDEN_ROOFING);
    public static final DeferredItem<BlockItem> LINDEN_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("linden_roofing_slab", GotModBlocks.LINDEN_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> LINDEN_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("linden_roofing_stairs", GotModBlocks.LINDEN_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> LINDEN_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("linden_roofing_wall", GotModBlocks.LINDEN_ROOFING_WALL);
    public static final DeferredItem<BlockItem> MAHOGANY_ROOFING =
            REGISTRY.registerSimpleBlockItem("mahogany_roofing", GotModBlocks.MAHOGANY_ROOFING);
    public static final DeferredItem<BlockItem> MAHOGANY_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("mahogany_roofing_slab", GotModBlocks.MAHOGANY_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> MAHOGANY_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("mahogany_roofing_stairs", GotModBlocks.MAHOGANY_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> MAHOGANY_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("mahogany_roofing_wall", GotModBlocks.MAHOGANY_ROOFING_WALL);
    public static final DeferredItem<BlockItem> MAPLE_ROOFING =
            REGISTRY.registerSimpleBlockItem("maple_roofing", GotModBlocks.MAPLE_ROOFING);
    public static final DeferredItem<BlockItem> MAPLE_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("maple_roofing_slab", GotModBlocks.MAPLE_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> MAPLE_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("maple_roofing_stairs", GotModBlocks.MAPLE_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> MAPLE_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("maple_roofing_wall", GotModBlocks.MAPLE_ROOFING_WALL);
    public static final DeferredItem<BlockItem> MYRRH_ROOFING =
            REGISTRY.registerSimpleBlockItem("myrrh_roofing", GotModBlocks.MYRRH_ROOFING);
    public static final DeferredItem<BlockItem> MYRRH_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("myrrh_roofing_slab", GotModBlocks.MYRRH_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> MYRRH_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("myrrh_roofing_stairs", GotModBlocks.MYRRH_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> MYRRH_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("myrrh_roofing_wall", GotModBlocks.MYRRH_ROOFING_WALL);
    public static final DeferredItem<BlockItem> PINE_ROOFING =
            REGISTRY.registerSimpleBlockItem("pine_roofing", GotModBlocks.PINE_ROOFING);
    public static final DeferredItem<BlockItem> PINE_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("pine_roofing_slab", GotModBlocks.PINE_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> PINE_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("pine_roofing_stairs", GotModBlocks.PINE_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> PINE_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("pine_roofing_wall", GotModBlocks.PINE_ROOFING_WALL);
    public static final DeferredItem<BlockItem> REDWOOD_ROOFING =
            REGISTRY.registerSimpleBlockItem("redwood_roofing", GotModBlocks.REDWOOD_ROOFING);
    public static final DeferredItem<BlockItem> REDWOOD_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("redwood_roofing_slab", GotModBlocks.REDWOOD_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> REDWOOD_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("redwood_roofing_stairs", GotModBlocks.REDWOOD_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> REDWOOD_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("redwood_roofing_wall", GotModBlocks.REDWOOD_ROOFING_WALL);
    public static final DeferredItem<BlockItem> SENTINAL_ROOFING =
            REGISTRY.registerSimpleBlockItem("sentinal_roofing", GotModBlocks.SENTINAL_ROOFING);
    public static final DeferredItem<BlockItem> SENTINAL_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("sentinal_roofing_slab", GotModBlocks.SENTINAL_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> SENTINAL_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("sentinal_roofing_stairs", GotModBlocks.SENTINAL_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> SENTINAL_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("sentinal_roofing_wall", GotModBlocks.SENTINAL_ROOFING_WALL);
    public static final DeferredItem<BlockItem> SOLDIER_PINE_ROOFING =
            REGISTRY.registerSimpleBlockItem("soldier_pine_roofing", GotModBlocks.SOLDIER_PINE_ROOFING);
    public static final DeferredItem<BlockItem> SOLDIER_PINE_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("soldier_pine_roofing_slab", GotModBlocks.SOLDIER_PINE_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> SOLDIER_PINE_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("soldier_pine_roofing_stairs", GotModBlocks.SOLDIER_PINE_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> SOLDIER_PINE_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("soldier_pine_roofing_wall", GotModBlocks.SOLDIER_PINE_ROOFING_WALL);
    public static final DeferredItem<BlockItem> WEIRWOOD_ROOFING =
            REGISTRY.registerSimpleBlockItem("weirwood_roofing", GotModBlocks.WEIRWOOD_ROOFING);
    public static final DeferredItem<BlockItem> WEIRWOOD_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("weirwood_roofing_slab", GotModBlocks.WEIRWOOD_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> WEIRWOOD_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("weirwood_roofing_stairs", GotModBlocks.WEIRWOOD_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> WEIRWOOD_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("weirwood_roofing_wall", GotModBlocks.WEIRWOOD_ROOFING_WALL);
    public static final DeferredItem<BlockItem> WILLOW_ROOFING =
            REGISTRY.registerSimpleBlockItem("willow_roofing", GotModBlocks.WILLOW_ROOFING);
    public static final DeferredItem<BlockItem> WILLOW_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("willow_roofing_slab", GotModBlocks.WILLOW_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> WILLOW_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("willow_roofing_stairs", GotModBlocks.WILLOW_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> WILLOW_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("willow_roofing_wall", GotModBlocks.WILLOW_ROOFING_WALL);
    public static final DeferredItem<BlockItem> WORMTREE_ROOFING =
            REGISTRY.registerSimpleBlockItem("wormtree_roofing", GotModBlocks.WORMTREE_ROOFING);
    public static final DeferredItem<BlockItem> WORMTREE_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("wormtree_roofing_slab", GotModBlocks.WORMTREE_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> WORMTREE_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("wormtree_roofing_stairs", GotModBlocks.WORMTREE_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> WORMTREE_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("wormtree_roofing_wall", GotModBlocks.WORMTREE_ROOFING_WALL);
    public static final DeferredItem<BlockItem> OAK_ROOFING =
            REGISTRY.registerSimpleBlockItem("oak_roofing", GotModBlocks.OAK_ROOFING);
    public static final DeferredItem<BlockItem> OAK_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("oak_roofing_slab", GotModBlocks.OAK_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> OAK_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("oak_roofing_stairs", GotModBlocks.OAK_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> OAK_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("oak_roofing_wall", GotModBlocks.OAK_ROOFING_WALL);
    public static final DeferredItem<BlockItem> SPRUCE_ROOFING =
            REGISTRY.registerSimpleBlockItem("spruce_roofing", GotModBlocks.SPRUCE_ROOFING);
    public static final DeferredItem<BlockItem> SPRUCE_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("spruce_roofing_slab", GotModBlocks.SPRUCE_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> SPRUCE_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("spruce_roofing_stairs", GotModBlocks.SPRUCE_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> SPRUCE_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("spruce_roofing_wall", GotModBlocks.SPRUCE_ROOFING_WALL);
    public static final DeferredItem<BlockItem> BIRCH_ROOFING =
            REGISTRY.registerSimpleBlockItem("birch_roofing", GotModBlocks.BIRCH_ROOFING);
    public static final DeferredItem<BlockItem> BIRCH_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("birch_roofing_slab", GotModBlocks.BIRCH_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> BIRCH_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("birch_roofing_stairs", GotModBlocks.BIRCH_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> BIRCH_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("birch_roofing_wall", GotModBlocks.BIRCH_ROOFING_WALL);
    public static final DeferredItem<BlockItem> JUNGLE_ROOFING =
            REGISTRY.registerSimpleBlockItem("jungle_roofing", GotModBlocks.JUNGLE_ROOFING);
    public static final DeferredItem<BlockItem> JUNGLE_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("jungle_roofing_slab", GotModBlocks.JUNGLE_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> JUNGLE_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("jungle_roofing_stairs", GotModBlocks.JUNGLE_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> JUNGLE_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("jungle_roofing_wall", GotModBlocks.JUNGLE_ROOFING_WALL);
    public static final DeferredItem<BlockItem> ACACIA_ROOFING =
            REGISTRY.registerSimpleBlockItem("acacia_roofing", GotModBlocks.ACACIA_ROOFING);
    public static final DeferredItem<BlockItem> ACACIA_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("acacia_roofing_slab", GotModBlocks.ACACIA_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> ACACIA_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("acacia_roofing_stairs", GotModBlocks.ACACIA_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> ACACIA_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("acacia_roofing_wall", GotModBlocks.ACACIA_ROOFING_WALL);
    public static final DeferredItem<BlockItem> DARK_OAK_ROOFING =
            REGISTRY.registerSimpleBlockItem("dark_oak_roofing", GotModBlocks.DARK_OAK_ROOFING);
    public static final DeferredItem<BlockItem> DARK_OAK_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("dark_oak_roofing_slab", GotModBlocks.DARK_OAK_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> DARK_OAK_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("dark_oak_roofing_stairs", GotModBlocks.DARK_OAK_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> DARK_OAK_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("dark_oak_roofing_wall", GotModBlocks.DARK_OAK_ROOFING_WALL);
    public static final DeferredItem<BlockItem> MANGROVE_ROOFING =
            REGISTRY.registerSimpleBlockItem("mangrove_roofing", GotModBlocks.MANGROVE_ROOFING);
    public static final DeferredItem<BlockItem> MANGROVE_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("mangrove_roofing_slab", GotModBlocks.MANGROVE_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> MANGROVE_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("mangrove_roofing_stairs", GotModBlocks.MANGROVE_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> MANGROVE_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("mangrove_roofing_wall", GotModBlocks.MANGROVE_ROOFING_WALL);
    public static final DeferredItem<BlockItem> CHERRY_ROOFING =
            REGISTRY.registerSimpleBlockItem("cherry_roofing", GotModBlocks.CHERRY_ROOFING);
    public static final DeferredItem<BlockItem> CHERRY_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("cherry_roofing_slab", GotModBlocks.CHERRY_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> CHERRY_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("cherry_roofing_stairs", GotModBlocks.CHERRY_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> CHERRY_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("cherry_roofing_wall", GotModBlocks.CHERRY_ROOFING_WALL);
    public static final DeferredItem<BlockItem> RED_CHERRY_ROOFING =
            REGISTRY.registerSimpleBlockItem("red_cherry_roofing", GotModBlocks.RED_CHERRY_ROOFING);
    public static final DeferredItem<BlockItem> RED_CHERRY_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("red_cherry_roofing_slab", GotModBlocks.RED_CHERRY_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> RED_CHERRY_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("red_cherry_roofing_stairs", GotModBlocks.RED_CHERRY_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> RED_CHERRY_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("red_cherry_roofing_wall", GotModBlocks.RED_CHERRY_ROOFING_WALL);
    public static final DeferredItem<BlockItem> BLACK_CHERRY_ROOFING =
            REGISTRY.registerSimpleBlockItem("black_cherry_roofing", GotModBlocks.BLACK_CHERRY_ROOFING);
    public static final DeferredItem<BlockItem> BLACK_CHERRY_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("black_cherry_roofing_slab", GotModBlocks.BLACK_CHERRY_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> BLACK_CHERRY_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("black_cherry_roofing_stairs", GotModBlocks.BLACK_CHERRY_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> BLACK_CHERRY_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("black_cherry_roofing_wall", GotModBlocks.BLACK_CHERRY_ROOFING_WALL);
    public static final DeferredItem<BlockItem> WHITE_CHERRY_ROOFING =
            REGISTRY.registerSimpleBlockItem("white_cherry_roofing", GotModBlocks.WHITE_CHERRY_ROOFING);
    public static final DeferredItem<BlockItem> WHITE_CHERRY_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("white_cherry_roofing_slab", GotModBlocks.WHITE_CHERRY_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> WHITE_CHERRY_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("white_cherry_roofing_stairs", GotModBlocks.WHITE_CHERRY_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> WHITE_CHERRY_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("white_cherry_roofing_wall", GotModBlocks.WHITE_CHERRY_ROOFING_WALL);
    public static final DeferredItem<BlockItem> BAMBOO_ROOFING =
            REGISTRY.registerSimpleBlockItem("bamboo_roofing", GotModBlocks.BAMBOO_ROOFING);
    public static final DeferredItem<BlockItem> BAMBOO_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("bamboo_roofing_slab", GotModBlocks.BAMBOO_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> BAMBOO_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("bamboo_roofing_stairs", GotModBlocks.BAMBOO_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> BAMBOO_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("bamboo_roofing_wall", GotModBlocks.BAMBOO_ROOFING_WALL);
    public static final DeferredItem<BlockItem> CRIMSON_ROOFING =
            REGISTRY.registerSimpleBlockItem("crimson_roofing", GotModBlocks.CRIMSON_ROOFING);
    public static final DeferredItem<BlockItem> CRIMSON_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("crimson_roofing_slab", GotModBlocks.CRIMSON_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> CRIMSON_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("crimson_roofing_stairs", GotModBlocks.CRIMSON_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> CRIMSON_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("crimson_roofing_wall", GotModBlocks.CRIMSON_ROOFING_WALL);
    public static final DeferredItem<BlockItem> WARPED_ROOFING =
            REGISTRY.registerSimpleBlockItem("warped_roofing", GotModBlocks.WARPED_ROOFING);
    public static final DeferredItem<BlockItem> WARPED_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("warped_roofing_slab", GotModBlocks.WARPED_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> WARPED_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("warped_roofing_stairs", GotModBlocks.WARPED_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> WARPED_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("warped_roofing_wall", GotModBlocks.WARPED_ROOFING_WALL);

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
    public static final DeferredItem<Item> GRANITE_ROCK = block(GotModBlocks.GREY_GRANITE_ROCK);
    public static final DeferredItem<Item> GRANITE_BRICK = block(GotModBlocks.GREY_GRANITE_BRICK);
    public static final DeferredItem<Item> CRACKED_GRANITE_BRICK = block(GotModBlocks.CRACKED_GREY_GRANITE_BRICK);
    public static final DeferredItem<Item> MOSSY_GRANITE_BRICK = block(GotModBlocks.MOSSY_GREY_GRANITE_BRICK);
    public static final DeferredItem<Item> GRANITE_COBBLESTONE = block(GotModBlocks.GREY_GRANITE_COBBLESTONE);
    public static final DeferredItem<Item> MOSSY_GRANITE_COBBLESTONE = block(GotModBlocks.MOSSY_GREY_GRANITE_COBBLESTONE);
    public static final DeferredItem<Item> SMOOTH_GRANITE_ROCK = block(GotModBlocks.SMOOTH_GREY_GRANITE_ROCK);
    public static final DeferredItem<Item> GRANITE_PILLAR = block(GotModBlocks.GREY_GRANITE_PILLAR);
    public static final DeferredItem<Item> GRANITE_ROCK_SLAB = block(GotModBlocks.GREY_GRANITE_ROCK_SLAB);
    public static final DeferredItem<Item> GRANITE_ROCK_STAIRS = block(GotModBlocks.GREY_GRANITE_ROCK_STAIRS);
    public static final DeferredItem<Item> GRANITE_ROCK_WALL = block(GotModBlocks.GREY_GRANITE_ROCK_WALL);
    public static final DeferredItem<Item> GRANITE_ROCK_BUTTON = block(GotModBlocks.GREY_GRANITE_ROCK_BUTTON);
    public static final DeferredItem<Item> GRANITE_ROCK_PRESSURE_PLATE = block(GotModBlocks.GREY_GRANITE_ROCK_PRESSURE_PLATE);
    public static final DeferredItem<Item> GRANITE_BRICK_SLAB = block(GotModBlocks.GREY_GRANITE_BRICK_SLAB);
    public static final DeferredItem<Item> GRANITE_BRICK_STAIRS = block(GotModBlocks.GREY_GRANITE_BRICK_STAIRS);
    public static final DeferredItem<Item> GRANITE_BRICK_WALL = block(GotModBlocks.GREY_GRANITE_BRICK_WALL);
    public static final DeferredItem<Item> CRACKED_GRANITE_BRICK_SLAB = block(GotModBlocks.CRACKED_GREY_GRANITE_BRICK_SLAB);
    public static final DeferredItem<Item> CRACKED_GRANITE_BRICK_STAIRS = block(GotModBlocks.CRACKED_GREY_GRANITE_BRICK_STAIRS);
    public static final DeferredItem<Item> CRACKED_GRANITE_BRICK_WALL = block(GotModBlocks.CRACKED_GREY_GRANITE_BRICK_WALL);
    public static final DeferredItem<Item> MOSSY_GRANITE_BRICK_SLAB = block(GotModBlocks.MOSSY_GREY_GRANITE_BRICK_SLAB);
    public static final DeferredItem<Item> MOSSY_GRANITE_BRICK_STAIRS = block(GotModBlocks.MOSSY_GREY_GRANITE_BRICK_STAIRS);
    public static final DeferredItem<Item> MOSSY_GRANITE_BRICK_WALL = block(GotModBlocks.MOSSY_GREY_GRANITE_BRICK_WALL);
    public static final DeferredItem<Item> GRANITE_COBBLESTONE_SLAB = block(GotModBlocks.GREY_GRANITE_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> GRANITE_COBBLESTONE_STAIRS = block(GotModBlocks.GREY_GRANITE_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> GRANITE_COBBLESTONE_WALL = block(GotModBlocks.GREY_GRANITE_COBBLESTONE_WALL);
    public static final DeferredItem<Item> MOSSY_GRANITE_COBBLESTONE_SLAB = block(GotModBlocks.MOSSY_GREY_GRANITE_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> MOSSY_GRANITE_COBBLESTONE_STAIRS = block(GotModBlocks.MOSSY_GREY_GRANITE_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> MOSSY_GRANITE_COBBLESTONE_WALL = block(GotModBlocks.MOSSY_GREY_GRANITE_COBBLESTONE_WALL);
    public static final DeferredItem<Item> SMOOTH_GREY_GRANITE_ROCK_SLAB = block(GotModBlocks.SMOOTH_GREY_GRANITE_ROCK_SLAB);
    public static final DeferredItem<Item> SMOOTH_GREY_GRANITE_ROCK_STAIRS = block(GotModBlocks.SMOOTH_GREY_GRANITE_ROCK_STAIRS);
    public static final DeferredItem<Item> SMOOTH_GREY_GRANITE_ROCK_WALL = block(GotModBlocks.SMOOTH_GREY_GRANITE_ROCK_WALL);

    // ── Flint items ──────────────────────────────────────────────────────
    public static final DeferredItem<Item> FLINT_ROCK = block(GotModBlocks.FLINT_ROCK);
    public static final DeferredItem<Item> FLINT_BRICK = block(GotModBlocks.FLINT_BRICK);
    public static final DeferredItem<Item> CRACKED_FLINT_BRICK = block(GotModBlocks.CRACKED_FLINT_BRICK);
    public static final DeferredItem<Item> MOSSY_FLINT_BRICK = block(GotModBlocks.MOSSY_FLINT_BRICK);
    public static final DeferredItem<Item> FLINT_ROCK_SLAB = block(GotModBlocks.FLINT_ROCK_SLAB);
    public static final DeferredItem<Item> FLINT_ROCK_STAIRS = block(GotModBlocks.FLINT_ROCK_STAIRS);
    public static final DeferredItem<Item> FLINT_ROCK_WALL = block(GotModBlocks.FLINT_ROCK_WALL);
    public static final DeferredItem<Item> FLINT_ROCK_BUTTON = block(GotModBlocks.FLINT_ROCK_BUTTON);
    public static final DeferredItem<Item> FLINT_ROCK_PRESSURE_PLATE = block(GotModBlocks.FLINT_ROCK_PRESSURE_PLATE);
    public static final DeferredItem<Item> FLINT_BRICK_SLAB = block(GotModBlocks.FLINT_BRICK_SLAB);
    public static final DeferredItem<Item> FLINT_BRICK_STAIRS = block(GotModBlocks.FLINT_BRICK_STAIRS);
    public static final DeferredItem<Item> FLINT_BRICK_WALL = block(GotModBlocks.FLINT_BRICK_WALL);
    public static final DeferredItem<Item> CRACKED_FLINT_BRICK_SLAB = block(GotModBlocks.CRACKED_FLINT_BRICK_SLAB);
    public static final DeferredItem<Item> CRACKED_FLINT_BRICK_STAIRS = block(GotModBlocks.CRACKED_FLINT_BRICK_STAIRS);
    public static final DeferredItem<Item> CRACKED_FLINT_BRICK_WALL = block(GotModBlocks.CRACKED_FLINT_BRICK_WALL);
    public static final DeferredItem<Item> MOSSY_FLINT_BRICK_SLAB = block(GotModBlocks.MOSSY_FLINT_BRICK_SLAB);
    public static final DeferredItem<Item> MOSSY_FLINT_BRICK_STAIRS = block(GotModBlocks.MOSSY_FLINT_BRICK_STAIRS);
    public static final DeferredItem<Item> MOSSY_FLINT_BRICK_WALL = block(GotModBlocks.MOSSY_FLINT_BRICK_WALL);

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
    public static final DeferredItem<Item> SLATE_SHINGLES_STAIRS = block(GotModBlocks.SLATE_SHINGLES_STAIRS);
    public static final DeferredItem<Item> SLATE_SHINGLES_WALL = block(GotModBlocks.SLATE_SHINGLES_WALL);
    public static final DeferredItem<Item> SLATE_SHINGLES_SLAB = block(GotModBlocks.SLATE_SHINGLES_SLAB);

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

    // ── Thatch block items ────────────────────────────────────────────────
    public static final DeferredItem<Item> LIGHT_THATCH        = block(GotModBlocks.LIGHT_THATCH);
    public static final DeferredItem<Item> LIGHT_THATCH_SLAB   = block(GotModBlocks.LIGHT_THATCH_SLAB);
    public static final DeferredItem<Item> LIGHT_THATCH_STAIRS = block(GotModBlocks.LIGHT_THATCH_STAIRS);
    public static final DeferredItem<Item> LIGHT_THATCH_WALL   = block(GotModBlocks.LIGHT_THATCH_WALL);
    public static final DeferredItem<Item> DARK_THATCH         = block(GotModBlocks.DARK_THATCH);
    public static final DeferredItem<Item> DARK_THATCH_SLAB    = block(GotModBlocks.DARK_THATCH_SLAB);
    public static final DeferredItem<Item> DARK_THATCH_STAIRS  = block(GotModBlocks.DARK_THATCH_STAIRS);
    public static final DeferredItem<Item> DARK_THATCH_WALL    = block(GotModBlocks.DARK_THATCH_WALL);

    // ── Ore block items ───────────────────────────────────────────────────
    public static final DeferredItem<Item> DRAGONGLASS_ORE    = block(GotModBlocks.DRAGONGLASS_ORE);
    public static final DeferredItem<Item> OPAL_ORE           = block(GotModBlocks.OPAL_ORE);
    public static final DeferredItem<Item> RUBY_ORE           = block(GotModBlocks.RUBY_ORE);
    public static final DeferredItem<Item> SAPPHIRE_ORE       = block(GotModBlocks.SAPPHIRE_ORE);
    public static final DeferredItem<Item> SILVER_ORE         = block(GotModBlocks.SILVER_ORE);
    public static final DeferredItem<Item> AMETHYST_ORE       = block(GotModBlocks.AMETHYST_ORE);
    public static final DeferredItem<Item> TIN_ORE            = block(GotModBlocks.TIN_ORE);
    public static final DeferredItem<Item> TOPAZ_ORE          = block(GotModBlocks.TOPAZ_ORE);
    public static final DeferredItem<Item> VALYRIAN_STEEL_ORE = block(GotModBlocks.VALYRIAN_STEEL_ORE) /* valyrian_ore */;
    public static final DeferredItem<Item> COBALT_ORE         = block(GotModBlocks.COBALT_ORE);
    public static final DeferredItem<Item> LEAD_ORE           = block(GotModBlocks.LEAD_ORE);
    public static final DeferredItem<Item> PLATINUM_ORE       = block(GotModBlocks.PLATINUM_ORE);
    public static final DeferredItem<Item> ZINC_ORE           = block(GotModBlocks.ZINC_ORE);

    // ── Deepslate ore block items ────────────────────────────────────────
    public static final DeferredItem<Item> DEEPSLATE_SILVER_ORE   = block(GotModBlocks.DEEPSLATE_SILVER_ORE);
    public static final DeferredItem<Item> DEEPSLATE_AMETHYST_ORE = block(GotModBlocks.DEEPSLATE_AMETHYST_ORE);
    public static final DeferredItem<Item> DEEPSLATE_TIN_ORE      = block(GotModBlocks.DEEPSLATE_TIN_ORE);
    public static final DeferredItem<Item> DEEPSLATE_COBALT_ORE   = block(GotModBlocks.DEEPSLATE_COBALT_ORE);
    public static final DeferredItem<Item> DEEPSLATE_LEAD_ORE     = block(GotModBlocks.DEEPSLATE_LEAD_ORE);
    public static final DeferredItem<Item> DEEPSLATE_PLATINUM_ORE = block(GotModBlocks.DEEPSLATE_PLATINUM_ORE);
    public static final DeferredItem<Item> DEEPSLATE_ZINC_ORE     = block(GotModBlocks.DEEPSLATE_ZINC_ORE);

    // ── Deepslate gem ore block items ────────────────────────────────────
    public static final DeferredItem<Item> DEEPSLATE_OPAL_ORE       = block(GotModBlocks.DEEPSLATE_OPAL_ORE);
    public static final DeferredItem<Item> DEEPSLATE_RUBY_ORE       = block(GotModBlocks.DEEPSLATE_RUBY_ORE);
    public static final DeferredItem<Item> DEEPSLATE_SAPPHIRE_ORE   = block(GotModBlocks.DEEPSLATE_SAPPHIRE_ORE);
    public static final DeferredItem<Item> DEEPSLATE_TOPAZ_ORE      = block(GotModBlocks.DEEPSLATE_TOPAZ_ORE);
    public static final DeferredItem<Item> DEEPSLATE_BERYL_ORE      = block(GotModBlocks.DEEPSLATE_BERYL_ORE);
    public static final DeferredItem<Item> DEEPSLATE_BLOODSTONE_ORE = block(GotModBlocks.DEEPSLATE_BLOODSTONE_ORE);
    public static final DeferredItem<Item> DEEPSLATE_CARNELIAN_ORE  = block(GotModBlocks.DEEPSLATE_CARNELIAN_ORE);
    public static final DeferredItem<Item> DEEPSLATE_CHALCEDONY_ORE = block(GotModBlocks.DEEPSLATE_CHALCEDONY_ORE);
    public static final DeferredItem<Item> DEEPSLATE_GARNET_ORE     = block(GotModBlocks.DEEPSLATE_GARNET_ORE);
    public static final DeferredItem<Item> DEEPSLATE_JADE_ORE       = block(GotModBlocks.DEEPSLATE_JADE_ORE);
    public static final DeferredItem<Item> DEEPSLATE_JASPER_ORE     = block(GotModBlocks.DEEPSLATE_JASPER_ORE);
    public static final DeferredItem<Item> DEEPSLATE_MALACHITE_ORE  = block(GotModBlocks.DEEPSLATE_MALACHITE_ORE);
    public static final DeferredItem<Item> DEEPSLATE_MOONSTONE_ORE  = block(GotModBlocks.DEEPSLATE_MOONSTONE_ORE);
    public static final DeferredItem<Item> DEEPSLATE_ONYX_ORE       = block(GotModBlocks.DEEPSLATE_ONYX_ORE);
    public static final DeferredItem<Item> DEEPSLATE_TIGERS_EYE_ORE = block(GotModBlocks.DEEPSLATE_TIGERS_EYE_ORE);
    public static final DeferredItem<Item> DEEPSLATE_TOURMALINE_ORE = block(GotModBlocks.DEEPSLATE_TOURMALINE_ORE);

    // ── Raw metal storage block items ────────────────────────────────────
    public static final DeferredItem<Item> RAW_SILVER_BLOCK   = block(GotModBlocks.RAW_SILVER_BLOCK);
    public static final DeferredItem<Item> RAW_TIN_BLOCK      = block(GotModBlocks.RAW_TIN_BLOCK);
    public static final DeferredItem<Item> PATH_BLOCK         = block(GotModBlocks.PATH_BLOCK);
    public static final DeferredItem<Item> COBBLED_PATH_BLOCK = block(GotModBlocks.COBBLED_PATH_BLOCK);
    public static final DeferredItem<Item> RAW_COBALT_BLOCK   = block(GotModBlocks.RAW_COBALT_BLOCK);
    public static final DeferredItem<Item> RAW_LEAD_BLOCK     = block(GotModBlocks.RAW_LEAD_BLOCK);
    public static final DeferredItem<Item> RAW_PLATINUM_BLOCK = block(GotModBlocks.RAW_PLATINUM_BLOCK);
    public static final DeferredItem<Item> RAW_ZINC_BLOCK     = block(GotModBlocks.RAW_ZINC_BLOCK);

    // ── Gemstones ─────────────────────────────────────────────────────────
    public static final DeferredItem<Item> AMBER           = simple("amber");
    public static final DeferredItem<Item> AMETHYST        = simple("amethyst");
    public static final DeferredItem<Item> DRAGONGLASS_SHARD = simple("dragonglass_shard");
    public static final DeferredItem<Item> OPAL            = simple("opal");
    public static final DeferredItem<Item> RUBY            = simple("ruby");
    public static final DeferredItem<Item> SAPPHIRE        = simple("sapphire");
    public static final DeferredItem<Item> TOPAZ           = simple("topaz");

    // ── Gem storage blocks (existing) ─────────────────────────────────────
    public static final DeferredItem<Item> OPAL_BLOCK      = block(GotModBlocks.OPAL_BLOCK);
    public static final DeferredItem<Item> RUBY_BLOCK      = block(GotModBlocks.RUBY_BLOCK);
    public static final DeferredItem<Item> SAPPHIRE_BLOCK  = block(GotModBlocks.SAPPHIRE_BLOCK);
    public static final DeferredItem<Item> TOPAZ_BLOCK     = block(GotModBlocks.TOPAZ_BLOCK);

    // ── New mineral gemstones ──────────────────────────────────────────────
    public static final DeferredItem<Item> BERYL            = simple("beryl");
    public static final DeferredItem<Item> BERYL_ORE        = block(GotModBlocks.BERYL_ORE);
    public static final DeferredItem<Item> BERYL_BLOCK      = block(GotModBlocks.BERYL_BLOCK);
    public static final DeferredItem<Item> BLOODSTONE       = simple("bloodstone");
    public static final DeferredItem<Item> BLOODSTONE_ORE   = block(GotModBlocks.BLOODSTONE_ORE);
    public static final DeferredItem<Item> BLOODSTONE_BLOCK = block(GotModBlocks.BLOODSTONE_BLOCK);
    public static final DeferredItem<Item> CARNELIAN        = simple("carnelian");
    public static final DeferredItem<Item> CARNELIAN_ORE    = block(GotModBlocks.CARNELIAN_ORE);
    public static final DeferredItem<Item> CARNELIAN_BLOCK  = block(GotModBlocks.CARNELIAN_BLOCK);
    public static final DeferredItem<Item> CHALCEDONY       = simple("chalcedony");
    public static final DeferredItem<Item> CHALCEDONY_ORE   = block(GotModBlocks.CHALCEDONY_ORE);
    public static final DeferredItem<Item> CHALCEDONY_BLOCK = block(GotModBlocks.CHALCEDONY_BLOCK);
    public static final DeferredItem<Item> GARNET           = simple("garnet");
    public static final DeferredItem<Item> GARNET_ORE       = block(GotModBlocks.GARNET_ORE);
    public static final DeferredItem<Item> GARNET_BLOCK     = block(GotModBlocks.GARNET_BLOCK);
    public static final DeferredItem<Item> JADE             = simple("jade");
    public static final DeferredItem<Item> JADE_ORE         = block(GotModBlocks.JADE_ORE);
    public static final DeferredItem<Item> JADE_BLOCK       = block(GotModBlocks.JADE_BLOCK);
    public static final DeferredItem<Item> JASPER           = simple("jasper");
    public static final DeferredItem<Item> JASPER_ORE       = block(GotModBlocks.JASPER_ORE);
    public static final DeferredItem<Item> JASPER_BLOCK     = block(GotModBlocks.JASPER_BLOCK);
    public static final DeferredItem<Item> MALACHITE        = simple("malachite");
    public static final DeferredItem<Item> MALACHITE_ORE    = block(GotModBlocks.MALACHITE_ORE);
    public static final DeferredItem<Item> MALACHITE_BLOCK  = block(GotModBlocks.MALACHITE_BLOCK);
    public static final DeferredItem<Item> MOONSTONE        = simple("moonstone");
    public static final DeferredItem<Item> MOONSTONE_ORE    = block(GotModBlocks.MOONSTONE_ORE);
    public static final DeferredItem<Item> MOONSTONE_BLOCK  = block(GotModBlocks.MOONSTONE_BLOCK);
    public static final DeferredItem<Item> ONYX             = simple("onyx");
    public static final DeferredItem<Item> ONYX_ORE         = block(GotModBlocks.ONYX_ORE);
    public static final DeferredItem<Item> ONYX_BLOCK       = block(GotModBlocks.ONYX_BLOCK);
    public static final DeferredItem<Item> TIGERS_EYE       = simple("tigers_eye");
    public static final DeferredItem<Item> TIGERS_EYE_ORE   = block(GotModBlocks.TIGERS_EYE_ORE);
    public static final DeferredItem<Item> TIGERS_EYE_BLOCK = block(GotModBlocks.TIGERS_EYE_BLOCK);
    public static final DeferredItem<Item> TOURMALINE       = simple("tourmaline");
    public static final DeferredItem<Item> TOURMALINE_ORE   = block(GotModBlocks.TOURMALINE_ORE);
    public static final DeferredItem<Item> TOURMALINE_BLOCK = block(GotModBlocks.TOURMALINE_BLOCK);

    // ── Organic materials (no ore) ────────────────────────────────────────
    public static final DeferredItem<Item> IVORY            = simple("ivory");
    public static final DeferredItem<Item> JET              = simple("jet");
    public static final DeferredItem<Item> PEARL            = simple("pearl");

    // ── Raw ores ──────────────────────────────────────────────────────────
    public static final DeferredItem<Item> RAW_SILVER          = simple("raw_silver");
    public static final DeferredItem<Item> RAW_TIN             = simple("raw_tin");
    public static final DeferredItem<Item> RAW_VALYRIAN_STEEL  = simple("raw_valyrian_steel");
    public static final DeferredItem<Item> RAW_COBALT          = simple("raw_cobalt");
    public static final DeferredItem<Item> RAW_LEAD            = simple("raw_lead");
    public static final DeferredItem<Item> RAW_PLATINUM        = simple("raw_platinum");
    public static final DeferredItem<Item> RAW_ZINC            = simple("raw_zinc");

    // ── Ingots ────────────────────────────────────────────────────────────
    public static final DeferredItem<Item> SILVER_INGOT         = simple("silver_ingot");
    public static final DeferredItem<Item> TIN_INGOT            = simple("tin_ingot");
    public static final DeferredItem<Item> BRONZE_INGOT         = simple("bronze_ingot");
    public static final DeferredItem<Item> STEEL_INGOT          = simple("steel_ingot");
    public static final DeferredItem<Item> VALYRIAN_STEEL_INGOT = simple("valyrian_steel_ingot");
    public static final DeferredItem<Item> COBALT_INGOT         = simple("cobalt_ingot");
    public static final DeferredItem<Item> LEAD_INGOT           = simple("lead_ingot");
    public static final DeferredItem<Item> PLATINUM_INGOT       = simple("platinum_ingot");
    public static final DeferredItem<Item> ZINC_INGOT           = simple("zinc_ingot");


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

    // ── Wood Slabs & Stairs — GOT trees ─────────────────────────────────────────
    public static final DeferredItem<Item> WEIRWOOD_WOOD_SLAB            = block(GotModBlocks.WEIRWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> WEIRWOOD_WOOD_STAIRS          = block(GotModBlocks.WEIRWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_WEIRWOOD_WOOD_SLAB   = block(GotModBlocks.STRIPPED_WEIRWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_WEIRWOOD_WOOD_STAIRS = block(GotModBlocks.STRIPPED_WEIRWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> ASPEN_WOOD_SLAB            = block(GotModBlocks.ASPEN_WOOD_SLAB);
    public static final DeferredItem<Item> ASPEN_WOOD_STAIRS          = block(GotModBlocks.ASPEN_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_ASPEN_WOOD_SLAB   = block(GotModBlocks.STRIPPED_ASPEN_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_ASPEN_WOOD_STAIRS = block(GotModBlocks.STRIPPED_ASPEN_WOOD_STAIRS);
    public static final DeferredItem<Item> ALDER_WOOD_SLAB            = block(GotModBlocks.ALDER_WOOD_SLAB);
    public static final DeferredItem<Item> ALDER_WOOD_STAIRS          = block(GotModBlocks.ALDER_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_ALDER_WOOD_SLAB   = block(GotModBlocks.STRIPPED_ALDER_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_ALDER_WOOD_STAIRS = block(GotModBlocks.STRIPPED_ALDER_WOOD_STAIRS);
    public static final DeferredItem<Item> PINE_WOOD_SLAB            = block(GotModBlocks.PINE_WOOD_SLAB);
    public static final DeferredItem<Item> PINE_WOOD_STAIRS          = block(GotModBlocks.PINE_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_PINE_WOOD_SLAB   = block(GotModBlocks.STRIPPED_PINE_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_PINE_WOOD_STAIRS = block(GotModBlocks.STRIPPED_PINE_WOOD_STAIRS);
    public static final DeferredItem<Item> FIR_WOOD_SLAB            = block(GotModBlocks.FIR_WOOD_SLAB);
    public static final DeferredItem<Item> FIR_WOOD_STAIRS          = block(GotModBlocks.FIR_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_FIR_WOOD_SLAB   = block(GotModBlocks.STRIPPED_FIR_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_FIR_WOOD_STAIRS = block(GotModBlocks.STRIPPED_FIR_WOOD_STAIRS);
    public static final DeferredItem<Item> SENTINAL_WOOD_SLAB            = block(GotModBlocks.SENTINAL_WOOD_SLAB);
    public static final DeferredItem<Item> SENTINAL_WOOD_STAIRS          = block(GotModBlocks.SENTINAL_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_SENTINAL_WOOD_SLAB   = block(GotModBlocks.STRIPPED_SENTINAL_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_SENTINAL_WOOD_STAIRS = block(GotModBlocks.STRIPPED_SENTINAL_WOOD_STAIRS);
    public static final DeferredItem<Item> IRONWOOD_WOOD_SLAB            = block(GotModBlocks.IRONWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> IRONWOOD_WOOD_STAIRS          = block(GotModBlocks.IRONWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_IRONWOOD_WOOD_SLAB   = block(GotModBlocks.STRIPPED_IRONWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_IRONWOOD_WOOD_STAIRS = block(GotModBlocks.STRIPPED_IRONWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> BEECH_WOOD_SLAB            = block(GotModBlocks.BEECH_WOOD_SLAB);
    public static final DeferredItem<Item> BEECH_WOOD_STAIRS          = block(GotModBlocks.BEECH_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_BEECH_WOOD_SLAB   = block(GotModBlocks.STRIPPED_BEECH_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_BEECH_WOOD_STAIRS = block(GotModBlocks.STRIPPED_BEECH_WOOD_STAIRS);
    public static final DeferredItem<Item> SOLDIER_PINE_WOOD_SLAB            = block(GotModBlocks.SOLDIER_PINE_WOOD_SLAB);
    public static final DeferredItem<Item> SOLDIER_PINE_WOOD_STAIRS          = block(GotModBlocks.SOLDIER_PINE_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_SOLDIER_PINE_WOOD_SLAB   = block(GotModBlocks.STRIPPED_SOLDIER_PINE_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_SOLDIER_PINE_WOOD_STAIRS = block(GotModBlocks.STRIPPED_SOLDIER_PINE_WOOD_STAIRS);
    public static final DeferredItem<Item> ASH_WOOD_SLAB            = block(GotModBlocks.ASH_WOOD_SLAB);
    public static final DeferredItem<Item> ASH_WOOD_STAIRS          = block(GotModBlocks.ASH_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_ASH_WOOD_SLAB   = block(GotModBlocks.STRIPPED_ASH_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_ASH_WOOD_STAIRS = block(GotModBlocks.STRIPPED_ASH_WOOD_STAIRS);
    public static final DeferredItem<Item> HAWTHORN_WOOD_SLAB            = block(GotModBlocks.HAWTHORN_WOOD_SLAB);
    public static final DeferredItem<Item> HAWTHORN_WOOD_STAIRS          = block(GotModBlocks.HAWTHORN_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_HAWTHORN_WOOD_SLAB   = block(GotModBlocks.STRIPPED_HAWTHORN_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_HAWTHORN_WOOD_STAIRS = block(GotModBlocks.STRIPPED_HAWTHORN_WOOD_STAIRS);
    public static final DeferredItem<Item> BLACKBARK_WOOD_SLAB            = block(GotModBlocks.BLACKBARK_WOOD_SLAB);
    public static final DeferredItem<Item> BLACKBARK_WOOD_STAIRS          = block(GotModBlocks.BLACKBARK_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_BLACKBARK_WOOD_SLAB   = block(GotModBlocks.STRIPPED_BLACKBARK_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_BLACKBARK_WOOD_STAIRS = block(GotModBlocks.STRIPPED_BLACKBARK_WOOD_STAIRS);
    public static final DeferredItem<Item> BLOODWOOD_WOOD_SLAB            = block(GotModBlocks.BLOODWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> BLOODWOOD_WOOD_STAIRS          = block(GotModBlocks.BLOODWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_BLOODWOOD_WOOD_SLAB   = block(GotModBlocks.STRIPPED_BLOODWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_BLOODWOOD_WOOD_STAIRS = block(GotModBlocks.STRIPPED_BLOODWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> BLUE_MAHOE_WOOD_SLAB            = block(GotModBlocks.BLUE_MAHOE_WOOD_SLAB);
    public static final DeferredItem<Item> BLUE_MAHOE_WOOD_STAIRS          = block(GotModBlocks.BLUE_MAHOE_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_BLUE_MAHOE_WOOD_SLAB   = block(GotModBlocks.STRIPPED_BLUE_MAHOE_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_BLUE_MAHOE_WOOD_STAIRS = block(GotModBlocks.STRIPPED_BLUE_MAHOE_WOOD_STAIRS);
    public static final DeferredItem<Item> COTTONWOOD_WOOD_SLAB            = block(GotModBlocks.COTTONWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> COTTONWOOD_WOOD_STAIRS          = block(GotModBlocks.COTTONWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_COTTONWOOD_WOOD_SLAB   = block(GotModBlocks.STRIPPED_COTTONWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_COTTONWOOD_WOOD_STAIRS = block(GotModBlocks.STRIPPED_COTTONWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_WOOD_SLAB            = block(GotModBlocks.BLACK_COTTONWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_WOOD_STAIRS          = block(GotModBlocks.BLACK_COTTONWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_BLACK_COTTONWOOD_WOOD_SLAB   = block(GotModBlocks.STRIPPED_BLACK_COTTONWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_BLACK_COTTONWOOD_WOOD_STAIRS = block(GotModBlocks.STRIPPED_BLACK_COTTONWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> CINNAMON_WOOD_SLAB            = block(GotModBlocks.CINNAMON_WOOD_SLAB);
    public static final DeferredItem<Item> CINNAMON_WOOD_STAIRS          = block(GotModBlocks.CINNAMON_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_CINNAMON_WOOD_SLAB   = block(GotModBlocks.STRIPPED_CINNAMON_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_CINNAMON_WOOD_STAIRS = block(GotModBlocks.STRIPPED_CINNAMON_WOOD_STAIRS);
    public static final DeferredItem<Item> CLOVE_WOOD_SLAB            = block(GotModBlocks.CLOVE_WOOD_SLAB);
    public static final DeferredItem<Item> CLOVE_WOOD_STAIRS          = block(GotModBlocks.CLOVE_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_CLOVE_WOOD_SLAB   = block(GotModBlocks.STRIPPED_CLOVE_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_CLOVE_WOOD_STAIRS = block(GotModBlocks.STRIPPED_CLOVE_WOOD_STAIRS);
    public static final DeferredItem<Item> EBONY_WOOD_SLAB            = block(GotModBlocks.EBONY_WOOD_SLAB);
    public static final DeferredItem<Item> EBONY_WOOD_STAIRS          = block(GotModBlocks.EBONY_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_EBONY_WOOD_SLAB   = block(GotModBlocks.STRIPPED_EBONY_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_EBONY_WOOD_STAIRS = block(GotModBlocks.STRIPPED_EBONY_WOOD_STAIRS);
    public static final DeferredItem<Item> ELM_WOOD_SLAB            = block(GotModBlocks.ELM_WOOD_SLAB);
    public static final DeferredItem<Item> ELM_WOOD_STAIRS          = block(GotModBlocks.ELM_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_ELM_WOOD_SLAB   = block(GotModBlocks.STRIPPED_ELM_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_ELM_WOOD_STAIRS = block(GotModBlocks.STRIPPED_ELM_WOOD_STAIRS);
    public static final DeferredItem<Item> CEDAR_WOOD_SLAB            = block(GotModBlocks.CEDAR_WOOD_SLAB);
    public static final DeferredItem<Item> CEDAR_WOOD_STAIRS          = block(GotModBlocks.CEDAR_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_CEDAR_WOOD_SLAB   = block(GotModBlocks.STRIPPED_CEDAR_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_CEDAR_WOOD_STAIRS = block(GotModBlocks.STRIPPED_CEDAR_WOOD_STAIRS);
    public static final DeferredItem<Item> APPLE_WOOD_SLAB            = block(GotModBlocks.APPLE_WOOD_SLAB);
    public static final DeferredItem<Item> APPLE_WOOD_STAIRS          = block(GotModBlocks.APPLE_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_APPLE_WOOD_SLAB   = block(GotModBlocks.STRIPPED_APPLE_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_APPLE_WOOD_STAIRS = block(GotModBlocks.STRIPPED_APPLE_WOOD_STAIRS);
    public static final DeferredItem<Item> GOLDENHEART_WOOD_SLAB            = block(GotModBlocks.GOLDENHEART_WOOD_SLAB);
    public static final DeferredItem<Item> GOLDENHEART_WOOD_STAIRS          = block(GotModBlocks.GOLDENHEART_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_GOLDENHEART_WOOD_SLAB   = block(GotModBlocks.STRIPPED_GOLDENHEART_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_GOLDENHEART_WOOD_STAIRS = block(GotModBlocks.STRIPPED_GOLDENHEART_WOOD_STAIRS);
    public static final DeferredItem<Item> LINDEN_WOOD_SLAB            = block(GotModBlocks.LINDEN_WOOD_SLAB);
    public static final DeferredItem<Item> LINDEN_WOOD_STAIRS          = block(GotModBlocks.LINDEN_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_LINDEN_WOOD_SLAB   = block(GotModBlocks.STRIPPED_LINDEN_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_LINDEN_WOOD_STAIRS = block(GotModBlocks.STRIPPED_LINDEN_WOOD_STAIRS);
    public static final DeferredItem<Item> MAHOGANY_WOOD_SLAB            = block(GotModBlocks.MAHOGANY_WOOD_SLAB);
    public static final DeferredItem<Item> MAHOGANY_WOOD_STAIRS          = block(GotModBlocks.MAHOGANY_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_MAHOGANY_WOOD_SLAB   = block(GotModBlocks.STRIPPED_MAHOGANY_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_MAHOGANY_WOOD_STAIRS = block(GotModBlocks.STRIPPED_MAHOGANY_WOOD_STAIRS);
    public static final DeferredItem<Item> MAPLE_WOOD_SLAB            = block(GotModBlocks.MAPLE_WOOD_SLAB);
    public static final DeferredItem<Item> MAPLE_WOOD_STAIRS          = block(GotModBlocks.MAPLE_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_MAPLE_WOOD_SLAB   = block(GotModBlocks.STRIPPED_MAPLE_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_MAPLE_WOOD_STAIRS = block(GotModBlocks.STRIPPED_MAPLE_WOOD_STAIRS);
    public static final DeferredItem<Item> MYRRH_WOOD_SLAB            = block(GotModBlocks.MYRRH_WOOD_SLAB);
    public static final DeferredItem<Item> MYRRH_WOOD_STAIRS          = block(GotModBlocks.MYRRH_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_MYRRH_WOOD_SLAB   = block(GotModBlocks.STRIPPED_MYRRH_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_MYRRH_WOOD_STAIRS = block(GotModBlocks.STRIPPED_MYRRH_WOOD_STAIRS);
    public static final DeferredItem<Item> REDWOOD_WOOD_SLAB            = block(GotModBlocks.REDWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> REDWOOD_WOOD_STAIRS          = block(GotModBlocks.REDWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_REDWOOD_WOOD_SLAB   = block(GotModBlocks.STRIPPED_REDWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_REDWOOD_WOOD_STAIRS = block(GotModBlocks.STRIPPED_REDWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> CHESTNUT_WOOD_SLAB            = block(GotModBlocks.CHESTNUT_WOOD_SLAB);
    public static final DeferredItem<Item> CHESTNUT_WOOD_STAIRS          = block(GotModBlocks.CHESTNUT_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_CHESTNUT_WOOD_SLAB   = block(GotModBlocks.STRIPPED_CHESTNUT_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_CHESTNUT_WOOD_STAIRS = block(GotModBlocks.STRIPPED_CHESTNUT_WOOD_STAIRS);
    public static final DeferredItem<Item> WILLOW_WOOD_SLAB            = block(GotModBlocks.WILLOW_WOOD_SLAB);
    public static final DeferredItem<Item> WILLOW_WOOD_STAIRS          = block(GotModBlocks.WILLOW_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_WILLOW_WOOD_SLAB   = block(GotModBlocks.STRIPPED_WILLOW_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_WILLOW_WOOD_STAIRS = block(GotModBlocks.STRIPPED_WILLOW_WOOD_STAIRS);
    public static final DeferredItem<Item> WORMTREE_WOOD_SLAB            = block(GotModBlocks.WORMTREE_WOOD_SLAB);
    public static final DeferredItem<Item> WORMTREE_WOOD_STAIRS          = block(GotModBlocks.WORMTREE_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_WORMTREE_WOOD_SLAB   = block(GotModBlocks.STRIPPED_WORMTREE_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_WORMTREE_WOOD_STAIRS = block(GotModBlocks.STRIPPED_WORMTREE_WOOD_STAIRS);
    public static final DeferredItem<Item> NIGHTWOOD_WOOD_SLAB            = block(GotModBlocks.NIGHTWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> NIGHTWOOD_WOOD_STAIRS          = block(GotModBlocks.NIGHTWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_NIGHTWOOD_WOOD_SLAB   = block(GotModBlocks.STRIPPED_NIGHTWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_NIGHTWOOD_WOOD_STAIRS = block(GotModBlocks.STRIPPED_NIGHTWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> PURPLEHEART_WOOD_SLAB            = block(GotModBlocks.PURPLEHEART_WOOD_SLAB);
    public static final DeferredItem<Item> PURPLEHEART_WOOD_STAIRS          = block(GotModBlocks.PURPLEHEART_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_PURPLEHEART_WOOD_SLAB   = block(GotModBlocks.STRIPPED_PURPLEHEART_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_PURPLEHEART_WOOD_STAIRS = block(GotModBlocks.STRIPPED_PURPLEHEART_WOOD_STAIRS);
    public static final DeferredItem<Item> TIGERWOOD_WOOD_SLAB            = block(GotModBlocks.TIGERWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> TIGERWOOD_WOOD_STAIRS          = block(GotModBlocks.TIGERWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_TIGERWOOD_WOOD_SLAB   = block(GotModBlocks.STRIPPED_TIGERWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_TIGERWOOD_WOOD_STAIRS = block(GotModBlocks.STRIPPED_TIGERWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> BURL_WOOD_SLAB            = block(GotModBlocks.BURL_WOOD_SLAB);
    public static final DeferredItem<Item> BURL_WOOD_STAIRS          = block(GotModBlocks.BURL_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_BURL_WOOD_SLAB   = block(GotModBlocks.STRIPPED_BURL_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_BURL_WOOD_STAIRS = block(GotModBlocks.STRIPPED_BURL_WOOD_STAIRS);
    public static final DeferredItem<Item> SANDALWOOD_WOOD_SLAB            = block(GotModBlocks.SANDALWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> SANDALWOOD_WOOD_STAIRS          = block(GotModBlocks.SANDALWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_SANDALWOOD_WOOD_SLAB   = block(GotModBlocks.STRIPPED_SANDALWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_SANDALWOOD_WOOD_STAIRS = block(GotModBlocks.STRIPPED_SANDALWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> SANDBEGGAR_WOOD_SLAB            = block(GotModBlocks.SANDBEGGAR_WOOD_SLAB);
    public static final DeferredItem<Item> SANDBEGGAR_WOOD_STAIRS          = block(GotModBlocks.SANDBEGGAR_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_SANDBEGGAR_WOOD_SLAB   = block(GotModBlocks.STRIPPED_SANDBEGGAR_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_SANDBEGGAR_WOOD_STAIRS = block(GotModBlocks.STRIPPED_SANDBEGGAR_WOOD_STAIRS);
    public static final DeferredItem<Item> APRICOT_WOOD_SLAB            = block(GotModBlocks.APRICOT_WOOD_SLAB);
    public static final DeferredItem<Item> APRICOT_WOOD_STAIRS          = block(GotModBlocks.APRICOT_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_APRICOT_WOOD_SLAB   = block(GotModBlocks.STRIPPED_APRICOT_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_APRICOT_WOOD_STAIRS = block(GotModBlocks.STRIPPED_APRICOT_WOOD_STAIRS);
    public static final DeferredItem<Item> BLACKTHORN_WOOD_SLAB            = block(GotModBlocks.BLACKTHORN_WOOD_SLAB);
    public static final DeferredItem<Item> BLACKTHORN_WOOD_STAIRS          = block(GotModBlocks.BLACKTHORN_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_BLACKTHORN_WOOD_SLAB   = block(GotModBlocks.STRIPPED_BLACKTHORN_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_BLACKTHORN_WOOD_STAIRS = block(GotModBlocks.STRIPPED_BLACKTHORN_WOOD_STAIRS);
    public static final DeferredItem<Item> RED_CHERRY_WOOD_SLAB            = block(GotModBlocks.RED_CHERRY_WOOD_SLAB);
    public static final DeferredItem<Item> RED_CHERRY_WOOD_STAIRS          = block(GotModBlocks.RED_CHERRY_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_RED_CHERRY_WOOD_SLAB   = block(GotModBlocks.STRIPPED_RED_CHERRY_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_RED_CHERRY_WOOD_STAIRS = block(GotModBlocks.STRIPPED_RED_CHERRY_WOOD_STAIRS);
    public static final DeferredItem<Item> BLACK_CHERRY_WOOD_SLAB            = block(GotModBlocks.BLACK_CHERRY_WOOD_SLAB);
    public static final DeferredItem<Item> BLACK_CHERRY_WOOD_STAIRS          = block(GotModBlocks.BLACK_CHERRY_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_BLACK_CHERRY_WOOD_SLAB   = block(GotModBlocks.STRIPPED_BLACK_CHERRY_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_BLACK_CHERRY_WOOD_STAIRS = block(GotModBlocks.STRIPPED_BLACK_CHERRY_WOOD_STAIRS);
    public static final DeferredItem<Item> WHITE_CHERRY_WOOD_SLAB            = block(GotModBlocks.WHITE_CHERRY_WOOD_SLAB);
    public static final DeferredItem<Item> WHITE_CHERRY_WOOD_STAIRS          = block(GotModBlocks.WHITE_CHERRY_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_WHITE_CHERRY_WOOD_SLAB   = block(GotModBlocks.STRIPPED_WHITE_CHERRY_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_WHITE_CHERRY_WOOD_STAIRS = block(GotModBlocks.STRIPPED_WHITE_CHERRY_WOOD_STAIRS);
    public static final DeferredItem<Item> CRABAPPLE_WOOD_SLAB            = block(GotModBlocks.CRABAPPLE_WOOD_SLAB);
    public static final DeferredItem<Item> CRABAPPLE_WOOD_STAIRS          = block(GotModBlocks.CRABAPPLE_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_CRABAPPLE_WOOD_SLAB   = block(GotModBlocks.STRIPPED_CRABAPPLE_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_CRABAPPLE_WOOD_STAIRS = block(GotModBlocks.STRIPPED_CRABAPPLE_WOOD_STAIRS);
    public static final DeferredItem<Item> DATE_PALM_WOOD_SLAB            = block(GotModBlocks.DATE_PALM_WOOD_SLAB);
    public static final DeferredItem<Item> DATE_PALM_WOOD_STAIRS          = block(GotModBlocks.DATE_PALM_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_DATE_PALM_WOOD_SLAB   = block(GotModBlocks.STRIPPED_DATE_PALM_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_DATE_PALM_WOOD_STAIRS = block(GotModBlocks.STRIPPED_DATE_PALM_WOOD_STAIRS);
    public static final DeferredItem<Item> FIG_WOOD_SLAB            = block(GotModBlocks.FIG_WOOD_SLAB);
    public static final DeferredItem<Item> FIG_WOOD_STAIRS          = block(GotModBlocks.FIG_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_FIG_WOOD_SLAB   = block(GotModBlocks.STRIPPED_FIG_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_FIG_WOOD_STAIRS = block(GotModBlocks.STRIPPED_FIG_WOOD_STAIRS);
    public static final DeferredItem<Item> LEMON_WOOD_SLAB            = block(GotModBlocks.LEMON_WOOD_SLAB);
    public static final DeferredItem<Item> LEMON_WOOD_STAIRS          = block(GotModBlocks.LEMON_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_LEMON_WOOD_SLAB   = block(GotModBlocks.STRIPPED_LEMON_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_LEMON_WOOD_STAIRS = block(GotModBlocks.STRIPPED_LEMON_WOOD_STAIRS);
    public static final DeferredItem<Item> LIME_WOOD_SLAB            = block(GotModBlocks.LIME_WOOD_SLAB);
    public static final DeferredItem<Item> LIME_WOOD_STAIRS          = block(GotModBlocks.LIME_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_LIME_WOOD_SLAB   = block(GotModBlocks.STRIPPED_LIME_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_LIME_WOOD_STAIRS = block(GotModBlocks.STRIPPED_LIME_WOOD_STAIRS);
    public static final DeferredItem<Item> OLIVE_WOOD_SLAB            = block(GotModBlocks.OLIVE_WOOD_SLAB);
    public static final DeferredItem<Item> OLIVE_WOOD_STAIRS          = block(GotModBlocks.OLIVE_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_OLIVE_WOOD_SLAB   = block(GotModBlocks.STRIPPED_OLIVE_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_OLIVE_WOOD_STAIRS = block(GotModBlocks.STRIPPED_OLIVE_WOOD_STAIRS);
    public static final DeferredItem<Item> ORANGE_WOOD_SLAB            = block(GotModBlocks.ORANGE_WOOD_SLAB);
    public static final DeferredItem<Item> ORANGE_WOOD_STAIRS          = block(GotModBlocks.ORANGE_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_ORANGE_WOOD_SLAB   = block(GotModBlocks.STRIPPED_ORANGE_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_ORANGE_WOOD_STAIRS = block(GotModBlocks.STRIPPED_ORANGE_WOOD_STAIRS);
    public static final DeferredItem<Item> ALMOND_WOOD_SLAB            = block(GotModBlocks.ALMOND_WOOD_SLAB);
    public static final DeferredItem<Item> ALMOND_WOOD_STAIRS          = block(GotModBlocks.ALMOND_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_ALMOND_WOOD_SLAB   = block(GotModBlocks.STRIPPED_ALMOND_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_ALMOND_WOOD_STAIRS = block(GotModBlocks.STRIPPED_ALMOND_WOOD_STAIRS);
    public static final DeferredItem<Item> HEMLOCK_WOOD_SLAB            = block(GotModBlocks.HEMLOCK_WOOD_SLAB);
    public static final DeferredItem<Item> HEMLOCK_WOOD_STAIRS          = block(GotModBlocks.HEMLOCK_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_HEMLOCK_WOOD_SLAB   = block(GotModBlocks.STRIPPED_HEMLOCK_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_HEMLOCK_WOOD_STAIRS = block(GotModBlocks.STRIPPED_HEMLOCK_WOOD_STAIRS);
    public static final DeferredItem<Item> NUTMEG_WOOD_SLAB            = block(GotModBlocks.NUTMEG_WOOD_SLAB);
    public static final DeferredItem<Item> NUTMEG_WOOD_STAIRS          = block(GotModBlocks.NUTMEG_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_NUTMEG_WOOD_SLAB   = block(GotModBlocks.STRIPPED_NUTMEG_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_NUTMEG_WOOD_STAIRS = block(GotModBlocks.STRIPPED_NUTMEG_WOOD_STAIRS);
    public static final DeferredItem<Item> PEACH_WOOD_SLAB            = block(GotModBlocks.PEACH_WOOD_SLAB);
    public static final DeferredItem<Item> PEACH_WOOD_STAIRS          = block(GotModBlocks.PEACH_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_PEACH_WOOD_SLAB   = block(GotModBlocks.STRIPPED_PEACH_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_PEACH_WOOD_STAIRS = block(GotModBlocks.STRIPPED_PEACH_WOOD_STAIRS);
    public static final DeferredItem<Item> PEAR_WOOD_SLAB            = block(GotModBlocks.PEAR_WOOD_SLAB);
    public static final DeferredItem<Item> PEAR_WOOD_STAIRS          = block(GotModBlocks.PEAR_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_PEAR_WOOD_SLAB   = block(GotModBlocks.STRIPPED_PEAR_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_PEAR_WOOD_STAIRS = block(GotModBlocks.STRIPPED_PEAR_WOOD_STAIRS);
    public static final DeferredItem<Item> PERSIMMON_WOOD_SLAB            = block(GotModBlocks.PERSIMMON_WOOD_SLAB);
    public static final DeferredItem<Item> PERSIMMON_WOOD_STAIRS          = block(GotModBlocks.PERSIMMON_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_PERSIMMON_WOOD_SLAB   = block(GotModBlocks.STRIPPED_PERSIMMON_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_PERSIMMON_WOOD_STAIRS = block(GotModBlocks.STRIPPED_PERSIMMON_WOOD_STAIRS);
    public static final DeferredItem<Item> PINK_IVORY_WOOD_SLAB            = block(GotModBlocks.PINK_IVORY_WOOD_SLAB);
    public static final DeferredItem<Item> PINK_IVORY_WOOD_STAIRS          = block(GotModBlocks.PINK_IVORY_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_PINK_IVORY_WOOD_SLAB   = block(GotModBlocks.STRIPPED_PINK_IVORY_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_PINK_IVORY_WOOD_STAIRS = block(GotModBlocks.STRIPPED_PINK_IVORY_WOOD_STAIRS);
    public static final DeferredItem<Item> PLUM_WOOD_SLAB            = block(GotModBlocks.PLUM_WOOD_SLAB);
    public static final DeferredItem<Item> PLUM_WOOD_STAIRS          = block(GotModBlocks.PLUM_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_PLUM_WOOD_SLAB   = block(GotModBlocks.STRIPPED_PLUM_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_PLUM_WOOD_STAIRS = block(GotModBlocks.STRIPPED_PLUM_WOOD_STAIRS);
    public static final DeferredItem<Item> POMEGRANATE_WOOD_SLAB            = block(GotModBlocks.POMEGRANATE_WOOD_SLAB);
    public static final DeferredItem<Item> POMEGRANATE_WOOD_STAIRS          = block(GotModBlocks.POMEGRANATE_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_POMEGRANATE_WOOD_SLAB   = block(GotModBlocks.STRIPPED_POMEGRANATE_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_POMEGRANATE_WOOD_STAIRS = block(GotModBlocks.STRIPPED_POMEGRANATE_WOOD_STAIRS);
    public static final DeferredItem<Item> PRUNE_WOOD_SLAB            = block(GotModBlocks.PRUNE_WOOD_SLAB);
    public static final DeferredItem<Item> PRUNE_WOOD_STAIRS          = block(GotModBlocks.PRUNE_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_PRUNE_WOOD_SLAB   = block(GotModBlocks.STRIPPED_PRUNE_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_PRUNE_WOOD_STAIRS = block(GotModBlocks.STRIPPED_PRUNE_WOOD_STAIRS);

    // ── Wood Slabs & Stairs — vanilla overworld trees ──────────────────────────
    public static final DeferredItem<Item> OAK_WOOD_SLAB            = block(GotModBlocks.OAK_WOOD_SLAB);
    public static final DeferredItem<Item> OAK_WOOD_STAIRS          = block(GotModBlocks.OAK_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_OAK_WOOD_SLAB   = block(GotModBlocks.STRIPPED_OAK_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_OAK_WOOD_STAIRS = block(GotModBlocks.STRIPPED_OAK_WOOD_STAIRS);
    public static final DeferredItem<Item> SPRUCE_WOOD_SLAB            = block(GotModBlocks.SPRUCE_WOOD_SLAB);
    public static final DeferredItem<Item> SPRUCE_WOOD_STAIRS          = block(GotModBlocks.SPRUCE_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_SPRUCE_WOOD_SLAB   = block(GotModBlocks.STRIPPED_SPRUCE_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_SPRUCE_WOOD_STAIRS = block(GotModBlocks.STRIPPED_SPRUCE_WOOD_STAIRS);
    public static final DeferredItem<Item> BIRCH_WOOD_SLAB            = block(GotModBlocks.BIRCH_WOOD_SLAB);
    public static final DeferredItem<Item> BIRCH_WOOD_STAIRS          = block(GotModBlocks.BIRCH_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_BIRCH_WOOD_SLAB   = block(GotModBlocks.STRIPPED_BIRCH_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_BIRCH_WOOD_STAIRS = block(GotModBlocks.STRIPPED_BIRCH_WOOD_STAIRS);
    public static final DeferredItem<Item> JUNGLE_WOOD_SLAB            = block(GotModBlocks.JUNGLE_WOOD_SLAB);
    public static final DeferredItem<Item> JUNGLE_WOOD_STAIRS          = block(GotModBlocks.JUNGLE_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_JUNGLE_WOOD_SLAB   = block(GotModBlocks.STRIPPED_JUNGLE_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_JUNGLE_WOOD_STAIRS = block(GotModBlocks.STRIPPED_JUNGLE_WOOD_STAIRS);
    public static final DeferredItem<Item> ACACIA_WOOD_SLAB            = block(GotModBlocks.ACACIA_WOOD_SLAB);
    public static final DeferredItem<Item> ACACIA_WOOD_STAIRS          = block(GotModBlocks.ACACIA_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_ACACIA_WOOD_SLAB   = block(GotModBlocks.STRIPPED_ACACIA_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_ACACIA_WOOD_STAIRS = block(GotModBlocks.STRIPPED_ACACIA_WOOD_STAIRS);
    public static final DeferredItem<Item> DARK_OAK_WOOD_SLAB            = block(GotModBlocks.DARK_OAK_WOOD_SLAB);
    public static final DeferredItem<Item> DARK_OAK_WOOD_STAIRS          = block(GotModBlocks.DARK_OAK_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_DARK_OAK_WOOD_SLAB   = block(GotModBlocks.STRIPPED_DARK_OAK_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_DARK_OAK_WOOD_STAIRS = block(GotModBlocks.STRIPPED_DARK_OAK_WOOD_STAIRS);
    public static final DeferredItem<Item> MANGROVE_WOOD_SLAB            = block(GotModBlocks.MANGROVE_WOOD_SLAB);
    public static final DeferredItem<Item> MANGROVE_WOOD_STAIRS          = block(GotModBlocks.MANGROVE_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_MANGROVE_WOOD_SLAB   = block(GotModBlocks.STRIPPED_MANGROVE_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_MANGROVE_WOOD_STAIRS = block(GotModBlocks.STRIPPED_MANGROVE_WOOD_STAIRS);
    public static final DeferredItem<Item> CHERRY_WOOD_SLAB            = block(GotModBlocks.CHERRY_WOOD_SLAB);
    public static final DeferredItem<Item> CHERRY_WOOD_STAIRS          = block(GotModBlocks.CHERRY_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_CHERRY_WOOD_SLAB   = block(GotModBlocks.STRIPPED_CHERRY_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_CHERRY_WOOD_STAIRS = block(GotModBlocks.STRIPPED_CHERRY_WOOD_STAIRS);
    public static final DeferredItem<Item> PALE_OAK_WOOD_SLAB            = block(GotModBlocks.PALE_OAK_WOOD_SLAB);
    public static final DeferredItem<Item> PALE_OAK_WOOD_STAIRS          = block(GotModBlocks.PALE_OAK_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_PALE_OAK_WOOD_SLAB   = block(GotModBlocks.STRIPPED_PALE_OAK_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_PALE_OAK_WOOD_STAIRS = block(GotModBlocks.STRIPPED_PALE_OAK_WOOD_STAIRS);

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
    public static final DeferredItem<Item> STRIPPED_WEIRWOOD_BRANCH = block(GotModBlocks.STRIPPED_WEIRWOOD_BRANCH);
    public static final DeferredItem<Item> ASPEN_BRANCH            = block(GotModBlocks.ASPEN_BRANCH);
    public static final DeferredItem<Item> STRIPPED_ASPEN_BRANCH = block(GotModBlocks.STRIPPED_ASPEN_BRANCH);
    public static final DeferredItem<Item> ALDER_BRANCH            = block(GotModBlocks.ALDER_BRANCH);
    public static final DeferredItem<Item> STRIPPED_ALDER_BRANCH = block(GotModBlocks.STRIPPED_ALDER_BRANCH);
    public static final DeferredItem<Item> PINE_BRANCH             = block(GotModBlocks.PINE_BRANCH);
    public static final DeferredItem<Item> STRIPPED_PINE_BRANCH = block(GotModBlocks.STRIPPED_PINE_BRANCH);
    public static final DeferredItem<Item> FIR_BRANCH              = block(GotModBlocks.FIR_BRANCH);
    public static final DeferredItem<Item> STRIPPED_FIR_BRANCH = block(GotModBlocks.STRIPPED_FIR_BRANCH);
    public static final DeferredItem<Item> SENTINAL_BRANCH         = block(GotModBlocks.SENTINAL_BRANCH);
    public static final DeferredItem<Item> STRIPPED_SENTINAL_BRANCH = block(GotModBlocks.STRIPPED_SENTINAL_BRANCH);
    public static final DeferredItem<Item> IRONWOOD_BRANCH         = block(GotModBlocks.IRONWOOD_BRANCH);
    public static final DeferredItem<Item> STRIPPED_IRONWOOD_BRANCH = block(GotModBlocks.STRIPPED_IRONWOOD_BRANCH);
    public static final DeferredItem<Item> BEECH_BRANCH            = block(GotModBlocks.BEECH_BRANCH);
    public static final DeferredItem<Item> STRIPPED_BEECH_BRANCH = block(GotModBlocks.STRIPPED_BEECH_BRANCH);
    public static final DeferredItem<Item> SOLDIER_PINE_BRANCH     = block(GotModBlocks.SOLDIER_PINE_BRANCH);
    public static final DeferredItem<Item> STRIPPED_SOLDIER_PINE_BRANCH = block(GotModBlocks.STRIPPED_SOLDIER_PINE_BRANCH);
    public static final DeferredItem<Item> ASH_BRANCH              = block(GotModBlocks.ASH_BRANCH);
    public static final DeferredItem<Item> STRIPPED_ASH_BRANCH = block(GotModBlocks.STRIPPED_ASH_BRANCH);
    public static final DeferredItem<Item> HAWTHORN_BRANCH         = block(GotModBlocks.HAWTHORN_BRANCH);
    public static final DeferredItem<Item> STRIPPED_HAWTHORN_BRANCH = block(GotModBlocks.STRIPPED_HAWTHORN_BRANCH);
    public static final DeferredItem<Item> BLACKBARK_BRANCH        = block(GotModBlocks.BLACKBARK_BRANCH);
    public static final DeferredItem<Item> STRIPPED_BLACKBARK_BRANCH = block(GotModBlocks.STRIPPED_BLACKBARK_BRANCH);
    public static final DeferredItem<Item> BLOODWOOD_BRANCH        = block(GotModBlocks.BLOODWOOD_BRANCH);
    public static final DeferredItem<Item> STRIPPED_BLOODWOOD_BRANCH = block(GotModBlocks.STRIPPED_BLOODWOOD_BRANCH);
    public static final DeferredItem<Item> BLUE_MAHOE_BRANCH       = block(GotModBlocks.BLUE_MAHOE_BRANCH);
    public static final DeferredItem<Item> STRIPPED_BLUE_MAHOE_BRANCH = block(GotModBlocks.STRIPPED_BLUE_MAHOE_BRANCH);
    public static final DeferredItem<Item> COTTONWOOD_BRANCH       = block(GotModBlocks.COTTONWOOD_BRANCH);
    public static final DeferredItem<Item> STRIPPED_COTTONWOOD_BRANCH = block(GotModBlocks.STRIPPED_COTTONWOOD_BRANCH);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_BRANCH = block(GotModBlocks.BLACK_COTTONWOOD_BRANCH);
    public static final DeferredItem<Item> STRIPPED_BLACK_COTTONWOOD_BRANCH = block(GotModBlocks.STRIPPED_BLACK_COTTONWOOD_BRANCH);
    public static final DeferredItem<Item> CINNAMON_BRANCH         = block(GotModBlocks.CINNAMON_BRANCH);
    public static final DeferredItem<Item> STRIPPED_CINNAMON_BRANCH = block(GotModBlocks.STRIPPED_CINNAMON_BRANCH);
    public static final DeferredItem<Item> CLOVE_BRANCH            = block(GotModBlocks.CLOVE_BRANCH);
    public static final DeferredItem<Item> STRIPPED_CLOVE_BRANCH = block(GotModBlocks.STRIPPED_CLOVE_BRANCH);
    public static final DeferredItem<Item> EBONY_BRANCH            = block(GotModBlocks.EBONY_BRANCH);
    public static final DeferredItem<Item> STRIPPED_EBONY_BRANCH = block(GotModBlocks.STRIPPED_EBONY_BRANCH);
    public static final DeferredItem<Item> ELM_BRANCH              = block(GotModBlocks.ELM_BRANCH);
    public static final DeferredItem<Item> STRIPPED_ELM_BRANCH = block(GotModBlocks.STRIPPED_ELM_BRANCH);
    public static final DeferredItem<Item> CEDAR_BRANCH            = block(GotModBlocks.CEDAR_BRANCH);
    public static final DeferredItem<Item> STRIPPED_CEDAR_BRANCH = block(GotModBlocks.STRIPPED_CEDAR_BRANCH);
    public static final DeferredItem<Item> APPLE_BRANCH            = block(GotModBlocks.APPLE_BRANCH);
    public static final DeferredItem<Item> STRIPPED_APPLE_BRANCH = block(GotModBlocks.STRIPPED_APPLE_BRANCH);
    public static final DeferredItem<Item> GOLDENHEART_BRANCH      = block(GotModBlocks.GOLDENHEART_BRANCH);
    public static final DeferredItem<Item> STRIPPED_GOLDENHEART_BRANCH = block(GotModBlocks.STRIPPED_GOLDENHEART_BRANCH);
    public static final DeferredItem<Item> LINDEN_BRANCH           = block(GotModBlocks.LINDEN_BRANCH);
    public static final DeferredItem<Item> STRIPPED_LINDEN_BRANCH = block(GotModBlocks.STRIPPED_LINDEN_BRANCH);
    public static final DeferredItem<Item> MAHOGANY_BRANCH         = block(GotModBlocks.MAHOGANY_BRANCH);
    public static final DeferredItem<Item> STRIPPED_MAHOGANY_BRANCH = block(GotModBlocks.STRIPPED_MAHOGANY_BRANCH);
    public static final DeferredItem<Item> MAPLE_BRANCH            = block(GotModBlocks.MAPLE_BRANCH);
    public static final DeferredItem<Item> STRIPPED_MAPLE_BRANCH = block(GotModBlocks.STRIPPED_MAPLE_BRANCH);
    public static final DeferredItem<Item> MYRRH_BRANCH            = block(GotModBlocks.MYRRH_BRANCH);
    public static final DeferredItem<Item> STRIPPED_MYRRH_BRANCH = block(GotModBlocks.STRIPPED_MYRRH_BRANCH);
    public static final DeferredItem<Item> REDWOOD_BRANCH          = block(GotModBlocks.REDWOOD_BRANCH);
    public static final DeferredItem<Item> STRIPPED_REDWOOD_BRANCH = block(GotModBlocks.STRIPPED_REDWOOD_BRANCH);
    public static final DeferredItem<Item> CHESTNUT_BRANCH         = block(GotModBlocks.CHESTNUT_BRANCH);
    public static final DeferredItem<Item> STRIPPED_CHESTNUT_BRANCH = block(GotModBlocks.STRIPPED_CHESTNUT_BRANCH);
    public static final DeferredItem<Item> WILLOW_BRANCH           = block(GotModBlocks.WILLOW_BRANCH);
    public static final DeferredItem<Item> STRIPPED_WILLOW_BRANCH = block(GotModBlocks.STRIPPED_WILLOW_BRANCH);
    public static final DeferredItem<Item> WORMTREE_BRANCH         = block(GotModBlocks.WORMTREE_BRANCH);
    public static final DeferredItem<Item> STRIPPED_WORMTREE_BRANCH = block(GotModBlocks.STRIPPED_WORMTREE_BRANCH);

    // ── Branches — vanilla overworld woods ──────────────────────────────────
    public static final DeferredItem<Item> OAK_BRANCH              = block(GotModBlocks.OAK_BRANCH);
    public static final DeferredItem<Item> STRIPPED_OAK_BRANCH      = block(GotModBlocks.STRIPPED_OAK_BRANCH);
    public static final DeferredItem<Item> SPRUCE_BRANCH           = block(GotModBlocks.SPRUCE_BRANCH);
    public static final DeferredItem<Item> STRIPPED_SPRUCE_BRANCH   = block(GotModBlocks.STRIPPED_SPRUCE_BRANCH);
    public static final DeferredItem<Item> BIRCH_BRANCH            = block(GotModBlocks.BIRCH_BRANCH);
    public static final DeferredItem<Item> STRIPPED_BIRCH_BRANCH    = block(GotModBlocks.STRIPPED_BIRCH_BRANCH);
    public static final DeferredItem<Item> JUNGLE_BRANCH           = block(GotModBlocks.JUNGLE_BRANCH);
    public static final DeferredItem<Item> STRIPPED_JUNGLE_BRANCH   = block(GotModBlocks.STRIPPED_JUNGLE_BRANCH);
    public static final DeferredItem<Item> ACACIA_BRANCH           = block(GotModBlocks.ACACIA_BRANCH);
    public static final DeferredItem<Item> STRIPPED_ACACIA_BRANCH   = block(GotModBlocks.STRIPPED_ACACIA_BRANCH);
    public static final DeferredItem<Item> DARK_OAK_BRANCH         = block(GotModBlocks.DARK_OAK_BRANCH);
    public static final DeferredItem<Item> STRIPPED_DARK_OAK_BRANCH = block(GotModBlocks.STRIPPED_DARK_OAK_BRANCH);
    public static final DeferredItem<Item> MANGROVE_BRANCH         = block(GotModBlocks.MANGROVE_BRANCH);
    public static final DeferredItem<Item> STRIPPED_MANGROVE_BRANCH = block(GotModBlocks.STRIPPED_MANGROVE_BRANCH);
    public static final DeferredItem<Item> CHERRY_BRANCH           = block(GotModBlocks.CHERRY_BRANCH);
    public static final DeferredItem<Item> STRIPPED_CHERRY_BRANCH   = block(GotModBlocks.STRIPPED_CHERRY_BRANCH);
    public static final DeferredItem<Item> PALE_OAK_BRANCH         = block(GotModBlocks.PALE_OAK_BRANCH);
    public static final DeferredItem<Item> STRIPPED_PALE_OAK_BRANCH = block(GotModBlocks.STRIPPED_PALE_OAK_BRANCH);

    // ── Signs ─────────────────────────────────────────────────────────────
    public static final DeferredItem<Item> WEIRWOOD_SIGN         = REGISTRY.registerItem("weirwood_sign",         p -> new SignItem(GotModBlocks.WEIRWOOD_SIGN.get(), GotModBlocks.WEIRWOOD_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> WEIRWOOD_HANGING_SIGN = REGISTRY.registerItem("weirwood_hanging_sign", p -> new HangingSignItem(GotModBlocks.WEIRWOOD_HANGING_SIGN.get(), GotModBlocks.WEIRWOOD_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> ASPEN_SIGN         = REGISTRY.registerItem("aspen_sign",         p -> new SignItem(GotModBlocks.ASPEN_SIGN.get(), GotModBlocks.ASPEN_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> ASPEN_HANGING_SIGN = REGISTRY.registerItem("aspen_hanging_sign", p -> new HangingSignItem(GotModBlocks.ASPEN_HANGING_SIGN.get(), GotModBlocks.ASPEN_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> ALDER_SIGN         = REGISTRY.registerItem("alder_sign",         p -> new SignItem(GotModBlocks.ALDER_SIGN.get(), GotModBlocks.ALDER_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> ALDER_HANGING_SIGN = REGISTRY.registerItem("alder_hanging_sign", p -> new HangingSignItem(GotModBlocks.ALDER_HANGING_SIGN.get(), GotModBlocks.ALDER_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> PINE_SIGN         = REGISTRY.registerItem("pine_sign",         p -> new SignItem(GotModBlocks.PINE_SIGN.get(), GotModBlocks.PINE_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> PINE_HANGING_SIGN = REGISTRY.registerItem("pine_hanging_sign", p -> new HangingSignItem(GotModBlocks.PINE_HANGING_SIGN.get(), GotModBlocks.PINE_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> FIR_SIGN         = REGISTRY.registerItem("fir_sign",         p -> new SignItem(GotModBlocks.FIR_SIGN.get(), GotModBlocks.FIR_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> FIR_HANGING_SIGN = REGISTRY.registerItem("fir_hanging_sign", p -> new HangingSignItem(GotModBlocks.FIR_HANGING_SIGN.get(), GotModBlocks.FIR_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> SENTINAL_SIGN         = REGISTRY.registerItem("sentinal_sign",         p -> new SignItem(GotModBlocks.SENTINAL_SIGN.get(), GotModBlocks.SENTINAL_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> SENTINAL_HANGING_SIGN = REGISTRY.registerItem("sentinal_hanging_sign", p -> new HangingSignItem(GotModBlocks.SENTINAL_HANGING_SIGN.get(), GotModBlocks.SENTINAL_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> IRONWOOD_SIGN         = REGISTRY.registerItem("ironwood_sign",         p -> new SignItem(GotModBlocks.IRONWOOD_SIGN.get(), GotModBlocks.IRONWOOD_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> IRONWOOD_HANGING_SIGN = REGISTRY.registerItem("ironwood_hanging_sign", p -> new HangingSignItem(GotModBlocks.IRONWOOD_HANGING_SIGN.get(), GotModBlocks.IRONWOOD_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> BEECH_SIGN         = REGISTRY.registerItem("beech_sign",         p -> new SignItem(GotModBlocks.BEECH_SIGN.get(), GotModBlocks.BEECH_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> BEECH_HANGING_SIGN = REGISTRY.registerItem("beech_hanging_sign", p -> new HangingSignItem(GotModBlocks.BEECH_HANGING_SIGN.get(), GotModBlocks.BEECH_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> SOLDIER_PINE_SIGN         = REGISTRY.registerItem("soldier_pine_sign",         p -> new SignItem(GotModBlocks.SOLDIER_PINE_SIGN.get(), GotModBlocks.SOLDIER_PINE_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> SOLDIER_PINE_HANGING_SIGN = REGISTRY.registerItem("soldier_pine_hanging_sign", p -> new HangingSignItem(GotModBlocks.SOLDIER_PINE_HANGING_SIGN.get(), GotModBlocks.SOLDIER_PINE_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> ASH_SIGN         = REGISTRY.registerItem("ash_sign",         p -> new SignItem(GotModBlocks.ASH_SIGN.get(), GotModBlocks.ASH_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> ASH_HANGING_SIGN = REGISTRY.registerItem("ash_hanging_sign", p -> new HangingSignItem(GotModBlocks.ASH_HANGING_SIGN.get(), GotModBlocks.ASH_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> HAWTHORN_SIGN         = REGISTRY.registerItem("hawthorn_sign",         p -> new SignItem(GotModBlocks.HAWTHORN_SIGN.get(), GotModBlocks.HAWTHORN_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> HAWTHORN_HANGING_SIGN = REGISTRY.registerItem("hawthorn_hanging_sign", p -> new HangingSignItem(GotModBlocks.HAWTHORN_HANGING_SIGN.get(), GotModBlocks.HAWTHORN_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> BLACKBARK_SIGN         = REGISTRY.registerItem("blackbark_sign",         p -> new SignItem(GotModBlocks.BLACKBARK_SIGN.get(), GotModBlocks.BLACKBARK_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> BLACKBARK_HANGING_SIGN = REGISTRY.registerItem("blackbark_hanging_sign", p -> new HangingSignItem(GotModBlocks.BLACKBARK_HANGING_SIGN.get(), GotModBlocks.BLACKBARK_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> BLOODWOOD_SIGN         = REGISTRY.registerItem("bloodwood_sign",         p -> new SignItem(GotModBlocks.BLOODWOOD_SIGN.get(), GotModBlocks.BLOODWOOD_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> BLOODWOOD_HANGING_SIGN = REGISTRY.registerItem("bloodwood_hanging_sign", p -> new HangingSignItem(GotModBlocks.BLOODWOOD_HANGING_SIGN.get(), GotModBlocks.BLOODWOOD_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> BLUE_MAHOE_SIGN         = REGISTRY.registerItem("blue_mahoe_sign",         p -> new SignItem(GotModBlocks.BLUE_MAHOE_SIGN.get(), GotModBlocks.BLUE_MAHOE_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> BLUE_MAHOE_HANGING_SIGN = REGISTRY.registerItem("blue_mahoe_hanging_sign", p -> new HangingSignItem(GotModBlocks.BLUE_MAHOE_HANGING_SIGN.get(), GotModBlocks.BLUE_MAHOE_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> COTTONWOOD_SIGN         = REGISTRY.registerItem("cottonwood_sign",         p -> new SignItem(GotModBlocks.COTTONWOOD_SIGN.get(), GotModBlocks.COTTONWOOD_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> COTTONWOOD_HANGING_SIGN = REGISTRY.registerItem("cottonwood_hanging_sign", p -> new HangingSignItem(GotModBlocks.COTTONWOOD_HANGING_SIGN.get(), GotModBlocks.COTTONWOOD_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> BLACK_COTTONWOOD_SIGN         = REGISTRY.registerItem("black_cottonwood_sign",         p -> new SignItem(GotModBlocks.BLACK_COTTONWOOD_SIGN.get(), GotModBlocks.BLACK_COTTONWOOD_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> BLACK_COTTONWOOD_HANGING_SIGN = REGISTRY.registerItem("black_cottonwood_hanging_sign", p -> new HangingSignItem(GotModBlocks.BLACK_COTTONWOOD_HANGING_SIGN.get(), GotModBlocks.BLACK_COTTONWOOD_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> CINNAMON_SIGN         = REGISTRY.registerItem("cinnamon_sign",         p -> new SignItem(GotModBlocks.CINNAMON_SIGN.get(), GotModBlocks.CINNAMON_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> CINNAMON_HANGING_SIGN = REGISTRY.registerItem("cinnamon_hanging_sign", p -> new HangingSignItem(GotModBlocks.CINNAMON_HANGING_SIGN.get(), GotModBlocks.CINNAMON_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> CLOVE_SIGN         = REGISTRY.registerItem("clove_sign",         p -> new SignItem(GotModBlocks.CLOVE_SIGN.get(), GotModBlocks.CLOVE_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> CLOVE_HANGING_SIGN = REGISTRY.registerItem("clove_hanging_sign", p -> new HangingSignItem(GotModBlocks.CLOVE_HANGING_SIGN.get(), GotModBlocks.CLOVE_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> EBONY_SIGN         = REGISTRY.registerItem("ebony_sign",         p -> new SignItem(GotModBlocks.EBONY_SIGN.get(), GotModBlocks.EBONY_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> EBONY_HANGING_SIGN = REGISTRY.registerItem("ebony_hanging_sign", p -> new HangingSignItem(GotModBlocks.EBONY_HANGING_SIGN.get(), GotModBlocks.EBONY_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> ELM_SIGN         = REGISTRY.registerItem("elm_sign",         p -> new SignItem(GotModBlocks.ELM_SIGN.get(), GotModBlocks.ELM_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> ELM_HANGING_SIGN = REGISTRY.registerItem("elm_hanging_sign", p -> new HangingSignItem(GotModBlocks.ELM_HANGING_SIGN.get(), GotModBlocks.ELM_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> CEDAR_SIGN         = REGISTRY.registerItem("cedar_sign",         p -> new SignItem(GotModBlocks.CEDAR_SIGN.get(), GotModBlocks.CEDAR_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> CEDAR_HANGING_SIGN = REGISTRY.registerItem("cedar_hanging_sign", p -> new HangingSignItem(GotModBlocks.CEDAR_HANGING_SIGN.get(), GotModBlocks.CEDAR_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> APPLE_SIGN         = REGISTRY.registerItem("apple_sign",         p -> new SignItem(GotModBlocks.APPLE_SIGN.get(), GotModBlocks.APPLE_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> APPLE_HANGING_SIGN = REGISTRY.registerItem("apple_hanging_sign", p -> new HangingSignItem(GotModBlocks.APPLE_HANGING_SIGN.get(), GotModBlocks.APPLE_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> GOLDENHEART_SIGN         = REGISTRY.registerItem("goldenheart_sign",         p -> new SignItem(GotModBlocks.GOLDENHEART_SIGN.get(), GotModBlocks.GOLDENHEART_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> GOLDENHEART_HANGING_SIGN = REGISTRY.registerItem("goldenheart_hanging_sign", p -> new HangingSignItem(GotModBlocks.GOLDENHEART_HANGING_SIGN.get(), GotModBlocks.GOLDENHEART_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> LINDEN_SIGN         = REGISTRY.registerItem("linden_sign",         p -> new SignItem(GotModBlocks.LINDEN_SIGN.get(), GotModBlocks.LINDEN_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> LINDEN_HANGING_SIGN = REGISTRY.registerItem("linden_hanging_sign", p -> new HangingSignItem(GotModBlocks.LINDEN_HANGING_SIGN.get(), GotModBlocks.LINDEN_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> MAHOGANY_SIGN         = REGISTRY.registerItem("mahogany_sign",         p -> new SignItem(GotModBlocks.MAHOGANY_SIGN.get(), GotModBlocks.MAHOGANY_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> MAHOGANY_HANGING_SIGN = REGISTRY.registerItem("mahogany_hanging_sign", p -> new HangingSignItem(GotModBlocks.MAHOGANY_HANGING_SIGN.get(), GotModBlocks.MAHOGANY_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> MAPLE_SIGN         = REGISTRY.registerItem("maple_sign",         p -> new SignItem(GotModBlocks.MAPLE_SIGN.get(), GotModBlocks.MAPLE_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> MAPLE_HANGING_SIGN = REGISTRY.registerItem("maple_hanging_sign", p -> new HangingSignItem(GotModBlocks.MAPLE_HANGING_SIGN.get(), GotModBlocks.MAPLE_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> MYRRH_SIGN         = REGISTRY.registerItem("myrrh_sign",         p -> new SignItem(GotModBlocks.MYRRH_SIGN.get(), GotModBlocks.MYRRH_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> MYRRH_HANGING_SIGN = REGISTRY.registerItem("myrrh_hanging_sign", p -> new HangingSignItem(GotModBlocks.MYRRH_HANGING_SIGN.get(), GotModBlocks.MYRRH_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> REDWOOD_SIGN         = REGISTRY.registerItem("redwood_sign",         p -> new SignItem(GotModBlocks.REDWOOD_SIGN.get(), GotModBlocks.REDWOOD_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> REDWOOD_HANGING_SIGN = REGISTRY.registerItem("redwood_hanging_sign", p -> new HangingSignItem(GotModBlocks.REDWOOD_HANGING_SIGN.get(), GotModBlocks.REDWOOD_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> CHESTNUT_SIGN         = REGISTRY.registerItem("chestnut_sign",         p -> new SignItem(GotModBlocks.CHESTNUT_SIGN.get(), GotModBlocks.CHESTNUT_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> CHESTNUT_HANGING_SIGN = REGISTRY.registerItem("chestnut_hanging_sign", p -> new HangingSignItem(GotModBlocks.CHESTNUT_HANGING_SIGN.get(), GotModBlocks.CHESTNUT_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> WILLOW_SIGN         = REGISTRY.registerItem("willow_sign",         p -> new SignItem(GotModBlocks.WILLOW_SIGN.get(), GotModBlocks.WILLOW_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> WILLOW_HANGING_SIGN = REGISTRY.registerItem("willow_hanging_sign", p -> new HangingSignItem(GotModBlocks.WILLOW_HANGING_SIGN.get(), GotModBlocks.WILLOW_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> WORMTREE_SIGN         = REGISTRY.registerItem("wormtree_sign",         p -> new SignItem(GotModBlocks.WORMTREE_SIGN.get(), GotModBlocks.WORMTREE_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> WORMTREE_HANGING_SIGN = REGISTRY.registerItem("wormtree_hanging_sign", p -> new HangingSignItem(GotModBlocks.WORMTREE_HANGING_SIGN.get(), GotModBlocks.WORMTREE_WALL_HANGING_SIGN.get(), p));
    // ── Boats ─────────────────────────────────────────────────────────────
    public static final DeferredItem<Item> WEIRWOOD_BOAT       = REGISTRY.registerItem("weirwood_boat",       p -> new GotBoatItem(GotModBoatEntities.WEIRWOOD_BOAT.get(), p));
    public static final DeferredItem<Item> WEIRWOOD_CHEST_BOAT = REGISTRY.registerItem("weirwood_chest_boat", p -> new GotBoatItem(GotModBoatEntities.WEIRWOOD_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> ASPEN_BOAT       = REGISTRY.registerItem("aspen_boat",       p -> new GotBoatItem(GotModBoatEntities.ASPEN_BOAT.get(), p));
    public static final DeferredItem<Item> ASPEN_CHEST_BOAT = REGISTRY.registerItem("aspen_chest_boat", p -> new GotBoatItem(GotModBoatEntities.ASPEN_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> ALDER_BOAT       = REGISTRY.registerItem("alder_boat",       p -> new GotBoatItem(GotModBoatEntities.ALDER_BOAT.get(), p));
    public static final DeferredItem<Item> ALDER_CHEST_BOAT = REGISTRY.registerItem("alder_chest_boat", p -> new GotBoatItem(GotModBoatEntities.ALDER_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> PINE_BOAT       = REGISTRY.registerItem("pine_boat",       p -> new GotBoatItem(GotModBoatEntities.PINE_BOAT.get(), p));
    public static final DeferredItem<Item> PINE_CHEST_BOAT = REGISTRY.registerItem("pine_chest_boat", p -> new GotBoatItem(GotModBoatEntities.PINE_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> FIR_BOAT       = REGISTRY.registerItem("fir_boat",       p -> new GotBoatItem(GotModBoatEntities.FIR_BOAT.get(), p));
    public static final DeferredItem<Item> FIR_CHEST_BOAT = REGISTRY.registerItem("fir_chest_boat", p -> new GotBoatItem(GotModBoatEntities.FIR_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> SENTINAL_BOAT       = REGISTRY.registerItem("sentinal_boat",       p -> new GotBoatItem(GotModBoatEntities.SENTINAL_BOAT.get(), p));
    public static final DeferredItem<Item> SENTINAL_CHEST_BOAT = REGISTRY.registerItem("sentinal_chest_boat", p -> new GotBoatItem(GotModBoatEntities.SENTINAL_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> IRONWOOD_BOAT       = REGISTRY.registerItem("ironwood_boat",       p -> new GotBoatItem(GotModBoatEntities.IRONWOOD_BOAT.get(), p));
    public static final DeferredItem<Item> IRONWOOD_CHEST_BOAT = REGISTRY.registerItem("ironwood_chest_boat", p -> new GotBoatItem(GotModBoatEntities.IRONWOOD_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> BEECH_BOAT       = REGISTRY.registerItem("beech_boat",       p -> new GotBoatItem(GotModBoatEntities.BEECH_BOAT.get(), p));
    public static final DeferredItem<Item> BEECH_CHEST_BOAT = REGISTRY.registerItem("beech_chest_boat", p -> new GotBoatItem(GotModBoatEntities.BEECH_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> SOLDIER_PINE_BOAT       = REGISTRY.registerItem("soldier_pine_boat",       p -> new GotBoatItem(GotModBoatEntities.SOLDIER_PINE_BOAT.get(), p));
    public static final DeferredItem<Item> SOLDIER_PINE_CHEST_BOAT = REGISTRY.registerItem("soldier_pine_chest_boat", p -> new GotBoatItem(GotModBoatEntities.SOLDIER_PINE_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> ASH_BOAT       = REGISTRY.registerItem("ash_boat",       p -> new GotBoatItem(GotModBoatEntities.ASH_BOAT.get(), p));
    public static final DeferredItem<Item> ASH_CHEST_BOAT = REGISTRY.registerItem("ash_chest_boat", p -> new GotBoatItem(GotModBoatEntities.ASH_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> HAWTHORN_BOAT       = REGISTRY.registerItem("hawthorn_boat",       p -> new GotBoatItem(GotModBoatEntities.HAWTHORN_BOAT.get(), p));
    public static final DeferredItem<Item> HAWTHORN_CHEST_BOAT = REGISTRY.registerItem("hawthorn_chest_boat", p -> new GotBoatItem(GotModBoatEntities.HAWTHORN_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> BLACKBARK_BOAT       = REGISTRY.registerItem("blackbark_boat",       p -> new GotBoatItem(GotModBoatEntities.BLACKBARK_BOAT.get(), p));
    public static final DeferredItem<Item> BLACKBARK_CHEST_BOAT = REGISTRY.registerItem("blackbark_chest_boat", p -> new GotBoatItem(GotModBoatEntities.BLACKBARK_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> BLOODWOOD_BOAT       = REGISTRY.registerItem("bloodwood_boat",       p -> new GotBoatItem(GotModBoatEntities.BLOODWOOD_BOAT.get(), p));
    public static final DeferredItem<Item> BLOODWOOD_CHEST_BOAT = REGISTRY.registerItem("bloodwood_chest_boat", p -> new GotBoatItem(GotModBoatEntities.BLOODWOOD_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> BLUE_MAHOE_BOAT       = REGISTRY.registerItem("blue_mahoe_boat",       p -> new GotBoatItem(GotModBoatEntities.BLUE_MAHOE_BOAT.get(), p));
    public static final DeferredItem<Item> BLUE_MAHOE_CHEST_BOAT = REGISTRY.registerItem("blue_mahoe_chest_boat", p -> new GotBoatItem(GotModBoatEntities.BLUE_MAHOE_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> COTTONWOOD_BOAT       = REGISTRY.registerItem("cottonwood_boat",       p -> new GotBoatItem(GotModBoatEntities.COTTONWOOD_BOAT.get(), p));
    public static final DeferredItem<Item> COTTONWOOD_CHEST_BOAT = REGISTRY.registerItem("cottonwood_chest_boat", p -> new GotBoatItem(GotModBoatEntities.COTTONWOOD_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> BLACK_COTTONWOOD_BOAT       = REGISTRY.registerItem("black_cottonwood_boat",       p -> new GotBoatItem(GotModBoatEntities.BLACK_COTTONWOOD_BOAT.get(), p));
    public static final DeferredItem<Item> BLACK_COTTONWOOD_CHEST_BOAT = REGISTRY.registerItem("black_cottonwood_chest_boat", p -> new GotBoatItem(GotModBoatEntities.BLACK_COTTONWOOD_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> CINNAMON_BOAT       = REGISTRY.registerItem("cinnamon_boat",       p -> new GotBoatItem(GotModBoatEntities.CINNAMON_BOAT.get(), p));
    public static final DeferredItem<Item> CINNAMON_CHEST_BOAT = REGISTRY.registerItem("cinnamon_chest_boat", p -> new GotBoatItem(GotModBoatEntities.CINNAMON_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> CLOVE_BOAT       = REGISTRY.registerItem("clove_boat",       p -> new GotBoatItem(GotModBoatEntities.CLOVE_BOAT.get(), p));
    public static final DeferredItem<Item> CLOVE_CHEST_BOAT = REGISTRY.registerItem("clove_chest_boat", p -> new GotBoatItem(GotModBoatEntities.CLOVE_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> EBONY_BOAT       = REGISTRY.registerItem("ebony_boat",       p -> new GotBoatItem(GotModBoatEntities.EBONY_BOAT.get(), p));
    public static final DeferredItem<Item> EBONY_CHEST_BOAT = REGISTRY.registerItem("ebony_chest_boat", p -> new GotBoatItem(GotModBoatEntities.EBONY_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> ELM_BOAT       = REGISTRY.registerItem("elm_boat",       p -> new GotBoatItem(GotModBoatEntities.ELM_BOAT.get(), p));
    public static final DeferredItem<Item> ELM_CHEST_BOAT = REGISTRY.registerItem("elm_chest_boat", p -> new GotBoatItem(GotModBoatEntities.ELM_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> CEDAR_BOAT       = REGISTRY.registerItem("cedar_boat",       p -> new GotBoatItem(GotModBoatEntities.CEDAR_BOAT.get(), p));
    public static final DeferredItem<Item> CEDAR_CHEST_BOAT = REGISTRY.registerItem("cedar_chest_boat", p -> new GotBoatItem(GotModBoatEntities.CEDAR_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> APPLE_BOAT       = REGISTRY.registerItem("apple_boat",       p -> new GotBoatItem(GotModBoatEntities.APPLE_BOAT.get(), p));
    public static final DeferredItem<Item> APPLE_CHEST_BOAT = REGISTRY.registerItem("apple_chest_boat", p -> new GotBoatItem(GotModBoatEntities.APPLE_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> GOLDENHEART_BOAT       = REGISTRY.registerItem("goldenheart_boat",       p -> new GotBoatItem(GotModBoatEntities.GOLDENHEART_BOAT.get(), p));
    public static final DeferredItem<Item> GOLDENHEART_CHEST_BOAT = REGISTRY.registerItem("goldenheart_chest_boat", p -> new GotBoatItem(GotModBoatEntities.GOLDENHEART_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> LINDEN_BOAT       = REGISTRY.registerItem("linden_boat",       p -> new GotBoatItem(GotModBoatEntities.LINDEN_BOAT.get(), p));
    public static final DeferredItem<Item> LINDEN_CHEST_BOAT = REGISTRY.registerItem("linden_chest_boat", p -> new GotBoatItem(GotModBoatEntities.LINDEN_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> MAHOGANY_BOAT       = REGISTRY.registerItem("mahogany_boat",       p -> new GotBoatItem(GotModBoatEntities.MAHOGANY_BOAT.get(), p));
    public static final DeferredItem<Item> MAHOGANY_CHEST_BOAT = REGISTRY.registerItem("mahogany_chest_boat", p -> new GotBoatItem(GotModBoatEntities.MAHOGANY_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> MAPLE_BOAT       = REGISTRY.registerItem("maple_boat",       p -> new GotBoatItem(GotModBoatEntities.MAPLE_BOAT.get(), p));
    public static final DeferredItem<Item> MAPLE_CHEST_BOAT = REGISTRY.registerItem("maple_chest_boat", p -> new GotBoatItem(GotModBoatEntities.MAPLE_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> MYRRH_BOAT       = REGISTRY.registerItem("myrrh_boat",       p -> new GotBoatItem(GotModBoatEntities.MYRRH_BOAT.get(), p));
    public static final DeferredItem<Item> MYRRH_CHEST_BOAT = REGISTRY.registerItem("myrrh_chest_boat", p -> new GotBoatItem(GotModBoatEntities.MYRRH_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> REDWOOD_BOAT       = REGISTRY.registerItem("redwood_boat",       p -> new GotBoatItem(GotModBoatEntities.REDWOOD_BOAT.get(), p));
    public static final DeferredItem<Item> REDWOOD_CHEST_BOAT = REGISTRY.registerItem("redwood_chest_boat", p -> new GotBoatItem(GotModBoatEntities.REDWOOD_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> CHESTNUT_BOAT       = REGISTRY.registerItem("chestnut_boat",       p -> new GotBoatItem(GotModBoatEntities.CHESTNUT_BOAT.get(), p));
    public static final DeferredItem<Item> CHESTNUT_CHEST_BOAT = REGISTRY.registerItem("chestnut_chest_boat", p -> new GotBoatItem(GotModBoatEntities.CHESTNUT_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> WILLOW_BOAT       = REGISTRY.registerItem("willow_boat",       p -> new GotBoatItem(GotModBoatEntities.WILLOW_BOAT.get(), p));
    public static final DeferredItem<Item> WILLOW_CHEST_BOAT = REGISTRY.registerItem("willow_chest_boat", p -> new GotBoatItem(GotModBoatEntities.WILLOW_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> WORMTREE_BOAT       = REGISTRY.registerItem("wormtree_boat",       p -> new GotBoatItem(GotModBoatEntities.WORMTREE_BOAT.get(), p));
    public static final DeferredItem<Item> WORMTREE_CHEST_BOAT = REGISTRY.registerItem("wormtree_chest_boat", p -> new GotBoatItem(GotModBoatEntities.WORMTREE_CHEST_BOAT.get(), p));


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

    // ── Flowers ───────────────────────────────────────────────────────────
    public static final DeferredItem<Item> BELLFLOWER         = block(GotModBlocks.BELLFLOWER);
    public static final DeferredItem<Item> BLACK_LOTUS        = block(GotModBlocks.BLACK_LOTUS);
    public static final DeferredItem<Item> BLOOD_BLOOM        = block(GotModBlocks.BLOOD_BLOOM);
    public static final DeferredItem<Item> COLDSNAPS          = block(GotModBlocks.COLDSNAPS);
    public static final DeferredItem<Item> DRAGONS_BREATH     = block(GotModBlocks.DRAGONS_BREATH);
    public static final DeferredItem<Item> EVENING_STAR       = block(GotModBlocks.EVENING_STAR);
    public static final DeferredItem<Item> FORGET_ME_NOT      = block(GotModBlocks.FORGET_ME_NOT);
    public static final DeferredItem<Item> FROSTFIRES         = block(GotModBlocks.FROSTFIRES);
    public static final DeferredItem<Item> GILLYFLOWER        = block(GotModBlocks.GILLYFLOWER);
    public static final DeferredItem<Item> GINGER             = block(GotModBlocks.GINGER);
    public static final DeferredItem<Item> GOATHEAD           = block(GotModBlocks.GOATHEAD);
    public static final DeferredItem<Item> GOLDENCUP          = block(GotModBlocks.GOLDENCUP);
    public static final DeferredItem<Item> GOLDENROD          = block(GotModBlocks.GOLDENROD);
    public static final DeferredItem<Item> GORSE              = block(GotModBlocks.GORSE);
    public static final DeferredItem<Item> LADYS_LACE         = block(GotModBlocks.LADYS_LACE);
    public static final DeferredItem<Item> LAVENDER           = block(GotModBlocks.LAVENDER);
    public static final DeferredItem<Item> LIVERWORT          = block(GotModBlocks.LIVERWORT);
    public static final DeferredItem<Item> LUNGWORT           = block(GotModBlocks.LUNGWORT);
    public static final DeferredItem<Item> MOONBLOOM          = block(GotModBlocks.MOONBLOOM);
    public static final DeferredItem<Item> NIGHTSHADE         = block(GotModBlocks.NIGHTSHADE);
    public static final DeferredItem<Item> PENNYROYAL         = block(GotModBlocks.PENNYROYAL);
    public static final DeferredItem<Item> POISON_KISSES      = block(GotModBlocks.POISON_KISSES);
    public static final DeferredItem<Item> THORNBUSH          = block(GotModBlocks.THORNBUSH);
    public static final DeferredItem<Item> OPIUM_POPPY        = block(GotModBlocks.OPIUM_POPPY);
    public static final DeferredItem<Item> GOLDEN_ROSE        = block(GotModBlocks.GOLDEN_ROSE);
    public static final DeferredItem<Item> RED_ROSE           = block(GotModBlocks.RED_ROSE);
    public static final DeferredItem<Item> WHITE_ROSE         = block(GotModBlocks.WHITE_ROSE);
    public static final DeferredItem<Item> WINTER_ROSE        = block(GotModBlocks.WINTER_ROSE);
    public static final DeferredItem<Item> SAFFRON_CROCUS     = block(GotModBlocks.SAFFRON_CROCUS);
    public static final DeferredItem<Item> SEDGE              = block(GotModBlocks.SEDGE);
    public static final DeferredItem<Item> SPICEFLOWER        = block(GotModBlocks.SPICEFLOWER);
    public static final DeferredItem<Item> TANSY              = block(GotModBlocks.TANSY);
    public static final DeferredItem<Item> THISTLE            = block(GotModBlocks.THISTLE);
    public static final DeferredItem<Item> WILD_RADISH        = block(GotModBlocks.WILD_RADISH);

    public static final DeferredItem<BlockItem> RED_ROSE_BUSH   = REGISTRY.registerSimpleBlockItem("red_rose_bush",   GotModBlocks.RED_ROSE_BUSH);
    public static final DeferredItem<BlockItem> GOLDEN_ROSE_BUSH = REGISTRY.registerSimpleBlockItem("golden_rose_bush", GotModBlocks.GOLDEN_ROSE_BUSH);
    public static final DeferredItem<BlockItem> WHITE_ROSE_BUSH  = REGISTRY.registerSimpleBlockItem("white_rose_bush",  GotModBlocks.WHITE_ROSE_BUSH);
    public static final DeferredItem<BlockItem> WINTER_ROSE_BUSH = REGISTRY.registerSimpleBlockItem("winter_rose_bush", GotModBlocks.WINTER_ROSE_BUSH);


    // ── NPC Workstations ─────────────────────────────────────────────────────

    // ── Currency ──────────────────────────────────────────────────────────────
    // Ordered smallest → largest.  Values (in halfpennies):
    //   Halfpenny=1  Penny=2  Halfgroat=4  Groat=8  Star=16
    //   Stag=112  Moon=784  Dragon=23520
    public static final DeferredItem<Item> COIN_HALFPENNY = simple("coin_halfpenny");
    public static final DeferredItem<Item> COIN_PENNY     = simple("coin_penny");
    public static final DeferredItem<Item> COIN_HALFGROAT = simple("coin_halfgroat");
    public static final DeferredItem<Item> COIN_GROAT     = simple("coin_groat");
    public static final DeferredItem<Item> COIN_STAR      = simple("coin_star");
    public static final DeferredItem<Item> COIN_STAG      = simple("coin_stag");
    public static final DeferredItem<Item> COIN_MOON      = simple("coin_moon");
    public static final DeferredItem<Item> COIN_DRAGON    = simple("coin_dragon");


    // ── Grasses ───────────────────────────────────────────────────────────
    public static final DeferredItem<Item> DEVILGRASS         = block(GotModBlocks.DEVILGRASS);
    public static final DeferredItem<Item> GHOST_GRASS        = block(GotModBlocks.GHOST_GRASS);
    public static final DeferredItem<Item> HRANNA             = block(GotModBlocks.HRANNA);
    public static final DeferredItem<Item> PIPERS_GRASS       = block(GotModBlocks.PIPERS_GRASS);
    public static final DeferredItem<Item> WHEATGRASS       = block(GotModBlocks.WHEATGRASS);

    // ── Wild Crop items ──────────────────────────────────────────────────────
    public static final DeferredItem<Item> WILD_WHEAT = block(GotModBlocks.WILD_WHEAT);
    public static final DeferredItem<Item> WILD_OAT = block(GotModBlocks.WILD_OAT);
    public static final DeferredItem<Item> WILD_RYE = block(GotModBlocks.WILD_RYE);
    public static final DeferredItem<Item> WILD_BARLEY = block(GotModBlocks.WILD_BARLEY);
    public static final DeferredItem<Item> WILD_BEETROOT = block(GotModBlocks.WILD_BEETROOT);
    public static final DeferredItem<Item> WILD_COTTON = block(GotModBlocks.WILD_COTTON);
    public static final DeferredItem<Item> WILD_PEPPERCORN = block(GotModBlocks.WILD_PEPPERCORN);
    public static final DeferredItem<Item> WILD_CARROT = block(GotModBlocks.WILD_CARROT);
    public static final DeferredItem<Item> WILD_PARSNIP = block(GotModBlocks.WILD_PARSNIP);
    public static final DeferredItem<Item> WILD_ONION = block(GotModBlocks.WILD_ONION);
    public static final DeferredItem<Item> WILD_TURNIP = block(GotModBlocks.WILD_TURNIP);
    public static final DeferredItem<Item> WILD_NEEP = block(GotModBlocks.WILD_NEEP);
    public static final DeferredItem<Item> WILD_PEAS = block(GotModBlocks.WILD_PEAS);
    public static final DeferredItem<Item> WILD_CABBAGE = block(GotModBlocks.WILD_CABBAGE);
    public static final DeferredItem<Item> WILD_GARLIC = block(GotModBlocks.WILD_GARLIC);
    public static final DeferredItem<Item> WILD_HORSERADISH = block(GotModBlocks.WILD_HORSERADISH);
    public static final DeferredItem<Item> WILD_LEEK = block(GotModBlocks.WILD_LEEK);

    // ── Quagmire ────────────────────────────────────────────────────────────────
    /** Quagmire block item — can be placed as a terrain block. */
    public static final DeferredItem<Item> QUAGMIRE = block(GotModBlocks.QUAGMIRE);

    // ── Reeds ────────────────────────────────────────────────────────────────
    public static final DeferredItem<Item> REEDS = block(GotModBlocks.REEDS);

    public static final DeferredItem<BlockItem> SHORT_REEDS = REGISTRY.registerSimpleBlockItem(
            "short_reeds", GotModBlocks.SHORT_REEDS);

    // ── Crop seeds (seed-type crops) ──────────────────────────────────────
    public static final DeferredItem<Item> OAT_SEEDS     = REGISTRY.registerItem("oat_seeds",     p -> new BlockItem(GotModBlocks.OAT_CROP.get(),      p));
    public static final DeferredItem<Item> RYE_SEEDS     = REGISTRY.registerItem("rye_seeds",     p -> new BlockItem(GotModBlocks.RYE_CROP.get(),      p));
    public static final DeferredItem<Item> BARLEY_SEEDS  = REGISTRY.registerItem("barley_seeds",  p -> new BlockItem(GotModBlocks.BARLEY_CROP.get(),   p));
    public static final DeferredItem<Item> COTTON_SEEDS      = REGISTRY.registerItem("cotton_seeds",      p -> new BlockItem(GotModBlocks.COTTON_CROP.get(),     p));
    public static final DeferredItem<Item> PEPPERCORN_SEEDS  = REGISTRY.registerItem("peppercorn_seeds",  p -> new BlockItem(GotModBlocks.PEPPERCORN_CROP.get(), p));

    public static final DeferredItem<Item> CARDAMOM_SEEDS      = REGISTRY.registerItem("cardamom_seeds",      p -> new BlockItem(GotModBlocks.CARDAMOM_CROP.get(),      p));
    public static final DeferredItem<Item> CHICKPEA_SEEDS      = REGISTRY.registerItem("chickpea_seeds",      p -> new BlockItem(GotModBlocks.CHICKPEA_CROP.get(),      p));
    public static final DeferredItem<Item> CORN_SEEDS          = REGISTRY.registerItem("corn_seeds",          p -> new BlockItem(GotModBlocks.CORN_CROP.get(),          p));
    public static final DeferredItem<Item> CUCUMBER_SEEDS      = REGISTRY.registerItem("cucumber_seeds",      p -> new BlockItem(GotModBlocks.CUCUMBER_CROP.get(),      p));
    public static final DeferredItem<Item> HEMP_SEEDS          = REGISTRY.registerItem("hemp_seeds",          p -> new BlockItem(GotModBlocks.HEMP_CROP.get(),          p));
    public static final DeferredItem<Item> LICORICE_SEEDS      = REGISTRY.registerItem("licorice_seeds",      p -> new BlockItem(GotModBlocks.LICORICE_CROP.get(),      p));
    public static final DeferredItem<Item> MUSTARD_PLANT_SEEDS = REGISTRY.registerItem("mustard_plant_seeds", p -> new BlockItem(GotModBlocks.MUSTARD_PLANT_CROP.get(), p));
    public static final DeferredItem<Item> PEPPER_PLANT_SEEDS  = REGISTRY.registerItem("pepper_plant_seeds",  p -> new BlockItem(GotModBlocks.PEPPER_PLANT_CROP.get(),  p));

    // ── Crop produce (seed-type crops) ────────────────────────────────────
    public static final DeferredItem<Item> OAT        = simple("oat");
    public static final DeferredItem<Item> RYE        = simple("rye");
    public static final DeferredItem<Item> BARLEY     = simple("barley");
    public static final DeferredItem<Item> COTTON     = simple("cotton");
    public static final DeferredItem<Item> PEPPERCORN = simple("peppercorn");
    // ── Flour, Dough, Bread ──────────────────────────────────────────────────
    public static final DeferredItem<Item> WHEAT_FLOUR  = simple("wheat_flour");
    public static final DeferredItem<Item> WHEAT_DOUGH  = simple("wheat_dough");
    public static final DeferredItem<Item> OAT_FLOUR  = simple("oat_flour");
    public static final DeferredItem<Item> OAT_DOUGH  = simple("oat_dough");
    public static final DeferredItem<Item> OAT_BREAD  = REGISTRY.registerItem("oat_bread",  p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(5).saturationModifier(0.6f).build()));
    public static final DeferredItem<Item> RYE_FLOUR  = simple("rye_flour");
    public static final DeferredItem<Item> RYE_DOUGH  = simple("rye_dough");
    public static final DeferredItem<Item> RYE_BREAD  = REGISTRY.registerItem("rye_bread",  p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(5).saturationModifier(0.6f).build()));
    public static final DeferredItem<Item> BARLEY_FLOUR  = simple("barley_flour");
    public static final DeferredItem<Item> BARLEY_DOUGH  = simple("barley_dough");
    public static final DeferredItem<Item> BARLEY_BREAD  = REGISTRY.registerItem("barley_bread",  p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(5).saturationModifier(0.6f).build()));


    // ── Crop produce (produce-type crops — also the planting item) ────────
    public static final DeferredItem<Item> PARSNIP     = REGISTRY.registerItem("parsnip",     p -> new BlockItem(GotModBlocks.PARSNIP_CROP.get(),     p), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.6f).build()));
    public static final DeferredItem<Item> ONION       = REGISTRY.registerItem("onion",       p -> new BlockItem(GotModBlocks.ONION_CROP.get(),       p), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.5f).build()));
    public static final DeferredItem<Item> TURNIP      = REGISTRY.registerItem("turnip",      p -> new BlockItem(GotModBlocks.TURNIP_CROP.get(),      p), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.5f).build()));
    public static final DeferredItem<Item> PEAS        = REGISTRY.registerItem("peas",        p -> new BlockItem(GotModBlocks.PEAS_CROP.get(),        p), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.6f).build()));
    public static final DeferredItem<Item> CABBAGE_PLANT_SEEDS = REGISTRY.registerItem("cabbage_plant_seeds", p -> new BlockItem(GotModBlocks.CABBAGE_CROP.get(), p));
    public static final DeferredItem<Item> CABBAGE     = REGISTRY.registerItem("cabbage",     p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.6f).build()));
    public static final DeferredItem<Item> GARLIC      = REGISTRY.registerItem("garlic",      p -> new BlockItem(GotModBlocks.GARLIC_CROP.get(),      p), new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.3f).build()));
    public static final DeferredItem<Item> NEEP        = REGISTRY.registerItem("neep",        p -> new BlockItem(GotModBlocks.NEEP_CROP.get(),        p), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.5f).build()));
    public static final DeferredItem<Item> HORSERADISH = REGISTRY.registerItem("horseradish", p -> new BlockItem(GotModBlocks.HORSERADISH_CROP.get(), p), new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.3f).build()));
    public static final DeferredItem<Item> LEEK        = REGISTRY.registerItem("leek",        p -> new BlockItem(GotModBlocks.LEEK_CROP.get(),        p), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.5f).build()));

    // ── Miscellaneous ASOIAF Plants ──────────────────────────────────────
    public static final DeferredItem<Item> WILD_BEAN          = block(GotModBlocks.WILD_BEAN);
    public static final DeferredItem<Item> BEAN               = REGISTRY.registerItem("bean",          p -> new BlockItem(GotModBlocks.BEAN_CROP.get(), p), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.5f).build()));

    public static final DeferredItem<Item> BRACKEN            = block(GotModBlocks.BRACKEN);
    public static final DeferredItem<Item> BRIAR              = block(GotModBlocks.BRIAR);
    public static final DeferredItem<Item> BROOM              = block(GotModBlocks.BROOM);

    public static final DeferredItem<Item> WILD_CARDAMOM      = block(GotModBlocks.WILD_CARDAMOM);
    public static final DeferredItem<Item> CARDAMOM           = REGISTRY.registerItem("cardamom",      p -> new Item(p));
    public static final DeferredItem<Item> WILD_CHICKPEA      = block(GotModBlocks.WILD_CHICKPEA);
    public static final DeferredItem<Item> CHICKPEA           = REGISTRY.registerItem("chickpea",      p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.5f).build()));
    public static final DeferredItem<Item> CORN_ON_THE_COB    = REGISTRY.registerItem("corn_on_the_cob", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(5).saturationModifier(0.6f).build()));
    public static final DeferredItem<Item> WILD_CUCUMBER      = block(GotModBlocks.WILD_CUCUMBER);
    public static final DeferredItem<Item> CUCUMBER           = REGISTRY.registerItem("cucumber",      p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.4f).build()));
    public static final DeferredItem<Item> DAGGERLEAF         = block(GotModBlocks.DAGGERLEAF);
    public static final DeferredItem<Item> FIREPOD            = block(GotModBlocks.FIREPOD);
    public static final DeferredItem<Item> GHOSTSKIN          = block(GotModBlocks.GHOSTSKIN);
    public static final DeferredItem<Item> GRAPE_VINE         = block(GotModBlocks.GRAPE_VINE);
    public static final DeferredItem<Item> HARPYS_GOLD        = block(GotModBlocks.HARPYS_GOLD);
    public static final DeferredItem<Item> WILD_HEMP          = block(GotModBlocks.WILD_HEMP);
    public static final DeferredItem<Item> HEMP               = REGISTRY.registerItem("hemp",          p -> new Item(p));
    public static final DeferredItem<Item> HORNWORT           = block(GotModBlocks.HORNWORT);
    public static final DeferredItem<Item> IVY                = block(GotModBlocks.IVY);
    public static final DeferredItem<Item> KINGSCOPPER        = block(GotModBlocks.KINGSCOPPER);
    public static final DeferredItem<Item> WILD_LICORICE      = block(GotModBlocks.WILD_LICORICE);
    public static final DeferredItem<Item> LICORICE           = REGISTRY.registerItem("licorice",      p -> new Item(p));
    public static final DeferredItem<Item> MISTLETOE          = block(GotModBlocks.MISTLETOE);
    public static final DeferredItem<Item> WILD_MUSTARD_PLANT = block(GotModBlocks.WILD_MUSTARD_PLANT);
    public static final DeferredItem<Item> NETTLE             = block(GotModBlocks.NETTLE);
    public static final DeferredItem<Item> WILD_PEPPER_PLANT  = block(GotModBlocks.WILD_PEPPER_PLANT);
    public static final DeferredItem<Item> PEPPER_PLANT       = REGISTRY.registerItem("pepper_plant",  p -> new Item(p));
    public static final DeferredItem<Item> PINCHFIRE          = block(GotModBlocks.PINCHFIRE);
    public static final DeferredItem<Item> PRICKLY_BEN        = block(GotModBlocks.PRICKLY_BEN);
    public static final DeferredItem<Item> SANDWILLOW         = block(GotModBlocks.SANDWILLOW);
    public static final DeferredItem<Item> SMOKEBERRY_BUSH    = block(GotModBlocks.SMOKEBERRY_BUSH);
    public static final DeferredItem<Item> SMOKEBERRIES       = REGISTRY.registerItem("smokeberries",  p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.4f).build()));
    public static final DeferredItem<Item> SOURLEAF           = block(GotModBlocks.SOURLEAF);
    public static final DeferredItem<Item> STING_ME_NOT       = block(GotModBlocks.STING_ME_NOT);
    public static final DeferredItem<Item> WASPWILLOW         = block(GotModBlocks.WASPWILLOW);

    // ── Berry produce ─────────────────────────────────────────────────────
    public static final DeferredItem<Item> BLACKBERRIES = REGISTRY.registerItem("blackberries", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.4f).build()));
    public static final DeferredItem<Item> BLUEBERRIES  = REGISTRY.registerItem("blueberries", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.4f).build()));
    public static final DeferredItem<Item> RASPBERRIES  = REGISTRY.registerItem("raspberries", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.4f).build()));
    public static final DeferredItem<Item> STRAWBERRIES = REGISTRY.registerItem("strawberries", p -> new BlockItem(GotModBlocks.STRAWBERRY_CROP.get(), p), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.5f).build()));
    // ── Berry Bush block items ────────────────────────────────────────────────
    public static final DeferredItem<Item> BLACKBERRY_BUSH  = block(GotModBlocks.BLACKBERRY_BUSH);
    public static final DeferredItem<Item> BLUEBERRY_BUSH   = block(GotModBlocks.BLUEBERRY_BUSH);
    public static final DeferredItem<Item> RASPBERRY_BUSH   = block(GotModBlocks.RASPBERRY_BUSH);
    public static final DeferredItem<Item> WILD_STRAWBERRY  = block(GotModBlocks.WILD_STRAWBERRY);


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

    // ── Steel tools ───────────────────────────────────────────────────────
    public static final DeferredItem<SwordItem>  STEEL_SWORD     = REGISTRY.registerItem("steel_sword",
            p -> new SwordItem(GotModTiers.STEEL, 3.0f, -2.4f, p));
    public static final DeferredItem<PickaxeItem> STEEL_PICKAXE  = REGISTRY.registerItem("steel_pickaxe",
            p -> new PickaxeItem(GotModTiers.STEEL, 1.0f, -2.8f, p));
    public static final DeferredItem<AxeItem>    STEEL_AXE       = REGISTRY.registerItem("steel_axe",
            p -> new AxeItem(GotModTiers.STEEL, 6.0f, -3.1f, p));
    public static final DeferredItem<ShovelItem> STEEL_SHOVEL    = REGISTRY.registerItem("steel_shovel",
            p -> new ShovelItem(GotModTiers.STEEL, 1.5f, -3.0f, p));
    public static final DeferredItem<HoeItem>    STEEL_HOE       = REGISTRY.registerItem("steel_hoe",
            p -> new HoeItem(GotModTiers.STEEL, 0.0f, -3.0f, p));

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

    // ── Steel armor ───────────────────────────────────────────────────────
    public static final DeferredItem<ArmorItem> STEEL_HELMET      = REGISTRY.registerItem("steel_helmet",
            p -> new ArmorItem(GotModArmorMaterials.STEEL.value(), ArmorType.HELMET, p));
    public static final DeferredItem<ArmorItem> STEEL_CHESTPLATE  = REGISTRY.registerItem("steel_chestplate",
            p -> new ArmorItem(GotModArmorMaterials.STEEL.value(), ArmorType.CHESTPLATE, p));
    public static final DeferredItem<ArmorItem> STEEL_LEGGINGS    = REGISTRY.registerItem("steel_leggings",
            p -> new ArmorItem(GotModArmorMaterials.STEEL.value(), ArmorType.LEGGINGS, p));
    public static final DeferredItem<ArmorItem> STEEL_BOOTS       = REGISTRY.registerItem("steel_boots",
            p -> new ArmorItem(GotModArmorMaterials.STEEL.value(), ArmorType.BOOTS, p));

    // ── Smithy components — iron ──────────────────────────────────────────────
    public static final DeferredItem<Item> IRON_SPEAR_HEAD          = simple("iron_spear_head");
    public static final DeferredItem<Item> IRON_ARROWHEAD           = simple("iron_arrowhead");
    public static final DeferredItem<Item> IRON_SHORT_AXE_HEAD      = simple("iron_short_axe_head");
    public static final DeferredItem<Item> IRON_LONG_AXE_HEAD       = simple("iron_long_axe_head");
    public static final DeferredItem<Item> IRON_LONGSWORD_BLADE     = simple("iron_longsword_blade");
    public static final DeferredItem<Item> IRON_BASTARD_SWORD_BLADE = simple("iron_bastard_sword_blade");
    public static final DeferredItem<Item> IRON_SHORTSWORD_BLADE    = simple("iron_shortsword_blade");
    public static final DeferredItem<Item> IRON_FALCHION_BLADE      = simple("iron_falchion_blade");
    public static final DeferredItem<Item> IRON_GREATSWORD_BLADE    = simple("iron_greatsword_blade");

    public static final DeferredItem<Item> IRON_ARMOR_PLATE         = simple("iron_armor_plate");
    // ── Smithy components — bronze ────────────────────────────────────────────
    public static final DeferredItem<Item> BRONZE_SPEAR_HEAD          = simple("bronze_spear_head");
    public static final DeferredItem<Item> BRONZE_ARROWHEAD           = simple("bronze_arrowhead");
    public static final DeferredItem<Item> BRONZE_SHORT_AXE_HEAD      = simple("bronze_short_axe_head");
    public static final DeferredItem<Item> BRONZE_LONG_AXE_HEAD       = simple("bronze_long_axe_head");
    public static final DeferredItem<Item> BRONZE_LONGSWORD_BLADE     = simple("bronze_longsword_blade");
    public static final DeferredItem<Item> BRONZE_BASTARD_SWORD_BLADE = simple("bronze_bastard_sword_blade");
    public static final DeferredItem<Item> BRONZE_SHORTSWORD_BLADE    = simple("bronze_shortsword_blade");
    public static final DeferredItem<Item> BRONZE_FALCHION_BLADE      = simple("bronze_falchion_blade");
    public static final DeferredItem<Item> BRONZE_GREATSWORD_BLADE    = simple("bronze_greatsword_blade");
    // ── Hilt components (wood) ────────────────────────────────────────────────
    public static final DeferredItem<Item> HILT      = simple("hilt");
    public static final DeferredItem<Item> LONG_HILT = simple("long_hilt");
    // ── Pommel & guard components (iron) ─────────────────────────────────────
    public static final DeferredItem<Item> POMMEL        = simple("pommel");
    public static final DeferredItem<Item> CROSSGUARD = simple("crossguard");
    public static final DeferredItem<Item> SLOPED_CROSSGUARD   = simple("sloped_crossguard");
    public static final DeferredItem<Item> BRONZE_ARMOR_PLATE         = simple("bronze_armor_plate");
    // ── Smithy components — steel ─────────────────────────────────────────────
    public static final DeferredItem<Item> STEEL_SPEAR_HEAD          = simple("steel_spear_head");
    public static final DeferredItem<Item> STEEL_ARROWHEAD           = simple("steel_arrowhead");
    public static final DeferredItem<Item> STEEL_SHORT_AXE_HEAD      = simple("steel_short_axe_head");
    public static final DeferredItem<Item> STEEL_LONG_AXE_HEAD       = simple("steel_long_axe_head");
    public static final DeferredItem<Item> STEEL_LONGSWORD_BLADE     = simple("steel_longsword_blade");
    public static final DeferredItem<Item> STEEL_BASTARD_SWORD_BLADE = simple("steel_bastard_sword_blade");
    public static final DeferredItem<Item> STEEL_SHORTSWORD_BLADE    = simple("steel_shortsword_blade");
    public static final DeferredItem<Item> STEEL_FALCHION_BLADE      = simple("steel_falchion_blade");
    public static final DeferredItem<Item> STEEL_GREATSWORD_BLADE    = simple("steel_greatsword_blade");

    public static final DeferredItem<Item> STEEL_ARMOR_PLATE         = simple("steel_armor_plate");

    // ── Assembled swords (blade + hilt + crossguard + pommel) ────────────────
    // Iron swords
    // Iron greatsword variants (crossguard × pommel)
    public static final DeferredItem<SwordItem> IRON_GREATSWORD_CROSSGUARD_POMMEL   = REGISTRY.registerItem("iron_greatsword_crossguard_pommel",   p -> new SwordItem(ToolMaterial.IRON, 4.5f, -2.8f, p));

    // Iron sword variants (crossguard × pommel)
    public static final DeferredItem<SwordItem> IRON_SHORTSWORD_CROSSGUARD_POMMEL = REGISTRY.registerItem("iron_shortsword_crossguard_pommel", p -> new SwordItem(ToolMaterial.IRON, 2.0f, -2.4f, p));
    public static final DeferredItem<SwordItem> IRON_LONGSWORD_CROSSGUARD_POMMEL = REGISTRY.registerItem("iron_longsword_crossguard_pommel", p -> new SwordItem(ToolMaterial.IRON, 3.0f, -2.4f, p));
    public static final DeferredItem<SwordItem> IRON_BASTARD_SWORD_CROSSGUARD_POMMEL = REGISTRY.registerItem("iron_bastard_sword_crossguard_pommel", p -> new SwordItem(ToolMaterial.IRON, 3.5f, -2.5f, p));
    public static final DeferredItem<SwordItem> IRON_FALCHION_CROSSGUARD_POMMEL = REGISTRY.registerItem("iron_falchion_crossguard_pommel", p -> new SwordItem(ToolMaterial.IRON, 2.5f, -2.3f, p));
    public static final DeferredItem<SwordItem> IRON_CLAYMORE_SLOPED_CROSSGUARD_POMMEL = REGISTRY.registerItem("iron_claymore_sloped_crossguard_pommel", p -> new SwordItem(ToolMaterial.IRON, 4.5f, -2.8f, p));
    // Bronze swords
    // Bronze greatsword variants (crossguard × pommel)
    public static final DeferredItem<SwordItem> BRONZE_GREATSWORD_CROSSGUARD_POMMEL   = REGISTRY.registerItem("bronze_greatsword_crossguard_pommel",   p -> new SwordItem(GotModTiers.BRONZE, 4.5f, -2.8f, p));

    // Bronze sword variants (crossguard × pommel)
    public static final DeferredItem<SwordItem> BRONZE_SHORTSWORD_CROSSGUARD_POMMEL = REGISTRY.registerItem("bronze_shortsword_crossguard_pommel", p -> new SwordItem(GotModTiers.BRONZE, 2.0f, -2.4f, p));
    public static final DeferredItem<SwordItem> BRONZE_LONGSWORD_CROSSGUARD_POMMEL = REGISTRY.registerItem("bronze_longsword_crossguard_pommel", p -> new SwordItem(GotModTiers.BRONZE, 3.0f, -2.4f, p));
    public static final DeferredItem<SwordItem> BRONZE_BASTARD_SWORD_CROSSGUARD_POMMEL = REGISTRY.registerItem("bronze_bastard_sword_crossguard_pommel", p -> new SwordItem(GotModTiers.BRONZE, 3.5f, -2.5f, p));
    public static final DeferredItem<SwordItem> BRONZE_FALCHION_CROSSGUARD_POMMEL = REGISTRY.registerItem("bronze_falchion_crossguard_pommel", p -> new SwordItem(GotModTiers.BRONZE, 2.5f, -2.3f, p));
    public static final DeferredItem<SwordItem> BRONZE_CLAYMORE_SLOPED_CROSSGUARD_POMMEL = REGISTRY.registerItem("bronze_claymore_sloped_crossguard_pommel", p -> new SwordItem(GotModTiers.BRONZE, 4.5f, -2.8f, p));
    // Steel swords
    // Steel greatsword variants (crossguard × pommel)
    public static final DeferredItem<SwordItem> STEEL_GREATSWORD_CROSSGUARD_POMMEL   = REGISTRY.registerItem("steel_greatsword_crossguard_pommel",   p -> new SwordItem(GotModTiers.STEEL, 4.5f, -2.8f, p));

    // Steel sword variants (crossguard × pommel)
    public static final DeferredItem<SwordItem> STEEL_SHORTSWORD_CROSSGUARD_POMMEL = REGISTRY.registerItem("steel_shortsword_crossguard_pommel", p -> new SwordItem(GotModTiers.STEEL, 2.0f, -2.4f, p));
    public static final DeferredItem<SwordItem> STEEL_LONGSWORD_CROSSGUARD_POMMEL = REGISTRY.registerItem("steel_longsword_crossguard_pommel", p -> new SwordItem(GotModTiers.STEEL, 3.0f, -2.4f, p));
    public static final DeferredItem<SwordItem> STEEL_BASTARD_SWORD_CROSSGUARD_POMMEL = REGISTRY.registerItem("steel_bastard_sword_crossguard_pommel", p -> new SwordItem(GotModTiers.STEEL, 3.5f, -2.5f, p));
    public static final DeferredItem<SwordItem> STEEL_FALCHION_CROSSGUARD_POMMEL = REGISTRY.registerItem("steel_falchion_crossguard_pommel", p -> new SwordItem(GotModTiers.STEEL, 2.5f, -2.3f, p));
    public static final DeferredItem<SwordItem> STEEL_CLAYMORE_SLOPED_CROSSGUARD_POMMEL = REGISTRY.registerItem("steel_claymore_sloped_crossguard_pommel", p -> new SwordItem(GotModTiers.STEEL, 4.5f, -2.8f, p));


    // ── Smallfolk NPC Spawn Eggs ─────────────────────────────────────────────
    public static final DeferredItem<SpawnEggItem> NORTHMAN_SPAWN_EGG = REGISTRY.registerItem(
            "northman_spawn_egg", p -> new SpawnEggItem(GotModEntities.NORTHMAN.get(), p));
    public static final DeferredItem<SpawnEggItem> RIVERLANDER_SPAWN_EGG = REGISTRY.registerItem(
            "riverlander_spawn_egg", p -> new SpawnEggItem(GotModEntities.RIVERLANDER.get(), p));
    public static final DeferredItem<SpawnEggItem> VALEMAN_SPAWN_EGG = REGISTRY.registerItem(
            "valeman_spawn_egg", p -> new SpawnEggItem(GotModEntities.VALEMAN.get(), p));
    public static final DeferredItem<SpawnEggItem> IRONBORN_SPAWN_EGG = REGISTRY.registerItem(
            "ironborn_spawn_egg", p -> new SpawnEggItem(GotModEntities.IRONBORN.get(), p));
    public static final DeferredItem<SpawnEggItem> WESTERMAN_SPAWN_EGG = REGISTRY.registerItem(
            "westerman_spawn_egg", p -> new SpawnEggItem(GotModEntities.WESTERMAN.get(), p));
    public static final DeferredItem<SpawnEggItem> REACHMAN_SPAWN_EGG = REGISTRY.registerItem(
            "reachman_spawn_egg", p -> new SpawnEggItem(GotModEntities.REACHMAN.get(), p));
    public static final DeferredItem<SpawnEggItem> STORMLORDER_SPAWN_EGG = REGISTRY.registerItem(
            "stormlorder_spawn_egg", p -> new SpawnEggItem(GotModEntities.STORMLORDER.get(), p));
    public static final DeferredItem<SpawnEggItem> DORNISHMAN_SPAWN_EGG = REGISTRY.registerItem(
            "dornishman_spawn_egg", p -> new SpawnEggItem(GotModEntities.DORNISHMAN.get(), p));

    // ── Levy Spawn Eggs (Tier 2) ─────────────────────────────────────────────
    public static final DeferredItem<SpawnEggItem> STARK_LEVY_SPAWN_EGG = REGISTRY.registerItem(
            "stark_levy_spawn_egg", p -> new SpawnEggItem(GotModEntities.STARK_LEVY.get(), p));
    public static final DeferredItem<SpawnEggItem> TULLY_LEVY_SPAWN_EGG = REGISTRY.registerItem(
            "tully_levy_spawn_egg", p -> new SpawnEggItem(GotModEntities.TULLY_LEVY.get(), p));
    public static final DeferredItem<SpawnEggItem> LANNISTER_LEVY_SPAWN_EGG = REGISTRY.registerItem(
            "lannister_levy_spawn_egg", p -> new SpawnEggItem(GotModEntities.LANNISTER_LEVY.get(), p));
    public static final DeferredItem<SpawnEggItem> BARATHEON_LEVY_SPAWN_EGG = REGISTRY.registerItem(
            "baratheon_levy_spawn_egg", p -> new SpawnEggItem(GotModEntities.BARATHEON_LEVY.get(), p));
    public static final DeferredItem<SpawnEggItem> GREYJOY_LEVY_SPAWN_EGG = REGISTRY.registerItem(
            "greyjoy_levy_spawn_egg", p -> new SpawnEggItem(GotModEntities.GREYJOY_LEVY.get(), p));
    public static final DeferredItem<SpawnEggItem> MARTELL_LEVY_SPAWN_EGG = REGISTRY.registerItem(
            "martell_levy_spawn_egg", p -> new SpawnEggItem(GotModEntities.MARTELL_LEVY.get(), p));
    public static final DeferredItem<SpawnEggItem> TYRELL_LEVY_SPAWN_EGG = REGISTRY.registerItem(
            "tyrell_levy_spawn_egg", p -> new SpawnEggItem(GotModEntities.TYRELL_LEVY.get(), p));
    public static final DeferredItem<SpawnEggItem> ARRYN_LEVY_SPAWN_EGG = REGISTRY.registerItem(
            "arryn_levy_spawn_egg", p -> new SpawnEggItem(GotModEntities.ARRYN_LEVY.get(), p));

    // ── Skilled Fighter Spawn Eggs (Tier 3) ──────────────────────────────────
    public static final DeferredItem<SpawnEggItem> NORTH_SOLDIER_SPAWN_EGG = REGISTRY.registerItem(
            "north_soldier_spawn_egg", p -> new SpawnEggItem(GotModEntities.NORTH_SOLDIER.get(), p));
    public static final DeferredItem<SpawnEggItem> VALE_KNIGHT_SPAWN_EGG = REGISTRY.registerItem(
            "vale_knight_spawn_egg", p -> new SpawnEggItem(GotModEntities.VALE_KNIGHT.get(), p));

    // ── Mount & Wildlife Spawn Eggs ───────────────────────────────────────────

    /** Stag spawn egg — bark brown with antler-cream spots. */
    public static final DeferredItem<SpawnEggItem> GOT_STAG_SPAWN_EGG = REGISTRY.registerItem(
            "got_stag_spawn_egg", p -> new SpawnEggItem(GotModEntities.GOT_STAG.get(), p));

    /** Heron spawn egg — pale grey body with a yellow-gold beak accent. */
    public static final DeferredItem<SpawnEggItem> GOT_HERON_SPAWN_EGG = REGISTRY.registerItem(
            "got_heron_spawn_egg", p -> new SpawnEggItem(GotModEntities.GOT_HERON.get(), p));

    /** Direwolf spawn egg — dark grey fur with pale grey underbelly. */
    public static final DeferredItem<SpawnEggItem> GOT_DIREWOLF_SPAWN_EGG = REGISTRY.registerItem(
            "got_direwolf_spawn_egg", p -> new SpawnEggItem(GotModEntities.GOT_DIREWOLF.get(), p));

    /** Crow spawn egg — jet black body with dark grey wing-tip accent. */
    public static final DeferredItem<SpawnEggItem> GOT_CROW_SPAWN_EGG = REGISTRY.registerItem(
            "got_crow_spawn_egg", p -> new SpawnEggItem(GotModEntities.GOT_CROW.get(), p));

    /** Mammoth spawn egg — shaggy dark brown body with ivory tusk accent. */
    public static final DeferredItem<SpawnEggItem> GOT_MAMMOTH_SPAWN_EGG = REGISTRY.registerItem(
            "got_mammoth_spawn_egg", p -> new SpawnEggItem(GotModEntities.GOT_MAMMOTH.get(), p));

    /** Brown Bear spawn egg — shaggy brown fur with light brown paw accent. */
    public static final DeferredItem<SpawnEggItem> GOT_BROWN_BEAR_SPAWN_EGG = REGISTRY.registerItem(
            "got_brown_bear_spawn_egg", p -> new SpawnEggItem(GotModEntities.GOT_BROWN_BEAR.get(), p));

    // ── Great House Banner Pattern Items (used in the loom) ──────────────────

    public static final DeferredItem<BannerPatternItem> STARK_BANNER_PATTERN =
            REGISTRY.registerItem("stark_banner_pattern",
                    p -> new BannerPatternItem(GotModBannerPatterns.STARK_PATTERN_TAG, p));
    public static final DeferredItem<BannerPatternItem> LANNISTER_BANNER_PATTERN =
            REGISTRY.registerItem("lannister_banner_pattern",
                    p -> new BannerPatternItem(GotModBannerPatterns.LANNISTER_PATTERN_TAG, p));
    public static final DeferredItem<BannerPatternItem> TARGARYEN_BANNER_PATTERN =
            REGISTRY.registerItem("targaryen_banner_pattern",
                    p -> new BannerPatternItem(GotModBannerPatterns.TARGARYEN_PATTERN_TAG, p));
    public static final DeferredItem<BannerPatternItem> BARATHEON_BANNER_PATTERN =
            REGISTRY.registerItem("baratheon_banner_pattern",
                    p -> new BannerPatternItem(GotModBannerPatterns.BARATHEON_PATTERN_TAG, p));
    public static final DeferredItem<BannerPatternItem> GREYJOY_BANNER_PATTERN =
            REGISTRY.registerItem("greyjoy_banner_pattern",
                    p -> new BannerPatternItem(GotModBannerPatterns.GREYJOY_PATTERN_TAG, p));
    public static final DeferredItem<BannerPatternItem> TYRELL_BANNER_PATTERN =
            REGISTRY.registerItem("tyrell_banner_pattern",
                    p -> new BannerPatternItem(GotModBannerPatterns.TYRELL_PATTERN_TAG, p));
    public static final DeferredItem<BannerPatternItem> MARTELL_BANNER_PATTERN =
            REGISTRY.registerItem("martell_banner_pattern",
                    p -> new BannerPatternItem(GotModBannerPatterns.MARTELL_PATTERN_TAG, p));
    public static final DeferredItem<BannerPatternItem> TULLY_BANNER_PATTERN =
            REGISTRY.registerItem("tully_banner_pattern",
                    p -> new BannerPatternItem(GotModBannerPatterns.TULLY_PATTERN_TAG, p));
    public static final DeferredItem<BannerPatternItem> ARRYN_BANNER_PATTERN =
            REGISTRY.registerItem("arryn_banner_pattern",
                    p -> new BannerPatternItem(GotModBannerPatterns.ARRYN_PATTERN_TAG, p));
    public static final DeferredItem<BannerPatternItem> BOLTON_BANNER_PATTERN =
            REGISTRY.registerItem("bolton_banner_pattern",
                    p -> new BannerPatternItem(GotModBannerPatterns.BOLTON_PATTERN_TAG, p));

    // ── Utility block items ───────────────────────────────────────────────────
    public static final DeferredItem<Item> OVEN = block(GotModBlocks.OVEN);
    public static final DeferredItem<Item> FORGE = block(GotModBlocks.FORGE);
    public static final DeferredItem<Item> SMITHING_ANVIL = block(GotModBlocks.SMITHING_ANVIL);
    public static final DeferredItem<Item> SMITHING_HAMMER = REGISTRY.registerItem("smithing_hammer",
            p -> new net.got.item.SmithingHammerItem(p.durability(250)));
    public static final DeferredItem<Item> BELLOWS = block(GotModBlocks.BELLOWS);

    // ── Helpers ───────────────────────────────────────────────────────────

    private static DeferredItem<Item> simple(String name) {
        return REGISTRY.registerSimpleItem(name);
    }
    // ── nightwood items ──
    public static final DeferredItem<Item> NIGHTWOOD_LOG            = block(GotModBlocks.NIGHTWOOD_LOG);
    public static final DeferredItem<Item> NIGHTWOOD_WOOD           = block(GotModBlocks.NIGHTWOOD_WOOD);
    public static final DeferredItem<Item> NIGHTWOOD_PLANKS         = block(GotModBlocks.NIGHTWOOD_PLANKS);
    public static final DeferredItem<Item> NIGHTWOOD_LEAVES         = block(GotModBlocks.NIGHTWOOD_LEAVES);
    public static final DeferredItem<Item> NIGHTWOOD_STAIRS         = block(GotModBlocks.NIGHTWOOD_STAIRS);
    public static final DeferredItem<Item> NIGHTWOOD_SLAB           = block(GotModBlocks.NIGHTWOOD_SLAB);
    public static final DeferredItem<Item> NIGHTWOOD_FENCE          = block(GotModBlocks.NIGHTWOOD_FENCE);
    public static final DeferredItem<Item> NIGHTWOOD_FENCE_GATE     = block(GotModBlocks.NIGHTWOOD_FENCE_GATE);
    public static final DeferredItem<Item> NIGHTWOOD_PRESSURE_PLATE = block(GotModBlocks.NIGHTWOOD_PRESSURE_PLATE);
    public static final DeferredItem<Item> NIGHTWOOD_BUTTON         = block(GotModBlocks.NIGHTWOOD_BUTTON);
    public static final DeferredItem<Item> NIGHTWOOD_DOOR           = door(GotModBlocks.NIGHTWOOD_DOOR);
    public static final DeferredItem<Item> NIGHTWOOD_TRAPDOOR       = block(GotModBlocks.NIGHTWOOD_TRAPDOOR);
    public static final DeferredItem<Item> NIGHTWOOD_BRANCH         = block(GotModBlocks.NIGHTWOOD_BRANCH);
    public static final DeferredItem<Item> STRIPPED_NIGHTWOOD_BRANCH = block(GotModBlocks.STRIPPED_NIGHTWOOD_BRANCH);
    public static final DeferredItem<Item> NIGHTWOOD_SIGN           = REGISTRY.registerItem("nightwood_sign",         p -> new SignItem(GotModBlocks.NIGHTWOOD_SIGN.get(), GotModBlocks.NIGHTWOOD_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> NIGHTWOOD_HANGING_SIGN   = REGISTRY.registerItem("nightwood_hanging_sign", p -> new HangingSignItem(GotModBlocks.NIGHTWOOD_HANGING_SIGN.get(), GotModBlocks.NIGHTWOOD_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> NIGHTWOOD_BOAT           = REGISTRY.registerItem("nightwood_boat",       p -> new GotBoatItem(GotModBoatEntities.NIGHTWOOD_BOAT.get(), p));
    public static final DeferredItem<Item> NIGHTWOOD_CHEST_BOAT     = REGISTRY.registerItem("nightwood_chest_boat", p -> new GotBoatItem(GotModBoatEntities.NIGHTWOOD_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> NIGHTWOOD_SAPLING        = block(GotModBlocks.NIGHTWOOD_SAPLING);
    public static final DeferredItem<Item> STRIPPED_NIGHTWOOD_LOG   = block(GotModBlocks.STRIPPED_NIGHTWOOD_LOG);
    public static final DeferredItem<Item> STRIPPED_NIGHTWOOD_WOOD  = block(GotModBlocks.STRIPPED_NIGHTWOOD_WOOD);
    public static final DeferredItem<BlockItem> NIGHTWOOD_ROOFING = REGISTRY.registerSimpleBlockItem("nightwood_roofing", GotModBlocks.NIGHTWOOD_ROOFING);
    public static final DeferredItem<BlockItem> NIGHTWOOD_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("nightwood_roofing_slab", GotModBlocks.NIGHTWOOD_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> NIGHTWOOD_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("nightwood_roofing_stairs", GotModBlocks.NIGHTWOOD_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> NIGHTWOOD_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("nightwood_roofing_wall", GotModBlocks.NIGHTWOOD_ROOFING_WALL);

    // ── purpleheart items ──
    public static final DeferredItem<Item> PURPLEHEART_LOG            = block(GotModBlocks.PURPLEHEART_LOG);
    public static final DeferredItem<Item> PURPLEHEART_WOOD           = block(GotModBlocks.PURPLEHEART_WOOD);
    public static final DeferredItem<Item> PURPLEHEART_PLANKS         = block(GotModBlocks.PURPLEHEART_PLANKS);
    public static final DeferredItem<Item> PURPLEHEART_LEAVES         = block(GotModBlocks.PURPLEHEART_LEAVES);
    public static final DeferredItem<Item> PURPLEHEART_STAIRS         = block(GotModBlocks.PURPLEHEART_STAIRS);
    public static final DeferredItem<Item> PURPLEHEART_SLAB           = block(GotModBlocks.PURPLEHEART_SLAB);
    public static final DeferredItem<Item> PURPLEHEART_FENCE          = block(GotModBlocks.PURPLEHEART_FENCE);
    public static final DeferredItem<Item> PURPLEHEART_FENCE_GATE     = block(GotModBlocks.PURPLEHEART_FENCE_GATE);
    public static final DeferredItem<Item> PURPLEHEART_PRESSURE_PLATE = block(GotModBlocks.PURPLEHEART_PRESSURE_PLATE);
    public static final DeferredItem<Item> PURPLEHEART_BUTTON         = block(GotModBlocks.PURPLEHEART_BUTTON);
    public static final DeferredItem<Item> PURPLEHEART_DOOR           = door(GotModBlocks.PURPLEHEART_DOOR);
    public static final DeferredItem<Item> PURPLEHEART_TRAPDOOR       = block(GotModBlocks.PURPLEHEART_TRAPDOOR);
    public static final DeferredItem<Item> PURPLEHEART_BRANCH         = block(GotModBlocks.PURPLEHEART_BRANCH);
    public static final DeferredItem<Item> STRIPPED_PURPLEHEART_BRANCH = block(GotModBlocks.STRIPPED_PURPLEHEART_BRANCH);
    public static final DeferredItem<Item> PURPLEHEART_SIGN           = REGISTRY.registerItem("purpleheart_sign",         p -> new SignItem(GotModBlocks.PURPLEHEART_SIGN.get(), GotModBlocks.PURPLEHEART_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> PURPLEHEART_HANGING_SIGN   = REGISTRY.registerItem("purpleheart_hanging_sign", p -> new HangingSignItem(GotModBlocks.PURPLEHEART_HANGING_SIGN.get(), GotModBlocks.PURPLEHEART_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> PURPLEHEART_BOAT           = REGISTRY.registerItem("purpleheart_boat",       p -> new GotBoatItem(GotModBoatEntities.PURPLEHEART_BOAT.get(), p));
    public static final DeferredItem<Item> PURPLEHEART_CHEST_BOAT     = REGISTRY.registerItem("purpleheart_chest_boat", p -> new GotBoatItem(GotModBoatEntities.PURPLEHEART_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> PURPLEHEART_SAPLING        = block(GotModBlocks.PURPLEHEART_SAPLING);
    public static final DeferredItem<Item> STRIPPED_PURPLEHEART_LOG   = block(GotModBlocks.STRIPPED_PURPLEHEART_LOG);
    public static final DeferredItem<Item> STRIPPED_PURPLEHEART_WOOD  = block(GotModBlocks.STRIPPED_PURPLEHEART_WOOD);
    public static final DeferredItem<BlockItem> PURPLEHEART_ROOFING = REGISTRY.registerSimpleBlockItem("purpleheart_roofing", GotModBlocks.PURPLEHEART_ROOFING);
    public static final DeferredItem<BlockItem> PURPLEHEART_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("purpleheart_roofing_slab", GotModBlocks.PURPLEHEART_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> PURPLEHEART_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("purpleheart_roofing_stairs", GotModBlocks.PURPLEHEART_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> PURPLEHEART_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("purpleheart_roofing_wall", GotModBlocks.PURPLEHEART_ROOFING_WALL);

    // ── tigerwood items ──
    public static final DeferredItem<Item> TIGERWOOD_LOG            = block(GotModBlocks.TIGERWOOD_LOG);
    public static final DeferredItem<Item> TIGERWOOD_WOOD           = block(GotModBlocks.TIGERWOOD_WOOD);
    public static final DeferredItem<Item> TIGERWOOD_PLANKS         = block(GotModBlocks.TIGERWOOD_PLANKS);
    public static final DeferredItem<Item> TIGERWOOD_LEAVES         = block(GotModBlocks.TIGERWOOD_LEAVES);
    public static final DeferredItem<Item> TIGERWOOD_STAIRS         = block(GotModBlocks.TIGERWOOD_STAIRS);
    public static final DeferredItem<Item> TIGERWOOD_SLAB           = block(GotModBlocks.TIGERWOOD_SLAB);
    public static final DeferredItem<Item> TIGERWOOD_FENCE          = block(GotModBlocks.TIGERWOOD_FENCE);
    public static final DeferredItem<Item> TIGERWOOD_FENCE_GATE     = block(GotModBlocks.TIGERWOOD_FENCE_GATE);
    public static final DeferredItem<Item> TIGERWOOD_PRESSURE_PLATE = block(GotModBlocks.TIGERWOOD_PRESSURE_PLATE);
    public static final DeferredItem<Item> TIGERWOOD_BUTTON         = block(GotModBlocks.TIGERWOOD_BUTTON);
    public static final DeferredItem<Item> TIGERWOOD_DOOR           = door(GotModBlocks.TIGERWOOD_DOOR);
    public static final DeferredItem<Item> TIGERWOOD_TRAPDOOR       = block(GotModBlocks.TIGERWOOD_TRAPDOOR);
    public static final DeferredItem<Item> TIGERWOOD_BRANCH         = block(GotModBlocks.TIGERWOOD_BRANCH);
    public static final DeferredItem<Item> STRIPPED_TIGERWOOD_BRANCH = block(GotModBlocks.STRIPPED_TIGERWOOD_BRANCH);
    public static final DeferredItem<Item> TIGERWOOD_SIGN           = REGISTRY.registerItem("tigerwood_sign",         p -> new SignItem(GotModBlocks.TIGERWOOD_SIGN.get(), GotModBlocks.TIGERWOOD_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> TIGERWOOD_HANGING_SIGN   = REGISTRY.registerItem("tigerwood_hanging_sign", p -> new HangingSignItem(GotModBlocks.TIGERWOOD_HANGING_SIGN.get(), GotModBlocks.TIGERWOOD_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> TIGERWOOD_BOAT           = REGISTRY.registerItem("tigerwood_boat",       p -> new GotBoatItem(GotModBoatEntities.TIGERWOOD_BOAT.get(), p));
    public static final DeferredItem<Item> TIGERWOOD_CHEST_BOAT     = REGISTRY.registerItem("tigerwood_chest_boat", p -> new GotBoatItem(GotModBoatEntities.TIGERWOOD_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> TIGERWOOD_SAPLING        = block(GotModBlocks.TIGERWOOD_SAPLING);
    public static final DeferredItem<Item> STRIPPED_TIGERWOOD_LOG   = block(GotModBlocks.STRIPPED_TIGERWOOD_LOG);
    public static final DeferredItem<Item> STRIPPED_TIGERWOOD_WOOD  = block(GotModBlocks.STRIPPED_TIGERWOOD_WOOD);
    public static final DeferredItem<BlockItem> TIGERWOOD_ROOFING = REGISTRY.registerSimpleBlockItem("tigerwood_roofing", GotModBlocks.TIGERWOOD_ROOFING);
    public static final DeferredItem<BlockItem> TIGERWOOD_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("tigerwood_roofing_slab", GotModBlocks.TIGERWOOD_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> TIGERWOOD_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("tigerwood_roofing_stairs", GotModBlocks.TIGERWOOD_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> TIGERWOOD_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("tigerwood_roofing_wall", GotModBlocks.TIGERWOOD_ROOFING_WALL);

    // ── burl items ──
    public static final DeferredItem<Item> BURL_LOG            = block(GotModBlocks.BURL_LOG);
    public static final DeferredItem<Item> BURL_WOOD           = block(GotModBlocks.BURL_WOOD);
    public static final DeferredItem<Item> BURL_PLANKS         = block(GotModBlocks.BURL_PLANKS);
    public static final DeferredItem<Item> BURL_LEAVES         = block(GotModBlocks.BURL_LEAVES);
    public static final DeferredItem<Item> BURL_STAIRS         = block(GotModBlocks.BURL_STAIRS);
    public static final DeferredItem<Item> BURL_SLAB           = block(GotModBlocks.BURL_SLAB);
    public static final DeferredItem<Item> BURL_FENCE          = block(GotModBlocks.BURL_FENCE);
    public static final DeferredItem<Item> BURL_FENCE_GATE     = block(GotModBlocks.BURL_FENCE_GATE);
    public static final DeferredItem<Item> BURL_PRESSURE_PLATE = block(GotModBlocks.BURL_PRESSURE_PLATE);
    public static final DeferredItem<Item> BURL_BUTTON         = block(GotModBlocks.BURL_BUTTON);
    public static final DeferredItem<Item> BURL_DOOR           = door(GotModBlocks.BURL_DOOR);
    public static final DeferredItem<Item> BURL_TRAPDOOR       = block(GotModBlocks.BURL_TRAPDOOR);
    public static final DeferredItem<Item> BURL_BRANCH         = block(GotModBlocks.BURL_BRANCH);
    public static final DeferredItem<Item> STRIPPED_BURL_BRANCH = block(GotModBlocks.STRIPPED_BURL_BRANCH);
    public static final DeferredItem<Item> BURL_SIGN           = REGISTRY.registerItem("burl_sign",         p -> new SignItem(GotModBlocks.BURL_SIGN.get(), GotModBlocks.BURL_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> BURL_HANGING_SIGN   = REGISTRY.registerItem("burl_hanging_sign", p -> new HangingSignItem(GotModBlocks.BURL_HANGING_SIGN.get(), GotModBlocks.BURL_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> BURL_BOAT           = REGISTRY.registerItem("burl_boat",       p -> new GotBoatItem(GotModBoatEntities.BURL_BOAT.get(), p));
    public static final DeferredItem<Item> BURL_CHEST_BOAT     = REGISTRY.registerItem("burl_chest_boat", p -> new GotBoatItem(GotModBoatEntities.BURL_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> BURL_SAPLING        = block(GotModBlocks.BURL_SAPLING);
    public static final DeferredItem<Item> STRIPPED_BURL_LOG   = block(GotModBlocks.STRIPPED_BURL_LOG);
    public static final DeferredItem<Item> STRIPPED_BURL_WOOD  = block(GotModBlocks.STRIPPED_BURL_WOOD);
    public static final DeferredItem<BlockItem> BURL_ROOFING = REGISTRY.registerSimpleBlockItem("burl_roofing", GotModBlocks.BURL_ROOFING);
    public static final DeferredItem<BlockItem> BURL_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("burl_roofing_slab", GotModBlocks.BURL_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> BURL_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("burl_roofing_stairs", GotModBlocks.BURL_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> BURL_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("burl_roofing_wall", GotModBlocks.BURL_ROOFING_WALL);

    // ── sandalwood items ──
    public static final DeferredItem<Item> SANDALWOOD_LOG            = block(GotModBlocks.SANDALWOOD_LOG);
    public static final DeferredItem<Item> SANDALWOOD_WOOD           = block(GotModBlocks.SANDALWOOD_WOOD);
    public static final DeferredItem<Item> SANDALWOOD_PLANKS         = block(GotModBlocks.SANDALWOOD_PLANKS);
    public static final DeferredItem<Item> SANDALWOOD_LEAVES         = block(GotModBlocks.SANDALWOOD_LEAVES);
    public static final DeferredItem<Item> SANDALWOOD_STAIRS         = block(GotModBlocks.SANDALWOOD_STAIRS);
    public static final DeferredItem<Item> SANDALWOOD_SLAB           = block(GotModBlocks.SANDALWOOD_SLAB);
    public static final DeferredItem<Item> SANDALWOOD_FENCE          = block(GotModBlocks.SANDALWOOD_FENCE);
    public static final DeferredItem<Item> SANDALWOOD_FENCE_GATE     = block(GotModBlocks.SANDALWOOD_FENCE_GATE);
    public static final DeferredItem<Item> SANDALWOOD_PRESSURE_PLATE = block(GotModBlocks.SANDALWOOD_PRESSURE_PLATE);
    public static final DeferredItem<Item> SANDALWOOD_BUTTON         = block(GotModBlocks.SANDALWOOD_BUTTON);
    public static final DeferredItem<Item> SANDALWOOD_DOOR           = door(GotModBlocks.SANDALWOOD_DOOR);
    public static final DeferredItem<Item> SANDALWOOD_TRAPDOOR       = block(GotModBlocks.SANDALWOOD_TRAPDOOR);
    public static final DeferredItem<Item> SANDALWOOD_BRANCH         = block(GotModBlocks.SANDALWOOD_BRANCH);
    public static final DeferredItem<Item> STRIPPED_SANDALWOOD_BRANCH = block(GotModBlocks.STRIPPED_SANDALWOOD_BRANCH);
    public static final DeferredItem<Item> SANDALWOOD_SIGN           = REGISTRY.registerItem("sandalwood_sign",         p -> new SignItem(GotModBlocks.SANDALWOOD_SIGN.get(), GotModBlocks.SANDALWOOD_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> SANDALWOOD_HANGING_SIGN   = REGISTRY.registerItem("sandalwood_hanging_sign", p -> new HangingSignItem(GotModBlocks.SANDALWOOD_HANGING_SIGN.get(), GotModBlocks.SANDALWOOD_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> SANDALWOOD_BOAT           = REGISTRY.registerItem("sandalwood_boat",       p -> new GotBoatItem(GotModBoatEntities.SANDALWOOD_BOAT.get(), p));
    public static final DeferredItem<Item> SANDALWOOD_CHEST_BOAT     = REGISTRY.registerItem("sandalwood_chest_boat", p -> new GotBoatItem(GotModBoatEntities.SANDALWOOD_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> SANDALWOOD_SAPLING        = block(GotModBlocks.SANDALWOOD_SAPLING);
    public static final DeferredItem<Item> STRIPPED_SANDALWOOD_LOG   = block(GotModBlocks.STRIPPED_SANDALWOOD_LOG);
    public static final DeferredItem<Item> STRIPPED_SANDALWOOD_WOOD  = block(GotModBlocks.STRIPPED_SANDALWOOD_WOOD);
    public static final DeferredItem<BlockItem> SANDALWOOD_ROOFING = REGISTRY.registerSimpleBlockItem("sandalwood_roofing", GotModBlocks.SANDALWOOD_ROOFING);
    public static final DeferredItem<BlockItem> SANDALWOOD_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("sandalwood_roofing_slab", GotModBlocks.SANDALWOOD_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> SANDALWOOD_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("sandalwood_roofing_stairs", GotModBlocks.SANDALWOOD_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> SANDALWOOD_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("sandalwood_roofing_wall", GotModBlocks.SANDALWOOD_ROOFING_WALL);

    // ── sandbeggar items ──
    public static final DeferredItem<Item> SANDBEGGAR_LOG            = block(GotModBlocks.SANDBEGGAR_LOG);
    public static final DeferredItem<Item> SANDBEGGAR_WOOD           = block(GotModBlocks.SANDBEGGAR_WOOD);
    public static final DeferredItem<Item> SANDBEGGAR_PLANKS         = block(GotModBlocks.SANDBEGGAR_PLANKS);
    public static final DeferredItem<Item> SANDBEGGAR_LEAVES         = block(GotModBlocks.SANDBEGGAR_LEAVES);
    public static final DeferredItem<Item> SANDBEGGAR_STAIRS         = block(GotModBlocks.SANDBEGGAR_STAIRS);
    public static final DeferredItem<Item> SANDBEGGAR_SLAB           = block(GotModBlocks.SANDBEGGAR_SLAB);
    public static final DeferredItem<Item> SANDBEGGAR_FENCE          = block(GotModBlocks.SANDBEGGAR_FENCE);
    public static final DeferredItem<Item> SANDBEGGAR_FENCE_GATE     = block(GotModBlocks.SANDBEGGAR_FENCE_GATE);
    public static final DeferredItem<Item> SANDBEGGAR_PRESSURE_PLATE = block(GotModBlocks.SANDBEGGAR_PRESSURE_PLATE);
    public static final DeferredItem<Item> SANDBEGGAR_BUTTON         = block(GotModBlocks.SANDBEGGAR_BUTTON);
    public static final DeferredItem<Item> SANDBEGGAR_DOOR           = door(GotModBlocks.SANDBEGGAR_DOOR);
    public static final DeferredItem<Item> SANDBEGGAR_TRAPDOOR       = block(GotModBlocks.SANDBEGGAR_TRAPDOOR);
    public static final DeferredItem<Item> SANDBEGGAR_BRANCH         = block(GotModBlocks.SANDBEGGAR_BRANCH);
    public static final DeferredItem<Item> STRIPPED_SANDBEGGAR_BRANCH = block(GotModBlocks.STRIPPED_SANDBEGGAR_BRANCH);
    public static final DeferredItem<Item> SANDBEGGAR_SIGN           = REGISTRY.registerItem("sandbeggar_sign",         p -> new SignItem(GotModBlocks.SANDBEGGAR_SIGN.get(), GotModBlocks.SANDBEGGAR_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> SANDBEGGAR_HANGING_SIGN   = REGISTRY.registerItem("sandbeggar_hanging_sign", p -> new HangingSignItem(GotModBlocks.SANDBEGGAR_HANGING_SIGN.get(), GotModBlocks.SANDBEGGAR_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> SANDBEGGAR_BOAT           = REGISTRY.registerItem("sandbeggar_boat",       p -> new GotBoatItem(GotModBoatEntities.SANDBEGGAR_BOAT.get(), p));
    public static final DeferredItem<Item> SANDBEGGAR_CHEST_BOAT     = REGISTRY.registerItem("sandbeggar_chest_boat", p -> new GotBoatItem(GotModBoatEntities.SANDBEGGAR_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> SANDBEGGAR_SAPLING        = block(GotModBlocks.SANDBEGGAR_SAPLING);
    public static final DeferredItem<Item> STRIPPED_SANDBEGGAR_LOG   = block(GotModBlocks.STRIPPED_SANDBEGGAR_LOG);
    public static final DeferredItem<Item> STRIPPED_SANDBEGGAR_WOOD  = block(GotModBlocks.STRIPPED_SANDBEGGAR_WOOD);
    public static final DeferredItem<BlockItem> SANDBEGGAR_ROOFING = REGISTRY.registerSimpleBlockItem("sandbeggar_roofing", GotModBlocks.SANDBEGGAR_ROOFING);
    public static final DeferredItem<BlockItem> SANDBEGGAR_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("sandbeggar_roofing_slab", GotModBlocks.SANDBEGGAR_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> SANDBEGGAR_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("sandbeggar_roofing_stairs", GotModBlocks.SANDBEGGAR_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> SANDBEGGAR_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("sandbeggar_roofing_wall", GotModBlocks.SANDBEGGAR_ROOFING_WALL);

    // ── apricot items ──
    public static final DeferredItem<Item> APRICOT_LOG            = block(GotModBlocks.APRICOT_LOG);
    public static final DeferredItem<Item> APRICOT_WOOD           = block(GotModBlocks.APRICOT_WOOD);
    public static final DeferredItem<Item> APRICOT_PLANKS         = block(GotModBlocks.APRICOT_PLANKS);
    public static final DeferredItem<Item> APRICOT_LEAVES         = block(GotModBlocks.APRICOT_LEAVES);
    public static final DeferredItem<Item> APRICOT_STAIRS         = block(GotModBlocks.APRICOT_STAIRS);
    public static final DeferredItem<Item> APRICOT_SLAB           = block(GotModBlocks.APRICOT_SLAB);
    public static final DeferredItem<Item> APRICOT_FENCE          = block(GotModBlocks.APRICOT_FENCE);
    public static final DeferredItem<Item> APRICOT_FENCE_GATE     = block(GotModBlocks.APRICOT_FENCE_GATE);
    public static final DeferredItem<Item> APRICOT_PRESSURE_PLATE = block(GotModBlocks.APRICOT_PRESSURE_PLATE);
    public static final DeferredItem<Item> APRICOT_BUTTON         = block(GotModBlocks.APRICOT_BUTTON);
    public static final DeferredItem<Item> APRICOT_DOOR           = door(GotModBlocks.APRICOT_DOOR);
    public static final DeferredItem<Item> APRICOT_TRAPDOOR       = block(GotModBlocks.APRICOT_TRAPDOOR);
    public static final DeferredItem<Item> APRICOT_BRANCH         = block(GotModBlocks.APRICOT_BRANCH);
    public static final DeferredItem<Item> STRIPPED_APRICOT_BRANCH = block(GotModBlocks.STRIPPED_APRICOT_BRANCH);
    public static final DeferredItem<Item> APRICOT_SIGN           = REGISTRY.registerItem("apricot_sign",         p -> new SignItem(GotModBlocks.APRICOT_SIGN.get(), GotModBlocks.APRICOT_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> APRICOT_HANGING_SIGN   = REGISTRY.registerItem("apricot_hanging_sign", p -> new HangingSignItem(GotModBlocks.APRICOT_HANGING_SIGN.get(), GotModBlocks.APRICOT_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> APRICOT_BOAT           = REGISTRY.registerItem("apricot_boat",       p -> new GotBoatItem(GotModBoatEntities.APRICOT_BOAT.get(), p));
    public static final DeferredItem<Item> APRICOT_CHEST_BOAT     = REGISTRY.registerItem("apricot_chest_boat", p -> new GotBoatItem(GotModBoatEntities.APRICOT_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> APRICOT_SAPLING        = block(GotModBlocks.APRICOT_SAPLING);
    public static final DeferredItem<Item> STRIPPED_APRICOT_LOG   = block(GotModBlocks.STRIPPED_APRICOT_LOG);
    public static final DeferredItem<Item> STRIPPED_APRICOT_WOOD  = block(GotModBlocks.STRIPPED_APRICOT_WOOD);
    public static final DeferredItem<BlockItem> APRICOT_ROOFING = REGISTRY.registerSimpleBlockItem("apricot_roofing", GotModBlocks.APRICOT_ROOFING);
    public static final DeferredItem<BlockItem> APRICOT_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("apricot_roofing_slab", GotModBlocks.APRICOT_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> APRICOT_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("apricot_roofing_stairs", GotModBlocks.APRICOT_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> APRICOT_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("apricot_roofing_wall", GotModBlocks.APRICOT_ROOFING_WALL);
    public static final DeferredItem<Item> APRICOT = REGISTRY.registerItem("apricot", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.5f).build()));

    // ── blackthorn items ──
    public static final DeferredItem<Item> BLACKTHORN_LOG            = block(GotModBlocks.BLACKTHORN_LOG);
    public static final DeferredItem<Item> BLACKTHORN_WOOD           = block(GotModBlocks.BLACKTHORN_WOOD);
    public static final DeferredItem<Item> BLACKTHORN_PLANKS         = block(GotModBlocks.BLACKTHORN_PLANKS);
    public static final DeferredItem<Item> BLACKTHORN_LEAVES         = block(GotModBlocks.BLACKTHORN_LEAVES);
    public static final DeferredItem<Item> BLACKTHORN_STAIRS         = block(GotModBlocks.BLACKTHORN_STAIRS);
    public static final DeferredItem<Item> BLACKTHORN_SLAB           = block(GotModBlocks.BLACKTHORN_SLAB);
    public static final DeferredItem<Item> BLACKTHORN_FENCE          = block(GotModBlocks.BLACKTHORN_FENCE);
    public static final DeferredItem<Item> BLACKTHORN_FENCE_GATE     = block(GotModBlocks.BLACKTHORN_FENCE_GATE);
    public static final DeferredItem<Item> BLACKTHORN_PRESSURE_PLATE = block(GotModBlocks.BLACKTHORN_PRESSURE_PLATE);
    public static final DeferredItem<Item> BLACKTHORN_BUTTON         = block(GotModBlocks.BLACKTHORN_BUTTON);
    public static final DeferredItem<Item> BLACKTHORN_DOOR           = door(GotModBlocks.BLACKTHORN_DOOR);
    public static final DeferredItem<Item> BLACKTHORN_TRAPDOOR       = block(GotModBlocks.BLACKTHORN_TRAPDOOR);
    public static final DeferredItem<Item> BLACKTHORN_BRANCH         = block(GotModBlocks.BLACKTHORN_BRANCH);
    public static final DeferredItem<Item> STRIPPED_BLACKTHORN_BRANCH = block(GotModBlocks.STRIPPED_BLACKTHORN_BRANCH);
    public static final DeferredItem<Item> BLACKTHORN_SIGN           = REGISTRY.registerItem("blackthorn_sign",         p -> new SignItem(GotModBlocks.BLACKTHORN_SIGN.get(), GotModBlocks.BLACKTHORN_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> BLACKTHORN_HANGING_SIGN   = REGISTRY.registerItem("blackthorn_hanging_sign", p -> new HangingSignItem(GotModBlocks.BLACKTHORN_HANGING_SIGN.get(), GotModBlocks.BLACKTHORN_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> BLACKTHORN_BOAT           = REGISTRY.registerItem("blackthorn_boat",       p -> new GotBoatItem(GotModBoatEntities.BLACKTHORN_BOAT.get(), p));
    public static final DeferredItem<Item> BLACKTHORN_CHEST_BOAT     = REGISTRY.registerItem("blackthorn_chest_boat", p -> new GotBoatItem(GotModBoatEntities.BLACKTHORN_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> BLACKTHORN_SAPLING        = block(GotModBlocks.BLACKTHORN_SAPLING);
    public static final DeferredItem<Item> STRIPPED_BLACKTHORN_LOG   = block(GotModBlocks.STRIPPED_BLACKTHORN_LOG);
    public static final DeferredItem<Item> STRIPPED_BLACKTHORN_WOOD  = block(GotModBlocks.STRIPPED_BLACKTHORN_WOOD);
    public static final DeferredItem<BlockItem> BLACKTHORN_ROOFING = REGISTRY.registerSimpleBlockItem("blackthorn_roofing", GotModBlocks.BLACKTHORN_ROOFING);
    public static final DeferredItem<BlockItem> BLACKTHORN_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("blackthorn_roofing_slab", GotModBlocks.BLACKTHORN_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> BLACKTHORN_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("blackthorn_roofing_stairs", GotModBlocks.BLACKTHORN_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> BLACKTHORN_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("blackthorn_roofing_wall", GotModBlocks.BLACKTHORN_ROOFING_WALL);

    // ── red_cherry items ──
    public static final DeferredItem<Item> RED_CHERRY_LOG            = block(GotModBlocks.RED_CHERRY_LOG);
    public static final DeferredItem<Item> RED_CHERRY_WOOD           = block(GotModBlocks.RED_CHERRY_WOOD);
    public static final DeferredItem<Item> RED_CHERRY_PLANKS         = block(GotModBlocks.RED_CHERRY_PLANKS);
    public static final DeferredItem<Item> RED_CHERRY_LEAVES         = block(GotModBlocks.RED_CHERRY_LEAVES);
    public static final DeferredItem<Item> RED_CHERRY_STAIRS         = block(GotModBlocks.RED_CHERRY_STAIRS);
    public static final DeferredItem<Item> RED_CHERRY_SLAB           = block(GotModBlocks.RED_CHERRY_SLAB);
    public static final DeferredItem<Item> RED_CHERRY_FENCE          = block(GotModBlocks.RED_CHERRY_FENCE);
    public static final DeferredItem<Item> RED_CHERRY_FENCE_GATE     = block(GotModBlocks.RED_CHERRY_FENCE_GATE);
    public static final DeferredItem<Item> RED_CHERRY_PRESSURE_PLATE = block(GotModBlocks.RED_CHERRY_PRESSURE_PLATE);
    public static final DeferredItem<Item> RED_CHERRY_BUTTON         = block(GotModBlocks.RED_CHERRY_BUTTON);
    public static final DeferredItem<Item> RED_CHERRY_DOOR           = door(GotModBlocks.RED_CHERRY_DOOR);
    public static final DeferredItem<Item> RED_CHERRY_TRAPDOOR       = block(GotModBlocks.RED_CHERRY_TRAPDOOR);
    public static final DeferredItem<Item> RED_CHERRY_BRANCH         = block(GotModBlocks.RED_CHERRY_BRANCH);
    public static final DeferredItem<Item> STRIPPED_RED_CHERRY_BRANCH = block(GotModBlocks.STRIPPED_RED_CHERRY_BRANCH);
    public static final DeferredItem<Item> RED_CHERRY_SIGN           = REGISTRY.registerItem("red_cherry_sign",         p -> new SignItem(GotModBlocks.RED_CHERRY_SIGN.get(), GotModBlocks.RED_CHERRY_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> RED_CHERRY_HANGING_SIGN   = REGISTRY.registerItem("red_cherry_hanging_sign", p -> new HangingSignItem(GotModBlocks.RED_CHERRY_HANGING_SIGN.get(), GotModBlocks.RED_CHERRY_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> RED_CHERRY_BOAT           = REGISTRY.registerItem("red_cherry_boat",       p -> new GotBoatItem(GotModBoatEntities.RED_CHERRY_BOAT.get(), p));
    public static final DeferredItem<Item> RED_CHERRY_CHEST_BOAT     = REGISTRY.registerItem("red_cherry_chest_boat", p -> new GotBoatItem(GotModBoatEntities.RED_CHERRY_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> RED_CHERRY_SAPLING        = block(GotModBlocks.RED_CHERRY_SAPLING);
    public static final DeferredItem<Item> STRIPPED_RED_CHERRY_LOG   = block(GotModBlocks.STRIPPED_RED_CHERRY_LOG);
    public static final DeferredItem<Item> STRIPPED_RED_CHERRY_WOOD  = block(GotModBlocks.STRIPPED_RED_CHERRY_WOOD);
    // ── black_cherry items ──
    public static final DeferredItem<Item> BLACK_CHERRY_LOG            = block(GotModBlocks.BLACK_CHERRY_LOG);
    public static final DeferredItem<Item> BLACK_CHERRY_WOOD           = block(GotModBlocks.BLACK_CHERRY_WOOD);
    public static final DeferredItem<Item> BLACK_CHERRY_PLANKS         = block(GotModBlocks.BLACK_CHERRY_PLANKS);
    public static final DeferredItem<Item> BLACK_CHERRY_LEAVES         = block(GotModBlocks.BLACK_CHERRY_LEAVES);
    public static final DeferredItem<Item> BLACK_CHERRY_STAIRS         = block(GotModBlocks.BLACK_CHERRY_STAIRS);
    public static final DeferredItem<Item> BLACK_CHERRY_SLAB           = block(GotModBlocks.BLACK_CHERRY_SLAB);
    public static final DeferredItem<Item> BLACK_CHERRY_FENCE          = block(GotModBlocks.BLACK_CHERRY_FENCE);
    public static final DeferredItem<Item> BLACK_CHERRY_FENCE_GATE     = block(GotModBlocks.BLACK_CHERRY_FENCE_GATE);
    public static final DeferredItem<Item> BLACK_CHERRY_PRESSURE_PLATE = block(GotModBlocks.BLACK_CHERRY_PRESSURE_PLATE);
    public static final DeferredItem<Item> BLACK_CHERRY_BUTTON         = block(GotModBlocks.BLACK_CHERRY_BUTTON);
    public static final DeferredItem<Item> BLACK_CHERRY_DOOR           = door(GotModBlocks.BLACK_CHERRY_DOOR);
    public static final DeferredItem<Item> BLACK_CHERRY_TRAPDOOR       = block(GotModBlocks.BLACK_CHERRY_TRAPDOOR);
    public static final DeferredItem<Item> BLACK_CHERRY_BRANCH         = block(GotModBlocks.BLACK_CHERRY_BRANCH);
    public static final DeferredItem<Item> STRIPPED_BLACK_CHERRY_BRANCH = block(GotModBlocks.STRIPPED_BLACK_CHERRY_BRANCH);
    public static final DeferredItem<Item> BLACK_CHERRY_SIGN           = REGISTRY.registerItem("black_cherry_sign",         p -> new SignItem(GotModBlocks.BLACK_CHERRY_SIGN.get(), GotModBlocks.BLACK_CHERRY_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> BLACK_CHERRY_HANGING_SIGN   = REGISTRY.registerItem("black_cherry_hanging_sign", p -> new HangingSignItem(GotModBlocks.BLACK_CHERRY_HANGING_SIGN.get(), GotModBlocks.BLACK_CHERRY_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> BLACK_CHERRY_BOAT           = REGISTRY.registerItem("black_cherry_boat",       p -> new GotBoatItem(GotModBoatEntities.BLACK_CHERRY_BOAT.get(), p));
    public static final DeferredItem<Item> BLACK_CHERRY_CHEST_BOAT     = REGISTRY.registerItem("black_cherry_chest_boat", p -> new GotBoatItem(GotModBoatEntities.BLACK_CHERRY_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> BLACK_CHERRY_SAPLING        = block(GotModBlocks.BLACK_CHERRY_SAPLING);
    public static final DeferredItem<Item> STRIPPED_BLACK_CHERRY_LOG   = block(GotModBlocks.STRIPPED_BLACK_CHERRY_LOG);
    public static final DeferredItem<Item> STRIPPED_BLACK_CHERRY_WOOD  = block(GotModBlocks.STRIPPED_BLACK_CHERRY_WOOD);
    public static final DeferredItem<Item> BLACK_CHERRY = REGISTRY.registerItem("black_cherry", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.4f).build()));

    // ── white_cherry items ──
    public static final DeferredItem<Item> WHITE_CHERRY_LOG            = block(GotModBlocks.WHITE_CHERRY_LOG);
    public static final DeferredItem<Item> WHITE_CHERRY_WOOD           = block(GotModBlocks.WHITE_CHERRY_WOOD);
    public static final DeferredItem<Item> WHITE_CHERRY_PLANKS         = block(GotModBlocks.WHITE_CHERRY_PLANKS);
    public static final DeferredItem<Item> WHITE_CHERRY_LEAVES         = block(GotModBlocks.WHITE_CHERRY_LEAVES);
    public static final DeferredItem<Item> WHITE_CHERRY_STAIRS         = block(GotModBlocks.WHITE_CHERRY_STAIRS);
    public static final DeferredItem<Item> WHITE_CHERRY_SLAB           = block(GotModBlocks.WHITE_CHERRY_SLAB);
    public static final DeferredItem<Item> WHITE_CHERRY_FENCE          = block(GotModBlocks.WHITE_CHERRY_FENCE);
    public static final DeferredItem<Item> WHITE_CHERRY_FENCE_GATE     = block(GotModBlocks.WHITE_CHERRY_FENCE_GATE);
    public static final DeferredItem<Item> WHITE_CHERRY_PRESSURE_PLATE = block(GotModBlocks.WHITE_CHERRY_PRESSURE_PLATE);
    public static final DeferredItem<Item> WHITE_CHERRY_BUTTON         = block(GotModBlocks.WHITE_CHERRY_BUTTON);
    public static final DeferredItem<Item> WHITE_CHERRY_DOOR           = door(GotModBlocks.WHITE_CHERRY_DOOR);
    public static final DeferredItem<Item> WHITE_CHERRY_TRAPDOOR       = block(GotModBlocks.WHITE_CHERRY_TRAPDOOR);
    public static final DeferredItem<Item> WHITE_CHERRY_BRANCH         = block(GotModBlocks.WHITE_CHERRY_BRANCH);
    public static final DeferredItem<Item> STRIPPED_WHITE_CHERRY_BRANCH = block(GotModBlocks.STRIPPED_WHITE_CHERRY_BRANCH);
    public static final DeferredItem<Item> WHITE_CHERRY_SIGN           = REGISTRY.registerItem("white_cherry_sign",         p -> new SignItem(GotModBlocks.WHITE_CHERRY_SIGN.get(), GotModBlocks.WHITE_CHERRY_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> WHITE_CHERRY_HANGING_SIGN   = REGISTRY.registerItem("white_cherry_hanging_sign", p -> new HangingSignItem(GotModBlocks.WHITE_CHERRY_HANGING_SIGN.get(), GotModBlocks.WHITE_CHERRY_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> WHITE_CHERRY_BOAT           = REGISTRY.registerItem("white_cherry_boat",       p -> new GotBoatItem(GotModBoatEntities.WHITE_CHERRY_BOAT.get(), p));
    public static final DeferredItem<Item> WHITE_CHERRY_CHEST_BOAT     = REGISTRY.registerItem("white_cherry_chest_boat", p -> new GotBoatItem(GotModBoatEntities.WHITE_CHERRY_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> WHITE_CHERRY_SAPLING        = block(GotModBlocks.WHITE_CHERRY_SAPLING);
    public static final DeferredItem<Item> STRIPPED_WHITE_CHERRY_LOG   = block(GotModBlocks.STRIPPED_WHITE_CHERRY_LOG);
    public static final DeferredItem<Item> STRIPPED_WHITE_CHERRY_WOOD  = block(GotModBlocks.STRIPPED_WHITE_CHERRY_WOOD);
    public static final DeferredItem<Item> WHITE_CHERRY = REGISTRY.registerItem("white_cherry", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.4f).build()));
    public static final DeferredItem<Item> RED_CHERRY = REGISTRY.registerItem("red_cherry", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.4f).build()));

    // ── crabapple items ──
    public static final DeferredItem<Item> CRABAPPLE_LOG            = block(GotModBlocks.CRABAPPLE_LOG);
    public static final DeferredItem<Item> CRABAPPLE_WOOD           = block(GotModBlocks.CRABAPPLE_WOOD);
    public static final DeferredItem<Item> CRABAPPLE_PLANKS         = block(GotModBlocks.CRABAPPLE_PLANKS);
    public static final DeferredItem<Item> CRABAPPLE_LEAVES         = block(GotModBlocks.CRABAPPLE_LEAVES);
    public static final DeferredItem<Item> CRABAPPLE_STAIRS         = block(GotModBlocks.CRABAPPLE_STAIRS);
    public static final DeferredItem<Item> CRABAPPLE_SLAB           = block(GotModBlocks.CRABAPPLE_SLAB);
    public static final DeferredItem<Item> CRABAPPLE_FENCE          = block(GotModBlocks.CRABAPPLE_FENCE);
    public static final DeferredItem<Item> CRABAPPLE_FENCE_GATE     = block(GotModBlocks.CRABAPPLE_FENCE_GATE);
    public static final DeferredItem<Item> CRABAPPLE_PRESSURE_PLATE = block(GotModBlocks.CRABAPPLE_PRESSURE_PLATE);
    public static final DeferredItem<Item> CRABAPPLE_BUTTON         = block(GotModBlocks.CRABAPPLE_BUTTON);
    public static final DeferredItem<Item> CRABAPPLE_DOOR           = door(GotModBlocks.CRABAPPLE_DOOR);
    public static final DeferredItem<Item> CRABAPPLE_TRAPDOOR       = block(GotModBlocks.CRABAPPLE_TRAPDOOR);
    public static final DeferredItem<Item> CRABAPPLE_BRANCH         = block(GotModBlocks.CRABAPPLE_BRANCH);
    public static final DeferredItem<Item> STRIPPED_CRABAPPLE_BRANCH = block(GotModBlocks.STRIPPED_CRABAPPLE_BRANCH);
    public static final DeferredItem<Item> CRABAPPLE_SIGN           = REGISTRY.registerItem("crabapple_sign",         p -> new SignItem(GotModBlocks.CRABAPPLE_SIGN.get(), GotModBlocks.CRABAPPLE_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> CRABAPPLE_HANGING_SIGN   = REGISTRY.registerItem("crabapple_hanging_sign", p -> new HangingSignItem(GotModBlocks.CRABAPPLE_HANGING_SIGN.get(), GotModBlocks.CRABAPPLE_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> CRABAPPLE_BOAT           = REGISTRY.registerItem("crabapple_boat",       p -> new GotBoatItem(GotModBoatEntities.CRABAPPLE_BOAT.get(), p));
    public static final DeferredItem<Item> CRABAPPLE_CHEST_BOAT     = REGISTRY.registerItem("crabapple_chest_boat", p -> new GotBoatItem(GotModBoatEntities.CRABAPPLE_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> CRABAPPLE_SAPLING        = block(GotModBlocks.CRABAPPLE_SAPLING);
    public static final DeferredItem<Item> STRIPPED_CRABAPPLE_LOG   = block(GotModBlocks.STRIPPED_CRABAPPLE_LOG);
    public static final DeferredItem<Item> STRIPPED_CRABAPPLE_WOOD  = block(GotModBlocks.STRIPPED_CRABAPPLE_WOOD);
    public static final DeferredItem<BlockItem> CRABAPPLE_ROOFING = REGISTRY.registerSimpleBlockItem("crabapple_roofing", GotModBlocks.CRABAPPLE_ROOFING);
    public static final DeferredItem<BlockItem> CRABAPPLE_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("crabapple_roofing_slab", GotModBlocks.CRABAPPLE_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> CRABAPPLE_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("crabapple_roofing_stairs", GotModBlocks.CRABAPPLE_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> CRABAPPLE_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("crabapple_roofing_wall", GotModBlocks.CRABAPPLE_ROOFING_WALL);
    public static final DeferredItem<Item> CRABAPPLE = REGISTRY.registerItem("crabapple", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.3f).build()));

    // ── date_palm items ──
    public static final DeferredItem<Item> DATE_PALM_LOG            = block(GotModBlocks.DATE_PALM_LOG);
    public static final DeferredItem<Item> DATE_PALM_WOOD           = block(GotModBlocks.DATE_PALM_WOOD);
    public static final DeferredItem<Item> DATE_PALM_PLANKS         = block(GotModBlocks.DATE_PALM_PLANKS);
    public static final DeferredItem<Item> DATE_PALM_LEAVES         = block(GotModBlocks.DATE_PALM_LEAVES);
    public static final DeferredItem<Item> DATE_PALM_STAIRS         = block(GotModBlocks.DATE_PALM_STAIRS);
    public static final DeferredItem<Item> DATE_PALM_SLAB           = block(GotModBlocks.DATE_PALM_SLAB);
    public static final DeferredItem<Item> DATE_PALM_FENCE          = block(GotModBlocks.DATE_PALM_FENCE);
    public static final DeferredItem<Item> DATE_PALM_FENCE_GATE     = block(GotModBlocks.DATE_PALM_FENCE_GATE);
    public static final DeferredItem<Item> DATE_PALM_PRESSURE_PLATE = block(GotModBlocks.DATE_PALM_PRESSURE_PLATE);
    public static final DeferredItem<Item> DATE_PALM_BUTTON         = block(GotModBlocks.DATE_PALM_BUTTON);
    public static final DeferredItem<Item> DATE_PALM_DOOR           = door(GotModBlocks.DATE_PALM_DOOR);
    public static final DeferredItem<Item> DATE_PALM_TRAPDOOR       = block(GotModBlocks.DATE_PALM_TRAPDOOR);
    public static final DeferredItem<Item> DATE_PALM_BRANCH         = block(GotModBlocks.DATE_PALM_BRANCH);
    public static final DeferredItem<Item> STRIPPED_DATE_PALM_BRANCH = block(GotModBlocks.STRIPPED_DATE_PALM_BRANCH);
    public static final DeferredItem<Item> DATE_PALM_SIGN           = REGISTRY.registerItem("date_palm_sign",         p -> new SignItem(GotModBlocks.DATE_PALM_SIGN.get(), GotModBlocks.DATE_PALM_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> DATE_PALM_HANGING_SIGN   = REGISTRY.registerItem("date_palm_hanging_sign", p -> new HangingSignItem(GotModBlocks.DATE_PALM_HANGING_SIGN.get(), GotModBlocks.DATE_PALM_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> DATE_PALM_BOAT           = REGISTRY.registerItem("date_palm_boat",       p -> new GotBoatItem(GotModBoatEntities.DATE_PALM_BOAT.get(), p));
    public static final DeferredItem<Item> DATE_PALM_CHEST_BOAT     = REGISTRY.registerItem("date_palm_chest_boat", p -> new GotBoatItem(GotModBoatEntities.DATE_PALM_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> DATE_PALM_SAPLING        = block(GotModBlocks.DATE_PALM_SAPLING);
    public static final DeferredItem<Item> STRIPPED_DATE_PALM_LOG   = block(GotModBlocks.STRIPPED_DATE_PALM_LOG);
    public static final DeferredItem<Item> STRIPPED_DATE_PALM_WOOD  = block(GotModBlocks.STRIPPED_DATE_PALM_WOOD);
    public static final DeferredItem<BlockItem> DATE_PALM_ROOFING = REGISTRY.registerSimpleBlockItem("date_palm_roofing", GotModBlocks.DATE_PALM_ROOFING);
    public static final DeferredItem<BlockItem> DATE_PALM_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("date_palm_roofing_slab", GotModBlocks.DATE_PALM_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> DATE_PALM_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("date_palm_roofing_stairs", GotModBlocks.DATE_PALM_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> DATE_PALM_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("date_palm_roofing_wall", GotModBlocks.DATE_PALM_ROOFING_WALL);
    public static final DeferredItem<Item> DATE = REGISTRY.registerItem("date", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(5).saturationModifier(0.6f).build()));

    // ── fig items ──
    public static final DeferredItem<Item> FIG_LOG            = block(GotModBlocks.FIG_LOG);
    public static final DeferredItem<Item> FIG_WOOD           = block(GotModBlocks.FIG_WOOD);
    public static final DeferredItem<Item> FIG_PLANKS         = block(GotModBlocks.FIG_PLANKS);
    public static final DeferredItem<Item> FIG_LEAVES         = block(GotModBlocks.FIG_LEAVES);
    public static final DeferredItem<Item> FIG_STAIRS         = block(GotModBlocks.FIG_STAIRS);
    public static final DeferredItem<Item> FIG_SLAB           = block(GotModBlocks.FIG_SLAB);
    public static final DeferredItem<Item> FIG_FENCE          = block(GotModBlocks.FIG_FENCE);
    public static final DeferredItem<Item> FIG_FENCE_GATE     = block(GotModBlocks.FIG_FENCE_GATE);
    public static final DeferredItem<Item> FIG_PRESSURE_PLATE = block(GotModBlocks.FIG_PRESSURE_PLATE);
    public static final DeferredItem<Item> FIG_BUTTON         = block(GotModBlocks.FIG_BUTTON);
    public static final DeferredItem<Item> FIG_DOOR           = door(GotModBlocks.FIG_DOOR);
    public static final DeferredItem<Item> FIG_TRAPDOOR       = block(GotModBlocks.FIG_TRAPDOOR);
    public static final DeferredItem<Item> FIG_BRANCH         = block(GotModBlocks.FIG_BRANCH);
    public static final DeferredItem<Item> STRIPPED_FIG_BRANCH = block(GotModBlocks.STRIPPED_FIG_BRANCH);
    public static final DeferredItem<Item> FIG_SIGN           = REGISTRY.registerItem("fig_sign",         p -> new SignItem(GotModBlocks.FIG_SIGN.get(), GotModBlocks.FIG_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> FIG_HANGING_SIGN   = REGISTRY.registerItem("fig_hanging_sign", p -> new HangingSignItem(GotModBlocks.FIG_HANGING_SIGN.get(), GotModBlocks.FIG_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> FIG_BOAT           = REGISTRY.registerItem("fig_boat",       p -> new GotBoatItem(GotModBoatEntities.FIG_BOAT.get(), p));
    public static final DeferredItem<Item> FIG_CHEST_BOAT     = REGISTRY.registerItem("fig_chest_boat", p -> new GotBoatItem(GotModBoatEntities.FIG_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> FIG_SAPLING        = block(GotModBlocks.FIG_SAPLING);
    public static final DeferredItem<Item> STRIPPED_FIG_LOG   = block(GotModBlocks.STRIPPED_FIG_LOG);
    public static final DeferredItem<Item> STRIPPED_FIG_WOOD  = block(GotModBlocks.STRIPPED_FIG_WOOD);
    public static final DeferredItem<BlockItem> FIG_ROOFING = REGISTRY.registerSimpleBlockItem("fig_roofing", GotModBlocks.FIG_ROOFING);
    public static final DeferredItem<BlockItem> FIG_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("fig_roofing_slab", GotModBlocks.FIG_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> FIG_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("fig_roofing_stairs", GotModBlocks.FIG_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> FIG_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("fig_roofing_wall", GotModBlocks.FIG_ROOFING_WALL);
    public static final DeferredItem<Item> FIG = REGISTRY.registerItem("fig", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.5f).build()));

    // ── lemon items ──
    public static final DeferredItem<Item> LEMON_LOG            = block(GotModBlocks.LEMON_LOG);
    public static final DeferredItem<Item> LEMON_WOOD           = block(GotModBlocks.LEMON_WOOD);
    public static final DeferredItem<Item> LEMON_PLANKS         = block(GotModBlocks.LEMON_PLANKS);
    public static final DeferredItem<Item> LEMON_LEAVES         = block(GotModBlocks.LEMON_LEAVES);
    public static final DeferredItem<Item> LEMON_STAIRS         = block(GotModBlocks.LEMON_STAIRS);
    public static final DeferredItem<Item> LEMON_SLAB           = block(GotModBlocks.LEMON_SLAB);
    public static final DeferredItem<Item> LEMON_FENCE          = block(GotModBlocks.LEMON_FENCE);
    public static final DeferredItem<Item> LEMON_FENCE_GATE     = block(GotModBlocks.LEMON_FENCE_GATE);
    public static final DeferredItem<Item> LEMON_PRESSURE_PLATE = block(GotModBlocks.LEMON_PRESSURE_PLATE);
    public static final DeferredItem<Item> LEMON_BUTTON         = block(GotModBlocks.LEMON_BUTTON);
    public static final DeferredItem<Item> LEMON_DOOR           = door(GotModBlocks.LEMON_DOOR);
    public static final DeferredItem<Item> LEMON_TRAPDOOR       = block(GotModBlocks.LEMON_TRAPDOOR);
    public static final DeferredItem<Item> LEMON_BRANCH         = block(GotModBlocks.LEMON_BRANCH);
    public static final DeferredItem<Item> STRIPPED_LEMON_BRANCH = block(GotModBlocks.STRIPPED_LEMON_BRANCH);
    public static final DeferredItem<Item> LEMON_SIGN           = REGISTRY.registerItem("lemon_sign",         p -> new SignItem(GotModBlocks.LEMON_SIGN.get(), GotModBlocks.LEMON_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> LEMON_HANGING_SIGN   = REGISTRY.registerItem("lemon_hanging_sign", p -> new HangingSignItem(GotModBlocks.LEMON_HANGING_SIGN.get(), GotModBlocks.LEMON_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> LEMON_BOAT           = REGISTRY.registerItem("lemon_boat",       p -> new GotBoatItem(GotModBoatEntities.LEMON_BOAT.get(), p));
    public static final DeferredItem<Item> LEMON_CHEST_BOAT     = REGISTRY.registerItem("lemon_chest_boat", p -> new GotBoatItem(GotModBoatEntities.LEMON_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> LEMON_SAPLING        = block(GotModBlocks.LEMON_SAPLING);
    public static final DeferredItem<Item> STRIPPED_LEMON_LOG   = block(GotModBlocks.STRIPPED_LEMON_LOG);
    public static final DeferredItem<Item> STRIPPED_LEMON_WOOD  = block(GotModBlocks.STRIPPED_LEMON_WOOD);
    public static final DeferredItem<BlockItem> LEMON_ROOFING = REGISTRY.registerSimpleBlockItem("lemon_roofing", GotModBlocks.LEMON_ROOFING);
    public static final DeferredItem<BlockItem> LEMON_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("lemon_roofing_slab", GotModBlocks.LEMON_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> LEMON_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("lemon_roofing_stairs", GotModBlocks.LEMON_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> LEMON_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("lemon_roofing_wall", GotModBlocks.LEMON_ROOFING_WALL);
    public static final DeferredItem<Item> LEMON = REGISTRY.registerItem("lemon", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.3f).build()));

    // ── lime items ──
    public static final DeferredItem<Item> LIME_LOG            = block(GotModBlocks.LIME_LOG);
    public static final DeferredItem<Item> LIME_WOOD           = block(GotModBlocks.LIME_WOOD);
    public static final DeferredItem<Item> LIME_PLANKS         = block(GotModBlocks.LIME_PLANKS);
    public static final DeferredItem<Item> LIME_LEAVES         = block(GotModBlocks.LIME_LEAVES);
    public static final DeferredItem<Item> LIME_STAIRS         = block(GotModBlocks.LIME_STAIRS);
    public static final DeferredItem<Item> LIME_SLAB           = block(GotModBlocks.LIME_SLAB);
    public static final DeferredItem<Item> LIME_FENCE          = block(GotModBlocks.LIME_FENCE);
    public static final DeferredItem<Item> LIME_FENCE_GATE     = block(GotModBlocks.LIME_FENCE_GATE);
    public static final DeferredItem<Item> LIME_PRESSURE_PLATE = block(GotModBlocks.LIME_PRESSURE_PLATE);
    public static final DeferredItem<Item> LIME_BUTTON         = block(GotModBlocks.LIME_BUTTON);
    public static final DeferredItem<Item> LIME_DOOR           = door(GotModBlocks.LIME_DOOR);
    public static final DeferredItem<Item> LIME_TRAPDOOR       = block(GotModBlocks.LIME_TRAPDOOR);
    public static final DeferredItem<Item> LIME_BRANCH         = block(GotModBlocks.LIME_BRANCH);
    public static final DeferredItem<Item> STRIPPED_LIME_BRANCH = block(GotModBlocks.STRIPPED_LIME_BRANCH);
    public static final DeferredItem<Item> LIME_SIGN           = REGISTRY.registerItem("lime_sign",         p -> new SignItem(GotModBlocks.LIME_SIGN.get(), GotModBlocks.LIME_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> LIME_HANGING_SIGN   = REGISTRY.registerItem("lime_hanging_sign", p -> new HangingSignItem(GotModBlocks.LIME_HANGING_SIGN.get(), GotModBlocks.LIME_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> LIME_BOAT           = REGISTRY.registerItem("lime_boat",       p -> new GotBoatItem(GotModBoatEntities.LIME_BOAT.get(), p));
    public static final DeferredItem<Item> LIME_CHEST_BOAT     = REGISTRY.registerItem("lime_chest_boat", p -> new GotBoatItem(GotModBoatEntities.LIME_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> LIME_SAPLING        = block(GotModBlocks.LIME_SAPLING);
    public static final DeferredItem<Item> STRIPPED_LIME_LOG   = block(GotModBlocks.STRIPPED_LIME_LOG);
    public static final DeferredItem<Item> STRIPPED_LIME_WOOD  = block(GotModBlocks.STRIPPED_LIME_WOOD);
    public static final DeferredItem<BlockItem> LIME_ROOFING = REGISTRY.registerSimpleBlockItem("lime_roofing", GotModBlocks.LIME_ROOFING);
    public static final DeferredItem<BlockItem> LIME_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("lime_roofing_slab", GotModBlocks.LIME_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> LIME_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("lime_roofing_stairs", GotModBlocks.LIME_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> LIME_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("lime_roofing_wall", GotModBlocks.LIME_ROOFING_WALL);
    public static final DeferredItem<Item> LIME = REGISTRY.registerItem("lime", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.3f).build()));

    // ── olive items ──
    public static final DeferredItem<Item> OLIVE_LOG            = block(GotModBlocks.OLIVE_LOG);
    public static final DeferredItem<Item> OLIVE_WOOD           = block(GotModBlocks.OLIVE_WOOD);
    public static final DeferredItem<Item> OLIVE_PLANKS         = block(GotModBlocks.OLIVE_PLANKS);
    public static final DeferredItem<Item> OLIVE_LEAVES         = block(GotModBlocks.OLIVE_LEAVES);
    public static final DeferredItem<Item> OLIVE_STAIRS         = block(GotModBlocks.OLIVE_STAIRS);
    public static final DeferredItem<Item> OLIVE_SLAB           = block(GotModBlocks.OLIVE_SLAB);
    public static final DeferredItem<Item> OLIVE_FENCE          = block(GotModBlocks.OLIVE_FENCE);
    public static final DeferredItem<Item> OLIVE_FENCE_GATE     = block(GotModBlocks.OLIVE_FENCE_GATE);
    public static final DeferredItem<Item> OLIVE_PRESSURE_PLATE = block(GotModBlocks.OLIVE_PRESSURE_PLATE);
    public static final DeferredItem<Item> OLIVE_BUTTON         = block(GotModBlocks.OLIVE_BUTTON);
    public static final DeferredItem<Item> OLIVE_DOOR           = door(GotModBlocks.OLIVE_DOOR);
    public static final DeferredItem<Item> OLIVE_TRAPDOOR       = block(GotModBlocks.OLIVE_TRAPDOOR);
    public static final DeferredItem<Item> OLIVE_BRANCH         = block(GotModBlocks.OLIVE_BRANCH);
    public static final DeferredItem<Item> STRIPPED_OLIVE_BRANCH = block(GotModBlocks.STRIPPED_OLIVE_BRANCH);
    public static final DeferredItem<Item> OLIVE_SIGN           = REGISTRY.registerItem("olive_sign",         p -> new SignItem(GotModBlocks.OLIVE_SIGN.get(), GotModBlocks.OLIVE_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> OLIVE_HANGING_SIGN   = REGISTRY.registerItem("olive_hanging_sign", p -> new HangingSignItem(GotModBlocks.OLIVE_HANGING_SIGN.get(), GotModBlocks.OLIVE_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> OLIVE_BOAT           = REGISTRY.registerItem("olive_boat",       p -> new GotBoatItem(GotModBoatEntities.OLIVE_BOAT.get(), p));
    public static final DeferredItem<Item> OLIVE_CHEST_BOAT     = REGISTRY.registerItem("olive_chest_boat", p -> new GotBoatItem(GotModBoatEntities.OLIVE_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> OLIVE_SAPLING        = block(GotModBlocks.OLIVE_SAPLING);
    public static final DeferredItem<Item> STRIPPED_OLIVE_LOG   = block(GotModBlocks.STRIPPED_OLIVE_LOG);
    public static final DeferredItem<Item> STRIPPED_OLIVE_WOOD  = block(GotModBlocks.STRIPPED_OLIVE_WOOD);
    public static final DeferredItem<BlockItem> OLIVE_ROOFING = REGISTRY.registerSimpleBlockItem("olive_roofing", GotModBlocks.OLIVE_ROOFING);
    public static final DeferredItem<BlockItem> OLIVE_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("olive_roofing_slab", GotModBlocks.OLIVE_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> OLIVE_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("olive_roofing_stairs", GotModBlocks.OLIVE_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> OLIVE_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("olive_roofing_wall", GotModBlocks.OLIVE_ROOFING_WALL);
    public static final DeferredItem<Item> OLIVE = REGISTRY.registerItem("olive", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.5f).build()));

    // ── orange items ──
    public static final DeferredItem<Item> ORANGE_LOG            = block(GotModBlocks.ORANGE_LOG);
    public static final DeferredItem<Item> ORANGE_WOOD           = block(GotModBlocks.ORANGE_WOOD);
    public static final DeferredItem<Item> ORANGE_PLANKS         = block(GotModBlocks.ORANGE_PLANKS);
    public static final DeferredItem<Item> ORANGE_LEAVES         = block(GotModBlocks.ORANGE_LEAVES);
    public static final DeferredItem<Item> ORANGE_STAIRS         = block(GotModBlocks.ORANGE_STAIRS);
    public static final DeferredItem<Item> ORANGE_SLAB           = block(GotModBlocks.ORANGE_SLAB);
    public static final DeferredItem<Item> ORANGE_FENCE          = block(GotModBlocks.ORANGE_FENCE);
    public static final DeferredItem<Item> ORANGE_FENCE_GATE     = block(GotModBlocks.ORANGE_FENCE_GATE);
    public static final DeferredItem<Item> ORANGE_PRESSURE_PLATE = block(GotModBlocks.ORANGE_PRESSURE_PLATE);
    public static final DeferredItem<Item> ORANGE_BUTTON         = block(GotModBlocks.ORANGE_BUTTON);
    public static final DeferredItem<Item> ORANGE_DOOR           = door(GotModBlocks.ORANGE_DOOR);
    public static final DeferredItem<Item> ORANGE_TRAPDOOR       = block(GotModBlocks.ORANGE_TRAPDOOR);
    public static final DeferredItem<Item> ORANGE_BRANCH         = block(GotModBlocks.ORANGE_BRANCH);
    public static final DeferredItem<Item> STRIPPED_ORANGE_BRANCH = block(GotModBlocks.STRIPPED_ORANGE_BRANCH);
    public static final DeferredItem<Item> ORANGE_SIGN           = REGISTRY.registerItem("orange_sign",         p -> new SignItem(GotModBlocks.ORANGE_SIGN.get(), GotModBlocks.ORANGE_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> ORANGE_HANGING_SIGN   = REGISTRY.registerItem("orange_hanging_sign", p -> new HangingSignItem(GotModBlocks.ORANGE_HANGING_SIGN.get(), GotModBlocks.ORANGE_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> ORANGE_BOAT           = REGISTRY.registerItem("orange_boat",       p -> new GotBoatItem(GotModBoatEntities.ORANGE_BOAT.get(), p));
    public static final DeferredItem<Item> ORANGE_CHEST_BOAT     = REGISTRY.registerItem("orange_chest_boat", p -> new GotBoatItem(GotModBoatEntities.ORANGE_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> ORANGE_SAPLING        = block(GotModBlocks.ORANGE_SAPLING);
    public static final DeferredItem<Item> STRIPPED_ORANGE_LOG   = block(GotModBlocks.STRIPPED_ORANGE_LOG);
    public static final DeferredItem<Item> STRIPPED_ORANGE_WOOD  = block(GotModBlocks.STRIPPED_ORANGE_WOOD);
    public static final DeferredItem<BlockItem> ORANGE_ROOFING = REGISTRY.registerSimpleBlockItem("orange_roofing", GotModBlocks.ORANGE_ROOFING);
    public static final DeferredItem<BlockItem> ORANGE_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("orange_roofing_slab", GotModBlocks.ORANGE_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> ORANGE_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("orange_roofing_stairs", GotModBlocks.ORANGE_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> ORANGE_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("orange_roofing_wall", GotModBlocks.ORANGE_ROOFING_WALL);
    public static final DeferredItem<Item> ORANGE = REGISTRY.registerItem("orange", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.5f).build()));

    // ── peach items ──
    public static final DeferredItem<Item> PEACH_LOG            = block(GotModBlocks.PEACH_LOG);
    public static final DeferredItem<Item> PEACH_WOOD           = block(GotModBlocks.PEACH_WOOD);
    public static final DeferredItem<Item> PEACH_PLANKS         = block(GotModBlocks.PEACH_PLANKS);
    public static final DeferredItem<Item> PEACH_LEAVES         = block(GotModBlocks.PEACH_LEAVES);
    public static final DeferredItem<Item> PEACH_STAIRS         = block(GotModBlocks.PEACH_STAIRS);
    public static final DeferredItem<Item> PEACH_SLAB           = block(GotModBlocks.PEACH_SLAB);
    public static final DeferredItem<Item> PEACH_FENCE          = block(GotModBlocks.PEACH_FENCE);
    public static final DeferredItem<Item> PEACH_FENCE_GATE     = block(GotModBlocks.PEACH_FENCE_GATE);
    public static final DeferredItem<Item> PEACH_PRESSURE_PLATE = block(GotModBlocks.PEACH_PRESSURE_PLATE);
    public static final DeferredItem<Item> PEACH_BUTTON         = block(GotModBlocks.PEACH_BUTTON);
    public static final DeferredItem<Item> PEACH_DOOR           = door(GotModBlocks.PEACH_DOOR);
    public static final DeferredItem<Item> PEACH_TRAPDOOR       = block(GotModBlocks.PEACH_TRAPDOOR);
    public static final DeferredItem<Item> PEACH_BRANCH         = block(GotModBlocks.PEACH_BRANCH);
    public static final DeferredItem<Item> STRIPPED_PEACH_BRANCH = block(GotModBlocks.STRIPPED_PEACH_BRANCH);
    public static final DeferredItem<Item> PEACH_SIGN           = REGISTRY.registerItem("peach_sign",         p -> new SignItem(GotModBlocks.PEACH_SIGN.get(), GotModBlocks.PEACH_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> PEACH_HANGING_SIGN   = REGISTRY.registerItem("peach_hanging_sign", p -> new HangingSignItem(GotModBlocks.PEACH_HANGING_SIGN.get(), GotModBlocks.PEACH_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> PEACH_BOAT           = REGISTRY.registerItem("peach_boat",       p -> new GotBoatItem(GotModBoatEntities.PEACH_BOAT.get(), p));
    public static final DeferredItem<Item> PEACH_CHEST_BOAT     = REGISTRY.registerItem("peach_chest_boat", p -> new GotBoatItem(GotModBoatEntities.PEACH_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> PEACH_SAPLING        = block(GotModBlocks.PEACH_SAPLING);
    public static final DeferredItem<Item> STRIPPED_PEACH_LOG   = block(GotModBlocks.STRIPPED_PEACH_LOG);
    public static final DeferredItem<Item> STRIPPED_PEACH_WOOD  = block(GotModBlocks.STRIPPED_PEACH_WOOD);
    public static final DeferredItem<BlockItem> PEACH_ROOFING = REGISTRY.registerSimpleBlockItem("peach_roofing", GotModBlocks.PEACH_ROOFING);
    public static final DeferredItem<BlockItem> PEACH_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("peach_roofing_slab", GotModBlocks.PEACH_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> PEACH_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("peach_roofing_stairs", GotModBlocks.PEACH_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> PEACH_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("peach_roofing_wall", GotModBlocks.PEACH_ROOFING_WALL);
    public static final DeferredItem<Item> PEACH = REGISTRY.registerItem("peach", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(5).saturationModifier(0.6f).build()));

    // ── pear items ──
    public static final DeferredItem<Item> PEAR_LOG            = block(GotModBlocks.PEAR_LOG);
    public static final DeferredItem<Item> PEAR_WOOD           = block(GotModBlocks.PEAR_WOOD);
    public static final DeferredItem<Item> PEAR_PLANKS         = block(GotModBlocks.PEAR_PLANKS);
    public static final DeferredItem<Item> PEAR_LEAVES         = block(GotModBlocks.PEAR_LEAVES);
    public static final DeferredItem<Item> PEAR_STAIRS         = block(GotModBlocks.PEAR_STAIRS);
    public static final DeferredItem<Item> PEAR_SLAB           = block(GotModBlocks.PEAR_SLAB);
    public static final DeferredItem<Item> PEAR_FENCE          = block(GotModBlocks.PEAR_FENCE);
    public static final DeferredItem<Item> PEAR_FENCE_GATE     = block(GotModBlocks.PEAR_FENCE_GATE);
    public static final DeferredItem<Item> PEAR_PRESSURE_PLATE = block(GotModBlocks.PEAR_PRESSURE_PLATE);
    public static final DeferredItem<Item> PEAR_BUTTON         = block(GotModBlocks.PEAR_BUTTON);
    public static final DeferredItem<Item> PEAR_DOOR           = door(GotModBlocks.PEAR_DOOR);
    public static final DeferredItem<Item> PEAR_TRAPDOOR       = block(GotModBlocks.PEAR_TRAPDOOR);
    public static final DeferredItem<Item> PEAR_BRANCH         = block(GotModBlocks.PEAR_BRANCH);
    public static final DeferredItem<Item> STRIPPED_PEAR_BRANCH = block(GotModBlocks.STRIPPED_PEAR_BRANCH);
    public static final DeferredItem<Item> PEAR_SIGN           = REGISTRY.registerItem("pear_sign",         p -> new SignItem(GotModBlocks.PEAR_SIGN.get(), GotModBlocks.PEAR_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> PEAR_HANGING_SIGN   = REGISTRY.registerItem("pear_hanging_sign", p -> new HangingSignItem(GotModBlocks.PEAR_HANGING_SIGN.get(), GotModBlocks.PEAR_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> PEAR_BOAT           = REGISTRY.registerItem("pear_boat",       p -> new GotBoatItem(GotModBoatEntities.PEAR_BOAT.get(), p));
    public static final DeferredItem<Item> PEAR_CHEST_BOAT     = REGISTRY.registerItem("pear_chest_boat", p -> new GotBoatItem(GotModBoatEntities.PEAR_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> PEAR_SAPLING        = block(GotModBlocks.PEAR_SAPLING);
    public static final DeferredItem<Item> STRIPPED_PEAR_LOG   = block(GotModBlocks.STRIPPED_PEAR_LOG);
    public static final DeferredItem<Item> STRIPPED_PEAR_WOOD  = block(GotModBlocks.STRIPPED_PEAR_WOOD);
    public static final DeferredItem<BlockItem> PEAR_ROOFING = REGISTRY.registerSimpleBlockItem("pear_roofing", GotModBlocks.PEAR_ROOFING);
    public static final DeferredItem<BlockItem> PEAR_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("pear_roofing_slab", GotModBlocks.PEAR_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> PEAR_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("pear_roofing_stairs", GotModBlocks.PEAR_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> PEAR_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("pear_roofing_wall", GotModBlocks.PEAR_ROOFING_WALL);
    public static final DeferredItem<Item> PEAR = REGISTRY.registerItem("pear", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.5f).build()));

    // ── persimmon items ──
    public static final DeferredItem<Item> PERSIMMON_LOG            = block(GotModBlocks.PERSIMMON_LOG);
    public static final DeferredItem<Item> PERSIMMON_WOOD           = block(GotModBlocks.PERSIMMON_WOOD);
    public static final DeferredItem<Item> PERSIMMON_PLANKS         = block(GotModBlocks.PERSIMMON_PLANKS);
    public static final DeferredItem<Item> PERSIMMON_LEAVES         = block(GotModBlocks.PERSIMMON_LEAVES);
    public static final DeferredItem<Item> PERSIMMON_STAIRS         = block(GotModBlocks.PERSIMMON_STAIRS);
    public static final DeferredItem<Item> PERSIMMON_SLAB           = block(GotModBlocks.PERSIMMON_SLAB);
    public static final DeferredItem<Item> PERSIMMON_FENCE          = block(GotModBlocks.PERSIMMON_FENCE);
    public static final DeferredItem<Item> PERSIMMON_FENCE_GATE     = block(GotModBlocks.PERSIMMON_FENCE_GATE);
    public static final DeferredItem<Item> PERSIMMON_PRESSURE_PLATE = block(GotModBlocks.PERSIMMON_PRESSURE_PLATE);
    public static final DeferredItem<Item> PERSIMMON_BUTTON         = block(GotModBlocks.PERSIMMON_BUTTON);
    public static final DeferredItem<Item> PERSIMMON_DOOR           = door(GotModBlocks.PERSIMMON_DOOR);
    public static final DeferredItem<Item> PERSIMMON_TRAPDOOR       = block(GotModBlocks.PERSIMMON_TRAPDOOR);
    public static final DeferredItem<Item> PERSIMMON_BRANCH         = block(GotModBlocks.PERSIMMON_BRANCH);
    public static final DeferredItem<Item> STRIPPED_PERSIMMON_BRANCH = block(GotModBlocks.STRIPPED_PERSIMMON_BRANCH);
    public static final DeferredItem<Item> PERSIMMON_SIGN           = REGISTRY.registerItem("persimmon_sign",         p -> new SignItem(GotModBlocks.PERSIMMON_SIGN.get(), GotModBlocks.PERSIMMON_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> PERSIMMON_HANGING_SIGN   = REGISTRY.registerItem("persimmon_hanging_sign", p -> new HangingSignItem(GotModBlocks.PERSIMMON_HANGING_SIGN.get(), GotModBlocks.PERSIMMON_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> PERSIMMON_BOAT           = REGISTRY.registerItem("persimmon_boat",       p -> new GotBoatItem(GotModBoatEntities.PERSIMMON_BOAT.get(), p));
    public static final DeferredItem<Item> PERSIMMON_CHEST_BOAT     = REGISTRY.registerItem("persimmon_chest_boat", p -> new GotBoatItem(GotModBoatEntities.PERSIMMON_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> PERSIMMON_SAPLING        = block(GotModBlocks.PERSIMMON_SAPLING);
    public static final DeferredItem<Item> STRIPPED_PERSIMMON_LOG   = block(GotModBlocks.STRIPPED_PERSIMMON_LOG);
    public static final DeferredItem<Item> STRIPPED_PERSIMMON_WOOD  = block(GotModBlocks.STRIPPED_PERSIMMON_WOOD);
    public static final DeferredItem<BlockItem> PERSIMMON_ROOFING = REGISTRY.registerSimpleBlockItem("persimmon_roofing", GotModBlocks.PERSIMMON_ROOFING);
    public static final DeferredItem<BlockItem> PERSIMMON_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("persimmon_roofing_slab", GotModBlocks.PERSIMMON_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> PERSIMMON_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("persimmon_roofing_stairs", GotModBlocks.PERSIMMON_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> PERSIMMON_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("persimmon_roofing_wall", GotModBlocks.PERSIMMON_ROOFING_WALL);
    public static final DeferredItem<Item> PERSIMMON = REGISTRY.registerItem("persimmon", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.5f).build()));

    // ── pink_ivory items ──
    public static final DeferredItem<Item> PINK_IVORY_LOG            = block(GotModBlocks.PINK_IVORY_LOG);
    public static final DeferredItem<Item> PINK_IVORY_WOOD           = block(GotModBlocks.PINK_IVORY_WOOD);
    public static final DeferredItem<Item> PINK_IVORY_PLANKS         = block(GotModBlocks.PINK_IVORY_PLANKS);
    public static final DeferredItem<Item> PINK_IVORY_LEAVES         = block(GotModBlocks.PINK_IVORY_LEAVES);
    public static final DeferredItem<Item> PINK_IVORY_STAIRS         = block(GotModBlocks.PINK_IVORY_STAIRS);
    public static final DeferredItem<Item> PINK_IVORY_SLAB           = block(GotModBlocks.PINK_IVORY_SLAB);
    public static final DeferredItem<Item> PINK_IVORY_FENCE          = block(GotModBlocks.PINK_IVORY_FENCE);
    public static final DeferredItem<Item> PINK_IVORY_FENCE_GATE     = block(GotModBlocks.PINK_IVORY_FENCE_GATE);
    public static final DeferredItem<Item> PINK_IVORY_PRESSURE_PLATE = block(GotModBlocks.PINK_IVORY_PRESSURE_PLATE);
    public static final DeferredItem<Item> PINK_IVORY_BUTTON         = block(GotModBlocks.PINK_IVORY_BUTTON);
    public static final DeferredItem<Item> PINK_IVORY_DOOR           = door(GotModBlocks.PINK_IVORY_DOOR);
    public static final DeferredItem<Item> PINK_IVORY_TRAPDOOR       = block(GotModBlocks.PINK_IVORY_TRAPDOOR);
    public static final DeferredItem<Item> PINK_IVORY_BRANCH         = block(GotModBlocks.PINK_IVORY_BRANCH);
    public static final DeferredItem<Item> STRIPPED_PINK_IVORY_BRANCH = block(GotModBlocks.STRIPPED_PINK_IVORY_BRANCH);
    public static final DeferredItem<Item> PINK_IVORY_SIGN           = REGISTRY.registerItem("pink_ivory_sign",         p -> new SignItem(GotModBlocks.PINK_IVORY_SIGN.get(), GotModBlocks.PINK_IVORY_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> PINK_IVORY_HANGING_SIGN   = REGISTRY.registerItem("pink_ivory_hanging_sign", p -> new HangingSignItem(GotModBlocks.PINK_IVORY_HANGING_SIGN.get(), GotModBlocks.PINK_IVORY_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> PINK_IVORY_BOAT           = REGISTRY.registerItem("pink_ivory_boat",       p -> new GotBoatItem(GotModBoatEntities.PINK_IVORY_BOAT.get(), p));
    public static final DeferredItem<Item> PINK_IVORY_CHEST_BOAT     = REGISTRY.registerItem("pink_ivory_chest_boat", p -> new GotBoatItem(GotModBoatEntities.PINK_IVORY_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> PINK_IVORY_SAPLING        = block(GotModBlocks.PINK_IVORY_SAPLING);
    public static final DeferredItem<Item> STRIPPED_PINK_IVORY_LOG   = block(GotModBlocks.STRIPPED_PINK_IVORY_LOG);
    public static final DeferredItem<Item> STRIPPED_PINK_IVORY_WOOD  = block(GotModBlocks.STRIPPED_PINK_IVORY_WOOD);
    public static final DeferredItem<BlockItem> PINK_IVORY_ROOFING = REGISTRY.registerSimpleBlockItem("pink_ivory_roofing", GotModBlocks.PINK_IVORY_ROOFING);
    public static final DeferredItem<BlockItem> PINK_IVORY_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("pink_ivory_roofing_slab", GotModBlocks.PINK_IVORY_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> PINK_IVORY_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("pink_ivory_roofing_stairs", GotModBlocks.PINK_IVORY_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> PINK_IVORY_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("pink_ivory_roofing_wall", GotModBlocks.PINK_IVORY_ROOFING_WALL);

    // ── plum items ──
    public static final DeferredItem<Item> PLUM_LOG            = block(GotModBlocks.PLUM_LOG);
    public static final DeferredItem<Item> PLUM_WOOD           = block(GotModBlocks.PLUM_WOOD);
    public static final DeferredItem<Item> PLUM_PLANKS         = block(GotModBlocks.PLUM_PLANKS);
    public static final DeferredItem<Item> PLUM_LEAVES         = block(GotModBlocks.PLUM_LEAVES);
    public static final DeferredItem<Item> PLUM_STAIRS         = block(GotModBlocks.PLUM_STAIRS);
    public static final DeferredItem<Item> PLUM_SLAB           = block(GotModBlocks.PLUM_SLAB);
    public static final DeferredItem<Item> PLUM_FENCE          = block(GotModBlocks.PLUM_FENCE);
    public static final DeferredItem<Item> PLUM_FENCE_GATE     = block(GotModBlocks.PLUM_FENCE_GATE);
    public static final DeferredItem<Item> PLUM_PRESSURE_PLATE = block(GotModBlocks.PLUM_PRESSURE_PLATE);
    public static final DeferredItem<Item> PLUM_BUTTON         = block(GotModBlocks.PLUM_BUTTON);
    public static final DeferredItem<Item> PLUM_DOOR           = door(GotModBlocks.PLUM_DOOR);
    public static final DeferredItem<Item> PLUM_TRAPDOOR       = block(GotModBlocks.PLUM_TRAPDOOR);
    public static final DeferredItem<Item> PLUM_BRANCH         = block(GotModBlocks.PLUM_BRANCH);
    public static final DeferredItem<Item> STRIPPED_PLUM_BRANCH = block(GotModBlocks.STRIPPED_PLUM_BRANCH);
    public static final DeferredItem<Item> PLUM_SIGN           = REGISTRY.registerItem("plum_sign",         p -> new SignItem(GotModBlocks.PLUM_SIGN.get(), GotModBlocks.PLUM_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> PLUM_HANGING_SIGN   = REGISTRY.registerItem("plum_hanging_sign", p -> new HangingSignItem(GotModBlocks.PLUM_HANGING_SIGN.get(), GotModBlocks.PLUM_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> PLUM_BOAT           = REGISTRY.registerItem("plum_boat",       p -> new GotBoatItem(GotModBoatEntities.PLUM_BOAT.get(), p));
    public static final DeferredItem<Item> PLUM_CHEST_BOAT     = REGISTRY.registerItem("plum_chest_boat", p -> new GotBoatItem(GotModBoatEntities.PLUM_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> PLUM_SAPLING        = block(GotModBlocks.PLUM_SAPLING);
    public static final DeferredItem<Item> STRIPPED_PLUM_LOG   = block(GotModBlocks.STRIPPED_PLUM_LOG);
    public static final DeferredItem<Item> STRIPPED_PLUM_WOOD  = block(GotModBlocks.STRIPPED_PLUM_WOOD);
    public static final DeferredItem<BlockItem> PLUM_ROOFING = REGISTRY.registerSimpleBlockItem("plum_roofing", GotModBlocks.PLUM_ROOFING);
    public static final DeferredItem<BlockItem> PLUM_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("plum_roofing_slab", GotModBlocks.PLUM_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> PLUM_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("plum_roofing_stairs", GotModBlocks.PLUM_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> PLUM_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("plum_roofing_wall", GotModBlocks.PLUM_ROOFING_WALL);
    public static final DeferredItem<Item> PLUM = REGISTRY.registerItem("plum", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.5f).build()));

    // ── pomegranate items ──
    public static final DeferredItem<Item> POMEGRANATE_LOG            = block(GotModBlocks.POMEGRANATE_LOG);
    public static final DeferredItem<Item> POMEGRANATE_WOOD           = block(GotModBlocks.POMEGRANATE_WOOD);
    public static final DeferredItem<Item> POMEGRANATE_PLANKS         = block(GotModBlocks.POMEGRANATE_PLANKS);
    public static final DeferredItem<Item> POMEGRANATE_LEAVES         = block(GotModBlocks.POMEGRANATE_LEAVES);
    public static final DeferredItem<Item> POMEGRANATE_STAIRS         = block(GotModBlocks.POMEGRANATE_STAIRS);
    public static final DeferredItem<Item> POMEGRANATE_SLAB           = block(GotModBlocks.POMEGRANATE_SLAB);
    public static final DeferredItem<Item> POMEGRANATE_FENCE          = block(GotModBlocks.POMEGRANATE_FENCE);
    public static final DeferredItem<Item> POMEGRANATE_FENCE_GATE     = block(GotModBlocks.POMEGRANATE_FENCE_GATE);
    public static final DeferredItem<Item> POMEGRANATE_PRESSURE_PLATE = block(GotModBlocks.POMEGRANATE_PRESSURE_PLATE);
    public static final DeferredItem<Item> POMEGRANATE_BUTTON         = block(GotModBlocks.POMEGRANATE_BUTTON);
    public static final DeferredItem<Item> POMEGRANATE_DOOR           = door(GotModBlocks.POMEGRANATE_DOOR);
    public static final DeferredItem<Item> POMEGRANATE_TRAPDOOR       = block(GotModBlocks.POMEGRANATE_TRAPDOOR);
    public static final DeferredItem<Item> POMEGRANATE_BRANCH         = block(GotModBlocks.POMEGRANATE_BRANCH);
    public static final DeferredItem<Item> STRIPPED_POMEGRANATE_BRANCH = block(GotModBlocks.STRIPPED_POMEGRANATE_BRANCH);
    public static final DeferredItem<Item> POMEGRANATE_SIGN           = REGISTRY.registerItem("pomegranate_sign",         p -> new SignItem(GotModBlocks.POMEGRANATE_SIGN.get(), GotModBlocks.POMEGRANATE_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> POMEGRANATE_HANGING_SIGN   = REGISTRY.registerItem("pomegranate_hanging_sign", p -> new HangingSignItem(GotModBlocks.POMEGRANATE_HANGING_SIGN.get(), GotModBlocks.POMEGRANATE_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> POMEGRANATE_BOAT           = REGISTRY.registerItem("pomegranate_boat",       p -> new GotBoatItem(GotModBoatEntities.POMEGRANATE_BOAT.get(), p));
    public static final DeferredItem<Item> POMEGRANATE_CHEST_BOAT     = REGISTRY.registerItem("pomegranate_chest_boat", p -> new GotBoatItem(GotModBoatEntities.POMEGRANATE_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> POMEGRANATE_SAPLING        = block(GotModBlocks.POMEGRANATE_SAPLING);
    public static final DeferredItem<Item> STRIPPED_POMEGRANATE_LOG   = block(GotModBlocks.STRIPPED_POMEGRANATE_LOG);
    public static final DeferredItem<Item> STRIPPED_POMEGRANATE_WOOD  = block(GotModBlocks.STRIPPED_POMEGRANATE_WOOD);
    public static final DeferredItem<BlockItem> POMEGRANATE_ROOFING = REGISTRY.registerSimpleBlockItem("pomegranate_roofing", GotModBlocks.POMEGRANATE_ROOFING);
    public static final DeferredItem<BlockItem> POMEGRANATE_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("pomegranate_roofing_slab", GotModBlocks.POMEGRANATE_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> POMEGRANATE_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("pomegranate_roofing_stairs", GotModBlocks.POMEGRANATE_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> POMEGRANATE_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("pomegranate_roofing_wall", GotModBlocks.POMEGRANATE_ROOFING_WALL);
    public static final DeferredItem<Item> POMEGRANATE = REGISTRY.registerItem("pomegranate", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(5).saturationModifier(0.6f).build()));

    // ── prune items ──
    public static final DeferredItem<Item> PRUNE_LOG            = block(GotModBlocks.PRUNE_LOG);
    public static final DeferredItem<Item> PRUNE_WOOD           = block(GotModBlocks.PRUNE_WOOD);
    public static final DeferredItem<Item> PRUNE_PLANKS         = block(GotModBlocks.PRUNE_PLANKS);
    public static final DeferredItem<Item> PRUNE_LEAVES         = block(GotModBlocks.PRUNE_LEAVES);
    public static final DeferredItem<Item> PRUNE_STAIRS         = block(GotModBlocks.PRUNE_STAIRS);
    public static final DeferredItem<Item> PRUNE_SLAB           = block(GotModBlocks.PRUNE_SLAB);
    public static final DeferredItem<Item> PRUNE_FENCE          = block(GotModBlocks.PRUNE_FENCE);
    public static final DeferredItem<Item> PRUNE_FENCE_GATE     = block(GotModBlocks.PRUNE_FENCE_GATE);
    public static final DeferredItem<Item> PRUNE_PRESSURE_PLATE = block(GotModBlocks.PRUNE_PRESSURE_PLATE);
    public static final DeferredItem<Item> PRUNE_BUTTON         = block(GotModBlocks.PRUNE_BUTTON);
    public static final DeferredItem<Item> PRUNE_DOOR           = door(GotModBlocks.PRUNE_DOOR);
    public static final DeferredItem<Item> PRUNE_TRAPDOOR       = block(GotModBlocks.PRUNE_TRAPDOOR);
    public static final DeferredItem<Item> PRUNE_BRANCH         = block(GotModBlocks.PRUNE_BRANCH);
    public static final DeferredItem<Item> STRIPPED_PRUNE_BRANCH = block(GotModBlocks.STRIPPED_PRUNE_BRANCH);
    public static final DeferredItem<Item> PRUNE_SIGN           = REGISTRY.registerItem("prune_sign",         p -> new SignItem(GotModBlocks.PRUNE_SIGN.get(), GotModBlocks.PRUNE_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> PRUNE_HANGING_SIGN   = REGISTRY.registerItem("prune_hanging_sign", p -> new HangingSignItem(GotModBlocks.PRUNE_HANGING_SIGN.get(), GotModBlocks.PRUNE_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> PRUNE_BOAT           = REGISTRY.registerItem("prune_boat",       p -> new GotBoatItem(GotModBoatEntities.PRUNE_BOAT.get(), p));
    public static final DeferredItem<Item> PRUNE_CHEST_BOAT     = REGISTRY.registerItem("prune_chest_boat", p -> new GotBoatItem(GotModBoatEntities.PRUNE_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> PRUNE_SAPLING        = block(GotModBlocks.PRUNE_SAPLING);
    public static final DeferredItem<Item> STRIPPED_PRUNE_LOG   = block(GotModBlocks.STRIPPED_PRUNE_LOG);
    public static final DeferredItem<Item> STRIPPED_PRUNE_WOOD  = block(GotModBlocks.STRIPPED_PRUNE_WOOD);
    public static final DeferredItem<BlockItem> PRUNE_ROOFING = REGISTRY.registerSimpleBlockItem("prune_roofing", GotModBlocks.PRUNE_ROOFING);
    public static final DeferredItem<BlockItem> PRUNE_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("prune_roofing_slab", GotModBlocks.PRUNE_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> PRUNE_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("prune_roofing_stairs", GotModBlocks.PRUNE_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> PRUNE_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("prune_roofing_wall", GotModBlocks.PRUNE_ROOFING_WALL);
    public static final DeferredItem<Item> PRUNE = REGISTRY.registerItem("prune", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.4f).build()));

    // ── almond items ──
    public static final DeferredItem<Item> ALMOND_LOG            = block(GotModBlocks.ALMOND_LOG);
    public static final DeferredItem<Item> ALMOND_WOOD           = block(GotModBlocks.ALMOND_WOOD);
    public static final DeferredItem<Item> ALMOND_PLANKS         = block(GotModBlocks.ALMOND_PLANKS);
    public static final DeferredItem<Item> ALMOND_LEAVES         = block(GotModBlocks.ALMOND_LEAVES);
    public static final DeferredItem<Item> ALMOND_STAIRS         = block(GotModBlocks.ALMOND_STAIRS);
    public static final DeferredItem<Item> ALMOND_SLAB           = block(GotModBlocks.ALMOND_SLAB);
    public static final DeferredItem<Item> ALMOND_FENCE          = block(GotModBlocks.ALMOND_FENCE);
    public static final DeferredItem<Item> ALMOND_FENCE_GATE     = block(GotModBlocks.ALMOND_FENCE_GATE);
    public static final DeferredItem<Item> ALMOND_PRESSURE_PLATE = block(GotModBlocks.ALMOND_PRESSURE_PLATE);
    public static final DeferredItem<Item> ALMOND_BUTTON         = block(GotModBlocks.ALMOND_BUTTON);
    public static final DeferredItem<Item> ALMOND_DOOR           = door(GotModBlocks.ALMOND_DOOR);
    public static final DeferredItem<Item> ALMOND_TRAPDOOR       = block(GotModBlocks.ALMOND_TRAPDOOR);
    public static final DeferredItem<Item> ALMOND_BRANCH         = block(GotModBlocks.ALMOND_BRANCH);
    public static final DeferredItem<Item> STRIPPED_ALMOND_BRANCH = block(GotModBlocks.STRIPPED_ALMOND_BRANCH);
    public static final DeferredItem<Item> ALMOND_SIGN           = REGISTRY.registerItem("almond_sign",         p -> new SignItem(GotModBlocks.ALMOND_SIGN.get(), GotModBlocks.ALMOND_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> ALMOND_HANGING_SIGN   = REGISTRY.registerItem("almond_hanging_sign", p -> new HangingSignItem(GotModBlocks.ALMOND_HANGING_SIGN.get(), GotModBlocks.ALMOND_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> ALMOND_BOAT           = REGISTRY.registerItem("almond_boat",       p -> new GotBoatItem(GotModBoatEntities.ALMOND_BOAT.get(), p));
    public static final DeferredItem<Item> ALMOND_CHEST_BOAT     = REGISTRY.registerItem("almond_chest_boat", p -> new GotBoatItem(GotModBoatEntities.ALMOND_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> ALMOND_SAPLING        = block(GotModBlocks.ALMOND_SAPLING);
    public static final DeferredItem<Item> STRIPPED_ALMOND_LOG   = block(GotModBlocks.STRIPPED_ALMOND_LOG);
    public static final DeferredItem<Item> STRIPPED_ALMOND_WOOD  = block(GotModBlocks.STRIPPED_ALMOND_WOOD);
    public static final DeferredItem<BlockItem> ALMOND_ROOFING = REGISTRY.registerSimpleBlockItem("almond_roofing", GotModBlocks.ALMOND_ROOFING);
    public static final DeferredItem<BlockItem> ALMOND_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("almond_roofing_slab", GotModBlocks.ALMOND_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> ALMOND_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("almond_roofing_stairs", GotModBlocks.ALMOND_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> ALMOND_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("almond_roofing_wall", GotModBlocks.ALMOND_ROOFING_WALL);
    public static final DeferredItem<Item> ALMOND = REGISTRY.registerItem("almond", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.5f).build()));

    // ── nutmeg items ──
    public static final DeferredItem<Item> NUTMEG_LOG            = block(GotModBlocks.NUTMEG_LOG);
    public static final DeferredItem<Item> NUTMEG_WOOD           = block(GotModBlocks.NUTMEG_WOOD);
    public static final DeferredItem<Item> NUTMEG_PLANKS         = block(GotModBlocks.NUTMEG_PLANKS);
    public static final DeferredItem<Item> NUTMEG_LEAVES         = block(GotModBlocks.NUTMEG_LEAVES);
    public static final DeferredItem<Item> NUTMEG_STAIRS         = block(GotModBlocks.NUTMEG_STAIRS);
    public static final DeferredItem<Item> NUTMEG_SLAB           = block(GotModBlocks.NUTMEG_SLAB);
    public static final DeferredItem<Item> NUTMEG_FENCE          = block(GotModBlocks.NUTMEG_FENCE);
    public static final DeferredItem<Item> NUTMEG_FENCE_GATE     = block(GotModBlocks.NUTMEG_FENCE_GATE);
    public static final DeferredItem<Item> NUTMEG_PRESSURE_PLATE = block(GotModBlocks.NUTMEG_PRESSURE_PLATE);
    public static final DeferredItem<Item> NUTMEG_BUTTON         = block(GotModBlocks.NUTMEG_BUTTON);
    public static final DeferredItem<Item> NUTMEG_DOOR           = door(GotModBlocks.NUTMEG_DOOR);
    public static final DeferredItem<Item> NUTMEG_TRAPDOOR       = block(GotModBlocks.NUTMEG_TRAPDOOR);
    public static final DeferredItem<Item> NUTMEG_BRANCH         = block(GotModBlocks.NUTMEG_BRANCH);
    public static final DeferredItem<Item> STRIPPED_NUTMEG_BRANCH = block(GotModBlocks.STRIPPED_NUTMEG_BRANCH);
    public static final DeferredItem<Item> NUTMEG_SIGN           = REGISTRY.registerItem("nutmeg_sign",         p -> new SignItem(GotModBlocks.NUTMEG_SIGN.get(), GotModBlocks.NUTMEG_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> NUTMEG_HANGING_SIGN   = REGISTRY.registerItem("nutmeg_hanging_sign", p -> new HangingSignItem(GotModBlocks.NUTMEG_HANGING_SIGN.get(), GotModBlocks.NUTMEG_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> NUTMEG_BOAT           = REGISTRY.registerItem("nutmeg_boat",       p -> new GotBoatItem(GotModBoatEntities.NUTMEG_BOAT.get(), p));
    public static final DeferredItem<Item> NUTMEG_CHEST_BOAT     = REGISTRY.registerItem("nutmeg_chest_boat", p -> new GotBoatItem(GotModBoatEntities.NUTMEG_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> NUTMEG_SAPLING        = block(GotModBlocks.NUTMEG_SAPLING);
    public static final DeferredItem<Item> STRIPPED_NUTMEG_LOG   = block(GotModBlocks.STRIPPED_NUTMEG_LOG);
    public static final DeferredItem<Item> STRIPPED_NUTMEG_WOOD  = block(GotModBlocks.STRIPPED_NUTMEG_WOOD);
    public static final DeferredItem<BlockItem> NUTMEG_ROOFING = REGISTRY.registerSimpleBlockItem("nutmeg_roofing", GotModBlocks.NUTMEG_ROOFING);
    public static final DeferredItem<BlockItem> NUTMEG_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("nutmeg_roofing_slab", GotModBlocks.NUTMEG_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> NUTMEG_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("nutmeg_roofing_stairs", GotModBlocks.NUTMEG_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> NUTMEG_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("nutmeg_roofing_wall", GotModBlocks.NUTMEG_ROOFING_WALL);
    public static final DeferredItem<Item> NUTMEG = REGISTRY.registerItem("nutmeg", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.4f).build()));

    // ── Meats (raw & cooked) ─────────────────────────────────────────────
    public static final DeferredItem<Item> RAW_BEAR_MEAT       = REGISTRY.registerItem("raw_bear_meat",       p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.3f).build()));
    public static final DeferredItem<Item> COOKED_BEAR_MEAT    = REGISTRY.registerItem("cooked_bear_meat",    p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.8f).build()));
    public static final DeferredItem<Item> RAW_MAMMOTH_MEAT    = REGISTRY.registerItem("raw_mammoth_meat",    p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.3f).build()));
    public static final DeferredItem<Item> COOKED_MAMMOTH_MEAT = REGISTRY.registerItem("cooked_mammoth_meat", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.8f).build()));
    public static final DeferredItem<Item> RAW_HORSE_MEAT      = REGISTRY.registerItem("raw_horse_meat",      p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.3f).build()));
    public static final DeferredItem<Item> COOKED_HORSE_MEAT   = REGISTRY.registerItem("cooked_horse_meat",   p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(7).saturationModifier(0.6f).build()));
    public static final DeferredItem<Item> RAW_HERON      = REGISTRY.registerItem("raw_heron",      p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.3f).build()));
    public static final DeferredItem<Item> COOKED_HERON   = REGISTRY.registerItem("cooked_heron",   p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.6f).build()));
    public static final DeferredItem<Item> RAW_VENISON         = REGISTRY.registerItem("raw_venison",         p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.3f).build()));
    public static final DeferredItem<Item> COOKED_VENISON      = REGISTRY.registerItem("cooked_venison",      p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(7).saturationModifier(0.7f).build()));

    // ── hemlock items ──
    public static final DeferredItem<Item> HEMLOCK_LOG            = block(GotModBlocks.HEMLOCK_LOG);
    public static final DeferredItem<Item> HEMLOCK_WOOD           = block(GotModBlocks.HEMLOCK_WOOD);
    public static final DeferredItem<Item> HEMLOCK_PLANKS         = block(GotModBlocks.HEMLOCK_PLANKS);
    public static final DeferredItem<Item> HEMLOCK_LEAVES         = block(GotModBlocks.HEMLOCK_LEAVES);
    public static final DeferredItem<Item> HEMLOCK_STAIRS         = block(GotModBlocks.HEMLOCK_STAIRS);
    public static final DeferredItem<Item> HEMLOCK_SLAB           = block(GotModBlocks.HEMLOCK_SLAB);
    public static final DeferredItem<Item> HEMLOCK_FENCE          = block(GotModBlocks.HEMLOCK_FENCE);
    public static final DeferredItem<Item> HEMLOCK_FENCE_GATE     = block(GotModBlocks.HEMLOCK_FENCE_GATE);
    public static final DeferredItem<Item> HEMLOCK_PRESSURE_PLATE = block(GotModBlocks.HEMLOCK_PRESSURE_PLATE);
    public static final DeferredItem<Item> HEMLOCK_BUTTON         = block(GotModBlocks.HEMLOCK_BUTTON);
    public static final DeferredItem<Item> HEMLOCK_DOOR           = door(GotModBlocks.HEMLOCK_DOOR);
    public static final DeferredItem<Item> HEMLOCK_TRAPDOOR       = block(GotModBlocks.HEMLOCK_TRAPDOOR);
    public static final DeferredItem<Item> HEMLOCK_BRANCH         = block(GotModBlocks.HEMLOCK_BRANCH);
    public static final DeferredItem<Item> STRIPPED_HEMLOCK_BRANCH = block(GotModBlocks.STRIPPED_HEMLOCK_BRANCH);
    public static final DeferredItem<Item> HEMLOCK_SIGN           = REGISTRY.registerItem("hemlock_sign",         p -> new SignItem(GotModBlocks.HEMLOCK_SIGN.get(), GotModBlocks.HEMLOCK_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> HEMLOCK_HANGING_SIGN   = REGISTRY.registerItem("hemlock_hanging_sign", p -> new HangingSignItem(GotModBlocks.HEMLOCK_HANGING_SIGN.get(), GotModBlocks.HEMLOCK_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> HEMLOCK_BOAT           = REGISTRY.registerItem("hemlock_boat",       p -> new GotBoatItem(GotModBoatEntities.HEMLOCK_BOAT.get(), p));
    public static final DeferredItem<Item> HEMLOCK_CHEST_BOAT     = REGISTRY.registerItem("hemlock_chest_boat", p -> new GotBoatItem(GotModBoatEntities.HEMLOCK_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> HEMLOCK_SAPLING        = block(GotModBlocks.HEMLOCK_SAPLING);
    public static final DeferredItem<Item> STRIPPED_HEMLOCK_LOG   = block(GotModBlocks.STRIPPED_HEMLOCK_LOG);
    public static final DeferredItem<Item> STRIPPED_HEMLOCK_WOOD  = block(GotModBlocks.STRIPPED_HEMLOCK_WOOD);
    public static final DeferredItem<BlockItem> HEMLOCK_ROOFING = REGISTRY.registerSimpleBlockItem("hemlock_roofing", GotModBlocks.HEMLOCK_ROOFING);
    public static final DeferredItem<BlockItem> HEMLOCK_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("hemlock_roofing_slab", GotModBlocks.HEMLOCK_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> HEMLOCK_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("hemlock_roofing_stairs", GotModBlocks.HEMLOCK_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> HEMLOCK_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("hemlock_roofing_wall", GotModBlocks.HEMLOCK_ROOFING_WALL);


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