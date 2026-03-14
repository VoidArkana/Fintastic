package net.voidarkana.fintastic.client.renderers.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.client.FintasticLayers;
import net.voidarkana.fintastic.client.models.entity.base.FintasticModel;
import net.voidarkana.fintastic.client.models.entity.guppy.BabyGuppyModel;
import net.voidarkana.fintastic.client.models.entity.guppy.GuppyModel;
import net.voidarkana.fintastic.client.renderers.entity.layers.GuppyFins;
import net.voidarkana.fintastic.client.renderers.entity.layers.GuppyPatternMain;
import net.voidarkana.fintastic.client.renderers.entity.layers.GuppyPatternSecond;
import net.voidarkana.fintastic.client.renderers.entity.layers.GuppyTail;
import net.voidarkana.fintastic.common.entity.custom.GuppyEntity;

public class GuppyRenderer <T extends GuppyEntity> extends MobRenderer<T, FintasticModel<T>> {

    private final GuppyModel<T> modelAdult;
    private final BabyGuppyModel<T> modelBaby;

    public GuppyRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new GuppyModel<>(pContext.bakeLayer(FintasticLayers.GUPPY)), 0.25f);

        this.modelAdult = new GuppyModel<>(pContext.bakeLayer(FintasticLayers.GUPPY));
        this.modelBaby = new BabyGuppyModel<>(pContext.bakeLayer(FintasticLayers.BABY_GUPPY));

        this.addLayer(new GuppyFins<>(this, pContext));
        this.addLayer(new GuppyTail<>(this, pContext));
        this.addLayer(new GuppyPatternMain<>(this, pContext));
        this.addLayer(new GuppyPatternSecond<>(this, pContext));
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        if (entity.isBaby())
            this.model = this.modelBaby;
        else
            this.model = this.modelAdult;

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(T pEntity) {
        if (pEntity.isBaby())
            return new ResourceLocation(Fintastic.MOD_ID,"textures/entity/guppy/baby_guppy.png");
        else
            return new ResourceLocation(Fintastic.MOD_ID,"textures/entity/guppy/base/guppy_base_"+pEntity.getVariantSkin()+".png");
    }

    @Override
    protected void setupRotations(T pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        super.setupRotations(pEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks);
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(pEntityLiving.currentRoll*360/4));
    }
}
