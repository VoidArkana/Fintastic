package net.voidarkana.fintastic.common.worldgen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.voidarkana.fintastic.common.block.custom.AlgaeLiveRockBlock;
import net.voidarkana.fintastic.common.worldgen.AlgaeBonemealConfig;

public class AlgaeBonemealFeature extends Feature<AlgaeBonemealConfig> {

    public AlgaeBonemealFeature(Codec<AlgaeBonemealConfig> p_66361_) {
        super(p_66361_);
    }

    public boolean place(FeaturePlaceContext<AlgaeBonemealConfig> pContext) {
        WorldGenLevel worldgenlevel = pContext.level();
        BlockPos blockpos = pContext.origin();
        BlockState blockstate = worldgenlevel.getBlockState(blockpos.below());
        AlgaeBonemealConfig algaeBonemealConfig = pContext.config();
        RandomSource randomsource = pContext.random();
        if (!(blockstate.getBlock() instanceof AlgaeLiveRockBlock)) {
            return false;
        } else {
            int i = blockpos.getY();
            if (i >= worldgenlevel.getMinBuildHeight() + 1 && i + 1 < worldgenlevel.getMaxBuildHeight()) {
                int j = 0;

                for(int k = 0; k < algaeBonemealConfig.spreadWidth * algaeBonemealConfig.spreadWidth; ++k) {
                    BlockPos blockpos1 = blockpos.offset(randomsource.nextInt(algaeBonemealConfig.spreadWidth) - randomsource.nextInt(algaeBonemealConfig.spreadWidth),
                            randomsource.nextInt(algaeBonemealConfig.spreadHeight) - randomsource.nextInt(algaeBonemealConfig.spreadHeight),
                            randomsource.nextInt(algaeBonemealConfig.spreadWidth) - randomsource.nextInt(algaeBonemealConfig.spreadWidth));

                    BlockState blockstate1 = algaeBonemealConfig.stateProvider.getState(randomsource, blockpos1);
                    if (worldgenlevel.getBlockState(blockpos1).is(Blocks.WATER)
                            && blockpos1.getY() > worldgenlevel.getMinBuildHeight()
                            && blockstate1.canSurvive(worldgenlevel, blockpos1)) {

                        worldgenlevel.setBlock(blockpos1, blockstate1, 2);
                        ++j;
                    }
                }

                return j > 0;
            } else {
                return false;
            }
        }
    }
}
