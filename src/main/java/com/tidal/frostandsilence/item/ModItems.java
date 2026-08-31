package com.tidal.frostandsilence.item;

import com.tidal.frostandsilence.FrostAndSilence;
import com.tidal.frostandsilence.food.ModFoods;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class ModItems {
    public static final Item SNOWFLAKE = registerItem("snowflake", Item::new);
    public static final Item CHOCOLATE = registerItem("chocolate", properties -> new Item(properties.food(ModFoods.CHOCOLATE, ModFoods.CHOCOLATE_CONSUMABLE)));
    public static final Item HOT_CHOCOLATE = registerItem("hot_chocolate", properties -> new Item(properties.food(ModFoods.HOT_CHOCOLATE, ModFoods.HOT_CHOCOLATE_CONSUMABLE)));

    private static Item registerItem(String name, Function<Item.Properties, Item> function) {
        return Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(FrostAndSilence.MOD_ID, name),
                function.apply(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FrostAndSilence.MOD_ID, name)))));
    }

    public static void registerModItems() {
        FrostAndSilence.LOGGER.info("Registering Mod Items for " + FrostAndSilence.MOD_ID);
    }
}
