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

public class OneToOneRecipes {
    protected final String name;
    protected final Collection<RecipeData> recipes;

    public OneToOneRecipes(String name, Collection<RecipeData> recipes) {
        this.name = name;
        this.recipes = recipes;
    }

    public void remove(@Optional IIngredient output, @Optional IIngredient input) {
        if (output == null) output = IngredientAny.INSTANCE;
        if (input == null) input = IngredientAny.INSTANCE;
        final IIngredient finalOutput = output;
        final IIngredient finalInput = input;
        CraftTweakerIntegration.defer("Remove recipe(s) matching " + output + " given " + input + " from " + this.name, () -> {
            if (!this.recipes.removeIf((data) -> {
                if (finalOutput.matchesExact(new MCItemStack(data.getOutput())) && finalInput.matchesExact(new MCItemStack(data.getInput()))) {
                    CraftTweakerAPI.logInfo(this.name + ": Removed recipe " + data);
                    return true;
                }
                return false;
            })) {
                CraftTweakerAPI.logError(name + ": No recipe matched");
            }
        });
    }

    public void addRecipe(@Nonnull final IItemStack output, @Nonnull final IItemStack input) {
        if (output == null) throw new IllegalArgumentException("output cannot be null");

        if (input == null) throw new IllegalArgumentException("input cannot be null");

        final RecipeData data = new RecipeData();
        data.setOutput(CraftTweakerMC.getItemStack(output));
        data.setInput(CraftTweakerMC.getItemStack(input));

        CraftTweakerIntegration.defer("Add recipe " + data + " to " + this.name, () -> {
            this.recipes.add(data);
            CraftTweakerAPI.logInfo(this.name + ": Added recipe " + data);
        });
    }
}
