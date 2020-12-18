package net.minecraft.item.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * Dirty hack to create cooking recipes serializers.
 *
 * @author Ocelot
 */
public class CookingHack
{
    public static <T extends AbstractCookingRecipe> CookingRecipeSerializer<T> createCookingSerializer(IFactory<T> factory, int cookingTime)
    {
        return new CookingRecipeSerializer<>(factory::create, cookingTime);
    }

    public interface IFactory<T extends AbstractCookingRecipe>
    {
        T create(ResourceLocation p_create_1_, String p_create_2_, Ingredient p_create_3_, ItemStack p_create_4_, float p_create_5_, int p_create_6_);
    }
}
