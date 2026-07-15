package net.voidarkana.fintastic.common.block.custom;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

public class AquariumGlassPane extends Block implements SimpleWaterloggedBlock {

    protected static final VoxelShape NORTH_SHAPE = Block.box(0, 0, 0, 16, 16, 2);
    protected static final VoxelShape SOUTH_SHAPE = Block.box(0, 0, 14, 16, 16, 16);
    protected static final VoxelShape EAST_SHAPE = Block.box(14, 0, 0, 16, 16, 16);
    protected static final VoxelShape WEST_SHAPE = Block.box(0, 0, 0, 2, 16, 16);

    public static final BooleanProperty NORTH = PipeBlock.NORTH;
    public static final BooleanProperty EAST = PipeBlock.EAST;
    public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
    public static final BooleanProperty WEST = PipeBlock.WEST;
    public static final BooleanProperty UP = PipeBlock.UP;
    public static final BooleanProperty DOWN = PipeBlock.DOWN;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = ImmutableMap.copyOf(Util.make(Maps.newEnumMap(Direction.class), (map) -> {
        map.put(Direction.NORTH, NORTH);
        map.put(Direction.EAST, EAST);
        map.put(Direction.SOUTH, SOUTH);
        map.put(Direction.WEST, WEST);
        map.put(Direction.UP, UP);
        map.put(Direction.DOWN, DOWN);
    }));

    public AquariumGlassPane(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH, Boolean.FALSE)
                .setValue(EAST, Boolean.FALSE)
                .setValue(SOUTH, Boolean.FALSE)
                .setValue(WEST, Boolean.FALSE)
                .setValue(UP, Boolean.FALSE)
                .setValue(DOWN, Boolean.FALSE)
                .setValue(WATERLOGGED, Boolean.FALSE)
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        if (state.getValue(FACING) == Direction.NORTH){
            return NORTH_SHAPE;

        }else if (state.getValue(FACING) == Direction.WEST){
            return WEST_SHAPE;

        }else if (state.getValue(FACING) == Direction.SOUTH){
            return SOUTH_SHAPE;

        }else{
            return EAST_SHAPE;
        }
    }

    public float getShadeBrightness(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return 1.0F;
    }

    public boolean propagatesSkylightDown(@NotNull BlockState state, @NotNull BlockGetter reader, @NotNull BlockPos pos) {
        return true;
    }

    public @NotNull BlockState rotate(@NotNull BlockState state, Rotation rotation) {
        return switch (rotation) {
            case CLOCKWISE_180 ->
                    state.setValue(FACING, rotation.rotate(state.getValue(FACING))).setValue(NORTH, state.getValue(SOUTH)).setValue(EAST, state.getValue(WEST)).setValue(SOUTH, state.getValue(NORTH)).setValue(WEST, state.getValue(EAST));
            case COUNTERCLOCKWISE_90 ->
                    state.setValue(FACING, rotation.rotate(state.getValue(FACING))).setValue(NORTH, state.getValue(EAST)).setValue(EAST, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(WEST)).setValue(WEST, state.getValue(NORTH));
            case CLOCKWISE_90 ->
                    state.setValue(FACING, rotation.rotate(state.getValue(FACING))).setValue(NORTH, state.getValue(WEST)).setValue(EAST, state.getValue(NORTH)).setValue(SOUTH, state.getValue(EAST)).setValue(WEST, state.getValue(SOUTH));
            default -> state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
        };
    }

    public @NotNull BlockState mirror(@NotNull BlockState state, Mirror mirror) {
        return switch (mirror) {
            case LEFT_RIGHT -> state.setValue(FACING, mirror.mirror(state.getValue(FACING)))
                    .setValue(NORTH, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(NORTH));
            case FRONT_BACK -> state.setValue(FACING, mirror.mirror(state.getValue(FACING)))
                    .setValue(EAST, state.getValue(WEST)).setValue(WEST, state.getValue(EAST));
            default -> super.mirror(state, mirror);
        };
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockGetter blockgetter = context.getLevel();
        BlockPos blockpos = context.getClickedPos();

        BlockPos blockpos1 = blockpos.north();
        BlockPos blockpos2 = blockpos.east();
        BlockPos blockpos3 = blockpos.south();
        BlockPos blockpos4 = blockpos.west();
        BlockPos blockpos5 = blockpos.above();
        BlockPos blockpos6 = blockpos.below();

        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());

        BlockState blockstate = blockgetter.getBlockState(blockpos1);
        BlockState blockstate1 = blockgetter.getBlockState(blockpos2);
        BlockState blockstate2 = blockgetter.getBlockState(blockpos3);
        BlockState blockstate3 = blockgetter.getBlockState(blockpos4);
        BlockState blockstate4 = blockgetter.getBlockState(blockpos5);
        BlockState blockstate5 = blockgetter.getBlockState(blockpos6);

        Direction direction = context.getHorizontalDirection().getOpposite();

        return Objects.requireNonNull(super.getStateForPlacement(context))
                .setValue(NORTH, this.connectsTo(blockstate, direction))
                .setValue(EAST, this.connectsTo(blockstate1, direction))
                .setValue(SOUTH, this.connectsTo(blockstate2, direction))
                .setValue(WEST, this.connectsTo(blockstate3, direction))
                .setValue(UP, this.connectsTo(blockstate4, direction))
                .setValue(DOWN, this.connectsTo(blockstate5, direction))
                .setValue(FACING, direction)
                .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    private boolean connectsTo(BlockState state, Direction direction) {
        if (state.is(this)){
            return state.getValue(FACING) == direction;
        }else {
            return false;
        }
    }

    public @NotNull BlockState updateShape(BlockState state, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        if (!state.canSurvive(level, currentPos)) {
            level.scheduleTick(currentPos, this, 1);
            return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        } else {
            boolean flag = facingState.is(this) && facingState.getValue(FACING) == state.getValue(FACING);
            return state.setValue(PROPERTY_BY_DIRECTION.get(facing), flag);
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN, FACING, WATERLOGGED);
    }

    public @NotNull VoxelShape getVisualShape(@NotNull BlockState state, @NotNull BlockGetter reader, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return Shapes.empty();
    }

    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public Direction getDirection(BlockState state) {
        return state.getValue(FACING);
    }

    @Override
    public boolean isPathfindable(@NotNull BlockState state, @NotNull PathComputationType type) {
        return false;
    }
}
