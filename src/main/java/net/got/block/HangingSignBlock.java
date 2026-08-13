package net.got.block;

import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;

public class HangingSignBlock extends CeilingHangingSignBlock {
    public HangingSignBlock(WoodType woodType, BlockBehaviour.Properties properties) {
        super(woodType, properties
                .sound(SoundType.HANGING_SIGN)
                .strength(1f)
                .noCollision()
                .ignitedByLava());
    }
}
