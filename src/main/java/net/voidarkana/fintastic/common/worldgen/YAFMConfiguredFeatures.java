package net.voidarkana.fintastic.common.worldgen;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.BaseCoralPlantBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CoralBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WaterloggedVegetationPatchFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.common.Tags;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.block.YAFMBlocks;
import net.voidarkana.fintastic.common.block.custom.AlgaeCarpetBlock;
import net.voidarkana.fintastic.common.worldgen.features.HornWortFeature;
import net.voidarkana.fintastic.common.worldgen.features.UnderwaterVegetationPatchFeature;
import net.voidarkana.fintastic.common.worldgen.features.UnderwaterWaterloggedVegetationPatchFeature;

import java.util.List;
import java.util.function.Supplier;

public class YAFMConfiguredFeatures {

    public static final DeferredRegister<Feature<?>> MOD_FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Fintastic.MOD_ID);

    public static final ResourceKey<ConfiguredFeature<?, ?>> HORNWORT_KEY = registerKey("hornwort_key");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DUCKWEED_KEY = registerKey("duckweed_key");

    public static final ResourceKey<ConfiguredFeature<?, ?>> GREEN_ALGAE_VEGETATION = registerKey("green_algae_vegetation");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GREEN_ALGAE_PATCH_BONEMEAL = registerKey("green_algae_patch_bonemeal");

    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_ALGAE_VEGETATION = registerKey("red_algae_vegetation");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_ALGAE_PATCH_BONEMEAL = registerKey("red_algae_patch_bonemeal");


    public static final RegistryObject<Feature<VegetationPatchConfiguration>> ALGAE_PATCH_FEATURE =
            register_feature("algae_patch_feature", () -> new UnderwaterVegetationPatchFeature(VegetationPatchConfiguration.CODEC));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> HORNWORT_FEATURE =
            register_feature("hornwort_feature", () -> new HornWortFeature(NoneFeatureConfiguration.CODEC));

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {

        HolderGetter<ConfiguredFeature<?, ?>> holdergetter = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, HORNWORT_KEY, YAFMConfiguredFeatures.HORNWORT_FEATURE.get(), FeatureConfiguration.NONE);

        register(context, DUCKWEED_KEY, Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(20, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(YAFMBlocks.DUCKWEED.get())))));



        register(context, GREEN_ALGAE_VEGETATION, Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                        .add(Blocks.SEA_PICKLE.defaultBlockState(), 4)
                        .add(YAFMBlocks.GREEN_ALGAE_CARPET.get().defaultBlockState().setValue(AlgaeCarpetBlock.WATERLOGGED, true), 25)
                        .add(Blocks.SEAGRASS.defaultBlockState(), 50)
                        .add(Blocks.KELP_PLANT.defaultBlockState(), 10))));

        register(context, GREEN_ALGAE_PATCH_BONEMEAL, ALGAE_PATCH_FEATURE.get(),
                new VegetationPatchConfiguration(BlockTags.MOSS_REPLACEABLE, BlockStateProvider.simple(YAFMBlocks.GREEN_ALGAE_BLOCK.get()),
                        PlacementUtils.inlinePlaced(holdergetter.getOrThrow(GREEN_ALGAE_VEGETATION)),
                        CaveSurface.FLOOR, ConstantInt.of(1), 0.0F, 5, 0.6F,
                        UniformInt.of(1, 2), 0.75F));



        register(context, RED_ALGAE_VEGETATION, Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                        .add(Blocks.FIRE_CORAL.defaultBlockState(), 4)
                        .add(YAFMBlocks.RED_ALGAE_CARPET.get().defaultBlockState().setValue(AlgaeCarpetBlock.WATERLOGGED, true), 25)
                        .add(YAFMBlocks.RED_ALGAE.get().defaultBlockState().setValue(BaseCoralPlantBlock.WATERLOGGED, true), 50)
                        .add(YAFMBlocks.RED_ALGAE_FAN.get().defaultBlockState().setValue(BaseCoralPlantBlock.WATERLOGGED, true), 10)
                )));

        register(context, RED_ALGAE_PATCH_BONEMEAL, ALGAE_PATCH_FEATURE.get(),
                new VegetationPatchConfiguration(BlockTags.MOSS_REPLACEABLE, BlockStateProvider.simple(YAFMBlocks.RED_ALGAE_BLOCK.get()),
                        PlacementUtils.inlinePlaced(holdergetter.getOrThrow(RED_ALGAE_VEGETATION)),
                        CaveSurface.FLOOR, ConstantInt.of(1), 0.0F, 5, 0.6F,
                        UniformInt.of(1, 2), 0.75F));
    }


    public static List<PlacementModifier> worldSurfaceSquaredWithCount(int pCount) {
        return List.of(CountPlacement.of(pCount), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Fintastic.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register
            (BootstapContext<ConfiguredFeature<?, ?>> context,
        ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {

        context.register(key, new ConfiguredFeature<>(feature, configuration));

    }

    public static <T extends FeatureConfiguration> RegistryObject<Feature<T>> register_feature(String name, Supplier<Feature<T>> featureSupplier) {
        return MOD_FEATURES.register(name, featureSupplier);
    }

    public static void register(IEventBus eventBus){
        MOD_FEATURES.register(eventBus);
    }

}
