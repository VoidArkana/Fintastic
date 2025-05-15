package net.voidarkana.fintastic.client;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.client.models.moonies.MoonyMidModel;
import net.voidarkana.fintastic.client.models.moonies.MoonySmallModel;
import net.voidarkana.fintastic.client.models.moonies.MoonyTallModel;
import net.voidarkana.fintastic.client.renderers.*;
import net.voidarkana.fintastic.common.block.YAFMBlocks;
import net.voidarkana.fintastic.common.entity.YAFMEntities;
import net.voidarkana.fintastic.common.item.YAFMItems;
import net.voidarkana.fintastic.common.item.custom.FishnetItem;

import static net.voidarkana.fintastic.Fintastic.PROXY;

@Mod.EventBusSubscriber(modid = Fintastic.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class YAFMClientEvents {

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        
        event.registerLayerDefinition(FintasticLayers.MOONYMID_LAYER, MoonyMidModel::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.MOONYSMALL_LAYER, MoonySmallModel::createBodyLayer);
        event.registerLayerDefinition(FintasticLayers.MOONYTALL_LAYER, MoonyTallModel::createBodyLayer);

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
