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
package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.gui.inventory.ISimpleInventory;
import com.mrcrayfish.furniture.util.InventoryUtil;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public abstract class BlockFurnitureTile extends BlockFurniture implements ITileEntityProvider
{
	public BlockFurnitureTile(Material material)
	{
		super(material);
		this.isBlockContainer = true;
	}
	
	@Override
	public int getComparatorInputOverride(World world, BlockPos pos)
	{
		TileEntity tileEntity = world.getTileEntity(pos);
		return Container.calcRedstone(tileEntity);
	}
	
	@Override
	public boolean hasComparatorInputOverride() 
	{
		return true;
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity instanceof IInventory)
		{
			IInventory inv = (IInventory) tileEntity;
			InventoryHelper.dropInventoryItems(world, pos, inv);
		}
		if (tileEntity instanceof ISimpleInventory)
		{
			ISimpleInventory inv = (ISimpleInventory) tileEntity;
			InventoryUtil.dropInventoryItems(world, pos, inv);
		}
		super.breakBlock(world, pos, state);
	}

	@Override
	public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam)
	{
		super.onBlockEventReceived(worldIn, pos, state, eventID, eventParam);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		return tileentity == null ? false : tileentity.receiveClientEvent(eventID, eventParam);
	}
}
