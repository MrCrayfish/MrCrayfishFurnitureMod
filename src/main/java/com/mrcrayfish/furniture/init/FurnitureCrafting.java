/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2016  MrCrayfish (http://www.mrcrayfish.com/)
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mrcrayfish.furniture.init;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.google.gson.JsonObject;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.handler.FuelHandler;
import com.mrcrayfish.furniture.util.RecipeUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class FurnitureCrafting
{
    public static void register()
    {
        GameRegistry.addSmelting(FurnitureItems.itemFlesh, new ItemStack(FurnitureItems.itemCookedFlesh), 0.05F);
        GameRegistry.registerFuelHandler(new FuelHandler());
        RegistrationHandler.RECIPES.add(new RecipeSoapyWater().setRegistryName("cfm:recipe_soapy_water"));
    }

    private static class RecipeSoapyWater extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<IRecipe> implements IRecipe
    {
        @Override
        public boolean matches(InventoryCrafting inv, World worldIn)
        {
            int size = 0;

            for(int i = 0; i < inv.getSizeInventory(); i++)
            {
                ItemStack stack = inv.getStackInSlot(i);
                if(!stack.isEmpty())
                {
                    size++;
                }
            }

            for(int i = 0; i < inv.getSizeInventory() - inv.getWidth(); i++)
            {
                ItemStack above = inv.getStackInSlot(i);
                if(!above.isEmpty() && above.getItem() == FurnitureItems.itemSoap)
                {
                    ItemStack below = inv.getStackInSlot(i + inv.getWidth());
                    if(!below.isEmpty() && below.getItem() == Items.WATER_BUCKET)
                    {
                        return size == 2;
                    }
                }
            }
            return false;
        }

        @Override
        public ItemStack getCraftingResult(InventoryCrafting inv)
        {
            return new ItemStack(FurnitureItems.itemSoapyWater);
        }

        @Override
        public boolean canFit(int width, int height)
        {
            return width > 0 && height > 1;
        }

        @Override
        public ItemStack getRecipeOutput()
        {
            return new ItemStack(FurnitureItems.itemSoapyWater);
        }

        @Override
        public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
        {
            NonNullList<ItemStack> items = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);

            for(int i = 0; i < inv.getSizeInventory(); i++)
            {
                ItemStack stack = inv.getStackInSlot(i);
                if(!stack.isEmpty() && stack.getItem() == FurnitureItems.itemSoap)
                {
                    ItemStack soap = stack.copy();
                    soap.shrink(1);
                    items.set(i, soap);
                    break;
                }
            }

            inv.clear();

            return items;
        }
    }

    private static class RecipeCuttingShapeless extends ShapelessOreRecipe
    {
        public RecipeCuttingShapeless(ResourceLocation group, NonNullList<Ingredient> input, ItemStack result)
        {
            super(group, input, result);
        }

        private Random random = new Random();

        private ItemStack damageTool(ItemStack stack)
        {
            EntityPlayer craftingPlayer = ForgeHooks.getCraftingPlayer();
            if(stack.attemptDamageItem(1, random, craftingPlayer instanceof EntityPlayerMP ? (EntityPlayerMP) craftingPlayer : null))
            {
                ForgeEventFactory.onPlayerDestroyItem(craftingPlayer, stack, null);
                return ItemStack.EMPTY;
            }

            return stack;
        }

        @Override
        public NonNullList<ItemStack> getRemainingItems(final InventoryCrafting inventoryCrafting)
        {
            final NonNullList<ItemStack> remainingItems = NonNullList.withSize(inventoryCrafting.getSizeInventory(), ItemStack.EMPTY);

            for(int i = 0; i < remainingItems.size(); ++i)
            {
                final ItemStack itemstack = inventoryCrafting.getStackInSlot(i);

                if(!itemstack.isEmpty() && itemstack.getItem().isDamageable())
                {
                    remainingItems.set(i, damageTool(itemstack.copy()));
                }
                else
                {
                    remainingItems.set(i, ForgeHooks.getContainerItem(itemstack));
                }
            }

            return remainingItems;
        }

        public static class Factory implements IRecipeFactory
        {
            @Override
            public IRecipe parse(final JsonContext context, final JsonObject json)
            {
                final String group = JsonUtils.getString(json, "group", "");
                final NonNullList<Ingredient> ingredients = RecipeUtil.parseShapeless(context, json);
                final ItemStack result = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context);

                return new RecipeCuttingShapeless(group.isEmpty() ? null : new ResourceLocation(group), ingredients, result);
            }
        }
    }

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class RegistrationHandler
    {
        public static final List<IRecipe> RECIPES = new LinkedList<>();

        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<IRecipe> event)
        {
            RECIPES.stream().forEach(recipe -> event.getRegistry().register(recipe));
        }
    }
}
