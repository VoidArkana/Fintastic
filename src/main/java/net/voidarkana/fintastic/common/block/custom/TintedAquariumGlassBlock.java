package net.voidarkana.fintastic.common.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TintedAquariumGlassBlock extends AquariumGlassBlock{
    public static final MapCodec<TintedAquariumGlassBlock> CODEC = simpleCodec(TintedAquariumGlassBlock::new);

    public TintedAquariumGlassBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull MapCodec<? extends TintedAquariumGlassBlock> codec() {
        return CODEC;
    }

    public boolean propagatesSkylightDown(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return false;
    }

    public int getLightBlock(@NotNull BlockState state, BlockGetter level, @NotNull BlockPos pos) {
        return level.getMaxLightLevel();
    }
}
