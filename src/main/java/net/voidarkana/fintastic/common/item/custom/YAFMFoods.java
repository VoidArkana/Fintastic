package net.voidarkana.fintastic.common.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class YAFMFoods {

    public static final FoodProperties RAW_FISH_TINY = (new FoodProperties.Builder())
            .nutrition(1)
            .saturationModifier(0F)
            .fast()
            .build();

    public static final FoodProperties RAW_FISH = (new FoodProperties.Builder())
            .nutrition(2)
            .saturationModifier(0.1F)
            .effect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F)
            .effect(new MobEffectInstance(MobEffects.POISON, 200, 0), 0.15F)
            .effect(new MobEffectInstance(MobEffects.CONFUSION, 300, 0), 0.2F)
            .build();

    public static final FoodProperties RAW_FISH_MED = (new FoodProperties.Builder())
            .nutrition(3)
            .saturationModifier(0.15F)
            .effect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.6F)
            .effect(new MobEffectInstance(MobEffects.POISON, 200, 0), 0.3F)
            .effect(new MobEffectInstance(MobEffects.CONFUSION, 300, 0), 0.4F)
            .build();

    public static final FoodProperties RAW_FISH_BIG = (new FoodProperties.Builder())
            .nutrition(4)
            .saturationModifier(0.2F)
            .effect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.6F)
            .effect(new MobEffectInstance(MobEffects.POISON, 200, 0), 0.3F)
            .effect(new MobEffectInstance(MobEffects.CONFUSION, 300, 0), 0.4F)
            .build();

    public static final FoodProperties COOKED_FISH = (new FoodProperties.Builder())
            .nutrition(5)
            .saturationModifier(0.6F).build();

    public static final FoodProperties SEA_GRAPE_SALAD = (new FoodProperties.Builder())
            .nutrition(6)
            .saturationModifier(0.4F).build();

    public static final FoodProperties LOTUS_ROOT = (new FoodProperties.Builder())
            .nutrition(2)
            .saturationModifier(0.1F).build();

    public static final FoodProperties COOKED_LOTUS_ROOT = (new FoodProperties.Builder())
            .nutrition(6)
            .saturationModifier(0.4F).build();
}
