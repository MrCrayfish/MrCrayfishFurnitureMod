package com.mrcrayfish.furniture.items;

import com.mrcrayfish.furniture.blocks.BlockHedge;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemHedge extends ItemBlock
{
	private BlockHedge hedge;
	
	public ItemHedge(Block block)
	{
		super(block);
		this.hedge = (BlockHedge) block;
	}

	@Override
	public int getColorFromItemStack(ItemStack stack, int renderPass)
	{
		return hedge.getRenderColor(hedge.getDefaultState());
	}
}
