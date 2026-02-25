package net.voidarkana.fintastic.common.item;

import net.minecraft.core.Direction;
import net.minecraft.world.item.*;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.block.FintyBlocks;
import net.voidarkana.fintastic.common.entity.FintyEntities;
import net.voidarkana.fintastic.common.item.custom.*;
import net.voidarkana.fintastic.common.sound.FintySounds;

@Mod.EventBusSubscriber(modid = Fintastic.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FintyItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Fintastic.MOD_ID);

    public static final RegistryObject<Item> FISHNET = ITEMS.register("fishnet",
            () -> new FishnetItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> FISHING_HAT = ITEMS.register("fishing_hat",
            ()-> new HatItem(YAFMArmorMaterials.HAT, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> FEATHERBACK_SPAWN_EGG = ITEMS.register("featherback_spawn_egg",
            () -> new FishSpawnEggItem(FintyEntities.FEATHERBACK, 0x82827a, 0xd7d7a7, new Item.Properties()));

    public static final RegistryObject<Item> FEATHERBACK_BUCKET = ITEMS.register("featherback_bucket", () -> {
        return new FishBucketItem(FintyEntities.FEATHERBACK, () -> {
            return Fluids.WATER;
        }, Items.BUCKET, false, (new Item.Properties()).stacksTo(1));
    });


    public static final RegistryObject<Item> MINNOW_SPAWN_EGG = ITEMS.register("minnow_spawn_egg",
            () -> new FishSpawnEggItem(FintyEntities.MINNOW, 0x40cb97, 0xd04a20, new Item.Properties()));

    public static final RegistryObject<Item> MINNOW_BUCKET = ITEMS.register("minnow_bucket", () -> {
        return new FishBucketItem(FintyEntities.MINNOW, () -> {
            return Fluids.WATER;
        }, Items.BUCKET, false, (new Item.Properties()).stacksTo(1));
    });


    public static final RegistryObject<Item> CATFISH_SPAWN_EGG = ITEMS.register("catfish_spawn_egg",
            () -> new FishSpawnEggItem(FintyEntities.CATFISH, 0x33485b, 0x7f8c96, new Item.Properties()));

    public static final RegistryObject<Item> CATFISH_BUCKET = ITEMS.register("catfish_bucket", () -> {
        return new FishBucketItem(FintyEntities.CATFISH, () -> {
            return Fluids.WATER;
        }, Items.BUCKET, false, (new Item.Properties()).stacksTo(1));
    });


    public static final RegistryObject<Item> GUPPY_SPAWN_EGG = ITEMS.register("guppy_spawn_egg",
            () -> new FishSpawnEggItem(FintyEntities.GUPPY, 0x343a5b, 0x71788b, new Item.Properties()));

    public static final RegistryObject<Item> GUPPY_BUCKET = ITEMS.register("guppy_bucket", () -> {
        return new FishBucketItem(FintyEntities.GUPPY, () -> {
            return Fluids.WATER;
        }, Items.BUCKET, false, (new Item.Properties()).stacksTo(1));
    });


    public static final RegistryObject<Item> FRESHWATER_SHARK_SPAWN_EGG = ITEMS.register("freshwater_shark_spawn_egg",
            () -> new FishSpawnEggItem(FintyEntities.SHARKMINNOW, 0x292929, 0x7b0b0b, new Item.Properties()));

    public static final RegistryObject<Item> FRESHWATER_SHARK_BUCKET = ITEMS.register("freshwater_shark_bucket", () -> {
        return new FishBucketItem(FintyEntities.SHARKMINNOW, () -> {
            return Fluids.WATER;
        }, Items.BUCKET, false, (new Item.Properties()).stacksTo(1));
    });


    public static final RegistryObject<Item> PLECO_SPAWN_EGG = ITEMS.register("pleco_spawn_egg",
            () -> new FishSpawnEggItem(FintyEntities.PLECO, 0x24211e, 0x97874b, new Item.Properties()));

    public static final RegistryObject<Item> PLECO_BUCKET = ITEMS.register("pleco_bucket", () -> {
        return new FishBucketItem(FintyEntities.PLECO, () -> {
            return Fluids.WATER;
        }, Items.BUCKET, false, (new Item.Properties()).stacksTo(1));
    });


    public static final RegistryObject<Item> ARAPAIMA_BUCKET = ITEMS.register("arapaima_bucket", () -> {
        return new FishBucketItem(FintyEntities.ARAPAIMA, () -> {
            return Fluids.WATER;
        }, Items.BUCKET, false, (new Item.Properties()).stacksTo(1));
    });

    public static final RegistryObject<Item> ARAPAIMA_FISHNET = ITEMS.register("arapaima_fishnet",
            () -> new FullFishnetItem(FintyEntities.ARAPAIMA, new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> ARAPAIMA_SPAWN_EGG = ITEMS.register("arapaima_spawn_egg",
            () -> new FishSpawnEggItem(FintyEntities.ARAPAIMA, 0x1b2321, 0x521c1a, new Item.Properties()));



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




    public static final RegistryObject<Item> ARTEMIA_SPAWN_EGG = ITEMS.register("artemia_spawn_egg",
            () -> new FishSpawnEggItem(FintyEntities.ARTEMIA, 0xb66259, 0x9e3c0e, new Item.Properties()));

    public static final RegistryObject<Item> ARTEMIA_BUCKET = ITEMS.register("artemia_bucket", () -> {
        return new FishBucketItem(FintyEntities.ARTEMIA, () -> {
            return Fluids.WATER;
        }, Items.BUCKET, false, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1));});



    public static final RegistryObject<Item> DAPHNIA_SPAWN_EGG = ITEMS.register("daphnia_spawn_egg",
            () -> new FishSpawnEggItem(FintyEntities.DAPHNIA, 0x90ba6d, 0xbdcdb0, new Item.Properties()));

    public static final RegistryObject<Item> DAPHNIA_BUCKET = ITEMS.register("daphnia_bucket", () -> {
        return new FishBucketItem(FintyEntities.DAPHNIA, () -> {
            return Fluids.WATER;
        }, Items.BUCKET, false, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1));});


    public static final RegistryObject<Item> MOONY_SPAWN_EGG = ITEMS.register("moony_spawn_egg",
            () -> new FishSpawnEggItem(FintyEntities.MOONY, 0x99adc7, 0xdfbb1a, new Item.Properties()));

    public static final RegistryObject<Item> MOONY_BUCKET = ITEMS.register("moony_bucket", () -> {
        return new FishBucketItem(FintyEntities.MOONY, () -> {
            return Fluids.WATER;
        }, Items.BUCKET, false, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1));});


    public static final RegistryObject<Item> COELACANTH_FISHNET = ITEMS.register("coelacanth_fishnet",
            () -> new FullFishnetItem(FintyEntities.COELACANTH, new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> COELACANTH_SPAWN_EGG = ITEMS.register("coelacanth_spawn_egg",
            () -> new FishSpawnEggItem(FintyEntities.COELACANTH, 0x152342, 0x6090a2, new Item.Properties()));

    public static final RegistryObject<Item> COELACANTH_BUCKET = ITEMS.register("baby_coelacanth_bucket", () -> {
        return new FishBucketItem(FintyEntities.COELACANTH, () -> {
            return Fluids.WATER;
        }, Items.BUCKET, false, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1));});


    public static final RegistryObject<Item> GOURAMI_SPAWN_EGG = ITEMS.register("gourami_spawn_egg",
            () -> new FishSpawnEggItem(FintyEntities.GOURAMI, 0x241613, 0xcc3224, new Item.Properties()));

    public static final RegistryObject<Item> GOURAMI_BUCKET = ITEMS.register("gourami_bucket", () -> {
        return new FishBucketItem(FintyEntities.GOURAMI, () -> {
            return Fluids.WATER;
        }, Items.BUCKET, false, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1));});


    public static final RegistryObject<Item> SEA_GRAPE_SALAD = ITEMS.register("sea_grape_salad",
            () -> new StackableBowlFoodItem(new Item.Properties().food(YAFMFoods.SEA_GRAPE_SALAD).stacksTo(16)));

    public static final RegistryObject<Item> FRESH_MUSIC_DISC = ITEMS.register("fresh_music_disc",
            () -> new RecordItem(5, FintySounds.FRESH, new Item.Properties().stacksTo(1)
                    .rarity(Rarity.RARE), 3840));

    public static final RegistryObject<Item> SALTY_MUSIC_DISC = ITEMS.register("salty_music_disc",
            () -> new RecordItem(6, FintySounds.SALTY, new Item.Properties().stacksTo(1)
                    .rarity(Rarity.RARE), 2400));


    public static final RegistryObject<Item> AXOLOTL_MUSIC_DISC = ITEMS.register("axolotl_music_disc",
            () -> new RecordItem(7, FintySounds.AXOLOTL, new Item.Properties().stacksTo(1)
                    .rarity(Rarity.RARE), 6120));

    public static final RegistryObject<Item> DRAGONFISH_MUSIC_DISC = ITEMS.register("dragonfish_music_disc",
            () -> new RecordItem(8, FintySounds.DRAGONFISH, new Item.Properties().stacksTo(1)
                    .rarity(Rarity.RARE), 7680));

    public static final RegistryObject<Item> SHUNJI_MUSIC_DISC = ITEMS.register("shunji_music_disc",
            () -> new RecordItem(9, FintySounds.SHUNJI, new Item.Properties().stacksTo(1)
                    .rarity(Rarity.RARE), 4960));

    public static final RegistryObject<Item> RED_ALGAE_FAN = ITEMS.register("red_algae_fan",
            ()-> new StandingAndWallBlockItem(FintyBlocks.RED_ALGAE_FAN.get(), FintyBlocks.RED_ALGAE_WALL_FAN.get(),
                    new Item.Properties(), Direction.DOWN));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
