package net.got.block;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GrassBlockStairsBlock extends StairBlock {
    private final DeferredBlock<Block> flattenedBlock;

    public GrassBlockStairsBlock(Properties properties, DeferredBlock<Block> flattenedBlock) {
        super(Blocks.GRASS_BLOCK.defaultBlockState(), properties);
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
