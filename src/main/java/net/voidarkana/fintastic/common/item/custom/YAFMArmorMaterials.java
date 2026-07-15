package net.voidarkana.fintastic.common.item.custom;

import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.voidarkana.fintastic.Fintastic;

import java.util.EnumMap;
import java.util.List;

public class YAFMArmorMaterials {

    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, Fintastic.MOD_ID);

    public static final int HAT_DURABILITY_MULTIPLIER = 5;

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> HAT = ARMOR_MATERIALS.register("hat",
            () -> new ArmorMaterial(
                    Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.HELMET, 1);
                        map.put(ArmorItem.Type.CHESTPLATE, 7);
                        map.put(ArmorItem.Type.LEGGINGS, 5);
                        map.put(ArmorItem.Type.BOOTS, 4);
                    }),
                    9,
                    SoundEvents.LLAMA_SWAG,
                    () -> Ingredient.of(ItemTags.WOOL),
                    List.of(new ArmorMaterial.Layer(Fintastic.location("fishing_hat"))),
                    0f,
                    0f));

    public static void register(IEventBus eventBus) {
        ARMOR_MATERIALS.register(eventBus);
    }
}
