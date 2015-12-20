package com.mrcrayfish.furniture.blocks;

import java.util.Random;

import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.util.CollisionHelper;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFairyLight extends BlockFurniture {

	public static final PropertyEnum TYPE = PropertyEnum.create("type", FairyLightType.class);
	
	public BlockFairyLight(Material materialIn) 
	{
		super(materialIn);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TYPE, FairyLightType.EVEN));
		this.setLightLevel(0.5F);
		this.setHardness(0.5F);
		this.setStepSound(Block.soundTypeGlass);
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
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, BlockPos pos)
	{
		int metadata = getMetaFromState(blockAccess.getBlockState(pos));
		float data[] = CollisionHelper.fixRotation(metadata, 0.375F, 0F, 0.6875F, 1F);
		setBlockBounds(data[0], 0.6875F, data[1], data[2], 1F, data[3]);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) 
	{
		return null;
	}
	
	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] { FACING, TYPE });
	}
	
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.TRANSLUCENT;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return FurnitureItems.itemFairyLight;
	}
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos)
	{
		return new ItemStack(FurnitureItems.itemFairyLight);
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
