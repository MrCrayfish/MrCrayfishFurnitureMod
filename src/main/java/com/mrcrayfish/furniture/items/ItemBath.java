package com.mrcrayfish.furniture.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBath extends ItemBlock
{
	public ItemBath(Block block)
	{
		super(block);
	}
	
	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack)
	{
		return worldIn.isAirBlock(pos.offset(side)) && worldIn.isAirBlock(pos.offset(side).offset(player.getHorizontalFacing(), 1));
	}
}
