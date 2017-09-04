package com.mrcrayfish.furniture.handler;

import com.mrcrayfish.furniture.init.FurnitureItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

public class FuelHandler implements IFuelHandler 
{
	@Override
	public int getBurnTime(ItemStack fuel) 
	{
		if(fuel.getItem() == FurnitureItems.itemLog)
		{
			return 500;
		}
		return 0;
	}
}
