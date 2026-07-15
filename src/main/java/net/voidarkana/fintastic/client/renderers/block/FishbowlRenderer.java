package net.voidarkana.fintastic.client.renderers.block;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.state.BlockState;
import net.voidarkana.fintastic.common.block.FintyBlocks;
import net.voidarkana.fintastic.common.blockentity.custom.FishbowlBlockEntity;
import net.voidarkana.fintastic.common.item.FintyItems;
import org.jetbrains.annotations.NotNull;

public class FishbowlRenderer implements BlockEntityRenderer<FishbowlBlockEntity> {

    public FishbowlRenderer(BlockEntityRendererProvider.Context pContext) {
    }

    @Override
    public void render(FishbowlBlockEntity tileEntityIn, float partialTicks, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Minecraft mc = Minecraft.getInstance();
        ItemRenderer ir = mc.getItemRenderer();

        if (!tileEntityIn.stack.isEmpty()) {
            if (tileEntityIn.stack.getItem() instanceof BlockItem blockItem){
                matrixStackIn.pushPose();
                float scale = 0.55f;
                matrixStackIn.translate(0.23, 0.315, 0.23);
                if (tileEntityIn.stack.is(FintyBlocks.HORNWORT.get().asItem()))
                    matrixStackIn.translate(0.15, 0, 0.15);
                matrixStackIn.scale(scale, scale, scale);
                BlockState state = blockItem.getBlock().defaultBlockState();
                Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
//            ir.renderStatic(tileEntityIn.stack, ItemDisplayContext.GROUND, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, mc.level, 0);
                matrixStackIn.popPose();
            }
        }
    }
}
