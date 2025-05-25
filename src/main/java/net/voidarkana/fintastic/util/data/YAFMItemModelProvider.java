package net.voidarkana.fintastic.util.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
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

        simpleItem(YAFMItems.DAPHNIA_BUCKET);
        withExistingParent(YAFMItems.DAPHNIA_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

        simpleItem(YAFMItems.ARTEMIA_BUCKET);
        withExistingParent(YAFMItems.ARTEMIA_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

        simpleItem(YAFMItems.MOONY_BUCKET);
        withExistingParent(YAFMItems.MOONY_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

        simpleItem(YAFMItems.SALTY_MUSIC_DISC);
        simpleItem(YAFMItems.FRESH_MUSIC_DISC);
        simpleItem(YAFMItems.AXOLOTL_MUSIC_DISC);
        simpleItem(YAFMItems.DRAGONFISH_MUSIC_DISC);
        simpleItem(YAFMItems.SHUNJI_MUSIC_DISC);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item){
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Fintastic.MOD_ID, "item/" + item.getId().getPath()));
    }
}
