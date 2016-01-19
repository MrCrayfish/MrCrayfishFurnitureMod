package com.mrcrayfish.furniture.blocks;

import java.util.Random;

import com.mrcrayfish.furniture.tileentity.TileEntityGrill;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGrill extends BlockFurnitureTile
{
	public BlockGrill(Material material) 
	{
		super(material);
	}
	
	@Override
	public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) 
	{
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if(tileEntity instanceof TileEntityGrill)
		{
			TileEntityGrill tileEntityGrill = (TileEntityGrill) tileEntity;
			if(tileEntityGrill.isFireStarted())
			{
				if(RANDOM.nextInt(4) == 0)
				{
					double posX = 0.2D + 0.6D * RANDOM.nextDouble();
					double posZ = 0.2D + 0.6D * RANDOM.nextDouble();
					worldIn.spawnParticle(EnumParticleTypes.FLAME, posX, 1.0D, posZ, 0.0D, 0.0D, 0.0D, new int[0]);
				}
			}
		}
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) 
	{
		System.out.println(hitX + " " + hitZ);
		if(!worldIn.isRemote)
		{
			TileEntity tileEntity = worldIn.getTileEntity(pos);
			if(tileEntity instanceof TileEntityGrill)
			{
				TileEntityGrill tileEntityGrill = (TileEntityGrill) tileEntity;
				ItemStack current = playerIn.getCurrentEquippedItem();
				
				if(current != null)
				{
					if(current.getItem() == Items.coal)
					{
						if(tileEntityGrill.addCoal())
						{
							current.stackSize--;
						}
					}
				}
			}
		}
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{
		return new TileEntityGrill();
	}
	
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.CUTOUT;
	}
}
