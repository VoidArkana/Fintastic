package net.voidarkana.fintastic.client.renderers.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.client.FintasticLayers;
import net.voidarkana.fintastic.client.models.entity.FintyCodModel;
import net.voidarkana.fintastic.common.entity.custom.FintasticCod;

public class CodRenderer<T extends FintasticCod> extends MobRenderer<T, FintyCodModel<T>> {

    public CodRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new FintyCodModel<>(pContext.bakeLayer(FintasticLayers.COD)), 0.35f);
    }

    @Override
    public ResourceLocation getTextureLocation(T pEntity) {
        FintasticCod.CodVariant variant = FintasticCod.CodVariant.byId(pEntity.getVariant());
        return new ResourceLocation(Fintastic.MOD_ID,"textures/entity/cod/cod_"+variant.getSerializedName()+".png");
    }

    @Override
    protected void setupRotations(T pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        super.setupRotations(pEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks);
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(pEntityLiving.currentRoll*360/4));
    }
}
