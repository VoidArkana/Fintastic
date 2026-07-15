package net.voidarkana.fintastic.common.event;

import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.FishingHookPredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.voidarkana.fintastic.Fintastic;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


@Mod.EventBusSubscriber(modid = Fintastic.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FintyForgeEvents {

    @SubscribeEvent
    public static void onLootLoad(LootTableLoadEvent event) {
        ResourceLocation name = event.getName();
        LootPool pool = event.getTable().getPool("main");
        if (name.equals(BuiltInLootTables.FISHING_FISH)) {
            if (pool!=null){
                addEntry(pool, getInjectEntry(new ResourceLocation(Fintastic.MOD_ID, "gameplay/fishing/junk"),
                        11, -2));

                LootTableReference.lootTableReference(new ResourceLocation(Fintastic.MOD_ID, "gameplay/fishing/treasure"))
                        .setWeight(6).setQuality(2).when(LootItemEntityPropertyCondition.hasProperties(
                                LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate
                                        (FishingHookPredicate.inOpenWater(true)))).build();

            }
        }
    }

    private static LootPoolEntryContainer getInjectEntry(ResourceLocation location, int weight, int quality) {
        return LootTableReference.lootTableReference(location).setWeight(weight).setQuality(quality).build();
    }

    private static void addEntry(LootPool pool, LootPoolEntryContainer entry) {
        try {
            Field entries = ObfuscationReflectionHelper.findField(LootPool.class, "f_79023_");
            entries.setAccessible(true);

            LootPoolEntryContainer[] lootPoolEntriesArray = (LootPoolEntryContainer[]) entries.get(pool);
            ArrayList<LootPoolEntryContainer> newLootEntries = new ArrayList<>(List.of(lootPoolEntriesArray));

            if (newLootEntries.stream().anyMatch(e -> e == entry)) {
                throw new RuntimeException("Attempted to add a duplicate entry to pool: " + entry);
            }

            newLootEntries.add(entry);

            LootPoolEntryContainer[] newLootEntriesArray = new LootPoolEntryContainer[newLootEntries.size()];
            newLootEntries.toArray(newLootEntriesArray);
            entries.set(pool, newLootEntriesArray);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
