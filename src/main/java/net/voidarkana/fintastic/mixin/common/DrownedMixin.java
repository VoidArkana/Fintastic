package net.voidarkana.fintastic.mixin.common;

import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.voidarkana.fintastic.common.item.YAFMItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Drowned.class)
public class DrownedMixin extends Zombie implements RangedAttackMob {
    public DrownedMixin(EntityType<? extends Zombie> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity) {
    }

    @Inject(
            method = {"populateDefaultEquipmentSlots"},
            cancellable = true,
            at = @At(value = "HEAD")
    )

    protected void addHatToSlots(RandomSource pRandom, DifficultyInstance pDifficulty, CallbackInfo ci) {
        if ((double)pRandom.nextFloat() > 0.9D) {
            this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(YAFMItems.FISHING_HAT.get()));
        }
    }

}
