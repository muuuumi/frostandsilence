package com.tidal.frostandsilence.temperature.environment;

import com.tidal.frostandsilence.config.TemperatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.biome.Biome;

public class EnvironmentalTemperatureCalculator {

    private final AltitudeTemperatureModifier altitudeModifier =
            new AltitudeTemperatureModifier();

    private final TimeTemperatureModifier timeModifier =
            new TimeTemperatureModifier();

    private final WeatherTemperatureModifier weatherModifier =
            new WeatherTemperatureModifier();

    private final WaterTemperatureModifier waterModifier =
            new WaterTemperatureModifier();

    public EnvironmentalTemperatureData calculate(
            ServerPlayer player) {

        BlockPos position =
                player.blockPosition();

        Biome biome =
                player.level()
                        .getBiome(position)
                        .value();

        double biomeTemperature =
                convertBiomeTemperature(
                        biome.getBaseTemperature()
                );

        double altitudeTemperature =
                altitudeModifier.calculate(player);

        double timeTemperature =
                timeModifier.calculate(player);

        double weatherTemperature =
                weatherModifier.calculate(player);

        double waterTemperature =
                waterModifier.calculate(player);

        double totalTemperature =
                biomeTemperature
                        + altitudeTemperature
                        + timeTemperature
                        + weatherTemperature
                        + waterTemperature;

        return new EnvironmentalTemperatureData(
                biomeTemperature,
                altitudeTemperature,
                timeTemperature,
                weatherTemperature,
                waterTemperature,
                totalTemperature
        );
    }

    private double convertBiomeTemperature(
            double biomeTemperature) {

        return TemperatureConfig.REFERENCE_ENVIRONMENTAL_TEMPERATURE
                + (biomeTemperature
                - TemperatureConfig.BIOME_REFERENCE_TEMPERATURE)
                * TemperatureConfig.BIOME_TEMPERATURE_SCALE;
    }
}