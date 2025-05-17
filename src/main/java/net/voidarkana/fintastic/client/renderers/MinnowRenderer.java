package net.voidarkana.fintastic.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.client.FintasticLayers;
import net.voidarkana.fintastic.client.models.base.FintasticModel;
import net.voidarkana.fintastic.client.models.minnows.*;
import net.voidarkana.fintastic.common.entity.custom.MinnowEntity;
import org.jetbrains.annotations.Nullable;

public class MinnowRenderer extends MobRenderer<MinnowEntity, FintasticModel<MinnowEntity>> {


    private final MinnowBigModel<MinnowEntity> minnowBigModel;
    private final MinnowHatchetModel<MinnowEntity> minnowHatchetModel;
    private final MinnowRoundModel<MinnowEntity> minnowRoundModel;
    private final MinnowSlimModel<MinnowEntity> minnowSlimModel;
    private final MinnowSmallModel<MinnowEntity> minnowSmallModel;
    private final MinnowThinModel<MinnowEntity> minnowThinModel;

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
    public void render(MinnowEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

        switch (entity.getVariantModel()){
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
    public ResourceLocation getTextureLocation(MinnowEntity pEntity) {

        int joinedVariantID = Integer.decode(String.valueOf(pEntity.getVariantModel()) + pEntity.getVariantSkin());
        MinnowEntity.MinnowVariant minnowVariant = MinnowEntity.MinnowVariant.byId(joinedVariantID);

        return new ResourceLocation(Fintastic.MOD_ID,"textures/entity/minnow/"+minnowVariant.getModelName()+"/"+minnowVariant.getName()+".png");
    }

    @Override
    protected @Nullable RenderType getRenderType(MinnowEntity pEntity, boolean pBodyVisible, boolean pTranslucent, boolean pGlowing) {

        int joinedVariantID = Integer.decode(String.valueOf(pEntity.getVariantModel()) + pEntity.getVariantSkin());
        MinnowEntity.MinnowVariant minnowVariant = MinnowEntity.MinnowVariant.byId(joinedVariantID);

        return RenderType.entityCutout(new ResourceLocation(Fintastic.MOD_ID,"textures/entity/minnow/"+minnowVariant.getModelName()+"/"+minnowVariant.getName()+".png"));
    }

    @Override
    protected void setupRotations(MinnowEntity pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        super.setupRotations(pEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks);
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(pEntityLiving.currentRoll*360/4));
    }
}
