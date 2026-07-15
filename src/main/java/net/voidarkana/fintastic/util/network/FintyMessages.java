package net.voidarkana.fintastic.util.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.util.network.messages.MultipartEntityMessage;
import net.voidarkana.fintastic.util.network.messages.TESyncPacket;

@EventBusSubscriber(modid = Fintastic.MOD_ID)
public class FintyMessages {

    private static final String PROTOCOL_VERSION = "1";

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION);

        registrar.playToServer(MultipartEntityMessage.TYPE, MultipartEntityMessage.STREAM_CODEC,
                MultipartEntityMessage::handle);

        registrar.playToClient(TESyncPacket.TYPE, TESyncPacket.STREAM_CODEC,
                TESyncPacket::handle);
    }

    public static void sendToServer(CustomPacketPayload message) {
        PacketDistributor.sendToServer(message);
    }

    public static void sendToPlayer(CustomPacketPayload message, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, message);
    }

    public static void sendToClients(CustomPacketPayload message) {
        PacketDistributor.sendToAllPlayers(message);
    }

    public static void sendToPlayersTrackingChunk(ServerLevel level, BlockPos pos, CustomPacketPayload message) {
        PacketDistributor.sendToPlayersTrackingChunk(level, new ChunkPos(pos), message);
    }
}
