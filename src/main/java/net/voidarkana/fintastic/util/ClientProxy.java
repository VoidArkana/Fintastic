package net.voidarkana.fintastic.util;


import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.client.renderers.*;
import net.voidarkana.fintastic.common.entity.YAFMEntities;
import net.voidarkana.fintastic.common.item.YAFMItems;
import net.voidarkana.fintastic.common.item.custom.FishnetItem;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Fintastic.MOD_ID, value = Dist.CLIENT)
public class ClientProxy extends CommonProxy{

    public void init() {}

    public void clientInit() {

        Fintastic.CALLBACKS.forEach(Runnable::run);
        Fintastic.CALLBACKS.clear();

        EntityRenderers.register(YAFMEntities.FEATHERBACK.get(), FeatherbackRenderer::new);
        EntityRenderers.register(YAFMEntities.MINNOW.get(), MinnowRenderer::new);
        EntityRenderers.register(YAFMEntities.CATFISH.get(), CatfishRenderer::new);
        EntityRenderers.register(YAFMEntities.GUPPY.get(), GuppyRenderer::new);
        EntityRenderers.register(YAFMEntities.FRESHWATER_SHARK.get(), FreshwaterSharkRenderer::new);
        EntityRenderers.register(YAFMEntities.PLECO.get(), PlecoRenderer::new);
        EntityRenderers.register(YAFMEntities.ARAPAIMA.get(), ArapaimaRenderer::new);

        EntityRenderers.register(YAFMEntities.DAPHNIA.get(), DaphniaRenderer::new);

        EntityRenderers.register(YAFMEntities.ARTEMIA.get(), ArtemiaRenderer::new);


        EntityRenderers.register(YAFMEntities.MOONY.get(), MoonyRenderer::new);

        ItemProperties.register(YAFMItems.FISHNET.get(), new ResourceLocation("has_entity"),
                (stack, level, living, i) -> FishnetItem.containsEntity(stack) ? 1 : 0);

    }

    public Player getClientSidePlayer() {
        return Minecraft.getInstance().player;
    }
}
