package com.tidal.frostandsilence.mixin;

import com.tidal.frostandsilence.temperature.TemperatureFood;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Hooks the vanilla "finished using" method so {@link TemperatureFood} can
 * react to whatever food item was just consumed.
 */
@Mixin(ItemStack.class)
public abstract class LivingEntityEatMixin {

    @Inject(
            method = "finishUsingItem(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/world/item/ItemStack;",
            at = @At("HEAD")
    )
    private void frostandsilence$onEat(
            Level level,
            LivingEntity consumer,
            CallbackInfoReturnable<ItemStack> cir
    ) {
        if (consumer instanceof ServerPlayer player) {
            TemperatureFood.onEaten(player, (ItemStack) (Object) this);
        }
    }
}
