package net.voidarkana.fintastic.common.item.custom.spawneggs;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.voidarkana.fintastic.common.entity.custom.Catfish;

import java.util.List;
import java.util.function.Supplier;

public class CatfishSpawnEgg extends FishVariantSpawnEggItem {

    public CatfishSpawnEgg(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Properties props) {
        super(type, backgroundColor, highlightColor, props);
    }

    @Override
    public void applyEntityVariant(ItemStack itemstack, Entity entity) {
        CompoundTag tag = getCreatureData(itemstack);
        if (entity instanceof Catfish catfish)
            if (tag.contains(DATA_CREATURE))
                if (tag.getInt(DATA_CREATURE)!=-1){
                    int i = tag.getInt(DATA_CREATURE);
                    Catfish.CatfishVariant variant = Catfish.CatfishVariant.values()[i];
                    if (variant != null)
                        catfish.setVariant(variant.getJoinedVariant());
                }
    }


    public int getLength() {
        int length = Catfish.CatfishVariant.values().length-1;
        return length;
    }

    public void setTooltip(List<Component> tooltip, int i, ChatFormatting[] achatformatting) {
        Catfish.CatfishVariant variant = Catfish.CatfishVariant.values()[i];
        if (variant != null) {
            String common = "fintastic.catfish_common." + variant.getSerializedName();

            tooltip.add(Component.translatable(common).withStyle(achatformatting));
        }
    }

}
