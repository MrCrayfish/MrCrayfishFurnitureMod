package com.mrcrayfish.furniture.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ColorizerFoliage;

public class ItemWreath extends ItemBlock
{
	public ItemWreath(Block block)
	{
		super(block);
	}
	
	@Override
	public int getColorFromItemStack(ItemStack stack, int renderPass)
	{
		return ColorizerFoliage.getFoliageColorPine();
	}
}
