package com.mrcrayfish.furniture.blocks;

import net.minecraft.block.material.Material;

/**
 * Author: MrCrayfish
 */
public class BlockModernTV extends BlockAbstractTV
{
    public BlockModernTV()
    {
        super(Material.ANVIL, 22, 12, 4, -0.35);
        this.setUnlocalizedName("modern_tv");
        this.setRegistryName("modern_tv");
    }
}
