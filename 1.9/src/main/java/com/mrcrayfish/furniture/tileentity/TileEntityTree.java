package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.gui.inventory.ISimpleInventory;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileEntityTree extends TileEntity implements ITickable, ISimpleInventory
{
	private ItemStack[] ornaments = new ItemStack[4];

	@Override
	public int getSize()
	{
		return ornaments.length;
	}

	@Override
	public ItemStack getItem(int i)
	{
		return ornaments[i];
	}

	@Override
	public void clear()
	{
		for (int i = 0; i < ornaments.length; i++)
		{
			ornaments[i] = null;
		}
	}

	@Override
	public void update()
	{
	}

	public void addOrnament(EnumFacing facing, ItemStack item)
	{
		ItemStack temp = ornaments[facing.getHorizontalIndex()];
		if (temp != null)
		{
			if (!worldObj.isRemote)
			{
				EntityItem entityItem = new EntityItem(worldObj, pos.getX() + 0.5, pos.getY() + 1.0D, pos.getZ() + 0.5, temp);
				worldObj.spawnEntityInWorld(entityItem);
			}
			ornaments[facing.getHorizontalIndex()] = null;
		}
		if (item != null)
		{
			ornaments[facing.getHorizontalIndex()] = item.copy().splitStack(1);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList var2 = (NBTTagList) par1NBTTagCompound.getTag("Items");
		this.ornaments = new ItemStack[this.getSize()];

		for (int var3 = 0; var3 < var2.tagCount(); ++var3)
		{
			NBTTagCompound var4 = (NBTTagCompound) var2.getCompoundTagAt(var3);
			int var5 = var4.getByte("Slot") & 255;

			if (var5 >= 0 && var5 < this.ornaments.length)
			{
				this.ornaments[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		NBTTagList var2 = new NBTTagList();

		for (int var3 = 0; var3 < this.ornaments.length; ++var3)
		{
			if (this.ornaments[var3] != null)
			{
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte) var3);
				this.ornaments[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}

		par1NBTTagCompound.setTag("Items", var2);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
		NBTTagCompound tagCom = pkt.getNbtCompound();
		this.readFromNBT(tagCom);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound tagCom = new NBTTagCompound();
		this.writeToNBT(tagCom);
		return new SPacketUpdateTileEntity(pos, getBlockMetadata(), tagCom);
	}
}
