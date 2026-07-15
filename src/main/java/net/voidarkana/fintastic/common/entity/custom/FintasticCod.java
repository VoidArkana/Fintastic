package net.voidarkana.fintastic.common.entity.custom;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
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
import net.voidarkana.fintastic.common.entity.FintyEntities;
import net.voidarkana.fintastic.common.entity.custom.ai.FishBreedGoal;
import net.voidarkana.fintastic.common.entity.custom.base.BreedableWaterAnimal;
import net.voidarkana.fintastic.common.entity.custom.base.VariantSchoolingFish;
import net.voidarkana.fintastic.common.item.FintyItems;
import net.voidarkana.fintastic.util.FintyCommonConfig;
import net.voidarkana.fintastic.util.FintyTags;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

public class FintasticCod extends VariantSchoolingFish {

    private static final Ingredient FOOD_ITEMS = Ingredient.of(FintyTags.Items.FISH_FEED);

    public FintasticCod(EntityType<? extends BreedableWaterAnimal> entityType, Level level) {
        super(entityType, level);
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
    }

    @Override
    public void saveToBucketTag(ItemStack bucket) {
        Bucketable.saveDefaultDataToBucketTag(this, bucket);
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, bucket, compoundnbt -> {
            compoundnbt.putFloat("Health", this.getHealth());
            compoundnbt.putInt("Variant", this.getVariant());
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

        if (tag.contains("Variant")) {
            this.setVariant(tag.getInt("Variant"));
        }
        if (tag.contains("Age")) {
            this.setAge(tag.getInt("Age"));
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

                if (level.getBiome(this.blockPosition()).is(Biomes.FROZEN_OCEAN) || level.getBiome(this.blockPosition()).is(Biomes.DEEP_FROZEN_OCEAN)){
                    variant = this.getRandom().nextBoolean() ? CodVariant.VANILLA.getVariant() : CodVariant.WHITING_POUT.getVariant();
                }else if (level.getBiome(this.blockPosition()).is(Biomes.COLD_OCEAN) || level.getBiome(this.blockPosition()).is(Biomes.DEEP_COLD_OCEAN)){
                    variant = Util.getRandom(CodVariant.values(), this.getRandom()).getVariant();
                }else {
                    variant = this.getRandom().nextBoolean() ? CodVariant.VANILLA.getVariant() : CodVariant.POLLOCK.getVariant();
                }

                spawnData = new FishGroupData(this, variant);
            }

            this.setVariant(variant);
        }else {

            this.setVariant(Util.getRandom(CodVariant.values(), this.getRandom()).getVariant());

        }

        return spawnData;
    }

    @Nullable
    @Override
    public BreedableWaterAnimal getBreedOffspring(ServerLevel level, BreedableWaterAnimal otherParent) {
        FintasticCod baby = FintyEntities.COD.get().create(level);
        if (baby != null){
            baby.setVariant(this.getVariant());
            baby.setFromBucket(true);
        }
        return baby;
    }

    public String getVariantName(){
        return CodVariant.byId(this.getVariant()).getSerializedName();
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(FintyItems.COD_BUCKET.get());
    }


    @Override
    public boolean canMate(BreedableWaterAnimal otherAnimal) {
        FintasticCod mate = (FintasticCod) otherAnimal;
        return super.canMate(otherAnimal) && this.getVariant() == mate.getVariant();
    }

    static class FishGroupData extends SchoolSpawnGroupData {
        final int variant;

        FishGroupData(FintasticCod leader, int variant) {
            super(leader);
            this.variant = variant;
        }
    }

    public enum CodVariant implements StringRepresentable {
        VANILLA(0, "vanilla"),
        HADDOCK(1, "haddock"),
        POLLOCK(2, "pollock"),
        WHITING_POUT(3, "whiting_pout");

        private final int variant;
        private final String name;

        CodVariant(int variant, String name){
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

        public static final IntFunction<CodVariant> BY_ID
                = ByIdMap.sparse(CodVariant::getVariant, values(), VANILLA);

        public static final EnumCodec<CodVariant> CODEC
                = StringRepresentable.fromEnum(CodVariant::values);

        public static CodVariant byId(int id) {
            return BY_ID.apply(id);
        }

        public static CodVariant byName(String name) {
            return CODEC.byName(name, VANILLA);
        }
    }

    public static boolean checkSurfaceWaterAnimalSpawnRules(EntityType<? extends WaterAnimal> waterAnimal, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        int i = level.getSeaLevel();
        int j = i - 25;
        return FintyCommonConfig.ALLOW_FINTASTIC_COD.get() && pos.getY() >= j && pos.getY() <= i && level.getFluidState(pos.below()).is(FluidTags.WATER) && level.getBlockState(pos.above()).is(Blocks.WATER);
    }
}
