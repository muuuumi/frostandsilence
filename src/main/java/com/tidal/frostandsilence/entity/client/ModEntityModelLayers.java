package com.tidal.frostandsilence.entity.client;

import com.tidal.frostandsilence.FrostAndSilence;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

public class ModEntityModelLayers {
    public static final ModelLayerLocation PENGUIN = createMain("penguin");

    private static ModelLayerLocation createMain(String name) {
        return new ModelLayerLocation(Identifier.fromNamespaceAndPath(FrostAndSilence.MOD_ID, "penguin"), "main");
    }

    public static void registerModelLayers() {
        ModelLayerRegistry.registerModelLayer(ModEntityModelLayers.PENGUIN, PenguinModel::getTexturedModelData);
    }
}

