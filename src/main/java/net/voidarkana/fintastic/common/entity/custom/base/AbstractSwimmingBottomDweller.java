package net.voidarkana.fintastic.common.entity.custom.base;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.JumpControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.voidarkana.fintastic.common.entity.custom.Pleco;
import net.voidarkana.fintastic.common.entity.custom.SmallCatfish;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractSwimmingBottomDweller extends BucketableFishEntity{

    public int swimmingTicks = 0;
    public int prevSwimTick = 0;
    int prevTicksOnGround;
    private static final EntityDataAccessor<Boolean> WANTS_TO_SWIM = SynchedEntityData.defineId(AbstractSwimmingBottomDweller.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> TICKS_ON_GROUND = SynchedEntityData.defineId(AbstractSwimmingBottomDweller.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(AbstractSwimmingBottomDweller.class, EntityDataSerializers.INT);

    protected AbstractSwimmingBottomDweller(EntityType<? extends BreedableWaterAnimal> entityType, Level level) {
        super(entityType, level);
        this.setStepHeight(1);
        this.jumpControl = new FishJumpControl(this);
        if (this instanceof SmallCatfish){
            this.moveControl = new SmoothSwimmingMoveControl(this, 1, 20, 0.02F, 0.1F, true);
        }else {
            this.moveControl = new SmoothSwimmingMoveControl(this, 1, 1, 0.02F, 0.1F, true);
        }
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(WANTS_TO_SWIM, false);
        builder.define(TICKS_ON_GROUND, 0);
        builder.define(VARIANT, 0);
    }

    protected void setStepHeight(float stepHeight) {
        AttributeInstance attributeinstance = this.getAttribute(Attributes.STEP_HEIGHT);
        if (attributeinstance != null) {
            attributeinstance.setBaseValue(stepHeight);
        }
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getVariant());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setVariant(compound.getInt("Variant"));
    }

    //variants
    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }

    public int getTicksOnGround() {
        return this.entityData.get(TICKS_ON_GROUND);
    }

    public void setTicksOnGround(int ticks) {
        this.entityData.set(TICKS_ON_GROUND, ticks);
    }

    public boolean getWantsToSwim() {
        return this.entityData.get(WANTS_TO_SWIM);
    }

    public void setWantsToSwim(boolean fromBucket) {
        if (fromBucket){
            this.lookControl = new SmoothSwimmingLookControl(this, 10);
        }else {
            this.lookControl = new LookControl(this);
        }
        this.entityData.set(WANTS_TO_SWIM, fromBucket);
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

        //            if ( this.isInWater() && !this.getWantsToSwim() && !this.onGround()){
        //                this.moveRelative(0, new Vec3(0, this.yya-1, 0));
        //                this.move(MoverType.SELF, this.getDeltaMovement());
        //            }

    }

    @Override
    public void aiStep() {
        if (this.isInWater()){
            BlockPos pos = this.blockPosition();
            BlockState block = this.level().getBlockState(pos.above());
            if (this.maxUpStep() >= 1 && block.getFluidState().is(Fluids.EMPTY)){
                this.setStepHeight(0);
            }else if (this.isInWater() && block.getFluidState().is(Fluids.WATER)){
                this.setStepHeight(1);
            }
            if (!this.level().isClientSide()){
                if (this.isInWaterOrBubble() && !this.onGround()){
                    if (this.getTicksOnGround() > 0){
                        this.prevTicksOnGround = this.getTicksOnGround();
                        this.setTicksOnGround(this.prevTicksOnGround-1);
                    }
                }else {
                    if (this.getTicksOnGround() < 3){
                        this.prevTicksOnGround = this.getTicksOnGround();
                        this.setTicksOnGround(this.prevTicksOnGround+1);
                    }
                }
            }
        }
        super.aiStep();
    }

    public void travel(Vec3 travelVector) {

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

        super.travel(travelVector);
    }

    @Override
    protected void actuallyHurt(@NotNull DamageSource damageSource, float damageAmount) {

        if (swimmingTicks>0){
            swimmingTicks=0;
        }
        if (!this.getWantsToSwim()){
            this.setWantsToSwim(true);
        }

        super.actuallyHurt(damageSource, damageAmount);
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


    public static class BottomDwellerSwimGoal extends RandomSwimmingGoal {
        AbstractSwimmingBottomDweller pleco;

        public BottomDwellerSwimGoal(AbstractSwimmingBottomDweller mob) {
            super(mob, 1.0D, 50);
            this.pleco = mob;
        }

        @Override
        public boolean canUse() {
            return this.pleco.getWantsToSwim() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return this.pleco.getWantsToSwim() && super.canContinueToUse();
        }
    }

    public static class BottomMoveGoal extends RandomStrollGoal {
        AbstractSwimmingBottomDweller fish;
        public BottomMoveGoal(AbstractSwimmingBottomDweller mob, double speedModifier, int interval) {
            super(mob, speedModifier, interval);
            this.fish = mob;
        }

        @Override
        public boolean canUse() {
            return !this.fish.getWantsToSwim() && this.fish.onGround() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return !this.fish.getWantsToSwim() && super.canContinueToUse();
        }

        @Nullable
        @Override
        protected Vec3 getPosition() {
            return DefaultRandomPos.getPos(this.fish, 10, 1);
        }
    }
}
