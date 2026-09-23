package com.tidal.frostandsilence.temperature;

import com.tidal.frostandsilence.temperature.config.TemperatureConfig;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public final class TemperatureEffects {

    private TemperatureEffects() {
    }

    public static void apply(ServerPlayer player) {

        TemperatureState state = TemperatureManager.getState(player);

        switch (state) {
            case FREEZING -> applyFreezing(player);
            case COLD -> applyCold(player);
            case COMFORTABLE -> {
                // No effect.
            }
            case HOT -> applyHot(player);
            case SCORCHING -> applyScorching(player);
        }

        spawnAmbientParticles(player, state);
    }

    private static void applyFreezing(ServerPlayer player) {

        TemperatureConfig.Values config = TemperatureConfig.VALUES;

        player.addEffect(new MobEffectInstance(
                MobEffects.SLOWNESS,
                config.effectDurationTicks,
                config.freezingSlownessAmplifier,
                false,
                false,
                true
        ));

        if (player.tickCount % config.freezingDamageIntervalTicks == 0) {
            player.hurtServer(player.level(), player.damageSources().freeze(), config.freezingDamage);
        }
    }

    private static void applyCold(ServerPlayer player) {

        TemperatureConfig.Values config = TemperatureConfig.VALUES;

        player.addEffect(new MobEffectInstance(
                MobEffects.SLOWNESS,
                config.effectDurationTicks,
                config.coldSlownessAmplifier,
                false,
                false,
                true
        ));
    }

    private static void applyHot(ServerPlayer player) {

        TemperatureConfig.Values config = TemperatureConfig.VALUES;

        player.addEffect(new MobEffectInstance(
                MobEffects.SLOWNESS,
                config.effectDurationTicks,
                config.hotSlownessAmplifier,
                false,
                false,
                true
        ));
    }

    private static void applyScorching(ServerPlayer player) {

        TemperatureConfig.Values config = TemperatureConfig.VALUES;

        player.addEffect(new MobEffectInstance(
                MobEffects.SLOWNESS,
                config.effectDurationTicks,
                config.scorchingSlownessAmplifier,
                false,
                false,
                true
        ));

        if (player.tickCount % config.scorchingDamageIntervalTicks == 0) {
            player.hurtServer(player.level(), player.damageSources().onFire(), config.scorchingDamage);
        }
    }

    /**
     * Cold states puff visible breath fog near the player's head; hot states
     * give off a light heat shimmer/smoke. Purely cosmetic, server-spawned so
     * every nearby player sees it, throttled by {@code particleIntervalTicks}.
     */
    private static void spawnAmbientParticles(ServerPlayer player, TemperatureState state) {

        TemperatureConfig.Values config = TemperatureConfig.VALUES;

        if (!config.particlesEnabled || state == TemperatureState.COMFORTABLE) {
            return;
        }

        if (player.tickCount % config.particleIntervalTicks != 0) {
            return;
        }

        if (!(player.level() instanceof ServerLevel level)) {
            return;
        }

        double x = player.getX();
        double y = player.getEyeY() - 0.15;
        double z = player.getZ();

        switch (state) {
            case FREEZING, COLD -> level.sendParticles(ParticleTypes.CLOUD, x, y, z, 2, 0.15, 0.05, 0.15, 0.01);
            case HOT, SCORCHING -> level.sendParticles(ParticleTypes.SMOKE, x, y, z, 2, 0.15, 0.15, 0.15, 0.01);
            default -> {
                // No particles for COMFORTABLE.
            }
        }
    }
}
