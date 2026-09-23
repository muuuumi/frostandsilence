package com.tidal.frostandsilence.temperature;

import com.tidal.frostandsilence.temperature.config.TemperatureConfig;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;

public final class TemperatureEnvironment {

    private static final long NOON_TICK = 6000L;
    private static final long MIDNIGHT_TICK = 18000L;

    private TemperatureEnvironment() {
    }

    public static double getTemperature(ServerPlayer player) {

        TemperatureConfig.Values config = TemperatureConfig.VALUES;

        long time = player.level().getDefaultClockTime() % 24000L;
        boolean isNight = time >= 13000L && time < 23000L;

        var biomeHolder = player.level().getBiome(player.blockPosition());
        double biomeTemperature = biomeHolder.value().getBaseTemperature();

        double temperature = (biomeTemperature - config.biomeNeutralPoint) * config.tempAmplitude;

        boolean sheltered = !player.level().canSeeSky(player.blockPosition());

        // Weather makes exposed areas colder.
        if (player.level().isRaining() && !sheltered) {
            temperature -= config.rainColdPenalty;
        }

        // Night is colder.
        if (isNight) {
            temperature -= config.nightColdPenalty;
        }

        // Deserts swing harder: scorching around noon, freezing around midnight.
        if (config.desertExtremesEnabled
                && !sheltered
                && (biomeHolder.is(BiomeTags.IS_BADLANDS)
                || biomeHolder.is(Biomes.DESERT))) {
            temperature += desertExtreme(time, config);
        }

        // Water and powder snow make the environment colder.
        temperature += TemperatureModifiers.getColdEffect(player, temperature);

        // Heat sources provide warmth.
        temperature += TemperatureModifiers.getHeat(player);

        // Leather clothing provides warmth.
        temperature += TemperatureClothing.getWarmth(player);

        return temperature;
    }

    /**
     * Returns a heat bonus that fades in/out around noon and a cold bonus that
     * fades in/out around midnight, both scaled by {@code desertExtremeWindowTicks}.
     */
    private static double desertExtreme(long time, TemperatureConfig.Values config) {

        double noonProximity = 1.0 - Math.min(1.0, Math.abs(time - NOON_TICK) / (double) config.desertExtremeWindowTicks);
        double midnightDistance = Math.min(Math.abs(time - MIDNIGHT_TICK), 24000L - Math.abs(time - MIDNIGHT_TICK));
        double midnightProximity = 1.0 - Math.min(1.0, midnightDistance / (double) config.desertExtremeWindowTicks);

        return (config.desertNoonHeatBonus * Math.max(0.0, noonProximity))
                - (config.desertNightColdBonus * Math.max(0.0, midnightProximity));
    }
}
