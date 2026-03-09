package net.voidarkana.fintastic.client.renderers.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.client.FintasticLayers;
import net.voidarkana.fintastic.client.models.entity.base.FintasticModel;
import net.voidarkana.fintastic.client.models.entity.sharkminnows.*;
import net.voidarkana.fintastic.common.entity.custom.Sharkminnow;
import org.jetbrains.annotations.Nullable;

public class SharkminnowRenderer extends MobRenderer<Sharkminnow, FintasticModel<Sharkminnow>> {

    private final BabyBalaSharkModel<Sharkminnow> babyBalaSharkModel;
    private final BalaSharkModel<Sharkminnow> balaSharkModel;
    private final BabyHighfinSharkModel<Sharkminnow> babyHighfinSharkModel;
    private final HighfinSharkModel<Sharkminnow> highfinSharkModel;
    private final RainbowSharkModel<Sharkminnow> rainbowSharkModel;

    public SharkminnowRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new BalaSharkModel<>(pContext.bakeLayer(FintasticLayers.BALA_SHARK_LAYER)), 0.3f);
        this.balaSharkModel = new BalaSharkModel<>(pContext.bakeLayer(FintasticLayers.BALA_SHARK_LAYER));
        this.babyBalaSharkModel = new BabyBalaSharkModel<>(pContext.bakeLayer(FintasticLayers.BABY_BALA_SHARK_LAYER));
        this.babyHighfinSharkModel = new BabyHighfinSharkModel<>(pContext.bakeLayer(FintasticLayers.BABY_HIGHFIN_SHARK_LAYER));
        this.highfinSharkModel = new HighfinSharkModel<>(pContext.bakeLayer(FintasticLayers.HIGHFIN_SHARK_LAYER));
        this.rainbowSharkModel = new RainbowSharkModel<>(pContext.bakeLayer(FintasticLayers.RAINBOW_SHARK_LAYER));
    }

    @Override
    public void render(Sharkminnow entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLightIn) {

        this.model = switch (entity.getVariantModel()){
            case 2,3,4 -> rainbowSharkModel;
            case 0, 5 -> entity.isBaby() ? babyBalaSharkModel : balaSharkModel;
            default -> entity.isBaby() ? babyHighfinSharkModel : highfinSharkModel;
        };

        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(Sharkminnow pEntity) {
        return new ResourceLocation(Fintastic.MOD_ID, "textures/entity/sharkminnow/"+pEntity.getVariantName()+(pEntity.isBaby() &&
                (pEntity.getVariantModel()<2 && pEntity.getVariantModel()>4) ? "_baby":"")+".png");
    }

    @Override
    protected void setupRotations(Sharkminnow animatable, PoseStack poseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        super.setupRotations(animatable, poseStack, pAgeInTicks, pRotationYaw, pPartialTicks);

        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(animatable.getTicksOutsideWater()/3f, animatable.currentRoll*360/2, 0)));
    }
}