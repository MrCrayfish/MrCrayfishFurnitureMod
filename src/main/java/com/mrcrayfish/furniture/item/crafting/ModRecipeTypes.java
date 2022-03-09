package com.mrcrayfish.furniture.item.crafting;

import net.minecraft.world.item.crafting.RecipeType;

/**
 * Author: MrCrayfish
 */
public class ModRecipeTypes
{
    public static final RecipeType<GrillCookingRecipe> GRILL_COOKING = RecipeType.register("cfm:grill_cooking");
    public static final RecipeType<FreezerSolidifyRecipe> FREEZER_SOLIDIFY = RecipeType.register("cfm:freezer_solidify");

    // Does nothing but trigger loading of static fields
    public static void init() {}
}
