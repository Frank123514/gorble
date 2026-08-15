package net.got.init;

import net.got.GotMod;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.equipment.ArmorType;
import net.got.item.GotBoatItem;
import net.minecraft.core.component.DataComponents;

import java.util.function.Function;

public class ModItems {
    public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(GotMod.MODID);
    public static final DeferredItem<Item> WEIRWOOD_LOG = block(ModBlocks.WEIRWOOD_LOG);
    public static final DeferredItem<Item> WEIRWOOD_WOOD = block(ModBlocks.WEIRWOOD_WOOD);
    public static final DeferredItem<Item> WEIRWOOD_PLANKS = block(ModBlocks.WEIRWOOD_PLANKS);
    public static final DeferredItem<Item> WEIRWOOD_LEAVES = block(ModBlocks.WEIRWOOD_LEAVES);
    public static final DeferredItem<Item> WEIRWOOD_STAIRS = block(ModBlocks.WEIRWOOD_STAIRS);
    public static final DeferredItem<Item> WEIRWOOD_SLAB = block(ModBlocks.WEIRWOOD_SLAB);
    public static final DeferredItem<Item> WEIRWOOD_FENCE = block(ModBlocks.WEIRWOOD_FENCE);
    public static final DeferredItem<Item> WEIRWOOD_FENCE_GATE = block(ModBlocks.WEIRWOOD_FENCE_GATE);
    public static final DeferredItem<Item> WEIRWOOD_PRESSURE_PLATE = block(ModBlocks.WEIRWOOD_PRESSURE_PLATE);
    public static final DeferredItem<Item> WEIRWOOD_BUTTON = block(ModBlocks.WEIRWOOD_BUTTON);
    public static final DeferredItem<Item> ASPEN_LOG = block(ModBlocks.ASPEN_LOG);
    public static final DeferredItem<Item> ASPEN_WOOD = block(ModBlocks.ASPEN_WOOD);
    public static final DeferredItem<Item> ASPEN_PLANKS = block(ModBlocks.ASPEN_PLANKS);
    public static final DeferredItem<Item> ASPEN_LEAVES = block(ModBlocks.ASPEN_LEAVES);
    public static final DeferredItem<Item> ASPEN_STAIRS = block(ModBlocks.ASPEN_STAIRS);
    public static final DeferredItem<Item> ASPEN_SLAB = block(ModBlocks.ASPEN_SLAB);
    public static final DeferredItem<Item> ASPEN_FENCE = block(ModBlocks.ASPEN_FENCE);
    public static final DeferredItem<Item> ASPEN_FENCE_GATE = block(ModBlocks.ASPEN_FENCE_GATE);
    public static final DeferredItem<Item> ASPEN_PRESSURE_PLATE = block(ModBlocks.ASPEN_PRESSURE_PLATE);
    public static final DeferredItem<Item> ASPEN_BUTTON = block(ModBlocks.ASPEN_BUTTON);
    public static final DeferredItem<Item> ALDER_LOG = block(ModBlocks.ALDER_LOG);
    public static final DeferredItem<Item> ALDER_WOOD = block(ModBlocks.ALDER_WOOD);
    public static final DeferredItem<Item> ALDER_PLANKS = block(ModBlocks.ALDER_PLANKS);
    public static final DeferredItem<Item> ALDER_LEAVES = block(ModBlocks.ALDER_LEAVES);
    public static final DeferredItem<Item> ALDER_STAIRS = block(ModBlocks.ALDER_STAIRS);
    public static final DeferredItem<Item> ALDER_SLAB = block(ModBlocks.ALDER_SLAB);
    public static final DeferredItem<Item> ALDER_FENCE = block(ModBlocks.ALDER_FENCE);
    public static final DeferredItem<Item> ALDER_FENCE_GATE = block(ModBlocks.ALDER_FENCE_GATE);
    public static final DeferredItem<Item> ALDER_PRESSURE_PLATE = block(ModBlocks.ALDER_PRESSURE_PLATE);
    public static final DeferredItem<Item> ALDER_BUTTON = block(ModBlocks.ALDER_BUTTON);
    public static final DeferredItem<Item> PINE_LOG = block(ModBlocks.PINE_LOG);
    public static final DeferredItem<Item> PINE_WOOD = block(ModBlocks.PINE_WOOD);
    public static final DeferredItem<Item> PINE_PLANKS = block(ModBlocks.PINE_PLANKS);
    public static final DeferredItem<Item> PINE_LEAVES = block(ModBlocks.PINE_LEAVES);
    public static final DeferredItem<Item> PINE_STAIRS = block(ModBlocks.PINE_STAIRS);
    public static final DeferredItem<Item> PINE_SLAB = block(ModBlocks.PINE_SLAB);
    public static final DeferredItem<Item> PINE_FENCE = block(ModBlocks.PINE_FENCE);
    public static final DeferredItem<Item> PINE_FENCE_GATE = block(ModBlocks.PINE_FENCE_GATE);
    public static final DeferredItem<Item> PINE_PRESSURE_PLATE = block(ModBlocks.PINE_PRESSURE_PLATE);
    public static final DeferredItem<Item> PINE_BUTTON = block(ModBlocks.PINE_BUTTON);
    public static final DeferredItem<Item> FIR_LOG = block(ModBlocks.FIR_LOG);
    public static final DeferredItem<Item> FIR_WOOD = block(ModBlocks.FIR_WOOD);
    public static final DeferredItem<Item> FIR_PLANKS = block(ModBlocks.FIR_PLANKS);
    public static final DeferredItem<Item> FIR_LEAVES = block(ModBlocks.FIR_LEAVES);
    public static final DeferredItem<Item> FIR_STAIRS = block(ModBlocks.FIR_STAIRS);
    public static final DeferredItem<Item> FIR_SLAB = block(ModBlocks.FIR_SLAB);
    public static final DeferredItem<Item> FIR_FENCE = block(ModBlocks.FIR_FENCE);
    public static final DeferredItem<Item> FIR_FENCE_GATE = block(ModBlocks.FIR_FENCE_GATE);
    public static final DeferredItem<Item> FIR_PRESSURE_PLATE = block(ModBlocks.FIR_PRESSURE_PLATE);
    public static final DeferredItem<Item> FIR_BUTTON = block(ModBlocks.FIR_BUTTON);
    public static final DeferredItem<Item> SENTINAL_LOG = block(ModBlocks.SENTINAL_LOG);
    public static final DeferredItem<Item> SENTINAL_WOOD = block(ModBlocks.SENTINAL_WOOD);
    public static final DeferredItem<Item> SENTINAL_PLANKS = block(ModBlocks.SENTINAL_PLANKS);
    public static final DeferredItem<Item> SENTINAL_LEAVES = block(ModBlocks.SENTINAL_LEAVES);
    public static final DeferredItem<Item> SENTINAL_STAIRS = block(ModBlocks.SENTINAL_STAIRS);
    public static final DeferredItem<Item> SENTINAL_SLAB = block(ModBlocks.SENTINAL_SLAB);
    public static final DeferredItem<Item> SENTINAL_FENCE = block(ModBlocks.SENTINAL_FENCE);
    public static final DeferredItem<Item> SENTINAL_FENCE_GATE = block(ModBlocks.SENTINAL_FENCE_GATE);
    public static final DeferredItem<Item> SENTINAL_PRESSURE_PLATE = block(ModBlocks.SENTINAL_PRESSURE_PLATE);
    public static final DeferredItem<Item> SENTINAL_BUTTON = block(ModBlocks.SENTINAL_BUTTON);
    public static final DeferredItem<Item> IRONWOOD_LOG = block(ModBlocks.IRONWOOD_LOG);
    public static final DeferredItem<Item> IRONWOOD_WOOD = block(ModBlocks.IRONWOOD_WOOD);
    public static final DeferredItem<Item> IRONWOOD_PLANKS = block(ModBlocks.IRONWOOD_PLANKS);
    public static final DeferredItem<Item> IRONWOOD_LEAVES = block(ModBlocks.IRONWOOD_LEAVES);
    public static final DeferredItem<Item> IRONWOOD_STAIRS = block(ModBlocks.IRONWOOD_STAIRS);
    public static final DeferredItem<Item> IRONWOOD_SLAB = block(ModBlocks.IRONWOOD_SLAB);
    public static final DeferredItem<Item> IRONWOOD_FENCE = block(ModBlocks.IRONWOOD_FENCE);
    public static final DeferredItem<Item> IRONWOOD_FENCE_GATE = block(ModBlocks.IRONWOOD_FENCE_GATE);
    public static final DeferredItem<Item> IRONWOOD_PRESSURE_PLATE = block(ModBlocks.IRONWOOD_PRESSURE_PLATE);
    public static final DeferredItem<Item> IRONWOOD_BUTTON = block(ModBlocks.IRONWOOD_BUTTON);
    public static final DeferredItem<Item> BEECH_LOG = block(ModBlocks.BEECH_LOG);
    public static final DeferredItem<Item> BEECH_WOOD = block(ModBlocks.BEECH_WOOD);
    public static final DeferredItem<Item> BEECH_PLANKS = block(ModBlocks.BEECH_PLANKS);
    public static final DeferredItem<Item> BEECH_LEAVES = block(ModBlocks.BEECH_LEAVES);
    public static final DeferredItem<Item> BEECH_STAIRS = block(ModBlocks.BEECH_STAIRS);
    public static final DeferredItem<Item> BEECH_SLAB = block(ModBlocks.BEECH_SLAB);
    public static final DeferredItem<Item> BEECH_FENCE = block(ModBlocks.BEECH_FENCE);
    public static final DeferredItem<Item> BEECH_FENCE_GATE = block(ModBlocks.BEECH_FENCE_GATE);
    public static final DeferredItem<Item> BEECH_PRESSURE_PLATE = block(ModBlocks.BEECH_PRESSURE_PLATE);
    public static final DeferredItem<Item> BEECH_BUTTON = block(ModBlocks.BEECH_BUTTON);
    public static final DeferredItem<Item> SOLDIER_PINE_LOG = block(ModBlocks.SOLDIER_PINE_LOG);
    public static final DeferredItem<Item> SOLDIER_PINE_WOOD = block(ModBlocks.SOLDIER_PINE_WOOD);
    public static final DeferredItem<Item> SOLDIER_PINE_PLANKS = block(ModBlocks.SOLDIER_PINE_PLANKS);
    public static final DeferredItem<Item> SOLDIER_PINE_LEAVES = block(ModBlocks.SOLDIER_PINE_LEAVES);
    public static final DeferredItem<Item> SOLDIER_PINE_STAIRS = block(ModBlocks.SOLDIER_PINE_STAIRS);
    public static final DeferredItem<Item> SOLDIER_PINE_SLAB = block(ModBlocks.SOLDIER_PINE_SLAB);
    public static final DeferredItem<Item> SOLDIER_PINE_FENCE = block(ModBlocks.SOLDIER_PINE_FENCE);
    public static final DeferredItem<Item> SOLDIER_PINE_FENCE_GATE = block(ModBlocks.SOLDIER_PINE_FENCE_GATE);
    public static final DeferredItem<Item> SOLDIER_PINE_PRESSURE_PLATE = block(ModBlocks.SOLDIER_PINE_PRESSURE_PLATE);
    public static final DeferredItem<Item> SOLDIER_PINE_BUTTON = block(ModBlocks.SOLDIER_PINE_BUTTON);
    public static final DeferredItem<Item> ASH_LOG = block(ModBlocks.ASH_LOG);
    public static final DeferredItem<Item> ASH_WOOD = block(ModBlocks.ASH_WOOD);
    public static final DeferredItem<Item> ASH_PLANKS = block(ModBlocks.ASH_PLANKS);
    public static final DeferredItem<Item> ASH_LEAVES = block(ModBlocks.ASH_LEAVES);
    public static final DeferredItem<Item> ASH_STAIRS = block(ModBlocks.ASH_STAIRS);
    public static final DeferredItem<Item> ASH_SLAB = block(ModBlocks.ASH_SLAB);
    public static final DeferredItem<Item> ASH_FENCE = block(ModBlocks.ASH_FENCE);
    public static final DeferredItem<Item> ASH_FENCE_GATE = block(ModBlocks.ASH_FENCE_GATE);
    public static final DeferredItem<Item> ASH_PRESSURE_PLATE = block(ModBlocks.ASH_PRESSURE_PLATE);
    public static final DeferredItem<Item> ASH_BUTTON = block(ModBlocks.ASH_BUTTON);
    public static final DeferredItem<Item> HAWTHORN_LOG = block(ModBlocks.HAWTHORN_LOG);
    public static final DeferredItem<Item> HAWTHORN_WOOD = block(ModBlocks.HAWTHORN_WOOD);
    public static final DeferredItem<Item> HAWTHORN_PLANKS = block(ModBlocks.HAWTHORN_PLANKS);
    public static final DeferredItem<Item> HAWTHORN_LEAVES = block(ModBlocks.HAWTHORN_LEAVES);
    public static final DeferredItem<Item> HAWTHORN_STAIRS = block(ModBlocks.HAWTHORN_STAIRS);
    public static final DeferredItem<Item> HAWTHORN_SLAB = block(ModBlocks.HAWTHORN_SLAB);
    public static final DeferredItem<Item> HAWTHORN_FENCE = block(ModBlocks.HAWTHORN_FENCE);
    public static final DeferredItem<Item> HAWTHORN_FENCE_GATE = block(ModBlocks.HAWTHORN_FENCE_GATE);
    public static final DeferredItem<Item> HAWTHORN_PRESSURE_PLATE = block(ModBlocks.HAWTHORN_PRESSURE_PLATE);
    public static final DeferredItem<Item> HAWTHORN_BUTTON = block(ModBlocks.HAWTHORN_BUTTON);

    public static final DeferredItem<Item> BLACKBARK_LOG            = block(ModBlocks.BLACKBARK_LOG);
    public static final DeferredItem<Item> BLACKBARK_WOOD           = block(ModBlocks.BLACKBARK_WOOD);
    public static final DeferredItem<Item> BLACKBARK_PLANKS         = block(ModBlocks.BLACKBARK_PLANKS);
    public static final DeferredItem<Item> BLACKBARK_LEAVES         = block(ModBlocks.BLACKBARK_LEAVES);
    public static final DeferredItem<Item> BLACKBARK_STAIRS         = block(ModBlocks.BLACKBARK_STAIRS);
    public static final DeferredItem<Item> BLACKBARK_SLAB           = block(ModBlocks.BLACKBARK_SLAB);
    public static final DeferredItem<Item> BLACKBARK_FENCE          = block(ModBlocks.BLACKBARK_FENCE);
    public static final DeferredItem<Item> BLACKBARK_FENCE_GATE     = block(ModBlocks.BLACKBARK_FENCE_GATE);
    public static final DeferredItem<Item> BLACKBARK_PRESSURE_PLATE = block(ModBlocks.BLACKBARK_PRESSURE_PLATE);
    public static final DeferredItem<Item> BLACKBARK_BUTTON         = block(ModBlocks.BLACKBARK_BUTTON);

    public static final DeferredItem<Item> BLOODWOOD_LOG            = block(ModBlocks.BLOODWOOD_LOG);
    public static final DeferredItem<Item> BLOODWOOD_WOOD           = block(ModBlocks.BLOODWOOD_WOOD);
    public static final DeferredItem<Item> BLOODWOOD_PLANKS         = block(ModBlocks.BLOODWOOD_PLANKS);
    public static final DeferredItem<Item> BLOODWOOD_LEAVES         = block(ModBlocks.BLOODWOOD_LEAVES);
    public static final DeferredItem<Item> BLOODWOOD_STAIRS         = block(ModBlocks.BLOODWOOD_STAIRS);
    public static final DeferredItem<Item> BLOODWOOD_SLAB           = block(ModBlocks.BLOODWOOD_SLAB);
    public static final DeferredItem<Item> BLOODWOOD_FENCE          = block(ModBlocks.BLOODWOOD_FENCE);
    public static final DeferredItem<Item> BLOODWOOD_FENCE_GATE     = block(ModBlocks.BLOODWOOD_FENCE_GATE);
    public static final DeferredItem<Item> BLOODWOOD_PRESSURE_PLATE = block(ModBlocks.BLOODWOOD_PRESSURE_PLATE);
    public static final DeferredItem<Item> BLOODWOOD_BUTTON         = block(ModBlocks.BLOODWOOD_BUTTON);

    public static final DeferredItem<Item> BLUE_MAHOE_LOG            = block(ModBlocks.BLUE_MAHOE_LOG);
    public static final DeferredItem<Item> BLUE_MAHOE_WOOD           = block(ModBlocks.BLUE_MAHOE_WOOD);
    public static final DeferredItem<Item> BLUE_MAHOE_PLANKS         = block(ModBlocks.BLUE_MAHOE_PLANKS);
    public static final DeferredItem<Item> BLUE_MAHOE_LEAVES         = block(ModBlocks.BLUE_MAHOE_LEAVES);
    public static final DeferredItem<Item> BLUE_MAHOE_STAIRS         = block(ModBlocks.BLUE_MAHOE_STAIRS);
    public static final DeferredItem<Item> BLUE_MAHOE_SLAB           = block(ModBlocks.BLUE_MAHOE_SLAB);
    public static final DeferredItem<Item> BLUE_MAHOE_FENCE          = block(ModBlocks.BLUE_MAHOE_FENCE);
    public static final DeferredItem<Item> BLUE_MAHOE_FENCE_GATE     = block(ModBlocks.BLUE_MAHOE_FENCE_GATE);
    public static final DeferredItem<Item> BLUE_MAHOE_PRESSURE_PLATE = block(ModBlocks.BLUE_MAHOE_PRESSURE_PLATE);
    public static final DeferredItem<Item> BLUE_MAHOE_BUTTON         = block(ModBlocks.BLUE_MAHOE_BUTTON);

    public static final DeferredItem<Item> COTTONWOOD_LOG            = block(ModBlocks.COTTONWOOD_LOG);
    public static final DeferredItem<Item> COTTONWOOD_WOOD           = block(ModBlocks.COTTONWOOD_WOOD);
    public static final DeferredItem<Item> COTTONWOOD_PLANKS         = block(ModBlocks.COTTONWOOD_PLANKS);
    public static final DeferredItem<Item> COTTONWOOD_LEAVES         = block(ModBlocks.COTTONWOOD_LEAVES);
    public static final DeferredItem<Item> COTTONWOOD_STAIRS         = block(ModBlocks.COTTONWOOD_STAIRS);
    public static final DeferredItem<Item> COTTONWOOD_SLAB           = block(ModBlocks.COTTONWOOD_SLAB);
    public static final DeferredItem<Item> COTTONWOOD_FENCE          = block(ModBlocks.COTTONWOOD_FENCE);
    public static final DeferredItem<Item> COTTONWOOD_FENCE_GATE     = block(ModBlocks.COTTONWOOD_FENCE_GATE);
    public static final DeferredItem<Item> COTTONWOOD_PRESSURE_PLATE = block(ModBlocks.COTTONWOOD_PRESSURE_PLATE);
    public static final DeferredItem<Item> COTTONWOOD_BUTTON         = block(ModBlocks.COTTONWOOD_BUTTON);

    public static final DeferredItem<Item> BLACK_COTTONWOOD_LOG            = block(ModBlocks.BLACK_COTTONWOOD_LOG);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_WOOD           = block(ModBlocks.BLACK_COTTONWOOD_WOOD);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_PLANKS         = block(ModBlocks.BLACK_COTTONWOOD_PLANKS);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_LEAVES         = block(ModBlocks.BLACK_COTTONWOOD_LEAVES);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_STAIRS         = block(ModBlocks.BLACK_COTTONWOOD_STAIRS);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_SLAB           = block(ModBlocks.BLACK_COTTONWOOD_SLAB);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_FENCE          = block(ModBlocks.BLACK_COTTONWOOD_FENCE);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_FENCE_GATE     = block(ModBlocks.BLACK_COTTONWOOD_FENCE_GATE);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_PRESSURE_PLATE = block(ModBlocks.BLACK_COTTONWOOD_PRESSURE_PLATE);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_BUTTON         = block(ModBlocks.BLACK_COTTONWOOD_BUTTON);

    public static final DeferredItem<Item> CINNAMON_LOG            = block(ModBlocks.CINNAMON_LOG);
    public static final DeferredItem<Item> CINNAMON_WOOD           = block(ModBlocks.CINNAMON_WOOD);
    public static final DeferredItem<Item> CINNAMON_PLANKS         = block(ModBlocks.CINNAMON_PLANKS);
    public static final DeferredItem<Item> CINNAMON_LEAVES         = block(ModBlocks.CINNAMON_LEAVES);
    public static final DeferredItem<Item> CINNAMON_STAIRS         = block(ModBlocks.CINNAMON_STAIRS);
    public static final DeferredItem<Item> CINNAMON_SLAB           = block(ModBlocks.CINNAMON_SLAB);
    public static final DeferredItem<Item> CINNAMON_FENCE          = block(ModBlocks.CINNAMON_FENCE);
    public static final DeferredItem<Item> CINNAMON_FENCE_GATE     = block(ModBlocks.CINNAMON_FENCE_GATE);
    public static final DeferredItem<Item> CINNAMON_PRESSURE_PLATE = block(ModBlocks.CINNAMON_PRESSURE_PLATE);
    public static final DeferredItem<Item> CINNAMON_BUTTON         = block(ModBlocks.CINNAMON_BUTTON);

    public static final DeferredItem<Item> CLOVE_LOG            = block(ModBlocks.CLOVE_LOG);
    public static final DeferredItem<Item> CLOVE_WOOD           = block(ModBlocks.CLOVE_WOOD);
    public static final DeferredItem<Item> CLOVE_PLANKS         = block(ModBlocks.CLOVE_PLANKS);
    public static final DeferredItem<Item> CLOVE_LEAVES         = block(ModBlocks.CLOVE_LEAVES);
    public static final DeferredItem<Item> CLOVE_STAIRS         = block(ModBlocks.CLOVE_STAIRS);
    public static final DeferredItem<Item> CLOVE_SLAB           = block(ModBlocks.CLOVE_SLAB);
    public static final DeferredItem<Item> CLOVE_FENCE          = block(ModBlocks.CLOVE_FENCE);
    public static final DeferredItem<Item> CLOVE_FENCE_GATE     = block(ModBlocks.CLOVE_FENCE_GATE);
    public static final DeferredItem<Item> CLOVE_PRESSURE_PLATE = block(ModBlocks.CLOVE_PRESSURE_PLATE);
    public static final DeferredItem<Item> CLOVE_BUTTON         = block(ModBlocks.CLOVE_BUTTON);

    public static final DeferredItem<Item> EBONY_LOG            = block(ModBlocks.EBONY_LOG);
    public static final DeferredItem<Item> EBONY_WOOD           = block(ModBlocks.EBONY_WOOD);
    public static final DeferredItem<Item> EBONY_PLANKS         = block(ModBlocks.EBONY_PLANKS);
    public static final DeferredItem<Item> EBONY_LEAVES         = block(ModBlocks.EBONY_LEAVES);
    public static final DeferredItem<Item> EBONY_STAIRS         = block(ModBlocks.EBONY_STAIRS);
    public static final DeferredItem<Item> EBONY_SLAB           = block(ModBlocks.EBONY_SLAB);
    public static final DeferredItem<Item> EBONY_FENCE          = block(ModBlocks.EBONY_FENCE);
    public static final DeferredItem<Item> EBONY_FENCE_GATE     = block(ModBlocks.EBONY_FENCE_GATE);
    public static final DeferredItem<Item> EBONY_PRESSURE_PLATE = block(ModBlocks.EBONY_PRESSURE_PLATE);
    public static final DeferredItem<Item> EBONY_BUTTON         = block(ModBlocks.EBONY_BUTTON);

    public static final DeferredItem<Item> ELM_LOG            = block(ModBlocks.ELM_LOG);
    public static final DeferredItem<Item> ELM_WOOD           = block(ModBlocks.ELM_WOOD);
    public static final DeferredItem<Item> ELM_PLANKS         = block(ModBlocks.ELM_PLANKS);
    public static final DeferredItem<Item> ELM_LEAVES         = block(ModBlocks.ELM_LEAVES);
    public static final DeferredItem<Item> ELM_STAIRS         = block(ModBlocks.ELM_STAIRS);
    public static final DeferredItem<Item> ELM_SLAB           = block(ModBlocks.ELM_SLAB);
    public static final DeferredItem<Item> ELM_FENCE          = block(ModBlocks.ELM_FENCE);
    public static final DeferredItem<Item> ELM_FENCE_GATE     = block(ModBlocks.ELM_FENCE_GATE);
    public static final DeferredItem<Item> ELM_PRESSURE_PLATE = block(ModBlocks.ELM_PRESSURE_PLATE);
    public static final DeferredItem<Item> ELM_BUTTON         = block(ModBlocks.ELM_BUTTON);

    public static final DeferredItem<Item> CEDAR_LOG            = block(ModBlocks.CEDAR_LOG);
    public static final DeferredItem<Item> CEDAR_WOOD           = block(ModBlocks.CEDAR_WOOD);
    public static final DeferredItem<Item> CEDAR_PLANKS         = block(ModBlocks.CEDAR_PLANKS);
    public static final DeferredItem<Item> CEDAR_LEAVES         = block(ModBlocks.CEDAR_LEAVES);
    public static final DeferredItem<Item> CEDAR_STAIRS         = block(ModBlocks.CEDAR_STAIRS);
    public static final DeferredItem<Item> CEDAR_SLAB           = block(ModBlocks.CEDAR_SLAB);
    public static final DeferredItem<Item> CEDAR_FENCE          = block(ModBlocks.CEDAR_FENCE);
    public static final DeferredItem<Item> CEDAR_FENCE_GATE     = block(ModBlocks.CEDAR_FENCE_GATE);
    public static final DeferredItem<Item> CEDAR_PRESSURE_PLATE = block(ModBlocks.CEDAR_PRESSURE_PLATE);
    public static final DeferredItem<Item> CEDAR_BUTTON         = block(ModBlocks.CEDAR_BUTTON);

    public static final DeferredItem<Item> APPLE_LOG            = block(ModBlocks.APPLE_LOG);
    public static final DeferredItem<Item> APPLE_WOOD           = block(ModBlocks.APPLE_WOOD);
    public static final DeferredItem<Item> APPLE_PLANKS         = block(ModBlocks.APPLE_PLANKS);
    public static final DeferredItem<Item> APPLE_LEAVES         = block(ModBlocks.APPLE_LEAVES);
    public static final DeferredItem<Item> APPLE_STAIRS         = block(ModBlocks.APPLE_STAIRS);
    public static final DeferredItem<Item> APPLE_SLAB           = block(ModBlocks.APPLE_SLAB);
    public static final DeferredItem<Item> APPLE_FENCE          = block(ModBlocks.APPLE_FENCE);
    public static final DeferredItem<Item> APPLE_FENCE_GATE     = block(ModBlocks.APPLE_FENCE_GATE);
    public static final DeferredItem<Item> APPLE_PRESSURE_PLATE = block(ModBlocks.APPLE_PRESSURE_PLATE);
    public static final DeferredItem<Item> APPLE_BUTTON         = block(ModBlocks.APPLE_BUTTON);

    public static final DeferredItem<Item> GOLDENHEART_LOG            = block(ModBlocks.GOLDENHEART_LOG);
    public static final DeferredItem<Item> GOLDENHEART_WOOD           = block(ModBlocks.GOLDENHEART_WOOD);
    public static final DeferredItem<Item> GOLDENHEART_PLANKS         = block(ModBlocks.GOLDENHEART_PLANKS);
    public static final DeferredItem<Item> GOLDENHEART_LEAVES         = block(ModBlocks.GOLDENHEART_LEAVES);
    public static final DeferredItem<Item> GOLDENHEART_STAIRS         = block(ModBlocks.GOLDENHEART_STAIRS);
    public static final DeferredItem<Item> GOLDENHEART_SLAB           = block(ModBlocks.GOLDENHEART_SLAB);
    public static final DeferredItem<Item> GOLDENHEART_FENCE          = block(ModBlocks.GOLDENHEART_FENCE);
    public static final DeferredItem<Item> GOLDENHEART_FENCE_GATE     = block(ModBlocks.GOLDENHEART_FENCE_GATE);
    public static final DeferredItem<Item> GOLDENHEART_PRESSURE_PLATE = block(ModBlocks.GOLDENHEART_PRESSURE_PLATE);
    public static final DeferredItem<Item> GOLDENHEART_BUTTON         = block(ModBlocks.GOLDENHEART_BUTTON);

    public static final DeferredItem<Item> LINDEN_LOG            = block(ModBlocks.LINDEN_LOG);
    public static final DeferredItem<Item> LINDEN_WOOD           = block(ModBlocks.LINDEN_WOOD);
    public static final DeferredItem<Item> LINDEN_PLANKS         = block(ModBlocks.LINDEN_PLANKS);
    public static final DeferredItem<Item> LINDEN_LEAVES         = block(ModBlocks.LINDEN_LEAVES);
    public static final DeferredItem<Item> LINDEN_STAIRS         = block(ModBlocks.LINDEN_STAIRS);
    public static final DeferredItem<Item> LINDEN_SLAB           = block(ModBlocks.LINDEN_SLAB);
    public static final DeferredItem<Item> LINDEN_FENCE          = block(ModBlocks.LINDEN_FENCE);
    public static final DeferredItem<Item> LINDEN_FENCE_GATE     = block(ModBlocks.LINDEN_FENCE_GATE);
    public static final DeferredItem<Item> LINDEN_PRESSURE_PLATE = block(ModBlocks.LINDEN_PRESSURE_PLATE);
    public static final DeferredItem<Item> LINDEN_BUTTON         = block(ModBlocks.LINDEN_BUTTON);

    public static final DeferredItem<Item> MAHOGANY_LOG            = block(ModBlocks.MAHOGANY_LOG);
    public static final DeferredItem<Item> MAHOGANY_WOOD           = block(ModBlocks.MAHOGANY_WOOD);
    public static final DeferredItem<Item> MAHOGANY_PLANKS         = block(ModBlocks.MAHOGANY_PLANKS);
    public static final DeferredItem<Item> MAHOGANY_LEAVES         = block(ModBlocks.MAHOGANY_LEAVES);
    public static final DeferredItem<Item> MAHOGANY_STAIRS         = block(ModBlocks.MAHOGANY_STAIRS);
    public static final DeferredItem<Item> MAHOGANY_SLAB           = block(ModBlocks.MAHOGANY_SLAB);
    public static final DeferredItem<Item> MAHOGANY_FENCE          = block(ModBlocks.MAHOGANY_FENCE);
    public static final DeferredItem<Item> MAHOGANY_FENCE_GATE     = block(ModBlocks.MAHOGANY_FENCE_GATE);
    public static final DeferredItem<Item> MAHOGANY_PRESSURE_PLATE = block(ModBlocks.MAHOGANY_PRESSURE_PLATE);
    public static final DeferredItem<Item> MAHOGANY_BUTTON         = block(ModBlocks.MAHOGANY_BUTTON);

    public static final DeferredItem<Item> MAPLE_LOG            = block(ModBlocks.MAPLE_LOG);
    public static final DeferredItem<Item> MAPLE_WOOD           = block(ModBlocks.MAPLE_WOOD);
    public static final DeferredItem<Item> MAPLE_PLANKS         = block(ModBlocks.MAPLE_PLANKS);
    public static final DeferredItem<Item> MAPLE_LEAVES         = block(ModBlocks.MAPLE_LEAVES);
    public static final DeferredItem<Item> MAPLE_STAIRS         = block(ModBlocks.MAPLE_STAIRS);
    public static final DeferredItem<Item> MAPLE_SLAB           = block(ModBlocks.MAPLE_SLAB);
    public static final DeferredItem<Item> MAPLE_FENCE          = block(ModBlocks.MAPLE_FENCE);
    public static final DeferredItem<Item> MAPLE_FENCE_GATE     = block(ModBlocks.MAPLE_FENCE_GATE);
    public static final DeferredItem<Item> MAPLE_PRESSURE_PLATE = block(ModBlocks.MAPLE_PRESSURE_PLATE);
    public static final DeferredItem<Item> MAPLE_BUTTON         = block(ModBlocks.MAPLE_BUTTON);

    public static final DeferredItem<Item> MYRRH_LOG            = block(ModBlocks.MYRRH_LOG);
    public static final DeferredItem<Item> MYRRH_WOOD           = block(ModBlocks.MYRRH_WOOD);
    public static final DeferredItem<Item> MYRRH_PLANKS         = block(ModBlocks.MYRRH_PLANKS);
    public static final DeferredItem<Item> MYRRH_LEAVES         = block(ModBlocks.MYRRH_LEAVES);
    public static final DeferredItem<Item> MYRRH_STAIRS         = block(ModBlocks.MYRRH_STAIRS);
    public static final DeferredItem<Item> MYRRH_SLAB           = block(ModBlocks.MYRRH_SLAB);
    public static final DeferredItem<Item> MYRRH_FENCE          = block(ModBlocks.MYRRH_FENCE);
    public static final DeferredItem<Item> MYRRH_FENCE_GATE     = block(ModBlocks.MYRRH_FENCE_GATE);
    public static final DeferredItem<Item> MYRRH_PRESSURE_PLATE = block(ModBlocks.MYRRH_PRESSURE_PLATE);
    public static final DeferredItem<Item> MYRRH_BUTTON         = block(ModBlocks.MYRRH_BUTTON);

    public static final DeferredItem<Item> REDWOOD_LOG            = block(ModBlocks.REDWOOD_LOG);
    public static final DeferredItem<Item> REDWOOD_WOOD           = block(ModBlocks.REDWOOD_WOOD);
    public static final DeferredItem<Item> REDWOOD_PLANKS         = block(ModBlocks.REDWOOD_PLANKS);
    public static final DeferredItem<Item> REDWOOD_LEAVES         = block(ModBlocks.REDWOOD_LEAVES);
    public static final DeferredItem<Item> REDWOOD_STAIRS         = block(ModBlocks.REDWOOD_STAIRS);
    public static final DeferredItem<Item> REDWOOD_SLAB           = block(ModBlocks.REDWOOD_SLAB);
    public static final DeferredItem<Item> REDWOOD_FENCE          = block(ModBlocks.REDWOOD_FENCE);
    public static final DeferredItem<Item> REDWOOD_FENCE_GATE     = block(ModBlocks.REDWOOD_FENCE_GATE);
    public static final DeferredItem<Item> REDWOOD_PRESSURE_PLATE = block(ModBlocks.REDWOOD_PRESSURE_PLATE);
    public static final DeferredItem<Item> REDWOOD_BUTTON         = block(ModBlocks.REDWOOD_BUTTON);

    public static final DeferredItem<Item> CHESTNUT_LOG            = block(ModBlocks.CHESTNUT_LOG);
    public static final DeferredItem<Item> CHESTNUT_WOOD           = block(ModBlocks.CHESTNUT_WOOD);
    public static final DeferredItem<Item> CHESTNUT_PLANKS         = block(ModBlocks.CHESTNUT_PLANKS);
    public static final DeferredItem<Item> CHESTNUT_LEAVES         = block(ModBlocks.CHESTNUT_LEAVES);
    public static final DeferredItem<Item> CHESTNUT_STAIRS         = block(ModBlocks.CHESTNUT_STAIRS);
    public static final DeferredItem<Item> CHESTNUT_SLAB           = block(ModBlocks.CHESTNUT_SLAB);
    public static final DeferredItem<Item> CHESTNUT_FENCE          = block(ModBlocks.CHESTNUT_FENCE);
    public static final DeferredItem<Item> CHESTNUT_FENCE_GATE     = block(ModBlocks.CHESTNUT_FENCE_GATE);
    public static final DeferredItem<Item> CHESTNUT_PRESSURE_PLATE = block(ModBlocks.CHESTNUT_PRESSURE_PLATE);
    public static final DeferredItem<Item> CHESTNUT_BUTTON         = block(ModBlocks.CHESTNUT_BUTTON);

    public static final DeferredItem<Item> WILLOW_LOG            = block(ModBlocks.WILLOW_LOG);
    public static final DeferredItem<Item> WILLOW_WOOD           = block(ModBlocks.WILLOW_WOOD);
    public static final DeferredItem<Item> WILLOW_PLANKS         = block(ModBlocks.WILLOW_PLANKS);
    public static final DeferredItem<Item> WILLOW_LEAVES         = block(ModBlocks.WILLOW_LEAVES);
    public static final DeferredItem<Item> WILLOW_STAIRS         = block(ModBlocks.WILLOW_STAIRS);
    public static final DeferredItem<Item> WILLOW_SLAB           = block(ModBlocks.WILLOW_SLAB);
    public static final DeferredItem<Item> WILLOW_FENCE          = block(ModBlocks.WILLOW_FENCE);
    public static final DeferredItem<Item> WILLOW_FENCE_GATE     = block(ModBlocks.WILLOW_FENCE_GATE);
    public static final DeferredItem<Item> WILLOW_PRESSURE_PLATE = block(ModBlocks.WILLOW_PRESSURE_PLATE);
    public static final DeferredItem<Item> WILLOW_BUTTON         = block(ModBlocks.WILLOW_BUTTON);

    public static final DeferredItem<Item> WORMTREE_LOG            = block(ModBlocks.WORMTREE_LOG);
    public static final DeferredItem<Item> WORMTREE_WOOD           = block(ModBlocks.WORMTREE_WOOD);
    public static final DeferredItem<Item> WORMTREE_PLANKS         = block(ModBlocks.WORMTREE_PLANKS);
    public static final DeferredItem<Item> WORMTREE_LEAVES         = block(ModBlocks.WORMTREE_LEAVES);
    public static final DeferredItem<Item> WORMTREE_STAIRS         = block(ModBlocks.WORMTREE_STAIRS);
    public static final DeferredItem<Item> WORMTREE_SLAB           = block(ModBlocks.WORMTREE_SLAB);
    public static final DeferredItem<Item> WORMTREE_FENCE          = block(ModBlocks.WORMTREE_FENCE);
    public static final DeferredItem<Item> WORMTREE_FENCE_GATE     = block(ModBlocks.WORMTREE_FENCE_GATE);
    public static final DeferredItem<Item> WORMTREE_PRESSURE_PLATE = block(ModBlocks.WORMTREE_PRESSURE_PLATE);
    public static final DeferredItem<Item> WORMTREE_BUTTON         = block(ModBlocks.WORMTREE_BUTTON);

    public static final DeferredItem<BlockItem> ALDER_ROOFING =
            REGISTRY.registerSimpleBlockItem("alder_roofing", ModBlocks.ALDER_ROOFING);
    public static final DeferredItem<BlockItem> ALDER_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("alder_roofing_slab", ModBlocks.ALDER_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> ALDER_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("alder_roofing_stairs", ModBlocks.ALDER_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> ALDER_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("alder_roofing_wall", ModBlocks.ALDER_ROOFING_WALL);
    public static final DeferredItem<BlockItem> APPLE_ROOFING =
            REGISTRY.registerSimpleBlockItem("apple_roofing", ModBlocks.APPLE_ROOFING);
    public static final DeferredItem<BlockItem> APPLE_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("apple_roofing_slab", ModBlocks.APPLE_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> APPLE_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("apple_roofing_stairs", ModBlocks.APPLE_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> APPLE_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("apple_roofing_wall", ModBlocks.APPLE_ROOFING_WALL);
    public static final DeferredItem<BlockItem> ASH_ROOFING =
            REGISTRY.registerSimpleBlockItem("ash_roofing", ModBlocks.ASH_ROOFING);
    public static final DeferredItem<BlockItem> ASH_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("ash_roofing_slab", ModBlocks.ASH_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> ASH_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("ash_roofing_stairs", ModBlocks.ASH_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> ASH_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("ash_roofing_wall", ModBlocks.ASH_ROOFING_WALL);
    public static final DeferredItem<BlockItem> ASPEN_ROOFING =
            REGISTRY.registerSimpleBlockItem("aspen_roofing", ModBlocks.ASPEN_ROOFING);
    public static final DeferredItem<BlockItem> ASPEN_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("aspen_roofing_slab", ModBlocks.ASPEN_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> ASPEN_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("aspen_roofing_stairs", ModBlocks.ASPEN_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> ASPEN_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("aspen_roofing_wall", ModBlocks.ASPEN_ROOFING_WALL);
    public static final DeferredItem<BlockItem> BEECH_ROOFING =
            REGISTRY.registerSimpleBlockItem("beech_roofing", ModBlocks.BEECH_ROOFING);
    public static final DeferredItem<BlockItem> BEECH_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("beech_roofing_slab", ModBlocks.BEECH_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> BEECH_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("beech_roofing_stairs", ModBlocks.BEECH_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> BEECH_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("beech_roofing_wall", ModBlocks.BEECH_ROOFING_WALL);
    public static final DeferredItem<BlockItem> BLACK_COTTONWOOD_ROOFING =
            REGISTRY.registerSimpleBlockItem("black_cottonwood_roofing", ModBlocks.BLACK_COTTONWOOD_ROOFING);
    public static final DeferredItem<BlockItem> BLACK_COTTONWOOD_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("black_cottonwood_roofing_slab", ModBlocks.BLACK_COTTONWOOD_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> BLACK_COTTONWOOD_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("black_cottonwood_roofing_stairs", ModBlocks.BLACK_COTTONWOOD_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> BLACK_COTTONWOOD_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("black_cottonwood_roofing_wall", ModBlocks.BLACK_COTTONWOOD_ROOFING_WALL);
    public static final DeferredItem<BlockItem> BLACKBARK_ROOFING =
            REGISTRY.registerSimpleBlockItem("blackbark_roofing", ModBlocks.BLACKBARK_ROOFING);
    public static final DeferredItem<BlockItem> BLACKBARK_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("blackbark_roofing_slab", ModBlocks.BLACKBARK_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> BLACKBARK_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("blackbark_roofing_stairs", ModBlocks.BLACKBARK_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> BLACKBARK_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("blackbark_roofing_wall", ModBlocks.BLACKBARK_ROOFING_WALL);
    public static final DeferredItem<BlockItem> BLOODWOOD_ROOFING =
            REGISTRY.registerSimpleBlockItem("bloodwood_roofing", ModBlocks.BLOODWOOD_ROOFING);
    public static final DeferredItem<BlockItem> BLOODWOOD_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("bloodwood_roofing_slab", ModBlocks.BLOODWOOD_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> BLOODWOOD_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("bloodwood_roofing_stairs", ModBlocks.BLOODWOOD_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> BLOODWOOD_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("bloodwood_roofing_wall", ModBlocks.BLOODWOOD_ROOFING_WALL);
    public static final DeferredItem<BlockItem> BLUE_MAHOE_ROOFING =
            REGISTRY.registerSimpleBlockItem("blue_mahoe_roofing", ModBlocks.BLUE_MAHOE_ROOFING);
    public static final DeferredItem<BlockItem> BLUE_MAHOE_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("blue_mahoe_roofing_slab", ModBlocks.BLUE_MAHOE_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> BLUE_MAHOE_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("blue_mahoe_roofing_stairs", ModBlocks.BLUE_MAHOE_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> BLUE_MAHOE_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("blue_mahoe_roofing_wall", ModBlocks.BLUE_MAHOE_ROOFING_WALL);
    public static final DeferredItem<BlockItem> CEDAR_ROOFING =
            REGISTRY.registerSimpleBlockItem("cedar_roofing", ModBlocks.CEDAR_ROOFING);
    public static final DeferredItem<BlockItem> CEDAR_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("cedar_roofing_slab", ModBlocks.CEDAR_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> CEDAR_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("cedar_roofing_stairs", ModBlocks.CEDAR_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> CEDAR_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("cedar_roofing_wall", ModBlocks.CEDAR_ROOFING_WALL);
    public static final DeferredItem<BlockItem> CHESTNUT_ROOFING =
            REGISTRY.registerSimpleBlockItem("chestnut_roofing", ModBlocks.CHESTNUT_ROOFING);
    public static final DeferredItem<BlockItem> CHESTNUT_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("chestnut_roofing_slab", ModBlocks.CHESTNUT_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> CHESTNUT_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("chestnut_roofing_stairs", ModBlocks.CHESTNUT_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> CHESTNUT_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("chestnut_roofing_wall", ModBlocks.CHESTNUT_ROOFING_WALL);
    public static final DeferredItem<BlockItem> CINNAMON_ROOFING =
            REGISTRY.registerSimpleBlockItem("cinnamon_roofing", ModBlocks.CINNAMON_ROOFING);
    public static final DeferredItem<BlockItem> CINNAMON_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("cinnamon_roofing_slab", ModBlocks.CINNAMON_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> CINNAMON_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("cinnamon_roofing_stairs", ModBlocks.CINNAMON_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> CINNAMON_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("cinnamon_roofing_wall", ModBlocks.CINNAMON_ROOFING_WALL);
    public static final DeferredItem<BlockItem> CLOVE_ROOFING =
            REGISTRY.registerSimpleBlockItem("clove_roofing", ModBlocks.CLOVE_ROOFING);
    public static final DeferredItem<BlockItem> CLOVE_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("clove_roofing_slab", ModBlocks.CLOVE_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> CLOVE_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("clove_roofing_stairs", ModBlocks.CLOVE_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> CLOVE_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("clove_roofing_wall", ModBlocks.CLOVE_ROOFING_WALL);
    public static final DeferredItem<Item> CINNAMON = REGISTRY.registerItem("cinnamon", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.4f).build()));
    public static final DeferredItem<Item> CLOVE    = REGISTRY.registerItem("clove",    p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.4f).build()));
    public static final DeferredItem<BlockItem> COTTONWOOD_ROOFING =
            REGISTRY.registerSimpleBlockItem("cottonwood_roofing", ModBlocks.COTTONWOOD_ROOFING);
    public static final DeferredItem<BlockItem> COTTONWOOD_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("cottonwood_roofing_slab", ModBlocks.COTTONWOOD_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> COTTONWOOD_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("cottonwood_roofing_stairs", ModBlocks.COTTONWOOD_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> COTTONWOOD_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("cottonwood_roofing_wall", ModBlocks.COTTONWOOD_ROOFING_WALL);
    public static final DeferredItem<BlockItem> EBONY_ROOFING =
            REGISTRY.registerSimpleBlockItem("ebony_roofing", ModBlocks.EBONY_ROOFING);
    public static final DeferredItem<BlockItem> EBONY_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("ebony_roofing_slab", ModBlocks.EBONY_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> EBONY_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("ebony_roofing_stairs", ModBlocks.EBONY_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> EBONY_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("ebony_roofing_wall", ModBlocks.EBONY_ROOFING_WALL);
    public static final DeferredItem<BlockItem> ELM_ROOFING =
            REGISTRY.registerSimpleBlockItem("elm_roofing", ModBlocks.ELM_ROOFING);
    public static final DeferredItem<BlockItem> ELM_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("elm_roofing_slab", ModBlocks.ELM_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> ELM_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("elm_roofing_stairs", ModBlocks.ELM_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> ELM_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("elm_roofing_wall", ModBlocks.ELM_ROOFING_WALL);
    public static final DeferredItem<BlockItem> FIR_ROOFING =
            REGISTRY.registerSimpleBlockItem("fir_roofing", ModBlocks.FIR_ROOFING);
    public static final DeferredItem<BlockItem> FIR_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("fir_roofing_slab", ModBlocks.FIR_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> FIR_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("fir_roofing_stairs", ModBlocks.FIR_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> FIR_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("fir_roofing_wall", ModBlocks.FIR_ROOFING_WALL);
    public static final DeferredItem<BlockItem> GOLDENHEART_ROOFING =
            REGISTRY.registerSimpleBlockItem("goldenheart_roofing", ModBlocks.GOLDENHEART_ROOFING);
    public static final DeferredItem<BlockItem> GOLDENHEART_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("goldenheart_roofing_slab", ModBlocks.GOLDENHEART_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> GOLDENHEART_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("goldenheart_roofing_stairs", ModBlocks.GOLDENHEART_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> GOLDENHEART_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("goldenheart_roofing_wall", ModBlocks.GOLDENHEART_ROOFING_WALL);
    public static final DeferredItem<BlockItem> HAWTHORN_ROOFING =
            REGISTRY.registerSimpleBlockItem("hawthorn_roofing", ModBlocks.HAWTHORN_ROOFING);
    public static final DeferredItem<BlockItem> HAWTHORN_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("hawthorn_roofing_slab", ModBlocks.HAWTHORN_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> HAWTHORN_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("hawthorn_roofing_stairs", ModBlocks.HAWTHORN_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> HAWTHORN_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("hawthorn_roofing_wall", ModBlocks.HAWTHORN_ROOFING_WALL);
    public static final DeferredItem<BlockItem> IRONWOOD_ROOFING =
            REGISTRY.registerSimpleBlockItem("ironwood_roofing", ModBlocks.IRONWOOD_ROOFING);
    public static final DeferredItem<BlockItem> IRONWOOD_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("ironwood_roofing_slab", ModBlocks.IRONWOOD_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> IRONWOOD_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("ironwood_roofing_stairs", ModBlocks.IRONWOOD_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> IRONWOOD_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("ironwood_roofing_wall", ModBlocks.IRONWOOD_ROOFING_WALL);
    public static final DeferredItem<BlockItem> LINDEN_ROOFING =
            REGISTRY.registerSimpleBlockItem("linden_roofing", ModBlocks.LINDEN_ROOFING);
    public static final DeferredItem<BlockItem> LINDEN_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("linden_roofing_slab", ModBlocks.LINDEN_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> LINDEN_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("linden_roofing_stairs", ModBlocks.LINDEN_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> LINDEN_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("linden_roofing_wall", ModBlocks.LINDEN_ROOFING_WALL);
    public static final DeferredItem<BlockItem> MAHOGANY_ROOFING =
            REGISTRY.registerSimpleBlockItem("mahogany_roofing", ModBlocks.MAHOGANY_ROOFING);
    public static final DeferredItem<BlockItem> MAHOGANY_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("mahogany_roofing_slab", ModBlocks.MAHOGANY_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> MAHOGANY_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("mahogany_roofing_stairs", ModBlocks.MAHOGANY_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> MAHOGANY_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("mahogany_roofing_wall", ModBlocks.MAHOGANY_ROOFING_WALL);
    public static final DeferredItem<BlockItem> MAPLE_ROOFING =
            REGISTRY.registerSimpleBlockItem("maple_roofing", ModBlocks.MAPLE_ROOFING);
    public static final DeferredItem<BlockItem> MAPLE_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("maple_roofing_slab", ModBlocks.MAPLE_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> MAPLE_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("maple_roofing_stairs", ModBlocks.MAPLE_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> MAPLE_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("maple_roofing_wall", ModBlocks.MAPLE_ROOFING_WALL);
    public static final DeferredItem<BlockItem> MYRRH_ROOFING =
            REGISTRY.registerSimpleBlockItem("myrrh_roofing", ModBlocks.MYRRH_ROOFING);
    public static final DeferredItem<BlockItem> MYRRH_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("myrrh_roofing_slab", ModBlocks.MYRRH_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> MYRRH_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("myrrh_roofing_stairs", ModBlocks.MYRRH_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> MYRRH_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("myrrh_roofing_wall", ModBlocks.MYRRH_ROOFING_WALL);
    public static final DeferredItem<BlockItem> PINE_ROOFING =
            REGISTRY.registerSimpleBlockItem("pine_roofing", ModBlocks.PINE_ROOFING);
    public static final DeferredItem<BlockItem> PINE_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("pine_roofing_slab", ModBlocks.PINE_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> PINE_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("pine_roofing_stairs", ModBlocks.PINE_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> PINE_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("pine_roofing_wall", ModBlocks.PINE_ROOFING_WALL);
    public static final DeferredItem<BlockItem> REDWOOD_ROOFING =
            REGISTRY.registerSimpleBlockItem("redwood_roofing", ModBlocks.REDWOOD_ROOFING);
    public static final DeferredItem<BlockItem> REDWOOD_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("redwood_roofing_slab", ModBlocks.REDWOOD_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> REDWOOD_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("redwood_roofing_stairs", ModBlocks.REDWOOD_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> REDWOOD_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("redwood_roofing_wall", ModBlocks.REDWOOD_ROOFING_WALL);
    public static final DeferredItem<BlockItem> SENTINAL_ROOFING =
            REGISTRY.registerSimpleBlockItem("sentinal_roofing", ModBlocks.SENTINAL_ROOFING);
    public static final DeferredItem<BlockItem> SENTINAL_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("sentinal_roofing_slab", ModBlocks.SENTINAL_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> SENTINAL_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("sentinal_roofing_stairs", ModBlocks.SENTINAL_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> SENTINAL_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("sentinal_roofing_wall", ModBlocks.SENTINAL_ROOFING_WALL);
    public static final DeferredItem<BlockItem> SOLDIER_PINE_ROOFING =
            REGISTRY.registerSimpleBlockItem("soldier_pine_roofing", ModBlocks.SOLDIER_PINE_ROOFING);
    public static final DeferredItem<BlockItem> SOLDIER_PINE_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("soldier_pine_roofing_slab", ModBlocks.SOLDIER_PINE_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> SOLDIER_PINE_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("soldier_pine_roofing_stairs", ModBlocks.SOLDIER_PINE_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> SOLDIER_PINE_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("soldier_pine_roofing_wall", ModBlocks.SOLDIER_PINE_ROOFING_WALL);
    public static final DeferredItem<BlockItem> WEIRWOOD_ROOFING =
            REGISTRY.registerSimpleBlockItem("weirwood_roofing", ModBlocks.WEIRWOOD_ROOFING);
    public static final DeferredItem<BlockItem> WEIRWOOD_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("weirwood_roofing_slab", ModBlocks.WEIRWOOD_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> WEIRWOOD_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("weirwood_roofing_stairs", ModBlocks.WEIRWOOD_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> WEIRWOOD_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("weirwood_roofing_wall", ModBlocks.WEIRWOOD_ROOFING_WALL);
    public static final DeferredItem<BlockItem> WILLOW_ROOFING =
            REGISTRY.registerSimpleBlockItem("willow_roofing", ModBlocks.WILLOW_ROOFING);
    public static final DeferredItem<BlockItem> WILLOW_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("willow_roofing_slab", ModBlocks.WILLOW_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> WILLOW_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("willow_roofing_stairs", ModBlocks.WILLOW_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> WILLOW_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("willow_roofing_wall", ModBlocks.WILLOW_ROOFING_WALL);
    public static final DeferredItem<BlockItem> WORMTREE_ROOFING =
            REGISTRY.registerSimpleBlockItem("wormtree_roofing", ModBlocks.WORMTREE_ROOFING);
    public static final DeferredItem<BlockItem> WORMTREE_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("wormtree_roofing_slab", ModBlocks.WORMTREE_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> WORMTREE_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("wormtree_roofing_stairs", ModBlocks.WORMTREE_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> WORMTREE_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("wormtree_roofing_wall", ModBlocks.WORMTREE_ROOFING_WALL);
    public static final DeferredItem<BlockItem> OAK_ROOFING =
            REGISTRY.registerSimpleBlockItem("oak_roofing", ModBlocks.OAK_ROOFING);
    public static final DeferredItem<BlockItem> OAK_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("oak_roofing_slab", ModBlocks.OAK_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> OAK_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("oak_roofing_stairs", ModBlocks.OAK_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> OAK_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("oak_roofing_wall", ModBlocks.OAK_ROOFING_WALL);
    public static final DeferredItem<BlockItem> SPRUCE_ROOFING =
            REGISTRY.registerSimpleBlockItem("spruce_roofing", ModBlocks.SPRUCE_ROOFING);
    public static final DeferredItem<BlockItem> SPRUCE_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("spruce_roofing_slab", ModBlocks.SPRUCE_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> SPRUCE_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("spruce_roofing_stairs", ModBlocks.SPRUCE_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> SPRUCE_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("spruce_roofing_wall", ModBlocks.SPRUCE_ROOFING_WALL);
    public static final DeferredItem<BlockItem> BIRCH_ROOFING =
            REGISTRY.registerSimpleBlockItem("birch_roofing", ModBlocks.BIRCH_ROOFING);
    public static final DeferredItem<BlockItem> BIRCH_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("birch_roofing_slab", ModBlocks.BIRCH_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> BIRCH_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("birch_roofing_stairs", ModBlocks.BIRCH_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> BIRCH_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("birch_roofing_wall", ModBlocks.BIRCH_ROOFING_WALL);
    public static final DeferredItem<BlockItem> JUNGLE_ROOFING =
            REGISTRY.registerSimpleBlockItem("jungle_roofing", ModBlocks.JUNGLE_ROOFING);
    public static final DeferredItem<BlockItem> JUNGLE_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("jungle_roofing_slab", ModBlocks.JUNGLE_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> JUNGLE_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("jungle_roofing_stairs", ModBlocks.JUNGLE_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> JUNGLE_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("jungle_roofing_wall", ModBlocks.JUNGLE_ROOFING_WALL);
    public static final DeferredItem<BlockItem> ACACIA_ROOFING =
            REGISTRY.registerSimpleBlockItem("acacia_roofing", ModBlocks.ACACIA_ROOFING);
    public static final DeferredItem<BlockItem> ACACIA_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("acacia_roofing_slab", ModBlocks.ACACIA_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> ACACIA_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("acacia_roofing_stairs", ModBlocks.ACACIA_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> ACACIA_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("acacia_roofing_wall", ModBlocks.ACACIA_ROOFING_WALL);
    public static final DeferredItem<BlockItem> DARK_OAK_ROOFING =
            REGISTRY.registerSimpleBlockItem("dark_oak_roofing", ModBlocks.DARK_OAK_ROOFING);
    public static final DeferredItem<BlockItem> DARK_OAK_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("dark_oak_roofing_slab", ModBlocks.DARK_OAK_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> DARK_OAK_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("dark_oak_roofing_stairs", ModBlocks.DARK_OAK_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> DARK_OAK_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("dark_oak_roofing_wall", ModBlocks.DARK_OAK_ROOFING_WALL);
    public static final DeferredItem<BlockItem> MANGROVE_ROOFING =
            REGISTRY.registerSimpleBlockItem("mangrove_roofing", ModBlocks.MANGROVE_ROOFING);
    public static final DeferredItem<BlockItem> MANGROVE_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("mangrove_roofing_slab", ModBlocks.MANGROVE_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> MANGROVE_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("mangrove_roofing_stairs", ModBlocks.MANGROVE_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> MANGROVE_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("mangrove_roofing_wall", ModBlocks.MANGROVE_ROOFING_WALL);
    public static final DeferredItem<BlockItem> CHERRY_ROOFING =
            REGISTRY.registerSimpleBlockItem("cherry_roofing", ModBlocks.CHERRY_ROOFING);
    public static final DeferredItem<BlockItem> CHERRY_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("cherry_roofing_slab", ModBlocks.CHERRY_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> CHERRY_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("cherry_roofing_stairs", ModBlocks.CHERRY_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> CHERRY_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("cherry_roofing_wall", ModBlocks.CHERRY_ROOFING_WALL);
    public static final DeferredItem<BlockItem> RED_CHERRY_ROOFING =
            REGISTRY.registerSimpleBlockItem("red_cherry_roofing", ModBlocks.RED_CHERRY_ROOFING);
    public static final DeferredItem<BlockItem> RED_CHERRY_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("red_cherry_roofing_slab", ModBlocks.RED_CHERRY_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> RED_CHERRY_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("red_cherry_roofing_stairs", ModBlocks.RED_CHERRY_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> RED_CHERRY_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("red_cherry_roofing_wall", ModBlocks.RED_CHERRY_ROOFING_WALL);
    public static final DeferredItem<BlockItem> BLACK_CHERRY_ROOFING =
            REGISTRY.registerSimpleBlockItem("black_cherry_roofing", ModBlocks.BLACK_CHERRY_ROOFING);
    public static final DeferredItem<BlockItem> BLACK_CHERRY_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("black_cherry_roofing_slab", ModBlocks.BLACK_CHERRY_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> BLACK_CHERRY_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("black_cherry_roofing_stairs", ModBlocks.BLACK_CHERRY_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> BLACK_CHERRY_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("black_cherry_roofing_wall", ModBlocks.BLACK_CHERRY_ROOFING_WALL);
    public static final DeferredItem<BlockItem> WHITE_CHERRY_ROOFING =
            REGISTRY.registerSimpleBlockItem("white_cherry_roofing", ModBlocks.WHITE_CHERRY_ROOFING);
    public static final DeferredItem<BlockItem> WHITE_CHERRY_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("white_cherry_roofing_slab", ModBlocks.WHITE_CHERRY_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> WHITE_CHERRY_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("white_cherry_roofing_stairs", ModBlocks.WHITE_CHERRY_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> WHITE_CHERRY_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("white_cherry_roofing_wall", ModBlocks.WHITE_CHERRY_ROOFING_WALL);
    public static final DeferredItem<BlockItem> BAMBOO_ROOFING =
            REGISTRY.registerSimpleBlockItem("bamboo_roofing", ModBlocks.BAMBOO_ROOFING);
    public static final DeferredItem<BlockItem> BAMBOO_ROOFING_SLAB =
            REGISTRY.registerSimpleBlockItem("bamboo_roofing_slab", ModBlocks.BAMBOO_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> BAMBOO_ROOFING_STAIRS =
            REGISTRY.registerSimpleBlockItem("bamboo_roofing_stairs", ModBlocks.BAMBOO_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> BAMBOO_ROOFING_WALL =
            REGISTRY.registerSimpleBlockItem("bamboo_roofing_wall", ModBlocks.BAMBOO_ROOFING_WALL);

    public static final DeferredItem<BlockItem> DAUB =
            REGISTRY.registerSimpleBlockItem("daub", ModBlocks.DAUB);
    public static final DeferredItem<BlockItem> DAUB_SLAB =
            REGISTRY.registerSimpleBlockItem("daub_slab", ModBlocks.DAUB_SLAB);
    public static final DeferredItem<BlockItem> DAUB_STAIRS =
            REGISTRY.registerSimpleBlockItem("daub_stairs", ModBlocks.DAUB_STAIRS);
    public static final DeferredItem<BlockItem> DAUB_WALL =
            REGISTRY.registerSimpleBlockItem("daub_wall", ModBlocks.DAUB_WALL);
    public static final DeferredItem<BlockItem> DAUB_VERTICAL_SLAB =
            REGISTRY.registerSimpleBlockItem("daub_vertical_slab", ModBlocks.DAUB_VERTICAL_SLAB);

    public static final DeferredItem<BlockItem> WATTLE =
            REGISTRY.registerSimpleBlockItem("wattle", ModBlocks.WATTLE);
    public static final DeferredItem<BlockItem> WATTLE_SLAB =
            REGISTRY.registerSimpleBlockItem("wattle_slab", ModBlocks.WATTLE_SLAB);
    public static final DeferredItem<BlockItem> WATTLE_STAIRS =
            REGISTRY.registerSimpleBlockItem("wattle_stairs", ModBlocks.WATTLE_STAIRS);
    public static final DeferredItem<BlockItem> WATTLE_WALL =
            REGISTRY.registerSimpleBlockItem("wattle_wall", ModBlocks.WATTLE_WALL);
    public static final DeferredItem<BlockItem> WATTLE_VERTICAL_SLAB =
            REGISTRY.registerSimpleBlockItem("wattle_vertical_slab", ModBlocks.WATTLE_VERTICAL_SLAB);
    public static final DeferredItem<BlockItem> WATTLE_FENCE =
            REGISTRY.registerSimpleBlockItem("wattle_fence", ModBlocks.WATTLE_FENCE);
    public static final DeferredItem<BlockItem> WATTLE_FENCE_GATE =
            REGISTRY.registerSimpleBlockItem("wattle_fence_gate", ModBlocks.WATTLE_FENCE_GATE);

    public static final DeferredItem<BlockItem> WATTLE_AND_DAUB =
            REGISTRY.registerSimpleBlockItem("wattle_and_daub", ModBlocks.WATTLE_AND_DAUB);
    public static final DeferredItem<BlockItem> WATTLE_AND_DAUB_SLAB =
            REGISTRY.registerSimpleBlockItem("wattle_and_daub_slab", ModBlocks.WATTLE_AND_DAUB_SLAB);
    public static final DeferredItem<BlockItem> WATTLE_AND_DAUB_STAIRS =
            REGISTRY.registerSimpleBlockItem("wattle_and_daub_stairs", ModBlocks.WATTLE_AND_DAUB_STAIRS);
    public static final DeferredItem<BlockItem> WATTLE_AND_DAUB_WALL =
            REGISTRY.registerSimpleBlockItem("wattle_and_daub_wall", ModBlocks.WATTLE_AND_DAUB_WALL);
    public static final DeferredItem<BlockItem> WATTLE_AND_DAUB_VERTICAL_SLAB =
            REGISTRY.registerSimpleBlockItem("wattle_and_daub_vertical_slab", ModBlocks.WATTLE_AND_DAUB_VERTICAL_SLAB);

    public static final DeferredItem<Item> BASALT_BRICK = block(ModBlocks.BASALT_BRICK);
    public static final DeferredItem<Item> CRACKED_BASALT_BRICK = block(ModBlocks.CRACKED_BASALT_BRICK);
    public static final DeferredItem<Item> MOSSY_BASALT_BRICK = block(ModBlocks.MOSSY_BASALT_BRICK);
    public static final DeferredItem<Item> BASALT_COBBLESTONE = block(ModBlocks.BASALT_COBBLESTONE);
    public static final DeferredItem<Item> MOSSY_BASALT_COBBLESTONE = block(ModBlocks.MOSSY_BASALT_COBBLESTONE);
    public static final DeferredItem<Item> BASALT_PILLAR = block(ModBlocks.BASALT_PILLAR);
    public static final DeferredItem<Item> BASALT_ROCK_SLAB = block(ModBlocks.BASALT_ROCK_SLAB);
    public static final DeferredItem<Item> BASALT_ROCK_STAIRS = block(ModBlocks.BASALT_ROCK_STAIRS);
    public static final DeferredItem<Item> BASALT_ROCK_WALL = block(ModBlocks.BASALT_ROCK_WALL);
    public static final DeferredItem<Item> BASALT_ROCK_BUTTON = block(ModBlocks.BASALT_ROCK_BUTTON);
    public static final DeferredItem<Item> BASALT_ROCK_PRESSURE_PLATE = block(ModBlocks.BASALT_ROCK_PRESSURE_PLATE);
    public static final DeferredItem<Item> BASALT_BRICK_SLAB = block(ModBlocks.BASALT_BRICK_SLAB);
    public static final DeferredItem<Item> BASALT_BRICK_STAIRS = block(ModBlocks.BASALT_BRICK_STAIRS);
    public static final DeferredItem<Item> BASALT_BRICK_WALL = block(ModBlocks.BASALT_BRICK_WALL);
    public static final DeferredItem<Item> CRACKED_BASALT_BRICK_SLAB = block(ModBlocks.CRACKED_BASALT_BRICK_SLAB);
    public static final DeferredItem<Item> CRACKED_BASALT_BRICK_STAIRS = block(ModBlocks.CRACKED_BASALT_BRICK_STAIRS);
    public static final DeferredItem<Item> CRACKED_BASALT_BRICK_WALL = block(ModBlocks.CRACKED_BASALT_BRICK_WALL);
    public static final DeferredItem<Item> MOSSY_BASALT_BRICK_SLAB = block(ModBlocks.MOSSY_BASALT_BRICK_SLAB);
    public static final DeferredItem<Item> MOSSY_BASALT_BRICK_STAIRS = block(ModBlocks.MOSSY_BASALT_BRICK_STAIRS);
    public static final DeferredItem<Item> MOSSY_BASALT_BRICK_WALL = block(ModBlocks.MOSSY_BASALT_BRICK_WALL);
    public static final DeferredItem<Item> BASALT_COBBLESTONE_SLAB = block(ModBlocks.BASALT_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> BASALT_COBBLESTONE_STAIRS = block(ModBlocks.BASALT_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> BASALT_COBBLESTONE_WALL = block(ModBlocks.BASALT_COBBLESTONE_WALL);
    public static final DeferredItem<Item> MOSSY_BASALT_COBBLESTONE_SLAB = block(ModBlocks.MOSSY_BASALT_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> MOSSY_BASALT_COBBLESTONE_STAIRS = block(ModBlocks.MOSSY_BASALT_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> MOSSY_BASALT_COBBLESTONE_WALL = block(ModBlocks.MOSSY_BASALT_COBBLESTONE_WALL);
    public static final DeferredItem<Item> SMOOTH_BASALT_ROCK_SLAB = block(ModBlocks.SMOOTH_BASALT_ROCK_SLAB);
    public static final DeferredItem<Item> SMOOTH_BASALT_ROCK_STAIRS = block(ModBlocks.SMOOTH_BASALT_ROCK_STAIRS);
    public static final DeferredItem<Item> SMOOTH_BASALT_ROCK_WALL = block(ModBlocks.SMOOTH_BASALT_ROCK_WALL);

    public static final DeferredItem<Item> FIELDSTONE = block(ModBlocks.FIELDSTONE);
    public static final DeferredItem<Item> FIELDSTONE_SLAB = block(ModBlocks.FIELDSTONE_SLAB);
    public static final DeferredItem<Item> FIELDSTONE_STAIRS = block(ModBlocks.FIELDSTONE_STAIRS);
    public static final DeferredItem<Item> FIELDSTONE_WALL = block(ModBlocks.FIELDSTONE_WALL);
    public static final DeferredItem<Item> FIELDSTONE_BUTTON = block(ModBlocks.FIELDSTONE_BUTTON);
    public static final DeferredItem<Item> FIELDSTONE_PRESSURE_PLATE = block(ModBlocks.FIELDSTONE_PRESSURE_PLATE);

    public static final DeferredItem<Item> GRANITE_ROCK = block(ModBlocks.GREY_GRANITE_ROCK);
    public static final DeferredItem<Item> GRANITE_BRICK = block(ModBlocks.GREY_GRANITE_BRICK);
    public static final DeferredItem<Item> CRACKED_GRANITE_BRICK = block(ModBlocks.CRACKED_GREY_GRANITE_BRICK);
    public static final DeferredItem<Item> MOSSY_GRANITE_BRICK = block(ModBlocks.MOSSY_GREY_GRANITE_BRICK);
    public static final DeferredItem<Item> GRANITE_COBBLESTONE = block(ModBlocks.GREY_GRANITE_COBBLESTONE);
    public static final DeferredItem<Item> MOSSY_GRANITE_COBBLESTONE = block(ModBlocks.MOSSY_GREY_GRANITE_COBBLESTONE);
    public static final DeferredItem<Item> SMOOTH_GRANITE_ROCK = block(ModBlocks.SMOOTH_GREY_GRANITE_ROCK);
    public static final DeferredItem<Item> GRANITE_PILLAR = block(ModBlocks.GREY_GRANITE_PILLAR);
    public static final DeferredItem<Item> GRANITE_ROCK_SLAB = block(ModBlocks.GREY_GRANITE_ROCK_SLAB);
    public static final DeferredItem<Item> GRANITE_ROCK_STAIRS = block(ModBlocks.GREY_GRANITE_ROCK_STAIRS);
    public static final DeferredItem<Item> GRANITE_ROCK_WALL = block(ModBlocks.GREY_GRANITE_ROCK_WALL);
    public static final DeferredItem<Item> GRANITE_ROCK_BUTTON = block(ModBlocks.GREY_GRANITE_ROCK_BUTTON);
    public static final DeferredItem<Item> GRANITE_ROCK_PRESSURE_PLATE = block(ModBlocks.GREY_GRANITE_ROCK_PRESSURE_PLATE);
    public static final DeferredItem<Item> GRANITE_BRICK_SLAB = block(ModBlocks.GREY_GRANITE_BRICK_SLAB);
    public static final DeferredItem<Item> GRANITE_BRICK_STAIRS = block(ModBlocks.GREY_GRANITE_BRICK_STAIRS);
    public static final DeferredItem<Item> GRANITE_BRICK_WALL = block(ModBlocks.GREY_GRANITE_BRICK_WALL);
    public static final DeferredItem<Item> CRACKED_GRANITE_BRICK_SLAB = block(ModBlocks.CRACKED_GREY_GRANITE_BRICK_SLAB);
    public static final DeferredItem<Item> CRACKED_GRANITE_BRICK_STAIRS = block(ModBlocks.CRACKED_GREY_GRANITE_BRICK_STAIRS);
    public static final DeferredItem<Item> CRACKED_GRANITE_BRICK_WALL = block(ModBlocks.CRACKED_GREY_GRANITE_BRICK_WALL);
    public static final DeferredItem<Item> MOSSY_GRANITE_BRICK_SLAB = block(ModBlocks.MOSSY_GREY_GRANITE_BRICK_SLAB);
    public static final DeferredItem<Item> MOSSY_GRANITE_BRICK_STAIRS = block(ModBlocks.MOSSY_GREY_GRANITE_BRICK_STAIRS);
    public static final DeferredItem<Item> MOSSY_GRANITE_BRICK_WALL = block(ModBlocks.MOSSY_GREY_GRANITE_BRICK_WALL);
    public static final DeferredItem<Item> GRANITE_COBBLESTONE_SLAB = block(ModBlocks.GREY_GRANITE_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> GRANITE_COBBLESTONE_STAIRS = block(ModBlocks.GREY_GRANITE_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> GRANITE_COBBLESTONE_WALL = block(ModBlocks.GREY_GRANITE_COBBLESTONE_WALL);
    public static final DeferredItem<Item> MOSSY_GRANITE_COBBLESTONE_SLAB = block(ModBlocks.MOSSY_GREY_GRANITE_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> MOSSY_GRANITE_COBBLESTONE_STAIRS = block(ModBlocks.MOSSY_GREY_GRANITE_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> MOSSY_GRANITE_COBBLESTONE_WALL = block(ModBlocks.MOSSY_GREY_GRANITE_COBBLESTONE_WALL);
    public static final DeferredItem<Item> SMOOTH_GREY_GRANITE_ROCK_SLAB = block(ModBlocks.SMOOTH_GREY_GRANITE_ROCK_SLAB);
    public static final DeferredItem<Item> SMOOTH_GREY_GRANITE_ROCK_STAIRS = block(ModBlocks.SMOOTH_GREY_GRANITE_ROCK_STAIRS);
    public static final DeferredItem<Item> SMOOTH_GREY_GRANITE_ROCK_WALL = block(ModBlocks.SMOOTH_GREY_GRANITE_ROCK_WALL);

    public static final DeferredItem<Item> FLINT_ROCK = block(ModBlocks.FLINT_ROCK);
    public static final DeferredItem<Item> FLINT_BRICK = block(ModBlocks.FLINT_BRICK);
    public static final DeferredItem<Item> CRACKED_FLINT_BRICK = block(ModBlocks.CRACKED_FLINT_BRICK);
    public static final DeferredItem<Item> MOSSY_FLINT_BRICK = block(ModBlocks.MOSSY_FLINT_BRICK);
    public static final DeferredItem<Item> FLINT_ROCK_SLAB = block(ModBlocks.FLINT_ROCK_SLAB);
    public static final DeferredItem<Item> FLINT_ROCK_STAIRS = block(ModBlocks.FLINT_ROCK_STAIRS);
    public static final DeferredItem<Item> FLINT_ROCK_WALL = block(ModBlocks.FLINT_ROCK_WALL);
    public static final DeferredItem<Item> FLINT_ROCK_BUTTON = block(ModBlocks.FLINT_ROCK_BUTTON);
    public static final DeferredItem<Item> FLINT_ROCK_PRESSURE_PLATE = block(ModBlocks.FLINT_ROCK_PRESSURE_PLATE);
    public static final DeferredItem<Item> FLINT_BRICK_SLAB = block(ModBlocks.FLINT_BRICK_SLAB);
    public static final DeferredItem<Item> FLINT_BRICK_STAIRS = block(ModBlocks.FLINT_BRICK_STAIRS);
    public static final DeferredItem<Item> FLINT_BRICK_WALL = block(ModBlocks.FLINT_BRICK_WALL);
    public static final DeferredItem<Item> CRACKED_FLINT_BRICK_SLAB = block(ModBlocks.CRACKED_FLINT_BRICK_SLAB);
    public static final DeferredItem<Item> CRACKED_FLINT_BRICK_STAIRS = block(ModBlocks.CRACKED_FLINT_BRICK_STAIRS);
    public static final DeferredItem<Item> CRACKED_FLINT_BRICK_WALL = block(ModBlocks.CRACKED_FLINT_BRICK_WALL);
    public static final DeferredItem<Item> MOSSY_FLINT_BRICK_SLAB = block(ModBlocks.MOSSY_FLINT_BRICK_SLAB);
    public static final DeferredItem<Item> MOSSY_FLINT_BRICK_STAIRS = block(ModBlocks.MOSSY_FLINT_BRICK_STAIRS);
    public static final DeferredItem<Item> MOSSY_FLINT_BRICK_WALL = block(ModBlocks.MOSSY_FLINT_BRICK_WALL);

    public static final DeferredItem<Item> LIMESTONE_ROCK = block(ModBlocks.LIMESTONE_ROCK);
    public static final DeferredItem<Item> LIMESTONE_BRICK = block(ModBlocks.LIMESTONE_BRICK);
    public static final DeferredItem<Item> CRACKED_LIMESTONE_BRICK = block(ModBlocks.CRACKED_LIMESTONE_BRICK);
    public static final DeferredItem<Item> MOSSY_LIMESTONE_BRICK = block(ModBlocks.MOSSY_LIMESTONE_BRICK);
    public static final DeferredItem<Item> LIMESTONE_COBBLESTONE = block(ModBlocks.LIMESTONE_COBBLESTONE);
    public static final DeferredItem<Item> MOSSY_LIMESTONE_COBBLESTONE = block(ModBlocks.MOSSY_LIMESTONE_COBBLESTONE);
    public static final DeferredItem<Item> SMOOTH_LIMESTONE_ROCK = block(ModBlocks.SMOOTH_LIMESTONE_ROCK);
    public static final DeferredItem<Item> LIMESTONE_PILLAR = block(ModBlocks.LIMESTONE_PILLAR);
    public static final DeferredItem<Item> LIMESTONE_ROCK_SLAB = block(ModBlocks.LIMESTONE_ROCK_SLAB);
    public static final DeferredItem<Item> LIMESTONE_ROCK_STAIRS = block(ModBlocks.LIMESTONE_ROCK_STAIRS);
    public static final DeferredItem<Item> LIMESTONE_ROCK_WALL = block(ModBlocks.LIMESTONE_ROCK_WALL);
    public static final DeferredItem<Item> LIMESTONE_ROCK_BUTTON = block(ModBlocks.LIMESTONE_ROCK_BUTTON);
    public static final DeferredItem<Item> LIMESTONE_ROCK_PRESSURE_PLATE = block(ModBlocks.LIMESTONE_ROCK_PRESSURE_PLATE);
    public static final DeferredItem<Item> LIMESTONE_BRICK_SLAB = block(ModBlocks.LIMESTONE_BRICK_SLAB);
    public static final DeferredItem<Item> LIMESTONE_BRICK_STAIRS = block(ModBlocks.LIMESTONE_BRICK_STAIRS);
    public static final DeferredItem<Item> LIMESTONE_BRICK_WALL = block(ModBlocks.LIMESTONE_BRICK_WALL);
    public static final DeferredItem<Item> CRACKED_LIMESTONE_BRICK_SLAB = block(ModBlocks.CRACKED_LIMESTONE_BRICK_SLAB);
    public static final DeferredItem<Item> CRACKED_LIMESTONE_BRICK_STAIRS = block(ModBlocks.CRACKED_LIMESTONE_BRICK_STAIRS);
    public static final DeferredItem<Item> CRACKED_LIMESTONE_BRICK_WALL = block(ModBlocks.CRACKED_LIMESTONE_BRICK_WALL);
    public static final DeferredItem<Item> MOSSY_LIMESTONE_BRICK_SLAB = block(ModBlocks.MOSSY_LIMESTONE_BRICK_SLAB);
    public static final DeferredItem<Item> MOSSY_LIMESTONE_BRICK_STAIRS = block(ModBlocks.MOSSY_LIMESTONE_BRICK_STAIRS);
    public static final DeferredItem<Item> MOSSY_LIMESTONE_BRICK_WALL = block(ModBlocks.MOSSY_LIMESTONE_BRICK_WALL);
    public static final DeferredItem<Item> LIMESTONE_COBBLESTONE_SLAB = block(ModBlocks.LIMESTONE_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> LIMESTONE_COBBLESTONE_STAIRS = block(ModBlocks.LIMESTONE_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> LIMESTONE_COBBLESTONE_WALL = block(ModBlocks.LIMESTONE_COBBLESTONE_WALL);
    public static final DeferredItem<Item> MOSSY_LIMESTONE_COBBLESTONE_SLAB = block(ModBlocks.MOSSY_LIMESTONE_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> MOSSY_LIMESTONE_COBBLESTONE_STAIRS = block(ModBlocks.MOSSY_LIMESTONE_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> MOSSY_LIMESTONE_COBBLESTONE_WALL = block(ModBlocks.MOSSY_LIMESTONE_COBBLESTONE_WALL);
    public static final DeferredItem<Item> SMOOTH_LIMESTONE_ROCK_SLAB = block(ModBlocks.SMOOTH_LIMESTONE_ROCK_SLAB);
    public static final DeferredItem<Item> SMOOTH_LIMESTONE_ROCK_STAIRS = block(ModBlocks.SMOOTH_LIMESTONE_ROCK_STAIRS);
    public static final DeferredItem<Item> SMOOTH_LIMESTONE_ROCK_WALL = block(ModBlocks.SMOOTH_LIMESTONE_ROCK_WALL);

    public static final DeferredItem<Item> SANDSTONE_BRICK = block(ModBlocks.SANDSTONE_BRICK);
    public static final DeferredItem<Item> CRACKED_SANDSTONE_BRICK = block(ModBlocks.CRACKED_SANDSTONE_BRICK);
    public static final DeferredItem<Item> MOSSY_SANDSTONE_BRICK = block(ModBlocks.MOSSY_SANDSTONE_BRICK);
    public static final DeferredItem<Item> SANDSTONE_COBBLESTONE = block(ModBlocks.SANDSTONE_COBBLESTONE);
    public static final DeferredItem<Item> MOSSY_SANDSTONE_COBBLESTONE = block(ModBlocks.MOSSY_SANDSTONE_COBBLESTONE);
    public static final DeferredItem<Item> SANDSTONE_PILLAR = block(ModBlocks.SANDSTONE_PILLAR);
    public static final DeferredItem<Item> SANDSTONE_ROCK_BUTTON = block(ModBlocks.SANDSTONE_ROCK_BUTTON);
    public static final DeferredItem<Item> SANDSTONE_ROCK_PRESSURE_PLATE = block(ModBlocks.SANDSTONE_ROCK_PRESSURE_PLATE);
    public static final DeferredItem<Item> SANDSTONE_BRICK_SLAB = block(ModBlocks.SANDSTONE_BRICK_SLAB);
    public static final DeferredItem<Item> SANDSTONE_BRICK_STAIRS = block(ModBlocks.SANDSTONE_BRICK_STAIRS);
    public static final DeferredItem<Item> SANDSTONE_BRICK_WALL = block(ModBlocks.SANDSTONE_BRICK_WALL);
    public static final DeferredItem<Item> CRACKED_SANDSTONE_BRICK_SLAB = block(ModBlocks.CRACKED_SANDSTONE_BRICK_SLAB);
    public static final DeferredItem<Item> CRACKED_SANDSTONE_BRICK_STAIRS = block(ModBlocks.CRACKED_SANDSTONE_BRICK_STAIRS);
    public static final DeferredItem<Item> CRACKED_SANDSTONE_BRICK_WALL = block(ModBlocks.CRACKED_SANDSTONE_BRICK_WALL);
    public static final DeferredItem<Item> MOSSY_SANDSTONE_BRICK_SLAB = block(ModBlocks.MOSSY_SANDSTONE_BRICK_SLAB);
    public static final DeferredItem<Item> MOSSY_SANDSTONE_BRICK_STAIRS = block(ModBlocks.MOSSY_SANDSTONE_BRICK_STAIRS);
    public static final DeferredItem<Item> MOSSY_SANDSTONE_BRICK_WALL = block(ModBlocks.MOSSY_SANDSTONE_BRICK_WALL);
    public static final DeferredItem<Item> SANDSTONE_COBBLESTONE_SLAB = block(ModBlocks.SANDSTONE_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> SANDSTONE_COBBLESTONE_STAIRS = block(ModBlocks.SANDSTONE_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> SANDSTONE_COBBLESTONE_WALL = block(ModBlocks.SANDSTONE_COBBLESTONE_WALL);
    public static final DeferredItem<Item> MOSSY_SANDSTONE_COBBLESTONE_SLAB = block(ModBlocks.MOSSY_SANDSTONE_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> MOSSY_SANDSTONE_COBBLESTONE_STAIRS = block(ModBlocks.MOSSY_SANDSTONE_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> MOSSY_SANDSTONE_COBBLESTONE_WALL = block(ModBlocks.MOSSY_SANDSTONE_COBBLESTONE_WALL);
    public static final DeferredItem<Item> SMOOTH_SANDSTONE_ROCK_WALL = block(ModBlocks.SMOOTH_SANDSTONE_ROCK_WALL);

    public static final DeferredItem<Item> RED_SANDSTONE_BRICK = block(ModBlocks.RED_SANDSTONE_BRICK);
    public static final DeferredItem<Item> CRACKED_RED_SANDSTONE_BRICK = block(ModBlocks.CRACKED_RED_SANDSTONE_BRICK);
    public static final DeferredItem<Item> MOSSY_RED_SANDSTONE_BRICK = block(ModBlocks.MOSSY_RED_SANDSTONE_BRICK);
    public static final DeferredItem<Item> RED_SANDSTONE_COBBLESTONE = block(ModBlocks.RED_SANDSTONE_COBBLESTONE);
    public static final DeferredItem<Item> MOSSY_RED_SANDSTONE_COBBLESTONE = block(ModBlocks.MOSSY_RED_SANDSTONE_COBBLESTONE);
    public static final DeferredItem<Item> RED_SANDSTONE_PILLAR = block(ModBlocks.RED_SANDSTONE_PILLAR);
    public static final DeferredItem<Item> RED_SANDSTONE_ROCK_BUTTON = block(ModBlocks.RED_SANDSTONE_ROCK_BUTTON);
    public static final DeferredItem<Item> RED_SANDSTONE_ROCK_PRESSURE_PLATE = block(ModBlocks.RED_SANDSTONE_ROCK_PRESSURE_PLATE);
    public static final DeferredItem<Item> RED_SANDSTONE_BRICK_SLAB = block(ModBlocks.RED_SANDSTONE_BRICK_SLAB);
    public static final DeferredItem<Item> RED_SANDSTONE_BRICK_STAIRS = block(ModBlocks.RED_SANDSTONE_BRICK_STAIRS);
    public static final DeferredItem<Item> RED_SANDSTONE_BRICK_WALL = block(ModBlocks.RED_SANDSTONE_BRICK_WALL);
    public static final DeferredItem<Item> CRACKED_RED_SANDSTONE_BRICK_SLAB = block(ModBlocks.CRACKED_RED_SANDSTONE_BRICK_SLAB);
    public static final DeferredItem<Item> CRACKED_RED_SANDSTONE_BRICK_STAIRS = block(ModBlocks.CRACKED_RED_SANDSTONE_BRICK_STAIRS);
    public static final DeferredItem<Item> CRACKED_RED_SANDSTONE_BRICK_WALL = block(ModBlocks.CRACKED_RED_SANDSTONE_BRICK_WALL);
    public static final DeferredItem<Item> MOSSY_RED_SANDSTONE_BRICK_SLAB = block(ModBlocks.MOSSY_RED_SANDSTONE_BRICK_SLAB);
    public static final DeferredItem<Item> MOSSY_RED_SANDSTONE_BRICK_STAIRS = block(ModBlocks.MOSSY_RED_SANDSTONE_BRICK_STAIRS);
    public static final DeferredItem<Item> MOSSY_RED_SANDSTONE_BRICK_WALL = block(ModBlocks.MOSSY_RED_SANDSTONE_BRICK_WALL);
    public static final DeferredItem<Item> RED_SANDSTONE_COBBLESTONE_SLAB = block(ModBlocks.RED_SANDSTONE_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> RED_SANDSTONE_COBBLESTONE_STAIRS = block(ModBlocks.RED_SANDSTONE_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> RED_SANDSTONE_COBBLESTONE_WALL = block(ModBlocks.RED_SANDSTONE_COBBLESTONE_WALL);
    public static final DeferredItem<Item> MOSSY_RED_SANDSTONE_COBBLESTONE_SLAB = block(ModBlocks.MOSSY_RED_SANDSTONE_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> MOSSY_RED_SANDSTONE_COBBLESTONE_STAIRS = block(ModBlocks.MOSSY_RED_SANDSTONE_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> MOSSY_RED_SANDSTONE_COBBLESTONE_WALL = block(ModBlocks.MOSSY_RED_SANDSTONE_COBBLESTONE_WALL);
    public static final DeferredItem<Item> SMOOTH_RED_SANDSTONE_ROCK_WALL = block(ModBlocks.SMOOTH_RED_SANDSTONE_ROCK_WALL);

    public static final DeferredItem<Item> SLATE_ROCK = block(ModBlocks.SLATE_ROCK);
    public static final DeferredItem<Item> SLATE_BRICK = block(ModBlocks.SLATE_BRICK);
    public static final DeferredItem<Item> CRACKED_SLATE_BRICK = block(ModBlocks.CRACKED_SLATE_BRICK);
    public static final DeferredItem<Item> MOSSY_SLATE_BRICK = block(ModBlocks.MOSSY_SLATE_BRICK);
    public static final DeferredItem<Item> SLATE_COBBLESTONE = block(ModBlocks.SLATE_COBBLESTONE);
    public static final DeferredItem<Item> MOSSY_SLATE_COBBLESTONE = block(ModBlocks.MOSSY_SLATE_COBBLESTONE);
    public static final DeferredItem<Item> SMOOTH_SLATE_ROCK = block(ModBlocks.SMOOTH_SLATE_ROCK);
    public static final DeferredItem<Item> SLATE_PILLAR = block(ModBlocks.SLATE_PILLAR);
    public static final DeferredItem<Item> SLATE_ROCK_SLAB = block(ModBlocks.SLATE_ROCK_SLAB);
    public static final DeferredItem<Item> SLATE_ROCK_STAIRS = block(ModBlocks.SLATE_ROCK_STAIRS);
    public static final DeferredItem<Item> SLATE_ROCK_WALL = block(ModBlocks.SLATE_ROCK_WALL);
    public static final DeferredItem<Item> SLATE_ROCK_BUTTON = block(ModBlocks.SLATE_ROCK_BUTTON);
    public static final DeferredItem<Item> SLATE_ROCK_PRESSURE_PLATE = block(ModBlocks.SLATE_ROCK_PRESSURE_PLATE);
    public static final DeferredItem<Item> SLATE_BRICK_SLAB = block(ModBlocks.SLATE_BRICK_SLAB);
    public static final DeferredItem<Item> SLATE_BRICK_STAIRS = block(ModBlocks.SLATE_BRICK_STAIRS);
    public static final DeferredItem<Item> SLATE_BRICK_WALL = block(ModBlocks.SLATE_BRICK_WALL);
    public static final DeferredItem<Item> CRACKED_SLATE_BRICK_SLAB = block(ModBlocks.CRACKED_SLATE_BRICK_SLAB);
    public static final DeferredItem<Item> CRACKED_SLATE_BRICK_STAIRS = block(ModBlocks.CRACKED_SLATE_BRICK_STAIRS);
    public static final DeferredItem<Item> CRACKED_SLATE_BRICK_WALL = block(ModBlocks.CRACKED_SLATE_BRICK_WALL);
    public static final DeferredItem<Item> MOSSY_SLATE_BRICK_SLAB = block(ModBlocks.MOSSY_SLATE_BRICK_SLAB);
    public static final DeferredItem<Item> MOSSY_SLATE_BRICK_STAIRS = block(ModBlocks.MOSSY_SLATE_BRICK_STAIRS);
    public static final DeferredItem<Item> MOSSY_SLATE_BRICK_WALL = block(ModBlocks.MOSSY_SLATE_BRICK_WALL);
    public static final DeferredItem<Item> SLATE_COBBLESTONE_SLAB = block(ModBlocks.SLATE_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> SLATE_COBBLESTONE_STAIRS = block(ModBlocks.SLATE_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> SLATE_COBBLESTONE_WALL = block(ModBlocks.SLATE_COBBLESTONE_WALL);
    public static final DeferredItem<Item> MOSSY_SLATE_COBBLESTONE_SLAB = block(ModBlocks.MOSSY_SLATE_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> MOSSY_SLATE_COBBLESTONE_STAIRS = block(ModBlocks.MOSSY_SLATE_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> MOSSY_SLATE_COBBLESTONE_WALL = block(ModBlocks.MOSSY_SLATE_COBBLESTONE_WALL);
    public static final DeferredItem<Item> SMOOTH_SLATE_ROCK_SLAB = block(ModBlocks.SMOOTH_SLATE_ROCK_SLAB);
    public static final DeferredItem<Item> SMOOTH_SLATE_ROCK_STAIRS = block(ModBlocks.SMOOTH_SLATE_ROCK_STAIRS);
    public static final DeferredItem<Item> SMOOTH_SLATE_ROCK_WALL = block(ModBlocks.SMOOTH_SLATE_ROCK_WALL);
    public static final DeferredItem<Item> SLATE_SHINGLES = block(ModBlocks.SLATE_SHINGLES);
    public static final DeferredItem<Item> SLATE_SHINGLES_STAIRS = block(ModBlocks.SLATE_SHINGLES_STAIRS);
    public static final DeferredItem<Item> SLATE_SHINGLES_WALL = block(ModBlocks.SLATE_SHINGLES_WALL);
    public static final DeferredItem<Item> SLATE_SHINGLES_SLAB = block(ModBlocks.SLATE_SHINGLES_SLAB);

    public static final DeferredItem<Item> OILY_BLACK_ROCK = block(ModBlocks.OILY_BLACK_ROCK);
    public static final DeferredItem<Item> OILY_BLACK_BRICK = block(ModBlocks.OILY_BLACK_BRICK);
    public static final DeferredItem<Item> CRACKED_OILY_BLACK_BRICK = block(ModBlocks.CRACKED_OILY_BLACK_BRICK);
    public static final DeferredItem<Item> MOSSY_OILY_BLACK_BRICK = block(ModBlocks.MOSSY_OILY_BLACK_BRICK);
    public static final DeferredItem<Item> OILY_BLACK_COBBLESTONE = block(ModBlocks.OILY_BLACK_COBBLESTONE);
    public static final DeferredItem<Item> MOSSY_OILY_BLACK_COBBLESTONE = block(ModBlocks.MOSSY_OILY_BLACK_COBBLESTONE);
    public static final DeferredItem<Item> SMOOTH_OILY_BLACK_ROCK = block(ModBlocks.SMOOTH_OILY_BLACK_ROCK);
    public static final DeferredItem<Item> OILY_BLACK_PILLAR = block(ModBlocks.OILY_BLACK_PILLAR);
    public static final DeferredItem<Item> OILY_BLACK_ROCK_SLAB = block(ModBlocks.OILY_BLACK_ROCK_SLAB);
    public static final DeferredItem<Item> OILY_BLACK_ROCK_STAIRS = block(ModBlocks.OILY_BLACK_ROCK_STAIRS);
    public static final DeferredItem<Item> OILY_BLACK_ROCK_WALL = block(ModBlocks.OILY_BLACK_ROCK_WALL);
    public static final DeferredItem<Item> OILY_BLACK_ROCK_BUTTON = block(ModBlocks.OILY_BLACK_ROCK_BUTTON);
    public static final DeferredItem<Item> OILY_BLACK_ROCK_PRESSURE_PLATE = block(ModBlocks.OILY_BLACK_ROCK_PRESSURE_PLATE);
    public static final DeferredItem<Item> OILY_BLACK_BRICK_SLAB = block(ModBlocks.OILY_BLACK_BRICK_SLAB);
    public static final DeferredItem<Item> OILY_BLACK_BRICK_STAIRS = block(ModBlocks.OILY_BLACK_BRICK_STAIRS);
    public static final DeferredItem<Item> OILY_BLACK_BRICK_WALL = block(ModBlocks.OILY_BLACK_BRICK_WALL);
    public static final DeferredItem<Item> CRACKED_OILY_BLACK_BRICK_SLAB = block(ModBlocks.CRACKED_OILY_BLACK_BRICK_SLAB);
    public static final DeferredItem<Item> CRACKED_OILY_BLACK_BRICK_STAIRS = block(ModBlocks.CRACKED_OILY_BLACK_BRICK_STAIRS);
    public static final DeferredItem<Item> CRACKED_OILY_BLACK_BRICK_WALL = block(ModBlocks.CRACKED_OILY_BLACK_BRICK_WALL);
    public static final DeferredItem<Item> MOSSY_OILY_BLACK_BRICK_SLAB = block(ModBlocks.MOSSY_OILY_BLACK_BRICK_SLAB);
    public static final DeferredItem<Item> MOSSY_OILY_BLACK_BRICK_STAIRS = block(ModBlocks.MOSSY_OILY_BLACK_BRICK_STAIRS);
    public static final DeferredItem<Item> MOSSY_OILY_BLACK_BRICK_WALL = block(ModBlocks.MOSSY_OILY_BLACK_BRICK_WALL);
    public static final DeferredItem<Item> OILY_BLACK_COBBLESTONE_SLAB = block(ModBlocks.OILY_BLACK_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> OILY_BLACK_COBBLESTONE_STAIRS = block(ModBlocks.OILY_BLACK_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> OILY_BLACK_COBBLESTONE_WALL = block(ModBlocks.OILY_BLACK_COBBLESTONE_WALL);
    public static final DeferredItem<Item> MOSSY_OILY_BLACK_COBBLESTONE_SLAB = block(ModBlocks.MOSSY_OILY_BLACK_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> MOSSY_OILY_BLACK_COBBLESTONE_STAIRS = block(ModBlocks.MOSSY_OILY_BLACK_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> MOSSY_OILY_BLACK_COBBLESTONE_WALL = block(ModBlocks.MOSSY_OILY_BLACK_COBBLESTONE_WALL);
    public static final DeferredItem<Item> SMOOTH_OILY_BLACK_ROCK_SLAB = block(ModBlocks.SMOOTH_OILY_BLACK_ROCK_SLAB);
    public static final DeferredItem<Item> SMOOTH_OILY_BLACK_ROCK_STAIRS = block(ModBlocks.SMOOTH_OILY_BLACK_ROCK_STAIRS);
    public static final DeferredItem<Item> SMOOTH_OILY_BLACK_ROCK_WALL = block(ModBlocks.SMOOTH_OILY_BLACK_ROCK_WALL);

    public static final DeferredItem<Item> FUSED_BLACK_ROCK = block(ModBlocks.FUSED_BLACK_ROCK);
    public static final DeferredItem<Item> FUSED_BLACK_BRICK = block(ModBlocks.FUSED_BLACK_BRICK);
    public static final DeferredItem<Item> CRACKED_FUSED_BLACK_BRICK = block(ModBlocks.CRACKED_FUSED_BLACK_BRICK);
    public static final DeferredItem<Item> MOSSY_FUSED_BLACK_BRICK = block(ModBlocks.MOSSY_FUSED_BLACK_BRICK);
    public static final DeferredItem<Item> FUSED_BLACK_COBBLESTONE = block(ModBlocks.FUSED_BLACK_COBBLESTONE);
    public static final DeferredItem<Item> MOSSY_FUSED_BLACK_COBBLESTONE = block(ModBlocks.MOSSY_FUSED_BLACK_COBBLESTONE);
    public static final DeferredItem<Item> SMOOTH_FUSED_BLACK_ROCK = block(ModBlocks.SMOOTH_FUSED_BLACK_ROCK);
    public static final DeferredItem<Item> FUSED_BLACK_PILLAR = block(ModBlocks.FUSED_BLACK_PILLAR);
    public static final DeferredItem<Item> FUSED_BLACK_ROCK_SLAB = block(ModBlocks.FUSED_BLACK_ROCK_SLAB);
    public static final DeferredItem<Item> FUSED_BLACK_ROCK_STAIRS = block(ModBlocks.FUSED_BLACK_ROCK_STAIRS);
    public static final DeferredItem<Item> FUSED_BLACK_ROCK_WALL = block(ModBlocks.FUSED_BLACK_ROCK_WALL);
    public static final DeferredItem<Item> FUSED_BLACK_ROCK_BUTTON = block(ModBlocks.FUSED_BLACK_ROCK_BUTTON);
    public static final DeferredItem<Item> FUSED_BLACK_ROCK_PRESSURE_PLATE = block(ModBlocks.FUSED_BLACK_ROCK_PRESSURE_PLATE);
    public static final DeferredItem<Item> FUSED_BLACK_BRICK_SLAB = block(ModBlocks.FUSED_BLACK_BRICK_SLAB);
    public static final DeferredItem<Item> FUSED_BLACK_BRICK_STAIRS = block(ModBlocks.FUSED_BLACK_BRICK_STAIRS);
    public static final DeferredItem<Item> FUSED_BLACK_BRICK_WALL = block(ModBlocks.FUSED_BLACK_BRICK_WALL);
    public static final DeferredItem<Item> CRACKED_FUSED_BLACK_BRICK_SLAB = block(ModBlocks.CRACKED_FUSED_BLACK_BRICK_SLAB);
    public static final DeferredItem<Item> CRACKED_FUSED_BLACK_BRICK_STAIRS = block(ModBlocks.CRACKED_FUSED_BLACK_BRICK_STAIRS);
    public static final DeferredItem<Item> CRACKED_FUSED_BLACK_BRICK_WALL = block(ModBlocks.CRACKED_FUSED_BLACK_BRICK_WALL);
    public static final DeferredItem<Item> MOSSY_FUSED_BLACK_BRICK_SLAB = block(ModBlocks.MOSSY_FUSED_BLACK_BRICK_SLAB);
    public static final DeferredItem<Item> MOSSY_FUSED_BLACK_BRICK_STAIRS = block(ModBlocks.MOSSY_FUSED_BLACK_BRICK_STAIRS);
    public static final DeferredItem<Item> MOSSY_FUSED_BLACK_BRICK_WALL = block(ModBlocks.MOSSY_FUSED_BLACK_BRICK_WALL);
    public static final DeferredItem<Item> FUSED_BLACK_COBBLESTONE_SLAB = block(ModBlocks.FUSED_BLACK_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> FUSED_BLACK_COBBLESTONE_STAIRS = block(ModBlocks.FUSED_BLACK_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> FUSED_BLACK_COBBLESTONE_WALL = block(ModBlocks.FUSED_BLACK_COBBLESTONE_WALL);
    public static final DeferredItem<Item> MOSSY_FUSED_BLACK_COBBLESTONE_SLAB = block(ModBlocks.MOSSY_FUSED_BLACK_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> MOSSY_FUSED_BLACK_COBBLESTONE_STAIRS = block(ModBlocks.MOSSY_FUSED_BLACK_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> MOSSY_FUSED_BLACK_COBBLESTONE_WALL = block(ModBlocks.MOSSY_FUSED_BLACK_COBBLESTONE_WALL);
    public static final DeferredItem<Item> SMOOTH_FUSED_BLACK_ROCK_SLAB = block(ModBlocks.SMOOTH_FUSED_BLACK_ROCK_SLAB);
    public static final DeferredItem<Item> SMOOTH_FUSED_BLACK_ROCK_STAIRS = block(ModBlocks.SMOOTH_FUSED_BLACK_ROCK_STAIRS);
    public static final DeferredItem<Item> SMOOTH_FUSED_BLACK_ROCK_WALL = block(ModBlocks.SMOOTH_FUSED_BLACK_ROCK_WALL);

    public static final DeferredItem<Item> MARBLE_ROCK = block(ModBlocks.MARBLE_ROCK);
    public static final DeferredItem<Item> MARBLE_BRICK = block(ModBlocks.MARBLE_BRICK);
    public static final DeferredItem<Item> CRACKED_MARBLE_BRICK = block(ModBlocks.CRACKED_MARBLE_BRICK);
    public static final DeferredItem<Item> MOSSY_MARBLE_BRICK = block(ModBlocks.MOSSY_MARBLE_BRICK);
    public static final DeferredItem<Item> MARBLE_COBBLESTONE = block(ModBlocks.MARBLE_COBBLESTONE);
    public static final DeferredItem<Item> MOSSY_MARBLE_COBBLESTONE = block(ModBlocks.MOSSY_MARBLE_COBBLESTONE);
    public static final DeferredItem<Item> SMOOTH_MARBLE_ROCK = block(ModBlocks.SMOOTH_MARBLE_ROCK);
    public static final DeferredItem<Item> MARBLE_PILLAR = block(ModBlocks.MARBLE_PILLAR);
    public static final DeferredItem<Item> MARBLE_ROCK_SLAB = block(ModBlocks.MARBLE_ROCK_SLAB);
    public static final DeferredItem<Item> MARBLE_ROCK_STAIRS = block(ModBlocks.MARBLE_ROCK_STAIRS);
    public static final DeferredItem<Item> MARBLE_ROCK_WALL = block(ModBlocks.MARBLE_ROCK_WALL);
    public static final DeferredItem<Item> MARBLE_ROCK_BUTTON = block(ModBlocks.MARBLE_ROCK_BUTTON);
    public static final DeferredItem<Item> MARBLE_ROCK_PRESSURE_PLATE = block(ModBlocks.MARBLE_ROCK_PRESSURE_PLATE);
    public static final DeferredItem<Item> MARBLE_BRICK_SLAB = block(ModBlocks.MARBLE_BRICK_SLAB);
    public static final DeferredItem<Item> MARBLE_BRICK_STAIRS = block(ModBlocks.MARBLE_BRICK_STAIRS);
    public static final DeferredItem<Item> MARBLE_BRICK_WALL = block(ModBlocks.MARBLE_BRICK_WALL);
    public static final DeferredItem<Item> CRACKED_MARBLE_BRICK_SLAB = block(ModBlocks.CRACKED_MARBLE_BRICK_SLAB);
    public static final DeferredItem<Item> CRACKED_MARBLE_BRICK_STAIRS = block(ModBlocks.CRACKED_MARBLE_BRICK_STAIRS);
    public static final DeferredItem<Item> CRACKED_MARBLE_BRICK_WALL = block(ModBlocks.CRACKED_MARBLE_BRICK_WALL);
    public static final DeferredItem<Item> MOSSY_MARBLE_BRICK_SLAB = block(ModBlocks.MOSSY_MARBLE_BRICK_SLAB);
    public static final DeferredItem<Item> MOSSY_MARBLE_BRICK_STAIRS = block(ModBlocks.MOSSY_MARBLE_BRICK_STAIRS);
    public static final DeferredItem<Item> MOSSY_MARBLE_BRICK_WALL = block(ModBlocks.MOSSY_MARBLE_BRICK_WALL);
    public static final DeferredItem<Item> MARBLE_COBBLESTONE_SLAB = block(ModBlocks.MARBLE_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> MARBLE_COBBLESTONE_STAIRS = block(ModBlocks.MARBLE_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> MARBLE_COBBLESTONE_WALL = block(ModBlocks.MARBLE_COBBLESTONE_WALL);
    public static final DeferredItem<Item> MOSSY_MARBLE_COBBLESTONE_SLAB = block(ModBlocks.MOSSY_MARBLE_COBBLESTONE_SLAB);
    public static final DeferredItem<Item> MOSSY_MARBLE_COBBLESTONE_STAIRS = block(ModBlocks.MOSSY_MARBLE_COBBLESTONE_STAIRS);
    public static final DeferredItem<Item> MOSSY_MARBLE_COBBLESTONE_WALL = block(ModBlocks.MOSSY_MARBLE_COBBLESTONE_WALL);
    public static final DeferredItem<Item> SMOOTH_MARBLE_ROCK_SLAB = block(ModBlocks.SMOOTH_MARBLE_ROCK_SLAB);
    public static final DeferredItem<Item> SMOOTH_MARBLE_ROCK_STAIRS = block(ModBlocks.SMOOTH_MARBLE_ROCK_STAIRS);
    public static final DeferredItem<Item> SMOOTH_MARBLE_ROCK_WALL = block(ModBlocks.SMOOTH_MARBLE_ROCK_WALL);

    public static final DeferredItem<Item> THATCH        = block(ModBlocks.THATCH);
    public static final DeferredItem<Item> THATCH_SLAB   = block(ModBlocks.THATCH_SLAB);
    public static final DeferredItem<Item> THATCH_STAIRS = block(ModBlocks.THATCH_STAIRS);
    public static final DeferredItem<Item> THATCH_WALL   = block(ModBlocks.THATCH_WALL);
    public static final DeferredItem<Item> WEATHERED_THATCH         = block(ModBlocks.WEATHERED_THATCH);
    public static final DeferredItem<Item> WEATHERED_THATCH_SLAB    = block(ModBlocks.WEATHERED_THATCH_SLAB);
    public static final DeferredItem<Item> WEATHERED_THATCH_STAIRS  = block(ModBlocks.WEATHERED_THATCH_STAIRS);
    public static final DeferredItem<Item> WEATHERED_THATCH_WALL    = block(ModBlocks.WEATHERED_THATCH_WALL);

    public static final DeferredItem<Item> DRAGONGLASS_ORE    = block(ModBlocks.DRAGONGLASS_ORE);
    public static final DeferredItem<Item> OPAL_ORE           = block(ModBlocks.OPAL_ORE);
    public static final DeferredItem<Item> RUBY_ORE           = block(ModBlocks.RUBY_ORE);
    public static final DeferredItem<Item> SAPPHIRE_ORE       = block(ModBlocks.SAPPHIRE_ORE);
    public static final DeferredItem<Item> SILVER_ORE         = block(ModBlocks.SILVER_ORE);
    public static final DeferredItem<Item> AMETHYST_ORE       = block(ModBlocks.AMETHYST_ORE);
    public static final DeferredItem<Item> TIN_ORE            = block(ModBlocks.TIN_ORE);
    public static final DeferredItem<Item> TOPAZ_ORE          = block(ModBlocks.TOPAZ_ORE);
    public static final DeferredItem<Item> VALYRIAN_STEEL_ORE = block(ModBlocks.VALYRIAN_STEEL_ORE) ;
    public static final DeferredItem<Item> COBALT_ORE         = block(ModBlocks.COBALT_ORE);
    public static final DeferredItem<Item> LEAD_ORE           = block(ModBlocks.LEAD_ORE);
    public static final DeferredItem<Item> PLATINUM_ORE       = block(ModBlocks.PLATINUM_ORE);
    public static final DeferredItem<Item> ZINC_ORE           = block(ModBlocks.ZINC_ORE);

    public static final DeferredItem<Item> DEEPSLATE_SILVER_ORE   = block(ModBlocks.DEEPSLATE_SILVER_ORE);
    public static final DeferredItem<Item> DEEPSLATE_AMETHYST_ORE = block(ModBlocks.DEEPSLATE_AMETHYST_ORE);
    public static final DeferredItem<Item> DEEPSLATE_TIN_ORE      = block(ModBlocks.DEEPSLATE_TIN_ORE);
    public static final DeferredItem<Item> DEEPSLATE_COBALT_ORE   = block(ModBlocks.DEEPSLATE_COBALT_ORE);
    public static final DeferredItem<Item> DEEPSLATE_LEAD_ORE     = block(ModBlocks.DEEPSLATE_LEAD_ORE);
    public static final DeferredItem<Item> DEEPSLATE_PLATINUM_ORE = block(ModBlocks.DEEPSLATE_PLATINUM_ORE);
    public static final DeferredItem<Item> DEEPSLATE_ZINC_ORE     = block(ModBlocks.DEEPSLATE_ZINC_ORE);

    public static final DeferredItem<Item> DEEPSLATE_OPAL_ORE       = block(ModBlocks.DEEPSLATE_OPAL_ORE);
    public static final DeferredItem<Item> DEEPSLATE_RUBY_ORE       = block(ModBlocks.DEEPSLATE_RUBY_ORE);
    public static final DeferredItem<Item> DEEPSLATE_SAPPHIRE_ORE   = block(ModBlocks.DEEPSLATE_SAPPHIRE_ORE);
    public static final DeferredItem<Item> DEEPSLATE_TOPAZ_ORE      = block(ModBlocks.DEEPSLATE_TOPAZ_ORE);
    public static final DeferredItem<Item> DEEPSLATE_BERYL_ORE      = block(ModBlocks.DEEPSLATE_BERYL_ORE);
    public static final DeferredItem<Item> DEEPSLATE_BLOODSTONE_ORE = block(ModBlocks.DEEPSLATE_BLOODSTONE_ORE);
    public static final DeferredItem<Item> DEEPSLATE_CARNELIAN_ORE  = block(ModBlocks.DEEPSLATE_CARNELIAN_ORE);
    public static final DeferredItem<Item> DEEPSLATE_CHALCEDONY_ORE = block(ModBlocks.DEEPSLATE_CHALCEDONY_ORE);
    public static final DeferredItem<Item> DEEPSLATE_GARNET_ORE     = block(ModBlocks.DEEPSLATE_GARNET_ORE);
    public static final DeferredItem<Item> DEEPSLATE_JADE_ORE       = block(ModBlocks.DEEPSLATE_JADE_ORE);
    public static final DeferredItem<Item> DEEPSLATE_JASPER_ORE     = block(ModBlocks.DEEPSLATE_JASPER_ORE);
    public static final DeferredItem<Item> DEEPSLATE_MALACHITE_ORE  = block(ModBlocks.DEEPSLATE_MALACHITE_ORE);
    public static final DeferredItem<Item> DEEPSLATE_MOONSTONE_ORE  = block(ModBlocks.DEEPSLATE_MOONSTONE_ORE);
    public static final DeferredItem<Item> DEEPSLATE_ONYX_ORE       = block(ModBlocks.DEEPSLATE_ONYX_ORE);
    public static final DeferredItem<Item> DEEPSLATE_TIGERS_EYE_ORE = block(ModBlocks.DEEPSLATE_TIGERS_EYE_ORE);
    public static final DeferredItem<Item> DEEPSLATE_TOURMALINE_ORE = block(ModBlocks.DEEPSLATE_TOURMALINE_ORE);

    public static final DeferredItem<Item> RAW_SILVER_BLOCK   = block(ModBlocks.RAW_SILVER_BLOCK);
    public static final DeferredItem<Item> RAW_TIN_BLOCK      = block(ModBlocks.RAW_TIN_BLOCK);
    public static final DeferredItem<Item> PATH_BLOCK         = block(ModBlocks.PATH_BLOCK);
    public static final DeferredItem<Item> COBBLED_PATH_BLOCK = block(ModBlocks.COBBLED_PATH_BLOCK);

    public static final DeferredItem<Item> DIRT_SLAB          = block(ModBlocks.DIRT_SLAB);
    public static final DeferredItem<Item> DIRT_STAIRS        = block(ModBlocks.DIRT_STAIRS);
    public static final DeferredItem<Item> MUD_SLAB           = block(ModBlocks.MUD_SLAB);
    public static final DeferredItem<Item> MUD_STAIRS         = block(ModBlocks.MUD_STAIRS);
    public static final DeferredItem<Item> DIRT_PATH_SLAB     = block(ModBlocks.DIRT_PATH_SLAB);
    public static final DeferredItem<Item> DIRT_PATH_STAIRS   = block(ModBlocks.DIRT_PATH_STAIRS);
    public static final DeferredItem<Item> COARSE_DIRT_SLAB   = block(ModBlocks.COARSE_DIRT_SLAB);
    public static final DeferredItem<Item> COARSE_DIRT_STAIRS = block(ModBlocks.COARSE_DIRT_STAIRS);
    public static final DeferredItem<Item> ROOTED_DIRT_SLAB   = block(ModBlocks.ROOTED_DIRT_SLAB);
    public static final DeferredItem<Item> ROOTED_DIRT_STAIRS = block(ModBlocks.ROOTED_DIRT_STAIRS);
    public static final DeferredItem<Item> PODZOL_SLAB        = block(ModBlocks.PODZOL_SLAB);
    public static final DeferredItem<Item> PODZOL_STAIRS      = block(ModBlocks.PODZOL_STAIRS);
    public static final DeferredItem<Item> GRASS_BLOCK_SLAB   = block(ModBlocks.GRASS_BLOCK_SLAB);
    public static final DeferredItem<Item> GRASS_BLOCK_STAIRS = block(ModBlocks.GRASS_BLOCK_STAIRS);
    public static final DeferredItem<Item> RAW_COBALT_BLOCK   = block(ModBlocks.RAW_COBALT_BLOCK);
    public static final DeferredItem<Item> RAW_LEAD_BLOCK     = block(ModBlocks.RAW_LEAD_BLOCK);
    public static final DeferredItem<Item> RAW_PLATINUM_BLOCK = block(ModBlocks.RAW_PLATINUM_BLOCK);
    public static final DeferredItem<Item> RAW_ZINC_BLOCK     = block(ModBlocks.RAW_ZINC_BLOCK);

    public static final DeferredItem<Item> AMBER           = simple("amber");
    public static final DeferredItem<Item> AMETHYST        = simple("amethyst");
    public static final DeferredItem<Item> DRAGONGLASS_SHARD = simple("dragonglass_shard");
    public static final DeferredItem<Item> OPAL            = simple("opal");
    public static final DeferredItem<Item> RUBY            = simple("ruby");
    public static final DeferredItem<Item> SAPPHIRE        = simple("sapphire");
    public static final DeferredItem<Item> TOPAZ           = simple("topaz");

    public static final DeferredItem<Item> OPAL_BLOCK      = block(ModBlocks.OPAL_BLOCK);
    public static final DeferredItem<Item> RUBY_BLOCK      = block(ModBlocks.RUBY_BLOCK);
    public static final DeferredItem<Item> SAPPHIRE_BLOCK  = block(ModBlocks.SAPPHIRE_BLOCK);
    public static final DeferredItem<Item> TOPAZ_BLOCK     = block(ModBlocks.TOPAZ_BLOCK);

    public static final DeferredItem<Item> BERYL            = simple("beryl");
    public static final DeferredItem<Item> BERYL_ORE        = block(ModBlocks.BERYL_ORE);
    public static final DeferredItem<Item> BERYL_BLOCK      = block(ModBlocks.BERYL_BLOCK);
    public static final DeferredItem<Item> BLOODSTONE       = simple("bloodstone");
    public static final DeferredItem<Item> BLOODSTONE_ORE   = block(ModBlocks.BLOODSTONE_ORE);
    public static final DeferredItem<Item> BLOODSTONE_BLOCK = block(ModBlocks.BLOODSTONE_BLOCK);
    public static final DeferredItem<Item> CARNELIAN        = simple("carnelian");
    public static final DeferredItem<Item> CARNELIAN_ORE    = block(ModBlocks.CARNELIAN_ORE);
    public static final DeferredItem<Item> CARNELIAN_BLOCK  = block(ModBlocks.CARNELIAN_BLOCK);
    public static final DeferredItem<Item> CHALCEDONY       = simple("chalcedony");
    public static final DeferredItem<Item> CHALCEDONY_ORE   = block(ModBlocks.CHALCEDONY_ORE);
    public static final DeferredItem<Item> CHALCEDONY_BLOCK = block(ModBlocks.CHALCEDONY_BLOCK);
    public static final DeferredItem<Item> GARNET           = simple("garnet");
    public static final DeferredItem<Item> GARNET_ORE       = block(ModBlocks.GARNET_ORE);
    public static final DeferredItem<Item> GARNET_BLOCK     = block(ModBlocks.GARNET_BLOCK);
    public static final DeferredItem<Item> JADE             = simple("jade");
    public static final DeferredItem<Item> JADE_ORE         = block(ModBlocks.JADE_ORE);
    public static final DeferredItem<Item> JADE_BLOCK       = block(ModBlocks.JADE_BLOCK);
    public static final DeferredItem<Item> JASPER           = simple("jasper");
    public static final DeferredItem<Item> JASPER_ORE       = block(ModBlocks.JASPER_ORE);
    public static final DeferredItem<Item> JASPER_BLOCK     = block(ModBlocks.JASPER_BLOCK);
    public static final DeferredItem<Item> MALACHITE        = simple("malachite");
    public static final DeferredItem<Item> MALACHITE_ORE    = block(ModBlocks.MALACHITE_ORE);
    public static final DeferredItem<Item> MALACHITE_BLOCK  = block(ModBlocks.MALACHITE_BLOCK);
    public static final DeferredItem<Item> MOONSTONE        = simple("moonstone");
    public static final DeferredItem<Item> MOONSTONE_ORE    = block(ModBlocks.MOONSTONE_ORE);
    public static final DeferredItem<Item> MOONSTONE_BLOCK  = block(ModBlocks.MOONSTONE_BLOCK);
    public static final DeferredItem<Item> ONYX             = simple("onyx");
    public static final DeferredItem<Item> ONYX_ORE         = block(ModBlocks.ONYX_ORE);
    public static final DeferredItem<Item> ONYX_BLOCK       = block(ModBlocks.ONYX_BLOCK);
    public static final DeferredItem<Item> TIGERS_EYE       = simple("tigers_eye");
    public static final DeferredItem<Item> TIGERS_EYE_ORE   = block(ModBlocks.TIGERS_EYE_ORE);
    public static final DeferredItem<Item> TIGERS_EYE_BLOCK = block(ModBlocks.TIGERS_EYE_BLOCK);
    public static final DeferredItem<Item> TOURMALINE       = simple("tourmaline");
    public static final DeferredItem<Item> TOURMALINE_ORE   = block(ModBlocks.TOURMALINE_ORE);
    public static final DeferredItem<Item> TOURMALINE_BLOCK = block(ModBlocks.TOURMALINE_BLOCK);

    public static final DeferredItem<Item> IVORY            = simple("ivory");
    public static final DeferredItem<Item> JET              = simple("jet");
    public static final DeferredItem<Item> PEARL            = simple("pearl");

    public static final DeferredItem<Item> RAW_SILVER          = simple("raw_silver");
    public static final DeferredItem<Item> RAW_TIN             = simple("raw_tin");
    public static final DeferredItem<Item> RAW_VALYRIAN_STEEL  = simple("raw_valyrian_steel");
    public static final DeferredItem<Item> RAW_COBALT          = simple("raw_cobalt");
    public static final DeferredItem<Item> RAW_LEAD            = simple("raw_lead");
    public static final DeferredItem<Item> RAW_PLATINUM        = simple("raw_platinum");
    public static final DeferredItem<Item> RAW_ZINC            = simple("raw_zinc");

    public static final DeferredItem<Item> SILVER_INGOT         = simple("silver_ingot");
    public static final DeferredItem<Item> TIN_INGOT            = simple("tin_ingot");
    public static final DeferredItem<Item> BRONZE_INGOT         = simple("bronze_ingot");
    public static final DeferredItem<Item> STEEL_INGOT          = simple("steel_ingot");
    public static final DeferredItem<Item> VALYRIAN_STEEL_INGOT = simple("valyrian_steel_ingot");
    public static final DeferredItem<Item> COBALT_INGOT         = simple("cobalt_ingot");
    public static final DeferredItem<Item> LEAD_INGOT           = simple("lead_ingot");
    public static final DeferredItem<Item> PLATINUM_INGOT       = simple("platinum_ingot");
    public static final DeferredItem<Item> ZINC_INGOT           = simple("zinc_ingot");

    public static final DeferredItem<Item> STRIPPED_WEIRWOOD_LOG = block(ModBlocks.STRIPPED_WEIRWOOD_LOG);
    public static final DeferredItem<Item> STRIPPED_ASPEN_LOG = block(ModBlocks.STRIPPED_ASPEN_LOG);
    public static final DeferredItem<Item> STRIPPED_ALDER_LOG = block(ModBlocks.STRIPPED_ALDER_LOG);
    public static final DeferredItem<Item> STRIPPED_PINE_LOG = block(ModBlocks.STRIPPED_PINE_LOG);
    public static final DeferredItem<Item> STRIPPED_FIR_LOG = block(ModBlocks.STRIPPED_FIR_LOG);
    public static final DeferredItem<Item> STRIPPED_SENTINAL_LOG = block(ModBlocks.STRIPPED_SENTINAL_LOG);
    public static final DeferredItem<Item> STRIPPED_IRONWOOD_LOG = block(ModBlocks.STRIPPED_IRONWOOD_LOG);
    public static final DeferredItem<Item> STRIPPED_BEECH_LOG = block(ModBlocks.STRIPPED_BEECH_LOG);
    public static final DeferredItem<Item> STRIPPED_SOLDIER_PINE_LOG = block(ModBlocks.STRIPPED_SOLDIER_PINE_LOG);
    public static final DeferredItem<Item> STRIPPED_ASH_LOG = block(ModBlocks.STRIPPED_ASH_LOG);
    public static final DeferredItem<Item> STRIPPED_HAWTHORN_LOG = block(ModBlocks.STRIPPED_HAWTHORN_LOG);
    public static final DeferredItem<Item> STRIPPED_BLACKBARK_LOG = block(ModBlocks.STRIPPED_BLACKBARK_LOG);
    public static final DeferredItem<Item> STRIPPED_BLOODWOOD_LOG = block(ModBlocks.STRIPPED_BLOODWOOD_LOG);
    public static final DeferredItem<Item> STRIPPED_BLUE_MAHOE_LOG = block(ModBlocks.STRIPPED_BLUE_MAHOE_LOG);
    public static final DeferredItem<Item> STRIPPED_COTTONWOOD_LOG = block(ModBlocks.STRIPPED_COTTONWOOD_LOG);
    public static final DeferredItem<Item> STRIPPED_BLACK_COTTONWOOD_LOG = block(ModBlocks.STRIPPED_BLACK_COTTONWOOD_LOG);
    public static final DeferredItem<Item> STRIPPED_CINNAMON_LOG = block(ModBlocks.STRIPPED_CINNAMON_LOG);
    public static final DeferredItem<Item> STRIPPED_CLOVE_LOG = block(ModBlocks.STRIPPED_CLOVE_LOG);
    public static final DeferredItem<Item> STRIPPED_EBONY_LOG = block(ModBlocks.STRIPPED_EBONY_LOG);
    public static final DeferredItem<Item> STRIPPED_ELM_LOG = block(ModBlocks.STRIPPED_ELM_LOG);
    public static final DeferredItem<Item> STRIPPED_CEDAR_LOG = block(ModBlocks.STRIPPED_CEDAR_LOG);
    public static final DeferredItem<Item> STRIPPED_APPLE_LOG = block(ModBlocks.STRIPPED_APPLE_LOG);
    public static final DeferredItem<Item> STRIPPED_GOLDENHEART_LOG = block(ModBlocks.STRIPPED_GOLDENHEART_LOG);
    public static final DeferredItem<Item> STRIPPED_LINDEN_LOG = block(ModBlocks.STRIPPED_LINDEN_LOG);
    public static final DeferredItem<Item> STRIPPED_MAHOGANY_LOG = block(ModBlocks.STRIPPED_MAHOGANY_LOG);
    public static final DeferredItem<Item> STRIPPED_MAPLE_LOG = block(ModBlocks.STRIPPED_MAPLE_LOG);
    public static final DeferredItem<Item> STRIPPED_MYRRH_LOG = block(ModBlocks.STRIPPED_MYRRH_LOG);
    public static final DeferredItem<Item> STRIPPED_REDWOOD_LOG = block(ModBlocks.STRIPPED_REDWOOD_LOG);
    public static final DeferredItem<Item> STRIPPED_CHESTNUT_LOG = block(ModBlocks.STRIPPED_CHESTNUT_LOG);
    public static final DeferredItem<Item> STRIPPED_WILLOW_LOG = block(ModBlocks.STRIPPED_WILLOW_LOG);
    public static final DeferredItem<Item> STRIPPED_WORMTREE_LOG = block(ModBlocks.STRIPPED_WORMTREE_LOG);

    public static final DeferredItem<Item> STRIPPED_WEIRWOOD_WOOD         = block(ModBlocks.STRIPPED_WEIRWOOD_WOOD);
    public static final DeferredItem<Item> STRIPPED_ASPEN_WOOD            = block(ModBlocks.STRIPPED_ASPEN_WOOD);
    public static final DeferredItem<Item> STRIPPED_ALDER_WOOD            = block(ModBlocks.STRIPPED_ALDER_WOOD);
    public static final DeferredItem<Item> STRIPPED_PINE_WOOD             = block(ModBlocks.STRIPPED_PINE_WOOD);
    public static final DeferredItem<Item> STRIPPED_FIR_WOOD              = block(ModBlocks.STRIPPED_FIR_WOOD);
    public static final DeferredItem<Item> STRIPPED_SENTINAL_WOOD         = block(ModBlocks.STRIPPED_SENTINAL_WOOD);
    public static final DeferredItem<Item> STRIPPED_IRONWOOD_WOOD         = block(ModBlocks.STRIPPED_IRONWOOD_WOOD);
    public static final DeferredItem<Item> STRIPPED_BEECH_WOOD            = block(ModBlocks.STRIPPED_BEECH_WOOD);
    public static final DeferredItem<Item> STRIPPED_SOLDIER_PINE_WOOD     = block(ModBlocks.STRIPPED_SOLDIER_PINE_WOOD);
    public static final DeferredItem<Item> STRIPPED_ASH_WOOD              = block(ModBlocks.STRIPPED_ASH_WOOD);
    public static final DeferredItem<Item> STRIPPED_HAWTHORN_WOOD         = block(ModBlocks.STRIPPED_HAWTHORN_WOOD);
    public static final DeferredItem<Item> STRIPPED_BLACKBARK_WOOD        = block(ModBlocks.STRIPPED_BLACKBARK_WOOD);
    public static final DeferredItem<Item> STRIPPED_BLOODWOOD_WOOD        = block(ModBlocks.STRIPPED_BLOODWOOD_WOOD);
    public static final DeferredItem<Item> STRIPPED_BLUE_MAHOE_WOOD       = block(ModBlocks.STRIPPED_BLUE_MAHOE_WOOD);
    public static final DeferredItem<Item> STRIPPED_COTTONWOOD_WOOD       = block(ModBlocks.STRIPPED_COTTONWOOD_WOOD);
    public static final DeferredItem<Item> STRIPPED_BLACK_COTTONWOOD_WOOD = block(ModBlocks.STRIPPED_BLACK_COTTONWOOD_WOOD);
    public static final DeferredItem<Item> STRIPPED_CINNAMON_WOOD         = block(ModBlocks.STRIPPED_CINNAMON_WOOD);
    public static final DeferredItem<Item> STRIPPED_CLOVE_WOOD            = block(ModBlocks.STRIPPED_CLOVE_WOOD);
    public static final DeferredItem<Item> STRIPPED_EBONY_WOOD            = block(ModBlocks.STRIPPED_EBONY_WOOD);
    public static final DeferredItem<Item> STRIPPED_ELM_WOOD              = block(ModBlocks.STRIPPED_ELM_WOOD);
    public static final DeferredItem<Item> STRIPPED_CEDAR_WOOD            = block(ModBlocks.STRIPPED_CEDAR_WOOD);
    public static final DeferredItem<Item> STRIPPED_APPLE_WOOD            = block(ModBlocks.STRIPPED_APPLE_WOOD);
    public static final DeferredItem<Item> STRIPPED_GOLDENHEART_WOOD      = block(ModBlocks.STRIPPED_GOLDENHEART_WOOD);
    public static final DeferredItem<Item> STRIPPED_LINDEN_WOOD           = block(ModBlocks.STRIPPED_LINDEN_WOOD);
    public static final DeferredItem<Item> STRIPPED_MAHOGANY_WOOD         = block(ModBlocks.STRIPPED_MAHOGANY_WOOD);
    public static final DeferredItem<Item> STRIPPED_MAPLE_WOOD            = block(ModBlocks.STRIPPED_MAPLE_WOOD);
    public static final DeferredItem<Item> STRIPPED_MYRRH_WOOD            = block(ModBlocks.STRIPPED_MYRRH_WOOD);
    public static final DeferredItem<Item> STRIPPED_REDWOOD_WOOD          = block(ModBlocks.STRIPPED_REDWOOD_WOOD);
    public static final DeferredItem<Item> STRIPPED_CHESTNUT_WOOD         = block(ModBlocks.STRIPPED_CHESTNUT_WOOD);
    public static final DeferredItem<Item> STRIPPED_WILLOW_WOOD           = block(ModBlocks.STRIPPED_WILLOW_WOOD);
    public static final DeferredItem<Item> STRIPPED_WORMTREE_WOOD         = block(ModBlocks.STRIPPED_WORMTREE_WOOD);

    public static final DeferredItem<Item> WEIRWOOD_WOOD_SLAB            = block(ModBlocks.WEIRWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> WEIRWOOD_WOOD_STAIRS          = block(ModBlocks.WEIRWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_WEIRWOOD_WOOD_SLAB   = block(ModBlocks.STRIPPED_WEIRWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_WEIRWOOD_WOOD_STAIRS = block(ModBlocks.STRIPPED_WEIRWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> ASPEN_WOOD_SLAB            = block(ModBlocks.ASPEN_WOOD_SLAB);
    public static final DeferredItem<Item> ASPEN_WOOD_STAIRS          = block(ModBlocks.ASPEN_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_ASPEN_WOOD_SLAB   = block(ModBlocks.STRIPPED_ASPEN_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_ASPEN_WOOD_STAIRS = block(ModBlocks.STRIPPED_ASPEN_WOOD_STAIRS);
    public static final DeferredItem<Item> ALDER_WOOD_SLAB            = block(ModBlocks.ALDER_WOOD_SLAB);
    public static final DeferredItem<Item> ALDER_WOOD_STAIRS          = block(ModBlocks.ALDER_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_ALDER_WOOD_SLAB   = block(ModBlocks.STRIPPED_ALDER_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_ALDER_WOOD_STAIRS = block(ModBlocks.STRIPPED_ALDER_WOOD_STAIRS);
    public static final DeferredItem<Item> PINE_WOOD_SLAB            = block(ModBlocks.PINE_WOOD_SLAB);
    public static final DeferredItem<Item> PINE_WOOD_STAIRS          = block(ModBlocks.PINE_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_PINE_WOOD_SLAB   = block(ModBlocks.STRIPPED_PINE_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_PINE_WOOD_STAIRS = block(ModBlocks.STRIPPED_PINE_WOOD_STAIRS);
    public static final DeferredItem<Item> FIR_WOOD_SLAB            = block(ModBlocks.FIR_WOOD_SLAB);
    public static final DeferredItem<Item> FIR_WOOD_STAIRS          = block(ModBlocks.FIR_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_FIR_WOOD_SLAB   = block(ModBlocks.STRIPPED_FIR_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_FIR_WOOD_STAIRS = block(ModBlocks.STRIPPED_FIR_WOOD_STAIRS);
    public static final DeferredItem<Item> SENTINAL_WOOD_SLAB            = block(ModBlocks.SENTINAL_WOOD_SLAB);
    public static final DeferredItem<Item> SENTINAL_WOOD_STAIRS          = block(ModBlocks.SENTINAL_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_SENTINAL_WOOD_SLAB   = block(ModBlocks.STRIPPED_SENTINAL_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_SENTINAL_WOOD_STAIRS = block(ModBlocks.STRIPPED_SENTINAL_WOOD_STAIRS);
    public static final DeferredItem<Item> IRONWOOD_WOOD_SLAB            = block(ModBlocks.IRONWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> IRONWOOD_WOOD_STAIRS          = block(ModBlocks.IRONWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_IRONWOOD_WOOD_SLAB   = block(ModBlocks.STRIPPED_IRONWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_IRONWOOD_WOOD_STAIRS = block(ModBlocks.STRIPPED_IRONWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> BEECH_WOOD_SLAB            = block(ModBlocks.BEECH_WOOD_SLAB);
    public static final DeferredItem<Item> BEECH_WOOD_STAIRS          = block(ModBlocks.BEECH_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_BEECH_WOOD_SLAB   = block(ModBlocks.STRIPPED_BEECH_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_BEECH_WOOD_STAIRS = block(ModBlocks.STRIPPED_BEECH_WOOD_STAIRS);
    public static final DeferredItem<Item> SOLDIER_PINE_WOOD_SLAB            = block(ModBlocks.SOLDIER_PINE_WOOD_SLAB);
    public static final DeferredItem<Item> SOLDIER_PINE_WOOD_STAIRS          = block(ModBlocks.SOLDIER_PINE_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_SOLDIER_PINE_WOOD_SLAB   = block(ModBlocks.STRIPPED_SOLDIER_PINE_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_SOLDIER_PINE_WOOD_STAIRS = block(ModBlocks.STRIPPED_SOLDIER_PINE_WOOD_STAIRS);
    public static final DeferredItem<Item> ASH_WOOD_SLAB            = block(ModBlocks.ASH_WOOD_SLAB);
    public static final DeferredItem<Item> ASH_WOOD_STAIRS          = block(ModBlocks.ASH_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_ASH_WOOD_SLAB   = block(ModBlocks.STRIPPED_ASH_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_ASH_WOOD_STAIRS = block(ModBlocks.STRIPPED_ASH_WOOD_STAIRS);
    public static final DeferredItem<Item> HAWTHORN_WOOD_SLAB            = block(ModBlocks.HAWTHORN_WOOD_SLAB);
    public static final DeferredItem<Item> HAWTHORN_WOOD_STAIRS          = block(ModBlocks.HAWTHORN_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_HAWTHORN_WOOD_SLAB   = block(ModBlocks.STRIPPED_HAWTHORN_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_HAWTHORN_WOOD_STAIRS = block(ModBlocks.STRIPPED_HAWTHORN_WOOD_STAIRS);
    public static final DeferredItem<Item> BLACKBARK_WOOD_SLAB            = block(ModBlocks.BLACKBARK_WOOD_SLAB);
    public static final DeferredItem<Item> BLACKBARK_WOOD_STAIRS          = block(ModBlocks.BLACKBARK_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_BLACKBARK_WOOD_SLAB   = block(ModBlocks.STRIPPED_BLACKBARK_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_BLACKBARK_WOOD_STAIRS = block(ModBlocks.STRIPPED_BLACKBARK_WOOD_STAIRS);
    public static final DeferredItem<Item> BLOODWOOD_WOOD_SLAB            = block(ModBlocks.BLOODWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> BLOODWOOD_WOOD_STAIRS          = block(ModBlocks.BLOODWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_BLOODWOOD_WOOD_SLAB   = block(ModBlocks.STRIPPED_BLOODWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_BLOODWOOD_WOOD_STAIRS = block(ModBlocks.STRIPPED_BLOODWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> BLUE_MAHOE_WOOD_SLAB            = block(ModBlocks.BLUE_MAHOE_WOOD_SLAB);
    public static final DeferredItem<Item> BLUE_MAHOE_WOOD_STAIRS          = block(ModBlocks.BLUE_MAHOE_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_BLUE_MAHOE_WOOD_SLAB   = block(ModBlocks.STRIPPED_BLUE_MAHOE_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_BLUE_MAHOE_WOOD_STAIRS = block(ModBlocks.STRIPPED_BLUE_MAHOE_WOOD_STAIRS);
    public static final DeferredItem<Item> COTTONWOOD_WOOD_SLAB            = block(ModBlocks.COTTONWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> COTTONWOOD_WOOD_STAIRS          = block(ModBlocks.COTTONWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_COTTONWOOD_WOOD_SLAB   = block(ModBlocks.STRIPPED_COTTONWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_COTTONWOOD_WOOD_STAIRS = block(ModBlocks.STRIPPED_COTTONWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_WOOD_SLAB            = block(ModBlocks.BLACK_COTTONWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_WOOD_STAIRS          = block(ModBlocks.BLACK_COTTONWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_BLACK_COTTONWOOD_WOOD_SLAB   = block(ModBlocks.STRIPPED_BLACK_COTTONWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_BLACK_COTTONWOOD_WOOD_STAIRS = block(ModBlocks.STRIPPED_BLACK_COTTONWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> CINNAMON_WOOD_SLAB            = block(ModBlocks.CINNAMON_WOOD_SLAB);
    public static final DeferredItem<Item> CINNAMON_WOOD_STAIRS          = block(ModBlocks.CINNAMON_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_CINNAMON_WOOD_SLAB   = block(ModBlocks.STRIPPED_CINNAMON_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_CINNAMON_WOOD_STAIRS = block(ModBlocks.STRIPPED_CINNAMON_WOOD_STAIRS);
    public static final DeferredItem<Item> CLOVE_WOOD_SLAB            = block(ModBlocks.CLOVE_WOOD_SLAB);
    public static final DeferredItem<Item> CLOVE_WOOD_STAIRS          = block(ModBlocks.CLOVE_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_CLOVE_WOOD_SLAB   = block(ModBlocks.STRIPPED_CLOVE_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_CLOVE_WOOD_STAIRS = block(ModBlocks.STRIPPED_CLOVE_WOOD_STAIRS);
    public static final DeferredItem<Item> EBONY_WOOD_SLAB            = block(ModBlocks.EBONY_WOOD_SLAB);
    public static final DeferredItem<Item> EBONY_WOOD_STAIRS          = block(ModBlocks.EBONY_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_EBONY_WOOD_SLAB   = block(ModBlocks.STRIPPED_EBONY_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_EBONY_WOOD_STAIRS = block(ModBlocks.STRIPPED_EBONY_WOOD_STAIRS);
    public static final DeferredItem<Item> ELM_WOOD_SLAB            = block(ModBlocks.ELM_WOOD_SLAB);
    public static final DeferredItem<Item> ELM_WOOD_STAIRS          = block(ModBlocks.ELM_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_ELM_WOOD_SLAB   = block(ModBlocks.STRIPPED_ELM_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_ELM_WOOD_STAIRS = block(ModBlocks.STRIPPED_ELM_WOOD_STAIRS);
    public static final DeferredItem<Item> CEDAR_WOOD_SLAB            = block(ModBlocks.CEDAR_WOOD_SLAB);
    public static final DeferredItem<Item> CEDAR_WOOD_STAIRS          = block(ModBlocks.CEDAR_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_CEDAR_WOOD_SLAB   = block(ModBlocks.STRIPPED_CEDAR_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_CEDAR_WOOD_STAIRS = block(ModBlocks.STRIPPED_CEDAR_WOOD_STAIRS);
    public static final DeferredItem<Item> APPLE_WOOD_SLAB            = block(ModBlocks.APPLE_WOOD_SLAB);
    public static final DeferredItem<Item> APPLE_WOOD_STAIRS          = block(ModBlocks.APPLE_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_APPLE_WOOD_SLAB   = block(ModBlocks.STRIPPED_APPLE_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_APPLE_WOOD_STAIRS = block(ModBlocks.STRIPPED_APPLE_WOOD_STAIRS);
    public static final DeferredItem<Item> GOLDENHEART_WOOD_SLAB            = block(ModBlocks.GOLDENHEART_WOOD_SLAB);
    public static final DeferredItem<Item> GOLDENHEART_WOOD_STAIRS          = block(ModBlocks.GOLDENHEART_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_GOLDENHEART_WOOD_SLAB   = block(ModBlocks.STRIPPED_GOLDENHEART_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_GOLDENHEART_WOOD_STAIRS = block(ModBlocks.STRIPPED_GOLDENHEART_WOOD_STAIRS);
    public static final DeferredItem<Item> LINDEN_WOOD_SLAB            = block(ModBlocks.LINDEN_WOOD_SLAB);
    public static final DeferredItem<Item> LINDEN_WOOD_STAIRS          = block(ModBlocks.LINDEN_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_LINDEN_WOOD_SLAB   = block(ModBlocks.STRIPPED_LINDEN_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_LINDEN_WOOD_STAIRS = block(ModBlocks.STRIPPED_LINDEN_WOOD_STAIRS);
    public static final DeferredItem<Item> MAHOGANY_WOOD_SLAB            = block(ModBlocks.MAHOGANY_WOOD_SLAB);
    public static final DeferredItem<Item> MAHOGANY_WOOD_STAIRS          = block(ModBlocks.MAHOGANY_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_MAHOGANY_WOOD_SLAB   = block(ModBlocks.STRIPPED_MAHOGANY_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_MAHOGANY_WOOD_STAIRS = block(ModBlocks.STRIPPED_MAHOGANY_WOOD_STAIRS);
    public static final DeferredItem<Item> MAPLE_WOOD_SLAB            = block(ModBlocks.MAPLE_WOOD_SLAB);
    public static final DeferredItem<Item> MAPLE_WOOD_STAIRS          = block(ModBlocks.MAPLE_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_MAPLE_WOOD_SLAB   = block(ModBlocks.STRIPPED_MAPLE_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_MAPLE_WOOD_STAIRS = block(ModBlocks.STRIPPED_MAPLE_WOOD_STAIRS);
    public static final DeferredItem<Item> MYRRH_WOOD_SLAB            = block(ModBlocks.MYRRH_WOOD_SLAB);
    public static final DeferredItem<Item> MYRRH_WOOD_STAIRS          = block(ModBlocks.MYRRH_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_MYRRH_WOOD_SLAB   = block(ModBlocks.STRIPPED_MYRRH_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_MYRRH_WOOD_STAIRS = block(ModBlocks.STRIPPED_MYRRH_WOOD_STAIRS);
    public static final DeferredItem<Item> REDWOOD_WOOD_SLAB            = block(ModBlocks.REDWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> REDWOOD_WOOD_STAIRS          = block(ModBlocks.REDWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_REDWOOD_WOOD_SLAB   = block(ModBlocks.STRIPPED_REDWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_REDWOOD_WOOD_STAIRS = block(ModBlocks.STRIPPED_REDWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> CHESTNUT_WOOD_SLAB            = block(ModBlocks.CHESTNUT_WOOD_SLAB);
    public static final DeferredItem<Item> CHESTNUT_WOOD_STAIRS          = block(ModBlocks.CHESTNUT_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_CHESTNUT_WOOD_SLAB   = block(ModBlocks.STRIPPED_CHESTNUT_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_CHESTNUT_WOOD_STAIRS = block(ModBlocks.STRIPPED_CHESTNUT_WOOD_STAIRS);
    public static final DeferredItem<Item> WILLOW_WOOD_SLAB            = block(ModBlocks.WILLOW_WOOD_SLAB);
    public static final DeferredItem<Item> WILLOW_WOOD_STAIRS          = block(ModBlocks.WILLOW_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_WILLOW_WOOD_SLAB   = block(ModBlocks.STRIPPED_WILLOW_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_WILLOW_WOOD_STAIRS = block(ModBlocks.STRIPPED_WILLOW_WOOD_STAIRS);
    public static final DeferredItem<Item> WORMTREE_WOOD_SLAB            = block(ModBlocks.WORMTREE_WOOD_SLAB);
    public static final DeferredItem<Item> WORMTREE_WOOD_STAIRS          = block(ModBlocks.WORMTREE_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_WORMTREE_WOOD_SLAB   = block(ModBlocks.STRIPPED_WORMTREE_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_WORMTREE_WOOD_STAIRS = block(ModBlocks.STRIPPED_WORMTREE_WOOD_STAIRS);
    public static final DeferredItem<Item> NIGHTWOOD_WOOD_SLAB            = block(ModBlocks.NIGHTWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> NIGHTWOOD_WOOD_STAIRS          = block(ModBlocks.NIGHTWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_NIGHTWOOD_WOOD_SLAB   = block(ModBlocks.STRIPPED_NIGHTWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_NIGHTWOOD_WOOD_STAIRS = block(ModBlocks.STRIPPED_NIGHTWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> PURPLEHEART_WOOD_SLAB            = block(ModBlocks.PURPLEHEART_WOOD_SLAB);
    public static final DeferredItem<Item> PURPLEHEART_WOOD_STAIRS          = block(ModBlocks.PURPLEHEART_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_PURPLEHEART_WOOD_SLAB   = block(ModBlocks.STRIPPED_PURPLEHEART_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_PURPLEHEART_WOOD_STAIRS = block(ModBlocks.STRIPPED_PURPLEHEART_WOOD_STAIRS);
    public static final DeferredItem<Item> TIGERWOOD_WOOD_SLAB            = block(ModBlocks.TIGERWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> TIGERWOOD_WOOD_STAIRS          = block(ModBlocks.TIGERWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_TIGERWOOD_WOOD_SLAB   = block(ModBlocks.STRIPPED_TIGERWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_TIGERWOOD_WOOD_STAIRS = block(ModBlocks.STRIPPED_TIGERWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> SANDALWOOD_WOOD_SLAB            = block(ModBlocks.SANDALWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> SANDALWOOD_WOOD_STAIRS          = block(ModBlocks.SANDALWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_SANDALWOOD_WOOD_SLAB   = block(ModBlocks.STRIPPED_SANDALWOOD_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_SANDALWOOD_WOOD_STAIRS = block(ModBlocks.STRIPPED_SANDALWOOD_WOOD_STAIRS);
    public static final DeferredItem<Item> SANDBEGGAR_WOOD_SLAB            = block(ModBlocks.SANDBEGGAR_WOOD_SLAB);
    public static final DeferredItem<Item> SANDBEGGAR_WOOD_STAIRS          = block(ModBlocks.SANDBEGGAR_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_SANDBEGGAR_WOOD_SLAB   = block(ModBlocks.STRIPPED_SANDBEGGAR_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_SANDBEGGAR_WOOD_STAIRS = block(ModBlocks.STRIPPED_SANDBEGGAR_WOOD_STAIRS);
    public static final DeferredItem<Item> APRICOT_WOOD_SLAB            = block(ModBlocks.APRICOT_WOOD_SLAB);
    public static final DeferredItem<Item> APRICOT_WOOD_STAIRS          = block(ModBlocks.APRICOT_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_APRICOT_WOOD_SLAB   = block(ModBlocks.STRIPPED_APRICOT_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_APRICOT_WOOD_STAIRS = block(ModBlocks.STRIPPED_APRICOT_WOOD_STAIRS);
    public static final DeferredItem<Item> BLACKTHORN_WOOD_SLAB            = block(ModBlocks.BLACKTHORN_WOOD_SLAB);
    public static final DeferredItem<Item> BLACKTHORN_WOOD_STAIRS          = block(ModBlocks.BLACKTHORN_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_BLACKTHORN_WOOD_SLAB   = block(ModBlocks.STRIPPED_BLACKTHORN_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_BLACKTHORN_WOOD_STAIRS = block(ModBlocks.STRIPPED_BLACKTHORN_WOOD_STAIRS);
    public static final DeferredItem<Item> RED_CHERRY_WOOD_SLAB            = block(ModBlocks.RED_CHERRY_WOOD_SLAB);
    public static final DeferredItem<Item> RED_CHERRY_WOOD_STAIRS          = block(ModBlocks.RED_CHERRY_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_RED_CHERRY_WOOD_SLAB   = block(ModBlocks.STRIPPED_RED_CHERRY_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_RED_CHERRY_WOOD_STAIRS = block(ModBlocks.STRIPPED_RED_CHERRY_WOOD_STAIRS);
    public static final DeferredItem<Item> BLACK_CHERRY_WOOD_SLAB            = block(ModBlocks.BLACK_CHERRY_WOOD_SLAB);
    public static final DeferredItem<Item> BLACK_CHERRY_WOOD_STAIRS          = block(ModBlocks.BLACK_CHERRY_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_BLACK_CHERRY_WOOD_SLAB   = block(ModBlocks.STRIPPED_BLACK_CHERRY_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_BLACK_CHERRY_WOOD_STAIRS = block(ModBlocks.STRIPPED_BLACK_CHERRY_WOOD_STAIRS);
    public static final DeferredItem<Item> WHITE_CHERRY_WOOD_SLAB            = block(ModBlocks.WHITE_CHERRY_WOOD_SLAB);
    public static final DeferredItem<Item> WHITE_CHERRY_WOOD_STAIRS          = block(ModBlocks.WHITE_CHERRY_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_WHITE_CHERRY_WOOD_SLAB   = block(ModBlocks.STRIPPED_WHITE_CHERRY_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_WHITE_CHERRY_WOOD_STAIRS = block(ModBlocks.STRIPPED_WHITE_CHERRY_WOOD_STAIRS);
    public static final DeferredItem<Item> CRABAPPLE_WOOD_SLAB            = block(ModBlocks.CRABAPPLE_WOOD_SLAB);
    public static final DeferredItem<Item> CRABAPPLE_WOOD_STAIRS          = block(ModBlocks.CRABAPPLE_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_CRABAPPLE_WOOD_SLAB   = block(ModBlocks.STRIPPED_CRABAPPLE_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_CRABAPPLE_WOOD_STAIRS = block(ModBlocks.STRIPPED_CRABAPPLE_WOOD_STAIRS);
    public static final DeferredItem<Item> DATE_PALM_WOOD_SLAB            = block(ModBlocks.DATE_PALM_WOOD_SLAB);
    public static final DeferredItem<Item> DATE_PALM_WOOD_STAIRS          = block(ModBlocks.DATE_PALM_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_DATE_PALM_WOOD_SLAB   = block(ModBlocks.STRIPPED_DATE_PALM_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_DATE_PALM_WOOD_STAIRS = block(ModBlocks.STRIPPED_DATE_PALM_WOOD_STAIRS);
    public static final DeferredItem<Item> FIG_WOOD_SLAB            = block(ModBlocks.FIG_WOOD_SLAB);
    public static final DeferredItem<Item> FIG_WOOD_STAIRS          = block(ModBlocks.FIG_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_FIG_WOOD_SLAB   = block(ModBlocks.STRIPPED_FIG_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_FIG_WOOD_STAIRS = block(ModBlocks.STRIPPED_FIG_WOOD_STAIRS);
    public static final DeferredItem<Item> LEMON_WOOD_SLAB            = block(ModBlocks.LEMON_WOOD_SLAB);
    public static final DeferredItem<Item> LEMON_WOOD_STAIRS          = block(ModBlocks.LEMON_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_LEMON_WOOD_SLAB   = block(ModBlocks.STRIPPED_LEMON_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_LEMON_WOOD_STAIRS = block(ModBlocks.STRIPPED_LEMON_WOOD_STAIRS);
    public static final DeferredItem<Item> LIME_WOOD_SLAB            = block(ModBlocks.LIME_WOOD_SLAB);
    public static final DeferredItem<Item> LIME_WOOD_STAIRS          = block(ModBlocks.LIME_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_LIME_WOOD_SLAB   = block(ModBlocks.STRIPPED_LIME_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_LIME_WOOD_STAIRS = block(ModBlocks.STRIPPED_LIME_WOOD_STAIRS);
    public static final DeferredItem<Item> OLIVE_WOOD_SLAB            = block(ModBlocks.OLIVE_WOOD_SLAB);
    public static final DeferredItem<Item> OLIVE_WOOD_STAIRS          = block(ModBlocks.OLIVE_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_OLIVE_WOOD_SLAB   = block(ModBlocks.STRIPPED_OLIVE_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_OLIVE_WOOD_STAIRS = block(ModBlocks.STRIPPED_OLIVE_WOOD_STAIRS);
    public static final DeferredItem<Item> ORANGE_WOOD_SLAB            = block(ModBlocks.ORANGE_WOOD_SLAB);
    public static final DeferredItem<Item> ORANGE_WOOD_STAIRS          = block(ModBlocks.ORANGE_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_ORANGE_WOOD_SLAB   = block(ModBlocks.STRIPPED_ORANGE_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_ORANGE_WOOD_STAIRS = block(ModBlocks.STRIPPED_ORANGE_WOOD_STAIRS);
    public static final DeferredItem<Item> ALMOND_WOOD_SLAB            = block(ModBlocks.ALMOND_WOOD_SLAB);
    public static final DeferredItem<Item> ALMOND_WOOD_STAIRS          = block(ModBlocks.ALMOND_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_ALMOND_WOOD_SLAB   = block(ModBlocks.STRIPPED_ALMOND_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_ALMOND_WOOD_STAIRS = block(ModBlocks.STRIPPED_ALMOND_WOOD_STAIRS);
    public static final DeferredItem<Item> HEMLOCK_WOOD_SLAB            = block(ModBlocks.HEMLOCK_WOOD_SLAB);
    public static final DeferredItem<Item> HEMLOCK_WOOD_STAIRS          = block(ModBlocks.HEMLOCK_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_HEMLOCK_WOOD_SLAB   = block(ModBlocks.STRIPPED_HEMLOCK_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_HEMLOCK_WOOD_STAIRS = block(ModBlocks.STRIPPED_HEMLOCK_WOOD_STAIRS);
    public static final DeferredItem<Item> NUTMEG_WOOD_SLAB            = block(ModBlocks.NUTMEG_WOOD_SLAB);
    public static final DeferredItem<Item> NUTMEG_WOOD_STAIRS          = block(ModBlocks.NUTMEG_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_NUTMEG_WOOD_SLAB   = block(ModBlocks.STRIPPED_NUTMEG_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_NUTMEG_WOOD_STAIRS = block(ModBlocks.STRIPPED_NUTMEG_WOOD_STAIRS);
    public static final DeferredItem<Item> PEACH_WOOD_SLAB            = block(ModBlocks.PEACH_WOOD_SLAB);
    public static final DeferredItem<Item> PEACH_WOOD_STAIRS          = block(ModBlocks.PEACH_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_PEACH_WOOD_SLAB   = block(ModBlocks.STRIPPED_PEACH_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_PEACH_WOOD_STAIRS = block(ModBlocks.STRIPPED_PEACH_WOOD_STAIRS);
    public static final DeferredItem<Item> PEAR_WOOD_SLAB            = block(ModBlocks.PEAR_WOOD_SLAB);
    public static final DeferredItem<Item> PEAR_WOOD_STAIRS          = block(ModBlocks.PEAR_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_PEAR_WOOD_SLAB   = block(ModBlocks.STRIPPED_PEAR_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_PEAR_WOOD_STAIRS = block(ModBlocks.STRIPPED_PEAR_WOOD_STAIRS);
    public static final DeferredItem<Item> PERSIMMON_WOOD_SLAB            = block(ModBlocks.PERSIMMON_WOOD_SLAB);
    public static final DeferredItem<Item> PERSIMMON_WOOD_STAIRS          = block(ModBlocks.PERSIMMON_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_PERSIMMON_WOOD_SLAB   = block(ModBlocks.STRIPPED_PERSIMMON_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_PERSIMMON_WOOD_STAIRS = block(ModBlocks.STRIPPED_PERSIMMON_WOOD_STAIRS);
    public static final DeferredItem<Item> PINK_IVORY_WOOD_SLAB            = block(ModBlocks.PINK_IVORY_WOOD_SLAB);
    public static final DeferredItem<Item> PINK_IVORY_WOOD_STAIRS          = block(ModBlocks.PINK_IVORY_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_PINK_IVORY_WOOD_SLAB   = block(ModBlocks.STRIPPED_PINK_IVORY_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_PINK_IVORY_WOOD_STAIRS = block(ModBlocks.STRIPPED_PINK_IVORY_WOOD_STAIRS);
    public static final DeferredItem<Item> PLUM_WOOD_SLAB            = block(ModBlocks.PLUM_WOOD_SLAB);
    public static final DeferredItem<Item> PLUM_WOOD_STAIRS          = block(ModBlocks.PLUM_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_PLUM_WOOD_SLAB   = block(ModBlocks.STRIPPED_PLUM_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_PLUM_WOOD_STAIRS = block(ModBlocks.STRIPPED_PLUM_WOOD_STAIRS);
    public static final DeferredItem<Item> POMEGRANATE_WOOD_SLAB            = block(ModBlocks.POMEGRANATE_WOOD_SLAB);
    public static final DeferredItem<Item> POMEGRANATE_WOOD_STAIRS          = block(ModBlocks.POMEGRANATE_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_POMEGRANATE_WOOD_SLAB   = block(ModBlocks.STRIPPED_POMEGRANATE_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_POMEGRANATE_WOOD_STAIRS = block(ModBlocks.STRIPPED_POMEGRANATE_WOOD_STAIRS);
    public static final DeferredItem<Item> PRUNE_WOOD_SLAB            = block(ModBlocks.PRUNE_WOOD_SLAB);
    public static final DeferredItem<Item> PRUNE_WOOD_STAIRS          = block(ModBlocks.PRUNE_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_PRUNE_WOOD_SLAB   = block(ModBlocks.STRIPPED_PRUNE_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_PRUNE_WOOD_STAIRS = block(ModBlocks.STRIPPED_PRUNE_WOOD_STAIRS);

    public static final DeferredItem<Item> OAK_WOOD_SLAB            = block(ModBlocks.OAK_WOOD_SLAB);
    public static final DeferredItem<Item> OAK_WOOD_STAIRS          = block(ModBlocks.OAK_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_OAK_WOOD_SLAB   = block(ModBlocks.STRIPPED_OAK_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_OAK_WOOD_STAIRS = block(ModBlocks.STRIPPED_OAK_WOOD_STAIRS);
    public static final DeferredItem<Item> SPRUCE_WOOD_SLAB            = block(ModBlocks.SPRUCE_WOOD_SLAB);
    public static final DeferredItem<Item> SPRUCE_WOOD_STAIRS          = block(ModBlocks.SPRUCE_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_SPRUCE_WOOD_SLAB   = block(ModBlocks.STRIPPED_SPRUCE_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_SPRUCE_WOOD_STAIRS = block(ModBlocks.STRIPPED_SPRUCE_WOOD_STAIRS);
    public static final DeferredItem<Item> BIRCH_WOOD_SLAB            = block(ModBlocks.BIRCH_WOOD_SLAB);
    public static final DeferredItem<Item> BIRCH_WOOD_STAIRS          = block(ModBlocks.BIRCH_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_BIRCH_WOOD_SLAB   = block(ModBlocks.STRIPPED_BIRCH_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_BIRCH_WOOD_STAIRS = block(ModBlocks.STRIPPED_BIRCH_WOOD_STAIRS);
    public static final DeferredItem<Item> JUNGLE_WOOD_SLAB            = block(ModBlocks.JUNGLE_WOOD_SLAB);
    public static final DeferredItem<Item> JUNGLE_WOOD_STAIRS          = block(ModBlocks.JUNGLE_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_JUNGLE_WOOD_SLAB   = block(ModBlocks.STRIPPED_JUNGLE_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_JUNGLE_WOOD_STAIRS = block(ModBlocks.STRIPPED_JUNGLE_WOOD_STAIRS);
    public static final DeferredItem<Item> ACACIA_WOOD_SLAB            = block(ModBlocks.ACACIA_WOOD_SLAB);
    public static final DeferredItem<Item> ACACIA_WOOD_STAIRS          = block(ModBlocks.ACACIA_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_ACACIA_WOOD_SLAB   = block(ModBlocks.STRIPPED_ACACIA_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_ACACIA_WOOD_STAIRS = block(ModBlocks.STRIPPED_ACACIA_WOOD_STAIRS);
    public static final DeferredItem<Item> DARK_OAK_WOOD_SLAB            = block(ModBlocks.DARK_OAK_WOOD_SLAB);
    public static final DeferredItem<Item> DARK_OAK_WOOD_STAIRS          = block(ModBlocks.DARK_OAK_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_DARK_OAK_WOOD_SLAB   = block(ModBlocks.STRIPPED_DARK_OAK_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_DARK_OAK_WOOD_STAIRS = block(ModBlocks.STRIPPED_DARK_OAK_WOOD_STAIRS);
    public static final DeferredItem<Item> MANGROVE_WOOD_SLAB            = block(ModBlocks.MANGROVE_WOOD_SLAB);
    public static final DeferredItem<Item> MANGROVE_WOOD_STAIRS          = block(ModBlocks.MANGROVE_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_MANGROVE_WOOD_SLAB   = block(ModBlocks.STRIPPED_MANGROVE_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_MANGROVE_WOOD_STAIRS = block(ModBlocks.STRIPPED_MANGROVE_WOOD_STAIRS);
    public static final DeferredItem<Item> CHERRY_WOOD_SLAB            = block(ModBlocks.CHERRY_WOOD_SLAB);
    public static final DeferredItem<Item> CHERRY_WOOD_STAIRS          = block(ModBlocks.CHERRY_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_CHERRY_WOOD_SLAB   = block(ModBlocks.STRIPPED_CHERRY_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_CHERRY_WOOD_STAIRS = block(ModBlocks.STRIPPED_CHERRY_WOOD_STAIRS);
    public static final DeferredItem<Item> PALE_OAK_WOOD_SLAB            = block(ModBlocks.PALE_OAK_WOOD_SLAB);
    public static final DeferredItem<Item> PALE_OAK_WOOD_STAIRS          = block(ModBlocks.PALE_OAK_WOOD_STAIRS);
    public static final DeferredItem<Item> STRIPPED_PALE_OAK_WOOD_SLAB   = block(ModBlocks.STRIPPED_PALE_OAK_WOOD_SLAB);
    public static final DeferredItem<Item> STRIPPED_PALE_OAK_WOOD_STAIRS = block(ModBlocks.STRIPPED_PALE_OAK_WOOD_STAIRS);

    public static final DeferredItem<Item> WEIRWOOD_DOOR             = door(ModBlocks.WEIRWOOD_DOOR);
    public static final DeferredItem<Item> ASPEN_DOOR                = door(ModBlocks.ASPEN_DOOR);
    public static final DeferredItem<Item> ALDER_DOOR                = door(ModBlocks.ALDER_DOOR);
    public static final DeferredItem<Item> PINE_DOOR                 = door(ModBlocks.PINE_DOOR);
    public static final DeferredItem<Item> FIR_DOOR                  = door(ModBlocks.FIR_DOOR);
    public static final DeferredItem<Item> SENTINAL_DOOR             = door(ModBlocks.SENTINAL_DOOR);
    public static final DeferredItem<Item> IRONWOOD_DOOR             = door(ModBlocks.IRONWOOD_DOOR);
    public static final DeferredItem<Item> BEECH_DOOR                = door(ModBlocks.BEECH_DOOR);
    public static final DeferredItem<Item> SOLDIER_PINE_DOOR         = door(ModBlocks.SOLDIER_PINE_DOOR);
    public static final DeferredItem<Item> ASH_DOOR                  = door(ModBlocks.ASH_DOOR);
    public static final DeferredItem<Item> HAWTHORN_DOOR             = door(ModBlocks.HAWTHORN_DOOR);
    public static final DeferredItem<Item> BLACKBARK_DOOR            = door(ModBlocks.BLACKBARK_DOOR);
    public static final DeferredItem<Item> BLOODWOOD_DOOR            = door(ModBlocks.BLOODWOOD_DOOR);
    public static final DeferredItem<Item> BLUE_MAHOE_DOOR           = door(ModBlocks.BLUE_MAHOE_DOOR);
    public static final DeferredItem<Item> COTTONWOOD_DOOR           = door(ModBlocks.COTTONWOOD_DOOR);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_DOOR     = door(ModBlocks.BLACK_COTTONWOOD_DOOR);
    public static final DeferredItem<Item> CINNAMON_DOOR             = door(ModBlocks.CINNAMON_DOOR);
    public static final DeferredItem<Item> CLOVE_DOOR                = door(ModBlocks.CLOVE_DOOR);
    public static final DeferredItem<Item> EBONY_DOOR                = door(ModBlocks.EBONY_DOOR);
    public static final DeferredItem<Item> ELM_DOOR                  = door(ModBlocks.ELM_DOOR);
    public static final DeferredItem<Item> CEDAR_DOOR                = door(ModBlocks.CEDAR_DOOR);
    public static final DeferredItem<Item> APPLE_DOOR                = door(ModBlocks.APPLE_DOOR);
    public static final DeferredItem<Item> GOLDENHEART_DOOR          = door(ModBlocks.GOLDENHEART_DOOR);
    public static final DeferredItem<Item> LINDEN_DOOR               = door(ModBlocks.LINDEN_DOOR);
    public static final DeferredItem<Item> MAHOGANY_DOOR             = door(ModBlocks.MAHOGANY_DOOR);
    public static final DeferredItem<Item> MAPLE_DOOR                = door(ModBlocks.MAPLE_DOOR);
    public static final DeferredItem<Item> MYRRH_DOOR                = door(ModBlocks.MYRRH_DOOR);
    public static final DeferredItem<Item> REDWOOD_DOOR              = door(ModBlocks.REDWOOD_DOOR);
    public static final DeferredItem<Item> CHESTNUT_DOOR             = door(ModBlocks.CHESTNUT_DOOR);
    public static final DeferredItem<Item> WILLOW_DOOR               = door(ModBlocks.WILLOW_DOOR);
    public static final DeferredItem<Item> WORMTREE_DOOR             = door(ModBlocks.WORMTREE_DOOR);

    public static final DeferredItem<Item> WEIRWOOD_TRAPDOOR         = block(ModBlocks.WEIRWOOD_TRAPDOOR);
    public static final DeferredItem<Item> ASPEN_TRAPDOOR            = block(ModBlocks.ASPEN_TRAPDOOR);
    public static final DeferredItem<Item> ALDER_TRAPDOOR            = block(ModBlocks.ALDER_TRAPDOOR);
    public static final DeferredItem<Item> PINE_TRAPDOOR             = block(ModBlocks.PINE_TRAPDOOR);
    public static final DeferredItem<Item> FIR_TRAPDOOR              = block(ModBlocks.FIR_TRAPDOOR);
    public static final DeferredItem<Item> SENTINAL_TRAPDOOR         = block(ModBlocks.SENTINAL_TRAPDOOR);
    public static final DeferredItem<Item> IRONWOOD_TRAPDOOR         = block(ModBlocks.IRONWOOD_TRAPDOOR);
    public static final DeferredItem<Item> BEECH_TRAPDOOR            = block(ModBlocks.BEECH_TRAPDOOR);
    public static final DeferredItem<Item> SOLDIER_PINE_TRAPDOOR     = block(ModBlocks.SOLDIER_PINE_TRAPDOOR);
    public static final DeferredItem<Item> ASH_TRAPDOOR              = block(ModBlocks.ASH_TRAPDOOR);
    public static final DeferredItem<Item> HAWTHORN_TRAPDOOR         = block(ModBlocks.HAWTHORN_TRAPDOOR);
    public static final DeferredItem<Item> BLACKBARK_TRAPDOOR        = block(ModBlocks.BLACKBARK_TRAPDOOR);
    public static final DeferredItem<Item> BLOODWOOD_TRAPDOOR        = block(ModBlocks.BLOODWOOD_TRAPDOOR);
    public static final DeferredItem<Item> BLUE_MAHOE_TRAPDOOR       = block(ModBlocks.BLUE_MAHOE_TRAPDOOR);
    public static final DeferredItem<Item> COTTONWOOD_TRAPDOOR       = block(ModBlocks.COTTONWOOD_TRAPDOOR);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_TRAPDOOR = block(ModBlocks.BLACK_COTTONWOOD_TRAPDOOR);
    public static final DeferredItem<Item> CINNAMON_TRAPDOOR         = block(ModBlocks.CINNAMON_TRAPDOOR);
    public static final DeferredItem<Item> CLOVE_TRAPDOOR            = block(ModBlocks.CLOVE_TRAPDOOR);
    public static final DeferredItem<Item> EBONY_TRAPDOOR            = block(ModBlocks.EBONY_TRAPDOOR);
    public static final DeferredItem<Item> ELM_TRAPDOOR              = block(ModBlocks.ELM_TRAPDOOR);
    public static final DeferredItem<Item> CEDAR_TRAPDOOR            = block(ModBlocks.CEDAR_TRAPDOOR);
    public static final DeferredItem<Item> APPLE_TRAPDOOR            = block(ModBlocks.APPLE_TRAPDOOR);
    public static final DeferredItem<Item> GOLDENHEART_TRAPDOOR      = block(ModBlocks.GOLDENHEART_TRAPDOOR);
    public static final DeferredItem<Item> LINDEN_TRAPDOOR           = block(ModBlocks.LINDEN_TRAPDOOR);
    public static final DeferredItem<Item> MAHOGANY_TRAPDOOR         = block(ModBlocks.MAHOGANY_TRAPDOOR);
    public static final DeferredItem<Item> MAPLE_TRAPDOOR            = block(ModBlocks.MAPLE_TRAPDOOR);
    public static final DeferredItem<Item> MYRRH_TRAPDOOR            = block(ModBlocks.MYRRH_TRAPDOOR);
    public static final DeferredItem<Item> REDWOOD_TRAPDOOR          = block(ModBlocks.REDWOOD_TRAPDOOR);
    public static final DeferredItem<Item> CHESTNUT_TRAPDOOR         = block(ModBlocks.CHESTNUT_TRAPDOOR);
    public static final DeferredItem<Item> WILLOW_TRAPDOOR           = block(ModBlocks.WILLOW_TRAPDOOR);
    public static final DeferredItem<Item> WORMTREE_TRAPDOOR         = block(ModBlocks.WORMTREE_TRAPDOOR);

    public static final DeferredItem<Item> WEIRWOOD_BRANCH         = block(ModBlocks.WEIRWOOD_BRANCH);
    public static final DeferredItem<Item> STRIPPED_WEIRWOOD_BRANCH = block(ModBlocks.STRIPPED_WEIRWOOD_BRANCH);
    public static final DeferredItem<Item> ASPEN_BRANCH            = block(ModBlocks.ASPEN_BRANCH);
    public static final DeferredItem<Item> STRIPPED_ASPEN_BRANCH = block(ModBlocks.STRIPPED_ASPEN_BRANCH);
    public static final DeferredItem<Item> ALDER_BRANCH            = block(ModBlocks.ALDER_BRANCH);
    public static final DeferredItem<Item> STRIPPED_ALDER_BRANCH = block(ModBlocks.STRIPPED_ALDER_BRANCH);
    public static final DeferredItem<Item> PINE_BRANCH             = block(ModBlocks.PINE_BRANCH);
    public static final DeferredItem<Item> STRIPPED_PINE_BRANCH = block(ModBlocks.STRIPPED_PINE_BRANCH);
    public static final DeferredItem<Item> FIR_BRANCH              = block(ModBlocks.FIR_BRANCH);
    public static final DeferredItem<Item> STRIPPED_FIR_BRANCH = block(ModBlocks.STRIPPED_FIR_BRANCH);
    public static final DeferredItem<Item> SENTINAL_BRANCH         = block(ModBlocks.SENTINAL_BRANCH);
    public static final DeferredItem<Item> STRIPPED_SENTINAL_BRANCH = block(ModBlocks.STRIPPED_SENTINAL_BRANCH);
    public static final DeferredItem<Item> IRONWOOD_BRANCH         = block(ModBlocks.IRONWOOD_BRANCH);
    public static final DeferredItem<Item> STRIPPED_IRONWOOD_BRANCH = block(ModBlocks.STRIPPED_IRONWOOD_BRANCH);
    public static final DeferredItem<Item> BEECH_BRANCH            = block(ModBlocks.BEECH_BRANCH);
    public static final DeferredItem<Item> STRIPPED_BEECH_BRANCH = block(ModBlocks.STRIPPED_BEECH_BRANCH);
    public static final DeferredItem<Item> SOLDIER_PINE_BRANCH     = block(ModBlocks.SOLDIER_PINE_BRANCH);
    public static final DeferredItem<Item> STRIPPED_SOLDIER_PINE_BRANCH = block(ModBlocks.STRIPPED_SOLDIER_PINE_BRANCH);
    public static final DeferredItem<Item> ASH_BRANCH              = block(ModBlocks.ASH_BRANCH);
    public static final DeferredItem<Item> STRIPPED_ASH_BRANCH = block(ModBlocks.STRIPPED_ASH_BRANCH);
    public static final DeferredItem<Item> HAWTHORN_BRANCH         = block(ModBlocks.HAWTHORN_BRANCH);
    public static final DeferredItem<Item> STRIPPED_HAWTHORN_BRANCH = block(ModBlocks.STRIPPED_HAWTHORN_BRANCH);
    public static final DeferredItem<Item> BLACKBARK_BRANCH        = block(ModBlocks.BLACKBARK_BRANCH);
    public static final DeferredItem<Item> STRIPPED_BLACKBARK_BRANCH = block(ModBlocks.STRIPPED_BLACKBARK_BRANCH);
    public static final DeferredItem<Item> BLOODWOOD_BRANCH        = block(ModBlocks.BLOODWOOD_BRANCH);
    public static final DeferredItem<Item> STRIPPED_BLOODWOOD_BRANCH = block(ModBlocks.STRIPPED_BLOODWOOD_BRANCH);
    public static final DeferredItem<Item> BLUE_MAHOE_BRANCH       = block(ModBlocks.BLUE_MAHOE_BRANCH);
    public static final DeferredItem<Item> STRIPPED_BLUE_MAHOE_BRANCH = block(ModBlocks.STRIPPED_BLUE_MAHOE_BRANCH);
    public static final DeferredItem<Item> COTTONWOOD_BRANCH       = block(ModBlocks.COTTONWOOD_BRANCH);
    public static final DeferredItem<Item> STRIPPED_COTTONWOOD_BRANCH = block(ModBlocks.STRIPPED_COTTONWOOD_BRANCH);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_BRANCH = block(ModBlocks.BLACK_COTTONWOOD_BRANCH);
    public static final DeferredItem<Item> STRIPPED_BLACK_COTTONWOOD_BRANCH = block(ModBlocks.STRIPPED_BLACK_COTTONWOOD_BRANCH);
    public static final DeferredItem<Item> CINNAMON_BRANCH         = block(ModBlocks.CINNAMON_BRANCH);
    public static final DeferredItem<Item> STRIPPED_CINNAMON_BRANCH = block(ModBlocks.STRIPPED_CINNAMON_BRANCH);
    public static final DeferredItem<Item> CLOVE_BRANCH            = block(ModBlocks.CLOVE_BRANCH);
    public static final DeferredItem<Item> STRIPPED_CLOVE_BRANCH = block(ModBlocks.STRIPPED_CLOVE_BRANCH);
    public static final DeferredItem<Item> EBONY_BRANCH            = block(ModBlocks.EBONY_BRANCH);
    public static final DeferredItem<Item> STRIPPED_EBONY_BRANCH = block(ModBlocks.STRIPPED_EBONY_BRANCH);
    public static final DeferredItem<Item> ELM_BRANCH              = block(ModBlocks.ELM_BRANCH);
    public static final DeferredItem<Item> STRIPPED_ELM_BRANCH = block(ModBlocks.STRIPPED_ELM_BRANCH);
    public static final DeferredItem<Item> CEDAR_BRANCH            = block(ModBlocks.CEDAR_BRANCH);
    public static final DeferredItem<Item> STRIPPED_CEDAR_BRANCH = block(ModBlocks.STRIPPED_CEDAR_BRANCH);
    public static final DeferredItem<Item> APPLE_BRANCH            = block(ModBlocks.APPLE_BRANCH);
    public static final DeferredItem<Item> STRIPPED_APPLE_BRANCH = block(ModBlocks.STRIPPED_APPLE_BRANCH);
    public static final DeferredItem<Item> GOLDENHEART_BRANCH      = block(ModBlocks.GOLDENHEART_BRANCH);
    public static final DeferredItem<Item> STRIPPED_GOLDENHEART_BRANCH = block(ModBlocks.STRIPPED_GOLDENHEART_BRANCH);
    public static final DeferredItem<Item> LINDEN_BRANCH           = block(ModBlocks.LINDEN_BRANCH);
    public static final DeferredItem<Item> STRIPPED_LINDEN_BRANCH = block(ModBlocks.STRIPPED_LINDEN_BRANCH);
    public static final DeferredItem<Item> MAHOGANY_BRANCH         = block(ModBlocks.MAHOGANY_BRANCH);
    public static final DeferredItem<Item> STRIPPED_MAHOGANY_BRANCH = block(ModBlocks.STRIPPED_MAHOGANY_BRANCH);
    public static final DeferredItem<Item> MAPLE_BRANCH            = block(ModBlocks.MAPLE_BRANCH);
    public static final DeferredItem<Item> STRIPPED_MAPLE_BRANCH = block(ModBlocks.STRIPPED_MAPLE_BRANCH);
    public static final DeferredItem<Item> MYRRH_BRANCH            = block(ModBlocks.MYRRH_BRANCH);
    public static final DeferredItem<Item> STRIPPED_MYRRH_BRANCH = block(ModBlocks.STRIPPED_MYRRH_BRANCH);
    public static final DeferredItem<Item> REDWOOD_BRANCH          = block(ModBlocks.REDWOOD_BRANCH);
    public static final DeferredItem<Item> STRIPPED_REDWOOD_BRANCH = block(ModBlocks.STRIPPED_REDWOOD_BRANCH);
    public static final DeferredItem<Item> CHESTNUT_BRANCH         = block(ModBlocks.CHESTNUT_BRANCH);
    public static final DeferredItem<Item> STRIPPED_CHESTNUT_BRANCH = block(ModBlocks.STRIPPED_CHESTNUT_BRANCH);
    public static final DeferredItem<Item> WILLOW_BRANCH           = block(ModBlocks.WILLOW_BRANCH);
    public static final DeferredItem<Item> STRIPPED_WILLOW_BRANCH = block(ModBlocks.STRIPPED_WILLOW_BRANCH);
    public static final DeferredItem<Item> WORMTREE_BRANCH         = block(ModBlocks.WORMTREE_BRANCH);
    public static final DeferredItem<Item> STRIPPED_WORMTREE_BRANCH = block(ModBlocks.STRIPPED_WORMTREE_BRANCH);

    public static final DeferredItem<Item> OAK_BRANCH              = block(ModBlocks.OAK_BRANCH);
    public static final DeferredItem<Item> STRIPPED_OAK_BRANCH      = block(ModBlocks.STRIPPED_OAK_BRANCH);
    public static final DeferredItem<Item> SPRUCE_BRANCH           = block(ModBlocks.SPRUCE_BRANCH);
    public static final DeferredItem<Item> STRIPPED_SPRUCE_BRANCH   = block(ModBlocks.STRIPPED_SPRUCE_BRANCH);
    public static final DeferredItem<Item> BIRCH_BRANCH            = block(ModBlocks.BIRCH_BRANCH);
    public static final DeferredItem<Item> STRIPPED_BIRCH_BRANCH    = block(ModBlocks.STRIPPED_BIRCH_BRANCH);
    public static final DeferredItem<Item> JUNGLE_BRANCH           = block(ModBlocks.JUNGLE_BRANCH);
    public static final DeferredItem<Item> STRIPPED_JUNGLE_BRANCH   = block(ModBlocks.STRIPPED_JUNGLE_BRANCH);
    public static final DeferredItem<Item> ACACIA_BRANCH           = block(ModBlocks.ACACIA_BRANCH);
    public static final DeferredItem<Item> STRIPPED_ACACIA_BRANCH   = block(ModBlocks.STRIPPED_ACACIA_BRANCH);
    public static final DeferredItem<Item> DARK_OAK_BRANCH         = block(ModBlocks.DARK_OAK_BRANCH);
    public static final DeferredItem<Item> STRIPPED_DARK_OAK_BRANCH = block(ModBlocks.STRIPPED_DARK_OAK_BRANCH);
    public static final DeferredItem<Item> MANGROVE_BRANCH         = block(ModBlocks.MANGROVE_BRANCH);
    public static final DeferredItem<Item> STRIPPED_MANGROVE_BRANCH = block(ModBlocks.STRIPPED_MANGROVE_BRANCH);
    public static final DeferredItem<Item> CHERRY_BRANCH           = block(ModBlocks.CHERRY_BRANCH);
    public static final DeferredItem<Item> STRIPPED_CHERRY_BRANCH   = block(ModBlocks.STRIPPED_CHERRY_BRANCH);
    public static final DeferredItem<Item> PALE_OAK_BRANCH         = block(ModBlocks.PALE_OAK_BRANCH);
    public static final DeferredItem<Item> STRIPPED_PALE_OAK_BRANCH = block(ModBlocks.STRIPPED_PALE_OAK_BRANCH);

    public static final DeferredItem<Item> WEIRWOOD_SIGN         = REGISTRY.registerItem("weirwood_sign",         p -> new SignItem(ModBlocks.WEIRWOOD_SIGN.get(), ModBlocks.WEIRWOOD_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> WEIRWOOD_HANGING_SIGN = REGISTRY.registerItem("weirwood_hanging_sign", p -> new HangingSignItem(ModBlocks.WEIRWOOD_HANGING_SIGN.get(), ModBlocks.WEIRWOOD_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> ASPEN_SIGN         = REGISTRY.registerItem("aspen_sign",         p -> new SignItem(ModBlocks.ASPEN_SIGN.get(), ModBlocks.ASPEN_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> ASPEN_HANGING_SIGN = REGISTRY.registerItem("aspen_hanging_sign", p -> new HangingSignItem(ModBlocks.ASPEN_HANGING_SIGN.get(), ModBlocks.ASPEN_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> ALDER_SIGN         = REGISTRY.registerItem("alder_sign",         p -> new SignItem(ModBlocks.ALDER_SIGN.get(), ModBlocks.ALDER_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> ALDER_HANGING_SIGN = REGISTRY.registerItem("alder_hanging_sign", p -> new HangingSignItem(ModBlocks.ALDER_HANGING_SIGN.get(), ModBlocks.ALDER_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> PINE_SIGN         = REGISTRY.registerItem("pine_sign",         p -> new SignItem(ModBlocks.PINE_SIGN.get(), ModBlocks.PINE_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> PINE_HANGING_SIGN = REGISTRY.registerItem("pine_hanging_sign", p -> new HangingSignItem(ModBlocks.PINE_HANGING_SIGN.get(), ModBlocks.PINE_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> FIR_SIGN         = REGISTRY.registerItem("fir_sign",         p -> new SignItem(ModBlocks.FIR_SIGN.get(), ModBlocks.FIR_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> FIR_HANGING_SIGN = REGISTRY.registerItem("fir_hanging_sign", p -> new HangingSignItem(ModBlocks.FIR_HANGING_SIGN.get(), ModBlocks.FIR_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> SENTINAL_SIGN         = REGISTRY.registerItem("sentinal_sign",         p -> new SignItem(ModBlocks.SENTINAL_SIGN.get(), ModBlocks.SENTINAL_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> SENTINAL_HANGING_SIGN = REGISTRY.registerItem("sentinal_hanging_sign", p -> new HangingSignItem(ModBlocks.SENTINAL_HANGING_SIGN.get(), ModBlocks.SENTINAL_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> IRONWOOD_SIGN         = REGISTRY.registerItem("ironwood_sign",         p -> new SignItem(ModBlocks.IRONWOOD_SIGN.get(), ModBlocks.IRONWOOD_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> IRONWOOD_HANGING_SIGN = REGISTRY.registerItem("ironwood_hanging_sign", p -> new HangingSignItem(ModBlocks.IRONWOOD_HANGING_SIGN.get(), ModBlocks.IRONWOOD_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> BEECH_SIGN         = REGISTRY.registerItem("beech_sign",         p -> new SignItem(ModBlocks.BEECH_SIGN.get(), ModBlocks.BEECH_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> BEECH_HANGING_SIGN = REGISTRY.registerItem("beech_hanging_sign", p -> new HangingSignItem(ModBlocks.BEECH_HANGING_SIGN.get(), ModBlocks.BEECH_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> SOLDIER_PINE_SIGN         = REGISTRY.registerItem("soldier_pine_sign",         p -> new SignItem(ModBlocks.SOLDIER_PINE_SIGN.get(), ModBlocks.SOLDIER_PINE_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> SOLDIER_PINE_HANGING_SIGN = REGISTRY.registerItem("soldier_pine_hanging_sign", p -> new HangingSignItem(ModBlocks.SOLDIER_PINE_HANGING_SIGN.get(), ModBlocks.SOLDIER_PINE_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> ASH_SIGN         = REGISTRY.registerItem("ash_sign",         p -> new SignItem(ModBlocks.ASH_SIGN.get(), ModBlocks.ASH_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> ASH_HANGING_SIGN = REGISTRY.registerItem("ash_hanging_sign", p -> new HangingSignItem(ModBlocks.ASH_HANGING_SIGN.get(), ModBlocks.ASH_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> HAWTHORN_SIGN         = REGISTRY.registerItem("hawthorn_sign",         p -> new SignItem(ModBlocks.HAWTHORN_SIGN.get(), ModBlocks.HAWTHORN_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> HAWTHORN_HANGING_SIGN = REGISTRY.registerItem("hawthorn_hanging_sign", p -> new HangingSignItem(ModBlocks.HAWTHORN_HANGING_SIGN.get(), ModBlocks.HAWTHORN_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> BLACKBARK_SIGN         = REGISTRY.registerItem("blackbark_sign",         p -> new SignItem(ModBlocks.BLACKBARK_SIGN.get(), ModBlocks.BLACKBARK_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> BLACKBARK_HANGING_SIGN = REGISTRY.registerItem("blackbark_hanging_sign", p -> new HangingSignItem(ModBlocks.BLACKBARK_HANGING_SIGN.get(), ModBlocks.BLACKBARK_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> BLOODWOOD_SIGN         = REGISTRY.registerItem("bloodwood_sign",         p -> new SignItem(ModBlocks.BLOODWOOD_SIGN.get(), ModBlocks.BLOODWOOD_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> BLOODWOOD_HANGING_SIGN = REGISTRY.registerItem("bloodwood_hanging_sign", p -> new HangingSignItem(ModBlocks.BLOODWOOD_HANGING_SIGN.get(), ModBlocks.BLOODWOOD_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> BLUE_MAHOE_SIGN         = REGISTRY.registerItem("blue_mahoe_sign",         p -> new SignItem(ModBlocks.BLUE_MAHOE_SIGN.get(), ModBlocks.BLUE_MAHOE_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> BLUE_MAHOE_HANGING_SIGN = REGISTRY.registerItem("blue_mahoe_hanging_sign", p -> new HangingSignItem(ModBlocks.BLUE_MAHOE_HANGING_SIGN.get(), ModBlocks.BLUE_MAHOE_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> COTTONWOOD_SIGN         = REGISTRY.registerItem("cottonwood_sign",         p -> new SignItem(ModBlocks.COTTONWOOD_SIGN.get(), ModBlocks.COTTONWOOD_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> COTTONWOOD_HANGING_SIGN = REGISTRY.registerItem("cottonwood_hanging_sign", p -> new HangingSignItem(ModBlocks.COTTONWOOD_HANGING_SIGN.get(), ModBlocks.COTTONWOOD_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> BLACK_COTTONWOOD_SIGN         = REGISTRY.registerItem("black_cottonwood_sign",         p -> new SignItem(ModBlocks.BLACK_COTTONWOOD_SIGN.get(), ModBlocks.BLACK_COTTONWOOD_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> BLACK_COTTONWOOD_HANGING_SIGN = REGISTRY.registerItem("black_cottonwood_hanging_sign", p -> new HangingSignItem(ModBlocks.BLACK_COTTONWOOD_HANGING_SIGN.get(), ModBlocks.BLACK_COTTONWOOD_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> CINNAMON_SIGN         = REGISTRY.registerItem("cinnamon_sign",         p -> new SignItem(ModBlocks.CINNAMON_SIGN.get(), ModBlocks.CINNAMON_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> CINNAMON_HANGING_SIGN = REGISTRY.registerItem("cinnamon_hanging_sign", p -> new HangingSignItem(ModBlocks.CINNAMON_HANGING_SIGN.get(), ModBlocks.CINNAMON_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> CLOVE_SIGN         = REGISTRY.registerItem("clove_sign",         p -> new SignItem(ModBlocks.CLOVE_SIGN.get(), ModBlocks.CLOVE_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> CLOVE_HANGING_SIGN = REGISTRY.registerItem("clove_hanging_sign", p -> new HangingSignItem(ModBlocks.CLOVE_HANGING_SIGN.get(), ModBlocks.CLOVE_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> EBONY_SIGN         = REGISTRY.registerItem("ebony_sign",         p -> new SignItem(ModBlocks.EBONY_SIGN.get(), ModBlocks.EBONY_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> EBONY_HANGING_SIGN = REGISTRY.registerItem("ebony_hanging_sign", p -> new HangingSignItem(ModBlocks.EBONY_HANGING_SIGN.get(), ModBlocks.EBONY_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> ELM_SIGN         = REGISTRY.registerItem("elm_sign",         p -> new SignItem(ModBlocks.ELM_SIGN.get(), ModBlocks.ELM_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> ELM_HANGING_SIGN = REGISTRY.registerItem("elm_hanging_sign", p -> new HangingSignItem(ModBlocks.ELM_HANGING_SIGN.get(), ModBlocks.ELM_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> CEDAR_SIGN         = REGISTRY.registerItem("cedar_sign",         p -> new SignItem(ModBlocks.CEDAR_SIGN.get(), ModBlocks.CEDAR_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> CEDAR_HANGING_SIGN = REGISTRY.registerItem("cedar_hanging_sign", p -> new HangingSignItem(ModBlocks.CEDAR_HANGING_SIGN.get(), ModBlocks.CEDAR_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> APPLE_SIGN         = REGISTRY.registerItem("apple_sign",         p -> new SignItem(ModBlocks.APPLE_SIGN.get(), ModBlocks.APPLE_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> APPLE_HANGING_SIGN = REGISTRY.registerItem("apple_hanging_sign", p -> new HangingSignItem(ModBlocks.APPLE_HANGING_SIGN.get(), ModBlocks.APPLE_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> GOLDENHEART_SIGN         = REGISTRY.registerItem("goldenheart_sign",         p -> new SignItem(ModBlocks.GOLDENHEART_SIGN.get(), ModBlocks.GOLDENHEART_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> GOLDENHEART_HANGING_SIGN = REGISTRY.registerItem("goldenheart_hanging_sign", p -> new HangingSignItem(ModBlocks.GOLDENHEART_HANGING_SIGN.get(), ModBlocks.GOLDENHEART_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> LINDEN_SIGN         = REGISTRY.registerItem("linden_sign",         p -> new SignItem(ModBlocks.LINDEN_SIGN.get(), ModBlocks.LINDEN_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> LINDEN_HANGING_SIGN = REGISTRY.registerItem("linden_hanging_sign", p -> new HangingSignItem(ModBlocks.LINDEN_HANGING_SIGN.get(), ModBlocks.LINDEN_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> MAHOGANY_SIGN         = REGISTRY.registerItem("mahogany_sign",         p -> new SignItem(ModBlocks.MAHOGANY_SIGN.get(), ModBlocks.MAHOGANY_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> MAHOGANY_HANGING_SIGN = REGISTRY.registerItem("mahogany_hanging_sign", p -> new HangingSignItem(ModBlocks.MAHOGANY_HANGING_SIGN.get(), ModBlocks.MAHOGANY_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> MAPLE_SIGN         = REGISTRY.registerItem("maple_sign",         p -> new SignItem(ModBlocks.MAPLE_SIGN.get(), ModBlocks.MAPLE_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> MAPLE_HANGING_SIGN = REGISTRY.registerItem("maple_hanging_sign", p -> new HangingSignItem(ModBlocks.MAPLE_HANGING_SIGN.get(), ModBlocks.MAPLE_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> MYRRH_SIGN         = REGISTRY.registerItem("myrrh_sign",         p -> new SignItem(ModBlocks.MYRRH_SIGN.get(), ModBlocks.MYRRH_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> MYRRH_HANGING_SIGN = REGISTRY.registerItem("myrrh_hanging_sign", p -> new HangingSignItem(ModBlocks.MYRRH_HANGING_SIGN.get(), ModBlocks.MYRRH_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> REDWOOD_SIGN         = REGISTRY.registerItem("redwood_sign",         p -> new SignItem(ModBlocks.REDWOOD_SIGN.get(), ModBlocks.REDWOOD_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> REDWOOD_HANGING_SIGN = REGISTRY.registerItem("redwood_hanging_sign", p -> new HangingSignItem(ModBlocks.REDWOOD_HANGING_SIGN.get(), ModBlocks.REDWOOD_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> CHESTNUT_SIGN         = REGISTRY.registerItem("chestnut_sign",         p -> new SignItem(ModBlocks.CHESTNUT_SIGN.get(), ModBlocks.CHESTNUT_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> CHESTNUT_HANGING_SIGN = REGISTRY.registerItem("chestnut_hanging_sign", p -> new HangingSignItem(ModBlocks.CHESTNUT_HANGING_SIGN.get(), ModBlocks.CHESTNUT_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> WILLOW_SIGN         = REGISTRY.registerItem("willow_sign",         p -> new SignItem(ModBlocks.WILLOW_SIGN.get(), ModBlocks.WILLOW_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> WILLOW_HANGING_SIGN = REGISTRY.registerItem("willow_hanging_sign", p -> new HangingSignItem(ModBlocks.WILLOW_HANGING_SIGN.get(), ModBlocks.WILLOW_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> WORMTREE_SIGN         = REGISTRY.registerItem("wormtree_sign",         p -> new SignItem(ModBlocks.WORMTREE_SIGN.get(), ModBlocks.WORMTREE_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> WORMTREE_HANGING_SIGN = REGISTRY.registerItem("wormtree_hanging_sign", p -> new HangingSignItem(ModBlocks.WORMTREE_HANGING_SIGN.get(), ModBlocks.WORMTREE_WALL_HANGING_SIGN.get(), p));

    public static final DeferredItem<Item> WEIRWOOD_BOAT       = REGISTRY.registerItem("weirwood_boat",       p -> new GotBoatItem(ModBoatEntities.WEIRWOOD_BOAT.get(), p));
    public static final DeferredItem<Item> WEIRWOOD_CHEST_BOAT = REGISTRY.registerItem("weirwood_chest_boat", p -> new GotBoatItem(ModBoatEntities.WEIRWOOD_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> ASPEN_BOAT       = REGISTRY.registerItem("aspen_boat",       p -> new GotBoatItem(ModBoatEntities.ASPEN_BOAT.get(), p));
    public static final DeferredItem<Item> ASPEN_CHEST_BOAT = REGISTRY.registerItem("aspen_chest_boat", p -> new GotBoatItem(ModBoatEntities.ASPEN_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> ALDER_BOAT       = REGISTRY.registerItem("alder_boat",       p -> new GotBoatItem(ModBoatEntities.ALDER_BOAT.get(), p));
    public static final DeferredItem<Item> ALDER_CHEST_BOAT = REGISTRY.registerItem("alder_chest_boat", p -> new GotBoatItem(ModBoatEntities.ALDER_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> PINE_BOAT       = REGISTRY.registerItem("pine_boat",       p -> new GotBoatItem(ModBoatEntities.PINE_BOAT.get(), p));
    public static final DeferredItem<Item> PINE_CHEST_BOAT = REGISTRY.registerItem("pine_chest_boat", p -> new GotBoatItem(ModBoatEntities.PINE_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> FIR_BOAT       = REGISTRY.registerItem("fir_boat",       p -> new GotBoatItem(ModBoatEntities.FIR_BOAT.get(), p));
    public static final DeferredItem<Item> FIR_CHEST_BOAT = REGISTRY.registerItem("fir_chest_boat", p -> new GotBoatItem(ModBoatEntities.FIR_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> SENTINAL_BOAT       = REGISTRY.registerItem("sentinal_boat",       p -> new GotBoatItem(ModBoatEntities.SENTINAL_BOAT.get(), p));
    public static final DeferredItem<Item> SENTINAL_CHEST_BOAT = REGISTRY.registerItem("sentinal_chest_boat", p -> new GotBoatItem(ModBoatEntities.SENTINAL_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> IRONWOOD_BOAT       = REGISTRY.registerItem("ironwood_boat",       p -> new GotBoatItem(ModBoatEntities.IRONWOOD_BOAT.get(), p));
    public static final DeferredItem<Item> IRONWOOD_CHEST_BOAT = REGISTRY.registerItem("ironwood_chest_boat", p -> new GotBoatItem(ModBoatEntities.IRONWOOD_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> BEECH_BOAT       = REGISTRY.registerItem("beech_boat",       p -> new GotBoatItem(ModBoatEntities.BEECH_BOAT.get(), p));
    public static final DeferredItem<Item> BEECH_CHEST_BOAT = REGISTRY.registerItem("beech_chest_boat", p -> new GotBoatItem(ModBoatEntities.BEECH_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> SOLDIER_PINE_BOAT       = REGISTRY.registerItem("soldier_pine_boat",       p -> new GotBoatItem(ModBoatEntities.SOLDIER_PINE_BOAT.get(), p));
    public static final DeferredItem<Item> SOLDIER_PINE_CHEST_BOAT = REGISTRY.registerItem("soldier_pine_chest_boat", p -> new GotBoatItem(ModBoatEntities.SOLDIER_PINE_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> ASH_BOAT       = REGISTRY.registerItem("ash_boat",       p -> new GotBoatItem(ModBoatEntities.ASH_BOAT.get(), p));
    public static final DeferredItem<Item> ASH_CHEST_BOAT = REGISTRY.registerItem("ash_chest_boat", p -> new GotBoatItem(ModBoatEntities.ASH_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> HAWTHORN_BOAT       = REGISTRY.registerItem("hawthorn_boat",       p -> new GotBoatItem(ModBoatEntities.HAWTHORN_BOAT.get(), p));
    public static final DeferredItem<Item> HAWTHORN_CHEST_BOAT = REGISTRY.registerItem("hawthorn_chest_boat", p -> new GotBoatItem(ModBoatEntities.HAWTHORN_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> BLACKBARK_BOAT       = REGISTRY.registerItem("blackbark_boat",       p -> new GotBoatItem(ModBoatEntities.BLACKBARK_BOAT.get(), p));
    public static final DeferredItem<Item> BLACKBARK_CHEST_BOAT = REGISTRY.registerItem("blackbark_chest_boat", p -> new GotBoatItem(ModBoatEntities.BLACKBARK_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> BLOODWOOD_BOAT       = REGISTRY.registerItem("bloodwood_boat",       p -> new GotBoatItem(ModBoatEntities.BLOODWOOD_BOAT.get(), p));
    public static final DeferredItem<Item> BLOODWOOD_CHEST_BOAT = REGISTRY.registerItem("bloodwood_chest_boat", p -> new GotBoatItem(ModBoatEntities.BLOODWOOD_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> BLUE_MAHOE_BOAT       = REGISTRY.registerItem("blue_mahoe_boat",       p -> new GotBoatItem(ModBoatEntities.BLUE_MAHOE_BOAT.get(), p));
    public static final DeferredItem<Item> BLUE_MAHOE_CHEST_BOAT = REGISTRY.registerItem("blue_mahoe_chest_boat", p -> new GotBoatItem(ModBoatEntities.BLUE_MAHOE_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> COTTONWOOD_BOAT       = REGISTRY.registerItem("cottonwood_boat",       p -> new GotBoatItem(ModBoatEntities.COTTONWOOD_BOAT.get(), p));
    public static final DeferredItem<Item> COTTONWOOD_CHEST_BOAT = REGISTRY.registerItem("cottonwood_chest_boat", p -> new GotBoatItem(ModBoatEntities.COTTONWOOD_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> BLACK_COTTONWOOD_BOAT       = REGISTRY.registerItem("black_cottonwood_boat",       p -> new GotBoatItem(ModBoatEntities.BLACK_COTTONWOOD_BOAT.get(), p));
    public static final DeferredItem<Item> BLACK_COTTONWOOD_CHEST_BOAT = REGISTRY.registerItem("black_cottonwood_chest_boat", p -> new GotBoatItem(ModBoatEntities.BLACK_COTTONWOOD_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> CINNAMON_BOAT       = REGISTRY.registerItem("cinnamon_boat",       p -> new GotBoatItem(ModBoatEntities.CINNAMON_BOAT.get(), p));
    public static final DeferredItem<Item> CINNAMON_CHEST_BOAT = REGISTRY.registerItem("cinnamon_chest_boat", p -> new GotBoatItem(ModBoatEntities.CINNAMON_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> CLOVE_BOAT       = REGISTRY.registerItem("clove_boat",       p -> new GotBoatItem(ModBoatEntities.CLOVE_BOAT.get(), p));
    public static final DeferredItem<Item> CLOVE_CHEST_BOAT = REGISTRY.registerItem("clove_chest_boat", p -> new GotBoatItem(ModBoatEntities.CLOVE_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> EBONY_BOAT       = REGISTRY.registerItem("ebony_boat",       p -> new GotBoatItem(ModBoatEntities.EBONY_BOAT.get(), p));
    public static final DeferredItem<Item> EBONY_CHEST_BOAT = REGISTRY.registerItem("ebony_chest_boat", p -> new GotBoatItem(ModBoatEntities.EBONY_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> ELM_BOAT       = REGISTRY.registerItem("elm_boat",       p -> new GotBoatItem(ModBoatEntities.ELM_BOAT.get(), p));
    public static final DeferredItem<Item> ELM_CHEST_BOAT = REGISTRY.registerItem("elm_chest_boat", p -> new GotBoatItem(ModBoatEntities.ELM_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> CEDAR_BOAT       = REGISTRY.registerItem("cedar_boat",       p -> new GotBoatItem(ModBoatEntities.CEDAR_BOAT.get(), p));
    public static final DeferredItem<Item> CEDAR_CHEST_BOAT = REGISTRY.registerItem("cedar_chest_boat", p -> new GotBoatItem(ModBoatEntities.CEDAR_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> APPLE_BOAT       = REGISTRY.registerItem("apple_boat",       p -> new GotBoatItem(ModBoatEntities.APPLE_BOAT.get(), p));
    public static final DeferredItem<Item> APPLE_CHEST_BOAT = REGISTRY.registerItem("apple_chest_boat", p -> new GotBoatItem(ModBoatEntities.APPLE_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> GOLDENHEART_BOAT       = REGISTRY.registerItem("goldenheart_boat",       p -> new GotBoatItem(ModBoatEntities.GOLDENHEART_BOAT.get(), p));
    public static final DeferredItem<Item> GOLDENHEART_CHEST_BOAT = REGISTRY.registerItem("goldenheart_chest_boat", p -> new GotBoatItem(ModBoatEntities.GOLDENHEART_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> LINDEN_BOAT       = REGISTRY.registerItem("linden_boat",       p -> new GotBoatItem(ModBoatEntities.LINDEN_BOAT.get(), p));
    public static final DeferredItem<Item> LINDEN_CHEST_BOAT = REGISTRY.registerItem("linden_chest_boat", p -> new GotBoatItem(ModBoatEntities.LINDEN_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> MAHOGANY_BOAT       = REGISTRY.registerItem("mahogany_boat",       p -> new GotBoatItem(ModBoatEntities.MAHOGANY_BOAT.get(), p));
    public static final DeferredItem<Item> MAHOGANY_CHEST_BOAT = REGISTRY.registerItem("mahogany_chest_boat", p -> new GotBoatItem(ModBoatEntities.MAHOGANY_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> MAPLE_BOAT       = REGISTRY.registerItem("maple_boat",       p -> new GotBoatItem(ModBoatEntities.MAPLE_BOAT.get(), p));
    public static final DeferredItem<Item> MAPLE_CHEST_BOAT = REGISTRY.registerItem("maple_chest_boat", p -> new GotBoatItem(ModBoatEntities.MAPLE_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> MYRRH_BOAT       = REGISTRY.registerItem("myrrh_boat",       p -> new GotBoatItem(ModBoatEntities.MYRRH_BOAT.get(), p));
    public static final DeferredItem<Item> MYRRH_CHEST_BOAT = REGISTRY.registerItem("myrrh_chest_boat", p -> new GotBoatItem(ModBoatEntities.MYRRH_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> REDWOOD_BOAT       = REGISTRY.registerItem("redwood_boat",       p -> new GotBoatItem(ModBoatEntities.REDWOOD_BOAT.get(), p));
    public static final DeferredItem<Item> REDWOOD_CHEST_BOAT = REGISTRY.registerItem("redwood_chest_boat", p -> new GotBoatItem(ModBoatEntities.REDWOOD_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> CHESTNUT_BOAT       = REGISTRY.registerItem("chestnut_boat",       p -> new GotBoatItem(ModBoatEntities.CHESTNUT_BOAT.get(), p));
    public static final DeferredItem<Item> CHESTNUT_CHEST_BOAT = REGISTRY.registerItem("chestnut_chest_boat", p -> new GotBoatItem(ModBoatEntities.CHESTNUT_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> WILLOW_BOAT       = REGISTRY.registerItem("willow_boat",       p -> new GotBoatItem(ModBoatEntities.WILLOW_BOAT.get(), p));
    public static final DeferredItem<Item> WILLOW_CHEST_BOAT = REGISTRY.registerItem("willow_chest_boat", p -> new GotBoatItem(ModBoatEntities.WILLOW_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> WORMTREE_BOAT       = REGISTRY.registerItem("wormtree_boat",       p -> new GotBoatItem(ModBoatEntities.WORMTREE_BOAT.get(), p));
    public static final DeferredItem<Item> WORMTREE_CHEST_BOAT = REGISTRY.registerItem("wormtree_chest_boat", p -> new GotBoatItem(ModBoatEntities.WORMTREE_CHEST_BOAT.get(), p));

    public static final DeferredItem<Item> WEIRWOOD_SAPLING = block(ModBlocks.WEIRWOOD_SAPLING);
    public static final DeferredItem<Item> ASPEN_SAPLING = block(ModBlocks.ASPEN_SAPLING);
    public static final DeferredItem<Item> ALDER_SAPLING = block(ModBlocks.ALDER_SAPLING);
    public static final DeferredItem<Item> PINE_SAPLING = block(ModBlocks.PINE_SAPLING);
    public static final DeferredItem<Item> FIR_SAPLING = block(ModBlocks.FIR_SAPLING);
    public static final DeferredItem<Item> SENTINAL_SAPLING = block(ModBlocks.SENTINAL_SAPLING);
    public static final DeferredItem<Item> IRONWOOD_SAPLING = block(ModBlocks.IRONWOOD_SAPLING);
    public static final DeferredItem<Item> BEECH_SAPLING = block(ModBlocks.BEECH_SAPLING);
    public static final DeferredItem<Item> SOLDIER_PINE_SAPLING = block(ModBlocks.SOLDIER_PINE_SAPLING);
    public static final DeferredItem<Item> ASH_SAPLING = block(ModBlocks.ASH_SAPLING);
    public static final DeferredItem<Item> HAWTHORN_SAPLING = block(ModBlocks.HAWTHORN_SAPLING);
    public static final DeferredItem<Item> BLACKBARK_SAPLING = block(ModBlocks.BLACKBARK_SAPLING);
    public static final DeferredItem<Item> BLOODWOOD_SAPLING = block(ModBlocks.BLOODWOOD_SAPLING);
    public static final DeferredItem<Item> BLUE_MAHOE_SAPLING = block(ModBlocks.BLUE_MAHOE_SAPLING);
    public static final DeferredItem<Item> COTTONWOOD_SAPLING = block(ModBlocks.COTTONWOOD_SAPLING);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_SAPLING = block(ModBlocks.BLACK_COTTONWOOD_SAPLING);
    public static final DeferredItem<Item> CINNAMON_SAPLING = block(ModBlocks.CINNAMON_SAPLING);
    public static final DeferredItem<Item> CLOVE_SAPLING = block(ModBlocks.CLOVE_SAPLING);
    public static final DeferredItem<Item> EBONY_SAPLING = block(ModBlocks.EBONY_SAPLING);
    public static final DeferredItem<Item> ELM_SAPLING = block(ModBlocks.ELM_SAPLING);
    public static final DeferredItem<Item> CEDAR_SAPLING = block(ModBlocks.CEDAR_SAPLING);
    public static final DeferredItem<Item> APPLE_SAPLING = block(ModBlocks.APPLE_SAPLING);
    public static final DeferredItem<Item> GOLDENHEART_SAPLING = block(ModBlocks.GOLDENHEART_SAPLING);
    public static final DeferredItem<Item> LINDEN_SAPLING = block(ModBlocks.LINDEN_SAPLING);
    public static final DeferredItem<Item> MAHOGANY_SAPLING = block(ModBlocks.MAHOGANY_SAPLING);
    public static final DeferredItem<Item> MAPLE_SAPLING = block(ModBlocks.MAPLE_SAPLING);
    public static final DeferredItem<Item> MYRRH_SAPLING = block(ModBlocks.MYRRH_SAPLING);
    public static final DeferredItem<Item> REDWOOD_SAPLING = block(ModBlocks.REDWOOD_SAPLING);
    public static final DeferredItem<Item> CHESTNUT_SAPLING = block(ModBlocks.CHESTNUT_SAPLING);
    public static final DeferredItem<Item> WILLOW_SAPLING = block(ModBlocks.WILLOW_SAPLING);
    public static final DeferredItem<Item> WORMTREE_SAPLING = block(ModBlocks.WORMTREE_SAPLING);

    public static final DeferredItem<Item> BELLFLOWER         = block(ModBlocks.BELLFLOWER);
    public static final DeferredItem<Item> BLACK_LOTUS        = block(ModBlocks.BLACK_LOTUS);
    public static final DeferredItem<Item> BLOOD_BLOOM        = block(ModBlocks.BLOOD_BLOOM);
    public static final DeferredItem<Item> COLDSNAPS          = block(ModBlocks.COLDSNAPS);
    public static final DeferredItem<Item> DRAGONS_BREATH     = block(ModBlocks.DRAGONS_BREATH);
    public static final DeferredItem<Item> EVENING_STAR       = block(ModBlocks.EVENING_STAR);
    public static final DeferredItem<Item> FORGET_ME_NOT      = block(ModBlocks.FORGET_ME_NOT);
    public static final DeferredItem<Item> FROSTFIRES         = block(ModBlocks.FROSTFIRES);
    public static final DeferredItem<Item> GILLYFLOWER        = block(ModBlocks.GILLYFLOWER);
    public static final DeferredItem<Item> GINGER             = block(ModBlocks.GINGER);
    public static final DeferredItem<Item> GOATHEAD           = block(ModBlocks.GOATHEAD);
    public static final DeferredItem<Item> GOLDENCUP          = block(ModBlocks.GOLDENCUP);
    public static final DeferredItem<Item> GOLDENROD          = block(ModBlocks.GOLDENROD);
    public static final DeferredItem<Item> GORSE              = block(ModBlocks.GORSE);
    public static final DeferredItem<Item> LADYS_LACE         = block(ModBlocks.LADYS_LACE);
    public static final DeferredItem<Item> LAVENDER           = block(ModBlocks.LAVENDER);
    public static final DeferredItem<Item> LIVERWORT          = block(ModBlocks.LIVERWORT);
    public static final DeferredItem<Item> LUNGWORT           = block(ModBlocks.LUNGWORT);
    public static final DeferredItem<Item> MOONBLOOM          = block(ModBlocks.MOONBLOOM);
    public static final DeferredItem<Item> NIGHTSHADE         = block(ModBlocks.NIGHTSHADE);
    public static final DeferredItem<Item> PENNYROYAL         = block(ModBlocks.PENNYROYAL);
    public static final DeferredItem<Item> POISON_KISSES      = block(ModBlocks.POISON_KISSES);
    public static final DeferredItem<Item> THORNBUSH          = block(ModBlocks.THORNBUSH);
    public static final DeferredItem<Item> OPIUM_POPPY        = block(ModBlocks.OPIUM_POPPY);
    public static final DeferredItem<Item> GOLDEN_ROSE        = block(ModBlocks.GOLDEN_ROSE);
    public static final DeferredItem<Item> RED_ROSE           = block(ModBlocks.RED_ROSE);
    public static final DeferredItem<Item> WHITE_ROSE         = block(ModBlocks.WHITE_ROSE);
    public static final DeferredItem<Item> WINTER_ROSE        = block(ModBlocks.WINTER_ROSE);
    public static final DeferredItem<Item> SAFFRON_CROCUS     = block(ModBlocks.SAFFRON_CROCUS);
    public static final DeferredItem<Item> SEDGE              = block(ModBlocks.SEDGE);
    public static final DeferredItem<Item> SPICEFLOWER        = block(ModBlocks.SPICEFLOWER);
    public static final DeferredItem<Item> TANSY              = block(ModBlocks.TANSY);
    public static final DeferredItem<Item> THISTLE            = block(ModBlocks.THISTLE);
    public static final DeferredItem<Item> WILD_RADISH        = block(ModBlocks.WILD_RADISH);

    public static final DeferredItem<BlockItem> RED_ROSE_BUSH   = REGISTRY.registerSimpleBlockItem("red_rose_bush",   ModBlocks.RED_ROSE_BUSH);
    public static final DeferredItem<BlockItem> GOLDEN_ROSE_BUSH = REGISTRY.registerSimpleBlockItem("golden_rose_bush", ModBlocks.GOLDEN_ROSE_BUSH);
    public static final DeferredItem<BlockItem> WHITE_ROSE_BUSH  = REGISTRY.registerSimpleBlockItem("white_rose_bush",  ModBlocks.WHITE_ROSE_BUSH);
    public static final DeferredItem<BlockItem> WINTER_ROSE_BUSH = REGISTRY.registerSimpleBlockItem("winter_rose_bush", ModBlocks.WINTER_ROSE_BUSH);

    public static final DeferredItem<Item> COIN_HALFPENNY = simple("coin_halfpenny");
    public static final DeferredItem<Item> COIN_PENNY     = simple("coin_penny");
    public static final DeferredItem<Item> COIN_HALFGROAT = simple("coin_halfgroat");
    public static final DeferredItem<Item> COIN_GROAT     = simple("coin_groat");
    public static final DeferredItem<Item> COIN_STAR      = simple("coin_star");
    public static final DeferredItem<Item> COIN_STAG      = simple("coin_stag");
    public static final DeferredItem<Item> COIN_MOON      = simple("coin_moon");
    public static final DeferredItem<Item> COIN_DRAGON    = simple("coin_dragon");

    public static final DeferredItem<Item> DEVILGRASS         = block(ModBlocks.DEVILGRASS);
    public static final DeferredItem<Item> GHOST_GRASS        = block(ModBlocks.GHOST_GRASS);
    public static final DeferredItem<Item> HRANNA             = block(ModBlocks.HRANNA);
    public static final DeferredItem<Item> PIPERS_GRASS       = block(ModBlocks.PIPERS_GRASS);
    public static final DeferredItem<Item> WHEATGRASS       = block(ModBlocks.WHEATGRASS);

    public static final DeferredItem<Item> WILD_WHEAT = block(ModBlocks.WILD_WHEAT);
    public static final DeferredItem<Item> WILD_OAT = block(ModBlocks.WILD_OAT);
    public static final DeferredItem<Item> WILD_RYE = block(ModBlocks.WILD_RYE);
    public static final DeferredItem<Item> WILD_BARLEY = block(ModBlocks.WILD_BARLEY);
    public static final DeferredItem<Item> WILD_BEETROOT = block(ModBlocks.WILD_BEETROOT);
    public static final DeferredItem<Item> WILD_COTTON = block(ModBlocks.WILD_COTTON);
    public static final DeferredItem<Item> WILD_PEPPERCORN = block(ModBlocks.WILD_PEPPERCORN);
    public static final DeferredItem<Item> WILD_CARROT = block(ModBlocks.WILD_CARROT);
    public static final DeferredItem<Item> WILD_PARSNIP = block(ModBlocks.WILD_PARSNIP);
    public static final DeferredItem<Item> WILD_ONION = block(ModBlocks.WILD_ONION);
    public static final DeferredItem<Item> WILD_TURNIP = block(ModBlocks.WILD_TURNIP);
    public static final DeferredItem<Item> WILD_NEEP = block(ModBlocks.WILD_NEEP);
    public static final DeferredItem<Item> WILD_PEAS = block(ModBlocks.WILD_PEAS);
    public static final DeferredItem<Item> WILD_CABBAGE = block(ModBlocks.WILD_CABBAGE);
    public static final DeferredItem<Item> WILD_GARLIC = block(ModBlocks.WILD_GARLIC);
    public static final DeferredItem<Item> WILD_HORSERADISH = block(ModBlocks.WILD_HORSERADISH);
    public static final DeferredItem<Item> WILD_LEEK = block(ModBlocks.WILD_LEEK);

    public static final DeferredItem<Item> QUAGMIRE = block(ModBlocks.QUAGMIRE);

    public static final DeferredItem<Item> REEDS = block(ModBlocks.REEDS);

    public static final DeferredItem<BlockItem> SHORT_REEDS = REGISTRY.registerSimpleBlockItem(
            "short_reeds", ModBlocks.SHORT_REEDS);

    public static final DeferredItem<BlockItem> RUSHES = REGISTRY.registerSimpleBlockItem(
            "rushes", ModBlocks.RUSHES);

    public static final DeferredItem<Item> OAT_SEEDS     = REGISTRY.registerItem("oat_seeds",     p -> new BlockItem(ModBlocks.OAT_CROP.get(),      p));
    public static final DeferredItem<Item> RYE_SEEDS     = REGISTRY.registerItem("rye_seeds",     p -> new BlockItem(ModBlocks.RYE_CROP.get(),      p));
    public static final DeferredItem<Item> BARLEY_SEEDS  = REGISTRY.registerItem("barley_seeds",  p -> new BlockItem(ModBlocks.BARLEY_CROP.get(),   p));
    public static final DeferredItem<Item> COTTON_SEEDS      = REGISTRY.registerItem("cotton_seeds",      p -> new BlockItem(ModBlocks.COTTON_CROP.get(),     p));
    public static final DeferredItem<Item> PEPPERCORN_SEEDS  = REGISTRY.registerItem("peppercorn_seeds",  p -> new BlockItem(ModBlocks.PEPPERCORN_CROP.get(), p));

    public static final DeferredItem<Item> CARDAMOM_SEEDS      = REGISTRY.registerItem("cardamom_seeds",      p -> new BlockItem(ModBlocks.CARDAMOM_CROP.get(),      p));
    public static final DeferredItem<Item> CHICKPEA_SEEDS      = REGISTRY.registerItem("chickpea_seeds",      p -> new BlockItem(ModBlocks.CHICKPEA_CROP.get(),      p));
    public static final DeferredItem<Item> CORN_SEEDS          = REGISTRY.registerItem("corn_seeds",          p -> new BlockItem(ModBlocks.CORN_CROP.get(),          p));
    public static final DeferredItem<Item> CUCUMBER_SEEDS      = REGISTRY.registerItem("cucumber_seeds",      p -> new BlockItem(ModBlocks.CUCUMBER_CROP.get(),      p));
    public static final DeferredItem<Item> HEMP_SEEDS          = REGISTRY.registerItem("hemp_seeds",          p -> new BlockItem(ModBlocks.HEMP_CROP.get(),          p));
    public static final DeferredItem<Item> LICORICE_SEEDS      = REGISTRY.registerItem("licorice_seeds",      p -> new BlockItem(ModBlocks.LICORICE_CROP.get(),      p));
    public static final DeferredItem<Item> MUSTARD_PLANT_SEEDS = REGISTRY.registerItem("mustard_plant_seeds", p -> new BlockItem(ModBlocks.MUSTARD_PLANT_CROP.get(), p));
    public static final DeferredItem<Item> PEPPER_PLANT_SEEDS  = REGISTRY.registerItem("pepper_plant_seeds",  p -> new BlockItem(ModBlocks.PEPPER_PLANT_CROP.get(),  p));

    public static final DeferredItem<Item> OAT        = simple("oat");
    public static final DeferredItem<Item> RYE        = simple("rye");
    public static final DeferredItem<Item> BARLEY     = simple("barley");
    public static final DeferredItem<Item> COTTON     = simple("cotton");
    public static final DeferredItem<Item> PEPPERCORN = simple("peppercorn");

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

    public static final DeferredItem<Item> PARSNIP     = REGISTRY.registerItem("parsnip",     p -> new BlockItem(ModBlocks.PARSNIP_CROP.get(),     p), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.6f).build()));
    public static final DeferredItem<Item> ONION       = REGISTRY.registerItem("onion",       p -> new BlockItem(ModBlocks.ONION_CROP.get(),       p), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.5f).build()));
    public static final DeferredItem<Item> TURNIP      = REGISTRY.registerItem("turnip",      p -> new BlockItem(ModBlocks.TURNIP_CROP.get(),      p), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.5f).build()));
    public static final DeferredItem<Item> PEAS        = REGISTRY.registerItem("peas",        p -> new BlockItem(ModBlocks.PEAS_CROP.get(),        p), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.6f).build()));
    public static final DeferredItem<Item> CABBAGE_PLANT_SEEDS = REGISTRY.registerItem("cabbage_plant_seeds", p -> new BlockItem(ModBlocks.CABBAGE_CROP.get(), p));
    public static final DeferredItem<Item> CABBAGE     = REGISTRY.registerItem("cabbage",     p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.6f).build()));
    public static final DeferredItem<Item> GARLIC      = REGISTRY.registerItem("garlic",      p -> new BlockItem(ModBlocks.GARLIC_CROP.get(),      p), new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.3f).build()));
    public static final DeferredItem<Item> NEEP        = REGISTRY.registerItem("neep",        p -> new BlockItem(ModBlocks.NEEP_CROP.get(),        p), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.5f).build()));
    public static final DeferredItem<Item> HORSERADISH = REGISTRY.registerItem("horseradish", p -> new BlockItem(ModBlocks.HORSERADISH_CROP.get(), p), new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.3f).build()));
    public static final DeferredItem<Item> LEEK        = REGISTRY.registerItem("leek",        p -> new BlockItem(ModBlocks.LEEK_CROP.get(),        p), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.5f).build()));

    public static final DeferredItem<Item> WILD_BEAN          = block(ModBlocks.WILD_BEAN);
    public static final DeferredItem<Item> BEAN               = REGISTRY.registerItem("bean",          p -> new BlockItem(ModBlocks.BEAN_CROP.get(), p), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.5f).build()));

    public static final DeferredItem<Item> BRACKEN            = block(ModBlocks.BRACKEN);
    public static final DeferredItem<Item> BRIAR              = block(ModBlocks.BRIAR);
    public static final DeferredItem<Item> BROOM              = block(ModBlocks.BROOM);

    public static final DeferredItem<Item> WILD_CARDAMOM      = block(ModBlocks.WILD_CARDAMOM);
    public static final DeferredItem<Item> CARDAMOM           = REGISTRY.registerItem("cardamom",      p -> new Item(p));
    public static final DeferredItem<Item> WILD_CHICKPEA      = block(ModBlocks.WILD_CHICKPEA);
    public static final DeferredItem<Item> CHICKPEA           = REGISTRY.registerItem("chickpea",      p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.5f).build()));
    public static final DeferredItem<Item> CORN_ON_THE_COB    = REGISTRY.registerItem("corn_on_the_cob", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(5).saturationModifier(0.6f).build()));
    public static final DeferredItem<Item> WILD_CUCUMBER      = block(ModBlocks.WILD_CUCUMBER);
    public static final DeferredItem<Item> CUCUMBER           = REGISTRY.registerItem("cucumber",      p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.4f).build()));
    public static final DeferredItem<Item> DAGGERLEAF         = block(ModBlocks.DAGGERLEAF);
    public static final DeferredItem<Item> FIREPOD            = block(ModBlocks.FIREPOD);
    public static final DeferredItem<Item> GHOSTSKIN          = block(ModBlocks.GHOSTSKIN);
    public static final DeferredItem<Item> GRAPE_VINE         = block(ModBlocks.GRAPE_VINE);
    public static final DeferredItem<Item> HARPYS_GOLD        = block(ModBlocks.HARPYS_GOLD);
    public static final DeferredItem<Item> WILD_HEMP          = block(ModBlocks.WILD_HEMP);
    public static final DeferredItem<Item> HEMP               = REGISTRY.registerItem("hemp",          p -> new Item(p));
    public static final DeferredItem<Item> HORNWORT           = block(ModBlocks.HORNWORT);
    public static final DeferredItem<Item> IVY                = block(ModBlocks.IVY);
    public static final DeferredItem<Item> KINGSCOPPER        = block(ModBlocks.KINGSCOPPER);
    public static final DeferredItem<Item> WILD_LICORICE      = block(ModBlocks.WILD_LICORICE);
    public static final DeferredItem<Item> LICORICE           = REGISTRY.registerItem("licorice",      p -> new Item(p));
    public static final DeferredItem<Item> MISTLETOE          = block(ModBlocks.MISTLETOE);
    public static final DeferredItem<Item> WILD_MUSTARD_PLANT = block(ModBlocks.WILD_MUSTARD_PLANT);
    public static final DeferredItem<Item> NETTLE             = block(ModBlocks.NETTLE);
    public static final DeferredItem<Item> WILD_PEPPER_PLANT  = block(ModBlocks.WILD_PEPPER_PLANT);
    public static final DeferredItem<Item> PEPPER_PLANT       = REGISTRY.registerItem("pepper_plant",  p -> new Item(p));
    public static final DeferredItem<Item> PINCHFIRE          = block(ModBlocks.PINCHFIRE);
    public static final DeferredItem<Item> PRICKLY_BEN        = block(ModBlocks.PRICKLY_BEN);
    public static final DeferredItem<Item> SANDWILLOW         = block(ModBlocks.SANDWILLOW);
    public static final DeferredItem<Item> SMOKEBERRY_BUSH    = block(ModBlocks.SMOKEBERRY_BUSH);
    public static final DeferredItem<Item> SMOKEBERRIES       = REGISTRY.registerItem("smokeberries",  p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.4f).build()));
    public static final DeferredItem<Item> SOURLEAF           = block(ModBlocks.SOURLEAF);
    public static final DeferredItem<Item> STING_ME_NOT       = block(ModBlocks.STING_ME_NOT);
    public static final DeferredItem<Item> WASPWILLOW         = block(ModBlocks.WASPWILLOW);

    public static final DeferredItem<Item> BLACKBERRIES = REGISTRY.registerItem("blackberries", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.4f).build()));
    public static final DeferredItem<Item> BLUEBERRIES  = REGISTRY.registerItem("blueberries", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.4f).build()));
    public static final DeferredItem<Item> RASPBERRIES  = REGISTRY.registerItem("raspberries", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.4f).build()));
    public static final DeferredItem<Item> STRAWBERRIES = REGISTRY.registerItem("strawberries", p -> new BlockItem(ModBlocks.STRAWBERRY_CROP.get(), p), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.5f).build()));

    public static final DeferredItem<Item> BLACKBERRY_BUSH  = block(ModBlocks.BLACKBERRY_BUSH);
    public static final DeferredItem<Item> BLUEBERRY_BUSH   = block(ModBlocks.BLUEBERRY_BUSH);
    public static final DeferredItem<Item> RASPBERRY_BUSH   = block(ModBlocks.RASPBERRY_BUSH);
    public static final DeferredItem<Item> WILD_STRAWBERRY  = block(ModBlocks.WILD_STRAWBERRY);

    public static final DeferredItem<Item> BRONZE_PICKAXE = REGISTRY.registerItem("bronze_pickaxe",
            p -> new Item(p.pickaxe(ModTiers.BRONZE, 1.0f, -2.8f)));
    public static final DeferredItem<AxeItem>    BRONZE_AXE      = REGISTRY.registerItem("bronze_axe",
            p -> new AxeItem(ModTiers.BRONZE, 7.0f, -3.1f, p));
    public static final DeferredItem<ShovelItem> BRONZE_SHOVEL   = REGISTRY.registerItem("bronze_shovel",
            p -> new ShovelItem(ModTiers.BRONZE, 1.5f, -3.0f, p));
    public static final DeferredItem<HoeItem>    BRONZE_HOE      = REGISTRY.registerItem("bronze_hoe",
            p -> new HoeItem(ModTiers.BRONZE, 0.0f, -3.0f, p));

    public static final DeferredItem<Item> STEEL_PICKAXE  = REGISTRY.registerItem("steel_pickaxe",
            p -> new Item(p.pickaxe(ModTiers.STEEL, 1.0f, -2.8f)));
    public static final DeferredItem<AxeItem>    STEEL_AXE       = REGISTRY.registerItem("steel_axe",
            p -> new AxeItem(ModTiers.STEEL, 6.0f, -3.1f, p));
    public static final DeferredItem<ShovelItem> STEEL_SHOVEL    = REGISTRY.registerItem("steel_shovel",
            p -> new ShovelItem(ModTiers.STEEL, 1.5f, -3.0f, p));
    public static final DeferredItem<HoeItem>    STEEL_HOE       = REGISTRY.registerItem("steel_hoe",
            p -> new HoeItem(ModTiers.STEEL, 0.0f, -3.0f, p));

    public static final DeferredItem<Item> BRONZE_HELMET     = REGISTRY.registerItem("bronze_helmet",
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BRONZE.value(), ArmorType.HELMET)));
    public static final DeferredItem<Item> BRONZE_CHESTPLATE = REGISTRY.registerItem("bronze_chestplate",
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BRONZE.value(), ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> BRONZE_LEGGINGS   = REGISTRY.registerItem("bronze_leggings",
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BRONZE.value(), ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> BRONZE_BOOTS      = REGISTRY.registerItem("bronze_boots",
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BRONZE.value(), ArmorType.BOOTS)));

    public static final DeferredItem<BowItem> LONGBOW = REGISTRY.registerItem("longbow",
            p -> new BowItem(p.durability(384).enchantable(1)));

    public static final DeferredItem<Item> STEEL_HELMET      = REGISTRY.registerItem("steel_helmet",
            p -> new Item(p.humanoidArmor(ModArmorMaterials.STEEL.value(), ArmorType.HELMET)));
    public static final DeferredItem<Item> STEEL_CHESTPLATE  = REGISTRY.registerItem("steel_chestplate",
            p -> new Item(p.humanoidArmor(ModArmorMaterials.STEEL.value(), ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> STEEL_LEGGINGS    = REGISTRY.registerItem("steel_leggings",
            p -> new Item(p.humanoidArmor(ModArmorMaterials.STEEL.value(), ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> STEEL_BOOTS       = REGISTRY.registerItem("steel_boots",
            p -> new Item(p.humanoidArmor(ModArmorMaterials.STEEL.value(), ArmorType.BOOTS)));

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

    public static final DeferredItem<Item> BRONZE_SPEAR_HEAD          = simple("bronze_spear_head");
    public static final DeferredItem<Item> BRONZE_ARROWHEAD           = simple("bronze_arrowhead");
    public static final DeferredItem<Item> BRONZE_SHORT_AXE_HEAD      = simple("bronze_short_axe_head");
    public static final DeferredItem<Item> BRONZE_LONG_AXE_HEAD       = simple("bronze_long_axe_head");
    public static final DeferredItem<Item> BRONZE_LONGSWORD_BLADE     = simple("bronze_longsword_blade");
    public static final DeferredItem<Item> BRONZE_BASTARD_SWORD_BLADE = simple("bronze_bastard_sword_blade");
    public static final DeferredItem<Item> BRONZE_SHORTSWORD_BLADE    = simple("bronze_shortsword_blade");
    public static final DeferredItem<Item> BRONZE_FALCHION_BLADE      = simple("bronze_falchion_blade");
    public static final DeferredItem<Item> BRONZE_GREATSWORD_BLADE    = simple("bronze_greatsword_blade");

    public static final DeferredItem<Item> HILT      = simple("hilt");
    public static final DeferredItem<Item> LONG_HILT = simple("long_hilt");

    public static final DeferredItem<Item> POMMEL        = simple("pommel");
    public static final DeferredItem<Item> CROSSGUARD = simple("crossguard");
    public static final DeferredItem<Item> SLOPED_CROSSGUARD   = simple("sloped_crossguard");
    public static final DeferredItem<Item> BRONZE_ARMOR_PLATE         = simple("bronze_armor_plate");

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

    public static final DeferredItem<Item> IRON_GREATSWORD_CROSSGUARD_POMMEL   = REGISTRY.registerItem("iron_greatsword_crossguard_pommel",   p -> new Item(p.sword(ToolMaterial.IRON, 4.5f, -2.8f)));

    public static final DeferredItem<Item> IRON_SHORTSWORD_CROSSGUARD_POMMEL = REGISTRY.registerItem("iron_shortsword_crossguard_pommel", p -> new Item(p.sword(ToolMaterial.IRON, 2.0f, -2.4f)));
    public static final DeferredItem<Item> IRON_LONGSWORD_CROSSGUARD_POMMEL = REGISTRY.registerItem("iron_longsword_crossguard_pommel", p -> new Item(p.sword(ToolMaterial.IRON, 3.0f, -2.4f)));
    public static final DeferredItem<Item> IRON_BASTARD_SWORD_CROSSGUARD_POMMEL = REGISTRY.registerItem("iron_bastard_sword_crossguard_pommel", p -> new Item(p.sword(ToolMaterial.IRON, 3.5f, -2.5f)));
    public static final DeferredItem<Item> IRON_FALCHION_CROSSGUARD_POMMEL = REGISTRY.registerItem("iron_falchion_crossguard_pommel", p -> new Item(p.sword(ToolMaterial.IRON, 2.5f, -2.3f)));
    public static final DeferredItem<Item> IRON_CLAYMORE_SLOPED_CROSSGUARD_POMMEL = REGISTRY.registerItem("iron_claymore_sloped_crossguard_pommel", p -> new Item(p.sword(ToolMaterial.IRON, 4.5f, -2.8f)));

    public static final DeferredItem<Item> BRONZE_GREATSWORD_CROSSGUARD_POMMEL   = REGISTRY.registerItem("bronze_greatsword_crossguard_pommel",   p -> new Item(p.sword(ModTiers.BRONZE, 4.5f, -2.8f)));

    public static final DeferredItem<Item> BRONZE_SHORTSWORD_CROSSGUARD_POMMEL = REGISTRY.registerItem("bronze_shortsword_crossguard_pommel", p -> new Item(p.sword(ModTiers.BRONZE, 2.0f, -2.4f)));
    public static final DeferredItem<Item> BRONZE_LONGSWORD_CROSSGUARD_POMMEL = REGISTRY.registerItem("bronze_longsword_crossguard_pommel", p -> new Item(p.sword(ModTiers.BRONZE, 3.0f, -2.4f)));
    public static final DeferredItem<Item> BRONZE_BASTARD_SWORD_CROSSGUARD_POMMEL = REGISTRY.registerItem("bronze_bastard_sword_crossguard_pommel", p -> new Item(p.sword(ModTiers.BRONZE, 3.5f, -2.5f)));
    public static final DeferredItem<Item> BRONZE_FALCHION_CROSSGUARD_POMMEL = REGISTRY.registerItem("bronze_falchion_crossguard_pommel", p -> new Item(p.sword(ModTiers.BRONZE, 2.5f, -2.3f)));
    public static final DeferredItem<Item> BRONZE_CLAYMORE_SLOPED_CROSSGUARD_POMMEL = REGISTRY.registerItem("bronze_claymore_sloped_crossguard_pommel", p -> new Item(p.sword(ModTiers.BRONZE, 4.5f, -2.8f)));

    public static final DeferredItem<Item> STEEL_GREATSWORD_CROSSGUARD_POMMEL   = REGISTRY.registerItem("steel_greatsword_crossguard_pommel",   p -> new Item(p.sword(ModTiers.STEEL, 4.5f, -2.8f)));

    public static final DeferredItem<Item> STEEL_SHORTSWORD_CROSSGUARD_POMMEL = REGISTRY.registerItem("steel_shortsword_crossguard_pommel", p -> new Item(p.sword(ModTiers.STEEL, 2.0f, -2.4f)));
    public static final DeferredItem<Item> STEEL_LONGSWORD_CROSSGUARD_POMMEL = REGISTRY.registerItem("steel_longsword_crossguard_pommel", p -> new Item(p.sword(ModTiers.STEEL, 3.0f, -2.4f)));
    public static final DeferredItem<Item> STEEL_BASTARD_SWORD_CROSSGUARD_POMMEL = REGISTRY.registerItem("steel_bastard_sword_crossguard_pommel", p -> new Item(p.sword(ModTiers.STEEL, 3.5f, -2.5f)));
    public static final DeferredItem<Item> STEEL_FALCHION_CROSSGUARD_POMMEL = REGISTRY.registerItem("steel_falchion_crossguard_pommel", p -> new Item(p.sword(ModTiers.STEEL, 2.5f, -2.3f)));
    public static final DeferredItem<Item> STEEL_CLAYMORE_SLOPED_CROSSGUARD_POMMEL = REGISTRY.registerItem("steel_claymore_sloped_crossguard_pommel", p -> new Item(p.sword(ModTiers.STEEL, 4.5f, -2.8f)));

    public static final DeferredItem<SpawnEggItem> NORTHMAN_SPAWN_EGG = REGISTRY.registerItem(
            "northman_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ModEntities.NORTHMAN.get())));
    public static final DeferredItem<SpawnEggItem> RIVERLANDER_SPAWN_EGG = REGISTRY.registerItem(
            "riverlander_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ModEntities.RIVERLANDER.get())));
    public static final DeferredItem<SpawnEggItem> VALEMAN_SPAWN_EGG = REGISTRY.registerItem(
            "valeman_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ModEntities.VALEMAN.get())));
    public static final DeferredItem<SpawnEggItem> IRONBORN_SPAWN_EGG = REGISTRY.registerItem(
            "ironborn_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ModEntities.IRONBORN.get())));
    public static final DeferredItem<SpawnEggItem> WESTERMAN_SPAWN_EGG = REGISTRY.registerItem(
            "westerman_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ModEntities.WESTERMAN.get())));
    public static final DeferredItem<SpawnEggItem> REACHMAN_SPAWN_EGG = REGISTRY.registerItem(
            "reachman_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ModEntities.REACHMAN.get())));
    public static final DeferredItem<SpawnEggItem> STORMLORDER_SPAWN_EGG = REGISTRY.registerItem(
            "stormlorder_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ModEntities.STORMLORDER.get())));
    public static final DeferredItem<SpawnEggItem> DORNISHMAN_SPAWN_EGG = REGISTRY.registerItem(
            "dornishman_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ModEntities.DORNISHMAN.get())));

    public static final DeferredItem<SpawnEggItem> STARK_LEVY_SPAWN_EGG = REGISTRY.registerItem(
            "stark_levy_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ModEntities.STARK_LEVY.get())));
    public static final DeferredItem<SpawnEggItem> TULLY_LEVY_SPAWN_EGG = REGISTRY.registerItem(
            "tully_levy_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ModEntities.TULLY_LEVY.get())));
    public static final DeferredItem<SpawnEggItem> LANNISTER_LEVY_SPAWN_EGG = REGISTRY.registerItem(
            "lannister_levy_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ModEntities.LANNISTER_LEVY.get())));
    public static final DeferredItem<SpawnEggItem> BARATHEON_LEVY_SPAWN_EGG = REGISTRY.registerItem(
            "baratheon_levy_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ModEntities.BARATHEON_LEVY.get())));
    public static final DeferredItem<SpawnEggItem> GREYJOY_LEVY_SPAWN_EGG = REGISTRY.registerItem(
            "greyjoy_levy_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ModEntities.GREYJOY_LEVY.get())));
    public static final DeferredItem<SpawnEggItem> MARTELL_LEVY_SPAWN_EGG = REGISTRY.registerItem(
            "martell_levy_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ModEntities.MARTELL_LEVY.get())));
    public static final DeferredItem<SpawnEggItem> TYRELL_LEVY_SPAWN_EGG = REGISTRY.registerItem(
            "tyrell_levy_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ModEntities.TYRELL_LEVY.get())));
    public static final DeferredItem<SpawnEggItem> ARRYN_LEVY_SPAWN_EGG = REGISTRY.registerItem(
            "arryn_levy_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ModEntities.ARRYN_LEVY.get())));

    public static final DeferredItem<SpawnEggItem> NORTH_SOLDIER_SPAWN_EGG = REGISTRY.registerItem(
            "north_soldier_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ModEntities.NORTH_SOLDIER.get())));
    public static final DeferredItem<SpawnEggItem> VALE_KNIGHT_SPAWN_EGG = REGISTRY.registerItem(
            "vale_knight_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ModEntities.VALE_KNIGHT.get())));

    public static final DeferredItem<SpawnEggItem> GOT_STAG_SPAWN_EGG = REGISTRY.registerItem(
            "got_stag_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ModEntities.GOT_STAG.get())));

    public static final DeferredItem<SpawnEggItem> GOT_HERON_SPAWN_EGG = REGISTRY.registerItem(
            "got_heron_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ModEntities.GOT_HERON.get())));

    public static final DeferredItem<SpawnEggItem> GOT_DIREWOLF_SPAWN_EGG = REGISTRY.registerItem(
            "got_direwolf_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ModEntities.GOT_DIREWOLF.get())));

    public static final DeferredItem<SpawnEggItem> GOT_CROW_SPAWN_EGG = REGISTRY.registerItem(
            "got_crow_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ModEntities.GOT_CROW.get())));

    public static final DeferredItem<SpawnEggItem> GOT_MAMMOTH_SPAWN_EGG = REGISTRY.registerItem(
            "got_mammoth_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ModEntities.GOT_MAMMOTH.get())));

    public static final DeferredItem<SpawnEggItem> GOT_BROWN_BEAR_SPAWN_EGG = REGISTRY.registerItem(
            "got_brown_bear_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ModEntities.GOT_BROWN_BEAR.get())));

    public static final DeferredItem<SpawnEggItem> GOT_GIANT_SPAWN_EGG = REGISTRY.registerItem(
            "got_giant_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ModEntities.GOT_GIANT.get())));

    public static final DeferredItem<Item> STARK_BANNER_PATTERN =
            REGISTRY.registerItem("stark_banner_pattern",
                    p -> new Item(p.component(DataComponents.PROVIDES_BANNER_PATTERNS, ModBannerPatterns.STARK_PATTERN_TAG)));
    public static final DeferredItem<Item> LANNISTER_BANNER_PATTERN =
            REGISTRY.registerItem("lannister_banner_pattern",
                    p -> new Item(p.component(DataComponents.PROVIDES_BANNER_PATTERNS, ModBannerPatterns.LANNISTER_PATTERN_TAG)));
    public static final DeferredItem<Item> TARGARYEN_BANNER_PATTERN =
            REGISTRY.registerItem("targaryen_banner_pattern",
                    p -> new Item(p.component(DataComponents.PROVIDES_BANNER_PATTERNS, ModBannerPatterns.TARGARYEN_PATTERN_TAG)));
    public static final DeferredItem<Item> BARATHEON_BANNER_PATTERN =
            REGISTRY.registerItem("baratheon_banner_pattern",
                    p -> new Item(p.component(DataComponents.PROVIDES_BANNER_PATTERNS, ModBannerPatterns.BARATHEON_PATTERN_TAG)));
    public static final DeferredItem<Item> GREYJOY_BANNER_PATTERN =
            REGISTRY.registerItem("greyjoy_banner_pattern",
                    p -> new Item(p.component(DataComponents.PROVIDES_BANNER_PATTERNS, ModBannerPatterns.GREYJOY_PATTERN_TAG)));
    public static final DeferredItem<Item> TYRELL_BANNER_PATTERN =
            REGISTRY.registerItem("tyrell_banner_pattern",
                    p -> new Item(p.component(DataComponents.PROVIDES_BANNER_PATTERNS, ModBannerPatterns.TYRELL_PATTERN_TAG)));
    public static final DeferredItem<Item> MARTELL_BANNER_PATTERN =
            REGISTRY.registerItem("martell_banner_pattern",
                    p -> new Item(p.component(DataComponents.PROVIDES_BANNER_PATTERNS, ModBannerPatterns.MARTELL_PATTERN_TAG)));
    public static final DeferredItem<Item> TULLY_BANNER_PATTERN =
            REGISTRY.registerItem("tully_banner_pattern",
                    p -> new Item(p.component(DataComponents.PROVIDES_BANNER_PATTERNS, ModBannerPatterns.TULLY_PATTERN_TAG)));
    public static final DeferredItem<Item> ARRYN_BANNER_PATTERN =
            REGISTRY.registerItem("arryn_banner_pattern",
                    p -> new Item(p.component(DataComponents.PROVIDES_BANNER_PATTERNS, ModBannerPatterns.ARRYN_PATTERN_TAG)));
    public static final DeferredItem<Item> BOLTON_BANNER_PATTERN =
            REGISTRY.registerItem("bolton_banner_pattern",
                    p -> new Item(p.component(DataComponents.PROVIDES_BANNER_PATTERNS, ModBannerPatterns.BOLTON_PATTERN_TAG)));

    public static final DeferredItem<Item> OVEN = block(ModBlocks.OVEN);
    public static final DeferredItem<Item> FORGE = block(ModBlocks.FORGE);
    public static final DeferredItem<Item> SMITHING_ANVIL = block(ModBlocks.SMITHING_ANVIL);
    public static final DeferredItem<Item> SMITHING_HAMMER = REGISTRY.registerItem("smithing_hammer",
            p -> new net.got.item.SmithingHammerItem(p.durability(250)));
    public static final DeferredItem<Item> BELLOWS = block(ModBlocks.BELLOWS);

    private static DeferredItem<Item> simple(String name) {
        return REGISTRY.registerSimpleItem(name);
    }

    public static final DeferredItem<Item> NIGHTWOOD_LOG            = block(ModBlocks.NIGHTWOOD_LOG);
    public static final DeferredItem<Item> NIGHTWOOD_WOOD           = block(ModBlocks.NIGHTWOOD_WOOD);
    public static final DeferredItem<Item> NIGHTWOOD_PLANKS         = block(ModBlocks.NIGHTWOOD_PLANKS);
    public static final DeferredItem<Item> NIGHTWOOD_LEAVES         = block(ModBlocks.NIGHTWOOD_LEAVES);
    public static final DeferredItem<Item> NIGHTWOOD_STAIRS         = block(ModBlocks.NIGHTWOOD_STAIRS);
    public static final DeferredItem<Item> NIGHTWOOD_SLAB           = block(ModBlocks.NIGHTWOOD_SLAB);
    public static final DeferredItem<Item> NIGHTWOOD_FENCE          = block(ModBlocks.NIGHTWOOD_FENCE);
    public static final DeferredItem<Item> NIGHTWOOD_FENCE_GATE     = block(ModBlocks.NIGHTWOOD_FENCE_GATE);
    public static final DeferredItem<Item> NIGHTWOOD_PRESSURE_PLATE = block(ModBlocks.NIGHTWOOD_PRESSURE_PLATE);
    public static final DeferredItem<Item> NIGHTWOOD_BUTTON         = block(ModBlocks.NIGHTWOOD_BUTTON);
    public static final DeferredItem<Item> NIGHTWOOD_DOOR           = door(ModBlocks.NIGHTWOOD_DOOR);
    public static final DeferredItem<Item> NIGHTWOOD_TRAPDOOR       = block(ModBlocks.NIGHTWOOD_TRAPDOOR);
    public static final DeferredItem<Item> NIGHTWOOD_BRANCH         = block(ModBlocks.NIGHTWOOD_BRANCH);
    public static final DeferredItem<Item> STRIPPED_NIGHTWOOD_BRANCH = block(ModBlocks.STRIPPED_NIGHTWOOD_BRANCH);
    public static final DeferredItem<Item> NIGHTWOOD_SIGN           = REGISTRY.registerItem("nightwood_sign",         p -> new SignItem(ModBlocks.NIGHTWOOD_SIGN.get(), ModBlocks.NIGHTWOOD_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> NIGHTWOOD_HANGING_SIGN   = REGISTRY.registerItem("nightwood_hanging_sign", p -> new HangingSignItem(ModBlocks.NIGHTWOOD_HANGING_SIGN.get(), ModBlocks.NIGHTWOOD_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> NIGHTWOOD_BOAT           = REGISTRY.registerItem("nightwood_boat",       p -> new GotBoatItem(ModBoatEntities.NIGHTWOOD_BOAT.get(), p));
    public static final DeferredItem<Item> NIGHTWOOD_CHEST_BOAT     = REGISTRY.registerItem("nightwood_chest_boat", p -> new GotBoatItem(ModBoatEntities.NIGHTWOOD_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> NIGHTWOOD_SAPLING        = block(ModBlocks.NIGHTWOOD_SAPLING);
    public static final DeferredItem<Item> STRIPPED_NIGHTWOOD_LOG   = block(ModBlocks.STRIPPED_NIGHTWOOD_LOG);
    public static final DeferredItem<Item> STRIPPED_NIGHTWOOD_WOOD  = block(ModBlocks.STRIPPED_NIGHTWOOD_WOOD);
    public static final DeferredItem<BlockItem> NIGHTWOOD_ROOFING = REGISTRY.registerSimpleBlockItem("nightwood_roofing", ModBlocks.NIGHTWOOD_ROOFING);
    public static final DeferredItem<BlockItem> NIGHTWOOD_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("nightwood_roofing_slab", ModBlocks.NIGHTWOOD_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> NIGHTWOOD_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("nightwood_roofing_stairs", ModBlocks.NIGHTWOOD_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> NIGHTWOOD_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("nightwood_roofing_wall", ModBlocks.NIGHTWOOD_ROOFING_WALL);

    public static final DeferredItem<Item> PURPLEHEART_LOG            = block(ModBlocks.PURPLEHEART_LOG);
    public static final DeferredItem<Item> PURPLEHEART_WOOD           = block(ModBlocks.PURPLEHEART_WOOD);
    public static final DeferredItem<Item> PURPLEHEART_PLANKS         = block(ModBlocks.PURPLEHEART_PLANKS);
    public static final DeferredItem<Item> PURPLEHEART_LEAVES         = block(ModBlocks.PURPLEHEART_LEAVES);
    public static final DeferredItem<Item> PURPLEHEART_STAIRS         = block(ModBlocks.PURPLEHEART_STAIRS);
    public static final DeferredItem<Item> PURPLEHEART_SLAB           = block(ModBlocks.PURPLEHEART_SLAB);
    public static final DeferredItem<Item> PURPLEHEART_FENCE          = block(ModBlocks.PURPLEHEART_FENCE);
    public static final DeferredItem<Item> PURPLEHEART_FENCE_GATE     = block(ModBlocks.PURPLEHEART_FENCE_GATE);
    public static final DeferredItem<Item> PURPLEHEART_PRESSURE_PLATE = block(ModBlocks.PURPLEHEART_PRESSURE_PLATE);
    public static final DeferredItem<Item> PURPLEHEART_BUTTON         = block(ModBlocks.PURPLEHEART_BUTTON);
    public static final DeferredItem<Item> PURPLEHEART_DOOR           = door(ModBlocks.PURPLEHEART_DOOR);
    public static final DeferredItem<Item> PURPLEHEART_TRAPDOOR       = block(ModBlocks.PURPLEHEART_TRAPDOOR);
    public static final DeferredItem<Item> PURPLEHEART_BRANCH         = block(ModBlocks.PURPLEHEART_BRANCH);
    public static final DeferredItem<Item> STRIPPED_PURPLEHEART_BRANCH = block(ModBlocks.STRIPPED_PURPLEHEART_BRANCH);
    public static final DeferredItem<Item> PURPLEHEART_SIGN           = REGISTRY.registerItem("purpleheart_sign",         p -> new SignItem(ModBlocks.PURPLEHEART_SIGN.get(), ModBlocks.PURPLEHEART_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> PURPLEHEART_HANGING_SIGN   = REGISTRY.registerItem("purpleheart_hanging_sign", p -> new HangingSignItem(ModBlocks.PURPLEHEART_HANGING_SIGN.get(), ModBlocks.PURPLEHEART_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> PURPLEHEART_BOAT           = REGISTRY.registerItem("purpleheart_boat",       p -> new GotBoatItem(ModBoatEntities.PURPLEHEART_BOAT.get(), p));
    public static final DeferredItem<Item> PURPLEHEART_CHEST_BOAT     = REGISTRY.registerItem("purpleheart_chest_boat", p -> new GotBoatItem(ModBoatEntities.PURPLEHEART_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> PURPLEHEART_SAPLING        = block(ModBlocks.PURPLEHEART_SAPLING);
    public static final DeferredItem<Item> STRIPPED_PURPLEHEART_LOG   = block(ModBlocks.STRIPPED_PURPLEHEART_LOG);
    public static final DeferredItem<Item> STRIPPED_PURPLEHEART_WOOD  = block(ModBlocks.STRIPPED_PURPLEHEART_WOOD);
    public static final DeferredItem<BlockItem> PURPLEHEART_ROOFING = REGISTRY.registerSimpleBlockItem("purpleheart_roofing", ModBlocks.PURPLEHEART_ROOFING);
    public static final DeferredItem<BlockItem> PURPLEHEART_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("purpleheart_roofing_slab", ModBlocks.PURPLEHEART_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> PURPLEHEART_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("purpleheart_roofing_stairs", ModBlocks.PURPLEHEART_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> PURPLEHEART_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("purpleheart_roofing_wall", ModBlocks.PURPLEHEART_ROOFING_WALL);

    public static final DeferredItem<Item> TIGERWOOD_LOG            = block(ModBlocks.TIGERWOOD_LOG);
    public static final DeferredItem<Item> TIGERWOOD_WOOD           = block(ModBlocks.TIGERWOOD_WOOD);
    public static final DeferredItem<Item> TIGERWOOD_PLANKS         = block(ModBlocks.TIGERWOOD_PLANKS);
    public static final DeferredItem<Item> TIGERWOOD_LEAVES         = block(ModBlocks.TIGERWOOD_LEAVES);
    public static final DeferredItem<Item> TIGERWOOD_STAIRS         = block(ModBlocks.TIGERWOOD_STAIRS);
    public static final DeferredItem<Item> TIGERWOOD_SLAB           = block(ModBlocks.TIGERWOOD_SLAB);
    public static final DeferredItem<Item> TIGERWOOD_FENCE          = block(ModBlocks.TIGERWOOD_FENCE);
    public static final DeferredItem<Item> TIGERWOOD_FENCE_GATE     = block(ModBlocks.TIGERWOOD_FENCE_GATE);
    public static final DeferredItem<Item> TIGERWOOD_PRESSURE_PLATE = block(ModBlocks.TIGERWOOD_PRESSURE_PLATE);
    public static final DeferredItem<Item> TIGERWOOD_BUTTON         = block(ModBlocks.TIGERWOOD_BUTTON);
    public static final DeferredItem<Item> TIGERWOOD_DOOR           = door(ModBlocks.TIGERWOOD_DOOR);
    public static final DeferredItem<Item> TIGERWOOD_TRAPDOOR       = block(ModBlocks.TIGERWOOD_TRAPDOOR);
    public static final DeferredItem<Item> TIGERWOOD_BRANCH         = block(ModBlocks.TIGERWOOD_BRANCH);
    public static final DeferredItem<Item> STRIPPED_TIGERWOOD_BRANCH = block(ModBlocks.STRIPPED_TIGERWOOD_BRANCH);
    public static final DeferredItem<Item> TIGERWOOD_SIGN           = REGISTRY.registerItem("tigerwood_sign",         p -> new SignItem(ModBlocks.TIGERWOOD_SIGN.get(), ModBlocks.TIGERWOOD_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> TIGERWOOD_HANGING_SIGN   = REGISTRY.registerItem("tigerwood_hanging_sign", p -> new HangingSignItem(ModBlocks.TIGERWOOD_HANGING_SIGN.get(), ModBlocks.TIGERWOOD_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> TIGERWOOD_BOAT           = REGISTRY.registerItem("tigerwood_boat",       p -> new GotBoatItem(ModBoatEntities.TIGERWOOD_BOAT.get(), p));
    public static final DeferredItem<Item> TIGERWOOD_CHEST_BOAT     = REGISTRY.registerItem("tigerwood_chest_boat", p -> new GotBoatItem(ModBoatEntities.TIGERWOOD_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> TIGERWOOD_SAPLING        = block(ModBlocks.TIGERWOOD_SAPLING);
    public static final DeferredItem<Item> STRIPPED_TIGERWOOD_LOG   = block(ModBlocks.STRIPPED_TIGERWOOD_LOG);
    public static final DeferredItem<Item> STRIPPED_TIGERWOOD_WOOD  = block(ModBlocks.STRIPPED_TIGERWOOD_WOOD);
    public static final DeferredItem<BlockItem> TIGERWOOD_ROOFING = REGISTRY.registerSimpleBlockItem("tigerwood_roofing", ModBlocks.TIGERWOOD_ROOFING);
    public static final DeferredItem<BlockItem> TIGERWOOD_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("tigerwood_roofing_slab", ModBlocks.TIGERWOOD_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> TIGERWOOD_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("tigerwood_roofing_stairs", ModBlocks.TIGERWOOD_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> TIGERWOOD_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("tigerwood_roofing_wall", ModBlocks.TIGERWOOD_ROOFING_WALL);

    public static final DeferredItem<Item> SANDALWOOD_LOG            = block(ModBlocks.SANDALWOOD_LOG);
    public static final DeferredItem<Item> SANDALWOOD_WOOD           = block(ModBlocks.SANDALWOOD_WOOD);
    public static final DeferredItem<Item> SANDALWOOD_PLANKS         = block(ModBlocks.SANDALWOOD_PLANKS);
    public static final DeferredItem<Item> SANDALWOOD_LEAVES         = block(ModBlocks.SANDALWOOD_LEAVES);
    public static final DeferredItem<Item> SANDALWOOD_STAIRS         = block(ModBlocks.SANDALWOOD_STAIRS);
    public static final DeferredItem<Item> SANDALWOOD_SLAB           = block(ModBlocks.SANDALWOOD_SLAB);
    public static final DeferredItem<Item> SANDALWOOD_FENCE          = block(ModBlocks.SANDALWOOD_FENCE);
    public static final DeferredItem<Item> SANDALWOOD_FENCE_GATE     = block(ModBlocks.SANDALWOOD_FENCE_GATE);
    public static final DeferredItem<Item> SANDALWOOD_PRESSURE_PLATE = block(ModBlocks.SANDALWOOD_PRESSURE_PLATE);
    public static final DeferredItem<Item> SANDALWOOD_BUTTON         = block(ModBlocks.SANDALWOOD_BUTTON);
    public static final DeferredItem<Item> SANDALWOOD_DOOR           = door(ModBlocks.SANDALWOOD_DOOR);
    public static final DeferredItem<Item> SANDALWOOD_TRAPDOOR       = block(ModBlocks.SANDALWOOD_TRAPDOOR);
    public static final DeferredItem<Item> SANDALWOOD_BRANCH         = block(ModBlocks.SANDALWOOD_BRANCH);
    public static final DeferredItem<Item> STRIPPED_SANDALWOOD_BRANCH = block(ModBlocks.STRIPPED_SANDALWOOD_BRANCH);
    public static final DeferredItem<Item> SANDALWOOD_SIGN           = REGISTRY.registerItem("sandalwood_sign",         p -> new SignItem(ModBlocks.SANDALWOOD_SIGN.get(), ModBlocks.SANDALWOOD_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> SANDALWOOD_HANGING_SIGN   = REGISTRY.registerItem("sandalwood_hanging_sign", p -> new HangingSignItem(ModBlocks.SANDALWOOD_HANGING_SIGN.get(), ModBlocks.SANDALWOOD_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> SANDALWOOD_BOAT           = REGISTRY.registerItem("sandalwood_boat",       p -> new GotBoatItem(ModBoatEntities.SANDALWOOD_BOAT.get(), p));
    public static final DeferredItem<Item> SANDALWOOD_CHEST_BOAT     = REGISTRY.registerItem("sandalwood_chest_boat", p -> new GotBoatItem(ModBoatEntities.SANDALWOOD_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> SANDALWOOD_SAPLING        = block(ModBlocks.SANDALWOOD_SAPLING);
    public static final DeferredItem<Item> STRIPPED_SANDALWOOD_LOG   = block(ModBlocks.STRIPPED_SANDALWOOD_LOG);
    public static final DeferredItem<Item> STRIPPED_SANDALWOOD_WOOD  = block(ModBlocks.STRIPPED_SANDALWOOD_WOOD);
    public static final DeferredItem<BlockItem> SANDALWOOD_ROOFING = REGISTRY.registerSimpleBlockItem("sandalwood_roofing", ModBlocks.SANDALWOOD_ROOFING);
    public static final DeferredItem<BlockItem> SANDALWOOD_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("sandalwood_roofing_slab", ModBlocks.SANDALWOOD_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> SANDALWOOD_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("sandalwood_roofing_stairs", ModBlocks.SANDALWOOD_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> SANDALWOOD_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("sandalwood_roofing_wall", ModBlocks.SANDALWOOD_ROOFING_WALL);

    public static final DeferredItem<Item> SANDBEGGAR_LOG            = block(ModBlocks.SANDBEGGAR_LOG);
    public static final DeferredItem<Item> SANDBEGGAR_WOOD           = block(ModBlocks.SANDBEGGAR_WOOD);
    public static final DeferredItem<Item> SANDBEGGAR_PLANKS         = block(ModBlocks.SANDBEGGAR_PLANKS);
    public static final DeferredItem<Item> SANDBEGGAR_LEAVES         = block(ModBlocks.SANDBEGGAR_LEAVES);
    public static final DeferredItem<Item> SANDBEGGAR_STAIRS         = block(ModBlocks.SANDBEGGAR_STAIRS);
    public static final DeferredItem<Item> SANDBEGGAR_SLAB           = block(ModBlocks.SANDBEGGAR_SLAB);
    public static final DeferredItem<Item> SANDBEGGAR_FENCE          = block(ModBlocks.SANDBEGGAR_FENCE);
    public static final DeferredItem<Item> SANDBEGGAR_FENCE_GATE     = block(ModBlocks.SANDBEGGAR_FENCE_GATE);
    public static final DeferredItem<Item> SANDBEGGAR_PRESSURE_PLATE = block(ModBlocks.SANDBEGGAR_PRESSURE_PLATE);
    public static final DeferredItem<Item> SANDBEGGAR_BUTTON         = block(ModBlocks.SANDBEGGAR_BUTTON);
    public static final DeferredItem<Item> SANDBEGGAR_DOOR           = door(ModBlocks.SANDBEGGAR_DOOR);
    public static final DeferredItem<Item> SANDBEGGAR_TRAPDOOR       = block(ModBlocks.SANDBEGGAR_TRAPDOOR);
    public static final DeferredItem<Item> SANDBEGGAR_BRANCH         = block(ModBlocks.SANDBEGGAR_BRANCH);
    public static final DeferredItem<Item> STRIPPED_SANDBEGGAR_BRANCH = block(ModBlocks.STRIPPED_SANDBEGGAR_BRANCH);
    public static final DeferredItem<Item> SANDBEGGAR_SIGN           = REGISTRY.registerItem("sandbeggar_sign",         p -> new SignItem(ModBlocks.SANDBEGGAR_SIGN.get(), ModBlocks.SANDBEGGAR_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> SANDBEGGAR_HANGING_SIGN   = REGISTRY.registerItem("sandbeggar_hanging_sign", p -> new HangingSignItem(ModBlocks.SANDBEGGAR_HANGING_SIGN.get(), ModBlocks.SANDBEGGAR_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> SANDBEGGAR_BOAT           = REGISTRY.registerItem("sandbeggar_boat",       p -> new GotBoatItem(ModBoatEntities.SANDBEGGAR_BOAT.get(), p));
    public static final DeferredItem<Item> SANDBEGGAR_CHEST_BOAT     = REGISTRY.registerItem("sandbeggar_chest_boat", p -> new GotBoatItem(ModBoatEntities.SANDBEGGAR_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> SANDBEGGAR_SAPLING        = block(ModBlocks.SANDBEGGAR_SAPLING);
    public static final DeferredItem<Item> STRIPPED_SANDBEGGAR_LOG   = block(ModBlocks.STRIPPED_SANDBEGGAR_LOG);
    public static final DeferredItem<Item> STRIPPED_SANDBEGGAR_WOOD  = block(ModBlocks.STRIPPED_SANDBEGGAR_WOOD);
    public static final DeferredItem<BlockItem> SANDBEGGAR_ROOFING = REGISTRY.registerSimpleBlockItem("sandbeggar_roofing", ModBlocks.SANDBEGGAR_ROOFING);
    public static final DeferredItem<BlockItem> SANDBEGGAR_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("sandbeggar_roofing_slab", ModBlocks.SANDBEGGAR_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> SANDBEGGAR_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("sandbeggar_roofing_stairs", ModBlocks.SANDBEGGAR_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> SANDBEGGAR_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("sandbeggar_roofing_wall", ModBlocks.SANDBEGGAR_ROOFING_WALL);

    public static final DeferredItem<Item> APRICOT_LOG            = block(ModBlocks.APRICOT_LOG);
    public static final DeferredItem<Item> APRICOT_WOOD           = block(ModBlocks.APRICOT_WOOD);
    public static final DeferredItem<Item> APRICOT_PLANKS         = block(ModBlocks.APRICOT_PLANKS);
    public static final DeferredItem<Item> APRICOT_LEAVES         = block(ModBlocks.APRICOT_LEAVES);
    public static final DeferredItem<Item> APRICOT_STAIRS         = block(ModBlocks.APRICOT_STAIRS);
    public static final DeferredItem<Item> APRICOT_SLAB           = block(ModBlocks.APRICOT_SLAB);
    public static final DeferredItem<Item> APRICOT_FENCE          = block(ModBlocks.APRICOT_FENCE);
    public static final DeferredItem<Item> APRICOT_FENCE_GATE     = block(ModBlocks.APRICOT_FENCE_GATE);
    public static final DeferredItem<Item> APRICOT_PRESSURE_PLATE = block(ModBlocks.APRICOT_PRESSURE_PLATE);
    public static final DeferredItem<Item> APRICOT_BUTTON         = block(ModBlocks.APRICOT_BUTTON);
    public static final DeferredItem<Item> APRICOT_DOOR           = door(ModBlocks.APRICOT_DOOR);
    public static final DeferredItem<Item> APRICOT_TRAPDOOR       = block(ModBlocks.APRICOT_TRAPDOOR);
    public static final DeferredItem<Item> APRICOT_BRANCH         = block(ModBlocks.APRICOT_BRANCH);
    public static final DeferredItem<Item> STRIPPED_APRICOT_BRANCH = block(ModBlocks.STRIPPED_APRICOT_BRANCH);
    public static final DeferredItem<Item> APRICOT_SIGN           = REGISTRY.registerItem("apricot_sign",         p -> new SignItem(ModBlocks.APRICOT_SIGN.get(), ModBlocks.APRICOT_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> APRICOT_HANGING_SIGN   = REGISTRY.registerItem("apricot_hanging_sign", p -> new HangingSignItem(ModBlocks.APRICOT_HANGING_SIGN.get(), ModBlocks.APRICOT_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> APRICOT_BOAT           = REGISTRY.registerItem("apricot_boat",       p -> new GotBoatItem(ModBoatEntities.APRICOT_BOAT.get(), p));
    public static final DeferredItem<Item> APRICOT_CHEST_BOAT     = REGISTRY.registerItem("apricot_chest_boat", p -> new GotBoatItem(ModBoatEntities.APRICOT_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> APRICOT_SAPLING        = block(ModBlocks.APRICOT_SAPLING);
    public static final DeferredItem<Item> STRIPPED_APRICOT_LOG   = block(ModBlocks.STRIPPED_APRICOT_LOG);
    public static final DeferredItem<Item> STRIPPED_APRICOT_WOOD  = block(ModBlocks.STRIPPED_APRICOT_WOOD);
    public static final DeferredItem<BlockItem> APRICOT_ROOFING = REGISTRY.registerSimpleBlockItem("apricot_roofing", ModBlocks.APRICOT_ROOFING);
    public static final DeferredItem<BlockItem> APRICOT_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("apricot_roofing_slab", ModBlocks.APRICOT_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> APRICOT_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("apricot_roofing_stairs", ModBlocks.APRICOT_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> APRICOT_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("apricot_roofing_wall", ModBlocks.APRICOT_ROOFING_WALL);
    public static final DeferredItem<Item> APRICOT = REGISTRY.registerItem("apricot", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.5f).build()));

    public static final DeferredItem<Item> BLACKTHORN_LOG            = block(ModBlocks.BLACKTHORN_LOG);
    public static final DeferredItem<Item> BLACKTHORN_WOOD           = block(ModBlocks.BLACKTHORN_WOOD);
    public static final DeferredItem<Item> BLACKTHORN_PLANKS         = block(ModBlocks.BLACKTHORN_PLANKS);
    public static final DeferredItem<Item> BLACKTHORN_LEAVES         = block(ModBlocks.BLACKTHORN_LEAVES);
    public static final DeferredItem<Item> BLACKTHORN_STAIRS         = block(ModBlocks.BLACKTHORN_STAIRS);
    public static final DeferredItem<Item> BLACKTHORN_SLAB           = block(ModBlocks.BLACKTHORN_SLAB);
    public static final DeferredItem<Item> BLACKTHORN_FENCE          = block(ModBlocks.BLACKTHORN_FENCE);
    public static final DeferredItem<Item> BLACKTHORN_FENCE_GATE     = block(ModBlocks.BLACKTHORN_FENCE_GATE);
    public static final DeferredItem<Item> BLACKTHORN_PRESSURE_PLATE = block(ModBlocks.BLACKTHORN_PRESSURE_PLATE);
    public static final DeferredItem<Item> BLACKTHORN_BUTTON         = block(ModBlocks.BLACKTHORN_BUTTON);
    public static final DeferredItem<Item> BLACKTHORN_DOOR           = door(ModBlocks.BLACKTHORN_DOOR);
    public static final DeferredItem<Item> BLACKTHORN_TRAPDOOR       = block(ModBlocks.BLACKTHORN_TRAPDOOR);
    public static final DeferredItem<Item> BLACKTHORN_BRANCH         = block(ModBlocks.BLACKTHORN_BRANCH);
    public static final DeferredItem<Item> STRIPPED_BLACKTHORN_BRANCH = block(ModBlocks.STRIPPED_BLACKTHORN_BRANCH);
    public static final DeferredItem<Item> BLACKTHORN_SIGN           = REGISTRY.registerItem("blackthorn_sign",         p -> new SignItem(ModBlocks.BLACKTHORN_SIGN.get(), ModBlocks.BLACKTHORN_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> BLACKTHORN_HANGING_SIGN   = REGISTRY.registerItem("blackthorn_hanging_sign", p -> new HangingSignItem(ModBlocks.BLACKTHORN_HANGING_SIGN.get(), ModBlocks.BLACKTHORN_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> BLACKTHORN_BOAT           = REGISTRY.registerItem("blackthorn_boat",       p -> new GotBoatItem(ModBoatEntities.BLACKTHORN_BOAT.get(), p));
    public static final DeferredItem<Item> BLACKTHORN_CHEST_BOAT     = REGISTRY.registerItem("blackthorn_chest_boat", p -> new GotBoatItem(ModBoatEntities.BLACKTHORN_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> BLACKTHORN_SAPLING        = block(ModBlocks.BLACKTHORN_SAPLING);
    public static final DeferredItem<Item> STRIPPED_BLACKTHORN_LOG   = block(ModBlocks.STRIPPED_BLACKTHORN_LOG);
    public static final DeferredItem<Item> STRIPPED_BLACKTHORN_WOOD  = block(ModBlocks.STRIPPED_BLACKTHORN_WOOD);
    public static final DeferredItem<BlockItem> BLACKTHORN_ROOFING = REGISTRY.registerSimpleBlockItem("blackthorn_roofing", ModBlocks.BLACKTHORN_ROOFING);
    public static final DeferredItem<BlockItem> BLACKTHORN_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("blackthorn_roofing_slab", ModBlocks.BLACKTHORN_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> BLACKTHORN_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("blackthorn_roofing_stairs", ModBlocks.BLACKTHORN_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> BLACKTHORN_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("blackthorn_roofing_wall", ModBlocks.BLACKTHORN_ROOFING_WALL);

    public static final DeferredItem<Item> RED_CHERRY_LOG            = block(ModBlocks.RED_CHERRY_LOG);
    public static final DeferredItem<Item> RED_CHERRY_WOOD           = block(ModBlocks.RED_CHERRY_WOOD);
    public static final DeferredItem<Item> RED_CHERRY_PLANKS         = block(ModBlocks.RED_CHERRY_PLANKS);
    public static final DeferredItem<Item> RED_CHERRY_LEAVES         = block(ModBlocks.RED_CHERRY_LEAVES);
    public static final DeferredItem<Item> RED_CHERRY_STAIRS         = block(ModBlocks.RED_CHERRY_STAIRS);
    public static final DeferredItem<Item> RED_CHERRY_SLAB           = block(ModBlocks.RED_CHERRY_SLAB);
    public static final DeferredItem<Item> RED_CHERRY_FENCE          = block(ModBlocks.RED_CHERRY_FENCE);
    public static final DeferredItem<Item> RED_CHERRY_FENCE_GATE     = block(ModBlocks.RED_CHERRY_FENCE_GATE);
    public static final DeferredItem<Item> RED_CHERRY_PRESSURE_PLATE = block(ModBlocks.RED_CHERRY_PRESSURE_PLATE);
    public static final DeferredItem<Item> RED_CHERRY_BUTTON         = block(ModBlocks.RED_CHERRY_BUTTON);
    public static final DeferredItem<Item> RED_CHERRY_DOOR           = door(ModBlocks.RED_CHERRY_DOOR);
    public static final DeferredItem<Item> RED_CHERRY_TRAPDOOR       = block(ModBlocks.RED_CHERRY_TRAPDOOR);
    public static final DeferredItem<Item> RED_CHERRY_BRANCH         = block(ModBlocks.RED_CHERRY_BRANCH);
    public static final DeferredItem<Item> STRIPPED_RED_CHERRY_BRANCH = block(ModBlocks.STRIPPED_RED_CHERRY_BRANCH);
    public static final DeferredItem<Item> RED_CHERRY_SIGN           = REGISTRY.registerItem("red_cherry_sign",         p -> new SignItem(ModBlocks.RED_CHERRY_SIGN.get(), ModBlocks.RED_CHERRY_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> RED_CHERRY_HANGING_SIGN   = REGISTRY.registerItem("red_cherry_hanging_sign", p -> new HangingSignItem(ModBlocks.RED_CHERRY_HANGING_SIGN.get(), ModBlocks.RED_CHERRY_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> RED_CHERRY_BOAT           = REGISTRY.registerItem("red_cherry_boat",       p -> new GotBoatItem(ModBoatEntities.RED_CHERRY_BOAT.get(), p));
    public static final DeferredItem<Item> RED_CHERRY_CHEST_BOAT     = REGISTRY.registerItem("red_cherry_chest_boat", p -> new GotBoatItem(ModBoatEntities.RED_CHERRY_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> RED_CHERRY_SAPLING        = block(ModBlocks.RED_CHERRY_SAPLING);
    public static final DeferredItem<Item> STRIPPED_RED_CHERRY_LOG   = block(ModBlocks.STRIPPED_RED_CHERRY_LOG);
    public static final DeferredItem<Item> STRIPPED_RED_CHERRY_WOOD  = block(ModBlocks.STRIPPED_RED_CHERRY_WOOD);

    public static final DeferredItem<Item> BLACK_CHERRY_LOG            = block(ModBlocks.BLACK_CHERRY_LOG);
    public static final DeferredItem<Item> BLACK_CHERRY_WOOD           = block(ModBlocks.BLACK_CHERRY_WOOD);
    public static final DeferredItem<Item> BLACK_CHERRY_PLANKS         = block(ModBlocks.BLACK_CHERRY_PLANKS);
    public static final DeferredItem<Item> BLACK_CHERRY_LEAVES         = block(ModBlocks.BLACK_CHERRY_LEAVES);
    public static final DeferredItem<Item> BLACK_CHERRY_STAIRS         = block(ModBlocks.BLACK_CHERRY_STAIRS);
    public static final DeferredItem<Item> BLACK_CHERRY_SLAB           = block(ModBlocks.BLACK_CHERRY_SLAB);
    public static final DeferredItem<Item> BLACK_CHERRY_FENCE          = block(ModBlocks.BLACK_CHERRY_FENCE);
    public static final DeferredItem<Item> BLACK_CHERRY_FENCE_GATE     = block(ModBlocks.BLACK_CHERRY_FENCE_GATE);
    public static final DeferredItem<Item> BLACK_CHERRY_PRESSURE_PLATE = block(ModBlocks.BLACK_CHERRY_PRESSURE_PLATE);
    public static final DeferredItem<Item> BLACK_CHERRY_BUTTON         = block(ModBlocks.BLACK_CHERRY_BUTTON);
    public static final DeferredItem<Item> BLACK_CHERRY_DOOR           = door(ModBlocks.BLACK_CHERRY_DOOR);
    public static final DeferredItem<Item> BLACK_CHERRY_TRAPDOOR       = block(ModBlocks.BLACK_CHERRY_TRAPDOOR);
    public static final DeferredItem<Item> BLACK_CHERRY_BRANCH         = block(ModBlocks.BLACK_CHERRY_BRANCH);
    public static final DeferredItem<Item> STRIPPED_BLACK_CHERRY_BRANCH = block(ModBlocks.STRIPPED_BLACK_CHERRY_BRANCH);
    public static final DeferredItem<Item> BLACK_CHERRY_SIGN           = REGISTRY.registerItem("black_cherry_sign",         p -> new SignItem(ModBlocks.BLACK_CHERRY_SIGN.get(), ModBlocks.BLACK_CHERRY_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> BLACK_CHERRY_HANGING_SIGN   = REGISTRY.registerItem("black_cherry_hanging_sign", p -> new HangingSignItem(ModBlocks.BLACK_CHERRY_HANGING_SIGN.get(), ModBlocks.BLACK_CHERRY_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> BLACK_CHERRY_BOAT           = REGISTRY.registerItem("black_cherry_boat",       p -> new GotBoatItem(ModBoatEntities.BLACK_CHERRY_BOAT.get(), p));
    public static final DeferredItem<Item> BLACK_CHERRY_CHEST_BOAT     = REGISTRY.registerItem("black_cherry_chest_boat", p -> new GotBoatItem(ModBoatEntities.BLACK_CHERRY_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> BLACK_CHERRY_SAPLING        = block(ModBlocks.BLACK_CHERRY_SAPLING);
    public static final DeferredItem<Item> STRIPPED_BLACK_CHERRY_LOG   = block(ModBlocks.STRIPPED_BLACK_CHERRY_LOG);
    public static final DeferredItem<Item> STRIPPED_BLACK_CHERRY_WOOD  = block(ModBlocks.STRIPPED_BLACK_CHERRY_WOOD);
    public static final DeferredItem<Item> BLACK_CHERRY = REGISTRY.registerItem("black_cherry", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.4f).build()));

    public static final DeferredItem<Item> WHITE_CHERRY_LOG            = block(ModBlocks.WHITE_CHERRY_LOG);
    public static final DeferredItem<Item> WHITE_CHERRY_WOOD           = block(ModBlocks.WHITE_CHERRY_WOOD);
    public static final DeferredItem<Item> WHITE_CHERRY_PLANKS         = block(ModBlocks.WHITE_CHERRY_PLANKS);
    public static final DeferredItem<Item> WHITE_CHERRY_LEAVES         = block(ModBlocks.WHITE_CHERRY_LEAVES);
    public static final DeferredItem<Item> WHITE_CHERRY_STAIRS         = block(ModBlocks.WHITE_CHERRY_STAIRS);
    public static final DeferredItem<Item> WHITE_CHERRY_SLAB           = block(ModBlocks.WHITE_CHERRY_SLAB);
    public static final DeferredItem<Item> WHITE_CHERRY_FENCE          = block(ModBlocks.WHITE_CHERRY_FENCE);
    public static final DeferredItem<Item> WHITE_CHERRY_FENCE_GATE     = block(ModBlocks.WHITE_CHERRY_FENCE_GATE);
    public static final DeferredItem<Item> WHITE_CHERRY_PRESSURE_PLATE = block(ModBlocks.WHITE_CHERRY_PRESSURE_PLATE);
    public static final DeferredItem<Item> WHITE_CHERRY_BUTTON         = block(ModBlocks.WHITE_CHERRY_BUTTON);
    public static final DeferredItem<Item> WHITE_CHERRY_DOOR           = door(ModBlocks.WHITE_CHERRY_DOOR);
    public static final DeferredItem<Item> WHITE_CHERRY_TRAPDOOR       = block(ModBlocks.WHITE_CHERRY_TRAPDOOR);
    public static final DeferredItem<Item> WHITE_CHERRY_BRANCH         = block(ModBlocks.WHITE_CHERRY_BRANCH);
    public static final DeferredItem<Item> STRIPPED_WHITE_CHERRY_BRANCH = block(ModBlocks.STRIPPED_WHITE_CHERRY_BRANCH);
    public static final DeferredItem<Item> WHITE_CHERRY_SIGN           = REGISTRY.registerItem("white_cherry_sign",         p -> new SignItem(ModBlocks.WHITE_CHERRY_SIGN.get(), ModBlocks.WHITE_CHERRY_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> WHITE_CHERRY_HANGING_SIGN   = REGISTRY.registerItem("white_cherry_hanging_sign", p -> new HangingSignItem(ModBlocks.WHITE_CHERRY_HANGING_SIGN.get(), ModBlocks.WHITE_CHERRY_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> WHITE_CHERRY_BOAT           = REGISTRY.registerItem("white_cherry_boat",       p -> new GotBoatItem(ModBoatEntities.WHITE_CHERRY_BOAT.get(), p));
    public static final DeferredItem<Item> WHITE_CHERRY_CHEST_BOAT     = REGISTRY.registerItem("white_cherry_chest_boat", p -> new GotBoatItem(ModBoatEntities.WHITE_CHERRY_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> WHITE_CHERRY_SAPLING        = block(ModBlocks.WHITE_CHERRY_SAPLING);
    public static final DeferredItem<Item> STRIPPED_WHITE_CHERRY_LOG   = block(ModBlocks.STRIPPED_WHITE_CHERRY_LOG);
    public static final DeferredItem<Item> STRIPPED_WHITE_CHERRY_WOOD  = block(ModBlocks.STRIPPED_WHITE_CHERRY_WOOD);
    public static final DeferredItem<Item> WHITE_CHERRY = REGISTRY.registerItem("white_cherry", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.4f).build()));
    public static final DeferredItem<Item> RED_CHERRY = REGISTRY.registerItem("red_cherry", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.4f).build()));

    public static final DeferredItem<Item> CRABAPPLE_LOG            = block(ModBlocks.CRABAPPLE_LOG);
    public static final DeferredItem<Item> CRABAPPLE_WOOD           = block(ModBlocks.CRABAPPLE_WOOD);
    public static final DeferredItem<Item> CRABAPPLE_PLANKS         = block(ModBlocks.CRABAPPLE_PLANKS);
    public static final DeferredItem<Item> CRABAPPLE_LEAVES         = block(ModBlocks.CRABAPPLE_LEAVES);
    public static final DeferredItem<Item> CRABAPPLE_STAIRS         = block(ModBlocks.CRABAPPLE_STAIRS);
    public static final DeferredItem<Item> CRABAPPLE_SLAB           = block(ModBlocks.CRABAPPLE_SLAB);
    public static final DeferredItem<Item> CRABAPPLE_FENCE          = block(ModBlocks.CRABAPPLE_FENCE);
    public static final DeferredItem<Item> CRABAPPLE_FENCE_GATE     = block(ModBlocks.CRABAPPLE_FENCE_GATE);
    public static final DeferredItem<Item> CRABAPPLE_PRESSURE_PLATE = block(ModBlocks.CRABAPPLE_PRESSURE_PLATE);
    public static final DeferredItem<Item> CRABAPPLE_BUTTON         = block(ModBlocks.CRABAPPLE_BUTTON);
    public static final DeferredItem<Item> CRABAPPLE_DOOR           = door(ModBlocks.CRABAPPLE_DOOR);
    public static final DeferredItem<Item> CRABAPPLE_TRAPDOOR       = block(ModBlocks.CRABAPPLE_TRAPDOOR);
    public static final DeferredItem<Item> CRABAPPLE_BRANCH         = block(ModBlocks.CRABAPPLE_BRANCH);
    public static final DeferredItem<Item> STRIPPED_CRABAPPLE_BRANCH = block(ModBlocks.STRIPPED_CRABAPPLE_BRANCH);
    public static final DeferredItem<Item> CRABAPPLE_SIGN           = REGISTRY.registerItem("crabapple_sign",         p -> new SignItem(ModBlocks.CRABAPPLE_SIGN.get(), ModBlocks.CRABAPPLE_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> CRABAPPLE_HANGING_SIGN   = REGISTRY.registerItem("crabapple_hanging_sign", p -> new HangingSignItem(ModBlocks.CRABAPPLE_HANGING_SIGN.get(), ModBlocks.CRABAPPLE_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> CRABAPPLE_BOAT           = REGISTRY.registerItem("crabapple_boat",       p -> new GotBoatItem(ModBoatEntities.CRABAPPLE_BOAT.get(), p));
    public static final DeferredItem<Item> CRABAPPLE_CHEST_BOAT     = REGISTRY.registerItem("crabapple_chest_boat", p -> new GotBoatItem(ModBoatEntities.CRABAPPLE_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> CRABAPPLE_SAPLING        = block(ModBlocks.CRABAPPLE_SAPLING);
    public static final DeferredItem<Item> STRIPPED_CRABAPPLE_LOG   = block(ModBlocks.STRIPPED_CRABAPPLE_LOG);
    public static final DeferredItem<Item> STRIPPED_CRABAPPLE_WOOD  = block(ModBlocks.STRIPPED_CRABAPPLE_WOOD);
    public static final DeferredItem<BlockItem> CRABAPPLE_ROOFING = REGISTRY.registerSimpleBlockItem("crabapple_roofing", ModBlocks.CRABAPPLE_ROOFING);
    public static final DeferredItem<BlockItem> CRABAPPLE_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("crabapple_roofing_slab", ModBlocks.CRABAPPLE_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> CRABAPPLE_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("crabapple_roofing_stairs", ModBlocks.CRABAPPLE_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> CRABAPPLE_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("crabapple_roofing_wall", ModBlocks.CRABAPPLE_ROOFING_WALL);
    public static final DeferredItem<Item> CRABAPPLE = REGISTRY.registerItem("crabapple", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.3f).build()));

    public static final DeferredItem<Item> DATE_PALM_LOG            = block(ModBlocks.DATE_PALM_LOG);
    public static final DeferredItem<Item> DATE_PALM_WOOD           = block(ModBlocks.DATE_PALM_WOOD);
    public static final DeferredItem<Item> DATE_PALM_PLANKS         = block(ModBlocks.DATE_PALM_PLANKS);
    public static final DeferredItem<Item> DATE_PALM_LEAVES         = block(ModBlocks.DATE_PALM_LEAVES);
    public static final DeferredItem<Item> DATE_PALM_STAIRS         = block(ModBlocks.DATE_PALM_STAIRS);
    public static final DeferredItem<Item> DATE_PALM_SLAB           = block(ModBlocks.DATE_PALM_SLAB);
    public static final DeferredItem<Item> DATE_PALM_FENCE          = block(ModBlocks.DATE_PALM_FENCE);
    public static final DeferredItem<Item> DATE_PALM_FENCE_GATE     = block(ModBlocks.DATE_PALM_FENCE_GATE);
    public static final DeferredItem<Item> DATE_PALM_PRESSURE_PLATE = block(ModBlocks.DATE_PALM_PRESSURE_PLATE);
    public static final DeferredItem<Item> DATE_PALM_BUTTON         = block(ModBlocks.DATE_PALM_BUTTON);
    public static final DeferredItem<Item> DATE_PALM_DOOR           = door(ModBlocks.DATE_PALM_DOOR);
    public static final DeferredItem<Item> DATE_PALM_TRAPDOOR       = block(ModBlocks.DATE_PALM_TRAPDOOR);
    public static final DeferredItem<Item> DATE_PALM_BRANCH         = block(ModBlocks.DATE_PALM_BRANCH);
    public static final DeferredItem<Item> STRIPPED_DATE_PALM_BRANCH = block(ModBlocks.STRIPPED_DATE_PALM_BRANCH);
    public static final DeferredItem<Item> DATE_PALM_SIGN           = REGISTRY.registerItem("date_palm_sign",         p -> new SignItem(ModBlocks.DATE_PALM_SIGN.get(), ModBlocks.DATE_PALM_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> DATE_PALM_HANGING_SIGN   = REGISTRY.registerItem("date_palm_hanging_sign", p -> new HangingSignItem(ModBlocks.DATE_PALM_HANGING_SIGN.get(), ModBlocks.DATE_PALM_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> DATE_PALM_BOAT           = REGISTRY.registerItem("date_palm_boat",       p -> new GotBoatItem(ModBoatEntities.DATE_PALM_BOAT.get(), p));
    public static final DeferredItem<Item> DATE_PALM_CHEST_BOAT     = REGISTRY.registerItem("date_palm_chest_boat", p -> new GotBoatItem(ModBoatEntities.DATE_PALM_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> DATE_PALM_SAPLING        = block(ModBlocks.DATE_PALM_SAPLING);
    public static final DeferredItem<Item> STRIPPED_DATE_PALM_LOG   = block(ModBlocks.STRIPPED_DATE_PALM_LOG);
    public static final DeferredItem<Item> STRIPPED_DATE_PALM_WOOD  = block(ModBlocks.STRIPPED_DATE_PALM_WOOD);
    public static final DeferredItem<BlockItem> DATE_PALM_ROOFING = REGISTRY.registerSimpleBlockItem("date_palm_roofing", ModBlocks.DATE_PALM_ROOFING);
    public static final DeferredItem<BlockItem> DATE_PALM_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("date_palm_roofing_slab", ModBlocks.DATE_PALM_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> DATE_PALM_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("date_palm_roofing_stairs", ModBlocks.DATE_PALM_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> DATE_PALM_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("date_palm_roofing_wall", ModBlocks.DATE_PALM_ROOFING_WALL);
    public static final DeferredItem<Item> DATE = REGISTRY.registerItem("date", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(5).saturationModifier(0.6f).build()));

    public static final DeferredItem<Item> FIG_LOG            = block(ModBlocks.FIG_LOG);
    public static final DeferredItem<Item> FIG_WOOD           = block(ModBlocks.FIG_WOOD);
    public static final DeferredItem<Item> FIG_PLANKS         = block(ModBlocks.FIG_PLANKS);
    public static final DeferredItem<Item> FIG_LEAVES         = block(ModBlocks.FIG_LEAVES);
    public static final DeferredItem<Item> FIG_STAIRS         = block(ModBlocks.FIG_STAIRS);
    public static final DeferredItem<Item> FIG_SLAB           = block(ModBlocks.FIG_SLAB);
    public static final DeferredItem<Item> FIG_FENCE          = block(ModBlocks.FIG_FENCE);
    public static final DeferredItem<Item> FIG_FENCE_GATE     = block(ModBlocks.FIG_FENCE_GATE);
    public static final DeferredItem<Item> FIG_PRESSURE_PLATE = block(ModBlocks.FIG_PRESSURE_PLATE);
    public static final DeferredItem<Item> FIG_BUTTON         = block(ModBlocks.FIG_BUTTON);
    public static final DeferredItem<Item> FIG_DOOR           = door(ModBlocks.FIG_DOOR);
    public static final DeferredItem<Item> FIG_TRAPDOOR       = block(ModBlocks.FIG_TRAPDOOR);
    public static final DeferredItem<Item> FIG_BRANCH         = block(ModBlocks.FIG_BRANCH);
    public static final DeferredItem<Item> STRIPPED_FIG_BRANCH = block(ModBlocks.STRIPPED_FIG_BRANCH);
    public static final DeferredItem<Item> FIG_SIGN           = REGISTRY.registerItem("fig_sign",         p -> new SignItem(ModBlocks.FIG_SIGN.get(), ModBlocks.FIG_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> FIG_HANGING_SIGN   = REGISTRY.registerItem("fig_hanging_sign", p -> new HangingSignItem(ModBlocks.FIG_HANGING_SIGN.get(), ModBlocks.FIG_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> FIG_BOAT           = REGISTRY.registerItem("fig_boat",       p -> new GotBoatItem(ModBoatEntities.FIG_BOAT.get(), p));
    public static final DeferredItem<Item> FIG_CHEST_BOAT     = REGISTRY.registerItem("fig_chest_boat", p -> new GotBoatItem(ModBoatEntities.FIG_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> FIG_SAPLING        = block(ModBlocks.FIG_SAPLING);
    public static final DeferredItem<Item> STRIPPED_FIG_LOG   = block(ModBlocks.STRIPPED_FIG_LOG);
    public static final DeferredItem<Item> STRIPPED_FIG_WOOD  = block(ModBlocks.STRIPPED_FIG_WOOD);
    public static final DeferredItem<BlockItem> FIG_ROOFING = REGISTRY.registerSimpleBlockItem("fig_roofing", ModBlocks.FIG_ROOFING);
    public static final DeferredItem<BlockItem> FIG_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("fig_roofing_slab", ModBlocks.FIG_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> FIG_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("fig_roofing_stairs", ModBlocks.FIG_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> FIG_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("fig_roofing_wall", ModBlocks.FIG_ROOFING_WALL);
    public static final DeferredItem<Item> FIG = REGISTRY.registerItem("fig", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.5f).build()));

    public static final DeferredItem<Item> LEMON_LOG            = block(ModBlocks.LEMON_LOG);
    public static final DeferredItem<Item> LEMON_WOOD           = block(ModBlocks.LEMON_WOOD);
    public static final DeferredItem<Item> LEMON_PLANKS         = block(ModBlocks.LEMON_PLANKS);
    public static final DeferredItem<Item> LEMON_LEAVES         = block(ModBlocks.LEMON_LEAVES);
    public static final DeferredItem<Item> LEMON_STAIRS         = block(ModBlocks.LEMON_STAIRS);
    public static final DeferredItem<Item> LEMON_SLAB           = block(ModBlocks.LEMON_SLAB);
    public static final DeferredItem<Item> LEMON_FENCE          = block(ModBlocks.LEMON_FENCE);
    public static final DeferredItem<Item> LEMON_FENCE_GATE     = block(ModBlocks.LEMON_FENCE_GATE);
    public static final DeferredItem<Item> LEMON_PRESSURE_PLATE = block(ModBlocks.LEMON_PRESSURE_PLATE);
    public static final DeferredItem<Item> LEMON_BUTTON         = block(ModBlocks.LEMON_BUTTON);
    public static final DeferredItem<Item> LEMON_DOOR           = door(ModBlocks.LEMON_DOOR);
    public static final DeferredItem<Item> LEMON_TRAPDOOR       = block(ModBlocks.LEMON_TRAPDOOR);
    public static final DeferredItem<Item> LEMON_BRANCH         = block(ModBlocks.LEMON_BRANCH);
    public static final DeferredItem<Item> STRIPPED_LEMON_BRANCH = block(ModBlocks.STRIPPED_LEMON_BRANCH);
    public static final DeferredItem<Item> LEMON_SIGN           = REGISTRY.registerItem("lemon_sign",         p -> new SignItem(ModBlocks.LEMON_SIGN.get(), ModBlocks.LEMON_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> LEMON_HANGING_SIGN   = REGISTRY.registerItem("lemon_hanging_sign", p -> new HangingSignItem(ModBlocks.LEMON_HANGING_SIGN.get(), ModBlocks.LEMON_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> LEMON_BOAT           = REGISTRY.registerItem("lemon_boat",       p -> new GotBoatItem(ModBoatEntities.LEMON_BOAT.get(), p));
    public static final DeferredItem<Item> LEMON_CHEST_BOAT     = REGISTRY.registerItem("lemon_chest_boat", p -> new GotBoatItem(ModBoatEntities.LEMON_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> LEMON_SAPLING        = block(ModBlocks.LEMON_SAPLING);
    public static final DeferredItem<Item> STRIPPED_LEMON_LOG   = block(ModBlocks.STRIPPED_LEMON_LOG);
    public static final DeferredItem<Item> STRIPPED_LEMON_WOOD  = block(ModBlocks.STRIPPED_LEMON_WOOD);
    public static final DeferredItem<BlockItem> LEMON_ROOFING = REGISTRY.registerSimpleBlockItem("lemon_roofing", ModBlocks.LEMON_ROOFING);
    public static final DeferredItem<BlockItem> LEMON_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("lemon_roofing_slab", ModBlocks.LEMON_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> LEMON_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("lemon_roofing_stairs", ModBlocks.LEMON_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> LEMON_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("lemon_roofing_wall", ModBlocks.LEMON_ROOFING_WALL);
    public static final DeferredItem<Item> LEMON = REGISTRY.registerItem("lemon", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.3f).build()));

    public static final DeferredItem<Item> LIME_LOG            = block(ModBlocks.LIME_LOG);
    public static final DeferredItem<Item> LIME_WOOD           = block(ModBlocks.LIME_WOOD);
    public static final DeferredItem<Item> LIME_PLANKS         = block(ModBlocks.LIME_PLANKS);
    public static final DeferredItem<Item> LIME_LEAVES         = block(ModBlocks.LIME_LEAVES);
    public static final DeferredItem<Item> LIME_STAIRS         = block(ModBlocks.LIME_STAIRS);
    public static final DeferredItem<Item> LIME_SLAB           = block(ModBlocks.LIME_SLAB);
    public static final DeferredItem<Item> LIME_FENCE          = block(ModBlocks.LIME_FENCE);
    public static final DeferredItem<Item> LIME_FENCE_GATE     = block(ModBlocks.LIME_FENCE_GATE);
    public static final DeferredItem<Item> LIME_PRESSURE_PLATE = block(ModBlocks.LIME_PRESSURE_PLATE);
    public static final DeferredItem<Item> LIME_BUTTON         = block(ModBlocks.LIME_BUTTON);
    public static final DeferredItem<Item> LIME_DOOR           = door(ModBlocks.LIME_DOOR);
    public static final DeferredItem<Item> LIME_TRAPDOOR       = block(ModBlocks.LIME_TRAPDOOR);
    public static final DeferredItem<Item> LIME_BRANCH         = block(ModBlocks.LIME_BRANCH);
    public static final DeferredItem<Item> STRIPPED_LIME_BRANCH = block(ModBlocks.STRIPPED_LIME_BRANCH);
    public static final DeferredItem<Item> LIME_SIGN           = REGISTRY.registerItem("lime_sign",         p -> new SignItem(ModBlocks.LIME_SIGN.get(), ModBlocks.LIME_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> LIME_HANGING_SIGN   = REGISTRY.registerItem("lime_hanging_sign", p -> new HangingSignItem(ModBlocks.LIME_HANGING_SIGN.get(), ModBlocks.LIME_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> LIME_BOAT           = REGISTRY.registerItem("lime_boat",       p -> new GotBoatItem(ModBoatEntities.LIME_BOAT.get(), p));
    public static final DeferredItem<Item> LIME_CHEST_BOAT     = REGISTRY.registerItem("lime_chest_boat", p -> new GotBoatItem(ModBoatEntities.LIME_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> LIME_SAPLING        = block(ModBlocks.LIME_SAPLING);
    public static final DeferredItem<Item> STRIPPED_LIME_LOG   = block(ModBlocks.STRIPPED_LIME_LOG);
    public static final DeferredItem<Item> STRIPPED_LIME_WOOD  = block(ModBlocks.STRIPPED_LIME_WOOD);
    public static final DeferredItem<BlockItem> LIME_ROOFING = REGISTRY.registerSimpleBlockItem("lime_roofing", ModBlocks.LIME_ROOFING);
    public static final DeferredItem<BlockItem> LIME_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("lime_roofing_slab", ModBlocks.LIME_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> LIME_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("lime_roofing_stairs", ModBlocks.LIME_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> LIME_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("lime_roofing_wall", ModBlocks.LIME_ROOFING_WALL);
    public static final DeferredItem<Item> LIME = REGISTRY.registerItem("lime", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.3f).build()));

    public static final DeferredItem<Item> OLIVE_LOG            = block(ModBlocks.OLIVE_LOG);
    public static final DeferredItem<Item> OLIVE_WOOD           = block(ModBlocks.OLIVE_WOOD);
    public static final DeferredItem<Item> OLIVE_PLANKS         = block(ModBlocks.OLIVE_PLANKS);
    public static final DeferredItem<Item> OLIVE_LEAVES         = block(ModBlocks.OLIVE_LEAVES);
    public static final DeferredItem<Item> OLIVE_STAIRS         = block(ModBlocks.OLIVE_STAIRS);
    public static final DeferredItem<Item> OLIVE_SLAB           = block(ModBlocks.OLIVE_SLAB);
    public static final DeferredItem<Item> OLIVE_FENCE          = block(ModBlocks.OLIVE_FENCE);
    public static final DeferredItem<Item> OLIVE_FENCE_GATE     = block(ModBlocks.OLIVE_FENCE_GATE);
    public static final DeferredItem<Item> OLIVE_PRESSURE_PLATE = block(ModBlocks.OLIVE_PRESSURE_PLATE);
    public static final DeferredItem<Item> OLIVE_BUTTON         = block(ModBlocks.OLIVE_BUTTON);
    public static final DeferredItem<Item> OLIVE_DOOR           = door(ModBlocks.OLIVE_DOOR);
    public static final DeferredItem<Item> OLIVE_TRAPDOOR       = block(ModBlocks.OLIVE_TRAPDOOR);
    public static final DeferredItem<Item> OLIVE_BRANCH         = block(ModBlocks.OLIVE_BRANCH);
    public static final DeferredItem<Item> STRIPPED_OLIVE_BRANCH = block(ModBlocks.STRIPPED_OLIVE_BRANCH);
    public static final DeferredItem<Item> OLIVE_SIGN           = REGISTRY.registerItem("olive_sign",         p -> new SignItem(ModBlocks.OLIVE_SIGN.get(), ModBlocks.OLIVE_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> OLIVE_HANGING_SIGN   = REGISTRY.registerItem("olive_hanging_sign", p -> new HangingSignItem(ModBlocks.OLIVE_HANGING_SIGN.get(), ModBlocks.OLIVE_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> OLIVE_BOAT           = REGISTRY.registerItem("olive_boat",       p -> new GotBoatItem(ModBoatEntities.OLIVE_BOAT.get(), p));
    public static final DeferredItem<Item> OLIVE_CHEST_BOAT     = REGISTRY.registerItem("olive_chest_boat", p -> new GotBoatItem(ModBoatEntities.OLIVE_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> OLIVE_SAPLING        = block(ModBlocks.OLIVE_SAPLING);
    public static final DeferredItem<Item> STRIPPED_OLIVE_LOG   = block(ModBlocks.STRIPPED_OLIVE_LOG);
    public static final DeferredItem<Item> STRIPPED_OLIVE_WOOD  = block(ModBlocks.STRIPPED_OLIVE_WOOD);
    public static final DeferredItem<BlockItem> OLIVE_ROOFING = REGISTRY.registerSimpleBlockItem("olive_roofing", ModBlocks.OLIVE_ROOFING);
    public static final DeferredItem<BlockItem> OLIVE_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("olive_roofing_slab", ModBlocks.OLIVE_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> OLIVE_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("olive_roofing_stairs", ModBlocks.OLIVE_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> OLIVE_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("olive_roofing_wall", ModBlocks.OLIVE_ROOFING_WALL);
    public static final DeferredItem<Item> OLIVE = REGISTRY.registerItem("olive", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.5f).build()));

    public static final DeferredItem<Item> ORANGE_LOG            = block(ModBlocks.ORANGE_LOG);
    public static final DeferredItem<Item> ORANGE_WOOD           = block(ModBlocks.ORANGE_WOOD);
    public static final DeferredItem<Item> ORANGE_PLANKS         = block(ModBlocks.ORANGE_PLANKS);
    public static final DeferredItem<Item> ORANGE_LEAVES         = block(ModBlocks.ORANGE_LEAVES);
    public static final DeferredItem<Item> ORANGE_STAIRS         = block(ModBlocks.ORANGE_STAIRS);
    public static final DeferredItem<Item> ORANGE_SLAB           = block(ModBlocks.ORANGE_SLAB);
    public static final DeferredItem<Item> ORANGE_FENCE          = block(ModBlocks.ORANGE_FENCE);
    public static final DeferredItem<Item> ORANGE_FENCE_GATE     = block(ModBlocks.ORANGE_FENCE_GATE);
    public static final DeferredItem<Item> ORANGE_PRESSURE_PLATE = block(ModBlocks.ORANGE_PRESSURE_PLATE);
    public static final DeferredItem<Item> ORANGE_BUTTON         = block(ModBlocks.ORANGE_BUTTON);
    public static final DeferredItem<Item> ORANGE_DOOR           = door(ModBlocks.ORANGE_DOOR);
    public static final DeferredItem<Item> ORANGE_TRAPDOOR       = block(ModBlocks.ORANGE_TRAPDOOR);
    public static final DeferredItem<Item> ORANGE_BRANCH         = block(ModBlocks.ORANGE_BRANCH);
    public static final DeferredItem<Item> STRIPPED_ORANGE_BRANCH = block(ModBlocks.STRIPPED_ORANGE_BRANCH);
    public static final DeferredItem<Item> ORANGE_SIGN           = REGISTRY.registerItem("orange_sign",         p -> new SignItem(ModBlocks.ORANGE_SIGN.get(), ModBlocks.ORANGE_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> ORANGE_HANGING_SIGN   = REGISTRY.registerItem("orange_hanging_sign", p -> new HangingSignItem(ModBlocks.ORANGE_HANGING_SIGN.get(), ModBlocks.ORANGE_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> ORANGE_BOAT           = REGISTRY.registerItem("orange_boat",       p -> new GotBoatItem(ModBoatEntities.ORANGE_BOAT.get(), p));
    public static final DeferredItem<Item> ORANGE_CHEST_BOAT     = REGISTRY.registerItem("orange_chest_boat", p -> new GotBoatItem(ModBoatEntities.ORANGE_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> ORANGE_SAPLING        = block(ModBlocks.ORANGE_SAPLING);
    public static final DeferredItem<Item> STRIPPED_ORANGE_LOG   = block(ModBlocks.STRIPPED_ORANGE_LOG);
    public static final DeferredItem<Item> STRIPPED_ORANGE_WOOD  = block(ModBlocks.STRIPPED_ORANGE_WOOD);
    public static final DeferredItem<BlockItem> ORANGE_ROOFING = REGISTRY.registerSimpleBlockItem("orange_roofing", ModBlocks.ORANGE_ROOFING);
    public static final DeferredItem<BlockItem> ORANGE_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("orange_roofing_slab", ModBlocks.ORANGE_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> ORANGE_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("orange_roofing_stairs", ModBlocks.ORANGE_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> ORANGE_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("orange_roofing_wall", ModBlocks.ORANGE_ROOFING_WALL);
    public static final DeferredItem<Item> ORANGE = REGISTRY.registerItem("orange", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.5f).build()));

    public static final DeferredItem<Item> PEACH_LOG            = block(ModBlocks.PEACH_LOG);
    public static final DeferredItem<Item> PEACH_WOOD           = block(ModBlocks.PEACH_WOOD);
    public static final DeferredItem<Item> PEACH_PLANKS         = block(ModBlocks.PEACH_PLANKS);
    public static final DeferredItem<Item> PEACH_LEAVES         = block(ModBlocks.PEACH_LEAVES);
    public static final DeferredItem<Item> PEACH_STAIRS         = block(ModBlocks.PEACH_STAIRS);
    public static final DeferredItem<Item> PEACH_SLAB           = block(ModBlocks.PEACH_SLAB);
    public static final DeferredItem<Item> PEACH_FENCE          = block(ModBlocks.PEACH_FENCE);
    public static final DeferredItem<Item> PEACH_FENCE_GATE     = block(ModBlocks.PEACH_FENCE_GATE);
    public static final DeferredItem<Item> PEACH_PRESSURE_PLATE = block(ModBlocks.PEACH_PRESSURE_PLATE);
    public static final DeferredItem<Item> PEACH_BUTTON         = block(ModBlocks.PEACH_BUTTON);
    public static final DeferredItem<Item> PEACH_DOOR           = door(ModBlocks.PEACH_DOOR);
    public static final DeferredItem<Item> PEACH_TRAPDOOR       = block(ModBlocks.PEACH_TRAPDOOR);
    public static final DeferredItem<Item> PEACH_BRANCH         = block(ModBlocks.PEACH_BRANCH);
    public static final DeferredItem<Item> STRIPPED_PEACH_BRANCH = block(ModBlocks.STRIPPED_PEACH_BRANCH);
    public static final DeferredItem<Item> PEACH_SIGN           = REGISTRY.registerItem("peach_sign",         p -> new SignItem(ModBlocks.PEACH_SIGN.get(), ModBlocks.PEACH_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> PEACH_HANGING_SIGN   = REGISTRY.registerItem("peach_hanging_sign", p -> new HangingSignItem(ModBlocks.PEACH_HANGING_SIGN.get(), ModBlocks.PEACH_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> PEACH_BOAT           = REGISTRY.registerItem("peach_boat",       p -> new GotBoatItem(ModBoatEntities.PEACH_BOAT.get(), p));
    public static final DeferredItem<Item> PEACH_CHEST_BOAT     = REGISTRY.registerItem("peach_chest_boat", p -> new GotBoatItem(ModBoatEntities.PEACH_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> PEACH_SAPLING        = block(ModBlocks.PEACH_SAPLING);
    public static final DeferredItem<Item> STRIPPED_PEACH_LOG   = block(ModBlocks.STRIPPED_PEACH_LOG);
    public static final DeferredItem<Item> STRIPPED_PEACH_WOOD  = block(ModBlocks.STRIPPED_PEACH_WOOD);
    public static final DeferredItem<BlockItem> PEACH_ROOFING = REGISTRY.registerSimpleBlockItem("peach_roofing", ModBlocks.PEACH_ROOFING);
    public static final DeferredItem<BlockItem> PEACH_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("peach_roofing_slab", ModBlocks.PEACH_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> PEACH_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("peach_roofing_stairs", ModBlocks.PEACH_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> PEACH_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("peach_roofing_wall", ModBlocks.PEACH_ROOFING_WALL);
    public static final DeferredItem<Item> PEACH = REGISTRY.registerItem("peach", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(5).saturationModifier(0.6f).build()));

    public static final DeferredItem<Item> PEAR_LOG            = block(ModBlocks.PEAR_LOG);
    public static final DeferredItem<Item> PEAR_WOOD           = block(ModBlocks.PEAR_WOOD);
    public static final DeferredItem<Item> PEAR_PLANKS         = block(ModBlocks.PEAR_PLANKS);
    public static final DeferredItem<Item> PEAR_LEAVES         = block(ModBlocks.PEAR_LEAVES);
    public static final DeferredItem<Item> PEAR_STAIRS         = block(ModBlocks.PEAR_STAIRS);
    public static final DeferredItem<Item> PEAR_SLAB           = block(ModBlocks.PEAR_SLAB);
    public static final DeferredItem<Item> PEAR_FENCE          = block(ModBlocks.PEAR_FENCE);
    public static final DeferredItem<Item> PEAR_FENCE_GATE     = block(ModBlocks.PEAR_FENCE_GATE);
    public static final DeferredItem<Item> PEAR_PRESSURE_PLATE = block(ModBlocks.PEAR_PRESSURE_PLATE);
    public static final DeferredItem<Item> PEAR_BUTTON         = block(ModBlocks.PEAR_BUTTON);
    public static final DeferredItem<Item> PEAR_DOOR           = door(ModBlocks.PEAR_DOOR);
    public static final DeferredItem<Item> PEAR_TRAPDOOR       = block(ModBlocks.PEAR_TRAPDOOR);
    public static final DeferredItem<Item> PEAR_BRANCH         = block(ModBlocks.PEAR_BRANCH);
    public static final DeferredItem<Item> STRIPPED_PEAR_BRANCH = block(ModBlocks.STRIPPED_PEAR_BRANCH);
    public static final DeferredItem<Item> PEAR_SIGN           = REGISTRY.registerItem("pear_sign",         p -> new SignItem(ModBlocks.PEAR_SIGN.get(), ModBlocks.PEAR_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> PEAR_HANGING_SIGN   = REGISTRY.registerItem("pear_hanging_sign", p -> new HangingSignItem(ModBlocks.PEAR_HANGING_SIGN.get(), ModBlocks.PEAR_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> PEAR_BOAT           = REGISTRY.registerItem("pear_boat",       p -> new GotBoatItem(ModBoatEntities.PEAR_BOAT.get(), p));
    public static final DeferredItem<Item> PEAR_CHEST_BOAT     = REGISTRY.registerItem("pear_chest_boat", p -> new GotBoatItem(ModBoatEntities.PEAR_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> PEAR_SAPLING        = block(ModBlocks.PEAR_SAPLING);
    public static final DeferredItem<Item> STRIPPED_PEAR_LOG   = block(ModBlocks.STRIPPED_PEAR_LOG);
    public static final DeferredItem<Item> STRIPPED_PEAR_WOOD  = block(ModBlocks.STRIPPED_PEAR_WOOD);
    public static final DeferredItem<BlockItem> PEAR_ROOFING = REGISTRY.registerSimpleBlockItem("pear_roofing", ModBlocks.PEAR_ROOFING);
    public static final DeferredItem<BlockItem> PEAR_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("pear_roofing_slab", ModBlocks.PEAR_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> PEAR_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("pear_roofing_stairs", ModBlocks.PEAR_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> PEAR_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("pear_roofing_wall", ModBlocks.PEAR_ROOFING_WALL);
    public static final DeferredItem<Item> PEAR = REGISTRY.registerItem("pear", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.5f).build()));

    public static final DeferredItem<Item> PERSIMMON_LOG            = block(ModBlocks.PERSIMMON_LOG);
    public static final DeferredItem<Item> PERSIMMON_WOOD           = block(ModBlocks.PERSIMMON_WOOD);
    public static final DeferredItem<Item> PERSIMMON_PLANKS         = block(ModBlocks.PERSIMMON_PLANKS);
    public static final DeferredItem<Item> PERSIMMON_LEAVES         = block(ModBlocks.PERSIMMON_LEAVES);
    public static final DeferredItem<Item> PERSIMMON_STAIRS         = block(ModBlocks.PERSIMMON_STAIRS);
    public static final DeferredItem<Item> PERSIMMON_SLAB           = block(ModBlocks.PERSIMMON_SLAB);
    public static final DeferredItem<Item> PERSIMMON_FENCE          = block(ModBlocks.PERSIMMON_FENCE);
    public static final DeferredItem<Item> PERSIMMON_FENCE_GATE     = block(ModBlocks.PERSIMMON_FENCE_GATE);
    public static final DeferredItem<Item> PERSIMMON_PRESSURE_PLATE = block(ModBlocks.PERSIMMON_PRESSURE_PLATE);
    public static final DeferredItem<Item> PERSIMMON_BUTTON         = block(ModBlocks.PERSIMMON_BUTTON);
    public static final DeferredItem<Item> PERSIMMON_DOOR           = door(ModBlocks.PERSIMMON_DOOR);
    public static final DeferredItem<Item> PERSIMMON_TRAPDOOR       = block(ModBlocks.PERSIMMON_TRAPDOOR);
    public static final DeferredItem<Item> PERSIMMON_BRANCH         = block(ModBlocks.PERSIMMON_BRANCH);
    public static final DeferredItem<Item> STRIPPED_PERSIMMON_BRANCH = block(ModBlocks.STRIPPED_PERSIMMON_BRANCH);
    public static final DeferredItem<Item> PERSIMMON_SIGN           = REGISTRY.registerItem("persimmon_sign",         p -> new SignItem(ModBlocks.PERSIMMON_SIGN.get(), ModBlocks.PERSIMMON_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> PERSIMMON_HANGING_SIGN   = REGISTRY.registerItem("persimmon_hanging_sign", p -> new HangingSignItem(ModBlocks.PERSIMMON_HANGING_SIGN.get(), ModBlocks.PERSIMMON_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> PERSIMMON_BOAT           = REGISTRY.registerItem("persimmon_boat",       p -> new GotBoatItem(ModBoatEntities.PERSIMMON_BOAT.get(), p));
    public static final DeferredItem<Item> PERSIMMON_CHEST_BOAT     = REGISTRY.registerItem("persimmon_chest_boat", p -> new GotBoatItem(ModBoatEntities.PERSIMMON_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> PERSIMMON_SAPLING        = block(ModBlocks.PERSIMMON_SAPLING);
    public static final DeferredItem<Item> STRIPPED_PERSIMMON_LOG   = block(ModBlocks.STRIPPED_PERSIMMON_LOG);
    public static final DeferredItem<Item> STRIPPED_PERSIMMON_WOOD  = block(ModBlocks.STRIPPED_PERSIMMON_WOOD);
    public static final DeferredItem<BlockItem> PERSIMMON_ROOFING = REGISTRY.registerSimpleBlockItem("persimmon_roofing", ModBlocks.PERSIMMON_ROOFING);
    public static final DeferredItem<BlockItem> PERSIMMON_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("persimmon_roofing_slab", ModBlocks.PERSIMMON_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> PERSIMMON_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("persimmon_roofing_stairs", ModBlocks.PERSIMMON_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> PERSIMMON_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("persimmon_roofing_wall", ModBlocks.PERSIMMON_ROOFING_WALL);
    public static final DeferredItem<Item> PERSIMMON = REGISTRY.registerItem("persimmon", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.5f).build()));

    public static final DeferredItem<Item> PINK_IVORY_LOG            = block(ModBlocks.PINK_IVORY_LOG);
    public static final DeferredItem<Item> PINK_IVORY_WOOD           = block(ModBlocks.PINK_IVORY_WOOD);
    public static final DeferredItem<Item> PINK_IVORY_PLANKS         = block(ModBlocks.PINK_IVORY_PLANKS);
    public static final DeferredItem<Item> PINK_IVORY_LEAVES         = block(ModBlocks.PINK_IVORY_LEAVES);
    public static final DeferredItem<Item> PINK_IVORY_STAIRS         = block(ModBlocks.PINK_IVORY_STAIRS);
    public static final DeferredItem<Item> PINK_IVORY_SLAB           = block(ModBlocks.PINK_IVORY_SLAB);
    public static final DeferredItem<Item> PINK_IVORY_FENCE          = block(ModBlocks.PINK_IVORY_FENCE);
    public static final DeferredItem<Item> PINK_IVORY_FENCE_GATE     = block(ModBlocks.PINK_IVORY_FENCE_GATE);
    public static final DeferredItem<Item> PINK_IVORY_PRESSURE_PLATE = block(ModBlocks.PINK_IVORY_PRESSURE_PLATE);
    public static final DeferredItem<Item> PINK_IVORY_BUTTON         = block(ModBlocks.PINK_IVORY_BUTTON);
    public static final DeferredItem<Item> PINK_IVORY_DOOR           = door(ModBlocks.PINK_IVORY_DOOR);
    public static final DeferredItem<Item> PINK_IVORY_TRAPDOOR       = block(ModBlocks.PINK_IVORY_TRAPDOOR);
    public static final DeferredItem<Item> PINK_IVORY_BRANCH         = block(ModBlocks.PINK_IVORY_BRANCH);
    public static final DeferredItem<Item> STRIPPED_PINK_IVORY_BRANCH = block(ModBlocks.STRIPPED_PINK_IVORY_BRANCH);
    public static final DeferredItem<Item> PINK_IVORY_SIGN           = REGISTRY.registerItem("pink_ivory_sign",         p -> new SignItem(ModBlocks.PINK_IVORY_SIGN.get(), ModBlocks.PINK_IVORY_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> PINK_IVORY_HANGING_SIGN   = REGISTRY.registerItem("pink_ivory_hanging_sign", p -> new HangingSignItem(ModBlocks.PINK_IVORY_HANGING_SIGN.get(), ModBlocks.PINK_IVORY_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> PINK_IVORY_BOAT           = REGISTRY.registerItem("pink_ivory_boat",       p -> new GotBoatItem(ModBoatEntities.PINK_IVORY_BOAT.get(), p));
    public static final DeferredItem<Item> PINK_IVORY_CHEST_BOAT     = REGISTRY.registerItem("pink_ivory_chest_boat", p -> new GotBoatItem(ModBoatEntities.PINK_IVORY_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> PINK_IVORY_SAPLING        = block(ModBlocks.PINK_IVORY_SAPLING);
    public static final DeferredItem<Item> STRIPPED_PINK_IVORY_LOG   = block(ModBlocks.STRIPPED_PINK_IVORY_LOG);
    public static final DeferredItem<Item> STRIPPED_PINK_IVORY_WOOD  = block(ModBlocks.STRIPPED_PINK_IVORY_WOOD);
    public static final DeferredItem<BlockItem> PINK_IVORY_ROOFING = REGISTRY.registerSimpleBlockItem("pink_ivory_roofing", ModBlocks.PINK_IVORY_ROOFING);
    public static final DeferredItem<BlockItem> PINK_IVORY_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("pink_ivory_roofing_slab", ModBlocks.PINK_IVORY_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> PINK_IVORY_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("pink_ivory_roofing_stairs", ModBlocks.PINK_IVORY_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> PINK_IVORY_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("pink_ivory_roofing_wall", ModBlocks.PINK_IVORY_ROOFING_WALL);

    public static final DeferredItem<Item> PLUM_LOG            = block(ModBlocks.PLUM_LOG);
    public static final DeferredItem<Item> PLUM_WOOD           = block(ModBlocks.PLUM_WOOD);
    public static final DeferredItem<Item> PLUM_PLANKS         = block(ModBlocks.PLUM_PLANKS);
    public static final DeferredItem<Item> PLUM_LEAVES         = block(ModBlocks.PLUM_LEAVES);
    public static final DeferredItem<Item> PLUM_STAIRS         = block(ModBlocks.PLUM_STAIRS);
    public static final DeferredItem<Item> PLUM_SLAB           = block(ModBlocks.PLUM_SLAB);
    public static final DeferredItem<Item> PLUM_FENCE          = block(ModBlocks.PLUM_FENCE);
    public static final DeferredItem<Item> PLUM_FENCE_GATE     = block(ModBlocks.PLUM_FENCE_GATE);
    public static final DeferredItem<Item> PLUM_PRESSURE_PLATE = block(ModBlocks.PLUM_PRESSURE_PLATE);
    public static final DeferredItem<Item> PLUM_BUTTON         = block(ModBlocks.PLUM_BUTTON);
    public static final DeferredItem<Item> PLUM_DOOR           = door(ModBlocks.PLUM_DOOR);
    public static final DeferredItem<Item> PLUM_TRAPDOOR       = block(ModBlocks.PLUM_TRAPDOOR);
    public static final DeferredItem<Item> PLUM_BRANCH         = block(ModBlocks.PLUM_BRANCH);
    public static final DeferredItem<Item> STRIPPED_PLUM_BRANCH = block(ModBlocks.STRIPPED_PLUM_BRANCH);
    public static final DeferredItem<Item> PLUM_SIGN           = REGISTRY.registerItem("plum_sign",         p -> new SignItem(ModBlocks.PLUM_SIGN.get(), ModBlocks.PLUM_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> PLUM_HANGING_SIGN   = REGISTRY.registerItem("plum_hanging_sign", p -> new HangingSignItem(ModBlocks.PLUM_HANGING_SIGN.get(), ModBlocks.PLUM_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> PLUM_BOAT           = REGISTRY.registerItem("plum_boat",       p -> new GotBoatItem(ModBoatEntities.PLUM_BOAT.get(), p));
    public static final DeferredItem<Item> PLUM_CHEST_BOAT     = REGISTRY.registerItem("plum_chest_boat", p -> new GotBoatItem(ModBoatEntities.PLUM_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> PLUM_SAPLING        = block(ModBlocks.PLUM_SAPLING);
    public static final DeferredItem<Item> STRIPPED_PLUM_LOG   = block(ModBlocks.STRIPPED_PLUM_LOG);
    public static final DeferredItem<Item> STRIPPED_PLUM_WOOD  = block(ModBlocks.STRIPPED_PLUM_WOOD);
    public static final DeferredItem<BlockItem> PLUM_ROOFING = REGISTRY.registerSimpleBlockItem("plum_roofing", ModBlocks.PLUM_ROOFING);
    public static final DeferredItem<BlockItem> PLUM_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("plum_roofing_slab", ModBlocks.PLUM_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> PLUM_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("plum_roofing_stairs", ModBlocks.PLUM_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> PLUM_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("plum_roofing_wall", ModBlocks.PLUM_ROOFING_WALL);
    public static final DeferredItem<Item> PLUM = REGISTRY.registerItem("plum", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.5f).build()));

    public static final DeferredItem<Item> POMEGRANATE_LOG            = block(ModBlocks.POMEGRANATE_LOG);
    public static final DeferredItem<Item> POMEGRANATE_WOOD           = block(ModBlocks.POMEGRANATE_WOOD);
    public static final DeferredItem<Item> POMEGRANATE_PLANKS         = block(ModBlocks.POMEGRANATE_PLANKS);
    public static final DeferredItem<Item> POMEGRANATE_LEAVES         = block(ModBlocks.POMEGRANATE_LEAVES);
    public static final DeferredItem<Item> POMEGRANATE_STAIRS         = block(ModBlocks.POMEGRANATE_STAIRS);
    public static final DeferredItem<Item> POMEGRANATE_SLAB           = block(ModBlocks.POMEGRANATE_SLAB);
    public static final DeferredItem<Item> POMEGRANATE_FENCE          = block(ModBlocks.POMEGRANATE_FENCE);
    public static final DeferredItem<Item> POMEGRANATE_FENCE_GATE     = block(ModBlocks.POMEGRANATE_FENCE_GATE);
    public static final DeferredItem<Item> POMEGRANATE_PRESSURE_PLATE = block(ModBlocks.POMEGRANATE_PRESSURE_PLATE);
    public static final DeferredItem<Item> POMEGRANATE_BUTTON         = block(ModBlocks.POMEGRANATE_BUTTON);
    public static final DeferredItem<Item> POMEGRANATE_DOOR           = door(ModBlocks.POMEGRANATE_DOOR);
    public static final DeferredItem<Item> POMEGRANATE_TRAPDOOR       = block(ModBlocks.POMEGRANATE_TRAPDOOR);
    public static final DeferredItem<Item> POMEGRANATE_BRANCH         = block(ModBlocks.POMEGRANATE_BRANCH);
    public static final DeferredItem<Item> STRIPPED_POMEGRANATE_BRANCH = block(ModBlocks.STRIPPED_POMEGRANATE_BRANCH);
    public static final DeferredItem<Item> POMEGRANATE_SIGN           = REGISTRY.registerItem("pomegranate_sign",         p -> new SignItem(ModBlocks.POMEGRANATE_SIGN.get(), ModBlocks.POMEGRANATE_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> POMEGRANATE_HANGING_SIGN   = REGISTRY.registerItem("pomegranate_hanging_sign", p -> new HangingSignItem(ModBlocks.POMEGRANATE_HANGING_SIGN.get(), ModBlocks.POMEGRANATE_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> POMEGRANATE_BOAT           = REGISTRY.registerItem("pomegranate_boat",       p -> new GotBoatItem(ModBoatEntities.POMEGRANATE_BOAT.get(), p));
    public static final DeferredItem<Item> POMEGRANATE_CHEST_BOAT     = REGISTRY.registerItem("pomegranate_chest_boat", p -> new GotBoatItem(ModBoatEntities.POMEGRANATE_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> POMEGRANATE_SAPLING        = block(ModBlocks.POMEGRANATE_SAPLING);
    public static final DeferredItem<Item> STRIPPED_POMEGRANATE_LOG   = block(ModBlocks.STRIPPED_POMEGRANATE_LOG);
    public static final DeferredItem<Item> STRIPPED_POMEGRANATE_WOOD  = block(ModBlocks.STRIPPED_POMEGRANATE_WOOD);
    public static final DeferredItem<BlockItem> POMEGRANATE_ROOFING = REGISTRY.registerSimpleBlockItem("pomegranate_roofing", ModBlocks.POMEGRANATE_ROOFING);
    public static final DeferredItem<BlockItem> POMEGRANATE_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("pomegranate_roofing_slab", ModBlocks.POMEGRANATE_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> POMEGRANATE_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("pomegranate_roofing_stairs", ModBlocks.POMEGRANATE_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> POMEGRANATE_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("pomegranate_roofing_wall", ModBlocks.POMEGRANATE_ROOFING_WALL);
    public static final DeferredItem<Item> POMEGRANATE = REGISTRY.registerItem("pomegranate", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(5).saturationModifier(0.6f).build()));

    public static final DeferredItem<Item> PRUNE_LOG            = block(ModBlocks.PRUNE_LOG);
    public static final DeferredItem<Item> PRUNE_WOOD           = block(ModBlocks.PRUNE_WOOD);
    public static final DeferredItem<Item> PRUNE_PLANKS         = block(ModBlocks.PRUNE_PLANKS);
    public static final DeferredItem<Item> PRUNE_LEAVES         = block(ModBlocks.PRUNE_LEAVES);
    public static final DeferredItem<Item> PRUNE_STAIRS         = block(ModBlocks.PRUNE_STAIRS);
    public static final DeferredItem<Item> PRUNE_SLAB           = block(ModBlocks.PRUNE_SLAB);
    public static final DeferredItem<Item> PRUNE_FENCE          = block(ModBlocks.PRUNE_FENCE);
    public static final DeferredItem<Item> PRUNE_FENCE_GATE     = block(ModBlocks.PRUNE_FENCE_GATE);
    public static final DeferredItem<Item> PRUNE_PRESSURE_PLATE = block(ModBlocks.PRUNE_PRESSURE_PLATE);
    public static final DeferredItem<Item> PRUNE_BUTTON         = block(ModBlocks.PRUNE_BUTTON);
    public static final DeferredItem<Item> PRUNE_DOOR           = door(ModBlocks.PRUNE_DOOR);
    public static final DeferredItem<Item> PRUNE_TRAPDOOR       = block(ModBlocks.PRUNE_TRAPDOOR);
    public static final DeferredItem<Item> PRUNE_BRANCH         = block(ModBlocks.PRUNE_BRANCH);
    public static final DeferredItem<Item> STRIPPED_PRUNE_BRANCH = block(ModBlocks.STRIPPED_PRUNE_BRANCH);
    public static final DeferredItem<Item> PRUNE_SIGN           = REGISTRY.registerItem("prune_sign",         p -> new SignItem(ModBlocks.PRUNE_SIGN.get(), ModBlocks.PRUNE_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> PRUNE_HANGING_SIGN   = REGISTRY.registerItem("prune_hanging_sign", p -> new HangingSignItem(ModBlocks.PRUNE_HANGING_SIGN.get(), ModBlocks.PRUNE_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> PRUNE_BOAT           = REGISTRY.registerItem("prune_boat",       p -> new GotBoatItem(ModBoatEntities.PRUNE_BOAT.get(), p));
    public static final DeferredItem<Item> PRUNE_CHEST_BOAT     = REGISTRY.registerItem("prune_chest_boat", p -> new GotBoatItem(ModBoatEntities.PRUNE_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> PRUNE_SAPLING        = block(ModBlocks.PRUNE_SAPLING);
    public static final DeferredItem<Item> STRIPPED_PRUNE_LOG   = block(ModBlocks.STRIPPED_PRUNE_LOG);
    public static final DeferredItem<Item> STRIPPED_PRUNE_WOOD  = block(ModBlocks.STRIPPED_PRUNE_WOOD);
    public static final DeferredItem<BlockItem> PRUNE_ROOFING = REGISTRY.registerSimpleBlockItem("prune_roofing", ModBlocks.PRUNE_ROOFING);
    public static final DeferredItem<BlockItem> PRUNE_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("prune_roofing_slab", ModBlocks.PRUNE_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> PRUNE_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("prune_roofing_stairs", ModBlocks.PRUNE_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> PRUNE_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("prune_roofing_wall", ModBlocks.PRUNE_ROOFING_WALL);
    public static final DeferredItem<Item> PRUNE = REGISTRY.registerItem("prune", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.4f).build()));

    public static final DeferredItem<Item> ALMOND_LOG            = block(ModBlocks.ALMOND_LOG);
    public static final DeferredItem<Item> ALMOND_WOOD           = block(ModBlocks.ALMOND_WOOD);
    public static final DeferredItem<Item> ALMOND_PLANKS         = block(ModBlocks.ALMOND_PLANKS);
    public static final DeferredItem<Item> ALMOND_LEAVES         = block(ModBlocks.ALMOND_LEAVES);
    public static final DeferredItem<Item> ALMOND_STAIRS         = block(ModBlocks.ALMOND_STAIRS);
    public static final DeferredItem<Item> ALMOND_SLAB           = block(ModBlocks.ALMOND_SLAB);
    public static final DeferredItem<Item> ALMOND_FENCE          = block(ModBlocks.ALMOND_FENCE);
    public static final DeferredItem<Item> ALMOND_FENCE_GATE     = block(ModBlocks.ALMOND_FENCE_GATE);
    public static final DeferredItem<Item> ALMOND_PRESSURE_PLATE = block(ModBlocks.ALMOND_PRESSURE_PLATE);
    public static final DeferredItem<Item> ALMOND_BUTTON         = block(ModBlocks.ALMOND_BUTTON);
    public static final DeferredItem<Item> ALMOND_DOOR           = door(ModBlocks.ALMOND_DOOR);
    public static final DeferredItem<Item> ALMOND_TRAPDOOR       = block(ModBlocks.ALMOND_TRAPDOOR);
    public static final DeferredItem<Item> ALMOND_BRANCH         = block(ModBlocks.ALMOND_BRANCH);
    public static final DeferredItem<Item> STRIPPED_ALMOND_BRANCH = block(ModBlocks.STRIPPED_ALMOND_BRANCH);
    public static final DeferredItem<Item> ALMOND_SIGN           = REGISTRY.registerItem("almond_sign",         p -> new SignItem(ModBlocks.ALMOND_SIGN.get(), ModBlocks.ALMOND_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> ALMOND_HANGING_SIGN   = REGISTRY.registerItem("almond_hanging_sign", p -> new HangingSignItem(ModBlocks.ALMOND_HANGING_SIGN.get(), ModBlocks.ALMOND_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> ALMOND_BOAT           = REGISTRY.registerItem("almond_boat",       p -> new GotBoatItem(ModBoatEntities.ALMOND_BOAT.get(), p));
    public static final DeferredItem<Item> ALMOND_CHEST_BOAT     = REGISTRY.registerItem("almond_chest_boat", p -> new GotBoatItem(ModBoatEntities.ALMOND_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> ALMOND_SAPLING        = block(ModBlocks.ALMOND_SAPLING);
    public static final DeferredItem<Item> STRIPPED_ALMOND_LOG   = block(ModBlocks.STRIPPED_ALMOND_LOG);
    public static final DeferredItem<Item> STRIPPED_ALMOND_WOOD  = block(ModBlocks.STRIPPED_ALMOND_WOOD);
    public static final DeferredItem<BlockItem> ALMOND_ROOFING = REGISTRY.registerSimpleBlockItem("almond_roofing", ModBlocks.ALMOND_ROOFING);
    public static final DeferredItem<BlockItem> ALMOND_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("almond_roofing_slab", ModBlocks.ALMOND_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> ALMOND_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("almond_roofing_stairs", ModBlocks.ALMOND_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> ALMOND_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("almond_roofing_wall", ModBlocks.ALMOND_ROOFING_WALL);
    public static final DeferredItem<Item> ALMOND = REGISTRY.registerItem("almond", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.5f).build()));

    public static final DeferredItem<Item> NUTMEG_LOG            = block(ModBlocks.NUTMEG_LOG);
    public static final DeferredItem<Item> NUTMEG_WOOD           = block(ModBlocks.NUTMEG_WOOD);
    public static final DeferredItem<Item> NUTMEG_PLANKS         = block(ModBlocks.NUTMEG_PLANKS);
    public static final DeferredItem<Item> NUTMEG_LEAVES         = block(ModBlocks.NUTMEG_LEAVES);
    public static final DeferredItem<Item> NUTMEG_STAIRS         = block(ModBlocks.NUTMEG_STAIRS);
    public static final DeferredItem<Item> NUTMEG_SLAB           = block(ModBlocks.NUTMEG_SLAB);
    public static final DeferredItem<Item> NUTMEG_FENCE          = block(ModBlocks.NUTMEG_FENCE);
    public static final DeferredItem<Item> NUTMEG_FENCE_GATE     = block(ModBlocks.NUTMEG_FENCE_GATE);
    public static final DeferredItem<Item> NUTMEG_PRESSURE_PLATE = block(ModBlocks.NUTMEG_PRESSURE_PLATE);
    public static final DeferredItem<Item> NUTMEG_BUTTON         = block(ModBlocks.NUTMEG_BUTTON);
    public static final DeferredItem<Item> NUTMEG_DOOR           = door(ModBlocks.NUTMEG_DOOR);
    public static final DeferredItem<Item> NUTMEG_TRAPDOOR       = block(ModBlocks.NUTMEG_TRAPDOOR);
    public static final DeferredItem<Item> NUTMEG_BRANCH         = block(ModBlocks.NUTMEG_BRANCH);
    public static final DeferredItem<Item> STRIPPED_NUTMEG_BRANCH = block(ModBlocks.STRIPPED_NUTMEG_BRANCH);
    public static final DeferredItem<Item> NUTMEG_SIGN           = REGISTRY.registerItem("nutmeg_sign",         p -> new SignItem(ModBlocks.NUTMEG_SIGN.get(), ModBlocks.NUTMEG_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> NUTMEG_HANGING_SIGN   = REGISTRY.registerItem("nutmeg_hanging_sign", p -> new HangingSignItem(ModBlocks.NUTMEG_HANGING_SIGN.get(), ModBlocks.NUTMEG_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> NUTMEG_BOAT           = REGISTRY.registerItem("nutmeg_boat",       p -> new GotBoatItem(ModBoatEntities.NUTMEG_BOAT.get(), p));
    public static final DeferredItem<Item> NUTMEG_CHEST_BOAT     = REGISTRY.registerItem("nutmeg_chest_boat", p -> new GotBoatItem(ModBoatEntities.NUTMEG_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> NUTMEG_SAPLING        = block(ModBlocks.NUTMEG_SAPLING);
    public static final DeferredItem<Item> STRIPPED_NUTMEG_LOG   = block(ModBlocks.STRIPPED_NUTMEG_LOG);
    public static final DeferredItem<Item> STRIPPED_NUTMEG_WOOD  = block(ModBlocks.STRIPPED_NUTMEG_WOOD);
    public static final DeferredItem<BlockItem> NUTMEG_ROOFING = REGISTRY.registerSimpleBlockItem("nutmeg_roofing", ModBlocks.NUTMEG_ROOFING);
    public static final DeferredItem<BlockItem> NUTMEG_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("nutmeg_roofing_slab", ModBlocks.NUTMEG_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> NUTMEG_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("nutmeg_roofing_stairs", ModBlocks.NUTMEG_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> NUTMEG_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("nutmeg_roofing_wall", ModBlocks.NUTMEG_ROOFING_WALL);
    public static final DeferredItem<Item> NUTMEG = REGISTRY.registerItem("nutmeg", p -> new Item(p), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.4f).build()));

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

    public static final DeferredItem<Item> HEMLOCK_LOG            = block(ModBlocks.HEMLOCK_LOG);
    public static final DeferredItem<Item> HEMLOCK_WOOD           = block(ModBlocks.HEMLOCK_WOOD);
    public static final DeferredItem<Item> HEMLOCK_PLANKS         = block(ModBlocks.HEMLOCK_PLANKS);
    public static final DeferredItem<Item> HEMLOCK_LEAVES         = block(ModBlocks.HEMLOCK_LEAVES);
    public static final DeferredItem<Item> HEMLOCK_STAIRS         = block(ModBlocks.HEMLOCK_STAIRS);
    public static final DeferredItem<Item> HEMLOCK_SLAB           = block(ModBlocks.HEMLOCK_SLAB);
    public static final DeferredItem<Item> HEMLOCK_FENCE          = block(ModBlocks.HEMLOCK_FENCE);
    public static final DeferredItem<Item> HEMLOCK_FENCE_GATE     = block(ModBlocks.HEMLOCK_FENCE_GATE);
    public static final DeferredItem<Item> HEMLOCK_PRESSURE_PLATE = block(ModBlocks.HEMLOCK_PRESSURE_PLATE);
    public static final DeferredItem<Item> HEMLOCK_BUTTON         = block(ModBlocks.HEMLOCK_BUTTON);
    public static final DeferredItem<Item> HEMLOCK_DOOR           = door(ModBlocks.HEMLOCK_DOOR);
    public static final DeferredItem<Item> HEMLOCK_TRAPDOOR       = block(ModBlocks.HEMLOCK_TRAPDOOR);
    public static final DeferredItem<Item> HEMLOCK_BRANCH         = block(ModBlocks.HEMLOCK_BRANCH);
    public static final DeferredItem<Item> STRIPPED_HEMLOCK_BRANCH = block(ModBlocks.STRIPPED_HEMLOCK_BRANCH);
    public static final DeferredItem<Item> HEMLOCK_SIGN           = REGISTRY.registerItem("hemlock_sign",         p -> new SignItem(ModBlocks.HEMLOCK_SIGN.get(), ModBlocks.HEMLOCK_WALL_SIGN.get(), p));
    public static final DeferredItem<Item> HEMLOCK_HANGING_SIGN   = REGISTRY.registerItem("hemlock_hanging_sign", p -> new HangingSignItem(ModBlocks.HEMLOCK_HANGING_SIGN.get(), ModBlocks.HEMLOCK_WALL_HANGING_SIGN.get(), p));
    public static final DeferredItem<Item> HEMLOCK_BOAT           = REGISTRY.registerItem("hemlock_boat",       p -> new GotBoatItem(ModBoatEntities.HEMLOCK_BOAT.get(), p));
    public static final DeferredItem<Item> HEMLOCK_CHEST_BOAT     = REGISTRY.registerItem("hemlock_chest_boat", p -> new GotBoatItem(ModBoatEntities.HEMLOCK_CHEST_BOAT.get(), p));
    public static final DeferredItem<Item> HEMLOCK_SAPLING        = block(ModBlocks.HEMLOCK_SAPLING);
    public static final DeferredItem<Item> STRIPPED_HEMLOCK_LOG   = block(ModBlocks.STRIPPED_HEMLOCK_LOG);
    public static final DeferredItem<Item> STRIPPED_HEMLOCK_WOOD  = block(ModBlocks.STRIPPED_HEMLOCK_WOOD);
    public static final DeferredItem<BlockItem> HEMLOCK_ROOFING = REGISTRY.registerSimpleBlockItem("hemlock_roofing", ModBlocks.HEMLOCK_ROOFING);
    public static final DeferredItem<BlockItem> HEMLOCK_ROOFING_SLAB = REGISTRY.registerSimpleBlockItem("hemlock_roofing_slab", ModBlocks.HEMLOCK_ROOFING_SLAB);
    public static final DeferredItem<BlockItem> HEMLOCK_ROOFING_STAIRS = REGISTRY.registerSimpleBlockItem("hemlock_roofing_stairs", ModBlocks.HEMLOCK_ROOFING_STAIRS);
    public static final DeferredItem<BlockItem> HEMLOCK_ROOFING_WALL = REGISTRY.registerSimpleBlockItem("hemlock_roofing_wall", ModBlocks.HEMLOCK_ROOFING_WALL);

    public static final DeferredItem<Item> WHITE_WOOL_SLAB        = block(ModBlocks.WHITE_WOOL_SLAB);
    public static final DeferredItem<Item> WHITE_WOOL_STAIRS      = block(ModBlocks.WHITE_WOOL_STAIRS);
    public static final DeferredItem<Item> ORANGE_WOOL_SLAB       = block(ModBlocks.ORANGE_WOOL_SLAB);
    public static final DeferredItem<Item> ORANGE_WOOL_STAIRS     = block(ModBlocks.ORANGE_WOOL_STAIRS);
    public static final DeferredItem<Item> MAGENTA_WOOL_SLAB      = block(ModBlocks.MAGENTA_WOOL_SLAB);
    public static final DeferredItem<Item> MAGENTA_WOOL_STAIRS    = block(ModBlocks.MAGENTA_WOOL_STAIRS);
    public static final DeferredItem<Item> LIGHT_BLUE_WOOL_SLAB   = block(ModBlocks.LIGHT_BLUE_WOOL_SLAB);
    public static final DeferredItem<Item> LIGHT_BLUE_WOOL_STAIRS = block(ModBlocks.LIGHT_BLUE_WOOL_STAIRS);
    public static final DeferredItem<Item> YELLOW_WOOL_SLAB       = block(ModBlocks.YELLOW_WOOL_SLAB);
    public static final DeferredItem<Item> YELLOW_WOOL_STAIRS     = block(ModBlocks.YELLOW_WOOL_STAIRS);
    public static final DeferredItem<Item> LIME_WOOL_SLAB         = block(ModBlocks.LIME_WOOL_SLAB);
    public static final DeferredItem<Item> LIME_WOOL_STAIRS       = block(ModBlocks.LIME_WOOL_STAIRS);
    public static final DeferredItem<Item> PINK_WOOL_SLAB         = block(ModBlocks.PINK_WOOL_SLAB);
    public static final DeferredItem<Item> PINK_WOOL_STAIRS       = block(ModBlocks.PINK_WOOL_STAIRS);
    public static final DeferredItem<Item> GRAY_WOOL_SLAB         = block(ModBlocks.GRAY_WOOL_SLAB);
    public static final DeferredItem<Item> GRAY_WOOL_STAIRS       = block(ModBlocks.GRAY_WOOL_STAIRS);
    public static final DeferredItem<Item> LIGHT_GRAY_WOOL_SLAB   = block(ModBlocks.LIGHT_GRAY_WOOL_SLAB);
    public static final DeferredItem<Item> LIGHT_GRAY_WOOL_STAIRS = block(ModBlocks.LIGHT_GRAY_WOOL_STAIRS);
    public static final DeferredItem<Item> CYAN_WOOL_SLAB         = block(ModBlocks.CYAN_WOOL_SLAB);
    public static final DeferredItem<Item> CYAN_WOOL_STAIRS       = block(ModBlocks.CYAN_WOOL_STAIRS);
    public static final DeferredItem<Item> PURPLE_WOOL_SLAB       = block(ModBlocks.PURPLE_WOOL_SLAB);
    public static final DeferredItem<Item> PURPLE_WOOL_STAIRS     = block(ModBlocks.PURPLE_WOOL_STAIRS);
    public static final DeferredItem<Item> BLUE_WOOL_SLAB         = block(ModBlocks.BLUE_WOOL_SLAB);
    public static final DeferredItem<Item> BLUE_WOOL_STAIRS       = block(ModBlocks.BLUE_WOOL_STAIRS);
    public static final DeferredItem<Item> BROWN_WOOL_SLAB        = block(ModBlocks.BROWN_WOOL_SLAB);
    public static final DeferredItem<Item> BROWN_WOOL_STAIRS      = block(ModBlocks.BROWN_WOOL_STAIRS);
    public static final DeferredItem<Item> GREEN_WOOL_SLAB        = block(ModBlocks.GREEN_WOOL_SLAB);
    public static final DeferredItem<Item> GREEN_WOOL_STAIRS      = block(ModBlocks.GREEN_WOOL_STAIRS);
    public static final DeferredItem<Item> RED_WOOL_SLAB          = block(ModBlocks.RED_WOOL_SLAB);
    public static final DeferredItem<Item> RED_WOOL_STAIRS        = block(ModBlocks.RED_WOOL_STAIRS);
    public static final DeferredItem<Item> BLACK_WOOL_SLAB        = block(ModBlocks.BLACK_WOOL_SLAB);
    public static final DeferredItem<Item> BLACK_WOOL_STAIRS      = block(ModBlocks.BLACK_WOOL_STAIRS);

    public static final DeferredItem<Item> ACACIA_ROOFING_VERTICAL_SLAB = block(ModBlocks.ACACIA_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> ACACIA_WOOD_VERTICAL_SLAB = block(ModBlocks.ACACIA_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> ALDER_ROOFING_VERTICAL_SLAB = block(ModBlocks.ALDER_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> ALDER_VERTICAL_SLAB = block(ModBlocks.ALDER_VERTICAL_SLAB);
    public static final DeferredItem<Item> ALDER_WOOD_VERTICAL_SLAB = block(ModBlocks.ALDER_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> ALMOND_ROOFING_VERTICAL_SLAB = block(ModBlocks.ALMOND_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> ALMOND_VERTICAL_SLAB = block(ModBlocks.ALMOND_VERTICAL_SLAB);
    public static final DeferredItem<Item> ALMOND_WOOD_VERTICAL_SLAB = block(ModBlocks.ALMOND_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> APPLE_ROOFING_VERTICAL_SLAB = block(ModBlocks.APPLE_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> APPLE_VERTICAL_SLAB = block(ModBlocks.APPLE_VERTICAL_SLAB);
    public static final DeferredItem<Item> APPLE_WOOD_VERTICAL_SLAB = block(ModBlocks.APPLE_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> APRICOT_ROOFING_VERTICAL_SLAB = block(ModBlocks.APRICOT_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> APRICOT_VERTICAL_SLAB = block(ModBlocks.APRICOT_VERTICAL_SLAB);
    public static final DeferredItem<Item> APRICOT_WOOD_VERTICAL_SLAB = block(ModBlocks.APRICOT_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> ASH_ROOFING_VERTICAL_SLAB = block(ModBlocks.ASH_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> ASH_VERTICAL_SLAB = block(ModBlocks.ASH_VERTICAL_SLAB);
    public static final DeferredItem<Item> ASH_WOOD_VERTICAL_SLAB = block(ModBlocks.ASH_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> ASPEN_ROOFING_VERTICAL_SLAB = block(ModBlocks.ASPEN_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> ASPEN_VERTICAL_SLAB = block(ModBlocks.ASPEN_VERTICAL_SLAB);
    public static final DeferredItem<Item> ASPEN_WOOD_VERTICAL_SLAB = block(ModBlocks.ASPEN_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> BAMBOO_ROOFING_VERTICAL_SLAB = block(ModBlocks.BAMBOO_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> BASALT_BRICK_VERTICAL_SLAB = block(ModBlocks.BASALT_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> BASALT_COBBLESTONE_VERTICAL_SLAB = block(ModBlocks.BASALT_COBBLESTONE_VERTICAL_SLAB);
    public static final DeferredItem<Item> BASALT_ROCK_VERTICAL_SLAB = block(ModBlocks.BASALT_ROCK_VERTICAL_SLAB);
    public static final DeferredItem<Item> BEECH_ROOFING_VERTICAL_SLAB = block(ModBlocks.BEECH_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> BEECH_VERTICAL_SLAB = block(ModBlocks.BEECH_VERTICAL_SLAB);
    public static final DeferredItem<Item> BEECH_WOOD_VERTICAL_SLAB = block(ModBlocks.BEECH_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> BIRCH_ROOFING_VERTICAL_SLAB = block(ModBlocks.BIRCH_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> BIRCH_WOOD_VERTICAL_SLAB = block(ModBlocks.BIRCH_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> BLACK_CHERRY_ROOFING_VERTICAL_SLAB = block(ModBlocks.BLACK_CHERRY_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> BLACK_CHERRY_VERTICAL_SLAB = block(ModBlocks.BLACK_CHERRY_VERTICAL_SLAB);
    public static final DeferredItem<Item> BLACK_CHERRY_WOOD_VERTICAL_SLAB = block(ModBlocks.BLACK_CHERRY_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_ROOFING_VERTICAL_SLAB = block(ModBlocks.BLACK_COTTONWOOD_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_VERTICAL_SLAB = block(ModBlocks.BLACK_COTTONWOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> BLACK_COTTONWOOD_WOOD_VERTICAL_SLAB = block(ModBlocks.BLACK_COTTONWOOD_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> BLACK_WOOL_VERTICAL_SLAB = block(ModBlocks.BLACK_WOOL_VERTICAL_SLAB);
    public static final DeferredItem<Item> BLACKBARK_ROOFING_VERTICAL_SLAB = block(ModBlocks.BLACKBARK_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> BLACKBARK_VERTICAL_SLAB = block(ModBlocks.BLACKBARK_VERTICAL_SLAB);
    public static final DeferredItem<Item> BLACKBARK_WOOD_VERTICAL_SLAB = block(ModBlocks.BLACKBARK_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> BLACKTHORN_ROOFING_VERTICAL_SLAB = block(ModBlocks.BLACKTHORN_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> BLACKTHORN_VERTICAL_SLAB = block(ModBlocks.BLACKTHORN_VERTICAL_SLAB);
    public static final DeferredItem<Item> BLACKTHORN_WOOD_VERTICAL_SLAB = block(ModBlocks.BLACKTHORN_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> BLOODWOOD_ROOFING_VERTICAL_SLAB = block(ModBlocks.BLOODWOOD_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> BLOODWOOD_VERTICAL_SLAB = block(ModBlocks.BLOODWOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> BLOODWOOD_WOOD_VERTICAL_SLAB = block(ModBlocks.BLOODWOOD_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> BLUE_MAHOE_ROOFING_VERTICAL_SLAB = block(ModBlocks.BLUE_MAHOE_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> BLUE_MAHOE_VERTICAL_SLAB = block(ModBlocks.BLUE_MAHOE_VERTICAL_SLAB);
    public static final DeferredItem<Item> BLUE_MAHOE_WOOD_VERTICAL_SLAB = block(ModBlocks.BLUE_MAHOE_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> BLUE_WOOL_VERTICAL_SLAB = block(ModBlocks.BLUE_WOOL_VERTICAL_SLAB);
    public static final DeferredItem<Item> BROWN_WOOL_VERTICAL_SLAB = block(ModBlocks.BROWN_WOOL_VERTICAL_SLAB);
    public static final DeferredItem<Item> CEDAR_ROOFING_VERTICAL_SLAB = block(ModBlocks.CEDAR_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> CEDAR_VERTICAL_SLAB = block(ModBlocks.CEDAR_VERTICAL_SLAB);
    public static final DeferredItem<Item> CEDAR_WOOD_VERTICAL_SLAB = block(ModBlocks.CEDAR_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> CHERRY_ROOFING_VERTICAL_SLAB = block(ModBlocks.CHERRY_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> CHERRY_WOOD_VERTICAL_SLAB = block(ModBlocks.CHERRY_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> CHESTNUT_ROOFING_VERTICAL_SLAB = block(ModBlocks.CHESTNUT_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> CHESTNUT_VERTICAL_SLAB = block(ModBlocks.CHESTNUT_VERTICAL_SLAB);
    public static final DeferredItem<Item> CHESTNUT_WOOD_VERTICAL_SLAB = block(ModBlocks.CHESTNUT_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> CINNAMON_ROOFING_VERTICAL_SLAB = block(ModBlocks.CINNAMON_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> CINNAMON_VERTICAL_SLAB = block(ModBlocks.CINNAMON_VERTICAL_SLAB);
    public static final DeferredItem<Item> CINNAMON_WOOD_VERTICAL_SLAB = block(ModBlocks.CINNAMON_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> CLOVE_ROOFING_VERTICAL_SLAB = block(ModBlocks.CLOVE_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> CLOVE_VERTICAL_SLAB = block(ModBlocks.CLOVE_VERTICAL_SLAB);
    public static final DeferredItem<Item> CLOVE_WOOD_VERTICAL_SLAB = block(ModBlocks.CLOVE_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> COARSE_DIRT_VERTICAL_SLAB = block(ModBlocks.COARSE_DIRT_VERTICAL_SLAB);
    public static final DeferredItem<Item> COTTONWOOD_ROOFING_VERTICAL_SLAB = block(ModBlocks.COTTONWOOD_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> COTTONWOOD_VERTICAL_SLAB = block(ModBlocks.COTTONWOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> COTTONWOOD_WOOD_VERTICAL_SLAB = block(ModBlocks.COTTONWOOD_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> CRABAPPLE_ROOFING_VERTICAL_SLAB = block(ModBlocks.CRABAPPLE_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> CRABAPPLE_VERTICAL_SLAB = block(ModBlocks.CRABAPPLE_VERTICAL_SLAB);
    public static final DeferredItem<Item> CRABAPPLE_WOOD_VERTICAL_SLAB = block(ModBlocks.CRABAPPLE_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> CRACKED_BASALT_BRICK_VERTICAL_SLAB = block(ModBlocks.CRACKED_BASALT_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> CRACKED_FLINT_BRICK_VERTICAL_SLAB = block(ModBlocks.CRACKED_FLINT_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> CRACKED_FUSED_BLACK_BRICK_VERTICAL_SLAB = block(ModBlocks.CRACKED_FUSED_BLACK_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> CRACKED_GREY_GRANITE_BRICK_VERTICAL_SLAB = block(ModBlocks.CRACKED_GREY_GRANITE_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> CRACKED_LIMESTONE_BRICK_VERTICAL_SLAB = block(ModBlocks.CRACKED_LIMESTONE_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> CRACKED_MARBLE_BRICK_VERTICAL_SLAB = block(ModBlocks.CRACKED_MARBLE_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> CRACKED_OILY_BLACK_BRICK_VERTICAL_SLAB = block(ModBlocks.CRACKED_OILY_BLACK_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> CRACKED_RED_SANDSTONE_BRICK_VERTICAL_SLAB = block(ModBlocks.CRACKED_RED_SANDSTONE_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> CRACKED_SANDSTONE_BRICK_VERTICAL_SLAB = block(ModBlocks.CRACKED_SANDSTONE_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> CRACKED_SLATE_BRICK_VERTICAL_SLAB = block(ModBlocks.CRACKED_SLATE_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> CYAN_WOOL_VERTICAL_SLAB = block(ModBlocks.CYAN_WOOL_VERTICAL_SLAB);
    public static final DeferredItem<Item> DARK_OAK_ROOFING_VERTICAL_SLAB = block(ModBlocks.DARK_OAK_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> DARK_OAK_WOOD_VERTICAL_SLAB = block(ModBlocks.DARK_OAK_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> WEATHERED_THATCH_VERTICAL_SLAB = block(ModBlocks.WEATHERED_THATCH_VERTICAL_SLAB);
    public static final DeferredItem<Item> DATE_PALM_ROOFING_VERTICAL_SLAB = block(ModBlocks.DATE_PALM_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> DATE_PALM_VERTICAL_SLAB = block(ModBlocks.DATE_PALM_VERTICAL_SLAB);
    public static final DeferredItem<Item> DATE_PALM_WOOD_VERTICAL_SLAB = block(ModBlocks.DATE_PALM_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> DIRT_PATH_VERTICAL_SLAB = block(ModBlocks.DIRT_PATH_VERTICAL_SLAB);
    public static final DeferredItem<Item> DIRT_VERTICAL_SLAB = block(ModBlocks.DIRT_VERTICAL_SLAB);
    public static final DeferredItem<Item> EBONY_ROOFING_VERTICAL_SLAB = block(ModBlocks.EBONY_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> EBONY_VERTICAL_SLAB = block(ModBlocks.EBONY_VERTICAL_SLAB);
    public static final DeferredItem<Item> EBONY_WOOD_VERTICAL_SLAB = block(ModBlocks.EBONY_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> ELM_ROOFING_VERTICAL_SLAB = block(ModBlocks.ELM_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> ELM_VERTICAL_SLAB = block(ModBlocks.ELM_VERTICAL_SLAB);
    public static final DeferredItem<Item> ELM_WOOD_VERTICAL_SLAB = block(ModBlocks.ELM_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> FIELDSTONE_VERTICAL_SLAB = block(ModBlocks.FIELDSTONE_VERTICAL_SLAB);
    public static final DeferredItem<Item> FIG_ROOFING_VERTICAL_SLAB = block(ModBlocks.FIG_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> FIG_VERTICAL_SLAB = block(ModBlocks.FIG_VERTICAL_SLAB);
    public static final DeferredItem<Item> FIG_WOOD_VERTICAL_SLAB = block(ModBlocks.FIG_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> FIR_ROOFING_VERTICAL_SLAB = block(ModBlocks.FIR_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> FIR_VERTICAL_SLAB = block(ModBlocks.FIR_VERTICAL_SLAB);
    public static final DeferredItem<Item> FIR_WOOD_VERTICAL_SLAB = block(ModBlocks.FIR_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> FLINT_BRICK_VERTICAL_SLAB = block(ModBlocks.FLINT_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> FLINT_ROCK_VERTICAL_SLAB = block(ModBlocks.FLINT_ROCK_VERTICAL_SLAB);
    public static final DeferredItem<Item> FUSED_BLACK_BRICK_VERTICAL_SLAB = block(ModBlocks.FUSED_BLACK_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> FUSED_BLACK_COBBLESTONE_VERTICAL_SLAB = block(ModBlocks.FUSED_BLACK_COBBLESTONE_VERTICAL_SLAB);
    public static final DeferredItem<Item> FUSED_BLACK_ROCK_VERTICAL_SLAB = block(ModBlocks.FUSED_BLACK_ROCK_VERTICAL_SLAB);
    public static final DeferredItem<Item> GOLDENHEART_ROOFING_VERTICAL_SLAB = block(ModBlocks.GOLDENHEART_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> GOLDENHEART_VERTICAL_SLAB = block(ModBlocks.GOLDENHEART_VERTICAL_SLAB);
    public static final DeferredItem<Item> GOLDENHEART_WOOD_VERTICAL_SLAB = block(ModBlocks.GOLDENHEART_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> GRASS_BLOCK_VERTICAL_SLAB = block(ModBlocks.GRASS_BLOCK_VERTICAL_SLAB);
    public static final DeferredItem<Item> GRAY_WOOL_VERTICAL_SLAB = block(ModBlocks.GRAY_WOOL_VERTICAL_SLAB);
    public static final DeferredItem<Item> GREEN_WOOL_VERTICAL_SLAB = block(ModBlocks.GREEN_WOOL_VERTICAL_SLAB);
    public static final DeferredItem<Item> GREY_GRANITE_BRICK_VERTICAL_SLAB = block(ModBlocks.GREY_GRANITE_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> GREY_GRANITE_COBBLESTONE_VERTICAL_SLAB = block(ModBlocks.GREY_GRANITE_COBBLESTONE_VERTICAL_SLAB);
    public static final DeferredItem<Item> GREY_GRANITE_ROCK_VERTICAL_SLAB = block(ModBlocks.GREY_GRANITE_ROCK_VERTICAL_SLAB);
    public static final DeferredItem<Item> HAWTHORN_ROOFING_VERTICAL_SLAB = block(ModBlocks.HAWTHORN_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> HAWTHORN_VERTICAL_SLAB = block(ModBlocks.HAWTHORN_VERTICAL_SLAB);
    public static final DeferredItem<Item> HAWTHORN_WOOD_VERTICAL_SLAB = block(ModBlocks.HAWTHORN_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> HEMLOCK_ROOFING_VERTICAL_SLAB = block(ModBlocks.HEMLOCK_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> HEMLOCK_VERTICAL_SLAB = block(ModBlocks.HEMLOCK_VERTICAL_SLAB);
    public static final DeferredItem<Item> HEMLOCK_WOOD_VERTICAL_SLAB = block(ModBlocks.HEMLOCK_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> IRONWOOD_ROOFING_VERTICAL_SLAB = block(ModBlocks.IRONWOOD_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> IRONWOOD_VERTICAL_SLAB = block(ModBlocks.IRONWOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> IRONWOOD_WOOD_VERTICAL_SLAB = block(ModBlocks.IRONWOOD_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> JUNGLE_ROOFING_VERTICAL_SLAB = block(ModBlocks.JUNGLE_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> JUNGLE_WOOD_VERTICAL_SLAB = block(ModBlocks.JUNGLE_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> LEMON_ROOFING_VERTICAL_SLAB = block(ModBlocks.LEMON_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> LEMON_VERTICAL_SLAB = block(ModBlocks.LEMON_VERTICAL_SLAB);
    public static final DeferredItem<Item> LEMON_WOOD_VERTICAL_SLAB = block(ModBlocks.LEMON_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> LIGHT_BLUE_WOOL_VERTICAL_SLAB = block(ModBlocks.LIGHT_BLUE_WOOL_VERTICAL_SLAB);
    public static final DeferredItem<Item> LIGHT_GRAY_WOOL_VERTICAL_SLAB = block(ModBlocks.LIGHT_GRAY_WOOL_VERTICAL_SLAB);
    public static final DeferredItem<Item> THATCH_VERTICAL_SLAB = block(ModBlocks.THATCH_VERTICAL_SLAB);
    public static final DeferredItem<Item> LIME_ROOFING_VERTICAL_SLAB = block(ModBlocks.LIME_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> LIME_VERTICAL_SLAB = block(ModBlocks.LIME_VERTICAL_SLAB);
    public static final DeferredItem<Item> LIME_WOOD_VERTICAL_SLAB = block(ModBlocks.LIME_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> LIME_WOOL_VERTICAL_SLAB = block(ModBlocks.LIME_WOOL_VERTICAL_SLAB);
    public static final DeferredItem<Item> LIMESTONE_BRICK_VERTICAL_SLAB = block(ModBlocks.LIMESTONE_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> LIMESTONE_COBBLESTONE_VERTICAL_SLAB = block(ModBlocks.LIMESTONE_COBBLESTONE_VERTICAL_SLAB);
    public static final DeferredItem<Item> LIMESTONE_ROCK_VERTICAL_SLAB = block(ModBlocks.LIMESTONE_ROCK_VERTICAL_SLAB);
    public static final DeferredItem<Item> LINDEN_ROOFING_VERTICAL_SLAB = block(ModBlocks.LINDEN_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> LINDEN_VERTICAL_SLAB = block(ModBlocks.LINDEN_VERTICAL_SLAB);
    public static final DeferredItem<Item> LINDEN_WOOD_VERTICAL_SLAB = block(ModBlocks.LINDEN_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> MAGENTA_WOOL_VERTICAL_SLAB = block(ModBlocks.MAGENTA_WOOL_VERTICAL_SLAB);
    public static final DeferredItem<Item> MAHOGANY_ROOFING_VERTICAL_SLAB = block(ModBlocks.MAHOGANY_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> MAHOGANY_VERTICAL_SLAB = block(ModBlocks.MAHOGANY_VERTICAL_SLAB);
    public static final DeferredItem<Item> MAHOGANY_WOOD_VERTICAL_SLAB = block(ModBlocks.MAHOGANY_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> MANGROVE_ROOFING_VERTICAL_SLAB = block(ModBlocks.MANGROVE_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> MANGROVE_WOOD_VERTICAL_SLAB = block(ModBlocks.MANGROVE_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> MAPLE_ROOFING_VERTICAL_SLAB = block(ModBlocks.MAPLE_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> MAPLE_VERTICAL_SLAB = block(ModBlocks.MAPLE_VERTICAL_SLAB);
    public static final DeferredItem<Item> MAPLE_WOOD_VERTICAL_SLAB = block(ModBlocks.MAPLE_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> MARBLE_BRICK_VERTICAL_SLAB = block(ModBlocks.MARBLE_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> MARBLE_COBBLESTONE_VERTICAL_SLAB = block(ModBlocks.MARBLE_COBBLESTONE_VERTICAL_SLAB);
    public static final DeferredItem<Item> MARBLE_ROCK_VERTICAL_SLAB = block(ModBlocks.MARBLE_ROCK_VERTICAL_SLAB);
    public static final DeferredItem<Item> MOSSY_BASALT_BRICK_VERTICAL_SLAB = block(ModBlocks.MOSSY_BASALT_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> MOSSY_BASALT_COBBLESTONE_VERTICAL_SLAB = block(ModBlocks.MOSSY_BASALT_COBBLESTONE_VERTICAL_SLAB);
    public static final DeferredItem<Item> MOSSY_FLINT_BRICK_VERTICAL_SLAB = block(ModBlocks.MOSSY_FLINT_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> MOSSY_FUSED_BLACK_BRICK_VERTICAL_SLAB = block(ModBlocks.MOSSY_FUSED_BLACK_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> MOSSY_FUSED_BLACK_COBBLESTONE_VERTICAL_SLAB = block(ModBlocks.MOSSY_FUSED_BLACK_COBBLESTONE_VERTICAL_SLAB);
    public static final DeferredItem<Item> MOSSY_GREY_GRANITE_BRICK_VERTICAL_SLAB = block(ModBlocks.MOSSY_GREY_GRANITE_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> MOSSY_GREY_GRANITE_COBBLESTONE_VERTICAL_SLAB = block(ModBlocks.MOSSY_GREY_GRANITE_COBBLESTONE_VERTICAL_SLAB);
    public static final DeferredItem<Item> MOSSY_LIMESTONE_BRICK_VERTICAL_SLAB = block(ModBlocks.MOSSY_LIMESTONE_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> MOSSY_LIMESTONE_COBBLESTONE_VERTICAL_SLAB = block(ModBlocks.MOSSY_LIMESTONE_COBBLESTONE_VERTICAL_SLAB);
    public static final DeferredItem<Item> MOSSY_MARBLE_BRICK_VERTICAL_SLAB = block(ModBlocks.MOSSY_MARBLE_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> MOSSY_MARBLE_COBBLESTONE_VERTICAL_SLAB = block(ModBlocks.MOSSY_MARBLE_COBBLESTONE_VERTICAL_SLAB);
    public static final DeferredItem<Item> MOSSY_OILY_BLACK_BRICK_VERTICAL_SLAB = block(ModBlocks.MOSSY_OILY_BLACK_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> MOSSY_OILY_BLACK_COBBLESTONE_VERTICAL_SLAB = block(ModBlocks.MOSSY_OILY_BLACK_COBBLESTONE_VERTICAL_SLAB);
    public static final DeferredItem<Item> MOSSY_RED_SANDSTONE_BRICK_VERTICAL_SLAB = block(ModBlocks.MOSSY_RED_SANDSTONE_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> MOSSY_RED_SANDSTONE_COBBLESTONE_VERTICAL_SLAB = block(ModBlocks.MOSSY_RED_SANDSTONE_COBBLESTONE_VERTICAL_SLAB);
    public static final DeferredItem<Item> MOSSY_SANDSTONE_BRICK_VERTICAL_SLAB = block(ModBlocks.MOSSY_SANDSTONE_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> MOSSY_SANDSTONE_COBBLESTONE_VERTICAL_SLAB = block(ModBlocks.MOSSY_SANDSTONE_COBBLESTONE_VERTICAL_SLAB);
    public static final DeferredItem<Item> MOSSY_SLATE_BRICK_VERTICAL_SLAB = block(ModBlocks.MOSSY_SLATE_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> MOSSY_SLATE_COBBLESTONE_VERTICAL_SLAB = block(ModBlocks.MOSSY_SLATE_COBBLESTONE_VERTICAL_SLAB);
    public static final DeferredItem<Item> MUD_VERTICAL_SLAB = block(ModBlocks.MUD_VERTICAL_SLAB);
    public static final DeferredItem<Item> MYRRH_ROOFING_VERTICAL_SLAB = block(ModBlocks.MYRRH_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> MYRRH_VERTICAL_SLAB = block(ModBlocks.MYRRH_VERTICAL_SLAB);
    public static final DeferredItem<Item> MYRRH_WOOD_VERTICAL_SLAB = block(ModBlocks.MYRRH_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> NIGHTWOOD_ROOFING_VERTICAL_SLAB = block(ModBlocks.NIGHTWOOD_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> NIGHTWOOD_VERTICAL_SLAB = block(ModBlocks.NIGHTWOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> NIGHTWOOD_WOOD_VERTICAL_SLAB = block(ModBlocks.NIGHTWOOD_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> NUTMEG_ROOFING_VERTICAL_SLAB = block(ModBlocks.NUTMEG_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> NUTMEG_VERTICAL_SLAB = block(ModBlocks.NUTMEG_VERTICAL_SLAB);
    public static final DeferredItem<Item> NUTMEG_WOOD_VERTICAL_SLAB = block(ModBlocks.NUTMEG_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> OAK_ROOFING_VERTICAL_SLAB = block(ModBlocks.OAK_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> OAK_WOOD_VERTICAL_SLAB = block(ModBlocks.OAK_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> OILY_BLACK_BRICK_VERTICAL_SLAB = block(ModBlocks.OILY_BLACK_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> OILY_BLACK_COBBLESTONE_VERTICAL_SLAB = block(ModBlocks.OILY_BLACK_COBBLESTONE_VERTICAL_SLAB);
    public static final DeferredItem<Item> OILY_BLACK_ROCK_VERTICAL_SLAB = block(ModBlocks.OILY_BLACK_ROCK_VERTICAL_SLAB);
    public static final DeferredItem<Item> OLIVE_ROOFING_VERTICAL_SLAB = block(ModBlocks.OLIVE_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> OLIVE_VERTICAL_SLAB = block(ModBlocks.OLIVE_VERTICAL_SLAB);
    public static final DeferredItem<Item> OLIVE_WOOD_VERTICAL_SLAB = block(ModBlocks.OLIVE_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> ORANGE_ROOFING_VERTICAL_SLAB = block(ModBlocks.ORANGE_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> ORANGE_VERTICAL_SLAB = block(ModBlocks.ORANGE_VERTICAL_SLAB);
    public static final DeferredItem<Item> ORANGE_WOOD_VERTICAL_SLAB = block(ModBlocks.ORANGE_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> ORANGE_WOOL_VERTICAL_SLAB = block(ModBlocks.ORANGE_WOOL_VERTICAL_SLAB);
    public static final DeferredItem<Item> PALE_OAK_WOOD_VERTICAL_SLAB = block(ModBlocks.PALE_OAK_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> PEACH_ROOFING_VERTICAL_SLAB = block(ModBlocks.PEACH_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> PEACH_VERTICAL_SLAB = block(ModBlocks.PEACH_VERTICAL_SLAB);
    public static final DeferredItem<Item> PEACH_WOOD_VERTICAL_SLAB = block(ModBlocks.PEACH_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> PEAR_ROOFING_VERTICAL_SLAB = block(ModBlocks.PEAR_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> PEAR_VERTICAL_SLAB = block(ModBlocks.PEAR_VERTICAL_SLAB);
    public static final DeferredItem<Item> PEAR_WOOD_VERTICAL_SLAB = block(ModBlocks.PEAR_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> PERSIMMON_ROOFING_VERTICAL_SLAB = block(ModBlocks.PERSIMMON_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> PERSIMMON_VERTICAL_SLAB = block(ModBlocks.PERSIMMON_VERTICAL_SLAB);
    public static final DeferredItem<Item> PERSIMMON_WOOD_VERTICAL_SLAB = block(ModBlocks.PERSIMMON_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> PINE_ROOFING_VERTICAL_SLAB = block(ModBlocks.PINE_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> PINE_VERTICAL_SLAB = block(ModBlocks.PINE_VERTICAL_SLAB);
    public static final DeferredItem<Item> PINE_WOOD_VERTICAL_SLAB = block(ModBlocks.PINE_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> PINK_IVORY_ROOFING_VERTICAL_SLAB = block(ModBlocks.PINK_IVORY_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> PINK_IVORY_VERTICAL_SLAB = block(ModBlocks.PINK_IVORY_VERTICAL_SLAB);
    public static final DeferredItem<Item> PINK_IVORY_WOOD_VERTICAL_SLAB = block(ModBlocks.PINK_IVORY_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> PINK_WOOL_VERTICAL_SLAB = block(ModBlocks.PINK_WOOL_VERTICAL_SLAB);
    public static final DeferredItem<Item> PLUM_ROOFING_VERTICAL_SLAB = block(ModBlocks.PLUM_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> PLUM_VERTICAL_SLAB = block(ModBlocks.PLUM_VERTICAL_SLAB);
    public static final DeferredItem<Item> PLUM_WOOD_VERTICAL_SLAB = block(ModBlocks.PLUM_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> PODZOL_VERTICAL_SLAB = block(ModBlocks.PODZOL_VERTICAL_SLAB);
    public static final DeferredItem<Item> POMEGRANATE_ROOFING_VERTICAL_SLAB = block(ModBlocks.POMEGRANATE_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> POMEGRANATE_VERTICAL_SLAB = block(ModBlocks.POMEGRANATE_VERTICAL_SLAB);
    public static final DeferredItem<Item> POMEGRANATE_WOOD_VERTICAL_SLAB = block(ModBlocks.POMEGRANATE_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> PRUNE_ROOFING_VERTICAL_SLAB = block(ModBlocks.PRUNE_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> PRUNE_VERTICAL_SLAB = block(ModBlocks.PRUNE_VERTICAL_SLAB);
    public static final DeferredItem<Item> PRUNE_WOOD_VERTICAL_SLAB = block(ModBlocks.PRUNE_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> PURPLE_WOOL_VERTICAL_SLAB = block(ModBlocks.PURPLE_WOOL_VERTICAL_SLAB);
    public static final DeferredItem<Item> PURPLEHEART_ROOFING_VERTICAL_SLAB = block(ModBlocks.PURPLEHEART_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> PURPLEHEART_VERTICAL_SLAB = block(ModBlocks.PURPLEHEART_VERTICAL_SLAB);
    public static final DeferredItem<Item> PURPLEHEART_WOOD_VERTICAL_SLAB = block(ModBlocks.PURPLEHEART_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> RED_CHERRY_ROOFING_VERTICAL_SLAB = block(ModBlocks.RED_CHERRY_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> RED_CHERRY_VERTICAL_SLAB = block(ModBlocks.RED_CHERRY_VERTICAL_SLAB);
    public static final DeferredItem<Item> RED_CHERRY_WOOD_VERTICAL_SLAB = block(ModBlocks.RED_CHERRY_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> RED_SANDSTONE_BRICK_VERTICAL_SLAB = block(ModBlocks.RED_SANDSTONE_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> RED_SANDSTONE_COBBLESTONE_VERTICAL_SLAB = block(ModBlocks.RED_SANDSTONE_COBBLESTONE_VERTICAL_SLAB);
    public static final DeferredItem<Item> RED_WOOL_VERTICAL_SLAB = block(ModBlocks.RED_WOOL_VERTICAL_SLAB);
    public static final DeferredItem<Item> REDWOOD_ROOFING_VERTICAL_SLAB = block(ModBlocks.REDWOOD_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> REDWOOD_VERTICAL_SLAB = block(ModBlocks.REDWOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> REDWOOD_WOOD_VERTICAL_SLAB = block(ModBlocks.REDWOOD_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> ROOTED_DIRT_VERTICAL_SLAB = block(ModBlocks.ROOTED_DIRT_VERTICAL_SLAB);
    public static final DeferredItem<Item> SANDALWOOD_ROOFING_VERTICAL_SLAB = block(ModBlocks.SANDALWOOD_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> SANDALWOOD_VERTICAL_SLAB = block(ModBlocks.SANDALWOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> SANDALWOOD_WOOD_VERTICAL_SLAB = block(ModBlocks.SANDALWOOD_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> SANDBEGGAR_ROOFING_VERTICAL_SLAB = block(ModBlocks.SANDBEGGAR_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> SANDBEGGAR_VERTICAL_SLAB = block(ModBlocks.SANDBEGGAR_VERTICAL_SLAB);
    public static final DeferredItem<Item> SANDBEGGAR_WOOD_VERTICAL_SLAB = block(ModBlocks.SANDBEGGAR_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> SANDSTONE_BRICK_VERTICAL_SLAB = block(ModBlocks.SANDSTONE_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> SANDSTONE_COBBLESTONE_VERTICAL_SLAB = block(ModBlocks.SANDSTONE_COBBLESTONE_VERTICAL_SLAB);
    public static final DeferredItem<Item> SENTINAL_ROOFING_VERTICAL_SLAB = block(ModBlocks.SENTINAL_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> SENTINAL_VERTICAL_SLAB = block(ModBlocks.SENTINAL_VERTICAL_SLAB);
    public static final DeferredItem<Item> SENTINAL_WOOD_VERTICAL_SLAB = block(ModBlocks.SENTINAL_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> SLATE_BRICK_VERTICAL_SLAB = block(ModBlocks.SLATE_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> SLATE_COBBLESTONE_VERTICAL_SLAB = block(ModBlocks.SLATE_COBBLESTONE_VERTICAL_SLAB);
    public static final DeferredItem<Item> SLATE_ROCK_VERTICAL_SLAB = block(ModBlocks.SLATE_ROCK_VERTICAL_SLAB);
    public static final DeferredItem<Item> SLATE_SHINGLES_VERTICAL_SLAB = block(ModBlocks.SLATE_SHINGLES_VERTICAL_SLAB);
    public static final DeferredItem<Item> SMOOTH_BASALT_ROCK_VERTICAL_SLAB = block(ModBlocks.SMOOTH_BASALT_ROCK_VERTICAL_SLAB);
    public static final DeferredItem<Item> SMOOTH_FUSED_BLACK_ROCK_VERTICAL_SLAB = block(ModBlocks.SMOOTH_FUSED_BLACK_ROCK_VERTICAL_SLAB);
    public static final DeferredItem<Item> SMOOTH_GREY_GRANITE_ROCK_VERTICAL_SLAB = block(ModBlocks.SMOOTH_GREY_GRANITE_ROCK_VERTICAL_SLAB);
    public static final DeferredItem<Item> SMOOTH_LIMESTONE_ROCK_VERTICAL_SLAB = block(ModBlocks.SMOOTH_LIMESTONE_ROCK_VERTICAL_SLAB);
    public static final DeferredItem<Item> SMOOTH_MARBLE_ROCK_VERTICAL_SLAB = block(ModBlocks.SMOOTH_MARBLE_ROCK_VERTICAL_SLAB);
    public static final DeferredItem<Item> SMOOTH_OILY_BLACK_ROCK_VERTICAL_SLAB = block(ModBlocks.SMOOTH_OILY_BLACK_ROCK_VERTICAL_SLAB);
    public static final DeferredItem<Item> SMOOTH_SLATE_ROCK_VERTICAL_SLAB = block(ModBlocks.SMOOTH_SLATE_ROCK_VERTICAL_SLAB);
    public static final DeferredItem<Item> SOLDIER_PINE_ROOFING_VERTICAL_SLAB = block(ModBlocks.SOLDIER_PINE_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> SOLDIER_PINE_VERTICAL_SLAB = block(ModBlocks.SOLDIER_PINE_VERTICAL_SLAB);
    public static final DeferredItem<Item> SOLDIER_PINE_WOOD_VERTICAL_SLAB = block(ModBlocks.SOLDIER_PINE_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> SPRUCE_ROOFING_VERTICAL_SLAB = block(ModBlocks.SPRUCE_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> SPRUCE_WOOD_VERTICAL_SLAB = block(ModBlocks.SPRUCE_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_ACACIA_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_ACACIA_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_ALDER_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_ALDER_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_ALMOND_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_ALMOND_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_APPLE_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_APPLE_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_APRICOT_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_APRICOT_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_ASH_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_ASH_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_ASPEN_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_ASPEN_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_BEECH_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_BEECH_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_BIRCH_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_BIRCH_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_BLACK_CHERRY_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_BLACK_CHERRY_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_BLACK_COTTONWOOD_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_BLACK_COTTONWOOD_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_BLACKBARK_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_BLACKBARK_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_BLACKTHORN_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_BLACKTHORN_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_BLOODWOOD_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_BLOODWOOD_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_BLUE_MAHOE_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_BLUE_MAHOE_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_CEDAR_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_CEDAR_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_CHERRY_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_CHERRY_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_CHESTNUT_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_CHESTNUT_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_CINNAMON_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_CINNAMON_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_CLOVE_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_CLOVE_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_COTTONWOOD_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_COTTONWOOD_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_CRABAPPLE_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_CRABAPPLE_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_DARK_OAK_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_DARK_OAK_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_DATE_PALM_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_DATE_PALM_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_EBONY_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_EBONY_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_ELM_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_ELM_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_FIG_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_FIG_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_FIR_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_FIR_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_GOLDENHEART_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_GOLDENHEART_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_HAWTHORN_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_HAWTHORN_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_HEMLOCK_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_HEMLOCK_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_IRONWOOD_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_IRONWOOD_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_JUNGLE_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_JUNGLE_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_LEMON_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_LEMON_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_LIME_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_LIME_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_LINDEN_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_LINDEN_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_MAHOGANY_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_MAHOGANY_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_MANGROVE_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_MANGROVE_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_MAPLE_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_MAPLE_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_MYRRH_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_MYRRH_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_NIGHTWOOD_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_NIGHTWOOD_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_NUTMEG_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_NUTMEG_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_OAK_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_OAK_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_OLIVE_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_OLIVE_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_ORANGE_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_ORANGE_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_PALE_OAK_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_PALE_OAK_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_PEACH_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_PEACH_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_PEAR_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_PEAR_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_PERSIMMON_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_PERSIMMON_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_PINE_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_PINE_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_PINK_IVORY_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_PINK_IVORY_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_PLUM_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_PLUM_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_POMEGRANATE_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_POMEGRANATE_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_PRUNE_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_PRUNE_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_PURPLEHEART_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_PURPLEHEART_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_RED_CHERRY_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_RED_CHERRY_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_REDWOOD_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_REDWOOD_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_SANDALWOOD_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_SANDALWOOD_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_SANDBEGGAR_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_SANDBEGGAR_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_SENTINAL_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_SENTINAL_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_SOLDIER_PINE_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_SOLDIER_PINE_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_SPRUCE_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_SPRUCE_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_TIGERWOOD_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_TIGERWOOD_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_WEIRWOOD_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_WEIRWOOD_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_WHITE_CHERRY_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_WHITE_CHERRY_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_WILLOW_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_WILLOW_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> STRIPPED_WORMTREE_WOOD_VERTICAL_SLAB = block(ModBlocks.STRIPPED_WORMTREE_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> TIGERWOOD_ROOFING_VERTICAL_SLAB = block(ModBlocks.TIGERWOOD_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> TIGERWOOD_VERTICAL_SLAB = block(ModBlocks.TIGERWOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> TIGERWOOD_WOOD_VERTICAL_SLAB = block(ModBlocks.TIGERWOOD_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> WEIRWOOD_ROOFING_VERTICAL_SLAB = block(ModBlocks.WEIRWOOD_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> WEIRWOOD_VERTICAL_SLAB = block(ModBlocks.WEIRWOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> WEIRWOOD_WOOD_VERTICAL_SLAB = block(ModBlocks.WEIRWOOD_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> WHITE_CHERRY_ROOFING_VERTICAL_SLAB = block(ModBlocks.WHITE_CHERRY_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> WHITE_CHERRY_VERTICAL_SLAB = block(ModBlocks.WHITE_CHERRY_VERTICAL_SLAB);
    public static final DeferredItem<Item> WHITE_CHERRY_WOOD_VERTICAL_SLAB = block(ModBlocks.WHITE_CHERRY_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> WHITE_WOOL_VERTICAL_SLAB = block(ModBlocks.WHITE_WOOL_VERTICAL_SLAB);
    public static final DeferredItem<Item> WILLOW_ROOFING_VERTICAL_SLAB = block(ModBlocks.WILLOW_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> WILLOW_VERTICAL_SLAB = block(ModBlocks.WILLOW_VERTICAL_SLAB);
    public static final DeferredItem<Item> WILLOW_WOOD_VERTICAL_SLAB = block(ModBlocks.WILLOW_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> WORMTREE_ROOFING_VERTICAL_SLAB = block(ModBlocks.WORMTREE_ROOFING_VERTICAL_SLAB);
    public static final DeferredItem<Item> WORMTREE_VERTICAL_SLAB = block(ModBlocks.WORMTREE_VERTICAL_SLAB);
    public static final DeferredItem<Item> WORMTREE_WOOD_VERTICAL_SLAB = block(ModBlocks.WORMTREE_WOOD_VERTICAL_SLAB);
    public static final DeferredItem<Item> YELLOW_WOOL_VERTICAL_SLAB = block(ModBlocks.YELLOW_WOOL_VERTICAL_SLAB);
    public static final DeferredItem<Item> OAK_VERTICAL_SLAB = block(ModBlocks.OAK_VERTICAL_SLAB);
    public static final DeferredItem<Item> SPRUCE_VERTICAL_SLAB = block(ModBlocks.SPRUCE_VERTICAL_SLAB);
    public static final DeferredItem<Item> BIRCH_VERTICAL_SLAB = block(ModBlocks.BIRCH_VERTICAL_SLAB);
    public static final DeferredItem<Item> JUNGLE_VERTICAL_SLAB = block(ModBlocks.JUNGLE_VERTICAL_SLAB);
    public static final DeferredItem<Item> ACACIA_VERTICAL_SLAB = block(ModBlocks.ACACIA_VERTICAL_SLAB);
    public static final DeferredItem<Item> DARK_OAK_VERTICAL_SLAB = block(ModBlocks.DARK_OAK_VERTICAL_SLAB);
    public static final DeferredItem<Item> MANGROVE_VERTICAL_SLAB = block(ModBlocks.MANGROVE_VERTICAL_SLAB);
    public static final DeferredItem<Item> CHERRY_VERTICAL_SLAB = block(ModBlocks.CHERRY_VERTICAL_SLAB);
    public static final DeferredItem<Item> BAMBOO_VERTICAL_SLAB = block(ModBlocks.BAMBOO_VERTICAL_SLAB);
    public static final DeferredItem<Item> BAMBOO_MOSAIC_VERTICAL_SLAB = block(ModBlocks.BAMBOO_MOSAIC_VERTICAL_SLAB);
    public static final DeferredItem<Item> CRIMSON_VERTICAL_SLAB = block(ModBlocks.CRIMSON_VERTICAL_SLAB);
    public static final DeferredItem<Item> WARPED_VERTICAL_SLAB = block(ModBlocks.WARPED_VERTICAL_SLAB);
    public static final DeferredItem<Item> PALE_OAK_VERTICAL_SLAB = block(ModBlocks.PALE_OAK_VERTICAL_SLAB);
    public static final DeferredItem<Item> PETRIFIED_OAK_VERTICAL_SLAB = block(ModBlocks.PETRIFIED_OAK_VERTICAL_SLAB);
    public static final DeferredItem<Item> STONE_VERTICAL_SLAB = block(ModBlocks.STONE_VERTICAL_SLAB);
    public static final DeferredItem<Item> SMOOTH_STONE_VERTICAL_SLAB = block(ModBlocks.SMOOTH_STONE_VERTICAL_SLAB);
    public static final DeferredItem<Item> COBBLESTONE_VERTICAL_SLAB = block(ModBlocks.COBBLESTONE_VERTICAL_SLAB);
    public static final DeferredItem<Item> MOSSY_COBBLESTONE_VERTICAL_SLAB = block(ModBlocks.MOSSY_COBBLESTONE_VERTICAL_SLAB);
    public static final DeferredItem<Item> GRANITE_VERTICAL_SLAB = block(ModBlocks.GRANITE_VERTICAL_SLAB);
    public static final DeferredItem<Item> POLISHED_GRANITE_VERTICAL_SLAB = block(ModBlocks.POLISHED_GRANITE_VERTICAL_SLAB);
    public static final DeferredItem<Item> DIORITE_VERTICAL_SLAB = block(ModBlocks.DIORITE_VERTICAL_SLAB);
    public static final DeferredItem<Item> POLISHED_DIORITE_VERTICAL_SLAB = block(ModBlocks.POLISHED_DIORITE_VERTICAL_SLAB);
    public static final DeferredItem<Item> ANDESITE_VERTICAL_SLAB = block(ModBlocks.ANDESITE_VERTICAL_SLAB);
    public static final DeferredItem<Item> POLISHED_ANDESITE_VERTICAL_SLAB = block(ModBlocks.POLISHED_ANDESITE_VERTICAL_SLAB);
    public static final DeferredItem<Item> BRICK_VERTICAL_SLAB = block(ModBlocks.BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> MUD_BRICK_VERTICAL_SLAB = block(ModBlocks.MUD_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> NETHER_BRICK_VERTICAL_SLAB = block(ModBlocks.NETHER_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> RED_NETHER_BRICK_VERTICAL_SLAB = block(ModBlocks.RED_NETHER_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> STONE_BRICK_VERTICAL_SLAB = block(ModBlocks.STONE_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> MOSSY_STONE_BRICK_VERTICAL_SLAB = block(ModBlocks.MOSSY_STONE_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> END_STONE_BRICK_VERTICAL_SLAB = block(ModBlocks.END_STONE_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> PRISMARINE_VERTICAL_SLAB = block(ModBlocks.PRISMARINE_VERTICAL_SLAB);
    public static final DeferredItem<Item> PRISMARINE_BRICK_VERTICAL_SLAB = block(ModBlocks.PRISMARINE_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> DARK_PRISMARINE_VERTICAL_SLAB = block(ModBlocks.DARK_PRISMARINE_VERTICAL_SLAB);
    public static final DeferredItem<Item> PURPUR_VERTICAL_SLAB = block(ModBlocks.PURPUR_VERTICAL_SLAB);
    public static final DeferredItem<Item> BLACKSTONE_VERTICAL_SLAB = block(ModBlocks.BLACKSTONE_VERTICAL_SLAB);
    public static final DeferredItem<Item> POLISHED_BLACKSTONE_VERTICAL_SLAB = block(ModBlocks.POLISHED_BLACKSTONE_VERTICAL_SLAB);
    public static final DeferredItem<Item> POLISHED_BLACKSTONE_BRICK_VERTICAL_SLAB = block(ModBlocks.POLISHED_BLACKSTONE_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> COBBLED_DEEPSLATE_VERTICAL_SLAB = block(ModBlocks.COBBLED_DEEPSLATE_VERTICAL_SLAB);
    public static final DeferredItem<Item> POLISHED_DEEPSLATE_VERTICAL_SLAB = block(ModBlocks.POLISHED_DEEPSLATE_VERTICAL_SLAB);
    public static final DeferredItem<Item> DEEPSLATE_BRICK_VERTICAL_SLAB = block(ModBlocks.DEEPSLATE_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> DEEPSLATE_TILE_VERTICAL_SLAB = block(ModBlocks.DEEPSLATE_TILE_VERTICAL_SLAB);
    public static final DeferredItem<Item> TUFF_VERTICAL_SLAB = block(ModBlocks.TUFF_VERTICAL_SLAB);
    public static final DeferredItem<Item> POLISHED_TUFF_VERTICAL_SLAB = block(ModBlocks.POLISHED_TUFF_VERTICAL_SLAB);
    public static final DeferredItem<Item> TUFF_BRICK_VERTICAL_SLAB = block(ModBlocks.TUFF_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> RESIN_BRICK_VERTICAL_SLAB = block(ModBlocks.RESIN_BRICK_VERTICAL_SLAB);
    public static final DeferredItem<Item> SANDSTONE_VERTICAL_SLAB = block(ModBlocks.SANDSTONE_VERTICAL_SLAB);
    public static final DeferredItem<Item> SMOOTH_SANDSTONE_VERTICAL_SLAB = block(ModBlocks.SMOOTH_SANDSTONE_VERTICAL_SLAB);
    public static final DeferredItem<Item> CUT_SANDSTONE_VERTICAL_SLAB = block(ModBlocks.CUT_SANDSTONE_VERTICAL_SLAB);
    public static final DeferredItem<Item> RED_SANDSTONE_VERTICAL_SLAB = block(ModBlocks.RED_SANDSTONE_VERTICAL_SLAB);
    public static final DeferredItem<Item> SMOOTH_RED_SANDSTONE_VERTICAL_SLAB = block(ModBlocks.SMOOTH_RED_SANDSTONE_VERTICAL_SLAB);
    public static final DeferredItem<Item> CUT_RED_SANDSTONE_VERTICAL_SLAB = block(ModBlocks.CUT_RED_SANDSTONE_VERTICAL_SLAB);
    public static final DeferredItem<Item> QUARTZ_VERTICAL_SLAB = block(ModBlocks.QUARTZ_VERTICAL_SLAB);
    public static final DeferredItem<Item> SMOOTH_QUARTZ_VERTICAL_SLAB = block(ModBlocks.SMOOTH_QUARTZ_VERTICAL_SLAB);

    private static <I extends Item> DeferredItem<I> register(String name, Function<Item.Properties, ? extends I> supplier) {
        return REGISTRY.registerItem(name, supplier, new Item.Properties());
    }

    private static DeferredItem<Item> block(DeferredHolder<Block, Block> block) {
        return block(block, new Item.Properties());
    }

    private static DeferredItem<Item> block(DeferredHolder<Block, Block> block, Item.Properties properties) {
        return REGISTRY.registerItem(block.getId().getPath(), prop -> new BlockItem(block.get(), prop), properties);
    }

    private static DeferredItem<Item> door(DeferredHolder<Block, Block> block) {
        return REGISTRY.registerItem(block.getId().getPath(),
                prop -> new DoubleHighBlockItem(block.get(), prop), new Item.Properties());
    }
}