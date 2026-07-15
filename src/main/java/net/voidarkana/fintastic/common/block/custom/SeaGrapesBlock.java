package net.voidarkana.fintastic.common.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.CommonHooks;
import net.voidarkana.fintastic.common.block.FintyBlocks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class SeaGrapesBlock extends GrowingPlantHeadBlock implements LiquidBlockContainer {
    public static final MapCodec<SeaGrapesBlock> CODEC = simpleCodec(SeaGrapesBlock::new);

    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D);
    private static final double GROW_PER_TICK_PROBABILITY = 0.14D;

    public SeaGrapesBlock(BlockBehaviour.Properties properties) {
        super(properties, Direction.UP, SHAPE, true, 0.14D);
    }

    @Override
    protected @NotNull MapCodec<? extends SeaGrapesBlock> codec() {
        return CODEC;
    }

    public @NotNull BlockState getStateForPlacement(LevelAccessor level) {
        return this.defaultBlockState().setValue(AGE, level.getRandom().nextInt(3));
    }

    protected boolean canGrowInto(BlockState state) {
        return state.is(Blocks.WATER);
    }

    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) < 3;
    }

    protected @NotNull Block getBodyBlock() {
        return FintyBlocks.SEA_GRAPES_PLANT.get();
    }

    public boolean canAttachTo(BlockState state) {
        return !state.is(Blocks.MAGMA_BLOCK);
    }

    public boolean canPlaceLiquid(@Nullable Player player, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Fluid fluid) {
        return false;
    }

    public boolean placeLiquid(@NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull FluidState fluidState) {
        return false;
    }

    protected int getBlocksToGrowWhenBonemealed(RandomSource random) {
        return random.nextInt(1,3);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return fluidstate.is(FluidTags.WATER) && fluidstate.getAmount() == 8 ? super.getStateForPlacement(context) : null;
    }

    public @NotNull FluidState getFluidState(@NotNull BlockState state) {
        return Fluids.WATER.getSource(false);
    }

    public void randomTick(BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (state.getValue(AGE) < 3 && CommonHooks.canCropGrow(level, pos.relative(this.growthDirection),
                level.getBlockState(pos.relative(this.growthDirection)),random.nextDouble() < GROW_PER_TICK_PROBABILITY)) {
            BlockPos blockpos = pos.relative(this.growthDirection);
            if (this.canGrowInto(level.getBlockState(blockpos))) {
                level.setBlockAndUpdate(blockpos, this.getGrowIntoState(state, level.random));
                CommonHooks.fireCropGrowPost(level, blockpos, level.getBlockState(blockpos));
            }
        }
    }

    public @NotNull BlockState getMaxAgeState(BlockState state) {
        return state.setValue(AGE, 3);
    }

    public boolean isMaxAge(BlockState state) {
        return state.getValue(AGE) == 3;
    }

    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, BlockPos pos, BlockState state) {
        BlockPos blockpos = pos.relative(this.growthDirection);
        int i = Math.min(state.getValue(AGE) + 1, 3);
        int j = this.getBlocksToGrowWhenBonemealed(random);

        for(int k = 0; k < j && this.canGrowInto(level.getBlockState(blockpos)); ++k) {
            level.setBlockAndUpdate(blockpos, state.setValue(AGE, i));
            blockpos = blockpos.relative(this.growthDirection);
            i = Math.min(i + 1, 3);
        }
    }


}
