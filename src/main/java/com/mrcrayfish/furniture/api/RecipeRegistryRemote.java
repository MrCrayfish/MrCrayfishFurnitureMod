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

import com.mrcrayfish.furniture.FurnitureConfig;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;

public class RecipeRegistryRemote extends RecipeAPI
{
    private static RecipeRegistryRemote furnitureRegister = null;

    public static RecipeRegistryRemote getInstance()
    {
        if(furnitureRegister == null)
        {
            furnitureRegister = new RecipeRegistryRemote();
        }
        return furnitureRegister;
    }

    public void registerMineBayItem(ItemStack item, ItemStack currency, int price)
    {
        addMineBayRecipe(new RecipeData().setInput(item).setCurrency(currency).setPrice(price), REMOTE);
    }

    public void registerOvenRecipe(ItemStack input, ItemStack output)
    {
        addOvenRecipe(new RecipeData().setInput(input).setOutput(output), REMOTE);
    }

    public void registerFreezerRecipe(ItemStack input, ItemStack output)
    {
        addFreezerRecipe(new RecipeData().setInput(input).setOutput(output), REMOTE);
    }

    public void registerPrinterRecipe(ItemStack input)
    {
        addPrinterRecipe(new RecipeData().setInput(input), REMOTE);
    }

    public void registerChoppingBoardRecipe(ItemStack input, ItemStack output)
    {
        addChoppingBoardRecipe(new RecipeData().setInput(input).setOutput(output), REMOTE);
    }

    public void registerToasterRecipe(ItemStack input, ItemStack output)
    {
        addToasterRecipe(new RecipeData().setInput(input).setOutput(output), REMOTE);
    }

    public void registerMicrowaveRecipe(ItemStack input, ItemStack output)
    {
        addMicrowaveRecipe(new RecipeData().setInput(input).setOutput(output), REMOTE);
    }

    public void registerWashingMachineRecipe(ItemStack input)
    {
        addWashingMachineRecipe(new RecipeData().setInput(input), REMOTE);
    }

    public void registerDishwasherRecipe(ItemStack input)
    {
        addDishwasherRecipe(new RecipeData().setInput(input), REMOTE);
    }

    public void registerGrillRecipe(ItemStack input, ItemStack output)
    {
        addGrillRecipe(new RecipeData().setInput(input).setOutput(output), REMOTE);
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
        addBlenderRecipe(data, REMOTE);
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

                RecipeRegistryRemote.getInstance().registerPrinterRecipe(new ItemStack(input, 1, i_metadata));
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

                        RecipeRegistryRemote.getInstance().registerOvenRecipe(new ItemStack(input, 1, i_metadata), new ItemStack(output, o_amount, o_metadata));
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

                        RecipeRegistryRemote.getInstance().registerFreezerRecipe(new ItemStack(input, 1, i_metadata), new ItemStack(output, o_amount, o_metadata));
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
        String payment_item_metadata = parser.getValue("payment-item-metadata", "0");
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

                    RecipeRegistryRemote.getInstance().registerMineBayItem(new ItemStack(input, i_amount, i_metadata), new ItemStack(payment, 1, p_metadata), p_price);
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

                        RecipeRegistryRemote.getInstance().registerToasterRecipe(new ItemStack(input, 1, i_metadata), new ItemStack(output, o_amount, o_metadata));
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

                        RecipeRegistryRemote.getInstance().registerChoppingBoardRecipe(new ItemStack(input, 1, i_metadata), new ItemStack(output, o_amount, o_metadata));
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

                        ItemStack[] in = parseIngredients(parser, ingredients, num);
                        if(in == null)
                        {
                            return;
                        }

                        int[] rgb = parseColour(parser, colour, num);
                        if(rgb == null)
                        {
                            return;
                        }

                        name = parseFormatting(parseSpaces(name));

                        RecipeRegistryRemote.getInstance().registerBlenderRecipe(name, h_amount, in, rgb);
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

    public static void registerGrillRecipe(Parser parser, int num)
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

                        RecipeRegistryRemote.getInstance().registerGrillRecipe(new ItemStack(input, 1, i_metadata), new ItemStack(output, o_amount, o_metadata));
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

    public static ItemStack[] parseIngredients(Parser parser, String ingredients, int num)
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

    public static int[] parseColour(Parser parser, String colour, int num)
    {
        String[] rgb = colour.split("-");
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

                        RecipeRegistryRemote.getInstance().registerMicrowaveRecipe(new ItemStack(input, 1, i_metadata), new ItemStack(output, 1, o_metadata));
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
                RecipeRegistryRemote.getInstance().registerWashingMachineRecipe(new ItemStack(input));
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
                RecipeRegistryRemote.getInstance().registerDishwasherRecipe(new ItemStack(input));
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

    public static void registerRemoteRecipes(ArrayList<String> data)
    {
        if(data.size() > 0)
        {
            System.out.println("RecipeAPI (Remote): Syncing " + data.size() + " recipes from the server.");
        }
        Parser parser = Parser.getInstance();
        for(int i = 0; i < data.size(); i++)
        {
            System.out.println(data.get(i));
            parser.parseLine(data.get(i), false);
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
}
