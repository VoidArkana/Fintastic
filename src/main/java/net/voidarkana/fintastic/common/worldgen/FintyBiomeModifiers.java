package net.voidarkana.fintastic.common.worldgen;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.util.FintyTags;

public class FintyBiomeModifiers {

    public static final ResourceKey<BiomeModifier> ADD_HORNWORT = registerKey("add_hornwort");
    public static final ResourceKey<BiomeModifier> ADD_DUCKWEED = registerKey("add_duckweed");
    public static final ResourceKey<BiomeModifier> ADD_LIVE_ROCK = registerKey("add_live_rock_boulder");
    public static final ResourceKey<BiomeModifier> ADD_STROMATOLITE = registerKey("add_stromatolite");
    public static final ResourceKey<BiomeModifier> ADD_FOSSIL_STROMATOLITE = registerKey("add_fossil_stromatolite");
    public static final ResourceKey<BiomeModifier> ADD_ANUBIAS = registerKey("add_anubias");
    public static final ResourceKey<BiomeModifier> ADD_AQUATIC_MOSS = registerKey("add_aquatic_moss");
    public static final ResourceKey<BiomeModifier> ADD_AMAZON_SWORDS = registerKey("add_amazon_swords");
    public static final ResourceKey<BiomeModifier> ADD_LOTUS = registerKey("add_lotus");
    public static final ResourceKey<BiomeModifier> ADD_FINTASTIC_COD = registerKey("add_fintastic_cod");
    public static final ResourceKey<BiomeModifier> ADD_FINTASTIC_SALMON = registerKey("add_fintastic_salmon");
    public static final ResourceKey<BiomeModifier> REMOVE_VANILLA_COD = registerKey(   "remove_vanilla_cod");
    public static final ResourceKey<BiomeModifier> REMOVE_VANILLA_SALMON = registerKey("remove_vanilla_salmon");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);
        var entities = context.lookup(Registries.ENTITY_TYPE);

        context.register(ADD_HORNWORT, new BiomeModifiers.AddFeaturesBiomeModifier(
           biomes.getOrThrow(FintyTags.Biomes.HORNWORT_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(FintyPlacedFeatures.HORNWORT_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_LIVE_ROCK, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(FintyTags.Biomes.LIVEROCK_BOULDER_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(FintyPlacedFeatures.LIVE_ROCK_PLACED_KEY)),
                GenerationStep.Decoration.LOCAL_MODIFICATIONS));

        context.register(ADD_STROMATOLITE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(FintyTags.Biomes.STROMATOLITE_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(FintyPlacedFeatures.STROMATOLITE_PLACED_KEY)),
                GenerationStep.Decoration.LOCAL_MODIFICATIONS));

        context.register(ADD_FOSSIL_STROMATOLITE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(FintyPlacedFeatures.FOSSIL_STROMATOLITE_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_ANUBIAS, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(FintyTags.Biomes.ANUBIAS_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(FintyPlacedFeatures.ANUBIAS_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_AQUATIC_MOSS, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(FintyTags.Biomes.AQUATIC_MOSS_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(FintyPlacedFeatures.AQUATIC_MOSS_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_AMAZON_SWORDS, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(FintyTags.Biomes.AMAZON_SWORD_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(FintyPlacedFeatures.AMAZON_SWORD_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_LOTUS, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(FintyTags.Biomes.LOTUS_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(FintyPlacedFeatures.LOTUS_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_DUCKWEED, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(FintyTags.Biomes.DUCKWEED_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(FintyPlacedFeatures.DUCKWEED_PLACED_KEY)),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION));

//        context.register(ADD_FINTASTIC_COD, new BiomeModifiers.AddSpawnsBiomeModifier(
//                biomes.getOrThrow(FintyTags.Biomes.COD_BIOMES),
//                List.of(new MobSpawnSettings.SpawnerData(FintyEntities.COD.get(), 10, 4, 8))));

//        context.register(REMOVE_VANILLA_SALMON, new BiomeModifiers.RemoveSpawnsBiomeModifier(
//                biomes.getOrThrow(FintyTags.Biomes.SALMON_BIOMES),
//                entities.getOrThrow(
//                    FintyTags.EntityTypes.VANILLA_SALMON
//                )));
//
//        context.register(REMOVE_VANILLA_COD, new BiomeModifiers.RemoveSpawnsBiomeModifier(
//                biomes.getOrThrow(FintyTags.Biomes.COD_BIOMES),
//                entities.getOrThrow(
//                    FintyTags.EntityTypes.VANILLA_COD
//                )));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, Fintastic.location(name));
    }

}
