package net.voidarkana.fintastic.common.event;

import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.FishingHookPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.voidarkana.fintastic.Fintastic;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


@EventBusSubscriber(modid = Fintastic.MOD_ID)
public class FintyForgeEvents {

    @SubscribeEvent
    public static void onLootLoad(LootTableLoadEvent event) {
        LootPool pool = event.getTable().getPool("main");
        if (event.getKey().equals(BuiltInLootTables.FISHING_FISH)) {
            if (pool!=null){
                addEntry(pool, getInjectEntry(lootTableKey("gameplay/fishing/junk"),
                        11, -2));

                NestedLootTable.lootTableReference(lootTableKey("gameplay/fishing/treasure"))
                        .setWeight(6).setQuality(2).when(LootItemEntityPropertyCondition.hasProperties(
                                LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate
                                        (FishingHookPredicate.inOpenWater(true)))).build();

            }
        }
    }

    private static ResourceKey<LootTable> lootTableKey(String path) {
        return ResourceKey.create(Registries.LOOT_TABLE,
                Fintastic.location(path));
    }

    private static LootPoolEntryContainer getInjectEntry(ResourceKey<LootTable> location, int weight, int quality) {
        return NestedLootTable.lootTableReference(location).setWeight(weight).setQuality(quality).build();
    }

    private static void addEntry(LootPool pool, LootPoolEntryContainer entry) {
        try {
            Field entries = LootPool.class.getDeclaredField("entries");
            entries.setAccessible(true);

            @SuppressWarnings("unchecked")
            List<LootPoolEntryContainer> lootPoolEntries = (List<LootPoolEntryContainer>) entries.get(pool);
            ArrayList<LootPoolEntryContainer> newLootEntries = new ArrayList<>(lootPoolEntries);

            if (newLootEntries.stream().anyMatch(e -> e == entry)) {
                throw new RuntimeException("Attempted to add a duplicate entry to pool: " + entry);
            }

            newLootEntries.add(entry);

            entries.set(pool, newLootEntries);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }
}
