package net.got.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;

import net.got.GotMod;

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
	public static final DeferredItem<Item> NORTH_ROCK = block(GotModBlocks.NORTH_ROCK);
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

    private static <I extends Item> DeferredItem<I> register(String name, Function<Item.Properties, ? extends I> supplier) {
        return REGISTRY.registerItem(name, supplier, new Item.Properties());
    }

    private static DeferredItem<Item> block(DeferredHolder<Block, Block> block) {
        return block(block, new Item.Properties());
    }

    private static DeferredItem<Item> block(DeferredHolder<Block, Block> block, Item.Properties properties) {
        return REGISTRY.registerItem(block.getId().getPath(), prop -> new BlockItem(block.get(), prop), properties);
    }
}