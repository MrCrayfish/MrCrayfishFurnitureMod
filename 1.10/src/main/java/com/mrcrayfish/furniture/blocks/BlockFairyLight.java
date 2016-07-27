package com.mrcrayfish.furniture.blocks;

import java.util.List;

import com.mrcrayfish.furniture.util.CollisionHelper;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFairyLight extends BlockFurniture {

	public static final PropertyEnum TYPE = PropertyEnum.create("type", FairyLightType.class);
	
	private static final AxisAlignedBB BOUNDING_BOX_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.375, 0.6875, 0.0, 0.6875, 1.0, 1.0);
	private static final AxisAlignedBB BOUNDING_BOX_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.375, 0.6875, 0.0, 0.6875, 1.0, 1.0);
	private static final AxisAlignedBB BOUNDING_BOX_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.375, 0.6875, 0.0, 0.6875, 1.0, 1.0);
	private static final AxisAlignedBB BOUNDING_BOX_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.375, 0.6875, 0.0, 0.6875, 1.0, 1.0);
	private static final AxisAlignedBB[] BOUNDING_BOX = { BOUNDING_BOX_SOUTH, BOUNDING_BOX_WEST, BOUNDING_BOX_NORTH, BOUNDING_BOX_EAST };
	
	public BlockFairyLight(Material materialIn) 
	{
		super(materialIn);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TYPE, FairyLightType.EVEN));
		this.setLightLevel(0.5F);
		this.setHardness(0.5F);
		this.setSoundType(SoundType.GLASS);
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) 
	{
		return worldIn.isSideSolid(pos.up(), EnumFacing.DOWN);
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) 
	{
		if((pos.getX() % 2) == (pos.getZ() % 2))
		{
			return state.withProperty(TYPE, FairyLightType.EVEN);
		}
		else
		{
			return state.withProperty(TYPE, FairyLightType.ODD);
		}
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) 
	{
		EnumFacing facing = state.getValue(FACING);
		return BOUNDING_BOX[facing.getHorizontalIndex()];
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState worldIn, World pos, BlockPos state) 
	{
		return NULL_AABB;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { FACING, TYPE });
	}
	
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	public static enum FairyLightType implements IStringSerializable
	{
		EVEN, ODD;

		@Override
		public String getName()
		{
			return this.toString().toLowerCase();
		}
	}

}
