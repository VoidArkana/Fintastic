package net.voidarkana.fintastic.common.worldgen.features;

import com.mojang.datafixers.kinds.IdF;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.BaseCoralPlantBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.material.Fluids;
import net.voidarkana.fintastic.common.block.custom.StromatoliteBlock;

public class SimpleWaterloggableBlockFeature extends Feature<SimpleBlockConfiguration> {

   public SimpleWaterloggableBlockFeature(Codec< SimpleBlockConfiguration > p_66808_) {
        super(p_66808_);
    }

    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> p_160341_) {
        SimpleBlockConfiguration simpleblockconfiguration = p_160341_.config();
        WorldGenLevel worldgenlevel = p_160341_.level();
        BlockPos blockpos = p_160341_.origin();
        BlockState blockstate = simpleblockconfiguration.toPlace().getState(p_160341_.random(), blockpos);
        if (worldgenlevel.getFluidState(blockpos).is(Fluids.WATER) && blockstate instanceof SimpleWaterloggedBlock){
            if (blockstate.getBlock() instanceof StromatoliteBlock){
                blockstate.setValue(StromatoliteBlock.WATERLOGGED, true);
            }
            if (blockstate.getBlock() instanceof BaseCoralPlantBlock){
                blockstate.setValue(BaseCoralPlantBlock.WATERLOGGED, true);
            }
        }
        if (blockstate.canSurvive(worldgenlevel, blockpos)) {
            if (blockstate.getBlock() instanceof DoublePlantBlock) {
                if (!worldgenlevel.isEmptyBlock(blockpos.above())) {
                    return false;
                }

                DoublePlantBlock.placeAt(worldgenlevel, blockstate, blockpos, 2);
            } else {
                worldgenlevel.setBlock(blockpos, blockstate, 2);
            }

            return true;
        } else {
            return false;
        }
    }
}
