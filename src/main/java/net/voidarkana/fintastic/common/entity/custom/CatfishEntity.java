package net.voidarkana.fintastic.common.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.common.Tags;
import net.voidarkana.fintastic.common.entity.YAFMEntities;
import net.voidarkana.fintastic.common.entity.custom.ai.FishBreedGoal;
import net.voidarkana.fintastic.common.entity.custom.base.BreedableWaterAnimal;
import net.voidarkana.fintastic.common.entity.custom.base.BucketableFishEntity;
import net.voidarkana.fintastic.common.item.YAFMItems;
import net.voidarkana.fintastic.util.YAFMTags;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class CatfishEntity extends BucketableFishEntity implements GeoEntity {

    protected static final RawAnimation SWIM = RawAnimation.begin().thenLoop("animation.catfish.swim");
    protected static final RawAnimation FLOP = RawAnimation.begin().thenLoop("animation.catfish.flop");
    protected static final RawAnimation PIRAIBA_SWIM = RawAnimation.begin().thenLoop("animation.catfish.piraiba_swim");
    protected static final RawAnimation PIRAIBA_FLOP = RawAnimation.begin().thenPlay("animation.catfish.piraiba_flop");

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(CatfishEntity.class, EntityDataSerializers.INT);

    private static final Ingredient FOOD_ITEMS = Ingredient.of(YAFMTags.Items.FISH_FEED);

    public boolean isFood(ItemStack pStack) {
        return FOOD_ITEMS.test(pStack);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        this.refreshDimensions();
        super.onSyncedDataUpdated(pKey);
    }

    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        return switch (this.getVariant()){
            case 1, 4, 5, 6 ->super.getDimensions(pPose).scale(0.8F, 0.8F);
            case 2 ->super.getDimensions(pPose);
            case 3 ->super.getDimensions(pPose).scale(1.1F, 1.1F);
            default ->super.getDimensions(pPose).scale(1.5F, 1.5F);
        };
    }

    public CatfishEntity(EntityType<? extends BreedableWaterAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.refreshDimensions();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.8F);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new FishBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 2D, FOOD_ITEMS, false));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getVariant());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setVariant(compound.getInt("Variant"));
    }

    //variants
    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    @Override
    public boolean canBeBucketed() {
        return this.getVariant()!=0 || (this.getVariant() == 0 && this.isBaby());
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
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
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {

        if (pReason == MobSpawnType.BUCKET && pDataTag != null && pDataTag.contains("Variant", 3)) {
            this.setVariant(pDataTag.getInt("Variant"));
            if (pDataTag.contains("Age")) {
                this.setAge(pDataTag.getInt("Age"));
            }
            this.setCanGrowUp(pDataTag.getBoolean("CanGrow"));
        }else{
            if (pReason == MobSpawnType.SPAWN_EGG || (pReason == MobSpawnType.BUCKET && pDataTag == null)){
                this.setVariant(this.random.nextInt(7));

                if (this.getVariant() == 0){
                    this.setAge(-24000);
                }
            }
            else {
                if (pLevel.getBiome(this.blockPosition()).is(Tags.Biomes.IS_SWAMP)){
                    switch (this.random.nextInt(4)){
                        case 1:
                            this.setVariant(1);
                            break;
                        case 2:
                            this.setVariant(4);
                            break;
                        case 3:
                            this.setVariant(5);
                            break;
                        default:
                            this.setVariant(6);
                    }
                } else if (pLevel.getBiome(this.blockPosition()).is(BiomeTags.IS_RIVER)){

                    switch (this.random.nextInt(5)){
                        case 1:
                            this.setVariant(1);
                            break;
                        case 2:
                            this.setVariant(4);
                            break;
                        case 3:
                            this.setVariant(5);
                            break;
                        case 4:
                            this.setVariant(3);
                            break;
                        default:
                            this.setVariant(6);
                    }

                } else if (pLevel.getBiome(this.blockPosition()).is(BiomeTags.IS_JUNGLE)){
                    this.setVariant(this.random.nextBoolean() ? 0 : 2);
                }
            }
        }

        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Nullable
    @Override
    public BreedableWaterAnimal getBreedOffspring(ServerLevel pLevel, BreedableWaterAnimal pOtherParent) {
        CatfishEntity baby = YAFMEntities.CATFISH.get().create(pLevel);
        if (baby != null){
            baby.setVariant(this.getVariant());
            baby.setFromBucket(true);
        }
        return baby;
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(YAFMItems.CATFISH_BUCKET.get());
    }

    public boolean hurt(DamageSource pSource, float pAmount) {
        if (this.level().isClientSide) {
            return false;
        } else {
            if (!pSource.is(DamageTypeTags.AVOIDS_GUARDIAN_THORNS) && !pSource.is(DamageTypes.THORNS)) {
                Entity entity = pSource.getDirectEntity();
                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity = (LivingEntity)entity;
                    livingentity.hurt(this.damageSources().thorns(this), 2.0F);
                }
            }
            return super.hurt(pSource, pAmount);
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController[]{new AnimationController(this, "Normal", 5, this::Controller)});
    }

    protected <E extends CatfishEntity> PlayState Controller(AnimationState<E> event) {
        if (this.isInWater()){
            event.setAndContinue(this.getVariant() > 0 ? SWIM : PIRAIBA_SWIM);
        }else{
            event.setAndContinue(this.getVariant() > 0 ? FLOP : PIRAIBA_FLOP);
        }
        return PlayState.CONTINUE;
    }

    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        if (itemstack.is(YAFMItems.REGULAR_FEED.get())){
            this.setFeedQuality(0);
        }
        if (itemstack.is(YAFMItems.QUALITY_FEED.get())){
            this.setFeedQuality(1);
        }
        if (itemstack.is(YAFMItems.GREAT_FEED.get())){
            this.setFeedQuality(2);
        }
        if (itemstack.is(YAFMItems.PREMIUM_FEED.get())){
            this.setFeedQuality(3);
        }

        return super.mobInteract(pPlayer, pHand);
    }

    @Override
    public boolean canMate(BreedableWaterAnimal pOtherAnimal) {
        CatfishEntity mate = (CatfishEntity) pOtherAnimal;
        return super.canMate(pOtherAnimal) && this.getVariant() == mate.getVariant();
    }



    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
