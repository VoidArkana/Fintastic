package net.voidarkana.fintastic.common.worldgen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Fluids;
import net.voidarkana.fintastic.common.block.FintyBlocks;
import net.voidarkana.fintastic.common.block.custom.*;

public class LotusFeature extends Feature<NoneFeatureConfiguration> {

    public LotusFeature(Codec<NoneFeatureConfiguration> p_66754_) {
        super(p_66754_);
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> pContext) {
        boolean flag = false;
        RandomSource randomsource = pContext.random();
        WorldGenLevel worldgenlevel = pContext.level();
        BlockPos blockpos = pContext.origin();
        int i = randomsource.nextInt(8) - randomsource.nextInt(8);
        int j = randomsource.nextInt(8) - randomsource.nextInt(8);
        int k = worldgenlevel.getHeight(Heightmap.Types.OCEAN_FLOOR, blockpos.getX() + i, blockpos.getZ() + j);
        BlockPos blockpos1 = new BlockPos(blockpos.getX() + i, k, blockpos.getZ() + j);

        if (worldgenlevel.getBlockState(blockpos1.above()).getFluidState().isEmpty()){
            Direction direction = switch (randomsource.nextInt(0, 4)) {
                case 1 -> Direction.EAST;
                case 2 -> Direction.WEST;
                case 3 -> Direction.NORTH;
                default -> Direction.SOUTH;};

            int amount = randomsource.nextInt(5) + 1;
            int maxFlowers = LotusPlantBlock.getMaxFlowers(amount);
            int flowers = randomsource.nextInt(maxFlowers+1);
            int actualFlowers = Math.max(0, Math.min(maxFlowers, flowers));

            Boolean bottomFluidstate = worldgenlevel.getBlockState(blockpos1).getFluidState().is(Fluids.WATER);

            BlockState blockstate = FintyBlocks.LOTUS.get().defaultBlockState()
                    .setValue(LotusPlantBlock.AMOUNT, amount)
                    .setValue(LotusPlantBlock.FACING, direction)
                    .setValue(LotusPlantBlock.HALF, DoubleBlockHalf.LOWER)
                    .setValue(LotusPlantBlock.WATERLOGGED, bottomFluidstate)
                    .setValue(LotusPlantBlock.FLOWERS, actualFlowers);

            BlockState blockstate2 = FintyBlocks.LOTUS.get().defaultBlockState()
                    .setValue(LotusPlantBlock.AMOUNT, amount)
                    .setValue(LotusPlantBlock.FACING, direction)
                    .setValue(LotusPlantBlock.HALF, DoubleBlockHalf.UPPER)
                    .setValue(LotusPlantBlock.FLOWERS, actualFlowers);

            if (blockstate.canSurvive(worldgenlevel, blockpos1)) {

                worldgenlevel.setBlock(blockpos1, blockstate, 2);
                worldgenlevel.setBlock(blockpos1.above(), blockstate2, 2);

                this.placePatchAround(worldgenlevel, randomsource, (bottomFluidstate ? blockpos1.above() : blockpos1), randomsource.nextInt(2, 5), randomsource.nextInt(2, 5));

                flag = true;
            }
        }

        return flag;
    }

    protected void placePatchAround(WorldGenLevel pLevel, RandomSource pRandom, BlockPos pPos, int pXRadius, int pZRadius) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pPos.mutable();
        for(int x = -pXRadius; x <= pXRadius; ++x) {
            for(int z = -pZRadius; z <= pZRadius; ++z) {

                int currentRadius = Math.toIntExact(Math.round(Math.sqrt(Math.pow(z, 2) + Math.pow(x, 2))));

                if (currentRadius <= pXRadius || currentRadius <= pZRadius && pRandom.nextInt(5)==0) {
                    blockpos$mutableblockpos.setWithOffset(pPos, x, 0, z);

                    BlockState blockState;
                    BlockPos blockpos = blockpos$mutableblockpos.immutable();


                    if (pRandom.nextInt(3)==0){
                        blockState = FintyBlocks.LOTUS_FLOWER.get().defaultBlockState().setValue(LotusFlowerBlock.FLOWERS, pRandom.nextInt(1,4));
                    }else {
                        blockState = FintyBlocks.LOTUS_PAD.get().defaultBlockState().setValue(LotusPadBlock.FLOWER, pRandom.nextInt(3)==0);
                    }

                    BlockState existingBlock = pLevel.getBlockState(blockpos$mutableblockpos);
                    if (existingBlock.is(Blocks.AIR)){
                        if (pRandom.nextInt(5)==0){

                            Direction direction = switch (pRandom.nextInt(0, 4)) {
                                case 1 -> Direction.EAST;
                                case 2 -> Direction.WEST;
                                case 3 -> Direction.NORTH;
                                default -> Direction.SOUTH;};
                            int amount = pRandom.nextInt(5) + 1;
                            int maxFlowers = LotusPlantBlock.getMaxFlowers(amount);
                            int flowers = pRandom.nextInt(maxFlowers+1);
                            int actualFlowers = Math.max(0, Math.min(maxFlowers, flowers));

                            boolean fluidstate = pLevel.getBlockState(pPos.below()).getFluidState().is(Fluids.WATER);

                            blockState = FintyBlocks.LOTUS.get().defaultBlockState()
                                    .setValue(LotusPlantBlock.AMOUNT, amount)
                                    .setValue(LotusPlantBlock.FACING, direction)
                                    .setValue(LotusPlantBlock.HALF, DoubleBlockHalf.LOWER)
                                    .setValue(LotusPlantBlock.WATERLOGGED, fluidstate)
                                    .setValue(LotusPlantBlock.FLOWERS, actualFlowers);

                            BlockState blockstate2 = FintyBlocks.LOTUS.get().defaultBlockState()
                                    .setValue(LotusPlantBlock.AMOUNT, amount)
                                    .setValue(LotusPlantBlock.FACING, direction)
                                    .setValue(LotusPlantBlock.HALF, DoubleBlockHalf.UPPER)
                                    .setValue(LotusPlantBlock.FLOWERS, actualFlowers);

                            if (blockState.canSurvive(pLevel, pPos.below())) {
                                pLevel.setBlock(pPos.below(), blockState, 2);
                                pLevel.setBlock(pPos, blockstate2, 2);
                            }else if (pLevel.getBlockState(pPos).isFaceSturdy(pLevel, pPos, Direction.UP)) {
                                pLevel.setBlock(pPos, blockState, 2);
                                pLevel.setBlock(pPos.above(), blockstate2, 2);
                            }

                        }else {
                            if (blockState.canSurvive(pLevel, blockpos$mutableblockpos)) {
                                pLevel.setBlock(blockpos, blockState, 3);
                            }
                        }
                    }

                }
            }
        }
    }
}
