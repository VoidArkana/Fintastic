package net.voidarkana.fintastic.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.client.models.CatfishModel;
import net.voidarkana.fintastic.common.entity.custom.CatfishEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CatfishRenderer extends GeoEntityRenderer<CatfishEntity> {
    public CatfishRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CatfishModel());
    }

    @Override
    public ResourceLocation getTextureLocation(CatfishEntity catfish) {
        return switch (catfish.getVariant()){
            case 1 -> new ResourceLocation(Fintastic.MOD_ID, "textures/entity/catfish/catfish_blue.png");
            case 2-> new ResourceLocation(Fintastic.MOD_ID, "textures/entity/catfish/catfish_big.png");
            case 3 -> new ResourceLocation(Fintastic.MOD_ID, "textures/entity/catfish/catfish_pangasius.png");
            case 4 -> new ResourceLocation(Fintastic.MOD_ID, "textures/entity/catfish/catfish_bullhead.png");
            case 5 -> new ResourceLocation(Fintastic.MOD_ID, "textures/entity/catfish/catfish_channel.png");
            case 6 -> new ResourceLocation(Fintastic.MOD_ID, "textures/entity/catfish/catfish_flathead.png");
            default -> new ResourceLocation(Fintastic.MOD_ID, "textures/entity/catfish/catfish_piraiba.png");
        };
    }

    @Override
    public void render(CatfishEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
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
    protected void applyRotations(CatfishEntity animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);
        if (animatable.isInWater()){
            poseStack.mulPose(Axis.ZP.rotationDegrees(animatable.currentRoll*360/4));
            //poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTick, -animatable.prevTilt, -animatable.tilt)));
        }
    }
}
