package net.voidarkana.fintastic.common.entity.custom.ai;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;
import net.voidarkana.fintastic.common.entity.custom.base.BreedableWaterAnimal;

import javax.annotation.Nullable;
import java.util.List;

public class FishFollowParentGoal extends Goal {

    private final BreedableWaterAnimal animal;
    @Nullable
    private BreedableWaterAnimal parent;
    private final double speedModifier;
    private int timeToRecalcPath;

    public FishFollowParentGoal(BreedableWaterAnimal pAnimal, double pSpeedModifier) {
        this.animal = pAnimal;
        this.speedModifier = pSpeedModifier;
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean canUse() {
        if (this.animal.getAge() >= 0 || !this.animal.isInWaterOrBubble()) {
            return false;
        } else {
            List<? extends BreedableWaterAnimal> list = this.animal.level().getEntitiesOfClass(this.animal.getClass(), this.animal.getBoundingBox().inflate(8.0D, 4.0D, 8.0D));
            BreedableWaterAnimal animal = null;
            double d0 = Double.MAX_VALUE;

            for(BreedableWaterAnimal animal1 : list) {
                if (animal1.getAge() >= 0) {
                    double d1 = this.animal.distanceToSqr(animal1);
                    if (!(d1 > d0)) {
                        d0 = d1;
                        animal = animal1;
                    }
                }
            }

            if (animal == null) {
                return false;
            } else if (d0 < 9.0D) {
                return false;
            } else {
                this.parent = animal;
                return true;
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean canContinueToUse() {
        if (this.animal.getAge() >= 0) {
            return false;
        } else if (!this.parent.isAlive()) {
            return false;
        } else {
            double d0 = this.animal.distanceToSqr(this.parent);
            return !(d0 < 10.0D) && !(d0 > 256.0D);
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        this.timeToRecalcPath = 0;
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void stop() {
        this.parent = null;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = this.adjustedTickDelay(10);
            this.animal.getNavigation().moveTo(this.parent, this.speedModifier);
        }
    }
}
