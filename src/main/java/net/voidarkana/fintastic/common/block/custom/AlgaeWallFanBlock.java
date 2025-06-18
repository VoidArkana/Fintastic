package net.voidarkana.fintastic.common.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.state.BlockState;

public class AlgaeWallFanBlock extends BaseCoralWallFanBlock {
    public AlgaeWallFanBlock(Properties pProperties) {
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
