package net.voidarkana.fintastic.client.renderers.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.client.FintasticLayers;
import net.voidarkana.fintastic.client.models.entity.FairyShrimpModel;
import net.voidarkana.fintastic.client.models.entity.base.FintasticModel;
import net.voidarkana.fintastic.client.models.entity.featherback.FeatherbackModelBig;
import net.voidarkana.fintastic.client.models.entity.featherback.FeatherbackModelMed;
import net.voidarkana.fintastic.client.models.entity.featherback.FeatherbackModelSmall;
import net.voidarkana.fintastic.common.entity.custom.FairyShrimp;
import net.voidarkana.fintastic.common.entity.custom.Featherback;

public class FairyShrimpRenderer<T extends FairyShrimp> extends MobRenderer<T, FairyShrimpModel<T>> {


    public FairyShrimpRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new FairyShrimpModel<>(pContext.bakeLayer(FintasticLayers.FAIRY_SHRIMP)), 0.25f);
    }


    @Override
    public ResourceLocation getTextureLocation(T pEntity) {
        if (pEntity.isNinni())
            return new ResourceLocation(Fintastic.MOD_ID,"textures/entity/fairy_shrimp/fairy_shrimp_ninni.png");

        FairyShrimp.FairyShrimpVariant variant = FairyShrimp.FairyShrimpVariant.byId(pEntity.getVariantSkin());

        return new ResourceLocation(Fintastic.MOD_ID,"textures/entity/fairy_shrimp/fairy_shrimp_"+variant.getSerializedName()+".png");
    }

    @Override
    protected void setupRotations(T pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        super.setupRotations(pEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks);
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(pEntityLiving.currentRoll*360));
    }
}
