package com.tidal.frostandsilence.temperature;

import com.tidal.frostandsilence.temperature.config.TemperatureConfig;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class TemperatureClothing {

    private TemperatureClothing() {
    }

    public static double getWarmth(ServerPlayer player) {

        double warmth = 0.0;

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            // Only check armor slots (HEAD, CHEST, LEGS, FEET).
            if (slot.getType() != EquipmentSlot.Type.HUMANOID_ARMOR) {
                continue;
            }

            ItemStack armor = player.getItemBySlot(slot);

            if (armor.is(Items.LEATHER_HELMET)
                    || armor.is(Items.LEATHER_CHESTPLATE)
                    || armor.is(Items.LEATHER_LEGGINGS)
                    || armor.is(Items.LEATHER_BOOTS)) {
                warmth += TemperatureConfig.VALUES.leatherWarmthPerPiece;
            }
        }

        return warmth;
    }
}
