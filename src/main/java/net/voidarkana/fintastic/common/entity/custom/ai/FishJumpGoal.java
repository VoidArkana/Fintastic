package net.voidarkana.fintastic.common.entity.custom.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.JumpGoal;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.voidarkana.fintastic.common.entity.custom.base.BucketableFishEntity;

public class FishJumpGoal extends JumpGoal {
    private static final int[] STEPS_TO_CHECK = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
    private final BucketableFishEntity dolphin;
    private final int interval;
    private boolean breached;

    public FishJumpGoal(BucketableFishEntity dolphin, int interval) {
        this.dolphin = dolphin;
        this.interval = reducedTickDelay(interval);
    }

    public boolean canUse() {
        if (this.dolphin.getRandom().nextInt(this.interval) != 0) {
            return false;
        } else {
            Direction direction = this.dolphin.getMotionDirection();
            int i = direction.getStepX();
            int j = direction.getStepZ();
            BlockPos blockpos = this.dolphin.blockPosition();

            for(int k : STEPS_TO_CHECK) {
                if (!this.waterIsClear(blockpos, i, j, k) || !this.surfaceIsClear(blockpos, i, j, k)) {
                    return false;
                }
            }

            return true;
        }
    }

    private boolean waterIsClear(BlockPos pos, int dx, int dz, int scale) {
        BlockPos blockpos = pos.offset(dx * scale, 0, dz * scale);
        return this.dolphin.level().getFluidState(blockpos).is(FluidTags.WATER) && !this.dolphin.level().getBlockState(blockpos).blocksMotion();
    }

    private boolean surfaceIsClear(BlockPos pos, int dx, int dz, int scale) {
        return this.dolphin.level().getBlockState(pos.offset(dx * scale, 1, dz * scale)).isAir() && this.dolphin.level().getBlockState(pos.offset(dx * scale, 2, dz * scale)).isAir();
    }

    public boolean canContinueToUse() {
        double d0 = this.dolphin.getDeltaMovement().y;
        return (!(d0 * d0 < (double)0.03F) || this.dolphin.getXRot() == 0.0F || !(Math.abs(this.dolphin.getXRot()) < 10.0F) || !this.dolphin.isInWater()) && !this.dolphin.onGround();
    }

    public boolean isInterruptable() {
        return false;
    }

    public void start() {
        Direction direction = this.dolphin.getMotionDirection();
        this.dolphin.setDeltaMovement(this.dolphin.getDeltaMovement().add((double)direction.getStepX() * 0.6D, 0.7D, (double)direction.getStepZ() * 0.6D));
        this.dolphin.getNavigation().stop();
    }

    public void stop() {
        this.dolphin.setXRot(0.0F);
    }

    public void tick() {
        boolean flag = this.breached;
        if (!flag) {
            FluidState fluidstate = this.dolphin.level().getFluidState(this.dolphin.blockPosition());
            this.breached = fluidstate.is(FluidTags.WATER);
        }

        if (this.breached && !flag) {
            this.dolphin.playSound(SoundEvents.DOLPHIN_JUMP, 1.0F, 1.0F);
        }

        Vec3 vec3 = this.dolphin.getDeltaMovement();
        if (vec3.y * vec3.y < (double)0.03F && this.dolphin.getXRot() != 0.0F) {
            this.dolphin.setXRot(Mth.rotLerp(0.2F, this.dolphin.getXRot(), 0.0F));
        } else if (vec3.length() > (double)1.0E-5F) {
            double d0 = vec3.horizontalDistance();
            double d1 = Math.atan2(-vec3.y, d0) * (double)(180F / (float)Math.PI);
            this.dolphin.setXRot((float)d1);
        }

    }
}
