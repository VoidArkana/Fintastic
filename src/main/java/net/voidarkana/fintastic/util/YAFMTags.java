package net.voidarkana.fintastic.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.voidarkana.fintastic.Fintastic;

public class YAFMTags {

    public static class Biomes {

        public static final TagKey<Biome> GUPPY_BIOMES = tag("guppy_biomes");
        public static final TagKey<Biome> ARAPAIMA_BIOMES = tag("arapaima_biomes");
        public static final TagKey<Biome> MINNOW_SURFACE_BIOMES = tag("minnow_surface_biomes");
        public static final TagKey<Biome> PLECO_BIOMES = tag("pleco_biomes");
        public static final TagKey<Biome> CATFISH_BIOMES = tag("catfish_biomes");
        public static final TagKey<Biome> FEATHERBACK_BIOMES = tag("featherback_biomes");
        public static final TagKey<Biome> FWSHARK_BIOMES = tag("fwshark_biomes");
        public static final TagKey<Biome> ARTEMIA_BIOMES = tag("artemia_biomes");
        public static final TagKey<Biome> DAPHNIA_BIOMES = tag("daphnia_biomes");

        public static final TagKey<Biome> MOONY_BIOMES = tag("moony_biomes");
        public static final TagKey<Biome> GOURAMI_BIOMES = tag("gourami_biomes");

        public static final TagKey<Biome> FRESHWATER_PLANT_BIOME_BLACKLIST = tag("non_freshwater_plant_biomes");

        public static final TagKey<Biome> LIVEROCK_BOULDER_BIOMES = tag("liverock_boulder_biomes");
        public static final TagKey<Biome> DUCKWEED_BIOMES = tag("duckweed_biomes");
        public static final TagKey<Biome> HORNWORT_BIOMES = tag("hornwort_biomes");
        public static final TagKey<Biome> STROMATOLITE_BIOMES = tag("stromatolite_biomes");
        public static final TagKey<Biome> ANUBIAS_BIOMES = tag("anubias_biomes");

        private static TagKey<Biome> tag(String pName) {
            return TagKey.create(Registries.BIOME, new ResourceLocation(Fintastic.MOD_ID, pName));
        }

    }

    public static class Items {

        public static final TagKey<Item> FISH_FEED = tag("fish_feed");
        public static final TagKey<Item> URANIUM = tag("uranium");
        public static final TagKey<Item> SUGAR_GLASS = tag("sugar_glass");

        private static TagKey<Item> tag(String name){
            return ItemTags.create(new ResourceLocation(Fintastic.MOD_ID, name));
        }
    }

    public static class Blocks {

        public static final TagKey<Block> AQUARIUM_GLASS = tag("aquarium_glass_blocks");
        public static final TagKey<Block> INFERNAL_AQUARIUM_GLASS = tag("infernal_aquarium_glass_blocks");
        public static final TagKey<Block> RADON_AQUARIUM_GLASS = tag("radon_aquarium_glass_blocks");
        public static final TagKey<Block> SUGAR_AQUARIUM_GLASS = tag("sugar_aquarium_glass_blocks");

        public static final TagKey<Block> FRESHWATER_PLANTS = tag("freshwater_plants");

        public static final TagKey<Block> RED_ALGAE = tag("propagates_red_algae");
        public static final TagKey<Block> GREEN_ALGAE = tag("propagates_green_algae");

        public static final TagKey<Block> ALGAE_REPLACEABLE = tag("algae_replaceable");

        public static final TagKey<Block> STROMATOLITE_REPLACEABLE = tag("stromatolite_replaceable");
        public static final TagKey<Block> FOSSIL_STROMATOLITE_REPLACEABLE = tag("fossil_stromatolite_replaceable");

        public static final TagKey<Block> AQUATIC_PLANTS = tag("aquatic_plants");

        public static final TagKey<Block> GOURAMI_INVESTIGATION_TARGETS = tag("gourami_investigation_targets");

        private static TagKey<Block> tag(String name){
            return BlockTags.create(new ResourceLocation(Fintastic.MOD_ID, name));
        }
    }

    public static class Fluid {
        public static final TagKey<net.minecraft.world.level.material.Fluid> AC_ACID
                = tag("ac_acid");

        public static final TagKey<net.minecraft.world.level.material.Fluid> AC_SODA
                = tag("ac_soda");

        private static TagKey<net.minecraft.world.level.material.Fluid> tag(String name){
            return FluidTags.create(new ResourceLocation(Fintastic.MOD_ID, name));
        }
    }

    public static class EntityType {

        public static final TagKey<net.minecraft.world.entity.EntityType<?>> FISHNET_ADDITIONS = tag("fishnet_additions");
        public static final TagKey<net.minecraft.world.entity.EntityType<?>> FISHNET_BLACKLIST = tag("fishnet_blacklist");

        public static final TagKey<net.minecraft.world.entity.EntityType<?>> PREDATOR_FISH = tag("predator_fish");
        public static final TagKey<net.minecraft.world.entity.EntityType<?>> FISH_PREY = tag("fish_prey");

        private static TagKey<net.minecraft.world.entity.EntityType<?>> tag(String name){
            return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(Fintastic.MOD_ID, name));
        }
    }

}
