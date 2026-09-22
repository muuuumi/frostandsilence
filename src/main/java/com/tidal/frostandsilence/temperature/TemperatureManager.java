package com.tidal.frostandsilence.temperature;

import com.tidal.frostandsilence.temperature.config.TemperatureConfig;
import com.tidal.frostandsilence.temperature.data.TemperatureData;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;

public final class TemperatureManager {

    private TemperatureManager() {
    }

    public static void tick(ServerPlayer player) {

        TemperatureData data = player.getAttachedOrCreate(TemperatureAttachments.TEMPERATURE);

        double bodyTemperature = data.temperature();
        double environmentTemperature = TemperatureEnvironment.getTemperature(player);
        double difference = environmentTemperature - bodyTemperature;

        double newTemperature = bodyTemperature + difference * TemperatureConfig.VALUES.changeSpeed;
        newTemperature = Math.clamp(newTemperature, -1.0, 1.0);

        TemperatureData newData = data.withTemperature(newTemperature);

        player.setAttached(TemperatureAttachments.TEMPERATURE, newData);

        ServerPlayNetworking.send(player, new TemperatureStatePayload(newData.getState()));
    }

    public static double getTemperature(ServerPlayer player) {
        return player.getAttachedOrCreate(TemperatureAttachments.TEMPERATURE).temperature();
    }

    public static TemperatureState getState(ServerPlayer player) {
        return player.getAttachedOrCreate(TemperatureAttachments.TEMPERATURE).getState();
    }
}
