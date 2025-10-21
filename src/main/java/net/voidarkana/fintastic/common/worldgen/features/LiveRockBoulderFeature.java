package net.voidarkana.fintastic.common.worldgen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.voidarkana.fintastic.common.block.custom.AlgaeLiveRockBlock;
import net.voidarkana.fintastic.common.worldgen.AlgaeBonemealConfig;
import net.voidarkana.fintastic.common.worldgen.LiveRockBoulderConfig;


public class LiveRockBoulderFeature extends Feature<LiveRockBoulderConfig> {

    public LiveRockBoulderFeature(Codec< LiveRockBoulderConfig > p_65248_) {
        super(p_65248_);
    }

    public boolean place(FeaturePlaceContext<LiveRockBoulderConfig> pContext) {
        BlockPos blockpos = pContext.origin();
        WorldGenLevel worldgenlevel = pContext.level();
        RandomSource pRandom = pContext.random();

        if (blockpos.getY() >= 50)
            return false;

        LiveRockBoulderConfig pConfig;
        for(pConfig = pContext.config(); blockpos.getY() > worldgenlevel.getMinBuildHeight() + 3; blockpos = blockpos.below()) {
            if (!worldgenlevel.isEmptyBlock(blockpos.below())) {
                BlockState blockstate = worldgenlevel.getBlockState(blockpos.below());
                if (isDirt(blockstate) || isStone(blockstate)) {
                    break;
                }
            }
        }

        if (blockpos.getY() <= worldgenlevel.getMinBuildHeight() + 3) {
            return false;
        } else {

            int x = pRandom.nextInt(2, 5);
            int y = pRandom.nextInt(2,6);
            int z = pRandom.nextInt(2, 5);
            float f = (float)(x + y + z) * 0.333F + 0.5F;
            boolean type = pRandom.nextBoolean();

            for(BlockPos blockpos1 : BlockPos.betweenClosed(blockpos.offset(-x, -y, -z), blockpos.offset(x, y, z))) {
                if (blockpos1.distSqr(blockpos) <= (double)(f * f)) {

                    BlockState blockstate;
                    if (type)
                        blockstate = pConfig.grassState1.getState(pRandom, blockpos1);
                    else
                        blockstate = pConfig.grassState2.getState(pRandom, blockpos1);

                    worldgenlevel.setBlock(blockpos1, blockstate, 3);
                    worldgenlevel.scheduleTick(blockpos1, blockstate.getBlock(), 0);

                    if (worldgenlevel.getBlockState(blockpos1).getBlock() instanceof AlgaeLiveRockBlock block
                            && pRandom.nextInt(4)==0){
                        if (type)
                            pConfig.vegetationFeature1.value().place(worldgenlevel, pContext.chunkGenerator(), pRandom, blockpos1.relative(Direction.UP));
                        else
                            pConfig.vegetationFeature2.value().place(worldgenlevel, pContext.chunkGenerator(), pRandom, blockpos1.relative(Direction.UP));
                    }
                }
            }

            return true;
        }
    }
}
