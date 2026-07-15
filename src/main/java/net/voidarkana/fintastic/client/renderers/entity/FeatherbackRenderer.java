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
import net.voidarkana.fintastic.client.models.entity.featherback.*;
import net.voidarkana.fintastic.common.entity.custom.Featherback;

public class FeatherbackRenderer<T extends Featherback> extends MobRenderer<T, FintasticModel<T>> {

    private final FeatherbackModelBig<T> modelBig;
    private final FeatherbackModelMed<T> modelMed;
    private final FeatherbackModelSmall<T> modelSmall;

    public FeatherbackRenderer(EntityRendererProvider.Context context) {
        super(context, new FeatherbackModelBig<>(context.bakeLayer(FintasticLayers.FEATHERBACK_BIG)), 0.25f);

        this.modelBig = new FeatherbackModelBig<>(context.bakeLayer(FintasticLayers.FEATHERBACK_BIG));
        this.modelMed = new FeatherbackModelMed<>(context.bakeLayer(FintasticLayers.FEATHERBACK_MED));
        this.modelSmall = new FeatherbackModelSmall<>(context.bakeLayer(FintasticLayers.FEATHERBACK_SMALL));
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
            poseStack.translate(0, 0, 0);

            Featherback.FeatherbackVariant variant = Featherback.FeatherbackVariant.byId(entity.getVariant());
            switch (variant.getModel()){
                case 1:
                    this.model = modelMed;
                    break;
                case 2:
                    this.model = modelBig;
                    break;
                default:
                    this.model = modelSmall;
            }
            super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {

        Featherback.FeatherbackVariant variant = Featherback.FeatherbackVariant.byId(entity.getVariant());

        return Fintastic.location("textures/entity/featherback/featherback_"+variant.getSerializedName()+".png");
    }

    @Override
    protected void setupRotations(T entityLiving, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks, float scale) {
        super.setupRotations(entityLiving, poseStack, ageInTicks, rotationYaw, partialTicks, scale);
        poseStack.mulPose(Axis.ZP.rotationDegrees(entityLiving.currentRoll*360/2));
    }
}
