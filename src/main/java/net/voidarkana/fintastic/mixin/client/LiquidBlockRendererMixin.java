package net.voidarkana.fintastic.mixin.client;

import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.voidarkana.fintastic.common.block.custom.AquariumGlassBlock;
import net.voidarkana.fintastic.common.block.custom.AquariumGlassPane;
import net.voidarkana.fintastic.util.FintyTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LiquidBlockRenderer.class)
public class LiquidBlockRendererMixin {

    @Inject(
            method = {"shouldRenderFace"},
            cancellable = true,
            at = @At(value = "TAIL")
    )
    private static void shouldRenderFace(BlockAndTintGetter pLevel, BlockPos pPos, FluidState pFluidState, BlockState pBlockState, Direction pSide, FluidState pNeighborFluid, CallbackInfoReturnable<Boolean> cir) {

        BlockState state = pLevel.getBlockState(pPos.offset(pSide.getNormal()));

        if (pFluidState.is(Fluids.WATER)){

            BlockState thisState = pLevel.getBlockState(pPos);
            if(state.is(FintyTags.Blocks.AQUARIUM_GLASS_PANES) || state.is(FintyTags.Blocks.AQUARIUM_GLASS)
                    || thisState.is(FintyTags.Blocks.AQUARIUM_GLASS_PANES)) {

                if (state.getBlock() instanceof AquariumGlassPane){
                    if (pSide == state.getValue(AquariumGlassPane.FACING).getOpposite()){
                        cir.setReturnValue(false);
                    }
                }else if(thisState.getBlock() instanceof AquariumGlassPane){
                    if (pSide == thisState.getValue(AquariumGlassPane.FACING)){
                        cir.setReturnValue(false);
                    }
                }else{
                    cir.setReturnValue(false);
                }
            }
        }

        if (pFluidState.is(Fluids.LAVA)){
            if(state.is(FintyTags.Blocks.INFERNAL_AQUARIUM_GLASS)){

                if (state.getBlock() instanceof AquariumGlassPane){
                    if (pSide == state.getValue(AquariumGlassPane.FACING).getOpposite()){
                        cir.setReturnValue(false);
                    }
                }else{
                    cir.setReturnValue(false);
                }
            }
        }

        if (pFluidState.is(FintyTags.Fluid.AC_ACID)){
            if(state.is(FintyTags.Blocks.RADON_AQUARIUM_GLASS)
                    || pLevel.getBlockState(pPos).is(FintyTags.Blocks.RADON_AQUARIUM_GLASS)){

                if (state.getBlock() instanceof AquariumGlassPane){
                    if (pSide == state.getValue(AquariumGlassPane.FACING).getOpposite()){
                        cir.setReturnValue(false);
                    }
                }else{
                    cir.setReturnValue(false);
                }
            }
        }

        if (pFluidState.is(FintyTags.Fluid.AC_SODA)){
            if(state.is(FintyTags.Blocks.SUGAR_AQUARIUM_GLASS)
                    || pLevel.getBlockState(pPos).is(FintyTags.Blocks.SUGAR_AQUARIUM_GLASS)){

                if (state.getBlock() instanceof AquariumGlassPane){
                    if (pSide == state.getValue(AquariumGlassPane.FACING).getOpposite()){
                        cir.setReturnValue(false);
                    }
                }else{
                    cir.setReturnValue(false);
                }

            }
        }

    }

}