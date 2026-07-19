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
import net.voidarkana.fintastic.common.block.custom.LotusPadBlock;
import net.voidarkana.fintastic.common.worldgen.configurations.DuckweedPatchConfiguration;
import net.voidarkana.fintastic.util.FintyTags;

import java.util.HashSet;
import java.util.Set;

public class DuckweedPatchFeature extends Feature<DuckweedPatchConfiguration> {

    public DuckweedPatchFeature(Codec<DuckweedPatchConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<DuckweedPatchConfiguration> context) {
        WorldGenLevel worldgenlevel = context.level();
        DuckweedPatchConfiguration vegetationpatchconfiguration = context.config();
        RandomSource randomsource = context.random();
        BlockPos blockpos = context.origin();
        if (worldgenlevel.getBiome(blockpos).is(FintyTags.Biomes.DUCKWEED_BLACKLISTED_BIOMES)){
            return false;
        }else {
            int i = vegetationpatchconfiguration.xzRadius().sample(randomsource) + randomsource.nextInt(1, 3);
            int j = vegetationpatchconfiguration.xzRadius().sample(randomsource) + randomsource.nextInt(1, 3);
            Set<BlockPos> set = this.placePatch(worldgenlevel, vegetationpatchconfiguration, randomsource, blockpos, i, j);
            return !set.isEmpty();
        }
    }

    protected Set<BlockPos> placePatch(WorldGenLevel level, DuckweedPatchConfiguration config, RandomSource random, BlockPos pos, int xRadius, int zRadius) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.mutable();
        Set<BlockPos> set = new HashSet<>();

        for(int x = -xRadius; x <= xRadius; ++x) {
            for(int z = -zRadius; z <= zRadius; ++z) {

                int currentRadius = Math.toIntExact(Math.round(Math.sqrt(Math.pow(z, 2) + Math.pow(x, 2))));

                if (currentRadius <= xRadius || currentRadius <= zRadius) {
                    blockpos$mutableblockpos.setWithOffset(pos, x, 0, z);
                    BlockState existingState = level.getBlockState(blockpos$mutableblockpos);

                    if (existingState.isAir() || existingState.is(FintyBlocks.DUCKWEED.get())){
                        int cellZ = Math.max(1, Math.abs(z));
                        int cellX = Math.max(1, Math.abs(x));
                        double mpowz = Math.pow(cellZ, 2);
                        double mpowx = Math.pow(cellX, 2);

                        double gauss_sharpness = 4.1; // less than your top limit
                        double gauss_width = 0.03;
                        int gauss_bottom_limit = 1;
                        double mexp = Math.exp(gauss_width*(-mpowx -mpowz));

                        int randomOffset = random.nextBoolean() ? -1 : 1;
                        int duckweedAmount = (int)Math.min(5, Math.round(mexp*gauss_sharpness + gauss_bottom_limit) + (x <= 1 || z <= 1 ? 0 : randomOffset));
                        if (duckweedAmount == 0 && random.nextBoolean()){
                            duckweedAmount = 1;
                        }
                        //Check why duckweed with fullness of 4 doesn't spawn

                        if (duckweedAmount != 0){
                            BlockState duckweed = FintyBlocks.DUCKWEED.get().defaultBlockState().setValue(DuckweedBlock.AMOUNT, duckweedAmount)
                                    .setValue(DuckweedBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random));

                            if (duckweed.canSurvive(level, blockpos$mutableblockpos)) {
                                BlockPos blockpos = blockpos$mutableblockpos.immutable();

                                if (level.getBlockState(blockpos$mutableblockpos).is(FintyBlocks.DUCKWEED.get())){
                                    BlockState newBlockstate = level.getBlockState(blockpos);
                                    int existingAmount = newBlockstate.getValue(DuckweedBlock.AMOUNT);

                                    level.setBlock(blockpos, newBlockstate.setValue(DuckweedBlock.AMOUNT, existingAmount == 5 ? 5 : Math.clamp((existingAmount + duckweedAmount) / 2, 1, 4)), 3);
                                }else {
                                    if (random.nextInt(100)<10 && duckweedAmount<3){
                                        duckweed = FintyBlocks.LOTUS_PAD.get().defaultBlockState().setValue(LotusPadBlock.FLOWER, random.nextBoolean());
                                    }
                                    level.setBlock(blockpos, duckweed, 3);
                                }

                                level.scheduleTick(blockpos, duckweed.getBlock(), 0);
                                level.scheduleTick(blockpos.north(), duckweed.getBlock(), 0);
                                level.scheduleTick(blockpos.south(), duckweed.getBlock(), 0);
                                level.scheduleTick(blockpos.east(), duckweed.getBlock(), 0);
                                level.scheduleTick(blockpos.west(), duckweed.getBlock(), 0);

                                set.add(blockpos);
                            }
                        }
                    }
                }
            }
        }

        return set;
    }
}
