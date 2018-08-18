package com.mrcrayfish.furniture.blocks;

import net.minecraft.block.material.Material;

/**
 * Author: MrCrayfish
 */
public class BlockCurtainsOpen extends BlockCurtains
{
    public BlockCurtainsOpen()
    {
        super(Material.CLOTH, "curtains_open");
    }

    @Override
    public boolean isOpen()
    {
        return true;
    }
}
