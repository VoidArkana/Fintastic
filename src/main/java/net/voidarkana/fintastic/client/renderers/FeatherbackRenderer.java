package net.voidarkana.fintastic.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.client.models.FeatherbackModel;
import net.voidarkana.fintastic.common.entity.custom.FeatherbackEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FeatherbackRenderer extends GeoEntityRenderer<FeatherbackEntity> {
    public FeatherbackRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FeatherbackModel());
    }

    @Override
    public ResourceLocation getTextureLocation(FeatherbackEntity featherbackEntity) {
        return switch (featherbackEntity.getVariant()){
            case 1 -> new ResourceLocation(Fintastic.MOD_ID, "textures/entity/featherback/featherback_1.png");
            case 2-> new ResourceLocation(Fintastic.MOD_ID, "textures/entity/featherback/featherback_2.png");
            case 3 -> new ResourceLocation(Fintastic.MOD_ID, "textures/entity/featherback/featherback_3.png");
            default -> new ResourceLocation(Fintastic.MOD_ID, "textures/entity/featherback/featherback_0.png");
        };
    }

    @Override
    public void render(FeatherbackEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
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
    protected void applyRotations(FeatherbackEntity animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);
        if (animatable.isInWater()){
            poseStack.mulPose(Axis.ZP.rotationDegrees(animatable.currentRoll*360/4));
            //poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTick, -animatable.prevTilt, -animatable.tilt)));
        }
    }
}
