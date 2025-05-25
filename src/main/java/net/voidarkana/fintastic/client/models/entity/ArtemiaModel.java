package net.voidarkana.fintastic.client.models.entity;

import net.minecraft.resources.ResourceLocation;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.entity.custom.ArtemiaEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class ArtemiaModel extends GeoModel<ArtemiaEntity> {

    @Override
    public ResourceLocation getModelResource(ArtemiaEntity artemiaEntity) {
        return new ResourceLocation(Fintastic.MOD_ID, "geo/artemia.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ArtemiaEntity artemiaEntity) {
        if (artemiaEntity.isNinni()){
            return new ResourceLocation(Fintastic.MOD_ID, "textures/entity/artemia/artemia_ninni.png");
        }else {
            return switch (artemiaEntity.getVariantSkin()){
                case 1 -> new ResourceLocation(Fintastic.MOD_ID, "textures/entity/artemia/artemia_beavertail.png");
                case 2-> new ResourceLocation(Fintastic.MOD_ID, "textures/entity/artemia/artemia_thai.png");
                default -> new ResourceLocation(Fintastic.MOD_ID, "textures/entity/artemia/artemia.png");
            };
        }
    }

    @Override
    public ResourceLocation getAnimationResource(ArtemiaEntity artemiaEntity) {
        return new ResourceLocation(Fintastic.MOD_ID, "animations/artemia.animation.json");
    }

    @Override
    public void setCustomAnimations(ArtemiaEntity animatable, long instanceId, AnimationState<ArtemiaEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        CoreGeoBone swimControl = this.getAnimationProcessor().getBone("swim_control");

        CoreGeoBone head = this.getAnimationProcessor().getBone("head_rot");
        CoreGeoBone tailRot = this.getAnimationProcessor().getBone("tail_rot");
        CoreGeoBone tailTipRot = this.getAnimationProcessor().getBone("tail_tip_rot");

        EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        swimControl.setRotX((entityData.headPitch() * ((float) Math.PI / 180F))*1.8F);

        head.setRotX(((entityData.headPitch() * ((float) Math.PI / 180F))/8));
        tailRot.setRotX(-((entityData.headPitch() * ((float) Math.PI / 180F))/4));
        tailTipRot.setRotX(-((entityData.headPitch() * ((float) Math.PI / 180F))/4));

    }
}
