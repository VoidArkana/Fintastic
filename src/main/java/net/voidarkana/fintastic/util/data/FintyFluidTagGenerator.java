package net.voidarkana.fintastic.util.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.util.FintyTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class FintyFluidTagGenerator extends FluidTagsProvider {

    public FintyFluidTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, Fintastic.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(FintyTags.Fluids.AC_ACID)
                .addOptional(ResourceLocation.parse("alexscaves:acid"))
                .addOptional(ResourceLocation.parse("alexscaves:flowing_acid"));

        this.tag(FintyTags.Fluids.AC_SODA)
                .addOptional(ResourceLocation.parse("alexscaves:purple_soda"))
                .addOptional(ResourceLocation.parse("alexscaves:flowing_purple_soda"));
    }
}
