package net.voidarkana.fintastic.common.item.custom.spawneggs;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.voidarkana.fintastic.common.entity.custom.Catfish;
import net.voidarkana.fintastic.common.entity.custom.Featherback;
import net.voidarkana.fintastic.common.item.custom.FishSpawnEggItem;

import java.util.List;
import java.util.function.Supplier;

public class FeatherbackSpawnEgg extends FishSpawnEggItem {

    public FeatherbackSpawnEgg(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Properties props) {
        super(type, backgroundColor, highlightColor, props);
    }

    @Override
    public void applyEntityVariant(ItemStack itemstack, Entity entity) {
        if (entity instanceof Featherback fish && itemstack.hasTag())
            if (itemstack.getTag().contains(DATA_CREATURE))
                if (itemstack.getTag().getInt(DATA_CREATURE)!=-1){
                    int i = itemstack.getTag().getInt(DATA_CREATURE);
                    Featherback.FeatherbackVariant variant = Featherback.FeatherbackVariant.values()[i];
                    if (variant != null)
                        fish.setVariant(variant.getJoinedVariant());
                }
    }


    public int getLength() {
        int length = Featherback.FeatherbackVariant.values().length-1;
        return length;
    }

    public void setTooltip(List<Component> tooltip, int i, ChatFormatting[] achatformatting) {
        Featherback.FeatherbackVariant variant = Featherback.FeatherbackVariant.values()[i];
        if (variant != null) {
            String common = "fintastic.featherback_common." + variant.getSerializedName();

            tooltip.add(Component.translatable(common).withStyle(achatformatting));
        }
    }

}
