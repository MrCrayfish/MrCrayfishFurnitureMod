package com.mrcrayfish.furniture;

import com.mrcrayfish.furniture.core.ModBlocks;
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
        return new ItemStack(ModBlocks.CHAIR_OAK);
    }
}
