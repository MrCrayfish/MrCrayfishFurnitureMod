package com.mrcrayfish.furniture.integration.crafttweaker;

import com.mrcrayfish.furniture.api.Recipes;
import com.mrcrayfish.furniture.integration.crafttweaker.helpers.OneToNoneRecipes;
import crafttweaker.annotations.ZenDoc;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nonnull;

@ZenRegister
@ZenClass(CraftTweakerIntegration.CLASS_PREFIX + "Printer")
public class Printer {
    private static final OneToNoneRecipes recipes = new OneToNoneRecipes("Printer", Recipes.localPrinterRecipes);

    @ZenMethod
    @ZenDoc("Remove matching printable items.")
    public static void remove(@Optional final IIngredient item) {
        recipes.remove(item);
    }

    @ZenMethod
    @ZenDoc("Add printable item.")
    public static void add(@Nonnull final IItemStack item) {
        recipes.add(item);
    }
}
