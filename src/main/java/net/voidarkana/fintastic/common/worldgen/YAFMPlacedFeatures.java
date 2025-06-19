package net.voidarkana.fintastic.common.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.voidarkana.fintastic.Fintastic;

import java.util.List;

public class YAFMPlacedFeatures {

    public static final ResourceKey<PlacedFeature> HORNWORT_PLACED_KEY = registerKey("hornwort_placed");

    public static final ResourceKey<PlacedFeature> DUCKWEED_PLACED_KEY = registerKey("duckweed_placed");

    public static final ResourceKey<PlacedFeature> LIVE_ROCK_PLACED_KEY = registerKey("live_rock_boulder_placed");


    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, HORNWORT_PLACED_KEY, configuredFeatures.getOrThrow(YAFMConfiguredFeatures.HORNWORT_KEY),
                hornwortPlacement(48));

        register(context, DUCKWEED_PLACED_KEY, configuredFeatures.getOrThrow(YAFMConfiguredFeatures.DUCKWEED_KEY),
                worldSurfaceSquaredWithCount(12));

        register(context, LIVE_ROCK_PLACED_KEY, configuredFeatures.getOrThrow(YAFMConfiguredFeatures.LIVE_ROCK_BOULDER)
                ,underwaterBoulderPlacement(1));

    }

    private static List<PlacementModifier> hornwortPlacement(int pCount) {
        return List.of(InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, CountPlacement.of(pCount), BiomeFilter.biome());
    }

    public static List<PlacementModifier> worldSurfaceSquaredWithCount(int pCount) {
        return List.of(CountPlacement.of(pCount), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
    }

    public static List<PlacementModifier> underwaterBoulderPlacement(int pCount) {
        return List.of(CountPlacement.of(pCount), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
    }

    public static void register(BootstapContext<PlacedFeature> pContext,
                                ResourceKey<PlacedFeature> pKey,
                                Holder<ConfiguredFeature<?, ?>> pConfiguredFeatures,
                                PlacementModifier... pPlacements) {
        register(pContext, pKey, pConfiguredFeatures, List.of(pPlacements));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Fintastic.MOD_ID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context,
                                 ResourceKey<PlacedFeature> key,
                                 Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
