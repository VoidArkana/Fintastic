package net.voidarkana.fintastic.common.entity.custom.base;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BucketableFishEntity extends BreedableWaterAnimal implements Bucketable {


    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(BucketableFishEntity.class, EntityDataSerializers.BOOLEAN);

    protected BucketableFishEntity(EntityType<? extends BreedableWaterAnimal> entityType, Level level) {
        super(entityType, level);
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(FROM_BUCKET, false);
    }

    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    public void setFromBucket(boolean fromBucket) {
        this.entityData.set(FROM_BUCKET, fromBucket);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("FromBucket", this.fromBucket());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setFromBucket(compound.getBoolean("FromBucket"));
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (canBeBucketed()){
            return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
        }else {
            return super.mobInteract(player, hand);
        }
    }

    public boolean canBeBucketed(){
        return true;
    }

    public void saveToBucketTag(ItemStack stack) {
        Bucketable.saveDefaultDataToBucketTag(this, stack);
    }

    public void loadFromBucketTag(CompoundTag tag) {
        Bucketable.loadDefaultDataFromBucketTag(this, tag);
    }

    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_FILL_FISH;
    }

    protected boolean canRandomSwim() {
        return true;
    }

    protected void playStepSound(BlockPos pos, BlockState block) {
    }

    public int getMaxSpawnClusterSize() {
        return 8;
    }

    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return !this.fromBucket() && !this.hasCustomName();
    }

    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.fromBucket();
    }


}
