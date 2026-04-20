package net.voidarkana.fintastic.common.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
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

import java.util.function.IntFunction;

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
        return switch (this.getVariant()){
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
        }else if (pReason != MobSpawnType.STRUCTURE && pReason != MobSpawnType.SPAWN_EGG && !(pReason == MobSpawnType.BUCKET && pDataTag == null)){

            int model;

            if (pSpawnData instanceof FishGroupData){
                FishGroupData fish$fishgroupdata = (FishGroupData)pSpawnData;
                model = fish$fishgroupdata.variantModel;

                this.startFollowing(((FishGroupData)pSpawnData).leader);
            }else {

                if (pLevel.getBiome(this.blockPosition()).is(BiomeTags.IS_JUNGLE)){
                    int chance = this.random.nextInt(3);
                    model = switch (chance){
                        case 1 -> SharkminnowVariant.BLACK_LABEO.getVariant();
                        case 2 -> SharkminnowVariant.CIGAR_SHARK.getVariant();
                        default -> SharkminnowVariant.BALA_SHARK.getVariant();
                    };
                }else {
                    int chance = this.random.nextInt(3);
                    model = switch (chance){
                        case 1 -> SharkminnowVariant.RUBY_SHARK.getVariant();
                        case 2 -> SharkminnowVariant.RAINBOW_SHARK.getVariant();
                        default -> SharkminnowVariant.HIGHFIN_SHARK.getVariant();
                    };
                }

                pSpawnData = new FishGroupData(this, model);
            }

            this.setVariant(model);
        }else {

            this.setVariant(this.getRandom().nextInt(6));

        }

        return pSpawnData;
    }

    @Nullable
    @Override
    public BreedableWaterAnimal getBreedOffspring(ServerLevel pLevel, BreedableWaterAnimal pOtherParent) {
        Sharkminnow baby = FintyEntities.SHARKMINNOW.get().create(pLevel);
        if (baby != null){
            baby.setVariant(this.getVariant());
            baby.setFromBucket(true);
        }
        return baby;
    }

    public String getVariantName(){
        return SharkminnowVariant.byId(this.getVariant()).getSerializedName();
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(FintyItems.FRESHWATER_SHARK_BUCKET.get());
    }


    @Override
    public boolean canMate(BreedableWaterAnimal pOtherAnimal) {
        Sharkminnow mate = (Sharkminnow) pOtherAnimal;
        return super.canMate(pOtherAnimal) && this.getVariant() == mate.getVariant();
    }

    static class FishGroupData extends SchoolSpawnGroupData {
        final int variantModel;

        FishGroupData(Sharkminnow pLeader, int pVariantModel) {
            super(pLeader);
            this.variantModel = pVariantModel;
        }
    }

    public enum SharkminnowVariant implements StringRepresentable {
        BALA_SHARK(0, "bala_shark"),
        HIGHFIN_SHARK(1, "highfin_shark"),
        BLACK_LABEO(2, "black_labeo"),
        RUBY_SHARK(3, "ruby_shark"),
        RAINBOW_SHARK(4, "rainbow_shark"),
        CIGAR_SHARK(5, "cigar_shark");

        private final int variant;
        private final String name;

        SharkminnowVariant(int variant, String name){
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

        public static final IntFunction<SharkminnowVariant> BY_ID
                = ByIdMap.sparse(SharkminnowVariant::getVariant, values(), BALA_SHARK);

        public static final StringRepresentable.EnumCodec<SharkminnowVariant> CODEC
                = StringRepresentable.fromEnum(SharkminnowVariant::values);

        public static SharkminnowVariant byId(int pId) {
            return BY_ID.apply(pId);
        }

        public static SharkminnowVariant byName(String pName) {
            return CODEC.byName(pName, BALA_SHARK);
        }
    }

}
