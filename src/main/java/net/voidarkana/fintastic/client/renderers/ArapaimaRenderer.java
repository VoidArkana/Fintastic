package net.voidarkana.fintastic.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import net.voidarkana.fintastic.client.models.ArapaimaModel;
import net.voidarkana.fintastic.common.entity.custom.ArapaimaEntity;
import net.voidarkana.fintastic.common.entity.custom.DaphniaEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ArapaimaRenderer extends GeoEntityRenderer<ArapaimaEntity> {

    public ArapaimaRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ArapaimaModel());
    }

    @Override
    public void render(ArapaimaEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
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
    protected void applyRotations(ArapaimaEntity animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);
        if (animatable.isInWater()){
            poseStack.mulPose(Axis.ZP.rotationDegrees(animatable.currentRoll*360/4));
        }
    }


}
