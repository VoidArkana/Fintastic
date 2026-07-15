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
import org.jetbrains.annotations.NotNull;

public class AlgaeBlock extends Block implements BonemealableBlock {

    ResourceKey<ConfiguredFeature<?, ?>> resourceKey;

    public AlgaeBlock(BlockBehaviour.Properties properties, ResourceKey<ConfiguredFeature<?, ?>> resourceKey) {
        super(properties);
        this.resourceKey = resourceKey;
    }

    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, @NotNull BlockState state) {
        return level.getFluidState(pos.above()).is(Fluids.WATER);
    }

    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    public void performBonemeal(ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        level.registryAccess().registry(Registries.CONFIGURED_FEATURE).flatMap((configuredFeatures) -> configuredFeatures.getHolder(this.resourceKey)).ifPresent((configuredFeatureReference) -> configuredFeatureReference.value().place(level, level.getChunkSource().getGenerator(), random, pos.above()));
    }
}
