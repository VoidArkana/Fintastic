package net.voidarkana.fintastic.common.event;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.block.FintyBlocks;
import net.voidarkana.fintastic.common.entity.villager.FintyVillagerProfessions;
import net.voidarkana.fintastic.common.item.FintyItems;
import net.voidarkana.fintastic.util.FintyCommonConfig;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Fintastic.MOD_ID)
public class FintyModEvents {

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event){

        if (event.getType() == FintyVillagerProfessions.AQUARIST.get()){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Level 1
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(FintyBlocks.DUCKWEED.get(), 20),
                    new ItemStack(Items.EMERALD, 1),
                    10, 8, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(FintyBlocks.HORNWORT.get(), 20),
                    new ItemStack(Items.EMERALD, 1),
                    10, 8, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(FintyBlocks.ANUBIAS.get(), 10),
                    new ItemStack(Items.EMERALD, 1),
                    10, 8, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(FintyBlocks.CAULERPA.get(), 10),
                    new ItemStack(Items.EMERALD, 1),
                    10, 8, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(FintyBlocks.SEA_GRAPES.get(), 20),
                    new ItemStack(Items.EMERALD, 1),
                    10, 8, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(FintyItems.REGULAR_FEED.get(), 6),
                    10, 8, 0.02f));


            // Level 2
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 15),
                    new ItemStack(FintyBlocks.DUCKWEED.get(), 1),
                    10, 8, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 15),
                    new ItemStack(FintyBlocks.HORNWORT.get(), 2),
                    10, 8, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 15),
                    new ItemStack(FintyBlocks.ANUBIAS.get(), 2),
                    10, 8, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 15),
                    new ItemStack(FintyBlocks.CAULERPA.get(), 2),
                    10, 8, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 15),
                    new ItemStack(FintyBlocks.SEA_GRAPES.get(), 5),
                    10, 8, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(FintyItems.DAPHNIA_BUCKET.get(), 1),
                    5, 9, 0.035f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(FintyItems.GUPPY_BUCKET.get(), 1),
                    5, 9, 0.035f));


            // Level 3

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(FintyItems.MINNOW_BUCKET.get(), 1),
                    5, 9, 0.035f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(FintyItems.GREAT_FEED.get(), 6),
                    5, 9, 0.035f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(FintyItems.PLECO_BUCKET.get(), 1),
                    5, 9, 0.035f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(FintyItems.FEATHERBACK_BUCKET.get(), 1),
                    5, 9, 0.035f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(FintyItems.MOONY_BUCKET.get(), 1),
                    5, 9, 0.035f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(FintyItems.GOURAMI_BUCKET.get(), 1),
                    5, 9, 0.035f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(FintyItems.SMALL_CATFISH_BUCKET.get(), 1),
                    5, 9, 0.035f));


            // Level 4

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(FintyItems.FRESHWATER_SHARK_BUCKET.get(), 1),
                    5, 9, 0.035f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(FintyItems.ARTEMIA_BUCKET.get(), 1),
                    5, 9, 0.035f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(FintyItems.CATFISH_BUCKET.get(), 1),
                    5, 9, 0.035f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 30),
                    new ItemStack(FintyItems.QUALITY_FEED.get(), 6),
                    5, 9, 0.035f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 30),
                    new ItemStack(FintyItems.FISHNET.get(), 1),
                    5, 9, 0.035f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 35),
                    new ItemStack(FintyItems.ARAPAIMA_FISHNET.get(), 1),
                    5, 9, 0.035f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 35),
                    new ItemStack(FintyItems.COELACANTH_FISHNET.get(), 1),
                    5, 9, 0.035f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(FintyItems.DWARF_FROG_BUCKET.get(), 1),
                    5, 9, 0.035f));

            //level 5
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 30),
                    new ItemStack(FintyItems.PREMIUM_FEED.get(), 6),
                    5, 9, 0.035f));

            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 15),
                    new ItemStack(FintyItems.FISHING_HAT.get(), 1),
                    5, 9, 0.035f));

            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 40),
                    new ItemStack(FintyItems.FRESH_MUSIC_DISC.get(), 1),
                    2, 18, 0.035f));

            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 40),
                    new ItemStack(FintyItems.SALTY_MUSIC_DISC.get(), 1),
                    2, 18, 0.035f));

            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 15),
                    new ItemStack(FintyBlocks.YELLOW_DWARF_FROGLIGHT.get(), 6),
                    5, 9, 0.035f));

            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 15),
                    new ItemStack(FintyBlocks.RED_DWARF_FROGLIGHT.get(), 6),
                    5, 9, 0.035f));

            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 15),
                    new ItemStack(FintyBlocks.PINK_DWARF_FROGLIGHT.get(), 6),
                    5, 9, 0.035f));

            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 15),
                    new ItemStack(FintyBlocks.GREEN_DWARF_FROGLIGHT.get(), 6),
                    5, 9, 0.035f));
        }

        if (event.getType() == VillagerProfession.FISHERMAN){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            //level 1
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(FintyItems.RAW_FISH.get(), 6),
                    new ItemStack(Items.EMERALD, 1),
                    5, 9, 0.035f));

            //level 4
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 15),
                    new ItemStack(FintyItems.FISHNET.get(), 1),
                    4, 9, 0.035f));
        }


    }



    @SubscribeEvent
    public static void addCustomWanderingTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();
        List<VillagerTrades.ItemListing> rareTrades = event.getRareTrades();

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 1),
                new ItemStack(FintyBlocks.DUCKWEED.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 1),
                new ItemStack(FintyBlocks.HORNWORT.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 1),
                new ItemStack(FintyBlocks.ANUBIAS.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 1),
                new ItemStack(FintyBlocks.CAULERPA.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 1),
                new ItemStack(FintyBlocks.RED_ALGAE.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 1),
                new ItemStack(FintyBlocks.RED_ALGAE_FAN.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 1),
                new ItemStack(FintyBlocks.SEA_GRAPES.get(), 1),
                3, 2, 0.2f));



        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 5),
                new ItemStack(FintyItems.FRESHWATER_SHARK_BUCKET.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 5),
                new ItemStack(FintyItems.DAPHNIA_BUCKET.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 5),
                new ItemStack(FintyItems.ARTEMIA_BUCKET.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 5),
                new ItemStack(FintyItems.MINNOW_BUCKET.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 5),
                new ItemStack(FintyItems.PLECO_BUCKET.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 5),
                new ItemStack(FintyItems.FEATHERBACK_BUCKET.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 5),
                new ItemStack(FintyItems.GUPPY_BUCKET.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 5),
                new ItemStack(FintyItems.CATFISH_BUCKET.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 15),
                new ItemStack(FintyItems.ARAPAIMA_FISHNET.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 15),
                new ItemStack(FintyItems.COELACANTH_FISHNET.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 5),
                new ItemStack(FintyItems.GOURAMI_BUCKET.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 5),
                new ItemStack(FintyItems.MOONY_BUCKET.get(), 1),
                3, 2, 0.2f));



        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 14),
                new ItemStack(FintyItems.FISHNET.get(), 1),
                2, 12, 0.15f));

        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 24),
                new ItemStack(FintyItems.DRAGONFISH_MUSIC_DISC.get(), 1),
                2, 12, 0.15f));

        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 24),
                new ItemStack(FintyItems.SHUNJI_MUSIC_DISC.get(), 1),
                2, 12, 0.15f));

        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 24),
                new ItemStack(FintyItems.AXOLOTL_MUSIC_DISC.get(), 1),
                2, 12, 0.15f));

        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 30),
                new ItemStack(FintyItems.FISHING_HAT.get(), 1),
                1, 12, 0.15f));
    }


    @SubscribeEvent
    public static void addNewVillageBuilding(final ServerAboutToStartEvent event) {
        Registry<StructureTemplatePool> templatePoolRegistry = event.getServer().registryAccess().registry(Registries.TEMPLATE_POOL).orElseThrow();
        Registry<StructureProcessorList> processorListRegistry = event.getServer().registryAccess().registry(Registries.PROCESSOR_LIST).orElseThrow();

        //desert
        addBuildingToPool(templatePoolRegistry, processorListRegistry,
                new ResourceLocation("minecraft:village/desert/houses"),
                "fintastic:village/desert/houses/desert_aquarist", FintyCommonConfig.DESERT_VILLAGE_WEIGHT.get());

        //plains
        addBuildingToPool(templatePoolRegistry, processorListRegistry,
                new ResourceLocation("minecraft:village/plains/houses"),
                "fintastic:village/plains/houses/plains_aquarist", FintyCommonConfig.PLAINS_VILLAGE_WEIGHT.get());

        //savanna
        addBuildingToPool(templatePoolRegistry, processorListRegistry,
                new ResourceLocation("minecraft:village/savanna/houses"),
                "fintastic:village/savanna/houses/savanna_aquarist", FintyCommonConfig.SAVANNA_VILLAGE_WEIGHT.get());

        //snowy
        addBuildingToPool(templatePoolRegistry, processorListRegistry,
                new ResourceLocation("minecraft:village/snowy/houses"),
                "fintastic:village/snowy/houses/snowy_aquarist", FintyCommonConfig.SNOWY_VILLAGE_WEIGHT.get());

        //taiga
        addBuildingToPool(templatePoolRegistry, processorListRegistry,
                new ResourceLocation("minecraft:village/taiga/houses"),
                "fintastic:village/taiga/houses/taiga_aquarist", FintyCommonConfig.TAIGA_VILLAGE_WEIGHT.get());
    }

    private static final ResourceKey<StructureProcessorList> EMPTY_PROCESSOR_LIST_KEY = ResourceKey.create(
            Registries.PROCESSOR_LIST, new ResourceLocation("minecraft:empty"));

    private static void addBuildingToPool(Registry<StructureTemplatePool> templatePoolRegistry,
                                          Registry<StructureProcessorList> processorListRegistry,
                                          ResourceLocation poolRL,
                                          String nbtPieceRL,
                                          int weight) {
        Holder<StructureProcessorList> emptyProcessorList = processorListRegistry.getHolderOrThrow(EMPTY_PROCESSOR_LIST_KEY);

        StructureTemplatePool pool = templatePoolRegistry.get(poolRL);
        if (pool == null) return;
        SinglePoolElement piece = SinglePoolElement.legacy(nbtPieceRL,
                emptyProcessorList).apply(StructureTemplatePool.Projection.RIGID);
        for (int i = 0; i < weight; i++) {
            pool.templates.add(piece);
        }
        List<Pair<StructurePoolElement, Integer>> listOfPieceEntries = new ArrayList<>(pool.rawTemplates);
        listOfPieceEntries.add(new Pair<>(piece, weight));
        pool.rawTemplates = listOfPieceEntries;
    }
}
