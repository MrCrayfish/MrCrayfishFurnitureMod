package com.mrcrayfish.furniture.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFirePit extends Block {
	
	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 4); 

	public BlockFirePit(Material materialIn) 
	{
		super(materialIn);
		this.setLightLevel(1.0F);
		this.setTickRandomly(true);
		this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, 4));
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean isFullCube()
	{
		return false;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, BlockPos pos)
	{
		setBlockBounds(0F, 0F, 0F, 1F, 0.1875F, 1F);
	}
	
	@Override
	public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity) 
	{
		setBlockBounds(0F, 0F, 0F, 1F, 0.1875F, 1F);
		super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) 
	{	
		return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return (Integer) state.getValue(STAGE);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		if(meta > 4) meta = 4;
		return this.getDefaultState().withProperty(STAGE, meta);
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] { STAGE });
	}
	
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.CUTOUT;
	}
}
