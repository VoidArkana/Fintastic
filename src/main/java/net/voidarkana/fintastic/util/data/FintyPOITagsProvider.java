package net.voidarkana.fintastic.util.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.tags.PoiTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.voidarkana.fintastic.Fintastic;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class FintyPOITagsProvider extends PoiTypeTagsProvider {
    public FintyPOITagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, Fintastic.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(PoiTypeTags.ACQUIRABLE_JOB_SITE).addOptional(Fintastic.location("fishbowl_poi"));
    }
}
