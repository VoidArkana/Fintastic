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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.block.YAFMBlocks;
import net.voidarkana.fintastic.common.block.custom.AlgaeCarpetBlock;
import net.voidarkana.fintastic.common.worldgen.features.*;
import net.voidarkana.fintastic.util.YAFMTags;

import java.util.List;
import java.util.function.Supplier;

public class YAFMConfiguredFeatures {

    public static final DeferredRegister<Feature<?>> MOD_FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Fintastic.MOD_ID);

    public static final ResourceKey<ConfiguredFeature<?, ?>> HORNWORT_KEY = registerKey("hornwort_key");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DUCKWEED_KEY = registerKey("duckweed_key");

    public static final ResourceKey<ConfiguredFeature<?, ?>> GREEN_ALGAE_VEGETATION = registerKey("green_algae_vegetation");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GREEN_ALGAE_PATCH_BONEMEAL = registerKey("green_algae_patch_bonemeal");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GREEN_ALGAE_VEGETATION_BONEMEAL = registerKey("green_algae_vegetation_bonemeal");

    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_ALGAE_VEGETATION = registerKey("red_algae_vegetation");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_ALGAE_PATCH_BONEMEAL = registerKey("red_algae_patch_bonemeal");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_ALGAE_VEGETATION_BONEMEAL = registerKey("red_algae_vegetation_bonemeal");

    public static final ResourceKey<ConfiguredFeature<?, ?>> LIVE_ROCK_BOULDER = registerKey("live_rock_boulder");

    public static final ResourceKey<ConfiguredFeature<?, ?>> STROMATOLITE_PATCH = registerKey("stromatolite_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> STROMATOLITE_DECORATION = registerKey("stromatolite_decoration");

    public static final ResourceKey<ConfiguredFeature<?, ?>> FOSSIL_STROMATOLITE_PATCH = registerKey("fossil_stromatolite_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FOSSIL_STROMATOLITE_DECORATION = registerKey("fossil_stromatolite_decoration");

    public static final RegistryObject<Feature<VegetationPatchConfiguration>> ALGAE_PATCH_FEATURE =
            register_feature("algae_patch_feature", () -> new UnderwaterVegetationPatchFeature(VegetationPatchConfiguration.CODEC));

    public static final RegistryObject<Feature<VegetationPatchConfiguration>> STROMATOLITE_PATCH_FEATURE =
            register_feature("stromatolite_patch_feature", () -> new AmphibiousVegetationPatchFeature(VegetationPatchConfiguration.CODEC));

    public static final RegistryObject<Feature<AlgaeBonemealConfig>> ALGAE_BONEMEAL_FEATURE =
            register_feature("algae_bonemeal_feature", () -> new AlgaeBonemealFeature(AlgaeBonemealConfig.CODEC));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> HORNWORT_FEATURE =
            register_feature("hornwort_feature", () -> new HornWortFeature(NoneFeatureConfiguration.CODEC));

    public static final RegistryObject<Feature<LiveRockBoulderConfig>> LIVE_ROCK_BOULDER_FEATURE =
            register_feature("live_rock_boulder_feature", () -> new LiveRockBoulderFeature(LiveRockBoulderConfig.CODEC));

    public static final RegistryObject<Feature<SimpleBlockConfiguration>> SIMPLE_WATERLOGGABLE_BLOCK
            = register_feature("simple_waterloggable_block", () -> new SimpleWaterloggableBlockFeature(SimpleBlockConfiguration.CODEC));

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {

        HolderGetter<ConfiguredFeature<?, ?>> holdergetter = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, HORNWORT_KEY, YAFMConfiguredFeatures.HORNWORT_FEATURE.get(), FeatureConfiguration.NONE);

        register(context, DUCKWEED_KEY, Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(20, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(YAFMBlocks.DUCKWEED.get())))));

        WeightedStateProvider greenAlgaeWSP = new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                .add(Blocks.SEA_PICKLE.defaultBlockState(), 4)
                .add(YAFMBlocks.GREEN_ALGAE_CARPET.get().defaultBlockState().setValue(AlgaeCarpetBlock.WATERLOGGED, true), 25)
                .add(Blocks.SEAGRASS.defaultBlockState(), 50)
                .add(Blocks.KELP_PLANT.defaultBlockState(), 10));

        register(context, GREEN_ALGAE_VEGETATION, Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(greenAlgaeWSP));

        register(context, GREEN_ALGAE_PATCH_BONEMEAL, ALGAE_PATCH_FEATURE.get(),
                new VegetationPatchConfiguration(YAFMTags.Blocks.ALGAE_REPLACEABLE, BlockStateProvider.simple(YAFMBlocks.GREEN_ALGAE_BLOCK.get()),
                        PlacementUtils.inlinePlaced(holdergetter.getOrThrow(GREEN_ALGAE_VEGETATION)),
                        CaveSurface.FLOOR, ConstantInt.of(1), 0.0F, 5, 0.6F,
                        UniformInt.of(1, 2), 0.75F));


        register(context, GREEN_ALGAE_VEGETATION_BONEMEAL, ALGAE_BONEMEAL_FEATURE.get(),
                new AlgaeBonemealConfig(greenAlgaeWSP, 3, 1));



        WeightedStateProvider redAlgaeWSP = new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                .add(Blocks.FIRE_CORAL.defaultBlockState(), 4)
                .add(YAFMBlocks.RED_ALGAE_CARPET.get().defaultBlockState().setValue(AlgaeCarpetBlock.WATERLOGGED, true), 25)
                .add(YAFMBlocks.RED_ALGAE.get().defaultBlockState().setValue(BaseCoralPlantBlock.WATERLOGGED, true), 50)
                .add(YAFMBlocks.RED_ALGAE_FAN.get().defaultBlockState().setValue(BaseCoralPlantBlock.WATERLOGGED, true), 10));

        register(context, RED_ALGAE_VEGETATION, Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(redAlgaeWSP));

        register(context, RED_ALGAE_PATCH_BONEMEAL, ALGAE_PATCH_FEATURE.get(),
                new VegetationPatchConfiguration(YAFMTags.Blocks.ALGAE_REPLACEABLE, BlockStateProvider.simple(YAFMBlocks.RED_ALGAE_BLOCK.get()),
                        PlacementUtils.inlinePlaced(holdergetter.getOrThrow(RED_ALGAE_VEGETATION)),
                        CaveSurface.FLOOR, ConstantInt.of(1), 0.0F, 5, 0.6F,
                        UniformInt.of(1, 2), 0.75F));

        register(context, RED_ALGAE_VEGETATION_BONEMEAL, ALGAE_BONEMEAL_FEATURE.get(),
                new AlgaeBonemealConfig(redAlgaeWSP, 3, 1));


        register(context, LIVE_ROCK_BOULDER, LIVE_ROCK_BOULDER_FEATURE.get(),
                new LiveRockBoulderConfig(BlockStateProvider.simple(YAFMBlocks.RED_ALGAE_LIVE_ROCK.get()),
                        BlockStateProvider.simple(YAFMBlocks.GREEN_ALGAE_LIVE_ROCK.get()), 0.6F,
                        PlacementUtils.inlinePlaced(holdergetter.getOrThrow(RED_ALGAE_VEGETATION)),
                        PlacementUtils.inlinePlaced(holdergetter.getOrThrow(GREEN_ALGAE_VEGETATION))));


        WeightedStateProvider stromatoliteWSP = new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                .add(YAFMBlocks.STROMATOLITE_GROWTHS.get().defaultBlockState(), 10)
                .add(YAFMBlocks.STROMATOLITE.get().defaultBlockState(), 4));

        register(context, STROMATOLITE_DECORATION, SIMPLE_WATERLOGGABLE_BLOCK.get(),
                new SimpleBlockConfiguration(stromatoliteWSP));

        register(context, STROMATOLITE_PATCH, STROMATOLITE_PATCH_FEATURE.get(),
                new VegetationPatchConfiguration(YAFMTags.Blocks.STROMATOLITE_REPLACEABLE, BlockStateProvider.simple(YAFMBlocks.STROMATOLITE_BLOCK.get()),
                        PlacementUtils.inlinePlaced(holdergetter.getOrThrow(STROMATOLITE_DECORATION)),
                        CaveSurface.FLOOR, ConstantInt.of(1), 0.0F, 5, 0.8F,
                        UniformInt.of(4, 7), 0.3F));

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
