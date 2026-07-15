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
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.block.FintyBlocks;
import net.voidarkana.fintastic.common.item.FintyItems;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class FintyRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public FintyRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput consumer) {

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

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.RED_AQUARIUM_GLASS.get().asItem(), 8)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .define('G', FintyBlocks.AQUARIUM_GLASS.get())
                .define('D', Items.RED_DYE)
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.RED_AQUARIUM_GLASS_PANE.get().asItem(), 16)
                .pattern("GGG")
                .pattern("GGG")
                .define('G', FintyBlocks.RED_AQUARIUM_GLASS.get())
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.RED_AQUARIUM_GLASS_PANE.get().asItem(), 8)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .define('G', FintyBlocks.AQUARIUM_GLASS_PANE.get())
                .define('D', Items.RED_DYE)
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer, this.name("red_aquarium_glass_pane_alt"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.WHITE_AQUARIUM_GLASS.get().asItem(), 8)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .define('G', FintyBlocks.AQUARIUM_GLASS.get())
                .define('D', Items.WHITE_DYE)
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.WHITE_AQUARIUM_GLASS_PANE.get().asItem(), 16)
                .pattern("GGG")
                .pattern("GGG")
                .define('G', FintyBlocks.WHITE_AQUARIUM_GLASS.get())
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.WHITE_AQUARIUM_GLASS_PANE.get().asItem(), 8)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .define('G', FintyBlocks.AQUARIUM_GLASS_PANE.get())
                .define('D', Items.WHITE_DYE)
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer, this.name("white_aquarium_glass_pane_alt"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.GRAY_AQUARIUM_GLASS.get().asItem(), 8)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .define('G', FintyBlocks.AQUARIUM_GLASS.get())
                .define('D', Items.GRAY_DYE)
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.GRAY_AQUARIUM_GLASS_PANE.get().asItem(), 16)
                .pattern("GGG")
                .pattern("GGG")
                .define('G', FintyBlocks.GRAY_AQUARIUM_GLASS.get())
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.GRAY_AQUARIUM_GLASS_PANE.get().asItem(), 8)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .define('G', FintyBlocks.AQUARIUM_GLASS_PANE.get())
                .define('D', Items.GRAY_DYE)
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer, this.name("gray_aquarium_glass_pane_alt"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.LIGHT_GRAY_AQUARIUM_GLASS.get().asItem(), 8)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .define('G', FintyBlocks.AQUARIUM_GLASS.get())
                .define('D', Items.LIGHT_GRAY_DYE)
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.LIGHT_GRAY_AQUARIUM_GLASS_PANE.get().asItem(), 16)
                .pattern("GGG")
                .pattern("GGG")
                .define('G', FintyBlocks.LIGHT_GRAY_AQUARIUM_GLASS.get())
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.LIGHT_GRAY_AQUARIUM_GLASS_PANE.get().asItem(), 8)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .define('G', FintyBlocks.AQUARIUM_GLASS_PANE.get())
                .define('D', Items.LIGHT_GRAY_DYE)
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer, this.name("light_gray_aquarium_glass_pane_alt"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.BLACK_AQUARIUM_GLASS.get().asItem(), 8)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .define('G', FintyBlocks.AQUARIUM_GLASS.get())
                .define('D', Items.BLACK_DYE)
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.BLACK_AQUARIUM_GLASS_PANE.get().asItem(), 16)
                .pattern("GGG")
                .pattern("GGG")
                .define('G', FintyBlocks.BLACK_AQUARIUM_GLASS.get())
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.BLACK_AQUARIUM_GLASS_PANE.get().asItem(), 8)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .define('G', FintyBlocks.AQUARIUM_GLASS_PANE.get())
                .define('D', Items.BLACK_DYE)
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer, this.name("black_aquarium_glass_pane_alt"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.ORANGE_AQUARIUM_GLASS.get().asItem(), 8)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .define('G', FintyBlocks.AQUARIUM_GLASS.get())
                .define('D', Items.ORANGE_DYE)
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.ORANGE_AQUARIUM_GLASS_PANE.get().asItem(), 16)
                .pattern("GGG")
                .pattern("GGG")
                .define('G', FintyBlocks.ORANGE_AQUARIUM_GLASS.get())
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.ORANGE_AQUARIUM_GLASS_PANE.get().asItem(), 8)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .define('G', FintyBlocks.AQUARIUM_GLASS_PANE.get())
                .define('D', Items.ORANGE_DYE)
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer, this.name("orange_aquarium_glass_pane_alt"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.YELLOW_AQUARIUM_GLASS.get().asItem(), 8)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .define('G', FintyBlocks.AQUARIUM_GLASS.get())
                .define('D', Items.YELLOW_DYE)
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.YELLOW_AQUARIUM_GLASS_PANE.get().asItem(), 16)
                .pattern("GGG")
                .pattern("GGG")
                .define('G', FintyBlocks.YELLOW_AQUARIUM_GLASS.get())
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.YELLOW_AQUARIUM_GLASS_PANE.get().asItem(), 8)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .define('G', FintyBlocks.AQUARIUM_GLASS_PANE.get())
                .define('D', Items.YELLOW_DYE)
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer, this.name("yellow_aquarium_glass_pane_alt"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.BROWN_AQUARIUM_GLASS.get().asItem(), 8)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .define('G', FintyBlocks.AQUARIUM_GLASS.get())
                .define('D', Items.BROWN_DYE)
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.BROWN_AQUARIUM_GLASS_PANE.get().asItem(), 16)
                .pattern("GGG")
                .pattern("GGG")
                .define('G', FintyBlocks.BROWN_AQUARIUM_GLASS.get())
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.BROWN_AQUARIUM_GLASS_PANE.get().asItem(), 8)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .define('G', FintyBlocks.AQUARIUM_GLASS_PANE.get())
                .define('D', Items.BROWN_DYE)
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer, this.name("brown_aquarium_glass_pane_alt"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.GREEN_AQUARIUM_GLASS.get().asItem(), 8)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .define('G', FintyBlocks.AQUARIUM_GLASS.get())
                .define('D', Items.GREEN_DYE)
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.GREEN_AQUARIUM_GLASS_PANE.get().asItem(), 16)
                .pattern("GGG")
                .pattern("GGG")
                .define('G', FintyBlocks.GREEN_AQUARIUM_GLASS.get())
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.GREEN_AQUARIUM_GLASS_PANE.get().asItem(), 8)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .define('G', FintyBlocks.AQUARIUM_GLASS_PANE.get())
                .define('D', Items.GREEN_DYE)
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer, this.name("green_aquarium_glass_pane_alt"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.LIME_AQUARIUM_GLASS.get().asItem(), 8)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .define('G', FintyBlocks.AQUARIUM_GLASS.get())
                .define('D', Items.LIME_DYE)
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.LIME_AQUARIUM_GLASS_PANE.get().asItem(), 16)
                .pattern("GGG")
                .pattern("GGG")
                .define('G', FintyBlocks.LIME_AQUARIUM_GLASS.get())
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.LIME_AQUARIUM_GLASS_PANE.get().asItem(), 8)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .define('G', FintyBlocks.AQUARIUM_GLASS_PANE.get())
                .define('D', Items.LIME_DYE)
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer, this.name("lime_aquarium_glass_pane_alt"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.CYAN_AQUARIUM_GLASS.get().asItem(), 8)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .define('G', FintyBlocks.AQUARIUM_GLASS.get())
                .define('D', Items.CYAN_DYE)
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.CYAN_AQUARIUM_GLASS_PANE.get().asItem(), 16)
                .pattern("GGG")
                .pattern("GGG")
                .define('G', FintyBlocks.CYAN_AQUARIUM_GLASS.get())
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.CYAN_AQUARIUM_GLASS_PANE.get().asItem(), 8)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .define('G', FintyBlocks.AQUARIUM_GLASS_PANE.get())
                .define('D', Items.CYAN_DYE)
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer, this.name("cyan_aquarium_glass_pane_alt"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.BLUE_AQUARIUM_GLASS.get().asItem(), 8)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .define('G', FintyBlocks.AQUARIUM_GLASS.get())
                .define('D', Items.BLUE_DYE)
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.BLUE_AQUARIUM_GLASS_PANE.get().asItem(), 16)
                .pattern("GGG")
                .pattern("GGG")
                .define('G', FintyBlocks.BLUE_AQUARIUM_GLASS.get())
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.BLUE_AQUARIUM_GLASS_PANE.get().asItem(), 8)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .define('G', FintyBlocks.AQUARIUM_GLASS_PANE.get())
                .define('D', Items.BLUE_DYE)
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer, this.name("blue_aquarium_glass_pane_alt"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.LIGHT_BLUE_AQUARIUM_GLASS.get().asItem(), 8)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .define('G', FintyBlocks.AQUARIUM_GLASS.get())
                .define('D', Items.LIGHT_BLUE_DYE)
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.LIGHT_BLUE_AQUARIUM_GLASS_PANE.get().asItem(), 16)
                .pattern("GGG")
                .pattern("GGG")
                .define('G', FintyBlocks.LIGHT_BLUE_AQUARIUM_GLASS.get())
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.LIGHT_BLUE_AQUARIUM_GLASS_PANE.get().asItem(), 8)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .define('G', FintyBlocks.AQUARIUM_GLASS_PANE.get())
                .define('D', Items.LIGHT_BLUE_DYE)
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer, this.name("light_blue_aquarium_glass_pane_alt"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.PURPLE_AQUARIUM_GLASS.get().asItem(), 8)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .define('G', FintyBlocks.AQUARIUM_GLASS.get())
                .define('D', Items.PURPLE_DYE)
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.PURPLE_AQUARIUM_GLASS_PANE.get().asItem(), 16)
                .pattern("GGG")
                .pattern("GGG")
                .define('G', FintyBlocks.PURPLE_AQUARIUM_GLASS.get())
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.PURPLE_AQUARIUM_GLASS_PANE.get().asItem(), 8)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .define('G', FintyBlocks.AQUARIUM_GLASS_PANE.get())
                .define('D', Items.PURPLE_DYE)
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer, this.name("purple_aquarium_glass_pane_alt"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.PINK_AQUARIUM_GLASS.get().asItem(), 8)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .define('G', FintyBlocks.AQUARIUM_GLASS.get())
                .define('D', Items.PINK_DYE)
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.PINK_AQUARIUM_GLASS_PANE.get().asItem(), 16)
                .pattern("GGG")
                .pattern("GGG")
                .define('G', FintyBlocks.PINK_AQUARIUM_GLASS.get())
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.PINK_AQUARIUM_GLASS_PANE.get().asItem(), 8)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .define('G', FintyBlocks.AQUARIUM_GLASS_PANE.get())
                .define('D', Items.PINK_DYE)
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer, this.name("pink_aquarium_glass_pane_alt"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.MAGENTA_AQUARIUM_GLASS.get().asItem(), 8)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .define('G', FintyBlocks.AQUARIUM_GLASS.get())
                .define('D', Items.MAGENTA_DYE)
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.MAGENTA_AQUARIUM_GLASS_PANE.get().asItem(), 16)
                .pattern("GGG")
                .pattern("GGG")
                .define('G', FintyBlocks.MAGENTA_AQUARIUM_GLASS.get())
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FintyBlocks.MAGENTA_AQUARIUM_GLASS_PANE.get().asItem(), 8)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .define('G', FintyBlocks.AQUARIUM_GLASS_PANE.get())
                .define('D', Items.MAGENTA_DYE)
                .unlockedBy(getHasName(FintyBlocks.AQUARIUM_GLASS.get()), has(FintyBlocks.AQUARIUM_GLASS.get()))
                .save(consumer, this.name("magenta_aquarium_glass_pane_alt"));
    }

    public ShapelessRecipeBuilder makePlanks(Supplier<? extends Block> plankOut, TagKey<Item> logIn) {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, plankOut.get(), 4).requires(logIn).group("planks").unlockedBy("has_log", has(logIn));
    }

    public ShapelessRecipeBuilder makePlanksFromThinLog(Supplier<? extends Block> plankOut, TagKey<Item> logIn) {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, plankOut.get(), 1).requires(logIn).group("planks").unlockedBy("has_log", has(logIn));
    }

    public ShapedRecipeBuilder makeDoor(Supplier<? extends Block> doorOut, Supplier<? extends Block> plankIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, doorOut.get(), 3).pattern("PP").pattern("PP").pattern("PP").define('P', plankIn.get()).unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(plankIn.get()).getPath(), has(plankIn.get()));
    }

    public ShapedRecipeBuilder makeTrapdoor(Supplier<? extends Block> trapdoorOut, Supplier<? extends Block> plankIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, trapdoorOut.get(), 2).pattern("PPP").pattern("PPP").define('P', plankIn.get()).unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(plankIn.get()).getPath(), has(plankIn.get()));
    }

    public ShapelessRecipeBuilder makeButton(Supplier<? extends Block> buttonOut, Supplier<? extends Block> blockIn) {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, buttonOut.get()).requires(blockIn.get()).unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(blockIn.get()).getPath(), has(blockIn.get()));
    }

    public ShapedRecipeBuilder makePressurePlate(Supplier<? extends Block> pressurePlateOut, Supplier<? extends Block> blockIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, pressurePlateOut.get()).pattern("BB").define('B', blockIn.get()).unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(blockIn.get()).getPath(), has(blockIn.get()));
    }

    public ShapedRecipeBuilder makeStairs(Supplier<? extends Block> blockIn, Supplier<? extends Block> stairsOut) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stairsOut.get(), 4).pattern("M  ").pattern("MM ").pattern("MMM").define('M', blockIn.get()).unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(blockIn.get()).getPath(), has(blockIn.get()));
    }

    public ShapedRecipeBuilder makeSlab(Supplier<? extends Block> blockIn, Supplier<? extends Block> slabOut) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slabOut.get(), 6).pattern("MMM").define('M', blockIn.get()).unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(blockIn.get()).getPath(), has(blockIn.get()));
    }

    public ShapedRecipeBuilder makeWall(Supplier<? extends Block> blockIn, Supplier<? extends Block> wallOut) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, wallOut.get(),
                6).pattern("MMM").pattern("MMM").define('M',
                blockIn.get()).unlockedBy("has_" +
                BuiltInRegistries.BLOCK.getKey(blockIn.get()).getPath(), has(blockIn.get()));
    }

    public ShapedRecipeBuilder makeFence(Supplier<? extends Block> fenceOut, Supplier<? extends Block> blockIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, fenceOut.get(), 6).pattern("M/M").pattern("M/M").define('M', blockIn.get()).define('/', Tags.Items.RODS_WOODEN).unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(blockIn.get()).getPath(), has(blockIn.get()));
    }

    public ShapedRecipeBuilder makeFenceGate(Supplier<? extends Block> fenceGateOut, Supplier<? extends Block> blockIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, fenceGateOut.get()).pattern("/M/").pattern("/M/").define('M', blockIn.get()).define('/', Tags.Items.RODS_WOODEN).unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(blockIn.get()).getPath(), has(blockIn.get()));
    }

    public ShapedRecipeBuilder makeBricks(Supplier<? extends Block> blockIn, Supplier<? extends Block> bricksOut) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,
                        bricksOut.get(), 4).pattern("MM").pattern("MM")
                .define('M', blockIn.get()).unlockedBy("has_" +
                        BuiltInRegistries.BLOCK.getKey(blockIn.get()).getPath(), has(blockIn.get()));
    }

    public ShapedRecipeBuilder makeChiseledBricks(Supplier<? extends Block> blockIn, Supplier<? extends Block> bricksOut) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,
                bricksOut.get()).pattern("M").pattern("M").define('M',
                blockIn.get()).unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(blockIn.get()).getPath(), has(blockIn.get()));
    }

    public ShapedRecipeBuilder makeWood(Supplier<? extends Block> woodOut, Supplier<? extends Block> logIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, woodOut.get(), 3).pattern("MM").pattern("MM").define('M', logIn.get()).unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(logIn.get()).getPath(), has(logIn.get()));
    }

    public ShapedRecipeBuilder makeIngotToBlock(Supplier<? extends Block> blockOut, Supplier<? extends Item> ingotIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,
                        blockOut.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ingotIn.get())
                .unlockedBy("has_" +
                                BuiltInRegistries.ITEM.getKey(ingotIn.get()).getPath(),
                        has(ingotIn.get()));
    }

    public ShapelessRecipeBuilder makeBlockToIngot(Supplier<? extends Item> ingotOut, Supplier<? extends Block> blockIn) {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ingotOut.get(), 9).requires(blockIn.get()).unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(blockIn.get()).getPath(), has(blockIn.get()));
    }

    public ShapedRecipeBuilder make4IngotToBlock(ItemLike blockOut, ItemLike ingotIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,
                        blockOut)
                .pattern("##")
                .pattern("##")
                .define('#', ingotIn)
                .unlockedBy("has_" +
                                BuiltInRegistries.ITEM.getKey(ingotIn.asItem()).getPath(),
                        has(ingotIn));
    }

    public ShapelessRecipeBuilder makeBlockTo4Ingot(ItemLike ingotOut, ItemLike blockIn) {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ingotOut, 4)
                .requires(blockIn).unlockedBy(getHasName(blockIn), has(blockIn));
    }

    public ShapedRecipeBuilder makeNuggetToIngot(Supplier<? extends Item> ingotOut, Supplier<? extends Item> nuggetIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ingotOut.get(), 1).pattern("NNN").pattern("NNN").pattern("NNN").define('N', nuggetIn.get()).unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(nuggetIn.get()).getPath(), has(nuggetIn.get()));
    }

    public ShapelessRecipeBuilder makeIngotToNugget(Supplier<? extends Item> nuggetOut, Supplier<? extends Item> ingotIn) {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, nuggetOut.get(), 9).requires(ingotIn.get()).unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(ingotIn.get()).getPath(), has(ingotIn.get()));
    }

    public ShapedRecipeBuilder makeHelmet(Supplier<? extends Item> helmetOut, Supplier<? extends Item> materialIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, helmetOut.get()).pattern("MMM").pattern("M M").define('M', materialIn.get()).unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(materialIn.get()).getPath(), has(materialIn.get()));
    }

    public ShapedRecipeBuilder makeBoat(Supplier<? extends Item> boatOut, Supplier<? extends Block> planksIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, boatOut.get()).pattern("P P").pattern("PPP").define('P', planksIn.get()).group("boat").unlockedBy("in_water", insideOf(Blocks.WATER));
    }

    public ShapelessRecipeBuilder makeChestBoat(Supplier<? extends Item> chestBoatOut, Supplier<? extends Block> boatIn) {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, chestBoatOut.get()).requires(boatIn.get()).requires(Tags.Items.CHESTS_WOODEN).group("chest_boat").unlockedBy("has_boat", has(ItemTags.BOATS));
    }

    public ShapedRecipeBuilder makeSign(Supplier<? extends SignBlock> signOut, Supplier<? extends Block> planksIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, signOut.get(), 3).pattern("PPP").pattern("PPP").pattern(" / ").define('P', planksIn.get()).define('/', Tags.Items.RODS_WOODEN).unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(planksIn.get()).getPath(), has(planksIn.get()));
    }
    public SingleItemRecipeBuilder stonecutting(Supplier<Block> input, ItemLike result) {
        SingleItemRecipeBuilder var10000 = SingleItemRecipeBuilder.stonecutting(Ingredient.of(input.get()), RecipeCategory.BUILDING_BLOCKS, result);
        Registry<Block> var10001 = BuiltInRegistries.BLOCK;
        return var10000.unlockedBy("has_" + var10001.getKey(input.get()), has(input.get()));
    }

    public SingleItemRecipeBuilder stonecutting(Supplier<Block> input, ItemLike result, int resultAmount) {
        SingleItemRecipeBuilder var10000 = SingleItemRecipeBuilder.stonecutting(Ingredient.of(input.get()), RecipeCategory.BUILDING_BLOCKS, result, resultAmount);
        Registry<Block> var10001 = BuiltInRegistries.BLOCK;
        return var10000.unlockedBy("has_" + var10001.getKey(input.get()), has(input.get()));
    }

    private ResourceLocation name(String name) {
        return Fintastic.location(name);
    }
}
