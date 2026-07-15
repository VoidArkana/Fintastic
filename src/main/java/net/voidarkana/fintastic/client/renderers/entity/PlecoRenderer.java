package net.voidarkana.fintastic.client.renderers.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.client.FintasticLayers;
import net.voidarkana.fintastic.client.models.entity.PlecoModel;
import net.voidarkana.fintastic.common.entity.custom.Pleco;

public class PlecoRenderer<T extends Pleco> extends MobRenderer<T, PlecoModel<T>> {


    public PlecoRenderer(EntityRendererProvider.Context context) {
        super(context, new PlecoModel<>(context.bakeLayer(FintasticLayers.PLECO)), 0.35f);
    }


    @Override
    public ResourceLocation getTextureLocation(T entity) {

        Pleco.PlecoVariant variant = Pleco.PlecoVariant.byId(entity.getVariant());

        return Fintastic.location("textures/entity/pleco/pleco_"+variant.getSerializedName()+".png");
    }

    @Override
    protected void setupRotations(T entityLiving, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks, float scale) {
        super.setupRotations(entityLiving, poseStack, ageInTicks, rotationYaw, partialTicks, scale);

        if (entityLiving.isAttached()){
            poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(Math.max(0, entityLiving.getTicksOutsideWater()/3-entityLiving.getTicksOnGround()/3), entityLiving.currentRoll*360/4, 0)));
        }
    }
}
