package net.voidarkana.fintastic.util.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.item.YAFMItems;
import net.voidarkana.fintastic.util.YAFMTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class YAFMItemTagGenerator extends ItemTagsProvider {

    public YAFMItemTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, Fintastic.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        this.tag(YAFMTags.Items.FISH_FEED)
                .add(YAFMItems.REGULAR_FEED.get())
                .add(YAFMItems.GREAT_FEED.get())
                .add(YAFMItems.QUALITY_FEED.get())
                .add(YAFMItems.PREMIUM_FEED.get())
                .add(YAFMItems.ARTEMIA_BUCKET.get());

        this.tag(ItemTags.FISHES).add(YAFMItems.RAW_FISH.get()).add(YAFMItems.COOKED_FISH.get());

        this.tag(ItemTags.AXOLOTL_TEMPT_ITEMS)
                .add(YAFMItems.MINNOW_BUCKET.get())
                .add(YAFMItems.FRESHWATER_SHARK_BUCKET.get())
                .add(YAFMItems.GUPPY_BUCKET.get())
                .add(YAFMItems.FEATHERBACK_BUCKET.get())
                .add(YAFMItems.PLECO_BUCKET.get())
                .add(YAFMItems.CATFISH_BUCKET.get())
                .add(YAFMItems.ARTEMIA_BUCKET.get());

        this.tag(ItemTags.PIGLIN_LOVED)
                .add(YAFMItems.PREMIUM_FEED.get());

        this.tag(ItemTags.MUSIC_DISCS).add(YAFMItems.SALTY_MUSIC_DISC.get()).add(YAFMItems.AXOLOTL_MUSIC_DISC.get())
                .add(YAFMItems.DRAGONFISH_MUSIC_DISC.get()).add(YAFMItems.SHUNJI_MUSIC_DISC.get()).add(YAFMItems.FRESH_MUSIC_DISC.get());

        this.tag(ItemTags.CREEPER_DROP_MUSIC_DISCS).add(YAFMItems.SALTY_MUSIC_DISC.get()).add(YAFMItems.AXOLOTL_MUSIC_DISC.get())
                .add(YAFMItems.DRAGONFISH_MUSIC_DISC.get()).add(YAFMItems.SHUNJI_MUSIC_DISC.get()).add(YAFMItems.FRESH_MUSIC_DISC.get());

        this.tag(YAFMTags.Items.URANIUM).addOptional(new ResourceLocation( "alexscaves:radon_bottle"));

        this.tag(YAFMTags.Items.SUGAR_GLASS).addOptional(new ResourceLocation( "alexscaves:peppermint_powder"));
    }
}
