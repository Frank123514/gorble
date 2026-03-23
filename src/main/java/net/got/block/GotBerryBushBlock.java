package net.got.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

import java.util.function.Supplier;

public class GotBerryBushBlock extends BushBlock implements BonemealableBlock {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    public static final MapCodec<GotBerryBushBlock> CODEC = MapCodec.unit(() -> null);

    @Override
    public MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    private final Supplier<Item> berrySupplier;

    public GotBerryBushBlock(Supplier<Item> berrySupplier, Properties properties) {
        super(properties);
        this.berrySupplier = berrySupplier;
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 3));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(AGE, 3);
    }

    // Only tick when regrowing after harvest (age < 3)
    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) < 3;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int age = state.getValue(AGE);
        if (age < 3 && random.nextInt(5) == 0) {
            level.setBlock(pos, state.setValue(AGE, age + 1), 2);
        }
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level,
                                          BlockPos pos, Player player, InteractionHand hand,
                                          BlockHitResult hit) {
        int age = state.getValue(AGE);
        if (age == 3) {
            int count = 1 + level.random.nextInt(2);
            popResource(level, pos, new ItemStack(berrySupplier.get(), count));
            level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES,
                    SoundSource.BLOCKS, 1.0f, 0.8f + level.random.nextFloat() * 0.4f);
            level.setBlock(pos, state.setValue(AGE, 1), 2);
            return InteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hit);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return state.getValue(AGE) < 3;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        int age = state.getValue(AGE);
        if (age < 3) {
            level.setBlock(pos, state.setValue(AGE, age + 1), 2);
        }
    }
}
