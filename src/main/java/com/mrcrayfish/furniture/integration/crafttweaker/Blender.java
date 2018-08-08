package com.mrcrayfish.furniture.integration.crafttweaker;

import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.api.Recipes;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenDoc;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Predicate;

@ZenRegister
@ZenClass(CraftTweakerIntegration.CLASS_PREFIX + "Blender")
public class Blender
{
    @ZenMethod
    @ZenDoc("Remove matching blended drinks.")
    public static void remove(@Optional final String name, @Optional final IItemStack[] ingredients, @Optional final Integer food, @Optional final int[] colour)
    {
        final StringBuilder description = new StringBuilder();
        Predicate<RecipeData> matcher = recipeData -> true;

        boolean first = true;

        description.append("Remove drink(s) matching '");

        if(name != null)
        {
            matcher = matcher.and((data) -> data.getDrinkName().equals(name));
            if(first) first = false;
            else description.append(',');
            description.append("name=" + name);
        }

        if(ingredients != null)
        {
            matcher = matcher.and((data) ->
            {
                if(data.getIngredients().size() != ingredients.length) return false;
                LinkedList<IItemStack> toCheck = new LinkedList<>();

                Collections.addAll(toCheck, ingredients);

                outer:
                for(ItemStack stack : data.getIngredients())
                {
                    for(Iterator<IItemStack> it = toCheck.iterator(); it.hasNext(); )
                    {
                        IItemStack checker = it.next();
                        if(CraftTweakerMC.matchesExact(checker, stack))
                        {
                            it.remove();
                            continue outer;
                        }
                    }
                    return false;
                }
                return true;
            });
            if(first) first = false;
            else description.append(',');
            description.append("ingredients=" + Arrays.toString(ingredients));
        }

        if(food != null)
        {
            matcher = matcher.and((data) -> data.getHealAmount() == food);
            if(first) first = false;
            else description.append(',');
            description.append("food=" + food);
        }

        if(colour != null)
        {
            if(colour.length != 3)
                throw new IllegalArgumentException("colour must have 3 components");
            for(int c : colour)
                if(c < 0 || c > 255)
                    throw new IllegalArgumentException("colour components must be between 0 and 255 inclusive");
            matcher = matcher.and((data) -> data.getRed() == colour[0] && data.getGreen() == colour[1] && data.getBlue() == colour[2]);
            if(first) first = false;
            else description.append(',');
            description.append("colour=" + Arrays.toString(colour));
        }

        description.append("' from Blender");

        if(first)
        {
            description.setLength(0);
            description.append("Remove all drinks from Blender");
        }

        final Predicate<RecipeData> finalMatcher = matcher;
        CraftTweakerIntegration.defer(description.toString(), () ->
        {
            if(!Recipes.localBlenderRecipes.removeIf((data) ->
            {
                if(finalMatcher.test(data))
                {
                    CraftTweakerAPI.logInfo("Blender: Removed blended drink " + data);
                    return true;
                }
                return false;
            }))
            {
                CraftTweakerAPI.logError("Blender: No blended drinks matched");
            }
        });
    }

    @ZenMethod
    @ZenDoc("Add a blended drink.")
    public static void addDrink(@Nonnull final String name, @Nonnull final IItemStack[] ingredients, final int food, @Nonnull final int[] colour)
    {
        if(name == null) throw new IllegalArgumentException("name cannot be null");
        if(ingredients == null) throw new IllegalArgumentException("ingredients cannot be null");
        if(food < 0) throw new IllegalArgumentException("food value must be non-negative");
        if(colour == null) throw new IllegalArgumentException("colour cannot be null");
        if(colour.length != 3) throw new IllegalArgumentException("colour must have 3 components");
        for(int c : colour)
            if(c < 0 || c > 255)
                throw new IllegalArgumentException("colour components must be between 0 and 255 inclusive");

        final RecipeData data = new RecipeData();
        data.setName(name);
        for(IItemStack i : ingredients)
        {
            data.addIngredient(CraftTweakerMC.getItemStack(i));
        }
        data.setHeal(food);
        data.setColour(colour[0], colour[1], colour[2]);

        CraftTweakerIntegration.defer("Add blended drink " + data + " to Blender", () ->
        {
            Recipes.localBlenderRecipes.add(data);
            CraftTweakerAPI.logInfo("Blender: Added trade " + data);
        });
    }
}
