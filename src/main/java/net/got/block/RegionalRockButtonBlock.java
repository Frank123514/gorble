package net.got.block;

import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class RegionalRockButtonBlock extends ButtonBlock {
    public RegionalRockButtonBlock(BlockBehaviour.Properties properties) {
        super(BlockSetType.STONE, 20, properties.noCollission());
    }
}
