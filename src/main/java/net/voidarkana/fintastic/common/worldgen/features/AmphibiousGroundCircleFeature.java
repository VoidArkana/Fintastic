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

    public AmphibiousGroundCircleFeature(Codec<VegetationPatchConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<VegetationPatchConfiguration> pContext) {
        WorldGenLevel worldgenlevel = pContext.level();
        VegetationPatchConfiguration vegetationpatchconfiguration = pContext.config();
        RandomSource randomsource = pContext.random();
        BlockPos blockpos = pContext.origin();
        Set<BlockPos> set = this.replaceGround(pContext, blockpos);
        this.distributeVegetation(pContext, worldgenlevel, vegetationpatchconfiguration, randomsource, set);
        return !set.isEmpty();
    }

    public Set<BlockPos> replaceGround(FeaturePlaceContext<VegetationPatchConfiguration> pContext, BlockPos pos) {

        Set<BlockPos> set = new HashSet<>();

        this.placeCircle(pContext, pos.west().north());
        this.placeCircle(pContext, pos.east(2).north());
        this.placeCircle(pContext, pos.west().south(2));
        this.placeCircle(pContext, pos.east(2).south(2));

        for(int j = 0; j < 5; ++j) {
            int k = pContext.random().nextInt(64);
            int l = k % 8;
            int i1 = k / 8;
            if (l == 0 || l == 7 || i1 == 0 || i1 == 7) {
                set.addAll(this.placeCircle(pContext, pos.offset(-3 + l, -1, -3 + i1)));
            }
        }

        return set;
    }

    private Set<BlockPos> placeCircle(FeaturePlaceContext<VegetationPatchConfiguration> pContext, BlockPos pPos) {

        Set<BlockPos> set = new HashSet<>();

        for(int i = -2; i <= 2; ++i) {
            for(int j = -2; j <= 2; ++j) {
                if (Math.abs(i) != 2 || Math.abs(j) != 2) {
                    set.addAll(this.placeGroundAt(pContext, pPos.offset(i, 0, j)));
                }
            }
        }

        return set;
    }

    private Set<BlockPos> placeGroundAt(FeaturePlaceContext<VegetationPatchConfiguration> pContext, BlockPos pPos) {

        Set<BlockPos> set = new HashSet<>();

        VegetationPatchConfiguration vegetationpatchconfiguration = pContext.config();
        WorldGenLevel worldgenlevel = pContext.level();
        BlockStateProvider provider = BlockStateProvider.simple(vegetationpatchconfiguration.groundState.getState(pContext.random(), pPos));

        for(int i = 2; i >= -3; --i) {
            BlockPos blockpos = pPos.above(i);
            if (worldgenlevel.getBlockState(blockpos).is(vegetationpatchconfiguration.replaceable)) {
                this.setBlock(pContext.level(), blockpos, net.minecraftforge.event.ForgeEventFactory.alterGround(pContext.level(), pContext.random(), blockpos, provider.getState(pContext.random(), pPos)));
                set.add(blockpos);
                break;
            }

            if ((!(pContext.level().isStateAtPosition(pPos, BlockBehaviour.BlockStateBase::isAir)) ||
                    !pContext.level().isFluidAtPosition(pPos, (p_225638_) -> {
                        return p_225638_.isSourceOfType(Fluids.WATER);
                    })) && i < 0) {
                break;
            }
        }

        return set;
    }

    protected void distributeVegetation(FeaturePlaceContext<VegetationPatchConfiguration> pContext, WorldGenLevel pLevel, VegetationPatchConfiguration pConfig, RandomSource pRandom, Set<BlockPos> pPossiblePositions) {
        for(BlockPos blockpos : pPossiblePositions) {
            if (pConfig.vegetationChance > 0.0F && pRandom.nextFloat() < pConfig.vegetationChance) {
                this.placeVegetation(pLevel, pConfig, pContext.chunkGenerator(), pRandom, blockpos);
            }
        }

    }

    protected void placeVegetation(WorldGenLevel pLevel, VegetationPatchConfiguration pConfig, ChunkGenerator pChunkGenerator, RandomSource pRandom, BlockPos pPos) {
        pConfig.vegetationFeature.value().place(pLevel, pChunkGenerator, pRandom, pPos.relative(Direction.UP));
    }
}
