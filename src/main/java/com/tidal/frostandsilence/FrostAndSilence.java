package com.tidal.frostandsilence;

import com.tidal.frostandsilence.command.ModCommands;
import com.tidal.frostandsilence.entity.ModEntities;
import com.tidal.frostandsilence.item.ModItems;
import com.tidal.frostandsilence.temperature.TemperatureInitializer;
import com.tidal.frostandsilence.temperature.TemperatureStatePayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrostAndSilence implements ModInitializer {

	public static final String MOD_ID = "frostandsilence";

	public static final Logger LOGGER =
			LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		// Register items.
		ModItems.registerModItems();

		// Register entities and their attributes.
		ModEntities.registerModEntityTypes();
		ModEntities.registerAttributes();

		// Temperature system.
		TemperatureInitializer.initialize();
		ModCommands.initialize();

		LOGGER.info("Freezing your world...");
	}

	public static Identifier id(String path) {

		return Identifier.fromNamespaceAndPath(
				MOD_ID,
				path
		);

	}
}