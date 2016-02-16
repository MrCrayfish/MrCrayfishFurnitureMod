package com.mrcrayfish.furniture.blocks;

import java.util.List;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.tileentity.TileEntityEski;
import com.mrcrayfish.furniture.util.CollisionHelper;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEski extends BlockFurniture implements ITileEntityProvider
{
	public static final PropertyBool OPENED = PropertyBool.create("open");
	
	public BlockEski(Material material)
	{
		super(material);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(OPENED, Boolean.valueOf(false)));
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
	{
		int meta = worldIn.getBlockState(pos).getValue(FACING).getHorizontalIndex();
		CollisionHelper.setBlockBounds(this, meta, 1.5F * 0.0625F, 0F, 0F, 14.5F * 0.0625F, 0.8F, 1F);
	}
	
	@Override
	public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity)
	{
		int meta = state.getValue(FACING).getHorizontalIndex();
		CollisionHelper.setBlockBounds(this, meta, 2.5F * 0.0625F, 0F, 1 * 0.0625F, 13.5F * 0.0625F, 0.75F, 15 * 0.0625F);
		super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if(playerIn.isSneaking())
		{
			TileEntity tileEntity = worldIn.getTileEntity(pos);
			if(state.getValue(OPENED))
			{
				worldIn.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "cfm:cabinetclose", 0.75F, 0.6F);
			}
			else
			{
				worldIn.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "cfm:cabinetopen", 0.75F, 0.7F);
			}
			worldIn.setBlockState(pos, state.withProperty(OPENED, !state.getValue(OPENED)));
			if(tileEntity != null)
			{
				tileEntity.validate();
				worldIn.setTileEntity(pos, tileEntity);
			}
		}
		else
		{
			if(state.getValue(OPENED))
			{
				playerIn.openGui(MrCrayfishFurnitureMod.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		return true;
	}
	
	@Override
	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		IBlockState state = super.onBlockPlaced(world, pos, facing, hitX, hitY, hitZ, meta, placer);
		return state.withProperty(FACING, placer.getHorizontalFacing()).withProperty(OPENED, Boolean.valueOf(false));
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((EnumFacing) state.getValue(FACING)).getHorizontalIndex() + (state.getValue(OPENED) ? 4 : 0);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta % 4)).withProperty(OPENED, Boolean.valueOf(meta - 4 > 0));
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] { FACING, OPENED });
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityEski();
	}
}
