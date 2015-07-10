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

import java.util.HashMap;
import java.util.Map;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class FurnitureAchievements
{
	public static Achievement installMod;
	public static Achievement mineKea;
	public static Achievement placeTree;
	public static Achievement unwrapPresent;
	public static Achievement houseParty;
	public static Achievement applianceCity;
	public static Achievement cookItem;
	public static Achievement buyItem;
	public static Achievement freezeItem;
	public static Achievement copyItem;
	public static Achievement whatDidYouEat;
	public static Achievement mailBox;
	public static Achievement sendMail;
	public static Achievement firstMail;
	public static Achievement donator;
	public static Achievement privacy;
	public static Achievement tapped;
	public static Achievement heyeyey;
	public static Achievement dingDong;
	public static Achievement careful;
	public static Achievement allClean;
	public static Achievement modernTechnology;
	public static Achievement gardening;
	public static Achievement bathroom;

	public static AchievementPage page;

	public static void loadAchievements()
	{
		installMod = new Achievement("achievement.cfm_install", "cfm_install", 0, 0, new ItemStack(FurnitureItems.itemCrayfish), null).setSpecial();
		installMod.registerStat();

		mineKea = new Achievement("achievement.cfm_minekea", "cfm_minekea", 2, 0, new ItemStack(FurnitureItems.itemChairWood), installMod);
		placeTree = new Achievement("achievement.cfm_placetree", "cfm_placetree", 3, 1, new ItemStack(FurnitureItems.itemTree), mineKea);
		unwrapPresent = new Achievement("achievement.cfm_unwrappresent", "cfm_unwrappresent", 4, 2, new ItemStack(FurnitureItems.itemPresent), placeTree);
		privacy = new Achievement("achievement.cfm_privacy", "cfm_privacy", 4, 0, new ItemStack(FurnitureItems.itemCurtains), mineKea);

		applianceCity = new Achievement("achievement.cfm_appliancecity", "cfm_appliancecity", 1, 2, new ItemStack(FurnitureItems.itemOven), installMod);
		cookItem = new Achievement("achievement.cfm_cookitem", "cfm_cookitem", 2, 3, new ItemStack(Items.cooked_chicken), applianceCity);
		freezeItem = new Achievement("achievement.cfm_freezeitem", "cfm_freezeitem", 0, 3, new ItemStack(Blocks.ice), applianceCity);

		modernTechnology = new Achievement("achievement.cfm_moderntechnology", "cfm_moderntechnology", -3, 2, new ItemStack(FurnitureItems.itemComputer), installMod);
		buyItem = new Achievement("achievement.cfm_buyitem", "cfm_buyitem", -2, 1, new ItemStack(Items.emerald), modernTechnology);
		copyItem = new Achievement("achievement.cfm_copyitem", "cfm_copyitem", -4, 1, new ItemStack(Items.book), modernTechnology);
		houseParty = new Achievement("achievement.cfm_houseparty", "cfm_houseparty", -3, 0, new ItemStack(FurnitureItems.itemStereo), modernTechnology);
		heyeyey = new Achievement("achievement.cfm_heyeyey", "cfm_heyeyey", -4, 3, new ItemStack(FurnitureItems.itemTV), modernTechnology);
		dingDong = new Achievement("achievement.cfm_dingdong", "cfm_dingdong", -3, 4, new ItemStack(FurnitureItems.itemDoorBell), modernTechnology);
		careful = new Achievement("achievement.cfm_careful", "cfm_careful", -2, 3, new ItemStack(FurnitureItems.itemElectricFence), modernTechnology);

		gardening = new Achievement("achievement.cfm_gardening", "cfm_gardening", -2, -2, new ItemStack(FurnitureItems.itemHedgeBasic), installMod);
		mailBox = new Achievement("achievement.cfm_mailbox", "cfm_mailbox", -3, -3, new ItemStack(FurnitureItems.itemMailBox), gardening);
		sendMail = new Achievement("achievement.cfm_sendmail", "cfm_sendmail", -4, -4, new ItemStack(FurnitureItems.itemEnvelope), mailBox);
		firstMail = new Achievement("achievement.cfm_firstmail", "cfm_firstmail", -4, -2, new ItemStack(FurnitureItems.itemPackage), mailBox);
		tapped = new Achievement("achievement.cfm_tapped", "cfm_tapped", -1, -3, new ItemStack(FurnitureItems.itemTap), gardening);
		donator = new Achievement("achievement.cfm_donator", "cfm_donator", -1, -4, new ItemStack(FurnitureItems.itemDollar), gardening);

		bathroom = new Achievement("achievement.cfm_bathroom", "cfm_bathroom", 2, -2, new ItemStack(FurnitureItems.itemBasin), installMod);
		whatDidYouEat = new Achievement("achievement.cfm_whatdidyoueat", "cfm_whatdidyoueat", 3, -3, new ItemStack(FurnitureItems.itemToilet), bathroom);
		allClean = new Achievement("achievement.cfm_allclean", "cfm_allclean", 1, -3, new ItemStack(FurnitureItems.itemShower), bathroom);

		page = new AchievementPage("Furniture Mod", installMod, mineKea, placeTree, unwrapPresent, privacy, applianceCity, cookItem, freezeItem, modernTechnology, buyItem, copyItem, houseParty, heyeyey, dingDong, careful, gardening, mailBox, sendMail, firstMail, tapped, donator, bathroom, whatDidYouEat, allClean);
	}

	public static void registerPage()
	{
		AchievementPage.registerAchievementPage(page);
	}
}
