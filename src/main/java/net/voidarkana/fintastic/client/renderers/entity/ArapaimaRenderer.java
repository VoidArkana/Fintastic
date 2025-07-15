package net.voidarkana.fintastic.client.renderers.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.client.FintasticLayers;
import net.voidarkana.fintastic.client.models.entity.arapaima.ArapaimaModel;
import net.voidarkana.fintastic.client.models.entity.arapaima.BabyArapaimaModel;
import net.voidarkana.fintastic.client.models.entity.base.FintasticModel;
import net.voidarkana.fintastic.common.entity.custom.ArapaimaEntity;

public class ArapaimaRenderer extends MobRenderer<ArapaimaEntity, FintasticModel<ArapaimaEntity>> {

    private final ArapaimaModel<ArapaimaEntity> arapaimaModel;
    private final BabyArapaimaModel<ArapaimaEntity> babyArapaimaModel;

    public ArapaimaRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new ArapaimaModel<>(pContext.bakeLayer(FintasticLayers.ARAPAIMA_LAYER)), 1f);
        this.arapaimaModel = new ArapaimaModel<>(pContext.bakeLayer(FintasticLayers.ARAPAIMA_LAYER));
        this.babyArapaimaModel = new BabyArapaimaModel<>(pContext.bakeLayer(FintasticLayers.BABY_ARAPAIMA_LAYER));
    }

    @Override
    public void render(ArapaimaEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLightIn) {
//        if(entity.isBaby()) {
//            poseStack.scale(0.4F, 0.4F, 0.4F);
//        }
//        else {
//            poseStack.scale(1.0F, 1.0F, 1.0F);
//        }

        if (entity.isBaby()){
            this.model = babyArapaimaModel;
        }else {
            this.model = arapaimaModel;
        }

        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(ArapaimaEntity pEntity) {
        return new ResourceLocation(Fintastic.MOD_ID, "textures/entity/arapaima/arapaima"+(pEntity.isBaby() ? "_baby":"")+".png");
    }

    @Override
    protected void setupRotations(ArapaimaEntity animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        super.setupRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);
        if (animatable.isInWaterOrBubble()){
            poseStack.mulPose(Axis.ZP.rotationDegrees(animatable.currentRoll*360/4));
        }else {
            poseStack.mulPose(Axis.ZP.rotationDegrees(0));
        }
    }


}
