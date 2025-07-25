package net.voidarkana.fintastic.common.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.block.YAFMBlocks;
import net.voidarkana.fintastic.common.item.YAFMItems;

import java.util.List;

@Mod.EventBusSubscriber(modid = Fintastic.MOD_ID)
public class YAFMModEvents {

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event){

        if (event.getType() == VillagerProfession.FISHERMAN){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Level 1
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(YAFMBlocks.DUCKWEED.get(), 20),
                    new ItemStack(Items.EMERALD, 1),
                    10, 8, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(YAFMBlocks.HORNWORT.get(), 20),
                    new ItemStack(Items.EMERALD, 1),
                    10, 8, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(YAFMBlocks.ANUBIAS.get(), 10),
                    new ItemStack(Items.EMERALD, 1),
                    10, 8, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(YAFMBlocks.CAULERPA.get(), 10),
                    new ItemStack(Items.EMERALD, 1),
                    10, 8, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(YAFMBlocks.SEA_GRAPES.get(), 20),
                    new ItemStack(Items.EMERALD, 1),
                    10, 8, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(YAFMItems.REGULAR_FEED.get(), 6),
                    10, 8, 0.02f));


            // Level 2
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 15),
                    new ItemStack(YAFMBlocks.DUCKWEED.get(), 1),
                    10, 8, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 15),
                    new ItemStack(YAFMBlocks.HORNWORT.get(), 2),
                    10, 8, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 15),
                    new ItemStack(YAFMBlocks.ANUBIAS.get(), 2),
                    10, 8, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 15),
                    new ItemStack(YAFMBlocks.CAULERPA.get(), 2),
                    10, 8, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 15),
                    new ItemStack(YAFMBlocks.SEA_GRAPES.get(), 5),
                    10, 8, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(YAFMItems.DAPHNIA_BUCKET.get(), 1),
                    5, 9, 0.035f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(YAFMItems.GUPPY_BUCKET.get(), 1),
                    5, 9, 0.035f));


            // Level 3

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(YAFMItems.MINNOW_BUCKET.get(), 1),
                    5, 9, 0.035f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(YAFMItems.GREAT_FEED.get(), 6),
                    5, 9, 0.035f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(YAFMItems.PLECO_BUCKET.get(), 1),
                    5, 9, 0.035f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(YAFMItems.FEATHERBACK_BUCKET.get(), 1),
                    5, 9, 0.035f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(YAFMItems.MOONY_BUCKET.get(), 1),
                    5, 9, 0.035f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(YAFMItems.GOURAMI_BUCKET.get(), 1),
                    5, 9, 0.035f));


            // Level 4

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(YAFMItems.FRESHWATER_SHARK_BUCKET.get(), 1),
                    5, 9, 0.035f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(YAFMItems.ARTEMIA_BUCKET.get(), 1),
                    5, 9, 0.035f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(YAFMItems.CATFISH_BUCKET.get(), 1),
                    5, 9, 0.035f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 30),
                    new ItemStack(YAFMItems.QUALITY_FEED.get(), 6),
                    5, 9, 0.035f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 30),
                    new ItemStack(YAFMItems.FISHNET.get(), 1),
                    5, 9, 0.035f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 35),
                    new ItemStack(YAFMItems.ARAPAIMA_FISHNET.get(), 1),
                    5, 9, 0.035f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 35),
                    new ItemStack(YAFMItems.COELACANTH_FISHNET.get(), 1),
                    5, 9, 0.035f));

            //level 5
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 40),
                    new ItemStack(YAFMItems.PREMIUM_FEED.get(), 6),
                    5, 9, 0.035f));

            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 15),
                    new ItemStack(YAFMItems.FISHING_HAT.get(), 1),
                    5, 9, 0.035f));
        }


    }



    @SubscribeEvent
    public static void addCustomWanderingTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();
        List<VillagerTrades.ItemListing> rareTrades = event.getRareTrades();

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 1),
                new ItemStack(YAFMBlocks.DUCKWEED.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 1),
                new ItemStack(YAFMBlocks.HORNWORT.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 1),
                new ItemStack(YAFMBlocks.ANUBIAS.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 1),
                new ItemStack(YAFMBlocks.CAULERPA.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 1),
                new ItemStack(YAFMBlocks.RED_ALGAE.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 1),
                new ItemStack(YAFMBlocks.RED_ALGAE_FAN.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 1),
                new ItemStack(YAFMBlocks.SEA_GRAPES.get(), 1),
                3, 2, 0.2f));



        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 5),
                new ItemStack(YAFMItems.FRESHWATER_SHARK_BUCKET.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 5),
                new ItemStack(YAFMItems.DAPHNIA_BUCKET.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 5),
                new ItemStack(YAFMItems.ARTEMIA_BUCKET.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 5),
                new ItemStack(YAFMItems.MINNOW_BUCKET.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 5),
                new ItemStack(YAFMItems.PLECO_BUCKET.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 5),
                new ItemStack(YAFMItems.FEATHERBACK_BUCKET.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 5),
                new ItemStack(YAFMItems.GUPPY_BUCKET.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 5),
                new ItemStack(YAFMItems.CATFISH_BUCKET.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 15),
                new ItemStack(YAFMItems.ARAPAIMA_FISHNET.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 15),
                new ItemStack(YAFMItems.COELACANTH_FISHNET.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 5),
                new ItemStack(YAFMItems.GOURAMI_BUCKET.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 5),
                new ItemStack(YAFMItems.MOONY_BUCKET.get(), 1),
                3, 2, 0.2f));



        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 14),
                new ItemStack(YAFMItems.FISHNET.get(), 1),
                2, 12, 0.15f));

        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 24),
                new ItemStack(YAFMItems.DRAGONFISH_MUSIC_DISC.get(), 1),
                2, 12, 0.15f));

        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 24),
                new ItemStack(YAFMItems.SHUNJI_MUSIC_DISC.get(), 1),
                2, 12, 0.15f));

        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 24),
                new ItemStack(YAFMItems.AXOLOTL_MUSIC_DISC.get(), 1),
                2, 12, 0.15f));

        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 30),
                new ItemStack(YAFMItems.FISHING_HAT.get(), 1),
                1, 12, 0.15f));
    }

}
