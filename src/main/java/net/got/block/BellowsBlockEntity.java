package net.got.block;

import net.got.init.GotModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BellowsBlockEntity extends BlockEntity {

    public static final int MAX_TICKS = 30;

    /** Increments each tick while pumping; resets to 0 when done. */
    public int animationProgress;
    public boolean pumping;

    public BellowsBlockEntity(BlockPos pos, BlockState state) {
        super(GotModBlockEntities.BELLOWS.get(), pos, state);
    }

    // ── Sync ─────────────────────────────────────────────────────────────────

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putBoolean("pumping", pumping);
        tag.putInt("animationProgress", animationProgress);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        pumping = tag.getBoolean("pumping");
        animationProgress = tag.getInt("animationProgress");
    }

    // ── Synced block event triggers the animation on the client ──────────────

    @Override
    public boolean triggerEvent(int id, int data) {
        if (id == 1) {
            this.pumping = true;
            this.animationProgress = 0;
            return true;
        }
        return super.triggerEvent(id, data);
    }

    /**
     * Call this from the server side when the bellows is activated.
     * Returns true if the animation was successfully started.
     */
    public boolean tryStartPump() {
        if (!pumping && level != null && !level.isClientSide()) {
            pumping = true;
            animationProgress = 0;
            // Notify client via synced block event (triggers triggerEvent on client)
            level.blockEvent(worldPosition, getBlockState().getBlock(), 1, 0);
            setChanged();
            return true;
        }
        return false;
    }

    // ── Ticking ──────────────────────────────────────────────────────────────

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