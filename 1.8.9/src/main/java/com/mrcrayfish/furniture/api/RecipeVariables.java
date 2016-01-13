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

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;

/**
 * Different recipes require certain variables for them to be registered. Some
 * recipes have optional variables which you can add but is not required. If the
 * required variables are not present, your custom recipe will be discarded.
 * Below is a table which should help you add the correct variables.
 * <table border="2">
 * <tr>
 * <TH>Type</TH>
 * <TH>Required Variables</TH>
 * <TH>Optional Variables</TH>
 * </tr>
 * <tr>
 * <td>Freezer</td>
 * <td>input (ItemStack), output (ItemStack)</td>
 * </tr>
 * <tr>
 * <td>Oven</td>
 * <td>input (ItemStack), output (ItemStack)</td>
 * </tr>
 * <tr>
 * <td>Printer</td>
 * <td>input (ItemStack)</td>
 * </tr>
 * <tr>
 * <td>MineBay</td>
 * <td>input (ItemStack)</td>
 * <td>currency (ItemStack), price (Integer)</td>
 * </tr>
 * <tr>
 * <td>Toaster</td>
 * <td>input (ItemStack), output (ItemStack)</td>
 * </tr>
 * <tr>
 * <td>Chopping Board</td>
 * <td>input (ItemStack), output (ItemStack)</td>
 * </tr>
 * <tr>
 * <td>Blender</td>
 * <td>name (String), heal (Integer), ingredients (ItemStack[]), colour (int[])</td>
 * </tr>
 * <tr>
 * <td>Dishwasher</td>
 * <td>input (ItemStack)</td>
 * </tr>
 * <tr>
 * <td>Washing Machine</td>
 * <td>input (ItemStack)</td>
 * </tr>
 * <tr>
 * <td>Microwave</td>
 * <td>input (ItemStack), output(ItemStack)</td>
 * </tr>
 * </table>
 * <br>
 * <br>
 * For example, an oven recipe might look like this.<br>
 * <br>
 * <i>RecipeVariables recipe = new RecipeVariables(); <br>
 * recipe.addValue("input", new ItemStack(Items.beef)); <br>
 * recipe.addValue("output", new ItemStack(Items.cooked_beef)); <br>
 * registry.registerRecipe("oven", recipe);</i> <br>
 * <br>
 * or<br>
 * <br>
 * <i>registry.registerRecipe("oven", new RecipeVariables().addValue("input",
 * new ItemStack(Items.beef)).addValue("output", new
 * ItemStack(Items.cooked_beef)));</i> <br>
 * <br>
 * For a full tutorial, <a
 * href="http://mrcrayfishs-furniture-mod.wikia.com/wiki/Developers">click
 * here</a>.
 * 
 * @author MrCrayfish
 *
 */
public class RecipeVariables {
	
	private HashMap<String, Object> variables = new HashMap<String, Object>();
	
	public Map<String, Object> getMap() {
		return variables;
	}
	
	/**
	 * Adds a value to the recipe variables
	 * 
	 * @param variable
	 *            The type of variable
	 * @param object
	 *            The object to bind to the variable
	 * @return RecipeVariables instance
	 * 
	 * @see {@link RecipeVariables} for required variables
	 * */
	@Deprecated
	public RecipeVariables addValue(String variable, Object object) {
		variables.put(variable, object);
		return this;
	}

	/**
	 * Sets the input ItemStack for the recipe<br>
	 * <br>
	 * Valid for recipe type(s):<br> 
	 * CHOPPING_BOARD,<br> 
	 * DISHWASHER,<br>  
	 * FREEZER,<br>  
	 * MICROWAVE,<br>  
	 * MINEBAY,<br>  
	 * OVEN,<br>  
	 * PRINTER,<br>
	 * TOASTER,<br>    
	 * WASHING_MACHINE
	 * 
	 * @param input
	 *            The input ItemStack for the recipe
	 * @return Instance of RecipeVariables
	 */
	public RecipeVariables setInput(ItemStack input) {
		variables.put("input", input);
		return this;
	}
	
	/**
	 * Sets the output ItemStack for the recipe<br>
	 * <br>
	 * Valid for recipe type(s):<br> 
	 * CHOPPING_BOARD,<br> 
	 * DISHWASHER,<br>  
	 * FREEZER,<br>  
	 * MICROWAVE,<br>  
	 * MINEBAY,<br>  
	 * OVEN,<br>  
	 * PRINTER,<br>
	 * TOASTER,<br>  
	 * WASHING_MACHINE
	 * 
	 * @param output
	 *            The output ItemStack for the recipe
	 * @return Instance of RecipeVariables
	 */
	public RecipeVariables setOutput(ItemStack output) {
		variables.put("output", output);
		return this;
	}

	/**
	 * Valid for recipe type(s): BLENDER
	 * 
	 * @param name
	 *            The name of the blended drink
	 * @return Instance of RecipeVariables
	 */
	public RecipeVariables setName(String name) {
		variables.put("name", name);
		return this;
	}

	/**
	 * Valid for recipe type(s): BLENDER
	 * 
	 * @param heal
	 *            The amount of hearts to heal
	 * @return Instance of RecipeVariables
	 */
	public RecipeVariables setHeal(int heal) {
		variables.put("heal", heal);
		return this;
	}

	/**
	 * Valid for recipe type(s): BLENDER
	 * 
	 * @param ingredients
	 *            The required ingredients for the blender recipe. Must be an ItemStack array.
	 * @return Instance of RecipeVariables
	 */
	public RecipeVariables setIngredients(ItemStack... ingredients) {
		variables.put("ingredients", ingredients);
		return this;
	}

	/**
	 * Valid for recipe type(s): BLENDER
	 * 
	 * @param rgb
	 *            The colour of the drink.
	 * @return Instance of RecipeVariables
	 */
	public RecipeVariables setColour(int[] rgb) {
		variables.put("colour", rgb);
		return this;
	}
}
