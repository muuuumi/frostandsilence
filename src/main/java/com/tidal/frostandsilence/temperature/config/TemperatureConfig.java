package com.tidal.frostandsilence.temperature.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * All tunable values for the temperature system, loaded from and saved to
 * {@code config/frostandsilence-temperature.json}. Edit that file to rebalance
 * the system without recompiling; missing/new fields are filled with defaults
 * and written back automatically.
 */
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

    /**
     * Plain data holder. Field order matches the generated JSON.
     */
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

        // --- Desert extremes (TemperatureEnvironment) ---
        // Extra heat/cold applied on top of the normal biome/day-night swing while
        // standing in a desert biome, scaled by how close it is to noon/midnight.
        public boolean desertExtremesEnabled = true;
        public double desertNoonHeatBonus = 0.30;
        public double desertNightColdBonus = 0.25;
        // Half-width (in ticks) of the window around noon (6000) / midnight (18000)
        // over which the bonus fades in and out.
        public int desertExtremeWindowTicks = 3000;

        // --- Food warmth (TemperatureFood) ---
        // Item id -> temperature delta applied when the food is eaten. Positive
        // warms the player, negative cools them. Add your own item ids here (including
        // custom modded foods) to extend this without touching code.
        public Map<String, Double> foodWarmth = defaultFoodWarmth();
        private static Map<String, Double> defaultFoodWarmth() {
            Map<String, Double> map = new LinkedHashMap<>();
            // Warming (cooked / hearty meals)
            map.put("minecraft:cooked_beef", 0.05);
            map.put("minecraft:cooked_porkchop", 0.05);
            map.put("minecraft:cooked_mutton", 0.05);
            map.put("minecraft:cooked_chicken", 0.05);
            map.put("minecraft:cooked_rabbit", 0.05);
            map.put("minecraft:cooked_salmon", 0.05);
            map.put("minecraft:baked_potato", 0.04);
            map.put("minecraft:mushroom_stew", 0.02);
            map.put("minecraft:rabbit_stew", 0.3);
            map.put("minecraft:beetroot_soup", 0.02);
            map.put("minecraft:pumpkin_pie", 0.01);
            // Cooling (fresh / raw / fruity foods)
            map.put("minecraft:melon_slice", -0.10);
            map.put("minecraft:apple", -0.05);
            map.put("minecraft:sweet_berries", -0.05);
            map.put("minecraft:glow_berries", -0.05);
            map.put("minecraft:chorus_fruit", -0.04);
            map.put("minecraft:golden_carrot", -0.06);
            map.put("minecraft:cookie", -0.03);
            return map;
        }

        // --- Feedback & juice (TemperatureEffects / client) ---
        public boolean soundCuesEnabled = true;
        public boolean particlesEnabled = true;
        public boolean vignetteEnabled = true;
        public boolean hudPulseEnabled = true;
        // How often (in ticks) ambient particles (breath fog / heat shimmer) spawn.
        public int particleIntervalTicks = 30;
    }
}
