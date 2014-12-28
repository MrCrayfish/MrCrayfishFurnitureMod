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
package com.mrcrayfish.furniture.init;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.items.ItemBath;
import com.mrcrayfish.furniture.items.ItemCup;
import com.mrcrayfish.furniture.items.ItemEnvelope;
import com.mrcrayfish.furniture.items.ItemEnvelopeSigned;
import com.mrcrayfish.furniture.items.ItemFridge;
import com.mrcrayfish.furniture.items.ItemNotAdded;
import com.mrcrayfish.furniture.items.ItemPackage;
import com.mrcrayfish.furniture.items.ItemPackageSigned;
import com.mrcrayfish.furniture.items.ItemPlacer;
import com.mrcrayfish.furniture.items.ItemRecipeBook;

public class FurnitureItems
{
	/** Initial Furniture */
	public static Item itemTableWood, itemTableStone;
	public static Item itemChairWood, itemChairStone;
	public static Item itemCabinet, itemBedsideCabinet;
	public static Item itemCoffeeTableWood, itemCoffeeTableStone;
	public static Item itemFridge, itemCoolPack;
	public static Item itemCouch;
	public static Item itemBlinds, itemCurtains;
	public static Item itemOven, itemOvenRangehood;
	public static Item itemFlesh, itemCookedFlesh;

	/** Garden Update */
	public static Item itemHedgeBasic, itemHedgeSpruce, itemHedgeBirch, itemHedgeJungle, itemHedgeAcacia, itemHedgeDarkOak;
	public static Item itemBirdBath, itemStonePath, itemWhiteFence, itemTap;
	public static Item itemMailBox, itemHammer;
	public static Item itemEnvelope, itemEnvelopeSigned;
	public static Item itemPackage, itemPackageSigned;

	/** Electronic Update */
	public static Item itemTV, itemComputer, itemPrinter, itemInkCartridge, itemStereo;
	public static Item itemElectricFence;
	public static Item itemCeilingLight, itemDoorBell, itemFireAlarm, itemLamp;

	/** Bathroom Update */
	public static Item itemToilet, itemBasin, itemWallCabinet, itemBath, itemShower, itemShowerHead, itemBin;

	/** Kitchen Update */
	public static Item itemToaster, itemMicrowave, itemBlender, itemWashingMachine, itemDishWasher;
	public static Item itemCounterDoored, itemCounterSink, itemKitchenCabinet;
	public static Item itemPlate, itemCookieJar, itemBarStool, itemChoppingBoard;
	public static Item itemKnife, itemCup, itemDrink, itemSoap, itemSoapyWater, itemSuperSoapyWater;

	/** Food */
	public static Item itemBreadSlice, itemToast;

	/** Misc */
	public static Item itemRecipeBook;
	public static Item itemPresentRed;
	public static Item itemPresentGreen;
	public static Item itemTree;
	public static Item itemCrayfish;
	public static Item itemDollar;

	public static void init()
	{
		itemTableWood        = new ItemNotAdded().setUnlocalizedName("item_table_wood").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemTableStone       = new ItemNotAdded().setUnlocalizedName("item_table_stone").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemChairWood        = new ItemPlacer(FurnitureBlocks.chair_wood).setUnlocalizedName("item_chair_wood").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemChairStone       = new ItemPlacer(FurnitureBlocks.chair_stone).setUnlocalizedName("item_chair_stone").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemCabinet          = new ItemPlacer(FurnitureBlocks.cabinet).setUnlocalizedName("item_cabinet").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemCoffeeTableWood  = new ItemPlacer(FurnitureBlocks.coffee_table_wood).setUnlocalizedName("item_coffee_table_wood").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemCoffeeTableStone = new ItemPlacer(FurnitureBlocks.coffee_table_stone).setUnlocalizedName("item_coffee_table_stone").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemFridge           = new ItemFridge().setUnlocalizedName("item_fridge").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemCouch 			 = new ItemPlacer(FurnitureBlocks.couch).setUnlocalizedName("item_couch").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemBlinds 			 = new ItemPlacer(FurnitureBlocks.blinds).setUnlocalizedName("item_blinds").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemCurtains 		 = new ItemPlacer(FurnitureBlocks.curtains).setUnlocalizedName("item_curtains").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemLamp             = new ItemPlacer(FurnitureBlocks.lamp_off).setUnlocalizedName("item_lamp").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemBedsideCabinet   = new ItemPlacer(FurnitureBlocks.bedside_cabinet).setUnlocalizedName("item_bedside_cabinet").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemCoolPack         = new Item().setUnlocalizedName("item_cool_pack").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemOven             = new ItemPlacer(FurnitureBlocks.oven).setUnlocalizedName("item_oven").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemOvenRangehood    = new ItemPlacer(FurnitureBlocks.range_hood).setUnlocalizedName("item_range_hood").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemFlesh            = new ItemFood(1, 2, false).setUnlocalizedName("item_flesh").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemCookedFlesh      = new ItemFood(4, 4, false).setUnlocalizedName("item_flesh_cooked").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemHedgeBasic       = new ItemPlacer(FurnitureBlocks.hedge_oak).setUnlocalizedName("item_hedge_basic").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemHedgeSpruce      = new ItemPlacer(FurnitureBlocks.hedge_spruce).setUnlocalizedName("item_hedge_spruce").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemHedgeBirch       = new ItemPlacer(FurnitureBlocks.hedge_birch).setUnlocalizedName("item_hedge_birch").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemHedgeJungle      = new ItemPlacer(FurnitureBlocks.hedge_jungle).setUnlocalizedName("item_hedge_jungle").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemHedgeAcacia      = new ItemPlacer(FurnitureBlocks.hedge_acacia).setUnlocalizedName("item_hedge_acacia").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemHedgeDarkOak     = new ItemPlacer(FurnitureBlocks.hedge_dark_oak).setUnlocalizedName("item_hedge_dark_oak").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemBirdBath         = new ItemNotAdded().setUnlocalizedName("item_bird_bath").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemStonePath        = new ItemPlacer(FurnitureBlocks.stone_path).setUnlocalizedName("item_stone_path").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemWhiteFence       = new ItemPlacer(FurnitureBlocks.white_fence).setUnlocalizedName("item_white_fence").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemTap              = new ItemPlacer(FurnitureBlocks.tap).setUnlocalizedName("item_tap").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemMailBox          = new ItemPlacer(FurnitureBlocks.mail_box).setUnlocalizedName("item_mail_box").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemEnvelope         = new ItemEnvelope().setUnlocalizedName("item_envelope").setMaxStackSize(1).setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemEnvelopeSigned   = new ItemEnvelopeSigned().setUnlocalizedName("item_envelope_signed").setMaxStackSize(1);
		itemPackage          = new ItemPackage().setUnlocalizedName("item_package").setMaxStackSize(1).setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemPackageSigned    = new ItemPackageSigned().setUnlocalizedName("item_package_signed").setMaxStackSize(1);
		itemHammer           = new Item().setUnlocalizedName("item_hammer").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemTV               = new ItemPlacer(FurnitureBlocks.tv).setUnlocalizedName("item_tv").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemComputer         = new ItemPlacer(FurnitureBlocks.computer).setUnlocalizedName("item_computer").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemPrinter          = new ItemPlacer(FurnitureBlocks.printer).setUnlocalizedName("item_printer").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemInkCartridge     = new Item().setUnlocalizedName("item_ink_cartridge").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemElectricFence    = new ItemPlacer(FurnitureBlocks.electric_fence).setUnlocalizedName("item_electric_fence").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemFireAlarm        = new ItemPlacer(FurnitureBlocks.fire_alarm_off).setUnlocalizedName("item_fire_alarm").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemCeilingLight     = new ItemPlacer(FurnitureBlocks.ceiling_light_off).setUnlocalizedName("item_ceiling_light").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemDoorBell         = new ItemPlacer(FurnitureBlocks.door_bell).setUnlocalizedName("item_door_bell").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemStereo           = new ItemPlacer(FurnitureBlocks.stereo).setUnlocalizedName("item_stereo").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemToilet           = new ItemNotAdded().setUnlocalizedName("item_toilet").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemBasin            = new ItemPlacer(FurnitureBlocks.basin).setUnlocalizedName("item_basin").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemWallCabinet      = new ItemPlacer(FurnitureBlocks.wall_cabinet).setUnlocalizedName("item_wall_cabinet").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemBath             = new ItemBath().setUnlocalizedName("item_bath").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemShower           = new ItemPlacer(FurnitureBlocks.shower_bottom).setUnlocalizedName("item_shower").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemShowerHead       = new ItemNotAdded().setUnlocalizedName("item_shower_head").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemBin              = new ItemPlacer(FurnitureBlocks.bin).setUnlocalizedName("item_bin").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemPresentRed       = new ItemNotAdded().setUnlocalizedName("item_present_red").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemPresentGreen     = new ItemNotAdded().setUnlocalizedName("item_present_green").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemTree             = new ItemNotAdded().setUnlocalizedName("item_tree").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemToaster          = new ItemPlacer(FurnitureBlocks.toaster).setUnlocalizedName("item_toaster").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemMicrowave        = new ItemPlacer(FurnitureBlocks.microwave).setUnlocalizedName("item_microwave").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemWashingMachine   = new ItemPlacer(FurnitureBlocks.washing_machine).setUnlocalizedName("item_washing_machine").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemCookieJar        = new ItemPlacer(FurnitureBlocks.cookie_jar).setUnlocalizedName("item_cookie_jar").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemBlender          = new ItemPlacer(FurnitureBlocks.blender).setUnlocalizedName("item_blender").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemPlate            = new ItemPlacer(FurnitureBlocks.plate).setUnlocalizedName("item_plate").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemCounterDoored    = new ItemNotAdded().setUnlocalizedName("item_kitchen_counter").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemCounterSink      = new ItemNotAdded().setUnlocalizedName("item_kitchen_counter_sink").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemDishWasher       = new ItemPlacer(FurnitureBlocks.dishwasher).setUnlocalizedName("item_dishwasher").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemKitchenCabinet   = new ItemPlacer(FurnitureBlocks.kitchen_cabinet).setUnlocalizedName("item_kitchen_cabinet").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemChoppingBoard    = new ItemPlacer(FurnitureBlocks.chopping_board).setUnlocalizedName("item_chopping_board").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemBarStool         = new ItemPlacer(FurnitureBlocks.bar_stool).setUnlocalizedName("item_bar_stool").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture).setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemBreadSlice       = new ItemFood(2, false).setUnlocalizedName("item_bread_slice").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemToast            = new ItemFood(4, false).setUnlocalizedName("item_toast").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemKnife            = new ItemSword(ToolMaterial.STONE).setMaxDamage(100).setUnlocalizedName("item_knife").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemCup              = new ItemCup(false).setUnlocalizedName("item_cup").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemDrink            = new ItemCup(true).setUnlocalizedName("item_drink").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemSoap             = new Item().setUnlocalizedName("item_soap").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemSoapyWater       = new Item().setUnlocalizedName("item_soap_water").setContainerItem(Items.bucket).setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemSuperSoapyWater  = new Item().setUnlocalizedName("item_super_soap_water").setContainerItem(Items.bucket).setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemRecipeBook       = new ItemRecipeBook().setUnlocalizedName("item_recipe_book").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemCrayfish         = new Item().setUnlocalizedName("item_crayfish").setMaxStackSize(1);
		itemDollar           = new Item().setUnlocalizedName("item_money").setMaxStackSize(1);
	}

	public static void registerItems()
	{
		GameRegistry.registerItem(itemTableWood, itemTableWood.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemTableStone, itemTableStone.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemChairWood, itemChairWood.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemChairStone, itemChairStone.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemCabinet, itemCabinet.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemBedsideCabinet, itemBedsideCabinet.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemCoffeeTableWood, itemCoffeeTableWood.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemCoffeeTableStone, itemCoffeeTableStone.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemFridge, itemFridge.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemCoolPack, itemCoolPack.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemCouch, itemCouch.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemBlinds, itemBlinds.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemCurtains, itemCurtains.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemOven, itemOven.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemOvenRangehood, itemOvenRangehood.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemFlesh, itemFlesh.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemCookedFlesh, itemCookedFlesh.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemHedgeBasic, itemHedgeBasic.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemHedgeSpruce, itemHedgeSpruce.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemHedgeBirch, itemHedgeBirch.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemHedgeJungle, itemHedgeJungle.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemHedgeAcacia, itemHedgeAcacia.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemHedgeDarkOak, itemHedgeDarkOak.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemBirdBath, itemBirdBath.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemStonePath, itemStonePath.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemWhiteFence, itemWhiteFence.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemTap, itemTap.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemMailBox, itemMailBox.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemHammer, itemHammer.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemEnvelope, itemEnvelope.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemEnvelopeSigned, itemEnvelopeSigned.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemPackage, itemPackage.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemPackageSigned, itemPackageSigned.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemTV, itemTV.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemComputer, itemComputer.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemPrinter, itemPrinter.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemInkCartridge, itemInkCartridge.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemStereo, itemStereo.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemElectricFence, itemElectricFence.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemCeilingLight, itemCeilingLight.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemDoorBell, itemDoorBell.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemFireAlarm, itemFireAlarm.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemLamp, itemLamp.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemToilet, itemToilet.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemBasin, itemBasin.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemWallCabinet, itemWallCabinet.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemBath, itemBath.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemShower, itemShower.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemShowerHead, itemShowerHead.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemBin, itemBin.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemPresentRed, itemPresentRed.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemPresentGreen, itemPresentGreen.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemTree, itemTree.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemToaster, itemToaster.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemMicrowave, itemMicrowave.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemWashingMachine, itemWashingMachine.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemCookieJar, itemCookieJar.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemBlender, itemBlender.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemPlate, itemPlate.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemCounterDoored, itemCounterDoored.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemCounterSink, itemCounterSink.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemDishWasher, itemDishWasher.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemKitchenCabinet, itemKitchenCabinet.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemChoppingBoard, itemChoppingBoard.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemBarStool, itemBarStool.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemBreadSlice, itemBreadSlice.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemToast, itemToast.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemKnife, itemKnife.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemCup, itemCup.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemDrink, itemDrink.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemSoap, itemSoap.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemSoapyWater, itemSoapyWater.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemSuperSoapyWater, itemSuperSoapyWater.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemRecipeBook, itemRecipeBook.getUnlocalizedName().substring(5));
	}

	public static void registerRenders()
	{
		registerRender(itemTableWood);
		registerRender(itemTableStone);
		registerRender(itemChairWood);
		registerRender(itemChairStone);
		registerRender(itemCabinet);
		registerRender(itemBedsideCabinet);
		registerRender(itemCoffeeTableWood);
		registerRender(itemCoffeeTableStone);
		registerRender(itemFridge);
		registerRender(itemCoolPack);
		registerRender(itemCouch);
		registerRender(itemBlinds);
		registerRender(itemCurtains);
		registerRender(itemOven);
		registerRender(itemOvenRangehood);
		registerRender(itemFlesh);
		registerRender(itemCookedFlesh);
		registerRender(itemHedgeBasic);
		registerRender(itemHedgeSpruce);
		registerRender(itemHedgeBirch);
		registerRender(itemHedgeJungle);
		registerRender(itemHedgeAcacia);
		registerRender(itemHedgeDarkOak);
		registerRender(itemBirdBath);
		registerRender(itemStonePath);
		registerRender(itemWhiteFence);
		registerRender(itemTap);
		registerRender(itemMailBox);
		registerRender(itemHammer);
		registerRender(itemEnvelope);
		registerRender(itemEnvelopeSigned);
		registerRender(itemPackage);
		registerRender(itemPackageSigned);
		registerRender(itemTV);
		registerRender(itemComputer);
		registerRender(itemPrinter);
		registerRender(itemInkCartridge);
		registerRender(itemStereo);
		registerRender(itemElectricFence);
		registerRender(itemCeilingLight);
		registerRender(itemDoorBell);
		registerRender(itemFireAlarm);
		registerRender(itemLamp);
		registerRender(itemToilet);
		registerRender(itemBasin);
		registerRender(itemWallCabinet);
		registerRender(itemBath);
		registerRender(itemShower);
		registerRender(itemShowerHead);
		registerRender(itemBin);
		registerRender(itemPresentRed);
		registerRender(itemPresentGreen);
		registerRender(itemTree);
		registerRender(itemToaster);
		registerRender(itemMicrowave);
		registerRender(itemWashingMachine);
		registerRender(itemCookieJar);
		registerRender(itemBlender);
		registerRender(itemPlate);
		registerRender(itemCounterDoored);
		registerRender(itemCounterSink);
		registerRender(itemDishWasher);
		registerRender(itemKitchenCabinet);
		registerRender(itemChoppingBoard);
		registerRender(itemBarStool);
		registerRender(itemBreadSlice);
		registerRender(itemToast);
		registerRender(itemKnife);
		registerRender(itemCup);
		registerRender(itemDrink);
		registerRender(itemSoap);
		registerRender(itemSoapyWater);
		registerRender(itemSuperSoapyWater);
		registerRender(itemRecipeBook);
		registerRender(itemCrayfish);
		registerRender(itemDollar);
	}

	public static void registerRender(Item item)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
