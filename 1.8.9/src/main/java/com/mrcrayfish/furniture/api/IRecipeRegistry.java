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

public interface IRecipeRegistry
{
	/**
	 * Adds a value to the recipe variables
	 * <br><br>
	 * Please read {@link RecipeVariables} or your recipe might not work
	 * 
	 * @param type
	 *            The recipe type
	 * @param params
	 *            The variables for the recipe.
	 */
	@Deprecated
	public void registerRecipe(String type, RecipeVariables params);
	
	/**
	 * Registers a recipe<br>
	 * <br>
	 * Please read {@link RecipeVariables} for information about<br>
	 * required variables for the specific {@link RecipeType}.
	 * 
	 * @param type
	 *            The recipe type. See {@link RecipeType}
	 * @param params
	 *            The variables for the recipe.
	 */
	public void registerRecipe(RecipeType type, RecipeVariables variables);
}
