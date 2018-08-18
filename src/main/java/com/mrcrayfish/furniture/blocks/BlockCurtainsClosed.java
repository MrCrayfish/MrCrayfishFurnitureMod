package com.mrcrayfish.furniture.blocks;

import net.minecraft.block.material.Material;

/**
 * Author: MrCrayfish
 */
public class BlockCurtainsClosed extends BlockCurtains
{
    public BlockCurtainsClosed()
    {
        super(Material.CLOTH, "curtains_closed");
        this.setCreativeTab(null);
    }

    @Override
    public boolean isOpen()
    {
        return false;
    }
}
