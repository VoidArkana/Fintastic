package net.voidarkana.fintastic.util.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.worldgen.FintyBiomeModifiers;
import net.voidarkana.fintastic.common.worldgen.FintyConfiguredFeatures;
import net.voidarkana.fintastic.common.worldgen.FintyPlacedFeatures;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class FintyWorldGenProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, FintyConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, FintyPlacedFeatures::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, FintyBiomeModifiers::bootstrap);

    public FintyWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(Fintastic.MOD_ID));
    }


}
