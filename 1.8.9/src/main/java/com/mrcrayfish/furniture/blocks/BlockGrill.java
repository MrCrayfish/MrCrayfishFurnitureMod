package com.mrcrayfish.furniture.blocks;

import java.util.Random;

import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.tileentity.TileEntityGrill;
import com.mrcrayfish.furniture.util.CollisionHelper;
import com.mrcrayfish.furniture.util.ParticleHelper;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
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
				if(RANDOM.nextInt(2) == 0)
				{
					double posX = 0.2D + 0.6D * RANDOM.nextDouble();
					double posZ = 0.2D + 0.6D * RANDOM.nextDouble();
					worldIn.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + posX, pos.getY() + 1.0D, pos.getZ() + posZ, 0.0D, 0.0D, 0.0D, new int[0]);
				}
				if(tileEntityGrill.leftCooked)
				{
					int meta = getMetaFromState(state);
					float[] leftFixed = ParticleHelper.fixRotation(meta, 0.5F, 0.25F);
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + leftFixed[0], pos.getY() + 1.0D, pos.getZ() + leftFixed[1], 0.0D, 0.0D, 0.0D, new int[0]);
				}
				if(tileEntityGrill.rightCooked)
				{
					int meta = getMetaFromState(state);
					float[] rightFixed = ParticleHelper.fixRotation(meta, 0.5F, 0.75F);
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + rightFixed[0], pos.getY() + 1.0D, pos.getZ() + rightFixed[1], 0.0D, 0.0D, 0.0D, new int[0]);
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
					else if(current.getItem() == Items.flint_and_steel)
					{
						tileEntityGrill.startFire();
					}
					else if(current.getItem() == FurnitureItems.itemSpatula && side == EnumFacing.UP)
					{
						EnumFacing facing = state.getValue(FACING);
						tileEntityGrill.flipFood(getClickedSide(facing, hitX, hitZ));
					}
					else if(current.getItem() instanceof ItemFood && side == EnumFacing.UP)
					{
						EnumFacing facing = state.getValue(FACING);
						if(tileEntityGrill.addFood(getClickedSide(facing, hitX, hitZ), current))
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
	
	public ClickedSide getClickedSide(EnumFacing facing, float hitX, float hitZ)
	{
		switch(facing)
		{
			case NORTH:
				if(hitX <= 0.5) return ClickedSide.LEFT;
				 return ClickedSide.RIGHT;
			case EAST:
				if(hitZ <= 0.5) return ClickedSide.LEFT;
				return ClickedSide.RIGHT;
			case SOUTH:
				if(hitX <= 0.5) return ClickedSide.RIGHT;
				return ClickedSide.LEFT;
			case WEST:
				if(hitZ <= 0.5) return ClickedSide.RIGHT;
				return ClickedSide.LEFT;
			default:
				return ClickedSide.UNKNOWN;		
		}
	}
	
	public static enum ClickedSide 
	{
		LEFT, RIGHT, UNKNOWN;
	}
}
