package com.mrcrayfish.furniture.item.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public class CookingRecipeSerializer<T extends AbstractCookingRecipe> extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T>
{
    private final IFactory<T> factory;
    private final int fallbackCookTime;

    public CookingRecipeSerializer(IFactory<T> factory, int fallbackCookTime)
    {
        this.factory = factory;
        this.fallbackCookTime = fallbackCookTime;
    }

    @Override
    public T read(ResourceLocation recipeId, JsonObject json)
    {
        String s = JSONUtils.getString(json, "group", "");
        JsonElement element = JSONUtils.getJsonObject(json, "ingredient");
        Ingredient ingredient = Ingredient.deserialize(element);
        if(!json.has("result"))
        {
            throw new com.google.gson.JsonSyntaxException("Missing result, expected to find a string or object");
        }
        String result = JSONUtils.getString(json, "result");
        ResourceLocation location = new ResourceLocation(result);
        ItemStack stack = new ItemStack(Optional.ofNullable(ForgeRegistries.ITEMS.getValue(location)).orElseThrow(() -> new IllegalStateException("Item: " + result + " does not exist")));
        float experience = JSONUtils.getFloat(json, "experience", 0.0F);
        int cookTime = JSONUtils.getInt(json, "cookingtime", this.fallbackCookTime);
        return this.factory.create(recipeId, s, ingredient, stack, experience, cookTime);
    }

    @Nullable
    @Override
    public T read(ResourceLocation recipeId, PacketBuffer buffer)
    {
        String group = buffer.readString(32767);
        Ingredient ingredient = Ingredient.read(buffer);
        ItemStack stack = buffer.readItemStack();
        float experience = buffer.readFloat();
        int cookTime = buffer.readVarInt();
        return this.factory.create(recipeId, group, ingredient, stack, experience, cookTime);
    }

    @Override
    public void write(PacketBuffer buffer, T recipe)
    {
        buffer.writeString(recipe.getGroup());
        recipe.getIngredients().get(0).write(buffer);
        buffer.writeItemStack(recipe.getRecipeOutput());
        buffer.writeFloat(recipe.getExperience());
        buffer.writeVarInt(recipe.getCookTime());
    }

    public interface IFactory<T extends AbstractCookingRecipe>
    {
        T create(ResourceLocation location, String group, Ingredient ingredient, ItemStack result, float experience, int cookTime);
    }
}
