package com.tidal.frostandsilence;

import com.tidal.frostandsilence.client.TemperatureClientInitializer;
import com.tidal.frostandsilence.entity.ModEntities;
import com.tidal.frostandsilence.entity.client.ModEntityModelLayers;
import com.tidal.frostandsilence.entity.client.PenguinRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class FrostAndSilenceClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ModEntityModelLayers.registerModelLayers();

        EntityRenderers.register(
                ModEntities.PENGUIN,
                PenguinRenderer::new
        );

        TemperatureClientInitializer.initialize();
    }
}
