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
import com.mrcrayfish.furniture.gui.containers.ContainerOven;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class TileEntityOven extends TileEntityFurniture implements ISidedInventory, ITickable
{
	private Random rand = new Random();
	
	private static final int[] left_slots = new int[] { 0, 1, 2, 3 };
	private static final int[] right_slots = new int[] { 4, 5, 6, 7 };

	public int cookTime = 0;
	public int cookingTime = 0;
	public int cookingItem = 0;
	
	public static final int COOK_TIME = 200;
	
	public TileEntityOven() 
	{
		super("oven", 8);
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);
		this.cookTime = tagCompound.getShort("cookTime");
		this.cookingTime = tagCompound.getShort("cookingTime");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);
		tagCompound.setShort("cookTime", (short) cookTime);
		tagCompound.setShort("cookingTime", (short) cookingTime);
		return tagCompound;
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
				world.updateComparatorOutputLevel(pos, blockType);
			}

			if (this.cookingTime == COOK_TIME)
			{
				this.cookingTime = 0;
				this.cookItems();
			}

			if (this.world.isRemote)
			{
				double randX = rand.nextDouble();
				double randZ = rand.nextDouble();
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + randX, pos.getY() + 1.0D, pos.getZ() + randZ, 0.0D, 0.0D, 0.0D, new int[0]);
			}
		}
		else
		{
			this.cookingTime = 0;
		}

		if (flag && cookingTime == 0)
		{
			world.updateComparatorOutputLevel(pos, blockType);
		}
	}

	public boolean canCook(int i)
	{
		if (i < 4)
		{
			if (!getStackInSlot(i).isEmpty())
			{
				return Recipes.getOvenRecipeFromInput(getStackInSlot(i)) != null;
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
			if (!getStackInSlot(x).isEmpty())
			{
				hasItem = true;
				itemNum = x;
				break;
			}
		}

		if (hasItem)
		{
			RecipeData data = Recipes.getOvenRecipeFromInput(getStackInSlot(itemNum));

			if (data == null)
			{
				return -1;
			}

			if (getStackInSlot(itemNum + 4).isEmpty())
			{
				return itemNum;
			}

			if (getStackInSlot(itemNum + 4).getItem() != data.getOutput().getItem())
				return -1;

			if (getStackInSlot(itemNum + 4).getCount() < this.getInventoryStackLimit() && getStackInSlot(itemNum + 4).getCount() < getStackInSlot(itemNum + 4).getMaxStackSize())
			{
				return itemNum;
			}

			if (getStackInSlot(itemNum + 4).getCount() < data.getOutput().getMaxStackSize())
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
			RecipeData data = Recipes.getOvenRecipeFromInput(getStackInSlot(itemNum));

			if (data == null)
			{
				return;
			}

			if (getStackInSlot(itemNum + 4).isEmpty())
			{
				setInventorySlotContents(itemNum + 4, data.getOutput().copy());
			}
			else if (getStackInSlot(itemNum + 4).getItem() == data.getOutput().getItem() && getStackInSlot(itemNum + 4).getItemDamage() == data.getOutput().getItemDamage())
			{
				getStackInSlot(itemNum + 4).grow(data.getOutput().copy().getCount());
			}

			if (getStackInSlot(itemNum).getItem().hasContainerItem())
			{
				setInventorySlotContents(itemNum, new ItemStack(getStackInSlot(itemNum).getItem().getContainerItem()));
			}
			else
			{
				getStackInSlot(itemNum).shrink(1);
			}

			if (getStackInSlot(itemNum).getCount() <= 0)
			{
				removeStackFromSlot(itemNum);
			}
		}
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
}
