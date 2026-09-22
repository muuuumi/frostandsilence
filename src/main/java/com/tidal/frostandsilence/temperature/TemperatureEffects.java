package com.tidal.frostandsilence.temperature;

import com.tidal.frostandsilence.temperature.config.TemperatureConfig;
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
}
