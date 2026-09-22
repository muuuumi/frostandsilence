package com.tidal.frostandsilence.temperature;

import com.tidal.frostandsilence.FrostAndSilence;
import com.tidal.frostandsilence.temperature.data.TemperatureData;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;

public final class TemperatureAttachments {

    private TemperatureAttachments() {
    }

    public static final AttachmentType<TemperatureData> TEMPERATURE = AttachmentRegistry.create(
            FrostAndSilence.id("temperature"),
            builder -> builder
                    .initializer(() -> TemperatureData.DEFAULT)
                    .persistent(TemperatureData.CODEC)
                    .copyOnDeath()
                    .syncWith(TemperatureData.STREAM_CODEC, AttachmentSyncPredicate.targetOnly())
    );

    public static void initialize() {
    }
}
