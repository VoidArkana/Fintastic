package net.voidarkana.fintastic.util.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
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

        this.tag(BlockTags.WALLS).add(YAFMBlocks.STROMATOLITE_BRICKS_WALL.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(YAFMBlocks.STROMATOLITE_BRICKS.get())
                .add(YAFMBlocks.STROMATOLITE_BRICKS_WALL.get())
                .add(YAFMBlocks.STROMATOLITE_BRICKS_SLAB.get())
                .add(YAFMBlocks.STROMATOLITE_BRICKS_STAIRS.get())
                .add(YAFMBlocks.STROMATOLITE_BLOCK.get())
                .add(YAFMBlocks.CUT_STROMATOLITE_BLOCK.get())
                .add(YAFMBlocks.FOSSIL_STROMATOLITE_BLOCK.get())
                .add(YAFMBlocks.STROMATOLITE.get())
                .add(YAFMBlocks.FOSSIL_STROMATOLITE.get())
                .add(YAFMBlocks.STROMATOLITE_GROWTHS.get())
                .add(YAFMBlocks.FOSSIL_STROMATOLITE_GROWTHS.get())
                .add(YAFMBlocks.LIVE_ROCK.get())
                .add(YAFMBlocks.POROUS_LIVE_ROCK.get())
                .add(YAFMBlocks.DEAD_LIVE_ROCK.get())
                .add(YAFMBlocks.DEAD_POROUS_LIVE_ROCK.get())
                .add(YAFMBlocks.RED_ALGAE_LIVE_ROCK.get())
                .add(YAFMBlocks.GREEN_ALGAE_LIVE_ROCK.get());

        this.tag(BlockTags.MINEABLE_WITH_HOE)
                .add(YAFMBlocks.GREEN_ALGAE_BLOCK.get())
                .add(YAFMBlocks.RED_ALGAE_BLOCK.get())
                .add(YAFMBlocks.GREEN_ALGAE_CARPET.get())
                .add(YAFMBlocks.RED_ALGAE_CARPET.get());

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

        this.tag(Tags.Blocks.GLASS).addTag(YAFMTags.Blocks.AQUARIUM_GLASS);

        this.tag(YAFMTags.Blocks.FRESHWATER_PLANTS)
                .add(YAFMBlocks.HORNWORT.get());

        this.tag(YAFMTags.Blocks.GREEN_ALGAE)
                .add(YAFMBlocks.GREEN_ALGAE_LIVE_ROCK.get())
                .add(YAFMBlocks.GREEN_ALGAE_BLOCK.get());

        this.tag(YAFMTags.Blocks.RED_ALGAE)
                .add(YAFMBlocks.RED_ALGAE_LIVE_ROCK.get())
                .add(YAFMBlocks.RED_ALGAE_BLOCK.get());

        this.tag(YAFMTags.Blocks.ALGAE_REPLACEABLE)
                .addTag(BlockTags.MOSS_REPLACEABLE)
                .addTag(Tags.Blocks.SAND);

        this.tag(YAFMTags.Blocks.STROMATOLITE_REPLACEABLE)
                .addTag(BlockTags.STONE_ORE_REPLACEABLES)
                .addTag(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        this.tag(YAFMTags.Blocks.STROMATOLITE_REPLACEABLE)
                .addTag(Tags.Blocks.STONE).addTag(Tags.Blocks.SAND)
                .addTag(BlockTags.MOSS_REPLACEABLE);

        this.tag(YAFMTags.Blocks.AQUATIC_PLANTS)
                .add(YAFMBlocks.ANUBIAS.get())
                .add(YAFMBlocks.CAULERPA.get())
                .add(YAFMBlocks.HORNWORT.get())
                .add(YAFMBlocks.DUCKWEED.get())
                .add(YAFMBlocks.RED_ALGAE.get())
                .add(YAFMBlocks.RED_ALGAE_FAN.get())
                .add(YAFMBlocks.RED_ALGAE_WALL_FAN.get())
                .add(YAFMBlocks.SEA_GRAPES.get())
                .add(YAFMBlocks.SEA_GRAPES_PLANT.get())
                .addTag(BlockTags.UNDERWATER_BONEMEALS)
                .add(Blocks.SEA_PICKLE)
                .add(Blocks.TALL_SEAGRASS)
                .add(Blocks.KELP_PLANT)
                .add(Blocks.KELP)
                .add(Blocks.LILY_PAD);

        this.tag(YAFMTags.Blocks.GOURAMI_INVESTIGATION_TARGETS)
                .addTag(YAFMTags.Blocks.AQUATIC_PLANTS)
                .add(YAFMBlocks.STROMATOLITE.get())
                .add(YAFMBlocks.STROMATOLITE_GROWTHS.get())
                .add(YAFMBlocks.FOSSIL_STROMATOLITE.get())
                .add(YAFMBlocks.FOSSIL_STROMATOLITE_GROWTHS.get());
    }
}
