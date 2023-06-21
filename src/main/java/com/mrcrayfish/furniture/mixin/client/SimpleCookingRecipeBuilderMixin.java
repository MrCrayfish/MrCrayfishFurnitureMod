package com.mrcrayfish.furniture.mixin.client;

import com.mrcrayfish.furniture.core.ModRecipeSerializers;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Author: MrCrayfish
 */
@Mixin(SimpleCookingRecipeBuilder.class)
public class SimpleCookingRecipeBuilderMixin
{
    @Inject(method = "determineRecipeCategory", at = @At(value = "HEAD"), cancellable = true)
    private static void cfmRecipeCategory(RecipeSerializer<? extends AbstractCookingRecipe> serializer, ItemLike like, CallbackInfoReturnable<CookingBookCategory> cir)
    {
        if(serializer == ModRecipeSerializers.GRILL_COOKING.get())
        {
            cir.setReturnValue(CookingBookCategory.FOOD);
        }
        else if(serializer == ModRecipeSerializers.FREEZER_SOLIDIFY.get())
        {
            cir.setReturnValue(CookingBookCategory.MISC);
        }
    }
}
