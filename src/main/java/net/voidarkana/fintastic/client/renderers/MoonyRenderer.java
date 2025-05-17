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
import net.voidarkana.fintastic.client.models.moonies.MoonyMidModel;
import net.voidarkana.fintastic.client.models.moonies.MoonySmallModel;
import net.voidarkana.fintastic.client.models.moonies.MoonyTallModel;
import net.voidarkana.fintastic.common.entity.custom.MinnowEntity;
import net.voidarkana.fintastic.common.entity.custom.Moony;
import org.jetbrains.annotations.Nullable;

public class MoonyRenderer extends MobRenderer<Moony, FintasticModel<Moony>> {


    private final MoonyMidModel<Moony> moonyMidModel;
    private final MoonySmallModel<Moony> moonySmallModel;
    private final MoonyTallModel<Moony> moonyTallModel;

    public MoonyRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new MoonyMidModel<>(pContext.bakeLayer(FintasticLayers.MOONYMID_LAYER)), 0.5f);
        this.moonyMidModel = new MoonyMidModel<>(pContext.bakeLayer(FintasticLayers.MOONYMID_LAYER));
        this.moonySmallModel = new MoonySmallModel<>(pContext.bakeLayer(FintasticLayers.MOONYSMALL_LAYER));
        this.moonyTallModel = new MoonyTallModel<>(pContext.bakeLayer(FintasticLayers.MOONYTALL_LAYER));
    }

    @Override
    public void render(Moony entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

        switch (entity.getVariantModel()){
            case 1:
                this.model = moonySmallModel;
                break;
            case 2:
                this.model = moonyTallModel;
                break;
            default:
                this.model = moonyMidModel;
        }

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(Moony pEntity) {
        return new ResourceLocation(Fintastic.MOD_ID,"textures/entity/moony/"+pEntity.getVariantName()+".png");
    }

    @Override
    protected @Nullable RenderType getRenderType(Moony pEntity, boolean pBodyVisible, boolean pTranslucent, boolean pGlowing) {
        return RenderType.entityCutout(new ResourceLocation(Fintastic.MOD_ID,"textures/entity/moony/"+pEntity.getVariantName()+".png"));
    }

    @Override
    protected void setupRotations(Moony pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        super.setupRotations(pEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks);
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(pEntityLiving.currentRoll*360/4));
    }
}
