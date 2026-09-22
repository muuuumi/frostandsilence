package com.tidal.frostandsilence.temperature;

import com.tidal.frostandsilence.temperature.config.TemperatureConfig;
import net.minecraft.server.level.ServerPlayer;

public final class TemperatureEnvironment {

    private TemperatureEnvironment() {
    }

    public static double getTemperature(ServerPlayer player) {

        TemperatureConfig.Values config = TemperatureConfig.VALUES;

        long time = player.level().getDefaultClockTime() % 24000L;
        boolean isNight = time >= 13000L && time < 23000L;

        double biomeTemperature = player.level()
                .getBiome(player.blockPosition())
                .value()
                .getBaseTemperature();

        double temperature = (biomeTemperature - config.biomeNeutralPoint) * config.tempAmplitude;

        boolean sheltered = !player.level().canSeeSky(player.blockPosition());

        // Weather makes exposed areas colder.
        if (player.level().isRaining() && !sheltered) {
            temperature -= config.rainColdPenalty;
        }

        // Night is colder.
        if (isNight) {
            temperature -= config.nightColdPenalty;
        }

        // Water and powder snow make the environment colder.
        temperature += TemperatureModifiers.getColdEffect(player, temperature);

        // Heat sources provide warmth.
        temperature += TemperatureModifiers.getHeat(player);

        // Leather clothing provides warmth.
        temperature += TemperatureClothing.getWarmth(player);

        return temperature;
    }
}
