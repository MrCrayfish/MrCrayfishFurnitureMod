package com.mrcrayfish.furniture.blocks;

import java.util.List;

import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.util.CollisionHelper;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class BlockDivingboard extends BlockFurniture 
{
	public BlockDivingboard(Material material) 
	{
		super(material);
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		if(this == FurnitureBlocks.divingboard_base)
		{
			world.setBlockState(pos.offset(placer.getHorizontalFacing()), FurnitureBlocks.divingboard_plank.getDefaultState().withProperty(FACING, state.getValue(FACING)));
		}
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, BlockPos pos)
	{
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 13 * 0.0625F, 1.0F);
	}
	
	@Override
	public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
	{
		int meta = getMetaFromState(state);
		if(this == FurnitureBlocks.divingboard_base)
		{
			CollisionHelper.setBlockBounds(this, meta, 0.0F, 0.0F, 0.0F, 0.875F, 1.0F, 1.5F * 0.0625F);
			super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
			CollisionHelper.setBlockBounds(this, meta, 0.0F, 0.0F, 14.5F * 0.0625F, 0.875F, 1.0F, 16 * 0.0625F);
			super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
		}
		CollisionHelper.setBlockBounds(this, meta, 0.0F, 4 * 0.0625F, 2 * 0.0625F, 1.0F, 6 * 0.0625F, 14 * 0.0625F);
		super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
	}
	
	@Override
	public void onLanded(World worldIn, Entity entityIn) 
	{
		if(this == FurnitureBlocks.divingboard_base) super.onLanded(worldIn, entityIn);
	}
	
	@Override
	public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) 
	{
		if(this == FurnitureBlocks.divingboard_plank && entityIn instanceof EntityLivingBase)
		{
			float height = entityIn.fallDistance;
			if(height > 0 && !entityIn.isSneaking()) 
			{
				//TODO make final var instead
				if(height > 4) height = 4;
				entityIn.motionY = 0;
				entityIn.addVelocity(0, getRequiredVelocity(height + 1), 0);
				worldIn.playSoundEffect(entityIn.posX, entityIn.posY, entityIn.posZ, "cfm:boing", 0.75F, 0.75F);
				if(worldIn.isRemote)
				{
					for(int i = 0; i < 3; i++)
					{
						double offsetX = -0.1 + 0.2 * RANDOM.nextDouble();
						double offsetZ = -0.1 + 0.2 * RANDOM.nextDouble();
						worldIn.spawnParticle(EnumParticleTypes.SPELL_MOB, entityIn.posX, entityIn.posY, entityIn.posZ, 1, 1, 1, 0);
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
}
