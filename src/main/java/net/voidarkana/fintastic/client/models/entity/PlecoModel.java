package net.voidarkana.fintastic.client.models.entity;

import net.minecraft.resources.ResourceLocation;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.entity.custom.PlecoEntity;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class PlecoModel extends GeoModel<PlecoEntity> {
    @Override
    public ResourceLocation getModelResource(PlecoEntity PlecoEntity) {
        return new ResourceLocation(Fintastic.MOD_ID, "geo/pleco.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PlecoEntity catfish) {
        return new ResourceLocation(Fintastic.MOD_ID, "textures/entity/pleco.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PlecoEntity PlecoEntity) {
        return new ResourceLocation(Fintastic.MOD_ID, "animations/pleco.animation.json");
    }

    @Override
    public void setCustomAnimations(PlecoEntity animatable, long instanceId, AnimationState<PlecoEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

//        CoreGeoBone swimControl = this.getAnimationProcessor().getBone("swim_control");
//
//        EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
//
//        if (!animatable.onGround() && !animatable.getWantsToSwim()){
//            swimControl.setRotX(((entityData.headPitch() * ((float) Math.PI / 180F))/2));
//        }
    }

}
