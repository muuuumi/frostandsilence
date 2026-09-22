package com.tidal.frostandsilence.command;

import com.mojang.brigadier.CommandDispatcher;
import com.tidal.frostandsilence.temperature.TemperatureEnvironment;
import com.tidal.frostandsilence.temperature.TemperatureManager;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import static net.minecraft.commands.Commands.literal;

public final class ModCommands {

    private ModCommands() {
    }

    public static void initialize() {

        CommandRegistrationCallback.EVENT.register(
                (dispatcher, registryAccess, environment) ->
                        register(dispatcher)
        );
    }

    private static void register(
            CommandDispatcher<CommandSourceStack> dispatcher
    ) {

        dispatcher.register(
                literal("temperature")
                        .executes(context -> {

                            ServerPlayer player =
                                    context.getSource().getPlayerOrException();

                            double bodyTemperature =
                                    TemperatureManager.getTemperature(player);

                            double environmentTemperature =
                                    TemperatureEnvironment.getTemperature(player);

                            player.sendSystemMessage(
                                    Component.literal(
                                            "Temperature: %.3f | Environment: %.3f | State: %s"
                                                    .formatted(
                                                            bodyTemperature,
                                                            environmentTemperature,
                                                            TemperatureManager
                                                                    .getState(player)
                                                    )
                                    )
                            );

                            return 1;
                        })
        );
    }
}