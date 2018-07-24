package com.mrcrayfish.furniture.blocks;

import net.minecraft.block.material.Material;

public class BlockCurtainsOpen extends BlockCurtains
{
    public BlockCurtainsOpen(Material material)
    {
        super(material);
    }

    @Override
    public boolean isOpen()
    {
        return true;
    }
}
