/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2014  MrCrayfish (http://www.mrcrayfish.com/)
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
package com.mrcrayfish.furniture.handler;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.mrcrayfish.furniture.api.RecipeRegistry;
import com.mrcrayfish.furniture.api.Recipes;

public class ConfigurationHandler
{
	public static Configuration config;

	public static final String CATEGORY_RECIPE_SETTINGS = "recipe-settings";
	public static final String CATEGORY_API = "recipe-api";
	public static final String CATEGORY_SETTINGS = "settings";

	public static String[] items = {};
	public static boolean canDisplay = true;
	public static boolean hasDisplayedOnce = false;
	public static boolean api_debug = true;
	
	public static boolean mirrorEnabled = true;
	public static int mirrorQuality = 128;
	public static float mirrorFov = 80F;
	public static boolean mirrorClouds = false;

	public static boolean printer_1 = true, printer_2 = true;
	public static boolean oven_1 = true, oven_2 = true, oven_3 = true, oven_4 = true, oven_5 = true, oven_6 = true, oven_7 = true;
	public static boolean frez_1 = true, frez_2 = true, frez_3 = true, frez_4 = true, frez_5 = true, frez_6 = true;
	public static boolean mine_1 = true, mine_2 = true, mine_3 = true, mine_4 = true, mine_5 = true, mine_6 = true, mine_7 = true, mine_8 = true, mine_9 = true, mine_10 = true;
	public static boolean blen_1 = true, blen_2 = true, blen_3 = true, blen_4 = true;
	public static boolean chop_1 = true;
	public static boolean dish_1 = true, dish_2 = true, dish_3 = true, dish_4 = true, dish_5 = true, dish_6 = true, dish_7 = true, dish_8 = true, dish_9 = true, dish_10 = true;
	public static boolean dish_11 = true, dish_12 = true, dish_13 = true, dish_14 = true, dish_15 = true, dish_16 = true, dish_17 = true, dish_18 = true, dish_19 = true, dish_20 = true;
	public static boolean dish_21 = true, dish_22 = true, dish_23 = true, dish_24 = true, dish_25 = true, dish_26 = true;

	public static boolean micr_1 = true, micr_2 = true;

	public static boolean toast_1 = true;

	public static boolean wash_1 = true, wash_2 = true, wash_3 = true, wash_4 = true, wash_5 = true, wash_6 = true, wash_7 = true, wash_8 = true, wash_9 = true, wash_10 = true;
	public static boolean wash_11 = true, wash_12 = true, wash_13 = true, wash_14 = true, wash_15 = true, wash_16 = true, wash_17 = true, wash_18 = true, wash_19 = true, wash_20 = true;

	public static void init(File file)
	{
		if (config == null)
		{
			config = new Configuration(file);
			loadConfig(false);
		}
	}

	public static void loadConfig(boolean shouldChange)
	{
		mirrorEnabled = config.getBoolean("mirror-enabled", CATEGORY_SETTINGS, true, "Determines whether the mirror will be rendered.");
		mirrorClouds = config.getBoolean("mirror-clouds", CATEGORY_SETTINGS, false, "Set whether the mirror should render clouds.");
		mirrorFov = config.getFloat("mirror-fov", CATEGORY_SETTINGS, 80F, 10F, 100F, "Sets the field of view for the mirror.");
		mirrorQuality = config.getInt("mirror-quality", CATEGORY_SETTINGS, 128, 16, 512, "Sets the resolution for the mirror. High number means better quality but worse performace.");
		api_debug = config.getBoolean("recipe-api-debug", CATEGORY_SETTINGS, true, "If true, prints out information about RecipeAPI. Recommended 'true' for people trying to add custom recipe.");
		canDisplay = config.getBoolean("display", CATEGORY_SETTINGS, canDisplay, "Enabled or disable the welcome message");
		items = config.getStringList("custom-recipes", CATEGORY_API, items, "Insert custom recipes here");
		config.addCustomCategoryComment(CATEGORY_RECIPE_SETTINGS, "Enabled or disable the default recipes");
		config.addCustomCategoryComment(CATEGORY_API, "RecipeAPI Configuration. How to use: http://mrcrayfishs-furniture-mod.wikia.com/wiki/Configuration");
		updateEnabledRecipes();

		if (config.hasChanged() && shouldChange)
		{
			Recipes.clearLocalRecipes();
			Recipes.clearCommRecipes();
			RecipeRegistry.registerDefaultRecipes();
			RecipeRegistry.registerConfigRecipes();
			Recipes.addCommRecipesToLocal();
			Recipes.updateDataList();
		}
		config.save();
	}

	private static void updateEnabledRecipes()
	{
		printer_1 = config.getBoolean("printer-1", CATEGORY_RECIPE_SETTINGS, printer_1, "Enchanted Book");
		printer_2 = config.getBoolean("printer-2", CATEGORY_RECIPE_SETTINGS, printer_2, "Written Book");
		oven_1 = config.getBoolean("oven-1", CATEGORY_RECIPE_SETTINGS, oven_1, "Beef -> Cooked Beef");
		oven_2 = config.getBoolean("oven-2", CATEGORY_RECIPE_SETTINGS, oven_2, "Porkchop -> Cooked Porkchop");
		oven_3 = config.getBoolean("oven-3", CATEGORY_RECIPE_SETTINGS, oven_3, "Potato -> Cooked Potato");
		oven_4 = config.getBoolean("oven-4", CATEGORY_RECIPE_SETTINGS, oven_4, "Chicken -> Cooked Chicken");
		oven_5 = config.getBoolean("oven-5", CATEGORY_RECIPE_SETTINGS, oven_5, "Raw Fish -> Cooked Fish");
		oven_6 = config.getBoolean("oven-6", CATEGORY_RECIPE_SETTINGS, oven_6, "Raw Salmon -> Cooked Salmon");
		oven_7 = config.getBoolean("oven-7", CATEGORY_RECIPE_SETTINGS, oven_7, "Flesh -> Cooked Flesh");
		frez_1 = config.getBoolean("freezer-1", CATEGORY_RECIPE_SETTINGS, frez_1, "Water Bucket -> Ice");
		frez_2 = config.getBoolean("freezer-2", CATEGORY_RECIPE_SETTINGS, frez_2, "Ice -> Packet Ice");
		frez_3 = config.getBoolean("freezer-3", CATEGORY_RECIPE_SETTINGS, frez_3, "Lava Bucket -> Obsidian");
		frez_4 = config.getBoolean("freezer-4", CATEGORY_RECIPE_SETTINGS, frez_4, "Slime Ball -> Snow Ball");
		frez_5 = config.getBoolean("freezer-5", CATEGORY_RECIPE_SETTINGS, frez_5, "Poinsonous Potato -> Potato");
		frez_6 = config.getBoolean("freezer-6", CATEGORY_RECIPE_SETTINGS, frez_6, "Rotten Flesh -> Flesh");
		mine_1 = config.getBoolean("minebay-1", CATEGORY_RECIPE_SETTINGS, mine_1, "16 Hardened Clay for 1 Emerald");
		mine_2 = config.getBoolean("minebay-2", CATEGORY_RECIPE_SETTINGS, mine_2, "1 Skeleton Skull for 8 Emeralds");
		mine_3 = config.getBoolean("minebay-3", CATEGORY_RECIPE_SETTINGS, mine_3, "1 Saddle for 4 Emeralds");
		mine_4 = config.getBoolean("minebay-4", CATEGORY_RECIPE_SETTINGS, mine_4, "1 Horse Spawn Egg for 8 Emeralds");
		mine_5 = config.getBoolean("minebay-5", CATEGORY_RECIPE_SETTINGS, mine_5, "1 Diamond Horse Armour for 8 Diamonds");
		mine_6 = config.getBoolean("minebay-6", CATEGORY_RECIPE_SETTINGS, mine_6, "1 Experience Bottle for 1 Iron Ingot");
		mine_7 = config.getBoolean("minebay-7", CATEGORY_RECIPE_SETTINGS, mine_7, "4 Christmas Firework for 1 Iron Ingot");
		mine_8 = config.getBoolean("minebay-8", CATEGORY_RECIPE_SETTINGS, mine_8, "1 Silk Touch Book for 8 Emeralds");
		mine_9 = config.getBoolean("minebay-9", CATEGORY_RECIPE_SETTINGS, mine_9, "2 Night Vision Potion for 1 Emerald");
		mine_10 = config.getBoolean("minebay-10", CATEGORY_RECIPE_SETTINGS, mine_10, "1 Recipe Book for 1 Emerald");
		blen_1 = config.getBoolean("blender-1", CATEGORY_RECIPE_SETTINGS, blen_1, "Fruit Crush");
		blen_2 = config.getBoolean("blender-2", CATEGORY_RECIPE_SETTINGS, blen_2, "Veggie Juice");
		blen_3 = config.getBoolean("blender-3", CATEGORY_RECIPE_SETTINGS, blen_3, "Fishy Blend");
		blen_4 = config.getBoolean("blender-4", CATEGORY_RECIPE_SETTINGS, blen_4, "Energy Drink");
		chop_1 = config.getBoolean("chopping-board-1", CATEGORY_RECIPE_SETTINGS, chop_1, "Bread -> 6 Bread Slices");
		dish_1 = config.getBoolean("dishwasher-1", CATEGORY_RECIPE_SETTINGS, dish_1, "Bow");
		dish_2 = config.getBoolean("dishwasher-2", CATEGORY_RECIPE_SETTINGS, dish_2, "Wooden Pickaxe");
		dish_3 = config.getBoolean("dishwasher-3", CATEGORY_RECIPE_SETTINGS, dish_3, "Wooden Axe");
		dish_4 = config.getBoolean("dishwasher-4", CATEGORY_RECIPE_SETTINGS, dish_4, "Wooden Shovel");
		dish_5 = config.getBoolean("dishwasher-5", CATEGORY_RECIPE_SETTINGS, dish_5, "Wooden Hoe");
		dish_6 = config.getBoolean("dishwasher-6", CATEGORY_RECIPE_SETTINGS, dish_6, "Wooden Sword");
		dish_7 = config.getBoolean("dishwasher-7", CATEGORY_RECIPE_SETTINGS, dish_7, "Stone Pickaxe");
		dish_8 = config.getBoolean("dishwasher-8", CATEGORY_RECIPE_SETTINGS, dish_8, "Stone Axe");
		dish_9 = config.getBoolean("dishwasher-9", CATEGORY_RECIPE_SETTINGS, dish_9, "Stone Shovel");
		dish_10 = config.getBoolean("dishwasher-10", CATEGORY_RECIPE_SETTINGS, dish_10, "Stone Hoe");
		dish_11 = config.getBoolean("dishwasher-11", CATEGORY_RECIPE_SETTINGS, dish_11, "Stone Sword");
		dish_12 = config.getBoolean("dishwasher-12", CATEGORY_RECIPE_SETTINGS, dish_12, "Iron Pickaxe");
		dish_13 = config.getBoolean("dishwasher-13", CATEGORY_RECIPE_SETTINGS, dish_13, "Iron Axe");
		dish_14 = config.getBoolean("dishwasher-14", CATEGORY_RECIPE_SETTINGS, dish_14, "Iron Shovel");
		dish_15 = config.getBoolean("dishwasher-15", CATEGORY_RECIPE_SETTINGS, dish_15, "Iron Hoe");
		dish_16 = config.getBoolean("dishwasher-16", CATEGORY_RECIPE_SETTINGS, dish_16, "Iron Sword");
		dish_17 = config.getBoolean("dishwasher-17", CATEGORY_RECIPE_SETTINGS, dish_17, "Golden Pickaxe");
		dish_18 = config.getBoolean("dishwasher-18", CATEGORY_RECIPE_SETTINGS, dish_18, "Golden Axe");
		dish_19 = config.getBoolean("dishwasher-19", CATEGORY_RECIPE_SETTINGS, dish_19, "Golden Shovel");
		dish_20 = config.getBoolean("dishwasher-20", CATEGORY_RECIPE_SETTINGS, dish_20, "Golden Hoe");
		dish_21 = config.getBoolean("dishwasher-21", CATEGORY_RECIPE_SETTINGS, dish_21, "Golden Sword");
		dish_22 = config.getBoolean("dishwasher-22", CATEGORY_RECIPE_SETTINGS, dish_22, "Diamond Pickaxe");
		dish_23 = config.getBoolean("dishwasher-23", CATEGORY_RECIPE_SETTINGS, dish_23, "Diamond Axe");
		dish_24 = config.getBoolean("dishwasher-24", CATEGORY_RECIPE_SETTINGS, dish_24, "Diamond Shovel");
		dish_25 = config.getBoolean("dishwasher-25", CATEGORY_RECIPE_SETTINGS, dish_25, "Diamond Hoe");
		dish_26 = config.getBoolean("dishwasher-26", CATEGORY_RECIPE_SETTINGS, dish_26, "Diamond Sword");

		micr_1 = config.getBoolean("microwave-1", CATEGORY_RECIPE_SETTINGS, micr_1, "Beef -> Cooked Beef");
		micr_2 = config.getBoolean("microwave-2", CATEGORY_RECIPE_SETTINGS, micr_2, "Potato -> Baked Potato");

		toast_1 = config.getBoolean("toast-2", CATEGORY_RECIPE_SETTINGS, toast_1, "Bread Slice -> Toast");

		wash_1 = config.getBoolean("washing-machine-1", CATEGORY_RECIPE_SETTINGS, wash_1, "Leather Helmet");
		wash_2 = config.getBoolean("washing-machine-2", CATEGORY_RECIPE_SETTINGS, wash_2, "Leather Chestplate");
		wash_3 = config.getBoolean("washing-machine-3", CATEGORY_RECIPE_SETTINGS, wash_3, "Leather Leggings");
		wash_4 = config.getBoolean("washing-machine-4", CATEGORY_RECIPE_SETTINGS, wash_4, "Leather Boots");
		wash_5 = config.getBoolean("washing-machine-5", CATEGORY_RECIPE_SETTINGS, wash_5, "Chainmail Helmet");
		wash_6 = config.getBoolean("washing-machine-6", CATEGORY_RECIPE_SETTINGS, wash_6, "Chainmail Chestplate");
		wash_7 = config.getBoolean("washing-machine-7", CATEGORY_RECIPE_SETTINGS, wash_7, "Chainmail Leggings");
		wash_8 = config.getBoolean("washing-machine-8", CATEGORY_RECIPE_SETTINGS, wash_8, "Chainmail Boots");
		wash_9 = config.getBoolean("washing-machine-9", CATEGORY_RECIPE_SETTINGS, wash_9, "Iron Helmet");
		wash_10 = config.getBoolean("washing-machine-10", CATEGORY_RECIPE_SETTINGS, wash_10, "Iron Chestplate");
		wash_11 = config.getBoolean("washing-machine-11", CATEGORY_RECIPE_SETTINGS, wash_11, "Iron Leggings");
		wash_12 = config.getBoolean("washing-machine-12", CATEGORY_RECIPE_SETTINGS, wash_12, "Iron Boots");
		wash_13 = config.getBoolean("washing-machine-13", CATEGORY_RECIPE_SETTINGS, wash_13, "Golden Helmet");
		wash_14 = config.getBoolean("washing-machine-14", CATEGORY_RECIPE_SETTINGS, wash_14, "Golden Chestplate");
		wash_15 = config.getBoolean("washing-machine-15", CATEGORY_RECIPE_SETTINGS, wash_15, "Golden Leggings");
		wash_16 = config.getBoolean("washing-machine-16", CATEGORY_RECIPE_SETTINGS, wash_16, "Golden Boots");
		wash_17 = config.getBoolean("washing-machine-17", CATEGORY_RECIPE_SETTINGS, wash_17, "Diamond Helmet");
		wash_18 = config.getBoolean("washing-machine-18", CATEGORY_RECIPE_SETTINGS, wash_18, "Diamond Chestplate");
		wash_19 = config.getBoolean("washing-machine-19", CATEGORY_RECIPE_SETTINGS, wash_19, "Diamond Leggings");
		wash_20 = config.getBoolean("washing-machine-20", CATEGORY_RECIPE_SETTINGS, wash_20, "Diamond Boots");
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs)
	{
		if (eventArgs.modID.equals("cfm"))
		{
			loadConfig(true);
		}
	}
}
