package com.tidal.frostandsilence.datagen;


import com.tidal.frostandsilence.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        return new RecipeProvider(registries, output) {
            @Override
            public void buildRecipes() {
                var items = registries.lookupOrThrow(Registries.ITEM);

                SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.CHOCOLATE), RecipeCategory.FOOD, CookingBookCategory.FOOD, ModItems.HOT_CHOCOLATE, 0.1f,200).unlockedBy(getHasName(ModItems.CHOCOLATE), has(ModItems.CHOCOLATE))
                        .save(output, "hot_chocolate_from_smelting");
                SimpleCookingRecipeBuilder.smoking(Ingredient.of(ModItems.CHOCOLATE), RecipeCategory.FOOD, ModItems.HOT_CHOCOLATE, 0.1f,100).unlockedBy(getHasName(ModItems.CHOCOLATE), has(ModItems.CHOCOLATE))
                        .save(output, "hot_chocolate_from_smoking");


                ShapelessRecipeBuilder.shapeless(items, RecipeCategory.FOOD, ModItems.CHOCOLATE,2).requires(Items.COCOA_BEANS).requires(Items.MILK_BUCKET).requires(Items.SUGAR).unlockedBy(getHasName(Items.COCOA_BEANS), has(Items.COCOA_BEANS))
                        .unlockedBy(getHasName(Items.MILK_BUCKET), has(Items.MILK_BUCKET))
                        .unlockedBy(getHasName(Items.SUGAR), has(Items.SUGAR))
                        .save(output, "chocolate_from_crafting");
            }
        };
    }


    @Override
    public String getName() {
        return "";
    }
}
