package com.tidal.frostandsilence.temperature.thermal;

public class ThermalEnvironment {

    private double temperature;

    public ThermalEnvironment(double temperature) {
        this.temperature = temperature;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}