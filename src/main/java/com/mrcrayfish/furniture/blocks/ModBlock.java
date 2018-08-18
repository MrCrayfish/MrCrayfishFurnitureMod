package com.mrcrayfish.furniture.blocks;

import java.util.List;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * A simple modded block. Used to handle things that all blocks need.
 * 
 * <br>
 * </br>
 * 
 * <b>Author: Ocelot5836</b>
 */
public class ModBlock extends Block implements IWireFrame
{
	public ModBlock(Material material, String name)
	{
		this(material, material.getMaterialMapColor(), name, name);
	}

	public ModBlock(Material material, String registryName, String unlocalizedName)
	{
		this(material, material.getMaterialMapColor(), registryName, unlocalizedName);
	}

	public ModBlock(Material material, MapColor color, String name)
	{
		this(material, color, name, name);
	}

	public ModBlock(Material material, MapColor color, String registryName, String unlocalizedName)
	{
		super(material, color);
		this.setRegistryName(registryName);
		this.setUnlocalizedName(unlocalizedName);
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entity, boolean isActualState)
	{
		if (this instanceof IRayTrace && ((IRayTrace) this).applyCollisions(this.getActualState(state, world, pos), world, pos))
		{
			IRayTrace rayTrace = (IRayTrace) this;
			List<AxisAlignedBB> list = Lists.newArrayList();
			rayTrace.addBoxes(this.getActualState(state, world, pos), world, pos, list);
			for (AxisAlignedBB box : list)
			{
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, box);
			}
		} else
		{
			super.addCollisionBoxToList(state, world, pos, entityBox, collidingBoxes, entity, isActualState);
		}
	}

	@Override
	public RayTraceResult collisionRayTrace(IBlockState state, World world, BlockPos pos, Vec3d start, Vec3d end)
	{
		if (this instanceof IRayTrace)
		{
			IRayTrace rayTrace = (IRayTrace) this;
			List<RayTraceResult> list = Lists.newArrayList();
			List<AxisAlignedBB> boxes = Lists.newArrayList();
			rayTrace.addBoxes(this.getActualState(state, world, pos), world, pos, boxes);

			for (AxisAlignedBB axisalignedbb : boxes)
			{
				list.add(this.rayTrace(pos, start, end, axisalignedbb));
			}

			RayTraceResult raytraceresult1 = null;
			double d1 = 0.0D;

			for (RayTraceResult raytraceresult : list)
			{
				if (raytraceresult != null)
				{
					double d0 = raytraceresult.hitVec.squareDistanceTo(end);

					if (d0 > d1)
					{
						raytraceresult1 = raytraceresult;
						d1 = d0;
					}
				}
			}

			return raytraceresult1;
		} else
		{
			return super.collisionRayTrace(state, world, pos, start, end);
		}
	}

	@Override
	public void addWireframeBoxes(IBlockState state, World world, BlockPos pos, List<AxisAlignedBB> boxes)
	{
		if (this instanceof IRayTrace)
		{
			((IRayTrace) this).addBoxes(state, world, pos, boxes);
		} else
		{
			boxes.add(this.getBoundingBox(state, world, pos));
		}
	}

	public boolean applyCollisions(IBlockState state, World world, BlockPos pos)
	{
		return true;
	}
}