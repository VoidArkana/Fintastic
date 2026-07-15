package net.voidarkana.fintastic.client.renderers.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.client.FintasticLayers;
import net.voidarkana.fintastic.client.models.entity.base.FintasticModel;
import net.voidarkana.fintastic.client.models.entity.guppy.GuppyModel;
import net.voidarkana.fintastic.common.entity.custom.Guppy;

public class GuppyTail <T extends Guppy> extends RenderLayer<T, FintasticModel<T>> {

    private final GuppyModel<T> guppyModel;

    public GuppyTail(RenderLayerParent<T, FintasticModel<T>> renderer, EntityRendererProvider.Context context) {
        super(renderer);
        this.guppyModel = new GuppyModel<>(context.bakeLayer(FintasticLayers.GUPPY));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {

        if (!entity.isInvisible() && !entity.isBaby()) {
            ResourceLocation texture = Fintastic.location("textures/entity/guppy/tail/"+entity.getTailName(entity.getTailModel())
                            +"/guppy_tail_"+entity.getTailName(entity.getTailModel())+"_"+entity.getTailColor()+".png");

            coloredCutoutModelCopyLayerRender(this.getParentModel(), guppyModel, texture, poseStack, buffer, packedLight,
                    entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,
                    partialTick, -1);
        }
    }
}
