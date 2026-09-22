package com.tidal.frostandsilence.temperature;

import com.tidal.frostandsilence.FrostAndSilence;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record TemperatureStatePayload(TemperatureState state) implements CustomPacketPayload {

    public static final Identifier ID = FrostAndSilence.id("temperature_state");

    public static final Type<TemperatureStatePayload> TYPE = new Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, TemperatureStatePayload> CODEC =
            new StreamCodec<>() {

                @Override
                public TemperatureStatePayload decode(RegistryFriendlyByteBuf buf) {
                    return new TemperatureStatePayload(TemperatureState.values()[buf.readVarInt()]);
                }

                @Override
                public void encode(RegistryFriendlyByteBuf buf, TemperatureStatePayload payload) {
                    buf.writeVarInt(payload.state().ordinal());
                }
            };

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
