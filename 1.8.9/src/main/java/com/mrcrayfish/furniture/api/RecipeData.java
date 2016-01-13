/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2016  MrCrayfish (http://www.mrcrayfish.com/)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mrcrayfish.furniture.api;

import java.util.ArrayList;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipeData
{

	/** ItemStack variables */
	private ItemStack input, output, currency;

	/** Price */
	private int price;

	/** Blender Variables */
	private String name;
	private int heal;
	private int red;
	private int green;
	private int blue;
	private ArrayList<ItemStack> ingredients;

	/**
	 * This method is used to set the input ItemStack. For instance, the Raw
	 * Beef before it gets cooked into Cooked Beef. Note: For MineBay, the input
	 * ItemStack is the item you are going to sell.
	 * 
	 * @param input
	 *            Sets the input ItemStack. E.g. Raw Beef
	 */
	public RecipeData setInput(ItemStack input)
	{
		this.input = input;
		return this;
	}

	/**
	 * This method is used to set the out ItemStack. For instance, its the
	 * Cooked Beef after its been cooked from Raw Beef. Note: For MineBay, you
	 * do not need to use this method at all. Please refer to setInput() and
	 * setCurrency() only.
	 * 
	 * @param output
	 *            Sets the output ItemStack. E.g. Cooked Beef
	 */
	public RecipeData setOutput(ItemStack output)
	{
		this.output = output;
		return this;
	}

	/**
	 * This method is used to set the currency in MineBay for the specific item.
	 * Note: For the Oven and Freezer, you do not need to use this method at
	 * all. Please refer to setInput() and setOutput() only.
	 * 
	 * @param currency
	 *            Sets the currency ItemStack. E.g. Gold Ingot
	 */
	public RecipeData setCurrency(ItemStack currency)
	{
		this.currency = currency;
		return this;
	}

	public RecipeData setPrice(int price)
	{
		this.price = price;
		return this;
	}

	public RecipeData setColour(int red, int green, int blue)
	{
		this.red = red;
		this.green = green;
		this.blue = blue;
		return this;
	}

	public RecipeData setName(String name)
	{
		this.name = name;
		return this;
	}

	public RecipeData setHeal(int heal)
	{
		this.heal = heal;
		return this;
	}

	public String getDrinkName()
	{
		return this.name;
	}

	public int getHealAmount()
	{
		return this.heal;
	}

	public int getRed()
	{
		return this.red;
	}

	public int getGreen()
	{
		return this.green;
	}

	public int getBlue()
	{
		return this.blue;
	}

	public RecipeData addIngredient(ItemStack ingredient)
	{
		if (ingredients == null)
		{
			ingredients = new ArrayList<ItemStack>();
		}
		ingredients.add(ingredient);
		return this;
	}

	public ArrayList<ItemStack> getIngredients()
	{
		return ingredients;
	}

	/** Gets the input ItemStack */
	public ItemStack getInput()
	{
		return input;
	}

	/** Gets the output ItemStack */
	public ItemStack getOutput()
	{
		return output;
	}

	/** Gets the currency ItemStack */
	public ItemStack getCurrency()
	{
		return currency;
	}

	/** Gets the price */
	public int getPrice()
	{
		return price;
	}

	@Override
	public String toString()
	{
		String result = "";
		if (input != null)
		{
			String name = GameRegistry.findUniqueIdentifierFor(input.getItem()).toString();
			result += "input-item=" + name + ",input-amount=" + input.stackSize + ",input-metadata=" + input.getItemDamage() + ",";
		}
		if (output != null)
		{
			String name = GameRegistry.findUniqueIdentifierFor(output.getItem()).toString();
			result += "output-item=" + name + ",output-amount=" + output.stackSize + ",output-metadata=" + output.getItemDamage() + ",";
		}
		if (currency != null)
		{
			String name = GameRegistry.findUniqueIdentifierFor(currency.getItem()).toString();
			result += "payment-item=" + name + ",output-item-metadata=" + currency.getItemDamage() + ",";
			result += "payment-price=" + price + ",";
		}
		if (name != null)
		{
			result += "name=" + name + ",";
			result += "heal=" + heal + ",";
			result += "colour=" + red + "-" + green + "-" + blue + ",";
		}
		if (ingredients != null)
		{
			String pre = "ingredients=";
			for (int i = 0; i < ingredients.size(); i++)
			{
				String name = GameRegistry.findUniqueIdentifierFor(ingredients.get(i).getItem()).toString();
				pre += name;
				pre += ":";
				pre += ingredients.get(i).stackSize;
				pre += ":";
				pre += ingredients.get(i).getItemDamage();
				if (i != ingredients.size() - 1)
				{
					pre += "/";
				}
			}
			result += pre + ",";
		}
		return result.substring(0, result.length() - 1);
	}

	public static RecipeData convertFrom(Map<String, Object> params)
	{
		RecipeData data = new RecipeData();
		if (params.containsKey("input"))
		{
			Object input = params.get("input");
			if (input instanceof ItemStack)
			{
				data.setInput((ItemStack) input);
			}
		}
		if (params.containsKey("output"))
		{
			Object output = params.get("output");
			if (output instanceof ItemStack)
			{
				data.setOutput((ItemStack) output);
			}
		}
		if (params.containsKey("currency"))
		{
			Object currency = params.get("currency");
			if (currency instanceof ItemStack)
			{
				data.setCurrency((ItemStack) currency);
			}
		}
		if (params.containsKey("price"))
		{
			Object price = params.get("price");
			if (price instanceof Integer)
			{
				data.setPrice((Integer) price);
			}
		}
		if (params.containsKey("ingredients"))
		{
			Object ingredients = params.get("ingredients");
			if (ingredients instanceof ItemStack[])
			{
				for (ItemStack ingredient : (ItemStack[]) ingredients)
				{
					data.addIngredient(new ItemStack(ingredient.getItem(), ingredient.stackSize, ingredient.getItemDamage()));
				}
			}
		}
		if (params.containsKey("colour"))
		{
			Object colour = params.get("colour");
			if (colour instanceof int[])
			{
				int[] rgb = (int[]) colour;
				data.setColour(rgb[0], rgb[1], rgb[2]);
			}
		}
		if (params.containsKey("name"))
		{
			Object name = params.get("name");
			if (name instanceof String)
			{
				data.setName((String) name);
			}
		}
		if (params.containsKey("heal"))
		{
			Object heal = params.get("heal");
			if (heal instanceof Integer)
			{
				data.setHeal((Integer) heal);
			}
		}
		return data;
	}
}
