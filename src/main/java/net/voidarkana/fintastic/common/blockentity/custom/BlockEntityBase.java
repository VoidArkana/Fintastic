package net.voidarkana.fintastic.common.blockentity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.voidarkana.fintastic.util.network.FintyMessages;
import net.voidarkana.fintastic.util.network.messages.TESyncPacket;
import org.jetbrains.annotations.NotNull;

public class BlockEntityBase extends BlockEntity {
    public BlockEntityBase(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public void onDestroyed(BlockState state, BlockPos pos) {
        if (level != null) level.invalidateCapabilities(pos);
    }

    public InteractionResult onActivated(BlockState state, BlockPos pos, Player player, InteractionHand hand) {
        return InteractionResult.PASS;
    }

    public void sync() {
        setChanged();
        if (level == null) return;
        if (level instanceof ServerLevel serverLevel) {
            CompoundTag tag = new CompoundTag();
            saveAdditional(tag, level.registryAccess());
            FintyMessages.sendToPlayersTrackingChunk(serverLevel, worldPosition, new TESyncPacket(worldPosition, tag));
        }
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this, BlockEntity::getUpdateTag);
    }

    @Override
    public void onDataPacket(@NotNull Connection net, @NotNull ClientboundBlockEntityDataPacket pkt, HolderLookup.@NotNull Provider registries) {
        super.onDataPacket(net, pkt, registries);
        handleUpdateTag(pkt.getTag(), registries);
    }
}
