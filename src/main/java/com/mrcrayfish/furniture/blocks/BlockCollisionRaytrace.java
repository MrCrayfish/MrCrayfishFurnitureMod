package com.mrcrayfish.furniture.blocks;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BlockCollisionRaytrace extends Block
{
    protected static final List<AxisAlignedBB> EMPTY = new ArrayList<>();

	public BlockCollisionRaytrace(Material material)
	{
		super(material);
	}

	public BlockCollisionRaytrace(Material material, MapColor blockMapColor)
	{
		super(material, blockMapColor);
	}

	@Nullable
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
    	return null;
    }

	@Nullable
    protected List<AxisAlignedBB> getRaytraceBoxes(IBlockState state, World world, BlockPos pos)
    {
        return getCollisionBoxes(state, world, pos, null, false);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity, boolean isActualState)
    {
    	List<AxisAlignedBB> boxes = getCollisionBoxes(state, world, pos, null, isActualState);
    	if (boxes == null)
    	{
    		super.addCollisionBoxToList(state, world, pos, entityBox, collidingBoxes, entity, isActualState);
    		return;
    	}
        entityBox = entityBox.offset(-pos.getX(), -pos.getY(), -pos.getZ());
        for (AxisAlignedBB box : boxes)
        {
            if (entityBox.intersects(box))
                collidingBoxes.add(box.offset(pos));
        }
    }

    @Override
    @Nullable
    public RayTraceResult collisionRayTrace(IBlockState state, World world, BlockPos pos, Vec3d start, Vec3d end)
    {
    	List<AxisAlignedBB> boxes = getRaytraceBoxes(state, world, pos);
    	if (boxes == null)
    		return super.collisionRayTrace(state, world, pos, start, end);

        double distanceSq;
        double distanceSqShortest = Double.POSITIVE_INFINITY;
        RayTraceResult resultClosest = null;
        RayTraceResult result;
        start = start.subtract(pos.getX(), pos.getY(), pos.getZ());
        end = end.subtract(pos.getX(), pos.getY(), pos.getZ());
        for (AxisAlignedBB box : boxes)
        {
            result = box.calculateIntercept(start, end);
            if (result == null)
                continue;

            distanceSq = result.hitVec.squareDistanceTo(start);
            if (distanceSq < distanceSqShortest)
            {
                distanceSqShortest = distanceSq;
                resultClosest = result;
            }
        }
        return resultClosest == null ? null : new RayTraceResult(RayTraceResult.Type.BLOCK, resultClosest.hitVec.addVector(pos.getX(), pos.getY(), pos.getZ()), resultClosest.sideHit, pos);
    }
}