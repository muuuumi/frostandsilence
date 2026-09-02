package com.tidal.frostandsilence.temperature.environment;

public record EnvironmentalTemperatureData(
        double biomeTemperature,
        double altitudeModifier,
        double timeModifier,
        double weatherModifier,
        double waterModifier,
        double totalTemperature
) {
}