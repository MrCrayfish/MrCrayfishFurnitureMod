package com.mrcrayfish.furniture.blocks;

import java.util.Random;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.init.FurnitureItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockChimney extends Block {
	
	public static final PropertyEnum TYPE = PropertyEnum.create("type", ChimneyType.class);
	
	public BlockChimney(Material material) 
	{
		super(material);
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, ChimneyType.TOP));
		this.setTickRandomly(true);
		this.setHardness(1.0F);
		this.setStepSound(Block.soundTypeStone);
		this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
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
		setBlockBounds(0.125F, 0.0F, 0.125F, 0.875F, 1.0F, 0.875F);
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		if(world.getBlockState(pos.up()).getBlock() == this)
		{
			return state.withProperty(TYPE, ChimneyType.BOTTOM);
		}
		else
		{
			return state.withProperty(TYPE, ChimneyType.TOP);
		}
	}
	
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
		IBlockState actualState = getActualState(state, worldIn, pos);
		if(actualState.getValue(TYPE) != ChimneyType.TOP)
			return;
		
		for(int i = 0; i < 2; i++)
		{
			double posX = 0.25 + (0.5 * rand.nextDouble());
			double posZ = 0.25 + (0.5 * rand.nextDouble());
			worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + posX, pos.getY() + 0.9, pos.getZ() + posZ, 0.0D, 0.0D, 0.0D, new int[0]);
			worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + posX, pos.getY() + 0.9, pos.getZ() + posZ, 0.0D, 0.0D, 0.0D, new int[0]);
		}
    }
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return 0;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState();
	}
	
	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this,  new IProperty[] { TYPE });
	}

	public static enum ChimneyType implements IStringSerializable
	{
		TOP, BOTTOM;

		@Override
		public String getName()
		{
			return this.toString().toLowerCase();
		}
	}

}
