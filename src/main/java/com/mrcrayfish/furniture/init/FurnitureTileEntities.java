/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2016  MrCrayfish (http://www.mrcrayfish.com/)
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mrcrayfish.furniture.init;

import com.mrcrayfish.furniture.tileentity.*;
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
        GameRegistry.registerTileEntity(TileEntityGrill.class, "cfmGrill");
        GameRegistry.registerTileEntity(TileEntityEsky.class, "cfmEski");
        GameRegistry.registerTileEntity(TileEntityDoorMat.class, "cfmDoorMat");
        GameRegistry.registerTileEntity(TileEntityCrate.class, "cfmCrate");
        GameRegistry.registerTileEntity(TileEntityLightSwitch.class, "cfmLightSwitch");
        GameRegistry.registerTileEntity(TileEntityCeilingFan.class, "cfmCeilingFan");
        GameRegistry.registerTileEntity(TileEntityDeskCabinet.class, "cfmDeskCabinet");
        GameRegistry.registerTileEntity(TileEntityModernSlidingDoor.class, "cfmModernSlidingDoor");
    }
}
