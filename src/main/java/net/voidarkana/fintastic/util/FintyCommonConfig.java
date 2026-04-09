package net.voidarkana.fintastic.util;

import net.minecraftforge.common.ForgeConfigSpec;

public class FintyCommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> REPLACE_VANILLA_COD;
    public static final ForgeConfigSpec.ConfigValue<Boolean> REPLACE_VANILLA_SALMON;
    static {
        BUILDER.push("Configs for Fintastic");

        REPLACE_VANILLA_COD = BUILDER.comment("Defines if vanilla Cod is removed from the biome spawns. turned on by default")
                .define("Replace Vanilla Cod?", true);
        REPLACE_VANILLA_SALMON = BUILDER.comment("Defines if vanilla Salmon is removed from the biome spawns. turned on by default")
                .define("Replace Vanilla Salmon?", true);

        BUILDER.pop();
        SPEC= BUILDER.build();
    }
}
