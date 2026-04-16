package net.voidarkana.fintastic.util;

import net.minecraftforge.common.ForgeConfigSpec;

public class FintyCommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> REPLACE_VANILLA_COD;
    public static final ForgeConfigSpec.ConfigValue<Boolean> REPLACE_VANILLA_SALMON;
    public static final ForgeConfigSpec.ConfigValue<Boolean> ALLOW_FINTASTIC_COD;
    public static final ForgeConfigSpec.ConfigValue<Boolean> ALLOW_FINTASTIC_SALMON;

    public static final ForgeConfigSpec.ConfigValue<Integer> LOTUS_PAD_FREQUENCY;
    public static final ForgeConfigSpec.ConfigValue<Float> LOTUS_PAD_LOUDNESS;
    static {
        BUILDER.push("Configs for Fintastic");

        REPLACE_VANILLA_COD = BUILDER.comment("Defines if vanilla Cod is removed from the biome spawns. turned on by default")
                .define("Replace Vanilla Cod?", true);
        REPLACE_VANILLA_SALMON = BUILDER.comment("Defines if vanilla Salmon is removed from the biome spawns. turned on by default")
                .define("Replace Vanilla Salmon?", true);

        ALLOW_FINTASTIC_COD = BUILDER.comment("Defines if Fintastic's revamped Cod is allowed to spawn. turned on by default")
                .define("Allow Fintastic Cod?", true);
        ALLOW_FINTASTIC_SALMON = BUILDER.comment("Defines if Fintastic's revamped Salmon is allowed to spawn. turned on by default")
                .define("Allow Fintastic Salmon?", true);

        LOTUS_PAD_FREQUENCY = BUILDER.comment("Defines the frequency that Lotus Pads will produce a sound. Lotus Pads will have this much change out of a 1000 to play a sound every tick. Number should not be above 1000.")
                .define("Lotus Pad Sound Frequency, This Many Times Every 1000 Ticks", 30);
        LOTUS_PAD_LOUDNESS = BUILDER.comment("Defines the volume of the sounds produced by Lotus Pads. Should be a floating number, ranging from 0 to 1.")
                .define("Lotus Pad Volume", 0.5f);

        BUILDER.pop();
        SPEC= BUILDER.build();
    }
}
