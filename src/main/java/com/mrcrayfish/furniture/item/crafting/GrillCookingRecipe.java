package com.mrcrayfish.furniture.item.crafting;

import com.mrcrayfish.furniture.core.ModBlocks;
import com.mrcrayfish.furniture.core.ModRecipeSerializers;
import com.mrcrayfish.furniture.core.ModRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

/**
 * Author: MrCrayfish
 */
public class GrillCookingRecipe extends AbstractCookingRecipe
{
    public GrillCookingRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredientIn, ItemStack resultIn, float experienceIn, int cookTimeIn)
    {
        super(ModRecipeTypes.GRILL_COOKING.get(), idIn, groupIn, ingredientIn, resultIn, experienceIn, cookTimeIn);
    }

    @Override
    public ItemStack getToastSymbol()
    {
        return new ItemStack(ModBlocks.GRILL_RED.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ModRecipeSerializers.GRILL_COOKING.get();
    }

    @Override
    public boolean isSpecial()
    {
        return true;
    }
}
