package com.tidal.frostandsilence.food;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

public class ModFoods {

    public static final FoodProperties CHOCOLATE = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f).build();
    public static final FoodProperties HOT_CHOCOLATE = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f).build();
    public static final Consumable CHOCOLATE_CONSUMABLE = Consumables.defaultFood()
            .consumeSeconds(1f).build();
    public static final Consumable HOT_CHOCOLATE_CONSUMABLE = Consumables.defaultDrink()
            .consumeSeconds(1f).build();
}
