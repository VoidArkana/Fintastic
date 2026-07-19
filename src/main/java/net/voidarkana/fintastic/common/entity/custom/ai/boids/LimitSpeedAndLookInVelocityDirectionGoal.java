package net.voidarkana.fintastic.common.entity.custom.ai.boids;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.entity.ai.goal.Goal;
import net.voidarkana.fintastic.common.entity.custom.base.VariantBoidingFish;

public class LimitSpeedAndLookInVelocityDirectionGoal extends Goal {
    private final VariantBoidingFish mob;
    private final float speed;
    //private final float maxSpeed;

    public LimitSpeedAndLookInVelocityDirectionGoal(VariantBoidingFish mob, float speed) {
        this.mob = mob;
        this.speed = speed;
        //this.maxSpeed = maxSpeed;
    }

    @Override
    public boolean canUse() {
        return this.mob.isInWaterOrBubble() && (mob.isFollower() || mob.hasFollowers());
    }

    @Override
    public void tick() {
        var velocity = mob.getDeltaMovement().normalize().scale(0.25).scale(speed);
        mob.setDeltaMovement(velocity);
        mob.lookAt(EntityAnchorArgument.Anchor.EYES, mob.position().add(velocity.x, velocity.y+(0.35), velocity.z));
    }

    @Override
    public void stop() {
        mob.lookAt(EntityAnchorArgument.Anchor.EYES, mob.position());
    }
}
