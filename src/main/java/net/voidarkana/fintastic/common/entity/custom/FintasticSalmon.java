package net.voidarkana.fintastic.common.entity.custom;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
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
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootTable;
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

    public FintasticSalmon(EntityType<? extends BreedableWaterAnimal> entityType, Level level) {
        super(entityType, level);
        this.refreshDimensions();
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        this.refreshDimensions();
        super.onSyncedDataUpdated(key);
    }

    @Override
    protected EntityDimensions getDefaultDimensions(Pose pose) {
        return super.getDefaultDimensions(pose).scale(SalmonSize.byId(this.getSize()).sizeMultiplier*1.3f);
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
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SIZE, SalmonSize.MEDIUM.getSizeNumber());
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
        Bucketable.saveDefaultDataToBucketTag(this, bucket);
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, bucket, compoundnbt -> {
            compoundnbt.putFloat("Health", this.getHealth());
            compoundnbt.putInt("Variant", this.getVariant());
            compoundnbt.putInt("Age", this.getAge());
            compoundnbt.putString("Size", SalmonSize.byId(this.getSize()).getSerializedName());

            compoundnbt.putBoolean("CanGrow", this.getCanGrowUp());
        });
        if (this.hasCustomName()) {
            bucket.set(DataComponents.CUSTOM_NAME, this.getCustomName());
        }
    }

    @Override
    public void loadFromBucketTag(CompoundTag tag) {
        Bucketable.loadDefaultDataFromBucketTag(this, tag);

        if (tag.contains("Variant")) {
            this.setVariant(tag.getInt("Variant"));
        }
        if (tag.contains("Age")) {
            this.setAge(tag.getInt("Age"));
        }
        if (tag.contains("Size")) {
            this.setSize(SalmonSize.byName(tag.getString("Size")).getSizeNumber());
        }
        if (tag.contains("CanGrow")) {
            this.setCanGrowUp(tag.getBoolean("CanGrow"));
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData) {

        super.finalizeSpawn(level, difficulty, reason, spawnData);

        if (reason != MobSpawnType.SPAWN_EGG && reason != MobSpawnType.BUCKET){

            int variant;

            if (spawnData instanceof FishGroupData fish$fishgroupdata){
                variant = fish$fishgroupdata.variant;

                this.startFollowing(fish$fishgroupdata.leader);
            }else {

                if (level.getBiome(this.blockPosition()).is(Biomes.FROZEN_OCEAN) || level.getBiome(this.blockPosition()).is(Biomes.DEEP_FROZEN_OCEAN) ||
                        level.getBiome(this.blockPosition()).is(Biomes.COLD_OCEAN) || level.getBiome(this.blockPosition()).is(Biomes.DEEP_COLD_OCEAN) && this.getRandom().nextBoolean()){
                    variant = SalmonVariant.ARCTIC_CHAR.getVariant();
                }else {
                    do {
                        variant = Util.getRandom(SalmonVariant.values(), this.getRandom()).getVariant();
                    } while (variant == SalmonVariant.RAINBOW_TROUT.getVariant() && !level.getBiome(this.blockPosition()).is(BiomeTags.IS_RIVER));
                }

                spawnData = new FishGroupData(this, variant);
            }

            this.setVariant(variant);
            this.setSize(Util.getRandom(SalmonSize.values(), this.getRandom()).getSizeNumber());
        }else {
            this.setVariant(Util.getRandom(SalmonVariant.values(), this.getRandom()).getVariant());
            this.setSize(Util.getRandom(SalmonSize.values(), this.getRandom()).getSizeNumber());
        }

        return spawnData;
    }

    public String getVariantName(){
        return SalmonVariant.byId(this.getVariant()).getSerializedName();
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(FintyItems.SALMON_BUCKET.get());
    }


    @Override
    public boolean canMate(BreedableWaterAnimal otherAnimal) {
        FintasticSalmon mate = (FintasticSalmon) otherAnimal;
        return super.canMate(otherAnimal) && this.getVariant() == mate.getVariant();
    }

    static class FishGroupData extends SchoolSpawnGroupData {
        final int variant;

        FishGroupData(FintasticSalmon leader, int variant) {
            super(leader);
            this.variant = variant;
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

        public static SalmonVariant byId(int id) {
            return BY_ID.apply(id);
        }

        public static SalmonVariant byName(String name) {
            return CODEC.byName(name, VANILLA);
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

        SalmonSize(int size, String name, float multiplier, float yBodyOffset){
            this.size = size;
            this.name = name;
            this.sizeMultiplier = multiplier;
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

        public static SalmonSize byId(int id) {
            return BY_ID.apply(id);
        }

        public static SalmonSize byName(String name) {
            return CODEC.byName(name, MEDIUM);
        }
    }

    public ResourceKey<LootTable> getDefaultLootTable() {
        return ResourceKey.create(Registries.LOOT_TABLE,
                Fintastic.location("entities/salmon/"+SalmonSize.byId(this.getSize()).getSerializedName()));
    }

    @Nullable
    @Override
    public BreedableWaterAnimal getBreedOffspring(ServerLevel level, BreedableWaterAnimal mate) {
        FintasticSalmon otherParent = (FintasticSalmon) mate;
        FintasticSalmon baby = FintyEntities.SALMON.get().create(level);

        if (baby != null){

            int lowerQuality = Math.min(this.getFeedQuality(), otherParent.getFeedQuality());
            int size;

            switch (lowerQuality){
                case 1:
                    if (this.random.nextBoolean()){
                        if (this.random.nextBoolean())
                            size = Math.clamp(this.getSize() + this.getRandom().nextInt(-1, 2), 0, 4);
                        else
                            size = Math.clamp(otherParent.getSize() + this.getRandom().nextInt(-1, 2), 0, 4);
                    }else {
                        size = Util.getRandom(SalmonSize.values(), this.getRandom()).getSizeNumber();
                    }
                    break;
                case 2:
                    if (this.random.nextBoolean())
                        size = Math.clamp(this.getSize() + this.getRandom().nextInt(0, 2), 0, 4);
                    else
                        size = Math.clamp(otherParent.getSize() + this.getRandom().nextInt(0, 2), 0, 4);
                    break;
                case 3:
                    if (this.getSize() >= otherParent.getSize())
                        size = Math.clamp(this.getSize() + this.getRandom().nextInt(0, 2), 0, 4);
                    else
                        size = Math.clamp(otherParent.getSize() + this.getRandom().nextInt(0, 2), 0, 4);
                    break;
                default:
                    if (this.random.nextBoolean()){
                        size = Math.clamp(this.getSize() + this.getRandom().nextInt(-1, 2), 0, 4);
                    }else {
                        size = Math.clamp(otherParent.getSize() + this.getRandom().nextInt(-1, 2), 0, 4);
                    }
                    break;
            }
            baby.setSize(size);
            baby.setVariant(this.getVariant());
            baby.setFromBucket(true);
        }

        return baby;
    }

    public static boolean checkSurfaceWaterAnimalSpawnRules(EntityType<? extends WaterAnimal> waterAnimal, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        int i = level.getSeaLevel();
        int j = i - 25;
        return FintyCommonConfig.ALLOW_FINTASTIC_SALMON.get() && pos.getY() >= j && pos.getY() <= i && level.getFluidState(pos.below()).is(FluidTags.WATER) && level.getBlockState(pos.above()).is(Blocks.WATER);
    }

}
