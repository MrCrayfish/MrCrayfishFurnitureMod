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

import com.mrcrayfish.furniture.gui.containers.ContainerBin;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.SoundCategory;

public class TileEntityBin extends TileEntityFurniture
{
	public TileEntityBin() 
	{
		super("bin", 12);
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
		world.playSound(pos.getX(), pos.getY(), pos.getZ(), FurnitureSounds.bin_open, SoundCategory.BLOCKS, 0.75F, 1.0F, true);
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
		world.playSound(pos.getX(), pos.getY(), pos.getZ(), FurnitureSounds.bin_close, SoundCategory.BLOCKS, 0.75F, 1.0F, true);
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) 
	{
		this.fillWithLoot(playerIn);
		return new ContainerBin(playerInventory, this);
	}
}
