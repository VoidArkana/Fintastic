package net.voidarkana.fintastic.common.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.HitResult;
import net.voidarkana.fintastic.common.entity.FintyEntities;
import net.voidarkana.fintastic.common.entity.custom.ai.FishBreedGoal;
import net.voidarkana.fintastic.common.entity.custom.base.BreedableWaterAnimal;
import net.voidarkana.fintastic.common.entity.custom.base.BucketableFishEntity;
import net.voidarkana.fintastic.common.item.FintyItems;
import net.voidarkana.fintastic.util.FintyTags;
import org.jetbrains.annotations.Nullable;

public class Coelacanth extends BucketableFishEntity {

    private static final Ingredient FOOD_ITEMS = Ingredient.of(FintyTags.Items.FISH_FEED);

    public boolean isFood(ItemStack stack) {
        return FOOD_ITEMS.test(stack);
    }

    public Coelacanth(EntityType<? extends BucketableFishEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new FishBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 2D, FOOD_ITEMS, false));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.6F);
    }

    @Override
    public @Nullable BreedableWaterAnimal getBreedOffspring(ServerLevel level, BreedableWaterAnimal otherParent) {
        Coelacanth baby = FintyEntities.COELACANTH.get().create(level);
        if (baby != null){
            baby.setFromBucket(true);
        }
        return baby;
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(FintyItems.COELACANTH_SPAWN_EGG.get());
    }

    public BlockPos getLightPosition() {
        BlockPos pos = new BlockPos((int) this.position().x, (int) this.position().y, (int) this.position().z);
        if (!level().getBlockState(pos).canOcclude()) {
            return pos.above();
        }
        return pos;
    }


    public static boolean checkCoelacanthSpawnRules(EntityType<? extends WaterAnimal> waterAnimal, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        int i = level.getSeaLevel();
        int j = i - 13;
        return (level.getBiome(pos).is(Biomes.WARM_OCEAN) && pos.getY() >= j && pos.getY() <= i && level.getFluidState(pos.below()).is(FluidTags.WATER) && level.getBlockState(pos.above()).is(Blocks.WATER))
                || (pos.getY() <= level.getSeaLevel() - 20 && pos.getY() > -10 && level.getBlockState(pos).is(Blocks.WATER));
    }

    @Override
    public void saveToBucketTag(ItemStack bucket) {
        Bucketable.saveDefaultDataToBucketTag(this, bucket);
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, bucket, compoundnbt -> {
            compoundnbt.putFloat("Health", this.getHealth());
            compoundnbt.putInt("Age", this.getAge());
            compoundnbt.putBoolean("CanGrow", this.getCanGrowUp());
        });
        if (this.hasCustomName()) {
            bucket.set(DataComponents.CUSTOM_NAME, this.getCustomName());
        }
    }

    @Override
    public void loadFromBucketTag(CompoundTag tag) {
        Bucketable.loadDefaultDataFromBucketTag(this, tag);

        if (tag.contains("Age")) {
            this.setAge(tag.getInt("Age"));
        }
        if (tag.contains("CanGrow")) {
            this.setCanGrowUp(tag.getBoolean("CanGrow"));
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData) {

        if (reason == MobSpawnType.BUCKET){
            this.setAge(-24000);
            this.setAirSupply(this.getMaxAirSupply());
        }

        return super.finalizeSpawn(level, difficulty, reason, spawnData);
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(FintyItems.COELACANTH_BUCKET.get());
    }

    @Override
    public boolean canBeBucketed() {
        return this.isBaby();
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return this.getDepthPathfindingFavor(pos, level);
    }

    float getDepthPathfindingFavor(BlockPos pos, LevelReader world) {
        int y = pos.getY() + Math.abs(world.getMinBuildHeight()) + 30;
        return 1f / (y < 0 ? 1 : y);
    }
}
