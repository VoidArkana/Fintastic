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
import net.voidarkana.fintastic.client.models.entity.minnows.*;
import net.voidarkana.fintastic.common.entity.custom.Minnow;

public class MinnowRenderer extends MobRenderer<Minnow, FintasticModel<Minnow>> {


    private final MinnowBigModel<Minnow> minnowBigModel;
    private final MinnowHatchetModel<Minnow> minnowHatchetModel;
    private final MinnowRoundModel<Minnow> minnowRoundModel;
    private final MinnowSlimModel<Minnow> minnowSlimModel;
    private final MinnowSmallModel<Minnow> minnowSmallModel;
    private final MinnowThinModel<Minnow> minnowThinModel;

    public MinnowRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new MinnowBigModel<>(pContext.bakeLayer(FintasticLayers.MINNOW_BIG_LAYER)), 0.25f);

        this.minnowBigModel = new MinnowBigModel<>(pContext.bakeLayer(FintasticLayers.MINNOW_BIG_LAYER));
        this.minnowHatchetModel = new MinnowHatchetModel<>(pContext.bakeLayer(FintasticLayers.MINNOW_HATCHET_LAYER));
        this.minnowRoundModel = new MinnowRoundModel<>(pContext.bakeLayer(FintasticLayers.MINNOW_ROUND_LAYER));
        this.minnowSlimModel = new MinnowSlimModel<>(pContext.bakeLayer(FintasticLayers.MINNOW_SLIM_LAYER));
        this.minnowSmallModel = new MinnowSmallModel<>(pContext.bakeLayer(FintasticLayers.MINNOW_SMALL_LAYER));
        this.minnowThinModel = new MinnowThinModel<>(pContext.bakeLayer(FintasticLayers.MINNOW_THIN_LAYER));
    }

    @Override
    public void render(Minnow entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        Minnow.MinnowVariant minnowVariant = Minnow.MinnowVariant.byId(entity.getVariant());

        switch (minnowVariant.getModel()){
            case 1:
                this.model = minnowHatchetModel;
                break;
            case 2:
                this.model = minnowRoundModel;
                break;
            case 3:
                this.model = minnowSlimModel;
                break;
            case 4:
                this.model = minnowSmallModel;
                break;
            case 5:
                this.model = minnowThinModel;
                break;
            default:
                this.model = minnowBigModel;
        }

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(Minnow pEntity) {
        Minnow.MinnowVariant minnowVariant = Minnow.MinnowVariant.byId(pEntity.getVariant());
        return new ResourceLocation(Fintastic.MOD_ID,"textures/entity/minnow/"+Minnow.getModelName(minnowVariant.getModel())+"/"+minnowVariant.getSerializedName()+".png");
    }

    @Override
    protected void setupRotations(Minnow pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        super.setupRotations(pEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks);
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(pEntityLiving.currentRoll*360/4));
    }
}
