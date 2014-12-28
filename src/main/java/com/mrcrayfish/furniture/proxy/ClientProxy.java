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
package com.mrcrayfish.furniture.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.render.tileentity.BlenderRenderer;
import com.mrcrayfish.furniture.render.tileentity.ChoppingBoardRenderer;
import com.mrcrayfish.furniture.render.tileentity.CookieRenderer;
import com.mrcrayfish.furniture.render.tileentity.CupRenderer;
import com.mrcrayfish.furniture.render.tileentity.MicrowaveRenderer;
import com.mrcrayfish.furniture.render.tileentity.PlateRenderer;
import com.mrcrayfish.furniture.render.tileentity.ToastRenderer;
import com.mrcrayfish.furniture.render.tileentity.WashingMachineRenderer;
import com.mrcrayfish.furniture.tileentity.TileEntityBlender;
import com.mrcrayfish.furniture.tileentity.TileEntityChoppingBoard;
import com.mrcrayfish.furniture.tileentity.TileEntityCookieJar;
import com.mrcrayfish.furniture.tileentity.TileEntityCup;
import com.mrcrayfish.furniture.tileentity.TileEntityMicrowave;
import com.mrcrayfish.furniture.tileentity.TileEntityPlate;
import com.mrcrayfish.furniture.tileentity.TileEntityToaster;
import com.mrcrayfish.furniture.tileentity.TileEntityWashingMachine;

public class ClientProxy extends CommonProxy
{
	public static int renderPass;

	@Override
	public void registerRenders()
	{
		FurnitureItems.registerRenders();
		
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCookieJar.class, new CookieRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPlate.class, new PlateRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityToaster.class, new ToastRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChoppingBoard.class, new ChoppingBoardRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlender.class, new BlenderRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMicrowave.class, new MicrowaveRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWashingMachine.class, new WashingMachineRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCup.class, new CupRenderer());
	}

	@Override
	public EntityPlayer getClientPlayer()
	{
		return Minecraft.getMinecraft().thePlayer;
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
}
