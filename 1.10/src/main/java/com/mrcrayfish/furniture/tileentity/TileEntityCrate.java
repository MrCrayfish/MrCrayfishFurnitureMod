package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.util.TileEntityUtil;

import net.minecraft.entity.player.EntityPlayer;
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

public class TileEntityCrate extends TileEntity implements IInventory
{
	public ItemStack[] inventory = new ItemStack[16];
	
	public boolean sealed = false;
	
	public void seal()
	{
		if(!sealed)
		{
			sealed = true;
			TileEntityUtil.markBlockForUpdate(worldObj, pos);
		}
	}

	@Override
	public int getSizeInventory()
	{
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int count)
	{
		if (this.inventory[slot] != null)
		{
			ItemStack var3;

			if (this.inventory[slot].stackSize <= count)
			{
				var3 = this.inventory[slot];
				this.inventory[slot] = null;
				this.markDirty();
				return var3;
			}
			var3 = this.inventory[slot].splitStack(count);

			if (this.inventory[slot].stackSize == 0)
			{
				this.inventory[slot] = null;
			}

			this.markDirty();
			return var3;
		}
		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int slot)
	{
		if (this.inventory[slot] != null)
		{
			ItemStack itemstack = this.inventory[slot];
			this.inventory[slot] = null;
			return itemstack;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		this.inventory[slot] = stack;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit())
		{
			stack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return this.worldObj.getTileEntity(pos) != this ? false : player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return false;
	}

	@Override
	public int getField(int id)
	{
		return 0;
	}

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public void clear()
	{
		for(int i = 0; i < inventory.length; i++)
		{
			inventory[i] = null;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) 
	{
		super.readFromNBT(compound);
		if (compound.hasKey("Items"))
		{
			NBTTagList tagList = (NBTTagList) compound.getTag("Items");
			this.inventory = new ItemStack[16];

			for (int i = 0; i < tagList.tagCount(); ++i)
			{
				NBTTagCompound nbt = (NBTTagCompound) tagList.getCompoundTagAt(i);
				byte slot = nbt.getByte("Slot");

				if (slot >= 0 && slot < this.inventory.length)
				{
					this.inventory[slot] = ItemStack.loadItemStackFromNBT(nbt);
				}
			}
		}
		
		this.sealed = compound.getBoolean("Sealed");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) 
	{
		super.writeToNBT(tagCompound);
		NBTTagList tagList = new NBTTagList();
		for (int slot = 0; slot < this.inventory.length; ++slot)
		{
			if (this.inventory[slot] != null)
			{
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setByte("Slot", (byte) slot);
				this.inventory[slot].writeToNBT(nbt);
				tagList.appendTag(nbt);
			}
		}
		tagCompound.setTag("Items", tagList);
		tagCompound.setBoolean("Sealed", this.sealed);
		return tagCompound;
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
		NBTTagCompound tagCom = pkt.getNbtCompound();
		this.readFromNBT(tagCom);
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		NBTTagCompound tagCom = new NBTTagCompound();
		this.writeToNBT(tagCom);
		return new SPacketUpdateTileEntity(pos, getBlockMetadata(), tagCom);
	}
	
	@Override
	public NBTTagCompound getUpdateTag() 
	{
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public String getName()
	{
		return "Crate";
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	@Override
	public ITextComponent getDisplayName() 
	{
		return new TextComponentString(getName());
	}
}
