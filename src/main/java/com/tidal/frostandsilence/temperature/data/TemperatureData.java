package com.tidal.frostandsilence.temperature.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tidal.frostandsilence.temperature.TemperatureState;
import com.tidal.frostandsilence.temperature.config.TemperatureConfig;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

/**
 * @param temperature body temperature, clamped to [-1, 1]
 */
public record TemperatureData(double temperature) {

    public static final double DEFAULT_TEMPERATURE = 0.0;

    public static final TemperatureData DEFAULT = new TemperatureData(DEFAULT_TEMPERATURE);

    public static final Codec<TemperatureData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.DOUBLE.fieldOf("temperature").forGetter(TemperatureData::temperature)
            ).apply(instance, TemperatureData::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, TemperatureData> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.DOUBLE,
                    TemperatureData::temperature,
                    TemperatureData::new
            );

    public TemperatureData withTemperature(double temperature) {
        return new TemperatureData(temperature);
    }

    public TemperatureState getState() {

        TemperatureConfig.Values config = TemperatureConfig.VALUES;

        if (temperature < config.freezingThreshold) {
            return TemperatureState.FREEZING;
        }

        if (temperature < config.coldThreshold) {
            return TemperatureState.COLD;
        }

        if (temperature > config.scorchingThreshold) {
            return TemperatureState.SCORCHING;
        }

        if (temperature > config.hotThreshold) {
            return TemperatureState.HOT;
        }

        return TemperatureState.COMFORTABLE;
    }
}
