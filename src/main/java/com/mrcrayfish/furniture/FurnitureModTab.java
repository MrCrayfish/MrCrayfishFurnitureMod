package com.mrcrayfish.furniture;

import com.mrcrayfish.furniture.core.ModBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class FurnitureModTab extends CreativeModeTab
{
    public FurnitureModTab(String label)
    {
        super(label);
    }

    @Override
    public ItemStack makeIcon()
    {
        return new ItemStack(ModBlocks.CHAIR_OAK.get());
    }
}
