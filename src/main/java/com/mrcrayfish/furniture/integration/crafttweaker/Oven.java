package com.mrcrayfish.furniture.integration.crafttweaker;

import com.mrcrayfish.furniture.api.Recipes;
import com.mrcrayfish.furniture.integration.crafttweaker.helpers.OneToOneRecipes;
import crafttweaker.annotations.ZenDoc;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nonnull;

@ZenRegister
@ZenClass(CraftTweakerIntegration.CLASS_PREFIX + "Oven")
public class Oven
{
    private static final OneToOneRecipes recipes = new OneToOneRecipes("Oven", Recipes.localOvenRecipes);

    @ZenMethod
    @ZenDoc("Remove matching oven recipes.")
    public static void remove(@Optional final IIngredient output, @Optional final IIngredient input)
    {
        recipes.remove(output, input);
    }

    @ZenMethod
    @ZenDoc("Add an oven recipe.")
    public static void addRecipe(@Nonnull final IItemStack output, @Nonnull final IItemStack input)
    {
        recipes.addRecipe(output, input);
    }
}