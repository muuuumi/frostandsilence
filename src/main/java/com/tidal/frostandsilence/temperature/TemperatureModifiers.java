package com.tidal.frostandsilence.temperature;

import com.tidal.frostandsilence.temperature.config.TemperatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;

/**
 * Cold and heat contributions from the player's immediate surroundings
 * (standing in water/powder snow, or being near fire/lava/campfires).
 */
public final class TemperatureModifiers {

    private TemperatureModifiers() {
    }

    public static double getColdEffect(ServerPlayer player, double currentTemperature) {

        if (player.isInPowderSnow) {
            return -TemperatureConfig.VALUES.powderSnowCold;
        }

        if (player.isInWater()) {
            return -TemperatureConfig.VALUES.waterCold;
        }

        return 0.0;
    }

    public static double getHeat(ServerPlayer player) {

        TemperatureConfig.Values config = TemperatureConfig.VALUES;
        BlockPos playerPos = player.blockPosition();

        double heat = 0.0;

        for (BlockPos pos : BlockPos.betweenClosed(
                playerPos.offset(-config.heatSearchRadiusHorizontal, -config.heatSearchRadiusVertical, -config.heatSearchRadiusHorizontal),
                playerPos.offset(config.heatSearchRadiusHorizontal, config.heatSearchRadiusVertical, config.heatSearchRadiusHorizontal)
        )) {

            var state = player.level().getBlockState(pos);

            if (state.is(Blocks.CAMPFIRE) && state.getValue(CampfireBlock.LIT)) {
                heat = Math.max(heat, config.campfireHeat);
            }

            if (state.is(Blocks.SOUL_CAMPFIRE) && state.getValue(CampfireBlock.LIT)) {
                heat = Math.max(heat, config.campfireHeat + config.soulCampfireBonus);
            }

            if (state.is(Blocks.FIRE)) {
                heat = Math.max(heat, config.fireHeat);
            }

            if (state.is(Blocks.SOUL_FIRE)) {
                heat = Math.max(heat, config.fireHeat);
            }

            if (state.is(Blocks.LAVA)) {
                heat = Math.max(heat, config.lavaHeat);
            }
        }

        return heat;
    }
}
