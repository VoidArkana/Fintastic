package net.voidarkana.fintastic.util.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.util.FintyTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class FintyBiomeTagGenerator extends BiomeTagsProvider {

    public FintyBiomeTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, Fintastic.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        this.tag(FintyTags.Biomes.FEATHERBACK_BIOMES)
                .addTag(Tags.Biomes.IS_SWAMP);

        this.tag(FintyTags.Biomes.ARAPAIMA_BIOMES)
                .addTag(BiomeTags.IS_JUNGLE);

        this.tag(FintyTags.Biomes.CATFISH_BIOMES)
                .addTag(BiomeTags.IS_RIVER)
                .addTag(BiomeTags.IS_JUNGLE)
                .addTag(Tags.Biomes.IS_SWAMP)
                .add(Biomes.WARM_OCEAN)
                .add(Biomes.LUKEWARM_OCEAN)
                .add(Biomes.DEEP_LUKEWARM_OCEAN);

        this.tag(FintyTags.Biomes.GUPPY_BIOMES)
                .addTag(BiomeTags.IS_JUNGLE)
                .addTag(Tags.Biomes.IS_SWAMP);

        this.tag(FintyTags.Biomes.FWSHARK_BIOMES)
                .addTag(BiomeTags.IS_RIVER)
                .addTag(BiomeTags.IS_JUNGLE);

        this.tag(FintyTags.Biomes.MINNOW_SURFACE_BIOMES)
                .addTag(BiomeTags.IS_RIVER)
                .addTag(BiomeTags.IS_JUNGLE)
                .addTag(Tags.Biomes.IS_SWAMP)
                .addTag(BiomeTags.IS_BEACH)
                .addTag(BiomeTags.IS_OCEAN);


        this.tag(FintyTags.Biomes.PLECO_BIOMES)
                .addTag(BiomeTags.IS_JUNGLE)
                .addTag(Tags.Biomes.IS_SWAMP);

        this.tag(FintyTags.Biomes.ARTEMIA_BIOMES)
                .addTag(Tags.Biomes.IS_DESERT);

        this.tag(FintyTags.Biomes.DAPHNIA_BIOMES)
                .addTag(BiomeTags.IS_JUNGLE)
                .addTag(BiomeTags.IS_FOREST)
                .addTag(BiomeTags.IS_MOUNTAIN)
                .addTag(BiomeTags.IS_BADLANDS)
                .addTag(BiomeTags.IS_SAVANNA)
                .addTag(Tags.Biomes.IS_PLAINS)
                .addTag(Tags.Biomes.IS_LUSH);

        this.tag(FintyTags.Biomes.COPEPOD_FRESHWATER_BIOMES)
                .addTag(BiomeTags.IS_JUNGLE)
                .addTag(BiomeTags.IS_FOREST)
                .addTag(BiomeTags.IS_MOUNTAIN)
                .addTag(BiomeTags.IS_BADLANDS)
                .addTag(BiomeTags.IS_SAVANNA)
                .addTag(Tags.Biomes.IS_PLAINS)
                .addTag(Tags.Biomes.IS_LUSH);

        this.tag(FintyTags.Biomes.COPEPOD_SALTWATER_BIOMES)
                .addTag(BiomeTags.IS_OCEAN);

        this.tag(FintyTags.Biomes.FRESHWATER_PLANT_BIOME_BLACKLIST)
                .addTag(BiomeTags.IS_OCEAN);

        this.tag(FintyTags.Biomes.MOONY_BIOMES)
                .add(Biomes.MANGROVE_SWAMP)
                .add(Biomes.BEACH)
                .add(Biomes.LUKEWARM_OCEAN)
                .add(Biomes.WARM_OCEAN)
                .add(Biomes.DEEP_LUKEWARM_OCEAN);

        this.tag(FintyTags.Biomes.GOURAMI_BIOMES)
                .addTag(Tags.Biomes.IS_SWAMP);

        this.tag(FintyTags.Biomes.LIVEROCK_BOULDER_BIOMES)
                .addTag(BiomeTags.PRODUCES_CORALS_FROM_BONEMEAL);

        this.tag(FintyTags.Biomes.STROMATOLITE_BIOMES)
                .addTag(BiomeTags.IS_BEACH);

        this.tag(FintyTags.Biomes.ANUBIAS_BIOMES)
                .addTag(BiomeTags.IS_JUNGLE);

        this.tag(FintyTags.Biomes.HORNWORT_BIOMES)
                .add(Biomes.RIVER);

        this.tag(FintyTags.Biomes.DUCKWEED_BIOMES)
                .addTag(Tags.Biomes.IS_SWAMP);

        this.tag(FintyTags.Biomes.AQUATIC_MOSS_BIOMES)
                .addTag(BiomeTags.IS_JUNGLE)
                .addTag(Tags.Biomes.IS_SWAMP)
                .addTag(Tags.Biomes.IS_LUSH);

        this.tag(FintyTags.Biomes.AMAZON_SWORD_BIOMES)
                .addTag(BiomeTags.IS_JUNGLE);

        this.tag(FintyTags.Biomes.LOTUS_BIOMES)
                .addTag(Tags.Biomes.IS_SWAMP)
                .addTag(BiomeTags.IS_JUNGLE);

        this.tag(FintyTags.Biomes.COD_BIOMES)
                .addTag(BiomeTags.IS_OCEAN);

        this.tag(FintyTags.Biomes.SALMON_BIOMES)
                .add(Biomes.RIVER)
                .add(Biomes.FROZEN_RIVER)
                .add(Biomes.DEEP_FROZEN_OCEAN)
                .add(Biomes.FROZEN_OCEAN)
                .add(Biomes.COLD_OCEAN)
                .add(Biomes.DEEP_COLD_OCEAN);
    }

}
