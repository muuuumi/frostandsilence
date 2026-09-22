package com.tidal.frostandsilence;

import com.tidal.frostandsilence.client.TemperatureHud;
import com.tidal.frostandsilence.entity.ModEntities;
import com.tidal.frostandsilence.entity.client.ModEntityModelLayers;
import com.tidal.frostandsilence.entity.client.PenguinRenderer;
import com.tidal.frostandsilence.temperature.TemperatureStatePayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class FrostAndSilenceClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ModEntityModelLayers.registerModelLayers();

        EntityRenderers.register(
                ModEntities.PENGUIN,
                PenguinRenderer::new
        );

        ClientPlayNetworking.registerGlobalReceiver(
                TemperatureStatePayload.TYPE,
                (payload, context) -> TemperatureHud.setState(
                        payload.state()
                )
        );

        TemperatureHud.initialize();
    }
}
