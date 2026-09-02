        package com.tidal.frostandsilence.temperature.environment;

import com.tidal.frostandsilence.config.TemperatureConfig;
import net.minecraft.server.level.ServerPlayer;

public class WaterTemperatureModifier {

    public double calculate(ServerPlayer player) {

        if (player.isInWater()) {
            return TemperatureConfig.WATER_TEMPERATURE_EFFECT;
        }

        return 0.0;
    }
}
