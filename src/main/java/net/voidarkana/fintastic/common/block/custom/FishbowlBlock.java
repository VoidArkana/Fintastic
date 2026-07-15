package net.voidarkana.fintastic.common.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.voidarkana.fintastic.common.blockentity.custom.FishbowlBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FishbowlBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {

    public static final MapCodec<FishbowlBlock> CODEC = simpleCodec(FishbowlBlock::new);

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public FishbowlBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    protected @NotNull MapCodec<? extends FishbowlBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new FishbowlBlockEntity(pos, state);
    }

    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return Shapes.or(Block.box(0, 2, 0, 16, 13, 16), Block.box(2, 0, 2, 14, 15, 14));
    }
    
    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }
    @Override
    public @NotNull BlockState playerWillDestroy(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        breakBlock(state, world, pos);
        return super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    public void onBlockExploded(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Explosion explosion) {
        breakBlock(state, world, pos);
        super.onBlockExploded(state, world, pos, explosion);
    }

    public void breakBlock(BlockState state, Level world, BlockPos pos) {
        BlockEntity te = world.getBlockEntity(pos);
        if (te instanceof FishbowlBlockEntity tile) {
            tile.onDestroyed(state, pos);
        }
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult ray) {
        InteractionHand hand = InteractionHand.MAIN_HAND;
        BlockEntity te = world.getBlockEntity(pos);
        if (te instanceof FishbowlBlockEntity tile) {
            return tile.onActivated(state, pos, player, hand);
        }
        return super.useWithoutItem(state, world, pos, player, ray);
    }

    @Override
    public boolean isPathfindable(@NotNull BlockState state, @NotNull PathComputationType type) {
        return false;
    }

    @javax.annotation.Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        LevelAccessor levelaccessor = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        boolean flag = levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER;
        return this.defaultBlockState().setValue(WATERLOGGED, flag);
    }

    public @NotNull BlockState updateShape(BlockState state, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED);
    }

    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
    public boolean placeLiquid(@NotNull LevelAccessor level, @NotNull BlockPos pos, BlockState state, @NotNull FluidState fluidState) {
        if (!state.getValue(BlockStateProperties.WATERLOGGED) && fluidState.getType() == Fluids.WATER) {
            level.setBlock(pos, state.setValue(WATERLOGGED, Boolean.TRUE), 3);
            level.scheduleTick(pos, fluidState.getType(), fluidState.getType().getTickDelay(level));
            return true;
        } else {
            return false;
        }
    }


}
