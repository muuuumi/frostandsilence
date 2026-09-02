package com.tidal.frostandsilence.temperature.environment;

import com.tidal.frostandsilence.config.TemperatureConfig;
import net.minecraft.server.level.ServerPlayer;

public class AltitudeTemperatureModifier {

    private static final double REFERENCE_ALTITUDE = 64.0;
    private static final double LAPSE_RATE = 0.0065;

    public double calculate(ServerPlayer player) {

        double altitude = player.getY();

        double altitudeDifference =
                altitude - TemperatureConfig.REFERENCE_ALTITUDE;

        return -TemperatureConfig.ALTITUDE_LAPSE_RATE
                * altitudeDifference;
    }
}