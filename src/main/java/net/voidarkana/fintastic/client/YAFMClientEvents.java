package net.voidarkana.fintastic.client;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.client.models.armor.HatModel;
import net.voidarkana.fintastic.client.models.entity.minnows.*;
import net.voidarkana.fintastic.client.models.entity.moonies.MoonyMidModel;
import net.voidarkana.fintastic.client.models.entity.moonies.MoonySmallModel;
import net.voidarkana.fintastic.client.models.entity.moonies.MoonyTallModel;
import net.voidarkana.fintastic.common.block.YAFMBlocks;

@Mod.EventBusSubscriber(modid = Fintastic.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class YAFMClientEvents {

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

        event.registerLayerDefinition(FintasticLayers.HAT_LAYER, HatModel::createArmorLayer);

    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event){
        event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) ->
                pLevel != null && pPos != null ? BiomeColors.getAverageWaterColor(pLevel, pPos)
                        : 0x4f9ce3,
                YAFMBlocks.AQUARIUM_GLASS.get(),
                YAFMBlocks.AQUARIUM_GLASS_PANE.get(),
                YAFMBlocks.TINTED_AQUARIUM_GLASS.get());

        event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) ->
                0xff6f36, YAFMBlocks.INFERNAL_AQUARIUM_GLASS.get(),
                YAFMBlocks.TINTED_INFERNAL_AQUARIUM_GLASS.get(),
                YAFMBlocks.INFERNAL_AQUARIUM_GLASS_PANE.get());

        event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) ->
                0x4eb821, YAFMBlocks.RADON_AQUARIUM_GLASS.get(),
                YAFMBlocks.RADON_AQUARIUM_GLASS_PANE.get(),
                YAFMBlocks.TINTED_RADON_AQUARIUM_GLASS.get());

        event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) ->
                0x8a1edc, YAFMBlocks.SUGAR_AQUARIUM_GLASS.get(),
                YAFMBlocks.SUGAR_AQUARIUM_GLASS_PANE.get(),
                YAFMBlocks.TINTED_SUGAR_AQUARIUM_GLASS.get());
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {

        event.getItemColors().register((pStack, pTintIndex) -> {
            BlockState blockstate = ((BlockItem)pStack.getItem()).getBlock().defaultBlockState();
            return event.getBlockColors().getColor(blockstate, null, null, pTintIndex);},
                YAFMBlocks.AQUARIUM_GLASS.get(),
                YAFMBlocks.AQUARIUM_GLASS_PANE.get(),
                YAFMBlocks.TINTED_AQUARIUM_GLASS.get());

        event.getItemColors().register((pStack, pTintIndex) -> 0xff6f36,
                YAFMBlocks.INFERNAL_AQUARIUM_GLASS.get(),
                YAFMBlocks.TINTED_INFERNAL_AQUARIUM_GLASS.get(),
                YAFMBlocks.INFERNAL_AQUARIUM_GLASS_PANE.get());

        event.getItemColors().register((pStack, pTintIndex) -> 0x4eb821,
                YAFMBlocks.RADON_AQUARIUM_GLASS.get(),
                YAFMBlocks.RADON_AQUARIUM_GLASS_PANE.get(),
                YAFMBlocks.TINTED_RADON_AQUARIUM_GLASS.get());

        event.getItemColors().register((pStack, pTintIndex) -> 0x8a1edc,
                YAFMBlocks.SUGAR_AQUARIUM_GLASS.get(),
                YAFMBlocks.SUGAR_AQUARIUM_GLASS_PANE.get(),
                YAFMBlocks.TINTED_SUGAR_AQUARIUM_GLASS.get());

    }
}
