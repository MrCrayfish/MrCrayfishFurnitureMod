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
import com.mrcrayfish.furniture.gui.inventory.ISimpleInventory;
import com.mrcrayfish.furniture.init.FurnitureItems;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBlender extends TileEntity implements IUpdatePlayerListBox, ISimpleInventory
{
	public ItemStack[] ingredients = new ItemStack[4];

	private boolean blending = false;
	public int progress = 0;
	public int drinkCount = 0;

	public String drinkName = "";
	public int healAmount;
	public int currentRed;
	public int currentGreen;
	public int currentBlue;

	public void addIngredient(ItemStack ingredient)
	{
		for (int i = 0; i < ingredients.length; i++)
		{
			if (ingredients[i] == null)
			{
				ingredients[i] = ingredient.copy();
				break;
			}
		}
	}

	public void removeIngredient()
	{
		for (int i = ingredients.length - 1; i >= 0; i--)
		{
			if (ingredients[i] != null)
			{
				if (!worldObj.isRemote)
				{
					EntityItem entityItem = new EntityItem(worldObj, pos.getX() + 0.5, pos.getY() + 1.0D, pos.getZ() + 0.5, ingredients[i]);
					worldObj.spawnEntityInWorld(entityItem);
				}
				ingredients[i] = null;
				break;
			}
		}
		worldObj.markBlockForUpdate(pos);
	}

	public boolean isFull()
	{
		for (int i = 0; i < ingredients.length; i++)
		{
			if (ingredients[i] == null)
			{
				return false;
			}
		}
		return true;
	}

	public ItemStack[] getIngredients()
	{
		return ingredients;
	}

	public boolean hasValidIngredients()
	{
		RecipeData data = RecipeAPI.getBlenderRecipeDataFromIngredients(ingredients);
		if (data == null)
		{
			return false;
		}
		drinkName = data.getDrinkName();
		healAmount = data.getHealAmount();
		currentRed = data.getRed();
		currentGreen = data.getGreen();
		currentBlue = data.getBlue();
		return true;
	}

	public void startBlending()
	{
		blending = true;
	}

	public boolean isBlending()
	{
		return blending;
	}

	public boolean hasDrink()
	{
		if (this.drinkCount > 0)
		{
			return true;
		}
		return false;
	}

	public ItemStack getDrink()
	{
		ItemStack cup = new ItemStack(FurnitureItems.itemDrink);
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setIntArray("Colour", new int[] { currentRed, currentGreen, currentBlue });
		nbt.setInteger("HealAmount", healAmount);
		cup.setTagCompound(nbt);
		cup.setStackDisplayName(new String(drinkName));
		return cup;
	}

	int timer = 0;

	@Override
	public void update()
	{
		if (blending)
		{
			progress++;
			if (progress == 200)
			{
				clearIngredients();
				drinkCount = 6;
				progress = 0;
				blending = false;
				worldObj.updateComparatorOutputLevel(pos, blockType);
			}

			if (timer == 20)
			{
				timer = 0;
			}
			if (timer == 0)
			{
				worldObj.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "cfm:blender", 0.75F, 1.0F);
			}
			timer++;
		}
	}

	public void clearIngredients()
	{
		for (int i = 0; i < ingredients.length; i++)
		{
			if (ingredients[i] != null)
			{
				if (ingredients[i].getItem().hasContainerItem())
				{
					if (!worldObj.isRemote)
					{
						EntityItem entityItem = new EntityItem(worldObj, pos.getX() + 0.5, pos.getY() + 0.6, pos.getZ() + 0.5, new ItemStack(ingredients[i].getItem().getContainerItem()));
						worldObj.spawnEntityInWorld(entityItem);
					}
				}
				ingredients[i] = null;
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		if (par1NBTTagCompound.hasKey("Items"))
		{
			NBTTagList tagList = (NBTTagList) par1NBTTagCompound.getTag("Items");
			this.ingredients = new ItemStack[4];

			for (int i = 0; i < tagList.tagCount(); ++i)
			{
				NBTTagCompound nbt = (NBTTagCompound) tagList.getCompoundTagAt(i);
				byte s = nbt.getByte("Slot");

				if (s >= 0 && s < this.ingredients.length)
				{
					this.ingredients[s] = ItemStack.loadItemStackFromNBT(nbt);
				}
			}
		}

		this.blending = par1NBTTagCompound.getBoolean("Blending");
		this.progress = par1NBTTagCompound.getInteger("Progress");
		this.drinkCount = par1NBTTagCompound.getInteger("DrinkCount");
		this.drinkName = par1NBTTagCompound.getString("DrinkName");
		this.healAmount = par1NBTTagCompound.getInteger("HealAmount");
		this.currentRed = par1NBTTagCompound.getInteger("CurrentRed");
		this.currentGreen = par1NBTTagCompound.getInteger("CurrentGreen");
		this.currentBlue = par1NBTTagCompound.getInteger("CurrentBlue");
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		NBTTagList tagList = new NBTTagList();
		for (int i = 0; i < this.ingredients.length; ++i)
		{
			if (this.ingredients[i] != null)
			{
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setByte("Slot", (byte) i);
				this.ingredients[i].writeToNBT(nbt);
				tagList.appendTag(nbt);
			}
		}
		par1NBTTagCompound.setTag("Items", tagList);
		par1NBTTagCompound.setBoolean("Blending", blending);
		par1NBTTagCompound.setInteger("Progress", progress);
		par1NBTTagCompound.setString("DrinkName", drinkName);
		par1NBTTagCompound.setInteger("DrinkCount", drinkCount);
		par1NBTTagCompound.setInteger("HealAmount", healAmount);
		par1NBTTagCompound.setInteger("CurrentRed", currentRed);
		par1NBTTagCompound.setInteger("CurrentGreen", currentGreen);
		par1NBTTagCompound.setInteger("CurrentBlue", currentBlue);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		NBTTagCompound tagCom = pkt.getNbtCompound();
		this.readFromNBT(tagCom);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound tagCom = new NBTTagCompound();
		this.writeToNBT(tagCom);
		return new S35PacketUpdateTileEntity(pos, getBlockMetadata(), tagCom);
	}

	@Override
	public int getSize()
	{
		return ingredients.length;
	}

	@Override
	public ItemStack getItem(int i)
	{
		return ingredients[i];
	}

	@Override
	public void clear()
	{
		for (int i = 0; i < ingredients.length; i++)
		{
			ingredients[i] = null;
		}
	}
}
