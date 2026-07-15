package net.voidarkana.fintastic.common.entity.villager;

import com.google.common.collect.ImmutableSet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.block.FintyBlocks;

public class FintyVillagerProfessions {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(Registries.POINT_OF_INTEREST_TYPE, Fintastic.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(Registries.VILLAGER_PROFESSION, Fintastic.MOD_ID);

    public static final DeferredHolder<PoiType, PoiType> FISHBOWL_POI = POI_TYPES.register("fishbowl_poi",
            () -> new PoiType(ImmutableSet.copyOf(FintyBlocks.FISHBOWL.get().getStateDefinition().getPossibleStates()),
                    1, 1));

    public static final DeferredHolder<VillagerProfession, VillagerProfession> AQUARIST =
            VILLAGER_PROFESSIONS.register("aquarist", () -> new VillagerProfession("aquarist",
                    holder -> holder.value() == FISHBOWL_POI.get(), holder -> holder.value() == FISHBOWL_POI.get(),
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_BUTCHER));



    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
