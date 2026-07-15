package net.voidarkana.fintastic.util.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.voidarkana.fintastic.util.data.loot.FintyBlockLootTableProvider;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class FintyLootTableProvider {
    public static LootTableProvider create(PackOutput output, CompletableFuture<HolderLookup.Provider> registries){
        return new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(FintyBlockLootTableProvider::new, LootContextParamSets.BLOCK)
        ), registries);
    }
}
