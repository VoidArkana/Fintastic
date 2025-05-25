package net.voidarkana.fintastic.client.renderers.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.client.models.entity.PlecoModel;
import net.voidarkana.fintastic.common.entity.custom.PlecoEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class PlecoRenderer extends GeoEntityRenderer<PlecoEntity> {
    public PlecoRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new PlecoModel());
    }

    @Override
    public ResourceLocation getTextureLocation(PlecoEntity catfish) {
        return new ResourceLocation(Fintastic.MOD_ID, "textures/entity/pleco.png");
    }

    @Override
    public void render(PlecoEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLightIn) {
        if(entity.isBaby()) {
            poseStack.scale(0.6F, 0.6F, 0.6F);
        }
        else {
            poseStack.scale(1.0F, 1.0F, 1.0F);
        }
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLightIn);
    }

    @Override
    protected void applyRotations(PlecoEntity animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);
        if (animatable.isInWater() && !animatable.onGround()){
            poseStack.mulPose(Axis.ZP.rotationDegrees(animatable.currentRoll*360/4));
           // poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTick, -animatable.prevTilt, -animatable.tilt)));
        }
    }
}
