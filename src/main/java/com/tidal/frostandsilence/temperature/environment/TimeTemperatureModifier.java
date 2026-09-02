package com.tidal.frostandsilence.temperature.environment;

import com.tidal.frostandsilence.config.TemperatureConfig;
import net.minecraft.server.level.ServerPlayer;

public class TimeTemperatureModifier {

    public double calculate(ServerPlayer player) {

        long time = player.level().getOverworldClockTime();

        long timeOfDay =
                Math.floorMod(time, 24000);

        double angle =
                ((timeOfDay - 6000) / 24000.0)
                        * (2.0 * Math.PI);

        return Math.cos(angle)
                * TemperatureConfig.DAYTIME_TEMPERATURE_EFFECT;
    }
}

