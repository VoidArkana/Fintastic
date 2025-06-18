package net.voidarkana.fintastic.common.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CoralBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.voidarkana.fintastic.common.block.YAFMBlocks;
import net.voidarkana.fintastic.util.YAFMTags;

public class LiveRockBlock extends CoralBlock implements BonemealableBlock {

    public LiveRockBlock(Block pDeadBlock, Properties pProperties) {
        super(pDeadBlock, pProperties);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        if (pLevel.getBlockState(pPos.above()).getFluidState().is(Fluids.WATER)
                || pLevel.getBlockState(pPos.above()).getFluidState().is(Fluids.FLOWING_WATER)) {
            
            for (BlockPos blockpos : BlockPos.betweenClosed(pPos.offset(-1, -1, -1), pPos.offset(1, 1, 1))) {
                if (pLevel.getBlockState(pPos).is(YAFMBlocks.POROUS_LIVE_ROCK.get()) &&
                        pLevel.getBlockState(blockpos).is(YAFMTags.Blocks.RED_ALGAE)) {
                    return true;
                }

                if (pLevel.getBlockState(pPos).is(YAFMBlocks.LIVE_ROCK.get()) &&
                        pLevel.getBlockState(blockpos).is(YAFMTags.Blocks.GREEN_ALGAE)) {
                    return true;
                }
            }

        }
        return false;
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        boolean flag = false;
        boolean flag1 = false;

        for(BlockPos blockpos : BlockPos.betweenClosed(pPos.offset(-1, -1, -1), pPos.offset(1, 1, 1))) {
            BlockState blockstate = pLevel.getBlockState(blockpos);
            if (pLevel.getBlockState(pPos).is(YAFMBlocks.POROUS_LIVE_ROCK.get()) &&
                    blockstate.is(YAFMTags.Blocks.RED_ALGAE)) {
                flag1 = true;
            }

            if (pLevel.getBlockState(pPos).is(YAFMBlocks.LIVE_ROCK.get()) &&
                    blockstate.is(YAFMTags.Blocks.GREEN_ALGAE)) {
                flag = true;
            }

            if (flag1 || flag) {
                break;
            }
        }

        if (flag1) {
            pLevel.setBlock(pPos, YAFMBlocks.RED_ALGAE_LIVE_ROCK.get().defaultBlockState(), 3);
        } else if (flag) {
            pLevel.setBlock(pPos, YAFMBlocks.GREEN_ALGAE_LIVE_ROCK.get().defaultBlockState(), 3);
        }

    }
}
