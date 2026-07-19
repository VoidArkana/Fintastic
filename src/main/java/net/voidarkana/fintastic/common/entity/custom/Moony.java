package net.voidarkana.fintastic.common.entity.custom;

import net.minecraft.Util;
import net.minecraft.core.component.DataComponents;
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
import net.minecraft.world.item.component.CustomData;
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

    public Moony(EntityType<? extends BucketableFishEntity> entityType, Level level) {
        super(entityType, level);
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
        this.goalSelector.addGoal(2, new LimitSpeedAndLookInVelocityDirectionGoal(this, 0.5f, 0.35F));

        this.goalSelector.addGoal(6, new RandomSwimmingGoal(this, 1, 10));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.8F);
    }

    @Override
    public void saveToBucketTag(ItemStack bucket) {
        Bucketable.saveDefaultDataToBucketTag(this, bucket);
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, bucket, compoundnbt -> {
            compoundnbt.putFloat("Health", this.getHealth());
            compoundnbt.putInt("Age", this.getAge());
            compoundnbt.putBoolean("CanGrow", this.getCanGrowUp());
            compoundnbt.putInt("Variant", this.getVariant());
        });
        if (this.hasCustomName()) {
            bucket.set(DataComponents.CUSTOM_NAME, this.getCustomName());
        }
    }

    @Override
    public void loadFromBucketTag(CompoundTag tag) {
        Bucketable.loadDefaultDataFromBucketTag(this, tag);
        if (tag.contains("Age")) {
            this.setAge(tag.getInt("Age"));}
        if (tag.contains("Variant")) {
            this.setAge(tag.getInt("Variant"));}
        if (tag.contains("Variant", 3)) {
            this.setVariant(tag.getInt("Variant"));}
        if (tag.contains("CanGrow")) {
            this.setCanGrowUp(tag.getBoolean("CanGrow"));}
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData) {

        if (spawnData == null)
            spawnData = super.finalizeSpawn(level, difficulty, reason, spawnData);


        if(reason == MobSpawnType.SPAWN_EGG || reason == MobSpawnType.BUCKET){
            this.setVariant(Util.getRandom(MoonyVariant.values(), this.random).getJoinedVariant());
        }else {
            int variant;

            if (spawnData instanceof MoonyGroupData groupData){

                variant = groupData.getVariant();
                this.startFollowing(groupData.leader);

            }else {

                if (level.getBiome(this.blockPosition()).is(BiomeTags.IS_BEACH)){
                    variant = switch (this.getRandom().nextInt(0, 4)) {
                        case 1 -> MoonyVariant.AFRICAN_MOONY_1.getJoinedVariant();
                        case 2 -> MoonyVariant.DWARF_MOONY_1.getJoinedVariant();
                        case 3 -> MoonyVariant.DWARF_MOONY_2.getJoinedVariant();
                        default -> MoonyVariant.AFRICAN_MOONY_2.getJoinedVariant();
                    };
                }else if (level.getBiome(this.blockPosition()).is(BiomeTags.IS_OCEAN)){
                    if (this.getRandom().nextBoolean())
                        variant = MoonyVariant.SILVER_MOONY.getJoinedVariant();
                    else
                        variant = MoonyVariant.FULL_MOONY.getJoinedVariant();
                }else {
                    variant = Util.getRandom(MoonyVariant.values(), this.random).getJoinedVariant();
                }

                spawnData = new MoonyGroupData(this, variant);
            }

            this.setVariant(variant);
        }

        return spawnData;
    }

    @Nullable
    @Override
    public BreedableWaterAnimal getBreedOffspring(ServerLevel level, BreedableWaterAnimal otherParent) {
        Moony baby = FintyEntities.MOONY.get().create(level);
        if (baby != null){
            baby.setFromBucket(true);
            baby.setVariant(this.getVariant());
        }
        return baby;
    }

    @Override
    public boolean canMate(BreedableWaterAnimal otherAnimal) {
        Moony mate = (Moony) otherAnimal;
        return super.canMate(otherAnimal) && mate.getVariant() == this.getVariant();
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(FintyItems.MOONY_BUCKET.get());
    }

    static class MoonyGroupData extends SchoolSpawnGroupData {
        final int variant;

        MoonyGroupData(Moony leader, int variant) {
            super(leader);
            this.variant = variant;
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

        public static MoonyVariant byId(int id) {
            return BY_ID.apply(id);
        }

        public static MoonyVariant byName(String name) {
            return CODEC.byName(name, DWARF_MOONY_1);
        }
    }

}
