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
@ZenClass(CraftTweakerIntegration.CLASS_PREFIX + "Toaster")
public class Toaster
{
    private static final OneToOneRecipes recipes = new OneToOneRecipes("Toaster", Recipes.localToasterRecipes);

    @ZenMethod
    @ZenDoc("Remove matching toaster recipes.")
    public static void remove(@Optional final IIngredient output, @Optional final IIngredient input)
    {
        recipes.remove(output, input);
    }

    @ZenMethod
    @ZenDoc("Add a toaster recipe.")
    public static void addRecipe(@Nonnull final IItemStack output, @Nonnull final IItemStack input)
    {
        recipes.addRecipe(output, input);
    }
}