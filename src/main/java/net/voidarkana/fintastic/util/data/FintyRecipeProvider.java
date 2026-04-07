package net.voidarkana.fintastic.util.data;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SignBlock;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.block.FintyBlocks;
import net.voidarkana.fintastic.common.item.FintyItems;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FintyRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public FintyRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FintyItems.RAW_FISH.get(), 3)
                .requires(FintyItems.FEATHERBACK.get())
                .unlockedBy(getHasName(FintyItems.FEATHERBACK.get()), has(FintyItems.FEATHERBACK.get()))
                .save(consumer, this.name("raw_fish_from_featherback"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FintyItems.RAW_FISH.get(), 1)
                .requires(FintyItems.GOURAMI.get())
                .unlockedBy(getHasName(FintyItems.GOURAMI.get()), has(FintyItems.GOURAMI.get()))
                .save(consumer, this.name("raw_fish_from_gourami"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FintyItems.RAW_FISH.get(), 4)
                .requires(FintyItems.ARAPAIMA.get())
                .unlockedBy(getHasName(FintyItems.ARAPAIMA.get()), has(FintyItems.ARAPAIMA.get()))
                .save(consumer, this.name("raw_fish_from_arapaima"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FintyItems.RAW_FISH.get(), 3)
                .requires(FintyItems.COELACANTH.get())
                .unlockedBy(getHasName(FintyItems.COELACANTH.get()), has(FintyItems.COELACANTH.get()))
                .save(consumer, this.name("raw_fish_from_coelacanth"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FintyItems.RAW_FISH.get(), 2)
                .requires(FintyItems.PLECO.get())
                .unlockedBy(getHasName(FintyItems.PLECO.get()), has(FintyItems.PLECO.get()))
                .save(consumer, this.name("raw_fish_from_pleco"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FintyItems.RAW_FISH.get(), 2)
                .requires(FintyItems.CATFISH.get())
                .unlockedBy(getHasName(FintyItems.CATFISH.get()), has(FintyItems.CATFISH.get()))
                .save(consumer, this.name("raw_fish_from_catfish"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FintyItems.RAW_FISH.get(), 2)
                .requires(FintyItems.SHARKMINNOW.get())
                .unlockedBy(getHasName(FintyItems.SHARKMINNOW.get()), has(FintyItems.SHARKMINNOW.get()))
                .save(consumer, this.name("raw_fish_from_sharkminnow"));



        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BONE_MEAL, 1)
                .requires(FintyItems.GUPPY.get())
                .unlockedBy(getHasName(FintyItems.GUPPY.get()), has(FintyItems.GUPPY.get()))
                .save(consumer, this.name("bone_meal_from_guppy"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BONE_MEAL, 1)
                .requires(FintyItems.MINNOW.get())
                .unlockedBy(getHasName(FintyItems.MINNOW.get()), has(FintyItems.MINNOW.get()))
                .save(consumer, this.name("bone_meal_from_minnow"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BONE_MEAL, 1)
                .requires(FintyItems.MOONY.get())
                .unlockedBy(getHasName(FintyItems.MOONY.get()), has(FintyItems.MOONY
                        .get()))
                .save(consumer, this.name("bone_meal_from_moony"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.AQUATIC_MOSS_BLOCK.get().asItem(), 2)
                .pattern("MM")
                .pattern("MM")
                .define('M', FintyBlocks.AQUATIC_MOSS_CARPET.get())
                .unlockedBy(getHasName(FintyBlocks.AQUATIC_MOSS_BLOCK.get()), has(FintyBlocks.AQUATIC_MOSS_BLOCK.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.AQUATIC_MOSS_CARPET.get().asItem(), 2)
                .pattern("MM")
                .define('M', FintyBlocks.AQUATIC_MOSS_BLOCK.get())
                .unlockedBy(getHasName(FintyBlocks.AQUATIC_MOSS_BLOCK.get()), has(FintyBlocks.AQUATIC_MOSS_BLOCK.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.PINK_DYE, 1)
                .requires(FintyBlocks.LOTUS_FLOWER.get())
                .unlockedBy(getHasName(FintyBlocks.LOTUS_FLOWER.get()), has(FintyBlocks.LOTUS_FLOWER.get()))
                .save(consumer, this.name("pink_dye_from_lotus"));
    }

    public ShapelessRecipeBuilder makePlanks(Supplier<? extends Block> plankOut, TagKey<Item> logIn) {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, (ItemLike)plankOut.get(), 4).requires(logIn).group("planks").unlockedBy("has_log", has(logIn));
    }

    public ShapelessRecipeBuilder makePlanksFromThinLog(Supplier<? extends Block> plankOut, TagKey<Item> logIn) {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, (ItemLike)plankOut.get(), 1).requires(logIn).group("planks").unlockedBy("has_log", has(logIn));
    }

    public ShapedRecipeBuilder makeDoor(Supplier<? extends Block> doorOut, Supplier<? extends Block> plankIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)doorOut.get(), 3).pattern("PP").pattern("PP").pattern("PP").define('P', (ItemLike)plankIn.get()).unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey((Block)plankIn.get()).getPath(), has((ItemLike)plankIn.get()));
    }

    public ShapedRecipeBuilder makeTrapdoor(Supplier<? extends Block> trapdoorOut, Supplier<? extends Block> plankIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)trapdoorOut.get(), 2).pattern("PPP").pattern("PPP").define('P', (ItemLike)plankIn.get()).unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey((Block)plankIn.get()).getPath(), has((ItemLike)plankIn.get()));
    }

    public ShapelessRecipeBuilder makeButton(Supplier<? extends Block> buttonOut, Supplier<? extends Block> blockIn) {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, (ItemLike)buttonOut.get()).requires((ItemLike)blockIn.get()).unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey((Block)blockIn.get()).getPath(), has((ItemLike)blockIn.get()));
    }

    public ShapedRecipeBuilder makePressurePlate(Supplier<? extends Block> pressurePlateOut, Supplier<? extends Block> blockIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)pressurePlateOut.get()).pattern("BB").define('B', (ItemLike)blockIn.get()).unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey((Block)blockIn.get()).getPath(), has((ItemLike)blockIn.get()));
    }

    public ShapedRecipeBuilder makeStairs(Supplier<? extends Block> blockIn, Supplier<? extends Block> stairsOut) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)stairsOut.get(), 4).pattern("M  ").pattern("MM ").pattern("MMM").define('M', (ItemLike)blockIn.get()).unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey((Block)blockIn.get()).getPath(), has((ItemLike)blockIn.get()));
    }

    public ShapedRecipeBuilder makeSlab(Supplier<? extends Block> blockIn, Supplier<? extends Block> slabOut) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)slabOut.get(), 6).pattern("MMM").define('M', (ItemLike)blockIn.get()).unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey((Block)blockIn.get()).getPath(), has((ItemLike)blockIn.get()));
    }

    public ShapedRecipeBuilder makeWall(Supplier<? extends Block> blockIn, Supplier<? extends Block> wallOut) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)wallOut.get(),
                6).pattern("MMM").pattern("MMM").define('M',
                (ItemLike)blockIn.get()).unlockedBy("has_" +
                ForgeRegistries.BLOCKS.getKey((Block)blockIn.get()).getPath(), has((ItemLike)blockIn.get()));
    }

    public ShapedRecipeBuilder makeFence(Supplier<? extends Block> fenceOut, Supplier<? extends Block> blockIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)fenceOut.get(), 6).pattern("M/M").pattern("M/M").define('M', (ItemLike)blockIn.get()).define('/', Tags.Items.RODS_WOODEN).unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey((Block)blockIn.get()).getPath(), has((ItemLike)blockIn.get()));
    }

    public ShapedRecipeBuilder makeFenceGate(Supplier<? extends Block> fenceGateOut, Supplier<? extends Block> blockIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)fenceGateOut.get()).pattern("/M/").pattern("/M/").define('M', (ItemLike)blockIn.get()).define('/', Tags.Items.RODS_WOODEN).unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey((Block)blockIn.get()).getPath(), has((ItemLike)blockIn.get()));
    }

    public ShapedRecipeBuilder makeBricks(Supplier<? extends Block> blockIn, Supplier<? extends Block> bricksOut) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,
                        (ItemLike)bricksOut.get(), 4).pattern("MM").pattern("MM")
                .define('M', (ItemLike)blockIn.get()).unlockedBy("has_" +
                        ForgeRegistries.BLOCKS.getKey((Block)blockIn.get()).getPath(), has((ItemLike)blockIn.get()));
    }

    public ShapedRecipeBuilder makeChiseledBricks(Supplier<? extends Block> blockIn, Supplier<? extends Block> bricksOut) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,
                (ItemLike)bricksOut.get()).pattern("M").pattern("M").define('M',
                (ItemLike)blockIn.get()).unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey((Block)blockIn.get()).getPath(), has((ItemLike)blockIn.get()));
    }

    public ShapedRecipeBuilder makeWood(Supplier<? extends Block> woodOut, Supplier<? extends Block> logIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)woodOut.get(), 3).pattern("MM").pattern("MM").define('M', (ItemLike)logIn.get()).unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey((Block)logIn.get()).getPath(), has((ItemLike)logIn.get()));
    }

    public ShapedRecipeBuilder makeIngotToBlock(Supplier<? extends Block> blockOut, Supplier<? extends Item> ingotIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,
                        blockOut.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ingotIn.get())
                .unlockedBy("has_" +
                                ForgeRegistries.ITEMS.getKey(ingotIn.get()).getPath(),
                        has(ingotIn.get()));
    }

    public ShapelessRecipeBuilder makeBlockToIngot(Supplier<? extends Item> ingotOut, Supplier<? extends Block> blockIn) {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, (ItemLike)ingotOut.get(), 9).requires((ItemLike)blockIn.get()).unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey((Block)blockIn.get()).getPath(), has((ItemLike)blockIn.get()));
    }

    public ShapedRecipeBuilder make4IngotToBlock(ItemLike blockOut, ItemLike ingotIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,
                        blockOut)
                .pattern("##")
                .pattern("##")
                .define('#', ingotIn)
                .unlockedBy("has_" +
                                ForgeRegistries.ITEMS.getKey(ingotIn.asItem()).getPath(),
                        has(ingotIn));
    }

    public ShapelessRecipeBuilder makeBlockTo4Ingot(ItemLike ingotOut, ItemLike blockIn) {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ingotOut, 4)
                .requires(blockIn).unlockedBy(getHasName(blockIn), has((ItemLike)blockIn));
    }

    public ShapedRecipeBuilder makeNuggetToIngot(Supplier<? extends Item> ingotOut, Supplier<? extends Item> nuggetIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)ingotOut.get(), 1).pattern("NNN").pattern("NNN").pattern("NNN").define('N', (ItemLike)nuggetIn.get()).unlockedBy("has_" + ForgeRegistries.ITEMS.getKey((Item)nuggetIn.get()).getPath(), has((ItemLike)nuggetIn.get()));
    }

    public ShapelessRecipeBuilder makeIngotToNugget(Supplier<? extends Item> nuggetOut, Supplier<? extends Item> ingotIn) {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, (ItemLike)nuggetOut.get(), 9).requires((ItemLike)ingotIn.get()).unlockedBy("has_" + ForgeRegistries.ITEMS.getKey((Item)ingotIn.get()).getPath(), has((ItemLike)ingotIn.get()));
    }

    public ShapedRecipeBuilder makeHelmet(Supplier<? extends Item> helmetOut, Supplier<? extends Item> materialIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (ItemLike)helmetOut.get()).pattern("MMM").pattern("M M").define('M', (ItemLike)materialIn.get()).unlockedBy("has_" + ForgeRegistries.ITEMS.getKey((Item)materialIn.get()).getPath(), has((ItemLike)materialIn.get()));
    }

    public ShapedRecipeBuilder makeBoat(Supplier<? extends Item> boatOut, Supplier<? extends Block> planksIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, (ItemLike)boatOut.get()).pattern("P P").pattern("PPP").define('P', (ItemLike)planksIn.get()).group("boat").unlockedBy("in_water", insideOf(Blocks.WATER));
    }

    public ShapelessRecipeBuilder makeChestBoat(Supplier<? extends Item> chestBoatOut, Supplier<? extends Block> boatIn) {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, (ItemLike)chestBoatOut.get()).requires((ItemLike)boatIn.get()).requires(Tags.Items.CHESTS_WOODEN).group("chest_boat").unlockedBy("has_boat", has(ItemTags.BOATS));
    }

    public ShapedRecipeBuilder makeSign(Supplier<? extends SignBlock> signOut, Supplier<? extends Block> planksIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)signOut.get(), 3).pattern("PPP").pattern("PPP").pattern(" / ").define('P', (ItemLike)planksIn.get()).define('/', Tags.Items.RODS_WOODEN).unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey((Block)planksIn.get()).getPath(), has((ItemLike)planksIn.get()));
    }
    public SingleItemRecipeBuilder stonecutting(Supplier<Block> input, ItemLike result) {
        SingleItemRecipeBuilder var10000 = SingleItemRecipeBuilder.stonecutting(Ingredient.of(new ItemLike[]{(ItemLike)input.get()}), RecipeCategory.BUILDING_BLOCKS, result);
        IForgeRegistry var10001 = ForgeRegistries.BLOCKS;
        return var10000.unlockedBy("has_" + var10001.getKey((Block)input.get()), has((ItemLike)input.get()));
    }

    public SingleItemRecipeBuilder stonecutting(Supplier<Block> input, ItemLike result, int resultAmount) {
        SingleItemRecipeBuilder var10000 = SingleItemRecipeBuilder.stonecutting(Ingredient.of(new ItemLike[]{(ItemLike)input.get()}), RecipeCategory.BUILDING_BLOCKS, result, resultAmount);
        IForgeRegistry var10001 = ForgeRegistries.BLOCKS;
        return var10000.unlockedBy("has_" + var10001.getKey((Block)input.get()), has((ItemLike)input.get()));
    }

    private ResourceLocation name(String name) {
        return new ResourceLocation(Fintastic.MOD_ID, name);
    }
}
