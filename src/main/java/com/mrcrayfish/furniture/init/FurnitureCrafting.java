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
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class FurnitureCrafting
{
	public static void register()
	{
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.table_oak, 1), "***", " * ", " * ", '*', new ItemStack(Blocks.PLANKS, 1, 0));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.table_spruce, 1), "***", " * ", " * ", '*', new ItemStack(Blocks.PLANKS, 1, 1));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.table_birch, 1), "***", " * ", " * ", '*', new ItemStack(Blocks.PLANKS, 1, 2));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.table_jungle, 1), "***", " * ", " * ", '*', new ItemStack(Blocks.PLANKS, 1, 3));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.table_acacia, 1), "***", " * ", " * ", '*', new ItemStack(Blocks.PLANKS, 1, 4));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.table_dark_oak, 1), "***", " * ", " * ", '*', new ItemStack(Blocks.PLANKS, 1, 5));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.table_stone, 1), "***", " * ", " * ", '*', Blocks.COBBLESTONE);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.chair_oak, 1), "*  ", "***", "* *", '*', new ItemStack(Blocks.PLANKS, 1, 0));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.chair_spruce, 1), "*  ", "***", "* *", '*', new ItemStack(Blocks.PLANKS, 1, 1));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.chair_birch, 1), "*  ", "***", "* *", '*', new ItemStack(Blocks.PLANKS, 1, 2));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.chair_jungle, 1), "*  ", "***", "* *", '*', new ItemStack(Blocks.PLANKS, 1, 3));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.chair_acacia, 1), "*  ", "***", "* *", '*', new ItemStack(Blocks.PLANKS, 1, 4));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.chair_dark_oak, 1), "*  ", "***", "* *", '*', new ItemStack(Blocks.PLANKS, 1, 5));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.chair_stone, 1), "*  ", "***", "* *", '*', Blocks.COBBLESTONE);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.couch, 1), "***", "***", '*', new ItemStack(Blocks.WOOL, 1, 0));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.freezer, 1), "***", "*#*", "*@*", '*', Blocks.IRON_BLOCK, '#', Blocks.CHEST, '@', Blocks.FURNACE);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.cabinet_oak, 1), "***", "*@*", "***", '*', new ItemStack(Blocks.PLANKS, 1, 0), '@', Blocks.CHEST);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.cabinet_spruce, 1), "***", "*@*", "***", '*', new ItemStack(Blocks.PLANKS, 1, 1), '@', Blocks.CHEST);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.cabinet_birch, 1), "***", "*@*", "***", '*', new ItemStack(Blocks.PLANKS, 1, 2), '@', Blocks.CHEST);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.cabinet_jungle, 1), "***", "*@*", "***", '*', new ItemStack(Blocks.PLANKS, 1, 3), '@', Blocks.CHEST);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.cabinet_acacia, 1), "***", "*@*", "***", '*', new ItemStack(Blocks.PLANKS, 1, 4), '@', Blocks.CHEST);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.cabinet_dark_oak, 1), "***", "*@*", "***", '*', new ItemStack(Blocks.PLANKS, 1, 5), '@', Blocks.CHEST);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.curtains, 2), "@@@", "* *", "@ @", '*', Items.GOLD_INGOT, '@', new ItemStack(Blocks.WOOL, 1, 14));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.blinds, 2), "***", "***", "***", '*', Items.STICK);
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemCoolPack, 2), "***", "*@*", "***", '*', Blocks.GLASS, '@', Items.WATER_BUCKET);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.coffee_table_oak, 1), "***", "* *", '*', new ItemStack(Blocks.PLANKS, 1, 0));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.coffee_table_spruce, 1), "***", "* *", '*', new ItemStack(Blocks.PLANKS, 1, 1));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.coffee_table_birch, 1), "***", "* *", '*', new ItemStack(Blocks.PLANKS, 1, 2));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.coffee_table_jungle, 1), "***", "* *", '*', new ItemStack(Blocks.PLANKS, 1, 3));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.coffee_table_acacia, 1), "***", "* *", '*', new ItemStack(Blocks.PLANKS, 1, 4));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.coffee_table_dark_oak, 1), "***", "* *", '*', new ItemStack(Blocks.PLANKS, 1, 5));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.coffee_table_stone, 1), "***", "* *", '*', Blocks.COBBLESTONE);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.lamp_off, 2), "***", "*@*", " & ", '*', Blocks.WOOL, '@', Blocks.GLOWSTONE, '&', Blocks.OBSIDIAN);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.bedside_cabinet_oak, 1), "***", "*@*", "*@*", '*', new ItemStack(Blocks.PLANKS, 1, 0), '@', Blocks.CHEST);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.bedside_cabinet_spruce, 1), "***", "*@*", "*@*", '*', new ItemStack(Blocks.PLANKS, 1, 1), '@', Blocks.CHEST);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.bedside_cabinet_birch, 1), "***", "*@*", "*@*", '*', new ItemStack(Blocks.PLANKS, 1, 2), '@', Blocks.CHEST);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.bedside_cabinet_jungle, 1), "***", "*@*", "*@*", '*', new ItemStack(Blocks.PLANKS, 1, 3), '@', Blocks.CHEST);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.bedside_cabinet_acacia, 1), "***", "*@*", "*@*", '*', new ItemStack(Blocks.PLANKS, 1, 4), '@', Blocks.CHEST);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.bedside_cabinet_dark_oak, 1), "***", "*@*", "*@*", '*', new ItemStack(Blocks.PLANKS, 1, 5), '@', Blocks.CHEST);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.oven, 1), "***", "*@*", "***", '*', Blocks.IRON_BLOCK, '@', Blocks.FURNACE);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.range_hood, 1), " * ", " * ", "*@*", '*', Items.IRON_INGOT, '@', Blocks.GLOWSTONE);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.hedge_oak, 4), "***", "***", '*', new ItemStack(Blocks.LEAVES, 1, 0));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.hedge_spruce, 4), "***", "***", '*', new ItemStack(Blocks.LEAVES, 1, 1));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.hedge_birch, 4), "***", "***", '*', new ItemStack(Blocks.LEAVES, 1, 2));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.hedge_jungle, 4), "***", "***", '*', new ItemStack(Blocks.LEAVES, 1, 3));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.hedge_acacia, 4), "***", "***", '*', new ItemStack(Blocks.LEAVES2, 1, 0));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.hedge_dark_oak, 4), "***", "***", '*', new ItemStack(Blocks.LEAVES2, 1, 1));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.bird_bath, 1), "***", " * ", " * ", '*', Blocks.STONE);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.stone_path, 8), "**", '*', Blocks.COBBLESTONE);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.tap, 1), " @ ", "***", "  *", '*', Blocks.STONE, '@', Items.IRON_INGOT);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.mail_box, 1), "*@*", "***", " * ", '*', Blocks.PLANKS, '@', Items.BOOK);
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemEnvelope, 1), "**", '*', Items.PAPER);
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemPackage, 1), "***", "***", '*', Items.PAPER);
		GameRegistry.addShapelessRecipe(new ItemStack(FurnitureBlocks.door_bell, 1), Blocks.NOTEBLOCK, Blocks.STONE_BUTTON);
		GameRegistry.addShapelessRecipe(new ItemStack(FurnitureBlocks.white_fence, 2), Blocks.OAK_FENCE, new ItemStack(Items.DYE, 1, 15));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.computer, 1), "***", "*@*", "*&*", '*', Blocks.IRON_BLOCK, '@', Blocks.GLASS_PANE, '&', Items.REDSTONE);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.printer, 1), "*@*", "&R&", "***", '*', Blocks.STONE, '@', Items.PAPER, '&', Blocks.IRON_BLOCK, 'R', Items.REDSTONE);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.electric_fence, 8), "***", "*@*", "*#*", '*', Items.IRON_INGOT, '@', FurnitureBlocks.white_fence, '#', Blocks.REDSTONE_TORCH);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.fire_alarm_off, 1), "*#*", "*@*", '*', Items.IRON_INGOT, '@', Blocks.NOTEBLOCK, '#', Items.REDSTONE);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.tv, 1), "***", "*@*", "*&*", '*', Blocks.LOG, '@', Blocks.GLASS_PANE, '&', Items.REDSTONE);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.stereo, 1), " * ", "NJN", '*', Items.IRON_INGOT, 'N', Blocks.NOTEBLOCK, 'J', Blocks.JUKEBOX);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.ceiling_light_off, 4), "O", "S", "G", 'O', Blocks.OBSIDIAN, 'S', Blocks.STONE, 'G', Blocks.GLOWSTONE);
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemInkCartridge, 2), "SSS", "SIS", "SSS", 'I', new ItemStack(Items.DYE, 1, 0), 'S', Blocks.STONE);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.tree_bottom, 1), " L ", "LLL", " P ", 'L', Blocks.LEAVES, 'P', Items.FLOWER_POT);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.present, 4, 0), "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.WOOL, 1, 15), 'P', FurnitureItems.itemPackage);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.present, 4, 1), "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.WOOL, 1, 11), 'P', FurnitureItems.itemPackage);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.present, 4, 2), "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.WOOL, 1, 12), 'P', FurnitureItems.itemPackage);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.present, 4, 3), "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.WOOL, 1, 9), 'P', FurnitureItems.itemPackage);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.present, 4, 4), "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.WOOL, 1, 7), 'P', FurnitureItems.itemPackage);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.present, 4, 5), "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.WOOL, 1, 13), 'P', FurnitureItems.itemPackage);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.present, 4, 6), "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.WOOL, 1, 3), 'P', FurnitureItems.itemPackage);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.present, 4, 7), "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.WOOL, 1, 5), 'P', FurnitureItems.itemPackage);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.present, 4, 8), "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.WOOL, 1, 2), 'P', FurnitureItems.itemPackage);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.present, 4, 9), "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.WOOL, 1, 1), 'P', FurnitureItems.itemPackage);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.present, 4, 10), "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.WOOL, 1, 6), 'P', FurnitureItems.itemPackage);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.present, 4, 11), "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.WOOL, 1, 10), 'P', FurnitureItems.itemPackage);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.present, 4, 12), "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.WOOL, 1, 14), 'P', FurnitureItems.itemPackage);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.present, 4, 13), "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.WOOL, 1, 8), 'P', FurnitureItems.itemPackage);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.present, 4, 14), "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.WOOL, 1, 0), 'P', FurnitureItems.itemPackage);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.present, 4, 15), "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.WOOL, 1, 4), 'P', FurnitureItems.itemPackage);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.toilet, 1), "QB ", "QQQ", " Q ", 'Q', Blocks.QUARTZ_BLOCK, 'B', Blocks.STONE_BUTTON);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.basin, 1), "BIB", "QQQ", " Q ", 'Q', Blocks.QUARTZ_BLOCK, 'B', Blocks.STONE_BUTTON, 'I', Items.IRON_INGOT);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.wall_cabinet, 1), "QQQ", "QCQ", "QQQ", 'Q', Blocks.QUARTZ_BLOCK, 'C', Blocks.CHEST);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.shower_bottom, 1), "QGQ", "QGQ", "QGQ", 'Q', Blocks.QUARTZ_BLOCK, 'G', Blocks.GLASS);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.bin, 1), "BSB", "I I", "III", 'B', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, 'S', Blocks.STONE, 'I', Items.IRON_INGOT);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.bath_1, 1), "B  ", "Q Q", "QQQ", 'Q', Blocks.QUARTZ_BLOCK, 'B', Blocks.STONE_BUTTON);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.shower_head_off, 1), "II ", " I ", "SSS", 'S', Blocks.STONE, 'I', Items.IRON_INGOT);
		GameRegistry.addShapelessRecipe(new ItemStack(FurnitureItems.itemSoap), Items.CLAY_BALL, new ItemStack(Items.DYE, 1, 15), new ItemStack(Items.DYE, 1, 12));
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemSuperSoapyWater), "GGG", "GSG", "GGG", 'G', Items.GOLD_INGOT, 'S', FurnitureItems.itemSoapyWater);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.toaster), "QBQ", "QPS", "WWW", 'Q', Blocks.QUARTZ_BLOCK, 'B', Blocks.IRON_BARS, 'S', Blocks.STONE, 'P', Blocks.PISTON, 'W', new ItemStack(Blocks.WOOL, 1, 15));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.microwave), "IIQ", "GGB", "IIQ", 'I', Items.IRON_INGOT, 'Q', Blocks.QUARTZ_BLOCK, 'G', Blocks.GLASS_PANE, 'B', Blocks.STONE_BUTTON);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.blender), "GBG", "GIG", "BBB", 'G', Blocks.GLASS_PANE, 'B', new ItemStack(Blocks.WOOL, 1, 15), 'I', Items.IRON_INGOT);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.washing_machine), "QQQ", "QGQ", "QFQ", 'Q', Blocks.QUARTZ_BLOCK, 'G', Blocks.GLASS_PANE, 'F', Blocks.FURNACE);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.dishwasher), "QQQ", "CBC", "CFC", 'Q', Blocks.QUARTZ_BLOCK, 'G', Blocks.GLASS_PANE, 'F', Blocks.FURNACE, 'C', new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 9), 'B', Blocks.IRON_BARS);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.counter), "CCC", "QQQ", "QQQ", 'C', new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 9), 'Q', Blocks.QUARTZ_BLOCK);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.counter_sink), "CSC", "QQQ", "QQQ", 'C', new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 9), 'S', FurnitureBlocks.basin, 'Q', Blocks.QUARTZ_BLOCK);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.kitchen_cabinet), "QQQ", "HCH", "QQQ", 'Q', Blocks.QUARTZ_BLOCK, 'C', Blocks.CHEST, 'H', new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 9));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.plate), "Q Q", " Q ", 'Q', Blocks.QUARTZ_BLOCK);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.cookie_jar), " W ", "G G", "GGG", 'W', new ItemStack(Blocks.WOOL, 1, 15), 'G', Blocks.GLASS_PANE);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.bar_stool), "WWW", "CCC", "Q Q", 'W', new ItemStack(Blocks.WOOL, 1, 0), 'C', new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 9), 'Q', Blocks.QUARTZ_BLOCK);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.chopping_board), "LLL", "SSS", "LLL", 'L', new ItemStack(Blocks.LOG, 1, 0), 'S', new ItemStack(Blocks.WOODEN_SLAB, 1, 0));
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemKnife), "I ", " S", 'I', Blocks.STONE, 'S', Items.STICK);
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemCup), "G G", "G G", "GGG", 'G', Blocks.GLASS_PANE);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.mirror), "BIB", "IGI", "BIB", 'I', Items.IRON_INGOT, 'G', new ItemStack(Blocks.STAINED_GLASS_PANE, 1, 0), 'B', Blocks.IRON_BLOCK);
		GameRegistry.addSmelting(FurnitureItems.itemFlesh, new ItemStack(FurnitureItems.itemCookedFlesh), 0.05F);
		GameRegistry.addShapelessRecipe(new ItemStack(FurnitureItems.itemLog, 16), Items.WOODEN_AXE, new ItemStack(Blocks.LOG, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(FurnitureItems.itemLog, 16), Items.STONE_AXE, new ItemStack(Blocks.LOG, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(FurnitureItems.itemLog, 16), Items.IRON_AXE, new ItemStack(Blocks.LOG, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(FurnitureItems.itemLog, 16), Items.GOLDEN_AXE, new ItemStack(Blocks.LOG, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(FurnitureItems.itemLog, 16), Items.DIAMOND_AXE, new ItemStack(Blocks.LOG, 1, 0));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.candle), "WWW", "SSS", " C ", 'W', new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 0), 'S', Blocks.STONE, 'C', Blocks.COBBLESTONE);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.chimney), "CCC", "BBB", "BBB", 'C', Blocks.COBBLESTONE, 'B', Blocks.BRICK_BLOCK);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.fairy_light), "SSS", "RGR", 'S', Items.STRING, 'R', new ItemStack(Blocks.STAINED_GLASS, 1, 14), 'G', new ItemStack(Blocks.STAINED_GLASS, 1, 13));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.grand_chair_bottom), "  W", "RRW", "W W", 'W', new ItemStack(Blocks.PLANKS, 1, 5), 'R', new ItemStack(Blocks.WOOL, 1, 14));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.mantel_piece), "BBB", "B B", "B B", 'B', Blocks.BRICK_BLOCK);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.wreath), "LLL", "L L", "LLL", 'L', new ItemStack(Blocks.LEAVES, 1, 0));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.trampoline), "WMW", "SWS", "S S", 'W', new ItemStack(Blocks.WOOL, 1, 11), 'M', new ItemStack(Items.STRING), 'S', new ItemStack(Blocks.STONE));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.crate), "DOD", "O O", "DOD", 'D', new ItemStack(Blocks.PLANKS, 1, 0), 'O', new ItemStack(Blocks.PLANKS, 1, 5));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.bench), "LPL", "P P", 'L', new ItemStack(Blocks.LOG, 1, 0), 'P', new ItemStack(Blocks.PLANKS, 1, 0));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.table_outdoor), "LGL", "P P", "P P", 'L', new ItemStack(Blocks.LOG, 1, 0), 'P', new ItemStack(Blocks.PLANKS, 1, 0), 'G', new ItemStack(Blocks.STAINED_GLASS, 1, 12));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.grill), "CMC", "SCS", "SMS", 'C', new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 14), 'M', new ItemStack(Blocks.IRON_BARS), 'S', new ItemStack(Blocks.STONE));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.divingboard_base), "C  ", "QQQ", "C  ", 'C', new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 9), 'Q', new ItemStack(Blocks.QUARTZ_BLOCK));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.door_mat), "B", "C", 'B', new ItemStack(Items.DYE, 1, 15), 'C', new ItemStack(Blocks.CARPET, 1, 12));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.esky), "BBB", "Q Q", "BBB", 'B', new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 11), 'Q', new ItemStack(Blocks.QUARTZ_BLOCK));
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemSpatula), "  M", " I ", "I  ", 'M', new ItemStack(Blocks.IRON_BARS), 'I', new ItemStack(Items.IRON_INGOT));
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemSausage, 16), "  B", " B ", "B  ", 'B', new ItemStack(Items.BEEF));
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemKebab, 2), "  S", " C ", "S  ", 'S', new ItemStack(Items.STICK), 'C', new ItemStack(Items.CHICKEN));
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemCrowBar), "  I", " C ", "I  ", 'C', new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 14), 'I', new ItemStack(Items.IRON_INGOT));
		GameRegistry.registerFuelHandler(new FuelHandler());

		GameRegistry.addRecipe(new IRecipe()
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
			public int getRecipeSize()
			{
				return 2;
			}

			@Override
			public ItemStack getRecipeOutput()
			{
				return ItemStack.EMPTY;
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
		});
	}
}
