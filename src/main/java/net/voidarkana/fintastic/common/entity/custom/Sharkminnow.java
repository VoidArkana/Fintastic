package net.voidarkana.fintastic.common.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.voidarkana.fintastic.common.entity.FintyEntities;
import net.voidarkana.fintastic.common.entity.custom.ai.FishBreedGoal;
import net.voidarkana.fintastic.common.entity.custom.ai.FishJumpGoal;
import net.voidarkana.fintastic.common.entity.custom.base.BreedableWaterAnimal;
import net.voidarkana.fintastic.common.entity.custom.base.VariantSchoolingFish;
import net.voidarkana.fintastic.common.item.FintyItems;
import net.voidarkana.fintastic.util.FintyTags;
import org.jetbrains.annotations.Nullable;

public class Sharkminnow extends VariantSchoolingFish {

    private static final Ingredient FOOD_ITEMS = Ingredient.of(FintyTags.Items.FISH_FEED);

    public Sharkminnow(EntityType<? extends BreedableWaterAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.refreshDimensions();
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        this.refreshDimensions();
        super.onSyncedDataUpdated(pKey);
    }

    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        return switch (this.getVariantModel()){
            case 2, 3, 4 -> super.getDimensions(pPose).scale(0.75F, 0.75F);
            default -> super.getDimensions(pPose).scale(1.5F, 1F);
        };
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0D)
                .add(Attributes.MOVEMENT_SPEED, 1F);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new FishBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 2D, FOOD_ITEMS, false));
        this.goalSelector.addGoal(5, new FishJumpGoal(this, 15));
    }

    @Override
    public void saveToBucketTag(ItemStack bucket) {
        CompoundTag compoundnbt = bucket.getOrCreateTag();
        Bucketable.saveDefaultDataToBucketTag(this, bucket);
        compoundnbt.putFloat("Health", this.getHealth());
        compoundnbt.putInt("VariantModel", this.getVariantModel());
        compoundnbt.putInt("Age", this.getAge());

        compoundnbt.putBoolean("CanGrow", this.getCanGrowUp());
        if (this.hasCustomName()) {
            bucket.setHoverName(this.getCustomName());
        }
    }

    @Override
    public void loadFromBucketTag(CompoundTag pTag) {
        Bucketable.loadDefaultDataFromBucketTag(this, pTag);
        this.setVariantModel(pTag.getInt("VariantModel"));
        if (pTag.contains("Age")) {
            this.setAge(pTag.getInt("Age"));
        }
        this.setCanGrowUp(pTag.getBoolean("CanGrow"));
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {

        super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);

        if (pReason == MobSpawnType.BUCKET && pDataTag != null && pDataTag.contains("VariantModel", 3)) {
            this.setVariantModel(pDataTag.getInt("VariantModel"));
            if (pDataTag.contains("Age")) {
                this.setAge(pDataTag.getInt("Age"));
            }
            this.setCanGrowUp(pDataTag.getBoolean("CanGrow"));
        }else if (pReason != MobSpawnType.SPAWN_EGG && !(pReason == MobSpawnType.BUCKET && pDataTag == null)){

            int model;

            if (pSpawnData instanceof FishGroupData){
                FishGroupData fish$fishgroupdata = (FishGroupData)pSpawnData;
                model = fish$fishgroupdata.variantModel;

                this.startFollowing(((FishGroupData)pSpawnData).leader);
            }else {

                if (pLevel.getBiome(this.blockPosition()).is(BiomeTags.IS_JUNGLE)){
                    int chance = this.random.nextInt(3);
                    model = switch (chance){
                        case 1 -> 2;
                        case 2 -> 5;
                        default -> 0;
                    };
                }else {
                    int chance = this.random.nextInt(3);
                    model = switch (chance){
                        case 1 -> 3;
                        case 2 -> 4;
                        default -> 1;
                    };
                }

                pSpawnData = new FishGroupData(this, model);
            }

            this.setVariantModel(model);
        }else {

            this.setVariantModel(this.getRandom().nextInt(6));

        }

        return pSpawnData;
    }

    @Nullable
    @Override
    public BreedableWaterAnimal getBreedOffspring(ServerLevel pLevel, BreedableWaterAnimal pOtherParent) {
        Sharkminnow baby = FintyEntities.SHARKMINNOW.get().create(pLevel);
        if (baby != null){
            baby.setVariantModel(this.getVariantModel());
            baby.setFromBucket(true);
        }
        return baby;
    }

    public String getVariantName(){
        return switch (this.getVariantModel()){
            case 1 -> "highfin_shark";
            case 2 -> "black_labeo";
            case 3 -> "ruby_shark";
            case 4 -> "rainbow_shark";
            case 5 -> "cigar_shark";
            default -> "bala_shark";
        };
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(FintyItems.FRESHWATER_SHARK_BUCKET.get());
    }


    @Override
    public boolean canMate(BreedableWaterAnimal pOtherAnimal) {
        Sharkminnow mate = (Sharkminnow) pOtherAnimal;
        return super.canMate(pOtherAnimal) && this.getVariantModel() == mate.getVariantModel();
    }

    static class FishGroupData extends SchoolSpawnGroupData {
        final int variantModel;

        FishGroupData(Sharkminnow pLeader, int pVariantModel) {
            super(pLeader);
            this.variantModel = pVariantModel;
        }
    }

}
