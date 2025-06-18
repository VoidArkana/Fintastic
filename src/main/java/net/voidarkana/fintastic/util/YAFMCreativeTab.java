package net.voidarkana.fintastic.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.block.YAFMBlocks;
import net.voidarkana.fintastic.common.item.YAFMItems;

public class YAFMCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Fintastic.MOD_ID);

    public static final RegistryObject<CreativeModeTab> YAFM_CREATIVE_TAB =
            CREATIVE_MODE_TABS.register("fintastic_creative_tab", ()-> CreativeModeTab.builder().icon(() -> new ItemStack(YAFMItems.FEATHERBACK_BUCKET.get()))
                    .title(Component.translatable("creativetab.fintastic_creative_tab"))
                    .displayItems((itemDisplayParameters, output) -> {

                        output.accept(YAFMItems.RAW_FISH.get());
                        output.accept(YAFMItems.COOKED_FISH.get());

                        output.accept(YAFMItems.BAD_FEED.get());
                        output.accept(YAFMItems.REGULAR_FEED.get());
                        output.accept(YAFMItems.QUALITY_FEED.get());
                        output.accept(YAFMItems.GREAT_FEED.get());
                        output.accept(YAFMItems.PREMIUM_FEED.get());

                        output.accept(YAFMItems.FISHNET.get());

                        output.accept(YAFMItems.ARAPAIMA_BUCKET.get());
                        output.accept(YAFMItems.ARTEMIA_BUCKET.get());
                        output.accept(YAFMItems.CATFISH_BUCKET.get());
                        output.accept(YAFMItems.DAPHNIA_BUCKET.get());
                        output.accept(YAFMItems.FEATHERBACK_BUCKET.get());
                        output.accept(YAFMItems.FRESHWATER_SHARK_BUCKET.get());
                        output.accept(YAFMItems.GUPPY_BUCKET.get());
                        output.accept(YAFMItems.MINNOW_BUCKET.get());
                        output.accept(YAFMItems.MOONY_BUCKET.get());
                        output.accept(YAFMItems.PLECO_BUCKET.get());

                        output.accept(YAFMItems.ARAPAIMA_SPAWN_EGG.get());
                        output.accept(YAFMItems.ARTEMIA_SPAWN_EGG.get());
                        output.accept(YAFMItems.CATFISH_SPAWN_EGG.get());
                        output.accept(YAFMItems.DAPHNIA_SPAWN_EGG.get());
                        output.accept(YAFMItems.FEATHERBACK_SPAWN_EGG.get());
                        output.accept(YAFMItems.FRESHWATER_SHARK_SPAWN_EGG.get());
                        output.accept(YAFMItems.GUPPY_SPAWN_EGG.get());
                        output.accept(YAFMItems.MINNOW_SPAWN_EGG.get());
                        output.accept(YAFMItems.MOONY_SPAWN_EGG.get());
                        output.accept(YAFMItems.PLECO_SPAWN_EGG.get());

                        output.accept(YAFMBlocks.DUCKWEED.get());
                        output.accept(YAFMBlocks.HORNWORT.get());
                        output.accept(YAFMBlocks.ANUBIAS.get());

                        output.accept(YAFMBlocks.GREEN_ALGAE_CARPET.get());
                        output.accept(YAFMBlocks.GREEN_ALGAE_BLOCK.get());

                        output.accept(YAFMBlocks.RED_ALGAE.get());
                        output.accept(YAFMItems.RED_ALGAE_FAN.get());
                        output.accept(YAFMBlocks.RED_ALGAE_CARPET.get());
                        output.accept(YAFMBlocks.RED_ALGAE_BLOCK.get());

                        output.accept(YAFMBlocks.LIVE_ROCK.get());
                        output.accept(YAFMBlocks.POROUS_LIVE_ROCK.get());
                        output.accept(YAFMBlocks.DEAD_LIVE_ROCK.get());
                        output.accept(YAFMBlocks.DEAD_POROUS_LIVE_ROCK.get());

                        output.accept(YAFMBlocks.STROMATOLITE.get());
                        output.accept(YAFMBlocks.STROMATOLITE_GROWTHS.get());
                        output.accept(YAFMBlocks.STROMATOLITE_BLOCK.get());

                        output.accept(YAFMBlocks.FOSSIL_STROMATOLITE.get());
                        output.accept(YAFMBlocks.FOSSIL_STROMATOLITE_GROWTHS.get());
                        output.accept(YAFMBlocks.FOSSIL_STROMATOLITE_BLOCK.get());

                        output.accept(YAFMBlocks.STROMATOLITE_BRICKS.get());
                        output.accept(YAFMBlocks.STROMATOLITE_BRICKS_SLAB.get());
                        output.accept(YAFMBlocks.STROMATOLITE_BRICKS_STAIRS.get());
                        output.accept(YAFMBlocks.STROMATOLITE_BRICKS_WALL.get());

                        output.accept(YAFMBlocks.AQUARIUM_GLASS.get());
                        output.accept(YAFMBlocks.AQUARIUM_GLASS_PANE.get());
                        output.accept(YAFMBlocks.TINTED_AQUARIUM_GLASS.get());

                        output.accept(YAFMBlocks.CLEAR_AQUARIUM_GLASS.get());
                        output.accept(YAFMBlocks.CLEAR_AQUARIUM_GLASS_PANE.get());

                        output.accept(YAFMBlocks.INFERNAL_AQUARIUM_GLASS.get());
                        output.accept(YAFMBlocks.INFERNAL_AQUARIUM_GLASS_PANE.get());
                        output.accept(YAFMBlocks.TINTED_INFERNAL_AQUARIUM_GLASS.get());

                        output.accept(YAFMBlocks.RADON_AQUARIUM_GLASS.get());
                        output.accept(YAFMBlocks.RADON_AQUARIUM_GLASS_PANE.get());
                        output.accept(YAFMBlocks.TINTED_RADON_AQUARIUM_GLASS.get());

                        output.accept(YAFMBlocks.SUGAR_AQUARIUM_GLASS.get());
                        output.accept(YAFMBlocks.SUGAR_AQUARIUM_GLASS_PANE.get());
                        output.accept(YAFMBlocks.TINTED_SUGAR_AQUARIUM_GLASS.get());

                        output.accept(YAFMItems.FRESH_MUSIC_DISC.get());
                        output.accept(YAFMItems.SALTY_MUSIC_DISC.get());
                        output.accept(YAFMItems.AXOLOTL_MUSIC_DISC.get());
                        output.accept(YAFMItems.DRAGONFISH_MUSIC_DISC.get());
                        output.accept(YAFMItems.SHUNJI_MUSIC_DISC.get());

                        output.accept(YAFMItems.FISHING_HAT.get());
                    })
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
