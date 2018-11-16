package com.mrcrayfish.furniture.gui;

import com.mrcrayfish.furniture.blocks.BlockBedsideCabinet;
import com.mrcrayfish.furniture.blocks.BlockCabinet;
import com.mrcrayfish.furniture.gui.containers.ContainerEnvelope;
import com.mrcrayfish.furniture.gui.containers.ContainerPackage;
import com.mrcrayfish.furniture.gui.containers.ContainerPresent;
import com.mrcrayfish.furniture.gui.inventory.InventoryEnvelope;
import com.mrcrayfish.furniture.gui.inventory.InventoryPackage;
import com.mrcrayfish.furniture.gui.inventory.InventoryPresent;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.tileentity.*;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        if(tileEntity instanceof TileEntityFurniture)
        {
            return ((TileEntityFurniture) tileEntity).createContainer(player.inventory, player);
        }
        else if(tileEntity instanceof TileEntityDeskCabinet)
        {
            return ((TileEntityDeskCabinet) tileEntity).createContainer(player.inventory, player);
        }
        else if(tileEntity instanceof TileEntityTVStand)
        {
            return ((TileEntityTVStand) tileEntity).createContainer(player.inventory, player);
        }
        else if(tileEntity instanceof TileEntityKitchenCounterDrawer)
        {
            return ((TileEntityKitchenCounterDrawer) tileEntity).createContainer(player.inventory, player);
        }

        ItemStack heldItem = player.getHeldItemMainhand();
        if(id == 5 && heldItem.getItem() == FurnitureItems.ENVELOPE)
        {
            return new ContainerEnvelope(player.inventory, new InventoryEnvelope(heldItem));
        }
        else if(id == 6 && heldItem.getItem() == FurnitureItems.ENVELOPE_SIGNED)
        {
            return new ContainerEnvelope(player.inventory, new InventoryEnvelope(heldItem).setCanInsertItems(false));
        }
        else if(id == 7 && heldItem.getItem() == FurnitureItems.PACKAGE)
        {
            return new ContainerPackage(player.inventory, new InventoryPackage(heldItem));
        }
        else if(id == 8 && heldItem.getItem() == FurnitureItems.PACKAGE_SIGNED)
        {
            return new ContainerPackage(player.inventory, new InventoryPackage(heldItem).setCanInsertItems(false));
        }
        else if(id == 9 && heldItem.getItem() == Item.getItemFromBlock(FurnitureBlocks.PRESENT))
        {
            return new ContainerPresent(player.inventory, new InventoryPresent(heldItem));
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        if(tileEntity instanceof IValueContainer && id == 1)
        {
            return new GuiEditValueContainer((IValueContainer) tileEntity);
        }
        if(tileEntity instanceof TileEntityFridge)
        {
            return new GuiFridge(player.inventory, (TileEntityFridge) tileEntity);
        }
        if(tileEntity instanceof TileEntityFreezer)
        {
            return new GuiFreezer(player.inventory, (TileEntityFreezer) tileEntity);
        }
        if(tileEntity instanceof TileEntityCabinet)
        {
            return new GuiCabinet(player.inventory, (TileEntityCabinet) tileEntity, (BlockCabinet) tileEntity.getBlockType());
        }
        if(tileEntity instanceof TileEntityBedsideCabinet)
        {
            return new GuiBedsideCabinet(player.inventory, (TileEntityBedsideCabinet) tileEntity, (BlockBedsideCabinet) tileEntity.getBlockType());
        }
        if(tileEntity instanceof TileEntityOven)
        {
            return new GuiOven(player.inventory, (TileEntityOven) tileEntity);
        }
        if(tileEntity instanceof TileEntityMailBox && id == 0)
        {
            return new GuiMailBox(player.inventory, (TileEntityMailBox) tileEntity);
        }
        if(tileEntity instanceof TileEntityComputer)
        {
            return new GuiComputer(player.inventory, (TileEntityComputer) tileEntity);
        }
        if(tileEntity instanceof TileEntityPrinter)
        {
            return new GuiPrinter(player.inventory, (TileEntityPrinter) tileEntity);
        }
        if(tileEntity instanceof TileEntityBin)
        {
            return new GuiBin(player.inventory, (TileEntityBin) tileEntity, x, y, z);
        }
        if(tileEntity instanceof TileEntityWallCabinet)
        {
            return new GuiWallCabinet(player.inventory, (TileEntityWallCabinet) tileEntity);
        }
        if(tileEntity instanceof TileEntityMicrowave)
        {
            return new GuiMicrowave(player.inventory, (TileEntityMicrowave) tileEntity);
        }
        if(tileEntity instanceof TileEntityWashingMachine)
        {
            return new GuiWashingMachine(player.inventory, (TileEntityWashingMachine) tileEntity);
        }
        if(tileEntity instanceof TileEntityDishwasher)
        {
            return new GuiDishwasher(player.inventory, (TileEntityDishwasher) tileEntity);
        }
        if(tileEntity instanceof TileEntityCabinetKitchen)
        {
            return new GuiKitchenCabinet(player.inventory, (TileEntityCabinetKitchen) tileEntity);
        }
        if(tileEntity instanceof TileEntityEsky)
        {
            return new GuiEski(player.inventory, (TileEntityEsky) tileEntity);
        }
        if(tileEntity instanceof TileEntityDoorMat)
        {
            return new GuiDoorMat(x, y, z);
        }
        if(tileEntity instanceof TileEntityCrate)
        {
            return new GuiCrate(player.inventory, (TileEntityCrate) tileEntity, x, y, z);
        }
        if(tileEntity instanceof TileEntityDeskCabinet)
        {
            return new GuiChest(player.inventory, (TileEntityDeskCabinet) tileEntity);
        }
        if(tileEntity instanceof TileEntityTVStand)
        {
            return new GuiChest(player.inventory, (TileEntityTVStand) tileEntity);
        }
        if(tileEntity instanceof TileEntityKitchenCounterDrawer)
        {
            return new GuiChest(player.inventory, (TileEntityKitchenCounterDrawer) tileEntity);
        }
        ItemStack heldItem = player.getHeldItemMainhand();
        if(id == 5 && heldItem.getItem() == FurnitureItems.ENVELOPE)
        {
            return new GuiEnvelope(player.inventory, new InventoryEnvelope(heldItem), player);
        }
        if(id == 6 && heldItem.getItem() == FurnitureItems.ENVELOPE_SIGNED)
        {
            return new GuiEnvelope(player.inventory, new InventoryEnvelope(heldItem).setCanInsertItems(false), player);
        }
        if(id == 7 && heldItem.getItem() == FurnitureItems.PACKAGE)
        {
            return new GuiPackage(player.inventory, new InventoryPackage(heldItem), player);
        }
        if(id == 8 && heldItem.getItem() == FurnitureItems.PACKAGE_SIGNED)
        {
            return new GuiPackage(player.inventory, new InventoryPackage(heldItem).setCanInsertItems(false), player);
        }
        if(id == 9 && heldItem.getItem() == Item.getItemFromBlock(FurnitureBlocks.PRESENT))
        {
            return new GuiPresent(player.inventory, new InventoryPresent(heldItem), player);
        }
        if(id == 10)
        {
            return new GuiRecipeBook();
        }
        return null;
    }
}