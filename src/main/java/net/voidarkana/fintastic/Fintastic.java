package net.voidarkana.fintastic;

import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.voidarkana.fintastic.common.block.FintyBlocks;
import net.voidarkana.fintastic.common.blockentity.FintyBlockEntities;
import net.voidarkana.fintastic.common.entity.FintyEntities;
import net.voidarkana.fintastic.common.entity.FintyEntityPlacements;
import net.voidarkana.fintastic.common.entity.villager.FintyVillagerProfessions;
import net.voidarkana.fintastic.common.event.FintyEvents;
import net.voidarkana.fintastic.common.item.FintyItems;
import net.voidarkana.fintastic.common.loot.FintyLootModifiers;
import net.voidarkana.fintastic.common.sound.FintySounds;
import net.voidarkana.fintastic.common.worldgen.FintyConfiguredFeatures;
import net.voidarkana.fintastic.util.network.FintyMessages;
import net.voidarkana.fintastic.util.ClientProxy;
import net.voidarkana.fintastic.util.CommonProxy;
import net.voidarkana.fintastic.util.FintyCreativeTab;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;


@Mod(Fintastic.MOD_ID)
public class Fintastic
{
    public static final CommonProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public static final String MOD_ID = "fintastic";
    public static final List<Runnable> CALLBACKS = new ArrayList<>();
    private static final Logger LOGGER = LogUtils.getLogger();

    public Fintastic()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        FintyCreativeTab.register(modEventBus);

        FintyEntities.register(modEventBus);
        FintyVillagerProfessions.register(modEventBus);
        FintySounds.register(modEventBus);
        FintyItems.register(modEventBus);
        FintyBlocks.register(modEventBus);
        FintyBlocks.registerPaintings(modEventBus);
        FintyBlockEntities.register(modEventBus);
        FintyLootModifiers.register(modEventBus);

        FintyConfiguredFeatures.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new FintyEvents());

        PROXY.init();

    }

    private void commonSetup(final FMLCommonSetupEvent event) {

        FintyMessages.register();

        event.enqueueWork(()->{
            FintyEntityPlacements.entityPlacement();
            ComposterBlock.COMPOSTABLES.put(FintyBlocks.DUCKWEED.get().asItem(), 0.4F);
            ComposterBlock.COMPOSTABLES.put(FintyBlocks.HORNWORT.get().asItem(), 0.4F);
            ComposterBlock.COMPOSTABLES.put(FintyBlocks.ANUBIAS.get().asItem(), 0.4F);
        });
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> PROXY.clientInit());
    }


}
