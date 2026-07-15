package net.voidarkana.fintastic.common.blockentity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.block.FintyBlocks;
import net.voidarkana.fintastic.common.blockentity.custom.FishbowlBlockEntity;

public class FintyBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Fintastic.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FishbowlBlockEntity>> FISHBOWL_ENTITY =
            BLOCK_ENTITIES.register("pedestal_entity", () ->
                    BlockEntityType.Builder.of(FishbowlBlockEntity::new,
                            FintyBlocks.FISHBOWL.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
