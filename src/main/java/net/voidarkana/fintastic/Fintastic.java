package net.voidarkana.fintastic;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.voidarkana.fintastic.common.block.YAFMBlocks;
import net.voidarkana.fintastic.common.entity.YAFMEntities;
import net.voidarkana.fintastic.common.entity.YAFMEntityPlacements;
import net.voidarkana.fintastic.common.event.YAFMEvents;
import net.voidarkana.fintastic.common.item.YAFMItems;
import net.voidarkana.fintastic.common.loot.YAFMLootModifiers;
import net.voidarkana.fintastic.common.sound.YAFMSounds;
import net.voidarkana.fintastic.common.worldgen.YAFMConfiguredFeatures;
import net.voidarkana.fintastic.server.MessageHurtMultipart;
import net.voidarkana.fintastic.server.MultipartEntityMessage;
import net.voidarkana.fintastic.util.ClientProxy;
import net.voidarkana.fintastic.util.CommonProxy;
import net.voidarkana.fintastic.util.YAFMCreativeTab;
import org.slf4j.Logger;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.ArrayList;
import java.util.List;


@Mod(Fintastic.MOD_ID)
public class Fintastic
{
    public static final SimpleChannel NETWORK_WRAPPER;
    private static final String PROTOCOL_VERSION = Integer.toString(1);
    private static int packetsRegistered;

    public static final CommonProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    static {
        NetworkRegistry.ChannelBuilder channel = NetworkRegistry.ChannelBuilder.named(new ResourceLocation("fintastic", "main_channel"));
        String version = PROTOCOL_VERSION;
        version.getClass();
        channel = channel.clientAcceptedVersions(version::equals);
        version = PROTOCOL_VERSION;
        version.getClass();
        NETWORK_WRAPPER = channel.serverAcceptedVersions(version::equals).networkProtocolVersion(() -> {
            return PROTOCOL_VERSION;
        }).simpleChannel();
    }

    public static final String MOD_ID = "fintastic";
    public static final List<Runnable> CALLBACKS = new ArrayList<>();
    private static final Logger LOGGER = LogUtils.getLogger();

    public Fintastic()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        YAFMCreativeTab.register(modEventBus);

        YAFMEntities.register(modEventBus);
        YAFMSounds.register(modEventBus);
        YAFMItems.register(modEventBus);
        YAFMBlocks.register(modEventBus);
        YAFMLootModifiers.register(modEventBus);

        YAFMConfiguredFeatures.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new YAFMEvents());

        PROXY.init();

    }

    private void commonSetup(final FMLCommonSetupEvent event) {

        event.enqueueWork(()->{
            YAFMEntityPlacements.entityPlacement();

            NETWORK_WRAPPER.registerMessage(packetsRegistered++, MessageHurtMultipart.class, MessageHurtMultipart::write, MessageHurtMultipart::read, MessageHurtMultipart.Handler::handle);
            NETWORK_WRAPPER.registerMessage(packetsRegistered++, MultipartEntityMessage.class, MultipartEntityMessage::write, MultipartEntityMessage::read, MultipartEntityMessage::handle);


            ComposterBlock.COMPOSTABLES.put(YAFMBlocks.DUCKWEED.get().asItem(), 0.4F);
            ComposterBlock.COMPOSTABLES.put(YAFMBlocks.HORNWORT.get().asItem(), 0.4F);
            ComposterBlock.COMPOSTABLES.put(YAFMBlocks.ANUBIAS.get().asItem(), 0.4F);
        });
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> PROXY.clientInit());
    }

    public static <MSG> void sendMSGToServer(MSG message) {
        NETWORK_WRAPPER.sendToServer(message);
    }

//    public static <MSG> void sendMSGToAll(MSG message) {
//        for (ServerPlayer player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
//            sendNonLocal(message, player);
//        }
//    }
//
//    public static <MSG> void sendNonLocal(MSG msg, ServerPlayer player) {
//        NETWORK_WRAPPER.sendTo(msg, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
//    }

}
