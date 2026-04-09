package net.voidarkana.fintastic.common.entity.custom;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
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
import net.voidarkana.fintastic.common.entity.FintyEntities;
import net.voidarkana.fintastic.common.entity.custom.ai.FishBreedGoal;
import net.voidarkana.fintastic.common.entity.custom.base.BreedableWaterAnimal;
import net.voidarkana.fintastic.common.entity.custom.base.VariantSchoolingFish;
import net.voidarkana.fintastic.common.entity.custom.base.BucketableFishEntity;
import net.voidarkana.fintastic.common.item.FintyItems;
import net.voidarkana.fintastic.util.FintyTags;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

public class Minnow extends VariantSchoolingFish {


    public Minnow(EntityType<? extends BucketableFishEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    private static final Ingredient FOOD_ITEMS = Ingredient.of(FintyTags.Items.FISH_FEED);

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new FishBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 2D, FOOD_ITEMS, false));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.6F);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setVariant(compound.getInt("Variant"));
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

        if (pTag.contains("Variant"))
            this.setVariant(pTag.getInt("Variant"));

        if (pTag.contains("Age")) {
            this.setAge(pTag.getInt("Age"));
        }
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
            MinnowVariant variant;

            int pVariant;

            if(pReason == MobSpawnType.SPAWN_EGG || (pReason == MobSpawnType.BUCKET && pDataTag == null)){

                variant = Util.getRandom(MinnowVariant.values(), this.random);

                this.setVariant(variant.getVariant());

            }else {

                if (pSpawnData instanceof MinnowGroupData groupData){

                    pVariant = groupData.getVariant();
                    this.startFollowing(groupData.leader);

                }else {
                    if (this.blockPosition().getY() <= pLevel.getSeaLevel() - 33
                            && pLevel.getBlockState(this.blockPosition()).is(Blocks.WATER)){

                        if (this.getRandom().nextBoolean()){
                            pVariant = MinnowVariant.MEXICAN_CAVE_TETRA.getVariant();
                        }else {
                            pVariant = MinnowVariant.SOUTHERN_CAVE_FISH.getVariant();
                        }

                    } else if (pLevel.getBiome(this.blockPosition()).is(BiomeTags.IS_OCEAN)){

                        pVariant = MinnowVariant.ATLANTIC_HERRING.getVariant();

                        if (pLevel.getBiome(this.blockPosition()).is(Biomes.WARM_OCEAN)){
                            if (this.getRandom().nextBoolean()){
                                pVariant = MinnowVariant.STRIPED_MOJARRA.getVariant();
                            }
                        }

                    } else if (pLevel.getBiome(this.blockPosition()).is(Tags.Biomes.IS_SWAMP)){

                        int var = this.getRandom().nextInt(16);


                        pVariant = switch (var){
                            case 1 -> MinnowVariant.NEON_GREEN_RASBORA.getVariant();
                            case 2 -> MinnowVariant.CHILI_RASBORA.getVariant();
                            case 3 ->MinnowVariant.NEON_TETRA.getVariant();
                            case 4 -> MinnowVariant.CARDINAL_TETRA.getVariant();
                            case 5 -> MinnowVariant.DRAGONFIN_TETRA.getVariant();
                            case 6 -> MinnowVariant.MARBLED_HATCHETFISH.getVariant();
                            case 7 -> MinnowVariant.SILVER_HATCHETFISH.getVariant();
                            case 8 -> MinnowVariant.COPELLA_TETRA.getVariant();
                            case 9 -> MinnowVariant.PIABUCO.getVariant();
                            case 10 -> MinnowVariant.GIANT_DANIO.getVariant();
                            case 11 -> MinnowVariant.BUENOS_AIRES_TETRA.getVariant();
                            case 12 -> MinnowVariant.RED_TAIL_ASTYANAX.getVariant();
                            case 13 -> MinnowVariant.MASKED_BARB.getVariant();
                            case 14 -> MinnowVariant.BANDED_ASTYANAX.getVariant();
                            case 15 -> MinnowVariant.STREAKED_PROCHILODUS.getVariant();
                            default -> MinnowVariant.FLAGTAIL_PROCHILODUS.getVariant();
                        };

                        if (pLevel.getBiome(this.blockPosition()).is(Biomes.MANGROVE_SWAMP) && this.random.nextFloat() < 0.06) {
                            pVariant = MinnowVariant.STRIPED_MOJARRA.getVariant();
                        }


                    } else if (pLevel.getBiome(this.blockPosition()).is(BiomeTags.IS_JUNGLE)){

                        int var = this.getRandom().nextInt(22);

                        pVariant = switch (var){
                            case 1 -> MinnowVariant.FLAGTAIL_PROCHILODUS.getVariant();
                            case 2 -> MinnowVariant.COPELLA_TETRA.getVariant();
                            case 3 -> MinnowVariant.PIABUCO.getVariant();
                            case 4 -> MinnowVariant.HARLEQUIN_RASBORA.getVariant();
                            case 5 -> MinnowVariant.ODESSA_BARB.getVariant();
                            case 6 -> MinnowVariant.RUBY_BARB.getVariant();
                            case 7 -> MinnowVariant.BANDED_ASTYANAX.getVariant();
                            case 8 -> MinnowVariant.TETRAZONA_BARB.getVariant();
                            case 9 -> MinnowVariant.MASKED_BARB.getVariant();
                            case 10 -> MinnowVariant.NEON_TETRA.getVariant();
                            case 11 -> MinnowVariant.BLUE_NEON_RASBORA.getVariant();
                            case 12 -> MinnowVariant.SCISSORTAIL_RASBORA.getVariant();
                            case 13 -> MinnowVariant.MOSQUITO_RASABORA.getVariant();
                            case 14 -> MinnowVariant.MARBLED_HATCHETFISH.getVariant();
                            case 15 -> MinnowVariant.SILVER_HATCHETFISH.getVariant();
                            case 16 -> MinnowVariant.DRAGONFIN_TETRA.getVariant();
                            case 17 -> MinnowVariant.CHERRY_BARB.getVariant();
                            case 18 -> MinnowVariant.GOLDEN_BARB.getVariant();
                            case 19 -> MinnowVariant.TORPEDO_BARB.getVariant();
                            case 20 -> MinnowVariant.ALESTES_TETRA.getVariant();
                            case 21 -> MinnowVariant.BLACKLINETAIL_TETRA.getVariant();
                            default -> MinnowVariant.SIXBAR_DISTICHODUS.getVariant();
                        };
                    } else if (pLevel.getBiome(this.blockPosition()).is(BiomeTags.IS_RIVER)){

                        int var = this.getRandom().nextInt(10);

                        pVariant = switch (var){
                            case 1 -> MinnowVariant.GALAXIAS.getVariant();
                            case 2 -> MinnowVariant.SAILFIN_SHINER.getVariant();
                            case 3 ->MinnowVariant.FAT_HEAD_MINNOW.getVariant();
                            case 4 -> MinnowVariant.RED_TAIL_ASTYANAX.getVariant();
                            case 5 -> MinnowVariant.BITTERLING.getVariant();
                            case 6 -> MinnowVariant.BANDED_ASTYANAX.getVariant();
                            case 7 -> MinnowVariant.STREAKED_PROCHILODUS.getVariant();
                            case 8 -> MinnowVariant.TINFOIL_BARB.getVariant();
                            case 9 -> MinnowVariant.SICKLEFIN_BARB.getVariant();
                            default -> MinnowVariant.DELTA_SMELT.getVariant();
                        };

                    } else if (pLevel.getBiome(this.blockPosition()).is(Biomes.MANGROVE_SWAMP)){

                        int var = this.getRandom().nextInt(3);

                        pVariant = switch (var){
                            case 1 -> MinnowVariant.GALAXIAS.getVariant();
                            case 2 -> MinnowVariant.STRIPED_MOJARRA.getVariant();
                            default -> MinnowVariant.DELTA_SMELT.getVariant();
                        };

                    } else if (pLevel.getBiome(this.blockPosition()).is(BiomeTags.IS_BEACH)){

                        int var = this.getRandom().nextInt(4);

                        pVariant = switch (var){
                            case 1 -> MinnowVariant.ATLANTIC_HERRING.getVariant();
                            case 2 -> MinnowVariant.GALAXIAS.getVariant();
                            case 3 -> MinnowVariant.STRIPED_MOJARRA.getVariant();
                            default -> MinnowVariant.DELTA_SMELT.getVariant();
                        };

                    } else {

                        variant = Util.getRandom(MinnowVariant.values(), this.random);

                        pVariant = variant.getVariant();

                    }

                    pSpawnData = new MinnowGroupData(this, pVariant);
                }
                this.setVariant(pVariant);
            }
        }

        return pSpawnData;
    }

    @Nullable
    @Override
    public BreedableWaterAnimal getBreedOffspring(ServerLevel pLevel, BreedableWaterAnimal pOtherParent) {
        Minnow baby = FintyEntities.MINNOW.get().create(pLevel);
        if (baby != null){
            baby.setFromBucket(true);
            baby.setVariant(this.getVariant());
        }
        return baby;
    }

    @Override
    public boolean canMate(BreedableWaterAnimal pOtherAnimal) {
        Minnow mate = (Minnow) pOtherAnimal;
        return super.canMate(pOtherAnimal) && mate.getVariant() == this.getVariant() && mate.getVariant() == this.getVariant();
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(FintyItems.MINNOW_BUCKET.get());
    }


    static class MinnowGroupData extends SchoolSpawnGroupData {
        final int variant;

        MinnowGroupData(Minnow pLeader, int pVariantModel) {
            super(pLeader);
            this.variant = pVariantModel;
        }

        public int getVariant(){
            return variant;
        }
    }

    public static String getModelName(int model){
        return switch (model) {
            case 1 -> "hatchet";
            case 2 -> "round";
            case 3 -> "slim";
            case 4 -> "small";
            case 5 -> "thin";
            default -> "big";
        };
    }

    public enum MinnowVariant implements StringRepresentable{
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
        BLACKLINETAIL_TETRA(310,"blacklinetail_tetra"),

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

        public int getVariant(){
            return this.joinedVariant;
        }

        public int getModel(){
            return this.joinedVariant > 100 ? this.joinedVariant/100 : this.joinedVariant/10;
        }

        public String getSerializedName(){
            return this.name;
        }

        public String getModelName(){
            return switch (this.getModel()) {
                case 1 -> "hatchet";
                case 2 -> "round";
                case 3 -> "slim";
                case 4 -> "small";
                case 5 -> "thin";
                default -> "big";
            };
        }

        private static final IntFunction<Minnow.MinnowVariant> BY_ID
                = ByIdMap.sparse(Minnow.MinnowVariant::getVariant, values(), TINFOIL_BARB);


        public static MinnowVariant byId(int pId) {
            return BY_ID.apply(pId);
        }
    }

    public static boolean checkSurfaceWaterAnimalSpawnRules(EntityType<? extends WaterAnimal> pWaterAnimal, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
        int i = pLevel.getSeaLevel();
        int j = i - 13;
        return ((pLevel.getBiome(pPos).is(FintyTags.Biomes.MINNOW_SURFACE_BIOMES) && pPos.getY() >= j && pPos.getY() <= i && pLevel.getFluidState(pPos.below()).is(FluidTags.WATER) && pLevel.getBlockState(pPos.above()).is(Blocks.WATER))
                || (!pLevel.getBiome(pPos).is(BiomeTags.IS_OCEAN) && pPos.getY() <= pLevel.getSeaLevel() - 33 && pLevel.getBlockState(pPos).is(Blocks.WATER) && (pLevel.getRawBrightness(pPos, 0) == 0 || pLevel.getBiome(pPos).is(Biomes.LUSH_CAVES))));
    }

}
