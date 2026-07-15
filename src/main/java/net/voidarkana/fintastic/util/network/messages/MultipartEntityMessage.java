package net.voidarkana.fintastic.util.network.messages;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.voidarkana.fintastic.Fintastic;

public record MultipartEntityMessage(int parentId, int playerId, int actionType, double damage) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MultipartEntityMessage> TYPE =
            new CustomPacketPayload.Type<>(Fintastic.location("multipart_entity"));

    public static final StreamCodec<FriendlyByteBuf, MultipartEntityMessage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, MultipartEntityMessage::parentId,
            ByteBufCodecs.VAR_INT, MultipartEntityMessage::playerId,
            ByteBufCodecs.VAR_INT, MultipartEntityMessage::actionType,
            ByteBufCodecs.DOUBLE, MultipartEntityMessage::damage,
            MultipartEntityMessage::new);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final MultipartEntityMessage message, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Player playerSided = context.player();
            Entity parent = playerSided.level().getEntity(message.parentId());
            Entity interacter = playerSided.level().getEntity(message.playerId());
            if (interacter != null && parent != null && parent.isMultipartEntity() && interacter.distanceTo(parent) < 16) {
                if (message.actionType() == 0) {
                    if (interacter instanceof Player player) {
                        parent.interact(player, player.getUsedItemHand());
                    }
                } else if (message.actionType() == 1) {
                    parent.hurt(parent.damageSources().generic(), (float) message.damage());
                }
            }
        });
    }
}
