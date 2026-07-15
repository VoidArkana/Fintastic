package net.voidarkana.fintastic.common.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseCoralFanBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class AlgaeFanBlock extends BaseCoralFanBlock {
    public AlgaeFanBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, LevelReader level, @NotNull BlockPos pos) {
        if (!level.isWaterAt(pos)){
            return false;
        }
        return super.canSurvive(state, level, pos);
    }
}
