package net.voidarkana.fintastic.common.entity.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.Tags;
import net.voidarkana.fintastic.common.entity.FintyEntities;
import net.voidarkana.fintastic.common.entity.custom.ai.FishBreedGoal;
import net.voidarkana.fintastic.common.entity.custom.ai.FollowIndiscriminateSchoolLeaderGoal;
import net.voidarkana.fintastic.common.entity.custom.base.BreedableWaterAnimal;
import net.voidarkana.fintastic.common.entity.custom.base.SchoolingFish;
import net.voidarkana.fintastic.common.item.FintyItems;
import net.voidarkana.fintastic.util.FintyTags;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

public class Copepod extends SchoolingFish {

    private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.DRIED_KELP);

    public final AnimationState legsAnimationState = new AnimationState();
    private int legsIdle = this.random.nextInt(120) + 120;

    public Copepod(EntityType<? extends BreedableWaterAnimal> entityType, Level level) {
        super(entityType, level);
    }

    public boolean isFood(ItemStack stack) {
        return FOOD_ITEMS.test(stack);
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

        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, WaterAnimal.class, 8.0F, 1.6D, 1.4D, (entity) -> entity.getType().is(FintyTags.EntityTypes.PREDATOR_FISH)));

        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 25));
    }

    public boolean isPlankton() {
        String s = ChatFormatting.stripFormatting(this.getName().getString());
        return s.equalsIgnoreCase("plankton") || s.equalsIgnoreCase("sheldon");
    }

    public boolean isJiggly() {
        String s = ChatFormatting.stripFormatting(this.getName().getString());
        return s.equalsIgnoreCase("jiggly") || s.equalsIgnoreCase("cyclops") || s.equalsIgnoreCase("mylo") || s.equalsIgnoreCase("mylo the jiggly cyclops") || s.equalsIgnoreCase("jiggly cyclops");
    }

    public boolean isMylops() {
        String s = ChatFormatting.stripFormatting(this.getName().getString());
        return s.equalsIgnoreCase("one big eye") || s.equalsIgnoreCase("mylops");
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(FintyItems.COPEPOD_BUCKET.get());
    }

    @Override
    public void saveToBucketTag(ItemStack bucket) {
        Bucketable.saveDefaultDataToBucketTag(this, bucket);
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, bucket, compoundnbt -> {
            compoundnbt.putFloat("Health", this.getHealth());
            compoundnbt.putInt("Variant", this.getVariant());

            compoundnbt.putInt("Age", this.getAge());
            compoundnbt.putBoolean("CanGrow", this.getCanGrowUp());
        });
        if (this.hasCustomName()) {
            bucket.set(DataComponents.CUSTOM_NAME, this.getCustomName());
        }
    }

    @Override
    public void loadFromBucketTag(CompoundTag tag) {
        Bucketable.loadDefaultDataFromBucketTag(this, tag);
        if (tag.contains("Age"))
            this.setAge(tag.getInt("Age"));
        if (tag.contains("Variant", 3))
            this.setVariant(tag.getInt("Variant"));
        if (tag.contains("CanGrow"))
            this.setCanGrowUp(tag.getBoolean("CanGrow"));
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData) {

        if (reason.equals(MobSpawnType.SPAWN_EGG) || reason == MobSpawnType.BUCKET){
            this.setVariant(Util.getRandom(Copepod.CopepodVariant.values(), random).getID());
        }else
        {
            if (level.getBiome(this.blockPosition()).is(BiomeTags.IS_OCEAN)){
                this.setVariant(this.random.nextBoolean() ? CopepodVariant.RED.getID() : CopepodVariant.GREEN.getID());
            }else {
                this.setVariant(this.random.nextBoolean() ? CopepodVariant.YELLOW.getID() : CopepodVariant.ORANGE.getID());
            }
        }

        return super.finalizeSpawn(level, difficulty, reason, spawnData);
    }

    @Override
    public BreedableWaterAnimal getBreedOffspring(ServerLevel level, BreedableWaterAnimal otherParent) {
        Copepod baby = FintyEntities.COPEPOD.get().create(level);
        Copepod otherGuy = (Copepod) otherParent;
        if (baby != null){
            baby.setVariant(this.random.nextBoolean() ? this.getVariant() : otherGuy.getVariant());
            baby.setFromBucket(true);
        }
        return baby;
    }

    public void setupAnimationStates() {
        super.setupAnimationStates();

        if (this.legsIdle <= 0 && this.isInWaterOrBubble()) {
            this.legsIdle = this.random.nextInt(120) + 120;
            this.legsAnimationState.start(this.tickCount);
        } else if (this.legsIdle > 0){
            --this.legsIdle;
        }
    }

    @Override
    public boolean inRangeOfLeader() {
        assert this.leader != null;
        return this.distanceToSqr(this.leader) <= 121.0D && this.distanceToSqr(this.leader) > 3D;
    }

    @Override
    public void spawnChildFromBreeding(ServerLevel level, BreedableWaterAnimal mate) {
        BreedableWaterAnimal ageablemob = this.getBreedOffspring(level, mate);
        BreedableWaterAnimal ageableMob2 = null;
        BreedableWaterAnimal ageableMob3 = null;
        BreedableWaterAnimal ageableMob4 = null;
        BreedableWaterAnimal ageableMob5 = null;

        final BabyFishSpawnEvent event = new BabyFishSpawnEvent(this, mate, ageablemob);
        ageablemob = event.getChild();

        if (this.random.nextBoolean() || this.random.nextBoolean()){
            ageableMob2 = this.getBreedOffspring(level, mate);
            final BabyFishSpawnEvent event2 = new BabyFishSpawnEvent(this, mate, ageableMob2);
            ageableMob2 = event2.getChild();

            if (this.random.nextBoolean()){
                ageableMob3 = this.getBreedOffspring(level, mate);
                final BabyFishSpawnEvent event3 = new BabyFishSpawnEvent(this, mate, ageableMob3);
                ageableMob3 = event3.getChild();

                if (this.random.nextBoolean()){

                    ageableMob4 = this.getBreedOffspring(level, mate);
                    final BabyFishSpawnEvent event4 = new BabyFishSpawnEvent(this, mate, ageableMob4);
                    ageableMob4 = event4.getChild();

                    if (this.random.nextBoolean()){
                        ageableMob5 = this.getBreedOffspring(level, mate);
                        final BabyFishSpawnEvent event5 = new BabyFishSpawnEvent(this, mate, ageableMob5);
                        ageableMob5 = event5.getChild();
                    }
                }
            }
        }

        final boolean cancelled = NeoForge.EVENT_BUS.post(event).isCanceled();
        if (cancelled) {
            //Reset the "inLove" state for the animals
            this.setAge(6000);
            mate.setAge(6000);
            this.resetLove();
            mate.resetLove();
            return;
        }
        if (ageablemob != null) {

            ageablemob.setAge(-12000);
            ageablemob.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
            this.finalizeSpawnChildFromBreeding(level, mate, ageablemob);
            level.addFreshEntityWithPassengers(ageablemob);

            if (ageableMob2 != null){

                ageableMob2.setAge(-12000);
                ageableMob2.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
                this.finalizeSpawnChildFromBreeding(level, mate, ageableMob2);
                level.addFreshEntityWithPassengers(ageableMob2);

                if (ageableMob3 != null){

                    ageableMob3.setAge(-12000);
                    ageableMob3.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
                    this.finalizeSpawnChildFromBreeding(level, mate, ageableMob3);
                    level.addFreshEntityWithPassengers(ageableMob3);

                    if (ageableMob4 != null){

                        ageableMob4.setAge(-12000);
                        ageableMob4.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
                        this.finalizeSpawnChildFromBreeding(level, mate, ageableMob4);
                        level.addFreshEntityWithPassengers(ageableMob4);

                        if (ageableMob5 != null){

                            ageableMob5.setAge(-12000);
                            ageableMob5.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
                            this.finalizeSpawnChildFromBreeding(level, mate, ageableMob5);
                            level.addFreshEntityWithPassengers(ageableMob5);
                        }
                    }
                }
            }
        }
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader world) {
        int y = Math.abs(world.getMaxBuildHeight()) - pos.getY();
        return 1f / (y == 0 ? 1 : y);
    }

    public enum CopepodVariant implements StringRepresentable {
        GREEN(0, "saltwater_green"),
        RED(1, "saltwater_red"),
        ORANGE(2, "freshwater_orange"),
        YELLOW(3, "freshwater_yellow");

        private final int variant;
        private final String name;

        CopepodVariant(int variant, String name){
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

        public static final IntFunction<CopepodVariant> BY_ID
                = ByIdMap.sparse(CopepodVariant::getID, values(), GREEN);

        public static final EnumCodec<CopepodVariant> CODEC
                = StringRepresentable.fromEnum(CopepodVariant::values);

        public static CopepodVariant byId(int id) {
            return BY_ID.apply(id);
        }

        public static CopepodVariant byName(String name) {
            return CODEC.byName(name, GREEN);
        }
    }


    public static boolean checkSurfaceWaterAnimalSpawnRules(EntityType<? extends WaterAnimal> waterAnimal, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        int i = level.getSeaLevel();
        int j = i - 13;
        return (!level.getBiome(pos).is(Tags.Biomes.IS_COLD) || (level.getBiome(pos).is(Tags.Biomes.IS_COLD) && level.getBiome(pos).is(BiomeTags.IS_OCEAN)))
                && pos.getY() >= j && pos.getY() <= i && level.getFluidState(pos.below()).is(FluidTags.WATER) && level.getBlockState(pos.above()).is(Blocks.WATER);
    }
}
