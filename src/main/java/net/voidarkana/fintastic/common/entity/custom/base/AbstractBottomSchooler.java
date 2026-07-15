package net.voidarkana.fintastic.common.entity.custom.base;

import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

public abstract class AbstractBottomSchooler extends AbstractSwimmingBottomDweller{

    protected AbstractBottomSchooler(EntityType<? extends BreedableWaterAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Nullable
    protected AbstractBottomSchooler leader;
    protected int schoolSize = 1;

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData) {
        super.finalizeSpawn(level, difficulty, reason, spawnData);

        if (reason == MobSpawnType.STRUCTURE){
            this.setFromBucket(true);
        }
        if (spawnData == null) {
            spawnData = new AbstractBottomSchooler.SchoolSpawnGroupData(this);
        } else {
            this.startFollowing(((AbstractBottomSchooler.SchoolSpawnGroupData)spawnData).leader);
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

    public AbstractBottomSchooler startFollowing(AbstractBottomSchooler leader) {
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
            List<? extends AbstractBottomSchooler> list = this.level().getEntitiesOfClass(this.getClass(), this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D));
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

    public void addFollowers(Stream<? extends AbstractBottomSchooler> followers) {
        followers.limit(this.getMaxSchoolSize() - this.schoolSize).filter((fish) -> fish != this).forEach((fish) -> fish.startFollowing(this));
    }

    public static class SchoolSpawnGroupData extends AgeableFishGroupData {
        public final AbstractBottomSchooler leader;
        public SchoolSpawnGroupData(AbstractBottomSchooler leader) {
            super(true);
            this.leader = leader;
        }
    }
}
