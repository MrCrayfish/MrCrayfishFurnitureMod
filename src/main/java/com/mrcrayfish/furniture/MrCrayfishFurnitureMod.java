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
package com.mrcrayfish.furniture;

import com.mrcrayfish.furniture.api.IRecipeRegistry;
import com.mrcrayfish.furniture.api.RecipeRegistry;
import com.mrcrayfish.furniture.api.RecipeRegistryComm;
import com.mrcrayfish.furniture.api.Recipes;
import com.mrcrayfish.furniture.blocks.tv.Channels;
import com.mrcrayfish.furniture.entity.EntityMirror;
import com.mrcrayfish.furniture.entity.EntitySittableBlock;
import com.mrcrayfish.furniture.gui.GuiHandler;
import com.mrcrayfish.furniture.handler.ConfigurationHandler;
import com.mrcrayfish.furniture.handler.PlayerEvents;
import com.mrcrayfish.furniture.init.FurnitureCrafting;
import com.mrcrayfish.furniture.init.FurnitureTab;
import com.mrcrayfish.furniture.init.FurnitureTileEntities;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.proxy.CommonProxy;
import com.mrcrayfish.furniture.render.tileentity.MirrorRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY_CLASS, acceptedMinecraftVersions = Reference.ACCEPTED_MC_VERSIONS)
public class MrCrayfishFurnitureMod
{
	@Instance(Reference.MOD_ID)
	public static MrCrayfishFurnitureMod instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public static CreativeTabs tabFurniture = new FurnitureTab("tabFurniture");

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		/** Config Changed Event */
		MinecraftForge.EVENT_BUS.register(new ConfigurationHandler());

		/** Crafting Recipes */
		FurnitureCrafting.register();

		/** Packet Handler Init */
		PacketHandler.init();

		/** Configuration Handler Init */
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
		
		proxy.preInit();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		if (event.getSide() == Side.CLIENT)
		{
			MinecraftForge.EVENT_BUS.register(new MirrorRenderer());
		}
		MinecraftForge.EVENT_BUS.register(new PlayerEvents());

		/** GUI Handler Registering */
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		
		/** TileEntity Registering */
		FurnitureTileEntities.register();

		/** Entity Registering */
		EntityRegistry.registerModEntity(new ResourceLocation("cfm:mountable_block"), EntitySittableBlock.class, "MountableBlock", 0, this, 80, 1, false);
		if (event.getSide() == Side.CLIENT)
		{
			EntityRegistry.registerModEntity(new ResourceLocation("cfm:mirror"), EntityMirror.class, "Mirror", 1, this, 80, 1, false);
		}

		proxy.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		/** Initialize API */
		RecipeRegistry.registerDefaultRecipes();
		RecipeRegistry.registerConfigRecipes();
		Recipes.addCommRecipesToLocal();
		Recipes.updateDataList();
		
		Channels.registerChannels();
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
		catch (Exception e)
		{
			System.out.println("RecipeAPI: Unable to register comm recipes for " + modid);
			e.printStackTrace();
		}
	}
}
