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
package com.mrcrayfish.furniture;

import java.awt.Color;
import java.lang.reflect.Method;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCMessage;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;

import com.mrcrayfish.furniture.api.IRecipeRegistry;
import com.mrcrayfish.furniture.api.RecipeRegistry;
import com.mrcrayfish.furniture.api.RecipeRegistryComm;
import com.mrcrayfish.furniture.api.Recipes;
import com.mrcrayfish.furniture.entity.EntitySittableBlock;
import com.mrcrayfish.furniture.gui.GuiHandler;
import com.mrcrayfish.furniture.handler.ConfigurationHandler;
import com.mrcrayfish.furniture.handler.CraftingHandler;
import com.mrcrayfish.furniture.handler.InputHandler;
import com.mrcrayfish.furniture.handler.PlayerEvents;
import com.mrcrayfish.furniture.handler.SyncEvent;
import com.mrcrayfish.furniture.init.FurnitureAchievements;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureCrafting;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.init.FurnitureTab;
import com.mrcrayfish.furniture.init.FurnitureTileEntities;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.proxy.CommonProxy;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY_CLASS)
public class MrCrayfishFurnitureMod
{

	@Instance(Reference.MOD_ID)
	public static MrCrayfishFurnitureMod instance = new MrCrayfishFurnitureMod();

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	private GuiHandler gui_handler;

	public static CreativeTabs tabFurniture = new FurnitureTab("tabFurniture");

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		/** Config Changed Event */
		FMLCommonHandler.instance().bus().register(new ConfigurationHandler());

		/** Block and Item Registring */
		FurnitureBlocks.init();
		FurnitureItems.init();
		FurnitureBlocks.register();
		FurnitureItems.registerItems();

		/** Achievement Registering */
		FurnitureAchievements.loadAchievements();
		FurnitureAchievements.registerPage();

		/** Packet Handler Init */
		PacketHandler.init();
		
		/** Event Registering */
		FMLCommonHandler.instance().bus().register(new CraftingHandler());
		FMLCommonHandler.instance().bus().register(new PlayerEvents());
		if (event.getSide() == Side.CLIENT)
		{
			FMLCommonHandler.instance().bus().register(new InputHandler());
		}
		else
		{
			FMLCommonHandler.instance().bus().register(new SyncEvent());
		}
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		/** Render Registering */
		proxy.registerRenders();

		/** GUI Handler Registering */
		gui_handler = new GuiHandler();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, gui_handler);

		/** TileEntity Registering */
		FurnitureTileEntities.register();

		/** Entity Registering */
		EntityRegistry.registerModEntity(EntitySittableBlock.class, "MountableBlock", 0, this, 80, 1, false);

		/** Crafting Recipes */
		FurnitureCrafting.register();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		/** Initialize API */
		RecipeRegistry.registerDefaultRecipes();
		RecipeRegistry.registerConfigRecipes();
		Recipes.addCommRecipesToLocal();
		Recipes.updateDataList();
		
		
		System.out.println(new Color(21,17,17).getRGB());
		System.out.println(new Color(42,51,129).getRGB());
		System.out.println(new Color(72,46,28).getRGB());
		System.out.println(new Color(42,101,126).getRGB());
		System.out.println(new Color(59,59,59).getRGB());
		System.out.println(new Color(49,65,25).getRGB());
		System.out.println(new Color(88,123,194).getRGB());
		System.out.println(new Color(59,160,52).getRGB());
		System.out.println(new Color(172,66,182).getRGB());
		System.out.println(new Color(217,117,51).getRGB());
		System.out.println(new Color(201,115,139).getRGB());
		System.out.println(new Color(117,55,169).getRGB());
		System.out.println(new Color(138,49,44).getRGB());
		System.out.println(new Color(141,149,149).getRGB());
		System.out.println(new Color(211,211,211).getRGB());
		System.out.println(new Color(207,194,49).getRGB());
	}

	@EventHandler
	public void processIMC(FMLInterModComms.IMCEvent event)
	{
		if (event.getMessages().size() > 0)
		{
			if (ConfigurationHandler.api_debug)
			{
				System.out.println("RecipeAPI (InterModComm): Registering recipes from " + event.getMessages().size() + " mod(s).");
			}
		}
		for (IMCMessage imcMessage : event.getMessages())
		{
			if (!imcMessage.isStringMessage())
				continue;

			if (imcMessage.key.equalsIgnoreCase("register"))
			{
				register(imcMessage.getStringValue(), imcMessage.getSender());
			}
		}
	}

	public void register(String method, String modid)
	{
		String[] data = method.split("\\.");
		String methodName = data[data.length - 1];
		String className = method.substring(0, method.length() - methodName.length() - 1);
		String modName = Loader.instance().getIndexedModList().get(modid).getName();

		try
		{
			Class clazz = Class.forName(className);
			Method registerMethod = clazz.getDeclaredMethod(methodName, IRecipeRegistry.class);
			registerMethod.invoke(null, (IRecipeRegistry) RecipeRegistryComm.getInstance(modName));
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
