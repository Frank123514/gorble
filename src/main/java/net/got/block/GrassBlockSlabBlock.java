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
