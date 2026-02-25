package net.voidarkana.fintastic.util.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.util.FintyTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class FintyFluidTagGenerator extends FluidTagsProvider {

    public FintyFluidTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, Fintastic.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(FintyTags.Fluid.AC_ACID)
                .addOptional(new ResourceLocation("alexscaves:acid"))
                .addOptional(new ResourceLocation("alexscaves:flowing_acid"));

        this.tag(FintyTags.Fluid.AC_SODA)
                .addOptional(new ResourceLocation("alexscaves:purple_soda"))
                .addOptional(new ResourceLocation("alexscaves:flowing_purple_soda"));
    }
}
