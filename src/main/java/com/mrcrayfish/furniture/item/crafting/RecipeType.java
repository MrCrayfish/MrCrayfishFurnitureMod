package com.mrcrayfish.furniture.item.crafting;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

/**
 * Author: MrCrayfish
 */
public class RecipeType
{
    public static final IRecipeType<GrillCookingRecipe> GRILL_COOKING = register("cfm:grill_cooking");
    public static final IRecipeType<FreezerSolidifyRecipe> FREEZER_SOLIDIFY = register("cfm:freezer_solidify");

    static <T extends IRecipe<?>> IRecipeType<T> register(final String key)
    {
        return Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(key), new IRecipeType<T>()
        {
            public String toString()
            {
                return key;
            }
        });
    }
}
