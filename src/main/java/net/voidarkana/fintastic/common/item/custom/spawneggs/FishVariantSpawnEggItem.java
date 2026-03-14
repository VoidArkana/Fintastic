package net.voidarkana.fintastic.common.item.custom.spawneggs;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class FishVariantSpawnEggItem extends FishSpawnEggItem{

    public static final String DATA_CREATURE = "CreatureData";

    public FishVariantSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Properties props) {
        super(type, backgroundColor, highlightColor, props);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        BlockHitResult blockhitresult = getPlayerPOVHitResult(pLevel, pPlayer, ClipContext.Fluid.SOURCE_ONLY);
        if (blockhitresult.getType() != HitResult.Type.BLOCK) {
            if (pPlayer.isCrouching()){
                this.changeEntityVariant(itemstack);
                return InteractionResultHolder.success(itemstack);
            }

            return InteractionResultHolder.pass(itemstack);
        } else
            return super.use(pLevel, pPlayer, pHand);
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
                    setTooltip(tooltip, i, achatformatting);
                }else{
                    tooltip.add(Component.translatable("fintastic.translatable.random_variant").withStyle(achatformatting));
                }
            }
        }else {
            tooltip.add(Component.translatable("fintastic.translatable.random_variant").withStyle(achatformatting));
        }
    }

    public void changeEntityVariant(ItemStack stack){
        int length = getLength();
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

    public int getLength(){
        return 0;
    }

    public void setTooltip(List<Component> tooltip, int i, ChatFormatting[] achatformatting) {

    }
}
