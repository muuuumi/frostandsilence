package com.tidal.frostandsilence.temperature;

import com.tidal.frostandsilence.temperature.config.TemperatureConfig;
import com.tidal.frostandsilence.temperature.data.TemperatureData;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

public final class TemperatureManager {

    private TemperatureManager() {
    }

    public static void tick(ServerPlayer player) {

        TemperatureConfig.Values config = TemperatureConfig.VALUES;
        TemperatureData data = player.getAttachedOrCreate(TemperatureAttachments.TEMPERATURE);
        TemperatureState oldState = data.getState();

        double bodyTemperature = data.temperature();
        double environmentTemperature = TemperatureEnvironment.getTemperature(player);
        double difference = environmentTemperature - bodyTemperature;

        double newTemperature = bodyTemperature + difference * config.changeSpeed;
        newTemperature = Math.clamp(newTemperature, -1.0, 1.0);

        TemperatureData newData = data.withTemperature(newTemperature);

        player.setAttached(TemperatureAttachments.TEMPERATURE, newData);

        TemperatureState newState = newData.getState();
        if (newState != oldState) {
            onStateChanged(player, newState);
        }

        ServerPlayNetworking.send(player, new TemperatureStatePayload(newState, newTemperature));
    }

    private static void onStateChanged(ServerPlayer player, TemperatureState newState) {

        if (!TemperatureConfig.VALUES.soundCuesEnabled) {
            return;
        }

        var sound = switch (newState) {
            case FREEZING -> SoundEvents.PLAYER_HURT_FREEZE;
            case COLD -> SoundEvents.POWDER_SNOW_STEP;
            case HOT -> SoundEvents.FIRE_AMBIENT;
            case SCORCHING -> SoundEvents.GENERIC_BURN;
            case COMFORTABLE -> null;
        };

        if (sound == null) {
            return;
        }

        player.level().playSound(null, player.blockPosition(), sound, SoundSource.PLAYERS, 0.6f, 1.0f);
    }

    public static double getTemperature(ServerPlayer player) {
        return player.getAttachedOrCreate(TemperatureAttachments.TEMPERATURE).temperature();
    }

    public static TemperatureState getState(ServerPlayer player) {
        return player.getAttachedOrCreate(TemperatureAttachments.TEMPERATURE).getState();
    }
}
