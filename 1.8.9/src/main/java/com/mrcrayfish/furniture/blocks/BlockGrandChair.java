package com.mrcrayfish.furniture.blocks;

import java.util.List;
import java.util.Random;

import com.mrcrayfish.furniture.entity.EntitySittableBlock;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.util.CollisionHelper;
import com.mrcrayfish.furniture.util.SittableUtil;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGrandChair extends BlockFurniture 
{

	public BlockGrandChair(Material materialIn, boolean top) 
	{
		super(materialIn);
		this.setHardness(1.0F);
		this.setStepSound(Block.soundTypeWood);
		if(top) this.setCreativeTab(null);
	}
	
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		if(this == FurnitureBlocks.grand_chair_bottom)
		{
			worldIn.destroyBlock(pos.up(), false);
		}
		else
		{
			worldIn.destroyBlock(pos.down(), false);
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
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if(this == FurnitureBlocks.grand_chair_bottom)
		{
			if(SittableUtil.sitOnBlock(world, pos.getX(), pos.getY(), pos.getZ(), player, 6 * 0.0625))
			{
				world.updateComparatorOutputLevel(pos, this);
				return true;
			}
		}
		else
		{
			world.getBlockState(pos.down()).getBlock().onBlockActivated(world, pos.down(), state, player, side, hitX, hitY, hitZ);
		}
		return false;
	}	
	
	@Override
	@SideOnly(Side.CLIENT)
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, BlockPos pos)
	{
		if(Minecraft.getMinecraft().thePlayer.ridingEntity instanceof EntitySittableBlock)
			return;
		
		if(this == FurnitureBlocks.grand_chair_bottom)
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.8F, 1.0F);
		}
		else
		{
			setBlockBounds(0.0F, -1.0F, 0.0F, 1.0F, 0.8F, 1.0F);
		}
	}
	
	@Override
	public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
	{
		if (!(collidingEntity instanceof EntitySittableBlock))
		{
			if(this == FurnitureBlocks.grand_chair_bottom)
			{
				setBlockBounds(0F, 0F, 0F, 1F, 0.5F, 1F);
				super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
			}
			else
			{
				int metadata = getMetaFromState(state);
				float data[] = CollisionHelper.fixRotation(metadata, 13 * 0.0625F, 0.0F, 1.0F, 1.0F);
				setBlockBounds(data[0], 0.0F, data[1], data[2], 0.8F, data[3]); 
				super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
			}
		}
		else
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
			super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
		}
	}
	
	@Override
	public boolean hasComparatorInputOverride() 
	{
		return true;
	}
	
	@Override
	public int getComparatorInputOverride(World world, BlockPos pos) 
	{
		return SittableUtil.isSomeoneSitting(world, pos.getX(), pos.getY(), pos.getZ()) ? 1 : 0;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return FurnitureItems.itemGrandChair;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos)
	{
		return new ItemStack(FurnitureItems.itemGrandChair);
	}
}
