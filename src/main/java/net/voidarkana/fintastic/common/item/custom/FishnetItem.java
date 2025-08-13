package net.voidarkana.fintastic.common.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.voidarkana.fintastic.common.item.YAFMItems;
import net.voidarkana.fintastic.util.YAFMTags;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class FishnetItem extends Item {

    public static final String DATA_CREATURE = "CreatureData";

    public FishnetItem(Properties properties) {
        super(properties);
    }

    static Random random = new Random();

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        Level level = player.level();
        if (containsEntity(stack)) return InteractionResult.PASS;

        if (!target.getPassengers().isEmpty()) target.ejectPassengers();

        if ((!target.getType().is(YAFMTags.EntityType.FISHNET_BLACKLIST) && target instanceof WaterAnimal)
                || target.getType().is(YAFMTags.EntityType.FISHNET_ADDITIONS)) {

            if (!level.isClientSide) {

                ItemStack stack1 = player.getItemInHand(hand);

                boolean more = stack.getCount() > 1;

                if (more) {
                    stack1 = new ItemStack(YAFMItems.FISHNET.get());

                    if (!player.getAbilities().instabuild)
                        stack.shrink(1);
                }

                CompoundTag targetTag = target.serializeNBT();
                targetTag.putString("OwnerName", player.getName().getString());
                CompoundTag tag = stack1.getOrCreateTag();
                tag.put(DATA_CREATURE, targetTag);
                stack1.setTag(tag);

                ItemStack stack2 = ItemUtils.createFilledResult(stack, player, stack1, false);

//                if (more) {
//                    if (!player.getInventory().add(stack2)) player.drop(stack2, true);
//                    else player.setItemInHand(hand, stack2);
//                }

                player.setItemInHand(hand, stack2);

                target.discard();

                level.playSound(null, player.blockPosition(), SoundEvents.FISHING_BOBBER_RETRIEVE, SoundSource.AMBIENT, 1, 1);
            }

            if (level.isClientSide) {
                double width = target.getBbWidth();
                for (int i = 0; i <= Math.floor(width) * 25; ++i) {
                    double x = target.getX();
                    double y = target.getY();
                    double z = target.getZ();
                    for (int j = 0; j < 8; j++) {
                        level.addParticle(ParticleTypes.BUBBLE, x + random.nextFloat() / 1.5F, y + 0.25F + (random.nextFloat() / 2.0F), z + random.nextFloat() / 1.5F, 5.0E-5D, 5.0E-5D, 5.0E-5D);
                        level.addParticle(ParticleTypes.BUBBLE, x + random.nextFloat() / -1.5F, y + 0.25F + (random.nextFloat() / 2.0F), z + random.nextFloat() / -1.5F, 5.0E-5D, 5.0E-5D, 5.0E-5D);
                        level.addParticle(ParticleTypes.BUBBLE, x + random.nextFloat() / -1.5F, y + 0.25F + (random.nextFloat() / 2.0F), z + random.nextFloat() / 1.5F, 5.0E-5D, 5.0E-5D, 5.0E-5D);
                        level.addParticle(ParticleTypes.BUBBLE, x + random.nextFloat() / 1.5F, y + 0.25F + (random.nextFloat() / 2.0F), z + random.nextFloat() / -1.5F, 5.0E-5D, 5.0E-5D, 5.0E-5D);
                    }
                }

            }
            return InteractionResult.SUCCESS;

        }

        return InteractionResult.sidedSuccess(true);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        BlockHitResult rt = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        ItemStack stack = player.getItemInHand(hand);
        if (rt.getType() == HitResult.Type.MISS) return InteractionResultHolder.pass(stack);
        BlockPos pos = rt.getBlockPos();
        if (!(level.getBlockState(pos).getBlock() instanceof LiquidBlock)) return InteractionResultHolder.success(stack);
        return new InteractionResultHolder<>(releaseEntity(level, player, stack, pos, rt.getDirection()), stack);
    }

    @Override
    public Component getName(ItemStack stack) {
        MutableComponent name = (MutableComponent) super.getName(stack);
        Component name2;

        if (containsEntity(stack)) {
            CompoundTag tag = stack.getTag().getCompound(DATA_CREATURE);

            if (tag.contains("CustomName")) {
                name2 = Component.Serializer.fromJson(tag.getString("CustomName"));
            }
            else {
                name2 = EntityType.byString(tag.getString("id")).orElse(null).getDescription();
            }

            name.append(" of ").append(name2);
        }
        return name;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flagIn) {
        if (containsEntity(stack)) {
            CompoundTag tag = stack.getTag().getCompound(DATA_CREATURE);
            Component name;

            name = EntityType.byString(tag.getString("id")).orElse(null).getDescription();
            tooltip.add(name.copy().withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        }else {
            ChatFormatting[] achatformatting = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};

            MutableComponent fishnetDesc = Component.translatable("fintastic.translatable.fishnet_description");
            fishnetDesc.withStyle(achatformatting);

            tooltip.add(fishnetDesc);
        }
    }

//    @Override
//    public boolean isFoil(ItemStack stack) {
//        return containsEntity(stack);
//    }


    public static boolean containsEntity(ItemStack stack) {
        return stack.getTag() != null && stack.hasTag() && stack.getTag().contains(DATA_CREATURE);
    }

    private static InteractionResult releaseEntity(Level level, Player player, ItemStack stack, BlockPos pos, Direction direction) {
        if (!containsEntity(stack)) return InteractionResult.PASS;

        CompoundTag tag = stack.getTag().getCompound(DATA_CREATURE);
        EntityType<?> type = EntityType.byString(tag.getString("id")).orElse(null);
        LivingEntity entity;

        if (type == null || (entity = (LivingEntity) type.create(level)) == null) {
            return InteractionResult.FAIL;
        }

        EntityDimensions size = entity.getDimensions(entity.getPose());
        if (!level.getBlockState(pos).getCollisionShape(level, pos).isEmpty())
            pos = pos.relative(direction, (int) (direction.getAxis().isHorizontal() ? size.width : 1));

        entity.absMoveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        AABB aabb = entity.getBoundingBox();

        if (!level.noCollision(entity, new AABB(aabb.minX, entity.getEyeY() - 0.35, aabb.minZ, aabb.maxX, entity.getEyeY() + 1.0, aabb.maxZ))) {
            return InteractionResult.FAIL;
        }

        if (!level.isClientSide) {
            UUID id = entity.getUUID();
            entity.deserializeNBT(tag);
            entity.setUUID(id);
            entity.moveTo(pos.getX(), pos.getY() + direction.getStepY() + 1.0, pos.getZ(), player.getYRot(), 0f);

            if (stack.hasCustomHoverName()) entity.setCustomName(stack.getHoverName());

            if (entity instanceof Bucketable fish){
                fish.setFromBucket(true);
            }

            if (!player.getAbilities().instabuild)
                stack.removeTagKey(DATA_CREATURE);

            level.addFreshEntity(entity);
            level.playSound(null, entity.blockPosition(), SoundEvents.FISHING_BOBBER_THROW, SoundSource.AMBIENT, 1, 1);

        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        if (!(world instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        } else if (context.getItemInHand().hasTag()) {
            ItemStack itemstack = context.getItemInHand();
            BlockPos blockpos = context.getClickedPos();
            Direction direction = context.getClickedFace();
            BlockState blockstate = world.getBlockState(blockpos);

            BlockPos blockpos1;
            if (blockstate.getCollisionShape(world, blockpos).isEmpty()) {
                blockpos1 = blockpos;
            } else {
                blockpos1 = blockpos.relative(direction);
            }

            ItemStack stack = context.getItemInHand();
            CompoundTag tag = stack.getTag().getCompound(DATA_CREATURE);
            EntityType<?> type = EntityType.byString(tag.getString("id")).orElse(null);
            LivingEntity entity = (LivingEntity) type.create(context.getLevel());
            if (entity == null) return InteractionResult.FAIL;

            UUID id = entity.getUUID();
            entity.deserializeNBT(tag);
            entity.setUUID(id);

            entity.moveTo(blockpos1.getX() + 0.5, blockpos1.getY(), blockpos1.getZ() + 0.5, context.getPlayer().getYRot(), 0f);

            if (stack.hasCustomHoverName()) entity.setCustomName(stack.getHoverName());

            if (!context.getPlayer().getAbilities().instabuild)
                stack.removeTagKey(DATA_CREATURE);

            if (entity instanceof Bucketable fish){
                fish.setFromBucket(true);
            }

            if (context.getLevel().addFreshEntity(entity) && !context.getPlayer().getAbilities().instabuild) {
                itemstack.shrink(1);
            }

            context.getLevel().playSound(null, entity.blockPosition(), SoundEvents.FISHING_BOBBER_THROW, SoundSource.AMBIENT, 1, 1);

            if (!context.getPlayer().getAbilities().instabuild)
                context.getPlayer().setItemInHand(context.getHand(), new ItemStack(YAFMItems.FISHNET.get()));

            return InteractionResult.CONSUME;
        }
        else {
            return super.useOn(context);
        }
    }
}
