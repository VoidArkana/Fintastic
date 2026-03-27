package net.voidarkana.fintastic.common.entity.custom.base;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.control.JumpControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.voidarkana.fintastic.common.entity.custom.Pleco;

public abstract class AbstractSwimmingBottomDweller extends BucketableFishEntity{

    public int swimmingTicks = 0;
    public int prevSwimTick = 0;
    private static final EntityDataAccessor<Boolean> WANTS_TO_SWIM = SynchedEntityData.defineId(AbstractSwimmingBottomDweller.class, EntityDataSerializers.BOOLEAN);

    protected AbstractSwimmingBottomDweller(EntityType<? extends BreedableWaterAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setMaxUpStep(1);
        this.jumpControl = new FishJumpControl(this);
        this.moveControl = new SmoothSwimmingMoveControl(this, 1, 1, 0.02F, 0.1F, true);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(WANTS_TO_SWIM, false);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("WantsToSwim", this.fromBucket());
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setFromBucket(pCompound.getBoolean("WantsToSwim"));
    }


    public boolean getWantsToSwim() {
        return this.entityData.get(WANTS_TO_SWIM);
    }

    public void setWantsToSwim(boolean pFromBucket) {
        if (pFromBucket){
            this.lookControl = new SmoothSwimmingLookControl(this, 10);
        }else {
            this.lookControl = new LookControl(this);
        }
        this.entityData.set(WANTS_TO_SWIM, pFromBucket);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.onGround() && this.isInWater() && this.random.nextInt(1000)==0 && !this.getWantsToSwim()){
            if (this instanceof Pleco pleco){
                if (!pleco.isStrafing())
                    this.setWantsToSwim(true);
            }else {
                this.setWantsToSwim(true);
            }
        }

        if (this.isInWater() && !this.onGround() && (this.random.nextInt(1000)==0 || swimmingTicks == 2400) && this.getWantsToSwim()){
            this.setWantsToSwim(false);
            swimmingTicks = 0;
            prevSwimTick = 0;
        }

        if (this.getWantsToSwim()){
            prevSwimTick = swimmingTicks;
            swimmingTicks = prevSwimTick+1;
        }

        if (!this.level().isClientSide) {

//            if ( this.isInWater() && !this.getWantsToSwim() && !this.onGround()){
//                this.moveRelative(0, new Vec3(0, this.yya-1, 0));
//                this.move(MoverType.SELF, this.getDeltaMovement());
//            }
        }

    }

    @Override
    public void aiStep() {
        if (this.isInWater()){
            BlockPos pos = this.blockPosition();
            BlockState block = this.level().getBlockState(pos.above());
            if (this.getStepHeight() >= 1 && block.getFluidState().is(Fluids.EMPTY)){
                this.setMaxUpStep(0);
            }else if (this.isInWater() && block.getFluidState().is(Fluids.WATER)){
                this.setMaxUpStep(1);
            }
        }
        super.aiStep();
    }

    public void travel(Vec3 pTravelVector) {

        if (this.isEffectiveAi() && this.isInWater() && !this.getWantsToSwim()) {
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        }

        if (this.isEffectiveAi() && this.isInWater() && this.getWantsToSwim()) {
            if (this.getTarget() == null && this.random.nextInt(100)==0) {
                if (this instanceof Pleco pleco)
                    this.setWantsToSwim(pleco.wantsToAttach());
                else
                    this.setWantsToSwim(false);
            }
        }

        super.travel(pTravelVector);
    }

    @Override
    protected void actuallyHurt(DamageSource pDamageSource, float pDamageAmount) {

        if (swimmingTicks>0){
            swimmingTicks=0;
        }
        if (!this.getWantsToSwim()){
            this.setWantsToSwim(true);
        }

        super.actuallyHurt(pDamageSource, pDamageAmount);
    }

    static class FishJumpControl extends JumpControl {

        AbstractSwimmingBottomDweller mob;
        public FishJumpControl(AbstractSwimmingBottomDweller fish) {
            super(fish);
            mob = fish;
        }

        @Override
        public void jump() {
            if (!mob.isInWater()){
                super.jump();
            }
        }
    }
}
