package net.got.block;

import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Branch Block — a wall-shaped block with the wood log texture.
 * Behaves identically to a WallBlock but is crafted from logs.
 */
public class WoodBranchBlock extends WallBlock {
    public WoodBranchBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
