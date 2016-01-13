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

import java.util.List;

import com.mrcrayfish.furniture.entity.EntitySittableBlock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockSittable extends Block
{
	public BlockSittable(Material par2Material)
	{
		super(par2Material);
	}

	public boolean sitOnBlock(World par1World, double x, double y, double z, EntityPlayer par5EntityPlayer, double par6)
	{
		if (!checkForExistingEntity(par1World, x, y, z, par5EntityPlayer))
		{
			EntitySittableBlock nemb = new EntitySittableBlock(par1World, x, y, z, par6);
			par1World.spawnEntityInWorld(nemb);
			par5EntityPlayer.mountEntity(nemb);
		}
		return true;
	}

	public boolean sitOnBlockWithRotationOffset(World par1World, double x, double y, double z, EntityPlayer par5EntityPlayer, double par6, int metadata, double offset)
	{
		if (!checkForExistingEntity(par1World, x, y, z, par5EntityPlayer))
		{
			EntitySittableBlock nemb = new EntitySittableBlock(par1World, x, y, z, par6, metadata, offset);
			par1World.spawnEntityInWorld(nemb);
			par5EntityPlayer.mountEntity(nemb);
		}
		return true;
	}

	public boolean checkForExistingEntity(World par1World, double x, double y, double z, EntityPlayer par5EntityPlayer)
	{
		List<EntitySittableBlock> listEMB = par1World.getEntitiesWithinAABB(EntitySittableBlock.class, new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D).expand(1D, 1D, 1D));
		for (EntitySittableBlock mount : listEMB)
		{
			if (mount.blockPosX == x && mount.blockPosY == y && mount.blockPosZ == z)
			{
				if (mount.riddenByEntity == null)
				{
					par5EntityPlayer.mountEntity(mount);
				}
				return true;
			}
		}
		return false;
	}

}
