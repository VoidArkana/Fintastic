package net.voidarkana.fintastic.client.renderers.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LightLayer;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.client.models.entity.CoelacanthModel;
import net.voidarkana.fintastic.common.entity.custom.Coelacanth;

public class CoelacanthEyes<T extends Coelacanth> extends EyesLayer<T, CoelacanthModel<T>> {

    private static final RenderType COELACANTH_EYES = RenderType.eyes(new ResourceLocation(Fintastic.MOD_ID,"textures/entity/coelacanth/coelacanth_eyes.png"));

    public CoelacanthEyes(RenderLayerParent<T, CoelacanthModel<T>> pRenderer) {
        super(pRenderer);
    }

    @Override
    public RenderType renderType() {
        return COELACANTH_EYES;
    }

    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, Coelacanth fish, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        long roundedTime = fish.level().getDayTime() % 24000;
        boolean night = roundedTime >= 13000 && roundedTime <= 22000;
        BlockPos ratPos = fish.getLightPosition();
        int i = fish.level().getBrightness(LightLayer.SKY, ratPos);
        int j = fish.level().getBrightness(LightLayer.BLOCK, ratPos);
        int brightness;
        if (night) {
            brightness = j;
        } else {
            brightness = Math.max(i, j);
        }
        if (brightness < 7) {
            VertexConsumer ivertexbuilder = bufferIn.getBuffer(COELACANTH_EYES);
            this.getParentModel().renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, LivingEntityRenderer.getOverlayCoords(fish, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        }

    }
}
