package net.voidarkana.fintastic.common.blockentity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemHandlerHelper;
import net.voidarkana.fintastic.common.blockentity.FintyBlockEntities;
import net.voidarkana.fintastic.util.FintyTags;
import org.jetbrains.annotations.NotNull;

public class FishbowlBlockEntity extends BlockEntityBase {

    public FishbowlBlockEntity(BlockPos pPos, BlockState pBlockState) {
        this(FintyBlockEntities.FISHBOWL_ENTITY.get(), pPos, pBlockState);
    }

    public FishbowlBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public ItemStack stack = ItemStack.EMPTY;

    @Override
    public void onDestroyed(BlockState state, BlockPos pos) {
        if (!stack.isEmpty())
            Containers.dropItemStack(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack);
    }

    @Override
    public InteractionResult onActivated(BlockState state, BlockPos pos, Player player, InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND && level != null) {
            ItemStack itemInHand = player.getItemInHand(hand);

            if (itemInHand.getItem() instanceof MobBucketItem){
                player.displayClientMessage(Component.translatable("message.fintastic.unethical_fishbowl"), true);
            }

            if (itemInHand.isEmpty() && !stack.isEmpty()) {
                ItemHandlerHelper.giveItemToPlayer(player, stack);
                stack = ItemStack.EMPTY;
                if (!level.isClientSide) sync();
                return InteractionResult.SUCCESS;
            } else if (itemInHand.is(FintyTags.Items.FISHBOWL_PLANTS) && stack.isEmpty()) {
                stack = player.isCreative() ? itemInHand.copyWithCount(1) : itemInHand.split(1);
                if (!level.isClientSide) sync();
                return InteractionResult.SUCCESS;
            } else if (itemInHand.is(FintyTags.Items.FISHBOWL_PLANTS) && !stack.isEmpty()) {
                if (player.isCreative()){
                    stack = itemInHand.copyWithCount(1);
                }else {
                    ItemStack oldstack = stack.copy();
                    stack = itemInHand.split(1);
                    ItemHandlerHelper.giveItemToPlayer(player, oldstack);
                }
                if (!level.isClientSide) sync();
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        stack = ItemStack.of(tag.getCompound("stack"));
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.put("stack", stack.save(new CompoundTag()));
    }
}
