package net.voidarkana.fintastic.common.worldgen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.material.Fluids;

import java.util.HashSet;
import java.util.Set;

public class AmphibiousGroundCircleFeature extends Feature<VegetationPatchConfiguration>{

    public AmphibiousGroundCircleFeature(Codec<VegetationPatchConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<VegetationPatchConfiguration> context) {
        WorldGenLevel worldgenlevel = context.level();
        VegetationPatchConfiguration vegetationpatchconfiguration = context.config();
        RandomSource randomsource = context.random();
        BlockPos blockpos = context.origin();
        Set<BlockPos> set = this.replaceGround(context, blockpos);
        this.distributeVegetation(context, worldgenlevel, vegetationpatchconfiguration, randomsource, set);
        return !set.isEmpty();
    }

    public Set<BlockPos> replaceGround(FeaturePlaceContext<VegetationPatchConfiguration> context, BlockPos pos) {

        Set<BlockPos> set = new HashSet<>();

        this.placeCircle(context, pos.west().north());
        this.placeCircle(context, pos.east(2).north());
        this.placeCircle(context, pos.west().south(2));
        this.placeCircle(context, pos.east(2).south(2));

        for(int j = 0; j < 5; ++j) {
            int k = context.random().nextInt(64);
            int l = k % 8;
            int i1 = k / 8;
            if (l == 0 || l == 7 || i1 == 0 || i1 == 7) {
                set.addAll(this.placeCircle(context, pos.offset(-3 + l, -1, -3 + i1)));
            }
        }

        return set;
    }

    private Set<BlockPos> placeCircle(FeaturePlaceContext<VegetationPatchConfiguration> context, BlockPos pos) {

        Set<BlockPos> set = new HashSet<>();

        for(int i = -2; i <= 2; ++i) {
            for(int j = -2; j <= 2; ++j) {
                if (Math.abs(i) != 2 || Math.abs(j) != 2) {
                    set.addAll(this.placeGroundAt(context, pos.offset(i, 0, j)));
                }
            }
        }

        return set;
    }

    private Set<BlockPos> placeGroundAt(FeaturePlaceContext<VegetationPatchConfiguration> context, BlockPos pos) {

        Set<BlockPos> set = new HashSet<>();

        VegetationPatchConfiguration vegetationpatchconfiguration = context.config();
        WorldGenLevel worldgenlevel = context.level();
        BlockStateProvider provider = BlockStateProvider.simple(vegetationpatchconfiguration.groundState.getState(context.random(), pos));

        for(int i = 2; i >= -3; --i) {
            BlockPos blockpos = pos.above(i);
            if (worldgenlevel.getBlockState(blockpos).is(vegetationpatchconfiguration.replaceable)) {
                this.setBlock(context.level(), blockpos, provider.getState(context.random(), pos));
                set.add(blockpos);
                break;
            }

            if ((!(context.level().isStateAtPosition(pos, BlockBehaviour.BlockStateBase::isAir)) ||
                    !context.level().isFluidAtPosition(pos, (fluidState) -> {
                        return fluidState.isSourceOfType(Fluids.WATER);
                    })) && i < 0) {
                break;
            }
        }

        return set;
    }

    protected void distributeVegetation(FeaturePlaceContext<VegetationPatchConfiguration> context, WorldGenLevel level, VegetationPatchConfiguration config, RandomSource random, Set<BlockPos> possiblePositions) {
        for(BlockPos blockpos : possiblePositions) {
            if (config.vegetationChance > 0.0F && random.nextFloat() < config.vegetationChance) {
                this.placeVegetation(level, config, context.chunkGenerator(), random, blockpos);
            }
        }

    }

    protected void placeVegetation(WorldGenLevel level, VegetationPatchConfiguration config, ChunkGenerator chunkGenerator, RandomSource random, BlockPos pos) {
        config.vegetationFeature.value().place(level, chunkGenerator, random, pos.relative(Direction.UP));
    }
}
