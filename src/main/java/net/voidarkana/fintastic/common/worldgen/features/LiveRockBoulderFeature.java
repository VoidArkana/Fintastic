package net.voidarkana.fintastic.common.worldgen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.voidarkana.fintastic.common.block.custom.AlgaeLiveRockBlock;
import net.voidarkana.fintastic.common.worldgen.configurations.LiveRockBoulderConfig;


public class LiveRockBoulderFeature extends Feature<LiveRockBoulderConfig> {

    public LiveRockBoulderFeature(Codec< LiveRockBoulderConfig > codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<LiveRockBoulderConfig> context) {
        BlockPos blockpos = context.origin();
        WorldGenLevel worldgenlevel = context.level();
        RandomSource random = context.random();

        if (blockpos.getY() >= 50)
            return false;

        LiveRockBoulderConfig config;
        for(config = context.config(); blockpos.getY() > worldgenlevel.getMinBuildHeight() + 3; blockpos = blockpos.below()) {
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

            int x = random.nextInt(2, 5);
            int y = random.nextInt(2,6);
            int z = random.nextInt(2, 5);
            float f = (float)(x + y + z) * 0.333F + 0.5F;
            boolean type = random.nextBoolean();

            for(BlockPos blockpos1 : BlockPos.betweenClosed(blockpos.offset(-x, -y, -z), blockpos.offset(x, y, z))) {
                if (blockpos1.distSqr(blockpos) <= (double)(f * f)) {

                    BlockState blockstate;
                    if (type)
                        blockstate = config.grassState1.getState(random, blockpos1);
                    else
                        blockstate = config.grassState2.getState(random, blockpos1);

                    worldgenlevel.setBlock(blockpos1, blockstate, 3);
                    worldgenlevel.scheduleTick(blockpos1, blockstate.getBlock(), 0);

                    if (worldgenlevel.getBlockState(blockpos1).getBlock() instanceof AlgaeLiveRockBlock
                            && random.nextInt(4)==0){
                        if (type)
                            config.vegetationFeature1.value().place(worldgenlevel, context.chunkGenerator(), random, blockpos1.relative(Direction.UP));
                        else
                            config.vegetationFeature2.value().place(worldgenlevel, context.chunkGenerator(), random, blockpos1.relative(Direction.UP));
                    }
                }
            }

            return true;
        }
    }
}
