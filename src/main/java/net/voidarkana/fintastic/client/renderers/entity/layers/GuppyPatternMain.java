package net.voidarkana.fintastic.client.renderers.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.entity.custom.GuppyEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class GuppyPatternMain extends GeoRenderLayer<GuppyEntity> {

    public GuppyPatternMain(GeoRenderer<GuppyEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, GuppyEntity entity, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {

        if (!entity.isInvisible() && !entity.isBaby()) {
            if (entity.getHasMainPattern()) {
                RenderType cameo = RenderType.entityCutoutNoCull(new ResourceLocation(Fintastic.MOD_ID,
                        "textures/entity/guppy/patterns/"+entity.getMainPatternName(entity.getMainPattern())
                                +"/guppy_pattern_"+entity.getMainPatternName(entity.getMainPattern())+"_"+entity.getMainPatternColor()+".png"));

                ResourceLocation trilobiteModel = new ResourceLocation(Fintastic.MOD_ID, "geo/guppy.geo.json");

                this.getRenderer().reRender(this.getGeoModel().getBakedModel(trilobiteModel), poseStack, bufferSource, entity, renderType,
                        bufferSource.getBuffer(cameo), partialTick, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            }
        }

    }
}
