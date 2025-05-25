package net.voidarkana.fintastic.util.data;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.voidarkana.fintastic.util.data.loot.YAFMBlockLootTableProvider;

import java.util.List;
import java.util.Set;

public class YAFMLootTableProvider {
    public static LootTableProvider create(PackOutput output){
        return new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(YAFMBlockLootTableProvider::new, LootContextParamSets.BLOCK)
        ));
    }
}
