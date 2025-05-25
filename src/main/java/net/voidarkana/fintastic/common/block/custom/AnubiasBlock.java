package net.voidarkana.fintastic.common.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class AnubiasBlock extends BushBlock implements BonemealableBlock, LiquidBlockContainer {

    protected static final VoxelShape FLOORNORTHSOUTH = Block.box(2, 0, 1, 14, 9, 15);
    protected static final VoxelShape FLOOREASTWEST = Block.box(1, 0, 2, 15, 9, 14);
    protected static final VoxelShape SOUTHWALL = Block.box(2, 1, 0, 14, 15, 8);
    protected static final VoxelShape WESTWALL = Block.box(8, 1, 2, 16, 15, 14);
    protected static final VoxelShape NORTHWALL = Block.box(2, 1, 8, 14, 15, 16);
    protected static final VoxelShape EASTWALL = Block.box(0, 1, 2, 8, 15, 14);

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<AttachFace> FACE = BlockStateProperties.ATTACH_FACE;

    public AnubiasBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(FACE, AttachFace.FLOOR));
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction direction = pState.getValue(FACING);
        if ((AttachFace)pState.getValue(FACE) == AttachFace.FLOOR){
            if (pState.getValue(FACING) == Direction.NORTH || pState.getValue(FACING) == Direction.SOUTH){
                return FLOORNORTHSOUTH;
            }else {
                return FLOOREASTWEST;
            }
        }else {
            VoxelShape voxelshape;
            switch (direction) {
                case EAST:
                    voxelshape = EASTWALL;
                    break;
                case WEST:
                    voxelshape = WESTWALL;
                    break;
                case SOUTH:
                    voxelshape = SOUTHWALL;
                    break;
                case NORTH:
                    voxelshape = NORTHWALL;
                    break;
                case UP:
                case DOWN:
                default:
                    throw new IncompatibleClassChangeError();
            }

            return voxelshape;
        }
    }

    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return canAttach(pLevel, pPos, getConnectedDirection(pState).getOpposite());
    }

    public static boolean canAttach(LevelReader pReader, BlockPos pPos, Direction pDirection) {
        BlockPos blockpos = pPos.relative(pDirection);
        return pReader.getBlockState(blockpos).isFaceSturdy(pReader, blockpos, pDirection.getOpposite()) && !pReader.getBlockState(blockpos).is(Blocks.MAGMA_BLOCK);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {

        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());

        if (fluidstate.is(FluidTags.WATER) && fluidstate.getAmount() == 8){
            for(Direction direction : pContext.getNearestLookingDirections()) {
                BlockState blockstate;

                if (direction.getAxis() == Direction.Axis.Y) {
                    blockstate = this.defaultBlockState().setValue(FACE, AttachFace.FLOOR).setValue(FACING, pContext.getHorizontalDirection());
                } else {
                    blockstate = this.defaultBlockState().setValue(FACE, AttachFace.WALL).setValue(FACING, direction.getOpposite());
                }

                if (blockstate.canSurvive(pContext.getLevel(), pContext.getClickedPos())) {
                    return blockstate;
                }
            }
        }

        return null;
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        BlockState blockstate = getConnectedDirection(pState).getOpposite() == pFacing &&
                !pState.canSurvive(pLevel, pCurrentPos) ?
                Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);

        if (!blockstate.isAir()) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return blockstate;
    }

    public FluidState getFluidState(BlockState p_154537_) {
        return Fluids.WATER.getSource(false);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        popResource(pLevel, pPos, new ItemStack(this));
    }

    @Override
    public boolean canPlaceLiquid(BlockGetter pLevel, BlockPos pPos, BlockState pState, Fluid pFluid) {
        return false;
    }

    @Override
    public boolean placeLiquid(LevelAccessor pLevel, BlockPos pPos, BlockState pState, FluidState pFluidState) {
        return false;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING).add(FACE);
    }

    protected static Direction getConnectedDirection(BlockState pState) {
        switch ((AttachFace)pState.getValue(FACE)) {
            case CEILING:
                return Direction.DOWN;
            case FLOOR:
                return Direction.UP;
            default:
                return pState.getValue(FACING);
        }
    }
}
