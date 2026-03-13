package net.voidarkana.fintastic.common.entity.custom;

import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.voidarkana.fintastic.common.entity.FintyEntities;
import net.voidarkana.fintastic.common.entity.custom.ai.FishBreedGoal;
import net.voidarkana.fintastic.common.entity.custom.ai.boids.BoidGoal;
import net.voidarkana.fintastic.common.entity.custom.ai.boids.LimitSpeedAndLookInVelocityDirectionGoal;
import net.voidarkana.fintastic.common.entity.custom.ai.boids.OrganizeBoidsVariantGoal;
import net.voidarkana.fintastic.common.entity.custom.ai.boids.StayInWaterGoal;
import net.voidarkana.fintastic.common.entity.custom.base.BreedableWaterAnimal;
import net.voidarkana.fintastic.common.entity.custom.base.BucketableFishEntity;
import net.voidarkana.fintastic.common.entity.custom.base.VariantBoidingFish;
import net.voidarkana.fintastic.common.item.FintyItems;
import net.voidarkana.fintastic.util.FintyTags;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;


public class Moony extends VariantBoidingFish {

    public Moony(EntityType<? extends BucketableFishEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    private static final Ingredient FOOD_ITEMS = Ingredient.of(FintyTags.Items.FISH_FEED);

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.targetSelector.addGoal(0, (new HurtByTargetGoal(this)).setAlertOthers());

        this.goalSelector.addGoal(1, new PanicGoal(this, 1.5D));
        this.goalSelector.addGoal(1, new TemptGoal(this, 2D, FOOD_ITEMS, false));
        this.goalSelector.addGoal(1, new FishBreedGoal(this, 1.5D));

        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 8.0F, 1.6D, 1.4D, (entity) -> {
            if (entity instanceof Player player){
                return !player.isCreative() && !player.isSpectator() && !player.getItemBySlot(EquipmentSlot.HEAD).is(FintyItems.FISHING_HAT.get());
            }
            return false;}));

        this.goalSelector.addGoal(1, new OrganizeBoidsVariantGoal(this));

        this.goalSelector.addGoal(2, new BoidGoal(this, 0.2f, 0.75f, 8 / 20f, 1 / 20f));
        this.goalSelector.addGoal(2, new StayInWaterGoal(this));
        this.goalSelector.addGoal(2, new LimitSpeedAndLookInVelocityDirectionGoal(this, 0.65f));

        this.goalSelector.addGoal(6, new RandomSwimmingGoal(this, 1, 10));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.8F);
    }

    @Override
    public void saveToBucketTag(ItemStack bucket) {
        CompoundTag compoundnbt = bucket.getOrCreateTag();
        Bucketable.saveDefaultDataToBucketTag(this, bucket);
        compoundnbt.putFloat("Health", this.getHealth());
        compoundnbt.putInt("Age", this.getAge());
        compoundnbt.putBoolean("CanGrow", this.getCanGrowUp());
        compoundnbt.putInt("Variant", this.getVariant());
        if (this.hasCustomName()) {
            bucket.setHoverName(this.getCustomName());
        }
    }

    @Override
    public void loadFromBucketTag(CompoundTag pTag) {
        Bucketable.loadDefaultDataFromBucketTag(this, pTag);
        if (pTag.contains("Age")) {
            this.setAge(pTag.getInt("Age"));}
        if (pTag.contains("Variant")) {
            this.setAge(pTag.getInt("Variant"));}
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {

        if (pSpawnData == null)
            pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);

        if (pReason == MobSpawnType.BUCKET && pDataTag != null && pDataTag.contains("Variant", 3)) {
            this.setVariant(pDataTag.getInt("Variant"));
            if (pDataTag.contains("Age")) {
                this.setAge(pDataTag.getInt("Age"));}
            this.setCanGrowUp(pDataTag.getBoolean("CanGrow"));
        }else{

            if(pReason == MobSpawnType.SPAWN_EGG || (pReason == MobSpawnType.BUCKET && pDataTag == null)){
                this.setVariant(this.random.nextInt(3));
            }else {
                int variant;

                if (pSpawnData instanceof MoonyGroupData groupData){

                    variant = groupData.getVariant();
                    this.startFollowing(groupData.leader);

                }else {

                    if (pLevel.getBiome(this.blockPosition()).is(BiomeTags.IS_BEACH)){
                        variant = switch (this.getRandom().nextInt(0, 4)) {
                            case 1 -> MoonyVariant.AFRICAN_MOONY_1.getJoinedVariant();
                            case 2 -> MoonyVariant.DWARF_MOONY_1.getJoinedVariant();
                            case 3 -> MoonyVariant.DWARF_MOONY_2.getJoinedVariant();
                            default -> MoonyVariant.AFRICAN_MOONY_2.getJoinedVariant();
                        };
                    }else if (pLevel.getBiome(this.blockPosition()).is(BiomeTags.IS_OCEAN)){
                        if (this.getRandom().nextBoolean())
                            variant = MoonyVariant.SILVER_MOONY.getJoinedVariant();
                        else
                            variant = MoonyVariant.FULL_MOONY.getJoinedVariant();
                    }else {
                        variant = Util.getRandom(MoonyVariant.values(), this.random).getJoinedVariant();
                    }

                    pSpawnData = new MoonyGroupData(this, variant);
                }

                this.setVariant(variant);
            }
        }

        return pSpawnData;
    }

    @Nullable
    @Override
    public BreedableWaterAnimal getBreedOffspring(ServerLevel pLevel, BreedableWaterAnimal pOtherParent) {
        Moony baby = FintyEntities.MOONY.get().create(pLevel);
        if (baby != null){
            baby.setFromBucket(true);
            baby.setVariant(this.getVariant());
        }
        return baby;
    }

    @Override
    public boolean canMate(BreedableWaterAnimal pOtherAnimal) {
        Moony mate = (Moony) pOtherAnimal;
        return super.canMate(pOtherAnimal) && mate.getVariant() == this.getVariant();
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(FintyItems.MOONY_BUCKET.get());
    }

    static class MoonyGroupData extends SchoolSpawnGroupData {
        final int variant;

        MoonyGroupData(Moony pLeader, int pVariant) {
            super(pLeader);
            this.variant = pVariant;
        }

        public int getVariant(){
            return variant;
        }
    }

    public enum MoonyVariant implements StringRepresentable {
        DWARF_MOONY_1(0, "dwarf_moony_1"),
        DWARF_MOONY_2(1, "dwarf_moony_2"),

        AFRICAN_MOONY_1(10, "african_moony_1"),
        AFRICAN_MOONY_2(11, "african_moony_2"),

        SILVER_MOONY(20, "silver_moony"),
        FULL_MOONY(21, "full_moony");

        private final int joinedVariant;
        private final String name;

        MoonyVariant(int variant, String name){
            this.joinedVariant = variant;
            this.name = name;
        }

        public int getJoinedVariant(){
            return this.joinedVariant;
        }

        public int getModel(){
            return this.joinedVariant/10;
        }

        public int getSkin(){
            return this.joinedVariant%10;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        public static final IntFunction<MoonyVariant> BY_ID
                = ByIdMap.sparse(MoonyVariant::getJoinedVariant, values(), DWARF_MOONY_1);

        public static final StringRepresentable.EnumCodec<MoonyVariant> CODEC
                = StringRepresentable.fromEnum(MoonyVariant::values);

        public static MoonyVariant byId(int pId) {
            return BY_ID.apply(pId);
        }

        public static MoonyVariant byName(String pName) {
            return CODEC.byName(pName, DWARF_MOONY_1);
        }
    }

    @Override
    public boolean canBabiesSchoolWithAdults() {
        return true;
    }
}
