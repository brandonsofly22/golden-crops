
package com.brandon.goldencrops.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class ModFoods {

    // Golden Bread
    public static final FoodProperties GOLDEN_BREAD =
            new FoodProperties.Builder()
                    .nutrition(8)
                    .saturationModifier(0.6F)
                    .build();

    public static final Consumable GOLDEN_BREAD_CONSUMABLE =
            Consumables.defaultFood()
                    .onConsume(new ApplyStatusEffectsConsumeEffect(
                            new MobEffectInstance(MobEffects.RESISTANCE, 600, 0)
                    ))
                    .build();

    // Golden Beetroot
    public static final FoodProperties GOLDEN_BEETROOT =
            new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationModifier(0.45F)
                    .build();

    public static final Consumable GOLDEN_BEETROOT_CONSUMABLE =
            Consumables.defaultFood()
                    .onConsume(new ApplyStatusEffectsConsumeEffect(
                            new MobEffectInstance(MobEffects.STRENGTH, 300, 0)
                    ))
                    .build();

    // Golden Beetroot Soup
    public static final FoodProperties GOLDEN_BEETROOT_SOUP =
            new FoodProperties.Builder()
                    .nutrition(9)
                    .saturationModifier(0.6F)
                    .build();

    public static final Consumable GOLDEN_BEETROOT_SOUP_CONSUMABLE =
            Consumables.defaultFood()
                    .onConsume(new ApplyStatusEffectsConsumeEffect(
                            new MobEffectInstance(MobEffects.STRENGTH, 600, 1)
                    ))
                    .build();

    // Golden Potato
    public static final FoodProperties GOLDEN_POTATO =
            new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationModifier(0.3F)
                    .build();

    public static final Consumable GOLDEN_POTATO_CONSUMABLE =
            Consumables.defaultFood()
                    .onConsume(new ApplyStatusEffectsConsumeEffect(
                            new MobEffectInstance(MobEffects.REGENERATION, 60, 0)
                    ))
                    .build();

    // Golden Baked Potato
    public static final FoodProperties GOLDEN_BAKED_POTATO =
            new FoodProperties.Builder()
                    .nutrition(8)
                    .saturationModifier(0.6F)
                    .build();

    public static final Consumable GOLDEN_BAKED_POTATO_CONSUMABLE =
            Consumables.defaultFood()
                    .onConsume(new ApplyStatusEffectsConsumeEffect(
                            new MobEffectInstance(MobEffects.REGENERATION, 160, 1)
                    ))
                    .build();
}
