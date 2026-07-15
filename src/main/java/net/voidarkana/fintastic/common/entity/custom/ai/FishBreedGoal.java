package net.voidarkana.fintastic.common.entity.custom.ai;
import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;
import net.voidarkana.fintastic.common.entity.custom.base.BreedableWaterAnimal;

public class FishBreedGoal extends Goal {
    private static final TargetingConditions PARTNER_TARGETING = TargetingConditions.forNonCombat().range(8.0D).ignoreLineOfSight();
    protected final BreedableWaterAnimal animal;
    private final Class<? extends BreedableWaterAnimal> partnerClass;
    protected final Level level;
    @Nullable
    protected BreedableWaterAnimal partner;
    private int loveTime;
    private final double speedModifier;

    public FishBreedGoal(BreedableWaterAnimal animal, double speedModifier) {
        this(animal, speedModifier, animal.getClass());
    }

    public FishBreedGoal(BreedableWaterAnimal animal, double speedModifier, Class<? extends BreedableWaterAnimal> partnerClass) {
        this.animal = animal;
        this.level = animal.level();
        this.partnerClass = partnerClass;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean canUse() {
        if (!this.animal.isInLove()) {
            return false;
        } else {
            this.partner = this.getFreePartner();
            return this.partner != null;
        }
    }

    public boolean canContinueToUse() {
        assert this.partner != null;
        return this.partner.isAlive() && this.partner.isInLove() && this.loveTime < 60;
    }

    public void stop() {
        this.partner = null;
        this.loveTime = 0;
    }

    public void tick() {
        assert this.partner != null;
        this.animal.getLookControl().setLookAt(this.partner, 10.0F, (float)this.animal.getMaxHeadXRot());
        this.animal.getNavigation().moveTo(this.partner, this.speedModifier);
        ++this.loveTime;
        if (this.loveTime >= this.adjustedTickDelay(60) && this.animal.distanceToSqr(this.partner) < 9.0D) {
            this.breed();
        }

    }

    @Nullable
    private BreedableWaterAnimal getFreePartner() {
        List<? extends BreedableWaterAnimal> list = this.level.getNearbyEntities(this.partnerClass, PARTNER_TARGETING, this.animal, this.animal.getBoundingBox().inflate(8.0D));
        double d0 = Double.MAX_VALUE;
        BreedableWaterAnimal animal = null;

        for(BreedableWaterAnimal animal1 : list) {
            if (this.animal.canMate(animal1) && this.animal.distanceToSqr(animal1) < d0) {
                animal = animal1;
                d0 = this.animal.distanceToSqr(animal1);
            }
        }

        return animal;
    }

    protected void breed() {
        this.animal.spawnChildFromBreeding((ServerLevel)this.level, this.partner);
    }
}