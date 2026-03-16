package net.voidarkana.fintastic.client.renderers.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.client.FintasticLayers;
import net.voidarkana.fintastic.client.models.entity.DaphniaModel;
import net.voidarkana.fintastic.client.models.entity.FairyShrimpModel;
import net.voidarkana.fintastic.common.entity.custom.Daphnia;
import net.voidarkana.fintastic.common.entity.custom.FairyShrimp;

public class DaphniaRenderer<T extends Daphnia> extends MobRenderer<T, DaphniaModel<T>> {

    public DaphniaRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new DaphniaModel<>(pContext.bakeLayer(FintasticLayers.DAPHNIA)), 0.25f);
    }

    @Override
    public ResourceLocation getTextureLocation(T pEntity) {
        return new ResourceLocation(Fintastic.MOD_ID,"textures/entity/daphnia.png");
    }

    @Override
    protected void setupRotations(T pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        super.setupRotations(pEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks);
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(pEntityLiving.currentRoll*360/3));
    }
}
