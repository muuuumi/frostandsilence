package com.tidal.frostandsilence.mixin;

import com.tidal.frostandsilence.config.TemperatureConfig;
import com.tidal.frostandsilence.temperature.BodyTemperature;
import com.tidal.frostandsilence.temperature.TemperatureHolder;
import com.tidal.frostandsilence.temperature.thermal.ThermalProperties;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Player.class)
public class PlayerEntityMixin implements TemperatureHolder {

    private final BodyTemperature bodyTemperature =
            new BodyTemperature(
                    TemperatureConfig.NORMAL_BODY_TEMPERATURE,
                    new ThermalProperties(
                            TemperatureConfig.BODY_MASS,
                            TemperatureConfig.BODY_SPECIFIC_HEAT
                    )
            );

    @Override
    public BodyTemperature getBodyTemperature() {
        return bodyTemperature;
    }
}
