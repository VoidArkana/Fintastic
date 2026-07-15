package net.voidarkana.fintastic.common.blockentity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.block.FintyBlocks;
import net.voidarkana.fintastic.common.blockentity.custom.FishbowlBlockEntity;

public class FintyBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Fintastic.MOD_ID);

    public static final RegistryObject<BlockEntityType<FishbowlBlockEntity>> FISHBOWL_ENTITY =
            BLOCK_ENTITIES.register("pedestal_entity", () ->
                    BlockEntityType.Builder.of(FishbowlBlockEntity::new,
                            FintyBlocks.FISHBOWL.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
