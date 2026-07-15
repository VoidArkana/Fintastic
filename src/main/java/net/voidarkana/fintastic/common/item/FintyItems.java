package net.voidarkana.fintastic.common.item;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.block.FintyBlocks;
import net.voidarkana.fintastic.common.entity.FintyEntities;
import net.voidarkana.fintastic.common.item.custom.*;
import net.voidarkana.fintastic.common.item.custom.spawneggs.*;

public class FintyItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Fintastic.MOD_ID);

    public static final DeferredItem<Item> FISHNET = ITEMS.register("fishnet",
            () -> new FishnetItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> FISHING_HAT = ITEMS.register("fishing_hat",
            ()-> new HatItem(YAFMArmorMaterials.HAT, ArmorItem.Type.HELMET, new Item.Properties()
                    .durability(ArmorItem.Type.HELMET.getDurability(YAFMArmorMaterials.HAT_DURABILITY_MULTIPLIER))));

    public static final DeferredItem<Item> FEATHERBACK_SPAWN_EGG = ITEMS.register("featherback_spawn_egg",
            () -> new FeatherbackSpawnEgg(FintyEntities.FEATHERBACK, 0xffffff, 0xffffff, new Item.Properties()));
    public static final DeferredItem<Item> FEATHERBACK_BUCKET = ITEMS.register("featherback_bucket", () -> new FishBucketItem(FintyEntities.FEATHERBACK, () -> Fluids.WATER, Items.BUCKET, false, (new Item.Properties()).stacksTo(1)));
    public static final DeferredItem<Item> FEATHERBACK = ITEMS.register("featherback",
            () -> new Item(new Item.Properties().craftRemainder(Items.BONE_MEAL)));


    public static final DeferredItem<Item> MINNOW_SPAWN_EGG = ITEMS.register("minnow_spawn_egg",
            () -> new MinnowSpawnEgg(FintyEntities.MINNOW, 0xffffff, 0xffffff, new Item.Properties()));
    public static final DeferredItem<Item> MINNOW_BUCKET = ITEMS.register("minnow_bucket", () -> new FishBucketItem(FintyEntities.MINNOW, () -> Fluids.WATER, Items.BUCKET, false, (new Item.Properties()).stacksTo(1)));
    public static final DeferredItem<Item> MINNOW = ITEMS.register("minnow",
            () -> new Item(new Item.Properties().food(YAFMFoods.RAW_FISH_TINY)));


    public static final DeferredItem<Item> CATFISH_SPAWN_EGG = ITEMS.register("catfish_spawn_egg",
            () -> new CatfishSpawnEgg(FintyEntities.CATFISH, 0xffffff, 0xffffff, new Item.Properties()));
    public static final DeferredItem<Item> CATFISH_BUCKET = ITEMS.register("catfish_bucket", () -> new FishBucketItem(FintyEntities.CATFISH, () -> Fluids.WATER, Items.BUCKET, false, (new Item.Properties()).stacksTo(1)));
    public static final DeferredItem<Item> CATFISH = ITEMS.register("catfish",
            () -> new Item(new Item.Properties().food(YAFMFoods.RAW_FISH_MED)));


    public static final DeferredItem<Item> GUPPY_SPAWN_EGG = ITEMS.register("guppy_spawn_egg",
            () -> new FishSpawnEggItem(FintyEntities.GUPPY, 0xffffff, 0xffffff, new Item.Properties()));
    public static final DeferredItem<Item> GUPPY_BUCKET = ITEMS.register("guppy_bucket", () -> new FishBucketItem(FintyEntities.GUPPY, () -> Fluids.WATER, Items.BUCKET, false, (new Item.Properties()).stacksTo(1)));
    public static final DeferredItem<Item> GUPPY = ITEMS.register("guppy",
            () -> new Item(new Item.Properties().food(YAFMFoods.RAW_FISH_TINY)));


    public static final DeferredItem<Item> FRESHWATER_SHARK_SPAWN_EGG = ITEMS.register("freshwater_shark_spawn_egg",
            () -> new SharkminnowSpawnEgg(FintyEntities.SHARKMINNOW, 0xffffff, 0xffffff, new Item.Properties()));
    public static final DeferredItem<Item> FRESHWATER_SHARK_BUCKET = ITEMS.register("freshwater_shark_bucket", () -> new FishBucketItem(FintyEntities.SHARKMINNOW, () -> Fluids.WATER, Items.BUCKET, false, (new Item.Properties()).stacksTo(1)));
    public static final DeferredItem<Item> SHARKMINNOW = ITEMS.register("sharkminnow",
            () -> new Item(new Item.Properties().food(YAFMFoods.RAW_FISH).craftRemainder(Items.BONE_MEAL)));


    public static final DeferredItem<Item> PLECO_SPAWN_EGG = ITEMS.register("pleco_spawn_egg",
            () -> new PlecoSpawnEgg(FintyEntities.PLECO, 0xffffff, 0xffffff, new Item.Properties()));
    public static final DeferredItem<Item> PLECO_BUCKET = ITEMS.register("pleco_bucket", () -> new FishBucketItem(FintyEntities.PLECO, () -> Fluids.WATER, Items.BUCKET, false, (new Item.Properties()).stacksTo(1)));
    public static final DeferredItem<Item> PLECO = ITEMS.register("pleco",
            () -> new Item(new Item.Properties().food(YAFMFoods.RAW_FISH).craftRemainder(Items.BONE_MEAL)));


    public static final DeferredItem<Item> ARAPAIMA_BUCKET = ITEMS.register("arapaima_bucket", () -> new FishBucketItem(FintyEntities.ARAPAIMA, () -> Fluids.WATER, Items.BUCKET, false, (new Item.Properties()).stacksTo(1)));
    public static final DeferredItem<Item> ARAPAIMA_FISHNET = ITEMS.register("arapaima_fishnet",
            () -> new FullFishnetItem(FintyEntities.ARAPAIMA, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> ARAPAIMA_SPAWN_EGG = ITEMS.register("arapaima_spawn_egg",
            () -> new FishSpawnEggItem(FintyEntities.ARAPAIMA, 0xffffff, 0xffffff, new Item.Properties()));
    public static final DeferredItem<Item> ARAPAIMA = ITEMS.register("arapaima",
            () -> new Item(new Item.Properties().craftRemainder(Items.BONE_MEAL)));


    public static final DeferredItem<Item> REGULAR_FEED = ITEMS.register("regular_feed",
            () -> new FishFeedItem(new Item.Properties(), 1));

    public static final DeferredItem<Item> QUALITY_FEED = ITEMS.register("quality_feed",
            () -> new FishFeedItem(new Item.Properties().rarity(Rarity.UNCOMMON), 2));

    public static final DeferredItem<Item> GREAT_FEED = ITEMS.register("great_feed",
            () -> new FishFeedItem(new Item.Properties().rarity(Rarity.RARE), 3));

    public static final DeferredItem<Item> PREMIUM_FEED = ITEMS.register("premium_feed",
            () -> new FishFeedItem(new Item.Properties().rarity(Rarity.EPIC), 4));

    public static final DeferredItem<Item> BAD_FEED = ITEMS.register("bad_feed",
            () -> new FishFeedItem(new Item.Properties(), 0));


    public static final DeferredItem<Item> RAW_FISH = ITEMS.register("raw_fish",
            () -> new Item(new Item.Properties().food(YAFMFoods.RAW_FISH)));

    public static final DeferredItem<Item> COOKED_FISH = ITEMS.register("cooked_fish",
            () -> new Item(new Item.Properties().food(YAFMFoods.COOKED_FISH)));




    public static final DeferredItem<Item> FAIRY_SHRIMP_SPAWN_EGG = ITEMS.register("artemia_spawn_egg",
            () -> new FairyShrimpSpawnEgg(FintyEntities.FAIRY_SHRIMP, 0xffffff, 0xffffff, new Item.Properties()));
    public static final DeferredItem<Item> ARTEMIA_BUCKET = ITEMS.register("artemia_bucket", () -> new FishBucketItem(FintyEntities.FAIRY_SHRIMP, () -> Fluids.WATER, Items.BUCKET, false, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final DeferredItem<Item> FAIRY_SHRIMP = ITEMS.register("fairy_shrimp",
            () -> new Item(new Item.Properties().food(YAFMFoods.RAW_FISH_TINY)));



    public static final DeferredItem<Item> DAPHNIA_SPAWN_EGG = ITEMS.register("daphnia_spawn_egg",
            () -> new FishSpawnEggItem(FintyEntities.DAPHNIA, 0xffffff, 0xffffff, new Item.Properties()));
    public static final DeferredItem<Item> DAPHNIA_BUCKET = ITEMS.register("daphnia_bucket", () -> new FishBucketItem(FintyEntities.DAPHNIA, () -> Fluids.WATER, Items.BUCKET, false, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final DeferredItem<Item> DAPHNIA = ITEMS.register("daphnia",
            () -> new Item(new Item.Properties().food(YAFMFoods.RAW_FISH_TINY)));


    public static final DeferredItem<Item> MOONY_SPAWN_EGG = ITEMS.register("moony_spawn_egg",
            () -> new MoonySpawnEgg(FintyEntities.MOONY, 0xffffff, 0xffffff, new Item.Properties()));
    public static final DeferredItem<Item> MOONY_BUCKET = ITEMS.register("moony_bucket", () -> new FishBucketItem(FintyEntities.MOONY, () -> Fluids.WATER, Items.BUCKET, false, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final DeferredItem<Item> MOONY = ITEMS.register("moony",
            () -> new Item(new Item.Properties().food(YAFMFoods.RAW_FISH)));


    public static final DeferredItem<Item> COELACANTH_FISHNET = ITEMS.register("coelacanth_fishnet",
            () -> new FullFishnetItem(FintyEntities.COELACANTH, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> COELACANTH_SPAWN_EGG = ITEMS.register("coelacanth_spawn_egg",
            () -> new FishSpawnEggItem(FintyEntities.COELACANTH, 0xffffff, 0xffffff, new Item.Properties()));
    public static final DeferredItem<Item> COELACANTH_BUCKET = ITEMS.register("baby_coelacanth_bucket", () -> new FishBucketItem(FintyEntities.COELACANTH, () -> Fluids.WATER, Items.BUCKET, false, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final DeferredItem<Item> COELACANTH = ITEMS.register("coelacanth",
            () -> new Item(new Item.Properties().craftRemainder(Items.BONE_MEAL)));


    public static final DeferredItem<Item> GOURAMI_SPAWN_EGG = ITEMS.register("gourami_spawn_egg",
            () -> new GouramiSpawnEgg(FintyEntities.GOURAMI, 0xffffff, 0xffffff, new Item.Properties()));
    public static final DeferredItem<Item> GOURAMI_BUCKET = ITEMS.register("gourami_bucket", () -> new FishBucketItem(FintyEntities.GOURAMI, () -> Fluids.WATER, Items.BUCKET, false, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final DeferredItem<Item> GOURAMI = ITEMS.register("gourami",
            () -> new Item(new Item.Properties().food(YAFMFoods.RAW_FISH).craftRemainder(Items.BONE_MEAL)));


    public static final DeferredItem<Item> COPEPOD_SPAWN_EGG = ITEMS.register("copepod_spawn_egg",
            () -> new CopepodSpawnEgg(FintyEntities.COPEPOD, 0xffffff, 0xffffff, new Item.Properties()));
    public static final DeferredItem<Item> COPEPOD_BUCKET = ITEMS.register("copepod_bucket", () -> new FishBucketItem(FintyEntities.COPEPOD, () -> Fluids.WATER, Items.BUCKET, false, (new Item.Properties()).stacksTo(1)));
    public static final DeferredItem<Item> COPEPOD = ITEMS.register("copepod",
            () -> new Item(new Item.Properties().food(YAFMFoods.RAW_FISH_TINY)));

    public static final DeferredItem<Item> SALMON_SPAWN_EGG = ITEMS.register("fintastic_salmon_spawn_egg",
            () -> new SalmonSpawnEgg(FintyEntities.SALMON, 0xffffff, 0xffffff, new Item.Properties()));
    public static final DeferredItem<Item> SALMON_BUCKET = ITEMS.register("fintastic_salmon_bucket", () -> new FishBucketItem(FintyEntities.SALMON, () -> Fluids.WATER, Items.BUCKET, false, (new Item.Properties()).stacksTo(1)));

    public static final DeferredItem<Item> COD_SPAWN_EGG = ITEMS.register("fintastic_cod_spawn_egg",
            () -> new CodSpawnEgg(FintyEntities.COD, 0xffffff, 0xffffff, new Item.Properties()));
    public static final DeferredItem<Item> COD_BUCKET = ITEMS.register("fintastic_cod_bucket", () -> new FishBucketItem(FintyEntities.COD, () -> Fluids.WATER, Items.BUCKET, false, (new Item.Properties()).stacksTo(1)));

    public static final DeferredItem<Item> DWARF_FROG_SPAWN_EGG = ITEMS.register("dwarf_frog_spawn_egg",
            () -> new DwarfFrogSpawnEgg(FintyEntities.DWARF_FROG, 0xddbe9e, 0x6d654e, new Item.Properties()));
    public static final DeferredItem<Item> DWARF_FROG_BUCKET = ITEMS.register("dwarf_frog_bucket", () -> new FishBucketItem(FintyEntities.DWARF_FROG, () -> Fluids.WATER, Items.BUCKET, false, (new Item.Properties()).stacksTo(1)));
    public static final DeferredItem<Item> DWARF_FROG_TADPOLE_BUCKET = ITEMS.register("dwarf_frog_tadpole_bucket", () -> new FishBucketItem(FintyEntities.DWARF_FROG, () -> Fluids.WATER, Items.BUCKET, false, (new Item.Properties()).stacksTo(1)));
    public static final DeferredItem<Item> DWARF_FROG = ITEMS.register("dwarf_frog",
            () -> new Item(new Item.Properties().food(YAFMFoods.RAW_FISH)));
    public static final DeferredItem<Item> COOKED_DWARF_FROG = ITEMS.register("cooked_dwarf_frog",
            () -> new Item(new Item.Properties().food(YAFMFoods.COOKED_FISH)));

    public static final DeferredItem<Item> SMALL_CATFISH_SPAWN_EGG = ITEMS.register("small_catfish_spawn_egg",
            () -> new SmallCatfishSpawnEgg(FintyEntities.SMALL_CATFISH, 0xffffff, 0xffffff, new Item.Properties()));

    public static final DeferredItem<Item> SMALL_CATFISH_BUCKET = ITEMS.register("small_catfish_bucket", () -> new FishBucketItem(FintyEntities.SMALL_CATFISH, () -> Fluids.WATER, Items.BUCKET, false, (new Item.Properties()).stacksTo(1)));
    public static final DeferredItem<Item> SMALL_CATFISH = ITEMS.register("small_catfish",
            () -> new Item(new Item.Properties().food(YAFMFoods.RAW_FISH_TINY)));

    public static final DeferredItem<Item> SEA_GRAPE_SALAD = ITEMS.register("sea_grape_salad",
            () -> new StackableBowlFoodItem(new Item.Properties().food(YAFMFoods.SEA_GRAPE_SALAD).stacksTo(16)));

    public static final DeferredItem<Item> FRESH_MUSIC_DISC = ITEMS.register("fresh_music_disc",
            () -> new Item(new Item.Properties().stacksTo(1)
                    .rarity(Rarity.RARE).jukeboxPlayable(jukeboxSong("fresh"))));

    public static final DeferredItem<Item> SALTY_MUSIC_DISC = ITEMS.register("salty_music_disc",
            () -> new Item(new Item.Properties().stacksTo(1)
                    .rarity(Rarity.RARE).jukeboxPlayable(jukeboxSong("salty"))));

    public static final DeferredItem<Item> LOTUS_ROOT = ITEMS.register("lotus_root",
            () -> new Item(new Item.Properties().food(YAFMFoods.LOTUS_ROOT)));

    public static final DeferredItem<Item> BAKED_LOTUS_ROOT = ITEMS.register("baked_lotus_root",
            () -> new Item(new Item.Properties().food(YAFMFoods.COOKED_LOTUS_ROOT)));


    public static final DeferredItem<Item> AXOLOTL_MUSIC_DISC = ITEMS.register("axolotl_music_disc",
            () -> new Item(new Item.Properties().stacksTo(1)
                    .rarity(Rarity.RARE).jukeboxPlayable(jukeboxSong("axolotl"))));

    public static final DeferredItem<Item> DRAGONFISH_MUSIC_DISC = ITEMS.register("dragonfish_music_disc",
            () -> new Item(new Item.Properties().stacksTo(1)
                    .rarity(Rarity.RARE).jukeboxPlayable(jukeboxSong("dragonfish"))));

    public static final DeferredItem<Item> SHUNJI_MUSIC_DISC = ITEMS.register("shunji_music_disc",
            () -> new Item(new Item.Properties().stacksTo(1)
                    .rarity(Rarity.RARE).jukeboxPlayable(jukeboxSong("shunji"))));

    public static final DeferredItem<Item> RED_ALGAE_FAN = ITEMS.register("red_algae_fan",
            ()-> new StandingAndWallBlockItem(FintyBlocks.RED_ALGAE_FAN.get(), FintyBlocks.RED_ALGAE_WALL_FAN.get(),
                    new Item.Properties(), Direction.DOWN));

    private static ResourceKey<JukeboxSong> jukeboxSong(String name) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, Fintastic.location(name));
    }

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
        YAFMArmorMaterials.register(eventBus);
    }
}
