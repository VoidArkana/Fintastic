package net.voidarkana.fintastic.util.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.block.YAFMBlocks;
import net.voidarkana.fintastic.common.item.YAFMItems;
import net.voidarkana.fintastic.common.loot.AddItemModifier;

public class YAFMGlobalLootModifiersProvider extends GlobalLootModifierProvider {

    public YAFMGlobalLootModifiersProvider(PackOutput output) {
        super(output, Fintastic.MOD_ID);
    }

    @Override
    protected void start() {

        add("salty_from_fishing", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("gameplay/fishing/treasure")).build(),
                LootItemRandomChanceCondition.randomChance(0.3f).build() }, YAFMItems.SALTY_MUSIC_DISC.get()));

        add("fresh_from_fishing", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("gameplay/fishing/treasure")).build(),
                LootItemRandomChanceCondition.randomChance(0.3f).build() }, YAFMItems.FRESH_MUSIC_DISC.get()));

        add("axolotl_from_fishing", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("gameplay/fishing/treasure")).build(),
                LootItemRandomChanceCondition.randomChance(0.4f).build() }, YAFMItems.AXOLOTL_MUSIC_DISC.get()));

        add("dragonfish_from_fishing", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("gameplay/fishing/treasure")).build(),
                LootItemRandomChanceCondition.randomChance(0.4f).build() }, YAFMItems.DRAGONFISH_MUSIC_DISC.get()));

        add("shunji_from_fishing", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("gameplay/fishing/treasure")).build(),
                LootItemRandomChanceCondition.randomChance(0.4f).build() }, YAFMItems.SHUNJI_MUSIC_DISC.get()));

        add("fishnet_from_fishing", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("gameplay/fishing/treasure")).build(),
                LootItemRandomChanceCondition.randomChance(0.6f).build() }, YAFMItems.FISHNET.get()));

        add("hat_from_fishing", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("gameplay/fishing/treasure")).build(),
                LootItemRandomChanceCondition.randomChance(0.35f).build() }, YAFMItems.FISHING_HAT.get()));



        add("hornwort_from_fishing", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("gameplay/fishing/junk")).build(),
                LootItemRandomChanceCondition.randomChance(0.25f).build() }, YAFMBlocks.HORNWORT.get().asItem()));

        add("duckweed_from_fishing", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("gameplay/fishing/junk")).build(),
                LootItemRandomChanceCondition.randomChance(0.25f).build() }, YAFMBlocks.DUCKWEED.get().asItem()));
    }
}
