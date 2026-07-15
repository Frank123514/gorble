package net.got.block;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Slab cut from {@link net.minecraft.world.level.block.Blocks#GRASS_BLOCK}.
 *
 * <p>Its biome-tinted top face is handled the same way vanilla grass_block is:
 * world rendering is tinted in {@code ClientSetup#registerBlockColors}, and the
 * inventory/hand icon is tinted via a {@code "minecraft:grass"} tint source on
 * its item model (assets/got/items/grass_block_slab.json). Right-clicking it
 * with a shovel flattens it into a dirt path slab, mirroring how a shovel turns
 * grass_block into dirt_path.
 *
 * <p>Ported from the "Grass Slabs" mod's {@code GrassSlabBlock}, adapted to this
 * mod's 1.21.4 NeoForge APIs ({@link ItemAbility} replaces the old {@code ToolAction}).
 */
public class GrassBlockSlabBlock extends SlabBlock {
    private final DeferredBlock<Block> flattenedBlock;

    public GrassBlockSlabBlock(Properties properties, DeferredBlock<Block> flattenedBlock) {
        super(properties);
        this.flattenedBlock = flattenedBlock;
    }

    @Nullable
    @Override
    public BlockState getToolModifiedState(@NotNull BlockState state, @NotNull UseOnContext context,
                                            @NotNull ItemAbility itemAbility, boolean simulate) {
        if (itemAbility != ItemAbilities.SHOVEL_FLATTEN) {
            return super.getToolModifiedState(state, context, itemAbility, simulate);
        }
        return flattenedBlock.get().defaultBlockState();
    }
}
