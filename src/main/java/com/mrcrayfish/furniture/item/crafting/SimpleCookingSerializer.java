package com.mrcrayfish.furniture.item.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;

/**
 * Author: MrCrayfish
 */
public class SimpleCookingSerializer<T extends AbstractCookingRecipe> extends net.minecraft.world.item.crafting.SimpleCookingSerializer<T>
{
    private final Factory<T> factory;
    private final int cookingTime;

    public SimpleCookingSerializer(Factory<T> factory, int cookingTime)
    {
        super(null, cookingTime);
        this.factory = factory;
        this.cookingTime = cookingTime;
    }

    @Override
    public T fromJson(ResourceLocation id, JsonObject object)
    {
        String group = GsonHelper.getAsString(object, "group", "");
        JsonElement element = (GsonHelper.isArrayNode(object, "ingredient") ? GsonHelper.getAsJsonArray(object, "ingredient") : GsonHelper.getAsJsonObject(object, "ingredient"));
        Ingredient ingredient = Ingredient.fromJson(element);
        if(!object.has("result"))
            throw new com.google.gson.JsonSyntaxException("Missing result, expected to find a string or object");
        ItemStack result = this.getResult(object);
        float experience = GsonHelper.getAsFloat(object, "experience", 0.0F);
        int cookingTime = GsonHelper.getAsInt(object, "cookingtime", this.cookingTime);
        return this.factory.create(id, group, ingredient, result, experience, cookingTime);
    }

    private ItemStack getResult(JsonObject object)
    {
        if(object.get("result").isJsonObject())
            return ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(object, "result"));
        String rawResult = GsonHelper.getAsString(object, "result");
        ResourceLocation resultId = new ResourceLocation(rawResult);
        return new ItemStack(Registry.ITEM.getOptional(resultId).orElseThrow(() -> new IllegalStateException("Item: " + rawResult + " does not exist")));
    }

    @Override
    public T fromNetwork(ResourceLocation id, FriendlyByteBuf buf)
    {
        return this.factory.create(id, buf.readUtf(), Ingredient.fromNetwork(buf), buf.readItem(), buf.readFloat(), buf.readVarInt());
    }

    public interface Factory<T extends AbstractCookingRecipe>
    {
        T create(ResourceLocation id, String group, Ingredient ingredient, ItemStack result, float experience, int cookingTime);
    }
}
