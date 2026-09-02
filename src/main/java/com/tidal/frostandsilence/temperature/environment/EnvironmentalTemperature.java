package com.tidal.frostandsilence.temperature.environment;

public class EnvironmentalTemperature {

    private double temperature;

    public EnvironmentalTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void addTemperature(double amount) {
        this.temperature += amount;
    }
}