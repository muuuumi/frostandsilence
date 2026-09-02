package com.tidal.frostandsilence.temperature.thermal;

public class ThermalInteraction {

    private double thermalConductance;

    public ThermalInteraction(double thermalConductance) {
        this.thermalConductance = thermalConductance;
    }

    public double getThermalConductance() {
        return thermalConductance;
    }

    public void setThermalConductance(double thermalConductance) {
        this.thermalConductance = thermalConductance;
    }
}