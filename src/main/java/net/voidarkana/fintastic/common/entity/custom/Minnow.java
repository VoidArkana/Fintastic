package net.voidarkana.fintastic.common.entity.custom;

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
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
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


    public Minnow(EntityType<? extends BucketableFishEntity> entityType, Level level) {
        super(entityType, level);
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

        if (tag.contains("Variant"))
            this.setVariant(tag.getInt("Variant"));

        if (tag.contains("Age")) {
            this.setAge(tag.getInt("Age"));
        }
        if (tag.contains("CanGrow")) {
            this.setCanGrowUp(tag.getBoolean("CanGrow"));
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData) {

        if (spawnData == null)
            spawnData = super.finalizeSpawn(level, difficulty, reason, spawnData);

        MinnowVariant variant;

        int variantId;

        if(reason == MobSpawnType.STRUCTURE || reason == MobSpawnType.SPAWN_EGG || reason == MobSpawnType.BUCKET){

            variant = Util.getRandom(MinnowVariant.values(), this.random);

            this.setVariant(variant.getVariant());

        }else {

            if (spawnData instanceof MinnowGroupData groupData){

                variantId = groupData.getVariant();
                this.startFollowing(groupData.leader);

            }else {
                if (this.blockPosition().getY() <= level.getSeaLevel() - 33
                        && level.getBlockState(this.blockPosition()).is(Blocks.WATER)){

                    if (this.getRandom().nextBoolean()){
                        variantId = MinnowVariant.MEXICAN_CAVE_TETRA.getVariant();
                    }else {
                        variantId = MinnowVariant.SOUTHERN_CAVE_FISH.getVariant();
                    }

                } else if (level.getBiome(this.blockPosition()).is(BiomeTags.IS_OCEAN)){

                    variantId = MinnowVariant.ATLANTIC_HERRING.getVariant();

                    if (level.getBiome(this.blockPosition()).is(Biomes.WARM_OCEAN)){
                        if (this.getRandom().nextBoolean()){
                            variantId = MinnowVariant.STRIPED_MOJARRA.getVariant();
                        }
                    }

                } else if (level.getBiome(this.blockPosition()).is(Tags.Biomes.IS_SWAMP)){

                    int var = this.getRandom().nextInt(16);


                    variantId = switch (var){
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

                    if (level.getBiome(this.blockPosition()).is(Biomes.MANGROVE_SWAMP) && this.random.nextFloat() < 0.06) {
                        variantId = MinnowVariant.STRIPED_MOJARRA.getVariant();
                    }


                } else if (level.getBiome(this.blockPosition()).is(BiomeTags.IS_JUNGLE)){

                    int var = this.getRandom().nextInt(22);

                    variantId = switch (var){
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
                } else if (level.getBiome(this.blockPosition()).is(BiomeTags.IS_RIVER)){

                    int var = this.getRandom().nextInt(10);

                    variantId = switch (var){
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

                } else if (level.getBiome(this.blockPosition()).is(Biomes.MANGROVE_SWAMP)){

                    int var = this.getRandom().nextInt(3);

                    variantId = switch (var){
                        case 1 -> MinnowVariant.GALAXIAS.getVariant();
                        case 2 -> MinnowVariant.STRIPED_MOJARRA.getVariant();
                        default -> MinnowVariant.DELTA_SMELT.getVariant();
                    };

                } else if (level.getBiome(this.blockPosition()).is(BiomeTags.IS_BEACH)){

                    int var = this.getRandom().nextInt(4);

                    variantId = switch (var){
                        case 1 -> MinnowVariant.ATLANTIC_HERRING.getVariant();
                        case 2 -> MinnowVariant.GALAXIAS.getVariant();
                        case 3 -> MinnowVariant.STRIPED_MOJARRA.getVariant();
                        default -> MinnowVariant.DELTA_SMELT.getVariant();
                    };

                } else {

                    variant = Util.getRandom(MinnowVariant.values(), this.random);

                    variantId = variant.getVariant();

                }

                spawnData = new MinnowGroupData(this, variantId);
            }
            this.setVariant(variantId);
        }

        return spawnData;
    }

    @Nullable
    @Override
    public BreedableWaterAnimal getBreedOffspring(ServerLevel level, BreedableWaterAnimal otherParent) {
        Minnow baby = FintyEntities.MINNOW.get().create(level);
        if (baby != null){
            baby.setFromBucket(true);
            baby.setVariant(this.getVariant());
        }
        return baby;
    }

    @Override
    public boolean canMate(BreedableWaterAnimal otherAnimal) {
        Minnow mate = (Minnow) otherAnimal;
        return super.canMate(otherAnimal) && mate.getVariant() == this.getVariant() && mate.getVariant() == this.getVariant();
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(FintyItems.MINNOW_BUCKET.get());
    }


    static class MinnowGroupData extends SchoolSpawnGroupData {
        final int variant;

        MinnowGroupData(Minnow leader, int variantModel) {
            super(leader);
            this.variant = variantModel;
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


        public static MinnowVariant byId(int id) {
            return BY_ID.apply(id);
        }
    }

    public static boolean checkSurfaceWaterAnimalSpawnRules(EntityType<? extends WaterAnimal> waterAnimal, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        int i = level.getSeaLevel();
        int j = i - 13;
        return ((level.getBiome(pos).is(FintyTags.Biomes.MINNOW_SURFACE_BIOMES) && pos.getY() >= j && pos.getY() <= i && level.getFluidState(pos.below()).is(FluidTags.WATER) && level.getBlockState(pos.above()).is(Blocks.WATER))
                || (!level.getBiome(pos).is(BiomeTags.IS_OCEAN) && pos.getY() <= level.getSeaLevel() - 33 && level.getBlockState(pos).is(Blocks.WATER) && (level.getRawBrightness(pos, 0) == 0 || level.getBiome(pos).is(Biomes.LUSH_CAVES))));
    }

}
