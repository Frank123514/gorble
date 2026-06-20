package net.got.block;

import net.got.init.GotModBlocks;
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

/**
 * Branch Block — a wall-shaped block with the wood log texture.
 * Behaves identically to a WallBlock but is crafted from logs.
 *
 * Axe-strippable: right-clicking with an axe turns a branch into its
 * stripped counterpart, looked up via GotModBlocks.BRANCHES / STRIPPED_BRANCHES
 * (same pattern as logs/wood via GotFlammableRotatedPillarBlock).
 */
public class WoodBranchBlock extends WallBlock {
    public WoodBranchBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockState getToolModifiedState(@NotNull BlockState state, @NotNull UseOnContext context,
                                                     @NotNull ItemAbility toolAction, boolean simulate) {
        if (toolAction == ItemAbilities.AXE_STRIP) {
            Block currentBlock = state.getBlock();

            for (String woodType : GotModBlocks.BRANCHES.keySet()) {
                if (GotModBlocks.BRANCHES.get(woodType).get() == currentBlock) {
                    var strippedBranchDeferred = GotModBlocks.STRIPPED_BRANCHES.get(woodType);
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

    /**
     * Copies a single property's value from one BlockState to another.
     * Pulled out into its own generic method so the property's type
     * variable T is captured exactly once — letting getValue/setValue
     * agree on T, which a single capture-converted call site cannot do.
     */
    private static <T extends Comparable<T>> BlockState copyProperty(BlockState from, BlockState to, Property<T> property) {
        return to.setValue(property, from.getValue(property));
    }
}

