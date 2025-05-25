package net.voidarkana.fintastic.util.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.block.YAFMBlocks;
import net.voidarkana.fintastic.util.YAFMTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class YAFMBlockTagGenerator extends BlockTagsProvider {

    public YAFMBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Fintastic.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        this.tag(BlockTags.UNDERWATER_BONEMEALS)
                .add(YAFMBlocks.HORNWORT.get());

        this.tag(YAFMTags.Blocks.AQUARIUM_GLASS)
                .add(YAFMBlocks.AQUARIUM_GLASS.get())
                .add(YAFMBlocks.AQUARIUM_GLASS_PANE.get())
                .add(YAFMBlocks.TINTED_AQUARIUM_GLASS.get())
                .add(YAFMBlocks.CLEAR_AQUARIUM_GLASS.get())
                .add(YAFMBlocks.CLEAR_AQUARIUM_GLASS_PANE.get())
                .add(YAFMBlocks.INFERNAL_AQUARIUM_GLASS.get())
                .add(YAFMBlocks.INFERNAL_AQUARIUM_GLASS_PANE.get())
                .add(YAFMBlocks.TINTED_INFERNAL_AQUARIUM_GLASS.get())
                .add(YAFMBlocks.RADON_AQUARIUM_GLASS.get())
                .add(YAFMBlocks.RADON_AQUARIUM_GLASS_PANE.get())
                .add(YAFMBlocks.TINTED_RADON_AQUARIUM_GLASS.get())
                .add(YAFMBlocks.SUGAR_AQUARIUM_GLASS.get())
                .add(YAFMBlocks.SUGAR_AQUARIUM_GLASS_PANE.get())
                .add(YAFMBlocks.TINTED_SUGAR_AQUARIUM_GLASS.get());

        this.tag(YAFMTags.Blocks.INFERNAL_AQUARIUM_GLASS)
                .add(YAFMBlocks.INFERNAL_AQUARIUM_GLASS.get())
                .add(YAFMBlocks.INFERNAL_AQUARIUM_GLASS_PANE.get())
                .add(YAFMBlocks.TINTED_INFERNAL_AQUARIUM_GLASS.get());

        this.tag(YAFMTags.Blocks.RADON_AQUARIUM_GLASS)
                .add(YAFMBlocks.RADON_AQUARIUM_GLASS.get())
                .add(YAFMBlocks.RADON_AQUARIUM_GLASS_PANE.get())
                .add(YAFMBlocks.TINTED_RADON_AQUARIUM_GLASS.get());

        this.tag(YAFMTags.Blocks.SUGAR_AQUARIUM_GLASS)
                .add(YAFMBlocks.SUGAR_AQUARIUM_GLASS.get())
                .add(YAFMBlocks.SUGAR_AQUARIUM_GLASS_PANE.get())
                .add(YAFMBlocks.TINTED_SUGAR_AQUARIUM_GLASS.get());

        this.tag(BlockTags.IMPERMEABLE).addTag(YAFMTags.Blocks.AQUARIUM_GLASS);

        this.tag(BlockTags.UNDERWATER_BONEMEALS).add(YAFMBlocks.HORNWORT.get());

        this.tag(Tags.Blocks.GLASS).addTags(YAFMTags.Blocks.AQUARIUM_GLASS);
    }
}
