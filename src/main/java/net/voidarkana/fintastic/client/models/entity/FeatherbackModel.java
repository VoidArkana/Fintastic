package net.voidarkana.fintastic.client.models.entity;

import net.minecraft.resources.ResourceLocation;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.entity.custom.FeatherbackEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class FeatherbackModel extends GeoModel<FeatherbackEntity> {
    @Override
    public ResourceLocation getModelResource(FeatherbackEntity featherbackEntity) {
        return switch (featherbackEntity.getVariant()){
            case 1 -> new ResourceLocation(Fintastic.MOD_ID, "geo/featherback_medium.geo.json");
            case 2, 3 ->new ResourceLocation(Fintastic.MOD_ID, "geo/featherback_big.geo.json");
            default ->new ResourceLocation(Fintastic.MOD_ID, "geo/featherback_small.geo.json");
        };
    }

    @Override
    public ResourceLocation getTextureResource(FeatherbackEntity featherbackEntity) {
        return switch (featherbackEntity.getVariant()){
            case 1 -> new ResourceLocation(Fintastic.MOD_ID, "textures/entity/featherback/featherback_1.png");
            case 2-> new ResourceLocation(Fintastic.MOD_ID, "textures/entity/featherback/featherback_2.png");
            case 3 -> new ResourceLocation(Fintastic.MOD_ID, "textures/entity/featherback/featherback_3.png");
            default -> new ResourceLocation(Fintastic.MOD_ID, "textures/entity/featherback/featherback_0.png");
        };
    }

    @Override
    public ResourceLocation getAnimationResource(FeatherbackEntity featherbackEntity) {
        return new ResourceLocation(Fintastic.MOD_ID, "animations/genericfish.animation.json");
    }

    @Override
    public void setCustomAnimations(FeatherbackEntity animatable, long instanceId, AnimationState<FeatherbackEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        CoreGeoBone swimControl = this.getAnimationProcessor().getBone("swim_control");

        CoreGeoBone tailRot = this.getAnimationProcessor().getBone("tail_rot");

        EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        swimControl.setRotX(((entityData.headPitch() * ((float) Math.PI / 180F))/2));

        tailRot.setRotY(animatable.currentRoll);

        if (animatable.getVariant() == 2 || animatable.getVariant() == 3){
            CoreGeoBone tailTipRot = this.getAnimationProcessor().getBone("tail_tip_rot");
            tailTipRot.setRotY(animatable.currentRoll);
        }

    }
}
