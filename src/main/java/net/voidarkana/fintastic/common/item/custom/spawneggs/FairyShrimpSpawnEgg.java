package net.voidarkana.fintastic.common.item.custom.spawneggs;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.voidarkana.fintastic.common.entity.custom.FairyShrimp;

import java.util.List;
import java.util.function.Supplier;

public class FairyShrimpSpawnEgg extends FishVariantSpawnEggItem {

    public FairyShrimpSpawnEgg(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Properties props) {
        super(type, backgroundColor, highlightColor, props);
    }

    @Override
    public void applyEntityVariant(ItemStack itemstack, Entity entity) {
        CompoundTag tag = getCreatureData(itemstack);
        if (entity instanceof FairyShrimp fish)
            if (tag.contains(DATA_CREATURE))
                if (tag.getInt(DATA_CREATURE)!=-1){
                    int i = tag.getInt(DATA_CREATURE);
                    FairyShrimp.FairyShrimpVariant variant = FairyShrimp.FairyShrimpVariant.values()[i];
                    if (variant != null)
                        fish.setVariant(variant.getID());
                }
    }


    public int getLength() {
        return FairyShrimp.FairyShrimpVariant.values().length-1;
    }

    public void setTooltip(List<Component> tooltip, int i, ChatFormatting[] achatformatting) {
        FairyShrimp.FairyShrimpVariant variant = FairyShrimp.FairyShrimpVariant.values()[i];
        if (variant != null) {
            String common = "fintastic.fairy_shrimp_common." + variant.getSerializedName();

            tooltip.add(Component.translatable(common).withStyle(achatformatting));
        }
    }

}
