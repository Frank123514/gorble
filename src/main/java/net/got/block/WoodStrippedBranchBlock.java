package net.got.block;

import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Stripped Branch Block — the axe-stripped (or directly crafted) counterpart
 * of a WoodBranchBlock. Behaves identically to a WallBlock but is textured
 * with the stripped-log texture. Has no further tool-modified state of its
 * own — once stripped, a branch stays stripped.
 */
public class WoodStrippedBranchBlock extends WallBlock {
    public WoodStrippedBranchBlock(Properties properties) {
        super(properties);
    }
}
