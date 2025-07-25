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

        this.nonRotateablePillarBlock(YAFMBlocks.CUT_STROMATOLITE_BLOCK,
                "cut_stromatolite_block_top",
                "cut_stromatolite_block_side");

        this.nonRotateablePillarBlock(YAFMBlocks.FOSSIL_STROMATOLITE_BLOCK,
                "fossil_stromatolite_block_top",
                "fossil_stromatolite_block_side");

        simpleBlockWithItem(YAFMBlocks.STROMATOLITE_GROWTHS.get(), models().cross(blockTexture(YAFMBlocks.STROMATOLITE_GROWTHS.get()).getPath(),
                blockTexture(YAFMBlocks.STROMATOLITE_GROWTHS.get())).renderType("cutout"));

        simpleBlockWithItem(YAFMBlocks.FOSSIL_STROMATOLITE_GROWTHS.get(), models().cross(blockTexture(YAFMBlocks.FOSSIL_STROMATOLITE_GROWTHS.get()).getPath(),
                blockTexture(YAFMBlocks.FOSSIL_STROMATOLITE_GROWTHS.get())).renderType("cutout"));

        this.blockWithItem(YAFMBlocks.DEAD_LIVE_ROCK);
        this.blockWithItem(YAFMBlocks.DEAD_POROUS_LIVE_ROCK);

        blockWithItem(YAFMBlocks.STROMATOLITE_BRICKS);
        stairsBlock(((StairBlock) YAFMBlocks.STROMATOLITE_BRICKS_STAIRS.get()), blockTexture(YAFMBlocks.STROMATOLITE_BRICKS.get()));
        slabBlock(((SlabBlock) YAFMBlocks.STROMATOLITE_BRICKS_SLAB.get()), blockTexture(YAFMBlocks.STROMATOLITE_BRICKS.get()), blockTexture(YAFMBlocks.STROMATOLITE_BRICKS.get()));
        wallBlock((WallBlock) YAFMBlocks.STROMATOLITE_BRICKS_WALL.get(), blockTexture(YAFMBlocks.STROMATOLITE_BRICKS.get()));

        this.blockWithItem(YAFMBlocks.GREEN_ALGAE_BLOCK);
        this.blockWithItem(YAFMBlocks.RED_ALGAE_BLOCK);

        simpleBlockWithItem(YAFMBlocks.RED_ALGAE.get(), models().cross(blockTexture(YAFMBlocks.RED_ALGAE.get()).getPath(),
                blockTexture(YAFMBlocks.RED_ALGAE.get())).renderType("cutout"));

        simpleBlockWithItem(YAFMBlocks.DRAGONS_BREATH_ALGAE.get(), models().cross(blockTexture(YAFMBlocks.DRAGONS_BREATH_ALGAE.get()).getPath(),
                blockTexture(YAFMBlocks.DRAGONS_BREATH_ALGAE.get())).renderType("cutout"));

        simpleBlockWithItem(YAFMBlocks.CAULERPA.get(), models().cross(blockTexture(YAFMBlocks.CAULERPA.get()).getPath(),
                blockTexture(YAFMBlocks.CAULERPA.get())).renderType("cutout"));

        simpleBlockWithItem(YAFMBlocks.SEA_GRAPES.get(), models().cross(blockTexture(YAFMBlocks.SEA_GRAPES.get()).getPath(),
                blockTexture(YAFMBlocks.SEA_GRAPES.get())).renderType("cutout"));
        
        simpleBlockWithItem(YAFMBlocks.SEA_GRAPES_PLANT.get(), models().cross(blockTexture(YAFMBlocks.SEA_GRAPES_PLANT.get()).getPath(),
                blockTexture(YAFMBlocks.SEA_GRAPES_PLANT.get())).renderType("cutout"));


        nonRotateablePillarBlock(YAFMBlocks.RED_ALGAE_LIVE_ROCK,
                "red_algae_block", "porous_live_rock", "red_algae_live_rock");

        nonRotateablePillarBlock(YAFMBlocks.GREEN_ALGAE_LIVE_ROCK,
                "green_algae_block", "live_rock", "green_algae_live_rock");
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
