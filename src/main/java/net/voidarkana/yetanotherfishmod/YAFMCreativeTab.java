package net.voidarkana.yetanotherfishmod;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.voidarkana.yetanotherfishmod.common.item.YAFMItems;

public class YAFMCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, YetAnotherFishMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> YAFM_CREATIVE_TAB =
            CREATIVE_MODE_TABS.register("yafm_creative_tab", ()-> CreativeModeTab.builder().icon(() -> new ItemStack(YAFMItems.FEATHERBACK_BUCKET.get()))
                    .title(Component.translatable("creativetab.yafm_creative_tab"))
                    .displayItems((itemDisplayParameters, output) -> {

                        output.accept(YAFMItems.BARB_BUCKET.get());
                        output.accept(YAFMItems.CATFISH_BUCKET.get());
                        output.accept(YAFMItems.FEATHERBACK_BUCKET.get());
                        output.accept(YAFMItems.GUPPY_BUCKET.get());

                        output.accept(YAFMItems.BARB_SPAWN_EGG.get());
                        output.accept(YAFMItems.CATFISH_SPAWN_EGG.get());
                        output.accept(YAFMItems.FEATHERBACK_SPAWN_EGG.get());
                        output.accept(YAFMItems.GUPPY_SPAWN_EGG.get());

                    })
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}