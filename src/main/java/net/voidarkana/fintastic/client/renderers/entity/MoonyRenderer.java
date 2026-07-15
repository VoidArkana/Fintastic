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
import net.voidarkana.fintastic.client.models.entity.moonies.MoonyMidModel;
import net.voidarkana.fintastic.client.models.entity.moonies.MoonySmallModel;
import net.voidarkana.fintastic.client.models.entity.moonies.MoonyTallModel;
import net.voidarkana.fintastic.common.entity.custom.Moony;

public class MoonyRenderer extends MobRenderer<Moony, FintasticModel<Moony>> {


    private final MoonyMidModel<Moony> moonyMidModel;
    private final MoonySmallModel<Moony> moonySmallModel;
    private final MoonyTallModel<Moony> moonyTallModel;

    public MoonyRenderer(EntityRendererProvider.Context context) {
        super(context, new MoonyMidModel<>(context.bakeLayer(FintasticLayers.MOONYMID_LAYER)), 0.5f);
        this.moonyMidModel = new MoonyMidModel<>(context.bakeLayer(FintasticLayers.MOONYMID_LAYER));
        this.moonySmallModel = new MoonySmallModel<>(context.bakeLayer(FintasticLayers.MOONYSMALL_LAYER));
        this.moonyTallModel = new MoonyTallModel<>(context.bakeLayer(FintasticLayers.MOONYTALL_LAYER));
    }

    @Override
    public void render(Moony entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

        Moony.MoonyVariant variant = Moony.MoonyVariant.byId(entity.getVariant());
        switch (variant.getModel()){
            case 1:
                this.model = moonyTallModel;
                break;
            case 2:
                this.model = moonyMidModel;
                break;
            default:
                this.model = moonySmallModel;
        }

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(Moony entity) {
        Moony.MoonyVariant variant = Moony.MoonyVariant.byId(entity.getVariant());
        return Fintastic.location("textures/entity/moony/"+variant.getSerializedName()+".png");
    }

    @Override
    protected void setupRotations(Moony entityLiving, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks, float scale) {
        super.setupRotations(entityLiving, poseStack, ageInTicks, rotationYaw, partialTicks, scale);
        poseStack.mulPose(Axis.ZP.rotationDegrees((entityLiving.currentRoll*360)/4));
    }
}
