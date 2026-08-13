package net.got.block;

import net.got.init.ModBlocks;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.ItemAbilities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WoodBranchBlock extends WallBlock {
    public WoodBranchBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockState getToolModifiedState(@NotNull BlockState state, @NotNull UseOnContext context,
                                                     @NotNull ItemAbility toolAction, boolean simulate) {
        if (toolAction == ItemAbilities.AXE_STRIP) {
            Block currentBlock = state.getBlock();

            for (String woodType : ModBlocks.BRANCHES.keySet()) {
                if (ModBlocks.BRANCHES.get(woodType).get() == currentBlock) {
                    var strippedBranchDeferred = ModBlocks.STRIPPED_BRANCHES.get(woodType);
                    if (strippedBranchDeferred != null) {
                        BlockState stripped = strippedBranchDeferred.get().defaultBlockState();
                        for (Property<?> property : state.getProperties()) {
                            if (stripped.hasProperty(property)) {
                                stripped = copyProperty(state, stripped, property);
                            }
                        }
                        return stripped;
                    }
                }
            }
        }

        return super.getToolModifiedState(state, context, toolAction, simulate);
    }

    private static <T extends Comparable<T>> BlockState copyProperty(BlockState from, BlockState to, Property<T> property) {
        return to.setValue(property, from.getValue(property));
    }
}
