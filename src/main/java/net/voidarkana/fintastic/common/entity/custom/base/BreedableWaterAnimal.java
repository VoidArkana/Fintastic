package net.voidarkana.fintastic.common.entity.custom.base;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.*;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.Cancelable;
import net.voidarkana.fintastic.common.item.YAFMItems;
import net.voidarkana.fintastic.util.YAFMTags;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public abstract class BreedableWaterAnimal extends WaterAnimal {

    public float currentRoll = 0.0F;

    protected BreedableWaterAnimal(EntityType<? extends BreedableWaterAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        if (hasNormalControls()){
            this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, true);
            this.lookControl = new SmoothSwimmingLookControl(this, 10);
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.5D));

        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 8.0F, 1.6D, 1.4D, (entity) -> {
            if (entity instanceof Player player){
                return !player.isCreative() && !player.isSpectator() && !player.getItemBySlot(EquipmentSlot.HEAD).is(YAFMItems.FISHING_HAT.get());
            }
            return false;}));

        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 10));
    }

    public boolean hasNormalControls(){
        return true;
    }

    public boolean canFlop(){
        return true;
    }

    //ageable mob
    private static final EntityDataAccessor<Boolean> DATA_BABY_ID = SynchedEntityData.defineId(BreedableWaterAnimal.class, EntityDataSerializers.BOOLEAN);
    public static final int BABY_START_AGE = -24000;
    private static final int FORCED_AGE_PARTICLE_TICKS = 40;
    protected int age;
    protected int forcedAge;
    protected int forcedAgeTimer;

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        if (pSpawnData == null) {
            pSpawnData = new BreedableWaterAnimal.AgeableFishGroupData(true);
        }

        BreedableWaterAnimal.AgeableFishGroupData ageablemob$ageablemobgroupdata = (BreedableWaterAnimal.AgeableFishGroupData)pSpawnData;
        if (ageablemob$ageablemobgroupdata.isShouldSpawnBaby() && ageablemob$ageablemobgroupdata.getGroupSize() > 0 && pLevel.getRandom().nextFloat() <= ageablemob$ageablemobgroupdata.getBabySpawnChance()) {
            this.setAge(-24000);
        }

        ageablemob$ageablemobgroupdata.increaseGroupSizeByOne();
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Nullable
    public abstract BreedableWaterAnimal getBreedOffspring(ServerLevel pLevel, BreedableWaterAnimal pOtherParent);

    public int getAge() {
        if (this.level().isClientSide) {
            return this.entityData.get(DATA_BABY_ID) ? -1 : 1;
        } else {
            return this.age;
        }
    }

    public void ageUp(int pAmount, boolean pForced) {
        int i = this.getAge();
        i += pAmount * 20;
        if (i > 0) {
            i = 0;
        }

        int j = i - i;
        this.setAge(i);
        if (pForced) {
            this.forcedAge += j;
            if (this.forcedAgeTimer == 0) {
                this.forcedAgeTimer = 40;
            }
        }

        if (this.getAge() == 0) {
            this.setAge(this.forcedAge);
        }

    }

    public void ageUp(int pAmount) {
        this.ageUp(pAmount, false);
    }

    public void setAge(int pAge) {
        int i = this.getAge();
        this.age = pAge;
        if (i < 0 && pAge >= 0 || i >= 0 && pAge < 0) {
            this.entityData.set(DATA_BABY_ID, pAge < 0);
            this.ageBoundaryReached();
        }

    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_BABY_ID.equals(pKey)) {
            this.refreshDimensions();
        }

        super.onSyncedDataUpdated(pKey);
    }

    protected void ageBoundaryReached() {
        if (!this.isBaby() && this.isPassenger()) {
            Entity entity = this.getVehicle();
            if (entity instanceof Boat) {
                Boat boat = (Boat)entity;
                if (!boat.hasEnoughSpaceFor(this)) {
                    this.stopRiding();
                }
            }
        }

    }

    public boolean isBaby() {
        return this.getAge() < 0;
    }

    public void setBaby(boolean pBaby) {
        this.setAge(pBaby ? -24000 : 0);
    }

    public static int getSpeedUpSecondsWhenFeeding(int pTicksUntilAdult) {
        return (int)((float)(pTicksUntilAdult / 20) * 0.1F);
    }

    public static class AgeableFishGroupData implements SpawnGroupData {
        private int groupSize;
        private final boolean shouldSpawnBaby;
        private final float babySpawnChance;

        private AgeableFishGroupData(boolean pShouldSpawnBaby, float pBabySpawnChance) {
            this.shouldSpawnBaby = pShouldSpawnBaby;
            this.babySpawnChance = pBabySpawnChance;
        }

        public AgeableFishGroupData(boolean pShouldSpawnBaby) {
            this(pShouldSpawnBaby, 0.05F);
        }

        public AgeableFishGroupData(float pBabySpawnChance) {
            this(true, pBabySpawnChance);
        }

        public int getGroupSize() {
            return this.groupSize;
        }

        public void increaseGroupSizeByOne() {
            ++this.groupSize;
        }

        public boolean isShouldSpawnBaby() {
            return this.shouldSpawnBaby;
        }

        public float getBabySpawnChance() {
            return this.babySpawnChance;
        }
    }





    //animal

    protected static final int PARENT_AGE_AFTER_BREEDING = 6000;
    private int inLove;
    @Nullable
    private UUID loveCause;

    protected void customServerAiStep() {
        if (this.getAge() != 0) {
            this.inLove = 0;
        }

        super.customServerAiStep();
    }

    public void travel(Vec3 pTravelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), pTravelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(pTravelVector);
        }

    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.COD_HURT;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.COD_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.COD_DEATH;
    }

    protected SoundEvent getSwimSound() {
        return SoundEvents.FISH_SWIM;
    }

    protected void playSwimSound(float pVolume) {
        this.playSound(this.getSwimSound(), 0, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
    }

    public boolean hurt(DamageSource pSource, float pAmount) {
        if (this.isInvulnerableTo(pSource)) {
            return false;
        } else {
            this.inLove = 0;
            return super.hurt(pSource, pAmount);
        }
    }

    public int getExperienceReward() {
        return 1 + this.level().random.nextInt(3);
    }

    protected void usePlayerItem(Player pPlayer, InteractionHand pHand, ItemStack pStack) {
        if (!pPlayer.getAbilities().instabuild) {
            pStack.shrink(1);
        }

    }

    public boolean canFallInLove() {
        return this.inLove <= 0;
    }

    public void setInLove(@Nullable Player pPlayer) {
        this.inLove = 600;
        if (pPlayer != null) {
            this.loveCause = pPlayer.getUUID();
        }

        this.level().broadcastEntityEvent(this, (byte)18);
    }

    public void setInLoveTime(int pInLove) {
        this.inLove = pInLove;
    }

    public int getInLoveTime() {
        return this.inLove;
    }

    @Nullable
    public ServerPlayer getLoveCause() {
        if (this.loveCause == null) {
            return null;
        } else {
            Player player = this.level().getPlayerByUUID(this.loveCause);
            return player instanceof ServerPlayer ? (ServerPlayer)player : null;
        }
    }

    public boolean isInLove() {
        return this.inLove > 0;
    }

    public void resetLove() {
        this.inLove = 0;
    }

    public boolean canMate(BreedableWaterAnimal pOtherAnimal) {
        if (pOtherAnimal == this) {
            return false;
        } else if (pOtherAnimal.getClass() != this.getClass()) {
            return false;
        } else {
            return this.isInLove() && pOtherAnimal.isInLove();
        }
    }

    public void spawnChildFromBreeding(ServerLevel pLevel, BreedableWaterAnimal pMate) {
        BreedableWaterAnimal ageablemob = this.getBreedOffspring(pLevel, pMate);
        BreedableWaterAnimal ageableMob2 = null;
        BreedableWaterAnimal ageableMob3 = null;
        BreedableWaterAnimal ageableMob4 = null;
        BreedableWaterAnimal ageableMob5 = null;

        int lowerQuality = Math.min(this.getFeedQuality(), pMate.getFeedQuality());

        final BreedableWaterAnimal.BabyFishSpawnEvent event = new BreedableWaterAnimal.BabyFishSpawnEvent(this, pMate, ageablemob);
        ageablemob = event.getChild();

        if ((lowerQuality > 0 && this.random.nextBoolean()) || lowerQuality > 2){
            ageableMob2 = this.getBreedOffspring(pLevel, pMate);
            final BreedableWaterAnimal.BabyFishSpawnEvent event2 = new BreedableWaterAnimal.BabyFishSpawnEvent(this, pMate, ageableMob2);
            ageableMob2 = event2.getChild();

            if ((lowerQuality > 1 && this.random.nextInt(4)==0) || (lowerQuality > 2 && this.random.nextBoolean())){
                ageableMob3 = this.getBreedOffspring(pLevel, pMate);
                final BreedableWaterAnimal.BabyFishSpawnEvent event3 = new BreedableWaterAnimal.BabyFishSpawnEvent(this, pMate, ageableMob3);
                ageableMob3 = event3.getChild();

                if (lowerQuality > 2 && this.random.nextBoolean()){

                    ageableMob4 = this.getBreedOffspring(pLevel, pMate);
                    final BreedableWaterAnimal.BabyFishSpawnEvent event4 = new BreedableWaterAnimal.BabyFishSpawnEvent(this, pMate, ageableMob4);
                    ageableMob4 = event4.getChild();

                    if (this.random.nextBoolean()){
                        ageableMob5 = this.getBreedOffspring(pLevel, pMate);
                        final BreedableWaterAnimal.BabyFishSpawnEvent event5 = new BreedableWaterAnimal.BabyFishSpawnEvent(this, pMate, ageableMob5);
                        ageableMob5 = event5.getChild();
                    }
                }
            }
        }

        final boolean cancelled = net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
        if (cancelled) {
            //Reset the "inLove" state for the animals
            this.setAge(6000);
            pMate.setAge(6000);
            this.resetLove();
            pMate.resetLove();
            return;
        }
        if (ageablemob != null) {

            ageablemob.setBaby(true);
            ageablemob.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
            this.finalizeSpawnChildFromBreeding(pLevel, pMate, ageablemob);
            pLevel.addFreshEntityWithPassengers(ageablemob);

            if (ageableMob2 != null){

                ageableMob2.setBaby(true);
                ageableMob2.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
                this.finalizeSpawnChildFromBreeding(pLevel, pMate, ageableMob2);
                pLevel.addFreshEntityWithPassengers(ageableMob2);

                if (ageableMob3 != null){

                    ageableMob3.setBaby(true);
                    ageableMob3.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
                    this.finalizeSpawnChildFromBreeding(pLevel, pMate, ageableMob3);
                    pLevel.addFreshEntityWithPassengers(ageableMob3);

                    if (ageableMob4 != null){

                        ageableMob4.setBaby(true);
                        ageableMob4.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
                        this.finalizeSpawnChildFromBreeding(pLevel, pMate, ageableMob4);
                        pLevel.addFreshEntityWithPassengers(ageableMob4);

                        if (ageableMob5 != null){

                            ageableMob5.setBaby(true);
                            ageableMob5.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
                            this.finalizeSpawnChildFromBreeding(pLevel, pMate, ageableMob5);
                            pLevel.addFreshEntityWithPassengers(ageableMob5);
                        }
                    }
                }
            }
        }
    }

    @Cancelable
    public class BabyFishSpawnEvent extends net.minecraftforge.eventbus.api.Event
    {
        private final Mob parentA;
        private final Mob parentB;
        private final Player causedByPlayer;
        private BreedableWaterAnimal child;

        public BabyFishSpawnEvent(Mob parentA, Mob parentB, @org.jetbrains.annotations.Nullable BreedableWaterAnimal proposedChild)
        {
            //causedByPlayer calculated here to simplify the patch.
            Player causedByPlayer = null;
            if (parentA instanceof BreedableWaterAnimal) {
                causedByPlayer = ((BreedableWaterAnimal)parentA).getLoveCause();
            }

            if (causedByPlayer == null && parentB instanceof BreedableWaterAnimal)
            {
                causedByPlayer = ((BreedableWaterAnimal)parentB).getLoveCause();
            }

            this.parentA = parentA;
            this.parentB = parentB;
            this.causedByPlayer = causedByPlayer;
            this.child = proposedChild;
        }

        public Mob getParentA()
        {
            return parentA;
        }

        public Mob getParentB()
        {
            return parentB;
        }

        @org.jetbrains.annotations.Nullable
        public Player getCausedByPlayer()
        {
            return causedByPlayer;
        }

        @org.jetbrains.annotations.Nullable
        public BreedableWaterAnimal getChild()
        {
            return child;
        }

        public void setChild(BreedableWaterAnimal proposedChild)
        {
            child = proposedChild;
        }
    }

    public void finalizeSpawnChildFromBreeding(ServerLevel pLevel, BreedableWaterAnimal pAnimal, @Nullable BreedableWaterAnimal pBaby) {
        Optional.ofNullable(this.getLoveCause()).or(() -> {
            return Optional.ofNullable(pAnimal.getLoveCause());
        }).ifPresent((p_277486_) -> {
            p_277486_.awardStat(Stats.ANIMALS_BRED);
            //CriteriaTriggers.BRED_ANIMALS.trigger(p_277486_, this, pAnimal, pBaby);

            LootContext $$4 = EntityPredicate.createContext(p_277486_, this);
            LootContext $$5 = EntityPredicate.createContext(p_277486_, pAnimal);
            LootContext $$6 = pBaby != null ? EntityPredicate.createContext(p_277486_, pBaby) : null;
            CriteriaTriggers.BRED_ANIMALS.trigger(p_277486_, (p_18653_) -> p_18653_.matches($$4, $$5, $$6));
        });
        this.setAge(6000);
        pAnimal.setAge(6000);
        this.resetLove();
        pAnimal.resetLove();
        pLevel.broadcastEntityEvent(this, (byte)18);
        if (pLevel.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            pLevel.addFreshEntity(new ExperienceOrb(pLevel, this.getX(), this.getY(), this.getZ(), this.getRandom().nextInt(7) + 1));
        }

    }

    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        if (itemstack.is(YAFMItems.REGULAR_FEED.get())){
            this.setFeedQuality(0);
        }
        if (itemstack.is(YAFMItems.QUALITY_FEED.get())){
            this.setFeedQuality(1);
        }
        if (itemstack.is(YAFMItems.GREAT_FEED.get())){
            this.setFeedQuality(2);
        }
        if (itemstack.is(YAFMItems.PREMIUM_FEED.get())){
            this.setFeedQuality(3);
        }

        return super.mobInteract(pPlayer, pHand);
    }

    public void handleEntityEvent(byte pId) {
        if (pId == 18) {
            for(int i = 0; i < 7; ++i) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                this.level().addParticle(ParticleTypes.HEART, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
            }
        } else {
            super.handleEntityEvent(pId);
        }

    }








    private static final Ingredient FOOD_ITEMS = Ingredient.of(YAFMTags.Items.FISH_FEED);

    public float prevTilt;
    public float tilt;

    private static final EntityDataAccessor<Integer> FEED_TYPE = SynchedEntityData.defineId(BreedableWaterAnimal.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> CAN_GROW_UP = SynchedEntityData.defineId(BreedableWaterAnimal.class, EntityDataSerializers.BOOLEAN);


    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FEED_TYPE, 0);
        this.entityData.define(CAN_GROW_UP, true);
        this.entityData.define(DATA_BABY_ID, false);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Age", this.getAge());
        pCompound.putInt("ForcedAge", this.forcedAge);
        pCompound.putBoolean("CanGrowUp", this.getCanGrowUp());

        pCompound.putInt("FeedQuality", this.getFeedQuality());

        pCompound.putInt("InLove", this.inLove);
        if (this.loveCause != null) {
            pCompound.putUUID("LoveCause", this.loveCause);
        }
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setAge(pCompound.getInt("Age"));
        this.forcedAge = pCompound.getInt("ForcedAge");
        this.setCanGrowUp(pCompound.getBoolean("CanGrowUp"));

        this.setFeedQuality(pCompound.getInt("FeedQuality"));

        this.inLove = pCompound.getInt("InLove");
        this.loveCause = pCompound.hasUUID("LoveCause") ? pCompound.getUUID("LoveCause") : null;
    }

    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        return new WaterBoundPathNavigation(this, pLevel);
    }

    public int getFeedQuality() {
        return this.entityData.get(FEED_TYPE);
    }

    public void setFeedQuality(int variant) {
        this.entityData.set(FEED_TYPE, variant);
    }

    public Boolean getCanGrowUp() {
        return this.entityData.get(CAN_GROW_UP);
    }

    public void setCanGrowUp(boolean variant) {
        this.entityData.set(CAN_GROW_UP, variant);
    }

    @Override
    public void aiStep() {

        if (!this.isInWater() && this.onGround() && this.verticalCollision && this.canFlop()) {
            this.setDeltaMovement(this.getDeltaMovement().add((double)((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F), (double)0.4F, (double)((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F)));
            this.setOnGround(false);
            this.hasImpulse = true;
            this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getVoicePitch());
        }

        super.aiStep();

        //ageable mob
        if (this.level().isClientSide) {
            if (this.forcedAgeTimer > 0) {
                if (this.forcedAgeTimer % 4 == 0) {
                    this.level().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), 0.0D, 0.0D, 0.0D);
                }

                --this.forcedAgeTimer;
            }
        } else if (this.isAlive()) {
            int i = this.getAge();
            if (i < 0) {
                ++i;
                this.setAge(i);
            } else if (i > 0) {
                --i;
                this.setAge(i);
            }
        }

        //animal
        if (this.getAge() != 0) {
            this.inLove = 0;
        }

        if (this.inLove > 0) {
            --this.inLove;
            if (this.inLove % 10 == 0) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                this.level().addParticle(ParticleTypes.HEART, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
            }
        }

        //fish
        prevTilt = tilt;
        if (this.isInWater() && !this.onGround()) {
            final float v = Mth.degreesDifference(this.getYRot(), yRotO);
            if (Math.abs(v) > 1) {
                if (Math.abs(tilt) < 25) {
                    tilt -= Math.signum(v);
                }
            } else {
                if (Math.abs(tilt) > 0) {
                    final float tiltSign = Math.signum(tilt);
                    tilt -= tiltSign * 0.85F;
                    if (tilt * tiltSign < 0) {
                        tilt = 0;
                    }
                }
            }
        } else {
            tilt = 0;
        }

        float prevRoll =  this.currentRoll;
        float targetRoll = Math.max(-0.45F, Math.min(0.45F, (this.getYRot() - this.yRotO) * 0.1F));
        targetRoll = -targetRoll;
        this.currentRoll = prevRoll + (targetRoll - prevRoll) * 0.05F;

        if (this.isAlive() && !this.getCanGrowUp()) {
            if (this.getAge() >- 500){
                int i = this.getAge();
                this.setAge(i-6000);
            }
        }
    }

    @Override
    public InteractionResult interactAt(Player pPlayer, Vec3 pVec, InteractionHand pHand) {

        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        int i = this.getAge();

        if (this.isBaby() && itemstack.is(YAFMItems.BAD_FEED.get()) && this.getCanGrowUp()){
            this.setCanGrowUp(false);

            this.setAge(-12000);

            for(int j = 0; j < 7; ++j) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                this.level().addParticle(ParticleTypes.ANGRY_VILLAGER, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
            }

            return InteractionResult.SUCCESS;
        }

        if (isFood(itemstack)){

            if (itemstack.is(YAFMItems.REGULAR_FEED.get())){
                this.setFeedQuality(0);
            }
            if (itemstack.is(YAFMItems.QUALITY_FEED.get())){
                this.setFeedQuality(1);
            }
            if (itemstack.is(YAFMItems.GREAT_FEED.get())){
                this.setFeedQuality(2);
            }
            if (itemstack.is(YAFMItems.PREMIUM_FEED.get())){
                this.setFeedQuality(3);
            }

            if (this.isBaby() && this.getCanGrowUp()){
                this.ageUp(getSpeedUpSecondsWhenFeedingFish(-i, this.getFeedQuality()), true);
                return InteractionResult.SUCCESS;
            }else if (this.isBaby()){
                if (itemstack.is(YAFMItems.PREMIUM_FEED.get())){
                    this.setCanGrowUp(true);

                    for(int j = 0; j < 7; ++j) {
                        double d0 = this.random.nextGaussian() * 0.02D;
                        double d1 = this.random.nextGaussian() * 0.02D;
                        double d2 = this.random.nextGaussian() * 0.02D;
                        this.level().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
                    }

                }else {
                    return InteractionResult.PASS;
                }
            }

            if (!this.level().isClientSide && i == 0 && this.canFallInLove()) {
                this.usePlayerItem(pPlayer, pHand, itemstack);
                this.setInLove(pPlayer);
                return InteractionResult.SUCCESS;
            }

            if (this.isBaby()) {
                this.usePlayerItem(pPlayer, pHand, itemstack);
                this.ageUp(getSpeedUpSecondsWhenFeeding(-i), true);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }

            if (this.level().isClientSide) {
                return InteractionResult.CONSUME;
            }
        }

        return super.interactAt(pPlayer, pVec, pHand);
    }

    public static int getSpeedUpSecondsWhenFeedingFish(int pTicksUntilAdult, int multiplier) {
        return (int)((float)(pTicksUntilAdult / 20) * 0.1F * (multiplier+1));
    }

    public boolean isFood(ItemStack pStack) {
        return FOOD_ITEMS.test(pStack);
    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP;
    }
}
