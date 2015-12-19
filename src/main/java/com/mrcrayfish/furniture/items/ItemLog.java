package com.mrcrayfish.furniture.items;

import com.mrcrayfish.furniture.blocks.BlockFirePit;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemLog extends ItemPlacer 
{
	public ItemLog(Block block) 
	{
		super(block);
	}
	
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		IBlockState state = world.getBlockState(pos);
		Block block = world.getBlockState(pos).getBlock();
		if(block instanceof BlockFirePit)
		{
			int meta = block.getMetaFromState(state);
			if(meta < 3)
			{
				world.setBlockState(pos, state.withProperty(BlockFirePit.STAGE, meta + 1));
				stack.stackSize--;
				return true;
			}
			return false;
		}
		return super.onItemUse(stack, player, world, pos, side, hitX, hitY, hitZ);
	}
}
