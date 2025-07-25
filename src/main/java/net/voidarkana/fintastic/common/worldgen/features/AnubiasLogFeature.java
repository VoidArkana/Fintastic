package net.voidarkana.fintastic.common.worldgen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.voidarkana.fintastic.common.block.YAFMBlocks;
import net.voidarkana.fintastic.common.block.custom.AnubiasBlock;
import net.voidarkana.fintastic.common.block.custom.HornwortBlock;

public class AnubiasLogFeature extends Feature<NoneFeatureConfiguration> {

    public AnubiasLogFeature(Codec<NoneFeatureConfiguration> p_66754_) {
        super(p_66754_);
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> pContext) {
        boolean flag = false;
        RandomSource randomsource = pContext.random();
        WorldGenLevel worldgenlevel = pContext.level();
        BlockPos blockpos = pContext.origin();
        int i = randomsource.nextInt(8) - randomsource.nextInt(8);
        int j = randomsource.nextInt(8) - randomsource.nextInt(8);
        int k = worldgenlevel.getHeight(Heightmap.Types.OCEAN_FLOOR, blockpos.getX() + i, blockpos.getZ() + j);
        BlockPos blockpos1 = new BlockPos(blockpos.getX() + i, k, blockpos.getZ() + j);
        if (worldgenlevel.getBlockState(blockpos1).is(Blocks.WATER) && worldgenlevel.getBlockState(blockpos1.above()).is(Blocks.WATER)) {

            Direction direction = switch (randomsource.nextInt(0, 4)) {
                case 1 -> Direction.EAST;
                case 2 -> Direction.WEST;
                case 3 -> Direction.NORTH;
                default -> Direction.SOUTH;};

            BlockState logState = Blocks.JUNGLE_LOG.defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, direction.getAxis());

            worldgenlevel.setBlock(blockpos1, logState, 2);

            BlockState blockstate = YAFMBlocks.ANUBIAS.get().defaultBlockState()
                    .setValue(AnubiasBlock.FACING, direction)
                    .setValue(AnubiasBlock.FACE, AttachFace.FLOOR);

            if (blockstate.canSurvive(worldgenlevel, blockpos1.above())) {
                worldgenlevel.setBlock(blockpos1.above(), blockstate, 2);
                flag = true;
            }

            if (randomsource.nextInt(0, 3) > 0){

                worldgenlevel.setBlock(blockpos1.relative(direction), logState, 2);

                if (randomsource.nextInt(0, 3) > 0){
                    Direction clockwise = randomsource.nextBoolean() ? direction.getClockWise() : direction.getCounterClockWise();

                    BlockState blockstate2 = YAFMBlocks.ANUBIAS.get().defaultBlockState()
                            .setValue(AnubiasBlock.FACING, clockwise)
                            .setValue(AnubiasBlock.FACE, AttachFace.WALL);

                    if (blockstate.canSurvive(worldgenlevel, blockpos1.relative(direction).relative(clockwise))) {
                        worldgenlevel.setBlock(blockpos1.relative(direction).relative(clockwise), blockstate2, 2);
                        flag = true;
                    }
                }
            }
            if (randomsource.nextInt(0, 3) > 0){
                worldgenlevel.setBlock(blockpos1.relative(direction.getOpposite()), logState, 2);

                if (randomsource.nextInt(0, 3) > 0){
                    Direction clockwise = randomsource.nextBoolean() ? direction.getClockWise() : direction.getCounterClockWise();

                    BlockState blockstate2 = YAFMBlocks.ANUBIAS.get().defaultBlockState()
                            .setValue(AnubiasBlock.FACING, clockwise)
                            .setValue(AnubiasBlock.FACE, AttachFace.WALL);

                    if (blockstate.canSurvive(worldgenlevel, blockpos1.relative(direction).relative(clockwise))) {
                        worldgenlevel.setBlock(blockpos1.relative(direction).relative(clockwise), blockstate2, 2);
                        flag = true;
                    }
                }
            }
        }

        return flag;
    }
}
