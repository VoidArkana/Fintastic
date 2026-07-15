package net.voidarkana.fintastic.common.entity.custom;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.entity.FintyEntities;
import net.voidarkana.fintastic.common.entity.custom.ai.FishBreedGoal;
import net.voidarkana.fintastic.common.entity.custom.ai.FishJumpGoal;
import net.voidarkana.fintastic.common.entity.custom.base.BreedableWaterAnimal;
import net.voidarkana.fintastic.common.entity.custom.base.VariantSchoolingFish;
import net.voidarkana.fintastic.common.item.FintyItems;
import net.voidarkana.fintastic.util.FintyCommonConfig;
import net.voidarkana.fintastic.util.FintyTags;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

public class FintasticSalmon extends VariantSchoolingFish {

    private static final Ingredient FOOD_ITEMS = Ingredient.of(FintyTags.Items.FISH_FEED);
    private static final EntityDataAccessor<Integer> SIZE = SynchedEntityData.defineId(FintasticSalmon.class, EntityDataSerializers.INT);

    public FintasticSalmon(EntityType<? extends BreedableWaterAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.refreshDimensions();
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        this.refreshDimensions();
        super.onSyncedDataUpdated(pKey);
    }

    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        return super.getDimensions(pPose).scale(SalmonSize.byId(this.getSize()).sizeMultiplier*1.3f);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.8F);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new FishBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 2D, FOOD_ITEMS, false));
        this.goalSelector.addGoal(4, new FishJumpGoal(this, 15));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SIZE, SalmonSize.MEDIUM.getSizeNumber());
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Size", SalmonSize.byId(this.getSize()).getSerializedName());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setSize(SalmonSize.byName(compound.getString("Size")).getSizeNumber());
    }

    public int getSize() {
        return this.entityData.get(SIZE);
    }

    public void setSize(int size) {
        this.entityData.set(SIZE, size);
    }

    @Override
    public void saveToBucketTag(ItemStack bucket) {
        CompoundTag compoundnbt = bucket.getOrCreateTag();
        Bucketable.saveDefaultDataToBucketTag(this, bucket);
        compoundnbt.putFloat("Health", this.getHealth());
        compoundnbt.putInt("Variant", this.getVariant());
        compoundnbt.putInt("Age", this.getAge());
        compoundnbt.putString("Size", SalmonSize.byId(this.getSize()).getSerializedName());

        compoundnbt.putBoolean("CanGrow", this.getCanGrowUp());
        if (this.hasCustomName()) {
            bucket.setHoverName(this.getCustomName());
        }
    }

    @Override
    public void loadFromBucketTag(CompoundTag pTag) {
        Bucketable.loadDefaultDataFromBucketTag(this, pTag);

        if (pTag.contains("Variant")) {
            this.setVariant(pTag.getInt("Variant"));
        }
        if (pTag.contains("Age")) {
            this.setAge(pTag.getInt("Age"));
        }
        if (pTag.contains("Size")) {
            this.setSize(SalmonSize.byName(pTag.getString("Size")).getSizeNumber());
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {

        super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);

        if (pReason == MobSpawnType.BUCKET && pDataTag != null && pDataTag.contains("Variant", 3)) {
            this.setVariant(pDataTag.getInt("Variant"));
            if (pDataTag.contains("Age")) {
                this.setAge(pDataTag.getInt("Age"));
            }
            this.setSize(SalmonSize.byName(pDataTag.getString("Size")).getSizeNumber());
            this.setCanGrowUp(pDataTag.getBoolean("CanGrow"));
        }else if (pReason != MobSpawnType.SPAWN_EGG && !(pReason == MobSpawnType.BUCKET && pDataTag == null)){

            int variant;

            if (pSpawnData instanceof FishGroupData){
                FishGroupData fish$fishgroupdata = (FishGroupData)pSpawnData;
                variant = fish$fishgroupdata.variant;

                this.startFollowing(((FishGroupData)pSpawnData).leader);
            }else {

                if (pLevel.getBiome(this.blockPosition()).is(Biomes.FROZEN_OCEAN) || pLevel.getBiome(this.blockPosition()).is(Biomes.DEEP_FROZEN_OCEAN) ||
                        pLevel.getBiome(this.blockPosition()).is(Biomes.COLD_OCEAN) || pLevel.getBiome(this.blockPosition()).is(Biomes.DEEP_COLD_OCEAN) && this.getRandom().nextBoolean()){
                    variant = SalmonVariant.ARCTIC_CHAR.getVariant();
                }else {
                    do {
                        variant = Util.getRandom(SalmonVariant.values(), this.getRandom()).getVariant();
                    } while (variant == SalmonVariant.RAINBOW_TROUT.getVariant() && !pLevel.getBiome(this.blockPosition()).is(BiomeTags.IS_RIVER));
                }

                pSpawnData = new FishGroupData(this, variant);
            }

            this.setVariant(variant);
            this.setSize(Util.getRandom(SalmonSize.values(), this.getRandom()).getSizeNumber());
        }else {
            this.setVariant(Util.getRandom(SalmonVariant.values(), this.getRandom()).getVariant());
            this.setSize(Util.getRandom(SalmonSize.values(), this.getRandom()).getSizeNumber());
        }

        return pSpawnData;
    }

    public String getVariantName(){
        return SalmonVariant.byId(this.getVariant()).getSerializedName();
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(FintyItems.SALMON_BUCKET.get());
    }


    @Override
    public boolean canMate(BreedableWaterAnimal pOtherAnimal) {
        FintasticSalmon mate = (FintasticSalmon) pOtherAnimal;
        return super.canMate(pOtherAnimal) && this.getVariant() == mate.getVariant();
    }

    static class FishGroupData extends SchoolSpawnGroupData {
        final int variant;

        FishGroupData(FintasticSalmon pLeader, int pVariant) {
            super(pLeader);
            this.variant = pVariant;
        }
    }

    public enum SalmonVariant implements StringRepresentable {
        VANILLA(0, "vanilla"),
        ARCTIC_CHAR(1, "arctic_char"),
        ATLANTIC(2, "atlantic"),
        CHERRY(3, "cherry"),
        CHINOOK(4, "chinook"),
        PINK(5, "pink"),
        RAINBOW_TROUT(6, "rainbow_trout");

        private final int variant;
        private final String name;

        SalmonVariant(int variant, String name){
            this.variant = variant;
            this.name = name;
        }

        public int getVariant(){
            return this.variant;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        public static final IntFunction<SalmonVariant> BY_ID
                = ByIdMap.sparse(SalmonVariant::getVariant, values(), VANILLA);

        public static final EnumCodec<SalmonVariant> CODEC
                = StringRepresentable.fromEnum(SalmonVariant::values);

        public static SalmonVariant byId(int pId) {
            return BY_ID.apply(pId);
        }

        public static SalmonVariant byName(String pName) {
            return CODEC.byName(pName, VANILLA);
        }
    }
    public enum SalmonSize implements StringRepresentable {
        TINY(0, "tiny", 0.5f, 1),
        SMALL(1, "small", 0.75f, 0.5f),
        MEDIUM(2, "medium", 1, 0),
        BIG(3, "big", 1.25f, -0.25f),
        HUGE(4, "huge", 1.5f, -1.5f);

        private final int size;
        private final String name;
        private final float sizeMultiplier;
        private final float yBodyOffset;

        SalmonSize(int pSize, String name, float pMultiplier, float yBodyOffset){
            this.size = pSize;
            this.name = name;
            this.sizeMultiplier = pMultiplier;
            this.yBodyOffset = yBodyOffset;
        }

        public int getSizeNumber(){
            return this.size;
        }

        public float getSizeMultiplier(){
            return this.sizeMultiplier;
        }

        public float getyBodyOffset(){
            return this.yBodyOffset;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        public static final IntFunction<SalmonSize> BY_ID
                = ByIdMap.sparse(SalmonSize::getSizeNumber, values(), MEDIUM);

        public static final EnumCodec<SalmonSize> CODEC
                = StringRepresentable.fromEnum(SalmonSize::values);

        public static SalmonSize byId(int pId) {
            return BY_ID.apply(pId);
        }

        public static SalmonSize byName(String pName) {
            return CODEC.byName(pName, MEDIUM);
        }
    }

    public ResourceLocation getDefaultLootTable() {
        return new ResourceLocation(Fintastic.MOD_ID, "entities/salmon/"+SalmonSize.byId(this.getSize()).getSerializedName());
    }

    @Nullable
    @Override
    public BreedableWaterAnimal getBreedOffspring(ServerLevel pLevel, BreedableWaterAnimal pOtherParent) {
        FintasticSalmon otherParent = (FintasticSalmon) pOtherParent;
        FintasticSalmon baby = FintyEntities.SALMON.get().create(pLevel);

        if (baby != null){

            int lowerQuality = Math.min(this.getFeedQuality(), otherParent.getFeedQuality());
            int size;

            switch (lowerQuality){
                case 1:
                    if (this.random.nextBoolean()){
                        if (this.random.nextBoolean())
                            size = Math.min(4, Math.max(0, this.getSize() + this.getRandom().nextInt(-1, 2)));
                        else
                            size = Math.min(4, Math.max(0, otherParent.getSize() + this.getRandom().nextInt(-1, 2)));
                    }else {
                        size = Util.getRandom(SalmonSize.values(), this.getRandom()).getSizeNumber();
                    }
                    break;
                case 2:
                    if (this.random.nextBoolean())
                        size = Math.min(4, Math.max(0, this.getSize() + this.getRandom().nextInt(0, 2)));
                    else
                        size = Math.min(4, Math.max(0, otherParent.getSize() + this.getRandom().nextInt(0, 2)));
                    break;
                case 3:
                    if (this.getSize() >= otherParent.getSize())
                        size = Math.min(4, Math.max(0, this.getSize() + this.getRandom().nextInt(0, 2)));
                    else
                        size = Math.min(4, Math.max(0, otherParent.getSize() + this.getRandom().nextInt(0, 2)));
                    break;
                default:
                    if (this.random.nextBoolean()){
                        size = Math.min(4, Math.max(0, this.getSize() + this.getRandom().nextInt(-1, 2)));
                    }else {
                        size = Math.min(4, Math.max(0, otherParent.getSize() + this.getRandom().nextInt(-1, 2)));
                    }
                    break;
            }
            baby.setSize(size);
            baby.setVariant(this.getVariant());
            baby.setFromBucket(true);
        }

        return baby;
    }

    public static boolean checkSurfaceWaterAnimalSpawnRules(EntityType<? extends WaterAnimal> pWaterAnimal, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
        int i = pLevel.getSeaLevel();
        int j = i - 25;
        return FintyCommonConfig.ALLOW_FINTASTIC_SALMON.get() && pPos.getY() >= j && pPos.getY() <= i && pLevel.getFluidState(pPos.below()).is(FluidTags.WATER) && pLevel.getBlockState(pPos.above()).is(Blocks.WATER);
    }

}
