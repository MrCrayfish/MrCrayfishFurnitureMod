package com.mrcrayfish.furniture.core;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.item.crafting.FreezerSolidifyRecipe;
import com.mrcrayfish.furniture.item.crafting.GrillCookingRecipe;
import com.mrcrayfish.furniture.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class ModRecipeSerializers
{
    public static final DeferredRegister<RecipeSerializer<?>> REGISTER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Reference.MOD_ID);

    public static final RegistryObject<SimpleCookingSerializer<GrillCookingRecipe>> GRILL_COOKING = register("grill_cooking", () -> new SimpleCookingSerializer<>(GrillCookingRecipe::new, 100));
    public static final RegistryObject<SimpleCookingSerializer<FreezerSolidifyRecipe>> FREEZER_SOLIDIFY = register("freezer_solidify", () -> new SimpleCookingSerializer<>(FreezerSolidifyRecipe::new, 100));

    private static <T extends RecipeSerializer<? extends Recipe<?>>> RegistryObject<T> register(String name, Supplier<T> serializer)
    {
        return REGISTER.register(name, serializer);
    }
}
