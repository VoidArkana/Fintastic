package net.voidarkana.fintastic.client;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.client.models.armor.HatModel;
import net.voidarkana.fintastic.client.models.entity.*;
import net.voidarkana.fintastic.client.models.entity.arapaima.ArapaimaModel;
import net.voidarkana.fintastic.client.models.entity.arapaima.BabyArapaimaModel;
import net.voidarkana.fintastic.client.models.entity.catfish.*;
import net.voidarkana.fintastic.client.models.entity.dwarf_frog.DwarfFrogModel;
import net.voidarkana.fintastic.client.models.entity.dwarf_frog.DwarfFrogTadpoleModel;
import net.voidarkana.fintastic.client.models.entity.featherback.*;
import net.voidarkana.fintastic.client.models.entity.gourami.*;
import net.voidarkana.fintastic.client.models.entity.guppy.BabyGuppyModel;
import net.voidarkana.fintastic.client.models.entity.guppy.GuppyModel;
import net.voidarkana.fintastic.client.models.entity.minnows.*;
import net.voidarkana.fintastic.client.models.entity.moonies.*;
import net.voidarkana.fintastic.client.models.entity.sharkminnows.*;
import net.voidarkana.fintastic.client.models.entity.small_catfish.SmallCatfishBanjoModel;
import net.voidarkana.fintastic.client.models.entity.small_catfish.SmallCatfishCoryModel;
import net.voidarkana.fintastic.client.models.entity.small_catfish.SmallCatfishThornyModel;
import net.voidarkana.fintastic.client.models.entity.small_catfish.SmallCatfishTinyCoryModel;
import net.voidarkana.fintastic.common.block.FintyBlocks;

@Mod.EventBusSubscriber(modid = Fintastic.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class FintyClientEvents {

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {

        event.registerLayerDefinition(FintasticLayers.MOONYMID_LAYER, MoonyMidModel::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.MOONYSMALL_LAYER, MoonySmallModel::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.MOONYTALL_LAYER, MoonyTallModel::createBodyLayer);

        event.registerLayerDefinition(FintasticLayers.MINNOW_BIG_LAYER, MinnowBigModel::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.MINNOW_HATCHET_LAYER, MinnowHatchetModel::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.MINNOW_ROUND_LAYER, MinnowRoundModel::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.MINNOW_SLIM_LAYER, MinnowSlimModel::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.MINNOW_SMALL_LAYER, MinnowSmallModel::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.MINNOW_THIN_LAYER, MinnowThinModel::createBodyLayer);

        event.registerLayerDefinition(FintasticLayers.ARAPAIMA_LAYER, ArapaimaModel::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.BABY_ARAPAIMA_LAYER, BabyArapaimaModel::createBodyLayer);

        event.registerLayerDefinition(FintasticLayers.COELACANTH_LAYER, CoelacanthModel::createBodyLayer);

        event.registerLayerDefinition(FintasticLayers.GOURAMI_HUGE_LAYER, GouramiHugeModel::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.GOURAMI_MED_LAYER, GouramiMedModel::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.GOURAMI_SMALL_LAYER, GouramiSmallModel::createBodyLayer);

        event.registerLayerDefinition(FintasticLayers.HIGHFIN_SHARK_LAYER, HighfinSharkModel::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.BABY_HIGHFIN_SHARK_LAYER, BabyHighfinSharkModel::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.BALA_SHARK_LAYER, BalaSharkModel::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.BABY_BALA_SHARK_LAYER, BabyBalaSharkModel::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.RAINBOW_SHARK_LAYER, RainbowSharkModel::createBodyLayer);

        event.registerLayerDefinition(FintasticLayers.CATFISH_BIG, CatfishModelBig::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.CATFISH_CHANNEL, CatfishModelChannel::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.CATFISH_FLAT, CatfishModelFlat::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.CATFISH_PANGASIUS, CatfishModelPangasius::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.CATFISH_PIRAIBA, CatfishModelPiraiba::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.CATFISH_SLENDER, CatfishModelSlender::createBodyLayer);

        event.registerLayerDefinition(FintasticLayers.FEATHERBACK_BIG, FeatherbackModelBig::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.FEATHERBACK_MED, FeatherbackModelMed::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.FEATHERBACK_SMALL, FeatherbackModelSmall::createBodyLayer);

        event.registerLayerDefinition(FintasticLayers.GUPPY, GuppyModel::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.BABY_GUPPY, BabyGuppyModel::createBodyLayer);

        event.registerLayerDefinition(FintasticLayers.FAIRY_SHRIMP, FairyShrimpModel::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.DAPHNIA, DaphniaModel::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.PLECO, PlecoModel::createBodyLayer);

        //Update 3.0
        event.registerLayerDefinition(FintasticLayers.COPEPOD, CopepodModel::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.COD, FintyCodModel::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.SALMON, FintySalmonModel::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.DWARF_FROG, DwarfFrogModel::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.DWARF_FROG_TADPOLE, DwarfFrogTadpoleModel::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.SMALL_CATFISH_BANJO, SmallCatfishBanjoModel::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.SMALL_CATFISH_CORY, SmallCatfishCoryModel::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.SMALL_CATFISH_THORNY, SmallCatfishThornyModel::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.SMALL_CATFISH_TINY_CORY, SmallCatfishTinyCoryModel::createBodyLayer);

        event.registerLayerDefinition(FintasticLayers.HAT_LAYER, HatModel::createArmorLayer);

    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event){
        event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) ->
                pLevel != null && pPos != null ? BiomeColors.getAverageWaterColor(pLevel, pPos)
                        : 0x4f9ce3,
                FintyBlocks.AQUARIUM_GLASS.get(),
                FintyBlocks.AQUARIUM_GLASS_PANE.get(),
                FintyBlocks.TINTED_AQUARIUM_GLASS.get());

        event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) ->
                        pLevel != null && pPos != null ? BiomeColors.getAverageWaterColor(pLevel, pPos)
                                : 0xffffff,
                FintyBlocks.FISHBOWL.get());

        event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) ->
                        pLevel != null && pPos != null ? BiomeColors.getAverageFoliageColor(pLevel, pPos)
                                : FoliageColor.getDefaultColor(),
                FintyBlocks.DUCKWEED.get());

        event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) ->
                0xff6f36, FintyBlocks.INFERNAL_AQUARIUM_GLASS.get(),
                FintyBlocks.TINTED_INFERNAL_AQUARIUM_GLASS.get(),
                FintyBlocks.INFERNAL_AQUARIUM_GLASS_PANE.get());

        event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) ->
                0x4eb821, FintyBlocks.RADON_AQUARIUM_GLASS.get(),
                FintyBlocks.RADON_AQUARIUM_GLASS_PANE.get(),
                FintyBlocks.TINTED_RADON_AQUARIUM_GLASS.get());

        event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) ->
                0x8a1edc, FintyBlocks.SUGAR_AQUARIUM_GLASS.get(),
                FintyBlocks.SUGAR_AQUARIUM_GLASS_PANE.get(),
                FintyBlocks.TINTED_SUGAR_AQUARIUM_GLASS.get());


        event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) ->
                        0xf23b2e, FintyBlocks.RED_AQUARIUM_GLASS.get(),
                FintyBlocks.RED_AQUARIUM_GLASS_PANE.get());

        event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) ->
                        0xffffff, FintyBlocks.WHITE_AQUARIUM_GLASS.get(),
                FintyBlocks.WHITE_AQUARIUM_GLASS_PANE.get());

        event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) ->
                        0xb5b5b5, FintyBlocks.LIGHT_GRAY_AQUARIUM_GLASS.get(),
                FintyBlocks.LIGHT_GRAY_AQUARIUM_GLASS_PANE.get());

        event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) ->
                        0x828282, FintyBlocks.GRAY_AQUARIUM_GLASS.get(),
                FintyBlocks.GRAY_AQUARIUM_GLASS_PANE.get());

        event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) ->
                        0x474747, FintyBlocks.BLACK_AQUARIUM_GLASS.get(),
                FintyBlocks.BLACK_AQUARIUM_GLASS_PANE.get());

        event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) ->
                        0x4a5bf0, FintyBlocks.BLUE_AQUARIUM_GLASS.get(),
                FintyBlocks.BLUE_AQUARIUM_GLASS_PANE.get());

        event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) ->
                        0x9cb4ff, FintyBlocks.LIGHT_BLUE_AQUARIUM_GLASS.get(),
                FintyBlocks.LIGHT_BLUE_AQUARIUM_GLASS_PANE.get());

        event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) ->
                        0x705438, FintyBlocks.BROWN_AQUARIUM_GLASS.get(),
                FintyBlocks.BROWN_AQUARIUM_GLASS_PANE.get());

        event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) ->
                        0xa9c979, FintyBlocks.LIME_AQUARIUM_GLASS.get(),
                FintyBlocks.LIME_AQUARIUM_GLASS_PANE.get());

        event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) ->
                        0x5e8045, FintyBlocks.GREEN_AQUARIUM_GLASS.get(),
                FintyBlocks.GREEN_AQUARIUM_GLASS_PANE.get());

        event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) ->
                        0x3d917e, FintyBlocks.CYAN_AQUARIUM_GLASS.get(),
                FintyBlocks.CYAN_AQUARIUM_GLASS_PANE.get());

        event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) ->
                        0xebc3ea, FintyBlocks.PINK_AQUARIUM_GLASS.get(),
                FintyBlocks.PINK_AQUARIUM_GLASS_PANE.get());

        event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) ->
                        0xed72c8, FintyBlocks.MAGENTA_AQUARIUM_GLASS.get(),
                FintyBlocks.MAGENTA_AQUARIUM_GLASS_PANE.get());

        event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) ->
                        0x9c59c9, FintyBlocks.PURPLE_AQUARIUM_GLASS.get(),
                FintyBlocks.PURPLE_AQUARIUM_GLASS_PANE.get());

        event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) ->
                        0xde8a5f, FintyBlocks.ORANGE_AQUARIUM_GLASS.get(),
                FintyBlocks.ORANGE_AQUARIUM_GLASS_PANE.get());

        event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) ->
                        0xf5e1ab, FintyBlocks.YELLOW_AQUARIUM_GLASS.get(),
                FintyBlocks.YELLOW_AQUARIUM_GLASS_PANE.get());
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {

        event.getItemColors().register((pStack, pTintIndex) -> {
            BlockState blockstate = ((BlockItem)pStack.getItem()).getBlock().defaultBlockState();
            return event.getBlockColors().getColor(blockstate, null, null, pTintIndex);},
                FintyBlocks.AQUARIUM_GLASS.get(),
                FintyBlocks.AQUARIUM_GLASS_PANE.get(),
                FintyBlocks.TINTED_AQUARIUM_GLASS.get());

        event.getItemColors().register((pStack, pTintIndex) -> 0xff6f36,
                FintyBlocks.INFERNAL_AQUARIUM_GLASS.get(),
                FintyBlocks.TINTED_INFERNAL_AQUARIUM_GLASS.get(),
                FintyBlocks.INFERNAL_AQUARIUM_GLASS_PANE.get());

        event.getItemColors().register((pStack, pTintIndex) -> 0x4eb821,
                FintyBlocks.RADON_AQUARIUM_GLASS.get(),
                FintyBlocks.RADON_AQUARIUM_GLASS_PANE.get(),
                FintyBlocks.TINTED_RADON_AQUARIUM_GLASS.get());

        event.getItemColors().register((pStack, pTintIndex) -> 0x8a1edc,
                FintyBlocks.SUGAR_AQUARIUM_GLASS.get(),
                FintyBlocks.SUGAR_AQUARIUM_GLASS_PANE.get(),
                FintyBlocks.TINTED_SUGAR_AQUARIUM_GLASS.get());


        event.getItemColors().register((pState,  pTintIndex) ->
                        0xf23b2e, FintyBlocks.RED_AQUARIUM_GLASS.get(),
                FintyBlocks.RED_AQUARIUM_GLASS_PANE.get());

        event.getItemColors().register((pState,  pTintIndex) ->
                        0xffffff, FintyBlocks.WHITE_AQUARIUM_GLASS.get(),
                FintyBlocks.WHITE_AQUARIUM_GLASS_PANE.get());

        event.getItemColors().register((pState,  pTintIndex) ->
                        0xb5b5b5, FintyBlocks.LIGHT_GRAY_AQUARIUM_GLASS.get(),
                FintyBlocks.LIGHT_GRAY_AQUARIUM_GLASS_PANE.get());

        event.getItemColors().register((pState,  pTintIndex) ->
                        0x828282, FintyBlocks.GRAY_AQUARIUM_GLASS.get(),
                FintyBlocks.GRAY_AQUARIUM_GLASS_PANE.get());

        event.getItemColors().register((pState,  pTintIndex) ->
                        0x474747, FintyBlocks.BLACK_AQUARIUM_GLASS.get(),
                FintyBlocks.BLACK_AQUARIUM_GLASS_PANE.get());

        event.getItemColors().register((pState,  pTintIndex) ->
                        0x4a5bf0, FintyBlocks.BLUE_AQUARIUM_GLASS.get(),
                FintyBlocks.BLUE_AQUARIUM_GLASS_PANE.get());

        event.getItemColors().register((pState,  pTintIndex) ->
                        0x9cb4ff, FintyBlocks.LIGHT_BLUE_AQUARIUM_GLASS.get(),
                FintyBlocks.LIGHT_BLUE_AQUARIUM_GLASS_PANE.get());

        event.getItemColors().register((pState,  pTintIndex) ->
                        0x705438, FintyBlocks.BROWN_AQUARIUM_GLASS.get(),
                FintyBlocks.BROWN_AQUARIUM_GLASS_PANE.get());

        event.getItemColors().register((pState,  pTintIndex) ->
                        0xa9c979, FintyBlocks.LIME_AQUARIUM_GLASS.get(),
                FintyBlocks.LIME_AQUARIUM_GLASS_PANE.get());

        event.getItemColors().register((pState,  pTintIndex) ->
                        0x5e8045, FintyBlocks.GREEN_AQUARIUM_GLASS.get(),
                FintyBlocks.GREEN_AQUARIUM_GLASS_PANE.get());

        event.getItemColors().register((pState,  pTintIndex) ->
                        0x3d917e, FintyBlocks.CYAN_AQUARIUM_GLASS.get(),
                FintyBlocks.CYAN_AQUARIUM_GLASS_PANE.get());

        event.getItemColors().register((pState,  pTintIndex) ->
                        0xebc3ea, FintyBlocks.PINK_AQUARIUM_GLASS.get(),
                FintyBlocks.PINK_AQUARIUM_GLASS_PANE.get());

        event.getItemColors().register((pState,  pTintIndex) ->
                        0xed72c8, FintyBlocks.MAGENTA_AQUARIUM_GLASS.get(),
                FintyBlocks.MAGENTA_AQUARIUM_GLASS_PANE.get());

        event.getItemColors().register((pState,  pTintIndex) ->
                        0x9c59c9, FintyBlocks.PURPLE_AQUARIUM_GLASS.get(),
                FintyBlocks.PURPLE_AQUARIUM_GLASS_PANE.get());

        event.getItemColors().register((pState, pTintIndex) ->
                        0xde8a5f, FintyBlocks.ORANGE_AQUARIUM_GLASS.get(),
                FintyBlocks.ORANGE_AQUARIUM_GLASS_PANE.get());

        event.getItemColors().register((pState,  pTintIndex) ->
                        0xf5e1ab, FintyBlocks.YELLOW_AQUARIUM_GLASS.get(),
                FintyBlocks.YELLOW_AQUARIUM_GLASS_PANE.get());
    }
}
