package net.voidarkana.fintastic.common.worldgen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class UnderwaterWaterloggedVegetationPatchFeature extends UnderwaterVegetationPatchFeature {
    public UnderwaterWaterloggedVegetationPatchFeature(Codec<VegetationPatchConfiguration> codec) {
        super(codec);
    }

    protected Set<BlockPos> placeGroundPatch(WorldGenLevel level, VegetationPatchConfiguration config, RandomSource random, BlockPos pos, Predicate<BlockState> state, int xRadius, int zRadius) {
        Set<BlockPos> set = super.placeGroundPatch(level, config, random, pos, state, xRadius, zRadius);
        Set<BlockPos> set1 = new HashSet<>();
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for(BlockPos blockpos : set) {
            if (!isExposed(level, set, blockpos, blockpos$mutableblockpos)) {
                set1.add(blockpos);
            }
        }

        for(BlockPos blockpos1 : set1) {
            level.setBlock(blockpos1, Blocks.WATER.defaultBlockState(), 2);
        }

        return set1;
    }

    private static boolean isExposed(WorldGenLevel level, Set<BlockPos> positions, BlockPos pos, BlockPos.MutableBlockPos mutablePos) {
        return isExposedDirection(level, pos, mutablePos, Direction.NORTH) || isExposedDirection(level, pos, mutablePos, Direction.EAST) || isExposedDirection(level, pos, mutablePos, Direction.SOUTH) || isExposedDirection(level, pos, mutablePos, Direction.WEST) || isExposedDirection(level, pos, mutablePos, Direction.DOWN);
    }

    private static boolean isExposedDirection(WorldGenLevel level, BlockPos pos, BlockPos.MutableBlockPos mutablePos, Direction direction) {
        mutablePos.setWithOffset(pos, direction);
        return !level.getBlockState(mutablePos).isFaceSturdy(level, mutablePos, direction.getOpposite());
    }

    protected boolean placeVegetation(WorldGenLevel level, VegetationPatchConfiguration config, ChunkGenerator chunkGenerator, RandomSource random, BlockPos pos) {
        if (super.placeVegetation(level, config, chunkGenerator, random, pos.below())) {
            BlockState blockstate = level.getBlockState(pos);
            if (blockstate.hasProperty(BlockStateProperties.WATERLOGGED) && !blockstate.getValue(BlockStateProperties.WATERLOGGED)) {
                level.setBlock(pos, blockstate.setValue(BlockStateProperties.WATERLOGGED, Boolean.TRUE), 2);
            }

            return true;
        } else {
            return false;
        }
    }
}
