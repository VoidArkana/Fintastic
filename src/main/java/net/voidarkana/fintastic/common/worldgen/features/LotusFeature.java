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

    public LotusFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        boolean flag = false;
        RandomSource randomsource = context.random();
        WorldGenLevel worldgenlevel = context.level();
        BlockPos blockpos = context.origin();
        int i = randomsource.nextInt(8) - randomsource.nextInt(8);
        int j = randomsource.nextInt(8) - randomsource.nextInt(8);
        int k = worldgenlevel.getHeight(Heightmap.Types.OCEAN_FLOOR, blockpos.getX() + i, blockpos.getZ() + j);
        BlockPos blockpos1 = new BlockPos(blockpos.getX() + i, k, blockpos.getZ() + j);

        if (worldgenlevel.getBlockState(blockpos1.above()).isAir() && (worldgenlevel.getBlockState(blockpos1).isAir()
                || worldgenlevel.getBlockState(blockpos1).getFluidState().is(Fluids.WATER))){
            Direction direction = switch (randomsource.nextInt(0, 4)) {
                case 1 -> Direction.EAST;
                case 2 -> Direction.WEST;
                case 3 -> Direction.NORTH;
                default -> Direction.SOUTH;};

            int amount = randomsource.nextInt(5) + 1;
            int maxFlowers = LotusPlantBlock.getMaxFlowers(amount);
            int flowers = randomsource.nextInt(maxFlowers+1);
            int actualFlowers = Math.clamp(maxFlowers, 0, flowers);

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

    protected void placePatchAround(WorldGenLevel level, RandomSource random, BlockPos pos, int xRadius, int zRadius) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.mutable();
        for(int x = -xRadius; x <= xRadius; ++x) {
            for(int z = -zRadius; z <= zRadius; ++z) {

                int currentRadius = Math.toIntExact(Math.round(Math.sqrt(Math.pow(z, 2) + Math.pow(x, 2))));

                if (currentRadius <= xRadius || currentRadius <= zRadius && random.nextInt(6)==0) {
                    blockpos$mutableblockpos.setWithOffset(pos, x, 0, z);

                    BlockState blockState;
                    BlockPos blockpos = blockpos$mutableblockpos.immutable();


                    if (random.nextInt(3)==0){
                        blockState = FintyBlocks.LOTUS_FLOWER.get().defaultBlockState().setValue(LotusFlowerBlock.FLOWERS, random.nextInt(1,4));
                    }else {
                        blockState = FintyBlocks.LOTUS_PAD.get().defaultBlockState().setValue(LotusPadBlock.FLOWER, random.nextInt(3)==0);
                    }

                    BlockState existingBlock = level.getBlockState(blockpos$mutableblockpos);
                    if (existingBlock.is(Blocks.AIR)){
                        if (random.nextInt(4)==0){

                            Direction direction = switch (random.nextInt(0, 4)) {
                                case 1 -> Direction.EAST;
                                case 2 -> Direction.WEST;
                                case 3 -> Direction.NORTH;
                                default -> Direction.SOUTH;};
                            int amount = random.nextInt(5) + 1;
                            int maxFlowers = LotusPlantBlock.getMaxFlowers(amount);
                            int flowers = random.nextInt(maxFlowers+1);
                            int actualFlowers = Math.clamp(maxFlowers, 0, flowers);

                            boolean fluidstate = level.getBlockState(pos.below()).getFluidState().is(Fluids.WATER);

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

                            if (level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP)
                                    && blockState.canSurvive(level, pos.below())) {
                                level.setBlock(pos.below(), blockState, 2);
                                level.setBlock(pos, blockstate2, 2);
                            }else if (level.getBlockState(pos).isFaceSturdy(level, pos, Direction.UP)
                                    && blockState.canSurvive(level, pos)) {
                                level.setBlock(pos, blockState, 2);
                                level.setBlock(pos.above(), blockstate2, 2);
                            }else {
                                if (random.nextInt(3)==0){
                                    blockState = FintyBlocks.LOTUS_FLOWER.get().defaultBlockState().setValue(LotusFlowerBlock.FLOWERS, random.nextInt(1,4));
                                }else {
                                    blockState = FintyBlocks.LOTUS_PAD.get().defaultBlockState().setValue(LotusPadBlock.FLOWER, random.nextInt(3)==0);
                                }

                                if (blockState.canSurvive(level, blockpos$mutableblockpos)) {
                                    level.setBlock(blockpos, blockState, 3);
                                }
                            }

                        }else {
                            if (blockState.canSurvive(level, blockpos$mutableblockpos)) {
                                level.setBlock(blockpos, blockState, 3);
                            }
                        }
                    }

                }
            }
        }
    }
}
