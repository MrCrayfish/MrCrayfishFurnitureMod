package com.mrcrayfish.furniture.items;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ColorizerFoliage;

public class ItemWreath extends ItemBlock implements IItemColor
{
	public ItemWreath(Block block)
	{
		super(block);
	}
	
	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) 
	{
		return ColorizerFoliage.getFoliageColorPine();
	}
}
