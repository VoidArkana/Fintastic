package net.voidarkana.fintastic.common.worldgen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.voidarkana.fintastic.common.block.FintyBlocks;
import net.voidarkana.fintastic.common.block.custom.DuckweedBlock;
import net.voidarkana.fintastic.common.worldgen.configurations.DuckweedPatchConfiguration;

import java.util.HashSet;
import java.util.Set;

public class DuckweedPatchFeature extends Feature<DuckweedPatchConfiguration> {

    public DuckweedPatchFeature(Codec<DuckweedPatchConfiguration> pCodec) {
        super(pCodec);
    }

    public boolean place(FeaturePlaceContext<DuckweedPatchConfiguration> pContext) {
        WorldGenLevel worldgenlevel = pContext.level();
        DuckweedPatchConfiguration vegetationpatchconfiguration = pContext.config();
        RandomSource randomsource = pContext.random();
        BlockPos blockpos = pContext.origin();
        int i = vegetationpatchconfiguration.xzRadius.sample(randomsource) + randomsource.nextInt(1, 3);
        int j = vegetationpatchconfiguration.xzRadius.sample(randomsource) + randomsource.nextInt(1, 3);
        Set<BlockPos> set = this.placePatch(worldgenlevel, vegetationpatchconfiguration, randomsource, blockpos, i, j);
        return !set.isEmpty();
    }

    protected Set<BlockPos> placePatch(WorldGenLevel pLevel, DuckweedPatchConfiguration pConfig, RandomSource pRandom, BlockPos pPos, int pXRadius, int pZRadius) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pPos.mutable();
        Set<BlockPos> set = new HashSet<>();

        int quintX = pXRadius/5;
        int quintZ = pZRadius/5;
        for(int x = -pXRadius; x <= pXRadius; ++x) {
            boolean isXEdge = x == -pXRadius || x == pXRadius;

            for(int z = -pZRadius; z <= pZRadius; ++z) {
                boolean isZEdge = z == -pZRadius || z == pZRadius;
                boolean isAnyEdge = isXEdge || isZEdge;
                boolean isBothEdge = isXEdge && isZEdge;
                boolean isOneEdge = isAnyEdge && !isBothEdge;
                if (!isBothEdge && (!isOneEdge || pConfig.extraEdgeColumnChance != 0.0F && !(pRandom.nextFloat() > pConfig.extraEdgeColumnChance))) {
                    blockpos$mutableblockpos.setWithOffset(pPos, x, 0, z);

                    int zRadius = Math.max(1, Math.abs(z));
                    int xRadius = Math.max(1, Math.abs(x));
                    double gauss_sharpness = 4.1; // less than your top limit
                    double gauss_width = 0.03;
                    int gauss_bottom_limit = 1;
                    double  mpowz = Math.pow(zRadius, 2);
                    double mpowx = Math.pow(xRadius, 2);
                    double mexp = Math.exp(gauss_width*(-mpowx -mpowz));
                    int random = pRandom.nextInt(-1, 2);
                    int duckweedAmount = (int)Math.min(5, Math.round(mexp*gauss_sharpness + gauss_bottom_limit) + (x <= 1 || z <= 1 ? 0 : random));

                    if (duckweedAmount == 0 && pRandom.nextBoolean()){
                        duckweedAmount = 1;
                    }

                    if (duckweedAmount != 0){
                        BlockState duckweed = FintyBlocks.DUCKWEED.get().defaultBlockState().setValue(DuckweedBlock.AMOUNT, duckweedAmount)
                                .setValue(DuckweedBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(pRandom));

                        if (duckweed.canSurvive(pLevel, blockpos$mutableblockpos)) {
                            pLevel.setBlock(blockpos$mutableblockpos, duckweed, 3);
                            pLevel.scheduleTick(blockpos$mutableblockpos, duckweed.getBlock(), 0);

                            BlockPos blockpos = blockpos$mutableblockpos.immutable();
                            set.add(blockpos);
                        }
                    }
                }
            }
        }

        return set;
    }
}
