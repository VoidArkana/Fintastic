package net.voidarkana.fintastic.util.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.block.FintyBlocks;
import net.voidarkana.fintastic.common.item.FintyItems;
import net.voidarkana.fintastic.util.FintyTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class FintyItemTagGenerator extends ItemTagsProvider {

    public FintyItemTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, Fintastic.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        this.tag(FintyTags.Items.FISH_FEED)
                .add(FintyItems.REGULAR_FEED.get())
                .add(FintyItems.GREAT_FEED.get())
                .add(FintyItems.QUALITY_FEED.get())
                .add(FintyItems.PREMIUM_FEED.get())
                .add(FintyItems.ARTEMIA_BUCKET.get());

        this.tag(ItemTags.FISHES).add(FintyItems.RAW_FISH.get()).add(FintyItems.COOKED_FISH.get());

        this.tag(ItemTags.AXOLOTL_TEMPT_ITEMS)
                .add(FintyItems.MINNOW_BUCKET.get())
                .add(FintyItems.FRESHWATER_SHARK_BUCKET.get())
                .add(FintyItems.GUPPY_BUCKET.get())
                .add(FintyItems.FEATHERBACK_BUCKET.get())
                .add(FintyItems.PLECO_BUCKET.get())
                .add(FintyItems.ARAPAIMA_BUCKET.get())
                .add(FintyItems.CATFISH_BUCKET.get())
                .add(FintyItems.ARTEMIA_BUCKET.get());

        this.tag(ItemTags.PIGLIN_LOVED)
                .add(FintyItems.PREMIUM_FEED.get());

        this.tag(ItemTags.MUSIC_DISCS).add(FintyItems.SALTY_MUSIC_DISC.get()).add(FintyItems.AXOLOTL_MUSIC_DISC.get())
                .add(FintyItems.DRAGONFISH_MUSIC_DISC.get()).add(FintyItems.SHUNJI_MUSIC_DISC.get()).add(FintyItems.FRESH_MUSIC_DISC.get());

        this.tag(ItemTags.CREEPER_DROP_MUSIC_DISCS).add(FintyItems.SALTY_MUSIC_DISC.get()).add(FintyItems.AXOLOTL_MUSIC_DISC.get())
                .add(FintyItems.DRAGONFISH_MUSIC_DISC.get()).add(FintyItems.SHUNJI_MUSIC_DISC.get()).add(FintyItems.FRESH_MUSIC_DISC.get());

        this.tag(FintyTags.Items.URANIUM).addOptional(new ResourceLocation( "alexscaves:radon_bottle"));

        this.tag(FintyTags.Items.SUGAR_GLASS).addOptional(new ResourceLocation( "alexscaves:peppermint_powder"));

        this.tag(FintyTags.Items.FISHBOWL_PLANTS)
                .add(FintyBlocks.ANUBIAS.get().asItem(),
                        FintyBlocks.HORNWORT.get().asItem(),
                        Items.BRAIN_CORAL,
                        Items.BRAIN_CORAL_FAN,
                        Items.DEAD_BRAIN_CORAL,
                        Items.DEAD_BRAIN_CORAL_FAN,

                        Items.BUBBLE_CORAL,
                        Items.BUBBLE_CORAL_FAN,
                        Items.DEAD_BUBBLE_CORAL,
                        Items.DEAD_BUBBLE_CORAL_FAN,

                        Items.TUBE_CORAL,
                        Items.TUBE_CORAL_FAN,
                        Items.DEAD_TUBE_CORAL,
                        Items.DEAD_TUBE_CORAL_FAN,

                        Items.FIRE_CORAL,
                        Items.FIRE_CORAL_FAN,
                        Items.DEAD_FIRE_CORAL,
                        Items.DEAD_FIRE_CORAL_FAN,

                        Items.HORN_CORAL,
                        Items.HORN_CORAL_FAN,
                        Items.DEAD_HORN_CORAL,
                        Items.DEAD_HORN_CORAL_FAN,

                        Items.KELP,
                        Items.SEAGRASS,
                        Items.SEA_PICKLE,
                        FintyBlocks.SEA_GRAPES.get().asItem(),
                        FintyBlocks.DRAGONS_BREATH_ALGAE.get().asItem(),
                        FintyBlocks.CAULERPA.get().asItem(),
                        FintyBlocks.RED_ALGAE.get().asItem(),
                        FintyBlocks.RED_ALGAE_FAN.get().asItem(),
                        FintyBlocks.STROMATOLITE_GROWTHS.get().asItem(),
                        FintyBlocks.FOSSIL_STROMATOLITE_GROWTHS.get().asItem())
                .addOptional(new ResourceLocation("marvelous_menagerie:charnia"))
                .addOptional(new ResourceLocation("marvelous_menagerie:wiwaxia"))
                .addOptional(new ResourceLocation("marvelous_menagerie:dickinsonia"))
                .addOptional(new ResourceLocation("marvelous_menagerie:herpetogaster"));
    }
}
