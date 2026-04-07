package net.voidarkana.fintastic.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.block.FintyBlocks;
import net.voidarkana.fintastic.common.item.FintyItems;

public class FintyCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Fintastic.MOD_ID);

    public static final RegistryObject<CreativeModeTab> YAFM_CREATIVE_TAB =
            CREATIVE_MODE_TABS.register("fintastic_creative_tab", ()-> CreativeModeTab.builder().icon(() -> new ItemStack(FintyItems.FEATHERBACK.get()))
                    .title(Component.translatable("creativetab.fintastic_creative_tab"))
                    .displayItems((itemDisplayParameters, output) -> {

                        output.accept(FintyItems.RAW_FISH.get());
                        output.accept(FintyItems.COOKED_FISH.get());

                        output.accept(FintyItems.BAD_FEED.get());
                        output.accept(FintyItems.REGULAR_FEED.get());
                        output.accept(FintyItems.QUALITY_FEED.get());
                        output.accept(FintyItems.GREAT_FEED.get());
                        output.accept(FintyItems.PREMIUM_FEED.get());

                        output.accept(FintyItems.FISHNET.get());

                        output.accept(FintyItems.FISHING_HAT.get());
                        output.accept(FintyBlocks.FISHBOWL.get());

                        output.accept(FintyItems.ARAPAIMA.get());
                        output.accept(FintyItems.ARAPAIMA_BUCKET.get());
                        output.accept(FintyItems.CATFISH.get());
                        output.accept(FintyItems.CATFISH_BUCKET.get());
                        output.accept(FintyItems.COELACANTH.get());
                        output.accept(FintyItems.COPEPOD.get());
                        output.accept(FintyItems.COPEPOD_BUCKET.get());
                        output.accept(FintyItems.DAPHNIA.get());
                        output.accept(FintyItems.DAPHNIA_BUCKET.get());
                        output.accept(FintyItems.FAIRY_SHRIMP.get());
                        output.accept(FintyItems.ARTEMIA_BUCKET.get());
                        output.accept(FintyItems.FEATHERBACK.get());
                        output.accept(FintyItems.FEATHERBACK_BUCKET.get());
                        output.accept(FintyItems.FRESHWATER_SHARK_BUCKET.get());
                        output.accept(FintyItems.GUPPY.get());
                        output.accept(FintyItems.GUPPY_BUCKET.get());
                        output.accept(FintyItems.GOURAMI.get());
                        output.accept(FintyItems.GOURAMI_BUCKET.get());
                        output.accept(FintyItems.MINNOW.get());
                        output.accept(FintyItems.MINNOW_BUCKET.get());
                        output.accept(FintyItems.MOONY.get());
                        output.accept(FintyItems.MOONY_BUCKET.get());
                        output.accept(FintyItems.PLECO.get());
                        output.accept(FintyItems.PLECO_BUCKET.get());
                        output.accept(FintyItems.SHARKMINNOW.get());

                        output.accept(FintyItems.ARAPAIMA_SPAWN_EGG.get());
                        output.accept(FintyItems.CATFISH_SPAWN_EGG.get());
                        output.accept(FintyItems.COELACANTH_SPAWN_EGG.get());
                        output.accept(FintyItems.COPEPOD_SPAWN_EGG.get());
                        output.accept(FintyItems.DAPHNIA_SPAWN_EGG.get());
                        output.accept(FintyItems.FAIRY_SHRIMP_SPAWN_EGG.get());
                        output.accept(FintyItems.FEATHERBACK_SPAWN_EGG.get());
                        output.accept(FintyItems.FRESHWATER_SHARK_SPAWN_EGG.get());
                        output.accept(FintyItems.GOURAMI_SPAWN_EGG.get());
                        output.accept(FintyItems.GUPPY_SPAWN_EGG.get());
                        output.accept(FintyItems.MINNOW_SPAWN_EGG.get());
                        output.accept(FintyItems.MOONY_SPAWN_EGG.get());
                        output.accept(FintyItems.PLECO_SPAWN_EGG.get());

                        output.accept(FintyBlocks.AMAZON_SWORD.get());
                        output.accept(FintyBlocks.TALL_AMAZON_SWORD.get());
                        output.accept(FintyBlocks.ANUBIAS.get());
                        output.accept(FintyBlocks.BLUE_HYPNEA.get());
                        output.accept(FintyBlocks.CAULERPA.get());
                        output.accept(FintyBlocks.DRAGONS_BREATH_ALGAE.get());
                        output.accept(FintyBlocks.DUCKWEED.get());
                        output.accept(FintyBlocks.HORNWORT.get());
                        output.accept(FintyBlocks.LOTUS.get());
                        output.accept(FintyBlocks.LOTUS_PAD.get());
                        output.accept(FintyBlocks.LOTUS_FLOWER.get());
                        output.accept(FintyBlocks.MERMAID_FAN.get());
                        output.accept(FintyBlocks.SEA_GRAPES.get());
                        output.accept(FintyItems.SEA_GRAPE_SALAD.get());

                        output.accept(FintyBlocks.AQUATIC_MOSS_PHYLLID.get());
                        output.accept(FintyBlocks.AQUATIC_MOSS_CARPET.get());
                        output.accept(FintyBlocks.AQUATIC_MOSS_BLOCK.get());

                        output.accept(FintyBlocks.GREEN_ALGAE_CARPET.get());
                        output.accept(FintyBlocks.GREEN_ALGAE_BLOCK.get());

                        output.accept(FintyBlocks.RED_ALGAE.get());
                        output.accept(FintyItems.RED_ALGAE_FAN.get());
                        output.accept(FintyBlocks.RED_ALGAE_CARPET.get());
                        output.accept(FintyBlocks.RED_ALGAE_BLOCK.get());

                        output.accept(FintyBlocks.GREEN_ALGAE_LIVE_ROCK.get());
                        output.accept(FintyBlocks.LIVE_ROCK.get());
                        output.accept(FintyBlocks.DEAD_LIVE_ROCK.get());

                        output.accept(FintyBlocks.RED_ALGAE_LIVE_ROCK.get());
                        output.accept(FintyBlocks.POROUS_LIVE_ROCK.get());
                        output.accept(FintyBlocks.DEAD_POROUS_LIVE_ROCK.get());

                        output.accept(FintyBlocks.STROMATOLITE.get());
                        output.accept(FintyBlocks.STROMATOLITE_GROWTHS.get());
                        output.accept(FintyBlocks.STROMATOLITE_BLOCK.get());
                        output.accept(FintyBlocks.CUT_STROMATOLITE_BLOCK.get());

                        output.accept(FintyBlocks.FOSSIL_STROMATOLITE.get());
                        output.accept(FintyBlocks.FOSSIL_STROMATOLITE_GROWTHS.get());
                        output.accept(FintyBlocks.FOSSIL_STROMATOLITE_BLOCK.get());

                        output.accept(FintyBlocks.STROMATOLITE_BRICKS.get());
                        output.accept(FintyBlocks.STROMATOLITE_BRICKS_SLAB.get());
                        output.accept(FintyBlocks.STROMATOLITE_BRICKS_STAIRS.get());
                        output.accept(FintyBlocks.STROMATOLITE_BRICKS_WALL.get());

                        output.accept(FintyBlocks.AQUARIUM_GLASS.get());
                        output.accept(FintyBlocks.AQUARIUM_GLASS_PANE.get());
                        output.accept(FintyBlocks.TINTED_AQUARIUM_GLASS.get());

                        output.accept(FintyBlocks.CLEAR_AQUARIUM_GLASS.get());
                        output.accept(FintyBlocks.CLEAR_AQUARIUM_GLASS_PANE.get());

                        output.accept(FintyBlocks.INFERNAL_AQUARIUM_GLASS.get());
                        output.accept(FintyBlocks.INFERNAL_AQUARIUM_GLASS_PANE.get());
                        output.accept(FintyBlocks.TINTED_INFERNAL_AQUARIUM_GLASS.get());

                        output.accept(FintyBlocks.RADON_AQUARIUM_GLASS.get());
                        output.accept(FintyBlocks.RADON_AQUARIUM_GLASS_PANE.get());
                        output.accept(FintyBlocks.TINTED_RADON_AQUARIUM_GLASS.get());

                        output.accept(FintyBlocks.SUGAR_AQUARIUM_GLASS.get());
                        output.accept(FintyBlocks.SUGAR_AQUARIUM_GLASS_PANE.get());
                        output.accept(FintyBlocks.TINTED_SUGAR_AQUARIUM_GLASS.get());

                        output.accept(FintyItems.FRESH_MUSIC_DISC.get());
                        output.accept(FintyItems.SALTY_MUSIC_DISC.get());
                        output.accept(FintyItems.AXOLOTL_MUSIC_DISC.get());
                        output.accept(FintyItems.DRAGONFISH_MUSIC_DISC.get());
                        output.accept(FintyItems.SHUNJI_MUSIC_DISC.get());

                    })
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
