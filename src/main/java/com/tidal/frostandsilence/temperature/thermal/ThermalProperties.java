package com.tidal.frostandsilence.temperature.thermal;

public class ThermalProperties {

    private final double mass;
    private final double specificHeatCapacity;

    public ThermalProperties(
            double mass,
            double specificHeatCapacity) {

        this.mass = mass;
        this.specificHeatCapacity = specificHeatCapacity;
    }

    public double getMass() {
        return mass;
    }

    public double getSpecificHeatCapacity() {
        return specificHeatCapacity;
    }

    public double getHeatCapacity() {
        return mass * specificHeatCapacity;
    }
}