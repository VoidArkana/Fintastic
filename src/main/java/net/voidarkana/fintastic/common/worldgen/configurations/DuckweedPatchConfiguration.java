package net.voidarkana.fintastic.common.worldgen.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record DuckweedPatchConfiguration(IntProvider xzRadius, float extraEdgeColumnChance) implements FeatureConfiguration {
    public static final Codec<DuckweedPatchConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            IntProvider.CODEC.fieldOf("xz_radius").forGetter((config)
                    -> config.xzRadius), Codec.floatRange(0.0F, 1.0F).fieldOf("extra_edge_column_chance").forGetter((config2)
                    -> config2.extraEdgeColumnChance)).apply(instance, DuckweedPatchConfiguration::new));
}
