package com.mrcrayfish.furniture.items;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;

import net.minecraft.item.ItemSword;

public class ItemKnife extends ItemSword {
	
	public ItemKnife()
	{
		super(ToolMaterial.STONE);
		setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
	}
}
