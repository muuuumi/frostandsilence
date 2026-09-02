package com.tidal.frostandsilence;

import com.tidal.frostandsilence.network.TemperatureSyncPayload;
import com.tidal.frostandsilence.temperature.TemperatureManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class FrostAndSilence implements ModInitializer {

	public static final String MOD_ID = "frostandsilence";

	private final TemperatureManager temperatureManager =
			new TemperatureManager();

	@Override
	public void onInitialize() {

		PayloadTypeRegistry.clientboundPlay().register(
				TemperatureSyncPayload.TYPE,
				TemperatureSyncPayload.CODEC
		);

		ServerTickEvents.END_SERVER_TICK.register(
				this::tickTemperature
		);
	}

	private void tickTemperature(MinecraftServer server) {

		for (ServerPlayer player :
				server.getPlayerList().getPlayers()) {

			temperatureManager.tick(player);
		}
	}
}