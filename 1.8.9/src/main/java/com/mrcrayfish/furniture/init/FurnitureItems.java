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

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.items.ItemCup;
import com.mrcrayfish.furniture.items.ItemEnvelope;
import com.mrcrayfish.furniture.items.ItemEnvelopeSigned;
import com.mrcrayfish.furniture.items.ItemGeneric;
import com.mrcrayfish.furniture.items.ItemHammer;
import com.mrcrayfish.furniture.items.ItemKnife;
import com.mrcrayfish.furniture.items.ItemLog;
import com.mrcrayfish.furniture.items.ItemPackage;
import com.mrcrayfish.furniture.items.ItemPackageSigned;
import com.mrcrayfish.furniture.items.ItemPlacer;
import com.mrcrayfish.furniture.items.ItemPresent;
import com.mrcrayfish.furniture.items.ItemRecipeBook;
import com.mrcrayfish.furniture.items.ItemSpatula;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class FurnitureItems
{
	/** Initial Furniture */
	public static Item itemFlesh, itemCookedFlesh, itemCoolPack;

	/** Garden Update */
	public static Item itemHammer;
	public static Item itemEnvelope, itemEnvelopeSigned;
	public static Item itemPackage, itemPackageSigned;

	/** Electronic Update */
	public static Item itemInkCartridge;

	/** Bathroom Update */

	/** Kitchen Update */
	public static Item itemKnife, itemCup, itemDrink, itemSoap, itemSoapyWater, itemSuperSoapyWater;

	/** Christmas Update */
	public static Item itemLog;

	/** Outdoor Update */
	public static Item itemSpatula, itemSausage, itemSausageCooked, itemKebab, itemKebabCooked, itemCrowBar;

	/** Food */
	public static Item itemBreadSlice, itemToast;

	/** Misc */
	public static Item itemRecipeBook;
	public static Item itemCrayfish;
	public static Item itemDollar;

	public static void init()
	{
		itemCoolPack = new ItemGeneric().setUnlocalizedName("item_cool_pack");
		itemInkCartridge = new ItemGeneric().setUnlocalizedName("item_ink_cartridge");
		itemFlesh = new ItemFood(1, 2, false).setUnlocalizedName("item_flesh").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemCookedFlesh = new ItemFood(4, 4, false).setUnlocalizedName("item_flesh_cooked").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemEnvelope = new ItemEnvelope().setUnlocalizedName("item_envelope");
		itemEnvelopeSigned = new ItemEnvelopeSigned().setUnlocalizedName("item_envelope_signed");
		itemPackage = new ItemPackage().setUnlocalizedName("item_package");
		itemPackageSigned = new ItemPackageSigned().setUnlocalizedName("item_package_signed");
		itemHammer = new ItemHammer().setUnlocalizedName("item_hammer");
		itemBreadSlice = new ItemFood(2, false).setUnlocalizedName("item_bread_slice").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemToast = new ItemFood(4, false).setUnlocalizedName("item_toast").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemKnife = new ItemKnife().setMaxDamage(100).setUnlocalizedName("item_knife");
		itemCup = new ItemCup(false).setUnlocalizedName("item_cup");
		itemDrink = new ItemCup(true).setUnlocalizedName("item_drink");
		itemSoap = new ItemGeneric().setUnlocalizedName("item_soap");
		itemSoapyWater = new ItemGeneric().setUnlocalizedName("item_soap_water").setContainerItem(Items.bucket).setMaxStackSize(1);
		itemSuperSoapyWater = new ItemGeneric().setUnlocalizedName("item_super_soap_water").setContainerItem(Items.bucket).setMaxStackSize(1);
		itemRecipeBook = new ItemRecipeBook().setUnlocalizedName("item_recipe_book");
		itemCrayfish = new Item().setUnlocalizedName("item_crayfish").setMaxStackSize(1);
		itemDollar = new Item().setUnlocalizedName("item_money").setMaxStackSize(1);
		itemLog = new ItemLog(FurnitureBlocks.fire_pit_off).setUnlocalizedName("item_log").setMaxStackSize(16);
		itemSpatula = new ItemSpatula().setUnlocalizedName("item_spatula");
		itemSausage = new ItemFood(1, false).setUnlocalizedName("item_sausage").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemSausageCooked = new ItemFood(4, false).setUnlocalizedName("item_sausage_cooked").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemKebab = new ItemFood(1, false).setPotionEffect(Potion.hunger.id, 30, 0, 0.3F).setUnlocalizedName("item_kebab").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemKebabCooked = new ItemFood(4, false).setUnlocalizedName("item_kebab_cooked").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemCrowBar = new Item().setUnlocalizedName("item_crow_bar").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
	}

	public static void registerItems()
	{
		GameRegistry.registerItem(itemFlesh, itemFlesh.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemCookedFlesh, itemCookedFlesh.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemHammer, itemHammer.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemEnvelope, itemEnvelope.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemEnvelopeSigned, itemEnvelopeSigned.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemPackage, itemPackage.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemPackageSigned, itemPackageSigned.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemBreadSlice, itemBreadSlice.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemToast, itemToast.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemKnife, itemKnife.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemCup, itemCup.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemDrink, itemDrink.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemSoap, itemSoap.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemSoapyWater, itemSoapyWater.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemSuperSoapyWater, itemSuperSoapyWater.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemRecipeBook, itemRecipeBook.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemCrayfish, itemCrayfish.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemDollar, itemDollar.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemLog, itemLog.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemSpatula, itemSpatula.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemSausage, itemSausage.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemSausageCooked, itemSausageCooked.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemKebab, itemKebab.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemKebabCooked, itemKebabCooked.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemCrowBar, itemCrowBar.getUnlocalizedName().substring(5));
	}

	public static void registerRenders()
	{
		registerRender(itemFlesh);
		registerRender(itemCookedFlesh);
		registerRender(itemHammer);
		registerRender(itemEnvelope);
		registerRender(itemEnvelopeSigned);
		registerRender(itemPackage);
		registerRender(itemPackageSigned);
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
		registerRender(itemLog);
		registerRender(itemSpatula);
		registerRender(itemSausage);
		registerRender(itemSausageCooked);
		registerRender(itemKebab);
		registerRender(itemKebabCooked);
		registerRender(itemCrowBar);
	}

	private static void registerRender(Item item)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}

	private static void registerRender(Item item, int maxMeta)
	{
		for (int i = 0; i < maxMeta; i++)
		{
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, i, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
		}
	}
}
