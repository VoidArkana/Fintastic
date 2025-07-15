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
import net.voidarkana.fintastic.common.block.YAFMBlocks;
import net.voidarkana.fintastic.common.item.YAFMItems;

public class YAFMItemModelProvider extends ItemModelProvider {
    public YAFMItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Fintastic.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(YAFMItems.FISHING_HAT);

        withExistingParent(YAFMItems.FEATHERBACK_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        simpleItem(YAFMItems.FEATHERBACK_BUCKET);

        withExistingParent(YAFMItems.MINNOW_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        simpleItem(YAFMItems.MINNOW_BUCKET);

        withExistingParent(YAFMItems.CATFISH_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        simpleItem(YAFMItems.CATFISH_BUCKET);

        withExistingParent(YAFMItems.GUPPY_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        simpleItem(YAFMItems.GUPPY_BUCKET);

        withExistingParent(YAFMItems.FRESHWATER_SHARK_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        simpleItem(YAFMItems.FRESHWATER_SHARK_BUCKET);

        simpleItem(YAFMItems.REGULAR_FEED);
        simpleItem(YAFMItems.QUALITY_FEED);
        simpleItem(YAFMItems.GREAT_FEED);
        simpleItem(YAFMItems.PREMIUM_FEED);
        simpleItem(YAFMItems.BAD_FEED);

        simpleItem(YAFMItems.COOKED_FISH);
        simpleItem(YAFMItems.RAW_FISH);

        withExistingParent(YAFMItems.PLECO_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        simpleItem(YAFMItems.PLECO_BUCKET);

        withExistingParent(YAFMItems.ARAPAIMA_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        simpleItem(YAFMItems.ARAPAIMA_BUCKET);

        simpleItem(YAFMItems.DAPHNIA_BUCKET);
        withExistingParent(YAFMItems.DAPHNIA_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

        simpleItem(YAFMItems.ARTEMIA_BUCKET);
        withExistingParent(YAFMItems.ARTEMIA_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

        simpleItem(YAFMItems.MOONY_BUCKET);
        withExistingParent(YAFMItems.MOONY_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

        withExistingParent(YAFMItems.COELACANTH_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

        simpleItem(YAFMItems.SALTY_MUSIC_DISC);
        simpleItem(YAFMItems.FRESH_MUSIC_DISC);
        simpleItem(YAFMItems.AXOLOTL_MUSIC_DISC);
        simpleItem(YAFMItems.DRAGONFISH_MUSIC_DISC);
        simpleItem(YAFMItems.SHUNJI_MUSIC_DISC);

        evenSimplerBlockItem(YAFMBlocks.STROMATOLITE_BRICKS_SLAB);
        evenSimplerBlockItem(YAFMBlocks.STROMATOLITE_BRICKS_STAIRS);
        wallItem(YAFMBlocks.STROMATOLITE_BRICKS_WALL, YAFMBlocks.STROMATOLITE_BRICKS);

        simpleBlockItemBlockTexture(YAFMBlocks.STROMATOLITE_GROWTHS);
        simpleBlockItemBlockTexture(YAFMBlocks.FOSSIL_STROMATOLITE_GROWTHS);
        simpleBlockItemBlockTexture(YAFMBlocks.RED_ALGAE);
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
}
