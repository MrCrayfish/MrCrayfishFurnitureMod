package com.mrcrayfish.furniture.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;

public class CollisionHelper
{
	private static final Map<IBakedModel, List<AxisAlignedBB>> MODEL_BOXES = Maps.<IBakedModel, List<AxisAlignedBB>>newHashMap();

	public static AxisAlignedBB getBlockBounds(EnumFacing facing, double x1, double y1, double z1, double x2, double y2, double z2)
	{
		double[] bounds = fixRotation(facing, x1, z1, x2, z2);
		return new AxisAlignedBB(bounds[0], y1, bounds[1], bounds[2], y2, bounds[3]);
	}

	public static AxisAlignedBB getBlockBounds(EnumFacing facing, Bounds bounds)
	{
		double[] fixedBounds = fixRotation(facing, bounds.x1, bounds.z1, bounds.x2, bounds.z2);
		return new AxisAlignedBB(fixedBounds[0], bounds.y1, fixedBounds[1], fixedBounds[2], bounds.y2, fixedBounds[3]);
	}

	private static double[] fixRotation(EnumFacing facing, double var1, double var2, double var3, double var4)
	{
		switch (facing)
		{
		case WEST:
			double var_temp_1 = var1;
			var1 = 1.0F - var3;
			double var_temp_2 = var2;
			var2 = 1.0F - var4;
			var3 = 1.0F - var_temp_1;
			var4 = 1.0F - var_temp_2;
			break;
		case NORTH:
			double var_temp_3 = var1;
			var1 = var2;
			var2 = 1.0F - var3;
			var3 = var4;
			var4 = 1.0F - var_temp_3;
			break;
		case SOUTH:
			double var_temp_4 = var1;
			var1 = 1.0F - var4;
			double var_temp_5 = var2;
			var2 = var_temp_4;
			double var_temp_6 = var3;
			var3 = 1.0F - var_temp_5;
			var4 = var_temp_6;
			break;
		default:
			break;
		}
		return new double[] { var1, var2, var3, var4 };
	}

	public static class Listener implements IResourceManagerReloadListener
	{
		private static final Listener INSTANCE = new Listener();

		private Listener()
		{
		}

		public static void init()
		{
			((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(INSTANCE);
		}

		@Override
		public void onResourceManagerReload(IResourceManager resourceManager)
		{
			MODEL_BOXES.clear();
		}
	}
}
