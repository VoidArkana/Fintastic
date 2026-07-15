package net.voidarkana.fintastic.util.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.entity.FintyEntities;
import net.voidarkana.fintastic.util.FintyTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class FintyEntityTypeTagGenerator extends EntityTypeTagsProvider {
    public FintyEntityTypeTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, Fintastic.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(FintyTags.EntityTypes.FISHNET_ADDITIONS)
                .add(EntityType.GUARDIAN)
                .add(EntityType.AXOLOTL)
                .add(EntityType.FROG)
                .add(EntityType.TURTLE)
                .addOptional(ResourceLocation.parse("alexsmobs:alligator_snapping_turtle"))
                .addOptional(ResourceLocation.parse("alexsmobs:terrapin"))
                .addOptional(ResourceLocation.parse("alexsmobs:alligator_snapping_turtle"))
                .addOptional(ResourceLocation.parse("alexsmobs:skelewag"))
                .addOptional(ResourceLocation.parse("alexsmobs:caiman"))
                .addOptional(ResourceLocation.parse("alexsmobs:cosmaw"))
                .addOptional(ResourceLocation.parse("alexsmobs:cosmic_cod"))
                .addOptional(ResourceLocation.parse("alexsmobs:skelewag"))
                .addOptional(ResourceLocation.parse("alexsmobs:endergrade"))
                .addOptional(ResourceLocation.parse("alexsmobs:skelewag"))
                .addOptional(ResourceLocation.parse("alexsmobs:mantis_shrimp"))
                .addOptional(ResourceLocation.parse("alexsmobs:mimic_octopus"))
                .addOptional(ResourceLocation.parse("alexsmobs:mudskipper"))
                .addOptional(ResourceLocation.parse("alexsmobs:platypus"))
                .addOptional(ResourceLocation.parse("alexsmobs:rain_frog"))
                .addOptional(ResourceLocation.parse("alexsmobs:sea_bear"))
                .addOptional(ResourceLocation.parse("alexsmobs:stradpole"))
                .addOptional(ResourceLocation.parse("babyfat:ranchu"))
                .addOptional(ResourceLocation.parse("spawn:tuna"))
                .addOptional(ResourceLocation.parse("spawn:sea_cow"))
                .addOptional(ResourceLocation.parse("spawn:octopus"))
                .addOptional(ResourceLocation.parse("alexscaves:mineguardians"))
                .addOptional(ResourceLocation.parse("upgrade_aquatic:thrasher"));

        this.tag(FintyTags.EntityTypes.FISHNET_BLACKLIST)
                .addOptional(ResourceLocation.parse("alexsmobs:cachalot_whale"))
                .addOptional(ResourceLocation.parse("alexsmobs:orca"))
                .addOptional(ResourceLocation.parse("alexscaves:hullbreaker"));

        this.tag(FintyTags.EntityTypes.PREDATOR_FISH).add(FintyEntities.ARAPAIMA.get()).add(FintyEntities.GUPPY.get())
                .add(FintyEntities.CATFISH.get()).add(FintyEntities.SHARKMINNOW.get()).add(FintyEntities.FEATHERBACK.get())
                .add(FintyEntities.MINNOW.get());

        this.tag(FintyTags.EntityTypes.FISH_PREY).add(FintyEntities.FAIRY_SHRIMP.get()).add(FintyEntities.DAPHNIA.get());

        this.tag(EntityTypeTags.AXOLOTL_HUNT_TARGETS)
                .add(FintyEntities.GOURAMI.get())
                .add(FintyEntities.GUPPY.get())
                .add(FintyEntities.COD.get())
                .add(FintyEntities.SALMON.get())
                .add(FintyEntities.COPEPOD.get())
                .add(FintyEntities.MOONY.get())
                .add(FintyEntities.DAPHNIA.get())
                .add(FintyEntities.FAIRY_SHRIMP.get());
    }
}
