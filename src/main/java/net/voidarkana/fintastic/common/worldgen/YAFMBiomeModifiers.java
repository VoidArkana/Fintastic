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

public class YAFMBiomeModifiers {

    public static final ResourceKey<BiomeModifier> ADD_HORNWORT = registerKey("add_hornwort");
    public static final ResourceKey<BiomeModifier> ADD_DUCKWEED = registerKey("add_duckweed");
    public static final ResourceKey<BiomeModifier> ADD_LIVE_ROCK = registerKey("add_live_rock_boulder");
    public static final ResourceKey<BiomeModifier> ADD_STROMATOLITE = registerKey("add_stromatolite");

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_HORNWORT, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
           biomes.getOrThrow(BiomeTags.IS_RIVER),
                HolderSet.direct(placedFeatures.getOrThrow(YAFMPlacedFeatures.HORNWORT_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_DUCKWEED, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_SWAMP),
                HolderSet.direct(placedFeatures.getOrThrow(YAFMPlacedFeatures.DUCKWEED_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_LIVE_ROCK, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.PRODUCES_CORALS_FROM_BONEMEAL),
                HolderSet.direct(placedFeatures.getOrThrow(YAFMPlacedFeatures.LIVE_ROCK_PLACED_KEY)),
                GenerationStep.Decoration.LOCAL_MODIFICATIONS));

        context.register(ADD_STROMATOLITE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_BEACH),
                HolderSet.direct(placedFeatures.getOrThrow(YAFMPlacedFeatures.STROMATOLITE_PLACED_KEY)),
                GenerationStep.Decoration.LOCAL_MODIFICATIONS));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Fintastic.MOD_ID, name));
    }

}
