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

    private final float youngScaleFactor;
    private final float bodyYOffset;

    public FintasticModel(float pYoungScaleFactor, float pBodyYOffset) {
        this(pYoungScaleFactor, pBodyYOffset, RenderType::entityCutoutNoCull);
    }

    public FintasticModel(float pYoungScaleFactor, float pBodyYOffset, Function<ResourceLocation, RenderType> pRenderType) {
        super(pRenderType);
        this.bodyYOffset = pBodyYOffset;
        this.youngScaleFactor = pYoungScaleFactor;
    }

    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {

        pPoseStack.pushPose();

        if (this.young) {
            pPoseStack.scale(this.youngScaleFactor, this.youngScaleFactor, this.youngScaleFactor);
            pPoseStack.translate(0.0F, this.bodyYOffset, 0.0F);
        }

        this.root().render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);

        pPoseStack.popPose();
    }

    protected void animateIdle(AnimationState pAnimationState, AnimationDefinition pAnimationDefinition, float pAgeInTicks, float pSpeed, float pScale) {
        float scale = Math.max(0, Math.min(1-Math.abs(pSpeed), 1f));
        pAnimationState.updateTime(pAgeInTicks, pSpeed);
        pAnimationState.ifStarted((p_233392_) -> {
            KeyframeAnimations.animate(this, pAnimationDefinition, p_233392_.getAccumulatedTime(), pScale, FintasticModel.ANIMATION_VECTOR_CACHE);
        });
    }

    protected void animate(AnimationState pAnimationState, AnimationDefinition pAnimationDefinition, float pAgeInTicks) {
        this.animate(pAnimationState, pAnimationDefinition, pAgeInTicks, 1.0F);
    }

    protected void animateWalk(AnimationDefinition pAnimationDefinition, float pLimbSwing, float pLimbSwingAmount, float pMaxAnimationSpeed, float pAnimationScaleFactor) {
        if (pLimbSwing != 0 && pLimbSwingAmount != 0){
            long i = (long)(pLimbSwing * 50.0F * pMaxAnimationSpeed);
            float f = Math.min(pLimbSwingAmount * pAnimationScaleFactor, 1.0F);
            KeyframeAnimations.animate(this, pAnimationDefinition, i, f, FintasticModel.ANIMATION_VECTOR_CACHE);
        }
    }

    protected void animate(AnimationState pAnimationState, AnimationDefinition pAnimationDefinition, float pAgeInTicks, float pSpeed) {
        pAnimationState.updateTime(pAgeInTicks, pSpeed);
        pAnimationState.ifStarted((p_233392_) -> {
            KeyframeAnimations.animate(this, pAnimationDefinition, p_233392_.getAccumulatedTime(), 1.0F, FintasticModel.ANIMATION_VECTOR_CACHE);
        });
    }

    protected void applyStatic(AnimationDefinition pAnimationDefinition) {
        KeyframeAnimations.animate(this, pAnimationDefinition, 0L, 1.0F, FintasticModel.ANIMATION_VECTOR_CACHE);
    }
}
