package net.voidarkana.fintastic.client.renderers.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.client.FintasticLayers;
import net.voidarkana.fintastic.client.models.entity.DaphniaModel;
import net.voidarkana.fintastic.common.entity.custom.Daphnia;

public class DaphniaRenderer<T extends Daphnia> extends MobRenderer<T, DaphniaModel<T>> {

    public DaphniaRenderer(EntityRendererProvider.Context context) {
        super(context, new DaphniaModel<>(context.bakeLayer(FintasticLayers.DAPHNIA)), 0.25f);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return Fintastic.location("textures/entity/daphnia.png");
    }

    @Override
    protected void setupRotations(T entityLiving, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks, float scale) {
        super.setupRotations(entityLiving, poseStack, ageInTicks, rotationYaw, partialTicks, scale);
        poseStack.mulPose(Axis.ZP.rotationDegrees(entityLiving.currentRoll*360/3));
    }
}
