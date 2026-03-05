package net.got.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.PushReaction;

public class GotDoorBlock extends DoorBlock {

    public GotDoorBlock(BlockSetType blockSetType, Properties p) {
        super(blockSetType, p.sound(SoundType.WOOD).strength(3f)
                .noOcclusion().pushReaction(PushReaction.DESTROY).ignitedByLava());
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 5;
    }
}