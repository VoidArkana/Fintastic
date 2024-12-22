package net.voidarkana.fintastic.common.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.voidarkana.fintastic.common.entity.YAFMEntities;
import net.voidarkana.fintastic.common.entity.custom.ai.FishBreedGoal;
import net.voidarkana.fintastic.common.entity.custom.base.AbstractSwimmingBottomDweller;
import net.voidarkana.fintastic.common.entity.custom.base.BreedableWaterAnimal;
import net.voidarkana.fintastic.common.item.YAFMItems;
import net.voidarkana.fintastic.util.YAFMTags;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class PlecoEntity extends AbstractSwimmingBottomDweller implements GeoEntity {

    protected static final RawAnimation SWIM = RawAnimation.begin().thenLoop("animation.pleco.swim");
    protected static final RawAnimation FLOP = RawAnimation.begin().thenLoop("animation.pleco.flop");
    protected static final RawAnimation SWIM_GROUND = RawAnimation.begin().thenLoop("animation.pleco.swim_ground");
    protected static final RawAnimation IDLE_GROUND = RawAnimation.begin().thenPlay("animation.pleco.idle_ground");
    protected static final RawAnimation IDLE_SWIM = RawAnimation.begin().thenPlay("animation.pleco.idle_swim");

    public PlecoEntity(EntityType<? extends BreedableWaterAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.5F);
    }

    private static final Ingredient FOOD_ITEMS = Ingredient.of(YAFMTags.Items.FISH_FEED);

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new FishBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 2D, FOOD_ITEMS, false));
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.5D));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 4.0F, 1.5D, 1.5D, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 50) {
            @Override
            public boolean canUse() {
                return PlecoEntity.this.getWantsToSwim() && super.canUse();
            }

            @Override
            public boolean canContinueToUse() {
                return PlecoEntity.this.getWantsToSwim() && super.canContinueToUse();
            }

        });
        this.goalSelector.addGoal(10, new RandomStrollGoal(this, 1, 80) {
            @Override
            public boolean canUse() {
                return !PlecoEntity.this.getWantsToSwim() && PlecoEntity.this.onGround() && super.canUse();
            }

            @Override
            public boolean canContinueToUse() {
                return !PlecoEntity.this.getWantsToSwim() && super.canContinueToUse();
            }

            @Nullable
            @Override
            protected Vec3 getPosition() {
                return DefaultRandomPos.getPos(this.mob, 10, 1);
            }
        });

    }

    @Override
    public void saveToBucketTag(ItemStack bucket) {
        CompoundTag compoundnbt = bucket.getOrCreateTag();
        Bucketable.saveDefaultDataToBucketTag(this, bucket);
        compoundnbt.putFloat("Health", this.getHealth());
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
        if (pReason == MobSpawnType.BUCKET && pDataTag != null && pDataTag.contains("Age", 3)) {
            if (pDataTag.contains("Age")) {
                this.setAge(pDataTag.getInt("Age"));}
            this.setCanGrowUp(pDataTag.getBoolean("CanGrow"));
        }
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Nullable
    @Override
    public BreedableWaterAnimal getBreedOffspring(ServerLevel pLevel, BreedableWaterAnimal pOtherParent) {
        PlecoEntity baby = YAFMEntities.PLECO.get().create(pLevel);
        if (baby != null){
            baby.setFromBucket(true);
        }
        return baby;
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(YAFMItems.PLECO_BUCKET.get());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController[]{new AnimationController(this, "Normal", 5, this::Controller)});
    }

    protected <E extends CatfishEntity> PlayState Controller(AnimationState<E> event) {
        if (this.isInWater()){
            if (this.onGround()){
                if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6){
                    event.setAndContinue(SWIM_GROUND);
                }else {
                    event.setAndContinue(IDLE_GROUND);
                }
            }else {
                if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6){
                    event.setAndContinue(SWIM);
                }else {
                    event.setAndContinue(IDLE_SWIM);
                }
            }
        }else{
            event.setAndContinue(FLOP);
        }
        return PlayState.CONTINUE;
    }
}
