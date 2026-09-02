package com.tidal.frostandsilence.temperature;

import com.tidal.frostandsilence.temperature.thermal.ThermalEnergy;
import com.tidal.frostandsilence.temperature.thermal.ThermalEnvironment;
import com.tidal.frostandsilence.temperature.thermal.ThermalInteraction;

public class TemperatureSimulator {

    public double calculateEnvironmentalEnergyTransfer(
            double currentTemperature,
            double environmentalTemperature,
            double thermalConductance,
            double heatCapacity,
            double deltaTime) {

        double heatTransferCoefficient =
                thermalConductance / heatCapacity;

        double temperatureDifference =
                environmentalTemperature - currentTemperature;

        double exponential =
                Math.exp(-heatTransferCoefficient * deltaTime);

        double transferFactor =
                1 - exponential;

        double temperatureChange =
                temperatureDifference * transferFactor;

        return heatCapacity * temperatureChange;
    }

    public void simulateStep(
            BodyTemperature body,
            ThermalEnvironment environment,
            ThermalInteraction interaction,
            double deltaTime,
            ThermalEnergy externalEnergy) {

        double environmentalEnergy =
                calculateEnvironmentalEnergyTransfer(
                        body.getTemperature(),
                        environment.getTemperature(),
                        interaction.getThermalConductance(),
                        body.getHeatCapacity(),
                        deltaTime
                );

        body.applyEnergy(environmentalEnergy);
        body.applyEnergy(externalEnergy.getJoules());
    }
}