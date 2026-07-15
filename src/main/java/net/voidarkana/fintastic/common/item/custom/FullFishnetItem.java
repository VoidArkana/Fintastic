package net.voidarkana.fintastic.common.item.custom;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.voidarkana.fintastic.common.item.FintyItems;

import java.util.function.Supplier;

public class FullFishnetItem extends DeferredSpawnEggItem {

    public FullFishnetItem(Supplier<? extends EntityType<? extends Mob>> type, Properties props) {
        super(type, 0xffffff, 0xffffff, props);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        context.getLevel().playSound(null, context.getClickedPos(), SoundEvents.FISHING_BOBBER_RETRIEVE, SoundSource.AMBIENT, 1, 1);
        assert context.getPlayer() != null;
        context.getPlayer().setItemInHand(context.getHand(), new ItemStack(FintyItems.FISHNET.get()));

        return super.useOn(context);
    }


}
