package net.voidarkana.fintastic.common.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.Fluids;
import net.voidarkana.fintastic.common.block.FintyBlocks;
import net.voidarkana.fintastic.common.worldgen.FintyConfiguredFeatures;

public class AlgaeBlock extends Block implements BonemealableBlock {

    ResourceKey<ConfiguredFeature<?, ?>> resourceKey;

    public AlgaeBlock(BlockBehaviour.Properties pProperties, ResourceKey<ConfiguredFeature<?, ?>> pResourceKey) {
        super(pProperties);
        this.resourceKey = pResourceKey;
    }

    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return pLevel.getFluidState(pPos.above()).is(Fluids.WATER);
    }

    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        pLevel.registryAccess().registry(Registries.CONFIGURED_FEATURE).flatMap((configuredFeatures) -> {
            return configuredFeatures.getHolder(this.resourceKey);
        }).ifPresent((configuredFeatureReference) -> {
            configuredFeatureReference.value().place(pLevel, pLevel.getChunkSource().getGenerator(), pRandom, pPos.above());
        });
    }
}
