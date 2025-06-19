package net.voidarkana.fintastic.common.entity.custom;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.voidarkana.fintastic.common.entity.YAFMEntities;
import net.voidarkana.fintastic.common.entity.custom.ai.FishBreedGoal;
import net.voidarkana.fintastic.common.entity.custom.base.BreedableWaterAnimal;
import net.voidarkana.fintastic.common.entity.custom.base.VariantSchoolingFish;
import net.voidarkana.fintastic.common.entity.custom.base.BucketableFishEntity;
import net.voidarkana.fintastic.common.item.YAFMItems;
import net.voidarkana.fintastic.util.YAFMTags;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

public class MinnowEntity extends VariantSchoolingFish {

    public final net.minecraft.world.entity.AnimationState idleAnimationState = new net.minecraft.world.entity.AnimationState();
    public final net.minecraft.world.entity.AnimationState flopAnimationState = new net.minecraft.world.entity.AnimationState();
    public final net.minecraft.world.entity.AnimationState swimAnimationState = new net.minecraft.world.entity.AnimationState();

    public MinnowEntity(EntityType<? extends BucketableFishEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    private static final Ingredient FOOD_ITEMS = Ingredient.of(YAFMTags.Items.FISH_FEED);

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new FishBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 2D, FOOD_ITEMS, false));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.8F);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setVariantModel(compound.getInt("VariantModel"));
        this.setVariantSkin(compound.getInt("VariantSkin"));
    }

    @Override
    public void saveToBucketTag(ItemStack bucket) {
        CompoundTag compoundnbt = bucket.getOrCreateTag();
        Bucketable.saveDefaultDataToBucketTag(this, bucket);
        compoundnbt.putFloat("Health", this.getHealth());
        compoundnbt.putInt("Age", this.getAge());
        compoundnbt.putBoolean("CanGrow", this.getCanGrowUp());
        compoundnbt.putInt("VariantModel", this.getVariantModel());
        compoundnbt.putInt("VariantSkin", this.getVariantSkin());
        if (this.hasCustomName()) {
            bucket.setHoverName(this.getCustomName());
        }
    }

    @Override
    public void loadFromBucketTag(CompoundTag pTag) {
        Bucketable.loadDefaultDataFromBucketTag(this, pTag);
    }

    @Override
    public void tick() {
        if (this.level().isClientSide()){
            this.setupAnimationStates();
        }
        super.tick();
    }

    private void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);

        this.swimAnimationState.animateWhen(this.walkAnimation.isMoving() && this.isInWaterOrBubble(), this.tickCount);

        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble(), this.tickCount);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {

        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);

        if (pReason == MobSpawnType.BUCKET && pDataTag != null && pDataTag.contains("VariantModel", 3)) {
            this.setVariantModel(pDataTag.getInt("VariantModel"));
            this.setVariantSkin(pDataTag.getInt("VariantSkin"));
            if (pDataTag.contains("Age")) {
                this.setAge(pDataTag.getInt("Age"));}
            this.setCanGrowUp(pDataTag.getBoolean("CanGrow"));
        }else{
            MinnowVariant variant;

            int model;
            int skin;

            if(pReason == MobSpawnType.SPAWN_EGG || (pReason == MobSpawnType.BUCKET && pDataTag == null)){

                variant = Util.getRandom(MinnowVariant.values(), this.random);

                this.setVariantModel(variant.getModel());
                this.setVariantSkin(variant.getSkin());

            }else {

                if (pSpawnData instanceof MinnowGroupData groupData){

                    model = groupData.getVariantModel();
                    skin = groupData.getVariantSkin();
                    this.startFollowing(groupData.leader);

                }else {

                    if (this.blockPosition().getY() <= pLevel.getSeaLevel() - 33
                            && pLevel.getBlockState(this.blockPosition()).is(Blocks.WATER)){

                        if (this.getRandom().nextBoolean()){
                            model = MinnowVariant.MEXICAN_CAVE_TETRA.getModel();
                            skin = MinnowVariant.MEXICAN_CAVE_TETRA.getSkin();
                        }else {
                            model = MinnowVariant.SOUTHERN_CAVE_FISH.getModel();
                            skin = MinnowVariant.SOUTHERN_CAVE_FISH.getSkin();
                        }

                    } else if (pLevel.getBiome(this.blockPosition()).is(Tags.Biomes.IS_SWAMP)){

                        int var = this.getRandom().nextInt(16);

                        model = switch (var){
                            case 1 -> MinnowVariant.NEON_GREEN_RASBORA.getModel();
                            case 2 -> MinnowVariant.CHILI_RASBORA.getModel();
                            case 3 ->MinnowVariant.NEON_TETRA.getModel();
                            case 4 -> MinnowVariant.CARDINAL_TETRA.getModel();
                            case 5 -> MinnowVariant.DRAGONFIN_TETRA.getModel();
                            case 6 -> MinnowVariant.MARBLED_HATCHETFISH.getModel();
                            case 7 -> MinnowVariant.SILVER_HATCHETFISH.getModel();
                            case 8 -> MinnowVariant.COPELLA_TETRA.getModel();
                            case 9 -> MinnowVariant.PIABUCO.getModel();
                            case 10 -> MinnowVariant.GIANT_DANIO.getModel();
                            case 11 -> MinnowVariant.BUENOS_AIRES_TETRA.getModel();
                            case 12 -> MinnowVariant.RED_TAIL_ASTYANAX.getModel();
                            case 13 -> MinnowVariant.MASKED_BARB.getModel();
                            case 14 -> MinnowVariant.BANDED_ASTYANAX.getModel();
                            case 15 -> MinnowVariant.STREAKED_PROCHILODUS.getModel();
                            default -> MinnowVariant.FLAGTAIL_PROCHILODUS.getModel();
                        };

                        skin = switch (var){
                            case 1 -> MinnowVariant.NEON_GREEN_RASBORA.getSkin();
                            case 2 -> MinnowVariant.CHILI_RASBORA.getSkin();
                            case 3 ->MinnowVariant.NEON_TETRA.getSkin();
                            case 4 -> MinnowVariant.CARDINAL_TETRA.getSkin();
                            case 5 -> MinnowVariant.DRAGONFIN_TETRA.getSkin();
                            case 6 -> MinnowVariant.MARBLED_HATCHETFISH.getSkin();
                            case 7 -> MinnowVariant.SILVER_HATCHETFISH.getSkin();
                            case 8 -> MinnowVariant.COPELLA_TETRA.getSkin();
                            case 9 -> MinnowVariant.PIABUCO.getSkin();
                            case 10 -> MinnowVariant.GIANT_DANIO.getSkin();
                            case 11 -> MinnowVariant.BUENOS_AIRES_TETRA.getSkin();
                            case 12 -> MinnowVariant.RED_TAIL_ASTYANAX.getSkin();
                            case 13 -> MinnowVariant.MASKED_BARB.getSkin();
                            case 14 -> MinnowVariant.BANDED_ASTYANAX.getSkin();
                            case 15 -> MinnowVariant.STREAKED_PROCHILODUS.getSkin();
                            default -> MinnowVariant.FLAGTAIL_PROCHILODUS.getSkin();
                        };


                    } else if (pLevel.getBiome(this.blockPosition()).is(BiomeTags.IS_JUNGLE)){

                        int var = this.getRandom().nextInt(21);

                        model = switch (var){
                            case 1 -> MinnowVariant.FLAGTAIL_PROCHILODUS.getModel();
                            case 2 -> MinnowVariant.COPELLA_TETRA.getModel();
                            case 3 -> MinnowVariant.PIABUCO.getModel();
                            case 4 -> MinnowVariant.HARLEQUIN_RASBORA.getModel();
                            case 5 -> MinnowVariant.ODESSA_BARB.getModel();
                            case 6 -> MinnowVariant.RUBY_BARB.getModel();
                            case 7 -> MinnowVariant.BANDED_ASTYANAX.getModel();
                            case 8 -> MinnowVariant.TETRAZONA_BARB.getModel();
                            case 9 -> MinnowVariant.MASKED_BARB.getModel();
                            case 10 -> MinnowVariant.NEON_TETRA.getModel();
                            case 11 -> MinnowVariant.BLUE_NEON_RASBORA.getModel();
                            case 12 -> MinnowVariant.SCISSORTAIL_RASBORA.getModel();
                            case 13 -> MinnowVariant.MOSQUITO_RASABORA.getModel();
                            case 14 -> MinnowVariant.MARBLED_HATCHETFISH.getModel();
                            case 15 -> MinnowVariant.SILVER_HATCHETFISH.getModel();
                            case 16 -> MinnowVariant.DRAGONFIN_TETRA.getModel();
                            case 17 -> MinnowVariant.CHERRY_BARB.getModel();
                            case 18 -> MinnowVariant.GOLDEN_BARB.getModel();
                            case 19 -> MinnowVariant.TORPEDO_BARB.getModel();
                            case 20 -> MinnowVariant.ALESTES_TETRA.getModel();
                            default -> MinnowVariant.SIXBAR_DISTICHODUS.getModel();
                        };

                        skin = switch (var){
                            case 1 -> MinnowVariant.FLAGTAIL_PROCHILODUS.getSkin();
                            case 2 -> MinnowVariant.COPELLA_TETRA.getSkin();
                            case 3 -> MinnowVariant.PIABUCO.getSkin();
                            case 4 -> MinnowVariant.HARLEQUIN_RASBORA.getSkin();
                            case 5 -> MinnowVariant.ODESSA_BARB.getSkin();
                            case 6 -> MinnowVariant.RUBY_BARB.getSkin();
                            case 7 -> MinnowVariant.BANDED_ASTYANAX.getSkin();
                            case 8 -> MinnowVariant.TETRAZONA_BARB.getSkin();
                            case 9 -> MinnowVariant.MASKED_BARB.getSkin();
                            case 10 -> MinnowVariant.NEON_TETRA.getSkin();
                            case 11 -> MinnowVariant.BLUE_NEON_RASBORA.getSkin();
                            case 12 -> MinnowVariant.SCISSORTAIL_RASBORA.getSkin();
                            case 13 -> MinnowVariant.MOSQUITO_RASABORA.getSkin();
                            case 14 -> MinnowVariant.MARBLED_HATCHETFISH.getSkin();
                            case 15 -> MinnowVariant.SILVER_HATCHETFISH.getSkin();
                            case 16 -> MinnowVariant.DRAGONFIN_TETRA.getSkin();
                            case 17 -> MinnowVariant.CHERRY_BARB.getSkin();
                            case 18 -> MinnowVariant.GOLDEN_BARB.getSkin();
                            case 19 -> MinnowVariant.TORPEDO_BARB.getSkin();
                            case 20 -> MinnowVariant.ALESTES_TETRA.getSkin();
                            default -> MinnowVariant.SIXBAR_DISTICHODUS.getSkin();
                        };
                    } else if (pLevel.getBiome(this.blockPosition()).is(BiomeTags.IS_RIVER)){

                        int var = this.getRandom().nextInt(10);

                        model = switch (var){
                            case 1 -> MinnowVariant.GALAXIAS.getModel();
                            case 2 -> MinnowVariant.SAILFIN_SHINER.getModel();
                            case 3 ->MinnowVariant.FAT_HEAD_MINNOW.getModel();
                            case 4 -> MinnowVariant.RED_TAIL_ASTYANAX.getModel();
                            case 5 -> MinnowVariant.BITTERLING.getModel();
                            case 6 -> MinnowVariant.BANDED_ASTYANAX.getModel();
                            case 7 -> MinnowVariant.STREAKED_PROCHILODUS.getModel();
                            case 8 -> MinnowVariant.TINFOIL_BARB.getModel();
                            case 9 -> MinnowVariant.SICKLEFIN_BARB.getModel();
                            default -> MinnowVariant.DELTA_SMELT.getModel();
                        };

                        skin = switch (var){
                            case 1 -> MinnowVariant.GALAXIAS.getSkin();
                            case 2 -> MinnowVariant.SAILFIN_SHINER.getSkin();
                            case 3 ->MinnowVariant.FAT_HEAD_MINNOW.getSkin();
                            case 4 -> MinnowVariant.RED_TAIL_ASTYANAX.getSkin();
                            case 5 -> MinnowVariant.BITTERLING.getSkin();
                            case 6 -> MinnowVariant.BANDED_ASTYANAX.getSkin();
                            case 7 -> MinnowVariant.STREAKED_PROCHILODUS.getSkin();
                            case 8 -> MinnowVariant.TINFOIL_BARB.getSkin();
                            case 9 -> MinnowVariant.SICKLEFIN_BARB.getSkin();
                            default -> MinnowVariant.DELTA_SMELT.getSkin();
                        };

                    } else if (pLevel.getBiome(this.blockPosition()).is(Biomes.MANGROVE_SWAMP)){

                        int var = this.getRandom().nextInt(3);

                        model = switch (var){
                            case 1 -> MinnowVariant.GALAXIAS.getModel();
                            case 2 -> MinnowVariant.STRIPED_MOJARRA.getModel();
                            default -> MinnowVariant.DELTA_SMELT.getModel();
                        };

                        skin = switch (var){
                            case 1 -> MinnowVariant.GALAXIAS.getSkin();
                            case 2 -> MinnowVariant.STRIPED_MOJARRA.getSkin();
                            default -> MinnowVariant.DELTA_SMELT.getSkin();
                        };

                    } else if (pLevel.getBiome(this.blockPosition()).is(BiomeTags.IS_BEACH)){

                        int var = this.getRandom().nextInt(4);

                        model = switch (var){
                            case 1 -> MinnowVariant.ATLANTIC_HERRING.getModel();
                            case 2 -> MinnowVariant.GALAXIAS.getModel();
                            case 3 -> MinnowVariant.STRIPED_MOJARRA.getModel();
                            default -> MinnowVariant.DELTA_SMELT.getModel();
                        };

                        skin = switch (var){
                            case 1 -> MinnowVariant.ATLANTIC_HERRING.getSkin();
                            case 2 -> MinnowVariant.GALAXIAS.getSkin();
                            case 3 -> MinnowVariant.STRIPED_MOJARRA.getSkin();
                            default -> MinnowVariant.DELTA_SMELT.getSkin();
                        };

                    } else {

                        variant = Util.getRandom(MinnowVariant.values(), this.random);

                        model = variant.getModel();
                        skin = variant.getSkin();

                    }

                    pSpawnData = new MinnowGroupData(this, model, skin);
                }

                this.setVariantModel(model);
                this.setVariantSkin(skin);
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().add((double)((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F), (double)0.4F, (double)((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F)));


        return pSpawnData;
    }

    @Nullable
    @Override
    public BreedableWaterAnimal getBreedOffspring(ServerLevel pLevel, BreedableWaterAnimal pOtherParent) {
        MinnowEntity baby = YAFMEntities.MINNOW.get().create(pLevel);
        if (baby != null){
            baby.setFromBucket(true);
            baby.setVariantModel(this.getVariantModel());
            baby.setVariantSkin(this.getVariantSkin());
        }
        return baby;
    }

    @Override
    public boolean canMate(BreedableWaterAnimal pOtherAnimal) {
        MinnowEntity mate = (MinnowEntity) pOtherAnimal;
        return super.canMate(pOtherAnimal) && mate.getVariantModel() == this.getVariantModel() && mate.getVariantSkin() == this.getVariantSkin();
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(YAFMItems.MINNOW_BUCKET.get());
    }


    static class MinnowGroupData extends SchoolSpawnGroupData {
        final int variantModel;
        final int variantSkin;

        MinnowGroupData(MinnowEntity pLeader, int pVariantModel, int pVariantSkin) {
            super(pLeader);
            this.variantModel = pVariantModel;
            this.variantSkin = pVariantSkin;
        }

        public int getVariantModel(){
            return variantModel;
        }

        public int getVariantSkin(){
            return variantSkin;
        }
    }

    public enum MinnowVariant{
        FLAGTAIL_PROCHILODUS(0, "flagtail_prochilodus"),
        SICKLEFIN_BARB(1, "sicklefin_barb"),
        SIXBAR_DISTICHODUS(2, "sixbar_distichodus"),
        STREAKED_PROCHILODUS(3, "streaked_prochilodus"),
        TINFOIL_BARB(4, "tinfoil_barb"),

        DRAGONFIN_TETRA(10, "dragonfin_tetra"),
        SILVER_HATCHETFISH(11, "silver_hatchetfish"),
        MARBLED_HATCHETFISH(12, "marbled_hatchetfish"),

        BANDED_ASTYANAX(20, "banded_astyanax"),
        BITTERLING(21, "bitterling"),
        HARLEQUIN_RASBORA(22, "harlequin_rasbora"),
        MASKED_BARB(23, "masked_barb"),
        ODESSA_BARB(24, "odessa_barb"),
        RUBY_BARB(25, "ruby_barb"),
        STRIPED_MOJARRA(26, "striped_mojarra"),
        TETRAZONA_BARB(27, "tetrazona_barb"),

        ALESTES_TETRA(30, "alestes_tetra"),
        BUENOS_AIRES_TETRA(31,"buenos_aires_tetra"),
        CHERRY_BARB(32,"cherry_barb"),
        FAT_HEAD_MINNOW(33,"fat_head_minnow"),
        GIANT_DANIO(34,"giant_danio"),
        GOLDEN_BARB(35,"golden_barb"),
        MEXICAN_CAVE_TETRA(36,"mexican_cave_tetra"),
        RED_TAIL_ASTYANAX(37,"red_tail_astyanax"),
        SAILFIN_SHINER(38,"sailfin_shiner"),
        TORPEDO_BARB(39,"torpedo_barb"),

        BLUE_NEON_RASBORA(40,"blue_neon_rasbora"),
        CARDINAL_TETRA(41,"cardinal_tetra"),
        CHILI_RASBORA(42,"chili_rasbora"),
        MOSQUITO_RASABORA(43,"mosquito_rasbora"),
        NEON_GREEN_RASBORA(44,"neon_green_rasbora"),
        NEON_TETRA(45,"neon_tetra"),
        SCISSORTAIL_RASBORA(46,"scissortail_rasbora"),

        ATLANTIC_HERRING(50,"atlantic_herring"),
        COPELLA_TETRA(51,"copella_tetra"),
        DELTA_SMELT(52,"delta_smelt"),
        GALAXIAS(53,"galaxias"),
        PIABUCO(54,"piabuco"),
        SOUTHERN_CAVE_FISH(55,"southern_cave_fish");

        private final int joinedVariant;
        private final String name;

        MinnowVariant(int variant, String name){
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

        public String getName(){
            return this.name;
        }

        public String getModelName(){
            return switch ((int)this.joinedVariant/10) {
                case 1 -> "hatchet";
                case 2 -> "round";
                case 3 -> "slim";
                case 4 -> "small";
                case 5 -> "thin";
                default -> "big";
            };
        }

        private static final IntFunction<MinnowEntity.MinnowVariant> BY_ID
                = ByIdMap.sparse(MinnowEntity.MinnowVariant::getJoinedVariant,
                values(), TINFOIL_BARB);


        public static MinnowVariant byId(int pId) {
            return BY_ID.apply(pId);
        }
    }

    public static boolean checkSurfaceWaterAnimalSpawnRules(EntityType<? extends WaterAnimal> pWaterAnimal, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
        int i = pLevel.getSeaLevel();
        int j = i - 13;
        return !pLevel.getBiome(pPos).is(BiomeTags.IS_OCEAN) && ((pLevel.getBiome(pPos).is(YAFMTags.Biomes.MINNOW_SURFACE_BIOMES) && pPos.getY() >= j && pPos.getY() <= i && pLevel.getFluidState(pPos.below()).is(FluidTags.WATER) && pLevel.getBlockState(pPos.above()).is(Blocks.WATER))
                || (pPos.getY() <= pLevel.getSeaLevel() - 33 && pLevel.getBlockState(pPos).is(Blocks.WATER)));
    }

}
