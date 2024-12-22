package net.voidarkana.fintastic.common.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.voidarkana.fintastic.common.entity.YAFMEntities;
import net.voidarkana.fintastic.common.entity.custom.ai.FishBreedGoal;
import net.voidarkana.fintastic.common.entity.custom.base.BreedableWaterAnimal;
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

public class ArapaimaEntity extends BucketableFishEntity implements GeoEntity {

    public final ArapaimaPart head;
    public final ArapaimaPart tail;
    public final ArapaimaPart[] allParts;
    public int ringBufferIndex = -1;
    public final float[][] ringBuffer = new float[64][3];

    protected static final RawAnimation ARAPAIMA_SWIM = RawAnimation.begin().thenLoop("animation.arapaima.swim");
    protected static final RawAnimation ARAPAIMA_FLOP = RawAnimation.begin().thenLoop("animation.arapaima.flop");

    public ArapaimaEntity(EntityType<? extends BreedableWaterAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);

        this.head = new ArapaimaPart(this, 1.2F,0.9F );
        this.tail = new ArapaimaPart(this, 1.2F, 0.9F);
        this.allParts = new ArapaimaPart[]{this.head, this.tail};

    }

    private static final Ingredient FOOD_ITEMS = Ingredient.of(YAFMTags.Items.FISH_FEED);

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.5D));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(2, new FishBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 2D, FOOD_ITEMS, false));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.8F);
    }


    public void aiStep() {
        super.aiStep();

        if (!this.isNoAi()) {
            if (this.ringBufferIndex < 0) {
                for (int i = 0; i < this.ringBuffer.length; ++i) {
                    this.ringBuffer[i][0] = this.getYRot();
                    this.ringBuffer[i][1] = (float) this.getY();
                }
            }
            this.ringBufferIndex++;
            if (this.ringBufferIndex == this.ringBuffer.length) {
                this.ringBufferIndex = 0;
            }
            this.ringBuffer[this.ringBufferIndex][0] = this.getYRot();
            this.ringBuffer[ringBufferIndex][1] = (float) this.getY();
            Vec3[] avector3d = new Vec3[this.allParts.length];

            for (int j = 0; j < this.allParts.length; ++j) {
                this.allParts[j].collideWithNearbyEntities();
                avector3d[j] = new Vec3(this.allParts[j].getX(), this.allParts[j].getY(), this.allParts[j].getZ());
            }
            final float f17 = this.getYRot() * Mth.DEG_TO_RAD;
            final float pitch = this.getXRot() * Mth.DEG_TO_RAD;
            final float xRotDiv90 = Math.abs(this.getXRot() / 90F);
            final float f3 = Mth.sin(f17) * (1 - xRotDiv90);
            final float f18 = Mth.cos(f17) * (1 - xRotDiv90);

            this.setPartPosition(this.head, f3 * -1.6F, -pitch * 0.8F, -f18 * -1.6F);
            this.setPartPosition(this.tail, f3 * 1.6F, pitch * 0.3F, f18 * -1.6F);

            for (int l = 0; l < this.allParts.length; ++l) {
                this.allParts[l].xo = avector3d[l].x;
                this.allParts[l].yo = avector3d[l].y;
                this.allParts[l].zo = avector3d[l].z;
                this.allParts[l].xOld = avector3d[l].x;
                this.allParts[l].yOld = avector3d[l].y;
                this.allParts[l].zOld = avector3d[l].z;
            }
        }
    }


    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public net.minecraftforge.entity.PartEntity<?>[] getParts() {
        return this.allParts;
    }

    private void setPartPosition(ArapaimaPart part, double offsetX, double offsetY, double offsetZ) {
        part.setPos(this.getX() + offsetX * part.scale, this.getY() + offsetY * part.scale, this.getZ() + offsetZ * part.scale);
    }

    public boolean attackEntityPartFrom(ArapaimaPart entityArapaimaPart, DamageSource source, float amount) {
        return this.hurt(source, amount);
    }

    public InteractionResult interactEntityPartFrom(ArapaimaPart entityArapaimaPart, Player player, InteractionHand hand) {
        return this.mobInteract(player, hand);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "movementController", 5, this::Controller));
    }

    protected PlayState Controller(AnimationState<ArapaimaEntity> state) {
        if (this.isInWater()) {
            return state.setAndContinue(ARAPAIMA_SWIM);
        } else {
            return state.setAndContinue(ARAPAIMA_FLOP);
        }
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(YAFMItems.ARAPAIMA_SPAWN_EGG.get());
    }

    @Nullable
    @Override
    public BreedableWaterAnimal getBreedOffspring(ServerLevel pLevel, BreedableWaterAnimal pOtherParent) {
        ArapaimaEntity baby = YAFMEntities.ARAPAIMA.get().create(pLevel);
        if (baby != null){
            baby.setFromBucket(true);
        }
        return baby;
    }

    @Override
    public void saveToBucketTag(ItemStack pStack) {

    }

    @Override
    public void loadFromBucketTag(CompoundTag pTag) {

    }

    @Override
    public ItemStack getBucketItemStack() {
        return null;
    }

    @Override
    public SoundEvent getPickupSound() {
        return null;
    }

}
