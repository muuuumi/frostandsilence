package com.tidal.frostandsilence;

import com.tidal.frostandsilence.entity.ModEntities;
import com.tidal.frostandsilence.entity.client.ModEntityModelLayers;
import com.tidal.frostandsilence.entity.client.PenguinRenderer;
import com.tidal.frostandsilence.network.TemperatureSyncPayload;
import com.tidal.frostandsilence.temperature.TemperatureState;
import com.tidal.frostandsilence.temperature.TemperatureStateCalculator;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.Identifier;

public class FrostAndSilenceClient implements ClientModInitializer {

    private static final Identifier TEMPERATURE_HUD =
            Identifier.fromNamespaceAndPath(
                    FrostAndSilence.MOD_ID,
                    "temperature_hud"
            );

    private static final TemperatureStateCalculator STATE_CALCULATOR =
            new TemperatureStateCalculator();

    private static double bodyTemperature = 37.0;
    private static double biomeTemperature = 0.0;
    private static double altitudeModifier = 0.0;
    private static double timeModifier = 0.0;
    private static double weatherModifier = 0.0;
    private static double waterModifier = 0.0;
    private static double environmentalTemperature = 0.0;

    @Override
    public void onInitializeClient() {

        ModEntityModelLayers.registerModelLayers();
        EntityRenderers.register(
                ModEntities.PENGUIN,
                PenguinRenderer::new
        );

        ClientPlayNetworking.registerGlobalReceiver(
                TemperatureSyncPayload.TYPE,
                (payload, context) -> {

                    bodyTemperature =
                            payload.bodyTemperature();

                    biomeTemperature =
                            payload.biomeTemperature();

                    altitudeModifier =
                            payload.altitudeModifier();

                    timeModifier =
                            payload.timeModifier();

                    weatherModifier =
                            payload.weatherModifier();

                    waterModifier =
                            payload.waterModifier();

                    environmentalTemperature =
                            payload.environmentalTemperature();
                }
        );

        HudElementRegistry.addLast(
                TEMPERATURE_HUD,
                (guiGraphics, deltaTracker) -> {

                    Minecraft client =
                            Minecraft.getInstance();

                    if (client.player == null) {
                        return;
                    }

                    TemperatureState temperatureState =
                            STATE_CALCULATOR.calculate(bodyTemperature);

                    String bodyText =
                            String.format(
                                    "Body: %.2f °C",
                                    bodyTemperature
                            );

                    String stateText =
                            "State: " + temperatureState;

                    String environmentText =
                            String.format(
                                    "Environment: %.2f °C",
                                    environmentalTemperature
                            );

                    String biomeText =
                            String.format(
                                    "Biome: %+.2f °C",
                                    biomeTemperature
                            );

                    String altitudeText =
                            String.format(
                                    "Altitude: %+.2f °C",
                                    altitudeModifier
                            );

                    String timeText =
                            String.format(
                                    "Time: %+.2f °C",
                                    timeModifier
                            );

                    String weatherText =
                            String.format(
                                    "Weather: %+.2f °C",
                                    weatherModifier
                            );

                    String waterText =
                            String.format(
                                    "Water: %+.2f °C",
                                    waterModifier
                            );

                    int x = 10;
                    int y = 10;
                    int lineHeight = 10;

                    guiGraphics.text(
                            client.font,
                            bodyText,
                            x,
                            y,
                            0xFFFFFFFF
                    );

                    guiGraphics.text(
                            client.font,
                            stateText,
                            x,
                            y + lineHeight,
                            0xFFFFFFFF
                    );

                    guiGraphics.text(
                            client.font,
                            environmentText,
                            x,
                            y + lineHeight * 2,
                            0xFFFFFFFF
                    );

                    guiGraphics.text(
                            client.font,
                            biomeText,
                            x,
                            y + lineHeight * 3,
                            0xFFFFFFFF
                    );

                    guiGraphics.text(
                            client.font,
                            altitudeText,
                            x,
                            y + lineHeight * 4,
                            0xFFFFFFFF
                    );

                    guiGraphics.text(
                            client.font,
                            timeText,
                            x,
                            y + lineHeight * 5,
                            0xFFFFFFFF
                    );

                    guiGraphics.text(
                            client.font,
                            weatherText,
                            x,
                            y + lineHeight * 6,
                            0xFFFFFFFF
                    );

                    guiGraphics.text(
                            client.font,
                            waterText,
                            x,
                            y + lineHeight * 7,
                            0xFFFFFFFF
                    );
                }
        );
    }
}