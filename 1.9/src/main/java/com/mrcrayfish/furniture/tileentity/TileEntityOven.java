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

import java.util.Random;

import com.mrcrayfish.furniture.api.RecipeAPI;
import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.api.Recipes;
import com.mrcrayfish.furniture.gui.containers.ContainerOven;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityOven extends TileEntityLockable implements ISidedInventory, ITickable
{
	private static final int[] left_slots = new int[] { 0, 1, 2, 3 };
	private static final int[] right_slots = new int[] { 4, 5, 6, 7 };

	private ItemStack[] inventory = new ItemStack[8];

	public int cookTime = 0;
	public int cookingTime = 0;
	public int cookingItem = 0;
	
	public static final int COOK_TIME = 200;

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
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);

		this.inventory = new ItemStack[this.getSizeInventory()];
		
		if(tagCompound.hasKey("inventory"))
		{
			NBTTagList tagList = (NBTTagList) tagCompound.getTag("inventory");
			if (tagList != null)
			{
				for (int i = 0; i < tagList.tagCount(); ++i)
				{
					NBTTagCompound itemTag = (NBTTagCompound) tagList.getCompoundTagAt(i);
					byte slot = itemTag.getByte("slot");

					if (slot >= 0 && slot < this.inventory.length)
					{
						this.inventory[slot] = ItemStack.loadItemStackFromNBT(itemTag);
					}
				}
			}
		}

		this.cookTime = tagCompound.getShort("cookTime");
		this.cookingTime = tagCompound.getShort("cookingTime");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);

		tagCompound.setShort("cookTime", (short) cookTime);
		tagCompound.setShort("cookingTime", (short) cookingTime);

		NBTTagList tagList = new NBTTagList();
		for (int slot = 0; slot < this.inventory.length; ++slot)
		{
			if (this.inventory[slot] != null)
			{
				NBTTagCompound itemTag = new NBTTagCompound();
				itemTag.setByte("slot", (byte) slot);
				this.inventory[slot].writeToNBT(itemTag);
				tagList.appendTag(itemTag);
			}
		}

		tagCompound.setTag("inventory", tagList);
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
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1)
	{
		return this.cookingTime * par1 / COOK_TIME;
	}

	public boolean isBurning()
	{
		return this.cookTime > 0;
	}

	public boolean isCooking()
	{
		return this.cookingTime > 0;
	}

	public int getCookingItem()
	{
		return cookingItem;
	}

	private Random rand = new Random();

	@Override
	public void update()
	{
		boolean flag = this.cookingTime > 0;

		cookingItem = canCook();
		if (cookingItem != -1)
		{
			++this.cookingTime;
			
			if(!flag)
			{
				worldObj.updateComparatorOutputLevel(pos, blockType);
			}

			if (this.cookingTime == COOK_TIME)
			{
				this.cookingTime = 0;
				this.cookItems();
			}

			if (this.worldObj.isRemote)
			{
				double randX = rand.nextDouble();
				double randZ = rand.nextDouble();
				worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + randX, pos.getY() + 1.0D, pos.getZ() + randZ, 0.0D, 0.0D, 0.0D, new int[0]);
			}
		}
		else
		{
			this.cookingTime = 0;
		}

		if (flag && cookingTime == 0)
		{
			worldObj.updateComparatorOutputLevel(pos, blockType);
		}
	}

	public boolean canCook(int i)
	{
		if (i < 4)
		{
			if (inventory[i] != null)
			{
				return Recipes.getOvenRecipeFromInput(inventory[i]) != null;
			}
		}
		return false;
	}

	private int canCook()
	{
		boolean hasItem = false;
		int itemNum = -1;
		for (int x = 0; x < 4; x++)
		{
			if (this.inventory[x] != null)
			{
				hasItem = true;
				itemNum = x;
				break;
			}
		}

		if (hasItem)
		{
			RecipeData data = Recipes.getOvenRecipeFromInput(inventory[itemNum]);

			if (data == null)
			{
				return -1;
			}

			if (inventory[itemNum + 4] == null)
			{
				return itemNum;
			}

			if (inventory[itemNum + 4].getItem() != data.getOutput().getItem())
				return -1;

			if (inventory[itemNum + 4].stackSize < this.getInventoryStackLimit() && inventory[itemNum + 4].stackSize < inventory[itemNum + 4].getMaxStackSize())
			{
				return itemNum;
			}

			if (inventory[itemNum + 4].stackSize < data.getOutput().getMaxStackSize())
			{
				return itemNum;
			}
			else
			{
				return -1;
			}
		}
		return -1;
	}

	public void cookItems()
	{
		int itemNum = canCook();
		if (itemNum != -1)
		{
			RecipeData data = Recipes.getOvenRecipeFromInput(inventory[itemNum]);

			if (data == null)
			{
				return;
			}

			if (inventory[itemNum + 4] == null)
			{
				inventory[itemNum + 4] = data.getOutput().copy();
			}
			else if (inventory[itemNum + 4].getItem() == data.getOutput().getItem() && inventory[itemNum + 4].getItemDamage() == data.getOutput().getItemDamage())
			{
				inventory[itemNum + 4].stackSize += data.getOutput().copy().stackSize;
			}

			if (inventory[itemNum].getItem().hasContainerItem())
			{
				inventory[itemNum] = new ItemStack(inventory[itemNum].getItem().getContainerItem());
			}
			else
			{
				inventory[itemNum].stackSize--;
			}

			if (inventory[itemNum].stackSize <= 0)
			{
				inventory[itemNum] = null;
			}
		}
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return this.worldObj.getTileEntity(pos) != this ? false : entityplayer.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
	}

	public boolean isStackValidForSlot(int par1, ItemStack par2ItemStack)
	{
		return RecipeAPI.getOvenRecipeFromInput(par2ItemStack) != null;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return i > 8 ? false : true;
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

	}

	@Override
	public String getName()
	{
		return "Oven";
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

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return side == EnumFacing.DOWN ? right_slots : left_slots;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
	{
		return isStackValidForSlot(index, itemStackIn) && index < left_slots.length;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	{
		return index >= left_slots.length;
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) 
	{
		return new ContainerOven(playerInventory, this);
	}

	@Override
	public String getGuiID() 
	{
		return "0";
	}
}
