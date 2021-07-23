package com.mrcrayfish.furniture.item.crafting;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

/**
 * Author: MrCrayfish
 */
public class ModRecipeType
{
    public static final RecipeType<GrillCookingRecipe> GRILL_COOKING = register("cfm:grill_cooking");
    public static final RecipeType<FreezerSolidifyRecipe> FREEZER_SOLIDIFY = register("cfm:freezer_solidify");

    static <T extends Recipe<?>> RecipeType<T> register(final String key)
    {
        return Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(key), new RecipeType<T>()
        {
            public String toString()
            {
                return key;
            }
        });
    }
}
