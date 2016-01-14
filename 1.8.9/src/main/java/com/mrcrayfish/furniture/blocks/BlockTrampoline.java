package com.mrcrayfish.furniture.blocks;

import java.util.List;

import com.mrcrayfish.furniture.util.ParticleSpawner;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTrampoline extends Block 
{
	public BlockTrampoline(Material materialIn) 
	{
		super(materialIn);
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
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 8 * 0.0625F, 1.0F);
	}
	
	@Override
	public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
	{
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 8 * 0.0625F, 1.0F);
		super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
	}
	
	@Override
	public void onLanded(World worldIn, Entity entityIn) 
	{
		if(worldIn.isRemote) entityIn.fallDistance = 0;
	}
	
	@Override
	public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) 
	{
		if(entityIn instanceof EntityLivingBase)
		{
			float height = entityIn.fallDistance;
			if(height > 0 && !entityIn.isSneaking()) 
			{
				if(height > 9) height = 9;
				entityIn.motionY = 0;
				entityIn.addVelocity(0, getRequiredVelocity(height + 1), 0);
				worldIn.playSoundEffect(entityIn.posX, entityIn.posY, entityIn.posZ, "cfm:boing", 0.75F, 1.0F);
				if(worldIn.isRemote)
				{
					for(int i = 0; i < 5; i++)
					{
						double offsetX = -0.1 + 0.2 * RANDOM.nextDouble();
						double offsetZ = -0.1 + 0.2 * RANDOM.nextDouble();
						worldIn.spawnParticle(EnumParticleTypes.SPELL_MOB, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 1, 1, 1, 0);
					}
				}
			}
			entityIn.fallDistance = 0;
		}
	}
	
	@Override
	public boolean addLandingEffects(WorldServer worldObj, BlockPos blockPosition, IBlockState iblockstate, EntityLivingBase entity, int numberOfParticles)
	{
		return true;
	}
	
	public double getRequiredVelocity(float height)
	{
		return Math.sqrt(0.22 * height);
	}
	
	
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.CUTOUT;
	}
}
