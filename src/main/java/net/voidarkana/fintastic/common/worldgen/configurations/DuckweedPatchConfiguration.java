package net.voidarkana.fintastic.common.worldgen.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class DuckweedPatchConfiguration implements FeatureConfiguration {
    public static final Codec<DuckweedPatchConfiguration> CODEC = RecordCodecBuilder.create((p_161304_) -> {
        return p_161304_.group(
        IntProvider.CODEC.fieldOf("xz_radius").forGetter((p_161308_) -> {
            return p_161308_.xzRadius;
        }), Codec.floatRange(0.0F, 1.0F).fieldOf("extra_edge_column_chance").forGetter((p_161306_) -> {
            return p_161306_.extraEdgeColumnChance;
        })).apply(p_161304_, DuckweedPatchConfiguration::new);
    });
    public final IntProvider xzRadius;
    public final float extraEdgeColumnChance;

    public DuckweedPatchConfiguration(IntProvider radius, float extraEdgeColumnChance) {
        this.xzRadius = radius;
        this.extraEdgeColumnChance = extraEdgeColumnChance;
    }
}
