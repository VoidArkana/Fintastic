package net.voidarkana.fintastic.common.entity.custom.base;

import net.minecraft.advancements.critereon.BredAnimalsTrigger;
import net.voidarkana.fintastic.mixin.common.SimpleCriterionTriggerInvoker;
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
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.*;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.voidarkana.fintastic.common.item.FintyItems;
import net.voidarkana.fintastic.util.FintyTags;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public abstract class BreedableWaterAnimal extends WaterAnimal {

    public final AnimationState idleAnimationState = new AnimationState();
    public float currentRoll = 0.0F;
    int prevTicksOutsideWater;
    @Nullable
    public RandomSwimmingGoal randomSwimmingGoal;

    protected BreedableWaterAnimal(EntityType<? extends BreedableWaterAnimal> entityType, Level level) {
        super(entityType, level);
        if (hasNormalControls()){
            this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, true);
            this.lookControl = new SmoothSwimmingLookControl(this, 10);
        }
    }

    public void calculateEntityAnimation(boolean includeHeight) {
        float f = (float)Mth.length(this.getX() - this.xo, this.floatsDown()  ? 0 : this.getY() - this.yo , this.getZ() - this.zo);
        this.updateWalkAnimation(f);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.5D));

        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 8.0F, 1.6D, 1.4D, (entity) -> {
            if (entity instanceof Player player){
                return !player.isCreative() && !player.isSpectator() && !player.getItemBySlot(EquipmentSlot.HEAD).is(FintyItems.FISHING_HAT.get());
            }
            return false;}));

        this.randomSwimmingGoal = new RandomSwimmingGoal(this, 1.0D, 10);

        this.goalSelector.addGoal(4, randomSwimmingGoal);
    }

    public boolean hasNormalControls(){
        return true;
    }

    public boolean canFlop(){
        return true;
    }

    //ageable mob
    private static final EntityDataAccessor<Boolean> DATA_BABY_ID = SynchedEntityData.defineId(BreedableWaterAnimal.class, EntityDataSerializers.BOOLEAN);
    protected int age;
    protected int forcedAge;
    protected int forcedAgeTimer;


    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnData) {

        if (spawnData == null) {
            spawnData = new BreedableWaterAnimal.AgeableFishGroupData(true);
        }

        if (reason == MobSpawnType.STRUCTURE && this instanceof Bucketable bucketable){
            bucketable.setFromBucket(true);
        }

        BreedableWaterAnimal.AgeableFishGroupData ageablemob$ageablemobgroupdata = (BreedableWaterAnimal.AgeableFishGroupData)spawnData;
        if (ageablemob$ageablemobgroupdata.isShouldSpawnBaby() && ageablemob$ageablemobgroupdata.getGroupSize() > 0 && level.getRandom().nextFloat() <= ageablemob$ageablemobgroupdata.getBabySpawnChance()) {
            this.setAge(-24000);
        }

        ageablemob$ageablemobgroupdata.increaseGroupSizeByOne();
        return super.finalizeSpawn(level, difficulty, reason, spawnData);
    }

    @Nullable
    public abstract BreedableWaterAnimal getBreedOffspring(ServerLevel level, BreedableWaterAnimal otherParent);

    public int getAge() {
        if (this.level().isClientSide) {
            return this.entityData.get(DATA_BABY_ID) ? -1 : 1;
        } else {
            return this.age;
        }
    }

    public void ageUp(int amount, boolean forced) {
        int i = this.getAge();
        i += amount * 20;
        if (i > 0) {
            i = 0;
        }

        this.setAge(i);
        if (forced) {
            if (this.forcedAgeTimer == 0) {
                this.forcedAgeTimer = 40;
            }
        }

        if (this.getAge() == 0) {
            this.setAge(this.forcedAge);
        }

    }

    public void ageUp(int amount) {
        this.ageUp(amount, false);
    }

    public void setAge(int age) {
        int i = this.getAge();
        this.age = age;
        if (i < 0 && age >= 0 || i >= 0 && age < 0) {
            this.entityData.set(DATA_BABY_ID, age < 0);
            this.ageBoundaryReached();
        }

    }

    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> key) {
        if (DATA_BABY_ID.equals(key)) {
            this.refreshDimensions();
        }

        super.onSyncedDataUpdated(key);
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

    public void setBaby(boolean baby) {
        this.setAge(baby ? -24000 : 0);
    }

    public static int getSpeedUpSecondsWhenFeeding(int ticksUntilAdult) {
        return (int)((float)(ticksUntilAdult / 20) * 0.1F);
    }

    public static class AgeableFishGroupData implements SpawnGroupData {
        private int groupSize;
        private final boolean shouldSpawnBaby;
        private final float babySpawnChance;

        private AgeableFishGroupData(boolean shouldSpawnBaby, float babySpawnChance) {
            this.shouldSpawnBaby = shouldSpawnBaby;
            this.babySpawnChance = babySpawnChance;
        }

        public AgeableFishGroupData(boolean shouldSpawnBaby) {
            this(shouldSpawnBaby, 0.05F);
        }

        public AgeableFishGroupData(float babySpawnChance) {
            this(true, babySpawnChance);
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

    public void travel(@NotNull Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.getTarget() == null && this.floatsUp()) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
            if (this.getTarget() == null && this.floatsDown()) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.009D, 0.0D));
            }
        } else {
            super.travel(travelVector);
        }

    }

    public boolean floatsUp(){
        return true;
    }


    public boolean floatsDown(){
        return false;
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return SoundEvents.COD_HURT;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.COD_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.COD_DEATH;
    }

    protected @NotNull SoundEvent getSwimSound() {
        return SoundEvents.FISH_SWIM;
    }

    protected void playSwimSound(float volume) {
        this.playSound(this.getSwimSound(), 0, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
    }

    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            this.inLove = 0;
            return super.hurt(source, amount);
        }
    }

    public int getBaseExperienceReward() {
        return 1 + this.level().random.nextInt(3);
    }

    protected void usePlayerItem(Player player, InteractionHand hand, ItemStack stack) {
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }

    }

    public boolean canFallInLove() {
        return this.inLove <= 0;
    }

    public void setInLove(@Nullable Player player) {
        this.inLove = 600;
        if (player != null) {
            this.loveCause = player.getUUID();
        }

        this.level().broadcastEntityEvent(this, (byte)18);
    }

    public void setInLoveTime(int inLove) {
        this.inLove = inLove;
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

    public boolean canMate(BreedableWaterAnimal otherAnimal) {
        if (otherAnimal == this) {
            return false;
        } else if (otherAnimal.getClass() != this.getClass()) {
            return false;
        } else {
            return this.isInLove() && otherAnimal.isInLove();
        }
    }

    public void spawnChildFromBreeding(ServerLevel level, BreedableWaterAnimal mate) {
        BreedableWaterAnimal ageablemob = this.getBreedOffspring(level, mate);
        BreedableWaterAnimal ageableMob2 = null;
        BreedableWaterAnimal ageableMob3 = null;
        BreedableWaterAnimal ageableMob4 = null;
        BreedableWaterAnimal ageableMob5 = null;

        int lowerQuality = Math.min(this.getFeedQuality(), mate.getFeedQuality());

        final BreedableWaterAnimal.BabyFishSpawnEvent event = new BreedableWaterAnimal.BabyFishSpawnEvent(this, mate, ageablemob);
        ageablemob = event.getChild();

        if ((lowerQuality > 0 && this.random.nextBoolean()) || lowerQuality > 2){
            ageableMob2 = this.getBreedOffspring(level, mate);
            final BreedableWaterAnimal.BabyFishSpawnEvent event2 = new BreedableWaterAnimal.BabyFishSpawnEvent(this, mate, ageableMob2);
            ageableMob2 = event2.getChild();

            if ((lowerQuality > 1 && this.random.nextInt(4)==0) || (lowerQuality > 2 && this.random.nextBoolean())){
                ageableMob3 = this.getBreedOffspring(level, mate);
                final BreedableWaterAnimal.BabyFishSpawnEvent event3 = new BreedableWaterAnimal.BabyFishSpawnEvent(this, mate, ageableMob3);
                ageableMob3 = event3.getChild();

                if (lowerQuality > 2 && this.random.nextBoolean()){

                    ageableMob4 = this.getBreedOffspring(level, mate);
                    final BreedableWaterAnimal.BabyFishSpawnEvent event4 = new BreedableWaterAnimal.BabyFishSpawnEvent(this, mate, ageableMob4);
                    ageableMob4 = event4.getChild();

                    if (this.random.nextBoolean()){
                        ageableMob5 = this.getBreedOffspring(level, mate);
                        final BreedableWaterAnimal.BabyFishSpawnEvent event5 = new BreedableWaterAnimal.BabyFishSpawnEvent(this, mate, ageableMob5);
                        ageableMob5 = event5.getChild();
                    }
                }
            }
        }

        final boolean cancelled = NeoForge.EVENT_BUS.post(event).isCanceled();
        if (cancelled) {
            //Reset the "inLove" state for the animals
            this.setAge(6000);
            mate.setAge(6000);
            this.resetLove();
            mate.resetLove();
            return;
        }
        if (ageablemob != null) {

            ageablemob.setBaby(true);
            ageablemob.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
            this.finalizeSpawnChildFromBreeding(level, mate, ageablemob);
            level.addFreshEntityWithPassengers(ageablemob);

            if (ageableMob2 != null){

                ageableMob2.setBaby(true);
                ageableMob2.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
                this.finalizeSpawnChildFromBreeding(level, mate, ageableMob2);
                level.addFreshEntityWithPassengers(ageableMob2);

                if (ageableMob3 != null){

                    ageableMob3.setBaby(true);
                    ageableMob3.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
                    this.finalizeSpawnChildFromBreeding(level, mate, ageableMob3);
                    level.addFreshEntityWithPassengers(ageableMob3);

                    if (ageableMob4 != null){

                        ageableMob4.setBaby(true);
                        ageableMob4.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
                        this.finalizeSpawnChildFromBreeding(level, mate, ageableMob4);
                        level.addFreshEntityWithPassengers(ageableMob4);

                        if (ageableMob5 != null){

                            ageableMob5.setBaby(true);
                            ageableMob5.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
                            this.finalizeSpawnChildFromBreeding(level, mate, ageableMob5);
                            level.addFreshEntityWithPassengers(ageableMob5);
                        }
                    }
                }
            }
        }
    }

    public class BabyFishSpawnEvent extends Event implements ICancellableEvent
    {
        private final Mob parentA;
        private final Mob parentB;
        private final Player causedByPlayer;
        private BreedableWaterAnimal child;

        public BabyFishSpawnEvent(Mob parentA, Mob parentB, @Nullable BreedableWaterAnimal proposedChild)
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

        @Nullable
        public Player getCausedByPlayer()
        {
            return causedByPlayer;
        }

        @Nullable
        public BreedableWaterAnimal getChild()
        {
            return child;
        }

        public void setChild(BreedableWaterAnimal proposedChild)
        {
            child = proposedChild;
        }
    }

    public void finalizeSpawnChildFromBreeding(ServerLevel level, BreedableWaterAnimal animal, @Nullable BreedableWaterAnimal baby) {
        Optional.ofNullable(this.getLoveCause()).or(() -> Optional.ofNullable(animal.getLoveCause())).ifPresent((player) -> {
            player.awardStat(Stats.ANIMALS_BRED);
            //CriteriaTriggers.BRED_ANIMALS.trigger(p_277486_, this, pAnimal, pBaby);

            LootContext $$4 = EntityPredicate.createContext(player, this);
            LootContext $$5 = EntityPredicate.createContext(player, animal);
            LootContext $$6 = baby != null ? EntityPredicate.createContext(player, baby) : null;
            ((SimpleCriterionTriggerInvoker<BredAnimalsTrigger.TriggerInstance>) CriteriaTriggers.BRED_ANIMALS)
                    .fintastic$trigger(player, (triggerInstance) -> triggerInstance.matches($$4, $$5, $$6));
        });
        this.setAge(6000);
        animal.setAge(6000);
        this.resetLove();
        animal.resetLove();
        level.broadcastEntityEvent(this, (byte)18);
        if (level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            level.addFreshEntity(new ExperienceOrb(level, this.getX(), this.getY(), this.getZ(), this.getRandom().nextInt(7) + 1));
        }

    }

    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (itemstack.is(FintyItems.REGULAR_FEED.get())){
            this.setFeedQuality(0);
        }
        if (itemstack.is(FintyItems.QUALITY_FEED.get())){
            this.setFeedQuality(1);
        }
        if (itemstack.is(FintyItems.GREAT_FEED.get())){
            this.setFeedQuality(2);
        }
        if (itemstack.is(FintyItems.PREMIUM_FEED.get())){
            this.setFeedQuality(3);
        }

        return super.mobInteract(player, hand);
    }

    public void handleEntityEvent(byte id) {
        if (id == 18) {
            for(int i = 0; i < 7; ++i) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                this.level().addParticle(ParticleTypes.HEART, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
            }
        } else {
            super.handleEntityEvent(id);
        }

    }








    private static final Ingredient FOOD_ITEMS = Ingredient.of(FintyTags.Items.FISH_FEED);

    public float prevTilt;
    public float tilt;

    private static final EntityDataAccessor<Integer> FEED_TYPE = SynchedEntityData.defineId(BreedableWaterAnimal.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> CAN_GROW_UP = SynchedEntityData.defineId(BreedableWaterAnimal.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> TICKS_OUTSIDE_WATER = SynchedEntityData.defineId(BreedableWaterAnimal.class, EntityDataSerializers.INT);


    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(FEED_TYPE, 0);
        builder.define(CAN_GROW_UP, true);
        builder.define(DATA_BABY_ID, false);
        builder.define(TICKS_OUTSIDE_WATER, 0);
    }

    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Age", this.getAge());
        compound.putInt("ForcedAge", this.forcedAge);
        compound.putBoolean("CanGrowUp", this.getCanGrowUp());

        compound.putInt("FeedQuality", this.getFeedQuality());

        compound.putInt("InLove", this.inLove);
        if (this.loveCause != null) {
            compound.putUUID("LoveCause", this.loveCause);
        }
    }

    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setAge(compound.getInt("Age"));
        this.forcedAge = compound.getInt("ForcedAge");
        this.setCanGrowUp(compound.getBoolean("CanGrowUp"));

        this.setFeedQuality(compound.getInt("FeedQuality"));

        this.inLove = compound.getInt("InLove");
        this.loveCause = compound.hasUUID("LoveCause") ? compound.getUUID("LoveCause") : null;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new WaterBoundPathNavigation(this, level);
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

    public int getTicksOutsideWater() {
        return this.entityData.get(TICKS_OUTSIDE_WATER);
    }

    public void setTicksOutsideWater(int variant) {
        this.entityData.set(TICKS_OUTSIDE_WATER, variant);
    }

    @Override
    public void aiStep() {

        if (!this.isInWater() && this.onGround() && this.verticalCollision && this.canFlop()) {
            this.setDeltaMovement(this.getDeltaMovement().add((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F, 0.4F, (this.random.nextFloat() * 2.0F - 1.0F) * 0.05F));
            this.setOnGround(false);
            this.hasImpulse = true;
            this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getVoicePitch());
        }

        super.aiStep();

        if (this.isInWaterOrBubble()){
            if (!this.level().isClientSide()){
                if (this.getTicksOutsideWater() > 0){
                    this.prevTicksOutsideWater = this.getTicksOutsideWater();
                    this.setTicksOutsideWater(this.prevTicksOutsideWater-1);
                }
            }
        }else {
            if (!this.level().isClientSide()){
                if (this.getTicksOutsideWater() < 3){
                    this.prevTicksOutsideWater = this.getTicksOutsideWater();
                    this.setTicksOutsideWater(this.prevTicksOutsideWater+1);
                }
            }
        }

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
        float targetRoll = Math.clamp((this.getYRot() - this.yRotO) * 0.1F, -0.45F, 0.45F);
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
    public @NotNull InteractionResult interactAt(Player player, @NotNull Vec3 vec, @NotNull InteractionHand hand) {

        ItemStack itemstack = player.getItemInHand(hand);

        int i = this.getAge();

        if (this.isBaby() && itemstack.is(FintyItems.BAD_FEED.get()) && this.getCanGrowUp()){
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

            if (itemstack.is(FintyItems.REGULAR_FEED.get())){
                this.setFeedQuality(0);
            }
            if (itemstack.is(FintyItems.QUALITY_FEED.get())){
                this.setFeedQuality(1);
            }
            if (itemstack.is(FintyItems.GREAT_FEED.get())){
                this.setFeedQuality(2);
            }
            if (itemstack.is(FintyItems.PREMIUM_FEED.get())){
                this.setFeedQuality(3);
            }

            if (this.isBaby() && this.getCanGrowUp()){
                this.ageUp(getSpeedUpSecondsWhenFeedingFish(-i, this.getFeedQuality()), true);
                return InteractionResult.SUCCESS;
            }else if (this.isBaby()){
                if (itemstack.is(FintyItems.PREMIUM_FEED.get())){
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
                this.usePlayerItem(player, hand, itemstack);
                this.setInLove(player);
                return InteractionResult.SUCCESS;
            }

            if (this.isBaby()) {
                this.usePlayerItem(player, hand, itemstack);
                this.ageUp(getSpeedUpSecondsWhenFeeding(-i), true);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }

            if (this.level().isClientSide) {
                return InteractionResult.CONSUME;
            }
        }

        return super.interactAt(player, vec, hand);
    }

    public static int getSpeedUpSecondsWhenFeedingFish(int ticksUntilAdult, int multiplier) {
        return (int)((float)(ticksUntilAdult / 20) * 0.1F * (multiplier+1));
    }

    public boolean isFood(ItemStack stack) {
        return FOOD_ITEMS.test(stack);
    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP;
    }


    @Override
    public void tick() {
        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }

        super.tick();
    }

    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(true, this.tickCount);
    }
}
