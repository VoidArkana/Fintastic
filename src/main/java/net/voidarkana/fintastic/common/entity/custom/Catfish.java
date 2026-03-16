package net.voidarkana.fintastic.common.entity.custom;

import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.common.Tags;
import net.voidarkana.fintastic.common.entity.FintyEntities;
import net.voidarkana.fintastic.common.entity.custom.ai.FishBreedGoal;
import net.voidarkana.fintastic.common.entity.custom.base.BreedableWaterAnimal;
import net.voidarkana.fintastic.common.entity.custom.base.BucketableFishEntity;
import net.voidarkana.fintastic.common.item.FintyItems;
import net.voidarkana.fintastic.util.FintyTags;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

public class Catfish extends BucketableFishEntity {

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Catfish.class, EntityDataSerializers.INT);

    private static final Ingredient FOOD_ITEMS = Ingredient.of(FintyTags.Items.FISH_FEED);

    public boolean isFood(ItemStack pStack) {
        return FOOD_ITEMS.test(pStack);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        this.refreshDimensions();
        super.onSyncedDataUpdated(pKey);
    }

    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        CatfishVariant variant = CatfishVariant.byId(this.getVariant());
        return switch (variant.getModel()){
            case 0 ->super.getDimensions(pPose).scale(1F, 1.1F);
            case 1, 2 ->super.getDimensions(pPose).scale(0.8F, 0.8F);
            case 4 ->super.getDimensions(pPose).scale(2F, 1.5F);
            case 5 ->super.getDimensions(pPose).scale(1.75F, 0.8F);
            default ->super.getDimensions(pPose);
        };
    }

    public Catfish(EntityType<? extends BreedableWaterAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.refreshDimensions();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.8F);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new FishBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 2D, FOOD_ITEMS, false));
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
    public boolean canBeBucketed() {
        return (this.getVariant()!=50 && this.getVariant()!=40) || ((this.getVariant() == 50 || this.getVariant() == 40) && this.isBaby());
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
            this.setCanGrowUp(pTag.getBoolean("CanGrowUp"));
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
            if (pReason == MobSpawnType.SPAWN_EGG || (pReason == MobSpawnType.BUCKET && pDataTag == null)){
                CatfishVariant variant = Util.getRandom(CatfishVariant.values(), this.random);
                this.setVariant(variant.getJoinedVariant());

                if (variant == CatfishVariant.PIRAIBA || variant == CatfishVariant.DEVIL_GOONCH){
                    this.setAge(-24000);
                }
            }
            else {
                if (pLevel.getBiome(this.blockPosition()).is(Tags.Biomes.IS_SWAMP)){
                    switch (this.random.nextInt(9)){
                        case 1:
                            this.setVariant(CatfishVariant.SUTCHI_PANGASIUS.getJoinedVariant());
                            break;
                        case 2:
                            this.setVariant(CatfishVariant.BLACK_EARED_PANGASIUS.getJoinedVariant());
                            break;
                        case 3:
                            this.setVariant(CatfishVariant.BLUE.getJoinedVariant());
                            break;
                        case 4:
                            this.setVariant(CatfishVariant.BULLHEAD.getJoinedVariant());
                            break;
                        case 5:
                            this.setVariant(CatfishVariant.CHANNEL.getJoinedVariant());
                            break;
                        case 6:
                            this.setVariant(CatfishVariant.ASIAN_REDTAIL.getJoinedVariant());
                            break;
                        case 7:
                            this.setVariant(CatfishVariant.SPOTTED_YELLOW_PIMELODUS.getJoinedVariant());
                            break;
                        case 8:
                            this.setVariant(CatfishVariant.FLATHEAD.getJoinedVariant());
                            break;
                        default:
                            this.setVariant(CatfishVariant.DEVIL_GOONCH.getJoinedVariant());
                    }
                } else if (pLevel.getBiome(this.blockPosition()).is(BiomeTags.IS_RIVER)){
                    switch (this.random.nextInt(5)){
                        case 1:
                            this.setVariant(CatfishVariant.BLUE.getJoinedVariant());
                            break;
                        case 2:
                            this.setVariant(CatfishVariant.BULLHEAD.getJoinedVariant());
                            break;
                        case 3:
                            this.setVariant(CatfishVariant.CHANNEL.getJoinedVariant());
                            break;
                        case 4:
                            this.setVariant(CatfishVariant.SPOTTED_YELLOW_PIMELODUS.getJoinedVariant());
                            break;
                        default:
                            this.setVariant(CatfishVariant.FLATHEAD.getJoinedVariant());
                    }
                } else if (pLevel.getBiome(this.blockPosition()).is(BiomeTags.IS_JUNGLE)){
                    switch (this.random.nextInt(6)){
                        case 1:
                            this.setVariant(CatfishVariant.REDTAIL.getJoinedVariant());
                            break;
                        case 2:
                            this.setVariant(CatfishVariant.ZUNGARO.getJoinedVariant());
                            break;
                        case 3:
                            this.setVariant(CatfishVariant.TIGERSTRIPED.getJoinedVariant());
                            break;
                        case 4:
                            this.setVariant(CatfishVariant.ASIAN_REDTAIL.getJoinedVariant());
                            break;
                        case 5:
                            this.setVariant(CatfishVariant.SPOTTED_YELLOW_PIMELODUS.getJoinedVariant());
                            break;
                        default:
                            this.setVariant(CatfishVariant.PIRAIBA.getJoinedVariant());
                    }
                }else if (pLevel.getBiome(this.blockPosition()).is(BiomeTags.HAS_OCEAN_RUIN_WARM)){
                    if (this.getRandom().nextBoolean())
                        this.setVariant(CatfishVariant.GAFFTOPSAIL.getJoinedVariant());
                    else
                        this.setVariant(CatfishVariant.COLUMBIAN_SHARK.getJoinedVariant());
                }
            }
        }

        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Nullable
    @Override
    public BreedableWaterAnimal getBreedOffspring(ServerLevel pLevel, BreedableWaterAnimal pOtherParent) {
        Catfish baby = FintyEntities.CATFISH.get().create(pLevel);
        if (baby != null){
            baby.setVariant(this.getVariant());
            baby.setFromBucket(true);
        }
        return baby;
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(FintyItems.CATFISH_BUCKET.get());
    }

    public boolean hurt(DamageSource pSource, float pAmount) {
        if (this.level().isClientSide) {
            return false;
        } else {
            if (!pSource.is(DamageTypeTags.AVOIDS_GUARDIAN_THORNS) && !pSource.is(DamageTypes.THORNS)) {
                Entity entity = pSource.getDirectEntity();
                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity = (LivingEntity)entity;
                    livingentity.hurt(this.damageSources().thorns(this), 2.0F);
                }
            }
            return super.hurt(pSource, pAmount);
        }
    }

    @Override
    public boolean canMate(BreedableWaterAnimal pOtherAnimal) {
        Catfish mate = (Catfish) pOtherAnimal;
        return super.canMate(pOtherAnimal) && this.getVariant() == mate.getVariant();
    }

    public enum CatfishVariant implements StringRepresentable{
        REDTAIL(0, "redtail"),
        ZUNGARO(1, "zungaro"),

        GAFFTOPSAIL(10, "gafftopsail"),
        BULLHEAD(11, "bullhead"),
        COLUMBIAN_SHARK(12, "columbian_shark"),
        BLUE(13, "blue"),
        CHANNEL(14, "channel"),

        FLATHEAD(20, "flathead"),
        TIGERSTRIPED(21, "tigerstriped"),
        SPOTTED_YELLOW_PIMELODUS(22, "spotted_yellow_pimelodus"),
        ASIAN_REDTAIL(23, "asian_redtail"),

        SUTCHI_PANGASIUS(30,"sutchi_pangasius"),
        BLACK_EARED_PANGASIUS(31,"black_eared_pangasius"),

        PIRAIBA(40,"piraiba"),

        DEVIL_GOONCH(50, "devil_goonch");

        private final int joinedVariant;
        private final String name;

        CatfishVariant(int variant, String name){
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

        @Override
        public String getSerializedName() {
            return this.name;
        }

        public static final IntFunction<Catfish.CatfishVariant> BY_ID
                = ByIdMap.sparse(Catfish.CatfishVariant::getJoinedVariant, values(), REDTAIL);

        public static final StringRepresentable.EnumCodec<Catfish.CatfishVariant> CODEC
                = StringRepresentable.fromEnum(Catfish.CatfishVariant::values);

        public static Catfish.CatfishVariant byId(int pId) {
            return BY_ID.apply(pId);
        }

        public static Catfish.CatfishVariant byName(String pName) {
            return CODEC.byName(pName, REDTAIL);
        }
    }
}
