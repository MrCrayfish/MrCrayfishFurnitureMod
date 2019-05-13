package com.mrcrayfish.furniture;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class FurnitureGroup extends ItemGroup
{
    public FurnitureGroup(String label)
    {
        super(label);
    }

    @Override
    public ItemStack createIcon()
    {
        return new ItemStack(Blocks.GRASS);
    }
}
