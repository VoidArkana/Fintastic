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

    public static final ForgeConfigSpec.ConfigValue<Integer> PLAINS_VILLAGE_WEIGHT;
    public static final ForgeConfigSpec.ConfigValue<Integer> SAVANNA_VILLAGE_WEIGHT;
    public static final ForgeConfigSpec.ConfigValue<Integer> SNOWY_VILLAGE_WEIGHT;
    public static final ForgeConfigSpec.ConfigValue<Integer> TAIGA_VILLAGE_WEIGHT;
    public static final ForgeConfigSpec.ConfigValue<Integer> DESERT_VILLAGE_WEIGHT;
    public static final ForgeConfigSpec.ConfigValue<Integer> HOUNEN_FAIRY_SHRIMP_BONEMEAL_RATE;
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

        PLAINS_VILLAGE_WEIGHT = BUILDER.comment("Defines the weight of aquarium shops in plains villages. The higher the value the more common they become. " +
                        "Default value is 30 and the number must be 0 or higher.")
                .define("Plains aquarium shop weight:", 30);

        SAVANNA_VILLAGE_WEIGHT = BUILDER.comment("Defines the weight of aquarium shops in savanna villages. The higher the value the more common they become. " +
                        "Default value is 25 and the number must be 0 or higher.")
                .define("Savanna aquarium shop weight:", 25);

        TAIGA_VILLAGE_WEIGHT = BUILDER.comment("Defines the weight of aquarium shops in taiga villages. The higher the value the more common they become. " +
                        "Default value is 20 and the number must be 0 or higher.")
                .define("Taiga aquarium shop weight:", 20);

        SNOWY_VILLAGE_WEIGHT = BUILDER.comment("Defines the weight of aquarium shops in snowy villages. The higher the value the more common they become. " +
                        "Default value is 20 and the number must be 0 or higher.")
                .define("Snowy aquarium shop weight:", 20);

        DESERT_VILLAGE_WEIGHT = BUILDER.comment("Defines the weight of aquarium shops in desert villages. The higher the value the more common they become. " +
                        "Default value is 30 and the number must be 0 or higher.")
                .define("Desert aquarium shop weight:", 30);

        HOUNEN_FAIRY_SHRIMP_BONEMEAL_RATE = BUILDER.comment("Defines how often Hounen Fairy Shrimp will bonemeal plants. The value is once every X amount of ticks, resulting in lower values making bonemealing more often and higher values making bonemealing less often." +
                        "Default value is 250 and the number must be 0 or higher.")
                .define("Hounen Fairy Shrimp Bonemeal Rate:", 250);

        BUILDER.pop();
        SPEC= BUILDER.build();
    }
}
