package net.voidarkana.fintastic.common.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.common.Tags;
import net.voidarkana.fintastic.common.entity.YAFMEntities;
import net.voidarkana.fintastic.common.entity.custom.ai.FishBreedGoal;
import net.voidarkana.fintastic.common.entity.custom.base.BreedableWaterAnimal;
import net.voidarkana.fintastic.common.entity.custom.base.VariantSchoolingFish;
import net.voidarkana.fintastic.common.entity.custom.base.BucketableFishEntity;
import net.voidarkana.fintastic.common.item.YAFMItems;
import net.voidarkana.fintastic.util.YAFMTags;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;


public class MinnowEntity extends VariantSchoolingFish implements GeoEntity {

    protected static final RawAnimation SWIM = RawAnimation.begin().thenLoop("animation.genericfish.swim");
    protected static final RawAnimation FLOP = RawAnimation.begin().thenLoop("animation.genericfish.flop");

    public MinnowEntity(EntityType<? extends BucketableFishEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    private static final Ingredient FOOD_ITEMS = Ingredient.of(YAFMTags.Items.FISH_FEED);

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new FishBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 2D, FOOD_ITEMS, false));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.8F);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setVariantModel(compound.getInt("VariantModel"));
        this.setVariantSkin(compound.getInt("VariantSkin"));
    }

    @Override
    public void saveToBucketTag(ItemStack bucket) {
        CompoundTag compoundnbt = bucket.getOrCreateTag();
        Bucketable.saveDefaultDataToBucketTag(this, bucket);
        compoundnbt.putFloat("Health", this.getHealth());
        compoundnbt.putInt("Age", this.getAge());
        compoundnbt.putBoolean("CanGrow", this.getCanGrowUp());
        compoundnbt.putInt("VariantModel", this.getVariantModel());
        compoundnbt.putInt("VariantSkin", this.getVariantSkin());
        if (this.hasCustomName()) {
            bucket.setHoverName(this.getCustomName());
        }
    }

    @Override
    public void loadFromBucketTag(CompoundTag pTag) {
        Bucketable.loadDefaultDataFromBucketTag(this, pTag);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {

        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);

        if (pReason == MobSpawnType.BUCKET && pDataTag != null && pDataTag.contains("VariantModel", 3)) {
            this.setVariantModel(pDataTag.getInt("VariantModel"));
            this.setVariantSkin(pDataTag.getInt("VariantSkin"));
            if (pDataTag.contains("Age")) {
                this.setAge(pDataTag.getInt("Age"));}
            this.setCanGrowUp(pDataTag.getBoolean("CanGrow"));
        }else{

            int skin = this.random.nextInt(4);

            if(pReason == MobSpawnType.SPAWN_EGG || (pReason == MobSpawnType.BUCKET && pDataTag == null)){
                this.setVariantModel(this.random.nextInt(4));
            }else {
                int model;

                if (pSpawnData instanceof MinnowEntity.FishGroupData){

                    MinnowEntity.FishGroupData fish$fishgroupdata = (MinnowEntity.FishGroupData)pSpawnData;
                    model = fish$fishgroupdata.variantModel;
                    skin = fish$fishgroupdata.variantSkin;

                }else {
                    if (pLevel.getBiome(this.blockPosition()).is(Tags.Biomes.IS_SWAMP)){
                        model = 3;
                    }else if (pLevel.getBiome(this.blockPosition()).is(BiomeTags.IS_JUNGLE)){
                        model = this.random.nextBoolean() ? 1 : 0;
                    }else if (pLevel.getBiome(this.blockPosition()).is(BiomeTags.IS_RIVER)){
                        model = 2;
                    }else {
                        model = this.random.nextInt(4);
                    }

                    pSpawnData = new MinnowEntity.FishGroupData(this, model, skin);
                }

                this.setVariantModel(model);
            }

            this.setVariantSkin(skin);
        }

        return pSpawnData;
    }

    @Nullable
    @Override
    public BreedableWaterAnimal getBreedOffspring(ServerLevel pLevel, BreedableWaterAnimal pOtherParent) {
        MinnowEntity baby = YAFMEntities.MINNOW.get().create(pLevel);
        if (baby != null){
            baby.setFromBucket(true);
            baby.setVariantModel(this.getVariantModel());
            baby.setVariantSkin(this.getVariantSkin());
        }
        return baby;
    }

    @Override
    public boolean canMate(BreedableWaterAnimal pOtherAnimal) {
        MinnowEntity mate = (MinnowEntity) pOtherAnimal;
        return super.canMate(pOtherAnimal) && mate.getVariantModel() == this.getVariantModel() && mate.getVariantSkin() == this.getVariantSkin();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController[]{new AnimationController(this, "Normal", 5, this::Controller)});
    }

    protected <E extends MinnowEntity> PlayState Controller(AnimationState<E> event) {
        if (this.isInWater()){
            event.setAndContinue(SWIM);
        }else{
            event.setAndContinue(FLOP);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(YAFMItems.BARB_BUCKET.get());
    }


    static class FishGroupData extends VariantSchoolingFish.SchoolSpawnGroupData {
        final int variantModel;
        final int variantSkin;

        FishGroupData(MinnowEntity pLeader, int pVariantModel, int pVariantSkin) {
            super(pLeader);
            this.variantModel = pVariantModel;
            this.variantSkin = pVariantSkin;
        }
    }

}
