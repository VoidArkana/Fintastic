package net.voidarkana.fintastic.util.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.block.FintyBlocks;
import net.voidarkana.fintastic.util.FintyTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class FintyBlockTagGenerator extends BlockTagsProvider {

    public FintyBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Fintastic.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        this.tag(BlockTags.WALLS).add(FintyBlocks.STROMATOLITE_BRICKS_WALL.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(FintyBlocks.STROMATOLITE_BRICKS.get())
                .add(FintyBlocks.STROMATOLITE_BRICKS_WALL.get())
                .add(FintyBlocks.STROMATOLITE_BRICKS_SLAB.get())
                .add(FintyBlocks.STROMATOLITE_BRICKS_STAIRS.get())
                .add(FintyBlocks.STROMATOLITE_BLOCK.get())
                .add(FintyBlocks.CUT_STROMATOLITE_BLOCK.get())
                .add(FintyBlocks.FOSSIL_STROMATOLITE_BLOCK.get())
                .add(FintyBlocks.STROMATOLITE.get())
                .add(FintyBlocks.FOSSIL_STROMATOLITE.get())
                .add(FintyBlocks.STROMATOLITE_GROWTHS.get())
                .add(FintyBlocks.FOSSIL_STROMATOLITE_GROWTHS.get())
                .add(FintyBlocks.LIVE_ROCK.get())
                .add(FintyBlocks.POROUS_LIVE_ROCK.get())
                .add(FintyBlocks.DEAD_LIVE_ROCK.get())
                .add(FintyBlocks.DEAD_POROUS_LIVE_ROCK.get())
                .add(FintyBlocks.RED_ALGAE_LIVE_ROCK.get())
                .add(FintyBlocks.GREEN_ALGAE_LIVE_ROCK.get());

        this.tag(BlockTags.MINEABLE_WITH_HOE)
                .add(FintyBlocks.GREEN_ALGAE_BLOCK.get())
                .add(FintyBlocks.RED_ALGAE_BLOCK.get())
                .add(FintyBlocks.GREEN_ALGAE_CARPET.get())
                .add(FintyBlocks.RED_ALGAE_CARPET.get())
                .add(FintyBlocks.AQUATIC_MOSS_BLOCK.get())
                .add(FintyBlocks.AQUATIC_MOSS_CARPET.get());

        this.tag(FintyTags.Blocks.AQUARIUM_GLASS)
                .add(FintyBlocks.AQUARIUM_GLASS.get())
                .add(FintyBlocks.AQUARIUM_GLASS_PANE.get())
                .add(FintyBlocks.TINTED_AQUARIUM_GLASS.get())
                .add(FintyBlocks.CLEAR_AQUARIUM_GLASS.get())
                .add(FintyBlocks.CLEAR_AQUARIUM_GLASS_PANE.get())
                .add(FintyBlocks.INFERNAL_AQUARIUM_GLASS.get())
                .add(FintyBlocks.INFERNAL_AQUARIUM_GLASS_PANE.get())
                .add(FintyBlocks.TINTED_INFERNAL_AQUARIUM_GLASS.get())
                .add(FintyBlocks.RADON_AQUARIUM_GLASS.get())
                .add(FintyBlocks.RADON_AQUARIUM_GLASS_PANE.get())
                .add(FintyBlocks.TINTED_RADON_AQUARIUM_GLASS.get())
                .add(FintyBlocks.SUGAR_AQUARIUM_GLASS.get())
                .add(FintyBlocks.SUGAR_AQUARIUM_GLASS_PANE.get())
                .add(FintyBlocks.TINTED_SUGAR_AQUARIUM_GLASS.get());

        this.tag(FintyTags.Blocks.INFERNAL_AQUARIUM_GLASS)
                .add(FintyBlocks.INFERNAL_AQUARIUM_GLASS.get())
                .add(FintyBlocks.INFERNAL_AQUARIUM_GLASS_PANE.get())
                .add(FintyBlocks.TINTED_INFERNAL_AQUARIUM_GLASS.get());

        this.tag(FintyTags.Blocks.RADON_AQUARIUM_GLASS)
                .add(FintyBlocks.RADON_AQUARIUM_GLASS.get())
                .add(FintyBlocks.RADON_AQUARIUM_GLASS_PANE.get())
                .add(FintyBlocks.TINTED_RADON_AQUARIUM_GLASS.get());

        this.tag(FintyTags.Blocks.SUGAR_AQUARIUM_GLASS)
                .add(FintyBlocks.SUGAR_AQUARIUM_GLASS.get())
                .add(FintyBlocks.SUGAR_AQUARIUM_GLASS_PANE.get())
                .add(FintyBlocks.TINTED_SUGAR_AQUARIUM_GLASS.get());

        this.tag(BlockTags.IMPERMEABLE).addTag(FintyTags.Blocks.AQUARIUM_GLASS);

        this.tag(Tags.Blocks.GLASS).addTag(FintyTags.Blocks.AQUARIUM_GLASS);

        this.tag(FintyTags.Blocks.FRESHWATER_PLANTS)
                .add(FintyBlocks.HORNWORT.get());

        this.tag(FintyTags.Blocks.GREEN_ALGAE)
                .add(FintyBlocks.GREEN_ALGAE_LIVE_ROCK.get())
                .add(FintyBlocks.GREEN_ALGAE_BLOCK.get());

        this.tag(FintyTags.Blocks.RED_ALGAE)
                .add(FintyBlocks.RED_ALGAE_LIVE_ROCK.get())
                .add(FintyBlocks.RED_ALGAE_BLOCK.get());

        this.tag(FintyTags.Blocks.ALGAE_REPLACEABLE)
                .addTag(BlockTags.MOSS_REPLACEABLE)
                .addTag(Tags.Blocks.SAND)
                .addTag(FintyTags.Blocks.GREEN_ALGAE)
                .addTag(FintyTags.Blocks.RED_ALGAE)
                .add(FintyBlocks.AQUATIC_MOSS_BLOCK.get());

        this.tag(FintyTags.Blocks.STROMATOLITE_REPLACEABLE)
                .addTag(BlockTags.STONE_ORE_REPLACEABLES)
                .addTag(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        this.tag(FintyTags.Blocks.FOSSIL_STROMATOLITE_REPLACEABLE)
                .addTag(Tags.Blocks.STONE).addTag(Tags.Blocks.SAND)
                .addTag(BlockTags.MOSS_REPLACEABLE);

        this.tag(FintyTags.Blocks.AQUATIC_PLANTS)
                .add(FintyBlocks.ANUBIAS.get())
                .add(FintyBlocks.CAULERPA.get())
                .add(FintyBlocks.HORNWORT.get())
                .add(FintyBlocks.DUCKWEED.get())
                .add(FintyBlocks.RED_ALGAE.get())
                .add(FintyBlocks.RED_ALGAE_FAN.get())
                .add(FintyBlocks.RED_ALGAE_WALL_FAN.get())
                .add(FintyBlocks.SEA_GRAPES.get())
                .add(FintyBlocks.SEA_GRAPES_PLANT.get())
                .addTag(BlockTags.UNDERWATER_BONEMEALS)
                .add(Blocks.SEA_PICKLE)
                .add(Blocks.TALL_SEAGRASS)
                .add(Blocks.KELP_PLANT)
                .add(Blocks.KELP)
                .add(Blocks.LILY_PAD)
                .add(FintyBlocks.LOTUS.get())
                .add(FintyBlocks.LOTUS_PAD.get())
                .add(FintyBlocks.LOTUS_FLOWER.get())
                .add(FintyBlocks.AQUATIC_MOSS_PHYLLID.get())
                .add(FintyBlocks.BLUE_HYPNEA.get())
                .add(FintyBlocks.TALL_AMAZON_SWORD.get())
                .add(FintyBlocks.AMAZON_SWORD.get());

        this.tag(FintyTags.Blocks.GOURAMI_INVESTIGATION_TARGETS)
                .addTag(FintyTags.Blocks.AQUATIC_PLANTS)
                .add(FintyBlocks.STROMATOLITE.get())
                .add(FintyBlocks.STROMATOLITE_GROWTHS.get())
                .add(FintyBlocks.FOSSIL_STROMATOLITE.get())
                .add(FintyBlocks.FOSSIL_STROMATOLITE_GROWTHS.get());

        this.tag(FintyTags.Blocks.LOTUS_GROWABLE)
                .add(Blocks.ROOTED_DIRT)
                .add(Blocks.MUD)
                .add(Blocks.MUDDY_MANGROVE_ROOTS);

        this.tag(FintyTags.Blocks.HOUNEN_FAIRY_SHRIMP_GROWABLE)
                .addOptional(new ResourceLocation("farmersdelight:rice"));
    }
}
