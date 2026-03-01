package net.got.block;

import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class RegionalRockPressurePlateBlock extends PressurePlateBlock {
    public RegionalRockPressurePlateBlock(BlockBehaviour.Properties properties) {
        super(BlockSetType.STONE, properties.noCollission());
    }
}
