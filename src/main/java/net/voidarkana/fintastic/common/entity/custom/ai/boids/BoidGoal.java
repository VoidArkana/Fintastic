package net.voidarkana.fintastic.common.entity.custom.ai.boids;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;
import net.voidarkana.fintastic.common.entity.custom.base.VariantBoidingFish;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.function.Predicate;

public class BoidGoal extends Goal {
    public static final Logger LOGGER = LogManager.getLogger();

    public final float separationInfluence;
    public final float separationRange;
    public final float alignmentInfluence;
    public final float cohesionInfluence;
    private final VariantBoidingFish mob;
    private int timeToFindNearbyEntities;
    List<? extends VariantBoidingFish> nearbyMobs;
    private boolean enabled = true;

    public BoidGoal(VariantBoidingFish mob, float separationInfluence, float separationRange, float alignmentInfluence, float cohesionInfluence) {
        timeToFindNearbyEntities = 0;

        this.mob = mob;
        this.separationInfluence = separationInfluence;
        this.separationRange = separationRange;
        this.alignmentInfluence = alignmentInfluence;
        this.cohesionInfluence = cohesionInfluence;
    }

    @Override
    public boolean canUse() {
        return this.mob.isInWater() && mob.isFollower(); //(mob.isFollower() || (mob.hasFollowers() && mob.canBeFollowed()));
    }

    public void tick() {
        if (!enabled) {
            return;
        }

        if (--this.timeToFindNearbyEntities <= 0) {
            this.timeToFindNearbyEntities = this.adjustedTickDelay(40);
            nearbyMobs = getNearbyEntitiesOfSameSchool(mob);
        } else {
            nearbyMobs.removeIf(LivingEntity::isDeadOrDying);
        }

        if (nearbyMobs.isEmpty()) {
            LOGGER.warn("No nearby entities found. There should always be at least the entity itself. Will disable behavior for this entity instead of crash for compatibility reasons");
            enabled = false;
        }

        if (enabled){
            //mob.addDeltaMovement(random());
            mob.addDeltaMovement(cohesion());
            mob.addDeltaMovement(alignment());
            mob.addDeltaMovement(separation());
        }


    }

    public static List<? extends VariantBoidingFish> getNearbyEntitiesOfSameSchool(VariantBoidingFish pMob) {
        Predicate<VariantBoidingFish> predicate =
                (pVariantBoidingFish) -> {
                    if (pVariantBoidingFish.isFollower() && pMob.isFollower()) {
                        return pVariantBoidingFish.leader == pMob.leader;
                    }else if (pVariantBoidingFish.isFollower() && pMob.canBeFollowed()){
                        return pVariantBoidingFish.leader == pMob;
                    }else if (pVariantBoidingFish.canBeFollowed() && pMob.isFollower()){
                        return pMob.leader == pVariantBoidingFish;
                    }
                    return false;
                };
//                        (pVariantBoidingFish.isFollower() && ((pVariantBoidingFish.leader == mob.leader && pVariantBoidingFish.isFollower() && Objects.requireNonNull(pVariantBoidingFish.leader).canBeFollowed())
//                                || (pVariantBoidingFish.leader == mob && mob.canBeFollowed()) ))
//                || (mob.isFollower() && ((pVariantBoidingFish.leader == mob.leader && mob.isFollower() && Objects.requireNonNull(pVariantBoidingFish.leader).canBeFollowed())
//                                || (mob.leader == pVariantBoidingFish && pVariantBoidingFish.canBeFollowed()) ));

        return pMob.level().getEntitiesOfClass(pMob.getClass(), pMob.getBoundingBox().inflate(4.0, 4.0, 4.0), predicate);
    }

    public Vec3 random() {
        var velocity = mob.getDeltaMovement();

        if (Mth.abs((float) velocity.x) < 0.1 && Mth.abs((float) velocity.z) < 0.1)
            return new Vec3(randomSign() * 0.1, 0, randomSign() * 0.1);

        return Vec3.ZERO;
    }

    public int randomSign() {
        var isNegative = mob.getRandom().nextBoolean();

        if (isNegative) {
            return -1;
        }

        return 1;
    }

    public Vec3 separation() {
        var c = Vec3.ZERO;

        for (Mob nearbyMob : nearbyMobs) {
            if ((nearbyMob.position().subtract(mob.position()).length()) < separationRange) {
                c = c.subtract(nearbyMob.position().subtract(mob.position()));
            }
        }

        return c.scale(separationInfluence);
    }

    public Vec3 alignment() {
        var c = Vec3.ZERO;

        for (Mob nearbyMob : nearbyMobs) {
            c = c.add(nearbyMob.getDeltaMovement());
        }

        c = c.scale(1f / nearbyMobs.size());
        c = c.subtract(mob.getDeltaMovement());
        return c.scale(alignmentInfluence);
    }

    public Vec3 cohesion() {
        var c = Vec3.ZERO;

        for (Mob nearbyMob : nearbyMobs) {
            c = c.add(nearbyMob.position());
        }

        c = c.scale(1f / nearbyMobs.size());

        c = c.subtract(mob.position());
        return c.scale(cohesionInfluence);
    }
}
