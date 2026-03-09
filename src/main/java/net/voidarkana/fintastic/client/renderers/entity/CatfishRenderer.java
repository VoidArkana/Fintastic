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
import net.voidarkana.fintastic.client.models.entity.catfish.*;
import net.voidarkana.fintastic.common.entity.custom.Catfish;

public class CatfishRenderer<T extends Catfish> extends MobRenderer<T, FintasticModel<T>> {

    private final CatfishModelBig<T> modelBig;
    private final CatfishModelChannel<T> modelChannel;
    private final CatfishModelFlat<T> modelFlat;
    private final CatfishModelPangasius<T> modelPangasius;
    private final CatfishModelPiraiba<T> modelPiraiba;
    private final CatfishModelSlender<T> modelSlender;

    public CatfishRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new CatfishModelBig<>(pContext.bakeLayer(FintasticLayers.CATFISH_BIG)), 0.25f);

        this.modelBig = new CatfishModelBig<>(pContext.bakeLayer(FintasticLayers.CATFISH_BIG));
        this.modelChannel = new CatfishModelChannel<>(pContext.bakeLayer(FintasticLayers.CATFISH_CHANNEL));
        this.modelFlat = new CatfishModelFlat<>(pContext.bakeLayer(FintasticLayers.CATFISH_FLAT));
        this.modelPangasius = new CatfishModelPangasius<>(pContext.bakeLayer(FintasticLayers.CATFISH_PANGASIUS));
        this.modelPiraiba = new CatfishModelPiraiba<>(pContext.bakeLayer(FintasticLayers.CATFISH_PIRAIBA));
        this.modelSlender = new CatfishModelSlender<>(pContext.bakeLayer(FintasticLayers.CATFISH_SLENDER));
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
            poseStack.translate(0, 0.05, 0);

            Catfish.CatfishVariant variant = Catfish.CatfishVariant.byId(entity.getVariant());
            switch (variant.getModel()){
                case 1:
                    this.model = modelChannel;
                    break;
                case 2:
                    this.model = modelFlat;
                    break;
                case 3:
                    this.model = modelPangasius;
                    break;
                case 4:
                    this.model = modelPiraiba;
                    break;
                case 5:
                    this.model = modelSlender;
                    break;
                default:
                    this.model = modelBig;
            }
            super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(T pEntity) {

        Catfish.CatfishVariant variant = Catfish.CatfishVariant.byId(pEntity.getVariant());

        return new ResourceLocation(Fintastic.MOD_ID,"textures/entity/catfish/catfish_"+variant.getName()+".png");
    }

    @Override
    protected void setupRotations(T pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        super.setupRotations(pEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks);
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(pEntityLiving.currentRoll*360/4));
    }
}
