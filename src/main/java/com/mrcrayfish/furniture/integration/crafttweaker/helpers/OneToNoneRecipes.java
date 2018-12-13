package com.mrcrayfish.furniture.integration.crafttweaker.helpers;

import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.integration.crafttweaker.CraftTweakerIntegration;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IngredientAny;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.item.MCItemStack;
import stanhebben.zenscript.annotations.Optional;

import javax.annotation.Nonnull;
import java.util.Collection;

public class OneToNoneRecipes
{
    protected final String name;
    protected final Collection<RecipeData> recipes;

    public OneToNoneRecipes(String name, Collection<RecipeData> recipes)
    {
        this.name = name;
        this.recipes = recipes;
    }

    public void remove(@Optional IIngredient item)
    {
        if(item == null) item = IngredientAny.INSTANCE;

        final IIngredient finalItem = item;
        CraftTweakerIntegration.defer("Remove item(s) matching " + item + " from " + this.name, () ->
        {
            if(!this.recipes.removeIf((data) ->
            {
                if(finalItem.matchesExact(new MCItemStack(data.getInput())))
                {
                    CraftTweakerAPI.logInfo(this.name + ": Removed item " + data);
                    return true;
                }
                return false;
            }))
            {
                CraftTweakerAPI.logError(name + ": No item matched");
            }
        });
    }

    public void add(@Nonnull final IItemStack item)
    {
        if(item == null) throw new IllegalArgumentException("item cannot be null");

        final RecipeData data = new RecipeData();
        data.setInput(CraftTweakerMC.getItemStack(item));

        CraftTweakerIntegration.defer("Add item " + data + " to " + this.name, () ->
        {
            this.recipes.add(data);
            CraftTweakerAPI.logInfo(this.name + ": Added item " + data);
        });
    }
}