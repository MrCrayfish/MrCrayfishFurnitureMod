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
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.table_oak, 1), "***", " * ", " * ", '*', new ItemStack(Blocks.planks, 1, 0));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.table_spruce, 1), "***", " * ", " * ", '*', new ItemStack(Blocks.planks, 1, 1));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.table_birch, 1), "***", " * ", " * ", '*', new ItemStack(Blocks.planks, 1, 2));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.table_jungle, 1), "***", " * ", " * ", '*', new ItemStack(Blocks.planks, 1, 3));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.table_acacia, 1), "***", " * ", " * ", '*', new ItemStack(Blocks.planks, 1, 4));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.table_dark_oak, 1), "***", " * ", " * ", '*', new ItemStack(Blocks.planks, 1, 5));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.table_stone, 1), "***", " * ", " * ", '*', Blocks.cobblestone);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.chair_oak, 1), "*  ", "***", "* *", '*', new ItemStack(Blocks.planks, 1, 0));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.chair_spruce, 1), "*  ", "***", "* *", '*', new ItemStack(Blocks.planks, 1, 1));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.chair_birch, 1), "*  ", "***", "* *", '*', new ItemStack(Blocks.planks, 1, 2));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.chair_jungle, 1), "*  ", "***", "* *", '*', new ItemStack(Blocks.planks, 1, 3));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.chair_acacia, 1), "*  ", "***", "* *", '*', new ItemStack(Blocks.planks, 1, 4));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.chair_dark_oak, 1), "*  ", "***", "* *", '*', new ItemStack(Blocks.planks, 1, 5));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.chair_stone, 1), "*  ", "***", "* *", '*', Blocks.cobblestone);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.couch, 1), "***", "***", '*', new ItemStack(Blocks.wool, 1, 0));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.freezer, 1), "***", "*#*", "*@*", '*', Blocks.iron_block, '#', Blocks.chest, '@', Blocks.furnace);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.cabinet_oak, 1), "***", "*@*", "***", '*', new ItemStack(Blocks.planks, 1, 0), '@', Blocks.chest);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.cabinet_spruce, 1), "***", "*@*", "***", '*', new ItemStack(Blocks.planks, 1, 1), '@', Blocks.chest);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.cabinet_birch, 1), "***", "*@*", "***", '*', new ItemStack(Blocks.planks, 1, 2), '@', Blocks.chest);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.cabinet_jungle, 1), "***", "*@*", "***", '*', new ItemStack(Blocks.planks, 1, 3), '@', Blocks.chest);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.cabinet_acacia, 1), "***", "*@*", "***", '*', new ItemStack(Blocks.planks, 1, 4), '@', Blocks.chest);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.cabinet_dark_oak, 1), "***", "*@*", "***", '*', new ItemStack(Blocks.planks, 1, 5), '@', Blocks.chest);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.curtains, 2), "@@@", "* *", "@ @", '*', Items.gold_ingot, '@', new ItemStack(Blocks.wool, 1, 14));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.blinds, 2), "***", "***", "***", '*', Items.stick);
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemCoolPack, 2), "***", "*@*", "***", '*', Blocks.glass, '@', Items.water_bucket);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.coffee_table_oak, 1), "***", "* *", '*', new ItemStack(Blocks.planks, 1, 0));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.coffee_table_spruce, 1), "***", "* *", '*', new ItemStack(Blocks.planks, 1, 1));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.coffee_table_birch, 1), "***", "* *", '*', new ItemStack(Blocks.planks, 1, 2));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.coffee_table_jungle, 1), "***", "* *", '*', new ItemStack(Blocks.planks, 1, 3));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.coffee_table_acacia, 1), "***", "* *", '*', new ItemStack(Blocks.planks, 1, 4));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.coffee_table_dark_oak, 1), "***", "* *", '*', new ItemStack(Blocks.planks, 1, 5));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.coffee_table_stone, 1), "***", "* *", '*', Blocks.cobblestone);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.lamp_off, 2), "***", "*@*", " & ", '*', Blocks.wool, '@', Blocks.glowstone, '&', Blocks.obsidian);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.bedside_cabinet_oak, 1), "***", "*@*", "*@*", '*', new ItemStack(Blocks.planks, 1, 0), '@', Blocks.chest);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.bedside_cabinet_spruce, 1), "***", "*@*", "*@*", '*', new ItemStack(Blocks.planks, 1, 1), '@', Blocks.chest);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.bedside_cabinet_birch, 1), "***", "*@*", "*@*", '*', new ItemStack(Blocks.planks, 1, 2), '@', Blocks.chest);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.bedside_cabinet_jungle, 1), "***", "*@*", "*@*", '*', new ItemStack(Blocks.planks, 1, 3), '@', Blocks.chest);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.bedside_cabinet_acacia, 1), "***", "*@*", "*@*", '*', new ItemStack(Blocks.planks, 1, 4), '@', Blocks.chest);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.bedside_cabinet_dark_oak, 1), "***", "*@*", "*@*", '*', new ItemStack(Blocks.planks, 1, 5), '@', Blocks.chest);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.oven, 1), "***", "*@*", "***", '*', Blocks.iron_block, '@', Blocks.furnace);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.range_hood, 1), " * ", " * ", "*@*", '*', Items.iron_ingot, '@', Blocks.glowstone);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.hedge_oak, 4), "***", "***", '*', new ItemStack(Blocks.leaves, 1, 0));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.hedge_spruce, 4), "***", "***", '*', new ItemStack(Blocks.leaves, 1, 1));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.hedge_birch, 4), "***", "***", '*', new ItemStack(Blocks.leaves, 1, 2));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.hedge_jungle, 4), "***", "***", '*', new ItemStack(Blocks.leaves, 1, 3));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.hedge_acacia, 4), "***", "***", '*', new ItemStack(Blocks.leaves2, 1, 0));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.hedge_dark_oak, 4), "***", "***", '*', new ItemStack(Blocks.leaves2, 1, 1));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.bird_bath, 1), "***", " * ", " * ", '*', Blocks.stone);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.stone_path, 8), "**", '*', Blocks.cobblestone);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.tap, 1), " @ ", "***", "  *", '*', Blocks.stone, '@', Items.iron_ingot);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.mail_box, 1), "*@*", "***", " * ", '*', Blocks.planks, '@', Items.book);
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemEnvelope, 1), "**", '*', Items.paper);
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemPackage, 1), "***", "***", '*', Items.paper);
		GameRegistry.addShapelessRecipe(new ItemStack(FurnitureBlocks.door_bell, 1), Blocks.noteblock, Blocks.stone_button);
		GameRegistry.addShapelessRecipe(new ItemStack(FurnitureBlocks.white_fence, 2), Blocks.oak_fence, new ItemStack(Items.dye, 1, 15));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.computer, 1), "***", "*@*", "*&*", '*', Blocks.iron_block, '@', Blocks.glass_pane, '&', Items.redstone);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.printer, 1), "*@*", "&R&", "***", '*', Blocks.stone, '@', Items.paper, '&', Blocks.iron_block, 'R', Items.redstone);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.electric_fence, 8), "***", "*@*", "*#*", '*', Items.iron_ingot, '@', FurnitureBlocks.white_fence, '#', Blocks.redstone_torch);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.fire_alarm_off, 1), "*#*", "*@*", '*', Items.iron_ingot, '@', Blocks.noteblock, '#', Items.redstone);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.tv, 1), "***", "*@*", "*&*", '*', Blocks.log, '@', Blocks.glass_pane, '&', Items.redstone);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.stereo, 1), " * ", "NJN", '*', Items.iron_ingot, 'N', Blocks.noteblock, 'J', Blocks.jukebox);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.ceiling_light_off, 4), "O", "S", "G", 'O', Blocks.obsidian, 'S', Blocks.stone, 'G', Blocks.glowstone);
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemInkCartridge, 2), "SSS", "SIS", "SSS", 'I', new ItemStack(Items.dye, 1, 0), 'S', Blocks.stone);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.tree_bottom, 1), " L ", "LLL", " P ", 'L', Blocks.leaves, 'P', Items.flower_pot);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.present, 4, 0), "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.wool, 1, 15), 'P', FurnitureItems.itemPackage);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.present, 4, 1), "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.wool, 1, 11), 'P', FurnitureItems.itemPackage);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.present, 4, 2), "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.wool, 1, 12), 'P', FurnitureItems.itemPackage);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.present, 4, 3), "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.wool, 1, 9), 'P', FurnitureItems.itemPackage);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.present, 4, 4), "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.wool, 1, 7), 'P', FurnitureItems.itemPackage);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.present, 4, 5), "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.wool, 1, 13), 'P', FurnitureItems.itemPackage);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.present, 4, 6), "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.wool, 1, 3), 'P', FurnitureItems.itemPackage);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.present, 4, 7), "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.wool, 1, 5), 'P', FurnitureItems.itemPackage);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.present, 4, 8), "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.wool, 1, 2), 'P', FurnitureItems.itemPackage);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.present, 4, 9), "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.wool, 1, 1), 'P', FurnitureItems.itemPackage);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.present, 4, 10), "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.wool, 1, 6), 'P', FurnitureItems.itemPackage);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.present, 4, 11), "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.wool, 1, 10), 'P', FurnitureItems.itemPackage);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.present, 4, 12), "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.wool, 1, 14), 'P', FurnitureItems.itemPackage);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.present, 4, 13), "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.wool, 1, 8), 'P', FurnitureItems.itemPackage);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.present, 4, 14), "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.wool, 1, 0), 'P', FurnitureItems.itemPackage);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.present, 4, 15), "WWW", "WPW", "WWW", 'W', new ItemStack(Blocks.wool, 1, 4), 'P', FurnitureItems.itemPackage);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.toilet, 1), "QB ", "QQQ", " Q ", 'Q', Blocks.quartz_block, 'B', Blocks.stone_button);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.basin, 1), "BIB", "QQQ", " Q ", 'Q', Blocks.quartz_block, 'B', Blocks.stone_button, 'I', Items.iron_ingot);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.wall_cabinet, 1), "QQQ", "QCQ", "QQQ", 'Q', Blocks.quartz_block, 'C', Blocks.chest);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.shower_bottom, 1), "QGQ", "QGQ", "QGQ", 'Q', Blocks.quartz_block, 'G', Blocks.glass);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.bin, 1), "BSB", "I I", "III", 'B', Blocks.heavy_weighted_pressure_plate, 'S', Blocks.stone, 'I', Items.iron_ingot);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.bath_1, 1), "B  ", "Q Q", "QQQ", 'Q', Blocks.quartz_block, 'B', Blocks.stone_button);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.shower_head_off, 1), "II ", " I ", "SSS", 'S', Blocks.stone, 'I', Items.iron_ingot);
		GameRegistry.addShapelessRecipe(new ItemStack(FurnitureItems.itemSoap), Items.clay_ball, new ItemStack(Items.dye, 1, 15), new ItemStack(Items.dye, 1, 12));
		GameRegistry.addShapelessRecipe(new ItemStack(FurnitureItems.itemSoapyWater), Items.water_bucket, FurnitureItems.itemSoap);
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemSuperSoapyWater), "GGG", "GSG", "GGG", 'G', Items.gold_ingot, 'S', FurnitureItems.itemSoapyWater);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.toaster), "QBQ", "QPS", "WWW", 'Q', Blocks.quartz_block, 'B', Blocks.iron_bars, 'S', Blocks.stone, 'P', Blocks.piston, 'W', new ItemStack(Blocks.wool, 1, 15));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.microwave), "IIQ", "GGB", "IIQ", 'I', Items.iron_ingot, 'Q', Blocks.quartz_block, 'G', Blocks.glass_pane, 'B', Blocks.stone_button);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.blender), "GBG", "GIG", "BBB", 'G', Blocks.glass_pane, 'B', new ItemStack(Blocks.wool, 1, 15), 'I', Items.iron_ingot);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.washing_machine), "QQQ", "QGQ", "QFQ", 'Q', Blocks.quartz_block, 'G', Blocks.glass_pane, 'F', Blocks.furnace);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.dishwasher), "QQQ", "CBC", "CFC", 'Q', Blocks.quartz_block, 'G', Blocks.glass_pane, 'F', Blocks.furnace, 'C', new ItemStack(Blocks.stained_hardened_clay, 1, 9), 'B', Blocks.iron_bars);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.counter), "CCC", "QQQ", "QQQ", 'C', new ItemStack(Blocks.stained_hardened_clay, 1, 9), 'Q', Blocks.quartz_block);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.counter_sink), "CSC", "QQQ", "QQQ", 'C', new ItemStack(Blocks.stained_hardened_clay, 1, 9), 'S', FurnitureBlocks.basin, 'Q', Blocks.quartz_block);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.kitchen_cabinet), "QQQ", "HCH", "QQQ", 'Q', Blocks.quartz_block, 'C', Blocks.chest, 'H', new ItemStack(Blocks.stained_hardened_clay, 1, 9));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.plate), "Q Q", " Q ", 'Q', Blocks.quartz_block);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.cookie_jar), " W ", "G G", "GGG", 'W', new ItemStack(Blocks.wool, 1, 15), 'G', Blocks.glass_pane);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.bar_stool), "WWW", "CCC", "Q Q", 'W', new ItemStack(Blocks.wool, 1, 0), 'C', new ItemStack(Blocks.stained_hardened_clay, 1, 9), 'Q', Blocks.quartz_block);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.chopping_board), "LLL", "SSS", "LLL", 'L', new ItemStack(Blocks.log, 1, 0), 'S', new ItemStack(Blocks.wooden_slab, 1, 0));
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemKnife), "I ", " S", 'I', Blocks.stone, 'S', Items.stick);
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemCup), "G G", "G G", "GGG", 'G', Blocks.glass_pane);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.mirror), "BIB", "IGI", "BIB", 'I', Items.iron_ingot, 'G', new ItemStack(Blocks.stained_glass_pane, 1, 0), 'B', Blocks.iron_block);
		GameRegistry.addSmelting(FurnitureItems.itemFlesh, new ItemStack(FurnitureItems.itemCookedFlesh), 0.05F);
		GameRegistry.addShapelessRecipe(new ItemStack(FurnitureItems.itemLog, 16), Items.wooden_axe, new ItemStack(Blocks.log, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(FurnitureItems.itemLog, 16), Items.stone_axe, new ItemStack(Blocks.log, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(FurnitureItems.itemLog, 16), Items.iron_axe, new ItemStack(Blocks.log, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(FurnitureItems.itemLog, 16), Items.golden_axe, new ItemStack(Blocks.log, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(FurnitureItems.itemLog, 16), Items.diamond_axe, new ItemStack(Blocks.log, 1, 0));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.candle), "WWW", "SSS", " C ", 'W', new ItemStack(Blocks.stained_hardened_clay, 1, 0), 'S', Blocks.stone, 'C', Blocks.cobblestone);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.chimney), "CCC", "BBB", "BBB", 'C', Blocks.cobblestone, 'B', Blocks.brick_block);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.fairy_light), "SSS", "RGR", 'S', Items.string, 'R', new ItemStack(Blocks.stained_glass, 1, 14), 'G', new ItemStack(Blocks.stained_glass, 1, 13));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.grand_chair_bottom), "  W", "RRW", "W W", 'W', new ItemStack(Blocks.planks, 1, 5), 'R', new ItemStack(Blocks.wool, 1, 14));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.mantel_piece), "BBB", "B B", "B B", 'B', Blocks.brick_block);
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.wreath), "LLL", "L L", "LLL", 'L', new ItemStack(Blocks.leaves, 1, 0));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.trampoline), "WMW", "SWS", "S S", 'W', new ItemStack(Blocks.wool, 1, 11), 'M', new ItemStack(Items.string), 'S', new ItemStack(Blocks.stone));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.crate), "DOD", "O O", "DOD", 'D', new ItemStack(Blocks.planks, 1, 0), 'O', new ItemStack(Blocks.planks, 1, 5));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.bench), "LPL", "P P", 'L', new ItemStack(Blocks.log, 1, 0), 'P', new ItemStack(Blocks.planks, 1, 0));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.table_outdoor), "LGL", "P P", "P P", 'L', new ItemStack(Blocks.log, 1, 0), 'P', new ItemStack(Blocks.planks, 1, 0), 'G', new ItemStack(Blocks.stained_glass, 1, 12));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.grill), "CMC", "SCS", "SMS", 'C', new ItemStack(Blocks.stained_hardened_clay, 1, 14), 'M', new ItemStack(Blocks.iron_bars), 'S', new ItemStack(Blocks.stone));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.divingboard_base), "C  ", "QQQ", "C  ", 'C', new ItemStack(Blocks.stained_hardened_clay, 1, 9), 'Q', new ItemStack(Blocks.quartz_block));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.door_mat), "B", "C", 'B', new ItemStack(Items.dye, 1, 15), 'C', new ItemStack(Blocks.carpet, 1, 12));
		GameRegistry.addRecipe(new ItemStack(FurnitureBlocks.esky), "BBB", "Q Q", "BBB", 'B', new ItemStack(Blocks.stained_hardened_clay, 1, 11), 'Q', new ItemStack(Blocks.quartz_block));
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemSpatula), "  M", " I ", "I  ", 'M', new ItemStack(Blocks.iron_bars), 'I', new ItemStack(Items.iron_ingot));
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemSausage, 16), "  B", " B ", "B  ", 'M', new ItemStack(Items.beef));
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemKebab, 2), "  S", " C ", "S  ", 'S', new ItemStack(Items.stick), 'C', new ItemStack(Items.chicken));
		GameRegistry.addRecipe(new ItemStack(FurnitureItems.itemCrowBar), "  I", " C ", "I  ", 'C', new ItemStack(Blocks.stained_hardened_clay, 1, 14), 'I', new ItemStack(Items.iron_ingot));
		GameRegistry.registerFuelHandler(new FuelHandler());
	}
}
