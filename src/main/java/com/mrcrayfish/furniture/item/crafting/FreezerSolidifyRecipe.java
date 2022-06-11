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
public class FreezerSolidifyRecipe extends AbstractCookingRecipe
{
    public FreezerSolidifyRecipe(ResourceLocation id, String group, Ingredient ingredient, ItemStack stack, float experience, int freezeTime)
    {
        super(ModRecipeTypes.FREEZER_SOLIDIFY.get(), id, group, ingredient, stack, experience, freezeTime);
    }

    @Override
    public ItemStack getToastSymbol()
    {
        return new ItemStack(ModBlocks.FREEZER_LIGHT.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ModRecipeSerializers.FREEZER_SOLIDIFY.get();
    }

    @Override
    public boolean isSpecial()
    {
        return true;
    }
}
