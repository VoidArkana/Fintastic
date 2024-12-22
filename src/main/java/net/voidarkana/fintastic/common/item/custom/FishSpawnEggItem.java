package net.voidarkana.fintastic.common.item.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.voidarkana.fintastic.common.entity.custom.base.BreedableWaterAnimal;

import java.util.Optional;
import java.util.function.Supplier;

public class FishSpawnEggItem extends ForgeSpawnEggItem {

    public FishSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Properties props) {
        super(type, backgroundColor, highlightColor, props);
    }

    @Override
    public Optional<Mob> spawnOffspringFromSpawnEgg(Player pPlayer, Mob pMob, EntityType<? extends Mob> pEntityType, ServerLevel pServerLevel, Vec3 pPos, ItemStack pStack) {
        if (!this.spawnsEntity(pStack.getTag(), pEntityType)) {
            return Optional.empty();
        } else {
            Mob mob;
            if (pMob instanceof BreedableWaterAnimal) {
                mob = ((BreedableWaterAnimal)pMob).getBreedOffspring(pServerLevel, (BreedableWaterAnimal)pMob);
            } else {
                mob = pEntityType.create(pServerLevel);
            }

            if (mob == null) {
                return Optional.empty();
            } else {
                mob.setBaby(true);
                if (!mob.isBaby()) {
                    return Optional.empty();
                } else {
                    mob.moveTo(pPos.x(), pPos.y(), pPos.z(), 0.0F, 0.0F);
                    pServerLevel.addFreshEntityWithPassengers(mob);
                    if (pStack.hasCustomHoverName()) {
                        mob.setCustomName(pStack.getHoverName());
                    }

                    if (!pPlayer.getAbilities().instabuild) {
                        pStack.shrink(1);
                    }

                    return Optional.of(mob);
                }
            }
        }
    }
}
