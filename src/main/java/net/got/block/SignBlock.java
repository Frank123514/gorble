package net.got.block;

import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;

public class SignBlock extends StandingSignBlock {

    public SignBlock(WoodType woodType, Properties properties) {
        super(woodType, properties);
    }
}