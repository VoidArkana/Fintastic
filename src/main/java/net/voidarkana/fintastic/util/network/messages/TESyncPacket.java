package net.voidarkana.fintastic.util.network.messages;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.voidarkana.fintastic.Fintastic;

public record TESyncPacket(BlockPos pos, CompoundTag tag) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<TESyncPacket> TYPE =
            new CustomPacketPayload.Type<>(Fintastic.location("te_sync"));

    public static final StreamCodec<FriendlyByteBuf, TESyncPacket> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, TESyncPacket::pos,
            ByteBufCodecs.COMPOUND_TAG, TESyncPacket::tag,
            TESyncPacket::new);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final TESyncPacket packet, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Level world = context.player().level();
            BlockEntity t = world.getBlockEntity(packet.pos());
            if (t != null) {
                t.loadWithComponents(packet.tag(), world.registryAccess());
                t.setChanged();
            }
        });
    }
}
