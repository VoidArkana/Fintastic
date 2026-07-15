package net.voidarkana.fintastic.client.renderers.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.client.FintasticLayers;
import net.voidarkana.fintastic.client.models.entity.FairyShrimpModel;
import net.voidarkana.fintastic.common.entity.custom.FairyShrimp;

public class FairyShrimpRenderer<T extends FairyShrimp> extends MobRenderer<T, FairyShrimpModel<T>> {


    public FairyShrimpRenderer(EntityRendererProvider.Context context) {
        super(context, new FairyShrimpModel<>(context.bakeLayer(FintasticLayers.FAIRY_SHRIMP)), 0.25f);
    }


    @Override
    public ResourceLocation getTextureLocation(T entity) {
        if (entity.isNinni())
            return Fintastic.location("textures/entity/fairy_shrimp/fairy_shrimp_ninni.png");

        FairyShrimp.FairyShrimpVariant variant = FairyShrimp.FairyShrimpVariant.byId(entity.getVariant());

        return Fintastic.location("textures/entity/fairy_shrimp/fairy_shrimp_"+variant.getSerializedName()+".png");
    }

    @Override
    protected void setupRotations(T entityLiving, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks, float scale) {
        super.setupRotations(entityLiving, poseStack, ageInTicks, rotationYaw, partialTicks, scale);
        poseStack.mulPose(Axis.ZP.rotationDegrees(entityLiving.currentRoll*360));
    }
}
