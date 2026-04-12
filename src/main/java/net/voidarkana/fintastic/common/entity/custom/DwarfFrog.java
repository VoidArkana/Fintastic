package net.voidarkana.fintastic.common.entity.custom;

import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.voidarkana.fintastic.common.entity.FintyEntities;
import net.voidarkana.fintastic.common.entity.custom.ai.FishBreedGoal;
import net.voidarkana.fintastic.common.entity.custom.base.BreedableWaterAnimal;
import net.voidarkana.fintastic.common.entity.custom.base.BucketableFishEntity;
import net.voidarkana.fintastic.common.item.FintyItems;
import net.voidarkana.fintastic.util.FintyTags;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

public class DwarfFrog extends BucketableFishEntity {

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(DwarfFrog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TICKS_ON_GROUND = SynchedEntityData.defineId(DwarfFrog.class, EntityDataSerializers.INT);

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
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 8.0F, 1.6D, 1.4D, (entity) -> {
            if (entity instanceof Player player){
                return !player.isCreative() && !player.isSpectator() && !player.getItemBySlot(EquipmentSlot.HEAD).is(FintyItems.FISHING_HAT.get());
            }
            return false;}));
        this.goalSelector.addGoal(4, new FrogSwimGoal(this, 1.0D, 400, 10));
        this.goalSelector.addGoal(2, new FishBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 2D, FOOD_ITEMS, false));
        this.goalSelector.addGoal(3, new TemptGoal(this, 2D, FOOD_ITEMS2, false));
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
        return !this.isBaby() && this.getNavigation().isDone();
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
            }else {
                if (this.getTicksOnGround() < 3){
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
}
