package net.voidarkana.fintastic.client.renderers.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.client.FintasticLayers;
import net.voidarkana.fintastic.client.models.entity.base.FintasticModel;
import net.voidarkana.fintastic.client.models.entity.guppy.GuppyModel;
import net.voidarkana.fintastic.common.entity.custom.Guppy;

public class GuppyFins<T extends Guppy> extends RenderLayer<T, FintasticModel<T>> {

    private final GuppyModel<T> guppyModel;

    public GuppyFins(RenderLayerParent<T, FintasticModel<T>> pRenderer, EntityRendererProvider.Context pContext) {
        super(pRenderer);
        this.guppyModel = new GuppyModel<>(pContext.bakeLayer(FintasticLayers.GUPPY));
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T entity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

        if (!entity.isInvisible() && !entity.isBaby()) {
            ResourceLocation texture = new ResourceLocation(Fintastic.MOD_ID,
                    "textures/entity/guppy/fins/"+entity.getFinsName(entity.getFinModel())
                            +"/guppy_fin_"+entity.getFinsName(entity.getFinModel())+"_"+entity.getFinColor()+".png");

            coloredCutoutModelCopyLayerRender(this.getParentModel(), guppyModel, texture, pPoseStack, pBuffer, pPackedLight,
                    entity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch,
                    pPartialTick, 1, 1, 1);
        }
    }
}
