package net.voidarkana.fintastic.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import net.voidarkana.fintastic.client.models.ArtemiaModel;
import net.voidarkana.fintastic.common.entity.custom.ArtemiaEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ArtemiaRenderer extends GeoEntityRenderer<ArtemiaEntity> {

    public ArtemiaRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ArtemiaModel());
    }

    @Override
    public void render(ArtemiaEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
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
    protected void applyRotations(ArtemiaEntity animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);
        if (animatable.isInWater()){
            //poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTick, -animatable.prevTilt, -animatable.tilt)));
            poseStack.mulPose(Axis.ZP.rotationDegrees(animatable.currentRoll*360/4));
        }
    }

}
