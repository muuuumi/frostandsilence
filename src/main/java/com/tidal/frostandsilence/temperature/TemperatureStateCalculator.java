package com.tidal.frostandsilence.temperature;

public class TemperatureStateCalculator {

    public TemperatureState calculate(double bodyTemperature) {

        if (bodyTemperature < 32.0) {
            return TemperatureState.FREEZING;
        }

        if (bodyTemperature < 34.0) {
            return TemperatureState.VERY_COLD;
        }

        if (bodyTemperature < 36.0) {
            return TemperatureState.COLD;
        }

        if (bodyTemperature <= 38.0) {
            return TemperatureState.COMFORTABLE;
        }

        if (bodyTemperature <= 39.0) {
            return TemperatureState.WARM;
        }

        if (bodyTemperature <= 40.0) {
            return TemperatureState.HOT;
        }

        return TemperatureState.BURNING;
    }
}