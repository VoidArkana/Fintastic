package net.voidarkana.fintastic.common.event;


import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.entity.YAFMEntities;
import net.voidarkana.fintastic.common.entity.custom.*;

@Mod.EventBusSubscriber(modid = Fintastic.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class YAFMEvents {

    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event){
        event.put(YAFMEntities.FEATHERBACK.get(), FeatherbackEntity.createAttributes().build());
        event.put(YAFMEntities.MINNOW.get(), MinnowEntity.createAttributes().build());
        event.put(YAFMEntities.CATFISH.get(), CatfishEntity.createAttributes().build());
        event.put(YAFMEntities.GUPPY.get(), GuppyEntity.createAttributes().build());
        event.put(YAFMEntities.FRESHWATER_SHARK.get(), FreshwaterSharkEntity.createAttributes().build());
        event.put(YAFMEntities.PLECO.get(), PlecoEntity.createAttributes().build());
        event.put(YAFMEntities.ARAPAIMA.get(), ArapaimaEntity.createAttributes().build());

        event.put(YAFMEntities.ARTEMIA.get(), ArtemiaEntity.createAttributes().build());
        event.put(YAFMEntities.DAPHNIA.get(), DaphniaEntity.createAttributes().build());

        event.put(YAFMEntities.MOONY.get(), Moony.createAttributes().build());
        event.put(YAFMEntities.COELACANTH.get(), Coelacanth.createAttributes().build());

        event.put(YAFMEntities.GOURAMI.get(), Gourami.createAttributes().build());
    }

}
