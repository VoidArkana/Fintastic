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
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.voidarkana.fintastic.common.entity.FintyEntities;
import net.voidarkana.fintastic.common.entity.custom.ai.FishBreedGoal;
import net.voidarkana.fintastic.common.entity.custom.base.AbstractSwimmingBottomDweller;
import net.voidarkana.fintastic.common.entity.custom.base.BreedableWaterAnimal;
import net.voidarkana.fintastic.common.item.FintyItems;
import net.voidarkana.fintastic.util.FintyTags;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

public class Pleco extends AbstractSwimmingBottomDweller {

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Pleco.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TICKS_ON_GROUND = SynchedEntityData.defineId(Pleco.class, EntityDataSerializers.INT);

    int prevTicksOnGround;

    public Pleco(EntityType<? extends BreedableWaterAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.5F);
    }

    private static final Ingredient FOOD_ITEMS = Ingredient.of(FintyTags.Items.FISH_FEED);

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new FishBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 2D, FOOD_ITEMS, false));
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.5D));

        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 8.0F, 1.6D, 1.4D, (entity) -> {
            if (entity instanceof Player player){
                return !player.isCreative() && !player.isSpectator() && !player.getItemBySlot(EquipmentSlot.HEAD).is(FintyItems.FISHING_HAT.get());
            }
            return false;}));

        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 50) {
            @Override
            public boolean canUse() {
                return Pleco.this.getWantsToSwim() && super.canUse();
            }

            @Override
            public boolean canContinueToUse() {
                return Pleco.this.getWantsToSwim() && super.canContinueToUse();
            }

        });
        this.goalSelector.addGoal(10, new RandomStrollGoal(this, 1, 80) {
            @Override
            public boolean canUse() {
                return !Pleco.this.getWantsToSwim() && Pleco.this.onGround() && super.canUse();
            }

            @Override
            public boolean canContinueToUse() {
                return !Pleco.this.getWantsToSwim() && super.canContinueToUse();
            }

            @Nullable
            @Override
            protected Vec3 getPosition() {
                return DefaultRandomPos.getPos(this.mob, 10, 1);
            }
        });

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
        this.entityData.define(TICKS_ON_GROUND, 0);
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

    @Override
    public void saveToBucketTag(ItemStack bucket) {
        CompoundTag compoundnbt = bucket.getOrCreateTag();
        Bucketable.saveDefaultDataToBucketTag(this, bucket);
        compoundnbt.putFloat("Health", this.getHealth());
        compoundnbt.putFloat("Variant", this.getVariant());
        compoundnbt.putInt("Age", this.getAge());
        compoundnbt.putBoolean("CanGrow", this.getCanGrowUp());
        if (this.hasCustomName()) {
            bucket.setHoverName(this.getCustomName());
        }
    }

    @Override
    public void loadFromBucketTag(CompoundTag pTag) {
        Bucketable.loadDefaultDataFromBucketTag(this, pTag);
        if (pTag.contains("Age"))
            this.setAge(pTag.getInt("Age"));
        if (pTag.contains("Variant"))
            this.setVariant(pTag.getInt("Variant"));
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        if (pReason == MobSpawnType.BUCKET && pDataTag != null && pDataTag.contains("Age", 3)) {
            if (pDataTag.contains("Age")) {
                this.setAge(pDataTag.getInt("Age"));}
            this.setCanGrowUp(pDataTag.getBoolean("CanGrow"));
            this.setVariant(pDataTag.getInt("Variant"));
        }else {
            this.setVariant(Util.getRandom(PlecoVariant.values(), random).getVariantID());
        }
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Nullable
    @Override
    public BreedableWaterAnimal getBreedOffspring(ServerLevel pLevel, BreedableWaterAnimal pOtherParent) {
        Pleco baby = FintyEntities.PLECO.get().create(pLevel);
        if (baby != null){
            baby.setFromBucket(true);
            baby.setVariant(this.getVariant());
        }
        return baby;
    }

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
    public boolean canMate(BreedableWaterAnimal pOtherAnimal) {
        Pleco otherGuy = (Pleco) pOtherAnimal;
        return this.getVariant() == otherGuy.getVariant() && super.canMate(pOtherAnimal);
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(FintyItems.PLECO_BUCKET.get());
    }

    @Override
    public boolean canFlop() {
        return false;
    }

    public enum PlecoVariant implements StringRepresentable {
        SAILFIN(0, "sailfin"),
        BLUE_EYED_PANAQUE(1, "blue_eyed_panaque"),
        BLUE_PHANTOM(2, "blue_phantom"),
        CACTUS(3, "cactus"),
        LUTEUS(4, "luteus");

        private final int variantID;
        private final String name;

        PlecoVariant(int variant, String name){
            this.variantID = variant;
            this.name = name;
        }

        public int getVariantID(){
            return this.variantID;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        public static final IntFunction<PlecoVariant> BY_ID
                = ByIdMap.sparse(PlecoVariant::getVariantID, values(), SAILFIN);

        public static final StringRepresentable.EnumCodec<PlecoVariant> CODEC
                = StringRepresentable.fromEnum(PlecoVariant::values);

        public static PlecoVariant byId(int pId) {
            return BY_ID.apply(pId);
        }

        public static PlecoVariant byName(String pName) {
            return CODEC.byName(pName, SAILFIN);
        }
    }
}
