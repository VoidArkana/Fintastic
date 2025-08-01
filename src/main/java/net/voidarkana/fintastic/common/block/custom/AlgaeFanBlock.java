package net.voidarkana.fintastic.common.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseCoralFanBlock;
import net.minecraft.world.level.block.state.BlockState;

public class AlgaeFanBlock extends BaseCoralFanBlock {
    public AlgaeFanBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        if (!pLevel.isWaterAt(pPos)){
            return false;
        }
        return super.canSurvive(pState, pLevel, pPos);
    }
}
