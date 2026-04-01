package net.voidarkana.fintastic.common.item.custom.spawneggs;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.voidarkana.fintastic.common.entity.custom.Copepod;
import net.voidarkana.fintastic.common.entity.custom.FairyShrimp;

import java.util.List;
import java.util.function.Supplier;

public class CopepodSpawnEgg extends FishVariantSpawnEggItem {

    public CopepodSpawnEgg(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Properties props) {
        super(type, backgroundColor, highlightColor, props);
    }

    @Override
    public void applyEntityVariant(ItemStack itemstack, Entity entity) {
        if (entity instanceof Copepod fish && itemstack.hasTag())
            if (itemstack.getTag().contains(DATA_CREATURE))
                if (itemstack.getTag().getInt(DATA_CREATURE)!=-1){
                    int i = itemstack.getTag().getInt(DATA_CREATURE);
                    Copepod.CopepodVariant variant = Copepod.CopepodVariant.values()[i];
                    if (variant != null)
                        fish.setVariantSkin(variant.getID());
                }
    }


    public int getLength() {
        int length = Copepod.CopepodVariant.values().length-1;
        return length;
    }

    public void setTooltip(List<Component> tooltip, int i, ChatFormatting[] achatformatting) {
        Copepod.CopepodVariant variant = Copepod.CopepodVariant.values()[i];
        if (variant != null) {
            String common = "fintastic.copepod_common." + variant.getSerializedName();

            tooltip.add(Component.translatable(common).withStyle(achatformatting));
        }
    }

}
