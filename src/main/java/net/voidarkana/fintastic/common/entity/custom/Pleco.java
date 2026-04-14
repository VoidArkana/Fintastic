package net.voidarkana.fintastic.common.entity.custom;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.voidarkana.fintastic.common.block.FintyBlocks;
import net.voidarkana.fintastic.common.block.custom.AquariumGlassPane;
import net.voidarkana.fintastic.common.entity.FintyEntities;
import net.voidarkana.fintastic.common.entity.custom.ai.FishBreedGoal;
import net.voidarkana.fintastic.common.entity.custom.base.AbstractSwimmingBottomDweller;
import net.voidarkana.fintastic.common.entity.custom.base.BreedableWaterAnimal;
import net.voidarkana.fintastic.common.item.FintyItems;
import net.voidarkana.fintastic.util.FintyTags;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

public class Pleco extends AbstractSwimmingBottomDweller {

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Pleco.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TICKS_ON_GROUND = SynchedEntityData.defineId(Pleco.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> WANTS_TO_ATTACH = SynchedEntityData.defineId(Pleco.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ATTACHED_TICKS = SynchedEntityData.defineId(Pleco.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Direction> ATTACHED_DIRECTION = SynchedEntityData.defineId(Pleco.class, EntityDataSerializers.DIRECTION);
    private static final EntityDataAccessor<Direction> POINTING_DIRECTION = SynchedEntityData.defineId(Pleco.class, EntityDataSerializers.DIRECTION);
    private static final EntityDataAccessor<Integer> STRAFING_TICKS = SynchedEntityData.defineId(Pleco.class, EntityDataSerializers.INT);

    int prevTicksOnGround;
    int prevTicksAttached;
    int prevTicksStrifing;

    public final AnimationState suckAnimationState = new AnimationState();
    int suckAnimationTimeout;

    @Nullable
    BlockPos attachmentPos;

    public Pleco(EntityType<? extends BreedableWaterAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.5F);
    }

    private static final Ingredient FOOD_ITEMS = Ingredient.of(FintyTags.Items.FISH_FEED);

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new FishBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 2D, FOOD_ITEMS, false));
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.5D));

        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 8.0F, 1.6D, 1.4D, (entity) -> {
            if (entity instanceof Player player){
                return !player.isCreative() && !player.isSpectator() && !player.getItemBySlot(EquipmentSlot.HEAD).is(FintyItems.FISHING_HAT.get());
            }
            return false;}));

        this.goalSelector.addGoal(4, new PlecoSwimGoal(this));
        this.goalSelector.addGoal(10, new PlecoBottomMoveGoal(this, 1, 80));

        this.goalSelector.addGoal(3, new PlecoAttachToWallGoal(this ));

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
        this.entityData.define(WANTS_TO_ATTACH, false);
        this.entityData.define(TICKS_ON_GROUND, 0);
        this.entityData.define(ATTACHED_TICKS, 0);
        this.entityData.define(STRAFING_TICKS, 0);
        this.entityData.define(ATTACHED_DIRECTION, Direction.DOWN);
        this.entityData.define(POINTING_DIRECTION, Direction.DOWN);
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

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }

    public int getTicksOnGround() {
        return this.entityData.get(TICKS_ON_GROUND);
    }

    public void setTicksOnGround(int ticks) {
        this.entityData.set(TICKS_ON_GROUND, ticks);
    }

    public int getTicksAttached() {
        return this.entityData.get(ATTACHED_TICKS);
    }

    public void setTicksAttached(int ticks) {
        this.entityData.set(ATTACHED_TICKS, ticks);
    }

    public int getStrafingTicks() {
        return this.entityData.get(STRAFING_TICKS);
    }

    public void setStrafingTicks(int ticks) {
        this.entityData.set(STRAFING_TICKS, ticks);
    }

    public boolean wantsToAttach() {
        return this.entityData.get(WANTS_TO_ATTACH);
    }

    public void setWantsToAttach(boolean wantsToAttach) {
        this.entityData.set(WANTS_TO_ATTACH, wantsToAttach);
    }

    public Direction getAttachedDirection() {
        return this.entityData.get(ATTACHED_DIRECTION);
    }

    public void setAttachedDirection(Direction direction) {
        this.entityData.set(ATTACHED_DIRECTION, direction);
    }

    public Direction getPointingDirection() {
        return this.entityData.get(POINTING_DIRECTION);
    }

    public void setPointingDirection(Direction direction) {
        this.entityData.set(POINTING_DIRECTION, direction);
    }

    public boolean isAttached(){
        return this.getAttachedDirection().getAxis().isHorizontal();
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
        if (pTag.contains("Age"))
            this.setAge(pTag.getInt("Age"));
        if (pTag.contains("Variant"))
            this.setVariant(pTag.getInt("Variant"));
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        if (pReason == MobSpawnType.BUCKET && pDataTag != null && pDataTag.contains("Age", 3)) {
            if (pDataTag.contains("Age")) {
                this.setAge(pDataTag.getInt("Age"));}
            this.setCanGrowUp(pDataTag.getBoolean("CanGrow"));
            this.setVariant(pDataTag.getInt("Variant"));
        }else {
            this.setVariant(Util.getRandom(PlecoVariant.values(), random).getVariantID());
        }
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        float width = (float) Mth.lerp(this.getTicksAttached()/3D, 1, 0.4);
        float height = (float) Mth.lerp(this.getTicksAttached()/3D, 1, 1.25);
        return super.getDimensions(pPose).scale(width, height);
    }

    @Nullable
    @Override
    public BreedableWaterAnimal getBreedOffspring(ServerLevel pLevel, BreedableWaterAnimal pOtherParent) {
        Pleco baby = FintyEntities.PLECO.get().create(pLevel);
        if (baby != null){
            baby.setFromBucket(true);
            baby.setVariant(this.getVariant());
        }
        return baby;
    }

    @Override
    public void tick() {

        if (this.isStrafing() && this.getStrafingTicks() < 5){
            this.prevTicksStrifing = this.getStrafingTicks();
            this.setStrafingTicks(this.prevTicksStrifing+1);
        }else if (!this.isStrafing() && this.getStrafingTicks() > 0){
            this.prevTicksStrifing = this.getStrafingTicks();
            this.setStrafingTicks(this.prevTicksStrifing-1);
        }

        super.tick();

        if (this.getRandom().nextInt(500)==0 && !this.wantsToAttach() && !this.getWantsToSwim()){
            this.setWantsToAttach(true);
        }

        if (this.isAttached() && wantsToAttach() && !this.isInWater()){
            this.setWantsToAttach(false);
            this.setAttachedDirection(Direction.DOWN);
            this.setPointingDirection(Direction.DOWN);
        }

        if (this.isAttached() && this.getRandom().nextInt(1000)==0){
            this.setWantsToAttach(false);
            this.setAttachedDirection(Direction.DOWN);
            this.setPointingDirection(Direction.DOWN);
        }
    }

    @Override
    public void setupAnimationStates() {
        super.setupAnimationStates();

        if (this.isStrafing() && this.suckAnimationTimeout <=0){
            this.suckAnimationTimeout = 39;
            this.suckAnimationState.start(this.tickCount);
        }else if (this.suckAnimationTimeout>0){
            this.suckAnimationTimeout--;
        }
    }

    @Override
    protected boolean isImmobile() {
        return this.isAttached() || super.isImmobile();
    }

    public boolean isStrafing(){
        return this.getPointingDirection() != Direction.DOWN && (this.isAttached() || this.onGround());
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        this.refreshDimensions();
        super.onSyncedDataUpdated(pKey);
    }

    @Override
    public void baseTick() {
        super.baseTick();
        if (this.isStrafing()){
            this.yBodyRot = this.getAttachedDirection().toYRot();
//            this.yBodyRotO = this.getAttachedDirection().toYRot();
        }
    }

    @Override
    public void aiStep() {

        super.aiStep();

        if (this.isStrafing() && this.tickCount % 40 == 0){
            this.setPointingDirection(Direction.DOWN);
        }

        if (this.isAttached()){
            BlockPos blockStuckTo = this.blockPosition().relative(this.getAttachedDirection());
            BlockState stateStuckTo = this.level().getBlockState(blockStuckTo);
            if (!(stateStuckTo.isFaceSturdy(this.level(), blockStuckTo, this.getAttachedDirection()) || stateStuckTo.is(Blocks.GLASS) || stateStuckTo.is(FintyBlocks.AQUARIUM_GLASS_PANE.get()))){
                Direction newDirection = this.getAttachedDirection();
                int counter = 0;
                for (int x = 0; x < 4; x++){
                    BlockPos newPos = this.blockPosition().relative(newDirection.getOpposite());

                    BlockState blockstate = this.level().getBlockState(newPos);
                    if (blockstate.isFaceSturdy(this.level(), newPos, newDirection) || blockstate.is(Blocks.GLASS) || blockstate.is(FintyBlocks.AQUARIUM_GLASS_PANE.get())){
                        if (blockstate.getBlock() instanceof AquariumGlassPane aquariumGlassPane){
                            if (aquariumGlassPane.getDirection(blockstate).getAxis() == newDirection.getAxis()){
                                this.setAttachedDirection(newDirection.getOpposite());
                                break;
                            }
                        }else {
                            this.setAttachedDirection(newDirection.getOpposite());
                            break;
                        }
                    }else{
                        counter++;
                    }
                    newDirection = newDirection.getClockWise();
                }

                if (counter >= 4){
                    this.setAttachedDirection(Direction.DOWN);
                }
            }
            this.yBodyRot = this.getAttachedDirection().toYRot();
            this.yHeadRot = this.getAttachedDirection().toYRot();

            this.addDeltaMovement(new Vec3(this.getAttachedDirection().getStepX()/16D, 0.005, this.getAttachedDirection().getStepZ()/16D));

            if (this.getRandom().nextInt(50)==0 && this.tickCount % 40 == 0
                    && !this.isStrafing() && this.getTicksAttached()>0){
                switch (this.getRandom().nextInt(3)){
                    case 1:
                        this.setPointingDirection(this.getAttachedDirection().getCounterClockWise());
                        break;
                    case 2:
                        this.setPointingDirection(this.getAttachedDirection().getClockWise());
                        break;
                    default:
                        this.setPointingDirection(Direction.UP);
                }
            }

            if (!this.isEyeInFluidType(Fluids.WATER.getFluidType())){

                this.setAttachedDirection(Direction.DOWN);
            }
        }

        if (this.isStrafing()){
            this.setDeltaMovement(this.getPointingDirection().getStepX()/16D,
                                  this.getPointingDirection().getStepY()/16D,
                                  this.getPointingDirection().getStepZ()/16D);

            if (this.isAttached()){
                this.yBodyRot = this.getAttachedDirection().toYRot();
                this.yHeadRot = this.getAttachedDirection().toYRot();
            }
        }

        if (this.getRandom().nextInt(100)==0 && this.getNavigation().isDone() && !this.isStrafing() && !this.isAttached() && this.onGround() && !this.getWantsToSwim() && this.tickCount % 40 == 0){
            this.setPointingDirection(Direction.fromYRot(Math.round(this.yHeadRot / 90.0) * 90));
        }

        if (!this.level().isClientSide()){
            if (this.isInWaterOrBubble() && !this.onGround()){
                if (this.getTicksOnGround() > 0){
                    this.prevTicksOnGround = this.getTicksOnGround();
                    this.setTicksOnGround(this.prevTicksOnGround-1);
                }
            }else {
                if (this.getTicksOnGround() < 3){
                    this.prevTicksOnGround = this.getTicksOnGround();
                    this.setTicksOnGround(this.prevTicksOnGround+1);
                }
            }

            if (this.isAttached() && this.getTicksAttached() < 3){
                this.prevTicksAttached = this.getTicksAttached();
                this.setTicksAttached(this.prevTicksAttached+1);
                this.refreshDimensions();
            }else if (!this.isAttached() && this.getTicksAttached() > 0){
                this.prevTicksAttached = this.getTicksAttached();
                this.setTicksAttached(this.prevTicksAttached-1);
                this.refreshDimensions();
            }
        }


        if ((!this.wantsToAttach() || !this.isInWater()) && this.isAttached()){
            this.setWantsToAttach(false);
            this.setAttachedDirection(Direction.DOWN);
            this.setPointingDirection(Direction.DOWN);
        }

        if (!this.isInWater() && this.wantsToAttach())
            this.setWantsToAttach(false);

    }

    @Override
    public boolean isPushable() {
        return super.isPushable() && !this.isAttached();
    }

    @Override
    public InteractionResult interactAt(Player pPlayer, Vec3 pVec, InteractionHand pHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);

        if (itemStack.is(Items.DEBUG_STICK) && !this.level().isClientSide){
//            if (!this.wantsToAttach())
//                this.setWantsToAttach(true);

            if (!this.isStrafing() && this.isAttached()){
                switch (this.getRandom().nextInt(3)){
                    case 1:
                        this.setPointingDirection(this.getAttachedDirection().getCounterClockWise());
                        break;
                    case 2:
                        this.setPointingDirection(this.getAttachedDirection().getClockWise());
                        break;
                    default:
                        this.setPointingDirection(Direction.UP);
                }
            }

            if (!this.isStrafing() && !this.isAttached() && this.onGround() && !this.getWantsToSwim()){
                this.setPointingDirection(Direction.fromYRot(Math.round(this.yHeadRot / 90.0) * 90));
            }
        }

        return super.interactAt(pPlayer, pVec, pHand);
    }

    @Override
    public boolean canMate(BreedableWaterAnimal pOtherAnimal) {
        Pleco otherGuy = (Pleco) pOtherAnimal;
        return this.getVariant() == otherGuy.getVariant() && super.canMate(pOtherAnimal);
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(FintyItems.PLECO_BUCKET.get());
    }

    @Override
    public boolean canFlop() {
        return false;
    }

    public enum PlecoVariant implements StringRepresentable {
        SAILFIN(0, "sailfin"),
        BLUE_EYED_PANAQUE(1, "blue_eyed_panaque"),
        BLUE_PHANTOM(2, "blue_phantom"),
        CACTUS(3, "cactus"),
        LUTEUS(4, "luteus");

        private final int variantID;
        private final String name;

        PlecoVariant(int variant, String name){
            this.variantID = variant;
            this.name = name;
        }

        public int getVariantID(){
            return this.variantID;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        public static final IntFunction<PlecoVariant> BY_ID
                = ByIdMap.sparse(PlecoVariant::getVariantID, values(), SAILFIN);

        public static final StringRepresentable.EnumCodec<PlecoVariant> CODEC
                = StringRepresentable.fromEnum(PlecoVariant::values);

        public static PlecoVariant byId(int pId) {
            return BY_ID.apply(pId);
        }

        public static PlecoVariant byName(String pName) {
            return CODEC.byName(pName, SAILFIN);
        }
    }

    @Override
    public boolean onClimbable() {
        return super.onClimbable();
    }

    class PlecoSwimGoal extends RandomSwimmingGoal{

        Pleco pleco;

        public PlecoSwimGoal(Pleco mob) {
            super(mob, 1.0D, 50);
            this.pleco = mob;
        }

        @Override
        public boolean canUse() {
            if (this.pleco.isAttached())
                return false;
            return this.pleco.getWantsToSwim() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            if (this.pleco.isAttached())
                return false;
            return this.pleco.getWantsToSwim() && super.canContinueToUse();
        }
    }

    class PlecoBottomMoveGoal extends RandomStrollGoal{

        Pleco pleco;
        public PlecoBottomMoveGoal(Pleco pMob, double pSpeedModifier, int interval) {
            super(pMob, pSpeedModifier, interval);
            this.pleco = pMob;
        }

        @Override
        public boolean canUse() {
            if (this.pleco.isAttached())
                return false;
            return !this.pleco.getWantsToSwim() && this.pleco.onGround() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            if (this.pleco.isAttached())
                return false;
            return !this.pleco.getWantsToSwim() && super.canContinueToUse();
        }

        @Nullable
        @Override
        protected Vec3 getPosition() {
            return DefaultRandomPos.getPos(this.pleco, 10, 1);
        }
    }

    class PlecoAttachToWallGoal extends MoveToBlockGoal{

        Pleco pleco;
        Direction direction;

        public PlecoAttachToWallGoal(Pleco pMob) {
            super(pMob, 1, 16, 8);
            this.pleco = pMob;
        }

        @Override
        public boolean canUse() {
            return !pleco.isAttached() && pleco.wantsToAttach() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return !pleco.isAttached() && super.canContinueToUse() && pleco.wantsToAttach();
        }

        protected int nextStartTick(PathfinderMob creature) {
            return 60;
        }

        @Override
        protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {
            Direction pDirection;
            BlockPos otherPos;
            for (int x = -1; x < 1; x++){
                for (int z = -1; z < 1; z++){
                    if (x == 0 || z == 0){
                        pDirection = Direction.fromDelta(x, 0, z);
                        if (pDirection != null){
                            otherPos = pPos.relative(pDirection);

                            BlockState blockstate = pLevel.getBlockState(otherPos);
                            if ((blockstate.isFaceSturdy(pLevel, otherPos, pDirection) || blockstate.is(Blocks.GLASS))
                                    && pLevel.getBlockState(pPos).is(Blocks.WATER)){
                                this.direction = pDirection;
                                return true;
                            }else if (blockstate.getBlock() instanceof AquariumGlassPane glassPane){
                                if (glassPane.getDirection(blockstate).getAxis() == this.direction.getAxis()){
                                    this.direction = pDirection;
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
            return false;
        }

        @Override
        public void start() {
            this.pleco.setWantsToSwim(true);
            super.start();
        }

        protected BlockPos getMoveToTarget() {
            return this.blockPos;
        }

        @Override
        public void tick() {


            if (this.shouldRecalculatePath()){
                this.mob.getNavigation().moveTo((double)((float)this.blockPos.getX()),
                        (double)this.blockPos.getY(),
                        (double)((float)this.blockPos.getZ()) + 0.5D, 1.25);

            }

            if (this.isReachedTarget() || this.pleco.horizontalCollision){
                this.pleco.setAttachedDirection(Direction.fromYRot(Math.round(this.pleco.yHeadRot / 90.0) * 90));
                this.pleco.setXRot(0);
                this.pleco.setDeltaMovement(Vec3.ZERO);
                this.stop();
            }else{
                this.mob.getLookControl().setLookAt((double)((float)this.blockPos.getX()) + 0.5D, (double)this.blockPos.getY(), (double)((float)this.blockPos.getZ()) + 0.5D);
            }

            super.tick();
        }

        @Override
        protected boolean findNearestBlock() {
//            return super.findNearestBlock();
            int i = 16;
            int j = 8;
            BlockPos blockpos = this.mob.blockPosition();
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for(int k = j; k >= -j; k--) {
                for(int l = 0; l < i; ++l) {
                    for(int i1 = 0; i1 <= l; i1 = i1 > 0 ? -i1 : 1 - i1) {
                        for(int j1 = i1 < l && i1 > -l ? l : 0; j1 <= l; j1 = j1 > 0 ? -j1 : 1 - j1) {
                            blockpos$mutableblockpos.setWithOffset(blockpos, i1, k, j1);
                            if (this.mob.isWithinRestriction(blockpos$mutableblockpos) && this.isValidTarget(this.mob.level(), blockpos$mutableblockpos)) {
                                this.blockPos = blockpos$mutableblockpos;
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }
    }

}
