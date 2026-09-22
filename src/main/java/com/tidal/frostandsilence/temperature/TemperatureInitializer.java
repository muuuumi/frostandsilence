package com.tidal.frostandsilence.temperature;

import com.tidal.frostandsilence.temperature.config.TemperatureConfig;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.server.level.ServerPlayer;

public final class TemperatureInitializer {

    private TemperatureInitializer() {
    }

    public static void initialize() {

        TemperatureAttachments.initialize();
        TemperatureConfig.load();

        PayloadTypeRegistry.clientboundPlay().register(
                TemperatureStatePayload.TYPE,
                TemperatureStatePayload.CODEC
        );

        ServerTickEvents.END_SERVER_TICK.register(server -> {

            for (ServerPlayer player : server.getPlayerList().getPlayers()) {

                if (player.tickCount % TemperatureConfig.VALUES.tickIntervalTicks != 0) {
                    continue;
                }

                TemperatureManager.tick(player);
                TemperatureEffects.apply(player);
            }
        });
    }
}
