package net.voidarkana.fintastic.common.entity.custom;

import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.voidarkana.fintastic.common.entity.FintyEntities;
import net.voidarkana.fintastic.common.entity.custom.ai.FishBreedGoal;
import net.voidarkana.fintastic.common.entity.custom.base.BreedableWaterAnimal;
import net.voidarkana.fintastic.common.entity.custom.base.VariantSchoolingFish;
import net.voidarkana.fintastic.common.item.FintyItems;
import net.voidarkana.fintastic.util.FintyTags;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

public class FintasticSalmon extends VariantSchoolingFish {

    private static final Ingredient FOOD_ITEMS = Ingredient.of(FintyTags.Items.FISH_FEED);

    public FintasticSalmon(EntityType<? extends BreedableWaterAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.6F);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new FishBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 2D, FOOD_ITEMS, false));
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

        if (pTag.contains("Variant")) {
            this.setVariant(pTag.getInt("Variant"));
        }
        if (pTag.contains("Age")) {
            this.setAge(pTag.getInt("Age"));
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
        }else {

            this.setVariant(Util.getRandom(SalmonVariant.values(), this.getRandom()).getVariant());

        }

        return pSpawnData;
    }

    @Nullable
    @Override
    public BreedableWaterAnimal getBreedOffspring(ServerLevel pLevel, BreedableWaterAnimal pOtherParent) {
        FintasticSalmon baby = FintyEntities.SALMON.get().create(pLevel);
        if (baby != null){
            baby.setVariant(this.getVariant());
            baby.setFromBucket(true);
        }
        return baby;
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

}
