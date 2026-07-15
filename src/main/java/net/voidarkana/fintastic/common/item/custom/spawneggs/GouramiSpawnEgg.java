package net.voidarkana.fintastic.common.item.custom.spawneggs;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.voidarkana.fintastic.common.entity.custom.Gourami;

import java.util.List;
import java.util.function.Supplier;

public class GouramiSpawnEgg extends FishVariantSpawnEggItem {

    public GouramiSpawnEgg(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Properties props) {
        super(type, backgroundColor, highlightColor, props);
    }

    @Override
    public void applyEntityVariant(ItemStack itemstack, Entity entity) {
        CompoundTag tag = getCreatureData(itemstack);
        if (entity instanceof Gourami fish)
            if (tag.contains(DATA_CREATURE))
                if (tag.getInt(DATA_CREATURE)!=-1){
                    int i = tag.getInt(DATA_CREATURE);
                    Gourami.GouramiVariant variant = Gourami.GouramiVariant.values()[i];
                    if (variant != null){
                        fish.setVariantModel(variant.getModel());
                        fish.setVariantSkin(variant.getSkin());
                    }
                }
    }

    public int getLength() {
        return Gourami.GouramiVariant.values().length-1;
    }

    public void setTooltip(List<Component> tooltip, int i, ChatFormatting[] achatformatting) {
        Gourami.GouramiVariant variant = Gourami.GouramiVariant.values()[i];
        if (variant != null) {
            String common = "fintastic.gourami_common." + variant.getJoinedVariant();

            tooltip.add(Component.translatable(common).withStyle(achatformatting));
        }
    }

}
