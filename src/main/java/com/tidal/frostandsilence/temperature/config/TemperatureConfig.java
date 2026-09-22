package com.tidal.frostandsilence.temperature.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class TemperatureConfig {

    private TemperatureConfig() {
    }

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH =
            FabricLoader.getInstance().getConfigDir().resolve("frostandsilence-temperature.json");

    public static Values VALUES = new Values();

    public static void load() {
        try {
            if (Files.exists(CONFIG_PATH)) {
                Values loaded = GSON.fromJson(Files.readString(CONFIG_PATH), Values.class);
                VALUES = loaded != null ? loaded : new Values();
            }
        } catch (IOException e) {
            VALUES = new Values();
        }
        save();
    }

    public static void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            Files.writeString(CONFIG_PATH, GSON.toJson(VALUES));
        } catch (IOException ignored) {
            // Config is non-critical; fall back to in-memory defaults.
        }
    }

    public static final class Values {

        // --- Body temperature simulation ---
        public double changeSpeed = 0.01;
        public int tickIntervalTicks = 10;

        // --- Ambient / biome temperature ---
        public double tempAmplitude = 0.6;
        public double biomeNeutralPoint = 0.8;
        public double rainColdPenalty = 0.15;
        public double nightColdPenalty = 0.10;

        // --- Cold sources (TemperatureCold) ---
        public double waterCold = 0.20;
        public double powderSnowCold = 0.40;

        // --- Heat sources (TemperatureHeat) ---
        public double campfireHeat = 0.35;
        public double soulCampfireBonus = 0.15;
        public double fireHeat = 0.25;
        public double lavaHeat = 0.50;
        public int heatSearchRadiusHorizontal = 5;
        public int heatSearchRadiusVertical = 3;

        // --- Clothing (TemperatureClothing) ---
        public double leatherWarmthPerPiece = 0.05;

        // --- State thresholds (TemperatureData) ---
        public double freezingThreshold = -0.6;
        public double coldThreshold = -0.3;
        public double hotThreshold = 0.3;
        public double scorchingThreshold = 0.6;

        // --- Status effects (TemperatureEffects) ---
        public int effectDurationTicks = 30;
        public int coldSlownessAmplifier = 0;
        public int freezingSlownessAmplifier = 1;
        public int hotSlownessAmplifier = 0;
        public int scorchingSlownessAmplifier = 1;
        public int freezingDamageIntervalTicks = 40;
        public float freezingDamage = 2.0f;
        public int scorchingDamageIntervalTicks = 40;
        public float scorchingDamage = 2.0f;
    }
}
