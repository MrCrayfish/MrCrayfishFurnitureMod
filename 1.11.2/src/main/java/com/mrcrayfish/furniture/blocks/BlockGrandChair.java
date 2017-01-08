package com.mrcrayfish.furniture.blocks;

import java.util.List;
import java.util.Random;

import com.mrcrayfish.furniture.entity.EntitySittableBlock;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.util.CollisionHelper;
import com.mrcrayfish.furniture.util.SittableUtil;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGrandChair extends BlockFurniture 
{
	private static final AxisAlignedBB BOUNDING_BOX_BOTTOM = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.8, 1.0);
	private static final AxisAlignedBB BOUNDING_BOX_TOP = new AxisAlignedBB(0.0, -1.0, 0.0, 1.0, 0.8, 1.0);
	
	private static final AxisAlignedBB COLLISION_BOX_BOTTOM = new AxisAlignedBB(0, 0, 0, 1, 0.5, 1);
	private static final AxisAlignedBB COLLISION_BOX_TOP_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 13 * 0.0625, 0.0, 0.0, 1.0, 0.8, 1.0);
	private static final AxisAlignedBB COLLISION_BOX_TOP_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 13 * 0.0625, 0.0, 0.0, 1.0, 0.8, 1.0);
	private static final AxisAlignedBB COLLISION_BOX_TOP_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 13 * 0.0625, 0.0, 0.0, 1.0, 0.8, 1.0);
	private static final AxisAlignedBB COLLISION_BOX_TOP_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 13 * 0.0625, 0.0, 0.0, 1.0, 0.8, 1.0);
	private static final AxisAlignedBB[] COLLISION_BOX_TOP = { COLLISION_BOX_TOP_SOUTH, COLLISION_BOX_TOP_WEST, COLLISION_BOX_TOP_NORTH, COLLISION_BOX_TOP_EAST };
	
	public BlockGrandChair(Material materialIn, boolean top) 
	{
		super(materialIn);
		this.setHardness(1.0F);
		this.setSoundType(SoundType.WOOD);
		if(top) this.setCreativeTab(null);
	}
	
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		if(this == FurnitureBlocks.grand_chair_bottom)
		{
			if(worldIn.getBlockState(pos.up()).getBlock() == FurnitureBlocks.grand_chair_top)
			{
				worldIn.destroyBlock(pos.up(), false);
			}
		}
		else
		{
			if(worldIn.getBlockState(pos.down()).getBlock() == FurnitureBlocks.grand_chair_bottom)
			{
				worldIn.destroyBlock(pos.down(), false);
			}
		}
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		return worldIn.isAirBlock(pos) && worldIn.isAirBlock(pos.up());
	}
	
	@Override
	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) 
	{
		if(this == FurnitureBlocks.grand_chair_bottom)
		{
			world.setBlockState(pos.up(), FurnitureBlocks.grand_chair_top.getDefaultState().withProperty(FACING, placer.getHorizontalFacing()));
		}
		return super.onBlockPlaced(world, pos, facing, hitX, hitY, hitZ, meta, placer);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(this == FurnitureBlocks.grand_chair_bottom)
		{
			if(SittableUtil.sitOnBlock(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn, 6 * 0.0625))
			{
				worldIn.updateComparatorOutputLevel(pos, this);
				return true;
			}
		}
		else
		{
			worldIn.getBlockState(pos.down()).getBlock().onBlockActivated(worldIn, pos.down(), state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
		}
		return false;
	}	
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) 
	{
		if(this == FurnitureBlocks.grand_chair_bottom)
		{
			return BOUNDING_BOX_BOTTOM;
		}
		else
		{
			return BOUNDING_BOX_TOP;
		}
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB axisAligned, List<AxisAlignedBB> axisAlignedList, Entity collidingEntity) 
	{
		if (!(collidingEntity instanceof EntitySittableBlock))
		{
			if(this == FurnitureBlocks.grand_chair_bottom)
			{
				super.addCollisionBoxToList(pos, axisAligned, axisAlignedList, COLLISION_BOX_BOTTOM);
			}
			else
			{
				EnumFacing facing = state.getValue(FACING);
				super.addCollisionBoxToList(pos, axisAligned, axisAlignedList, COLLISION_BOX_TOP[facing.getHorizontalIndex()]);
			}
		}
	}
	
	@Override
	public boolean hasComparatorInputOverride(IBlockState state) 
	{
		return true;
	}
	
	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) 
	{
		return SittableUtil.isSomeoneSitting(world, pos.getX(), pos.getY(), pos.getZ()) ? 1 : 0;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return new ItemStack(FurnitureBlocks.grand_chair_bottom).getItem();
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) 
	{
		return new ItemStack(FurnitureBlocks.grand_chair_bottom);
	}
}
