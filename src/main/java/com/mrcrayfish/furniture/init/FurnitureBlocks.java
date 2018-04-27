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

import java.rmi.registry.RegistryHandler;
import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.ImmutableList;
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
import com.mrcrayfish.furniture.blocks.item.ItemBlockMeta;
import com.mrcrayfish.furniture.items.ItemBath;
import com.mrcrayfish.furniture.items.ItemCrate;
import com.mrcrayfish.furniture.items.ItemHedge;
import com.mrcrayfish.furniture.items.ItemPresent;
import com.mrcrayfish.furniture.items.ItemWreath;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

	/** Special */
	public static Block mirror;

	/** Misc */
	public static Block hey;
	public static Block nyan;
	public static Block pattern;
	public static Block yellow_glow;
	public static Block white_glass;

	public static void init()
	{
		coffee_table_oak = new BlockCoffeeTable(Material.WOOD, SoundType.WOOD, "coffee_table_oak");
		coffee_table_spruce = new BlockCoffeeTable(Material.WOOD, SoundType.WOOD, "coffee_table_spruce");
		coffee_table_birch = new BlockCoffeeTable(Material.WOOD, SoundType.WOOD, "coffee_table_birch");
		coffee_table_jungle = new BlockCoffeeTable(Material.WOOD, SoundType.WOOD, "coffee_table_jungle");
		coffee_table_acacia = new BlockCoffeeTable(Material.WOOD, SoundType.WOOD, "coffee_table_acacia");
		coffee_table_dark_oak = new BlockCoffeeTable(Material.WOOD, SoundType.WOOD, "coffee_table_dark_oak");
		coffee_table_stone = new BlockCoffeeTable(Material.ROCK, SoundType.STONE, "coffee_table_stone");
		table_oak = new BlockTable(Material.WOOD, SoundType.WOOD).setUnlocalizedName("table_oak").setRegistryName("table_oak");
		table_spruce = new BlockTable(Material.WOOD, SoundType.WOOD).setUnlocalizedName("table_spruce").setRegistryName("table_spruce");
		table_birch = new BlockTable(Material.WOOD, SoundType.WOOD).setUnlocalizedName("table_birch").setRegistryName("table_birch");
		table_jungle = new BlockTable(Material.WOOD, SoundType.WOOD).setUnlocalizedName("table_jungle").setRegistryName("table_jungle");
		table_acacia = new BlockTable(Material.WOOD, SoundType.WOOD).setUnlocalizedName("table_acacia").setRegistryName("table_acacia");
		table_dark_oak = new BlockTable(Material.WOOD, SoundType.WOOD).setUnlocalizedName("table_dark_oak").setRegistryName("table_dark_oak");
		table_stone = new BlockTable(Material.ROCK, SoundType.STONE).setUnlocalizedName("table_stone").setRegistryName("table_stone");
		chair_oak = new BlockChair(Material.WOOD, SoundType.WOOD).setUnlocalizedName("chair_oak").setRegistryName("chair_oak");
		chair_spruce = new BlockChair(Material.WOOD, SoundType.WOOD).setUnlocalizedName("chair_spruce").setRegistryName("chair_spruce");
		chair_birch = new BlockChair(Material.WOOD, SoundType.WOOD).setUnlocalizedName("chair_birch").setRegistryName("chair_birch");
		chair_jungle = new BlockChair(Material.WOOD, SoundType.WOOD).setUnlocalizedName("chair_jungle").setRegistryName("chair_jungle");
		chair_acacia = new BlockChair(Material.WOOD, SoundType.WOOD).setUnlocalizedName("chair_acacia").setRegistryName("chair_acacia");
		chair_dark_oak = new BlockChair(Material.WOOD, SoundType.WOOD).setUnlocalizedName("chair_dark_oak").setRegistryName("chair_dark_oak");
		chair_stone = new BlockChair(Material.ROCK, SoundType.STONE).setUnlocalizedName("chair_stone").setRegistryName("chair_stone");
		freezer = new BlockFreezer(Material.ROCK).setUnlocalizedName("freezer").setRegistryName("freezer");
		fridge = new BlockFridge(Material.ROCK).setUnlocalizedName("fridge").setRegistryName("fridge");
		cabinet_oak = new BlockCabinet(Material.WOOD).setUnlocalizedName("cabinet_oak").setRegistryName("cabinet_oak");
		cabinet_spruce = new BlockCabinet(Material.WOOD).setUnlocalizedName("cabinet_spruce").setRegistryName("cabinet_spruce");
		cabinet_birch = new BlockCabinet(Material.WOOD).setUnlocalizedName("cabinet_birch").setRegistryName("cabinet_birch");
		cabinet_jungle = new BlockCabinet(Material.WOOD).setUnlocalizedName("cabinet_jungle").setRegistryName("cabinet_jungle");
		cabinet_acacia = new BlockCabinet(Material.WOOD).setUnlocalizedName("cabinet_acacia").setRegistryName("cabinet_acacia");
		cabinet_dark_oak = new BlockCabinet(Material.WOOD).setUnlocalizedName("cabinet_dark_oak").setRegistryName("cabinet_dark_oak");
		couch = new BlockCouchNormal().setUnlocalizedName("couch").setRegistryName("couch");
		couch_jeb = new BlockCouchJeb().setUnlocalizedName("couch_jeb").setRegistryName("couch_jeb");
		lamp_on = new BlockLampOn(Material.GLASS).setUnlocalizedName("lamp_on").setRegistryName("lamp_on");
		lamp_off = new BlockLamp(Material.GLASS, false).setUnlocalizedName("lamp_off").setRegistryName("lamp_off");
		blinds = new BlockBlinds(Material.WOOD, true).setUnlocalizedName("blinds_open").setRegistryName("blinds_open");
		blinds_closed = new BlockBlinds(Material.WOOD, false).setUnlocalizedName("blinds_closed").setRegistryName("blinds_closed");
		curtains = new BlockCurtainsOpen(Material.CLOTH).setUnlocalizedName("curtains_open").setRegistryName("curtains_open");
		curtains_closed = new BlockCurtainsClosed(Material.CLOTH).setUnlocalizedName("curtains_closed").setRegistryName("curtains_closed");
		bedside_cabinet_oak = new BlockBedsideCabinet(Material.WOOD).setUnlocalizedName("bedside_cabinet_oak").setRegistryName("bedside_cabinet_oak");
		bedside_cabinet_spruce = new BlockBedsideCabinet(Material.WOOD).setUnlocalizedName("bedside_cabinet_spruce").setRegistryName("bedside_cabinet_spruce");
		bedside_cabinet_birch = new BlockBedsideCabinet(Material.WOOD).setUnlocalizedName("bedside_cabinet_birch").setRegistryName("bedside_cabinet_birch");
		bedside_cabinet_jungle = new BlockBedsideCabinet(Material.WOOD).setUnlocalizedName("bedside_cabinet_jungle").setRegistryName("bedside_cabinet_jungle");
		bedside_cabinet_acacia = new BlockBedsideCabinet(Material.WOOD).setUnlocalizedName("bedside_cabinet_acacia").setRegistryName("bedside_cabinet_acacia");
		bedside_cabinet_dark_oak = new BlockBedsideCabinet(Material.WOOD).setUnlocalizedName("bedside_cabinet_dark_oak").setRegistryName("bedside_cabinet_dark_oak");
		oven = new BlockOven(Material.ROCK).setUnlocalizedName("oven").setRegistryName("oven");
		range_hood = new BlockRangeHood(Material.ROCK).setUnlocalizedName("range_hood").setRegistryName("range_hood");
		hedge_oak = new BlockHedge(Material.LEAVES).setUnlocalizedName("hedge_oak").setRegistryName("hedge_oak");
		hedge_spruce = new BlockHedge(Material.LEAVES).setUnlocalizedName("hedge_spruce").setRegistryName("hedge_spruce");
		hedge_birch = new BlockHedge(Material.LEAVES).setUnlocalizedName("hedge_birch").setRegistryName("hedge_birch");
		hedge_jungle = new BlockHedge(Material.LEAVES).setUnlocalizedName("hedge_jungle").setRegistryName("hedge_jungle");
		hedge_acacia = new BlockHedge(Material.LEAVES).setUnlocalizedName("hedge_acacia").setRegistryName("hedge_acacia");
		hedge_dark_oak = new BlockHedge(Material.LEAVES).setUnlocalizedName("hedge_dark_oak").setRegistryName("hedge_dark_oak");
		bird_bath = new BlockBirdBath(Material.ROCK).setUnlocalizedName("bird_bath").setRegistryName("bird_bath");
		stone_path = new BlockStonePath(Material.ROCK).setUnlocalizedName("stone_path").setRegistryName("stone_path");
		white_fence = new BlockWhiteFence(Material.WOOD).setUnlocalizedName("white_fence").setRegistryName("white_fence");
		tap = new BlockTap(Material.ROCK).setUnlocalizedName("tap").setRegistryName("tap");
		mail_box = new BlockMailBox(Material.WOOD).setUnlocalizedName("mail_box").setRegistryName("mail_box");
		tv = new BlockTV(Material.WOOD).setUnlocalizedName("tv").setRegistryName("tv");
		computer = new BlockComputer(Material.IRON).setUnlocalizedName("computer").setRegistryName("computer");
		printer = new BlockPrinter(Material.IRON).setUnlocalizedName("printer").setRegistryName("printer");
		electric_fence = new BlockElectricFence(Material.ROCK).setUnlocalizedName("electric_fence").setRegistryName("electric_fence");
		door_bell = new BlockDoorBell(Material.WOOD).setUnlocalizedName("door_bell").setRegistryName("door_bell");
		fire_alarm_on = new BlockFireAlarm(Material.ROCK, true).setUnlocalizedName("fire_alarm_on").setRegistryName("fire_alarm_on");
		fire_alarm_off = new BlockFireAlarm(Material.ROCK, false).setUnlocalizedName("fire_alarm_off").setRegistryName("fire_alarm_off");
		ceiling_light_on = new BlockCeilingLight(Material.GLASS, true).setUnlocalizedName("ceiling_light_on").setRegistryName("ceiling_light_on");
		ceiling_light_off = new BlockCeilingLight(Material.GLASS, false).setUnlocalizedName("ceiling_light_off").setRegistryName("ceiling_light_off");
		stereo = new BlockStereo(Material.WOOD).setUnlocalizedName("stereo").setRegistryName("stereo");
		toilet = new BlockToilet(Material.ROCK).setUnlocalizedName("toilet").setRegistryName("toilet");
		basin = new BlockBasin(Material.ROCK).setUnlocalizedName("basin").setRegistryName("basin");
		wall_cabinet = new BlockWallCabinet(Material.ROCK).setUnlocalizedName("wall_cabinet").setRegistryName("wall_cabinet");
		bath_1 = new BlockBath(Material.ROCK, false).setUnlocalizedName("bath_bottom").setRegistryName("bath_bottom");
		bath_2 = new BlockBath(Material.ROCK, true).setUnlocalizedName("bath_top").setRegistryName("bath_top");
		shower_bottom = new BlockShower(Material.ROCK, false).setUnlocalizedName("shower_bottom").setRegistryName("shower_bottom");
		shower_top = new BlockShower(Material.ROCK, true).setUnlocalizedName("shower_top").setRegistryName("shower_top");
		shower_head_off = new BlockShowerHeadOff(Material.ROCK).setUnlocalizedName("shower_head_off").setRegistryName("shower_head_off");
		shower_head_on = new BlockShowerHeadOn(Material.ROCK).setUnlocalizedName("shower_head_on").setRegistryName("shower_head_on");
		bin = new BlockBin(Material.ROCK).setUnlocalizedName("bin").setRegistryName("bin");
		tree_top = new BlockTree(Material.WOOD, true).setUnlocalizedName("tree_top").setRegistryName("tree_top");
		tree_bottom = new BlockTree(Material.WOOD, false).setUnlocalizedName("tree_bottom").setRegistryName("tree_bottom");
		present = new BlockPresent(Material.CLOTH).setUnlocalizedName("present").setRegistryName("present");
		toaster = new BlockToaster(Material.WOOD).setUnlocalizedName("toaster").setRegistryName("toaster");
		microwave = new BlockMicrowave(Material.WOOD).setUnlocalizedName("microwave").setRegistryName("microwave");
		washing_machine = new BlockWashingMachine(Material.ROCK).setUnlocalizedName("washing_machine").setRegistryName("washing_machine");
		cookie_jar = new BlockCookieJar(Material.GLASS).setUnlocalizedName("cookie_jar").setRegistryName("cookie_jar");
		blender = new BlockBlender(Material.GLASS).setUnlocalizedName("blender").setRegistryName("blender");
		cup = new BlockCup(Material.GLASS).setUnlocalizedName("cup").setRegistryName("cup");
		plate = new BlockPlate(Material.GLASS).setUnlocalizedName("plate").setRegistryName("plate");
		counter = new BlockCounter(Material.WOOD).setUnlocalizedName("counter").setRegistryName("counter");
		counter_sink = new BlockCounterSink(Material.WOOD).setUnlocalizedName("counter_sink").setRegistryName("counter_sink");
		dishwasher = new BlockDishwasher(Material.ROCK).setUnlocalizedName("dishwasher").setRegistryName("dishwasher");
		kitchen_cabinet = new BlockCabinetKitchen(Material.WOOD).setUnlocalizedName("cabinet_kitchen").setRegistryName("cabinet_kitchen");
		chopping_board = new BlockChoppingBoard(Material.WOOD).setUnlocalizedName("chopping_board").setRegistryName("chopping_board");
		bar_stool = new BlockBarStool(Material.WOOD).setUnlocalizedName("bar_stool").setRegistryName("bar_stool");
		mirror = new BlockMirror(Material.GLASS).setUnlocalizedName("mirror").setRegistryName("mirror");
		mantel_piece = new BlockMantelPiece(Material.ROCK).setUnlocalizedName("mantel_piece").setRegistryName("mantel_piece");
		grand_chair_top = new BlockGrandChair(Material.WOOD, true).setUnlocalizedName("grand_chair_top").setRegistryName("grand_chair_top");
		grand_chair_bottom = new BlockGrandChair(Material.WOOD, false).setUnlocalizedName("grand_chair_bottom").setRegistryName("grand_chair_bottom");
		candle = new BlockCandle(Material.ROCK).setUnlocalizedName("candle").setRegistryName("candle");
		chimney = new BlockChimney(Material.ROCK).setUnlocalizedName("chimney").setRegistryName("chimney");
		wreath = new BlockWreath(Material.LEAVES).setUnlocalizedName("wreath").setRegistryName("wreath");
		fairy_light = new BlockFairyLight(Material.GLASS).setUnlocalizedName("fairy_light").setRegistryName("fairy_light");
		fire_pit_off = new BlockFirePitOff(Material.WOOD).setUnlocalizedName("fire_pit_off").setRegistryName("fire_pit_off");
		fire_pit_on = new BlockFirePitOn(Material.WOOD).setUnlocalizedName("fire_pit_on").setRegistryName("fire_pit_on");
		trampoline = new BlockTrampoline(Material.ROCK).setUnlocalizedName("trampoline").setRegistryName("trampoline");
		crate = new BlockCrate(Material.WOOD).setUnlocalizedName("crate").setRegistryName("crate");
		bench = new BlockBench(Material.WOOD).setUnlocalizedName("bench").setRegistryName("bench");
		table_outdoor = new BlockOutdoorTable(Material.WOOD, SoundType.WOOD).setUnlocalizedName("table_outdoor").setRegistryName("table_outdoor");
		grill = new BlockGrill(Material.ANVIL).setUnlocalizedName("grill").setRegistryName("grill");
		divingboard_base = new BlockDivingboard(Material.ROCK, false).setUnlocalizedName("divingboard_base").setRegistryName("divingboard_base");
		divingboard_plank = new BlockDivingboard(Material.ROCK, true).setUnlocalizedName("divingboard_plank").setRegistryName("divingboard_plank");
		door_mat = new BlockDoorMat(Material.CLOTH).setUnlocalizedName("door_mat").setRegistryName("door_mat");
		esky = new BlockEsky(Material.CLAY).setUnlocalizedName("esky").setRegistryName("esky");
	}

	public static void register()
	{
		/** Block Registering */
		registerBlock(coffee_table_oak);
		registerBlock(coffee_table_spruce);
		registerBlock(coffee_table_birch);
		registerBlock(coffee_table_jungle);
		registerBlock(coffee_table_acacia);
		registerBlock(coffee_table_dark_oak);
		registerBlock(coffee_table_stone);
		registerBlock(table_oak);
		registerBlock(table_spruce);
		registerBlock(table_birch);
		registerBlock(table_jungle);
		registerBlock(table_acacia);
		registerBlock(table_dark_oak);
		registerBlock(table_stone);
		registerBlock(chair_oak);
		registerBlock(chair_spruce);
		registerBlock(chair_birch);
		registerBlock(chair_jungle);
		registerBlock(chair_acacia);
		registerBlock(chair_dark_oak);
		registerBlock(chair_stone);
		registerBlock(freezer);
		registerBlock(fridge, null);
		registerBlock(cabinet_oak);
		registerBlock(cabinet_spruce);
		registerBlock(cabinet_birch);
		registerBlock(cabinet_jungle);
		registerBlock(cabinet_acacia);
		registerBlock(cabinet_dark_oak);
		registerBlock(couch, new ItemBlockMeta(couch));
		registerBlock(couch_jeb);
		registerBlock(lamp_on, null);
		registerBlock(lamp_off);
		registerBlock(blinds);
		registerBlock(blinds_closed, null);
		registerBlock(curtains);
		registerBlock(curtains_closed, null);
		registerBlock(bedside_cabinet_oak);
		registerBlock(bedside_cabinet_spruce);
		registerBlock(bedside_cabinet_birch);
		registerBlock(bedside_cabinet_jungle);
		registerBlock(bedside_cabinet_acacia);
		registerBlock(bedside_cabinet_dark_oak);
		registerBlock(oven);
		registerBlock(range_hood);
		registerBlock(hedge_oak, new ItemHedge(hedge_oak));
		registerBlock(hedge_spruce, new ItemHedge(hedge_spruce));
		registerBlock(hedge_birch, new ItemHedge(hedge_birch));
		registerBlock(hedge_jungle, new ItemHedge(hedge_jungle));
		registerBlock(hedge_acacia, new ItemHedge(hedge_acacia));
		registerBlock(hedge_dark_oak, new ItemHedge(hedge_dark_oak));
		registerBlock(bird_bath);
		registerBlock(stone_path);
		registerBlock(white_fence);
		registerBlock(tap);
		registerBlock(mail_box);
		registerBlock(tv);
		registerBlock(computer);
		registerBlock(printer);
		registerBlock(electric_fence);
		registerBlock(door_bell);
		registerBlock(fire_alarm_off);
		registerBlock(fire_alarm_on);
		registerBlock(ceiling_light_off);
		registerBlock(ceiling_light_on, null);
		registerBlock(stereo);
		registerBlock(toilet);
		registerBlock(basin);
		registerBlock(wall_cabinet);
		registerBlock(bath_1, new ItemBath(bath_1));
		registerBlock(bath_2, null);
		registerBlock(shower_bottom);
		registerBlock(shower_top, null);
		registerBlock(shower_head_off);
		registerBlock(shower_head_on, null);
		registerBlock(bin);
		registerBlock(present, new ItemPresent(present));
		registerBlock(tree_top, null);
		registerBlock(tree_bottom, new ItemWreath(tree_bottom));
		registerBlock(toaster);
		registerBlock(microwave);
		registerBlock(washing_machine);
		registerBlock(cookie_jar);
		registerBlock(blender);
		registerBlock(cup, null);
		registerBlock(plate);
		registerBlock(counter);
		registerBlock(counter_sink);
		registerBlock(dishwasher);
		registerBlock(kitchen_cabinet);
		registerBlock(chopping_board);
		registerBlock(bar_stool);
		registerBlock(mirror);
		registerBlock(mantel_piece);
		registerBlock(grand_chair_top, null);
		registerBlock(grand_chair_bottom);
		registerBlock(candle);
		registerBlock(chimney);
		registerBlock(wreath, new ItemWreath(wreath));
		registerBlock(fairy_light);
		registerBlock(fire_pit_off, null);
		registerBlock(fire_pit_on);
		registerBlock(trampoline);
		registerBlock(crate, new ItemCrate(crate));
		registerBlock(bench);
		registerBlock(table_outdoor);
		registerBlock(grill);
		registerBlock(divingboard_base, new ItemBath(divingboard_base));
		registerBlock(divingboard_plank, null);
		registerBlock(door_mat);
		registerBlock(esky);
	}

	public static void registerBlock(Block block)
	{
		registerBlock(block, new ItemBlock(block));
	}

	public static void registerBlock(Block block, ItemBlock item)
	{
		RegistrationHandler.BLOCKS.add(block);
		if (item != null) {
			item.setRegistryName(block.getRegistryName());
			FurnitureItems.RegistrationHandler.ITEMS.add(item);
		}
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
		registerRender(couch_jeb);
		registerRender(oven);
		registerRender(range_hood);
		registerRender(hedge_oak);
		registerRender(hedge_spruce);
		registerRender(hedge_birch);
		registerRender(hedge_jungle);
		registerRender(hedge_acacia);
		registerRender(hedge_dark_oak);
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
		registerRender(shower_head_off);
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
		registerRender(cup);
		registerRender(plate);
		registerRender(cookie_jar);
		registerRender(bar_stool);
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
		
		for(int i = 0; i < EnumDyeColor.values().length; i++) {
			registerRender(couch, i, "couch_" + EnumDyeColor.values()[i].getName());
		}
	}

	@SideOnly(Side.CLIENT)
	private static void registerRender(Block block)
	{
		registerRender(block, 0, block.getUnlocalizedName().substring(5));
	}

	@SideOnly(Side.CLIENT)
	private static void registerRender(Block block, int metadata, String fileName)
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), metadata, new ModelResourceLocation(Reference.MOD_ID + ":" + fileName, "inventory"));
	}

	@SideOnly(Side.CLIENT)
	private static void registerRender(Block block, String... fileNames)
	{
		for (int i = 0; i < fileNames.length; i++) {
			registerRender(block, i, fileNames[i]);
		}
	}

	@SideOnly(Side.CLIENT)
	private static void registerPresents()
	{
		ItemBlock blockItem = (ItemBlock) Item.getItemFromBlock(present);
		Block block = blockItem.getBlock();
		StateMapperBase b = new DefaultStateMapper();
		BlockStateContainer bsc = block.getBlockState();
		ImmutableList<IBlockState> values = bsc.getValidStates();
		for (IBlockState state : values) {
			String str = b.getPropertyString(state.getProperties());
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(present), block.getMetaFromState(state), new ModelResourceLocation(Reference.MOD_ID + ":" + "present_" + EnumDyeColor.values()[block.getMetaFromState(state)].getName(), str));
		}
	}

	@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
	public static class RegistrationHandler
	{
		public static final List<Block> BLOCKS = new LinkedList<>();

		@SubscribeEvent
		public static void registerItems(final RegistryEvent.Register<Block> event)
		{
			FurnitureBlocks.init();
			FurnitureBlocks.register();
			BLOCKS.stream().forEach(block -> event.getRegistry().register(block));
		}
	}
}
