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
package com.mrcrayfish.furniture.proxy;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.render.tileentity.BlenderRenderer;
import com.mrcrayfish.furniture.render.tileentity.ChoppingBoardRenderer;
import com.mrcrayfish.furniture.render.tileentity.CookieRenderer;
import com.mrcrayfish.furniture.render.tileentity.CupRenderer;
import com.mrcrayfish.furniture.render.tileentity.DoorMatRenderer;
import com.mrcrayfish.furniture.render.tileentity.EskyRenderer;
import com.mrcrayfish.furniture.render.tileentity.GrillRenderer;
import com.mrcrayfish.furniture.render.tileentity.MicrowaveRenderer;
import com.mrcrayfish.furniture.render.tileentity.MirrorRenderer;
import com.mrcrayfish.furniture.render.tileentity.OvenRenderer;
import com.mrcrayfish.furniture.render.tileentity.PlateRenderer;
import com.mrcrayfish.furniture.render.tileentity.ToastRenderer;
import com.mrcrayfish.furniture.render.tileentity.TreeRenderer;
import com.mrcrayfish.furniture.render.tileentity.WashingMachineRenderer;
import com.mrcrayfish.furniture.tileentity.TileEntityBlender;
import com.mrcrayfish.furniture.tileentity.TileEntityChoppingBoard;
import com.mrcrayfish.furniture.tileentity.TileEntityCookieJar;
import com.mrcrayfish.furniture.tileentity.TileEntityCup;
import com.mrcrayfish.furniture.tileentity.TileEntityDoorMat;
import com.mrcrayfish.furniture.tileentity.TileEntityEsky;
import com.mrcrayfish.furniture.tileentity.TileEntityGrill;
import com.mrcrayfish.furniture.tileentity.TileEntityMicrowave;
import com.mrcrayfish.furniture.tileentity.TileEntityMirror;
import com.mrcrayfish.furniture.tileentity.TileEntityOven;
import com.mrcrayfish.furniture.tileentity.TileEntityPlate;
import com.mrcrayfish.furniture.tileentity.TileEntityToaster;
import com.mrcrayfish.furniture.tileentity.TileEntityTree;
import com.mrcrayfish.furniture.tileentity.TileEntityWashingMachine;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientProxy extends CommonProxy
{
	public static boolean rendering = false;
	public static Entity renderEntity = null;
	public static Entity backupEntity = null;

	@Override
	public void registerRenders()
	{
		for(EnumDyeColor dye : EnumDyeColor.values())
		{
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(FurnitureBlocks.present), dye.getMetadata(), new ModelResourceLocation(Reference.MOD_ID + ":" + "present_" + dye.getName(), "inventory"));
		}
		
		FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler((IItemColor) FurnitureItems.itemDrink, FurnitureItems.itemDrink);
		registerColorHandlerForBlock(FurnitureBlocks.hedge_oak);
		registerColorHandlerForBlock(FurnitureBlocks.hedge_spruce);
		registerColorHandlerForBlock(FurnitureBlocks.hedge_birch);
		registerColorHandlerForBlock(FurnitureBlocks.hedge_jungle);
		registerColorHandlerForBlock(FurnitureBlocks.hedge_acacia);
		registerColorHandlerForBlock(FurnitureBlocks.hedge_dark_oak);
		registerColorHandlerForBlock(FurnitureBlocks.tree_bottom);
		registerColorHandlerForBlock(FurnitureBlocks.tree_top);
		registerColorHandlerForBlock(FurnitureBlocks.wreath);
		
		FurnitureItems.registerRenders();
		FurnitureBlocks.registerRenders();

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCookieJar.class, new CookieRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPlate.class, new PlateRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityToaster.class, new ToastRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChoppingBoard.class, new ChoppingBoardRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlender.class, new BlenderRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMicrowave.class, new MicrowaveRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWashingMachine.class, new WashingMachineRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCup.class, new CupRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTree.class, new TreeRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMirror.class, new MirrorRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOven.class, new OvenRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGrill.class, new GrillRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEsky.class, new EskyRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDoorMat.class, new DoorMatRenderer());
	}
	
	public void registerColorHandlerForBlock(Block block)
	{
		FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler((IItemColor) Item.getItemFromBlock(block), Item.getItemFromBlock(block));
		FMLClientHandler.instance().getClient().getBlockColors().registerBlockColorHandler((IBlockColor) block, block);
	}

	@Override
	public EntityPlayer getClientPlayer()
	{
		return Minecraft.getMinecraft().player;
	}

	@Override
	public boolean isSinglePlayer()
	{
		return Minecraft.getMinecraft().isSingleplayer();
	}

	@Override
	public boolean isDedicatedServer()
	{
		return false;
	}

	@Override
	public void preInit()
	{
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onClientWorldLoad(WorldEvent.Load event)
	{
		if (event.getWorld() instanceof WorldClient)
		{
			MirrorRenderer.mirrorGlobalRenderer.setWorldAndLoadRenderers((WorldClient) event.getWorld());
		}
	}

	@SubscribeEvent
	public void onClientWorldUnload(WorldEvent.Unload event)
	{
		if (event.getWorld() instanceof WorldClient)
		{
			MirrorRenderer.clearRegisteredMirrors();
		}
	}


	@SubscribeEvent
	public void onPrePlayerRender(RenderPlayerEvent.Pre event)
	{
		if(!rendering)
			return;
		
		if(event.getEntityPlayer() == renderEntity)
		{
			this.backupEntity = Minecraft.getMinecraft().getRenderManager().renderViewEntity;
			Minecraft.getMinecraft().getRenderManager().renderViewEntity = renderEntity;
		}
	}

	@SubscribeEvent
	public void onPostPlayerRender(RenderPlayerEvent.Post event)
	{
		if(!rendering)
			return;
		
		if (event.getEntityPlayer() == renderEntity)
		{
			Minecraft.getMinecraft().getRenderManager().renderViewEntity = backupEntity;
			renderEntity = null;
		}
	}
}
