package net.voidarkana.fintastic.common.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class LiveRockBoulderConfig implements FeatureConfiguration {

    public static final Codec<LiveRockBoulderConfig> CODEC = RecordCodecBuilder.create((configInstance) -> {
        return configInstance.group(BlockStateProvider.CODEC.fieldOf("grass_state_1").forGetter((config) -> {
            return config.grassState1;
        }),BlockStateProvider.CODEC.fieldOf("grass_state_2").forGetter((config2) -> {
            return config2.grassState2;
        }), Codec.floatRange(0.0F, 1.0F).fieldOf("vegetation_chance").forGetter((config4) -> {
            return config4.vegetationChance;
        }), PlacedFeature.CODEC.fieldOf("vegetation_feature1").forGetter((p_204867_) -> {
            return p_204867_.vegetationFeature1;
        }), PlacedFeature.CODEC.fieldOf("vegetation_feature2").forGetter((p_204867_) -> {
            return p_204867_.vegetationFeature2;
        })).apply(configInstance, LiveRockBoulderConfig::new);
    });

    public final BlockStateProvider grassState1;
    public final BlockStateProvider grassState2;
    public final float vegetationChance;
    public final Holder<PlacedFeature> vegetationFeature1;
    public final Holder<PlacedFeature> vegetationFeature2;

    public LiveRockBoulderConfig(BlockStateProvider grassState1, BlockStateProvider grassState2, float vegetationChance, Holder<PlacedFeature> vegetationFeature1, Holder<PlacedFeature> vegetationFeature2) {
        this.grassState1 = grassState1;
        this.grassState2 = grassState2;
        this.vegetationChance = vegetationChance;
        this.vegetationFeature1 = vegetationFeature1;
        this.vegetationFeature2 = vegetationFeature2;
    }
}
