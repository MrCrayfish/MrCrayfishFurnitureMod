package com.mrcrayfish.furniture.core;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.item.crafting.CookingRecipeSerializer;
import com.mrcrayfish.furniture.item.crafting.GrillCookingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModRecipeSerializers
{
    private static final List<IRecipeSerializer> RECIPES = new ArrayList<>();

    public static final CookingRecipeSerializer<GrillCookingRecipe> GRILL_COOKING = register("cfm:grill_cooking", new CookingRecipeSerializer<>(GrillCookingRecipe::new, 100));

    private static <T extends IRecipeSerializer<? extends IRecipe<?>>> T register(String name, T t)
    {
        t.setRegistryName(new ResourceLocation(name));
        RECIPES.add(t);
        return t;
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void registerItems(final RegistryEvent.Register<IRecipeSerializer<?>> event)
    {
        RECIPES.forEach(item -> event.getRegistry().register(item));
        RECIPES.clear();
    }
}
