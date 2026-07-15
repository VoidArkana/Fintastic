package net.voidarkana.fintastic.common.block.custom;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.voidarkana.fintastic.common.block.FintyBlocks;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

public class DuckweedBlock extends Block implements BonemealableBlock {
    protected static final VoxelShape AABB = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 1.5D, 15.0D);

    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;

    public static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = ImmutableMap.copyOf(Util.make(Maps.newEnumMap(Direction.class), (map) -> {
        map.put(Direction.NORTH, NORTH);
        map.put(Direction.EAST, EAST);
        map.put(Direction.SOUTH, SOUTH);
        map.put(Direction.WEST, WEST);
    }));

    public static final IntegerProperty AMOUNT = IntegerProperty.create("duckweed_amount", 1, 5);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public DuckweedBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, Boolean.FALSE)
                .setValue(EAST, Boolean.FALSE).setValue(SOUTH, Boolean.FALSE)
                .setValue(WEST, Boolean.FALSE)
                .setValue(FACING, Direction.NORTH).setValue(AMOUNT, 1));
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {

        BlockState blockstate = level.getBlockState(pos);

        if (stack.is(FintyBlocks.DUCKWEED.get().asItem()) && blockstate.getValue(AMOUNT) < 5) {
            this.usePlayerItem(player, stack);
            level.playSound(null, pos, SoundEvents.LILY_PAD_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            int prev = blockstate.getValue(AMOUNT);
            level.setBlock(pos, state.setValue(AMOUNT, prev + 1), 2);
            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    protected void usePlayerItem(Player player, ItemStack stack) {
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, WEST, SOUTH, AMOUNT, FACING);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockGetter blockgetter = context.getLevel();

        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = context.getLevel().getBlockState(blockpos);

        BlockPos blockpos1 = blockpos.north();
        BlockPos blockpos2 = blockpos.east();
        BlockPos blockpos3 = blockpos.south();
        BlockPos blockpos4 = blockpos.west();
        BlockState blockstate1 = blockgetter.getBlockState(blockpos1);
        BlockState blockstate2 = blockgetter.getBlockState(blockpos2);
        BlockState blockstate3 = blockgetter.getBlockState(blockpos3);
        BlockState blockstate4 = blockgetter.getBlockState(blockpos4);

        return blockstate.is(this) ?
                Objects.requireNonNull(super.getStateForPlacement(context))
                    .setValue(NORTH, connectsTo(blockstate1, blockstate))
                    .setValue(EAST, connectsTo(blockstate2, blockstate))
                    .setValue(SOUTH, connectsTo(blockstate3, blockstate))
                    .setValue(WEST, connectsTo(blockstate4, blockstate))
                :
                Objects.requireNonNull(super.getStateForPlacement(context))
                    .setValue(NORTH, connectsTo(blockstate1, blockstate))
                    .setValue(EAST, connectsTo(blockstate2, blockstate))
                    .setValue(SOUTH, connectsTo(blockstate3, blockstate))
                    .setValue(WEST, connectsTo(blockstate4, blockstate))
                    .setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    public boolean propagatesSkylightDown(@NotNull BlockState state, @NotNull BlockGetter reader, @NotNull BlockPos pos) {
        return true;
    }

    public @NotNull BlockState updateShape(BlockState state, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {

        if (!state.canSurvive(level, currentPos)) {
            level.scheduleTick(currentPos, this, 1);
            return Blocks.AIR.defaultBlockState();
        } else if (facing.getAxis().getPlane() == Direction.Plane.HORIZONTAL){
            boolean flag = connectsTo(facingState, state);
            return state.setValue(PROPERTY_BY_DIRECTION.get(facing), flag);
        }
        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return AABB;
    }

    public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, BlockPos pos) {
        return mayPlaceOn(level, pos.below());
    }

    private static boolean mayPlaceOn(BlockGetter level, BlockPos pos) {
        FluidState fluidstate = level.getFluidState(pos);
        FluidState fluidstate1 = level.getFluidState(pos.above());
        return fluidstate.getType() == Fluids.WATER && fluidstate1.getType() == Fluids.EMPTY;
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, BlockState state) {
        int i = state.getValue(AMOUNT);
        if (i < 5) {
            level.setBlock(pos, state.setValue(AMOUNT, i + 1), 2);
        } else {
            popResource(level, pos, new ItemStack(this));
        }

    }

    public boolean isPathfindable(@NotNull BlockState state, @NotNull PathComputationType type) {
        return true;
    }

    public static boolean connectsTo(BlockState otherState, BlockState thisState) {
        if (otherState.is(FintyBlocks.DUCKWEED.get()) && thisState.is(FintyBlocks.DUCKWEED.get())){
            return otherState.getValue(AMOUNT)==5 && thisState.getValue(AMOUNT)==5;
        }else {
            return false;
        }
    }

    public void tick(BlockState state, @NotNull ServerLevel level, @NotNull BlockPos blockpos, @NotNull RandomSource random) {
        if (state.is(this))
            if (state.getValue(AMOUNT) == 5){

                BlockPos blockpos1 = blockpos.north();
                BlockPos blockpos2 = blockpos.east();
                BlockPos blockpos3 = blockpos.south();
                BlockPos blockpos4 = blockpos.west();
                BlockState blockstate1 = level.getBlockState(blockpos1);
                BlockState blockstate2 = level.getBlockState(blockpos2);
                BlockState blockstate3 = level.getBlockState(blockpos3);
                BlockState blockstate4 = level.getBlockState(blockpos4);

                level.setBlock(blockpos,
                        state.setValue(NORTH, connectsTo(blockstate1, state))
                              .setValue(EAST,  connectsTo(blockstate2, state))
                              .setValue(SOUTH, connectsTo(blockstate3, state))
                              .setValue(WEST,  connectsTo(blockstate4, state)),
                        2);
            }
    }

}
