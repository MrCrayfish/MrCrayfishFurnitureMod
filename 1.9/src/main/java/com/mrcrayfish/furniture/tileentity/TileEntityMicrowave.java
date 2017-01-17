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
import com.mrcrayfish.furniture.gui.containers.ContainerMicrowave;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import com.mrcrayfish.furniture.util.ParticleSpawner;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class TileEntityMicrowave extends TileEntityLockable implements ISidedInventory, ITickable
{
	private static final int[] slot = new int[] { 0 };
	
	private ItemStack item = null;
	private String customName;
	private boolean cooking = false;
	public int progress = 0;

	public ItemStack getItem()
	{
		return item;
	}

	public void startCooking()
	{
		if (item != null)
		{
			RecipeData data = RecipeAPI.getMicrowaveRecipeFromIngredients(item);
			if (data != null)
			{
				cooking = true;
				worldObj.updateComparatorOutputLevel(pos, blockType);
			}
		}
	}

	public void stopCooking()
	{
		this.cooking = false;
		this.progress = 0;
		worldObj.updateComparatorOutputLevel(pos, blockType);
	}

	public boolean isCooking()
	{
		return cooking;
	}

	private Random rand = new Random();
	private int timer = 0;

	@Override
	public void update()
	{
		if (cooking)
		{
			if (this.worldObj.isRemote)
			{
				double posX = pos.getX() + 0.35D + (rand.nextDouble() / 3);
				double posZ = pos.getZ() + 0.35D + (rand.nextDouble() / 3);
				ParticleSpawner.spawnParticle("smoke", posX, pos.getY() + 0.065D, posZ);
			}

			progress++;
			if (progress >= 40)
			{
				if (item != null)
				{
					RecipeData data = RecipeAPI.getMicrowaveRecipeFromIngredients(item);
					if (data != null)
					{
						this.item = data.getOutput().copy();
					}
				}
				if (!worldObj.isRemote)
				{
					worldObj.playSound(pos.getX(), pos.getY(), pos.getZ(), FurnitureSounds.microwave_finish, SoundCategory.BLOCKS, 0.75F, 1.0F, true);
				}
				timer = 0;
				progress = 0;
				cooking = false;
				worldObj.updateComparatorOutputLevel(pos, blockType);
			}
			else
			{
				if (timer == 20)
				{
					timer = 0;
				}
				if (timer == 0)
				{
					worldObj.playSound(pos.getX(), pos.getY(), pos.getZ(), FurnitureSounds.microwave_running, SoundCategory.BLOCKS, 0.75F, 1.0F, true);
				}
				timer++;
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);
		if (tagCompound.hasKey("Item"))
		{
			NBTTagCompound nbt = tagCompound.getCompoundTag("Item");
			this.item = ItemStack.loadItemStackFromNBT(nbt);
		}
		this.cooking = tagCompound.getBoolean("Coooking");
		this.progress = tagCompound.getInteger("Progress");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);
		NBTTagCompound nbt = new NBTTagCompound();
		if (item != null)
		{
			item.writeToNBT(nbt);
		}
		tagCompound.setTag("Item", nbt);
		tagCompound.setBoolean("Coooking", cooking);
		tagCompound.setInteger("Progress", progress);
		return tagCompound;
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
		this.readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() 
	{
		return new SPacketUpdateTileEntity(pos, getBlockMetadata(), this.writeToNBT(new NBTTagCompound()));
	}
	
	@Override
	public NBTTagCompound getUpdateTag() 
	{
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public int getSizeInventory()
	{
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return this.item;
	}

	@Override
	public ItemStack decrStackSize(int slot, int number)
	{
		if (this.item != null)
		{
			ItemStack itemstack;

			if (this.item.stackSize <= number)
			{
				itemstack = this.item;
				this.item = null;
				return itemstack;
			}
			itemstack = this.item.splitStack(number);

			if (this.item.stackSize == 0)
			{
				this.item = null;
			}

			return itemstack;
		}
		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int slot)
	{
		return item;
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		this.item = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return this.worldObj.getTileEntity(pos) != this ? false : entityplayer.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
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
		item = null;
	}

	@Override
	public String getName()
	{
		return hasCustomName() ? customName : "Microwave";
	}

	@Override
	public boolean hasCustomName()
	{
		return customName != null;
	}

	@Override
	public ITextComponent getDisplayName() 
	{
		return new TextComponentString(getName());
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return RecipeAPI.getMicrowaveRecipeFromIngredients(stack) != null;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) 
	{
		return slot;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction) 
	{
		return RecipeAPI.getMicrowaveRecipeFromIngredients(stack) != null;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) 
	{
		return RecipeAPI.getMicrowaveRecipeFromIngredients(stack) == null;
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) 
	{
		return new ContainerMicrowave(playerInventory, this);
	}

	@Override
	public String getGuiID() 
	{
		return "0";
	}
}
