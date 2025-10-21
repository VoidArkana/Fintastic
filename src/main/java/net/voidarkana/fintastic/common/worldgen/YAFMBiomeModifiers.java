package net.voidarkana.fintastic.common.worldgen;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.util.YAFMTags;

public class YAFMBiomeModifiers {

    public static final ResourceKey<BiomeModifier> ADD_HORNWORT = registerKey("add_hornwort");
    public static final ResourceKey<BiomeModifier> ADD_DUCKWEED = registerKey("add_duckweed");
    public static final ResourceKey<BiomeModifier> ADD_LIVE_ROCK = registerKey("add_live_rock_boulder");
    public static final ResourceKey<BiomeModifier> ADD_STROMATOLITE = registerKey("add_stromatolite");
    public static final ResourceKey<BiomeModifier> ADD_FOSSIL_STROMATOLITE = registerKey("add_fossil_stromatolite");
    public static final ResourceKey<BiomeModifier> ADD_ANUBIAS = registerKey("add_anubias");

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_HORNWORT, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
           biomes.getOrThrow(YAFMTags.Biomes.HORNWORT_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(YAFMPlacedFeatures.HORNWORT_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_DUCKWEED, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(YAFMTags.Biomes.DUCKWEED_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(YAFMPlacedFeatures.DUCKWEED_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_LIVE_ROCK, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(YAFMTags.Biomes.LIVEROCK_BOULDER_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(YAFMPlacedFeatures.LIVE_ROCK_PLACED_KEY)),
                GenerationStep.Decoration.LOCAL_MODIFICATIONS));

        context.register(ADD_STROMATOLITE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(YAFMTags.Biomes.STROMATOLITE_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(YAFMPlacedFeatures.STROMATOLITE_PLACED_KEY)),
                GenerationStep.Decoration.LOCAL_MODIFICATIONS));

        context.register(ADD_FOSSIL_STROMATOLITE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(YAFMPlacedFeatures.FOSSIL_STROMATOLITE_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_ANUBIAS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(YAFMTags.Biomes.ANUBIAS_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(YAFMPlacedFeatures.ANUBIAS_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Fintastic.MOD_ID, name));
    }

}
