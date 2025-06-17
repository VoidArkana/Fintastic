package net.voidarkana.fintastic.util.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.block.YAFMBlocks;

public class YAFMBlockStateProvider extends BlockStateProvider {

    public YAFMBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Fintastic.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.nonRotateablePillarBlock(YAFMBlocks.STROMATOLITE_BLOCK,
                "stromatolite_block_top",
                "stromatolite_block_side");

        this.nonRotateablePillarBlock(YAFMBlocks.FOSSIL_STROMATOLITE_BLOCK,
                "fossil_stromatolite_block_top",
                "fossil_stromatolite_block_side");

        this.blockWithItem(YAFMBlocks.DEAD_LIVE_ROCK);

        this.blockWithItem(YAFMBlocks.DEAD_POROUS_LIVE_ROCK);

        blockWithItem(YAFMBlocks.STROMATOLITE_BRICKS);
        stairsBlock(((StairBlock) YAFMBlocks.STROMATOLITE_BRICKS_STAIRS.get()), blockTexture(YAFMBlocks.STROMATOLITE_BRICKS.get()));
        slabBlock(((SlabBlock) YAFMBlocks.STROMATOLITE_BRICKS_SLAB.get()), blockTexture(YAFMBlocks.STROMATOLITE_BRICKS.get()), blockTexture(YAFMBlocks.STROMATOLITE_BRICKS.get()));
        wallBlock((WallBlock) YAFMBlocks.STROMATOLITE_BRICKS_WALL.get(), blockTexture(YAFMBlocks.STROMATOLITE_BRICKS.get()));
    }

    private void leavesBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),
                models().singleTexture(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), new ResourceLocation("minecraft:block/leaves"),
                        "all", blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void blockItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(Fintastic.MOD_ID +
                ":block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ResourceLocation texture) {
        ModelFile sign = models().sign(name(signBlock), texture);
        hangingSignBlock(signBlock, wallSignBlock, sign);
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ModelFile sign) {
        simpleBlock(signBlock, sign);
        simpleBlock(wallSignBlock, sign);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject){
        simpleBlockWithItem(blockRegistryObject.get(),
                cubeAll(blockRegistryObject.get()));
    }

    private void nonRotateablePillarBlock(RegistryObject<Block> blockRegistryObject, String top, String bottom, String side){
        ResourceLocation rSide = new ResourceLocation(Fintastic.MOD_ID, "block/" + side);
        ResourceLocation rTop = new ResourceLocation(Fintastic.MOD_ID, "block/" + top);
        ResourceLocation rBottom = new ResourceLocation(Fintastic.MOD_ID, "block/" + bottom);

        simpleBlockWithItem(blockRegistryObject.get(),
                models().cubeBottomTop(name(blockRegistryObject.get()), rSide, rBottom, rTop));
    }

    private void nonRotateablePillarBlock(RegistryObject<Block> blockRegistryObject, String top_bottom, String side){
        ResourceLocation rSide = new ResourceLocation(Fintastic.MOD_ID, "block/" + side);
        ResourceLocation rTop = new ResourceLocation(Fintastic.MOD_ID, "block/" + top_bottom);
        ResourceLocation rBottom = new ResourceLocation(Fintastic.MOD_ID, "block/" + top_bottom);

        simpleBlockWithItem(blockRegistryObject.get(),
                models().cubeBottomTop(name(blockRegistryObject.get()), rSide, rBottom, rTop));
    }

    private String name(Block block) {
        return key(block).getPath();
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }
}
