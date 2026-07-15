package net.voidarkana.fintastic.common.item.custom.spawneggs;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.voidarkana.fintastic.common.entity.custom.Minnow;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class MinnowSpawnEgg extends FishVariantSpawnEggItem {

    public MinnowSpawnEgg(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Properties props) {
        super(type, backgroundColor, highlightColor, props);
    }

    @Override
    public void applyEntityVariant(ItemStack itemstack, Entity entity) {
        if (entity instanceof Minnow minnow && itemstack.hasTag())
            if (itemstack.getTag().contains(DATA_CREATURE))
                if (itemstack.getTag().getInt(DATA_CREATURE)!=-1){
                    int i = itemstack.getTag().getInt(DATA_CREATURE);
                    Minnow.MinnowVariant variant = Minnow.MinnowVariant.values()[i];
                    if (variant != null){
                        minnow.setVariant(variant.getVariant());
                    }
                }
    }

    public void changeEntityVariant(ItemStack stack){
        int length = Minnow.MinnowVariant.values().length-1;
        int currentIndex = -1;
        int newIndex;

        if (stack.hasTag())
            if (stack.getTag().contains(DATA_CREATURE))
                currentIndex = stack.getTag().getInt(DATA_CREATURE);

        if (currentIndex == -1){
            newIndex = length;
        }else {
            newIndex = currentIndex-1;
        }
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt(DATA_CREATURE, newIndex);
        stack.setTag(tag);
    }

    @Override
    public void appendHoverText(ItemStack itemstack, @Nullable Level world, List<Component> tooltip, TooltipFlag flagIn) {
        ChatFormatting[] achatformatting = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.BLUE};
        ChatFormatting[] bchatformatting = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};

        tooltip.add(Component.translatable("fintastic.translatable.spawn_egg_instructions").withStyle(bchatformatting));

        if (itemstack.hasTag()){
            if (itemstack.getTag().contains(DATA_CREATURE)){
                if (itemstack.getTag().getInt(DATA_CREATURE)!=-1){
                    int i = itemstack.getTag().getInt(DATA_CREATURE);
                    Minnow.MinnowVariant variant = Minnow.MinnowVariant.values()[i];
                    if (variant != null) {
                        String common = "fintastic.minnow_common." + variant.getSerializedName();

                        tooltip.add(Component.translatable(common).withStyle(achatformatting));
                    }
                }else {
                    tooltip.add(Component.translatable("fintastic.translatable.random_variant").withStyle(achatformatting));
                }
            }
        }
        else
            tooltip.add(Component.translatable("fintastic.translatable.random_variant").withStyle(achatformatting));


    }

}
