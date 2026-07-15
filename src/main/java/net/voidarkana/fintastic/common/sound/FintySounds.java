package net.voidarkana.fintastic.common.sound;

import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.voidarkana.fintastic.Fintastic;

public class FintySounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, Fintastic.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> SALTY = registerSoundEvents("salty");
    public static final DeferredHolder<SoundEvent, SoundEvent> FRESH = registerSoundEvents("fresh");

    public static final DeferredHolder<SoundEvent, SoundEvent> AXOLOTL = registerSoundEvents("axolotl");
    public static final DeferredHolder<SoundEvent, SoundEvent> DRAGONFISH = registerSoundEvents("dragonfish");
    public static final DeferredHolder<SoundEvent, SoundEvent> SHUNJI = registerSoundEvents("shunji");

    public static final DeferredHolder<SoundEvent, SoundEvent> GOURAMI_CROAK = registerSoundEvents("gourami_croak");
    public static final DeferredHolder<SoundEvent, SoundEvent> DWARF_FROG_IDLE = registerSoundEvents("dwarf_frog_idle");

    public static final DeferredHolder<SoundEvent, SoundEvent> LOTUS_WATER = registerSoundEvents("lotus_water");
    public static final DeferredHolder<SoundEvent, SoundEvent> LOTUS_BUG = registerSoundEvents("lotus_bug");
    public static final DeferredHolder<SoundEvent, SoundEvent> LOTUS_FROG = registerSoundEvents("lotus_frog");
    public static final DeferredHolder<SoundEvent, SoundEvent> LOTUS_BUG_NIGHT = registerSoundEvents("lotus_bug_night");
    public static final DeferredHolder<SoundEvent, SoundEvent> LOTUS_FROG_NIGHT = registerSoundEvents("lotus_frog_night");

    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(Fintastic.location(name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
