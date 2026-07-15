package net.voidarkana.fintastic.client.renderers.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.client.FintasticLayers;
import net.voidarkana.fintastic.client.models.entity.arapaima.ArapaimaModel;
import net.voidarkana.fintastic.client.models.entity.arapaima.BabyArapaimaModel;
import net.voidarkana.fintastic.client.models.entity.base.FintasticModel;
import net.voidarkana.fintastic.common.entity.custom.Arapaima;

public class ArapaimaRenderer extends MobRenderer<Arapaima, FintasticModel<Arapaima>> {

    private final ArapaimaModel<Arapaima> arapaimaModel;
    private final BabyArapaimaModel<Arapaima> babyArapaimaModel;

    public ArapaimaRenderer(EntityRendererProvider.Context context) {
        super(context, new ArapaimaModel<>(context.bakeLayer(FintasticLayers.ARAPAIMA_LAYER)), 1f);
        this.arapaimaModel = new ArapaimaModel<>(context.bakeLayer(FintasticLayers.ARAPAIMA_LAYER));
        this.babyArapaimaModel = new BabyArapaimaModel<>(context.bakeLayer(FintasticLayers.BABY_ARAPAIMA_LAYER));
    }

    @Override
    public void render(Arapaima entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLightIn) {
        poseStack.pushPose();
            if (entity.isBaby()){
                this.model = babyArapaimaModel;
                poseStack.translate(0.0F, Mth.lerp(entity.getTicksOutsideWater()/3f, 0, 0.05), 0.0F);

            }else {
                this.model = arapaimaModel;
                poseStack.translate(0.0F, Mth.lerp(entity.getTicksOutsideWater()/3f, 0, 0.25), 0.0F);
            }

        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLightIn);
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(Arapaima entity) {
        return Fintastic.location("textures/entity/arapaima/arapaima"+(entity.isBaby() ? "_baby":"")+".png");
    }

    @Override
    protected void setupRotations(Arapaima animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick, float scale) {
        super.setupRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick, scale);
        if (animatable.isInWaterOrBubble()){
            poseStack.mulPose(Axis.ZP.rotationDegrees(animatable.currentRoll*360/4));
        }else {
            poseStack.mulPose(Axis.ZP.rotationDegrees(0));
        }
    }


}
