package net.got.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Shared class for all regional rock cube-all variants:
 * base rock, brick, cracked brick, mossy brick,
 * cobblestone, mossy cobblestone, polished rock.
 */
public class RegionalRockBlock extends Block {
    public RegionalRockBlock(Properties properties) {
        super(properties.strength(1.5f, 6f).requiresCorrectToolForDrops());
    }

    @Override
    public int getLightBlock(BlockState state) {
        return 15;
    }
}
