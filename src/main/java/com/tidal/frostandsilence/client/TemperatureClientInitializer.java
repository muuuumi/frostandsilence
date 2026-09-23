package com.tidal.frostandsilence.client;

import com.tidal.frostandsilence.temperature.TemperatureStatePayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public final class TemperatureClientInitializer {

    private TemperatureClientInitializer() {
    }

    public static void initialize() {

        TemperatureHud.initialize();

        ClientPlayNetworking.registerGlobalReceiver(TemperatureStatePayload.TYPE, (payload, context) ->
                context.client().execute(() -> TemperatureHud.update(payload.state(), payload.temperature()))
        );
    }
}
