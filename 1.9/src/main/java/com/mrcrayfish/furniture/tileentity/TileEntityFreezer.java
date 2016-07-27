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
package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.api.RecipeAPI;
import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.api.Recipes;
import com.mrcrayfish.furniture.gui.containers.ContainerFreezer;
import com.mrcrayfish.furniture.init.FurnitureItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class TileEntityFreezer extends TileEntityLockable implements ISidedInventory, ITickable
{
	private static final int[] slots_bottom = new int[] { 2 };
	private static final int[] slots_sides = new int[] { 0, 1 };

	private ItemStack[] inventory = new ItemStack[3];

	private boolean freezing = false;
	public int progress = 0;
	public int timeRemaining = 0;
	public int fuelTime = 0;
	
	public void startFreezing()
	{
		if(canFreeze())
		{
			if(timeRemaining == 0)
			{
				fuelTime = getFuelTime(inventory[0]);
				timeRemaining = fuelTime;
				
				inventory[0].stackSize--;
				if(inventory[0].stackSize <= 0)
				{
					inventory[0] = null;
				}
			}
			freezing = true;
			worldObj.updateComparatorOutputLevel(pos, blockType);
		}
	}
	
	public void stopFreezing()
	{
		freezing = false;
		worldObj.updateComparatorOutputLevel(pos, blockType);
	}
	
	public boolean canFreeze()
	{
		if (inventory[0] == null && timeRemaining == 0)
		{
			return false;
		}
		
		if (inventory[0] != null && timeRemaining == 0)
		{
			if(!isFuel(inventory[0]))
			{
				return false;
			}
		}
		
		if (inventory[1] != null)
		{
			RecipeData data = Recipes.getFreezerRecipeFromInput(inventory[1]);

			if (data == null)
			{
				return false;
			}

			if (inventory[2] == null)
			{
				return true;
			}

			if (inventory[2].getItem() != data.getOutput().getItem())
			{
				return false;
			}

			if (inventory[2].stackSize < this.getInventoryStackLimit() && inventory[2].stackSize < inventory[2].getMaxStackSize())
			{
				return true;
			}
		}
		return false;
	}
	
	public void freezeItem()
	{
		if (inventory[1] != null)
		{
			RecipeData data = Recipes.getFreezerRecipeFromInput(inventory[1]);

			if (data == null)
			{
				return;
			}

			if (inventory[2] == null)
			{
				inventory[2] = data.getOutput().copy();
			}
			else if (inventory[2].getItem() == data.getOutput().getItem() && inventory[2].getItemDamage() == data.getOutput().getItemDamage())
			{
				inventory[2].stackSize += data.getOutput().copy().stackSize;
			}

			if (inventory[1].getItem().hasContainerItem())
			{
				inventory[1] = new ItemStack(inventory[1].getItem().getContainerItem());
			}
			else
			{
				inventory[1].stackSize--;
			}

			if (inventory[1].stackSize <= 0)
			{
				inventory[1] = null;
			}
		}
	}
	
	public boolean isFreezing()
	{
		return freezing;
	}
	
	public static boolean isFuel(ItemStack stack)
	{
		return getFuelTime(stack) > 0;
	}
	
	private static int getFuelTime(ItemStack stack)
	{
		if(stack == null)
			return 0;
		if(stack.getItem() == Item.getItemFromBlock(Blocks.packed_ice))
			return 3000;
		if(stack.getItem() == Item.getItemFromBlock(Blocks.ice))
			return 2000;
		if(stack.getItem() == FurnitureItems.itemCoolPack)
			return 400;
		return 0;
	}
	
	@Override
	public void update()
	{
		if(freezing)
		{
			if(!canFreeze())
			{
				freezing = false;
				worldObj.updateComparatorOutputLevel(pos, blockType);
				return;
			}

			progress++;
			if(progress >= 200)
			{
				freezeItem();
				progress = 0;
			}
			
			timeRemaining--;
			if (timeRemaining <= 0)
			{
				if(inventory[0] != null && isFuel(inventory[0]))
				{
					fuelTime = getFuelTime(inventory[0]);
					timeRemaining = fuelTime;
					
					inventory[0].stackSize--;
					if(inventory[0].stackSize <= 0)
					{
						inventory[0] = null;
					}
				}
				else
				{
					timeRemaining = 0;
					freezing = false;
					worldObj.updateComparatorOutputLevel(pos, blockType);
				}
			}
		}
		else
		{
			if(progress > 0)
			{
				progress--;
			}
		}
	}

	@Override
	public int getSizeInventory()
	{
		return this.inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int par1)
	{
		return this.inventory[par1];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2)
	{
		if (this.inventory[par1] != null)
		{
			ItemStack itemstack;

			if (this.inventory[par1].stackSize <= par2)
			{
				itemstack = this.inventory[par1];
				this.inventory[par1] = null;
				return itemstack;
			}
			itemstack = this.inventory[par1].splitStack(par2);

			if (this.inventory[par1].stackSize == 0)
			{
				this.inventory[par1] = null;
			}

			return itemstack;
		}
		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int par1)
	{
		if (this.inventory[par1] != null)
		{
			ItemStack itemstack = this.inventory[par1];
			this.inventory[par1] = null;
			return itemstack;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		this.inventory[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
	}
	

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return this.worldObj.getTileEntity(this.pos) != this ? false : entityplayer.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);
		
		NBTTagList tagList = (NBTTagList) tagCompound.getTag("Items");
		this.inventory = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < tagList.tagCount(); ++i)
		{
			NBTTagCompound nbt = (NBTTagCompound) tagList.getCompoundTagAt(i);
			byte slot = nbt.getByte("Slot");

			if (slot >= 0 && slot < this.inventory.length)
			{
				this.inventory[slot] = ItemStack.loadItemStackFromNBT(nbt);
			}
		}

		this.freezing = tagCompound.getBoolean("Freezing");
		this.progress = tagCompound.getInteger("Progress");
		this.fuelTime = tagCompound.getInteger("FuelTime");
		this.timeRemaining = tagCompound.getInteger("Remaining");
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound)
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
		tagCompound.setBoolean("Freezing", freezing);
		tagCompound.setInteger("Progress", progress);
		tagCompound.setInteger("FuelTime", fuelTime);
		tagCompound.setInteger("Remaining", timeRemaining);
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

	@Override
	public void openInventory(EntityPlayer player)
	{
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
	}

	@Override
	public int getField(int id)
	{
		return 0;
	}

	@Override
	public void setField(int id, int value)
	{
	}

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public void clear()
	{
		for (int i = 0; i < inventory.length; i++)
		{
			inventory[i] = null;
		}
	}

	public String getName()
	{
		return "Freezer";
	}

	public boolean hasCustomName()
	{
		return false;
	}

	@Override
	public ITextComponent getDisplayName() 
	{
		return new TextComponentString(getName());
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		return true;
	}
	
	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		if(side == EnumFacing.DOWN) return slots_bottom;
		return slots_sides;
	}

	@Override
	public boolean canInsertItem(int slotIn, ItemStack stack, EnumFacing side)
	{
		if(isLocked())
			return false;

		if(side != EnumFacing.DOWN)
		{
			if(slotIn == 0) return isFuel(stack);
			if(slotIn == 1) return RecipeAPI.getFreezerRecipeFromInput(stack) != null;
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int slotId, ItemStack stack, EnumFacing side)
	{
		return side == EnumFacing.DOWN && !isLocked();
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) 
	{
		return new ContainerFreezer(playerInventory, this);
	}

	@Override
	public String getGuiID() 
	{
		return "0";
	}
}
