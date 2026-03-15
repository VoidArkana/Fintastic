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
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.item.ItemStack;
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

public class Featherback extends BucketableFishEntity {

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Featherback.class, EntityDataSerializers.INT);

    private static final Ingredient FOOD_ITEMS = Ingredient.of(FintyTags.Items.FISH_FEED);

    public boolean isFood(ItemStack pStack) {
        return FOOD_ITEMS.test(pStack);
    }

    public Featherback(EntityType<? extends BreedableWaterAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.refreshDimensions();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new FishBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 2D, FOOD_ITEMS, false));
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        this.refreshDimensions();
        super.onSyncedDataUpdated(pKey);
    }

    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        return switch (FeatherbackVariant.byId(this.getVariant()).getModel()){
            case 2 ->super.getDimensions(pPose);
            default -> super.getDimensions(pPose).scale(1F, 0.6F);
        };
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.6F);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
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
            this.setVariant(Util.getRandom(FeatherbackVariant.values(), this.random).getJoinedVariant());
        }

        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Nullable
    @Override
    public BreedableWaterAnimal getBreedOffspring(ServerLevel pLevel, BreedableWaterAnimal pOtherParent) {
        Featherback baby = FintyEntities.FEATHERBACK.get().create(pLevel);
        if (baby != null){
            baby.setVariant(this.getVariant());
            baby.setFromBucket(true);
        }
        return baby;
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(FintyItems.FEATHERBACK_BUCKET.get());
    }

    @Override
    public boolean canMate(BreedableWaterAnimal pOtherAnimal) {
        Featherback mate = (Featherback) pOtherAnimal;
        return super.canMate(pOtherAnimal) && this.getVariant() == mate.getVariant();
    }

    public enum FeatherbackVariant implements StringRepresentable {
        AFRICAN_BROWN_KNIFEFISH(0, "african_brown_knifefish"),

        RETICULATED_KNIFEFISH(10, "reticulated_knifefish"),

        CLOWN_FEATHERBACK(20, "clown_featherback"),
        BELIDA(21, "belida");

        private final int joinedVariant;
        private final String name;

        FeatherbackVariant(int variant, String name){
            this.joinedVariant = variant;
            this.name = name;
        }

        public int getJoinedVariant(){
            return this.joinedVariant;
        }

        public int getModel(){
            return this.joinedVariant/10;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        public static final IntFunction<FeatherbackVariant> BY_ID
                = ByIdMap.sparse(FeatherbackVariant::getJoinedVariant, values(), AFRICAN_BROWN_KNIFEFISH);

        public static final StringRepresentable.EnumCodec<FeatherbackVariant> CODEC
                = StringRepresentable.fromEnum(FeatherbackVariant::values);

        public static FeatherbackVariant byId(int pId) {
            return BY_ID.apply(pId);
        }

        public static FeatherbackVariant byName(String pName) {
            return CODEC.byName(pName, AFRICAN_BROWN_KNIFEFISH);
        }
    }
}
