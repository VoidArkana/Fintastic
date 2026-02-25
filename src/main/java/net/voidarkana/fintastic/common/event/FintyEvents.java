package net.voidarkana.fintastic.common.event;


import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.entity.FintyEntities;
import net.voidarkana.fintastic.common.entity.custom.*;

@Mod.EventBusSubscriber(modid = Fintastic.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FintyEvents {

    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event){
        event.put(FintyEntities.FEATHERBACK.get(), FeatherbackEntity.createAttributes().build());
        event.put(FintyEntities.MINNOW.get(), MinnowEntity.createAttributes().build());
        event.put(FintyEntities.CATFISH.get(), CatfishEntity.createAttributes().build());
        event.put(FintyEntities.GUPPY.get(), GuppyEntity.createAttributes().build());
        event.put(FintyEntities.SHARKMINNOW.get(), Sharkminnow.createAttributes().build());
        event.put(FintyEntities.PLECO.get(), PlecoEntity.createAttributes().build());
        event.put(FintyEntities.ARAPAIMA.get(), ArapaimaEntity.createAttributes().build());

        event.put(FintyEntities.ARTEMIA.get(), ArtemiaEntity.createAttributes().build());
        event.put(FintyEntities.DAPHNIA.get(), DaphniaEntity.createAttributes().build());

        event.put(FintyEntities.MOONY.get(), Moony.createAttributes().build());
        event.put(FintyEntities.COELACANTH.get(), Coelacanth.createAttributes().build());

        event.put(FintyEntities.GOURAMI.get(), Gourami.createAttributes().build());
    }

}
