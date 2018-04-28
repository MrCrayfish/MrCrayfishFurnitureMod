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
import com.mrcrayfish.furniture.items.*;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;
import java.util.List;

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

	public static void init()
	{
		itemCoolPack = new ItemGeneric().setUnlocalizedName("item_cool_pack").setRegistryName("item_cool_pack");
		itemInkCartridge = new ItemGeneric().setUnlocalizedName("item_ink_cartridge").setRegistryName("item_ink_cartridge");
		itemFlesh = new ItemFood(1, 2, false).setUnlocalizedName("item_flesh").setRegistryName("item_flesh").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemCookedFlesh = new ItemFood(4, 4, false).setUnlocalizedName("item_flesh_cooked").setRegistryName("item_flesh_cooked").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemEnvelope = new ItemEnvelope().setUnlocalizedName("item_envelope").setRegistryName("item_envelope");
		itemEnvelopeSigned = new ItemEnvelopeSigned().setUnlocalizedName("item_envelope_signed").setRegistryName("item_envelope_signed");
		itemPackage = new ItemPackage().setUnlocalizedName("item_package").setRegistryName("item_package");
		itemPackageSigned = new ItemPackageSigned().setUnlocalizedName("item_package_signed").setRegistryName("item_package_signed");
		itemHammer = new Item().setUnlocalizedName("item_hammer").setRegistryName("item_hammer");
		itemBreadSlice = new ItemFood(2, false).setUnlocalizedName("item_bread_slice").setRegistryName("item_bread_slice").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemToast = new ItemFood(4, false).setUnlocalizedName("item_toast").setRegistryName("item_toast").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemKnife = new ItemKnife().setMaxDamage(100).setUnlocalizedName("item_knife").setRegistryName("item_knife");
		itemCup = new ItemCup(false).setUnlocalizedName("item_cup").setRegistryName("item_cup");
		itemDrink = new ItemCup(true).setUnlocalizedName("item_drink").setRegistryName("item_drink");
		itemSoap = new ItemGeneric().setUnlocalizedName("item_soap").setRegistryName("item_soap");
		itemSoapyWater = new ItemGeneric().setUnlocalizedName("item_soap_water").setRegistryName("item_soap_water").setContainerItem(Items.BUCKET).setMaxStackSize(1);
		itemSuperSoapyWater = new ItemGeneric().setUnlocalizedName("item_super_soap_water").setRegistryName("item_super_soap_water").setContainerItem(Items.BUCKET).setMaxStackSize(1);
		itemRecipeBook = new ItemRecipeBook().setUnlocalizedName("item_recipe_book").setRegistryName("item_recipe_book");
		itemCrayfish = new Item().setUnlocalizedName("item_crayfish").setRegistryName("item_crayfish").setMaxStackSize(1);
		itemLog = new ItemLog(FurnitureBlocks.fire_pit_off).setUnlocalizedName("item_log").setRegistryName("item_log").setMaxStackSize(16);
		itemSpatula = new Item().setUnlocalizedName("item_spatula").setRegistryName("item_spatula").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemSausage = new ItemFood(1, false).setUnlocalizedName("item_sausage").setRegistryName("item_sausage").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemSausageCooked = new ItemFood(4, false).setUnlocalizedName("item_sausage_cooked").setRegistryName("item_sausage_cooked").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemKebab = new ItemFood(1, false).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 600, 0), 0.3F).setUnlocalizedName("item_kebab").setRegistryName("item_kebab").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemKebabCooked = new ItemFood(4, false).setUnlocalizedName("item_kebab_cooked").setRegistryName("item_kebab_cooked").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemCrowBar = new Item().setUnlocalizedName("item_crow_bar").setRegistryName("item_crow_bar").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
	}

	public static void register()
	{
		registerItem(itemFlesh);
		registerItem(itemCookedFlesh);
		registerItem(itemCoolPack);
		registerItem(itemHammer);
		registerItem(itemEnvelope);
		registerItem(itemEnvelopeSigned);
		registerItem(itemPackage);
		registerItem(itemPackageSigned);
		registerItem(itemInkCartridge);
		registerItem(itemBreadSlice);
		registerItem(itemToast);
		registerItem(itemKnife);
		registerItem(itemCup);
		registerItem(itemDrink);
		registerItem(itemSoap);
		registerItem(itemSoapyWater);
		registerItem(itemSuperSoapyWater);
		registerItem(itemRecipeBook);
		registerItem(itemCrayfish);
		registerItem(itemLog);
		registerItem(itemSpatula);
		registerItem(itemSausage);
		registerItem(itemSausageCooked);
		registerItem(itemKebab);
		registerItem(itemKebabCooked);
		registerItem(itemCrowBar);
	}

	public static void registerItem(Item item)
	{
		RegistrationHandler.ITEMS.add(item);
	}

	public static void registerRenders()
	{
		registerRender(itemFlesh);
		registerRender(itemCookedFlesh);
		registerRender(itemCoolPack);
		registerRender(itemHammer);
		registerRender(itemEnvelope);
		registerRender(itemEnvelopeSigned);
		registerRender(itemPackage);
		registerRender(itemPackageSigned);
		registerRender(itemInkCartridge);
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
		registerRender(itemLog);
		registerRender(itemSpatula);
		registerRender(itemSausage);
		registerRender(itemSausageCooked);
		registerRender(itemKebab);
		registerRender(itemKebabCooked);
		registerRender(itemCrowBar);
	}

	@SideOnly(Side.CLIENT)
	private static void registerRender(Item item)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}

	@SideOnly(Side.CLIENT)
	private static void registerRender(Item item, int metadata, String fileName)
	{
		ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(Reference.MOD_ID + ":" + fileName, "inventory"));
	}

	@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
	public static class RegistrationHandler
	{
		public static final List<Item> ITEMS = new LinkedList<>();

		@SubscribeEvent
		public static void registerItems(final RegistryEvent.Register<Item> event)
		{
			FurnitureItems.init();
			FurnitureItems.register();
			ITEMS.stream().forEach(item -> event.getRegistry().register(item));
		}
	}
}
