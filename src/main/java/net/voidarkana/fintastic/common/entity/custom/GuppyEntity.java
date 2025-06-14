package net.voidarkana.fintastic.common.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.voidarkana.fintastic.common.entity.YAFMEntities;
import net.voidarkana.fintastic.common.entity.custom.ai.FishBreedGoal;
import net.voidarkana.fintastic.common.entity.custom.ai.FollowIndiscriminateSchoolLeaderGoal;
import net.voidarkana.fintastic.common.entity.custom.base.BreedableWaterAnimal;
import net.voidarkana.fintastic.common.entity.custom.base.SchoolingFish;
import net.voidarkana.fintastic.common.item.YAFMItems;
import net.voidarkana.fintastic.util.YAFMTags;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class GuppyEntity extends SchoolingFish implements GeoEntity {

    private static final Ingredient FOOD_ITEMS = Ingredient.of(YAFMTags.Items.FISH_FEED);

    protected static final RawAnimation SWIM = RawAnimation.begin().thenLoop("animation.guppy.swim");
    protected static final RawAnimation FLOP = RawAnimation.begin().thenLoop("animation.guppy.flop");

    private static final EntityDataAccessor<Integer> FIN_MODEL = SynchedEntityData.defineId(GuppyEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> FIN_COLOR = SynchedEntityData.defineId(GuppyEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TAIL_MODEL = SynchedEntityData.defineId(GuppyEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TAIL_COLOR = SynchedEntityData.defineId(GuppyEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PATTERN_1 = SynchedEntityData.defineId(GuppyEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PATTERN_2 = SynchedEntityData.defineId(GuppyEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PATTERN_1_COLOR = SynchedEntityData.defineId(GuppyEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PATTERN_2_COLOR = SynchedEntityData.defineId(GuppyEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> HAS_PATTERN_1 = SynchedEntityData.defineId(GuppyEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HAS_PATTERN_2 = SynchedEntityData.defineId(GuppyEntity.class, EntityDataSerializers.BOOLEAN);

    public GuppyEntity(EntityType<? extends BreedableWaterAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 2.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.65F);
    }

    public boolean isFood(ItemStack pStack) {
        return FOOD_ITEMS.test(pStack);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new FishBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 2D, FOOD_ITEMS, false));
        this.goalSelector.addGoal(5, new FollowIndiscriminateSchoolLeaderGoal(this));
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.5D));

        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 8.0F, 1.6D, 1.4D, (entity) -> {
            if (entity instanceof Player player){
                return !player.isCreative() && !player.isSpectator() && !player.getItemBySlot(EquipmentSlot.HEAD).is(YAFMItems.FISHING_HAT.get());
            }
            return false;}));

        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 25));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FIN_MODEL, 0);
        this.entityData.define(FIN_COLOR, 0);
        this.entityData.define(TAIL_MODEL, 0);
        this.entityData.define(TAIL_COLOR, 0);
        this.entityData.define(PATTERN_1, 0);
        this.entityData.define(PATTERN_2, 0);
        this.entityData.define(PATTERN_1_COLOR, 0);
        this.entityData.define(PATTERN_2_COLOR, 0);
        this.entityData.define(HAS_PATTERN_1, false);
        this.entityData.define(HAS_PATTERN_2, false);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("FinModel", this.getFinModel());
        compound.putInt("FinColor", this.getFinColor());
        compound.putInt("TailModel", this.getTailModel());
        compound.putInt("TailColor", this.getTailColor());

        compound.putBoolean("HasMainPattern", this.getHasMainPattern());
        compound.putInt("MainPattern", this.getMainPattern());
        compound.putInt("MainPatternColor", this.getMainPatternColor());

        compound.putBoolean("HasSecondaryPattern", this.getHasSecondPattern());
        compound.putInt("SecondaryPattern", this.getSecondPattern());
        compound.putInt("SecondaryPatternColor", this.getSecondPatternColor());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setFinModel(compound.getInt("FinModel"));
        this.setFinColor(compound.getInt("FinColor"));
        this.setTailModel(compound.getInt("TailModel"));
        this.setTailColor(compound.getInt("TailColor"));


        this.setHasMainPattern(compound.getBoolean("HasMainPattern"));
        this.setMainPattern(compound.getInt("MainPattern"));
        this.setMainPatternColor(compound.getInt("MainPatternColor"));

        this.setHasSecondPattern(compound.getBoolean("HasSecondaryPattern"));
        this.setSecondPattern(compound.getInt("SecondaryPattern"));
        this.setSecondPatternColor(compound.getInt("SecondaryPatternColor"));
    }

    public int getFinModel() {
        return this.entityData.get(FIN_MODEL);
    }

    public void setFinModel(int variant) {
        this.entityData.set(FIN_MODEL, variant);
    }


    public int getFinColor() {
        return this.entityData.get(FIN_COLOR);
    }

    public void setFinColor(int variant) {
        this.entityData.set(FIN_COLOR, variant);
    }


    public int getTailModel() {
        return this.entityData.get(TAIL_MODEL);
    }

    public void setTailModel(int variant) {
        this.entityData.set(TAIL_MODEL, variant);
    }


    public int getTailColor() {
        return this.entityData.get(TAIL_COLOR);
    }

    public void setTailColor(int variant) {
        this.entityData.set(TAIL_COLOR, variant);
    }


    public Boolean getHasMainPattern() {
        return this.entityData.get(HAS_PATTERN_1);
    }

    public void setHasMainPattern(Boolean variant) {
        this.entityData.set(HAS_PATTERN_1, variant);
    }

    public int getMainPattern() {
        return this.entityData.get(PATTERN_1);
    }

    public void setMainPattern(int variant) {
        this.entityData.set(PATTERN_1, variant);
    }

    public int getMainPatternColor() {
        return this.entityData.get(PATTERN_1_COLOR);
    }

    public void setMainPatternColor(int variant) {
        this.entityData.set(PATTERN_1_COLOR, variant);
    }


    public Boolean getHasSecondPattern() {
        return this.entityData.get(HAS_PATTERN_2);
    }

    public void setHasSecondPattern(Boolean variant) {
        this.entityData.set(HAS_PATTERN_2, variant);
    }

    public int getSecondPattern() {
        return this.entityData.get(PATTERN_2);
    }

    public void setSecondPattern(int variant) {
        this.entityData.set(PATTERN_2, variant);
    }

    public int getSecondPatternColor() {
        return this.entityData.get(PATTERN_2_COLOR);
    }

    public void setSecondPatternColor(int variant) {
        this.entityData.set(PATTERN_2_COLOR, variant);
    }


    @Override
    public void saveToBucketTag(ItemStack bucket) {
        CompoundTag compoundnbt = bucket.getOrCreateTag();
        Bucketable.saveDefaultDataToBucketTag(this, bucket);
        compoundnbt.putFloat("Health", this.getHealth());
        compoundnbt.putInt("VariantSkin", this.getVariantSkin());
        compoundnbt.putInt("FinModel", this.getFinModel());
        compoundnbt.putInt("FinColor", this.getFinColor());
        compoundnbt.putInt("TailModel", this.getTailModel());
        compoundnbt.putInt("TailColor", this.getTailColor());

        compoundnbt.putBoolean("HasMainPattern", this.getHasMainPattern());
        compoundnbt.putInt("MainPattern", this.getMainPattern());
        compoundnbt.putInt("MainPatternColor", this.getMainPatternColor());

        compoundnbt.putBoolean("HasSecondaryPattern", this.getHasSecondPattern());
        compoundnbt.putInt("SecondaryPattern", this.getSecondPattern());
        compoundnbt.putInt("SecondaryPatternColor", this.getSecondPatternColor());

        compoundnbt.putInt("Age", this.getAge());

        compoundnbt.putBoolean("CanGrow", this.getCanGrowUp());

        if (this.hasCustomName()) {
            bucket.setHoverName(this.getCustomName());
        }
    }

    @Override
    public void loadFromBucketTag(CompoundTag pTag) {
        Bucketable.loadDefaultDataFromBucketTag(this, pTag);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {

        if (pReason == MobSpawnType.BUCKET && pDataTag != null && pDataTag.contains("VariantSkin", 3)) {

            this.setVariantSkin(pDataTag.getInt("VariantSkin"));
            this.setFinModel(pDataTag.getInt("FinModel"));
            this.setFinColor(pDataTag.getInt("FinColor"));
            this.setTailModel(pDataTag.getInt("TailModel"));
            this.setTailColor(pDataTag.getInt("TailColor"));

            this.setHasMainPattern(pDataTag.getBoolean("HasMainPattern"));
            this.setMainPattern(pDataTag.getInt("MainPattern"));
            this.setMainPatternColor(pDataTag.getInt("MainPatternColor"));

            this.setHasSecondPattern(pDataTag.getBoolean("HasSecondaryPattern"));
            this.setSecondPattern(pDataTag.getInt("SecondaryPattern"));
            this.setSecondPatternColor(pDataTag.getInt("SecondaryPatternColor"));

            if (pDataTag.contains("Age")) {
                this.setAge(pDataTag.getInt("Age"));
            }

            this.setCanGrowUp(pDataTag.getBoolean("CanGrow"));

        }else{

            this.setVariantSkin(this.random.nextInt(12));

            this.setFinModel(this.random.nextInt(2));
            this.setFinColor(this.random.nextInt(22));

            //0 checkered_front (checkered1)
            //1 checkered_back (checkered2)
            //2 line_up (line1)
            //3 line_down (line2)
            //4 striped_forward (striped1)
            //5 striped_backward (striped2)
            //6 mask
            //7 koi
            //8 dipped
            //9 belly

            this.setTailModel(this.random.nextInt(19));
            this.setTailColor(this.random.nextInt(22));

            //10 fullbody

            this.setHasMainPattern(this.random.nextInt(3)==0);
            this.setMainPattern(this.random.nextInt(11));
            this.setMainPatternColor(this.random.nextInt(22));


            this.setHasSecondPattern(this.random.nextInt(3)==0);
            this.setSecondPattern(this.random.nextInt(18));
            this.setSecondPatternColor(this.random.nextInt(22));

            //10 dot_tail (dot1)
            //11 dot_mid_up (dot2)
            //12 dot_mid_down (dot3)
            //13 dot_back (dot4)
            //14 dot_front (dot5)
            //15 double_dot
            //16 coat
            //17 bar
        }

        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    public static String getFinsName(int fin){
        if (fin == 0){
            return "jumbo";
        }else{
            return "regular";
        }
    }

    public static String getTailName(int pattern){
        return switch (pattern){
            case 1 -> "btmpin";
            case 2 -> "btmsword";
            case 3 -> "drkcapped";
            case 4 -> "drkmoon";
            case 5 -> "drkmosaic";
            case 6 -> "drkstreak";
            case 7 -> "flag";
            case 8 -> "lhtcapped";
            case 9 -> "lhtmoon";
            case 10 -> "lhtmosaic";
            case 11 -> "lhtstreak";
            case 12 -> "lyre";
            case 13 -> "scissor";
            case 14 -> "square";
            case 15 -> "sunrise";
            case 16 -> "sword";
            case 17 -> "toppin";
            case 18 -> "topsword";
            default -> "round";
        };
    }

    public static String getMainPatternName(int pattern){
        return switch (pattern){
            case 1 -> "checkered2";
            case 2 -> "line1";
            case 3 -> "line2";
            case 4 -> "striped1";
            case 5 -> "striped2";
            case 6 -> "mask";
            case 7 -> "koi";
            case 8 -> "dipped";
            case 9 -> "belly";
            case 10 -> "fullbody";
            default -> "checkered1";
        };
    }

    public static String getSecondPatternName(int pattern){
        return switch (pattern){
            case 1 -> "checkered2";
            case 2 -> "line1";
            case 3 -> "line2";
            case 4 -> "striped1";
            case 5 -> "striped2";
            case 6 -> "mask";
            case 7 -> "koi";
            case 8 -> "dipped";
            case 9 -> "belly";
            case 10 -> "dot1";
            case 11 -> "dot2";
            case 12 -> "dot3";
            case 13 -> "dot4";
            case 14 -> "dot5";
            case 15 -> "doubledot";
            case 16 -> "coat";
            case 17 -> "bar";
            default -> "checkered1";
        };
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController[]{new AnimationController(this, "Normal", 5, this::Controller)});
    }

    protected <E extends GuppyEntity> PlayState Controller(AnimationState<E> event) {
        if (this.isInWater()){
            event.setAndContinue(SWIM);

            if (this.isBaby()){
                event.getController().setAnimationSpeed(2d);
            }

        }else{
            event.setAndContinue(FLOP);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(YAFMItems.GUPPY_BUCKET.get());
    }

    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        if (itemstack.is(YAFMItems.REGULAR_FEED.get())){
            this.setFeedQuality(0);
        }
        if (itemstack.is(YAFMItems.QUALITY_FEED.get())){
            this.setFeedQuality(1);
        }
        if (itemstack.is(YAFMItems.GREAT_FEED.get())){
            this.setFeedQuality(2);
        }
        if (itemstack.is(YAFMItems.PREMIUM_FEED.get())){
            this.setFeedQuality(3);
        }

        return super.mobInteract(pPlayer, pHand);
    }

    @Nullable
    @Override
    public BreedableWaterAnimal getBreedOffspring(ServerLevel pLevel, BreedableWaterAnimal pOtherParent) {
        GuppyEntity otherParent = (GuppyEntity) pOtherParent;
        GuppyEntity baby = YAFMEntities.GUPPY.get().create(pLevel);

        if (baby != null){

            int lowerQuality = Math.min(this.getFeedQuality(), otherParent.getFeedQuality());
            int skin;
            int finModel;
            int finColor;
            int tailModel;
            int tailColor;
            boolean hasMainPattern;
            int mainPatternType;
            int mainPatternColor;
            boolean hasSecondPattern;
            int secondPatternType;
            int secondPatternColor;

            switch (lowerQuality){

                case 1:
                    if (this.random.nextBoolean()){
                        skin = this.random.nextInt(12);
                        finModel = this.random.nextInt(2);
                        finColor = this.random.nextInt(22);

                        tailModel = this.random.nextInt(19);
                        tailColor = this.random.nextInt(22);

                        hasMainPattern = this.random.nextInt(3)==0;
                        mainPatternType = this.random.nextInt(11);
                        mainPatternColor = this.random.nextInt(22);

                        hasSecondPattern = this.random.nextInt(3)==0;
                        secondPatternType = this.random.nextInt(18);
                        secondPatternColor = this.random.nextInt(22);
                    }else {
                        skin = this.random.nextBoolean() ? this.getVariantSkin() : otherParent.getVariantSkin();
                        finModel = this.random.nextBoolean() ? this.getFinModel() : otherParent.getFinModel();
                        finColor = this.random.nextBoolean() ? this.getFinColor() : otherParent.getFinColor();

                        tailModel = this.random.nextBoolean() ? this.getTailModel() : otherParent.getTailModel();
                        tailColor = this.random.nextBoolean() ? this.getTailColor() : otherParent.getTailColor();

                        hasMainPattern = this.random.nextBoolean() ? this.getHasMainPattern() : otherParent.getHasMainPattern();
                        mainPatternType = this.random.nextBoolean() ? this.getMainPattern() : otherParent.getMainPattern();
                        mainPatternColor = this.random.nextBoolean() ? this.getMainPatternColor() : otherParent.getMainPatternColor();

                        hasSecondPattern = this.random.nextBoolean() ? this.getHasSecondPattern() : otherParent.getHasSecondPattern();
                        secondPatternType = this.random.nextBoolean() ? this.getSecondPattern() : otherParent.getSecondPattern();
                        secondPatternColor = this.random.nextBoolean() ? this.getSecondPatternColor(): otherParent.getSecondPatternColor();
                    }
                    break;

                case 2:
                    skin = this.random.nextBoolean() ? this.getVariantSkin() : otherParent.getVariantSkin();
                    finModel = this.random.nextBoolean() ? this.getFinModel() : otherParent.getFinModel();
                    finColor = this.random.nextBoolean() ? this.getFinColor() : otherParent.getFinColor();

                    tailModel = this.random.nextBoolean() ? this.getTailModel() : otherParent.getTailModel();
                    tailColor = this.random.nextBoolean() ? this.getTailColor() : otherParent.getTailColor();

                    hasMainPattern = this.random.nextBoolean() ? this.getHasMainPattern() : otherParent.getHasMainPattern();
                    mainPatternType = this.random.nextBoolean() ? this.getMainPattern() : otherParent.getMainPattern();
                    mainPatternColor = this.random.nextBoolean() ? this.getMainPatternColor() : otherParent.getMainPatternColor();

                    hasSecondPattern = this.random.nextBoolean() ? this.getHasSecondPattern() : otherParent.getHasSecondPattern();
                    secondPatternType = this.random.nextBoolean() ? this.getSecondPattern() : otherParent.getSecondPattern();
                    secondPatternColor = this.random.nextBoolean() ? this.getSecondPatternColor(): otherParent.getSecondPatternColor();
                    break;

                case 3:
                    boolean parent = this.random.nextBoolean();

                    skin = parent ? this.getVariantSkin() : otherParent.getVariantSkin();
                    finModel = parent ? this.getFinModel() : otherParent.getFinModel();
                    finColor = parent ? this.getFinColor() : otherParent.getFinColor();

                    tailModel = parent ? this.getTailModel() : otherParent.getTailModel();
                    tailColor = parent ? this.getTailColor() : otherParent.getTailColor();

                    hasMainPattern = parent ? this.getHasMainPattern() : otherParent.getHasMainPattern();
                    mainPatternType = parent ? this.getMainPattern() : otherParent.getMainPattern();
                    mainPatternColor = parent ? this.getMainPatternColor() : otherParent.getMainPatternColor();

                    hasSecondPattern = parent ? this.getHasSecondPattern() : otherParent.getHasSecondPattern();
                    secondPatternType = parent ? this.getSecondPattern() : otherParent.getSecondPattern();
                    secondPatternColor = parent ? this.getSecondPatternColor(): otherParent.getSecondPatternColor();
                    break;

                default:
                    skin = this.random.nextInt(12);
                    finModel = this.random.nextInt(2);
                    finColor = this.random.nextInt(22);

                    tailModel = this.random.nextInt(19);
                    tailColor = this.random.nextInt(22);

                    hasMainPattern = this.random.nextInt(3)==0;
                    mainPatternType = this.random.nextInt(11);
                    mainPatternColor = this.random.nextInt(22);

                    hasSecondPattern = this.random.nextInt(3)==0;
                    secondPatternType = this.random.nextInt(18);
                    secondPatternColor = this.random.nextInt(22);
                    break;
            }

            baby.setVariantSkin(skin);
            baby.setFinModel(finModel);
            baby.setFinColor(finColor);
            baby.setTailModel(tailModel);
            baby.setTailColor(tailColor);

            baby.setHasMainPattern(hasMainPattern);
            baby.setMainPattern(mainPatternType);
            baby.setMainPatternColor(mainPatternColor);

            baby.setHasSecondPattern(hasSecondPattern);
            baby.setSecondPattern(secondPatternType);
            baby.setSecondPatternColor(secondPatternColor);
            baby.setFromBucket(true);
        }

        return baby;
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


    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }


}
