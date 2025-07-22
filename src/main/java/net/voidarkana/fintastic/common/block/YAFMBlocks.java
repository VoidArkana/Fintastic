package net.voidarkana.fintastic.common.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.block.custom.*;
import net.voidarkana.fintastic.common.item.YAFMItems;

import java.util.function.Function;
import java.util.function.Supplier;

public class YAFMBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Fintastic.MOD_ID);

    public static final Supplier<Block> DUCKWEED = registerBlockWithItem("duckweed",
            ()-> new DuckweedBlock(BlockBehaviour.Properties.copy(Blocks.LILY_PAD).mapColor(MapColor.COLOR_LIGHT_GREEN)
                    .instabreak().noCollission()),
                    (entry) -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));

    public static final RegistryObject<Block> HORNWORT = registerBlock("hornwort",
            ()-> new HornwortBlock(BlockBehaviour.Properties.copy(Blocks.SEAGRASS).noOcclusion().instabreak().noCollission()));

    public static final RegistryObject<Block> ANUBIAS = registerBlock("anubias",
            ()-> new AnubiasBlock(BlockBehaviour.Properties.copy(Blocks.SEAGRASS).noOcclusion().instabreak().noCollission()));



    public static final RegistryObject<Block> AQUARIUM_GLASS = registerBlock("aquarium_glass",
            ()-> new AquariumGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.WATER)));

    public static final RegistryObject<Block> CLEAR_AQUARIUM_GLASS = registerBlock("clear_aquarium_glass",
            ()-> new AquariumGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.NONE)));

    public static final RegistryObject<Block> INFERNAL_AQUARIUM_GLASS = registerBlock("infernal_aquarium_glass",
            ()-> new AquariumGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.FIRE)));

    public static final RegistryObject<Block> TINTED_INFERNAL_AQUARIUM_GLASS = registerBlock("tinted_infernal_aquarium_glass",
            ()-> new TintedAquariumGlassBlock(BlockBehaviour.Properties.copy(Blocks.TINTED_GLASS).mapColor(MapColor.FIRE)));

    public static final RegistryObject<Block> TINTED_AQUARIUM_GLASS = registerBlock("tinted_aquarium_glass",
            ()-> new TintedAquariumGlassBlock(BlockBehaviour.Properties.copy(Blocks.TINTED_GLASS).mapColor(MapColor.WATER)));


    public static final RegistryObject<Block> AQUARIUM_GLASS_PANE = registerBlock("aquarium_glass_pane",
            ()-> new AquariumGlassPane(BlockBehaviour.Properties.copy(Blocks.GLASS_PANE).mapColor(MapColor.WATER)));

    public static final RegistryObject<Block> CLEAR_AQUARIUM_GLASS_PANE = registerBlock("clear_aquarium_glass_pane",
            ()-> new AquariumGlassPane(BlockBehaviour.Properties.copy(Blocks.GLASS_PANE).mapColor(MapColor.NONE)));

    public static final RegistryObject<Block> INFERNAL_AQUARIUM_GLASS_PANE = registerBlock("infernal_aquarium_glass_pane",
            ()-> new AquariumGlassPane(BlockBehaviour.Properties.copy(Blocks.GLASS_PANE).mapColor(MapColor.FIRE)));



    public static final RegistryObject<Block> RADON_AQUARIUM_GLASS = registerBlock("radon_aquarium_glass",
            ()-> new AquariumGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.COLOR_LIGHT_GREEN)));

    public static final RegistryObject<Block> RADON_AQUARIUM_GLASS_PANE = registerBlock("radon_aquarium_glass_pane",
            ()-> new AquariumGlassPane(BlockBehaviour.Properties.copy(Blocks.GLASS_PANE).mapColor(MapColor.COLOR_LIGHT_GREEN)));

    public static final RegistryObject<Block> TINTED_RADON_AQUARIUM_GLASS = registerBlock("tinted_radon_aquarium_glass",
            ()-> new TintedAquariumGlassBlock(BlockBehaviour.Properties.copy(Blocks.TINTED_GLASS).mapColor(MapColor.COLOR_GREEN)));



    public static final RegistryObject<Block> SUGAR_AQUARIUM_GLASS = registerBlock("sugar_aquarium_glass",
            ()-> new AquariumGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistryObject<Block> SUGAR_AQUARIUM_GLASS_PANE = registerBlock("sugar_aquarium_glass_pane",
            ()-> new AquariumGlassPane(BlockBehaviour.Properties.copy(Blocks.GLASS_PANE).mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistryObject<Block> TINTED_SUGAR_AQUARIUM_GLASS = registerBlock("tinted_sugar_aquarium_glass",
            ()-> new TintedAquariumGlassBlock(BlockBehaviour.Properties.copy(Blocks.TINTED_GLASS).mapColor(MapColor.COLOR_PURPLE)));



    public static final RegistryObject<Block> DEAD_LIVE_ROCK = registerBlock("dead_live_rock",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).forceSolidOn()
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(1.5F, 6.0F)));

    public static final RegistryObject<Block> DEAD_POROUS_LIVE_ROCK = registerBlock("dead_porous_live_rock",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).forceSolidOn()
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(1.5F, 6.0F)));

    public static final RegistryObject<Block> LIVE_ROCK = registerBlock("live_rock",
            () -> new LiveRockBlock(DEAD_LIVE_ROCK.get(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));

    public static final RegistryObject<Block> POROUS_LIVE_ROCK = registerBlock("porous_live_rock",
            () -> new LiveRockBlock(DEAD_POROUS_LIVE_ROCK.get(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));


    public static final RegistryObject<Block> GREEN_ALGAE_LIVE_ROCK = registerBlock("green_algae_live_rock",
            () -> new AlgaeLiveRockBlock(DEAD_LIVE_ROCK.get(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));

    public static final RegistryObject<Block> RED_ALGAE_LIVE_ROCK = registerBlock("red_algae_live_rock",
            () -> new AlgaeLiveRockBlock(DEAD_POROUS_LIVE_ROCK.get(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));



    public static final RegistryObject<Block> GREEN_ALGAE_BLOCK = registerBlock("green_algae_block",
            () -> new AlgaeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN)
                    .strength(0.5F, 2.5F).sound(SoundType.WET_GRASS)));

    public static final RegistryObject<Block> GREEN_ALGAE_CARPET = registerBlock("green_algae_carpet",
            () -> new AlgaeCarpetBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(0.1F)
                    .sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> CAULERPA = registerBlock("caulerpa",
            () -> new AlgaeGrowthBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).replaceable()
                    .noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));


    public static final RegistryObject<Block> RED_ALGAE_BLOCK = registerBlock("red_algae_block",
            () -> new AlgaeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED)
                    .strength(0.5F, 2.5F).sound(SoundType.WET_GRASS)));

    public static final RegistryObject<Block> RED_ALGAE_CARPET = registerBlock("red_algae_carpet",
            () -> new AlgaeCarpetBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).strength(0.1F)
                    .sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> RED_ALGAE = registerBlock("red_algae",
            () -> new AlgaeGrowthBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).replaceable()
                    .noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> RED_ALGAE_FAN = BLOCKS.register("red_algae_fan",
            () -> new AlgaeFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).replaceable()
                    .noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> RED_ALGAE_WALL_FAN = BLOCKS.register("red_algae_wall_fan",
            () -> new AlgaeWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).replaceable()
                    .noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> DRAGONS_BREATH_ALGAE = registerBlock("dragons_breath_algae",
            () -> new AlgaeGrowthBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).replaceable()
                    .noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));


    public static final RegistryObject<Block> STROMATOLITE = registerBlock("stromatolite",
            () -> new StromatoliteBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(1.5F, 6.0F)));

    public static final RegistryObject<Block> FOSSIL_STROMATOLITE = registerBlock("fossil_stromatolite",
            () -> new StromatoliteBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(1.5F, 6.0F)));

    public static final RegistryObject<Block> STROMATOLITE_GROWTHS = registerBlock("stromatolite_growths",
            () -> new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().noCollission().instabreak()));

    public static final RegistryObject<Block> FOSSIL_STROMATOLITE_GROWTHS = registerBlock("fossil_stromatolite_growths",
            () -> new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().noCollission().instabreak()));

    public static final RegistryObject<Block> STROMATOLITE_BLOCK = registerBlock("stromatolite_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).forceSolidOn()
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().instabreak()));

    public static final RegistryObject<Block> CUT_STROMATOLITE_BLOCK = registerBlock("cut_stromatolite_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_YELLOW).forceSolidOn()
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().instabreak()));

    public static final RegistryObject<Block> FOSSIL_STROMATOLITE_BLOCK = registerBlock("fossil_stromatolite_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).forceSolidOn()
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(1.5F, 6.0F)));


    public static final RegistryObject<Block> STROMATOLITE_BRICKS = registerBlock("stromatolite_bricks",
            ()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> STROMATOLITE_BRICKS_SLAB = registerBlock("stromatolite_bricks_slab",
            ()-> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> STROMATOLITE_BRICKS_STAIRS = registerBlock("stromatolite_bricks_stairs",
            ()-> new StairBlock(() -> YAFMBlocks.STROMATOLITE_BRICKS.get().defaultBlockState(),BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> STROMATOLITE_BRICKS_WALL = registerBlock("stromatolite_bricks_wall",
            ()-> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));



    private static <T extends Block> Supplier<T> registerBlockWithItem(String key, Supplier<T> block, Function<Supplier<T>, Item> item) {
        Supplier<T> entry = create(key, block);
        YAFMItems.ITEMS.register(key, () -> item.apply(entry));
        return entry;
    }

    private static <T extends Block> Supplier<T> create(String key, Supplier<T> block) {
        return BLOCKS.register(key, block);
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block){
        return YAFMItems.ITEMS.register(name, ()-> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
