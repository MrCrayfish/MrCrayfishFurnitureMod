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
package com.mrcrayfish.furniture.handler;

import java.util.Random;

import com.mrcrayfish.furniture.init.FurnitureAchievements;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class PlayerEvents
{
	private final String PREFIX = "-> ";
	
	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent e)
	{
		EntityPlayer player = (EntityPlayer) e.player;
		player.triggerAchievement(FurnitureAchievements.installMod);
		if (ConfigurationHandler.canDisplay)
		{
			if (!player.worldObj.isRemote)
			{
				if (!ConfigurationHandler.hasDisplayedOnce)
				{
					ChatComponentText prefix = new ChatComponentText(EnumChatFormatting.GOLD + "Thank you for downloading MrCrayfish's Furniture Mod");
					prefix.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("You can disable this login message in the config")));
					player.addChatMessage(prefix);
					
					ChatComponentText url;
					Random rand = new Random();
					switch(rand.nextInt(4))
					{
					case 0:
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + PREFIX + EnumChatFormatting.GREEN + "Check out all MrCrayfish's Mods"));
						url = new ChatComponentText(EnumChatFormatting.GOLD + PREFIX + EnumChatFormatting.RESET + "mrcrayfish.com");
						url.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://mrcrayfish.com/mods"));
						url.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("Open URL")));
						player.addChatMessage(url);
						break;
					case 1:
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + PREFIX + EnumChatFormatting.GREEN + "Check out the Furniture Mod Wiki"));
						url = new ChatComponentText(EnumChatFormatting.GOLD + PREFIX + EnumChatFormatting.RESET + "mrcrayfishs-furniture-mod.wikia.com");
						url.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://mrcrayfishs-furniture-mod.wikia.com/"));
						url.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("Open URL")));
						player.addChatMessage(url);
						break;
					case 2:
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + PREFIX + EnumChatFormatting.GREEN + "Check out MrCrayfish's YouTube"));
						url = new ChatComponentText(EnumChatFormatting.GOLD + PREFIX + EnumChatFormatting.RESET + "youtube.com/user/MrCrayfishMinecraft");
						url.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.youtube.com/user/MrCrayfishMinecraft"));
						url.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("Open URL")));
						player.addChatMessage(url);
						break;
					case 3:
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + PREFIX + EnumChatFormatting.GREEN + "Check out the Community Edition for more Furniture!"));
						url = new ChatComponentText(EnumChatFormatting.GOLD + PREFIX + EnumChatFormatting.RESET + "mrcrayfish.com/furniture-comm-edition");
						url.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://mrcrayfish.com/furniture-comm-edition"));
						url.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("Open URL")));
						player.addChatMessage(url);
						break;
					}
					//ConfigurationHandler.hasDisplayedOnce = true;
				}
			}
		}
	}
}
