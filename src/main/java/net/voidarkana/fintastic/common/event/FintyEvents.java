package net.voidarkana.fintastic.common.event;


import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.entity.FintyEntities;
import net.voidarkana.fintastic.common.entity.custom.*;

@EventBusSubscriber(modid = Fintastic.MOD_ID)
public class FintyEvents {

    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event){
        event.put(FintyEntities.FEATHERBACK.get(), Featherback.createAttributes().build());
        event.put(FintyEntities.MINNOW.get(), Minnow.createAttributes().build());
        event.put(FintyEntities.CATFISH.get(), Catfish.createAttributes().build());
        event.put(FintyEntities.GUPPY.get(), Guppy.createAttributes().build());
        event.put(FintyEntities.SHARKMINNOW.get(), Sharkminnow.createAttributes().build());
        event.put(FintyEntities.PLECO.get(), Pleco.createAttributes().build());
        event.put(FintyEntities.ARAPAIMA.get(), Arapaima.createAttributes().build());

        event.put(FintyEntities.FAIRY_SHRIMP.get(), FairyShrimp.createAttributes().build());
        event.put(FintyEntities.DAPHNIA.get(), Daphnia.createAttributes().build());

        event.put(FintyEntities.MOONY.get(), Moony.createAttributes().build());
        event.put(FintyEntities.COELACANTH.get(), Coelacanth.createAttributes().build());
        event.put(FintyEntities.GOURAMI.get(), Gourami.createAttributes().build());

        event.put(FintyEntities.COPEPOD.get(), Copepod.createAttributes().build());
        event.put(FintyEntities.COD.get(), FintasticCod.createAttributes().build());
        event.put(FintyEntities.SALMON.get(), FintasticSalmon.createAttributes().build());
        event.put(FintyEntities.DWARF_FROG.get(), DwarfFrog.createAttributes().build());
        event.put(FintyEntities.SMALL_CATFISH.get(), SmallCatfish.createAttributes().build());
    }

}
