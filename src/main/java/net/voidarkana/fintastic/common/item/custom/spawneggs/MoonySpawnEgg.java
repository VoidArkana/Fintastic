package net.voidarkana.fintastic.common.item.custom.spawneggs;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.voidarkana.fintastic.common.entity.custom.Moony;

import java.util.List;
import java.util.function.Supplier;

public class MoonySpawnEgg extends FishVariantSpawnEggItem {

    public MoonySpawnEgg(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Properties props) {
        super(type, backgroundColor, highlightColor, props);
    }

    @Override
    public void applyEntityVariant(ItemStack itemstack, Entity entity) {
        CompoundTag tag = getCreatureData(itemstack);
        if (entity instanceof Moony fish)
            if (tag.contains(DATA_CREATURE))
                if (tag.getInt(DATA_CREATURE)!=-1){
                    int i = tag.getInt(DATA_CREATURE);
                    Moony.MoonyVariant variant = Moony.MoonyVariant.values()[i];
                    if (variant != null)
                        fish.setVariant(variant.getJoinedVariant());
                }
    }


    public int getLength() {
        return Moony.MoonyVariant.values().length-1;
    }

    public void setTooltip(List<Component> tooltip, int i, ChatFormatting[] achatformatting) {
        Moony.MoonyVariant variant = Moony.MoonyVariant.values()[i];
        if (variant != null) {
            String common = "fintastic.moony_common." + variant.getSerializedName();

            tooltip.add(Component.translatable(common).withStyle(achatformatting));
        }
    }

}
