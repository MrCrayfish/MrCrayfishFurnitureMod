package com.mrcrayfish.furniture.blocks;

import java.util.List;

import com.mrcrayfish.furniture.util.CollisionHelper;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMantelPiece extends BlockFurniture 
{
	public BlockMantelPiece(Material materialIn) 
	{
		super(materialIn);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, BlockPos pos)
	{
		int metadata = getMetaFromState(blockAccess.getBlockState(pos));
		float data[] = CollisionHelper.fixRotation(metadata, 0.625F, -0.5F, 1.0F, 1.5F);
		setBlockBounds(data[0], 0F, data[1], data[2], 1.4F, data[3]);
	}
	
	@Override
	public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
	{
		int metadata = getMetaFromState(state);
		float data[] = CollisionHelper.fixRotation(metadata, 0.625F, -0.25F, 1.0F, 1.25F);
		setBlockBounds(data[0], 0F, data[1], data[2], 1.25F, data[3]);
	}
}
