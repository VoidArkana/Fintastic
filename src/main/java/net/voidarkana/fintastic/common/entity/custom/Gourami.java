package net.voidarkana.fintastic.common.entity.custom;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ByIdMap;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.voidarkana.fintastic.common.block.YAFMBlocks;
import net.voidarkana.fintastic.common.entity.YAFMEntities;
import net.voidarkana.fintastic.common.entity.custom.ai.FishBreedGoal;
import net.voidarkana.fintastic.common.entity.custom.base.BreedableWaterAnimal;
import net.voidarkana.fintastic.common.entity.custom.base.BucketableFishEntity;
import net.voidarkana.fintastic.common.item.YAFMItems;
import net.voidarkana.fintastic.util.YAFMTags;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.function.IntFunction;

public class Gourami extends BucketableFishEntity {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState flopAnimationState = new AnimationState();
    public final AnimationState investigatingAnimationState = new AnimationState();
    boolean canFloat;

    private static final Ingredient FOOD_ITEMS = Ingredient.of(YAFMTags.Items.FISH_FEED);

    private static final EntityDataAccessor<Integer> VARIANT_MODEL = SynchedEntityData.defineId(Gourami.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> VARIANT_SKIN = SynchedEntityData.defineId(Gourami.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> INVESTIGATING_TIME = SynchedEntityData.defineId(Gourami.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> WANTS_TO_INVESTIGATE = SynchedEntityData.defineId(Gourami.class, EntityDataSerializers.BOOLEAN);

    public boolean isFood(ItemStack pStack) {
        return FOOD_ITEMS.test(pStack);
    }

    public Gourami(EntityType<? extends BucketableFishEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.refreshDimensions();
        this.setCanFloat(true);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        this.refreshDimensions();
        super.onSyncedDataUpdated(pKey);
    }

    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        return switch (this.getVariantModel()){
            case 1, 2 ->super.getDimensions(pPose);
            default ->super.getDimensions(pPose).scale(2F, 3.5F);
        };
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new FishBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 2D, FOOD_ITEMS, false));
        this.goalSelector.addGoal(4, new GouramiInvestigateGoal(this, 40));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 7.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.8F);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT_MODEL, 0);
        this.entityData.define(VARIANT_SKIN, 0);
        this.entityData.define(INVESTIGATING_TIME, 0);
        this.entityData.define(WANTS_TO_INVESTIGATE, false);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("VariantModel", this.getVariantModel());
        pCompound.putInt("VariantSkin", this.getVariantSkin());
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setVariantModel(pCompound.getInt("VariantModel"));
        this.setVariantSkin(pCompound.getInt("VariantSkin"));
    }

    public int getVariantModel() {
        return this.entityData.get(VARIANT_MODEL);
    }

    public void setVariantModel(int pVariant) {
        this.entityData.set(VARIANT_MODEL, pVariant);
    }

    public int getVariantSkin() {
        return this.entityData.get(VARIANT_SKIN);
    }

    public void setVariantSkin(int pVariant) {
        this.entityData.set(VARIANT_SKIN, pVariant);
    }

    public int getInvestigatingTime() {
        return this.entityData.get(INVESTIGATING_TIME);
    }

    public void setInvestigatingTime(int pTime) {
        this.entityData.set(INVESTIGATING_TIME, pTime);
    }

    public boolean isInvestigating(){
        return this.getInvestigatingTime()>0;
    }

    public void setWantsToInvestigate(boolean wantsToInvestigate) {
        this.entityData.set(WANTS_TO_INVESTIGATE, wantsToInvestigate);
    }

    public boolean wantsToInvestigate(){
        return this.entityData.get(WANTS_TO_INVESTIGATE);
    }

    @Override
    public boolean canFloat() {
        return canFloat;
    }

    public void setCanFloat(boolean canFloat) {
        this.canFloat = canFloat;
    }

    @Nullable
    @Override
    public BreedableWaterAnimal getBreedOffspring(ServerLevel pLevel, BreedableWaterAnimal pOtherParent) {
        Gourami baby = YAFMEntities.GOURAMI.get().create(pLevel);
        if (baby != null){
            baby.setFromBucket(true);
            baby.setVariantModel(this.getVariantModel());
            baby.setVariantSkin(this.getVariantSkin());
        }
        return baby;
    }

    @Override
    public void tick() {
        if (this.level().isClientSide()){
            this.setupAnimationStates();
        }

        super.tick();

        if (!this.wantsToInvestigate() && this.random.nextInt(10)==0){
            this.setWantsToInvestigate(true);
        }

        if (this.isInvestigating()){
            int invTime = this.getInvestigatingTime();
            this.setInvestigatingTime(invTime-1);
        }
    }

    private void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isAlive(), this.tickCount);
        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble(), this.tickCount);

        this.investigatingAnimationState.animateWhen(this.isInvestigating(), this.tickCount);
//        if (this.getInvestigatingTime()==39){
//            this.idleAnimationState.start(this.tickCount);
//        }
    }

    @Override
    public void saveToBucketTag(ItemStack bucket) {
        CompoundTag compoundnbt = bucket.getOrCreateTag();
        Bucketable.saveDefaultDataToBucketTag(this, bucket);
        compoundnbt.putFloat("Health", this.getHealth());
        compoundnbt.putInt("Age", this.getAge());
        compoundnbt.putBoolean("CanGrow", this.getCanGrowUp());
        compoundnbt.putInt("VariantModel", this.getVariantModel());
        compoundnbt.putInt("VariantSkin", this.getVariantSkin());
        if (this.hasCustomName()) {
            bucket.setHoverName(this.getCustomName());
        }
    }

    @Override
    public void loadFromBucketTag(CompoundTag pTag) {
        Bucketable.loadDefaultDataFromBucketTag(this, pTag);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {

        if (pReason == MobSpawnType.BUCKET && pDataTag != null && pDataTag.contains("Age", 3)) {

            if (pDataTag.contains("Age")) {
                this.setAge(pDataTag.getInt("Age"));
            }
            this.setCanGrowUp(pDataTag.getBoolean("CanGrow"));
            this.setVariantModel(pDataTag.getInt("VariantModel"));
            this.setVariantSkin(pDataTag.getInt("VariantSkin"));
            this.setAirSupply(this.getMaxAirSupply());

        }else if (pReason == MobSpawnType.SPAWN_EGG || (pReason == MobSpawnType.BUCKET && pDataTag == null)){

            GouramiVariant variant = Util.getRandom(GouramiVariant.values(), this.random);

            this.setVariantModel(variant.getModel());
            this.setVariantSkin(variant.getSkin());

            if (this.getVariantModel() == 0 && pReason == MobSpawnType.BUCKET)
                this.setAge(-24000);

            this.setAirSupply(this.getMaxAirSupply());
        }

        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(YAFMItems.GOURAMI_BUCKET.get());
    }

    @Override
    public boolean canBeBucketed() {
        return this.getVariantModel()!=0 || (this.getVariantModel() == 0 && this.isBaby());
    }

    @Override
    public boolean canMate(BreedableWaterAnimal pOtherAnimal) {
        Gourami mate = (Gourami) pOtherAnimal;
        return super.canMate(pOtherAnimal) && mate.getVariantModel() == this.getVariantModel() && mate.getVariantSkin() == this.getVariantSkin();
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_FILL_FISH;
    }

    public static class GouramiInvestigateGoal extends MoveToBlockGoal{
        private final Gourami fish;
        int duration;
        int currentDuration;

        public GouramiInvestigateGoal(Gourami pMob, int duration) {
            super(pMob, 0.9, 16, 16);
            this.fish = pMob;
            this.duration = duration;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
        }

        @Override
        public boolean canUse() {
            return super.canUse() && this.fish.wantsToInvestigate();
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && this.currentDuration>0;
        }

        public void start() {
            this.currentDuration = duration;
            this.fish.level().broadcastEntityEvent(this.fish, (byte)10);
            this.fish.setInvestigatingTime(0);
        }
        
        public void tick() {
            BlockPos blockpos = this.getMoveToTarget();
            this.fish.getLookControl().setLookAt((double)blockpos.getX() + 0.5D, blockpos.getY() + 0.5D,
                    (double)blockpos.getZ() + 0.5D, 10.0F, (float)this.fish.getMaxHeadXRot());

            if (!blockpos.closerToCenterThan(this.mob.position(), this.acceptedDistance())) {
                ++this.tryTicks;
                if (this.shouldRecalculatePath()) {
                    this.mob.getNavigation().moveTo((double)((float)blockpos.getX()) + 0.5D, blockpos.getY()-0.5D,
                            (double)((float)blockpos.getZ()) + 0.5D, this.speedModifier);
                }
            } else {

                if (!this.fish.isInvestigating() && this.currentDuration > 0){
                    this.fish.setInvestigatingTime(40);
                    this.fish.getNavigation().stop();
                }

                this.nextStartTick = 10;
                this.currentDuration--;

                if (this.currentDuration <=0){
                    this.stop();
                }
                --this.tryTicks;
            }

        }

        public void stop() {
            this.nextStartTick = 10;
            if (this.currentDuration<=0){
                if (this.fish.randomSwimmingGoal != null) {
                    this.fish.randomSwimmingGoal.trigger();
                }
            }else {
                this.currentDuration = 0;
            }
            this.fish.setInvestigatingTime(0);
            this.fish.setWantsToInvestigate(false);
            this.fish.setCanFloat(true);

        }

        @Override
        protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {
            BlockState blockstate = pLevel.getBlockState(pPos);
            return blockstate.is(YAFMTags.Blocks.GOURAMI_INVESTIGATION_TARGETS) &&
                    !(blockstate.is(Blocks.SEAGRASS) || blockstate.is(Blocks.TALL_SEAGRASS));
        }
    }
    
    public enum GouramiVariant{
        OSPHRONEMUS_GORAMI(0, "osphronemus_gorami"),
        REDTAIL_GIANT_GOURAMI(1, "redtail_giant_gourami"),
        RUBY_RED_GIANT_GOURAMI(2, "ruby_red_giant_gourami"),

        BLUE_THREE_SPOT_GOURAMI(10, "blue_three_spot_gourami"),
        DWARF_GOURAMI(11, "dwarf_gourami"),
        HONEY_GOURAMI(12, "honey_gourami"),
        KISSING_GOURAMI(13, "kissing_gourami"),
        LAVENDER_THREE_SPOT_GOURAMI(14, "lavender_three_spot_gourami"),
        PEARL_GOURAMI(15, "pearl_gourami"),
        YELLOW_THREE_SPOT_GOURAMI(16, "yellow_three_spot_gourami"),

        BETTA_ALBIMARGINATA(20, "betta_albimarginata"),
        BETTA_MAHACHAI(21, "betta_mahachai"),
        BETTA_SPLENDENS_WILD(22, "betta_splendens_wild"),
        CROAKING_GOURAMI(23, "croaking_gourami");

        private final int joinedVariant;
        private final String name;

        GouramiVariant(int variant, String name){
            this.joinedVariant = variant;
            this.name = name;
        }

        public int getJoinedVariant(){
            return this.joinedVariant;
        }

        public int getModel(){
            return this.joinedVariant/10;
        }

        public int getSkin(){
            return this.joinedVariant%10;
        }

        public String getName(){
            return this.name;
        }

        public String getModelName(){
            return switch (this.joinedVariant /10) {
                case 1 -> "med";
                case 2 -> "small";
                default -> "huge";
            };
        }

        private static final IntFunction<Gourami.GouramiVariant> BY_ID
                = ByIdMap.sparse(Gourami.GouramiVariant::getJoinedVariant,
                values(), OSPHRONEMUS_GORAMI);


        public static Gourami.GouramiVariant byId(int pId) {
            return BY_ID.apply(pId);
        }
    }
}
