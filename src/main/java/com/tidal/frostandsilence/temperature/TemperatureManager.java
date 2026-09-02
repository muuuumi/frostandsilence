package com.tidal.frostandsilence.temperature;

import com.tidal.frostandsilence.network.TemperatureSyncPayload;
import com.tidal.frostandsilence.temperature.environment.EnvironmentalTemperatureCalculator;
import com.tidal.frostandsilence.temperature.environment.EnvironmentalTemperatureData;
import com.tidal.frostandsilence.temperature.thermal.ThermalEnergy;
import com.tidal.frostandsilence.temperature.thermal.ThermalEnvironment;
import com.tidal.frostandsilence.temperature.thermal.ThermalInteraction;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;

public class TemperatureManager {
 // test
    private final TemperatureSimulator simulator =
            new TemperatureSimulator();

    private final EnvironmentalTemperatureCalculator
            environmentCalculator =
            new EnvironmentalTemperatureCalculator();

    public void tick(ServerPlayer player) {

        BodyTemperature body =
                ((TemperatureHolder) player)
                        .getBodyTemperature();

        EnvironmentalTemperatureData environmentData =
                environmentCalculator.calculate(player);

        ThermalEnvironment environment =
                new ThermalEnvironment(
                        environmentData.totalTemperature()
                );

        ThermalInteraction interaction =
                new ThermalInteraction(243.0);

        ThermalEnergy externalEnergy =
                new ThermalEnergy(0.0);

        simulator.simulateStep(
                body,
                environment,
                interaction,
                0.05,
                externalEnergy
        );

        ServerPlayNetworking.send(
                player,
                new TemperatureSyncPayload(
                        body.getTemperature(),
                        environmentData.biomeTemperature(),
                        environmentData.altitudeModifier(),
                        environmentData.timeModifier(),
                        environmentData.weatherModifier(),
                        environmentData.waterModifier(),
                        environmentData.totalTemperature()
                )
        );
    }
}