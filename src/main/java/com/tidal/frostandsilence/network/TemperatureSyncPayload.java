package com.tidal.frostandsilence.network;

import com.tidal.frostandsilence.FrostAndSilence;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record TemperatureSyncPayload(
        double bodyTemperature,
        double biomeTemperature,
        double altitudeModifier,
        double timeModifier,
        double weatherModifier,
        double waterModifier,
        double environmentalTemperature
) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<TemperatureSyncPayload> TYPE =
            new CustomPacketPayload.Type<>(
                    Identifier.fromNamespaceAndPath(
                            FrostAndSilence.MOD_ID,
                            "temperature_sync"
                    )
            );

    public static final StreamCodec<ByteBuf, TemperatureSyncPayload> CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.DOUBLE,
                    TemperatureSyncPayload::bodyTemperature,

                    ByteBufCodecs.DOUBLE,
                    TemperatureSyncPayload::biomeTemperature,

                    ByteBufCodecs.DOUBLE,
                    TemperatureSyncPayload::altitudeModifier,

                    ByteBufCodecs.DOUBLE,
                    TemperatureSyncPayload::timeModifier,

                    ByteBufCodecs.DOUBLE,
                    TemperatureSyncPayload::weatherModifier,

                    ByteBufCodecs.DOUBLE,
                    TemperatureSyncPayload::waterModifier,

                    ByteBufCodecs.DOUBLE,
                    TemperatureSyncPayload::environmentalTemperature,

                    TemperatureSyncPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
