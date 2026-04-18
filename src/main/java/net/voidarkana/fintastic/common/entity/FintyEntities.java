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

public class FintyEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Fintastic.MOD_ID);

    public static final RegistryObject<EntityType<Featherback>> FEATHERBACK =
            ENTITY_TYPES.register("featherback",
                    () -> EntityType.Builder.of(Featherback::new, MobCategory.WATER_CREATURE)
                            .sized(0.7f, 0.9f)
                            .build(new ResourceLocation(Fintastic.MOD_ID, "featherback").toString()));

    public static final RegistryObject<EntityType<Minnow>> MINNOW =
            ENTITY_TYPES.register("minnow",
                    () -> EntityType.Builder.of(Minnow::new, MobCategory.WATER_AMBIENT)
                            .sized(0.4f, 0.4f)
                            .build(new ResourceLocation(Fintastic.MOD_ID, "minnow").toString()));

    public static final RegistryObject<EntityType<Catfish>> CATFISH =
            ENTITY_TYPES.register("catfish",
                    () -> EntityType.Builder.of(Catfish::new, MobCategory.WATER_CREATURE)
                            .sized(1f, 0.9f)
                            .build(new ResourceLocation(Fintastic.MOD_ID, "catfish").toString()));

    public static final RegistryObject<EntityType<Guppy>> GUPPY =
            ENTITY_TYPES.register("guppy",
                    () -> EntityType.Builder.of(Guppy::new, MobCategory.WATER_AMBIENT)
                            .sized(0.3f, 0.3f)
                            .build(new ResourceLocation(Fintastic.MOD_ID, "guppy").toString()));

    public static final RegistryObject<EntityType<Sharkminnow>> SHARKMINNOW =
            ENTITY_TYPES.register("freshwater_shark",
                    () -> EntityType.Builder.of(Sharkminnow::new, MobCategory.WATER_CREATURE)
                            .sized(0.6f, 0.4f)
                            .build(new ResourceLocation(Fintastic.MOD_ID, "freshwater_shark").toString()));

    public static final RegistryObject<EntityType<Pleco>> PLECO =
            ENTITY_TYPES.register("pleco",
                    () -> EntityType.Builder.of(Pleco::new, MobCategory.WATER_CREATURE)
                            .sized(1f, 0.5f)
                            .build(new ResourceLocation(Fintastic.MOD_ID, "pleco").toString()));

    public static final RegistryObject<EntityType<Arapaima>> ARAPAIMA =
            ENTITY_TYPES.register("arapaima",
                    () -> EntityType.Builder.of(Arapaima::new, MobCategory.WATER_CREATURE)
                            .sized(1.5f, 0.8f)
                            .build(new ResourceLocation(Fintastic.MOD_ID, "arapaima").toString()));

    public static final RegistryObject<EntityType<FairyShrimp>> FAIRY_SHRIMP =
            ENTITY_TYPES.register("artemia",
                    () -> EntityType.Builder.of(FairyShrimp::new, MobCategory.WATER_AMBIENT)
                            .sized(0.8f, 0.4f)
                            .build(new ResourceLocation(Fintastic.MOD_ID, "artemia").toString()));

    public static final RegistryObject<EntityType<Daphnia>> DAPHNIA =
            ENTITY_TYPES.register("daphnia",
                    () -> EntityType.Builder.of(Daphnia::new, MobCategory.WATER_CREATURE)
                            .sized(0.8f, 0.8f)
                            .build(new ResourceLocation(Fintastic.MOD_ID, "daphnia").toString()));


    //update 2.0
    public static final RegistryObject<EntityType<Moony>> MOONY =
            ENTITY_TYPES.register("moony",
                    () -> EntityType.Builder.of(Moony::new, MobCategory.WATER_AMBIENT)
                            .sized(0.3f, 0.5f)
                            .build(new ResourceLocation(Fintastic.MOD_ID, "moony").toString()));

    public static final RegistryObject<EntityType<Coelacanth>> COELACANTH =
            ENTITY_TYPES.register("coelacanth",
                    () -> EntityType.Builder.of(Coelacanth::new, MobCategory.WATER_CREATURE)
                            .sized(1.5f, 1f)
                            .build(new ResourceLocation(Fintastic.MOD_ID, "coelacanth").toString()));

    public static final RegistryObject<EntityType<Gourami>> GOURAMI =
            ENTITY_TYPES.register("gourami",
                    () -> EntityType.Builder.of(Gourami::new, MobCategory.WATER_AMBIENT)
                            .sized(0.6f, 0.4f)
                            .build(new ResourceLocation(Fintastic.MOD_ID, "gourami").toString()));

    //update 3.0
    public static final RegistryObject<EntityType<Copepod>> COPEPOD =
            ENTITY_TYPES.register("copepod",
                    () -> EntityType.Builder.of(Copepod::new, MobCategory.WATER_CREATURE)
                            .sized(0.8f, 0.4f)
                            .build(new ResourceLocation(Fintastic.MOD_ID, "copepod").toString()));

    public static final RegistryObject<EntityType<FintasticCod>> COD =
            ENTITY_TYPES.register("fintastic_cod",
                    () -> EntityType.Builder.of(FintasticCod::new, MobCategory.WATER_AMBIENT)
                            .sized(0.6f, 0.4f)
                            .build(new ResourceLocation(Fintastic.MOD_ID, "fintastic_cod").toString()));

    public static final RegistryObject<EntityType<FintasticSalmon>> SALMON =
            ENTITY_TYPES.register("fintastic_salmon",
                    () -> EntityType.Builder.of(FintasticSalmon::new, MobCategory.WATER_AMBIENT)
                            .sized(0.6f, 0.4f)
                            .build(new ResourceLocation(Fintastic.MOD_ID, "fintastic_salmon").toString()));

    public static final RegistryObject<EntityType<DwarfFrog>> DWARF_FROG =
            ENTITY_TYPES.register("dwarf_frog",
                    () -> EntityType.Builder.of(DwarfFrog::new, MobCategory.WATER_CREATURE)
                            .sized(0.4f, 0.4f)
                            .build(new ResourceLocation(Fintastic.MOD_ID, "dwarf_frog").toString()));

    public static final RegistryObject<EntityType<SmallCatfish>> SMALL_CATFISH =
            ENTITY_TYPES.register("small_catfish",
                    () -> EntityType.Builder.of(SmallCatfish::new, MobCategory.WATER_AMBIENT)
                            .sized(0.4f, 0.4f)
                            .build(new ResourceLocation(Fintastic.MOD_ID, "small_catfish").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
