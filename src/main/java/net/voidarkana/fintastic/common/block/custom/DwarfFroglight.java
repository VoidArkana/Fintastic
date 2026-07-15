package net.voidarkana.fintastic.common.block.custom;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.*;
import net.voidarkana.fintastic.common.block.FintyBlocks;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class DwarfFroglight extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty NORTH = PipeBlock.NORTH;
    public static final BooleanProperty EAST = PipeBlock.EAST;
    public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
    public static final BooleanProperty WEST = PipeBlock.WEST;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION.entrySet().stream().filter((entry) -> entry.getKey().getAxis().isHorizontal()).collect(Util.toMap());
    public static final EnumProperty<AttachmentStyle> TYPE = EnumProperty.create("type", AttachmentStyle.class);
    public static final EnumProperty<Direction.Axis> FACING = BlockStateProperties.HORIZONTAL_AXIS;

    public static final VoxelShape CENTER_DOWN = Block.box(4, 0, 4, 12, 8, 12);
    public static final VoxelShape CENTER_MID =  Block.box(4, 4, 4, 12, 12,12);
    public static final VoxelShape CENTER_UP =   Block.box(4, 8, 4, 12, 16,12);
    public static final VoxelShape NORTH_UP =    Block.box(4, 8, 0, 12, 16, 4)  ;
    public static final VoxelShape SOUTH_UP =    Block.box(4, 8, 12, 12, 16, 16);
    public static final VoxelShape EAST_UP =     Block.box(12, 8, 4, 16, 16, 12);
    public static final VoxelShape WEST_UP =     Block.box(0, 8, 4, 4, 16, 12)  ;
    public static final VoxelShape NORTH_MID =   Block.box(4, 4, 0, 12, 12, 4)  ;
    public static final VoxelShape SOUTH_MID =   Block.box(4, 4, 12, 12, 12, 16);
    public static final VoxelShape EAST_MID =    Block.box(12, 4, 4, 16, 12, 12);
    public static final VoxelShape WEST_MID =    Block.box(0, 4, 4, 4, 12, 12)  ;
    public static final VoxelShape NORTH_DOWN =  Block.box(4, 0, 0, 12, 8, 4)  ;
    public static final VoxelShape SOUTH_DOWN =  Block.box(4, 0, 12, 12, 8, 16);
    public static final VoxelShape EAST_DOWN =   Block.box(12, 0, 4, 16, 8, 12);
    public static final VoxelShape WEST_DOWN =   Block.box(0, 0, 4, 4, 8, 12)  ;

    public DwarfFroglight(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(TYPE, AttachmentStyle.MIDDLE).setValue(FACING, Direction.Axis.X).setValue(NORTH, Boolean.FALSE).setValue(EAST, Boolean.FALSE).setValue(SOUTH, Boolean.FALSE).setValue(WEST, Boolean.FALSE).setValue(WATERLOGGED, Boolean.FALSE));
    }

    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return switch (state.getValue(TYPE)) {
            case TOP -> {
                if (state.getValue(FACING) == Direction.Axis.X) {
                    if (state.getValue(NORTH) && state.getValue(SOUTH)) {
                        yield Shapes.or(CENTER_UP, NORTH_UP, SOUTH_UP);
                    } else if (state.getValue(NORTH)) {
                        yield Shapes.or(CENTER_UP, NORTH_UP);
                    } else if (state.getValue(SOUTH)) {
                        yield Shapes.or(CENTER_UP, SOUTH_UP);
                    }
                } else {
                    if (state.getValue(EAST) && state.getValue(WEST)) {
                        yield Shapes.or(CENTER_UP, EAST_UP, WEST_UP);
                    } else if (state.getValue(EAST)) {
                        yield Shapes.or(CENTER_UP, EAST_UP);
                    } else if (state.getValue(WEST)) {
                        yield Shapes.or(CENTER_UP, WEST_UP);
                    }
                }
                yield CENTER_UP;
            }
            case BOTTOM -> {
                if (state.getValue(FACING) == Direction.Axis.X) {
                    if (state.getValue(NORTH) && state.getValue(SOUTH)) {
                        yield Shapes.or(CENTER_DOWN, NORTH_DOWN, SOUTH_DOWN);
                    } else if (state.getValue(NORTH)) {
                        yield Shapes.or(CENTER_DOWN, NORTH_DOWN);
                    } else if (state.getValue(SOUTH)) {
                        yield Shapes.or(CENTER_DOWN, SOUTH_DOWN);
                    }
                } else {
                    if (state.getValue(EAST) && state.getValue(WEST)) {
                        yield Shapes.or(CENTER_DOWN, EAST_DOWN, WEST_DOWN);
                    } else if (state.getValue(EAST)) {
                        yield Shapes.or(CENTER_DOWN, EAST_DOWN);
                    } else if (state.getValue(WEST)) {
                        yield Shapes.or(CENTER_DOWN, WEST_DOWN);
                    }
                }
                yield CENTER_DOWN;
            }
            default -> {
                if (state.getValue(FACING) == Direction.Axis.X) {
                    if (state.getValue(NORTH) && state.getValue(SOUTH)) {
                        yield Shapes.or(CENTER_MID, NORTH_MID, SOUTH_MID);
                    } else if (state.getValue(NORTH)) {
                        yield Shapes.or(CENTER_MID, NORTH_MID);
                    } else if (state.getValue(SOUTH)) {
                        yield Shapes.or(CENTER_MID, SOUTH_MID);
                    }
                } else {
                    if (state.getValue(EAST) && state.getValue(WEST)) {
                        yield Shapes.or(CENTER_MID, EAST_MID, WEST_MID);
                    } else if (state.getValue(EAST)) {
                        yield Shapes.or(CENTER_MID, EAST_MID);
                    } else if (state.getValue(WEST)) {
                        yield Shapes.or(CENTER_MID, WEST_MID);
                    }
                }
                yield CENTER_MID;
            }
        };
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        for(Direction direction : context.getNearestLookingDirections()) {
            BlockState state;
            if (direction.getAxis() == Direction.Axis.Y) {
                state = this.defaultBlockState().setValue(TYPE, direction == Direction.UP ? AttachmentStyle.TOP : AttachmentStyle.BOTTOM)
                        .setValue(FACING, context.getHorizontalDirection().getCounterClockWise().getAxis());
            } else {
                state = this.defaultBlockState().setValue(TYPE, AttachmentStyle.MIDDLE).setValue(FACING, context.getHorizontalDirection().getCounterClockWise().getAxis());
            }
            BlockState otherBlock = context.getLevel().getBlockState(context.getClickedPos().offset(direction.getNormal()));

            if (this.isSameFroglight(state, otherBlock)){
                if (state.getValue(FACING) == otherBlock.getValue(FACING)){
                    state.setValue(TYPE, otherBlock.getValue(TYPE));
                }
            }

            BlockGetter blockgetter = context.getLevel();
            BlockPos blockpos = context.getClickedPos();
            FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
            BlockPos blockpos1 = blockpos.north();
            BlockPos blockpos2 = blockpos.east();
            BlockPos blockpos3 = blockpos.south();
            BlockPos blockpos4 = blockpos.west();
            BlockState blockstate = blockgetter.getBlockState(blockpos1);
            BlockState blockstate1 = blockgetter.getBlockState(blockpos2);
            BlockState blockstate2 = blockgetter.getBlockState(blockpos3);
            BlockState blockstate3 = blockgetter.getBlockState(blockpos4);
            return state
                    .setValue(NORTH, this.connectsTo(state, blockstate, blockstate.isFaceSturdy(blockgetter, blockpos1, Direction.SOUTH)))
                    .setValue(EAST, this.connectsTo(state, blockstate1, blockstate1.isFaceSturdy(blockgetter, blockpos2, Direction.WEST)))
                    .setValue(SOUTH, this.connectsTo(state, blockstate2, blockstate2.isFaceSturdy(blockgetter, blockpos3, Direction.NORTH)))
                    .setValue(WEST, this.connectsTo(state, blockstate3, blockstate3.isFaceSturdy(blockgetter, blockpos4, Direction.EAST)))
                    .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
        }
        return null;
    }

    public boolean connectsTo(BlockState thisState, BlockState otherState, boolean isSideSolid) {
        boolean flag = this.isSameFroglight(thisState, otherState);
        return (!isExceptionForConnection(otherState) && isSideSolid && thisState.getValue(TYPE) == AttachmentStyle.MIDDLE) || flag;
    }

    private boolean isSameFroglight(BlockState otherState, BlockState state) {
        return ((state.is(FintyBlocks.GREEN_DWARF_FROGLIGHT.get()) && otherState.is(FintyBlocks.GREEN_DWARF_FROGLIGHT.get())) ||
                (state.is(FintyBlocks.RED_DWARF_FROGLIGHT.get()) && otherState.is(FintyBlocks.RED_DWARF_FROGLIGHT.get())) ||
                (state.is(FintyBlocks.YELLOW_DWARF_FROGLIGHT.get()) && otherState.is(FintyBlocks.YELLOW_DWARF_FROGLIGHT.get())) ||
                (state.is(FintyBlocks.PINK_DWARF_FROGLIGHT.get()) && otherState.is(FintyBlocks.PINK_DWARF_FROGLIGHT.get()))) &&
                state.getValue(FACING) == otherState.getValue(FACING) && state.getValue(TYPE) == otherState.getValue(TYPE);
    }

    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE, NORTH, EAST, WEST, SOUTH, WATERLOGGED, FACING);
    }

    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public boolean propagatesSkylightDown(BlockState state, @NotNull BlockGetter reader, @NotNull BlockPos pos) {
        return !state.getValue(WATERLOGGED);
    }

    public @NotNull BlockState updateShape(BlockState state, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return facing.getAxis().getPlane() == Direction.Plane.HORIZONTAL ? state.setValue(PROPERTY_BY_DIRECTION.get(facing),
                this.connectsTo(state, facingState, facingState.isFaceSturdy(level, facingPos, facing.getOpposite()))) :
                super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    public @NotNull BlockState rotate(@NotNull BlockState state, Rotation rot) {
        return switch (rot) {
            case CLOCKWISE_180 ->
                    state.setValue(NORTH, state.getValue(SOUTH)).setValue(EAST, state.getValue(WEST)).setValue(SOUTH, state.getValue(NORTH)).setValue(WEST, state.getValue(EAST));
            case COUNTERCLOCKWISE_90 ->
                    state.setValue(FACING, state.getValue(FACING) == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X).setValue(NORTH, state.getValue(EAST)).setValue(EAST, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(WEST)).setValue(WEST, state.getValue(NORTH));
            case CLOCKWISE_90 ->
                    state.setValue(FACING, state.getValue(FACING) == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X).setValue(NORTH, state.getValue(WEST)).setValue(EAST, state.getValue(NORTH)).setValue(SOUTH, state.getValue(EAST)).setValue(WEST, state.getValue(SOUTH));
            default -> state;
        };
    }

    public @NotNull BlockState mirror(@NotNull BlockState state, Mirror mirror) {
        return switch (mirror) {
            case LEFT_RIGHT -> state.setValue(NORTH, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(NORTH));
            case FRONT_BACK -> state.setValue(EAST, state.getValue(WEST)).setValue(WEST, state.getValue(EAST));
            default -> super.mirror(state, mirror);
        };
    }

    public enum AttachmentStyle implements StringRepresentable {
        TOP("up"),
        BOTTOM("down"),
        MIDDLE("mid");

        private final String name;

        AttachmentStyle(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }

        public @NotNull String getSerializedName() {
            return this.name;
        }
    }
}
