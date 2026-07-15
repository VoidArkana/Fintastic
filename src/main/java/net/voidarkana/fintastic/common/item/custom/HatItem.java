package net.voidarkana.fintastic.common.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.voidarkana.fintastic.Fintastic;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class HatItem extends ArmorItem {
    public HatItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, world, entity, slotId, isSelected);

        if (entity instanceof Player player && player.getItemBySlot(EquipmentSlot.HEAD) == stack) {
            if (player.getItemInHand(InteractionHand.MAIN_HAND).is(Items.FISHING_ROD))
                player.addEffect(new MobEffectInstance(MobEffects.LUCK, 10, 0, false, false));
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept((IClientItemExtensions) Fintastic.PROXY.getArmorRenderProperties());
    }

    @Nullable
    @Override
    public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {

        if (this.material == YAFMArmorMaterials.HAT) {
            return Fintastic.location("textures/models/armor/fishing_hat.png");
        }

        return super.getArmorTexture(stack, entity, slot, layer, innerModel);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {

        ChatFormatting[] achatformatting = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};

        MutableComponent fishnetDesc = Component.translatable("item.fintastic.fishing_hat.desc");
        fishnetDesc.withStyle(achatformatting);

        tooltip.add(fishnetDesc);

    }
}
