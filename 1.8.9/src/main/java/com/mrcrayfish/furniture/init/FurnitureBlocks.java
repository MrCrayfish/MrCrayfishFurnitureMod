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

import java.util.ArrayList;
import java.util.List;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.blocks.BlockBarStool;
import com.mrcrayfish.furniture.blocks.BlockBasin;
import com.mrcrayfish.furniture.blocks.BlockBath;
import com.mrcrayfish.furniture.blocks.BlockBedsideCabinet;
import com.mrcrayfish.furniture.blocks.BlockBench;
import com.mrcrayfish.furniture.blocks.BlockBin;
import com.mrcrayfish.furniture.blocks.BlockBirdBath;
import com.mrcrayfish.furniture.blocks.BlockBlender;
import com.mrcrayfish.furniture.blocks.BlockBlinds;
import com.mrcrayfish.furniture.blocks.BlockCabinet;
import com.mrcrayfish.furniture.blocks.BlockCabinetKitchen;
import com.mrcrayfish.furniture.blocks.BlockCandle;
import com.mrcrayfish.furniture.blocks.BlockCeilingLight;
import com.mrcrayfish.furniture.blocks.BlockChair;
import com.mrcrayfish.furniture.blocks.BlockChimney;
import com.mrcrayfish.furniture.blocks.BlockChoppingBoard;
import com.mrcrayfish.furniture.blocks.BlockCoffeeTable;
import com.mrcrayfish.furniture.blocks.BlockComputer;
import com.mrcrayfish.furniture.blocks.BlockCookieJar;
import com.mrcrayfish.furniture.blocks.BlockCouchJeb;
import com.mrcrayfish.furniture.blocks.BlockCouchNormal;
import com.mrcrayfish.furniture.blocks.BlockCounter;
import com.mrcrayfish.furniture.blocks.BlockCounterSink;
import com.mrcrayfish.furniture.blocks.BlockCrate;
import com.mrcrayfish.furniture.blocks.BlockCup;
import com.mrcrayfish.furniture.blocks.BlockCurtainsClosed;
import com.mrcrayfish.furniture.blocks.BlockCurtainsOpen;
import com.mrcrayfish.furniture.blocks.BlockDishwasher;
import com.mrcrayfish.furniture.blocks.BlockDivingboard;
import com.mrcrayfish.furniture.blocks.BlockDoorBell;
import com.mrcrayfish.furniture.blocks.BlockDoorMat;
import com.mrcrayfish.furniture.blocks.BlockElectricFence;
import com.mrcrayfish.furniture.blocks.BlockEsky;
import com.mrcrayfish.furniture.blocks.BlockFairyLight;
import com.mrcrayfish.furniture.blocks.BlockFireAlarm;
import com.mrcrayfish.furniture.blocks.BlockFirePitOff;
import com.mrcrayfish.furniture.blocks.BlockFirePitOn;
import com.mrcrayfish.furniture.blocks.BlockFreezer;
import com.mrcrayfish.furniture.blocks.BlockFridge;
import com.mrcrayfish.furniture.blocks.BlockGrandChair;
import com.mrcrayfish.furniture.blocks.BlockGrill;
import com.mrcrayfish.furniture.blocks.BlockHedge;
import com.mrcrayfish.furniture.blocks.BlockLamp;
import com.mrcrayfish.furniture.blocks.BlockLampOn;
import com.mrcrayfish.furniture.blocks.BlockMailBox;
import com.mrcrayfish.furniture.blocks.BlockMantelPiece;
import com.mrcrayfish.furniture.blocks.BlockMicrowave;
import com.mrcrayfish.furniture.blocks.BlockMirror;
import com.mrcrayfish.furniture.blocks.BlockOutdoorTable;
import com.mrcrayfish.furniture.blocks.BlockOven;
import com.mrcrayfish.furniture.blocks.BlockPlate;
import com.mrcrayfish.furniture.blocks.BlockPresent;
import com.mrcrayfish.furniture.blocks.BlockPrinter;
import com.mrcrayfish.furniture.blocks.BlockRangeHood;
import com.mrcrayfish.furniture.blocks.BlockShower;
import com.mrcrayfish.furniture.blocks.BlockShowerHeadOff;
import com.mrcrayfish.furniture.blocks.BlockShowerHeadOn;
import com.mrcrayfish.furniture.blocks.BlockStereo;
import com.mrcrayfish.furniture.blocks.BlockStonePath;
import com.mrcrayfish.furniture.blocks.BlockTV;
import com.mrcrayfish.furniture.blocks.BlockTable;
import com.mrcrayfish.furniture.blocks.BlockTap;
import com.mrcrayfish.furniture.blocks.BlockToaster;
import com.mrcrayfish.furniture.blocks.BlockToilet;
import com.mrcrayfish.furniture.blocks.BlockTrampoline;
import com.mrcrayfish.furniture.blocks.BlockTree;
import com.mrcrayfish.furniture.blocks.BlockWallCabinet;
import com.mrcrayfish.furniture.blocks.BlockWashingMachine;
import com.mrcrayfish.furniture.blocks.BlockWhiteFence;
import com.mrcrayfish.furniture.blocks.BlockWreath;
import com.mrcrayfish.furniture.items.ItemBath;
import com.mrcrayfish.furniture.items.ItemCrate;
import com.mrcrayfish.furniture.items.ItemHedge;
import com.mrcrayfish.furniture.items.ItemPresent;
import com.mrcrayfish.furniture.items.ItemWreath;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class FurnitureBlocks
{
	/** Initial Furniture */
	public static Block coffee_table_oak, coffee_table_stone;
	public static Block table_oak, table_stone;
	public static Block chair_oak, chair_stone;
	public static Block freezer, fridge;
	public static Block cabinet_oak, bedside_cabinet_oak;
	public static Block couch, couch_jeb;
	public static Block blinds, blinds_closed;
	public static Block curtains, curtains_closed;
	public static Block oven, range_hood;

	/** Garden Update */
	public static Block hedge_oak, hedge_spruce, hedge_birch, hedge_jungle, hedge_acacia, hedge_dark_oak; 
	public static Block bird_bath, stone_path, white_fence, tap, mail_box;

	/** Electronic Update */
	public static Block tv, computer, printer, electric_fence, door_bell, stereo;
	public static Block fire_alarm_off, fire_alarm_on;
	public static Block ceiling_light_off, ceiling_light_on;
	public static Block lamp_on, lamp_off;

	/** Bathroom Update */
	public static Block toilet, basin, bath_1, bath_2;
	public static Block shower_bottom, shower_top, shower_head_off, shower_head_on;
	public static Block wall_cabinet;
	public static Block bin;

	/** Kitchen Update */
	public static Block toaster, microwave, blender, washing_machine, dishwasher;
	public static Block counter, counter_sink, kitchen_cabinet;
	public static Block cup, plate, cookie_jar, bar_stool, chopping_board;

	/** Christmas Update */
	public static Block present, tree_bottom, tree_top, string;
	public static Block mantel_piece, grand_chair_top, grand_chair_bottom, candle, chimney, wreath, fairy_light, fire_pit_off, fire_pit_on;
	
	/** Outdoor Update */
	public static Block trampoline, crate, bench, table_outdoor, grill, sprinkler, divingboard_base, divingboard_plank, door_mat, esky, white_fence_gate;
	public static Block coffee_table_spruce, coffee_table_birch, coffee_table_jungle, coffee_table_acacia, coffee_table_dark_oak;
	public static Block chair_spruce, chair_birch, chair_jungle, chair_acacia, chair_dark_oak;
	public static Block table_spruce, table_birch, table_jungle, table_acacia, table_dark_oak;
	public static Block cabinet_spruce, cabinet_birch, cabinet_jungle, cabinet_acacia, cabinet_dark_oak;
	public static Block bedside_cabinet_spruce, bedside_cabinet_birch, bedside_cabinet_jungle, bedside_cabinet_acacia, bedside_cabinet_dark_oak;
	
	/* Special */
	public static Block mirror;

	/** Misc */
	public static Block hey;
	public static Block nyan;
	public static Block pattern;
	public static Block yellow_glow;
	public static Block white_glass;

	public static void init()
	{
		coffee_table_oak = new BlockCoffeeTable(Material.wood, Block.soundTypeWood).setUnlocalizedName("coffee_table_oak");
		coffee_table_spruce = new BlockCoffeeTable(Material.wood, Block.soundTypeWood).setUnlocalizedName("coffee_table_spruce");
		coffee_table_birch = new BlockCoffeeTable(Material.wood, Block.soundTypeWood).setUnlocalizedName("coffee_table_birch");
		coffee_table_jungle = new BlockCoffeeTable(Material.wood, Block.soundTypeWood).setUnlocalizedName("coffee_table_jungle");
		coffee_table_acacia = new BlockCoffeeTable(Material.wood, Block.soundTypeWood).setUnlocalizedName("coffee_table_acacia");
		coffee_table_dark_oak = new BlockCoffeeTable(Material.wood, Block.soundTypeWood).setUnlocalizedName("coffee_table_dark_oak");
		coffee_table_stone = new BlockCoffeeTable(Material.rock, Block.soundTypeStone).setUnlocalizedName("coffee_table_stone");
		table_oak = new BlockTable(Material.wood, Block.soundTypeWood).setUnlocalizedName("table_oak");
		table_spruce = new BlockTable(Material.wood, Block.soundTypeWood).setUnlocalizedName("table_spruce");
		table_birch = new BlockTable(Material.wood, Block.soundTypeWood).setUnlocalizedName("table_birch");
		table_jungle = new BlockTable(Material.wood, Block.soundTypeWood).setUnlocalizedName("table_jungle");
		table_acacia = new BlockTable(Material.wood, Block.soundTypeWood).setUnlocalizedName("table_acacia");
		table_dark_oak = new BlockTable(Material.wood, Block.soundTypeWood).setUnlocalizedName("table_dark_oak");
		table_stone = new BlockTable(Material.rock, Block.soundTypeStone).setUnlocalizedName("table_stone");
		chair_oak = new BlockChair(Material.wood, Block.soundTypeWood).setUnlocalizedName("chair_oak");
		chair_spruce = new BlockChair(Material.wood, Block.soundTypeWood).setUnlocalizedName("chair_spruce");
		chair_birch = new BlockChair(Material.wood, Block.soundTypeWood).setUnlocalizedName("chair_birch");
		chair_jungle = new BlockChair(Material.wood, Block.soundTypeWood).setUnlocalizedName("chair_jungle");
		chair_acacia = new BlockChair(Material.wood, Block.soundTypeWood).setUnlocalizedName("chair_acacia");
		chair_dark_oak = new BlockChair(Material.wood, Block.soundTypeWood).setUnlocalizedName("chair_dark_oak");
		chair_stone = new BlockChair(Material.rock, Block.soundTypeStone).setUnlocalizedName("chair_stone");
		freezer = new BlockFreezer(Material.rock).setUnlocalizedName("freezer");
		fridge = new BlockFridge(Material.rock).setUnlocalizedName("fridge");
		cabinet_oak = new BlockCabinet(Material.wood).setUnlocalizedName("cabinet_oak");
		cabinet_spruce = new BlockCabinet(Material.wood).setUnlocalizedName("cabinet_spruce");
		cabinet_birch = new BlockCabinet(Material.wood).setUnlocalizedName("cabinet_birch");
		cabinet_jungle = new BlockCabinet(Material.wood).setUnlocalizedName("cabinet_jungle");
		cabinet_acacia = new BlockCabinet(Material.wood).setUnlocalizedName("cabinet_acacia");
		cabinet_dark_oak = new BlockCabinet(Material.wood).setUnlocalizedName("cabinet_dark_oak");
		couch = new BlockCouchNormal().setUnlocalizedName("couch");
		couch_jeb = new BlockCouchJeb().setUnlocalizedName("couch_jeb");
		lamp_on = new BlockLampOn(Material.glass).setUnlocalizedName("lamp_on");
		lamp_off = new BlockLamp(Material.glass, false).setUnlocalizedName("lamp_off");
		blinds = new BlockBlinds(Material.wood, true).setUnlocalizedName("blinds_open");
		blinds_closed = new BlockBlinds(Material.wood, false).setUnlocalizedName("blinds_closed");
		curtains = new BlockCurtainsOpen(Material.cloth).setUnlocalizedName("curtains_open");
		curtains_closed = new BlockCurtainsClosed(Material.cloth).setUnlocalizedName("curtains_closed");
		bedside_cabinet_oak = new BlockBedsideCabinet(Material.wood).setUnlocalizedName("bedside_cabinet_oak");
		bedside_cabinet_spruce = new BlockBedsideCabinet(Material.wood).setUnlocalizedName("bedside_cabinet_spruce");
		bedside_cabinet_birch = new BlockBedsideCabinet(Material.wood).setUnlocalizedName("bedside_cabinet_birch");
		bedside_cabinet_jungle = new BlockBedsideCabinet(Material.wood).setUnlocalizedName("bedside_cabinet_jungle");
		bedside_cabinet_acacia = new BlockBedsideCabinet(Material.wood).setUnlocalizedName("bedside_cabinet_acacia");
		bedside_cabinet_dark_oak = new BlockBedsideCabinet(Material.wood).setUnlocalizedName("bedside_cabinet_dark_oak");
		oven = new BlockOven(Material.rock).setUnlocalizedName("oven");
		range_hood = new BlockRangeHood(Material.rock).setUnlocalizedName("range_hood");
		hedge_oak = new BlockHedge(Material.leaves).setUnlocalizedName("hedge_oak");
		hedge_spruce = new BlockHedge(Material.leaves).setUnlocalizedName("hedge_spruce");
		hedge_birch = new BlockHedge(Material.leaves).setUnlocalizedName("hedge_birch");
		hedge_jungle = new BlockHedge(Material.leaves).setUnlocalizedName("hedge_jungle");
		hedge_acacia = new BlockHedge(Material.leaves).setUnlocalizedName("hedge_acacia");
		hedge_dark_oak = new BlockHedge(Material.leaves).setUnlocalizedName("hedge_dark_oak");
		bird_bath = new BlockBirdBath(Material.rock).setUnlocalizedName("bird_bath");
		stone_path = new BlockStonePath(Material.rock).setUnlocalizedName("stone_path");
		white_fence = new BlockWhiteFence(Material.wood).setUnlocalizedName("white_fence");
		tap = new BlockTap(Material.rock).setUnlocalizedName("tap");
		mail_box = new BlockMailBox(Material.wood).setUnlocalizedName("mail_box");
		tv = new BlockTV(Material.wood).setUnlocalizedName("tv");
		computer = new BlockComputer(Material.iron).setUnlocalizedName("computer");
		printer = new BlockPrinter(Material.iron).setUnlocalizedName("printer");
		electric_fence = new BlockElectricFence(Material.rock).setUnlocalizedName("electric_fence");
		door_bell = new BlockDoorBell(Material.wood).setUnlocalizedName("door_bell");
		fire_alarm_on = new BlockFireAlarm(Material.rock, true).setUnlocalizedName("fire_alarm_on");
		fire_alarm_off = new BlockFireAlarm(Material.rock, false).setUnlocalizedName("fire_alarm_off");
		ceiling_light_on = new BlockCeilingLight(Material.glass, true).setUnlocalizedName("ceiling_light_on");
		ceiling_light_off = new BlockCeilingLight(Material.glass, false).setUnlocalizedName("ceiling_light_off");
		stereo = new BlockStereo(Material.wood).setUnlocalizedName("stereo");
		toilet = new BlockToilet(Material.rock).setUnlocalizedName("toilet");
		basin = new BlockBasin(Material.rock).setUnlocalizedName("basin");
		wall_cabinet = new BlockWallCabinet(Material.rock).setUnlocalizedName("wall_cabinet");
		bath_1 = new BlockBath(Material.rock, false).setUnlocalizedName("bath_bottom");
		bath_2 = new BlockBath(Material.rock, true).setUnlocalizedName("bath_top");
		shower_bottom = new BlockShower(Material.rock, false).setUnlocalizedName("shower_bottom");
		shower_top = new BlockShower(Material.rock, true).setUnlocalizedName("shower_top");
		shower_head_off = new BlockShowerHeadOff(Material.rock).setUnlocalizedName("shower_head_off");
		shower_head_on = new BlockShowerHeadOn(Material.rock).setUnlocalizedName("shower_head_on");
		bin = new BlockBin(Material.rock).setUnlocalizedName("bin");
		tree_top = new BlockTree(Material.wood, true).setUnlocalizedName("tree_top");
		tree_bottom = new BlockTree(Material.wood, false).setUnlocalizedName("tree_bottom");
		present = new BlockPresent(Material.cloth).setUnlocalizedName("present");
		toaster = new BlockToaster(Material.wood).setUnlocalizedName("toaster");
		microwave = new BlockMicrowave(Material.wood).setUnlocalizedName("microwave");
		washing_machine = new BlockWashingMachine(Material.rock).setUnlocalizedName("washing_machine");
		cookie_jar = new BlockCookieJar(Material.glass).setUnlocalizedName("cookie_jar");
		blender = new BlockBlender(Material.glass).setUnlocalizedName("blender");
		cup = new BlockCup(Material.glass).setUnlocalizedName("cup");
		plate = new BlockPlate(Material.glass).setUnlocalizedName("plate");
		counter = new BlockCounter(Material.wood).setUnlocalizedName("counter");
		counter_sink = new BlockCounterSink(Material.wood).setUnlocalizedName("counter_sink");
		dishwasher = new BlockDishwasher(Material.rock).setUnlocalizedName("dishwasher");
		kitchen_cabinet = new BlockCabinetKitchen(Material.wood).setUnlocalizedName("cabinet_kitchen");
		chopping_board = new BlockChoppingBoard(Material.wood).setUnlocalizedName("chopping_board");
		bar_stool = new BlockBarStool(Material.wood).setUnlocalizedName("bar_stool");
		mirror = new BlockMirror(Material.glass).setUnlocalizedName("mirror");
		mantel_piece = new BlockMantelPiece(Material.rock).setUnlocalizedName("mantel_piece");
		grand_chair_top = new BlockGrandChair(Material.wood, true).setUnlocalizedName("grand_chair_top");
		grand_chair_bottom = new BlockGrandChair(Material.wood, false).setUnlocalizedName("grand_chair_bottom");
		candle = new BlockCandle(Material.rock).setUnlocalizedName("candle");
		chimney = new BlockChimney(Material.rock).setUnlocalizedName("chimney");
		wreath = new BlockWreath(Material.leaves).setUnlocalizedName("wreath");
		fairy_light = new BlockFairyLight(Material.glass).setUnlocalizedName("fairy_light");
		fire_pit_off = new BlockFirePitOff(Material.wood).setUnlocalizedName("fire_pit_off");
		fire_pit_on = new BlockFirePitOn(Material.wood).setUnlocalizedName("fire_pit_on");
		trampoline = new BlockTrampoline(Material.rock).setUnlocalizedName("trampoline");
		crate = new BlockCrate(Material.wood).setUnlocalizedName("crate");
		bench = new BlockBench(Material.wood).setUnlocalizedName("bench");
		table_outdoor = new BlockOutdoorTable(Material.wood, Block.soundTypeWood).setUnlocalizedName("table_outdoor");
		grill = new BlockGrill(Material.anvil).setUnlocalizedName("grill");
		divingboard_base = new BlockDivingboard(Material.rock, false).setUnlocalizedName("divingboard_base");
		divingboard_plank = new BlockDivingboard(Material.rock, true).setUnlocalizedName("divingboard_plank"); 
		door_mat = new BlockDoorMat(Material.cloth).setUnlocalizedName("door_mat");
		esky = new BlockEsky(Material.clay).setUnlocalizedName("esky");
	}

	public static void register()
	{
		/** Block Registering */
		GameRegistry.registerBlock(coffee_table_oak, coffee_table_oak.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(coffee_table_spruce, coffee_table_spruce.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(coffee_table_birch, coffee_table_birch.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(coffee_table_jungle, coffee_table_jungle.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(coffee_table_acacia, coffee_table_acacia.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(coffee_table_dark_oak, coffee_table_dark_oak.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(coffee_table_stone, coffee_table_stone.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(table_oak, table_oak.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(table_spruce, table_spruce.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(table_birch, table_birch.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(table_jungle, table_jungle.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(table_acacia, table_acacia.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(table_dark_oak, table_dark_oak.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(table_stone, table_stone.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(chair_oak, chair_oak.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(chair_spruce, chair_spruce.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(chair_birch, chair_birch.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(chair_jungle, chair_jungle.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(chair_acacia, chair_acacia.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(chair_dark_oak, chair_dark_oak.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(chair_stone, chair_stone.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(freezer, freezer.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(fridge, fridge.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(cabinet_oak, cabinet_oak.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(cabinet_spruce, cabinet_spruce.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(cabinet_birch, cabinet_birch.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(cabinet_jungle, cabinet_jungle.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(cabinet_acacia, cabinet_acacia.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(cabinet_dark_oak, cabinet_dark_oak.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(couch, couch.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(couch_jeb, couch_jeb.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(lamp_on, lamp_on.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(lamp_off, lamp_off.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(blinds, blinds.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(blinds_closed, blinds_closed.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(curtains, curtains.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(curtains_closed, curtains_closed.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(bedside_cabinet_oak, bedside_cabinet_oak.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(bedside_cabinet_spruce, bedside_cabinet_spruce.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(bedside_cabinet_birch, bedside_cabinet_birch.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(bedside_cabinet_jungle, bedside_cabinet_jungle.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(bedside_cabinet_acacia, bedside_cabinet_acacia.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(bedside_cabinet_dark_oak, bedside_cabinet_dark_oak.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(oven, oven.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(range_hood, range_hood.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(hedge_oak, ItemHedge.class, hedge_oak.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(hedge_spruce, ItemHedge.class, hedge_spruce.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(hedge_birch, ItemHedge.class, hedge_birch.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(hedge_jungle, ItemHedge.class, hedge_jungle.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(hedge_acacia, ItemHedge.class, hedge_acacia.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(hedge_dark_oak, ItemHedge.class, hedge_dark_oak.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(bird_bath, bird_bath.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(stone_path, stone_path.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(white_fence, white_fence.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(tap, tap.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(mail_box, mail_box.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(tv, tv.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(computer, computer.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(printer, printer.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(electric_fence, electric_fence.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(door_bell, door_bell.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(fire_alarm_off, fire_alarm_off.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(fire_alarm_on, fire_alarm_on.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(ceiling_light_off, ceiling_light_off.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(ceiling_light_on, ceiling_light_on.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(stereo, stereo.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(toilet, toilet.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(basin, basin.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(wall_cabinet, wall_cabinet.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(bath_1, ItemBath.class, bath_1.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(bath_2, bath_2.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(shower_bottom, shower_bottom.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(shower_top, shower_top.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(shower_head_off, shower_head_off.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(shower_head_on, shower_head_on.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(bin, bin.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(present, ItemPresent.class, present.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(tree_top, ItemWreath.class, tree_top.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(tree_bottom, ItemWreath.class, tree_bottom.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(toaster, toaster.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(microwave, microwave.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(washing_machine, washing_machine.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(cookie_jar, cookie_jar.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(blender, blender.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(cup, cup.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(plate, plate.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(counter, counter.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(counter_sink, counter_sink.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(dishwasher, dishwasher.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(kitchen_cabinet, kitchen_cabinet.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(chopping_board, chopping_board.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(bar_stool, bar_stool.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(mirror, mirror.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(mantel_piece, mantel_piece.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(grand_chair_top, grand_chair_top.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(grand_chair_bottom, grand_chair_bottom.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(candle, candle.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(chimney, chimney.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(wreath, ItemWreath.class, wreath.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(fairy_light, fairy_light.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(fire_pit_off, fire_pit_off.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(fire_pit_on, fire_pit_on.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(trampoline, trampoline.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(crate, ItemCrate.class, crate.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(bench, bench.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(table_outdoor, table_outdoor.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(grill, grill.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(divingboard_base, ItemBath.class, divingboard_base.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(divingboard_plank, divingboard_plank.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(door_mat, door_mat.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(esky, esky.getUnlocalizedName().substring(5));
	}
	
	public static void registerRenders()
	{
		registerRender(table_oak);
		registerRender(table_spruce);
		registerRender(table_birch);
		registerRender(table_jungle);
		registerRender(table_acacia);
		registerRender(table_dark_oak);
		registerRender(table_stone);
		registerRender(coffee_table_oak);
		registerRender(coffee_table_spruce);
		registerRender(coffee_table_birch);
		registerRender(coffee_table_jungle);
		registerRender(coffee_table_acacia);
		registerRender(coffee_table_dark_oak);
		registerRender(coffee_table_stone);
		registerRender(chair_oak);
		registerRender(chair_spruce);
		registerRender(chair_birch);
		registerRender(chair_jungle);
		registerRender(chair_acacia);
		registerRender(chair_dark_oak);
		registerRender(chair_stone);
		registerRender(cabinet_oak);
		registerRender(cabinet_spruce);
		registerRender(cabinet_birch);
		registerRender(cabinet_jungle);
		registerRender(cabinet_acacia);
		registerRender(cabinet_dark_oak);
		registerRender(bedside_cabinet_oak);
		registerRender(bedside_cabinet_spruce);
		registerRender(bedside_cabinet_birch);
		registerRender(bedside_cabinet_jungle);
		registerRender(bedside_cabinet_acacia);
		registerRender(bedside_cabinet_dark_oak);
		registerRender(fridge);
		registerRender(freezer);
		registerRender(couch);
		registerRender(couch_jeb);
		registerRender(oven);
		registerRender(range_hood);
		
		registerRender(hedge_oak);
		registerRender(hedge_spruce);
		registerRender(hedge_birch);
		registerRender(hedge_jungle);
		registerRender(hedge_acacia);
		registerRender(hedge_dark_oak );
		registerRender(bird_bath);
		registerRender(stone_path);
		registerRender(white_fence);
		registerRender(tap);
		registerRender(mail_box);
		
		registerRender(tv);
		registerRender(computer);
		registerRender(printer);
		registerRender(electric_fence);
		registerRender(door_bell);
		registerRender(stereo);
		registerRender(fire_alarm_off);
		registerRender(ceiling_light_off);
		registerRender(lamp_off);
		
		registerRender(toilet);
		registerRender(basin);
		registerRender(bath_1);
		registerRender(shower_bottom);
		registerRender(shower_head_off );
		registerRender(wall_cabinet);
		registerRender(bin);
		registerRender(mirror);
		
		registerRender(toaster);
		registerRender(microwave);
		registerRender(blender);
		registerRender(washing_machine);
		registerRender(dishwasher);
		registerRender(counter);
		registerRender(counter_sink);
		registerRender(kitchen_cabinet);
		registerRender(cup );
		registerRender(plate);
		registerRender(cookie_jar);
		registerRender(bar_stool );
		registerRender(chopping_board);
		
		registerRender(tree_bottom);
		registerRender(mantel_piece);
		registerRender(grand_chair_top);
		registerRender(grand_chair_bottom);
		registerRender(candle);
		registerRender(chimney);
		registerRender(wreath);
		registerRender(fairy_light);
		registerRender(fire_alarm_on);
		registerRender(fire_pit_on);
		
		registerRender(blinds);
		registerRender(curtains);
		registerRender(trampoline);
		registerRender(crate);
		registerRender(bench);
		registerRender(table_outdoor);
		registerRender(grill);
		registerRender(divingboard_base);
		registerRender(divingboard_plank);
		registerRender(door_mat);
		registerRender(esky);
		
		registerPresents();
	}
	
	private static void registerRender(Block block)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(Reference.MOD_ID + ":" + block.getUnlocalizedName().substring(5), "inventory"));
	}

	private static void registerPresents()
	{
		Item blockItem = Item.getItemFromBlock(present);
        List<ItemStack> subItems = new ArrayList<ItemStack>();
		present.getSubBlocks(blockItem, null, subItems);
		for(int i = 0; i < 16; i++)
		{
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(blockItem, subItems.get(i).getMetadata(), new ModelResourceLocation(Reference.MOD_ID + ":" + "present_" + EnumDyeColor.values()[i].getName(), "inventory"));
		}
	}
}
