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

import com.mrcrayfish.furniture.tileentity.TileEntityBath;
import com.mrcrayfish.furniture.tileentity.TileEntityBedsideCabinet;
import com.mrcrayfish.furniture.tileentity.TileEntityBin;
import com.mrcrayfish.furniture.tileentity.TileEntityBlender;
import com.mrcrayfish.furniture.tileentity.TileEntityCabinet;
import com.mrcrayfish.furniture.tileentity.TileEntityCabinetKitchen;
import com.mrcrayfish.furniture.tileentity.TileEntityChoppingBoard;
import com.mrcrayfish.furniture.tileentity.TileEntityComputer;
import com.mrcrayfish.furniture.tileentity.TileEntityCookieJar;
import com.mrcrayfish.furniture.tileentity.TileEntityCouch;
import com.mrcrayfish.furniture.tileentity.TileEntityCup;
import com.mrcrayfish.furniture.tileentity.TileEntityDishwasher;
import com.mrcrayfish.furniture.tileentity.TileEntityFreezer;
import com.mrcrayfish.furniture.tileentity.TileEntityFridge;
import com.mrcrayfish.furniture.tileentity.TileEntityMailBox;
import com.mrcrayfish.furniture.tileentity.TileEntityMicrowave;
import com.mrcrayfish.furniture.tileentity.TileEntityMirror;
import com.mrcrayfish.furniture.tileentity.TileEntityOven;
import com.mrcrayfish.furniture.tileentity.TileEntityPlate;
import com.mrcrayfish.furniture.tileentity.TileEntityPresent;
import com.mrcrayfish.furniture.tileentity.TileEntityPrinter;
import com.mrcrayfish.furniture.tileentity.TileEntityShowerHead;
import com.mrcrayfish.furniture.tileentity.TileEntityStereo;
import com.mrcrayfish.furniture.tileentity.TileEntityTV;
import com.mrcrayfish.furniture.tileentity.TileEntityToaster;
import com.mrcrayfish.furniture.tileentity.TileEntityTree;
import com.mrcrayfish.furniture.tileentity.TileEntityWallCabinet;
import com.mrcrayfish.furniture.tileentity.TileEntityWashingMachine;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class FurnitureTileEntities
{
	public static void register()
	{
		GameRegistry.registerTileEntity(TileEntityOven.class, "cfmOven");
		GameRegistry.registerTileEntity(TileEntityFridge.class, "cfmFridge");
		GameRegistry.registerTileEntity(TileEntityCabinet.class, "cfmCabinet");
		GameRegistry.registerTileEntity(TileEntityFreezer.class, "cfmFreezer");
		GameRegistry.registerTileEntity(TileEntityBedsideCabinet.class, "cfmBedsideCabinet");
		GameRegistry.registerTileEntity(TileEntityMailBox.class, "cfmMailBox");
		GameRegistry.registerTileEntity(TileEntityComputer.class, "cfmComputer");
		GameRegistry.registerTileEntity(TileEntityPrinter.class, "cfmPrinter");
		GameRegistry.registerTileEntity(TileEntityTV.class, "cfmTV");
		GameRegistry.registerTileEntity(TileEntityStereo.class, "cfmStereo");
		GameRegistry.registerTileEntity(TileEntityPresent.class, "cfmPresent");
		GameRegistry.registerTileEntity(TileEntityBin.class, "cfmBin");
		GameRegistry.registerTileEntity(TileEntityWallCabinet.class, "cfmWallCabinet");
		GameRegistry.registerTileEntity(TileEntityBath.class, "cfmBath");
		GameRegistry.registerTileEntity(TileEntityShowerHead.class, "cfmShowerHead");
		GameRegistry.registerTileEntity(TileEntityPlate.class, "cfmPlate");
		GameRegistry.registerTileEntity(TileEntityCouch.class, "cfmCouch");
		GameRegistry.registerTileEntity(TileEntityToaster.class, "cfmToaster");
		GameRegistry.registerTileEntity(TileEntityChoppingBoard.class, "cfmChoppingBoard");
		GameRegistry.registerTileEntity(TileEntityBlender.class, "cfmBlender");
		GameRegistry.registerTileEntity(TileEntityMicrowave.class, "cfmMicrowave");
		GameRegistry.registerTileEntity(TileEntityWashingMachine.class, "cfmWashingMachine");
		GameRegistry.registerTileEntity(TileEntityDishwasher.class, "cfmDishwasher");
		GameRegistry.registerTileEntity(TileEntityCabinetKitchen.class, "cfmCabinetKitchen");
		GameRegistry.registerTileEntity(TileEntityCup.class, "cfmCup");
		GameRegistry.registerTileEntity(TileEntityCookieJar.class, "cfmCookieJar");
		GameRegistry.registerTileEntity(TileEntityTree.class, "cfmTree");
		GameRegistry.registerTileEntity(TileEntityMirror.class, "cfmMirror");
	}
}
