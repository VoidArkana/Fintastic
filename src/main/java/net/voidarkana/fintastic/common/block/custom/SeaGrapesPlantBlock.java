package net.voidarkana.fintastic.common.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.Shapes;
import net.voidarkana.fintastic.common.block.FintyBlocks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class SeaGrapesPlantBlock extends GrowingPlantBodyBlock implements LiquidBlockContainer {
    public static final MapCodec<SeaGrapesPlantBlock> CODEC = simpleCodec(SeaGrapesPlantBlock::new);

    public SeaGrapesPlantBlock(BlockBehaviour.Properties properties) {
        super(properties, Direction.UP, Shapes.block(), true);
    }

    @Override
    protected @NotNull MapCodec<? extends SeaGrapesPlantBlock> codec() {
        return CODEC;
    }

    protected @NotNull GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) FintyBlocks.SEA_GRAPES.get();
    }

    public @NotNull FluidState getFluidState(@NotNull BlockState state) {
        return Fluids.WATER.getSource(false);
    }

    public boolean canAttachTo(@NotNull BlockState state) {
        return ((SeaGrapesBlock) this.getHeadBlock()).canAttachTo(state);
    }

    public boolean canPlaceLiquid(@Nullable Player player, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Fluid fluid) {
        return false;
    }

    public boolean placeLiquid(@NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull FluidState fluidState) {
        return false;
    }

}
