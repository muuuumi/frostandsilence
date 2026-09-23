package com.tidal.frostandsilence.temperature;

import com.tidal.frostandsilence.temperature.config.TemperatureConfig;
import com.tidal.frostandsilence.temperature.data.TemperatureData;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * Applies warmth or cooling when a player finishes eating a food
 * item. The item -> delta mapping lives entirely in {@link TemperatureConfig},
 * so new (including custom/modded) foods can be added without touching code.
 */
public final class TemperatureFood {

    private TemperatureFood() {
    }

    /**
     * Called when a player finishes eating.
     */
    public static void onEaten(ServerPlayer player, ItemStack food) {

        Double delta = lookup(food);

        if (delta == null || delta == 0.0) {
            return;
        }

        TemperatureData data = player.getAttachedOrCreate(TemperatureAttachments.TEMPERATURE);
        double newTemperature = Math.clamp(
                data.temperature() + delta,
                -1.0,
                1.0
        );

        player.setAttached(
                TemperatureAttachments.TEMPERATURE,
                data.withTemperature(newTemperature)
        );
    }

    private static Double lookup(ItemStack food) {

        Item item = food.getItem();
        Identifier id = BuiltInRegistries.ITEM.getKey(item);

        if (id == null) {
            return null;
        }

        return TemperatureConfig.VALUES.foodWarmth.get(id.toString());
    }
}
