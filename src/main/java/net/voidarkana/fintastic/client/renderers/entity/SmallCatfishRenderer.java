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
import net.voidarkana.fintastic.client.models.entity.small_catfish.SmallCatfishBanjoModel;
import net.voidarkana.fintastic.client.models.entity.small_catfish.SmallCatfishCoryModel;
import net.voidarkana.fintastic.client.models.entity.small_catfish.SmallCatfishThornyModel;
import net.voidarkana.fintastic.client.models.entity.small_catfish.SmallCatfishTinyCoryModel;
import net.voidarkana.fintastic.common.entity.custom.Catfish;
import net.voidarkana.fintastic.common.entity.custom.SmallCatfish;

public class SmallCatfishRenderer<T extends SmallCatfish> extends MobRenderer<T, FintasticModel<T>> {

    private final SmallCatfishBanjoModel<T> modelBanjo;
    private final SmallCatfishCoryModel<T> modelCory;
    private final SmallCatfishTinyCoryModel<T> modelTinyCory;
    private final SmallCatfishThornyModel<T> modelThorny;

    public SmallCatfishRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new SmallCatfishBanjoModel<>(pContext.bakeLayer(FintasticLayers.SMALL_CATFISH_BANJO)), 0.15f);

        this.modelBanjo = new SmallCatfishBanjoModel<>(pContext.bakeLayer(FintasticLayers.SMALL_CATFISH_BANJO));
        this.modelCory = new SmallCatfishCoryModel<>(pContext.bakeLayer(FintasticLayers.SMALL_CATFISH_CORY));
        this.modelTinyCory = new SmallCatfishTinyCoryModel<>(pContext.bakeLayer(FintasticLayers.SMALL_CATFISH_TINY_CORY));
        this.modelThorny = new SmallCatfishThornyModel<>(pContext.bakeLayer(FintasticLayers.SMALL_CATFISH_THORNY));
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
            poseStack.translate(0, 0.05, 0);

            SmallCatfish.SmallCatfishVariant variant = SmallCatfish.SmallCatfishVariant.byId(entity.getVariant());
            switch (variant.getModel()){
                case 1:
                    this.model = modelCory;
                    break;
                case 2:
                    this.model = modelThorny;
                    break;
                case 3:
                    this.model = modelTinyCory;
                    break;
                default:
                    this.model = modelBanjo;
            }
            super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(T pEntity) {
        SmallCatfish.SmallCatfishVariant variant = SmallCatfish.SmallCatfishVariant.byId(pEntity.getVariant());
//        System.out.println(variant.getModelName());
        return new ResourceLocation(Fintastic.MOD_ID,"textures/entity/small_catfish/"+variant.getModelName()+"/smallcatfish_"+variant.getSerializedName()+".png");
    }
}
