package net.voidarkana.fintastic.common.worldgen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.voidarkana.fintastic.common.block.FintyBlocks;
import net.voidarkana.fintastic.common.block.custom.HornwortBlock;

public class HornWortFeature extends Feature<NoneFeatureConfiguration> {

    public HornWortFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        boolean flag = false;
        RandomSource randomsource = context.random();
        WorldGenLevel worldgenlevel = context.level();
        BlockPos blockpos = context.origin();
        int i = randomsource.nextInt(8) - randomsource.nextInt(8);
        int j = randomsource.nextInt(8) - randomsource.nextInt(8);
        int k = worldgenlevel.getHeight(Heightmap.Types.OCEAN_FLOOR, blockpos.getX() + i, blockpos.getZ() + j);
        BlockPos blockpos1 = new BlockPos(blockpos.getX() + i, k, blockpos.getZ() + j);
        if (worldgenlevel.getBlockState(blockpos1).is(Blocks.WATER)) {
            //boolean flag1 = randomsource.nextDouble() < (double)probabilityfeatureconfiguration.probability;

            Direction direction = switch (randomsource.nextInt(0, 4)) {
                case 1 -> Direction.EAST;
                case 2 -> Direction.WEST;
                case 3 -> Direction.NORTH;
                default -> Direction.SOUTH;};

            BlockState blockstate = FintyBlocks.HORNWORT.get().defaultBlockState()
                    .setValue(HornwortBlock.AMOUNT, randomsource.nextInt(4) + 1)
                    .setValue(HornwortBlock.FACING, direction);

            if (blockstate.canSurvive(worldgenlevel, blockpos1)) {

                worldgenlevel.setBlock(blockpos1, blockstate, 2);

                flag = true;
            }
        }

        return flag;
    }
}
