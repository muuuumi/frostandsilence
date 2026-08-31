package com.tidal.frostandsilence;

import com.tidal.frostandsilence.item.ModItems;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrostAndSilence implements ModInitializer {
	public static final String MOD_ID = "frostandsilence";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Freezing your world...");
		ModItems.registerModItems();
	}
	//YETI

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
