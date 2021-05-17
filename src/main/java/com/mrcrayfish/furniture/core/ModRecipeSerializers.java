package com.mrcrayfish.furniture.core;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.item.crafting.FreezerSolidifyRecipe;
import com.mrcrayfish.furniture.item.crafting.GrillCookingRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.CookingHack;
import net.minecraft.item.crafting.CookingRecipeSerializer;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class ModRecipeSerializers
{
    public static final DeferredRegister<IRecipeSerializer<?>> REGISTER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Reference.MOD_ID);

    public static final RegistryObject<CookingRecipeSerializer<GrillCookingRecipe>> GRILL_COOKING = register("grill_cooking", CookingHack.createCookingSerializer(GrillCookingRecipe::new, 100));
    public static final RegistryObject<CookingRecipeSerializer<FreezerSolidifyRecipe>> FREEZER_SOLIDIFY = register("freezer_solidify", CookingHack.createCookingSerializer(FreezerSolidifyRecipe::new, 100));

    private static <T extends IRecipeSerializer<? extends IRecipe<?>>> RegistryObject<T> register(String name, T t)
    {
        return REGISTER.register(name, () -> t);
    }
}
