package net.voidarkana.fintastic.util.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.entity.FintyEntities;
import net.voidarkana.fintastic.util.FintyTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class FintyEntityTypeTagGenerator extends EntityTypeTagsProvider {
    public FintyEntityTypeTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, Fintastic.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(FintyTags.EntityType.FISHNET_ADDITIONS).add(EntityType.GUARDIAN).add(EntityType.AXOLOTL)
                .add(EntityType.FROG).add(EntityType.TURTLE)
                .addOptional(new ResourceLocation("alexsmobs:alligator_snapping_turtle"))
                .addOptional(new ResourceLocation("alexsmobs:terrapin"))
                .addOptional(new ResourceLocation("alexsmobs:alligator_snapping_turtle"))
                .addOptional(new ResourceLocation("alexsmobs:skelewag"))
                .addOptional(new ResourceLocation("alexsmobs:caiman"))
                .addOptional(new ResourceLocation("alexsmobs:cosmaw"))
                .addOptional(new ResourceLocation("alexsmobs:cosmic_cod"))
                .addOptional(new ResourceLocation("alexsmobs:skelewag"))
                .addOptional(new ResourceLocation("alexsmobs:endergrade"))
                .addOptional(new ResourceLocation("alexsmobs:skelewag"))
                .addOptional(new ResourceLocation("alexsmobs:mantis_shrimp"))
                .addOptional(new ResourceLocation("alexsmobs:mimic_octopus"))
                .addOptional(new ResourceLocation("alexsmobs:mudskipper"))
                .addOptional(new ResourceLocation("alexsmobs:platypus"))
                .addOptional(new ResourceLocation("alexsmobs:rain_frog"))
                .addOptional(new ResourceLocation("alexsmobs:sea_bear"))
                .addOptional(new ResourceLocation("alexsmobs:stradpole"))
                .addOptional(new ResourceLocation("babyfat:ranchu"))
                .addOptional(new ResourceLocation("spawn:tuna"))
                .addOptional(new ResourceLocation("alexscaves:mineguardians"))
                .addOptional(new ResourceLocation("upgrade_aquatic:thrasher"));

        this.tag(FintyTags.EntityType.FISHNET_BLACKLIST)
                .addOptional(new ResourceLocation("alexsmobs:cachalot_whale"))
                .addOptional(new ResourceLocation("alexsmobs:orca"))
                .addOptional(new ResourceLocation("alexscaves:hullbreaker"));

        this.tag(FintyTags.EntityType.PREDATOR_FISH).add(FintyEntities.ARAPAIMA.get()).add(FintyEntities.GUPPY.get())
                .add(FintyEntities.CATFISH.get()).add(FintyEntities.SHARKMINNOW.get()).add(FintyEntities.FEATHERBACK.get())
                .add(FintyEntities.MINNOW.get());

        this.tag(FintyTags.EntityType.FISH_PREY).add(FintyEntities.ARTEMIA.get()).add(FintyEntities.DAPHNIA.get());
    }
}
