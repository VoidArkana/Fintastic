package net.voidarkana.fintastic.common.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.common.Tags;
import net.voidarkana.fintastic.common.entity.YAFMEntities;
import net.voidarkana.fintastic.common.entity.custom.ai.FishBreedGoal;
import net.voidarkana.fintastic.common.entity.custom.ai.boids.BoidGoal;
import net.voidarkana.fintastic.common.entity.custom.ai.boids.LimitSpeedAndLookInVelocityDirectionGoal;
import net.voidarkana.fintastic.common.entity.custom.ai.boids.OrganizeBoidsVariantGoal;
import net.voidarkana.fintastic.common.entity.custom.ai.boids.StayInWaterGoal;
import net.voidarkana.fintastic.common.entity.custom.base.BreedableWaterAnimal;
import net.voidarkana.fintastic.common.entity.custom.base.BucketableFishEntity;
import net.voidarkana.fintastic.common.entity.custom.base.VariantBoidingFish;
import net.voidarkana.fintastic.common.entity.custom.base.VariantSchoolingFish;
import net.voidarkana.fintastic.common.item.YAFMItems;
import net.voidarkana.fintastic.util.YAFMTags;
import org.jetbrains.annotations.Nullable;


public class Moony extends VariantBoidingFish {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState flopAnimationState = new AnimationState();
    public final AnimationState swimAnimationState = new AnimationState();

    public Moony(EntityType<? extends BucketableFishEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    private static final Ingredient FOOD_ITEMS = Ingredient.of(YAFMTags.Items.FISH_FEED);

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.targetSelector.addGoal(0, (new HurtByTargetGoal(this)).setAlertOthers());

        this.goalSelector.addGoal(1, new PanicGoal(this, 1.5D));
        this.goalSelector.addGoal(1, new TemptGoal(this, 2D, FOOD_ITEMS, false));
        this.goalSelector.addGoal(1, new FishBreedGoal(this, 1.5D));

        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 8.0F, 1.6D, 1.4D, (entity) -> {
            if (entity instanceof Player player){
                return !player.isCreative() && !player.isSpectator() && !player.getItemBySlot(EquipmentSlot.HEAD).is(YAFMItems.FISHING_HAT.get());
            }
            return false;}));

        this.goalSelector.addGoal(1, new OrganizeBoidsVariantGoal(this));

        this.goalSelector.addGoal(2, new BoidGoal(this, 0.2f, 0.75f, 8 / 20f, 1 / 20f));
        this.goalSelector.addGoal(2, new StayInWaterGoal(this));
        this.goalSelector.addGoal(2, new LimitSpeedAndLookInVelocityDirectionGoal(this, 0.65f));

        this.goalSelector.addGoal(6, new RandomSwimmingGoal(this, 1, 10));
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


    @Override
    public void tick() {
        if (this.level().isClientSide()){
            this.setupAnimationStates();
        }
        super.tick();
    }

    private void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);

        this.swimAnimationState.animateWhen(this.walkAnimation.isMoving() && this.isInWaterOrBubble(), this.tickCount);

        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble(), this.tickCount);
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

            int skin = this.random.nextInt(2);

            if(pReason == MobSpawnType.SPAWN_EGG || (pReason == MobSpawnType.BUCKET && pDataTag == null)){
                this.setVariantModel(this.random.nextInt(3));
            }else {
                int model;

                if (pSpawnData instanceof MinnowGroupData groupData){

//                    System.out.println(groupData.getVariantModel());
//                    System.out.println(groupData.getVariantSkin());

                    model = groupData.getVariantModel();
                    skin = groupData.getVariantSkin();
                    this.startFollowing(groupData.leader);

                }else {
//                    if (pLevel.getBiome(this.blockPosition()).is(Tags.Biomes.IS_SWAMP)){
//                        model = 3;
//                    }else if (pLevel.getBiome(this.blockPosition()).is(BiomeTags.IS_JUNGLE)){
//                        model = this.random.nextBoolean() ? 1 : 0;
//                    }else if (pLevel.getBiome(this.blockPosition()).is(BiomeTags.IS_RIVER)){
//                        model = 2;
//                    }else {
//                        model = this.random.nextInt(4);
//                    }

                    model = this.random.nextInt(3);


                    pSpawnData = new MinnowGroupData(this, model, skin);
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
        Moony baby = YAFMEntities.MOONY.get().create(pLevel);
        if (baby != null){
            baby.setFromBucket(true);
            baby.setVariantModel(this.getVariantModel());
            baby.setVariantSkin(this.getVariantSkin());
        }
        return baby;
    }

    @Override
    public boolean canMate(BreedableWaterAnimal pOtherAnimal) {
        Moony mate = (Moony) pOtherAnimal;
        return super.canMate(pOtherAnimal) && mate.getVariantModel() == this.getVariantModel() && mate.getVariantSkin() == this.getVariantSkin();
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(YAFMItems.MOONY_BUCKET.get());
    }


    static class MinnowGroupData extends SchoolSpawnGroupData {
        final int variantModel;
        final int variantSkin;

        MinnowGroupData(Moony pLeader, int pVariantModel, int pVariantSkin) {
            super(pLeader);
            this.variantModel = pVariantModel;
            this.variantSkin = pVariantSkin;
        }

        public int getVariantModel(){
            return variantModel;
        }

        public int getVariantSkin(){
            return variantSkin;
        }
    }

    public String getVariantName(){
        String variant;

        switch (this.getVariantModel()){
            case 1:
                variant = "moonysmall";
                break;
            case 2:
                variant = "moonytall";
                break;
            default:
                variant = "moony";
        }

        if (this.getVariantSkin() == 0){
            variant = variant + "_0";
        }else {
            variant = variant + "_1";
        }

        return variant;
    }

    public static String getVariantName(int variantModel, int variantSkin){
        String variant;

        switch (variantModel){
            case 1:
                variant = "moonysmall";
                break;
            case 2:
                variant = "moonytall";
                break;
            default:
                variant = "moony";
        }

        if (variantSkin == 0){
            variant = variant + "_0";
        }else {
            variant = variant + "_1";
        }

        return variant;
    }

    @Override
    public boolean canBabiesSchoolWithAdults() {
        return true;
    }
}
