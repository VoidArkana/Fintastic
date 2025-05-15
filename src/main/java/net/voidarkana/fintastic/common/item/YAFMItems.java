package net.voidarkana.fintastic.common.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.entity.YAFMEntities;
import net.voidarkana.fintastic.common.item.custom.*;
import net.voidarkana.fintastic.common.sound.YAFMSounds;

@Mod.EventBusSubscriber(modid = Fintastic.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class YAFMItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Fintastic.MOD_ID);

    public static final RegistryObject<Item> FEATHERBACK_SPAWN_EGG = ITEMS.register("featherback_spawn_egg",
            () -> new FishSpawnEggItem(YAFMEntities.FEATHERBACK, 0x82827a, 0xd7d7a7, new Item.Properties()));

    public static final RegistryObject<Item> FEATHERBACK_BUCKET = ITEMS.register("featherback_bucket", () -> {
        return new FishBucketItem(YAFMEntities.FEATHERBACK, () -> {
            return Fluids.WATER;
        }, Items.BUCKET, false, (new Item.Properties()).stacksTo(1));
    });


    public static final RegistryObject<Item> MINNOW_SPAWN_EGG = ITEMS.register("minnow_spawn_egg",
            () -> new FishSpawnEggItem(YAFMEntities.MINNOW, 0x40cb97, 0xd04a20, new Item.Properties()));

    public static final RegistryObject<Item> MINNOW_BUCKET = ITEMS.register("minnow_bucket", () -> {
        return new FishBucketItem(YAFMEntities.MINNOW, () -> {
            return Fluids.WATER;
        }, Items.BUCKET, false, (new Item.Properties()).stacksTo(1));
    });


    public static final RegistryObject<Item> CATFISH_SPAWN_EGG = ITEMS.register("catfish_spawn_egg",
            () -> new FishSpawnEggItem(YAFMEntities.CATFISH, 0x33485b, 0x7f8c96, new Item.Properties()));

    public static final RegistryObject<Item> CATFISH_BUCKET = ITEMS.register("catfish_bucket", () -> {
        return new FishBucketItem(YAFMEntities.CATFISH, () -> {
            return Fluids.WATER;
        }, Items.BUCKET, false, (new Item.Properties()).stacksTo(1));
    });


    public static final RegistryObject<Item> GUPPY_SPAWN_EGG = ITEMS.register("guppy_spawn_egg",
            () -> new FishSpawnEggItem(YAFMEntities.GUPPY, 0x343a5b, 0x71788b, new Item.Properties()));

    public static final RegistryObject<Item> GUPPY_BUCKET = ITEMS.register("guppy_bucket", () -> {
        return new FishBucketItem(YAFMEntities.GUPPY, () -> {
            return Fluids.WATER;
        }, Items.BUCKET, false, (new Item.Properties()).stacksTo(1));
    });


    public static final RegistryObject<Item> FRESHWATER_SHARK_SPAWN_EGG = ITEMS.register("freshwater_shark_spawn_egg",
            () -> new FishSpawnEggItem(YAFMEntities.FRESHWATER_SHARK, 0x292929, 0x7b0b0b, new Item.Properties()));

    public static final RegistryObject<Item> FRESHWATER_SHARK_BUCKET = ITEMS.register("freshwater_shark_bucket", () -> {
        return new FishBucketItem(YAFMEntities.FRESHWATER_SHARK, () -> {
            return Fluids.WATER;
        }, Items.BUCKET, false, (new Item.Properties()).stacksTo(1));
    });


    public static final RegistryObject<Item> PLECO_SPAWN_EGG = ITEMS.register("pleco_spawn_egg",
            () -> new FishSpawnEggItem(YAFMEntities.PLECO, 0x24211e, 0x97874b, new Item.Properties()));

    public static final RegistryObject<Item> PLECO_BUCKET = ITEMS.register("pleco_bucket", () -> {
        return new FishBucketItem(YAFMEntities.PLECO, () -> {
            return Fluids.WATER;
        }, Items.BUCKET, false, (new Item.Properties()).stacksTo(1));
    });


    public static final RegistryObject<Item> ARAPAIMA_FISHNET = ITEMS.register("arapaima_fishnet",
            () -> new FullFishnetItem(YAFMEntities.ARAPAIMA, new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> ARAPAIMA_SPAWN_EGG = ITEMS.register("arapaima_spawn_egg",
            () -> new FishSpawnEggItem(YAFMEntities.ARAPAIMA, 0x1b2321, 0x521c1a, new Item.Properties()));



    public static final RegistryObject<Item> REGULAR_FEED = ITEMS.register("regular_feed",
            () -> new FishFeedItem(new Item.Properties(), 1));

    public static final RegistryObject<Item> QUALITY_FEED = ITEMS.register("quality_feed",
            () -> new FishFeedItem(new Item.Properties().rarity(Rarity.UNCOMMON), 2));

    public static final RegistryObject<Item> GREAT_FEED = ITEMS.register("great_feed",
            () -> new FishFeedItem(new Item.Properties().rarity(Rarity.RARE), 3));

    public static final RegistryObject<Item> PREMIUM_FEED = ITEMS.register("premium_feed",
            () -> new FishFeedItem(new Item.Properties().rarity(Rarity.EPIC), 4));

    public static final RegistryObject<Item> BAD_FEED = ITEMS.register("bad_feed",
            () -> new FishFeedItem(new Item.Properties(), 0));


    public static final RegistryObject<Item> RAW_FISH = ITEMS.register("raw_fish",
            () -> new Item(new Item.Properties().food(YAFMFoods.RAW_FISH)));

    public static final RegistryObject<Item> COOKED_FISH = ITEMS.register("cooked_fish",
            () -> new Item(new Item.Properties().food(YAFMFoods.COOKED_FISH)));

    public static final RegistryObject<Item> FISHNET = ITEMS.register("fishnet",
            () -> new FishnetItem(new Item.Properties().stacksTo(1)));



    public static final RegistryObject<Item> ARTEMIA_SPAWN_EGG = ITEMS.register("artemia_spawn_egg",
            () -> new FishSpawnEggItem(YAFMEntities.ARTEMIA, 0xb66259, 0x9e3c0e, new Item.Properties()));

    public static final RegistryObject<Item> ARTEMIA_BUCKET = ITEMS.register("artemia_bucket", () -> {
        return new FishBucketItem(YAFMEntities.ARTEMIA, () -> {
            return Fluids.WATER;
        }, Items.BUCKET, false, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1));});



    public static final RegistryObject<Item> DAPHNIA_SPAWN_EGG = ITEMS.register("daphnia_spawn_egg",
            () -> new FishSpawnEggItem(YAFMEntities.DAPHNIA, 0x90ba6d, 0xbdcdb0, new Item.Properties()));

    public static final RegistryObject<Item> DAPHNIA_BUCKET = ITEMS.register("daphnia_bucket", () -> {
        return new FishBucketItem(YAFMEntities.DAPHNIA, () -> {
            return Fluids.WATER;
        }, Items.BUCKET, false, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1));});


    public static final RegistryObject<Item> MOONY_SPAWN_EGG = ITEMS.register("moony_spawn_egg",
            () -> new FishSpawnEggItem(YAFMEntities.MOONY, 0x99adc7, 0xdfbb1a, new Item.Properties()));

    public static final RegistryObject<Item> MOONY_BUCKET = ITEMS.register("moony_bucket", () -> {
        return new FishBucketItem(YAFMEntities.MOONY, () -> {
            return Fluids.WATER;
        }, Items.BUCKET, false, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1));});


    public static final RegistryObject<Item> FRESH_MUSIC_DISC = ITEMS.register("fresh_music_disc",
            () -> new RecordItem(5, YAFMSounds.FRESH, new Item.Properties().stacksTo(1)
                    .rarity(Rarity.RARE), 3840));

    public static final RegistryObject<Item> SALTY_MUSIC_DISC = ITEMS.register("salty_music_disc",
            () -> new RecordItem(6, YAFMSounds.SALTY, new Item.Properties().stacksTo(1)
                    .rarity(Rarity.RARE), 2400));


    public static final RegistryObject<Item> AXOLOTL_MUSIC_DISC = ITEMS.register("axolotl_music_disc",
            () -> new RecordItem(7, YAFMSounds.AXOLOTL, new Item.Properties().stacksTo(1)
                    .rarity(Rarity.RARE), 6120));

    public static final RegistryObject<Item> DRAGONFISH_MUSIC_DISC = ITEMS.register("dragonfish_music_disc",
            () -> new RecordItem(8, YAFMSounds.DRAGONFISH, new Item.Properties().stacksTo(1)
                    .rarity(Rarity.RARE), 7680));

    public static final RegistryObject<Item> SHUNJI_MUSIC_DISC = ITEMS.register("shunji_music_disc",
            () -> new RecordItem(9, YAFMSounds.SHUNJI, new Item.Properties().stacksTo(1)
                    .rarity(Rarity.RARE), 4960));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
