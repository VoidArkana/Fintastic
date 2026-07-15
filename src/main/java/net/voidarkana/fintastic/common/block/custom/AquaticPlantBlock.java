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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.IShearable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class AquaticPlantBlock extends BushBlock implements BonemealableBlock, LiquidBlockContainer, IShearable {
    public static final MapCodec<AquaticPlantBlock> CODEC = simpleCodec(AquaticPlantBlock::new);

    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);
    Block tallBlock;
    boolean growsOnBonemeal;

    public AquaticPlantBlock(BlockBehaviour.Properties properties, boolean growsInto, Block blockThatItGrowsInto) {
        super(properties);
        this.growsOnBonemeal = growsInto;
        this.tallBlock = blockThatItGrowsInto;
    }

    public AquaticPlantBlock(BlockBehaviour.Properties properties) {
        this(properties, false, Blocks.WATER);
    }

    @Override
    protected @NotNull MapCodec<? extends AquaticPlantBlock> codec() {
        return CODEC;
    }

    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    protected boolean mayPlaceOn(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return state.isFaceSturdy(level, pos, Direction.UP) && !state.is(Blocks.MAGMA_BLOCK) && level.getBlockState(pos.above()).getFluidState().is(Fluids.WATER);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return fluidstate.is(FluidTags.WATER) && fluidstate.getAmount() == 8 ? super.getStateForPlacement(context) : null;
    }

    public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        BlockState blockstate = super.updateShape(state, direction, neighborState, level, pos, neighborPos);
        if (!blockstate.isAir()) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return blockstate;
    }

    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
        if (this.growsOnBonemeal){
            BlockPos blockpos = pos.above();
            return level.getBlockState(blockpos).is(Blocks.WATER);
        }else {
            return true;
        }
    }

    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    public @NotNull FluidState getFluidState(@NotNull BlockState state) {
        return Fluids.WATER.getSource(false);
    }

    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        if (this.growsOnBonemeal){
            BlockState blockstate = this.tallBlock.defaultBlockState();
            BlockState blockstate1 = blockstate.setValue(TallAquaticPlantBlock.HALF, DoubleBlockHalf.UPPER);
            BlockPos blockpos = pos.above();
            if (level.getBlockState(blockpos).is(Blocks.WATER)) {
                level.setBlock(pos, blockstate, 2);
                level.setBlock(blockpos, blockstate1, 2);
            }
        }else {
            popResource(level, pos, new ItemStack(this));
        }
    }

    public boolean canPlaceLiquid(@Nullable Player player, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Fluid fluid) {
        return false;
    }

    public boolean placeLiquid(@NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull FluidState fluidState) {
        return false;
    }

    public float getMaxHorizontalOffset() {
        return 0.15F;
    }
}
