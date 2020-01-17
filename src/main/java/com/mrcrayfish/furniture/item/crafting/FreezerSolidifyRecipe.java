package com.mrcrayfish.furniture.item.crafting;

import com.mrcrayfish.furniture.core.ModBlocks;
import com.mrcrayfish.furniture.core.ModRecipeSerializers;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

/**
 * Author: MrCrayfish
 */
public class FreezerSolidifyRecipe extends AbstractCookingRecipe
{
    public FreezerSolidifyRecipe(ResourceLocation id, String group, Ingredient ingredient, ItemStack stack, float experience, int freezeTime)
    {
        super(RecipeType.FREEZER_SOLIDIFY, id, group, ingredient, stack, experience, freezeTime);
    }

    @Override
    public ItemStack getIcon()
    {
        return new ItemStack(ModBlocks.FREEZER_LIGHT);
    }

    @Override
    public IRecipeSerializer<?> getSerializer()
    {
        return ModRecipeSerializers.FREEZER_SOLIDIFY;
    }
}
