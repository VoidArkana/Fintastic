package net.voidarkana.fintastic.client.renderers.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.client.FintasticLayers;
import net.voidarkana.fintastic.client.models.entity.FintySalmonModel;
import net.voidarkana.fintastic.common.entity.custom.FintasticSalmon;

public class SalmonRenderer<T extends FintasticSalmon> extends MobRenderer<T, FintySalmonModel<T>> {

    public SalmonRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new FintySalmonModel<>(pContext.bakeLayer(FintasticLayers.SALMON)), 0.35f);
    }

    @Override
    public ResourceLocation getTextureLocation(T pEntity) {
        FintasticSalmon.SalmonVariant variant = FintasticSalmon.SalmonVariant.byId(pEntity.getVariant());
        return new ResourceLocation(Fintastic.MOD_ID,"textures/entity/salmon/salmon_"+variant.getSerializedName()+".png");
    }

    @Override
    protected void setupRotations(T pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        super.setupRotations(pEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks);
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(pEntityLiving.currentRoll*360/4));
    }

    @Override
    public void render(T pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        this.model.salmonSize = FintasticSalmon.SalmonSize.byId(pEntity.getSize());
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
}
