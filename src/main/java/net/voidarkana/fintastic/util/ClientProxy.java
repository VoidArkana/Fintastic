package net.voidarkana.fintastic.util;


import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.client.renderers.block.FishbowlRenderer;
import net.voidarkana.fintastic.client.renderers.entity.*;
import net.voidarkana.fintastic.client.renderers.item.CustomArmorRenderProperties;
import net.voidarkana.fintastic.common.blockentity.FintyBlockEntities;
import net.voidarkana.fintastic.common.entity.FintyEntities;
import net.voidarkana.fintastic.common.item.FintyItems;
import net.voidarkana.fintastic.common.item.custom.FishnetItem;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Fintastic.MOD_ID, value = Dist.CLIENT)
public class ClientProxy extends CommonProxy{

    public void init() {}

    public void clientInit() {

        Fintastic.CALLBACKS.forEach(Runnable::run);
        Fintastic.CALLBACKS.clear();

        EntityRenderers.register(FintyEntities.MOONY.get(), MoonyRenderer::new);
        EntityRenderers.register(FintyEntities.COELACANTH.get(), CoelacanthRenderer::new);
        EntityRenderers.register(FintyEntities.GOURAMI.get(), GouramiRenderer::new);
        EntityRenderers.register(FintyEntities.MINNOW.get(), MinnowRenderer::new);
        EntityRenderers.register(FintyEntities.ARAPAIMA.get(), ArapaimaRenderer::new);
        EntityRenderers.register(FintyEntities.SHARKMINNOW.get(), SharkminnowRenderer::new);
        EntityRenderers.register(FintyEntities.CATFISH.get(), CatfishRenderer::new);
        EntityRenderers.register(FintyEntities.FEATHERBACK.get(), FeatherbackRenderer::new);
        EntityRenderers.register(FintyEntities.DAPHNIA.get(), DaphniaRenderer::new);
        EntityRenderers.register(FintyEntities.FAIRY_SHRIMP.get(), FairyShrimpRenderer::new);
        EntityRenderers.register(FintyEntities.GUPPY.get(), GuppyRenderer::new);
        EntityRenderers.register(FintyEntities.PLECO.get(), PlecoRenderer::new);

        ItemProperties.register(FintyItems.FISHNET.get(), new ResourceLocation("has_entity"),
                (stack, level, living, i) -> FishnetItem.containsEntity(stack) ? 1 : 0);

        BlockEntityRenderers.register(FintyBlockEntities.FISHBOWL_ENTITY.get(), FishbowlRenderer::new);

    }

    public Player getClientSidePlayer() {
        return Minecraft.getInstance().player;
    }

    @Override
    public Object getArmorRenderProperties() {
        return new CustomArmorRenderProperties();
    }

    @Override
    public Level getWorld() {
        return Minecraft.getInstance().level;
    }
}
