package net.voidarkana.fintastic.util;

import net.minecraftforge.common.ForgeConfigSpec;

public class FintyCommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> REPLACE_VANILLA_COD;
    public static final ForgeConfigSpec.ConfigValue<Boolean> REPLACE_VANILLA_SALMON;
    public static final ForgeConfigSpec.ConfigValue<Boolean> ALLOW_FINTASTIC_COD;
    public static final ForgeConfigSpec.ConfigValue<Boolean> ALLOW_FINTASTIC_SALMON;
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

        BUILDER.pop();
        SPEC= BUILDER.build();
    }
}
