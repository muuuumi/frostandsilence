package com.tidal.frostandsilence.temperature.environment;

import com.tidal.frostandsilence.config.TemperatureConfig;
import net.minecraft.server.level.ServerPlayer;

public class WeatherTemperatureModifier {

    public double calculate(ServerPlayer player) {

        if (player.level().isThundering()) {
            return TemperatureConfig.THUNDER_TEMPERATURE_EFFECT;
        }

        if (player.level().isRaining()) {
            return TemperatureConfig.RAIN_TEMPERATURE_EFFECT;
        }

        return 0.0;
    }
}