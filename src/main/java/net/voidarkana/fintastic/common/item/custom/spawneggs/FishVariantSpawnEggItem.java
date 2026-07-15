package net.voidarkana.fintastic.common.item.custom.spawneggs;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.List;
import java.util.function.Supplier;

public class FishVariantSpawnEggItem extends FishSpawnEggItem{

    public static final String DATA_CREATURE = "CreatureData";

    public FishVariantSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Properties props) {
        super(type, backgroundColor, highlightColor, props);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        BlockHitResult blockhitresult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        if (blockhitresult.getType() != HitResult.Type.BLOCK) {
            if (player.isCrouching()){
                this.changeEntityVariant(itemstack);
                return InteractionResultHolder.success(itemstack);
            }

            return InteractionResultHolder.pass(itemstack);
        } else
            return super.use(level, player, hand);
    }
    @Override
    public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        ChatFormatting[] achatformatting = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.BLUE};
        ChatFormatting[] bchatformatting = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};

        tooltip.add(Component.translatable("fintastic.translatable.spawn_egg_instructions").withStyle(bchatformatting));

        CompoundTag tag = getCreatureData(itemstack);
        if (tag.contains(DATA_CREATURE) && tag.getInt(DATA_CREATURE)!=-1){
            int i = tag.getInt(DATA_CREATURE);
            setTooltip(tooltip, i, achatformatting);
        }else {
            tooltip.add(Component.translatable("fintastic.translatable.random_variant").withStyle(achatformatting));
        }
    }

    public void changeEntityVariant(ItemStack stack){
        int length = getLength();
        int currentIndex = -1;
        int newIndex;

        CompoundTag tag = getCreatureData(stack);
        if (tag.contains(DATA_CREATURE))
            currentIndex = tag.getInt(DATA_CREATURE);

        if (currentIndex == -1){
            newIndex = length;
        }else {
            newIndex = currentIndex-1;
        }
        CustomData.update(DataComponents.CUSTOM_DATA, stack, t -> t.putInt(DATA_CREATURE, newIndex));
    }

    protected static CompoundTag getCreatureData(ItemStack stack){
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
    }

    public int getLength(){
        return 0;
    }

    public void setTooltip(List<Component> tooltip, int i, ChatFormatting[] achatformatting) {

    }
}
