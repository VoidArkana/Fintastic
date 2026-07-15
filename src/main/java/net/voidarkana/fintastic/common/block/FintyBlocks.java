package net.voidarkana.fintastic.common.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.block.custom.*;
import net.voidarkana.fintastic.common.item.FintyItems;
import net.voidarkana.fintastic.common.worldgen.FintyConfiguredFeatures;

import java.util.function.Function;
import java.util.function.Supplier;

public class FintyBlocks {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Fintastic.MOD_ID);

    public static final Supplier<Block> DUCKWEED = registerBlockWithItem("duckweed",
            ()-> new DuckweedBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD).mapColor(MapColor.COLOR_LIGHT_GREEN)
                    .instabreak().noCollission()),
                    (entry) -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));

    public static final DeferredBlock<Block> HORNWORT = registerBlock("hornwort",
            ()-> new HornwortBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SEAGRASS).noOcclusion().instabreak().noCollission()));

    public static final DeferredBlock<Block> ANUBIAS = registerBlock("anubias",
            ()-> new AnubiasBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SEAGRASS).noOcclusion().instabreak().noCollission()));



    public static final DeferredBlock<Block> FISHBOWL = registerBlock("fishbowl",
            ()-> new FishbowlBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)));

    public static final DeferredBlock<Block> AQUARIUM_GLASS = registerBlock("aquarium_glass",
            ()-> new AquariumGlassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).mapColor(MapColor.WATER).noOcclusion()));

    public static final DeferredBlock<Block> CLEAR_AQUARIUM_GLASS = registerBlock("clear_aquarium_glass",
            ()-> new AquariumGlassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).mapColor(MapColor.NONE)));

    public static final DeferredBlock<Block> INFERNAL_AQUARIUM_GLASS = registerBlock("infernal_aquarium_glass",
            ()-> new AquariumGlassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).mapColor(MapColor.FIRE)));

    public static final DeferredBlock<Block> TINTED_INFERNAL_AQUARIUM_GLASS = registerBlock("tinted_infernal_aquarium_glass",
            ()-> new TintedAquariumGlassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TINTED_GLASS).mapColor(MapColor.FIRE)));

    public static final DeferredBlock<Block> TINTED_AQUARIUM_GLASS = registerBlock("tinted_aquarium_glass",
            ()-> new TintedAquariumGlassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TINTED_GLASS).mapColor(MapColor.WATER)));


    public static final DeferredBlock<Block> AQUARIUM_GLASS_PANE = registerBlock("aquarium_glass_pane",
            ()-> new AquariumGlassPane(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE).mapColor(MapColor.WATER)));

    public static final DeferredBlock<Block> CLEAR_AQUARIUM_GLASS_PANE = registerBlock("clear_aquarium_glass_pane",
            ()-> new AquariumGlassPane(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE).mapColor(MapColor.NONE)));

    public static final DeferredBlock<Block> INFERNAL_AQUARIUM_GLASS_PANE = registerBlock("infernal_aquarium_glass_pane",
            ()-> new AquariumGlassPane(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE).mapColor(MapColor.FIRE)));



    public static final DeferredBlock<Block> RADON_AQUARIUM_GLASS = registerBlock("radon_aquarium_glass",
            ()-> new AquariumGlassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).mapColor(MapColor.COLOR_LIGHT_GREEN)));

    public static final DeferredBlock<Block> RADON_AQUARIUM_GLASS_PANE = registerBlock("radon_aquarium_glass_pane",
            ()-> new AquariumGlassPane(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE).mapColor(MapColor.COLOR_LIGHT_GREEN)));

    public static final DeferredBlock<Block> TINTED_RADON_AQUARIUM_GLASS = registerBlock("tinted_radon_aquarium_glass",
            ()-> new TintedAquariumGlassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TINTED_GLASS).mapColor(MapColor.COLOR_GREEN)));



    public static final DeferredBlock<Block> SUGAR_AQUARIUM_GLASS = registerBlock("sugar_aquarium_glass",
            ()-> new AquariumGlassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).mapColor(MapColor.COLOR_PURPLE)));

    public static final DeferredBlock<Block> SUGAR_AQUARIUM_GLASS_PANE = registerBlock("sugar_aquarium_glass_pane",
            ()-> new AquariumGlassPane(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE).mapColor(MapColor.COLOR_PURPLE)));

    public static final DeferredBlock<Block> TINTED_SUGAR_AQUARIUM_GLASS = registerBlock("tinted_sugar_aquarium_glass",
            ()-> new TintedAquariumGlassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TINTED_GLASS).mapColor(MapColor.COLOR_PURPLE)));


    public static final DeferredBlock<Block> RED_AQUARIUM_GLASS = registerBlock("red_aquarium_glass",
            ()-> new AquariumGlassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).mapColor(MapColor.COLOR_RED)));

    public static final DeferredBlock<Block> RED_AQUARIUM_GLASS_PANE = registerBlock("red_aquarium_glass_pane",
            ()-> new AquariumGlassPane(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE).mapColor(MapColor.COLOR_RED)));

    public static final DeferredBlock<Block> WHITE_AQUARIUM_GLASS = registerBlock("white_aquarium_glass",
            ()-> new AquariumGlassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_WHITE)));

    public static final DeferredBlock<Block> WHITE_AQUARIUM_GLASS_PANE = registerBlock("white_aquarium_glass_pane",
            ()-> new AquariumGlassPane(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE).mapColor(MapColor.TERRACOTTA_WHITE)));

    public static final DeferredBlock<Block> LIGHT_GRAY_AQUARIUM_GLASS = registerBlock("light_gray_aquarium_glass",
            ()-> new AquariumGlassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).mapColor(MapColor.COLOR_LIGHT_GRAY)));

    public static final DeferredBlock<Block> LIGHT_GRAY_AQUARIUM_GLASS_PANE = registerBlock("light_gray_aquarium_glass_pane",
            ()-> new AquariumGlassPane(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE).mapColor(MapColor.COLOR_LIGHT_GRAY)));

    public static final DeferredBlock<Block> GRAY_AQUARIUM_GLASS = registerBlock("gray_aquarium_glass",
            ()-> new AquariumGlassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).mapColor(MapColor.COLOR_GRAY)));

    public static final DeferredBlock<Block> GRAY_AQUARIUM_GLASS_PANE = registerBlock("gray_aquarium_glass_pane",
            ()-> new AquariumGlassPane(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE).mapColor(MapColor.COLOR_GRAY)));

    public static final DeferredBlock<Block> BLACK_AQUARIUM_GLASS = registerBlock("black_aquarium_glass",
            ()-> new AquariumGlassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).mapColor(MapColor.COLOR_BLACK)));

    public static final DeferredBlock<Block> BLACK_AQUARIUM_GLASS_PANE = registerBlock("black_aquarium_glass_pane",
            ()-> new AquariumGlassPane(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE).mapColor(MapColor.COLOR_BLACK)));

    public static final DeferredBlock<Block> ORANGE_AQUARIUM_GLASS = registerBlock("orange_aquarium_glass",
            ()-> new AquariumGlassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).mapColor(MapColor.COLOR_ORANGE)));

    public static final DeferredBlock<Block> ORANGE_AQUARIUM_GLASS_PANE = registerBlock("orange_aquarium_glass_pane",
            ()-> new AquariumGlassPane(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE).mapColor(MapColor.COLOR_ORANGE)));

    public static final DeferredBlock<Block> YELLOW_AQUARIUM_GLASS = registerBlock("yellow_aquarium_glass",
            ()-> new AquariumGlassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).mapColor(MapColor.COLOR_YELLOW)));

    public static final DeferredBlock<Block> YELLOW_AQUARIUM_GLASS_PANE = registerBlock("yellow_aquarium_glass_pane",
            ()-> new AquariumGlassPane(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE).mapColor(MapColor.COLOR_YELLOW)));

    public static final DeferredBlock<Block> GREEN_AQUARIUM_GLASS = registerBlock("green_aquarium_glass",
            ()-> new AquariumGlassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).mapColor(MapColor.COLOR_GREEN)));

    public static final DeferredBlock<Block> GREEN_AQUARIUM_GLASS_PANE = registerBlock("green_aquarium_glass_pane",
            ()-> new AquariumGlassPane(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE).mapColor(MapColor.COLOR_GREEN)));

    public static final DeferredBlock<Block> LIME_AQUARIUM_GLASS = registerBlock("lime_aquarium_glass",
            ()-> new AquariumGlassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).mapColor(MapColor.COLOR_LIGHT_GREEN)));

    public static final DeferredBlock<Block> LIME_AQUARIUM_GLASS_PANE = registerBlock("lime_aquarium_glass_pane",
            ()-> new AquariumGlassPane(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE).mapColor(MapColor.COLOR_LIGHT_GREEN)));

    public static final DeferredBlock<Block> BROWN_AQUARIUM_GLASS = registerBlock("brown_aquarium_glass",
            ()-> new AquariumGlassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).mapColor(MapColor.COLOR_BROWN)));

    public static final DeferredBlock<Block> BROWN_AQUARIUM_GLASS_PANE = registerBlock("brown_aquarium_glass_pane",
            ()-> new AquariumGlassPane(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE).mapColor(MapColor.COLOR_BROWN)));

    public static final DeferredBlock<Block> LIGHT_BLUE_AQUARIUM_GLASS = registerBlock("light_blue_aquarium_glass",
            ()-> new AquariumGlassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).mapColor(MapColor.COLOR_LIGHT_BLUE)));

    public static final DeferredBlock<Block> LIGHT_BLUE_AQUARIUM_GLASS_PANE = registerBlock("light_blue_aquarium_glass_pane",
            ()-> new AquariumGlassPane(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE).mapColor(MapColor.COLOR_LIGHT_BLUE)));

    public static final DeferredBlock<Block> BLUE_AQUARIUM_GLASS = registerBlock("blue_aquarium_glass",
            ()-> new AquariumGlassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).mapColor(MapColor.COLOR_BLUE)));

    public static final DeferredBlock<Block> BLUE_AQUARIUM_GLASS_PANE = registerBlock("blue_aquarium_glass_pane",
            ()-> new AquariumGlassPane(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE).mapColor(MapColor.COLOR_BLUE)));

    public static final DeferredBlock<Block> CYAN_AQUARIUM_GLASS = registerBlock("cyan_aquarium_glass",
            ()-> new AquariumGlassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).mapColor(MapColor.COLOR_CYAN)));

    public static final DeferredBlock<Block> CYAN_AQUARIUM_GLASS_PANE = registerBlock("cyan_aquarium_glass_pane",
            ()-> new AquariumGlassPane(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE).mapColor(MapColor.COLOR_CYAN)));

    public static final DeferredBlock<Block> PURPLE_AQUARIUM_GLASS = registerBlock("purple_aquarium_glass",
            ()-> new AquariumGlassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).mapColor(MapColor.COLOR_PURPLE)));

    public static final DeferredBlock<Block> PURPLE_AQUARIUM_GLASS_PANE = registerBlock("purple_aquarium_glass_pane",
            ()-> new AquariumGlassPane(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE).mapColor(MapColor.COLOR_PURPLE)));

    public static final DeferredBlock<Block> MAGENTA_AQUARIUM_GLASS = registerBlock("magenta_aquarium_glass",
            ()-> new AquariumGlassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).mapColor(MapColor.COLOR_MAGENTA)));

    public static final DeferredBlock<Block> MAGENTA_AQUARIUM_GLASS_PANE = registerBlock("magenta_aquarium_glass_pane",
            ()-> new AquariumGlassPane(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE).mapColor(MapColor.COLOR_MAGENTA)));

    public static final DeferredBlock<Block> PINK_AQUARIUM_GLASS = registerBlock("pink_aquarium_glass",
            ()-> new AquariumGlassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).mapColor(MapColor.COLOR_PINK)));

    public static final DeferredBlock<Block> PINK_AQUARIUM_GLASS_PANE = registerBlock("pink_aquarium_glass_pane",
            ()-> new AquariumGlassPane(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE).mapColor(MapColor.COLOR_PINK)));








    public static final DeferredBlock<Block> DEAD_LIVE_ROCK = registerBlock("dead_live_rock",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).forceSolidOn()
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(1.5F, 6.0F)));

    public static final DeferredBlock<Block> DEAD_POROUS_LIVE_ROCK = registerBlock("dead_porous_live_rock",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).forceSolidOn()
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(1.5F, 6.0F)));

    public static final DeferredBlock<Block> LIVE_ROCK = registerBlock("live_rock",
            () -> new LiveRockBlock(DEAD_LIVE_ROCK.get(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));

    public static final DeferredBlock<Block> POROUS_LIVE_ROCK = registerBlock("porous_live_rock",
            () -> new LiveRockBlock(DEAD_POROUS_LIVE_ROCK.get(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));


    public static final DeferredBlock<Block> GREEN_ALGAE_LIVE_ROCK = registerBlock("green_algae_live_rock",
            () -> new AlgaeLiveRockBlock(DEAD_LIVE_ROCK.get(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));

    public static final DeferredBlock<Block> RED_ALGAE_LIVE_ROCK = registerBlock("red_algae_live_rock",
            () -> new AlgaeLiveRockBlock(DEAD_POROUS_LIVE_ROCK.get(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));



    public static final DeferredBlock<Block> GREEN_ALGAE_BLOCK = registerBlock("green_algae_block",
            () -> new AlgaeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN)
                    .strength(0.5F, 2.5F).sound(SoundType.WET_GRASS),
                    FintyConfiguredFeatures.GREEN_ALGAE_PATCH_BONEMEAL));

    public static final DeferredBlock<Block> GREEN_ALGAE_CARPET = registerBlock("green_algae_carpet",
            () -> new AlgaeCarpetBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(0.1F)
                    .sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> CAULERPA = registerBlock("caulerpa",
            () -> new AquaticPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).replaceable()
                    .offsetType(BlockBehaviour.OffsetType.XZ).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));


    public static final DeferredBlock<Block> RED_ALGAE_BLOCK = registerBlock("red_algae_block",
            () -> new AlgaeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED)
                    .strength(0.5F, 2.5F).sound(SoundType.WET_GRASS),
                    FintyConfiguredFeatures.RED_ALGAE_PATCH_BONEMEAL));

    public static final DeferredBlock<Block> RED_ALGAE_CARPET = registerBlock("red_algae_carpet",
            () -> new AlgaeCarpetBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).strength(0.1F)
                    .sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> RED_ALGAE = registerBlock("red_algae",
            () -> new AquaticPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).replaceable()
                    .offsetType(BlockBehaviour.OffsetType.XZ).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> RED_ALGAE_FAN = BLOCKS.register("red_algae_fan",
            () -> new AlgaeFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).replaceable()
                    .noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> RED_ALGAE_WALL_FAN = BLOCKS.register("red_algae_wall_fan",
            () -> new AlgaeWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).replaceable()
                    .noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> DRAGONS_BREATH_ALGAE = registerBlock("dragons_breath_algae",
            () -> new AquaticPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).replaceable()
                    .offsetType(BlockBehaviour.OffsetType.XZ).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));


    public static final DeferredBlock<Block> STROMATOLITE = registerBlock("stromatolite",
            () -> new StromatoliteBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(1.5F, 6.0F)));

    public static final DeferredBlock<Block> FOSSIL_STROMATOLITE = registerBlock("fossil_stromatolite",
            () -> new StromatoliteBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(1.5F, 6.0F)));

    public static final DeferredBlock<Block> STROMATOLITE_GROWTHS = registerBlock("stromatolite_growths",
            () -> new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().noCollission()
                    .offsetType(BlockBehaviour.OffsetType.XZ).strength(0.25F, 3.5F)));

    public static final DeferredBlock<Block> FOSSIL_STROMATOLITE_GROWTHS = registerBlock("fossil_stromatolite_growths",
            () -> new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().noCollission()
                    .offsetType(BlockBehaviour.OffsetType.XZ).strength(0.25F, 3.5F)));

    public static final DeferredBlock<Block> STROMATOLITE_BLOCK = registerBlock("stromatolite_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).forceSolidOn()
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> CUT_STROMATOLITE_BLOCK = registerBlock("cut_stromatolite_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_YELLOW).forceSolidOn()
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> FOSSIL_STROMATOLITE_BLOCK = registerBlock("fossil_stromatolite_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).forceSolidOn()
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(1.5F, 6.0F)));


    public static final DeferredBlock<Block> STROMATOLITE_BRICKS = registerBlock("stromatolite_bricks",
            ()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));

    public static final DeferredBlock<Block> STROMATOLITE_BRICKS_SLAB = registerBlock("stromatolite_bricks_slab",
            ()-> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));

    public static final DeferredBlock<Block> STROMATOLITE_BRICKS_STAIRS = registerBlock("stromatolite_bricks_stairs",
            ()-> new StairBlock(FintyBlocks.STROMATOLITE_BRICKS.get().defaultBlockState(),BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));

    public static final DeferredBlock<Block> STROMATOLITE_BRICKS_WALL = registerBlock("stromatolite_bricks_wall",
            ()-> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));

    public static final DeferredBlock<Block> SEA_GRAPES = registerBlock("sea_grapes",
            ()-> new SeaGrapesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WATER)
                    .noCollission().randomTicks().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> SEA_GRAPES_PLANT = registerBlock("sea_grapes_plant",
            ()-> new SeaGrapesPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WATER)
                    .noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));


    public static final DeferredBlock<Block> TALL_AMAZON_SWORD = registerBlock("tall_amazon_sword",
            () -> new TallAquaticPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN)
                    .offsetType(BlockBehaviour.OffsetType.XZ).noCollission().instabreak().sound(SoundType.WET_GRASS)
                    .pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> AMAZON_SWORD = registerBlock("amazon_sword",
            () -> new AquaticPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN)
                    .offsetType(BlockBehaviour.OffsetType.XZ).noCollission().instabreak().sound(SoundType.WET_GRASS)
                    .pushReaction(PushReaction.DESTROY), true, TALL_AMAZON_SWORD.get()));

    public static final DeferredBlock<Block> BLUE_HYPNEA = registerBlock("blue_hypnea",
            () -> new AquaticPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).replaceable()
                    .offsetType(BlockBehaviour.OffsetType.XZ).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> MERMAID_FAN = registerBlock("mermaid_fan",
            () -> new AquaticPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).replaceable()
                    .offsetType(BlockBehaviour.OffsetType.XZ).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> AQUATIC_MOSS_BLOCK = registerBlock("aquatic_moss_block",
            () -> new AlgaeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN)
                    .strength(0.5F, 2.5F).sound(SoundType.WET_GRASS),
                    FintyConfiguredFeatures.AQUATIC_MOSS_PATCH_BONEMEAL));

    public static final DeferredBlock<Block> AQUATIC_MOSS_CARPET = registerBlock("aquatic_moss_carpet",
            () -> new AlgaeCarpetBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).strength(0.1F)
                    .sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> AQUATIC_MOSS_PHYLLID = registerBlock("aquatic_moss_phyllid",
            () -> new AquaticPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).replaceable()
                    .offsetType(BlockBehaviour.OffsetType.XZ).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> LOTUS = registerBlock("lotus",
            () -> new LotusPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN)
                    .noCollission().noOcclusion().instabreak().sound(SoundType.WET_GRASS)
                    .pushReaction(PushReaction.DESTROY).randomTicks()));

    public static final Supplier<Block> LOTUS_FLOWER = registerBlockWithItem("lotus_flower",
            () -> new LotusFlowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK)
                    .noOcclusion().offsetType(BlockBehaviour.OffsetType.XZ).noCollission().instabreak().sound(SoundType.MOSS).pushReaction(PushReaction.DESTROY)),
            (entry) -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));

    public static final Supplier<Block> LOTUS_PAD = registerBlockWithItem("lotus_pad",
            ()-> new LotusPadBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD).mapColor(MapColor.COLOR_GREEN)
                    .offsetType(BlockBehaviour.OffsetType.XZ).noOcclusion().instabreak().noCollission().pushReaction(PushReaction.DESTROY)),
            (entry) -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));

    public static final Supplier<Block> DWARF_FROGSPAWN = registerBlockWithItem("dwarf_frogspawn",
            ()-> new DwarfFrogspawnBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FROGSPAWN)
                    .instabreak().noCollission().pushReaction(PushReaction.DESTROY)),
            (entry) -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));

    public static final DeferredBlock<Block> GREEN_DWARF_FROGLIGHT = registerBlock("green_dwarf_froglight",
            () -> new DwarfFroglight(BlockBehaviour.Properties.of().instabreak().sound(SoundType.FROGLIGHT).pushReaction(PushReaction.DESTROY)
                    .lightLevel((state) -> 15)));

    public static final DeferredBlock<Block> RED_DWARF_FROGLIGHT = registerBlock("red_dwarf_froglight",
            () -> new DwarfFroglight(BlockBehaviour.Properties.of().instabreak().sound(SoundType.FROGLIGHT).pushReaction(PushReaction.DESTROY)
                    .lightLevel((state) -> 15)));

    public static final DeferredBlock<Block> YELLOW_DWARF_FROGLIGHT = registerBlock("yellow_dwarf_froglight",
            () -> new DwarfFroglight(BlockBehaviour.Properties.of().instabreak().sound(SoundType.FROGLIGHT).pushReaction(PushReaction.DESTROY)
                    .lightLevel((state) -> 15)));

    public static final DeferredBlock<Block> PINK_DWARF_FROGLIGHT = registerBlock("pink_dwarf_froglight",
            () -> new DwarfFroglight(BlockBehaviour.Properties.of().instabreak().sound(SoundType.FROGLIGHT).pushReaction(PushReaction.DESTROY)
                    .lightLevel((state) -> 15)));

    private static <T extends Block> Supplier<T> registerBlockWithItem(String key, Supplier<T> block, Function<Supplier<T>, Item> item) {
        Supplier<T> entry = create(key, block);
        FintyItems.ITEMS.register(key, () -> item.apply(entry));
        return entry;
    }

    private static <T extends Block> Supplier<T> create(String key, Supplier<T> block) {
        return BLOCKS.register(key, block);
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block){
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block){
        FintyItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
