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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileEntityFreezer extends TileEntityFurniture implements ISidedInventory, ITickable
{
	private static final int[] slots_bottom = new int[] { 2 };
	private static final int[] slots_sides = new int[] { 0, 1 };

	private boolean freezing = false;
	public int progress = 0;
	public int timeRemaining = 0;
	public int fuelTime = 0;
	
	public TileEntityFreezer() 
	{
		super("freezer", 3);
	}

	public void startFreezing()
	{
		if(canFreeze())
		{
			if(timeRemaining == 0)
			{
				fuelTime = getFuelTime(inventory.get(0));
				timeRemaining = fuelTime;
				
				inventory.get(0).shrink(1);
				if(inventory.get(0).getCount() <= 0)
				{
					removeStackFromSlot(0);
				}
			}
			freezing = true;
			world.updateComparatorOutputLevel(pos, blockType);
		}
	}
	
	public void stopFreezing()
	{
		freezing = false;
		world.updateComparatorOutputLevel(pos, blockType);
	}
	
	public boolean canFreeze()
	{
		if (inventory.get(0) == ItemStack.EMPTY && timeRemaining == 0)
		{
			return false;
		}
		
		if (inventory.get(0) != ItemStack.EMPTY && timeRemaining == 0)
		{
			if(!isFuel(inventory.get(0)))
			{
				return false;
			}
		}
		
		if (inventory.get(1) != ItemStack.EMPTY)
		{
			RecipeData data = Recipes.getFreezerRecipeFromInput(inventory.get(1));

			if (data == null)
			{
				return false;
			}

			if (inventory.get(2) == ItemStack.EMPTY)
			{
				return true;
			}

			if (inventory.get(2).getItem() != data.getOutput().getItem())
			{
				return false;
			}

			if (inventory.get(2).getCount() < this.getInventoryStackLimit() && inventory.get(2).getCount() < inventory.get(2).getMaxStackSize())
			{
				return true;
			}
		}
		return false;
	}
	
	public void freezeItem()
	{
		if (inventory.get(1) != ItemStack.EMPTY)
		{
			RecipeData data = Recipes.getFreezerRecipeFromInput(inventory.get(1));

			if (data == null)
			{
				return;
			}

			if (inventory.get(2) == ItemStack.EMPTY)
			{
				inventory.set(2, data.getOutput().copy());
			}
			else if (inventory.get(2).getItem() == data.getOutput().getItem() && inventory.get(2).getItemDamage() == data.getOutput().getItemDamage())
			{
				inventory.get(2).grow(data.getOutput().copy().getCount());
			}

			if (inventory.get(1).getItem().hasContainerItem())
			{
				inventory.set(1, new ItemStack(inventory.get(1).getItem().getContainerItem()));
			}
			else
			{
				inventory.get(1).shrink(1);
			}

			if (inventory.get(1).getCount() <= 0)
			{
				removeStackFromSlot(1);
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
		if(stack.getItem() == Item.getItemFromBlock(Blocks.PACKED_ICE))
			return 3000;
		if(stack.getItem() == Item.getItemFromBlock(Blocks.ICE))
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
				world.updateComparatorOutputLevel(pos, blockType);
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
				if(inventory.get(0) != ItemStack.EMPTY && isFuel(inventory.get(0)))
				{
					fuelTime = getFuelTime(inventory.get(0));
					timeRemaining = fuelTime;
					
					inventory.get(0).shrink(1);
					if(inventory.get(0).getCount() <= 0)
					{
						removeStackFromSlot(0);
					}
				}
				else
				{
					timeRemaining = 0;
					freezing = false;
					world.updateComparatorOutputLevel(pos, blockType);
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
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);
		this.freezing = tagCompound.getBoolean("Freezing");
		this.progress = tagCompound.getInteger("Progress");
		this.fuelTime = tagCompound.getInteger("FuelTime");
		this.timeRemaining = tagCompound.getInteger("Remaining");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);
		tagCompound.setBoolean("Freezing", freezing);
		tagCompound.setInteger("Progress", progress);
		tagCompound.setInteger("FuelTime", fuelTime);
		tagCompound.setInteger("Remaining", timeRemaining);
		return tagCompound;
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
		this.fillWithLoot(playerIn);
		return new ContainerFreezer(playerInventory, this);
	}
}
