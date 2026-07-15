package net.voidarkana.fintastic.client.renderers.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.client.FintasticLayers;
import net.voidarkana.fintastic.client.models.entity.FintyCodModel;
import net.voidarkana.fintastic.common.entity.custom.FintasticCod;

public class CodRenderer<T extends FintasticCod> extends MobRenderer<T, FintyCodModel<T>> {

    public CodRenderer(EntityRendererProvider.Context context) {
        super(context, new FintyCodModel<>(context.bakeLayer(FintasticLayers.COD)), 0.35f);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        FintasticCod.CodVariant variant = FintasticCod.CodVariant.byId(entity.getVariant());
        return Fintastic.location("textures/entity/cod/cod_"+variant.getSerializedName()+".png");
    }

    @Override
    protected void setupRotations(T entityLiving, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks, float scale) {
        super.setupRotations(entityLiving, poseStack, ageInTicks, rotationYaw, partialTicks, scale);
        poseStack.mulPose(Axis.ZP.rotationDegrees(entityLiving.currentRoll*360/4));
    }
}
