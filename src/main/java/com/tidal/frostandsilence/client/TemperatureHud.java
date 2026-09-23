package com.tidal.frostandsilence.client;

import com.tidal.frostandsilence.FrostAndSilence;
import com.tidal.frostandsilence.temperature.TemperatureState;
import com.tidal.frostandsilence.temperature.config.TemperatureConfig;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public final class TemperatureHud {

    private TemperatureHud() {
    }

    private static final Identifier COLD_TEXTURE = FrostAndSilence.id("textures/gui/temperature_cold.png");
    private static final Identifier HOT_TEXTURE = FrostAndSilence.id("textures/gui/temperature_hot.png");

    // Blue / orange vignette tints, alpha is scaled per-frame by intensity.
    private static final int COLD_TINT = 0x2050A0FF;
    private static final int HOT_TINT = 0xD0500AFF;

    // Vignette tuning.
    private static final double VIGNETTE_MAX_REACH = 0.30;     // fraction of min(width, height)
    private static final double VIGNETTE_FALLOFF_POWER = 2.0;  // higher = tighter near edge

    private static TemperatureState currentState = TemperatureState.COMFORTABLE;
    private static double currentTemperature = 0.0;

    public static void initialize() {
        HudElementRegistry.addLast(FrostAndSilence.id("temperature_hud"), TemperatureHud::render);
    }

    /**
     * Called by {@link TemperatureClientInitializer} whenever the server sends
     * an updated {@code TemperatureStatePayload}.
     */
    public static void update(TemperatureState state, double temperature) {
        currentState = state;
        currentTemperature = temperature;
    }

    private static void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {

        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player == null || currentState == TemperatureState.COMFORTABLE) {
            return;
        }

        TemperatureConfig.Values config = TemperatureConfig.VALUES;
        boolean isCold = currentState == TemperatureState.FREEZING || currentState == TemperatureState.COLD;

        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();

        if (config.vignetteEnabled) {
            renderVignette(graphics, screenWidth, screenHeight, config, isCold);
        }

        renderIcon(graphics, screenWidth, screenHeight, isCold);
    }

    private static void renderVignette(GuiGraphicsExtractor graphics, int width, int height,
                                       TemperatureConfig.Values config, boolean isCold) {

        double intensity = isCold
                ? normalize(currentTemperature, config.coldThreshold, config.freezingThreshold)
                : normalize(currentTemperature, config.hotThreshold, config.scorchingThreshold);

        if (intensity <= 0.0) {
            return;
        }

        int baseColor = isCold ? COLD_TINT : HOT_TINT;
        int maxAlpha = (baseColor >>> 24) & 0xFF;
        int peakAlpha = (int) (maxAlpha * Math.min(1.0, intensity));
        int rgb = baseColor & 0x00FFFFFF;

        if (peakAlpha <= 0) {
            return;
        }

        // How far the vignette reaches in from each edge. Scales with GUI size
        // so it looks the same at every GUI scale.
        int minDim = Math.min(width, height);
        int thickness = (int) (minDim * VIGNETTE_MAX_REACH * Math.min(1.0, intensity));
        thickness = Math.max(1, Math.min(thickness, minDim / 2));

        // Smooth falloff: each edge is drawn as 1px bands whose alpha follows a
        // smooth curve from peakAlpha at the edge to 0 at the inner limit.
        // Corners come out darker automatically because the bands overlap.

        // Top edge
        for (int i = 0; i < thickness; i++) {
            int alpha = bandAlpha(peakAlpha, i, thickness);
            if (alpha <= 0) {
                continue;
            }
            graphics.fill(0, i, width, i + 1, (alpha << 24) | rgb);
        }

        // Bottom edge
        for (int i = 0; i < thickness; i++) {
            int alpha = bandAlpha(peakAlpha, i, thickness);
            if (alpha <= 0) {
                continue;
            }
            int y = height - 1 - i;
            graphics.fill(0, y, width, y + 1, (alpha << 24) | rgb);
        }

        // Left edge
        for (int i = 0; i < thickness; i++) {
            int alpha = bandAlpha(peakAlpha, i, thickness);
            if (alpha <= 0) {
                continue;
            }
            graphics.fill(i, 0, i + 1, height, (alpha << 24) | rgb);
        }

        // Right edge
        for (int i = 0; i < thickness; i++) {
            int alpha = bandAlpha(peakAlpha, i, thickness);
            if (alpha <= 0) {
                continue;
            }
            int x = width - 1 - i;
            graphics.fill(x, 0, x + 1, height, (alpha << 24) | rgb);
        }
    }

    /**
     * Alpha for a vignette band {@code i} pixels in from an edge, where the
     * vignette extends {@code thickness} pixels inward.
     * <p>
     * {@code i == 0} => {@code peakAlpha} (darkest, right at the edge).<br>
     * {@code i == thickness} => 0 (fully transparent, blends into the screen).
     * <p>
     * Uses a power falloff curve, which looks far smoother than hard rectangles
     * and avoids the "chunky" banding of a few big fills.
     */
    private static int bandAlpha(int peakAlpha, int i, int thickness) {
        double t = (double) i / thickness;          // 0 at edge, 1 at inner limit
        double f = 1.0 - t;
        double curve = Math.pow(f, VIGNETTE_FALLOFF_POWER);
        return (int) (peakAlpha * curve);
    }

    private static void renderIcon(
            GuiGraphicsExtractor graphics,
            int screenWidth,
            int screenHeight,
            boolean isCold
    ) {

        Identifier texture = isCold ? COLD_TEXTURE : HOT_TEXTURE;
        int width = isCold ? 25 : 14;

        int x = screenWidth / 2 - 14;
        int y = screenHeight - 60;

        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                texture,
                x,
                y,
                0,
                0,
                width,
                25,
                width,
                25
        );
    }

    /**
     * 0 at {@code start} (state just entered), 1 at {@code end} (deepest extreme).
     */
    private static double normalize(double value, double start, double end) {
        if (end == start) {
            return 0.0;
        }
        return Math.clamp((value - start) / (end - start), 0.0, 1.0);
    }
}