package net.voidarkana.fintastic.client.renderers.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.client.models.entity.FreshwaterSharkModel;
import net.voidarkana.fintastic.common.entity.custom.FreshwaterSharkEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FreshwaterSharkRenderer extends GeoEntityRenderer<FreshwaterSharkEntity> {

    public FreshwaterSharkRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FreshwaterSharkModel());
    }

    @Override
    public ResourceLocation getTextureLocation(FreshwaterSharkEntity fish) {
        if (fish.isBaby()){
            return switch (fish.getVariantModel()){
                case 1 -> new ResourceLocation(Fintastic.MOD_ID, "textures/entity/freshwater_shark/shark_highfin_juvenile.png");
                case 2 -> new ResourceLocation(Fintastic.MOD_ID, "textures/entity/freshwater_shark/shark_rainbow_juvenile.png");
                case 3 -> new ResourceLocation(Fintastic.MOD_ID, "textures/entity/freshwater_shark/shark_ruby.png");
                default -> new ResourceLocation(Fintastic.MOD_ID, "textures/entity/freshwater_shark/shark_bala_juvenile.png");
            };
        }else {
            return switch (fish.getVariantModel()){
                case 1 -> new ResourceLocation(Fintastic.MOD_ID, "textures/entity/freshwater_shark/shark_highfin_adult_"+fish.getVariantSkin()+".png");
                case 2 -> new ResourceLocation(Fintastic.MOD_ID, "textures/entity/freshwater_shark/shark_rainbow_adult.png");
                case 3 -> new ResourceLocation(Fintastic.MOD_ID, "textures/entity/freshwater_shark/shark_ruby.png");
                default -> new ResourceLocation(Fintastic.MOD_ID, "textures/entity/freshwater_shark/shark_bala_adult.png");
            };
        }
    }


    @Override
    public void render(FreshwaterSharkEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLightIn) {
        if(entity.isBaby() && entity.getVariantModel()==3) {
            poseStack.scale(0.6F, 0.6F, 0.6F);
        }
        else {
            poseStack.scale(1.0F, 1.0F, 1.0F);
        }
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLightIn);
    }

    @Override
    protected void applyRotations(FreshwaterSharkEntity animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);
        if (animatable.isInWater()){
            poseStack.mulPose(Axis.ZP.rotationDegrees(animatable.currentRoll*360/4));
            //poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTick, -animatable.prevTilt, -animatable.tilt)));
        }
    }
}