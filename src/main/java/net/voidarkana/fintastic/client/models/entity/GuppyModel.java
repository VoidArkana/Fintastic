package net.voidarkana.fintastic.client.models.entity;

import net.minecraft.resources.ResourceLocation;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.entity.custom.GuppyEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class GuppyModel extends GeoModel<GuppyEntity> {
    @Override
    public ResourceLocation getModelResource(GuppyEntity barbfishEntity) {
        return new ResourceLocation(Fintastic.MOD_ID, barbfishEntity.isBaby() ?  "geo/baby_guppy.geo.json" : "geo/guppy.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GuppyEntity barbfishEntity) {
        return new ResourceLocation(Fintastic.MOD_ID, "textures/entity/guppy/guppy_base_"+barbfishEntity.getVariantSkin()+".png");
    }

    @Override
    public ResourceLocation getAnimationResource(GuppyEntity barbfishEntity) {
        return new ResourceLocation(Fintastic.MOD_ID, "animations/guppy.animation.json");
    }

    @Override
    public void setCustomAnimations(GuppyEntity animatable, long instanceId, AnimationState<GuppyEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        CoreGeoBone swimControl = this.getAnimationProcessor().getBone("swim_control");

        EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        swimControl.setRotX(((entityData.headPitch() * ((float) Math.PI / 180F))));
        swimControl.setRotZ(-((entityData.netHeadYaw() * ((float) Math.PI / 180F))/2));
    }
}