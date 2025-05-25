package net.voidarkana.fintastic.client.models.entity;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.entity.custom.DaphniaEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class DaphniaModel extends GeoModel<DaphniaEntity> {

    @Override
    public ResourceLocation getModelResource(DaphniaEntity artemiaEntity) {
        return new ResourceLocation(Fintastic.MOD_ID, "geo/daphnia.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DaphniaEntity artemiaEntity) {
        return new ResourceLocation(Fintastic.MOD_ID, "textures/entity/daphnia.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DaphniaEntity artemiaEntity) {
        return new ResourceLocation(Fintastic.MOD_ID, "animations/daphnia.animation.json");
    }

    @Override
    public void setCustomAnimations(DaphniaEntity animatable, long instanceId, AnimationState<DaphniaEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        CoreGeoBone swimControl = this.getAnimationProcessor().getBone("swim_control");

        EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        swimControl.setRotX((entityData.headPitch() * ((float) Math.PI / 180F))/2);
    }

    @Override
    public RenderType getRenderType(DaphniaEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(texture);
    }

}
