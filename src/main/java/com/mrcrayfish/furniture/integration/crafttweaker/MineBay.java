package com.mrcrayfish.furniture.integration.crafttweaker;

import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.api.Recipes;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenDoc;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IngredientAny;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nonnull;

@ZenRegister
@ZenClass(CraftTweakerIntegration.CLASS_PREFIX + "MineBay")
public class MineBay {
    @ZenMethod
    @ZenDoc("Remove matching trades.")
    public static void remove(@Optional IIngredient item) {
        if (item == null) item = IngredientAny.INSTANCE;

        final IIngredient finalItem = item;
        CraftTweakerIntegration.defer("Remove trade(s) matching " + item + " from MineBay", () -> {
            if (!Recipes.localMineBayRecipes.removeIf((data) -> {
                if (finalItem.matchesExact(new MCItemStack(data.getInput()))) {
                    CraftTweakerAPI.logInfo("MineBay: Removed trade " + data);
                    return true;
                }
                return false;
            })) {
                CraftTweakerAPI.logError("MineBay: No trades matched " + finalItem);
            }
        });
    }

    @ZenMethod
    @ZenDoc("Add a trade. Price range 1 to 64 (default 1). Currency defaults to Emerald.")
    public static void addTrade(@Nonnull IItemStack item, @Optional Integer price, @Optional IItemStack currency) {
        if (item == null) throw new IllegalArgumentException("item cannot be null");

        final RecipeData data = new RecipeData();
        data.setInput(CraftTweakerMC.getItemStack(item));
        data.setPrice(price == null ? 1 : price);
        data.setCurrency(currency == null ? new ItemStack(Items.EMERALD) : CraftTweakerMC.getItemStack(currency));

        CraftTweakerIntegration.defer("Add trade " + data + " to MineBay", () -> {
            if (data.getPrice() < 1 || data.getPrice() > 64) {
                CraftTweakerAPI.logError("MineBay: Invalid trade price. Must be from 1 to 64.");
                return;
            }
            Recipes.localMineBayRecipes.add(data);
            CraftTweakerAPI.logInfo("MineBay: Added trade " + data);
        });
    }
}
