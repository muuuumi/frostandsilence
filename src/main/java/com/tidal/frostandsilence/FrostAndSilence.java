package com.tidal.frostandsilence;

import com.tidal.frostandsilence.item.ModItems;
import com.tidal.frostandsilence.network.TemperatureSyncPayload;
import com.tidal.frostandsilence.temperature.TemperatureManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrostAndSilence implements ModInitializer {

	public static final String MOD_ID = "frostandsilence";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private final TemperatureManager temperatureManager = new TemperatureManager();

	@Override
	public void onInitialize() {
		// Register the clientbound payload for temperature sync
		PayloadTypeRegistry.clientboundPlay().register(
				TemperatureSyncPayload.TYPE,
				TemperatureSyncPayload.CODEC
		);

		// Register a tick handler for temperature updates
		ServerTickEvents.END_SERVER_TICK.register(this::tickTemperature);


		ModItems.registerModItems();
		LOGGER.info("FrostAndSilence initialized!");
	}

	private void tickTemperature(MinecraftServer server) {
		for (ServerPlayer player : server.getPlayerList().getPlayers()) {
			temperatureManager.tick(player);
		}
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}