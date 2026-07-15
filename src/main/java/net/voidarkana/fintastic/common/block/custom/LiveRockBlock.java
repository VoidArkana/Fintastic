package net.voidarkana.fintastic.common.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CoralBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.voidarkana.fintastic.common.block.FintyBlocks;
import net.voidarkana.fintastic.util.FintyTags;
import org.jetbrains.annotations.NotNull;

public class LiveRockBlock extends CoralBlock implements BonemealableBlock {

    public LiveRockBlock(Block deadBlock, Properties properties) {
        super(deadBlock, properties);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, @NotNull BlockState state) {
        if (level.getBlockState(pos.above()).getFluidState().is(Fluids.WATER)
                || level.getBlockState(pos.above()).getFluidState().is(Fluids.FLOWING_WATER)) {
            
            for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1))) {
                if (level.getBlockState(pos).is(FintyBlocks.POROUS_LIVE_ROCK.get()) &&
                        level.getBlockState(blockpos).is(FintyTags.Blocks.RED_ALGAE)) {
                    return true;
                }

                if (level.getBlockState(pos).is(FintyBlocks.LIVE_ROCK.get()) &&
                        level.getBlockState(blockpos).is(FintyTags.Blocks.GREEN_ALGAE)) {
                    return true;
                }
            }

        }
        return false;
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, BlockPos pos, @NotNull BlockState state) {
        boolean flag = false;
        boolean flag1 = false;

        for(BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1))) {
            BlockState blockstate = level.getBlockState(blockpos);
            if (level.getBlockState(pos).is(FintyBlocks.POROUS_LIVE_ROCK.get()) &&
                    blockstate.is(FintyTags.Blocks.RED_ALGAE)) {
                flag1 = true;
            }

            if (level.getBlockState(pos).is(FintyBlocks.LIVE_ROCK.get()) &&
                    blockstate.is(FintyTags.Blocks.GREEN_ALGAE)) {
                flag = true;
            }

            if (flag1 || flag) {
                break;
            }
        }

        if (flag1) {
            level.setBlock(pos, FintyBlocks.RED_ALGAE_LIVE_ROCK.get().defaultBlockState(), 3);
        } else if (flag) {
            level.setBlock(pos, FintyBlocks.GREEN_ALGAE_LIVE_ROCK.get().defaultBlockState(), 3);
        }

    }
}
