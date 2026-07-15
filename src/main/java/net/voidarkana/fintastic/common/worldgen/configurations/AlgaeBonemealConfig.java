package net.voidarkana.fintastic.common.worldgen.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class AlgaeBonemealConfig extends BlockPileConfiguration {
    public static final Codec<AlgaeBonemealConfig> CODEC = RecordCodecBuilder.create((instance)
            -> instance.group(BlockStateProvider.CODEC.fieldOf("state_provider").forGetter((config)
            -> config.stateProvider), ExtraCodecs.POSITIVE_INT.fieldOf("spread_width").forGetter((config2)
            -> config2.spreadWidth), ExtraCodecs.POSITIVE_INT.fieldOf("spread_height").forGetter((config3)
            -> config3.spreadHeight)).apply(instance, AlgaeBonemealConfig::new));
    public final int spreadWidth;
    public final int spreadHeight;

    public AlgaeBonemealConfig(BlockStateProvider stateProvider, int spreadWidth, int spreadHeight) {
        super(stateProvider);
        this.spreadWidth = spreadWidth;
        this.spreadHeight = spreadHeight;
    }
}
