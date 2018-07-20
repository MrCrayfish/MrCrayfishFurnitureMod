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
package com.mrcrayfish.furniture.api;

import java.util.ArrayList;

import com.mrcrayfish.furniture.FurnitureConfig;
import com.mrcrayfish.furniture.init.FurnitureItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;

public class RecipeRegistry extends RecipeAPI
{
    private static RecipeRegistry furnitureRegister = null;

    public static RecipeRegistry getInstance()
    {
        if(furnitureRegister == null)
        {
            furnitureRegister = new RecipeRegistry();
        }
        return furnitureRegister;
    }

    public void registerMineBayItem(ItemStack item, ItemStack currency, int price)
    {
        addMineBayRecipe(new RecipeData().setInput(item).setCurrency(currency).setPrice(price), LOCAL);
    }

    public void registerOvenRecipe(ItemStack input, ItemStack output)
    {
        addOvenRecipe(new RecipeData().setInput(input).setOutput(output), LOCAL);
    }

    public void registerFreezerRecipe(ItemStack input, ItemStack output)
    {
        addFreezerRecipe(new RecipeData().setInput(input).setOutput(output), LOCAL);
    }

    public void registerPrinterRecipe(ItemStack input)
    {
        addPrinterRecipe(new RecipeData().setInput(input), LOCAL);
    }

    public void registerChoppingBoardRecipe(ItemStack input, ItemStack output)
    {
        addChoppingBoardRecipe(new RecipeData().setInput(input).setOutput(output), LOCAL);
    }

    public void registerToasterRecipe(ItemStack input, ItemStack output)
    {
        addToasterRecipe(new RecipeData().setInput(input).setOutput(output), LOCAL);
    }

    public void registerMicrowaveRecipe(ItemStack input, ItemStack output)
    {
        addMicrowaveRecipe(new RecipeData().setInput(input).setOutput(output), LOCAL);
    }

    public void registerWashingMachineRecipe(ItemStack input)
    {
        addWashingMachineRecipe(new RecipeData().setInput(input), LOCAL);
    }

    public void registerDishwasherRecipe(ItemStack input)
    {
        addDishwasherRecipe(new RecipeData().setInput(input), LOCAL);
    }

    public void registerGrillRecipe(ItemStack input, ItemStack output)
    {
        addGrillRecipe(new RecipeData().setInput(input).setOutput(output), LOCAL);
    }

    public void registerBlenderRecipe(String name, int heal, ItemStack[] ingredients, int[] rgb)
    {
        RecipeData data = new RecipeData();
        data.setName(name);
        data.setHeal(heal);
        for(ItemStack item : ingredients)
        {
            data.addIngredient(item);
        }
        data.setColour(rgb[0], rgb[1], rgb[2]);
        addBlenderRecipe(data, LOCAL);
    }

    public static void registerPrinterRecipe(Parser parser, int num)
    {
        String input_item = parser.getValue("input-item", null);
        String input_metadata = parser.getValue("input-metadata", "0");

        if(input_item != null)
        {
            Item input = Item.getByNameOrId(input_item);
            if(input != null)
            {
                int i_metadata = 0;
                try
                {
                    i_metadata = Integer.parseInt(input_metadata);
                }
                catch(NumberFormatException e)
                {
                    if(FurnitureConfig.COMMON.recipe.apiDebug)
                    {
                        RecipeUtil.printReport(parser, num, "input-metadata", "Could not parse the value '" + input_metadata + "' to an integer.");
                    }
                    return;
                }

                RecipeRegistry.getInstance().registerPrinterRecipe(new ItemStack(input, 1, i_metadata));
            }
            else
            {
                if(FurnitureConfig.COMMON.recipe.apiDebug)
                {
                    RecipeUtil.printReport(parser, num, "input-item", "The input-item '" + input_item + "' does not exist");
                }
            }
        }
        else
        {
            if(FurnitureConfig.COMMON.recipe.apiDebug)
            {
                RecipeUtil.printMissing(parser, num, "input-item", "An input-item is required");
            }
        }
    }

    public static void registerOvenRecipe(Parser parser, int num)
    {
        String input_item = parser.getValue("input-item", null);
        String input_metadata = parser.getValue("input-metadata", "0");
        String output_item = parser.getValue("output-item", null);
        String output_metadata = parser.getValue("output-metadata", "0");
        String output_amount = parser.getValue("output-amount", "1");

        if(input_item != null)
        {
            if(output_item != null)
            {
                Item input = Item.getByNameOrId(input_item);
                Item output = Item.getByNameOrId(output_item);
                if(input != null)
                {
                    if(output != null)
                    {
                        int i_metadata = 0;
                        try
                        {
                            i_metadata = Integer.parseInt(input_metadata);
                        }
                        catch(NumberFormatException e)
                        {
                            if(FurnitureConfig.COMMON.recipe.apiDebug)
                            {
                                RecipeUtil.printReport(parser, num, "input-metadata", "Could not parse the value '" + input_metadata + "' to an integer");
                            }
                            return;
                        }

                        int o_amount = 1;
                        try
                        {
                            o_amount = Integer.parseInt(output_amount);
                        }
                        catch(NumberFormatException e)
                        {
                            if(FurnitureConfig.COMMON.recipe.apiDebug)
                            {
                                RecipeUtil.printReport(parser, num, "output-amount", "Could not parse the value '" + output_amount + "' to an integer");
                            }
                            return;
                        }

                        int o_metadata = 0;
                        try
                        {
                            o_metadata = Integer.parseInt(output_metadata);
                        }
                        catch(NumberFormatException e)
                        {
                            if(FurnitureConfig.COMMON.recipe.apiDebug)
                            {
                                RecipeUtil.printReport(parser, num, "output-metadata", "Could not parse the value '" + output_metadata + "' to an integer");
                            }
                            return;
                        }

                        RecipeRegistry.getInstance().registerOvenRecipe(new ItemStack(input, 1, i_metadata), new ItemStack(output, o_amount, o_metadata));
                    }
                    else
                    {
                        if(FurnitureConfig.COMMON.recipe.apiDebug)
                        {
                            RecipeUtil.printReport(parser, num, "output-item", "The output-item '" + output_item + "' does not exist");
                        }
                    }
                }
                else
                {
                    if(FurnitureConfig.COMMON.recipe.apiDebug)
                    {
                        RecipeUtil.printReport(parser, num, "input-item", "The input-item '" + input_item + "' does not exist");
                    }
                }
            }
            else
            {
                if(FurnitureConfig.COMMON.recipe.apiDebug)
                {
                    RecipeUtil.printMissing(parser, num, "output-item", "An output-item is required");
                }
            }
        }
        else
        {
            if(FurnitureConfig.COMMON.recipe.apiDebug)
            {
                RecipeUtil.printMissing(parser, num, "input-item", "An input-item is required");
            }
        }
    }

    public static void registerFreezerRecipe(Parser parser, int num)
    {
        String input_item = parser.getValue("input-item", null);
        String input_metadata = parser.getValue("input-metadata", "0");
        String output_item = parser.getValue("output-item", null);
        String output_metadata = parser.getValue("output-metadata", "0");
        String output_amount = parser.getValue("output-amount", "1");

        if(input_item != null)
        {
            if(output_item != null)
            {
                Item input = Item.getByNameOrId(input_item);
                Item output = Item.getByNameOrId(output_item);
                if(input != null)
                {
                    if(output != null)
                    {
                        int i_metadata = 0;
                        try
                        {
                            i_metadata = Integer.parseInt(input_metadata);
                        }
                        catch(NumberFormatException e)
                        {
                            if(FurnitureConfig.COMMON.recipe.apiDebug)
                            {
                                RecipeUtil.printReport(parser, num, "input-metdata", "Could not parse the value '" + input_metadata + "' to an integer");
                            }
                            return;
                        }

                        int o_amount = 1;
                        try
                        {
                            o_amount = Integer.parseInt(output_amount);
                        }
                        catch(NumberFormatException e)
                        {
                            if(FurnitureConfig.COMMON.recipe.apiDebug)
                            {
                                RecipeUtil.printReport(parser, num, "output-amount", "Could not parse the value '" + output_amount + "' to an integer");
                            }
                            return;
                        }

                        int o_metadata = 0;
                        try
                        {
                            o_metadata = Integer.parseInt(output_metadata);
                        }
                        catch(NumberFormatException e)
                        {
                            if(FurnitureConfig.COMMON.recipe.apiDebug)
                            {
                                RecipeUtil.printReport(parser, num, "output-metadata", "Could not parse the value '" + output_metadata + "' to an integer");
                            }
                            return;
                        }

                        RecipeRegistry.getInstance().registerFreezerRecipe(new ItemStack(input, 1, i_metadata), new ItemStack(output, o_amount, o_metadata));
                    }
                    else
                    {
                        if(FurnitureConfig.COMMON.recipe.apiDebug)
                        {
                            RecipeUtil.printReport(parser, num, "output-item", "The output-item '" + output_item + "' does not exist");
                        }
                    }
                }
                else
                {
                    if(FurnitureConfig.COMMON.recipe.apiDebug)
                    {
                        RecipeUtil.printReport(parser, num, "input-item", "The input-item '" + input_item + "' does not exist");
                    }
                }
            }
            else
            {
                if(FurnitureConfig.COMMON.recipe.apiDebug)
                {
                    RecipeUtil.printMissing(parser, num, "output-item", "An output-item is required");
                }
            }
        }
        else
        {
            if(FurnitureConfig.COMMON.recipe.apiDebug)
            {
                RecipeUtil.printMissing(parser, num, "input-item", "An input-item is required");
            }
        }
    }

    public static void registerMineBayRecipe(Parser parser, int num)
    {
        String input_item = parser.getValue("input-item", null);
        String input_metadata = parser.getValue("input-metadata", "0");
        String input_amount = parser.getValue("input-amount", "1");
        String payment_item = parser.getValue("payment-item", "minecraft:emerald");
        String payment_item_metadata = parser.getValue("payment-metadata", "0");
        String price = parser.getValue("payment-price", "1");

        if(input_item != null)
        {
            Item input = Item.getByNameOrId(input_item);
            Item payment = Item.getByNameOrId(payment_item);
            if(input != null)
            {
                if(payment != null)
                {

                    int i_metadata = 0;
                    try
                    {
                        i_metadata = Integer.parseInt(input_metadata);
                    }
                    catch(NumberFormatException e)
                    {
                        if(FurnitureConfig.COMMON.recipe.apiDebug)
                        {
                            RecipeUtil.printReport(parser, num, "input-metadata", "Could not parse the value '" + input_metadata + "' to an integer");
                        }
                        return;
                    }

                    int i_amount = 0;
                    try
                    {
                        i_amount = Integer.parseInt(input_amount);
                    }
                    catch(NumberFormatException e)
                    {
                        if(FurnitureConfig.COMMON.recipe.apiDebug)
                        {
                            RecipeUtil.printReport(parser, num, "input-amount", "Could not parse the value '" + input_amount + "' to an integer");
                        }
                        return;
                    }

                    int p_metadata = 0;
                    try
                    {
                        p_metadata = Integer.parseInt(payment_item_metadata);
                    }
                    catch(NumberFormatException e)
                    {
                        if(FurnitureConfig.COMMON.recipe.apiDebug)
                        {
                            RecipeUtil.printReport(parser, num, "payment-metadata", "Could not parse the value '" + p_metadata + "' to an integer");
                        }
                        return;
                    }

                    int p_price = 1;
                    try
                    {
                        p_price = Integer.parseInt(price);
                    }
                    catch(NumberFormatException e)
                    {
                        if(FurnitureConfig.COMMON.recipe.apiDebug)
                        {
                            RecipeUtil.printReport(parser, num, "price", "Could not parse the value '" + p_price + "' to an integer");
                        }
                        return;
                    }

                    RecipeRegistry.getInstance().registerMineBayItem(new ItemStack(input, i_amount, i_metadata), new ItemStack(payment, 1, p_metadata), p_price);
                }
                else
                {
                    if(FurnitureConfig.COMMON.recipe.apiDebug)
                    {
                        RecipeUtil.printReport(parser, num, "payment-item", "The payment-item '" + payment_item + "' does not exist");
                    }
                }
            }
            else
            {
                if(FurnitureConfig.COMMON.recipe.apiDebug)
                {
                    RecipeUtil.printReport(parser, num, "input-item", "The input-item '" + input_item + "' does not exist");
                }
            }
        }
        else
        {
            if(FurnitureConfig.COMMON.recipe.apiDebug)
            {
                RecipeUtil.printMissing(parser, num, "input-item", "An input-item is required");
            }
        }
    }

    public static void registerToasterRecipe(Parser parser, int num)
    {
        String input_item = parser.getValue("input-item", null);
        String input_metadata = parser.getValue("input-metadata", "0");
        String output_item = parser.getValue("output-item", null);
        String output_metadata = parser.getValue("output-metadata", "0");
        String output_amount = parser.getValue("output-amount", "1");

        if(input_item != null)
        {
            if(output_item != null)
            {
                Item input = Item.getByNameOrId(input_item);
                Item output = Item.getByNameOrId(output_item);
                if(input != null)
                {
                    if(output != null)
                    {
                        int i_metadata = 0;
                        try
                        {
                            i_metadata = Integer.parseInt(input_metadata);
                        }
                        catch(NumberFormatException e)
                        {
                            if(FurnitureConfig.COMMON.recipe.apiDebug)
                            {
                                RecipeUtil.printReport(parser, num, "input-metadata", "Could not parse the value '" + input_metadata + "' to an integer");
                            }
                            return;
                        }

                        int o_amount = 1;
                        try
                        {
                            o_amount = Integer.parseInt(output_amount);
                        }
                        catch(NumberFormatException e)
                        {
                            if(FurnitureConfig.COMMON.recipe.apiDebug)
                            {
                                RecipeUtil.printReport(parser, num, "output-amount", "Could not parse the value '" + output_amount + "' to an integer");
                            }
                            return;
                        }

                        int o_metadata = 0;
                        try
                        {
                            o_metadata = Integer.parseInt(output_metadata);
                        }
                        catch(NumberFormatException e)
                        {
                            if(FurnitureConfig.COMMON.recipe.apiDebug)
                            {
                                RecipeUtil.printReport(parser, num, "output-metadata", "Could not parse the value '" + output_metadata + "' to an integer");
                            }
                            return;
                        }

                        RecipeRegistry.getInstance().registerToasterRecipe(new ItemStack(input, 1, i_metadata), new ItemStack(output, o_amount, o_metadata));
                    }
                    else
                    {
                        if(FurnitureConfig.COMMON.recipe.apiDebug)
                        {
                            RecipeUtil.printReport(parser, num, "output-item", "The output-item '" + output_item + "' does not exist");
                        }
                    }
                }
                else
                {
                    if(FurnitureConfig.COMMON.recipe.apiDebug)
                    {
                        RecipeUtil.printReport(parser, num, "input-item", "The input-item '" + input_item + "' does not exist");
                    }
                }
            }
            else
            {
                if(FurnitureConfig.COMMON.recipe.apiDebug)
                {
                    RecipeUtil.printMissing(parser, num, "output-item", "An output-item is required");
                }
            }
        }
        else
        {
            if(FurnitureConfig.COMMON.recipe.apiDebug)
            {
                RecipeUtil.printMissing(parser, num, "input-item", "An input-item is required");
            }
        }
    }

    public static void registerChoppingBoardRecipe(Parser parser, int num)
    {
        String input_item = parser.getValue("input-item", null);
        String input_metadata = parser.getValue("input-metadata", "0");
        String output_item = parser.getValue("output-item", null);
        String output_metadata = parser.getValue("output-metadata", "0");
        String output_amount = parser.getValue("output-amount", "1");

        if(input_item != null)
        {
            if(output_item != null)
            {
                Item input = Item.getByNameOrId(input_item);
                Item output = Item.getByNameOrId(output_item);
                if(input != null)
                {
                    if(output != null)
                    {
                        int i_metadata = 0;
                        try
                        {
                            i_metadata = Integer.parseInt(input_metadata);
                        }
                        catch(NumberFormatException e)
                        {
                            if(FurnitureConfig.COMMON.recipe.apiDebug)
                            {
                                RecipeUtil.printReport(parser, num, "input-metadata", "Could not parse the value '" + input_metadata + "' to an integer");
                            }
                            return;
                        }

                        int o_amount = 1;
                        try
                        {
                            o_amount = Integer.parseInt(output_amount);
                        }
                        catch(NumberFormatException e)
                        {
                            if(FurnitureConfig.COMMON.recipe.apiDebug)
                            {
                                RecipeUtil.printReport(parser, num, "output-amount", "Could not parse the value '" + output_amount + "' to an integer");
                            }
                            return;
                        }

                        int o_metadata = 0;
                        try
                        {
                            o_metadata = Integer.parseInt(output_metadata);
                        }
                        catch(NumberFormatException e)
                        {
                            if(FurnitureConfig.COMMON.recipe.apiDebug)
                            {
                                RecipeUtil.printReport(parser, num, "output-metadata", "Could not parse the value '" + output_metadata + "' to an integer");
                            }
                            return;
                        }

                        RecipeRegistry.getInstance().registerChoppingBoardRecipe(new ItemStack(input, 1, i_metadata), new ItemStack(output, o_amount, o_metadata));
                    }
                    else
                    {
                        if(FurnitureConfig.COMMON.recipe.apiDebug)
                        {
                            RecipeUtil.printReport(parser, num, "output-item", "The output-item '" + output_item + "' does not exist");
                        }
                    }
                }
                else
                {
                    if(FurnitureConfig.COMMON.recipe.apiDebug)
                    {
                        RecipeUtil.printReport(parser, num, "input-item", "The input-item '" + input_item + "' does not exist");
                    }
                }
            }
            else
            {
                if(FurnitureConfig.COMMON.recipe.apiDebug)
                {
                    RecipeUtil.printMissing(parser, num, "output-item", "An output-item is required");
                }
            }
        }
        else
        {
            if(FurnitureConfig.COMMON.recipe.apiDebug)
            {
                RecipeUtil.printMissing(parser, num, "input-item", "An input-item is required");
            }
        }
    }

    public static void registerBlenderRecipe(Parser parser, int num)
    {
        String name = parser.getValue("name", null);
        String heal_amount = parser.getValue("heal", null);
        String ingredients = parser.getValue("ingredients", null);
        String colour = parser.getValue("colour", null);

        if(name != null)
        {
            if(heal_amount != null)
            {
                if(ingredients != null)
                {
                    if(colour != null)
                    {
                        int h_amount = 0;
                        try
                        {
                            h_amount = Integer.parseInt(heal_amount);
                        }
                        catch(NumberFormatException e)
                        {
                            if(FurnitureConfig.COMMON.recipe.apiDebug)
                            {
                                RecipeUtil.printReport(parser, num, "heal", "Could not parse the value '" + heal_amount + "' to an integer");
                            }
                            return;
                        }

                        ItemStack[] in = parseIngredients(ingredients, num, parser);
                        if(in == null)
                        {
                            return;
                        }

                        int[] rgb = parseColour(colour, num, parser);
                        if(rgb == null)
                        {
                            return;
                        }

                        name = parseFormatting(parseSpaces(name));

                        RecipeRegistry.getInstance().registerBlenderRecipe(name, h_amount, in, rgb);
                    }
                    else
                    {

                        if(FurnitureConfig.COMMON.recipe.apiDebug)
                        {
                            RecipeUtil.printMissing(parser, num, "colour", "A colour is required");
                        }
                    }
                }
                else
                {
                    if(FurnitureConfig.COMMON.recipe.apiDebug)
                    {
                        RecipeUtil.printMissing(parser, num, "ingredients", "Ingredients are required");
                    }
                }
            }
            else
            {
                if(FurnitureConfig.COMMON.recipe.apiDebug)
                {
                    RecipeUtil.printMissing(parser, num, "heal", "A heal amount is required");
                }
            }
        }
        else
        {
            if(FurnitureConfig.COMMON.recipe.apiDebug)
            {
                RecipeUtil.printMissing(parser, num, "name", "A name is required");
            }
        }
    }

    public static ItemStack[] parseIngredients(String ingredients, int num, Parser parser)
    {
        ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        String[] ingredientData = ingredients.split("/");

        if(ingredientData.length == 0)
        {
            return null;
        }

        int length = ingredientData.length > 4 ? 4 : ingredientData.length;
        for(int i = 0; i < length; i++)
        {
            String[] itemData = ingredientData[i].split(":");
            String itemName = itemData[0] + ":" + itemData[1];
            String itemAmount = "1";
            String itemMetadata = "0";

            if(itemData.length > 2)
            {
                itemAmount = itemData[2];
                if(itemData.length > 3)
                {
                    itemMetadata = itemData[3];
                }
            }

            Item item = Item.getByNameOrId(itemName);

            int i_amount = 1;
            try
            {
                i_amount = Integer.parseInt(itemAmount);
            }
            catch(NumberFormatException e)
            {
                if(FurnitureConfig.COMMON.recipe.apiDebug)
                {
                    RecipeUtil.printReport(parser, num, "ingredients", "Could not parse the value '" + itemAmount + "' to an integer for ingredient number " + i);
                }
                return null;
            }

            int i_metadata = 0;
            try
            {
                i_metadata = Integer.parseInt(itemMetadata);
            }
            catch(NumberFormatException e)
            {
                if(FurnitureConfig.COMMON.recipe.apiDebug)
                {
                    RecipeUtil.printReport(parser, num, "ingredients", "Could not parse the value '" + itemMetadata + "' to an integer for ingredient number " + i);
                }
                return null;
            }

            list.add(new ItemStack(item, i_amount, i_metadata));
        }
        return list.toArray(new ItemStack[0]);
    }

    public static int[] parseColour(String colour, int num, Parser parser)
    {
        String[] rgb = colour.split(":");
        if(rgb.length == 3)
        {
            String r = rgb[0];
            String g = rgb[1];
            String b = rgb[2];

            int red = 0;
            try
            {
                red = Integer.parseInt(r);
            }
            catch(NumberFormatException e)
            {
                if(FurnitureConfig.COMMON.recipe.apiDebug)
                {
                    RecipeUtil.printReport(parser, num, "colour", "Could not parse the value '" + r + "' to an integer");
                }
                return null;
            }

            int green = 0;
            try
            {
                green = Integer.parseInt(g);
            }
            catch(NumberFormatException e)
            {
                if(FurnitureConfig.COMMON.recipe.apiDebug)
                {
                    RecipeUtil.printReport(parser, num, "colour", "Could not parse the value '" + g + "' to an integer");
                }
                return null;
            }

            int blue = 0;
            try
            {
                blue = Integer.parseInt(b);
            }
            catch(NumberFormatException e)
            {
                if(FurnitureConfig.COMMON.recipe.apiDebug)
                {
                    RecipeUtil.printReport(parser, num, "colour", "Could not parse the value '" + b + "' to an integer");
                }
                return null;
            }

            return new int[]{red, green, blue};
        }
        else
        {
            if(FurnitureConfig.COMMON.recipe.apiDebug)
            {
                RecipeUtil.printReport(parser, num, "colour", "The colour variable doesn't have all three rgb values set, it needs to be colour=r-g-b");
            }
            return null;
        }
    }

    public static String parseSpaces(String name)
    {
        return name.replaceAll("_", " ");
    }

    public static String parseFormatting(String name)
    {
        name = name.replaceAll("&0", TextFormatting.BLACK.toString());
        name = name.replaceAll("&1", TextFormatting.DARK_BLUE.toString());
        name = name.replaceAll("&2", TextFormatting.DARK_GREEN.toString());
        name = name.replaceAll("&3", TextFormatting.DARK_AQUA.toString());
        name = name.replaceAll("&4", TextFormatting.DARK_RED.toString());
        name = name.replaceAll("&5", TextFormatting.DARK_PURPLE.toString());
        name = name.replaceAll("&6", TextFormatting.GOLD.toString());
        name = name.replaceAll("&7", TextFormatting.GRAY.toString());
        name = name.replaceAll("&8", TextFormatting.DARK_GRAY.toString());
        name = name.replaceAll("&9", TextFormatting.BLUE.toString());
        name = name.replaceAll("&a", TextFormatting.GREEN.toString());
        name = name.replaceAll("&b", TextFormatting.AQUA.toString());
        name = name.replaceAll("&c", TextFormatting.RED.toString());
        name = name.replaceAll("&d", TextFormatting.LIGHT_PURPLE.toString());
        name = name.replaceAll("&e", TextFormatting.YELLOW.toString());
        name = name.replaceAll("&f", TextFormatting.WHITE.toString());
        name = name.replaceAll("&k", TextFormatting.OBFUSCATED.toString());
        name = name.replaceAll("&l", TextFormatting.BOLD.toString());
        name = name.replaceAll("&m", TextFormatting.STRIKETHROUGH.toString());
        name = name.replaceAll("&n", TextFormatting.UNDERLINE.toString());
        name = name.replaceAll("&o", TextFormatting.ITALIC.toString());
        name = name.replaceAll("&r", TextFormatting.RESET.toString());
        return name;
    }

    public static void registerMicrowaveRecipe(Parser parser, int num)
    {
        String input_item = parser.getValue("input-item", null);
        String input_metadata = parser.getValue("input-metadata", "0");
        String output_item = parser.getValue("output-item", null);
        String output_metadata = parser.getValue("output-metadata", "0");

        if(input_item != null)
        {
            if(output_item != null)
            {
                Item input = Item.getByNameOrId(input_item);
                Item output = Item.getByNameOrId(output_item);
                if(input != null)
                {
                    if(output != null)
                    {
                        int i_metadata = 0;
                        try
                        {
                            i_metadata = Integer.parseInt(input_metadata);
                        }
                        catch(NumberFormatException e)
                        {
                            if(FurnitureConfig.COMMON.recipe.apiDebug)
                            {
                                RecipeUtil.printReport(parser, num, "input-metadata", "Could not parse the value '" + input_metadata + "' to an integer");
                            }
                            return;
                        }

                        int o_metadata = 0;
                        try
                        {
                            o_metadata = Integer.parseInt(output_metadata);
                        }
                        catch(NumberFormatException e)
                        {
                            if(FurnitureConfig.COMMON.recipe.apiDebug)
                            {
                                RecipeUtil.printReport(parser, num, "output-metadata", "Could not parse the value '" + output_metadata + "' to an integer");
                            }
                            return;
                        }

                        RecipeRegistry.getInstance().registerMicrowaveRecipe(new ItemStack(input, 1, i_metadata), new ItemStack(output, 1, o_metadata));
                    }
                    else
                    {
                        if(FurnitureConfig.COMMON.recipe.apiDebug)
                        {
                            RecipeUtil.printReport(parser, num, "output-item", "The output-item '" + output_item + "' does not exist");
                        }
                    }
                }
                else
                {
                    if(FurnitureConfig.COMMON.recipe.apiDebug)
                    {
                        RecipeUtil.printReport(parser, num, "input-item", "The input-item '" + input_item + "' does not exist");
                    }
                }
            }
            else
            {
                if(FurnitureConfig.COMMON.recipe.apiDebug)
                {
                    RecipeUtil.printMissing(parser, num, "output-item", "An output-item is required");
                }
            }
        }
        else
        {
            if(FurnitureConfig.COMMON.recipe.apiDebug)
            {
                RecipeUtil.printMissing(parser, num, "input-item", "An input-item is required");
            }
        }
    }

    public static void registerWashingMachineRecipe(Parser parser, int num)
    {
        String input_item = parser.getValue("input-item", null);

        if(input_item != null)
        {
            Item input = Item.getByNameOrId(input_item);
            if(input != null)
            {
                RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(input));
            }
            else
            {
                if(FurnitureConfig.COMMON.recipe.apiDebug)
                {
                    RecipeUtil.printReport(parser, num, "input-item", "The input-item '" + input_item + "' does not exist");
                }
            }
        }
        else
        {
            if(FurnitureConfig.COMMON.recipe.apiDebug)
            {
                RecipeUtil.printMissing(parser, num, "input-item", "An input-item is required");
            }
        }
    }

    public static void registerDishwasherRecipe(Parser parser, int num)
    {
        String input_item = parser.getValue("input-item", null);

        if(input_item != null)
        {
            Item input = Item.getByNameOrId(input_item);
            if(input != null)
            {
                RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(input));
            }
            else
            {
                if(FurnitureConfig.COMMON.recipe.apiDebug)
                {
                    RecipeUtil.printReport(parser, num, "input-item", "The input-item '" + input_item + "' does not exist");
                }
            }
        }
        else
        {
            if(FurnitureConfig.COMMON.recipe.apiDebug)
            {
                RecipeUtil.printMissing(parser, num, "input-item", "An input-item is required");
            }
        }
    }

    public static void registerGrillRecipe(Parser parser, int num)
    {
        String input_item = parser.getValue("input-item", null);
        String input_metadata = parser.getValue("input-metadata", "0");
        String output_item = parser.getValue("output-item", null);
        String output_metadata = parser.getValue("output-metadata", "0");

        if(input_item != null)
        {
            if(output_item != null)
            {
                Item input = Item.getByNameOrId(input_item);
                Item output = Item.getByNameOrId(output_item);
                if(input != null)
                {
                    if(output != null)
                    {
                        int i_metadata = 0;
                        try
                        {
                            i_metadata = Integer.parseInt(input_metadata);
                        }
                        catch(NumberFormatException e)
                        {
                            if(FurnitureConfig.COMMON.recipe.apiDebug)
                            {
                                RecipeUtil.printReport(parser, num, "input-metadata", "Could not parse the value '" + input_metadata + "' to an integer");
                            }
                            return;
                        }

                        int o_metadata = 0;
                        try
                        {
                            o_metadata = Integer.parseInt(output_metadata);
                        }
                        catch(NumberFormatException e)
                        {
                            if(FurnitureConfig.COMMON.recipe.apiDebug)
                            {
                                RecipeUtil.printReport(parser, num, "output-metadata", "Could not parse the value '" + output_metadata + "' to an integer");
                            }
                            return;
                        }

                        RecipeRegistry.getInstance().registerGrillRecipe(new ItemStack(input, 1, i_metadata), new ItemStack(output, 1, o_metadata));
                    }
                    else
                    {
                        if(FurnitureConfig.COMMON.recipe.apiDebug)
                        {
                            RecipeUtil.printReport(parser, num, "output-item", "The output-item '" + output_item + "' does not exist");
                        }
                    }
                }
                else
                {
                    if(FurnitureConfig.COMMON.recipe.apiDebug)
                    {
                        RecipeUtil.printReport(parser, num, "input-item", "The input-item '" + input_item + "' does not exist");
                    }
                }
            }
            else
            {
                if(FurnitureConfig.COMMON.recipe.apiDebug)
                {
                    RecipeUtil.printMissing(parser, num, "output-item", "An output-item is required");
                }
            }
        }
        else
        {
            if(FurnitureConfig.COMMON.recipe.apiDebug)
            {
                RecipeUtil.printMissing(parser, num, "input-item", "An input-item is required");
            }
        }
    }

    public static void registerConfigRecipes()
    {
        if(FurnitureConfig.COMMON.recipe.items.length > 0 && FurnitureConfig.COMMON.recipe.apiDebug)
        {
            System.out.println("RecipeAPI (Configuration): Registering " + FurnitureConfig.COMMON.recipe.items.length + " recipes from the config.");
        }
        Parser parser = Parser.getInstance();
        for(int i = 0; i < FurnitureConfig.COMMON.recipe.items.length; i++)
        {
            parser.parseLine(FurnitureConfig.COMMON.recipe.items[i], true);
            String type = parser.getValue("type", null);

            int realNum = i + 1;

            if(type != null)
            {
                if(type.equalsIgnoreCase("printer"))
                {
                    registerPrinterRecipe(parser, realNum);
                }
                else if(type.equalsIgnoreCase("oven"))
                {
                    registerOvenRecipe(parser, realNum);
                }
                else if(type.equalsIgnoreCase("freezer"))
                {
                    registerFreezerRecipe(parser, realNum);
                }
                else if(type.equalsIgnoreCase("minebay"))
                {
                    registerMineBayRecipe(parser, realNum);
                }
                else if(type.equalsIgnoreCase("choppingboard"))
                {
                    registerChoppingBoardRecipe(parser, realNum);
                }
                else if(type.equalsIgnoreCase("toaster"))
                {
                    registerToasterRecipe(parser, realNum);
                }
                else if(type.equalsIgnoreCase("microwave"))
                {
                    registerMicrowaveRecipe(parser, realNum);
                }
                else if(type.equalsIgnoreCase("blender"))
                {
                    registerBlenderRecipe(parser, realNum);
                }
                else if(type.equalsIgnoreCase("washingmachine"))
                {
                    registerWashingMachineRecipe(parser, realNum);
                }
                else if(type.equalsIgnoreCase("dishwasher"))
                {
                    registerDishwasherRecipe(parser, realNum);
                }
                else if(type.equalsIgnoreCase("grill"))
                {
                    registerGrillRecipe(parser, realNum);
                }
                else
                {
                    if(FurnitureConfig.COMMON.recipe.apiDebug)
                    {
                        RecipeUtil.printReport(parser, realNum, "type", "The recipe type '" + type + "' does not exist");
                    }
                }
            }
        }
    }

    public static void registerDefaultRecipes()
    {
        if (FurnitureConfig.COMMON.recipe.enabled.printer.bookEnchanted)
            RecipeRegistry.getInstance().registerPrinterRecipe(new ItemStack(Items.ENCHANTED_BOOK));
        if (FurnitureConfig.COMMON.recipe.enabled.printer.bookWritten)
            RecipeRegistry.getInstance().registerPrinterRecipe(new ItemStack(Items.WRITTEN_BOOK));

        if (FurnitureConfig.COMMON.recipe.enabled.oven.beef)
            RecipeRegistry.getInstance().registerOvenRecipe(new ItemStack(Items.BEEF), new ItemStack(Items.COOKED_BEEF));
        if (FurnitureConfig.COMMON.recipe.enabled.oven.porkchop)
            RecipeRegistry.getInstance().registerOvenRecipe(new ItemStack(Items.PORKCHOP), new ItemStack(Items.COOKED_PORKCHOP));
        if (FurnitureConfig.COMMON.recipe.enabled.oven.potato)
            RecipeRegistry.getInstance().registerOvenRecipe(new ItemStack(Items.POTATO), new ItemStack(Items.BAKED_POTATO));
        if (FurnitureConfig.COMMON.recipe.enabled.oven.chicken)
            RecipeRegistry.getInstance().registerOvenRecipe(new ItemStack(Items.CHICKEN), new ItemStack(Items.COOKED_CHICKEN));
        if (FurnitureConfig.COMMON.recipe.enabled.oven.fish)
            RecipeRegistry.getInstance().registerOvenRecipe(new ItemStack(Items.FISH, 1, 0), new ItemStack(Items.COOKED_FISH, 1, 0));
        if (FurnitureConfig.COMMON.recipe.enabled.oven.salmon)
            RecipeRegistry.getInstance().registerOvenRecipe(new ItemStack(Items.FISH, 1, 1), new ItemStack(Items.COOKED_FISH, 1, 1));
        if (FurnitureConfig.COMMON.recipe.enabled.oven.flesh)
            RecipeRegistry.getInstance().registerOvenRecipe(new ItemStack(FurnitureItems.FLESH), new ItemStack(FurnitureItems.COOKED_FLESH));

        if (FurnitureConfig.COMMON.recipe.enabled.freezer.waterToIce)
            RecipeRegistry.getInstance().registerFreezerRecipe(new ItemStack(Items.WATER_BUCKET), new ItemStack(Blocks.ICE));
        if (FurnitureConfig.COMMON.recipe.enabled.freezer.iceToPacked)
            RecipeRegistry.getInstance().registerFreezerRecipe(new ItemStack(Blocks.ICE), new ItemStack(Blocks.PACKED_ICE));
        if (FurnitureConfig.COMMON.recipe.enabled.freezer.lavaToObsidian)
            RecipeRegistry.getInstance().registerFreezerRecipe(new ItemStack(Items.LAVA_BUCKET), new ItemStack(Blocks.OBSIDIAN));
        if (FurnitureConfig.COMMON.recipe.enabled.freezer.slimeToSnow)
            RecipeRegistry.getInstance().registerFreezerRecipe(new ItemStack(Items.SLIME_BALL), new ItemStack(Items.SNOWBALL));
        if (FurnitureConfig.COMMON.recipe.enabled.freezer.poisonousToPotato)
            RecipeRegistry.getInstance().registerFreezerRecipe(new ItemStack(Items.POISONOUS_POTATO), new ItemStack(Items.POTATO));
        if (FurnitureConfig.COMMON.recipe.enabled.freezer.rottenToFlesh)
            RecipeRegistry.getInstance().registerFreezerRecipe(new ItemStack(Items.ROTTEN_FLESH), new ItemStack(FurnitureItems.FLESH));

        if (FurnitureConfig.COMMON.recipe.enabled.minebay.clayForEmerald)
            RecipeRegistry.getInstance().registerMineBayItem(new ItemStack(Blocks.HARDENED_CLAY, 16), new ItemStack(Items.EMERALD), 1);
        if (FurnitureConfig.COMMON.recipe.enabled.minebay.skullForEmerald)
            RecipeRegistry.getInstance().registerMineBayItem(new ItemStack(Items.SKULL), new ItemStack(Items.EMERALD), 8);
        if (FurnitureConfig.COMMON.recipe.enabled.minebay.saddleForEmerald)
            RecipeRegistry.getInstance().registerMineBayItem(new ItemStack(Items.SADDLE), new ItemStack(Items.EMERALD), 4);
        if (FurnitureConfig.COMMON.recipe.enabled.minebay.horseForEmerald)
            RecipeRegistry.getInstance().registerMineBayItem(new ItemStack(Items.SPAWN_EGG, 1, 100), new ItemStack(Items.EMERALD), 8);
        if (FurnitureConfig.COMMON.recipe.enabled.minebay.horse_armourForDiamond)
            RecipeRegistry.getInstance().registerMineBayItem(new ItemStack(Items.DIAMOND_HORSE_ARMOR), new ItemStack(Items.DIAMOND), 8);
        if (FurnitureConfig.COMMON.recipe.enabled.minebay.xpForIron)
            RecipeRegistry.getInstance().registerMineBayItem(new ItemStack(Items.EXPERIENCE_BOTTLE), new ItemStack(Items.IRON_INGOT), 1);
        if (FurnitureConfig.COMMON.recipe.enabled.minebay.fireworkForIron)
        {
            ItemStack stack = new ItemStack(Items.FIREWORKS, 4);
            NBTTagCompound master = new NBTTagCompound();
            NBTTagCompound firework = new NBTTagCompound();
            firework.setByte("Flight", (byte) 1);
            NBTTagList list = new NBTTagList();
            NBTTagCompound data = new NBTTagCompound();
            data.setByte("Trail", (byte) 1);
            data.setByte("Type", (byte) 0);
            data.setIntArray("Colors", new int[]{11743532, 3887386});
            list.appendTag(data);
            firework.setTag("Explosions", list);
            master.setTag("Fireworks", firework);
            stack.setTagCompound(master);
            stack.setStackDisplayName(TextFormatting.RED + "Christmas" + TextFormatting.GREEN + " Firework");
            RecipeRegistry.getInstance().registerMineBayItem(stack, new ItemStack(Items.IRON_INGOT), 1);
        }
        if (FurnitureConfig.COMMON.recipe.enabled.minebay.silkTouchForEmerald)
        {
            ItemStack stack = new ItemStack(Items.ENCHANTED_BOOK);
            NBTTagCompound master = new NBTTagCompound();
            NBTTagList list = new NBTTagList();
            NBTTagCompound data = new NBTTagCompound();
            data.setShort("id", (short) 33);
            data.setShort("lvl", (short) 1);
            list.appendTag(data);
            master.setTag("StoredEnchantments", list);
            stack.setTagCompound(master);
            RecipeRegistry.getInstance().registerMineBayItem(stack, new ItemStack(Items.EMERALD), 8);
        }
        if (FurnitureConfig.COMMON.recipe.enabled.minebay.nightVisionForEmerald)
        {
            ItemStack stack = new ItemStack(Items.POTIONITEM, 2);
            NBTTagCompound data = new NBTTagCompound();
            data.setString("Potion", "night_vision");
            stack.setTagCompound(data);
            RecipeRegistry.getInstance().registerMineBayItem(stack, new ItemStack(Items.EMERALD), 1);
        }
        if (FurnitureConfig.COMMON.recipe.enabled.minebay.recipeBookForEmerald)
            RecipeRegistry.getInstance().registerMineBayItem(new ItemStack(FurnitureItems.RECIPE_BOOk), new ItemStack(Items.EMERALD), 1);
        if (FurnitureConfig.COMMON.recipe.enabled.blender.fruit)
            RecipeRegistry.getInstance().registerBlenderRecipe("Fruit Crush", 4, new ItemStack[]{new ItemStack(Items.APPLE, 2), new ItemStack(Items.MELON, 4)}, new int[]{255, 58, 37});
        if (FurnitureConfig.COMMON.recipe.enabled.blender.veggie)
            RecipeRegistry.getInstance().registerBlenderRecipe("Veggie Juice", 6, new ItemStack[]{new ItemStack(Items.CARROT, 4), new ItemStack(Items.POTATO, 1), new ItemStack(Items.PUMPKIN_PIE, 2)}, new int[]{247, 139, 122});
        if (FurnitureConfig.COMMON.recipe.enabled.blender.fish)
            RecipeRegistry.getInstance().registerBlenderRecipe("Cookies and Cream Milkshake", 4, new ItemStack[]{new ItemStack(Items.COOKIE, 2), new ItemStack(Items.MILK_BUCKET)}, new int[]{255, 214, 164});
        if (FurnitureConfig.COMMON.recipe.enabled.blender.energy)
            RecipeRegistry.getInstance().registerBlenderRecipe("Energy Drink", 8, new ItemStack[]{new ItemStack(Items.REDSTONE, 8), new ItemStack(Items.FIRE_CHARGE, 1), new ItemStack(Items.SUGAR, 16)}, new int[]{92, 23, 8});

        RecipeRegistry.getInstance().registerBlenderRecipe("Egg Nog", 5, new ItemStack[]{new ItemStack(Items.EGG, 4), new ItemStack(Items.MILK_BUCKET, 1), new ItemStack(Items.SUGAR, 2)}, new int[]{255, 234, 178});

        if (FurnitureConfig.COMMON.recipe.enabled.choppingBoard.bread)
            RecipeRegistry.getInstance().registerChoppingBoardRecipe(new ItemStack(Items.BREAD), new ItemStack(FurnitureItems.BREAD_SLICE, 6));

        if (FurnitureConfig.COMMON.recipe.enabled.dishwasher.bow)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.BOW));
        if (FurnitureConfig.COMMON.recipe.enabled.dishwasher.woodPickaxe)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.WOODEN_PICKAXE));
        if (FurnitureConfig.COMMON.recipe.enabled.dishwasher.woodAxe)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.WOODEN_AXE));
        if (FurnitureConfig.COMMON.recipe.enabled.dishwasher.woodShovel)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.WOODEN_SHOVEL));
        if (FurnitureConfig.COMMON.recipe.enabled.dishwasher.woodHoe)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.WOODEN_HOE));
        if (FurnitureConfig.COMMON.recipe.enabled.dishwasher.woodSword)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.WOODEN_SWORD));
        if (FurnitureConfig.COMMON.recipe.enabled.dishwasher.stonePickaxe)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.STONE_PICKAXE));
        if (FurnitureConfig.COMMON.recipe.enabled.dishwasher.stoneAxe)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.STONE_AXE));
        if (FurnitureConfig.COMMON.recipe.enabled.dishwasher.stoneShovel)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.STONE_SHOVEL));
        if (FurnitureConfig.COMMON.recipe.enabled.dishwasher.stoneHoe)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.STONE_HOE));
        if (FurnitureConfig.COMMON.recipe.enabled.dishwasher.stoneSword)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.STONE_SWORD));
        if (FurnitureConfig.COMMON.recipe.enabled.dishwasher.ironPickaxe)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.IRON_PICKAXE));
        if (FurnitureConfig.COMMON.recipe.enabled.dishwasher.ironAxe)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.IRON_AXE));
        if (FurnitureConfig.COMMON.recipe.enabled.dishwasher.ironShovel)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.IRON_SHOVEL));
        if (FurnitureConfig.COMMON.recipe.enabled.dishwasher.ironHoe)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.IRON_HOE));
        if (FurnitureConfig.COMMON.recipe.enabled.dishwasher.ironSword)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.IRON_SWORD));
        if (FurnitureConfig.COMMON.recipe.enabled.dishwasher.goldPickaxe)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.GOLDEN_PICKAXE));
        if (FurnitureConfig.COMMON.recipe.enabled.dishwasher.goldAxe)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.GOLDEN_AXE));
        if (FurnitureConfig.COMMON.recipe.enabled.dishwasher.goldShovel)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.GOLDEN_SHOVEL));
        if (FurnitureConfig.COMMON.recipe.enabled.dishwasher.goldhoe)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.GOLDEN_HOE));
        if (FurnitureConfig.COMMON.recipe.enabled.dishwasher.goldSword)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.GOLDEN_SWORD));
        if (FurnitureConfig.COMMON.recipe.enabled.dishwasher.diamondPickaxe)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.DIAMOND_PICKAXE));
        if (FurnitureConfig.COMMON.recipe.enabled.dishwasher.diamondAxe)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.DIAMOND_AXE));
        if (FurnitureConfig.COMMON.recipe.enabled.dishwasher.diamondShovel)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.DIAMOND_SHOVEL));
        if (FurnitureConfig.COMMON.recipe.enabled.dishwasher.diamondHoe)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.DIAMOND_HOE));
        if (FurnitureConfig.COMMON.recipe.enabled.dishwasher.diamondSword)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.DIAMOND_SWORD));
        if (FurnitureConfig.COMMON.recipe.enabled.dishwasher.fishingRod)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.FISHING_ROD));
        if (FurnitureConfig.COMMON.recipe.enabled.dishwasher.flintAndSteel)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.FLINT_AND_STEEL));
        if (FurnitureConfig.COMMON.recipe.enabled.dishwasher.shears)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.SHEARS));
        if (FurnitureConfig.COMMON.recipe.enabled.dishwasher.shield)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.SHIELD));

        if (FurnitureConfig.COMMON.recipe.enabled.microwave.beef)
            RecipeRegistry.getInstance().registerMicrowaveRecipe(new ItemStack(Items.BEEF), new ItemStack(Items.COOKED_BEEF));
        if (FurnitureConfig.COMMON.recipe.enabled.microwave.potato)
            RecipeRegistry.getInstance().registerMicrowaveRecipe(new ItemStack(Items.POTATO), new ItemStack(Items.BAKED_POTATO));

        if (FurnitureConfig.COMMON.recipe.enabled.toaster.toast)
            RecipeRegistry.getInstance().registerToasterRecipe(new ItemStack(FurnitureItems.BREAD_SLICE), new ItemStack(FurnitureItems.TOAST));

        if (FurnitureConfig.COMMON.recipe.enabled.washingMachine.leatherHelmet)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.LEATHER_HELMET));
        if (FurnitureConfig.COMMON.recipe.enabled.washingMachine.leatherChestplate)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.LEATHER_CHESTPLATE));
        if (FurnitureConfig.COMMON.recipe.enabled.washingMachine.leatherLeggings)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.LEATHER_LEGGINGS));
        if (FurnitureConfig.COMMON.recipe.enabled.washingMachine.leatherBoots)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.LEATHER_BOOTS));
        if (FurnitureConfig.COMMON.recipe.enabled.washingMachine.chainmailHelmet)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.CHAINMAIL_HELMET));
        if (FurnitureConfig.COMMON.recipe.enabled.washingMachine.chainmailChestplate)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.CHAINMAIL_CHESTPLATE));
        if (FurnitureConfig.COMMON.recipe.enabled.washingMachine.chainmailLeggings)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.CHAINMAIL_LEGGINGS));
        if (FurnitureConfig.COMMON.recipe.enabled.washingMachine.chainmailBoots)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.CHAINMAIL_BOOTS));
        if (FurnitureConfig.COMMON.recipe.enabled.washingMachine.ironHelmet)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.IRON_HELMET));
        if (FurnitureConfig.COMMON.recipe.enabled.washingMachine.ironChestplate)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.IRON_CHESTPLATE));
        if (FurnitureConfig.COMMON.recipe.enabled.washingMachine.ironLeggings)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.IRON_LEGGINGS));
        if (FurnitureConfig.COMMON.recipe.enabled.washingMachine.ironBoots)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.IRON_BOOTS));
        if (FurnitureConfig.COMMON.recipe.enabled.washingMachine.goldHelmet)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.GOLDEN_HELMET));
        if (FurnitureConfig.COMMON.recipe.enabled.washingMachine.goldChestplate)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.GOLDEN_CHESTPLATE));
        if (FurnitureConfig.COMMON.recipe.enabled.washingMachine.goldLeggings)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.GOLDEN_LEGGINGS));
        if (FurnitureConfig.COMMON.recipe.enabled.washingMachine.goldBoots)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.GOLDEN_BOOTS));
        if (FurnitureConfig.COMMON.recipe.enabled.washingMachine.diamondHelmet)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.DIAMOND_HELMET));
        if (FurnitureConfig.COMMON.recipe.enabled.washingMachine.diamondChestplate)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.DIAMOND_CHESTPLATE));
        if (FurnitureConfig.COMMON.recipe.enabled.washingMachine.diamondLeggings)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.DIAMOND_LEGGINGS));
        if (FurnitureConfig.COMMON.recipe.enabled.washingMachine.diamondBoots)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.DIAMOND_BOOTS));
        if (FurnitureConfig.COMMON.recipe.enabled.washingMachine.elytra)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.ELYTRA));

        if (FurnitureConfig.COMMON.recipe.enabled.grill.beef)
            RecipeRegistry.getInstance().registerGrillRecipe(new ItemStack(Items.BEEF), new ItemStack(Items.COOKED_BEEF));
        if (FurnitureConfig.COMMON.recipe.enabled.grill.sausage)
            RecipeRegistry.getInstance().registerGrillRecipe(new ItemStack(FurnitureItems.SAUSAGE), new ItemStack(FurnitureItems.SAUSAGE_COOKED));
        if (FurnitureConfig.COMMON.recipe.enabled.grill.kebab)
            RecipeRegistry.getInstance().registerGrillRecipe(new ItemStack(FurnitureItems.KEBAB), new ItemStack(FurnitureItems.KEBAB_COOKED));
    }
}
