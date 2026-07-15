package net.voidarkana.fintastic.common.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class FishFeedItem extends Item {

    private final int quality;

    public FishFeedItem(Properties properties, int quality) {
        super(properties);
        this.quality = quality;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, context, tooltipComponents, isAdvanced);

        ChatFormatting[] achatformatting = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.AQUA};
        ChatFormatting[] bchatformatting = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};

        MutableComponent translatable = Component.translatable("fintastic.translatable.shift");
        translatable.withStyle(achatformatting);

        MutableComponent fishfeedDesc = Component.translatable("fintastic.translatable.fishfeed." + this.quality);
        fishfeedDesc.withStyle(bchatformatting);

        if (!Screen.hasShiftDown()){
            tooltipComponents.add(translatable);
        }else {
            tooltipComponents.add(fishfeedDesc);
        }

    }

}
