package net.voidarkana.fintastic.common.entity.custom;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.voidarkana.fintastic.common.block.FintyBlocks;
import net.voidarkana.fintastic.common.entity.FintyEntities;
import net.voidarkana.fintastic.common.entity.custom.ai.FishBreedGoal;
import net.voidarkana.fintastic.common.entity.custom.base.BreedableWaterAnimal;
import net.voidarkana.fintastic.common.entity.custom.base.BucketableFishEntity;
import net.voidarkana.fintastic.common.item.FintyItems;
import net.voidarkana.fintastic.common.sound.FintySounds;
import net.voidarkana.fintastic.util.FintyTags;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

public class DwarfFrog extends BucketableFishEntity {

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(DwarfFrog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TICKS_ON_GROUND = SynchedEntityData.defineId(DwarfFrog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_PREGNANT = SynchedEntityData.defineId(DwarfFrog.class, EntityDataSerializers.BOOLEAN);

    private static final Ingredient FOOD_ITEMS = Ingredient.of(FintyTags.Items.FISH_FEED);
    private static final Ingredient FOOD_ITEMS2 = Ingredient.of(Items.SLIME_BALL);

    public boolean isFood(ItemStack pStack) {
        return FOOD_ITEMS.test(pStack) || pStack.is(Items.SLIME_BALL);
    }

    public DwarfFrog(EntityType<? extends BreedableWaterAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.5D));
        this.goalSelector.addGoal(1, new FrogBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(1, new FrogLaySpawnGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 8.0F, 1.6D, 1.4D, (entity) -> {
            if (entity instanceof Player player){
                return !player.isCreative() && !player.isSpectator() && !player.getItemBySlot(EquipmentSlot.HEAD).is(FintyItems.FISHING_HAT.get());
            }
            return false;}));
        this.goalSelector.addGoal(3, new TemptGoal(this, 2D, FOOD_ITEMS, false));
        this.goalSelector.addGoal(3, new TemptGoal(this, 2D, FOOD_ITEMS2, false));
        this.goalSelector.addGoal(4, new FrogSwimGoal(this, 1.0D, 400, 10));
        this.goalSelector.addGoal(5, new FrogGetAirGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.6F);
    }

    @Override
    public boolean floatsUp() {
        return this.isBaby() || this.getNavigation().isInProgress();
    }

    @Override
    public boolean floatsDown() {
        return !this.isBaby() && this.getNavigation().isDone() && !this.isPregnant();
    }

    @Override
    public boolean canFlop() {
        return this.isBaby();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
        this.entityData.define(TICKS_ON_GROUND, 3);
        this.entityData.define(IS_PREGNANT, false);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getVariant());
        compound.putBoolean("IsPregnant", this.isPregnant());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setVariant(compound.getInt("Variant"));
        this.setPregnant(compound.getBoolean("IsPregnant"));
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

    public boolean isPregnant() {
        return this.entityData.get(IS_PREGNANT);
    }

    public void setPregnant(boolean isPregnant) {
        this.entityData.set(IS_PREGNANT, isPregnant);
    }

    int prevTicksOnGround;
    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide()){

            if (this.isInWaterOrBubble() && !this.onGround()){
                if (this.getTicksOnGround() > 0){
                    this.prevTicksOnGround = this.getTicksOnGround();
                    this.setTicksOnGround(this.prevTicksOnGround-1);
                }
            }else if (this.isInWaterOrBubble() && this.onGround()){
                if (this.getTicksOnGround() < 20){
                    this.prevTicksOnGround = this.getTicksOnGround();
                    this.setTicksOnGround(this.prevTicksOnGround+1);
                }
            }
        }
    }

    @Override
    public void saveToBucketTag(ItemStack bucket) {
        CompoundTag compoundnbt = bucket.getOrCreateTag();
        Bucketable.saveDefaultDataToBucketTag(this, bucket);
        compoundnbt.putFloat("Health", this.getHealth());
        compoundnbt.putInt("Variant", this.getVariant());
        compoundnbt.putInt("Age", this.getAge());

        compoundnbt.putBoolean("CanGrow", this.getCanGrowUp());
        if (this.hasCustomName()) {
            bucket.setHoverName(this.getCustomName());
        }
    }

    @Override
    public void loadFromBucketTag(CompoundTag pTag) {
        Bucketable.loadDefaultDataFromBucketTag(this, pTag);

        if (pTag.contains("Variant"))
            this.setVariant(pTag.getInt("Variant"));

        if (pTag.contains("Age")) {
            this.setAge(pTag.getInt("Age"));
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {

        if (pReason == MobSpawnType.BUCKET && pDataTag != null && pDataTag.contains("Variant", 3)) {
            this.setVariant(pDataTag.getInt("Variant"));
            if (pDataTag.contains("Age")) {
                this.setAge(pDataTag.getInt("Age"));
            }
            this.setCanGrowUp(pDataTag.getBoolean("CanGrow"));
        }else{
            this.setVariant(Util.getRandom(FrogVariant.values(), this.random).getJoinedVariant());
        }

        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Nullable
    @Override
    public BreedableWaterAnimal getBreedOffspring(ServerLevel pLevel, BreedableWaterAnimal pOtherParent) {
        DwarfFrog baby = FintyEntities.DWARF_FROG.get().create(pLevel);
        if (baby != null){
            baby.setVariant(this.getVariant());
            baby.setFromBucket(true);
        }
        return baby;
    }

    @Override
    public ItemStack getBucketItemStack() {
        if (this.isBaby())
            return new ItemStack(FintyItems.DWARF_FROG_TADPOLE_BUCKET.get());
        else
            return new ItemStack(FintyItems.DWARF_FROG_BUCKET.get());
    }

    public static class FrogSwimGoal extends RandomSwimmingGoal{
        DwarfFrog frog;
        int babyInterval;
        public FrogSwimGoal(DwarfFrog mob, double speedModifier, int adultInterval, int babyInterval) {
            super(mob, speedModifier, adultInterval);
            this.frog = mob;
            this.babyInterval = babyInterval;
        }

        @Override
        public boolean canUse() {
            if (frog.isBaby())
                this.setInterval(this.babyInterval);
            return super.canUse();
        }
    }

    public static class FrogGetAirGoal extends BreathAirGoal {
        DwarfFrog frog;
        public FrogGetAirGoal(DwarfFrog pMob) {
            super(pMob);
            this.frog = pMob;
        }

        @Override
        public boolean canUse() {
            return this.frog.getRandom().nextInt(6000)==0;
        }

        @Override
        public boolean isInterruptable() {
            return true;
        }
    }

    public static class FrogBreedGoal extends FishBreedGoal {
        private final DwarfFrog animal;

        public FrogBreedGoal(DwarfFrog pAnimal, double pSpeedModifier) {
            super(pAnimal, pSpeedModifier);
            this.animal = pAnimal;
        }

        public boolean canUse() {
            return super.canUse() && !animal.isPregnant();
        }

        protected void breed() {
            ServerPlayer serverplayer = this.animal.getLoveCause();
            if (serverplayer == null && this.partner.getLoveCause() != null) {
                serverplayer = this.partner.getLoveCause();
            }

            if (serverplayer != null) {
                serverplayer.awardStat(Stats.ANIMALS_BRED);
//                CriteriaTriggers.BRED_ANIMALS.trigger(serverplayer, this.animal, this.partner, (AgeableMob)null);
            }

            this.animal.setPregnant(true);
            this.animal.setAge(6000);
            this.partner.setAge(6000);
            this.animal.resetLove();
            this.partner.resetLove();
            RandomSource randomsource = this.animal.getRandom();
            if (this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                this.level.addFreshEntity(new ExperienceOrb(this.level, this.animal.getX(), this.animal.getY(), this.animal.getZ(), randomsource.nextInt(7) + 1));
            }
        }
    }

    public static class FrogLaySpawnGoal extends MoveToBlockGoal{

        DwarfFrog animal;
        public FrogLaySpawnGoal(DwarfFrog pMob, double pSpeedModifier) {
            super(pMob, pSpeedModifier, 10, 10);
            this.animal = pMob;
        }

        @Override
        public boolean canUse() {
            return animal.isPregnant() && super.canUse();
        }

        public boolean canContinueToUse() {
            return animal.isPregnant() && super.canContinueToUse();
        }

        public void tick() {
            super.tick();
            BlockPos blockpos = this.animal.blockPosition();
            if (this.isReachedTarget()) {
                Level level = this.animal.level();
                level.playSound(null, blockpos, SoundEvents.FROG_LAY_SPAWN, SoundSource.BLOCKS, 0.3F, 0.9F + level.random.nextFloat() * 0.2F);
                BlockPos blockpos1 = this.blockPos;
                BlockState blockstate = FintyBlocks.DWARF_FROGSPAWN.get().defaultBlockState();
                level.setBlock(blockpos1, blockstate, 3);
                level.gameEvent(GameEvent.BLOCK_PLACE, blockpos1, GameEvent.Context.of(this.animal, blockstate));
                animal.setPregnant(false);
                this.animal.setInLoveTime(600);
            }else if (isValidTarget(animal.level(), animal.blockPosition().above())){

                Level level = this.animal.level();
                level.playSound(null, blockpos, SoundEvents.FROG_LAY_SPAWN, SoundSource.BLOCKS, 0.3F, 0.9F + level.random.nextFloat() * 0.2F);
                BlockPos blockpos1 = animal.blockPosition().above();
                BlockState blockstate = FintyBlocks.DWARF_FROGSPAWN.get().defaultBlockState();
                level.setBlock(blockpos1, blockstate, 3);
                level.gameEvent(GameEvent.BLOCK_PLACE, blockpos1, GameEvent.Context.of(this.animal, blockstate));
                animal.setPregnant(false);
                this.animal.setInLoveTime(600);
            }
        }

        @Override
        protected int nextStartTick(PathfinderMob pCreature) {
            return 80 + pCreature.getRandom().nextInt(100);
        }

        @Override
        protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {
            return pLevel.getBlockState(pPos.below()).getFluidState().is(Fluids.WATER) && pLevel.getBlockState(pPos).isAir();
        }
    }

    public enum FrogVariant implements StringRepresentable {
        BROWN(0, "brown", "tan"),
        GREEN(1, "green", "tan"),
        PEACH(2, "peach", "pale"),
        PINK(3, "pink", "tan");

        private final int joinedVariant;
        private final String name;
        private final String tadpole;

        FrogVariant(int variant, String name, String tadpole){
            this.joinedVariant = variant;
            this.name = name;
            this.tadpole = tadpole;
        }

        public int getJoinedVariant(){
            return this.joinedVariant;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        public String getTadpoleName() {
            return this.tadpole;
        }

        public static final IntFunction<FrogVariant> BY_ID
                = ByIdMap.sparse(FrogVariant::getJoinedVariant, values(), PEACH);

        public static final EnumCodec<FrogVariant> CODEC
                = StringRepresentable.fromEnum(FrogVariant::values);

        public static FrogVariant byId(int pId) {
            return BY_ID.apply(pId);
        }

        public static FrogVariant byName(String pName) {
            return CODEC.byName(pName, PEACH);
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.isBaby() || this.getRandom().nextBoolean() ? super.getAmbientSound() : FintySounds.DWARF_FROG_IDLE.get();
    }

    public int getAmbientSoundInterval() {
        return 200;
    }
}
