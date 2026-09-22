package com.tidal.frostandsilence.client;

import com.tidal.frostandsilence.FrostAndSilence;
import com.tidal.frostandsilence.temperature.TemperatureState;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public final class TemperatureHud {

    private TemperatureHud() {
    }

    private static final Identifier COLD_TEXTURE =
            FrostAndSilence.id("textures/gui/temperature_cold.png");

    private static final Identifier HOT_TEXTURE =
            FrostAndSilence.id("textures/gui/temperature_hot.png");

    private static final int SLOT_WIDTH = 25;
    private static final int SLOT_HEIGHT = 25;

    private static final int COLD_WIDTH = 25;
    private static final int COLD_HEIGHT = 25;

    private static final int HOT_WIDTH = 14;
    private static final int HOT_HEIGHT = 25;

    private static final int Y_OFFSET = 60;

    private static TemperatureState currentState =
            TemperatureState.COMFORTABLE;

    public static void initialize() {
        HudElementRegistry.addLast(
                FrostAndSilence.id("temperature_hud"),
                TemperatureHud::render
        );
    }

    public static void setState(TemperatureState state) {
        currentState = state;
    }

    private static void render(
            net.minecraft.client.gui.GuiGraphicsExtractor graphics,
            DeltaTracker deltaTracker
    ) {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player == null || currentState == TemperatureState.COMFORTABLE) {
            return;
        }

        int guiWidth = minecraft.getWindow().getGuiScaledWidth();
        int guiHeight = minecraft.getWindow().getGuiScaledHeight();

        int slotX = guiWidth / 2 - SLOT_WIDTH / 2;
        int slotY = guiHeight - Y_OFFSET;

        switch (currentState) {
            case FREEZING, COLD -> {
                blit(
                        graphics,
                        COLD_TEXTURE,
                        slotX,
                        slotY,
                        COLD_WIDTH,
                        COLD_HEIGHT
                );
            }
            case HOT, SCORCHING -> {
                int hotX = slotX + (SLOT_WIDTH - HOT_WIDTH) / 2;

                blit(
                        graphics,
                        HOT_TEXTURE,
                        hotX,
                        slotY,
                        HOT_WIDTH,
                        HOT_HEIGHT
                );
            }
            case COMFORTABLE -> {
            }
        }
    }

    private static void blit(
            net.minecraft.client.gui.GuiGraphicsExtractor graphics,
            Identifier texture,
            int x,
            int y,
            int width,
            int height
    ) {
        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                texture,
                x,
                y,
                0,
                0,
                width,
                height,
                width,
                height
        );
    }
}