package net.voidarkana.fintastic.common.entity.custom.base;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.voidarkana.fintastic.common.entity.custom.ai.FollowIndiscriminateSchoolLeaderGoal;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

public abstract class SchoolingFish extends BucketableFishEntity{

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(SchoolingFish.class, EntityDataSerializers.INT);

    protected SchoolingFish(EntityType<? extends BreedableWaterAnimal> entityType, Level level) {
        super(entityType, level);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(5, new FollowIndiscriminateSchoolLeaderGoal(this));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getVariant());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setVariant(compound.getInt("Variant"));
    }

    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }

    @Nullable
    protected SchoolingFish leader;
    private int schoolSize = 1;


    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData) {
        super.finalizeSpawn(level, difficulty, reason, spawnData);

        if (reason == MobSpawnType.STRUCTURE){
            this.setFromBucket(true);
        }
        if (spawnData == null) {
            spawnData = new SchoolingFish.SchoolSpawnGroupData(this);
        } else {
            this.startFollowing(((SchoolingFish.SchoolSpawnGroupData)spawnData).leader);
        }

        return spawnData;
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
        return this.leader != null && this.leader.isAlive();
    }

    public SchoolingFish startFollowing(SchoolingFish leader) {
        this.leader = leader;
        leader.addFollower();
        return leader;
    }

    public void stopFollowing() {
        assert this.leader != null;
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
        assert this.leader != null;
        return this.distanceToSqr(this.leader) <= 121.0D;
    }

    public void pathToLeader() {
        if (this.isFollower()) {
            assert this.leader != null;
            this.getNavigation().moveTo(this.leader, 1.2D);
        }
    }

    public void addFollowers(Stream<? extends SchoolingFish> followers) {
        followers.limit(this.getMaxSchoolSize() - this.schoolSize).filter((fish) -> fish != this).forEach((fish) -> fish.startFollowing(this));
    }

    public static class SchoolSpawnGroupData extends AgeableFishGroupData {
        public final SchoolingFish leader;
        public SchoolSpawnGroupData(SchoolingFish leader) {
            super(true);
            this.leader = leader;
        }
    }

}
