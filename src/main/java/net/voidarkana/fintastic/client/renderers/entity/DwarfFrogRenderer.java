package net.voidarkana.fintastic.client.renderers.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.client.FintasticLayers;
import net.voidarkana.fintastic.client.models.entity.base.FintasticModel;
import net.voidarkana.fintastic.client.models.entity.dwarf_frog.DwarfFrogModel;
import net.voidarkana.fintastic.client.models.entity.dwarf_frog.DwarfFrogTadpoleModel;
import net.voidarkana.fintastic.client.models.entity.sharkminnows.*;
import net.voidarkana.fintastic.common.entity.custom.DwarfFrog;

public class DwarfFrogRenderer<T extends DwarfFrog> extends MobRenderer<T, FintasticModel<T>> {

    private final DwarfFrogModel<T> adultModel;
    private final DwarfFrogTadpoleModel<T> babyModel;

    public DwarfFrogRenderer(EntityRendererProvider.Context context) {
        super(context, new DwarfFrogModel<>(context.bakeLayer(FintasticLayers.DWARF_FROG)), 0.3f);
        this.adultModel = new DwarfFrogModel<>(context.bakeLayer(FintasticLayers.DWARF_FROG));
        this.babyModel = new DwarfFrogTadpoleModel<>(context.bakeLayer(FintasticLayers.DWARF_FROG_TADPOLE));
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLightIn) {

        this.model = entity.isBaby() ? this.babyModel : this.adultModel;

        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        DwarfFrog.FrogVariant variant = DwarfFrog.FrogVariant.byId(entity.getVariant());
        return Fintastic.location("textures/entity/dwarf_frog/dwarf_frog_"
                + (entity.isBaby() ? "tadpole_"+ variant.getTadpoleName() : variant.getSerializedName() )+".png");
    }
}