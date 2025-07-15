package net.voidarkana.fintastic.client.renderers.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EnderEyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.client.FintasticLayers;
import net.voidarkana.fintastic.client.models.entity.CoelacanthModel;
import net.voidarkana.fintastic.client.renderers.entity.layers.CoelacanthEyes;
import net.voidarkana.fintastic.common.entity.custom.Coelacanth;

public class CoelacanthRenderer extends MobRenderer<Coelacanth, CoelacanthModel<Coelacanth>> {

    public CoelacanthRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new CoelacanthModel<>(pContext.bakeLayer(FintasticLayers.COELACANTH_LAYER)), 1f);
        this.addLayer(new CoelacanthEyes<>(this));
    }

    @Override
    public void render(Coelacanth entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLightIn) {
        if(entity.isBaby()) {
            poseStack.scale(0.4F, 0.4F, 0.4F);
        }
        else {
            poseStack.scale(1.0F, 1.0F, 1.0F);
        }

        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(Coelacanth pEntity) {
        return new ResourceLocation(Fintastic.MOD_ID, "textures/entity/coelacanth/coelacanth.png");
    }

    @Override
    protected void setupRotations(Coelacanth animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        super.setupRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);
        if (animatable.isInWaterOrBubble()){
            poseStack.mulPose(Axis.ZP.rotationDegrees(animatable.currentRoll*360/4));
        }else {
            poseStack.mulPose(Axis.ZP.rotationDegrees(0));
        }
    }


}
