package net.voidarkana.fintastic.util.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.block.FintyBlocks;
import net.voidarkana.fintastic.common.item.FintyItems;

public class FintyItemModelProvider extends ItemModelProvider {
    public FintyItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Fintastic.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(FintyItems.FISHING_HAT);

        withExistingParent(FintyItems.FEATHERBACK_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        simpleItem(FintyItems.FEATHERBACK_BUCKET);

        withExistingParent(FintyItems.MINNOW_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        simpleItem(FintyItems.MINNOW_BUCKET);

        withExistingParent(FintyItems.CATFISH_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        simpleItem(FintyItems.CATFISH_BUCKET);

        withExistingParent(FintyItems.GUPPY_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        simpleItem(FintyItems.GUPPY_BUCKET);

        withExistingParent(FintyItems.FRESHWATER_SHARK_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        simpleItem(FintyItems.FRESHWATER_SHARK_BUCKET);

        simpleItem(FintyItems.REGULAR_FEED);
        simpleItem(FintyItems.QUALITY_FEED);
        simpleItem(FintyItems.GREAT_FEED);
        simpleItem(FintyItems.PREMIUM_FEED);
        simpleItem(FintyItems.BAD_FEED);

        simpleItem(FintyItems.COOKED_FISH);
        simpleItem(FintyItems.RAW_FISH);

        withExistingParent(FintyItems.PLECO_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        simpleItem(FintyItems.PLECO_BUCKET);

        withExistingParent(FintyItems.ARAPAIMA_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        simpleItem(FintyItems.ARAPAIMA_BUCKET);

        simpleItem(FintyItems.DAPHNIA_BUCKET);
        withExistingParent(FintyItems.DAPHNIA_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

        simpleItem(FintyItems.ARTEMIA_BUCKET);
        withExistingParent(FintyItems.ARTEMIA_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

        simpleItem(FintyItems.MOONY_BUCKET);
        withExistingParent(FintyItems.MOONY_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

        simpleItem(FintyItems.COELACANTH_BUCKET);
        withExistingParent(FintyItems.COELACANTH_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

        simpleItem(FintyItems.GOURAMI_BUCKET);
        withExistingParent(FintyItems.GOURAMI_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

        simpleItem(FintyItems.SALTY_MUSIC_DISC);
        simpleItem(FintyItems.FRESH_MUSIC_DISC);
        simpleItem(FintyItems.AXOLOTL_MUSIC_DISC);
        simpleItem(FintyItems.DRAGONFISH_MUSIC_DISC);
        simpleItem(FintyItems.SHUNJI_MUSIC_DISC);

        evenSimplerBlockItem(FintyBlocks.STROMATOLITE_BRICKS_SLAB);
        evenSimplerBlockItem(FintyBlocks.STROMATOLITE_BRICKS_STAIRS);
        wallItem(FintyBlocks.STROMATOLITE_BRICKS_WALL, FintyBlocks.STROMATOLITE_BRICKS);

        simpleBlockItemBlockTexture(FintyBlocks.STROMATOLITE_GROWTHS);
        simpleBlockItemBlockTexture(FintyBlocks.FOSSIL_STROMATOLITE_GROWTHS);
        simpleBlockItemBlockTexture(FintyBlocks.RED_ALGAE);
        simpleBlockItemBlockTexture(FintyBlocks.DRAGONS_BREATH_ALGAE);
        simpleBlockItemBlockTexture(FintyBlocks.CAULERPA);

        simpleItem(FintyItems.SEA_GRAPE_SALAD);

        simpleBlockItem(FintyBlocks.SEA_GRAPES);
    }

    private ItemModelBuilder simpleBlockItemBlockTexture(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Fintastic.MOD_ID,"block/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item){
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Fintastic.MOD_ID, "item/" + item.getId().getPath()));
    }

    public void evenSimplerBlockItem(RegistryObject<Block> block) {
        this.withExistingParent(Fintastic.MOD_ID + ":" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
    }
    
    public void wallItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall", new ResourceLocation(Fintastic.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    private ItemModelBuilder simpleBlockItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Fintastic.MOD_ID,"item/" + item.getId().getPath()));
    }
}
