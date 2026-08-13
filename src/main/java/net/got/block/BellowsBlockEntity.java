package net.got.block;

import net.got.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class BellowsBlockEntity extends BlockEntity {

    public static final int MAX_TICKS = 30;

    public int animationProgress;
    public boolean pumping;

    public BellowsBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BELLOWS.get(), pos, state);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        
    }

    @Override
    public boolean triggerEvent(int id, int data) {
        if (id == 1) {
            
            this.pumping = true;
            this.animationProgress = 0;
            return true;
        }
        return super.triggerEvent(id, data);
    }

    public boolean tryStartPump() {
        if (!pumping && level != null && !level.isClientSide()) {
            pumping = true;
            animationProgress = 0;
            level.blockEvent(worldPosition, getBlockState().getBlock(), 1, 0);
            return true;
        }
        return false;
    }

    private static void tick(BellowsBlockEntity be) {
        if (be.pumping) {
            be.animationProgress++;
            if (be.animationProgress > MAX_TICKS) {
                be.pumping = false;
                be.animationProgress = 0;
            }
        }
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, BellowsBlockEntity be) {
        tick(be);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, BellowsBlockEntity be) {
        tick(be);
    }
}