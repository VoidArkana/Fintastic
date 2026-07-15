package net.voidarkana.fintastic.common.worldgen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class UnderwaterVegetationPatchFeature extends Feature<VegetationPatchConfiguration> {

    public UnderwaterVegetationPatchFeature(Codec<VegetationPatchConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<VegetationPatchConfiguration> context) {
        WorldGenLevel worldgenlevel = context.level();
        VegetationPatchConfiguration vegetationpatchconfiguration = context.config();
        RandomSource randomsource = context.random();
        BlockPos blockpos = context.origin();
        Predicate<BlockState> predicate = (blockState) -> blockState.is(vegetationpatchconfiguration.replaceable);
        int i = vegetationpatchconfiguration.xzRadius.sample(randomsource) + 1;
        int j = vegetationpatchconfiguration.xzRadius.sample(randomsource) + 1;
        Set<BlockPos> set = this.placeGroundPatch(worldgenlevel, vegetationpatchconfiguration, randomsource, blockpos, predicate, i, j);
        this.distributeVegetation(context, worldgenlevel, vegetationpatchconfiguration, randomsource, set, i, j);
        return !set.isEmpty();
    }

    protected Set<BlockPos> placeGroundPatch(WorldGenLevel level, VegetationPatchConfiguration config, RandomSource random, BlockPos pos, Predicate<BlockState> state, int xRadius, int zRadius) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.mutable();
        BlockPos.MutableBlockPos blockpos$mutableblockpos1 = blockpos$mutableblockpos.mutable();
        Direction direction = Direction.DOWN;
        Direction direction1 = direction.getOpposite();
        Set<BlockPos> set = new HashSet<>();

        for(int i = -xRadius; i <= xRadius; ++i) {
            boolean isXEdge = i == -xRadius || i == xRadius;

            for(int j = -zRadius; j <= zRadius; ++j) {
                boolean isZEdge = j == -zRadius || j == zRadius;
                boolean isAnyEdge = isXEdge || isZEdge;
                boolean isBothEdge = isXEdge && isZEdge;
                boolean isOneEdge = isAnyEdge && !isBothEdge;
                if (!isBothEdge && (!isOneEdge || config.extraEdgeColumnChance != 0.0F && !(random.nextFloat() > config.extraEdgeColumnChance))) {
                    blockpos$mutableblockpos.setWithOffset(pos, i, 0, j);

                    for(int k = 0; level.isStateAtPosition(blockpos$mutableblockpos, (blockState) -> blockState.is(Blocks.WATER)) && k < config.verticalRange; ++k) {

                        blockpos$mutableblockpos.move(direction);
                    }

                    for(int i1 = 0; level.isStateAtPosition(blockpos$mutableblockpos, (blockState) -> !blockState.is(Blocks.WATER)) && i1 < config.verticalRange; ++i1) {

                        blockpos$mutableblockpos.move(direction1);
                    }

                    blockpos$mutableblockpos1.setWithOffset(blockpos$mutableblockpos, Direction.DOWN);
                    BlockState blockstate = level.getBlockState(blockpos$mutableblockpos1);
                    if ((level.isEmptyBlock(blockpos$mutableblockpos) || level.getBlockState(blockpos$mutableblockpos).is(Blocks.WATER))
                            && blockstate.isFaceSturdy(level, blockpos$mutableblockpos1, Direction.DOWN.getOpposite())) {
                        int l = config.depth.sample(random) + (config.extraBottomBlockChance > 0.0F && random.nextFloat() < config.extraBottomBlockChance ? 1 : 0);
                        BlockPos blockpos = blockpos$mutableblockpos1.immutable();
                        boolean flag5 = this.placeGround(level, config, state, random, blockpos$mutableblockpos1, l);
                        if (flag5) {
                            set.add(blockpos);
                        }
                    }
                }
            }
        }

        return set;
    }

    protected void distributeVegetation(FeaturePlaceContext<VegetationPatchConfiguration> context, WorldGenLevel level, VegetationPatchConfiguration config, RandomSource random, Set<BlockPos> possiblePositions, int xRadius, int zRadius) {
        for(BlockPos blockpos : possiblePositions) {
            if (config.vegetationChance > 0.0F && random.nextFloat() < config.vegetationChance) {
                this.placeVegetation(level, config, context.chunkGenerator(), random, blockpos);
            }
        }

    }

    protected boolean placeVegetation(WorldGenLevel level, VegetationPatchConfiguration config, ChunkGenerator chunkGenerator, RandomSource random, BlockPos pos) {
        return config.vegetationFeature.value().place(level, chunkGenerator, random, pos.relative(Direction.DOWN.getOpposite()));
    }

    protected boolean placeGround(WorldGenLevel level, VegetationPatchConfiguration config, Predicate<BlockState> replaceableblocks, RandomSource random, BlockPos.MutableBlockPos mutablePos, int maxDistance) {
        for(int i = 0; i < maxDistance; ++i) {
            BlockState blockstate = config.groundState.getState(random, mutablePos);
            BlockState blockstate1 = level.getBlockState(mutablePos);
            if (!blockstate.is(blockstate1.getBlock())) {
                if (!replaceableblocks.test(blockstate1)) {
                    return i != 0;
                }

                level.setBlock(mutablePos, blockstate, 2);
                mutablePos.move(Direction.DOWN);
            }
        }

        return true;
    }
}
