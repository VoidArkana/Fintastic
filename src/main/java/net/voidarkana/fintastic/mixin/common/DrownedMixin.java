package net.voidarkana.fintastic.mixin.common;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.voidarkana.fintastic.common.item.YAFMItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Drowned.class)
public class DrownedMixin extends Zombie {
    public DrownedMixin(EntityType<? extends Zombie> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(
            method = {"finalizeSpawn"},
            at = @At(value = "HEAD")
    )
    protected void finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, SpawnGroupData pSpawnData, CompoundTag pDataTag, CallbackInfoReturnable<SpawnGroupData> cir) {
        if (this.getItemBySlot(EquipmentSlot.HEAD).isEmpty() && pLevel.getRandom().nextFloat() < 0.06F) {
            this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(YAFMItems.FISHING_HAT.get()));
            if (pLevel.getRandom().nextInt(0, 3) == 0){
                this.setGuaranteedDrop(EquipmentSlot.HEAD);
            }
        }

    }

}
