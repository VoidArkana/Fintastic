package net.voidarkana.fintastic.common.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.voidarkana.fintastic.common.entity.YAFMEntities;
import net.voidarkana.fintastic.common.entity.custom.ai.FishBreedGoal;
import net.voidarkana.fintastic.common.entity.custom.ai.FishJumpGoal;
import net.voidarkana.fintastic.common.entity.custom.base.BreedableWaterAnimal;
import net.voidarkana.fintastic.common.entity.custom.base.VariantSchoolingFish;
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

public class FreshwaterSharkEntity extends VariantSchoolingFish implements GeoEntity {

    protected static final RawAnimation SWIM = RawAnimation.begin().thenLoop("animation.genericfish.swim");
    protected static final RawAnimation FLOP = RawAnimation.begin().thenLoop("animation.genericfish.flop");

    protected static final RawAnimation HEAD_SWIM = RawAnimation.begin().thenLoop("animation.genericfish.headswim");
    protected static final RawAnimation HEAD_FLOP = RawAnimation.begin().thenLoop("animation.genericfish.headflop");

    private static final Ingredient FOOD_ITEMS = Ingredient.of(YAFMTags.Items.FISH_FEED);

    public FreshwaterSharkEntity(EntityType<? extends BreedableWaterAnimal> pEntityType, Level pLevel) {
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
            case 1, 4 ->super.getDimensions(pPose).scale(1.5F, 1.5F);
            case 2 ->super.getDimensions(pPose).scale(1.7F, 1.7F);
            case 3, 5 ->super.getDimensions(pPose).scale(1F, 1F);
            default ->super.getDimensions(pPose);
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
        compoundnbt.putInt("VariantSkin", this.getVariantSkin());
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

        super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);

        if (pReason == MobSpawnType.BUCKET && pDataTag != null && pDataTag.contains("VariantModel", 3)) {
            this.setVariantModel(pDataTag.getInt("VariantModel"));
            this.setVariantSkin(pDataTag.getInt("VariantSkin"));
            if (pDataTag.contains("Age")) {
                this.setAge(pDataTag.getInt("Age"));
            }
            this.setCanGrowUp(pDataTag.getBoolean("CanGrow"));
        }else{

            int model;
            int skin;

            if (pSpawnData instanceof FishGroupData){
                FreshwaterSharkEntity.FishGroupData fish$fishgroupdata = (FreshwaterSharkEntity.FishGroupData)pSpawnData;
                model = fish$fishgroupdata.variantModel;
                skin = fish$fishgroupdata.variantSkin;

                this.startFollowing(((FreshwaterSharkEntity.FishGroupData)pSpawnData).leader);
            }else {
                model = this.random.nextInt(4);

                if (model==1){
                    skin = this.random.nextInt(3);
                }else{
                    skin = 0;
                }
                pSpawnData = new FreshwaterSharkEntity.FishGroupData(this, model, skin);
            }

            this.setVariantModel(model);
            this.setVariantSkin(skin);
        }

        return pSpawnData;
    }

    @Nullable
    @Override
    public BreedableWaterAnimal getBreedOffspring(ServerLevel pLevel, BreedableWaterAnimal pOtherParent) {
        FreshwaterSharkEntity baby = YAFMEntities.FRESHWATER_SHARK.get().create(pLevel);
        if (baby != null){
            baby.setVariantModel(this.getVariantModel());
            if (this.getVariantModel()==1){
                FreshwaterSharkEntity otherParent = (FreshwaterSharkEntity) pOtherParent;
                int lowerQuality = Math.min(this.getFeedQuality(), otherParent.getFeedQuality());

                switch (lowerQuality){
                    case 1:
                        baby.setVariantSkin(this.random.nextBoolean() ? this.random.nextInt(3)
                                : this.random.nextBoolean() ? this.getVariantSkin() : otherParent.getVariantSkin());
                        break;
                    case 2, 3:
                        baby.setVariantSkin(this.random.nextBoolean() ? this.getVariantSkin() : otherParent.getVariantSkin());
                        break;
                    default:
                        baby.setVariantSkin(this.random.nextInt(3));
                        break;
                }
            }
            baby.setFromBucket(true);
        }
        return baby;
    }


    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(YAFMItems.FRESHWATER_SHARK_BUCKET.get());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController[]{new AnimationController(this, "Normal", 5, this::Controller)});
    }

    protected <E extends FreshwaterSharkEntity> PlayState Controller(AnimationState<E> event) {
        if (this.isInWater()){
            event.setAndContinue(this.getVariantModel() == 0 ? SWIM : HEAD_SWIM);
            event.getController().setAnimationSpeed(1.5);
        }else{
            event.setAndContinue(this.getVariantModel() == 0 ? FLOP : HEAD_FLOP);
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
        FreshwaterSharkEntity mate = (FreshwaterSharkEntity) pOtherAnimal;
        return super.canMate(pOtherAnimal) && this.getVariantModel() == mate.getVariantModel();
    }

    static class FishGroupData extends VariantSchoolingFish.SchoolSpawnGroupData {
        final int variantModel;
        final int variantSkin;

        FishGroupData(FreshwaterSharkEntity pLeader, int pVariantModel, int pVariantSkin) {
            super(pLeader);
            this.variantModel = pVariantModel;
            this.variantSkin = pVariantSkin;
        }
    }


    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

}
