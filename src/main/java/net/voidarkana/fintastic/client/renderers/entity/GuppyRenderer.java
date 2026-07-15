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
import net.voidarkana.fintastic.common.entity.custom.Guppy;

public class GuppyRenderer <T extends Guppy> extends MobRenderer<T, FintasticModel<T>> {

    private final GuppyModel<T> modelAdult;
    private final BabyGuppyModel<T> modelBaby;

    public GuppyRenderer(EntityRendererProvider.Context context) {
        super(context, new GuppyModel<>(context.bakeLayer(FintasticLayers.GUPPY)), 0.25f);

        this.modelAdult = new GuppyModel<>(context.bakeLayer(FintasticLayers.GUPPY));
        this.modelBaby = new BabyGuppyModel<>(context.bakeLayer(FintasticLayers.BABY_GUPPY));

        this.addLayer(new GuppyFins<>(this, context));
        this.addLayer(new GuppyTail<>(this, context));
        this.addLayer(new GuppyPatternMain<>(this, context));
        this.addLayer(new GuppyPatternSecond<>(this, context));
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
    public ResourceLocation getTextureLocation(T entity) {
        if (entity.isBaby())
            return Fintastic.location("textures/entity/guppy/baby_guppy.png");
        else
            return Fintastic.location("textures/entity/guppy/base/guppy_base_"+entity.getVariant()+".png");
    }

    @Override
    protected void setupRotations(T entityLiving, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks, float scale) {
        super.setupRotations(entityLiving, poseStack, ageInTicks, rotationYaw, partialTicks, scale);
        poseStack.mulPose(Axis.ZP.rotationDegrees(entityLiving.currentRoll*360/4));
    }
}
