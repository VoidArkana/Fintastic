package net.voidarkana.fintastic.client.renderers.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.client.FintasticLayers;
import net.voidarkana.fintastic.client.models.entity.base.FintasticModel;
import net.voidarkana.fintastic.client.models.entity.gourami.GouramiHugeModel;
import net.voidarkana.fintastic.client.models.entity.gourami.GouramiMedModel;
import net.voidarkana.fintastic.client.models.entity.gourami.GouramiSmallModel;
import net.voidarkana.fintastic.common.entity.custom.Gourami;
import org.jetbrains.annotations.Nullable;

public class GouramiRenderer extends MobRenderer<Gourami, FintasticModel<Gourami>> {

    private final GouramiHugeModel<Gourami> gouramiHugeModel;
    private final GouramiMedModel<Gourami> gouramiMedModel;
    private final GouramiSmallModel<Gourami> gouramiSmallModel;

    public GouramiRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new GouramiHugeModel<>(pContext.bakeLayer(FintasticLayers.GOURAMI_HUGE_LAYER)), 0.25f);

        this.gouramiHugeModel = new GouramiHugeModel<>(pContext.bakeLayer(FintasticLayers.GOURAMI_HUGE_LAYER));
        this.gouramiMedModel = new GouramiMedModel<>(pContext.bakeLayer(FintasticLayers.GOURAMI_MED_LAYER));
        this.gouramiSmallModel = new GouramiSmallModel<>(pContext.bakeLayer(FintasticLayers.GOURAMI_SMALL_LAYER));
    }

    @Override
    public void render(Gourami entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

        switch (entity.getVariantModel()){
            case 1:
                this.model = gouramiMedModel;
                break;
            case 2:
                this.model = gouramiSmallModel;
                break;
            default:
                this.model = gouramiHugeModel;
        }

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(Gourami pEntity) {

        int joinedVariantID = Integer.decode(String.valueOf(pEntity.getVariantModel()) + pEntity.getVariantSkin());
        Gourami.GouramiVariant gouramiVariant = Gourami.GouramiVariant.byId(joinedVariantID);

        return new ResourceLocation(Fintastic.MOD_ID,"textures/entity/gourami/"+gouramiVariant.getModelName()+"/gourami_"+gouramiVariant.getName()+".png");
    }

    @Override
    protected @Nullable RenderType getRenderType(Gourami pEntity, boolean pBodyVisible, boolean pTranslucent, boolean pGlowing) {

        int joinedVariantID = Integer.decode(String.valueOf(pEntity.getVariantModel()) + pEntity.getVariantSkin());
        Gourami.GouramiVariant gouramiVariant = Gourami.GouramiVariant.byId(joinedVariantID);

        return RenderType.entityCutout(new ResourceLocation(Fintastic.MOD_ID,"textures/entity/gourami/"+gouramiVariant.getModelName()+"/gourami_"+gouramiVariant.getName()+".png"));
    }

    @Override
    protected void setupRotations(Gourami pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        super.setupRotations(pEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks);
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(pEntityLiving.currentRoll*360/4));
    }
}
