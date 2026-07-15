package net.voidarkana.fintastic.client.renderers.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.client.FintasticLayers;
import net.voidarkana.fintastic.client.models.entity.FintySalmonModel;
import net.voidarkana.fintastic.common.entity.custom.FintasticSalmon;

public class SalmonRenderer<T extends FintasticSalmon> extends MobRenderer<T, FintySalmonModel<T>> {

    public SalmonRenderer(EntityRendererProvider.Context context) {
        super(context, new FintySalmonModel<>(context.bakeLayer(FintasticLayers.SALMON)), 0.35f);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        FintasticSalmon.SalmonVariant variant = FintasticSalmon.SalmonVariant.byId(entity.getVariant());
        return Fintastic.location("textures/entity/salmon/salmon_"+variant.getSerializedName()+".png");
    }

    @Override
    protected void setupRotations(T entityLiving, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks, float scale) {
        super.setupRotations(entityLiving, poseStack, ageInTicks, rotationYaw, partialTicks, scale);
        poseStack.mulPose(Axis.ZP.rotationDegrees(entityLiving.currentRoll*360/4));
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        this.model.salmonSize = FintasticSalmon.SalmonSize.byId(entity.getSize());
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
