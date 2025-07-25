package net.voidarkana.fintastic.common.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.voidarkana.fintastic.Fintastic;

public class YAFMSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Fintastic.MOD_ID);

    public static final RegistryObject<SoundEvent> SALTY = registerSoundEvents("salty");
    public static final RegistryObject<SoundEvent> FRESH = registerSoundEvents("fresh");

    public static final RegistryObject<SoundEvent> AXOLOTL = registerSoundEvents("axolotl");
    public static final RegistryObject<SoundEvent> DRAGONFISH = registerSoundEvents("dragonfish");
    public static final RegistryObject<SoundEvent> SHUNJI = registerSoundEvents("shunji");

    public static final RegistryObject<SoundEvent> GOURAMI_CROAK = registerSoundEvents("gourami_croak");

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Fintastic.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
