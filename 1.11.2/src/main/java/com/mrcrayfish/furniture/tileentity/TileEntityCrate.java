package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.gui.containers.ContainerCrate;
import com.mrcrayfish.furniture.util.TileEntityUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class TileEntityCrate extends TileEntityFurniture
{
	public TileEntityCrate() 
	{
		super("crate", 16);
	}

	public boolean sealed = false;
	
	public void seal()
	{
		if(!sealed)
		{
			sealed = true;
			TileEntityUtil.markBlockForUpdate(world, pos);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) 
	{
		super.readFromNBT(tagCompound);
		this.sealed = tagCompound.getBoolean("Sealed");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) 
	{
		super.writeToNBT(tagCompound);
		tagCompound.setBoolean("Sealed", this.sealed);
		return tagCompound;
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) 
	{
		return new ContainerCrate(playerInventory, this);
	}
}
