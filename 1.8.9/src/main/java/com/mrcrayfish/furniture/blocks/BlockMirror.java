package com.mrcrayfish.furniture.blocks;

import java.util.List;
import java.util.Random;

import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.tileentity.TileEntityMirror;
import com.mrcrayfish.furniture.util.CollisionHelper;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMirror extends BlockFurnitureTile
{
	public BlockMirror(Material material)
	{
		super(material);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, BlockPos pos)
	{
		IBlockState state = blockAccess.getBlockState(pos);
		int rotation = ((EnumFacing) state.getValue(FACING)).getHorizontalIndex();
		float[] bounds = CollisionHelper.fixRotation(rotation, 0.9375F, 0.0F, 1.0F, 1.0F);
		setBlockBounds(bounds[0], 0.0F, bounds[1], bounds[2], 1.0F, bounds[3]);
	}
	
	@Override
	public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
	{
		int rotation = ((EnumFacing) state.getValue(FACING)).getHorizontalIndex();
		float[] bounds = CollisionHelper.fixRotation(rotation, 0.9375F, 0.0F, 1.0F, 1.0F);
		setBlockBounds(bounds[0], 0.0F, bounds[1], bounds[2], 1.0F, bounds[3]);
		super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityMirror();
	}
}
