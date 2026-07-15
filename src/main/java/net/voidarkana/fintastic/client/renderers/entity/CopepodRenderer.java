package net.voidarkana.fintastic.client.renderers.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.client.FintasticLayers;
import net.voidarkana.fintastic.client.models.entity.CopepodModel;
import net.voidarkana.fintastic.common.entity.custom.Copepod;

public class CopepodRenderer<T extends Copepod> extends MobRenderer<T, CopepodModel<T>> {

    public CopepodRenderer(EntityRendererProvider.Context context) {
        super(context, new CopepodModel<>(context.bakeLayer(FintasticLayers.COPEPOD)), 0.35f);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        if (entity.isPlankton())
            return Fintastic.location("textures/entity/copepod/copepod_plankton.png");
        if (entity.isJiggly())
            return Fintastic.location("textures/entity/copepod/copepod_jiggly.png");
        if (entity.isMylops())
            return Fintastic.location("textures/entity/copepod/copepod_mylops.png");

        Copepod.CopepodVariant variant = Copepod.CopepodVariant.byId(entity.getVariant());

        return Fintastic.location("textures/entity/copepod/copepod_"+variant.getSerializedName()+".png");
    }

    @Override
    protected void setupRotations(T entityLiving, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks, float scale) {
        super.setupRotations(entityLiving, poseStack, ageInTicks, rotationYaw, partialTicks, scale);
        poseStack.mulPose(Axis.ZP.rotationDegrees(entityLiving.currentRoll*360/4));
    }
}
