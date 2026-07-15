package net.voidarkana.fintastic.common.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class AnubiasBlock extends BushBlock implements BonemealableBlock, LiquidBlockContainer {

    public static final MapCodec<AnubiasBlock> CODEC = simpleCodec(AnubiasBlock::new);

    protected static final VoxelShape FLOORNORTHSOUTH = Block.box(2, 0, 1, 14, 9, 15);
    protected static final VoxelShape FLOOREASTWEST = Block.box(1, 0, 2, 15, 9, 14);
    protected static final VoxelShape SOUTHWALL = Block.box(2, 1, 0, 14, 15, 8);
    protected static final VoxelShape WESTWALL = Block.box(8, 1, 2, 16, 15, 14);
    protected static final VoxelShape NORTHWALL = Block.box(2, 1, 8, 14, 15, 16);
    protected static final VoxelShape EASTWALL = Block.box(0, 1, 2, 8, 15, 14);

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<AttachFace> FACE = BlockStateProperties.ATTACH_FACE;

    public AnubiasBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(FACE, AttachFace.FLOOR));
    }

    @Override
    protected @NotNull MapCodec<? extends AnubiasBlock> codec() {
        return CODEC;
    }

    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        Direction direction = state.getValue(FACING);
        if (state.getValue(FACE) == AttachFace.FLOOR){
            if (state.getValue(FACING) == Direction.NORTH || state.getValue(FACING) == Direction.SOUTH){
                return FLOORNORTHSOUTH;
            }else {
                return FLOOREASTWEST;
            }
        }else {

            return switch (direction) {
                case EAST -> EASTWALL;
                case WEST -> WESTWALL;
                case SOUTH -> SOUTHWALL;
                case NORTH -> NORTHWALL;
                default -> throw new IncompatibleClassChangeError();
            };
        }
    }

    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
        return canAttach(level, pos, getConnectedDirection(state).getOpposite());
    }

    public static boolean canAttach(LevelReader reader, BlockPos pos, Direction direction) {
        BlockPos blockpos = pos.relative(direction);
        return reader.getBlockState(blockpos).isFaceSturdy(reader, blockpos, direction.getOpposite()) && !reader.getBlockState(blockpos).is(Blocks.MAGMA_BLOCK);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());

        if (fluidstate.is(FluidTags.WATER) && fluidstate.getAmount() == 8){
            for(Direction direction : context.getNearestLookingDirections()) {
                BlockState blockstate;

                if (direction.getAxis() == Direction.Axis.Y) {
                    blockstate = this.defaultBlockState().setValue(FACE, AttachFace.FLOOR).setValue(FACING, context.getHorizontalDirection());
                } else {
                    blockstate = this.defaultBlockState().setValue(FACE, AttachFace.WALL).setValue(FACING, direction.getOpposite());
                }

                if (blockstate.canSurvive(context.getLevel(), context.getClickedPos())) {
                    return blockstate;
                }
            }
        }

        return null;
    }

    public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        BlockState blockstate = getConnectedDirection(state).getOpposite() == facing &&
                !state.canSurvive(level, currentPos) ?
                Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, level, currentPos, facingPos);

        if (!blockstate.isAir()) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return blockstate;
    }

    public @NotNull FluidState getFluidState(@NotNull BlockState state) {
        return Fluids.WATER.getSource(false);
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        popResource(level, pos, new ItemStack(this));
    }

    @Override
    public boolean canPlaceLiquid(@Nullable Player player, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Fluid fluid) {
        return false;
    }

    @Override
    public boolean placeLiquid(@NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull FluidState fluidState) {
        return false;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(FACE);
    }

    protected static Direction getConnectedDirection(BlockState state) {
        return switch (state.getValue(FACE)) {
            case CEILING -> Direction.DOWN;
            case FLOOR -> Direction.UP;
            default -> state.getValue(FACING);
        };
    }
}
