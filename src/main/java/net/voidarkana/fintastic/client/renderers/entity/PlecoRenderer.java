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


    public PlecoRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new PlecoModel<>(pContext.bakeLayer(FintasticLayers.PLECO)), 0.35f);
    }


    @Override
    public ResourceLocation getTextureLocation(T pEntity) {

        Pleco.PlecoVariant variant = Pleco.PlecoVariant.byId(pEntity.getVariant());

        return new ResourceLocation(Fintastic.MOD_ID,"textures/entity/pleco/pleco_"+variant.getSerializedName()+".png");
    }

    @Override
    protected void setupRotations(T pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        super.setupRotations(pEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks);

        if (pEntityLiving.isAttached()){
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(Math.max(0, pEntityLiving.getTicksOutsideWater()/3-pEntityLiving.getTicksOnGround()/3), pEntityLiving.currentRoll*360/4, 0)));
        }
    }
}
