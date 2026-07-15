package net.voidarkana.fintastic.util.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.block.FintyBlocks;
import net.voidarkana.fintastic.common.item.FintyItems;
import net.voidarkana.fintastic.common.loot.AddItemModifier;

import java.util.concurrent.CompletableFuture;

public class FintyGlobalLootModifiersProvider extends GlobalLootModifierProvider {

    public FintyGlobalLootModifiersProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, Fintastic.MOD_ID);
    }

    @Override
    protected void start() {

        add("salty_from_fishing", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(ResourceLocation.parse("gameplay/fishing/treasure")).build(),
                LootItemRandomChanceCondition.randomChance(0.3f).build() }, FintyItems.SALTY_MUSIC_DISC.get()));

        add("fresh_from_fishing", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(ResourceLocation.parse("gameplay/fishing/treasure")).build(),
                LootItemRandomChanceCondition.randomChance(0.3f).build() }, FintyItems.FRESH_MUSIC_DISC.get()));

        add("axolotl_from_fishing", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(ResourceLocation.parse("gameplay/fishing/treasure")).build(),
                LootItemRandomChanceCondition.randomChance(0.4f).build() }, FintyItems.AXOLOTL_MUSIC_DISC.get()));

        add("dragonfish_from_fishing", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(ResourceLocation.parse("gameplay/fishing/treasure")).build(),
                LootItemRandomChanceCondition.randomChance(0.4f).build() }, FintyItems.DRAGONFISH_MUSIC_DISC.get()));

        add("shunji_from_fishing", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(ResourceLocation.parse("gameplay/fishing/treasure")).build(),
                LootItemRandomChanceCondition.randomChance(0.4f).build() }, FintyItems.SHUNJI_MUSIC_DISC.get()));

        add("fishnet_from_fishing", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(ResourceLocation.parse("gameplay/fishing/treasure")).build(),
                LootItemRandomChanceCondition.randomChance(0.6f).build() }, FintyItems.FISHNET.get()));

        add("hat_from_fishing", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(ResourceLocation.parse("gameplay/fishing/treasure")).build(),
                LootItemRandomChanceCondition.randomChance(0.35f).build() }, FintyItems.FISHING_HAT.get()));



        add("hornwort_from_fishing", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(ResourceLocation.parse("gameplay/fishing/junk")).build(),
                LootItemRandomChanceCondition.randomChance(0.25f).build() }, FintyBlocks.HORNWORT.get().asItem()));

        add("duckweed_from_fishing", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(ResourceLocation.parse("gameplay/fishing/junk")).build(),
                LootItemRandomChanceCondition.randomChance(0.25f).build() }, FintyBlocks.DUCKWEED.get().asItem()));
    }
}
