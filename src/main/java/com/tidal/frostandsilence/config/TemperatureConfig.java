package com.tidal.frostandsilence.config;

public final class TemperatureConfig {

    public static final double NORMAL_BODY_TEMPERATURE = 37.0;

    public static final double BODY_MASS = 70.0;
    public static final double BODY_SPECIFIC_HEAT = 3470.0;

    public static final double BIOME_REFERENCE_TEMPERATURE = 0.8;
    public static final double REFERENCE_ENVIRONMENTAL_TEMPERATURE = 37.0;
    public static final double BIOME_TEMPERATURE_SCALE = 35.0;

    public static final double REFERENCE_ALTITUDE = 64.0;
    public static final double ALTITUDE_LAPSE_RATE = 0.0065;

    public static final double DAYTIME_TEMPERATURE_EFFECT = 2.0;
    public static final double NIGHTTIME_TEMPERATURE_EFFECT = -2.0;

    public static final double RAIN_TEMPERATURE_EFFECT = -4.0;
    public static final double THUNDER_TEMPERATURE_EFFECT = -7.0;

    public static final double WATER_TEMPERATURE_EFFECT = -4.0;

    private TemperatureConfig() {

    }
}

