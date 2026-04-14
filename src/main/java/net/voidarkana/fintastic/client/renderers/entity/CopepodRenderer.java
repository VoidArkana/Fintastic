package net.voidarkana.fintastic.client.renderers.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.client.FintasticLayers;
import net.voidarkana.fintastic.client.models.entity.CopepodModel;
import net.voidarkana.fintastic.common.entity.custom.Copepod;

public class CopepodRenderer<T extends Copepod> extends MobRenderer<T, CopepodModel<T>> {

    public CopepodRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new CopepodModel<>(pContext.bakeLayer(FintasticLayers.COPEPOD)), 0.35f);
    }

    @Override
    public ResourceLocation getTextureLocation(T pEntity) {
        if (pEntity.isPlankton())
            return new ResourceLocation(Fintastic.MOD_ID,"textures/entity/copepod/copepod_plankton.png");
        if (pEntity.isJiggly())
            return new ResourceLocation(Fintastic.MOD_ID,"textures/entity/copepod/copepod_jiggly.png");
        if (pEntity.isMylops())
            return new ResourceLocation(Fintastic.MOD_ID,"textures/entity/copepod/copepod_mylops.png");

        Copepod.CopepodVariant variant = Copepod.CopepodVariant.byId(pEntity.getVariant());

        return new ResourceLocation(Fintastic.MOD_ID,"textures/entity/copepod/copepod_"+variant.getSerializedName()+".png");
    }

    @Override
    protected void setupRotations(T pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        super.setupRotations(pEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks);
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(pEntityLiving.currentRoll*360/4));
    }
}
