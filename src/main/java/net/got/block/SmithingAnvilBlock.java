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
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
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

/**
 * SmithingAnvilBlock — the dedicated smithing workstation.
 * <p>
 * Accepts heated (malleable) metal ingots from the Forge's heat-treating mode
 * and lets the player choose a SmithyRecipe to craft them into finished items.
 * Hosts a {@link SmithingAnvilBlockEntity} with the full stonecutter-style
 * recipe-selection interface that previously lived in the Forge.
 */
public class SmithingAnvilBlock extends BaseEntityBlock {

    public static final MapCodec<SmithingAnvilBlock> CODEC = simpleCodec(SmithingAnvilBlock::new);

    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty         LIT    = BlockStateProperties.LIT;

    // Shape built to match the model geometry exactly, in the model's native
    // (FACING = NORTH) orientation:
    //   Base plate:  [2,0,2]  → [14,4,14]
    //   Waist step:  [4,4,3]  → [12,5,13]
    //   Neck:        [6,5,4]  → [10,10,12]
    //   Anvil head:  [3,10,0] → [13,16,16]
    // The horns that stick out beyond z=0 and z=16 are decorative geometry
    // outside the block bounds and don't need collision coverage.
    // This must be rotated to match FACING — see SHAPES_BY_FACING below.
    private static final VoxelShape SHAPE_NORTH = Shapes.or(
            Block.box(2, 0, 2,  14,  4, 14),   // base plate
            Block.box(4, 4, 3,  12,  5, 13),   // waist step
            Block.box(6, 5, 4,  10, 10, 12),   // neck
            Block.box(3,10, 0,  13, 16, 16)    // anvil head (top working surface)
    );

    /** SHAPE_NORTH pre-rotated for every horizontal facing, keyed by FACING. */
    private static final Map<Direction, VoxelShape> SHAPES_BY_FACING =
            buildRotatedShapes();

    private static Map<Direction, VoxelShape> buildRotatedShapes() {
        Map<Direction, VoxelShape> map = new EnumMap<>(Direction.class);
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            map.put(dir, rotateShape(SHAPE_NORTH, dir));
        }
        return map;
    }

    /**
     * Rotates a VoxelShape (built assuming FACING = NORTH) around the block's
     * vertical (Y) axis so it matches the given facing. Rotation follows the
     * same convention as block model "y" rotation in blockstates: looking
     * down the Y axis, NORTH -> EAST -> SOUTH -> WEST is a clockwise turn.
     */
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
            // Rotate 90 deg clockwise (viewed from above) around the block center (0.5, y, 0.5):
            // (x, z) -> (1 - z, x)
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
    public void onRemove(BlockState state, Level level, BlockPos pos,
                         BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof SmithingAnvilBlockEntity anvil) {
                Containers.dropContents(level, pos, anvil);
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                               Player player, BlockHitResult hitResult) {
        if (level.isClientSide) return InteractionResult.SUCCESS;

        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof SmithingAnvilBlockEntity anvil) {
            if (anvil.isAwaitingPickup()) {
                // Right-click collects the finished item and unlocks the anvil
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
        if (level.isClientSide) return null;
        return createTickerHelper(type,
                net.got.init.GotModBlockEntities.SMITHING_ANVIL.get(),
                SmithingAnvilBlockEntity::serverTick);
    }

    @Override
    protected void attack(BlockState state, Level level, BlockPos pos, Player player) {
        if (level.isClientSide) return;

        ItemStack heldStack = player.getMainHandItem();
        if (!(heldStack.getItem() instanceof SmithingHammerItem)) return;
        if (!(level instanceof ServerLevel serverLevel)) return;

        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof SmithingAnvilBlockEntity anvil)) return;
        if (anvil.isAwaitingPickup()) return; // must collect first

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
                // Play a dull clank — progress reset
                level.playSound(null, pos, SoundEvents.ANVIL_LAND, SoundSource.BLOCKS, 0.5F, 0.8F);
            }
            case NOTHING_TO_WORK -> { /* no ingot, no recipe selected, or output full — silently ignore */ }
        }
    }

    /** Wears the hammer down by one point of durability, breaking it if it runs out. */
    private void damageHammer(ItemStack hammer, Player player) {
        if (!hammer.isDamageableItem()) return;
        hammer.setDamageValue(hammer.getDamageValue() + 1);
        if (hammer.getDamageValue() >= hammer.getMaxDamage()) {
            hammer.shrink(1);
            player.level().playSound(null, player.blockPosition(),
                    SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
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