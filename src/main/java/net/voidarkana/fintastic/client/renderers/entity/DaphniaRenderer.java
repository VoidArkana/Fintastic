package net.voidarkana.fintastic.client.renderers.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.voidarkana.fintastic.client.models.entity.DaphniaModel;
import net.voidarkana.fintastic.common.entity.custom.DaphniaEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DaphniaRenderer extends GeoEntityRenderer<DaphniaEntity> {

    public DaphniaRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DaphniaModel());
    }

    @Override
    public void render(DaphniaEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLightIn) {
        if(entity.isBaby()) {
            poseStack.scale(0.6F, 0.6F, 0.6F);
        }
        else {
            poseStack.scale(1.0F, 1.0F, 1.0F);
        }
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLightIn);
    }

//    @Override
//    protected void applyRotations(DaphniaEntity animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
//        super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);
//        if (animatable.isInWater()){
//            poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTick, -animatable.prevTilt, -animatable.tilt)));
//        }
//    }

}
