package net.minecraft.world.level.block;

import com.mojang.serialization.MapCodec;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;

public class EndGatewayBlock extends BaseEntityBlock implements Portal {
    public static final MapCodec<EndGatewayBlock> CODEC = simpleCodec(EndGatewayBlock::new);

    @Override
    public MapCodec<EndGatewayBlock> codec() {
        return CODEC;
    }

    public EndGatewayBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TheEndGatewayBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(
            blockEntityType, BlockEntityType.END_GATEWAY, level.isClientSide ? TheEndGatewayBlockEntity::beamAnimationTick : TheEndGatewayBlockEntity::portalTick
        );
    }

    /**
     * Called periodically clientside on blocks near the player to show effects (like furnace fire particles).
     */
    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof TheEndGatewayBlockEntity) {
            int i = ((TheEndGatewayBlockEntity)blockentity).getParticleAmount();

            for (int j = 0; j < i; j++) {
                double d0 = (double)pos.getX() + random.nextDouble();
                double d1 = (double)pos.getY() + random.nextDouble();
                double d2 = (double)pos.getZ() + random.nextDouble();
                double d3 = (random.nextDouble() - 0.5) * 0.5;
                double d4 = (random.nextDouble() - 0.5) * 0.5;
                double d5 = (random.nextDouble() - 0.5) * 0.5;
                int k = random.nextInt(2) * 2 - 1;
                if (random.nextBoolean()) {
                    d2 = (double)pos.getZ() + 0.5 + 0.25 * (double)k;
                    d5 = (double)(random.nextFloat() * 2.0F * (float)k);
                } else {
                    d0 = (double)pos.getX() + 0.5 + 0.25 * (double)k;
                    d3 = (double)(random.nextFloat() * 2.0F * (float)k);
                }

                level.addParticle(ParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
            }
        }
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }

    @Override
    protected boolean canBeReplaced(BlockState state, Fluid fluid) {
        return false;
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity.canUsePortal(false)
            && !level.isClientSide
            && level.getBlockEntity(pos) instanceof TheEndGatewayBlockEntity theendgatewayblockentity
            && !theendgatewayblockentity.isCoolingDown()) {
            entity.setAsInsidePortal(this, pos);
            TheEndGatewayBlockEntity.triggerCooldown(level, pos, state, theendgatewayblockentity);
        }
    }

    @Nullable
    @Override
    public TeleportTransition getPortalDestination(ServerLevel level, Entity entity, BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof TheEndGatewayBlockEntity theendgatewayblockentity) {
            Vec3 vec3 = theendgatewayblockentity.getPortalPosition(level, pos);
            if (vec3 == null) {
                return null;
            } else {
                return entity instanceof ThrownEnderpearl
                    ? new TeleportTransition(level, vec3, Vec3.ZERO, 0.0F, 0.0F, Set.of(), TeleportTransition.PLACE_PORTAL_TICKET)
                    : new TeleportTransition(
                        level, vec3, Vec3.ZERO, 0.0F, 0.0F, Relative.union(Relative.DELTA, Relative.ROTATION), TeleportTransition.PLACE_PORTAL_TICKET
                    );
            }
        } else {
            return null;
        }
    }
}
