package net.voidarkana.fintastic.mixin.common;

import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.voidarkana.fintastic.common.item.FintyItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Drowned.class)
public class DrownedMixin extends Zombie {
    public DrownedMixin(EntityType<? extends Zombie> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
            method = {"finalizeSpawn"},
            at = @At(value = "HEAD")
    )
    protected void finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData spawnData, CallbackInfoReturnable<SpawnGroupData> cir) {
        if (this.getItemInHand(InteractionHand.MAIN_HAND).is(Items.FISHING_ROD) && level.getRandom().nextInt(0, 3)==0){
            this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(FintyItems.FISHING_HAT.get()));
            if (level.getRandom().nextInt(0, 3) == 0){
                this.setGuaranteedDrop(EquipmentSlot.HEAD);
            }
        }else if (this.getItemBySlot(EquipmentSlot.HEAD).isEmpty() && level.getRandom().nextFloat() < 0.06F) {
            this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(FintyItems.FISHING_HAT.get()));
            if (level.getRandom().nextInt(0, 3) == 0){
                this.setGuaranteedDrop(EquipmentSlot.HEAD);
            }
        }

    }

}
