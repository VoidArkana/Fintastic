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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class HornwortBlock extends BushBlock implements BonemealableBlock, LiquidBlockContainer {

    public static final MapCodec<HornwortBlock> CODEC = simpleCodec(HornwortBlock::new);

    protected static final VoxelShape SOUTH_1 = Block.box(8, 0, 8, 15, 15, 15);
    protected static final VoxelShape SOUTH_2 = Block.box(8, 0, 1, 15, 15, 15);

    protected static final VoxelShape EAST_1 = Block.box(8, 0, 1, 15, 15, 8);
    protected static final VoxelShape EAST_2 = Block.box(1, 0, 1, 15, 15, 8);

    protected static final VoxelShape NORTH_1 = Block.box(1, 0, 1, 8, 15, 8);
    protected static final VoxelShape NORTH_2 = Block.box(1, 0, 1, 8, 15, 15);

    protected static final VoxelShape WEST_1 = Block.box(1, 0, 8, 8, 15, 15);
    protected static final VoxelShape WEST_2 = Block.box(1, 0, 8, 15, 15, 15);

    protected static final VoxelShape FULL = Block.box(1, 0, 1, 15, 15, 15);

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty AMOUNT = BlockStateProperties.FLOWER_AMOUNT;

    public HornwortBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(AMOUNT, 1));
    }

    @Override
    protected @NotNull MapCodec<? extends HornwortBlock> codec() {
        return CODEC;
    }

    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {

        if (state.getValue(AMOUNT)<3) {
            if (state.getValue(FACING) == Direction.NORTH){
                return state.getValue(AMOUNT)==1 ? NORTH_1: NORTH_2;

            }else if (state.getValue(FACING) == Direction.WEST){
                return state.getValue(AMOUNT)==1 ? WEST_1: WEST_2;

            }else if (state.getValue(FACING) == Direction.SOUTH){
                return state.getValue(AMOUNT)==1 ? SOUTH_1: SOUTH_2;

            }else{
                return state.getValue(AMOUNT)==1 ? EAST_1: EAST_2;
            }
        }else {
            return FULL;
        }

    }

    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    public boolean canBeReplaced(@NotNull BlockState state, BlockPlaceContext context) {
        return !context.isSecondaryUseActive() && context.getItemInHand().is(this.asItem()) && state.getValue(AMOUNT) < 4 || super.canBeReplaced(state, context);
    }

    protected boolean mayPlaceOn(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return state.isFaceSturdy(level, pos, Direction.UP) && !state.is(Blocks.MAGMA_BLOCK);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());

        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return fluidstate.is(FluidTags.WATER) && fluidstate.getAmount() == 8 ?
                blockstate.is(this) ? blockstate.setValue(AMOUNT, Math.min(4, blockstate.getValue(AMOUNT) + 1))
                        : this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()) : null;
    }

    public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        BlockState blockstate = super.updateShape(state, facing, facingState, level, currentPos, facingPos);
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

    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, BlockState state) {
        int i = state.getValue(AMOUNT);
        if (i < 4) {
            level.setBlock(pos, state.setValue(AMOUNT, i + 1), 2);
        } else {
            popResource(level, pos, new ItemStack(this));
        }

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
        builder.add(FACING, AMOUNT);
    }
}
