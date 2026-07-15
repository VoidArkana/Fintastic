package net.voidarkana.fintastic.common.block.custom;

import com.google.common.collect.ImmutableMap;
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

import java.util.Map;

public class DwarfFroglight extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty NORTH = PipeBlock.NORTH;
    public static final BooleanProperty EAST = PipeBlock.EAST;
    public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
    public static final BooleanProperty WEST = PipeBlock.WEST;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION.entrySet().stream().filter((p_52346_) -> {
        return p_52346_.getKey().getAxis().isHorizontal();
    }).collect(Util.toMap());
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

    public DwarfFroglight(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(TYPE, AttachmentStyle.MIDDLE).setValue(FACING, Direction.Axis.X).setValue(NORTH, Boolean.valueOf(false)).setValue(EAST, Boolean.valueOf(false)).setValue(SOUTH, Boolean.valueOf(false)).setValue(WEST, Boolean.valueOf(false)).setValue(WATERLOGGED, Boolean.valueOf(false)));
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        switch (pState.getValue(TYPE)){
            case TOP:
                if (pState.getValue(FACING) == Direction.Axis.X){
                    if (pState.getValue(NORTH) && pState.getValue(SOUTH)){
                        return Shapes.or(CENTER_UP, NORTH_UP, SOUTH_UP);
                    }else if (pState.getValue(NORTH)){
                        return Shapes.or(CENTER_UP, NORTH_UP);
                    }else if (pState.getValue(SOUTH)){
                        return Shapes.or(CENTER_UP, SOUTH_UP);
                    }
                }else {
                    if (pState.getValue(EAST) && pState.getValue(WEST)){
                        return Shapes.or(CENTER_UP, EAST_UP, WEST_UP);
                    }else if (pState.getValue(EAST)){
                        return Shapes.or(CENTER_UP, EAST_UP);
                    }else if (pState.getValue(WEST)){
                        return Shapes.or(CENTER_UP, WEST_UP);
                    }
                }
                return CENTER_UP;
            case BOTTOM:
                if (pState.getValue(FACING) == Direction.Axis.X){
                    if (pState.getValue(NORTH) && pState.getValue(SOUTH)){
                        return Shapes.or(CENTER_DOWN, NORTH_DOWN, SOUTH_DOWN);
                    }else if (pState.getValue(NORTH)){
                        return Shapes.or(CENTER_DOWN, NORTH_DOWN);
                    }else if (pState.getValue(SOUTH)){
                        return Shapes.or(CENTER_DOWN, SOUTH_DOWN);
                    }
                }else {
                    if (pState.getValue(EAST) && pState.getValue(WEST)){
                        return Shapes.or(CENTER_DOWN, EAST_DOWN, WEST_DOWN);
                    }else if (pState.getValue(EAST)){
                        return Shapes.or(CENTER_DOWN, EAST_DOWN);
                    }else if (pState.getValue(WEST)){
                        return Shapes.or(CENTER_DOWN, WEST_DOWN);
                    }
                }
                return CENTER_DOWN;
            default:
                if (pState.getValue(FACING) == Direction.Axis.X){
                    if (pState.getValue(NORTH) && pState.getValue(SOUTH)){
                        return Shapes.or(CENTER_MID, NORTH_MID, SOUTH_MID);
                    }else if (pState.getValue(NORTH)){
                        return Shapes.or(CENTER_MID, NORTH_MID);
                    }else if (pState.getValue(SOUTH)){
                        return Shapes.or(CENTER_MID, SOUTH_MID);
                    }
                }else {
                    if (pState.getValue(EAST) && pState.getValue(WEST)){
                        return Shapes.or(CENTER_MID, EAST_MID, WEST_MID);
                    }else if (pState.getValue(EAST)){
                        return Shapes.or(CENTER_MID, EAST_MID);
                    }else if (pState.getValue(WEST)){
                        return Shapes.or(CENTER_MID, WEST_MID);
                    }
                }
                return CENTER_MID;
        }
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        for(Direction direction : pContext.getNearestLookingDirections()) {
            BlockState state;
            if (direction.getAxis() == Direction.Axis.Y) {
                state = this.defaultBlockState().setValue(TYPE, direction == Direction.UP ? AttachmentStyle.TOP : AttachmentStyle.BOTTOM)
                        .setValue(FACING, pContext.getHorizontalDirection().getCounterClockWise().getAxis());
            } else {
                state = this.defaultBlockState().setValue(TYPE, AttachmentStyle.MIDDLE).setValue(FACING, pContext.getHorizontalDirection().getCounterClockWise().getAxis());
            }
            BlockState otherBlock = pContext.getLevel().getBlockState(pContext.getClickedPos().offset(direction.getNormal()));

            if (this.isSameFroglight(state, otherBlock)){
                if (state.getValue(FACING) == otherBlock.getValue(FACING)){
                    state.setValue(TYPE, otherBlock.getValue(TYPE));
                }
            }

            BlockGetter blockgetter = pContext.getLevel();
            BlockPos blockpos = pContext.getClickedPos();
            FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
            BlockPos blockpos1 = blockpos.north();
            BlockPos blockpos2 = blockpos.east();
            BlockPos blockpos3 = blockpos.south();
            BlockPos blockpos4 = blockpos.west();
            BlockState blockstate = blockgetter.getBlockState(blockpos1);
            BlockState blockstate1 = blockgetter.getBlockState(blockpos2);
            BlockState blockstate2 = blockgetter.getBlockState(blockpos3);
            BlockState blockstate3 = blockgetter.getBlockState(blockpos4);
            return state
                    .setValue(NORTH, Boolean.valueOf(this.connectsTo(state, blockstate, blockstate.isFaceSturdy(blockgetter, blockpos1, Direction.SOUTH))))
                    .setValue(EAST, Boolean.valueOf(this.connectsTo(state, blockstate1, blockstate1.isFaceSturdy(blockgetter, blockpos2, Direction.WEST))))
                    .setValue(SOUTH, Boolean.valueOf(this.connectsTo(state, blockstate2, blockstate2.isFaceSturdy(blockgetter, blockpos3, Direction.NORTH))))
                    .setValue(WEST, Boolean.valueOf(this.connectsTo(state, blockstate3, blockstate3.isFaceSturdy(blockgetter, blockpos4, Direction.EAST))))
                    .setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
        }
        return null;
    }

    public boolean connectsTo(BlockState thisState, BlockState otherState, boolean pIsSideSolid) {
        boolean flag = this.isSameFroglight(thisState, otherState);
        return (!isExceptionForConnection(otherState) && pIsSideSolid && thisState.getValue(TYPE) == AttachmentStyle.MIDDLE) || flag;
    }

    private boolean isSameFroglight(BlockState otherState, BlockState pState) {
        return ((pState.is(FintyBlocks.GREEN_DWARF_FROGLIGHT.get()) && otherState.is(FintyBlocks.GREEN_DWARF_FROGLIGHT.get())) ||
                (pState.is(FintyBlocks.RED_DWARF_FROGLIGHT.get()) && otherState.is(FintyBlocks.RED_DWARF_FROGLIGHT.get())) ||
                (pState.is(FintyBlocks.YELLOW_DWARF_FROGLIGHT.get()) && otherState.is(FintyBlocks.YELLOW_DWARF_FROGLIGHT.get())) ||
                (pState.is(FintyBlocks.PINK_DWARF_FROGLIGHT.get()) && otherState.is(FintyBlocks.PINK_DWARF_FROGLIGHT.get()))) &&
                pState.getValue(FACING) == otherState.getValue(FACING) && pState.getValue(TYPE) == otherState.getValue(TYPE);
    }

    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(TYPE, NORTH, EAST, WEST, SOUTH, WATERLOGGED, FACING);
    }

    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    public boolean propagatesSkylightDown(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return !pState.getValue(WATERLOGGED);
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return pFacing.getAxis().getPlane() == Direction.Plane.HORIZONTAL ? pState.setValue(PROPERTY_BY_DIRECTION.get(pFacing),
                Boolean.valueOf(this.connectsTo(pState, pFacingState, pFacingState.isFaceSturdy(pLevel, pFacingPos, pFacing.getOpposite())))) :
                super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    public BlockState rotate(BlockState pState, Rotation pRot) {
        return switch (pRot) {
            case CLOCKWISE_180 ->
                    pState.setValue(NORTH, pState.getValue(SOUTH)).setValue(EAST, pState.getValue(WEST)).setValue(SOUTH, pState.getValue(NORTH)).setValue(WEST, pState.getValue(EAST));
            case COUNTERCLOCKWISE_90 ->
                    pState.setValue(FACING, pState.getValue(FACING) == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X).setValue(NORTH, pState.getValue(EAST)).setValue(EAST, pState.getValue(SOUTH)).setValue(SOUTH, pState.getValue(WEST)).setValue(WEST, pState.getValue(NORTH));
            case CLOCKWISE_90 ->
                    pState.setValue(FACING, pState.getValue(FACING) == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X).setValue(NORTH, pState.getValue(WEST)).setValue(EAST, pState.getValue(NORTH)).setValue(SOUTH, pState.getValue(EAST)).setValue(WEST, pState.getValue(SOUTH));
            default -> pState;
        };
    }

    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return switch (pMirror) {
            case LEFT_RIGHT -> pState.setValue(NORTH, pState.getValue(SOUTH)).setValue(SOUTH, pState.getValue(NORTH));
            case FRONT_BACK -> pState.setValue(EAST, pState.getValue(WEST)).setValue(WEST, pState.getValue(EAST));
            default -> super.mirror(pState, pMirror);
        };
    }

    public enum AttachmentStyle implements StringRepresentable {
        TOP("up"),
        BOTTOM("down"),
        MIDDLE("mid");

        private final String name;

        AttachmentStyle(String pName) {
            this.name = pName;
        }

        public String toString() {
            return this.name;
        }

        public String getSerializedName() {
            return this.name;
        }
    }
}
