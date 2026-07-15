package net.voidarkana.fintastic.common.entity.custom;

import net.minecraft.core.component.DataComponents;
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
import net.minecraft.world.item.component.CustomData;
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

    public Sharkminnow(EntityType<? extends BreedableWaterAnimal> entityType, Level level) {
        super(entityType, level);
        this.refreshDimensions();
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        this.refreshDimensions();
        super.onSyncedDataUpdated(key);
    }

    @Override
    protected EntityDimensions getDefaultDimensions(Pose pose) {
        return switch (this.getVariant()){
            case 2, 3, 4 -> super.getDefaultDimensions(pose).scale(0.75F, 0.75F);
            default -> super.getDefaultDimensions(pose).scale(1.5F, 1F);
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

        if (reason != MobSpawnType.STRUCTURE && reason != MobSpawnType.SPAWN_EGG && reason != MobSpawnType.BUCKET){

            int model;

            if (spawnData instanceof FishGroupData fish$fishgroupdata){
                model = fish$fishgroupdata.variantModel;

                this.startFollowing(fish$fishgroupdata.leader);
            }else {

                if (level.getBiome(this.blockPosition()).is(BiomeTags.IS_JUNGLE)){
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

                spawnData = new FishGroupData(this, model);
            }

            this.setVariant(model);
        }else {

            this.setVariant(this.getRandom().nextInt(6));

        }

        return spawnData;
    }

    @Nullable
    @Override
    public BreedableWaterAnimal getBreedOffspring(ServerLevel level, BreedableWaterAnimal otherParent) {
        Sharkminnow baby = FintyEntities.SHARKMINNOW.get().create(level);
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
    public boolean canMate(BreedableWaterAnimal otherAnimal) {
        Sharkminnow mate = (Sharkminnow) otherAnimal;
        return super.canMate(otherAnimal) && this.getVariant() == mate.getVariant();
    }

    static class FishGroupData extends SchoolSpawnGroupData {
        final int variantModel;

        FishGroupData(Sharkminnow leader, int variantModel) {
            super(leader);
            this.variantModel = variantModel;
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

        public static SharkminnowVariant byId(int id) {
            return BY_ID.apply(id);
        }

        public static SharkminnowVariant byName(String name) {
            return CODEC.byName(name, BALA_SHARK);
        }
    }

}
