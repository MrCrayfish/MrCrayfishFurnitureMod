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
        GameRegistry.registerTileEntity(TileEntityDigitalClock.class, "cfmDigitalClock");
    }
}
