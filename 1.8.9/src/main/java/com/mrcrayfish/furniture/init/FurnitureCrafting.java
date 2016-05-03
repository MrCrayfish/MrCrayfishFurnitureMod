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
package com.mrcrayfish.furniture.init;

import com.mrcrayfish.furniture.handler.FuelHandler;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class FurnitureCrafting
{
	public static void register()
	{
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemTableWood, 1), new Object[] { "***", " * ", " * ", '*', Blocks.planks });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemTableStone, 1), new Object[] { "***", " * ", " * ", '*', Blocks.cobblestone });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemChairWood, 1), new Object[] { "*  ", "***", "* *", '*', Blocks.planks });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemChairStone, 1), new Object[] { "*  ", "***", "* *", '*', Blocks.cobblestone });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemCouch, 1), new Object[] { "***", "***", '*', new ItemStack(Blocks.wool, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemFridge, 1), new Object[] { "***", "*#*", "*@*", '*', Blocks.iron_block, '#', Blocks.chest, '@', Blocks.furnace });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemCabinet, 1), new Object[] { "***", "*@*", "***", '*', Blocks.planks, '@', Blocks.chest });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemCurtains, 2), new Object[] { "@@@", "* *", "@ @", '*', Items.gold_ingot, '@', new ItemStack(Blocks.wool, 1, 14) });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemBlinds, 2), new Object[] { "***", "***", "***", '*', Items.stick });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemCoolPack, 2), new Object[] { "***", "*@*", "***", '*', Blocks.glass, '@', Items.water_bucket });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemCoffeeTableWood, 1), new Object[] { "***", "* *", '*', Blocks.planks });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemCoffeeTableStone, 1), new Object[] { "***", "* *", '*', Blocks.cobblestone });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemLamp, 2), new Object[] { "***", "*@*", " & ", '*', Blocks.wool, '@', Blocks.glowstone, '&', Blocks.obsidian });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemBedsideCabinet, 1), new Object[] { "***", "*@*", "*@*", '*', Blocks.planks, '@', Blocks.chest });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemOven, 1), new Object[] { "***", "*@*", "***", '*', Blocks.iron_block, '@', Blocks.furnace });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemOvenRangehood, 1), new Object[] { " * ", " * ", "*@*", '*', Items.iron_ingot, '@', Blocks.glowstone });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemHedgeBasic, 4), new Object[] { "***", "***", '*', new ItemStack(Blocks.leaves, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemHedgeSpruce, 4), new Object[] { "***", "***", '*', new ItemStack(Blocks.leaves, 1, 1) });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemHedgeBirch, 4), new Object[] { "***", "***", '*', new ItemStack(Blocks.leaves, 1, 2) });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemHedgeJungle, 4), new Object[] { "***", "***", '*', new ItemStack(Blocks.leaves, 1, 3) });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemHedgeAcacia, 4), new Object[] { "***", "***", '*', new ItemStack(Blocks.leaves2, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemHedgeDarkOak, 4), new Object[] { "***", "***", '*', new ItemStack(Blocks.leaves2, 1, 1) });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemBirdBath, 1), new Object[] { "***", " * ", " * ", '*', Blocks.stone });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemStonePath, 8), new Object[] { "**", '*', Blocks.cobblestone });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemTap, 1), new Object[] { " @ ", "***", "  *", '*', Blocks.stone, '@', Items.iron_ingot });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemMailBox, 1), new Object[] { "*@*", "***", " * ", '*', Blocks.planks, '@', Items.book });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemEnvelope, 1), new Object[] { "**", '*', Items.paper });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemPackage, 1), new Object[] { "***", "***", '*', Items.paper });
		GameRegistry.addShapelessRecipe(new ItemStack(FurnitureItems.itemDoorBell, 1), Blocks.noteblock, Blocks.stone_button);
		GameRegistry.addShapelessRecipe(new ItemStack(FurnitureItems.itemWhiteFence, 2), Blocks.oak_fence, new ItemStack(Items.dye, 1, 15));
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemComputer, 1), new Object[] { "***", "*@*", "*&*", '*', Blocks.iron_block, '@', Blocks.glass_pane, '&', Items.redstone });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemPrinter, 1), new Object[] { "*@*", "&R&", "***", '*', Blocks.stone, '@', Items.paper, '&', Blocks.iron_block, 'R', Items.redstone });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemElectricFence, 8), new Object[] { "***", "*@*", "*#*", '*', Items.iron_ingot, '@', FurnitureItems.itemWhiteFence, '#', Blocks.redstone_torch });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemFireAlarm, 1), new Object[] { "*#*", "*@*", '*', Items.iron_ingot, '@', Blocks.noteblock, '#', Items.redstone });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemTV, 1), new Object[] { "***", "*@*", "*&*", '*', Blocks.log, '@', Blocks.glass_pane, '&', Items.redstone });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemStereo, 1), new Object[] { " * ", "NJN", '*', Items.iron_ingot, 'N', Blocks.noteblock, 'J', Blocks.jukebox });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemCeilingLight, 4), new Object[] { "O", "S", "G", 'O', Blocks.obsidian, 'S', Blocks.stone, 'G', Blocks.glowstone });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemInkCartridge, 2), new Object[] { "SSS", "SIS", "SSS", 'I', new ItemStack(Items.dye, 1, 0), 'S', Blocks.stone });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemTree, 1), new Object[] { " L ", "LLL", " P ", 'L', Blocks.leaves, 'P', Items.flower_pot });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemPresent, 4, 0), new Object[] { "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.wool, 1, 15), 'P', FurnitureItems.itemPackage });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemPresent, 4, 1), new Object[] { "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.wool, 1, 11), 'P', FurnitureItems.itemPackage });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemPresent, 4, 2), new Object[] { "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.wool, 1, 12), 'P', FurnitureItems.itemPackage });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemPresent, 4, 3), new Object[] { "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.wool, 1, 9), 'P', FurnitureItems.itemPackage });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemPresent, 4, 4), new Object[] { "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.wool, 1, 7), 'P', FurnitureItems.itemPackage });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemPresent, 4, 5), new Object[] { "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.wool, 1, 13), 'P', FurnitureItems.itemPackage });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemPresent, 4, 6), new Object[] { "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.wool, 1, 3), 'P', FurnitureItems.itemPackage });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemPresent, 4, 7), new Object[] { "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.wool, 1, 5), 'P', FurnitureItems.itemPackage });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemPresent, 4, 8), new Object[] { "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.wool, 1, 2), 'P', FurnitureItems.itemPackage });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemPresent, 4, 9), new Object[] { "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.wool, 1, 1), 'P', FurnitureItems.itemPackage });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemPresent, 4, 10), new Object[] { "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.wool, 1, 6), 'P', FurnitureItems.itemPackage });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemPresent, 4, 11), new Object[] { "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.wool, 1, 10), 'P', FurnitureItems.itemPackage });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemPresent, 4, 12), new Object[] { "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.wool, 1, 14), 'P', FurnitureItems.itemPackage });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemPresent, 4, 13), new Object[] { "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.wool, 1, 8), 'P', FurnitureItems.itemPackage });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemPresent, 4, 14), new Object[] { "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.wool, 1, 0), 'P', FurnitureItems.itemPackage });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemPresent, 4, 15), new Object[] { "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.wool, 1, 4), 'P', FurnitureItems.itemPackage });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemToilet, 1), new Object[] { "QB ", "QQQ", " Q ", 'Q', Blocks.quartz_block, 'B', Blocks.stone_button });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemBasin, 1), new Object[] { "BIB", "QQQ", " Q ", 'Q', Blocks.quartz_block, 'B', Blocks.stone_button, 'I', Items.iron_ingot });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemWallCabinet, 1), new Object[] { "QQQ", "QCQ", "QQQ", 'Q', Blocks.quartz_block, 'C', Blocks.chest });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemShower, 1), new Object[] { "QGQ", "QGQ", "QGQ", 'Q', Blocks.quartz_block, 'G', Blocks.glass });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemBin, 1), new Object[] { "BSB", "I I", "III", 'B', Blocks.heavy_weighted_pressure_plate, 'S', Blocks.stone, 'I', Items.iron_ingot });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemBath, 1), new Object[] { "B  ", "Q Q", "QQQ", 'Q', Blocks.quartz_block, 'B', Blocks.stone_button });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemShowerHead, 1), new Object[] { "II ", " I ", "SSS", 'S', Blocks.stone, 'I', Items.iron_ingot });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemShowerHead, 1), new Object[] { "II ", " I ", "SSS", 'S', Blocks.stone, 'I', Items.iron_ingot });
		GameRegistry.addShapelessRecipe(new ItemStack(FurnitureItems.itemSoap), Items.clay_ball, new ItemStack(Items.dye, 1, 15), new ItemStack(Items.dye, 1, 12));
		GameRegistry.addShapelessRecipe(new ItemStack(FurnitureItems.itemSoapyWater), Items.water_bucket, FurnitureItems.itemSoap);
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemSuperSoapyWater), new Object[] { "GGG", "GSG", "GGG", 'G', Items.gold_ingot, 'S', FurnitureItems.itemSoapyWater });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemToaster), new Object[] { "QBQ", "QPS", "WWW", 'Q', Blocks.quartz_block, 'B', Blocks.iron_bars, 'S', Blocks.stone, 'P', Blocks.piston, 'W', new ItemStack(Blocks.wool, 1, 15) });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemMicrowave), new Object[] { "IIQ", "GGB", "IIQ", 'I', Items.iron_ingot, 'Q', Blocks.quartz_block, 'G', Blocks.glass_pane, 'B', Blocks.stone_button });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemBlender), new Object[] { "GBG", "GIG", "BBB", 'G', Blocks.glass_pane, 'B', new ItemStack(Blocks.wool, 1, 15), 'I', Items.iron_ingot });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemWashingMachine), new Object[] { "QQQ", "QGQ", "QFQ", 'Q', Blocks.quartz_block, 'G', Blocks.glass_pane, 'F', Blocks.furnace });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemDishWasher), new Object[] { "QQQ", "CBC", "CFC", 'Q', Blocks.quartz_block, 'G', Blocks.glass_pane, 'F', Blocks.furnace, 'C', new ItemStack(Blocks.stained_hardened_clay, 1, 9), 'B', Blocks.iron_bars });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemCounterDoored), new Object[] { "CCC", "QQQ", "QQQ", 'C', new ItemStack(Blocks.stained_hardened_clay, 1, 9), 'Q', Blocks.quartz_block });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemCounterSink), new Object[] { "CSC", "QQQ", "QQQ", 'C', new ItemStack(Blocks.stained_hardened_clay, 1, 9), 'S', FurnitureItems.itemBasin, 'Q', Blocks.quartz_block });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemKitchenCabinet), new Object[] { "QQQ", "HCH", "QQQ", 'Q', Blocks.quartz_block, 'C', Blocks.chest, 'H', new ItemStack(Blocks.stained_hardened_clay, 1, 9) });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemPlate), new Object[] { "Q Q", " Q ", 'Q', Blocks.quartz_block });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemCookieJar), new Object[] { " W ", "G G", "GGG", 'W', new ItemStack(Blocks.wool, 1, 15), 'G', Blocks.glass_pane });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemBarStool), new Object[] { "WWW", "CCC", "Q Q", 'W', new ItemStack(Blocks.wool, 1, 0), 'C', new ItemStack(Blocks.stained_hardened_clay, 1, 9), 'Q', Blocks.quartz_block });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemChoppingBoard), new Object[] { "LLL", "SSS", "LLL", 'L', new ItemStack(Blocks.log, 1, 0), 'S', new ItemStack(Blocks.wooden_slab, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemKnife), new Object[] { "I ", " S", 'I', Blocks.stone, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemCup), new Object[] { "G G", "G G", "GGG", 'G', Blocks.glass_pane });
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemMirror), new Object[] { "BIB", "IGI", "BIB", 'I', Items.iron_ingot, 'G', new ItemStack(Blocks.stained_glass_pane, 1, 0), 'B', Blocks.iron_block });
		GameRegistry.addSmelting(FurnitureItems.itemFlesh, new ItemStack(FurnitureItems.itemCookedFlesh), 0.05F);
		GameRegistry.addShapelessRecipe(new ItemStack(FurnitureItems.itemLog, 16), Items.wooden_axe, new ItemStack(Blocks.log, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(FurnitureItems.itemLog, 16), Items.stone_axe, new ItemStack(Blocks.log, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(FurnitureItems.itemLog, 16), Items.iron_axe, new ItemStack(Blocks.log, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(FurnitureItems.itemLog, 16), Items.golden_axe, new ItemStack(Blocks.log, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(FurnitureItems.itemLog, 16), Items.diamond_axe, new ItemStack(Blocks.log, 1, 0));
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemCandle), "WWW", "SSS", " C ", 'W', new ItemStack(Blocks.stained_hardened_clay, 1, 0), 'S', Blocks.stone, 'C', Blocks.cobblestone);
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemChimney), "CCC", "BBB", "BBB", 'C', Blocks.cobblestone, 'B', Blocks.brick_block);
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemFairyLight), "SSS", "RGR", 'S', Items.string, 'R', new ItemStack(Blocks.stained_glass, 1, 14), 'G', new ItemStack(Blocks.stained_glass, 1, 13));
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemGrandChair), "  W", "RRW", "W W", 'W', new ItemStack(Blocks.planks, 1, 5), 'R', new ItemStack(Blocks.wool, 1, 14));
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemMantelpiece), "BBB", "B B", "B B", 'B', Blocks.brick_block);
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemWreath), "LLL", "L L", "LLL", 'L', new ItemStack(Blocks.leaves, 1, 0));
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemEnderCabinet), new Object[]{ "OGO", "EBE", "OOO", 'O', Blocks.obsidian, 'G', Items.gold_ingot, 'E', Items.ender_eye, 'B', FurnitureItems.itemBedsideCabinet });
		GameRegistry.registerFuelHandler(new FuelHandler());
	}
}
