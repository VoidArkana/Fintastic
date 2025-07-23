package net.voidarkana.fintastic.common.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.entity.custom.*;

public class YAFMEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Fintastic.MOD_ID);

    public static final RegistryObject<EntityType<FeatherbackEntity>> FEATHERBACK =
            ENTITY_TYPES.register("featherback",
                    () -> EntityType.Builder.of(FeatherbackEntity::new, MobCategory.WATER_AMBIENT)
                            .sized(0.7f, 0.9f)
                            .build(new ResourceLocation(Fintastic.MOD_ID, "featherback").toString()));

    public static final RegistryObject<EntityType<MinnowEntity>> MINNOW =
            ENTITY_TYPES.register("minnow",
                    () -> EntityType.Builder.of(MinnowEntity::new, MobCategory.WATER_AMBIENT)
                            .sized(0.4f, 0.4f)
                            .build(new ResourceLocation(Fintastic.MOD_ID, "minnow").toString()));

    public static final RegistryObject<EntityType<CatfishEntity>> CATFISH =
            ENTITY_TYPES.register("catfish",
                    () -> EntityType.Builder.of(CatfishEntity::new, MobCategory.WATER_AMBIENT)
                            .sized(1f, 1f)
                            .build(new ResourceLocation(Fintastic.MOD_ID, "catfish").toString()));

    public static final RegistryObject<EntityType<GuppyEntity>> GUPPY =
            ENTITY_TYPES.register("guppy",
                    () -> EntityType.Builder.of(GuppyEntity::new, MobCategory.WATER_AMBIENT)
                            .sized(0.3f, 0.3f)
                            .build(new ResourceLocation(Fintastic.MOD_ID, "guppy").toString()));

    public static final RegistryObject<EntityType<FreshwaterSharkEntity>> FRESHWATER_SHARK =
            ENTITY_TYPES.register("freshwater_shark",
                    () -> EntityType.Builder.of(FreshwaterSharkEntity::new, MobCategory.WATER_AMBIENT)
                            .sized(0.6f, 0.4f)
                            .build(new ResourceLocation(Fintastic.MOD_ID, "freshwater_shark").toString()));

    public static final RegistryObject<EntityType<PlecoEntity>> PLECO =
            ENTITY_TYPES.register("pleco",
                    () -> EntityType.Builder.of(PlecoEntity::new, MobCategory.WATER_AMBIENT)
                            .sized(1f, 0.5f)
                            .build(new ResourceLocation(Fintastic.MOD_ID, "pleco").toString()));

    public static final RegistryObject<EntityType<ArapaimaEntity>> ARAPAIMA =
            ENTITY_TYPES.register("arapaima",
                    () -> EntityType.Builder.of(ArapaimaEntity::new, MobCategory.WATER_AMBIENT)
                            .sized(1.5f, 0.8f)
                            .build(new ResourceLocation(Fintastic.MOD_ID, "arapaima").toString()));

    public static final RegistryObject<EntityType<ArtemiaEntity>> ARTEMIA =
            ENTITY_TYPES.register("artemia",
                    () -> EntityType.Builder.of(ArtemiaEntity::new, MobCategory.WATER_AMBIENT)
                            .sized(0.8f, 0.4f)
                            .build(new ResourceLocation(Fintastic.MOD_ID, "artemia").toString()));

    public static final RegistryObject<EntityType<DaphniaEntity>> DAPHNIA =
            ENTITY_TYPES.register("daphnia",
                    () -> EntityType.Builder.of(DaphniaEntity::new, MobCategory.WATER_AMBIENT)
                            .sized(0.8f, 0.8f)
                            .build(new ResourceLocation(Fintastic.MOD_ID, "daphnia").toString()));



    public static final RegistryObject<EntityType<Moony>> MOONY =
            ENTITY_TYPES.register("moony",
                    () -> EntityType.Builder.of(Moony::new, MobCategory.WATER_AMBIENT)
                            .sized(0.3f, 0.5f)
                            .build(new ResourceLocation(Fintastic.MOD_ID, "moony").toString()));

    public static final RegistryObject<EntityType<Coelacanth>> COELACANTH =
            ENTITY_TYPES.register("coelacanth",
                    () -> EntityType.Builder.of(Coelacanth::new, MobCategory.WATER_AMBIENT)
                            .sized(1.5f, 1f)
                            .build(new ResourceLocation(Fintastic.MOD_ID, "coelacanth").toString()));

    public static final RegistryObject<EntityType<Gourami>> GOURAMI =
            ENTITY_TYPES.register("gourami",
                    () -> EntityType.Builder.of(Gourami::new, MobCategory.WATER_AMBIENT)
                            .sized(0.8f, 0.5f)
                            .build(new ResourceLocation(Fintastic.MOD_ID, "gourami").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
