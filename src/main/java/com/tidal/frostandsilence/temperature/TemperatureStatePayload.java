package com.tidal.frostandsilence.temperature;

import com.tidal.frostandsilence.FrostAndSilence;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record TemperatureStatePayload(TemperatureState state, double temperature) implements CustomPacketPayload {

    public static final Identifier ID = FrostAndSilence.id("temperature_state");

    public static final Type<TemperatureStatePayload> TYPE = new Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, TemperatureStatePayload> CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VAR_INT, payload -> payload.state().ordinal(),
                    ByteBufCodecs.DOUBLE, TemperatureStatePayload::temperature,
                    (ordinal, temperature) -> new TemperatureStatePayload(TemperatureState.values()[ordinal], temperature)
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
