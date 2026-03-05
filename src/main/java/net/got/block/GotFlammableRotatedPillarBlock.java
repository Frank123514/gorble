package net.got.block;

import net.got.init.GotModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.ItemAbilities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GotFlammableRotatedPillarBlock extends RotatedPillarBlock {

    public GotFlammableRotatedPillarBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFlammable(@NotNull BlockState state, @NotNull BlockGetter level,
                               @NotNull BlockPos pos, @NotNull Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(@NotNull BlockState state, @NotNull BlockGetter level,
                               @NotNull BlockPos pos, @NotNull Direction direction) {
        return 5;
    }

    @Override
    public int getFireSpreadSpeed(@NotNull BlockState state, @NotNull BlockGetter level,
                                  @NotNull BlockPos pos, @NotNull Direction direction) {
        return 5;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(@NotNull BlockState state, @NotNull UseOnContext context,
                                                     @NotNull ItemAbility toolAction, boolean simulate) {
        if (toolAction == ItemAbilities.AXE_STRIP) {
            Block currentBlock = state.getBlock();

            // Check logs
            for (String woodType : GotModBlocks.LOGS.keySet()) {
                if (GotModBlocks.LOGS.get(woodType).get() == currentBlock) {
                    Block strippedLog = GotModBlocks.STRIPPED_LOGS.get(woodType).get();
                    return strippedLog.defaultBlockState().setValue(AXIS, state.getValue(AXIS));
                }
            }

            // Check woods (bark-all-sides)
            for (String woodType : GotModBlocks.WOODS.keySet()) {
                if (GotModBlocks.WOODS.get(woodType).get() == currentBlock) {
                    Block strippedWood = GotModBlocks.STRIPPED_WOODS.get(woodType).get();
                    return strippedWood.defaultBlockState().setValue(AXIS, state.getValue(AXIS));
                }
            }
        }

        return super.getToolModifiedState(state, context, toolAction, simulate);
    }
}