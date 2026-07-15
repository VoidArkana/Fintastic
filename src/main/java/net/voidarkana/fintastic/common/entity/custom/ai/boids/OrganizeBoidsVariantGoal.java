package net.voidarkana.fintastic.common.entity.custom.ai.boids;

import com.mojang.datafixers.DataFixUtils;
import net.minecraft.world.entity.ai.goal.Goal;
import net.voidarkana.fintastic.common.entity.custom.base.VariantBoidingFish;

import java.util.List;
import java.util.function.Predicate;

public class OrganizeBoidsVariantGoal extends Goal {

    private final VariantBoidingFish mob;
    private int nextStartTick;

    public OrganizeBoidsVariantGoal(VariantBoidingFish fish) {
        this.mob = fish;
        this.nextStartTick = this.nextStartTick(fish);
    }

    protected int nextStartTick(VariantBoidingFish taskOwner) {
        return reducedTickDelay(200 + taskOwner.getRandom().nextInt(200) % 20);
    }

    public boolean canUse() {
        if (this.mob.hasFollowers()) {
            return false;
        } else if (this.mob.isFollower()) {
            return true;
        } else if (this.nextStartTick > 0) {
            --this.nextStartTick;
            return false;
        } else {
            this.nextStartTick = this.nextStartTick(this.mob);
            Predicate<VariantBoidingFish> predicate = (fish) -> fish.canBeFollowed() || !fish.isFollower();
            List<? extends VariantBoidingFish> list = this.mob.level().getEntitiesOfClass(this.mob.getClass(), this.mob.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), predicate);
            VariantBoidingFish abstractschoolingfish = DataFixUtils.orElse(list.stream().filter(VariantBoidingFish::canBeFollowed).findAny(), this.mob);
            abstractschoolingfish.addFollowers(list.stream().filter((fish) -> !fish.isFollower()));
            return this.mob.isFollower();
        }
    }

    public boolean canContinueToUse() {
        return this.mob.isFollower() && this.mob.inRangeOfLeader();
    }

    public void start() {
    }

    public void stop() {
        this.mob.stopFollowing();
    }
}
