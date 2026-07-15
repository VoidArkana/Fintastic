package net.voidarkana.fintastic.client.models.entity.base;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import org.joml.Vector3f;

import java.util.function.Function;

public abstract class FintasticModel <E extends Entity> extends HierarchicalModel<E> {

    private static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();

    public final float youngScaleFactor;
    public final float bodyYOffset;

    public FintasticModel(float youngScaleFactor, float bodyYOffset) {
        this(youngScaleFactor, bodyYOffset, RenderType::entityCutoutNoCull);
    }

    public FintasticModel(float youngScaleFactor, float bodyYOffset, Function<ResourceLocation, RenderType> renderType) {
        super(renderType);
        this.bodyYOffset = bodyYOffset;
        this.youngScaleFactor = youngScaleFactor;
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {

        poseStack.pushPose();

        if (this.young) {
            poseStack.scale(this.youngScaleFactor, this.youngScaleFactor, this.youngScaleFactor);
            poseStack.translate(0.0F, this.bodyYOffset, 0.0F);
        }

        this.root().render(poseStack, buffer, packedLight, packedOverlay, color);

        poseStack.popPose();
    }

    protected void animateIdle(AnimationState animationState, AnimationDefinition animationDefinition, float ageInTicks, float speed, float scale) {
        animationState.updateTime(ageInTicks, speed);
        animationState.ifStarted((state) -> {
            KeyframeAnimations.animate(this, animationDefinition, state.getAccumulatedTime(), scale, FintasticModel.ANIMATION_VECTOR_CACHE);
        });
    }

    protected void animate(AnimationState animationState, AnimationDefinition animationDefinition, float ageInTicks) {
        this.animate(animationState, animationDefinition, ageInTicks, 1.0F);
    }

    protected void animateWalk(AnimationDefinition animationDefinition, float limbSwing, float limbSwingAmount, float maxAnimationSpeed, float animationScaleFactor) {
        if (limbSwing != 0 && limbSwingAmount != 0){
            long i = (long)(limbSwing * 50.0F * maxAnimationSpeed);
            float f = Math.min(limbSwingAmount * animationScaleFactor, 1.0F);
            KeyframeAnimations.animate(this, animationDefinition, i, f, FintasticModel.ANIMATION_VECTOR_CACHE);
        }
    }

    protected void animate(AnimationState animationState, AnimationDefinition animationDefinition, float ageInTicks, float speed) {
        animationState.updateTime(ageInTicks, speed);
        animationState.ifStarted((state) -> {
            KeyframeAnimations.animate(this, animationDefinition, state.getAccumulatedTime(), 1.0F, FintasticModel.ANIMATION_VECTOR_CACHE);
        });
    }

    protected void applyStatic(AnimationDefinition animationDefinition) {
        KeyframeAnimations.animate(this, animationDefinition, 0L, 1.0F, FintasticModel.ANIMATION_VECTOR_CACHE);
    }
}
