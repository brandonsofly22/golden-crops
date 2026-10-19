
package com.brandon.goldencrops.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class ModFoods {

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
}