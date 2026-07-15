package net.voidarkana.fintastic.mixin.client;

import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
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
            method = "shouldRenderFace(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/material/FluidState;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/block/state/BlockState;)Z",
            cancellable = true,
            at = @At(value = "TAIL")
    )
    private static void shouldRenderFace(BlockAndTintGetter level, BlockPos pos, FluidState fluidState, BlockState blockState, Direction side, BlockState neighborState, CallbackInfoReturnable<Boolean> cir) {

        BlockState state = level.getBlockState(pos.offset(side.getNormal()));

        if (fluidState.is(Fluids.WATER)){

            BlockState thisState = level.getBlockState(pos);
            if(state.is(FintyTags.Blocks.AQUARIUM_GLASS_PANES) || state.is(FintyTags.Blocks.AQUARIUM_GLASS)
                    || thisState.is(FintyTags.Blocks.AQUARIUM_GLASS_PANES)) {

                if (state.getBlock() instanceof AquariumGlassPane){
                    if (side == state.getValue(AquariumGlassPane.FACING).getOpposite()){
                        cir.setReturnValue(false);
                    }
                }else if(thisState.getBlock() instanceof AquariumGlassPane){
                    if (side == thisState.getValue(AquariumGlassPane.FACING)){
                        cir.setReturnValue(false);
                    }
                }else{
                    cir.setReturnValue(false);
                }
            }
        }

        if (fluidState.is(Fluids.LAVA)){
            if(state.is(FintyTags.Blocks.INFERNAL_AQUARIUM_GLASS)){

                if (state.getBlock() instanceof AquariumGlassPane){
                    if (side == state.getValue(AquariumGlassPane.FACING).getOpposite()){
                        cir.setReturnValue(false);
                    }
                }else{
                    cir.setReturnValue(false);
                }
            }
        }

        if (fluidState.is(FintyTags.Fluids.AC_ACID)){
            if(state.is(FintyTags.Blocks.RADON_AQUARIUM_GLASS)
                    || level.getBlockState(pos).is(FintyTags.Blocks.RADON_AQUARIUM_GLASS)){

                if (state.getBlock() instanceof AquariumGlassPane){
                    if (side == state.getValue(AquariumGlassPane.FACING).getOpposite()){
                        cir.setReturnValue(false);
                    }
                }else{
                    cir.setReturnValue(false);
                }
            }
        }

        if (fluidState.is(FintyTags.Fluids.AC_SODA)){
            if(state.is(FintyTags.Blocks.SUGAR_AQUARIUM_GLASS)
                    || level.getBlockState(pos).is(FintyTags.Blocks.SUGAR_AQUARIUM_GLASS)){

                if (state.getBlock() instanceof AquariumGlassPane){
                    if (side == state.getValue(AquariumGlassPane.FACING).getOpposite()){
                        cir.setReturnValue(false);
                    }
                }else{
                    cir.setReturnValue(false);
                }

            }
        }

    }

}