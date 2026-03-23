package net.got.block;

import com.mojang.serialization.MapCodec;
import net.got.init.GotModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class OvenBlock extends AbstractFurnaceBlock {

    public static final MapCodec<OvenBlock> CODEC = MapCodec.unit(OvenBlock::new);

    @Override
    public MapCodec<OvenBlock> codec() { return CODEC; }

    public OvenBlock() {
        this(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.FURNACE));
    }

    public OvenBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new OvenBlockEntity(pos, state);
    }

    @Override
    protected void openContainer(Level level, BlockPos pos, Player player) {
        if (!level.isClientSide) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof OvenBlockEntity oven) {
                player.openMenu(oven);
            }
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide) return null;
        return createTickerHelper(type, GotModBlockEntities.OVEN.get(),
                OvenBlockEntity::serverTick);
    }
}
