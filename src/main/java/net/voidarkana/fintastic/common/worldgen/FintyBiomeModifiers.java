package net.voidarkana.fintastic.common.worldgen;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cod;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.entity.FintyEntities;
import net.voidarkana.fintastic.util.FintyCommonConfig;
import net.voidarkana.fintastic.util.FintyTags;

import java.util.List;

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

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);
        var entities = context.lookup(Registries.ENTITY_TYPE);

        context.register(ADD_HORNWORT, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
           biomes.getOrThrow(FintyTags.Biomes.HORNWORT_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(FintyPlacedFeatures.HORNWORT_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_LIVE_ROCK, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(FintyTags.Biomes.LIVEROCK_BOULDER_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(FintyPlacedFeatures.LIVE_ROCK_PLACED_KEY)),
                GenerationStep.Decoration.LOCAL_MODIFICATIONS));

        context.register(ADD_STROMATOLITE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(FintyTags.Biomes.STROMATOLITE_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(FintyPlacedFeatures.STROMATOLITE_PLACED_KEY)),
                GenerationStep.Decoration.LOCAL_MODIFICATIONS));

        context.register(ADD_FOSSIL_STROMATOLITE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(FintyPlacedFeatures.FOSSIL_STROMATOLITE_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_ANUBIAS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(FintyTags.Biomes.ANUBIAS_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(FintyPlacedFeatures.ANUBIAS_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_AQUATIC_MOSS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(FintyTags.Biomes.AQUATIC_MOSS_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(FintyPlacedFeatures.AQUATIC_MOSS_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_AMAZON_SWORDS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(FintyTags.Biomes.AMAZON_SWORD_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(FintyPlacedFeatures.AMAZON_SWORD_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_LOTUS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(FintyTags.Biomes.LOTUS_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(FintyPlacedFeatures.LOTUS_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_DUCKWEED, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(FintyTags.Biomes.DUCKWEED_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(FintyPlacedFeatures.DUCKWEED_PLACED_KEY)),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION));

//        context.register(ADD_FINTASTIC_COD, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
//                biomes.getOrThrow(FintyTags.Biomes.COD_BIOMES),
//                List.of(new MobSpawnSettings.SpawnerData(FintyEntities.COD.get(), 10, 4, 8))));

//        context.register(REMOVE_VANILLA_SALMON, new ForgeBiomeModifiers.RemoveSpawnsBiomeModifier(
//                biomes.getOrThrow(FintyTags.Biomes.SALMON_BIOMES),
//                entities.getOrThrow(
//                    FintyTags.EntityType.VANILLA_SALMON
//                )));
//
//        context.register(REMOVE_VANILLA_COD, new ForgeBiomeModifiers.RemoveSpawnsBiomeModifier(
//                biomes.getOrThrow(FintyTags.Biomes.COD_BIOMES),
//                entities.getOrThrow(
//                    FintyTags.EntityType.VANILLA_COD
//                )));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Fintastic.MOD_ID, name));
    }

}
