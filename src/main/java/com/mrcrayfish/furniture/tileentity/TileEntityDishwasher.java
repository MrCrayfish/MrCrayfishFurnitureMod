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
import com.mrcrayfish.furniture.gui.containers.ContainerDishwasher;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;

import java.util.Random;

public class TileEntityDishwasher extends TileEntityFurniture implements ISidedInventory, ITickable
{
	private Random rand = new Random();
	
	private static final int[] slots_top = new int[] { 0, 1, 2, 3, 4, 5 };
	private static final int[] slots_bottom = new int[] { 0, 1, 2, 3, 4, 5, 6 };
	private static final int[] slots_sides = new int[] { 6 };
	
	private boolean washing = false;
	public boolean superMode = false;
	public int progress = 0;
	public int timeRemaining = 0;
	private int timer = 0;
	
	public TileEntityDishwasher() 
	{
		super("diswasher", 7);
	}

	public void startWashing()
	{
		if (canWash())
		{
			if (timeRemaining == 0)
			{
				if (inventory.get(6).getItem() == FurnitureItems.itemSuperSoapyWater)
				{
					superMode = true;
				}
				else
				{
					superMode = false;
				}
				inventory.set(6, new ItemStack(inventory.get(6).getItem().getContainerItem()));
				timeRemaining = 5000;
			}
			washing = true;
			world.updateComparatorOutputLevel(pos, blockType);
		}
	}

	public void stopWashing()
	{
		progress = 0;
		washing = false;
		world.updateComparatorOutputLevel(pos, blockType);
	}

	public boolean canWash()
	{
		if (inventory.get(6).isEmpty() && timeRemaining == 0)
		{
			return false;
		}

		if (!inventory.get(6).isEmpty() && timeRemaining == 0)
		{
			return isFuel(inventory.get(6));
		}

		for (int i = 0; i < 6; i++)
		{
			if(!inventory.get(i).isEmpty())
			{
				RecipeData data = RecipeAPI.getDishwasherRecipeFromInput(inventory.get(i));
				if(data == null)
				{
					return false;
				}
			}
		}
		return true;
	}

	public boolean isWashing()
	{
		return washing;
	}
	
	public static boolean isFuel(ItemStack stack)
	{
		if(stack == null)
			return false;
		if(stack.getItem() == FurnitureItems.itemSoapyWater)
			return true;
		return stack.getItem() == FurnitureItems.itemSuperSoapyWater;
	}

	@Override
	public void update()
	{
		if (washing)
		{
			if(!canWash())
			{
				washing = false;
				world.updateComparatorOutputLevel(pos, blockType);
				return;
			}
			
			if (canRepair())
			{
				for (int i = 0; i < 6; i++)
				{
					if (inventory.get(i) != ItemStack.EMPTY)
					{
						if (inventory.get(i).getMaxDamage() - inventory.get(i).getItemDamage() != inventory.get(i).getMaxDamage())
						{
							inventory.get(i).setItemDamage(inventory.get(i).getItemDamage() - 1);
						}
					}
				}
			}

			timeRemaining--;
			if (timeRemaining <= 0)
			{
				if (inventory.get(4) != ItemStack.EMPTY)
				{
					if (inventory.get(4).getItem() == FurnitureItems.itemSoapyWater)
					{
						this.superMode = false;
						inventory.set(4, new ItemStack(FurnitureItems.itemSoapyWater.getContainerItem()));
						timeRemaining = 5000;
					}
					else if (inventory.get(4).getItem() == FurnitureItems.itemSuperSoapyWater)
					{
						this.superMode = true;
						inventory.set(4, new ItemStack(FurnitureItems.itemSuperSoapyWater.getContainerItem()));
						timeRemaining = 5000;
					}
				}
				else
				{
					timeRemaining = 0;
					progress = 0;
					washing = false;
					world.updateComparatorOutputLevel(pos, blockType);
				}
			}
			
			progress++;

			if (timer == 19)
			{
				timer = 0;
			}
			if (timer == 0)
			{
				world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), FurnitureSounds.dishwasher, SoundCategory.BLOCKS, 0.75F, 1.0F);
			}
			timer++;
		}
	}

	public boolean canRepair()
	{
		return progress % (superMode ? 20 : 50) == 0;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);
		this.washing = tagCompound.getBoolean("Washing");
		this.superMode = tagCompound.getBoolean("SuperMode");
		this.progress = tagCompound.getInteger("Progress");
		this.timeRemaining = tagCompound.getInteger("Remaining");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);
		tagCompound.setBoolean("Washing", washing);
		tagCompound.setBoolean("SuperMode", superMode);
		tagCompound.setInteger("Progress", progress);
		tagCompound.setInteger("Remaining", timeRemaining);
		return tagCompound;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		int target = ContainerDishwasher.toolToSlot(stack);
		if(target != -1)
		{
			return slot == target;
		}
		return true;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) 
	{
		if(side == EnumFacing.UP) return slots_top;
		if(side == EnumFacing.DOWN) return slots_bottom;
		return slots_sides;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack stack, EnumFacing side) 
	{
		if(isLocked())
		{
			return false;
		}
		if(side == EnumFacing.UP)
		{
			return RecipeAPI.getDishwasherRecipeFromInput(stack) != null;
		}
		if(side != EnumFacing.DOWN)
		{
			return isFuel(stack);
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing side) 
	{
		return side == EnumFacing.DOWN && !isFuel(stack) && stack.getItemDamage() == 0 && !isLocked();
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) 
	{
		this.fillWithLoot(playerIn);
		return new ContainerDishwasher(playerInventory, this);
	}
}
