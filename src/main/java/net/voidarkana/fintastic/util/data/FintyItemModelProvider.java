package net.voidarkana.fintastic.util.data;

import java.util.function.Supplier;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.minecraft.core.registries.BuiltInRegistries;
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

        simpleItem(FintyItems.FEATHERBACK_SPAWN_EGG);
        simpleItem(FintyItems.FEATHERBACK_BUCKET);

        simpleItem(FintyItems.MINNOW_SPAWN_EGG);
        simpleItem(FintyItems.MINNOW_BUCKET);
        simpleItem(FintyItems.MINNOW);

        simpleItem(FintyItems.CATFISH_SPAWN_EGG);
        simpleItem(FintyItems.CATFISH_BUCKET);
        simpleItem(FintyItems.CATFISH);

        simpleItem(FintyItems.GUPPY_SPAWN_EGG);
        simpleItem(FintyItems.GUPPY_BUCKET);
        simpleItem(FintyItems.GUPPY);

        simpleItem(FintyItems.FRESHWATER_SHARK_SPAWN_EGG);
        simpleItem(FintyItems.FRESHWATER_SHARK_BUCKET);
        simpleItem(FintyItems.SHARKMINNOW);

        simpleItem(FintyItems.REGULAR_FEED);
        simpleItem(FintyItems.QUALITY_FEED);
        simpleItem(FintyItems.GREAT_FEED);
        simpleItem(FintyItems.PREMIUM_FEED);
        simpleItem(FintyItems.BAD_FEED);

        simpleItem(FintyItems.COOKED_FISH);
        simpleItem(FintyItems.RAW_FISH);

        simpleItem(FintyItems.PLECO_SPAWN_EGG);
        simpleItem(FintyItems.PLECO_BUCKET);
        simpleItem(FintyItems.PLECO);

        simpleItem(FintyItems.ARAPAIMA_SPAWN_EGG);
        simpleItem(FintyItems.ARAPAIMA_BUCKET);

        simpleItem(FintyItems.DAPHNIA_BUCKET);
        simpleItem(FintyItems.DAPHNIA_SPAWN_EGG);
        simpleItem(FintyItems.DAPHNIA);

        simpleItem(FintyItems.ARTEMIA_BUCKET);
        simpleItem(FintyItems.FAIRY_SHRIMP_SPAWN_EGG);
        simpleItem(FintyItems.FAIRY_SHRIMP);

        simpleItem(FintyItems.MOONY_BUCKET);
        simpleItem(FintyItems.MOONY_SPAWN_EGG);
        simpleItem(FintyItems.MOONY);

        simpleItem(FintyItems.COELACANTH_BUCKET);
        simpleItem(FintyItems.COELACANTH_SPAWN_EGG);

        simpleItem(FintyItems.GOURAMI_BUCKET);
        simpleItem(FintyItems.GOURAMI_SPAWN_EGG);
        simpleItem(FintyItems.GOURAMI);

        simpleItem(FintyItems.COPEPOD_BUCKET);
        simpleItem(FintyItems.COPEPOD_SPAWN_EGG);
        simpleItem(FintyItems.COPEPOD);

        simpleItem(FintyItems.SALMON_BUCKET);
        simpleItem(FintyItems.SALMON_SPAWN_EGG);

        simpleItem(FintyItems.COD_BUCKET);
        simpleItem(FintyItems.COD_SPAWN_EGG);

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

        simpleBlockItemBlockTexture(FintyBlocks.AQUATIC_MOSS_PHYLLID);

        simpleBlockItemBlockTexture(FintyBlocks.AMAZON_SWORD);
        simpleBlockItemBlockTexture(FintyBlocks.BLUE_HYPNEA);
        simpleBlockItemBlockTexture(FintyBlocks.MERMAID_FAN);

        simpleItem(FintyItems.SEA_GRAPE_SALAD);

        simpleBlockItem();

        simpleItem(FintyItems.DWARF_FROG_BUCKET);
        simpleItem(FintyItems.DWARF_FROG_TADPOLE_BUCKET);
        simpleItem(FintyItems.DWARF_FROG_SPAWN_EGG);
        simpleItem(FintyItems.DWARF_FROG);
        simpleItem(FintyItems.COOKED_DWARF_FROG);

        simpleItem(FintyItems.SMALL_CATFISH_SPAWN_EGG);
        simpleItem(FintyItems.SMALL_CATFISH_BUCKET);
        simpleItem(FintyItems.SMALL_CATFISH);

        simpleItem(FintyItems.LOTUS_ROOT);
        simpleItem(FintyItems.BAKED_LOTUS_ROOT);
    }

    private ItemModelBuilder simpleBlockItemBlockTexture(Supplier<Block> item) {
        String name = BuiltInRegistries.BLOCK.getKey(item.get()).getPath();
        return withExistingParent(name,
                ResourceLocation.parse("item/generated")).texture("layer0",
                Fintastic.location("block/" + name));
    }

    private ItemModelBuilder simpleItem(Supplier<Item> item){
        String name = BuiltInRegistries.ITEM.getKey(item.get()).getPath();
        return withExistingParent(name,
                ResourceLocation.parse("item/generated")).texture("layer0",
                Fintastic.location("item/" + name));
    }

    public void evenSimplerBlockItem(Supplier<Block> block) {
        this.withExistingParent(Fintastic.MOD_ID + ":" + BuiltInRegistries.BLOCK.getKey(block.get()).getPath(),
                modLoc("block/" + BuiltInRegistries.BLOCK.getKey(block.get()).getPath()));
    }
    
    public void wallItem(Supplier<Block> block, Supplier<Block> baseBlock) {
        this.withExistingParent(BuiltInRegistries.BLOCK.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall", Fintastic.location("block/" + BuiltInRegistries.BLOCK.getKey(baseBlock.get()).getPath()));
    }

    private ItemModelBuilder simpleBlockItem() {
        String name = BuiltInRegistries.BLOCK.getKey(((Supplier<Block>) FintyBlocks.SEA_GRAPES).get()).getPath();
        return withExistingParent(name,
                ResourceLocation.parse("item/generated")).texture("layer0",
                Fintastic.location("item/" + name));
    }
}
