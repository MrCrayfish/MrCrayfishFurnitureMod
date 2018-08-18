package com.mrcrayfish.furniture.blocks;

import java.util.Random;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureItems;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class BlockFirePitOff extends BlockFirePit
{
	public BlockFirePitOff(String name)
	{
		super(name);
		this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, 0));
	}

	@Override
	public boolean isBurning()
	{
		return false;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack heldItem = playerIn.getHeldItem(hand);
		if (!heldItem.isEmpty())
		{
			if (heldItem.getItem() instanceof ItemFlintAndSteel)
			{
				if (getMetaFromState(state) == 3)
				{
					worldIn.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, RANDOM.nextFloat() * 0.4F + 0.8F);
					worldIn.setBlockState(pos, FurnitureBlocks.FIRE_PIT_ON.getDefaultState());
					heldItem.damageItem(1, playerIn);
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random)
	{
		return getMetaFromState(state) + 1;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return FurnitureItems.LOG;
	}
}
