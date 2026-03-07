package net.got.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class GotWallSignBlock extends WallSignBlock {
    private final Supplier<BlockEntityType<SignBlockEntity>> blockEntityType;

    public GotWallSignBlock(WoodType woodType, BlockBehaviour.Properties props,
                            Supplier<BlockEntityType<SignBlockEntity>> blockEntityType) {
        super(woodType, props);
        this.blockEntityType = blockEntityType;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SignBlockEntity(blockEntityType.get(), pos, state);
    }
}