package     net.got.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GhostskinBlock extends Block implements BonemealableBlock {
    public static final MapCodec<GhostskinBlock> CODEC = simpleCodec(GhostskinBlock::new);
    private static final int SIDE_PADDING = 1;
    private static final VoxelShape TIP_SHAPE = Block.box(1.0, 2.0, 1.0, 15.0, 16.0, 15.0);
    private static final VoxelShape BASE_SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);
    public static final BooleanProperty TIP = BlockStateProperties.TIP;

    @Override
    public MapCodec<GhostskinBlock> codec() {
        return CODEC;
    }

    public GhostskinBlock(BlockBehaviour.Properties p_379903_) {
        super(p_379903_);
        this.registerDefaultState(this.stateDefinition.any().setValue(TIP, Boolean.valueOf(true)));
    }

    @Override
    protected VoxelShape getShape(BlockState p_379697_, BlockGetter p_380282_, BlockPos p_379821_, CollisionContext p_379644_) {
        return p_379697_.getValue(TIP) ? TIP_SHAPE : BASE_SHAPE;
    }

    @Override
    public void animateTick(BlockState p_379410_, Level p_379865_, BlockPos p_379365_, RandomSource p_380130_) {
        if (p_380130_.nextInt(500) == 0) {
            BlockState blockstate = p_379865_.getBlockState(p_379365_.above());
            if (blockstate.is(BlockTags.PALE_OAK_LOGS) || blockstate.is(Blocks.PALE_OAK_LEAVES)) {
                p_379865_.playLocalSound(
                        (double)p_379365_.getX(),
                        (double)p_379365_.getY(),
                        (double)p_379365_.getZ(),
                        SoundEvents.PALE_HANGING_MOSS_IDLE,
                        SoundSource.BLOCKS,
                        1.0F,
                        1.0F,
                        false
                );
            }
        }
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState p_380235_) {
        return true;
    }

    @Override
    protected boolean canSurvive(BlockState p_380096_, LevelReader p_379969_, BlockPos p_380283_) {
        return this.canStayAtPosition(p_379969_, p_380283_);
    }

    private boolean canStayAtPosition(BlockGetter level, BlockPos pos) {
        BlockPos blockpos = pos.relative(Direction.UP);
        BlockState blockstate = level.getBlockState(blockpos);
        return MultifaceBlock.canAttachTo(level, Direction.UP, blockpos, blockstate) || blockstate.is(this);
    }

    @Override
    protected BlockState updateShape(
            BlockState p_380182_,
            LevelReader p_380219_,
            ScheduledTickAccess p_380011_,
            BlockPos p_380024_,
            Direction p_380101_,
            BlockPos p_380258_,
            BlockState p_379654_,
            RandomSource p_379547_
    ) {
        if (!this.canStayAtPosition(p_380219_, p_380024_)) {
            p_380011_.scheduleTick(p_380024_, this, 1);
        }

        return p_380182_.setValue(TIP, Boolean.valueOf(!p_380219_.getBlockState(p_380024_.below()).is(this)));
    }

    @Override
    protected void tick(BlockState p_381085_, ServerLevel p_381014_, BlockPos p_381010_, RandomSource p_380962_) {
        if (!this.canStayAtPosition(p_381014_, p_381010_)) {
            p_381014_.destroyBlock(p_381010_, true);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_379416_) {
        p_379416_.add(TIP);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader p_379509_, BlockPos p_379596_, BlockState p_380331_) {
        return this.canGrowInto(p_379509_.getBlockState(this.getTip(p_379509_, p_379596_).below()));
    }

    private boolean canGrowInto(BlockState state) {
        return state.isAir();
    }

    public BlockPos getTip(BlockGetter level, BlockPos pos) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.mutable();

        BlockState blockstate;
        do {
            blockpos$mutableblockpos.move(Direction.DOWN);
            blockstate = level.getBlockState(blockpos$mutableblockpos);
        } while (blockstate.is(this));

        return blockpos$mutableblockpos.relative(Direction.UP).immutable();
    }

    @Override
    public boolean isBonemealSuccess(Level p_380206_, RandomSource p_380151_, BlockPos p_379719_, BlockState p_379567_) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel p_379337_, RandomSource p_379974_, BlockPos p_379496_, BlockState p_379559_) {
        BlockPos blockpos = this.getTip(p_379337_, p_379496_).below();
        if (this.canGrowInto(p_379337_.getBlockState(blockpos))) {
            p_379337_.setBlockAndUpdate(blockpos, p_379559_.setValue(TIP, Boolean.valueOf(true)));
        }
    }
}