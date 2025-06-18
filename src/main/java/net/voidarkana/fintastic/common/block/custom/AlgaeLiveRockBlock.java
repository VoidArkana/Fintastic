package net.voidarkana.fintastic.common.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CoralBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.lighting.LightEngine;
import net.voidarkana.fintastic.common.block.YAFMBlocks;
import net.voidarkana.fintastic.common.worldgen.YAFMConfiguredFeatures;

public class AlgaeLiveRockBlock extends CoralBlock implements BonemealableBlock {

    public AlgaeLiveRockBlock(Block pDeadBlock, Properties pProperties) {
        super(pDeadBlock, pProperties);
    }

    private static boolean canHaveAlgae(BlockState pState, LevelReader pReader, BlockPos pPos) {
        BlockPos blockpos = pPos.above();
        BlockState blockstate = pReader.getBlockState(blockpos);
        int i = LightEngine.getLightBlockInto(pReader, pState, pPos, blockstate, blockpos, Direction.UP, blockstate.getLightBlock(pReader, blockpos));
        return i < pReader.getMaxLightLevel();
    }

    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!canHaveAlgae(pState, pLevel, pPos)) {
            pLevel.setBlockAndUpdate(pPos, pLevel.getBlockState(pPos).is(YAFMBlocks.GREEN_ALGAE_LIVE_ROCK.get())
                    ? YAFMBlocks.LIVE_ROCK.get().defaultBlockState() : YAFMBlocks.POROUS_LIVE_ROCK.get().defaultBlockState() );
        }

    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return pLevel.getBlockState(pPos.above()).is(Blocks.WATER);
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        BlockState blockstate = pLevel.getBlockState(pPos);
        BlockPos blockpos = pPos.above();
        ChunkGenerator chunkgenerator = pLevel.getChunkSource().getGenerator();
        Registry<ConfiguredFeature<?, ?>> registry = pLevel.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
        if (blockstate.is(YAFMBlocks.RED_ALGAE_LIVE_ROCK.get())) {
            this.place(registry, YAFMConfiguredFeatures.RED_ALGAE_VEGETATION_BONEMEAL, pLevel, chunkgenerator, pRandom, blockpos);
        } else if (blockstate.is(YAFMBlocks.GREEN_ALGAE_LIVE_ROCK.get())) {
            this.place(registry, YAFMConfiguredFeatures.GREEN_ALGAE_VEGETATION_BONEMEAL, pLevel, chunkgenerator, pRandom, blockpos);
        }
    }

    private void place(Registry<ConfiguredFeature<?, ?>> pFeatureRegistry, ResourceKey<ConfiguredFeature<?, ?>> pFeatureKey, ServerLevel pLevel, ChunkGenerator pChunkGenerator, RandomSource pRandom, BlockPos pPos) {
        pFeatureRegistry.getHolder(pFeatureKey).ifPresent((p_255920_) -> {
            p_255920_.value().place(pLevel, pChunkGenerator, pRandom, pPos);
        });
    }
}
