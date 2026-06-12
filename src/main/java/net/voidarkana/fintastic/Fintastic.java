package net.voidarkana.fintastic;

import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.block.ComposterBlock;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.voidarkana.fintastic.common.block.FintyBlocks;
import net.voidarkana.fintastic.common.blockentity.FintyBlockEntities;
import net.voidarkana.fintastic.common.entity.FintyEntities;
import net.voidarkana.fintastic.common.entity.FintyEntityPlacements;
import net.voidarkana.fintastic.common.entity.custom.base.VariantSchoolingFish;
import net.voidarkana.fintastic.common.entity.villager.FintyVillagerProfessions;
import net.voidarkana.fintastic.common.event.FintyEvents;
import net.voidarkana.fintastic.common.item.FintyItems;
import net.voidarkana.fintastic.common.loot.FintyLootModifiers;
import net.voidarkana.fintastic.common.sound.FintySounds;
import net.voidarkana.fintastic.common.worldgen.FintyConfiguredFeatures;
import net.voidarkana.fintastic.util.FintyCommonConfig;
import net.voidarkana.fintastic.util.network.FintyMessages;
import net.voidarkana.fintastic.util.ClientProxy;
import net.voidarkana.fintastic.util.CommonProxy;
import net.voidarkana.fintastic.util.FintyCreativeTab;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


@Mod(Fintastic.MOD_ID)
public class Fintastic
{
    public static final CommonProxy PROXY = unsafeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public static final String MOD_ID = "fintastic";
    public static final List<Runnable> CALLBACKS = new ArrayList<>();
    private static final Logger LOGGER = LogUtils.getLogger();

    public Fintastic()
    {
        IEventBus modEventBus = ModLoadingContext.get().getActiveContainer().getEventBus();

        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, FintyCommonConfig.SPEC,
                "fintastic.toml");

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        FintyCreativeTab.register(modEventBus);

        FintyEntities.register(modEventBus);
        FintyVillagerProfessions.register(modEventBus);
        FintySounds.register(modEventBus);
        FintyItems.register(modEventBus);
        FintyBlocks.register(modEventBus);
        FintyBlocks.registerPaintings(modEventBus);
        FintyBlockEntities.register(modEventBus);
        FintyLootModifiers.register(modEventBus);

        FintyConfiguredFeatures.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.register(new FintyEvents());
        NeoForge.EVENT_BUS.addListener(this::addEntityGoals);

        PROXY.init();

    }

    private void commonSetup(final FMLCommonSetupEvent event) {

        FintyMessages.register();

        event.enqueueWork(()->{
            FintyEntityPlacements.entityPlacement();
            ComposterBlock.COMPOSTABLES.put(FintyBlocks.DUCKWEED.get().asItem(), 0.4F);
            ComposterBlock.COMPOSTABLES.put(FintyBlocks.HORNWORT.get().asItem(), 0.4F);
            ComposterBlock.COMPOSTABLES.put(FintyBlocks.ANUBIAS.get().asItem(), 0.4F);
            ComposterBlock.COMPOSTABLES.put(FintyBlocks.GREEN_ALGAE_BLOCK.get().asItem(), 0.4F);
            ComposterBlock.COMPOSTABLES.put(FintyBlocks.GREEN_ALGAE_CARPET.get().asItem(), 0.4F);
            ComposterBlock.COMPOSTABLES.put(FintyBlocks.RED_ALGAE.get().asItem(), 0.4F);
            ComposterBlock.COMPOSTABLES.put(FintyBlocks.RED_ALGAE_BLOCK.get().asItem(), 0.4F);
            ComposterBlock.COMPOSTABLES.put(FintyBlocks.RED_ALGAE_CARPET.get().asItem(), 0.4F);
            ComposterBlock.COMPOSTABLES.put(FintyBlocks.RED_ALGAE_FAN.get().asItem(), 0.4F);
            ComposterBlock.COMPOSTABLES.put(FintyBlocks.CAULERPA.get().asItem(), 0.4F);
            ComposterBlock.COMPOSTABLES.put(FintyBlocks.SEA_GRAPES.get().asItem(), 0.4F);
            ComposterBlock.COMPOSTABLES.put(FintyBlocks.BLUE_HYPNEA.get().asItem(), 0.4F);
            ComposterBlock.COMPOSTABLES.put(FintyBlocks.DRAGONS_BREATH_ALGAE.get().asItem(), 0.4F);
            ComposterBlock.COMPOSTABLES.put(FintyBlocks.AQUATIC_MOSS_BLOCK.get().asItem(), 0.4F);
            ComposterBlock.COMPOSTABLES.put(FintyBlocks.AQUATIC_MOSS_PHYLLID.get().asItem(), 0.4F);
            ComposterBlock.COMPOSTABLES.put(FintyBlocks.AQUATIC_MOSS_CARPET.get().asItem(), 0.4F);
            ComposterBlock.COMPOSTABLES.put(FintyBlocks.LOTUS.get().asItem(), 0.4F);
            ComposterBlock.COMPOSTABLES.put(FintyBlocks.LOTUS_FLOWER.get().asItem(), 0.4F);
            ComposterBlock.COMPOSTABLES.put(FintyBlocks.LOTUS_PAD.get().asItem(), 0.4F);
        });
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> PROXY.clientInit());
    }

    private static final Predicate<LivingEntity> FISH_PREY = (p_289448_) -> {
        EntityType<?> entitytype = p_289448_.getType();
        return entitytype == FintyEntities.COD.get() || entitytype == FintyEntities.SALMON.get();
    };

    private void addEntityGoals(EntityJoinLevelEvent e) {
        if (e.getEntity() instanceof Fox fox) {
            fox.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(fox, VariantSchoolingFish.class, false, FISH_PREY));
        }
    }

    private static <T> T unsafeRunForDist(java.util.function.Supplier<java.util.function.Supplier<T>> clientTarget, java.util.function.Supplier<java.util.function.Supplier<T>> serverTarget) {
        return switch (FMLEnvironment.dist) {
            case CLIENT -> clientTarget.get().get();
            case DEDICATED_SERVER -> serverTarget.get().get();
        };
    }
}
