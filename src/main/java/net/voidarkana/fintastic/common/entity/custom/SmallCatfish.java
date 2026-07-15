package net.voidarkana.fintastic.common.entity.custom;

import net.minecraft.Util;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.voidarkana.fintastic.common.entity.FintyEntities;
import net.voidarkana.fintastic.common.entity.custom.ai.FishBreedGoal;
import net.voidarkana.fintastic.common.entity.custom.ai.FollowBottomDwellingLeaderGoal;
import net.voidarkana.fintastic.common.entity.custom.base.AbstractBottomSchooler;
import net.voidarkana.fintastic.common.entity.custom.base.BreedableWaterAnimal;
import net.voidarkana.fintastic.common.item.FintyItems;
import net.voidarkana.fintastic.util.FintyTags;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;
import java.util.stream.Stream;

public class SmallCatfish extends AbstractBottomSchooler {

    private static final Ingredient FOOD_ITEMS = Ingredient.of(FintyTags.Items.FISH_FEED);

    public boolean isFood(ItemStack stack) {
        return FOOD_ITEMS.test(stack);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        this.refreshDimensions();
        super.onSyncedDataUpdated(key);
    }

    @Override
    protected EntityDimensions getDefaultDimensions(Pose pose) {
        SmallCatfishVariant variant = SmallCatfishVariant.byId(this.getVariant());
        return switch (variant.getModel()){
            case 0 ->super.getDefaultDimensions(pose).scale(2F, 1F);
            case 2 ->super.getDefaultDimensions(pose).scale(1.25F, 1.25F);
            case 3 ->super.getDefaultDimensions(pose).scale(0.8F, 0.8F);
            default ->super.getDefaultDimensions(pose);
        };
    }

    public SmallCatfish(EntityType<? extends BreedableWaterAnimal> entityType, Level level) {
        super(entityType, level);
        this.refreshDimensions();
    }

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

        this.goalSelector.addGoal(4, new BottomDwellerSwimGoal(this));
        this.goalSelector.addGoal(10, new BottomMoveGoal(this, 1, 30));
        this.goalSelector.addGoal(2, new FollowBottomDwellingLeaderGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.7F);
    }

    @Nullable
    @Override
    public BreedableWaterAnimal getBreedOffspring(ServerLevel level, BreedableWaterAnimal otherParent) {
        SmallCatfish baby = FintyEntities.SMALL_CATFISH.get().create(level);
        if (baby != null){
            baby.setFromBucket(true);
            baby.setVariant(this.getRandom().nextBoolean() ? ((SmallCatfish)otherParent).getVariant() : this.getVariant());
        }
        return baby;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isFollower() && this.tickCount % 40 == 0){
            assert this.leader != null;
            this.setWantsToSwim(this.leader.getWantsToSwim());
            if (!this.getWantsToSwim()){
                this.swimmingTicks = 0;
                this.prevSwimTick = 0;
            }
        }
    }

    @Override
    public boolean canFlop() {
        return SmallCatfish.SmallCatfishVariant.byId(this.getVariant()).canFlop();
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(FintyItems.SMALL_CATFISH_BUCKET.get());
    }

    public boolean isFollower() {
        if (super.isFollower()) {
            if (!SmallCatfishVariant.byId(this.getVariant()).canSchool()) return false;
            assert this.leader != null;
            return SmallCatfishVariant.byId(this.getVariant()).isSameSpecies(SmallCatfishVariant.byId(this.leader.getVariant()));
        }
        return false;
    }

    @Override
    public boolean canBeFollowed() {
        return SmallCatfish.SmallCatfishVariant.byId(this.getVariant()).canSchool() && super.canBeFollowed();
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
        if (tag.contains("Variant"))
            this.setVariant(tag.getInt("Variant"));
        if (tag.contains("CanGrow"))
            this.setCanGrowUp(tag.getBoolean("CanGrow"));
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData) {
        if (spawnData instanceof SchoolSpawnGroupData fish$fishgroupdata){
            AbstractBottomSchooler leader = fish$fishgroupdata.leader;

            this.startFollowing(fish$fishgroupdata.leader);
            this.setVariant(leader.getVariant());
        }else {
            SmallCatfishVariant variant = Util.getRandom(SmallCatfishVariant.values(), random);
            this.setVariant(variant.getVariant());

            if (variant.canSchool())
                spawnData = new AbstractBottomSchooler.SchoolSpawnGroupData(this);
        }
        return super.finalizeSpawn(level, difficulty, reason, spawnData);
    }

    @Override
    public boolean canMate(BreedableWaterAnimal otherAnimal) {
        SmallCatfish otherGuy = (SmallCatfish) otherAnimal;
        return SmallCatfish.SmallCatfishVariant.byId(this.getVariant()).isSameSpecies(SmallCatfish.SmallCatfishVariant.byId(otherGuy.getVariant())) && super.canMate(otherAnimal);
    }

    @Override
    public void addFollowers(Stream<? extends AbstractBottomSchooler> followers) {
        followers.limit(this.getMaxSchoolSize() - this.schoolSize).filter((fish) -> fish != this).forEach((fish) -> {
            if (this.getVariant()==fish.getVariant()
                    && this.isBaby()==fish.isBaby()){
                fish.startFollowing(this);
            }
        });
    }

    public enum SmallCatfishVariant implements StringRepresentable{
        ANCHOR_CATFISH(0, "anchor_catfish"),
        BANJO_CATFISH(1, "banjo_catfish"),
        EEL_TAILED_BANJO_CATFISH(2, "eel_tailed_banjo_catfish"),

        YELLOWFIN_CORYDORAS(100, "yellowfin_corydoras"),
        ADOLFOS_CORYDORAS(101, "adolfos_corydoras"),
        ALBINO_CORYDORAS(102, "albino_corydoras"),
        BLACK_CORYDORAS(103, "black_corydoras"),
        BLUE_LASER_CORYDORAS(104, "blue_laser_corydoras"),
        BRONZE_CORYDORAS(105, "bronze_corydoras"),
        FIREBALL_CORYDORAS(106, "fireball_corydoras"),
        FLAGTAIL_PANDA_CORYDORAS(107, "flagtail_panda_corydoras"),
        GOLD_LASER_CORYDORAS(108, "gold_laser_corydoras"),
        GREEN_LASER_CORYDORAS(109, "green_laser_corydoras"),
        GUAPORE_CORYDORAS(110, "guapore_corydoras"),
        HOPLISOMA_SP(111, "hoplisoma_sp"),
        HORSEMANS_CORYDORAS(112, "horsemans_corydoras"),
        LONG_FIN_ALBINO_CORYDORAS(113, "long_fin_albino_corydoras"),
        OSTEOGASTER_SP(114, "osteogaster_sp"),
        PANDA_CORYDORAS(115, "panda_corydoras"),
        PANTANA_CORYDORAS(116, "pantana_corydoras"),
        PEPPERED_CORYDORAS(117, "peppered_corydoras"),
        SLATE_CORYDORAS(118, "slate_corydoras"),
        SMUDGE_SPOT_CORYDORAS(119, "smudge_spot_corydoras"),
        STERBAS_CORYDORAS(120, "sterbas_corydoras"),
        SWORD_CORYDORAS(121, "sword_corydoras"),
        TWO_TONE_CORYDORAS(122, "two_tone_corydoras"),
        VENEZUELA_CORYDORAS(123, "venezuela_corydoras"),
        WIDE_CORYDORAS(124, "wide_corydoras"),
        YELLOW_BRUSH_CORYDORAS(125, "yellow_brush_corydoras"),

        CHOCOLATE_TALKING_CATFISH(200, "chocolate_talking_catfish"),
        ROCK_BACU(201, "rock_bacu"),
        SPOTTED_RAPHAEL_CATFISH(202, "spotted_raphael_catfish"),
        STRIPED_RAPHAEL_CATFISH(203, "striped_raphael_catfish"),

        ALBINO_PYGMY_CORYDORAS(300, "albino_pygmy_corydoras"),
        DAINTY_CORYDORAS(301, "dainty_corydoras"),
        DWARF_CORYDORAS(302, "dwarf_corydoras"),
        PYGMY_CORYDORAS(303, "pygmy_corydoras");

        private final int variant;
        private final String name;

        SmallCatfishVariant(int variant, String name){
            this.variant = variant;
            this.name = name;
        }

        public int getVariant(){
            return this.variant;
        }

        public int getModel(){
            return getVariant()/100;
        }

        public boolean canFlop(){
            return getModel() != 0;
        }

        public boolean canSchool(){
            return getModel() == 1 || getModel() == 3;
        }

        public boolean isSameSpecies(SmallCatfishVariant otherVariant){
            return this == otherVariant || (this.isLaserCorydoras() && otherVariant.isLaserCorydoras())
                    || (this == ALBINO_PYGMY_CORYDORAS && otherVariant == PYGMY_CORYDORAS)
                    || (otherVariant == ALBINO_PYGMY_CORYDORAS && this == PYGMY_CORYDORAS);
        }

        public boolean isLaserCorydoras() {
            return this == BLUE_LASER_CORYDORAS || this == GREEN_LASER_CORYDORAS || this == GOLD_LASER_CORYDORAS;
        }

        public String getModelName(){
            return switch (getVariant()/100){
                case 1 -> "corydoras";
                case 2 -> "thorny";
                case 3 -> "tiny_cory";
                default -> "banjo";
            };
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        public static final IntFunction<SmallCatfishVariant> BY_ID
                = ByIdMap.sparse(SmallCatfishVariant::getVariant, values(), ANCHOR_CATFISH);

        public static final EnumCodec<SmallCatfishVariant> CODEC
                = StringRepresentable.fromEnum(SmallCatfishVariant::values);

        public static SmallCatfishVariant byId(int id) {
            return BY_ID.apply(id);
        }

        public static SmallCatfishVariant byName(String name) {
            return CODEC.byName(name, ANCHOR_CATFISH);
        }
    }
}
