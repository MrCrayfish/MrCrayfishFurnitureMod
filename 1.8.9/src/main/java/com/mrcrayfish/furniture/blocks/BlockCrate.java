package com.mrcrayfish.furniture.blocks;

import java.util.ArrayList;
import java.util.List;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.tileentity.TileEntityCrate;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCrate extends Block implements ITileEntityProvider
{
	public BlockCrate(Material materialIn)
	{
		super(materialIn);
		this.setHardness(1.0F);
		this.setStepSound(Block.soundTypeWood);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		if(stack.hasTagCompound())
		{
			TileEntity crate = new TileEntityCrate();
			crate.readFromNBT(stack.getTagCompound());
			crate.validate();
			worldIn.setTileEntity(pos, crate);
		}
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if(tileEntity instanceof TileEntityCrate)
		{
			TileEntityCrate crate = (TileEntityCrate) tileEntity;
			
			ItemStack drop = new ItemStack(this);
			NBTTagCompound compound = new NBTTagCompound();
			crate.writeToNBT(compound);
			drop.setTagCompound(compound);
			worldIn.spawnEntityInWorld(new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop));
		}
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		System.out.println("Called 1");
		List<ItemStack> drops = new ArrayList<ItemStack>();
		
		
		return drops;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!worldIn.isRemote)
		{
			TileEntity tileEntity = worldIn.getTileEntity(pos);

			if (tileEntity instanceof TileEntityCrate)
			{
				TileEntityCrate crate = (TileEntityCrate) tileEntity;
				if(!crate.sealed)
				{
					playerIn.openGui(MrCrayfishFurnitureMod.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
				}
			}
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityCrate();
	}
}
