package net.voidarkana.fintastic.client.renderers.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.client.layer.GuppyFins;
import net.voidarkana.fintastic.client.layer.GuppyPatternMain;
import net.voidarkana.fintastic.client.layer.GuppyPatternSecond;
import net.voidarkana.fintastic.client.layer.GuppyTail;
import net.voidarkana.fintastic.client.models.entity.GuppyModel;
import net.voidarkana.fintastic.common.entity.custom.GuppyEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GuppyRenderer extends GeoEntityRenderer<GuppyEntity> {

    public GuppyRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new GuppyModel());
        this.addRenderLayer(new GuppyFins(this));
        this.addRenderLayer(new GuppyTail(this));
        this.addRenderLayer(new GuppyPatternMain(this));
        this.addRenderLayer(new GuppyPatternSecond(this));
    }

    @Override
    public ResourceLocation getTextureLocation(GuppyEntity barbfishEntity) {
        return new ResourceLocation(Fintastic.MOD_ID, barbfishEntity.isBaby()
                ? "textures/entity/guppy/baby_guppy.png"
                : "textures/entity/guppy/base/guppy_base_"+barbfishEntity.getVariantSkin()+".png");
    }

    @Override
    public void render(GuppyEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLightIn) {
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLightIn);
    }

    @Override
    protected void applyRotations(GuppyEntity animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);
        if (animatable.isInWater()){
            poseStack.mulPose(Axis.ZP.rotationDegrees(animatable.currentRoll*360/4));
            //poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTick, -animatable.prevTilt, -animatable.tilt)));
        }
    }
}
