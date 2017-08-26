package com.mrcrayfish.furniture.blocks;

import net.minecraft.block.material.Material;

public class BlockCurtainsClosed extends BlockCurtains 
{
	public BlockCurtainsClosed(Material material) 
	{
		super(material);
		this.setCreativeTab(null);
	}

	@Override
	public boolean isOpen() 
	{
		return false;
	}
}
