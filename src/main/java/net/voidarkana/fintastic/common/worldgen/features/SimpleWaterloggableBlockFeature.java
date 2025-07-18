package net.voidarkana.fintastic.common.worldgen.features;
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
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.voidarkana.fintastic.common.block.custom.StromatoliteBlock;

public class SimpleWaterloggableBlockFeature extends Feature<SimpleBlockConfiguration> {

   public SimpleWaterloggableBlockFeature(Codec< SimpleBlockConfiguration > p_66808_) {
        super(p_66808_);
    }

    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> pContext) {
        SimpleBlockConfiguration simpleblockconfiguration = pContext.config();
        WorldGenLevel worldgenlevel = pContext.level();
        BlockPos blockpos = pContext.origin();
        BlockState blockstate = simpleblockconfiguration.toPlace().getState(pContext.random(), blockpos);

        if (((worldgenlevel.isEmptyBlock(blockpos)) || (worldgenlevel.isWaterAt(blockpos)))
                && (worldgenlevel.isEmptyBlock(blockpos.above()) || (worldgenlevel.isWaterAt(blockpos)))) {
            if (blockstate.canSurvive(worldgenlevel, blockpos)) {
                if (blockstate.getBlock() instanceof DoublePlantBlock) {
                    if (worldgenlevel.isEmptyBlock(blockpos.above().above()) || (worldgenlevel.isWaterAt(blockpos.above().above()))) {
                        DoublePlantBlock.placeAt(worldgenlevel, blockstate, blockpos, 2);
                        return true;
                    }

                } else {

                    if (blockstate.getBlock() instanceof SimpleWaterloggedBlock){
                        FluidState fluidstate = worldgenlevel.getFluidState(blockpos);

                        if (blockstate.getBlock() instanceof StromatoliteBlock){
                            worldgenlevel.setBlock(blockpos, blockstate.setValue(StromatoliteBlock.WATERLOGGED,
                                    fluidstate.getType() == Fluids.WATER), 2);

                        }else if (blockstate.getBlock() instanceof BaseCoralPlantBlock){
                            worldgenlevel.setBlock(blockpos, blockstate.setValue(BaseCoralPlantBlock.WATERLOGGED,
                                    fluidstate.getType() == Fluids.WATER), 2);

                        }else {
                            worldgenlevel.setBlock(blockpos, blockstate, 2);
                        }
                    }else{
                        worldgenlevel.setBlock(blockpos, blockstate, 2);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
