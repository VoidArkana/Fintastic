package net.voidarkana.fintastic.mixin.common;

import net.minecraft.core.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.voidarkana.fintastic.common.entity.FintyEntities;
import net.voidarkana.fintastic.common.entity.custom.FintasticCod;
import net.voidarkana.fintastic.common.entity.custom.FintasticSalmon;
import net.voidarkana.fintastic.util.FintyCommonConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractSchoolingFish.class)
public abstract class VanillaFishMixin extends AbstractFish {
    public VanillaFishMixin(EntityType<? extends AbstractFish> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
            method = {"finalizeSpawn"},
            at = @At(value = "HEAD")
    )
    protected void finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData spawnData, CallbackInfoReturnable<SpawnGroupData> cir) {
        var self = (AbstractFish) this;

        if (reason != MobSpawnType.SPAWN_EGG){
            if (FintyCommonConfig.REPLACE_VANILLA_COD.get() && self.getType() == EntityType.COD){
                FintasticCod cod = FintyEntities.COD.get().create(level.getLevel());

                if (cod != null){
                    if (FintyCommonConfig.ALLOW_FINTASTIC_COD.get() && this.getRandom().nextBoolean()){
                        BlockPos pos = self.blockPosition();
                        cod.moveTo(pos.getX(), pos.getY(), pos.getZ(), random.nextInt(360), 0.0F);
                        cod.finalizeSpawn(level,difficulty,reason,null);
                        level.addFreshEntity(cod);
                    }
                    this.discard();
                }
            }

            if (FintyCommonConfig.REPLACE_VANILLA_SALMON.get() && self.getType() == EntityType.SALMON){
                FintasticSalmon salmon = FintyEntities.SALMON.get().create(level.getLevel());

                if (salmon != null){
                    if (FintyCommonConfig.ALLOW_FINTASTIC_SALMON.get() && this.getRandom().nextBoolean()){
                        BlockPos pos = self.blockPosition();
                        salmon.moveTo(pos.getX(), pos.getY(), pos.getZ(), random.nextInt(360), 0.0F);
                        salmon.finalizeSpawn(level,difficulty,reason,null);
                        level.addFreshEntity(salmon);
                    }
                    this.discard();
                }
            }
        }
    }
}
