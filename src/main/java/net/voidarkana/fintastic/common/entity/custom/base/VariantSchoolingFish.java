package net.voidarkana.fintastic.common.entity.custom.base;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.voidarkana.fintastic.common.entity.custom.ai.FollowVariantSchoolLeaderGoal;
import software.bernie.geckolib.core.animation.AnimatableManager;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

public abstract class VariantSchoolingFish extends BucketableFishEntity{

    private static final EntityDataAccessor<Integer> MODEL_VARIANT = SynchedEntityData.defineId(VariantSchoolingFish.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SKIN_VARIANT = SynchedEntityData.defineId(VariantSchoolingFish.class, EntityDataSerializers.INT);

    protected VariantSchoolingFish(EntityType<? extends BreedableWaterAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(5, new FollowVariantSchoolLeaderGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MODEL_VARIANT, 0);
        this.entityData.define(SKIN_VARIANT, 0);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("VariantModel", this.getVariantModel());
        compound.putInt("VariantSkin", this.getVariantSkin());
    }

    public int getVariantModel() {
        return this.entityData.get(MODEL_VARIANT);
    }

    public void setVariantModel(int variant) {
        this.entityData.set(MODEL_VARIANT, variant);
    }

    public int getVariantSkin() {
        return this.entityData.get(SKIN_VARIANT);
    }

    public void setVariantSkin(int variant) {
        this.entityData.set(SKIN_VARIANT, variant);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setVariantModel(compound.getInt("VariantModel"));
        this.setVariantSkin(compound.getInt("VariantSkin"));
    }

    @Override
    public abstract ItemStack getBucketItemStack();

    @Nullable
    private VariantSchoolingFish leader;
    private int schoolSize = 1;


    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        if (pSpawnData == null) {
            pSpawnData = new VariantSchoolingFish.SchoolSpawnGroupData(this);
        } else {
            this.startFollowing(((VariantSchoolingFish.SchoolSpawnGroupData)pSpawnData).leader);
        }

        return pSpawnData;
    }


    public int getMaxSpawnClusterSize() {
        return this.getMaxSchoolSize();
    }

    public int getMaxSchoolSize() {
        return super.getMaxSpawnClusterSize();
    }

    protected boolean canRandomSwim() {
        return !this.isFollower();
    }

    public boolean isFollower() {
        return this.leader != null && this.leader.isAlive()
                && this.leader.getVariantModel()==this.getVariantModel()
                && this.leader.getVariantSkin()==this.getVariantSkin()
                && this.isBaby() == this.leader.isBaby();
    }

    public VariantSchoolingFish startFollowing(VariantSchoolingFish pLeader) {
        this.leader = pLeader;
        pLeader.addFollower();
        return pLeader;
    }

    public void stopFollowing() {
        this.leader.removeFollower();
        this.leader = null;
    }

    private void addFollower() {
        ++this.schoolSize;
    }

    private void removeFollower() {
        --this.schoolSize;
    }

    public boolean canBeFollowed() {
        return this.hasFollowers() && this.schoolSize < this.getMaxSchoolSize();
    }

    public void tick() {
        super.tick();
        if (this.hasFollowers() && this.level().random.nextInt(200) == 1) {
            List<? extends BucketableFishEntity> list = this.level().getEntitiesOfClass(this.getClass(), this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D));
            if (list.size() <= 1) {
                this.schoolSize = 1;
            }
        }

    }

    public boolean hasFollowers() {
        return this.schoolSize > 1;
    }

    public boolean inRangeOfLeader() {
        return this.distanceToSqr(this.leader) <= 121.0D;
    }

    public void pathToLeader() {
        if (this.isFollower()) {
            this.getNavigation().moveTo(this.leader, 1.2D);
        }

    }

    public void addFollowers(Stream<? extends VariantSchoolingFish> pFollowers) {
        pFollowers.limit((long)(this.getMaxSchoolSize() - this.schoolSize)).filter((p_27538_) -> {
            return p_27538_ != this;
        }).forEach((fish) -> {
            if (this.getVariantSkin()==fish.getVariantSkin()
                    && this.getVariantModel()==fish.getVariantModel()
                    && this.isBaby()==fish.isBaby()){
                fish.startFollowing(this);
            }
        });
    }

    public static class SchoolSpawnGroupData extends AgeableFishGroupData{
        public final VariantSchoolingFish leader;

        public SchoolSpawnGroupData(VariantSchoolingFish pLeader) {
            super(true);
            this.leader = pLeader;
        }
    }

}
