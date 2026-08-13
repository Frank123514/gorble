package net.got.block;

import com.mojang.serialization.MapCodec;
import net.got.item.SmithingHammerItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.Map;

public class SmithingAnvilBlock extends BaseEntityBlock {

    public static final MapCodec<SmithingAnvilBlock> CODEC = simpleCodec(SmithingAnvilBlock::new);

    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty         LIT    = BlockStateProperties.LIT;

    private static final VoxelShape SHAPE_NORTH = Shapes.or(
            Block.box(2, 0, 2,  14,  4, 14),
            Block.box(4, 4, 3,  12,  5, 13),
            Block.box(6, 5, 4,  10, 10, 12),
            Block.box(3,10, 0,  13, 16, 16)
    );

    private static final Map<Direction, VoxelShape> SHAPES_BY_FACING =
            buildRotatedShapes();

    private static Map<Direction, VoxelShape> buildRotatedShapes() {
        Map<Direction, VoxelShape> map = new EnumMap<>(Direction.class);
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            map.put(dir, rotateShape(SHAPE_NORTH, dir));
        }
        return map;
    }

    private static VoxelShape rotateShape(VoxelShape shape, Direction facing) {
        int steps = switch (facing) {
            case NORTH -> 0;
            case EAST  -> 1;
            case SOUTH -> 2;
            case WEST  -> 3;
            default    -> 0;
        };
        VoxelShape result = shape;
        for (int i = 0; i < steps; i++) {
            VoxelShape current = result;
            
            VoxelShape[] holder = new VoxelShape[]{Shapes.empty()};
            current.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
                holder[0] = Shapes.or(holder[0], Shapes.box(
                        1 - maxZ, minY, minX,
                        1 - minZ, maxY, maxX
                ));
            });
            result = holder[0];
        }
        return result;
    }

    public SmithingAnvilBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(LIT, false));
    }

    @Override public MapCodec<SmithingAnvilBlock> codec() { return CODEC; }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState()
                .setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) { return RenderShape.MODEL; }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level,
                                  BlockPos pos, CollisionContext context) {
        return SHAPES_BY_FACING.get(state.getValue(FACING));
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) { return true; }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                               Player player, BlockHitResult hitResult) {
        if (level.isClientSide()) return InteractionResult.SUCCESS;

        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof SmithingAnvilBlockEntity anvil) {
            if (anvil.isAwaitingPickup()) {
                
                anvil.collectCraftedItem(player);
                return InteractionResult.CONSUME;
            }
            player.openMenu(anvil);
            player.awardStat(Stats.INTERACT_WITH_FURNACE);
        }
        return InteractionResult.CONSUME;
    }

    @Nullable @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SmithingAnvilBlockEntity(pos, state);
    }

    @Nullable @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) return null;
        return createTickerHelper(type,
                net.got.init.ModBlockEntities.SMITHING_ANVIL.get(),
                SmithingAnvilBlockEntity::serverTick);
    }

    @Override
    protected void attack(BlockState state, Level level, BlockPos pos, Player player) {
        if (level.isClientSide()) return;

        ItemStack heldStack = player.getMainHandItem();
        if (!(heldStack.getItem() instanceof SmithingHammerItem)) return;
        if (!(level instanceof ServerLevel serverLevel)) return;

        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof SmithingAnvilBlockEntity anvil)) return;
        if (anvil.isAwaitingPickup()) return;

        SmithingAnvilBlockEntity.HitResult result = anvil.hitWithHammer(serverLevel);
        switch (result) {
            case PROGRESSED -> {
                playHitSound(level, pos);
                spawnHitParticles(level, pos, state.getValue(FACING));
                damageHammer(heldStack, player);
            }
            case COMPLETED -> {
                playFinishSound(level, pos);
                spawnHitParticles(level, pos, state.getValue(FACING));
                damageHammer(heldStack, player);
            }
            case MISS -> {
                
                level.playSound(null, pos, SoundEvents.ANVIL_LAND, SoundSource.BLOCKS, 0.5F, 0.8F);
            }
            case NOTHING_TO_WORK -> {  }
        }
    }

    private void damageHammer(ItemStack hammer, Player player) {
        if (!hammer.isDamageableItem()) return;
        hammer.setDamageValue(hammer.getDamageValue() + 1);
        if (hammer.getDamageValue() >= hammer.getMaxDamage()) {
            hammer.shrink(1);
            player.level().playSound(null, player.blockPosition(),
                    SoundEvents.ITEM_BREAK.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }

    private void playHitSound(Level level, BlockPos pos) {
        level.playSound(null, pos, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    private void playFinishSound(Level level, BlockPos pos) {
        level.playSound(null, pos, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1.0F, 1.4F);
    }

    private void spawnHitParticles(Level level, BlockPos pos, Direction facing) {
        if (!(level instanceof ServerLevel serverLevel)) return;

        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.65;
        double z = pos.getZ() + 0.5;

        Direction.Axis axis = facing.getAxis();
        double dx = axis == Direction.Axis.X ? facing.getStepX() * 0.3 : 0;
        double dz = axis == Direction.Axis.Z ? facing.getStepZ() * 0.3 : 0;

        serverLevel.sendParticles(ParticleTypes.CRIT, x + dx, y, z + dz, 6, 0.15, 0.1, 0.15, 0.0);
    }
}