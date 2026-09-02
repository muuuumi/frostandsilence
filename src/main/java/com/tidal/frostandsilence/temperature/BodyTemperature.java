package com.tidal.frostandsilence.temperature;

import com.tidal.frostandsilence.temperature.thermal.ThermalProperties;

public class BodyTemperature {

    private double temperature;
    private final ThermalProperties thermalProperties;

    private final TemperatureStateCalculator stateCalculator =
            new TemperatureStateCalculator();

    public TemperatureState getState() {

        return stateCalculator.calculate(temperature);
    }

    public BodyTemperature(
            double temperature,
            ThermalProperties thermalProperties) {

        this.temperature = temperature;
        this.thermalProperties = thermalProperties;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHeatCapacity() {
        return thermalProperties.getHeatCapacity();
    }

    public ThermalProperties getThermalProperties() {
        return thermalProperties;
    }

    public void changeTemperature(double amount) {
        this.temperature += amount;
    }

    public void applyEnergy(double joules) {
        changeTemperature(joules / getHeatCapacity());
    }
}