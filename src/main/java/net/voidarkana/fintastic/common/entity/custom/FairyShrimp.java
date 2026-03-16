package net.voidarkana.fintastic.common.entity.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.voidarkana.fintastic.common.entity.FintyEntities;
import net.voidarkana.fintastic.common.entity.custom.ai.FishBreedGoal;
import net.voidarkana.fintastic.common.entity.custom.ai.FollowIndiscriminateSchoolLeaderGoal;
import net.voidarkana.fintastic.common.entity.custom.base.BreedableWaterAnimal;
import net.voidarkana.fintastic.common.entity.custom.base.SchoolingFish;
import net.voidarkana.fintastic.common.item.FintyItems;
import net.voidarkana.fintastic.util.FintyTags;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

public class FairyShrimp extends SchoolingFish {

    private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.DRIED_KELP);

    public final AnimationState circleAnimationState = new AnimationState();
    private int circleTimeout = this.random.nextInt(320) + 160;

    public FairyShrimp(EntityType<? extends BreedableWaterAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public boolean isFood(ItemStack pStack) {
        return FOOD_ITEMS.test(pStack);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 2.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.65F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new FishBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 2D, FOOD_ITEMS, false));
        this.goalSelector.addGoal(5, new FollowIndiscriminateSchoolLeaderGoal(this));
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.5D));

        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 8.0F, 1.6D, 1.4D, (entity) -> {
            if (entity instanceof Player player){
                return !player.isCreative() && !player.isSpectator() && !player.getItemBySlot(EquipmentSlot.HEAD).is(FintyItems.FISHING_HAT.get());
            }
            return false;}));

        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, WaterAnimal.class, 8.0F, 1.6D, 1.4D, (entity) -> {
            return entity.getType().is(FintyTags.EntityType.PREDATOR_FISH);}));

        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 25));
    }

    public boolean isNinni() {
        String s = ChatFormatting.stripFormatting(this.getName().getString());
        return s != null && (s.toLowerCase().contains("ninnih_")
                ||s.toLowerCase().contains("ninnih") ||
                s.toLowerCase().contains("ninni") ||
                s.toLowerCase().contains("sea monkey") ||
                s.toLowerCase().contains("seamonkey"));
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(FintyItems.ARTEMIA_BUCKET.get());
    }

    @Override
    public void saveToBucketTag(ItemStack bucket) {
        CompoundTag compoundnbt = bucket.getOrCreateTag();
        Bucketable.saveDefaultDataToBucketTag(this, bucket);
        compoundnbt.putFloat("Health", this.getHealth());
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
        if (pTag.contains("Age"))
            this.setAge(pTag.getInt("Age"));
        if (pTag.contains("VariantSkin"))
            this.setVariantSkin(pTag.getInt("VariantSkin"));
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {

        if (pReason == MobSpawnType.BUCKET && pDataTag != null && pDataTag.contains("VariantSkin", 3)) {

            this.setVariantSkin(pDataTag.getInt("VariantSkin"));
            if (pDataTag.contains("Age")) {
                this.setAge(pDataTag.getInt("Age"));
            }

            this.setCanGrowUp(pDataTag.getBoolean("CanGrow"));

        }else {
            this.setVariantSkin(Util.getRandom(FairyShrimp.FairyShrimpVariant.values(), random).getID());
        }

        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public BreedableWaterAnimal getBreedOffspring(ServerLevel pLevel, BreedableWaterAnimal pOtherParent) {
        FairyShrimp baby = FintyEntities.FAIRY_SHRIMP.get().create(pLevel);
        FairyShrimp otherGuy = (FairyShrimp) pOtherParent;
        if (baby != null){
            baby.setVariantSkin(this.random.nextBoolean() ? this.getVariantSkin() : otherGuy.getVariantSkin());
            baby.setFromBucket(true);
        }
        return baby;
    }

    public void setupAnimationStates() {
        super.setupAnimationStates();

        if (this.circleTimeout <= 0 && this.isInWaterOrBubble()) {
            this.circleTimeout = this.random.nextInt(320) + 160;
            this.circleAnimationState.start(this.tickCount);
        } else if (this.circleTimeout > 0){
            --this.circleTimeout;
        }
    }

    @Override
    public boolean inRangeOfLeader() {
        return this.distanceToSqr(this.leader) <= 121.0D && this.distanceToSqr(this.leader) > 3D;
    }

    @Override
    public void spawnChildFromBreeding(ServerLevel pLevel, BreedableWaterAnimal pMate) {
        BreedableWaterAnimal ageablemob = this.getBreedOffspring(pLevel, pMate);
        BreedableWaterAnimal ageableMob2 = null;
        BreedableWaterAnimal ageableMob3 = null;
        BreedableWaterAnimal ageableMob4 = null;
        BreedableWaterAnimal ageableMob5 = null;

        final BreedableWaterAnimal.BabyFishSpawnEvent event = new BreedableWaterAnimal.BabyFishSpawnEvent(this, pMate, ageablemob);
        ageablemob = event.getChild();

        if (this.random.nextBoolean() || this.random.nextBoolean()){
            ageableMob2 = this.getBreedOffspring(pLevel, pMate);
            final BreedableWaterAnimal.BabyFishSpawnEvent event2 = new BreedableWaterAnimal.BabyFishSpawnEvent(this, pMate, ageableMob2);
            ageableMob2 = event2.getChild();

            if (this.random.nextBoolean()){
                ageableMob3 = this.getBreedOffspring(pLevel, pMate);
                final BreedableWaterAnimal.BabyFishSpawnEvent event3 = new BreedableWaterAnimal.BabyFishSpawnEvent(this, pMate, ageableMob3);
                ageableMob3 = event3.getChild();

                if (this.random.nextBoolean()){

                    ageableMob4 = this.getBreedOffspring(pLevel, pMate);
                    final BreedableWaterAnimal.BabyFishSpawnEvent event4 = new BreedableWaterAnimal.BabyFishSpawnEvent(this, pMate, ageableMob4);
                    ageableMob4 = event4.getChild();

                    if (this.random.nextBoolean()){
                        ageableMob5 = this.getBreedOffspring(pLevel, pMate);
                        final BreedableWaterAnimal.BabyFishSpawnEvent event5 = new BreedableWaterAnimal.BabyFishSpawnEvent(this, pMate, ageableMob5);
                        ageableMob5 = event5.getChild();
                    }
                }
            }
        }

        final boolean cancelled = net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
        if (cancelled) {
            //Reset the "inLove" state for the animals
            this.setAge(6000);
            pMate.setAge(6000);
            this.resetLove();
            pMate.resetLove();
            return;
        }
        if (ageablemob != null) {

            ageablemob.setAge(-12000);
            ageablemob.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
            this.finalizeSpawnChildFromBreeding(pLevel, pMate, ageablemob);
            pLevel.addFreshEntityWithPassengers(ageablemob);

            if (ageableMob2 != null){

                ageableMob2.setAge(-12000);
                ageableMob2.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
                this.finalizeSpawnChildFromBreeding(pLevel, pMate, ageableMob2);
                pLevel.addFreshEntityWithPassengers(ageableMob2);

                if (ageableMob3 != null){

                    ageableMob3.setAge(-12000);
                    ageableMob3.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
                    this.finalizeSpawnChildFromBreeding(pLevel, pMate, ageableMob3);
                    pLevel.addFreshEntityWithPassengers(ageableMob3);

                    if (ageableMob4 != null){

                        ageableMob4.setAge(-12000);
                        ageableMob4.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
                        this.finalizeSpawnChildFromBreeding(pLevel, pMate, ageableMob4);
                        pLevel.addFreshEntityWithPassengers(ageableMob4);

                        if (ageableMob5 != null){

                            ageableMob5.setAge(-12000);
                            ageableMob5.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
                            this.finalizeSpawnChildFromBreeding(pLevel, pMate, ageableMob5);
                            pLevel.addFreshEntityWithPassengers(ageableMob5);
                        }
                    }
                }
            }
        }
    }

    public enum FairyShrimpVariant implements StringRepresentable {
        ARTEMIA(0, "artemia"),
        BEAVERTAIL(1, "beavertail"),
        THAI(2, "thai"),
        HOUNEN(3, "hounen");

        private final int variant;
        private final String name;

        FairyShrimpVariant(int variant, String name){
            this.variant = variant;
            this.name = name;
        }

        public int getID(){
            return this.variant;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        public static final IntFunction<FairyShrimpVariant> BY_ID
                = ByIdMap.sparse(FairyShrimpVariant::getID, values(), ARTEMIA);

        public static final StringRepresentable.EnumCodec<FairyShrimpVariant> CODEC
                = StringRepresentable.fromEnum(FairyShrimpVariant::values);

        public static FairyShrimpVariant byId(int pId) {
            return BY_ID.apply(pId);
        }

        public static FairyShrimpVariant byName(String pName) {
            return CODEC.byName(pName, ARTEMIA);
        }
    }
}
