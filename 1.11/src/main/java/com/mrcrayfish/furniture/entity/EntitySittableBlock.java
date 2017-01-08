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
package com.mrcrayfish.furniture.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntitySittableBlock extends Entity
{
	public int blockPosX;
	public int blockPosY;
	public int blockPosZ;

	public EntitySittableBlock(World world)
	{
		super(world);
		this.noClip = true;
		this.height = 0.01F;
		this.width = 0.01F;
	}

	public EntitySittableBlock(World world, double x, double y, double z, double y0ffset)
	{
		this(world);
		this.blockPosX = (int) x;
		this.blockPosY = (int) y;
		this.blockPosZ = (int) z;
		setPosition(x + 0.5D, y + y0ffset, z + 0.5D);
	}

	public EntitySittableBlock(World world, double x, double y, double z, double y0ffset, int rotation, double rotationOffset)
	{
		this(world);
		this.blockPosX = (int) x;
		this.blockPosY = (int) y;
		this.blockPosZ = (int) z;
		setPostionConsideringRotation(x + 0.5D, y + y0ffset, z + 0.5D, rotation, rotationOffset);
	}

	public void setPostionConsideringRotation(double x, double y, double z, int rotation, double rotationOffset)
	{
		switch (rotation)
		{
		case 2:
			z += rotationOffset;
			break;
		case 0:
			z -= rotationOffset;
			break;
		case 3:
			x -= rotationOffset;
			break;
		case 1:
			x += rotationOffset;
			break;
		}
		setPosition(x, y, z);
	}

	@Override
	public double getMountedYOffset()
	{
		return this.height * 0.0D;
	}

	@Override
	protected boolean shouldSetPosAfterLoading()
	{
		return false;
	}

	@Override
	public void onEntityUpdate()
	{
		if (!this.worldObj.isRemote)
		{
			if (!this.isBeingRidden() || this.worldObj.isAirBlock(new BlockPos(blockPosX, blockPosY, blockPosZ)))
			{
				this.setDead();
				worldObj.updateComparatorOutputLevel(getPosition(), worldObj.getBlockState(getPosition()).getBlock());
			}
		}
	}

	@Override
	protected void entityInit()
	{
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
	}

}
